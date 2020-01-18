package com.bo.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bo.admin.model.AdressbookVO;
import com.bo.admin.service.InterAdminService;
import com.bo.member.model.MemberVO;

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
	
	// 나중에 삭제
	// 메인페이지 2020/01/06 LBH 
	@RequestMapping(value="/main.action")
	public String maincontenttest() {
		return "main.tiles1";
	}
	
	// 로그인 2020/01/07 LBH
	@RequestMapping(value="/login.action")
	public String login() {
		return "login.tiles1";
	}
	
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
	
	//신규 입사 등록 2020/01/15 LBH
	@RequestMapping(value="/register.action")
	public ModelAndView register(HttpServletRequest request, ModelAndView mav) {
		
		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");
		String name = request.getParameter("name");
		String jubun = request.getParameter("jubun1") + request.getParameter("jubun2");
		String email = request.getParameter("email");
		String emailpw = request.getParameter("emailpw");
		String phone = request.getParameter("phone");
		
		MemberVO mvo = new MemberVO();
		mvo.setId(id);
		mvo.setPasswd(passwd);
		mvo.setName(name);
		mvo.setJubun(jubun);
		mvo.setEmail(email);
		mvo.setEmailpw(emailpw);
		mvo.setPhone(phone);
		
		int n = service.register(mvo);
		mav.addObject("n", n);
		mav.setViewName("admin/joinSawon.tiles1");
		return mav;
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
	
	
	// 개인주소록 2020/01/06 LBH
	@RequestMapping(value="/addressBook.action")
	public String addressBook() {
		return "addressBook.tiles1";
	}
	
	// 개인주소록 추가등록 2020/01/16 LBH
	@RequestMapping(value="/registerPersonal.action")
	public ModelAndView registerPersonal(HttpServletRequest request, ModelAndView mav) {
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String positionname = request.getParameter("positionname");
		String departmentname = request.getParameter("departmentname");
		String company = request.getParameter("company");
		String group = request.getParameter("group");
		
		AdressbookVO abvo = new AdressbookVO();
		abvo.setName(name);
		abvo.setEmail(email);
		abvo.setPhone(phone);
		abvo.setPositionname(positionname);
		abvo.setDepartmentname(departmentname);
		abvo.setCompany(company);
		abvo.setGroup(group);
		
		int n = service.registerPersonal(abvo);
		mav.addObject("n", n);
		mav.setViewName("addressBook.tiles1");
		return mav;
	}
	
	// 공용주소록 2020/01/07 LBH
	@RequestMapping(value="/addressOpenBook.action")
	public String addressOpenBook() {
		return "addressOpenBook.tiles1";
	}
	
	
	
	
	
}
