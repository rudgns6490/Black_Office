package com.bo.board.model;

import java.util.HashMap;
import java.util.List;

import com.bo.member.model.MemberVO;

public interface InterBoardDAO {

	List<String> getImgfilenameList(); // 이미지 파일명 가져오기  

	/*MemberVO getLoginMember(HashMap<String, String> paraMap); // 로그인 처리하기       
	void setLastLoginDate(HashMap<String, String> paraMap);   // 마지막으로 로그인 한 날짜시간 변경(기록)하기 
*/
	int add(BoardVO boardvo); //글쓰기(파일첨부가 없는것)

	List<BoardVO> boardListNoSearch(); // 페이징 처리를 안한 검색어가 없는 전체 글목록 보여주기 

	BoardVO getView(String seq);      // 1개 글 보여주기 
	void setAddReadCount(String seq); // 글조회수 1증가하기 

	boolean checkPW(BoardVO boardvo); // 글수정 및 글삭제시 암호일치 여부 알아오기 

	int updateBoard(BoardVO boardvo); // 글 1개를 수정한다.

	MemberVO getLoginMember(HashMap<String, String> paraMap);

	
}

