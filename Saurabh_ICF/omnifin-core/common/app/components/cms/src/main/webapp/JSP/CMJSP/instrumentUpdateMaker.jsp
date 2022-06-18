<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
<html:html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />


	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
	<%
		String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":"
					+ request.getServerPort() + path + "/";
	%>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
		
		<script type="text/javascript" src="<%=path%>/js/cmScript/instrumentCapturing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>

	<script type="text/javascript">
	        function infoCall()
        {	
        document.getElementById("loanInfo").style.display='';
        }
      
			function no_disbursal()
			{
					var url="PopupDisbursal.html"
	newwindow=window.open(url,'init_disbursal','top=200,left=250,scrollbars=no,width=1366,height=768' );
	if (window.focus) {newwindow.focus()}
	return false;
			}
			
				function defaultFocus()
			{
				document.getElementById('sourcingForm').loanButton.focus();
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
<body onload="enableAnchor();checkChanges('sourcingForm');defaultFocus();init_fields();" onclick="parent_disable();"
	onunload="closeLOVWindow();closeAllLovCallUnloadBody();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>

	<div id="centercolumn">
		<div id="revisedcontainer">
			<html:form action="/instrumentUpdateProcessAction" method="post"	styleId="sourcingForm">
				<html:javascript formName="InstrumentCapturingMakerValidatorForm" />
				<html:errors />
				<input type="hidden" name="contextPath" id="contextPath"
					value="<%=request.getContextPath()%>" />
				<input type="hidden" name="pdcPartialForward" id="pdcPartialForward"
					value="<%=request.getAttribute("pdcPartialForward")%>" />
					
				<input type="hidden" name="installmentDiff" id="installmentDiff"
					value="<%=request.getAttribute("installmentDiff")%>" />	
					
			<input type="hidden" name="alertStatusFromParameterMst" id="alertStatusFromParameterMst"
					value="<%=request.getAttribute("customerTypeStatus")%>" />	
					
					<input type="hidden" name="readyToForard" id="readyToForard"
					value="${sessionScope.readyToForard }" />	
					
					<html:hidden styleClass="text" property="otherInstallmentCharges"
													styleId="otherInstallmentCharges" 
													value="${arryList[0].otherInstallmentCharges}" disabled="true"  />
					<html:hidden styleClass="text" property="totalChargeInstallmentAmount"
													styleId="totalChargeInstallmentAmount" 
													value="${arryList[0].totalChargeInstallmentAmount}" disabled="true"/>											
													
													
				<logic:present name="insNonInsFlag">
				
					<logic:iterate name="insNonInsFlag" id="insNonInsFlagObj">

						<input type="hidden" name="loanInstallmentMode"
							id="loanInstallmentMode"
							value="${insNonInsFlagObj.loanInstallmentMode}" />
						<input type="hidden" name="loanAdvanceInstallment"
							id="loanAdvanceInstallment"
							value="${insNonInsFlagObj.loanAdvanceInstallment}" />


					</logic:iterate>
				</logic:present>

				<logic:notPresent name="insNonInsFlag">
					<input type="hidden" name="loanInstallmentMode"
						id="loanInstallmentMode" />
					<input type="hidden" name="loanAdvanceInstallment"
						id="loanAdvanceInstallment" />
				</logic:notPresent>

				<fieldset>
					<legend>
						<bean:message key="lbl.instrumentUpdateMaker" />
					</legend>
					<input type="hidden" name="formatD" id="formatD"
						value="<bean:message key="lbl.dateFormat"/>" />
					<table width="100%" border="0" cellspacing="0" cellpadding="0">


						<tr>
							<td>
								<table width="100%" border="0" cellspacing="1" cellpadding="1">


									<tr>
										<td width="25%">
											<bean:message key="lbl.loanNumber" />
											<font color="red">*</font>
										</td>

										<td width="25%">




											<logic:present name="arryList">
											<logic:notEmpty  name="arryList">
												<html:text styleClass="text" property="loanAccNo"
													styleId="loanAccNo" tabindex="-1" maxlength="100"
													value="${arryList[0].loanAccNo}" readonly="true" />
<!--												<html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(387,'sourcingForm','loanAccNo','','', '','','','customerName','totalInstallments','installmentAmount');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>-->
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="${arryList[0].lbxLoanNoHID}" />
													<html:hidden property="repayment" styleId="repayment"
													value="${arryList[0].repayment}" />
											</logic:notEmpty></logic:present>
											<!--  <img onclick="openLOVCommon(49,'sourcingForm','loanAccNo','','', '','','','customerName','totalInstallments','installmentAmount')" SRC="<%=request.getContextPath()%>/images/lov.gif">-->

											<logic:notPresent name="arryList">
												<logic:notPresent name="author">
													<html:text styleClass="text" property="loanAccNo"
														styleId="loanAccNo" tabindex="-1" maxlength="100"
														readonly="true" />
													<html:button property="loanButton" styleId="loanButton"
														onclick="openLOVCommon(387,'sourcingForm','loanAccNo','','', '','','insnonins','customerName','totalInstallments','installmentAmount');closeLovCallonLov1();"
														value=" " styleClass="lovbutton"></html:button>
													<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID" />
													<html:hidden property="repayment" styleId="repayment" />

												</logic:notPresent>
											</logic:notPresent>



										</td>


										<td width="25%">
											<bean:message key="lbl.customerName" />
										</td>
										<td width="25%">
											<logic:present name="arryList">
											<logic:notEmpty  name="arryList">
												<html:text styleClass="text" property="customerName"
													styleId="customerName" tabindex="-1" maxlength="100"
													value="${arryList[0].customerName}" readonly="true" />
												</logic:notEmpty>
											</logic:present>
											<logic:notPresent name="arryList">
												<html:text styleClass="text" property="customerName"
													styleId="customerName" tabindex="-1" maxlength="100"
													readonly="true" />
											</logic:notPresent>
										</td>


									</tr>



									<tr>
										<logic:notPresent name="author">
											<td>
												<bean:message key="lbl.instrumentMode" />
												<font color="red">*</font>
											</td>
											<td>
												<label>
													<html:select property="instrumentType"
														styleId="instrumentType" styleClass="text" value="${requestScope.insMode}"
														onchange="ecsFunctionForUpdate();">
														<html:option value="">--Select--</html:option>
														<html:option value="Q">PDC</html:option>
														<html:option value="E">ECS</html:option>
														<html:option value="DIR">Direct Debit</html:option>
														<html:option value="H">NACH</html:option>
													</html:select>
												</label>
											</td>
										</logic:notPresent>
										<logic:present name="purposeList">
									
											<td id="A">
												<bean:message key="lbl.purpose" />
												<font color="red">*</font>
											</td>
											<td id="B">
												<label>

													<html:select property="purpose" styleId="purpose"
														styleClass="text" onchange="select();disableFromToInUpdateInstrument();" value="${requestScope.purpose}">
														<option value="">
															--
															<bean:message key="lbl.select" />
															--
														</option>
															<logic:notEmpty name="purposeList">
														<html:optionsCollection name="purposeList"
															label="pdcPurposeDesc" value="pdcPurposeValue" />
															</logic:notEmpty>
													</html:select>

												</label>
											</td>
											
										</logic:present>


									</tr>
									<tr>
									<td>
												<bean:message key="lbl.installmentType" />
												
											</td>
										<td><input type="text" name="installmentType" id="installmentType" class="text" readonly="readonly" value="${arryList[0].installmentType}"/></td>
										<td id="s1" style="display: none;"><bean:message key="lbl.submitBy" /><font color="red">*</font></td>
										<td id="s2" style="display: none;">
				 							<html:select property="submitBy" styleId="submitBy" styleClass="text">
                 							<logic:present name="applist" >          	
          	     							<html:optionsCollection name="applist" label="applicant_desc" value="applicant_code" />
                 							</logic:present>
          		 							</html:select>
          								</td>
          								</tr>
          								
          								<logic:notPresent name="author">
          								<tr>
											<td><bean:message key="lbl.fromInstallment" />
											</td>
											<td>
												<label>
													<html:text styleClass="text" property="fromInstallment"
														styleId="fromInstallment" maxlength="3"
														onkeypress="return isNumberKey(event);" value="${requestScope.fromIns}"/>
												</label>
											</td>

											<td>
												<bean:message key="lbl.toInstallment" />
												
											</td>
											<td>
											
												<html:text styleClass="text" property="toInstallment"
													styleId="toInstallment" maxlength="3"
													onchange="return validate();"
													onkeypress="return isNumberKey(event);"  value="${requestScope.toIns}"/>
											</td>
										</tr>
										
										<tr>
											<td align="left">  
											    <button type="button" id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return searchInstrumentByInsNo();"><bean:message key="button.search" /></button>
											     
											</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
										</tr>
										</logic:notPresent>
								</table>
							</td>
						</tr>

					</table>
				</fieldset>

				<logic:notPresent name="author">
					<fieldset>
						<legend>
					<bean:message key="lbl.pdcDetails" />
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="1" cellpadding="1">
										<tr>
											<td width="25%">
												<bean:message key="lbl.totalInstallments" />
											</td>
											<td width="25%">
												<html:text styleClass="text" property="totalInstallments"
													styleId="totalInstallments" tabindex="-1" maxlength="100"
													value="${arryList[0].totalInstallments}" readonly="true" />
											</td>
											<td width="25%">
												<bean:message key="lbl.installmentAmount" />
											</td>
											<td width="25%">
												<html:text styleClass="text" property="installmentAmount"
													styleId="installmentAmount" tabindex="-1" maxlength="100"
													value="${arryList[0].installmentAmount}" readonly="true" />
											</td>
										</tr>
										<!-- Nishant space starts -->
										<tr>
											<td width="25%">
												<bean:message key="lbl.otherInstallmentCharges" />
											</td>
											<td width="25%">
												<html:text styleClass="text" property="otherInstallmentCharges"
													styleId="otherInstallmentCharges" tabindex="-1" maxlength="100"
													value="${arryList[0].otherInstallmentCharges}" readonly="true" />
											</td>
											<td width="25%">
												<bean:message key="lbl.totalChargeInstallmentAmount" />
											</td>
											<td width="25%">
												<html:text styleClass="text" property="totalChargeInstallmentAmount"
													styleId="totalChargeInstallmentAmount" tabindex="-1" maxlength="100"
													value="${arryList[0].totalChargeInstallmentAmount}" readonly="true" />
											</td>
										</tr>
										
										<!-- Nishant space ends -->
										<tr>
											<td>
												<bean:message key="lbl.bank" />
												
											</td>

											<td>
												<div style="float: left;">
													<html:text styleClass="text" property="bank" styleId="bank"
														maxlength="100" tabindex="-1" readonly="true" />
													<html:hidden property="lbxBankID" styleId="lbxBankID" />
													<html:button property="loanBankButton"
														styleId="loanBankButton"
														onclick="closeLovCallonLov1();openLOVCommon(7,'sourcingForm','bank','','branch', 'lbxBranchID','','','lbxBankID')"
														value=" " styleClass="lovbutton"></html:button>
													<!-- <img onclick="openLOVCommon(7,'sourcingForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" src="<%=request.getContextPath()%>/images/lov.gif"> -->
												</div>
											</td>

											<td>
												<bean:message key="lbl.branch" />
												
											</td>
											<td>
												<html:text styleClass="text" property="branch"
													styleId="branch" maxlength="100" tabindex="-1"
													readonly="true" />
												<html:hidden property="lbxBranchID" styleId="lbxBranchID" />
												<div id="pdc" style="display:Inline"><html:button property="loanBranchButton" styleId="loanBranchButton" onclick="openLOVCommon(50,'sourcingForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifscCode');closeLovCallonLov('bank');"	value=" " styleClass="lovbutton"></html:button></div>
												<div id="ecs" style="display:none"><html:button property="loanBranchButton" styleId="loanBranchButton" onclick="openLOVCommon(349,'sourcingForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifscCode');closeLovCallonLov('bank');"	value=" " styleClass="lovbutton"></html:button></div>
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.micr" />
											</td>
											<td>
												<label>
													<html:text styleClass="text" property="micr" styleId="micr"
														maxlength="100" readonly="true" tabindex="-1" />
												</label>
											</td>

											<td>
												<bean:message key="lbl.ifscCode" />
											</td>
											<td>
												<html:text styleClass="text" property="ifscCode"
													styleId="ifscCode" maxlength="50" readonly="true"
													tabindex="-1" />
											</td>

										</tr>
										<tr>
											<td>
												<bean:message key="lbl.bankAccount" />
												
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="bankAccount"
													styleId="bankAccount" maxlength="20" />
											</td>


											<td>
												<bean:message key="lbl.instrumetAmount" />
												
											</td>
											<td>
												<html:text styleClass="text" property="instrumentAmount"
													styleId="instrumentAmount" maxlength="100"
													onkeypress="return numbersonly(event,id,18)"
													onblur="formatNumber(this.value,id);"
													onkeyup="checkNumber(this.value, event, 18,id);"
													onfocus="keyUpNumber(this.value, event, 18,id);"
													value="" />
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.location" />

											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="location"
													styleId="location" maxlength="50" />
											</td>
											<td id="Y">
												<bean:message key="lbl.clearingType" />
												
											</td>
											<td id="Z">
												<html:select property="clearingType" styleId="clearingType" styleClass="text">
                 							    <logic:present name="clearingTypeList" > 
                 							    <html:option value="">--Select--</html:option>         	
          	     							    <html:optionsCollection name="clearingTypeList" label="clTypeDesc" value="clTypeCode" />
                 							    </logic:present>
          		 							    </html:select>
											</td>



										</tr>

										<tr>


											<logic:present name="customeracTypeList">
											<td id="E">
													<bean:message key="lbl.customeracType" />
												</td>
												<td id="F">
													<label>
														<html:select property="customeracType" styleId="customeracType" styleClass="text" value="${dfltACType}">
															<option value="">--<bean:message key="lbl.select" />--</option>
															<logic:notEmpty name="customeracTypeList">
															      <html:optionsCollection name="customeracTypeList" label="customeracTypeDesc" value="customeracTypeValue" />
															</logic:notEmpty>
														</html:select>

													</label>
												</td>
											
											</logic:present>
											<logic:present name="ecsTransactionCodeList">
										
												<td id="C">
													<bean:message key="lbl.ecsTransactionCode" />
													
												</td>
												<td id="D">
													<label>

														<html:select property="ecsTransactionCode"
															styleId="ecsTransactionCode" styleClass="text">
															<option value="">
																--
																<bean:message key="lbl.select" />
																--
															</option>
															<logic:notEmpty name="ecsTransactionCodeList">
															<html:optionsCollection name="ecsTransactionCodeList"
																label="ecsTransactionCodeDesc"
																value="ecsTransactionCodeValue" />
																	</logic:notEmpty>
														</html:select>
												
													</label>
												</td>
											
											</logic:present>


										</tr>



										<tr>
											<logic:present name="spnserBnkBrncCodeList">
										
												<td id="G">
													<bean:message key="lbl.spnserBnkBrncCode" />
													
												</td>
												<td id="H">
													<label>

														<html:select property="spnserBnkBrncCode"
															styleId="spnserBnkBrncCode" styleClass="text">
															<option value="">
																--
																<bean:message key="lbl.select" />
																--
															</option>
														<logic:notEmpty name="spnserBnkBrncCodeList">
															<html:optionsCollection name="spnserBnkBrncCodeList"
																label="spnserBnkBrncCodeDesc"
																value="spnserBnkBrncCodeValue" />
																	</logic:notEmpty>
														</html:select>
												
													</label>
												</td>
											
											</logic:present>
											<logic:present name="utilityNoList">
											
												<td id="I">
													<bean:message key="lbl.utilityNo" />
													
												</td>
												<td id="J">
													<label>

														<html:select property="utilityNo" styleId="utilityNo"
															styleClass="text">
															<option value="">
																--
																<bean:message key="lbl.select" />
																--
															</option>
															<logic:notEmpty name="utilityNoList">
															<html:optionsCollection name="utilityNoList"
																label="utilityNoDesc" value="utilityNoValue" />
																	</logic:notEmpty>
														</html:select>

													</label>
												</td>
											
											</logic:present>

										</tr>


										<tr>

											<td>
												<bean:message key="lbl.binNo" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="binNo"
													styleId="binNo" maxlength="20" />
											</td>
											<td id="instrument">
												<bean:message key="lbl.MicrNonMicr" />
												</td>
											<td id="instrument_type">
											<html:select property="micrNonmicr" styleId="micrNonmicr"
													styleClass="text">
													<html:option value="Y">MICR</html:option>
													<html:option value="N">NON-MICR</html:option>
												</html:select>
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.makerRemark" />
<!--												<font color="red">*</font>--><!-- Commented by Nishant -->
											</td>
											<td>
												<textarea name="remarks" id="remarks" class="text"></textarea>
											</td>


											<td nowrap="nowrap">
												<bean:message key="lbl.authorRemarks" />
											</td>
											<td nowrap="nowrap">
												<html:textarea styleClass="text" property="authorRemarks"
													styleId="authorRemarks" disabled="true"
													value="${arryList[0].authorRemarks}" />
											</td>
										</tr>
										
									</table>
								</td>
							</tr>

						</table>

					</fieldset>
				</logic:notPresent>
				<fieldset>
					<legend>
						<bean:message key="lbl.pdcSummary" />
					</legend>


					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="gridtd">

								<table id="table1" width="100%" border="0" cellspacing="1"
									cellpadding="4">
									<logic:notPresent name="author">

										<tr class="white2">
											<td>
												<b><bean:message key="lbl.select" /> </b>
												<input type="checkbox" name="chkd" id="allchkd"
													onclick="allChecked();" />
											</td>
											<td>
												<strong><bean:message key="lbl.duedate" /> </strong>
											</td>
											<td>
												<strong><bean:message key="lbl.pdcDate" /> </strong>
											</td>
											<td>
												<b><bean:message key="lbl.instrumentNo" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.installmentNo" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.bank" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.branch" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.micr" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.ifscCode" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.bankAccount" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.purpose" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.installmentAmount" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.pdcAmount" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.instrumentMode" /> </b>
											</td>

											<td>
												<b><bean:message key="lbl.ecsTransactionCode" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.customeracType" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.spnserBnkBrncCode" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.utilityNo" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.linkedLoan" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.makerDate" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.maker" /> </b>
											</td>
										</tr>


										<logic:present name="arrList">
											
										<logic:notEmpty name="arrList">
											<logic:iterate id="arrListobj" name="arrList" indexId="count">
												<html:hidden property="installments" styleId="installments" value="${arrListobj.installmentNo}"/>
												<html:hidden property="instrumentAmountAll" styleId="instrumentAmountAll" value="${arrListobj.instrumentAmount}"/>
												<html:hidden property="installmentAmountAll" styleId="installmentAmountAll" value="${arrListobj.installmentAmount}"/>
												<html:hidden property="purposeAll" styleId="purposeAll" value="${arrListobj.purpose}"/>
												<html:hidden property="instrumentTypeAll" styleId="instrumentTypeAll" value="${arrListobj.instrumentType}"/>
												<html:hidden property="startingChequeNoAll" styleId="startingChequeNoAll" value="${arrListobj.startingChequeNo}"/>
												<logic:equal name="arrListobj" property="pdcStatus"
													value="P">
													
													
													<tr class="white2">
														<td>
														<input type="checkbox" name="chk"
																id="chk<%=count.intValue() + 1%>"
																value="${arrListobj.instrumentID }" />
<!--															<input type="checkbox" name="" id=""-->
<!--																value="${arrListobj.instrumentID}" disabled />-->
														</td>

														<td>
															${arrListobj.date}
														</td>

																											
														  <logic:equal name="arrListobj" property="purpose"
															value="PRE EMI">
															<script type="text/javascript">

																     $(function() {
																
																			var contextPath =document.getElementById('contextPath').value ;
																			$("#pdcDate<%=count.intValue() + 1%>").datepicker({
																			format: "%Y-%m-%d %H:%i:%s %E %#",
																			formatUtcOffset: "%: (%@)",
																			placement: "inline",
																			
																			 changeMonth: true,
																			 changeYear: true,
																			 yearRange: '1900:+10',
																			 showOn: 'both',
																			 <logic:present name="image">
																    	   		buttonImage: '<%=request.getContextPath()%>/${image }/calendar.gif',
																            </logic:present>
																    		<logic:notPresent name="image">
																    			buttonImage: '<%=request.getContextPath()%>/images/theme1/calendar.gif',
																    		</logic:notPresent>
																			 buttonImageOnly: true,
																			 dateFormat:"<bean:message key="lbl.dateFormat"/>",
																			 defaultDate:'${userobject.businessdate }'
																			
																				});
																				});
																	
 
                                                                </script>
															<td>
																<input type="text" name="pdcDate"
																	id="pdcDate<%=count.intValue() + 1%>"
																	value="${arrListobj.pdcDate}" class="text"
																	readonly="readonly" />
																<input type="hidden" name="dateOne" id="dateOne"
																	value="${arrListobj.date}" />
															</td>
														</logic:equal>
														  <logic:notEqual name="arrListobj" property="purpose"
															value="PRE EMI">
																<td>
																	${arrListobj.pdcDate}
																	<input type="hidden" name="pdcDate" id="pdcDate"
																	value="" />
																</td>
														</logic:notEqual>
														<td>
															${arrListobj.startingChequeNo}
														</td>
														<td>
															${arrListobj.installmentNo}
														</td>

														<td>
															${arrListobj.bank}
														</td>
														<td>
															${arrListobj.branch}
														</td>
														<td>
															${arrListobj.micr}
														</td>
														<td>
															${arrListobj.ifscCode}
														</td>
														<td>
															${arrListobj.bankAccount}
														</td>
														<td>
															${arrListobj.purpose}
														</td>

														<td>
															${arrListobj.installmentAmount}
														</td>
														<td>
															${arrListobj.instrumentAmount}
														</td>
														<logic:equal name="arrListobj" property="instrumentType"
															value="Q">
															<td>
																PDC
															</td>
														</logic:equal>

														<logic:equal name="arrListobj" property="instrumentType"
															value="E">
															<td>
																ECS
															</td>
														</logic:equal>
														<logic:equal name="arrListobj" property="instrumentType"
															value="H">
															<td>
																NACH
															</td>
														</logic:equal>
														<logic:equal name="arrListobj" property="instrumentType"
															value="DIR">
															<td>
																Direct debit
															</td>
														</logic:equal>


														<td>
															${arrListobj.ecsTransactionCode}
														</td>
														<td>
															${arrListobj.customeracType}
														</td>
														<td>
															${arrListobj.spnserBnkBrncCode}
														</td>

														<td>
															${arrListobj.utilityNo}
														</td>
														<td></td>
														<td>
															${arrListobj.makerDate}
														</td>
														<td>
															${arrListobj.maker}
														</td>
														<html:hidden property="lbxLoanNoHidden"	styleId="lbxLoanNoHidden" value="lbxLoanNoHID" />
													</tr>
												</logic:equal>
												
												<logic:equal name="arrListobj" property="pdcStatus"
													value="A">
													
													<tr class="white2">
														<td>
														<input type="checkbox" name="chk"
																id="chk<%=count.intValue() + 1%>"
																value="${arrListobj.instrumentID }" />
<!--															<input type="checkbox" name="" id=""-->
<!--																value="${arrListobj.instrumentID}" disabled />-->
														</td>
                                                         
														<td>
															${arrListobj.date}
														</td>

													
														  <logic:equal name="arrListobj" property="purpose"
															value="PRE EMI">
															<script type="text/javascript">

																     $(function() {
																
																			var contextPath =document.getElementById('contextPath').value ;
																			$("#pdcDate<%=count.intValue() + 1%>").datepicker({
																			format: "%Y-%m-%d %H:%i:%s %E %#",
																			formatUtcOffset: "%: (%@)",
																			placement: "inline",
																			
																			 changeMonth: true,
																			 changeYear: true,
																			 yearRange: '1900:+10',
																			 showOn: 'both',
																			 <logic:present name="image">
																    	   		buttonImage: '<%=request.getContextPath()%>/${image }/calendar.gif',
																            </logic:present>
																    		<logic:notPresent name="image">
																    			buttonImage: '<%=request.getContextPath()%>/images/theme1/calendar.gif',
																    		</logic:notPresent>
																			 buttonImageOnly: true,
																			 dateFormat:"<bean:message key="lbl.dateFormat"/>",
																			 defaultDate:'${userobject.businessdate }'
																			
																				});
																				});
																	
 
                                                                </script>
															<td>
																<input type="text" name="pdcDate"
																	id="pdcDate<%=count.intValue() + 1%>"
																	value="${arrListobj.pdcDate}" class="text"
																	readonly="readonly" />
																<input type="hidden" name="dateOne" id="dateOne"
																	value="${arrListobj.date}" />
															</td>
														</logic:equal>
														  <logic:notEqual name="arrListobj" property="purpose"
															value="PRE EMI">
																<td>
																	${arrListobj.pdcDate}
																	<input type="hidden" name="pdcDate" id="pdcDate"
																	value="" />
																</td>
														</logic:notEqual>
														<td>
															${arrListobj.startingChequeNo}
														</td>
														<td>
															${arrListobj.installmentNo}
														</td>

														<td>
															${arrListobj.bank}
														</td>
														<td>
															${arrListobj.branch}
														</td>
														<td>
															${arrListobj.micr}
														</td>
														<td>
															${arrListobj.ifscCode}
														</td>
														<td>
															${arrListobj.bankAccount}
														</td>
														<td>
															${arrListobj.purpose}
														</td>

														<td>
															${arrListobj.installmentAmount}
														</td>
														<td>
															${arrListobj.instrumentAmount}
														</td>
														<logic:equal name="arrListobj" property="instrumentType"
															value="Q">
															<td>
																PDC
															</td>
														</logic:equal>

														<logic:equal name="arrListobj" property="instrumentType"
															value="E">
															<td>
																ECS
															</td>
														</logic:equal>
														<logic:equal name="arrListobj" property="instrumentType"
															value="H">
															<td>
																NACH
															</td>
														</logic:equal>
														<logic:equal name="arrListobj" property="instrumentType"
															value="DIR">
															<td>
																Direct debit
															</td>
														</logic:equal>


														<td>
															${arrListobj.ecsTransactionCode}
														</td>
														<td>
															${arrListobj.customeracType}
														</td>
														<td>
															${arrListobj.spnserBnkBrncCode}
														</td>

														<td>
															${arrListobj.utilityNo}
														</td>
														<td></td>
														<td>
															${arrListobj.makerDate}
														</td>
														<td>
															${arrListobj.maker}
														</td>
														<html:hidden property="lbxLoanNoHidden"	styleId="lbxLoanNoHidden" value="lbxLoanNoHID" />
													</tr>
												</logic:equal>

												
											</logic:iterate>
											</logic:notEmpty>
										</logic:present>
									</logic:notPresent>




									<logic:present name="author">
									
									
										<tr class="white2">

											<td>
												<strong><bean:message key="lbl.duedate" /> </strong>
											</td>
											<td>
												<strong><bean:message key="lbl.pdcDate" /> </strong>
											</td>
											<td>
												<b><bean:message key="lbl.instrumentNo" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.installmentNo" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.bank" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.branch" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.micr" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.ifscCode" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.bankAccount" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.purpose" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.installmentAmount" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.pdcAmount" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.instrumentMode" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.ecsTransactionCode" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.customeracType" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.spnserBnkBrncCode" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.utilityNo" /> </b>
											</td>
										</tr>


										<logic:present name="arrList">
										<logic:notEmpty name="arrList">
											<logic:iterate id="arrListobj" name="arrList">
												<tr class="white1">




													<td>
														${arrListobj.date}
													</td>

													<td>
														${arrListobj.pdcDate}
													</td>
													<td>
														${arrListobj.startingChequeNo}
													</td>
													<td>
														${arrListobj.installmentNo}
													</td>

													<td>
														${arrListobj.bank}
													</td>
													<td>
														${arrListobj.branch}
													</td>
													<td>
														${arrListobj.micr}
													</td>
													<td>
														${arrListobj.ifscCode}
													</td>
													<td>
														${arrListobj.bankAccount}
													</td>
													<td>
														${arrListobj.purpose}
													</td>

													<td>
														${arrListobj.installmentAmount}
													</td>
													<td>
														${arrListobj.instrumentAmount}
													</td>

													<logic:equal name="arrListobj" property="instrumentType"
														value="Q">
														<td>
															PDC
														</td>
													</logic:equal>

													<logic:equal name="arrListobj" property="instrumentType"
														value="E">
														<td>
															ECS
														</td>
													</logic:equal>
													<logic:equal name="arrListobj" property="instrumentType"
															value="H">
															<td>
																NACH
															</td>
														</logic:equal>
													<logic:equal name="arrListobj" property="instrumentType"
															value="DIR">
															<td>
																Direct debit
															</td>
														</logic:equal>

													<td>
														${arrListobj.ecsTransactionCode}
													</td>
													<td>
														${arrListobj.customeracType}
													</td>
													<td>
														${arrListobj.spnserBnkBrncCode}
													</td>
													<td>
														${arrListobj.utilityNo}
													</td>

													<html:hidden property="lbxLoanNoHidden"
														styleId="lbxLoanNoHidden" value="lbxLoanNoHID" />
												</tr>

											</logic:iterate>
											</logic:notEmpty>
										</logic:present>
									</logic:present>

									<tr>
									</tr>
								</table>
							</td>
						</tr>

						<logic:notPresent name="author">

							<tr>
								<td colspan="4">
									&nbsp;
									<button type="button"  id="save"
													class="topformbutton2" title="Alt+G" accesskey="G"
													onclick="return insertInstrumentIntoTemp('<bean:message key="lbl.plsSelOneRecord"/>');" ><bean:message key="button.save" /></button>
									<button type="button"  id="savenfor"
										class="topformbutton3" title="Alt+F" accesskey="F"
										onclick="return forwardPDCInstrument('<bean:message key="lbl.firstUpdateThenForward"/>','<bean:message key="msg.confirmationForwardMsg" />');" ><bean:message key="button.forward" /></button>
									
									<logic:present name="readyToForard">
									<button type="button"  id="del"
										class="topformbutton2" title="Alt+D" accesskey="D"
										onclick="return deleteInstrument('<bean:message key="lbl.plsSelOneRecord"/>');" ><bean:message key="button.delete" /></button>
									</logic:present>

								</td>
							</tr>
						</logic:notPresent>

					</table>

					<table width="100%" border="0" cellpadding="2" cellspacing="1">
					</table>
				</FIELDSET>
			</html:form>
		</div>

	</div>
	
	<logic:present name="alertMsg">
		<script type="text/javascript">
	if(('<%=request.getAttribute("alertMsg")%>') == 'Q'){
	 alert("PDC Updated ");	
	}
	if(('<%=request.getAttribute("alertMsg")%>') == 'E'){
	 alert("ECS Updated ");	
	}
	if(('<%=request.getAttribute("alertMsg")%>') == 'H'){
		 alert("NACH Updated ");	
		}
	if(('<%=request.getAttribute("alertMsg")%>') == 'D'){
	 alert("Direct Debit Updated ");	
	}
	
	if(('<%=request.getAttribute("alertMsg")%>') == 'N'){
	 alert("PDC not Updated ");	
	}
	
		if(('<%=request.getAttribute("alertMsg")%>') == 'NE'){
	 alert("ECS not Updated ");	
	}
		if(('<%=request.getAttribute("alertMsg")%>') == 'NH'){
			 alert("NACH not Updated ");	
			}
	if(('<%=request.getAttribute("alertMsg")%>') == 'I'){
	 alert("Same PDC/s exist for the selected Bank/Branch/Account No. combination of the captioned Loan. ");	
	}
	
	</script>
	</logic:present>

	<logic:present name="alertMsgforSanF">
		<script type="text/javascript">
	
	if(('<%=request.getAttribute("alertMsgforSanF")%>') == 'Y'){
	 alert("Data forwarded successfully");	
	  document.getElementById("sourcingForm").action="instrumentUpdateMakerActions.do?method=searchInstrumentForUpdate";
	    document.getElementById("sourcingForm").submit();
	}
	if(('<%=request.getAttribute("alertMsgforSanF")%>') == 'N'){
	 alert("Data not forwarded");
	  
	}
	
	</script>
	</logic:present>

	<logic:present name="alertMsgfrDel">
		<script type="text/javascript">
	if(('<%=request.getAttribute("alertMsgfrDel")%>') == 'Y'){
	 alert("Data deleted successfully");	
	  
	}
	if(('<%=request.getAttribute("alertMsgfrDel")%>') == 'N'){
	 alert("Data not deleted ");
	  
	}
	
</script>
	</logic:present>
	
<script>
	setFramevalues("sourcingForm");
</script>
</body>
</html:html>