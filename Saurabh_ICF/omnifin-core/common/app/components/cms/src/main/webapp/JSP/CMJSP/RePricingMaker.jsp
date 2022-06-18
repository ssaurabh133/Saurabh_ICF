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
	
	 <logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
	<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
	<script type="text/javascript" src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/cmLoanInitjs.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
	<script type="text/javascript" src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script> 	
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency-1.3.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency.all.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
	
	
<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('rePricingMakerForm').partPaymentDateButton.focus();
     }
     else if(document.getElementById('modifyRecord').value =='N')
     {
    	document.getElementById('rePricingMakerForm').loanButton.focus();
     }
    
     return true;
}

</script>
<style type="text/css">
	.readonly{
			width:150px !important;
	}
</style>

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
<body onclick="parent_disable();" onunload="closeAllLovCallUnloadBody();" onload="enableAnchor();checkChanges('rePricingMakerForm');fntab();">
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" name="instmntSaveFlag" id="instmntSaveFlag" value="" />
	<input type="hidden" name="maxInstallmentDate" id="maxInstallmentDate" value="${rePricingData[0].maxInstallmentDate}" />
	 <input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<logic:present name="image">
    	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
        </logic:present>
    	<logic:notPresent name="image">
    	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    	</logic:notPresent>
    	
	<div id=centercolumn>
		<div id=revisedcontainer> 
		
	
			<!-- --------------------------------------- Repricing New --------------------------------- -->

			<logic:present name="rePricingNew">
				<html:form action="/rePricingMaker" method="post"
					styleId="rePricingMakerForm">
					<html:javascript formName="RePricingMakerDynaValidatorForm"/>
					<fieldset>
						<legend>
							<bean:message key="lbl.rePricingDetails" />
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="hidden" name="businessDate" id="businessDate"
										value="<%=initiationDate%>" />
									<input type="hidden" id="modifyRecord" name="modifyRecord" value="N"/>

									<table width="100%" border="0" cellspacing="1" cellpadding="1">

										<tr>
											<td width="25%">
												<bean:message key="lbl.loanNo" />
												<font color="red">*</font>
											</td>
											<td width="25%">
												<html:text styleClass="text" property="loanNo"
													styleId="loanNo" value="" readonly="true" maxlength="20" />
												<html:button property="loanButton" styleId="loanButton" value=" "
													onclick="openLOVCommon(224,'rePricingMakerForm','loanNo','','', '','','generateRepricingValues','customerName');closeLovCallonLov1();" 
													styleClass="lovbutton"> </html:button>
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
													value="" tabindex="-1" />
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.product" />
											</td>
											<td>
												<html:text styleClass="text" property="product"
													styleId="product" value="" maxlength="50" 
													disabled="true" tabindex="-1"/>
											</td>
											<td>
												<bean:message key="lbl.scheme" />
											</td>
											<td>
												<html:text styleClass="text" property="scheme"
													styleId="scheme" maxlength="50" value="" 
													disabled="true" tabindex="-1"/>
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.disbursedAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursedAmount"
													styleId="disbursedAmount" maxlength="18" value=""
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											<td>
												<bean:message key="lbl.outstandingLoanAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="outstandingLoanAmount" style="text-align:right;"
													styleId="outstandingLoanAmount" maxlength="18" value=""
													readonly="true" tabindex="-1" />
											</td>
										</tr>

										<tr>
											<td>
												<bean:message key="lbl.rePricingSinceDsibursal" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="rePricingSinceDsibursal"
													styleId="rePricingSinceDsibursal" value="" maxlength="5"
													disabled="true" tabindex="-1"/>
											</td>
											<td>
												<bean:message key="lbl.lastRePricingDate" />
											</td>
											<td >
												<html:text styleClass="text" property="lastRePricingDate" 
													styleId="lastRePricingDate" maxlength="10" value=""
												 tabindex="-1"/>
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.rePricingEffectiveRate"/>
											</td>
											<td>
												<html:text styleClass="text" property="effectiveRate" 
													styleId="effectiveRate" value=""
													readonly="true" tabindex="-1" 
													style="text-align:right;"/>
											</td>
											
											<td>
												<bean:message key="lbl.flatRate"/>
											</td>
											<td>
												<input type="text" class="text" name="flatRate" id="flatRate" readonly="readonly" tabindex="-1"
												style="text-align:right;" />
											</td>
											
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.emi"/>
											</td>
											<td>
												<html:text styleClass="text" property="emi" 
													styleId="emi" value=""
													readonly="true" tabindex="-1"
													style="text-align:right;" />
											</td>
											<td>
												<bean:message key="lbl.rePricingFromInstallment" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text styleClass="text" property="rePricingFromInstallment"
													styleId="rePricingFromInstallment" value="" maxlength="3"
													readonly="true" tabindex="-1" style="text-align:right;"/>
												<html:button property="partPaymentDateButton" styleId="partPaymentDateButton" value=" "
													onclick="openLOVCommon(219,'rePricingMakerForm','reschDate','loanNo','lbxInstlNo','lbxLoanNoHID','Please Select Loan','','rePricingFromInstallment');closeLovCallonLov('loanNo');" 
													styleClass="lovbutton"> 
												</html:button>
												<html:hidden property="lbxInstlNo" styleId="lbxInstlNo" value="" />
											</td>
											
										</tr>
										<tr>
										
										<td>
												<bean:message key="lbl.instlDate"/>
											</td>
											<td>
												<html:text property="reschDate" styleId="reschDate"
													styleClass="text" readonly="true" tabindex="-1"
													value=""/>
											</td>
										
										
											<td>
												<bean:message key="lbl.rePricingCondition" />
												<font color="red">*</font>
											</td>
											<td>
												<html:select property="rePricingCondition" styleId="rePricingCondition"
													styleClass="text" value="" onchange="changeRepricingCondition()">
													<html:option value="E">Change Plan</html:option>
													<html:option value="T">Keep Same Plan</html:option>
												</html:select>
											</td>
											
										</tr>
										<!--  Added By Rahul papneja |09112017 -->
										
										
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.repricingDate" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="repricingDate" tabindex="-1"
													styleId="repricingDate" maxlength="10" readonly="true"
													value="<%=initiationDate%>" />
									
										
										</tr>
										
										
										
										<!-- Ends Here -->
										
										
										<tr>
											<td>
												<bean:message key="lbl.reqRefNo" />
											</td>
											<td>
												<html:text styleClass="text" property="reqRefNo"
													styleId="reqRefNo" value="" maxlength="10"
													/>
											</td>
											<td>
												<bean:message key="lbl.reschCharges" />
												<font color="red">*</font>
											</td>
											<td><html:text styleClass="text" property="reschCharges" styleId="reschCharges" value="" maxlength="18" readonly="false" tabindex="-1" 
												onfocus="keyUpNumber(this.value, event, 18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" 
												onkeyup="checkNumber(this.value, event, 18,id);" style="text-align:right;" />
											</td>
											
										</tr>
										<tr>
										<td>
												<bean:message key="lbl.rePricingReason"/>
												<font color="red">*</font>
											</td>
											<td>
												<textarea name="rePricingReason" id="rePricingReason"
													maxlength="1000" value="" class="text"></textarea>
											</td>
											<td>
												<bean:message key="lbl.authorRemarks"/>
											</td>
											<td>
												<textarea name="authorRemarks" id="authorRemarks"
													disabled="disabled" tabindex="-1" class="text"></textarea>
											</td>
											</tr>
											<tr>
										<td>
											<button type="button" name="calcInterest" id="calcInterest" 
												onclick="return generateRepricingReschCharges();" title="Alt+C" accesskey="C" 
												class="topformbutton3"><bean:message key="button.calcharge" /></button>
										</td>
										<td></td>

										</tr>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend>
							<bean:message key="lbl.rePricingParameters"/>
						</legend>
						<table width="100%" cellspacing="0" cellpadding="0"  border="0">
				  			<tr>
				     			<td class="gridtd">
				     			<div id="rePricingParams">
				 	  				<table width="100%"  border="0" cellspacing="1" cellpadding="1">
				        				<tr class="white2">
				        					<td width="6%">
				        						<b><bean:message key="lbl.rePricingStatus" /></b>
				        					</td>
				        					<td width="10%">
												<b><bean:message key="lbl.interestRateType"/></b>
											</td>
				        					<td width="10%">
												<b><bean:message key="lbl.interestRateMethod"/></b>
											</td>
										
											<td width="8%">
												<b><bean:message key="lbl.baseRateType"/></b>
											</td>
											<td width="8%">
												<b><bean:message key="lbl.baseRate"/></b>
											</td>
											<td width="8%">
												<b><bean:message key="lbl.markUp"/></b>
											</td>
											<td width="8%">
												<b><bean:message key="lbl.finalRate"/></b>
											</td>
											<td width="8%">
												<b><bean:message key="lbl.installmentFreq"/></b>
											</td>
											<td width="8%">
												<b><bean:message key="lbl.installmentType"/></b>
											</td>
											<td width="8%">
												<b><bean:message key="lbl.tenure"/></b>
											</td>
											<td width="10%">
												<b><bean:message key="lbl.noOfInstallment"/></b>
											</td>
											<td width="8%">
												<b><bean:message key="lbl.maturityDate"/></b>
											</td>
										</tr>
									</table>
									</div>
								</td>
							</tr>
						</table>
						<button type="button" name="save" id="save" 
							class="topformbutton2" title="Alt+V" accesskey="V"
							onclick="return saveRepricing();"><bean:message key="button.save" /></button>
						<button type="button" name="saveFwd" id="saveFwd" title="Alt+F" accesskey="F"
							 class="topformbutton2"
							onclick="return updateDisbursalBeforeSave();"><bean:message key="button.forward" /></button>
						<button type="button" name="generateInstallmentPlan"
							id="generateRepaymentSch"
							 title="Alt+O" accesskey="O"
							class="topformbutton4" 
							onclick="return viewInstallmentPlanRepricing();" ><bean:message key="button.oldinstplan" /></button>
						<button type="button" name="generateRepaymentSch"
							id="generateRepaymentSch"
							 title="Alt+R" accesskey="R"
							class="topformbutton4" 
							onclick="return viewRepaymentScheduleDisbursal();" ><bean:message key="button.oldrepay" /></button>
						<button type="button" name="generateInstallmentPlanNew"
							id="generateRepaymentSchNew"
							title="Alt+I" accesskey="I"
							class="topformbutton4" 
							onclick="javascript:alert('Please Save the Repricing First');" ><bean:message key="button.newinstplan" /></button>
						<button type="button" name="generateRepaymentSchNew"
							id="generateRepaymentSchNew"
							 title="Alt+N" accesskey="N"
							class="topformbutton4" 
							onclick="javascript:alert('Please Save the Repricing First');" > <bean:message key="button.newrepay" /></button>
					</fieldset>
				</html:form>
			</logic:present>

			<!-- --------------------------------------- Repricing New Ends --------------------------------- -->
		
			<!-- --------------------------------------- Repricing Maker Starts--------------------------------- -->
			
			<logic:present name="rePricingData">
				<html:form action="/rePricingMaker" method="post"
					styleId="rePricingMakerForm">
					<html:javascript formName="RePricingMakerDynaValidatorForm"/>
					<fieldset>
						<legend>
							<bean:message key="lbl.rePricingDetails" />
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="hidden" name="businessDate" id="businessDate"
										value="<%=initiationDate %>" />
									<input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
									<html:hidden property="reschId" styleId="reschId"
										value="${requestScope.reschId}" styleClass="text"/>
									<table width="100%" border="0" cellspacing="1" cellpadding="1">

										<tr>
											<td width="25%">
												<bean:message key="lbl.loanNo" />
												<font color="red">*</font>
											</td>
											<td width="25%">
												<html:text styleClass="text" property="loanNo"
													styleId="loanNo" value="${rePricingData[0].loanNo}" readonly="true" maxlength="20" />
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="${rePricingData[0].lbxLoanNoHID}" />
											</td>
											<td width="25%">
												<bean:message key="lbl.customerName" />
											</td>
											<td width="25%">
												<html:text styleClass="text" property="customerName"
													styleId="customerName" maxlength="50" disabled="true"
													value="${rePricingData[0].customerName}" tabindex="-1" />
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.product" />
											</td>
											<td>
												<html:text styleClass="text" property="product"
													styleId="product" value="${rePricingData[0].product}" maxlength="50" 
													disabled="true" tabindex="-1"/>
											</td>
											<td>
												<bean:message key="lbl.scheme" />
											</td>
											<td>
												<html:text styleClass="text" property="scheme"
													styleId="scheme" maxlength="50" value="${rePricingData[0].scheme}" 
													disabled="true" tabindex="-1"/>
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.disbursedAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursedAmount"
													styleId="disbursedAmount" maxlength="18" 
													value="${rePricingData[0].disbursedAmount}"
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											<td>
												<bean:message key="lbl.outstandingLoanAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="outstandingLoanAmount" style="text-align:right;"
													styleId="outstandingLoanAmount" maxlength="18" 
													value="${rePricingData[0].outstandingLoanAmount}"
													readonly="true" tabindex="-1" />
											</td>
										</tr>

										<tr>
											<td>
												<bean:message key="lbl.rePricingSinceDsibursal" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="rePricingSinceDsibursal"
													styleId="rePricingSinceDsibursal" 
													value="${rePricingData[0].rePricingSinceDsibursal}" maxlength="5"
													disabled="true" tabindex="-1"/>
											</td>
											<td>
												<bean:message key="lbl.lastRePricingDate" />
											</td>
											<td >
												<html:text styleClass="text" property="lastRePricingDate" 
													styleId="lastRePricingDate" maxlength="10" 
													value="${rePricingData[0].lastRePricingDate}"
													disabled="true" tabindex="-1"/>
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.rePricingEffectiveRate"/>
											</td>
											<td>
												<html:text styleClass="text" property="effectiveRate" 
													styleId="effectiveRate" value="${rePricingData[0].effectiveRate}"
													readonly="true" tabindex="-1" style="text-align:right;"/>
											</td>
											<td>
												<bean:message key="lbl.flatRate"/>
											</td>
											<td>
												<input type="text" class="text" name="flatRate" id="flatRate" readonly="readonly" tabindex="-1"
												 value="${rePricingData[0].flatRate}" style="text-align:right;" />
											</td>
										</tr>
										<tr>
										<td>
												<bean:message key="lbl.emi"/>
											</td>
											<td>
												<html:text styleClass="text" property="emi" 
													styleId="emi" value="${rePricingData[0].emi}"
													readonly="true" tabindex="-1" style="text-align:right;"/>
											</td>
											<td>
												<bean:message key="lbl.rePricingFromInstallment" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text styleClass="text" property="rePricingFromInstallment"
													styleId="rePricingFromInstallment" value="${rePricingData[0].rePricingFromInstallment}" maxlength="3"
													readonly="true" tabindex="-1" style="text-align:right;"/>
												<html:button property="partPaymentDateButton" styleId="partPaymentDateButton" value=" "
													onclick="openLOVCommon(219,'rePricingMakerForm','reschDate','loanNo','lbxInstlNo','lbxLoanNoHID','Please Select Loan','','rePricingFromInstallment');closeLovCallonLov('loanNo');" 
													styleClass="lovbutton"> 
												</html:button>
												<html:hidden property="lbxInstlNo" styleId="lbxInstlNo" value="" />
											</td>
											
										</tr>
										<tr>
										<td>
												<bean:message key="lbl.instlDate"/>
											</td>
											<td>
												<html:text property="reschDate" styleId="reschDate"
													styleClass="text" readonly="true" tabindex="-1"
													value="${rePricingData[0].reschDate}"/>
											</td>
											<td>
												<bean:message key="lbl.rePricingCondition" />
												<font color="red">*</font>
											</td>
											<td>
												<html:select property="rePricingCondition" styleId="rePricingCondition"
													styleClass="text" value="${rePricingData[0].rePricingCondition}"
													onchange="changeRepricingCondition()">
													<html:option value="E">Change Plan</html:option>
													<html:option value="T">Keep Same Plan</html:option>
													
												</html:select>
											</td>
											
										</tr>
										
										<!--  Added By Rahul papneja |09112017 -->
										
										
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.repricingDate" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="repricingDate" tabindex="-1"
													styleId="repricingDate" maxlength="10" readonly="true"
													value="${rePricingData[0].repricingDate}" />
											<%-- 	<html:button property="partPaymentDateButton" styleId="partPaymentDateButton" value=" "
													onclick="openLOVCommon(219,'partPrepaymentMakerForm','partPaymentDate','loanNo','lbxInstlNo','lbxLoanNoHID','Please Select Loan','','hid');closeLovCallonLov('loanNo');" 
													styleClass="lovbutton"> 
												</html:button>
												--%>
										
										</tr>
										
										
										
										<!-- Ends Here -->
										
										
										<tr>
										<td>
												<bean:message key="lbl.reqRefNo" />
											</td>
											<td>
												<html:text styleClass="text" property="reqRefNo"
													styleId="reqRefNo" value="${rePricingData[0].reqRefNo}" maxlength="10"
													/>
											</td>
											<td>
												<bean:message key="lbl.reschCharges" />
												<font color="red">*</font>
											</td>
											<td><html:text styleClass="text" property="reschCharges" styleId="reschCharges" value="${rePricingData[0].reschCharges}" maxlength="18" readonly="false" tabindex="-1" 
												onfocus="keyUpNumber(this.value, event, 18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" 
												onkeyup="checkNumber(this.value, event, 18,id);"style="text-align:right;" />
											</td>
											
										</tr>
										<tr>
										<td>
												<bean:message key="lbl.rePricingReason"/>
												<font color="red">*</font>
											</td>
											<td>
												<textarea name="rePricingReason" id="rePricingReason"
													maxlength="1000" class="text">${rePricingData[0].rePricingReason}</textarea>
											</td>
											<td>
												<bean:message key="lbl.authorRemarks"/>
											</td>
											<td>
												<textarea name="authorRemarks" id="authorRemarks"
													disabled="disabled" tabindex="-1" class="text">${rePricingData[0].authorRemarks}</textarea>
											</td>
											</tr>
											<tr>
										<td>
										
											 <button type="button"  name="calcInterest" id="calcInterest" 
												 onclick="return generateRepricingReschCharges();" title="Alt+C" accesskey="C" 
												class="topformbutton3"><bean:message key="button.calcharge" /></button>
											
										</td>
										<td></td>

										</tr>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend>
							<bean:message key="lbl.rePricingParameters"/>
						</legend>
						<table cellspacing=0 cellpadding=0 width="100%" border=0>
				  			<tr>
				     			<td class="gridtd">
				 	  				<table width="100%" border="0" cellspacing="1" cellpadding="1">
				        				<tr class="white2">
				        					<td>
   												<b><bean:message key="lbl.rePricingStatus" /></b>
   											</td>
   											<td>
												<b><bean:message key="lbl.interestRateType"/></b>
											</td>
						   					<td>
												<b><bean:message key="lbl.interestRateMethod"/></b>
											</td>
										
											<td>
												<b><bean:message key="lbl.baseRateType"/></b>
											</td>
											<td>
												<b><bean:message key="lbl.baseRate"/></b>
											</td>
											<td>
												<b><bean:message key="lbl.markUp"/></b>
											</td>
											<td>
												<b><bean:message key="lbl.finalRate"/></b>
											</td>
											<td>
												<b><bean:message key="lbl.installmentFreq"/></b>
											</td>
											<td>
												<b><bean:message key="lbl.installmentType"/></b>
											</td>
											<td>
												<b><bean:message key="lbl.tenure"/></b>
											</td>
											<td>
												<b><bean:message key="lbl.noOfInstallment"/></b>
											</td>
											<td>
												<b><bean:message key="lbl.maturityDate"/></b>
											</td>
										</tr>
										<tr class="white1">
											<td><b>Current</b></td>
											
											<td>
						      				<html:hidden property="interestRateTypeOld" styleId="interestRateTypeOld" value="${rePricingData[0].interestRateTypeOldHid}" styleClass="text4"/>
<!--												<input type="hidden" name="interestRateTypeOld" id="interestRateTypeOld"-->
<!--													value="${rePricingData[0].interestRateTypeOldHid}" class="text4"/>-->
												${rePricingData[0].interestRateTypeOld}
											</td>
											
						      				<td>
						      				<html:hidden property="interestRateMethodOld" styleId="interestRateMethodOld" value="${rePricingData[0].interestRateMethodOldHid}" styleClass="text4"/>
<!--						      					<input type="hidden" name="interestRateMethodOld" id="interestRateMethodOld"-->
<!--													value="${rePricingData[0].interestRateMethodOldHid}" class="text4"/>-->
												${rePricingData[0].interestRateMethodOld}
						      				</td>
						      				
											<td>
											<html:hidden property="baseRateTypeOld" styleId="baseRateTypeOld" value="${rePricingData[0].baseRateTypeOld}" styleClass="text4"/>
<!--												<input type="hidden" name="baseRateTypeOld" id="baseRateTypeOld"-->
<!--													value="${rePricingData[0].baseRateTypeOld}" class="text4"/>-->
												${rePricingData[0].baseRateTypeOld}
											</td>
											<td>
											<html:hidden property="baseRateOld" styleId="baseRateOld" value="${rePricingData[0].baseRateOld}" styleClass="text4"/>
<!--												<input type="hidden" name="baseRateOld" id="baseRateOld"-->
<!--													value="${rePricingData[0].baseRateOld}" class="text4"/>-->
												${rePricingData[0].baseRateOld}
											</td>
											<td>
											<html:hidden property="markupOld" styleId="markupOld" value="${rePricingData[0].markupOld}" styleClass="text4"/>
<!--												<input type="hidden" name="markupOld" id="markupOld"-->
<!--													value="${rePricingData[0].markupOld}" class="text4"/>-->
												${rePricingData[0].markupOld}
											</td>
											<td>
											<html:hidden property="finalRateOld" styleId="finalRateOld" value="${rePricingData[0].finalRateOld}" styleClass="text4"/>
<!--												<input type="hidden" name="finalRateOld" id="finalRateOld"-->
<!--													value="${rePricingData[0].finalRateOld}" class="text4"/>-->
												${rePricingData[0].finalRateOld}
											</td>
											<td>
											<html:hidden property="installmentFrequencyOld" styleId="installmentFrequencyOld" value="${rePricingData[0].installmentFrequencyOldHid}" styleClass="text4"/>
<!--												<input type="hidden" name="installmentFrequencyOld" id="installmentFrequencyOld"-->
<!--													value="${rePricingData[0].installmentFrequencyOldHid}" class="text4" />-->
												${rePricingData[0].installmentFrequencyOld}
											</td>
											<td>
											<html:hidden property="installmentTypeOld" styleId="installmentTypeOld" value="${rePricingData[0].installmentTypeOldHid}" styleClass="text4"/>
<!--												<input type="hidden" name="installmentTypeOld" id="installmentTypeOld"-->
<!--													value="${rePricingData[0].installmentTypeOldHid}" class="text4"/>-->
												${rePricingData[0].installmentTypeOld}
											</td>
											<td>
											<html:hidden property="tenureOld" styleId="tenureOld" value="${rePricingData[0].tenureOld}" styleClass="text4"/>
<!--												<input type="hidden" name="tenureOld" id="tenureOld"-->
<!--													value="${rePricingData[0].tenureOld}" class="text4" />-->
												${rePricingData[0].tenureOld}
											</td>
											<td>
											<html:hidden property="noOfInstlOld" styleId="noOfInstlOld" value="${rePricingData[0].noOfInstlOld}" styleClass="text4"/>
<!--												<input type="hidden" name="noOfInstlOld" id="noOfInstlOld"-->
<!--													value="${rePricingData[0].noOfInstlOld}" class="text4" />-->
												${rePricingData[0].noOfInstlOld}
											</td>
											<td>
											<html:hidden property="maturityDateOld" styleId="maturityDateOld" value="${rePricingData[0].maturityDateOld}" styleClass="text4"/>
<!--												<input type="hidden" name="maturityDateOld" id="maturityDateOld"-->
<!--													value="${rePricingData[0].maturityDateOld}" class="text4"/>-->
												${rePricingData[0].maturityDateOld}
											</td>
										</tr>
										<tr class="white1">
											<td ><b>New</b></td>
											
											<td>
												<html:select property="interestRateType" styleId="interestRateType"
													styleClass="text5" value="${rePricingData[0].interestRateTypeHid}" 
													onchange="return repricingInterestRateTypeChange();">
													<html:option value="E">Effective</html:option>
													<html:option value="F">Flat</html:option>
												</html:select>
											</td>
											
											<td>
						      					<html:select property="interestRateMethod" styleId="interestRateMethod"
						      						styleClass="text5" value="${rePricingData[0].interestRateMethodHid}"
						      						onchange="changeInterestRateMethod()">
						      						<html:option value="F">Fixed</html:option>
						      						<html:option value="L">Floating</html:option>
						      					</html:select>
						      				</td>
											
											<td>
												<logic:iterate name="rePricingData" id="subList">
													<logic:equal name="subList" property="interestRateTypeHid" value="F">
														<html:select property="baseRateType" styleClass="text5" styleId="baseRateType" 
															disabled="true" 
															value="${rePricingData[0].baseRateType}" onchange="return getBaseRate();">
															<html:option value="">--Select--</html:option>
									             			<logic:present name="baseRateList">
									               				<html:optionsCollection name="baseRateList" label="id" value="id" /> 
									            			</logic:present>
									          			</html:select>
									          		</logic:equal>
									          		<logic:equal name="subList" property="interestRateTypeHid" value="E">
														<html:select property="baseRateType" styleClass="text5" styleId="baseRateType" 
															value="${rePricingData[0].baseRateType}" onchange="return getBaseRate();">
															<html:option value="">--Select--</html:option>
									             			<logic:present name="baseRateList">
									               				<html:optionsCollection name="baseRateList" label="id" value="id" /> 
									            			</logic:present>
									          			</html:select>
									          		</logic:equal>
								          		</logic:iterate>
											</td>
											<td>
											<html:text property="baseRate" styleId="baseRate" value="${rePricingData[0].baseRate}" styleClass="text4" readonly="true" tabindex="-1" style="text-align:right;"/>
<!--												<input type="text" name="baseRate" id="baseRate"-->
<!--													value="${rePricingData[0].baseRate}" class="text4"-->
<!--													readonly="readonly" tabindex="-1" style="text-align:right;"/>-->
											</td>
											<td>
											<html:text property="markup" styleId="markup" value="${rePricingData[0].markup}" styleClass="text4" readonly="true" maxlength="3" onkeypress="return sevenDecimalnumbersonly(event, id, 3)" 
													onblur="sevenDecimalNumber(this.value, id);" 
													onkeyup="sevenDecimalcheckNumber(this.value, event, 3, id);" style="text-align:right;"/>
<!--												<input type="text" name="markup" id="markup" style="text-align:right;"-->
<!--													value="${rePricingData[0].markup}" class="text4"-->
<!--													maxlength="3" onkeypress="return sevenDecimalnumbersonly(event, id, 3)" -->
<!--													onblur="sevenDecimalNumber(this.value, id);checkRate('markup');calculateFinalRate();" -->
<!--													onkeyup="sevenDecimalcheckNumber(this.value, event, 3, id);"/>-->
											</td>
											<td>
											<html:text property="finalRate" styleId="finalRate" value="${rePricingData[0].finalRate}" styleClass="text4" 
											onblur="calculateFinalRate();"
											 tabindex="-1" style="text-align:right;"/>
<!--												<input type="text" name="finalRate" id="finalRate" readonly="readonly"-->
<!--													value="${rePricingData[0].finalRate}" class="text4"-->
<!--													style="text-align:right;"/>-->
											</td>
											<td>
												<logic:iterate name="rePricingData" id="sublist">
												<logic:equal name="sublist" property="rePricingCondition" value="E">
												<html:select property="installmentFrequency" styleId="installmentFrequency"
													styleClass="text5" value="${rePricingData[0].installmentFrequencyHid}"
													onchange="calculateInstallmentNoInRepricing()">
													<html:option value="M">Monthly</html:option>
													<html:option value="B">Bimonthly</html:option>
													<html:option value="Q">Quarterly</html:option>
													<html:option value="H">Half Yearly</html:option>
													<html:option value="Y">Yearly</html:option>
												</html:select>
												</logic:equal>
												<logic:equal name="sublist" property="rePricingCondition" value="T">
												<html:select property="installmentFrequency" styleId="installmentFrequency"
													styleClass="text5" value="${rePricingData[0].installmentFrequencyHid}"
													onchange="calculateInstallmentNoInRepricing()" disabled="true">
													<html:option value="M">Monthly</html:option>
													<html:option value="B">Bimonthly</html:option>
													<html:option value="Q">Quarterly</html:option>
													<html:option value="H">Half Yearly</html:option>
													<html:option value="Y">Yearly</html:option>
												</html:select>
												</logic:equal>
												</logic:iterate>
											</td>
											<td>
												<logic:iterate name="rePricingData" id="sublist">
												<logic:equal name="sublist" property="rePricingCondition" value="E">
												<html:select property="installmentType" styleId="installmentType"
													styleClass="text5" value="${rePricingData[0].installmentTypeHid}">
													<html:option value="E">Eq. Installment</html:option>
													<html:option value="G">Gr. Installment</html:option>
													<html:option value="P">Eq. Principal</html:option>
													<html:option value="Q">Gr. Principal1</html:option>
													<html:option value="S">SEPARATE PRINCIPAL & INTEREST</html:option>
												</html:select>
												</logic:equal>
												<logic:equal name="sublist" property="rePricingCondition" value="T">
												<html:select property="installmentType" styleId="installmentType"
													styleClass="text5" value="${rePricingData[0].installmentTypeHid}"
													disabled="true">
													<html:option value="E">Eq. Installment</html:option>
													<html:option value="G">Gr. Installment</html:option>
													<html:option value="P">Eq. Principal</html:option>
													<html:option value="Q">Gr. Principal1</html:option>
													<html:option value="S">SEPARATE PRINCIPAL & INTEREST</html:option>
												</html:select>
												</logic:equal>
												</logic:iterate>
											</td>
											<td>
												<logic:iterate name="rePricingData" id="sublist">
												<logic:equal name="sublist" property="rePricingCondition" value="E">
												<html:text property="tenure" styleId="tenure" value="${rePricingData[0].tenure}" styleClass="text4"  onchange="calculateInstallmentNoInRepricing();calculateMaturityDateInRepricing();" 
													maxlength="3" tabindex="-1" style="text-align:right;"/>
<!--												<input type="text" name="tenure" id="tenure"-->
<!--													value="${rePricingData[0].tenure}" class="text4" -->
<!--													onchange="calculateInstallmentNoInRepricing();calculateMaturityDateInRepricing();" -->
<!--													maxlength="3" style="text-align:right;"/>-->
												</logic:equal>
												<logic:equal name="sublist" property="rePricingCondition" value="T">
												<html:text property="tenure" styleId="tenure" value="${rePricingData[0].tenure}" styleClass="text4" onchange="calculateInstallmentNoInRepricing();calculateMaturityDateInRepricing();" readonly="true" maxlength="3" tabindex="-1" style="text-align:right;"/>
<!--												<input type="text" name="tenure" id="tenure"-->
<!--													value="${rePricingData[0].tenure}" class="text4" -->
<!--													onchange="calculateInstallmentNoInRepricing();calculateMaturityDateInRepricing();" -->
<!--													maxlength="3" style="text-align:right;" readonly="readonly" tabindex="-1"/>-->
												</logic:equal>
												</logic:iterate>
											</td>
											<td>
											<html:text property="noOfInstl" styleId="noOfInstl" value="${rePricingData[0].noOfInstl}" styleClass="text4" maxlength="3" readonly="true" tabindex="-1" style="text-align:right;"/>
<!--												<input type="text" name="noOfInstl" id="noOfInstl" -->
<!--													readonly="readonly" value="${rePricingData[0].noOfInstl}" -->
<!--													class="text4" tabindex="-1"-->
<!--													maxlength="3" style="text-align:right;"/>-->
											</td>
											<td>
											<html:text property="maturityDate" styleId="maturityDate" value="${rePricingData[0].maturityDate}" styleClass="text3" readonly="true" tabindex="-1" />

											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<button type="button" name="save" id="save" 
							class="topformbutton2" title="Alt+V" accesskey="V"
							onclick="return updateRepricing('P');"><bean:message key="button.save" /></button>
						<button type="button" name="saveFwd" id="saveFwd" title="Alt+F" accesskey="F"
							 class="topformbutton2"
							onclick="return updateRepricing('F','<bean:message key="msg.confirmationForwardMsg" />');"><bean:message key="button.forward" /></button>
						<button type="button" name="generateInstallmentPlan"
							id="generateInstallmentPlan"
							 title="Alt+O" accesskey="O"
							class="topformbutton4" 
							onclick="return viewInstallmentPlanRepricing()" ><bean:message key="button.oldinstplan" /></button>
						<button type="button" name="generateRepaymentSch"
							id="generateRepaymentSch"
							 title="Alt+R" accesskey="R"
							class="topformbutton4" 
							onclick="return viewRepaymentScheduleDisbursal();" ><bean:message key="button.oldrepay" /></button>
						<logic:iterate name="rePricingData" id="sublist">
						<logic:equal name="sublist" property="rePricingCondition" value="E">
						<button type="button" name="generateInstallmentPlanNew"
							id="generateRepaymentSchNew"
							 title="Alt+I" accesskey="I"
							class="topformbutton4" 
							onclick="return generateInstallmentPlanRepricing('P')" ><bean:message key="button.newinstplan" /></button>
						</logic:equal>
						<logic:equal name="sublist" property="rePricingCondition" value="T">
						<button type="button" name="generateInstallmentPlanNew"
							id="generateRepaymentSchNew"
							 title="Alt+I" accesskey="I"
							class="topformbutton4" disabled="disabled" ><bean:message key="button.newinstplan" /></button>
						</logic:equal>
						</logic:iterate>
						<button type="button" name="generateInstallmentPlan"
							id="generateInstallmentPlan"
							 title="Alt+N" accesskey="N"
							class="topformbutton4" 
							onclick="return newRepaymentScheduleRepricing();" > <bean:message key="button.newrepay" /></button>
							<button type="button" name="delete" id="delete" 
										class="topformbutton2" accesskey="D" title="Alt+D"
										onclick="return deleteRecord()"><bean:message key="button.delete" /></button>
					</fieldset>
				</html:form>
			</logic:present>
			
			<!-- --------------------------------------- Repricing Maker Ends --------------------------------- -->

			<!-- --------------------------------------- Repricing Author Starts --------------------------------- -->
			
			<logic:present name="rePricingDataAuthor">
				<html:form action="/rePricingMaker" method="post"
					styleId="rePricingMakerForm">
					<html:javascript formName="RePricingMakerDynaValidatorForm"/>
					<fieldset>
						<legend>
							<bean:message key="lbl.rePricingDetails" />
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="hidden" name="businessDate" id="businessDate"
										value="<%=initiationDate%>" />
									<input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
									<html:hidden property="reschId" styleId="reschId"
										value="${sessionScope.reschId}" styleClass="text"/>
									<table width="100%" border="0" cellspacing="1" cellpadding="1">

										<tr>
											<td width="25%">
												<bean:message key="lbl.loanNo" />
												<font color="red">*</font>
											</td>
											<td width="25%">
												<html:text styleClass="text" property="loanNo"
													styleId="loanNo" value="${rePricingDataAuthor[0].loanNo}" disabled="true"
													tabindex="-1" maxlength="20" />
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="${rePricingDataAuthor[0].lbxLoanNoHID}" />
											</td>
											<td width="25%">
												<bean:message key="lbl.customerName" />
											</td>
											<td width="25%">
												<html:text styleClass="text" property="customerName"
													styleId="customerName" maxlength="50" disabled="true"
													value="${rePricingDataAuthor[0].customerName}" tabindex="-1" />
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.product" />
											</td>
											<td>
												<html:text styleClass="text" property="product"
													styleId="product" value="${rePricingDataAuthor[0].product}" maxlength="50" 
													disabled="true" tabindex="-1"/>
											</td>
											<td>
												<bean:message key="lbl.scheme" />
											</td>
											<td>
												<html:text styleClass="text" property="scheme"
													styleId="scheme" maxlength="50" value="${rePricingDataAuthor[0].scheme}" 
													disabled="true" tabindex="-1"/>
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.disbursedAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursedAmount"
													styleId="disbursedAmount" maxlength="18" 
													value="${rePricingDataAuthor[0].disbursedAmount}"
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											<td>
												<bean:message key="lbl.outstandingLoanAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="outstandingLoanAmount" style="text-align:right;"
													styleId="outstandingLoanAmount" maxlength="18" 
													value="${rePricingDataAuthor[0].outstandingLoanAmount}"
													readonly="true" tabindex="-1" />
											</td>
										</tr>

										<tr>
											<td>
												<bean:message key="lbl.rePricingSinceDsibursal" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="rePricingSinceDsibursal"
													styleId="rePricingSinceDsibursal" 
													value="${rePricingDataAuthor[0].rePricingSinceDsibursal}" maxlength="5"
													disabled="true" tabindex="-1"/>
											</td>
											<td>
												<bean:message key="lbl.lastRePricingDate" />
											</td>
											<td >
												<html:text styleClass="text" property="lastRePricingDate" 
													styleId="lastRePricingDate" maxlength="10" 
													value="${rePricingDataAuthor[0].lastRePricingDate}"
													disabled="true" tabindex="-1"/>
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.rePricingEffectiveRate"/>
											</td>
											<td>
												<html:text styleClass="text" property="effectiveRate" 
													styleId="effectiveRate" value="${rePricingDataAuthor[0].effectiveRate}"
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
										
											<td>
												<bean:message key="lbl.flatRate"/>
											</td>
											<td>
												<input type="text" class="text" name="flatRate" id="flatRate" readonly="readonly" tabindex="-1"
												 value="${rePricingDataAuthor[0].flatRate}" style="text-align:right;" />
											</td>
											
										</tr>
										<tr>
										<td>
												<bean:message key="lbl.emi"/>
											</td>
											<td>
												<html:text styleClass="text" property="emi" 
													styleId="emi" value="${rePricingDataAuthor[0].emi}"
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											<td>
												<bean:message key="lbl.rePricingFromInstallment" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text styleClass="text" property="rePricingFromInstallment"
													styleId="rePricingFromInstallment" maxlength="3"
													value="${rePricingDataAuthor[0].rePricingFromInstallment}" 
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											
										</tr>
										<tr>
										<td>
												<bean:message key="lbl.instlDate"/>
											</td>
											<td>
												<html:text property="reschDate" styleId="reschDate"
													styleClass="text" disabled="true" tabindex="-1"
													value="${rePricingDataAuthor[0].reschDate}"/>
											</td>
											<td>
												<bean:message key="lbl.rePricingCondition" />
												<font color="red">*</font>
											</td>
											<td>
												<html:select property="rePricingCondition" styleId="rePricingCondition"
													styleClass="text" value="${rePricingDataAuthor[0].rePricingCondition}"
													disabled="true" tabindex="-1" >
													<html:option value="E">Change Plan</html:option>
													<html:option value="T">Keep Same Plan</html:option>
													
												</html:select>
											</td>
										
										</tr>
										
										<!--  Added By Rahul papneja |09112017 -->
										
										
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.repricingDate" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="repricingDate" tabindex="-1"
													styleId="repricingDate" maxlength="10"  disabled="true"
													value="${rePricingDataAuthor[0].repricingDate}" />
											<%-- 	<html:button property="partPaymentDateButton" styleId="partPaymentDateButton" value=" "
													onclick="openLOVCommon(219,'partPrepaymentMakerForm','partPaymentDate','loanNo','lbxInstlNo','lbxLoanNoHID','Please Select Loan','','hid');closeLovCallonLov('loanNo');" 
													styleClass="lovbutton"> 
												</html:button>
												--%>
										
										</tr>
										
										
										
										<!-- Ends Here -->
										
										<tr>
											<td>
												<bean:message key="lbl.reqRefNo" />
											</td>
											<td>
												<html:text styleClass="text" property="reqRefNo"
													styleId="reqRefNo" value="${rePricingDataAuthor[0].reqRefNo}" maxlength="10"
													disabled="true" tabindex="-1"/>
											</td>
											<td>
												<bean:message key="lbl.reschCharges" />
												<font color="red">*</font>
											</td>
											<td><html:text styleClass="text" property="reschCharges" onkeyup="return numbersonly(event,id,18)"
													styleId="reschCharges" value="${rePricingDataAuthor[0].reschCharges}" maxlength="18"
													disabled="true" tabindex="-1" style="text-align:right;" />
											</td>
											
										</tr>
										<tr>
										<td>
												<bean:message key="lbl.rePricingReason"/>
												<font color="red">*</font>
											</td>
											<td>
												<textarea name="rePricingReason" id="rePricingReason"
													maxlength="1000" class="text" 
													disabled="disabled" tabindex="-1">${rePricingDataAuthor[0].rePricingReason}</textarea>
											</td>
											<td>
												<bean:message key="lbl.authorRemarks"/>
											</td>
											<td>
												<textarea name="authorRemarks" id="authorRemarks"
													disabled="disabled" tabindex="-1" class="text">${rePricingDataAuthor[0].authorRemarks}</textarea>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend>
							<bean:message key="lbl.rePricingParameters"/>
						</legend>
						<table cellspacing=0 cellpadding=0 width="100%" border=0>
				  			<tr>
				     			<td class="gridtd">
				 	  				<table width="100%" border="0" cellspacing="1" cellpadding="1">
				        				<tr class="white2">
				        					<td>
   												<b><bean:message key="lbl.rePricingStatus" /></b>
   											</td>
   											<td>
												<b><bean:message key="lbl.interestRateType"/></b>
											</td>
						   					<td>
												<b><bean:message key="lbl.interestRateMethod"/></b>
											</td>
											
											<td>
												<b><bean:message key="lbl.baseRateType"/></b>
											</td>
											<td>
												<b><bean:message key="lbl.baseRate"/></b>
											</td>
											<td>
												<b><bean:message key="lbl.markUp"/></b>
											</td>
											<td>
												<b><bean:message key="lbl.finalRate"/></b>
											</td>
											<td>
												<b><bean:message key="lbl.installmentFreq"/></b>
											</td>
											<td>
												<b><bean:message key="lbl.installmentType"/></b>
											</td>
											<td>
												<b><bean:message key="lbl.tenure"/></b>
											</td>
											<td>
												<b><bean:message key="lbl.noOfInstallment"/></b>
											</td>
											<td>
												<b><bean:message key="lbl.maturityDate"/></b>
											</td>
										</tr>
										<tr class="white1">
											<td><b>Current</b></td>
											<td>
						      				<html:hidden property="interestRateTypeOld" styleId="interestRateTypeOld" value="${rePricingDataAuthor[0].interestRateTypeOldHid}" styleClass="text4"/>
<!--												<input type="hidden" name="interestRateTypeOld" id="interestRateTypeOld"-->
<!--													value="${rePricingDataAuthor[0].interestRateTypeOldHid}" class="text4"/>-->
												${rePricingDataAuthor[0].interestRateTypeOld}
											</td>
						      				<td>
						      				<html:hidden property="interestRateMethodOld" styleId="interestRateMethodOld" value="${rePricingDataAuthor[0].interestRateMethodOldHid}" styleClass="text4"/>
<!--						      					<input type="hidden" name="interestRateMethodOld" id="interestRateMethodOld"-->
<!--													value="${rePricingDataAuthor[0].interestRateMethodOldHid}" class="text4"/>-->
												${rePricingDataAuthor[0].interestRateMethodOld}
						      				</td>
						      				
											<td>
											<html:hidden property="baseRateTypeOld" styleId="baseRateTypeOld" value="${rePricingDataAuthor[0].baseRateTypeOld}" styleClass="text4"/>
<!--												<input type="hidden" name="baseRateTypeOld" id="baseRateTypeOld"-->
<!--													value="${rePricingDataAuthor[0].baseRateTypeOld}" class="text4"/>-->
												${rePricingDataAuthor[0].baseRateTypeOld}
											</td>
											<td>
											<html:hidden property="baseRateOld" styleId="baseRateOld" value="${rePricingDataAuthor[0].baseRateOld}" styleClass="text4"/>
<!--												<input type="hidden" name="baseRateOld" id="baseRateOld"-->
<!--													value="${rePricingDataAuthor[0].baseRateOld}" class="text4"/>-->
												${rePricingDataAuthor[0].baseRateOld}
											</td>
											<td>
											<html:hidden property="markupOld" styleId="markupOld" value="${rePricingDataAuthor[0].markupOld}" styleClass="text4"/>
<!--												<input type="hidden" name="markupOld" id="markupOld"-->
<!--													value="${rePricingDataAuthor[0].markupOld}" class="text4"/>-->
												${rePricingDataAuthor[0].markupOld}
											</td>
											<td>
											<html:hidden property="finalRateOld" styleId="finalRateOld" value="${rePricingDataAuthor[0].finalRateOld}" styleClass="text4"/>
<!--												<input type="hidden" name="finalRateOld" id="finalRateOld"-->
<!--													value="${rePricingDataAuthor[0].finalRateOld}" class="text4"/>-->
												${rePricingDataAuthor[0].finalRateOld}
											</td>
											<td>
											<html:hidden property="installmentFrequencyOld" styleId="installmentFrequencyOld" value="${rePricingDataAuthor[0].installmentFrequencyOldHid}" styleClass="text4"/>
<!--												<input type="hidden" name="installmentFrequencyOld" id="installmentFrequencyOld"-->
<!--													value="${rePricingDataAuthor[0].installmentFrequencyOldHid}" class="text4" />-->
												${rePricingDataAuthor[0].installmentFrequencyOld}
											</td>
											<td>
											<html:hidden property="installmentTypeOld" styleId="installmentTypeOld" value="${rePricingDataAuthor[0].installmentTypeOldHid}" styleClass="text4"/>
<!--												<input type="hidden" name="installmentTypeOld" id="installmentTypeOld"-->
<!--													value="${rePricingDataAuthor[0].installmentTypeOldHid}" class="text4"/>-->
												${rePricingDataAuthor[0].installmentTypeOld}
											</td>
											<td>
											<html:hidden property="tenureOld" styleId="tenureOld" value="${rePricingDataAuthor[0].tenureOld}" styleClass="text4"/>
<!--												<input type="hidden" name="tenureOld" id="tenureOld"-->
<!--													value="${rePricingDataAuthor[0].tenureOld}" class="text4" />-->
												${rePricingDataAuthor[0].tenureOld}
											</td>
											<td>
											<html:hidden property="noOfInstlOld" styleId="noOfInstlOld" value="${rePricingDataAuthor[0].noOfInstlOld}" styleClass="text4"/>
<!--												<input type="hidden" name="noOfInstlOld" id="noOfInstlOld"-->
<!--													value="${rePricingDataAuthor[0].noOfInstlOld}" class="text4" />-->
												${rePricingDataAuthor[0].noOfInstlOld}
											</td>
											<td>
											<html:hidden property="maturityDateOld" styleId="maturityDateOld" value="${rePricingDataAuthor[0].maturityDateOld}" styleClass="text4"/>
<!--												<input type="hidden" name="maturityDateOld" id="maturityDateOld"-->
<!--													value="${rePricingDataAuthor[0].maturityDateOld}" class="text4"/>-->
												${rePricingDataAuthor[0].maturityDateOld}
											</td>
										</tr>
										<tr class="white1">
											<td ><b>New</b></td>
											<td>
												<html:select property="interestRateType" styleId="interestRateType"
													styleClass="text5" value="${rePricingDataAuthor[0].interestRateTypeHid}" 
													onchange="return repricingInterestRateTypeChange();"
													disabled="true" tabindex="-1">
													<html:option value="E">Effective</html:option>
													<html:option value="F">Flat</html:option>
												</html:select>
											</td>
											<td>
						      					<html:select property="interestRateMethod" styleId="interestRateMethod"
						      						styleClass="text5" value="${rePricingDataAuthor[0].interestRateMethodHid}"
						      						disabled="true" tabindex="-1">
						      						<html:option value="F">Fixed</html:option>
						      						<html:option value="L">Floating</html:option>
						      					</html:select>
						      				</td>
											
											<td>
												<html:select property="baseRateType" styleClass="text5" styleId="baseRateType" 
													disabled="true" tabindex="-1"
													value="${rePricingDataAuthor[0].baseRateType}" onchange="return getBaseRate();">
													<html:option value="">--Select--</html:option>
							             			<logic:present name="baseRateList">
							               				<html:optionsCollection name="baseRateList" label="id" value="id" /> 
							            			</logic:present>
							          			</html:select>
											</td>
											<td>
												<input type="text" name="baseRate" id="baseRate"
													value="${rePricingDataAuthor[0].baseRate}" class="text4"
													disabled="disabled" tabindex="-1" style="text-align:right;"/>
											</td>
											<td>
												<input type="text" name="markup" id="markup" style="text-align:right;"
													value="${rePricingDataAuthor[0].markup}" class="text4"
													maxlength="3" onkeypress="return sevenDecimalnumbersonly(event, id, 3)" 
													onblur="sevenDecimalNumber(this.value, id);" 
													onkeyup="sevenDecimalcheckNumber(this.value, event, 3, id);"
													disabled="disabled" tabindex="-1"/>
											</td>
											<td>
												<input type="text" name="finalRate" id="finalRate" 
													value="${rePricingDataAuthor[0].finalRate}" class="text4"
													onblur="calculateFinalRate();"
													style="text-align:right;" disabled="disabled" tabindex="-1"/>
											</td>
											<td>
												<html:select property="installmentFrequency" styleId="installmentFrequency"
													styleClass="text5" value="${rePricingDataAuthor[0].installmentFrequencyHid}"
													disabled="true" tabindex="-1">
													<html:option value="M">Monthly</html:option>
													<html:option value="B">Bimonthly</html:option>
													<html:option value="Q">Quarterly</html:option>
													<html:option value="H">Half Yearly</html:option>
													<html:option value="Y">Yearly</html:option>
												</html:select>
											</td>
											<td>
												<html:select property="installmentType" styleId="installmentType"
													styleClass="text5" value="${rePricingDataAuthor[0].installmentTypeHid}"
													disabled="true" tabindex="-1">
													<html:option value="E">Eq. Installment</html:option>
													<html:option value="G">Gr. Installment</html:option>
													<html:option value="P">Eq. Principal</html:option>
													<html:option value="Q">Gr. Principal1</html:option>
													<html:option value="S">SEPARATE PRINCIPAL & INTEREST</html:option>
												</html:select> 
											</td>
											<td>
											<html:text property="tenure" styleId="tenure" value="${rePricingDataAuthor[0].tenure}" styleClass="text4" onkeypress="return isNumberKey(event);" readonly="true" maxlength="3" tabindex="-1" style="text-align:right;"/>
<!--												<input type="text" name="tenure" id="tenure"-->
<!--													value="${rePricingDataAuthor[0].tenure}" class="text4" -->
<!--													onkeypress="return isNumberKey(event);" maxlength="3" style="text-align:right;"-->
<!--													disabled="disabled" tabindex="-1"/>-->
											</td>
											<td>
											<html:text property="noOfInstl" styleId="noOfInstl" value="${rePricingDataAuthor[0].noOfInstl}" onkeypress="return isNumberKey(event);"  styleClass="text4" maxlength="3" readonly="true" tabindex="-1" style="text-align:right;"/>
<!--												<input type="text" name="noOfInstl" id="noOfInstl"-->
<!--													value="${rePricingDataAuthor[0].noOfInstl}" class="text4" -->
<!--													onkeypress="return isNumberKey(event);" maxlength="3" style="text-align:right;"-->
<!--													disabled="disabled" tabindex="-1"/>-->
											</td>
											<td>
											
											<html:text property="maturityDate" styleId="maturityDate" value="${rePricingDataAuthor[0].maturityDate}" styleClass="text3" readonly="true" tabindex="-1" />	
<!--												<input type="text" name="maturityDate" id="maturityDate"-->
<!--													value="${rePricingDataAuthor[0].maturityDate}" class="text3"-->
<!--													disabled="disabled" tabindex="-1"/>-->
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<button type="button" name="generateInstallmentPlan"
							id="generateInstallmentPlan"
							 title="Alt+O" accesskey="O"
							class="topformbutton4" 
							onclick="return viewInstallmentPlanRepricing()" > <bean:message key="button.oldinstplan" /></button>
						<button type="button" name="generateRepaymentSch"
							id="generateRepaymentSch"
							 title="Alt+R" accesskey="R"
							class="topformbutton4" 
							onclick="return viewRepaymentScheduleDisbursal();" ><bean:message key="button.oldrepay" /></button>
						<logic:iterate name="rePricingDataAuthor" id="sublist">
						<logic:equal name="sublist" property="rePricingCondition" value="E">
						<button type="button" name="generateInstallmentPlanNew"
							id="generateRepaymentSchNew"
							 title="Alt+I" accesskey="I"
							class="topformbutton4" 
							onclick="return generateInstallmentPlanRepricing('F')" ><bean:message key="button.newinstplan" /></button>
						</logic:equal>
						<logic:equal name="sublist" property="rePricingCondition" value="T">
						<button type="button" name="generateInstallmentPlanNew"
							id="generateRepaymentSchNew"
							 title="Alt+I" accesskey="I"
							class="topformbutton4" disabled="disabled" ><bean:message key="button.newinstplan" /></button>
						</logic:equal>
						</logic:iterate>
						<button type="button" name="generateInstallmentPlan"
							id="generateInstallmentPlan"
							 title="Alt+N" accesskey="N"
							class="topformbutton4" 
							onclick="return newRepaymentScheduleRepricing();" ><bean:message key="button.newrepay" /></button>
					</fieldset>
				</html:form>
			</logic:present>
			
			<!-- --------------------------------------- Repricing Author Ends --------------------------------- -->

		</div>
	</div>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<script>
	setFramevalues("rePricingMakerForm");
</script>
</body>
</html:html>
<logic:present name="delete">
<script type="text/javascript">
		alert("<bean:message key="lbl.dataDeleted" />");
		window.location = "<%=request.getContextPath()%>/rePricingSearch.do?method=rePricingMakerSearch";	
</script>
</logic:present>
<logic:present name="notDelete">
<script type="text/javascript">
		alert("<bean:message key="lbl.dataNtDeleted" />");
</script>
</logic:present>
<logic:present name="message">

<script type="text/javascript">
if("<%=request.getAttribute("message")%>"=="S")
{
	alert("<bean:message key="msg.DataSaved" />");
}
else if("<%=request.getAttribute("message")%>"=="F")
{
	alert("<bean:message key="msg.ForwardSuccessfully" />");
	window.location = "<%=request.getContextPath()%>/rePricingSearch.do?method=rePricingMakerSearch";	
}
else if("<%=request.getAttribute("message")%>"=="newInstlPlan")
{
	alert("<bean:message key="msg.saveNewInstlPlan" />");
}
else if("<%=request.getAttribute("message")%>"=="E" && "<%=request.getAttribute("msg")%>"!="")
{
	//alert("<bean:message key="msg.DataNotSaved" />");
	alert("<%=request.getAttribute("msg")%>");
	window.location = "<%=request.getContextPath()%>/rePricingSearchBehind.do?method=rePricingMakerSearch";
}
else if("<%=request.getAttribute("message")%>"=="E")
{
	alert("<bean:message key="msg.DataNotSaved" />");
	window.location = "<%=request.getContextPath()%>/rePricingSearchBehind.do?method=rePricingMakerSearch";
}
</script>
</logic:present>

<logic:present name="nonInstallmentBased">
	<script type="text/javascript">
		alert("<bean:message key="msg.nonInstallmentBased" />");
	</script>
</logic:present>
