package com.bo.board.common;

import javax.servlet.http.HttpServletRequest;

public class MyUtil {

	// *** 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어코드)를 작성해주는 메소드 생성 *** // 
	public static String replaceParameter(String param) {
		String result = param;
		
		if(param != null) {
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("&", "&amp;");
			result = result.replaceAll("\"", "&quot;");
		}

		return result;
	}
	
	
	// *** ? 다음의 데이터까지 포함한 URL 주소를 알아오는 메소드 생성 *** //
	public static String getCurrentURL(HttpServletRequest request) {
		
		String currentURL = request.getRequestURL().toString();
		// http://localhost:9090/MyMVC/shop/prodView.up
		
		String queryString = request.getQueryString();
		// pnum=3
		
		currentURL += "?"+queryString;
		// http://localhost:9090/MyMVC/shop/prodView.up?pnum=3
		
		String ctxPath = request.getContextPath();
		//   /MyMVC
		
		int beginIndex = currentURL.indexOf(ctxPath) + ctxPath.length();
		//                              21           +     6 
		
		currentURL = currentURL.substring(beginIndex+1);
		//                                    27+1
		// shop/prodView.up?pnum=3
		
		return currentURL;
		//  shop/prodView.up?pnum=3
	}
	
	
	
	
	
	
	
	
}
