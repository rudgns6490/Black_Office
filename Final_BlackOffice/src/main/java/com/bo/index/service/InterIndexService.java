package com.bo.index.service;

import java.util.HashMap;

import com.bo.member.model.MemberVO;

public interface InterIndexService {

	MemberVO getLoginMember(HashMap<String, String> paraMap);	//로그인 시도

}
