package com.bo.admin.model;

public class AddressbookVO {
	
	private int ADDRNO;				// 사원번호
	private String name;			// 사원이름
	private String email;			// 이메일
	private String phone;			// 전화번호
	private String positionname;	// 직책
	private String departmentname;	// 부서
	private String companyname;			// 회사
	private String groupname;			// 그룹
	private int fk_employeeno;		// 사원번호
	
	public AddressbookVO() {}

	public AddressbookVO(int aDDRNO, String name, String email, String phone, String positionname, String departmentname,
			String company, String group) {
		super();
		ADDRNO = aDDRNO;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.positionname = positionname;
		this.departmentname = departmentname;
		this.companyname = companyname;
		this.groupname = groupname;
	}

	public int getFk_employeeno() {
		return fk_employeeno;
	}

	public void setFk_employeeno(int fk_employeeno) {
		this.fk_employeeno = fk_employeeno;
	}
	
	public int getADDRNO() {
		return ADDRNO;
	}

	public void setADDRNO(int aDDRNO) {
		ADDRNO = aDDRNO;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPositionname() {
		return positionname;
	}

	public void setPositionname(String positionname) {
		this.positionname = positionname;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
		
}
