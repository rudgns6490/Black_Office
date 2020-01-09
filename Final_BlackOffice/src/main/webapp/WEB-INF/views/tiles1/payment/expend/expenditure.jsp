<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String ctxPath = request.getContextPath(); %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
	
	body{ background-color: #f7f7f7; }
	
	#draftContainer {	/* 전체 div */
		width: 100%;
		height: 100%;
		margin: 0 auto;
     } 	 
	
	.dfcontainer {		/* 부트스트랩 class */
   		width: 90%;
   		height: 90%;
   		margin-top: 30px;	
   }  	
   
   	.titleTable { clear: both; border: solid 1px #639c9c;  width: 100%;}
  	.titleTd1 { width: 80%; background-color: #fff;} 
	.titleTd2 { background-color: #e0ebeb; text-align: center; font-size: 15pt; color: #333333; }
	.radioCursor:hover { cursor: pointer; }
	
	
</style>

<script type="text/javascript">


	$(document).ready(function(){
		
		$(".title").focus();
		
		$("input:radio[name=radio]").click(function(){

			$("input:radio[name=radio]").each(function(){
				var bool = $(this).is(":checked");
				if(bool) {
					var result = $(this).val();
					
					switch (result) {
					
					case "1":
						goexReport();
						break;
					
					case "2":
						goBusinessTrip();
						break;	
						
					case "3":
						goBusinessReturn();
						break;	
						 
				}// end of switch()----------------*
				}
			});
		});// end of $("input:radio[name=radio]").click(function(){})----------	
	});// end of $(document).ready(function(){})------------------------------------
	
	
	// 지출결의서 보고서
	function goexReport() {
		
		var radioVal = $("input:radio[id=use1]").val();

		var frm = document.radioChoice;
		frm.radioVal.value = radioVal;
		frm.method = "POST";
		frm.action = "<%= ctxPath%>/exReport.action";
		frm.submit();

		<%-- window.location.href = "<%= ctxPath%>/exReport.action" --%>
			
	}
	
	// 출장 신청서
	function goBusinessTrip() {
		
		var radioVal = $("input:radio[id=use2]").val();

		var frm = document.radioChoice;
		frm.radioVal.value = radioVal;
		frm.method = "POST";
		frm.action = "<%= ctxPath%>/business_trip.action";
		frm.submit();
		
		<%-- window.location.href = "<%= ctxPath%>/business_trip.action" --%>
		
	}
	
	// 출장 복명서
	function goBusinessReturn() {
		
		var radioVal = $("input:radio[id=use3]").val();

		var frm = document.radioChoice;
		frm.radioVal.value = radioVal;
		frm.method = "POST";
		frm.action = "<%= ctxPath%>/business_return.action";
		frm.submit();
		
		<%-- window.location.href = "<%= ctxPath%>/business_return.action" --%>
	}
	
</script>



</head>
<body>
	<div id="draftContainer"> 
		<div class="container-fluid dfcontainer">	
			<img src="<%= request.getContextPath() %>/resources/images/문서.png">&nbsp;<span style="font-size: 18pt; color: #595959;  position: relative; top: 4.0px;">결재문서 작성</span><br/><br/>

		<div class="row" align="center">
	    	<div class="col-md-12 ">
	    		<form name="radioChoice">
	    		<table class="titleTable">
		 			<tbody>
		 				<tr>
		 					<td class="titleTd2">수당/지출문서</td>
		 					<td class="titleTd1">&nbsp;&nbsp;
		 						<input type="radio" name="radio" value="1" style="width: 15px; height: 15px; position: relative; top: 4.5px;" class="radioCursor" id="use1"/>&nbsp;
		 						<label for="use1" style="font-size: 13pt; color:#4c4c4c; position: relative; top: 3.0px;" class="radioCursor">지출결의서</label>&nbsp;&nbsp;
		 						 
		 						<input type="radio" name="radio" value="2" style="width: 15px; height: 15px; position: relative; top: 4.5px;" class="radioCursor" id="use2"/>&nbsp;
		 						<label for="use2" style="font-size: 13pt; color:#4c4c4c; position: relative; top: 3.0px;" class="radioCursor">출장신청서</label>&nbsp;&nbsp;
		 						
		 						<input type="radio" name="radio" value="3" style="width: 15px; height: 15px; position: relative; top: 4.5px;" class="radioCursor" id="use3"/>&nbsp;
		 						<label for="use3" style="font-size: 13pt; color:#4c4c4c; position: relative; top: 3.0px;" class="radioCursor">출장복명서</label>
		 						
		 						<input type="hidden" name="radioVal" />
		 					</td>
		 				</tr>
		 			</tbody>
		 		</table>
	    		</form>
	    	</div>
		</div> 
		<br/><br/>	
			<div class="row" align="center">
			<!-- <div class="choice" style="margin-top: 50px;"> -->
				<div class="col-md-12 ">				
					<span style="font-size: 15pt;">작성하시려는 전자결재 문서를 선택하세요.</span>
				</div>
			</div>
			
		</div>

	</div>
</body>
</html>











