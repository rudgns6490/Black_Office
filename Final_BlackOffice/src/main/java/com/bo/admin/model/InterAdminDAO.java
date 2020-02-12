package com.bo.admin.model;

import java.util.HashMap;
import java.util.List;

import com.bo.board.model.BoardVO;
import com.bo.board.model.DeptBoardVO;
import com.bo.member.model.MemberVO;

public interface InterAdminDAO {
	
	/* 관리자 메뉴 관련 인터페이스 DAO  2020/01/15 lbh */
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 메인 ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	List<BoardVO> boardListWithPaging(); // 메인에 공지사항 게시판 데이터 넘기기 2020/02/05 KYH
	List<DeptBoardVO> deptBoardListWithPaging(); // 메인에 공지사항 게시판 데이터 넘기기 2020/02/05 KYH
	
	List<HashMap<String, String>> textReportShow(String employeeno);	// 보고서 데이터 메인에 보여주기
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 로그인 ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	MemberVO getLoginMember(HashMap<String, String> paraMap); //로그인하기
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 통계(차트) ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	int getTotalCountExpenditure(); // 지출결재 테이블 총 개수 가져오기
	int getTotalCountExpenditureSearch(HashMap<String, String> paraMap); // 지출결재 테이블 검색조건의 총 개수 가져오기
	List<HashMap<String, String>> getadmintotalexpenditure(HashMap<String, String> paraMap); // 지출통계에 데이터 가져오기 (select)
	
	List<HashMap<String, String>> getadmintotaldepartment(); // 부서통계 테이블 데이터 가져오기 (select)
	List<String> departmentList(); // 부서통계 테이블에 트랜잭션으로 부서명 가져오기 [ OR ]  // 지출통계에 부서명 가져오기
	
	List<HashMap<String, String>> getadmintotalposition(); // 직위통계 테이블 데이터 가져오기 (select)
	List<String> positionList(); // 직위통계 테이블에 트랜잭션으로 직위명 가져오기 [ OR ]  // 지출통계에 부서명 가져오기
	
	// ▼ 차트그리기
	List<HashMap<String, String>> departmentstatistics(); // 부서별 인원수 및 인원퍼센티지 조회
	List<HashMap<String, String>> positionstatistics(); // 직위별 인원수 및 인원퍼센티지 조회
	// ▲ 차트그리기
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 인사이동 ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	List<HashMap<String, String>> selectSawon(HashMap<String, String> paraMap);	//인사이동 할 사원 검색
	
	int retirementSawon(HashMap<String, String> employeeno);	// 퇴사처리 사원 테이블 status 0 으로 변경

	int addListRetirement(HashMap<String, String> paraMap);	// 인사이동 내역에 insert
	
	int moveDepartment(HashMap<String, String> paraMap);	// 부서 변경 처리

	int addListMoveDepartment(HashMap<String, String> paraMap);	// 부서변경 내역 저장
	
	int moveposition(HashMap<String, String> paraMap);		//진급 처리
	
	int addListMovePosition(HashMap<String, String> paraMap);	// 진급 내역 저장
	
	int leaveofabsence(HashMap<String, String> paraMap);		//휴직처리
	
	int addListleaveofabsence(HashMap<String, String> paraMap);	//휴직 내역 저장
	
	int reappoint(HashMap<String, String> paraMap);			// 복직 처리
	
	int addListreappoint(HashMap<String, String> paraMap);	//복직 내역 저장
	
	List<HashMap<String, String>> retirementList(HashMap<String, String> paraMap);		//퇴사 내역 조회
	
	List<HashMap<String, String>> movedepartmentList(HashMap<String, String> paraMap);	//부서 이동 내역 조회
	
	List<HashMap<String, String>> movePositionList(HashMap<String, String> paraMap);	//진급 내역 조회
	
	List<HashMap<String, String>> leaveofAbsenceList(HashMap<String, String> paraMap);	//휴직 내역 조회
	
	List<HashMap<String, String>> reappointList(HashMap<String, String> paraMap);	// 복직 내역 조회
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 입사처리/인사 ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	int register(MemberVO mvo); //신규 입사 등록 2020/01/15 lbh
	int attach_register(MemberVO mvo);	//사진 첨부 신규 입사 등록 2020/01/20 KKH
	boolean idDuplicateCheck(String id); // 아이디 중복체크 2020/01/30 LBH
	
	int revision(MemberVO mvo); // 내 정보 수정 2020/01/31/금 LBH
	int attach_revision(MemberVO mvo); // 이미지 첨부한 내 정보 수정 2020/01/31/금 LBH
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 주소록 ( 개인 / 공용 ) ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	int registerPersonal(AddressbookVO abvo); // 개인주소록 추가등록 2020/01/16 LBH

	String getPositionname(int fk_positionno);	//직위 이름 얻어오기

	String getDepartmentname(int fk_departmentno);	//부서 이름 얻어오기

	int addressRegister(AddressbookVO addressbookvo);	// 신규 입사 등록 시 공용주소록 추가 트랜잭션 처리

	int getTotalCountWithNoSearch();	//검색어가 없는 공용주소록 갯수 구하기

	int getTotalCountWithSearch(HashMap<String, String> paraMap); // 검색어가 있는 공용주소록 갯수 구하기

	List<AddressbookVO> abvoListWithPaging(HashMap<String, String> paraMap); // 페이징 처리한 공용 주소록 목록 구하기

	int getTotalCountWithNoSearch2(String employeeno);	//검색어 없는 개인 주소록 갯수 구하기
		
	int getTotalCountWithSearch2(HashMap<String, String> paraMap);		//검색어가 있는 개인 주소록 갯수 구하기

	List<AddressbookVO> abvoListWithPaging2(HashMap<String, String> paraMap);	//페이징 처리한 개인 주소록 목록 구하기

	List<AddressbookVO> abvoList(HashMap<String, String> paraMap);		// 개인 주소록 목록 조회

	int getFk_epmplyeeno(String id);		//사원 번호 얻어오기

	int removeOpenAddressbookList(HashMap<String, String> paraMap);		//퇴사 시 공용 주소록 삭제 트랜잭션 처리

	int deleteaddressbookList(String string);		//선택 된 개인 주소록 삭제하기
	

	

	

	

	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////

	
}
