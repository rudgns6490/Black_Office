package com.bo.index.service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bo.member.model.MemberVO;
import com.bo.common.AES256;
import com.bo.index.model.AdressbookVO;
import com.bo.index.model.InterIndexDAO;

@Service
public class IndexService implements InterIndexService {

	
	// === #34. 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private InterIndexDAO dao;
	
	
	// === #45. 양방향 암호화 알고리즘인 AES256를 사용하여 암호화/복호화 하기위한 클래스 의존객체 주입하기 (DI: Dependency Injection)
	@Autowired
	private AES256 aes;
	
	//로그인 시도
	@Override
	public MemberVO getLoginMember(HashMap<String, String> paraMap) {
		MemberVO loginuser =  dao.getLoginMember(paraMap);
		return loginuser;
	}
	
	// 개인주소록 추가등록 2020/01/16 LBH 
	@Override
	public int registerPersonal(AdressbookVO abvo) {
		int registerPersonal = dao.registerPersonal(abvo);
		return registerPersonal;
	}

}
