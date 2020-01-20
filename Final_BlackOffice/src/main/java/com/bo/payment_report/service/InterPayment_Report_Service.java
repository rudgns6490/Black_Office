package com.bo.payment_report.service;

import java.util.HashMap;
import java.util.List;

import com.bo.payment_report.model.ExReportVO;

public interface InterPayment_Report_Service {

	List<HashMap<String, String>> addAprrovalModal(); // Modal ajax로 부서 넣어주기

	List<HashMap<String, String>> addModalSelect(String departmentname); // Modal ajax로 넣어준 부서에 대한 사원명단 select

 //-------------------------------------------------//	
	int add(ExReportVO exReportvo);				// 파일첨부가 없는 경우 exReport insert 하기
	int add_withFile(ExReportVO exReportvo);	// 파일첨부가 있는 경우
 //-------------------------------------------------//
	
	
	
	
	
}
