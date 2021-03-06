package com.bo.member.model;

import org.springframework.web.multipart.MultipartFile;

public class MemberVO {
	
	private int employeeno;			// 사원번호
	private String name;			// 사원이름
	private String id;				// 아이디
	private String passwd;			// 비밀번호
	private String jubun;			// 주민번호
	private String email;			// 이메일
	private String emailpw;			// 이메일 비밀번호
	private String phone;			// 전화번호
	private String address;			// 주소
	private String detailaddress;	// 상세주소
	private String registerday;		// 입사일자
	private int gotowork;			// 출근 1 퇴근 0
	private int status;				// 퇴사 0 재직 1 휴직2
	private int fk_positionno;		// 참조 키 직위번호
	private int fk_departmentno;	// 참조 키 부서번호
	
	//사진 첨부를 위한 추가 2020/01/15 KKH
	private String fileName;      // WAS(톰캣)에 저장될 파일명(20190725092715353243254235235234.png)
	private String orgFilename;   // 진짜 파일명(강아지.png)  // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명 
	private String fileSize;      // 파일크기 
	private String thumbnailFileName; // 썸네일이미지 파일명
	
	public String getThumbnailFileName() {
		return thumbnailFileName;
	}

	public void setThumbnailFileName(String thumbnailFileName) {
		this.thumbnailFileName = thumbnailFileName;
	}

	private MultipartFile attach; // 진짜 파일 ==> WAS(톰캣) 디스크에 저장됨.
	
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

	public MultipartFile getAttach() {
		return attach;
	}

	public void setAttach(MultipartFile attach) {
		this.attach = attach;
	}

	public MemberVO() {}

	public MemberVO(int employeeno, String name, String id, String passwd, String jubun, String email, String emailpw,
			String phone, String address, String detailaddress, String registerday, int gotowork, int status,
			int fk_positionno, int fk_departmentno) {
		super();
		this.employeeno = employeeno;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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




