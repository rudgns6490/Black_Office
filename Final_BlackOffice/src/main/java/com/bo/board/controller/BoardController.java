package com.bo.board.controller;

import java.util.HashMap;
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


import com.bo.common.MyUtil;
import com.bo.member.model.MemberVO;
import com.bo.board.model.*;
import com.bo.board.service.*;

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
	//페이징 처리를 한 검색어가 있는 전체 글목록 보여주기
	@RequestMapping(value="/noticeBoardList.action", method = {RequestMethod.GET})
	public ModelAndView noticeBoardList(HttpServletRequest request, ModelAndView mav) {
		
		List<BoardVO> noticeBoardList = null;
	
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		int totalCount = 0;			// 총게시물 건수
		int sizePerPage = 10; 		// 한페이지당 보여줄 게시물수
		int currentShowPageNo = 0;	// 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
		int totalPage = 0;			// 총 페이지수(웹브라우저 상에 보여줄 총페이지 갯수, 페이지바)
		
		int startRno = 0;			// 시작 행번호
		int endRno = 0;				// 끝 행번호
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		
		if( searchWord == null || searchWord.trim().isEmpty() ) { // null 이던지 공백이라면
			searchWord = "";
		}
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
	
		// 먼저 총 게시물 건수(totalCount)를 구해와야한다.
		// 총 게시물 건수(totalCount)는 검색조건이 있을때와 없을때로 나뉘어진다.
		
		// 검색조건이 없을 경우의 총 게시물 건수(totalCount)
		if("".equals(searchWord)) {
			totalCount = service.getTotalCountWithNOsearch();
		}
		
		// 검색조건이 있을 경우의 총 게시물 건수(totalCount)
		else {
			totalCount = service.getTotalCountWithsearch(paraMap);
		}
		
		totalPage = (int) Math.ceil( (double)totalCount/sizePerPage );
	
		if(str_currentShowPageNo == null) {
			// 게시판에 보여지는 초기화면
			
			currentShowPageNo = 1;
			// 즉, 초기 화면은 /list.action?currentShowPageNo=1 로 한다는 말이다.
		
		}
		else {
			
			try {
				currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
				if(currentShowPageNo <1 || currentShowPageNo > totalPage) {
					currentShowPageNo = 1;
				}
			} catch (NumberFormatException e) {
				currentShowPageNo = 1;

			}
			
		}
		// *** 가져올 게시글의 범위를 구한다.(공식임!) *** //
								
		startRno = ((currentShowPageNo-1)* sizePerPage)+1;
		endRno = startRno + sizePerPage -1 ;
		
		paraMap.put("startRno", String.valueOf(startRno));
		paraMap.put("endRno", String.valueOf(endRno));
	
		noticeBoardList = service.boardListWithPaging(paraMap);
		// 페이징 처리한 글목록 가겨오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)
		
		if( !"".equals(searchWord) ) { // 검색이 있다라면
			// 유지시키겠다.
			mav.addObject("paraMap", paraMap);
		}
		
		// === 페이지 바 만들기
		String pageBar ="<ul>";
		
		int blockSize = 10;
		// blockSize 는 1개 블럭(토막)당 보여지는 페이지번호의 갯수 이다.
				
		int loop = 1;
		/*
		    loop 는 1부터 증가하여 1개 블럭을 이루는 페이지번호의 갯수(위의 설명상 지금은  10개(==blockSize))까지만 증가하는 용도이다. 
		*/
				
		int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
		// *** !! 공식이다. !! *** //
		
		String url = "noticeBoardList.action";	
		
		String lastStr = url.substring(url.length()-1);
		if(!"?".equals(lastStr)) 
			url += "?"; 
		
		// *** [이전] 만들기 *** //    
		if(pageNo != 1) {
			pageBar += "&nbsp;<a href='"+url+"&currentShowPageNo="+(pageNo-1)+"&sizePerPage="+sizePerPage+"&searchType="+searchType+"&searchWord="+searchWord+"'>[이전]</a>&nbsp;";
		}
		
		while( !(loop>blockSize || pageNo>totalPage) ) {
			
			if(pageNo == currentShowPageNo) {
				pageBar += "&nbsp;<span style='color: red; border: 1px solid gray; padding: 2px 4px;'>"+pageNo+"</span>&nbsp;";
			}
			else {
				pageBar += "&nbsp;<a href='"+url+"&currentShowPageNo="+pageNo+"&sizePerPage="+sizePerPage+"&searchType="+searchType+"&searchWord="+searchWord+"'>"+pageNo+"</a>&nbsp;"; 
				       // ""+1+"&nbsp;"+2+"&nbsp;"+3+"&nbsp;"+......+10+"&nbsp;"
			}
			
			loop++;
			pageNo++;
		}// end of while---------------------------------
		
		// *** [다음] 만들기 *** //
		if( !(pageNo>totalPage) ) {
			pageBar += "&nbsp;<a href='"+url+"&currentShowPageNo="+pageNo+"&sizePerPage="+sizePerPage+"&searchType="+searchType+"&searchWord="+searchWord+"'>[다음]</a>&nbsp;"; 
		}

		pageBar += "</ul>";
		
		mav.addObject("pageBar", pageBar);

		////////////////////////////////////
		String gobackURL = MyUtil.getCurrentURL(request);
		
		// 페이징 처리 되어진 후 특정 글제목을 클릭하여 상세내용을 본이후
		// 사용자가 목록보기 버튼을 클릭 했을때 돌아갈 페이지를 알려주기 위해
		// 현재 페이지 주소를 뷰단으로 넘겨준다.
		mav.addObject("gobackURL", gobackURL);

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
		반드시 웹브라우저에서 주소창에 "/noticeBoard.action" 이라고 입력해야만 얻어올 수 있다. 
		*/
		///////////////////////////////////////////////////////////////
		
		mav.addObject("noticeBoardList", noticeBoardList);
		mav.setViewName("board/noticeBoardList.tiles1");
		
		return mav;
	}
					
	// 공지사항 게시물 하나를 보여주는 페이지 요청 ===
	@RequestMapping(value="/noticeDetailBoard.action", method = {RequestMethod.GET})
	public ModelAndView noticeDetailBoard(HttpServletRequest request, ModelAndView mav) {
		
		// 조회하고자 하는 글번호 받아오기
		String seq = request.getParameter("seq"); // jsp의 form 을 통해 보내진 input 태그의 name - seq
		String gobackURL = request.getParameter("gobackURL");
		mav.addObject("gobackURL", gobackURL);
		
		
		// 로그인 유무확인
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		String userid = null;
		
		if( loginuser != null ) { // null 이 아니면 로그인을 했다.
			userid = loginuser.getUserid();
			// 로그인 되어진 사용자의 userid
		}
		
		// 		글을 새로고침했을 때 DML 안되게 막기 ===
		// 				!!! 중요  !!! 
		//     글1개를 보여주는 페이지 요청은 select 와 함께 
	    //     DML문(지금은 글조회수 증가인 update문)이 포함되어져 있다.
	    //     이럴경우 웹브라우저에서 페이지 새로고침(F5)을 했을때 DML문이 실행되어
	    //     매번 글조회수 증가가 발생한다.
	    //     그래서 우리는 웹브라우저에서 페이지 새로고침(F5)을 했을때는
	    //     단순히 select만 해주고 DML문(지금은 글조회수 증가인 update문)은 
	    //     실행하지 않도록 해주어야 한다. !!! === //
				
		BoardVO boardvo = null;
		
		// 위의 글목록 보기 - [ session.setAttribute("readCountPermission", "yes"); ] 해두었다.
		if( "yes".equals(session.getAttribute("readCountPermission")) ) {
			// 글목록보기를 클릭한 다음 특정글을 조회해온 경우이다.
			boardvo = service.getView(seq, userid); // userid 로 누구인지 알 수 있게 넣어준다.
			// ▲ 글조회수 증가와 함께 글 1개를 조회를 해주는 것
			
			session.removeAttribute("readCountPermission");
			// 중요함 !!! session 에 저장된 readCountPermission 을 삭제한다.
		}
		else { // 웹브라우저에서 새로고침(F5)을 누른 경우다. 
			boardvo = service.getViewWithNoAddCount(seq);
			// ▲ 글조회수 증가는 없고 단순히 글 1개 조회만 해주는 것.
		}
			
		
		mav.addObject("boardvo", boardvo);
		mav.setViewName("board/noticeDetailBoard.tiles1");
		
		return mav;
	}
	
	// === 공지 글쓰기 홈페이지 요청
	@RequestMapping(value="/noticeBoardWrite.action")
	public ModelAndView requireLogin_noticeBoardWrite(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		mav.setViewName("/board/noticeBoardWrite.tiles1");
		
		return mav;
	}
	
	//공지 작성 완료
	@RequestMapping(value="/noticeBoardWriteEnd.action")
	public ModelAndView addEnd(BoardVO boardvo, ModelAndView mav) {
		
		// *** 크로스 사이트 스트립트 공격에 대응하는 안전한 코드(secure code)를 작성해주는 메소드 생성 *** //
		boardvo.setSubject( MyUtil.replaceParameter(boardvo.getSubject()) );
		boardvo.setContent( MyUtil.replaceParameter(boardvo.getContent()) );
		boardvo.setContent( boardvo.getContent().replaceAll("\r\n", "<br>") ) ;
		
		int n = service.add(boardvo);
			mav.addObject("n", n);
			mav.setViewName("board/noticeBoardWriteEnd.tiles1");
		return mav;
			
	}
	
	// 공지사항 게시물 수정 페이지요청 === //
	@RequestMapping(value="/noticeBoardEdit.action", method = {RequestMethod.GET})
	public ModelAndView requireLogin_noticeBoardEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		// 글 수정 해야할 글번호 가져오기
		String seq = request.getParameter("seq");
		
		// 글 수정해야할 글 1개 내용 가져오기 ( 본인의 것이면 조회수를 올리지 않는다. )
		 BoardVO boardvo = service.getViewWithNoAddCount(seq);
		// ▲ 글조회수 증가는 없고 단순히 글 1개 조회만 해주는 것.
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		if( !loginuser.getUserid().equals(boardvo.getFk_userid()) ) {
						
			
			String msg = "다른 사용자의 글은 수정이 불가합니다.";
			String loc = "javascript:history.back()";
			
			mav.addObject("msg", msg);
			mav.addObject("loc", loc);
			mav.setViewName("msg");
		}
		else { // 자신의 글을 수정한 경우
			
			// (edit.jsp View 단)HTML 에서 <br> 을 \n 으로 바꾸지 않으면 아래를 사용한다.
//			// *** 크로스 사이트 스트립트 공격에 대응하는 안전한 코드(secure code)를 작성해주는 메소드 생성 *** //
//			boardvo.setSubject( MyUtil.replaceParameter(boardvo.getSubject()) );
//			boardvo.setContent( MyUtil.replaceParameter(boardvo.getContent()) );
//			boardvo.setContent( boardvo.getContent().replaceAll("<br>", "\r\n") ) ; // <br> 을 엔터로 바꾼다.
						
			// 가져온 1개글을 글수정할 폼에 있는 View단으로 보내준다.
			mav.addObject("boardvo", boardvo);
			mav.setViewName("board/noticeBoardEdit.tiles1");
		}
		
		return mav;
	}
	
	// 공지사항 게시물 글수정 페이지 완료하기 === //
	@RequestMapping(value="/noticeBoardEditEnd.action", method = {RequestMethod.POST})
	public ModelAndView requireLogin_noticeBoardEditEnd(HttpServletRequest request, HttpServletResponse response, BoardVO boardvo, ModelAndView mav) {
		
		// *** 크로스 사이트 스트립트 공격에 대응하는 안전한 코드(secure code)를 작성해주는 메소드 생성 *** //
		boardvo.setSubject( MyUtil.replaceParameter(boardvo.getSubject()) );
		boardvo.setContent( MyUtil.replaceParameter(boardvo.getContent()) );
		boardvo.setContent( boardvo.getContent().replaceAll("\r\n", "<br>") ) ;
		
		// 글 수정을 하려면 기존암호와 수정시 입력해준 암호가 일치할때만 수정이 가능하도록 한다.
		int n = service.edit(boardvo);
		
		if( n == 0 ) {
			mav.addObject("msg", "암호가 일치하지 않아 글 수정이 불가합니다.");
		}
		else {
			mav.addObject("msg", "글수정 성공!!!");
		}
		
		// 글성공시 수정된 주소로 이동하기 위해
		mav.addObject("loc", request.getContextPath() + "/noticeDetailBoard.action?seq=" + boardvo.getSeq());
		mav.setViewName("msg");
		
		// 로그인 유무확인 ( requireLogin : 로그인 해야만 한다고 알기위해 이름지은 것 )
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		return mav;
				
	}
	
	 // 공지사항 글삭제 페이지 요청하기 
 	@RequestMapping(value="/noticeBoardDelete.action", method = {RequestMethod.GET}) 
 	public ModelAndView requireLogin_noticeBoardDelete(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
 		
 		// 삭제해야할 글 번호를 받아온다.
 		String seq = request.getParameter("seq");
 		
 		// 삭제해야할  글1개 내용 가져와서 로그인한 사람이 쓴 글이라면 글 삭제가 가능하지만
 		// 다른사람이 쓴 글은 삭제가 불가하도록 해야 한다.
 		BoardVO boardvo = service.getViewWithNoAddCount(seq);
 		// ▲ 글조회수(readCound) 증가 없이 그냥 글1개만 가져오는 것
 		
 		HttpSession session = request.getSession();
 		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
 		
 		if( !loginuser.getUserid().equals(boardvo.getFk_userid()) ) { // 남이쓴글
 			String msg = "다른 사용자의 글은 삭제가 불가합니다.";
 			String loc = "javascript:history.back()";
 			
 			mav.addObject("msg", msg);
 			mav.addObject("loc", loc);
 			mav.setViewName("msg");
 		}
 		else { // 내가 쓴글
 			// 자신의 글을 삭제할 경우
 			// 글작성시 입력했던 암호와 일치하는지 알아오도록
 			// del.jsp 페이지로 넘긴다.
 			mav.addObject("seq", seq);
 			mav.setViewName("board/noticeBoardDelete.tiles1");
 		}
 		
		return mav;
 	}

 	// 공지사항 글삭제 페이지 완료하기 === //
  	@RequestMapping(value="/noticeBoardDeleteEnd.action", method = {RequestMethod.POST}) 
  	public ModelAndView requireLogin_noticeBoardDeleteEnd(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
  		
  		// 삭제해야할 글번호 및 사용자가 입력한 글암호를 받아온다.
  		String seq = request.getParameter("seq");
  		String pw = request.getParameter("pw");
  		
  		BoardVO boardvo = new BoardVO();
  		boardvo.setSeq(seq);
  		boardvo.setPw(pw);
  		
  		int n = service.del(boardvo);
  		
  		if( n == 0 ) {
  			mav.addObject("msg", "암호가 일치하지 않아 글 삭제가 불가합니다.");
  		}
  		else {
  			mav.addObject("msg", "글삭제 성공!!");
  		}
  		
  		mav.addObject("loc", request.getContextPath() + "/noticeBoardList.action");
  		mav.setViewName("msg");
  		
  		return mav;
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

