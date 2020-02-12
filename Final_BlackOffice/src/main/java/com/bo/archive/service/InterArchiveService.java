package com.bo.archive.service;

import java.util.HashMap;
import java.util.List;

import com.bo.archive.model.ExpenditureVO;
import com.bo.archive.model.ReportVO;
import com.bo.archive.model.VacationVO;
import com.bo.payment_report.model.ExReportVO;

public interface InterArchiveService {

	//////////////=== 보고서함 ===  //////////////
	// 내가 쓴보고서 함
	int getmyReportTotalCountWithNoSearch(HashMap<String, String> paraMap);                        // 검색조건이 없을경우 내가 쓴 리포트 총 게시물 수 (totalCount)
	int getmyReportTotalCountWithSearch(HashMap<String, String> paraMap);                        // 검색조건이 있을경우 내가 쓴 리포트 총 게시물 수 (totalCount)
	List<HashMap<String, String>> myWriteReportListWithPaging(HashMap<String, String> paraMap);         // 페이징 한 내가 쓴 보고서 목록
	
	// 보고서 수신함 
	int getreceiveReportTotalCountWithNoSearch(HashMap<String, String> paraMap);                  // 검색조건이 없을경우 내가 받은 리포트 총 게시물 수 (totalCount)
	int getreceiveReportTotalCountWithSearch(HashMap<String, String> paraMap);                     // 검색조건이 있을경우 내가 받은 리포트 총 게시물 수 (totalCount)
	List<HashMap<String, String>> receiveWriteReportListWithPaging(HashMap<String, String> paraMap);   // 페이징 한 내가 받은 보고서 목록
	
	
	
	//////////////=== 결재문서함 ===  //////////////
	// === 미결재 문서 목록 보여주기 ===
	int getIncompleteDocumentTotalCountWithNoSearch(HashMap<String, String> paraMap);               // 검색조건이 없을경우 미결재 문서 총 게시물 수 (totalCount)
	int getIncompleteDocumentTotalCountWithSearch(HashMap<String, String> paraMap);                  // 검색조건이 '있'을경우 미결재 문서 총 게시물 수 (totalCount)
	List<HashMap<String, String>> incompleteDocumentListWithPaging(HashMap<String, String> paraMap);   // 페이징처리 한 미결재 문서 목록 가져오기
	
	
	// === 내가 받은 (내가 결재권자인) 결재문서 수신함  목록 보여주기 ===
	int getreceivePaymentDocumentTotalCountWithNoSearch(HashMap<String, String> paraMap);            // 검색조건이 없을경우 내 결재문서 총 게시물 수 (totalCount)
	int getreceivePaymentDocumentTotalCountWithSearch(HashMap<String, String> paraMap);               // 검색조건이 '있'을경우 내 결재문서 총 게시물 수 (totalCount)
	List<HashMap<String, String>> receiveDocumentListWithPaging(HashMap<String, String> paraMap);      // 페이징처리 한 내 결재문서 목록 가져오기
		
// == 여기서부터 상세보기 ==============================================================================================//
	
	int approverPeoples(String textnumber); // 지출 결재자 인원 알아오기
	int approverLeavePeoples(String textnumber); // 휴가 신청서 결재자 인원 알아오기
	
	List<HashMap<String, String>> detailList(HashMap<String, String> paraMap); // 지출결의서 상세보기
	
	List<HashMap<String, String>> appAjax(String number); // 지출 결재란 ajax
	
	ExReportVO getViewWithNoAddCount(String textnumber); // 지출 결재서 다운로드
	
	List<HashMap<String, String>> detaiLeavelList(HashMap<String, String> paraMap);// 휴가 신청서 상세보기
	
	List<HashMap<String, String>> appLeaveAjax(String number); // 휴가 결재란 ajax
	
	ExReportVO getleavedownload(String textnumber); // 휴가 지출서 파일 다운로드
	
	int approverSuccess(HashMap<String, String> paraMap); // 지출 결재 후 업데이트
	int approverLeaveSuccess(HashMap<String, String> paraMap); // 휴가 신청서 결재 후 업데이트
	int updateApprover(HashMap<String, String> paraMap); // 결재완료 테이블 update
	int updateLeaveApprover(HashMap<String, String> paraMap); // 결재완료 테이블 update
	
	String approverFindAjax(String textnumber); // 결재완료 상태를 나타내기 위한 ajax
	
	String approverFindAjaxTwo(String textnumber); //결재완료 ajax 2번째 지출 결의서 개수 알아오기
	String approverFindAjaxThree(String textnumber); //결재완료 ajax 3번째 휴가 신청서 개수 알아오기.
	
	
	
	
	


}











