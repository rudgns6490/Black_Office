package com.bo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	
	/* 관리자 메뉴 관련 컨트롤러  2020/01/03 kkh*/
	
	/*지출통계 컨트롤러 2020/01/06 kkh*/
	@RequestMapping(value="/admintotalexpenditure.action")
	public String admintotalexpenditure() {
		return "admin/admintotalexpenditure.tiles1";
	}

	//직위 설정 컨트롤러 2020/01/06 kkh
	@RequestMapping(value="/positionmanagement.action")
	public String positionmanagement() {
		return "admin/positionmanagement.tiles1";
	}
	
	//신규 입사 컨트롤러 2020/01/06 kkh
	@RequestMapping(value="/joinSawon.action")
	public String joinSawon() {
		return "admin/joinSawon.tiles1";
	}
	
	//인사 이동 컨트롤러 2020/01/06 kkh
	@RequestMapping(value="/personnelAnnouncement.action")
	public String personnelAnnouncement() {
		return "admin/personnelAnnouncement.tiles1";
	}
	
	//인사 이동 내역 컨트롤러 2020/01/06 kkh
	@RequestMapping(value="/personnelAnnouncementList.action")
	public String personnelAnnouncementList() {
		return "admin/personnelAnnouncementList.tiles1";
	}
	
}
