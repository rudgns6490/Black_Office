package com.bo.archive.service;

import java.util.HashMap;
import java.util.List;

import com.bo.archive.model.ReportVO;

public interface InterArchiveService {

	// 내가 쓴보고서 함
	int getReportTotalCountWithNoSearch();											// 검색조건이 없을경우 내가 쓴 리포트 총 게시물 수 (totalCount)
	int getReportTotalCountWithSearch(HashMap<String, String> paraMap);				// 검색조건이 있을경우 내가 쓴 리포트 총 게시물 수 (totalCount)
	List<ReportVO> myWriteReportListWithPaging(HashMap<String, String> paraMap);	// 페이징 한 내가 쓴 보고서 목록

}
