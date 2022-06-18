<!-- 
Author Name :- Pranaya
Date of Creation :05-09-2016
-->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>

<!-- css and jquery for Datepicker -->
<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>

<!-- css and jquery for Datepicker -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/omniFinReportScript.js"></script>
<%
//String path = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
</head>

<body onload="enableAnchor();document.getElementById('interestWorking').interestWorkingDate.focus();init_fields();">
<div id="centercolumn">
<div id="revisedcontainer">
<logic:present name="image">
<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
</logic:present>
<logic:notPresent name="image">
<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
</logic:notPresent>
<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />

<html:form action="/interestWorking" method="POST" styleId="interestWorking">
	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	<fieldset>	  
	<legend><bean:message key="lbl.interestWorkingReport"/> </legend>         
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
		<td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<%-- <tr>
			<td><bean:message key="lbl.loanNumber"/></td>
			<td><html:text property="loanNo" styleClass="text" styleId="loanNo" />
			<html:hidden  property="lbxLoanNo" styleId="lbxLoanNo" />
			<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
			<html:button property="loanButton" styleId="loanButton" 
			 			 onclick="openLOVCommon(85,'InterestWorkingForm','loanNo','userId','loanNo', 'userId','','','hCommon');closeLovCallonLov1();clearLoan();" 
			 			 value=" " styleClass="lovbutton">
			</html:button></td>
		</tr>	 --%>		
		<%-- <tr>
			<td><bean:message key="lbl.FromDate"/><bean:message key="lbl.dateFormat"/></td>
	        <td><html:text property="interestWorkingFromDate" styleClass="text" styleId="interestWorkingFromDate"/></td>
	        
	        <td><bean:message key="lbl.ToDate"/><bean:message key="lbl.dateFormat"/></td>
	        <td><html:text property="interestWorkingToDate" styleClass="text" styleId="interestWorkingToDate"/></td>
			
			<td></td>
			<td></td>
		</tr> --%>
		<tr>
		<td width="13%">Report Name</td>
		<td>
		<html:select property="reportName" styleId="reportName" styleClass="text">
			<html:option value="">--Select--</html:option>
			<html:option value="iwr">Interest Working Report</html:option>
		</html:select>
		</td>
		</tr>
		
		<tr>
		<td>
		<button type="button" value="" class="topformbutton3"  title="Alt+G" accesskey="G" 
				onclick="return viewInterestWorking();"> <bean:message key="button.generate" /></button>
				<%-- '<bean:message key="msg.gendate"/>' --%>
		</td>
		<td nowrap="nowrap" >&nbsp;</td>
		<td></td>
		<td ></td>
		</tr>
		</table>
		</td>
		</tr>
	</table>
	</fieldset>	
</html:form>
</div>
</div>
</body>
</html:html>