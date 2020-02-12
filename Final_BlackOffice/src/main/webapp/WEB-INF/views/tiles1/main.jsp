<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String ctxPath = request.getContextPath();
%>

<!-- highchart js script -->
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script src="https://code.highcharts.com/modules/accessibility.js"></script>


<script src="https://code.highcharts.com/modules/data.js"></script>
<script src="https://code.highcharts.com/modules/drilldown.js"></script>

<script src="https://code.highcharts.com/highcharts-3d.js"></script>
<script src="https://code.highcharts.com/modules/cylinder.js"></script>

<script src="https://code.highcharts.com/modules/sankey.js"></script>
<script src="https://code.highcharts.com/modules/organization.js"></script>

<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/resources/css/main.css">
<link href="<%= ctxPath %>/resources/css/admin/organizationChart2.css" rel="stylesheet">

<!-- 캘린더 부분 css js hjp 2020-01-31 -->
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/vendor/hjp/mainCalendar/core/main.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/vendor/hjp/mainCalendar/daygrid/main.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/vendor/hjp/mainCalendar/list/main.css" />
<script src="<%=request.getContextPath() %>/resources/vendor/hjp/mainCalendar/core/main.js"></script>
<script src="<%=request.getContextPath() %>/resources/vendor/hjp/mainCalendar/interaction/main.js"></script>
<script src="<%=request.getContextPath() %>/resources/vendor/hjp/mainCalendar/daygrid/main.js"></script>
<script src="<%=request.getContextPath() %>/resources/vendor/hjp/mainCalendar/list/main.js"></script>


<!-- 내부 css -->
<style type="text/css">
	#calendar > div.fc-view-container > div > table > thead > tr > td > div {margin-right: 0px !important; }
	#calendar > div.fc-view-container > div > table > tbody > tr > td > div {height: 100% !important;}
	.fc-button:hover { background: white !important; color: blue; border-color: white;}
	.fc-button {background: white !important; color: blue; border-color: white;}
	#calendar > div.fc-toolbar.fc-header-toolbar > div.fc-center {font-size: 2px !important; } 
	#calendar1 > div.fc-toolbar.fc-header-toolbar {font-size: 10px !important; }
	#calendar1 > div.fc-toolbar.fc-header-toolbar > div.fc-right > button {background: white !important; color: blue; border-color: white; border: solid 1px blue;}
	#calendar1 > div.fc-view-container > div > div {height: 500px !important;}
	
		
	/* 결재 테이블 select 해와서 메인에 show 해준 css----------------------------------------- */
	.reportList {background: white; border: 0px solid #e6e6e6; width: 100%;}
	
	.reportList_title {
      border-bottom: 1px solid #e6e6e6;
       background: #1c5691;
       font-weight: bold;
       font-size: 12pt;
       line-height: 15px;
       color: #fff;
       text-align: center;
   }
   
   .reportList_title th {padding: 15px 0;}
   .reportList_contents td {
      padding: 7px 0;
       border-bottom: 1px solid #e6e6e6;
       background: #fff;
       font-size: 14px;
       line-height: 17px;
       color: #777777;
       text-align: center;
   }
   
   /* -----------------------------------------------------------------------------------  */
	.title{cursor: pointer;}
	
</style>



<script type="text/javascript">
	$(document).ready(function() {
		
		$(".fc-center").addClass("row");

		// 시계함수 불러오기
		loopshowNowTime();

		// 통계선택
		$("#searchType").bind("change", function() {
			func_Ajax($(this).val());
		});
		
		
		// 결재 문서 상세보기 제목을 클릭하였을 경우
	      $(".title").click(function(){
	         var papersName = $(this).parent().find(".papersName").val();
	         if(papersName == "지출 결의서") {
	            var textnumber = ($(this).parent().find(".textnumber").val());
	            var employeename = ($(this).parent().find(".employeename").val());
	            $(".textnumber").val(textnumber);
	            $(".employeename").val(employeename);
	            
	            var frm = document.detailList;
	            frm.action="<%= request.getContextPath()%>/approver_ex.action";
	            frm.method="POST";
	            frm.submit();
	         } 
	         else {
	            var textnumber = ($(this).parent().find(".textnumber").val());
	            var employeename = ($(this).parent().find(".employeename").val());
	            $(".textnumber").val(textnumber);
	            $(".employeename").val(employeename);
	            
	            var frm = document.detailList;
	            frm.method="POST";
	            frm.action="<%= request.getContextPath()%>/approver_va.action";
	            frm.submit();
	         }
	      }); // end of $(".title").click(function(){})--------------------
		
		

	}); // end of ready(); ---------------------------------

	/////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////	[ 시계 ]
	/////////////////////////////////////////////////////////////////////////////////////////

	// 시계만들기
	function showNowTime() {

		var now = new Date();

		var strNow = now.getFullYear() + "-" + (now.getMonth() + 1) + "-"
				+ now.getDate();

		var hour = "";
		if (now.getHours() >= 12) {
			hour = "오후 " + (now.getHours() - 12);
		} else {
			hour = "오전 " + now.getHours();
		}

		var minute = "";
		if (now.getMinutes() < 10) {
			minute = "0" + now.getMinutes();
		} else {
			minute = now.getMinutes();
		}

		var second = "";
		if (now.getSeconds() < 10) {
			second = "0" + now.getSeconds();
		} else {
			second = now.getSeconds();
		}

		strNow += " " + hour + ":" + minute + ":" + second;

		$("#clock").html(
				"<span style='color:skyblue; font-weight:bold;'>" + strNow
						+ "</span>");

	}// end of function showNowTime() -----------------------------

	// 시계함수
	function loopshowNowTime() {
		showNowTime();

		var timejugi = 1000; // 시간을 1초 마다 자동 갱신하려고.

		setTimeout(function() {
			loopshowNowTime();
		}, timejugi);

	}// end of loopshowNowTime() --------------------------
	


	/////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////	[ 차트 - 통계 ]
	/////////////////////////////////////////////////////////////////////////////////////////

	// 통계 차트
	function func_Ajax(searchTypeVal) {

		switch (searchTypeVal) {
		case "":
			$("#chart_container").empty();
			break;
		case "organizationChart":
			// 조직도 차트
			Highcharts.chart('container', {
			    chart: {
			    	width: 600,
			        height: 500,
			        inverted: true
			    },

			    title: {
			        text: 'BlackOffice 조직도'        
			    },

			    accessibility: {
			        point: {
			            descriptionFormatter: function (point) {
			                var nodeName = point.toNode.name,
			                    nodeId = point.toNode.id,
			                    nodeDesc = nodeName === nodeId ? nodeName : nodeName + ', ' + nodeId,
			                    parentDesc = point.fromNode.id;
			                return point.index + '. ' + nodeDesc + ', reports to ' + parentDesc + '.';
			            }
			        }
			    },

			    series: [{
			        type: 'organization',
			        name: 'Highsoft',
			        keys: ['from', 'to'],
			        data: [
			            ['BlackOffice', 'Shareholder'],
			            ['Shareholder', '사장'],
			            ['사장'        , '인사과장'],
			            ['사장'        , '마케팅부장'],            
			            ['사장'        , '영업부장'],
			            ['사장'        , '개발1부장'],
			            ['사장'        , '개발2부장'],
			            ['마케팅부장'   , '마케팅팀'],
			            ['영업부장'		  , '영업팀'],
			            ['개발1부장'    , '개발1팀'],
			            ['개발2부장'    , '개발2팀']
			        ],
			        levels: [{
			            level: 0,
			            color: 'black',
			            dataLabels: {
			                color: 'white'
			            },
			            height: 25
			        }, {
			            level: 1,
			            color: 'silver',
			            dataLabels: {
			                color: 'black'
			            },
			            height: 25
			        }, {
			            level: 2,
			            color: '#980104'
			        }, {
			            level: 4,
			            color: '#359154'
			        }],
			        nodes: [{
			            id: 'Shareholder',
			            title: 'Shareholder',
			            name: '서영학',
			            image: 'https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fk.kakaocdn.net%2Fdn%2Fq07mZ%2FbtqBD1iZtGl%2FlNFCuRaABcgyMfTF9n3U00%2Fimg.jpg'
			        }, {
			            id: 'BlackOffice'
			        }, {
			            id: '사장',
			            title: '사장',
			            name: '김경훈',
			            image: 'https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fk.kakaocdn.net%2Fdn%2FAjTRu%2FbtqBEBdix7c%2FAKifrzNC6N9t0YYgQK0SKk%2Fimg.jpg'
			        }, {
			            id: '인사과장',
			            title: '인사과장',
			            name: '이병희',
			            color: '#007ad0',
			            image: 'https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fk.kakaocdn.net%2Fdn%2FvXwRY%2FbtqBFvKp060%2FhKXqPWa6ZGlAREy1o2qfu0%2Fimg.jpg',
			            column: 3,
			            offset: '75%',
			        }, {
			            id: '마케팅부장',
			            title: '부장',
			            name: '김용호',
			            column: 4,
			            image: 'https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fk.kakaocdn.net%2Fdn%2F9THhk%2FbtqBFN47GU2%2FvKqyNBcg9mJ6hRlZrek4w1%2Fimg.png',
			            layout: 'hanging'
			        }, {
			            id: '영업부장',
			            title: '부장',
			            name: '호정풍',
			            column: 4,
			            image: 'https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fk.kakaocdn.net%2Fdn%2Fbnb1o5%2FbtqBD2bcBf9%2FXv679yCqXOtRm8FZ9v0GQ0%2Fimg.jpg',
			            layout: 'hanging'
			        }, {
			            id: '개발1부장',
			            title: '부장',
			            name: '박시준',
			            column: 4,
			            image: 'https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fk.kakaocdn.net%2Fdn%2Fuwwpa%2FbtqBE1pey5Z%2FBDYmo1vSVJx77C7uEqkKlK%2Fimg.jpg',
			            layout: 'hanging'
			        }, {
			            id: '개발2부장',
			            title: '부장',
			            name: '임용준',
			            column: 4,
			            image: 'https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fk.kakaocdn.net%2Fdn%2Fet03NB%2FbtqBGpJxmEP%2FKGAWLwNTg18AYzhwPlCsQ1%2Fimg.jpg',
			            layout: 'hanging'
			        }, {
			            id: '마케팅',
			            name: '마케팅팀'
			        }, {
			            id: '영업팀',
			            name: '영업팀'
			        }, {
			            id: '개발1팀',
			            name: '개발1팀'
			        }, {
			            id: '개발2팀',
			            name: '개발2팀'
			        }],
			        colorByPoint: false,
			        color: '#007ad0',
			        dataLabels: {
			            color: 'white'
			        },
			        borderColor: 'white',
			        nodeWidth: 50
			    }],
			    tooltip: {
			        outside: true
			    },
			    exporting: {
			        allowHTML: true,
			        sourceWidth: 200,
			        sourceHeight: 800
			    }

			});
			break;

		case "departmentStatistics":
			// 부서별 인원통계	
			$.ajax({
						url : "/controller/departmentstatistics.action",
						dataType : "JSON",
						success : function(json) {

							$("#chart_container").empty();

							var deptnameArr = [];

							$.each(json, function(index, item) {
								deptnameArr.push({
									name : item.departmentname,
									y : Number(item.PERCENTAGE1),
									drilldown : item.departmentname
								});

							});// end of $.each(json, function(index, item)--------------

							var deptnameGenderArr = [];

							$
									.each(
											json,
											function(index2, item2) {

												$
														.ajax({
															url : "/controller/departmentstatistics.action",
															type : "GET",
															data : {
																"departmentname" : item2.departmentname
															},
															dataType : "JSON",
															success : function(
																	json2) {
																var subArr = [];

																$
																		.each(
																				json2,
																				function(
																						index3,
																						item3) {
																					subArr
																							.push([
																									item3.CNT1,
																									Number(item3.PERCENTAGE1) ]);
																				});// end of $.each(json2, function(index3, item3)------------

																deptnameGenderArr
																		.push({
																			name : item2.departmentname,
																			id : item2.departmentname,
																			data : subArr
																		});

															},
															error : function(
																	request,
																	status,
																	error) {
																alert("code: "
																		+ request.status
																		+ "\n"
																		+ "message: "
																		+ request.responseText
																		+ "\n"
																		+ "error: "
																		+ error);
															}
														});

											});// end of  $.each(json, function(index2, item2)---------------

							////////////////
							// ▼ 차트 넣기 // 막대그래프
							////////////////
							Highcharts
									.chart(
											'chart_container',
											{
												chart : {
													type : 'column'
												},
												title : {
													text : '부서별 인원통계'
												},
												subtitle : {
													text : '그래프 클릭시 데이터 색 변환'
												},
												accessibility : {
													announceNewData : {
														enabled : true
													}
												},
												xAxis : {
													type : 'category'
												},
												yAxis : {
													title : {
														text : '인원비율(%)'
													}

												},
												legend : {
													enabled : false
												},
												plotOptions : {
													series : {
														borderWidth : 0,
														dataLabels : {
															enabled : true,
															format : '{point.y:.1f}%'
														}
													}
												},

												tooltip : {
													headerFormat : '<span style="font-size:11px">{series.name}</span><br>',
													pointFormat : '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
												},

												series : [ {
													name : "부서명",
													colorByPoint : true,
													data : deptnameArr
												// *** 위에서 구한 값을 대입시킴 ***
												} ],
												drilldown : {
													series : deptnameGenderArr
												// *** 위에서 구한 값을 대입시킴 ***
												}
											});
							////////////////
							// ▲ 차트 넣기 //
							////////////////	
						},
						error : function(request, status, error) {
							alert("code: " + request.status + "\n"
									+ "message: " + request.responseText + "\n"
									+ "error: " + error);
						}
					});
			break;

		case "positionStatistics":
			// 직위별 인원통계	
			$.ajax({
						url : "/controller/positionstatistics.action",
						dataType : "JSON",
						success : function(json) {
							$("#chart_container").empty();

							var resultArr = [];

							for (var i = 0; i < json.length; i++) {

								var obj = {
									name : json[i].POSITIONNAME,
									y : Number(json[i].PERCENTAGE)
								};

								resultArr.push(obj); // 배열속에 객체를 넣기 
							}

							////////////////
							// ▼ 차트 넣기 // 원그래프
							////////////////
							Highcharts
									.chart(
											'chart_container',
											{
												chart : {
													plotBackgroundColor : null,
													plotBorderWidth : null,
													plotShadow : false,
													type : 'pie'
												},
												title : {
													text : '직위별 인원통계'
												},
												subtitle : {
													text : '사장 : 1 / 이사 : 2 / 부장 : 3 / 차장 : 4 / 과장 : 5 / 대리 : 6 / 사원 : 7'
												},
												tooltip : {
													pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
												},
												accessibility : {
													point : {
														valueSuffix : '%'
													}
												},
												plotOptions : {
													pie : {
														allowPointSelect : true,
														cursor : 'pointer',
														dataLabels : {
															enabled : true,
															format : '<b>{point.name}</b>: {point.percentage:.1f} %'
														}
													}
												},
												series : [ {
													name : '인원비율',
													colorByPoint : true,
													data : resultArr
												} ]
											});
							////////////////
							// ▲ 차트 넣기 //
							////////////////
						},
						error : function(request, status, error) {
							alert("code: " + request.status + "\n"
									+ "message: " + request.responseText + "\n"
									+ "error: " + error);
						}
					});
			break;

		default:
			alert("아무데이터도 넘어오지 않았습니다.");
			alert("선택한 값 : " + searchTypeVal);
			break;
		}

	}// end of function func_Ajax(searchTypeVal)-----
	

	////////////////////////////////////////////////////////
	// 일정
	
	// 캘린더 부분함수 추가  2020-01-31 hjp 
  	document.addEventListener('DOMContentLoaded', function() {
	    var calendarEl = document.getElementById('calendar');

	    var calendar = new FullCalendar.Calendar(calendarEl, {
	      plugins: [ 'interaction', 'dayGrid' ],
	      locale: 'ko',
	      firstDay: 1,
	      editable: true,
	      eventLimit: true, // allow "more" link when too many events
	    });

	    calendar.render();
	  });  // 캘린더 함수 end -------------------------------------------
	  
	
	  
	// 메인메뉴 개일일정 
	document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar1');
    var myarr = [];
    
    $.ajax({
	      type: "get",
	      dataType : "JSON", 
	      url: "/controller/mainScheduleJSONList.action",
	      success: function (response) {   
	    	  
	    		  $.each(response,function(key, value) {
	    			  
		    		  myarr.push({title : value.title,
	    				          start : value.start});
	    		  });
	    		  
	    		  var calendar = new FullCalendar.Calendar(calendarEl, {
	    			    
	    		      plugins: [ 'list' ],

	    		      header: {
	    		        left: 'prev,next',
	    		        center: 'title',
	    		        right: 'today'
	    		      },
	    		      locale: 'ko',
	    		      defaultView: 'listWeek',
	    		      navLinks: true, // can click day/week names to navigate views
	    		      editable: true,
	    		      eventLimit: true, // allow "more" link when too many events
	    		      events: myarr  
	    		    	  
	    		    });

	    		    calendar.render();
	      }, 
	      error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
	    });
    
	});
	
	//////////////////////메인 공지사항에서 제목을 클릭 하였을때 세부 내용으로 이동
	function goView(seq) {
		/* 게시물을 클릭 했을때 게시물 내용 받아오고 세부 게시물로 이동  */
		var frm = document.goViewFrm;
		frm.seq.value = seq;
		
		frm.method = "GET";
		frm.action = "noticeDetailBoard.action";
		frm.submit();
	}

	function goDeptView(seq) {
		/* 게시물을 클릭 했을때 게시물 내용 받아오고 세부 게시물로 이동  */
		var frm = document.goDeptViewFrm;
		frm.seq.value = seq;
		
		frm.method = "GET";
		frm.action = "deptDetailBoard.action";
		frm.submit();
	}

	
</script>


<div id="content-wrapper" style="padding: 0px;">
	<div class="container-fluid text-center">

		<!-- 메인 본문 내용 소스 작업은 여기서 수정 2020/01/03 kkh -->
		<div class="row content"
			style="padding: 20px; background-color: #ccccc;">

			<!-- 왼쪽 큰박스 -->
			<div class="col-sm-3" style="padding: 20px;">


				<!-- 왼쪽 위 -->
				<div class="maincontainer">
					<div id="location1" class="contentMenu">
						<span style="font-size: small;">내정보</span>
					</div>
					
					<!-- 내용 -->
					<div>
						<!-- Login_Thumbnail-->
						<c:if test="${sessionScope.loginuser != null }">
								<img style="width: 80px; height: 80px; border-radius: 50%;"
									src="<%= request.getContextPath() %>/resources/files/${loginuser.thumbnailFileName}">
						</c:if>
						<c:if test="${sessionScope.loginuser == null }">
								<img src="<%=ctxPath%>/resources/images/myinforpicture.PNG">
						</c:if> <!-- Login_Thumbnail-->

						<br> <br> <strong style="color: blue;">${loginuser.name}</strong>님
						출근했어요.<br>
					</div>

					<br>
					<div style="margin: 0 auto;" align="center">
						<img style="width: 30px; height: 30px;"
							src="<%=ctxPath%>/resources/images/time.PNG"> 현재시각 :
						<div id="clock" style="display: inline;"></div>
					</div>

					<hr>
					<!-- 라인만들기 -->
					<div class="col-sm-12">
						<div class="col-sm-10 inbl">
							<span style="color: blue;">부서 : </span>
							<c:if test="${loginuser.fk_departmentno == 1}">인사팀</c:if>
							<c:if test="${loginuser.fk_departmentno == 2}">마캐팅팀</c:if>
							<c:if test="${loginuser.fk_departmentno == 3}">개발1팀</c:if>
							<c:if test="${loginuser.fk_departmentno == 4}">개발2팀</c:if>
							<c:if test="${loginuser.fk_departmentno == 5}">영업팀</c:if>
						</div>
						<div class="col-sm-10 inbl">
							<span style="color: red;">직급 : </span>
							<c:if test="${loginuser.fk_positionno == 1}">사장</c:if>
							<c:if test="${loginuser.fk_positionno == 2}">이사</c:if>
							<c:if test="${loginuser.fk_positionno == 3}">부장</c:if>
							<c:if test="${loginuser.fk_positionno == 4}">차장</c:if>
							<c:if test="${loginuser.fk_positionno == 5}">과장</c:if>
							<c:if test="${loginuser.fk_positionno == 6}">대리</c:if>
							<c:if test="${loginuser.fk_positionno == 7}">사원</c:if>
						</div>
					</div>
					<hr>
					<!-- 라인만들기 -->
					<div style="padding: 0px; margin: 0px;">
					</div>

					<a style="position: relative; top: -10px;" href="<%=ctxPath%>/logout.action"><button class="buttons bluebutton">로그아웃</button></a>

				</div>


				<!-- 왼쪽 아래 -->
				<div class="maincontainer" style="margin-top: 50px; height: 450px;">
				
					<div id="location2" class="contentMenu">
						<a style="color:white;" href="http://localhost:9090/controller/internetchat.action">
							<span style="font-size: small;">채팅</span>
						</a>
					</div>
				
					<!-- 내용 -->
					<!-- ▼ 채팅 -->
					<div id="tlkio" data-channel="BlackOffice" data-theme="theme--day" style="width: 100%; height: 400px;"></div><script async src="http://tlk.io/embed.js" type="text/javascript"></script>
					<!-- ▲ 채팅 -->
				</div>
				
				
				<!-- 왼쪽 맨 아래 -->
				<div class="maincontainer" style="margin-top: 50px; height: 550px;">
					
					<div id="location2" class="contentMenu">
						<a style="color:white;" href="http://localhost:9090/controller/admintotaldepartment.action">
							<span style="font-size: small;">차트</span>
						</a>
					</div>
					
					<!-- 내용 -->
					<form name="searchFrm" style="margin-bottom: 70px;">
						<select name="searchType" id="searchType" style="height: 25px;">
							<option value="">선택</option>
							<option value="organizationChart">조직도</option>
							<option value="departmentStatistics">부서별 인원통계</option>
							<option value="positionStatistics">직위별 인원통계</option>
						</select>
					</form>
					<br>
					<!-- ▼ 차트그리기 -->
					<div id="chart_container" style="position: relative; top: -80px; min-width: 150px; height: 450px; max-width: 350px; margin: 0 auto"></div>
					<!-- ▲ 차트그리기 -->
				</div>
				
			</div>


			<!-- 중간 큰 박스 -->
			<div class="col-sm-6" style="padding: 20px;">


				<!-- 중간에서 위 -->
				<div class="maincontainer" style="text-align: left;">
					
					<div class="maincontainer" style="text-align: left; ">
	        		<div class="contentMenu inbl">
	        			<a style="color:white;" href="http://localhost:9090/controller/receive_payment_archive.action">
	        				<span style="font-size: small;">업무공유<br>전자결제</span>
	        			</a>
	        		</div>
	        		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	           		<div class="inbl" style="margin-left: 70px; margin-top: 10px;">
		           		<span style="font-size: 18pt; color: #007bff;">결재 문서</span>  
	           		</div>
	           		<hr> <!-- 라인만들기 -->
	           		<br>
		           			<div class="col-sm-12 reportList" style="margin: 0 auto;">   
                          <!-- 결재 문서 테이블 가져온것 --------------------------------------------  -->
                              <form name="detailList">
                              <table>
                                 <thead>
                                 <tr class="reportList_title">
                                    <th style="width: 10%;">문서번호</th>
                                    <th style="width: 20%;">제목</th>
                                    <th style="width: 5%;">기안자</th>
                                    <th style="width: 15%;">문서분류</th>
                                 </tr>
                              </thead>
                                 
                                 <tbody class="reportList_contents">
                                    <tr>
                                       <c:if test="${not empty map}">
                                          <c:forEach var="documentMap" items="${map}" varStatus="status">
                                             <tr>
                                                <td>
                                                   ${documentMap.textnumber}
                                             <input type="hidden" class="textnumber" name="textnumber" value="${documentMap.textnumber}" />
                                                </td>
                                                <td class="title" style="text-align: left; color: #2276BB;">
                                                   ${documentMap.title}
                                                </td>
                                                <td>
                                                   ${documentMap.employeename}
                                              <input type="hidden" class="employeename" name="employeename" value="${documentMap.employeename}"/>
                                                </td>
                                                <td>
                                                   ${documentMap.papersname}
                                             <input type="hidden" class="papersName papersName${status.index}" value="${documentMap.papersname}" />
                                                </td>
                                             </tr>   
                                          </c:forEach>   
                                       </c:if>   
                                       <c:if test="${empty map}">
                                          <td colspan="4">최근 작성된 문서가 없습니다.</td>
                                       </c:if>
                                    </tr>
                                 </tbody>
                              </table>
                              </form>   
                           </div>	
				</div>
					
					
				</div>


				<!-- 중간에서 중간 -->
				<div class="col-sm-12" style="margin: 50px 0 50px 0;">
					<div class="row content">
						<div class="col-sm-12 dvideLine maincontainer" style="height: 200px; padding: 0 20px 0 0;">
							<div class="contentMenu">
								<a style="color:white;" href="http://localhost:9090/controller/noticeBoardList.action">
									<span style="font-size: small;">공지게시판</span>
								</a>
							</div>	
								<!-- 내용 -->
								<div>
									<table id="myTable" class="table table-hover" style="position: relative; left: 10px;">	
										
										<thead style="background-color: navy; color: #fff;" class="boardHeader" >
									    <tr>
										    <th id="boardHeaderTitle" width="500" >제목</th>
										    <th id="boardHeaderWriter" width="200">작성자</th>
										    <th id="boardHeaderWriteDate" width="200">작성일</th>
										</tr>
								  	</thead>
										<c:forEach var="boardvo" items="${mainNoticeBoardList}" varStatus="status">
											<tr>
												<%-- <td align="center">${boardvo.seq}</td> --%>
												<td align="center"> 
												 <span class="subject" onclick="goView('${boardvo.seq}');">${boardvo.subject}</span>
												</td>
												<td align="center">${boardvo.name}</td>
												<td align="center">${boardvo.regDate}</td>
											
											</tr>
										</c:forEach>
									</table>
									<!-- 제목을 클릭하면 게시물로 이동시킨다 -->
									<form name="goViewFrm">
									<input type="hidden" name="seq" />
									<input type="hidden" name="gobackURL" value="${gobackURL}" /> 
									</form>
		
								</div>
								
						</div>
					</div>
				</div>


				<!-- 중간에서 아래 -->
				<div class="col-sm-12" style="margin: 50px 0 50px 0;">
					<div class="row content">
						<div class="col-sm-12 dvideLine maincontainer" style="height: 200px; padding: 0 20px 0 0;">
							<div class="contentMenu">
								<a style="color:white;" href="http://localhost:9090/controller/deptBoard.action">
									<span style="font-size: small;">업무게시판</span>
								</a>
							</div>
							
							<!-- 내용 -->
							<div>
								<table id="myTable" class="table table-hover" style="position: relative; left: 10px;">	
									
									<thead style="background-color: navy; color: #fff;" class="boardHeader" >
								    <tr>
									    <th id="boardHeaderTitle" width="500" >제목</th>
									    <th id="boardHeaderWriter" width="200">작성자</th>
									    <th id="boardHeaderWriteDate" width="200">작성일</th>
									</tr>
							  	</thead>
									<c:forEach var="deptboardvo" items="${mainDeptBoardList}" varStatus="status">
										<tr>
											<%-- <td align="center">${boardvo.seq}</td> --%>
											<td align="center"> 
											 <span class="subject" onclick="goDeptView('${deptboardvo.seq}');">${deptboardvo.subject}</span>
											</td>
											<td align="center">${deptboardvo.name}</td>
											<td align="center">${deptboardvo.regDate}</td>
										
										</tr>
									</c:forEach>
								</table>
								<!-- 제목을 클릭하면 게시물로 이동시킨다 -->
								<form name="goDeptViewFrm">
								<input type="hidden" name="seq" />
								<input type="hidden" name="gobackURL" value="${gobackURL}" /> 
								</form>
	
							</div>
						</div>
					</div>
				</div>
				
				
				<!-- 중간에서 맨 아래 -->
				<div class="col-sm-12" style="margin: 50px 0 50px 0;">
					<div class="row content">
						<div class="col-sm-12 dvideLine maincontainer" style="height: 550px; padding: 0 20px 0 0;">
							
							<div class="contentMenu">
								<a style="color:white;" href="http://localhost:9090/controller/organizationChart.action">
									<span style="font-size: small;">조직도</span>
								</a>
							</div>
							
							<!-- 내용 -->
							<div style="margin: 0px auto;"> 
								<div id="container" style="width: 800px; margin: 0px auto;"></div>
							</div>
							
						</div>
					</div>
				</div>


			</div>
	
	
			<!-- 오른쪽 큰박스 -->
			<div class="col-sm-3" style="padding: 20px;">


				<!-- 오른쪽 위 -->
				<div class="maincontainer" style="height: 725px;">
					
					<div class="contentMenu">
						<a style="color:white;" href="http://localhost:9090/controller/individualSchedule.action">
							<span style="font-size: small;">달력</span>
						</a>	
					</div>
					
					<!-- 내용 -->
					<!-- 캘린더 부분 2020-01-31 hjp -->
		           	<div id='calendar' class="col-sm-12" style="height: 100%"></div>
			
					
				</div>


				<!-- 오른쪽 아래 -->
				<div class="maincontainer" style="height: 725px; margin-top: 50px;">
					<div class="row-sm-3">
					
						
						<div class="contentMenu">
							<a style="color:white;" href="http://localhost:9090/controller/individualSchedule.action">
								<span style="font-size: small;">등록된일정</span>
							</a>
						</div>
						
						<!-- 내용 -->
						<div id='calendar1' class="col-sm-12" style="height: 100%"></div>
						
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

