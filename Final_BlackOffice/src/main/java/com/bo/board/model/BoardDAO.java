package com.bo.board.model;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bo.board.model.BoardVO;
import com.bo.board.model.CommentVO;

//=== DAO 선언 ===
@Repository
public class BoardDAO implements InterBoardDAO {

	// === 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private SqlSessionTemplate sqlsession;
	
	// === 글쓰기(파일첨부가 없는것) ===
	@Override
	public int add(BoardVO boardvo) {
		int n = sqlsession.insert("board.add", boardvo);
		return n;
	}

	// === 1개 글 보여주기 === //
	@Override
	public BoardVO getView(String seq) {
		BoardVO boardvo = sqlsession.selectOne("board.getView", seq);
		return boardvo;
	}
	// === 글조회수 1증가하기 === //
	@Override
	public void setAddReadCount(String seq) {
		sqlsession.update("board.setAddReadCount", seq);
	}


	// === 글수정 및 글삭제시 작성자 이이디 일치 여부 알아오기 === 
	@Override
	public boolean checkID(BoardVO boardvo) {
		int n = sqlsession.selectOne("board.checkID", boardvo); 
		
		if(n==1)
			return true;
		else
			return false;
	}


	// === 글 1개를 수정한다. === 
	@Override
	public int updateBoard(BoardVO boardvo) {
		int n = sqlsession.update("board.updateBoard", boardvo);
		return n;
	}

	// === 글 1개를 삭제한다. === //  
		@Override
		public int deleteBoard(String seq) {
			int n = sqlsession.update("board.deleteBoard", seq);
			return n;
		}

	// ===댓글쓰기(tblComment 테이블에 insert) === //
	@Override
	public int addComment(CommentVO commentvo) {
		int n = sqlsession.insert("board.addComment", commentvo);
		return n;
	}

	// === tblBoard 테이블에 commentCount 컬럼의 값을 1증가(update) === //
	@Override
	public int updateCommentCount(String parentSeq) {
		int n = sqlsession.update("board.updateCommentCount", parentSeq);
		return n;
	}

	// === 원게시물에 딸린 댓글 보여주기 === //
	@Override
	public List<CommentVO> getCommentList(String parentSeq) {
		List<CommentVO> commentList = sqlsession.selectList("board.getCommentList", parentSeq);
		return commentList;
	}

	// === 원게시물에 딸린 모든 댓글들을 삭제하도록 한다. === //
	@Override
	public int deleteComent(String seq) {
		int n = sqlsession.delete("board.deleteComent", seq);
		return n;
	}
	
	// ===  페이징 처리를 안한 검색어가 있는 전체 글목록 보여주기 === //
	@Override
	public List<BoardVO> boardListSearch(HashMap<String, String> paraMap) {
		List<BoardVO> boardList = sqlsession.selectList("board.boardListSearch", paraMap);
		return boardList;
	}

	// 검색조건이 있을 경우의 총 게시물 건수(totalCount)
	@Override
	public int getTotalCountWithsearch(HashMap<String, String> paraMap) {
		int count = sqlsession.selectOne("board.getTotalCountWithsearch", paraMap);
		return count;
	}

	// 페이징 처리한 글목록 가겨오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)
	@Override
	public List<BoardVO> boardListWithPaging(HashMap<String, String> paraMap) {
		List<BoardVO> boardList = sqlsession.selectList("board.boardListWithPaging", paraMap);
		return boardList;
	}

	// 페이징 처리를 안한 검색어가 없는 전체 글목록 보여주기 == //
	@Override
	public List<BoardVO> boardListNoSearch() {
		List<BoardVO> boardList = sqlsession.selectList("board.boardListNoSearch");
		return boardList;
	}

	// 검색조건이 없을 경우의 총 게시물 건수(totalCount)
	@Override
	public int getTotalCountWithNOsearch() {
		int count = sqlsession.selectOne("board.getTotalCountWithNOsearch");
		return count;
	}

	

	
	
	
	
}
