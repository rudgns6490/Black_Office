package com.bo.payment_report.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bo.payment_report.service.InterPayment_Report_Service;


// === 컨트롤러 선언 ===
/* XML에서 빈을 만드는 대신에 클래스명 앞에 @Component 어노테이션을 적어주면 해당 클래스는 bean으로 자동 등록된다. 
그리고 bean의 이름(첫글자는 소문자)은 해당 클래스명이 된다. */
@Component
@Controller
public class Payment_Report_Controller { 
	
	// === #35. 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private InterPayment_Report_Service service;
	
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
	

	
}













