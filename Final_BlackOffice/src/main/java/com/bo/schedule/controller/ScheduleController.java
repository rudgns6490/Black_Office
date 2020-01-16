package com.bo.schedule.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bo.schedule.model.Inter_ScheduleDAO;
import com.bo.schedule.model.ScheduleDAO;
import com.bo.schedule.model.ScheduleVO;
import com.bo.schedule.service.Inter_ScheduleService;
import com.google.gson.Gson;

@Controller
public class ScheduleController {

	// ***** 개인일정 
	@Autowired  
	private Inter_ScheduleService service;



	// 개인일정 홈페이지 2020/01/13 hjp
	@RequestMapping(value="/individualSchedule.action")
	public String individualschedule() { 
		return "schedule/individualschedule.tiles1";
	}




	// 개인일정 JSON 데이터 2020/01/13 hjp
	@RequestMapping(value="/individualScheduleJSONList.action")
	public ModelAndView individualcalendarJSONList(HttpServletRequest request, ModelAndView mav) { 

		String result = service.individualcalendarJSONList(); 

		mav.addObject("result", result); 
		mav.setViewName("jsonResult");

		return mav; 
	}





	// 일정 추가 2020/01/13 hjp
	@ResponseBody
	@RequestMapping(value="/addSchedule.action", produces="text/plain;charset=UTF-8")
	public ModelAndView addSchedule(HttpServletRequest request, ModelAndView mav) { 

		String result1 = request.getParameter("eventData") ;

		String result = service.addSchedule(result1, request); 

		mav.addObject("result", result); 
		mav.setViewName("jsonResult");

		return mav; 

	}



	// 일정드랍 event시 이벤트 2020/01/13 hjp
	@RequestMapping(value="/calendarDropUpdate.action")
	public ModelAndView calendarDropUpdate(HttpServletRequest request, ModelAndView mav) { 

		String result1 = request.getParameter("eventData") ;

		String result = service.calendarDropUpdate(result1, request); 

		mav.addObject("result", result); 
		mav.setViewName("jsonResult");

		return mav; 
	}






	// 일정 수정 2020/01/13 hjp
	@ResponseBody
	@RequestMapping(value="/calendarUpdate.action")
	public ModelAndView calendarUpdate(HttpServletRequest request, ModelAndView mav) { 

		String result1 = request.getParameter("eventData") ;

		String result = service.updateCalendarSchedule(result1, request); 

		mav.addObject("result", result); 
		mav.setViewName("jsonResult");

		return mav; 

	}





	/*



	// 일정삭제 2020/01/13 hjp
	@RequestMapping(value="/calendarDelete.action")
	public String calendarDelete() { 
		return "schedule/calendarDelete.tiles1";
	}


	 */


}
