package com.bo.board.model;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bo.member.model.MemberVO;

//=== DAO 선언 ===
@Repository
public class BoardDAO implements InterBoardDAO {

	// === 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private SqlSessionTemplate sqlsession;

	
	/*// === 메인 페이지용 이미지 파일명 가져오기 ===  
	@Override
	public List<String> getImgfilenameList() {
		List<String> imgfilenameList = sqlsession.selectList("board.getImgfilenameList");
		return imgfilenameList;
	}*/


	/*// === 로그인 처리하기 ===  
	@Override
	public MemberVO getLoginMember(HashMap<String, String> paraMap) {
		MemberVO loginuser = sqlsession.selectOne("board.getLoginMember", paraMap);
		return loginuser;
	}*/
	
	/*// 마지막으로 로그인 한 날짜시간 변경(기록)하기
	@Override
	public void setLastLoginDate(HashMap<String, String> paraMap) {
		sqlsession.update("board.setLastLoginDate", paraMap);
	}*/

	// === 글쓰기(파일첨부가 없는것) ===
	@Override
	public int add(BoardVO boardvo) {
		int n = sqlsession.insert("board.add", boardvo);
		return n;
	}


	// === 페이징 처리를 안한 검색어가 없는 전체 글목록 보여주기 ===
	@Override
	public List<BoardVO> boardListNoSearch() {
		List<BoardVO> boardList = sqlsession.selectList("board.boardListNoSearch");
		return boardList;
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


	// === 글수정 및 글삭제시 암호일치 여부 알아오기 ===  일치하는 아이디만 게시판 수정 및 글삭제가 가능하게 변경 필요
	@Override
	public boolean checkPW(BoardVO boardvo) {
		int n = sqlsession.selectOne("board.checkPW", boardvo); 
		
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
	@Override
	public List<String> getImgfilenameList() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public MemberVO getLoginMember(HashMap<String, String> paraMap) {
		// TODO Auto-generated method stub
		return null;
	}


	
	
	
}
