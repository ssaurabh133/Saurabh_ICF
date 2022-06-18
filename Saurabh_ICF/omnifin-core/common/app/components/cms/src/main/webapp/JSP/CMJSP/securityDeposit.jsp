<%--
      Author Name-Kaustuv Ranjan 
      Date of creation -26/04/2011
      Purpose-   Providing User Interface for Deposition of Security       
      Documentation-     
      
 --%>

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/customerEntry.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/cmLoanInitjs.js"></script>
	 <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		

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
<body oncontextmenu="return false" onload="enableAnchor();checkChanges('securityForm');">

<div id=centercolumn>
<div id=revisedcontainer>
<logic:notPresent name="cmAuthor">
<html:form action="/securityDeposit" styleId="securityForm" method="post">
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<html:errors />
<fieldset>

<logic:present name="dealId">
	  <table cellspacing="0" cellpadding="0" width="100%" border="0">
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${dealHeader[0].dealNo}</td>
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
	          <td >Deal Capturing</td>
          </tr>
        </table> 
</td>
</tr>
</table>
</logic:present>


<logic:present name="loanId">

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
 </logic:present>
</fieldset>	
<fieldset>
<legend><bean:message key="lbl.securityDetails"/></legend>

<table cellspacing="0" cellpadding="1" border="0" width="100%">
						
				<tr>
					<td width="18%"><bean:message key="lbl.securityAmount"/><font color="red">*</font></td>
					<td ><html:text property="securityAmount" style="text-align: right" styleClass="text" styleId="securityAmount" value="${tenureAmount[0].securityAmount}${amount }" maxlength="100" readonly="true" size="10" onchange="return calculateInterest();"/></td>
								
					<td width="16%"><bean:message key="lbl.interestType"/><font color="red">*</font></td>
					<td width="19%">
				    <logic:present name="briefSecurity">
						<logic:notEmpty name="briefSecurity">
					
						<logic:iterate id="subbriefSecurity" name="briefSecurity">
						  <logic:equal name="subbriefSecurity" property="interestType" value="NO">	
							<input type ="radio" name="interestType" id="interestType1"  value="N" checked="checked" onclick="compFq();calculateInterest();"/>No
						  </logic:equal>
						  <logic:notEqual name="subbriefSecurity" property="interestType" value="NO" >
						  	<input type ="radio" name="interestType" id="interestType1"  value="N" onclick="compFq();calculateInterest();"/>No
						  </logic:notEqual>
						   <logic:equal name="subbriefSecurity" property="interestType" value="SIMPLE">	
								<input type ="radio" name="interestType"  id="interestType2" value="S" checked="checked" onclick="compFq();calculateInterest();" />Simple
							</logic:equal>
							 <logic:notEqual name="subbriefSecurity" property="interestType" value="SIMPLE" >
							 	<html:radio property="interestType"  styleId="interestType2" value="S" onclick="compFq();calculateInterest();">Simple</html:radio>
							 </logic:notEqual>
							<logic:equal name="subbriefSecurity" property="interestType" value="COMPOUND">	
							   <input type ="radio" name="interestType"  id="interestType3" value="C" checked="checked" onclick="compFq();calculateInterest();" />Compound
							</logic:equal>
							 <logic:notEqual name="subbriefSecurity" property="interestType" value="COMPOUND" >
							 	<html:radio property="interestType"  styleId="interestType3" value="C" onclick="compFq();calculateInterest();">Compound</html:radio>
							 </logic:notEqual>
						</logic:iterate>
						</logic:notEmpty>
						<logic:empty name="briefSecurity">
							<input type ="radio" name="interestType" id="interestType1"  value="N" checked="checked" onclick="compFq();calculateInterest();"/>No
							<html:radio property="interestType"  styleId="interestType2" value="S" onclick="compFq();calculateInterest();">Simple</html:radio>
							<html:radio property="interestType"  styleId="interestType3" value="C" onclick="compFq();calculateInterest();">Compound</html:radio>
						</logic:empty>
					</logic:present>
					
					</td>
                   <td width="10%"> </td>
                   <td width="15%"> </td>
      
				</tr>
				
				<tr>
					<td><bean:message key="lbl.interestRate"/><font color="red">*</font></td>
					<td id="">
					<logic:notEmpty name="briefSecurity">
					  <logic:iterate name="briefSecurity" id="obBriefSecurity">

						   <logic:equal name="obBriefSecurity" property="interestType" value="NO">
						   		 <html:text property="interestRate" styleClass="text" styleId="interestRate" value="${briefSecurity[0].interestRate}" readonly="true" maxlength="100" size="10" onkeypress="return fourDecimalnumbersonly(event, id, 3);" onblur="fourDecimalNumber(this.value, id);"  onchange="fourDecimalcheckNumber(this.value, event, 3, id);checkRate('interestRate');calculateInterest();" onfocus="fourDecimalkeyUpNumber(this.value, event, 3, id);" style="text-align: right"/>
						   </logic:equal>
						    <logic:notEqual name="obBriefSecurity" property="interestType" value="NO">
						        <html:text property="interestRate" styleClass="text" styleId="interestRate" value="${briefSecurity[0].interestRate}" maxlength="100" size="10" onkeypress="return fourDecimalnumbersonly(event, id, 3);" onblur="fourDecimalNumber(this.value, id);"  onchange="fourDecimalcheckNumber(this.value, event, 3, id);checkRate('interestRate');calculateInterest();" onfocus="fourDecimalkeyUpNumber(this.value, event, 3, id);" style="text-align: right"/>
							</logic:notEqual>
					</logic:iterate>
					</logic:notEmpty>
					<logic:empty name="briefSecurity">
						 <html:text property="interestRate" styleClass="text" styleId="interestRate" value="0.00" readonly="true" maxlength="100" size="10" onkeypress="return fourDecimalnumbersonly(event, id, 3);" onblur="fourDecimalNumber(this.value, id);"  onchange="fourDecimalcheckNumber(this.value, event, 3, id);checkRate('interestRate');calculateInterest();" onfocus="fourDecimalkeyUpNumber(this.value, event, 3, id);" style="text-align: right"/>
					</logic:empty>
					 </td>
					
					<td><bean:message key="lbl.compFrequency"/><font color="red">*</font></td>	
			<td class="" id=""> 
			<logic:present name="briefSecurity">
			<logic:notEmpty name="briefSecurity">
			<logic:iterate id="subbriefSecurity" name="briefSecurity">
				<logic:equal name="subbriefSecurity" property="interestType" value="COMPOUND">	
					 <html:select property="compoundFrequency" styleId="compoundFrequency" styleClass="text" value="${briefSecurity[0].compoundFrequency}" onchange="return calculateInterest();">
			           <html:option value="">---Select----</html:option>
		              <html:option value="M">MONTHLY</html:option>
				      <html:option value="B">BIMONTHLY</html:option>
				      <html:option value="Q">QUARTERLY</html:option>
				      <html:option value="H">HALFYERALY</html:option>
				      <html:option value="Y">YEARLY</html:option>

		          	       </html:select> 
          	       </logic:equal>
          	        <logic:notEqual name="subbriefSecurity" property="interestType" value="COMPOUND" >
          	        	 <html:select property="compoundFrequency" styleId="compoundFrequency" styleClass="text" value="${briefSecurity[0].compoundFrequency}" disabled="true" onchange="return calculateInterest();">
			           <html:option value="">---Select----</html:option>
		              <html:option value="M">MONTHLY</html:option>
				      <html:option value="B">BIMONTHLY</html:option>
				      <html:option value="Q">QUARTERLY</html:option>
				      <html:option value="H">HALFYERALY</html:option>
				      <html:option value="Y">YEARLY</html:option>

		          	       </html:select> 
          	        </logic:notEqual>
          	       </logic:iterate>
          	       </logic:notEmpty>
          	         <logic:empty name="briefSecurity">
          	       			 <html:select property="compoundFrequency" styleId="compoundFrequency" styleClass="text" value="${briefSecurity[0].compoundFrequency}" disabled="true" onchange="return calculateInterest();">
			           <html:option value="">---Select----</html:option>
		              <html:option value="M">MONTHLY</html:option>
				      <html:option value="B">BIMONTHLY</html:option>
				      <html:option value="Q">QUARTERLY</html:option>
				      <html:option value="H">HALFYERALY</html:option>
				      <html:option value="Y">YEARLY</html:option>

		          	       </html:select> 
          	       </logic:empty>
          	       </logic:present>
          	        	
               			</td>						
				</tr>	
				<tr>
					<td><bean:message key="lbl.tenure"/><font color="red">*</font></td>
					<input type="hidden" name="internalTenure" id="internalTenure" value="${tenureAmount[0].tenure}"/>
				  <td width="28%" >
				  <logic:notEmpty name="briefSecurity">
				  	<html:text property="tenure" styleClass="text" style="text-align: right" styleId="tenure" value="${briefSecurity[0].tenure}" maxlength="5" onkeypress="return isNumberKey(event);"   onchange="calculateInterest();"/>
				  </logic:notEmpty>
				  <logic:empty name="briefSecurity">
				  	 <html:text property="tenure" styleClass="text" style="text-align: right" styleId="tenure" value="${tenureAmount[0].tenure}" maxlength="5" onkeypress="return isNumberKey(event);"   onchange="calculateInterest();"/>
				  </logic:empty>
				   </td>
					<td><bean:message key="lbl.interest"/><font color="red">*</font></td>	
				  <td width="14%" ><html:text property="relatedInterest" styleClass="text" styleId="relatedInterest" value="${briefSecurity[0].relatedInterest}" maxlength="20" size="10" readonly="true"/></td>
				</tr>	
				<tr>
					<td><bean:message key="lbl.sdAdjust"/><font color="red">*</font></td>	
				    <td width="14%" >
				    <html:select property="sdAdjust" styleId="sdAdjust" styleClass="text" value="${briefSecurity[0].sdAdjust}" >
			          <html:option value="N">NO</html:option>
		              <html:option value="Y">YES</html:option>
				   
          	       </html:select>  
				    </td>
				</tr>
					
  <tr>
 <td align="left">

 <button type="button" name="save" id="save" title="Alt+V" accesskey="V" class="topformbutton2" onclick="saveSecurityDeposit();"><bean:message key="button.save" /></button>  </td> </tr></table>
				</fieldset>	

	<logic:present name="sms">

 <script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert('<bean:message key="lbl.securitySuccess" />');
	}
	else
	{
		alert('<bean:message key="lbl.securityError" />');
	}	
</script>
</logic:present>
<logic:present name="deleteS">

 <script type="text/javascript">
	if('<%=request.getAttribute("deleteS")%>'=='S')
	{
		alert('<bean:message key="lbl.securityDeleteSuccess" />');
	}
	else
	{
		alert('<bean:message key="lbl.securityDeleteError" />');
	}	
</script>
</logic:present>	
			
							
</html:form>
</logic:notPresent>

<%--					Loan Initiation for Author															 --%>

<logic:present name="cmAuthor">

<html:form action="/securityDeposit" styleId="securityForm" method="post">
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<html:errors />
<fieldset>

<logic:present name="dealId">
	  <table cellspacing="0" cellpadding="0" width="100%" border="0">
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${dealHeader[0].dealNo}</td>
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
	          <td >Deal Capturing</td>
          </tr>
        </table> 
</td>
</tr>
</table>
</logic:present>


<logic:present name="loanId">

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
 </logic:present>
 
</fieldset>	
<fieldset>
<legend><bean:message key="lbl.securityDetails"/></legend>

<table cellspacing="0" cellpadding="1" border="0" width="100%">
						
				<tr>
					<td width="18%"><bean:message key="lbl.securityAmount"/><font color="red">*</font></td>
					<td ><html:text property="securityAmount" styleClass="text" styleId="securityAmount" value="${tenureAmount[0].securityAmount}" style="text-align: right" maxlength="100" disabled="true" size="10" onchange="return calculateInterest();"/></td>
								
					<td width="16%"><bean:message key="lbl.interestType"/><font color="red">*</font></td>
					<td width="19%">
				    <logic:present name="briefSecurity">
						<logic:notEmpty name="briefSecurity">
					
						<logic:iterate id="subbriefSecurity" name="briefSecurity">
						  <logic:equal name="subbriefSecurity" property="interestType" value="NO">	
							<input type ="radio" name="interestType" id="interestType1"  value="N" checked="checked" disabled="disabled" onclick="compFq();calculateInterest();"/>No
						  </logic:equal>
						  <logic:notEqual name="subbriefSecurity" property="interestType" value="NO" >
						  	<input type ="radio" name="interestType" id="interestType1"  value="N" disabled="disabled" onclick="compFq();calculateInterest();"/>No
						  </logic:notEqual>
						   <logic:equal name="subbriefSecurity" property="interestType" value="SIMPLE">	
								<input type ="radio" name="interestType"  id="interestType2" value="S" disabled="disabled" checked="checked" onclick="compFq();calculateInterest();" />Simple
							</logic:equal>
							 <logic:notEqual name="subbriefSecurity" property="interestType" value="SIMPLE" >
							 	<html:radio property="interestType"  styleId="interestType2" value="S" disabled="true" onclick="compFq();calculateInterest();">Simple</html:radio>
							 </logic:notEqual>
							<logic:equal name="subbriefSecurity" property="interestType" value="COMPOUND">	
							   <input type ="radio" name="interestType"  id="interestType3" value="C" disabled="disabled" checked="checked" onclick="compFq();calculateInterest();" />Compound
							</logic:equal>
							 <logic:notEqual name="subbriefSecurity" property="interestType" value="COMPOUND" >
							 	<html:radio property="interestType"  styleId="interestType3" value="C" disabled="true" onclick="compFq();calculateInterest();">Compound</html:radio>
							 </logic:notEqual>
						</logic:iterate>
						</logic:notEmpty>
						<logic:empty name="briefSecurity">
							<input type ="radio" name="interestType" id="interestType1"  value="N" disabled="disabled" checked="checked" onclick="compFq();calculateInterest();"/>No
							<input type ="radio" name="interestType" id="interestType2" value="S" disabled="disabled" onclick="compFq();calculateInterest();"/>Simple
							<input type ="radio" name="interestType"  id="interestType3" value="C" disabled="disabled" onclick="compFq();calculateInterest();"/>Compound
						</logic:empty>
					</logic:present>
					
					</td>
                   <td width="10%"> </td>
                   <td width="15%"> </td>
      
				</tr>
				
				<tr>
					<td><bean:message key="lbl.interestRate"/><font color="red">*</font></td>
					<td id=""><html:text property="interestRate" styleClass="text" style="text-align: right" styleId="interestRate" disabled="true" value="${briefSecurity[0].interestRate}" maxlength="100" size="10" onkeypress="return numbersonly(event, id, 3);" onblur="formatNumber(this.value, id);"  onchange="checkNumber(this.value, event, 3, id);checkRate('interestRate');calculateInterest();" onfocus="keyUpNumber(this.value, event, 3, id);"/></td>
					<td><bean:message key="lbl.compFrequency"/><font color="red">*</font></td>	
			<td class="" id=""> 
			<html:select property="compoundFrequency" styleId="compoundFrequency" styleClass="text" value="${briefSecurity[0].compoundFrequency}" disabled="true" onchange="return calculateInterest();">
	           <html:option value="">---Select----</html:option>
              <html:option value="M">MONTHLY</html:option>
		      <html:option value="B">BIMONTHLY</html:option>
		      <html:option value="Q">QUARTERLY</html:option>
		      <html:option value="H">HALFYERALY</html:option>
		      <html:option value="Y">YEARLY</html:option>
        
          	       </html:select>  	
               			</td>						
				</tr>	
				<tr>
					<td><bean:message key="lbl.tenure"/><font color="red">*</font></td>
				  <td width="28%" ><html:text property="tenure" styleClass="text" style="text-align: right" styleId="tenure" value="${briefSecurity[0].tenure}" maxlength="5" disabled="true"  onchange="calculateInterest();"/></td>
					<td><bean:message key="lbl.interest"/><font color="red">*</font></td>	
				  <td width="14%" ><html:text property="relatedInterest" styleClass="text" styleId="relatedInterest" value="${briefSecurity[0].relatedInterest}" maxlength="20" size="10" disabled="true"/></td>
				</tr>	
				<tr>
					<td><bean:message key="lbl.sdAdjust"/><font color="red">*</font></td>	
				    <td width="14%" >
				    <html:select property="sdAdjust" styleId="sdAdjust" styleClass="text" disabled="true" value="${briefSecurity[0].sdAdjust}" >
			          <html:option value="N">NO</html:option>
		              <html:option value="Y">YES</html:option>
				   
          	       </html:select>  
				    </td>
				</tr>
					
  <tr>
 <td align="left">
	
							
</html:form>
</logic:present>


</div></div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">
		
</div>

<script>
	setFramevalues("securityForm");
</script>
</body>
</html:html>

