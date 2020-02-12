<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<%
	String ctxPath = request.getContextPath();
%>
    
<link href="<%= ctxPath %>/resources/css/admin/personnelAnnouncement.css" rel="stylesheet">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script type="text/javascript">
  
	$(document).ready(function(){
		 
		var searchType = sessionStorage.getItem("searchType_pa");
		var searchWord = sessionStorage.getItem("searchWord_pa");
		
		if(searchType != null && searchWord != null) {
			$("select[name=searchType]").val(searchType);
			$("#searchWord").val(searchWord);
		}
		
		$("#searchWord").keydown(function(event) {
	        if(event.keycode == 13) {
	        	// 엔터를 했을 경우
	        	sessionStorage.removeItem("searchType_pa");
				sessionStorage.removeItem("searchWord_pa");
		    	
		    	sessionStorage.setItem("searchType_pa", $("select[name=searchType]").val().trim());
		    	sessionStorage.setItem("searchWord_pa", $("#searchWord").val().trim());
			
				var frm = document.searchFrm;
			    frm.method = "GET";
			    frm.action = "<%= request.getContextPath()%>/personnelAnnouncement.action";
			    frm.submit();
	        }
	    });
		
		$("#goSearch").on("click", function() {
			sessionStorage.removeItem("searchType_pa");
			sessionStorage.removeItem("searchWord_pa");
	    	
	    	sessionStorage.setItem("searchType_pa", $("select[name=searchType]").val().trim());
	    	sessionStorage.setItem("searchWord_pa", $("#searchWord").val().trim());
		
			var frm = document.searchFrm;
		    frm.method = "GET";
		    frm.action = "<%= request.getContextPath()%>/personnelAnnouncement.action";
		    frm.submit();
		});

	    var employeeno = "";
	    var name = "";
	    var departmentname = "";
	    var positionname= "";
		
		$(document).on("click", ".memberVO", function(){
			
			employeeno = $(this).data("employeeno");
			name = $(this).data("name");
			departmentname = $(this).data("departmentname");
			positionname = $(this).data("positionname");
			
			$(".employeeno").val(employeeno);
			$(".name").val(name);
			$(".departmentname").val(departmentname);
			$(".positionname").val(positionname);
			
		});
		
		$("#goRetirement").on('click', function(){
	    	var frm = document.retirementFrm;
	    	frm.method = "GET";
	    	frm.action = "<%= request.getContextPath()%>/retirement.action"
	    	frm.submit();
        });
		
		$("#moveDepartment").on('click', function(){
			var frm = document.moveDepartmentFrm;
	    	frm.method = "GET";
	    	frm.action = "<%= request.getContextPath()%>/moveDepartment.action"
	    	frm.submit();
		});
		
		$("#moveposition").on('click', function(){
			var frm = document.movePositionFrm;
	    	frm.method = "GET";
	    	frm.action = "<%= request.getContextPath()%>/moveposition.action"
	    	frm.submit();
		});
		
		$("#leaveofabsence").on('click', function(){
			var frm = document.leaveofabsenceFrm;
	    	frm.method = "GET";
	    	frm.action = "<%= request.getContextPath()%>/leaveofabsence.action"
	    	frm.submit();
		});
		
		$("#reappoint").on('click', function(){
			var frm = document.reappointFrm;
	    	frm.method = "GET";
	    	frm.action = "<%= request.getContextPath()%>/reappoint.action"
	    	frm.submit();
		});
	}); 
	//end of $(document).ready(function(){ --------------------------
</script>

  <div id="content-wrapper" style="padding-top: 0;">
    <div class="container-fluid text-center">
      
      <!-- 메인 본문 내용 소스 작업은 여기서 수정 2020/01/03 kkh -->
      <div class="row content">
        <div class="col-sm-12" style="padding: 5%;">
		  <fieldset>
		    <legend>인사이동</legend>
		    
		    <form name="searchFrm" method="post">
		      <table class="table table-bordered">
		      	<tr>
		      	  <td style="width: 15%; background-color: #e0ebeb;">검색조건</td>
		      	  <td>
		      	    <select name="searchType" id="searchType" class="form-control" style="width: 15%; display: inline-block;">
	  				  <option value="name">이름</option>
	  				  <option value="employeeno">사원번호</option>
	  				  <option value="fk_departmentno">부서번호</option>
	  				  <option value="fk_positionno">직위번호</option>
					</select>
					<input type="text"  name="searchWord" id="searchWord" value="" maxlength="20" required style="width: 30%; display: inline-block;" />
					<button type="button" class="btn btn-primary" id="goSearch" style="width: 10%; display: inline-block;">검색</button>
		      	  </td>
		      	</tr>
		      </table>
		    </form>
		  </fieldset>
		  
      	  <table class="table table-hover">
      	    <thead style="background-color: navy; color: #fff;">
      	      <th>사번</th>
      	      <th>성명</th>
      	      <th>부서</th>
      	      <th>직위</th>
      	      <th>상태</th>
      	    </thead>
      	    <tbody>
      	      <c:forEach var="map" items='${mapList}' varStatus="status">
			  	  <tr data-toggle="modal" data-target="#myModal"
			  	   data-employeeno="${map.employeeno}"
			  	   data-name="${map.name}"
			  	   data-departmentname="${map.departmentname}"
			  	   data-positionname="${map.positionname}"
			  	   data-backdrop="static" class="memberVO">
			  	    <td>${map.employeeno}</td>
			  	    <td>${map.name}</td>
			  	    <td>${map.departmentname}</td>
			  	    <td>${map.positionname}</td>
			  	    <td>${map.status}</td>
			  	  </tr>
			  </c:forEach>
      	    </tbody>
      	  </table>
      	
   	      <table class="table table-bordered">
   	        <thead class="thead-dark">
   	          <tr>
   	            <th>참조</th>
   	          </tr>
   	        </thead>
   	        <tbody style="text-align: left;">
   	          <tr>
   	            <td>
   	       	      <p>- 휴직/복직/퇴사의 경우 해당 휴직/복직/퇴사일 당일 배치프로그램에서 수행됩니다.<br/>
   	       	      - 퇴사자의 경우 당일 배치프로그램에서 삭제합니다.</p>
   	            </td>
   	          </tr>
   	        </tbody>
   	      </table>
		 
        </div>
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

  <!-- 검색한 사원 클릭 시 해당 사원 인사이동 창이 뜬다. 2020/01/06 kkh --> 
  <!-- The Modal -->
  <div class="modal" id="myModal">
    <div class="modal-dialog modal-xl">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">인사이동</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
          <!-- Nav tabs -->
  		  <ul class="nav nav-tabs">
    		<li class="nav-item">
      		  <a class="personnelAnnouncement nav-link active" data-toggle="tab" href="#retirement">퇴사</a>
    		</li>
    		<li class="nav-item">
      		  <a class="personnelAnnouncement nav-link" data-toggle="tab" href="#departmentmove">부서이동</a>
    		</li>
    		<li class="nav-item">
      		  <a class="personnelAnnouncement nav-link" data-toggle="tab" href="#updateposition">승진</a>
    		</li>
    		<li class="nav-item">
      		  <a class="personnelAnnouncement nav-link" data-toggle="tab" href="#leaveofAbsence">휴직</a>
    		</li>
    		<li class="nav-item">
      		  <a class="personnelAnnouncement nav-link" data-toggle="tab" href="#reinstate">복직</a>
    		</li>
  		  </ul>

  		  <!-- Tab panes -->
  		  <div class="tab-content">
    		<div id="retirement" class="container tab-pane active"><br>
      		  <h3>퇴사</h3>
      		  <div>
      		    <form name="retirementFrm">
      		    <table class="table">
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">사원번호</td>
      		        <td>
      		          <input style="border: none;" type="text" name="employeeno" class="employeeno" />
      		        </td>
      		      </tr> 
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">이름</td>
      		        <td>
      		          <input style="border: none;" type="text" name="name" class="name" />
      		        </td>
      		      </tr>
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">부서명</td>
      		        <td>
      		          <input style="border: none;" type="text" name="departmentname" class="departmentname" />
      		        </td>
      		      </tr>
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">직위명</td>
      		        <td>
      		          <input style="border: none;" type="text" name="positionname" class="positionname" />
      		        </td>
      		      </tr>
      		      <tr>
      		        <td colspan="2">
      		          <button type="button" class="btn btn-primary" id="goRetirement" >확인</button>
      		        </td>
      		      </tr>
      		    </table>
      		    </form>
      		  </div>
    		</div>
    		<div id="departmentmove" class="container tab-pane fade"><br>
      		  <h3>부서이동</h3>
      		  <div>
      		    <form name="moveDepartmentFrm">
      		    <table class="table">
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">사원번호</td>
      		        <td>
      		          <input style="border: none;" type="text" name="employeeno" class="employeeno" />
      		        </td>
      		      </tr>  
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">이름</td>
      		        <td>
      		          <input style="border: none;" type="text" name="name" class="name" />
      		        </td>
      		      </tr>
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">부서명</td>
      		        <td>
      		          <input style="border: none;" type="text" name="departmentname" class="departmentname" />
      		        </td>
      		      </tr>
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">이동할 부서명</td>
      		        <td>
      		          <select name="movedepartmentno" class="form-control" style="width: 30%;">
	  				    <option value="1">인사팀</option>
	  				  	<option value="2">마케팅팀</option>
	  				  	<option value="3">개발1팀</option>
	  				  	<option value="4">개발2팀</option>
	  				  	<option value="5">영업팀</option>
					  </select>
      		        </td>  
      		      </tr>
      		      <tr>
      		        <td colspan="2">
      		          <button type="button" class="btn btn-primary" id="moveDepartment" >확인</button>
      		        </td>
      		      </tr>
      		    </table>
      		    </form>
      		  </div>
    		</div>
    		<div id="updateposition" class="container tab-pane fade"><br>
      		  <h3>직위변경</h3>
      		  <div>
      		    <form name="movePositionFrm">
      		    <table class="table">
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">사원번호</td>
      		        <td>
      		          <input style="border: none;" type="text" name="employeeno" class="employeeno" />
      		        </td>
      		      </tr>  
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">이름</td>
      		        <td>
      		          <input style="border: none;" type="text" name="name" class="name" />
      		        </td>
      		      </tr>
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">부서명</td>
      		        <td>
      		          <input style="border: none;" type="text" name="departmentname" class="departmentname" />
      		        </td>
      		      </tr>
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">직위명</td>
      		        <td>
      		          <input style="border: none;" type="text" name="positionname" class="positionname" />
      		        </td>
      		      </tr>
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">변경할 직위명</td>
      		        <td>
      		          <select name="movepositionno" class="movepositionno" class="form-control" style="width: 30%;">
	  				    <option value="1">사장</option>
	  				  	<option value="2">이사</option>
	  				  	<option value="3">부장</option>
	  				  	<option value="4">차장</option>
	  				  	<option value="5">과장</option>
	  				  	<option value="6">대리</option>
	  				  	<option value="7">사원</option>
					  </select>
      		        </td>  
      		      </tr>
      		      <tr>
      		        <td colspan="2">
      		          <button type="button" class="btn btn-primary" id="moveposition" >확인</button>
      		        </td>
      		      </tr>
      		    </table>
      		    </form>
      		  </div>
    		</div>
    		<div id="leaveofAbsence" class="container tab-pane fade"><br>
      		  <h3>휴직</h3>
      		  <div>
      		    <form name="leaveofabsenceFrm">
      		    <table class="table">
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">사원번호</td>
      		        <td>
      		          <input style="border: none;" type="text" name="employeeno" class="employeeno" />
      		        </td>
      		      </tr>  
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">이름</td>
      		        <td>
      		          <input style="border: none;" type="text" name="name" class="name" />
      		        </td>
      		      </tr>
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">부서명</td>
      		        <td>
      		          <input style="border: none;" type="text" name="departmentname" class="departmentname" />
      		        </td>
      		      </tr>
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">직위명</td>
      		        <td>
      		          <input style="border: none;" type="text" name="positionname" class="positionname" />
      		        </td>
      		      </tr>
				  <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">휴직종류</td>
      		        <td>
      		          <select name="leaveofabsence" class="form-control leaveofabsence" style="width: 30%;">
	  				    <option value="parentalleave">육아휴직</option>
	  				    <option value="paidleave">유급휴직</option>
	  				    <option value="unpaidleave">무급휴직</option>
					  </select>
      		        </td>  
      		      </tr>
      		      <tr>
      		        <td colspan="2">
      		          <button type="button" class="btn btn-primary" id="leaveofabsence" >확인</button>
      		        </td>
      		      </tr>
      		    </table>
      		    </form>
      		  </div>
    		</div>
    		<div id="reinstate" class="container tab-pane fade"><br>
      		  <h3>복직</h3>
      		  <div>
      		    <form name="reappointFrm">
      		    <table class="table">
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">사원번호</td>
      		        <td>
      		          <input style="border: none;" type="text" name="employeeno" class="employeeno" />
      		        </td>
      		      </tr>  
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">이름</td>
      		        <td>
      		          <input style="border: none;" type="text" name="name" class="name" />
      		        </td>
      		      </tr>
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">부서명</td>
      		        <td>
      		          <input style="border: none;" type="text" name="departmentname" class="departmentname" />
      		        </td>
      		      </tr>
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">직위명</td>
      		        <td>
      		          <input style="border: none;" type="text" name="positionname" class="positionname" />
      		        </td>
      		      </tr>
      		      <tr>
      		        <td colspan="2">
      		          <button type="button" class="btn btn-primary" id="reappoint" >확인</button>
      		        </td>
      		      </tr>
      		    </table>
      		    </form>
      		  </div>
    		</div>
  		  </div>
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
        </div>
        
      </div>
    </div>
  </div>

