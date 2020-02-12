<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String ctxPath = request.getContextPath();
%>

<style type="text/css">
	
	.archiveContainer {background: #f7f7f7;	width: 100%;}
	
/* 	맨 위 부분 */
	.archiveheader {margin: 10px 0 5px 25px; padding-left: 10px;}
	.headercon {vertical-align: top;}
	h5 {font-family: 'Malgun Gothic', '맑은 고딕', 'Dotum', '돋움', sans-serif; font-weight: bold;}	
/* 	맨 위 부분 끝	 */

/* 	검색 부분	 */
	.reportsearch {background: white; border: 1px solid #e6e6e6; width: 97%; margin-bottom: 20px;}
	.reportsearch tr {border-bottom: 1px solid #e6e6e6;} 
	.reportsearch th {
		font-family: 'Malgun Gothic', '맑은 고딕', 'Dotum', '돋움', sans-serif;
		background: #007bff;
	    min-width: 110px;
	    width: 160px;
	    padding: 10px;
	    font-size: 13px;
	    line-height: 17px;
	    color: #fff;
	    vertical-align: middle;
	}
	.reportsearch td {padding: 10px 10px 5px 10px;}
	.search_Condition {
		font-family: 'Malgun Gothic', '맑은 고딕', 'Dotum', '돋움', sans-serif;
		display: inline-block;
	    text-align: center;
	    height: 30px;
	    margin: 0 4px 5px 4px;
	    padding: 2px 18px;
	    border: 1px solid #6c757d;
	    border-radius: 3px;
	    background: #6c757d;
	    font-size: 13px;
	    line-height: 23px;
	    color: white;
	    vertical-align: middle;
	    cursor: pointer;
	}
	.search_select {height: 30px; font-size: 14px; position: relative; top: -1px;}
	.report_search {width: 200px; height: 30px; margin-right: 5px;}
	.report_search_btn {
		width: 80px;
		height: 30px;
		border: solid 1px #007bda;
	    border-radius: 3px;
	    box-sizing: border-box;
	    background: #0083e7;
	    color: #fff;
	}	
/* 	검색 부분 끝	 */
	
/* 	글 목록	 */
	.reportList {background: white;	border: 1px solid #e6e6e6; width: 97%;}
	.reportList_title {
		border-bottom: 1px solid #e6e6e6;
	    background: #1c5691;
	    font-weight: bold;
	    font-size: 15px;
	    line-height: 15px;
	    color: #fff;
	    text-align: center;
	}
	.reportList_title th {padding: 15px 0;}
	.reportList_contents td {
		padding: 7px 0;
	    border-bottom: 1px solid #e6e6e6;
	    background: #fff;
	    font-size: 14px;
	    line-height: 17px;
	    color: #777777;
	    text-align: center;
	}
/* 	페이징	 */
	.paging_area {
		text-align: center;
		margin-top: 50px;
	}
	
	.paging_area li {
		display: inline-block;
	}
/* 	페이징	 */
/* 	글 목록 끝	 */
	
	
	.excel_btn {
      margin-left: 10px;
      border: 10px;
      background: #4bc5c3;
      color: white;
      border: 1px solid #e6e6e6;
      border-radius: 3px;
      height: 31px;
   }
	
	
/* --------- 아래부터는 상제정보를 하기 위한 것 -------------------------------------------   */
	.title:hover { cursor: pointer; }	
	
</style>

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">

	$(document).ready(function() {
		document.title='결재문서 수신함';	
		
		//캘린더 시작~~~~~~~~~~~~
		$('#fromDate').datepicker({
			showOn: "both",                     						// 달력을 표시할 타이밍 (both: focus or button)
			buttonImage: "<%= ctxPath %>/resources/images/cal.jpg", 	// 버튼 이미지
			buttonImageOnly : true,             						// 버튼 이미지만 표시할지 여부
			buttonText: "날짜선택",             							// 버튼의 대체 텍스트
			dateFormat: "yy-mm-dd",             						// 날짜의 형식
			changeMonth: true,                  						// 월을 이동하기 위한 선택상자 표시여부
			onClose: function( selectedDate ) {    
				$("#toDate").datepicker( "option", "minDate", selectedDate );
			}                
		});
		
		//종료일
		$('#toDate').datepicker({
			showOn: "both", 
			buttonImage: "<%= ctxPath %>/resources/images/cal.jpg", 
			buttonImageOnly : true,
			buttonText: "날짜선택",
			dateFormat: "yy-mm-dd",
			changeMonth: true,
			onClose: function( selectedDate ) {
				$("#fromDate").datepicker( "option", "maxDate", selectedDate );
			}                
		});  // 캘린더 종료--------------------------------------
		
		
		// 검색창 엔터키 입력
		$("input[name=report_search_word]").keydown(function(event) {
			if(event.keyCode == 13) {
				goDocumentSearch();
			}
		});
		
		
		// 검색 조건, 검색어 유지
      if( ${paraMap != null} ) {
         $("#search_select").val("${paraMap.search_select}");
         $("#report_search_word").val("${paraMap.report_search_word}");
         $("#search_Time").val("${paraMap.search_Time}");
         $("#fromDate").val("${paraMap.fromDate}");
         $("#toDate").val("${paraMap.toDate}");
      }
		
		/* ---------  아래부터는 상제정보를 하기 위한 것  -----------------------------------------------*/
		// 제목을 클릭하였을 경우
		$(".title").click(function(){
			var papersName = $(this).parent().find(".papersName").val();
			if(papersName == "지출 결의서") {
				var textnumber = ($(this).parent().find(".textnumber").val());
				var employeename = ($(this).parent().find(".employeename").val());
				$(".textnumber").val(textnumber);
				$(".employeename").val(employeename);
				
				var frm = document.incompleteList;
				frm.action="<%= request.getContextPath()%>/incomplete_approver_ex.action";
				frm.method="POST";
				frm.submit();
			} 
			else {
				var textnumber = ($(this).parent().find(".textnumber").val());
				var employeename = ($(this).parent().find(".employeename").val());
				$(".textnumber").val(textnumber);
				$(".employeename").val(employeename);
				
				var frm = document.incompleteList;
				frm.method="POST";
				frm.action="<%= request.getContextPath()%>/incomplete_approver_va.action";
				frm.submit();
			}
		}); // end of $(".title").click(function(){})--------------------
	
						
	});	// end of $(document).ready()-----------------------------------------
	
		
	function goDocumentSearch() {
		
		var frm = document.documentsearchFrm;
		frm.method = "GET";
		frm.action = "receive_payment_archive.action";
		frm.submit();
		
	}	// end of goReportSearch()--------------------------------------------
	
	// 날짜(기간) 검색 버튼
	function goSearchTime(val) {
			
		var search_Time = val;
		
		$("#search_Time").val(search_Time);
		
		goDocumentSearch();
	}
	
	
	// 엑셀파일로 저장
   function goExcel() {
      
      var frm = document.documentsearchFrm;
      frm.method = "POST";
      frm.action = "receive_payment_downloadExcelFile.action";
      frm.submit();
   }

</script>


<div class="archiveContainer">
	<%--	맨 위 부분	 --%>
	<div class="archiveheader">
		<img class="headercon" src="resources/images/archive/titleicon.png">
		<h5 class="headercon" style="display: inline-block; margin-top: 2px;">결재해야 할 문서 목록</h5>
	</div>
	<%--	맨 위 부분 끝	--%>

	<div class="col-md-12 archivebody" style="margin-left: 25px;">
		<%--	검색 부분	 --%>
		<div class="reportsearch">
			<form name="documentsearchFrm">
				<table style="width: 100%;">
					<tr>
						<th>작성일자</th>
						<td>
							<button type="button" class="search_Condition" id="today" value="0" onclick="goSearchTime(this.value);">당일</button>
							<button type="button" class="search_Condition" id="weekend" value="6" onclick="goSearchTime(this.value);">1주일</button>
							<button type="button" class="search_Condition" id="onemonth" value="-1" onclick="goSearchTime(this.value);">1개월</button>
							<button type="button" class="search_Condition" id="threemonth" value="-3" onclick="goSearchTime(this.value);">3개월</button>
							<button type="button" class="search_Condition" id="sixmonth" value="-6" onclick="goSearchTime(this.value);">6개월</button>
							<button type="button" class="search_Condition" id="oneyear" value="-12" onclick="goSearchTime(this.value);">1년</button>
							
							<input type="hidden" name="search_Time" id="search_Time" value=""/>
							
							
							<div id="Datepicker" style="margin-top: 5px; margin-left: 10px; display: inline-block; ">
		                        <label for="fromDate">작성 기간 : </label>
		                        <input type="text" name="fromDate" id="fromDate" value="${fromDate}">
		                         ~
		                        <input type="text" name="toDate" id="toDate" value="${toDate}">
		                        <label for="toDate">까지</label>
	               		  	</div>
						</td>
					</tr>
					<tr>
						<th>문서 검색</th>
						<td>
							<select name="search_select" id="search_select" class="search_select">
								<option value="" hidden="" disabled selected="selected">선택</option>
								<option value="title">제목</option>
								<option value="employeename">기안자</option>
							</select>
							
							<input type="text" name="report_search_word" id="report_search_word" class="report_search_word"/>
							<button type="button" class="report_search_btn" onclick="goDocumentSearch();">검색</button>
							<button type="button" class="excel_btn" onclick="goExcel();">엑셀파일로 저장</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<%--	검색 부분 끝	 --%>
		
		<%--	글 목록	 --%>
		<div class="reportList">
		<form name="incompleteList">
			<table style="width: 100%;">
				<thead>
					<tr class="reportList_title">
						<th style="width: 5%;">No.</th>
						<th style="width: 7%;">문서번호</th>
						<th style="width: 10%;">문서분류</th>
						<th>제목</th>
						<th style="width: 10%;">상태</th>
						<th style="width: 10%;">부서</th>
						<th style="width: 10%;">기안자</th>
						<th style="width: 20%;">작성일</th>
					</tr>
				</thead>
					
				<tbody class="reportList_contents">
					<tr>
						<c:if test="${not empty requestScope.receiveDocumentList}">
							<c:forEach var="receiveList" items="${requestScope.receiveDocumentList}" varStatus="status">
								<tr>
									<td>${receiveList.rno}</td>
									<td>
										${receiveList.textnumber}
										<input type="hidden" class="textnumber" name="textnumber" value="${receiveList.textnumber}" />
									</td>
									<td>
										${receiveList.papersname}
										<input type="hidden" class="papersName" value="${receiveList.papersname}" />
									</td>
									<td class="title" style="text-align: left; color: #2276BB;">
										${receiveList.title}
										<c:if test="${not empty receiveList.filename}">
											<img style="width: 15px; height: 16px; margin-left: 1px;" src="<%=ctxPath %>/resources/images/파일첨부icon.png">
										</c:if>
									</td>
									<td>
										<c:if test="${receiveList.status == 0}">
											<span>미결재 상태</span>
										</c:if>
										<c:if test="${receiveList.status == 1}">
											<span style="color: red;">결재완료</span>
										</c:if> 
									</td>
									<td>${receiveList.departmentname}</td>
			 						<td>
			 							${receiveList.employeename}
			 							<input type="hidden" class="employeename" name="employeename" value="${receiveList.employeename}"/>
			 						</td>
			 						<td>${receiveList.writeday}</td>
								</tr>
							</c:forEach>
						</c:if>

						<c:if test="${empty requestScope.receiveDocumentList}">
							<tr>
								<td colspan="8"><span>미결재 상태의 문서가 없습니다.</span></td>
							</tr>
						</c:if>
					</tr>
				</tbody>
				
			</table>
			</form>
			<%--	페이징처리 시작	 --%>	
			<div class="paging_area">
				${pageBar}
			</div>
			<%--	페이징처리 끝	 --%>
		</div>
		<%--	글 목록 끝	 --%>
		
	</div>
	
	

</div>
























