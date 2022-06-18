<!--
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface for Approve success
 	Documentation    :- 
 -->
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body onload="enableAnchor();">
<!--    <script type="text/javascript">-->
<!--     alert("Customer is Approved Successfully!!!");-->
<!--     javascript:window.top.close();-->
<!--     -->
<!-- </script>-->

<script type="text/javascript">
     alert("Customer is Approved Successfully!!!");
     window.opener.location.reload();
     javascript:window.top.close();
     
  </script>
  </body>
</html>
