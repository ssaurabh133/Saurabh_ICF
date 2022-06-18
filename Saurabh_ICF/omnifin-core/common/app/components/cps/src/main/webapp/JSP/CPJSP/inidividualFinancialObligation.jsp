<!--
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 10 jan 2012
 	Purpose          :- To provide user interface for individual financial obligation
 	Documentation    :- 
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
         <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/individualFinancialAnalysis.js"></script> 
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
  		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
  		<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
			<script type="text/javascript">
 	
		$(function() {
		$("#maturityDate").datepicker({
		 changeMonth: true,
		 changeYear: true,
	     yearRange: '1900:+10',
	     showOn: 'both',
		<logic:present name="image">
    	   		buttonImage: '<%=request.getContextPath() %>/${image }/calendar.gif',
            </logic:present>
    		<logic:notPresent name="image">
    			buttonImage: '<%=request.getContextPath() %>/images/theme1/calendar.gif',
    		</logic:notPresent>
		 buttonImageOnly: true,
		 dateFormat:"<bean:message key="lbl.dateFormat"/>",
		 defaultDate:'${userobject.businessdate }'
	
	});
     	});
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
<body oncontextmenu="return false" onunload="closeAllLovCallUnloadBody();" onload="enableAnchor();checkChanges('obligationForm');" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
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
	<html:form action="/saveobligationDetail"  styleId="obligationForm">
	<input type="hidden" id="contextPath" value="<%=path %>" />
	<input type="hidden" id="lbxDealNo" value="${sessionScope.financialDealId}" name="lbxDealNo" />
	
	<html:javascript formName="ObligationDynaValidatorForm"/>
	<logic:present name="obligationDetail">
	<fieldset>
	
		  <legend><bean:message key="lbl.obligation"/></legend>         
	   
	
	      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr>
		<td ><bean:message key="lbl.customerType"/></td>
		<td  nowrap="nowrap">
		<html:select property="customerType" styleId="customerType" value="${obligationDetail[0].customerType}" onchange="getCustomerName();" styleClass="text">
		<html:optionsCollection name="customerTypeList" value="parameCode" label="paramName"/>
		</html:select>
			
		     </td>
			<td ><bean:message key="lbl.customerName"/></td>
			<td >
			<div id="customerNameDiv">
			<html:select property="customerName" styleId="customerName" value="${obligationDetail[0].customerId}" styleClass="text">
			<html:optionsCollection name="customerNameList" value="parameCode" label="paramName"/>
			</html:select>
			</div>
			</td>
				</tr>
		<tr>
					
		   <td width="20%"><bean:message key="credit.name"/><font color="red">*</font> </td>
			 <td width="35%">
			<html:text  styleClass="text" property="institutionName" styleId="institutionName" value="${obligationDetail[0].institutionName}" maxlength="50" /></td>
	         <td><bean:message key="lbl.outstandingLoanAmount"/></td>
	         <td><html:text  styleClass="text" property="outstandingLoanAmount" styleId="outstandingLoanAmount" value="${obligationDetail[0].outstandingLoanAmount}" maxlength="22" 
	         style="text-align: right;"
		   onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		</tr>
		<!-- Nishant space starts -->
		<tr>		
		   <td width="20%"><bean:message key="lbl.LoanNo"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanNo" styleId="loanNo" value="${obligationDetail[0].loanNo}" maxlength="50" /></td>
	         <td><bean:message key="lbl.typeOfLoan"/></td>
	         <td nowrap="nowrap">
				 <html:select property="typeOfLoan" styleId="typeOfLoan" styleClass="text" value="${obligationDetail[0].typeOfLoan}" >
			    <html:option value="">Select</html:option>
			    <html:optionsCollection name="typeOfLoanList" label="typeOfLoanValueId" value="typeOfLoanValue"/>
			    </html:select>
	     	</td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.loanAmount"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanAmount" styleId="loanAmount" value="${obligationDetail[0].loanAmount}" maxlength="22" style="text-align: right;" onkeypress="return numbersonly(event,id,22)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 22,id);"	onfocus="keyUpNumber(this.value, event, 22,id);" /></td>
	         <td><bean:message key="lbl.loanMonth"/></td>
	         <td><html:text  styleClass="text" property="loanMonth" styleId="loanMonth" value="${obligationDetail[0].loanMonth}" maxlength="7" onblur="checkFormate(this.value,this.id)"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.tenure"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanTenure" styleId="loanTenure" value="${obligationDetail[0].loanTenure}" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);" /></td>
	         <td><bean:message key="lbl.emiPaid"/></td>
	         <td><html:text  styleClass="text" property="emiPaid" styleId="emiPaid" value="${obligationDetail[0].emiPaid}" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.emiPending"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="emiPending" styleId="emiPending" value="${obligationDetail[0].emiPending}" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);" /></td>
	         <td><bean:message key="lbl.loanStatus"/></td>
	         <td><html:text  styleClass="text" property="loanStatus" styleId="loanStatus" value="${obligationDetail[0].loanStatus}" maxlength="50"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.obligationConsider"/></td>
			 <td nowrap="nowrap">
				 <html:select property="obligationConsider" styleId="obligationConsider" styleClass="text" value="${obligationDetail[0].obligationConsider}" >
				    <html:option value="">Select</html:option>
				    <html:option value="Y">Yes</html:option>
				    <html:option value="N">No</html:option>
			    </html:select>
	     	</td>
	         <td><bean:message key="lbl.trackPer"/></td>
	         <td><html:text  styleClass="text" property="trackPer" styleId="trackPer" value="${obligationDetail[0].trackPer}" maxlength="100"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.emiPaidFrom"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="emiPaidFrom" styleId="emiPaidFrom" value="${obligationDetail[0].emiPaidFrom}" maxlength="50" /></td>
		</tr>
		<!-- Nishant space ends -->
	  <tr>
	    <td><bean:message key="lbl.emiAmount"/><font color="red">*</font> </td>
	    <td><html:text  styleClass="text" property="emiAmount" styleId="emiAmount" value="${obligationDetail[0].emiAmount}" maxlength="22" 
	    style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		<td><bean:message key="lbl.remainingTenureInMonths"/><font color="red">*</font></td>
		    <td nowrap="nowrap" ><html:text  styleClass="text" property="tenure" styleId="tenure" value="${obligationDetail[0].tenure}" maxlength="3" 
		    style="text-align: right;" onkeypress="return isNumberKey(event);" />  </td>
	  </tr>
		 <tr>
		   <td><bean:message key="lbl.sdMaturityDate"/> <font color="red">*</font></td>
		   <td nowrap="nowrap" ><html:text  styleClass="text" property="maturityDate" styleId="maturityDate" value="${obligationDetail[0].maturityDate}" maxlength="20" />  </td>
		   <td><bean:message key="lbl.purpose"/><font color="red">*</font></td>
		    <td nowrap="nowrap" ><label>
		    <html:text  styleClass="text" property="purpose" styleId="purpose" value="${obligationDetail[0].purpose}" maxlength="50" />
		    </label></td>
		 </tr>
		 <tr>
		 <td><bean:message key="lbl.obligationType"/><font color="red">*</font></td>
		 <td>
		 
		 <html:select property="obligationType" styleId="obligationType" styleClass="text" value="${obligationDetail[0].obligationType}" >
	     <html:option value="">Select</html:option>
	     <html:optionsCollection name="obligationTypeList" label="paramName" value="parameCode"/>
	     </html:select>
		 </td>
		 <td><bean:message key="lbl.financeAmount"/><font color="red">*</font> </td>
		 <td><html:text  styleClass="text" property="financeAmount" styleId="financeAmount" value="${obligationDetail[0].financeAmount}"
            style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);" />  </td>
		 </tr>
		 <tr>
		 <td ><bean:message key="lbl.varificationmethod"/><font color="red">*</font></td>
		<td nowrap="nowrap">
		 <html:select property="varificationMethod" styleId="varificationMethod" styleClass="text" value="${obligationDetail[0].varificationMethod}" >
	    <html:option value="">Select</html:option>
	    <html:optionsCollection name="verifMethodList" label="name" value="id"/>
	    </html:select>
     </td>
     	<td width="13%"><bean:message key="lbl.comment" /></td>
     	<td width="13%"> 
     		<textarea  class="text" id="comment" 	name="comment" 	onkeypress="textCounter(this,this.form.counter,999);">${obligationDetail[0].comment}</textarea>
     	</td>
		 </tr>
		 <tr>
		  <td align="left" class="form2" colspan="4">
		    <logic:present name="insert">
		   <button type="button" name="Save" class="topformbutton2" id="Save" onclick="return saveIndividualObligation();" title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
		   </logic:present>
		   
		    <logic:notPresent name="insert">
		    <button type="button" name="Save" class="topformbutton2" id="Save"  title="Alt+V" accesskey="V" onclick="updateIndividualObligation(${obligationDetail[0].obligationId});" ><bean:message key="button.save" /></button>
			</logic:notPresent>		    
		     
	        </td>
		  </tr>
		</table>
		
	      </td>
	</tr>
	</table>
	</fieldset>
	 </logic:present>
	 <logic:notPresent name="obligationDetail">
	<fieldset>
	
		  <legend><bean:message key="lbl.obligation"/></legend>      
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
		<td ><bean:message key="lbl.customerType"/></td>
		<td nowrap="nowrap">
		<html:select property="customerType" styleId="customerType" value="PRAPPL" onchange="getCustomerName();" styleClass="text">
		<html:optionsCollection name="customerTypeList" value="parameCode" label="paramName"/>
		</html:select>
			
		     </td>
			<td ><bean:message key="lbl.customerName"/></td>
			<td >
			<div id="customerNameDiv">
			<html:select property="customerName" styleId="customerName" value="" styleClass="text">
			<html:optionsCollection name="customerNameList" value="parameCode" label="paramName"/>
			</html:select>
			</div>
			</td>
		</tr>
		<tr>
					
		   <td width="20%"><bean:message key="credit.name"/><font color="red">*</font> </td>
			 <td width="35%">
			<html:text  styleClass="text" property="institutionName" styleId="institutionName" value="" maxlength="50" /></td>
	         <td><bean:message key="lbl.outstandingLoanAmount"/></td>
	         <td><html:text  styleClass="text" property="outstandingLoanAmount" styleId="outstandingLoanAmount" value="" maxlength="22" 
	         style="text-align: right;"
		   onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		</tr>
		<!-- Nishant space starts -->
		<tr>		
		   <td width="20%"><bean:message key="lbl.LoanNo"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanNo" styleId="loanNo" value="${obligationDetail[0].loanNo}" maxlength="50" /></td>
	         <td><bean:message key="lbl.typeOfLoan"/></td>
	         <td nowrap="nowrap">
				 <html:select property="typeOfLoan" styleId="typeOfLoan" styleClass="text" value="${obligationDetail[0].typeOfLoan}" >
			    <html:option value="">Select</html:option>
			    <html:optionsCollection name="typeOfLoanList" label="typeOfLoanValueId" value="typeOfLoanValue"/>
			    </html:select>
	     	</td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.loanAmount"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanAmount" styleId="loanAmount" value="${obligationDetail[0].loanAmount}" maxlength="22" style="text-align: right;" onkeypress="return numbersonly(event,id,22)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 22,id);"	onfocus="keyUpNumber(this.value, event, 22,id);" /></td>
	         <td><bean:message key="lbl.loanMonth"/></td>
	         <td><html:text  styleClass="text" property="loanMonth" styleId="loanMonth" value="${obligationDetail[0].loanMonth}" maxlength="7" onblur="checkFormate(this.value,this.id)"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.tenure"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanTenure" styleId="loanTenure" value="${obligationDetail[0].loanTenure}" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);" /></td>
	         <td><bean:message key="lbl.emiPaid"/></td>
	         <td><html:text  styleClass="text" property="emiPaid" styleId="emiPaid" value="${obligationDetail[0].emiPaid}" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.emiPending"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="emiPending" styleId="emiPending" value="${obligationDetail[0].emiPending}" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);" /></td>
	         <td><bean:message key="lbl.loanStatus"/></td>
	         <td><html:text  styleClass="text" property="loanStatus" styleId="loanStatus" value="${obligationDetail[0].loanStatus}" maxlength="50"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.obligationConsider"/></td>
			 <td nowrap="nowrap">
				 <html:select property="obligationConsider" styleId="obligationConsider" styleClass="text" value="${obligationDetail[0].obligationConsider}" >
				    <html:option value="">Select</html:option>
				    <html:option value="Y">Yes</html:option>
				    <html:option value="N">No</html:option>
			    </html:select>
	     	</td>
	         <td><bean:message key="lbl.trackPer"/></td>
	         <td><html:text  styleClass="text" property="trackPer" styleId="trackPer" value="${obligationDetail[0].trackPer}" maxlength="100"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.emiPaidFrom"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="emiPaidFrom" styleId="emiPaidFrom" value="${obligationDetail[0].emiPaidFrom}" maxlength="50" /></td>
		</tr>
		<!-- Nishant space ends -->
	  <tr>
	    <td><bean:message key="lbl.emiAmount"/><font color="red">*</font> </td>
	    <td><html:text  styleClass="text" property="emiAmount" styleId="emiAmount" value="" maxlength="22" 
	    style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		<td><bean:message key="lbl.remainingTenureInMonths"/><font color="red">*</font></td>
		    <td nowrap="nowrap" ><html:text  styleClass="text" property="tenure" styleId="tenure" value="" maxlength="3" 
		    style="text-align: right;" onkeypress="return isNumberKey(event);" />  </td>
	  </tr>
		 <tr>
		   <td><bean:message key="lbl.sdMaturityDate"/> <font color="red">*</font></td>
		   <td nowrap="nowrap" ><html:text  styleClass="text" property="maturityDate" styleId="maturityDate" value="" maxlength="20" />  </td>
		   <td><bean:message key="lbl.purpose"/><font color="red">*</font></td>
		    <td nowrap="nowrap" ><label>
		    <html:text  styleClass="text" property="purpose" styleId="purpose" value="" maxlength="50" />
		    </label></td>
		 </tr>
		 <tr>
		 <td><bean:message key="lbl.obligationType"/><font color="red">*</font></td>
		 <td>
		 
		 <html:select property="obligationType" styleId="obligationType" styleClass="text" value="" >
	     <html:option value="">Select</html:option>
	     <html:optionsCollection name="obligationTypeList" label="paramName" value="parameCode"/>
	     </html:select>
		 </td>
		 <td><bean:message key="lbl.financeAmount"/> <font color="red">*</font></td>
		 <td><html:text  styleClass="text" property="financeAmount" styleId="financeAmount" value="" 
            style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);" />  </td>
		 </tr>
		 <tr>
		 <td ><bean:message key="lbl.varificationmethod"/><font color="red">*</font></td>
		<td nowrap="nowrap">
		 <html:select property="varificationMethod" styleId="varificationMethod" styleClass="text" value="${editIncomeDetails[0].varificationMethod}" >
	    <html:option value="">Select</html:option>
	    <html:optionsCollection name="verifMethodList" label="name" value="id"/>
	    </html:select>
     	</td>
     	<td width="13%"><bean:message key="lbl.comment" /></td>
     	<td width="13%"> 
     		<textarea  class="text" id="comment" 	name="comment" 	onkeypress="textCounter(this,this.form.counter,999);">${editIncomeDetails[0].comment}</textarea>
     	</td>
		 </tr>
		 <tr>
		  <td align="left" class="form2" colspan="4"> 
		   
		   <button type="button" name="Save" class="topformbutton2" id="Save" onclick="return saveIndividualObligation();" ><bean:message key="button.save" /></button>
		    <button type="button"  class="topformbutton2" onclick="IndividualObligatoinClear();" accesskey="C" title="Alt+C"><bean:message key="button.clear" /></button>
	        </td>
		  </tr>
		</table>
		
	      </td>
	</tr>
	</table>
	</fieldset>
	 </logic:notPresent>
	 
	  <br/>
	<logic:present name="financialDealId">
	
<fieldset>	
<!--		 <legend><bean:message key="stakeObligation.details"/> </legend>  -->

  
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
    	<td >
    	 <b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/><bean:message key="contact.select" /></b>
	    </td>
	     <td ><b><bean:message key="lbl.customerName"/></b> </td> 
        <td ><b><bean:message key="credit.name"/></b> </td>
		<td ><strong><bean:message key="lbl.outstandingLoanAmount"/> </strong></td>
        <td ><b><bean:message key="lbl.emiAmount"/> </b></td>
        <td ><strong><bean:message key="lbl.tenure"/></strong></td>
        <td ><b><bean:message key="lbl.closureDate"/> </b></td>
        <td ><strong><bean:message key="lbl.purpose"/></strong></td>
	</tr>
	
		<logic:present name="obligationList">
			<logic:iterate id="subObligationList" name="obligationList">
				<tr class="white1">
					<td >
    					<input type="checkbox" name="chk" id="chk" value="${subObligationList.obligationId }"/>
					</td>
					 <td ><a href="#" id="anchor0" onclick="return getIndividualObligation(${subObligationList.obligationId });">${subObligationList.customerName }</a></td> 
					<td >${subObligationList.institutionName }</td>
					<td >${subObligationList.outstandingLoanAmount }</td>
					<td>${subObligationList.emiAmount }</td>
					<td>${subObligationList.tenure }</td>
					<td >${subObligationList.maturityDate }</td>
					<td>${subObligationList.purpose }</td>
				</tr>	
			</logic:iterate>
			<input type="hidden" id="dealId" value="${subObligationList.dealId }"/>
		</logic:present>	
	
	
 </table>    </td>
</tr>

</table></fieldset>
		<button type="button" name="button1" class="topformbutton2" title="Alt+D" accesskey="D" onclick="deleteIndividualObligation();"><bean:message key="button.delete" /></button>
</logic:present>
	</html:form>
	</logic:notPresent>
	<%--   -------------- -------------------- ---------View  mode--- -------------------- -------------------- --------------------- --%>
<logic:present name="underWriterViewData">
		<html:form action="/saveobligationDetail"  styleId="obligationForm">
	<input type="hidden" id="contextPath" value="<%=path %>" />
	<html:javascript formName="ObligationDynaValidatorForm"/>
	<logic:present name="obligationDetail">
	<fieldset>
	
		  <legend><bean:message key="lbl.obligation"/></legend>         
	   
	
	      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr>
		<td ><bean:message key="lbl.customerType"/></td>
		<td  nowrap="nowrap">
		<html:select property="customerType" styleId="customerType" value="${obligationDetail[0].customerType}" onchange="getCustomerName();" disabled="true" styleClass="text">
		<html:optionsCollection name="customerTypeList" value="parameCode" label="paramName"/>
		</html:select>
			
		     </td>
			<td ><bean:message key="lbl.customerName"/></td>
			<td >
			<div id="customerNameDiv">
			<html:select property="customerName" styleId="customerName" value="${obligationDetail[0].customerId}" disabled="true" styleClass="text">
			<html:optionsCollection name="customerNameList" value="parameCode" label="paramName"/>
			</html:select>
			</div>
			</td>
				</tr>
		<tr>
					
		   <td width="20%"><bean:message key="credit.name"/><font color="red">*</font> </td>
			 <td width="35%">
			<html:text  styleClass="text" property="institutionName" styleId="institutionName" readonly="true"  value="${obligationDetail[0].institutionName}" maxlength="50" /></td>
	         <td><bean:message key="lbl.outstandingLoanAmount"/> </td>
	         <td><html:text  styleClass="text" property="outstandingLoanAmount" styleId="outstandingLoanAmount" readonly="true"  value="${obligationDetail[0].outstandingLoanAmount}" maxlength="22" 
	         style="text-align: right;"
		   onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" readonly="true"  onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		</tr>
		<!-- Nishant space starts -->
		<tr>		
		   <td width="20%"><bean:message key="lbl.LoanNo"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanNo" styleId="loanNo" value="${obligationDetail[0].loanNo}" maxlength="50" /></td>
	         <td><bean:message key="lbl.typeOfLoan"/></td>
	         <td nowrap="nowrap">
				 <html:select property="typeOfLoan" styleId="typeOfLoan" styleClass="text" value="${obligationDetail[0].typeOfLoan}" >
			    <html:option value="">Select</html:option>
			    <html:optionsCollection name="typeOfLoanList" label="typeOfLoanValueId" value="typeOfLoanValue"/>
			    </html:select>
	     	</td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.loanAmount"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanAmount" styleId="loanAmount" value="${obligationDetail[0].loanAmount}" maxlength="22" style="text-align: right;" onkeypress="return numbersonly(event,id,22)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 22,id);"	onfocus="keyUpNumber(this.value, event, 22,id);" /></td>
	         <td><bean:message key="lbl.loanMonth"/></td>
	         <td><html:text  styleClass="text" property="loanMonth" styleId="loanMonth" value="${obligationDetail[0].loanMonth}" maxlength="7" onblur="checkFormate(this.value,this.id)"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.tenure"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanTenure" styleId="loanTenure" value="${obligationDetail[0].loanTenure}" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);" /></td>
	         <td><bean:message key="lbl.emiPaid"/></td>
	         <td><html:text  styleClass="text" property="emiPaid" styleId="emiPaid" value="${obligationDetail[0].emiPaid}" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.emiPending"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="emiPending" styleId="emiPending" value="${obligationDetail[0].emiPending}" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);" /></td>
	         <td><bean:message key="lbl.loanStatus"/></td>
	         <td><html:text  styleClass="text" property="loanStatus" styleId="loanStatus" value="${obligationDetail[0].loanStatus}" maxlength="50"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.obligationConsider"/></td>
			 <td nowrap="nowrap">
				 <html:select property="obligationConsider" styleId="obligationConsider" styleClass="text" value="${obligationDetail[0].obligationConsider}" >
				    <html:option value="">Select</html:option>
				    <html:option value="Y">Yes</html:option>
				    <html:option value="N">No</html:option>
			    </html:select>
	     	</td>
	         <td><bean:message key="lbl.trackPer"/></td>
	         <td><html:text  styleClass="text" property="trackPer" styleId="trackPer" value="${obligationDetail[0].trackPer}" maxlength="100"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.emiPaidFrom"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="emiPaidFrom" styleId="emiPaidFrom" value="${obligationDetail[0].emiPaidFrom}" maxlength="50" /></td>
		</tr>
		<!-- Nishant space ends -->
	  <tr>
	    <td><bean:message key="lbl.emiAmount"/><font color="red">*</font> </td>
	    <td><html:text  styleClass="text" property="emiAmount" styleId="emiAmount" value="${obligationDetail[0].emiAmount}" maxlength="22" 
	    style="text-align: right;" onkeypress="return numbersonly(event,id,18)" readonly="true"  onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		<td><bean:message key="lbl.remainingTenureInMonths"/><font color="red">*</font></td>
		    <td nowrap="nowrap" ><html:text  styleClass="text" property="tenure" styleId="tenure" value="${obligationDetail[0].tenure}" maxlength="3" 
		    style="text-align: right;" onkeypress="return isNumberKey(event);" />  </td>
	  </tr>
		 <tr>
		   <td><bean:message key="lbl.sdMaturityDate"/> <font color="red">*</font></td>
		   <td nowrap="nowrap" ><html:text  styleClass="text" property="maturityDate" readonly="true"  styleId="maturityDate" value="${obligationDetail[0].maturityDate}" maxlength="20" />  </td>
		   <td><bean:message key="lbl.purpose"/><font color="red">*</font></td>
		    <td nowrap="nowrap" ><label>
		    <html:text  styleClass="text" property="purpose" readonly="true"  styleId="purpose" value="${obligationDetail[0].purpose}" maxlength="50" />
		    </label></td>
		 </tr>
		 <tr>
		 <td><bean:message key="lbl.obligationType"/> <font color="red">*</font></td>
		 <td>
		 
		 <html:select property="obligationType" styleId="obligationType" styleClass="text" value="${obligationDetail[0].obligationType}" disabled="true" >
	     <html:option value="">Select</html:option>
	     <html:optionsCollection name="obligationTypeList" label="paramName" value="parameCode"/>
	     </html:select>
		 </td>
		 <td><bean:message key="lbl.financeAmount"/> <font color="red">*</font></td>
		 <td><html:text  styleClass="text" property="financeAmount" styleId="financeAmount" value="${obligationDetail[0].financeAmount}" readonly="true"
            style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);" />  </td>
		 </tr>
		 <tr>
		 <td ><bean:message key="lbl.varificationmethod"/><font color="red">*</font></td>
		<td nowrap="nowrap">
		 <html:select property="varificationMethod" styleId="varificationMethod" styleClass="text" value="${obligationDetail[0].varificationMethod}" disabled="true" >
	    <html:option value="">Select</html:option>
	    <html:optionsCollection name="verifMethodList" label="name" value="id"/>
	    </html:select>
     </td>
     	<td width="13%"><bean:message key="lbl.comment" /></td>
     	<td width="13%"> 
     		<textarea  class="text" id="comment" readonly="readonly" name="comment" 	onkeypress="textCounter(this,this.form.counter,999);">${obligationDetail[0].comment}</textarea>
     	</td>
		 </tr>
		</table>
		
	      </td>
	</tr>
	</table>
	</fieldset>
	 </logic:present>
	 <logic:notPresent name="obligationDetail">
	<fieldset>
	
		  <legend><bean:message key="lbl.obligation"/></legend>      
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr>
		<td ><bean:message key="lbl.customerType"/></td>
		<td  nowrap="nowrap">
		<html:select property="customerType" styleId="customerType" value="${obligationDetail[0].customerType}" onchange="getCustomerName();" disabled="true" styleClass="text">
		<html:optionsCollection name="customerTypeList" value="parameCode" label="paramName"/>
		</html:select>
			
		     </td>
			<td ><bean:message key="lbl.customerName"/></td>
			<td >
			<div id="customerNameDiv">
			<html:select property="customerName" styleId="customerName" value="${obligationDetail[0].customerId}" disabled="true" styleClass="text">
			<html:optionsCollection name="customerNameList" value="parameCode" label="paramName"/>
			</html:select>
			</div>
			</td>
				</tr>
		<tr>
					
		   <td width="20%"><bean:message key="credit.name"/><font color="red">*</font> </td>
			 <td width="35%">
			<html:text  styleClass="text" property="institutionName" readonly="true"  styleId="institutionName" value="" maxlength="50" /></td>
	         <td><bean:message key="lbl.outstandingLoanAmount"/></td>
	         <td><html:text  styleClass="text" property="outstandingLoanAmount" styleId="outstandingLoanAmount" value="" maxlength="22" 
	         style="text-align: right;"
		   onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" readonly="true"  onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		</tr>
		<!-- Nishant space starts -->
		<tr>		
		   <td width="20%"><bean:message key="lbl.LoanNo"/>Test1</td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanNo" styleId="loanNo" value="${obligationDetail[0].loanNo}" maxlength="50" /></td>
	         <td><bean:message key="lbl.typeOfLoan"/></td>
	         <td nowrap="nowrap">
				 <html:select property="typeOfLoan" styleId="typeOfLoan" styleClass="text" value="${obligationDetail[0].typeOfLoan}" >
			    <html:option value="">Select</html:option>
			    <html:optionsCollection name="typeOfLoanList" label="typeOfLoanValueId" value="typeOfLoanValue"/>
			    </html:select>
	     	</td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.loanAmount"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanAmount" styleId="loanAmount" value="${obligationDetail[0].loanAmount}" maxlength="22" style="text-align: right;" onkeypress="return numbersonly(event,id,22)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 22,id);"	onfocus="keyUpNumber(this.value, event, 22,id);" /></td>
	         <td><bean:message key="lbl.loanMonth"/></td>
	         <td><html:text  styleClass="text" property="loanMonth" styleId="loanMonth" value="${obligationDetail[0].loanMonth}" maxlength="7" onblur="checkFormate(this.value,this.id)"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.tenure"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanTenure" styleId="loanTenure" value="${obligationDetail[0].loanTenure}" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);" /></td>
	         <td><bean:message key="lbl.emiPaid"/></td>
	         <td><html:text  styleClass="text" property="emiPaid" styleId="emiPaid" value="${obligationDetail[0].emiPaid}" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.emiPending"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="emiPending" styleId="emiPending" value="${obligationDetail[0].emiPending}" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);" /></td>
	         <td><bean:message key="lbl.loanStatus"/></td>
	         <td><html:text  styleClass="text" property="loanStatus" styleId="loanStatus" value="${obligationDetail[0].loanStatus}" maxlength="50"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.obligationConsider"/></td>
			 <td nowrap="nowrap">
				 <html:select property="obligationConsider" styleId="obligationConsider" styleClass="text" value="${obligationDetail[0].obligationConsider}" >
				    <html:option value="">Select</html:option>
				    <html:option value="Y">Yes</html:option>
				    <html:option value="N">No</html:option>
			    </html:select>
	     	</td>
	         <td><bean:message key="lbl.trackPer"/></td>
	         <td><html:text  styleClass="text" property="trackPer" styleId="trackPer" value="${obligationDetail[0].trackPer}" maxlength="100"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.emiPaidFrom"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="emiPaidFrom" styleId="emiPaidFrom" value="${obligationDetail[0].emiPaidFrom}" maxlength="50" /></td>
		</tr>
		<!-- Nishant space ends -->
	  <tr>
	    <td><bean:message key="lbl.emiAmount"/><font color="red">*</font> </td>
	    <td><html:text  styleClass="text" property="emiAmount" styleId="emiAmount" value="" maxlength="22" 
	    style="text-align: right;" onkeypress="return numbersonly(event,id,18)" readonly="true"  onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		<td><bean:message key="lbl.remainingTenureInMonths"/><font color="red">*</font></td>
		    <td nowrap="nowrap" ><html:text  styleClass="text" property="tenure" styleId="tenure" value="" maxlength="3" 
		    style="text-align: right;" onkeypress="return isNumberKey(event);" />  </td>
	  </tr>
		 <tr>
		   <td><bean:message key="lbl.sdMaturityDate"/> <font color="red">*</font></td>
		   <td nowrap="nowrap" ><html:text  styleClass="text" property="maturityDate" readonly="true"  styleId="maturityDate" value="" maxlength="20" />  </td>
		   <td><bean:message key="lbl.purpose"/><font color="red">*</font></td>
		    <td nowrap="nowrap" ><label>
		    <html:text  styleClass="text" property="purpose" styleId="purpose" readonly="true" value="" maxlength="50" />
		    </label></td>
		 </tr>
		 		 <tr>
		 <td><bean:message key="lbl.obligationType"/> <font color="red">*</font></td>
		 <td>
		 
		 <html:select property="obligationType" styleId="obligationType" styleClass="text" value="${obligationDetail[0].obligationType}" disabled="true" >
	     <html:option value="">Select</html:option>
	     <html:optionsCollection name="obligationTypeList" label="paramName" value="parameCode"/>
	     </html:select>
		 </td>
		 <td><bean:message key="lbl.financeAmount"/> <font color="red">*</font></td>
		 <td><html:text  styleClass="text" property="financeAmount" styleId="financeAmount" value="${obligationDetail[0].financeAmount}" readonly="true"
            style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);" />  </td>
		 </tr>
		 <tr>
		 <td ><bean:message key="lbl.varificationmethod"/><font color="red">*</font></td>
		<td nowrap="nowrap">
		 <html:select property="varificationMethod" styleId="varificationMethod" styleClass="text" value="${obligationDetail[0].varificationMethod}" disabled="true" >
	    <html:option value="">Select</html:option>
	    <html:optionsCollection name="verifMethodList" label="name" value="id"/>
	    </html:select>
     </td>
     	<td width="13%"><bean:message key="lbl.comment" /></td>
     	<td width="13%"> 
     		<textarea  class="text" id="comment" 	name="comment" readonly="readonly"	onkeypress="textCounter(this,this.form.counter,999);">${obligationDetail[0].comment}</textarea>
     	</td>
		 </tr>
		</table>
		
	      </td>
	</tr>
	</table>
	</fieldset>
	 </logic:notPresent>
	 
	  <br/>
	<logic:present name="financialDealId">
	
<fieldset>	
<!--		 <legend><bean:message key="stakeObligation.details"/> </legend>  -->

  
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
    	<td >
    	 <b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();" disabled="disabled"/><bean:message key="contact.select" /></b>
	    </td> 
	    <td ><b><bean:message key="lbl.customerName"/></b> </td>
        <td ><b><bean:message key="credit.name"/></b> </td>
		<td ><strong><bean:message key="lbl.outstandingLoanAmount"/> </strong></td>
        <td ><b><bean:message key="lbl.emiAmount"/> </b></td>
        <td ><strong><bean:message key="lbl.tenure"/></strong></td>
        <td ><b><bean:message key="lbl.closureDate"/> </b></td>
        <td ><strong><bean:message key="lbl.purpose"/></strong></td>
	</tr>
	
		<logic:present name="obligationList">
			<logic:iterate id="subObligationList" name="obligationList">
				<tr class="white1">
					<td >
    					<input type="checkbox" name="chk" id="chk" value="${subObligationList.obligationId }" disabled="disabled"/>
					</td>
					 <td ><a href="#" id="anchor0" onclick="return getIndividualObligation(${subObligationList.obligationId });">${subObligationList.customerName }</a></td> 
					<td >${subObligationList.institutionName }</td>
					<td >${subObligationList.outstandingLoanAmount }</td>
					<td>${subObligationList.emiAmount }</td>
					<td>${subObligationList.tenure }</td>
					<td >${subObligationList.maturityDate }</td>
					<td>${subObligationList.purpose }</td>
				</tr>	
			</logic:iterate>
			<input type="hidden" id="dealId" value="${subObligationList.dealId }"/>
		</logic:present>	
	
	
 </table>    </td>
</tr>

</table></fieldset>
		
</logic:present>
	</html:form>
</logic:present>
</div></div>
<logic:present name="sms">
 <script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="msg.DataSaved" />");
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		document.getElementById("maturityDate").focus();
		alert("<bean:message key="lbl.maturityDateLessBussDate" />");
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='Del')
	{
		alert("<bean:message key="lbl.dataDeleted" />");
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='DE')
	{
		alert("<bean:message key="lbl.dataNtDeleted" />");
		
	}else if('<%=request.getAttribute("sms").toString()%>'=='A')
	{
		alert("<bean:message key="lbl.dataExist" />");
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.DataNotSaved" />");
	}
	
	</script>
	</logic:present>
	<script>
	setFramevalues("obligationForm");
</script>
	
</body>
</html:html>