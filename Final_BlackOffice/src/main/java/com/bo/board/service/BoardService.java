package com.bo.board.service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bo.board.model.BoardVO;
import com.bo.board.model.CommentVO;
import com.bo.board.model.InterBoardDAO;
import com.bo.member.model.MemberVO;


//=== Service 선언 ===
@Service 
public class BoardService implements InterBoardService {

	// === 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private InterBoardDAO dao;
	
	// === 글쓰기(파일첨부가 없는것) ===
	@Override
	public int add(BoardVO boardvo) {
		
		int n = dao.add(boardvo);
		
		return n;
	}
	
	// == 페이징 처리를 안한 검색어가 없는 전체 글목록 보여주기 == //
		@Override
		public List<BoardVO> boardListNoSearch() {
			List<BoardVO> boardList = dao.boardListNoSearch();
			return boardList;
		}

	

	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)
		@Override
		public List<BoardVO> boardListWithPaging(HashMap<String, String> paraMap) {
			List<BoardVO> boardList = dao.boardListWithPaging(paraMap);
			return boardList;
		}

	// === 글조회수 증가와 함께 글1개를 조회를 해주는 것 ===
    // 글조회수 증가는 다른 사람의 글을 읽을때만 증가하도록 한다.
    // 로그인 하지 않은 상태에서 글을 읽을때 조회수 증가가 일어나지 않도록 한다.
	@Override
	public BoardVO getView(String seq, String userid) { 
		                              // userid 는 로그인한 사용자의 userid 이다. 
		                              // 만약에 로그인을 하지 않은 상태이라면 userid 는 null 이다. 
		BoardVO boardvo = dao.getView(seq);
		
		
			dao.setAddReadCount(seq); // 글조회수 1증가하기 
			boardvo = dao.getView(seq);
			
		/* 글조회수 증가 안되므로 조건절 삭제함
		if(userid != null && !userid.equals(boardvo.getFk_userid()) ) { 
			// 글조회수 증가는 다른 사람의 글을 읽을때만 증가하도록 해야 한다. 
			// 로그인 하지 않은 상태에서는 글조회수 증가는 없다. 
			
			dao.setAddReadCount(seq); // 글조회수 1증가하기 
			boardvo = dao.getView(seq);
		}
		*/
		return boardvo;
	}
	
	// === 글조회수 증가는 없고 단순히 글1개 조회만을 해주는 것. ===
	@Override
	public BoardVO getViewWithNoAddCount(String seq) {
		BoardVO boardvo = dao.getView(seq);
		return boardvo;
	}
	
	// === 1개글 수정하기 === 
	@Override
	public int edit(BoardVO boardvo) {
		
		int n = dao.updateBoard(boardvo);
		return n;
		// 수정시 작성자 아이디 비교를 통해 수정 권한 여부 실폐로 주석처리함.
		
		/*
		// 수정하려는 글에 대한 작성자 아이디를 읽어와 
		// 작성자 아이디와 로그인한 아이디 비교를 한다.
		boolean bool = dao.checkID(boardvo); // 본인아이디랑 일치할경우 수정가능으로 변경
		
		if(!bool) // 로그인 아이디가 작성자 아이디와 일치하지 않는 경우 
			return 0;
		else {
			// 글 1개를 수정한다.
			int n = dao.updateBoard(boardvo);
			
			return n;
		}
		*/
		
		
	}

	// === #80. 1개글 삭제하기(댓글쓰기가 없는 게시판) === //
		// === #99. 댓글쓰기 게시판인 경우 트랜잭션 처리를 해야 한다. === //  ◀ 2020.01.14.화
		/* (non-Javadoc)
		 * @see com.bo.board.service.InterBoardService#del(com.bo.board.model.BoardVO)
		 */
		@Override
		@Transactional(propagation=Propagation.REQUIRED, isolation= Isolation.READ_COMMITTED, rollbackFor={Throwable.class})
		public int del(String seq) {
			
			//뎃글 있는 계시판에서의 삭제 진행
			int m = 0;
			BoardVO boardVO = dao.getView(seq);
			// 원글에 딸린 댓글의 개수를 알아온다.
			// 에러로 인한 int -> string
			/*String strCommentCount = "";
			
			if(boardVO != null) {
				strCommentCount = boardVO.getCommentCount();
			}
			else {
				System.out.println("확인용!!!!!!! 글 조회가 안됨");
			}
			
			
			System.out.println("strCommentCount: " + strCommentCount);
			
			int commentCount = Integer.parseInt(strCommentCount);
			if( commentCount > 0 ) {
				// 댓글이 있으면 댓글을 모두 삭제하도록 한다.
				m = dao.deleteComment(seq);
			}
			*/
			
			// 글1개를 삭제한다.
			int n = dao.deleteBoard(seq);
		
			// return n; // 뎃글 없는 계시판에서의 삭제 진행
			
			// 뎃글 있는 계시판에서의 삭제 진행
			 int result = (m==0)?n:n*m;
			
			return result;
		
			
	/*	아이디 확인후 글삭제 기능구현 못함 주석처리.
			// 삭제하려는 글에 대한 원래의 암호를 읽어와서
			// 삭제시 입력한 글암호와 비교를 한다.
			boolean bool = dao.checkID(boardvo);
			
			if(!bool) { // 로그인 아이디가 작성자 아이디와 일치하지 않는 경우
				return 0;
			}
			else { // 로그인 아이디가 작성자 아이디와 일치하는 경우
				/////////////////////////////////////////////////////////////////
				// ▼ 2020.01.14.화
				// === 댓글이 있는 게시판인 경우에만 === //
				// 먼저 원게시물에 딸린 모든 댓글들을 삭제하도록 한다.
				String seq = boardvo.getSeq();
				
				int m = 0;
				String strCommentCount = dao.getView(seq).getCommentCount();
				// 원글에 딸린 댓글의 개수를 알아온다.
				
				int commentCount = Integer.parseInt(strCommentCount);
				if( commentCount > 0 ) {
					// 댓글이 있으면 댓글을 모두 삭제하도록 한다.
					m = dao.deleteComment(boardvo.getSeq());
				}
				// ▲ 2020.01.14.화
				/////////////////////////////////////////////////////////////////
				
				// 글1개를 삭제한다.
				int n = dao.deleteBoard(boardvo);
			
				
				int n = dao.deleteBoard(boardvo.getNoticeno());
				// return n;
				
				int result = (m==0)?n:n*m;
				return result;
			}
	*/
						
		}
		/*
		// === 댓글쓰기 === 
		// tblComment 테이블에 insert 된 다음에 
		// tblBoard 테이블에 commentCount 컬럼이 1증가(update) 하도록 요청한다.
		// 즉, 2개이상의 DML 처리를 해야하므로 Transaction 처리를 해야 한다.
		// >>>>> 트랜잭션처리를 해야할 메소드에 @Transactional 어노테이션을 설정하면 된다. 
		// rollbackFor={Throwable.class} 은 롤백을 해야할 범위를 말하는데 Throwable.class 은 error 및 exception 을 포함한 최상위 루트이다. 즉, 해당 메소드 실행시 발생하는 모든 error 및 exception 에 대해서 롤백을 하겠다는 말이다.
		@Override
		@Transactional(propagation=Propagation.REQUIRED, isolation= Isolation.READ_COMMITTED, rollbackFor={Throwable.class})
		public int addComment(CommentVO commentvo) throws Throwable {
			
			int result = 0;
			int n = 0;
			
			n = dao.addComment(commentvo); // 댓글쓰기(tblComment 테이블에 insert)
			if(n==1) {
				result = dao.updateCommentCount(commentvo.getParentSeq()); // tblBoard 테이블에 commentCount 컬럼의 값을 1증가(update) 
			}
			
			return result;
		}
		
		// === 원게시물에 딸린 댓글 보여주기 === //
		@Override
		public List<CommentVO> getCommentList(String parentSeq) {
			List<CommentVO> commentList = dao.getCommentList(parentSeq);
			return commentList;
		}
*/
		// 페이징 처리를 안한 검색어가 있는 전체 글목록 보여주기 === 
		@Override
		public List<BoardVO> boardListSearch(HashMap<String, String> paraMap) { 
			List<BoardVO> boardList = dao.boardListSearch(paraMap);
			return boardList;
		}

		// 검색어 입력시 자동글 완성하기 4 === //
		@Override
		public List<String> wordSearchShow(HashMap<String, String> paraMap) {
			List<String> wordList = dao.wordSearchShow(paraMap); 
			return wordList;
		}
				
		// 검색조건이 없을 경우의 총 게시물 건수(totalCount)
		@Override
		public int getTotalCountWithNOsearch() {
			int count = dao.getTotalCountWithNOsearch();
			return count;
		}
					
		// === 글쓰기(첨부파일이 있는 경우의 글쓰기) ===  
		@Override
		public int add_withFile(BoardVO boardvo) {
			
			int n = dao.add_withFile(boardvo); // 첨부파일이 있는 경우 
			return n;	
		}
/*
		@Override
		public int updateCommentCount(String parentSeq) {
			// TODO Auto-generated method stub
			return 0;
		}
	*/	
		// 검색조건이 있을 경우의 총 게시물 건수(totalCount) ===  
		@Override
		public int getTotalCountWithSearch(HashMap<String, String> paraMap) {
			int count = dao.getTotalCountWithSearch(paraMap);
			return count;
		}

		// === #42. 로그인 처리하기 ===
		@Override
		public MemberVO getLoginMember(HashMap<String, String> paraMap) { 
			MemberVO loginuser = dao.getLoginMember(paraMap);
			
			return loginuser;
		}	

	
}