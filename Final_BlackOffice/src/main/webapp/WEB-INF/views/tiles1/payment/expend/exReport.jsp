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
   
   	.titleTable { clear: both; border: solid 1px #639c9c;  width: 100%;}
   	.Td1 { width: 80%; background-color: #fff;} 
	.Td2 { background-color: #e0ebeb; text-align: center; font-size: 15pt; color: #333333; }
	.radioCursor:hover { cursor: pointer; }
	/*----------------------------------------------------------------------------------------------  */
	
	.headertable th,td {
		border: solid 1px #639c9c;
		border-collapse: collapse;  
	} 
	
	#ptLineAdd th,td {
		border: solid 1px #639c9c;
		border-collapse: collapse;  
	} 
	
	.headertable th { background-color: #e0ebeb;}	
	.hdth { width: 100px; text-align: center; font-size: 17pt; font-weight: normal; color:#595959;}
	.hdtd , .hdtd0 , .hdtd1 , .hdtd2 , .hdtd3 { width: 100px; text-align: center; font-size: 17pt; font-weight: normal; color:#595959;}
	.approvalImg { width: 100px; text-align: center; font-size: 17pt; font-weight: normal; color:#595959;}
	.headertable {float: right; margin-top:20px;}
	
	#ptLineAdd {  clear: both; float: right; margin-top:20px; width:300px; height: 35px; font-size: 15pt; }
	.approval th{ background-color: #e0ebeb; font-size: 13pt; font-weight: bold; }
	.approvalImg:hover { cursor: pointer; }
	
	.titleLine { clear: both; border: solid 0px #639c9c; height: 35px; }  /* 야매로 줄바꿈 해주기위한것 */
	.title2, .title3 { clear: both; border: solid 1px #639c9c; border-bottom: none;  width: 100%;}
	.title2 th,td { border: solid 1px #639c9c; border-collapse: collapse; height: 40px; }
	.title2Td { width: 80%; } 
	.title2Td1 { background-color: #e0ebeb; text-align: center; font-size: 15pt; color: #333333; }
	.iframeDiv { width: 100%; height: 500px; } 
	
	/*----------------------------------------------------------------------------------------------  */
	
	.title {
		background: url(<%= request.getContextPath() %>/resources/images/체크이미지.png) no-repeat 3px center; 
		padding-left: 20px; 
	}
	
	
	#tableRadio, #tableRadio1, .radioSpan { position: relative; top: 4.5px; } 
	
	
	/*--- 아래 저장버튼 -----------------------------------------------------------------------------------------  */
	.save { float: right; margin-right: 90px; }
	.saveBtn { border: solid 1px #0099cc; border-radius:3px; padding: 5px 20px; background-color: #0099cc; color: #fff; }
	.saveBtn2 {padding: 5px 20px; border: solid 1px #bfbfbf; border-radius:3px;}  
	
	
	/* 	결재라인추가 modal css		   */
	.add_search {background: white; border: 1px solid #e6e6e6; width: 100%;}
	
	.add_search > th {
		font-family: 'Malgun Gothic', '맑은 고딕', 'Dotum', '돋움', sans-serif;
		background: #007bff;
	    min-width: 50px;
	    width: 100px;
	    padding: 10px;
	    font-size: 13px;
	    line-height: 17px;
	    color: #fff;
	    vertical-align: middle;
	}
	
	.add_search > td {padding: 10px 10px 5px 10px;}
	
	.add_search_btn {
		width: 80px;
		height: 30px;
		border: solid 1px #007bda;
	    border-radius: 3px;
	    box-sizing: border-box;
	    background: #0083e7;
	    color: #fff;
	}
	
	.add_result_List {background: white;	border: 1px solid #e6e6e6; width: 97%;}
	.add_result_List_title {
		border-bottom: 1px solid #e6e6e6;
	    background: #1c5691;
	    font-weight: bold;
	    font-size: 15px;
	    line-height: 15px;
	    color: #fff;
	    text-align: center;
	}
	.add_result_List th {padding: 15px 0;}
	.add_result_List_contents td {
		padding: 7px 0;
	    border-bottom: 1px solid #e6e6e6;
	    background: #fff;
	    font-size: 14px;
	    line-height: 17px;
	    color: #777777;
	    text-align: center;
	}
	
</style>

<script type="text/javascript">


	$(document).ready(function(){

		$(".title").focus();
		$("input:radio[id=tableRadio]").prop("checked",true);
		
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
		
		
		// 결재란 td 클릭시 이미지 넣어주기
		$(".approvalImg").click(function(){
			var clickIndex = $(this).index();
			
			if($(".hdtd"+clickIndex+"").text().length > 1) { 
				var appr_length = $("input:hidden[name = approvalHidden]").length;
				
					var hiddenVal = $("input:hidden[class=approvalHidden"+clickIndex+"]").val();
					
					if(hiddenVal == "0") {
						$(this).html("<img style='width:80%; height:91%;' src='<%= ctxPath%>/resources/images/뭐.png'>"); 
						$("input:hidden[class=approvalHidden"+clickIndex+"]").val("1");	
					}
					else {
						$(this).html("");
						$("input:hidden[class=approvalHidden"+clickIndex+"]").val("0");
					}
					
				//	alert($("input:hidden[class=approvalHidden"+clickIndex+"]").val());
				
					/* 결재상태 status */
					/* var bool = !$("input:hidden[class=approvalHidden"+clickIndex+"]").val().empty;
					var imgVal = $("input:hidden[class=approvalHidden"+clickIndex+"]").val(); */
					
				//	console.log($("td.approvalImg")[0] || $("td.approvalImg")[1] )
				//	console.log($(".approvalHidden")[0].val());
				
				//	var flag= false;
					
					
				// 4 
			/* 		 for(var i=0; i<appr_length-1; i++) {
						 var flag = false;
						 if($("#approvalImg"+i+"").val() != "1") {
							flag = false;
							return false;		// break				
						}
					}
				
				if(flag == true) {
					$(".statusHidFrm").val("1");
				}	
				
				console.log($(".statusHidFrm").val());
				
				} */
		}
			
			else {
				alert("결재자가 비어 있습니다.");
			}
		});
		
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
	
	// 제출하기 버튼의 onclick
	function save() {
		
		for(var i=0; i<n; i++) {
			if($("#approvalImg"+ i+"").val() == "0") {
				alert("결제자를 확인해주세여"); 
				return false; 
			} 
		}
		
		
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
		 					<td class="Td2">수당/지출문서</td>
		 					<td class="Td1">&nbsp;&nbsp;
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
	    	<br/><br/><br/>
	    	<!-- ---------------------------------------------------------------------------------------------------------------------------------------------- -->
			
				    
	    	<div class="col-md-12 " style="background-color: #fff;">
	    		<h2 style="border-bottom: solid 1px blue; color:#3399ff; padding: 18px; 0px;">지출결의서</h2>
	    		
	    		<form name="submitFrm">
	    		<table class="headertable">
	    				<thead>
	    				
	    				<tr>
	    					<th rowspan="2" class="hdth">결재</th> 
	    					<th class="hdth">결재자</th>
	    					<th class="hdth"></th>
	    					<th class="hdth"></th>
	    					<th class="hdth"></th>
	    					
	    				</tr> 
	    				
	    				<tr>
	    					<td class="hdtd hdtd0">김차장</td>  
	    					<td class="hdtd hdtd1">박차장</td> 
	    					<td class="hdtd hdtd2"></td> 
	    					<td class="hdtd hdtd3"></td>
	    				</tr>	
	    				</thead>

	    				<tbody>
	    				<tr>
	    					<th rowspan="2" class="hdth">결재란</th>  
	    				</tr>
	    				
	    				<tr id="test">
	    					<td class="approvalImg">	    						
	    					</td>
	    					<td class="approvalImg">	    						
	    					</td>
	    					<td class="approvalImg">  						
	    					</td>
	    					<td class="approvalImg">    						
	    					</td>
	    				</tr>
	    				</tbody>
	    		</table>
	    		
	    		
	    		<!-- <div id="ptLineAdd">
	    			<div><button type="button" style="color: #333333; border-radius: 5px;">결재라인 추가</button></div><br/>
	    		</div> -->
	    		
	 
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
		 					<td class="title2Td">&nbsp;<input id="" name="ex_sysdt" readonly style="border: none;" value="${sysYear}" /></td>
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
		 					<td class="title2Td1">지출 선택</td>
		 					<td class="title2Td">
		 						&nbsp;<input type="radio" name="ex_expend_ch" id="tableRadio" />&nbsp;
		 						<span><label for="tableRadio" class="radioSpan">개인지출 경비</label></span>&nbsp;&nbsp;
		 						
		 						<input type="radio" name="ex_expend_ch" id="tableRadio1" />&nbsp;
		 						<span><label for="tableRadio1" class="radioSpan">법인지출 경비</label></span>
		 					</td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">지출일자</td>
		 					<td class="title2Td">
		 						&nbsp;<input type="date" name="ex_exp_dt" />
		 					</td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">제목</td>
		 					<td class="title2Td">
		 						&nbsp;<input type="text" name="ex_title" class="title" style="width: 99%;" />
		 					</td>
		 				</tr>
		 			 	
		 				<tr>
		 					<td colspan="2">
		 						<div >
	    							<iframe class="iframeDiv" name="ex_content" ></iframe>
	    						</div>
	    					</td>	
		 				</tr> 
		 				
		 				<tr>
		 					<td class="title2Td1">부서공유</td>
		 					<td class="title2Td">&nbsp;&nbsp;
		 						<input type="checkbox" name="ex_share" style="width: 20px; height: 20px; position: relative; top: 6.5px;" id="use"/>&nbsp;
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
		 		</form>
		 		<br/>
	<!--  --------------------------------------------------------------------------------------------------------------------                    -->	 		
	    	
		
	    	</div>	
		</div> 
		</div>	
			<br/> <!-- 아래여백을 주기위함  -->
		<div class="save">
			<button type="button" class="saveBtn" onclick="save()">제출하기</button>&nbsp;
			<button type="button" class="saveBtn2" onclick="javascript:history.go(0);">취소</button>&nbsp;
		</div>
	
	<!--  --------------------------------------------------------------------------------------------------------------------                    -->	
			<br/><br/><br/> 
		<div><input type="hidden" name="approvalHidden" class="approvalHidden0" value="0" /></div> <!-- 결재란 이미지위해 숨긴 div  -->	
		<div><input type="hidden" name="approvalHidden" class="approvalHidden1" value="0" /></div> <!-- 결재란 이미지위해 숨긴 div  -->	
		<div><input type="hidden" name="approvalHidden" class="approvalHidden2" value="0" /></div> <!-- 결재란 이미지위해 숨긴 div  -->	
		<div><input type="hidden" name="approvalHidden" class="approvalHidden3" value="0" /></div> <!-- 결재란 이미지위해 숨긴 div  -->
		<div><input type="hidden" name="statusHidFrm" class="statusHidFrm" value="0" /></div> <!-- 결재상태 value  -->	
	</div>

	

<!-- 	결재라인추가 modal 시작		 -->

<div class="container" >

	<!-- Trigger the modal with a button -->
	<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal"
	style="width: 170px; height: 50px; background-color: #0099cc;">결재라인 추가</button>
	
	<!-- Modal -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">
		
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button><%--	x로 닫기 버튼   --%>
				<h4 class="modal-title">결재자추가</h4>
			</div>
			<div class="modal-body" style="background-color: #e6e6e6;">
			
				<div class="add_search">
					<form action="">
						<table class="add_search_table">
							<tr>
								<th>부서</th>
								<td>
									<select>
										<option value="">부서 1</option>
										<option value="">부서 2</option>
										<option value="">부서 3</option>
										<option value="">부서 4</option>
										<option value="">부서 5</option>
									</select>
								</td>
							</tr>
							
							<tr>
								<th>성명</th>
								<td>
									<input type="text" name="" class="" />
									<button type="button" class="add_search_btn">검색</button>
									<input type="text" style="display: none;" />
									
								</td>
							</tr>
						</table>
					</form>
				</div>
				
				<div class="add_result_List">
					<table style="width: 100%;">
						<thead>
							<tr class="add_result_List_title">
								<th>부서</th>
								<th>성명</th>
								<th>직위</th>
							</tr>
						</thead>
						<tbody class="add_result_List_contents">
							<tr>
								<td>부서부서</td>
								<td>너의 이름은?</td>
								<td>레벨</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
		  
		</div>
	</div>
  
</div>
<!-- 	결재라인추가 modal 끝		 -->
	
	

</body>
</html>











