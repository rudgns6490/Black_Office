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
	/* ----------------------------------------------------------------------------------------------  */
	
	.headertable { margin-right: 30px; }
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
	.hdtd { width: 120px; text-align: center; font-size: 17pt; font-weight: normal; color:#595959;}
	.approvalImg { width: 100px; text-align: center; font-size: 17pt; font-weight: normal; color:#595959;}
	.headertable {float: right; margin-top:20px;}
	
	#ptLineAdd {  clear: both; float: right; display:inline-block; margin-top:20px; width:300px; height: 35px; font-size: 15pt; }
	.approval th{ background-color: #e0ebeb; font-size: 13pt; font-weight: bold; }
	.approvalImg:hover { cursor: pointer; }
	.minus:hover { cursor: pointer; }
	
	.titleLine { clear: both; border: solid 0px #639c9c; height: 35px; }  /* 야매로 줄바꿈 해주기위한것 */
	.title2, .title3 { clear: both; border: solid 1px #639c9c; border-bottom: none;  width: 100%;}
	.title2 th,td { border: solid 1px #639c9c; border-collapse: collapse; height: 40px; }
	.title2Td { width: 80%; } 
	.title2Td1 { background-color: #e0ebeb; text-align: center; font-size: 15pt; color: #333333; }
	.iframeDiv { width: 100%; height: 500px; } 
	
	/* ----------------------------------------------------------------------------------------------  */
	
	.title {
		background: url(<%= request.getContextPath() %>/resources/images/체크이미지.png) no-repeat 3px center; 
		padding-left: 20px; 
	}
	.payment_money {
		background: url(<%= request.getContextPath() %>/resources/images/체크이미지.png) no-repeat 3px center; 
		padding-left: 20px;
	}
	
	
	#tableRadio, #tableRadio1, .radioSpan { position: relative; top: 4.5px; } 
	
	
	/* --- 아래 제출버튼 --------------------------------------------------------------------------------  */
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

	//전역변수
	var obj = [];

	$(document).ready(function(){
		
		$(".modaladdTd").append("");
		$(".payment_money").focus();
		$("input:radio[id=tableRadio]").prop("checked",true);
		
		$('#expenditureday').datepicker({
	         showOn: "both",                                       // 달력을 표시할 타이밍 (both: focus or button)
	         buttonImage: "<%= request.getContextPath()%>/resources/images/cal.jpg",    // 버튼 이미지
	         buttonImageOnly : true,                               // 버튼 이미지만 표시할지 여부
	         buttonText: "날짜선택",                                  // 버튼의 대체 텍스트
	         dateFormat: "yy-mm-dd",                               // 날짜의 형식
	         changeMonth: true,                                    // 월을 이동하기 위한 선택상자 표시여부
	         onClose: function( selectedDate ) {    
	            $("#toDate").datepicker( "option", "minDate", selectedDate );
	         }                
	     });

		<%-- === #153. 스마트에디터 구현 시작 === --%>
	    //스마트에디터 프레임생성
	    nhn.husky.EZCreator.createInIFrame({
	        oAppRef: obj,
	        elPlaceHolder: "content",
	        sSkinURI: "<%= ctxPath%>/resources/smarteditor/SmartEditor2Skin.html",
	        htParams : {
	            // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
	            bUseToolbar : true,            
	            // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
	            bUseVerticalResizer : true,    
	            // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
	            bUseModeChanger : true,
	        }
	    });
		<%-- === 스마트에디터 구현 끝 === --%>
		<%-- === 스마트에디터 구현 시작 === --%>	
		//id가 content인 textarea에 에디터에서 대입
    	// obj.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
    	<%-- === 스마트에디터 구현 끝 === --%>
		
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
							html += "<tr class='modalAddEmployees' class='close'>"
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
			var random = Math.floor(Math.random()*100);
			
			if(hdtdLength < 3){
				
				valHtml0 += "<th class='hdth hdth"+random+" th'>결재자</th>";
				$(".modaladdTh").append(valHtml0);
				
				
				
				<%-- valHtml1 += "<td class='hdtd hdtd"+hdtdLength+" td'>"+name+"&nbsp;<img class='minus' style='width:20px; height:20px; position: relative; top: -2.5px;'  src='<%= ctxPath%>/resources/images/빼기이미지.png'></td> "; --%>
				valHtml1 += "<td class='hdtd hdtd"+random+" td' >"+name+"&nbsp;<img class='minus"+random+" minus' style='width:20px; height:20px; position: relative; top: -2.5px;'  src='<%= ctxPath%>/resources/images/빼기이미지.png'></td> ";
				$(".modaladdTd").append(valHtml1); 
				 
				
				
				valHidden += "<input type='hidden' class='approverhidden approverhidden"+random+"' name='approverhidden"+hdtdLength+"' value='"+employeeno+"' />";
				valHidden += "<input type='hidden' style='border:none; width:70px;' class='approverName approverName"+random+"' name='approver"+hdtdLength+"' value='"+name+"' readonly/>";
				$("#formHidden").append(valHidden);
							
				
				
				valHtml2 = "<td class='approvalImg approvalImg"+random+"'></td>";
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
					console.log(approvalHtml.substring(5,7));
			    	var count = approvalHtml.substring(5,7);
			    
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
		
		<%-- === 스마트에디터 구현 시작 === --%>	
		//id가 content인 textarea에 에디터에서 대입
        obj.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
	    <%-- === 스마트에디터 구현 끝 === --%> 
			   
		var modaladdTd = $(".modaladdTd").text();
		var title = $(".title").val();
		var contentval = $("#content").val();
		var payment_money = $(".payment_money").val();
		
		if(modaladdTd.trim() == "") {
			alert("결재자를 선택해 주세요");
			return;
		} 
		else if(payment_money.trim() == "") {
			alert("금액을 입력해 주세요");
			return;
		}
		else if(title.trim() == "") {
			alert("제목을 입력해 주세요");
			return;
		}
		
		
	
	<%-- === 스마트에디터 구현 시작 === --%>
		// 스마트에디터 사용시 무의미하게 생기는 p태그 제거하기전에 먼저 유효성 검사를 하도록 한다.
        // 글내용 유효성 검사 
        else if(contentval == "" || contentval == "<p>&nbsp;</p>") {
        	alert("글내용을 입력하세요!!");
        	return;        
        }
     // 스마트에디터 사용시 무의미하게 생기는 p태그 제거하기
        contentval = $("#content").val().replace(/<p><br><\/p>/gi, "<br>"); //<p><br></p> -> <br>로 변환
    /*    
              대상문자열.replace(/찾을 문자열/gi, "변경할 문자열");
        ==> 여기서 꼭 알아야 될 점은 나누기(/)표시안에 넣는 찾을 문자열의 따옴표는 없어야 한다는 점입니다. 
                     그리고 뒤의 gi는 다음을 의미합니다.

        	g : 전체 모든 문자열을 변경 global
        	i : 영문 대소문자를 무시, 모두 일치하는 패턴 검색 ignore
    */    
        contentval = contentval.replace(/<\/p><p>/gi, "<br>"); //</p><p> -> <br>로 변환  
        contentval = contentval.replace(/(<\/p><br>|<p><br>)/gi, "<br><br>"); //</p><br>, <p><br> -> <br><br>로 변환
        contentval = contentval.replace(/(<p>|<\/p>)/gi, ""); //<p> 또는 </p> 모두 제거시
    
        $("#content").val(contentval);
     // alert(contentval);
	
        
	<%-- === 스마트에디터 구현 끝 === --%>
		
			var td = $(".modaladdTd").find('td').text();
		//	alert(td);
			var count = $(".th").length;
			$("input:hidden[id = tdCount]").val(count);
		//	alert($("#tdCount").val());
		
			frm = document.submitFrm;
			frm.method = "POST";
			frm.action = "<%= ctxPath%>/addreport.action";
			frm.submit();
	}
	
	////////////////////////////////////////////////////
	

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
	    		
	    		<form name="submitFrm"  enctype="multipart/form-data">
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
	    		
	    		
	    		<!-- <div id="ptLineAdd">
	    			<div><button type="button" style="color: #333333; border-radius: 5px;">결재라인 추가</button></div><br/>
	    		</div> -->
	    		

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
											<tr class="add_result_List_title">
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
		 					<td class="title2Td">&nbsp;<input name="writeday" readonly style="border: none;" value="${sysYear}" /></td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">제출자</td>
		 					<td class="title2Td">
		 						<input name="employeename" value="${requestScope.userName}" readonly style="border: none; margin-left: 10px;" />
		 						<input type="hidden" id="" name="fk_employeeno" value="${requestScope.employeeno}" readonly style="border: none;" />
		 					</td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">소속</td>
		 					<td class="title2Td">
		 						<input id="department" name="departmentname" value="${requestScope.deptName}" readonly style="border: none; margin-left: 10px;" />
		 					</td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">지출 선택</td>
		 					<td class="title2Td">
		 						&nbsp;<input type="radio" name="expendituremode" id="tableRadio" value="0" />&nbsp;
		 						<span><label for="tableRadio" class="radioSpan">개인지출 경비</label></span>&nbsp;&nbsp;
		 						
		 						<input type="radio" name="expendituremode" id="tableRadio1" value="1"/>&nbsp;
		 						<span><label for="tableRadio1" class="radioSpan">법인지출 경비</label></span>
		 					</td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">지출일자</td>
		 					<td class="title2Td">
		 						&nbsp;<input type="text" id="expenditureday" name="expenditureday" />
		 					</td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">지출금액</td> 
		 					<td class="title2Td">
		 						&nbsp;<input type="text" class="payment_money" name="payment_money" style="width: 15%;" placeholder=" ex) 2,000,000" />&nbsp;원
		 					</td>
		 				</tr>
		 				
		 				<tr>
		 					<td class="title2Td1">제목</td>
		 					<td class="title2Td">
		 						&nbsp;<input type="text" name="title" class="title" style="width: 99%;" />
		 					</td>
		 				</tr>
		 			 	
		 				<tr>
		 					<td class="title2Td1">내용</td>
		 					<td>
		 						<div >
	    							<textarea id="content" name="content" style="width: 100%; height: 400px;"></textarea>
	    						</div>
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
				</div>

		 		</form>
		 		<br/>
	<!--  --------------------------------------------------------------------------------------------------------------------                    -->	 		
	    	
		
	    	</div>	
		</div> 
		</div>	
			<br/> <!-- 아래여백을 주기위함  -->
		<div class="save">
			<button type="button" class="saveBtn"  onclick="save()">제출하기</button>&nbsp;
			<button type="button" class="saveBtn2" onclick="javascript:history.go(0);">취소</button>&nbsp;
		</div>
	
	<!--  --------------------------------------------------------------------------------------------------------------------                    -->	
			<br/><br/><br/> 

		<!-- <div><input type="hidden" name="approvalHidden" class="approvalHidden3" value="0" /></div> --> <!-- 결재란 이미지위해 숨긴 div  -->
		<div class="hiddenval"></div> <!-- 결재란 이미지위해 숨긴 div  -->
		
	</div>
</body>
</html>










