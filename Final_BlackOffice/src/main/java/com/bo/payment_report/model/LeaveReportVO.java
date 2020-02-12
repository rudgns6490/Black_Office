package com.bo.payment_report.model;

import org.springframework.web.multipart.MultipartFile;

public class LeaveReportVO {
	
	private String vacationno;
	private String fk_employeeno;
	private String writeday;
	private String startday;
	private String endday;
	private String title;
	private String reason;
	private String emergencycontactnetwork;
	private String sharedepartmentno;
	private String status;
	private String approver;
	private String approver_name;	
	private String departmentname;
	private String rank;
	private String fileName;
	private String orgFilename;
	private String fileSize;
	private String papersname;
	private String employeename;
	private String textnumber;
	
	private MultipartFile attach;
	
	
	public LeaveReportVO() {}


	public LeaveReportVO(String vacationno, String fk_employeeno, String writeday, String startday, String endday,
			String title, String reason, String emergencycontactnetwork, String sharedepartmentno, String status,
			String approver, String approver_name, String departmentname, String rank, String fileName,
			String orgFilename, String fileSize, String papersname, String employeename, String textnumber,
			MultipartFile attach) {
		super();
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
		this.approver_name = approver_name;
		this.departmentname = departmentname;
		this.rank = rank;
		this.fileName = fileName;
		this.orgFilename = orgFilename;
		this.fileSize = fileSize;
		this.papersname = papersname;
		this.employeename = employeename;
		this.textnumber = textnumber;
		this.attach = attach;
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


	public String getApprover_name() {
		return approver_name;
	}


	public void setApprover_name(String approver_name) {
		this.approver_name = approver_name;
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


	public MultipartFile getAttach() {
		return attach;
	}


	public void setAttach(MultipartFile attach) {
		this.attach = attach;
	}


	


	


	


	
}
