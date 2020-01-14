package com.bo.board.service;

import java.util.HashMap;
import java.util.List;

import com.bo.board.model.BoardVO;
import com.bo.member.model.MemberVO;

public interface InterBoardService {

	List<String> getImgfilenameList(); // 이미지 파일명 가져오기 

/*	MemberVO getLoginMember(HashMap<String, String> paraMap); // 로그인 처리하기  */
	int add(BoardVO boardvo);  // 글쓰기(파일첨부가 없는것) 

	List<BoardVO> boardListNoSearch(); // == 페이징 처리를 안한 검색어가 없는 전체 글목록 보여주기 == //

	BoardVO getView(String seq, String userid); // 글조회수 증가와 함께 글1개를 조회를 해주는 것
	                                            // 글조회수 증가는 다른 사람의 글을 읽을때만 증가하도록 한다.
	                                            // 로그인 하지 않은 상태에서 글을 읽을때 조회수 증가가 일어나지 않도록 한다. 

	BoardVO getViewWithNoAddCount(String seq); // 글조회수 증가는 없고 단순히 글1개 조회만을 해주는 것.

	int edit(BoardVO boardvo); // 1개글 수정하기  
	

}





