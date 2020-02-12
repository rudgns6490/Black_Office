package com.bo.schedule.model;

import java.util.List;

public interface Inter_ScheduleDAO {
	
	// 개인일정 불러오기 
	List<ScheduleVO> individualScheduleJSONList(int no);

	// 일정추가하기 
	int addCalendaraddSchedule(ScheduleVO schedulevo);
	
	// 일정드랍 event시 이벤트 2020/01/13 hjp
	int updateDropCalendarSchedule(ScheduleVO schedulevo);

	// 일정 수정 2020/01/13 hjp
	int updateCalendarSchedule(ScheduleVO schedulevo);

	// 일정삭제 2020/01/13 hjp
	int calendarDelete(ScheduleVO schedulevo);

	// 부서일정 JSON 데이터 2020/01/20 hjp
	List<ScheduleVO> departmentcalendarJSONList(int employeeno);
	
	// 부서일정 추가하기 
	int departmentAddSchedule(ScheduleVO schedulevo);

	// 개인일정
	List<ScheduleVO> mainScheduleJSONList(int employeeno);

}
