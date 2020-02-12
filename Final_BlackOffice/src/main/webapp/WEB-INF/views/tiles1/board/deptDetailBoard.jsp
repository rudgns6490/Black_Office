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
	
var commentid = "";
var editComment = "";
var commentSeq = "";
var parentSeq = "";

$(document).ready(function(){
//	$(".editCommentOk").hide();
	
	/* 이전글 다음글 클릭시 이동하는 기능 구현 시작*/
	$(".move").hover(function(){
		   $(this).addClass("moveColor");	
		 },
         function(){
		   $(this).removeClass("moveColor");
	});
	/* 이전글 다음글 클릭시 이동하는 기능 구현 끝*/
	
	// 댓글 기본 disabled 
	$(".testinput").attr("disabled", true);
	$(".buttonOK").hide();
	// 수정버튼을 클릭 하였을 때 본인이 작성한 뎃글인지 확인함
	$(".editComment").click(function(){
					
		var loginuserid = "${requestScope.id}";
		
		var btn = $(this);
		var tr = btn.parent().parent();
		var td = tr.children();
		
		commentid = td.eq(1).text();

		var CommentCount = "${requestScope.CommentCount}";
		
		td.eq(2).find('input[type="text"]').attr("disabled", false);
		
		td.eq(2).find('input[type="text"]').focus();

		editComment = td.eq(2).find('input[type="text"]').val();
		
		commentSeq = td.eq(4).find('input[type="hidden"]').val();
		
//		alert(commentSeq);
		
//		$(".editComment").hide();
		
//		$(".editCommentOk").show();

	});
				
	
 	// 댓글 수정 창에서 엔터 키를 누르면
	$(".testinput").keydown(function(event){
			if(event.keyCode == 13) { // 엔터를 했을 경우
				editComment = $(this).val();
				parentSeq = $("#parentSeq").val();
				alert(parentSeq);
				location.href='<%= request.getContextPath() %>/updateComment.action?commentid='+commentid+'&editComment='+editComment+'&commentSeq='+commentSeq+'&parentSeq='+parentSeq;
			}
	}); // end of $("#pwd").keydown();-----------------------
		
	
	$(".replyDelete").click(function(){
		
		var btn = $(this);
		var tr = btn.parent().parent();
		var td = tr.children();
		
		commentid = td.eq(1).text();
		
		commentSeq = td.eq(4).find('input[type="hidden"]').val();
		parentSeq = $("#parentSeq").val();
		
		location.href='<%= request.getContextPath() %>/deleteComment.action?commentSeq='+commentSeq+'&parentSeq='+parentSeq;
		
	});
	/*
		// 댓글 - 확인을 클릭시
		$(".buttonOK").click(function(){
			$(".buttonOK").hide();
			
		});
	*/
		//댓글 수정완료버튼
		
/* 		 
		var CommentCount = "${requestScope.CommentCount}";
		
		for(var i=0; i<CommentCount; i++) {		
			$(".editComment"+i+"").click(function(){
			 alert($(this).parent().parent().find(".testinput"+i+"").val());
			});
		}
		 */
		
		
		
});// end of $(document).ready()----------------------


/* 부서별 계시판에서 댓글 달기 코드 추가할것 */
/*
	function makeComment(comments){
		 var html = "";
			$.each(comments, function(index, item){
				html += "<tr style='margin-top: 20px; width:100% border: none;'>";
				html += "<td style='width:15%; background-color: #e0ebeb; padding: 20px; font-weight: bold;'>"+item.name+"</td>";
				html += "<td style='text-align: center; padding: 20px; width:100px;'>"+item.fk_userid+"</td>";
				html += "<td style='width:50%; text-align: left; padding: 20px;'>"+item.content+"</td>";
				html += "<td style='text-align: center; padding: 20px; width:200px;'>"+item.regDate+"</td>";
				html += '<td id="editCommentBtn" style="text-align: center; padding: 20px; border: none;">' +'<button class="revisionbutton btn btn_commentsave btn-primary" style="width:100px; height: 50px; margin: 0 auto; ">'+수 정+'</button>'+'</td>';
	
				html += '<td id="replyDelete" style="padding: 20px; cursor:pointer;">' +'<button class="btn btn-danger btn_commentsave btn-danger deletebutton " style="width:100px; height: 50px; margin: 0 auto; ">'+삭 제+'</button>'+'</td>';
				
				html += "</tr>";
			}); 
	*/
				/*			
			<c:if test="${requestScope.id == commentvo.fk_userid}">
				<td id="editCommentBtn" style="text-align: center; padding: 20px; border: none;">
					<button class="revisionbutton btn btn_commentsave btn-primary" style="width:100px; height: 50px; margin: 0 auto; ">수 정</button>
				
				</td>	
				<td id="replyDelete" style="text-align: center; padding: 20px; border: none;">
					<button class=" btn btn_commentsave btn-danger deletebutton " style="width:100px; height: 50px; margin: 0 auto; ">삭 제</button>
				</td>
			</c:if>
																	
		*/
		/*
			$("#commentDisplay").html(html);
	}
*/

	function goAddWrite() {
		var frm = document.addWriteFrm;
		var cotentVal = frm.content.value.trim();
		if(cotentVal=="") {
			alert("댓글 내용을 입력하세요!!");
			return;
		}		
		
		var form_data = $("form[name=addWriteFrm]").serialize(); 
			
		$.ajax({
			url:"<%= request.getContextPath()%>/addComment.action",
			data:form_data,
			type:"POST",
			dataType:"JSON",
			success:function(json){
				/*
				makeComment(json)
				frm.content.value = "";
				*/
				 /*노트북 소스 코드 활용에 따른 주석 처리  makeComment 에서 기능 구현됨*/
				 
				var html = "";
				$.each(comments, function(index, item){
					html += "<tr style='margin-top: 20px; width:100%'>";
					html += "<td style='width:15%; background-color: #e0ebeb; padding: 20px; font-weight: bold;'>"+item.name+"</td>";
					html += "<td style='width:60%; text-align: left; padding: 20px;'>"+item.content+"</td>";
					html += "<td style='text-align: center; padding: 20px;'>"+item.regDate+"</td>";
					html += "<td id='replyModify' style='padding: 20px; cursor:pointer;'>"+수정+"</td>";
					html += "<td id='replyDelete' style='padding: 20px; cursor:pointer;'>"+삭제+"</td>";
					html += "</tr>";
				}); 
				
				$("#commentDisplay").html(html);
				frm.content.value = ""; 
				
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});
		
		function editComment() {
			 
			var frm = document.editCommentFrm
			frm.method = "GET";
			frm.action = "<%= ctxPath%>/updateComment.action";
			frm.submit();
		}
		
	// 노트북 소스 코드 병합에 따른 주석화
		window.location.reload();
	}// end of function goAddWrite()-------------
 
</script>

<div id="content-wrapper" style="padding-top: 0;">
<div class="container-fluid text-center">
		  
	<div class="row content container-fluid text-center" >
		<div class="section col-sm-12" style="padding: 5%;">
		 <fieldset>
    	<legend >업무 게시판</legend>
			<table class="table table-bordered">
				<tbody>
				<tr>
					<th class="" style="background-color: #e0ebeb;" width="200">작성자</th>
					<td id="boardWriter" width="500" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${deptboardvo.name}</td>
					<th class="" style="background-color: #e0ebeb;" width="200">작성일</th>
					<td id="boardWriteDate" style="text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${deptboardvo.regDate}</td>
				</tr>
				<tr>
					<th id="boardTitle" class="" style="background-color: #e0ebeb;">제목</th>
					<td id="boardContent" colspan="3" style="text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${deptboardvo.subject}</td>		
				</tr>
				<tr>
					<th class="" style="background-color: #e0ebeb;" >첨부파일</th>
					<td>
					<!-- 파일 유무에 따른 표시 -->							
					<c:if test="${not empty deptboardvo.orgFilename}">
						<a href="<%= request.getContextPath()%>/deptDownload.action?seq=${deptboardvo.seq}">  
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${deptboardvo.orgFilename}
						</a>
					</c:if>
					<c:if test="${empty deptboardvo.orgFilename}">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-
					</c:if>
						<%-- 
						로그인 유무에 따른 파일 다운로드 기능 활성화
						<c:if test="${sessionScope.loginuser != null}">
							<a href="<%= request.getContextPath()%>/deptDownload.action?seq=${deptboardvo.seq}">  
								${deptboardvo.orgFilename}
							</a>
						</c:if>
						<c:if test="${sessionScope.loginuser == null}">
							${deptboardvo.orgFilename}
						</c:if>
						 --%>
					</td>
												
					<th style="background-color: #e0ebeb;">파일크기</th>
					<td colspan="2">
					<c:if test="${not empty deptboardvo.fileSize}">
						 
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${deptboardvo.fileSize}&nbsp;(bytes)
		
					</c:if>
					<c:if test="${empty deptboardvo.fileSize}">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-
					</c:if>
					</td>
				</tr>
				<tr id="boardContent" width="500" height="500" style="text-align: left;">					
					<td colspan="4" >
						${deptboardvo.content}
					</td>
				</tr>
				
				</tbody>
			</table>
			
			<br/>
			<div style="margin-bottom: 1%;">이전글 : 
				<c:if test="${deptboardvo.previousseq != null}">
					<span class="move" onClick="javascript:location.href='deptDetailBoard.action?seq=${deptboardvo.previousseq}'">${deptboardvo.previoussubject}</span>
				</c:if>
				<c:if test="${deptboardvo.previousseq == null}">
					이전글 없음
				</c:if>
			</div>							
			<div style="margin-bottom: 1%;">다음글 : 
				<c:if test="${deptboardvo.nextseq != null}">
					<span class="move" onClick="javascript:location.href='deptDetailBoard.action?seq=${deptboardvo.nextseq}'">${deptboardvo.nextsubject}</span>
				</c:if>
				<c:if test="${deptboardvo.nextseq == null}">
					다음글 없음
				</c:if>
			</div>
			<div>
				<input id="parentSeq" type="hidden" value="${deptboardvo.seq}">
			</div>
			<br/>
			
			<div align="right" style="margin-right: 50px;" >
				<button type="button" class="btn btn_modify btn-primary" id="btnUpdate" style="width: 10%; display: inline-block;" onClick="javascript:location.href='<%= request.getContextPath() %>/deptBoardEdit.action?seq=${deptboardvo.seq}'">수정</button>
				<button type="button" class="btn btn_delete btn-primary" id="btn_delete" style="width: 10%; display: inline-block;" onClick="javascript:location.href='<%= request.getContextPath() %>/deptBoardDelete.action?seq=${deptboardvo.seq}'">삭제</button>					
				<button type="button" class="btn btn_write btn-primary" style="width: 10%; display: inline-block;" onClick="javascript:location.href='<%= request.getContextPath() %>/deptBoardList.action'">목록</button>
			</div>
			
			<!-- 뎃글 기능 구현 시작  -->
			<h3 style="margin-top: 50px;">댓글쓰기 및 보기</h3>
			<form name="addWriteFrm" style="margin-top: 20px;">
				<input type="hidden" name="fk_userid" value="${requestScope.id}" />
										
				<table class="table table-bordered">
				<tbody>
				<tr style="width:100%">
					<th id="" class="" style="background-color: #e0ebeb; width:10%; padding: 20px; "> <!-- sessionScope.loginuser.name --> <!--  requestScope.name -->
						<input type="text" name="name" style= "width:160px; background-color: #e0ebeb; font-weight: bold; border: none;" value="${requestScope.name}" class="short" readonly />
					</th>
					<td id="boardComment"  style="text-align: left;">
						<input id="commentContent" type="text" name="content" class="long" style="width:90%;  border: none; text-align: left; padding: 20px;" />
					</td>
				</tr>
				</tbody>
				</table>
							
				<!-- 댓글에 달리는 원게시물 글번호(즉, 댓글의 부모글 글번호) -->
				<input type="hidden" name="parentSeq" value="${deptboardvo.seq}" /> 
				<div class=" my-auto padding-0" align="right" style="margin-right: 50px;" >
				<button id="btnComment" type="button" class="btn btn_commentsave btn-primary" style="width: 10%; height: 150%; display: inline-block;" onclick="goAddWrite()">댓글 등록</button>

				</div>		
			</form>	
				
			
			<!-- style="padding: 50px;" -->
			<div class="" style="margin-top: 25px;">
				<table class="commentTable table table-bordered" style=" border: none;">
				<tbody id="commentDisplay" style=" border: none;">
					
						<c:forEach var="commentvo" items="${commentList}" varStatus="status" >
							
							<tr style="margin-top: 20px; width:100%; border: none;">
								
								<td style="width:15%; background-color: #e0ebeb; padding: 20px;">${commentvo.name}</td>
								<td style="text-align: center; padding: 20px; width:100px;">${commentvo.fk_userid}</td>
								<td style="width:40%; text-align: left; padding: 20px;">
								<!-- 진행중 -->
									<input class="testinput" type="text" class="long" size="20" style="width:250px; border: 0; background-color: white;" value="${commentvo.content}">
								</td>
								<td  style="text-align: center; padding: 20px; width:200px;">${commentvo.regDate} </td>
								<td style=" border: none;">
									<input type="hidden" value="${commentvo.seq}" >
									<!-- <input class="buttonOK" type="text" value="확인" style="width: 35px; border: solid 1px black; background-color: white; float: right; display: inline-block; cursor: pointer; radius: 10px;" disabled> -->
								</td>
													
								<c:if test="${requestScope.id == commentvo.fk_userid}">
									 <td id="editCommentBtn" style="text-align: center; padding: 20px; border: none;">
										<button type="button" class="btn btn_commentsave btn-primary editComment" style="width:100px; height: 50px; margin: 0 auto;" >수 정</button>
										<!-- <button type="button" class="btn btn_commentsave btn-primary editCommentOk" style="width:100px; height: 50px; margin: 0 auto;" >수정 완료</button> -->
									</td>	
									<td id="replyDelete" style="text-align: center; padding: 20px; border: none;">
										<button type="button" class="replyDelete btn  btn-danger deletebutton " style="width:100px; height: 50px; margin: 0 auto; ">삭 제</button>
									</td>
								</c:if>
								 														
							</tr>
							
						</c:forEach>
							
				</tbody>
				</table>
			
			</div>
			</fieldset>
		</div>				
	</div>

	</div>
	</div>
     



