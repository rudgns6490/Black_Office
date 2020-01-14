package com.bo.board.service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bo.board.model.BoardVO;
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

	// === 글조회수 증가와 함께 글1개를 조회를 해주는 것 ===
    // 글조회수 증가는 다른 사람의 글을 읽을때만 증가하도록 한다.
    // 로그인 하지 않은 상태에서 글을 읽을때 조회수 증가가 일어나지 않도록 한다.
	@Override
	public BoardVO getView(String seq, String userid) { 
		                              // userid 는 로그인한 사용자의 userid 이다. 
		                              // 만약에 로그인을 하지 않은 상태이라면 userid 는 null 이다. 
		BoardVO boardvo = dao.getView(seq);
		
		if(userid != null && !userid.equals(boardvo.getFk_userid()) ) { 
			// 글조회수 증가는 다른 사람의 글을 읽을때만 증가하도록 해야 한다. 
			// 로그인 하지 않은 상태에서는 글조회수 증가는 없다. 
			
			dao.setAddReadCount(seq); // 글조회수 1증가하기 
			boardvo = dao.getView(seq);
		}

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
		
		// 수정하려는 글에 대한 원래의 암호를 읽어와서 
		// 수정시 입력한 암호와 비교를 한다.
		boolean bool = dao.checkPW(boardvo); // 본인아이디랑 일치할경우 수정가능으로 변경
		
		if(!bool) // 암호가 일치하지 않는 경우 
			return 0;
		else {
			// 글 1개를 수정한다.
			int n = dao.updateBoard(boardvo);
			
			return n;
		}
	}

	@Override
	public List<String> getImgfilenameList() {
		// TODO Auto-generated method stub
		return null;
	}

	

	
}