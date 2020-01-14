package com.bo.member.model;

public class MemberVO {

	private int employeeno;
	private String id;
	private String passwd;
	private String jubun;
	private String email;
	private String emailpw;
	private String phone;
	private String address;
	private String detailaddress;
	private String registerday;
	private int gotowork;
	private int status;
	private int fk_positionno;
	private int fk_departmentno;
	
	
	public MemberVO() {}
	
	public MemberVO(int employeeno, String id, String passwd,
					String jubun, String email, String emailpw,
					String phone, String address, String detailaddress,
					String registerday, int gotowork, int status,
					int fk_positionno, int fk_departmentno) {
		super();
		this.employeeno = employeeno;
		this.id = id;
		this.passwd = passwd;
		this.jubun = jubun;
		this.email = email;
		this.emailpw = emailpw;
		this.phone = phone;
		this.address = address;
		this.detailaddress = detailaddress;
		this.registerday = registerday;
		this.gotowork = gotowork;
		this.status = status;
		this.fk_positionno = fk_positionno;
		this.fk_departmentno = fk_departmentno;
	}

	public int getEmployeeno() {
		return employeeno;
	}

	public void setEmployeeno(int employeeno) {
		this.employeeno = employeeno;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getJubun() {
		return jubun;
	}

	public void setJubun(String jubun) {
		this.jubun = jubun;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailpw() {
		return emailpw;
	}

	public void setEmailpw(String emailpw) {
		this.emailpw = emailpw;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDetailaddress() {
		return detailaddress;
	}

	public void setDetailaddress(String detailaddress) {
		this.detailaddress = detailaddress;
	}

	public String getRegisterday() {
		return registerday;
	}

	public void setRegisterday(String registerday) {
		this.registerday = registerday;
	}

	public int getGotowork() {
		return gotowork;
	}

	public void setGotowork(int gotowork) {
		this.gotowork = gotowork;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getFk_positionno() {
		return fk_positionno;
	}

	public void setFk_positionno(int fk_positionno) {
		this.fk_positionno = fk_positionno;
	}

	public int getFk_departmentno() {
		return fk_departmentno;
	}

	public void setFk_departmentno(int fk_departmentno) {
		this.fk_departmentno = fk_departmentno;
	}
	
	
	
	
}
