<%@ page language="java"  pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title><bean:message key="title.name"/></title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body onload="enableAnchor();" oncontextmenu="return false" onclick="parent_disable();">
  <table width="100%" height="100%">
  <tr>

  <td align="center" valign="middle">
   <logic:present name="image">
    	 <img ondragstart="return false" onselectstart="return false" src="${image }/CommonLogo.jpg" width="301" height="126"></img>
       </logic:present>
    	<logic:notPresent name="image">
    		 <img ondragstart="return false" onselectstart="return false" src="images/theme1/CommonLogo.jpg" width="301" height="126"></img>
    	</logic:notPresent>
   
  </td>

  </tr>
  </table>
  </body>
</html>
