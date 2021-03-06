<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String ctxPath = request.getContextPath();
%>
    
<link href="<%= ctxPath %>/resources/css/admin/joinSawon.css" rel="stylesheet">

<script type="text/javascript">
	
	$(document).ready(function(){
		$(".userid_error").hide();
		
		$("#id").bind("keyup", function(){
			alert("아이디중복확인 버튼을 클릭하여 ID중복 검사를 하세요!!");
			$(this).val("");
		}); // end of $("#userid").bind()----------
		
		//아이디검사
		$("input#id").blur(function(){
			var regExp = /^[A-Za-z0-9]{4,}$/; // 4글자이상 가능
		    var bool = regExp.test($(this).val()); 
		    if(!bool) {
		    	$("span.userid_error").css('display','');
		    	$(":input").attr("disabled", true);
		        $(this).attr("disabled", false);
		        $(this).val("");
		        $(this).focus();
		    }
		    else {
		    	$("span.userid_error").css('display','none');
		    	$(":input").attr("disabled", false);
		    	$("input#passwd").focus();
		    }
		});// end of $("input#userid").blur()--------
		
		/// **** ID중복확인하기 위한 팝업창 띄우기 ****///
		$("#idcheck").click(function() {
			
			// 팝업창 띄우기
			var url = "idDuplicateCheck.action";
			window.open(url, "idcheck",
					    "left=500px, top=100px, width=300px, height=270px");
			// 기본적으로 아무런 조건없이 그냥 어떤 창을 띄우면 method 는 GET 방식으로 움직인다. 
			
		});// end of $("#idcheck").click()------------
		
		// 비밀번호 확인하기
		$("input#passwdCheck").blur(function(){
	    	if( $(this).val() != $("input#passwd").val() ) {
	    		$("span.passwdCheck_error").html("비밀번호를 다시 확인하세요!").css("color", 'red');
	    		$("span.passwdCheck_error").css('display','');
	    		
	    		$("input#passwd").val("");
	    		$(this).val("");
	    		
	    		$(":input").attr("disabled",true);
	    		$("input#passwd").attr("disabled",false);
	    		$(this).attr("disabled",false);
	    		
	    		$("input#passwd").focus();
	    	}
	    	else {
	    		$("span.passwdCheck_error").empty();
	    		$(":input").attr("disabled",false);
	    		
	    		$("input#name").focus();
	    	}
	    });
		
	    // 주민번호1 확인하기
	    $("span.jubunCheck1").hide();
	    $("span.jubunCheck2").hide();
	    $("#jubun1").blur(function(){
		    var jubun = $("input#jubun1").val().trim();
			// 오직 숫자만 6자리를 입력해야 한다. == 
			
			var regExp = /^[0-9]{6}$/;
			var bool = regExp.test(jubun);
			
			if(!bool) {
	    		$(":input").attr("disabled",true);
	    		$("input#jubun1").attr("disabled",false);
	    		$(this).attr("disabled",false);
				$("span.jubunCheck1").show();
				$("input#jubun1").val("");
				$("input#jubun1").focus();
				return false;  
				// 여기서 종료함. 즉, 서브밋을 하지 말라는 뜻이다.
			}
			else {
				$("span.jubunCheck1").hide();
	    		$(":input").attr("disabled",false);
	    		
	    		$("input#jubun2").focus();
	    	}
	    });
	    
	 	// 주민번호2 확인하기
	    $("#jubun2").blur(function(){
		    var jubun = $("input#jubun2").val().trim();
			// 첫글자는 1234 중하나이고 총 7 글자를 입력하여야 합니다. == 
			
			var regExp = /^[1234][0-9]{6}$/;
			var bool = regExp.test(jubun);
			
			if(!bool) {
	    		
	    		$(":input").attr("disabled",true);
	    		$("input#jubun2").attr("disabled",false);
	    		$(this).attr("disabled",false);
	    		
				$("span.jubunCheck2").show();
				$("input#jubun2").val("");
				$("input#jubun2").focus();
				return false;  
				// 여기서 종료함. 즉, 서브밋을 하지 말라는 뜻이다.
			}
			else {
				$("span.jubunCheck2").hide();
	    		$(":input").attr("disabled",false);
	    		
	    		$("input#email").focus();
	    	}
	    });
	    
		
		// 이메일
		$("#emailCheckResult").hide();
		$("#email").blur(function(){
			var email = $(this).val();
			var regExp_EMAIL = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;  
			var bool = regExp_EMAIL.test(email);
			if(!bool) {
				$("#emailCheckResult").show();
				$(":input").attr("disabled",true).addClass("bgcol");
				$(this).attr("disabled",false).removeClass("bgcol"); 
				$(this).focus();
			}
			else {	
				$("#emailCheckResult").hide();
				$(":input").attr("disabled",false).removeClass("bgcol");
				
				$("input#emailpw").focus();
			}
		});
		
		
		// 이메일 비밀번호 확인하기
		$("input#emailpwCheck").blur(function(){
	    	if( $(this).val() != $("input#emailpw").val() ) {
	    		$("span.emailpwCheck_error").html("이메일 비밀번호를 다시 확인하세요!").css("color", 'red');
	    		$("span.emailpwCheck_error").css('display','');
	    		
	    		$("input#emailpw").val("");
	    		$(this).val("");
	    		
	    		$(":input").attr("disabled",true);
	    		$("input#emailpw").attr("disabled",false);
	    		$(this).attr("disabled",false);
	    		
	    		$("input#emailpw").focus();
	    	}
	    	else {
	    		$("span.emailpwCheck_error").empty();
	    		$(":input").attr("disabled",false);
	    		
	    		$("input#phone").focus();
	    	}
	    });
		
	 	// 전화번호
		$(".phoneCheck_error").hide();
		$("#phone").blur(function(){
			var phone = $("input#phone").val().trim();
			//  == 
			var regExp = /^01([0|1|6|7|8|9]?)([0-9]{3,4})([0-9]{4})$/;
			var bool = regExp.test(phone);
			
			if(!bool) {
	    		
	    		$(":input").attr("disabled",true);
	    		$("input#phone").attr("disabled",false);
	    		$(this).attr("disabled",false);
	    		
				$("span.phoneCheck_error").show();
				$("input#jubun2").val("");
				$("input#jubun2").focus();
				return false;  
				// 여기서 종료함. 즉, 서브밋을 하지 말라는 뜻이다.
			}
			else {
				$("span.phoneCheck_error").hide();
	    		$(":input").attr("disabled",false);
	    	}
		});
	    
		// 등록하기
		$("#register").click(function(){
			goRegister();
		});
		
		// 이메일 비밀번호에서 
		$("#phone").keydown(function(event){
  			if(event.keyCode == 13) { // 엔터를 했을 경우
  				goRegister();
  			}
    	}); // end of $("#pwd").keydown();-----------------------
		
	}); // end of $(document).ready(function(){
	
	function goRegister() {
   		 
		var id = $("#id").val(); 
		var passwd = $("#passwd").val();
		var name = $("#name").val(); 
		var jubun1 = $("#jubun1").val();
		var jubun2 = $("#jubun2").val();
		var email = $("#email").val();
		var emailpw = $("#emailpw").val();
		var phone = $("#phone").val();
		
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
		
		if(name.trim()=="") {
			alert("성명을 입력하세요!!");
			$("#name").val(""); 
			$("#name").focus();
			return;
		}
		
		// 주민번호 1
		if(jubun1.trim()=="") {
			alert("주민번호를 입력하세요!!");
			$("#jubun1").val(""); 
			$("#jubun1").focus();
			return;
		}
		
		// 주민번호 2
		if(jubun2.trim()=="") {
			alert("주민번호를 입력하세요!!");
			$("#jubun2").val(""); 
			$("#jubun2").focus();
			return;
		}
		
		// 이메일
		if(email.trim()=="") {
			alert("이메일을 입력하세요!!");
			$("#email").val(""); 
			$("#email").focus();
			return;
		}
		
		// 이메일 비밀번호
		if(emailpw.trim()=="") {
			alert("이메일 비밀번호를 입력하세요!!");
			$("#emailpw").val(""); 
			$("#emailpw").focus();
			return;
		}
		
		// 전화번호
		if(phone.trim()=="") {
			alert("전화번호를 입력하세요!!");
			$("#phone").val(""); 
			$("#phone").focus();
			return;
		}
		
		var frm = document.registerSawon
		frm.method = "POST";
		frm.action = "<%= ctxPath%>/register.action";
		frm.submit();
	}

</script>
<div id="wrapper">
  <div id="content-wrapper" style="padding-top: 0;">
    <div class="container-fluid text-center">
      
      <!-- 메인 본문 내용 소스 작업은 여기서 수정 2020/01/03 kkh -->
      <div class="row content">
        <div class="col-sm-12" style="padding: 5%;">
        <form name="registerSawon" id="registerSawon" enctype="multipart/form-data">
		  <fieldset form="registerSawon">
		    <legend style="font-size: 24pt; text-align: left;">입사처리</legend>
		    
		      <table class="table table-bordered">
		        <tr>
				<th style="background-color: #e0ebeb;">
					<label>사진 첨부</label>
				</th>
				  <td>
					<input type="file" name="attach"  />
				  </td>
				</tr>
		      
		      	<tr>
		      	  <th style="background-color: #e0ebeb; height: 10px;">
		      	  	<label for="id">아이디</label>
		      	  </th>
		      	  <td>
		      	    <input type="text" name="id" id="id" value="" maxlength="20" required autofocus autocomplete="off" style="width: 200px;"/>@blackoffice.com &nbsp;
		      	    <!-- 아이디중복체크 -->
			    	<img id="idcheck" style="display: inline-block; cursor:pointer;" src="<%=ctxPath%>/resources/images/b_id_check.gif" style="vertical-align: middle;" />
		      	    <span id="error" class="userid_error" style="color: red; font-size: 12pt; font-weight: bold;">4글자 이상 아이디를 입력하세요!</span><br><br>
		      	  </td>
		      	</tr>
		      	
		      	
		      	<tr>
		      	  <th style="background-color: #e0ebeb;">
		      	  	<label for="passwd">비밀번호</label>
		      	  </th>
		      	  <td>
		      	    <input type="password" name="passwd" id="passwd" value="" maxlength="20" required style="width: 200px;"/>
		      	  </td>
		      	</tr>
		      	
		      	
	      		<tr>
		      	  <th style="background-color: #e0ebeb;">
		      	  	<label for="passwdCheck">비밀번호 확인</label>
		      	  </th>
		      	  <td>
		      	    <input type="password" name="passwdCheck" id="passwdCheck" value="" maxlength="20" required style="width: 200px;"/>
		      	    <span class="error passwdCheck_error"></span>
		      	  </td>
		      	</tr>
		      	
		      	
		      	<tr>
		      	  <th style="background-color: #e0ebeb;">
		      	  	<label for="name">성명</label>
		      	  </th>
		      	  <td>
		      	    <input type="text" name="name" id="name" value="" maxlength="20" required autofocus autocomplete="off" style="width: 200px;"/>
		      	  </td>
		      	</tr>
		      	
		      	
		      	<tr>
		      	  <th style="background-color: #e0ebeb;">
		      	   	<label for="jubun1">주민번호</label>
		      	  </th>
		      	  <td>
		      	    <input type="text" name="jubun1" id="jubun1" value="" maxlength="6" required autofocus autocomplete="off" placeholder="6자리 숫자" style="width: 93px;"/> - 
		      	    <input type="text" name="jubun2" id="jubun2" value="" maxlength="7" required autofocus autocomplete="off" placeholder="7자리 숫자" style="width: 93px;"/>
		      	    <%-- ====  jubunCheck ==== --%>
		      	    <span class="error jubunCheck1" style="color: red">6자리 - 7자리 숫자만 입력하세요.</span>
		      	    <span class="error jubunCheck2" style="color: red">첫글자는 1234 중하나이며 총 7 자를 입력하여야 합니다</span>
		      	    <%-- ====  jubunCheck ==== --%>
		      	  </td>
		      	</tr>
		      	
		      	
		      	<tr>
		      	  <th style="background-color: #e0ebeb;">
		      	  	<label for="email">이메일</label>
		      	  </th>
		      	  <td>
		      	    <input type="text" name="email" id="email" value="" maxlength="30" required autofocus autocomplete="off" placeholder="형식 - ***@***.***" style="width: 200px;"/>
		      	    
		      	    <%-- ====  email check ==== --%>
				    <span id="emailCheckResult" style="color: red;">이메일 형식에 맞지 않습니다.</span>
				    <%-- ====  email check ==== --%>
				    
		      	  </td>
		      	</tr>
		      	
		      	
		      	<tr>
		      	  <th style="background-color: #e0ebeb;">
		      	  	<label for="emailpw">이메일 비밀번호</label>
		      	  </th>
		      	  <td>
		      	    <input type="password" name="emailpw" id="emailpw" value="" maxlength="20" required autofocus autocomplete="off" style="width: 200px;"/>
		      	  </td>
		      	</tr>
		      	
		      	
		      	<tr>
		      	  <th style="background-color: #e0ebeb;">
		      	  	<label for="emailpwCheck">이메일 비밀번호 확인</label>
		      	  </th>
		      	  <td>
		      	    <input type="password" name="emailpwCheck" id="emailpwCheck" value="" maxlength="20" required style="width: 200px;"/>
		      	    
		      	    <%-- ====  emailpwCheck check ==== --%>
		      	    <span class="error emailpwCheck_error"></span>
		      	    <%-- ====  emailpwCheck check ==== --%>
		      	    
		      	  </td>
		      	</tr>
		      	
		      	
		      	<tr>
		      	  <th style="background-color: #e0ebeb;">
		      	  	<label for="phone">전화번호</label>
		      	  </th>
		      	  <td>
		      	    <input type="text" name="phone" id="phone" value="" maxlength="11" required placeholder="-는 제외하고 입력" style="width: 200px;"/>
		      	    
		      	    <%-- ====  emailpwCheck check ==== --%>
		      	    <span class="error phoneCheck_error" style="color: red; font-size: 12px;">01([0|1|6|7|8|9]) 시작하는 11자리 숫자로 -는 제외하고 입력하세요</span>
		      	    <%-- ====  emailpwCheck check ==== --%>
		      	    
		      	  </td>
		      	</tr>
		      	
		      	
		      	<tr>
		      	  <th style="background-color: #e0ebeb;">
		      	  	<label>부서</label>
		      	  </th>
		      	  <td>
		      	    <select name="fk_departmentno" id="fk_departmentno" class="form-control" style="width: 30%;">
		      	      <option>선택</option>
	  				  <option value="1">인사팀</option>
	  				  <option value="2">마케팅팀</option>
	  				  <option value="3">개발1팀</option>
	  				  <option value="4">개발2팀</option>
	  				  <option value="5">영업팀</option>
		      	      
					</select>
		      	  </td>
		      	</tr>
		      	<tr>
		      	  <th style="background-color: #e0ebeb;">
		      	  	<label>직위</label>
		      	  </th>
		      	  
		      	  <td>
		      	    <select name="fk_positionno" id="fk_positionno" class="form-control" style="width: 30%;">
		      	      <option>선택</option>
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
		      </table>
		      
		      <div style="float: right; argin-top: 10px;">
		        <button type="button" id="register" class="btn btn-primary" onclick="goRegister();">등록</button>
		      	<button type="reset" class="btn btn-secondary" onclick="goCancle();">취소</button>
		      </div>
		 </fieldset> 
		</form>  
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


