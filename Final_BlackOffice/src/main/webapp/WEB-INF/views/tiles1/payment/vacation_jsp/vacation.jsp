<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String ctxPath = request.getContextPath(); %>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>휴직 결재서 작성</title>

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
   	.Td1 { width: 80%; background-color: #fff;} 
	.Td2 { background-color: #e0ebeb; text-align: center; font-size: 15pt; color: #333333; }
	.radioCursor:hover { cursor: pointer; }
	/*----------------------------------------------------------------------------------------------  */
	
	.headertable th,td{
		border: solid 1px #639c9c;
		border-collapse: collapse;  
	} 
	
	.headertable th { background-color: #e0ebeb;}	
	.hdth { width: 100px; text-align: center; font-size: 17pt; font-weight: normal; color:#595959;}
	.headertable {float: right; margin-top:20px;}
	
	#ptLineAdd {  clear: both; float: right; margin-top:20px; width:150px; height: 35px; font-size: 15pt;}
	
	.titleLine { clear: both; border: solid 0px #639c9c; height: 35px; }  /* 야매로 줄바꿈 해주기위한것 */
	.title2, .title3 { clear: both; border: solid 1px #639c9c; border-bottom: none;  width: 100%;}
	.title2 th,td { border: solid 1px #639c9c; border-collapse: collapse; height: 40px; }
	.title2Td { width: 80%; } 
	.title2Td1 { background-color: #e0ebeb; text-align: center; font-size: 15pt; color: #333333; }
	.iframeDiv { width: 100%; height: 500px; }
   
   	.title {
		background: url(<%= request.getContextPath() %>/resources/images/체크이미지.png) no-repeat 3px center; 
		padding-left: 20px; 
	}
	
	.cancleReason {
		background: url(<%= request.getContextPath() %>/resources/images/체크이미지.png) no-repeat 3px center; 
		padding-left: 20px; 
	}
	
	/*--- 아래 저장버튼 -----------------------------------------------------------------------------------------  */
	.save { float: right; margin-right: 90px; }
	.saveBtn { border: solid 1px #0099cc; border-radius:3px; padding: 5px 20px; background-color: #0099cc; color: #fff; }
	.saveBtn2 {padding: 5px 20px; border: solid 1px #bfbfbf; border-radius:3px;}   
	
</style>

<script type="text/javascript">
		
	$(document).ready(function(){
		
		$(".title").focus();
		$("input:radio[id=use1]").prop("checked",true);
		
		var radioVal ="${radioVal}";
		for(var i=1; i<5; i++){
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
						goVacation();
						break;
						
					case "2":
						goBabyVacation();
						break;	
				
				}// end of switch()----------------
			}
			});	
		});// end of $("input:radio[name=radio]").click(function(){})----------	
	}); // end of $(document).ready(function(){})-------------------------------
	
	// 휴직 신청서
	function goVacation() {
		
		var radioVal = $("input:radio[id=use1]").val();
		
		var frm = document.radioChoice;
		frm.radioVal.value = radioVal;
		frm.method = "POST";
		frm.action = "<%= ctxPath%>/vacation.action";
		frm.submit();
		
		<%-- window.location.href = "<%= ctxPath%>/vacation.action" --%>
	}
	
	// 연차 신청서
	function goBabyVacation() {
		
		var radioVal = $("input:radio[id=use2]").val();
		
		var frm = document.radioChoice;
		frm.radioVal.value = radioVal;
		frm.method = "POST";
		frm.action = "<%= ctxPath%>/babyVacation.action";
		frm.submit();
		
		<%-- window.location.href = "<%= ctxPath%>/babyVacation.action" --%>
	}
	
	// 제출하기 버튼의 onclick
	function save() {
		var frm = document.submitFrm;
		frm.method = "POST";
		frm.action = "<%= ctxPath%>/maintest.action";
		frm.submit();
	}
	
	// 저장하기 버튼의 onclick
	function temporary() {
		var frm = document.submitFrm;
		frm.method = "POST";
		frm.action = "<%= ctxPath%>/maintest.action";
		frm.submit();
	}
		
</script>

</head>
<body>
	<div id="draftContainer">
		<div class="container-fluid dfcontainer">
			<img src="<%= request.getContextPath() %>/resources/images/문서.png">&nbsp;<span style="font-size: 18pt; color: #595959;  position: relative; top: 4.0px;">결재문서 작성</span><br/><br/>
			
			<div class="row" align="center">
			
			<!-- ---------------------------------------------------------------------------------------------------------------------------------------------- -->
	    	
	    	<div class="col-md-12 ">
	    		<form name="radioChoice">
	    		<table class="titleTable">
		 			<tbody>
		 				<tr>
		 					<td class="Td2">휴직 종류</td>
		 					<td class="Td1">&nbsp;&nbsp;
		 						<input type="radio" name="radio" value="1" style="width: 15px; height: 15px; position: relative; top: 4.5px;" id="use1" class="radioCursor"/>&nbsp;
		 						<label for="use1" style="font-size: 13pt; color:#4c4c4c; position: relative; top: 3.0px;" class="radioCursor">휴직 신청서</label>&nbsp;&nbsp;


		 						<input type="radio" name="radio" value="2" style="width: 15px; height: 15px; position: relative; top: 4.5px;" id="use2" class="radioCursor"/>&nbsp;
		 						<label for="use2" style="font-size: 13pt; color:#4c4c4c; position: relative; top: 3.0px;" class="radioCursor">육아휴직 신청서</label>&nbsp;&nbsp;
		 						
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
	    		<h2 style="border-bottom: solid 1px blue; color:#3399ff; padding: 18px; 0px;">휴직 신청서</h2>
	    		
	    		<form name="submitFrm">
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
		 					<td class="title2Td"><input id="" name="" readonly style="border: none;" /></td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">제출자</td>
		 					<td class="title2Td"><input id="" name="" readonly style="border: none;" /></td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">소속</td>
		 					<td class="title2Td"><input id="" name="" readonly style="border: none;" /></td>
		 				</tr>
		 				
		 				
		 				<tr>
		 					<td class="title2Td1">직급</td>
		 					<td class="title2Td">
		 						&nbsp;&nbsp;<select id="" name="" style="width: 80px;">
		 							<option value="">사원</option>
		 							<option value="">대리</option>
		 							<option value="">과장</option>
		 							<option value="">부장</option>
		 						</select>
		 					</td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">요청 기간</td>
		 					<td class="title2Td">
		 						&nbsp;&nbsp;<span>시작일</span>&nbsp;<input type="date" name="btperiod" />&nbsp;
		 						<span>~</span>
		 						&nbsp;<span>종료일</span>&nbsp;<input type="date" name="btperiod" />&nbsp;&nbsp;
		 						<span>( 총</span>&nbsp;&nbsp;<span>0</span>&nbsp;&nbsp;<span>일 )</span>
		 					</td>
		 				</tr>
		 				
		 				
		 				<tr>
		 					<td class="title2Td1">제목</td>
		 					<td class="title2Td">
		 						&nbsp;<input type="text" name="title" class="title" style="width: 99%;" autocomplete="off"/>
		 					</td>
		 				</tr>
		 				
		 				
		 				<tr>
		 					<td class="title2Td1">사유</td> 
		 					<td class="title2Td">
		 						&nbsp;<textarea name="" class="cancleReason" rows="5" cols="150" autocomplete="off"></textarea> 
		 					</td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">연락처</td>
		 					<td class="title2Td">
		 						&nbsp;<input type="text" name="purpose" class="purpose" style="width: 99%;" autocomplete="off"/>
		 					</td>
		 				</tr>
		 				
		 				
		 			 	<tr>
		 					<td class="title2Td1">부서공유</td>
		 					<td class="title2Td">&nbsp;&nbsp;
		 						<input type="checkbox" name="checkbox" style="width: 20px; height: 20px; position: relative; top: 6.5px;" id="use"/>&nbsp;
		 						<label for="use" style="font-size: 13pt; color:#4c4c4c; position: relative; top: 3.0px;">사용</label>&nbsp;
		 						<span style="color: red; position: relative; top: 1.5px;">[선택 시 문서가 작성자의 소속 부서원들에게 공유되며, 해당문서는 부서결재함에서 확인이 가능합니다.]</span>
		 					</td>
		 				</tr>
	<!--  --------------------------------------------------------------------------------------------------------------------                    -->	 				 
		 				 <tr>
		 					<td class="title2Td1">파일첨부</td>
		 					<td class="title2Td">
		 						<button type="button" style="border:solid 1px #0099cc; margin : 6px 30px; width: 80px; background-color: #0099cc; color: #fff; border-radius:3px;">파일</button>
		 						<span class="fileName">파일명 :</span>&nbsp;&nbsp; 
		 					</td>
		 				</tr>
		 			</tbody>
		 		</table>
		 		<table class="title3">
		
		 		</table>  
		 		</form>
	<!--  --------------------------------------------------------------------------------------------------------------------                    -->	 		
	    	</div>	
	    	
		</div> 
			
		</div>	
			<br/> <!-- 아래여백을 주기위함  -->
		<div class="save">
			<button type="button" class="saveBtn" onclick="save()">제출하기</button>&nbsp;
			<button type="button" class="saveBtn2" onclick="temporary()">저장하기</button>
		</div>
	
	<!--  --------------------------------------------------------------------------------------------------------------------                    -->	
			<br/><br/><br/> 
		<div></div>	
	</div>
</body>
</html>