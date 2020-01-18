package com.bo.board.service;

import java.util.HashMap;
import java.util.List;

import com.bo.board.model.BoardVO;
import com.bo.board.model.CommentVO;


public interface InterBoardService {
 

	int add(BoardVO boardvo);  // 글쓰기(파일첨부가 없는것) 

	List<BoardVO> boardListNoSearch(); // == 페이징 처리를 안한 검색어가 없는 전체 글목록 보여주기 == //

	BoardVO getView(String seq, String userid); // 글조회수 증가와 함께 글1개를 조회를 해주는 것
	                                            // 글조회수 증가는 다른 사람의 글을 읽을때만 증가하도록 한다.
	                                            // 로그인 하지 않은 상태에서 글을 읽을때 조회수 증가가 일어나지 않도록 한다. 

	BoardVO getViewWithNoAddCount(String seq); // 글조회수 증가는 없고 단순히 글1개 조회만을 해주는 것.

	int edit(BoardVO boardvo); // 1개글 수정하기  

	int getTotalCountWithNOsearch(); // 검색조건이 없을 경우의 총 게시물 건수(totalCount)
	
	int getTotalCountWithsearch(HashMap<String, String> paraMap); // 검색조건이 있을 경우의 총 게시물 건수(totalCount)

	List<BoardVO> boardListWithPaging(HashMap<String, String> paraMap); // 페이징 처리한 글목록 가겨오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)
	
	int del(BoardVO boardvo); // 1개글 삭제하기(댓글쓰기가 없는게시판)

	int addComment(CommentVO commentvo) throws Throwable; // 댓글쓰기 (Ajax 로 처리)
		
	List<CommentVO> getCommentList(String parentSeq); // 원게시물에 딸린 댓글들을 조회하는것

	List<BoardVO> boardListSearch(HashMap<String, String> paraMap); // 페이징 처리를 안한 검색어가 있는 전체 글목록 보여주기 

}





