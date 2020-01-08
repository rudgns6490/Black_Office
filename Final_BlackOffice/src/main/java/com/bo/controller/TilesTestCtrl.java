package com.bo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TilesTestCtrl {

	
	/*임의로 테스트 하기위해서 만든 컨트롤 클래스
	각자 맡은 부분의 폴더의 파일을 만들어서 사용  2020/01/03 kkh*/
	
	@RequestMapping(value="/maintest.action")
	public String maincontenttest() {
		return "indexcontent.tiles1";
	}
	
	@RequestMapping(value="/template.action")
	public String template() {
		return "template";
	}
}
