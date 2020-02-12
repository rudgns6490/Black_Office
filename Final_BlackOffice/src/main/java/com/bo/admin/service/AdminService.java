package com.bo.admin.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bo.admin.model.AddressbookVO;
import com.bo.admin.model.InterAdminDAO;
import com.bo.board.model.BoardVO;
import com.bo.board.model.DeptBoardVO;
import com.bo.common.AES256;
import com.bo.member.model.MemberVO;


//=== Service 선언 ===
@Service 
public class AdminService implements InterAdminService{
	
	/* 관리자 메뉴 관련 서비스  2020/01/15 LBH */
	
	// === 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private InterAdminDAO dao;
	
	// === #45. 양방향 암호화 알고리즘인 AES256를 사용하여 암호화/복호화 하기위한 클래스 의존객체 주입하기 (DI: Dependency Injection)
	@Autowired
	private AES256 aes;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 메인 ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 메인에 공지사항 게시판 데이터 넘기기 2020/02/05 KYH
	@Override
	public List<BoardVO> boardListWithPaging() {
	List<BoardVO> boardListWithPaging = dao.boardListWithPaging();
	return boardListWithPaging;
	}
	
	// 메인에 공지사항 게시판 데이터 넘기기 2020/02/05 KYH
	@Override
	public List<DeptBoardVO> deptBoardListWithPaging() {
	List<DeptBoardVO> deptBoardListWithPaging = dao.deptBoardListWithPaging();
	return deptBoardListWithPaging;
	}
	
	// 보고서 데이터 메인에 보여주기
	@Override
	public List<HashMap<String, String>> textReportShow(String employeeno) {
		List<HashMap<String, String>> map = dao.textReportShow(employeeno);
		return map;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 로그인 ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 로그인 시도
	@Override
	public MemberVO getLoginMember(HashMap<String, String> paraMap) {
		MemberVO loginuser =  dao.getLoginMember(paraMap);
		return loginuser;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 통계(차트) ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	

	// 지출결재 테이블 총 개수 가져오기
	@Override
	public int getTotalCountExpenditure() {
		int getTotalCountExpenditure = dao.getTotalCountExpenditure();
		return getTotalCountExpenditure;
	}
	
	// 지출결재 테이블 검색조건의 총 개수 가져오기
	@Override
	public int getTotalCountExpenditureSearch(HashMap<String, String> paraMap) {
		
		String expenditureDepartment = dao.getDepartmentname(Integer.parseInt(paraMap.get("expenditureDepartment")));
		
		System.out.println("~~~expenditureDepartment: " + expenditureDepartment);
		
		paraMap.put("expenditureDepartment", expenditureDepartment);
		
		int getTotalCountExpenditureSearch = dao.getTotalCountExpenditureSearch(paraMap);
		return getTotalCountExpenditureSearch;
	}
	
	// 지출통계에 데이터 가져오기 (select)
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation= Isolation.READ_COMMITTED, rollbackFor={Throwable.class})
	public List<HashMap<String, String>> getadmintotalexpenditure(HashMap<String, String> paraMap) {
		
		/*String expenditureDepartment = dao.getDepartmentname(Integer.parseInt(paraMap.get("expenditureDepartment")));
		
		System.out.println("~~~expenditureDepartment: " + expenditureDepartment);
		
		paraMap.put("expenditureDepartment", expenditureDepartment);*/
		
		List<HashMap<String, String>> expenditure =  dao.getadmintotalexpenditure(paraMap);
//			System.out.println("paraMap : " + paraMap);
//			System.out.println("expenditure : " + expenditure);
		return expenditure;
	}
		
	// 지출통계에 부서명 가져오기
	@Override
	public List<String> departmentList() {
		List<String> departmentList = dao.departmentList();
		return departmentList;
	}
	
	// 지출통계에 직위명 가져오기
	@Override
	public List<String> positionList() {
		List<String> positionList = dao.positionList();
		return positionList;
	}
	
	// 부서통계 테이블 데이터 가져오기 (select)
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation= Isolation.READ_COMMITTED, rollbackFor={Throwable.class})
	public List<HashMap<String, String>> getadmintotaldepartment() {
		List<HashMap<String, String>> department = dao.getadmintotaldepartment();
		List<String> departmentList = dao.departmentList(); // 부서통계 테이블에 트랜잭션으로 부서명 가져오기 
		
		// department 에 departmentList 데이터(부서명) 값 넣어주기
		for(int i=0; i<department.size(); i++) {
			department.get(i).put("departmentname", departmentList.get(i));
		}
		
		return department;
	}
	
	// 직위통계 테이블 데이터 가져오기 (select)
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation= Isolation.READ_COMMITTED, rollbackFor={Throwable.class})
	public List<HashMap<String, String>> getadmintotalposition() {
		List<HashMap<String, String>> position = dao.getadmintotalposition();
		List<String> positionList = dao.positionList(); // 직위통계 테이블에 트랜잭션으로 직위명 가져오기
		
		// position 에 positionList 데이터(직위명) 값 넣어주기
		for(int i=0; i<position.size(); i++) {
			position.get(i).put("POSITIONNAME", positionList.get(i));
		}
		
		return position;
	}
	
	// ▼ 차트그리기
	// 부서별 인원수 및 인원퍼센티지 조회
	@Override
	public List<HashMap<String, String>> departmentstatistics() {
		List<HashMap<String, String>> departmentstatistics = dao.departmentstatistics();
		List<String> departmentList = dao.departmentList(); // 부서통계 테이블에 트랜잭션으로 부서명 가져오기
		
		// departmentstatistics 에 departmentList 데이터(부서명) 값 넣어주기
		for(int i=0; i<departmentstatistics.size(); i++) {
			departmentstatistics.get(i).put("departmentname", departmentList.get(i));
		}
		
		return departmentstatistics;
	}
	
	// 직위별 인원수 및 인원퍼센티지 조회
	@Override
	public List<HashMap<String, String>> positionstatistics() {
		List<HashMap<String, String>> positionstatistics = dao.positionstatistics();
		List<String> positionList = dao.positionList(); // 직위통계 테이블에 트랜잭션으로 직위명 가져오기
		
		// position 에 positionList 데이터(직위명) 값 넣어주기
		for(int i=0; i<positionstatistics.size(); i++) {
			positionstatistics.get(i).put("POSITIONNAME", positionList.get(i));
		}
		return positionstatistics;
	}
	// ▲ 차트그리기
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 인사이동 ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//인사이동할 사원 목록 검색
	@Override
	public List<HashMap<String, String>> selectSawon(HashMap<String, String> paraMap) {
		List<HashMap<String, String>> mapList = dao.selectSawon(paraMap);
		return mapList;
	}
	
	//선택한 사원 퇴사 처리 하기
	//트랜잭션 처리
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation= Isolation.READ_COMMITTED, rollbackFor={Throwable.class})
	public int tranRetirement(HashMap<String, String> paraMap) {
		
		int retirement = dao.retirementSawon(paraMap);
		
		retirement = dao.addListRetirement(paraMap);
		
		retirement = dao.removeOpenAddressbookList(paraMap);
		
		return retirement;
		
	}
	
	//부서 변경하기
	//트랜잭션 처리
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation= Isolation.READ_COMMITTED, rollbackFor={Throwable.class})
	public int moveDepartment(HashMap<String, String> paraMap) {
		
		int n = dao.moveDepartment(paraMap);
		
		String movedepartmentname = dao.getDepartmentname(Integer.parseInt(paraMap.get("movedepartmentno")));
		paraMap.put("movedepartmentno", movedepartmentname);
		n = dao.addListMoveDepartment(paraMap);
		
		return n;
	}
	
	//진급 하기
	//트랜잭션처리
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation= Isolation.READ_COMMITTED, rollbackFor={Throwable.class})
	public int moveposition(HashMap<String, String> paraMap) {
		int n = dao.moveposition(paraMap);
		
		
		String movepositionname = dao.getPositionname(Integer.parseInt(paraMap.get("movepositionno")));
		
		paraMap.put("movepositionno", movepositionname);
		
		n = dao.addListMovePosition(paraMap);
		
		return n;
	}
	
	//휴직
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation= Isolation.READ_COMMITTED, rollbackFor={Throwable.class})
	public int leaveofabsence(HashMap<String, String> paraMap) {
		int n = dao.leaveofabsence(paraMap);
		n = dao.addListleaveofabsence(paraMap);
		
		return n;
	}
	
	//복직
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation= Isolation.READ_COMMITTED, rollbackFor={Throwable.class})
	public int reappoint(HashMap<String, String> paraMap) {
		int n = dao.reappoint(paraMap);
		n = dao.addListreappoint(paraMap);
		return n;
	}
	
	//퇴사 내역 조회
	@Override
	public List<HashMap<String, String>> retirementList(HashMap<String, String> paraMap) {
		List<HashMap<String, String>> retirementList = dao.retirementList(paraMap);
		return retirementList;
	}
	
	//부서 이동 내역 조회
	@Override
	public List<HashMap<String, String>> movedepartmentList(HashMap<String, String> paraMap) {
		List<HashMap<String, String>> movedepartmentList = dao.movedepartmentList(paraMap);
		return movedepartmentList;
	}
	
	//진급 내역 조회
	@Override
	public List<HashMap<String, String>> movePositionList(HashMap<String, String> paraMap) {
		List<HashMap<String, String>> movePositionList = dao.movePositionList(paraMap);
		return movePositionList;
	}
	
	//휴직 내역 조회
	@Override
	public List<HashMap<String, String>> leaveofAbsenceList(HashMap<String, String> paraMap) {
		List<HashMap<String, String>> leaveofAbsenceList = dao.leaveofAbsenceList(paraMap);
		return leaveofAbsenceList;
	}

	//복직 내역 조회
	@Override
	public List<HashMap<String, String>> reappointList(HashMap<String, String> paraMap) {
		List<HashMap<String, String>> reappointList = dao.reappointList(paraMap);
		return reappointList;
	}

	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 입사처리/인사 ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//신규 입사 등록 2020/01/15 LBH
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation= Isolation.READ_COMMITTED, rollbackFor={Throwable.class})
	public int register(MemberVO mvo, AddressbookVO addressbookvo) {
		
		int register = dao.register(mvo);
		
		int fk_employeeno = dao.getFk_epmplyeeno(mvo.getId());
		String positionname = dao.getPositionname(mvo.getFk_positionno());
		String departmentname = dao.getDepartmentname(mvo.getFk_departmentno());
		
		addressbookvo.setPositionname(positionname);
		addressbookvo.setDepartmentname(departmentname);
		addressbookvo.setFk_employeeno(fk_employeeno);
		
		System.out.println("positionname: " + positionname + ", departmentname: " + departmentname + ", fk_employeeno: " + fk_employeeno);
		
		register = dao.addressRegister(addressbookvo);
		return register;
	}
	
	//사진 첨부하는 신규 입사 등록 2020/01/20 KKH
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation= Isolation.READ_COMMITTED, rollbackFor={Throwable.class})
	public int attach_register(MemberVO mvo, AddressbookVO addressbookvo) {
		
		int register = dao.attach_register(mvo);
		
		int fk_employeeno = dao.getFk_epmplyeeno(mvo.getId());
		String positionname = dao.getPositionname(mvo.getFk_positionno());
		String departmentname = dao.getDepartmentname(mvo.getFk_departmentno());
		
		addressbookvo.setPositionname(positionname);
		addressbookvo.setDepartmentname(departmentname);
		addressbookvo.setFk_employeeno(fk_employeeno);
		
		System.out.println("positionname: " + positionname + ", departmentname: " + departmentname + ", fk_employeeno: " + fk_employeeno);
		
		register = dao.addressRegister(addressbookvo);
		return register;
	}
	
	// 아이디 중복체크 2020/01/30 LBH
	@Override
	public boolean idDuplicateCheck(String id) {
		boolean idDuplicateCheck = dao.idDuplicateCheck(id);
		System.out.println("idDuplicateCheck : " + idDuplicateCheck);
		return idDuplicateCheck;
	}
	
	// 내 정보 수정 2020/01/31/금 LBH
	@Override
	public int revision(MemberVO mvo) {
		int revision = dao.revision(mvo);
		return revision;
	}
	
	// 이미지 첨부한 내 정보 수정 2020/01/31/금 LBH
	@Override
	public int attach_revision(MemberVO mvo) {
		int attach_revision = dao.attach_revision(mvo);
		return attach_revision;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 주소록 ( 개인 / 공용 ) ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////

	// 개인주소록 추가등록 2020/01/16 LBH 
	@Override
	public int registerPersonal(AddressbookVO abvo) {
		int registerPersonal = dao.registerPersonal(abvo);
		return registerPersonal;
	}

	// 검색어가 없을 때 공용주소록 갯수 구하기
	@Override
	public int getTotalCountWithNoSearch() {
		int count = dao.getTotalCountWithNoSearch();
		return count;
	}

	// 검색어가 있을 때 공용주소록 갯수 구하기 
	@Override
	public int getTotalCountWithSearch(HashMap<String, String> paraMap) {
		int count = dao.getTotalCountWithSearch(paraMap);
		return count;
	}

	//페이징 처리한 공용주소록 리스트 구하기
	@Override
	public List<AddressbookVO> abvoListWithPaging(HashMap<String, String> paraMap) {
		List<AddressbookVO> abvoList = dao.abvoListWithPaging(paraMap);
		return abvoList;
	}

	//검색어가 없을 때 개인 주소록 갯수 구하기
	@Override
	public int getTotalCountWithNoSearch2(String employeeno) {
		int count = dao.getTotalCountWithNoSearch2(employeeno);
		return count;
	}

	//검색어가 있을 때 개인 주소록 갯수 구하기
	@Override
	public int getTotalCountWithSearch2(HashMap<String, String> paraMap) {
		int count = dao.getTotalCountWithSearch2(paraMap);
		return count;
	}

	//페이징 처리한 개인 주소록 리스트 구하기
	@Override
	public List<AddressbookVO> abvoListWithPaging2(HashMap<String, String> paraMap) {
		List<AddressbookVO> abvoList = dao.abvoListWithPaging2(paraMap);
		return abvoList;
	}

	//개인 주소록 목록구하기
	@Override
	public List<AddressbookVO> abvoList(HashMap<String, String> paraMap) {
		List<AddressbookVO> abvoList = dao.abvoList(paraMap);
		return abvoList;
	}

	//선택된 주소록 삭제
	@Override
	public int deleteaddressbookList(List<String> nameList) {
		
		int n = 0;
		
		for(int i=0; i<nameList.size(); i++) {
			n = dao.deleteaddressbookList(nameList.get(i));
		}
		
		return n;
	}

	

	

	




	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	

	
}
