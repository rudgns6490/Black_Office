package com.bo.schedule.model;

import java.util.List;

public interface Inter_ScheduleDAO {
	
	// 개인일정 불러오기 
	List<ScheduleVO> individualScheduleJSONList();

	// 일정추가하기 
	int addCalendaraddSchedule(ScheduleVO schedulevo);

}
