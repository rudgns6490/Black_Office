package com.bo.archive.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bo.archive.model.ExpenditureVO;
import com.bo.archive.model.ReportVO;
import com.bo.archive.model.VacationVO;
import com.bo.archive.service.InterArchiveService;
import com.bo.common.FileManager;
import com.bo.common.MyUtil;
import com.bo.member.model.MemberVO;
import com.bo.payment_report.model.ExReportVO;


@Controller
public class ArchiveController {
	
	@Autowired
	private InterArchiveService service;
	
	@Autowired
	private FileManager fileManager;
	
	// === 내가 쓴 보고서함 목록 보여주기 ===
	   @RequestMapping(value="/my_report_archive.action", method= {RequestMethod.GET})
	   public ModelAndView requireLoginCheck_my_report_archive(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
	      
	      // view페이지에서 가져오는 값들
	      String search_select = request.getParameter("search_select");
	      String report_search_word = request.getParameter("report_search_word");
	      String search_Time = request.getParameter("search_Time");
	      String fromDate = request.getParameter("fromDate");
	      String toDate = request.getParameter("toDate");
	      
	      if( report_search_word == null || report_search_word.trim().isEmpty() ) {
	         report_search_word = "";
	      }
	      if( search_Time == null || search_Time.trim().isEmpty()) {
	         search_Time = "";
	      }
	      if( fromDate == null || fromDate.trim().isEmpty()) {
	         fromDate = "";
	      }
	      if( toDate == null || toDate.trim().isEmpty()) {
	         toDate = "";
	      }
	      
	      // session에서 가져오는 값들
	      HttpSession session = request.getSession();
	      MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
	      
	      String employeeno = String.valueOf(loginuser.getEmployeeno());
	      String departmentname = String.valueOf(loginuser.getFk_departmentno());
	      switch (departmentname) {
	         case "1":
	            departmentname = "인사팀";
	            break;
	         case "2":
	            departmentname = "마케팅팀";
	            break;
	         case "3":
	            departmentname = "개발1팀";
	            break;
	         case "4":
	            departmentname = "개발2팀";
	            break;
	         case "5":
	            departmentname = "영업팀";
	            break;
	         default :
	            departmentname = "부서등록 필요";
	            break;
	      }
	      
	      
	      HashMap<String, String> paraMap = new HashMap<String, String>();
	      paraMap.put("search_select", search_select);
	      paraMap.put("report_search_word", report_search_word);
	      paraMap.put("employeeno", employeeno);
	      paraMap.put("search_Time", search_Time);
	      paraMap.put("departmentname", departmentname);
	      paraMap.put("fromDate", fromDate);
	      paraMap.put("toDate", toDate);
	         
	      
	      // 페이징처리에 필요한 값들
	      String str_currentShowPageNo = request.getParameter("currentShowPageNo");
	      int totalCount = 0;
	      int sizePerPage = 10;
	      int currentShowPageNo = 0;
	      int totalPage = 0;
	      
	      int startRno = 0;
	      int endRno = 0;
	         
	      if( "".equals(report_search_word) && "".equals(search_Time) && "".equals(fromDate) && "".equals(toDate)) {
	         // 검색조건이 없을경우 총 게시물 수 (totalCount)
	         totalCount = service.getmyReportTotalCountWithNoSearch(paraMap);
	      }
	      else {
	         // 검색조건이 있을경우 총 게시물 수 (totalCount)
	         totalCount = service.getmyReportTotalCountWithSearch(paraMap);
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
	      
	      
	      // 페이징 글목록 가져오기
	      List<HashMap<String, String>> myReportList = service.myWriteReportListWithPaging(paraMap);
	            
	               
	      // 페이지 바
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
	         pageBar += "&nbsp;<a href='" +url+ "&currentShowPageNo=" +(pageNo-1)+ "&sizePerPage=" +sizePerPage+ "&search_select=" +search_select+ "&report_search_word=" +report_search_word+ "'>[이전]</a>&nbsp;";     
	      }
	      // 페이지 바
	      while( !(loop>blockSize || pageNo>totalPage) ) {
	         
	         if(pageNo == currentShowPageNo) {
	            pageBar += "&nbsp;<span style='color: red;'>" +pageNo+ "</span>&nbsp;";
	         } else {
	            pageBar += "&nbsp;<a href='" +url+ "&currentShowPageNo=" +pageNo+ "&sizePerPage=" +sizePerPage+ "&search_select=" +search_select+ "&report_search_word=" +report_search_word+ "'>" +pageNo+ "</a>&nbsp;";     
	         }
	         loop++;
	         pageNo++;
	      }
	      // [다음] 버튼
	      if( !(pageNo > totalPage) ) {
	         pageBar += "&nbsp;<a href='" +url+ "&currentShowPageNo=" +pageNo+ "&sizePerPage=" +sizePerPage+ "&search_select=" +search_select+ "&report_search_word=" +report_search_word+ "'>[다음]</a>&nbsp;";
	      }
	      
	      pageBar += "</ul>";
	            
	      mav.addObject("pageBar", pageBar);
	      
	      // 검색 후 검색어 유지
	      if( !"".equals(report_search_word) || !"".equals(search_select) ) {
	         mav.addObject("paraMap", paraMap);
	      }
	            
	      String goBackURL = MyUtil.getCurrentURL(request);
	      mav.addObject("goBackURL", goBackURL);
	      
	      mav.addObject("myReportList", myReportList);
	      
	      mav.setViewName("archive/report/my_report.tiles1");
	      
	      return mav;

	   }
	   
	   
	   ///////////////////////////////////////////////////////////
	   // === 보고서 수신함 목록 보여주기 ===
	   @RequestMapping(value="/receive_report_archive.action")
	   public ModelAndView requireLoginCheck_receive_report_archive(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
	      
	      // view페이지에서 가져오는 값들
	      String search_select = request.getParameter("search_select");
	      String report_search_word = request.getParameter("report_search_word");
	      String search_Time = request.getParameter("search_Time");
	      String fromDate = request.getParameter("fromDate");
	      String toDate = request.getParameter("toDate");
	      
	      if( report_search_word == null || report_search_word.trim().isEmpty() ) {
	         report_search_word = "";
	      }
	      if( search_Time == null || search_Time.trim().isEmpty()) {
	         search_Time = "";
	      }
	      if( fromDate == null || fromDate.trim().isEmpty()) {
	         fromDate = "";
	      }
	      if( toDate == null || toDate.trim().isEmpty()) {
	         toDate = "";
	      }
	      
	      // session에서 가져오는 값들
	      HttpSession session = request.getSession();
	      MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
	      
	      String employeeno = String.valueOf(loginuser.getEmployeeno());
	      String departmentname = String.valueOf(loginuser.getFk_departmentno());
	      switch (departmentname) {
	         case "1":
	            departmentname = "인사팀";
	            break;
	         case "2":
	            departmentname = "마케팅팀";
	            break;
	         case "3":
	            departmentname = "개발1팀";
	            break;
	         case "4":
	            departmentname = "개발2팀";
	            break;
	         case "5":
	            departmentname = "영업팀";
	            break;
	         default :
	            departmentname = "부서등록 필요";
	            break;
	      }
	      
	      
	      HashMap<String, String> paraMap = new HashMap<String, String>();
	      paraMap.put("search_select", search_select);
	      paraMap.put("report_search_word", report_search_word);
	      paraMap.put("employeeno", employeeno);
	      paraMap.put("search_Time", search_Time);
	      paraMap.put("departmentname", departmentname);
	      paraMap.put("fromDate", fromDate);
	      paraMap.put("toDate", toDate);
	         
	      
	      // 페이징처리에 필요한 값들
	      String str_currentShowPageNo = request.getParameter("currentShowPageNo");
	      int totalCount = 0;
	      int sizePerPage = 10;
	      int currentShowPageNo = 0;
	      int totalPage = 0;
	      
	      int startRno = 0;
	      int endRno = 0;
	         
	      if( "".equals(report_search_word) && "".equals(search_Time) && "".equals(fromDate) && "".equals(toDate)) {
	         // 검색조건이 없을경우 총 게시물 수 (totalCount)
	         totalCount = service.getreceiveReportTotalCountWithNoSearch(paraMap);
	      }
	      else {
	         // 검색조건이 있을경우 총 게시물 수 (totalCount)
	         totalCount = service.getreceiveReportTotalCountWithSearch(paraMap);
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
	      
	      
	      // 페이징 글목록 가져오기
	      List<HashMap<String, String>> receiveReportList = service.receiveWriteReportListWithPaging(paraMap);
	      
	               
	      // 페이지 바
	      String pageBar = "<ul class='pageBar' style='list-style: none; padding-left: 0px;'>";
	      
	      int blockSize = 10;
	      int loop = 1;
	      int pageNo = ( (currentShowPageNo - 1) / blockSize ) * blockSize + 1;
	      // 페이지바 한블럭에 첫번째 번호
	      
	      String url = "receive_report_archive.action";
	      String lastStr = url.substring(url.length()-1);
	      if(!"?".equals(lastStr)) {
	         url += "?";
	      }
	      
	      // [이전] 버튼
	      if(pageNo != 1) {
	         pageBar += "&nbsp;<a href='" +url+ "&currentShowPageNo=" +(pageNo-1)+ "&sizePerPage=" +sizePerPage+ "&search_select=" +search_select+ "&report_search_word=" +report_search_word+ "'>[이전]</a>&nbsp;";     
	      }
	      // 페이지 바
	      while( !(loop>blockSize || pageNo>totalPage) ) {
	         
	         if(pageNo == currentShowPageNo) {
	            pageBar += "&nbsp;<span style='color: red;'>" +pageNo+ "</span>&nbsp;";
	         } else {
	            pageBar += "&nbsp;<a href='" +url+ "&currentShowPageNo=" +pageNo+ "&sizePerPage=" +sizePerPage+ "&search_select=" +search_select+ "&report_search_word=" +report_search_word+ "'>" +pageNo+ "</a>&nbsp;";     
	         }
	         loop++;
	         pageNo++;
	      }
	      // [다음] 버튼
	      if( !(pageNo > totalPage) ) {
	         pageBar += "&nbsp;<a href='" +url+ "&currentShowPageNo=" +pageNo+ "&sizePerPage=" +sizePerPage+ "&search_select=" +search_select+ "&report_search_word=" +report_search_word+ "'>[다음]</a>&nbsp;";
	      }
	      
	      pageBar += "</ul>";
	            
	      mav.addObject("pageBar", pageBar);
	      
	      // 검색 후 검색어 유지
	      if( !"".equals(report_search_word) || !"".equals(search_select) ) {
	         mav.addObject("paraMap", paraMap);
	      }
	            
	      String goBackURL = MyUtil.getCurrentURL(request);
	      mav.addObject("goBackURL", goBackURL);
	      
	      mav.addObject("receiveReportList", receiveReportList);
	      
	      
	      mav.setViewName("archive/report/receive_report.tiles1");
	      
	      return mav;
	   }
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   ////////////////////////////////////////////////////////////////////////////////////////
	   ////////////// === 결재문서함 ===  //////////////
	   
	   // === 미결재 문서 목록 보여주기 ===
	   @RequestMapping(value="/incomplete_payment_archive.action")
	   public ModelAndView requireLoginCheck_incomplete_payment_archive(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
	      
	      // view페이지에서 가져오는 값들
	      String search_select = request.getParameter("search_select");
	      String report_search_word = request.getParameter("report_search_word");
	      String search_Time = request.getParameter("search_Time");
	      String fromDate = request.getParameter("fromDate");
	      String toDate = request.getParameter("toDate");
	      
	      if(search_select == null || search_select.trim().isEmpty()) {
	          search_select = "title";
	       }
	      
	      if( report_search_word == null || report_search_word.trim().isEmpty() ) {
	         report_search_word = "";
	      }
	      if( search_Time == null || search_Time.trim().isEmpty()) {
	         search_Time = "";
	      }
	      if( fromDate == null || fromDate.trim().isEmpty()) {
	         fromDate = "";
	      }
	      if( toDate == null || toDate.trim().isEmpty()) {
	         toDate = "";
	      }
	      
	      // session에서 가져오는 값들
	      HttpSession session = request.getSession();
	      MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
	      
	      String employeeno = String.valueOf(loginuser.getEmployeeno());
	      String departmentname = String.valueOf(loginuser.getFk_departmentno());
	      
	      switch (departmentname) {
	         case "1":
	            departmentname = "인사팀";
	            break;
	         case "2":
	            departmentname = "마케팅팀";
	            break;
	         case "3":
	            departmentname = "개발1팀";
	            break;
	         case "4":
	            departmentname = "개발2팀";
	            break;
	         case "5":
	            departmentname = "영업팀";
	            break;
	         default :
	            departmentname = "부서등록 필요";
	            break;
	      }
	      
	      
	      HashMap<String, String> paraMap = new HashMap<String, String>();
	      paraMap.put("search_select", search_select);
	      paraMap.put("report_search_word", report_search_word);
	      paraMap.put("employeeno", employeeno);
	      paraMap.put("search_Time", search_Time);
	      paraMap.put("departmentname", departmentname);
	      paraMap.put("fromDate", fromDate);
	      paraMap.put("toDate", toDate);
	         
	      
	      // 페이징처리에 필요한 값들
	      String str_currentShowPageNo = request.getParameter("currentShowPageNo");
	      int totalCount = 0;
	      int sizePerPage = 10;
	      int currentShowPageNo = 0;
	      int totalPage = 0;
	      
	      int startRno = 0;
	      int endRno = 0;
	         
	      if( "".equals(report_search_word) && "".equals(search_Time) && "".equals(fromDate) && "".equals(toDate)) {
	         // 검색조건이 없을경우 총 게시물 수 (totalCount)
	         totalCount = service.getIncompleteDocumentTotalCountWithNoSearch(paraMap);
	      }
	      else {
	         // 검색조건이 있을경우 총 게시물 수 (totalCount)
	         totalCount = service.getIncompleteDocumentTotalCountWithSearch(paraMap);
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
	      
	      
	      // 페이징 글목록 가져오기
	      List<HashMap<String, String>> incompletePaymentDocumentList = service.incompleteDocumentListWithPaging(paraMap);
	      int listSize = incompletePaymentDocumentList.size();
	               
	      // 페이지 바
	      String pageBar = "<ul class='pageBar' style='list-style: none; padding-left: 0px;'>";
	      
	      int blockSize = 10;
	      int loop = 1;
	      int pageNo = ( (currentShowPageNo - 1) / blockSize ) * blockSize + 1;
	      // 페이지바 한블럭에 첫번째 번호
	      
	      String url = "incomplete_payment_archive.action";
	      String lastStr = url.substring(url.length()-1);
	      if(!"?".equals(lastStr)) {
	         url += "?";
	      }
	      
	      // [이전] 버튼
	      if(pageNo != 1) {
	         pageBar += "&nbsp;<a href='" +url+ "&currentShowPageNo=" +(pageNo-1)+ "&sizePerPage=" +sizePerPage+ "&search_select=" +search_select+ "&report_search_word=" +report_search_word+ "'>[이전]</a>&nbsp;";     
	      }
	      // 페이지 바
	      while( !(loop>blockSize || pageNo>totalPage) ) {
	         
	         if(pageNo == currentShowPageNo) {
	            pageBar += "&nbsp;<span style='color: red;'>" +pageNo+ "</span>&nbsp;";
	         } else {
	            pageBar += "&nbsp;<a href='" +url+ "&currentShowPageNo=" +pageNo+ "&sizePerPage=" +sizePerPage+ "&search_select=" +search_select+ "&report_search_word=" +report_search_word+ "'>" +pageNo+ "</a>&nbsp;";     
	         }
	         loop++;
	         pageNo++;
	      }
	      // [다음] 버튼
	      if( !(pageNo > totalPage) ) {
	         pageBar += "&nbsp;<a href='" +url+ "&currentShowPageNo=" +pageNo+ "&sizePerPage=" +sizePerPage+ "&search_select=" +search_select+ "&report_search_word=" +report_search_word+ "'>[다음]</a>&nbsp;";
	      }
	      
	      pageBar += "</ul>";
	            
	      mav.addObject("pageBar", pageBar);
	      
	      // 검색 후 검색어 유지
	      // 엑셀파일로 저장할때 더 필요함.
	      if( !"".equals(report_search_word) || !"".equals(report_search_word) ) {
	         mav.addObject("paraMap", paraMap);
	      }
	      if( !"".equals(search_select) || !"".equals(search_select) ) {
	         mav.addObject("paraMap", paraMap);
	      }
	      if( !"".equals(search_Time) || !"".equals(search_Time) ) {
	         mav.addObject("paraMap", paraMap);
	      }
	      if( !"".equals(fromDate) || !"".equals(fromDate) ) {
	         mav.addObject("paraMap", paraMap);
	      }
	      if( !"".equals(toDate) || !"".equals(toDate) ) {
	         mav.addObject("paraMap", paraMap);
	      }
	      
	      String goBackURL = MyUtil.getCurrentURL(request);
	      mav.addObject("goBackURL", goBackURL);
	      
	      mav.addObject("incompletePaymentDocumentList", incompletePaymentDocumentList);
	      
	      mav.addObject("listSize", listSize); // 총 사이즈 
	      mav.setViewName("archive/payment/incomplete.tiles1");
	      
	      return mav;
	   }
	   
	   
	//////////////////////////////////////////////////////////////////////////////////////////////
	// 미결제 문서함 리스트 엑셀파일로 다운로드하기
	@RequestMapping(value="/incomplete_payment_downloadExcelFile.action", method= {RequestMethod.POST})
	public String incomplete_payment_downloadExcelFile(HttpServletRequest request, Model model) {
	
		// view페이지에서 가져오는 값들
		String search_select = request.getParameter("search_select");
		String report_search_word = request.getParameter("report_search_word");
		String search_Time = request.getParameter("search_Time");
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		
		if(search_select == null || search_select.trim().isEmpty()) {
	         search_select = "title";
	      }
		if( report_search_word == null || report_search_word.trim().isEmpty() ) {
			report_search_word = "";
		}
		if( search_Time == null || search_Time.trim().isEmpty()) {
			search_Time = "";
		}
		if( fromDate == null || fromDate.trim().isEmpty()) {
			fromDate = "";
		}
		if( toDate == null || toDate.trim().isEmpty()) {
			toDate = "";
		}
		
		// session에서 가져오는 값들
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		String employeeno = String.valueOf(loginuser.getEmployeeno());
		String departmentname = String.valueOf(loginuser.getFk_departmentno());
		switch (departmentname) {
			case "1":
				departmentname = "인사팀";
				break;
			case "2":
				departmentname = "마케팅팀";
				break;
			case "3":
				departmentname = "개발1팀";
				break;
			case "4":
				departmentname = "개발2팀";
				break;
			case "5":
				departmentname = "영업팀";
				break;
			default :
				departmentname = "부서등록 필요";
			break;
		}
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("search_select", search_select);
		paraMap.put("report_search_word", report_search_word);
		paraMap.put("employeeno", employeeno);
		paraMap.put("search_Time", search_Time);
		paraMap.put("departmentname", departmentname);
		paraMap.put("fromDate", fromDate);
		paraMap.put("toDate", toDate);
		
		List<HashMap<String, String>> incompletePaymentDocumentList = service.incompleteDocumentListWithPaging(paraMap);
		
		
		// 시트를 생성하고, 행을 생성하고, 셀을 생성하고, 셀 안에 내용을 넣어준다.
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		
		// 시트생성
		SXSSFSheet sheet = workbook.createSheet("결재 문서 목록");   // 시트이름
		
		// 시트 열 너비 설정(이 경우 열이 8개 이므로 인덱스번호 0~7까지를 적어준다.)
		sheet.setColumnWidth(1, 1500);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 15000);
		sheet.setColumnWidth(5, 3000);
		sheet.setColumnWidth(6, 3000);
		sheet.setColumnWidth(7, 3000);
		sheet.setColumnWidth(8, 5000);
		
		// 행의 위치
		int rowLocation = 0;
		
		// CellStyle 정렬하기(Alignment)
		// CellStyle 객체를 생성
		// HorizontalAlignment(가로) VerticalAlignment(세로) 정렬
		CellStyle mergeRowStyle = workbook.createCellStyle();
		mergeRowStyle.setAlignment(HorizontalAlignment.CENTER);
		mergeRowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HorizontalAlignment.LEFT);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		CellStyle contentStyle = workbook.createCellStyle();
		contentStyle.setAlignment(HorizontalAlignment.CENTER);
		contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		
		// CellStyle 배경색(ForegroundColor)만들기
		// setFillForegroundColor 메소드에 IndexedColors Enum인자를 사용한다.
		// setFillPattern은 해당 색을 어떤 패턴으로 입힐지를 정한다.
		mergeRowStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex()); // getIndex : 색상에 대한 RGB 값을 가져오는 것.
		mergeRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
		
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		
		// Cell 폰트(Font) 설정하기
		// 해당 객체의 세터를 사용해 폰트를 설정. 대표적으로 글씨체, 크기, 색상, 굵기설정.
		// 이후 CellStyle의 setFont 메소드를 사용해 인자로 폰트를 넣어준다.
		Font mergeRowFont = workbook.createFont();
		mergeRowFont.setFontName("나눔고딕");
		mergeRowFont.setFontHeight((short)500);
		mergeRowFont.setColor(IndexedColors.GREY_80_PERCENT.getIndex());
		mergeRowFont.setBold(true);
		
		Font headRowFont = workbook.createFont();
		headRowFont.setFontHeight((short)300);
		headRowFont.setColor(IndexedColors.WHITE.getIndex());
		
		mergeRowStyle.setFont(mergeRowFont);
		headerStyle.setFont(headRowFont);
		
		
		// CellStyle 테두리 Border
		// setBorderTop, Bottom, Left, Right 메소드와 POI라이브러리의 BorderStyle 인자를 넣어서 적용.
		headerStyle.setBorderTop(BorderStyle.NONE);
		headerStyle.setBorderBottom(BorderStyle.NONE);
		headerStyle.setBorderLeft(BorderStyle.NONE);
		headerStyle.setBorderRight(BorderStyle.NONE);
		
		
		
		// Cell Merge 셀 병합시키기
		/* 셀병합은 시트의 addMergeRegion 메소드에 CellRangeAddress 객체를 인자로 하여 병합시킨다.
		CellRangeAddress 생성자의 인자로(시작 행, 끝 행, 시작 열, 끝 열) 순서대로 넣어서 병합시킬 범위를 정한다. 배열처럼 시작은 0부터이다.  
		*/
		// 병합할 행 만들기
		Row mergeRow = sheet.createRow(rowLocation);  // 엑셀에서 행의 시작은 0 부터 시작한다.
		
		// 병합할 행에  셀을 만들어 셀에 스타일을 주기 
		for(int i=1; i<9; i++) {
			Cell cell = mergeRow.createCell(i);
			cell.setCellStyle(mergeRowStyle);
			cell.setCellValue("결재 문서 리스트");
		}
		
		// 셀 병합하기 
		sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 1, 8)); // 시작 행, 끝 행, 시작 열, 끝 열 
		
		//// CellStyle 천단위 쉼표, 금액
		//CellStyle moneyStyle = workbook.createCellStyle();
		//moneyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
		////////////////////////////////////////////////////////////////////////////////////////
		
		
		
		// 헤더 행 생성 (행 이름이 있는 첫번째 행)
		Row headerRow = sheet.createRow(++rowLocation);      // 0을 적어주면 엑셀의 시트 가장 첫번째 행에 헤더를 생성한다.
		// ++ 전위연산자. ()안의 행인덱스 앞에 적어주면 해당 행의 그 다음 행부터 헤더를 생성한다.
		
		// 해당 행의 첫번째 열 셀 생성
		Cell headerCell = headerRow.createCell(1);          // 엑셀에서 열의 시작은 0 부터 시작한다.
		headerCell.setCellValue("No.");
		headerCell.setCellStyle(headerStyle);        
		// 해당 행의 두번째 열 셀 생성
		headerCell = headerRow.createCell(2);
		headerCell.setCellValue("문서번호");
		headerCell.setCellStyle(headerStyle);
		// 해당 행의 세번째 열 셀 생성
		headerCell = headerRow.createCell(3);
		headerCell.setCellValue("문서분류");
		headerCell.setCellStyle(headerStyle);
		// 해당 행의 네번째 열 셀 생성
		headerCell = headerRow.createCell(4);
		headerCell.setCellValue("제목");
		headerCell.setCellStyle(headerStyle);
		// 해당 행의 다섯번째 열 셀 생성
		headerCell = headerRow.createCell(5);
		headerCell.setCellValue("상태");
		headerCell.setCellStyle(headerStyle);
		// 해당 행의 여섯번째 열 셀 생성
		headerCell = headerRow.createCell(6);
		headerCell.setCellValue("부서");
		headerCell.setCellStyle(headerStyle);
		// 해당 행의 일곱번째 열 셀 생성
		headerCell = headerRow.createCell(7);
		headerCell.setCellValue("기안자");
		headerCell.setCellStyle(headerStyle);
		// 해당 행의 여덟번째 열 셀 생성
		headerCell = headerRow.createCell(8);
		headerCell.setCellValue("작성일");
		headerCell.setCellStyle(headerStyle);
		
		// 시트 내용에 해당하는 행 및 셀 생성하기 
		Row bodyRow = null;
		Cell bodyCell = null;
		
		for(int i=0; i<incompletePaymentDocumentList.size(); i++) {            // incompletePaymentDocumentList : DB에서 읽어온 값
		
			HashMap<String,String> incomdocuMap = incompletePaymentDocumentList.get(i);
			
			// 행 생성
			bodyRow = sheet.createRow(i + (rowLocation+1) );               // headerRow의 그 다음 행부터 값을 넣어줘야하기 때문에 rowLocation+1을 해줬다.
			
			bodyCell = bodyRow.createCell(1);
			bodyCell.setCellValue(incomdocuMap.get("rno"));
			
			bodyCell = bodyRow.createCell(2);
			bodyCell.setCellValue(incomdocuMap.get("textnumber"));
			
			bodyCell = bodyRow.createCell(3);
			bodyCell.setCellValue(incomdocuMap.get("papersname"));
			
			
			bodyCell = bodyRow.createCell(4);
			bodyCell.setCellValue(incomdocuMap.get("title"));
			bodyCell.setCellStyle(titleStyle);
		
		
			bodyCell = bodyRow.createCell(5);
			String status = "";
			
			if( incomdocuMap.get("status").equals("0") ) {
				incomdocuMap.replace("status","미결재 상태");
			}
			if( incomdocuMap.get("status").equals("1") ) {
				incomdocuMap.replace("status", "결재완료");
			}
			
			bodyCell.setCellValue(incomdocuMap.get("status"));
			
			
			bodyCell = bodyRow.createCell(6);
			bodyCell.setCellValue(incomdocuMap.get("departmentname"));   // 월급은 수식의 계산이 필요할 때를 위해 정수형으로 변형해 줬다.
			
			bodyCell = bodyRow.createCell(7);
			bodyCell.setCellValue(incomdocuMap.get("employeename"));
			
			bodyCell = bodyRow.createCell(8);
			bodyCell.setCellValue(incomdocuMap.get("writeday"));         // 나이도 월급과 마찬가지로 수식의 계산이 필요할 때를 위해 정수형으로 변형해 줬다.
		
		}// end of for----------------------------------
		
		// request.setAttribute의 역할
		model.addAttribute("locale", Locale.KOREA);                        // Locale : 엑셀에서 해당 지역 통화기호를 사용하기 위해 표시해줘야 한다.
		model.addAttribute("workbook", workbook);
		model.addAttribute("workbookName", "결재 문서 목록");
		
		return "excelDownloadView"; 
	}
	   
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   
	   
	
	   
	   
	   
	   ////////////////////////////////////////////////////////////////////////
	   // === 내가 받은 (내가 결재권자인) 결재문서 수신함  목록 보여주기 ===
	   @RequestMapping(value="/receive_payment_archive.action")
	   public ModelAndView requireLoginCheck_receive_payment_archive(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
	      // view페이지에서 가져오는 값들
	      String search_select = request.getParameter("search_select");
	      String report_search_word = request.getParameter("report_search_word");
	      String search_Time = request.getParameter("search_Time");
	      String fromDate = request.getParameter("fromDate");
	      String toDate = request.getParameter("toDate");
	      
	      if( report_search_word == null || report_search_word.trim().isEmpty() ) {
	         report_search_word = "";
	      }
	      if( search_Time == null || search_Time.trim().isEmpty()) {
	         search_Time = "";
	      }
	      if( fromDate == null || fromDate.trim().isEmpty()) {
	         fromDate = "";
	      }
	      if( toDate == null || toDate.trim().isEmpty()) {
	         toDate = "";
	      }
	      
	      // session에서 가져오는 값들
	      HttpSession session = request.getSession();
	      MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
	      
	      String employeeno = String.valueOf(loginuser.getEmployeeno());
	      String departmentname = String.valueOf(loginuser.getFk_departmentno());
	      switch (departmentname) {
	         case "1":
	            departmentname = "인사팀";
	            break;
	         case "2":
	            departmentname = "마케팅팀";
	            break;
	         case "3":
	            departmentname = "개발1팀";
	            break;
	         case "4":
	            departmentname = "개발2팀";
	            break;
	         case "5":
	            departmentname = "영업팀";
	            break;
	         default :
	            departmentname = "부서등록 필요";
	            break;
	      }
	      
	      
	      HashMap<String, String> paraMap = new HashMap<String, String>();
	      paraMap.put("search_select", search_select);
	      paraMap.put("report_search_word", report_search_word);
	      paraMap.put("employeeno", employeeno);
	      paraMap.put("search_Time", search_Time);
	      paraMap.put("departmentname", departmentname);
	      paraMap.put("fromDate", fromDate);
	      paraMap.put("toDate", toDate);
	         
	      
	      // 페이징처리에 필요한 값들
	      String str_currentShowPageNo = request.getParameter("currentShowPageNo");
	      int totalCount = 0;
	      int sizePerPage = 10;
	      int currentShowPageNo = 0;
	      int totalPage = 0;
	      
	      int startRno = 0;
	      int endRno = 0;
	         
	      if( "".equals(report_search_word) && "".equals(search_Time) && "".equals(fromDate) && "".equals(toDate)) {
	         // 검색조건이 없을경우 총 게시물 수 (totalCount)
	         totalCount = service.getreceivePaymentDocumentTotalCountWithNoSearch(paraMap);
	      }
	      else {
	         // 검색조건이 있을경우 총 게시물 수 (totalCount)
	         totalCount = service.getreceivePaymentDocumentTotalCountWithSearch(paraMap);
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
	      
	      
	      // 페이징 글목록 가져오기
	      List<HashMap<String, String>> receiveDocumentList = service.receiveDocumentListWithPaging(paraMap);
	      
	      
	               
	      // 페이지 바
	      String pageBar = "<ul class='pageBar' style='list-style: none; padding-left: 0px;'>";
	      
	      int blockSize = 10;
	      int loop = 1;
	      int pageNo = ( (currentShowPageNo - 1) / blockSize ) * blockSize + 1;
	      // 페이지바 한블럭에 첫번째 번호
	      
	      String url = "receive_payment_archive.action";
	      String lastStr = url.substring(url.length()-1);
	      if(!"?".equals(lastStr)) {
	         url += "?";
	      }
	      
	      // [이전] 버튼
	      if(pageNo != 1) {
	         pageBar += "&nbsp;<a href='" +url+ "&currentShowPageNo=" +(pageNo-1)+ "&sizePerPage=" +sizePerPage+ "&search_select=" +search_select+ "&report_search_word=" +report_search_word+ "'>[이전]</a>&nbsp;";     
	      }
	      // 페이지 바
	      while( !(loop>blockSize || pageNo>totalPage) ) {
	         
	         if(pageNo == currentShowPageNo) {
	            pageBar += "&nbsp;<span style='color: red;'>" +pageNo+ "</span>&nbsp;";
	         } else {
	            pageBar += "&nbsp;<a href='" +url+ "&currentShowPageNo=" +pageNo+ "&sizePerPage=" +sizePerPage+ "&search_select=" +search_select+ "&report_search_word=" +report_search_word+ "'>" +pageNo+ "</a>&nbsp;";     
	         }
	         loop++;
	         pageNo++;
	      }
	      // [다음] 버튼
	      if( !(pageNo > totalPage) ) {
	         pageBar += "&nbsp;<a href='" +url+ "&currentShowPageNo=" +pageNo+ "&sizePerPage=" +sizePerPage+ "&search_select=" +search_select+ "&report_search_word=" +report_search_word+ "'>[다음]</a>&nbsp;";
	      }
	      
	      pageBar += "</ul>";
	            
	      mav.addObject("pageBar", pageBar);
	      
	      // 검색 후 검색어 유지
	      // 엑셀파일로 저장할때 더 필요함.
	      if( !"".equals(report_search_word) || !"".equals(report_search_word) ) {
	         mav.addObject("paraMap", paraMap);
	      }
	      if( !"".equals(search_select) || !"".equals(search_select) ) {
	         mav.addObject("paraMap", paraMap);
	      }
	      if( !"".equals(search_Time) || !"".equals(search_Time) ) {
	         mav.addObject("paraMap", paraMap);
	      }
	      if( !"".equals(fromDate) || !"".equals(fromDate) ) {
	         mav.addObject("paraMap", paraMap);
	      }
	      if( !"".equals(toDate) || !"".equals(toDate) ) {
	         mav.addObject("paraMap", paraMap);
	      }
	      
	  // System.out.println("~~"+receiveDocumentList);
	      
	      String goBackURL = MyUtil.getCurrentURL(request);
	      mav.addObject("goBackURL", goBackURL);
	      
	      mav.addObject("receiveDocumentList", receiveDocumentList);
	      
	      mav.setViewName("archive/payment/receive_payment.tiles1");
	      
	      return mav;
	   }
	
	   
	//////////////////////////////////////////////////////////////////////////////////////////////
	// 내가 받은 (내가 결재권자인) 결재문서 수신함  리스트 엑셀파일로 다운로드하기
	@RequestMapping(value="/receive_payment_downloadExcelFile.action", method= {RequestMethod.POST})
	public String receive_payment_downloadExcelFile(HttpServletRequest request, Model model) {
	
		// view페이지에서 가져오는 값들
		String search_select = request.getParameter("search_select");
		String report_search_word = request.getParameter("report_search_word");
		String search_Time = request.getParameter("search_Time");
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		
		if(search_select == null || search_select.trim().isEmpty()) {
	         search_select = "title";
	      }
		if( report_search_word == null || report_search_word.trim().isEmpty() ) {
			report_search_word = "";
		}
		if( search_Time == null || search_Time.trim().isEmpty()) {
			search_Time = "";
		}
		if( fromDate == null || fromDate.trim().isEmpty()) {
			fromDate = "";
		}
		if( toDate == null || toDate.trim().isEmpty()) {
			toDate = "";
		}
		
		// session에서 가져오는 값들
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		String employeeno = String.valueOf(loginuser.getEmployeeno());
		String departmentname = String.valueOf(loginuser.getFk_departmentno());
		switch (departmentname) {
			case "1":
				departmentname = "인사팀";
			break;
			case "2":
				departmentname = "마케팅팀";
			break;
			case "3":
				departmentname = "개발1팀";
			break;
			case "4":
				departmentname = "개발2팀";
			break;
			case "5":
				departmentname = "영업팀";
			break;
			default :
				departmentname = "부서등록 필요";
			break;
		}
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("search_select", search_select);
		paraMap.put("report_search_word", report_search_word);
		paraMap.put("employeeno", employeeno);
		paraMap.put("search_Time", search_Time);
		paraMap.put("departmentname", departmentname);
		paraMap.put("fromDate", fromDate);
		paraMap.put("toDate", toDate);
		
		List<HashMap<String, String>> receiveDocumentList = service.receiveDocumentListWithPaging(paraMap);
		
		
		// 시트를 생성하고, 행을 생성하고, 셀을 생성하고, 셀 안에 내용을 넣어준다.
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		
		// 시트생성
		SXSSFSheet sheet = workbook.createSheet("결재해야할 문서 목록");   // 시트이름
		
		// 시트 열 너비 설정(이 경우 열이 8개 이므로 인덱스번호 0~7까지를 적어준다.)
		sheet.setColumnWidth(1, 1500);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 15000);
		sheet.setColumnWidth(5, 3000);
		sheet.setColumnWidth(6, 3000);
		sheet.setColumnWidth(7, 3000);
		sheet.setColumnWidth(8, 5000);
		
		// 행의 위치
		int rowLocation = 0;
		
		// CellStyle 정렬하기(Alignment)
		// CellStyle 객체를 생성
		// HorizontalAlignment(가로) VerticalAlignment(세로) 정렬
		CellStyle mergeRowStyle = workbook.createCellStyle();
		mergeRowStyle.setAlignment(HorizontalAlignment.CENTER);
		mergeRowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HorizontalAlignment.LEFT);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		CellStyle contentStyle = workbook.createCellStyle();
		contentStyle.setAlignment(HorizontalAlignment.CENTER);
		contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		
		// CellStyle 배경색(ForegroundColor)만들기
		// setFillForegroundColor 메소드에 IndexedColors Enum인자를 사용한다.
		// setFillPattern은 해당 색을 어떤 패턴으로 입힐지를 정한다.
		mergeRowStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex()); // getIndex : 색상에 대한 RGB 값을 가져오는 것.
		mergeRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
		
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		
		// Cell 폰트(Font) 설정하기
		// 해당 객체의 세터를 사용해 폰트를 설정. 대표적으로 글씨체, 크기, 색상, 굵기설정.
		// 이후 CellStyle의 setFont 메소드를 사용해 인자로 폰트를 넣어준다.
		Font mergeRowFont = workbook.createFont();
		mergeRowFont.setFontName("나눔고딕");
		mergeRowFont.setFontHeight((short)500);
		mergeRowFont.setColor(IndexedColors.GREY_80_PERCENT.getIndex());
		mergeRowFont.setBold(true);
		
		Font headRowFont = workbook.createFont();
		headRowFont.setFontHeight((short)300);
		headRowFont.setColor(IndexedColors.WHITE.getIndex());
		
		mergeRowStyle.setFont(mergeRowFont);
		headerStyle.setFont(headRowFont);
		
		
		// CellStyle 테두리 Border
		// setBorderTop, Bottom, Left, Right 메소드와 POI라이브러리의 BorderStyle 인자를 넣어서 적용.
		headerStyle.setBorderTop(BorderStyle.NONE);
		headerStyle.setBorderBottom(BorderStyle.NONE);
		headerStyle.setBorderLeft(BorderStyle.NONE);
		headerStyle.setBorderRight(BorderStyle.NONE);
		
		
		
		// Cell Merge 셀 병합시키기
		/* 셀병합은 시트의 addMergeRegion 메소드에 CellRangeAddress 객체를 인자로 하여 병합시킨다.
		CellRangeAddress 생성자의 인자로(시작 행, 끝 행, 시작 열, 끝 열) 순서대로 넣어서 병합시킬 범위를 정한다. 배열처럼 시작은 0부터이다.  
		*/
		// 병합할 행 만들기
		Row mergeRow = sheet.createRow(rowLocation);  // 엑셀에서 행의 시작은 0 부터 시작한다.
		
		// 병합할 행에 우리회사 사원정보로 셀을 만들어 셀에 스타일을 주기 
		for(int i=0; i<8; i++) {
			Cell cell = mergeRow.createCell(i);
			cell.setCellStyle(mergeRowStyle);
			cell.setCellValue("결재 문서 리스트");
		}
		
		// 셀 병합하기 
		sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 1, 8)); // 시작 행, 끝 행, 시작 열, 끝 열 
		
		//// CellStyle 천단위 쉼표, 금액
		//CellStyle moneyStyle = workbook.createCellStyle();
		//moneyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
		////////////////////////////////////////////////////////////////////////////////////////
		
		
		
		// 헤더 행 생성 (행 이름이 있는 첫번째 행)
		Row headerRow = sheet.createRow(++rowLocation);      // 0을 적어주면 엑셀의 시트 가장 첫번째 행에 헤더를 생성한다.
		// ++ 전위연산자. ()안의 행인덱스 앞에 적어주면 해당 행의 그 다음 행부터 헤더를 생성한다.
		
		// 해당 행의 첫번째 열 셀 생성
		Cell headerCell = headerRow.createCell(1);          // 엑셀에서 열의 시작은 0 부터 시작한다.
		headerCell.setCellValue("No.");
		headerCell.setCellStyle(headerStyle);        
		// 해당 행의 두번째 열 셀 생성
		headerCell = headerRow.createCell(2);
		headerCell.setCellValue("문서번호");
		headerCell.setCellStyle(headerStyle);
		// 해당 행의 세번째 열 셀 생성
		headerCell = headerRow.createCell(3);
		headerCell.setCellValue("문서분류");
		headerCell.setCellStyle(headerStyle);
		// 해당 행의 네번째 열 셀 생성
		headerCell = headerRow.createCell(4);
		headerCell.setCellValue("제목");
		headerCell.setCellStyle(headerStyle);
		// 해당 행의 다섯번째 열 셀 생성
		headerCell = headerRow.createCell(5);
		headerCell.setCellValue("상태");
		headerCell.setCellStyle(headerStyle);
		// 해당 행의 여섯번째 열 셀 생성
		headerCell = headerRow.createCell(6);
		headerCell.setCellValue("부서");
		headerCell.setCellStyle(headerStyle);
		// 해당 행의 일곱번째 열 셀 생성
		headerCell = headerRow.createCell(7);
		headerCell.setCellValue("기안자");
		headerCell.setCellStyle(headerStyle);
		// 해당 행의 여덟번째 열 셀 생성
		headerCell = headerRow.createCell(8);
		headerCell.setCellValue("작성일");
		headerCell.setCellStyle(headerStyle);
		
		// 시트 내용에 해당하는 행 및 셀 생성하기 
		Row bodyRow = null;
		Cell bodyCell = null;
		
		for(int i=0; i<receiveDocumentList.size(); i++) {            // incompletePaymentDocumentList : DB에서 읽어온 값
		
			HashMap<String,String> receivedocuMap = receiveDocumentList.get(i);
			
			// 행 생성
			bodyRow = sheet.createRow(i + (rowLocation+1) );               // headerRow의 그 다음 행부터 값을 넣어줘야하기 때문에 rowLocation+1을 해줬다.
			
			
			bodyCell = bodyRow.createCell(1);
			bodyCell.setCellValue(receivedocuMap.get("rno"));
			bodyCell.setCellStyle(titleStyle);
			
			
			bodyCell = bodyRow.createCell(2);
			bodyCell.setCellValue(receivedocuMap.get("textnumber"));
			
			
			bodyCell = bodyRow.createCell(3);
			bodyCell.setCellValue(receivedocuMap.get("papersname"));
			bodyCell.setCellStyle(titleStyle);
			
			
			bodyCell = bodyRow.createCell(4);
			bodyCell.setCellValue(receivedocuMap.get("title"));
			bodyCell.setCellStyle(titleStyle);
			
			
			bodyCell = bodyRow.createCell(5);
			bodyCell.setCellValue(receivedocuMap.get("status"));
			bodyCell.setCellStyle(titleStyle);
			
			bodyCell = bodyRow.createCell(6);
			bodyCell.setCellValue(receivedocuMap.get("departmentname"));   // 월급은 수식의 계산이 필요할 때를 위해 정수형으로 변형해 줬다.
			bodyCell.setCellStyle(titleStyle);  
			
			bodyCell = bodyRow.createCell(7);
			bodyCell.setCellValue(receivedocuMap.get("employeename"));
			bodyCell.setCellStyle(titleStyle);
			
			bodyCell = bodyRow.createCell(8);
			bodyCell.setCellValue(receivedocuMap.get("writeday"));         // 나이도 월급과 마찬가지로 수식의 계산이 필요할 때를 위해 정수형으로 변형해 줬다.
		
		}// end of for----------------------------------
		
		// request.setAttribute의 역할
		model.addAttribute("locale", Locale.KOREA);                        // Locale : 엑셀에서 해당 지역 통화기호를 사용하기 위해 표시해줘야 한다.
		model.addAttribute("workbook", workbook);
		model.addAttribute("workbookName", "결재 문서 목록");
		
		return "excelDownloadView";
	}
	   
	

// ---------------------------------------------------------------------------------------------------------------//
	// 여기서 부터는 상세보기를 하기 위한 것이다. //
	
	//////////////////결재자 view 단 /////////////////////////////////////////////////////////////
	
	// 지출 결재란 ajax
	@ResponseBody
	@RequestMapping(value="/approverAjax.action", produces="text/plain;charset=UTF-8")
	public String approverAjax(HttpServletRequest request) {
		
		String number = request.getParameter("textnumber");

		List<HashMap<String,String>> appMap = service.appAjax(number);

		JSONArray jsonArr = new JSONArray();
		for(HashMap<String,String> map : appMap) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("EXPENDITURENO", map.get("EXPENDITURENO"));
			jsonObj.put("FK_EMPLOYEENO", map.get("FK_EMPLOYEENO"));
			jsonObj.put("WRITEDAY", map.get("WRITEDAY"));
			jsonObj.put("EXPENDITUREDAY", map.get("EXPENDITUREDAY"));
			jsonObj.put("EXPENDITUREMODE", map.get("EXPENDITUREMODE"));
			jsonObj.put("TITLE", map.get("TITLE"));
			jsonObj.put("CONTENT", map.get("CONTENT"));
			jsonObj.put("SHAREDEPARTMENTNO", map.get("SHAREDEPARTMENTNO"));
			jsonObj.put("STATUS", map.get("STATUS"));
			jsonObj.put("APPROVER", map.get("APPROVER"));
			jsonObj.put("DEPARTMENTNAME", map.get("DEPARTMENTNAME"));
			jsonObj.put("FILENAME", map.get("FILENAME"));
			jsonObj.put("ORGFILENAME", map.get("ORGFILENAME"));
			jsonObj.put("FILESIZE", map.get("FILESIZE"));
			jsonObj.put("PAPERSNAME", map.get("PAPERSNAME"));
			jsonObj.put("EMPLOYEENAME", map.get("EMPLOYEENAME"));
			jsonObj.put("TEXTNUMBER", map.get("TEXTNUMBER"));
			jsonObj.put("PAYMENT_MONEY", map.get("PAYMENT_MONEY"));
			jsonObj.put("APPROVER_NAME", map.get("APPROVER_NAME"));
			
			jsonArr.put(jsonObj);			
		}
		return jsonArr.toString();
	}
	
	
	// 지출
	@RequestMapping(value="/approver_ex.action", method= {RequestMethod.POST})
	public String requireLoginCheck_approver_ex (HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		String employeeno = String.valueOf(loginuser.getEmployeeno());
		
		String textnumber = request.getParameter("textnumber");
	//	System.out.println(textnumber);
		String employeename = request.getParameter("employeename");
	//	System.out.println(employeename);
		
		
		HashMap<String, String> paraMap = new HashMap<String,String>();
		paraMap.put("employeeno", employeeno);
		paraMap.put("textnumber", textnumber);
		paraMap.put("employeename", employeename);
		
	//	System.out.println(textnumber);
		
		List<HashMap<String,String>> detailList = service.detailList(paraMap);
	//	System.out.println(detailList);
		String CONTENT = detailList.get(0).get("CONTENT");
	//	System.out.println(CONTENT);
		String EXPENDITUREMODE = detailList.get(0).get("EXPENDITUREMODE");
	//	System.out.println(EXPENDITUREMODE);

		CONTENT = CONTENT.replaceAll("<br/>", "\r\n");
				
		request.setAttribute("textnumber", textnumber);
		request.setAttribute("CONTENT", CONTENT);
		request.setAttribute("EXPENDITUREMODE", EXPENDITUREMODE);
		request.setAttribute("detailList", detailList);
		
		return "approver/exReport.tiles1";
	}
	
	// 지출 결재서 파일 다운로드
	@RequestMapping(value = "/exportdownload.action", method= {RequestMethod.GET})
	public void requireLoginCheck_download(HttpServletRequest request, HttpServletResponse response) {
		String textnumber = request.getParameter("textnumber"); 
	
		ExReportVO exreportvo = service.getViewWithNoAddCount(textnumber);
		
		 
		String fileName = exreportvo.getFileName(); 
		String orgFilename = exreportvo.getOrgFilename(); 
		
		HttpSession session = request.getSession();
		
		String root = session.getServletContext().getRealPath("/"); 
		String path = root + "resources"+File.separator+"files";
		
		boolean flag = false;
		
		flag = fileManager.doFileDownload(fileName, orgFilename, path, response);
		
		
		if(!flag) {
			
			
			response.setContentType("text/html; charset=UTF-8"); 
			PrintWriter writer = null;
			
			try {
				writer = response.getWriter();
			} catch (IOException e) {
				
			}
			
			writer.println("<script type='text/javascript'>alert('파일 다운로드가 불가능합니다.!!')</script>");       
			
		}// end of if()----------------------------		
	} // end of requireLoginCheck_download()-----------------------------------------
	
	// ====  스마트에디터. 드래그앤드롭을 사용한 다중사진 파일업로드 ====
		@RequestMapping(value="/image/multiplePhotoUpload.action" )
		public void multiplePhotoUpload(HttpServletRequest req, HttpServletResponse res) {
		    

			HttpSession session = req.getSession();
			String root = session.getServletContext().getRealPath("/"); 
			String path = root + "resources"+File.separator+"photo_upload";
				
			File dir = new File(path);
			if(!dir.exists())
			    dir.mkdirs();
				
			String strURL = "";
				
			try {
				if(!"OPTIONS".equals(req.getMethod().toUpperCase())) {
				    String filename = req.getHeader("file-name"); //파일명을 받는다 - 일반 원본파일명

			    	   InputStream is = req.getInputStream();
			    	   String newFilename = fileManager.doFileUpload(is, filename, path);
			    	
				   int width = fileManager.getImageWidth(path+File.separator+newFilename);
					
				   if(width > 600)
				      width = 600;

				   String CP = req.getContextPath(); // board
					
				   strURL += "&bNewLine=true&sFileName="; 
		            	   strURL += newFilename;
		            	   strURL += "&sWidth="+width;
		            	   strURL += "&sFileURL="+CP+"/resources/photo_upload/"+newFilename;
			    	}
				
			    	/// 웹브라우저상에 사진 이미지를 쓰기 ///
				   PrintWriter out = res.getWriter();
				   out.print(strURL);
			} catch(Exception e){
					e.printStackTrace();
			}
	   
	   }
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	// 휴가 결재란 ajax
	@ResponseBody
	@RequestMapping(value="/approverLeaveAjax.action", produces="text/plain;charset=UTF-8")
	public String approverLeaveAjax(HttpServletRequest request) {
		
		String number = request.getParameter("textnumber");
		
		List<HashMap<String,String>> appMap = service.appLeaveAjax(number);
			
		JSONArray jsonArr = new JSONArray();
		for(HashMap<String,String> map : appMap) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("VACATIONNO", map.get("VACATIONNO"));
			jsonObj.put("FK_EMPLOYEENO", map.get("FK_EMPLOYEENO"));
			jsonObj.put("WRITEDAY", map.get("WRITEDAY"));
			jsonObj.put("STARTDAY", map.get("STARTDAY"));
			jsonObj.put("ENDDAY", map.get("ENDDAY"));
			jsonObj.put("TITLE", map.get("TITLE"));
			jsonObj.put("REASON", map.get("REASON"));
			jsonObj.put("EMERGENCYCONTACTNETWORK", map.get("EMERGENCYCONTACTNETWORK"));
			jsonObj.put("SHAREDEPARTMENTNO", map.get("SHAREDEPARTMENTNO"));
			jsonObj.put("STATUS", map.get("STATUS"));
			jsonObj.put("APPROVER", map.get("APPROVER"));
			jsonObj.put("APPROVER_NAME", map.get("APPROVER_NAME"));
			jsonObj.put("DEPARTMENTNAME", map.get("DEPARTMENTNAME"));
			jsonObj.put("RANK", map.get("RANK"));
			jsonObj.put("FILENAME", map.get("FILENAME"));
			jsonObj.put("ORGFILENAME", map.get("ORGFILENAME"));
			jsonObj.put("FILESIZE", map.get("FILESIZE"));
			jsonObj.put("PAPERSNAME", map.get("PAPERSNAME"));
			jsonObj.put("EMPLOYEENAME", map.get("EMPLOYEENAME"));
			jsonObj.put("TEXTNUMBER", map.get("TEXTNUMBER"));
			
			jsonArr.put(jsonObj);			
		}
		return jsonArr.toString();
	}

	
	// 휴가
	@RequestMapping(value="/approver_va.action", method= {RequestMethod.POST})
	public String requireLoginCheck_approver_va (HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		String employeeno = String.valueOf(loginuser.getEmployeeno());
		
		String textnumber = request.getParameter("textnumber");
	//	System.out.println(textnumber);
		String employeename = request.getParameter("employeename");
	//	System.out.println(employeename);
		
		
		HashMap<String, String> paraMap = new HashMap<String,String>();
		paraMap.put("employeeno", employeeno);
		paraMap.put("textnumber", textnumber);
		paraMap.put("employeename", employeename);
		
	//	System.out.println(textnumber);
		
		List<HashMap<String,String>> detailList = service.detaiLeavelList(paraMap);
	//	System.out.println(detailList);
		String REASON = detailList.get(0).get("REASON");
	//	System.out.println(CONTENT);

		REASON = REASON.replaceAll("<br/>", "\r\n");
				
		request.setAttribute("textnumber", textnumber);
		request.setAttribute("REASON", REASON);
		request.setAttribute("detailList", detailList);		
		request.setAttribute("textnumber", textnumber);

		return "approver/leave.tiles1";
	} // end of requireLoginCheck_approver_va()-----------------------------------------------
	
	
	// 휴가 지출서 파일 다운로드
	@RequestMapping(value = "/leavedownload.action", method= {RequestMethod.GET})
	public void requireLoginCheck_leavedownload(HttpServletRequest request, HttpServletResponse response) {
		String textnumber = request.getParameter("textnumber"); 
	
		ExReportVO exreportvo = service.getleavedownload(textnumber);
		
		 
		String fileName = exreportvo.getFileName(); 
		String orgFilename = exreportvo.getOrgFilename(); 
		
		HttpSession session = request.getSession();
		
		String root = session.getServletContext().getRealPath("/"); 
		String path = root + "resources"+File.separator+"files";
		
		boolean flag = false;
		
		flag = fileManager.doFileDownload(fileName, orgFilename, path, response);
		
		
		if(!flag) {
			
			
			response.setContentType("text/html; charset=UTF-8"); 
			PrintWriter writer = null;
			
			try {
				writer = response.getWriter();
			} catch (IOException e) {
				
			}
			
			writer.println("<script type='text/javascript'>alert('파일 다운로드가 불가능합니다.!!')</script>");       
			
		}// end of if()----------------------------		
	} // end of requireLoginCheck_download()-----------------------------------------
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////
	
	
	////////// 수신함 결재 //////////////////////////////////////////////////////////////////////////////
	
	// 지출
	@RequestMapping(value="/incomplete_approver_ex.action", method= {RequestMethod.POST})
	public String requireLoginCheck_incomplete_approver_ex(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		String employeeno = String.valueOf(loginuser.getEmployeeno());
		String userName = loginuser.getName();
		
		
		String textnumber = request.getParameter("textnumber");
	//	System.out.println(textnumber);
		String employeename = request.getParameter("employeename");
	//	System.out.println(employeename);
		
		
		HashMap<String, String> paraMap = new HashMap<String,String>();
		paraMap.put("employeeno", employeeno);
		paraMap.put("textnumber", textnumber);
		paraMap.put("employeename", employeename);
		
	//	System.out.println(textnumber);
		
		List<HashMap<String,String>> detailList = service.detailList(paraMap);
	//	System.out.println(detailList);
		String CONTENT = detailList.get(0).get("CONTENT");
	//	System.out.println(CONTENT);
		String EXPENDITUREMODE = detailList.get(0).get("EXPENDITUREMODE");
	//	System.out.println(EXPENDITUREMODE);

		CONTENT = CONTENT.replaceAll("<br/>", "\r\n");
				
		request.setAttribute("userName", userName);
		request.setAttribute("employeeno", employeeno);
		request.setAttribute("textnumber", textnumber);
		request.setAttribute("CONTENT", CONTENT);
		request.setAttribute("EXPENDITUREMODE", EXPENDITUREMODE);
		request.setAttribute("detailList", detailList);
		
		return "incomplete/incomplete_exReport.tiles1";
	}
		
		
	//////////////////////////////////////////////////////////////////////////////////////////////////
		
	// 휴가
	@RequestMapping(value="/incomplete_approver_va.action", method= {RequestMethod.POST})
	public String incomplete_approver_va (HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		String employeeno = String.valueOf(loginuser.getEmployeeno());
		String userName = loginuser.getName();
		
		String textnumber = request.getParameter("textnumber");
	//	System.out.println(textnumber);
		String employeename = request.getParameter("employeename");
	//	System.out.println(employeename);
		
		
		HashMap<String, String> paraMap = new HashMap<String,String>();
		paraMap.put("employeeno", employeeno);
		paraMap.put("textnumber", textnumber);
		paraMap.put("employeename", employeename);
		
	//	System.out.println(textnumber);
		
		List<HashMap<String,String>> detailList = service.detaiLeavelList(paraMap);
	//	System.out.println(detailList);
		String REASON = detailList.get(0).get("REASON");
	//	System.out.println(CONTENT);

		REASON = REASON.replaceAll("<br/>", "\r\n");
				
		request.setAttribute("userName", userName);
		request.setAttribute("employeeno", employeeno);
		request.setAttribute("textnumber", textnumber);
		request.setAttribute("REASON", REASON);
		request.setAttribute("detailList", detailList);		
		request.setAttribute("textnumber", textnumber);

		return "incomplete/incomplete_leave.tiles1";
	} // end of requireLoginCheck_approver_va()-----------------------------------------------
	

	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/// 결재 후 업데이트 ///////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value ="/approverSuccess.action")
	public String requireLoginCheck_approverSuccess(HttpServletRequest request, HttpServletResponse response) {
		
		String userName = request.getParameter("userName");
		String textnumber = request.getParameter("textnumber");
		String employeeno = request.getParameter("employeeno");
		
		HashMap<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("userName", userName);
		paraMap.put("textnumber", textnumber);
		paraMap.put("employeeno", employeeno);
		
		int result = service.approverSuccess(paraMap); // 지출 결재 후 업데이트
		int leaveResult = service.approverLeaveSuccess(paraMap); // 휴가 신청서 결재 후 업데이트
		
		if(result == 1) {
			int n = service.updateApprover(paraMap); // 결재완료 테이블 update
			if(n == 1) {
				request.setAttribute("msg", "정상적으로 결재가 완료 되었습니다.");
			}
			else {
				request.setAttribute("msg", "결재가 실패 되었습니다.");
			}
		} // end of if()-----------------
		
		else if(leaveResult == 1) {
			int m = service.updateLeaveApprover(paraMap); // 결재완료 테이블 update
			if(m == 1) {
				request.setAttribute("msg", "정상적으로 결재가 완료 되었습니다.");
			}
			else {
				request.setAttribute("msg", "결재가 실패 되었습니다.");
			}
		} // end of else if()--------------------
		
		else {
			request.setAttribute("msg", "결재가 실패 되었습니다.");
		} // end of else()----------------------

		request.setAttribute("loc", request.getContextPath()+"/receive_payment_archive.action");
		return "msg";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 결재완료 상태를 나타내기 위한 ajax //////////////////////////////////////////////////////
	
	@ResponseBody
	@RequestMapping(value="/approverFindAjax.action")
	public String requireLoginCheck_approverFindAjax(HttpServletRequest request, HttpServletResponse response) {
		
		int listSize = Integer.parseInt(request.getParameter("listSize"));
		List<String> result = new ArrayList();
		String jnObj = "";
		for(int i=0; i<listSize; i++) {
			
			String textnumber = request.getParameter("ajaxTextnumber"+i+""); //결재완료 ajax 1번째
		//	System.out.println(textnumber);
			result.add(service.approverFindAjax(textnumber));
		//	System.out.println(result);
		}
	
		JSONArray jsonArr = new JSONArray();
		for(int j =0; j<result.size(); j++) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("ajaxTextNumber", result.get(j));
			jsonArr.put(jsonObj);
		}
		jnObj = jsonArr.toString();
	//	System.out.println(jnObj);
		return jnObj;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	
	
	
	// 결재완료 상태를 나타내기 위한 ajax2 번째 지출결의서 갯수 알아오기///////////////////////////////////
	@ResponseBody
	@RequestMapping(value="/approverFindAjaxTwo.action")
	public String requireLoginCheck_approverFindAjaxTwo(HttpServletRequest request, HttpServletResponse response) {
		
		int listSize = Integer.parseInt(request.getParameter("listSize"));
		List<String> result = new ArrayList<String>();
		String jnObj = "";
		for(int i=0; i<listSize; i++) {
			
			String textnumber = request.getParameter("ajaxTextNumberHidden"+i+"");
		//	System.out.println(textnumber);
			result.add(service.approverFindAjaxTwo(textnumber)); //결재완료 ajax 2번째
		//	System.out.println(result);
		}
	
		JSONArray jsonArr = new JSONArray();
		for(int j =0; j<result.size(); j++) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("ajaxExpendCount", result.get(j));
			jsonArr.put(jsonObj);
		}
		jnObj = jsonArr.toString();
	//	System.out.println(jnObj);
		return jnObj;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	
	// 결재완료 상태를 나타내기 위한 ajax3 번째 휴가 신청서 개수 알아오기///////////////////////////////////
		@ResponseBody
		@RequestMapping(value="/approverFindAjaxThree.action")
		public String requireLoginCheck_approverFindAjaxThree(HttpServletRequest request, HttpServletResponse response) {
			
			int listSize = Integer.parseInt(request.getParameter("listSize"));
			List<String> result = new ArrayList<String>();
			String jnObj = "";
			for(int i=0; i<listSize; i++) {
				
				String textnumber = request.getParameter("ajaxTextNumberHidden"+i+"");
			//	System.out.println(textnumber);
				result.add(service.approverFindAjaxThree(textnumber)); //결재완료 ajax 3번째 휴가 신청서 개수 알아오기.
			//	System.out.println(result);
			}
		
			JSONArray jsonArr = new JSONArray();
			for(int j =0; j<result.size(); j++) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("ajaxExpendCount", result.get(j));
				jsonArr.put(jsonObj);
			}
			jnObj = jsonArr.toString();
		//	System.out.println(jnObj);
			return jnObj;
		}
		
		///////////////////////////////////////////////////////////////////////////////////////
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	
}	// End-----------------------------------------------------------------------------









