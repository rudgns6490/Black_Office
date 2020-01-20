package com.bo.schedule.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bo.member.model.MemberVO;
import com.bo.schedule.service.Inter_ScheduleService;

@Controller
public class ScheduleController {

	// 의존객체 주입  
	@Autowired  
	private Inter_ScheduleService service;
	
	
	public MemberVO memberinfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberVO membervo = (MemberVO)session.getAttribute("loginuser"); 
		return membervo; 
	}



	// 개인일정 홈페이지 2020/01/13 hjp
	@RequestMapping(value="/individualSchedule.action")
	public String requireLoginCheck_individualschedule(HttpServletRequest request, HttpServletResponse response) { 
		
		return "schedule/individualschedule.tiles1";
	}

	
	// 개인일정 JSON 데이터 2020/01/13 hjp
	@ResponseBody
	@RequestMapping(value="/individualScheduleJSONList.action", produces="text/plain;charset=UTF-8")
	public String requireLoginCheck_individualcalendarJSONList(HttpServletRequest request, HttpServletResponse response) { 

		String result = service.individualcalendarJSONList(); 
		return result; 
	}

	
	// 일정 추가 2020/01/13 hjp
	@ResponseBody
	@RequestMapping(value="/addSchedule.action", produces="text/plain;charset=UTF-8")
	public String requireLoginCheck_addSchedule(HttpServletRequest request, HttpServletResponse response) { 
		
		String result1 = request.getParameter("eventData") ;
		String result = service.addSchedule(result1); 
		return result; 

	}

	
	// 일정드랍 event시 이벤트 2020/01/13 hjp
	@ResponseBody
	@RequestMapping(value="/calendarDropUpdate.action", produces="text/plain;charset=UTF-8")
	public String requireLoginCheck_calendarDropUpdate(HttpServletRequest request, HttpServletResponse response) { 
		
		String result1 = request.getParameter("eventData") ;
		String result = service.calendarDropUpdate(result1); 
		return result; 
	}

	
	// 일정 수정 2020/01/13 hjp
	@ResponseBody
	@RequestMapping(value="/calendarUpdate.action", produces="text/plain;charset=UTF-8")
	public String requireLoginCheck_calendarUpdate(HttpServletRequest request, HttpServletResponse response) { 

		String result1 = request.getParameter("eventData") ;
		String result = service.updateCalendarSchedule(result1); 
		return result; 
	}


	// 일정삭제 2020/01/13 hjp
	@ResponseBody
	@RequestMapping(value="/calendarDelete.action", produces="text/plain;charset=UTF-8")
	public String requireLoginCheck_calendarDelete(HttpServletRequest request, HttpServletResponse response) { 

		String result1 = request.getParameter("eventData") ;
		String result = service.calendarDelete(result1); 
		return result; 
	}

}
