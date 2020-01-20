package com.bo.archive.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bo.archive.model.InterArchiveDAO;
import com.bo.archive.model.ReportVO;

@Service
public class ArchiveService implements InterArchiveService {
	
	@Autowired
	private InterArchiveDAO dao;

	/////////////////////////////////////////////////////////////////////////////////////////////
	// 내가 쓴 보고서 함
	// 검색조건이 없을경우 내가 쓴 리포트 총 게시물 수 (totalCount)
	@Override
	public int getReportTotalCountWithNoSearch() {
		int count = dao.getReportTotalCountWithNoSearch();
		return count;
	}
	// 검색조건이 있을경우 내가 쓴 리포트 총 게시물 수 (totalCount)
	@Override
	public int getReportTotalCountWithSearch(HashMap<String, String> paraMap) {
		int count = dao.getReportTotalCountWithSearch(paraMap);
		return count;
	}
	// 페이징 한 내가 쓴 보고서 목록
	@Override
	public List<ReportVO> myWriteReportListWithPaging(HashMap<String, String> paraMap) {
		List<ReportVO> myReportList = dao.myWriteReportListWithPaging(paraMap);
		return myReportList;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////
	

}	// End--------------------------------------------------------------------
