package com.bo.payment_report.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bo.payment_report.model.ExReportVO;
import com.bo.payment_report.model.InterPayment_Report_DAO;

//=== Service 선언 ===
@Service 
public class Payment_Report_Service implements InterPayment_Report_Service {
	
	// === 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired  // Type에 따라 알아서 Bean 을 주입해준다.
	private InterPayment_Report_DAO dao;
	
	
	// Modal ajax로 부서 넣어주기
	@Override
	public List<HashMap<String, String>> addAprrovalModal() {
		
		List<HashMap<String, String>> addAprrovalList = dao.addAprrovalModal();
		
		//결제 완료
		
		//결제 완결 확인
		
		// 결제 완결 업데이트
		
		return addAprrovalList;
	}


	// Modal ajax로 넣어준 부서에 대한 사원명단 select
	@Override
	public List<HashMap<String, String>> addModalSelect(String departmentname) {
		List<HashMap<String, String>> addModalSelectList = dao.addModalSelect(departmentname);
		return addModalSelectList;
	}

	// 파일첨부가 없는 경우 exReport insert 하기
	@Override
	public int add(ExReportVO exReportvo) {
		int n = dao.add(exReportvo);
		return n;
	}
	// 파일첨부가 있는 경우 exReport insert 하기
	@Override
	public int add_withFile(ExReportVO exReportvo) {
		int n =dao.add_withFile(exReportvo);
		return n;
	}
}















