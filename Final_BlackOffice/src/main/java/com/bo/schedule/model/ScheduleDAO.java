package com.bo.schedule.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleDAO implements Inter_ScheduleDAO {

	
	@Autowired  
	private SqlSessionTemplate sqlschedule;
	
	
	// 2020.01.14 개인일정 가져오기 
	@Override
	public List<ScheduleVO> individualScheduleJSONList() {
		List<ScheduleVO> JSONList = sqlschedule.selectList("schedule.individualScheduleJSONList");

		return JSONList; 
	}

	// 2020.01.14 일정추가하기 
	@Override
	public int addCalendaraddSchedule(ScheduleVO schedulevo) {
		
		int n = sqlschedule.insert("schedule.addCalendaraddSchedule", schedulevo); 
		return n ; 
	}

}
