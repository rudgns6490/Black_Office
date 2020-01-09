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
	.documentchoice_box {
		width: 97%;
		margin-bottom: 10px;
		background: #f7f7f7;
		border-bottom: solid 1px #4bc5c3;
	}
	
	.documentchoice {
	    display: inline-block;
	    padding: 7px 15px 9px;
	    border: 1px solid #d1d1d1;
	    background: #ebebeb;
	    font-size: 14px;
	    line-height: 18px;
	    color: #555;
	}
	
	.documentchoice_active {
		background: #4bc5c3;
		color: white;
		font-weight: bold;
	}
	
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
	.paging_area a {
	    display: inline-block;
	    margin: 0 2.5px;
	    width: 25px;
	    height: 25px;
	    box-sizing: border-box;
	    border: 1px solid #d2d2d2;
	    background: #fff;
	    line-height: 23px;
	    vertical-align: top;
	}
	.paging_area b {
	    display: inline-block;
	    margin: 0 2.5px;
	    width: 25px;
	    height: 25px;
	    box-sizing: border-box;
	    background: #4bc5c3;
	    vertical-align: top;
	    line-height: 25px;
	    color: #fff;
	}
/* 	페이징 끝	 */
/* 	글 목록 끝	 */

</style>

<script type="text/javascript">

	$(document).ready(function() {
		document.title='부서문서 보관함';		
	});

</script>


<div class="archiveContainer">
	<%--	맨 위 부분	 --%>
	<div class="archiveheader">
		<img class="headercon" src="resources/images/archive/titleicon.png">
		<h5 class="headercon" style="display: inline-block; margin-top: 2px;">부서문서</h5>
	</div>
	<%--	맨 위 부분 끝	--%>
	
	<div class="col-md-12 archivebody" style="margin-left: 25px;">
	
	<%--	문서종류 선택	 --%>
	<div class="documentchoice_box">
		<a href="<%=ctxPath %>/deptdocument_archive_normal.action" class="documentchoice documentchoice_active">일반 결재 문서</a>
		<a href="<%=ctxPath %>/deptdocument_archive_expenses.action" class="documentchoice">지출 결재 문서</a>
		<a href="<%=ctxPath %>/deptdocument_archive_leave.action" class="documentchoice">휴가/휴직 결재 문서</a>
	</div>
	<%--	문서종류 선택 끝	 --%>
	
	
	<%--	검색 부분	 --%>
		<div class="reportsearch">
						
			<form action="">
				<table style="width: 100%;">
					<tr>
						<th>결재상태</th>
						<td>
							<select class="search_select">
								<option value="">전체보기</option>
								<option value="">결재진행중</option>
								<option value="">반려</option>
								<option value="">보류</option>
								<option value="">최종결재대기중</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<th>작성일자</th>
						<td>
							<span class="search_Condition">당일</span>
							<span class="search_Condition">1주일</span>
							<span class="search_Condition">1개월</span>
							<span class="search_Condition">3개월</span>
							<span class="search_Condition">6개월</span>
							<span class="search_Condition">1년</span>
							<input type="date" /> ~ <input type="date" />
						</td>
					</tr>
					<tr>
						<th>문서 검색</th>
						<td>
							<span class="search_Condition">전체보기</span>
							<select class="search_select">
								<option value="writer">기안자</option>
								<option value="title">제목</option>
								<option value="document_no">문서번호</option>
							</select>
							<input type="text" name="report_search" class="report_search"/>
							<button type="button" class="report_search_btn">검색</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<%--	검색 부분 끝	 --%>
	
	
		
	<%--	글 목록	 --%>
		<div class="reportList">
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
						<td>테스트1</td>
						<td>문서번호1</td>
						<td>문서분류1</td>
						<td style="text-align: left; color: #2276BB; padding-left: 10px;">제목1</td>
						<td>상태1</td>
						<td>부서1</td>
						<td>기안자1</td>
						<td>작성일1</td>
					</tr>
				</tbody>
				
			</table>
		<%--	페이징처리 시작	 --%>	
<!-- 		<div class="paging_area"> -->
<!-- 			<a>이전페이지</a> -->
<!-- 			<a>1</a> -->
<!--			<a>2</a> -->
<!--			<a>3</a> -->
<!-- 		</div> -->
		<%--	페이징처리 끝	 --%>
		</div>
		<%--	글 목록 끝	 --%>
	
	</div>
	
	
	
	
	
</div>



