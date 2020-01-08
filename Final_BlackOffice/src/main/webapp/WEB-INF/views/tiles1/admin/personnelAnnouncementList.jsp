<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String ctxPath = request.getContextPath();
%>
    
<link href="<%= ctxPath %>/resources/css/admin/personnelAnnouncementList.css" rel="stylesheet">

  <div id="content-wrapper" style="padding-top: 0;">
    <div class="container-fluid text-center">
      
      <!-- 메인 본문 내용 소스 작업은 여기서 수정 2020/01/03 kkh -->
      <div class="row content">
        <div class="col-sm-12" style="padding: 5%;">
        
	      <h2 style="text-align: left;">인사이동내역</h2>
	    
	      <!-- Nav tabs -->
 		  <ul class="nav nav-tabs">
   		    <li class="nav-item">
     		  <a class="nav-link active" data-toggle="tab" href="#join">입사</a>
   		    </li>
   		    <li class="nav-item">
     		  <a class="nav-link" data-toggle="tab" href="#retirement">퇴사</a>
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
  		  
  		    <div id="join" class="container tab-pane active col-sm-12" style="margin-top: 30px;">
      		  <form name="registerSawon" method="post">
		        <table class="table table-bordered" style="text-align: left;">
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
			  
			  <table class="table table-hover">
      	    	<thead style="background-color: navy; color: #fff;">
      	      	  <th>사번</th>
      	      	  <th>성명</th>
      	      	  <th>부서</th>
      	      	  <th>직위</th>
      	          <th>상태</th>
      	    	</thead>
      	        <tbody>
      	          <tr>
      	        	<td colspan="5">입력된 정보가 없습니다.</td>
      	      	  </tr>
      	    	</tbody>
      	  	  </table>
    		</div>
  		  
    		<div id="retirement" class="container tab-pane fade col-sm-12" style="margin-top: 30px;">
      		  <form name="registerSawon" method="post">
		        <table class="table table-bordered" style="text-align: left;">
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
			  
			  <table class="table table-hover">
      	    	<thead style="background-color: navy; color: #fff;">
      	      	  <th>사번</th>
      	      	  <th>성명</th>
      	      	  <th>부서</th>
      	      	  <th>직위</th>
      	          <th>상태</th>
      	    	</thead>
      	        <tbody>
      	          <tr>
      	        	<td colspan="5">입력된 정보가 없습니다.</td>
      	      	  </tr>
      	    	</tbody>
      	  	  </table>
    		</div>
    		
    		<div id="departmentmove" class="container tab-pane fade col-sm-12" style="margin-top: 30px;">
			  <form name="registerSawon" method="post">
		        <table class="table table-bordered" style="text-align: left;">
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
			  
			  <table class="table table-hover">
      	    	<thead style="background-color: navy; color: #fff;">
      	      	  <th>사번</th>
      	      	  <th>성명</th>
      	      	  <th>부서</th>
      	      	  <th>직위</th>
      	          <th>상태</th>
      	    	</thead>
      	        <tbody>
      	          <tr>
      	        	<td colspan="5">입력된 정보가 없습니다.</td>
      	      	  </tr>
      	    	</tbody>
      	  	  </table>
    		</div>
    		
    		<div id="updateposition" class="container tab-pane fade col-sm-12" style="margin-top: 30px;">
			  <form name="registerSawon" method="post">
		        <table class="table table-bordered" style="text-align: left;">
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
			  
			  <table class="table table-hover">
      	    	<thead style="background-color: navy; color: #fff;">
      	      	  <th>사번</th>
      	      	  <th>성명</th>
      	      	  <th>부서</th>
      	      	  <th>직위</th>
      	          <th>상태</th>
      	    	</thead>
      	        <tbody>
      	          <tr>
      	        	<td colspan="5">입력된 정보가 없습니다.</td>
      	      	  </tr>
      	    	</tbody>
      	  	  </table>
    		</div>
    		
    		<div id="leaveofAbsence" class="container tab-pane fade col-sm-12" style="margin-top: 30px;">
			  <form name="registerSawon" method="post">
		        <table class="table table-bordered" style="text-align: left;">
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
			  
			  <table class="table table-hover">
      	    	<thead style="background-color: navy; color: #fff;">
      	      	  <th>사번</th>
      	      	  <th>성명</th>
      	      	  <th>부서</th>
      	      	  <th>직위</th>
      	          <th>상태</th>
      	    	</thead>
      	        <tbody>
      	          <tr>
      	        	<td colspan="5">입력된 정보가 없습니다.</td>
      	      	  </tr>
      	    	</tbody>
      	  	  </table>
    		</div>
    		
    		<div id="reinstate" class="container tab-pane fade col-sm-12" style="margin-top: 30px;">
			  <form name="registerSawon" method="post">
		        <table class="table table-bordered" style="text-align: left;">
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
			  
			  <table class="table table-hover">
      	    	<thead style="background-color: navy; color: #fff;">
      	      	  <th>사번</th>
      	      	  <th>성명</th>
      	      	  <th>부서</th>
      	      	  <th>직위</th>
      	          <th>상태</th>
      	    	</thead>
      	        <tbody>
      	          <tr>
      	        	<td colspan="5">입력된 정보가 없습니다.</td>
      	      	  </tr>
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

