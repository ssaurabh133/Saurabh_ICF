<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@page import="com.login.roleManager.UserObject"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>
	<%
		String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":"
					+ request.getServerPort() + path + "/";

			UserObject userobj = (UserObject) session
					.getAttribute("userobject");
			String initiationDate = userobj.getBusinessdate();
	%>


	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />

	<script type="text/javascript" 
		src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cmScript/sdLiquidation.js"></script>
	<script type="text/javascript" 
		src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>

	
<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='I')	
     {
    	 document.getElementById('sdLiquidationMakerForm').loanLov.focus();
     }
     else if(document.getElementById('modifyRecord').value =='E')
     {
    	document.getElementById('sdLiquidationMakerForm').liquidationAmountPrincipal.focus();
     }
     return true;
}
</script>

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
<body oncontextmenu="return false" onclick="parent_disable()" onload="enableAnchor();fntab();init_fields();" onunload="closeAllLovCallUnloadBody();">
		
		<input type="hidden" id="businessDate1" value="${requestScope.businessDate1}" />
		<input type="hidden" id="makerDate" value="${requestScope.makerDate}" />
		<input type="hidden" id="checkFlag" value="${requestScope.checkFlag}" />
	
	<div id=centercolumn>
		<div id=revisedcontainer>
		
		<!-- **************** SD Liquidation New ************** -->
		
			<logic:present name="sdLiquidationNew">
			 <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
				<html:form action="/sdLiquidationMaker" method="post"
					styleId="sdLiquidationMakerForm">
					<html:javascript formName="SDLiquidationMakerDynaValidatorForm"/>
					<html:errors/>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="hidden" name="businessDate" id="businessDate"
										value="<%=initiationDate%>" />
									<fieldset>
									<legend>
										<bean:message key="lbl.loanDetails" />
									</legend>
										<table width="100%" border="0" cellspacing="1" cellpadding="1">

											<tr>
												<td width="25%">
													<bean:message key="lbl.loanNo" />
													<font color="red">*</font>
												</td>
												<td width="25%">
													<html:text styleClass="text" property="loanNo" tabindex="-1"
														styleId="loanNo" value="" readonly="true" maxlength="20" />
													<html:button property="loanLov" styleId="loanLov" styleClass="lovbutton" value=" "
														onclick="openLOVCommon(129,'sdLiquidationMakerForm','loanNo','','', '','','generateValuesLoanForLiquidation','customerName');closeLovCallonLov1();"/>
													<input type="hidden" name="contextPath"
														value="<%=request.getContextPath()%>" id="contextPath" />
													<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
														value="" />
												</td>
												<td width="25%">
													<bean:message key="lbl.customerName" />
												</td>
												<td width="25%">
													<html:text styleClass="text" property="customerName"
														styleId="customerName" maxlength="50" disabled="true"
														value="" />
												</td>
											</tr>
											<tr>
												<td>
													<bean:message key="lbl.product" />
												</td>
												<td>
													<html:text styleClass="text" property="product"
														styleId="product" value="" disabled="true" maxlength="20" />
												</td>
												<td>
													<bean:message key="lbl.scheme" />
												</td>
												<td>
													<html:text styleClass="text" property="scheme"
														styleId="scheme" maxlength="50" value="" disabled="true" />
												</td>
											</tr>
											<tr>
												<td>
													<bean:message key="lbl.loanAmt" />
												</td>
												<td>
													<html:text styleClass="text" property="loanAmt" style="text-align:right;"
														styleId="loanAmt" value="" disabled="true" maxlength="18" />
												</td>
												<td>
													<bean:message key="lbl.loanApprovalDate" />
												</td>
												<td>
													<html:text styleClass="text" property="loanApprovalDate"
														styleId="loanApprovalDate" value="" disabled="true" maxlength="10" />
												</td>
											</tr>
											<tr>
												<td>
													<bean:message key="lbl.loanStatus" />
												</td>
												<td>
													<html:text property="loanStatus"
														styleId="loanStatus" disabled="true" value="" styleClass="text" />
												</td>
												<td>
													<bean:message key="lbl.disbursalStatus" />
												</td>
												<td>
													<html:text property="disbursalStatus"
														styleId="disbursalStatus" disabled="true" value="" styleClass="text" />
												</td>
											</tr>
										</table>
									</fieldset>
									</td>
									</tr>
									<tr>
									<td>
									<fieldset>
										<legend>
											<bean:message key="lbl.sdLiquidationDetails" />
										</legend>
										<table width="100%" border="0" cellspacing="1" cellpadding="1">

											<tr>
												<td width="25%">
													<bean:message key="lbl.sdNo" />
													<font color="red">*</font>
												</td>
												<td width="25%">
													<html:text styleClass="text" property="sdNo" tabindex="-1"
														styleId="sdNo" value="" readonly="true" maxlength="20" />
													<html:button property="sdLov" styleId="sdLov" styleClass="lovbutton" value=" "
														onclick="openLOVCommon(130,'sdLiquidationMakerForm','sdNo','loanNo','lbxSdNoHid', 'lbxLoanNoHID','Please Select Loan LOV First','generateValuesLiquidation','sdAmount');closeLovCallonLov('loanNo');"/>
													<input type="hidden" name="contextPath"
														value="<%=request.getContextPath()%>" id="contextPath" />
													<html:hidden property="lbxSdNoHid" styleId="lbxSdNoHid"
														value="" />
												</td>
												<td width="25%">
													<bean:message key="lbl.sdAmount" />
												</td>
												<td width="25%">
													<html:text styleClass="text" property="sdAmount"
														styleId="sdAmount" maxlength="18" disabled="true"
														value="" style="text-align:right;"/>
												</td>
											</tr>
											<tr>
												<td>
													<bean:message key="lbl.sdStartDate" />
												</td>
												<td>
													<html:text property="sdStartDate" maxlength="10" disabled="true"
														styleId="sdStartDate" value="" styleClass="text" />
												</td>
												<td>
													<bean:message key="lbl.sdMaturityDate" />
												</td>
												<td>
													<html:text styleClass="text" property="sdMaturityDate"
														styleId="sdMaturityDate" value="" disabled="true" maxlength="10" />
												</td>
											</tr>
											<tr>
												<td>
													<bean:message key="lbl.sdInterestType" />
												</td>
												<td>
													<html:text styleClass="text" property="sdInterestType" 
														styleId="sdInterestType" value="" disabled="true" maxlength="20" />
												</td>
												<td>
													<bean:message key="lbl.sdCompoundingFreq" />
												</td>
												<td>
													<html:text styleClass="text" property="sdCompoundingFreq"
														styleId="sdCompoundingFreq" value="" disabled="true" maxlength="20" />
												</td>
												
											</tr>
											<tr>
												<td>
													<bean:message key="lbl.sdInterestRate" />
												</td>
												<td>
													<html:text styleClass="text" property="sdInterestRate" style="text-align:right;"
														styleId="sdInterestRate" maxlength="7" value="" disabled="true" />
												</td>
												<td>
													<bean:message key="lbl.sdTDSRate" />
												</td>
												<td>
													<html:text property="sdTDSRate" maxlength="7" disabled="true"
														styleId="sdTDSRate" value="" styleClass="text" style="text-align:right;"/>
												</td>
											</tr>
											<tr>
												<td>
													<bean:message key="lbl.sdInterestAccrued" />
												</td>
												<td>
													<html:hidden property="sdInterestAccrued" styleId="sdInterestAccrued" value="" styleClass="text" />
													<html:text property="sdTotalInterest"  styleId="sdTotalInterest" value="" styleClass="text" style="text-align:right;" readonly="true"/>
													<html:text property="sdFinalInterest" styleId="sdFinalInterest" value="" styleClass="text" style="text-align:right;display: none;" readonly="true"/>
													
												</td>
												<td>
													<bean:message key="lbl.sdInterestAccruedDate" />
												</td>
												<td>
													<html:text styleClass="text" property="sdInterestAccruedDate"
														styleId="sdInterestAccruedDate" value="" disabled="true" maxlength="10" />
												</td>	
											</tr>
											<tr>
												<td>
													<bean:message key="lbl.sdTDSDeducted" />
												</td>
												<td>
													<html:text property="sdTDSDeducted" maxlength="18" disabled="true"
														styleId="sdTDSDeducted" value="" styleClass="text" 
														style="text-align:right;"/>
												</td>

												<td>
													<bean:message key="lbl.liquidationFlag"/>
												</td>
												<td>
													<html:select property="liquidationFlag" styleId="liquidationFlag"
														styleClass="text" value="" onchange="generateSDAccrual();getSDLiquidAmountPI();">
														<option value="P">Partial</option>
														<option value="F">Final</option>
													</html:select>
												</td>
											</tr>
											<tr>
												<td>
													<bean:message key="lbl.gapInterest"/>
												</td>
												<td>
													<div id="partialI">
														<html:text property="gapInterest" styleId="gapInterest" 
															styleClass="text" value="0.00" readonly="true" maxlength="18"
															tabindex="-1" style="text-align:right;"/>
													</div>
													<div id="finalI" style="display:none;">
														
													</div>
												</td>
												<td>
													<bean:message key="lbl.gapTDS"/>
												</td>
												<td>
													<div id="partialT">
														<html:text property="gapTDS" styleId="gapTDS" value="0.00"
															styleClass="text" readonly="true" tabindex="-1"
															maxlength="18" style="text-align:right;"/>
													</div>
													<div id="finalT" style="display:none;">
														
												</div>
												</td>
											</tr>
											<tr>
												<td>
													<bean:message key="lbl.liquidatedAmountPrincipal" />
												</td>
												<td>
													<html:text property="liquidatedAmountPrincipal" maxlength="18" disabled="true"
														styleId="liquidatedAmountPrincipal" value="" styleClass="text" 
														style="text-align:right;"/>
												</td>
												<td>
													<bean:message key="lbl.liquidatedAmountInterest" />
												</td>
												<td>
													<html:text property="liquidatedAmountInterest" maxlength="18" disabled="true"
														styleId="liquidatedAmountInterest" value="" styleClass="text" 
														style="text-align:right;"/>
												</td>
											</tr>
											<tr>
												<td>
													<bean:message key="lbl.liquidationAmountPrincipal" />
													<font color="red">*</font>
												</td>
												<td>
													<html:text property="liquidationAmountPrincipal" onkeypress="return numbersonly(event,id,18)" 
														onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
														onfocus="keyUpNumber(this.value, event, 18,id);"
														styleId="liquidationAmountPrincipal" value="" styleClass="text" 
														style="text-align:right;"/>
												</td>
												<td>
													<bean:message key="lbl.liquidationAmountInterest" />
													<font color="red">*</font>
												</td>
												<td>
													<html:text property="liquidationAmountInterest" onkeypress="return numbersonly(event,id,18)" 
														onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
														onfocus="keyUpNumber(this.value, event, 18,id);"
														styleId="liquidationAmountInterest" value="" styleClass="text" 
														style="text-align:right;"/>
												</td>
											</tr>
											<tr>
												
												<td>
													<bean:message key="lbl.makerRemark" />
												</td>
												<td>
													<textarea name="remarks" id="remarks" maxlength="500"></textarea>
												</td>
											
											
			<td ><bean:message key="lbl.authorRemarks" /></td>
			<td><html:textarea styleClass="text" property="authorRemarks" disabled="true"
			value="${liquidationData[0].authorRemarks}" /></td>
			</tr>
											
										</table>
										<button type="button" name="save" id="save"
											class="topformbutton2" accesskey="V" title="Alt+V"
											onclick="return saveLiquidationData();"><bean:message key="button.save" /></button>
										<button type="button" name="saveFwd" id="saveFwd"
											 class="topformbutton2" accesskey="F" title="Alt+F"
											onclick="return updateDisbursalBeforeSave();"><bean:message key="button.forward" /></button>
									</fieldset>								
								</td>
							</tr>
						</table>
				</html:form>
			</logic:present>
			
			
			<!-- **************** SD Liquidation Maker ************** -->
			
				<logic:present name="liquidationData">
				<logic:iterate name="liquidationData" id="liquidationDataList">
				 <input type="hidden" id="modifyRecord" name="modifyRecord" value="E"/>
				<html:form action="/sdLiquidationMaker" method="post"
					styleId="sdLiquidationMakerForm">
					<html:javascript formName="SDLiquidationMakerDynaValidatorForm"/>
					<html:errors/>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="hidden" name="businessDate" id="businessDate"
										value="<%=initiationDate%>" />
									<html:hidden property="sdLiquidationId" styleId="sdLiquidationId"
										value="${requestScope.sdLiquidationId}"/>
									<fieldset>
									<legend>
										<bean:message key="lbl.loanDetails" />
									</legend>
										<table width="100%" border="0" cellspacing="1" cellpadding="1">

											<tr>
												<td width="25%">
													<bean:message key="lbl.loanNo" />
													<font color="red">*</font>
												</td>
												<td width="25%">
													<html:text styleClass="text" property="loanNo" tabindex="-1"
														styleId="loanNo" value="${liquidationData[0].loanNo}" readonly="true" maxlength="20" />
													<input type="hidden" name="contextPath"
														value="<%=request.getContextPath()%>" id="contextPath" />
													<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
														value="${liquidationData[0].lbxLoanNoHID}" />
												</td>
												<td width="25%">
													<bean:message key="lbl.customerName" />
												</td>
												<td width="25%">
													<html:text styleClass="text" property="customerName"
														styleId="customerName" maxlength="50" disabled="true"
														value="${liquidationData[0].customerName}" />
												</td>
											</tr>
											<tr>
												<td >
													<bean:message key="lbl.product" />
												</td>
												<td >
													<html:text styleClass="text" property="product"
														styleId="product" value="${liquidationData[0].product}" disabled="true" maxlength="20" />
												</td>
												<td >
													<bean:message key="lbl.scheme" />
												</td>
												<td >
													<html:text styleClass="text" property="scheme"
														styleId="scheme" maxlength="50" value="${liquidationData[0].scheme}" disabled="true" />
												</td>
											</tr>
											<tr>
												<td >
													<bean:message key="lbl.loanAmt" />
												</td>
												<td >
													<html:text styleClass="text" property="loanAmt"
														styleId="loanAmt" value="${liquidationData[0].loanAmt}" disabled="true" 
														maxlength="18" style="text-align:right;"/>
												</td>
												<td >
													<bean:message key="lbl.loanApprovalDate" />
												</td>
												<td >
													<html:text styleClass="text" property="loanApprovalDate"
														styleId="loanApprovalDate" value="${liquidationData[0].loanApprovalDate}" disabled="true" maxlength="10" />
												</td>
											</tr>
											<tr>
												<td >
													<bean:message key="lbl.loanStatus" />
												</td>
												<td >
													<html:text property="loanStatus"
														styleId="loanStatus" disabled="true" value="${liquidationData[0].loanStatus}" styleClass="text" />
												</td>
												<td >
													<bean:message key="lbl.disbursalStatus" />
												</td>
												<td >
													<html:text property="disbursalStatus"
														styleId="disbursalStatus" disabled="true" value="${liquidationData[0].disbursalStatus}" styleClass="text" />
												</td>
											</tr>
										</table>
									</fieldset>
									</td>
									</tr>
									<tr>
									<td>
									<fieldset>
										<legend>
											<bean:message key="lbl.sdLiquidationDetails" />
										</legend>
										<table width="100%" border="0" cellspacing="1" cellpadding="1">

											<tr>
												<td width="25%">
													<bean:message key="lbl.sdNo" />
													<font color="red">*</font>
												</td>
												<td width="25%">
													<html:text styleClass="text" property="sdNo"
														styleId="sdNo" value="${liquidationData[0].sdNo}" readonly="true" maxlength="20" tabindex="-1"/>
												</td>
												<td width="25%">
													<bean:message key="lbl.sdAmount" />
												</td>
												<td width="25%">
													<html:text styleClass="text" property="sdAmount"
														styleId="sdAmount" maxlength="18" disabled="true"
														value="${liquidationData[0].sdAmount}" style="text-align:right;"/>
												</td>
											</tr>
											<tr>
												<td >
													<bean:message key="lbl.sdStartDate" />
												</td>
												<td >
													<html:text property="sdStartDate" maxlength="10" disabled="true"
														styleId="sdStartDate" value="${liquidationData[0].sdStartDate}" styleClass="text" />
												</td>
												<td >
													<bean:message key="lbl.sdMaturityDate" />
												</td>
												<td >
													<html:text styleClass="text" property="sdMaturityDate"
														styleId="sdMaturityDate" value="${liquidationData[0].sdMaturityDate}" disabled="true" maxlength="10" />
												</td>
											</tr>
											<tr>
												<td >
													<bean:message key="lbl.sdInterestType" />
												</td>
												<td >
													<html:text styleClass="text" property="sdInterestType"
														styleId="sdInterestType" value="${liquidationData[0].sdInterestType}" disabled="true" maxlength="20" />
												</td>
												<td >
													<bean:message key="lbl.sdCompoundingFreq" />
												</td>
												<td >
													<html:text styleClass="text" property="sdCompoundingFreq"
														styleId="sdCompoundingFreq" value="${liquidationData[0].sdCompoundingFreq}" disabled="true" maxlength="20" />
												</td>
												
											</tr>
											<tr>
												<td >
													<bean:message key="lbl.sdInterestRate" />
												</td>
												<td >
													<html:text styleClass="text" property="sdInterestRate" style="text-align:right;"
														styleId="sdInterestRate" maxlength="7" value="${liquidationData[0].sdInterestRate}" disabled="true" />
												</td>
												<td >
													<bean:message key="lbl.sdTDSRate" />
												</td>
												<td >
													<html:text property="sdTDSRate" maxlength="7" disabled="true" 
														styleId="sdTDSRate" value="${liquidationData[0].sdTDSRate}" 
														styleClass="text" style="text-align:right;"/>
												</td>
											</tr>
											<tr>
												<td >
													<bean:message key="lbl.sdInterestAccrued" />
												</td>
												<td>
														<html:text property="sdInterestAccrued" maxlength="7" styleId="sdInterestAccrued" value="${requestScope.default}" styleClass="text" style="text-align:right;" readonly="true"/>
														<html:hidden property="sdTotalInterest" styleId="sdTotalInterest" value="${liquidationData[0].sdInterestAccrued}" styleClass="text" />
														<html:hidden property="sdFinalInterest" styleId="sdFinalInterest" value="${liquidationData[0].sdFinalInterest}" styleClass="text" />														
												</td>
												<td >
													<bean:message key="lbl.sdInterestAccruedDate" />
												</td>
												<td >
													<html:text styleClass="text" property="sdInterestAccruedDate"
														styleId="sdInterestAccruedDate" value="${liquidationData[0].sdInterestAccruedDate}" 
														disabled="true" maxlength="10" />
												</td>	
											</tr>
											<tr>
												<td>
													<bean:message key="lbl.sdTDSDeducted" />
												</td>
												<td>
													<html:text property="sdTDSDeducted" maxlength="18" disabled="true"
														styleId="sdTDSDeducted" value="${liquidationData[0].sdTDSDeducted}"
														styleClass="text" style="text-align:right;"/>
												</td>

												<td>
													<bean:message key="lbl.liquidationFlag"/>
												</td>
												<td>
													<html:select property="liquidationFlag" styleId="liquidationFlag"
														styleClass="text" onchange="generateSDAccrualEditable();getSDLiquidAmountPI();"
														value="${liquidationData[0].liquidationFlag}">
														<html:option value="P">Partial</html:option>
														<html:option value="F">Final</html:option>
													</html:select>
												</td>
											</tr>
											<tr>
												<td>
													<bean:message key="lbl.gapInterest"/>
												</td>
												<td>
												<logic:equal name="liquidationDataList" property="liquidationFlag" value="P">
													<div id="partialI">
													<html:text property="gapInterest" styleId="gapInterest" 
														styleClass="text" value="${liquidationData[0].gapInterest}" 
														readonly="true" maxlength="18"
														tabindex="-1" style="text-align:right;"/>
													</div>
													<div id="finalI" style="display:none;"></div>
												</logic:equal>
												<logic:equal name="liquidationDataList" property="liquidationFlag" value="F">
													<div id="finalI">
													<html:text property="gapInterestF" styleId="gapInterestF" 
														styleClass="text" value="${liquidationData[0].gapInterest}" 
														readonly="true" maxlength="18"
														tabindex="-1" style="text-align:right;"/>
													</div>
													<div id="partialI" style="display:none;">
														<html:text property="gapInterest" styleId="gapInterest" 
														styleClass="text" value="0.00" 
														readonly="true" maxlength="18"
														tabindex="-1" style="text-align:right;"/>
													</div>
												</logic:equal>
												</td>
												<td>
													<bean:message key="lbl.gapTDS"/>
												</td>
												<td>
												<logic:equal name="liquidationDataList" property="liquidationFlag" value="P">
													<div id="partialT">
													<html:text property="gapTDS" styleId="gapTDS" 
														value="${liquidationData[0].gapTDS}"
														styleClass="text" readonly="true" tabindex="-1"
														maxlength="18" style="text-align:right;"/>
													</div>
													<div id="finalT" style="display:none;"></div>	
												</logic:equal>
												<logic:equal name="liquidationDataList" property="liquidationFlag" value="F">
													<div id="finalT">
													<html:text property="gapTDSF" styleId="gapTDSF" 
														value="${liquidationData[0].gapTDS}"
														styleClass="text" readonly="true" tabindex="-1"
														maxlength="18" style="text-align:right;"/>
													</div>
													<div id="partialT" style="display:none;">
														<html:text property="gapTDS" styleId="gapTDS" 
														value="0.00"
														styleClass="text" readonly="true" tabindex="-1"
														maxlength="18" style="text-align:right;"/>
													</div>	
												</logic:equal>			
												</td>
											</tr>
											<tr>
												<td>
													<bean:message key="lbl.liquidatedAmountPrincipal" />
												</td>
												<td>
													<html:text property="liquidatedAmountPrincipal" maxlength="18" disabled="true"
														styleId="liquidatedAmountPrincipal" value="${liquidationData[0].liquidatedAmountPrincipal}"
														styleClass="text" style="text-align:right;"/>
												</td>
												<td>
													<bean:message key="lbl.liquidatedAmountInterest" />
												</td>
												<td>
													<html:text property="liquidatedAmountInterest" maxlength="18" disabled="true"
														styleId="liquidatedAmountInterest" value="${liquidationData[0].liquidatedAmountInterest}" 
														styleClass="text" style="text-align:right;"/>
												</td>
											</tr>
											<tr>
												<td>
													<bean:message key="lbl.liquidationAmountPrincipal" />
													<font color="red">*</font>
												</td>
												<td>
													<html:text property="liquidationAmountPrincipal" onkeypress="return numbersonly(event,id,18)" 
														onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
														onfocus="keyUpNumber(this.value, event, 18,id);"
														styleId="liquidationAmountPrincipal" value="${liquidationData[0].liquidationAmountPrincipal}" 
														styleClass="text" style="text-align:right;"/>
												</td>
												<td>
													<bean:message key="lbl.liquidationAmountInterest" />
													<font color="red">*</font>
												</td>
												<td>
													<html:text property="liquidationAmountInterest" onkeypress="return numbersonly(event,id,18)" 
														onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
														onfocus="keyUpNumber(this.value, event, 18,id);"
														styleId="liquidationAmountInterest" value="${liquidationData[0].liquidationAmountInterest}" 
														styleClass="text" style="text-align:right;"/>
												</td>
											</tr>
											<tr>
												
												<td>
													<bean:message key="lbl.makerRemark" />
												</td>
												<td>
													<textarea name="remarks" id="remarks" maxlength="500">${liquidationData[0].remarks}</textarea>
												</td>
											
			
			<td nowrap="nowrap"><bean:message key="lbl.authorRemarks" /></td>
			<td nowrap="nowrap"><html:textarea styleClass="text" property="authorRemarks" disabled="true"
			value="${liquidationData[0].authorRemarks}" /></td>
			</tr>
											
										</table>
										<button type="button" name="save" id="save" 
											class="topformbutton2" accesskey="V" title="Alt+V"
											onclick="return updateLiquidationData('P');"><bean:message key="button.save" /></button>
										<button type="button" name="saveFwd" id="saveFwd"
											class="topformbutton2" accesskey="F" title="Alt+F"
											onclick="return updateLiquidationData('F');"><bean:message key="button.forward" /></button>
										<button type="button" name="delete" id="delete" 
											class="topformbutton2" accesskey="D" title="Alt+D"
											onclick="return deleteLiquidationData();"><bean:message key="button.delete" /></button>
									</fieldset>										
								</td>
							</tr>
						</table>
				</html:form>
				</logic:iterate>
			</logic:present>
			
			
			<!-- **************** SD Liquidation Author ************** -->
			
			<logic:present name="liquidationDataDisabled">
				<html:form action="/sdLiquidationMaker" method="post"
					styleId="sdLiquidationMakerForm">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="hidden" name="businessDate" id="businessDate"
										value="<%=initiationDate%>" />
									<fieldset>
									<legend>
										<bean:message key="lbl.loanDetails" />
									</legend>
										<table width="100%" border="0" cellspacing="1" cellpadding="1">

											<tr>
												<td width="25%">
													<bean:message key="lbl.loanNo" />
													<font color="red">*</font>
												</td>
												<td width="25%">
													<html:text styleClass="text" property="loanNo"
														styleId="loanNo" value="${liquidationDataDisabled[0].loanNo}" disabled="true" maxlength="20" />
													<input type="hidden" name="contextPath"
														value="<%=request.getContextPath()%>" id="contextPath" />
													<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
														value="${liquidationDataDisabled[0].lbxLoanNoHID}" />
												</td>
												<td width="25%">
													<bean:message key="lbl.customerName" />
												</td>
												<td width="25%">
													<html:text styleClass="text" property="customerName"
														styleId="customerName" maxlength="50" disabled="true"
														value="${liquidationDataDisabled[0].customerName}" />
												</td>
											</tr>
											<tr>
												<td >
													<bean:message key="lbl.product" />
												</td>
												<td >
													<html:text styleClass="text" property="product"
														styleId="product" value="${liquidationDataDisabled[0].product}" disabled="true" maxlength="20" />
												</td>
												<td >
													<bean:message key="lbl.scheme" />
												</td>
												<td >
													<html:text styleClass="text" property="scheme"
														styleId="scheme" maxlength="50" value="${liquidationDataDisabled[0].scheme}" disabled="true" />
												</td>
											</tr>
											<tr>
												<td >
													<bean:message key="lbl.loanAmt" />
												</td>
												<td >
													<html:text styleClass="text" property="loanAmt" style="text-align:right;"
														styleId="loanAmt" value="${liquidationDataDisabled[0].loanAmt}" 
														disabled="true" maxlength="18" />
												</td>
												<td >
													<bean:message key="lbl.loanApprovalDate" />
												</td>
												<td >
													<html:text styleClass="text" property="loanApprovalDate"
														styleId="loanApprovalDate" value="${liquidationDataDisabled[0].loanApprovalDate}" disabled="true" maxlength="10" />
												</td>
											</tr>
											<tr>
												<td >
													<bean:message key="lbl.loanStatus" />
												</td>
												<td >
													<html:text property="loanStatus"
														styleId="loanStatus" disabled="true" value="${liquidationDataDisabled[0].loanStatus}" styleClass="text" />
												</td>
												<td >
													<bean:message key="lbl.disbursalStatus" />
												</td>
												<td >
													<html:text property="disbursalStatus"
														styleId="disbursalStatus" disabled="true" value="${liquidationDataDisabled[0].disbursalStatus}" styleClass="text" />
												</td>
											</tr>
										</table>
									</fieldset>
									</td>
									</tr>
									<tr>
									<td>
									<fieldset>
										<legend>
											<bean:message key="lbl.sdLiquidationDetails" />
										</legend>
										<table width="100%" border="0" cellspacing="1" cellpadding="1">

											<tr>
												<td width="25%">
													<bean:message key="lbl.sdNo" />
													<font color="red">*</font>
												</td>
												<td width="25%">
													<html:text styleClass="text" property="sdNo"
														styleId="sdNo" value="${liquidationDataDisabled[0].sdNo}" disabled="true" maxlength="20" />
												</td>
												<td width="25%">
													<bean:message key="lbl.sdAmount" />
												</td>
												<td width="25%">
													<html:text styleClass="text" property="sdAmount"
														styleId="sdAmount" maxlength="18" disabled="true"
														value="${liquidationDataDisabled[0].sdAmount}" 
														style="text-align:right;"/>
												</td>
											</tr>
											<tr>
												<td >
													<bean:message key="lbl.sdStartDate" />
												</td>
												<td >
													<html:text property="sdStartDate" maxlength="10" disabled="true"
														styleId="sdStartDate" value="${liquidationDataDisabled[0].sdStartDate}" styleClass="text" />
												</td>
												<td >
													<bean:message key="lbl.sdMaturityDate" />
												</td>
												<td >
													<html:text styleClass="text" property="sdMaturityDate"
														styleId="sdMaturityDate" value="${liquidationDataDisabled[0].sdMaturityDate}" disabled="true" maxlength="10" />
												</td>
											</tr>
											<tr>
												<td >
													<bean:message key="lbl.sdInterestType" />
												</td>
												<td >
													<html:text styleClass="text" property="sdInterestType"
														styleId="sdInterestType" value="${liquidationDataDisabled[0].sdInterestType}" disabled="true" maxlength="20" />
												</td>
												<td >
													<bean:message key="lbl.sdCompoundingFreq" />
												</td>
												<td >
													<html:text styleClass="text" property="sdCompoundingFreq"
														styleId="sdCompoundingFreq" value="${liquidationDataDisabled[0].sdCompoundingFreq}" disabled="true" maxlength="20" />
												</td>
												
											</tr>
											<tr>
												<td >
													<bean:message key="lbl.sdInterestRate" />
												</td>
												<td >
													<html:text styleClass="text" property="sdInterestRate" style="text-align:right;"
														styleId="sdInterestRate" maxlength="7" 
														value="${liquidationDataDisabled[0].sdInterestRate}" disabled="true" />
												</td>
												<td >
													<bean:message key="lbl.sdTDSRate" />
												</td>
												<td >
													<html:text property="sdTDSRate" maxlength="7" disabled="true"
														styleId="sdTDSRate" value="${liquidationDataDisabled[0].sdTDSRate}" 
														styleClass="text" style="text-align:right;"/>
												</td>
											</tr>
											<tr>
												<td >
													<bean:message key="lbl.sdInterestAccrued" />
												</td>
												<td >
													<html:text property="sdInterestAccrued" maxlength="7" styleId="sdInterestAccrued" value="${sessionScope.amount}" styleClass="text" style="text-align:right;" readonly="true"/>
												</td>
												<td >
													<bean:message key="lbl.sdInterestAccruedDate" />
												</td>
												<td >
													<html:text styleClass="text" property="sdInterestAccruedDate"
														styleId="sdInterestAccruedDate" value="${liquidationDataDisabled[0].sdInterestAccruedDate}" disabled="true" maxlength="10" />
												</td>	
											</tr>
											<tr>
												<td>
													<bean:message key="lbl.sdTDSDeducted" />
												</td>
												<td>
													<html:text property="sdTDSDeducted" maxlength="18" disabled="true"
														styleId="sdTDSDeducted" value="${liquidationDataDisabled[0].sdTDSDeducted}"
														styleClass="text" style="text-align:right;"/>
												</td>

												<td>
													<bean:message key="lbl.liquidationFlag"/>
												</td>
												<td>
													<html:select property="liquidationFlag" styleId="liquidationFlag" disabled="true"
														styleClass="text" value="${liquidationDataDisabled[0].liquidationFlag}">
														<html:option value="P">Partial</html:option>
														<html:option value="F">Final</html:option>
													</html:select>
												</td>
											</tr>
											<tr>
												<td>
													<bean:message key="lbl.gapInterest"/>
												</td>
												<td>
													<html:text property="gapInterest" styleId="gapInterest" 
														styleClass="text" value="${liquidationDataDisabled[0].gapInterest}" 
														disabled="true" tabindex="-1" style="text-align:right;"/>
												</td>
												<td>
													<bean:message key="lbl.gapTDS"/>
												</td>
												<td>
													<html:text property="gapTDS" styleId="gapTDS" 
														value="${liquidationDataDisabled[0].gapTDS}" tabindex="-1"
														styleClass="text" disabled="true" style="text-align:right;"/>
												</td>
											</tr>
											<tr>
												<td>
													<bean:message key="lbl.liquidatedAmountPrincipal" />
												</td>
												<td>
													<html:text property="liquidatedAmountPrincipal" maxlength="18" disabled="true"
														styleId="liquidatedAmountPrincipal" value="${liquidationDataDisabled[0].liquidatedAmountPrincipal}"
														styleClass="text" style="text-align:right;"/>
												</td>
												<td>
													<bean:message key="lbl.liquidatedAmountInterest" />
												</td>
												<td>
													<html:text property="liquidatedAmountInterest" maxlength="18" disabled="true"
														styleId="liquidatedAmountInterest" value="${liquidationDataDisabled[0].liquidatedAmountInterest}" 
														styleClass="text" style="text-align:right;"/>
												</td>
											</tr>
											<tr>
												<td>
													<bean:message key="lbl.liquidationAmountPrincipal" />
													<font color="red">*</font>
												</td>
												<td>
													<html:text property="liquidationAmountPrincipal" onkeypress="return numbersonly(event,id,18)" 
														onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
														onfocus="keyUpNumber(this.value, event, 18,id);" disabled="true"
														styleId="liquidationAmountPrincipal" value="${liquidationDataDisabled[0].liquidationAmountPrincipal}" 
														styleClass="text" style="text-align:right;"/>
												</td>
												<td>
													<bean:message key="lbl.liquidationAmountInterest" />
													<font color="red">*</font>
												</td>
												<td>
													<html:text property="liquidationAmountInterest" onkeypress="return numbersonly(event,id,18)" 
														onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
														onfocus="keyUpNumber(this.value, event, 18,id);" disabled="true"
														styleId="liquidationAmountInterest" value="${liquidationDataDisabled[0].liquidationAmountInterest}" 
														styleClass="text" style="text-align:right;"/>
												</td>
											</tr>
											<tr>
												
												<td>
													<bean:message key="lbl.makerRemark" />
													<font color="red">*</font>
												</td>
												<td>
													<textarea name="remarks" id="remarks" disabled="disabled" maxlength="500">${liquidationDataDisabled[0].remarks}</textarea>
												</td>
											
			<td nowrap="nowrap"><bean:message key="lbl.authorRemarks" /></td>
			<td nowrap="nowrap"><html:textarea styleClass="text" property="authorRemarks" disabled="true"
			value="${liquidationDataDisabled[0].authorRemarks}" /></td>
			</tr>
											
										</table>
									</fieldset>								
								</td>
							</tr>
						</table>
				</html:form>
			</logic:present>
		</div>
	</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>	
</body>
</html:html>

<logic:present name="message">
<script type="text/javascript">
	if("<%=request.getAttribute("message")%>"=="S")
	{
		alert("<bean:message key="msg.DataSaved" />");
	}
	else if("<%=request.getAttribute("message")%>"=="U")
	{
		alert("<bean:message key="msg.DataSaved" />");
		window.location = "<%=request.getContextPath() %>/sdLiquidationSearchBehind.do?method=sdLiquidationMakerSearch";
	}
	else if("<%=request.getAttribute("message")%>"=="E")
	{
		alert("<bean:message key="msg.DataNotSaved" />");
		window.location = "<%=request.getContextPath() %>/sdLiquidationSearchBehind.do?method=sdLiquidationMakerSearch";
	}
	else if("<%=request.getAttribute("message")%>"=="D")
	{
		alert("<bean:message key="msg.DataDelete" />");
		window.location = "<%=request.getContextPath() %>/sdLiquidationSearchBehind.do?method=sdLiquidationMakerSearch";
	}
</script>	
</logic:present>