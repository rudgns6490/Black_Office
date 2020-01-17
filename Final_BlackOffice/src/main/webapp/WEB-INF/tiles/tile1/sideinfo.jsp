<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.net.InetAddress"%>

<% 
	String ctxPath = request.getContextPath();
	
	//=== (웹채팅관련3) === //
	// === 서버 IP 주소 알아오기 ===
	InetAddress inet = InetAddress.getLocalHost();
	String serverIP = inet.getHostAddress(); 		// IP 주소를 얻어온 것
	
// 	System.out.println("serverIP : " + serverIP);
	// serverIP : 192.168.50.39

	serverIP = "172.30.1.23";						// 본인의 IP 를 넣어줘야 한다.
	int portnumber = request.getServerPort();		// 포트넘버 얻어온 것
// 	System.out.println("portnumber : " + portnumber);
	// portnumber : 9090
	
	String serverName ="http://" + serverIP + ":" + portnumber;	// 주소값 풀네임 만들어서 가져온 것
// 	System.out.println("serverName : " + serverName);
	// serverName : http://192.168.50.39:9090

%>     
  <div id="wrapper"> 

	<!-- 사이드바 내용은 요기에 추가 2020/01/03 kkh -->
    <!-- Sidebar -->
    <ul class="sidebar navbar-nav">
      <li class="nav-item active">
        <a class="nav-link" href="<%= ctxPath%>/main.action">
          <span>HOME</span>
        </a> 
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="pagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-fw fa-folder"></i>
          <span>게시판</span>
        </a>
        <div class="dropdown-menu" aria-labelledby="pagesDropdown">
          <a class="dropdown-item" href="<%= ctxPath%>/noticeBoard.action">전체게시판</a>
          <a class="dropdown-item" href="<%= ctxPath%>/deptBoard.action">업무게시판</a>
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
          
          <h6 class="dropdown-header">결재보관함</h6>
          <a class="dropdown-item" href="<%= ctxPath%>/incomplete_payment_archive.action">결재완료/미결재 문서</a>
          <a class="dropdown-item" href="<%= ctxPath%>/receive_payment_archive.action">결재문서 수신함</a>
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
          <a class="dropdown-item" href="<%= ctxPath%>/my_report_archive.action">보고서함</a>
          <a class="dropdown-item" href="<%= ctxPath%>/receive_report_archive.action">보고서 수신함</a>
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
          <a class="dropdown-item" href="http://localhost:9090/controller/addressBook.action">개인 주소록</a>
          <a class="dropdown-item" href="http://localhost:9090/controller/addressOpenBook.action">공용 주소록</a>
          <div class="dropdown-divider"></div>
        </div>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="pagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-fw fa-folder"></i>
          <span>채팅</span>
        </a>
        <div class="dropdown-menu" aria-labelledby="pagesDropdown">
          <a class="dropdown-item" href="<%= serverName%><%=ctxPath%>/chatting.action">채팅</a>
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
          <a class="dropdown-item" href="<%= ctxPath%>/admintotalexpenditure.action">지출통계</a>
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
          <a class="dropdown-item" href="<%= ctxPath%>/positionmanagement.action">직위관리</a>
          <h5 style="font-weight: blod;" class="dropdown-header">인사관리</h5>
          <a class="dropdown-item" href="<%= ctxPath%>/joinSawon.action">입사처리</a>
          <a class="dropdown-item" href="<%= ctxPath%>/personnelAnnouncement.action">인사이동</a>
          <a class="dropdown-item" href="<%= ctxPath%>/personnelAnnouncementList.action">인사관리내역</a>
          <div class="dropdown-divider"></div>
        </div>
      </li>
    </ul>