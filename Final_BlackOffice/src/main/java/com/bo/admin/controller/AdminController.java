package com.bo.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bo.admin.service.InterAdminService;

//=== 컨트롤러 선언 ===
@Component
/* XML에서 빈을 만드는 대신에 클래스명 앞에 @Component 어노테이션을 적어주면 해당 클래스는 bean으로 자동 등록된다. 
그리고 bean의 이름(첫글자는 소문자)은 해당 클래스명이 된다. */
@Controller
public class AdminController {
	
	// === 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private InterAdminService service;
	
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
	
	//신규 입사 등록 2020/01/15 lbh
//	@RequestMapping(value="/register.action")
//	public ModelAndView register(ModelAndView mav) {
//		// int n = service.register(registervo);
////		mav.addObject("n", n);
//		mav.setViewName("admin/joinSawon.tiles1");
//		return mav;
//	}
	
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
