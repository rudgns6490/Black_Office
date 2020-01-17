package com.bo.admin.model;

import com.bo.member.model.MemberVO;

public interface InterAdminDAO {
	
	/* 관리자 메뉴 관련 인터페이스 DAO  2020/01/15 lbh */
	
	int register(MemberVO mvo); //신규 입사 등록 2020/01/15 lbh

	int registerPersonal(AdressbookVO abvo); // 개인주소록 추가등록 2020/01/16 LBH

	
	
	
}
