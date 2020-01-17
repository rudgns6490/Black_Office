package com.bo.admin.model;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bo.member.model.MemberVO;

@Repository
public class AdminDAO implements InterAdminDAO {
	
	/* 관리자 메뉴 관련 DAO  2020/01/15 lbh */
	
	// === 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private SqlSessionTemplate sqlsession;
	
	@Override
	public int register(MemberVO mvo) {
		int register = sqlsession.insert("admin.register", mvo);
		return register;
	}
	
	
}
