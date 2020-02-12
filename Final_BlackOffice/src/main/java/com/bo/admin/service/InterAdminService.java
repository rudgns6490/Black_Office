package com.bo.admin.service;

import java.util.HashMap;
import java.util.List;

import com.bo.board.model.DeptBoardVO;
import com.bo.admin.model.AddressbookVO;
import com.bo.board.model.BoardVO;
import com.bo.member.model.MemberVO;

public interface InterAdminService {
	
	/* 관리자 메뉴 관련 인터페이스 서비스  2020/01/15 LBH */
	
	//메인화면
	
	List<BoardVO> boardListWithPaging();			//메인화면 공지사항 가져오기

	List<DeptBoardVO> deptBoardListWithPaging();	//메인화면 업무게시판 가져오기
	
	List<HashMap<String, String>> textReportShow(String employeeno);		//보고서 데이터 메인에 보여주기
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 로그인 ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	MemberVO getLoginMember(HashMap<String, String> paraMap);	//로그인 시도
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 통계(차트) ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	

	int getTotalCountExpenditure(); // 지출결재 테이블 총 개수 가져오기
	int getTotalCountExpenditureSearch(HashMap<String, String> paraMap); // 지출결재 테이블 검색조건의 총 개수 가져오기
	List<HashMap<String, String>> getadmintotalexpenditure(HashMap<String, String> paraMap); // 지출통계에 데이터 가져오기 (select)
	List<String> departmentList(); // 지출통계에 부서명 가져오기
	List<String> positionList(); // 지출통계에 직위명 가져오기
	
	List<HashMap<String, String>> getadmintotaldepartment(); // 부서통계 테이블 데이터 가져오기 (select)
	
	List<HashMap<String, String>> getadmintotalposition(); // 직위통계 테이블 데이터 가져오기 (select)
	
	// ▼ 차트그리기
	List<HashMap<String, String>> departmentstatistics(); // 부서별 인원수 및 인원퍼센티지 조회 
	List<HashMap<String, String>> positionstatistics(); // 직위별 인원수 및 인원퍼센티지 조회
	// ▲ 차트그리기
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 인사이동 ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	List<HashMap<String, String>> selectSawon(HashMap<String, String> paraMap);	// 인사이동할 사원 목록검색
	int tranRetirement(HashMap<String, String> paraMap);	//퇴사처리 트랜잭션처리해야됨
	int moveDepartment(HashMap<String, String> paraMap);	//부서변경 트랜잭션처리해야됨
	int moveposition(HashMap<String, String> paraMap);		//진급 트랜잭션처리해야함
	int leaveofabsence(HashMap<String, String> paraMap);	//휴직 트랜잭션처리해야함
	int reappoint(HashMap<String, String> paraMap);			//복직 트랜잭션처리해야함
	
	List<HashMap<String, String>> retirementList(HashMap<String, String> paraMap);	//퇴사 내역 조회
	List<HashMap<String, String>> movedepartmentList(HashMap<String, String> paraMap); // 부서 이동 내역 조회
	List<HashMap<String, String>> movePositionList(HashMap<String, String> paraMap);	//진급 내역 조회
	List<HashMap<String, String>> leaveofAbsenceList(HashMap<String, String> paraMap);	//휴직 내역 조회
	List<HashMap<String, String>> reappointList(HashMap<String, String> paraMap);		//복직 내역 조회
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 입사처리/인사 ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	int register(MemberVO mvo, AddressbookVO addressbookvo); //신규 입사 등록 2020/01/15 LBH
	int attach_register(MemberVO mvo, AddressbookVO addressbookvo); //이미지 첨부한 신규 입사 등록 2020/01/20 KKH
	boolean idDuplicateCheck(String id); // 아이디 중복체크 2020/01/30 LBH
	
	int revision(MemberVO mvo); // 내 정보 수정 2020/01/31/금 LBH
	int attach_revision(MemberVO mvo); // 이미지 첨부한 내 정보 수정 2020/01/31/금 LBH
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 주소록 ( 개인 / 공용 ) ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////

	int registerPersonal(AddressbookVO abvo); // 개인주소록 추가등록 2020/01/16 LBH

	int getTotalCountWithNoSearch();	// 검색어가 없을 때 공용주소록 갯수 구하기 

	int getTotalCountWithSearch(HashMap<String, String> paraMap);	// 검색어가 있을 때 공용주소록 갯수 구하기 

	List<AddressbookVO> abvoListWithPaging(HashMap<String, String> paraMap);		//페이징 처리한 공용주소록 리스트 구하기

	int getTotalCountWithNoSearch2(String employeeno); //검색어가 없을 때 개인 주소록 갯수 구하기

	int getTotalCountWithSearch2(HashMap<String, String> paraMap); // 검색어가 있을 때 개인 주소록 갯수 구하기

	List<AddressbookVO> abvoListWithPaging2(HashMap<String, String> paraMap); // 페이징 처리한 개인 주소록 리스트 구하기

	List<AddressbookVO> abvoList(HashMap<String, String> paraMap);		// 개인주소록 목록구하기

	int deleteaddressbookList(List<String> nameList);		//선택된 개인 주소록 삭제

	


	

	

}





