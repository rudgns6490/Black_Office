<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String ctxPath = request.getContextPath();
%>
	
  <link href="<%= ctxPath %>/resources/css/Board.css" rel="stylesheet">
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<script type="text/javascript">

$(document).ready(function(){
	
	$(".subject").bind("mouseover", function(event){
		var $target = $(event.target);
		$target.addClass("subjectStyle");
	});
	
	$(".subject").bind("mouseout", function(event){
		var $target = $(event.target);
		$target.removeClass("subjectStyle");
	});
		
	$("#searchWord").keydown(function(event){
		if(event.keyCode == 13) {
			// 엔터를 했을 경우
			goSearch();
		}
	});
	
	
	// 검색시 검색조건 및 검색어 값 유지시키기 
	if(${paraMap != null}) {
		$("#searchType").val("${paraMap.searchType}");
		$("#searchWord").val("${paraMap.searchWord}");
	}
	
	<%-- ===  검색어 입력시 자동글 완성하기 2 === --%>
	$("#displayList").hide();
	
	$("#searchWord").keyup(function(){
		
		var wordLength = $("#searchWord").val().length; 
		// 검색어의 길이를 알아온다.
		
		if(wordLength == 0) {
			$("#displayList").hide(); 
			// 검색어 입력후 백스페이스키를 눌러서 검색어를 모두 지우면 검색된 내용이 안 나오도록 해야 한다.
		}
		else { 
		
			$.ajax({
				url:"<%= request.getContextPath()%>/wordSearchShow.action",
				type:"GET",
				data:{ "searchType":$("#searchType").val()
					  ,"searchWord":$("#searchWord").val() },
				dataType:"JSON",
				success:function(json){
					
					<%-- === #113. 검색어 입력시 자동글 완성하기 7 === --%>
					if(json.length > 0) {
						// 검색된 데이터가 있는 경우임.
						// 만약에 조회된 데이터가 없을 경우 if(json == null) 이 아니고 if(json.length == 0) 이라고 써야 한다.

					//	console.log("확인용 => "+json);
						var html = "";
						
						$.each(json, function(index, item){
							var word = item.word;
							var index = word.toLowerCase().indexOf( $("#searchWord").val().toLowerCase() ); 
							//  alert("index : " + index);
							var len = $("#searchWord").val().length;
							var result = "";
							
							result = "<span class='first' style='color:blue;'>" +word.substr(0, index)+ "</span>" + "<span class='second' style='color:red; font-weight:bold;'>" +word.substr(index, len)+ "</span>" + "<span class='third' style='color:blue;'>" +word.substr(index+len, word.length - (index+len) )+ "</span>";  
							
							html += "<span style='cursor:pointer;'>"+ result +"</span><br/>"; 
						});
						
						$("#displayList").html(html);
						$("#displayList").show();
					}
					else {
						// 검색된 데이터가 존재하지 않는 경우임.
						$("#displayList").hide();
					}// end of if ~ else---------------------
					
				},
				error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
			}); // end of $.ajax({});-------------------
		
		 } // end of if ~ else---------------------------- 
		
	}); // end of $("#searchWord").keyup();-------------  
	
	$("#displayList").click(function(event){
		
		var word = "";
		
		var $target = $(event.target);
		
		if($target.is(".first")) {
			word = $target.text() + $target.next().text() + $target.next().next().text();
		}
		else if($target.is(".second")) {
			word = $target.prev().text() + $target.text() + $target.next().text();
		}
		else if($target.is(".third")) {
			word = $target.prev().prev().text() + $target.prev().text() + $target.text();
		}
		
		$("#searchWord").val(word); 
		// 텍스트박스에 검색된 결과의 문자열을 입력해준다.
		
		$("#displayList").hide();
		
		goSearch();
		
	});
		
});  /* end of $(document).ready()------------------ */

function goView(seq) {
	/* 게시물을 클릭 했을때 게시물 내용 받아오고 세부 게시물로 이동  */
	var frm = document.goViewFrm;
	frm.seq.value = seq;
	
	frm.method = "GET";
	frm.action = "noticeDetailBoard.action";
	frm.submit();
}


function goSearch() {
	/* 검색후 검색결과를 리스트로 보냄 */
	var frm = document.searchFrm;
	frm.method = "GET";
	frm.action = "<%= request.getContextPath()%>/noticeBoardList.action"; 
	frm.submit();
}

</script>


<div id="content-wrapper" style="padding-top: 0;">
<div class="container-fluid text-center">

<div class="row content">
<div class="col-sm-12" style="padding: 5%;">
		  <fieldset>
		    <legend>공지사항</legend>
			<form name="searchFrm"><!-- 글검색폼 시작 -->
		      <table class="table table-bordered" style="position: relative; z-index: 1;">
		      	<tr>
		      	  <td class="table-active" style="width: 15%;" >검색조건</td>
		      	  <td>
		      	    <select name="searchType" id="searchType" class="form-control" style="width: 15%; display: inline-block;">
	  				  <option value="subject">제목</option>
	  				  <option value="content">내용</option>
	  				  <option value="name">작성자</option>
	  				</select>
					<input type="text"  name="searchWord" id="searchWord" size="40" style="width: 30%;" autocomplete="off" />
					<div id="displayList" style=" position: relative; z-index: 2; width: 314px; height: 100px; overflow: auto; margin-left: 70px; margin-top: -1px; border-top: 0px; border: solid 1px gray;">                         
					</div>
					<button type="button" class="btn btn-primary" onclick="goSearch()" style="width:10%; display: inline-block;">검색</button>
		      	  </td>
		      	</tr>
		      </table>
		    </form><!-- 글검색폼 끝 -->
		    <%-- 검색어 입력시 자동글 완성하기 --%>
			
		  </fieldset> <!-- 제목, 검색조건, 검색버튼 -->

		<table id="myTable" class="table table-hover">	
		
			<thead style="background-color: navy; color: #fff;" class="boardHeader" >
			    <tr>
				    <th id="boardHeaderNo" width="100">No.</th>
				    <th id="boardHeaderTitle" width="600" >제목</th>
				    <th id="boardHeaderWriter" width="100">작성자</th>
				    <th id="boardHeaderWriteDate" width="200">작성일</th>
				    <th id="boardHeaderReadCount" width="100">조회수</th>
					<th style="text-align: center;" width="50">파일</th>
					<th style="text-align: center;" width="100">크기(bytes)</th>
		
			    </tr>
		  	</thead>
			<tbody style="cursor:pointer" id="boardBody">
			<c:forEach var="boardvo" items="${noticeBoardList}" varStatus="status">
				<tr>
				<td align="center">${boardvo.seq}</td>
				<td align="left"> 
				 <span class="subject" onclick="goView('${boardvo.seq}');">${boardvo.subject}</span>
				</td>
				<td align="center">${boardvo.name}</td>
				<td align="center">${boardvo.regDate}</td>
				<td align="center">${boardvo.readCount}</td>
				
				<%-- 파일과 크기를 보여주도록  /Board/src/main/webapp/resources/images/disk.gif 이미지 파일을 사용하여 첨부파일의 유무를 보여주도록 한다. --%>
				<td align="center">
					<c:if test="${not empty boardvo.fileName}">
						<img style="width: 20px" src="<%= request.getContextPath()%>/resources/images/disk.gif" /><!-- 디스크모양 아이콘 -->
					</c:if>
					<c:if test="${empty boardvo.fileName}">
						X
					</c:if>
				</td>
				<td align="center">
					<c:if test="${not empty boardvo.fileSize}">
						${boardvo.fileSize} <%-- 파일크기 --%>
					</c:if>
					<c:if test="${empty boardvo.fileSize}">
						-
					</c:if>
				</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		
		<form name="goViewFrm">
		<input type="hidden" name="seq" />
		<input type="hidden" name="gobackURL" value="${gobackURL}" /> 
		</form>
		
		
		<%-- 페이지바 보여주기 --%>
		<div align="center" style="">
			${pageBar}
		</div>
		
		<div align="right" style="margin-right: 50px;" >
			<button type="button" class="btn btn_write btn-primary" id="btnWrite" onClick="javascript:location.href='<%= request.getContextPath() %>/noticeBoardWrite.action'" style="width: 10%; display: inline-block;">쓰기</button> 
		</div>
		
		</div>
	</div>
        
	</div>
	 <!-- /.container-fluid -->
</div>
<!-- /.content-wrapper -->


