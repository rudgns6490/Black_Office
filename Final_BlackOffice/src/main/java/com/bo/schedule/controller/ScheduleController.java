package com.bo.schedule.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bo.member.model.MemberVO;
import com.bo.schedule.model.ScheduleVO;
import com.bo.schedule.service.Inter_ScheduleService;

@Controller
public class ScheduleController {

	// 의존객체 주입  
	@Autowired  
	private Inter_ScheduleService service;

	// 로그인 정보 
	public MemberVO memberinfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberVO membervo = (MemberVO)session.getAttribute("loginuser"); 
		return membervo; 
	}
	

	// 메인 홈페이지 json데이터  
	@ResponseBody
	@RequestMapping(value="/mainScheduleJSONList.action")
	public String mainScheduleJSONList(HttpServletRequest request, HttpServletResponse response) { 
		
		MemberVO member = memberinfo(request); 

		String result = service.mainScheduleJSONList(member); 
		return result; 
	}
	
	

	// 개인일정 홈페이지 2020/01/13 hjp
	@RequestMapping(value="/individualSchedule.action")
	public String individualschedule(HttpServletRequest request, HttpServletResponse response) { 

		return "schedule/individualschedule.tiles1";
	}


	// 개인일정 JSON 데이터 2020/01/13 hjp
	@ResponseBody
	@RequestMapping(value="/individualScheduleJSONList.action", produces="text/plain;charset=UTF-8")
	public String individualcalendarJSONList(HttpServletRequest request, HttpServletResponse response) { 

		MemberVO member = memberinfo(request); 

		String result = service.individualcalendarJSONList(member); 
		return result; 
	}


	// 부서일정 홈페이지 2020/01/20 hjp
	@RequestMapping(value="/departmentSchedule.action")
	public String departmentschedule(HttpServletRequest request, HttpServletResponse response) { 
		
		MemberVO member = memberinfo(request); 
		String departmentname = ""; 
		
		if( member.getFk_departmentno() == 1)
			departmentname =  "인사팀";
		
		if( member.getFk_departmentno() == 2)
			departmentname =  "마케팅팀";
		
		if( member.getFk_departmentno() == 3)
			departmentname =  "개발1팀";
		
		if( member.getFk_departmentno() == 4)
			departmentname =  "개발2팀";
		
		if( member.getFk_departmentno() == 5)
			departmentname =  "영업팀";
		
				
		request.setAttribute("departmentname", departmentname);

		return "schedule/departmentschedule.tiles1";
	}


	// 부서일정 JSON 데이터 2020/01/20 hjp
	@ResponseBody
	@RequestMapping(value="/departmentScheduleJSONList.action", produces="text/plain;charset=UTF-8")
	public String departmentcalendarJSONList(HttpServletRequest request, HttpServletResponse response) { 

		MemberVO member = memberinfo(request); 

		String result = service.departmentcalendarJSONList(member); 
		return result; 
	}
	
	
	// 부서일정 추가  2020/01/30 hjp
	@ResponseBody
	@RequestMapping(value="/departmentAddSchedule.action", produces="text/plain;charset=UTF-8")
	public String  departmentAddSchedule(HttpServletRequest request, HttpServletResponse response) {
		
		String result1 = request.getParameter("eventData") ;
		
		MemberVO member = memberinfo(request);
		int fk_employeeno = member.getEmployeeno();
		int fk_departmentno = member.getFk_departmentno(); 
		
		String result = service.departmentAddSchedule(result1, fk_employeeno, fk_departmentno); 
		
		return result; 
	}
	
	
	
	
	






	// 개인일정 추가 2020/01/13 hjp
	@ResponseBody
	@RequestMapping(value="/addSchedule.action", produces="text/plain;charset=UTF-8")
	public String  addSchedule(HttpServletRequest request, HttpServletResponse response) { 

		String result1 = request.getParameter("eventData") ;

		MemberVO member = memberinfo(request); 
		int fk_employeeno = member.getEmployeeno();

		String result = service.addSchedule(result1, fk_employeeno); 
		return result; 
	}


	// 개인일정드랍 event시 이벤트 2020/01/13 hjp
	@ResponseBody
	@RequestMapping(value="/calendarDropUpdate.action", produces="text/plain;charset=UTF-8")
	public String  calendarDropUpdate(HttpServletRequest request, HttpServletResponse response) { 

		String result1 = request.getParameter("eventData") ;
		String result = service.calendarDropUpdate(result1); 
		return result; 
	}


	// 일정 수정 2020/01/13 hjp
	@ResponseBody
	@RequestMapping(value="/calendarUpdate.action", produces="text/plain;charset=UTF-8")
	public String  calendarUpdate(HttpServletRequest request, HttpServletResponse response) { 

		String result1 = request.getParameter("eventData") ;
		String result = service.updateCalendarSchedule(result1); 
		return result; 
	}

	
	// 일정삭제 2020/01/13 hjp
	@ResponseBody
	@RequestMapping(value="/calendarDelete.action", produces="text/plain;charset=UTF-8")
	public String  calendarDelete(HttpServletRequest request, HttpServletResponse response) { 

		String result1 = request.getParameter("eventData") ;
		String result = service.calendarDelete(result1); 
		return result; 
	}







}
