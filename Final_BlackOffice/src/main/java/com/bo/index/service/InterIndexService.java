package com.bo.index.service;

import java.util.HashMap;

import com.bo.index.model.AdressbookVO;
import com.bo.member.model.MemberVO;

public interface InterIndexService {

	MemberVO getLoginMember(HashMap<String, String> paraMap);	//로그인 시도

	int registerPersonal(AdressbookVO abvo); // 개인주소록 추가등록 2020/01/16 LBH 

}
