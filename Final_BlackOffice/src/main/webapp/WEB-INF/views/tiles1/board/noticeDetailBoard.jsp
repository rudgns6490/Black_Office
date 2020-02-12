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

<style type="text/css">
	.move {cursor: pointer;} 						/* 이전글 다음글을 꾸며주는 css */
	.moveColor {color: #660029; font-weight: bold;} /* 이전글 다음글을 꾸며주는 css */
</style>

<script type="text/javascript">
    
	$(document).ready(function(){
		/* 이전글 다음글 클릭시 이동하는 기능 구현 시작*/
		$(".move").hover(function(){
			   $(this).addClass("moveColor");	
			 },
	         function(){
			   $(this).removeClass("moveColor");
		});
		/* 이전글 다음글 클릭시 이동하는 기능 구현 끝*/
				
	});// end of $(document).ready()----------------------
	
</script>

<div id="content-wrapper" style="padding-top: 0;">
<div class="container-fluid text-center">
		  
			<div class="row content container-fluid text-center" >
				<div class="section col-sm-12" style="padding: 5%;">
				 <fieldset>
		    	<legend >공지사항</legend>
					<table class="table table-bordered">
						<tbody>
						<tr>
							<th class="" style="background-color: #e0ebeb;" width="200">작성자</th>
							<td id="boardWriter" width="500" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${boardvo.name}</td>
							<th class="" style="background-color: #e0ebeb;" width="200">작성일</th>
							<td id="boardWriteDate" style="text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${boardvo.regDate}</td>
						</tr>
						<tr>
							<th id="boardTitle" class="" style="background-color: #e0ebeb;">제목</th>
							<td id="boardContent" colspan="3" style="text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${boardvo.subject}</td>
												
						</tr>
						<tr>
							<th class="" style="background-color: #e0ebeb;" >첨부파일</th>
							<td>
							<!-- 파일 유무에 따른 표시 -->							
							<c:if test="${not empty boardvo.orgFilename}">
								<a href="<%= request.getContextPath()%>/download.action?seq=${boardvo.seq}">  
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${boardvo.orgFilename}
								</a>
							</c:if>
							<c:if test="${empty boardvo.orgFilename}">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-
							</c:if>
								<%-- 로그인 유무에 따른 파일 다운로드 기능 활성화
								<c:if test="${sessionScope.loginuser != null}">
									<a href="<%= request.getContextPath()%>/download.action?seq=${boardvo.seq}">  
										${boardvo.orgFilename}
									</a>
								</c:if>
								<c:if test="${sessionScope.loginuser == null}">
									${boardvo.orgFilename}
								</c:if>
								 --%>
							</td>
														
							<th style="background-color: #e0ebeb;">파일크기</th>
							<td colspan="2">
							<c:if test="${not empty boardvo.fileSize}">
								 
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${boardvo.fileSize}&nbsp;(bytes)
				
							</c:if>
							<c:if test="${empty boardvo.fileSize}">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-
							</c:if>
							</td>
						</tr>
						<tr id="boardContent" width="500" height="500" style="text-align: left;">					
							<td colspan="4" >
								${boardvo.content}
							</td>
						</tr>
						
						</tbody>
					</table>
					
					<br/>
					<div style="margin-bottom: 1%;">이전글 : 
						<c:if test="${boardvo.previousseq != null}">
							<span class="move" onClick="javascript:location.href='noticeDetailBoard.action?seq=${boardvo.previousseq}'">${boardvo.previoussubject}</span>
						</c:if>
						<c:if test="${boardvo.previousseq == null}">
							이전글 없음
						</c:if>
					</div>							
					<div style="margin-bottom: 1%;">다음글 : 
						<c:if test="${boardvo.nextseq != null}">
							<span class="move" onClick="javascript:location.href='noticeDetailBoard.action?seq=${boardvo.nextseq}'">${boardvo.nextsubject}</span>
						</c:if>
						<c:if test="${boardvo.nextseq == null}">
							다음글 없음
						</c:if>
					</div>
					<br/>
					
					<div align="right" style="margin-right: 50px;" >
						<button type="button" class="btn btn_modify btn-primary" id="btnUpdate" style="width: 10%; display: inline-block;" onClick="javascript:location.href='<%= request.getContextPath() %>/noticeBoardEdit.action?seq=${boardvo.seq}'">수정</button>
					<!-- 구현실폐 <button type="button" class="btn btn_delete btn-primary" id="btnDelete" style="width: 10%; display: inline-block;" >삭제</button> -->
				
						<%-- <button type="button" class="btn btn_delete btn-primary" id="btn_delete" style="width: 10%; display: inline-block;" onClick="javascript:location.href='<%= request.getContextPath() %>/noticeBoardDeleteEnd.action'">삭제</button> --%>
						<button type="button" class="btn btn_delete btn-primary" id="btn_delete" style="width: 10%; display: inline-block;" onClick="javascript:location.href='<%= request.getContextPath() %>/noticeBoardDelete.action?seq=${boardvo.seq}'">삭제</button>					
						<button type="button" class="btn btn_write btn-primary" style="width: 10%; display: inline-block;" onClick="javascript:location.href='<%= request.getContextPath() %>/noticeBoardList.action'">목록</button>
					</div>

					</fieldset>
				</div>				
			</div>

		
		
	</div>
	</div>
     



