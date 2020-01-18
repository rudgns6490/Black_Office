<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String ctxPath = request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/resources/css/login.css">

<script type="text/javascript">
	function goLogin(){
		
		var id = $("#id").val(); 
		 var passwd = $("#passwd").val(); 
		
		 if(id.trim()=="") {
		 	 alert("아이디를 입력하세요!!");
			 $("#id").val(""); 
			 $("#id").focus();
			 return;
		 }
		
		 if(passwd.trim()=="") {
			 alert("비밀번호를 입력하세요!!");
			 $("#passwd").val(""); 
			 $("#passwd").focus();
			 return;
		 }
		
		var frm = document.login;
		
		frm.action = "<%= ctxPath%>/loginEnd.action";
		frm.method = "POST";
		frm.submit();
	}
</script>

<div id="content-wrapper" style="padding: 0px;" > 
	<div class="container-fluid text-center">

		<!-- 메인 본문 내용 소스 작업은 여기서 수정 2020/01/03 kkh -->
		<div class="row content" style="padding: 20px; background-color: #ccccc;">
	        <div class="col-sm-3" style="padding: 20px;">
	        	
	           	<div class="maincontainer3" >
<!-- 	           		<div style="margin: 0 auto;" align="center"> -->
<%-- 						<img style="width: 30px; height:30px;" src="<%= ctxPath %>/resources/images/time.PNG"> --%>
<!-- 						&nbsp;현재시각 :&nbsp; -->
<!-- 						<div id="clock" style="display:inline;"></div> -->
<!-- 					</div> -->
	        	</div>
            
           		<div class="maincontainer3" style="margin-top: 50px;">
           		</div>
         
         	</div>
         
         	<div class="col-sm-6" style="padding: 20px;">
         	
	           	<div class="maincontainer2" style="text-align: left; ">
	        		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	           		<div class="inbl">
	           		</div>
	           		<br>
	           	</div>
           		
	           	<div class="col-sm-12" style="height: 400px; margin: 50px 0 300px 0;">
	           		
		           		<div class="row content">
		           			
		           			<!-- login content -->
		           			<form name="login">
			           			<div class="col-sm-12 dvideLine maincontainer" style="height: 400px; padding: 0 20px 0 0; text-align: center;">
			           				<div id="location1" class="contentMenu"><span style="font-size: xx-large;">Login</span></div>
			           				<a href="http://localhost:9090/controller/login.action"><h1>Black Office</h1></a>
			           				<hr style="width: 650px;">
			           				
			           				<br>
			           				
			           				<div class="writelogin" style="width: 400px; text-align: center;">
			           					<label for="id"><img src="<%= ctxPath %>/resources/images/myinforpicture.PNG"></label>
			           					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
			           					<input type="text" name="id" id="id" class="myinput" size="20" maxlength="20"
										required placeholder="아이디" autocomplete="off">
			           				
			           				</div>
			           				
			           				<br>
			           				
			           				<div class="writelogin" style="width: 400px; text-align: center;">
			           					<label for="passwd"><img style="width: 70px; height: 70px; position: relative; top: 0px; left: 5px;" src="<%= ctxPath %>/resources/images/password.PNG"></label> 
			           					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			           					<input type="password" id="passwd" class="myinput" size="20" maxlength="20"
										required="required" placeholder="비밀번호">
		           					</div>
			           				
			           				<div id="idsavecheckbox">
				           				<input type="checkbox" name="hobby" id="hobby1" value="1">
			           					<label for="hobby1" class="hobby">&nbsp;아이디 저장</label>
			           				</div>
			           				
			           				<button class="buttons bluebutton" onclick="goLogin()">로그인</button>
			           				
			           	 		</div>
		           	 		</form>
		           		</div>
	           		
	           	</div>
         	</div>
         
         	<div class="col-sm-3" style="padding: 20px;">
         	
	           	<div class="maincontainer3" style="height: 400px;">
	           	</div>
	           	<div class="maincontainer3" style="height: 400px; margin-top: 50px;">
	           		<div class="row-sm-3">
	           		</div>
	           	</div>
	           	
     		</div>
      	</div>
	</div>
	<!-- /.container-fluid -->
</div>
<!-- /.content-wrapper -->



<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top"> <i
	class="fas fa-angle-up"></i>
</a>

<!-- Logout Modal-->
<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
				<button class="close" type="button" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
			</div>
			<div class="modal-body">Select "Logout" below if you are ready
				to end your current session.</div>
			<div class="modal-footer">
				<button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
				<a class="btn btn-primary" href="login.html">Logout</a>
			</div>
		</div>
	</div>
</div>

