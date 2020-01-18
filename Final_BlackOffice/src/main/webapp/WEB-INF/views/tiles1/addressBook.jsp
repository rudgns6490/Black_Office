<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String ctxPath = request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/resources/css/addressBook.css">

<script type="text/javascript">

	function goRegister() {
		var frm = document.registerPersonal
		frm.method = "POST";
		frm.action = "<%= ctxPath%>/registerPersonal.action";
		frm.submit();
	}
	
</script>

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
						<strong>개인주소</strong>
					</h4>
				</div>
				0 / 0
			</div>

			<div class="col-sm-12 displayline"
				style="margin: 0px; background-color: #ebf0f3; padding: 15px 0 15px 30px;">
				<button class="addressbutton">복사</button>
				<button class="addressbutton">공유</button>
				<button class="addressbutton">삭제</button>
			</div>

			<div class="col-sm-12 displayline textline bordertopline" style="height: 65px; padding: 15px 0 15px 30px;">
				<input class="inputboxcolor" type="text" placeholder="이름">
				<input class="inputboxcolor" type="text" placeholder="전화번호">
				<input class="inputboxcolor" type="text" placeholder="이메일">
				<input class="inputboxcolor" type="text" placeholder="회사">
				&nbsp;&nbsp;
				
				<button class="btn1" style="position: relative; top: -2px;" onclick="searchAlert()">검색</button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button style="position: relative; top: -2px;" type="button"
					class="btn1 btn2 btn-primary" onclick="writeAddress()"
					data-toggle="modal" data-target="#myModal" data-backdrop="static">추가</button>
					
					
			</div>

			<div class="col-sm-12 displayline textline bordertopline" style="height: 65px; padding: 15px 0 10px 20px;">
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
				<div class="table1" style="width: 250px;">이메일</div>
				<div class="table1" style="width: 150px;">전화번호</div>
				<div class="table1" style="width: 100px;">직책</div>
				<div class="table1" style="width: 150px;">부서</div>
				<div class="table1" style="width: 200px;">회사</div>
				<div class="table1" style="width: 200px;">그룹</div>
				<div class="table1" style="width: 100px;">수정</div>
			</div>


			<div class="col-sm-12" style="border-top: solid 1px #cccccc; border-bottom: solid 1px #cccccc; padding: 20px;">데이터가 없습니다</div>

		</div>
	</div>
	<!-- /.container-fluid -->
</div>
<!-- /.content-wrapper -->



<!-- 추가 버튼을 누를시의 Modal 창 -->
					
<!-- The Modal -->
  <div class="modal fade" id="myModal">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
          <h3 class="modal-title">개인 주소 추가</h3>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
			<div class="row content">
		        <div class="col-sm-12" style="padding: 5%;">
				  <fieldset>
				    <form name="registerPersonal" method="post">
				      <table class="table table-bordered">

				      	<tr>
				      	  <td style="background-color: #e0ebeb;">
				      	  	<label for="name">이름</label>
				      	  </td>
				      	  <td>
				      	    <input type="text" name="name" id="name" value="" maxlength="20" required autofocus autocomplete="off" style="width: 200px;"/>
				      	  </td>
				      	</tr>
				      	
				      	<tr>
				      	  <td style="background-color: #e0ebeb;">
				      	  	<label for="email">이메일</label>
				      	  </td>
				      	  <td>
				      	    <input type="text" name="email" id="email" value="" maxlength="30" required autofocus autocomplete="off" style="width: 200px;"/>
				      	  </td>
				      	</tr>
				      	
				      	<tr>
				      	  <td style="background-color: #e0ebeb;">
				      	  	<label for="phone">전화번호</label>
				      	  </td>
				      	  <td>
				      	    <input type="text" name="phone" id="phone" value="" maxlength="11" required style="width: 200px;"/>
				      	  </td>
				      	</tr>
				      	
				      	<tr>
				      	  <td style="background-color: #e0ebeb;">
				      	  	<label for="company">회사</label>
				      	  </td>
				      	  <td>
				      	    <input type="text" name="company" id="company" value="" maxlength="20" required style="width: 200px;"/>
				      	  </td>
				      	</tr>
				      	
				      	<tr>
				      	  <td style="background-color: #e0ebeb;">
				      	  	<label for="group">그룹</label>
				      	  </td>
				      	  <td>
				      	    <input type="text" name="group" id="group" value="" maxlength="20" required style="width: 200px;"/>
				      	  </td>
				      	</tr>
				      	
				      	<tr>
				      	  <td style="background-color: #e0ebeb;">
				      	  	<label for="positionname">직책</label>
				      	  </td>
				      	  <td>
				      	    <select name="positionname" id="positionname" class="form-control" style="width: 30%;">
				      	    	  <option></option>
								  <option value="1">사장</option>
								  <option value="2">이사</option>
								  <option value="3">부장</option>
								  <option value="3">차장</option>
								  <option>과장</option>
								  <option>대리</option>
								  <option>사원</option>
							</select>
				      	  </td>
				      	</tr>
				      	
				      	<tr>
				      	  <td style="background-color: #e0ebeb;">
				      	  	<label for="departmentname">부서</label>
				      	  </td>
				      	  <td>
				      	    <select name="departmentname" id="departmentname" class="form-control" style="width: 30%;">
				      	    	  <option></option> 
								  <option>인사팀</option>
								  <option>마케팅팀</option>
								  <option>개발1팀</option>
								  <option>개발2팀</option>
								  <option>영업팀</option>
							</select>
				      	  </td>
				      	</tr>
				      	
				      </table>
				      
				      <div style="float: right; margin-top: 10px; padding: 5%;">
				        <button type="button" id="register" class="btn btn-primary" onclick="goRegister();">등록</button>
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
					  </div>
				          
				    </form>
				  </fieldset>
		        </div>
			</div>
        </div> 
        
<!-- 추가 버튼을 누를시의 Modal 창 -->
