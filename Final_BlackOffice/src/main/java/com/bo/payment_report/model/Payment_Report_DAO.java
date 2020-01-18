package com.bo.payment_report.model;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//=== DAO 선언 ===
@Repository
public class Payment_Report_DAO implements InterPayment_Report_DAO {
	
	// === 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private SqlSessionTemplate sqlsession;
	
	
	// Modal ajax로 부서 넣어주기
	@Override
	public List<HashMap<String, String>> addAprrovalModal() {
		List<HashMap<String, String>> addAprrovalList = sqlsession.selectList("payment_report.addAprrovalModal");
		return addAprrovalList;
	}

	
	// Modal ajax로 넣어준 부서에 대한 사원명단 select
	@Override
	public List<HashMap<String, String>> addModalSelect(String departmentname) {
		List<HashMap<String, String>> addModalSelectList = sqlsession.selectList("payment_report.addModalSelect", departmentname);
		return addModalSelectList;
	}
}
