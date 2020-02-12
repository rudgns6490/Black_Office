package com.bo.archive.model;

public class ExpenditureVO {
	
	private String rno;							// 행번호(게시판에 글 No. 나타내기)
	private String expenditureno;			// 지출 결재 문서 번호
	private String fk_employeeno;			// 참조 키 사원번호			
	private String writeday;				// 결재 작성 일자			
	private String expenditureday;			// 지출 일자
	private String expendituremode;			// 지출 종류 -- 개인지출 이면 '0' , 법인지출 '1'
	private String title;					// 결재 제목
	private String content;					// 갤재 내용
	private String sharedepartmentno;		// 공유 부서 -- 공유 안하면 0
	private String status;					// 결재 여부 1이면 결재완료 0이면 미결재
	private String approver;				// 결재자 사원번호
	private String filename;				// WAS(톰캣)에 저장될 파일명(20190725092715353243254235235234.png)
	private String orgfilename;				// 진짜 파일명(강아지.png)
	private String filesize;				// 파일크기
	private String departmentname;			// 부서명
	private String papersname;				// 문서분류(명) -- '지출 결의서'
	private String employeename;			// 사원명
	private String textnumber;				// 원래 문서번호 채번해온 것
//-------------------------------------------------------------------------------------------------------------		
	
	public ExpenditureVO() { }
	
	public ExpenditureVO(String rno, String expenditureno, String fk_employeeno, String writeday, String expenditureday,
			String expendituremode, String title, String content, String sharedepartmentno, String status,
			String approver, String filename, String orgfilename, String filesize, String departmentname,
			String papersname, String employeename, String textnumber) {
		this.rno = rno;
		this.expenditureno = expenditureno;
		this.fk_employeeno = fk_employeeno;
		this.writeday = writeday;
		this.expenditureday = expenditureday;
		this.expendituremode = expendituremode;
		this.title = title;
		this.content = content;
		this.sharedepartmentno = sharedepartmentno;
		this.status = status;
		this.approver = approver;
		this.filename = filename;
		this.orgfilename = orgfilename;
		this.filesize = filesize;
		this.departmentname = departmentname;
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
	
	public String getExpenditureno() {
		return expenditureno;
	}
	public void setExpenditureno(String expenditureno) {
		this.expenditureno = expenditureno;
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
	
	public String getExpenditureday() {
		return expenditureday;
	}
	public void setExpenditureday(String expenditureday) {
		this.expenditureday = expenditureday;
	}
	
	public String getExpendituremode() {
		return expendituremode;
	}
	public void setExpendituremode(String expendituremode) {
		this.expendituremode = expendituremode;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
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
