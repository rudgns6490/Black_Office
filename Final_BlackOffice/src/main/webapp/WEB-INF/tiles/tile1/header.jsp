<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String ctxPath = request.getContextPath();
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

  <nav class="navbar navbar-expand navbar-dark bg-dark static-top">

    <a class="navbar-brand mr-1" href="<%= ctxPath%>/main.action">Black Office</a>

    <!-- Navbar Search -->
    <form class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">
      <div class="input-group">
        <input type="text" class="form-control" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
        <div class="input-group-append">
          <button class="btn btn-primary" type="button">
            <i class="fas fa-search"></i>
          </button>
        </div>
      </div>
    </form>

    <!-- Navbar -->
    <ul class="navbar-nav ml-auto ml-md-0">
      <li class="nav-item dropdown no-arrow">
        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <c:if test="${sessionScope.loginuser != null }">
            <img style="border-radius: 50%;" src="<%= request.getContextPath() %>/resources/files/${loginuser.thumbnailFileName}">
          </c:if>
          
          <c:if test="${sessionScope.loginuser == null }">
          	<i class="fas fa-user-circle fa-fw"></i>
          </c:if>
          
          
        </a>
        <c:if test="${sessionScope.loginuser != null}">
	        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
	        	
		          <a class="dropdown-item" href="<%= ctxPath %>/revisionMyInfo.action">내 정보 수정</a>
		          <div class="dropdown-divider"></div>
		          <a class="dropdown-item" href="<%= ctxPath %>/logout.action">로그아웃</a>
		        
	        </div>
        </c:if>  
      </li>
    </ul>

  </nav>

  