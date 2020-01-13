<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%
	String ctxPath = request.getContextPath();
%>
	
  <link href="<%= ctxPath %>/resources/css/Board.css" rel="stylesheet"><!-- 부서별 css 생성하거나 공콩 css로 통일 -->
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
		    <legend>부서별 게시판</legend>
			<form name="deptBoardSearch" method="post">
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
					<th id="boardHeaderComment">댓글수</th> 
				    <th id="boardHeaderAddFile">파일</th> 
			    </tr>
		  	</thead>
			<tbody id="boardBody">
				<tr>
				    <td id="boardNo">1</td>
				    <td id="boardTitle">세미프로잭트</td>
				    <td id="boardWriter">김팀장</td>
				    <td id="boardWriteDate">2019-10-12</td>
				    <td id="boardReadCount">30</td>
				    <td id="boardCommentCnt">5</td>
				    <td id="boardAddFileNum">3</td>
				</tr>
				<tr>
				    <td id="boardNo">2</td>
				    <td id="boardTitle">일반업무 전달</td>
				    <td id="boardWriter">이부장</td>
				    <td id="boardWriteDate">2019-11-22</td>
				    <td id="boardReadCount">50</td>
				    <td id="boardCommentCnt">3</td>
				    <td id="boardAddFileNum">1</td>
				 </tr>
				 <tr>
				    <td id="boardNo">3</td>
				    <td id="boardTitle">헙무협조전</td>
				    <td id="boardWriter">박과장</td>
				    <td id="boardWriteDate">2019-12-12</td>
				    <td id="boardReadCount">60</td>
				    <td id="boardCommentCnt">7</td>
				    <td id="boardAddFileNum">3</td>
				 </tr>
				 
			</tbody>
		</table>
		<div class="row" style="margin: 0 auto;">
			<b class="pageNo" id="btn_pageNo"  style="float:none; margin:0 auto;">1</b>
		</div>
		
		
		<div align="right" style="margin-right: 50px;" >
			<button type="button" class="btn btn_write btn-primary" id="btn_write" style="width: 10%; display: inline-block;">쓰기</button>
			<!-- <button type="button" class="btn btn_modify btn-primary" id="btn_modify" style="width: 10%; display: inline-block;">수정</button>
			<button type="button" class="btn btn_delete btn-primary" id="btn_delete" style="width: 10%; display: inline-block;">삭제</button> -->
		</div>
		
	</div>
	</div>
      
	

 	
	</div>
	 <!-- /.container-fluid -->
</div>
<!-- /.content-wrapper -->


