<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>     
  <div id="wrapper"> 

	<!-- 사이드바 내용은 요기에 추가 2020/01/03 kkh -->
    <!-- Sidebar -->
    <ul class="sidebar navbar-nav">
      <li class="nav-item active">
        <a class="nav-link" href="template.jsp">
          <span>HOME</span>
        </a> 
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="pagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-fw fa-folder"></i>
          <span>게시판</span>
        </a>
        <div class="dropdown-menu" aria-labelledby="pagesDropdown">
          <a class="dropdown-item" href="">전체게시판</a>
          <a class="dropdown-item" href="">업무게시판</a>
          <div class="dropdown-divider"></div>
        </div>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="pagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-fw fa-folder"></i>
          <span>전자결재</span>
        </a>
        <div class="dropdown-menu" aria-labelledby="pagesDropdown">
          <h6 class="dropdown-header">결재</h6>
          <a class="dropdown-item" href="<%= ctxPath%>/draft.action">일반결재서 작성</a>
          <a class="dropdown-item" href="<%= ctxPath%>/expenditure.action">지출결재서 작성</a>
          <a class="dropdown-item" href="<%= ctxPath%>/leave.action">휴가 결재서 작성</a>
          <a class="dropdown-item" href="<%= ctxPath%>/vacation.action">휴직 결재서 작성</a>
          
          <h6 class="dropdown-header">결재함</h6>
          <a class="dropdown-item" href="<%= ctxPath%>/incomplete_archive_normal.action">미결재 문서함</a>
          <a class="dropdown-item" href="<%= ctxPath%>/complete_archive_normal.action">결재완료 문서함</a>
          
          <h6 class="dropdown-header">문서함</h6>
          <a class="dropdown-item" href="<%= ctxPath%>/mydocument_archive_normal.action">내문서함</a>
          <a class="dropdown-item" href="<%= ctxPath%>/deptdocument_archive_normal.action">부서문서함</a>
          <div class="dropdown-divider"></div>

        </div>
       
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="pagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-fw fa-folder"></i>
          <span>보고</span>
        </a>
        <div class="dropdown-menu" aria-labelledby="pagesDropdown">
          <h6 class="dropdown-header">보고</h6>
          <a class="dropdown-item" href="<%= ctxPath%>/report.action">보고서 작성</a>
          <a class="dropdown-item" href="<%= ctxPath%>/report_archive.action">보고서함</a>
          <a class="dropdown-item" href="<%= ctxPath%>/temp_archive_normal.action">보고서 임시저장함</a>
          <div class="dropdown-divider"></div>
        </div>
      </li>
     
	  <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="pagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-fw fa-folder"></i>
          <span>일정</span>
        </a>
        <div class="dropdown-menu" aria-labelledby="pagesDropdown">
          <a class="dropdown-item" href="">개인 일정</a>
          <a class="dropdown-item" href="">공유된 일정</a>
          <div class="dropdown-divider"></div>
        </div>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="pagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-fw fa-folder"></i>
          <span>주소록</span>
        </a>
        <div class="dropdown-menu" aria-labelledby="pagesDropdown">
          <a class="dropdown-item" href="">개인 주소록</a>
          <a class="dropdown-item" href="">공용 주소록</a>
          <div class="dropdown-divider"></div>
        </div>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="pagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-fw fa-folder"></i>
          <span>채팅</span>
        </a>
        <div class="dropdown-menu" aria-labelledby="pagesDropdown">
          <a class="dropdown-item" href="">채팅</a>
          <div class="dropdown-divider"></div>
        </div>
      </li>
      <!-- 관리자로 로그인 했을 경우에만 관리자 메뉴가 보이도록 수정해야됨
        	  추후 AOP로 로그인 처리후 작업할 예정 2020/01/03 kkh -->
	  <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="pagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-fw fa-folder"></i>
          <span>통계</span>
        </a>
        <div class="dropdown-menu" aria-labelledby="pagesDropdown">
          <a class="dropdown-item" href="">지출통계</a>
          <div class="dropdown-divider"></div>
        </div>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="pagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-fw fa-folder"></i>
          <span>전사관리</span>
        </a>
        <div class="dropdown-menu" aria-labelledby="pagesDropdown">
          <h5 style="font-weight: bold;" class="dropdown-header">조직관리</h5>
          <a class="dropdown-item" href="">직위관리</a>
          <h5 style="font-weight: blod;" class="dropdown-header">인사관리</h5>
          <a class="dropdown-item" href="">입사처리</a>
          <a class="dropdown-item" href="">인사이동</a>
          <a class="dropdown-item" href="">인사관리내역</a>
          <div class="dropdown-divider"></div>
        </div>
      </li>
    </ul>