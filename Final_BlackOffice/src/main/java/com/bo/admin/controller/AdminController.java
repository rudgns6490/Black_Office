package com.bo.admin.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bo.admin.model.AddressbookVO;
import com.bo.admin.service.InterAdminService;
import com.bo.board.model.BoardVO;
import com.bo.board.model.DeptBoardVO;
import com.bo.common.SHA256;
import com.bo.member.model.MemberVO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.bo.common.ThumbnailManager;
import com.bo.common.FileManager;
import com.bo.common.MyUtil;

//=== 컨트롤러 선언 ===
@Component
/* XML에서 빈을 만드는 대신에 클래스명 앞에 @Component 어노테이션을 적어주면 해당 클래스는 bean으로 자동 등록된다. 
그리고 bean의 이름(첫글자는 소문자)은 해당 클래스명이 된다. */
@Controller
public class AdminController {
	
	// === 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private InterAdminService service;
	
	@Autowired
	private FileManager fileManager;
	
	@Autowired
	private ThumbnailManager thumbnailManager;
	
	/* 관리자 메뉴 관련 컨트롤러  2020/01/03 kkh*/
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////// //////////////////////////////////////////			[ 메인페이지 ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	// 메인페이지 2020/01/06 LBH 
	@RequestMapping(value="/main.action")
	public ModelAndView requireLoginCheck_maincontenttest(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		// 메인에 공지사항 게시판 데이터 넘기기 2020/02/05 KYH
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		List<BoardVO> mainNoticeBoardList = null;
		List<DeptBoardVO> mainDeptBoardList = null;
		
		mainNoticeBoardList = service.boardListWithPaging();
		// 페이징 처리한 글목록 가겨오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)
		mainDeptBoardList = service.deptBoardListWithPaging();
		
		HttpSession session = request.getSession();
	    MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
	      
	    String employeeno = String.valueOf(loginuser.getEmployeeno());
		
		// 보고서 데이터 메인에 보여주기
		List<HashMap<String,String>> map = service.textReportShow(employeeno);
		
		
		request.setAttribute("map", map);
		mav.addObject("mainNoticeBoardList", mainNoticeBoardList);
		mav.addObject("mainDeptBoardList", mainDeptBoardList);
		mav.setViewName("main.tiles1");
		
		return mav;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 회사소개 ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	// 조직도
	@RequestMapping(value="/organizationChart.action")
	public ModelAndView requireLoginCheck_organizationChart(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
	mav.setViewName("admin/organizationChart.tiles1");
	return mav;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 로그인 ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 로그인 2020/01/07 LBH
	@RequestMapping(value="/login.action")
	public ModelAndView login(ModelAndView mav) {
		mav.setViewName("login/loginform.tiles1");
		return mav;
	}
	
	// === 로그인 처리하기 ===
	@RequestMapping(value="/loginEnd.action", method = {RequestMethod.POST} )
	public ModelAndView loginEnd(HttpServletRequest request, ModelAndView mav) { 
		
	 	String id = request.getParameter("id");
	 	String passwd = request.getParameter("passwd");
	 	
	 	HashMap<String, String> paraMap = new HashMap<String, String>(); 
	 	paraMap.put("id", id);
		paraMap.put("passwd",    SHA256.encrypt(passwd));
		
		MemberVO loginuser = service.getLoginMember(paraMap);
		/////////////////////////////////////////////////////
		
		HttpSession session = request.getSession();
		
		if(loginuser == null) {
			String msg = "아이디 또는 암호가 틀립니다.";
			String loc = "javascript:history.back()";
			
			mav.addObject("msg", msg);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg");
			//  /Board/src/main/webapp/WEB-INF/views/msg.jsp 파일을 생성한다.
		}
		else {
	
			// 아무런 이상없이 로그인 하는 경우
			session.setAttribute("loginuser", loginuser);
			
			System.out.println("~~~~~~~~~~ "+loginuser.getThumbnailFileName());
			
			if(session.getAttribute("gobackURL") != null) {
				// 세션에 저장된 돌아갈 페이지의 주소(gobackURL)이 있다라면 ( pakage : com.spring.board.LoginCheck - session.setAttribute("gobackURL", url); // 세션에 url 정보를 저장시켜둔다. )
				
				String gobackURL = (String) session.getAttribute("gobackURL");
				mav.addObject("gobackURL", gobackURL); // request 영역에 저장시키는 것이다.
				
				session.removeAttribute("gobackURL"); // 세션은 없앴다.
			}
			
			mav.setViewName("login/loginEnd.tiles1");
			//  /Board/src/main/webapp/WEB-INF/views/tiles1/login/loginEnd.jsp 파일을 생성한다.	
		}
			
		return mav;
	}
	
	//로그 아웃
	@RequestMapping(value="/logout.action")
	public ModelAndView logout(HttpServletRequest request, ModelAndView mav) { 
		
		HttpSession session = request.getSession();
		session.invalidate();

		String msg = "로그아웃 되었습니다.";
		String loc = request.getContextPath() + "/login.action";
		
		mav.addObject("msg", msg);
		mav.addObject("loc", loc);
		
		
		mav.setViewName("msg");
		return mav;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 채팅 ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	// 채팅1 은 pakge : com.bo.chatting.controller.ChattingController 에 존재함
	// 채팅2 2020/02/01/토 LBH
	@RequestMapping(value="/internetchat.action")
	public String requireLoginCheck_internetchat(HttpServletRequest request, HttpServletResponse response) {
		return "chatting/internetchat.tiles1";
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 통계(차트) ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////

	
	// 지출통계 컨트롤러 보여주기 2020/01/29/수 LBH
		@RequestMapping(value="/admintotalexpenditure.action")
		public ModelAndView requireLoginCheck_admintotalexpenditure(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
			mav.setViewName("admin/admintotalexpenditure.tiles1");
			return mav;
		}
		
		// 지출통계 컨트롤러 2020/01/06 kkh => 재수정 2020/01/29/수 LBH ==> 페이징처리 2020/02/01/토 LBH
		@RequestMapping(value="/admintotalexpenditureshow.action")
		public ModelAndView requireLoginCheck_admintotalexpenditureshow(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
			
			// 날짜와 부서번호로 지출데이터 검색하기
			String expenditureDepartment = request.getParameter("expenditureDepartment");	// 부서번호
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			
			HashMap<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("expenditureDepartment", expenditureDepartment);
			paraMap.put("fromDate", fromDate);
			paraMap.put("toDate", toDate);
			
			System.out.println("fromDate: "+ fromDate + ", toDate: "+ toDate + ", expenditureDepartment: "+ expenditureDepartment);
			
			System.out.println("paraMap : " + paraMap);
			///////////////////////////////////////////////////////////////////////////
			// 페이징 처리를 통한 글목록 보여주기는 예를 들어 3페이지의 내용을 보고자 한다라면//
			//	/list.action?currentShowPageNo=3 와 같이 해주어야 한다.				 //
			String str_currentShowPageNo = request.getParameter("currentShowPageNo");
			
			// * 필요한 것 *
			int totalCount = 0;			// 총 게시물 건수
			int sizePerPage = 10;		// 한페이지당 보여줄 게시물 수
			int currentShowPageNo = 0;	// 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
			int totalPage = 0; 			// 총 페이지수(웹브라우저상에 보여줄 총 페이지 개수, 페이지바)
			
			int startRno = 0; 			// 시작	행 번호
			int endRno = 0;				// 끝   	행 번호
			
//			String searchType = request.getParameter("searchType");
//			String searchWord = request.getParameter("searchWord");
			
			if( fromDate == null || fromDate.trim().isEmpty() ) { // null 이던지 공백이라면
				fromDate = "";
			}
			
			if( toDate == null || toDate.trim().isEmpty() ) { // null 이던지 공백이라면
				toDate = "";
			}
			
			
			// 먼저 총 게시물 건수(totalCount)를 구해와야 한다.
			// 총 게시물 건수(totalCount)는 검색조건이 있을 때와 없을 때로 나뉘어진다.
			
			/*// 검색조건이 없을 경우의 총 게시물 건수(totalCount)
			if("".equals(fromDate)) {
				totalCount = service.getTotalCountExpenditure(); // 지출결재 테이블 총 개수 가져오기
			}
			// 검색조건이 있을 경우의 총 게시물 건수(totalCount)
			else {
				totalCount = service.getTotalCountExpenditureSearch(paraMap); // 지출결재 테이블 검색조건의 총 개수 가져오기
			}*/
			
			totalCount = service.getTotalCountExpenditureSearch(paraMap); // 지출결재 테이블 검색조건의 총 개수 가져오기
			
			totalPage = (int) Math.ceil((double)totalCount/sizePerPage);
			
			System.out.println("totalPage: " + totalPage + ", totalCount: " + totalCount);
			
			if( str_currentShowPageNo == null ) {
				// 게시판에 보여지는 초기화면
				
				currentShowPageNo = 1;
				// 즉, 초기화면은 /list.action?currentShowPageNo=1 로 한다는 말이다.
			}
			else {
				
				try {
					currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
					
					if( currentShowPageNo < 1 || currentShowPageNo > totalPage ) { // 0보다 작거나 토탈페이지보다 크면 1페이지
						currentShowPageNo = 1;
					}
					
				} catch (NumberFormatException e) {
					currentShowPageNo = 1; // number가 아니라면 
				}
				
			}
			
			// **** 가져올 게시물의 범위를 구한다.(공식임!!!) **** //
			/*
				currentShowPageNo		startRno	endRno
				------------------------------------------
					1page			==>		1		  10
					1page			==>		11		  20
					1page			==>		21		  30
					1page			==>		31		  40
					1page			==>		41		  50
					.....					..		  ..
					
			*/
			
			startRno = ( (currentShowPageNo - 1) * sizePerPage) + 1;
			endRno   = startRno + sizePerPage - 1; 
			
			paraMap.put("startRno", String.valueOf(startRno)); // int 를 String 타입으로 바꿔야 한다.
			paraMap.put("endRno"  , String.valueOf(endRno));
			
			List<HashMap<String, String>> expenditure = service.getadmintotalexpenditure(paraMap); // 지출통계에 데이터 가져오기 (select)

			// ▲ 페이징 처리한 글목록 가져오기(검색이 있던지 검색이 없든지 모두다 포함한 것)
			// 진행중
			
			System.out.println("검증 : " + expenditure);
			
			// View 단에 유지시키겠다.
			if( !"".equals(fromDate) ) { // 검색이 있다라면
				mav.addObject("paraMap", paraMap);
			}
			
			// === #125. 페이지바 만들기 === //
			String pageBar = "<ul>";

			int blockSize = 10;
			// blockSize 는 1개 블럭(토막)당 보여지는 페이지번호의 갯수 이다.
			/*
			    1 2 3 4 5 6 7 8 9 10           -- 1개 블럭 
			    11 12 13 14 15 16 17 18 19 20  -- 1개 블럭  
			*/
					
					int loop = 1;
					/*
					    loop 는 1부터 증가하여 1개 블럭을 이루는 페이지번호의 갯수(위의 설명상 지금은  10개(==blockSize))까지만 증가하는 용도이다. 
					*/
					
					int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
					// *** !! 공식이다. !! *** //
					System.out.println("pageNo : " + pageNo);
				/*
			        1  2  3  4  5  6  7  8  9  10  -- 첫번째 블럭의 페이지번호 시작값(pageNo)은 1 이다.
			        11 12 13 14 15 16 17 18 19 20  -- 두번째 블럭의 페이지번호 시작값(pageNo)은 11 이다.
			        21 22 23 24 25 26 27 28 29 30  -- 세번째 블럭의 페이지번호 시작값(pageNo)은 21 이다.
			        
			        currentShowPageNo         pageNo
			       ----------------------------------
			             1                      1 = ((1 - 1)/10) * 10 + 1
			             2                      1 = ((2 - 1)/10) * 10 + 1
			             3                      1 = ((3 - 1)/10) * 10 + 1
			             4                      1
			             5                      1
			             6                      1
			             7                      1 
			             8                      1
			             9                      1
			             10                     1 = ((10 - 1)/10) * 10 + 1
			            
			             11                    11 = ((11 - 1)/10) * 10 + 1
			             12                    11 = ((12 - 1)/10) * 10 + 1
			             13                    11 = ((13 - 1)/10) * 10 + 1
			             14                    11
			             15                    11
			             16                    11
			             17                    11
			             18                    11 
			             19                    11 
			             20                    11 = ((20 - 1)/10) * 10 + 1
			             
			             21                    21 = ((21 - 1)/10) * 10 + 1
			             22                    21 = ((22 - 1)/10) * 10 + 1
			             23                    21 = ((23 - 1)/10) * 10 + 1
			             ..                    ..
			             29                    21
			             30                    21 = ((30 - 1)/10) * 10 + 1
			    */
				String url = "admintotalexpenditureshow.action"; // 현재 보고자 하는 페이지	
					String lastStr = url.substring(url.length()-1);
					if( !"?".equals(lastStr) ) { // 물음표가 없다면  
						url += "?"; // 물음표를 붙이겠다.
					}
					// *** [이전] 만들기 *** //    
					if(pageNo != 1) {
						//pageBar += "&nbsp;<a href='"+url+"&currentShowPageNo="+(pageNo-1)+"&sizePerPage="+sizePerPage+"&expenditureDepartment="+expenditureDepartment+"&fromDate="+fromDate+"&toDate="+toDate+"'>[이전]</a>&nbsp;";
						pageBar += "&nbsp;<a href='"+url+"&currentShowPageNo="+(pageNo-1)+"'>[이전]</a>&nbsp;";
					}
					
					while( !(loop>blockSize || pageNo>totalPage) ) {
						
						if(pageNo == currentShowPageNo) {
							pageBar += "&nbsp;<span style='color: red; border: 1px solid gray; padding: 2px 6px;'>"+pageNo+"</span>&nbsp;";
						}
						else {
							//pageBar += "&nbsp;<a href='"+url+"&currentShowPageNo="+pageNo+"&sizePerPage="+sizePerPage+"&expenditureDepartment="+expenditureDepartment+"&fromDate="+fromDate+"&toDate="+toDate+"'>"+pageNo+"</a>&nbsp;";
							pageBar += "&nbsp;<a href='"+url+"&currentShowPageNo="+pageNo+"&sizePerPage="+sizePerPage+"'>"+pageNo+"</a>&nbsp;";
							       // ""+1+"&nbsp;"+2+"&nbsp;"+3+"&nbsp;"+......+10+"&nbsp;"
						}
						
						loop++;
						pageNo++;
					}// end of while---------------------------------
					
					// *** [다음] 만들기 *** //
					if( !(pageNo>totalPage) ) {
						//pageBar += "&nbsp;<a href='"+url+"&currentShowPageNo="+pageNo+"&sizePerPage="+sizePerPage+"&expenditureDepartment="+expenditureDepartment+"&fromDate="+fromDate+"&toDate="+toDate+"'>[다음]</a>&nbsp;";
						pageBar += "&nbsp;<a href='"+url+"&currentShowPageNo="+pageNo+"&sizePerPage="+sizePerPage+"'>[다음]</a>&nbsp;";
					}
					
					
			System.out.println("지출내역 페이징 처리 검증: " + pageBar);
			
			mav.addObject("pageBar", pageBar);
			
			String gobackURL = MyUtil.getCurrentURL(request);
			
			mav.addObject("gobackURL", gobackURL);
			
			mav.addObject("expenditure", expenditure);
		//	mav.addObject("departmentList", departmentList);
		//	mav.addObject("positionList", positionList);
			mav.setViewName("admin/admintotalexpenditure.tiles1");
			
			return mav;
//			return "admin/admintotalexpenditure.tiles1";
		}
		
		
		
		//부서통계 컨트롤러 2020/01/22 LBH
		@RequestMapping(value="/admintotaldepartment.action")
		public ModelAndView requireLoginCheck_admintotaldepartment(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
			List<HashMap<String, String>> department = service.getadmintotaldepartment(); // 부서통계 테이블 데이터 가져오기 (select)
			
			for(int i=0; i<department.size(); i++) {
				System.out.println("department : " + department.get(i).get("departmentname")); // service 에서 만들어준 키값으로 검색을 한다.
			}
			
			mav.addObject("department", department);
			mav.setViewName("admin/admintotaldepartment.tiles1");
			
			return mav;
		}
		
		//직위통계 컨트롤러 2020/01/22 LBH
		@RequestMapping(value="/admintotalposition.action")
		public ModelAndView requireLoginCheck_admintotalposition(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
			List<HashMap<String, String>> position = service.getadmintotalposition(); // 직위통계 테이블 데이터 가져오기 (select)
			
			for(int i=0; i<position.size(); i++) {
				System.out.println("position : " + position.get(i).get("POSITIONNAME")); // service 에서 만들어준 키값으로 검색을 한다.
			}
			
			mav.addObject("position", position);
			mav.setViewName("admin/admintotalposition.tiles1");
			
			return mav;
		}
		
		// ▼ 차트그리기
		// Ajax 부서별 인원수 및 인원퍼센티지 조회
		@ResponseBody // json 으로 넘어온 것이기에 사용
		@RequestMapping(value = "/departmentstatistics.action", produces = "text/plain;charset=UTF-8")
		public String departmentstatistics() {

			List<HashMap<String, String>> departmentstatistics = service.departmentstatistics();
			
			for(int i=0; i<departmentstatistics.size(); i++) {
				System.out.println("departmentstatistics : " + departmentstatistics.get(i).get("departmentname")); // service 에서 만들어준 키값으로 검색을 한다.
			}
			
			Gson gson = new Gson();
			JsonArray jsonArr = new JsonArray();

			for (HashMap<String, String> map : departmentstatistics) {
				JsonObject jsonObj = new JsonObject();
				jsonObj.addProperty("department_chart", map.get("department_chart")); // 왼쪽 json 키값 // 오른쪽 mapper 키값
				jsonObj.addProperty("departmentname", map.get("departmentname"));
				jsonObj.addProperty("CNT1", map.get("CNT1"));
				jsonObj.addProperty("PERCENTAGE1", map.get("PERCENTAGE1"));

				jsonArr.add(jsonObj);
			}

			return gson.toJson(jsonArr);

		}
		
		// Ajax 직위별 인원수 및 인원퍼센티지 조회
		@ResponseBody // json 으로 넘어온 것이기에 사용
		@RequestMapping(value = "/positionstatistics.action", produces = "text/plain;charset=UTF-8")
		public String positionstatistics() {

			List<HashMap<String, String>> positionstatistics = service.positionstatistics();
			
			for(int i=0; i<positionstatistics.size(); i++) {
				System.out.println("positionstatistics : " + positionstatistics.get(i).get("POSITIONNAME")); // service 에서 만들어준 키값으로 검색을 한다.
			}
			
			Gson gson = new Gson();
			JsonArray jsonArr = new JsonArray();

			for (HashMap<String, String> map : positionstatistics) {
				JsonObject jsonObj = new JsonObject();
				jsonObj.addProperty("position_chart", map.get("position_chart")); // 왼쪽 json 키값 // 오른쪽 mapper 키값
				jsonObj.addProperty("POSITIONNAME", map.get("POSITIONNAME"));
				jsonObj.addProperty("CNT2", map.get("CNT2"));
				jsonObj.addProperty("PERCENTAGE", map.get("PERCENTAGE"));

				jsonArr.add(jsonObj);
			}

			return gson.toJson(jsonArr);

		}
		// ▲ 차트그리기
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 입사처리/인사 ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//신규 입사 컨트롤러 2020/01/06 kkh
	@RequestMapping(value="/joinSawon.action")
	public String joinSawon() {
		return "admin/joinSawon.tiles1";
	}
	
	//신규 입사 등록 2020/01/15 LBH
	@RequestMapping(value="/register.action")
	public ModelAndView register(MultipartHttpServletRequest mrequest, ModelAndView mav) {
		
		//===== 사용자가 쓴 글에 파일이 첨부되어 있는 것인지 아니면 파일첨부가 안된것인지 구분을 지어주어야 한다. =====
		//========= !!첨부파일이 있는지 없는지 알아오기 시작!! ========= */ 
		MultipartFile attach = mrequest.getFile("attach");
		
		String id = mrequest.getParameter("id");
		String passwd = mrequest.getParameter("passwd");
		String name = mrequest.getParameter("name");
		String jubun = mrequest.getParameter("jubun1") + mrequest.getParameter("jubun2");
		String email = mrequest.getParameter("email");
		String emailpw = mrequest.getParameter("emailpw");
		String phone = mrequest.getParameter("phone");
		int fk_positionno = Integer.parseInt(mrequest.getParameter("fk_positionno"));
		int fk_departmentno = Integer.parseInt(mrequest.getParameter("fk_departmentno"));
		
		System.out.println("fk_positionno: " + fk_positionno + ", fk_departmentno: " + fk_departmentno);
		
		MemberVO mvo = new MemberVO();
		AddressbookVO addressbookvo = new AddressbookVO();
		
		mvo.setId(id);
		//mvo.setPasswd(passwd);
		mvo.setPasswd(SHA256.encrypt(passwd));
		mvo.setName(name);
		mvo.setJubun(jubun);
		mvo.setEmail(email);
		mvo.setEmailpw(emailpw);
		mvo.setPhone(phone);
		mvo.setFk_positionno(fk_positionno);
		mvo.setFk_departmentno(fk_departmentno);
		
		addressbookvo.setName(name);
		addressbookvo.setEmail(email);
		addressbookvo.setPhone(phone);
		
		//!!첨부파일이 있는지 없는지 알아오기 시작!!
		//첨부파일이 있는 경우라면
		/*
		 1. 사용자가 보낸 파일을 WAS(톰캣)의 특정 폴더에 저장해주어야 한다.
		 >> 파일이 업로드 되어질 특정 경로(폴더)지정해주기
		 우리는 WAS Webapp/resources//files 라는 폴더로 지정해준다.
		 
		 WAS의 webapp의 절대 경로를 알아와야 한다. */
		if(!attach.isEmpty()) {
			HttpSession session = mrequest.getSession();
		 	String root = session.getServletContext().getRealPath("/");
		 	String path = root + "resources" + File.separator + "files";
		 	// File.separator 는 운영체제에서 사용하는 폴더와 파일의 구분자이다.
		 	// 운영체제가 Windows 이라면 File.separator 은 "\" 이고,
		    // 운영체제가 UNIX, Linux 이라면 File.separator 은 "/" 이다.
		 	
		 	// path 가 첨부파일을 저장할 WAS(톰캣)의 폴더가 된다.
		 	System.out.println(">>> 확인용 path ==>" + path);
			// >>> 확인용 path ==>C:\springworkspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\resources\files
		 	
		 	/*
		 	 2. 파일첨부를 위한 변수의 설정 및 값을 초기화 후 파일 올리기
		 	 */
		 	String newFileName = "";
		 	// WAS(톰캣)의 디스크에 저장될 파일명
		 	
		 	byte[] bytes = null;
		 	// 첨부파일을 WAS(톰캣)의 디스크에 저장할때 사용되는 용도
		 	
		 	long fileSize = 0;
		 	// 파일크기를 읽어오기 위한 용도 
		 	
		 	String thumbnailFileName = "";
		 	// WAS 디스크에 저장될 thumbnail 파일명 
		 	
		 	try {
				bytes = attach.getBytes();
				// getBytes() 메소드는 첨부된 파일을 바이트단위로 파일을 다 읽어오는 것이다. 
				// 예를 들어, 첨부한 파일이 "강아지.png" 이라면
				// 이파일을 WAS(톰캣) 디스크에 저장시키기 위해 byte[] 타입으로 변경해서 올린다.
				
				newFileName = fileManager.doFileUpload(bytes, attach.getOriginalFilename(), path);
				// 이제 파일올리기를 한다.
				// attach.getOriginalFilename() 은 첨부된 파일의 파일명(강아지.png)이다.
				
				System.out.println(">>> 확인용 newFileName ==> " + newFileName); 
				// >>> 확인용 newFileName ==> 201907251244341722885843352000.jpg 
				
				// == 3. BoardVO boardvo 에 fileName 값과 orgFilename 값과  fileSize 값을 넣어주기 
				mvo.setFileName(newFileName);
				// WAS(톰캣)에 저장된 파일명(201907251244341722885843352000.png)
				System.out.println(">>> 확인용 fileName ==> " + mvo.getFileName()); 
				
				mvo.setOrgFilename(attach.getOriginalFilename());
				// 게시판 페이지에서 첨부된 파일의 파일명(강아지.png)을 보여줄때 및  
				// 사용자가 파일을 다운로드 할때 사용되어지는 파일
				System.out.println(">>> 확인용 orgFilename ==> " + mvo.getOrgFilename()); 
				
				fileSize = attach.getSize();
				mvo.setFileSize(String.valueOf(fileSize));
				// 게시판 페이지에서 첨부한 파일의 크기를 보여줄때 String 타입으로 변경해서 저장함.
				System.out.println(">>> 확인용 fileSize ==> " + mvo.getFileSize()); 
		
				// ============= >>>> 첨부파일에 대한 thumbnail 파일 생성하기 <<<< =====================  //
				thumbnailFileName = thumbnailManager.doCreateThumbnail(newFileName, path); 
				// ==============================================================================  //
				System.out.println(">>> 확인용 thumbnailFileName ==> " + thumbnailFileName); 
				
				mvo.setThumbnailFileName(thumbnailFileName);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//!!첨부파일이 있는지 없는지 알아오기 끝!!
		
		//int n = service.add(boardvo);
		//  === #143. 파일첨부가 있는 경우와 없는 경우에 따라서 Service단 호출하기 === 
		//      먼저 위의 	int n = service.add(boardvo); 부분을 주석처리하고서 아래처럼 한다.
		
		int n = 0;
		if(attach.isEmpty()) {
			// 첨부파일이 없는 경우이라면
			n = service.register(mvo, addressbookvo);
		}
		else {
			// 첨부파일이 있는 경우이라면
			n = service.attach_register(mvo, addressbookvo);
		}
		
		mrequest.setAttribute("n", n);
		
		mav.setViewName("admin/joinSawon.tiles1");
		return mav;
	}
	
	// 신규 입사 아이디 중복확인 2020/01/30 LBH 
	@RequestMapping(value="/idDuplicateCheck.action")
	public String idcheck(HttpServletRequest request) {
		
		String method = request.getMethod(); // "GET" or "POST"
		System.out.println("method : " + method);	
		if( "post".equalsIgnoreCase(method) ) { // ignoreCase = 대소문자 구문안함
			String userid = request.getParameter("userid"); // form 태그에서 보내준 name 값
			System.out.println("userid : " + userid);
			// userid 중복검사를 해주는 메소드 호출
			boolean isExistUserid = false;
			
			try {
				isExistUserid = service.idDuplicateCheck(userid);
				System.out.println("isExistUserid : " + isExistUserid);
				request.setAttribute("isExistUserid", isExistUserid);
				request.setAttribute("userid", userid);
			} catch (Exception e) {
				System.out.println("중복된 아이디가 없습니다. = 사용 가능합니다.");
				isExistUserid = false;
				System.out.println("isExistUserid : " + isExistUserid );
				request.setAttribute("isExistUserid", isExistUserid);
				request.setAttribute("userid", userid);
				//e.printStackTrace();
			}
			
		}
		
		request.setAttribute("method", method);
		
		return "idcheck";
	}
	
	// 내정보 수정 컨트롤러 2020/01/30/목 LBH
	@RequestMapping(value="/revisionMyInfo.action")
	public String requireLoginCheck_revisionMyInfo(HttpServletRequest request, HttpServletResponse response) {
		return "admin/revisionMyInfo.tiles1";
	}
	
	// 내정보 수정 2020/01/31/금 LBH
	@RequestMapping(value="/revision.action")
	public ModelAndView revision(MultipartHttpServletRequest mrequest, ModelAndView mav) {
		
		//===== 사용자가 쓴 글에 파일이 첨부되어 있는 것인지 아니면 파일첨부가 안된것인지 구분을 지어주어야 한다. =====
		//========= !!첨부파일이 있는지 없는지 알아오기 시작!! ========= */
		MultipartFile attach = mrequest.getFile("attach");		// 사진 첨부
		String email = mrequest.getParameter("email");			// 이메일
		String phone = mrequest.getParameter("phone");			// 전화번호
		String address = mrequest.getParameter("addr1");		// 주소
		String detailaddress = mrequest.getParameter("addr2");	// 상세주소
		
		System.out.println("데이터 테스트 : email = " + email + ", phone = " + phone + ", address = " + address + ", detailaddress = " + detailaddress);
		
		HttpSession session = mrequest.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		String id = String.valueOf(loginuser.getId());
		System.out.println("id : " + id);
		
		
		MemberVO mvo = new MemberVO();
		
		mvo.setEmail(email);
		mvo.setPhone(phone);
		mvo.setAddress(address);
		mvo.setDetailaddress(detailaddress);
		mvo.setId(id);
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//!!첨부파일이 있는지 없는지 알아오기 시작!!
		//첨부파일이 있는 경우라면
		/*
		 1. 사용자가 보낸 파일을 WAS(톰캣)의 특정 폴더에 저장해주어야 한다.
		 >> 파일이 업로드 되어질 특정 경로(폴더)지정해주기
		 우리는 WAS Webapp/resources//files 라는 폴더로 지정해준다.
		 
		 WAS의 webapp의 절대 경로를 알아와야 한다. */
		if(!attach.isEmpty()) {
			//HttpSession session = mrequest.getSession();
		 	String root = session.getServletContext().getRealPath("/");
		 	String path = root + "resources" + File.separator + "files";
		 	// File.separator 는 운영체제에서 사용하는 폴더와 파일의 구분자이다.
		 	// 운영체제가 Windows 이라면 File.separator 은 "\" 이고,
		    // 운영체제가 UNIX, Linux 이라면 File.separator 은 "/" 이다.
		 	
		 	// path 가 첨부파일을 저장할 WAS(톰캣)의 폴더가 된다.
		 	System.out.println(">>> 확인용 path ==>" + path);
			// >>> 확인용 path ==>C:\springworkspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\resources\files
		 	
		 	/*
		 	 2. 파일첨부를 위한 변수의 설정 및 값을 초기화 후 파일 올리기
		 	 */
		 	String newFileName = "";
		 	// WAS(톰캣)의 디스크에 저장될 파일명
		 	
		 	byte[] bytes = null;
		 	// 첨부파일을 WAS(톰캣)의 디스크에 저장할때 사용되는 용도
		 	
		 	long fileSize = 0;
		 	// 파일크기를 읽어오기 위한 용도 
		 	
		 	String thumbnailFileName = "";
		 	// WAS 디스크에 저장될 thumbnail 파일명 
		 	
		 	try {
				bytes = attach.getBytes();
				// getBytes() 메소드는 첨부된 파일을 바이트단위로 파일을 다 읽어오는 것이다. 
				// 예를 들어, 첨부한 파일이 "강아지.png" 이라면
				// 이파일을 WAS(톰캣) 디스크에 저장시키기 위해 byte[] 타입으로 변경해서 올린다.
				
				newFileName = fileManager.doFileUpload(bytes, attach.getOriginalFilename(), path);
				// 이제 파일올리기를 한다.
				// attach.getOriginalFilename() 은 첨부된 파일의 파일명(강아지.png)이다.
				
				System.out.println(">>> 확인용 newFileName ==> " + newFileName); 
				// >>> 확인용 newFileName ==> 201907251244341722885843352000.jpg 
				
				// == 3. BoardVO boardvo 에 fileName 값과 orgFilename 값과  fileSize 값을 넣어주기 
				mvo.setFileName(newFileName);
				// WAS(톰캣)에 저장된 파일명(201907251244341722885843352000.png)
				System.out.println(">>> 확인용 fileName ==> " + mvo.getFileName()); 
				
				mvo.setOrgFilename(attach.getOriginalFilename());
				// 게시판 페이지에서 첨부된 파일의 파일명(강아지.png)을 보여줄때 및  
				// 사용자가 파일을 다운로드 할때 사용되어지는 파일
				System.out.println(">>> 확인용 orgFilename ==> " + mvo.getOrgFilename()); 
				
				fileSize = attach.getSize();
				mvo.setFileSize(String.valueOf(fileSize));
				// 게시판 페이지에서 첨부한 파일의 크기를 보여줄때 String 타입으로 변경해서 저장함.
				System.out.println(">>> 확인용 fileSize ==> " + mvo.getFileSize()); 
		
				// ============= >>>> 첨부파일에 대한 thumbnail 파일 생성하기 <<<< =====================  //
				thumbnailFileName = thumbnailManager.doCreateThumbnail(newFileName, path); 
				// ==============================================================================  //
				System.out.println(">>> 확인용 thumbnailFileName ==> " + thumbnailFileName); 
				
				mvo.setThumbnailFileName(thumbnailFileName);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//!!첨부파일이 있는지 없는지 알아오기 끝!!
		
		//int n = service.add(boardvo);
		//  === #143. 파일첨부가 있는 경우와 없는 경우에 따라서 Service단 호출하기 === 
		//      먼저 위의 	int n = service.add(boardvo); 부분을 주석처리하고서 아래처럼 한다.
		
		int n = 0;
		if(attach.isEmpty()) {
			// 첨부파일이 없는 경우이라면
			n = service.revision(mvo);
		}
		else {
			// 첨부파일이 있는 경우이라면
			n = service.attach_revision(mvo);
		}
		
		mrequest.setAttribute("n", n);
		
		String msg = "내정보가 수정되었습니다.";
		String loc = mrequest.getContextPath() + "/main.action";
		
		mav.addObject("msg", msg);
		mav.addObject("loc", loc);
		
		mav.setViewName("msg");
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////				
//			mav.setViewName("admin/revisionMyInfo.tiles1");
		return mav;
	}
	
	//인사 이동 컨트롤러 2020/01/06 kkh
	@RequestMapping(value="/personnelAnnouncement.action")
	public ModelAndView personnelAnnouncement(HttpServletRequest request, ModelAndView mav) {
		
		List<HashMap<String, String>> mapList = null;
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		
		if( searchWord == null || searchWord.trim().isEmpty() ) { // null 이던지 공백이라면
			searchWord = "";
		}
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		mapList = service.selectSawon(paraMap);
		
		for(int i=0; i<mapList.size(); i++) {
			System.out.println(mapList.get(i).get("name"));
		}
		
		mav.addObject("mapList", mapList);
		mav.setViewName("admin/personnelAnnouncement.tiles1");
		
		return mav;
	}
	
	//인사 이동 내역 컨트롤러 2020/01/06 kkh
	@RequestMapping(value="/personnelAnnouncementList.action")
	public String personnelAnnouncementList() {
		return "admin/personnelAnnouncementList.tiles1";
	}
	
	//퇴사 내역 조회
	@RequestMapping(value="/retirementList.action")
	public ModelAndView retirementList(HttpServletRequest request, ModelAndView mav) {
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String tabid = request.getParameter("tabid");
		
		if( searchWord == null || searchWord.trim().isEmpty() ) { // null 이던지 공백이라면
			searchWord = "";
		}
		
		System.out.println("1 searchType: "+searchType + ", searchWord: "+searchWord +", tabid: "+tabid );
		
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		List<HashMap<String, String>> retirementList = service.retirementList(paraMap);
		
		for(int i=0; i<retirementList.size(); i++) {
			System.out.println(retirementList.get(i).get("name"));
		}
		
		mav.addObject("tabid", tabid);
		mav.addObject("retirementList", retirementList);
		mav.setViewName("admin/personnelAnnouncementList.tiles1");
		
		return mav;
	}
	
	//부서 이동 내역 조회
	@RequestMapping(value="/movedepartmentList.action")
	public ModelAndView movedepartmentList(HttpServletRequest request, ModelAndView mav) {
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String tabid = request.getParameter("tabid");
		
		if( searchWord == null || searchWord.trim().isEmpty() ) { // null 이던지 공백이라면
			searchWord = "";
		}
		
		System.out.println("2 searchType: "+searchType + ", searchWord: "+searchWord +", tabid: "+ tabid );
		
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		List<HashMap<String, String>> movedepartmentList = service.movedepartmentList(paraMap);
		
		for(int i=0; i<movedepartmentList.size(); i++) {
			System.out.println(movedepartmentList.get(i).get("name"));
		}
		
		mav.addObject("tabid", tabid);
		mav.addObject("movedepartmentList", movedepartmentList);
		mav.setViewName("admin/personnelAnnouncementList.tiles1");
		
		return mav;
	}
	
	//진급 내역 조회
	@RequestMapping(value="/movePositionList.action")
	public ModelAndView movePositionList(HttpServletRequest request, ModelAndView mav) {
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String tabid = request.getParameter("tabid");
		
		mav.addObject("tabid", tabid);
		
		if( searchWord == null || searchWord.trim().isEmpty() ) { // null 이던지 공백이라면
			searchWord = "";
		}
		
		System.out.println("3 searchType: "+searchType + ", searchWord: "+searchWord +", tabid: "+tabid );
		
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		List<HashMap<String, String>> movePositionList = service.movePositionList(paraMap);
		
		for(int i=0; i<movePositionList.size(); i++) {
			System.out.println(movePositionList.get(i).get("name"));
		}
		
		mav.addObject("movePositionList", movePositionList);
		mav.setViewName("admin/personnelAnnouncementList.tiles1");
		
		return mav;
	}
	
	//휴직 내역 조회
	@RequestMapping(value="/leaveofAbsenceList.action")
	public ModelAndView leaveofAbsenceList(HttpServletRequest request, ModelAndView mav) {
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String tabid = request.getParameter("tabid");
		mav.addObject("tabid", tabid);
		
		if( searchWord == null || searchWord.trim().isEmpty() ) { // null 이던지 공백이라면
			searchWord = "";
		}
		
		System.out.println("3 searchType: "+searchType + ", searchWord: "+searchWord +", tabid: "+tabid );
		
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		List<HashMap<String, String>> leaveofAbsenceList = service.leaveofAbsenceList(paraMap);
		
		for(int i=0; i<leaveofAbsenceList.size(); i++) {
			System.out.println(leaveofAbsenceList.get(i).get("name"));
		}
		
		mav.addObject("leaveofAbsenceList", leaveofAbsenceList);
		mav.setViewName("admin/personnelAnnouncementList.tiles1");
		
		return mav;
	}
	
	//복직 내역 조회
	@RequestMapping(value="/reappointList.action")
	public ModelAndView reappointList(HttpServletRequest request, ModelAndView mav) {
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String tabid = request.getParameter("tabid");
		mav.addObject("tabid", tabid);
		
		if( searchWord == null || searchWord.trim().isEmpty() ) { // null 이던지 공백이라면
			searchWord = "";
		}
		
		System.out.println("3 searchType: "+searchType + ", searchWord: "+searchWord +", tabid: "+tabid );
		
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		List<HashMap<String, String>> reappointList = service.reappointList(paraMap);
		
		for(int i=0; i<reappointList.size(); i++) {
			System.out.println(reappointList.get(i).get("name"));
		}
		
		mav.addObject("reappointList", reappointList);
		mav.setViewName("admin/personnelAnnouncementList.tiles1");
		
		return mav;
	}
	
	//퇴사
	@RequestMapping(value="/retirement.action")
	public ModelAndView retirement(HttpServletRequest request, ModelAndView mav) {
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		
		String employeeno = request.getParameter("employeeno");
		String name = request.getParameter("name");
		String departmentname = request.getParameter("departmentname");
		String positionname = request.getParameter("positionname");
		
		paraMap.put("employeeno", employeeno);
		paraMap.put("name", name);
		paraMap.put("departmentname", departmentname);
		paraMap.put("positionname", positionname);
		
		System.out.println("employeeno: " + employeeno);
		System.out.println("name: " + name);
		System.out.println("departmentname: " + departmentname);
		System.out.println("positionname: " + positionname);
		
		// 트랜잭션처리해야 함
		int n = service.tranRetirement(paraMap);
		
		String msg = "";

		if(n == 1) {
			msg = "퇴사처리 되었습니다.";
		}
		else {
			msg = "퇴사처리 되지 않았습니다..";
		}
		
		String loc = request.getContextPath() + "/personnelAnnouncement.action";
		
		mav.addObject("msg", msg);
		mav.addObject("loc", loc);
		
		mav.setViewName("msg");
		
		return mav;
	}
	
	//부서 변경
	@RequestMapping(value="/moveDepartment.action")
	public ModelAndView moveDepartment(HttpServletRequest request, ModelAndView mav) {
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		
		String employeeno = request.getParameter("employeeno");
		String name = request.getParameter("name");
		String departmentname = request.getParameter("departmentname");
		String movedepartmentno = request.getParameter("movedepartmentno");
		
		paraMap.put("employeeno", employeeno);
		paraMap.put("name", name);
		paraMap.put("departmentname", departmentname);
		paraMap.put("movedepartmentno", movedepartmentno);
		
		System.out.println("employeeno: " + employeeno);
		System.out.println("name: " + name);
		System.out.println("departmentname: " + departmentname);
		System.out.println("movedepartmentno: " + movedepartmentno);
		
		
		int n = service.moveDepartment(paraMap);
		
		String msg = "";

		if(n == 1) {
			msg = "부서가 변경되었습니다.";
		}
		else {
			msg = "부서 변경에 실패했습니다.";
		}
		
		String loc = request.getContextPath() + "/personnelAnnouncement.action";
		
		mav.addObject("msg", msg);
		mav.addObject("loc", loc);
		
		mav.setViewName("msg");
		
		return mav;
	}
	
	//진급
	@RequestMapping(value="/moveposition.action")
	public ModelAndView moveposition(HttpServletRequest request, ModelAndView mav) {
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		
		String employeeno = request.getParameter("employeeno");
		String name = request.getParameter("name");
		String departmentname = request.getParameter("departmentname");
		String positionname = request.getParameter("positionname");
		String movepositionno = request.getParameter("movepositionno");
		
		paraMap.put("employeeno", employeeno);
		paraMap.put("name", name);
		paraMap.put("departmentname", departmentname);
		paraMap.put("positionname", positionname);
		paraMap.put("movepositionno", movepositionno);
		
		System.out.println("employeeno: " + employeeno);
		System.out.println("name: " + name);
		System.out.println("departmentname: " + departmentname);
		System.out.println("positionname: " + positionname);
		System.out.println("movepositionno: " + movepositionno);
		
		
		int n = service.moveposition(paraMap);
		
		String msg = "";

		if(n == 1) {
			msg = "직위가 변경되었습니다.";
		}
		else {
			msg = "직위 변경에 실패했습니다.";
		}
		
		String loc = request.getContextPath() + "/personnelAnnouncement.action";
		
		mav.addObject("msg", msg);
		mav.addObject("loc", loc);
		
		mav.setViewName("msg");
		
		return mav;
	}
	
	//휴직
	@RequestMapping(value="/leaveofabsence.action")
	public ModelAndView leaveofabsence(HttpServletRequest request, ModelAndView mav) {
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		
		String employeeno = request.getParameter("employeeno");
		String name = request.getParameter("name");
		String departmentname = request.getParameter("departmentname");
		String positionname = request.getParameter("positionname");
		String leaveofabsence = request.getParameter("leaveofabsence");
		
		paraMap.put("employeeno", employeeno);
		paraMap.put("name", name);
		paraMap.put("departmentname", departmentname);
		paraMap.put("positionname", positionname);
		paraMap.put("leaveofabsence", leaveofabsence);
		
		System.out.println("employeeno: " + employeeno);
		System.out.println("name: " + name);
		System.out.println("departmentname: " + departmentname);
		System.out.println("positionname: " + positionname);
		System.out.println("leaveofabsence: " + leaveofabsence);
		
		
		int n = service.leaveofabsence(paraMap);
		
		String msg = "";

		if(n == 1) {
			msg = "휴직 처리가 완료되었습니다.";
		}
		else {
			msg = "휴직 처리에 실패했습니다.";
		}
		
		String loc = request.getContextPath() + "/personnelAnnouncement.action";
		
		mav.addObject("msg", msg);
		mav.addObject("loc", loc);
		
		mav.setViewName("msg");
		
		return mav;
	}
	
	//복직
	@RequestMapping(value="/reappoint.action")
	public ModelAndView reappoint(HttpServletRequest request, ModelAndView mav) {
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		
		String employeeno = request.getParameter("employeeno");
		String name = request.getParameter("name");
		String departmentname = request.getParameter("departmentname");
		String positionname = request.getParameter("positionname");
		
		paraMap.put("employeeno", employeeno);
		paraMap.put("name", name);
		paraMap.put("departmentname", departmentname);
		paraMap.put("positionname", positionname);
		
		System.out.println("employeeno: " + employeeno);
		System.out.println("name: " + name);
		System.out.println("departmentname: " + departmentname);
		System.out.println("positionname: " + positionname);
		
		int n = service.reappoint(paraMap);
		
		String msg = "";

		if(n == 1) {
			msg = "복직 처리가 완료되었습니다.";
		}
		else {
			msg = "복직 처리에 실패했습니다.";
		}
		
		String loc = request.getContextPath() + "/personnelAnnouncement.action";
		
		mav.addObject("msg", msg);
		mav.addObject("loc", loc);
		
		mav.setViewName("msg");
		
		return mav;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 주소록 ( 개인 / 공용 ) ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//개인 주소록 엑셀파일로 다운받기
	@RequestMapping(value="/downloadAddressBook.action")
	public String downloadAddressBook(HttpServletRequest request, ModelAndView mav, Model model) {
		
		List<AddressbookVO> abvoList = null;
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		String employeeno = String.valueOf(loginuser.getEmployeeno());
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		
		if( searchWord == null || searchWord.trim().isEmpty() ) { // null 이던지 공백이라면
			searchWord = "";
		}
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		paraMap.put("employeeno", employeeno);
		
		abvoList = service.abvoList(paraMap);
		// ▲ 페이징 처리한 글목록 가져오기(검색이 있던지 검색이 없든지 모두다 포함한 것)
		
		SXSSFWorkbook workbook = new SXSSFWorkbook();

		// 시트생성
		SXSSFSheet sheet = workbook.createSheet("업체주소록");

		// 시트 열 너비 설정
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 2000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 3000);
		
		// 행의 위치를 나타내는 변수
		int rowLocation = 0;
		
		////////////////////////////////////////////////////////////////////////////////////////
		// CellStyle 정렬하기(Alignment)
		// CellStyle 객체를 생성하여 Alignment 세팅하는 메소드를 호출해서 인자값을 넣어준다.
		// 아래는 HorizontalAlignment(가로)와 VerticalAlignment(세로)를 모두 가운데 정렬 시켰다.
		CellStyle mergeRowStyle = workbook.createCellStyle();
		mergeRowStyle.setAlignment(HorizontalAlignment.CENTER);
		mergeRowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		// import org.apache.poi.ss.usermodel.VerticalAlignment 으로 해야함.
		
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		// CellStyle 배경색(ForegroundColor)만들기
		// setFillForegroundColor 메소드에 IndexedColors Enum인자를 사용한다.
		// setFillPattern은 해당 색을 어떤 패턴으로 입힐지를 정한다.
		mergeRowStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex()); // IndexedColors.DARK_BLUE.getIndex()
				// 는 색상(남색)의 인덱스값을 리턴시켜준다.
		mergeRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex()); // IndexedColors.LIGHT_YELLOW.getIndex()
				// 는 연한노랑의 인덱스값을 리턴시켜준다.
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		// Cell 폰트(Font) 설정하기
		// 폰트 적용을 위해 POI 라이브러리의 Font 객체를 생성해준다.
		// 해당 객체의 세터를 사용해 폰트를 설정해준다. 대표적으로 글씨체, 크기, 색상, 굵기만 설정한다.
		// 이후 CellStyle의 setFont 메소드를 사용해 인자로 폰트를 넣어준다.
		Font mergeRowFont = workbook.createFont(); // import org.apache.poi.ss.usermodel.Font; 으로 한다.
		mergeRowFont.setFontName("나눔고딕");
		mergeRowFont.setFontHeight((short) 500);
		mergeRowFont.setColor(IndexedColors.WHITE.getIndex());
		mergeRowFont.setBold(true);
		
		mergeRowStyle.setFont(mergeRowFont);
		
		// CellStyle 테두리 Border
		// 테두리는 각 셀마다 상하좌우 모두 설정해준다.
		// setBorderTop, Bottom, Left, Right 메소드와 인자로 POI라이브러리의 BorderStyle 인자를 넣어서
		// 적용한다. 
		headerStyle.setBorderTop(BorderStyle.THICK);
		headerStyle.setBorderBottom(BorderStyle.THICK);
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setBorderRight(BorderStyle.THIN);
		
		// Cell Merge 셀 병합시키기
		/*
		* 셀병합은 시트의 addMergeRegion 메소드에 CellRangeAddress 객체를 인자로 하여 병합시킨다.
		* CellRangeAddress 생성자의 인자로(시작 행, 끝 행, 시작 열, 끝 열) 순서대로 넣어서 병합시킬 범위를 정한다. 배열처럼
		* 시작은 0부터이다.
		*/
		// 병합할 행 만들기
		Row mergeRow = sheet.createRow(rowLocation); // 엑셀에서 행의 시작은 0 부터 시작한다.
		
		// 병합할 행에 우리회사 사원정보로 셀을 만들어 셀에 스타일을 주기
		for (int i = 0; i < 5; i++) {
			Cell cell = mergeRow.createCell(i);
			cell.setCellStyle(mergeRowStyle);
			cell.setCellValue("업체주소록");
		}
		
		// 셀 병합하기
		sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 0, 4)); // 시작 행, 끝 행, 시작 열, 끝 열

		// CellStyle 천단위 쉼표, 금액
		CellStyle moneyStyle = workbook.createCellStyle();
		moneyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
		////////////////////////////////////////////////////////////////////////////////////////

		// 헤더 행 생성
		Row headerRow = sheet.createRow(++rowLocation); // 엑셀에서 행의 시작은 0부터 시작한다. ++ -> 한칸띄운다. (전위연산자)

		// 해당 행의 첫번째 열 셀 생성
		Cell headerCell = headerRow.createCell(0); // 엑셀에서 열의 시작은 0 부터 시작한다.
		headerCell.setCellValue("이름");
		headerCell.setCellStyle(headerStyle);

		// 해당 행의 두번째 열 셀 생성
		headerCell = headerRow.createCell(1);
		headerCell.setCellValue("이메일");
		headerCell.setCellStyle(headerStyle);

		// 해당 행의 세번째 열 셀 생성
		headerCell = headerRow.createCell(2);
		headerCell.setCellValue("전화번호");
		headerCell.setCellStyle(headerStyle);

		// 해당 행의 네번째 열 셀 생성
		headerCell = headerRow.createCell(3);
		headerCell.setCellValue("회사이름");
		headerCell.setCellStyle(headerStyle);

		// 해당 행의 다섯번째 열 셀 생성
		headerCell = headerRow.createCell(4);
		headerCell.setCellValue("그룹이름");
		headerCell.setCellStyle(headerStyle);

		// HR사원정보 내용에 해당하는 행 및 셀 생성하기
		Row bodyRow = null;
		Cell bodyCell = null;

		for (int i = 0; i < abvoList.size(); i++) {
			AddressbookVO abvo = abvoList.get(i);

			// 행 생성
			bodyRow = sheet.createRow(i + (rowLocation + 1));

			// 데이터 이름
			bodyCell = bodyRow.createCell(0);
			bodyCell.setCellValue(abvo.getName());

			// 데이터 이메일
			bodyCell = bodyRow.createCell(1);
			bodyCell.setCellValue(abvo.getEmail());

			// 데이터 전화번호
			bodyCell = bodyRow.createCell(2);
			bodyCell.setCellValue(abvo.getPhone());

			// 데이터 회사명
			bodyCell = bodyRow.createCell(3);
			bodyCell.setCellValue(abvo.getCompanyname());

			// 데이터 그룹명
			bodyCell = bodyRow.createCell(4);
			bodyCell.setCellValue(abvo.getGroupname());

		} // end of for----------------------------------
		
		
		model.addAttribute("locale", Locale.KOREA); // 쓰고있는 지역 // Excel = 통화기호 // 모든것이 한국으로 규칙을 한다.
		model.addAttribute("workbook", workbook);
		model.addAttribute("workbookName", "개인주소록");
		
		return "excelDownloadView";
	}
	
	//deleteaddressbookList
	@RequestMapping(value="/deleteaddressbookList.action")
	public ModelAndView deleteaddressbookList(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		String name = request.getParameter("name");
		
		//?name="김경훈,김용호,이병희"
		String[] nameArr = name.split(",");
		List<String> nameList = new ArrayList();
		
		for(int i=0; i<nameArr.length; i++) {
			System.out.println(nameArr[i]);
			nameList.add(nameArr[i]);
		}
		
		int n = service.deleteaddressbookList(nameList);

		String msg = "";

		if(n == 1) {
			msg = "주소록 삭제 처리가 완료되었습니다.";
		}
		else {
			msg = "주소록 삭제 처리에 실패했습니다.";
		}
		
		String loc = request.getContextPath() + "/addressBook.action";
		
		mav.addObject("msg", msg);
		mav.addObject("loc", loc);
		
		mav.setViewName("msg");
		
		return mav;
	}
	
	// 개인주소록 목록 2020/01/06 LBH
	@RequestMapping(value="/addressBook.action")
	public ModelAndView addressBook(HttpServletRequest request, ModelAndView mav) {
		
		List<AddressbookVO> abvoList = null;
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		String employeeno = String.valueOf(loginuser.getEmployeeno());
		
		System.out.println("사원번호 확인용: " + employeeno);
		
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		// * 필요한 것 *
		int totalCount = 0;			// 총 게시물 건수
		int sizePerPage = 10;		// 한페이지당 보여줄 게시물 수
		int currentShowPageNo = 0;	// 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
		int totalPage = 0; 			// 총 페이지수(웹브라우저상에 보여줄 총 페이지 개수, 페이지바)
		
		int startRno = 0; 			// 시작	행 번호
		int endRno = 0;				// 끝   	행 번호
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		
		if( searchWord == null || searchWord.trim().isEmpty() ) { // null 이던지 공백이라면
			searchWord = "";
		}
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		paraMap.put("employeeno", employeeno);
		
		// 먼저 총 게시물 건수(totalCount)를 구해와야 한다.
		// 총 게시물 건수(totalCount)는 검색조건이 있을 때와 없을 때로 나뉘어진다.
		
		
		
		// 검색조건이 없을 경우의 총 게시물 건수(totalCount)
		if("".equals(searchWord)) {
			totalCount = service.getTotalCountWithNoSearch2(employeeno);
		}
		
		// 검색조건이 있을 경우의 총 게시물 건수(totalCount)
		else {
			totalCount = service.getTotalCountWithSearch2(paraMap);
		}
		
		totalPage = (int) Math.ceil((double)totalCount/sizePerPage);
		
		if( str_currentShowPageNo == null ) {
			// 게시판에 보여지는 초기화면
			
			currentShowPageNo = 1;
			// 즉, 초기화면은 /list.action?currentShowPageNo=1 로 한다는 말이다.
		}
		else {
			
			try {
				currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
				
				if( currentShowPageNo < 1 || currentShowPageNo > totalPage ) { // 0보다 작거나 토탈페이지보다 크면 1페이지
					currentShowPageNo = 1;
				}
				
			} catch (NumberFormatException e) {
				currentShowPageNo = 1; // number가 아니라면 
			}
			
		}
		
		startRno = ( (currentShowPageNo - 1) * sizePerPage) + 1;
		endRno   = startRno + sizePerPage - 1; 
		
		paraMap.put("startRno", String.valueOf(startRno)); // int 를 String 타입으로 바꿔야 한다.
		paraMap.put("endRno"  , String.valueOf(endRno));
		
		abvoList = service.abvoListWithPaging2(paraMap);
		// ▲ 페이징 처리한 글목록 가져오기(검색이 있던지 검색이 없든지 모두다 포함한 것)
		
		if( !"".equals(searchWord) ) { // 검색어가 있다라면
			// View 단에 유지시키겠다.
			mav.addObject("paraMap", paraMap);
		}
		
		// === #125. 페이지바 만들기 === //
		String pageBar = "<ul>";

		int blockSize = 10;
		// blockSize 는 1개 블럭(토막)당 보여지는 페이지번호의 갯수 이다.
		/*
		    1 2 3 4 5 6 7 8 9 10           -- 1개 블럭 
		    11 12 13 14 15 16 17 18 19 20  -- 1개 블럭  
		*/
				
				int loop = 1;
				/*
				    loop 는 1부터 증가하여 1개 블럭을 이루는 페이지번호의 갯수(위의 설명상 지금은  10개(==blockSize))까지만 증가하는 용도이다. 
				*/
				
				int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
				// *** !! 공식이다. !! *** //
				
			/*
		        1  2  3  4  5  6  7  8  9  10  -- 첫번째 블럭의 페이지번호 시작값(pageNo)은 1 이다.
		        11 12 13 14 15 16 17 18 19 20  -- 두번째 블럭의 페이지번호 시작값(pageNo)은 11 이다.
		        21 22 23 24 25 26 27 28 29 30  -- 세번째 블럭의 페이지번호 시작값(pageNo)은 21 이다.
		        
		        currentShowPageNo         pageNo
		       ----------------------------------
		             1                      1 = ((1 - 1)/10) * 10 + 1
		             2                      1 = ((2 - 1)/10) * 10 + 1
		             3                      1 = ((3 - 1)/10) * 10 + 1
		             4                      1
		             5                      1
		             6                      1
		             7                      1 
		             8                      1
		             9                      1
		             10                     1 = ((10 - 1)/10) * 10 + 1
		            
		             11                    11 = ((11 - 1)/10) * 10 + 1
		             12                    11 = ((12 - 1)/10) * 10 + 1
		             13                    11 = ((13 - 1)/10) * 10 + 1
		             14                    11
		             15                    11
		             16                    11
		             17                    11
		             18                    11 
		             19                    11 
		             20                    11 = ((20 - 1)/10) * 10 + 1
		             
		             21                    21 = ((21 - 1)/10) * 10 + 1
		             22                    21 = ((22 - 1)/10) * 10 + 1
		             23                    21 = ((23 - 1)/10) * 10 + 1
		             ..                    ..
		             29                    21
		             30                    21 = ((30 - 1)/10) * 10 + 1
		    */
			String url = "addressBook.action"; // 현재 보고자 하는 페이지	
				String lastStr = url.substring(url.length()-1);
				if( !"?".equals(lastStr) ) { // 물음표가 없다면  
					url += "?"; // 물음표를 붙이겠다.
				}
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
		
		System.out.println("개인주소록 페이징처리 검증: " + pageBar);
		mav.addObject("pageBar", pageBar);
		
		/////////////////////////////////////////
		String gobackURL = MyUtil.getCurrentURL(request);
		// 페이징 처리되어진 후 특정글제목을 클릭하여 상세내용을 본 이후
		// 사용자가 목록보기 버튼을 클릭했을 때 돌아갈 페이지를 알려주기 위해
		// 현재 페이지 주소를 뷰단으로 넘겨준다.
		
		mav.addObject("gobackURL", gobackURL);
		mav.addObject("abvoList", abvoList);
		mav.setViewName("addressBook.tiles1");
		
		
		return mav;
		//return "addressBook.tiles1";
	}
	
	// 개인주소록 추가등록 2020/01/16 LBH
	@RequestMapping(value="/registerPersonal.action")
	public ModelAndView registerPersonal(HttpServletRequest request, ModelAndView mav) {
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String companyname = request.getParameter("companyname");
		String groupname = request.getParameter("groupname");
		int fk_employeeno = loginuser.getEmployeeno();
		
		AddressbookVO abvo = new AddressbookVO();
		abvo.setName(name);
		abvo.setEmail(email);
		abvo.setPhone(phone);
		abvo.setCompanyname(companyname);
		abvo.setGroupname(groupname);
		abvo.setFk_employeeno(fk_employeeno);
		
		int n = service.registerPersonal(abvo);
		String msg = "";

		if(n == 1) {
			msg = "주소록에 추가 되었습니다.";
		}
		else {
			msg = "주소록에 추가 되지 않았습니다..";
		}
		
		String loc = request.getContextPath() + "/addressBook.action";
		
		mav.addObject("msg", msg);
		mav.addObject("loc", loc);
		
		mav.setViewName("msg");
		
		return mav;
	}

	
	// 검색어가 있는 공용 주소록 페이징 처리 2020/01/07 LBH
	@RequestMapping(value="/addressOpenBookList.action", method = {RequestMethod.GET})
	public ModelAndView addressOpenBook(HttpServletRequest request, ModelAndView mav) {
		
		List<AddressbookVO> abvoList = null;
		
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		// * 필요한 것 *
		int totalCount = 0;			// 총 게시물 건수
		int sizePerPage = 10;		// 한페이지당 보여줄 게시물 수
		int currentShowPageNo = 0;	// 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
		int totalPage = 0; 			// 총 페이지수(웹브라우저상에 보여줄 총 페이지 개수, 페이지바)
		
		int startRno = 0; 			// 시작	행 번호
		int endRno = 0;				// 끝   	행 번호
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		
		if( searchWord == null || searchWord.trim().isEmpty() ) { // null 이던지 공백이라면
			searchWord = "";
		}
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		// 먼저 총 게시물 건수(totalCount)를 구해와야 한다.
		// 총 게시물 건수(totalCount)는 검색조건이 있을 때와 없을 때로 나뉘어진다.
		
		// 검색조건이 없을 경우의 총 게시물 건수(totalCount)
		if("".equals(searchWord)) {
			totalCount = service.getTotalCountWithNoSearch();
		}
		
		// 검색조건이 있을 경우의 총 게시물 건수(totalCount)
		else {
			totalCount = service.getTotalCountWithSearch(paraMap);
		}
		
		totalPage = (int) Math.ceil((double)totalCount/sizePerPage);
		
		if( str_currentShowPageNo == null ) {
			// 게시판에 보여지는 초기화면
			
			currentShowPageNo = 1;
			// 즉, 초기화면은 /list.action?currentShowPageNo=1 로 한다는 말이다.
		}
		else {
			
			try {
				currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
				
				if( currentShowPageNo < 1 || currentShowPageNo > totalPage ) { // 0보다 작거나 토탈페이지보다 크면 1페이지
					currentShowPageNo = 1;
				}
				
			} catch (NumberFormatException e) {
				currentShowPageNo = 1; // number가 아니라면 
			}
			
		}
		
		// **** 가져올 게시물의 범위를 구한다.(공식임!!!) **** //
		/*
			currentShowPageNo		startRno	endRno
			------------------------------------------
				1page			==>		1		  10
				1page			==>		11		  20
				1page			==>		21		  30
				1page			==>		31		  40
				1page			==>		41		  50
				.....					..		  ..
				
		*/
		
		startRno = ( (currentShowPageNo - 1) * sizePerPage) + 1;
		endRno   = startRno + sizePerPage - 1; 
		
		paraMap.put("startRno", String.valueOf(startRno)); // int 를 String 타입으로 바꿔야 한다.
		paraMap.put("endRno"  , String.valueOf(endRno));
		
		abvoList = service.abvoListWithPaging(paraMap);
		// ▲ 페이징 처리한 글목록 가져오기(검색이 있던지 검색이 없든지 모두다 포함한 것)
		
		if( !"".equals(searchWord) ) { // 검색어가 있다라면
			// View 단에 유지시키겠다.
			mav.addObject("paraMap", paraMap);
		}
		
		// === #125. 페이지바 만들기 === //
		String pageBar = "<ul>";

		int blockSize = 10;
		// blockSize 는 1개 블럭(토막)당 보여지는 페이지번호의 갯수 이다.
		/*
		    1 2 3 4 5 6 7 8 9 10           -- 1개 블럭 
		    11 12 13 14 15 16 17 18 19 20  -- 1개 블럭  
		*/
				
				int loop = 1;
				/*
				    loop 는 1부터 증가하여 1개 블럭을 이루는 페이지번호의 갯수(위의 설명상 지금은  10개(==blockSize))까지만 증가하는 용도이다. 
				*/
				
				int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
				// *** !! 공식이다. !! *** //
				
			/*
		        1  2  3  4  5  6  7  8  9  10  -- 첫번째 블럭의 페이지번호 시작값(pageNo)은 1 이다.
		        11 12 13 14 15 16 17 18 19 20  -- 두번째 블럭의 페이지번호 시작값(pageNo)은 11 이다.
		        21 22 23 24 25 26 27 28 29 30  -- 세번째 블럭의 페이지번호 시작값(pageNo)은 21 이다.
		        
		        currentShowPageNo         pageNo
		       ----------------------------------
		             1                      1 = ((1 - 1)/10) * 10 + 1
		             2                      1 = ((2 - 1)/10) * 10 + 1
		             3                      1 = ((3 - 1)/10) * 10 + 1
		             4                      1
		             5                      1
		             6                      1
		             7                      1 
		             8                      1
		             9                      1
		             10                     1 = ((10 - 1)/10) * 10 + 1
		            
		             11                    11 = ((11 - 1)/10) * 10 + 1
		             12                    11 = ((12 - 1)/10) * 10 + 1
		             13                    11 = ((13 - 1)/10) * 10 + 1
		             14                    11
		             15                    11
		             16                    11
		             17                    11
		             18                    11 
		             19                    11 
		             20                    11 = ((20 - 1)/10) * 10 + 1
		             
		             21                    21 = ((21 - 1)/10) * 10 + 1
		             22                    21 = ((22 - 1)/10) * 10 + 1
		             23                    21 = ((23 - 1)/10) * 10 + 1
		             ..                    ..
		             29                    21
		             30                    21 = ((30 - 1)/10) * 10 + 1
		    */
			String url = "addressOpenBookList.action"; // 현재 보고자 하는 페이지	
				String lastStr = url.substring(url.length()-1);
				if( !"?".equals(lastStr) ) { // 물음표가 없다면  
					url += "?"; // 물음표를 붙이겠다.
				}
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
		
		mav.addObject("pageBar", pageBar);
		
		/////////////////////////////////////////
		String gobackURL = MyUtil.getCurrentURL(request);
		// 페이징 처리되어진 후 특정글제목을 클릭하여 상세내용을 본 이후
		// 사용자가 목록보기 버튼을 클릭했을 때 돌아갈 페이지를 알려주기 위해
		// 현재 페이지 주소를 뷰단으로 넘겨준다.

		mav.addObject("gobackURL", gobackURL);
		mav.addObject("abvoList", abvoList);
		mav.setViewName("addressOpenBook.tiles1");
		
		return mav;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
}
