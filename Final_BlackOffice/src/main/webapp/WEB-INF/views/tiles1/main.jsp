<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String ctxPath = request.getContextPath();
%>

<style type="text/css">

/* Tag */

a {
	text-decoration: none !important; 
}

hr {
	border: solid 1px #ccc;
}

/* Class */

.inbl {
	display: inline-block;  
}

.dvideLine {
/*  	border: solid 1px black; */
 	height: 400px;
}

.contentMenu{
	data-text-content="true";
	width:100px;
	color: rgb(255, 255, 255);
	background-color: rgb(46, 204, 113);
	padding: 15px;
	border-radius: 4px;
	text-align: center;
	font-size: 16px; line-height: 0.8em;
	data-min-width="40";
	data-min-height="40";
	position: relative; top: -10px; left: -10px;
} 
 
.buttons {
  background-color: #4CAF50; /* Green */
  border: none;
  color: white;
  padding: 6px 16px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  outline: none;
  cursor: pointer;
  margin-top: 12px;  
}

.bluebutton {
	background-color: #008CBA;
	border-radius: 6px;;
}

.maincontainer { /* 메인 각박스 */
	background-color: #fff;
	height: 400px;
	/* border: solid 1px gray; */
	border-radius: 16px;
}

/* ID */
#content-wrapper {
	background-color: #f2f2f2;
}


</style>

<script type="text/javascript">

	$(document).ready(function() {
		
		// 시계함수 불러오기
		loopshowNowTime();
		
	}); // end of ready(); ---------------------------------
	
	// 시계만들기
	function showNowTime() {
		
		var now = new Date();
	
		var strNow = now.getFullYear() + "-" + (now.getMonth() + 1) + "-" + now.getDate();
		
		var hour = "";
	    if(now.getHours() >= 12) {
	    	hour = "오후 " + (now.getHours() - 12); 
	    } 
	    else {
	    	hour = "오전 " + now.getHours();
	    }
		
		var minute = "";
		if(now.getMinutes() < 10) {
			minute = "0"+now.getMinutes();
		} else {
			minute = now.getMinutes();
		}
		
		var second = "";
		if(now.getSeconds() < 10) {
			second = "0"+now.getSeconds();
		} else {
			second = now.getSeconds();
		}
		
		strNow += " "+hour + ":" + minute + ":" + second;
		
		$("#clock").html("<span style='color:skyblue; font-weight:bold;'>"+strNow+"</span>");
	
	}// end of function showNowTime() -----------------------------

	// 시계함수
	function loopshowNowTime() {
		showNowTime();
		
		var timejugi = 1000;   // 시간을 1초 마다 자동 갱신하려고.
		
		setTimeout(function() {
						loopshowNowTime();	
					}, timejugi);
		
	}// end of loopshowNowTime() --------------------------
		
</script>


<div id="content-wrapper" style="padding: 0px;" > 
	<div class="container-fluid text-center">

		<!-- 메인 본문 내용 소스 작업은 여기서 수정 2020/01/03 kkh -->
		<div class="row content" style="padding: 20px; background-color: #ccccc;">
	        <div class="col-sm-3" style="padding: 20px;">
	        	
	           	<div class="maincontainer" >
	           	
	           		<div id="location1" class="contentMenu"><span style="font-size: small;">내정보</span></div>
	           		<div>
	           			<a href="">
		           			<img src="<%= ctxPath %>/resources/images/myinforpicture.PNG">
		           		</a>
		           		<br>
		           		김경훈님 안녕하세요.<br>
		           		접속IP:192.168.50.36
		           	</div>
		           	
		           	<hr> <!-- 라인만들기 -->
           			<div style="padding:0px; margin:0px;"><a href="http://localhost:9090/controller/maintest.action">근태정보</a>&nbsp;&nbsp;&nbsp;<a href="http://localhost:9090/controller/maintest.action">휴가정보</a></div> 
	           		<hr> <!-- 라인만들기 -->
	           		
	           		<div style="margin: 0 auto;" align="center">
						<img style="width: 30px; height:30px;" src="<%= ctxPath %>/resources/images/time.PNG">
						&nbsp;현재시각 :&nbsp;
						<div id="clock" style="display:inline;"></div>
					</div>
					
	           		<hr> <!-- 라인만들기 -->
	           		<div style="padding: 0;">출근 : ? &nbsp;&nbsp;&nbsp; 퇴근 : ?</div> 
	           		<button class="buttons bluebutton" >출근 / 퇴근</button>
	           		
	        	</div>
            
           		<div class="maincontainer" style="margin-top: 50px;">
           			
           			<div id="location2" class="contentMenu"><span style="font-size: small;">차트</span></div>
           			<br><br>
           			<h3>이번달 결제</h3> 
           			<br><br>
           			<a href="http://localhost:9090/controller/maintest.action"><img src="<%= ctxPath %>/resources/images/chart.PNG"></a>
           			
           			
           		</div>
         
         	</div>
         
         	<div class="col-sm-6" style="padding: 20px;">
         	
	           	<div class="maincontainer" style="text-align: left; ">
	        		<div class="contentMenu inbl"><span style="font-size: small;">업무공유 / 전자결제</span></div>
	        		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	           		<div class="inbl">
		           		<a href="http://localhost:9090/controller/maintest.action"><strong>공유업무</strong></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		           		<a href="http://localhost:9090/controller/maintest.action"><strong>일일보고</strong></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		           		<a href="http://localhost:9090/controller/maintest.action"><strong>특별보고</strong></a>
	           		</div>
	           		<hr> <!-- 라인만들기 -->
	           		<br>
		           		<div style="text-align: center;" class="row content">
		           			<div class="col-sm-6" style="border-right: solid 1px black;">
		           				<br>
	           					<a href="http://localhost:9090/controller/maintest.action">
	           						<img style="width: 280px; height: 220px;" src="<%= ctxPath %>/resources/images/workimage1.PNG">
		           				</a>
		           	 		</div>
		           	 		
		           	 		<div class="col-sm-6">	
		           				<br>
		           				<a href="http://localhost:9090/controller/maintest.action">
	           						<img style="width: 280px; height: 220px;" src="<%= ctxPath %>/resources/images/workimage2.PNG">
	           					</a>	
		           			</div>
		           		</div>
	           	</div>
           		
	           	<div class="col-sm-12" style="height: 400px; margin: 50px 0 300px 0;">
	           		
		           		<div class="row content">
		           			<div class="col-sm-6 dvideLine maincontainer" style="height: 700px; padding: 0 20px 0 0;">
		           				<div class="contentMenu"><span style="font-size: small;">채팅</span></div>
		           				<br><br>
		           				<img style="height: 350px;"src="<%= ctxPath %>/resources/images/chat.PNG">
		           				
		           	 		</div>
		           	 		
		           	 		<div class="col-sm-5 dvideLine maincontainer" style="margin: 0 0 0 57px; padding: 0 20px 0 0;">	
		           				<div class="contentMenu"><span style="font-size: small;">공지사항</span></div>
		           				<hr style="width: 292px;">
		           				<br><br>
			           				<div>
				           				<ul>
											<li><a href="http://localhost:9090/controller/maintest.action">공지사항1</a></li>
											<li><a href="http://localhost:9090/controller/maintest.action">공지사항2</a></li>
											<li><a href="http://localhost:9090/controller/maintest.action">공지사항3</a></li>
											<li><a href="http://localhost:9090/controller/maintest.action">공지사항4</a></li>
										</ul>
									</div>
		           			</div>	
		           		</div>
	           		
	           	</div>
         	</div>
         
         	<div class="col-sm-3" style="padding: 20px;">
         	
	           	<div class="maincontainer" style="height: 400px;">
	           		<div class="contentMenu"><span style="font-size: small;">일정</span></div>
	           		<br>
	           		<a href="http://localhost:9090/controller/maintest.action"><img style="width: 315px; height: 285px;" src="<%= ctxPath %>/resources/images/calendar.PNG"></a>
	           		<div>등록된 일정이 없습니다.</div>
	           	</div>
           
	           	<div class="maincontainer" style="height: 400px; margin-top: 50px;">
	           		<div class="row-sm-3">
	           			<div class="contentMenu"><span style="font-size: small;">근태현황<br>업무공유</span></div>
	           			
	           			근태 현황
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

