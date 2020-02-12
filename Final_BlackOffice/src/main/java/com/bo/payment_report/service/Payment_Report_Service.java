package com.bo.payment_report.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bo.payment_report.model.ExReportVO;
import com.bo.payment_report.model.InterPayment_Report_DAO;
import com.bo.payment_report.model.LeaveReportVO;

//=== Service 선언 ===
@Service 
public class Payment_Report_Service implements InterPayment_Report_Service {
	
	// === 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired  // Type에 따라 알아서 Bean 을 주입해준다.
	private InterPayment_Report_DAO dao;
	
	// 부서명 가지고 오기
	@Override
	public String departmentName(String employeeno) {
		String deptName = dao.departmentName(employeeno);
		return deptName;
	}
	
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
	
	
	
	
	// 첨부파일이 없는 경우 leave inset 하기
	@Override
	public int addLeaveReport(LeaveReportVO leaveReportvo) {
		int n = dao.addLeaveReport(leaveReportvo);
		return n;
	}
	// 첨부파일이 있는 경우 leave inset 하기
	@Override
	public int addLeaveReport_withFile(LeaveReportVO leaveReportvo) {
		int n = dao.addLeaveReport_withFile(leaveReportvo);
		return n;
	}
	
	
	// 지출결의서 문서번호 사용할 시퀀스 채번해오기
	@Override
	public int textNumber() {
		int number = dao.textNumber();
		return number;
	}

	
	
	// 지출결의서 제출시 결재자 테이블 insert
	@Override
	public int addApprover(ExReportVO exReportvo) {
		int addApprover = dao.addApprover(exReportvo);
		return addApprover;
	}
	
	// 휴가신청서 제출시 결재자 테이블 insert
	@Override
	public int addVactionApprover(LeaveReportVO leaveReportvo) {
		int addVactionApprover = dao.addVactionApprover(leaveReportvo);
		return addVactionApprover;
	}
	
	
	// 미결재보관함 List select 하기
	/*@Override
	public List<HashMap<String, String>> addList(LeaveReportVO leaveReportvo) {
		List<HashMap<String, String>> addListMap = dao.addList(leaveReportvo);
		return addListMap;
	}*/

	
}















