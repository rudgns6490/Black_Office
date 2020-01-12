<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
    
<% String ctxPath = request.getContextPath(); %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>지출 결의서</title>

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
   
 
	/*----------------------------------------------------------------------------------------------  */
	
	.headertable th,td{
		border: solid 1px #639c9c;
		border-collapse: collapse;  
	} 
	
	#ptLineAdd th,td{
		border: solid 1px #639c9c;
		border-collapse: collapse;  
	} 
	
	.headertable th { background-color: #e0ebeb;}	
	.hdth { width: 100px; text-align: center; font-size: 17pt; font-weight: normal; color:#595959;}
	.app_td { width: 100px; text-align: center; font-size: 17pt; font-weight: normal; color:#595959;}
	.approvalImg { width: 100px; text-align: center; font-size: 17pt; font-weight: normal; color:#595959;}
	.headertable {float: right; margin-top:20px;}
	.approvalImg:hover { cursor: pointer; }
	
	#ptLineAdd {  clear: both; float: right; margin-top:20px; width:150px; height: 35px; font-size: 15pt; }
	 
	.titleLine { clear: both; border: solid 0px #639c9c; height: 35px; }  /* 야매로 줄바꿈 해주기위한것 */
	.title2, .title3 { clear: both; border: solid 1px #639c9c; border-bottom: none;  width: 100%;}
	.title2 th,td { border: solid 1px #639c9c; border-collapse: collapse; height: 40px; }
	.title2Td { width: 80%;} 
	.title2Td1 { background-color: #e0ebeb; text-align: center; font-size: 15pt; color: #333333; }
	.iframeDiv { width: 100%; height: 500px; } 
	
	/*----------------------------------------------------------------------------------------------  */
	
	
	.title {
		background: url('<%= request.getContextPath() %>/resources/images/체크이미지.png') no-repeat 3px center;
		padding-left: 20px;	  
	}
	
	
	
	#tableRadio, #tableRadio1, .radioSpan { position: relative; top: 4.5px; } 
	.radioSpan:hover { cursor: pointer; }
	/*--- 아래 저장버튼 -----------------------------------------------------------------------------------------  */
	.save { float: right; margin-right: 90px; }
	.saveBtn { border: solid 1px #0099cc; border-radius:3px; padding: 5px 20px; background-color: #0099cc; color: #fff; }
	.saveBtn2 {padding: 5px 20px; border: solid 1px #bfbfbf; border-radius:3px; }  
	
</style>

<script type="text/javascript">

	$(document).ready(function(){
		
		$(".title").focus();
		$("input:radio[id=tableRadio]").prop("checked",true);
		
		
		
		// 결재란 td 클릭시 이미지 넣어주기
		$(".approvalImg").click(function(){
			var hiddenVal = $("input:hidden[name=approvalHidden]").val();
			
			if(hiddenVal == "0") {
				$(this).html("<img style='width:80%; height:91%;' src='<%= ctxPath%>/resources/images/체크이미지.png'>"); 
				$("input:hidden[name=approvalHidden]").val("1");
			}
			else {
				$(this).html("");
				$("input:hidden[name=approvalHidden]").val("0");
			}
			
		});
		
		
		
	});// end of $(document).ready(function(){})------------------------------------
	
	
	
	
	// 저장 버튼의 onclick
	function save() {
		var frm = document.submitFrm;
		frm.method = "POST";
		frm.action = "<%= ctxPath%>/maintest.action";
		frm.submit();
	}
	
	// 임시저장 버튼의 onclick
	function temporary() {
		var frm = document.submitFrm;
		frm.method = "POST";
		frm.action = "<%= ctxPath%>/maintest.action";
		frm.submit();
	}
	
	// 미리보기 버튼의 onclick
	function preview(report) {
		
		var frm = document.submitFrm;
		 frm.title.value = $("input:text[class=title]").val();
		 frm.action = "<%= ctxPath%>/report.action";
		 frm.method = "POST";
		 frm.submit();
		 
		 
		var sw=screen.width;	// 모니터 화면의 가로 길이 
	     var sh=screen.height;	// 모니터 화면의 높이
	     var popw=1500; 		//팝업창 가로길이
	     var poph=800; 			//세로길이
	     var xpos=(sw-popw)/2;  //화면중앙에띄우도록한다 
	     var ypos=(sh-poph)/2;  //화면중앙에띄우도록한다 
	 
	     var popHeader="<html><head><title>"+report+"</title>"
	     				+"<style>"
						     +".dfcontainer {width: 90%; height: 90%; margin-top: 30px;	}"
						     +"table { border : solid 1px gray; border-collapse: collapse;}"
						     +".headertable th,td{border: solid 1px #639c9c;   }"
						     +".headertable th { background-color: #e0ebeb;}"
						     +".hdth { width: 100px; text-align: center; font-size: 17pt; font-weight: normal; color:#595959;}"
						     +".headertable {float: right; margin-top:20px;}"
						     +"#ptLineAdd {  clear: both; float: right; margin-top:20px; width:150px; height: 35px; font-size: 15pt;}"
						     +".titleLine { clear: both; border: solid 0px #639c9c; height: 35px; }"
						     +".title2, .title3 { clear: both; border: solid 1px #639c9c; border-bottom: none;  width: 100%;}"
						     +".title2 th,td { border: solid 1px #639c9c; border-collapse: collapse; height: 40px; }"
						     +".title2Td { width: 80%;} "
						     +".title2Td1 { background-color: #e0ebeb; text-align: center; font-size: 15pt; color: #333333; }"
						     +".iframeDiv { width: 100%; height: 500px; }"
						     +".printAlign { text-align: center; font-size: 25pt; font-weight: normal;  }"
						     +".lineBtn { color: #333333; border-radius: 5px; font-size: 15pt;}"
	     				+"</style></head><body>";
	     				
	     
	  var popContent=document.getElementById("print").innerHTML + "<br/>";
	     //innerHTML을 이용하여 Div로 묶어준 부분을 가져온다.
	  
	   var popFooter="</body></html>";
	     
		 popContent=popHeader + popContent + popFooter; 
	      
	     var popWin=window.open("","print","width=" + popw +",height="+ poph +",top=" + ypos + ",left="+ xpos +",status=yes,scrollbars=yes"); 
	     // 일단 내용이 없는 팝업윈도창을 만든다.
	    
	     popWin.document.open(); // 팝업윈도창에 내용을 넣을 수 있도록 오픈한다.
	     popWin.document.write(popContent); // 새롭게 만든 html소스를 팝업윈도창에 문서에 쓴다.
	     
	  /*    popWin.document.write("<tr>"); 
	     popWin.document.write("<td class='title2Td1'>제목</td>");
	     popWin.document.write("<td class='title2Td'>");
	     popWin.document.write("&nbsp;<input type='text' name='title' class='title' style='width: 99%;' autocomplete='off' value='"+$(".title").val()+"'/>");
	     popWin.document.write("</td>"); */
		
	     
	     popWin.document.write("</body></html>");
	     
	//     popWin.document.close(); // 팝업윈도창 문서를 클로즈
	 //    popWin.print(); // 팝업윈도창에 대한 인쇄 창 띄우고
	 //    popWin.close(); // 인쇄를 하던가 또는 취소를 누르면 팝업윈도창을 닫는다.
	    
	//    window.print(); 
	 
	 
	 
	        
	} 



</script>



</head>
<body>
	<div id="draftContainer"> 
		<div class="container-fluid dfcontainer" >	 
			<img src="<%= request.getContextPath() %>/resources/images/문서.png">&nbsp;<span style="font-size: 18pt; color: #595959;  position: relative; top: 4.0px;">결재문서 작성</span><br/><br/>

		<div class="row" align="center" id="print">
	    	
	    	<div class="col-md-12" style="background-color: #fff;">
	    		<h2 class ="printAlign" style="border-bottom: solid 1px blue; color:#3399ff; padding: 18px; 0px;">보고서</h2>
	    		
	    		<form name="submitFrm">	
	    		<table class="headertable">
	    			
	    				<tr>
	    					<th rowspan="2" class="hdth">결재</th> 
	    					<th class="hdth">결재자</th>
	    					<th class="hdth"></th>
	    					<th class="hdth"></th>
	    					<th class="hdth"></th>
	    				</tr>	
	    			
	    				<tr>
	    					<td class="app_td"></td> 
	    					<td class="app_td"></td> 
	    					<td class="app_td"></td>
	    					<td class="app_td"></td>
	    				</tr>
	    				
	    				<tr>
	    					<th rowspan="2" class="hdth">결재란</th>  
	    				</tr>
	    				
	    				<tr>
	    					<td class="approvalImg">	    						
	    					</td>
	    					<td class="approvalImg">	    						
	    					</td>
	    					<td class="approvalImg">	    						
	    					</td>
	    					<td class="approvalImg">	    						
	    					</td>
	    				</tr>
	    		</table>
	    		
	    		
	    		<div id="ptLineAdd">
	    			<div><button type="button" style="color: #333333; border-radius: 5px;">결재라인 추가</button></div><br/>
	    		</div>
	    		<br/><br/>
	    		
	    		<div class="row titleLine"> <!-- 라인을 띄우기위해 야매로 해온것이다. -->
		 		</div>
		 		
		 		
		 		<!-- 현재년도 -->
				<c:set var="now" value="<%=new java.util.Date()%>" />
				<c:set var="sysYear"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd" /></c:set>
		 		<table class="title2">
		 			<tbody>
		 				<tr>
		 					<td class="title2Td1">제출일자</td>
		 					<td class="title2Td">&nbsp;<input id="" name="" readonly style="border: none;" value="${sysYear}" /></td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">제출자</td>
		 					<td class="title2Td"><input id="" name="" readonly style="border: none;" value="${sessionScope.loginuser.name}" />
		 					<input type="hidden" id="" name="fk_userid" readonly style="border: none;" value="${sessionScope.loginuser.userid}" /></td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">소속</td>
		 					<td class="title2Td"><input id="" name="" readonly style="border: none;" /></td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">보고서 선택</td>
		 					<td class="title2Td">
		 						&nbsp;<input type="radio" name="tableRadio" class="radioSpan" id="tableRadio" value="" />&nbsp;
		 						<span><label for="tableRadio" class="radioSpan">일일 보고서</label></span>&nbsp;&nbsp;
		 						
		 						<input type="radio" name="tableRadio" class="radioSpan" id="tableRadio1" value="" />&nbsp;
		 						<span><label for="tableRadio1" class="radioSpan">주간 보고서</label></span>
		 					</td>
		 				</tr>
		 				
		 				
		 				<tr>
		 					<td class="title2Td1">제목</td>
		 					<td class="title2Td">
		 						&nbsp;<input type="text" name="title" class="title" style="width: 99%;" autocomplete="off" value="${title}"/>
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
		 					<td class="title2Td1">메모</td>
		 					<td class="title2Td" align="center">&nbsp;&nbsp;
		 						<textarea id="memo" name="" style="width: 95%; height: 90%; position: relative; top: 3.0px; right: 7px;"></textarea> 
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
			<button type="button" class="saveBtn" onclick="save()">저장</button>&nbsp;
			<button type="button" class="saveBtn2" onclick="temporary()">임시저장</button>&nbsp;
			<button type="button" class="saveBtn" onclick="preview('보고서')">미리보기</button>&nbsp;
			<button type="button" class="saveBtn2" onclick="javascript:history.go(0);">취소</button>
		</div>
	
	<!--  --------------------------------------------------------------------------------------------------------------------                    -->	
			<br/><br/><br/> 
		<div><input type="hidden" name="approvalHidden" class="approvalHidden" value="0" /></div> <!-- 결재란 이미지위해 숨긴 div  -->
	</div>
</body>
</html>











