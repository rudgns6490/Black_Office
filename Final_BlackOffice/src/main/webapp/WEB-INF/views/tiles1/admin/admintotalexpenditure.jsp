<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String ctxPath = request.getContextPath();
%>
    
<link href="<%= ctxPath %>/resources/css/admin/admintotalexpenditure.css" rel="stylesheet">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script type="text/javascript">
  
  $(document).ready(function(){
	 
	//캘린더 시작~~~~~~~~~~~~
	$('#fromDate').datepicker({
      showOn: "both",                     // 달력을 표시할 타이밍 (both: focus or button)
      buttonImage: "<%= ctxPath %>/images/cal.jpg", // 버튼 이미지
      buttonImageOnly : true,             // 버튼 이미지만 표시할지 여부
      buttonText: "날짜선택",             // 버튼의 대체 텍스트
      dateFormat: "yy-mm-dd",             // 날짜의 형식
      changeMonth: true,                  // 월을 이동하기 위한 선택상자 표시여부
      onClose: function( selectedDate ) {    
        $("#toDate").datepicker( "option", "minDate", selectedDate );
      }                
    });

    //종료일
    $('#toDate').datepicker({
      showOn: "both", 
      buttonImage: "<%= ctxPath %>/images/cal.jpg", 
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

      //$("#fromDate").val();
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
    $("#calBtn").click(function(){
      if($("#toDate").val()==null || $("#fromDate").val()==null ){
        alert("날짜를 선택하세요");
      }
      
      var toDate = $("#toDate").val();
      var fromDate = $("#fromDate").val();

      location.href="<%= request.getContextPath() %>/member/memberMyOrder.army?toDate="+toDate+"&fromDate="+fromDate;            
         
    });  
  });//end of $(document).ready(function(){
  
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
  
</script>

  <div id="content-wrapper" style="padding-top: 0;">
    <div class="container-fluid text-center">
      
      <!-- 메인 본문 내용 소스 작업은 여기서 수정 2020/01/03 kkh -->
      <div class="row content">
		<div class="col-sm-12">
      	  <div style="padding: 20px;">
      	    <form name="registerFrm" action="<%= ctxPath %>/admintotalexpenditure.action" method="GET">
			  <fieldset>
				<legend>지출통계검색</legend>
				<table class="table">
				  <tr>
				  	<td style="background-color: #e0ebeb;">
				  	  <label for="name">검색(일)</label>
				  	</td>
				  	<td>
					  <button class="btn btn-secondary search_btn" id="week">1주일</button>
               		  <button class="btn btn-secondary search_btn" id="oneMonth">1개월</button>
               		  <button class="btn btn-secondary search_btn" id="threeMonth">3개월</button>
               		  <button class="btn btn-secondary search_btn" id="sixMonth">6개월</button>
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
				  	  <select class="form-control" style="width: 20%; display: inline-block;">
	  				    <option>1</option>
	  				    <option>2</option>
	  				    <option>3</option>
	  				    <option>4</option>
	  				    <option>5</option>
					  </select>
					  <select class="form-control" style="width: 20%; display: inline-block;">
	  				    <option>1</option>
	  				    <option>2</option>
	  				    <option>3</option>
	  				    <option>4</option>
	  				    <option>5</option>
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
      	        <th>성명</th>
      	        <th>사번</th>
      	        <th>직위</th>
      	        <th>지출통계</th>
      	      </thead>
      	      <tbody>
      	        <tr>
      	          <td colspan="5">입력된 정보가 없습니다.</td>
      	        </tr>
      	      </tbody>
      	    </table>
      	  </div>
      	</div>
      	
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
   	         	    - 항목을 선택시 상세내역으로 이동합니다.<br/>
   	         	    - 소속검색시 하위 소속도 같이 조회됩니다.<br/>
				    </p>
   	              </td>
   	            </tr>
   	          </tbody>
   	        </table>
   	      </div>
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

