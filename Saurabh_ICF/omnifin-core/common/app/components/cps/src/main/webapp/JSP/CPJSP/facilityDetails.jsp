<!--  
Author Name:- Amit Kumar
Date of Creation:- 30/04/2011
Purpose:-  
-->


<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp"%>
<%@include file="/JSP/commonIncludeContent.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="" />


<link type="text/css"
	href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css"
	rel="stylesheet" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
	
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/cpScript/facilityDetails.js"></script>
		
 	<!--jquery and js for number formatting -->
  	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/jquery.emicalculator.min.js"></script>
       <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/globalize.min.js"></script>
    <!-- jquery and js for number formatting -->
	
<style type="text/css">
textarea {
	color: #000;
	font-family: arial, serif;
	font-size: 13px;
	padding-left: 2px;
	width: 700px;
	resize: none;
	height: 50px;
}
</style>
<title><bean:message key="lbl.facilityDetails" /></title>
</head>
<body oncontextmenu="return false"
	onload="fnFinalRate1();enableAnchor();checkChanges('specialConditionForm');">

<div id=centercolumn>
		<div id=revisedcontainer>

	<div align="center" class="opacity" style="display: none;"
		id="processingImage"></div>
	<input type="hidden" name="formatD" id="formatD"
		value="<bean:message key="lbl.dateFormat"/>" />
	<logic:present name="image">
		<input type="hidden" id="CalImage"
			value='<%=request.getContextPath() %>/${image}/calendar.gif' />
	</logic:present>
	<logic:notPresent name="image">
		<input type="hidden" id="CalImage"
			value='<%=request.getContextPath()%>/images/theme1/calendar.gif' />
	</logic:notPresent>
	<input type="hidden" id="businessdate"
		value='${userobject.businessdate}' />
		
		<fieldset>
		
		<table cellSpacing=0 cellPadding=0 width="100%" border=0>
		  <tr>
		     <td class="gridtd">
		 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
		        <tr class="white2">
		          <td><b><bean:message key="lbl.dealNo" /></b></td>
		          <td>${dealHeader[0].dealNo}</td>
		          <td><b><bean:message key="lbl.date"/></b></td>
		          <td>${dealHeader[0].dealDate}</td>
		          <td><b><bean:message key="lbl.customerName"/></b> </td>
		          <td colspan="3" >${dealHeader[0].dealCustomerName}</td>
		         </tr>
		          <tr class="white2">
			          <td><b><bean:message key="lbl.productType"/></b></td>
			          <td >${dealHeader[0].dealProductCat}</td>
			          <td ><b><bean:message key="lbl.product"/></b></td>
			          <td >${dealHeader[0].dealProduct}</td>
			          <td ><b><bean:message key="lbl.scheme"/></b></td>
			          <td>${dealHeader[0].dealScheme}</td>
			          <td><b><bean:message key="lbl.currentStage"/></b></td>
			          <td >Facility Details</td>
		          </tr>
		        </table> 
		</td>
		</tr>
		</table>
		 
		</fieldset>
		
		
		<html:form styleId="facilityDetailsForm"
						action="/facilityDetails" method="post">
	<logic:notPresent name = "facilityDetailsDV">
	<div>

		<div>
				<fieldset>
					<html:hidden property="contextPath" styleId="contextPath"
						value="<%=request.getContextPath()%>" />
					<html:hidden property="specialDealId" styleId="specialDealId"
						value="${fecthList[0].specialDealId}" />
						
				
					<legend>
						<bean:message key="lbl.facilityDetails" />
					</legend>
					<logic:notPresent name="fetchFacilityDetailsList">
					<html:hidden property="dealRepaymentType" styleId="dealRepaymentType"
						value="${dealProductDetailList[0].dealRepaymentType}" />
						
	
	<input type="hidden" id="minFlatRate" name="minFlatRate" value="${requestScope.allDetail[0].minFlatRate}"/>
	<input type="hidden" id="maxFlatRate" name="maxFlatRate" value="${requestScope.allDetail[0].maxFlatRate}"/>
	<input type="hidden" id="minEffectiveRate" name="minEffectiveRate" value="${requestScope.allDetail[0].minEffectiveRate}"/>
	<input type="hidden" id="maxEffectiveRate" name="maxEffectiveRate" value="${requestScope.allDetail[0].maxEffectiveRate}"/>
	
	<input type="hidden" id="defaultFlatRate" name="defaultFlatRate" value="${requestScope.allDetail[0].defaultFlatRate}"/>
	<input type="hidden" id="defaultEffectiveRate" name="defaultEffectiveRate" value="${requestScope.allDetail[0].defaultEffectiveRate}"/>
	<input type="hidden" id="minFinance" name="minFinance" value="${requestScope.allDetail[0].minFinanceAmount}"/>
	<input type="hidden" id="maxFinance" name="maxFinance" value="${requestScope.allDetail[0].maxFinanceAmount}" />
	<input type="hidden" id="minMarginRate" name="minMarginRate" value="${requestScope.allDetail[0].minMRate}"/>
	<input type="hidden" id="maxMarginRate" name="maxMarginRate" value="${requestScope.allDetail[0].maxMRate}" />
	<input type="hidden" id="minTenure" name="minTenure" value="${requestScope.allDetail[0].minTenure}"/>
	<input type="hidden" id="maxTenure" name="maxTenure" value="${requestScope.allDetail[0].maxTenure}"/>
	<input type="hidden" id="rate" name="rate" value="${requestScope.allDetail[0].baseRateType}"/>
	<input type="hidden" id="rateBase" name="rateBase" value="${requestScope.allDetail[0].baseRate}"/>
						
						<html:hidden property="actionMode" styleClass="text" styleId="actionMode"
										value="I" />
					
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>

									<td><bean:message key="lbl.product" /><font color="red">*</font></td>
									<td><html:text property="productDesc"
										styleClass="text" styleId="productDesc"
										value="${dealProductDetailList[0].productDesc}" readonly="true"/>
										
										<html:hidden
										property="productId" styleClass="text" styleId="productId"
										value="${dealProductDetailList[0].productId}" /></td>
									<td><bean:message key="lbl.scheme" /><font color="red">*</font></td>
									<td><html:text styleClass="text"
										property="schemeDesc" styleId="schemeDesc" maxlength="20" value=""
										readonly="true" tabindex="-1" /> 
										<html:button
										property="schemeButton" styleId="schemeButton"
										onclick="openLOVCommon(6160,'facilityDetailsForm','schemeDesc','productId','schemeId', 'productId','Please Select Product','getDefaultLoanFacilityDetail','schemeId');"
										value=" " styleClass="lovbutton"></html:button> 
										<html:hidden
										property="schemeId" styleId="schemeId" value="" /></td>
								</tr>
								<tr>
									<td><bean:message key="lbl.loanAmount" /><font color="red">*</font></td>
									<td>
									<html:text property="loanAmount"
									styleClass="text" styleId="loanAmount"
									value="" maxlength="22" onblur="numberFormatting(this.id,'2');LoanAmountCkeck();" style="text-align:right;"/>																
									</td>
									<td><bean:message key="lbl.tenure" /><font color="red">*</font></td>
									<td><html:text property="txtTenure"
									styleClass="text" styleId="txtTenure"
									value="" maxlength="3" onkeypress="return isNumberKey(event);" onchange = "fnCalculateInstNo();fnCalcTenure();" /></td>
								</tr>
								<tr>
									  <td><bean:message key="lbl.insurancePremium" /></td>
							                     <td ><html:text property="insurancePremium" styleClass="text" styleId="insurancePremium"  
							                     style="text-align: right" value="${fetchFacilityDetailsList[0].insurancePremium}" onkeypress="return isNumberKey(event);" /></td>
								           
									<td><bean:message key="lbl.interestRateType" /><font color="red">*</font></td>
									<td>
										<html:select property="interestRateType" styleId="interestRateType"
													styleClass="text" value="" 
													onchange="getBasefacilityRate();fnFinalRate();">
													<html:option value="E">Effective</html:option>
													<html:option value="F">Flat</html:option>
												</html:select>
											</td>
									
								</tr>
								
								<tr>
									<td><bean:message key="lbl.interestRateMethod" /><font color="red">*</font></td>
									<td>
						      			<html:select property="interestRateMethod" styleId="interestRateMethod"
						      						styleClass="text" value="${fetchFacilityDetailsList[0].interestRateMethod}" disabled="true" 
						      						onchange="fnValidateRateMethod();">
						      						<html:option value="F">Fixed</html:option>
						      						<html:option value="L">Floating</html:option>
						      					</html:select>
						      				</td>
						      			<td><bean:message key="lbl.baseRateType" /></td>
									
									<td>
												<html:select property="baseRateType" styleClass="text" styleId="baseRateType" 
															
															value="${fetchFacilityDetailsList[0].baseRateType}" onchange="return getBasefacilityRate();">
															<html:option value="">--Select--</html:option>
									             			<logic:present name="baseRateList">
									               				<html:optionsCollection name="baseRateList" label="id" value="id" /> 
									            			</logic:present>
									          			</html:select>
											</td>
									
								</tr>
								
								<tr>
								
								    <td><bean:message key="lbl.baseRate" /></td>
									<td>
											<html:text property="baseRate" styleId="baseRate" value="${fetchFacilityDetailsList[0].baseRate}" styleClass="text" readonly="true" tabindex="-1" style="text-align:right;"/>

											</td>
											    <td><bean:message key="lbl.markUp" /></td>
											<td>
											<html:text property="markup" styleId="markup" value="${fetchFacilityDetailsList[0].markup}" styleClass="text" readonly="true" maxlength="3" onkeypress="return sevenDecimalnumbersonly(event, id, 3)" 
													onblur="sevenDecimalNumber(this.value, id);" 
													onkeyup="sevenDecimalcheckNumber(this.value, event, 3, id);" style="text-align:right;"/>

											</td>
								</tr>
								
								<tr>
								    <td><bean:message key="lbl.finalRate" /><font color="red">*</font></td>
									<td>
											<html:text property="finalRate" styleId="finalRate" value="${fetchFacilityDetailsList[0].finalRate}" styleClass="text" 
											onblur="fourDecimalNumber(this.value, id);checkRate('finalRate');calculateFacilityFinalRate();" onkeyup="checkNumber(this.value, event, 3, id);" onfocus="keyUpNumber(this.value, event, 3, id);"
											 tabindex="-1" style="text-align:right;"/>
								
											</td>
										<td><bean:message key="lbl.installmentFreq" /><font color="red">*</font></td>
										<td>
												<html:select property="installmentFrequency" styleId="installmentFrequency"
													styleClass="text" value="${fetchFacilityDetailsList[0].installmentFrequency}"
													onchange = "fnCalculateInstNo();">
													<html:option value="M">Monthly</html:option>
													<html:option value="B">Bimonthly</html:option>
													<html:option value="Q">Quarterly</html:option>
													<html:option value="H">Half Yearly</html:option>
													<html:option value="Y">Yearly</html:option>
												</html:select>
											</td>
								</tr>
								
								<tr>
								<td><bean:message key="lbl.installmentType" /><font color="red">*</font></td>
									<td>
												<html:select property="installmentType" styleId="installmentType"
													styleClass="text" value="">
													<html:option value="E">Eq. Installment</html:option>
													<html:option value="G">Gr. Installment</html:option>
													<html:option value="P">Eq. Principal</html:option>
													<html:option value="Q">Gr. Principal1</html:option>
													<html:option value="S">Gr. Principal2</html:option>
												</html:select>
											</td>
										<td><bean:message key="lbl.noOfInstallment" /><font color="red">*</font></td>
								<td>
								 <html:text property="noOfInstl" styleId="noOfInstl" value="${fetchFacilityDetailsList[0].noOfInstl}" styleClass="text" maxlength="3" onkeypress="return isNumberKey(event);" onchange="calcDay();calculateInstallment();calculateMaturityDateInDeal();" readonly="true" tabindex="-1" style="text-align:right;"/>

											</td>
								</tr>
								
								
								
						</table>
						
						<br/>
						<table>
						<logic:notEqual name="functionId" value="500000123">	
							<tr>
								<td><button type="button" class="topformbutton2" name="save"  id="save"  onclick="return fnSaveFacilityDetails();" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button></td>
								<td><button type="button" class="topformbutton2" name="delete"  id="delete"  onclick="return fnDeleteFacilityDetails();" title="Alt+D" accesskey="D" ><bean:message key="button.delete" /></button></td>
								<td><button type="button" class="topformbutton2" name="clear"  id="save"  onclick="return fnClearFacilityDetails();" title="Alt+C" accesskey="V" ><bean:message key="button.clear" /></button></td>
								
							</tr>
						</logic:notEqual>
						</table>
	
					</logic:notPresent>
					
					
					<logic:present name="fetchFacilityDetailsList">
						<html:hidden property="dealLoanId" styleClass="text" styleId="dealLoanId"
										value="${fetchFacilityDetailsList[0].dealLoanId}" />
						
						<html:hidden property="actionMode" styleClass="text" styleId="actionMode"
										value="${actionMode}" />
						<html:hidden property="dealRepaymentType" styleId="dealRepaymentType"
						value="${fetchFacilityDetailsList[0].dealRepaymentType}" />
	
	 <input type="hidden" id="minFlatRate" name="minFlatRate" value="${requestScope.allDetail[0].minFlatRate}"/>
	<input type="hidden" id="maxFlatRate" name="maxFlatRate" value="${requestScope.allDetail[0].maxFlatRate}"/>
	<input type="hidden" id="minEffectiveRate" name="minEffectiveRate" value="${requestScope.allDetail[0].minEffectiveRate}"/>
	<input type="hidden" id="maxEffectiveRate" name="maxEffectiveRate" value="${requestScope.allDetail[0].maxEffectiveRate}"/>
	
	<input type="hidden" id="defaultFlatRate" name="defaultFlatRate" value="${requestScope.allDetail[0].defaultFlatRate}"/>
	<input type="hidden" id="defaultEffectiveRate" name="defaultEffectiveRate" value="${requestScope.allDetail[0].defaultEffectiveRate}"/>
	<input type="hidden" id="minFinance" name="minFinance" value="${requestScope.allDetail[0].minFinanceAmount}"/>
	<input type="hidden" id="maxFinance" name="maxFinance" value="${requestScope.allDetail[0].maxFinanceAmount}" />
	<input type="hidden" id="minMarginRate" name="minMarginRate" value="${requestScope.allDetail[0].minMRate}"/>
	<input type="hidden" id="maxMarginRate" name="maxMarginRate" value="${requestScope.allDetail[0].maxMRate}" />
	<input type="hidden" id="minTenure" name="minTenure" value="${requestScope.allDetail[0].minTenure}"/>
	<input type="hidden" id="maxTenure" name="maxTenure" value="${requestScope.allDetail[0].maxTenure}"/>
	<input type="hidden" id="rate" name="rate" value="${requestScope.allDetail[0].baseRateType}"/>
	<input type="hidden" id="rateBase" name="rateBase" value="${requestScope.allDetail[0].baseRate}"/>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>

									<td><bean:message key="lbl.product" /><font color="red">*</font></td>
									<td><html:text property="productDesc"
										styleClass="text" styleId="productDesc"
										value="${fetchFacilityDetailsList[0].productDesc}" readonly="true"/>
										<html:hidden
										property="productId" styleClass="text" styleId="productId"
										value="${fetchFacilityDetailsList[0].productId}" /></td>
									<td><bean:message key="lbl.scheme" /><font color="red">*</font></td>
									<td><html:text styleClass="text"
										property="schemeDesc" styleId="schemeDesc" maxlength="20" value="${fetchFacilityDetailsList[0].schemeDesc}"
										readonly="true" tabindex="-1" /> 
										<html:button
										property="schemeButton" styleId="schemeButton"
										onclick="openLOVCommon(6160,'facilityDetailsForm','schemeDesc','productId','schemeId', 'productId','Please Select Product','getDefaultLoanFacilityDetail','schemeId');"
										value=" " styleClass="lovbutton"></html:button> 
										<html:hidden
										property="schemeId" styleId="schemeId" value="${fetchFacilityDetailsList[0].schemeId}" /></td>
								</tr>
								<tr>
									<td><bean:message key="lbl.loanAmount" /><font color="red">*</font></td>
									<td>
									<html:text property="loanAmount"
									styleClass="text" styleId="loanAmount"
									value="${fetchFacilityDetailsList[0].loanAmount}" maxlength="22" onblur="numberFormatting(this.id,'2');LoanAmountCkeck();" style="text-align:right;" />
									</td>
									<td><bean:message key="lbl.tenure" /><font color="red">*</font></td>
									<td><html:text property="txtTenure"
									styleClass="text" styleId="txtTenure"
									value="${fetchFacilityDetailsList[0].txtTenure}" maxlength="3" onkeypress="return isNumberKey(event);" onchange = "fnCalculateInstNo();fnCalcTenure();" /></td>
								</tr>
								<tr>
									  <td><bean:message key="lbl.insurancePremium" /></td>
							                     <td ><html:text property="insurancePremium" styleClass="text" styleId="insurancePremium"  
							                     style="text-align: right" onkeypress="return isNumberKey(event);" value="${fetchFacilityDetailsList[0].insurancePremium}" /></td>
								  
									<td><bean:message key="lbl.interestRateType" /><font color="red">*</font></td>
									<td>
										<html:select property="interestRateType" styleId="interestRateType"
													styleClass="text" value="${fetchFacilityDetailsList[0].interestRateType}" 
													onchange="fnFinalRate();" >
													<html:option value="E">Effective</html:option>
													<html:option value="F">Flat</html:option>
												</html:select>
											</td>
									
								</tr>
									
								<tr>
									<td><bean:message key="lbl.interestRateMethod" /><font color="red">*</font></td>
									<td>
						      			<html:select property="interestRateMethod" styleId="interestRateMethod" disabled="true" 
						      						styleClass="text" value="${fetchFacilityDetailsList[0].interestRateMethod}"
						      						onchange="fnValidateRateMethod();">
						      						<html:option value="F">Fixed</html:option>
						      						<html:option value="L">Floating</html:option>
						      					</html:select>
						      				</td>
						      			<td><bean:message key="lbl.baseRateType" /></td>
									
									<td>
												<html:select property="baseRateType" styleClass="text" styleId="baseRateType" 
															
															value="${fetchFacilityDetailsList[0].baseRateType}" onchange="return getBasefacilityRate();">
															<html:option value="">--Select--</html:option>
									             			<logic:present name="baseRateList">
									               				<html:optionsCollection name="baseRateList" label="id" value="id" /> 
									            			</logic:present>
									          			</html:select>
											</td>
									<input type="hidden" id="baseRateType" name="baseRateType" value="${requestScope.allDetail[0].baseRateType}"/>
								</tr>
								
								<tr>
								
								    <td><bean:message key="lbl.baseRate" /></td>
									<td>
											<html:text property="baseRate" styleId="baseRate" value="${requestScope.allDetail[0].baseRate}" styleClass="text" readonly="true" tabindex="-1" style="text-align:right;"/>
									<input type="hidden" id="baseRate" name="baseRate" value="${requestScope.allDetail[0].baseRate}"/>
											</td>
											    <td><bean:message key="lbl.markUp" /></td>
											<td>
											<html:text property="markup" styleId="markup" value="${fetchFacilityDetailsList[0].markup}" styleClass="text" readonly="true" maxlength="3" onkeypress="return sevenDecimalnumbersonly(event, id, 3)" 
													onblur="sevenDecimalNumber(this.value, id);" 
													onkeyup="sevenDecimalcheckNumber(this.value, event, 3, id);" style="text-align:right;"/>

											</td>
								</tr>
								
								<tr>
								    <td><bean:message key="lbl.finalRate" /><font color="red">*</font></td>
									<td>
											<html:text property="finalRate" styleId="finalRate" value="${fetchFacilityDetailsList[0].finalRate}" styleClass="text" 
											onblur="fourDecimalNumber(this.value, id);checkRate('finalRate');calculateFacilityFinalRate();finalRate();" onkeyup="checkNumber(this.value, event, 3, id);" onfocus="keyUpNumber(this.value, event, 3, id);"
											 tabindex="-1" style="text-align:right;"/>
								
											</td>
										<td><bean:message key="lbl.installmentFreq" /><font color="red">*</font></td>
										<td>
												<html:select property="installmentFrequency" styleId="installmentFrequency"
													styleClass="text" value="${fetchFacilityDetailsList[0].installmentFrequency}"
													onchange="fnCalculateInstNo();">
													<html:option value="M">Monthly</html:option>
													<html:option value="B">Bimonthly</html:option>
													<html:option value="Q">Quarterly</html:option>
													<html:option value="H">Half Yearly</html:option>
													<html:option value="Y">Yearly</html:option>
												</html:select>
											</td>
								</tr>
								
								<tr>
								<td><bean:message key="lbl.installmentType" /><font color="red">*</font></td>
									<td>
												<html:select property="installmentType" styleId="installmentType"
													styleClass="text" value="${fetchFacilityDetailsList[0].installmentType}">
													<html:option value="E">Eq. Installment</html:option>
													<html:option value="G">Gr. Installment</html:option>
													<html:option value="P">Eq. Principal</html:option>
													<html:option value="Q">Gr. Principal1</html:option>
													<html:option value="S">Gr. Principal2</html:option>
												</html:select>
											</td>
											<td><bean:message key="lbl.noOfInstallment" /><font color="red">*</font></td>
								<td>
								 <html:text property="noOfInstl" styleId="noOfInstl" value="${fetchFacilityDetailsList[0].noOfInstl}" styleClass="text" maxlength="3" onkeypress="return isNumberKey(event);" onchange="calcDay();calculateInstallment();calculateMaturityDateInDeal();" readonly="true" tabindex="-1" style="text-align:right;"/>

											</td>
											
								</tr>
								
								
						</table>
						
						<br/>
						<logic:notEqual name="functionId" value="500000123">
						<table>
							<tr>
							   <td><button type="button" class="topformbutton2" name="save"  id="save"  onclick="return fnUpdateFacilityDetails();" title="Alt+V" accesskey="V" ><bean:message key="button.update" /></button></td>
													
							   <td><button type="button" class="topformbutton2" name="delete"  id="delete"  onclick="return fnDeleteFacilityDetails();" title="Alt+D" accesskey="D" ><bean:message key="button.delete" /></button></td>
							   <td><button type="button" class="topformbutton2" name="clear"  id="save"  onclick="return fnClearFacilityDetails();" title="Alt+C" accesskey="V" ><bean:message key="button.clear" /></button></td>
							</tr>
						</table>
						</logic:notEqual>
	
					</logic:present>
					
				</fieldset>
		</div>
	</div>
</logic:notPresent>



<logic:present name = "facilityDetailsDV">
	<div>
<html:hidden property="dealLoanId" styleClass="text" styleId="dealLoanId"
										value="${fetchFacilityDetailsList[0].dealLoanId}" />
						
						<html:hidden property="actionMode" styleClass="text" styleId="actionMode"
										value="${actionMode}" />
						<html:hidden property="dealRepaymentType" styleId="dealRepaymentType"
						value="${fetchFacilityDetailsList[0].dealRepaymentType}" />
		<div>
				<fieldset>
					<html:hidden property="contextPath" styleId="contextPath"
						value="<%=request.getContextPath()%>" />
					<html:hidden property="specialDealId" styleId="specialDealId"
						value="${fecthList[0].specialDealId}" />
					<legend>
						<bean:message key="lbl.facilityDetails" />
					</legend>
					<logic:notPresent name="fetchFacilityDetailsList">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>

									<td><bean:message key="lbl.product" /><font color="red">*</font></td>
									<td><html:text property="productDesc"
										styleClass="text" styleId="productDesc"
										value="${dealProductDetailList[0].productDesc}" readonly="true"/>
										<html:hidden
										property="productId" styleClass="text" styleId="productId"
										value="${dealProductDetailList[0].productId}" /></td>
									<td><bean:message key="lbl.scheme" /><font color="red">*</font></td>
									<td><html:text styleClass="text"
										property="schemeDesc" styleId="schemeDesc" maxlength="20" value=""
										readonly="true" tabindex="-1" />
										<html:hidden
										property="schemeId" styleId="schemeId" value="" /></td>
								</tr>
								<tr>
									<td><bean:message key="lbl.loanAmount" /><font color="red">*</font></td>
									<logic:equal name="functionId" value="3000297">
									<td>
									<html:text property="loanAmount"
									styleClass="text" styleId="loanAmount"
									value="" maxlength="22"  onblur="numberFormatting(this.id,'2');LoanAmountCkeck();" style="text-align:right;"/>
									</td>
									</logic:equal>
									<logic:notEqual name="functionId" value="3000297">
									<td>
									<html:text property="loanAmount"
									styleClass="text" styleId="loanAmount"
									value="" maxlength="22" readonly="true" onblur="numberFormatting(this.id,'2');LoanAmountCkeck();" style="text-align:right;"/>
									</td>
									</logic:notEqual>
									<td><bean:message key="lbl.tenure" /><font color="red">*</font></td>
									<td><html:text property="txtTenure"
									styleClass="text" styleId="txtTenure"
									value="" maxlength="3" readonly="true"/></td>
								</tr>
								<tr>
								
									<td><bean:message key="lbl.insurancePremium" /></td>
							                     <td ><html:text property="insurancePremium" styleClass="text" styleId="insurancePremium"  readonly="true"
							                     style="text-align: right" value="${fetchFacilityDetailsList[0].insurancePremium}" onkeypress="return isNumberKey(event);"  onblur="numberFormatting(this.id,'2');" /></td>
								    
									<td><bean:message key="lbl.interestRateType" /><font color="red">*</font></td>
									<td>
										<html:select property="interestRateType" styleId="interestRateType"
													styleClass="text" value="" 
													disabled="true">
													<html:option value="E">Effective</html:option>
													<html:option value="F">Flat</html:option>
												</html:select>
											</td>
									
								</tr>
								
								<tr>
									<td><bean:message key="lbl.interestRateMethod" /><font color="red">*</font></td>
									<td>
						      			<html:select property="interestRateMethod" styleId="interestRateMethod"
						      						styleClass="text" value="${fetchFacilityDetailsList[0].interestRateMethod}" disabled="true" 
						      						>
						      						<html:option value="F">Fixed</html:option>
						      						<html:option value="L">Floating</html:option>
						      					</html:select>
						      				</td>
						      			<td><bean:message key="lbl.baseRateType" /></td>
									
									<td>
												<html:select property="baseRateType" styleClass="text" styleId="baseRateType" 
															disabled="true" 
															value="${fetchFacilityDetailsList[0].baseRateType}">
															<html:option value="">--Select--</html:option>
									             			<logic:present name="baseRateList">
									               				<html:optionsCollection name="baseRateList" label="id" value="id" /> 
									            			</logic:present>
									          			</html:select>
											</td>
									
								</tr>
								
								<tr>
								
								    <td><bean:message key="lbl.baseRate" /></td>
									<td>
											<html:text property="baseRate" styleId="baseRate" value="${requestScope.allDetail[0].baseRate}" styleClass="text" readonly="true" tabindex="-1" style="text-align:right;"/>

											</td>
											    <td><bean:message key="lbl.markUp" /></td>
											<td>
											<html:text property="markup" styleId="markup" value="${fetchFacilityDetailsList[0].markup}" styleClass="text" readonly="true" maxlength="3" onkeypress="return sevenDecimalnumbersonly(event, id, 3)" 
													onblur="sevenDecimalNumber(this.value, id);" 
													onkeyup="sevenDecimalcheckNumber(this.value, event, 3, id);" style="text-align:right;"/>

											</td>
								</tr>
								
								<tr>
								    <td><bean:message key="lbl.finalRate" /><font color="red">*</font></td>
									<td>
											<html:text property="finalRate" styleId="finalRate" value="${fetchFacilityDetailsList[0].finalRate}" styleClass="text" 
										onblur="fourDecimalNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 3, id);" onfocus="keyUpNumber(this.value, event, 3, id);"
											 tabindex="-1" style="text-align:right;" readonly="true"/>
								
											</td>
										<td><bean:message key="lbl.installmentFreq" /><font color="red">*</font></td>
										<td>
												<html:select property="installmentFrequency" styleId="installmentFrequency"
													styleClass="text" value="${fetchFacilityDetailsList[0].installmentFrequency}"
													 disabled="true">
													<html:option value="M">Monthly</html:option>
													<html:option value="B">Bimonthly</html:option>
													<html:option value="Q">Quarterly</html:option>
													<html:option value="H">Half Yearly</html:option>
													<html:option value="Y">Yearly</html:option>
												</html:select>
											</td>
								</tr>
								
								<tr>
								<td><bean:message key="lbl.installmentType" /><font color="red">*</font></td>
									<td>
										<html:select property="installmentType" styleId="installmentType" styleClass="text" value="" disabled="true">
											<html:option value="E">Eq. Installment</html:option>
											<html:option value="G">Gr. Installment</html:option>
											<html:option value="P">Eq. Principal</html:option>
											<html:option value="Q">Gr. Principal1</html:option>
											<html:option value="S">Gr. Principal2</html:option>
										</html:select>
									</td>
											
												<td><bean:message key="lbl.noOfInstallment" /><font color="red">*</font></td>
								<td>
								 <html:text property="noOfInstl" styleId="noOfInstl" value="${fetchFacilityDetailsList[0].noOfInstl}" styleClass="text" maxlength="3" readonly="true" tabindex="-1" style="text-align:right;"/>

											</td>
								</tr>
								
								
						</table>
						
						<br/>
							<%-- <logic:equal name="functionId" value="3000297">
					 <table>
							<tr>
								  <td><button type="button" class="topformbutton2" name="save"  id="save"  onclick="return fnUpdateFacilityDetails();" title="Alt+V" accesskey="V" ><bean:message key="button.update" /></button></td>
								
							</tr>
						</table>
						</logic:equal> --%>
					</logic:notPresent>
					
					
					<logic:present name="fetchFacilityDetailsList">
					<html:hidden property="dealLoanId" styleClass="text" styleId="dealLoanId"
										value="${fetchFacilityDetailsList[0].dealLoanId}" />
						
						<html:hidden property="actionMode" styleClass="text" styleId="actionMode"
										value="${actionMode}" />
						<html:hidden property="dealRepaymentType" styleId="dealRepaymentType"
						value="${fetchFacilityDetailsList[0].dealRepaymentType}" />
						<html:hidden property="facilityDetailsID" styleClass="text" styleId="productId"
										value="${fetchFacilityDetailsList[0].facilityDetailsID}" />
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>

									<td><bean:message key="lbl.product" /><font color="red">*</font></td>
									<td><html:text property="productDesc"
										styleClass="text" styleId="productDesc"
										value="${fetchFacilityDetailsList[0].productDesc}" readonly="true"/>
										<html:hidden
										property="productId" styleClass="text" styleId="productId"
										value="${fetchFacilityDetailsList[0].productId}" /></td>
									<td><bean:message key="lbl.scheme" /><font color="red">*</font></td>
									<td><html:text styleClass="text"
										property="schemeDesc" styleId="schemeDesc" maxlength="20" value="${fetchFacilityDetailsList[0].schemeDesc}"
										readonly="true" tabindex="-1" /> 
										
										<html:hidden
										property="schemeId" styleId="schemeId" value="${fetchFacilityDetailsList[0].schemeId}" /></td>
								</tr>
								<tr>
									<td><bean:message key="lbl.loanAmount" /><font color="red">*</font></td>
										<logic:equal name="functionId" value="3000297">
									<td>
									<html:text property="loanAmount"
									styleClass="text" styleId="loanAmount"
									value="${fetchFacilityDetailsList[0].loanAmount}" maxlength="22"  onblur="numberFormatting(this.id,'2');LoanAmountCkeck();" style="text-align:right;"/>
									</td>
									</logic:equal>
									
									<logic:notEqual name="functionId" value="3000297">
									<td>
									<html:text property="loanAmount"
									styleClass="text" styleId="loanAmount"
									value="${fetchFacilityDetailsList[0].loanAmount}" maxlength="22" readonly="true" onblur="numberFormatting(this.id,'2');LoanAmountCkeck();" style="text-align:right;"/>
									</td>
									</logic:notEqual>
									<td><bean:message key="lbl.tenure" /><font color="red">*</font></td>
									<td><html:text property="txtTenure"
									styleClass="text" styleId="txtTenure"
									value="${fetchFacilityDetailsList[0].txtTenure}" maxlength="3" readonly="true"/></td>
								</tr>
								<tr>
									
									 <td><bean:message key="lbl.insurancePremium" /></td>
							                     <td ><html:text property="insurancePremium" styleClass="text" styleId="insurancePremium"  readonly="true"
							                     style="text-align: right" value="${fetchFacilityDetailsList[0].insurancePremium}" onkeypress="return isNumberKey(event);" onblur="numberFormatting(this.id,'2');" /></td>
								
									<td><bean:message key="lbl.interestRateType" /><font color="red">*</font></td>
									<td>
										<html:select property="interestRateType" styleId="interestRateType"
													styleClass="text" value="" 
													disabled="true">
													<html:option value="E">Effective</html:option>
													<html:option value="F">Flat</html:option>
												</html:select>
											</td>
									
								</tr>
								
								<tr>
									<td><bean:message key="lbl.interestRateMethod" /><font color="red">*</font></td>
									<td>
						      			<html:select property="interestRateMethod" styleId="interestRateMethod"
						      						styleClass="text" value="${fetchFacilityDetailsList[0].interestRateMethod}" disabled="true" 
						      						>
						      						<html:option value="F">Fixed</html:option>
						      						<html:option value="L">Floating</html:option>
						      					</html:select>
						      				</td>
						      			<td><bean:message key="lbl.baseRateType" /></td>
									
										<td>
												<html:select property="baseRateType" styleClass="text" styleId="baseRateType" 
															disabled="true" 
															value="${fetchFacilityDetailsList[0].baseRateType}" >
															<html:option value="">--Select--</html:option>
									             			<logic:present name="baseRateList">
									               				<html:optionsCollection name="baseRateList" label="id" value="id" /> 
									            			</logic:present>
									          			</html:select>
											</td>
									
								</tr>
								
								<tr>
								
								    <td><bean:message key="lbl.baseRate" /></td>
									<td>
											<html:text property="baseRate" styleId="baseRate" value="${requestScope.allDetail[0].baseRate}" styleClass="text" readonly="true" tabindex="-1" style="text-align:right;"/>

											</td>
											    <td><bean:message key="lbl.markUp" /></td>
											<td>
											<html:text property="markup" styleId="markup" value="${fetchFacilityDetailsList[0].markup}" styleClass="text" readonly="true" maxlength="3" onkeypress="return sevenDecimalnumbersonly(event, id, 3)" 
													onblur="sevenDecimalNumber(this.value, id);" 
													onkeyup="sevenDecimalcheckNumber(this.value, event, 3, id);" style="text-align:right;"/>

											</td>
								</tr>
								
								<tr>
								    <td><bean:message key="lbl.finalRate" /><font color="red">*</font></td>
									<td>
											<html:text property="finalRate" styleId="finalRate" value="${fetchFacilityDetailsList[0].finalRate}" styleClass="text" 
										onblur="fourDecimalNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 3, id);" onfocus="keyUpNumber(this.value, event, 3, id);"
											 tabindex="-1" style="text-align:right;" readonly="true"/>
								
											</td>
										<td><bean:message key="lbl.installmentFreq" /><font color="red">*</font></td>
										<td>
												<html:select property="installmentFrequency" styleId="installmentFrequency"
													styleClass="text" value="${fetchFacilityDetailsList[0].installmentFrequency}"
													disabled="true">
													<html:option value="M">Monthly</html:option>
													<html:option value="B">Bimonthly</html:option>
													<html:option value="Q">Quarterly</html:option>
													<html:option value="H">Half Yearly</html:option>
													<html:option value="Y">Yearly</html:option>
												</html:select>
											</td>
								</tr>
								
								<tr>
								<td><bean:message key="lbl.installmentType" /><font color="red">*</font></td>
									<td>
												<html:select property="installmentType" styleId="installmentType"
													styleClass="text" value="${fetchFacilityDetailsList[0].installmentType}" disabled="true">
													<html:option value="E">Eq. Installment</html:option>
													<html:option value="G">Gr. Installment</html:option>
													<html:option value="P">Eq. Principal</html:option>
													<html:option value="Q">Gr. Principal1</html:option>
													<html:option value="S">Gr. Principal2</html:option>
												</html:select>
											</td>
											
										<td><bean:message key="lbl.noOfInstallment" /><font color="red">*</font></td>
								<td>
								 <html:text property="noOfInstl" styleId="noOfInstl" value="${fetchFacilityDetailsList[0].noOfInstl}" styleClass="text" maxlength="3" readonly="true" tabindex="-1" style="text-align:right;"/>

											</td>	
								</tr>
								
								
						</table>
						
						<br/>
					 	<logic:equal name="functionId" value="3000297">
						<table>
							<tr>
								  <td><button type="button" class="topformbutton2" name="save"  id="save"  onclick="return fnUpdateFacilityLimitDetails();" title="Alt+V" accesskey="V" ><bean:message key="button.update" /></button></td>
								
							</tr>
						</table>
					</logic:equal>
					</logic:present>
					
				</fieldset>
		</div>
	</div>
</logic:present>


<br/>
	<fieldset>
		<legend>
			<bean:message key="lbl.facilityDetailsList" />
		</legend>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="gridtd">

					<table width="100%" border="0" cellspacing="1" cellpadding="1"
						id="gridTable">
						<tr class="white2">
							<td width="3%">Select</td>
							<td><b><bean:message key="lbl.product" /></b></td>
							<td><b><bean:message key="lbl.scheme" /></b></td>
							<td><b><bean:message key="lbl.loanAmount" /></b></td>
							<td><b><bean:message key="lbl.tenure" /></b></td>
							<td><b><bean:message key="lbl.finalRate" /></b></td>
							<td><b><bean:message key="lbl.emiAmount" /></b></td>
						</tr>
						<logic:present name="facilityDetailsList">
							<logic:iterate name="facilityDetailsList"
								id="facilityDetailsListData" indexId="counter">
								<tr class="white1">
									<td><input type="radio" name="chk"
										id="chk${facilityDetailsListData.dealLoanId}"
										value="${facilityDetailsListData.dealLoanId}" /></td>
									<td>${facilityDetailsListData.productDesc}</td>
									<td>${facilityDetailsListData.schemeDesc}</td>
									<td align="right">${facilityDetailsListData.loanAmount}</td>
									<td align="right">${facilityDetailsListData.txtTenure}</td>
									<td align="right">${facilityDetailsListData.roi}</td>
									<td align="right">${facilityDetailsListData.emiAmount}</td>
								</tr>
							</logic:iterate>
						</logic:present>
					</table>
				</td>
			</tr>
			<tr>
			    <td>
			    <!-- <button type="button" name="generateChargesNew" id="generateChargesNew" class="topformbutton4" onclick="return fnGetCharges();"  >Charge</button> -->
				<button type="button" name="generateInstallmentPlanNew" id="generateInstallmentPlanNew" class="topformbutton4" onclick="return fnGetInstallmentPlan();" ><bean:message key="button.newinstplan" /></button>
                <button type="button" name="generateRepaymentSchNew" id="generateRepaymentSchNew" title="Alt+N" accesskey="N" class="topformbutton4"  onclick="return fnGetRepaymentSchedule();" ><bean:message key="button.newrepay" /></button></td>
			</tr>
		</table>

	</fieldset>
</html:form>


	<script>
	setFramevalues("facilityDetailsForm");
</script>

</div>
</div>
</body>
</html:html>


<logic:present name="msg">
	<script type="text/javascript">
	if('<%=request.getAttribute("msg").toString()%>'=='S')
	{
		alert('<bean:message key="msg.DataSaved" />');
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='E')
	{
		alert('<bean:message key="msg.DataNotSaved" />');
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='D')
	{
		alert('<bean:message key="lbl.deleted" />');
	}
	else if('<%=request.getAttribute("msg").toString()%>' == 'N') {
			alert('<bean:message key="lbl.notDeleteFacility" />');
		}
	else if('<%=request.getAttribute("msg").toString()%>' == 'V') {
		alert('<bean:message key="lbl.sanctionedAmountValidationFailed" />');
	}
	</script>
</logic:present>
