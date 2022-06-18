<!-- 
Author Name :- Aditi
Date of Creation :25-01-2012
Purpose :-  screen for the Showing All Logged User 
-->

<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />

		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->

		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/report/report.js"></script>
		
	</head>
	<body onload="enableAnchor();">
	<html:form action="/userLoggedInReportBehindAction" method="post" styleId="userLoggedForm">

<fieldset><legend><bean:message key="lbl.userLogged"/></legend>  
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
<!--<html:hidden property="reportId" styleId="contextPath" value="" />-->
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
     <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
    	  <td align="center"><b><bean:message key="lbl.userName"/></b></td>
         <td align="center"><strong><bean:message key="lbl.userEmpID"/></strong></td>
	     <td align="center"><strong><bean:message key="lbl.branchName"/></strong></td>
         <td align="center"><strong><bean:message key="lbl.department"/></strong></td>
         <td align="center"><strong><bean:message key="lbl.userReportingTo"/></strong></td>
	     <td align="center"><strong><bean:message key="lbl.mobileNo"/></strong></td>
	     <td align="center"><strong><bean:message key="lbl.loginStatus"/></strong></td>
	     <td align="center"><strong><bean:message key="lbl.ipAddress"/></strong></td>
	  </tr>
	<logic:present name="list">	
		<logic:iterate name="list" id="sublist">
		<tr class="white1">
	    	<td>${sublist.userName}</td>
	    	<td>${sublist.userEmpID}</td>
	    	<td>${sublist.branchName}</td>
	    	<td>${sublist.department}</td>
	    	<td>${sublist.reportingTo}</td>
	    	<td>${sublist.mobileNo}</td>
	    	<td>${sublist.loginStatus}</td>
	    	<td>${sublist.ipAddress}</td>
     </tr> 
    </logic:iterate>
   </logic:present>
   </table>
   </td>
   </tr>
   	</table>
   	<table width="100%" border="0" cellspacing="1" cellpadding="1">
   	<tr>
  	 <td>
   	<button type="button" name="mybutton" title="Alt+R" accesskey="R"  class="topformbutton2" onclick="loggedUserRefresh();"  ><bean:message key="button.refresh" /></button>
  		 </td>
   	</tr>
   	</table>
   </fieldset>
 
  </html:form>
</body>
</html>