<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String ctxPath = request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/resources/css/addressOpenBook.css">

<div id="content-wrapper" style="padding: 0px;">
	<div class="container-fluid text-center">

		<!-- 메인 본문 내용 소스 작업은 여기서 수정 2020/01/03 kkh -->
		<div class="row content"
			style="padding: 20px; background-color: #ccccc;">
			<div class="col-sm-12 displayline textline"
				style="height: 70px; background-color: #ebf0f3; padding: 30px;">
				<a href=""><img
					style="width: 30px; height: 30px; position: relative; top: -5px; left:"
					src="<%=ctxPath%>/resources/images/addressbook.PNG"></a>
				<div class="displayline">
					<h4>
						<strong>공용주소</strong>
					</h4>
				</div>
				0 / 0
			</div>

			<div class="col-sm-12 displayline"
				style="margin: 0px; background-color: #ebf0f3; padding: 15px 0 15px 30px;">
				<button class="addressbutton">이동</button>
				<button class="addressbutton">복사</button>
				<button class="addressbutton">삭제</button>
				<button class="addressbutton">메일</button>
			</div>

			<div class="col-sm-12 displayline textline"
				style="height: 65px; border-top: solid 1px #cccccc;; padding: 15px 0 15px 30px;">

				<input class="inputboxcolor" type="text" placeholder="이름"> 
				<input class="inputboxcolor" type="text" placeholder="전화번호">
				<input class="inputboxcolor" type="text" placeholder="이메일">
				<input class="inputboxcolor" type="text" placeholder="회사"> 
				&nbsp;&nbsp;
			</div>

			<div class="col-sm-12 displayline textline"
				style="height: 65px; border-top: solid 1px #cccccc; padding: 15px 0 10px 20px;">
				<div class="displayline">페이지당 줄수</div>
				<select>
					<option>10</option>
					<option>20</option>
					<option>30</option>
					<option>All</option>
				</select>

			</div>

			
			<div class="col-sm-12 bordertopline" style="padding: 15px 0px; " >
				<div class="displayline checkboxline">
					<input type="checkbox">&nbsp;&nbsp;&nbsp;
				</div>
				<div class="table1" style="width: 100px;">이름</div>
				<div class="table1" style="width: 400px;">이메일</div>
				<div class="table1" style="width: 250px;">전화번호</div>
				<div class="table1" style="width: 100px;">직책</div>
				<div class="table1" style="width: 200px;">부서</div>
				<div class="table1" style="width: 200px;">회사</div>
				<div class="table1" style="width: 200px;">그룹</div>
				<div class="table1" style="width: 150px; text-align: center;">수정</div>
			</div>
			
			
			<!-- 테이블 내용 -->
			<div class="col-sm-12 borderTopAndBottomline" style="padding: 15px 0px; " >
				<div class="displayline checkboxline">
					<input type="checkbox">&nbsp;&nbsp;&nbsp;
				</div>
				<div class="table1" style="width: 100px;">관리자</div>
				<div class="table1" style="width: 400px;">demo@demo.whoisg.kr</div>
				<div class="table1" style="width: 250px;">00001</div>
				<div class="table1" style="width: 100px;">관리자</div>
				<div class="table1" style="width: 200px;">G2WorksDemo</div>
				<div class="table1" style="width: 200px;">G2WorksDemo</div>
				<div class="table1" style="width: 200px;">직원주소록</div>
				<div class="table1" style="width: 150px; text-align: center;"><button class="btn1">수정</button></div>
			</div>
			
			
			
		</div>
	</div>
	<!-- /.container-fluid -->
</div>
<!-- /.content-wrapper -->