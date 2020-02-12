<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String ctxPath = request.getContextPath(); %>  

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>휴가 신청서</title>

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
   	.Td1 { width: 85%; background-color: #fff;} 
	.Td2 { background-color: #e0ebeb; text-align: center; font-size: 15pt; color: #333333; }
	.radioCursor:hover { cursor: pointer; }
	/*----------------------------------------------------------------------------------------------  */
   
   .headertable { margin-right: 30px; }
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
	.hdtd { width: 100px; text-align: center; font-size: 17pt; font-weight: normal; color:#595959;}
	.approvalImg { width: 100px; text-align: center; font-size: 17pt; font-weight: normal; color:#595959;}
	.headertable {float: right; margin-top:20px;}
	
	#ptLineAdd {  clear: both; float: right; margin-top:20px; width:300px; height: 35px; font-size: 15pt; }
	.approval th{ background-color: #e0ebeb; font-size: 13pt; font-weight: bold; }
	.approvalImg:hover { cursor: pointer; }
	.minus:hover { cursor: pointer; }
	
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
	.purpose {
		background: url(<%= request.getContextPath() %>/resources/images/체크이미지.png) no-repeat 3px center; 
		padding-left: 20px; 
	} 
	
	.reason {
		background: url(<%= request.getContextPath() %>/resources/images/체크이미지.png) no-repeat 3px center; 
		padding-left: 20px;  
	}   
   
   
   
   
   /*--- 아래 저장버튼 -----------------------------------------------------------------------------------------  */
	.save { float: right; margin-right: 90px; }
	.saveBtn { border: solid 1px #0099cc; border-radius:3px; padding: 5px 20px; background-color: #0099cc; color: #fff; }
	.saveBtn2 {padding: 5px 20px; border: solid 1px #bfbfbf; border-radius:3px;}  
	
	/* 	결재라인추가 modal css	  */ 
	.add_search {background: white; width: 97%; margin-bottom: 10px;}
	
	.add_search_table {border: none; width: 100%}
	
	.add_search th {
		font-family: 'Malgun Gothic', '맑은 고딕', 'Dotum', '돋움', sans-serif;
		background: #639c9c;
	    min-width: 50px;
	    width: 100px;
	    padding: 10px;
	    font-size: 15px;
	    line-height: 17px;
	    color: #fff;
	    vertical-align: middle;
	    text-align: center; 
	}
		
	.add_search td {padding: 10px; vertical-align: text-top;}
	
	.add_search_name {font-size: 12pt; width: 220px; margin-right: 12px;}
	
	.add_search_btn {
		width: 80px;
		height: 30px;
		border: solid 1px #639c9c;
	    border-radius: 3px;
	    box-sizing: border-box;
	    background: #639c9c;      /* #0083e7 */
	    color: #fff;
	    font-size: 12pt;
	}
	
	.add_result_List {background: white; border: 1px solid #e6e6e6; width: 97%;}
	
	.add_result_List_title {
		border-bottom: 1px solid #e6e6e6;
	    background: #0000b3;
	    font-weight: bold;
	    font-size: 15px;
	    line-height: 15px;
	    color: #fff;
	    text-align: center;
	}
	.add_result_List th {padding: 15px 0;}
	
	.add_result_List_contents td {
		padding: 7px 0;
	    background: #fff;
	    font-size: 14px;
	    line-height: 17px;
	    color: #777777;
	    text-align: center;
	}
	
	.modalAddEmployees { cursor: pointer; } /* Modal 안에 td  */
	
   
</style>

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script type="text/javascript">
	
$(document).ready(function(){
	
		$(".title").focus();
		 
		$("input:radio[id=use1]").prop("checked",true);
		
		var radioVal ="${radioVal}";
		for(var i=1; i<8; i++){
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
						goLeave();
						break;
						
					case "2":
						goYear();
						break;	
					
					case "3":
						goBaby();
						break;	
						
					case "4":
						goCancellation();
						break;	
						
					case "5":
						goEarly_leave();
						break;
						
					case "6":
						goSick_leave();
						break;
						
					case "7":
						goOut();
						break;
					
				}// end of switch()----------------
			}
			});	
		});// end of $("input:radio[name=radio]").click(function(){})----------	
		
		// 결재라인 추가시 <select> <option> 부서 넣어주기
		$(".addApproval").append("");
		$.ajax({
			url:"<%= ctxPath%>/addAprroval.action",
			type:"GET",
			dataType:"JSON",
			success:function(json){
				var html = "";
				$.each(json,function(index, item){
					html += "<option>"+item.departmentname+"</option>"			
				}); 
				$(".addApproval").append(html); 
				
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		}); // end of ajax({})-------------------------------------------------
		
		
		/* ---------------------------------------------------------------------------------------------------- */
		// 결재라인 부서에 따른 사원 select 
		$(".addApproval").on('change',function(event){ 
		//	alert( $(this).val() );	
			if($(this).val() != null && $(this).val() != "0") {
				
				$.ajax({
					url:"<%= ctxPath%>/addModalVal.action",
					type:"GET",
					data:{"modalDepartmentName":$(this).val()},
					dataType:"JSON",
					success:function(json){
						var html = "";
						$.each(json,function(index, item){
							html += "<tr class='modalAddEmployees'>"
							html += "<td class='DEPARTMENTNAME'>"+item.DEPARTMENTNAME+"</td>";
							html += "<td class='NAME'>"+item.NAME+"</td>";
							html += "<td class='POSITIONNAME'>"+item.POSITIONNAME+"<input class='employeeno' type='hidden' value='"+item.EMPLOYEENO+"' /></td>";
							html += "</tr>";	
						}); 
						$(".modalTrLength").html(html);
						
					},
					error: function(request, status, error){
						alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
					}
				}); // end of ajax({})-------------------------------------------------
			}
			
		});// end of $(".addApproval").on('change',function(event){})--------------------------------
		
		
		/* ---------------------------------------------------------------------------------------------------- */
	
		
		$(document).on("click",".modalAddEmployees",function(){
			var valHtml0 = ""; 
			var valHtml1 = "";
			var valHtml2 = "";
			var valHidden = "";
			var employeeno = $(this).find('.employeeno').val();
		//	var modalAddLength = $(".modalAddEmployees").length;
		//	alert(modalAddLength);
			/*
			//	alert($(this).index());
			var index = $(this).index();
			//	alert($(".NAME").text());
		//	alert($(index).find('.NAME').val());
			alert($(index).children().first().val()); */
			
		//	alert($(this).find('.NAME').val());
			
		//	console.log($(".addApproval").val());
		//	console.log( $(this).text() );
		
		//  console.log($(this).html());
		    
		    var html = $(this).html();
	     // <td class="DEPARTMENTNAME">개발2팀</td><td class="NAME">박시준</td><td class="POSITIONNAME">부장</td>
	        var index = html.indexOf('class="NAME"');
	        html = html.substring(index);
	     // console.log(html);
	     // class="NAME">박시준</td><td class="POSITIONNAME">부장</td>
	        	        
	        index = html.indexOf(">");
	        html = html.substring(index);
	     // console.log(html);
	     // >박시준</td><td class="POSITIONNAME">부장</td>
	        
	        index = html.indexOf(">");
	        html = html.substring(index);
	     // console.log(html);
	     // >박시준</td><td class="POSITIONNAME">부장</td>
	        
	        index2 = html.indexOf("<");
	        // index2 ==> 4
	        
	     //   console.log(html.substring(1,4));
	        var name = html.substring(1,4);
	        
			/* 
			var totalText = $(this).text();
			var deptName = $(".addApproval").val();
			var beginIndex = totalText.length - deptName.length - 1;
			console.log( totalText.substr(beginIndex, 3)); */

	// ----------------------------- 결재라인 추가 -------------------------------------- //	
			 
		//	var count = $(this).index();
			var hdtdLength = $(".th").length;
			if(hdtdLength < 3){
				
				valHtml0 += "<th class='hdth hdth"+hdtdLength+" th'>결재자</th>";
				$(".modaladdTh").append(valHtml0);
				
				valHtml1 += "<td class='hdtd hdtd"+hdtdLength+" td'>"+name+"&nbsp;<img class='minus"+hdtdLength+" minus' style='width:20px; height:20px; position: relative; top: -2.5px;'  src='<%= ctxPath%>/resources/images/빼기이미지.png'></td> ";
				$(".modaladdTd").append(valHtml1);
				
				valHidden += "<input type='hidden' class='approverhidden approverhidden"+hdtdLength+"' name='approverhidden"+hdtdLength+"' value='"+employeeno+"' />";
				valHidden += "<input type='hidden' style='border:none; width:70px;' class='approverName approverName"+hdtdLength+"' name='approver"+hdtdLength+"' value='"+name+"' readonly/>";
				$("#formHidden").append(valHidden);
							
				valHtml2 = "<td class='approvalImg approvalImg"+hdtdLength+"'></td>";
				$("#test").append(valHtml2); 
			}
			else {
				alert("더이상 추가할 수 없습니다.");
			}
			
	// --------------------------------------------------------------------------- //		
		}); // $(document).on("click",".modalAddEmployees",function(){})-----------------------
		
	// --------------------------------------------------------------------------- //	
		// 결재란 이미지 넣기
		$(document).on("click",".approvalImg",function(){			
			alert("결재가 불가 합니다.");
		}); // end of $(document).on("click",".approvalImg",function(){})-------------------------------
		
		
		// 결재란 비우기
		$(document).on("click",".minus",function(){
		//	alert($(this).parent().text());
		//	$(this).parent().text("");
			var idx = $(this).parents().index();
		//	alert(idx);
			/* $(".hdth"+index+"").remove();
			$(this).parent().remove();
			$(".approvalImg"+index+"").remove(); */
		//	var class_by_class = $(this).parent().parent().html();
		
		//------------------------------------------------------------//
		//	alert(idx);
		//	alert((".approvalImg").length);
			 /* for(var i=0; i<$(".td").length; i++) {
				var hdthInedx = $(".hdth"+i+"").index();
				hdthInedx = $(".hdth"+i+"").index()-1;
			//	alert(hdthInedx);
				var imgIndex = $(".approvalImg"+i+"").index();
				if(idx == $(this).parents().index() && hdthInedx == idx && imgIndex == idx ) {
				//	alert("ddd");
					$(".hdth"+i+"").remove();
					$(this).parent().remove();
					$(".approvalImg"+i+"").remove();
					break;
				}  
				} // end of for()---------------*/
				
			//	alert($(this).parent().text());	
				/* var idx = $(this).parent().index();
				var thText = $(this).parents().parents().find(".hdth"+idx+"").text();
				var imgText = $(this).parents().parents().find(".approvalImg"+idx+"").text(); */
				
				
				var approvalHtml = $(this).parents().html();
				// 박시준&nbsp;<img class="minus0 minus" style="width:20px; height:20px; position: relative; top: -2.5px;" src="/controller/resources/images/빼기이미지.png">
					var str_appHtml = approvalHtml.indexOf('class');
					approvalHtml = approvalHtml.substring(str_appHtml);
				//	console.log(approvalHtml);
				//	class="minus1 minus" style="width:20px; height:20px; position: relative; top: -2.5px;" src="/controller/resources/images/빼기이미지.png">
					str_appHtml = approvalHtml.indexOf('m');
					approvalHtml = approvalHtml.substring(str_appHtml);
				//	console.log(approvalHtml);
				//	minus2 minus" style="width:20px; height:20px; position: relative; top: -2.5px;" src="/controller/resources/images/빼기이미지.png">
					var index = approvalHtml.indexOf('0');
				//	console.log(index);
				//	index = 5
				//	console.log(approvalHtml.substring(5,6));
			    	var count = approvalHtml.substring(5,6);
			    
		    	$(".hdth"+count+"").remove();
				$(this).parent().remove();
				$(".approverhidden"+count+"").remove(); 
				$(".approvalImg"+count+"").remove();
				$(".approverName"+count+"").remove();
	
		}); // end of $(document).on("click",".minus",function(){})----------------------------
	// --------------------------------------------------------------------------- //
		
		// 결재란 취소
		$(".reset").click(function(){
			$(".td").remove();
			$(".approvalImg").remove();
			$(".approverhidden").remove();
			$(".approverName").remove();
			$(".th").remove();
			
		});
		
		//캘린더 시작~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	      $('#fromDate').datepicker({
	         showOn: "both",                                       // 달력을 표시할 타이밍 (both: focus or button)
	         buttonImage: "<%= ctxPath %>/resources/images/cal.jpg",    // 버튼 이미지
	         buttonImageOnly : true,                               // 버튼 이미지만 표시할지 여부
	         buttonText: "날짜선택",                                  // 버튼의 대체 텍스트
	         dateFormat: "yy-mm-dd",                               // 날짜의 형식
	         changeMonth: true,                                    // 월을 이동하기 위한 선택상자 표시여부
	         onClose: function( selectedDate ) {    
	            $("#toDate").datepicker( "option", "minDate", selectedDate );
	         }                
	      });
	      
	      //종료일
	      $('#toDate').datepicker({
	         showOn: "both", 
	         buttonImage: "<%= ctxPath %>/resources/images/cal.jpg", 
	         buttonImageOnly : true,
	         buttonText: "날짜선택",
	         dateFormat: "yy-mm-dd",
	         changeMonth: true,
	         onClose: function( selectedDate ) {
	            $("#fromDate").datepicker( "option", "maxDate", selectedDate );
	         }                
	      });  // 캘린더 종료--------------------------------------
		
		
		
		
	}); // end of $(document).ready(function(){})-------------------------------
	
	// 휴가 신청서
	function goLeave() {
		
		var radioVal = $("input:radio[id=use1]").val();
		
		var frm = document.radioChoice;
		frm.radioVal.value = radioVal;
		frm.method = "POST";
		frm.action = "<%= ctxPath%>/leave.action";
		frm.submit();
		
		<%-- window.location.href = "<%= ctxPath%>/leave.action" --%>
	}
	
	// 연차 신청서
	function goYear() {
		
		var radioVal = $("input:radio[id=use2]").val();
		
		var frm = document.radioChoice;
		frm.radioVal.value = radioVal;
		frm.method = "POST";
		frm.action = "<%= ctxPath%>/year.action";
		frm.submit();
		
		<%-- window.location.href = "<%= ctxPath%>/year.action" --%>
	}
	
	// 출산 휴가계
	function goBaby() {
		
		var radioVal = $("input:radio[id=use3]").val();
		
		var frm = document.radioChoice;
		frm.radioVal.value = radioVal;
		frm.method = "POST";
		frm.action = "<%= ctxPath%>/baby.action";
		frm.submit();
		
		<%-- window.location.href = "<%= ctxPath%>/baby.action" --%>
	}
	
	// 휴가 취소
	function goCancellation() {
		
	var radioVal = $("input:radio[id=use4]").val();
		
		var frm = document.radioChoice;
		frm.radioVal.value = radioVal;
		frm.method = "POST";
		frm.action = "<%= ctxPath%>/cancellation.action";
		frm.submit();
		
		<%-- window.location.href = "<%= ctxPath%>/cancellation.action" --%>
	}
	
	
	// 조퇴계
	function goEarly_leave() {
		
		var radioVal = $("input:radio[id=use5]").val();
		
		var frm = document.radioChoice;
		frm.radioVal.value = radioVal;
		frm.method = "POST";
		frm.action = "<%= ctxPath%>/early_leave.action";
		frm.submit();
		
		<%-- window.location.href = "<%= ctxPath%>/early_leave.action" --%>
	}
	
	
	// 병가계
	function goSick_leave() {
		
		var radioVal = $("input:radio[id=use6]").val();
		
		var frm = document.radioChoice;
		frm.radioVal.value = radioVal;
		frm.method = "POST";
		frm.action = "<%= ctxPath%>/sick_leave.action";
		frm.submit();
		
		<%-- window.location.href = "<%= ctxPath%>/sick_leave.action" --%>
		
	}
	
	// 외출계
	function goOut() {
		
		var radioVal = $("input:radio[id=use7]").val();
		
		var frm = document.radioChoice;
		frm.radioVal.value = radioVal;
		frm.method = "POST";
		frm.action = "<%= ctxPath%>/out.action";
		frm.submit();
		
		<%-- window.location.href = "<%= ctxPath%>/out.action" --%>
	}
	
	// 제출하기 버튼의 onclick
	function save() {
		var modaladdTd = $(".modaladdTd").text();
		var title = $(".title").val();
		var reason = $(".reason").val();
		var phone = $(".purpose").val();
		var rank = $(".rank").val();

		if(modaladdTd.trim() == "") {
			alert("결재자를 선택해 주세요");
			return;
		} 
		
		else if(rank.trim() == "선택") {
			alert("직급을 선택해 주세요");
			return;
		}
		
		else if(title.trim() == "") {
			alert("제목을 입력해 주세요");
			return;
		}
		else if(reason.trim() == "") {
			alert("사유를 입력해 주세요");
			return;
		}
 		else if(phone.trim() == "" || phone.trim().length > 11) {
			alert("연락처를 옳바르게 입력해 주세요");		
			return;
		}
	
			
		var td = $(".modaladdTd").find('td').text();
		var count = $(".th").length;
		$("input:hidden[id = tdCount]").val(count);
		
		var frm = document.submitFrm;
		frm.method = "POST";
		frm.action = "<%= ctxPath%>/addLeaveReport.action";
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
		 					<td class="Td2">휴가 종류</td>
		 					<td class="Td1">&nbsp;&nbsp;
		 						<input type="radio" name="radio" value="1" style="width: 15px; height: 15px; position: relative; top: 4.5px;" id="use1" class="radioCursor"/>&nbsp;
		 						<label for="use1" style="font-size: 13pt; color:#4c4c4c; position: relative; top: 3.0px;" class="radioCursor">휴가 신청서</label>&nbsp;&nbsp;


		 						<input type="radio" name="radio" value="2" style="width: 15px; height: 15px; position: relative; top: 4.5px;" id="use2" class="radioCursor"/>&nbsp;
		 						<label for="use2" style="font-size: 13pt; color:#4c4c4c; position: relative; top: 3.0px;" class="radioCursor">연차 신청서</label>&nbsp;&nbsp;
		 						
		 						<input type="radio" name="radio" value="3" style="width: 15px; height: 15px; position: relative; top: 4.5px;" id="use3" class="radioCursor"/>&nbsp;
		 						<label for="use3" style="font-size: 13pt; color:#4c4c4c; position: relative; top: 3.0px;" class="radioCursor">출산 휴가계</label>&nbsp;&nbsp;
		 						
		 						<input type="radio" name="radio" value="4" style="width: 15px; height: 15px; position: relative; top: 4.5px;" id="use4" class="radioCursor"/>&nbsp;
		 						<label for="use4" style="font-size: 13pt; color:#4c4c4c; position: relative; top: 3.0px;" class="radioCursor">휴가취소 신청서</label>&nbsp;&nbsp;
		 						
		 						<input type="radio" name="radio" value="5" style="width: 15px; height: 15px; position: relative; top: 4.5px;" id="use5" class="radioCursor"/>&nbsp;
		 						<label for="use5" style="font-size: 13pt; color:#4c4c4c; position: relative; top: 3.0px;" class="radioCursor">조퇴계 신청서</label>&nbsp;&nbsp;
		 						
		 						<input type="radio" name="radio" value="6" style="width: 15px; height: 15px; position: relative; top: 4.5px;" id="use6" class="radioCursor"/>&nbsp;
		 						<label for="use6" style="font-size: 13pt; color:#4c4c4c; position: relative; top: 3.0px;" class="radioCursor">병가계 신청서</label>&nbsp;&nbsp;
		 						
		 						<input type="radio" name="radio" value="7" style="width: 15px; height: 15px; position: relative; top: 4.5px;" id="use7" class="radioCursor"/>&nbsp;
		 						<label for="use7" style="font-size: 13pt; color:#4c4c4c; position: relative; top: 3.0px;" class="radioCursor">외출계 신청서</label>
		 						
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
	    		<h2 style="border-bottom: solid 1px blue; color:#3399ff; padding: 18px; 0px;">휴가 신청서</h2>
	    		
	    		<form name="submitFrm" enctype="multipart/form-data">
	    		<table class="headertable">
	    			<thead>
	                   <tr class ="modaladdTh">
	                      <th rowspan="2" class="hdth">결재</th> 
	                      <%-- <th class="hdth">결재자</th> --%>
	                   </tr> 
	                   
	                   <tr class ="modaladdTd"></tr>   
                   </thead>

                   <tbody>
	                   <tr>
	                      <th rowspan="2" class="hdth">결재란</th>
	                   </tr>
	                   
	                   <tr id="test"></tr>
                   </tbody>
	    		</table>
	    		
	    		
	  <!------ 	결재라인추가 modal 시작	-------------------------------	 -->
		
				<div class="container" id="ptLineAdd" > 
					<!-- Trigger the modal with a button -->
					<button type="button" class="btn" data-toggle="modal" data-target="#myModal" style="background-color: #0099cc; color: white;">결재라인 추가</button>
					<button type="button" class="btn reset" style="background-color: #0099cc; color: white;">결재란 취소</button>
					
					<!-- Modal -->
					<div class="modal fade" id="myModal" role="dialog">
						<div class="modal-dialog">
						
						<!-- Modal content-->
						<div class="modal-content">
							<div class="modal-header">
								<h4 class="modal-title" style="font-weight: bold;">결재자추가</h4>
								<button type="button" class="close" data-dismiss="modal" >&times;</button><%--	x로 닫기 버튼   --%>
							</div>
							<div class="modal-body" style="background-color: #e6e6e6;">
							
								<div class="add_search">
										<table class="add_search_table">
											<tr>
												<th>부서</th>
												<td>
													<select class="addApproval">
														<option value="0"> -- 부서 선택 -- </option> 
													</select>
												</td>
											</tr>
											
											<tr>
												<th>성명</th>
												<td>
													<input type="text" name="name" class="add_search_name" />
													<button type="button" class="add_search_btn">검색</button>
													<input type="text" style="display: none;" />
													
												</td>
											</tr>
										</table>
								</div>
								
								<div class="add_result_List">
									<table style="width: 100%;">
										<thead>
											<tr class="add_result_List_title"  style=' height: 20px; overflow: auto;'>
												<th>부서</th>
												<th>성명</th>
												<th>직위</th>
											</tr>
										</thead>
										<tbody class="add_result_List_contents modalTrLength">

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
		<!-- -----------	결재라인추가 modal 끝	------------------------------------------------------------	 -->
	    		
	    		
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
		 					<td class="title2Td">&nbsp;<input id="" name="writeday" readonly style="border: none;" value="${sysYear}" /></td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">제출자</td>
		 					<td class="title2Td">
		 						<input id="" name="employeename" value="${requestScope.userName}" readonly style="border: none; margin-left: 10px;" />
		 						<input type="hidden" id="" name="fk_employeeno" value="${requestScope.employeeno}" readonly style="border: none;" />
		 					</td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">소속</td>
		 					<td class="title2Td"><input id="" name="departmentname" value="${requestScope.deptName}" readonly style="border: none; margin-left: 10px;" /></td>
		 				</tr>
		 				
		 				
		 				<tr>
		 					<td class="title2Td1">직급</td>
		 					<td class="title2Td">
		 						&nbsp;&nbsp;<select name="rank" id="rank" class="rank" style="width: 30%;">
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
		 				
		 				<tr>
		 					<td class="title2Td1">요청 기간</td>
		 					<td class="title2Td">
		 						&nbsp;&nbsp;<span>시작일</span>&nbsp;<input id="fromDate" type="text" name="startday" value="${fromDate }" />&nbsp;
		 						<span>~</span>
								<span>종료일</span>&nbsp;<input type="text" id="toDate" name="endday" value="${toDate }"/>
		 					</td>
		 				</tr>
		 				
		 				<tr> 
		 					<td class="title2Td1">제목</td>
		 					<td class="title2Td">
		 						&nbsp;<input type="text" name="title" class="title" style="width: 99%;" autocomplete="off" />
		 					</td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">사유</td>
		 					<td class="title2Td">
		 						&nbsp;<textarea name="reason" class="reason" rows="5" cols="150"></textarea> 
		 					</td>
		 				</tr>
		 				
		 			 	
		 			 	<tr>
		 					<td class="title2Td1">연락처</td>
		 					<td class="title2Td">
		 						&nbsp;<input type="text" name="emergencycontactnetwork" class="purpose" style="width: 99%;" placeholder=" - 를 생략하고 입력해 주세요." />
		 					</td>
		 				</tr>
		 			 	
	<!--  --------------------------------------------------------------------------------------------------------------------                    -->	 				 
		 				 <tr>
		 					<td class="title2Td1">파일첨부</td>
		 					<td class="title2Td">
		 						<input type="file" class="attach" name="attach" style="margin : 6px 30px;" />
		 					</td>
		 				</tr>
		 			</tbody>
		 		</table>
		 		
		 		<table class="title3">
		 		</table>  
		 		
		 		<div id="formHidden"> <!-- 결재란 td 개수를 가지고 넘어가기 위해 숨겨둔다. -->
					<input type="hidden" id="tdCount" name="tdCount" value="" />
					<input type="hidden" id="tdCount" name="tdCount" value="" />
				</div>
		 		
		 		</form>
	<!--  --------------------------------------------------------------------------------------------------------------------                    -->	 		
	    	</div>	
	    	
		</div> 
			
		</div>	
			<br/> <!-- 아래여백을 주기위함  -->
		<div class="save">
			<button type="button" class="saveBtn" onclick="save()">제출하기</button>&nbsp;
			<button type="button" class="saveBtn2" onclick="javascript:history.go(0);">취소</button>
		</div>
	
	<!--  --------------------------------------------------------------------------------------------------------------------                    -->	
			<br/><br/><br/> 
		<div><input type="hidden" name="approvalHidden" class="approvalHidden" value="0" /></div> <!-- 결재란 이미지위해 숨긴 div  -->	
	</div>
</body>
</html>




