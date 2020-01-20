package com.bo.payment_report.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.bo.payment_report.model.ExReportVO;
import com.bo.payment_report.service.InterPayment_Report_Service;
import com.bo.common.MyUtil;
import com.bo.common.FileManager;


// === 컨트롤러 선언 ===
/* XML에서 빈을 만드는 대신에 클래스명 앞에 @Component 어노테이션을 적어주면 해당 클래스는 bean으로 자동 등록된다. 
그리고 bean의 이름(첫글자는 소문자)은 해당 클래스명이 된다. */
@Component
@Controller
public class Payment_Report_Controller { 
	
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private InterPayment_Report_Service service;
	 
	@Autowired
	private FileManager fileManager;
	
	// 기안서
	@RequestMapping(value="/draft.action")
	public String draft() { 
		return "payment/draft.tiles1";
	}

////////////////////////////////// 지출결재서 작성 /////////////////////////////////////////
	
	// 지출결의서
	@RequestMapping(value="/expenditure.action")
	public String expenditure(HttpServletRequest request) {
		return "payment/expend/expenditure.tiles1";
	}
	
	// 지출결의서 보고서
	@RequestMapping(value="/exReport.action", method= {RequestMethod.POST})
	public String goexReport(HttpServletRequest request) {
		
		String radioVal = request.getParameter("radioVal");
		
		request.setAttribute("radioVal", radioVal);
		return "payment/expend/exReport.tiles1";
	}
	
	// 출장 신청서
	@RequestMapping(value="/business_trip.action", method= {RequestMethod.POST})
	public String goBusinessTrip(HttpServletRequest request) {
		
		String radioVal = request.getParameter("radioVal");
		
		request.setAttribute("radioVal", radioVal);
		return "payment/expend/business_trip.tiles1";
	}
	
	// 출장 복명서
	@RequestMapping(value="/business_return.action", method= {RequestMethod.POST})
	public String goBusinessReturn(HttpServletRequest request) {
		
		String radioVal = request.getParameter("radioVal");
		
		request.setAttribute("radioVal", radioVal);
		
		return "payment/expend/business_return.tiles1";
	}
	
///////////////////////////////// 휴가결재서 작성 /////////////////////////////////////////////	
	
	// 휴가 신청서 작성
	@RequestMapping(value="leave.action")
	public String leave(HttpServletRequest request) {
		
		String radioVal = request.getParameter("radioVal");
		
		request.setAttribute("radioVal", radioVal);
		return "payment/leave_jsp/leave.tiles1";
	}
	
	// 연차 신청서 작성
	@RequestMapping(value="year.action", method= {RequestMethod.POST})
	public String year(HttpServletRequest request) {
		
		String radioVal = request.getParameter("radioVal");
		
		request.setAttribute("radioVal", radioVal);
		return "payment/leave_jsp/year.tiles1";
	}
	
	
	// 출산 휴가계
	@RequestMapping(value="baby.action", method= {RequestMethod.POST})
	public String baby(HttpServletRequest request) {
		
		String radioVal = request.getParameter("radioVal");
		
		request.setAttribute("radioVal", radioVal);
		return "payment/leave_jsp/baby.tiles1";
	}
	
	// 휴가 취소
	@RequestMapping(value="cancellation.action", method= {RequestMethod.POST})
	public String cancellation(HttpServletRequest request) {
		
		String radioVal = request.getParameter("radioVal");
		
		request.setAttribute("radioVal", radioVal);
		return "payment/leave_jsp/cancellation.tiles1";
	}
	
	// 조퇴계
	@RequestMapping(value="early_leave.action", method= {RequestMethod.POST})
	public String earlyLeave(HttpServletRequest request) {
		
		String radioVal = request.getParameter("radioVal");
		
		request.setAttribute("radioVal", radioVal);
		return "payment/leave_jsp/earlyLeave.tiles1";
	}
	
	// 병가계
	@RequestMapping(value="sick_leave.action", method= {RequestMethod.POST})
	public String sickLeave(HttpServletRequest request) {
		
		String radioVal = request.getParameter("radioVal");
		
		request.setAttribute("radioVal", radioVal);
		return "payment/leave_jsp/sickLeave.tiles1";
	}
	
	// 외출계
	@RequestMapping(value="out.action", method= {RequestMethod.POST})
	public String out(HttpServletRequest request) {
		
		String radioVal = request.getParameter("radioVal");
		
		request.setAttribute("radioVal", radioVal);
		return "payment/leave_jsp/out.tiles1";
	}
	
	
//////////////////////////////////////////////////////////////////////////////////	
	
/////////////////////////////// 휴직결재서 작성 //////////////////////////////////////
	
	// 휴직결재서 작성
	@RequestMapping(value="vacation.action") 
	public String vacation(HttpServletRequest request) {
		
		String radioVal = request.getParameter("radioVal");
		
		request.setAttribute("radioVal", radioVal);		
		return "payment/vacation_jsp/vacation.tiles1";
	}
	
	
	// 육아휴직 결재 신청서
	@RequestMapping(value = "babyVacation.action", method= {RequestMethod.POST})
	public String BabyVacation(HttpServletRequest request) {
		
		String radioVal = request.getParameter("radioVal");
		
		request.setAttribute("radioVal", radioVal);
		return "payment/vacation_jsp/babyVacation.tiles1";
	}
	
//////////////////////////////////////////////////////////////////////////////////
	
/////////////////////////////// 보고서 작성 //////////////////////////////////////
	
	// 보고서 작성
	@RequestMapping(value="report.action")
	public String report(HttpServletRequest request) {
		
		String title = request.getParameter("title");
		
		request.setAttribute("title", title);
		return "report/report_jsp/reportWrite.tiles1";
	}
//////////////////////////////////////////////////////////////////////////////////
	
/////////////////////////////// Modal ajax로 부서 넣어주기 //////////////////////////////////////	
	
	// Modal ajax
	@ResponseBody
	@RequestMapping(value="/addAprroval.action", produces = "text/plain;charset=UTF-8")
	public String addAprroval() {
		
		List<HashMap<String,String>> addAprrovalList = service.addAprrovalModal();
		
		JSONArray jsonArr = new JSONArray();
		for( HashMap<String,String> addAprrovalMap : addAprrovalList ) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("departmentname", addAprrovalMap.get("departmentname"));
			
			jsonArr.put(jsonObj);
		}
		String addAprroval = jsonArr.toString();
		return addAprroval;
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////	
	

	
/////////////////////////////// Modal ajax로 넣어준 부서에 대한 사원명단 select //////////////////////////////////////		
	
	@ResponseBody
	@RequestMapping(value="/addModalVal.action", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
	public String addModalSelect(HttpServletRequest request) {
		
		String departmentname = request.getParameter("modalDepartmentName");

		List<HashMap<String,String>> addModalSelectList = service.addModalSelect(departmentname);
	//	System.out.println(addModalSelectList);
		JSONArray jsonArr = new JSONArray();
		for( HashMap<String,String> modalmap : addModalSelectList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("DEPARTMENTNAME", modalmap.get("DEPARTMENTNAME"));
			jsonObj.put("NAME", modalmap.get("NAME"));
			jsonObj.put("POSITIONNAME", modalmap.get("POSITIONNAME"));
			jsonObj.put("EMPLOYEENO", modalmap.get("EMPLOYEENO"));
			
			jsonArr.put(jsonObj);
		}
		return jsonArr.toString();
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	

	
/////////////////////////////// 지출결재서 결재함으로 보내주기 //////////////////////////////////////	
	@RequestMapping(value="/addreport.action", method= {RequestMethod.POST})
//	public String addreport(ExReportVO exReportvo, MultipartHttpServletRequest mrequest) {
	public String addreport(HttpServletRequest request) {  
		
		int tdCount = Integer.parseInt(request.getParameter("tdCount"));
		
		for(int i=0; i<tdCount; i++) {
			String approver = request.getParameter("approverhidden"+i+"");
			System.out.println(approver);
		}
		
		/*MultipartFile attach = exReportvo.getAttach();
		
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
		 // == 2. 파일첨부를 위한 변수의 설정 및 값을 초기화한 후 파일 올리기 ==
		 	String newFileName = "";
		 	// WAS(톰캣)의 디스크에 저장될 파일명
		 	
		 	byte[] bytes = null;
		 	// 첨부파일을 WAS(톰캣)의 디스크에 저장할때 사용되는 용도
		 	
		 	long fileSize = 0;
		 	// 파일크기를 읽어오기 위한 용도 
		 	
		 	try {
				bytes = attach.getBytes();
				// getBytes() 메소드는 첨부된 파일을 바이트단위로 파일을 다 읽어오는 것이다. 
				// 예를 들어, 첨부한 파일이 "강아지.png" 이라면
				// 이파일을 WAS(톰캣) 디스크에 저장시키기 위해 byte[] 타입으로 변경해서 올린다.
				
				newFileName = fileManager.doFileUpload(bytes, attach.getOriginalFilename(), path);
				// 이제 파일올리기를 한다.
				// attach.getOriginalFilename() 은 첨부된 파일의 파일명(강아지.png)이다.getOriginalFilename() 이미 메소드가 만들어져 있는것이다.
				
				System.out.println(">>> 확인용 newFileName ==> " + newFileName); 
				// >>> 확인용 newFileName ==> 201907251244341722885843352000.png 
				
				// == 3. BoardVO boardvo 에 fileName 값과 orgFilename 값과  fileSize 값을 넣어주기 
				exReportvo.setFileName(newFileName);
				// WAS(톰캣)에 저장된 파일명(201907251244341722885843352000.png)
				
				exReportvo.setOrgFilename(attach.getOriginalFilename());
				// 게시판 페이지에서 첨부된 파일의 파일명(강아지.png)을 보여줄때 및  
				// 사용자가 파일을 다운로드 할때 사용되어지는 파일명
				
				fileSize = attach.getSize();
				exReportvo.setFileSize(String.valueOf(fileSize));
				// 게시판 페이지에서 첨부한 파일의 크기를 보여줄때 String 타입으로 변경해서 저장함.
				
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		 ========= !!첨부파일이 있는지 없는지 알아오기 끝!! =========  
				
				
			// *** 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어 코드) 작성하기 *** //
			exReportvo.setTitle(MyUtil.replaceParameter(exReportvo.getTitle()));
			exReportvo.setContent(MyUtil.replaceParameter(exReportvo.getContent()));
			exReportvo.setContent(exReportvo.getContent().replaceAll("\r\n", "<br/>"));
			
		//	int n = service.add(boardvo);
			
		//  === #143. 파일첨부가 있는 경우와 없는 경우에 따라서 Service단 호출하기 === 
		//      먼저 위의 	int n = service.add(boardvo); 부분을 주석처리하고서 아래처럼 한다.
		
			int n = 0;
			if(attach.isEmpty()) {
				// 첨부파일이 없는 경우이라면
				n = service.add(exReportvo); 
			}
			else {
				// 첨부파일이 있는 경우이라면
				n = service.add_withFile(exReportvo);
			}*/
			
			
			//	mav.addObject("n", n);
			//	mav.setViewName("board/addEnd.tiles1");
			//	return mav;

		//	mrequest.setAttribute("n", n);
			
			return "";
		}
	
////////////////////////////////////////////////////////////////////////////////////////////	
}













