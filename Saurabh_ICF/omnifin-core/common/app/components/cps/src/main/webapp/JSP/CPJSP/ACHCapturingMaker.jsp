<%--
 	Author Name      :- Himanshu Verma
 	Date of Creation :- 07 Dec 2015
 	Purpose          :- ACH Capturing Search Screen 
 --%>
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/achCapturing.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>

	<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/jquery.emicalculator.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/globalize.min.js"></script>
		
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="maker" content="" />
		
		<script>
		 
		$(function() {
			$("#dteDate").datepicker({
				changeMonth: true,
			changeYear: true,
			yearRange: '1900:+10',
			showOn: 'both',
			buttonImage: document.getElementById("CalImage").value,
			buttonImageOnly: true,
			dateFormat: document.getElementById("formatD").value,
			defaultDate: document.getElementById("businessdate").value
			
			});
		});
		
		$(function() {
			$("#dteFromDate").datepicker({
				changeMonth: true,
			changeYear: true,
			yearRange: '1900:+10',
			showOn: 'both',
			buttonImage: document.getElementById("CalImage").value,
			buttonImageOnly: true,
			dateFormat: document.getElementById("formatD").value,
			defaultDate: document.getElementById("businessdate").value
			
			});
		});
		
		$(function() {
			$("#dteToDate").datepicker({
				changeMonth: true,
			changeYear: true,
			yearRange: '1900:+10',
			showOn: 'both',
			buttonImage: document.getElementById("CalImage").value,
			buttonImageOnly: true,
			dateFormat: document.getElementById("formatD").value,
			defaultDate: document.getElementById("businessdate").value
			
			});
		});
		</script>
		
	</head>
	<body oncontextmenu="return false"
		onunload=""
		onload="enableAnchor();">
		
		<logic:present name="image">
   	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
        </logic:present>
   		<logic:notPresent name="image">
   			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
   		</logic:notPresent>
   		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
		<div align="center" class="opacity" style="display: none;"
			id="processingImage">
		</div>

		<html:form action="/achCapturingAction"	styleId="achCapturingSearchForm" method="post">
			<input type="hidden" id="contextPath" value="<%=path%>" />
			 <logic:notEqual name="functionId" value="3000362">
			<input type="hidden" name="reportName" id="reportName" value="ACH_SLIP_REPORT" />
			</logic:notEqual>
			<logic:equal name="functionId" value="3000362">
				<input type="hidden" name="reportName" id="reportName" value="ACH_SLIP_REPORT_DEAL" />
				</logic:equal>
		<logic:notPresent name="ACHCapturingEditData">
			<logic:notPresent name="view">
				<fieldset>
					<legend>
						<bean:message key="lbl.customer_detail" />
					</legend>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<table width="100%" border="0" cellspacing="1" cellpadding="1">
									<tr>
									 <logic:notEqual name="functionId" value="3000362">
										<td>
											<bean:message key="lbl.loanNo"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtDealNo" styleId="txtDealNo" styleClass="text" maxlength="50" value="" readonly="true" tabindex="-1"/>
							  				<%-- <input type="button" class="lovbutton" id="btnDealNo"
												onClick="openLOVCommon(16105,'achCapturingSearchForm','txtDealNo','','', '','','fetchACHCustomerDetail','txtCustomerName','hidDealNo')"
												value=" " name="dealButton"/>--%>
												
												
												<input type="button" class="lovbutton" id="btnDealNo"
												onClick="achDecisionvalue();"
												value=" " name="dealButton"/>
											<input type="hidden" name="hidDealNo" id="hidDealNo" value=""/>
										</td>
										</logic:notEqual>
										<logic:equal name="functionId" value="3000362">
										<td>
											<bean:message key="lbl.DealNo"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtDealNo" styleId="txtDealNo" styleClass="text" maxlength="50" value="" readonly="true" tabindex="-1"/>
							  				<input type="button" class="lovbutton" id="btnDealNo"
												onClick="openLOVCommon(16106,'achCapturingSearchForm','txtDealNo','','', '','','fetchACHCustomerDetail','txtCustomerName','hidDealNo')"
												value=" " name="dealButton"/>
											<input type="hidden" name="hidDealNo" id="hidDealNo" value=""/>
										</td>
										</logic:equal>
										<td>
											<bean:message key="lbl.customerName"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtCustomerName" styleId="txtCustomerName" styleClass="text"  readonly="true" maxlength="50" value=""/>
										</td>
									</tr>
									<tr>
										
										<td>
											<bean:message key="lbl.date"/><font color="red">*</font>
										</td>
										<td>

											<html:text property="dteDate" styleId="dteDate" styleClass="text" maxlength="10" value="${userobject.businessdate}" onchange="return checkDate('dteDate');" />

										

										</td>
										<td>
											<bean:message key="lbl.weHerebyAuthorize"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtWeHerebyAuthorize" styleId="txtWeHerebyAuthorize" styleClass="text" maxlength="50" value="" />
										</td>
										
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.bankAccountNo"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtBankAccountNo" styleId="txtBankAccountNo" styleClass="text" maxlength="20" value=""/>
										</td>
										<td>
											<bean:message key="lbl.BankName"/><font color="red">*</font>
										</td>
										<td>
											<%--
											<html:text property="txtBankName" styleId="txtBankName" styleClass="text" maxlength="50" value="" />
											--%>
											<html:text property="txtBankName" styleId="txtBankName" styleClass="text" maxlength="50" value="" readonly="true" tabindex="-1"/>
							  				<input type="button" class="lovbutton" id="btnDealNo"
												onClick="clearBankBranchValues();openLOVCommon(16098,'achCapturingSearchForm','txtBankName','','', '','','','hidBankName')"
												value=" " name="dealButton"/>
											<input type="hidden" name="hidBankName" id="hidBankName" value=""/>
										</td>
									</tr>
									<tr>
										
										<td>
											<bean:message key="lbl.branchDesc"/><font color="red">*</font>
										</td>
										<td>
										<%-- 	<html:text property="txtBankBranchName" styleId="txtBankBranchName" styleClass="text" maxlength="50" value=""/>
											--%>
											<html:text property="txtBankBranchName" styleId="txtBankBranchName" styleClass="text" maxlength="50" value="" readonly="true" tabindex="-1"/>
							  				<input type="button" class="lovbutton" id="btnDealNo"
												onClick="checkBankNameForBranch();"
												value=" " name="dealButton"/>
											<input type="hidden" name="hidBankBranchName" id="hidBankBranchName" value=""/>
										</td>
										<td>
											<bean:message key="lbl.MICR"/><font color="red">*</font>
										</td>
										<td>

											<html:text property="txtMicr" styleId="txtMicr" styleClass="text" maxlength="9" value="" />

									

										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.ifscCode"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtIfsc" styleId="txtIfsc" styleClass="text" maxlength="11" value=""/>
										</td>
									
										<td>
											<bean:message key="lbl.loanAmount"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtLoanAmount" styleId="txtLoanAmount" styleClass="text"  onchange="numberFormatting(this.id,'2')" maxlength="22" value="" />
										</td>
									</tr>
									<tr>
										
										<td>
											<bean:message key="lbl.achAmount"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtTotalAmount" styleId="txtTotalAmount" styleClass="text"  onchange="numberFormatting(this.id,'2')" maxlength="22" value=""/>
										</td>
										<td>
											<bean:message key="lbl.refrenceNo1"/>
										</td>
										<td>
											<html:text property="txtReferenceNo" styleId="txtReferenceNo" styleClass="text" maxlength="50" value="" />
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.refrenceNo2"/>
										</td>
										<td>
											<html:text property="txtReferenceNo2" styleId="txtReferenceNo2" styleClass="text" maxlength="50" value="" />
										</td>
										
										<td>
											<bean:message key="lbl.phoneoff"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtPhoneNo" styleId="txtPhoneNo" styleClass="text"  onkeypress="return isNumberKey(event);" maxlength="10" value=""/>
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.email"/>
										</td>
										<td>
											<html:text property="txtEmailId" styleId="txtEmailId" styleClass="text" maxlength="50" value="" />
										</td>

										<td>
											<bean:message key="lbl.frequency"/><font color="red">*</font>
										</td>
										<td>
									<%-- 		<html:text property="selFrequency" styleId="selFrequency" styleClass="text" maxlength="50" value=""/>--%>
											<html:select property="selFrequency" styleId="selFrequency"
												styleClass="text" value="" style="width:90px !important; min-width:85px !important;">
												<html:option value="">--Select--</html:option>
												<logic:present name="FrequencyList">
													<html:optionsCollection name="FrequencyList"
														label="selFrequencyDescription"
														value="selFrequencyValue" />
												</logic:present>
											</html:select>
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.fromDate"/>
										</td>
										<td>
											<html:text property="dteFromDate" styleId="dteFromDate" styleClass="text" maxlength="10" value="${userobject.businessdate}" onchange="return checkDate('dteFromDate');" />
										</td>
										
										<td>
											<bean:message key="lbl.toDate"/>
										</td>
										<td>
											<html:text property="dteToDate" styleId="dteToDate" styleClass="text" maxlength="10" value="" onchange="return checkDate('dteToDate');"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</fieldset>
				
				
				
				
				<fieldset>
					<legend>
						<bean:message key="lbl.achDetails" />
					</legend>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<table width="100%" border="0" cellspacing="1" cellpadding="1">
									<tr>
										<td>
											<bean:message key="lbl.sponsorBankCode"/>
										</td>
										<td>
											<html:text property="txtSponsorBankCode" styleId="txtSponsorBankCode" styleClass="text" maxlength="20" value="" />
							  				
										</td>
										
										<td>
											<bean:message key="lbl.utilityCode"/>
										</td>
										<td>
											<html:text property="txtUtilityCode" styleId="txtUtilityCode" styleClass="text" maxlength="20" value=""/>
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.toDebit"/><font color="red">*</font>
										</td>
										<td>
											<%-- <html:text property="selToDebit" styleId="selToDebit" styleClass="text" maxlength="50" value="" />
											--%>
											
											<html:select property="selToDebit" styleId="selToDebit"
												styleClass="text" value="" style="width:90px !important; min-width:85px !important;">
												<%-- <html:option value="">--Select--</html:option> --%>
												<logic:present name="toDebitList">
													<html:optionsCollection name="toDebitList" label="selToDebitDescription" value="selToDebitValue" />
												</logic:present>
											</html:select>
										</td>
										
										<td>
											<bean:message key="lbl.debitType"/><font color="red">*</font>
										</td>
										<td>
									<%---
							 		<html:text property="selDebitType" styleId="selDebitType" styleClass="text" maxlength="50" value=""/>--%>
											<html:select property="selDebitType" styleId="selDebitType"
												styleClass="text" value="" style="width:90px !important; min-width:85px !important;">
												<%-- <html:option value="">--Select--</html:option> --%>
												<logic:present name="debitTypeList">
													<html:optionsCollection name="debitTypeList"
														label="selDebitTypeDescription"
														value="selDebitTypeValue" />
												</logic:present>
											</html:select>
											
										</td>
									</tr>
									<tr>
									<%-- 
										<td>
											<bean:message key="lbl.fixedMaximumAmount"/>
										</td>
										<td>
											<html:select property="selFixedMaximumAmount" styleId="selFixedMaximumAmount"
												styleClass="text" value="" style="width:90px !important; min-width:85px !important;">
												<html:option value="">--Select--</html:option>
												<logic:present name="fixMaxAmtList">
													<html:optionsCollection name="fixMaxAmtList"
														label="selFixedMaximumAmountValue"
														value="selFixedMaximumAmountDescription" />
												</logic:present>
											</html:select>
										</td>
										--%>
										<td>
											<bean:message key="lbl.nameOfAccountHolder"/>
										</td>
										<td>
											<html:text property="txtNameAccountHolder" styleId="txtNameAccountHolder" styleClass="text" maxlength="50" value=""/>
										</td>
										<td>
											<bean:message key="lbl.achDecision"/>
										</td>
										<td>
											<html:select styleClass="text" property="achDecision"   styleId="achDecision" >
							    			<html:option value="C">Create</html:option>
							    			<html:option value="M">Modify</html:option>
							    			<html:option value="X">Cancel</html:option>
							    			
							    			
							    			</html:select>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
										
				</fieldset>
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td>
						<button type="button" name="Save" class="topformbutton2" id="Save" title="Alt+S" accesskey="S" onclick="return saveACHData();">
							<bean:message key="button.save" />
						</button>
						
						<button type="button" name="Delete" class="topformbutton2" id="Delete" title="Alt+D" accesskey="D" onclick="return deleteACHData();">
							<bean:message key="lbl.delete" />
						</button>
						
						<button type="button" name="Print" class="topformbutton2" id="Print" title="Alt+P" accesskey="P" onclick="return printACHData();">
							<bean:message key="btn.print" />
						</button>
					</td>
				</tr>
			</table>
				
				
			</logic:notPresent>
		</logic:notPresent>
			
			<%--	Changed for Edit screen starts here --%>
			
			<logic:present name="ACHCapturingEditData">
				<logic:notPresent name="view">
				<html:hidden property="hidAchCapturingId" styleId="hidAchCapturingId"	value="${achCapturingEditDatarecord[0].hidAchCapturingId}" />
															
				<fieldset>
					<legend>
						<bean:message key="lbl.customer_detail" />
					</legend>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<table width="100%" border="0" cellspacing="1" cellpadding="1">
									<tr>
									<logic:notEqual name="functionId" value="3000362">
										<td>
											<bean:message key="lbl.loanNo"/><font color="red">*</font>
										</td>
										</logic:notEqual>
										<logic:equal name="functionId" value="3000362">
										<td>
											<bean:message key="lbl.DealNo"/><font color="red">*</font>
										</td>
										</logic:equal>
										<td>
											<html:text property="txtDealNo" styleId="txtDealNo" styleClass="text" maxlength="50" value="${achCapturingEditDatarecord[0].txtDealNo}" readonly="true" tabindex="-1" />
							  			<%-- 	<input type="button" class="lovbutton" id="btnDealNo"
												onClick="openLOVCommon(16105,'achCapturingSearchForm','txtDealNo','','', '','','fetchACHCustomerDetail','txtCustomerName','hidDealNo')"
												value=" " name="dealButton"/>--%>
											<input type="hidden" name="hidDealNo" id="hidDealNo" value="${achCapturingEditDatarecord[0].hidDealNo}"/>
										</td>
										<td>
											<bean:message key="lbl.customerName"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtCustomerName" styleId="txtCustomerName" styleClass="text" readonly="true" maxlength="50" value="${achCapturingEditDatarecord[0].txtCustomerName}" />
										</td>
									</tr>
									<tr>
										
										<td>
											<bean:message key="lbl.date"/><font color="red">*</font>
										</td>
										<td>

											<html:text property="dteDate" styleId="dteDate" styleClass="text" maxlength="10" value="${achCapturingEditDatarecord[0].dteDate}"  onchange="return checkDate('dteDate');"/>

											

										</td>
										<td>
											<bean:message key="lbl.weHerebyAuthorize"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtWeHerebyAuthorize" styleId="txtWeHerebyAuthorize" styleClass="text" maxlength="50" value="${achCapturingEditDatarecord[0].txtWeHerebyAuthorize}" />
										</td>
										
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.bankAccountNo"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtBankAccountNo" styleId="txtBankAccountNo" styleClass="text" maxlength="20" value="${achCapturingEditDatarecord[0].txtBankAccountNo}"/>
										</td>
										<td>
											<bean:message key="lbl.BankName"/><font color="red">*</font>
										</td>
										<td>
											<%--
											<html:text property="txtBankName" styleId="txtBankName" styleClass="text" maxlength="50" value="" />
											--%>
											<html:text property="txtBankName" styleId="txtBankName" styleClass="text" maxlength="50" value="${achCapturingEditDatarecord[0].txtBankName}" readonly="true" tabindex="-1"/>
							  				<input type="button" class="lovbutton" id="btnDealNo"
												onClick="clearBankBranchValues();openLOVCommon(16098,'achCapturingSearchForm','txtBankName','','', '','','','hidBankName')"
												value=" " name="dealButton"/>
											<input type="hidden" name="hidBankName" id="hidBankName" value="${achCapturingEditDatarecord[0].hidBankName}"/>
										</td>
									</tr>
									<tr>
										
										<td>
											<bean:message key="lbl.branchDesc"/><font color="red">*</font>
										</td>
										<td>
										<%-- 	<html:text property="txtBankBranchName" styleId="txtBankBranchName" styleClass="text" maxlength="50" value=""/>
											--%>
											<html:text property="txtBankBranchName" styleId="txtBankBranchName" styleClass="text" maxlength="50" value="${achCapturingEditDatarecord[0].txtBankBranchName}" readonly="true" tabindex="-1"/>
							  				<input type="button" class="lovbutton" id="btnDealNo"
												onClick="checkBankNameForBranch();"
												value=" " name="dealButton"/>
											<input type="hidden" name="hidBankBranchName" id="hidBankBranchName" value="${achCapturingEditDatarecord[0].hidBankBranchName}"/>
										</td>
										<td>
											<bean:message key="lbl.MICR"/><font color="red">*</font>
										</td>
										<td>

											<html:text property="txtMicr" styleId="txtMicr" styleClass="text" maxlength="9" value="${achCapturingEditDatarecord[0].txtMicr}" />

											

										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.ifscCode"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtIfsc" styleId="txtIfsc" styleClass="text" maxlength="11" value="${achCapturingEditDatarecord[0].txtIfsc}"/>
										</td>
									
										<td>
											<bean:message key="lbl.loanAmount"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtLoanAmount" styleId="txtLoanAmount" style="text-align: right" styleClass="text" maxlength="22" onchange="numberFormatting(this.id,'2')" value="${achCapturingEditDatarecord[0].txtLoanAmount}" />
										</td>
									</tr>
									<tr>
										
										<td>
											<bean:message key="lbl.achAmount"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtTotalAmount" styleId="txtTotalAmount" style="text-align: right" styleClass="text" maxlength="22" onchange="numberFormatting(this.id,'2')" value="${achCapturingEditDatarecord[0].txtTotalAmount}"/>
										</td>
										<td>
											<bean:message key="lbl.refrenceNo1"/>
										</td>
										<td>
											<html:text property="txtReferenceNo" styleId="txtReferenceNo" styleClass="text" maxlength="50" value="${achCapturingEditDatarecord[0].txtReferenceNo}" />
										</td>
									</tr>
									<tr>
										
										<td>
											<bean:message key="lbl.refrenceNo2"/>
										</td>
										<td>
											<html:text property="txtReferenceNo2" styleId="txtReferenceNo2" styleClass="text" maxlength="50" value="${achCapturingEditDatarecord[0].txtReferenceNo2}" />
										</td>
										
										<td>
											<bean:message key="lbl.phoneoff"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtPhoneNo" styleId="txtPhoneNo" styleClass="text"  onkeypress="return isNumberKey(event);" maxlength="10" value="${achCapturingEditDatarecord[0].txtPhoneNo}"/>
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.email"/>
										</td>
										<td>
											<html:text property="txtEmailId" styleId="txtEmailId" styleClass="text" maxlength="50" value="${achCapturingEditDatarecord[0].txtEmailId}" />
										</td>
										
										<td>
											<bean:message key="lbl.frequency"/><font color="red">*</font>
										</td>
										<td>
									<%-- 		<html:text property="selFrequency" styleId="selFrequency" styleClass="text" maxlength="50" value=""/>--%>
											<html:select property="selFrequency" styleId="selFrequency"
												styleClass="text" value="${achCapturingEditDatarecord[0].selFrequency}" style="width:90px !important; min-width:85px !important;">
												<html:option value="">--Select--</html:option>
												<logic:present name="FrequencyList">
													<html:optionsCollection name="FrequencyList"
														label="selFrequencyDescription"
														value="selFrequencyValue" />
												</logic:present>
											</html:select>
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.fromDate"/>
										</td>
										<td>
											<html:text property="dteFromDate" styleId="dteFromDate" styleClass="text" maxlength="10" value="${achCapturingEditDatarecord[0].dteFromDate}" onchange="return checkDate('dteFromDate');" />
										</td>
										
										<td>
											<bean:message key="lbl.toDate"/>
										</td>
										<td>
											<html:text property="dteToDate" styleId="dteToDate" styleClass="text" maxlength="10" value="${achCapturingEditDatarecord[0].dteToDate}" onchange="return checkDate('dteToDate');"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</fieldset>
				
				
				
				
				<fieldset>
					<legend>
						<bean:message key="lbl.achDetails" />
					</legend>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<table width="100%" border="0" cellspacing="1" cellpadding="1">
									<tr>
										<td>
											<bean:message key="lbl.sponsorBankCode"/>
										</td>
										<td>
											<html:text property="txtSponsorBankCode" styleId="txtSponsorBankCode" styleClass="text" maxlength="20" value="${achCapturingEditDatarecord[0].txtSponsorBankCode}" />
							  				
										</td>
										
										<td>
											<bean:message key="lbl.utilityCode"/>
										</td>
										<td>
											<html:text property="txtUtilityCode" styleId="txtUtilityCode" styleClass="text" maxlength="20" value="${achCapturingEditDatarecord[0].txtUtilityCode}"/>
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.toDebit"/><font color="red">*</font>
										</td>
										<td>
											<%-- <html:text property="selToDebit" styleId="selToDebit" styleClass="text" maxlength="50" value="" />
											--%>
											
											<html:select property="selToDebit" styleId="selToDebit"
												styleClass="text" value="${achCapturingEditDatarecord[0].selToDebit}" style="width:90px !important; min-width:85px !important;">
												<%-- <html:option value="">--Select--</html:option> --%>
												<logic:present name="toDebitList">
													<html:optionsCollection name="toDebitList"
														label="selToDebitDescription"
														value="selToDebitValue" />
												</logic:present>
											</html:select>
										</td>
										
										<td>
											<bean:message key="lbl.debitType"/><font color="red">*</font>
										</td>
										<td>
									<%---
							 		<html:text property="selDebitType" styleId="selDebitType" styleClass="text" maxlength="50" value=""/>--%>
											<html:select property="selDebitType" styleId="selDebitType"
												styleClass="text" value="${achCapturingEditDatarecord[0].selDebitType}" style="width:90px !important; min-width:85px !important;">
												<%-- <html:option value="">--Select--</html:option> --%>
												<logic:present name="debitTypeList">
													<html:optionsCollection name="debitTypeList"
														label="selDebitTypeDescription"
														value="selDebitTypeValue" />
												</logic:present>
											</html:select>
											
										</td>
									</tr>
									<tr>
									<%-- 
										<td>
											<bean:message key="lbl.fixedMaximumAmount"/>
										</td>
										<td>
											<html:select property="selFixedMaximumAmount" styleId="selFixedMaximumAmount"
												styleClass="text" value="${achCapturingEditDatarecord[0].selFixedMaximumAmount}" style="width:90px !important; min-width:85px !important;">
												<html:option value="">--Select--</html:option>
												<logic:present name="fixMaxAmtList">
													<html:optionsCollection name="fixMaxAmtList"
														label="selFixedMaximumAmountValue"
														value="selFixedMaximumAmountDescription" />
												</logic:present>
											</html:select>
										</td>
										--%>
										<td>
											<bean:message key="lbl.nameOfAccountHolder"/>
										</td>
										<td>
											<html:text property="txtNameAccountHolder" styleId="txtNameAccountHolder" styleClass="text" maxlength="50" value="${achCapturingEditDatarecord[0].txtNameAccountHolder}"/>
										</td>
										<td>
											<bean:message key="lbl.achDecision"/>
										</td>
										<td>
											<html:select styleClass="text" property="achDecision"   styleId="achDecision" value="${achCapturingEditDatarecord[0].achDecision}">
							    			<html:option value="C">Create</html:option>
							    			<html:option value="M">Modify</html:option>
							    			<html:option value="X">Cancel</html:option>
							    			</html:select>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					
				</fieldset>
				
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td>
						<button type="button" name="Save" class="topformbutton2" id="Save" title="Alt+S" accesskey="S" onclick="return udpateACHData();">
							<bean:message key="button.save" />
						</button>
						
						<button type="button" name="Delete" class="topformbutton2" id="Delete" title="Alt+D" accesskey="D" onclick="return deleteACHData();">
							<bean:message key="lbl.delete" />
						</button>
						
						<button type="button" name="Print" class="topformbutton2" id="Print" title="Alt+P" accesskey="P" onclick="return printACHData();">
							<bean:message key="btn.print" />
						</button>
					</td>
				</tr>
			</table>
				</logic:notPresent>
				</logic:present>
				<!-- Rohit Changes for View  mode Starst-->
				<logic:present name="view">
				<html:hidden property="hidAchCapturingId" styleId="hidAchCapturingId"	value="${achCapturingEditDatarecord[0].hidAchCapturingId}" />
															
				<fieldset>
					<legend>
						<bean:message key="lbl.customer_detail" />
					</legend>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<table width="100%" border="0" cellspacing="1" cellpadding="1">
									<tr>
									 <logic:notEqual name="functionId" value="3000362">
										<td>
											<bean:message key="lbl.loanNo"/><font color="red">*</font>
										</td>
										</logic:notEqual>
										 <logic:equal name="functionId" value="3000362">
										<td>
											<bean:message key="lbl.DealNo"/><font color="red">*</font>
										</td>
										</logic:equal>
										<td>
											<html:text property="txtDealNo" styleId="txtDealNo" styleClass="text"  readonly="true" maxlength="50" value="${achCapturingEditDatarecord[0].txtDealNo}" readonly="true" tabindex="-1" />
							  			<%-- 	<input type="button" class="lovbutton" id="btnDealNo"
												onClick="openLOVCommon(16105,'achCapturingSearchForm','txtDealNo','','', '','','fetchACHCustomerDetail','txtCustomerName','hidDealNo')"
												value=" " name="dealButton"/>--%>
											<input type="hidden" name="hidDealNo" id="hidDealNo" value="${achCapturingEditDatarecord[0].hidDealNo}"/>
										</td>
										<td>
											<bean:message key="lbl.customerName"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtCustomerName" styleId="txtCustomerName" readonly="true" styleClass="text" readonly="true" maxlength="50" value="${achCapturingEditDatarecord[0].txtCustomerName}" />
										</td>
									</tr>
									<tr>
										
										<td>
											<bean:message key="lbl.date"/><font color="red">*</font>
										</td>
										<td>

											<html:text property="dteDate" styleId="dteDateInView" styleClass="text" readonly="true" maxlength="10" value="${achCapturingEditDatarecord[0].dteDate}"  onchange="return checkDate('dteDate');" disabled="true"/>

											

										</td>
										<td>
											<bean:message key="lbl.weHerebyAuthorize"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtWeHerebyAuthorize" styleId="txtWeHerebyAuthorize" styleClass="text" readonly="true" maxlength="50" value="${achCapturingEditDatarecord[0].txtWeHerebyAuthorize}" />
										</td>
										
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.bankAccountNo"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtBankAccountNo" styleId="txtBankAccountNo" styleClass="text" readonly="true" maxlength="20" value="${achCapturingEditDatarecord[0].txtBankAccountNo}"/>
										</td>
										<td>
											<bean:message key="lbl.BankName"/><font color="red">*</font>
										</td>
										<td>
											<%--
											<html:text property="txtBankName" styleId="txtBankName" styleClass="text" maxlength="50" value="" />
											--%>
											<html:text property="txtBankName" styleId="txtBankName" styleClass="text" maxlength="50" readonly="true" value="${achCapturingEditDatarecord[0].txtBankName}" readonly="true" tabindex="-1"/>
							  				<%--<input type="button" class="lovbutton" id="btnDealNo"
												 onClick="clearBankBranchValues();openLOVCommon(16098,'achCapturingSearchForm','txtBankName','','', '','','','hidBankName')"
												value=" " name="dealButton"/>
											<input type="hidden" name="hidBankName" id="hidBankName" value="${achCapturingEditDatarecord[0].hidBankName}"/> --%>
										</td>
									</tr>
									<tr>
										
										<td>
											<bean:message key="lbl.branchDesc"/><font color="red">*</font>
										</td>
										<td>
										<%-- 	<html:text property="txtBankBranchName" styleId="txtBankBranchName" styleClass="text" maxlength="50" value=""/>
											--%>
											<html:text property="txtBankBranchName" styleId="txtBankBranchName" styleClass="text" readonly="true" maxlength="50" value="${achCapturingEditDatarecord[0].txtBankBranchName}" readonly="true" tabindex="-1"/>
							  				
										</td>
										<td>
											<bean:message key="lbl.MICR"/><font color="red">*</font>
										</td>
										<td>

											<html:text property="txtMicr" styleId="txtMicr" styleClass="text" readonly="true" maxlength="9" value="${achCapturingEditDatarecord[0].txtMicr}" />

											

										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.ifscCode"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtIfsc" styleId="txtIfsc" styleClass="text" readonly="true" maxlength="11" value="${achCapturingEditDatarecord[0].txtIfsc}"/>
										</td>
									
										<td>
											<bean:message key="lbl.loanAmount"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtLoanAmount" styleId="txtLoanAmount" readonly="true" style="text-align: right" styleClass="text" maxlength="22" onchange="numberFormatting(this.id,'2')" value="${achCapturingEditDatarecord[0].txtLoanAmount}" />
										</td>
									</tr>
									<tr>
										
										<td>
											<bean:message key="lbl.achAmount"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtTotalAmount" styleId="txtTotalAmount" readonly="true" style="text-align: right" styleClass="text" maxlength="22" onchange="numberFormatting(this.id,'2')" value="${achCapturingEditDatarecord[0].txtTotalAmount}"/>
										</td>
										<td>
											<bean:message key="lbl.refrenceNo1"/>
										</td>
										<td>
											<html:text property="txtReferenceNo" styleId="txtReferenceNo" styleClass="text" readonly="true" maxlength="50" value="${achCapturingEditDatarecord[0].txtReferenceNo}" />
										</td>
									</tr>
									<tr>
										
										<td>
											<bean:message key="lbl.refrenceNo2"/>
										</td>
										<td>
											<html:text property="txtReferenceNo2" styleId="txtReferenceNo2" styleClass="text" readonly="true" maxlength="50" value="${achCapturingEditDatarecord[0].txtReferenceNo2}" />
										</td>
										
										<td>
											<bean:message key="lbl.phoneoff"/><font color="red">*</font>
										</td>
										<td>
											<html:text property="txtPhoneNo" styleId="txtPhoneNo" styleClass="text" readonly="true" onkeypress="return isNumberKey(event);" maxlength="10" value="${achCapturingEditDatarecord[0].txtPhoneNo}"/>
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.email"/>
										</td>
										<td>
											<html:text property="txtEmailId" styleId="txtEmailId" styleClass="text" readonly="true" maxlength="50" value="${achCapturingEditDatarecord[0].txtEmailId}" />
										</td>
										
										<td>
											<bean:message key="lbl.frequency"/><font color="red">*</font>
										</td>
										<td>
									<%-- 		<html:text property="selFrequency" styleId="selFrequency" styleClass="text" maxlength="50" value=""/>--%>
											<%-- <html:text property="selFrequency" styleId="selFrequency"
												styleClass="text" value="${achCapturingEditDatarecord[0].selFrequency}" readonly="true"  style="width:90px !important; min-width:85px !important;"/>
												 --%>
											<html:select property="selFrequency" styleId="selFrequency"
												styleClass="text" disabled="true" value="${achCapturingEditDatarecord[0].selFrequency}" style="width:90px !important; min-width:85px !important;">
												<html:option value="">--Select--</html:option>
												<logic:present name="FrequencyList">
													<html:optionsCollection name="FrequencyList"
														label="selFrequencyDescription"
														value="selFrequencyValue" />
												</logic:present>
											</html:select>
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.fromDate"/>
										</td>
										<td>
											<html:text property="dteFromDate" styleId="dteFromDateInView" styleClass="text" maxlength="10" readonly="true" value="${achCapturingEditDatarecord[0].dteFromDate}" onchange="return checkDate('dteFromDate');" disabled="true"/>
										</td>
										
										<td>
											<bean:message key="lbl.toDate"/>
										</td>
										<td>
											<html:text property="dteToDate" styleId="dteToDateInView" styleClass="text" maxlength="10" readonly="true" value="${achCapturingEditDatarecord[0].dteToDate}" onchange="return checkDate('dteToDate');" disabled="true"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</fieldset>
				
				
				
				
				<fieldset>
					<legend>
						<bean:message key="lbl.achDetails" />
					</legend>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<table width="100%" border="0" cellspacing="1" cellpadding="1">
									<tr>
										<td>
											<bean:message key="lbl.sponsorBankCode"/>
										</td>
										<td>
											<html:text property="txtSponsorBankCode" styleId="txtSponsorBankCode" styleClass="text" readonly="true"  maxlength="20" value="${achCapturingEditDatarecord[0].txtSponsorBankCode}" />
							  				
										</td>
										
										<td>
											<bean:message key="lbl.utilityCode"/>
										</td>
										<td>
											<html:text property="txtUtilityCode" styleId="txtUtilityCode" styleClass="text" readonly="true" maxlength="20" value="${achCapturingEditDatarecord[0].txtUtilityCode}"/>
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.toDebit"/><font color="red">*</font>
										</td>
										<td>
											<%-- <html:text property="selToDebit" styleId="selToDebit" styleClass="text" maxlength="50" value="" />
											--%>
											
											<%-- <html:text property="selToDebit" styleId="selToDebit"
												styleClass="text" readonly="true" value="${achCapturingEditDatarecord[0].selToDebit}" style="width:90px !important; min-width:85px !important;">
												
											</html:text> --%>
											<html:select property="selToDebit" styleId="selToDebit"
												styleClass="text" disabled="true" value="${achCapturingEditDatarecord[0].selToDebit}" style="width:90px !important; min-width:85px !important;">
											<%-- 	<html:option value="">--Select--</html:option> --%>
												<logic:present name="toDebitList">
													<html:optionsCollection name="toDebitList"
														label="selToDebitDescription"
														value="selToDebitValue" />
												</logic:present>
											</html:select>
											
										</td>
										
										<td>
											<bean:message key="lbl.debitType"/><font color="red">*</font>
										</td>
										<td>
									<%---
							 		<html:text property="selDebitType" styleId="selDebitType" styleClass="text" maxlength="50" value=""/>--%>
										<%-- 	<html:text property="selDebitType" styleId="selDebitType"
												styleClass="text"  readonly="true" value="${achCapturingEditDatarecord[0].selDebitType}" style="width:90px !important; min-width:85px !important;">
												
											</html:text> --%>
											<html:select property="selDebitType" styleId="selDebitType"
												styleClass="text" disabled="true" value="${achCapturingEditDatarecord[0].selDebitType}" style="width:90px !important; min-width:85px !important;">
												<%-- <html:option value="">--Select--</html:option> --%>
												<logic:present name="debitTypeList">
													<html:optionsCollection name="debitTypeList"
														label="selDebitTypeDescription"
														value="selDebitTypeValue" />
												</logic:present>
											</html:select>
											
										</td>
									</tr>
									<tr>
									<%-- 
										<td>
											<bean:message key="lbl.fixedMaximumAmount"/>
										</td>
										<td>
											<html:select property="selFixedMaximumAmount" styleId="selFixedMaximumAmount"
												styleClass="text" value="${achCapturingEditDatarecord[0].selFixedMaximumAmount}" style="width:90px !important; min-width:85px !important;">
												<html:option value="">--Select--</html:option>
												<logic:present name="fixMaxAmtList">
													<html:optionsCollection name="fixMaxAmtList"
														label="selFixedMaximumAmountValue"
														value="selFixedMaximumAmountDescription" />
												</logic:present>
											</html:select>
										</td>
										--%>
										<td>
											<bean:message key="lbl.nameOfAccountHolder"/>
										</td>
										<td>
											<html:text property="txtNameAccountHolder" styleId="txtNameAccountHolder" styleClass="text"  readonly="true" maxlength="50" value="${achCapturingEditDatarecord[0].txtNameAccountHolder}"/>
										</td>
										<td>
											<bean:message key="lbl.achDecision"/>
										</td>
										<td>
											<html:select styleClass="text" property="achDecision"  styleId="achDecision" value="${achCapturingEditDatarecord[0].achDecision}">
							    			<html:option value="C">Create</html:option>
							    			<html:option value="M">Modify</html:option>
							    			<html:option value="X">Cancel</html:option>
							    			</html:select>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					
				</fieldset>
				
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td>
						
						<button type="button" name="Print" class="topformbutton2" id="Print" title="Alt+P" accesskey="P" onclick="return printACHData();">
							<bean:message key="btn.print" />
						</button>
					</td>
				</tr>
			</table>
				
				</logic:present>
				<!-- Rohit end -->
		</html:form>
		<logic:present name="message" >
		<script>
		if('<%=session.getAttribute("message").toString()%>'=='S')
		{
			alert("<bean:message key="msg.DataSaved" />");
		}
		else if('<%=session.getAttribute("message").toString()%>'=='D') 
		{
			alert("<bean:message key="msg.DataDelete" />");
			parent.location='<%=request.getContextPath()%>/achCapturingAction.do?method=searchACHCapturingRecords';	
		}
		<%-- else if('<%=session.getAttribute("message").toString()%>'=='Back')
		{
			alert("Please capture some details");
			parent.location='<%=request.getContextPath()%>/achCapturingAction.do?method=fetchACHData';
		} --%>
		</script>
		</logic:present>
	</body>
</html:html>