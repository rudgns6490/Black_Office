<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<%
	String ctxPath = request.getContextPath();
%>
    
<link href="<%= ctxPath %>/resources/css/admin/personnelAnnouncementList.css" rel="stylesheet">

<script>

$(document).ready(function(){
	
	
	var searchType = sessionStorage.getItem("searchType");
	var searchWord = sessionStorage.getItem("searchWord");

	if(searchType != null && searchWord != null) {
		$("select[name=searchType]").val(searchType);
		$(".searchWord").val(searchWord);
	}
	
 	if( ${tabid != null} ){
		$("#${tabid}").tab('show');
	}
 	else{
 		$("#retirement").tab('show');
 	}
	 
	//퇴사
    $("#searchretirement").on('click', function(){

    	sessionStorage.removeItem("searchType");
    	sessionStorage.removeItem("searchWord");
    	
    	sessionStorage.setItem("searchType", $("select[name=searchType]").val().trim());
    	sessionStorage.setItem("searchWord", $("#searchretirementList").val().trim());
    	
    	var frm = document.retirementFrm;
    	frm.method = "GET";
    	frm.action = "<%= request.getContextPath()%>/retirementList.action"
    	frm.submit();
    });
    
	//부서이동
	$("#movedepartment").on('click', function(){
		
		sessionStorage.removeItem("searchType");
    	sessionStorage.removeItem("searchWord");

		sessionStorage.setItem("searchType", $("select[name=searchType]").val().trim());
    	sessionStorage.setItem("searchWord", $("#searchmovedepartment").val().trim());
    	
    	var frm = document.movedepartmentFrm;
    	frm.method = "GET";
    	frm.action = "<%= request.getContextPath()%>/movedepartmentList.action"
    	frm.submit();
    });
	
	//진급
	$("#movePosition").on('click', function(){

		sessionStorage.removeItem("searchType");
    	sessionStorage.removeItem("searchWord");
		
		sessionStorage.setItem("searchType", $("select[name=searchType]").val().trim());
    	sessionStorage.setItem("searchWord", $("#searchmoveposition").val().trim());
		
    	var frm = document.movepositionFrm;
    	frm.method = "GET";
    	frm.action = "<%= request.getContextPath()%>/movePositionList.action"
    	frm.submit();
    });
    
	//휴직
	$("#searchleaveofAbsence").on('click', function(){
		
		sessionStorage.removeItem("searchType");
    	sessionStorage.removeItem("searchWord");
		
		sessionStorage.setItem("searchType", $("select[name=searchType]").val().trim());
    	sessionStorage.setItem("searchWord", $("#searchleaveofAbsenceList").val().trim());
    	
    	var frm = document.leaveofAbsenceFrm;
    	frm.method = "GET";
    	frm.action = "<%= request.getContextPath()%>/leaveofAbsenceList.action"
    	frm.submit();
    });
	
	//복직
	$("#reappoint").on('click', function(){

		sessionStorage.removeItem("searchType");
    	sessionStorage.removeItem("searchWord");
		
		sessionStorage.setItem("searchType", $("select[name=searchType]").val().trim());
    	sessionStorage.setItem("searchWord", $("#searchreappoint").val().trim());
    	
    	var frm = document.reappointFrm;
    	frm.method = "GET";
    	frm.action = "<%= request.getContextPath()%>/reappointList.action"
    	frm.submit();
    });
    
});

</script>

  <div id="content-wrapper" style="padding-top: 0;">
    <div class="container-fluid text-center">
      
      <!-- 메인 본문 내용 소스 작업은 여기서 수정 2020/01/03 kkh -->
      <div class="row content">
        <div class="col-sm-12" style="padding: 5%;">
        
	      <h2 style="text-align: left;">인사이동내역</h2>
	    
	      <!-- Nav tabs -->
 		  <ul class="nav nav-tabs">
   		    <li class="nav-item">
     		  <a class="nav-link active" data-toggle="tab" href="#retirement">퇴사</a>
   		    </li>
   		    <li class="nav-item">
     		  <a class="nav-link" data-toggle="tab" href="#departmentmove">부서이동</a>
   		    </li>
   		    <li class="nav-item">
     		  <a class="nav-link" data-toggle="tab" href="#updateposition">승진</a>
   		    </li>
   		    <li class="nav-item">
     		  <a class="nav-link" data-toggle="tab" href="#leaveofAbsence">휴직</a>
   		    </li>
   		    <li class="nav-item">
      		  <a class="nav-link" data-toggle="tab" href="#reinstate">복직</a>
   		    </li>
 		  </ul>

  		  <!-- Tab panes -->
  		  <div class="tab-content">
  		    <!-- 퇴사 -->
    		<div id="retirement" class="container tab-pane fade" style="margin-top: 30px;">
      		  <form name="retirementFrm">
		        <table class="table table-bordered" style="text-align: left;">
		      	  <tr>
		      	    <td style="width: 15%; background-color: #e0ebeb;">검색조건</td>
		      	    <td>
		      	      <select name="searchType" class="form-control searchType" style="width: 15%; display: inline-block;">
	  				    <option value="employeeno">사원번호</option>
	  				    <option value="name">이름</option>
	  				    <option value="departmentname">부서명</option>
	  				    <option value="positionname">직위명</option>
					  </select>
					  <input type="text"  name="searchWord" class="searchWord" id="searchretirementList" value="" maxlength="20" required style="width: 30%; display: inline-block;" />
					  <input type="hidden" name="tabid" value="retirement" />
					  <button type="button" class="btn btn-primary" id="searchretirement" style="width: 10%; display: inline-block;">검색</button>
		      	    </td>
		      	  </tr>
		        </table>
			  </form>
			  
			  <table class="table table-hover">
      	    	<thead style="background-color: navy; color: #fff;">
      	    	  <tr>
      	      	    <th>사번</th>
      	      	    <th>성명</th>
      	      	    <th>부서</th>
      	      	    <th>직위</th>
      	            <th>퇴사일자</th>
      	          </tr>
      	    	</thead>
      	        <tbody>
      	          <c:forEach var="retirement" items='${retirementList}' varStatus="status">
			  	    <tr>
			  	      <td>${retirement.employeeno}</td>
			  	      <td>${retirement.name}</td>
			  	      <td>${retirement.departmentname}</td>
			  	      <td>${retirement.positionname}</td>
			  	      <td>${retirement.registerday}</td>
			  	    </tr>
			  	  </c:forEach>
      	    	</tbody>
      	  	  </table>
    		</div>
    		<!-- 부서이동 -->
    		<div id="departmentmove" class="container tab-pane fade col-sm-12" style="margin-top: 30px;">
			  <form name="movedepartmentFrm">
		        <table class="table table-bordered" style="text-align: left;">
		      	  <tr>
		      	    <td style="width: 15%; background-color: #e0ebeb;">검색조건</td>
		      	    <td>
		      	      <select name="searchType" class="form-control searchType" style="width: 15%; display: inline-block;">
	  				    <option value="employeeno">사원번호</option>
	  				    <option value="name">이름</option>
	  				    <option value="departmentname">부서명</option>
	  				    <option value="positionname">직위명</option>
					  </select>
					  <input type="text"  name="searchWord" class="searchWord" id="searchmovedepartment" value="" maxlength="20" required style="width: 30%; display: inline-block;" />
					  <input type="hidden" name="tabid" value="departmentmove" />
					  <button type="button" class="btn btn-primary" id="movedepartment" style="width: 10%; display: inline-block;">검색</button>
		      	    </td>
		      	  </tr>
		        </table>
			  </form>
			  
			  <table class="table table-hover">
      	    	<thead style="background-color: navy; color: #fff;">
      	    	  <tr>
      	      	    <th>사번</th>
      	      	    <th>성명</th>
      	      	    <th>부서</th>
      	      	    <th>이동된 부서명</th>
      	            <th>이동날짜</th>
      	          </tr>
      	    	</thead>
      	        <tbody>
      	          <c:forEach var="movedepartment" items='${movedepartmentList}' varStatus="status">
			  	    <tr>
			  	      <td>${movedepartment.employeeno}</td>
			  	      <td>${movedepartment.name}</td>
			  	      <td>${movedepartment.departmentname}</td>
			  	      <td>${movedepartment.movedepartmentname}</td>
			  	      <td>${movedepartment.registerday}</td>
			  	    </tr>
			  	  </c:forEach>
      	    	</tbody>
      	  	  </table>
    		</div>
    		<!-- 진급 -->
    		<div id="updateposition" class="container tab-pane fade col-sm-12" style="margin-top: 30px;">
			  <form name="movepositionFrm">
		        <table class="table table-bordered" style="text-align: left;">
		      	  <tr>
		      	    <td style="width: 15%; background-color: #e0ebeb;">검색조건</td>
		      	    <td>
		      	      <select name="searchType" class="form-control searchType" style="width: 15%; display: inline-block;">
	  				    <option value="employeeno">사원번호</option>
	  				    <option value="name">이름</option>
	  				    <option value="departmentname">부서명</option>
	  				    <option value="positionname">직위명</option>
					  </select>
					  <input type="text"  name="searchWord" class="searchWord" id="searchmoveposition" value="" maxlength="20" required style="width: 30%; display: inline-block;" />
					  <input type="hidden" name="tabid" value="updateposition" />
					  <button type="button" class="btn btn-primary" id="movePosition" style="width: 10%; display: inline-block;">검색</button>
		      	    </td>
		      	  </tr>
		        </table>
			  </form>
			  
			  <table class="table table-hover">
      	    	<thead style="background-color: navy; color: #fff;">
      	    	  <tr>
      	      	    <th>사번</th>
      	      	    <th>성명</th>
      	      	    <th>부서</th>
      	      	    <th>직위</th>
      	      	    <th>진급한직위명</th>
      	            <th>진급날짜</th>
      	          </tr>
      	    	</thead>
      	        <tbody>
      	          <c:forEach var="movePosition" items='${movePositionList}' varStatus="status">
			  	    <tr>
			  	      <td>${movePosition.employeeno}</td>
			  	      <td>${movePosition.name}</td>
			  	      <td>${movePosition.departmentname}</td>
			  	      <td>${movePosition.positionname}</td>
			  	      <td>${movePosition.movepositionname}</td>
			  	      <td>${movePosition.registerday}</td>
			  	    </tr>
			  	  </c:forEach>
      	    	</tbody>
      	  	  </table>
    		</div>
    		<!-- 휴직 -->
    		<div id="leaveofAbsence" class="container tab-pane fade col-sm-12" style="margin-top: 30px;">
			  <form name="leaveofAbsenceFrm">
		        <table class="table table-bordered" style="text-align: left;">
		      	  <tr>
		      	    <td style="width: 15%; background-color: #e0ebeb;">검색조건</td>
		      	    <td>
		      	      <select name="searchType" class="form-control searchType" style="width: 15%; display: inline-block;">
	  				    <option value="employeeno">사원번호</option>
	  				    <option value="name">이름</option>
	  				    <option value="departmentname">부서명</option>
	  				    <option value="positionname">직위명</option>
					  </select>
					  <input type="text"  name="searchWord" class="searchWord" id="searchleaveofAbsenceList" value="" maxlength="20" required style="width: 30%; display: inline-block;" />
					  <input type="hidden" name="tabid" value="leaveofAbsence" />
					  <button type="button" class="btn btn-primary" id="searchleaveofAbsence" style="width: 10%; display: inline-block;">검색</button>
		      	    </td>
		      	  </tr>
		        </table>
			  </form>
			  
			  <table class="table table-hover">
      	    	<thead style="background-color: navy; color: #fff;">
      	    	  <tr>
      	      	    <th>사번</th>
      	      	    <th>성명</th>
      	      	    <th>부서</th>
      	      	    <th>직위</th>
      	      	    <th>휴직종류</th>
      	      	    <th>휴직일자</th>
      	      	  </tr>
      	    	</thead>
      	        <tbody>
      	          <c:forEach var="leaveofAbsence" items='${leaveofAbsenceList}' varStatus="status">
			  	    <tr>
			  	      <td>${leaveofAbsence.employeeno}</td>
			  	      <td>${leaveofAbsence.name}</td>
			  	      <td>${leaveofAbsence.departmentname}</td>
			  	      <td>${leaveofAbsence.positionname}</td>
			  	      <td>${leaveofAbsence.leaveofabsence}</td>
			  	      <td>${leaveofAbsence.registerday}</td>
			  	    </tr>
			  	  </c:forEach>
      	    	</tbody>
      	  	  </table>
    		</div>
    		<!-- 복직 -->
    		<div id="reinstate" class="container tab-pane fade col-sm-12" style="margin-top: 30px;">
			  <form name="reappointFrm">
		        <table class="table table-bordered" style="text-align: left;">
		      	  <tr>
		      	    <td style="width: 15%; background-color: #e0ebeb;">검색조건</td>
		      	    <td>
		      	      <select name="searchType" class="form-control searchType" style="width: 15%; display: inline-block;">
	  				    <option value="employeeno">사원번호</option>
	  				    <option value="name">이름</option>
	  				    <option value="departmentname">부서명</option>
	  				    <option value="positionname">직위명</option>
					  </select>
					  <input type="text"  name="searchWord" class="searchWord" id="searchreappoint" value="" maxlength="20" required style="width: 30%; display: inline-block;" />
					  <input type="hidden" name="tabid" value="reinstate" />
					  <button type="button" class="btn btn-primary" id="reappoint" style="width: 10%; display: inline-block;">검색</button>
		      	    </td>
		      	  </tr>
		        </table>
			  </form>
			  
			  <table class="table table-hover">
      	    	<thead style="background-color: navy; color: #fff;">
      	    	  <tr>
      	      	    <th>사번</th>
      	      	    <th>성명</th>
      	      	    <th>부서</th>
      	      	    <th>직위</th>
      	            <th>복직일자</th>
      	          </tr>
      	    	</thead>
      	        <tbody>
      	          <c:forEach var="reappoint" items='${reappointList}' varStatus="status">
			  	    <tr>
			  	      <td>${reappoint.employeeno}</td>
			  	      <td>${reappoint.name}</td>
			  	      <td>${reappoint.departmentname}</td>
			  	      <td>${reappoint.positionname}</td>
			  	      <td>${reappoint.registerday}</td>
			  	    </tr>
			  	  </c:forEach>
      	    	</tbody>
      	  	  </table>
    		</div>
    		
  		  </div>
        </div>
      </div>

      <div class="col-sm-12" style="margin-top: 10px; padding: 5%;">
   	    <table class="table table-bordered">
   	      <thead class="thead-dark">
   	        <tr>
   	          <th>참조</th>
   	        </tr>
   	      </thead>
   	      <tbody style="text-align: left;">
   	        <tr>
   	          <td>
   	         	<p>- 전사관리 내역을 조회할수 있습니다.<br/>
   	         	- 조회만 가능하고 수정은 불가능합니다.<br/>
				</p>
   	          </td>
   	        </tr>
   	      </tbody>
   	    </table>
      </div>
      
    </div>
    <!-- /.container-fluid -->
  </div>
  <!-- /.content-wrapper -->

</div>
<!-- /#wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>


