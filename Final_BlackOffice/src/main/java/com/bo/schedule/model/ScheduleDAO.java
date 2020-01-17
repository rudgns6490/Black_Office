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
	public List<ScheduleVO> individualScheduleJSONList() {
		
		List<ScheduleVO> JSONList = sqlsession.selectList("schedule.individualScheduleJSONList");

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
	
	
	
	

}
