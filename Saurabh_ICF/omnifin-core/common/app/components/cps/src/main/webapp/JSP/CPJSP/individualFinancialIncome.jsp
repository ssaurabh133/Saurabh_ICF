<!--
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 10 jan 2012
 	Purpose          :- To provide user interface to Individual Financial Income
 	Updated by        :-Arun Kumar Mishra
 	Update             :-19 oct 2102
 -->

 <%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
            <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		 	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/individualFinancialAnalysis.js"></script> 
		    <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
  		    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
  		    <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
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
	<body oncontextmenu="return false" onunload="closeAllLovCallUnloadBody();" onload="enableAnchor();checkChanges('individualFinancialIncomeForm');" >
	<div id="centercolumn">
	<div id="revisedcontainer">
	
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
	          <td >Individual Financial Analysis</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>
	
<logic:notPresent name="underWriterViewData">
	<html:form action="/individualFinancialIncomeBehindAction" styleId="individualFinancialIncomeForm" method="post">
	  <input type="hidden" id="contextPath" value="<%=path %>" />
	  <input type="hidden" id="lbxDealNo" name="lbxDealNo" value="${sessionScope.financialDealId}"/>
	   <input type="hidden" id="reloadFlag" value="${reloadFlag}" />
	  <logic:present name="editIncomeDetails">
	   <input type="hidden" id="insertUpdateFlag" name="insertUpdateFlag" value="M"/>
	  </logic:present>
	  <logic:notPresent name="editIncomeDetails">
	  <input type="hidden" id="insertUpdateFlag" name="insertUpdateFlag" value="I"/>
	 </logic:notPresent >
	   <fieldset>	
	     
	 <legend><bean:message key="lbl.income"/></legend>
	 	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
		<td>
		<logic:notPresent name="editIncomeDetails">
		<table width="100%" border="0" cellspacing="1" cellpadding="1">

		<%--< tr>
		<td colspan="2"><bean:message key="lbl.customerType"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">
		<html:select property="customerType" styleId="customerType" value="PRAPPL" styleClass="text" onchange="getCustomerName();">
		<html:optionsCollection name="customerTypeList" value="parameCode" label="paramName"/>
		</html:select>

     </td>
	<td width="17%"><bean:message key="lbl.customerName"/><font color="red">*</font></td>
	<td width="28%">
	<div id="customerNameDiv">
	<html:select property="customerName" styleId="customerName" value="" styleClass="text">
	<html:optionsCollection name="customerNameList" value="parameCode" label="paramName"/>
	</html:select>
	</div>
	</td>
		</tr> --%>
		<!-- Changes By Sarvesh -->
		<tr>
		<td colspan="2"><bean:message key="lbl.customerType"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">

		<html:text property="customerType" styleId="customerType" styleClass="text" maxlength="50" value="${customerType}" readonly="true" tabindex="-1" />
  				<input type="button" class="lovbutton" id="customerTypeButton"
								onClick="openLOVCommon(6092,'individualFinancialIncomeForm','customerName','','', '','','','customerType','lbxCustomerRoleType');changeReloadFlag();"
								value=" " name="dealButton"/>
							<input type="hidden" name="lbxCustomerId" id="lbxCustomerId" value="${lbxCustomerId}"/>
							<input type="hidden" name="lbxCustomerRoleType" id="lbxCustomerRoleType" value="${lbxCustomerRoleType}" />
								
     </td>
	<td width="17%"><bean:message key="lbl.customerName"/><font color="red">*</font></td>
	<td width="28%">
	<html:text  styleClass="text" property="customerName" styleId="customerName" value="${customerName}" maxlength="50" readonly="true" tabindex="-1"/>
    </td>
		</tr>
		
		<tr>
		<td colspan="2"><bean:message key="lbl.monthlyIncome"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">
		
		<html:text property="monthlyIncome" styleId="monthlyIncome" styleClass="text" style="text-align: right;" value=""
		 onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);"  onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event,18,id);"/>
  
     </td>
	<td width="17%"><bean:message key="lbl.annuallyIncome"/><font color="red">*</font></td>
	<td width="28%">
	<html:text  styleClass="text" property="annuallyIncome" styleId="annuallyIncome" value="" style="text-align: right;"
	 onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);"  onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event,18,id);"/>
    </td>
		</tr>
		
		<tr>
		<td colspan="2"><bean:message key="lbl.varificationmethod"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">
		 <html:select property="varificationMethod" styleId="varificationMethod" styleClass="text" value="">
	    <html:option value="">Select</html:option>
	    <html:optionsCollection name="verifMethodList" label="name" value="id"/>
	    </html:select>
     </td>
	<td width="17%"><bean:message key="lbl.sourceType"/></td>
	<td width="28%">
		<html:select property="sourceType" styleId="sourceType"  styleClass="text" value="SALARY">
	<html:optionsCollection name="sourceTypeList" value="parameCode" label="paramName"/>
	</html:select>
	
    </td>
		</tr>
		
	  <tr>
	  
	  <td colspan="2"><bean:message key="lbl.remarks"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">
		
		<html:textarea property="remarks" styleId="remarks"  value="" styleClass="text"/>
  
     </td>
	<td width="17%"><bean:message key="lbl.includeInRatio"/></td>
	<td width="28%">
		<input type="checkbox" name="includeInRatio" id="includeInRatio"  class="text"/>
	
    </td>

	  </tr>
	  	  </table>
</logic:notPresent>
<logic:present name="editIncomeDetails">
		<table width="100%" border="0" cellspacing="1" cellpadding="1">

		<%-- <tr>
		<td colspan="2"><bean:message key="lbl.customerType"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">
		<html:select property="customerType" styleId="customerType" value="${editIncomeDetails[0].customerType}" onchange="getCustomerName();" styleClass="text">
		<html:optionsCollection name="customerTypeList" value="parameCode" label="paramName"/>
		</html:select>
	<input type="hidden" name="financialId" id="financialId" value="${editIncomeDetails[0].financialId}"/>
     </td>
	<td width="17%"><bean:message key="lbl.customerName"/><font color="red">*</font></td>
	<td width="28%">
	<div id="customerNameDiv">
	<html:select property="customerName" styleId="customerName" value="${editIncomeDetails[0].customerName}" styleClass="text">
	<html:optionsCollection name="customerNameList" value="parameCode" label="paramName"/>
	</html:select>
	</div>
	</td>
		</tr> --%>
		<!-- Changes By Sarvesh -->
		<tr>
		<td colspan="2"><bean:message key="lbl.customerType"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">

		<html:text property="customerType" styleId="customerType" styleClass="text" maxlength="50" value="${editIncomeDetails[0].customerType}" readonly="true" tabindex="-1"/>
  				<input type="button" class="lovbutton" id="customerTypeButton"
								onClick="openLOVCommon(6092,'individualFinancialIncomeForm','customerName','','', '','','','customerType','lbxCustomerRoleType');changeReloadFlag();"
								value=" " name="dealButton"/>
							<input type="hidden" name="lbxCustomerId" id="lbxCustomerId" value="${editIncomeDetails[0].customerId}"/>
							<input type="hidden" name="lbxCustomerRoleType" id="lbxCustomerRoleType" value="${editIncomeDetails[0].lbxCustomerRoleType}"/>
								
     </td>
	<td width="17%"><bean:message key="lbl.customerName"/></td>
	<td width="28%">
	<html:text  styleClass="text" property="customerName" styleId="customerName" value="${editIncomeDetails[0].customerName}" maxlength="50" readonly="true" tabindex="-1"/>
    </td>
		</tr>
		
		<tr>
		<td colspan="2"><bean:message key="lbl.monthlyIncome"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">
		
		<html:text property="monthlyIncome" styleId="monthlyIncome" styleClass="text" style="text-align: right;" value="${editIncomeDetails[0].monthlyIncome}" 
		 onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);"  onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event,18,id);"/>
  
     </td>
	<td width="17%"><bean:message key="lbl.annuallyIncome"/><font color="red">*</font></td>
	<td width="28%">
	<html:text  styleClass="text" property="annuallyIncome" styleId="annuallyIncome" value="" style="text-align: right;" value="${editIncomeDetails[0].annuallyIncome}" 
	 onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);"  onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event,18,id);"/>
    </td>
		</tr>
		
		<tr>
		<td colspan="2"><bean:message key="lbl.varificationmethod"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">
		 <html:select property="varificationMethod" styleId="varificationMethod" styleClass="text" value="${editIncomeDetails[0].varificationMethod}" >
	    <html:option value="">Select</html:option>
	    <html:optionsCollection name="verifMethodList" label="name" value="id"/>
	    </html:select>
     </td>
	<td width="17%"><bean:message key="lbl.sourceType"/></td>
	<td width="28%">
		<html:select property="sourceType" styleId="sourceType"  styleClass="text" value="${editIncomeDetails[0].sourceType}">
	<html:optionsCollection name="sourceTypeList" value="parameCode" label="paramName"/>
	</html:select>
	
    </td>
		</tr>
		
	  <tr>
	  
	  <td colspan="2"><bean:message key="lbl.remarks"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">
		
		<html:textarea property="remarks" styleId="remarks" styleClass="text" value="${editIncomeDetails[0].remarks}"/>
  
     </td>
	<td width="17%"><bean:message key="lbl.includeInRatio"/></td>
	<td width="28%">
	<logic:present name="action">
	<logic:equal name="action" value="Y">
		<input type="checkbox" name="includeInRatio" id="includeInRatio" checked="true"   class="text"/>
	</logic:equal>
	<logic:equal name="action" value="N">
		<input type="checkbox" name="includeInRatio" id="includeInRatio"  class="text"/>
	</logic:equal>
	</logic:present>
    </td>

	  </tr>
	  	  </table>
</logic:present>
	 </td></tr>
  <tr>
			<td nowrap colspan="10" class="white">
			<logic:present name="financialDealId">
			    <input type="hidden" id="dealId" value="${sessionScope.financialDealId}"/>
		    </logic:present>
			 <button type="button" name="Reload"  title="Alt+V" accesskey="V" class="topformbutton2" id="Reload" onclick="reloadIndividualFinancialIncome();" ><bean:message key="button.reload" /></button>	
			 <button type="button" name="Save"  title="Alt+V" accesskey="V" class="topformbutton2" id="Save" onclick="saveIndividualFinancialIncome();" ><bean:message key="button.save" /></button>	
		    <button type="button" name="button"  id="Forward" title="Alt+F" accesskey="F" class="topformbutton3" onclick="return individualFinancialAnalysisForward('<bean:message key="msg.confirmationForwardMsg" />');"><bean:message key="button.forward" /></button>
			 <!--<input type="button" value="Clear" class="topformbutton2" onclick="financialClear();" accesskey="L" title="Alt+L"/>-->
			</td>
			</tr>
		</table>
	</fieldset>	
	<br/>
	  <fieldset>	
		 <legend><bean:message key="lbl.incomeParamDetails"/></legend>  
  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
   
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        <td> <b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/><bean:message key="contact.select" /></b></td>
        <td><center><b><bean:message key="lbl.customerName"/></b></center></td>
        <td><center><b><bean:message key="lbl.customerType"/></b></center></td>
        <td><center><b><bean:message key="lbl.monthlyIncome"/></b></center></td>
        <td><center><b><bean:message key="lbl.annuallyIncome"/></b></center></td>
        <td><center><b><bean:message key="lbl.varificationmethod"/></b></center></td>
        <td><center><b><bean:message key="lbl.sourceType"/></b></center></td>
	</tr>
	<tr>
		
	
		<logic:notEmpty name="incomeDetailList">
		<logic:iterate name="incomeDetailList" id="objincomeDetailList" indexId="count">
		<tr class="white1">
		<td >
		<input type="checkbox" name="chk" id="chk" value="${objincomeDetailList.financialId }"/>
    	 </td>
		<td ><a href="#" id="anchor0" onclick="return editIncomesDetails(${objincomeDetailList.financialId});">${objincomeDetailList.customerName}</a></td>
		<td >${objincomeDetailList.customerType }</td>
		<td>${objincomeDetailList.monthlyIncome }</td>
		<td>${objincomeDetailList.annuallyIncome }</td>
		<td>${objincomeDetailList.varificationMethod}</td>
		<td>${objincomeDetailList.sourceType}</td>
		</tr>	
	 
	   	</logic:iterate>
		</logic:notEmpty>
		</tr>
	
 </table>    </td>
</tr>
<logic:present name="incomeDetailList">
   <tr><td >
  	 <!-- <button type="button" name="button1" class="topformbutton2" title="Alt+D" accesskey="D" onclick="deleteIncomeDetail();"><bean:message key="button.delete" /></button>
	   -->     </td></tr>
	 </logic:present>
</table>

	</fieldset>
</html:form>
</logic:notPresent>
<%--   -------------- -------------------- ---------View  mode--- -------------------- -------------------- --------------------- --%>
<logic:present name="underWriterViewData">
	<br /><html:form action="/individualFinancialIncomeBehindAction" styleId="individualFinancialIncomeForm" method="post">
	  <input type="hidden" id="contextPath" value="<%=path %>" />
	   <input type="hidden" id="reloadFlag" value="${reloadFlag}" />
	   <input type="hidden" id="lbxDealNo" name="lbxDealNo" value="${sessionScope.financialDealId}"/>
	   <fieldset>	  
	<legend></legend>
	 	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
   
  <%--  <tr>
		<td colspan="2"><bean:message key="lbl.customerType"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">
		<html:select property="customerType" styleId="customerType" value="${editIncomeDetails[0].customerType}" onchange="getCustomerName();" disabled="true" styleClass="text">
		<html:optionsCollection name="customerTypeList" value="parameCode" label="paramName"/>
		</html:select>
	<input type="hidden" name="financialId" id="financialId" value="${editIncomeDetails[0].financialId}"/>
     </td>
	<td width="17%"><bean:message key="lbl.customerName"/></td>
	<td width="28%">
	<div id="customerNameDiv">
	<html:select property="customerName" styleId="customerName" value="${editIncomeDetails[0].customerName}" styleClass="text" disabled="true">
	<html:optionsCollection name="customerNameList" value="parameCode" label="paramName"/>
	</html:select>
	</div>
	</td>
		</tr> --%>
		<!-- Changes By Sarvesh -->
		<tr>
		<td colspan="2"><bean:message key="lbl.customerType"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">

		<html:text property="customerType" styleId="customerType" styleClass="text" maxlength="50" value="${editIncomeDetails[0].customerType}" readonly="true" tabindex="-1"/>
  				<input type="button" class="lovbutton" id="customerTypeButton"
								onClick="openLOVCommon(6092,'financialBalacnceSheetForm','customerName','','', '','','','customerType','lbxCustomerRoleType');changeReloadFlag();"
								value=" " name="dealButton"/>
								<input type="hidden" name="lbxCustomerId" id="lbxCustomerId" value="${editIncomeDetails[0].customerId}"/>
							<input type="hidden" name="lbxCustomerRoleType" id="lbxCustomerRoleType" value="${editIncomeDetails[0].lbxCustomerRoleType}"/>
								
								
     </td>
	<td width="17%"><bean:message key="lbl.customerName"/></td>
	<td width="28%">
	<html:text  styleClass="text" property="customerName" styleId="customerName" value="${editIncomeDetails[0].customerName}" maxlength="50" readonly="true" tabindex="-1"/>
    </td>
		</tr>
		
		
		<tr>
		<td colspan="2"><bean:message key="lbl.monthlyIncome"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">
		
		<html:text property="monthlyIncome" styleId="monthlyIncome" styleClass="text" style="text-align: right;" value="${editIncomeDetails[0].monthlyIncome}"  readonly="true"
		 onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);"  onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event,18,id);"/>
  
     </td>
	<td width="17%"><bean:message key="lbl.annuallyIncome"/><font color="red">*</font></td>
	<td width="28%">
	<html:text  styleClass="text" property="annuallyIncome" styleId="annuallyIncome" value="" style="text-align: right;" value="${editIncomeDetails[0].annuallyIncome}"  readonly="true"
	 onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);"  onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event,18,id);"/>
    </td>
		</tr>
		
		<tr>
		<td colspan="2"><bean:message key="lbl.varificationmethod"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">
		 <html:select property="varificationMethod" styleId="varificationMethod" styleClass="text" value="${editIncomeDetails[0].varificationMethod}" disabled="true">
	    <html:option value="">Select</html:option>
	    <html:optionsCollection name="verifMethodList" label="name" value="id"/>
	    </html:select>
     </td>
	<td width="17%"><bean:message key="lbl.sourceType"/></td>
	<td width="28%">
		<html:select property="sourceType" styleId="sourceType"  styleClass="text" value="${editIncomeDetails[0].sourceType}" disabled="true">
	<html:optionsCollection name="sourceTypeList" value="parameCode" label="paramName"/>
	</html:select>
	
    </td>
		</tr>
		
	  <tr>
	  
	  <td colspan="2"><bean:message key="lbl.remarks"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">
		
		<html:textarea property="remarks" styleId="remarks" styleClass="text" value="${editIncomeDetails[0].remarks}" readonly="true"/>
  
     </td>
		<td width="17%"><bean:message key="lbl.includeInRatio"/></td>
	<td width="28%">
	<logic:present name="action">
	<logic:equal name="action" value="Y">
		<input type="checkbox" name="includeInRatio" id="includeInRatio"  checked="true"  class="text" disabled="true"/>
	</logic:equal>
	<logic:equal name="action" value="N">
		<input type="checkbox" name="includeInRatio" id="includeInRatio" checked="false" class="text" disabled="true"/>
	</logic:equal>
	</logic:present>
    </td>

	  </tr>

	  	  </table>
	  	  
	  	   <button type="button" name="Reload"  title="Alt+V" accesskey="V" class="topformbutton2" id="Reload" onclick="reloadIndividualFinancialIncome();" ><bean:message key="button.reload" /></button>
	  <fieldset>	
		 <legend></legend>  
  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
     <tr>
    <td class="gridtd">
   
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        <td> <b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked()" disabled="disabled"/><bean:message key="contact.select" /></b></td>
        <td><center><b><bean:message key="lbl.customerName"/></b></center></td>
        <td><center><b><bean:message key="lbl.customerType"/></b></center></td>
        <td><center><b><bean:message key="lbl.monthlyIncome"/></b></center></td>
        <td><center><b><bean:message key="lbl.annuallyIncome"/></b></center></td>
        <td><center><b><bean:message key="lbl.varificationmethod"/></b></center></td>
        <td><center><b><bean:message key="lbl.sourceType"/></b></center></td>
	</tr>
	<tr>
		
	
		<logic:notEmpty name="incomeDetailList">
		<logic:iterate name="incomeDetailList" id="objincomeDetailList" indexId="count">
		<tr class="white1">
		<td >
		<input type="checkbox" name="chk" id="chk" disabled="disabled"/>
    	 </td>
		<td ><a href="#" id="anchor0" onclick="return editIncomesDetails(${objincomeDetailList.financialId});">${objincomeDetailList.customerName}</a></td>
		<td >${objincomeDetailList.customerType }</td>
		<td>${objincomeDetailList.monthlyIncome }</td>
		<td>${objincomeDetailList.annuallyIncome }</td>
		<td>${objincomeDetailList.varificationMethod}</td>
		<td>${objincomeDetailList.sourceType}</td>
		</tr>	
	 
	   	</logic:iterate>
		</logic:notEmpty>
		</tr>
	
 </table>    </td>
</tr>
</table>

	</fieldset>
	  
  <tr>
			<td nowrap colspan="10" class="white">
			<logic:present name="financialDealId">
			    <input type="hidden" id="dealId" value="${sessionScope.financialDealId}"/>
		    </logic:present>
		
			</td>
			</tr></td></tr>
		</table>
	</fieldset>	
	
</html:form>
</logic:present>
</div>



</div>
<logic:present name="sms">
 <script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="msg.DataSaved" />");
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='B')
	{
			alert("<bean:message key="lbl.firstUpdateThenForward" />");
			location.href="individualFinancialIncomeBehindAction.do?method=incomeBehindDetail";    
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='BB')
	{
			alert("<bean:message key="lbl.updateRatioAnalysis" />");
			location.href="individualFinancialIncomeBehindAction.do?method=incomeBehindDetail";    
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='F')
	{
		alert("<bean:message key="lbl.forwardSuccess" />");
		parent.location.href="financialAnalysisSearchBehindAction.do";
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='A')
	{
		alert("<bean:message key="lbl.dataExist" />");
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.DataNotSaved" />");
	}
	
	
	</script>
</logic:present>
<logic:present name="notForward">

	<script type="text/javascript">
	
		alert("<bean:message key="lbl.notForward" />")
		
</script>
</logic:present>

<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<script>
	setFramevalues("individualFinancialIncomeForm");
</script>

</body>
</html:html>
