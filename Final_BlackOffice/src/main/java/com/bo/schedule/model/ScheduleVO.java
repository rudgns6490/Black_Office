package com.bo.schedule.model;

public class ScheduleVO {
	
	

	private int schedule_no ;			// 일정번호
	private int user_no ; 				// 유저no
	private String schedule_title; 		// 일정제목
	private String schedule_content;	// 일정내용
	private String schedule_start; 		// 시작날짜
	private String schedule_end; 		// 끝나는날짜
	private String schedule_color; 		// 색상
	private int schedule_importance ;	// 중요도( 중요하지 않으면 0, 중요하면 1 )
	private int schedule_progresStat ; 	// 진행여부  ( 미해결 0 , 해결 1 ) 
	private int schedule_allday ; 		// allday 인지 아닌지 ( allday 아니면  0 , 맞으면 1 )  
	private String schedule_authority; 	// 일정 수정권한 ( 없으면 0 있으면 1 )
	private int schedule_type ; 		// 일정종류  (개일일정 0, 회사전체일정 1, 부서일정 2)
	private boolean allday; 
	private String type; 
	
	
	
	public String getType() {
		if( schedule_type == 0)
			return "개인일정"; 
		else if(schedule_type == 1) 
			return "회사전체일정"; 
		else 
			return "부서일정"; 
	}
	
	
	
	public ScheduleVO(int schedule_no, int user_no, String schedule_title, String schedule_content,
			String schedule_start, String schedule_end, String schedule_color, int schedule_importance,
			int schedule_progresStat, int schedule_allday, String schedule_authority, int schedule_type,
			boolean allday) {
		super();
		this.schedule_no = schedule_no;
		this.user_no = user_no;
		this.schedule_title = schedule_title;
		this.schedule_content = schedule_content;
		this.schedule_start = schedule_start;
		this.schedule_end = schedule_end;
		this.schedule_color = schedule_color;
		this.schedule_importance = schedule_importance;
		this.schedule_progresStat = schedule_progresStat;
		this.schedule_allday = schedule_allday;
		this.schedule_authority = schedule_authority;
		this.schedule_type = schedule_type;
		this.allday = allday;
	}
	public boolean isAllday() {
		
		if (schedule_allday == 0) 
			allday = false;
		
		else 
			allday = true; 
		
		return allday;
	}
	public void setAllday(boolean allday) {
		this.allday = allday;
	}
	public int getSchedule_no() {
		
		return schedule_no;
	}
	public void setSchedule_no(int schedule_no) {
		this.schedule_no = schedule_no;
	}
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public String getSchedule_title() {
		return schedule_title;
	}
	public void setSchedule_title(String schedule_title) {
		this.schedule_title = schedule_title;
	}
	public String getSchedule_content() {
		return schedule_content;
	}
	public void setSchedule_content(String schedule_content) {
		this.schedule_content = schedule_content;
	}
	public String getSchedule_start() {
		return schedule_start;
	}
	public void setSchedule_start(String schedule_start) {
		this.schedule_start = schedule_start;
	}
	public String getSchedule_end() {
		return schedule_end;
	}
	public void setSchedule_end(String schedule_end) {
		this.schedule_end = schedule_end;
	}
	public String getSchedule_color() {
		return schedule_color;
	}
	public void setSchedule_color(String schedule_color) {
		this.schedule_color = schedule_color;
	}
	public int getSchedule_importance() {
		return schedule_importance;
	}
	public void setSchedule_importance(int schedule_importance) {
		this.schedule_importance = schedule_importance;
	}
	public int getSchedule_progresStat() {
		return schedule_progresStat;
	}
	public void setSchedule_progresStat(int schedule_progresStat) {
		this.schedule_progresStat = schedule_progresStat;
	}
	public int getSchedule_allday() {
		return schedule_allday;
	}
	public void setSchedule_allday(int schedule_allday) {
		this.schedule_allday = schedule_allday;
	}
	public String getSchedule_authority() {
		return schedule_authority;
	}
	public void setSchedule_authority(String schedule_authority) {
		this.schedule_authority = schedule_authority;
	}
	public int getSchedule_type() {
		return schedule_type;
	}
	public void setSchedule_type(int schedule_type) {
		this.schedule_type = schedule_type;
	}
	public ScheduleVO(int schedule_no, int user_no, String schedule_title, String schedule_content,
			String schedule_start, String schedule_end, String schedule_color, int schedule_importance,
			int schedule_progresStat, int schedule_allday, String schedule_authority, int schedule_type) {
		super();
		this.schedule_no = schedule_no;
		this.user_no = user_no;
		this.schedule_title = schedule_title;
		this.schedule_content = schedule_content;
		this.schedule_start = schedule_start;
		this.schedule_end = schedule_end;
		this.schedule_color = schedule_color;
		this.schedule_importance = schedule_importance;
		this.schedule_progresStat = schedule_progresStat;
		this.schedule_allday = schedule_allday;
		this.schedule_authority = schedule_authority;
		this.schedule_type = schedule_type;
	}
	
	public ScheduleVO() {	}
	
	
	


}
