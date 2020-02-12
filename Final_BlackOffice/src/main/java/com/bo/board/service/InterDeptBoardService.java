package com.bo.board.service;

import java.util.HashMap;
import java.util.List;

import com.bo.board.model.DeptBoardVO;
import com.bo.board.model.CommentVO;
import com.bo.member.model.MemberVO;


public interface InterDeptBoardService {
 
	MemberVO getLoginMember(HashMap<String, String> paraMap); // 로그인 처리하

	int add(DeptBoardVO deptboardvo);  // 글쓰기(파일첨부가 없는것) 

	List<DeptBoardVO> boardListNoSearch(); // == 페이징 처리를 안한 검색어가 없는 전체 글목록 보여주기 == //

	DeptBoardVO getView(String seq, String userid); // 글조회수 증가와 함께 글1개를 조회를 해주는 것
	                                            // 글조회수 증가는 다른 사람의 글을 읽을때만 증가하도록 한다.
	                                            // 로그인 하지 않은 상태에서 글을 읽을때 조회수 증가가 일어나지 않도록 한다. 

	DeptBoardVO getViewWithNoAddCount(String seq); // 글조회수 증가는 없고 단순히 글1개 조회만을 해주는 것.

	int edit(DeptBoardVO deptboardvo); // 1개글 수정하기  

	int getTotalCountWithNOsearch(); // 검색조건이 없을 경우의 총 게시물 건수(totalCount)
	
	int getTotalCountWithSearch(HashMap<String, String> paraMap); // 검색조건이 있을 경우의 총 게시물 건수(totalCount)

	List<DeptBoardVO> boardListWithPaging(HashMap<String, String> paraMap); // 페이징 처리한 글목록 가겨오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)
	
	int del(String seq); // 1개글 삭제하기(댓글쓰기가 없는게시판)

	int addComment(CommentVO commentvo) throws Throwable ; // 댓글쓰기(tblComment 테이블에 insert)
	int updateCommentCount(String parentSeq); // tblBoard 테이블에 commentCount 컬럼의 값을 1증가(update)  

	// 노트북 소스 병합
	int deleteComment(String commentSeq, String parentSeq);// 댓글 삭제
	int updateComment(HashMap<String, String> paraMap);// 댓글수정(tblComment 테이블에 update)
	
/*	
	int editComment(CommentVO content); // 댓글수정(tblComment 테이블에 update)
	int minusCommentCount(String parentSeq); // tblBoard 테이블에 commentCount 컬럼의 값을 1증가(update)  
*/	
	
	List<CommentVO> getCommentList(String parentSeq); // 원게시물에 딸린 댓글 보여주기 
	
	List<String> wordSearchShow(HashMap<String, String> paraMap); // 검색어 입력시 자동글 완성하기 
	
	List<DeptBoardVO> boardListSearch(HashMap<String, String> paraMap); // 페이징 처리를 안한 검색어가 있는 전체 글목록 보여주기 

	int add_withFile(DeptBoardVO deptboardvo); // 글쓰기(첨부파일이 있는 경우의 글쓰기) 

	int getCommentCount(String seq); // 게시물에 달린 댓글 겟수 알아오기



}





