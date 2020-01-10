package com.bo.index.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class mainCtrl {

	
	/*임의로 테스트 하기위해서 만든 컨트롤 클래스
	각자 맡은 부분의 폴더의 파일을 만들어서 사용  2020/01/03 kkh*/
	
	// 메인페이지 2020/01/06 LBH 
	@RequestMapping(value="/main.action")
	public String maincontenttest() {
		return "main.tiles1";
	}
	
	// 주소록 2020/01/06 LBH
	@RequestMapping(value="/addressBook.action")
	public String addressBook() {
		return "addressBook.tiles1";
	}
	
	// 주소록 2020/01/07 LBH
	@RequestMapping(value="/addressOpenBook.action")
	public String addressOpenBook() {
		return "addressOpenBook.tiles1";
	}
	
	// 로그인 2020/01/07 LBH
	@RequestMapping(value="/login.action")
	public String login() {
		return "login.tiles1";
	}
}
 