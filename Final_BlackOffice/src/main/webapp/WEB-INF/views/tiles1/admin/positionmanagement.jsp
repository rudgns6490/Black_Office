<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String ctxPath = request.getContextPath();
%>
    
<link href="<%= ctxPath %>/resources/css/admin/positionmanagement.css" rel="stylesheet">

  <div id="content-wrapper" style="padding-top: 0;">
    <div class="container-fluid text-center">
      
      <!-- 메인 본문 내용 소스 작업은 여기서 수정 2020/01/03 kkh -->
      <div class="row content">
        <div class="col-sm-12" style="padding: 5%;">
		  <fieldset>
		    <legend>직위관리</legend>
		  
		    <table class="table table-hover">
		      <thead style="background-color: navy; color: #fff;">
		        <th>
		          <input type="checkbox" id="ckeckAll" />
		        </th>
		        <th>직위번호</th>
		        <th>직위명</th>
		        <th>직원수</th>
		        <th>비고</th>
		      </thead>
		      <tbody>
		      </tbody>
		        <tr>
		          <td colspan="5">설정된 직위가 없습니다.</td>
		        </tr>
		    </table>
		  </fieldset>
        </div>
      </div>
      
      <div style="float: right; margin-top: 50px; padding: 5%;">
        <button type="button" class="btn btn-primary" onclick="goInsert();">등록</button>
      	<button type="button" class="btn btn-secondary" onclick="goDelete();">삭제</button>
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
   	         	<p>- 직위 순서의 숫자가 낮을수록 상위 직위로 처리됩니다.<br/>
   	         	- 직위는 직원 수가 0명이고 상태가 미사용중 일 경우에만 삭제처리가 가능합니다.<br/>
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

