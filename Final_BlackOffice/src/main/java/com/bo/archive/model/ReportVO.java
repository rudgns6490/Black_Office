package com.bo.archive.model;

public class ReportVO {
	
	private int rno;			// 행번호(게시판에 글 No. 나타내기)
	private int reportno;		// 보고서 번호
	private int fk_employeeno;	// 참조키 사원번호
	private String reportday;		// 보고 일자
	private int reporttype;		// 일일보고는 0, 주간보고는 1, 월간보고 2
	private String title;			// 제목
	private String content;			// 보고서 내용
	private String memo;			// 메모
	private String addressee;		// 수신인 사번
	
	
//--------------------------------------------------------------------------	
	
	public ReportVO() { }
	
	public ReportVO(int rno, int reportno, int fk_employeeno, String reportday, int reporttype, String title,
			String content, String memo, String addressee) {
		this.rno = rno;
		this.reportno = reportno;
		this.fk_employeeno = fk_employeeno;
		this.reportday = reportday;
		this.reporttype = reporttype;
		this.title = title;
		this.content = content;
		this.memo = memo;
		this.addressee = addressee;
	}
//--------------------------------------------------------------------------
	public int getRno() {
		return rno;
	}
	public void setRno(int rno) {
		this.rno = rno;
	}
	
	public int getReportno() {
		return reportno;
	}
	public void setReportno(int reportno) {
		this.reportno = reportno;
	}

	public int getFk_employeeno() {
		return fk_employeeno;
	}
	public void setFk_employeeno(int fk_employeeno) {
		this.fk_employeeno = fk_employeeno;
	}

	public String getReportday() {
		return reportday;
	}
	public void setReportday(String reportday) {
		this.reportday = reportday;
	}

	public int getReporttype() {
		return reporttype;
	}
	public void setReporttype(int reporttype) {
		this.reporttype = reporttype;
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

	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getAddressee() {
		return addressee;
	}
	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}
//--------------------------------------------------------------------------	
	
	
	
	
	
	

}
