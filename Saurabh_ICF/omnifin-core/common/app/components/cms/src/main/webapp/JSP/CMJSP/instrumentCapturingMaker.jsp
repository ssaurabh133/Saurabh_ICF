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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
		<script type="text/javascript" src="<%=path%>/js/cmScript/instrumentCapturing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>

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
<body onload="enableAnchor();ecsFunction();checkChanges('sourcingForm');defaultFocus();init_fields();" onclick="parent_disable();"
	onunload="closeLOVWindow();closeAllLovCallUnloadBody();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>

	<div id="centercolumn">
		<div id="revisedcontainer">
		
	
			<html:form action="/instrumentCapProcessAction" method="post"
				styleId="sourcingForm">
				
		   <logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		   <input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
		
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

				<logic:present name="insNonInsFlag">
				
					<logic:iterate name="insNonInsFlag" id="insNonInsFlagObj">

						<input type="hidden" name="loanInstallmentMode"
							id="loanInstallmentMode"
							value="${insNonInsFlagObj.loanInstallmentMode}" />
						<input type="hidden" name="loanAdvanceInstallment"
							id="loanAdvanceInstallment"
							value="${insNonInsFlagObj.loanAdvanceInstallment}" />
							<html:hidden property="repayment" styleId="repayment" value="${arryList[0].repayment}"/>


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
						<bean:message key="lbl.instrumentCapturingMaker" />
					</legend>
					
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
												<!--<html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(49,'sourcingForm','loanAccNo','','', '','','','customerName','totalInstallments','installmentAmount');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>-->
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="${arryList[0].lbxLoanNoHID}" />
											</logic:notEmpty></logic:present>
											<!--  <img onclick="openLOVCommon(49,'sourcingForm','loanAccNo','','', '','','','customerName','totalInstallments','installmentAmount')" SRC="<%=request.getContextPath()%>/images/lov.gif">-->

											<logic:notPresent name="arryList">
												<logic:notPresent name="author">
													<html:text styleClass="text" property="loanAccNo"
														styleId="loanAccNo" tabindex="-1" maxlength="100"
														readonly="true" />
													<html:button property="loanButton" styleId="loanButton"
														onclick="openLOVCommon(49,'sourcingForm','loanAccNo','','', '','','insnonins','customerName','totalInstallments','installmentAmount');closeLovCallonLov1();"
														value=" " styleClass="lovbutton"></html:button>
													<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID" />
													<html:hidden property="repayment" styleId="repayment" value="${arryList[0].repayment}"/>

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
														styleId="instrumentType" styleClass="text" value="${arryList[0].instrumentType}"
														onchange="ecsFunction();">
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
														styleClass="text" onchange="select();disableFromTo();" value="">
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
				 							<html:select property="submitBy" styleId="submitBy" value="" styleClass="text" onchange="getPDCSubmitCustomerName();">
                 							<option value="">---Select---</option>
                 							<logic:present name="applist" >          	
          	     							<html:optionsCollection name="applist" label="applicant_desc" value="applicant_code" />
                 							</logic:present>
                 							<option value="OTHER">OTHER</option>
          		 							</html:select>
          								</td>
          								</tr>
          								<tr id=s3 style="display: none;">
          								<td><bean:message key="lbl.nameOfPDCSubmitBy" /></td>
          								<td>
          							    <div id="s4">
          								     <html:text styleClass="text" property="pdcSubmitCustomerName"
													styleId="pdcSubmitCustomerName" tabindex="-1" maxlength="100"
													value=""  />
										</div>	
										<div id="s5" style="display: none;">
          								     <html:text styleClass="text" property="pdcSubmitCustomerName"
													styleId="pdcSubmitCustomerName1" tabindex="-1" maxlength="100"
													value=""  />
										</div>
          								</td>
          								</tr>
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
										
										<tr>
											<td>
												<bean:message key="lbl.fromInstallment" />
												<font color="red">*</font>
											</td>
											<td>
												<label>
													<html:text styleClass="text" property="fromInstallment"
														styleId="fromInstallment" maxlength="3"
														onkeypress="return isNumberKey(event);" readonly="true"/>
												</label>
											</td>

											<td>
												<bean:message key="lbl.toInstallment" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text styleClass="text" property="toInstallment"
													styleId="toInstallment" maxlength="3"
													onchange="return validate();"
													onkeypress="return isNumberKey(event);" readonly="true" />
											</td>
										</tr>
										<tr id="SIN">
											<td>
												<bean:message key="lbl.startingChequeNo" />
												<font color="red">*</font>
											</td>
											<td>
												<label>
													<html:text styleClass="text" property="startingChequeNo"
														styleId="startingChequeNo" maxlength="7"
														onkeypress="return isNumberKey(event);" />
												</label>
											</td>
											<td>
												<bean:message key="lbl.endingChequeNo" />
												<font color="red">*</font>
											</td>
											<td>
												<label>
													<html:text styleClass="text" property="endingChequeNo"
														styleId="endingChequeNo" maxlength="7"
														onkeypress="return isNumberKey(event);" />
												</label>
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.bank" />
												<font color="red">*</font>
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
												<font color="red">*</font>
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
												<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="bankAccount"
													styleId="bankAccount" maxlength="20" />
											</td>


											<td>
												<bean:message key="lbl.instrumetAmount" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text styleClass="text" property="instrumentAmount"
													styleId="instrumentAmount" maxlength="100"
													onkeypress="return numbersonly(event,id,18)"
													onblur="formatNumber(this.value,id);"
													onkeyup="checkNumber(this.value, event, 18,id);"
													onfocus="keyUpNumber(this.value, event, 18,id);" />
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.location" />
											<font color="red">*</font>
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="location"
													styleId="location" maxlength="50" />
											</td>
											<td id="Y">
												<bean:message key="lbl.clearingType" />
												<font color="red">*</font>
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
													<bean:message key="lbl.customeracType" /><font color="red">*</font>
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
													<font color="red">*</font>
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
													<font color="red">*</font>
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
													<font color="red">*</font>
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
										
										<tr>
											<td>
											<html:hidden styleClass="text" property="instrumentPreEmiDate" styleId="instrumentPreEmiDate" value="${userobject.businessdate}" />
<!--											<div id="preemiDateLebel" style="display: none;">-->
<!--												<bean:message key="lbl.instrumentDate" />-->
<!--												<font color="red">*</font>-->
<!--											</div>-->
<!--											</td>-->
<!--											<td>-->
<!--											<div id="preemiDate" style="display: none;">-->
<!--												<html:text styleClass="text" property="instrumentPreEmiDate" styleId="instrumentPreEmiDate" maxlength="20" value="" />-->
<!--											</div>-->
											</td>

										</tr>
										
										<tr>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td></td>
											<td nowrap="nowrap">
												<button type="button" name="generateRepaymentSch"
													id="generateRepaymentSch" 
													title="Alt+S" accesskey="S"  class="topformbutton4"
													onclick="return viewRepaymentScheduleDisbursal();"><bean:message key="button.repayschedule" /></button>
												<button type="button"  id="save"
													class="topformbutton3" title="Alt+G" accesskey="G"
													onclick="return saveInstrument();" ><bean:message key="button.generatePDC" /></button>



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
												<b><bean:message key="lbl.submitBy" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.nameOfPDCSubmitBy" /> </b>
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
											<!-- Nishant Space starts -->
											<td>
												<b><bean:message key="lbl.location" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.clearingType" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.totalEMI" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.makerRemarks" /> </b>
											</td>
											<!-- Nishant space ends -->
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
<!--												<input type="hidden" name="installments" id="installments"-->
<!--													value="${arrListobj.installmentNo}" />-->
<!--												<input type="hidden" name="instrumentAmountAll"-->
<!--													id="instrumentAmountAll"-->
<!--													value="${arrListobj.instrumentAmount}" />-->
<!--												<input type="hidden" name="installmentAmountAll"-->
<!--													id="installmentAmountAll"-->
<!--													value="${arrListobj.installmentAmount}" />-->
<!--												<input type="hidden" name="purposeAll" id="purposeAll"-->
<!--													value="${arrListobj.purpose}" />-->
<!--												<input type="hidden" name="instrumentTypeAll"-->
<!--													id="instrumentTypeAll" value="${arrListobj.instrumentType}" />-->
<!--												<input type="hidden" name="startingChequeNoAll"-->
<!--													id="startingChequeNoAll"-->
<!--													value="${arrListobj.startingChequeNo}" />-->

												<logic:notEqual name="arrListobj" property="pdcStatus"
													value="P">
													<tr class="white2">
														<td>
															<input type="checkbox" name="" id=""
																value="${arrListobj.instrumentID}" disabled />
														</td>

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
															${arrListobj.submitBy}
														</td>
														<td>
															${arrListobj.pdcSubmitCustomerName}
														</td>
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
														<!-- Nishant starts -->
														<td>
															${arrListobj.location}
														</td>
														<td>
															${arrListobj.clearingType}
														</td>
														<td>
															${arrListobj.totalEMI}
														</td>
														<td>
															${arrListobj.remarks}
														</td>
														<!-- Nishant ends -->
														<td>
															${arrListobj.makerDate}
														</td>
														<td>
															${arrListobj.maker}
														</td>
														
														<html:hidden property="lbxLoanNoHidden"	styleId="lbxLoanNoHidden" value="lbxLoanNoHID" />
													</tr>
												</logic:notEqual>

												<logic:equal name="arrListobj" property="pdcStatus"	value="P">
													<tr class="white1">

														<td>
															<input type="checkbox" name="chk"
																id="chk<%=count.intValue() + 1%>"
																value="${arrListobj.instrumentID }" />
														</td>



														<td>
															${arrListobj.date}
														</td>

														<logic:equal name="arrListobj" property="installmentNo"
															value="">
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


														<logic:notEqual name="arrListobj" property="installmentNo"
															value="">
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
															${arrListobj.submitBy}
														</td>
														<td>
															${arrListobj.pdcSubmitCustomerName}
														</td>
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
														
														<logic:notEqual name="arrListobj" property="installmentNo"
															value="">
															<td>
																<a href="#"
																	onclick="fnDetailsforLinkedlan('${arrListobj.lbxLoanNoHID}','${arrListobj.date}','${arrListobj.installmentAmount}','${arrListobj.instrumentAmount}','${arrListobj.instrumentID}','${arrListobj.installmentNo}');">Linked
																	Loan</a>
															</td>
														</logic:notEqual>
														<logic:equal name="arrListobj" property="installmentNo" value="">
															<td></td>
														</logic:equal>
														<!-- Nishant starts -->
														<td>
															${arrListobj.location}
														</td>
														<td>
															${arrListobj.clearingType}
														</td>
														<td>
															${arrListobj.totalEMI}
														</td>
														<td>
															${arrListobj.remarks}
														</td>
														<!-- Nishant ends -->
														<td>
															${arrListobj.makerDate}
														</td>
														<td>
															${arrListobj.maker}
														</td>
														<html:hidden property="lbxLoanNoHidden"
															styleId="lbxLoanNoHidden" value="lbxLoanNoHID" />
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
												<b><bean:message key="lbl.submitBy" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.nameOfPDCSubmitBy" /> </b>
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
											<!-- Nishant Space starts -->
											<td>
												<b><bean:message key="lbl.location" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.clearingType" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.totalEMI" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.makerRemarks" /> </b>
											</td>
											<!-- Nishant space ends -->
											<td>
												<b><bean:message key="lbl.makerDate" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.maker" /> </b>
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
															${arrListobj.submitBy}
														</td>
														<td>
															${arrListobj.pdcSubmitCustomerName}
														</td>
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
													<logic:notEqual name="arrListobj" property="installmentNo"
															value="">
														<td>
															<a href="#"
																onclick="fnDetailsforLinkedlan('${arrListobj.lbxLoanNoHID}','${arrListobj.date}','${arrListobj.installmentAmount}','${arrListobj.instrumentAmount}','${arrListobj.instrumentID}','${arrListobj.installmentNo}');">Linked
																Loan</a>
														</td>
													</logic:notEqual>
													<logic:equal name="arrListobj" property="installmentNo" value="">
														<td></td>
													</logic:equal>
													<!-- Nishant starts -->
													<td>
														${arrListobj.location}
													</td>
													<td>
														${arrListobj.clearingType}
													</td>
													<td>
														${arrListobj.totalEMI}
													</td>
													<td>
														${arrListobj.remarks}
													</td>
													<!-- Nishant ends -->
													<td>
														${arrListobj.makerDate}
													</td>
													<td>
														${arrListobj.maker}
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

									<button type="button"  id="savenfor"
										class="topformbutton3" title="Alt+F" accesskey="F"
										onclick="return savenForPDCInstrument('<bean:message key="lbl.plsGenratePDC"/>','<bean:message key="msg.confirmationForwardMsg" />');" ><bean:message key="button.savefrwd" /></button>
									<button type="button"  id="del"
										class="topformbutton2" title="Alt+D" accesskey="D"
										onclick="return deletePDCInstrument('<bean:message key="lbl.plsSelOneRecord"/>');" ><bean:message key="button.delete" /></button>


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
	<logic:present name="procval">
		<script type="text/javascript">
	if('<%=request.getAttribute("procval")%>'!='NONE')
	{
	   	alert('<%=request.getAttribute("procval").toString()%>');
	   	document.getElementById("sourcingForm").action="<%=request.getContextPath()%>/instrumentCapProcessActionforNew.do?method=searchInstrument";
		document.getElementById("sourcingForm").submit();
	}
	</script>
	</logic:present>
	<logic:present name="alertMsg">
		<script type="text/javascript">
	if(('<%=request.getAttribute("alertMsg")%>') == 'Q'){
	 alert("PDC generated ");	
	}
	if(('<%=request.getAttribute("alertMsg")%>') == 'E'){
	 alert("ECS generated ");	
	}
	if(('<%=request.getAttribute("alertMsg")%>') == 'H'){
		 alert("NACH generated ");	
		}
	if(('<%=request.getAttribute("alertMsg")%>') == 'D'){
	 alert("Direct Debit generated ");	
	}
	
	if(('<%=request.getAttribute("alertMsg")%>') == 'N'){
	 alert("PDC not generated ");	
	}
	
		if(('<%=request.getAttribute("alertMsg")%>') == 'NE'){
	 alert("ECS not generated ");	
	}
		if(('<%=request.getAttribute("alertMsg")%>') == 'NH'){
			 alert("NACH not generated ");	
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
	  document.getElementById("sourcingForm").action="instrumentCapProcessActionforNew.do?method=searchInstrument";
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