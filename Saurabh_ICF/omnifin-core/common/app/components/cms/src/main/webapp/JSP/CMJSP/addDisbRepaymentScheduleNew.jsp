<!-- 
Author Name :- Prashant kumar
Date of Creation :02/12/2011
Purpose :-  screen for the view Repament Schedule.
-->


<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
<html:html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />

 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cmScript/cmLoanInitjs.js"></script>
</head>
<body onload="enableAnchor();init_fields();onLoadPage()">
	<div id="revisedcontainer">
		<fieldset>
			<legend>
				<bean:message key="lbl.repaySDetail" />
			</legend>
			<table cellspacing="0" cellpadding="1" border="0" width="100%">
				<tr>
					<td>
						<bean:message key="lbl.repaySflatRate" />
					</td>
					<td>
						<input type="text" class="text" name="Input" disabled="disabled"
							style="text-align: right" value="${repShedule[0].finalRate}" />
					</td>
					<td>
						<bean:message key="lbl.repaySeffectRate" />
					</td>
					<td>
						<input type="text" class="text" name="Input32" disabled="disabled"
							style="text-align: right" value="${repShedule[0].effectiveRate}" />
					</td>
				</tr>
				<tr>
					<td>
						<bean:message key="lbl.repaySMKTIRR1" />
					</td>
					<td>
						<input type="text" class="text" name="Input3" disabled="disabled"
							value="${repShedule[0].mktIRR1}" style="text-align: right" />
					</td>
					<td>
						<bean:message key="lbl.repaySMKTIRR2" />
					</td>
					<td>
						<input type="text" class="text" name="Input3" disabled="disabled"
							value="${repShedule[0].mktIRR2}" style="text-align: right" />
					</td>
				</tr>
				<tr>
				<td>
					<bean:message key="lbl.dealSanctionedIrr"/>
				</td>
				<td>
					<input type="text" class="text" name="Input4" disabled="disabled"
						value="${repShedule[0].dealIRR2}" style="text-align: right" />
				</td>
				<td></td>
				<td></td>
				</tr>
			</table>
		</fieldset>
		
		<fieldset>
			<legend>
				<bean:message key="lbl.repaySDetail" />
			</legend>
			<input type="hidden" name="contextPath"
				value="<%=request.getContextPath()%>" id="contextPath" />
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="gridtd">
						<table width="100%" border="0" cellspacing="1" cellpadding="4"
							id="gridTable">
							<tr class="white2">
								<td>
									<strong><bean:message key="lbl.repaySIns" /> </strong>
								</td>
								<td>
									<strong><bean:message key="lbl.repaySDue" /> </strong>
								</td>
								<td>
									<b><bean:message key="lbl.repaySInsAm" /> </b>
								</td>
								<td>
									<strong><bean:message key="lbl.repaySPrinc" /> </strong>
								</td>
								<td>
									<strong><bean:message key="lbl.repaySInterest" /> </strong>
								</td>
								<td>
									<strong><bean:message key="lbl.repaySExcess" /> </strong>
								</td>
								<td>
									<strong><bean:message key="lbl.repaySPrincOS" /> </strong>
								</td>
								<td>
									<strong><bean:message key="lbl.repaySAdF" /> </strong>
								</td>

							</tr>
							<logic:present name="repShedule">
								<logic:iterate name="repShedule" id="repSheduleOb">
									<tr class="white1">
										<td style="text-align: right">
											${repSheduleOb.instNo}
										</td>
										<td>
											${repSheduleOb.dueDate}
										</td>
										<td style="text-align: right">
											${repSheduleOb.instAmount}
										</td>
										<td style="text-align: right">
											${repSheduleOb.principle}
										</td>
										<td style="text-align: right">
											${repSheduleOb.instCom}
										</td>
										<td style="text-align: right">
											${repSheduleOb.excess}
										</td>
										<td style="text-align: right">
											${repSheduleOb.prinOS}
										</td>
										<td>
											${repSheduleOb.advFlag}
										</td>
									</tr>
								</logic:iterate>
							</logic:present>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<html:button property="close" styleId="close" value="Close"
							styleClass="topformbutton2" onclick="javascript:window.close();"></html:button>
					</td>
				</tr>
			</table>
		</fieldset>
	</div>
</body>
</html:html>

<logic:present name="noData">
	<script type="text/javascript">
		alert('<bean:message key="lbl.ProcNotfound" />');
		self.close();
	</script>
</logic:present>
<logic:present name="procStatus">
	<script type="text/javascript">
		alert('${procStatus}');
		self.close();
	</script>
</logic:present>
