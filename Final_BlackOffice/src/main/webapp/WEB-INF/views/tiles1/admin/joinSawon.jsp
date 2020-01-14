<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String ctxPath = request.getContextPath();
%>
    
<link href="<%= ctxPath %>/resources/css/admin/joinSawon.css" rel="stylesheet">

  <div id="content-wrapper" style="padding-top: 0;">
    <div class="container-fluid text-center">
      
      <!-- 메인 본문 내용 소스 작업은 여기서 수정 2020/01/03 kkh -->
      <div class="row content">
        <div class="col-sm-12" style="padding: 5%;">
		  <fieldset>
		    <legend>입사처리</legend>
		    
		    <form name="registerSawon" method="post">
		      <table class="table table-bordered">
		      	<tr>
		      	  <td style="background-color: #e0ebeb;">아이디</td>
		      	  <td>
		      	    <input type="text" name="email" id="email" value="" maxlength="20" required autofocus autocomplete="off" />@blackoffice.com
		      	  </td>
		      	</tr>
		      	<tr>
		      	  <td style="background-color: #e0ebeb;">비밀번호</td>
		      	  <td>
		      	    <input type="password" name="password" id="password" value="" maxlength="20" required />
		      	  </td>
		      	</tr>
		      	<tr>
		      	  <td style="background-color: #e0ebeb;">성명</td>
		      	  <td>
		      	    <input type="text" name="name" id="name" value="" maxlength="20" required autofocus autocomplete="off" />
		      	  </td>
		      	</tr>
		      	<tr>
		      	  <td style="background-color: #e0ebeb;">주민번호</td>
		      	  <td>
		      	    <input type="text" name="jubun1" id="jubun1" value="" maxlength="6" required autofocus autocomplete="off" /> - 
		      	    <input type="text" name="jubun2" id="jubun2" value="" maxlength="7" required autofocus autocomplete="off" />
		      	  </td>
		      	</tr>
		      	<tr>
		      	  <td style="background-color: #e0ebeb;">부서</td>
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
		      	  <td style="background-color: #e0ebeb;">직위</td>
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
		      </table>
		    </form>
		  
		  </fieldset>
        </div>
      </div>
      
      <div style="float: right; margin-top: 10px; padding: 5%;">
        <button type="button" class="btn btn-primary" onclick="goRegister();">등록</button>
      	<button type="button" class="btn btn-secondary" onclick="goCancle();">취소</button>
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

