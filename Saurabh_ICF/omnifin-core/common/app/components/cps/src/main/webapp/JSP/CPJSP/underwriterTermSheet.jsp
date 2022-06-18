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


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />

	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>	  	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/underWriter.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
	<script type="text/javascript" src=" <%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
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
<body oncontextmenu="return false;" onload="enableAnchor();calculateSdprct();checkChanges('termSheetForm');">

<div  align="center" class="opacity" style="display: none;"  id="processingImage">
</div>

		<div id="centercolumn">

			<div id="revisedcontainer">
<fieldset>
<logic:present name="loanHeader">
<logic:notPresent name="dealHeader">
<table cellspacing=0 cellpadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${loanHeader[0].dealLoanNo}</td>
           <td ><b><bean:message key="lbl.loanNo" /></b></td>
          <td >${loanHeader[0].loanNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${loanHeader[0].loanDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${loanHeader[0].loanCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${loanHeader[0].loanProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${loanHeader[0].loanProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${loanHeader[0].loanScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Loan Initiation</td>
          </tr>
        </table> 
</td>
</tr>
</table>
</logic:notPresent>
</logic:present>
<logic:present name="dealHeader">
<logic:notPresent name="loanHeader">
<table cellspacing=0 cellpadding=0 width="100%" border=0>
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
          <td >Under Writer</td>
        </tr>
        </table> 
</td>
</tr>
</table>
</logic:notPresent>
</logic:present>
</fieldset>
<html:form action="/underwritingTermSheetProcess" styleId="termSheetForm" method="post">
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>" />
<input type="hidden" name="expAmt" id="expAmt" value="${exposr[0].expAmt}"/>
<input type="hidden" name="amountFrom" id="amountFrom" value="${exposr[0].amountFrom}"/>
<input type="hidden" name="amountTo" id="amountTo" value="${exposr[0].amountTo}"/>
<input type="hidden" name="includeExposure" id="includeExposure" value="${exposr[0].includeExposure}"/>
<input type="hidden" name="exposureWithSd" id="exposureWithSd" value="${exposr[0].exposureWithSd}"/>

							
<fieldset>

		<legend>
			<bean:message key="lbl.decisionCom" />
		</legend>

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>

									<table width="100%" border="0" cellspacing="1" cellpadding="1">

										<tr>
											<td>
												<bean:message key="lbl.dateOfCC" />
											</td>
											<td>
												<html:text property="dateOfCC" styleClass="text" styleId="dateOfCC" readonly="true" value="${termsheet[0].dateOfCC}"  tabindex="-1"/>
											</td>
									
											<td>
												<bean:message key="lbl.nameOfBranch" />
											</td>
											<td>
												<html:text property="nameOfBranch" styleClass="text" styleId="nameOfBranch" readonly="true" value="${termsheet[0].nameOfBranch}"  tabindex="-1"/>
											</td>
											
										</tr>

										<tr>
											<td>
												<bean:message key="lbl.grossAmountProduct" />
											</td>
											<td>
												<html:text property="grossAmountProduct" styleClass="text" styleId="grossAmountProduct" readonly="true" style="text-align: right" value="${termsheet[0].grossAmountProduct}"  tabindex="-1"/>
											</td>
									
											<td>
												<bean:message key="lbl.grossAmountLoan" />
												<html:hidden property="defaultLoanAmount" styleId="defaultLoanAmount" value="${termsheet[0].defaultLoanAmount}" />
											</td>
											<logic:notPresent name="strFlagDV">
											<logic:notPresent name="approvalLevel">
												<td>
													<html:text property="grossAmountLoan" styleClass="text" styleId="grossAmountLoan" maxlength="22" style="text-align: right" value="${termsheet[0].grossAmountLoan}" onchange="calculateSdprct()" tabindex="-1"/>
												</td>
											</logic:notPresent>
											</logic:notPresent>
											<logic:present name="strFlagDV">
											<td>
												<html:text property="grossAmountLoan" styleClass="text" styleId="grossAmountLoan" style="text-align: right" value="${termsheet[0].grossAmountLoan}"  readonly="true" tabindex="-1"/>
											</td>
											</logic:present>
											<logic:notPresent name="strFlagDV">
											<logic:present name="approvalLevel">
											<td>
												<html:text property="grossAmountLoan" styleClass="text" styleId="grossAmountLoan" style="text-align: right" value="${termsheet[0].grossAmountLoan}"  readonly="true" tabindex="-1"/>
											</td>
											</logic:present>
											</logic:notPresent>
										</tr>
										<tr>
											<td><bean:message key="lbl.scheme" /></td>
											<td><html:text property="dealScheme" styleClass="text" styleId="dealScheme" readonly="true" value="${termsheet[0].dealScheme}"  tabindex="-1"/></td>
											<td><bean:message key="lbl.dealCat" /></td>
											<td><html:text property="dealCategory" styleClass="text" styleId="dealCategory" readonly="true" value="${termsheet[0].dealCategory}"  tabindex="-1"/></td>
										</tr>		
										<tr>
											<td><bean:message key="lbl.typeOfIndustry" /></td>
											<td><html:text property="typeOfIndustry" styleClass="text" styleId="typeOfIndustry" readonly="true" value="${termsheet[0].typeOfIndustry}"  tabindex="-1"/></td>
											<td><bean:message key="subIndustry" /></td>
											<td><html:text property="subIndustry" styleClass="text" styleId="subIndustry" readonly="true" value="${termsheet[0].subIndustry}"  tabindex="-1"/></td>
										 </tr>
										 <tr>									
											<td><bean:message key="lbl.creditCommittee" /></td>
											<logic:notPresent name="strFlagDV">
											<logic:notPresent name="approvalLevel">
											<td>
												<html:select property="creditcommittee" styleId="creditcommittee" value="${termsheet[0].creditcommittee}"  styleClass="text">
													<option value="">--<bean:message key="lbl.select" />--</option>
													<logic:present name="creditcommitteeList">
													<logic:notEmpty name="creditcommitteeList">
														<html:optionsCollection name="creditcommitteeList" label="value" value="description" />
													</logic:notEmpty>
													</logic:present>
												</html:select>									
											</td>
											</logic:notPresent>
											</logic:notPresent>
											<logic:present name="strFlagDV">
											<td>
												<html:select property="creditcommittee" styleId="creditcommittee" value="${termsheet[0].creditcommittee}"  styleClass="text" disabled="true">
													<option value="">--<bean:message key="lbl.select" />--</option>
													<logic:present name="creditcommitteeList">
													<logic:notEmpty name="creditcommitteeList">
														<html:optionsCollection name="creditcommitteeList" label="value" value="description" />
													</logic:notEmpty>
													</logic:present>
												</html:select>	
											</td>
											</logic:present>
											<logic:notPresent name="strFlagDV">
											<logic:present name="approvalLevel">
											<td>
												<html:select property="creditcommittee" styleId="creditcommittee" value="${termsheet[0].creditcommittee}"  styleClass="text" disabled="true" >
													<option value="">--<bean:message key="lbl.select" />--</option>
													<logic:present name="creditcommitteeList">
													<logic:notEmpty name="creditcommitteeList">
														<html:optionsCollection name="creditcommitteeList" label="value" value="description" />
													</logic:notEmpty>
													</logic:present>
												</html:select>	
											</td>
											</logic:present>
											</logic:notPresent>
											
											<td><bean:message key="lbl.pslOption" /></td>
											<!-- Nishant space starts -->
											<logic:notPresent name="strFlagDV">
											<logic:notPresent name="approvalLevel">
											<td>
												<html:select property="pslOption" styleId="pslOption" value="${termsheet[0].pslOption}" styleClass="text">
													 <html:option value="">-----Select-----</html:option>
												     <html:option value="Y">Yes</html:option>
												     <html:option value="N">No</html:option>
												</html:select>
											</td>
											</logic:notPresent>
											</logic:notPresent>
											<logic:present name="strFlagDV">
											<td>
												<html:select property="pslOption" styleId="pslOption" value="${termsheet[0].pslOption}" disabled="true"  styleClass="text">
													 <html:option value="">-----Select-----</html:option>
												     <html:option value="Y">Yes</html:option>
												     <html:option value="N">No</html:option>
												</html:select>
											</td>
											</logic:present>
											<logic:notPresent name="strFlagDV">
											<logic:present name="approvalLevel">
											<td>
												<html:select property="pslOption" styleId="pslOption" value="${termsheet[0].pslOption}" disabled="true" styleClass="text">
													 <html:option value="">-----Select-----</html:option>
												     <html:option value="Y">Yes</html:option>
												     <html:option value="N">No</html:option>
												</html:select>
											</td>
											</logic:present>
											</logic:notPresent>
										<!-- Nishant space end -->
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.roi" />
											</td>
											<logic:notPresent name="strFlagDV">
											<logic:notPresent name="approvalLevel">
											<td>
												<html:text property="roi" styleClass="text" styleId="roi" style="text-align: right" onfocus="keyUpNumber(this.value, event,3,id);" onblur="formatNumber(this.value,id);checkRate('roi');" onkeypress="return numbersonly(event,id,3);" onkeyup="checkNumber(this.value, event,3,id);" value="${termsheet[0].roi}" maxlength="10" tabindex="-1"/>
											</td>
											</logic:notPresent>
											</logic:notPresent>
											<logic:present name="strFlagDV">
											<td>
												<html:text property="roi" styleClass="text" styleId="roi" value="${termsheet[0].roi}"  readonly="true" tabindex="-1"/>
											</td>
											</logic:present>
											<logic:notPresent name="strFlagDV">
											<logic:present name="approvalLevel">
											<td>
												<html:text property="roi" styleClass="text" styleId="roi" value="${termsheet[0].roi}"  readonly="true" tabindex="-1"/>
											</td>
											</logic:present>
											</logic:notPresent>
									
											<td>
												<bean:message key="lbl.managementFee" />
											</td>
											<logic:notPresent name="strFlagDV">
											<logic:notPresent name="approvalLevel">
											<td>
												<html:text property="managementFee" styleClass="text" styleId="managementFee"  value="${termsheet[0].managementFee}" style="text-align: right"  onfocus="keyUpNumber(this.value, event,3,id);" onblur="formatNumber(this.value,id);checkRate('managementFee');" onkeypress="return numbersonly(event,id,3);" onkeyup="checkNumber(this.value, event,3,id);"  tabindex="-1"/>
											</td>
											</logic:notPresent>
											</logic:notPresent>
											<logic:present name="strFlagDV">
											<td>
												<html:text property="managementFee" styleClass="text" styleId="managementFee"  readonly="true" value="${termsheet[0].managementFee}"  tabindex="-1"/>
											</td>
											</logic:present>
											<logic:notPresent name="strFlagDV">
											<logic:present name="approvalLevel">
											<td>
												<html:text property="managementFee" styleClass="text" styleId="managementFee"  readonly="true" value="${termsheet[0].managementFee}"  tabindex="-1"/>
											</td>
											</logic:present>
											</logic:notPresent>
											
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.tenure" />
											</td>
											<logic:notPresent name="strFlagDV">
											<logic:notPresent name="approvalLevel">
											<td>
												<html:text property="tenure" styleClass="text" styleId="tenure"  onkeypress="return isNumberKey(event);" value="${termsheet[0].tenure}" maxlength="5" tabindex="-1"/>
											</td>
											</logic:notPresent>
											</logic:notPresent>
											<logic:present name="strFlagDV">
											<td>
												<html:text property="tenure" styleClass="text" styleId="tenure"  readonly="true" value="${termsheet[0].tenure}" tabindex="-1"/>
											</td>
											</logic:present>
											<logic:notPresent name="strFlagDV">
											<logic:present name="approvalLevel">
											<td>
												<html:text property="tenure" styleClass="text" styleId="tenure"  readonly="true" value="${termsheet[0].tenure}" tabindex="-1"/>
											</td>
											</logic:present>
											</logic:notPresent>
											
											<td><bean:message key="lbl.rocApp" /></td>
											<logic:notPresent name="strFlagDV">
											<logic:notPresent name="approvalLevel">
											<td>
												<html:select property="rocApplicable" styleId="rocApplicable" value="${termsheet[0].rocApplicable}" styleClass="text">
													 <html:option value="">-----Select-----</html:option>
												     <html:option value="Y">Yes</html:option>
												     <html:option value="N">No</html:option>
												</html:select>
											</td>
											</logic:notPresent>
											</logic:notPresent>
											<logic:present name="strFlagDV">
											<td>
												<html:select property="rocApplicable" styleId="rocApplicable" value="${termsheet[0].rocApplicable}" disabled="true"  styleClass="text">
													 <html:option value="">-----Select-----</html:option>
												     <html:option value="Y">Yes</html:option>
												     <html:option value="N">No</html:option>
												</html:select>
											</td>
											</logic:present>
											<logic:notPresent name="strFlagDV">
											<logic:present name="approvalLevel">
											<td>
												<html:select property="rocApplicable" styleId="rocApplicable" value="${termsheet[0].rocApplicable}" disabled="true" styleClass="text">
													 <html:option value="">-----Select-----</html:option>
												     <html:option value="Y">Yes</html:option>
												     <html:option value="N">No</html:option>
												</html:select>
											</td>
											</logic:present>
											</logic:notPresent>
										</tr>
										<tr>
											<td><bean:message key="lbl.sdAmount" /></td>
											<logic:notPresent name="strFlagDV">
											<logic:notPresent name="approvalLevel">
											<td>
												<html:text property="sdAmount" styleClass="text" styleId="sdAmount"  value="${termsheet[0].sdAmount}"  style="text-align: right"  onfocus="keyUpNumber(this.value, event,3,id);" onblur="formatNumber(this.value,id);calculateSdprct();" onkeypress="return numbersonly(event,id,3);" onkeyup="checkNumber(this.value, event,3,id);" tabindex="-1"/>
											</td>
											</logic:notPresent>
											</logic:notPresent>
											<logic:present name="strFlagDV">
											<td>
												<html:text property="sdAmount" styleClass="text" styleId="sdAmount" readonly="true" value="${termsheet[0].sdAmount}"  style="text-align: right" tabindex="-1"/>
											</td>
											</logic:present>
											<logic:notPresent name="strFlagDV">
											<logic:present name="approvalLevel">
											<td>
												<html:text property="sdAmount" styleClass="text" styleId="sdAmount" readonly="true" value="${termsheet[0].sdAmount}"  style="text-align: right" tabindex="-1"/>
											</td>
											</logic:present>
											</logic:notPresent>
											<td><bean:message key="lbl.calculatedSD" /></td>
											<td><html:text property="calculatedSD" styleClass="text" styleId="calculatedSD" readonly="true" value="${termsheet[0].calculatedSD}"  style="text-align: right" tabindex="-1"/></td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.guaranteesPrsnl" />
											</td>
											<logic:notPresent name="strFlagDV">
											<logic:notPresent name="approvalLevel">
											<td>
												<html:textarea property="guaranteesPrsnl" styleClass="text" styleId="guaranteesPrsnl" onkeyup="return imposeMaxLength(this, 5000);"   value="${termsheet[0].guaranteesPrsnl}"  tabindex="-1"/>
											</td>
											</logic:notPresent>
											</logic:notPresent>
											<logic:present name="strFlagDV">
											<td>
												<html:textarea property="guaranteesPrsnl" styleClass="text" styleId="guaranteesPrsnl"  readonly="true" value="${termsheet[0].guaranteesPrsnl}"  tabindex="-1"/>
											</td>
											</logic:present>
											<logic:notPresent name="strFlagDV">
											<logic:present name="approvalLevel">
											<td>
												<html:textarea property="guaranteesPrsnl" styleClass="text" styleId="guaranteesPrsnl"  readonly="true" value="${termsheet[0].guaranteesPrsnl}"  tabindex="-1"/>
											</td>
											</logic:present>
											</logic:notPresent>
											<td>
												<bean:message key="lbl.guaranteesCorp" />
											</td>
											<logic:notPresent name="strFlagDV">
											<logic:notPresent name="approvalLevel">
											<td>
												<html:textarea property="guaranteesCorp" styleClass="text" styleId="guaranteesCorp" onkeyup="return imposeMaxLength(this, 5000);"   value="${termsheet[0].guaranteesCorp}"  tabindex="-1"/>
											</td>
											</logic:notPresent>
											</logic:notPresent>
											<logic:present name="strFlagDV">
											<td>
												<html:textarea property="guaranteesCorp" styleClass="text" styleId="guaranteesCorp" readonly="true" value="${termsheet[0].guaranteesCorp}"  tabindex="-1"/>
											</td>
											</logic:present>
											<logic:notPresent name="strFlagDV">
											<logic:present name="approvalLevel">
											<td>
												<html:textarea property="guaranteesCorp" styleClass="text" styleId="guaranteesCorp" readonly="true" value="${termsheet[0].guaranteesCorp}"  tabindex="-1"/>
											</td>
											</logic:present>
											</logic:notPresent>
											
										</tr>
										
										<tr>
<!--											<td>-->
<!--												<bean:message key="lbl.rocCharges" />-->
<!--											</td>-->
<!--											<logic:notPresent name="strFlagDV">-->
<!--											<logic:notPresent name="approvalLevel">-->
<!--											<td>-->
<!--												<html:textarea property="rocCharges" styleClass="text" styleId="rocCharges" onkeyup="return imposeMaxLength(this, 5000);"  value="${termsheet[0].rocCharges}"  tabindex="-1"/>-->
<!--											</td>-->
<!--											</logic:notPresent>-->
<!--											</logic:notPresent>-->
<!--											<logic:present name="strFlagDV">-->
<!--											<td>-->
<!--												<html:textarea property="rocCharges" styleClass="text" styleId="rocCharges"  readonly="true" value="${termsheet[0].rocCharges}"  tabindex="-1"/>-->
<!--											</td>-->
<!--											</logic:present>-->
<!--											<logic:notPresent name="strFlagDV">-->
<!--											<logic:present name="approvalLevel">-->
<!--											<td>-->
<!--												<html:textarea property="rocCharges" styleClass="text" styleId="rocCharges"  readonly="true" value="${termsheet[0].rocCharges}"  tabindex="-1"/>-->
<!--											</td>-->
<!--											</logic:present>-->
<!--											</logic:notPresent>-->
												
											<td>
												<bean:message key="lbl.hypothecation" />
											</td>
											<logic:notPresent name="strFlagDV">
											<logic:notPresent name="approvalLevel">
											<td>
												<html:textarea property="hypothecation" styleClass="text" styleId="hypothecation"  onkeyup="return imposeMaxLength(this, 5000);"   value="${termsheet[0].hypothecation}"  tabindex="-1"/>
											</td>
											</logic:notPresent>
											</logic:notPresent>
											<logic:present name="strFlagDV">
											<td>
												<html:textarea property="hypothecation" styleClass="text" styleId="hypothecation" readonly="true" value="${termsheet[0].hypothecation}"  tabindex="-1"/>
											</td>
											</logic:present>
											<logic:notPresent name="strFlagDV">
											<logic:present name="approvalLevel">
											<td>
												<html:textarea property="hypothecation" styleClass="text" styleId="hypothecation" readonly="true" value="${termsheet[0].hypothecation}"  tabindex="-1"/>
											</td>
											</logic:present>
											</logic:notPresent>
											<td>
												<bean:message key="lbl.additionalCommitee" />
											</td>
											<logic:notPresent name="strFlagDV">
											<logic:notPresent name="approvalLevel">
											<td>
												<html:textarea property="additionalCommitee" styleClass="text" styleId="additionalCommitee" onkeyup="return imposeMaxLength(this, 5000);"  value="${termsheet[0].additionalCommitee}"  tabindex="-1"/>
											</td>
											</logic:notPresent>
											</logic:notPresent>
											<logic:present name="strFlagDV">
											<td>
												<html:textarea property="additionalCommitee" styleClass="text" styleId="additionalCommitee" readonly="true" value="${termsheet[0].additionalCommitee}"  tabindex="-1"/>
											</td>
											</logic:present>
											<logic:notPresent name="strFlagDV">
											<logic:present name="approvalLevel">
											<td>
												<html:textarea property="additionalCommitee" styleClass="text" styleId="additionalCommitee" readonly="true" value="${termsheet[0].additionalCommitee}"  tabindex="-1"/>
											</td>
											</logic:present>
											</logic:notPresent>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.insurance" />
											</td>
											<logic:notPresent name="strFlagDV">
											<logic:notPresent name="approvalLevel">
											<td>
												<html:textarea property="insurance" styleClass="text" styleId="insurance" onkeyup="return imposeMaxLength(this, 5000);"  value="${termsheet[0].insurance}"  tabindex="-1"/>
											</td>
											</logic:notPresent>
											</logic:notPresent>
											<logic:present name="strFlagDV">
											<td>
												<html:textarea property="insurance" styleClass="text" styleId="insurance" readonly="true" value="${termsheet[0].insurance}"  tabindex="-1"/>
											</td>
											</logic:present>
											<logic:notPresent name="strFlagDV">
											<logic:present name="approvalLevel">
											<td>
												<html:textarea property="insurance" styleClass="text" styleId="insurance" readonly="true" value="${termsheet[0].insurance}"  tabindex="-1"/>
											</td>
											</logic:present>
											</logic:notPresent>
											<td>
												<bean:message key="lbl.otherCondition" />
											</td>
											<logic:notPresent name="strFlagDV">
											<logic:notPresent name="approvalLevel">
											<td>
												<html:textarea property="otherCondition" style="height:150px;width:200px;"  styleClass="text" styleId="otherCondition" onkeyup="return imposeMaxLength(this, 5000);"  value="${termsheet[0].otherCondition}"  tabindex="-1"/>
											</td>
											</logic:notPresent>
											</logic:notPresent>
											<logic:present name="strFlagDV">
											<td>
												<html:textarea property="otherCondition" styleClass="text" style="height:150px;width:200px;"  styleId="otherCondition"  readonly="true" value="${termsheet[0].otherCondition}"  tabindex="-1"/>
											</td>
											</logic:present>
											<logic:notPresent name="strFlagDV">
											<logic:present name="approvalLevel">
											<td>
												<html:textarea property="otherCondition" style="height:150px;width:200px;"  styleClass="text" styleId="otherCondition"  readonly="true" value="${termsheet[0].otherCondition}"  tabindex="-1"/>
											</td>
											</logic:present>
											</logic:notPresent>
											
											
										</tr>
										<tr>
											<td align="left" class="form2" colspan="4">
												<logic:notPresent name="strFlagDV">
												<logic:notPresent name="approvalLevel">
														<button type="button" name="saveGroup" id="saveGroup" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveTermSheet();"><bean:message key="button.save" /></button>
												</logic:notPresent>
												</logic:notPresent>
												<button type="button" name="mybutton" id="mybutton" class="topformbutton3" title="Alt+G" accesskey="G" onclick="return generateTermSheet();"><bean:message key="button.generate" /></button>
											</td>
										</tr>
									</table>

								</td>
							</tr>

						</table>

					</fieldset>
					
	  <fieldset>	
		<legend><bean:message key="lbl.machinesPurchased"/></legend>  

        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
    				<tr class="white2">
      					<td ><b><bean:message key="lbl.assetsType"/></b></td>
				        <td ><b><bean:message key="lbl.assetsDetail"/></b></td>
				        <td ><b><bean:message key="lbl.supplier"/></b></td>
				        <td ><b><bean:message key="lbl.amount"/></b></td>
        			</tr>
    				<logic:present name="machineDetails">
    				
		 			<logic:iterate name="machineDetails" id="subMachineDetails">
		 			
		 			<tr class="white1">						
			     			<td>${subMachineDetails.assetsType}</td>
				 			<td>${subMachineDetails.assetsDetails}</td>
				 			<td>${subMachineDetails.assetsSupplier}</td>
				 			<td>${subMachineDetails.assetsAmount}</td>
				 	</tr>	
				 			
				 		
			 		</logic:iterate>
	   				</logic:present>
 				</table>
			</td>
		</tr>
		</table>

	</fieldset>
	
	 <fieldset>	
		<legend><bean:message key="lbl.approvalCommitee"/></legend>  

        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
    				<tr class="white2">
      					<td ><b><bean:message key="lbl.userNam"/></b></td>
				        <td><b><bean:message key="lbl.level"/></b></td>
        			</tr>
    				<logic:present name="approvalCommitteeList">
    				
		 			<logic:iterate name="approvalCommitteeList" id="subApprovalCommitteeList">
		 			
		 			<tr class="white1">						
			     			<td>${subApprovalCommitteeList.userName}</td>
				 			<td>${subApprovalCommitteeList.level}</td>
				 	</tr>	
				 			
				 		
			 		</logic:iterate>
	   				</logic:present>
 				</table>
			</td>
		</tr>
		</table>

	</fieldset>

</html:form>
</div>
</div>
	
<logic:present name="sms">
		
	<script type="text/javascript">
		if('<%=request.getAttribute("sms")%>'=='S')
		{
			alert("<bean:message key="msg.manualAdviceMakerSaved" />");
		    
			
		}
		else if('<%=request.getAttribute("sms")%>'=='X')
		{
			alert("<bean:message key="msg.manualAdviceMakerUnsuccessful" />");
			
		}
		
	</script>
	
</logic:present>

	<script type="text/javascript">
		setFramevalues('termSheetForm');
	</script>
</body>
</html:html>