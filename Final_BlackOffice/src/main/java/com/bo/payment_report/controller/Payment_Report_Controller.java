package com.bo.payment_report.controller;

import java.io.File;
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

import com.bo.payment_report.model.ExReportVO;
import com.bo.payment_report.model.LeaveReportVO;
import com.bo.payment_report.service.InterPayment_Report_Service;
import com.bo.common.MyUtil;
import com.bo.member.model.MemberVO;
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
	public String requireLoginCheck_goexReport(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		String userName = loginuser.getName();
		String employeeno = String.valueOf(loginuser.getEmployeeno());
		
		String radioVal = request.getParameter("radioVal");
		
		// 부서명 가지고 오기
		String deptName = service.departmentName(employeeno);
		
		request.setAttribute("userName", userName);
		request.setAttribute("deptName", deptName);
		request.setAttribute("employeeno", employeeno);
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
	public String requireLoginCheck_leave(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		String userName = loginuser.getName();
		String employeeno = String.valueOf(loginuser.getEmployeeno());
		
		String radioVal = request.getParameter("radioVal");
		
		// 부서명 가지고 오기
		String deptName = service.departmentName(employeeno);
		
		request.setAttribute("userName", userName);
		request.setAttribute("deptName", deptName);
		request.setAttribute("employeeno", employeeno);
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
		
		/*String title = request.getParameter("title");
		
		request.setAttribute("title", title);*/
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		String userName = loginuser.getName();
		String employeeno = String.valueOf(loginuser.getEmployeeno());
		
		
		// 부서명 가지고 오기
		String deptName = service.departmentName(userName);
		
		request.setAttribute("userName", userName);
		request.setAttribute("deptName", deptName);
		request.setAttribute("employeeno", employeeno);
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
	public String addreport(MultipartHttpServletRequest mrequest) {
		
		MultipartFile attach = mrequest.getFile("attach");
		
		int tdCount = Integer.parseInt(mrequest.getParameter("tdCount"));
		int textnumber = 0;
		// 문서번호 사용할 시퀀스 채번해오기
		textnumber = service.textNumber();

		for(int i=0; i<tdCount; i++) {
			 String approver = "";
			 String approverName ="";
			
			String writeday = mrequest.getParameter("writeday");
			String employeename = mrequest.getParameter("employeename");
			String fk_employeeno = mrequest.getParameter("fk_employeeno");
			String departmentname = mrequest.getParameter("departmentname");
			String expendituremode = mrequest.getParameter("expendituremode");
			String expenditureday = mrequest.getParameter("expenditureday");
			String title = mrequest.getParameter("title");
			String content = mrequest.getParameter("content");
			String payment_money = mrequest.getParameter("payment_money");
			
			approver = mrequest.getParameter("approverhidden"+i+"");
			approverName = mrequest.getParameter("approver"+i+"");


			ExReportVO exReportvo = new ExReportVO();
			exReportvo.setWriteday(writeday);
			exReportvo.setFk_employeeno(fk_employeeno);
			exReportvo.setDepartmentname(departmentname);
			exReportvo.setExpendituremode(expendituremode);
			exReportvo.setExpenditureday(expenditureday);
			exReportvo.setTitle(title);
			exReportvo.setContent(content);
			exReportvo.setApprover(approver);
			exReportvo.setEmployeename(employeename);
			exReportvo.setTextnumber(String.valueOf(textnumber)); 
			exReportvo.setPayment_money(payment_money);
			exReportvo.setApprover_name(approverName);
			
						
			/*HashMap<String,Object> paraMap = new HashMap<String,Object>();
			paraMap.put("writeday", writeday);
			paraMap.put("employeename", employeename);
			paraMap.put("fk_employeeno", fk_employeeno);
			paraMap.put("departmentname", departmentname);
			paraMap.put("expendituremode", expendituremode);
			paraMap.put("expenditureday", expenditureday);
			paraMap.put("title", title);
			paraMap.put("content", content);
			paraMap.put("approver", approver);
			paraMap.put("attach", attach1);*/			
			
			
			if(!attach.isEmpty()) {
				HttpSession session = mrequest.getSession();
			 	String root = session.getServletContext().getRealPath("/");
			 	String path = root + "resources" + File.separator + "files";

			 	String newFileName = "";
			 	
			 	byte[] bytes = null;
			 	
			 	long fileSize = 0;
			 	
			 	try {
					bytes = attach.getBytes();
					
					newFileName = fileManager.doFileUpload(bytes, attach.getOriginalFilename(), path);
					
					exReportvo.setFileName(newFileName);
					
					exReportvo.setOrgFilename(attach.getOriginalFilename());
					
					fileSize = attach.getSize();
					exReportvo.setFileSize(String.valueOf(fileSize));
					
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
			
			// *** 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어 코드) 작성하기 *** //
			exReportvo.setTitle(MyUtil.replaceParameter(exReportvo.getTitle()));
			exReportvo.setContent(MyUtil.replaceParameter(exReportvo.getContent()));
			exReportvo.setContent(exReportvo.getContent().replaceAll("\r\n", "<br/>")); 
		
		// ========= !!첨부파일이 있는지 없는지 알아오기 끝!! =========  
			
			
			int n = 0;
			if(attach.isEmpty()) {
				// 첨부파일이 없는 경우이라면
				n = service.add(exReportvo);
				mrequest.setAttribute("msg", "정상적으로 결재가 제출되었습니다.");
				
			}
			else {
				// 첨부파일이 있는 경우이라면
				n = service.add_withFile(exReportvo);
				mrequest.setAttribute("msg", "정상적으로 결재가 제출되었습니다.");
			}	
			
			if(n == 1) {
				// 지출결의서 제출시 결재자 테이블 insert 
				int addApprover = service.addApprover(exReportvo);
			}
		}
		
		mrequest.setAttribute("loc", mrequest.getContextPath()+"/incomplete_payment_archive.action");
		return "msg";
	}
	
////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
/////////////////////////////// 휴가 결재함으로 보내주기 //////////////////////////////////////	
	@RequestMapping(value="/addLeaveReport.action", method= {RequestMethod.POST})
		public String addLeaveReport(MultipartHttpServletRequest mrequest, HttpServletResponse response) {
		
		MultipartFile attach = mrequest.getFile("attach");
		
		LeaveReportVO leaveReportvo = new LeaveReportVO();
		
		int tdCount = Integer.parseInt(mrequest.getParameter("tdCount"));
		int textnumber = 0;
		// 문서번호 사용할 시퀀스 채번해오기
		textnumber = service.textNumber();
		for(int i=0; i<tdCount; i++) {
			String approver = "";
			String approverName ="";
			
			String writeday = mrequest.getParameter("writeday");
			String employeename = mrequest.getParameter("employeename");
			String fk_employeeno = mrequest.getParameter("fk_employeeno");
			String departmentname = mrequest.getParameter("departmentname");
			String rank = mrequest.getParameter("rank");
			String startday = mrequest.getParameter("startday");
			String endday = mrequest.getParameter("endday");
			String title = mrequest.getParameter("title");
			String reason = mrequest.getParameter("reason");
			String emergencycontactnetwork = mrequest.getParameter("emergencycontactnetwork");
			String sharedepartmentno = mrequest.getParameter("sharedepartmentno");
			
			
			approver = mrequest.getParameter("approverhidden"+i+"");
			approverName = mrequest.getParameter("approver"+i+"");
			
			
			leaveReportvo.setWriteday(writeday);
			leaveReportvo.setFk_employeeno(fk_employeeno);
			leaveReportvo.setDepartmentname(departmentname);
			leaveReportvo.setRank(rank);
			leaveReportvo.setStartday(startday);
			leaveReportvo.setEndday(endday);
			leaveReportvo.setTitle(title);
			leaveReportvo.setReason(reason);
			leaveReportvo.setEmergencycontactnetwork(emergencycontactnetwork);
			leaveReportvo.setSharedepartmentno(sharedepartmentno);
			leaveReportvo.setApprover(approver);
			leaveReportvo.setEmployeename(employeename);
			leaveReportvo.setTextnumber(String.valueOf(textnumber));
			leaveReportvo.setApprover_name(approverName);
			
			
			/*HashMap<String,Object> paraMap = new HashMap<String,Object>();
			paraMap.put("writeday", writeday);
			paraMap.put("employeename", employeename);
			paraMap.put("fk_employeeno", fk_employeeno);
			paraMap.put("departmentname", departmentname);
			paraMap.put("expendituremode", expendituremode);
			paraMap.put("expenditureday", expenditureday);
			paraMap.put("title", title);
			paraMap.put("content", content);
			paraMap.put("approver", approver);
			paraMap.put("attach", attach1);*/			
			
			
			if(!attach.isEmpty()) {
				HttpSession session = mrequest.getSession();
				String root = session.getServletContext().getRealPath("/");
				String path = root + "resources" + File.separator + "files";
				
				String newFileName = "";
				
				byte[] bytes = null;
				
				long fileSize = 0;
			
				try {
					bytes = attach.getBytes();
					
					newFileName = fileManager.doFileUpload(bytes, attach.getOriginalFilename(), path);
					
					leaveReportvo.setFileName(newFileName);
					
					leaveReportvo.setOrgFilename(attach.getOriginalFilename());
					
					fileSize = attach.getSize();
					
					leaveReportvo.setFileSize(String.valueOf(fileSize));
			
				} catch (Exception e) {
						e.printStackTrace();
				}	
			}
			
			// *** 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어 코드) 작성하기 *** //
			leaveReportvo.setTitle(MyUtil.replaceParameter(leaveReportvo.getTitle()));
			leaveReportvo.setReason(MyUtil.replaceParameter(leaveReportvo.getReason()));
			leaveReportvo.setReason(leaveReportvo.getReason().replaceAll("\r\n", "<br/>")); 
			
			// ========= !!첨부파일이 있는지 없는지 알아오기 끝!! =========  
			
			
			int n = 0;
			if(attach.isEmpty()) {
				// 첨부파일이 없는 경우이라면
				n = service.addLeaveReport(leaveReportvo); 
				mrequest.setAttribute("msg", "정상적으로 결재가 제출되었습니다.");
			}
			else {
				// 첨부파일이 있는 경우이라면
				n = service.addLeaveReport_withFile(leaveReportvo); 
				mrequest.setAttribute("msg", "정상적으로 결재가 제출되었습니다.");
			}	
			
			if(n == 1) {
				// 휴가신청서 제출시 결재자 테이블 insert 
				int addVactionApprover = service.addVactionApprover(leaveReportvo);
			}
		}
		
		/*HttpSession session = mrequest.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		int employeeno = loginuser.getEmployeeno();*/
		
		/*List<HashMap<String,String>> addListMap = service.addList(leaveReportvo); // 미결재보관함 List select 하기
		System.out.println(addListMap);
		
		mrequest.setAttribute("addListMap", addListMap);*/		
		mrequest.setAttribute("loc", mrequest.getContextPath()+"/incomplete_payment_archive.action");
		return "msg";
		}
	
	////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	
	
}
	
	














