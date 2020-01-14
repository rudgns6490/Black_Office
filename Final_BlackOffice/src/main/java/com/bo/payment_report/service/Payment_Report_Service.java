package com.bo.payment_report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bo.payment_report.model.InterPayment_Report_DAO;

//=== Service 선언 ===
@Service 
public class Payment_Report_Service implements InterPayment_Report_Service {
	
	// === 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired  // Type에 따라 알아서 Bean 을 주입해준다.
	private InterPayment_Report_DAO dao;
}
