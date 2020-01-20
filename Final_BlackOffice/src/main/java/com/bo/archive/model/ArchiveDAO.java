package com.bo.archive.model;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArchiveDAO implements InterArchiveDAO {

	@Autowired
	private SqlSessionTemplate sqlsession;
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	// 내가 쓴 보고서 함
	// 검색조건이 없을경우 총 게시물 수 (totalCount)
	@Override
	public int getReportTotalCountWithNoSearch() {
		int count = sqlsession.selectOne("archive.getReportTotalCountWithNoSearch");
		return count;
	}
	// 검색조건이 있을경우 총 게시물 수 (totalCount)
	@Override
	public int getReportTotalCountWithSearch(HashMap<String, String> paraMap) {
		int count = sqlsession.selectOne("archive.getReportTotalCountWithSearch", paraMap);
		return count;
	}
	// 페이징 한 내가 쓴 보고서 목록
	@Override
	public List<ReportVO> myWriteReportListWithPaging(HashMap<String, String> paraMap) {
		List<ReportVO> myReportList = sqlsession.selectList("archive.myWriteReportListWithPaging", paraMap);
		return myReportList;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	

}	// End----------------------------------------------------------------------------
