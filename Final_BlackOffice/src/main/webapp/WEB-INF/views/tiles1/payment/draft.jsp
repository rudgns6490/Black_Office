<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
    
<% String ctxPath = request.getContextPath(); %> 

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>   
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>기안서</title>

<style type="text/css">
	body{ background-color: #f7f7f7; }
	
	#draftContainer {	/* 전체 div */
		width: 100%;
		height: 100%;
		margin: 0 auto;
     } 	
     
   .dfcontainer {		/* 부트스트랩 class */
   		width: 90%;
   		margin-top: 30px;	
   }  	
   
	
	.headertable th,td{
		border: solid 1px #639c9c;
		border-collapse: collapse;  
	} 
	
	#ptLineAdd th,td{
		border: solid 1px #639c9c;
		border-collapse: collapse;  
	} 
	
	.headertable th { background-color: #e0ebeb;}	
	.hdth { width: 100px; text-align: center; font-size: 17pt; font-weight: normal; color:#595959; }
	.headertable  {float: right; margin-top:20px;}
	
	#ptLineAdd {  clear: both; float: right; margin-top:20px; width:150px; height: 35px; font-size: 15pt; }
	.approval th{ background-color: #e0ebeb; font-size: 13pt; font-weight: bold; }
	.approvalImg:hover { cursor: pointer; }
	
	.titleLine { clear: both; border: solid 0px #639c9c; height: 35px; }  /* 야매로 줄바꿈 해주기위한것 */
	.title2, .title3 { clear: both; border: solid 1px #639c9c; border-bottom: none;  width: 100%;}
	.title2 th,td { border: solid 1px #639c9c; border-collapse: collapse; height: 40px; }
	.title2Td { width: 80%; } 
	.title2Td1 { background-color: #e0ebeb; text-align: center; font-size: 15pt; color: #333333; }
	.iframeDiv { width: 100%; height: 500px; } 
	
	
	.title {
		background: url("<%= request.getContextPath() %>/resources/images/체크이미지.png") no-repeat 3px center; 
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
		
		// 결재란 td 클릭시 이미지 넣어주기
		$(".approvalImg").click(function(){
			var hiddenVal = $("input:hidden[name=approvalHidden]").val();
			
			if(hiddenVal == "1") {
				$(this).html("<img style='width:80%; height:91%;' src='<%= ctxPath%>/resources/images/뭐.png'>"); 
				$("input:hidden[name=approvalHidden]").val("2");
			}
			else {
				$(this).html("");
				$("input:hidden[name=approvalHidden]").val("1");
			}
		});
		
	}); // end of $(document).ready(function(){})----------------------
	
	// 제출하기 버튼의 onclick
	function save() {
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
		
		<form name="submitFrm">	
		<div class="row " align="center">
	    	<div class="col-md-12 " style="background-color: #fff;">
	    		<h2 style="border-bottom: solid 1px blue; color:#3399ff; padding: 18px; 0px;">기안서</h2>
	    		
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
	    			<div><button type="button" style="color: #333333; border-radius: 5px;">결재라인 추가</button></div><br/>
	    			<table class="approval">
	    				<tr>
	    					<th rowspan="2" class="hdth">결재란</th>  
	    					<th class="hdth">결재</th>
	    				</tr>
	    				
	    				<tr>
	    					<td class="hdth approvalImg"><input type="hidden" class="appr_check" name="appr_check" value="0" /></td> 
	    				</tr>
	    			</table>
	    		</div>
	    		<br/><br/>
	    		
	    		<div class="row titleLine"> <!-- 라인을 띄우기위해 야매로 해온것이다. -->
		 		</div>
		 		<div class="row titleLine"> <!-- 라인을 띄우기위해 야매로 해온것이다. -->
		 		</div>
		 		<div class="row titleLine"> <!-- 라인을 띄우기위해 야매로 해온것이다. -->
		 		</div>
		 		<div class="row titleLine"> <!-- 라인을 띄우기위해 야매로 해온것이다. -->
		 		</div>
		 		
		 		<!-- 현재년도 -->
				<c:set var="now" value="<%=new java.util.Date()%>" />
				<c:set var="sysYear"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd" /></c:set>
		 		<table class="title2">
		 			<tbody>
		 				<tr>
		 					<td class="title2Td1">기안일자</td>
		 					<td class="title2Td">&nbsp;<input id="" name="" readonly style="border: none;" value="${sysYear}" /></td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">기안부서</td>
		 					<td class="title2Td"><input id="" name="" readonly style="border: none;" /></td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">기안담당</td>
		 					<td class="title2Td"><input id="" name="" readonly style="border: none;" /></td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">제목</td>
		 					<td class="title2Td">
		 						<input type="text" name="title" class="title" style="width: 100%;"/>
		 					</td>
		 				</tr>
		 			 	
		 				<tr>
		 					<td colspan="2">
		 						<div >
	    							<iframe class="iframeDiv" ></iframe>
	    						</div>
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
		
		 			</tbody>
		 		</table>
		 		<br/>
	<!--  --------------------------------------------------------------------------------------------------------------------                    -->	 		
	    	</div>	
	    	
		</div> 
		</form>
		</div>	
			<br/> <!-- 아래여백을 주기위함  -->
		<div class="save">
			<button type="button" class="saveBtn" onclick="save()">제출하기</button>&nbsp;
			<button type="button" class="saveBtn2" onclick="temporary()">저장하기</button>&nbsp;
		</div>
	
	<!--  --------------------------------------------------------------------------------------------------------------------                    -->	
			<br/><br/><br/> 
		<div><input type="hidden" name="approvalHidden" class="approvalHidden" value="1" /></div> <!-- 결재란 이미지위해 숨긴 div  -->	
	</div>
</body>
</html>





  
