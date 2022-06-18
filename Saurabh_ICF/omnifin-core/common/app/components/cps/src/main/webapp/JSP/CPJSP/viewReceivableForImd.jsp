<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
<html:html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cmScript/cmLoanInitjs.js"></script>
</head>
<body onload="enableAnchor();init_fields()">
	<div id="revisedcontainer">
			<fieldset>
				<legend>
					<bean:message key="lbl.imdDetails" />
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
												<b><bean:message key="lbl.chargeCode" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.chargeDesc" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.chargeBPN" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.taxIncl" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.taxRate1" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.taxRate2" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.bpMethod" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.chargeAmount" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.chargeFinal" />
												</b>
											</td>
										</tr>

								<logic:present name="ReceivableList">
									<logic:iterate name="ReceivableList" id="subcharges">
												<tr class="white1">
													<html:hidden property="chargeIdDtl"
														value="${subcharges.chargeId}" />
													<td>
														${subcharges.chargeCode}
													</td>
													<td>
														${subcharges.chargeDesc}
													</td>
													<td>
														${subcharges.chargeBPId}
													</td>
													<td>
														${subcharges.taxsInclusive}
													</td>
													<td>
														${subcharges.taxtRat1}
													</td>
													<td>
														${subcharges.taxtRat2}
													</td>
													<td>
														${subcharges.chargeMethod}
													</td>
													<td>
														${subcharges.chargeCal}													
													</td>
													<td>
														${subcharges.chargeFinal}
													</td>

												</tr>
											</logic:iterate>
										</logic:present>
										
							<!--  Code by Rajesh Kumar - Start -->
							<logic:present name="printTotalAmounts">
								<tr class="white2">
									<td><strong>Total </strong></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td><b>${totalFinalAmount} </b></td>
								</tr>
							</logic:present>
							<!--  Code by Rajesh Kumar - End -->	
																
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<button type="button" name="close" id="close" title="Alt+C" accesskey="C"
								class="topformbutton2" onclick="javascript:window.close();"><bean:message key="button.close" /></button>
						</td>
					</tr>
				</table>
			</fieldset>
	</div>
</body>
</html:html>