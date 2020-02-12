package com.bo.schedule.service;

import com.bo.member.model.MemberVO;

public interface Inter_ScheduleService {

	// 개인 일정 리스트 불러오기 
	String individualcalendarJSONList(MemberVO member);

	// 일정 추가하기 
	String addSchedule(String result1, int fk_employeeno);

	// 일정드랍 event시 이벤트 2020/01/13 hjp
	String calendarDropUpdate(String result1);

	// 일정 수정 2020/01/13 hjp
	String updateCalendarSchedule(String result1);

	// 일정삭제 2020/01/13 hjp
	String calendarDelete(String result1);

	// 부서일정 2020/01/20 hjp
	String departmentcalendarJSONList(MemberVO member);

	// 부서일정 추가  2020/01/30 hjp
	String departmentAddSchedule(String result1, int fk_employeeno, int fk_departmentno);
	 
	// 메인홈페이지 
	String mainScheduleJSONList(MemberVO member);
	
	

}
