<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<%
	String ctxPath = request.getContextPath();
%>
    
<link href="<%= ctxPath %>/resources/css/admin/admintotalexpenditure.css" rel="stylesheet">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script type="text/javascript">
  
  $(document).ready(function(){
	  
/* 	//검색어 유지시키기  
	if(${paraMap != null}) {
		$("#expenditureDepartment").val("${paraMap.expenditureDepartment}");
		$("#fromDate").val("${paraMap.fromDate}");
		$("#toDate").val("${paraMap.toDate}");
	} */
	
	var fromDate = sessionStorage.getItem("fromDate");
	var toDate = sessionStorage.getItem("toDate");
	var expenditureDepartment = sessionStorage.getItem("expenditureDepartment");
	
	if(fromDate != null && toDate != null && expenditureDepartment != null) {
		$("select[name=expenditureDepartment]").val(expenditureDepartment);
		$("#fromDate").val(fromDate);
		$("#toDate").val(toDate);
	}
      
	//캘린더 시작~~~~~~~~~~~~
	$('#fromDate').datepicker({
      showOn: "both",                     // 달력을 표시할 타이밍 (both: focus or button)
      buttonImage: "<%= ctxPath %>/resources/images/cal.jpg", // 버튼 이미지
      buttonImageOnly : true,             // 버튼 이미지만 표시할지 여부
      buttonText: "날짜선택",             	  // 버튼의 대체 텍스트
      dateFormat: "yy-mm-dd",             // 날짜의 형식
      changeMonth: true,                  // 월을 이동하기 위한 선택상자 표시여부
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
      
    var fromDate;      
    var toDate; 
    var today;
    var day = new Date();
      
    // 1주 버튼
    $("#week").click(function(){
      $("#toDate").val(getTodayType2()); 
      //today = dt.getDate();
         
      if(day.getDate()<7){
        $("#fromDate").val(getPastDateWeek2());
      }
      else{
        $("#fromDate").val(getTodayTypeWeek());
      }

      $("#fromDate").val();
    });
      
    // 1달
    $("#oneMonth").click(function(){
      $("#toDate").val(getTodayType2());   
      $("#fromDate").val(getPastDate(1));     
    });
    
    // 3달
    $("#threeMonth").click(function(){
      $("#toDate").val(getTodayType2());
      $("#fromDate").val(getPastDate(3));
    });       
    
    // 6달
    $("#sixMonth").click(function(){
      $("#toDate").val(getTodayType2());
      $("#fromDate").val(getPastDate(6));
      
    });   
      
    // 캘린더 버튼을 눌렀을 때-------------------------------------
//     $("#calBtn").click(function(){
//       if($("#toDate").val()==null || $("#fromDate").val()==null ){
//         alert("날짜를 선택하세요");
//       }
      
//       var toDate = $("#toDate").val();
//       var fromDate = $("#fromDate").val();

<%--       location.href="<%= request.getContextPath() %>/member/memberMyOrder.army?toDate="+toDate+"&fromDate="+fromDate;             --%>
//     });
    
  }); // end of $(document).ready(function(){
  
  // 현재 날짜
  function getTodayType2() {
	var date = new Date(); 
	return date.getFullYear() + "-" + ("0"+(date.getMonth()+1)).slice(-2) + "-" + ("0"+date.getDate()).slice(-2); 
  }

  
  function getTodayTypeWeek() {
	var date = new Date(); 
	return date.getFullYear() + "-" + ("0"+(date.getMonth()+1)).slice(-2) + "-" + ("0"+(date.getDate()-7)).slice(-2); 
  }
	 
  function getPastDate(period){
	var dt = new Date();
	
	dt.setMonth((dt.getMonth() + 1) - period);
	var year = dt.getFullYear();
	var month = dt.getMonth();
	var day = dt.getDate();
	 
	if(month < 10) month = "0" + month;
	if(day < 10) day = "0" + day;
	
	if(month == 00 ) {
		year = year - 1;
		month = 12;
	}
	 
	return year + "-" + month + "-" + day;
  }

  function getPastDateWeek2(){
	var dt = new Date();
	 
	dt.setMonth((dt.getMonth() + 1));
	dt.setDate((dt.getDate() - 7));
	   
	var year = dt.getFullYear();
	var month = dt.getMonth();
	var day = dt.getDate();
	 
	if(month < 10) month = "0" + month;
	if(day < 10) day = "0" + day;
	 
	return year + "-" + month + "-" + day;
  }
  
  function gosubmit() {
	
	var expenditureDepartment = $("#expenditureDepartment").val(); 
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val(); 
	
	if(fromDate.trim()=="") {
		alert("날짜를 모두 입력하세요!!");
		$("#fromDate").val(""); 
		$("#fromDate").focus();
		return;
	}
	
	if(toDate.trim()=="") {
		alert("날짜를 모두 입력하세요!!");
		$("#toDate").val(""); 
		$("#toDate").focus();
		return;
	}
	
	if(expenditureDepartment.trim()=="") {
		alert("부서를 선택하세요!!");
		$("#expenditureDepartment").val(""); 
		$("#expenditureDepartment").focus();
		return;
	}
	
	
	sessionStorage.removeItem("fromDate");
	sessionStorage.removeItem("toDate");
	sessionStorage.removeItem("expenditureDepartment");
	
	sessionStorage.setItem("expenditureDepartment", $("select[name=expenditureDepartment]").val().trim());
	sessionStorage.setItem("fromDate", $("#fromDate").val().trim());
	sessionStorage.setItem("toDate", $("#toDate").val().trim());
	
	
	var frm = document.expenditureFrm;
	
	frm.method = "GET";
	frm.action = "<%= request.getContextPath() %>/admintotalexpenditureshow.action";
	frm.submit();
  	
  }
  
  
</script>

  <div id="content-wrapper" style="padding-top: 0;">
    <div class="container-fluid text-center">
      
      <!-- 메인 본문 내용 소스 작업은 여기서 수정 2020/01/03 kkh -->
      <div class="row content">
		<div class="col-sm-12">
      	  <div style="padding: 20px;">
      	    <form name="expenditureFrm" action="<%= ctxPath %>/admintotalexpenditure.action" method="GET">
			  <fieldset>
				<legend>지출내역검색</legend>
				<table class="table">
				  <tr>
				  	<td style="background-color: #e0ebeb;">
				  	  <label for="name">검색(일)</label>
				  	</td>
				  	<td>
					  <button type="button" class="btn btn-secondary search_btn" id="week" >1주일</button>
               		  <button type="button" class="btn btn-secondary search_btn" id="oneMonth" >1개월</button>
               		  <button type="button" class="btn btn-secondary search_btn" id="threeMonth" >3개월</button>
               		  <button type="button" class="btn btn-secondary search_btn" id="sixMonth" >6개월</button>
					  <div id="Datepicker" style="margin-top: 5px;" style="float: right;">     
                        <label for="fromDate"></label>
                        <input type="text" name="fromDate" id="fromDate" value="${fromDate}">
                         ~
                        <label for="toDate" ></label>
                        <input type="text" name="toDate" id="toDate" value="${toDate}"> 
               		  </div>
				  	</td>
				  </tr>
				  
				  
				  <tr>
				    <td style="background-color: #e0ebeb;">
				      <label for="name">검색조건</label>
				  	</td>
				  	<td>
				  	  <select name="expenditureDepartment" id="expenditureDepartment" class="form-control" style="width: 20%; display: inline-block;">
				  	  	<option value="">부서명</option>
				  	  	<option value="1">인사팀</option>
				  	  	<option value="2">마케팅팀</option>
				  	  	<option value="3">개발1팀</option>
				  	  	<option value="4">개발2팀</option>
				  	  	<option value="5">영업팀</option>
					  </select>
					  <button type="button" class="btn btn-primary" onclick="gosubmit();">검색</button>
				  	</td>
				  </tr>
				</table>
			  </fieldset>
			</form>
      	  </div>
      	</div>
      	
      	<div class="col-sm-12">
      	  <div style="padding: 20px;">
      	    <table class="table table-hover">
      	      <thead style="background-color: navy; color: #fff;">
      	        <th>No.</th>
      	        <th>부서</th>
      	        <th>직위</th>
      	        <th>사번</th>
      	        <th>성명</th>
      	        <th>지출금액</th>
      	        <th>지출날짜</th>
      	      </thead>
      	      <tbody>
      	       <!-- ▼ 테이블 데이터 불러오기 2020.01.29.수 LBH -->
		      <c:if test="${!empty expenditure}">
				  <tr class="" role="listbox">
				  	<c:forEach var="viewexpecditure" items="${expenditure}" varStatus="status">
					  	<tr>
				  			<td class="item">${viewexpecditure.EXPENDITURENO}</td>
				  			<c:if test="${viewexpecditure.FK_POSITIONNO == 1}"><td class="item">사장</td></c:if>
				  			<c:if test="${viewexpecditure.FK_POSITIONNO == 2}"><td class="item">이사</td></c:if>
				  			<c:if test="${viewexpecditure.FK_POSITIONNO == 3}"><td class="item">부장</td></c:if>
				  			<c:if test="${viewexpecditure.FK_POSITIONNO == 4}"><td class="item">차장</td></c:if>
				  			<c:if test="${viewexpecditure.FK_POSITIONNO == 5}"><td class="item">과장</td></c:if>
				  			<c:if test="${viewexpecditure.FK_POSITIONNO == 6}"><td class="item">대리</td></c:if>
				  			<c:if test="${viewexpecditure.FK_POSITIONNO == 7}"><td class="item">사원</td></c:if>
				  			
				  			<td class="item">${viewexpecditure.DEPARTMENTNAME}</td>
							<%-- <c:if test="${viewexpecditure.FK_POSITIONNO == 1}"><td class="item">인사팀</td></c:if>
				  			<c:if test="${viewexpecditure.FK_POSITIONNO == 2}"><td class="item">마케팅팀</td></c:if>
				  			<c:if test="${viewexpecditure.FK_POSITIONNO == 3}"><td class="item">개발1팀</td></c:if>
				  			<c:if test="${viewexpecditure.FK_POSITIONNO == 4}"><td class="item">개발2팀</td></c:if>
				  			<c:if test="${viewexpecditure.FK_POSITIONNO == 5}"><td class="item">영업팀</td></c:if> --%>
							
				  			
				  			<%-- <td class="item">${viewexpecditure.FK_EMPLOYEENO}</td>
				  			<td class="item">${viewexpecditure.EMPLOYEENAME}</td>
				  			<td class="item">${viewexpecditure.PAYMENT_MONEY}</td>
				  			<td class="item testDate">${viewexpecditure.EXPENDITUREDAY}</td> --%>
				  			<td class="item">${viewexpecditure.FK_EMPLOYEENO}</td>
				  			<td class="item">${viewexpecditure.EMPLOYEENAME}</td>
				  			<td class="item">${viewexpecditure.PAYMENT_MONEY}</td>
				  			<td class="item">${viewexpecditure.EXPENDITUREDAY}</td>
				  			
				  		</tr>
				  	</c:forEach>
				  </tr>
		      </c:if>
		      
		      <c:if test="${empty expenditure}">
			      	<tr>
			          <td colspan="7">입력된 정보가 없습니다.</td>
			        </tr>
			  </c:if>
			  <!-- ▲ 테이블 데이터 불러오기 2020.01.29.수 LBH -->
			  	</table>
      	      </tbody>
      	  </div>
      	</div>
      	
      	<!-- === #126. 페이지바 만들기 === -->
		<div class="col-sm-12">
			${pageBar}
		</div>
      	
      	
<!--       	
      	<div class="col-sm-12">
      	  <div style="padding: 20px;">
   	        <table class="table table-bordered">
   	          <thead class="thead-dark">
   	            <tr>
   	              <th>참조</th>
   	            </tr>
   	          </thead>
   	          <tbody style="text-align: left;">
   	            <tr>
   	              <td>
   	         	    <p>- 검색기간은 지출결의서 항목 날짜를 기준으로 합니다.<br/>
   	         	       - 승인완료된 지출결의서만 조회가능합니다.<br/>
   	         	       - 부서명 시작날짜 끝날자를 모두 입력해야 조회 가능합니다.<br/>
				    </p>
   	              </td>
   	            </tr>
   	          </tbody>
   	        </table>
   	      </div>
      	</div>
      	 -->
      
      
      
	   	</div>
	   <!-- row content -->
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

  <!-- Logout Modal-->
  <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">×</span>
          </button>
        </div>
        <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
        <div class="modal-footer">
          <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
          <a class="btn btn-primary" href="login.html">Logout</a>
        </div>
      </div>
    </div>
  </div>

