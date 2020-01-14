package com.bo.schedule.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bo.schedule.model.Inter_ScheduleDAO;
import com.bo.schedule.model.ScheduleVO;


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
				jsobj.put("user_no", cvo.getUser_no());
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



	
}
