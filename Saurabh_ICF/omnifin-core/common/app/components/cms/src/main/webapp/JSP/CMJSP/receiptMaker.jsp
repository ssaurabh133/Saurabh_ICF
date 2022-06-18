<%-- 
	Author Name :- Manisha Tomar
Date of Creation :23-04-2011
Purpose :-  screen for the Receipt Maker
--%>


<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	com.login.roleManager.UserObject userobj = (com.login.roleManager.UserObject) session
			.getAttribute("userobject");
	String initiationDate = userobj.getBusinessdate();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/popup.js"></script>


	<!-- css and jquery for Datepicker -->

	<link type="text/css"
		href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css"
		rel="stylesheet" />
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>

	<!-- css and jquery for Datepicker -->
	<!-- css and jquery for tooltip -->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/jquery.simpletip-1.3.1.js"></script>
	<link type="text/css"
		href="<%=request.getContextPath()%>/css/toolTip.css" rel="stylesheet" />
	<!-- css and jquery for tooltip -->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cmScript/paymentMaker.js"></script>
	<script type="text/javascript"
		src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>

	<script type="text/javascript">	
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('receiptMakerForm').receiptMode.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('receiptMakerForm').loanAccountButton.focus();
     }
     return true;
}
</script>

	<script type="text/javascript">

			function popitup(url) {
	newwindow=window.open(url,'name','height=270,width=700,top=200,left=250');
	if (window.focus) {newwindow.focus()}
	return false;
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
<body
	onload="enableAnchor();depositShowHide();checkChanges('receiptMakerForm');document.getElementById('receiptMakerForm').loanAccountButton.focus();fntab();"
	onunload="closeAllWindowCallUnloadBody();">
	<logic:present name="image">
		<input type="hidden" id="CalImage"
			value='<%=request.getContextPath()%>/${image}/calendar.gif' />
	</logic:present>
	<logic:notPresent name="image">
		<input type="hidden" id="CalImage"
			value='<%=request.getContextPath()%>/images/theme1/calendar.gif' />
	</logic:notPresent>
	<input type="hidden" id="businessdate"
		value='${userobject.businessdate}' />

	<input type="hidden" name="forwardLoanID" id="forwardLoanID"
		value="${forwardLoanID}" />
	<input type="hidden" name="forwardInstrumentID"
		id="forwardInstrumentID" value="${forwardInstrumentID}" />
	<input type="hidden" name="formatD" id="formatD"
		value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" name="loanRecStatus"
		value="${requestScope.loanRecStatus}" id="loanRecStatus" />
	<html:hidden property="receiptNoFlag" styleId="receiptNoFlag"
		value="${sessionScope.receiptNoFlag}" />
	<input type="hidden" name="loanBranch"
		value="${requestScope.loanBranchStatus}" id="loanBranch" />
	<input type="hidden" name="autoManualFlag" id="autoManualFlag" 
		value="${autoManualFlag}"/>


	<input type="hidden" name="cashDepositFlag" id="cashDepositFlag"
		value="${sessionScope.cashDepositFlag}" />
	<input type="hidden" name="nonCashDepositFlag" id="nonCashDepositFlag"
		value="${sessionScope.nonCashDepositFlag}" />
		
	<input type="hidden" name="allocationGridReceipt" id="allocationGridReceipt"
		value="${sessionScope.allocationGridReceipt}" />

   <input type="hidden" name="tdsreceiptStatus" id="tdsreceiptStatus"
		value="${sessionScope.tdsreceiptStatus}" />
		
  <input type="hidden" name="repoFlagMarked" id="repoFlagMarked"
		value="${sessionScope.repoFlagMarked}" />
  
   

	<div id="centercolumn">

		<div id="revisedcontainer">

			<html:form action="/receiptMakerProcessAction" method="post"
				styleId="receiptMakerForm">
				<html:javascript formName="ReceiptMakerDynaValidatorForm" />
				<html:errors />


				<input type="hidden" name="canForward"
					value="${requestScope.canForward}" id="canForward" />
				<input type="hidden" name="beforeForward" value=""
					id="beforeForward" />
				<input type="hidden" name="VallocatedAmount" value=""
					id="VallocatedAmount" />
				<input type="hidden" name="VtdsAmount" value="" id="VtdsAmount" />


				<input type="hidden" name="formatD"
					value="<bean:message key="lbl.dateFormat"/>" id="formatD" />
				<input type="hidden" name="businessDate" id="businessDate"
					value="<%=initiationDate%>" />
				<input type="hidden" name="contextPath"
					value="<%=request.getContextPath()%>" id="contextPath" />
				<input type="hidden" name="basePath"
					value="<%=request.getScheme() + "://"
							+ request.getServerName() + ":"
							+ request.getServerPort() + path + "/"%>"
					id="basePath" />
				
				<input type="hidden" name="loanRepaymentType" id="loanRepaymentType"
		            value="${requestScope.savedReceipt[0].loanRepaymentType}" />

				<fieldset>
					<legend>
						<bean:message key="lbl.receiptsMaker"></bean:message>
					</legend>

					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="center">

								<table width="100%" border="0" cellspacing="1" cellpadding="1">

									<tr>


										<logic:present name="savedReceipt">
											<logic:notPresent name="newCase">
												<input type="hidden" id="modifyRecord" name="modifyRecord"
													value="M" />

												<html:hidden property="loanId" styleId="loanId"
													value="${requestScope.savedReceipt[0].lbxLoanNoHID}" />
												<!--			<input type="hidden" value="${requestScope.savedReceipt[0].lbxLoanNoHID}" id="loanId" name="loanId"/>	-->
											<tr>	
												<td>
													<bean:message key="lbl.loanAccount"></bean:message>
												</td>
												<td>

													<html:text styleClass="text" property="loanAccountNumber"
														styleId="loanAccountNumber" maxlength="20"
														value="${requestScope.savedReceipt[0].loanAccountNumber}"
														style="background-color:#F2F2F2" readonly="true" />
													<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
														value="${requestScope.savedReceipt[0].lbxLoanNoHID}" />
													<html:hidden property="instrumentID" styleId="instrumentID"
														value="${requestScope.savedReceipt[0].instrumentID}" />

												</td>
												<td>
													<bean:message key="lbl.customerName"></bean:message>
												</td>
												<td>
													<a rel="tooltip3" id="tool"> <html:text
															property="customerName" styleClass="text"
															styleId="customerName"
															value="${requestScope.savedReceipt[0].customerName}"
															onmouseover="applyToolTip(id);"
															onchange="blankcanforward();" maxlength="50"
															style="background-color:#F2F2F2" readonly="true" /> </a>
												</td>
											</tr>	
												
												<tr>

													<td>
														<bean:message key="lbl.businessPartnerType"></bean:message>
													</td>
													<td>
														<html:text styleClass="text"
															property="businessPartnerType"
															styleId="businessPartnerType" maxlength="50"
															value="${requestScope.savedReceipt[0].businessPartnerType}"
															readonly="true" tabindex="-1" />
														<html:hidden property="lbxBPType" styleId="lbxBPType"
															value="${requestScope.savedReceipt[0].lbxBPType}" />
														<html:button property="loanAccountButton"
															styleId="loanAccountButton"
															onclick="openLOVCommon(147,'receiptMakerForm','businessPartnerType','loanAccountNumber','lbxBPType', 'lbxLoanNoHID','Select loanAccountNumber','totalRec','lbxBPNID','businessPartnerName');closeLovCallonLov('loanAccountNumber');"
															value=" " styleClass="lovbutton"></html:button>

													</td>

													<td>
														<bean:message key="lbl.businessPartnerName"></bean:message>
													</td>
													<td>

														<html:text styleClass="text"
															property="businessPartnerName"
															styleId="businessPartnerName" maxlength="50"
															value="${requestScope.savedReceipt[0].businessPartnerName}"
															style="background-color:#F2F2F2" readonly="true"
															tabindex="-1" />
														<html:hidden property="lbxBPNID" styleId="lbxBPNID"
															value="${requestScope.savedReceipt[0].lbxBPNID}" />

													</td>
												</tr>
											</logic:notPresent>

											<!--	  change by sachin-->
											<logic:present name="newCase">
												<tr>
													<html:hidden property="loanId" styleId="loanId"
														value="${requestScope.savedReceipt[0].lbxLoanNoHID}" />
													<td>
														<bean:message key="lbl.loanAccount"></bean:message>
														<font color="red">*</font>
													</td>
													<td>

														<html:text styleClass="text" property="loanAccountNumber"
															readonly="true" styleId="loanAccountNumber"
															maxlength="20"
															value="${requestScope.savedReceipt[0].loanAccountNumber}"
															tabindex="-1" />
														<html:hidden property="loanRefernceNo"
															styleId="loanRefernceNo"
															value="" />
														<input type="hidden" id="assetDesc" name="assetDesc" value="" />
														<html:hidden property="lbxLoanNoHID"
															styleId="lbxLoanNoHID"
															value="${requestScope.savedReceipt[0].lbxLoanNoHID}" />
														<html:button property="loanAccountButton"
															styleId="loanAccountButton"
															onclick="closeLovCallonLov1();openLOVCommon(265,'receiptMakerForm','loanAccountNumber','','', '','','getDefaultBusinessPartnerTypeCS','customerName','loanRefernceNo','assetDesc')"
															value=" " styleClass="lovbutton"></html:button>
														<html:hidden property="instrumentID"
															styleId="instrumentID"
															value="${requestScope.savedReceipt[0].instrumentID}" />
														

													</td>
													<td>
														<bean:message key="lbl.customerName"></bean:message>
														<font color="red">*</font>
													</td>
													<td>
														<a rel="tooltip3" id="tool"> <html:text
																property="customerName" styleClass="text"
																styleId="customerName"
																value="${requestScope.savedReceipt[0].customerName}"
																onmouseover="applyToolTip(id);" maxlength="50"
																readonly="true"></html:text> </a>
														<input type="hidden" name="contextPath"
															value="<%=request.getContextPath()%>" id="contextPath" />
													</td>
												</tr>
<!-- 												Nishant space starts -->
<!-- 												<tr> -->
<%-- 													<td><bean:message key="lbl.vehicleNo"></bean:message></td> --%>
<!-- 													<td> -->
<%-- 														<html:text styleClass="text" property="vehicleNo" readonly="true" styleId="vehicleNo" maxlength="20" value="${bpList[0].vehicleNo}" tabindex="-1" /> --%>
<%-- 														<html:button property="vehicleButton" styleId="vehicleButton" onclick="closeLovCallonLov1();openLOVCommon(576,'receiptMakerForm','vehicleNo','','', '','','getDefaultBusinessPartnerTypeVeh','vehicleNo','loanRefernceNo','customerName')" value=" " styleClass="lovbutton"></html:button> --%>
<!-- 													</td> -->
<!-- 												</tr> -->
<!-- 												Nishant space starts -->
												<tr>
													<td>
														<bean:message key="lbl.businessPartnerType"></bean:message>
														<font color="red">* </font>
													</td>
													<td>
														<html:text styleClass="text"
															property="businessPartnerType"
															styleId="businessPartnerType" maxlength="50"
															value="${requestScope.savedReceipt[0].businessPartnerType}"
															readonly="true" tabindex="-1" />
														<html:hidden property="lbxBPType" styleId="lbxBPType"
															value="${requestScope.savedReceipt[0].lbxBPType}" />
														<html:button property="businessPartnerButton"
															styleId="businessPartnerButton"
															onclick="openLOVCommon(147,'receiptMakerForm','businessPartnerType','loanAccountNumber','lbxBPType', 'lbxLoanNoHID','Select loanAccountNumber','totalRec','lbxBPNID','businessPartnerName');closeLovCallonLov('loanAccountNumber');"
															value=" " styleClass="lovbutton"></html:button>
														<!--     <img onclick="openLOVCommon(147,'receiptMakerForm','businessPartnerType','lbxLoanNoHID','lbxBPType', 'loanAccountNumber','Select loanAccountNumber','','lbxBPNID','businessPartnerName')" src="<%=request.getContextPath()%>/images/lov.gif"/>   -->



													</td>

													<td>
														<bean:message key="lbl.businessPartnerName"></bean:message>
													</td>
													<td>

														<html:text styleClass="text"
															property="businessPartnerName"
															styleId="businessPartnerName" maxlength="50"
															value="${requestScope.savedReceipt[0].businessPartnerName}"
															readonly="true" tabindex="-1" />
														<html:hidden property="lbxBPNID" styleId="lbxBPNID"
															value="${requestScope.savedReceipt[0].lbxBPNID}" />
													</td>
												</tr>

											</logic:present>

											<!--end by sachin-->
											<tr>
												<td>
													<bean:message key="lbl.receiptMode"></bean:message>
													<font color="red">*</font>
												</td>
												<td>
													<span style="float: left;"> <html:select
															property="receiptMode" styleId="receiptMode"
															styleClass="text"
															value="${requestScope.savedReceipt[0].receiptMode}"
															onchange="getCashDepositAccount();depositShowHide();fnReceiptNull();cashAccountDisable();blankcanforward();">
															<html:option value="">--Select--</html:option>
															<html:option value="C">Cash</html:option>
															<html:option value="Q">Cheque</html:option>
															<html:option value="D">DD</html:option>
															<html:option value="N">NEFT</html:option>
															<html:option value="R">RTGS</html:option>
															<html:option value="DIR">Direct Debit</html:option>
															<html:option value="S">Adjustment</html:option>
														</html:select> <html:hidden property="lbxreceiptMode"
															styleId="lbxreceiptMode"
															value="${requestScope.savedReceipt[0].lbxreceiptMode}" />

													</span>
												</td>
												<td>
													<bean:message key="lbl.receiptDate"></bean:message>
													<bean:message key="lbl.dateFormat(dd-mm-yyyy)" />
													<font color="red">*</font>
												</td>
												<td>
													<html:text property="receiptDate" styleClass="text"
														onchange="blankcanforward();" styleId="receiptDate"
														value="${requestScope.savedReceipt[0].receiptDate}"
														maxlength="10" onchange="return checkDate('receiptDate');"></html:text>
												</td>


											</tr>
											<tr>

												<td>
													<bean:message key="lbl.instrumentNumber"></bean:message>
													<font color="red">*</font>
												</td>
												<logic:present name="savedReceipt">
													<logic:iterate name="savedReceipt" id="subReceiptList"
														length="1">
														<logic:equal name="subReceiptList" property="receiptMode"
															value="C">
															<td>
																<html:text property="instrumentNumber"
																	onchange="blankcanforward();" styleClass="text"
																	styleId="instrumentNumber" disabled="true"
																	value="${requestScope.savedReceipt[0].instrumentNumber}"
																	maxlength="20"></html:text>
															</td>
														</logic:equal>
														<logic:equal name="subReceiptList" property="receiptMode"
															value="S">
															<td>
																<html:text property="instrumentNumber"
																	onchange="blankcanforward();" styleClass="text"
																	styleId="instrumentNumber" disabled="true"
																	value="${requestScope.savedReceipt[0].instrumentNumber}"
																	maxlength="20"></html:text>
															</td>
														</logic:equal>
														<logic:equal name="subReceiptList" property="receiptMode"
															value="DIR">
															<td>
																<html:text property="instrumentNumber"
																	onchange="blankcanforward();" styleClass="text"
																	styleId="instrumentNumber" disabled="true"
																	value="${requestScope.savedReceipt[0].instrumentNumber}"
																	maxlength="20"></html:text>
															</td>
														</logic:equal>

														<logic:notEqual name="subReceiptList"
															property="receiptMode" value="C">
															<logic:notEqual name="subReceiptList"
																property="receiptMode" value="S">
																<logic:notEqual name="subReceiptList"
																	property="receiptMode" value="DIR">
																	<td>
																		<html:text property="instrumentNumber"
																			onchange="blankcanforward();" styleClass="text"
																			styleId="instrumentNumber" disabled="false"
																			value="${requestScope.savedReceipt[0].instrumentNumber}"
																			maxlength="20"></html:text>
																	</td>
																</logic:notEqual>
															</logic:notEqual>
														</logic:notEqual>
													</logic:iterate>
												</logic:present>

												<td>
													<bean:message key="lbl.instrumentDate"></bean:message>
													<bean:message key="lbl.dateFormat(dd-mm-yyyy)" />
													<font color="red">*</font>
												</td>

												<logic:present name="savedReceipt">
													<logic:iterate name="savedReceipt" id="subReceiptList"
														length="1">
														<logic:equal name="subReceiptList" property="receiptMode"
															value="C">
															<td>
																<div id="disIdInsD" style="display: none;">

																	<html:text property="instrumentDate" styleClass="text"
																		onchange="blankcanforward();" styleId="instrumentDate"
																		value="${requestScope.savedReceipt[0].instrumentDate}"
																		maxlength="10"
																		onchange="return checkDate('instrumentDate');"></html:text>
																</div>
																<div id="disIdIns" style="display: inline">
																	<html:text property="instrumentDate" styleClass="text"
																		onchange="blankcanforward();"
																		styleId="instrumentDateDis" readonly="true"
																		value="${requestScope.savedReceipt[0].instrumentDate}"
																		maxlength="10" tabindex="-1"></html:text>
																</div>
															</td>
														</logic:equal>
														<logic:equal name="subReceiptList" property="receiptMode"
															value="S">
															<td>
																<div id="disIdInsD" style="display: none;">

																	<html:text property="instrumentDate" styleClass="text"
																		onchange="blankcanforward();" styleId="instrumentDate"
																		value="${requestScope.savedReceipt[0].instrumentDate}"
																		maxlength="100"></html:text>
																</div>
																<div id="disIdIns" style="display: inline">
																	<html:text property="instrumentDate" styleClass="text"
																		onchange="blankcanforward();"
																		styleId="instrumentDateDis" readonly="true" value=""
																		maxlength="100" tabindex="-1"></html:text>
																</div>

															</td>
														</logic:equal>
														<logic:equal name="subReceiptList" property="receiptMode"
															value="DIR">
															<td>
																<div id="disIdInsD" style="display: none;">

																	<html:text property="instrumentDate" styleClass="text"
																		onchange="blankcanforward();" styleId="instrumentDate"
																		value="${requestScope.savedReceipt[0].instrumentDate}"
																		maxlength="100"></html:text>
																</div>
																<div id="disIdIns" style="display: inline">
																	<html:text property="instrumentDate" styleClass="text"
																		onchange="blankcanforward();"
																		styleId="instrumentDateDis" readonly="true" value=""
																		maxlength="100" tabindex="-1"></html:text>
																</div>

															</td>
														</logic:equal>
														<logic:notEqual name="subReceiptList"
															property="receiptMode" value="C">
															<logic:notEqual name="subReceiptList"
																property="receiptMode" value="S">
																<logic:notEqual name="subReceiptList"
																	property="receiptMode" value="DIR">
																	<td>

																		<div id="disIdInsD" style="display: inline">
																			<html:text property="instrumentDate"
																				styleClass="text" onchange="blankcanforward();"
																				styleId="instrumentDate"
																				value="${requestScope.savedReceipt[0].instrumentDate}"
																				maxlength="100"></html:text>
																		</div>
																		<div id="disIdIns" style="display: none;">
																			<html:text property="instrumentDate"
																				styleClass="text" onchange="blankcanforward();"
																				styleId="instrumentDateDis" readonly="true" value=""
																				maxlength="100"></html:text>
																		</div>
																	</td>
																</logic:notEqual>
															</logic:notEqual>
														</logic:notEqual>
													</logic:iterate>
												</logic:present>
											</tr>
											<tr>


												<td>
													<bean:message key="lbl.IssuingBank"></bean:message>
												</td>
												<logic:present name="savedReceipt">
													<logic:iterate name="savedReceipt" id="subReceiptList"
														length="1">
														<logic:equal name="subReceiptList" property="receiptMode"
															value="C">

															<td>
																<html:text styleClass="text" property="bank"
																	styleId="bank" maxlength="100" tabindex="-1"
																	value="${requestScope.savedReceipt[0].bank}"
																	readonly="true" />
																<div id="disId" style="display: none;">
																	<html:hidden property="lbxBankID" styleId="lbxBankID"
																		disabled="true"
																		value="${requestScope.savedReceipt[0].lbxBankID}" />
																	<html:button property="loanBankButton"
																		styleId="loanBankButton"
																		onclick="closeLovCallonLov1();openLOVCommon(7,'receiptMakerForm','bank','','branch', 'lbxBranchID','','','lbxBankID')"
																		value=" " styleClass="lovbutton"
																		onchange="blankcanforward();"></html:button>
																</div>
															</td>
														</logic:equal>
														<logic:equal name="subReceiptList" property="receiptMode"
															value="S">

															<td>
																<html:text styleClass="text" property="bank"
																	styleId="bank" maxlength="100" tabindex="-1"
																	value="${requestScope.savedReceipt[0].bank}"
																	readonly="true" />
																<div id="disId" style="display: none;">
																	<html:hidden property="lbxBankID" styleId="lbxBankID"
																		disabled="true"
																		value="${requestScope.savedReceipt[0].lbxBankID}" />
																	<html:button property="loanBankButton"
																		styleId="loanBankButton"
																		onclick="closeLovCallonLov1();openLOVCommon(7,'receiptMakerForm','bank','','branch', 'lbxBranchID','','','lbxBankID')"
																		value=" " styleClass="lovbutton"
																		onchange="blankcanforward();"></html:button>
																</div>
															</td>

														</logic:equal>
														<logic:equal name="subReceiptList" property="receiptMode"
															value="DIR">

															<td>
																<html:text styleClass="text" property="bank"
																	styleId="bank" maxlength="100" tabindex="-1"
																	value="${requestScope.savedReceipt[0].bank}"
																	readonly="true" />
																<div id="disId" style="display: none;">
																	<html:hidden property="lbxBankID" styleId="lbxBankID"
																		disabled="true"
																		value="${requestScope.savedReceipt[0].lbxBankID}" />
																	<html:button property="loanBankButton"
																		styleId="loanBankButton"
																		onclick="closeLovCallonLov1();openLOVCommon(7,'receiptMakerForm','bank','','branch', 'lbxBranchID','','','lbxBankID')"
																		value=" " styleClass="lovbutton"
																		onchange="blankcanforward();"></html:button>
																</div>
															</td>

														</logic:equal>
														<logic:notEqual name="subReceiptList"
															property="receiptMode" value="C">
															<logic:notEqual name="subReceiptList"
																property="receiptMode" value="S">
																<logic:notEqual name="subReceiptList"
																	property="receiptMode" value="DIR">
																	<td>

																		<html:text styleClass="text" property="bank"
																			styleId="bank" maxlength="100" tabindex="-1"
																			value="${requestScope.savedReceipt[0].bank}"
																			readonly="true" />
																		<html:hidden property="lbxBankID" styleId="lbxBankID"
																			disabled="false"
																			value="${requestScope.savedReceipt[0].lbxBankID}" />
																		<div id="disId" style="display: inline;">
																			<html:button property="loanBankButton"
																				styleId="loanBankButton"
																				onclick="closeLovCallonLov1();openLOVCommon(7,'receiptMakerForm','bank','','branch', 'lbxBranchID','','','lbxBankID')"
																				value=" " styleClass="lovbutton"
																				onchange="blankcanforward();"></html:button>
																		</div>

																	</td>
																</logic:notEqual>
															</logic:notEqual>
														</logic:notEqual>
													</logic:iterate>
												</logic:present>

												<td>
													<bean:message key="lbl.Issuingbranch"></bean:message>
												</td>

												<logic:present name="savedReceipt">
												   <html:hidden property="micr" styleClass="text"
																		styleId="micr"
																		value="${requestScope.savedReceipt[0].micr}" />
																	<html:hidden property="ifsCode" styleId="ifsCode"
																		styleClass="text"
																		value="${requestScope.savedReceipt[0].ifsCode}" />
												   
													<logic:iterate name="savedReceipt" id="subReceipt"
														length="1">
														<logic:equal name="subReceipt" property="receiptMode"
															value="C">

															<td>
																<html:text styleClass="text" property="branch"
																	styleId="branch" maxlength="100" disabled="true"
																	tabindex="-1"
																	value="${requestScope.savedReceipt[0].branch}"
																	readonly="true" />
																<div id="disIdBranch" style="display: none;">
																	<html:hidden property="lbxBranchID"
																		styleId="lbxBranchID" disabled="true"
																		value="${requestScope.savedReceipt[0].lbxBranchID}" />
																	<html:button property="loanBranchButton"
																		styleId="loanBranchButton"
																		onclick="openLOVCommon(50,'receiptMakerForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifsCode');closeLovCallonLov('bank');"
																		value=" " styleClass="lovbutton"
																		onchange="blankcanforward();"></html:button>

																</div>
															</td>

														</logic:equal>
														<logic:equal name="subReceipt" property="receiptMode"
															value="DIR">

															<td>
																<html:text styleClass="text" property="branch"
																	styleId="branch" maxlength="100" disabled="true"
																	tabindex="-1"
																	value="${requestScope.savedReceipt[0].branch}"
																	readonly="true" />
																<div id="disIdBranch" style="display: none;">
																	<html:hidden property="lbxBranchID"
																		styleId="lbxBranchID" disabled="true"
																		value="${requestScope.savedReceipt[0].lbxBranchID}" />
																	<html:button property="loanBranchButton"
																		styleId="loanBranchButton"
																		onclick="openLOVCommon(50,'receiptMakerForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifsCode');closeLovCallonLov('bank');"
																		value=" " styleClass="lovbutton"
																		onchange="blankcanforward();"></html:button>

																</div>
															</td>

														</logic:equal>
														<logic:equal name="subReceipt" property="receiptMode"
															value="S">
															<td>
																<html:text styleClass="text" property="branch"
																	styleId="branch" maxlength="100" tabindex="-1"
																	value="${requestScope.savedReceipt[0].branch}"
																	readonly="true" />
																<div id="disIdBranch" style="display: none;">
																	<html:hidden property="lbxBranchID"
																		styleId="lbxBranchID" disabled="true"
																		value="${requestScope.savedReceipt[0].lbxBranchID}" />
																	<html:button property="loanBranchButton"
																		styleId="loanBranchButton"
																		onclick="openLOVCommon(50,'receiptMakerForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifsCode');closeLovCallonLov('bank');"
																		value=" " styleClass="lovbutton"
																		onchange="blankcanforward();"></html:button>

																</div>
															</td>

														</logic:equal>

														<logic:equal name="subReceipt" property="receiptMode"
															value="N">
															<td>
																<html:text styleClass="text" property="branch"
																	styleId="branch" maxlength="100" tabindex="-1"
																	value="${requestScope.savedReceipt[0].branch}"
																	readonly="true" />
																<div id="disIdBranch" style="display: none;">
																	<html:hidden property="lbxBranchID"
																		styleId="lbxBranchID" disabled="true"
																		value="${requestScope.savedReceipt[0].lbxBranchID}" />
																	<html:button property="loanBranchButton"
																		styleId="loanBranchButton"
																		onclick="openLOVCommon(50,'receiptMakerForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifsCode');closeLovCallonLov('bank');"
																		value=" " styleClass="lovbutton"
																		onchange="blankcanforward();"></html:button>

																</div>
															</td>
														</logic:equal>
														<logic:equal name="subReceipt" property="receiptMode"
															value="R">
															<td>
																<html:text styleClass="text" property="branch"
																	styleId="branch" maxlength="100" tabindex="-1"
																	value="${requestScope.savedReceipt[0].branch}"
																	readonly="true" />
																<div id="disIdBranch" style="display: none;">
																	<html:button property="loanBranchButton"
																		styleId="loanBranchButton"
																		onclick="openLOVCommon(50,'receiptMakerForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifsCode');closeLovCallonLov('bank');"
																		value=" " styleClass="lovbutton"
																		onchange="blankcanforward();"></html:button>
																	<html:hidden property="lbxBranchID"
																		styleId="lbxBranchID" disabled="true"
																		value="${requestScope.savedReceipt[0].lbxBranchID}" />
																	
																</div>
															</td>
														</logic:equal>
														<logic:notEqual name="subReceipt" property="receiptMode"
															value="C">
															<logic:notEqual name="subReceipt" property="receiptMode"
																value="S">
																<logic:notEqual name="subReceipt" property="receiptMode"
																	value="N">
																	<logic:notEqual name="subReceipt"
																		property="receiptMode" value="R">
																		<logic:notEqual name="subReceipt"
																			property="receiptMode" value="DIR">
																			<td>
																				<html:text styleClass="text" property="branch"
																					styleId="branch" maxlength="100" tabindex="-1"
																					value="${requestScope.savedReceipt[0].branch}"
																					readonly="true" />

																				<div id="disIdBranch" style="display: inline;">
																					<html:button property="loanBranchButton"
																						styleId="loanBranchButton"
																						onclick="openLOVCommon(50,'receiptMakerForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifsCode');closeLovCallonLov('bank');"
																						value=" " styleClass="lovbutton"
																						onchange="blankcanforward();"></html:button>
																					<html:hidden property="lbxBranchID"
																						styleId="lbxBranchID" disabled="false"
																						value="${requestScope.savedReceipt[0].lbxBranchID}" />
																					<html:hidden property="micr" styleClass="text"
																						styleId="micr"
																						value="${requestScope.savedReceipt[0].micr}" />
																					<html:hidden property="ifsCode" styleId="ifsCode"
																						styleClass="text"
																						value="${requestScope.savedReceipt[0].ifsCode}" />

																				</div>

																			</td>
																		</logic:notEqual>
																	</logic:notEqual>
																</logic:notEqual>
															</logic:notEqual>
														</logic:notEqual>
													</logic:iterate>
												</logic:present>
											</tr>

											<tr>
											   <td width="13%"><bean:message key="lbl.statusReceipt"/></td>
										  <td>
										      	<html:select property="statusReceipt" styleId="statusReceipt" styleClass="text" value="${requestScope.savedReceipt[0].statusReceipt}"onchange="receiptShowHide();" >
										      	
										      		<html:option value="M">Manual</html:option>
										      		<html:option value="A">Auto</html:option>
										      	</html:select>		
									     </td>

											<td>
													<bean:message key="lbl.receiptNo"></bean:message>
													<font color="red">*</font>
												</td>
												<td>
													<html:text property="receiptNo" styleClass="text"
														styleId="receiptNo"
														value="${requestScope.savedReceipt[0].receiptNo}"
														maxlength="20"></html:text>
												</td>
											</tr>

											<tr>
												<td>
													<bean:message key="lbl.receiptAmount"></bean:message>
													<font color="red">*</font>
												</td>
												<td>
													<div style="float: left;">
														<html:text property="receiptAmount" styleClass="text"
															styleId="receiptAmount"
															value="${requestScope.savedReceipt[0].receiptAmount}"
															onkeypress="return numbersonly(event,id,18)"
															onblur="formatNumber(this.value,id);"
															onkeyup="checkNumber(this.value, event, 18,id);"
															onfocus="keyUpNumber(this.value, event, 18,id);"
															onchange="blankcanforward();" style="text-align: right"></html:text>


														<html:hidden property="txnAdvicedID"
															styleId="txnAdvicedID"
															value="${requestScope.savedReceipt[0].txnAdvicedID}" />
														<html:hidden property="hidPmntId" styleId="hidPmntId"
															value="${requestScope.savedReceipt[0].hidPmntId}" />
														<html:hidden property="instrumentID"
															styleId="instrumentID"
															value="${requestScope.savedReceipt[0].instrumentID}"
															style="text-align: right" />
													</div>
												</td>

												<td>
													<bean:message key="lbl.tdsAmount"></bean:message>
												</td>
												
												<td>
												<logic:present name="savedReceipt">
											
												   <logic:iterate name="savedReceipt" id="subSavedReceipt" length="1">
											
												     <logic:equal name="subSavedReceipt" property="loanRepaymentType" value="I">
													     <logic:equal name="tdsreceiptStatus" value="A">
															<html:text property="tdsAmount" styleId="tdsAmount"
																styleClass="text"
																value="${requestScope.savedReceipt[0].tdsAmount}"
																onkeypress="return numbersonly(event,id,18)"
																onblur="formatNumber(this.value,id);"
																onkeyup="checkNumber(this.value, event, 18,id);"
																onfocus="keyUpNumber(this.value, event, 18,id);"
																onchange="blankcanforward();" style="text-align: right"></html:text>
														 </logic:equal>
														 <logic:equal name="tdsreceiptStatus" value="I">
															<html:text property="tdsAmount" styleId="tdsAmount"
																styleClass="text"
																value="${requestScope.savedReceipt[0].tdsAmount}"
																onkeypress="return numbersonly(event,id,18)"
																onblur="formatNumber(this.value,id);"
																onkeyup="checkNumber(this.value, event, 18,id);"
																onfocus="keyUpNumber(this.value, event, 18,id);"
																onchange="blankcanforward();" style="text-align: right"></html:text>
														 </logic:equal>
														 
														  <logic:equal name="tdsreceiptStatus" value="B">
															<html:text property="tdsAmount" styleId="tdsAmount"
																styleClass="text"
																value="${requestScope.savedReceipt[0].tdsAmount}"
																onkeypress="return numbersonly(event,id,18)"
																onblur="formatNumber(this.value,id);"
																onkeyup="checkNumber(this.value, event, 18,id);"
																onfocus="keyUpNumber(this.value, event, 18,id);"
																onchange="blankcanforward();" style="text-align: right" readonly="true"></html:text>
														 </logic:equal>
														 <logic:equal name="tdsreceiptStatus" value="N">
															<html:text property="tdsAmount" styleId="tdsAmount"
																styleClass="text"
																value="${requestScope.savedReceipt[0].tdsAmount}"
																onkeypress="return numbersonly(event,id,18)"
																onblur="formatNumber(this.value,id);"
																onkeyup="checkNumber(this.value, event, 18,id);"
																onfocus="keyUpNumber(this.value, event, 18,id);"
																onchange="blankcanforward();" style="text-align: right" readonly="true"></html:text>
														 </logic:equal>
														 
													 </logic:equal>
													 <logic:equal name="subSavedReceipt" property="loanRepaymentType" value="N">
													      <logic:equal name="tdsreceiptStatus" value="A">
															<html:text property="tdsAmount" styleId="tdsAmount"
																styleClass="text"
																value="${requestScope.savedReceipt[0].tdsAmount}"
																onkeypress="return numbersonly(event,id,18)"
																onblur="formatNumber(this.value,id);"
																onkeyup="checkNumber(this.value, event, 18,id);"
																onfocus="keyUpNumber(this.value, event, 18,id);"
																onchange="blankcanforward();" style="text-align: right"></html:text>
														 </logic:equal>
														 <logic:equal name="tdsreceiptStatus" value="N">
															<html:text property="tdsAmount" styleId="tdsAmount"
																styleClass="text"
																value="${requestScope.savedReceipt[0].tdsAmount}"
																onkeypress="return numbersonly(event,id,18)"
																onblur="formatNumber(this.value,id);"
																onkeyup="checkNumber(this.value, event, 18,id);"
																onfocus="keyUpNumber(this.value, event, 18,id);"
																onchange="blankcanforward();" style="text-align: right"></html:text>
														 </logic:equal>
														 
														  <logic:equal name="tdsreceiptStatus" value="B">
															<html:text property="tdsAmount" styleId="tdsAmount"
																styleClass="text"
																value="${requestScope.savedReceipt[0].tdsAmount}"
																onkeypress="return numbersonly(event,id,18)"
																onblur="formatNumber(this.value,id);"
																onkeyup="checkNumber(this.value, event, 18,id);"
																onfocus="keyUpNumber(this.value, event, 18,id);"
																onchange="blankcanforward();" style="text-align: right" readonly="true"></html:text>
														 </logic:equal>
														 <logic:equal name="tdsreceiptStatus" value="I">
															<html:text property="tdsAmount" styleId="tdsAmount"
																styleClass="text"
																value="${requestScope.savedReceipt[0].tdsAmount}"
																onkeypress="return numbersonly(event,id,18)"
																onblur="formatNumber(this.value,id);"
																onkeyup="checkNumber(this.value, event, 18,id);"
																onfocus="keyUpNumber(this.value, event, 18,id);"
																onchange="blankcanforward();" style="text-align: right" readonly="true"></html:text>
														 </logic:equal>
													 </logic:equal>
												 </logic:iterate>
												</logic:present>
												
												</td>
											</tr>

											<tr>
												<td>
													<bean:message key="lbl.totalRecevable"></bean:message>
												</td>
												<td>
													<html:text styleClass="text" property="totalRecevable"
														styleId="totalRecevable" readonly="true" value="${amount}"
														style="text-align: right" />
												</td>
												
												<td>
													<bean:message key="lbl.depositBnkAcc" />
												</td>
												<td>
													<html:text styleClass="text" property="depositBankAccount"
														styleId="depositBankAccount" maxlength="50"
														value="${requestScope.savedReceipt[0].depositBankAccount}"
														readonly="true" />
													<html:button property="bButton" styleId="bButton"
														onclick="openDepositBranchLov();" value=" "
														styleClass="lovbutton"></html:button>
													<html:hidden property="depositBankID"
														styleId="depositBankID"
														value="${requestScope.savedReceipt[0].depositBankID}" />
													<html:hidden property="depositBranchID"
														styleId="depositBranchID"
														value="${requestScope.savedReceipt[0].depositBranchID}" />
													<html:hidden property="depositMicr" styleId="depositMicr"
														value="${requestScope.savedReceipt[0].depositMicr}" />
													<html:hidden property="depositIfscCode"
														styleId="depositIfscCode"
														value="${requestScope.savedReceipt[0].depositIfscCode}" />
													<input type="hidden" name="temp" id="temp" />
												</td>

											</tr>
											<tr>
												<td>
													<bean:message key="lbl.receivedFrom"></bean:message>
												</td>
												<td>
													<html:text styleClass="text" property="receivedFrom"
														styleId="receivedFrom" maxlength="80" value="${requestScope.savedReceipt[0].receivedFrom}"
														style="text-align: right" onchange="blankcanforward();"/>
												</td>
												<td>
													<bean:message key="lbl.contactNoReceipt" />
												</td>
												<td>
													<html:text styleClass="text" property="contactNo"
														styleId="contactNo" maxlength="10"  value="${requestScope.savedReceipt[0].contactNo}"
														style="text-align: right" onkeypress="return numericOnlyAtReceipt(event,20);"  onchange="blankcanforward();"/>
												</td>

											</tr>
											<tr>
												<td>
													<bean:message key="lbl.makerRemark"></bean:message>
												</td>
												<td>
													<textarea name="remarks" id="remarks" maxlength="1000"
														class="text" onchange="blankcanforward();">${requestScope.savedReceipt[0].remarks}</textarea>
												</td>
												<td>
													<bean:message key="lbl.authorRemarks" />
												</td>
												<td>
													<html:textarea styleClass="text" property="authorRemarks"
														readonly="true"
														value="${requestScope.savedReceipt[0].authorRemarks}"
														onchange="blankcanforward();" />
												</td>

											</tr>

										</logic:present>
									</tr>
									<tr>
										<logic:present name="datatoapproveList">
											<input type="hidden" id="modifyRecord" name="modifyRecord"
												value="V" />
											<html:hidden property="loanId" styleId="loanId"
												value="${sessionScope.datatoapproveList[0].lbxLoanNoHID}" />

											<td>
												<bean:message key="lbl.loanAccount"></bean:message>
											</td>
											<td>

												<html:text styleClass="text" property="loanAccountNumber"
													styleId="loanAccountNumber" maxlength="100"
													value="${sessionScope.datatoapproveList[0].loanAccountNumber}"
													readonly="true" disabled="true" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="${sessionScope.datatoapproveList[0].lbxLoanNoHID}" />

											</td>
											<td>
												<bean:message key="lbl.customerName"></bean:message>
											</td>
											<td>
												<a rel="tooltip3" id="tool"> <html:text
														property="customerName" styleClass="text"
														styleId="customerName"
														value="${sessionScope.datatoapproveList[0].customerName}"
														onmouseover="applyToolTip(id);" maxlength="100"
														disabled="true"></html:text> </a>
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
											</td>
									</tr>
									<tr>

										<td>
											<bean:message key="lbl.businessPartnerType"></bean:message>
										</td>
										<td>
											<html:text styleClass="text" property="businessPartnerType"
												styleId="businessPartnerType" maxlength="100"
												value="${sessionScope.datatoapproveList[0].businessPartnerType}"
												disabled="true" />
											<html:hidden property="lbxBPType" styleId="lbxBPType"
												value="${sessionScope.datatoapproveList[0].lbxBPType}" />
										</td>

										<td>
											<bean:message key="lbl.businessPartnerName"></bean:message>
										</td>
										<td>

											<html:text styleClass="text" property="businessPartnerName"
												styleId="businessPartnerName" maxlength="100"
												value="${sessionScope.datatoapproveList[0].businessPartnerName}"
												disabled="true" />
											<html:hidden property="lbxBPNID" styleId="lbxBPNID"
												value="${sessionScope.datatoapproveList[0].lbxBPNID}" />

										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.receiptMode"></bean:message>
										</td>
										<td>
											<span style="float: left;"> <html:select
													property="receiptMode" styleId="receiptMode"
													styleClass="text"
													value="${sessionScope.datatoapproveList[0].receiptMode}"
													disabled="true">
													<html:option value="">--Select--</html:option>
													<html:option value="C">Cash</html:option>
													<html:option value="Q">Cheque</html:option>
													<html:option value="D">DD</html:option>
													<html:option value="N">NEFT</html:option>
													<html:option value="R">RTGS</html:option>
													<html:option value="DIR">Direct Debit</html:option>
													<html:option value="S">Adjustment</html:option>
												</html:select> </span>
										</td>
										<td>
											<bean:message key="lbl.receiptDate"></bean:message>
											<bean:message key="lbl.dateFormat(dd-mm-yyyy)" />
										</td>
										<td>
											<html:text property="receiptDate" styleClass="text"
												styleId="receiptAmount"
												value="${sessionScope.datatoapproveList[0].receiptDate}"
												maxlength="100" disabled="true" readonly="true"></html:text>
										</td>


									</tr>
									<tr>

										<td>
											<bean:message key="lbl.instrumentNumber"></bean:message>
										</td>
										<td>
											<div style="float: left;">
												<html:text property="instrumentNumber" styleClass="text"
													styleId="instrumentNumber"
													value="${sessionScope.datatoapproveList[0].instrumentNumber}"
													maxlength="100" disabled="true"></html:text>
											</div>
										</td>
										<td>
											<bean:message key="lbl.instrumentDate"></bean:message>
											<bean:message key="lbl.dateFormat(dd-mm-yyyy)" />
										</td>
										<td>
									
										<logic:equal name="receiptCheck"  value="DIR" >
											<html:text property="instrumentDate" styleClass="text"
												styleId="instrumentDate"
												value=""
												maxlength="100" disabled="true"></html:text>
      								    </logic:equal>
      								    
      								   <logic:notEqual name="receiptCheck" value="DIR" >
											<html:text property="instrumentDate" styleClass="text"
												styleId="instrumentDate"
												value="${sessionScope.datatoapproveList[0].instrumentDate}"
												maxlength="100" disabled="true"></html:text>
      								    </logic:notEqual>


										</td>


									</tr>
									<tr>

										<td>
											<bean:message key="lbl.IssuingBank"></bean:message>
										</td>
										<td>
											<div style="float: left;">


												<html:text styleClass="text" property="bank" styleId="bank"
													maxlength="50"
													value="${sessionScope.datatoapproveList[0].bank}"
													disabled="true" />
												<html:hidden property="lbxBankID" styleId="lbxBankID"
													value="${sessionScope.datatoapproveList[0].lbxBankID}" />


											</div>
										</td>
										<td>
											<bean:message key="lbl.Issuingbranch"></bean:message>
										</td>
										<td>

											<html:text styleClass="text" property="branch"
												styleId="branch" maxlength="100"
												value="${sessionScope.datatoapproveList[0].branch}"
												disabled="true" />
											<html:hidden property="lbxBranchID" styleId="lbxBranchID"
												value="${sessionScope.datatoapproveList[0].lbxBranchID}" />
                                            
											<html:hidden property="micr" styleClass="text"
															styleId="micr" value="" />
											<html:hidden property="ifsCode" styleId="ifsCode"
															styleClass="text" value="" />

										</td>

									</tr>

									<tr>
										   <td width="13%"><bean:message key="lbl.statusReceipt"/></td>
		  <td>
		      	<html:select property="statusReceipt" styleId="statusReceipt" styleClass="text" value="${sessionScope.datatoapproveList[0].statusReceipt}" onchange="receiptShowHide();" disabled="true">
		      
		      		<html:option value="M">Manual</html:option>
		      		<html:option value="A">Auto</html:option>
		      	</html:select>		
	     </td>
									

										<td>
											<bean:message key="lbl.receiptNo"></bean:message>
											<font color="red">*</font>
										</td>
										<td>
											<html:text property="receiptNo" styleClass="text"
												styleId="receiptNo"
												value="${sessionScope.datatoapproveList[0].receiptNo}"
												maxlength="20" readonly="true"></html:text>
										</td>
									</tr>

									<tr>
										<td>
											<bean:message key="lbl.receiptAmount"></bean:message>
											<font color="red">*</font>
										</td>
										<td>
											<div style="float: left;">
												<html:text property="receiptAmount" styleClass="text"
													styleId="receiptAmount" disabled="true"
													value="${sessionScope.datatoapproveList[0].receiptAmount}"
													onkeypress="return numbersonly(event,id,18)"
													onblur="formatNumber(this.value,id);"
													onkeyup="checkNumber(this.value, event, 18,id);"
													onfocus="keyUpNumber(this.value, event, 18,id);"
													style="text-align: right"></html:text>

											</div>
										</td>
										<td>
											<bean:message key="lbl.tdsAmount"></bean:message>
										</td>
										<td>
											<html:text property="tdsAmount" styleId="tdsAmount"
												styleClass="text"
												value="${sessionScope.datatoapproveList[0].tdsAmount}"
												maxlength="18" disabled="true" style="text-align: right"></html:text>
										</td>


									</tr>

									<tr>
										<td>
											<bean:message key="lbl.totalRecevable"></bean:message>
										</td>
										<td>
											<input type="text" name="totalRecevable" id="totalRecevable"
												readonly="readonly" value="0.00" style="text-align: right" />
										</td>

										<td>
											<bean:message key="lbl.depositBnkAcc" />
										</td>
										<td>
											<html:text styleClass="text" property="depositBankAccount"
												styleId="depositBankAccount" maxlength="50" value="${sessionScope.datatoapproveList[0].depositBankAccount}"
												readonly="true" />
											<html:hidden property="depositBankID" styleId="depositBankID" />
											<html:hidden property="depositBranchID"
												styleId="depositBranchID" />
											<html:hidden property="depositMicr" styleId="depositMicr" />
											<html:hidden property="depositIfscCode"
												styleId="depositIfscCode" />
											<input type="hidden" name="temp" id="temp" />
										</td>
									</tr>
									<tr>
												<td>
													<bean:message key="lbl.receivedFrom"></bean:message>
												</td>
												<td>
													<html:text styleClass="text" property="receivedFrom"
														styleId="receivedFrom"  disabled="true" value="${sessionScope.datatoapproveList[0].receivedFrom}"
														style="text-align: right" onchange="blankcanforward();"/>
												</td>
												<td>
													<bean:message key="lbl.contactNoReceipt" />
												</td>
												<td>
													<html:text styleClass="text" property="contactNo"
														styleId="contactNo" disabled="true" value="${sessionScope.datatoapproveList[0].contactNo}"
														style="text-align: right" onchange="blankcanforward();"/>
												</td>

											</tr>
									<tr>
										<td>
											<bean:message key="lbl.makerRemark"></bean:message>
										</td>
										<td>
											<textarea name="remarks" id="remarks" disabled="disabled"
												class="text">${sessionScope.datatoapproveList[0].remarks}</textarea>
										</td>

										<td nowrap="nowrap">
											<bean:message key="lbl.authorRemarks" />
										</td>
										<td nowrap="nowrap">
											<html:textarea styleClass="text" property="authorRemarks"
												readonly="true"
												value="${sessionScope.datatoapproveList[0].authorRemarks}" />
										</td>

									</tr>

									</logic:present>

									<logic:notPresent name="savedReceipt">
										<logic:notPresent name="datatoapproveList">
											<input type="hidden" id="modifyRecord" name="modifyRecord"
												value="I" />
											<tr>

												<td>
													<input type="hidden" value='${requestScope.laonId}'
														id="loanId" name="loanId" />
													<bean:message key="lbl.loanAccount"></bean:message>
													<font color="red">*</font>
												</td>
												<td>

													<html:text styleClass="text" property="loanAccountNumber"
														readonly="true" styleId="loanAccountNumber" maxlength="20"
														value="${bpList[0].loanAccountNumber}" tabindex="-1" />
														<html:hidden property="loanRefernceNo"
															styleId="loanRefernceNo"
															value="" />
													<input type="hidden" id="assetDesc" name="assetDesc" value="" />
													<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID" />
													<html:button property="loanAccountButton"
														styleId="loanAccountButton"
														onclick="closeLovCallonLov1();openLOVCommon(265,'receiptMakerForm','loanAccountNumber','','', '','','getDefaultBusinessPartnerTypeCS','loanRefernceNo','customerName','assetDesc')"
														value=" " styleClass="lovbutton"></html:button>


												</td>
												<td>
													<bean:message key="lbl.customerName"></bean:message>
													<font color="red">*</font>
												</td>
												<td>
													<a rel="tooltip3" id="tool"> <html:text
															property="customerName" styleClass="text"
															styleId="customerName" value="${bpList[0].customerName}"
															onmouseover="applyToolTip(id);" maxlength="50"
															readonly="true"></html:text> </a>
													<input type="hidden" name="contextPath"
														value="<%=request.getContextPath()%>" id="contextPath" />
												</td>
											</tr>
<!-- 											Nishant space starts -->
<!-- 											<tr> -->
<%-- 												<td><bean:message key="lbl.vehicleNo"></bean:message></td> --%>
<!-- 												<td> -->
<%-- 													<html:text styleClass="text" property="vehicleNo" readonly="true" styleId="vehicleNo" maxlength="20" value="${bpList[0].vehicleNo}" tabindex="-1" /> --%>
<%-- 													<html:button property="vehicleButton" styleId="vehicleButton" onclick="closeLovCallonLov1();openLOVCommon(576,'receiptMakerForm','vehicleNo','','', '','','getDefaultBusinessPartnerTypeVeh','vehicleNo','loanRefernceNo','customerName')" value=" " styleClass="lovbutton"></html:button> --%>
<!-- 												</td> -->
<!-- 											</tr> -->
<!-- 											Nishant space starts -->
											<tr>
												<td>
													<bean:message key="lbl.businessPartnerType"></bean:message>
													<font color="red">* </font>
												</td>
												<td>
													<html:text styleClass="text" property="businessPartnerType"
														styleId="businessPartnerType" maxlength="50" value="${bpList[0].businessPartnerType}"
														readonly="true" tabindex="-1" />
													<html:hidden property="lbxBPType" styleId="lbxBPType" value="${bpList[0].lbxBPType}"/>
													<html:button property="businessPartnerButton"
														styleId="businessPartnerButton"
														onclick="openLOVCommon(147,'receiptMakerForm','businessPartnerType','loanAccountNumber','lbxBPType', 'lbxLoanNoHID','Select loanAccountNumber','getDefaultBusinessPartnerTypeReceipt','lbxBPNID','businessPartnerName');closeLovCallonLov('loanAccountNumber');"
														value=" " styleClass="lovbutton"></html:button>




												</td>

												<td>
													<bean:message key="lbl.businessPartnerName"></bean:message>
												</td>
												<td>

													<html:text styleClass="text" property="businessPartnerName"
														styleId="businessPartnerName" maxlength="50" value="${bpList[0].businessPartnerName}"
														readonly="true" tabindex="-1" />
													<html:hidden property="lbxBPNID" styleId="lbxBPNID" value="${bpList[0].lbxBPNID}"/>
												</td>
											</tr>
											<tr>
												<td>
													<bean:message key="lbl.receiptMode"></bean:message>
													<font color="red">*</font>
												</td>
												<td>
													<span style="float: left;"> <html:select
															property="receiptMode" styleId="receiptMode"
															styleClass="text"
															onchange="getCashDepositAccount();depositShowHide();fnReceiptNull();cashAccountDisable()">
															<html:option value="">--Select--</html:option>
															<html:option value="C">Cash</html:option>
															<html:option value="Q">Cheque</html:option>
															<html:option value="D">DD</html:option>
															<html:option value="N">NEFT</html:option>
															<html:option value="R">RTGS</html:option>
															<html:option value="DIR">Direct Debit</html:option>
															<html:option value="S">Adjustment</html:option>
														</html:select> <input type="hidden" name="lbxreceiptMode"
															id="lbxreceiptMode" /> </span>
												</td>

												<td>
													<bean:message key="lbl.receiptDate"></bean:message>
													<bean:message key="lbl.dateFormat(dd-mm-yyyy)" />
													<font color="red">*</font>
												</td>
												<td>
													<html:text property="receiptDate" styleClass="text"
														styleId="receiptDate" value="${userobject.businessdate}"
														maxlength="10" onchange="return checkDate('receiptDate');"></html:text>
												</td>


											</tr>
											<tr>

												<td>
													<bean:message key="lbl.instrumentNumber"></bean:message>
													<font color="red">*</font>
												</td>
												<td>
													<div style="float: left;">
														<html:text property="instrumentNumber" styleClass="text"
															styleId="instrumentNumber" value="" maxlength="20"></html:text>
														<input type="hidden" name="instrumentID" id="instrumentID"
															value="" />
													</div>
												</td>
												<td>
													<bean:message key="lbl.instrumentDate"></bean:message>
													<bean:message key="lbl.dateFormat(dd-mm-yyyy)" />
													<font color="red">*</font>
												</td>
												<td>
													<div id="disIdInsD" style="display: inline;">
														<html:text property="instrumentDate" styleClass="text"
															styleId="instrumentDate"
															value="${userobject.businessdate}" maxlength="10"
															onchange="return checkDate('instrumentDate');"></html:text>
													</div>
													<div id="disIdIns" style="display: none;">
														<html:text property="instrumentDate" styleClass="text"
															styleId="instrumentDateDis" readonly="true" value=""
															maxlength="100"></html:text>
													</div>
												</td>
											</tr>
											<tr>



												<td>
													<bean:message key="lbl.IssuingBank"></bean:message>
												</td>

												<td>
													<html:text styleClass="text" property="bank" styleId="bank"
														maxlength="100" tabindex="-1" readonly="true" />
													<html:hidden property="lbxBankID" styleId="lbxBankID" />
													<div id="disId" style="display: inline;">
														<html:button property="loanBankButton"
															styleId="loanBankButton"
															onclick="closeLovCallonLov1();openLOVCommon(7,'receiptMakerForm','bank','','branch', 'lbxBranchID','','','lbxBankID')"
															value=" " styleClass="lovbutton"></html:button>

													</div>
												</td>
												<td>
													<bean:message key="lbl.Issuingbranch"></bean:message>
												</td>
												<td>
													<html:text styleClass="text" property="branch"
														styleId="branch" maxlength="100" tabindex="-1"
														readonly="true" />
                                                    <html:hidden property="lbxBranchID" styleId="lbxBranchID" />
														<html:hidden property="micr" styleClass="text"
															styleId="micr" value="" />
														<html:hidden property="ifsCode" styleId="ifsCode"
															styleClass="text" value="" />
													<div id="disIdBranch" style="display: inline;">
														<html:button property="loanBranchButton"
															styleId="loanBranchButton"
															onclick="openLOVCommon(50,'receiptMakerForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifsCode');closeLovCallonLov('bank');"
															value=" " styleClass="lovbutton"></html:button>
														
													</div>
												</td>
											</tr>

											<tr>
											
											   <td width="13%"><bean:message key="lbl.statusReceipt"/></td>
		  <td>
		      	<html:select property="statusReceipt" styleId="statusReceipt" styleClass="text" onchange="receiptShowHide();">
		      	
		      		<html:option value="M">Manual</html:option>
		      		<html:option value="A">Auto</html:option>
		      	</html:select>		
	     </td>					



												<td>
													<bean:message key="lbl.receiptNo"></bean:message>
													<font color="red">*</font>
												</td>
												<td>
													<html:text property="receiptNo" styleClass="text"
														styleId="receiptNo" value="" 
														maxlength="20"></html:text>
												</td>
											</tr>

											<tr>

												<td>
													<bean:message key="lbl.receiptAmount"></bean:message>
													<font color="red">*</font>
												</td>
												<td>
													<div style="float: left;">
														<html:text property="receiptAmount" styleClass="text"
															styleId="receiptAmount" value=""
															onkeypress="return numbersonly(event,id,18)"
															onblur="formatNumber(this.value,id);"
															onkeyup="checkNumber(this.value, event, 18,id);"
															onfocus="keyUpNumber(this.value, event, 18,id);"
															style="text-align: right"></html:text>

													</div>
												</td>
												<td>
													<bean:message key="lbl.tdsAmount"></bean:message>
												</td>
												<td>
													<html:text property="tdsAmount" styleId="tdsAmount"
														styleClass="text" value=""
														onkeypress="return numbersonly(event,id,18)"
														onblur="formatNumber(this.value,id);"
														onkeyup="checkNumber(this.value, event, 18,id);"
														onfocus="keyUpNumber(this.value, event, 18,id);"
														style="text-align: right"></html:text>
												</td>


											</tr>
											<!-- Nishant space starts for Deposit receipt Lov -->
											<tr>

												<td>
													<bean:message key="lbl.totalRecevable"></bean:message>
												</td>
												<td>
													<input type="text" name="totalRecevable"
														id="totalRecevable" readonly="readonly" value="${amount}"
														style="text-align: right" />
												</td>
												<td>
													<bean:message key="lbl.depositBnkAcc" />
												</td>
												<td>
													<html:text styleClass="text" property="depositBankAccount"
														styleId="depositBankAccount" maxlength="50" value=""
														readonly="true" />
													<html:button property="bButton" styleId="bButton"
														onclick="openDepositBranchLov();" value=" "
														styleClass="lovbutton"></html:button>
													<html:hidden property="depositBankID"
														styleId="depositBankID" />
													<html:hidden property="depositBranchID"
														styleId="depositBranchID" />
													<html:hidden property="depositMicr" styleId="depositMicr" />
													<html:hidden property="depositIfscCode"
														styleId="depositIfscCode" />
													<input type="hidden" name="temp" id="temp" />
												</td>



											</tr>
											<!-- Nishant space ends -->
                                           <tr>
												<td>
													<bean:message key="lbl.receivedFrom"></bean:message>
												</td>
												<td>
													<html:text styleClass="text" property="receivedFrom"
														styleId="receivedFrom"  value=""
														style="text-align: right" maxlength="80" onchange="blankcanforward();"/>
												</td>
												<td>
													<bean:message key="lbl.contactNoReceipt" />
												</td>
												<td>
													<html:text styleClass="text" property="contactNo"
														styleId="contactNo" value="" maxlength="10"
														style="text-align: right" onkeypress="return numericOnlyAtReceipt(event,20);" onchange="blankcanforward();"/>
												</td>

											</tr>
											<tr>
												<td>
													<bean:message key="lbl.makerRemark"></bean:message>
												</td>
												<td>
													<textarea name="remarks" id="remarks" maxlength="1000"
														class="text"></textarea>
												</td>

												<td nowrap="nowrap">
													<bean:message key="lbl.authorRemarks" />
												</td>
												<td nowrap="nowrap">
													<html:textarea styleClass="text" property="authorRemarks"
														readonly="true"
														value="${requestScope.savedReceipt[0].authorRemarks}" />
												</td>

											</tr>

										</logic:notPresent>
									</logic:notPresent>



								</table>
							</td>
						</tr>
						<!-- Nishant space strats for charge Allocation Details-->
						<logic:notPresent name="datatoapproveList">
							<logic:present name="charges">
								<input type="hidden" name="allocGridFlag" id="allocGridFlag"
									value="Y" />
							</logic:present>
							<logic:notPresent name="charges">
								<input type="hidden" name="allocGridFlag" id="allocGridFlag"
									value="N" />
							</logic:notPresent>
							<logic:present name="charges">
								<tr>

									<td>
										<fieldset>
											<legend>
												<bean:message key="lbl.allocationDtl" />
											</legend>
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td class="gridtd">
														<table id="chargeTable" width="100%" border="0"
															cellspacing="1" cellpadding="1">
															<tr class="white2">

																<td>
																	<b><bean:message key="lbl.chargeDesc" /> </b>
																</td>

																<td>
																	<b><bean:message key="lbl.balanceAmount" /> </b>
																</td>
																<td>
																	<b><bean:message key="lbl.allocatedAmount" /> </b>
																</td>
																<td>
																	<b><bean:message key="lbl.tdsAllocatedAmount" /> </b>
																</td>
															</tr>

															<logic:iterate name="charges" id="subcharges" indexId="i">
															
																														
																<tr class="white1">
                                                              
																	<td>
																		${subcharges.allocChargeDesc}
																		<input type="hidden" name="allocChargeCodeArray"
																			id="allocChargeCode${i+1}"
																			value="${subcharges.allocChargeCode}"  />
																		<input type="hidden" name="allocAdviceAmountArray"
																			id="allocAdviceAmount${i+1}"
																			value="${subcharges.allocAdviceAmount}"  />
																		<input type="hidden" name="allocAdjustedAmountArray"
																			id="allocAdjustedAmount${i+1}"
																			value="${subcharges.allocAdjustedAmount}"  />
																		<input type="hidden" name="allocAmountInProcessArray"
																			id="allocAmountInProcess${i+1}"
																			value="${subcharges.allocAmountInProcess}"  />
																			
																	</td>

																	<td>
																	
																		<input type="text" name="allocBalAmountArray"
																			id="allocBalAmount${i+1}"
																			value="${subcharges.allocBalAmount}" readonly="readonly" />
																	</td>

																	<td>
																		<logic:equal name="subcharges" property="allocAmount" value="0.00">
																		
																			<html:text styleClass="text" style="text-align: right"
																				styleId="allocAmount${i+1}"
																				property="allocAmountStringArray"
																				value="${requestScope.savedReceipt[0].allocAmountStringArray[i]}"
																				onkeypress="return numbersonly(event, id, 18);"
																				onblur="formatNumber(this.value, id);"
																				onkeyup="checkNumber(this.value, event, 18, id);"
																				onfocus="keyUpNumber(this.value, event, 18, id);" 
																				onchange="getTotalAllocatedAmt();" />
																		</logic:equal>
																		<logic:notEqual name="subcharges" property="allocAmount" value="0.00">
																		
																			<html:text styleClass="text" style="text-align: right"
																				styleId="allocAmount${i+1}"
																				property="allocAmountStringArray"
																				value="${subcharges.allocAmount} "
																				onkeypress="return numbersonly(event, id, 18);"
																				onblur="formatNumber(this.value, id);"
																				onkeyup="checkNumber(this.value, event, 18, id);"
																				onfocus="keyUpNumber(this.value, event, 18, id);" 
																				onchange="getTotalAllocatedAmt();" />
																		</logic:notEqual>
																	</td>
																	<td>
																	<logic:equal name="subcharges" property="allocTdsAllocatedAmount" value="0.00">
																		<html:text styleClass="text" style="text-align: right"
																			styleId="allocTdsAllocatedAmount${i+1}"
																			property="allocTdsAllocatedAmountStringArray"
																			value="${requestScope.savedReceipt[0].allocTdsAllocatedAmountStringArray[i]}"
																			onkeypress="return numbersonly(event, id, 18);"
																			onblur="formatNumber(this.value, id);"
																			onkeyup="checkNumber(this.value, event, 18, id);"
																			onfocus="keyUpNumber(this.value, event, 18, id);" 
																			onchange="getTotalTDSAmt();" />
																	</logic:equal>
																	<logic:notEqual name="subcharges" property="allocTdsAllocatedAmount" value="0.00">
																		<html:text styleClass="text" style="text-align: right"
																			styleId="allocTdsAllocatedAmount${i+1}"
																			property="allocTdsAllocatedAmountStringArray"
																			value="${subcharges.allocTdsAllocatedAmount}"
																			onkeypress="return numbersonly(event, id, 18);"
																			onblur="formatNumber(this.value, id);"
																			onkeyup="checkNumber(this.value, event, 18, id);"
																			onfocus="keyUpNumber(this.value, event, 18, id);" 
																			onchange="getTotalTDSAmt();" />
																	</logic:notEqual>
																	</td>
																</tr>
															</logic:iterate>




														</table>
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
												<bean:message key="lbl.otherChargeAllocationDtl" />
											</legend>
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td class="gridtd">
														<input type="hidden" name="psize" id="psize" />
														<input type="hidden" name="pcheck" id="pcheck" />
														<table id="otherGridtable" style="width: 100%;" border="0"
															cellspacing="1" cellpadding="4">
															<tr class="white2">
																<td>
																	<b><bean:message key="lbl.select" /> </b>
																</td>
																<td>
																	<b><bean:message key="lbl.chargeDesc" /> </b>
																</td>

																<td>
																	<b><bean:message key="lbl.balanceAmount" /> </b>
																</td>
																<td>
																	<b><bean:message key="lbl.allocatedAmount" /> </b>
																</td>
																<td>
																	<b><bean:message key="lbl.tdsAllocatedAmount" /> </b>
																</td>
															</tr>

															<logic:present name="otherAddCharges">
																<logic:notEmpty name="otherAddCharges">
															
																	<logic:iterate name="otherAddCharges"
																		id="subOtherAddCharges" indexId="i">
																		 
																		<tr class="white1" style="width: 25px;">
																			<td>
																			   
																				<input type="checkbox" name="chk" id="chk<%=i + 1%>"
																					value="" />
																			</td>
																			<td>
																			    <html:text styleClass="text" property="allocChargeDes" readonly="true" tabindex="-1" styleId="allocChargeDes<%=i + 1%>" value="${subOtherAddCharges.otherAddChargeDesc }"/>
																				
																				<html:hidden  property="lbxAllocChargeStringArray"  styleId="lbxAllocChargeStringArray<%=i + 1%>" value="${subOtherAddCharges.otherAddChargeCode }"/>
																				
																				
																				<input type="hidden" name="temp" value="" id="temp" />
																				<html:button property="docButton"
																					styleId="docButton"
																					onclick="openLOVCommon(540,'receiptMakerForm','allocChargeDes${i+1}','','', '','','','lbxAllocChargeStringArray${i+1}');closeLovCallonLov1();"
																					value=" " styleClass="lovbutton">
																				</html:button>
																			</td>
																			<td>
																			  <html:text styleClass="text" property="allocOtherBalAmountStringArray" readonly="true" tabindex="-1" styleId="otherAddBalanceAmount<%=i + 1%>" value="${subOtherAddCharges.otherAddBalanceAmount }"/>
																				
																			</td>
																			<td>
																			  
																	            <html:text styleClass="text" property="allocOtherAmountStringArray" onchange="getTotalAllocatedAmt();" styleId="otherAddAllocateAmount<%=i + 1%>" value="${subOtherAddCharges.otherAddAllocateAmount } "/>
																																				
																			 
																			</td>
																			<td>
																			   
																		      <html:text styleClass="text" property="allocTdsOtherAllAmtStringArray" onchange="getTotalTDSAmt();" styleId="otherAddTDSAmount<%=i + 1%>" value="${subOtherAddCharges.otherAddTDSAmount } "/>
																																							  
																			</td>
																		</tr>
																	</logic:iterate>
																</logic:notEmpty>
																<logic:empty name="otherAddCharges">
																
																<logic:notPresent name="otherChargeCodeList">														       
															     
																	<tr class="white1" style="width: 25px;">
																		<td>
																			<input type="checkbox" name="chk" id="chk1" value="" />
																		</td>
																		<td>
																			 <html:text  property="allocChargeDes"
																				styleId="allocChargeDes1" value="" readonly="readonly"
																				styleClass="text" tabindex="-1" />
																			<html:hidden property="lbxAllocChargeStringArray"
																				styleId="lbxAllocChargeStringArray1" value="" />
																			<input type="hidden" name="temp" value="" id="temp" />
																			<html:button property="docButton" styleId="docButton"
																				onclick="openLOVCommon(540,'receiptMakerForm','lbxAllocChargeStringArray1','','', '','','','allocChargeDes1');closeLovCallonLov1();"
																				value=" " styleClass="lovbutton">
																			</html:button>
																		</td>
																		<td>
																			 <html:text
																				property="allocOtherBalAmountStringArray"
																				styleId="otherAddBalanceAmount1" value="0.00"
																				readonly="true" styleClass="text" tabindex="-1"
																				/>
																		</td>
																		<td>
																		
																			 <html:text property="allocOtherAmountStringArray"
																				styleId="otherAddAllocateAmount1" value="0.00"
																				styleClass="text"
																				onchange="getTotalAllocatedAmt();" />
																		</td>
																		<td>
																			<html:text
																				property="allocTdsOtherAllAmtStringArray"
																				styleId="otherAddTDSAmount1" value="0.00" styleClass="text"
																				onchange="getTotalTDSAmt();" />
																		</td>
																	</tr>
																	
                                                               
                                                                 </logic:notPresent>
																	<logic:present name="otherChargeCodeList">														       
															      <logic:iterate name="otherChargeCodeList"
																		id="subal2" indexId="i">
																	<tr class="white1" style="width: 25px;">
																		<td>
																			<input type="checkbox" name="chk" id="chk${i+1 }" value="" />
																		</td>
																		<td>
																			 <html:text  property="allocChargeDes"
																				styleId="allocChargeDes${i+1 }" value="${requestScope.savedReceipt[0].allocChargeDes[i]}" readonly="readonly"
																				styleClass="text" tabindex="-1" />
																			<html:hidden property="lbxAllocChargeStringArray"
																				styleId="lbxAllocChargeStringArray${i+1 }" value="${requestScope.savedReceipt[0].lbxAllocChargeStringArray[i]}" />
																			<input type="hidden" name="temp" value="" id="temp" />
																			<html:button property="docButton" styleId="docButton"
																				onclick="openLOVCommon(540,'receiptMakerForm','lbxAllocChargeStringArray${i+1 }','','', '','','','allocChargeDes${i+1 }');closeLovCallonLov1();"
																				value=" " styleClass="lovbutton">
																			</html:button>
																		</td>
																		<td>
																			 <html:text
																				property="allocOtherBalAmountStringArray"
																				styleId="otherAddBalanceAmount${i+1 }" value="${requestScope.savedReceipt[0].allocOtherBalAmountStringArray[i]}"
																				readonly="true" styleClass="text" tabindex="-1"
																				/>
																		</td>
																		<td>
																		
																			 <html:text property="allocOtherAmountStringArray"
																				styleId="otherAddAllocateAmount${i+1 }" value="${requestScope.savedReceipt[0].allocOtherAmountStringArray[i]}"
																				styleClass="text"
																				onchange="getTotalAllocatedAmt();" />
																		</td>
																		<td>
																			<html:text
																				property="allocTdsOtherAllAmtStringArray"
																				styleId="otherAddTDSAmount${i+1 }" value="${requestScope.savedReceipt[0].allocTdsOtherAllAmtStringArray[i]}" styleClass="text"
																				onchange="getTotalTDSAmt();" />
																		</td>
																	</tr>
																	
                                                                 </logic:iterate>
                                                                 </logic:present>
																</logic:empty>
															<table width="100%" border="0" cellspacing="0"
												                 cellpadding="0">
												                
																 <tr class="white2" >
																		<td colspan="2" width="30%">
																			<b>Total Amount</b>
																		</td>
																		
																		<td width="22%">
																			  <html:text
																				property="showTotalBalanceAmt"
																				styleId="showTotalBalanceAmt" value="${showTotal[0].showTotalBalanceAmt }"
																				readonly="readonly" styleClass="text" tabindex="-1" />
																		
																		</td>
																		<td width="20%">
																		 	<html:text property="showTotalAllocatedAmount"
																				styleId="showTotalAllocatedAmount" value="${showTotal[0].showTotalAllocatedAmount }"
																				styleClass="text" tabindex="-1" />
																	
																		</td>
																		<td width="22%">
																		
																			<html:text
																				property="showTotalTdsAmount"
																				styleId="showTotalTdsAmount" value="${showTotal[0].showTotalTdsAmount }" styleClass="text"
																				tabindex="-1" />
																	
																		</td>
																	</tr>
																	
																</table>
																
															</logic:present>


														</table>
														<table width="100%" border="0" cellspacing="1"
															cellpadding="4" id="gridtableButton">
															<tr class="white1">
																<td align="left" colspan="6">
																	<button type="button" title="Alt+A" accesskey="A"
																		onclick="return addRow('Save');"
																		class="topformbutton2">
																		<bean:message key="button.addrow" />
																	</button>
																	&nbsp;&nbsp;&nbsp;
																	<button type="button" class="topformbutton2"
																		title="Alt+D" accesskey="D" id="btn">
																		<bean:message key="button.deleterow" />
																	</button>
																</td>
															</tr>

														</table>
													</td>
												</tr>
											</table>
										</fieldset>
									</td>
								</tr>

							</logic:present>
						</logic:notPresent>
						<logic:present name="datatoapproveList">

							<logic:present name="charges">
								<tr>

									<td>
										<fieldset>
											<legend>
												<bean:message key="lbl.allocationDtl" />
											</legend>
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td class="gridtd">
														<table id="chargeTable" width="100%" border="0"
															cellspacing="1" cellpadding="1">
															<tr class="white2">

																<td>
																	<b><bean:message key="lbl.chargeDesc" /> </b>
																</td>

																<td>
																	<b><bean:message key="lbl.balanceAmount" /> </b>
																</td>
																<td>
																	<b><bean:message key="lbl.allocatedAmount" /> </b>
																</td>
																<td>
																	<b><bean:message key="lbl.tdsAllocatedAmount" /> </b>
																</td>
															</tr>

															<logic:iterate name="charges" id="subcharges" indexId="i">
																<tr class="white1">

																	<td>
																		${subcharges.allocChargeDesc}
																	</td>

																	<td>
														
																		<input type="text" name="allocBalAmount"
																			id="allocBalAmount${i+1}"
																			value="${subcharges.allocBalAmount}" readonly="readonly"/>
																	</td>

																	<td>
																		<html:text styleClass="text" style="text-align: right"
																			styleId="allocAmount${i+1}"
																			property="allocAmountStringArray"
																			value="${subcharges.allocAmount}"
																			onkeypress="return numbersonly(event, id, 18);"
																			onblur="formatNumber(this.value, id);"
																			onkeyup="checkNumber(this.value, event, 18, id);"
																			readonly="true"
																			onfocus="keyUpNumber(this.value, event, 18, id);" />
																	</td>
																	<td>
																		<html:text styleClass="text" style="text-align: right"
																			readonly="true"
																			styleId="allocTdsAllocatedAmount${i+1}"
																			property="allocTdsAllocatedAmountStringArray"
																			value="${subcharges.allocTdsAllocatedAmount}"
																			onkeypress="return numbersonly(event, id, 18);"
																			onblur="formatNumber(this.value, id);"
																			onkeyup="checkNumber(this.value, event, 18, id);"
																			onfocus="keyUpNumber(this.value, event, 18, id);" />
																	</td>
																</tr>
															</logic:iterate>




														</table>
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
												<bean:message key="lbl.otherChargeAllocationDtl" />
											</legend>
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td class="gridtd">
														<input type="hidden" name="psize" id="psize" />
														<input type="hidden" name="pcheck" id="pcheck" />
														<table id="gridtable" style="width: 100%;" border="0"
															cellspacing="1" cellpadding="4">
															<tr class="white2">
																<td>
																	<b><bean:message key="lbl.select" /> </b>
																</td>
																<td>
																	<b><bean:message key="lbl.chargeDesc" /> </b>
																</td>

																<td>
																	<b><bean:message key="lbl.balanceAmount" /> </b>
																</td>
																<td>
																	<b><bean:message key="lbl.allocatedAmount" /> </b>
																</td>
																<td>
																	<b><bean:message key="lbl.tdsAllocatedAmount" /> </b>
																</td>
															</tr>

															<logic:present name="otherAddCharges">
																<logic:notEmpty name="otherAddCharges">
																	<logic:iterate name="otherAddCharges"
																		id="subOtherAddCharges" indexId="i">
																		<tr class="white1" style="width: 25px;">
																			<td>
																				<input type="checkbox" name="chk" id="chk<%=i + 1%>"
																					value="" disabled="disabled" />
																			</td>
																			<td>
																				<html:text property="allocChargeDes"
																					styleId="allocChargeDes<%=i + 1%>"
																					value="${subOtherAddCharges.otherAddChargeDesc }"
																					readonly="true" styleClass="text" tabindex="-1" />
																			</td>
																			<td>
																				<html:text
																					property="allocOtherBalAmountStringArray"
																					styleId="otherAddBalanceAmount<%=i + 1%>"
																					value="${subOtherAddCharges.otherAddBalanceAmount }"
																					readonly="true" styleClass="text" tabindex="-1" />
																			</td>
																			<td>
																				<html:text
																					property="allocOtherAmountStringArray"
																					styleId="otherAddAllocateAmount<%=i + 1%>"
																					value="${subOtherAddCharges.otherAddAllocateAmount }"
																					readonly="true" styleClass="text" tabindex="-1" />
																			</td>
																			<td>
																				<html:text
																					property="allocTdsOtherAllAmtStringArray"
																					styleId="otherAddTDSAmount<%=i + 1%>"
																					value="${subOtherAddCharges.otherAddTDSAmount }"
																					styleClass="text" readonly="true" tabindex="-1" />
																			</td>
																		</tr>
																	</logic:iterate>
																</logic:notEmpty>
																<logic:empty name="otherAddCharges">
																	<tr class="white1" style="width: 25px;">
																		<td>
																			<input type="checkbox" name="chk" id="chk1" value=""
																				disabled="disabled" />
																		</td>
																		<td>
																			<html:text property="allocChargeDes"
																				styleId="allocChargeDes1"
																				value="${subOtherAddCharges.otherAddChargeDesc }"
																				readonly="true" styleClass="text" tabindex="-1" />
																		</td>
																		<td>
																			<html:text
																				property="allocOtherBalAmountStringArray"
																				styleId="otherAddBalanceAmount1"
																				value="${subOtherAddCharges.otherAddBalanceAmount }"
																				readonly="true" styleClass="text" tabindex="-1" />
																		</td>
																		<td>
																			<html:text property="allocOtherAmountStringArray"
																				styleId="otherAddAllocateAmount1"
																				value="${subOtherAddCharges.otherAddAllocateAmount }"
																				readonly="true" styleClass="text" tabindex="-1" />
																		</td>
																		<td>
																			<html:text
																				property="allocTdsOtherAllAmtStringArray"
																				styleId="otherAddTDSAmount1"
																				value="${subOtherAddCharges.otherAddTDSAmount }"
																				readonly="true" styleClass="text" tabindex="-1" />
																		</td>
																	</tr>

																</logic:empty>
															</logic:present>


														</table>

													</td>
												</tr>
											</table>
										</fieldset>
									</td>
								</tr>

							</logic:present>
						</logic:present>
						<!-- Nishant Space end -->
						<tr>
						
						
							<logic:notPresent name="datatoapproveList">
								<logic:present name="savedReceipt">
									<td>
									  <logic:present name="saveForwardReceipt">
										<logic:equal name="saveForwardReceipt" value="Y">
									    <button type="button" id="saveForward" class="topformbutton3"
											onclick="return receiptSaveForward('<bean:message key="msg.InstDateLessRecp" />','<bean:message key="msg.BussDateLessRec" />','<bean:message key="msg.confirmationForwardMsg" />');"
											title="Alt+F" accesskey="F">
											<bean:message key="button.savefrwd" />
										</button>
										</logic:equal>
										<logic:equal name="saveForwardReceipt" value="N">
										<button type="button" id="save" class="topformbutton2"
											onclick="return onUpdateReceipt('<bean:message key="msg.InstDateLessRecp" />','<bean:message key="msg.BussDateLessRec" />');"
											title="Alt+V" accesskey="V">
											<bean:message key="button.save" />
										</button>
										<button type="button" id="saveForward" class="topformbutton3"
											onclick="return onReceiptForwardCheck('<bean:message key="msg.InstDateLessRecp" />','<bean:message key="msg.BussDateLessRec" />','<bean:message key="msg.confirmationForwardMsg" />');"
											title="Alt+F" accesskey="F">
											<bean:message key="button.forward" />
										</button>
										</logic:equal>
										</logic:present>
										<button type="button" type='button' class="topformbutton3"
											onclick="return viewReceivable('<bean:message key="msg.LoanAccBPType" />');"
											title="Alt+R" accesskey="R">
											<bean:message key="button.viewRecievable" />
										</button>
										<logic:present name="allocationGridReceipt">
										   <logic:equal name="allocationGridReceipt" value="N">
												<button type="button" id="allocateRec" class="topformbutton4"
													onclick="return allocateReceivable('<bean:message key="msg.plsSaveViewR" />');"
													title="Alt+A" accesskey="A">
													<bean:message key="button.allocreceivable" />
												</button>
											</logic:equal>
										</logic:present>
										<button type="button" id="delete" class="topformbutton2"
											onclick="return deleteReceipt();" title="Alt+D" accesskey="D">
											<bean:message key="button.delete" />
										</button>
										<%-- <button type="button" id="allocateRec" class="topformbutton4" onclick="return allocateCharge();" title="Alt+C" accesskey="C" ><bean:message key="button.alloCharge" /></button> --%>
									</td>
								</logic:present>
								<logic:notPresent name="savedReceipt">
									<td>
									<logic:present name="saveForwardReceipt">
										<logic:equal name="saveForwardReceipt" value="Y">
									    <button type="button" id="saveForward" class="topformbutton3"
											onclick="return receiptSaveForward();"
											title="Alt+F" accesskey="F">
											<bean:message key="button.savefrwd" />
										</button>
										</logic:equal>
										<logic:equal name="saveForwardReceipt" value="N">
										<button type="button" id="save" class="topformbutton2"
											onclick="return onSaveReceipt('<bean:message key="msg.InstDateLessRecp" />','<bean:message key="msg.BussDateLessRec" />');"
											title="Alt+V" accesskey="V">
											<bean:message key="button.save" />
										</button>
										<button type="button" id="saveForward" class="topformbutton3"
											onclick="return onReceiptForwardCheck('<bean:message key="msg.InstDateLessRecp" />','<bean:message key="msg.BussDateLessRec" />','<bean:message key="msg.confirmationForwardMsg" />');"
											title="Alt+F" accesskey="F">
											<bean:message key="button.forward" />
										</button>
										</logic:equal>
									</logic:present>
										<button type="button" type='button' class="topformbutton3"
											onclick="return viewReceivable('<bean:message key="msg.LoanAccBPType" />');"
											title="Alt+R" accesskey="R">
											<bean:message key="button.viewRecievable" />
										</button>
									
										<logic:present name="allocationGridReceipt">
										  <logic:equal name="allocationGridReceipt" value="N">
									    	<button type="button" id="allocateRec" class="topformbutton4"
											   onclick="return allocateReceivable('<bean:message key="msg.plsSaveViewR" />');"
											   title="Alt+A" accesskey="A">
											<bean:message key="button.allocreceivable" />
										   </button>
										</logic:equal>
										</logic:present>
									</td>
								</logic:notPresent>
							</logic:notPresent>
						</tr>


					</table>

				</fieldset>



			</html:form>

		
			<logic:present name="msg">
				<script type="text/javascript">
		if('<%=request.getAttribute("msg")%>'=='S')
		{
			alert("<bean:message key="lbl.dataSave" />");
			
		}
		else if('<%=request.getAttribute("msg")%>'=='F')
		{
			alert("<bean:message key="lbl.forwardSuccess" />");
			var frdLoanID=document.getElementById("forwardLoanID").value;
			var frdInstrumentID=document.getElementById("forwardInstrumentID").value;
		    window.location="<%=request.getContextPath()%>/receiptMakerSearch.do?forward=forward&frdLoanID="+frdLoanID+"&frdInstrumentID="+frdInstrumentID;
	      
		}
		else if('<%=request.getAttribute("msg")%>'=='E')
		{
			alert("<bean:message key="lbl.errorSuccess" />");
			
		}
		
		else if('<%=request.getAttribute("msg")%>'=="DS")
		{
			alert("<bean:message key="lbl.dataDeleted" />");
			window.location="<%=request.getContextPath()%>/receiptMakerSearch.do";
		}else if('<%=request.getAttribute("msg").toString()%>'=='DN')
		{
			alert("<bean:message key="lbl.dataNtDeleted" />");
			
		}
		else if('<%=request.getAttribute("msg")%>'=="RECEIPTAMOUNTNOTALLOCATED")
		{
			var forwardLoanID=document.getElementById("forwardLoanID").value;
			var forwardInstrumentID=document.getElementById("forwardInstrumentID").value;
			if(confirm("Receipt Amount has not been Allocated.Do you want to proceed")){
		  
			 var basePath=document.getElementById("contextPath").value;		 
			 document.getElementById("receiptMakerForm").action=basePath+"/receiptMakerProcessAction.do?method=onDirectForwardOnReceipt&forwardInstrumentID="+forwardInstrumentID+"&forwardLoanID="+forwardLoanID;
	         document.getElementById("receiptMakerForm").submit();
	         }
		}
		else if('<%=request.getAttribute("msg")%>'=="REPOFLAGMARKED")
		{
			var forwardLoanID=document.getElementById("forwardLoanID").value;
			var forwardInstrumentID=document.getElementById("forwardInstrumentID").value;
			if(confirm("Loan is marked as Repo.Do you want to proceed")){
		  
			 var basePath=document.getElementById("contextPath").value;		 
			 document.getElementById("receiptMakerForm").action=basePath+"/receiptMakerProcessAction.do?method=checkRepoForwardOnReceipt&forwardInstrumentID="+forwardInstrumentID+"&forwardLoanID="+forwardLoanID;
	         document.getElementById("receiptMakerForm").submit();
	         }
		}
		else
		{
		    alert('${msg}');
		}
		</script>
			</logic:present>

		</div>
   


	</div>
	<div align="center" class="opacity" style="display: none;"
		id="processingImage">
	</div>
	<script>
	setFramevalues("receiptMakerForm");
</script>
</body>
</html:html>