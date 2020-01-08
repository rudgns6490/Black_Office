<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- === tiles 를 사용하는 레이아웃1 페이지 만들기 --%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>  

<%
	String ctxPath = request.getContextPath();
    //    /startspring
%>   
 
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Black Office</title>
  
  <!-- 공통적인 css, js 를 추가해야되는 경우 여기 추가 2020/01/03 kkh -->

  <!-- Custom fonts for this template-->
  <link href="<%= ctxPath %>/resources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

  <!-- Page level plugin CSS-->
  <link href="<%= ctxPath %>/resources/vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="<%= ctxPath %>/resources/css/sb-admin.css" rel="stylesheet">
  
  <!-- Bootstrap core JavaScript-->
  <script src="<%= ctxPath %>/resources/vendor/jquery/jquery.min.js"></script>
  <script src="<%= ctxPath %>/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="<%= ctxPath %>/resources/vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Page level plugin JavaScript-->
  <!-- 차트 js 경우 차트를 만들지 않을경우에 있으면 null error 가 발생하므로 주석
  	    추후에 차트를 사용하는 부분에서만 추가해서 사용하면 된다. 2020/01/03 kkh -->
  <%-- <script src="<%= ctxPath %>/resources/vendor/chart.js/Chart.min.js"></script> --%>
  <script src="<%= ctxPath %>/resources/vendor/datatables/jquery.dataTables.js"></script>
  <script src="<%= ctxPath %>/resources/vendor/datatables/dataTables.bootstrap4.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="<%= ctxPath %>/resources/js/sb-admin.min.js"></script>

  <!-- Demo scripts for this page-->
  <script src="<%= ctxPath %>/resources/js/demo/datatables-demo.js"></script>
  <!-- 차트 js 경우 차트를 만들지 않을경우에 있으면 null error 가 발생하므로 주석
  	    추후에 차트를 사용하는 부분에서만 추가해서 사용하면 된다. 2020/01/03 kkh -->
  <%-- <script src="<%= ctxPath %>/resources/js/demo/chart-area-demo.js"></script> --%>

</head>
<body id="page-top">
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="sideinfo" />
	<tiles:insertAttribute name="content" />
</body>
</html>





