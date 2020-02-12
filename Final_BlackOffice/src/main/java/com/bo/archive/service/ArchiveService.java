package com.bo.archive.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bo.archive.model.ExpenditureVO;
import com.bo.archive.model.InterArchiveDAO;
import com.bo.archive.model.ReportVO;
import com.bo.archive.model.VacationVO;
import com.bo.payment_report.model.ExReportVO;

@Service
public class ArchiveService implements InterArchiveService {
	
	@Autowired
	private InterArchiveDAO dao;

/////////////////////////////////////////////////////////////////////////////////////////////
// 내가 쓴 보고서 함
// 검색조건이 없을경우 내가 쓴 리포트 총 게시물 수 (totalCount)
@Override
public int getmyReportTotalCountWithNoSearch(HashMap<String, String> paraMap) {
int count = dao.getmyReportTotalCountWithNoSearch(paraMap);
return count;
}
// 검색조건이 있을경우 내가 쓴 리포트 총 게시물 수 (totalCount)
@Override
public int getmyReportTotalCountWithSearch(HashMap<String, String> paraMap) {
int count = dao.getmyReportTotalCountWithSearch(paraMap);
return count;
}
// 페이징 한 내가 쓴 보고서 목록
@Override
public List<HashMap<String, String>> myWriteReportListWithPaging(HashMap<String, String> paraMap) {
List<HashMap<String, String>> myReportList = dao.myWriteReportListWithPaging(paraMap);
return myReportList;
}


// 보고서 수신함 
// 검색조건이 없을경우 내가 받은 리포트 총 게시물 수 (totalCount)
@Override
public int getreceiveReportTotalCountWithNoSearch(HashMap<String, String> paraMap) {
int count = dao.getreceiveReportTotalCountWithNoSearch(paraMap);
return count;
}
// 검색조건이 있을경우 내가 받은 리포트 총 게시물 수 (totalCount)
@Override
public int getreceiveReportTotalCountWithSearch(HashMap<String, String> paraMap) {
int count = dao.getreceiveReportTotalCountWithSearch(paraMap);
return count;
}
// 페이징 한 내가 받은 보고서 목록
@Override
public List<HashMap<String, String>> receiveWriteReportListWithPaging(HashMap<String, String> paraMap) {
List<HashMap<String, String>> receiveReportList = dao.receiveWriteReportListWithPaging(paraMap);
return receiveReportList;
}



////////////////////////////////////////////////////////////////////////////////////////////////////////////


	
	//////////////=== 결재문서함 ===  //////////////
	
	// === '미결재' 문서 목록 보여주기 ===
	// 검색조건이 없을경우 미결재 문서 총 게시물 수 (totalCount)
	@Override
	public int getIncompleteDocumentTotalCountWithNoSearch(HashMap<String, String> paraMap) {
		int count = dao.getIncompleteDocumentTotalCountWithNoSearch(paraMap);
		return count;
	}
	// 검색조건이 '있'을경우 미결재 문서 총 게시물 수 (totalCount)
	@Override
	public int getIncompleteDocumentTotalCountWithSearch(HashMap<String, String> paraMap) {
		int count = dao.getIncompleteDocumentTotalCountWithSearch(paraMap);
		return count;
	}
	// 페이징처리 한 미결재 문서 목록 가져오기
	@Override
	public List<HashMap<String, String>> incompleteDocumentListWithPaging(HashMap<String, String> paraMap) {
		List<HashMap<String, String>> incompleteDocumentList = dao.incompleteDocumentListWithPaging(paraMap);
		return incompleteDocumentList;
	}
	
	
	
	
	
	// === 내가 받은 (내가 결재권자인) 결재문서 수신함  목록 보여주기 ===
	// 검색조건이 없을경우 내 결재문서 총 게시물 수 (totalCount)
	@Override
	public int getreceivePaymentDocumentTotalCountWithNoSearch(HashMap<String, String> paraMap) {
		int count = dao.getreceivePaymentDocumentTotalCountWithNoSearch(paraMap);
		return count;
	}
	// 검색조건이 '있'을경우 내 결재문서 총 게시물 수 (totalCount)
	@Override
	public int getreceivePaymentDocumentTotalCountWithSearch(HashMap<String, String> paraMap) {
		int count = dao.getreceivePaymentDocumentTotalCountWithSearch(paraMap);
		return count;
	}
	// 페이징처리 한 내 결재문서 목록 가져오기
	@Override
	public List<HashMap<String, String>> receiveDocumentListWithPaging(HashMap<String, String> paraMap) {
		List<HashMap<String, String>> receiveDocumentList = dao.receiveDocumentListWithPaging(paraMap);
		return receiveDocumentList;
	}
	
	
	
	
	
	
// == 여기서부터 상세보기 ==============================================================================================//	
	
	// 지출 결재자 인원 알아오기
	@Override
	public int approverPeoples(String textnumber) {
		int peoples = dao.approverPeoples(textnumber);
		return peoples;
	}
	
	
	// 휴가 신청서 결재자 인원 알아오기
	@Override
	public int approverLeavePeoples(String textnumber) {
		int leavePeople = dao.approverLeavePeoples(textnumber);
		return leavePeople;
	}
	
	
	// 지출결의서 상세보기
	@Override
	public List<HashMap<String, String>> detailList(HashMap<String, String> paraMap) {
		List<HashMap<String, String>> detailList = dao.detailList(paraMap);
		return detailList;
	}
	
	// 지출 결재란 ajax
	@Override
	public List<HashMap<String, String>> appAjax(String number) {
		List<HashMap<String, String>> appMap = dao.appAjax(number);
		return appMap;
	}
	
	// 지출 결재서 다운로드
	@Override
	public ExReportVO getViewWithNoAddCount(String textnumber) {
		ExReportVO exreportvo = dao.getView(textnumber);
		return exreportvo;
	}
	
	// 휴가 신청서 상세보기
	@Override
	public List<HashMap<String, String>> detaiLeavelList(HashMap<String, String> paraMap) {
		List<HashMap<String, String>> detailList = dao.detaiLeavelList(paraMap);
		return detailList;
	}
	
	// 휴가 결재란 ajax
	@Override
	public List<HashMap<String, String>> appLeaveAjax(String number) {
		List<HashMap<String, String>> appMap = dao.appLeaveAjax(number);
		return appMap;
	}
	
	// 휴가 지출서 파일 다운로드
	@Override
	public ExReportVO getleavedownload(String textnumber) {
		ExReportVO exreportvo = dao.getleavedownload(textnumber);
		return exreportvo;
	}
	
	// 지출 결재 후 업데이트
	@Override
	public int approverSuccess(HashMap<String, String> paraMap) {
		int result = dao.approverSuccess(paraMap);
		return result;
	}
	
	// 휴가 신청서 결재 후 업데이트
	@Override
	public int approverLeaveSuccess(HashMap<String, String> paraMap) {
		int result = dao.approverLeaveSuccess(paraMap);
		return result;
	}
	
	// 결재완료 테이블 update
	@Override
	public int updateApprover(HashMap<String, String> paraMap) {
		int n = dao.updateApprover(paraMap);
		return n;
	}
	
	// 결재완료 테이블 update
	@Override
	public int updateLeaveApprover(HashMap<String, String> paraMap) {
		int m = dao.updateLeaveApprover(paraMap);
		return m;
	}
	
	// 결재완료 상태를 나타내기 위한 ajax
	@Override
	public String approverFindAjax(String textnumber) {
		String result = dao.approverFindAjax(textnumber);
		return result;
	}
	
	//결재완료 ajax 2번째 지출 결의서 개수 알아오기
	@Override
	public String approverFindAjaxTwo(String textnumber) {
		String result = dao.approverFindAjaxTwo(textnumber);
		return result;
	}
	//결재완료 ajax 3번째 휴가 신청서 개수 알아오기.
	@Override
	public String approverFindAjaxThree(String textnumber) {
		String result = dao.approverFindAjaxThree(textnumber);
		return result;
	}
	
	
	

}	// End--------------------------------------------------------------------










