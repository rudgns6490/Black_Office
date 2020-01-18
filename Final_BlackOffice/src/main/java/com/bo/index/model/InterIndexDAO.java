package com.bo.index.model;

import java.util.HashMap;

import com.bo.member.model.MemberVO;

public interface InterIndexDAO {

	//로그인하기
	MemberVO getLoginMember(HashMap<String, String> paraMap);

	int registerPersonal(AdressbookVO abvo); // 개인주소록 추가등록 2020/01/16 LBH
}
