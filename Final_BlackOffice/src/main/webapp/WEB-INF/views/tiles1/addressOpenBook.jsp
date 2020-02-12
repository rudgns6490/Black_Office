<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String ctxPath = request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/resources/css/addressOpenBook.css">

<script type="text/javascript">

	$(document).ready(function(){
		
		var searchType = sessionStorage.getItem("searchType_oab");
		var searchWord = sessionStorage.getItem("searchWord_oab");
		
		if(searchType != null && searchWord != null) {
			$("select[name=searchType]").val(searchType);
			$("#searchWord").val(searchWord);
		}
		
		$("#searchWord").keydown(function(event) {
	        if(event.keycode == 13) {
	        	// 엔터를 했을 경우
	        	sessionStorage.removeItem("searchType_oab");
				sessionStorage.removeItem("searchWord_oab");
		    	
		    	sessionStorage.setItem("searchType_oab", $("select[name=searchType]").val().trim());
		    	sessionStorage.setItem("searchWord_oab", $("#searchWord").val().trim());
	        	
		    	var frm = document.searchFrm;
			      frm.method = "GET";
			      frm.action = "<%= request.getContextPath()%>/addressOpenBookList.action";
			      frm.submit();
	        }
	    });
		
		// ▼ 검색어 유지
	    $("#goSearch").on("click", function() {
	    	
	    	sessionStorage.removeItem("searchType_oab");
			sessionStorage.removeItem("searchWord_oab");
	    	
	    	sessionStorage.setItem("searchType_oab", $("select[name=searchType]").val().trim());
	    	sessionStorage.setItem("searchWord_oab", $("#searchWord").val().trim());
	    	
	    	var frm = document.searchFrm;
		      frm.method = "GET";
		      frm.action = "<%= request.getContextPath()%>/addressOpenBookList.action";
		      frm.submit();
	    });
	      
	});
</script>

<div id="content-wrapper" style="padding: 0px;">
	<div class="container-fluid text-center">

		<!-- 메인 본문 내용 소스 작업은 여기서 수정 2020/01/03 kkh -->
		<div class="row content"
			style="padding: 20px; background-color: #ccccc;">
			<div class="col-sm-12 displayline textline"
				style="height: 70px; background-color: #ebf0f3; padding: 30px;">
				<a href=""><img
					style="width: 30px; height: 30px; position: relative; top: -5px; left:"
					src="<%=ctxPath%>/resources/images/addressbook.PNG"></a>
				<div class="displayline">
					<h4>
						<strong>공용 주소록</strong>
					</h4>
				</div>
			</div>

<!-- 			<div class="col-sm-12 displayline"
				style="margin: 0px; background-color: #ebf0f3; padding: 15px 0 15px 30px;">
				<button class="addressbutton">이동</button>
				<button class="addressbutton">복사</button>
			</div> -->

			<div class="col-sm-12 displayline textline"
				style="height: 65px; border-top: solid 1px #cccccc;; padding: 15px 0 15px 30px;">

			  <form name="searchFrm" style="">
      			<select name="searchType" id="searchType" style="height: 26px;">
         		  <option value="name">이름</option>
         		  <option value="departmentname">부서이름</option>
         		  <option value="positionname">직위</option>
      			</select>
      			<input type="text" name="searchWord" id="searchWord" size="40" autocomplete="off" /> 
      			<button style="display: inline-block;" type="button" id="goSearch">검색</button>
   			  </form>
				&nbsp;&nbsp;
			</div>
			
			<div class="col-sm-12" style="padding: 15px 0px; " >
			
			  <table class="table">
			  	<tr>
			  	  <th style="width: 15%" align="center">이름</th>
			  	  <th style="width: 25%" align="center">이메일</th>
			  	  <th style="width: 20%" align="center">전화번호</th>
			  	  <th style="width: 15%" align="center">직책</th>
			  	  <th style="width: 15%" align="center">부서</th>
			  	</tr>
			  	
			  	<c:forEach var="abvo" items='${abvoList}' varStatus="status">
			  	  <tr>
			  	    <td style="width: 15%" align="center">${abvo.name}</td>
			  	    <td style="width: 25%" align="center">${abvo.email}</td>
			  	    <td style="width: 20%" align="center">${abvo.phone}</td>
			  	    <td style="width: 15%" align="center">${abvo.positionname}</td>
			  	    <td style="width: 15%" align="center">${abvo.departmentname}</td>
			  	  </tr>
			  	</c:forEach>
			  </table>
			  
			  <form name="goViewFrm">
			    <input type="hidden" name="addrno" > 
			    <input type="hidden" name="gobackURL" value="${gobackURL}" >
			  </form>
			  
			  <!-- === #126. 페이지바 만들기 === -->
			  <div align="center">
			    ${pageBar}
			  </div>
			</div>
		  </div>
		</div>
	</div>
	<!-- /.container-fluid -->
</div>
<!-- /.content-wrapper -->
