package com.bo.index.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bo.common.SHA256;
import com.bo.index.model.AdressbookVO;
import com.bo.index.service.InterIndexService;
import com.bo.member.model.MemberVO;

@Controller
public class mainController {

	
	/*임의로 테스트 하기위해서 만든 컨트롤 클래스
	각자 맡은 부분의 폴더의 파일을 만들어서 사용  2020/01/03 kkh*/
	
	// === #35. 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private InterIndexService service;
	
	// 메인페이지 2020/01/06 LBH 
	@RequestMapping(value="/main.action")
	public String requireLoginCheck_maincontenttest(HttpServletRequest request, HttpServletResponse response) {
	/*public String maincontenttest(HttpServletRequest request, HttpServletResponse response) {*/
		return "main.tiles1";
	}
	
	// 개인주소록 2020/01/06 LBH
	@RequestMapping(value="/addressBook.action")
	public ModelAndView addressBook(ModelAndView mav) {
		
		mav.setViewName("addressBook.tiles1");
		return mav;
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
	public ModelAndView addressOpenBook(ModelAndView mav) {
		mav.setViewName("addressOpenBook.tiles1");
		return mav;
	}
	
	// 로그인 2020/01/07 LBH
	@RequestMapping(value="/login.action")
	public ModelAndView login(ModelAndView mav) {
		mav.setViewName("login/loginform.tiles1");
		return mav;
	}
	
	// === #41. 로그인 처리하기 ===
	@RequestMapping(value="/loginEnd.action", method = {RequestMethod.POST} )
	public ModelAndView loginEnd(HttpServletRequest request, ModelAndView mav) { 
		
	 	String id = request.getParameter("id");
	 	String passwd = request.getParameter("passwd");
	 	
	 	HashMap<String, String> paraMap = new HashMap<String, String>(); 
	 	paraMap.put("id", id);
		paraMap.put("passwd",    SHA256.encrypt(passwd));
		
		MemberVO loginuser = service.getLoginMember(paraMap);
		/////////////////////////////////////////////////////
		
		HttpSession session = request.getSession();
		
		if(loginuser == null) {
			String msg = "아이디 또는 암호가 틀립니다.";
			String loc = "javascript:history.back()";
			
			mav.addObject("msg", msg);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg");
			//  /Board/src/main/webapp/WEB-INF/views/msg.jsp 파일을 생성한다.
		}
		else {
	
			// 아무런 이상없이 로그인 하는 경우
			session.setAttribute("loginuser", loginuser);
			
			if(session.getAttribute("gobackURL") != null) {
				// 세션에 저장된 돌아갈 페이지의 주소(gobackURL)이 있다라면 ( pakage : com.spring.board.LoginCheck - session.setAttribute("gobackURL", url); // 세션에 url 정보를 저장시켜둔다. )
				
				String gobackURL = (String) session.getAttribute("gobackURL");
				mav.addObject("gobackURL", gobackURL); // request 영역에 저장시키는 것이다.
				
				session.removeAttribute("gobackURL"); // 세션은 없앴다.
			}
			
			mav.setViewName("login/loginEnd.tiles1");
			//  /Board/src/main/webapp/WEB-INF/views/tiles1/login/loginEnd.jsp 파일을 생성한다.	
		}
			
		return mav;
	}
	
	//로그 아웃
	@RequestMapping(value="/logout.action")
	public ModelAndView logout(HttpServletRequest request, ModelAndView mav) { 
		
		HttpSession session = request.getSession();
		session.invalidate();

		String msg = "로그아웃 되었습니다.";
		String loc = request.getContextPath() + "/login.action";
		
		mav.addObject("msg", msg);
		mav.addObject("loc", loc);
		
		
		mav.setViewName("msg");
		return mav;
	}
}
 