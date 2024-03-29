<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String ctxPath = request.getContextPath(); %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>지출 결의서</title>

<style type="text/css">
	
	body{ background-color: #f5f5f0; }
	
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
   	.Td1 { width: 80%; background-color: #fff;} 
	.Td2 { background-color: #e0ebeb; text-align: center; font-size: 15pt; color: #333333; }
	
	/*----------------------------------------------------------------------------------------------  */
	
	.headertable th,td{
		border: solid 1px #639c9c;
		border-collapse: collapse;  
	} 
	
	.headertable th { background-color: #e0ebeb;}	
	.hdth { width: 100px; text-align: center; font-size: 17pt; font-weight: normal;}
	.headertable {float: right; margin-top:20px;}
	
	#ptLineAdd {  clear: both; float: right; margin-top:20px; width:150px; height: 35px; font-size: 15pt;}
	
	.titleLine { clear: both; border: solid 0px #639c9c; height: 35px; }  /* 야매로 줄바꿈 해주기위한것 */
	.title2, .title3 { clear: both; border: solid 1px #639c9c; border-bottom: none;  width: 100%;}
	.title2 th,td { border: solid 1px #639c9c; border-collapse: collapse; height: 40px; }
	.title2Td { width: 80%; } 
	.title2Td1 { background-color: #e0ebeb; text-align: center; font-size: 15pt; color: #333333; }
	.iframeDiv { width: 100%; height: 500px; } 
	
	/*----------------------------------------------------------------------------------------------  */
	
	#tableRadio, #tableRadio1, .radioSpan { position: relative; top: 4.5px; } 
	
	
</style>

<script type="text/javascript">

	$(document).ready(function(){
		
		$(".title").focus();
		
		var radioVal ="${radioVal}";
		for(var i=1; i<4; i++){
			var useRadio = $("input:radio[id=use"+i+"]").val();
			if(radioVal == useRadio) {
				$("input:radio[id=use"+i+"]").prop("checked",true);
			}
		}
		
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
			<h4>결재문서 작성</h4><br/>

		<div class="row" align="center">
	    	
	    	<!-- ---------------------------------------------------------------------------------------------------------------------------------------------- -->
	    	
	    	<div class="col-md-12 ">
	    		<form name="radioChoice">
	    		<table class="titleTable">
		 			<tbody>
		 				<tr>
		 					<td class="Td2">수당/지출문서</td>
		 					<td class="Td1">&nbsp;&nbsp;
		 						<input type="radio" name="radio" value="1" style="width: 15px; height: 15px; position: relative; top: 4.5px;" id="use1"/>&nbsp;
		 						<label for="use1" style="font-size: 13pt; color:#4c4c4c; position: relative; top: 3.0px;">지출결의서</label>&nbsp;&nbsp;


		 						<input type="radio" name="radio" value="2" style="width: 15px; height: 15px; position: relative; top: 4.5px;" id="use2"/>&nbsp;
		 						<label for="use2" style="font-size: 13pt; color:#4c4c4c; position: relative; top: 3.0px;">출장신청서</label>&nbsp;&nbsp;
		 						
		 						<input type="radio" name="radio" value="3" style="width: 15px; height: 15px; position: relative; top: 4.5px;" id="use3"/>&nbsp;
		 						<label for="use3" style="font-size: 13pt; color:#4c4c4c; position: relative; top: 3.0px;">출장복명서</label>
		 						
		 						<input type="hidden" name="radioVal" />
		 					</td>
		 				</tr>
		 			</tbody>
		 		</table>
	    		</form>
	    	</div>
	    	<br/><br/><br/>
	    	<!-- ---------------------------------------------------------------------------------------------------------------------------------------------- -->
				    
	    	<div class="col-md-12 " style="background-color: #fff;">
	    		<h2 style="border-bottom: solid 1px blue; color:#3399ff; padding: 18px; 0px;">지출결의서</h2>
	    		
	    		<table class="headertable">
	    			
	    				<tr>
	    					<th rowspan="2" class="hdth">결재</th> 
	    					<th class="hdth">작성자</th>
	    					<th class="hdth"></th>
	    					<th class="hdth"></th>
	    					<th class="hdth"></th>
	    				</tr>	
	    			
	    				<tr>
	    					<td class="hdth"></td> 
	    					<td class="hdth"></td> 
	    					<td class="hdth"></td>
	    					<td class="hdth"></td>
	    				</tr>
	    			
	    		</table>
	    		
	    		
	    		<div id="ptLineAdd">
	    			<button type="button" style="color: #333333; border-radius: 5px;">결재라인 추가</button>
	    		</div>
	    		
	    		<div class="row titleLine"> <!-- 라인을 띄우기위해 야매로 해온것이다. -->
			  		<div class="col-sm-4" style=""></div>
	  		  		<div class="col-sm-8" style=""></div>	
		 		</div>
		 		
		 		<table class="title2">
		 			<tbody>
		 				<tr>
		 					<td class="title2Td1">제출일자</td>
		 					<td class="title2Td"></td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">제출자</td>
		 					<td class="title2Td"></td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">소속</td>
		 					<td class="title2Td"></td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">지출 선택</td>
		 					<td class="title2Td">
		 						&nbsp;<input type="radio" name="tableRadio" id="tableRadio" />&nbsp;
		 						<span><label for="tableRadio" class="radioSpan">개인지출 경비</label></span>&nbsp;&nbsp;
		 						
		 						<input type="radio" name="tableRadio" id="tableRadio1" />&nbsp;
		 						<span><label for="tableRadio1" class="radioSpan">법인지출 경비</label></span>
		 					</td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">지출일자</td>
		 					<td class="title2Td">
		 						&nbsp;<input type="date" />
		 					</td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">제목</td>
		 					<td class="title2Td">
		 						&nbsp;<input type="text" name="title" class="title" style="width: 99%;" />
		 					</td>
		 				</tr>
		 			 	
		 				<tr>
		 					<td colspan="2">
		 						<div >
	    							<iframe class="iframeDiv" ></iframe>
	    						</div>
	    					</td>	
		 				</tr> 
		 			</tbody>
		 		</table>
		 		<br/>
		 		
		 		<table class="title3">
		 			<tbody>
		 				<tr>
		 					<td class="title2Td1">부서공유</td>
		 					<td class="title2Td">&nbsp;&nbsp;
		 						<input type="checkbox" name="checkbox" style="width: 20px; height: 20px; position: relative; top: 6.5px;" id="use"/>&nbsp;
		 						<label for="use" style="font-size: 13pt; color:#4c4c4c; position: relative; top: 3.0px;">사용</label>&nbsp;
		 						<span style="color: red; position: relative; top: 1.5px;">[선택 시 문서가 작성자의 소속 부서원들에게 공유되며, 해당문서는 부서결재함에서 확인이 가능합니다.]</span>
		 					</td>
		 				</tr>
		 			</tbody>
		 		</table>
		 		
		 		
	    	</div>	
	    	
		</div> 
			
		</div>	<!-- 아래여백을 주기위함  -->
			<br/><br/><br/>
		<div>
		</div>
	</div>
</body>
</html>











