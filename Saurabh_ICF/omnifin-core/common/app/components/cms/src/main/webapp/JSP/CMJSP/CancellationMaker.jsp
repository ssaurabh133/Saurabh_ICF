<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.login.roleManager.UserObject"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	UserObject userobj = (UserObject) session
			.getAttribute("userobject");
	String initiationDate = userobj.getBusinessdate();
%>

<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
<html:html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
	
<!--[if IE]>
	<style type="text/css">
		.opacity{
			 position:fixed;
  			 _position:absolute;
  			 top:0;
 			 _top:expression(eval(document.body.scrollTop));
		}
	</style>
<! [endif] -->

</head>
<script type="text/javascript" 
		src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/cmScript/loanClosure.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
<script type="text/javascript">
	function fntab() {
		if (document.getElementById('modifyRecord').value == 'N') {
			document.getElementById('cancellationForm').loanAcButton.focus();
		} else if (document.getElementById('modifyRecord').value == 'CM') {
			document.getElementById('cancellationForm').reasonForClosure
					.focus();
		}
		return true;
	}
</script>

<body oncontextmenu="return false" onload="enableAnchor();checkChanges('cancellationForm');fntab();init_fields();"
	onclick="parent_disable();"
onunload="closeAllLovCallUnloadBody();">

	<input type="hidden" name="formatD" id="formatD"
		value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" name="businessDate" id="businessDate"
		value="<%=initiationDate%>" />
		<input type="hidden" id="businessDate" value="${sessionScope.businessDate}" />
		<input type="hidden" id="makerDate" value="${sessionScope.makerDate}" />
		<input type="hidden" id="checkFlag" value="${sessionScope.checkFlag}" />
	<div id="centercolumn">
		<div id="revisedcontainer">

			<!-- ********************************************* For New Cancellation **************************************************** -->

			<logic:present name="cancellationNew">
				<input type="hidden" id="modifyRecord" name="modifyRecord" value="N" />
				<html:form action="/cancellation" styleId="cancellationForm"
					method="post">
					<html:javascript formName="CancellationMakerDynaValidatorForm" />
					<html:errors />
					<fieldset>
						<LEGEND>
							<bean:message key="lbl.cancellation" />
						</LEGEND>

						<table width="100%" border="0" cellspacing="1" cellpadding="1">
							<html:hidden property="closureType" styleId="closureType"
								value="X" />
							<tr>

								<td>
									<bean:message key="lbl.loanAc" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td>
									<html:text styleClass="text" property="loanAc" styleId="loanAc"
										value="" readonly="true" />
									<html:button
										onclick="openLOVCommon(176,'cancellationForm','loanAc','','', '','','generateValuesCancellation','customerName');closeLovCallonLov1();"
										property="loanAcButton" styleId="loanAcButton" value=" "
										styleClass="lovButton" />
									<input type="hidden" name="contextPath"
										value="<%=request.getContextPath()%>" id="contextPath" />
									<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
										value="" />
								</td>

								<td>
									<bean:message key="lbl.customerName" />
								</td>
								<td>
									<html:text styleClass="text" styleId="customerName"
										tabindex="-1" maxlength="50" disabled="true"
										property="customerName" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.loanDate" />
								</td>
								<td>
									<html:text styleClass="text" styleId="loanDate" maxlength="10"
										disabled="true" property="loanDate" tabindex="-1" />
								</td>
								<td>
									<bean:message key="lbl.loanAmt" />
								</td>
								<td>
									<html:text styleClass="text" styleId="loanAmt" maxlength="18"
										disabled="true" property="loanAmt" tabindex="-1" 
										style="text-align:right;"/>
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.product" />
								</td>
								<td>
									<html:text styleClass="text" styleId="product" disabled="true"
										maxlength="50" property="product" tabindex="-1" />
								</td>
								<td>
									<bean:message key="lbl.scheme" />
								</td>
								<td>
									<html:text styleClass="text" styleId="scheme" disabled="true"
										maxlength="50" property="scheme" tabindex="-1" />
								</td>
							</tr>
							<tr>
								<td>
									<bean:message key="lbl.originalTenure" />
								</td>
								<td>
									<html:text styleClass="text" styleId="originalTenure"
										tabindex="-1" disabled="true" maxlength="4"
										property="originalTenure" style="text-align:right;"/>
								</td>
								<td>
									<bean:message key="lbl.frequency" />
								</td>
								<td>
									<html:text styleClass="text" styleId="frequency" tabindex="-1"
										disabled="true" maxlength="1" property="frequency" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.maturityDate" />
								</td>
								<td>
									<html:text styleClass="text" styleId="maturityDate"
										tabindex="-1" disabled="true" maxlength="10"
										property="maturityDate" />
								</td>
								<td>
									<bean:message key="lbl.remainingTenure" />
								</td>
								<td>
									<html:text styleClass="text" styleId="remainingTenure"
										tabindex="-1" disabled="true" maxlength="4"
										property="remainingTenure" style="text-align:right;"/>
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.cancellationRate" />
								</td>
								<td>
									<html:text styleClass="text" styleId="rate" maxlength="3"
										property="rate" value="" tabindex="-1" disabled="true" 
										style="text-align:right;"/>
								</td>
								<td>
									<bean:message key="lbl.billedPrincipal" />
								</td>
								<td>
									<html:text styleClass="text" styleId="billedPrincipal"
										tabindex="-1" maxlength="18" property="billedPrincipal"
										value="" disabled="true" style="text-align:right;"/>
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.disbursalStatus" />
								</td>
								<td>
									<html:text styleClass="text" styleId="disbursalStatus"
										tabindex="-1" maxlength="10" property="disbursalStatus"
										value="" disabled="true" />
								</td>
								<td>
									<bean:message key="lbl.disbursedAmount" />
								</td>
								<td>
									<html:text styleClass="text" styleId="disbursedAmount"
										tabindex="-1" maxlength="18" property="disbursedAmount"
										value="" disabled="true" style="text-align:right;"/>
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.balancePrincipal" />
								</td>
								<td>
									<html:text styleClass="text" styleId="balancePrincipal"
										tabindex="-1" maxlength="18" property="balancePrincipal"
										value="" disabled="true" style="text-align:right;"/>
								</td>
								<td>
									<bean:message key="lbl.overduePrincipal" />
								</td>
								<td>
									<html:text styleClass="text" styleId="overduePrincipal"
										tabindex="-1" maxlength="18" property="overduePrincipal"
										value="" disabled="true" style="text-align:right;"/>
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.billedInstallments" />
								</td>
								<td>
									<html:text styleClass="text" styleId="billedInstallments"
										tabindex="-1" maxlength="3" property="billedInstallments"
										value="" disabled="true" style="text-align:right;"/>
								</td>
								<td>
									<bean:message key="lbl.billedInstallmentAmount" />
								</td>
								<td>
									<html:text styleClass="text" styleId="billedInstallmentAmount"
										tabindex="-1" maxlength="18" style="text-align:right;"
										property="billedInstallmentAmount" value="" disabled="true" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.receivedInstallmentAmount" />
								</td>
								<td>
									<html:text styleClass="text"
										styleId="receivedInstallmentAmount" maxlength="18"
										disabled="true" property="receivedInstallmentAmount" value=""
										tabindex="-1" style="text-align:right;"/>
								</td>
								<td>
									<bean:message key="lbl.requestNumber" />
								</td>
								<td>
									<html:text styleClass="text" styleId="requestNumber"
										maxlength="10" property="requestNumber" value="" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.reasonForClosure" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td>
									<html:text styleClass="text" styleId="reasonForClosure"
										maxlength="1000" property="reasonForClosure" value="" />
								</td>

								<td nowrap="nowrap">
									<bean:message key="lbl.authorRemarks" />
								</td>
								<td nowrap="nowrap">
									<html:textarea styleClass="text" property="authorRemarks"
										disabled="true" value="${requestScope.authorRemarks}" />
								</td>
							</tr>

							<tr>
								<td colspan="4">
									<button type="button" name="save" id="save" 
										class="topformbutton2" accesskey="V" title="Alt+V"
										onclick="return saveCancellationDetails();" ><bean:message key="button.save" /></button>
									<button type="button" name="saveForward" id="saveForward"
										 class="topformbutton2" accesskey="F"
										title="Alt+F" onclick="return updateDisbursalBeforeSave();" ><bean:message key="button.forward" /></button>
									<button name="view" id="view" accesskey="L"
										title="Alt+L" type="button"
										class="topformbutton3" onclick="return viewLoanDetails();" ><bean:message key="button.viewloandetails" /></button>
									<button type="button"
										class="topformbutton3" accesskey="P" title="Alt+P"
										onclick="return viewPayableEarly('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.viewPayable" /></button>
									<button type="button"
										class="topformbutton3" accesskey="R" title="Alt+R"
										onclick="return viewReceivableEarly('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.viewRecievable" /></button>
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
			</logic:present>

			<!-- ********************************************* For Cancellation Maker **************************************************** -->

			<logic:present name="cancellationData">
				<input type="hidden" id="modifyRecord" name="modifyRecord"
					value="CM" />
				<html:form action="/cancellation" styleId="cancellationForm"
					method="post">
					<html:javascript formName="CancellationMakerDynaValidatorForm" />
					<html:errors />

					<fieldset>
						<LEGEND>
							<bean:message key="lbl.cancellation" />
						</LEGEND>
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
							<html:hidden property="closureType" styleId="closureType"
								value="X" />
							<html:hidden property="terminationId" styleId="terminationId"
								value="${cancellationData[0].terminationId}" />
							<tr>

								<td>
									<bean:message key="lbl.loanAc" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td>
									<html:text styleClass="text" property="loanAc" styleId="loanAc"
										value="${cancellationData[0].loanAc}" readonly="true" />
									<input type="hidden" name="contextPath"
										value="<%=request.getContextPath()%>" id="contextPath" />
									<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
										value="${cancellationData[0].lbxLoanNoHID}" />
								</td>
								<td>
									<bean:message key="lbl.customerName" />
								</td>
								<td>
									<html:text styleClass="text" styleId="customerName"
										tabindex="-1" maxlength="50" disabled="true"
										property="customerName"
										value="${cancellationData[0].customerName}" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.loanDate" />
								</td>
								<td>
									<html:text styleClass="text" styleId="loanDate" maxlength="10"
										disabled="true" property="loanDate" tabindex="-1"
										value="${cancellationData[0].loanDate}" />
								</td>
								<td>
									<bean:message key="lbl.loanAmt" />
								</td>
								<td>
									<html:text styleClass="text" styleId="loanAmt" maxlength="18"
										disabled="true" property="loanAmt" tabindex="-1"
										value="${cancellationData[0].loanAmt}" 
										style="text-align:right;"/>
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.product" />
								</td>
								<td>
									<html:text styleClass="text" styleId="product" disabled="true"
										maxlength="50" property="product" tabindex="-1"
										value="${cancellationData[0].product}" />
								</td>
								<td>
									<bean:message key="lbl.scheme" />
								</td>
								<td>
									<html:text styleClass="text" styleId="scheme" disabled="true"
										maxlength="50" property="scheme" tabindex="-1"
										value="${cancellationData[0].scheme}" />
								</td>
							</tr>
							<tr>
								<td>
									<bean:message key="lbl.originalTenure" />
								</td>
								<td>
									<html:text styleClass="text" styleId="originalTenure"
										tabindex="-1" disabled="true" maxlength="4"
										property="originalTenure" style="text-align:right;"
										value="${cancellationData[0].originalTenure}" />
								</td>
								<td>
									<bean:message key="lbl.frequency" />
								</td>
								<td>
									<html:text styleClass="text" styleId="frequency" tabindex="-1"
										disabled="true" maxlength="1" property="frequency"
										value="${cancellationData[0].frequency}" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.maturityDate" />
								</td>
								<td>
									<html:text styleClass="text" styleId="maturityDate"
										tabindex="-1" disabled="true" maxlength="10"
										property="maturityDate"
										value="${cancellationData[0].maturityDate}" />
								</td>
								<td>
									<bean:message key="lbl.remainingTenure" />
								</td>
								<td>
									<html:text styleClass="text" styleId="remainingTenure"
										tabindex="-1" disabled="true" maxlength="4"
										property="remainingTenure" style="text-align:right;"
										value="${cancellationData[1].remainingTenure}" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.cancellationRate" />
								</td>
								<td>
									<html:text styleClass="text" styleId="rate" maxlength="3"
										property="rate" value="" tabindex="-1" disabled="true"
										value="${cancellationData[0].rate}" />
								</td>
								<td>
									<bean:message key="lbl.billedPrincipal" />
								</td>
								<td>
									<html:text styleClass="text" styleId="billedPrincipal"
										tabindex="-1" maxlength="18" property="billedPrincipal"
										value="" disabled="true" style="text-align:right;"
										value="${cancellationData[0].billedPrincipal}" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.disbursalStatus" />
								</td>
								<td>
									<html:text styleClass="text" styleId="disbursalStatus"
										tabindex="-1" maxlength="10" property="disbursalStatus"
										value="" disabled="true"
										value="${cancellationData[0].disbursalStatus}" />
								</td>
								<td>
									<bean:message key="lbl.disbursedAmount" />
								</td>
								<td>
									<html:text styleClass="text" styleId="disbursedAmount"
										tabindex="-1" maxlength="18" property="disbursedAmount"
										value="" disabled="true" style="text-align:right;"
										value="${cancellationData[0].disbursedAmount}" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.balancePrincipal" />
								</td>
								<td>
									<html:text styleClass="text" styleId="balancePrincipal"
										tabindex="-1" maxlength="18" property="balancePrincipal"
										value="" disabled="true" style="text-align:right;"
										value="${cancellationData[0].balancePrincipal}" />
								</td>
								<td>
									<bean:message key="lbl.overduePrincipal" />
								</td>
								<td>
									<html:text styleClass="text" styleId="overduePrincipal"
										tabindex="-1" maxlength="18" property="overduePrincipal"
										value="" disabled="true" style="text-align:right;"
										value="${cancellationData[0].overduePrincipal}" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.billedInstallments" />
								</td>
								<td>
									<html:text styleClass="text" styleId="billedInstallments"
										tabindex="-1" maxlength="3" property="billedInstallments"
										disabled="true"  style="text-align:right;"
										value="${cancellationData[0].billedInstallments}" />
								</td>
								<td>
									<bean:message key="lbl.billedInstallmentAmount" />
								</td>
								<td>
									<html:text styleClass="text" styleId="billedInstallmentAmount"
										tabindex="-1" maxlength="18" style="text-align:right;"
										value="${cancellationData[0].billedInstallmentAmount}"
										property="billedInstallmentAmount" disabled="true" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.receivedInstallmentAmount" />
								</td>
								<td>
									<html:text styleClass="text"
										styleId="receivedInstallmentAmount" maxlength="18"
										disabled="true" property="receivedInstallmentAmount"
										tabindex="-1" style="text-align:right;"
										value="${cancellationData[0].receivedInstallmentAmount}" />
								</td>
								<td>
									<bean:message key="lbl.requestNumber" />
								</td>
								<td>
									<html:text styleClass="text" styleId="requestNumber"
										maxlength="10" property="requestNumber"
										value="${cancellationData[0].requestNumber}" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.reasonForClosure" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td>
									<html:text styleClass="text" styleId="reasonForClosure"
										maxlength="1000" property="reasonForClosure"
										value="${cancellationData[0].reasonForClosure}" />
								</td>

								<td nowrap="nowrap">
									<bean:message key="lbl.authorRemarks" />
								</td>
								<td nowrap="nowrap">
									<html:textarea styleClass="text" property="authorRemarks"
										disabled="true" value="${cancellationData[0].authorRemarks}" />
								</td>
							</tr>

							<tr>
								<td colspan="4">
									<button type="button" name="save" id="save"
										class="topformbutton2" accesskey="S" title="Alt+S"
										onclick="return updateCancellationDetails('P','');" ><bean:message key="button.save" /></button>
									<button type="button" name="saveForward" id="saveForward"
									 class="topformbutton2" accesskey="F"
										title="Alt+F"
										onclick="return updateCancellationDetails('F','<bean:message key="msg.confirmationForwardMsg" />');" ><bean:message key="button.forward" /></button>
									<button type="button"  name="view" id="view" accesskey="L"
										title="Alt+L" 
										class="topformbutton3" onclick="return viewLoanDetails();" ><bean:message key="button.viewloandetails" /></button>
									<button type="button"
										class="topformbutton3" accesskey="P" title="Alt+P"
										onclick="return viewPayableEarly('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.viewPayable" /></button>
									<button type="button"
										class="topformbutton3" accesskey="R" title="Alt+R"
										onclick="return viewReceivableEarly('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.viewRecievable" /></button>
									<button type="button" name="delete" id="delete" 
										class="topformbutton2" accesskey="D" title="Alt+D"
										onclick="return deleteCancellationDetails();"><bean:message key="button.delete" /></button>
								</td>
							</tr>
						</table>

					</fieldset>
				</html:form>
			</logic:present>

			<!-- ********************************************* For Cancellation Author **************************************************** -->

			<logic:present name="cancellationDataDisabled">
			<logic:notEmpty  name="cancellationDataDisabled">
				<html:form action="/cancellation" styleId="cancellationForm"
					method="post">

					<fieldset>
						<LEGEND>
							<bean:message key="lbl.cancellation" />
						</LEGEND>

						<table width="100%" border="0" cellspacing="1" cellpadding="1">
							<html:hidden property="closureType" styleId="closureType"
								value="X" />
							<html:hidden property="terminationId" styleId="terminationId"
								value="${cancellationDataDisabled[0].terminationId}" />
							<tr>

								<td>
									<bean:message key="lbl.loanAc" />
								</td>
								<td>
									<html:text styleClass="text" property="loanAc" styleId="loanAc"
										disabled="true" value="${cancellationDataDisabled[0].loanAc}" />
									<input type="hidden" name="contextPath"
										value="<%=request.getContextPath()%>" id="contextPath" />
									<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
										disabled="true"
										value="${cancellationDataDisabled[0].lbxLoanNoHID}" />
								</td>

								<td>
									<bean:message key="lbl.customerName" />
								</td>
								<td>
									<html:text styleClass="text" styleId="customerName"
										tabindex="-1" maxlength="50" disabled="true"
										property="customerName"
										value="${cancellationDataDisabled[0].customerName}" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.loanDate" />
								</td>
								<td>
									<html:text styleClass="text" styleId="loanDate" maxlength="10"
										disabled="true" property="loanDate" tabindex="-1"
										value="${cancellationDataDisabled[0].loanDate}" />
								</td>
								<td>
									<bean:message key="lbl.loanAmt" />
								</td>
								<td>
									<html:text styleClass="text" styleId="loanAmt" maxlength="18"
										disabled="true" property="loanAmt" tabindex="-1"
										value="${cancellationDataDisabled[0].loanAmt}"
										style="text-align:right;" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.product" />
								</td>
								<td>
									<html:text styleClass="text" styleId="product" disabled="true"
										maxlength="50" property="product" tabindex="-1"
										value="${cancellationDataDisabled[0].product}" />
								</td>
								<td>
									<bean:message key="lbl.scheme" />
								</td>
								<td>
									<html:text styleClass="text" styleId="scheme" disabled="true"
										maxlength="50" property="scheme" tabindex="-1"
										value="${cancellationDataDisabled[0].scheme}" />
								</td>
							</tr>
							<tr>
								<td>
									<bean:message key="lbl.originalTenure" />
								</td>
								<td>
									<html:text styleClass="text" styleId="originalTenure"
										tabindex="-1" disabled="true" maxlength="4"
										property="originalTenure" style="text-align:right;"
										value="${cancellationDataDisabled[0].originalTenure}" />
								</td>
								<td>
									<bean:message key="lbl.frequency" />
								</td>
								<td>
									<html:text styleClass="text" styleId="frequency" tabindex="-1"
										disabled="true" maxlength="1" property="frequency"
										value="${cancellationDataDisabled[0].frequency}" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.maturityDate" />
								</td>
								<td>
									<html:text styleClass="text" styleId="maturityDate"
										tabindex="-1" disabled="true" maxlength="10"
										property="maturityDate"
										value="${cancellationDataDisabled[0].maturityDate}" />
								</td>
								<td>
									<bean:message key="lbl.remainingTenure" />
								</td>
								<td>
									<html:text styleClass="text" styleId="remainingTenure"
										tabindex="-1" disabled="true" maxlength="4"
										property="remainingTenure" style="text-align:right;"
										value="${cancellationDataDisabled[1].remainingTenure}" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.cancellationRate" />
								</td>
								<td>
									<html:text styleClass="text" styleId="rate" maxlength="3"
										property="rate" tabindex="-1" disabled="true"
										value="${cancellationDataDisabled[0].rate}" 
										style="text-align:right;"/>
								</td>
								<td>
									<bean:message key="lbl.billedPrincipal" />
								</td>
								<td>
									<html:text styleClass="text" styleId="billedPrincipal"
										tabindex="-1" maxlength="18" property="billedPrincipal"
										disabled="true" style="text-align:right;"
										value="${cancellationDataDisabled[0].billedPrincipal}" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.disbursalStatus" />
								</td>
								<td>
									<html:text styleClass="text" styleId="disbursalStatus"
										tabindex="-1" maxlength="10" property="disbursalStatus"
										disabled="true"
										value="${cancellationDataDisabled[0].disbursalStatus}" />
								</td>
								<td>
									<bean:message key="lbl.disbursedAmount" />
								</td>
								<td>
									<html:text styleClass="text" styleId="disbursedAmount"
										tabindex="-1" maxlength="18" property="disbursedAmount"
										disabled="true" style="text-align:right;"
										value="${cancellationDataDisabled[0].disbursedAmount}" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.balancePrincipal" />
								</td>
								<td>
									<html:text styleClass="text" styleId="balancePrincipal"
										tabindex="-1" maxlength="18" property="balancePrincipal"
										disabled="true" style="text-align:right;"
										value="${cancellationDataDisabled[0].balancePrincipal}" />
								</td>
								<td>
									<bean:message key="lbl.overduePrincipal" />
								</td>
								<td>
									<html:text styleClass="text" styleId="overduePrincipal"
										tabindex="-1" maxlength="18" property="overduePrincipal"
										disabled="true" style="text-align:right;"
										value="${cancellationDataDisabled[0].overduePrincipal}" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.billedInstallments" />
								</td>
								<td>
									<html:text styleClass="text" styleId="billedInstallments"
										tabindex="-1" maxlength="3" property="billedInstallments"
										disabled="true" style="text-align:right;"
										value="${cancellationDataDisabled[0].billedInstallments}" />
								</td>
								<td>
									<bean:message key="lbl.billedInstallmentAmount" />
								</td>
								<td>
									<html:text styleClass="text" styleId="billedInstallmentAmount"
										tabindex="-1" maxlength="18" style="text-align:right;"
										value="${cancellationDataDisabled[0].billedInstallmentAmount}"
										property="billedInstallmentAmount" disabled="true" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.receivedInstallmentAmount" />
								</td>
								<td>
									<html:text styleClass="text"
										styleId="receivedInstallmentAmount" maxlength="18"
										disabled="true" property="receivedInstallmentAmount"
										tabindex="-1" style="text-align:right;"
										value="${cancellationDataDisabled[0].receivedInstallmentAmount}" />
								</td>
								<td>
									<bean:message key="lbl.requestNumber" />
								</td>
								<td>
									<html:text styleClass="text" styleId="requestNumber"
										disabled="true" maxlength="10" property="requestNumber"
										value="${cancellationDataDisabled[0].requestNumber}" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.reasonForClosure" />
								</td>
								<td>
									<html:text styleClass="text" styleId="reasonForClosure"
										disabled="true" maxlength="1000" property="reasonForClosure"
										value="${cancellationDataDisabled[0].reasonForClosure}" />
								</td>
								<td nowrap="nowrap">
									<bean:message key="lbl.authorRemarks" />
								</td>
								<td nowrap="nowrap">
									<html:textarea styleClass="text" property="authorRemarks"
										disabled="true" value="${cancellationDataDisabled[0].authorRemarks}" />
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
				</logic:notEmpty>
			</logic:present>
			<!-- ********************************************* Cancellation Author Ends **************************************************** -->
		</div>
	</div>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<script>
	setFramevalues("cancellationForm");
</script>
</body>
</html:html>

<logic:present name="sms">

	<script type="text/javascript">
	if("<%=request.getAttribute("sms")%>"=="S")
	{
		alert("<bean:message key="msg.DataSaved" />");
	}
	else if("<%=request.getAttribute("sms")%>"=="U")
	{
		alert("<bean:message key="msg.DataSaved" />");
		window.location = "<%=request.getContextPath() %>/closureInitiationBehind.do?method=cancellationClosure";
	}
	else if("<%=request.getAttribute("sms")%>"=="E")
	{
		alert("<bean:message key="msg.DataNotSaved" />");
		window.location = "<%=request.getContextPath() %>/closureInitiationBehind.do?method=cancellationClosure";
	}
	else if("<%=request.getAttribute("sms")%>"=="AP")
	{
		alert("<bean:message key="msg.ActivePayment" />");
		window.location = "<%=request.getContextPath() %>/closureInitiationBehind.do?method=cancellationClosure";
	}
	else if("<%=request.getAttribute("sms")%>"=="D")
	{
		alert("<bean:message key="msg.DataDelete" />");
		window.location = "<%=request.getContextPath() %>/closureInitiationBehind.do?method=cancellationClosure";
	}	
</script>
</logic:present>
