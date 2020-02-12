package com.bo.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


import com.bo.common.MyUtil;
import com.bo.member.model.MemberVO;

import com.bo.board.model.DeptBoardVO;
import com.bo.board.model.CommentVO;
import com.bo.board.service.*;
import com.bo.common.FileManager;

//=== 컨트롤러 선언 ===
@Component
/* XML에서 빈을 만드는 대신에 클래스명 앞에 @Component 어노테이션을 적어주면 해당 클래스는 bean으로 자동 등록된다. 
그리고 bean의 이름(첫글자는 소문자)은 해당 클래스명이 된다. */
@Controller
public class DeptBoardController {

	/* 관리자 메뉴 관련 컨트롤러  2020/01/05 kyh*/
	
	// === 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private InterDeptBoardService deptservice;
	
	// === 파일업로드 및 다운로드를 해주는 FileManager 클래스 의존객체 주입하기(DI: Dependency Injection) ===  
	@Autowired
	private FileManager fileManager;

    
	/* ===== 부서별 게시판 컨트롤러 시작 ===== */
	////////////////////////////////////////////////////////////////
	
	//부서별 게시판 목록
	//페이징 처리를 한 검색어가 있는 전체 글목록 보여주기
	@RequestMapping(value="/deptBoardList.action", method = {RequestMethod.GET})
	public ModelAndView deptBoardList(HttpServletRequest request, ModelAndView mav) {
		
		List<DeptBoardVO> deptBoardList = null;
	
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
			totalCount = deptservice.getTotalCountWithNOsearch();
		}
		
		// 검색조건이 있을 경우의 총 게시물 건수(totalCount)
		else {
			totalCount = deptservice.getTotalCountWithSearch(paraMap);
								 
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
	
		deptBoardList = deptservice.boardListWithPaging(paraMap);
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
		
		String url = "deptBoardList.action";	
		
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
		// 			글조회수(readCount)증가 (DML문 update)는
		//          반드시 목록보기에 와서 해당 글제목을 클릭했을 경우에만 증가되고,
		//          웹브라우저에서 새로고침(F5)을 했을 경우에는 증가가 되지 않도록 해야 한다.
		//          이것을 하기 위해서는 session 을 사용하여 처리하면 된다.
		HttpSession session = request.getSession();
		session.setAttribute("readCountPermission", "yes"); 
		/*
		session 에  "readCountPermission" 키값으로 저장된 value값은 "yes" 이다.
		session 에  "readCountPermission" 키값에 해당하는 value값 "yes"를 얻으려면 
		반드시 웹브라우저에서 주소창에 "/deptBoardList.action" 이라고 입력해야만 얻어올 수 있다. 
		*/
		///////////////////////////////////////////////////////////////
		
		mav.addObject("deptBoardList", deptBoardList);
		mav.setViewName("board/deptBoardList.tiles1");
		
		return mav;
	}
					
	// 부서글사항 게시물 하나를 보여주는 페이지 요청 ===
	@RequestMapping(value="/deptDetailBoard.action", method = {RequestMethod.GET})
	public ModelAndView deptDetailBoard(HttpServletRequest request, ModelAndView mav) {
		
		// 조회하고자 하는 글번호 받아오기
		String seq = request.getParameter("seq"); // jsp의 form 을 통해 보내진 input 태그의 name - seq
		
		// 게시물에 달린 댓글 겟수 알아오기
		System.out.println("seq : "+ seq);

		int CommentCount = deptservice.getCommentCount(seq);
		
		HttpSession session = request.getSession();
		
		System.out.println(">>> 확인용 commentcount ==> " + CommentCount); 
		
		
		String gobackURL = request.getParameter("gobackURL");
		mav.addObject("gobackURL", gobackURL);
		//////////////////////////////////
		
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
/*	
		System.out.println("부서글 글하나 보기 댓글 아이디 loginuser.getId() => " + loginuser.getId());     // admin
		System.out.println("부서글 글하나 보기 댓글 이름 loginuser.getName() => " + loginuser.getName()); // 관리자
*/		
		
		mav.addObject("name", loginuser.getName());
		mav.addObject("CommentCount", CommentCount);
		mav.addObject("id", loginuser.getId());
		
		//////////////////////////////////
		// 로그인 유무확인
		
		String id = null;
		
		if( loginuser != null ) { // null 이 아니면 로그인을 했다.
			id = loginuser.getId();
			// 로그인 되어진 사용자의 id
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
				
		DeptBoardVO deptboardvo = null;
		
		// 위의 글목록 보기 - [ session.setAttribute("readCountPermission", "yes"); ] 해두었다.
		if( "yes".equals(session.getAttribute("readCountPermission")) ) {
			// 글목록보기를 클릭한 다음 특정글을 조회해온 경우이다.
			deptboardvo = deptservice.getView(seq, id); // userid 로 누구인지 알 수 있게 넣어준다.
			// ▲ 글조회수 증가와 함께 글 1개를 조회를 해주는 것
			
			session.removeAttribute("readCountPermission");
			// 중요함 !!! session 에 저장된 readCountPermission 을 삭제한다.
		}
		else { // 웹브라우저에서 새로고침(F5)을 누른 경우다. 
			deptboardvo = deptservice.getViewWithNoAddCount(seq);
			// ▲ 글조회수 증가는 없고 단순히 글 1개 조회만 해주는 것.
		}
					
		
		// ===  댓글쓰기가 있는 게시판인 경우 원글의 내용과 함께 원글에 딸린 댓글의 내용도 보여준다. === // 
		List<CommentVO> commentList = deptservice.getCommentList(seq);
		// 원게시물에 딸린 댓글들을 조회해오는 것
		
		System.out.println("1. commentList : " + commentList);
		System.out.println("2. deptboardvo : " + deptboardvo);
		
//		for(int i=0; i<deptboardvo.size(); i++) {
////			System.out.println("deptboardvo : " + deptboardvo.get(i).get("POSITIONNAME")); // service 에서 만들어준 키값으로 검색을 한다.
//		}
		
		mav.addObject("commentList", commentList);
		
		
		mav.addObject("deptboardvo", deptboardvo);
		mav.setViewName("board/deptDetailBoard.tiles1");
		
		return mav;
	}
	
	
	
	// === 부서글 글쓰기 홈페이지 요청
	@RequestMapping(value="/deptBoardWrite.action")
	public ModelAndView requireLogin_deptBoardWrite(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
	
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		System.out.println("부서글쓰기 loginuser.getId() => " + loginuser.getId());     // admin
		System.out.println("부서글쓰기 loginuser.getName() => " + loginuser.getName()); // 관리자
		
		mav.addObject("name", loginuser.getName());
		mav.addObject("id", loginuser.getId());
		
		
		mav.setViewName("/board/deptBoardWrite.tiles1");
		
		return mav;
	}
	
	//부서글 작성 완료
	@RequestMapping(value="/deptBoardWriteEnd.action")
	public String deptBoardWriteEnd(DeptBoardVO deptboardvo, MultipartHttpServletRequest mrequest, HttpServletRequest request) {
	/*	public ModelAndView addEnd(DeptBoardVO deptboardvo, ModelAndView mav, MultipartHttpServletRequest mrequest) {*/
		/*
		===== 사용자가 쓴 글에 파일이 첨부되어 있는 것인지 아니면 파일첨부가 안된것인지 구분을 지어주어야 한다. =====
		========= !!첨부파일이 있는지 없는지 알아오기 시작!! ========= 
		*/ 
		MultipartFile attach = deptboardvo.getAttach();	
		
		
		
		if(!attach.isEmpty()) {
			// attach 가 비어있지 않다면(즉, 첨부파일이 있는 경우라면) 
			
		/*
		   1. 사용자가 보낸 파일을 WAS(톰캣)의 특정 폴더에 저장해주어야 한다. 
		   >>> 파일이 업로드 되어질 특정 경로(폴더)지정해주기
		      우리는 WAS의 webapp/resources/files 라는 폴더로 지정해준다.	
		 */
		    // WAS의 webapp 의 절대경로를 알아와야 한다.
			HttpSession session = mrequest.getSession();
		 	String root = session.getServletContext().getRealPath("/");
		 	String path = root + "resources" + File.separator + "files";
		 	// File.separator 는 운영체제에서 사용하는 폴더와 파일의 구분자이다.
		/*
		 	// 로그인시 이름을 받아오는 메소드
		 	MemberVO mvo = (MemberVO)session.getAttribute("loginuser");
		 	String name = mvo.getName();
		 	
		 	deptboardvo.setName(name);
		 	
		 	System.out.println(">>> 확인용 name ==>" + name);
		 */	
		 // path 가 첨부파일을 저장할 WAS(톰캣)의 폴더가 된다.
		 //	System.out.println(">>> 확인용 path ==>" + path);
		
		 	 // == 2. 파일첨부를 위한 변수의 설정 및 값을 초기화 한 후 파일 올리기 == 
		 	String newFileName = "";
		 	// WAS(톰캣)의 디스크에 저장될 파일명
		 	
		 	byte[] bytes = null;
		 	// 첨부파일을 WAS(톰캣)의 디스크에 저장할때 사용되는 용도
		 	
		 	long fileSize = 0;
		 	// 파일크기를 읽어오기 위한 용도 
		 	
		 	try {
				bytes = attach.getBytes();
				// getBytes() 메소드는 첨부된 파일을 바이트단위로 파일을 다 읽어오는 것이다. 
		 	
				newFileName = fileManager.doFileUpload(bytes, attach.getOriginalFilename(), path);
				// 이제 파일올리기를 한다.
				
			//	System.out.println(">>> 확인용 newFileName ==> " + newFileName); 
				// >>> 확인용 newFileName ==> 201907251244341722885843352000.png 
				
				// DeptBoardVO deptboardvo 에 fileName 값과 orgFilename 값과  fileSize 값을 넣어주기 
				deptboardvo.setFileName(newFileName);
				
				deptboardvo.setOrgFilename(attach.getOriginalFilename());
				// 게시판 페이지에서 첨부된 파일의 파일명(강아지.png)을 보여줄때 및  
				// 사용자가 파일을 다운로드 할때 사용되어지는 파일명
				
				fileSize = attach.getSize();
				deptboardvo.setFileSize(String.valueOf(fileSize));
				// 게시판 페이지에서 첨부한 파일의 크기를 보여줄때 String 타입으로 변경해서 저장함.
				
			} catch (Exception e) {
				e.printStackTrace();
			}	
			
		}
	//	========= !!첨부파일이 있는지 없는지 알아오기 끝!! =========  
		 	
		// *** 크로스 사이트 스트립트 공격에 대응하는 안전한 코드(secure code)를 작성해주는 메소드 생성 *** //
		deptboardvo.setSubject( MyUtil.replaceParameter(deptboardvo.getSubject()) );
		deptboardvo.setContent( MyUtil.replaceParameter(deptboardvo.getContent()) );
		deptboardvo.setContent( deptboardvo.getContent().replaceAll("\r\n", "<br>") ) ;
		
		int n = 0;
		if(attach.isEmpty()) {
			// 첨부파일이 없는 경우이라면
			n = deptservice.add(deptboardvo); 
		}
		else {
			// 첨부파일이 있는 경우이라면
			n = deptservice.add_withFile(deptboardvo);
		}	
		
	//	mav.addObject("n", n);
		mrequest.setAttribute("n", n);
		
		return "board/deptBoardWriteEnd.tiles1";
	}
	
	
	// 부서글사항 게시물 수정 페이지요청 === //
	@RequestMapping(value="/deptBoardEdit.action", method = {RequestMethod.GET})
	public ModelAndView requireLogin_deptBoardWriteEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		// 글 수정 해야할 글번호 가져오기
		String seq = request.getParameter("seq");
		
		// 글 수정해야할 글 1개 내용 가져오기 ( 본인의 것이면 조회수를 올리지 않는다. )
		 DeptBoardVO deptboardvo = deptservice.getViewWithNoAddCount(seq);
		// ▲ 글조회수 증가는 없고 단순히 글 1개 조회만 해주는 것.
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		mav.addObject("deptboardvo", deptboardvo);
		mav.setViewName("board/deptBoardEdit.tiles1");
		// 수정 진행확인후 아래 주석의 코드로 대체
		
		/* 수정진행을 위한 주석처리 수정 확인후 로그인절차를 거처 진행한다
			if( !loginuser.getId().equals(deptboardvo.getFk_userid()) ) {
										
				String msg = "다른 사용자의 글은 수정이 불가합니다.";
				String loc = "javascript:history.back()";
				
				mav.addObject("msg", msg);
				mav.addObject("loc", loc);
				mav.setViewName("msg");
			}
			else { // 자신의 글을 수정한 경우
				
				// (edit.jsp View 단)HTML 에서 <br> 을 \n 으로 바꾸지 않으면 아래를 사용한다.
//				// *** 크로스 사이트 스트립트 공격에 대응하는 안전한 코드(secure code)를 작성해주는 메소드 생성 *** //
//				deptboardvo.setTitle( MyUtil.replaceParameter(deptboardvo.getTitle()) );
//				deptboardvo.setContent( MyUtil.replaceParameter(deptboardvo.getContent()) );
//				deptboardvo.setContent( deptboardvo.getContent().replaceAll("<br>", "\r\n") ) ; // <br> 을 엔터로 바꾼다.
							
				// 가져온 1개글을 글수정할 폼에 있는 View단으로 보내준다.
				mav.addObject("deptboardvo", deptboardvo);
				mav.setViewName("board/deptBoardEdit.tiles1");
			}*/
		
		return mav;
	}
	
	// 부서글사항 게시물 글수정 페이지 완료하기 === //
	@RequestMapping(value="/deptBoardEditEnd.action", method = {RequestMethod.POST})
	public ModelAndView requireLogin_deptBoardEditEnd(HttpServletRequest request, HttpServletResponse response, DeptBoardVO deptboardvo, ModelAndView mav) {
		
		// *** 크로스 사이트 스트립트 공격에 대응하는 안전한 코드(secure code)를 작성해주는 메소드 생성 *** //
		deptboardvo.setSubject( MyUtil.replaceParameter(deptboardvo.getSubject()) );
		deptboardvo.setContent( MyUtil.replaceParameter(deptboardvo.getContent()) );
		deptboardvo.setContent( deptboardvo.getContent().replaceAll("\r\n", "<br>") ) ;
		/* 기능 구현이 불안하여 주석처리 */
		// 글 수정을 하려면 작성자 아이디와 수정시도하는 사람아이디가 일치할때만 수정이 가능하도록 한다.
		int n = deptservice.edit(deptboardvo);
		
		if( n == 0 ) {
			mav.addObject("msg", "작성자가 일치하지 않아 글 수정이 불가합니다.");
		}
		else {
			mav.addObject("msg", "글수정 성공!!!");
		}
		
		// 글성공시 수정된 주소로 이동하기 위해
		mav.addObject("loc", request.getContextPath() + "/deptDetailBoard.action?seq=" + deptboardvo.getSeq());
		mav.setViewName("msg");
		
		// 로그인 유무확인 ( requireLogin : 로그인 해야만 한다고 알기위해 이름지은 것 )
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		return mav;
				
	}
	
	 // 부서글사항 글삭제 페이지 요청하기 삭제하고자 하는 글페이지만 불어온다 실제 삭제는 deleteEnd 에서 진행됨
 	@RequestMapping(value="/deptBoardDelete.action", method = {RequestMethod.GET}) 
 	public ModelAndView requireLogin_deptBoardDelete(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
 		
 		// 삭제해야할 글 번호를 받아온다.
 		String seq = request.getParameter("seq");
 		
 		// 삭제해야할  글1개 내용 가져와서 로그인한 사람이 쓴 글이라면 글 삭제가 가능하지만
 		// 다른사람이 쓴 글은 삭제가 불가하도록 해야 한다.
 		DeptBoardVO deptboardvo = deptservice.getViewWithNoAddCount(seq);
 		// ▲ 글조회수(readCound) 증가 없이 그냥 글1개만 가져오는 것
 		
 		HttpSession session = request.getSession();
 		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
 		// 글삭제 코드 아래 아이디 조회후 글삭제 코드는 추후 복구 해서 사용
 		mav.addObject("seq", seq);
		mav.setViewName("board/deptBoardDelete.tiles1");
 		
 	/*
 		 아이디 조회가 안되므로 주석처리한다
 		if( !loginuser.getId().equals(deptboardvo.getFk_userid()) ) { // 남이쓴글
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
 			mav.setViewName("board/deptBoardDelete.tiles1");
 		}
 	*/
 		
 		
		return mav;
 	}
 	

 	// 부서글사항 글삭제 페이지 완료하기 === // 실제 글삭제가 이루어진다
  	@RequestMapping(value="/deptBoardDeleteEnd.action", method = {RequestMethod.POST}) 
  	public ModelAndView requireLogin_deptBoardDeleteEnd(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
  		
  		// 삭제해야할 글번호 및 사용자가 입력한 글암호를 받아온다.
  		String seq = request.getParameter("seq");
  		/*String id = request.getParameter("id");*/
  		 		

		System.out.println("~~~확인용 seq : " + seq);

  		/*deptboardvo.setFk_userid(id);*/
  		
  		int n = deptservice.del(seq);
  		
  		if( n == 0 ) {
  			mav.addObject("msg", "작성자가 일치하지 않아 글 삭제가 불가합니다.");
  		}
  		else {
  			mav.addObject("msg", "글삭제 성공!!");
  		}
  		
  		mav.addObject("loc", request.getContextPath() + "/deptBoardList.action");
  		mav.setViewName("msg");
  		
  		return mav;
  	}
  	

  	 // === 댓글쓰기(Ajax로 처리) ===
  	 	@ResponseBody
  	 	@RequestMapping(value="/addComment.action", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")       
  	 	public String addComment(CommentVO commentvo) {
  	 		
  	 		String jsonStr = "";
  	 		
  	 		try {
  	 			int n = deptservice.addComment(commentvo);
  	 			
  	 			if(n==1) {
  	 				// 댓글쓰기(insert) 및 
  	 				// 원게시물(tblBoard 테이블)에 댓글의 갯수 증가(update 1씩 증가)가 성공했다라면 
  	 				
  	 				List<CommentVO> commentList = deptservice.getCommentList(commentvo.getParentSeq()); 
  	 				// 원게시물에 딸린 댓글들을 조회해오는 것 
  	 				
  	 				JSONArray jsonArr = new JSONArray();
  	 				
  	 				for(CommentVO cmtvo : commentList) {
  	 					JSONObject jsonObj = new JSONObject();
  	 					jsonObj.put("name", cmtvo.getName());
  	 					jsonObj.put("content", cmtvo.getContent());
  	 					jsonObj.put("regDate", cmtvo.getRegDate());
  	 					
  	 					jsonArr.put(jsonObj);
  	 				}
  	 				
  	 				jsonStr = jsonArr.toString();
  	 				
  	 			}// end of if---------------------
  	 			
  	 		} catch (Throwable e) {
  	 			e.printStackTrace();
  	 		}
  	 		
  	 		return jsonStr;
  	 	}
  	 	// 노트북 소스 병합
  	// === 댓글수정 ===
  	 	@ResponseBody
  	 	@RequestMapping(value="/updateComment.action", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")       
  	 	public ModelAndView updateComment(HttpServletRequest request, ModelAndView mav) {
  	 		
  	 		String commentSeq = request.getParameter("commentSeq");
  	 		String commentid = request.getParameter("commentid");
  	 		String editComment = request.getParameter("editComment");
  	 		String parentSeq = request.getParameter("parentSeq");
  	 		
  	 		HashMap<String, String> paraMap = new HashMap<String, String>();
  	 		
  	 		paraMap.put("commentSeq", commentSeq);
  	 		paraMap.put("commentid", commentid);
  	 		paraMap.put("editComment", editComment);
  	 		
  	 		System.out.println("댓글 수정 검증 commentSeq: "+ commentSeq + ", commentid: " + commentid +", editComment: " + editComment +", parentSeq: " + parentSeq);
  	 		
  	 
 			int n = deptservice.updateComment(paraMap);
 			
 			String msg = "";

 			if(n == 1) {
 				msg = "댓글 수정이 완료되었습니다.";
 			}
 			else {
 				msg = "댓글 수정에 실패했습니다.";
 			}

 			String loc = request.getContextPath() + "/deptDetailBoard.action?seq="+parentSeq;
 			
 			mav.addObject("msg", msg);
 			mav.addObject("loc", loc);
 			
 			mav.setViewName("msg");
 			
 			return mav;
  	 	}
  	 	
  	// === 댓글삭제(Ajax로 처리) ===
  	 	@ResponseBody
  	 	@RequestMapping(value="/deleteComment.action")       
  	 	public ModelAndView deleteComment(HttpServletRequest request, ModelAndView mav) {
  	 		
  	 		String commentSeq = request.getParameter("commentSeq");
  	 		String parentSeq = request.getParameter("parentSeq");
  	 		
  	 		int n = deptservice.deleteComment(commentSeq, parentSeq);
  	 		
  	 		String msg = "";

 			if(n == 1) {
 				msg = "댓글 삭제가 완료되었습니다.";
 			}
 			else {
 				msg = "댓글 삭제에 실패했습니다.";
 			}

 			String loc = request.getContextPath() + "/deptDetailBoard.action?seq="+parentSeq;
 			
 			mav.addObject("msg", msg);
 			mav.addObject("loc", loc);
 			
 			mav.setViewName("msg");
 			
 			return mav;

  	 	}
  	 	
  	 	/*
  	 	// 부서글 게시물 댓글수정 페이지 완료하기 === //

  	 	@ResponseBody
  	 	@RequestMapping(value="/editComment.action", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")       
  	 	public String editComment(CommentVO commentvo) {
  	 		
  	 		String jsonStr = "";
  	 		
  	 		try {
  	 			int n = deptservice.editComment(commentvo);
  	 			
  	 			if(n==1) {
  	 				// 댓글쓰기(update) 및 
  	 				// 원게시물(tblBoard 테이블)에 댓글의 갯수 감소(update 1씩 감소)가 성공했다라면 
  	 				
  	 				List<CommentVO> commentList = deptservice.getCommentList(commentvo.getParentSeq()); 
  	 				// 원게시물에 딸린 댓글들을 조회해오는 것 
  	 				
  	 				JSONArray jsonArr = new JSONArray();
  	 				
  	 				for(CommentVO cmtvo : commentList) {
  	 					JSONObject jsonObj = new JSONObject();
  	 					jsonObj.put("name", cmtvo.getName());
  	 					jsonObj.put("content", cmtvo.getContent());
  	 					jsonObj.put("regDate", cmtvo.getRegDate());
  	 					
  	 					jsonArr.put(jsonObj);
  	 				}
  	 				
  	 				jsonStr = jsonArr.toString();
  	 				
  	 			}// end of if---------------------
  	 			
  	 		} catch (Throwable e) {
  	 			e.printStackTrace();
  	 		}
  	 		
  	 		return jsonStr;
  	 	}
  	 			
  	 	*/
  	 // === 검색어 입력시 자동글 완성하기 3 === //
  	 	@ResponseBody
  	 	@RequestMapping(value="/DeptWordSearchShow.action", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
  	 	public String wordSearchShow(HttpServletRequest request) { 
  	 		
  	 		String searchType = request.getParameter("searchType"); 
  	 		String searchWord = request.getParameter("searchWord"); 
  	 		
  	 		HashMap<String,String> paraMap = new HashMap<String,String>(); 
  	 		paraMap.put("searchType", searchType);
  	 		paraMap.put("searchWord", searchWord);
  	 		
  	 		List<String> wordList = deptservice.wordSearchShow(paraMap); 
  	 		
  	 		JSONArray jsonArr = new JSONArray();
  	 		
  	 		if(wordList != null) {
  	 			for(String word : wordList) {
  	 				JSONObject jsonObj = new JSONObject();
  	 				jsonObj.put("word", word);
  	 				jsonArr.put(jsonObj);
  	 			}
  	 		}
  	 		
  	 		return jsonArr.toString(); 
  	 	}
  	 	
  	 	
  	 	// ===== 첨부파일 다운로드 받기 =====
  	 	@RequestMapping(value="/deptDownload.action", method={RequestMethod.GET}) 
  	 	public void requireLogin_deptdownload(HttpServletRequest request, HttpServletResponse response) {
  	 		
  	 		String seq = request.getParameter("seq"); 
  	 		// 첨부파일이 있는 글번호
  	 		
  	 		// 첨부파일이 있는 글번호에서 
  	 		// 201907250930481985323774614.png 처럼
  	 		// 이러한 fileName 값을 DB에서 가져와야 한다. 
  	 		// 또한 orgFileName 값도 DB에서 가져와야 한다.
  	 		
  	 		DeptBoardVO vo = deptservice.getViewWithNoAddCount(seq);
  	 		// 조회수 증가 없이 1개 글 가져오기
  	 		// 먼저 board.xml 에 가서 id가 getView 인것에서
  	 		// select 절에 fileName, orgFilename, fileSize 컬럼을
  	 		// 추가해주어야 한다.
  	 		
  	 		String fileName = vo.getFileName(); 
  	 		// 201907250930481985323774614.png 와 같은 것이다.
  	 		// 이것이 바로 WAS(톰캣) 디스크에 저장된 파일명이다.
  	 		
  	 		String orgFilename = vo.getOrgFilename(); 
  	 		// 강아지.png 처럼 다운받을 사용자에게 보여줄 파일명.
  	 		
  	 		
  	 		// 첨부파일이 저장되어 있는 
  	 		// WAS(톰캣)의 디스크 경로명을 알아와야만 다운로드를 해줄수 있다. 
  	 		// 이 경로는 우리가 파일첨부를 위해서
  	 		//    /addEnd.action 에서 설정해두었던 경로와 똑같아야 한다.
  	 		// WAS 의 webapp 의 절대경로를 알아와야 한다. 
  	 		HttpSession session = request.getSession();
  	 		
  	 		String root = session.getServletContext().getRealPath("/"); 
  	 		String path = root + "resources"+File.separator+"files";
  	 		// path 가 첨부파일들을 저장할 WAS(톰캣)의 폴더가 된다. 
  	 		
  	 		// **** 다운로드 하기 **** //
  	 		// 다운로드가 실패할 경우 메시지를 띄워주기 위해서
  	 		// boolean 타입 변수 flag 를 선언한다.
  	 		boolean flag = false;
  	 		
  	 		flag = fileManager.doFileDownload(fileName, orgFilename, path, response);
  	 		// 다운로드가 성공이면 true 를 반납해주고,
  	 		// 다운로드가 실패이면 false 를 반납해준다.
  	 		
  	 		if(!flag) {
  	 			// 다운로드가 실패할 경우 메시지를 띄워준다.
  	 			
  	 			response.setContentType("text/html; charset=UTF-8"); 
  	 			PrintWriter writer = null;
  	 			
  	 			try {
  	 				writer = response.getWriter();
  	 				// 웹브라우저상에 메시지를 쓰기 위한 객체생성.
  	 			} catch (IOException e) {
  	 				
  	 			}
  	 			
  	 			writer.println("<script type='text/javascript'>alert('파일 다운로드가 불가능합니다.!!')</script>");       
  	 			
  	 		}
  	 		 
  	 	} // end of void download(HttpServletRequest req, HttpServletResponse res)---------	
  	 	
  	  	
  	 	  // 스마트에디터. 드래그앤드롭을 사용한 다중사진 파일업로드 ====
  	 	@RequestMapping(value="/image/deptMultiplePhotoUpload.action", method={RequestMethod.GET})
  	   public void deptMultiplePhotoUpload(HttpServletRequest req, HttpServletResponse res) {
  		    
  		/*
  		   1. 사용자가 보낸 파일을 WAS(톰캣)의 특정 폴더에 저장해주어야 한다.
  		   >>>> 파일이 업로드 되어질 특정 경로(폴더)지정해주기
  		        우리는 WAS 의 webapp/resources/photo_upload 라는 폴더로 지정해준다.
  		 */
  			
  		// WAS 의 webapp 의 절대경로를 알아와야 한다. 
  		HttpSession session = req.getSession();
  		String root = session.getServletContext().getRealPath("/"); 
  		String path = root + "resources"+File.separator+"photo_upload";
  		// path 가 첨부파일들을 저장할 WAS(톰캣)의 폴더가 된다. 
  			
  		 System.out.println(">>>> 확인용 path ==> " + path); 
  		// >>>> 확인용 path ==> C:\springworkspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\resources\photo_upload  
  			
  		File dir = new File(path);
  		if(!dir.exists())
  		    dir.mkdirs();
  			
  		String strURL = "";
  			
  		try {
  			if(!"OPTIONS".equals(req.getMethod().toUpperCase())) {
  			    String filename = req.getHeader("file-name"); //파일명을 받는다 - 일반 원본파일명
  		    		
  		        // System.out.println(">>>> 확인용 filename ==> " + filename); 
  		        // >>>> 확인용 filename ==> berkelekle%ED%8A%B8%EB%9E%9C%EB%94%9405.jpg
  		    		
  		    	   InputStream is = req.getInputStream();
  		    	/*
  		          요청 헤더의 content-type이 application/json 이거나 multipart/form-data 형식일 때,
  		          혹은 이름 없이 값만 전달될 때 이 값은 요청 헤더가 아닌 바디를 통해 전달된다. 
  		          이러한 형태의 값을 'payload body'라고 하는데 요청 바디에 직접 쓰여진다 하여 'request body post data'라고도 한다.

  	               	  서블릿에서 payload body는 Request.getParameter()가 아니라 
  	            	  Request.getInputStream() 혹은 Request.getReader()를 통해 body를 직접 읽는 방식으로 가져온다. 	
  		    	*/
  		    	   String newFilename = fileManager.doFileUpload(is, filename, path);
  		    	
  			   int width = fileManager.getImageWidth(path+File.separator+newFilename);
  				
  			   if(width > 600)
  			      width = 600;
  					
  			// System.out.println(">>>> 확인용 width ==> " + width);
  			// >>>> 확인용 width ==> 600
  			// >>>> 확인용 width ==> 121
  		    	
  			   String CP = req.getContextPath(); // board
  				
  			   strURL += "&bNewLine=true&sFileName="; 
  	            	   strURL += newFilename;
  	            	   strURL += "&sWidth="+width;
  	            	   strURL += "&sFileURL="+CP+"/resources/photo_upload/"+newFilename;
  		    	}
  			
  		    	/// 웹브라우저상에 사진 이미지를 쓰기 ///
  			   PrintWriter out = res.getWriter();
  			   out.print(strURL);
  		} catch(Exception e){
  				e.printStackTrace();
  		}
  	   
  	   }
  	 	
}

