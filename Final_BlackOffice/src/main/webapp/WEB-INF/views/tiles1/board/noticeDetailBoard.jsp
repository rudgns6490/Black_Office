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
		    	<legend >공지사항</legend>
					<table class="table table-bordered">
						<tbody>
						<tr>
							<th class="" style="background-color: #e0ebeb;" >작성자</th>
							<td id="boardWriter">관리자</td>
							<th class="" style="background-color: #e0ebeb;">작성일</th>
							<td id="boardWriteDate" style="text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2019-11-25 07:21:34</td>
						</tr>
						<tr>
							<th id="boardTitle" class="" style="background-color: #e0ebeb;">제목</th>
							<td id="boardContent" colspan="3" style="text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;공지사항 이용시 주의점</td>
												
						</tr>
						<tr>
							<th class="" style="background-color: #e0ebeb;" >첨부파일</th>
							<td id="boardAddFile" colspan="3"style="text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="javascript:download('공지서식.jpg', '/2019/11/15746346776566248.jpg', 'IMAGE')">공지 서식.jpg</a>&nbsp;[826.11KB]<br>
							</td>
						</tr>
						<tr id="boardContent" width="500" height="500" style="text-align: left;">					
							<td colspan="4" >
								내용 공지 작성시 업무 양식에 맞추어 작성해주시길 바랍니다.
							</td>
						</tr>
						
						</tbody>
					</table>
					<div align="right" style="margin-right: 50px;" >
						<button type="button" class="btn btn_write btn-primary" id="btn_write" style="width: 10%; display: inline-block; " >목록</button>
					</div>
					
					<!-- <div class="commentSection row" style="margin-top: 50px;" > 공지사항에서는 댓글 비활성화
						<div id="boardComment" class="boardComment col-sm-10"style="float:none; margin:0 auto">
							<textarea name="commentcontent" id="commentcontent" rows="3" maxlength="1000" style="width:100%;" onkeyup="byteCheckTextLength(this,commentcounter);" onblur="onkeyup();"></textarea>
							
						</div>			
							
						<span id="commentcounter" class="col-sm-1 my-auto" align="left" style="background: #fff; border-radius: 0.2em; padding: 0 .2em 0 .2em; font-size: 0.75em;">0/1000</span> 댓글 글자제한 1000자 			
						
						<div class="col-sm-1 my-auto" style="float:none; margin:0 auto" >
							<button type="button" class="btn btn_commentsave btn-primary" id="btn_commentsave" style="width: 100%; display: inline-block;">저장</button>
						</div>
					</div>-->
					</fieldset>
				</div>				
			</div>

		<div align="right" style="margin-right: 50px;" >
			<button type="button" class="btn btn_write btn-primary" id="btn_write" style="width: 10%; display: inline-block;">쓰기</button>
			<button type="button" class="btn btn_modify btn-primary" id="btn_modify" style="width: 10%; display: inline-block;">수정</button>
			<button type="button" class="btn btn_delete btn-primary" id="btn_delete" style="width: 10%; display: inline-block;">삭제</button>
		</div>
		
	</div>
	</div>
     



