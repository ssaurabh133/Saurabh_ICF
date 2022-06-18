<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.logger.LoggerMsg"%>
<%@page import="com.connect.CommonFunction"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page import="java.util.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />		 
		<!-- css for Datepicker -->
		
		<link type="text/css" href="<%=request.getContextPath() %>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.core.js"></script>
		
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript"  src="<%=request.getContextPath() %>/js/cmScript/bulkReportGeneration.js"></script>
		<script type="text/javascript"  src="<%=request.getContextPath() %>/js/sczScript/securitizationReportGeneration.js"></script>
	</head>
	<body onload="enableAnchor();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<html:form styleId="sourcingForm" action="/paymentScheduleProcessAction" method="post" >   
	 <input type="hidden" id="contextPath" value="<%=request.getContextPath() %>" /> 
	<fieldset><legend><bean:message key="lbl.paymentScheduleSearch"/></legend>  
		
		<legend><bean:message key="lbl.UploadData"/></legend>  	 
		<table width="100%"  border="0" cellspacing="1" cellpadding="4">
		<tr>
			<td><bean:message key="lbl.poolID"/></td>
			<td>
				<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />  
				<html:text styleClass="text" readonly="true" property="poolID" styleId="poolID" maxlength="100"  readonly="true" tabindex="-1"/>   
   				<html:hidden property="lbxPoolID" styleId="lbxPoolID" />
    			<input type="hidden" name="hcommon" id="hcommon" />
	 			<html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(12001,'sourcingForm','poolID','userId','poolID', 'userId','','','poolName','poolCreationDate');" value=" " styleClass="lovbutton"></html:button>   
           	</td>
           	<td><bean:message key="lbl.PoolName"/></td>
		 	<td><html:text styleClass="text" property="poolName" readonly="true" disabled="true" styleId="poolName"  maxlength="50"  /> </td>
		</tr>
		<tr>   
			<td><bean:message key="lbl.PoolCreationDate"/></td>
			<td><html:text styleClass="text" property="poolCreationDate" readonly="true" disabled="true" styleId="poolCreationDate" onchange="checkDate('poolCreationDate')" /></td>
     	</tr>
		<tr>
		    <td align="left">  
				<button type="button" name="button" id="search" class="topformbutton3" title="Alt+S" accesskey="S" onclick="return generateReportForPaymentSchedule();">GenerateReport</button> 
			</td>
		</tr>
		</table>
    </fieldset>
	</html:form>
</body>
</html:html>
<logic:present name="msg">
	<script type="text/javascript">
	alert('${msg}');
	</script>
</logic:present>
