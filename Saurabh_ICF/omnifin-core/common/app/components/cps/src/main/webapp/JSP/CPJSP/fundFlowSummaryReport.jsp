
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp"%>
<%@include file="/JSP/commonIncludeContent.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="" />

<link type="text/css"
	href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css"
	rel="stylesheet" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>

<title>Reports</title>

<script type="text/javascript"	src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/js/cpScript/fundFlowAnalysis.js"></script>
<script type="text/javascript"	src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
</head>

<body onload="enableAnchor();checkChanges('SummaryReportForm');">
	<html:form action="/SummaryReport" styleId="SummaryReportForm">
	<input type="hidden" name="contextPath"
								value="<%=request.getContextPath()%>" id="contextPath" />
		<fieldset>
			<legend>
				<bean:message key="lbl.collSummary" />
			</legend>
			<input type="hidden" id="businessdate"
				value='${userobject.businessdate}' /> <input type="hidden"
				name="formatD" id="formatD"
				value="<bean:message key="lbl.dateFormat"/>" /> <input
				type="hidden" name="format" id="format" value="F" /> <input
				type="hidden" name="path" id="path"
				value="<%=request.getContextPath()%>" />
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<html:hidden property="caseId" styleId="caseId"
					value="${requestScope.caseId}" />

				<table cellSpacing=0 cellPadding=0 width="100%" border=0>
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
								<tr class="white2">
									<td><b><bean:message key="lbl.dealNo" /></b></td>
									<td>${dealHeader[0].dealNo}</td>
									<td><b><bean:message key="lbl.date" /></b></td>
									<td>${dealHeader[0].dealDate}</td>
									<td><b><bean:message key="lbl.customerName" /></b></td>
									<td colspan="3">${dealHeader[0].dealCustomerName}</td>
								</tr>
								<tr class="white2">
									<td><b><bean:message key="lbl.productType" /></b></td>
									<td>${dealHeader[0].dealProductCat}</td>
									<td><b><bean:message key="lbl.product" /></b></td>
									<td>${dealHeader[0].dealProduct}</td>
									<td><b><bean:message key="lbl.scheme" /></b></td>
									<td>${dealHeader[0].dealScheme}</td>
									<td><b><bean:message key="lbl.currentStage" /></b></td>
									<td>Fund Flow Analysis</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset>
					<table ccellspacing="1" cellpadding="1" width="100%" border=0>
						<tr>

							<td>
									<bean:message key="lbl.ReportType" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td><html:select property="reportName" styleId="reportName" onchange="changeReportDay();" styleClass="text" >
										<html:option value="">--Select--</html:option>
										<html:option value="Fund_Flow_Summary_Report">Fund Flow Summary Detail Report</html:option>
										<html:option value="Average_Bank_Bal_Calc_Report">Average Bank Bal Calc Report</html:option>
									</html:select></td>

							<td><bean:message key="lbl.reporttype" /></td>
							<td><html:select property="reporttype" styleId="reporttype"	styleClass="text">
									 <html:option value="">--Select--</html:option> 
									<html:option value="P">PDF</html:option>
									 <html:option value="E">Excel</html:option> 
								</html:select></td>

						</tr>
						
					
					</table>
					<table cellSpacing=0 cellPadding=0 width="50%" border=0>

						<tr>
							<td>
								<button type="button" name=" mybutton" title="Alt+G"
									accesskey="G" class="topformbutton3"
									onclick="generateReport();">
									<bean:message key="button.generate" />
							</td>
						</tr>
					</table>
				</fieldset>
				</html:form>
				<div align="center" class="opacity" style="display: none;"
					id="processingImage"></div>
				<script>
					setFramevalues("SummaryReportForm");
				</script>
</body>
</html>