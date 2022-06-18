<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>

	<%
		String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":"
					+ request.getServerPort() + path + "/";
	%>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cmScript/cmLoanInitjs.js"></script>

	
</head>
<body onload="enableAnchor();init_fields();">
	<div id="centercolumn">
		<div id="revisedcontainer">
		<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>"/>

				<fieldset>
					<legend>
						<bean:message key="lbl.disbursalSchedule"/>
					</legend>

					<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="gridtd">

						<table width="100%" border="0" cellspacing="1" cellpadding="4">
						<logic:present name="disbursalScheduleList">
							<tr class="white2">
								<td>
									<strong><bean:message key="lbl.loanNo"/></strong>
								</td>
								<td>
									<strong><bean:message key="lbl.disbursalNo"/></strong>
								</td>
								<td>
									<b><bean:message key="lbl.disbursalDescription"/></b>
								</td>
								<td>
									<strong><bean:message key="lbl.proposedDisbursalDate"/></strong>
								</td>
								<td>
									<b><bean:message key="lbl.proposedDisbursalAmount"/></b>
								</td>
<!--								<td>-->
<!--									<strong><bean:message key="lbl.disbursalStatus"/></strong>-->
<!--								</td>-->
							</tr>
							<logic:notEmpty name="disbursalScheduleList">
							<logic:iterate name="disbursalScheduleList" id="sublist">
							<tr class="white1">
								<td>
									${sublist.loanNo}
								</td>
								<td>
									${sublist.disbursalNo}
								</td>
								<td>
									${sublist.disbursalDescription}
								</td>
								<td>
									${sublist.proposedDisbursalDate}
								</td>
								<td>
									${sublist.proposedDisbursalAmount}
								</td>
<!--								<td>-->
<!--									<bean:write name="sublist" property="finalDisbursal"/>-->
<!--								</td>-->
							</tr>
							</logic:iterate>
							</logic:notEmpty>
							</logic:present>
						</table>
					</td>
				</tr>
			</table>
			</fieldset>
		</div>

		<fieldset>
			<legend>
				<bean:message key="lbl.disbursalRecords"/>
			</legend>


			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="gridtd">

						<table width="100%" border="0" cellspacing="1" cellpadding="4">
						<logic:present name="disbursalDataList">
							<tr class="white2">
								<td>
									<strong><bean:message key="lbl.loanNo"/></strong>
								</td>
								<td>
									<strong><bean:message key="lbl.disbursalNo"/></strong>
								</td>
								<td>
									<b><bean:message key="lbl.disbursalDescription"/></b>
								</td>
								<td>
									<strong><bean:message key="lbl.disbursalDate"/></strong>
								</td>
								<td>
									<b><bean:message key="lbl.disbursalAmount"/></b>
								</td>
								<td>
									<strong><bean:message key="lbl.disbursalStatus"/></strong>
								</td>
							</tr>
							<logic:notEmpty  name="disbursalDataList">
							<logic:iterate name="disbursalDataList" id="sublist">
							<tr class="white1">
							<td>
									${sublist.loanNo}
								</td>
								<td>
									${sublist.disbursalNo}
								</td>
								<td>
									${sublist.disbursalDescription}
								</td>
								<td>
									${sublist.disbursalDate}
								</td>
								<td>
									${sublist.disbursalAmount}
								</td>
								<td>
									${sublist.finalDisbursal}
								</td>

							
							</tr>
							</logic:iterate>
							</logic:notEmpty>
							</logic:present>
						</table>
					</td>
				</tr>
			</table>

		</fieldset>
		<!--<html:button property="close" styleId="close" value="Close" styleClass="topformbutton2" onclick="javascript:window.close();"></html:button>-->
		<button type="button" class="topformbutton2"  name="close" id="close" onclick="window.close();" title="Alt+C" accesskey="C" ><bean:message key="button.close" /></button>
	</div>
</body>
</html:html>