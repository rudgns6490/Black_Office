package com.bo.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BoardController {
	
	/* 관리자 메뉴 관련 컨트롤러  2020/01/05 kyh*/
			
	//공지사항 게시판 목록
	@RequestMapping(value="/noticeBoard.action")
	public String noticeBoard() {
		return "board/noticeBoard.tiles1";
	}
	
	//공지 게시판
	@RequestMapping(value="/noticeDetailBoard.action")
	public String noticeDetailBoard() {
		return "board/noticeDetailBoard.tiles1";
	}
	
	//공지 작성
	@RequestMapping(value="/noticeBoardWrite.action")
	public String noticeBoardWrite() {
		return "board/noticeBoardWrite.tiles1";
	}
	
	//부서별 게시판 목록
	@RequestMapping(value="/deptBoard.action")
	public String deptBoard() {
		return "board/deptBoard.tiles1";
	}
	
	//부서별 게시판
	@RequestMapping(value="/deptDetailBoard.action")
	public String deptDetailBoard() {
		return "board/deptDetailBoard.tiles1";
	}
	
	//부서별 게시판
	@RequestMapping(value="/deptBoardWrite.action")
	public String deptBoardWrite() {
		return "board/deptBoardWrite.tiles1";
	}
			
}

