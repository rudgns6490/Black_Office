package com.bo.payment_report.model;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//=== DAO 선언 ===
@Repository
public class Payment_Report_DAO implements InterPayment_Report_DAO {
	
	// === 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private SqlSessionTemplate sqlsession;
}
