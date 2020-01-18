package com.bo.admin.service;

import com.bo.admin.model.AdressbookVO;
import com.bo.member.model.MemberVO;

public interface InterAdminService {
	
	/* 관리자 메뉴 관련 인터페이스 서비스  2020/01/15 LBH */
	
	int register(MemberVO mvo); //신규 입사 등록 2020/01/15 LBH

	int registerPersonal(AdressbookVO abvo); // 개인주소록 추가등록 2020/01/16 LBH 
}





