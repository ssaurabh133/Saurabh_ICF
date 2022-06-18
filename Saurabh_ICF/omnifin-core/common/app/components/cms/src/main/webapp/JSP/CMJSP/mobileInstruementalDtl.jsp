<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>
<title><bean:message key="a3s.noida" /></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript" 	src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/capsScript/contactRecordingScript.js"></script>

<script type="text/javascript">
function fntab()
{
	document.getElementById('loanNoButton').focus();
	return true;
}
	
</script>
</head>

<body onload="enableAnchor();fntab();">
<html:form styleId="mobilePayInSlip"  method="post"  action="/mobliePayInSlipAction" enctype="multipart/form-data" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
<fieldset>
<legend><bean:message key="lbl.mobileInstrumentDetails" /></legend>
				<logic:notEmpty name="mobInstrumentDtl">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr><td class="gridtd">
						<table width="100%" border="0" cellspacing="1" cellpadding="1" id="InstrumentTable">
						<tr class="white2">
							<td><b><bean:message key="lbl.payInSlipNo"/></b></td>
							<td><b><bean:message key="lbl.instrumentID"/></b></td>
							<td><b><bean:message key="lbl.recipetMode"/></b></td>
							<td><b><bean:message key="lbl.receivedDate"/></b></td>
							<td><b><bean:message key="lbl.instrument.date"/></b></td>
							<td><b><bean:message key="lbl.instrumetAmount"/></b></td>
							<td><b><bean:message key="lbl.uploadedBy" /></b></td>
							<td><b><bean:message key="lbl.uploadedDate" /></b></td>
							
						</tr>
						<logic:present name="mobInstrumentDtl">
						<logic:iterate id="list" name="mobInstrumentDtl" indexId="count">
						<tr class="white1">
							<td>${list.payInSlipId}</td>
							<td>${list.instrumentID}</td>
							<td>${list.instrumentMode}</td>
							<td>${list.receivedDate}
							<td>${list.instrumentDate}</td>
							<td>${list.instrumentAmount}</td>
							<td>${list.makerName}</td>
							<td>${list.makerDate}</td>
						</tr>
						</logic:iterate> 
						</logic:present>
						</table>
					</td></tr>
					</table>
				</logic:notEmpty>
				<logic:empty name="mobInstrumentDtl" >
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr><td class="gridtd">
						<table width="100%" border="0" cellspacing="1" cellpadding="1" id="InstrumentTable">
						<tr class="white2">
							<td><b><bean:message key="lbl.payInSlipNo"/></b></td>
							<td><b><bean:message key="lbl.instrumentID"/></b></td>
							<td><b><bean:message key="lbl.recipetMode"/></b></td>
							<td><b><bean:message key="lbl.receivedDate"/></b></td>
							<td><b><bean:message key="lbl.instrument.date"/></b></td>
							<td><b><bean:message key="lbl.instrumetAmount"/></b></td>
							<td><b><bean:message key="lbl.uploadedBy" /></b></td>
							<td><b><bean:message key="lbl.uploadedDate" /></b></td>
						</tr>
						<tr class="white2" >
							<td colspan="8"><bean:message key="lbl.noDataFound" /></td>
						</tr>
						</table>
						</td>
					</tr>
				</table>
				</logic:empty>
</fieldset>
</html:form>

<logic:present name="sms">
<script type="text/javascript">


if('<%=request.getAttribute("sms").toString()%>'=='S')
{
	alert("<bean:message key="lbl.dataSave" />");
	window.close();
}
else if('<%=request.getAttribute("sms").toString()%>'=='I')
{
	alert("<bean:message key="lbl.dataInitiatedCM" />");

}
</script>
</logic:present>
 </body>
</html:html>