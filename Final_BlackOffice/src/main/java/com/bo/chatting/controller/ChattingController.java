package com.bo.chatting.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// === (웹채팅관련4) === //
@Controller
public class ChattingController {
	 
	@RequestMapping(value="/chatting/multichat.action")
	public String requireLoginCheck_multichat(HttpServletRequest request, HttpServletResponse response) {
		// 채팅을하렬면 로그인을 해야한다. 
		// AOP (Aspect Oriented Programing) - com.spring.board.aop - LoginCheck 에 있는
		// @Pointcut 에서 정의한 requireLogin 를 메소드명에 넣어줌
		
		return "chatting/multichat.tiles1";
	}
	
}

