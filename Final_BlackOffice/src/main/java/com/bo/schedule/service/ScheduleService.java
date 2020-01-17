package com.bo.schedule.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bo.schedule.model.Inter_ScheduleDAO;
import com.bo.schedule.model.ScheduleDAO;
import com.bo.schedule.model.ScheduleVO;
import com.google.gson.Gson;


@Service
public class ScheduleService implements Inter_ScheduleService {
	

	@Autowired 
	private Inter_ScheduleDAO dao;
	
	
	
	// 2020-01-13 hjp 
	// 개인일정 가져오기 json데이터  
	@Override
	public String individualcalendarJSONList() {

		JSONArray jsonArr = new JSONArray();

		// 개인일정 불러오기 
		List<ScheduleVO> calendarList = dao.individualScheduleJSONList();

		if(calendarList != null)  {

			for(ScheduleVO cvo : calendarList) {

				JSONObject jsobj = new JSONObject();

				jsobj.put("schedule_no", cvo.getSchedule_no());
				jsobj.put("fk_employeeno", cvo.getfk_employeeno());
				jsobj.put("title", cvo.getSchedule_title());
				jsobj.put("description", cvo.getSchedule_content());
				jsobj.put("start", cvo.getSchedule_start());
				jsobj.put("end", cvo.getSchedule_end());
				jsobj.put("backgroundColor", cvo.getSchedule_color());
				jsobj.put("schedule_importance", cvo.getSchedule_importance());
				jsobj.put("schedule_progresStat", cvo.getSchedule_progresStat());
				jsobj.put("schedule_allday", cvo.getSchedule_allday());
				jsobj.put("schedule_authority", cvo.getSchedule_authority());
				jsobj.put("schedule_type", cvo.getSchedule_type());
				jsobj.put("textColor", "#ffffff");
				
				// 한글로 표기하기 위해 
				String type = cvo.getType();
				jsobj.put("type", type);
				jsobj.put("allDay", cvo.isAllday());
				
				// 일부러 만든 내용 
				// db추가 되면 수정해주기 
				jsobj.put("username", "사장1");
				
				jsonArr.put(jsobj); 
			}

		}
		
		return jsonArr.toString();
	}


	// 2020-01-13 hjp 
	// 일정추가하기 
	@Override
	public String addSchedule(String result1, HttpServletRequest request) {

		Gson gson = new Gson(); 
		ScheduleVO schedulevo = gson.fromJson(result1, ScheduleVO.class); 

		if( schedulevo.isAllday() == false ) {
			schedulevo.setSchedule_allday(0);
		}

		else 
			schedulevo.setSchedule_allday(1);


		int n = dao.addCalendaraddSchedule(schedulevo); 

		JSONObject jsobj = new JSONObject();

		if( n == 1) 
			jsobj.put("result", true); 

		else 
			jsobj.put("result", false); 

		String result = jsobj.toString(); 

		request.setAttribute("result", result);  // 필요없음 
		
		return result; 
	}


	
	// 일정드랍 event시 이벤트 2020/01/13 hjp
	@Override
	public String calendarDropUpdate(String result1, HttpServletRequest request) {
		
		Gson gson = new Gson(); 
		ScheduleVO schedulevo = gson.fromJson(result1, ScheduleVO.class); 

		int n = dao.updateDropCalendarSchedule(schedulevo); 

		JSONObject jsobj = new JSONObject();

		if( n == 1) 
			jsobj.put("result", true); 

		else 
			jsobj.put("result", false); 


		String result = jsobj.toString(); 

		request.setAttribute("result", result);
		
		return result; 
	}

	
	
	
	// 일정 수정 2020/01/13 hjp
	@Override
	public String updateCalendarSchedule(String result1, HttpServletRequest request) {
		
		Gson gson = new Gson(); 
		ScheduleVO schedulevo = gson.fromJson(result1, ScheduleVO.class); 

		if( schedulevo.isAllday() == false ) {
			schedulevo.setSchedule_allday(0);
		}

		else 
			schedulevo.setSchedule_allday(1);

		int n = dao.updateCalendarSchedule(schedulevo); 


		JSONObject jsobj = new JSONObject();

		if( n == 1) 
			jsobj.put("result", true); 

		else 
			jsobj.put("result", false); 

		String result = jsobj.toString(); 

		request.setAttribute("result", result);
		
		return result; 
	}
	
	
	



	
}
