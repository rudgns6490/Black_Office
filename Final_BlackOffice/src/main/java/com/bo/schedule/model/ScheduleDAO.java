package com.bo.schedule.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleDAO implements Inter_ScheduleDAO {

	
	@Autowired  
	private SqlSessionTemplate sqlsession;
	
	
	// 2020.01.14 개인일정 가져오기 
	@Override
	public List<ScheduleVO> individualScheduleJSONList(int fk_employeeno) {
		
		List<ScheduleVO> JSONList = sqlsession.selectList("schedule.individualScheduleJSONList", fk_employeeno);
		return JSONList; 
	}
    
	// 2020.01.14 일정추가하기 
	@Override
	public int addCalendaraddSchedule(ScheduleVO schedulevo) {
		
		int n = sqlsession.insert("schedule.addCalendaraddSchedule", schedulevo); 
		return n ; 
	}

	// 일정드랍 event시 이벤트 2020/01/13 hjp
	@Override
	public int updateDropCalendarSchedule(ScheduleVO schedulevo) {

		int n = sqlsession.update("schedule.updateDropCalendarSchedule", schedulevo); 
		return n ; 
	}

	// 일정 수정 2020/01/13 hjp
	@Override
	public int updateCalendarSchedule(ScheduleVO schedulevo) {
		
		int n = sqlsession.update("schedule.updateCalendarSchedule", schedulevo); 
		return n ; 
	}

	
	// 일정삭제 2020/01/13 hjp
	@Override
	public int calendarDelete(ScheduleVO schedulevo) {
		
		int n = sqlsession.delete("schedule.calendarDelete", schedulevo); 
		return n; 
		
	}
	
	
	// 부서일정 JSON 데이터 2020/01/20 hjp
	@Override
	public List<ScheduleVO> departmentcalendarJSONList(int departmentno) {
		
		List<ScheduleVO> JSONList = sqlsession.selectList("schedule.departmentcalendarJSONList", departmentno);
		return JSONList; 
	}

	// 부서일정추가 
	@Override
	public int departmentAddSchedule(ScheduleVO schedulevo) {
		
		int n = sqlsession.insert("schedule.departmentAddSchedule", schedulevo); 
		return n ; 
	}

	
	
	// 메인일정 
	@Override
	public List<ScheduleVO> mainScheduleJSONList(int fk_employeeno) {
		
		List<ScheduleVO> JSONList = sqlsession.selectList("schedule.mainScheduleJSONList", fk_employeeno);
		return JSONList; 
	}
	
	
	
	

}
