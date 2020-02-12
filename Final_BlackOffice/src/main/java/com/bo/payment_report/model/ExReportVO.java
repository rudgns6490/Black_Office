package com.bo.payment_report.model;

import org.springframework.web.multipart.MultipartFile;

public class ExReportVO {
	
	private String expenditureno;	// 지출 결재 번호
	private String fk_employeeno;	// 참조 키 사원번호
	private String writeday;		// 결재 작성 일자
	private String expenditureday;	// 지출 일자
	private String expendituremode; // 지출 종류
	private String title;			// 결재 제목
	private String content;			// 갤재 내용
	private String sharedepartmentno;	// 공유 부서 번호
	private String status;			// 결재 여부 1이면 결재완료 0이면 미결재
	private String approver;		// 결재자 사원번호
	private String approver_name;	// 결재자 이름
	
	private String fileName;      // WAS(톰캣)에 저장될 파일명(20190725092715353243254235235234.png)
	private String orgFilename;   // 진짜 파일명(강아지.png)  // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명 
	private String fileSize;      // 파일크기
	private String departmentname;	// 부서명
	private String papersname;	  // 문서분류(명) 지출결의서
	private String employeename;  // 사원명
	
	private String textnumber;	  // 진짜 문서번호 채번해온 것 
	private String payment_money; // 지출 금액
	
	private MultipartFile attach;
	
	public ExReportVO() {}

	public ExReportVO(String expenditureno, String fk_employeeno, String writeday, String expenditureday,
			String expendituremode, String title, String content, String sharedepartmentno, String status,
			String approver, String approver_name, String fileName, String orgFilename, String fileSize,
			String departmentname, String papersname, String employeename, String textnumber, String payment_money,
			MultipartFile attach) {
		super();
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
		this.approver_name = approver_name;
		this.fileName = fileName;
		this.orgFilename = orgFilename;
		this.fileSize = fileSize;
		this.departmentname = departmentname;
		this.papersname = papersname;
		this.employeename = employeename;
		this.textnumber = textnumber;
		this.payment_money = payment_money;
		this.attach = attach;
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

	public String getApprover_name() {
		return approver_name;
	}

	public void setApprover_name(String approver_name) {
		this.approver_name = approver_name;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOrgFilename() {
		return orgFilename;
	}

	public void setOrgFilename(String orgFilename) {
		this.orgFilename = orgFilename;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
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

	public String getPayment_money() {
		return payment_money;
	}

	public void setPayment_money(String payment_money) {
		this.payment_money = payment_money;
	}

	public MultipartFile getAttach() {
		return attach;
	}

	public void setAttach(MultipartFile attach) {
		this.attach = attach;
	}

	
	
	
}
