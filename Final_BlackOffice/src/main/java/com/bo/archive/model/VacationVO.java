package com.bo.archive.model;

public class VacationVO {
	
	private String rno;							// 행번호(게시판에 글 No. 나타내기)
	private String vacationno;					// 휴가/휴직 문서번호
	private String fk_employeeno;				// 참조키 사원번호
	private String writeday;					// 작성일자
	private String startday;					// 시작일
	private String endday;						// 종료일
	private String title;						// 제목
	private String reason;						// 사유
	private String emergencycontactnetwork;		// 비상 연락망
	private String sharedepartmentno;			// 공유 부서번호   -- 공유 안하면 0
	private String status;						// 결재여부   -- 1이면 결재완료, 0이면 미결재
	private String approver;					// 결재자 사원번호
	private String departmentname;				// 부서명
	private String rank;						// 직급
	private String filename;					// WAS(톰캣)에 저장될 파일명(20190725092715353243254235235234.png)
	private String orgfilename;					// 진짜 파일명(강아지.png)
	private String filesize;					// 파일크기
	private String papersname;					// 문서분류(명)
	private String employeename;				// 사원명
	private String textnumber;				// 원래 문서번호 채번해온 것
//-------------------------------------------------------------------------------------------------------------
	
	public VacationVO() { }
	
	public VacationVO(String rno, String vacationno, String fk_employeeno, String writeday, String startday, String endday,
			String title, String reason, String emergencycontactnetwork, String sharedepartmentno, String status,
			String approver, String departmentname, String rank, String filename, String orgfilename, String filesize,
			String papersname, String employeename, String textnumber) {
		this.rno = rno;
		this.vacationno = vacationno;
		this.fk_employeeno = fk_employeeno;
		this.writeday = writeday;
		this.startday = startday;
		this.endday = endday;
		this.title = title;
		this.reason = reason;
		this.emergencycontactnetwork = emergencycontactnetwork;
		this.sharedepartmentno = sharedepartmentno;
		this.status = status;
		this.approver = approver;
		this.departmentname = departmentname;
		this.rank = rank;
		this.filename = filename;
		this.orgfilename = orgfilename;
		this.filesize = filesize;
		this.papersname = papersname;
		this.employeename = employeename;
		this.textnumber = textnumber;
	}
//-------------------------------------------------------------------------------------------------------------
	public String getRno() {
		return rno;
	}
	public void setRno(String rno) {
		this.rno = rno;
	}
	
	public String getVacationno() {
		return vacationno;
	}
	public void setVacationno(String vacationno) {
		this.vacationno = vacationno;
	}
	public String getFk_employeeno() {
		return fk_employeeno;
	}
	public void setFk_employeeno(String fk_employeeno) {
		this.fk_employeeno = fk_employeeno;
	}
	public String getWriteday() {
		return writeday;
	}
	public void setWriteday(String writeday) {
		this.writeday = writeday;
	}
	public String getStartday() {
		return startday;
	}
	public void setStartday(String startday) {
		this.startday = startday;
	}
	public String getEndday() {
		return endday;
	}
	public void setEndday(String endday) {
		this.endday = endday;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getEmergencycontactnetwork() {
		return emergencycontactnetwork;
	}
	public void setEmergencycontactnetwork(String emergencycontactnetwork) {
		this.emergencycontactnetwork = emergencycontactnetwork;
	}
	public String getSharedepartmentno() {
		return sharedepartmentno;
	}
	public void setSharedepartmentno(String sharedepartmentno) {
		this.sharedepartmentno = sharedepartmentno;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getOrgfilename() {
		return orgfilename;
	}
	public void setOrgfilename(String orgfilename) {
		this.orgfilename = orgfilename;
	}
	public String getFilesize() {
		return filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	public String getPapersname() {
		return papersname;
	}
	public void setPapersname(String papersname) {
		this.papersname = papersname;
	}
	public String getEmployeename() {
		return employeename;
	}
	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}
	public String getTextnumber() {
		return textnumber;
	}
	public void setTextnumber(String textnumber) {
		this.textnumber = textnumber;
	}
	
	
	
	
	
	
	
	
	
	

}	// End--------------------------------------------------------------------------
