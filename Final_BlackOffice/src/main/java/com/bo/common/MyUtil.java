package com.bo.common;

import javax.servlet.http.HttpServletRequest;

public class MyUtil {
	
	// *** 크로스 사이트 스트립트 공격에 대응하는 안전한 코드(secure code)를 작성해주는 메소드 생성 *** //
	public static String replaceParameter(String param) {
		String result = param;
		
		if(param != null) {
			result.replaceAll("<", "&lt;");
			result.replaceAll(">", "&gt;");
			result.replaceAll("&", "&amp;");
			result.replaceAll("\"", "&quot;");
		}
		
		return result;
	}

	// *** ? 다음의 데이터까지 포함한 URL 주소를 알아오는 메소드 생성 *** //
	public static String getCurrentURL(HttpServletRequest request) {
		
		String currentURL = request.getRequestURL().toString();
		// http://localhost:9090/MyMVC/shop/prodView.up
		
		String queryString = request.getQueryString(); 
		// pnum=2
		
		currentURL += "?"+queryString; 			// 물음표 다음 끝까지
		// http://localhost:9090/MyMVC/shop/prodView.up?pnum=2
		
		// /shop/prodView.up?pnum=3 이것만 필요
		String ctxPath = request.getContextPath();
		// /MyMVC
		
		int beginIndex = currentURL.indexOf(ctxPath) + ctxPath.length();
		// 						21  +  6    = 27
		// 9090 까지 20, ctxPath는 21에 위치한다. ( ctxPath = /MyMVC 이 어디인지 알아온다. (21~26) )
		
		currentURL = currentURL.substring(beginIndex+1); // 27+1 = 28 부터
		// shop/prodView.up?pnum=3
		
		return currentURL;
		// shop/prodView.up?pnum=3
	}
	
}
