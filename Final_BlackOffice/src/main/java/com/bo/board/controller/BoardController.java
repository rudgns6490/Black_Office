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
import com.bo.board.common.MyUtil;
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
			
	
	
	
	// === #51. 게시판 글쓰기 폼페이지 요청 ===
		@RequestMapping(value="/add.action")
		public ModelAndView requireLogin_add(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
			
			mav.setViewName("board/add.tiles1");
			
			return mav;
		}
		
		
		// === #54. 게시판 글쓰기 완료 요청 === 
		@RequestMapping(value="/addEnd.action", method= {RequestMethod.POST})
		public ModelAndView addEnd(BoardVO boardvo, ModelAndView mav) {
			
			// *** 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어 코드) 작성하기 *** //
			boardvo.setSubject(MyUtil.replaceParameter(boardvo.getSubject()));
			boardvo.setContent(MyUtil.replaceParameter(boardvo.getContent()));  	   
			boardvo.setContent(boardvo.getContent().replaceAll("\r\n", "<br/>"));
			
			int n = service.add(boardvo);
			
			mav.addObject("n", n);
			mav.setViewName("board/addEnd.tiles1");
			
			return mav;
		}
		
		
		// === #58. 글목록 보기 페이지 요청 ===
		@RequestMapping(value="/list.action", method= {RequestMethod.GET})
		public ModelAndView list(HttpServletRequest request, ModelAndView mav) {
			
			List<BoardVO> boardList = null;
			
			// == 페이징 처리를 안한 검색어가 없는 전체 글목록 보여주기 == //
			boardList = service.boardListNoSearch();
			
			//////////////////////////////////////////////////////////////
			// === #69. 글조회수(readCount)증가 (DML문 update)는
			//          반드시 목록보기에 와서 해당 글제목을 클릭했을 경우에만 증가되고,
			//          웹브라우저에서 새로고침(F5)을 했을 경우에는 증가가 되지 않도록 해야 한다.
			//          이것을 하기 위해서는 session 을 사용하여 처리하면 된다.
			HttpSession session = request.getSession();
			session.setAttribute("readCountPermission", "yes"); 
			/*
			session 에  "readCountPermission" 키값으로 저장된 value값은 "yes" 이다.
			session 에  "readCountPermission" 키값에 해당하는 value값 "yes"를 얻으려면 
			반드시 웹브라우저에서 주소창에 "/list.action" 이라고 입력해야만 얻어올 수 있다. 
			*/
			///////////////////////////////////////////////////////////////
			
			mav.addObject("boardList", boardList);
			mav.setViewName("board/list.tiles1");
			
			return mav;
		}
		
		
		// === #62. 글1개를 보여주는 페이지 요청 ===
		@RequestMapping(value="/view.action", method= {RequestMethod.GET})
		public ModelAndView view(HttpServletRequest request, ModelAndView mav) {
			
			// 조회하고자 하는 글번호 받아오기
			String seq = request.getParameter("seq");
			
			
			HttpSession session = request.getSession();
			MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
			
			String id = null;
			
			if(loginuser != null) {
				id = loginuser.getId();
				// 로그인 되어진 사용자의 userid
			}
			
			// === #68. !!! 중요  !!! 
		    //     글1개를 보여주는 페이지 요청은 select 와 함께 
		    //     DML문(지금은 글조회수 증가인 update문)이 포함되어져 있다.
		    //     이럴경우 웹브라우저에서 페이지 새로고침(F5)을 했을때 DML문이 실행되어
		    //     매번 글조회수 증가가 발생한다.
		    //     그래서 우리는 웹브라우저에서 페이지 새로고침(F5)을 했을때는
		    //     단순히 select만 해주고 DML문(지금은 글조회수 증가인 update문)은 
		    //     실행하지 않도록 해주어야 한다. !!! === //
			
			BoardVO boardvo = null;
			
			// 위의 글목록보기 #69. 에서 session.setAttribute("readCountPermission", "yes"); 해두었다. 
			if("yes".equals(session.getAttribute("readCountPermission"))) {
				// 글목록보기를 클릭한 다음 특정글을 조회해온 경우이다. 
				
				boardvo = service.getView(seq, id); 
				// 글조회수 증가와 함께 글1개를 조회를 해주는 것 
				
				session.removeAttribute("readCountPermission");
				// 중요함!! session 에 저장된 readCountPermission 을 삭제한다. 
			}
			else {
				// 웹브라우저에서 새로고침(F5)를 클릭한 경우이다.
				
				boardvo = service.getViewWithNoAddCount(seq);
				// 글조회수 증가는 없고 단순히 글1개 조회만을 해주는 것.
			}
			
			
			
			mav.addObject("boardvo", boardvo);
			mav.setViewName("board/view.tiles1");
			
			return mav;
		}
		
		
		// === #71. 글수정 페이지 요청 ===
		@RequestMapping(value="/edit.action", method= {RequestMethod.GET})
		public ModelAndView requireLogin_edit(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
			
			// 글 수정해야할 글번호 가져오기
			String seq = request.getParameter("seq");
			
			// 글 수정해야할 글1개 내용가져오기
			BoardVO boardvo = service.getViewWithNoAddCount(seq);
			// 글조회수 증가는 없고 단순히 글1개 조회만을 해주는 것.
			
			HttpSession session = request.getSession();
			MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
			
			if( !loginuser.getId().equals(boardvo.getFk_userid())) { 
				String msg = "다른 사용자의 글은 수정이 불가합니다.";
				String loc = "javascript:history.back()";
				
				mav.addObject("msg", msg);
				mav.addObject("loc", loc);
				mav.setViewName("msg");
			}
			else {
				// 자신의 글을 수정한 경우 
				// 가져온 1개글을 글수정할 폼이 있는 View단으로 보내준다.
				mav.addObject("boardvo", boardvo);
				mav.setViewName("board/edit.tiles1");
			}
			
			return mav;
		}
		
		
		// === #72. 글수정 페이지 완료하기 ===
		@RequestMapping(value="editEnd.action", method= {RequestMethod.POST})
		public ModelAndView requireLogin_editEnd(HttpServletRequest request, HttpServletResponse response, BoardVO boardvo, ModelAndView mav) { 
			
			// *** 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어 코드) 작성하기 *** //
			boardvo.setSubject(MyUtil.replaceParameter(boardvo.getSubject()));
			boardvo.setContent(MyUtil.replaceParameter(boardvo.getContent()));  	   
			boardvo.setContent(boardvo.getContent().replaceAll("\r\n", "<br/>"));
			
			/* 글 수정을 하려면 원본글의 암호와 수정시 입력해준 암호가 일치할때만 
			      수정이 가능하도록 해야 한다. */
			int n = service.edit(boardvo);
			
			if(n==0) {
				mav.addObject("msg", "암호가 일치하지 않아 글 수정이 불가합니다.");
			}
			else {
				mav.addObject("msg", "글수정 성공!!");
			}
			
			mav.addObject("loc", request.getContextPath()+"/view.action?seq="+boardvo.getSeq());
			mav.setViewName("msg");
			
			return mav;
		}
}

