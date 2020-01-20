package com.bo.admin.service;

import java.util.HashMap;

import com.bo.admin.model.AdressbookVO;
import com.bo.member.model.MemberVO;

public interface InterAdminService {
	
	/* 관리자 메뉴 관련 인터페이스 서비스  2020/01/15 LBH */
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 로그인 ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	MemberVO getLoginMember(HashMap<String, String> paraMap);	//로그인 시도
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 통계(차트) ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 입사처리/인사 ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	int register(MemberVO mvo, AdressbookVO adressbookvo); //신규 입사 등록 2020/01/15 LBH
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////			[ 주소록 ( 개인 / 공용 ) ]
	//////////////////////////////////////////////////////////////////////////////////////////////////////

	int registerPersonal(AdressbookVO abvo); // 개인주소록 추가등록 2020/01/16 LBH

	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	

}




