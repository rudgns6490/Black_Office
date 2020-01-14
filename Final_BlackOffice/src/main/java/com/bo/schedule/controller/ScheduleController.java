package com.bo.schedule.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public String individualcalendarJSONList(HttpServletRequest request) { 

		String result = service.individualcalendarJSONList(); 

		request.setAttribute("result", result);

		return "schedule/jsonResult.tiles1";

	}





	// 일정 추가 2020/01/13 hjp
	@ResponseBody
	@RequestMapping(value="/addSchedule.action", produces="text/plain;charset=UTF-8")
	public String addSchedule(HttpServletRequest request) { 

		String result1 = request.getParameter("eventData") ;

		// String JSON데이터를 객체화
		Gson gson = new Gson(); 
		ScheduleVO schedulevo = gson.fromJson(result1, ScheduleVO.class); 

		if( schedulevo.isAllday() == false ) {
			schedulevo.setSchedule_allday(0);
		}

		else 
			schedulevo.setSchedule_allday(1);


		Inter_ScheduleDAO dao = new ScheduleDAO(); 

		int n = dao.addCalendaraddSchedule(schedulevo); 

		JSONObject jsobj = new JSONObject();

		if( n == 1) 
			jsobj.put("result", true); 

		else 
			jsobj.put("result", false); 

		String result = jsobj.toString(); 

		request.setAttribute("result", result);

		return "schedule/jsonResult.tiles1";
	}



	// 일정드랍 event시 이벤트 2020/01/13 hjp
	@RequestMapping(value="/calendarDropUpdate.action")
	public String calendarDropUpdate(HttpServletRequest request) { 


		String result1 = request.getParameter("eventData") ;

		Gson gson = new Gson(); 
		ScheduleVO schedulevo = gson.fromJson(result1, ScheduleVO.class); 


		Inter_ScheduleDAO dao = new ScheduleDAO(); 

	//	int n = dao.updateDropCalendarSchedule(schedulevo); 

		JSONObject jsobj = new JSONObject();

	//	if( n == 1) 
			jsobj.put("result", true); 

	//	else 
			jsobj.put("result", false); 


		String result = jsobj.toString(); 

		request.setAttribute("result", result);




		return "schedule/jsonResult.tiles1";
	}


	/*



	// 일정 수정 2020/01/13 hjp
	@ResponseBody
	@RequestMapping(value="/calendarUpdate.action")
	public String calendarUpdate() { 
		return "schedule/calendarUpdate.tiles1";
	}





	// 일정삭제 2020/01/13 hjp
	@RequestMapping(value="/calendarDelete.action")
	public String calendarDelete() { 
		return "schedule/calendarDelete.tiles1";
	}


	 */


}
