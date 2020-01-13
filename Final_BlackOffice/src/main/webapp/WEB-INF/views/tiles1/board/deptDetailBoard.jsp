<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%
	String ctxPath = request.getContextPath();
%>
	
  <link href="<%= ctxPath %>/resources/css/noticeBoard.css" rel="stylesheet"><!-- 부서별 css 생성하거나 공콩 css로 통일 -->
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>


<div id="content-wrapper" style="padding-top: 0;">
<div class="container-fluid text-center">
		  
	<div class="row content container-fluid text-center" >
		<div class="section col-sm-12" style="padding: 5%;">
		 <fieldset>
    	<legend >부서 게시판</legend>
			<table class="table table-bordered">
				<tbody>
				<tr>
					<th class="" style="background-color: #e0ebeb;" >작성자</th>
					<td id="boardWriter">김팀장</td>
					<th class="" style="background-color: #e0ebeb;">작성일</th>
					<td id="boardWriteDate" style="text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2019-12-15 10:01:30</td>
				</tr>
				<tr>
					<th id="boardTitle" class="" style="background-color: #e0ebeb;">제목</th>
					<td id="boardContent" colspan="3" style="text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 프로젝트 A 전달사항 -필독- </td>
										
				</tr>
				<tr>
					<th class="" style="background-color: #e0ebeb;" >첨부파일</th>
					<td id="boardAddFile" colspan="3"style="text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:download('기술데이터2-1.word', '/2019/11/15746346776566248.jpg', 'IMAGE')">기술데이터2-1.word</a>&nbsp;[5.2MB]<br>
					</td>
				</tr>
				<tr id="boardContent" width="500" height="500" style="text-align: left;">					
					<td colspan="4" >
						프로젝트 A 진행시 기술데이터 참고 하여 진행 할것.
					</td>
				</tr>
				
				</tbody>
			</table>
			<div align="right" style="margin-right: 50px;" >
				<button type="button" class="btn btn_write btn-primary" id="btn_write" style="width: 10%; display: inline-block; " >목록</button>
			</div>
			
			<div class="commentSection row" style="margin-top: 50px;" >
				<div id="boardComment" class="boardComment"style="float:none; width:80%; margin:0 auto">
					<textarea name="commentcontent" id="commentcontent" rows="3" maxlength="1000" style="width:100%;" onkeyup="byteCheckTextLength(this,commentcounter);" onblur="onkeyup();"></textarea>
					
				</div>			
					
				<span id="commentcounter" class=" my-auto padding-0" align="left" style=" width:5%; background: #fff; border-radius: 0.2em; padding: 0 .2em 0 .2em; font-size: 0.75em;">0/1000</span> <!-- 댓글 글자제한 1000자  -->			
				
				<div class=" my-auto padding-0" style="width:13%; float:none; margin:0 auto" >
					<button type="button" class="btn btn_commentsave btn-primary" id="btn_commentsave" style="width: 50%; height: 150%; display: inline-block;">댓글 등록</button>
				</div>
			</div>
			<div align="right" style="margin-right: 50px; margin-top: 25px;" >
				<button type="button" class="btn btn_write btn-primary" id="btn_write" style="width: 10%; display: inline-block;">쓰기</button>
				<button type="button" class="btn btn_modify btn-primary" id="btn_modify" style="width: 10%; display: inline-block;">수정</button>
				<button type="button" class="btn btn_delete btn-primary" id="btn_delete" style="width: 10%; display: inline-block;">삭제</button>
			</div>	
			<!-- style="padding: 50px;" -->
			<div class="" style="margin-top: 25px;">
				<table class="commentTable table-bordered">
				<tbody >
				<tr style="margin-top: 20px; width:100%">
					<th id="replyId" class="" style="width:15%; background-color: #e0ebeb; padding: 20px;" >이대리</th>
					<td id="replyContent" style="width:60%; text-align: left; padding: 20px;">알겠습니다</td>
					<td id="replyDate" style=" padding: 20px;">2019-12-15 10:01:30</td>
					<td id="replyModify" style="padding: 20px; cursor:pointer">수정</td>
					<td id="replyDelete" style="padding: 20px; cursor:pointer">삭제</td>
				</tr>
							
				<tr style="margin-top: 20px; width:100%">
					<th id="replyId" class="" style="width:15%; background-color: #e0ebeb; padding: 20px;" >윤주임</th>
					<td id="replyContent" style="width:60%; text-align: left; padding: 20px;">확인했습니다</td>
					<td id="replyDate" style=" padding: 20px; ">2019-12-15 10:10:10</td>
					<td id="replyModify" style="padding: 20px; cursor:pointer">수정</td>
					<td id="replyDelete" style="padding: 20px; cursor:pointer">삭제</td>
				</tr>
				
				</tbody>
				</table>
			
			</div>
			</fieldset>
		</div>				
	</div>

		
		
	</div>
	</div>
     



