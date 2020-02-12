package com.bo.admin.model;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bo.board.model.BoardVO;
import com.bo.board.model.DeptBoardVO;
import com.bo.member.model.MemberVO;

//=== DAO 선언 ===
@Repository
public class AdminDAO implements InterAdminDAO {
	
	/* 관리자 메뉴 관련 DAO  2020/01/15 lbh */
	
	// === 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private SqlSessionTemplate sqlsession;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 메인 ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 메인에 공지사항 게시판 데이터 넘기기 2020/02/05 KYH
	@Override
	public List<BoardVO> boardListWithPaging() {
	List<BoardVO> boardList = sqlsession.selectList("admin.mainboardListWithPaging");
	return boardList;
	}
	
	@Override
	public List<DeptBoardVO> deptBoardListWithPaging() {
	List<DeptBoardVO> deptboardList = sqlsession.selectList("admin.maindeptBoardListWithPaging");
	return deptboardList;
	}
	
	// 보고서 데이터 메인에 보여주기
	@Override
	public List<HashMap<String, String>> textReportShow(String employeeno) {
		List<HashMap<String, String>> map = sqlsession.selectList("admin.textReportShow", employeeno);
		return map;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 로그인 ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//로그인하기
	@Override
	public MemberVO getLoginMember(HashMap<String, String> paraMap) {
		MemberVO loginuser = sqlsession.selectOne("admin.getLoginMember", paraMap);
		return loginuser;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 통계(차트) ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	

	// 지출결재 테이블 총 개수 가져오기
	@Override
	public int getTotalCountExpenditure() {
		int getTotalCountExpenditure = sqlsession.selectOne("admin.getTotalCountExpenditure");
		return getTotalCountExpenditure;
	}
	
	// 지출결재 테이블 검색조건의 총 개수 가져오기
	@Override
	public int getTotalCountExpenditureSearch(HashMap<String, String> paraMap) {
		int getTotalCountExpenditureSearch = sqlsession.selectOne("admin.getTotalCountExpenditureSearch", paraMap);
		return getTotalCountExpenditureSearch;
	}
	
	// 지출통계에 데이터 가져오기 (select)
	@Override
	public List<HashMap<String, String>> getadmintotalexpenditure(HashMap<String, String> paraMap) {
		List<HashMap<String, String>> expenditure = sqlsession.selectList("admin.getadmintotalexpenditure", paraMap);
//			System.out.println("paraMap : " + paraMap);
//			System.out.println("expenditure : " + expenditure);
		return expenditure;
	}
	
	// 부서통계 테이블 데이터 가져오기 (select)
	@Override
	public List<HashMap<String, String>> getadmintotaldepartment() {
		List<HashMap<String, String>> department = sqlsession.selectList("admin.getadmintotaldepartment"); 
		return department;
	}
	// 부서통계 테이블에 트랜잭션으로 부서명 가져오기
	@Override
	public List<String> departmentList() {
		List<String> departmentList = sqlsession.selectList("admin.departmentList");
		return departmentList;
	}

	
	// 직위통계 테이블 데이터 가져오기 (select)
	@Override
	public List<HashMap<String, String>> getadmintotalposition() {
		List<HashMap<String, String>> position = sqlsession.selectList("admin.getadmintotalposition");
		return position;
	}
	// 직위통계 테이블에 트랜잭션으로 직위명 가져오기
	@Override
	public List<String> positionList() {
		List<String> positionList = sqlsession.selectList("admin.positionList");
		return positionList;
	}
	
		
	// ▼ 차트그리기
	// 부서별 인원수 및 인원퍼센티지 조회
	@Override
	public List<HashMap<String, String>> departmentstatistics() {
		List<HashMap<String, String>> deptnamePercentageList = sqlsession.selectList("admin.departmentstatistics");
		return deptnamePercentageList;
	}
	
	// 직위별 인원수 및 인원퍼센티지 조회
	@Override
	public List<HashMap<String, String>> positionstatistics() {
		List<HashMap<String, String>> positionstatistics = sqlsession.selectList("admin.positionstatistics");
		return positionstatistics;
	}

	// ▲ 차트그리기
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 인사이동 ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//인사 이동할 사원 검색
	@Override
	public List<HashMap<String, String>> selectSawon(HashMap<String, String> paraMap) {
		
		List<HashMap<String, String>> mapList = sqlsession.selectList("admin.selectSawon", paraMap);
		return mapList;
	}
	
	// 사원 퇴사처리
	@Override
	public int retirementSawon(HashMap<String, String> paraMap) {
		int n = sqlsession.update("admin.retirementSawon", paraMap);
		return n;
	}

	//인사이동 목록에 퇴사내역 추가
	@Override
	public int addListRetirement(HashMap<String, String> paraMap) {
		int n = sqlsession.insert("admin.addListRetirement", paraMap);
		return n;
	}
	
	//부서변경 처리
	@Override
	public int moveDepartment(HashMap<String, String> paraMap) {
		int n = sqlsession.update("admin.moveDepartment", paraMap);
		return n;
	}

	//인사이동 목록에 부서변경 내역 추가
	@Override
	public int addListMoveDepartment(HashMap<String, String> paraMap) {
		int n = sqlsession.insert("admin.addListMoveDepartment", paraMap);
		return n;
	}
	
	//진급 처리
	@Override
	public int moveposition(HashMap<String, String> paraMap) {
		int n = sqlsession.update("admin.moveposition", paraMap);
		return n;
	}

	//진급 내역 저장
	@Override
	public int addListMovePosition(HashMap<String, String> paraMap) {
		int n = sqlsession.insert("admin.addListMovePosition", paraMap);
		return n;
	}
	
	//휴직처리
	@Override
	public int leaveofabsence(HashMap<String, String> paraMap) {
		int n = sqlsession.update("admin.leaveofabsence", paraMap);
		return n;
	}

	//휴직 내역 저장
	@Override
	public int addListleaveofabsence(HashMap<String, String> paraMap) {
		int n = sqlsession.insert("admin.addListleaveofabsence", paraMap);
		return n;
	}
	
	//복직 처리
	@Override
	public int reappoint(HashMap<String, String> paraMap) {
		int n = sqlsession.update("admin.reappoint", paraMap);
		return n;
	}

	// 복직 내역 저장
	@Override
	public int addListreappoint(HashMap<String, String> paraMap) {
		int n = sqlsession.insert("admin.addListreappoint", paraMap);
		return n;
	}
	
	//퇴사 내역 조회
	@Override
	public List<HashMap<String, String>> retirementList(HashMap<String, String> paraMap) {
		List<HashMap<String, String>> retirementList = sqlsession.selectList("admin.retirementList", paraMap);
		return retirementList;
	}
	
	//부서 이동 내역 조회
	@Override
	public List<HashMap<String, String>> movedepartmentList(HashMap<String, String> paraMap) {
		List<HashMap<String, String>> movedepartmentList = sqlsession.selectList("admin.movedepartmentList", paraMap);
		return movedepartmentList;
	}
	
	//진급 내역 조회
	@Override
	public List<HashMap<String, String>> movePositionList(HashMap<String, String> paraMap) {
		List<HashMap<String, String>> movePositionList = sqlsession.selectList("admin.movePositionList", paraMap);
		return movePositionList;
	}
	
	//휴직 내역 조회
	@Override
	public List<HashMap<String, String>> leaveofAbsenceList(HashMap<String, String> paraMap) {
		List<HashMap<String, String>> leaveofAbsenceList = sqlsession.selectList("admin.leaveofAbsenceList", paraMap);
		return leaveofAbsenceList;
	}

	//복직 내역 조회
	@Override
	public List<HashMap<String, String>> reappointList(HashMap<String, String> paraMap) {
		List<HashMap<String, String>> reappointList = sqlsession.selectList("admin.reappointList", paraMap);
		return reappointList;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 입사처리/인사 ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//신규 입사 등록 2020/01/15 lbh
	@Override
	public int register(MemberVO mvo) {
		int register = sqlsession.insert("admin.register", mvo);
		return register;
	}
	
	//사진 첨부 신규 입사 등록
	@Override
	public int attach_register(MemberVO mvo) {
		int register = sqlsession.insert("admin.attach_register", mvo);
		return register;
	}
	
	// 아이디 중복체크 2020/01/30 LBH
	@Override
	public boolean idDuplicateCheck(String id) {
		boolean idDuplicateCheck = sqlsession.selectOne("admin.idDuplicateCheck", id);
		return idDuplicateCheck;
	}
	
	// 내 정보 수정 2020/01/31/금 LBH
	@Override
	public int revision(MemberVO mvo) {
		int revision = sqlsession.update("admin.revision", mvo);
		return revision;
	}

	// 이미지 첨부한 내 정보 수정 2020/01/31/금 LBH
	@Override
	public int attach_revision(MemberVO mvo) {
		int attach_revision = sqlsession.update("admin.attach_revision", mvo);
		return attach_revision;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 주소록 ( 개인 / 공용 ) ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 개인주소록 추가등록 2020/01/16 LBH
	@Override
	public int registerPersonal(AddressbookVO abvo) {
		int registerPersonal = sqlsession.insert("admin.registerPersonal", abvo);
		return registerPersonal;
	}

	//직위 이름 구하기
	@Override
	public String getPositionname(int fk_positionno) {
		String fk_positionname = sqlsession.selectOne("admin.getPositionname", fk_positionno);
		return fk_positionname;
	}

	//부서 이름 구하기
	@Override
	public String getDepartmentname(int fk_departmentno) {
		String fk_departmentname = sqlsession.selectOne("admin.getDepartmentname", fk_departmentno);
		return fk_departmentname;
	}

	// 신규 사원 등록 시 공용 주소록 추가 트랜잭션 처리
	@Override
	public int addressRegister(AddressbookVO addressbookvo) {
		int m = sqlsession.insert("admin.addressRegister", addressbookvo);
		return m;
	}

	// 검색어가 없는 공용 주소록 갯수 구하기
	@Override
	public int getTotalCountWithNoSearch() {
		int count = sqlsession.selectOne("admin.getTotalCountWithNoSearch");
		return count;
	}

	// 검색어가 있는 공용 주소록 갯수 구하기
	@Override
	public int getTotalCountWithSearch(HashMap<String, String> paraMap) {
		int count = sqlsession.selectOne("admin.getTotalCountWithSearch", paraMap);
		return count;
	}

	//페이징 처리한 공용주소록 주소 갯수 구하기
	@Override
	public List<AddressbookVO> abvoListWithPaging(HashMap<String, String> paraMap) {
		List<AddressbookVO> abvoList = sqlsession.selectList("admin.abvoListWithPaging", paraMap);
		return abvoList;
	}

	//검색어가 없는 개인 주소록 갯수 구하기
	@Override
	public int getTotalCountWithNoSearch2(String employeeno) {
		int count = sqlsession.selectOne("admin.getTotalCountWithNoSearch2", employeeno);
		return count;
	}

	//검색어가 있는 개인 주소록 갯수 구하기
	@Override
	public int getTotalCountWithSearch2(HashMap<String, String> paraMap) {
		int count = sqlsession.selectOne("admin.getTotalCountWithSearch2", paraMap);
		return count;
	}

	// 페이징 처리한 개인 주소록 목록 구하기
	@Override
	public List<AddressbookVO> abvoListWithPaging2(HashMap<String, String> paraMap) {
		List<AddressbookVO> abvoList = sqlsession.selectList("admin.abvoListWithPaging2", paraMap);
		return abvoList;
	}

	//개인 주소록 목록 조회
	@Override
	public List<AddressbookVO> abvoList(HashMap<String, String> paraMap) {
		List<AddressbookVO> abvoList = sqlsession.selectList("admin.abvoList", paraMap);
		return abvoList;
	}

	//사원 번호 얻어오기
	@Override
	public int getFk_epmplyeeno(String id) {
		int fk_employeeno = sqlsession.selectOne("admin.getFk_epmplyeeno", id);
		return fk_employeeno;
	}

	//퇴사 시 공용 주소록 삭제
	@Override
	public int removeOpenAddressbookList(HashMap<String, String> paraMap) {
		int retirement = sqlsession.delete("admin.removeOpenAddressbookList", paraMap);
		return retirement;
	}

	//선택 된 개인 주소록 삭제
	@Override
	public int deleteaddressbookList(String string) {

		int n = sqlsession.delete("admin.deleteaddressbookList", string);
		return n;
	}


	


	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////

	
	
}
