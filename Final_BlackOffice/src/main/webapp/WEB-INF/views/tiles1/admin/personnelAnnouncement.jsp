<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String ctxPath = request.getContextPath();
%>
    
<link href="<%= ctxPath %>/resources/css/admin/personnelAnnouncement.css" rel="stylesheet">
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
    
  });//end of $(document).ready(function(){ --------------------------
  
  
</script>

  <div id="content-wrapper" style="padding-top: 0;">
    <div class="container-fluid text-center">
      
      <!-- 메인 본문 내용 소스 작업은 여기서 수정 2020/01/03 kkh -->
      <div class="row content">
        <div class="col-sm-12" style="padding: 5%;">
		  <fieldset>
		    <legend>인사이동</legend>
		    
		    <form name="registerSawon" method="post">
		      <table class="table table-bordered">
		      	<tr>
		      	  <td style="width: 15%; background-color: #e0ebeb;">검색조건</td>
		      	  <td>
		      	    <select class="form-control" style="width: 15%; display: inline-block;">
	  				  <option>1</option>
	  				  <option>2</option>
	  				  <option>3</option>
	  				  <option>4</option>
	  				  <option>5</option>
					</select>
					<input type="text"  name="searchWord" id="searchWord" value="" maxlength="20" required style="width: 30%; display: inline-block;" />
					<button type="button" class="btn btn-primary" onclick="goSearch();" style="width: 10%; display: inline-block;">검색</button>
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
      	      <tr data-toggle="modal" data-target="#myModal" data-backdrop="static">
      	        <td colspan="5">입력된 정보가 없습니다.</td>
      	      </tr>
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
    		<div id="retirement" class="container tab-pane active"><br>
      		  <h3>퇴사</h3>
      		  <div>
      		    <table class="table">
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">사원번호</td>
      		        <td>사번1</td>
      		      </tr> 
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">대상자</td>
      		        <td>사원1</td>
      		      </tr>
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">소속</td>
      		        <td>부서명</td>
      		      </tr>
      		      <tr>
      		        <td colspan="2">
      		          <button type="button" class="btn btn-primary" onclick="goRetirement()" >확인</button>
      		        </td>
      		      </tr>
      		    </table>
      		  </div>
    		</div>
    		<div id="departmentmove" class="container tab-pane fade"><br>
      		  <h3>부서이동</h3>
      		  <div>
      		    <table class="table">
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">사원번호</td>
      		        <td>사번1</td>
      		      </tr>  
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">대상자</td>
      		        <td>사원1</td>
      		      </tr>
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">부서명</td>
      		        <td>부서명1</td>
      		      </tr>
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">이동할 부서명</td>
      		        <td>
      		          <select class="form-control" style="width: 30%;">
	  				    <option>1</option>
	  				    <option>2</option>
	  				    <option>3</option>
	  				    <option>4</option>
	  				    <option>5</option>
					  </select>
      		        </td>  
      		      </tr>
      		      <tr>
      		        <td colspan="2">
      		          <button type="button" class="btn btn-primary" onclick="goRetirement()" >확인</button>
      		        </td>
      		      </tr>
      		    </table>
      		  </div>
    		</div>
    		<div id="updateposition" class="container tab-pane fade"><br>
      		  <h3>직위변경</h3>
      		  <div>
      		    <table class="table">
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">사원번호</td>
      		        <td>사번1</td>
      		      </tr>  
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">대상자</td>
      		        <td>사원1</td>
      		      </tr>
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">부서명</td>
      		        <td>부서명1</td>
      		      </tr>
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">직위명</td>
      		        <td>직위명1</td>
      		      </tr>
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">변경할 직위명</td>
      		        <td>
      		          <select class="form-control" style="width: 30%;">
	  				    <option>1</option>
	  				    <option>2</option>
	  				    <option>3</option>
	  				    <option>4</option>
	  				    <option>5</option>
					  </select>
      		        </td>  
      		      </tr>
      		      <tr>
      		        <td colspan="2">
      		          <button type="button" class="btn btn-primary" onclick="goRetirement()" >확인</button>
      		        </td>
      		      </tr>
      		    </table>
      		  </div>
    		</div>
    		<div id="leaveofAbsence" class="container tab-pane fade"><br>
      		  <h3>휴직</h3>
      		  <div>
      		    <table class="table">
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">사원번호</td>
      		        <td>사번1</td>
      		      </tr>  
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">대상자</td>
      		        <td>사원1</td>
      		      </tr>
				  <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">휴직종류</td>
      		        <td>
      		          <select class="form-control" style="width: 30%;">
	  				    <option>1</option>
	  				    <option>2</option>
	  				    <option>3</option>
	  				    <option>4</option>
	  				    <option>5</option>
					  </select>
      		        </td>  
      		      </tr>
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">휴직기간</td>
      		        <td>
      		          <div id="Datepicker" style="margin-top: 5px;" style="float: right;">     
                        <label for="fromDate">시작일</label>
                        <input type="text" name="fromDate" id="fromDate" value="${fromDate}">
                         ~
                        <label for="toDate" >종료일</label>
                        <input type="text" name="toDate" id="toDate" value="${toDate}"> 
               		  </div>
      		        </td>
      		      </tr>
      		      <tr>
      		        <td colspan="2">
      		          <button type="button" class="btn btn-primary" onclick="goRetirement()" >확인</button>
      		        </td>
      		      </tr>
      		    </table>
      		  </div>
    		</div>
    		<div id="reinstate" class="container tab-pane fade"><br>
      		  <h3>복직</h3>
      		  <div>
      		    <table class="table">
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">사원번호</td>
      		        <td>사번1</td>
      		      </tr>  
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">대상자</td>
      		        <td>사원1</td>
      		      </tr>
      		      <tr>
      		        <td style="width: 20%; background-color: #e0ebeb;">부서명</td>
      		        <td>부서명1</td>
      		      </tr>
      		      <tr>
      		        <td colspan="2">
      		          <button type="button" class="btn btn-primary" onclick="goRetirement()" >확인</button>
      		        </td>
      		      </tr>
      		    </table>
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

