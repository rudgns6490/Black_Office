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



/* 	문서종류 선택	 */
	.documentchoice_box {width: 97%; margin-bottom: 10px; background: #f7f7f7; border-bottom: solid 1px #4bc5c3;}
	.documentchoice {
	    display: inline-block;
	    padding: 7px 15px 9px;
	    border: 1px solid #d1d1d1;
	    background: #ebebeb;
	    font-size: 12pt;
	    line-height: 18px;
	    color: #555;
	}
	.documentchoice_active {background: #4bc5c3; color: white; font-weight: bold;}
	.documentchoice_active:hover {color: white;}
	a:hover {color:inherit; text-decoration: none;}
	a:link {text-decoration: none;}
	a:visited {text-decoration: none;}
		
/* 	문서종류 선택 끝	 */



/* 	검색 부분	 */
	.reportsearch {background: white; border: 1px solid #e6e6e6; width: 97%; margin-bottom: 20px;}
	.reportsearch tr {border-bottom: 1px solid #e6e6e6;} 
	.reportsearch th {
		font-family: 'Malgun Gothic', '맑은 고딕', 'Dotum', '돋움', sans-serif;
		background: #007bff;
	    min-width: 110px;
	    width: 160px;
	    padding: 10px;
	    font-size: 11pt;
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
	    font-size: 12pt;
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
/* 	페이징 끝	 */
/* 	글 목록 끝	 */



/* --------- 아래부터는 상제정보를 하기 위한 것 -------------------------------------------   */
	.title:hover { cursor: pointer; }


	/* 엑셀 css  */
	.excel_btn {
      margin-left: 10px;
      border: 10px;
      background: #4bc5c3;
      color: white;
      border: 1px solid #e6e6e6;
      border-radius: 3px;
      height: 31px;
   }



</style>

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">


	$(document).ready(function() {
		document.title='결재문서함';
		
		$(".successFind"+i+"").hide(); // 결재완료 숨기기
		
		refresh(); // 새로고침
		
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
				
				var frm = document.detailList;
				frm.action="<%= request.getContextPath()%>/approver_ex.action";
				frm.method="POST";
				frm.submit();
			} 
			else {
				var textnumber = ($(this).parent().find(".textnumber").val());
				var employeename = ($(this).parent().find(".employeename").val());
				$(".textnumber").val(textnumber);
				$(".employeename").val(employeename);
				
				var frm = document.detailList;
				frm.method="POST";
				frm.action="<%= request.getContextPath()%>/approver_va.action";
				frm.submit();
			}
		}); // end of $(".title").click(function(){})--------------------
		
		
		// 결재완료 를 나타내기 위한 ajax
		var arr1 = [];
		var str_arr1 = "";

		var arr2 = [];
		var str_arr2 = "";
		
		var arr3 = [];
		var str_arr3 = "";
		
		var form_data = $("form[name=detailList]").serialize();
		$.ajax({
			url:"<%= request.getContextPath()%>/approverFindAjax.action",
			data:form_data,
			type:"GET",
			dataType:"JSON",
			success:function(json){
				var html = "";
				arr1 = [];
				
				$.each(json,function(index,item){
					var textNumber = item.ajaxTextNumber;
					$(".sucessHidden"+index+"").val(textNumber);
				
					arr1.push(textNumber);
				});
				
				str_arr1 = arr1.join(",");
				sessionStorage.removeItem("ajaxTextNumber");
				sessionStorage.setItem("ajaxTextNumber",str_arr1);
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
			
		});// end of ajax()------------------------------------------------
		
		
		// 결재완료 를 나타내기 위한 ajax2 번째 지출, 휴가 문서에 따른 개수 알아오기.
		var listSize = $(".listSize").val();
		for(var i=0; i<listSize; i++) {
			
			var papersName = $(".papersName"+i+"").val();
			if(papersName == "지출 결의서") {
				var ajaxTextnumber = $(".ajaxTextnumber"+i+"").val();
				$(".ajaxTextNumberHidden"+i+"").val(ajaxTextnumber);
				
				var form_data = $("form[name=detailList]").serialize();
				$.ajax({
					url:"<%= request.getContextPath()%>/approverFindAjaxTwo.action",
					data:form_data,
					type:"GET",
					dataType:"JSON",
					success:function(json){
						var html = "";
						arr2 = [];
						
						$.each(json,function(index,item){
							var expendCount = item.ajaxExpendCount;
							$(".ajaxExpendCount"+index+"").val(expendCount);
						
							arr2.push(expendCount);
						});
						
						str_arr2 = arr2.join(",");
						sessionStorage.removeItem("ajaxExpendCount");
						sessionStorage.setItem("ajaxExpendCount",str_arr2);
					},
					error: function(request, status, error){
						alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
					}
				}); // end of ajax()----------------
				
				
			} // end of if()-------------------
			
			else {
				var ajaxTextnumber = $(".ajaxTextnumber"+i+"").val(); 
				$(".ajaxTextNumberHidden"+i+"").val(ajaxTextnumber);
				
				var form_data = $("form[name=detailList]").serialize();
				$.ajax({
					url:"<%= request.getContextPath()%>/approverFindAjaxThree.action",
					data:form_data,
					type:"GET",
					dataType:"JSON",
					success:function(json){
						var html = "";
						arr3 = [];
						
						$.each(json,function(index,item){
							var expendCount = item.ajaxExpendCount;
							$(".ajaxLeaveCount"+index+"").val(expendCount);
						
							arr3.push(expendCount);
						});

						str_arr3 = arr3.join(",");
						sessionStorage.removeItem("ajaxLeaveCount");
						sessionStorage.setItem("ajaxLeaveCount",str_arr3); 
					},
					error: function(request, status, error){
						alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
					}
				}); // end of ajax()-------------------------
			} // end of else ()------------------------
			
		}
		
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
		str_arr1 = sessionStorage.getItem("ajaxTextNumber");
	    arr1 = str_arr1.split(",");
		
	    var listSize = $(".listSize").val();
		for(var i=0; i<listSize; i++) {
		    var papersName = $(".papersName"+i+"").val();
			if(papersName == "지출 결의서") {
			    str_arr2 = sessionStorage.getItem("ajaxExpendCount"); 
			    arr2 = str_arr2.split(",");
			}
			else {
			    str_arr3 = sessionStorage.getItem("ajaxLeaveCount");
			    arr3 = str_arr3.split(",");
			}
		}
	   
	   for(var i=0; i<arr1.length; i++) {
	    	var a = arr1[i];
	    	var b = arr2[i];
	    	var c = arr3[i];
	    	
	    	if(a > 0) {
		    	if(a==b  || a==c ) {
			    	$(".successFail"+i+"").remove();
			    	$(".successFind"+i+"").show();
		    	}
		    	else {
		    		$(".successFind"+i+"").hide(); 
		    	}
	    	}
	    	else {
	    		$(".successFind"+i+"").hide();
	    	}
	    }
	   
	
	   

	});	// end of $(document).ready()-----------------------------------------
	
	
	function goDocumentSearch() {
		
		var frm = document.documentsearchFrm;
		frm.method = "GET";
		frm.action = "incomplete_payment_archive.action";
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
	      frm.action = "incomplete_payment_downloadExcelFile.action";
	      frm.submit();
	   }

	// 새로고침
   function refresh() {
      
      var refresh = sessionStorage.getItem("refresh");
            
      if(refresh == null) {
         sessionStorage.setItem("refresh", "refresh");
         location.href = "javascript:history.go(0);";
      }
      if(refresh != null) {
         sessionStorage.removeItem("refresh");
      }
   }
	
</script>


<div class="archiveContainer">
	<%--	맨 위 부분	 --%>
	<div class="archiveheader">
		<img class="headercon" src="resources/images/archive/titleicon.png">
		<h5 class="headercon" style="display: inline-block; margin-top: 2px;">미결재/결재완료 문서 목록</h5>
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
						<td><%--	검색조건 mapper에서 수정하기 지출,휴가문서 문서번호 컬럼명이 다름	 --%>
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
		<form name="detailList">
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
						<c:if test="${not empty requestScope.incompletePaymentDocumentList}">
							<c:forEach var="documentList" items="${requestScope.incompletePaymentDocumentList}" varStatus="status">
								<tr>
									<td>${documentList.rno}</td>
									<td>
										${documentList.textnumber}
										<input type="hidden" class="textnumber" name="textnumber" value="${documentList.textnumber}" />
										<input type="hidden" class="ajaxTextnumber${status.index}" name="ajaxTextnumber${status.index}" value="${documentList.textnumber}" />
									</td> 
									<td>
										${documentList.papersname}
										<input type="hidden" class="papersName papersName${status.index}" value="${documentList.papersname}" />
									</td>
									<td class="title" style="text-align: left; color: #2276BB;">
										${documentList.title}
										<c:if test="${not empty documentList.filename}">
											<img style="width: 15px; height: 16px; margin-left: 1px;" src="<%=ctxPath %>/resources/images/파일첨부icon.png">
										</c:if>
									</td>							
									
									<td class="approverStatus" align="center"> 
										<input type="text" class="successFail${status.index}" value="미결재" style="border:none; width: 50px;" readonly />
										<input type="text" class="successFind${status.index}" value="결재 완료" style="color: red; border:none; width: 70px;" readonly />
									</td>
									
									<td>${documentList.departmentname}</td>
			 						<td>
			 							${documentList.employeename}
			 							<input type="hidden" class="employeename" name="employeename" value="${documentList.employeename}"/>
			 						</td>
			 						<td>
			 							${documentList.writeday}
			 							<input type="hidden" class="sucessHidden${status.index}" value="" />
			 							<input type="hidden" class="ajaxTextNumberHidden${status.index}" name="ajaxTextNumberHidden${status.index}" value="" />
			 							<input type="hidden" class="ajaxExpendCount${status.index}" name="ajaxExpendCount${status.index}" value="" />
			 							<input type="hidden" class="ajaxLeaveCount${status.index}" name="ajaxLeaveCount${status.index}" value="" />
			 						</td>
								</tr>
								
							</c:forEach>
						</c:if>

						<c:if test="${empty requestScope.incompletePaymentDocumentList}">
							<tr>
								<td colspan="8"><span>미결재 상태의 문서가 없습니다.</span></td>
							</tr>
						</c:if>
					</tr>
				</tbody>
			
			</table>
			<!-- =========================================================================================  -->
				<input type="hidden" class="listSize" name="listSize" value="${requestScope.listSize }"/> <!-- 총 게시물 수 알아 온것  -->
			<!-- =========================================================================================  -->
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



