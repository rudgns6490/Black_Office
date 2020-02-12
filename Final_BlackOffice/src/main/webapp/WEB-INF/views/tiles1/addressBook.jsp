<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String ctxPath = request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/resources/css/addressBook.css">

<script type="text/javascript">

	$(document).ready(function(){
		
/* 		var searchType = sessionStorage.getItem("searchType_ab");
		var searchWord = sessionStorage.getItem("searchWord_ab");
		
 		if(searchType != null && searchWord != null) {
			$("select[name=searchType]").val(searchType);
			$("#searchWord").val(searchWord);
		}
		 */
		
	 	if( ${paraMap != null}){
			$("#searchType").val("${paraMap.searchType}");
			$("#searchWord").val("${paraMap.searchWord}");
		}
		 
		$("#searchWord").keydown(function(event) {
	        if(event.keycode == 13) {
	        	// 엔터를 했을 경우
/* 	        	sessionStorage.removeItem("searchType_ab");
				sessionStorage.removeItem("searchWord_ab");
		    	
		    	sessionStorage.setItem("searchType_ab", $("select[name=searchType]").val().trim());
		    	sessionStorage.setItem("searchWord_ab", $("#searchWord").val().trim()); */
	        	
		    	var frm = document.searchFrm;
			      frm.method = "GET";
			      frm.action = "<%= request.getContextPath()%>/addressBook.action";
			      frm.submit();
	        }
	    });
	           
		
	    $("#register").on('click', function(){
        	
	    	var frm = document.abRegisterFrm;
	    	frm.method = "POST";
	    	frm.action = "<%= request.getContextPath()%>/registerPersonal.action"
	    	frm.submit();
        });
	    
	 	// ▼ 검색어 유지
	    $("#goSearch").on("click", function() {
	    	
	    	/* sessionStorage.removeItem("searchType_ab");
			sessionStorage.removeItem("searchWord_ab");
	    	
	    	sessionStorage.setItem("searchType_ab", $("select[name=searchType]").val().trim());
	    	sessionStorage.setItem("searchWord_ab", $("#searchWord").val().trim()); */
	    	
	    	var frm = document.searchFrm;
		      frm.method = "GET";
		      frm.action = "<%= request.getContextPath()%>/addressBook.action";
		      frm.submit();
	    });
		 // ▲ 검색어 유지
	      
		$("#exceldownload").click(function(){
			var frm = document.searchFrm;
		    frm.method = "GET";
		    frm.action = "<%= request.getContextPath()%>/downloadAddressBook.action";
		    frm.submit();
		});
		 
		// 선택 된 행의 데이터를 삭제한다.
		$("#deleteList").click(function(){ 
			
			var name = "";
			var checkbox = $("input[name=user_CheckBox]:checked");
			
			checkbox.each(function(i){
				var tr = checkbox.parent().parent().eq(i);
				var td = tr.children();
				
				name = name + td.eq(1).text()+",";
			});
		
			name = name.substring(0,name.lastIndexOf( ",")); //맨끝 콤마 지우기
			
			location.href="<%= request.getContextPath()%>/deleteaddressbookList.action?name="+name;
			
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
						<strong>업체 주소록</strong>
					</h4>
				</div>
			</div>

			<div class="col-sm-12 displayline"
				style="margin: 0px; background-color: #ebf0f3; padding: 15px 0 15px 30px;">
				<button class="addressbutton" id="exceldownload">변환하기</button>
				<button class="addressbutton" id="deleteList">삭제</button>
			</div>

			<div class="col-sm-12 displayline textline" style="height: 65px; padding: 15px 0 15px 30px;">
			  <form style="display: inline-block;" name="searchFrm">
      			<select name="searchType" id="searchType" style="height: 26px;">
         		  <option value="name">이름</option>
         		  <option value="departmentname">부서이름</option>
         		  <option value="positionname">직위</option>
      			</select>
      			<input type="text" name="searchWord" id="searchWord" size="40" autocomplete="off" /> 
      			<button type="button" id="goSearch" >검색</button>
   			  </form>
				<button id="add" style="display: inline-block;" type="button" class="btn1 btn2 btn-primary"
					data-toggle="modal" data-target="#myModal" data-backdrop="static">추가</button>
					
			</div>

			<div class="col-sm-12 displayline textline bordertopline" style="height: 65px; padding: 15px 0 10px 20px;">
				<div class="displayline">페이지당 줄수</div>
				<select>
					<option value="10">10</option>
					<option value="20">20</option>
				</select>

			</div>
			
			<div class="col-sm-12 bordertopline" style="padding: 15px 0px; " >
			  
			  <table>
			    <tr>
			      <th style="width: 7%"><input type="checkbox" id="allSelect"/></th>
			      <th style="width: 10%" align="center">이름</th>
			      <th style="width: 15%" align="center">이메일</th>
			      <th style="width: 15%" align="center">전화번호</th>
			      <th style="width: 7%" align="center">회사</th>
			      <th style="width: 7%" align="center">그룹</th>
			    </tr>
			    
			    <c:forEach var="abvo" items='${abvoList}' varStatus="status">
			  	  <tr>
			  	    <td style="width: 7%"><input type="checkbox" id="status" name="user_CheckBox" /></td>
			  	    <td style="width: 10%" align="center" class="name">${abvo.name}</td>
			  	    <td style="width: 15%" align="center">${abvo.email}</td>
			  	    <td style="width: 15%" align="center">${abvo.phone}</td>
			  	    <td style="width: 7%" align="center">${abvo.companyname}</td>
			  	    <td style="width: 7%" align="center">${abvo.groupname}</td>
			  	  </tr>
			  	</c:forEach>
			    
			  </table>
			</div>

			<form name="goViewFrm">
			  <input type="hidden" name="addrno" > 
			  <input type="hidden" name="gobackURL" value="${gobackURL}" >
			</form>
			  
			<!-- === #126. 페이지바 만들기 === -->
			<div class="col-sm-12" align="center">
			  ${pageBar}
			</div>
			
		</div>
	</div>
	<!-- /.container-fluid -->
</div>
<!-- /.content-wrapper -->



<!-- 추가 버튼을 누를시의 Modal 창 -->
					
<!-- The Modal -->
  <div class="modal fade" id="myModal">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
          <h3 class="modal-title">개인 주소 추가</h3>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
			<div class="row content">
		        <div class="col-sm-12" style="padding: 5%;">
		          <form name="abRegisterFrm">
				      <table class="table table-bordered">

				      	<tr>
				      	  <td style="background-color: #e0ebeb;">
				      	  	<label for="name">이름</label>
				      	  </td>
				      	  <td>
				      	    <input type="text" name="name" id="name" value="" maxlength="20" required autofocus autocomplete="off" style="width: 200px;"/>
				      	  </td>
				      	</tr>
				      	
				      	<tr>
				      	  <td style="background-color: #e0ebeb;">
				      	  	<label for="email">이메일</label>
				      	  </td>
				      	  <td>
				      	    <input type="text" name="email" id="email" value="" maxlength="30" required autofocus autocomplete="off" style="width: 200px;"/>
				      	  </td>
				      	</tr>
				      	
				      	<tr>
				      	  <td style="background-color: #e0ebeb;">
				      	  	<label for="phone">전화번호</label>
				      	  </td>
				      	  <td>
				      	    <input type="text" name="phone" id="phone" value="" maxlength="11" required style="width: 200px;"/>
				      	  </td>
				      	</tr>
				      	
				      	<tr>
				      	  <td style="background-color: #e0ebeb;">
				      	  	<label for="companyname">회사</label>
				      	  </td>
				      	  <td>
				      	    <input type="text" name="companyname" id="companyname" value="" maxlength="20" required style="width: 200px;"/>
				      	  </td>
				      	</tr>
				      	
				      	<tr>
				      	  <td style="background-color: #e0ebeb;">
				      	  	<label for="groupname">그룹</label>
				      	  </td>
				      	  <td>
				      	    <input type="text" name="groupname" id="groupname" value="" maxlength="20" required style="width: 200px;"/>
				      	  </td>
				      	</tr>
				      	
				        </table>
				     </form> 
				      <!-- Modal footer -->
				      <div class="modal-footer">
				        <button type="button" class="btn btn-primary" id="register">등록</button>
				        <button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
				      </div>
				      
				     
		        </div>
			</div>
        </div> 
      </div>
    </div>
  </div>
        
<!-- 추가 버튼을 누를시의 Modal 창 -->
