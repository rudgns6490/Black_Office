package com.bo.payment_report.model;

import java.util.HashMap;
import java.util.List;

public interface InterPayment_Report_DAO {

	List<HashMap<String, String>> addAprrovalModal(); // Modal ajax로 부서 넣어주기

	List<HashMap<String, String>> addModalSelect(String departmentname); // Modal ajax로 넣어준 부서에 대한 사원명단 select
	
	String departmentName(String employeeno);  // 부서명 가지고 오기S
	
	//-------------------------------------------------//	
	int add(ExReportVO exReportvo);	// 파일첨부가 없는 경우 exReport insert 하기
	int add_withFile(ExReportVO exReportvo); // 파일첨부가 있는 경우 exReport insert 하기
	//-------------------------------------------------//
	
	//-------------------------------------------------//	
	int addLeaveReport(LeaveReportVO leaveReportvo); // 첨부파일이 없는 경우 leave inset 하기
	int addLeaveReport_withFile(LeaveReportVO leaveReportvo); // 첨부파일이 있는 경우 leave inset 하기
	//-------------------------------------------------//

	int textNumber(); // 지출결의서 문서번호 사용할 시퀀스 채번해오기

	int addApprover(ExReportVO exReportvo);	// 지출결의서 제출시 결재자 테이블 insert
	int addVactionApprover(LeaveReportVO leaveReportvo);  // 휴가신청서 제출시 결재자 테이블 insert

//	List<HashMap<String, String>> addList(LeaveReportVO leaveReportvo); // 미결재보관함 List select 하기
	
	
	

}
