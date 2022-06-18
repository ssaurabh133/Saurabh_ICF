<!-- 
Author Name :- Manisha Tomar
Date of Creation :12-07-2011
Purpose :-  screen for the view Receivable for early closure
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

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cmScript/cmLoanInitjs.js"></script>
</head>
<body onload="enableAnchor();init_fields()">
	<div id="revisedcontainer">
			<fieldset>
				<legend>
					<bean:message key="lbl.receiptDetails" />
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
										<strong><bean:message key="lbl.date" />
										</strong>
									</td>
									<td>
										<strong><bean:message key="lbl.business_partner_type" />
										</strong>
									</td>
									<td>
										<strong><bean:message key="lbl.chargeDescription" />
										</strong>
									</td>
									<td>
										<strong><bean:message key="lbl.originalAmount" />
										</strong>
									</td>
									<td>
										<strong><bean:message key="lbl.waivedOffAmount" />
										</strong>
									</td>
									<td>
										<strong><bean:message key="lbl.adviceAmount" />
										</strong>
									</td>
									
									<td>
										<b><bean:message key="lbl.adjustedAmount" />
										</b>
									</td>
									<td>
										<b><bean:message key="lbl.amountInProcess" />
										</b>
									</td>
									<td>
										<b><bean:message key="lbl.balanceAmount" /> </b>
									</td>
								</tr>
								<logic:present name="ReceivableList">
									<logic:iterate name="ReceivableList" id="subReceivableList"
										indexId="count">
										<tr class="white1">
											<td>
												${subReceivableList.paymentDate}
											</td>
											<td>
												${subReceivableList.businessPartnerType}
											</td>
											<td>
												${subReceivableList.chargeDesc}
											</td>
											<td>
												${subReceivableList.originalAmount}
													
											</td>
											<td>
												${subReceivableList.waiveOffAmount}
													
											</td>
											<td>
												${subReceivableList.adviceAmount}
													
											</td>
											
											<td>
												${subReceivableList.adjustedAmount}
													
											</td>
											<td>
												${subReceivableList.amountInProcess}
													
											</td>
											<td>
												${subReceivableList.balanceAmount}
													
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
									<td><strong>${totalOriginalAmount} </strong></td>
									<td><strong>${totalWaivedOffAmount} </strong></td>
									<td><strong>${totalAdviceAmount}</strong></td>
									<td><b>${totalAdjustedAmount}</b></td>
									<td><b>${totalAmountInProcess} </b></td>
									<td><b>${totalBalanceAmount} </b></td>
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