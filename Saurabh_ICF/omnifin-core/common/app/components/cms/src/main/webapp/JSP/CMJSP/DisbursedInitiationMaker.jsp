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
	
 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>

	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	
		
		<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cmScript/disbursalJS.js"></script>
		
	
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
	<script type="text/javascript"
		src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
    	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
<script type="text/javascript">
	function fntab()
{
     if(document.getElementById('modifyRecord').value =='CM')	
     {
    	 document.getElementById('disbursalMakerForm').shortPayAmount.focus();
     }
     else if(document.getElementById('modifyRecord').value =='N')
     {
    	document.getElementById('disbursalMakerForm').loanButton.focus();
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
<body  onclick="parent_disable();" onunload="closeAllWindowCallUnloadBody();" onload="enableAnchor();fntab();hideRepayDate();">
<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
	<input type="hidden" name="forwardedLoanId" id="forwardedLoanId" value="${forwardedLoanId}"/>
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" name="totalReceivableCurrShortPay" id="totalReceivableCurrShortPay" value="${sessionScope.totalReceivableCurrShortPay }" />
	
	
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
    		
	<div id=centercolumn>
		<div id=revisedcontainer>

			<!-- --------------------------------------- Disbursal New --------------------------------- -->

			<logic:present name="disbursalNew">

			 
				<html:form action="/disbursalMaker" method="post"
					styleId="disbursalMakerForm">
					<input type="hidden" name="maxDisbursalDate" id="maxDisbursalDate" value="${maxDisbursalDate}" />
					<html:javascript
						formName="DisbursedInitiationMakerDynaValidatorForm" />
					<html:errors />
					<fieldset>
						<legend>
							<bean:message key="lbl.disbursalDetails" />
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="hidden" name="businessDate" id="businessDate"
										value="<%=initiationDate%>" />
									<html:hidden property="disbursalNo" styleId="disbursalNo"
										value="" />
									<input type="hidden" id="modifyRecord" name="modifyRecord" value="N"/>
									<input type="hidden" id="pdcDepositCount" name="pdcDepositCount" value="" />
																
									<table width="100%" border="0" cellspacing="1" cellpadding="1">

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.loanNo" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="loanNo"
													styleId="loanNo" value="" readonly="true" maxlength="20" />
												<html:button property="loanButton" styleId="loanButton" value=" "
													onclick="openLOVCommon(53,'disbursalMakerForm','loanNo','','', '','','generateValuesDisbursal','hid');closeLovCallonLov1();" 
													styleClass="lovbutton"> </html:button>
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="" />
												<html:hidden property="hid" styleId="hid" value="" />
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.customerName" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="customerName"
													styleId="customerName" maxlength="50" readonly="true"
													value="" tabindex="-1" />
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.loanApprovalDate" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="loanApprovalDate"
													styleId="loanApprovalDate" value="" readonly="true"
													tabindex="-1" />
											</td>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.disbursalDate" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursalDate" 
													styleId="disbursalDate" maxlength="10" 
													onchange="checkDate('disbursalDate');" value="<%=initiationDate %>" />
											</td>
											
										</tr>
										<tr>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.product" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="product"
													styleId="product" value="" readonly="true" maxlength="50"
													tabindex="-1" />
											</td>
																						
											<td nowrap="nowrap">
												<bean:message key="lbl.scheme" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="scheme"
													styleId="scheme" maxlength="50" value="" readonly="true"
													tabindex="-1" />
											</td>
											
										</tr>

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.loanAmt" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="loanAmt"
													styleId="loanAmt" value="" readonly="true" maxlength="18"
													tabindex="-1" style="text-align:right;"/>
											</td>
												<td nowrap="nowrap">
												<bean:message key="lbl.disbursedAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursedAmount"
													styleId="disbursedAmount" maxlength="18" value=""
													readonly="true" tabindex="-1" style="text-align:right;"/>
											</td>

										</tr>
										
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.disbursalTo" />
												
											</td>
											<td nowrap="nowrap">
												 
									              <select name="disbursalTo" id="disbursalTo" class="text" onchange="activeSupplier(this.value);getShortPayOnDisbursalTo(this.value);" >
									              	<option value="CS">Customer</option>
									              	<option value="SU">Supplier</option>
									              	<option value="MF">Manufacturer</option>
									              </select>
											</td>
											<td nowrap="nowrap">
											<div id="supplierLableDiv" style="display:none">
												<bean:message key="lbl.supplier"/><font color="red">*</font>
											</div>
											<div id="manufactLableDiv" style="display:none">
												<bean:message key="lbl.manufact"/><font color="red">*</font>
											</div>
														
												
											</td>
											<td nowrap="nowrap">
												<div id="supplierDiv" style="display:none">

													<html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="" tabindex="-1"/>
													<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
					              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
					              					<html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(375,'disbursalMakerForm','businessPartnerType','loanNo|disbursalTo', 'lbxBusinessPartnearHID','lbxLoanNoHID|disbursalTo','Select Loan Account LOV First','clearTALoanLOV','bankAcountName','businessPartnerTypeDesc','businessPartnerName');closeLovCallonLov('loanNo');" value=" " styleClass="lovbutton"></html:button>
              										<html:hidden property="businessPartnerName" styleId="businessPartnerName"  value="" styleClass="text" />
              										<html:hidden property="bankAcountName" styleId="bankAcountName"  value="" styleClass="text" />
												</div>
												<html:hidden  property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="" />
											
											</td>
										</tr>
										
										<tr id="taLoanNoTrDiv" style="display: none;">
										
											<td nowrap="nowrap">
												<bean:message key="lbl.taLoanNo" />
												
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="taLoanNo" styleId="taLoanNo" value="" readonly="true" maxlength="20" />
												
												<html:button property="loanButton" styleId="loanButton" value=" " onclick="setSupplierManufacturerIdInSession();openLOVCommon(452,'disbursalMakerForm','taLoanNo','businessPartnerTypeDesc','lbxTaLoanNoHID', 'lbxBusinessPartnearHID','Select Supplier/Manufacturer First','','taCustomerName');closeLovCallonLov('taLoanNo');" styleClass="lovbutton"> </html:button>
<!--												<html:button property="chargeButton" styleId="chargeButton" onclick="cleanField('C');openLOVCommon(198,'waiveOffMakerFormCSE','chargeDescription','valueDate|businessPartnerTypeDesc|loanAccountNo','lbxChargeCodeHID', 'initiationDateDbFormat|businessPartnerType|lbxLoanNoHID','Select WaiveOff Date First|Select BP Type LOV First|Select Loan Account LOV First','getWaiveOffList','chargeType','htxnAdviceId','htype');closeLovCallonLov('chargeType');" value=" " styleClass="lovbutton"></html:button>-->
												
												<input type="hidden" name="contextPath"	value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxTaLoanNoHID" styleId="lbxTaLoanNoHID" value="" />
												<html:hidden property="hid" styleId="hid" value="" />
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.taCustomerName" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="taCustomerName"
													styleId="taCustomerName" maxlength="50" readonly="true"
													value="" tabindex="-1" />
											</td>
											
										</tr>
										
										<tr>
										
											<td nowrap="nowrap">
												<bean:message key="lbl.proposedShortPayAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="proposedShortPayAmount" style="text-align:right;"
													styleId="proposedShortPayAmount" maxlength="18" value=""
													readonly="true" tabindex="-1" />
											</td>
											
												<td nowrap="nowrap">
												<bean:message key="lbl.adjustedShortPayAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="adjustedShortPayAmount" style="text-align:right;"
													styleId="adjustedShortPayAmount" maxlength="18" value=""
													readonly="true" tabindex="-1" />
											</td>

										</tr>

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.disbursalAmount" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursalAmount"
													styleId="disbursalAmount" value=""
													onkeypress="return numbersonly(event,id,18)"
													onblur="formatNumber(this.value,id);"
													onkeyup="checkNumber(this.value, event, 18,id);"
													onfocus="keyUpNumber(this.value, event, 18,id);" 
													style="text-align:right;" onchange="changeDisbursAmount()"/>
											</td>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.shortPayAmount" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="shortPayAmount"
													styleId="shortPayAmount" value="" style="text-align:right;"
													onkeypress="return numbersonly(event,id,18)"
													onblur="formatNumber(this.value,id);"
													onkeyup="checkNumber(this.value, event, 18,id);"
													onfocus="keyUpNumber(this.value, event, 18,id);" />
											</td>
										</tr>
											
										<tr>
											<td nowrap="nowrap">
												
											</td>
											<td nowrap="nowrap">
											<html:hidden property="maxExpectedPayDate" styleId="maxExpectedPayDate" value=""/>
											
												<div id="expectedPmntDtInsDiv" style="display: none;">
													<html:hidden styleClass="text" property="expectedPaymentDate"
														styleId="expectedPmntDtIns"  value="<%=initiationDate %>"
														onchange="checkDate('expectedPmntDtIns');"
														/>
												</div>
												
												<div id="expectedPmntDtNonInsDiv" style="display: none;">
													<html:hidden styleClass="text" property="expectedPaymentDate"
														styleId="expectedPmntDtNonIns"  value="<%=initiationDate %>"
													
														/>
												</div>
												<div id="expectedPmntDtDiv">
													<html:hidden styleClass="text" property="expectedPaymentDate"
														styleId="expectedPmntDt"  value="<%=initiationDate %>"
														onchange="checkDate('expectedPmntDt');"
														/>
												</div>
												
											</td>
											<td>				
											</td>
											<td></td>
										</tr>

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.finalDisbursal" />
											</td>
											<td nowrap="nowrap">
												<div id="finDisb">
													<input type="checkbox" name="finalDisbursal" id="finalDisbursal" onclick="showEffectiveDate();" />
													<html:hidden property="repayMode" styleId="repayMode" styleClass="text" value=""/>
												</div>
											</td>
											<td>
												<bean:message key="lbl.repayEffDate" />
											</td>
											<td>
												<div id="repayEffId" style="display: none;">
													<html:text styleClass="text" property="repayEffDate"
														styleId="repayEffDate" maxlength="10" value="<%=initiationDate %>"
														onchange="checkDate('repayEffDate');"
														tabindex="-1"/>
												</div>
												<div id="repayId">
													<html:text styleClass="text" property="repayEffDate"
														styleId="repayEff" value="" disabled="true" maxlength="10" 
														tabindex="-1"/>
												</div>
												<div id="repayIdBusDate" style="display: none;">
													<html:text styleClass="text" property="repayEffDate" 
														styleId="repayEffBusDate" value=""  maxlength="10" onchange="checkDate('repayEffBusDate');"/>
												</div>
											</td>
										</tr>
										<tr>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.cycleDate" />
											</td>
											<td nowrap="nowrap">
												
											<div id="no3">
	            									<html:select property="cycleDate" styleId="cycleDate" styleClass="text" 
	            										disabled="true"  onchange="nullNextDue(this.value);">
														<html:option value="">--<bean:message key="lbl.select" />--</html:option>
			 										</html:select>
												</div>
												<div id="yes3" style="display: none;">
	            									<input type="hidden" name="hiddenDueDate" id="hiddenDueDate" value="${disbursalDataNew[0].cycleDate}"/>
														<html:select property="cycleDate" styleId="cycleDate" value="${disbursalDataNew[0].cycleDate}" styleClass="text"  onchange="nullNextDue(this.value);">
																    <html:option value="">--<bean:message key="lbl.select" />--</html:option>
																    <logic:present name="cycleDate">
																    <html:optionsCollection name="cycleDate" label="id" value="name" />
																    </logic:present>
														</html:select>
												</div>
											</td>
											<td>
												<input type="hidden" id="val" value='N'/>
											     <div id="no1">
											     	<bean:message key="lbl.nextDueDate" />
											     </div>
											     <div id="yes1" style="display: none">
											     	<bean:message key="lbl.nextDueDate" /><font color="red">*</font>
											     </div>
											</td>
										    <td>
										    	<div id="no2">
										    		<html:text property="nextDueDateNo" styleClass="text" value="" tabindex="-1"  disabled="true" />
										    	</div>
										    	<div id="yes2" style="display: none">
										    		<html:text property="nextDueDate" styleClass="text"  value="${disbursalData[0].nextDueDate}"
											          styleId="nextDueDate" onchange="checkDueDate(value);checkRepayEffectiveDate(value);" />
											    </div>										    	
										    </td>
										</tr>
										<tr>
										
											<td nowrap="nowrap"><bean:message key="lbl.currentMonthEMI"/>
											<input type="hidden" id="EMIFlag" value="F"/>
											</td>
											<td nowrap="nowrap">
												<html:text property="currentMonthEMI" styleClass="text"  value="${disbursalData[0].currentMonthEMI}" styleId="currentMonthEMI" readonly="true" style="text-align:right;"/>
											</td>
											<%-- <td nowrap="nowrap"><bean:message key="lbl.nextMonthEMI"/></td> --%>										
											<td nowrap="nowrap">
												<html:hidden property="preEMINextMonth" styleClass="text"  value="${disbursalData[0].preEMINextMonth}"  styleId="preEMINextMonth"  style="text-align:right;"/>
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.makerRemark" />
<!--												<font color="red">*</font>--><!-- Commented by Nishant -->
											</td>
											<td nowrap="nowrap">
												<html:textarea property="disbursalDescription"
													styleId="disbursalDescription" value="" styleClass="text"></html:textarea>
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.authorRemarks" />
											</td>
										
											<td nowrap="nowrap">
												<html:textarea styleClass="text"
													property="authorRemarks"
													styleId="authorRemarks" 
													disabled="true"
													value="${disbursalDataNew[0].authorRemarks}"
													 />
											</td>
										</tr>
									</table>
							<button type="button" name="EMI" id="EMI" 
										class="topformbutton4" title="Alt+E" accesskey="E"
										onclick="return calculateEMI()"><bean:message key="button.calculatePreEMI" /></button>
									<button type="button" name="save" id="save"
										class="topformbutton2" title="Alt+V" accesskey="V"
										onclick="return saveDisbursalData(document.getElementById('lbxLoanNoHID').value);"><bean:message key="button.save" /></button>
									<button type="button" name="saveFwd" id="saveFwd" title="Alt+F" accesskey="F"
										 class="topformbutton2"
										onclick="return updateDisbursalBeforeSave();"><bean:message key="button.forward" /></button>
									<button type="button" name="generateRepaymentSch"
										id="generateRepaymentSch" 
										class="topformbutton4" title="Alt+S" accesskey="S"

					
										onclick="return callRepayBeforeSave('<bean:message key="msg.saveBeforeRepay" />');"
										disabled="disabled" ><bean:message key="button.repayschedule" /></button>

									<button type="button" name="oldDisbursal" id="oldDisbursal"
										class="topformbutton4" title="Alt+O" accesskey="O"
										onclick="return openDisbursalSchedule();"><bean:message key="button.olddisbursal" /></button>
									<button type="button" name="recieveButton" id="recieveButton"
										class="topformbutton3" title="Alt+R" accesskey="R"
										onclick="return viewReceivableDisbursal('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.viewRecievable" /></button>
								
								<button type="button" name="specialCondButton" id="specialCondButton"
										class="topformbutton4" title="Alt+S" accesskey="S"
										onclick="return viewSpecialCondButton('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.specialCondition" /></button>
								
								
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
			</logic:present>


			<!-- --------------------------------------- Disbursal New Ends --------------------------------- -->

			<!-- --------------------------------------- Disbursal New Failed Starts--------------------------------- -->

			<logic:present name="disbursalDataNew">
			

				<html:form action="/disbursalMaker" method="post"
					styleId="disbursalMakerForm">
					<input type="hidden" name="maxDisbursalDate" id="maxDisbursalDate" value="${maxDisbursalDate}" />
					<html:javascript
						formName="DisbursedInitiationMakerDynaValidatorForm" />
					<html:errors />
					<fieldset>
						<legend>
							<bean:message key="lbl.disbursalDetails" />
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<logic:iterate id="disbursalDataNewList" name="disbursalDataNew">
							<tr>
								<td>
									<input type="hidden" name="businessDate" id="businessDate"
										value="<%=initiationDate%>" />
									<html:hidden property="disbursalNo" styleId="disbursalNo"
										value="${disbursalDataNew[0].disbursalNo}" />
									<input type="hidden" id="modifyRecord" name="modifyRecord" value="CM"/>
									<input type="hidden" id="pdcDepositCount" name="pdcDepositCount" 
										value="${disbursalDataNew[0].pdcDepositCount}"/>

						
						
									<table width="100%" border="0" cellspacing="1" cellpadding="1">

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.loanNo" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="loanNo"
													styleId="loanNo" tabindex="-1"
													value="${disbursalDataNew[0].loanNo}" readonly="true"
													maxlength="20" />
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="${disbursalDataNew[0].lbxLoanNoHID}" />
												<html:hidden property="hid" styleId="hid" value="" />
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.customerName" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="customerName"
													styleId="customerName"
													value="${disbursalDataNew[0].customerName}" maxlength="50"
													readonly="true" tabindex="-1" />
											</td>
										</tr>
										<tr>
										
											<td nowrap="nowrap">
												<bean:message key="lbl.loanApprovalDate" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="loanApprovalDate"
													styleId="loanApprovalDate"
													value="${disbursalDataNew[0].loanApprovalDate}"
													maxlength="10" readonly="true" tabindex="-1" />
											</td>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.disbursalDate" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursalDate"
													styleId="disbursalDate" maxlength="10" 
													value="${disbursalDataNew[0].disbursalDate}" 
													onchange="checkDate('disbursalDate');" />
											</td>
											
										</tr>
										<tr>
										
											<td nowrap="nowrap">
												<bean:message key="lbl.product" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="product"
													styleId="product" tabindex="-1"
													value="${disbursalDataNew[0].product}" readonly="true"
													maxlength="20" />
											</td>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.scheme" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="scheme"
													styleId="scheme" tabindex="-1" maxlength="50"
													value="${disbursalDataNew[0].scheme}" readonly="true" />
											</td>
											
										</tr>

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.loanAmt" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="loanAmt"
													styleId="loanAmt" tabindex="-1"
													value="${disbursalDataNew[0].loanAmt}" readonly="true"
													maxlength="18" style="text-align:right;"/>
											</td>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.disbursedAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursedAmount"
													styleId="disbursedAmount" maxlength="18"
													value="${disbursalDataNew[0].disbursedAmount}"
													readonly="true" tabindex="-1" style="text-align:right;"/>
											</td>
										</tr>
										
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.disbursalTo" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												 
									              <html:select property="disbursalTo" styleId="disbursalTo" styleClass="text" onchange="activeSupplier(this.value);getShortPayOnDisbursalTo(this.value);" value="${disbursalDataNew[0].disbursalTo}">
									              	<html:option value="CS">Customer</html:option>
									              	<html:option value="SU">Supplier</html:option>
									              	<html:option value="MF">Manufacturer</html:option>
									            </html:select>
											</td>
											<td nowrap="nowrap">

											<logic:equal name="disbursalTo" value='CS'>
												<div id="supplierLableDiv" style="display:none">
													<bean:message key="lbl.supplier"/>
											
													</div>
												<div id="manufactLableDiv" style="display:none">
													<bean:message key="lbl.manufact"/>
											
													</div>
											</logic:equal>
											<logic:notEqual name="disbursalTo" value='CS'>
												<logic:equal name="disbursalTo"  value='SU'>
													<div id="supplierLableDiv">
													<bean:message key="lbl.supplier"/><font color="red">*</font>
											
													</div>
													<div id="manufactLableDiv" style="display:none">
														<bean:message key="lbl.manufact"/><font color="red">*</font>
													</div>
												</logic:equal>
												<logic:equal name="disbursalTo" value='MF'>
													<div id="manufactLableDiv">
														<bean:message key="lbl.manufact"/><font color="red">*</font>
													</div>
													<div id="supplierLableDiv" style="display:none">
													<bean:message key="lbl.supplier"/><font color="red">*</font>
											
													</div>
												</logic:equal>
											</logic:notEqual>
											
											</td>
											<td nowrap="nowrap">
											<logic:notEqual name="disbursalTo"  value='CS'>
												<div id="supplierDiv" >
													<html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="${disbursalDataNew[0].businessPartnerTypeDesc}" tabindex="-1"/>
													<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
					              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
					              					<html:hidden  property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="${disbursalDataNew[0].lbxBusinessPartnearHID}" />
													<html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(375,'disbursalMakerForm','businessPartnerType','loanNo|disbursalTo', 'lbxBusinessPartnearHID','lbxLoanNoHID|disbursalTo','Select Loan Account LOV First','clearTALoanLOV','bankAcountName','businessPartnerTypeDesc','businessPartnerName');closeLovCallonLov('loanNo');" value=" " styleClass="lovbutton"></html:button>
              										<html:hidden property="businessPartnerName" styleId="businessPartnerName"  value="" styleClass="text" />
              										<html:hidden property="bankAcountName" styleId="bankAcountName"  value="" styleClass="text" />
												</div>
											</logic:notEqual>
											<logic:equal name="disbursalTo" value='CS'>
												<div id="supplierDiv" style="display:none;">
													<html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="${disbursalDataNew[0].businessPartnerTypeDesc}" tabindex="-1"/>
													<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
					              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
					              					<html:hidden  property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="${disbursalDataNew[0].lbxBusinessPartnearHID}" />
													<html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(375,'disbursalMakerForm','lbxBusinessPartnearHID','loanNo|disbursalTo', 'lbxBusinessPartnearHID','lbxLoanNoHID|disbursalTo','Select Loan Account LOV First','','businessPartnerType','businessPartnerTypeDesc','businessPartnerName');closeLovCallonLov('loanNo');" value=" " styleClass="lovbutton"></html:button>
              										<html:hidden property="businessPartnerName" styleId="businessPartnerName"  value="" styleClass="text" />
												</div>
										</logic:equal>
												
											
											</td>
										</tr>
										<logic:equal name="disbursalDataNewList" property="repayMode" value="I">
										<tr>
										
											<td nowrap="nowrap">
												<bean:message key="lbl.taLoanNo" />
												
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="taLoanNo" styleId="taLoanNo" value="${disbursalDataNew[0].taLoanNo}" readonly="true" maxlength="20" />
												
												<html:button property="loanButton" styleId="loanButton" value=" " onclick="setSupplierManufacturerIdInSession();openLOVCommon(452,'disbursalMakerForm','taLoanNo','businessPartnerTypeDesc','lbxTaLoanNoHID', 'lbxBusinessPartnearHID','Select Supplier/Manufacturer First','','taCustomerName');closeLovCallonLov('taLoanNo');" styleClass="lovbutton"> </html:button>
												
												<input type="hidden" name="contextPath"	value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxTaLoanNoHID" styleId="lbxTaLoanNoHID" value="${disbursalDataNew[0].lbxTaLoanNoHID}" />
												<html:hidden property="hid" styleId="hid" value="" />
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.taCustomerName" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="taCustomerName"
													styleId="taCustomerName" maxlength="50" readonly="true"
													value="${disbursalDataNew[0].taCustomerName}" tabindex="-1" />
											</td>
											
										</tr>
										</logic:equal>

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.proposedShortPayAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="proposedShortPayAmount" style="text-align:right;"
													styleId="proposedShortPayAmount" maxlength="18"
													value="${disbursalDataNew[0].proposedShortPayAmount}"
													readonly="true" tabindex="-1" />
											</td>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.adjustedShortPayAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="adjustedShortPayAmount"
													styleId="adjustedShortPayAmount" maxlength="18"
													value="${disbursalDataNew[0].adjustedShortPayAmount}"
													readonly="true" tabindex="-1" style="text-align:right;"/>
											</td>
											

										</tr>

										<tr>
										
											<td nowrap="nowrap">
												<bean:message key="lbl.disbursalAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursalAmount"
													styleId="disbursalAmount"
													onkeypress="return numbersonly(event,id,18)"
													onblur="formatNumber(this.value,id);"
													onkeyup="checkNumber(this.value, event, 18,id);"
													onfocus="keyUpNumber(this.value, event, 18,id);" 
													style="text-align:right;" onchange="changeDisbursAmount()"/>
											</td>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.shortPayAmount" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="shortPayAmount"
													styleId="shortPayAmount"
													
													onkeypress="return numbersonly(event,id,18)"
													onblur="formatNumber(this.value,id);"
													onkeyup="checkNumber(this.value, event, 18,id);"
													onfocus="keyUpNumber(this.value, event, 18,id);" 
													style="text-align:right;"/>
											</td>
											
										</tr>
			
										<tr>
											<td nowrap="nowrap">
											
											</td>
											<td nowrap="nowrap">
												<html:hidden property="maxExpectedPayDate" styleId="maxExpectedPayDate" value="${disbursalDataNew[0].maxExpectedPayDate}"/>										
												<logic:equal name="disbursalDataNewList" property="repayMode" value="I">
												
													<html:hidden styleClass="text" property="expectedPaymentDate"
														styleId="expectedPmntDtIns"  value="${disbursalDataNew[0].expectedPaymentDate}"
														onchange="checkDate('expectedPmntDtIns');"
														/>
												</logic:equal>
												
												<logic:notEqual name="disbursalDataNewList" property="repayMode" value="I">
												
													<html:hidden styleClass="text" property="expectedPaymentDate"
														styleId="expectedPmntDtNonIns"  value="${disbursalDataNew[0].expectedPaymentDate}"
													
														/>
												</logic:notEqual>
												
												<div id="expectedPmntDtDiv" style="display: none;">
													<html:hidden styleClass="text" property="expectedPaymentDate"
														styleId="expectedPmntDt"  value="${disbursalDataNew[0].expectedPaymentDate}"
														onchange="checkDate('expectedPmntDt');"
														/>
												</div>
											</td>
											<td>		
											</td>
											<td></td>
										</tr>
												
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.finalDisbursal" />
											</td>
											<td nowrap="nowrap">
												<logic:equal name="disbursalDataNewList" property="repayMode" value="I">
												<logic:equal value="F" name="fianlDisb">
                                              
													<input type="checkbox" name="finalDisbursal"
													id="finalDisbursal" checked="checked"
														onclick="showEffectiveDate();" />
												</logic:equal>
												<logic:notEqual name="fianlDisb" value="F">

												<input type="checkbox" name="finalDisbursal"
														id="finalDisbursal" onclick="showEffectiveDate();" />
												</logic:notEqual>
													<html:hidden property="repayMode" styleId="repayMode" 
														styleClass="text" value="${disbursalDataNew[0].repayMode}"/>
												</logic:equal>
												
												<logic:equal name="disbursalDataNewList" property="repayMode" value="N">
													<input type="checkbox" name="finalDisbursal" disabled="disabled"
														id="finalDisbursal" onclick="showEffectiveDate();" />
													<html:hidden property="repayMode" styleId="repayMode" 
														styleClass="text" value="${disbursalDataNew[0].repayMode}"/>
												</logic:equal>
											</td>
											<td>
												<bean:message key="lbl.repayEffDate" />
											</td>
											<td>
												<logic:equal name="disbursalDataNewList" property="repayMode" value="I">
												
												
												<logic:equal value="F" name="fianlDisb">
												<div id="repayEffId">
													<html:text styleClass="text" property="repayEffDate"
														styleId="repayEffDate" maxlength="10" value="${disbursalDataNew[0].repayEffDate}"
														onchange="checkDate('repayEffDate');" 
														tabindex="-1"/>
															</div>
												</logic:equal>
											
												
												<logic:notEqual name="fianlDisb" value="F">
											<div id="repayId">
													<html:text styleClass="text" property="repayEffDate"
														styleId="repayEff" disabled="true" maxlength="10"
														value="" tabindex="-1"/>
											</div>
												</logic:notEqual>
												
												</logic:equal>
												<div id="repayEffId" style="display:none">
													<html:text styleClass="text" property="repayEffDate"
														styleId="repayEffDate" maxlength="10" value="${disbursalDataNew[0].repayEffDate}"
														onchange="checkDate('repayEffDate');" 
														tabindex="-1"/>
															</div>
													<div id="repayId" style="display:none">
													<html:text styleClass="text" property="repayEffDate"
														styleId="repayEff" disabled="true" maxlength="10"
														value="" tabindex="-1"/>
											</div>
											<logic:notEqual name="disbursalDataNewList" property="repayMode" value="I">
												<div id="repayIdBusDate" >
													<html:text styleClass="text" property="repayEffDate" 
														styleId="repayEffBusDate" value="${disbursalDataNew[0].repayEffDate}"  maxlength="10" onchange="checkDate('repayEffBusDate');"/>
												</div>
											</logic:notEqual>
											</td>
										</tr>
										<tr>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.cycleDate" />
											</td>
											<td nowrap="nowrap">
											
											    
											    
											<div id="cycleDateI">
												<logic:equal name="disbursalDataNewList" property="repayMode" value="I">
													
												<logic:equal value="F" name="fianlDisb">
											
												
											    
														<logic:present name="cycleDate">
															
														<logic:notEmpty name="cycleDate">
														
															<div id="no2" style="display: none">
																<input type="hidden" name="hiddenDueDate" id="hiddenDueDate" value="${disbursalDataNew[0].cycleDate}"/>
																<html:select property="cycleDate" styleId="cycleDate" value="${disbursalDataNew[0].cycleDate}" styleClass="text"  onchange="nullNextDue(this.value);"  disabled="true">
																		<html:option value="">--<bean:message key="lbl.select" />--</html:option>
																</html:select>				
															</div>
															
															<div id="yes2" >
																<input type="hidden" name="hiddenDueDate" id="hiddenDueDate" value="${disbursalDataNew[0].cycleDate}"/>
																<html:select property="cycleDate" styleId="cycleDate" value="${disbursalDataNew[0].cycleDate}" styleClass="text"  onchange="nullNextDue(this.value);">
																    <html:option value="">--<bean:message key="lbl.select" />--</html:option>
																    <html:optionsCollection name="cycleDate" label="id" value="name" />
																</html:select>
															</div>
															
														</logic:notEmpty>
														
														</logic:present>
												
												</logic:equal>
												
												<logic:notEqual name="fianlDisb" value="F">
													<div id="cycleDateI">
												
	            									      <div id="no2" style="display: none">
																<input type="hidden" name="hiddenDueDate" id="hiddenDueDate" value="${disbursalDataNew[0].cycleDate}"/>
																<html:select property="cycleDate" styleId="cycleDate" value="${disbursalDataNew[0].cycleDate}" styleClass="text"  onchange="nullNextDue(this.value);"  disabled="true">
																		<html:option value="">--<bean:message key="lbl.select" />--</html:option>
																</html:select>				
															</div>
															
															<div id="yes2" >
																<input type="hidden" name="hiddenDueDate" id="hiddenDueDate" value="${disbursalDataNew[0].cycleDate}"/>
																<html:select property="cycleDate" styleId="cycleDate" value="${disbursalDataNew[0].cycleDate}" styleClass="text"  disabled="true" onchange="nullNextDue(this.value);">
																    <html:option value="">--<bean:message key="lbl.select" />--</html:option>
																    <html:optionsCollection name="cycleDate" label="id" value="name" />
																</html:select>
															</div>
			 										
												</div>
												</logic:notEqual>
												</logic:equal>
												
											</div>
												<logic:notEqual name="disbursalDataNewList" property="repayMode" value="I">
													<html:select property="cycleDate" styleId="cycleDate" styleClass="text" 
	            										disabled="true"  onchange="nullNextDue(this.value);">
														<html:option value="">--<bean:message key="lbl.select" />--</html:option>
			 										</html:select>
												</logic:notEqual>
												<div id="cycleDateFinal"  style="display: none">
													<logic:present name="cycleDate">
												 
		            								<html:select property="cycleDate" styleId="cycleDate" value="${disbursalData[0].cycleDateValue}" styleClass="text"  onchange="nullNextDue(this.value);">
		            									<logic:notEmpty name="cycleDate">
		            									     <html:option value="">--<bean:message key="lbl.select" />--</html:option>
															<html:optionsCollection name="cycleDate" label="id" value="name" />
				 										</logic:notEmpty>
				 										</html:select>
													</logic:present>
												</div>
											</td>
											<td>
										
											<logic:present name="fianlDisb">
												<logic:equal value="F" name="fianlDisb">
													<input type="hidden" id="val" value="Y" />
												</logic:equal>
												<logic:notEqual value="F" name="fianlDisb">
													<input type="hidden" id="val" value="N" />
												</logic:notEqual>
											</logic:present>
											<logic:notPresent name="fianlDisb">
											
													<input type="hidden" id="val" value="N" />
												
											</logic:notPresent>
											
											     <div id="no1">
											     	<bean:message key="lbl.nextDueDate" />
											     </div>
											     <div id="yes1" style="display: none">
											     	<bean:message key="lbl.nextDueDate" /><font color="red">*</font>
											     </div>
											</td>
										    <td >
										    <logic:present name="fianlDisb">
										  
										    <logic:equal value="F" name="fianlDisb">
										   
										        <div id="no3" style="display: none;">
										    		<html:text property="nextDueDateNo" value="" styleClass="text" disabled="true" />
										    	</div>
										    	<div id="yes3" >
										     <html:text property="nextDueDate" styleClass="text"  value="${disbursalDataNew[0].nextDueDate}"
											          styleId="nextDueDate" onchange="checkDueDate(value);checkRepayEffectiveDate(value);" />
											    </div>
										    </logic:equal>
										    	 <logic:notEqual value="F" name="fianlDisb">
										    	<div id="no3">
										    		<html:text property="nextDueDateNo" value="" styleClass="text"  disabled="true" />
										    	</div>
										    	<div id="yes3" style="display: none">
										    		<html:text property="nextDueDate" styleClass="text"  value="${disbursalDataNew[0].nextDueDate}"
											          styleId="nextDueDate" onchange="checkDueDate(value);checkRepayEffectiveDate(value);" />
											    </div>	
											    </logic:notEqual>
											    </logic:present>									    	
											
												 <logic:equal name="disbursalDataNewList" property="repayMode" value="N">
											    	<div id="no3">
										    		<html:text property="nextDueDateNo" value="" styleClass="text"  disabled="true" />
										    	</div>
											    </logic:equal>					
										    </td>
											
										</tr>
										<tr>
											<td nowrap="nowrap"><bean:message key="lbl.currentMonthEMI"/>
											<input type="hidden" id="EMIFlag" value="F"/>
											</td>
											<td nowrap="nowrap">
												<html:text property="currentMonthEMI" styleClass="text"  value="${disbursalData[0].currentMonthEMI}" styleId="currentMonthEMI" readonly="true" style="text-align:right;"/>
											</td>
											<%-- <td nowrap="nowrap"><bean:message key="lbl.nextMonthEMI"/></td> --%>										
											<td nowrap="nowrap">
												<html:hidden property="preEMINextMonth" styleClass="text"  value="${disbursalData[0].preEMINextMonth}"  styleId="preEMINextMonth"  style="text-align:right;"/>
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.makerRemark" />
<!--												<font color="red">*</font>--><!-- Commented by Nishant -->
											</td>
											<td nowrap="nowrap">
												<html:textarea styleClass="text"
													property="disbursalDescription"
													styleId="disbursalDescription" styleClass="text" />
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.authorRemarks" />
											</td>
										
											<td nowrap="nowrap">
												<html:textarea styleClass="text"
													property="authorRemarks"
													styleId="authorRemarks" 
													disabled="true"
													value="${disbursalDataNew[0].authorRemarks}"
													 />
											</td>
										</tr>
									</table>
								<logic:equal name="disbursalDataNewList" property="repayMode" value="I">
									<button type="button" name="EMI" id="EMI" 
										class="topformbutton4" title="Alt+E" accesskey="E"
										onclick="return calculateEMI()"><bean:message key="button.calculatePreEMI" /></button>
								</logic:equal>

									<button type="button" name="save" id="save" 
										class="topformbutton2" title="Alt+V" accesskey="V"
										onclick="return saveDisbursalData(document.getElementById('lbxLoanNoHID').value);"><bean:message key="button.save" /></button>
									<button type="button" name="saveFwd" id="saveFwd" title="Alt+F" accesskey="F"
										 class="topformbutton2"
										onclick="return updateDisbursalBeforeSave();"><bean:message key="button.forward" /></button>
									<button type="button" name="generateRepaymentSch"
										id="generateRepaymentSch" 
										class="topformbutton4" title="Alt+S" accesskey="S"
			onclick="return callRepayBeforeSave('<bean:message key="msg.saveBeforeRepay" />');"
										disabled="disabled" ><bean:message key="button.repayschedule" /></button>

									
	
							
									<button type="button" name="oldDisbursal" id="oldDisbursal"
										 class="topformbutton4" title="Alt+O" accesskey="O"
										onclick="return openDisbursalSchedule();"><bean:message key="button.olddisbursal" /></button>
									<button type="button" class="topformbutton3" title="Alt+R" accesskey="R"
										onclick="return viewReceivableDisbursal('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.viewRecievable" /></button>
								
									<button type="button" name="specialCondButton" id="specialCondButton"
										class="topformbutton4" title="Alt+S" accesskey="S"
										onclick="return viewSpecialCondButton('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.specialCondition" /></button>
								
								</td>
							</tr>
							</logic:iterate>
						</table>
					</fieldset>
				</html:form>
			</logic:present>

			<!-- --------------------------------------- Disbursal New Failed Ends --------------------------------- -->

			<!-- --------------------------------------- Disbursal Maker Starts--------------------------------- -->


			<logic:present name="disbursalData">
		
				 <div id="no3" style="display: none;"></div>
			  <div id="yes3" style="display: none;"></div>
				<html:form action="/disbursalMaker" method="post"
					styleId="disbursalMakerForm">
					<input type="hidden" name="maxDisbursalDate" id="maxDisbursalDate" value="${maxDisbursalDate}" />
					<html:javascript
						formName="DisbursedInitiationMakerDynaValidatorForm" />
					<html:errors />
					<fieldset>
						<legend>
							<bean:message key="lbl.disbursalDetails" />
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="hidden" name="businessDate" id="businessDate"
										value="<%=initiationDate%>" />
									<html:hidden property="disbursalNo" styleId="disbursalNo"
										value="${disbursalData[0].disbursalNo}" />
									<input type="hidden" id="modifyRecord" name="modifyRecord" value="CM"/>
									<input type="hidden" id="pdcDepositCount" name="pdcDepositCount"
										value="${disbursalData[0].pdcDepositCount}" />
										
									

									<table width="100%" border="0" cellspacing="1" cellpadding="1">
									<logic:iterate id="disbursalDataList" name="disbursalData">
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.loanNo" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="loanNo"
													styleId="loanNo" tabindex="-1"
													value="${disbursalData[0].loanNo}" readonly="true"
													maxlength="20" />
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="${disbursalData[0].lbxLoanNoHID}" />
												<html:hidden property="hid" styleId="hid" value="" />
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.customerName" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="customerName"
													styleId="customerName"
													value="${disbursalData[0].customerName}" maxlength="50"
													disabled="true" tabindex="-1" />
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.loanApprovalDate" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="loanApprovalDate"
													styleId="loanApprovalDate"
													value="${disbursalData[0].loanApprovalDate}" maxlength="10"
													disabled="true" tabindex="-1" />
											</td>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.disbursalDate" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursalDate"
													styleId="disbursalDate" maxlength="10"
													value="${disbursalData[0].disbursalDate}" 
													onchange="checkDate('disbursalDate');"/>
											</td>
											
										</tr>
										<tr>
										
											<td nowrap="nowrap">
												<bean:message key="lbl.product" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="product"
													styleId="product" tabindex="-1"
													value="${disbursalData[0].product}" disabled="true"
													maxlength="50" />
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.scheme" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="scheme"
													styleId="scheme" tabindex="-1" maxlength="50"
													value="${disbursalData[0].scheme}" disabled="true" />
											</td>
											
										</tr>

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.loanAmt" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="loanAmt"
													styleId="loanAmt" value="${disbursalData[0].loanAmt}"
													disabled="true" maxlength="18" tabindex="-1" 
													style="text-align:right;"/>
											</td>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.disbursedAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursedAmount"
													styleId="disbursedAmount" maxlength="18"
													value="${disbursalData[0].disbursedAmount}" disabled="true"
													tabindex="-1" style="text-align:right;"/>
											</td>

										</tr>
										
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.disbursalTo" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												 
									              <html:select property="disbursalTo" styleId="disbursalTo" styleClass="text" onchange="activeSupplier(this.value);getShortPayOnDisbursalTo(this.value);" value="${requestScope.disbursalTo}">
									              	<html:option value="CS">Customer</html:option>
									              	<html:option value="SU">Supplier</html:option>
									              	<html:option value="MF">Manufacturer</html:option>
									            </html:select>
											</td>
											<td nowrap="nowrap">
											<logic:equal name="disbursalTo" value='CS'>
												<div id="supplierLableDiv" style="display:none">
													<bean:message key="lbl.supplier"/>
											
													</div>
												<div id="manufactLableDiv" style="display:none">
													<bean:message key="lbl.manufact"/>
											
													</div>
											</logic:equal>
											<logic:notEqual name="disbursalTo" value='CS'>
												<logic:equal name="disbursalTo"  value='SU'>
													<div id="supplierLableDiv">
													<bean:message key="lbl.supplier"/><font color="red">*</font>
											
													</div>
													<div id="manufactLableDiv" style="display:none">
														<bean:message key="lbl.manufact"/><font color="red">*</font>
													</div>
												</logic:equal>
												<logic:equal name="disbursalTo" value='MF'>
													<div id="manufactLableDiv">
														<bean:message key="lbl.manufact"/><font color="red">*</font>
													</div>
													<div id="supplierLableDiv" style="display:none">
													<bean:message key="lbl.supplier"/><font color="red">*</font>
											
													</div>
												</logic:equal>
											</logic:notEqual>
												
											</td>
											<td nowrap="nowrap">
											<logic:notEqual name="disbursalTo"  value='CS'>
												<div id="supplierDiv" >
												
													<html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="${disbursalData[0].supplierDesc}" tabindex="-1"/>
													<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
					              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
					              					<html:hidden  property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="${disbursalData[0].lbxBusinessPartnearHID}" />
													<html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(375,'disbursalMakerForm','businessPartnerType','loanNo|disbursalTo', 'lbxBusinessPartnearHID','lbxLoanNoHID|disbursalTo','Select Loan Account LOV First','clearTALoanLOV','bankAcountName','businessPartnerTypeDesc','businessPartnerName');closeLovCallonLov('loanNo');" value=" " styleClass="lovbutton"></html:button>
              										<html:hidden property="businessPartnerName" styleId="businessPartnerName"  value="" styleClass="text" />
              										<html:hidden property="bankAcountName" styleId="bankAcountName"  value="" styleClass="text" />
												</div>
											</logic:notEqual>
											<logic:equal name="disbursalTo" value='CS'>
												<div id="supplierDiv" style="display:none;">
													<html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="${disbursalData[0].supplierDesc}" tabindex="-1"/>
													<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
					              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
					              					<html:hidden  property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="${disbursalData[0].lbxBusinessPartnearHID}" />
													<html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(375,'disbursalMakerForm','lbxBusinessPartnearHID','loanNo|disbursalTo', 'lbxBusinessPartnearHID','lbxLoanNoHID|disbursalTo','Select Loan Account LOV First','','businessPartnerType','businessPartnerTypeDesc','businessPartnerName');closeLovCallonLov('loanNo');" value=" " styleClass="lovbutton"></html:button>
              										<html:hidden property="businessPartnerName" styleId="businessPartnerName"  value="" styleClass="text" />
												</div>
												
										</logic:equal>
												
											
											</td>
										</tr>
										
										<logic:equal name="disbursalDataList" property="repayMode" value="I">
										<tr>
										
											<td nowrap="nowrap">
												<bean:message key="lbl.taLoanNo" />
												
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="taLoanNo" styleId="taLoanNo" value="${disbursalData[0].taLoanNo}" readonly="true" maxlength="20" />
												
												<html:button property="loanButton" styleId="loanButton" value=" " onclick="setSupplierManufacturerIdInSession();openLOVCommon(452,'disbursalMakerForm','taLoanNo','businessPartnerTypeDesc','lbxTaLoanNoHID', 'lbxBusinessPartnearHID','Select Supplier/Manufacturer First','','taCustomerName');closeLovCallonLov('taLoanNo');" styleClass="lovbutton"> </html:button>
<!--												<html:button property="chargeButton" styleId="chargeButton" onclick="cleanField('C');openLOVCommon(198,'waiveOffMakerFormCSE','chargeDescription','valueDate|businessPartnerTypeDesc|loanAccountNo','lbxChargeCodeHID', 'initiationDateDbFormat|businessPartnerType|lbxLoanNoHID','Select WaiveOff Date First|Select BP Type LOV First|Select Loan Account LOV First','getWaiveOffList','chargeType','htxnAdviceId','htype');closeLovCallonLov('chargeType');" value=" " styleClass="lovbutton"></html:button>-->
												
												<input type="hidden" name="contextPath"	value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxTaLoanNoHID" styleId="lbxTaLoanNoHID" value="${disbursalData[0].lbxTaLoanNoHID}" />
												<html:hidden property="hid" styleId="hid" value="" />
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.taCustomerName" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="taCustomerName"
													styleId="taCustomerName" maxlength="50" readonly="true"
													value="${disbursalData[0].taCustomerName}" tabindex="-1" />
											</td>
											
										</tr>
										</logic:equal>

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.proposedShortPayAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="proposedShortPayAmount"
													styleId="proposedShortPayAmount" maxlength="18"
													value="${disbursalData[0].proposedShortPayAmount}"
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.adjustedShortPayAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="adjustedShortPayAmount"
													styleId="adjustedShortPayAmount" maxlength="18"
													value="${disbursalData[0].adjustedShortPayAmount}"
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											

										</tr>

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.disbursalAmount" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursalAmount"
													styleId="disbursalAmount"
													value="${disbursalData[0].disbursalAmount}"
													onkeypress="return numbersonly(event,id,18)"
													onblur="formatNumber(this.value,id);"
													onkeyup="checkNumber(this.value, event, 18,id);"
													onfocus="keyUpNumber(this.value, event, 18,id);" 
													style="text-align:right;" onchange="changeDisbursAmount()"/>
											</td>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.shortPayAmount" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="shortPayAmount"
													styleId="shortPayAmount"
													value="${disbursalData[0].shortPayAmount}"
													onkeypress="return numbersonly(event,id,18)"
													onblur="formatNumber(this.value,id);"
													onkeyup="checkNumber(this.value, event, 18,id);"
													onfocus="keyUpNumber(this.value, event, 18,id);" 
													style="text-align:right;"/>
											</td>
										</tr>

										<tr>
											<td nowrap="nowrap">
												
											</td>
											<td nowrap="nowrap">
											<html:hidden property="maxExpectedPayDate" styleId="maxExpectedPayDate" value="${disbursalData[0].maxExpectedPayDate}"/>
												<logic:equal name="disbursalDataList" property="repayMode" value="I">
												
													<html:hidden styleClass="text" property="expectedPaymentDate"
														styleId="expectedPmntDtIns"  value="${disbursalData[0].expectedPaymentDate}"
														onchange="checkDate('expectedPmntDtIns');"
														/>
												
												
												</logic:equal>
												
												<logic:notEqual name="disbursalDataList" property="repayMode" value="I">
												
													<html:hidden styleClass="text" property="expectedPaymentDate"
														styleId="expectedPmntDtNonIns"  value="${disbursalData[0].expectedPaymentDate}"
													
														/>
												
												
												</logic:notEqual>
												
											</td>
											<td>		
											</td>
											<td></td>
										</tr>
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.finalDisbursal" />
											</td>
											<td nowrap="nowrap">
												<logic:equal name="disbursalDataList" property="repayMode" value="I">
												<logic:equal value="F" name="fianlDisb">

													<input type="checkbox" name="finalDisbursal"
														id="finalDisbursal" checked="checked"
														onclick="showEffectiveDate();" />
												</logic:equal>
												<logic:notEqual name="fianlDisb" value="F">

													<input type="checkbox" name="finalDisbursal"
														id="finalDisbursal" onclick="showEffectiveDate();" />
												</logic:notEqual>
													<html:hidden property="repayMode" styleId="repayMode" 
														styleClass="text" value="${disbursalData[0].repayMode}"/>
												</logic:equal>
												
												<logic:equal name="disbursalDataList" property="repayMode" value="N">
													<input type="checkbox" name="finalDisbursal" disabled="disabled"
														id="finalDisbursal" onclick="showEffectiveDate();" />
													<html:hidden property="repayMode" styleId="repayMode" 
														styleClass="text" value="${disbursalData[0].repayMode}"/>
												</logic:equal>
											</td>
											<td>
												<bean:message key="lbl.repayEffDate" />
											</td>
											<logic:equal value="F" name="fianlDisb">
												<td>
												<logic:equal name="disbursalDataList" property="repayMode" value="I">
													<div id="repayEffId">
														<html:text styleClass="text" property="repayEffDate"
															styleId="repayEffDate" maxlength="10"
															value="${disbursalData[0].repayEffDate}"
															onchange="checkDate('repayEffDate');" />
													</div>
												</logic:equal>
													<div id="repayId" style="display: none;">
														<html:text styleClass="text" property="repayEffDate"
															styleId="repayEff" disabled="true" maxlength="10"
															value="" />
													</div>
													<logic:equal name="disbursalDataList" property="repayMode" value="N">
													<div id="repayIdBusDate" style="display: none;">
													<html:text styleClass="text" property="repayEffDate" 
														styleId="repayEffBusDate" value="${disbursalData[0].repayEffDate}"  maxlength="10" onchange="checkDate('repayEffBusDate');"
														/>
														</div>
													</logic:equal>
												</td>
											</logic:equal>
											<logic:notEqual name="fianlDisb" value="F">
												<td>
												<logic:equal name="disbursalDataList" property="repayMode" value="I">
													<div id="repayEffId" style="display: none;">
														<html:text styleClass="text" property="repayEffDate"
															styleId="repayEffDate" maxlength="10"
															value="<%=initiationDate %>" 
															onchange="checkDate('repayEffDate');" />
													</div>
													<div id="repayId">
														<html:text styleClass="text" property="repayEffDate"
															styleId="repayEff" disabled="true" maxlength="10"
															value="" />
													</div>
												</logic:equal>
													<logic:equal name="disbursalDataList" property="repayMode" value="N">
													<div id="repayIdBusDate">
													<html:text styleClass="text" property="repayEffDate" 
														styleId="repayEffBusDate" value="${disbursalData[0].repayEffDate}"  maxlength="10" onchange="checkDate('repayEffBusDate');"
														/>
														</div>
													
													</logic:equal>
												</td>
											</logic:notEqual>
										</tr>
										<tr>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.cycleDate" />
											</td>
											<td nowrap="nowrap">
											<logic:equal value="F" name="fianlDisb">
												<input type="hidden" name="hiddenDueDate" id="hiddenDueDate" value="${disbursalData[0].cycleDateValue}"/>
												<div id="cycleDateI" style="display: none;">
	            									<html:select property="cycleDate" styleId="cycleDate" styleClass="text" 
	            										disabled="true"  onchange="nullNextDue(this.value);">
														<html:option value="">--<bean:message key="lbl.select" />--</html:option>
			 										</html:select>
												</div>
												<div id="cycleDateFinal">
												<logic:present name="cycleDate">
											 
	            								<html:select property="cycleDate" styleId="cycleDate" value="${disbursalData[0].cycleDateValue}" styleClass="text"  onchange="nullNextDue(this.value);">
	            									<logic:notEmpty name="cycleDate">
	            									     <html:option value="">--<bean:message key="lbl.select" />--</html:option>
														<html:optionsCollection name="cycleDate" label="id" value="name" />
			 										</logic:notEmpty>
			 										</html:select>
												</logic:present>
												</div>
											</logic:equal>
											
											<logic:notEqual value="F" name="fianlDisb">
											
											<input type="hidden" name="hiddenDueDate" id="hiddenDueDate" value="${disbursalData[0].cycleDateValue}" />
												<div id="cycleDateI" >
												
	            									<html:select property="cycleDate" styleId="cycleDate" styleClass="text"
	            										disabled="true"  onchange="nullNextDue(this.value);">
	            										
														<html:option value="">--<bean:message key="lbl.select" />--</html:option>
			 										</html:select>
												</div>
												<div id="cycleDateFinal" style="display: none;">
													
												<logic:present name="cycleDate">
	            									<html:select property="cycleDate" styleId="cycleDate" value="${disbursalData[0].cycleDateValue}" styleClass="text" onchange="nullNextDue(this.value);" >
														<logic:notEmpty name="cycleDate">
														<html:option value="">--<bean:message key="lbl.select" />--</html:option>
														<html:optionsCollection name="cycleDate" label="id" value="name" />
														</logic:notEmpty>
			 										</html:select>
												</logic:present>
												</div>
											</logic:notEqual>
											</td>
											<td>
												 <logic:equal value="F" name="fianlDisb">
													<input type="hidden" id="val" value="Y" />
												</logic:equal>
												<logic:notEqual value="F" name="fianlDisb">
													<input type="hidden" id="val" value="N" />
												</logic:notEqual>
											     
											     <div id="no1">
											     	<bean:message key="lbl.nextDueDate" />
											     </div>
											     <div id="yes1" style="display: none">
											     	<bean:message key="lbl.nextDueDate" /><font color="red">*</font>
											     </div>
											</td>
										    <td >
										    <logic:present name="fianlDisb">
										     
										    <logic:equal value="F" name="fianlDisb">
										    <div id="no2" style="display: none">
										    		<html:text property="nextDueDateNo" value="" styleClass="text" readonly="true" disabled="disabled" />
										    	</div>
										    	<div id="yes2" >
										    		<html:text property="nextDueDate" styleClass="text"  value="${disbursalData[0].nextDueDate}"
											          styleId="nextDueDate" onchange="checkDueDate(value);checkRepayEffectiveDate(value);" />
											    </div>
										    	</logic:equal>
										    	 <logic:notEqual value="F" name="fianlDisb">
										    	<div id="no2">
										    		<html:text property="nextDueDateNo" value="" styleClass="text" readonly="true" disabled="disabled" />
										    	</div>
										    	<div id="yes2" style="display: none">
										    		<html:text property="nextDueDate" styleClass="text"  value="${disbursalData[0].nextDueDate}"
											          styleId="nextDueDate" onchange="checkDueDate(value);checkRepayEffectiveDate(value);" />
											    </div>	
											    </logic:notEqual>
											    </logic:present>									    	
										    </td>
											
											</tr>
											<tr>
											<td nowrap="nowrap"><bean:message key="lbl.currentMonthEMI"/>
											<input type="hidden" id="EMIFlag" value="T"/>
											</td>
											<td nowrap="nowrap">
												<html:text property="currentMonthEMI" styleClass="text"  value="${disbursalData[0].currentMonthEMI}" styleId="currentMonthEMI" readonly="true" style="text-align:right;"/>
											</td>
											<%-- <td nowrap="nowrap"><bean:message key="lbl.nextMonthEMI"/></td> --%>										
											<td nowrap="nowrap">
												<html:hidden property="preEMINextMonth" styleClass="text"  value="${disbursalData[0].preEMINextMonth}"  styleId="preEMINextMonth"  style="text-align:right;"/>
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.makerRemark" />
<!--												<font color="red">*</font>--><!-- Commented by Nishant -->
											</td>
											<td nowrap="nowrap">
												<html:textarea styleClass="text"
													property="disbursalDescription"
													styleId="disbursalDescription" styleClass="text"
													value="${disbursalData[0].disbursalDescription}" />
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.authorRemarks" />
											</td>
											<td nowrap="nowrap">
												<html:textarea styleClass="text"
													property="authorRemarks"
													styleId="authorRemarks" 
													disabled="true"
													value="${disbursalData[0].authorRemarks}"
													 />
											</td>
										</tr>
									</logic:iterate>
									</table>
									<logic:equal name="disbursalDataList" property="repayMode"  value="I">
									<button type="button" name="EMI" id="EMI"
										class="topformbutton4" title="Alt+E" accesskey="E"
										onclick="return calculateEMI()"><bean:message key="button.calculatePreEMI" /></button>
									</logic:equal>
									<button type="button" name="save" id="save" 
										class="topformbutton2" title="Alt+V" accesskey="V"
										onclick="return updateDisbursalData('P');"><bean:message key="button.save" /></button>
									<button type="button" name="saveFwd" id="saveFwd"
										 class="topformbutton2" title="Alt+F" accesskey="F"
										onclick="return updateDisbursalData('F','<bean:message key="msg.confirmationForwardMsg" />');"><bean:message key="button.forward" /></button>


									<logic:equal value="F" name="fianlDisb">
									<button type="button" name="generateRepaymentSch"
										id="generateRepaymentSch"
										 title="Alt+R" accesskey="R"
										class="topformbutton4" onclick="return callRepay();" ><bean:message key="button.repayschedule" /></button>
									</logic:equal>
									<logic:notEqual name="fianlDisb" value="F">
									<button type="button" name="generateRepaymentSch"
										id="generateRepaymentSch"
										 title="Alt+R" accesskey="R"
										class="topformbutton4" disabled="disabled" ><bean:message key="button.repayschedule" /></button>	
									</logic:notEqual>
									<button type="button" name="oldDisbursal" id="oldDisbursal" 
										 class="topformbutton4" title="Alt+O" accesskey="O"
										onclick="return openDisbursalSchedule();"><bean:message key="button.olddisbursal" /></button>
									<button type="button" 
										class="topformbutton3" title="Alt+I" accesskey="I"
										onclick="return viewReceivableDisbursal('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.viewRecievable" /></button>
										
								<button type="button" name="specialCondButton" id="specialCondButton"
										class="topformbutton4" title="Alt+S" accesskey="S"
										onclick="return viewSpecialCondButton('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.specialCondition" /></button>
								
								
										<input type="hidden" name="loandisbursalid" id="loandisbursalid" value="${disbursalData[0].loanDisbursalId}" />
								<button type="button" name="Delete"  id="Delete" class="topformbutton2" 
								onclick="return deletedisbursalDetails();" title="Alt+D" accesskey="D" tabindex="44"><bean:message key="button.delete" /></button>
								
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
			</logic:present>

			<!-- --------------------------------------- Disbursal Maker Ends --------------------------------- -->

			<!-- --------------------------------------- Disbursal Author Starts --------------------------------- -->

			<logic:present name="disbursalDataAuthor">
			
				<html:form action="/disbursalMaker" method="post"
					styleId="disbursalMakerForm">
					<input type="hidden" name="maxDisbursalDate" id="maxDisbursalDate" value="${maxDisbursalDate}" />
					<fieldset>
						<legend>
							<bean:message key="lbl.disbursalDetails" />
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="hidden" name="businessDate" id="businessDate"
										value="<%=initiationDate%>" />
									<html:hidden property="disbursalNo" styleId="disbursalNo"
										value="${disbursalDataAuthor[0].disbursalNo}" />
										
									

									<table width="100%" border="0" cellspacing="1" cellpadding="1">
									<logic:iterate id="disbursalDataAuthorList" name="disbursalDataAuthor">
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.loanNo" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="loanNo"
													styleId="loanNo" value="${disbursalDataAuthor[0].loanNo}"
													disabled="true" maxlength="20" tabindex="-1"/>
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="${disbursalDataAuthor[0].lbxLoanNoHID}" />
												<html:hidden property="hid" styleId="hid" value="" />
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.customerName" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="customerName"
													styleId="customerName" tabindex="-1"
													value="${disbursalDataAuthor[0].customerName}"
													maxlength="50" disabled="true" />
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.loanApprovalDate" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="loanApprovalDateAuthor" styleId="loanApprovalDateAuthor" tabindex="-1" value="${disbursalDataAuthor[0].loanApprovalDate}" maxlength="10" disabled="true" />
											</td>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.disbursalDate" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursalDate"
													maxlength="10" tabindex="-1"
													value="${disbursalDataAuthor[0].disbursalDate}"
													disabled="true" />
											</td>
										
										</tr>
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.product" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="product" tabindex="-1"
													styleId="product" value="${disbursalDataAuthor[0].product}"
													disabled="true" maxlength="50" />
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.scheme" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="scheme"
													styleId="scheme" maxlength="50" tabindex="-1"
													value="${disbursalDataAuthor[0].scheme}" disabled="true" />
											</td>
											
										</tr>

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.loanAmt" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="loanAmt"
													styleId="loanAmt" value="${disbursalDataAuthor[0].loanAmt}"
													disabled="true" maxlength="18" tabindex="-1"
													style="text-align:right;"/>
											</td>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.disbursedAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursedAmount"
													styleId="disbursedAmount" maxlength="18"
													value="${disbursalDataAuthor[0].disbursedAmount}"
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>

										</tr>
										
										<tr>
											<td nowrap="nowrap">
													<bean:message key="lbl.disbursalTo" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
										
									              <html:select property="disbursalTo" styleId="disbursalTo" styleClass="text" onchange="return activeSupplier(this.value);" value="${sessionScope.disbursalTo}" disabled="true">
									              	<html:option value="CS">Customer</html:option>
									              	<html:option value="SU">Supplier</html:option>
									              	<html:option value="MF">Manufacturer</html:option>
									            </html:select>
											</td>
											<td nowrap="nowrap">
												<logic:equal name="disbursalTo" value='CS'>
												<div id="supplierLableDiv" style="display:none">
													<bean:message key="lbl.supplier"/>
											
													</div>
												<div id="manufactLableDiv" style="display:none">
													<bean:message key="lbl.manufact"/>
											
													</div>
											</logic:equal>
											<logic:notEqual name="disbursalTo" value='CS'>
												<logic:equal name="disbursalTo"  value='SU'>
													<div id="supplierLableDiv">
													<bean:message key="lbl.supplier"/>
											
													</div>
													<div id="manufactLableDiv" style="display:none">
														<bean:message key="lbl.manufact"/>
													</div>
												</logic:equal>
												<logic:equal name="disbursalTo" value='MF'>
													<div id="manufactLableDiv">
														<bean:message key="lbl.manufact"/>
													</div>
													<div id="supplierLableDiv" style="display:none">
													<bean:message key="lbl.supplier"/>
											
													</div>
												</logic:equal>
											</logic:notEqual>
											</td>
											<td nowrap="nowrap">
											<logic:notEqual name="disbursalTo"  value='CS'>
												<div id="supplierDiv" >
													<html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="${disbursalDataAuthor[0].supplierDesc}" tabindex="-1" disabled="true"/>
													<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
					              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
					              					<html:hidden  property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="${disbursalDataAuthor[0].lbxBusinessPartnearHID}" />
<!--					              					<html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(375,'disbursalMakerForm','lbxBusinessPartnearHID','loanNo', 'lbxBusinessPartnearHID','lbxLoanNoHID','Select Loan Account LOV First','','businessPartnerType','businessPartnerTypeDesc','businessPartnerName');closeLovCallonLov('loanNo');" value=" " styleClass="lovbutton"></html:button>-->
              										<html:hidden property="businessPartnerName" styleId="businessPartnerName"  value="" styleClass="text" />
              										
												</div>
											</logic:notEqual>
											<logic:equal name="disbursalTo" value='CS'>
												<div id="supplierDiv" style="display:none;">
													<html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="" tabindex="-1"/>
													<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
					              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
					              					<html:hidden  property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="" />
<!--					              					<html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(375,'disbursalMakerForm','lbxBusinessPartnearHID','loanNo', 'lbxBusinessPartnearHID','lbxLoanNoHID','Select Loan Account LOV First','','businessPartnerType','businessPartnerTypeDesc','businessPartnerName');closeLovCallonLov('loanNo');" value=" " styleClass="lovbutton"></html:button>-->
              										<html:hidden property="businessPartnerName" styleId="businessPartnerName"  value="" styleClass="text" />
												</div>
										</logic:equal>
												
											
											</td>
										</tr>
										
										<logic:equal name="disbursalDataAuthorList" property="repayMode" value="I">
										<tr>
										
											<td nowrap="nowrap">
												<bean:message key="lbl.taLoanNo" />
												
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="taLoanNo" styleId="taLoanNo" value="${disbursalDataAuthor[0].taLoanNo}" readonly="true" maxlength="20" />
												<input type="hidden" name="contextPath"	value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxTaLoanNoHID" styleId="lbxTaLoanNoHID" value="${disbursalDataAuthor[0].lbxTaLoanNoHID}" />
												
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.taCustomerName" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="taCustomerName"
													styleId="taCustomerName" maxlength="50" readonly="true"
													value="${disbursalDataAuthor[0].taCustomerName}" tabindex="-1" />
											</td>
											
										</tr>
										</logic:equal>
										
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.proposedShortPayAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="proposedShortPayAmount"
													styleId="proposedShortPayAmount" maxlength="18"
													value="${disbursalDataAuthor[0].proposedShortPayAmount}"
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.adjustedShortPayAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="adjustedShortPayAmount"
													styleId="adjustedShortPayAmount" maxlength="18"
													value="${disbursalDataAuthor[0].adjustedShortPayAmount}"
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											
											
											
										</tr>

										<tr>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.disbursalAmount" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursalAmount"
													styleId="disbursalAmount" maxlength="18"
													value="${disbursalDataAuthor[0].disbursalAmount}"
													disabled="true" tabindex="-1" style="text-align:right;" onchange="changeDisbursAmount()"/>
											</td>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.shortPayAmount" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="shortPayAmount"
													styleId="shortPayAmount" maxlength="18"
													value="${disbursalDataAuthor[0].shortPayAmount}"
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											
										</tr>
										<tr>
											<td nowrap="nowrap">
											
											</td>
											<td nowrap="nowrap">
											<html:hidden property="maxExpectedPayDate" styleId="maxExpectedPayDate" value="${disbursalDataAuthor[0].maxExpectedPayDate}"/>
												<logic:present name="disbursalDataAuthorList">
													<html:hidden styleClass="text" property="expectedPaymentDate"
														styleId="expectedPaymentDate1" value="${disbursalDataAuthor[0].expectedPaymentDate}"
														disabled="true" 
														/>
												</logic:present>
												
												<logic:notPresent name="disbursalDataAuthorList">
													<html:hidden styleClass="text" property="expectedPaymentDate"
														styleId="expectedPaymentDate"  value="${disbursalDataAuthor[0].expectedPaymentDate}"
														disabled="true" 
														/>
												</logic:notPresent>
												
											</td>
											<td>				
											</td>
											<td></td>
										</tr>
										
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.finalDisbursal" />
											</td>
											<td nowrap="nowrap">
												<logic:equal name="disbursalDataAuthorList" property="repayMode" value="I">
												<logic:equal name="fianlDisb" value="F">
													<input type="checkbox" name="finalDisbursal" tabindex="-1"
														id="finalDisbursal" checked="checked" disabled="disabled" />
												</logic:equal>
												<logic:notEqual name="fianlDisb" value="F">
													<input type="checkbox" name="finalDisbursal" tabindex="-1"
														id="finalDisbursal" disabled="disabled" />
												</logic:notEqual>
													<html:hidden property="repayMode" styleId="repayMode" 
														styleClass="text" value="${disbursalDataAuthor[0].repayMode}"/>
												</logic:equal>
												
												<logic:equal name="disbursalDataAuthorList" property="repayMode" value="N">
													<input type="checkbox" name="finalDisbursal" disabled="disabled"
														id="finalDisbursal" onclick="showEffectiveDate();" />
													<html:hidden property="repayMode" styleId="repayMode" 
														styleClass="text" value="${disbursalDataAuthor[0].repayMode}"/>
												</logic:equal>
											</td>
											<td>
												<bean:message key="lbl.repayEffDate" />
											</td>
											<logic:equal value="F" name="fianlDisb">
												<td>
													<div id="repayEffId">
														<html:text styleClass="text" property="repayEffDate"
															maxlength="10" tabindex="-1"
															value="${disbursalDataAuthor[0].repayEffDate}"
															disabled="true" />
													</div>
													<div id="repayId" style="display: none;">
														<html:text styleClass="text" property="repayEffDate"
															disabled="true" maxlength="10" tabindex="-1"
															value=""
															disabled="true" />
													</div>
												</td>
											</logic:equal>
											<logic:notEqual name="fianlDisb" value="F">
												<td>
													<div id="repayEffId" style="display: none;">
														<html:text styleClass="text" property="repayEffDate"
															maxlength="10" tabindex="-1"
															value="${disbursalDataAuthor[0].repayEffDate}"
															disabled="true" />
													</div>
												<logic:equal name="disbursalDataAuthorList" property="repayMode" value="N">
														<div id="repayId">
														<html:text styleClass="text" property="repayEffDate"
															disabled="true" maxlength="10" tabindex="-1"
															value="${disbursalDataAuthor[0].repayEffDate}"
															disabled="true" />
													</div>
													</logic:equal>
													<logic:notEqual name="disbursalDataAuthorList" property="repayMode" value="N">
														<div id="repayId">
														<html:text styleClass="text" property="repayEffDate"
															disabled="true" maxlength="10" tabindex="-1"
															value=""
															disabled="true" />
													</div>
													</logic:notEqual>
					
												</td>
											</logic:notEqual>
										</tr>
										<tr>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.cycleDate" />
											</td>
											<td nowrap="nowrap">
												<logic:equal value="F" name="fianlDisb">
												<input type="hidden" name="hiddenDueDate" id="hiddenDueDate" value="${disbursalDataAuthor[0].cycleDateValue}"/>
												<div id="cycleDateI" style="display: none;">
	            									<html:select property="cycleDate" styleId="cycleDate" styleClass="text" 
	            										disabled="true"  onchange="nullNextDue(this.value);">
														<html:option value="">--<bean:message key="lbl.select" />--</html:option>
			 										</html:select>
												</div>
												<div id="cycleDateFinal">
												<logic:present name="cycleDate">
	            									<html:select property="cycleDate" styleId="cycleDate" value="${disbursalDataAuthor[0].cycleDateValue}" styleClass="text" 
	            										disabled="true"  onchange="nullNextDue(this.value);">
	            										<logic:notEmpty name="cycleDate">
	            										<html:option value="">--<bean:message key="lbl.select" />--</html:option>
														<html:optionsCollection name="cycleDate" label="id" value="name" />
														</logic:notEmpty>
			 										</html:select>
												</logic:present>
												</div>
											</logic:equal>
											
											<logic:notEqual value="F" name="fianlDisb">
												<div id="cycleDateI" >
	            									<html:select property="cycleDate" styleId="cycleDate" styleClass="text"
	            										disabled="true" onchange="nullNextDue(this.value);">
														<html:option value="">--<bean:message key="lbl.select" />--</html:option>
			 										</html:select>
												</div>
												<div id="cycleDateFinal" style="display: none;">
												<logic:present name="cycleDate">
	            									<html:select property="cycleDate" styleId="cycleDate" value="${disbursalDataAuthor[0].cycleDateValue}" styleClass="text"
	            										disabled="true" onchange="nullNextDue(this.value);">
	            											<logic:notEmpty name="cycleDate">
	            											<html:option value="">--<bean:message key="lbl.select" />--</html:option>
														<html:optionsCollection name="cycleDate" label="id" value="name" />
														</logic:notEmpty>
			 										</html:select>
												</logic:present>
												</div>
											</logic:notEqual>
											</td>
											<td><bean:message key="lbl.nextDueDate" /><font color="red">*</font></td>
										    <td>
										         <html:text property="nextDueDateAuthor" styleClass="text"  value="${disbursalDataAuthor[0].nextDueDate}" styleId="nextDueDateAuthor" readonly="true" />
										    </td>
										</tr>
										<tr>
											<td nowrap="nowrap"><bean:message key="lbl.currentMonthEMI"/>
											<input type="hidden" id="EMIFlag" value="T"/>
											</td>
											<td nowrap="nowrap">
												<html:text property="currentMonthEMI" styleClass="text"  value="${disbursalDataAuthor[0].currentMonthEMI}" styleId="currentMonthEMI" readonly="true" style="text-align:right;"/>
											</td>
											<%-- <td nowrap="nowrap"><bean:message key="lbl.nextMonthEMI"/></td> --%>										
											<td nowrap="nowrap">
												<html:hidden property="preEMINextMonth" styleClass="text"  value="${disbursalDataAuthor[0].preEMINextMonth}"  styleId="preEMINextMonth"  style="text-align:right;"/>
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.makerRemark" />
<!--												<font color="red">*</font>--><!-- Commented by Nishant -->
											</td>
											<td nowrap="nowrap">
												<html:textarea styleClass="text"
													property="disbursalDescription"
													styleId="disbursalDescription" styleClass="text"
													value="${disbursalDataAuthor[0].disbursalDescription}"
													disabled="true" tabindex="-1"/>
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.authorRemarks" />
											</td>
											<td nowrap="nowrap"><html:textarea styleClass="text"
													property="authorRemarks"
													styleId="authorRemarks" 
													disabled="true"
													value="${disbursalDataAuthor[0].authorRemarks}"
													 />
											</td>
										</tr>
										</logic:iterate>
									</table>
									<logic:equal value="F" name="fianlDisb">
									<button type="button" name="generateRepaymentSch"
										id="generateRepaymentSch"
										 title="Alt+S" accesskey="S"
										class="topformbutton4" onclick="return viewRepaymentScheduleDisbursal();" ><bean:message key="button.repayschedule" /></button>
									</logic:equal>
									<logic:notEqual name="fianlDisb" value="F">
									<button type="button" name="generateRepaymentSch"
										id="generateRepaymentSch"
										 title="Alt+S" accesskey="S"
										class="topformbutton4" disabled="disabled" ><bean:message key="button.repayschedule" /></button>	
									</logic:notEqual>
									<button type="button" name="oldDisbursal" id="oldDisbursal" 
										 class="topformbutton4" title="Alt+O" accesskey="O"
										onclick="return openDisbursalSchedule();"><bean:message key="button.olddisbursal" /></button>
									<button type="button" 
										class="topformbutton3" title="Alt+R" accesskey="R"
										onclick="return viewReceivableDisbursal('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.viewRecievable" /></button>
										
									<button type="button" name="specialCondButton" id="specialCondButton"
										class="topformbutton4" title="Alt+S" accesskey="S"
										onclick="return viewSpecialCondButton('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.specialCondition" /></button>
								
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
			</logic:present>

			<!-- --------------------------------------- Disbursal Author Ends --------------------------------- -->

		</div>
	</div>
	
</body>
</html:html>

<logic:present name="disbStatus">
	<script type="text/javascript">
if('<%=request.getAttribute("disbStatus").toString()%>'=='DisbAmt')
{
	alert('<bean:message key="msg.DisbursalAmtBig"/>');
}
else if('<%=request.getAttribute("disbStatus").toString()%>'=='ShrtPayAmt')
{
	alert('<bean:message key="msg.ShortPayAmtBig"/>');
	document.getElementById("save").removeAttribute("disabled","true");
}
else if('<%=request.getAttribute("disbStatus").toString()%>'=='LnBalPrinMoreThanLnAmt')
{
	alert('<bean:message key="msg.LnBalPrinMoreThanLnAmt"/>');
	document.getElementById("save").removeAttribute("disabled","true");
}

else if('<%=request.getAttribute("disbStatus").toString()%>'=='preDisbDate')
{
	alert('<bean:message key="msg.PreviousDisbDateAfter"/>');
}
else if('<%=request.getAttribute("disbStatus").toString()%>'=='preProposedDisbDate')
{
	alert('<bean:message key="msg.PreviousProposedDisbDate"/>');
}
else if('<%=request.getAttribute("disbStatus").toString()%>'=='disbursalAmount')
{
	alert('<bean:message key="msg.disbursalAmountSch"/>');
}
else if('<%=request.getAttribute("disbStatus").toString()%>'=='disbursalAmtForNonInst')
{
	alert('<bean:message key="msg.disbursalAmtForNonInst"/>');
}
else if('<%=request.getAttribute("disbStatus").toString()%>'=='groupExposure')
{
	alert('<bean:message key="msg.groupExposure"/>');
}
else if('<%=request.getAttribute("disbStatus").toString()%>'=='customerExposure')
{
	alert('<bean:message key="msg.customerExposure"/>');
}
else if('<%=request.getAttribute("disbStatus")%>'=='Locked')
{
	alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
}
else if('<%=request.getAttribute("disbStatus")%>'=='PRSDocs' && '<%=request.getAttribute("chkPRSPRDDocsFlag")%>'=='Y')
{
	alert('<bean:message key="msg.docsPRS"/>');
}
else if('<%=request.getAttribute("disbStatus")%>'=='PRDDocs'&& '<%=request.getAttribute("chkPRSPRDDocsFlag")%>'=='Y')
{
	alert('<bean:message key="msg.docsPRD"/>');
}
else if('<%=request.getAttribute("disbStatus")%>'=='PRSDocs' && '<%=request.getAttribute("chkPRSPRDDocsFlag")%>'=='N')
{
	var flag=confirm('<bean:message key="msg.docsPRSPRDNotReceived"/>');
	var docs="N";
	var recStatus="F";
	if(flag){
	document.getElementById("disbursalMakerForm").action="disbursalMaker.do?method=updateDisbursalData&docs="+docs+"&recStatus="+recStatus;
    document.getElementById("disbursalMakerForm").submit();
	}
}
else if('<%=request.getAttribute("disbStatus")%>'=='PRDDocs'&& '<%=request.getAttribute("chkPRSPRDDocsFlag")%>'=='N')
{
	var flag=confirm('<bean:message key="msg.docsPRSPRDNotReceived"/>');
	var docs="N";
	var recStatus="F";
	if(flag){
	document.getElementById("disbursalMakerForm").action="disbursalMaker.do?method=updateDisbursalData&docs="+docs+"&recStatus="+recStatus;
   	document.getElementById("disbursalMakerForm").submit();
	}
}
	
</script>
</logic:present>


<logic:notPresent name="specialConditionStatus">
	<logic:present name="message">
		<script type="text/javascript">
		if('<%=request.getAttribute("message").toString()%>'=='S')
		{
			alert('<bean:message key="msg.DataSaved" />');
		}
		else if('<%=request.getAttribute("message").toString()%>'=='E')
		{
			alert('<bean:message key="msg.DataNotSaved" />');
		}
		else if('<%=request.getAttribute("message").toString()%>'=='D')
		{
			alert('<bean:message key="lbl.dataDeleted" />');
			location.href="<%=request.getContextPath()%>/disbursalSearch.do?method=searchDisbursalMakerLnik";
			
		}
		
		</script>
	</logic:present>
</logic:notPresent>

<logic:present name="specialConditionStatus">
<script type="text/javascript">
	alert('<bean:message key="lbl.updateSpecialCondition" />');
</script>
</logic:present>


