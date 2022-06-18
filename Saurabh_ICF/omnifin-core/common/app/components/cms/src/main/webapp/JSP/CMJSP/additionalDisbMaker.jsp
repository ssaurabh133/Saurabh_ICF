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
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormat=resource.getString("lbl.dateFormat");

		String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":"
					+ request.getServerPort() + path + "/";

			UserObject userobj = (UserObject) session
					.getAttribute("userobject");
			
			String initiationDate = userobj.getBusinessdate();
			session.setAttribute("bussDate",initiationDate);
			session.setAttribute("format",dateFormat);
	%>


	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />

	<script type="text/javascript" 
		src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cmScript/additionalDisbJS.js"></script>
		
	
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
    	 document.getElementById('addDisbMakerForm').reqRefNo.focus();
     }
     else if(document.getElementById('modifyRecord').value =='N')
     {
    	document.getElementById('addDisbMakerForm').loanButton.focus();
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
<body onunload="closeAllLovCallUnloadBody();" onload="enableAnchor();checkChanges('addDisbMakerForm');fntab();init_fields();">

	<input type="hidden" id="bDate" name="bDate" value="${userobject.businessdate }"/>
	<input type="hidden" name="formatD" id="formatD"
		value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
	<div id=centercolumn>
		<div id=revisedcontainer>

			<!-- --------------------------------------- Additional Disbursal Maker New --------------------------------- -->

			<logic:present name="newDisbMaker">
				<html:form action="/addDisbursalMakerAction" method="post"
					styleId="addDisbMakerForm">
				
					<fieldset>
						<legend>
							<bean:message key="lbl.addDisbDetails" />
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="hidden" name="businessDate" id="businessDate"
										value="<%=initiationDate%>" />
									<html:hidden property="installmentType" styleId="installmentType"
										value="" styleClass="text"/>
									<input type="hidden" id="modifyRecord" name="modifyRecord" value="N"/>

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
													onclick="openLOVCommon(317,'addDisbMakerForm','loanNo','','', '','','generateAdditionalDisbValues','hid');closeLovCallonLov1();" 
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
													styleId="customerName" maxlength="50" disabled="true"
													value="" tabindex="-1" />
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.product" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="product"
													styleId="product" value="" maxlength="50" 
													disabled="true" tabindex="-1"/>
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.scheme" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="scheme"
													styleId="scheme" maxlength="50" value="" 
													disabled="true" tabindex="-1"/>
											</td>
										</tr>
										
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.sanctionedAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="sanctionedAmount"
													styleId="sanctionedAmount" maxlength="18" value=""
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.utilizedAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="utilizedAmount" style="text-align:right;"
													styleId="utilizedAmount" maxlength="18" value=""
													readonly="true" tabindex="-1" />
											</td>
										</tr>
										
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.disbursedAmt" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursedAmount"
													styleId="disbursedAmount" maxlength="18" value=""
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											<td nowrap="nowrap">
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
											<td nowrap="nowrap">
												<bean:message key="lbl.nextDueDate" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="nextDueDate"
													styleId="nextDueDate" value="" maxlength="10"
													readonly="true" tabindex="-1"/>
											</td>
											
												<td nowrap="nowrap">
												<bean:message key="lbl.reqRefNo" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="reqRefNo"
													styleId="reqRefNo" value="" maxlength="10"
													/>
											</td>
										</tr>

										<tr>
											<td>
												<bean:message key="lbl.disburseDate" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text styleClass="text" property="disbursalDate" 
													styleId="disbursalDate" maxlength="10" 
												 	value="${userobject.businessdate }" readonly="true" />
											<%-- 	<html:button property="partPaymentDateButton" styleId="partPaymentDateButton" value=" "
													onclick="openLOVCommon(219,'addDisbMakerForm','partPaymentDate','loanNo','lbxInstlNo','lbxLoanNoHID','Please Select Loan','','hid');closeLovCallonLov('loanNo');" 
													styleClass="lovbutton"> 
												</html:button>
												--%>
												<html:hidden property="lbxInstlNo" styleId="lbxInstlNo" value="" />
												
											</td>
											<td>
												<bean:message key="lbl.disbAmount"/>
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursalAm"
													styleId="disbursalAm" value="0"
													onkeypress="return numbersonly(event,id,18)"
													onblur="formatNumber(this.value,id); generateReschCharges();"
													onkeyup="checkNumber(this.value, event, 18,id);"
													onfocus="keyUpNumber(this.value, event, 18,id);"
													style="text-align:right;"/>
											</td>
										</tr>

										<tr>
											
											<!-- amandeep work starts for rescheduling method -->
											
											 <td>
											<bean:message key="lbl.rescheduleParameter" /><font color="red">*</font>
												</td>
												<td>
												    <div id="intsTypeVar">
													<html:select styleClass="text" property="rescheduleParameter"
														styleId="rescheduleParameter" value="" >
														 <html:option value="">--Select--</html:option>
														<html:option value="T">Change Tenure</html:option>
														<html:option value="E">Change EMI</html:option>  
														<html:option value="F">FIFO</html:option>
														<html:option value="A">EQUAL DISTRIBUTION ACTUAL</html:option>  
														<html:option value="O">EQUAL DISTRIBUTION ORIGNAL</html:option>
													</html:select>
													</div> 
												</td>
											
											<!-- amandeep work ends for rescheduling method -->
											<td nowrap="nowrap">
												<bean:message key="lbl.reschedulCharges" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="reschCharges" styleId="reschCharges" value="0" readonly="false"  tabindex="-1"
													onfocus="keyUpNumber(this.value, event, 18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" 
													onkeyup="checkNumber(this.value, event, 18,id);" style="text-align:right;"/>
												<input type="hidden" name="interestForGapPeriod"
													id="interestForGapPeriod" value=""/>
											</td>
											<!-- comment by amandeep -->
											<%-- <html:hidden styleClass="text" property="rescheduleParameter"
													styleId="rescheduleParameter" value="T" /> --%>
										<%-- <td>
											<bean:message key="lbl.rescheduleParameter" />
												</td>
												<td>
												   
													<html:select styleClass="text" property="rescheduleParameter"
														styleId="rescheduleParameter" value="" disabled="true"
														onchange="changeInstlDatePartPayment();">
														<html:option value="T">Keep Same Plan</html:option>
														<html:option value="I">Change Plan</html:option>
													</html:select>
												</td>
										--%>
										
										
											
										</tr>
										<tr>
											
											
											<td nowrap="nowrap">
												<bean:message key="lbl.reschReason" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:textarea property="reschReason"
													styleId="reschReason" value="" styleClass="text"></html:textarea>
											</td>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.authorRemarks" />
											</td>
										
											<td nowrap="nowrap">
												<html:textarea styleClass="text"
													property="authorRemarks"
													styleId="authorRemarks" 
													disabled="true"
													value="" />
											</td>
										</tr>
									</table>
									
									
									<button type="button" name="save" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveAdditionalDisbursal();"><bean:message key="button.save" /></button>
									
									<button type="button" name="saveFwd" id="saveFwd" class="topformbutton2" title="Alt+F" accesskey="F" onclick="return additionalDisbursalBeforeSave();"><bean:message key="button.forward" /></button>
									
									<button type="button" name="generateInstallmentPlan" id="generateInstallmentPlan" class="topformbutton4" title="Alt+O" accesskey="O" onclick="return viewInstallmentPlanAddtionalDisbursal();"><bean:message key="button.oldinstplan" /></button>
									
									<button type="button" name="generateRepaymentSch" id="generateRepaymentSch" class="topformbutton4" title="Alt+R" accesskey="R" onclick="return viewRepaymentScheduleAdditionDisbursal();"><bean:message key="button.oldrepay" /></button>
									
									
									<%-- <button type="button" name="generateInstallmentPlanNew" id="generateInstallmentPlanNew" class="topformbutton4" title="Alt+I" accesskey="I" onclick="javascript:alert('Please Save the Additional Disbursal First');"><bean:message key="button.newinstplan" /></button>  --%>
									
									<button type="button" name="generateRepaymentSchNew" id="generateRepaymentSchNew" class="topformbutton4" title="Alt+N" accesskey="N" onclick="javascript:alert('Please Save the Additional Disbursal First');"><bean:message key="button.newrepay" /></button>
									
									
									<button type="button" name="recButton" id="recButton" class="topformbutton3" title="Alt+V" accesskey="V" onclick="return viewReceivableAdditionDisbursal('Please Save the Additional Disbursal First');"><bean:message key="button.viewRecievable1" /></button>
									
							
								
									
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
			</logic:present>


			<!-- --------------------------------------- Additional Disbursal New Ends --------------------------------- -->

			<!-- --------------------------------------- Additional Disbursal Update Maker Starts--------------------------------- -->


			<logic:present name="addDisbursalData">
				<html:form action="/addDisbursalMakerAction" method="post"
					styleId="addDisbMakerForm">
					<fieldset>
						<legend>
							<bean:message key="lbl.addDisbDetails" />
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="hidden" name="businessDate" id="businessDate"
										value="<%=initiationDate%>" />
									<html:hidden property="installmentType" styleId="installmentType"
										value="${addDisbursalData[0].installmentType}" styleClass="text"/>
									<html:hidden property="reschId" styleId="reschId"
										value="${requestScope.reschId}" styleClass="text"/>
									<input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
									
									<html:hidden property="lbxInstlNo" styleId="lbxInstlNo" value="${addDisbursalData[0].lbxInstlNo}" />
									
									<table width="100%" border="0" cellspacing="1" cellpadding="1">
										
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.loanNo" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="loanNo"
													styleId="loanNo" value="${addDisbursalData[0].loanNo}"
													readonly="true" maxlength="20" />
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="${addDisbursalData[0].lbxLoanNoHID}" />
													
										
													
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.customerName" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="customerName"
													styleId="customerName" maxlength="50" disabled="true"
													value="${addDisbursalData[0].customerName}" tabindex="-1" />
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.product" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="product"
													styleId="product" value="${addDisbursalData[0].product}" maxlength="50" 
													disabled="true" tabindex="-1"/>
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.scheme" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="scheme"
													styleId="scheme" maxlength="50" value="${addDisbursalData[0].scheme}" 
													disabled="true" tabindex="-1"/>
											</td>
										</tr>
										
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.sanctionedAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="sanctionedAmount"
													styleId="sanctionedAmount" maxlength="18" value="${addDisbursalData[0].sanctionedAmount}"
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.utilizedAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="utilizedAmount" style="text-align:right;"
													styleId="utilizedAmount" maxlength="18" value="${addDisbursalData[0].utilizedAmount}"
													readonly="true" tabindex="-1" />
											</td>
										</tr>
										
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.disbursedAmt" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursedAmount"
													styleId="disbursedAmount" maxlength="18" value="${addDisbursalData[0].disbursedAmount}"
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.outstandingLoanAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="outstandingLoanAmount" style="text-align:right;"
													styleId="outstandingLoanAmount" maxlength="18" value="${addDisbursalData[0].outstandingLoanAmount}"
													readonly="true" tabindex="-1" />
											</td>
										</tr>

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.nextDueDate" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="nextDueDate"
													styleId="nextDueDate" value="${addDisbursalData[0].nextDueDate}" maxlength="10"
													readonly="true" tabindex="-1"/>
											</td>
											
												<td nowrap="nowrap">
												<bean:message key="lbl.reqRefNo" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="reqRefNo" readonly="true"
													styleId="reqRefNo" value="${addDisbursalData[0].reqRefNo}" 
													 maxlength="10"/>
											</td>
											
										</tr>

										<tr>
											
										
										</tr>

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.disburseDate" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursalDate" tabindex="-1"
													styleId="disbursalDate" maxlength="10" readonly="true"
													value="${addDisbursalData[0].disbursalDate}" />
											
											</td>
											<td>
												<bean:message key="lbl.disbAmount"/>
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursalAm"
													styleId="disbursalAm" value="${addDisbursalData[0].disbursalAm}"
													onkeypress="return numbersonly(event,id,18)"
													onblur="formatNumber(this.value,id); generateReschCharges();"
													onkeyup="checkNumber(this.value, event, 18,id);"
													onfocus="keyUpNumber(this.value, event, 18,id);" 
													style="text-align:right;"/>
											</td>
										</tr>

										<tr>
										
										
										<!-- amandeep work starts for rescheduling method -->
											
											 <td>
											<bean:message key="lbl.rescheduleParameter" /><font color="red">*</font>
												</td>
												<td>
												   
													<html:select styleClass="text" property="rescheduleParameter"
														styleId="rescheduleParameter" value="${addDisbursalData[0].rescheduleParameter}" >
														 <html:option value="">--Select--</html:option>
														<logic:present name="genericMasterList">
														<logic:notEmpty name="genericMasterList">
														<html:optionsCollection name="genericMasterList" label="name" value="id" />
														</logic:notEmpty>
														
														</logic:present>
													</html:select>
												</td>
											
											<!-- amandeep work ends for rescheduling method -->
											<td nowrap="nowrap">
												<bean:message key="lbl.reschedulCharges" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="reschCharges" styleId="reschCharges" value="${addDisbursalData[0].reschCharges}" readonly="false"  tabindex="-1"
													onfocus="keyUpNumber(this.value, event, 18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" 
													onkeyup="checkNumber(this.value, event, 18,id);" style="text-align:right;"/>
											</td>
											
											<%-- <html:hidden styleClass="text" property="rescheduleParameter"
													styleId="rescheduleParameter" value="T" /> --%>
											<%-- 
											<td>
												<bean:message key="lbl.rescheduleParameter" />
											</td>
											<td>
											<logic:iterate name="addDisbursalData" id="obj">
													<logic:equal name="obj" property="installmentType" value="I">
													<html:select styleClass="text" property="rescheduleParameter"
														styleId="rescheduleParameter" value="${addDisbursalData[0].rescheduleParameter}"
														onchange="changeInstlDatePartPayment();">
														<html:option value="T">Keep Same Plan</html:option>
														<html:option value="I">Change Plan</html:option>
													</html:select>
													</logic:equal>
													<logic:equal name="obj" property="installmentType" value="N">
													<html:select styleClass="text" property="rescheduleParameter" disabled="true"
														styleId="rescheduleParameter" value="${addDisbursalData[0].rescheduleParameter}"
														onchange="changeInstlDatePartPayment();">
														<html:option value="T">Keep Same Plan</html:option>
														<html:option value="I">Change Plan</html:option>
													</html:select>
													</logic:equal>
												</logic:iterate>
											</td>
											--%>
											
											
											
										</tr>
									
										<tr>
										
										
										
										<td nowrap="nowrap">
												<bean:message key="lbl.reschReason" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:textarea property="reschReason"
													styleId="reschReason" value="${addDisbursalData[0].reschReason}" styleClass="text"></html:textarea>
											</td>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.authorRemarks" />
											</td>
										
											<td nowrap="nowrap">
												<html:textarea styleClass="text"
													property="authorRemarks"
													styleId="authorRemarks" 
													disabled="true"
													value="${addDisbursalData[0].authorRemarks}" />
											</td>
										</tr>
									
									</table>
									
									<button type="button" name="save" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveAdditionalDisbursal();"><bean:message key="button.save" /></button>
									
									<button type="button" name="saveFwd" id="saveFwd" class="topformbutton2" title="Alt+F" accesskey="F" onclick="return forwardAdditionalDisbData();"><bean:message key="button.forward" /></button>
									
									
									<button type="button" name="generateInstallmentPlan" id="generateInstallmentPlan" class="topformbutton4" title="Alt+O" accesskey="O" onclick="return viewInstallmentPlanAddtionalDisbursal();"><bean:message key="button.oldinstplan" /></button>
									
									<button type="button" name="generateRepaymentSch" id="generateRepaymentSch" class="topformbutton4" title="Alt+R" accesskey="R" onclick="return viewRepaymentScheduleAdditionDisbursal();"><bean:message key="button.oldrepay" /></button>
									
									
									<%-- 						
									
									<logic:iterate name="addDisbursalData" id="sublist">
									<logic:equal name="sublist" property="rescheduleParameter" value="T">
									       <button type="button" name="generateInstallmentPlanNew" id="generateInstallmentPlanNew" class="topformbutton4" title="Alt+I" accesskey="I" ><bean:message key="button.newinstplan" /></button>
									</logic:equal>
									<logic:equal name="sublist" property="rescheduleParameter" value="I">
									
									    <button type="button" name="generateInstallmentPlanNew" id="generateInstallmentPlanNew" class="topformbutton4" title="Alt+I" accesskey="I" onclick="return generateInstallmentPlanAdditionalDisbursal('P');"><bean:message key="button.newinstplan" /></button>
									
									</logic:equal>
									</logic:iterate>
									
									--%>
									<button type="button" name="generateRepaymentSchNew" id="generateRepaymentSchNew" class="topformbutton4" title="Alt+N" accesskey="N" onclick="return newRepaymentScheduleAdditionalDisbursal();"><bean:message key="button.newrepay" /></button>
									
									
									<button type="button" name="recButton" id="recButton" class="topformbutton3" title="Alt+V" accesskey="V" onclick="return viewReceivableAdditionDisbursal('Please Save the Additional Disbursal First');"><bean:message key="button.viewRecievable1" /></button>
									<button type="button" name="delete" id="delete" class="topformbutton2" accesskey="D" title="Alt+D"	onclick="return deleteAdditionalDisbDetails();"><bean:message key="button.delete" /></button>
															
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
				
				
				<logic:present name="fwdStatus">
					<script type="text/javascript">
					if("<%=request.getAttribute("fwdStatus")%>"=="S")
					{
						alert("<bean:message key="msg.ForwardSuccessfully" />");
						window.location = "<%=request.getContextPath()%>/additionalDisbursalMaker.do?method=searchAdditionalDisbursalMaker";
					}
					else if("<%=request.getAttribute("fwdStatus")%>"=="E")
					{
						
						alert("<bean:message key="msg.ForwardNotSuccessfully" />");
						window.location = "<%=request.getContextPath()%>/additionalDisbursalMaker.do?method=searchAdditionalDisbursalMaker";
						
					}
					</script>
              </logic:present>
				
			</logic:present>

			<!-- --------------------------------------- Additional Disbursal   Maker Ends --------------------------------- -->

			<!-- --------------------------------------- Additional Disbursal Author  Starts --------------------------------- -->

			<logic:present name="additionDisbDataAuthor">
				<html:form action="/addDisbursalMakerAction" method="post"
					styleId="addDisbMakerForm">
					<fieldset>
						<legend>
							<bean:message key="lbl.addDisbDetails" />
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="hidden" name="businessDate" id="businessDate"
										value="<%=initiationDate%>" />
									<html:hidden property="installmentType" styleId="installmentType"
										value="${additionDisbDataAuthor[0].installmentType}" styleClass="text"/>
									<html:hidden property="reschId" styleId="reschId"
										value="${sessionScope.reschId}" styleClass="text"/>
										
										<html:hidden property="lbxInstlNo" styleId="lbxInstlNo" value="${additionDisbDataAuthor[0].lbxInstlNo}" />

									<table width="100%" border="0" cellspacing="1" cellpadding="1">
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.loanNo" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="loanNo"
													styleId="loanNo" value="${additionDisbDataAuthor[0].loanNo}"
													disabled="true" maxlength="20" />
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="${additionDisbDataAuthor[0].lbxLoanNoHID}" />
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.customerName" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="customerName"
													styleId="customerName" maxlength="50" disabled="true"
													value="${additionDisbDataAuthor[0].customerName}" tabindex="-1" />
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.product" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="product"
													styleId="product" value="${additionDisbDataAuthor[0].product}" maxlength="50" 
													disabled="true" tabindex="-1"/>
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.scheme" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="scheme"
													styleId="scheme" maxlength="50" value="${additionDisbDataAuthor[0].scheme}" 
													disabled="true" tabindex="-1"/>
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.disbursedAmt" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursedAmount"
													styleId="disbursedAmount" maxlength="18" value="${additionDisbDataAuthor[0].disbursedAmount}"
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.outstandingLoanAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="outstandingLoanAmount" style="text-align:right;"
													styleId="outstandingLoanAmount" maxlength="18" value="${additionDisbDataAuthor[0].outstandingLoanAmount}"
													disabled="true" tabindex="-1" />
											</td>
										</tr>

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.nextDueDate" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="nextDueDate"
													styleId="nextDueDate" value="${additionDisbDataAuthor[0].nextDueDate}" maxlength="10"
													disabled="true" tabindex="-1"/>
											</td>
												<td nowrap="nowrap">
												<bean:message key="lbl.reqRefNo" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="reqRefNo" disabled="true"
													styleId="reqRefNo" value="${additionDisbDataAuthor[0].reqRefNo}" 
													 maxlength="10"/>
											</td>
											
										</tr>

									
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.disburseDate" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursalDate" tabindex="-1"
													styleId="disbursalDate" maxlength="10" disabled="true"
													value="${additionDisbDataAuthor[0].disbursalDate}" />
											
											</td>
											<td>
												<bean:message key="lbl.disbAmount"/>
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursalAm" disabled="true"
													styleId="disbursalAm" value="${additionDisbDataAuthor[0].disbursalAm}"
													onkeypress="return numbersonly(event,id,18)"
													onblur="formatNumber(this.value,id); generateReschCharges();"
													onkeyup="checkNumber(this.value, event, 18,id);"
													onfocus="keyUpNumber(this.value, event, 18,id);" 
													style="text-align:right;"/>
											</td>
											
										</tr>

										<tr>
										
										
										<!-- amandeep work starts for rescheduling method -->
											
											 <td>
											<bean:message key="lbl.rescheduleParameter" /><font color="red">*</font>
												</td>
												<td>
												   
													<html:select styleClass="text" property="rescheduleParameter"
														styleId="rescheduleParameter" disabled="true" value="${additionDisbDataAuthor[0].rescheduleParameter}">
														 <html:option value="">--Select--</html:option>
														<logic:present name="genericMasterList">
														<logic:notEmpty name="genericMasterList">
														<html:optionsCollection name="genericMasterList" label="name" value="id" />
														</logic:notEmpty>
														
														</logic:present>
													</html:select>
												</td>
											
											<!-- amandeep work ends for rescheduling method -->
											<td>
												<bean:message key="lbl.reschedulCharges" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text styleClass="text" property="reschCharges"
													styleId="reschCharges" value="${additionDisbDataAuthor[0].reschCharges}" 
													style="text-align:right;" disabled="true"/>
											</td>
											
											<%-- <html:hidden styleClass="text" property="rescheduleParameter"
													styleId="rescheduleParameter" value="T" /> --%>
										<%-- 	
											<td>
												<bean:message key="lbl.rescheduleParameter" />
											</td>
											<td>
												<html:select styleClass="text" property="rescheduleParameter" disabled="true"
													styleId="rescheduleParameter" value="${additionDisbDataAuthor[0].rescheduleParameter}"
													onchange="changeInstlDatePartPayment();">
													<html:option value="T">Keep Same Plan</html:option>
													<html:option value="I">Change Plan</html:option>
												</html:select>
											</td>
											--%>
											
											
											
										</tr>
										<tr>
										
										
										<td>
												<bean:message key="lbl.reschReason" />
												<font color="red">*</font>
											</td>
											<td>
												<html:textarea property="reschReason" disabled="true"
													styleId="reschReason" value="${additionDisbDataAuthor[0].reschReason}" styleClass="text"></html:textarea>
											</td>
											
											<td>
												<bean:message key="lbl.authorRemarks" />
											</td>
										
											<td><html:textarea styleClass="text"
													property="authorRemarks"
													styleId="authorRemarks" 
													disabled="true"
													value="${additionDisbDataAuthor[0].authorRemarks}" />
											</td>
										</tr>
									</table>
									
									
																	
									<button type="button" name="generateInstallmentPlan" id="generateInstallmentPlan" class="topformbutton4" title="Alt+O" accesskey="O" onclick="return viewInstallmentPlanAddtionalDisbursal();"><bean:message key="button.oldinstplan" /></button>
									
									<button type="button" name="generateRepaymentSch" id="generateRepaymentSch" class="topformbutton4" title="Alt+R" accesskey="R" onclick="return viewRepaymentScheduleAdditionDisbursal();"><bean:message key="button.oldrepay" /></button>
									
									
								
									
								<%-- 
									<logic:iterate name="additionDisbDataAuthor" id="sublist">
									<logic:equal name="sublist" property="rescheduleParameter" value="T">
									     <button type="button" name="generateInstallmentPlanNew" id="generateInstallmentPlanNew" class="topformbutton4" title="Alt+I" accesskey="I" ><bean:message key="button.newinstplan" /></button>
									</logic:equal>
									<logic:equal name="sublist" property="rescheduleParameter" value="I">
									
									 <button type="button" name="generateInstallmentPlanNew" id="generateInstallmentPlanNew" class="topformbutton4" title="Alt+I" accesskey="I" onclick="return generateInstallmentPlanAdditionalDisbursal('F');"><bean:message key="button.newinstplan" /></button>
									
									</logic:equal>
									</logic:iterate>
									--%>
									<button type="button" name="generateRepaymentSchNew" id="generateRepaymentSchNew" class="topformbutton4" title="Alt+N" accesskey="N" onclick="return newRepaymentScheduleAdditionalDisbursal();"><bean:message key="button.newrepay" /></button>
									
									
									<button type="button" name="recButton" id="recButton" class="topformbutton3" title="Alt+V" accesskey="V" onclick="return viewReceivableAdditionDisbursal('Please Save the Additional Disbursal First');"><bean:message key="button.viewRecievable1" /></button>
											
									
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
			</logic:present>

			<!-- --------------------------------------- Additional Disbursal Author Ends --------------------------------- -->

		</div>
	</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<script>
	setFramevalues("addDisbMakerForm");
</script>	
</body>
</html:html>
<logic:present name="delete">
<script type="text/javascript">
		alert("<bean:message key="lbl.dataDeleted" />");
		window.location = "<%=request.getContextPath()%>/additionalDisbursalMaker.do?method=searchAdditionalDisbursalMaker";	
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
else if("<%=request.getAttribute("message")%>"=="E")
{
	
	alert("<%=request.getAttribute("msg")%>");
	
}
</script>
</logic:present>




<logic:present name="nonInstallmentBased">
	<script type="text/javascript">
		alert("<bean:message key="msg.nonInstallmentBased" />");
	</script>
</logic:present>