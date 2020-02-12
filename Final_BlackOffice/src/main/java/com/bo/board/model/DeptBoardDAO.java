package com.bo.board.model;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bo.board.model.DeptBoardVO;
import com.bo.board.model.CommentVO;
import com.bo.member.model.MemberVO;

//=== DAO 선언 ===
@Repository
public class DeptBoardDAO implements InterDeptBoardDAO {

	// === 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private SqlSessionTemplate sqlsession;
	
	// === 로그인 처리하기 ===  
	@Override
	public MemberVO getLoginMember(HashMap<String, String> paraMap) {
		MemberVO loginuser = sqlsession.selectOne("deptBoard.getLoginMember", paraMap);
		return loginuser;
	}
	
	// === 글쓰기(파일첨부가 없는것) ===
	@Override
	public int add(DeptBoardVO deptboardvo) {
		int n = sqlsession.insert("deptBoard.add", deptboardvo);
		return n;
	}
	
	// === 1개 글 보여주기 === //
	@Override
	public DeptBoardVO getView(String seq) {
		DeptBoardVO deptboardvo = sqlsession.selectOne("deptBoard.getView", seq);
		return deptboardvo;
	}
	// === 글조회수 1증가하기 === //
	@Override
	public void setAddReadCount(String seq) {
		sqlsession.update("deptBoard.setAddReadCount", seq);
	}

	/* 기능구현이 블안하므로 주석처리 */
	// === 글수정 및 글삭제시 작성자 이이디 일치 여부 알아오기 === 
	@Override
	public boolean checkID(DeptBoardVO deptboardvo) {
		int n = sqlsession.selectOne("deptBoard.checkID", deptboardvo); 
		
		if(n==1)
			return true;
		else
			return false;
	}
	
/*
	@Override
	public boolean checkID(DeptBoardVO deptboardvo) {
		// TODO Auto-generated method stub
		return false;
	}*/

	// === 글 1개를 수정한다. === 
	@Override
	public int updateBoard(DeptBoardVO deptboardvo) {
		int n = sqlsession.update("deptBoard.updateBoard", deptboardvo);
		return n;
	}

	// === 글1개를 삭제한다. === 
	@Override
	public int deleteBoard(String seq) {
		int n = sqlsession.update("deptBoard.deleteBoard", seq);
		return n;
	}

	// ===댓글쓰기(tblComment 테이블에 insert) === //
	@Override
	public int addComment(CommentVO commentvo) {
		int n = sqlsession.insert("deptBoard.addComment", commentvo);
		return n;
	}
	// 노트북 소스 코드 병합
	// ===댓글수정(tblComment 테이블에 update) === //
	@Override
	public int updateComment(HashMap<String, String> paraMap) {
		int updatedCommentCount = sqlsession.update("deptBoard.updateComment", paraMap);
		return updatedCommentCount;
	}
/*
	// ===댓글수정(tblComment 테이블에 update) === //
	@Override
	public int editComment(CommentVO content) {
		int n = sqlsession.update("deptBoard.editComment", content);
		return n;
	}
*/
	
	// tblBoard 테이블에 commentCount 컬럼의 값을 1증가(update) === 
	@Override
	public int updateCommentCount(String parentSeq) {
		int n = sqlsession.update("deptBoard.updateCommentCount", parentSeq);
		return n;
	}
	
	
	// 노트북 소스 병합
	// tblBoard 테이블에 commentCount 컬럼의 값을 1증가(update) === 
	@Override
	public int commentCountDown(String parentSeq) {
		int n = sqlsession.update("deptBoard.commentCountDown", parentSeq);
		return n;
	}
	
	@Override
	public int commentCountUp(int parentSeq) {
		int n = sqlsession.update("deptBoard.commentCountUp", parentSeq);
		return n;
	}
	/*
	// tblBoard 테이블에 commentCount 컬럼의 값을 1증가(update) === 
	@Override
	public int updateCommentCount(String parentSeq) {
		int n = sqlsession.update("deptBoard.updateCommentCount", parentSeq);
		return n;
	}
	
	// tblBoard 테이블에 commentCount 컬럼의 값을 1감소(update) === 
	@Override
	public int minusCommentCount(String parentSeq) {
		int n = sqlsession.update("deptBoard.minusCommentCount", parentSeq);
		return n;
	}
	*/
	
		


	// === 원게시물에 딸린 댓글 보여주기 === //
	@Override
	public List<CommentVO> getCommentList(String parentSeq) {
		List<CommentVO> commentList = sqlsession.selectList("deptBoard.getCommentList", parentSeq);
		return commentList;
	}
	
	// 노트북 소스 코드 병합
	// === 원게시물에 딸린 모든 댓글들을 삭제하도록 한다. === 
	@Override
	public int deleteComment(int seq) {
		int deletedCommentCount = sqlsession.delete("deptBoard.deleteComment", seq);
		return deletedCommentCount;
	}
	
	@Override
	public int selectDeleteComment(String commentSeq) {
		int deletedCommentCount = sqlsession.delete("deptBoard.selectDeleteComment", commentSeq);
		return deletedCommentCount;
	}
	
/*
	// === 원게시물에 딸린 모든 댓글들을 삭제하도록 한다. === 
	@Override
	public int deleteComment(String seq) {
		int n = sqlsession.delete("deptBoard.deleteComment", seq);
		return n;
	}
*/
	// ===  페이징 처리를 안한 검색어가 있는 전체 글목록 보여주기 === //
	@Override
	public List<DeptBoardVO> boardListSearch(HashMap<String, String> paraMap) {
		List<DeptBoardVO> boardList = sqlsession.selectList("deptBoard.boardListSearch", paraMap);
		return boardList;
	}
	
	// === 검색어 입력시 자동글 완성하기 5 === //
	@Override
	public List<String> wordSearchShow(HashMap<String, String> paraMap) {
		List<String> wordList = sqlsession.selectList("deptBoard.wordSearchShow", paraMap);
		return wordList;
	}
	
	// === 검색조건이 없을 경우의 총 게시물 건수(totalCount) === 
	@Override
	public int getTotalCountWithNOsearch() {
		int count = sqlsession.selectOne("deptBoard.getTotalCountWithNOsearch");
		return count;
	}	
		
	// 검색조건이 있을 경우의 총 게시물 건수(totalCount)
	@Override
	public int getTotalCountWithSearch(HashMap<String, String> paraMap) {
		int count = sqlsession.selectOne("deptBoard.getTotalCountWithSearch", paraMap);
		
		return count;
	}
	
	// 페이징 처리한 글목록 가겨오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)
	@Override
	public List<DeptBoardVO> boardListWithPaging(HashMap<String, String> paraMap) {
		List<DeptBoardVO> boardList = sqlsession.selectList("deptBoard.boardListWithPaging", paraMap);
		return boardList;
	}

	// 페이징 처리를 안한 검색어가 없는 전체 글목록 보여주기 == //
	@Override
	public List<DeptBoardVO> boardListNoSearch() {
		List<DeptBoardVO> boardList = sqlsession.selectList("deptBoard.boardListNoSearch");
		return boardList;
	}

	// === 글쓰기(첨부파일이 있는 글쓰기) === 
	@Override
	public int add_withFile(DeptBoardVO deptboardvo) {
		int n = sqlsession.insert("deptBoard.add_withFile", deptboardvo);
		return n;
	}
		

	// 게시물에 달린 댓글 겟수 알아오기
	@Override
	public int getCommentCount(String seq) {
		int CommentCount = sqlsession.selectOne("deptBoard.getCommentCount", seq);
		return CommentCount;
	}

	

}
