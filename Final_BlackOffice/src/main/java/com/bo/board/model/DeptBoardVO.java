package com.bo.board.model;

import org.springframework.web.multipart.MultipartFile;

// === VO 생성하기
//     먼저, 오라클에서 tblBoard 테이블을 생성해야 한다. 
public class DeptBoardVO { 

	private String seq;          	// 글번호 
	private String fk_userid;    	// 사용자ID
	private String name;         	// 글쓴이 
	private String subject;      	// 글제목
	private String content;      	// 글내용 
	private String pw;           	// 글암호
	private String readCount;    	// 글조회수
	private String regDate;      	// 글쓴시간
	private String status;       	// 글삭제여부   1:사용가능한 글,  0:삭제된글 
	private String fk_employeeno;	// 참조키 작성자 사원번호
	
	private String previousseq;     // 이전글번호
	private String previoussubject; // 이전글제목
	private String nextseq;         // 다음글번호
	private String nextsubject;     // 다음글제목
	

	//  댓글형 게시판을 위한 commentCount 필드 추가하기 
	//  먼저 tblBoard 테이블에 commentCount 라는 컬럼이 존재해야 한다. 
	private String commentCount;     // 댓글수 
		
	/* 아래 컬럼 3개 추가 진행 */
	private String fileName;      	// WAS(톰캣)에 저장될 파일명(20190725092715353243254235235234.png)
	private String orgFilename;   	// 진짜 파일명(강아지.png)  // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명 
	private String fileSize;      	// 파일크기 
	
	private MultipartFile attach; 	// 진짜 파일 ==> WAS(톰캣) 디스크에 저장됨.
									// !!!!!! MultipartFile attach 는 오라클 데이터베이스 tblBoard 테이블의 컬럼이 아니다.!!!!!!  
									// /Board/src/main/webapp/WEB-INF/views/tiles1/board/add.jsp 파일에서 input type="file" 인 name 의 이름(attach)과 
									// 동일해야만 파일첨부가 가능해진다.!!!!

	public DeptBoardVO() { }
		
		public DeptBoardVO(String seq, String fk_userid, String name, String subject,
				String content, String pw, String readCount, String regDate, String status,
				String fk_employeeno, 
				
				String previousseq, 
				String previoussubject,
				String nextseq, 
				String nextsubject,
				
				String commentCount,
				
				String fileName, 
				String orgFilename,
				String fileSize,
				
				MultipartFile attach) {
			
			this.seq = seq;
			this.fk_userid = fk_userid;
			this.name = name;
			this.subject = subject;
			this.content = content;
			this.pw = pw;
			this.readCount = readCount;
			this.regDate = regDate;
			this.status = status;
			this.fk_employeeno = fk_employeeno;
			
			this.previousseq = previousseq;
			this.previoussubject = previoussubject;
			this.nextseq = nextseq;
			this.nextsubject = nextsubject;
			
			this.commentCount = commentCount;
			
			this.fileName = fileName;
			this.orgFilename = orgFilename;
			this.fileSize = fileSize;
			this.attach = attach;
		}

		public String getSeq() {
			return seq;
		}

		public void setSeq(String seq) {
			this.seq = seq;
		}

		public String getFk_userid() {
			return fk_userid;
		}

		public void setFk_userid(String fk_userid) {
			this.fk_userid = fk_userid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getPw() {
			return pw;
		}

		public void setPw(String pw) {
			this.pw = pw;
		}

		public String getReadCount() {
			return readCount;
		}

		public void setReadCount(String readCount) {
			this.readCount = readCount;
		}

		public String getRegDate() {
			return regDate;
		}

		public void setRegDate(String regDate) {
			this.regDate = regDate;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getFk_employeeno() {
			return fk_employeeno;
		}

		public void setFk_employeeno(String fk_employeeno) {
			this.fk_employeeno = fk_employeeno;
		}

		public String getPreviousseq() {
			return previousseq;
		}

		public void setPreviousseq(String previousseq) {
			this.previousseq = previousseq;
		}

		public String getPrevioussubject() {
			return previoussubject;
		}

		public void setPrevioussubject(String previoussubject) {
			this.previoussubject = previoussubject;
		}

		public String getNextseq() {
			return nextseq;
		}

		public void setNextseq(String nextseq) {
			this.nextseq = nextseq;
		}

		public String getNextsubject() {
			return nextsubject;
		}

		public void setNextsubject(String nextsubject) {
			this.nextsubject = nextsubject;
		}

		public String getCommentCount() {
			return commentCount;
		}

		public void setCommentCount(String commentCount) {
			this.commentCount = commentCount;
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

		public MultipartFile getAttach() {
			return attach;
		}

		public void setAttach(MultipartFile attach) {
			this.attach = attach;
		}

		
		
		
	
	
}

