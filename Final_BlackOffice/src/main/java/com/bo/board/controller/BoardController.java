package com.bo.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bo.board.service.InterBoardService;
import com.bo.board.model.BoardVO;
import com.bo.common.MyUtil;
import com.bo.member.model.MemberVO;

//=== 컨트롤러 선언 ===
@Component
/* XML에서 빈을 만드는 대신에 클래스명 앞에 @Component 어노테이션을 적어주면 해당 클래스는 bean으로 자동 등록된다. 
그리고 bean의 이름(첫글자는 소문자)은 해당 클래스명이 된다. */
@Controller
public class BoardController {
	
	/* 관리자 메뉴 관련 컨트롤러  2020/01/05 kyh*/
	
	// === 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private InterBoardService service;
			
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

