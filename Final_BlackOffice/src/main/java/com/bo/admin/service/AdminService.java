package com.bo.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bo.admin.model.InterAdminDAO;
import com.bo.member.model.MemberVO;


//=== Service 선언 ===
@Service 
public class AdminService implements InterAdminService{
	
	/* 관리자 메뉴 관련 서비스  2020/01/15 LBH */
	
	// === 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private InterAdminDAO dao;
	
	//신규 입사 등록 2020/01/15 LBH
	@Override
	public int register(MemberVO mvo) {
		int register = dao.register(mvo);
		return register;
	}
	

	
	
	
	
	

	
}
