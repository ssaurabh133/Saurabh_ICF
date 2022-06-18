<!--
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface for Update Rating
 	Documentation    :- 
 -->


<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 <link type="text/css" rel="stylesheet" href="css/contentstyle.css"/>
		 <link type="text/css" rel="stylesheet" href="css/subpage.css"/>
	   
		 
	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customer_address.js"></script>
		
		
	</head>
  
  <body onload="enableAnchor();">
  <script type="text/javascript">
     alert("Rating Details are successfully updated!!!");
     javascript:window.top.close();
     
  </script>
  <p><br>
  </p>
  <p>&nbsp;</p>
  <table width="100%" border="0" align="center" cellpadding="4" cellspacing="1">
    <tr>
      <td width="30%">&nbsp;</td>
      <td><h2><font color="red">${requestScope.sms}</font></h2></td>
     
    </tr>
    
  </table>
  </body>
</html:html>
