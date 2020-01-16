package com.bo.schedule.service;

import javax.servlet.http.HttpServletRequest;

public interface Inter_ScheduleService {

	// 개인 일정 리스트 불러오기 
	String individualcalendarJSONList();

	// 일정 추가하기 
	String addSchedule(String result1, HttpServletRequest request);

	// 일정드랍 event시 이벤트 2020/01/13 hjp
	String calendarDropUpdate(String result1, HttpServletRequest request);

	// 일정 수정 2020/01/13 hjp
	String updateCalendarSchedule(String result1, HttpServletRequest request);
	
	

}
