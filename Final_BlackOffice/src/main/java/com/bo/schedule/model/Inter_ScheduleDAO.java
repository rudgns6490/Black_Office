package com.bo.schedule.model;

import java.util.List;

public interface Inter_ScheduleDAO {
	
	// 개인일정 불러오기 
	List<ScheduleVO> individualScheduleJSONList();

	// 일정추가하기 
	int addCalendaraddSchedule(ScheduleVO schedulevo);
	
	// 일정드랍 event시 이벤트 2020/01/13 hjp
	int updateDropCalendarSchedule(ScheduleVO schedulevo);

	// 일정 수정 2020/01/13 hjp
	int updateCalendarSchedule(ScheduleVO schedulevo);

	// 일정삭제 2020/01/13 hjp
	int calendarDelete(ScheduleVO schedulevo);

}
