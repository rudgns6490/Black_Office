<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%
	String ctxPath = request.getContextPath();
%>
	
  <link href="<%= ctxPath %>/resources/css/noticeBoard.css" rel="stylesheet">
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>


<script> // 로딩된 테이블내에서 이름으로 검색
/* function myFunction() {
  var input, filter, table, tr, td, i, txtValue;
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  table = document.getElementById("myTable");
  tr = table.getElementsByTagName("tr");
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[1]; ///////[0]글번호 [1]제목 [2]작성자
    if (td) {
      txtValue = td.textContent || td.innerText;
      if (txtValue.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }       
  }
} */


</script>
<div id="content-wrapper" style="padding-top: 0;">
<div class="container-fluid text-center">

<div class="row content">
<div class="col-sm-12" style="padding: 5%;">
		  <fieldset>
		    <legend>공지사항</legend>
			<form name="noticeBoardSearch" method="post">
		      <table class="table table-bordered">
		      	<tr>
		      	  <td class="table-active" style="width: 15%;">검색조건</td>
		      	  <td>
		      	    <select class="form-control" style="width: 15%; display: inline-block;">
	  				  <option>제목</option>
	  				  <option>내용</option>
	  				  <option>작성자</option>
	  				</select>
					<input type="text"  name="searchWord" id="searchWord" value="" maxlength="20" required style="width: 30%; display: inline-block;" />
					<button type="button" class="btn btn-primary" onclick="goSearch();" style="width: 10%; display: inline-block;">검색</button>
		      	  <!-- <div class="row">
					<div class="col-xs-1"></div>
					<input type="text" id="myInput" onkeyup="myFunction()" placeholder="검색" title="이름으로 검색" />
					</div>	 -->
		      	  </td>
		      	</tr>
		      </table>
		    </form>
		  </fieldset> <!-- 제목, 검색조건, 검색버튼 -->

		<table id="myTable" class="table table-hover">	
		
			<thead style="background-color: navy; color: #fff;" class="boardHeader" >
			    <tr>
				    <th id="boardHeaderNo">No.</th>
				    <th id="boardHeaderTitle">제목</th>
				    <th id="boardHeaderWriter">작성자</th>
				    <th id="boardHeaderWriteDate">작성일</th>
				    <th id="boardHeaderReadCount">조회수</th>
				<!--<th id="boardHeaderComment">댓글수</th> //공지사항에서는 비활성
				    <th id="boardHeaderAddFile">파일</th>  //공지사항에서는 비활성 -->
			    </tr>
		  	</thead>
			<tbody id="boardBody">
				<tr>
				    <td id="boardNo">1</td>
				    <td id="boardTitle">단합대회</td>
				    <td id="boardWriter">김부장</td>
				    <td id="boardWriteDate">2019-12-10</td>
				    <td id="boardReadCount">30</td>
				</tr>
				<tr>
				    <td id="boardNo">2</td>
				    <td id="boardTitle">종무식일정</td>
				    <td id="boardWriter">이사장</td>
				    <td id="boardWriteDate">2019-12-20</td>
				    <td id="boardReadCount">50</td>
				 </tr>
				 <tr>
				    <td id="boardNo">3</td>
				    <td id="boardTitle">시무식일정</td>
				    <td id="boardWriter">이사장</td>
				    <td id="boardWriteDate">2019-12-30</td>
				    <td id="boardReadCount">60</td>
				 </tr>
				 
			</tbody>
		</table>
		<div class="row" style="margin: 0 auto;">
			<b class="pageNo" id="btn_pageNo"  style="float:none; margin:0 auto;">1</b>
		</div>
		
		
		<div align="right" style="margin-right: 50px;" >
			<button type="button" class="btn btn_write btn-primary" id="btn_write" style="width: 10%; display: inline-block;">쓰기</button>
			<button type="button" class="btn btn_modify btn-primary" id="btn_modify" style="width: 10%; display: inline-block;">수정</button>
			<button type="button" class="btn btn_delete btn-primary" id="btn_delete" style="width: 10%; display: inline-block;">삭제</button>
		</div>
		
		</div>
	</div>
        
	

 	
	</div>
	 <!-- /.container-fluid -->
</div>
<!-- /.content-wrapper -->


