package com.bo.archive.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bo.archive.model.ReportVO;
import com.bo.archive.service.InterArchiveService;
import com.bo.common.MyUtil;
import com.bo.member.model.MemberVO;

@Controller
public class ArchiveController {
	
	@Autowired
	private InterArchiveService service;
	
	// === 내가 쓴 보고서함 ===
	@RequestMapping(value="/my_report_archive.action", method= {RequestMethod.GET})
	public ModelAndView my_report_archive(HttpServletRequest request, ModelAndView mav) {
				
		List<ReportVO> reportList = null;
			
		// 페이징 처리
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		int totalCount = 0;
		int sizePerPage = 10;
		int currentShowPageNo = 0;
		int totalPage = 0;
		int startRno = 0;
		int endRno = 0;
		
		String search_select = request.getParameter("search_select");
		String report_search_word = request.getParameter("report_search_word");
		String search_Time = request.getParameter("search_Time");
//		String employeeno = request.getParameter("employeeno");
//		int employeeno = 2;
		
	
		System.out.println("검색타입 : " +search_select);
		System.out.println("검색어 : " +report_search_word);
		System.out.println("검색기간 : " +search_Time);
		
	/*
		작성일자 불러올때 쓸 것
		String  = request.getParameter("");
		String  = request.getParameter("");
	*/	
		if( report_search_word == null || report_search_word.trim().isEmpty() ) {
			report_search_word = "";
		}
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("search_select", search_select);
		paraMap.put("report_search_word", report_search_word);
//		paraMap.put("employeeno", String.valueOf(employeeno));
		
		if( "".equals(report_search_word) ) {
			// 검색조건이 없을경우 총 게시물 수 (totalCount)
			totalCount = service.getReportTotalCountWithNoSearch();
		}
		else {
			// 검색조건이 있을경우 총 게시물 수 (totalCount)
			totalCount = service.getReportTotalCountWithSearch(paraMap);
		}
		
		totalPage =  (int) Math.ceil( (double)totalCount / sizePerPage );
		
		if(str_currentShowPageNo == null) {
			currentShowPageNo = 1;
		}
		else {
			try {
				currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
				
				if(currentShowPageNo < 1 || currentShowPageNo > totalPage) {
					currentShowPageNo = 1;
				}
			} catch (NumberFormatException e) {
				currentShowPageNo = 1;
			}
		}
		
		startRno = ( (currentShowPageNo - 1) * sizePerPage ) + 1;
		endRno = startRno + sizePerPage - 1;
		
		paraMap.put("startRno", String.valueOf(startRno));
		paraMap.put("endRno", String.valueOf(endRno));
		
		reportList = service.myWriteReportListWithPaging(paraMap);
		
		// 검색 후 검색어 유지
		if( !"".equals(report_search_word)) {
			mav.addObject("paraMap", paraMap);
		}
		
		String pageBar = "<ul class='pageBar' style='list-style: none; padding-left: 0px;'>";
		
		int blockSize = 10;
		int loop = 1;
		int pageNo = ( (currentShowPageNo - 1) / blockSize ) * blockSize + 1;
		// 페이지바 한블럭에 첫번째 번호
		
		String url = "my_report_archive.action";
		String lastStr = url.substring(url.length()-1);
		if(!"?".equals(lastStr)) {
			url += "?";
		}
		
		// [이전] 버튼
		if(pageNo != 1) {
			pageBar += "&nbsp;<li><a href='" +url+ "&currentShowPageNo=" +(pageNo-1)+ "&sizePerPage=" +sizePerPage+ "&search_select=" +search_select+ "&report_search_word=" +report_search_word+ "'>[이전]</a></li>&nbsp;";     
		}
		// 페이지 바
		while( !(loop>blockSize || pageNo>totalPage) ) {
			
			if(pageNo == currentShowPageNo) {
				pageBar += "&nbsp;<li><span style='color: red;'>" +pageNo+ "</span></li>&nbsp;";
			} else {
				pageBar += "&nbsp;<li><a href='" +url+ "&currentShowPageNo=" +pageNo+ "&sizePerPage=" +sizePerPage+ "&search_select=" +search_select+ "&report_search_word=" +report_search_word+ "'>" +pageNo+ "</a></li>&nbsp;";     
			}
			loop++;
			pageNo++;
		}
		// [다음] 버튼
		if( !(pageNo > totalPage) ) {
			pageBar += "&nbsp;<li><a href='" +url+ "&currentShowPageNo=" +pageNo+ "&sizePerPage=" +sizePerPage+ "&search_select=" +search_select+ "&report_search_word=" +report_search_word+ "'>[다음]</a></li>&nbsp;";
		}
		
		pageBar += "</ul>";
		
		mav.addObject("pageBar", pageBar);
						
				
		String goBackURL = MyUtil.getCurrentURL(request);
		mav.addObject("goBackURL", goBackURL);
		
		mav.addObject("reportList", reportList);
		
		mav.setViewName("archive/report/my_report.tiles1");
		
		return mav;
	}
	
	// === 보고서 수신함 ===
	@RequestMapping(value="/receive_report_archive.action")
	public String receive_report_archive() {
		return "archive/report/receive_report.tiles1";
	}
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////
	////////////// === 결재문서함 ===  //////////////
	// === 미결재 문서 ===
	@RequestMapping(value="/incomplete_payment_archive.action")
	public String incomplete_payment_archive() {
		return "archive/payment/incomplete.tiles1";
	}
	// === 결재완료 문서 ===
	@RequestMapping(value="/complete_payment_archive.action")
	public String complete_payment_archive() {
		return "archive/payment/complete.tiles1";
	}
	
		
	// === 결재문서 수신함  ===
	@RequestMapping(value="/receive_payment_archive.action")
	public String receive_payment_archive() {
		return "archive/payment/receive_payment.tiles1";
	}
	
	
	
	
	
	
}	// End-----------------------------------------------------------------------------
