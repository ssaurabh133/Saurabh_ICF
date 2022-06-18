<!--
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface for Save And Forward
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
    
    <title>My JSP 'saveAndForward.jsp' starting page</title>
    
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
  <logic:present name="Individual">
  	<script type="text/javascript">
     	alert("This record is successfully save and ready for approval!!!");
       	parent.location="<%=request.getContextPath()%>/individualFrame.do";          
  	</script>
  </logic:present>
  <logic:present name="Corporate">
     <script type="text/javascript">
     	alert("This record is successfully save and ready for approval!!!");
       	parent.location="<%=request.getContextPath()%>/corporateFrame.do";          
  	</script>
  </logic:present>
    <logic:present name="updateInMaker">
     <script type="text/javascript">
     	alert("This record is successfully save and ready for approval!!!");
       	parent.location="<%=request.getContextPath()%>/updateCustomerMaker.do";        
  	</script>
  </logic:present>
     <logic:present name="forwardFromUPMaker">
     <script type="text/javascript">
     	alert("This record is successfully save and ready for approval!!!");
       	parent.location="<%=request.getContextPath()%>/updateCustomerMaker.do";        
  	</script>
  </logic:present>
  
  </body>
</html>
