package com.bo.board.model;

import java.util.HashMap;
import java.util.List;


import com.bo.board.model.BoardVO;
import com.bo.board.model.CommentVO;

public interface InterBoardDAO {

	int add(BoardVO boardvo); //글쓰기(파일첨부가 없는것)

	BoardVO getView(String seq);      // 1개 글 보여주기 
	void setAddReadCount(String seq); // 글조회수 1증가하기 

	int updateBoard(BoardVO boardvo); // 글 1개를 수정한다.

	int deleteBoard(String seq); // 글 1개를 삭제한다.

	int addComment(CommentVO commentvo); 		// 댓글쓰기(tblComment 테이블에 insert) 
	int updateCommentCount(String parentSeq);	// tblBoard 테이블에 commentCount 컬럼의 값을 1증가(update)

	List<CommentVO> getCommentList(String parentSeq); // 원게시물에 딸린 댓글 보여주기

	int deleteComent(String seq); // 원게시물에 딸린 모든 댓글들을 삭제하도록 한다.

	List<BoardVO> boardListSearch(HashMap<String, String> paraMap); // 페이징 처리를 안한 검색어가 있는 전체 글목록 보여주기 == //

	
	int getTotalCountWithsearch(HashMap<String, String> paraMap); // 검색조건이 있을 경우의 총 게시물 건수(totalCount)

	List<BoardVO> boardListWithPaging(HashMap<String, String> paraMap); // 페이징 처리한 글목록 가겨오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)

	boolean checkID(BoardVO boardvo); // 글수정 및 글삭제시 작성자 이이디 일치 여부 알아오기  

	List<BoardVO> boardListNoSearch(); // == 페이징 처리를 안한 검색어가 없는 전체 글목록 보여주기 == //

	int getTotalCountWithNOsearch(); // 검색조건이 없을 경우의 총 게시물 건수(totalCount)
	
	
}

