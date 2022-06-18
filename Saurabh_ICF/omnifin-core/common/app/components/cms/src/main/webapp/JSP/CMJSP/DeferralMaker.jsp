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
		src="<%=request.getContextPath()%>/js/cmScript/cmLoanInitjs.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
	<script type="text/javascript"
		src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
<script type="text/javascript">
	function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('deferralMakerForm').deferredInstallmentNo.focus();
     }
     else if(document.getElementById('modifyRecord').value =='N')
     {
    	document.getElementById('deferralMakerForm').loanButton.focus();
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
<body onclick="parent_disable();" onunload="closeAllLovCallUnloadBody();" onload="enableAnchor();checkChanges('deferralMakerForm');fntab();">
	<input type="hidden" name="formatD" id="formatD"
		value="<bean:message key="lbl.dateFormat"/>" />
	<div id=centercolumn>
		<div id=revisedcontainer>

			<!-- --------------------------------------- Deferral New --------------------------------- -->

			<logic:present name="deferralNew">
				<html:form action="/deferralMaker" method="post"
					styleId="deferralMakerForm">
					<html:javascript formName="DeferralMakerDynaValidatorForm"/>
					<fieldset>
						<legend>
							<bean:message key="lbl.deferralDetails" />
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
													styleId="loanNo" value="" readonly="true" 
													maxlength="20" />
												<html:button property="loanButton" styleId="loanButton" value=" "
													onclick="openLOVCommon(218,'deferralMakerForm','loanNo','','', '','','generateDeferralValues','customerName');closeLovCallonLov1();" 
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
													styleId="customerName" maxlength="50" readonly="true"
													value="" tabindex="-1" />
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.product" />
											</td>
											<td>
												<html:text styleClass="text" property="product"
													styleId="product" value="" 
													maxlength="50" 
													readonly="true" tabindex="-1"/>
											</td>
											<td>
												<bean:message key="lbl.scheme" />
											</td>
											<td>
												<html:text styleClass="text" property="scheme"
													styleId="scheme" maxlength="50" 
													value="" 
													readonly="true" tabindex="-1"/>
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.disbursedAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursedAmount"
													styleId="disbursedAmount" maxlength="18" 
													value=""
													readonly="true" tabindex="-1" style="text-align:right;"/>
											</td>
											<td>
												<bean:message key="lbl.outstandingLoanAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="outstandingLoanAmount" style="text-align:right;"
													styleId="outstandingLoanAmount" maxlength="18" 
													value=""
													readonly="true" tabindex="-1" />
											</td>
										</tr>

										<tr>
											<td>
												<bean:message key="lbl.deferralsSinceDsibursal" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="deferralsSinceDsibursal"
													styleId="deferralsSinceDsibursal" 
													value="" maxlength="3"
													readonly="true" tabindex="-1"/>
											</td>
											<td>
												<bean:message key="lbl.lastDeferralDate" />
											</td>
											<td >
												<html:text styleClass="text" property="lastDeferralDate" 
													styleId="lastDeferralDate" maxlength="10" 
													value=""
													readonly="true" tabindex="-1"/>
											</td>
										</tr>

										<tr>
											<td>
												<bean:message key="lbl.deferralFromInstallment" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text styleClass="text" property="deferralFromInstallment"
													styleId="deferralFromInstallment" 
													value="" maxlength="3"
													readonly="true" tabindex="-1" style="text-align:right;"/>
												<html:button property="partPaymentDateButton" styleId="partPaymentDateButton" value=" "
													onclick="openLOVCommon(219,'deferralMakerForm','reschDate','loanNo','lbxInstlNo','lbxLoanNoHID','Please Select Loan','','deferralFromInstallment');closeLovCallonLov('loanNo');" 
													styleClass="lovbutton"> 
												</html:button>
												<html:hidden property="lbxInstlNo" styleId="lbxInstlNo" value="" />
											</td>
											<td>
												<bean:message key="lbl.deferredInstallmentNo" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text styleClass="text" property="deferredInstallmentNo"
													styleId="deferredInstallmentNo" 
													value="" maxlength="3" />
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
												<bean:message key="lbl.reqRefNo" />
											</td>
											<td>
												<html:text styleClass="text" property="reqRefNo"
													styleId="reqRefNo" value="" maxlength="10"
													/>
											</td>
										</tr>
										<tr>
											<td>
												<button type="button" name="calcInterest" id="calcInterest" 
													onclick="return generateDeferralReschCharges();" title="Alt+C" accesskey="C" 
													class="topformbutton3"><bean:message key="button.calcharge"/></button>
											</td>
											<td></td>
											<td>
												<bean:message key="lbl.authorRemarks"/>
											</td>
											<td>
												<textarea name="authorRemarks" id="authorRemarks"
													disabled="disabled" tabindex="-1" class="text"></textarea>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend>
							<bean:message key="lbl.deferralParameters"/>
						</legend>
						<table cellspacing=0 cellpadding=0 width="100%" border=0>
				  			<tr>
				     			<td class="gridtd">
				 	  				<table width="100%" border="0" cellspacing="1" cellpadding="1">
				        				<tr class="white2">
											<td width="10%">
												<b><bean:message key="lbl.interestRateType"/></b>
											</td>
											<td width="10%">
												<b><bean:message key="lbl.baseRateType"/></b>
											</td>
											<td width="8%">
												<b><bean:message key="lbl.baseRate"/></b>
											</td>
											<td width="10%">
												<b><bean:message key="lbl.installmentFreq"/></b>
											</td>
											<td width="10%">
												<b><bean:message key="lbl.installmentType"/></b>
											</td>
											<td width="6%">
												<b><bean:message key="lbl.emi"/></b>
											</td>
											<td width="6%">
												<b><bean:message key="lbl.tenure"/></b>
											</td>
											<td width="8%">
												<b><bean:message key="lbl.maturityDate"/></b>
											</td>
											<td width="8%">
												<b><bean:message key="lbl.nextDueDate"/></b>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
							<td class="gridtd">
								<div id="deferralParams">
										<table width="100%" border="0" cellspacing="1" cellpadding="1">
										<tr class="white1">
											<td width="10%"></td>
											<td width="10%"></td>
											<td width="8%"></td>
											<td width="10%"></td>
											<td width="10%"></td>
											<td width="6%"></td>
											<td width="6%"></td>
											<td width="8%"></td>
											<td width="8%"></td>
										</tr>
									</table>
								</div>
							</td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend>
							<bean:message key="lbl.chargecalculation"/>
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="1" cellpadding="1">
										<tr>
											<td width="25%">
												<bean:message key="lbl.reschCharges" />
												<font color="red">*</font>
											</td>
											<td width="25%">
												<html:text styleClass="text" property="reschCharges" styleId="reschCharges" value="" maxlength="18" readonly="false"  tabindex="-1" 
												onfocus="keyUpNumber(this.value, event, 18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" 
												onkeyup="checkNumber(this.value, event, 18,id);" style="text-align:right;" />
											</td>
											<td width="25%">
												<bean:message key="lbl.deferralReason"/>
												<font color="red">*</font>
											</td>
											<td width="25%">
												<textarea name="deferralReason" id="deferralReason"
													maxlength="1000" value="" class="text"></textarea>
											</td>
										</tr>
									</table>
									<button type="button" name="save" id="save"
										class="topformbutton2" title="Alt+V" accesskey="V"
										onclick="return saveDeferral();"><bean:message key="button.save" /></button>
									<button type="button" name="saveFwd" id="saveFwd" title="Alt+F" accesskey="F"
										 class="topformbutton2"
										onclick="return updateDisbursalBeforeSave();"><bean:message key="button.forward" /></button>
									<button type="button" name="generateRepaymentSch" id="generateRepaymentSch" title="Alt+R" accesskey="R" class="topformbutton4" 
										onclick="return viewRepaymentScheduleDisbursal();" > <bean:message key="button.oldrepay" /></button>
									<button type="button" name="generateRepaymentSchNew"
										id="generateRepaymentSchNew" title="Alt+N" accesskey="N" class="topformbutton4" 
										onclick="javascript:alert('Please Save the Deferral First');" ><bean:message key="button.newrepay" /></button>
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
			</logic:present>

			<!-- --------------------------------------- Deferral New Ends --------------------------------- -->
			
			<!-- --------------------------------------- Deferral New Failed --------------------------------- -->

			<logic:present name="deferralNewFailed">
				<html:form action="/deferralMaker" method="post"
					styleId="deferralMakerForm">
					<html:javascript formName="DeferralMakerDynaValidatorForm"/>
					<fieldset>
						<legend>
							<bean:message key="lbl.deferralDetails" />
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
													styleId="loanNo" value="${deferralNewFailed[0].loanNo}" readonly="true" 
													maxlength="20" />
												<html:button property="loanButton" styleId="loanButton" value=" "
													onclick="openLOVCommon(218,'deferralMakerForm','loanNo','','', '','','generateDeferralValues','customerName');closeLovCallonLov1();" 
													styleClass="lovbutton"> </html:button>
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="${deferralNewFailed[0].lbxLoanNoHID}" />
											</td>
											<td width="25%">
												<bean:message key="lbl.customerName" />
											</td>
											<td width="25%">
												<html:text styleClass="text" property="customerName"
													styleId="customerName" maxlength="50" readonly="true"
													value="${deferralNew[0].customerName}" tabindex="-1" />
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.product" />
											</td>
											<td>
												<html:text styleClass="text" property="product"
													styleId="product" value="${deferralNewFailed[0].product}" 
													maxlength="50" 
													readonly="true" tabindex="-1"/>
											</td>
											<td>
												<bean:message key="lbl.scheme" />
											</td>
											<td>
												<html:text styleClass="text" property="scheme"
													styleId="scheme" maxlength="50" 
													value="${deferralNewFailed[0].scheme}" 
													readonly="true" tabindex="-1"/>
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.disbursedAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursedAmount"
													styleId="disbursedAmount" maxlength="18" 
													value="${deferralNewFailed[0].disbursedAmount}"
													readonly="true" tabindex="-1" style="text-align:right;"/>
											</td>
											<td>
												<bean:message key="lbl.outstandingLoanAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="outstandingLoanAmount" style="text-align:right;"
													styleId="outstandingLoanAmount" maxlength="18" 
													value="${deferralNewFailed[0].outstandingLoanAmount}"
													readonly="true" tabindex="-1" />
											</td>
										</tr>

										<tr>
											<td>
												<bean:message key="lbl.deferralsSinceDsibursal" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="deferralsSinceDsibursal"
													styleId="deferralsSinceDsibursal" 
													value="${deferralNewFailed[0].deferralsSinceDsibursal}" maxlength="3"
													readonly="true" tabindex="-1"/>
											</td>
											<td>
												<bean:message key="lbl.lastDeferralDate" />
											</td>
											<td >
												<html:text styleClass="text" property="lastDeferralDate" 
													styleId="lastDeferralDate" maxlength="10" 
													value="${deferralNewFailed[0].lastDeferralDate}"
													readonly="true" tabindex="-1"/>
											</td>
										</tr>

										<tr>
											<td>
												<bean:message key="lbl.deferralFromInstallment" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text styleClass="text" property="deferralFromInstallment"
													styleId="deferralFromInstallment" 
													value="${deferralNewFailed[0].deferralFromInstallment}" maxlength="3"
													readonly="true" tabindex="-1" style="text-align:right;"/>
												<html:button property="partPaymentDateButton" styleId="partPaymentDateButton" value=" "
													onclick="openLOVCommon(219,'deferralMakerForm','reschDate','loanNo','lbxInstlNo','lbxLoanNoHID','Please Select Loan','','deferralFromInstallment');closeLovCallonLov('loanNo');" 
													styleClass="lovbutton"> 
												</html:button>
												<html:hidden property="lbxInstlNo" styleId="lbxInstlNo" value="" />
											</td>
											<td>
												<bean:message key="lbl.deferredInstallmentNo" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text styleClass="text" property="deferredInstallmentNo"
													styleId="deferredInstallmentNo" 
													value="${deferralNewFailed[0].deferredInstallmentNo}" maxlength="3" />
											</td>
										</tr>

										<tr>
											<td>
												<bean:message key="lbl.instlDate"/>
											</td>
											<td>
												<html:text property="reschDate" styleId="reschDate"
													styleClass="text" readonly="true" tabindex="-1"
													value="${deferralNewFailed[0].reschDate}"/>
											</td>
											<td>
												<bean:message key="lbl.reqRefNo" />
											</td>
											<td>
												<html:text styleClass="text" property="reqRefNo"
													styleId="reqRefNo" 
													value="${deferralNewFailed[0].reqRefNo}" maxlength="10"
													/>
											</td>
										</tr>
										<tr>
											<td>
												 <button type="button"  name="calcInterest" id="calcInterest" 
													onclick="return generateDeferralReschCharges();" title="Alt+C" accesskey="C" 
													class="topformbutton3"><bean:message key="button.calcharge"/></button>
													
											</td>
											<td></td>
											<td>
												<bean:message key="lbl.authorRemarks"/>
											</td>
											<td>
												<textarea name="authorRemarks" id="authorRemarks"
													disabled="disabled" tabindex="-1" class="text"></textarea>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend>
							<bean:message key="lbl.deferralParameters"/>
						</legend>
						<table cellspacing=0 cellpadding=0 width="100%" border=0>
				  			<tr>
				     			<td class="gridtd">
				 	  				<table width="100%" border="0" cellspacing="1" cellpadding="1">
				        				<tr class="white2">
											<td width="10%">
												<b><bean:message key="lbl.interestRateType"/></b>
											</td>
											<td width="10%">
												<b><bean:message key="lbl.baseRateType"/></b>
											</td>
											<td width="8%">
												<b><bean:message key="lbl.baseRate"/></b>
											</td>
											<td width="10%">
												<b><bean:message key="lbl.installmentFreq"/></b>
											</td>
											<td width="10%">
												<b><bean:message key="lbl.installmentType"/></b>
											</td>
											<td width="6%">
												<b><bean:message key="lbl.emi"/></b>
											</td>
											<td width="6%">
												<b><bean:message key="lbl.tenure"/></b>
											</td>
											<td width="8%">
												<b><bean:message key="lbl.maturityDate"/></b>
											</td>
											<td width="8%">
												<b><bean:message key="lbl.nextDueDate"/></b>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
							<td class="gridtd">
								<div id="deferralParams">
										<table width="100%" border="0" cellspacing="1" cellpadding="1">
										<tr class="white1">
											<td width="10%">${deferralNewFailed[0].interestRateType}</td>
							    			<td width="10%">${deferralNewFailed[0].baseRateType}</td>
											<td width="8%">${deferralNewFailed[0].baseRate}</td>
											<td width="10%">${deferralNewFailed[0].installmentFrequency}</td>
											<td width="10%">${deferralNewFailed[0].installmentType}</td>
											<td width="6%">${deferralNewFailed[0].emi}</td>
											<td width="6%">${deferralNewFailed[0].tenure}</td>
											<td width="8%">${deferralNewFailed[0].maturityDate}</td>
											<td width="8%">${deferralNewFailed[0].nextDueDate}</td>
										</tr>
									</table>
								</div>
							</td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend>
							<bean:message key="lbl.chargecalculation"/>
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="1" cellpadding="1">
										<tr>
											<td width="25%">
												<bean:message key="lbl.reschCharges" />
												<font color="red">*</font>
											</td>
											<td width="25%">
												<html:text styleClass="text" property="reschCharges" styleId="reschCharges" value="${deferralNewFailed[0].reschCharges}" maxlength="18" readonly="true" tabindex="-1" 
													onfocus="keyUpNumber(this.value, event, 18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" 
													onkeyup="checkNumber(this.value, event, 18,id);" style="text-align:right;" />
											</td>
											<td width="25%">
												<bean:message key="lbl.deferralReason"/>
												<font color="red">*</font>
											</td>
											<td width="25%">
												<textarea name="deferralReason" id="deferralReason"
													maxlength="1000" class="text">
												${deferralNewFailed[0].deferralReason}	
												</textarea>
											</td>
										</tr>
									</table>
									<button type="button" name="save" id="save" 
										class="topformbutton2" title="Alt+V" accesskey="V"
										onclick="return saveDeferral();"><bean:message key="button.save" /></button>
									<button type="button" name="saveFwd" id="saveFwd" title="Alt+F" accesskey="F"
										class="topformbutton2"
										onclick="return updateDisbursalBeforeSave();"><bean:message key="button.forward" /></button>
									<button type="button" name="generateRepaymentSch"
										id="generateRepaymentSch"
										title="Alt+R" accesskey="R"
										class="topformbutton4" 
										onclick="return viewRepaymentScheduleDisbursal();" ><bean:message key="button.oldrepay" /></button>
									<button type="button" name="generateRepaymentSchNew"
										id="generateRepaymentSchNew"
										title="Alt+N" accesskey="N"
										class="topformbutton4" 
										onclick="javascript:alert('Please Save the Deferral First');" ><bean:message key="button.newrepay" /></button>
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
			</logic:present>

			<!-- --------------------------------------- Deferral New Failed Ends --------------------------------- -->

			<!-- --------------------------------------- Deferral Maker Starts--------------------------------- -->
			
			<logic:present name="deferralData">
				<html:form action="/deferralMaker" method="post"
					styleId="deferralMakerForm">
					<html:javascript formName="DeferralMakerDynaValidatorForm"/>
					<fieldset>
						<legend>
							<bean:message key="lbl.deferralDetails" />
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="hidden" name="businessDate" id="businessDate"
										value="<%=initiationDate%>" />
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
													styleId="loanNo" value="${deferralData[0].loanNo}" 
													readonly="true" maxlength="20" />
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="${deferralData[0].lbxLoanNoHID}" />
											</td>
											<td width="25%">
												<bean:message key="lbl.customerName" />
											</td>
											<td width="25%">
												<html:text styleClass="text" property="customerName"
													styleId="customerName" maxlength="50" disabled="true"
													value="${deferralData[0].customerName}" tabindex="-1" />
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.product" />
											</td>
											<td>
												<html:text styleClass="text" property="product"
													styleId="product" value="${deferralData[0].product}" maxlength="50" 
													disabled="true" tabindex="-1"/>
											</td>
											<td>
												<bean:message key="lbl.scheme" />
											</td>
											<td>
												<html:text styleClass="text" property="scheme"
													styleId="scheme" maxlength="50" value="${deferralData[0].scheme}" 
													disabled="true" tabindex="-1"/>
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.disbursedAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursedAmount"
													styleId="disbursedAmount" maxlength="18" value="${deferralData[0].disbursedAmount}"
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											<td>
												<bean:message key="lbl.outstandingLoanAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="outstandingLoanAmount" style="text-align:right;"
													styleId="outstandingLoanAmount" maxlength="18" value="${deferralData[0].outstandingLoanAmount}"
													readonly="true" tabindex="-1" />
											</td>
										</tr>

										<tr>
											<td>
												<bean:message key="lbl.deferralsSinceDsibursal" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="deferralsSinceDsibursal"
													styleId="deferralsSinceDsibursal" value="${deferralData[0].deferralsSinceDsibursal}" maxlength="3"
													disabled="true" tabindex="-1"/>
											</td>
											<td>
												<bean:message key="lbl.lastDeferralDate" />
											</td>
											<td >
												<html:text styleClass="text" property="lastDeferralDate" 
													styleId="lastDeferralDate" maxlength="10" value="${deferralData[0].lastDeferralDate}"
													disabled="true" tabindex="-1"/>
											</td>
										</tr>

										<tr>
											<td>
												<bean:message key="lbl.deferralFromInstallment" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text styleClass="text" property="deferralFromInstallment"
													styleId="deferralFromInstallment" value="${deferralData[0].deferralFromInstallment}" maxlength="3"
													readonly="true" tabindex="-1" style="text-align:right;"/>
												<html:button property="partPaymentDateButton" styleId="partPaymentDateButton" value=" "
													onclick="openLOVCommon(219,'deferralMakerForm','reschDate','loanNo','lbxInstlNo','lbxLoanNoHID','Please Select Loan','','deferralFromInstallment');closeLovCallonLov('loanNo');" 
													styleClass="lovbutton"> 
												</html:button>
												<html:hidden property="lbxInstlNo" styleId="lbxInstlNo" value="" />
											</td>
											<td>
												<bean:message key="lbl.deferredInstallmentNo" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text styleClass="text" property="deferredInstallmentNo"
													styleId="deferredInstallmentNo" value="${deferralData[0].deferredInstallmentNo}" maxlength="3" />
											</td>
										</tr>

										<tr>
											<td>
												<bean:message key="lbl.instlDate"/>
											</td>
											<td>
												<html:text property="reschDate" styleId="reschDate"
													styleClass="text" readonly="true" tabindex="-1"
													value="${deferralData[0].reschDate}"/>
											</td>
											<td>
												<bean:message key="lbl.reqRefNo" />
											</td>
											<td>
												<html:text styleClass="text" property="reqRefNo"
													styleId="reqRefNo" value="${deferralData[0].reqRefNo}"
													maxlength="10"
													/>
											</td>
										</tr>
										<tr>
											<td>
												 <button type="button"  name="calcInterest" id="calcInterest" 
												onclick="return generateDeferralReschCharges();" title="Alt+C" accesskey="C" 
													class="topformbutton3"><bean:message key="button.calcharge"/></button>
													
											</td>
											<td></td>
											<td>
												<bean:message key="lbl.authorRemarks"/>
											</td>
											<td>
												<textarea name="authorRemarks" id="authorRemarks"
													disabled="disabled" tabindex="-1" class="text">${deferralData[0].authorRemarks}</textarea>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend>
							<bean:message key="lbl.deferralParameters"/>
						</legend>
						<table cellspacing=0 cellpadding=0 width="100%" border=0>
				  			<tr>
				     			<td class="gridtd">
				 	  				<table width="100%" border="0" cellspacing="1" cellpadding="1">
				        				<tr class="white2">
											<td width="10%">
												<b><bean:message key="lbl.interestRateType"/></b>
											</td>
											<td width="10%">
												<b><bean:message key="lbl.baseRateType"/></b>
											</td>
											<td width="8%">
												<b><bean:message key="lbl.baseRate"/></b>
											</td>
											<td width="10%">
												<b><bean:message key="lbl.installmentFreq"/></b>
											</td>
											<td width="10%">
												<b><bean:message key="lbl.installmentType"/></b>
											</td>
											<td width="6%">
												<b><bean:message key="lbl.emi"/></b>
											</td>
											<td width="6%">
												<b><bean:message key="lbl.tenure"/></b>
											</td>
											<td width="8%">
												<b><bean:message key="lbl.maturityDate"/></b>
											</td>
											<td width="8%">
												<b><bean:message key="lbl.nextDueDate"/></b>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
							<td class="gridtd">
								<div id="deferralParams">
										<table width="100%" border="0" cellspacing="1" cellpadding="1">
										<tr class="white1">
											<td width="10%">${deferralData[0].interestRateType}</td>
											<td width="10%">${deferralData[0].baseRateType}</td>
											<td width="8%">${deferralData[0].baseRate}</td>
											<td width="10%">${deferralData[0].installmentFrequency}</td>
											<td width="10%">${deferralData[0].installmentType}</td>
											<td width="6%">${deferralData[0].emi}</td>
											<td width="6%">${deferralData[0].tenure}</td>
											<td width="8%">${deferralData[0].maturityDate}</td>
											<td width="8%">${deferralData[0].nextDueDate}</td>
										</tr>
									</table>
								</div>
							</td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend>
							<bean:message key="lbl.chargecalculation"/>
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="1" cellpadding="1">
										<tr>
											<td width="25%">
												<bean:message key="lbl.reschCharges" />
												<font color="red">*</font>
											</td>
											<td width="25%">
												<html:text styleClass="text" property="reschCharges" styleId="reschCharges" value="${deferralData[0].reschCharges}" maxlength="18" readonly="false" tabindex="-1" 
												onfocus="keyUpNumber(this.value, event, 18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" 
												onkeyup="checkNumber(this.value, event, 18,id);" style="text-align:right;" />
											</td>
											<td width="25%">
												<bean:message key="lbl.deferralReason"/>
												<font color="red">*</font>
											</td>
											<td width="25%">
												<textarea name="deferralReason" id="deferralReason"
													maxlength="1000" class="text">${deferralData[0].deferralReason}</textarea>
											</td>
										</tr>
									</table>
									<button type="button" name="save" id="save" 
										class="topformbutton2" title="Alt+V" accesskey="V"
										onclick="return updateDeferral('P');"><bean:message key="button.save" /></button>
									<button type="button" name="saveFwd" id="saveFwd" title="Alt+F" accesskey="F"
										class="topformbutton2"
										onclick="return updateDeferral('F','<bean:message key="msg.confirmationForwardMsg" />');"><bean:message key="button.forward" /></button>
									<button type="button" name="generateRepaymentSch"
										id="generateRepaymentSch"
										 title="Alt+R" accesskey="R"
										class="topformbutton4" 
										onclick="return viewRepaymentScheduleDisbursal();" ><bean:message key="button.oldrepay" /></button>
									<button type="button" name="generateRepaymentSchNew"
										id="generateRepaymentSchNew"
									 title="Alt+N" accesskey="N"
										class="topformbutton4" 
										onclick="return newRepaymentScheduleDeferral();" ><bean:message key="button.newrepay" /></button>
									<button type="button" name="delete" id="delete" 
										class="topformbutton2" accesskey="D" title="Alt+D"
										onclick="return deleteDeferralDetails();"><bean:message key="button.delete" /></button>
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
			</logic:present>
			
			<!-- --------------------------------------- Deferral Maker Ends --------------------------------- -->

			<!-- --------------------------------------- Deferral Author Starts --------------------------------- -->
			
			<logic:present name="deferralDataAuthor">
				<html:form action="/deferralMaker" method="post"
					styleId="deferralMakerForm">
					<html:javascript formName="DeferralMakerDynaValidatorForm"/>
					<fieldset>
						<legend>
							<bean:message key="lbl.deferralDetails" />
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
													styleId="loanNo" value="${deferralDataAuthor[0].loanNo}" 
													disabled="true" tabindex="-1" maxlength="20" />
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="${deferralDataAuthor[0].lbxLoanNoHID}" />
											</td>
											<td width="25%">
												<bean:message key="lbl.customerName" />
											</td>
											<td width="25%">
												<html:text styleClass="text" property="customerName"
													styleId="customerName" maxlength="50" disabled="true"
													value="${deferralDataAuthor[0].customerName}" tabindex="-1" />
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.product" />
											</td>
											<td>
												<html:text styleClass="text" property="product"
													styleId="product" value="${deferralDataAuthor[0].product}"
													maxlength="50" 
													disabled="true" tabindex="-1"/>
											</td>
											<td>
												<bean:message key="lbl.scheme" />
											</td>
											<td>
												<html:text styleClass="text" property="scheme"
													styleId="scheme" maxlength="50" 
													value="${deferralDataAuthor[0].scheme}" 
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
													value="${deferralDataAuthor[0].disbursedAmount}"
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											<td>
												<bean:message key="lbl.outstandingLoanAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="outstandingLoanAmount" style="text-align:right;"
													styleId="outstandingLoanAmount" maxlength="18" 
													value="${deferralDataAuthor[0].outstandingLoanAmount}"
													disabled="true" tabindex="-1" />
											</td>
										</tr>

										<tr>
											<td>
												<bean:message key="lbl.deferralsSinceDsibursal" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="deferralsSinceDsibursal"
													styleId="deferralsSinceDsibursal" 
													value="${deferralDataAuthor[0].deferralsSinceDsibursal}" maxlength="3"
													disabled="true" tabindex="-1"/>
											</td>
											<td>
												<bean:message key="lbl.lastDeferralDate" />
											</td>
											<td >
												<html:text styleClass="text" property="lastDeferralDate" 
													styleId="lastDeferralDate" maxlength="10" 
													value="${deferralDataAuthor[0].lastDeferralDate}"
													disabled="true" tabindex="-1"/>
											</td>
										</tr>

										<tr>
											<td>
												<bean:message key="lbl.deferralFromInstallment" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text styleClass="text" property="deferralFromInstallment"
													styleId="deferralFromInstallment" 
													value="${deferralDataAuthor[0].deferralFromInstallment}" maxlength="3"
													disabled="true" tabindex="-1" style="text-align:right;"/>
												<html:hidden property="lbxInstlNo" styleId="lbxInstlNo" value="" />
											</td>
											<td>
												<bean:message key="lbl.deferredInstallmentNo" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text styleClass="text" property="deferredInstallmentNo"
													styleId="deferredInstallmentNo" 
													value="${deferralDataAuthor[0].deferredInstallmentNo}" maxlength="3"
													disabled="true" tabindex="-1" />
											</td>
										</tr>

										<tr>
											<td>
												<bean:message key="lbl.instlDate"/>
											</td>
											<td>
												<html:text property="reschDate" styleId="reschDate"
													styleClass="text" tabindex="-1"
													value="${deferralDataAuthor[0].reschDate}"
													disabled="true" tabindex="-1" />
											</td>
											<td>
												<bean:message key="lbl.reqRefNo" />
											</td>
											<td>
												<html:text styleClass="text" property="reqRefNo"
													styleId="reqRefNo" value="${deferralDataAuthor[0].reqRefNo}"
													maxlength="10"
													disabled="true" tabindex="-1" />
											</td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td>
												<bean:message key="lbl.authorRemarks"/>
											</td>
											<td>
												<textarea name="authorRemarks" id="authorRemarks"
													disabled="disabled" tabindex="-1" class="text">${deferralDataAuthor[0].authorRemarks}</textarea>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend>
							<bean:message key="lbl.deferralParameters"/>
						</legend>
						<table cellspacing=0 cellpadding=0 width="100%" border=0>
				  			<tr>
				     			<td class="gridtd">
				 	  				<table width="100%" border="0" cellspacing="1" cellpadding="1">
				        				<tr class="white2">
											<td width="10%">
												<b><bean:message key="lbl.interestRateType"/></b>
											</td>
											<td width="10%">
												<b><bean:message key="lbl.baseRateType"/></b>
											</td>
											<td width="8%">
												<b><bean:message key="lbl.baseRate"/></b>
											</td>
											<td width="10%">
												<b><bean:message key="lbl.installmentFreq"/></b>
											</td>
											<td width="10%">
												<b><bean:message key="lbl.installmentType"/></b>
											</td>
											<td width="6%">
												<b><bean:message key="lbl.emi"/></b>
											</td>
											<td width="6%">
												<b><bean:message key="lbl.tenure"/></b>
											</td>
											<td width="8%">
												<b><bean:message key="lbl.maturityDate"/></b>
											</td>
											<td width="8%">
												<b><bean:message key="lbl.nextDueDate"/></b>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
							<td class="gridtd">
								<div id="deferralParams">
										<table width="100%" border="0" cellspacing="1" cellpadding="1">
										<tr class="white1">
											<td width="10%">${deferralDataAuthor[0].interestRateType}</td>
											<td width="10%">${deferralDataAuthor[0].baseRateType}</td>
											<td width="8%">${deferralDataAuthor[0].baseRate}</td>
											<td width="10%">${deferralDataAuthor[0].installmentFrequency}</td>
											<td width="10%">${deferralDataAuthor[0].installmentType}</td>
											<td width="6%">${deferralDataAuthor[0].emi}</td>
											<td width="6%">${deferralDataAuthor[0].tenure}</td>
											<td width="8%">${deferralDataAuthor[0].maturityDate}</td>
											<td width="8%">${deferralDataAuthor[0].nextDueDate}</td>
										</tr>
									</table>
								</div>
							</td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend>
							<bean:message key="lbl.chargecalculation"/>
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="1" cellpadding="1">
										<tr>
											<td width="25%">
												<bean:message key="lbl.reschCharges" />
												<font color="red">*</font>
											</td>
											<td width="25%">
												<html:text styleClass="text" property="reschCharges"
													styleId="reschCharges" value="${deferralDataAuthor[0].reschCharges}" 
													maxlength="18"
													disabled="true" tabindex="-1" style="text-align:right;" />
											</td>
											<td width="25%">
												<bean:message key="lbl.deferralReason"/>
												<font color="red">*</font>
											</td>
											<td width="25%">
												<textarea name="deferralReason" id="deferralReason"
													maxlength="1000" class="text"
													disabled="disabled" tabindex="-1">${deferralDataAuthor[0].deferralReason}</textarea>
											</td>
										</tr>
									</table>
									<button type="button" name="generateRepaymentSch"
										id="generateRepaymentSch"
										 title="Alt+R" accesskey="R"
										class="topformbutton4" 
										onclick="return viewRepaymentScheduleDisbursal();" ><bean:message key="button.oldrepay" /></button>
									<button type="button" name="generateRepaymentSchNew"
										id="generateRepaymentSchNew"
										 title="Alt+N" accesskey="N"
										class="topformbutton4" 
										onclick="return newRepaymentScheduleDeferral();" > <bean:message key="button.newrepay" /></button>
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
			</logic:present>
			
			<!-- --------------------------------------- Deferral Author Ends --------------------------------- -->

		</div>
	</div>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<script>
	setFramevalues("deferralMakerForm");
</script>
</body>
</html:html>

<logic:present name="delete">
<script type="text/javascript">
		alert("<bean:message key="lbl.dataDeleted" />");
		window.location = "<%=request.getContextPath()%>/deferralSearch.do?method=deferralMakerSearch";	
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
	window.location = "<%=request.getContextPath()%>/deferralSearch.do?method=deferralMakerSearch";
}
else if("<%=request.getAttribute("message")%>"=="E")
{
	alert("<bean:message key="msg.DataNotSaved" />");
	window.location = "<%=request.getContextPath()%>/deferralSearchBehind.do?method=deferralMakerSearch";
}
else if("<%=request.getAttribute("message")%>"=="maxDefrMnthAllwd")
{
	alert("<bean:message key="msg.maxDefrMnthAllwd" />");
}
else if("<%=request.getAttribute("message")%>"=="maxDefrMnthTotal")
{
	alert("<bean:message key="msg.maxDefrMnthTotal" />");
}

</script>
</logic:present>