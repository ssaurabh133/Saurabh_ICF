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
	
		 <logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
	
 <!-- css and jquery for Datepicker -->
	
	<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
	
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/cmLoanInitjs.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
	<script type="text/javascript" src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script> 	
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency-1.3.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency.all.js"></script>
	
	   
		
			
		
<script type="text/javascript">
	function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('partPrepaymentMakerForm').reqRefNo.focus();
     }
     else if(document.getElementById('modifyRecord').value =='N')
     {
    	document.getElementById('partPrepaymentMakerForm').loanButton.focus();
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
<body onclick="parent_disable();" onunload="closeAllLovCallUnloadBody();" onload="enableAnchor();checkChanges('partPrepaymentMakerForm');fntab();init_fields();">

	<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
	<input type="hidden" id="bDate" name="bDate" value="${userobject.businessdate }"/>
		<logic:present name="image">
    	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
        </logic:present>
    	<logic:notPresent name="image">
    	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    	</logic:notPresent>
    	<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
		<input type="hidden" name="maxInstallmentDate" id="maxInstallmentDate" value="${partPrePaymentData[0].maxInstallmentDate}" />
		
		<div id=centercolumn>
		<div id=revisedcontainer>
		<input type="hidden" id="contextPath" name="contextPath" value="<%=path %>"/>
		<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" >
		
	
			<!-- --------------------------------------- Part Prepayment New --------------------------------- -->

			<logic:present name="partPrepaymentNew">
				<html:form action="/partPrePaymentMaker" method="post"
					styleId="partPrepaymentMakerForm">
					<html:javascript
						formName="PartPrePaymentMakerDynaValidatorForm" />
					<fieldset>
						<legend>
							<bean:message key="lbl.partPrePaymentDetails" />
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

										<tr>	<td><html:hidden property="realize" styleId="realize" value=""/></td>
												<td><html:hidden property="realizeFlag" styleId="realizeFlag" value="${sessionScope.realizeFlag}" /></td></tr>
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.loanNo" />
												<font color="red">*</font>
												<html:hidden styleClass="text"  styleId="amount" property="amount" value="${partPrePaymetList[0].amount}" />
											</td>
											
											<td nowrap="nowrap">
												<html:text styleClass="text" property="loanNo"
													styleId="loanNo" value="" readonly="true" maxlength="20" />
												<html:button property="loanButton" styleId="loanButton" value=" "
													onclick="openLOVCommon(179,'partPrepaymentMakerForm','loanNo','','', '','','generatePartPrepaymentValues','hid');closeLovCallonLov1();" 
													styleClass="lovbutton"> </html:button>
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="" />
												<html:hidden property="hid" styleId="hid" value="" />
												
												
												<input type="hidden" name="partPrePaymentCal"
													value="" id="partPrePaymentCal" />
												
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
												<bean:message key="lbl.disbursedAmount" />
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
													styleId="partNextDueDate" value="" maxlength="10"
													readonly="true" tabindex="-1"/>
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.partPaymentSinceDisbursal" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="partPaymentSinceDisbursal" style="text-align:right;"
													styleId="partPaymentSinceDisbursal" maxlength="10" value=""
													disabled="true" tabindex="-1"/>
											</td>
										</tr>

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.lastPartPaymentDate" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="lastPartPaymentDate"
													styleId="lastPartPaymentDate" value="" maxlength="10"
													disabled="true" tabindex="-1"/>
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
												<bean:message key="lbl.partPaymentDate" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text styleClass="text" property="partPaymentDate" 
													styleId="partPaymentDate" maxlength="10" 
												 	value="" readonly="true" />
											<%-- 	<html:button property="partPaymentDateButton" styleId="partPaymentDateButton" value=" "
													onclick="openLOVCommon(219,'partPrepaymentMakerForm','partPaymentDate','loanNo','lbxInstlNo','lbxLoanNoHID','Please Select Loan','','hid');closeLovCallonLov('loanNo');" 
													styleClass="lovbutton"> 
												</html:button>
												--%>
												<html:hidden property="lbxInstlNo" styleId="lbxInstlNo" value="" />
												
											</td>
											<td>
												<bean:message key="lbl.partPaymentAmount"/>
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="partPaymentAmount"
													styleId="partPaymentAmount" value=""
													onkeypress="return numbersonly(event,id,18)"
													onblur="formatNumber(this.value,id); generateReschCharges();"
													onkeyup="checkNumber(this.value, event, 18,id);"
													onfocus="keyUpNumber(this.value, event, 18,id);"
													style="text-align:right;"/>
											</td>
										</tr>

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.partPaymentCharges" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="reschCharges" styleId="reschCharges" value="0" readonly="false" tabindex="-1" 
												onfocus="keyUpNumber(this.value, event, 18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" 
												onkeyup="checkNumber(this.value, event, 18,id);" style="text-align:right;"/>
												<input type="hidden" name="interestForGapPeriod"
													id="interestForGapPeriod" value=""/>
											</td>
											
											<%-- <html:hidden styleClass="text" property="partPaymentParameter"
													styleId="partPaymentParameter" value="T" /> --%>
											<%-- 
												<td>
													<bean:message key="lbl.partPaymentParameter" />
												</td>
												<td>
													<html:select styleClass="text" property="partPaymentParameter"
														styleId="partPaymentParameter" value="" disabled="true"
														onchange="changeInstlDatePartPayment();">
														<html:option value="T">Keep Same Plan</html:option>
														<html:option value="I">Change Plan</html:option>
													</html:select>
												</td>
											--%>
											 <td>
											<bean:message key="lbl.rescheduleParameter" /><font color="red">*</font>
												</td>
												<td>
												  <div id="intsTypeE">
													<html:select styleClass="text" property="partPaymentParameter"
														styleId="partPaymentParameter" value="" >
														<%--  <html:option value="">--Select--</html:option>
														<logic:present name="genericMasterList">
														<logic:notEmpty name="genericMasterList">
														<html:optionsCollection name="genericMasterList" label="name" value="id" />
														</logic:notEmpty>
														
														</logic:present> --%>
													
														 <html:option value="">--Select--</html:option>
														<html:option value="T">Change Tenure</html:option>
														<html:option value="E">Change EMI</html:option>
														<html:option value="F">FIFO</html:option>
														<%-- <html:option value="L">LIFO</html:option> --%>
														<html:option value="Q">EQUAL</html:option>
													</html:select>
													</div> 
												</td>
											
										</tr>
										<tr>
											
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
											
											<td nowrap="nowrap">
												<bean:message key="lbl.reschReason" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:textarea property="reschReason"
													styleId="reschReason" value="" styleClass="text"></html:textarea>
											</td>
										</tr>
									</table>
									<button type="button" name="save" id="save" 
										class="topformbutton2" title="Alt+V" accesskey="V"
										onclick="return savePartPrePayment();"><bean:message key="button.save" /></button>
									<button type="button" name="saveFwd" id="saveFwd" title="Alt+F" accesskey="F"
										 class="topformbutton2"
										onclick="return updateDisbursalBeforeSave();"><bean:message key="button.forward" /></button>
									<button type="button" name="generateInstallmentPlan"
										id="generateRepaymentSch"
										 title="Alt+O" accesskey="O"
										class="topformbutton4" 
										onclick="return viewInstallmentPlanPartPayment();" ><bean:message key="button.oldinstplan" /></button>
									<button type="button" name="generateRepaymentSch"
										id="generateRepaymentSch"
										 title="Alt+R" accesskey="R"
										class="topformbutton4" 
										onclick="return viewRepaymentScheduleDisbursal();" ><bean:message key="button.oldrepay" /></button>
										<%-- 
											<button type="button" name="generateInstallmentPlanNew"
												styleId="generateRepaymentSchNew"
												 title="Alt+I" accesskey="I"
												class="topformbutton4" 
												onclick="javascript:alert('Please Save the Part Prepayment First');" ><bean:message key="button.newinstplan" /></button>
												
										
										--%>
										
									<button type="button" name="generateRepaymentSchNew"
										id="generateRepaymentSchNew"
										 title="Alt+N" accesskey="N"
										class="topformbutton4" 
										onclick="javascript:alert('Please Save the Part Prepayment First');" ><bean:message key="button.newrepay" /></button>
									<button type="button" name="viewRec"
										id="viewRec"
										 title="Alt+R" accesskey="R"
										class="topformbutton4" 
										onclick="return viewReceivablePartPrePayment('Please Save the Part Prepayment First');" ><bean:message key="button.View_Receivable" /></button>
										
										</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
			</logic:present>


			<!-- --------------------------------------- Part Prepayment New Ends --------------------------------- -->

			<!-- --------------------------------------- Part Prepayment Maker Starts--------------------------------- -->


			<logic:present name="partPrePaymentData">
				<html:form action="/partPrePaymentMaker" method="post"
					styleId="partPrepaymentMakerForm">
					<html:javascript
						formName="PartPrePaymentMakerDynaValidatorForm" />
					<fieldset>
						<legend>
							<bean:message key="lbl.partPrePaymentDetails" />
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="hidden" name="businessDate" id="businessDate"
										value="<%=initiationDate%>" />
									<html:hidden property="installmentType" styleId="installmentType"
										value="${partPrePaymentData[0].installmentType}" styleClass="text"/>
									<html:hidden property="reschId" styleId="reschId"
										value="${requestScope.reschId}" styleClass="text"/>
									<input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
									
									
									<table width="100%" border="0" cellspacing="1" cellpadding="1">
										<logic:iterate id="partPrePaymentDataList" name="partPrePaymentData">
										<tr>	<td><html:hidden property="realize" styleId="realize" value="${partPrePaymentData[0].realize}"/></td>
												<td><html:hidden property="realizeFlag" styleId="realizeFlag" value="${sessionScope.realizeFlag}" /></td></tr>
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.loanNo" />
												<font color="red">*</font>
												<html:hidden styleClass="text"  styleId="amount" property="amount" value="${partPrePaymentData[0].amount}" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="loanNo"
													styleId="loanNo" value="${partPrePaymentData[0].loanNo}"
													readonly="true" maxlength="20" />
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="${partPrePaymentData[0].lbxLoanNoHID}" />
													
													
												<input type="hidden" id="partPrePaymentCal" name="partPrePaymentCal" value="${partPrePaymentData[0].partPrePaymentCal}"/>	
													
													
													
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.customerName" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="customerName"
													styleId="customerName" maxlength="50" disabled="true"
													value="${partPrePaymentData[0].customerName}" tabindex="-1" />
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.product" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="product"
													styleId="product" value="${partPrePaymentData[0].product}" maxlength="50" 
													disabled="true" tabindex="-1"/>
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.scheme" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="scheme"
													styleId="scheme" maxlength="50" value="${partPrePaymentData[0].scheme}" 
													disabled="true" tabindex="-1"/>
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.disbursedAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursedAmount"
													styleId="disbursedAmount" maxlength="18" value="${partPrePaymentData[0].disbursedAmount}"
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.outstandingLoanAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="outstandingLoanAmount" style="text-align:right;"
													styleId="outstandingLoanAmount" maxlength="18" value="${partPrePaymentData[0].outstandingLoanAmount}"
													readonly="true" tabindex="-1" />
											</td>
										</tr>

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.nextDueDate" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="nextDueDate"
													styleId="partNextDueDate" value="${partPrePaymentData[0].nextDueDate}" maxlength="10"
													readonly="true" tabindex="-1"/>
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.partPaymentSinceDisbursal" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="partPaymentSinceDisbursal" style="text-align:right;"
													styleId="partPaymentSinceDisbursal" maxlength="10" value="${partPrePaymentData[0].partPaymentSinceDisbursal}"
													disabled="true" tabindex="-1"/>
											</td>
										</tr>

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.lastPartPaymentDate" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="lastPartPaymentDate"
													styleId="lastPartPaymentDate" value="${partPrePaymentData[0].lastPartPaymentDate}" maxlength="10"
													readonly="true" tabindex="-1"/>
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.reqRefNo" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="reqRefNo"
													styleId="reqRefNo" value="${partPrePaymentData[0].reqRefNo}" 
													 maxlength="10"/>
											</td>
										</tr>

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.partPaymentDate" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="partPaymentDate" tabindex="-1"
													styleId="partPaymentDate" maxlength="10" readonly="true"
													value="${partPrePaymentData[0].partPaymentDate}" />
											<%-- 	<html:button property="partPaymentDateButton" styleId="partPaymentDateButton" value=" "
													onclick="openLOVCommon(219,'partPrepaymentMakerForm','partPaymentDate','loanNo','lbxInstlNo','lbxLoanNoHID','Please Select Loan','','hid');closeLovCallonLov('loanNo');" 
													styleClass="lovbutton"> 
												</html:button>
												--%>
												<html:hidden property="lbxInstlNo" styleId="lbxInstlNo" value="${partPrePaymentData[0].lbxInstlNo}" />
												<input type="hidden" name="hid" id="hid" />
											</td>
											<td>
												<bean:message key="lbl.partPaymentAmount"/>
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="partPaymentAmount"
													styleId="partPaymentAmount" value="${partPrePaymentData[0].partPaymentAmount}"
													onkeypress="return numbersonly(event,id,18)"
													onblur="formatNumber(this.value,id); generateReschCharges();"
													onkeyup="checkNumber(this.value, event, 18,id);"
													onfocus="keyUpNumber(this.value, event, 18,id);" 
													style="text-align:right;"/>
											</td>
										</tr>

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.partPaymentCharges" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="reschCharges" styleId="reschCharges" value="${partPrePaymentData[0].reschCharges}" readonly="false" tabindex="-1" 
												onfocus="keyUpNumber(this.value, event, 18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" 
												onkeyup="checkNumber(this.value, event, 18,id);" style="text-align:right;"/>
											</td>
											
											<%-- <html:hidden styleClass="text" property="partPaymentParameter"
													styleId="partPaymentParameter" value="T" /> --%>
											<%-- 
											<td>
												<bean:message key="lbl.partPaymentParameter" />
											</td>
											<td>
												<logic:equal name="partPrePaymentDataList" property="installmentType" value="I">
												<html:select styleClass="text" property="partPaymentParameter"
													styleId="partPaymentParameter" value="${partPrePaymentData[0].partPaymentParameter}"
													onchange="changeInstlDatePartPayment();">
													<html:option value="T">Keep Same Plan</html:option>
													<html:option value="I">Change Plan</html:option>
												</html:select>
												</logic:equal>
												<logic:equal name="partPrePaymentDataList" property="installmentType" value="N">
												<html:select styleClass="text" property="partPaymentParameter" disabled="true"
													styleId="partPaymentParameter" value="${partPrePaymentData[0].partPaymentParameter}"
													onchange="changeInstlDatePartPayment();">
													<html:option value="T">Keep Same Plan</html:option>
													<html:option value="I">Change Plan</html:option>
												</html:select>
												</logic:equal>
											</td>
											--%>
										
											<td>
											<bean:message key="lbl.rescheduleParameter" /><font color="red">*</font>
												</td>
												<td>
												   
													<html:select styleClass="text" property="partPaymentParameter"
														styleId="partPaymentParameter" value="${partPrePaymentData[0].partPaymentParameter}" >
														 <html:option value="">--Select--</html:option>
														  
														<logic:present name="genericMasterList">
														<logic:notEmpty name="genericMasterList">
														<html:optionsCollection name="genericMasterList" label="name" value="id" />
														</logic:notEmpty>
														
														</logic:present>
														 
														<%-- <html:option value="T">Change Tenure</html:option>
														<html:option value="E">Change EMI</html:option> --%>
													</html:select>
												</td>
											
										</tr>
										<tr>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.authorRemarks" />
											</td>
										
											<td nowrap="nowrap">
												<html:textarea styleClass="text"
													property="authorRemarks"
													styleId="authorRemarks" 
													disabled="true"
													value="${partPrePaymentData[0].authorRemarks}" />
											</td>
											
											<td nowrap="nowrap">
												<bean:message key="lbl.reschReason" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:textarea property="reschReason"
													styleId="reschReason" value="${partPrePaymentData[0].reschReason}" styleClass="text"></html:textarea>
											</td>
											<html:hidden property="disbStatus" styleId="disbStatus" value="${partPrePaymentData[0].disbursalStatus}" />
										</tr>
										</logic:iterate>
									</table>
									<button type="button" name="save" id="save" 
										class="topformbutton2" accesskey="V" title="Alt+V"
										onclick="return updatePartPrePaymentData('P');"><bean:message key="button.save" /></button>
									<button type="button"  name="saveFwd" id="saveFwd"
										 class="topformbutton2" accesskey="F" title="Alt+F"
										onclick="return updatePartPrePaymentData('F','<bean:message key="msg.confirmationForwardMsg" />');"><bean:message key="button.forward" /></button>
									<button type="button"  name="generateInstallmentPlan"
										id="generateInstallmentPlan"
										 title="Alt+O" accesskey="O"
										class="topformbutton4" 
										onclick="return viewInstallmentPlanPartPayment()" ><bean:message key="button.oldinstplan" /></button>
									<button type="button"  name="generateRepaymentSch"
										id="generateRepaymentSch"
										 title="Alt+R" accesskey="R"
										class="topformbutton4" 
										onclick="return viewRepaymentScheduleDisbursal();" ><bean:message key="button.oldrepay" /></button>
										
										<%--
												<logic:iterate name="partPrePaymentData" id="sublist">
												<logic:equal name="sublist" property="partPaymentParameter" value="T">
												<button type="button"  name="generateInstallmentPlanNew"
													id="generateRepaymentSchNew"
												 title="Alt+I" accesskey="I"
													class="topformbutton4" disabled="disabled" ><bean:message key="button.newinstplan" /></button>
												</logic:equal>
												<logic:equal name="sublist" property="partPaymentParameter" value="I">
												<button type="button"  name="generateInstallmentPlanNew"
													id="generateRepaymentSchNew"
													 title="Alt+I" accesskey="I"
													class="topformbutton4" 
													onclick="return generateInstallmentPlanPartPayment('P')" ><bean:message key="button.newinstplan" /></button>
												</logic:equal>
												</logic:iterate>
									 --%>
									
									<button type="button"  name="generateRepaymentSchNew"
										id="generateRepaymentSchNew"
										 title="Alt+N" accesskey="N"
										class="topformbutton4" 
										onclick="return newRepaymentSchedulePartPayment();" ><bean:message key="button.newrepay" /></button>
										
									<button type="button" id="viewRec" class="topformbutton3" 
									onclick="return viewReceivablePartPrePayment('Please Save the Part Prepayment First');"><bean:message key="button.viewRecievable1" /></button>
									<button type="button" name="delete" id="delete" 
										class="topformbutton2" accesskey="D" title="Alt+D"
										onclick="return deletePartPaymentDetails();"><bean:message key="button.delete" /></button>
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
			</logic:present>

			<!-- --------------------------------------- Part Prepayment Maker Ends --------------------------------- -->

			<!-- --------------------------------------- Part Prepayment Author Starts --------------------------------- -->

			<logic:present name="partPrePaymentDataAuthor">
				<html:form action="/partPrePaymentMaker" method="post"
					styleId="partPrepaymentMakerForm">
					<html:javascript
						formName="PartPrePaymentMakerDynaValidatorForm" />
					<fieldset>
						<legend>
							<bean:message key="lbl.partPrePaymentDetails" />
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="hidden" name="businessDate" id="businessDate"
										value="<%=initiationDate%>" />
									<html:hidden property="installmentType" styleId="installmentType"
										value="${partPrePaymentDataAuthor[0].installmentType}" styleClass="text"/>
									<html:hidden property="reschId" styleId="reschId"
										value="${sessionScope.reschId}" styleClass="text"/>

									<table width="100%" border="0" cellspacing="1" cellpadding="1">
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.loanNo" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="loanNo"
													styleId="loanNo" value="${partPrePaymentDataAuthor[0].loanNo}"
													disabled="true" maxlength="20" />
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="${partPrePaymentDataAuthor[0].lbxLoanNoHID}" />
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.customerName" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="customerName"
													styleId="customerName" maxlength="50" disabled="true"
													value="${partPrePaymentDataAuthor[0].customerName}" tabindex="-1" />
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.product" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="product"
													styleId="product" value="${partPrePaymentDataAuthor[0].product}" maxlength="50" 
													disabled="true" tabindex="-1"/>
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.scheme" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="scheme"
													styleId="scheme" maxlength="50" value="${partPrePaymentDataAuthor[0].scheme}" 
													disabled="true" tabindex="-1"/>
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.disbursedAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursedAmount"
													styleId="disbursedAmount" maxlength="18" value="${partPrePaymentDataAuthor[0].disbursedAmount}"
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.outstandingLoanAmount" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="outstandingLoanAmount" style="text-align:right;"
													styleId="outstandingLoanAmount" maxlength="18" value="${partPrePaymentDataAuthor[0].outstandingLoanAmount}"
													disabled="true" tabindex="-1" />
											</td>
										</tr>

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.nextDueDate" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="nextDueDate"
													styleId="partNextDueDate" value="${partPrePaymentDataAuthor[0].nextDueDate}" maxlength="10"
													disabled="true" tabindex="-1"/>
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.partPaymentSinceDisbursal" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="partPaymentSinceDisbursal" style="text-align:right;"
													styleId="partPaymentSinceDisbursal" maxlength="10" value="${partPrePaymentDataAuthor[0].partPaymentSinceDisbursal}"
													disabled="true" tabindex="-1"/>
											</td>
										</tr>

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.lastPartPaymentDate" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="lastPartPaymentDate"
													styleId="lastPartPaymentDate" value="${partPrePaymentDataAuthor[0].lastPartPaymentDate}" maxlength="10"
													disabled="true" tabindex="-1"/>
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.reqRefNo" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="reqRefNo"
													styleId="reqRefNo" value="${partPrePaymentDataAuthor[0].reqRefNo}"
													maxlength="10" disabled="true"/>
											</td>
										</tr>

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.partPaymentDate" />
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="partPaymentDate" tabindex="-1"
													styleId="partPaymentDate1" maxlength="10" disabled="true"
													onchange="checkDate('partPaymentDate');" value="${partPrePaymentDataAuthor[0].partPaymentDate}" />
												<html:hidden property="lbxInstlNo" styleId="lbxInstlNo" value="${partPrePaymentDataAuthor[0].lbxInstlNo}" />
											</td>
											<td>
												<bean:message key="lbl.partPaymentAmount"/>
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="partPaymentAmount"
													styleId="partPaymentAmount" value="${partPrePaymentDataAuthor[0].partPaymentAmount}"
													style="text-align:right;" disabled="true"/>
											</td>
										</tr>

										<tr>
											<td>
												<bean:message key="lbl.partPaymentCharges" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text styleClass="text" property="reschCharges"
													styleId="reschCharges" value="${partPrePaymentDataAuthor[0].reschCharges}" 
													style="text-align:right;" disabled="true"/>
											</td>
											
												<%-- <html:hidden styleClass="text" property="partPaymentParameter"
													styleId="partPaymentParameter" value="T" /> --%>
											<%-- 
											<td>
												<bean:message key="lbl.partPaymentParameter" />
											</td>
											<td>
												<html:select styleClass="text" property="partPaymentParameter" disabled="true"
													styleId="partPaymentParameter" value="${partPrePaymentDataAuthor[0].partPaymentParameter}"
													onchange="changeInstlDatePartPayment();">
													<html:option value="T">Keep Same Plan</html:option>
													<html:option value="I">Change Plan</html:option>
												</html:select>
											</td>
											--%>
											<td>
											<bean:message key="lbl.rescheduleParameter" /><font color="red">*</font>
												</td>
												<td>
												   
													<html:select styleClass="text" property="partPaymentParameter"
														styleId="partPaymentParameter" disabled="true" value="${partPrePaymentDataAuthor[0].partPaymentParameter}">
														 <html:option value="">--Select--</html:option>
														 
														<logic:present name="genericMasterList">
														<logic:notEmpty name="genericMasterList">
														<html:optionsCollection name="genericMasterList" label="name" value="id" />
														</logic:notEmpty>
														
														</logic:present>
									
													</html:select>
												</td>
										</tr>
										<tr>
											
											<td>
												<bean:message key="lbl.authorRemarks" />
											</td>
										
											<td><html:textarea styleClass="text"
													property="authorRemarks"
													styleId="authorRemarks" 
													disabled="true"
													value="${partPrePaymentDataAuthor[0].authorRemarks}" />
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.reschReason" />
												<font color="red">*</font>
											</td>
											<td>
												<html:textarea property="reschReason" disabled="true"
													styleId="reschReason" value="${partPrePaymentDataAuthor[0].reschReason}" styleClass="text"></html:textarea>
											</td>
											<html:hidden property="disbStatus" styleId="disbStatus" value="${partPrePaymentDataAuthor[0].disbursalStatus}" />
										</tr>
									</table>
									<button type="button" name="generateInstallmentPlan" id="generateInstallmentPlan" 
										class="topformbutton4" accesskey="O" title="Alt+O"
										onclick="return viewInstallmentPlanPartPayment()"><bean:message key="button.oldinstplan" /></button>
									<button type="button" name="generateRepaymentSch" id="generateRepaymentSch" 
										class="topformbutton4" accesskey="R" title="Alt+R"
										onclick="return viewRepaymentScheduleDisbursal();"><bean:message key="button.oldrepay" /></button>
										
									

										<%-- 
										<logic:iterate name="partPrePaymentDataAuthor" id="sublist">
										<logic:equal name="sublist" property="partPaymentParameter" value="T">
										<html:button property="generateInstallmentPlanNew"
											styleId="generateRepaymentSchNew"
											 title="Alt+I" accesskey="I"
											styleClass="topformbutton4" disabled="true" ><bean:message key="button.newinstplan" /></html:button>
										</logic:equal>
										<logic:equal name="sublist" property="partPaymentParameter" value="I">
										<html:button property="generateInstallmentPlanNew"
											styleId="generateRepaymentSchNew"
											 title="Alt+I" accesskey="I"
											styleClass="topformbutton4" 
											onclick="return generateInstallmentPlanPartPayment('F')" ><bean:message key="button.newinstplan" /></html:button>
										</logic:equal>
										</logic:iterate>
									--%>

									<button type="button" name="generateRepaymentSchNew" id="generateRepaymentSchNew" 
										class="topformbutton4" accesskey="N" title="Alt+N"
										onclick="return newRepaymentSchedulePartPayment();"><bean:message key="button.newrepay" /></button>
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
			</logic:present>

			<!-- --------------------------------------- Part Prepayment Author Ends --------------------------------- -->

		</div>
	</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<script>
	setFramevalues("partPrepaymentMakerForm");
</script>	
</body>
</html:html>
<logic:present name="delete">
<script type="text/javascript">
		alert("<bean:message key="lbl.dataDeleted" />");
		window.location = "<%=request.getContextPath()%>/partPrePaymentSearch.do?method=partPrePaymetMakerSearch";	
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
		window.location = "<%=request.getContextPath()%>/partPrePaymentSearch.do?method=partPrePaymetMakerSearch";
}
else if("<%=request.getAttribute("message")%>"=="newInstlPlan")
{
	alert("<bean:message key="msg.saveNewInstlPlan" />");
}
else if("<%=request.getAttribute("message")%>"=="E")
{
	//alert("<bean:message key="msg.DataNotSaved" />");
	alert("<%=request.getAttribute("msg")%>");
	//window.location = "<%=request.getContextPath()%>/partPrePaymentSearchBehind.do?method=partPrePaymetMakerSearch";
}
</script>
</logic:present>

<logic:present name="nonInstallmentBased">
	<script type="text/javascript">
		alert("<bean:message key="msg.nonInstallmentBased" />");
	</script>
</logic:present>