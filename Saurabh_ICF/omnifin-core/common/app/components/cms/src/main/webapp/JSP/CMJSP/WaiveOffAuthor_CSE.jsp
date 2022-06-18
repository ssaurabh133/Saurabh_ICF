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
 <script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
 <script type="text/javascript" src="<%=path%>/js/cmScript/waiveOff.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	
	</head> 
<body onload="enableAnchor();checkChanges('waiveOffMakerFormCSE');">
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<logic:present name="AuthorData">
	<html:form action="/waiveOffMakerAction" styleId="waiveOffMakerFormCSE">
	
	<fieldset>	  
	  <legend><bean:message key="lbl.waiveOffmaker"/> </legend>         
	   
	  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>

		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		  <tr>
            <td width="25%"><bean:message key="lbl.lan"/> </td>
		    <td width="25%">	
		      <html:text styleClass="text" property="loanAccountNo" maxlength="20" styleId="loanAccountNo" style="" disabled="true" value="${AuthorData[0].loanAccountNo}"/> 
               <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
              <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="" />
                   <html:hidden property="waveOffId" styleId="waveOffId" value="${AuthorData[0].waveOffId}"/>
    </td>
		  
		    <td width="25%"><bean:message key="lbl.CustomerName"/>  </td>
		    <td width="25%"><html:text styleClass="text" disabled="true" property="customerName" style="" styleId="customerName" value="${AuthorData[0].customerName}" maxlength="50"/></td>
		    </tr>
		  <tr>
            <td> <bean:message key="lbl.businessPartnerType"/>  </td>
		    <td nowrap="nowrap" >
								<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" disabled="true" value="${AuthorData[0].businessPartnerType}"/>
								<html:text styleClass="text" maxlength="10" property="businessPartnerDesc" styleId="businessPartnerDesc" style="" readonly="true" value="${AuthorData[0].businessPartnerDescription}"/>              					
              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
              				    <html:hidden  property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="" />
					</td>
            	         			
            
		    <td nowrap="nowrap"><bean:message key="lbl.businessPartnerName"/> </td>	    
		    
		    <td nowrap="nowrap">	
		      <html:text property="businessPartnerName" styleId="businessPartnerName" disabled="true" value="${AuthorData[0].businessPartnerName}" styleClass="text" maxlength="50"/>
							
			 </td>					
   
		  </tr>
		  
		  <tr>
				<td>
					<bean:message key="lbl.waiveOffDate" />
				</td>
				<td>
					
						<html:text styleClass="text" property="valueDate"
						styleId="valueDate1" maxlength="10"
						value="${AuthorData[0].valueDate}" readonly="true"/>
											
					</td>
					<td><bean:message key="lbl.waiveOffApprovedBy" /></td>
					<td nowrap="nowrap">  
               		  <html:text property="approvedBy" readonly="true" styleClass="text" value="${AuthorData[0].lbxapprovedBy}" styleId="approvedBy" tabindex="-1" />
				 	   <html:hidden  property="lbxapprovedBy" styleId="lbxapprovedBy" value="${AuthorData[0].lbxapprovedBy}"/>
        			 </td>
			</tr>
		  
		  <tr>
            <td nowrap="nowrap"><bean:message key="lbl.chargeId"/> </td>
            <td nowrap="nowrap">	 
              <html:text styleClass="text" maxlength="10" property="chargeType" styleId="chargeType" disabled="true" value="${AuthorData[0].chargeType}"/> 
             
    </td>
		    <td nowrap="nowrap"><bean:message key="lbl.chargeDescription"/></td>
		    <td nowrap="nowrap" >
		    <html:text styleClass="text" property="chargeDescription" styleId="chargeDescription" style="" disabled="true" value="${AuthorData[0].chargeDescription}" maxlength="50"/></td>
		    </tr>
				
		
		<!--	Change from here start	-->
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		</table>
		<tr>
		<td class="gridtd">
		<table width="100%" border="0" cellspacing="1" cellpadding="1" >
		<tr class="white2">
			<td width="25%"><bean:message key="lbl.chargeDetails"/></td>
			<td width="25%"><bean:message key="lbl.chargeAmount"/></td>
			<td width="25%"><bean:message key="lbl.waiveOffAmount"/></td>
			<td width="25%"><bean:message key="lbl.EffectiveChargeAmount"/></td>
		</tr>
		<tr class="white1">		  
		    <td nowrap="nowrap"><bean:message key="lbl.chargeAmount"/>  </td>
		    <td nowrap="nowrap"><html:text  styleClass="text" readonly="true" property="chargeAmount" styleId="chargeAmount" style="text-align: right;" value="${AuthorData[0].chargeAmount}" maxlength="22" disabled="true"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" property="waivOffAmount" styleId="waivOffAmount" style="text-align: right;" value="${AuthorData[0].waivOffAmount}" maxlength="22" onkeypress="return isNumberKey(event);" onchange="return newAmount1();" disabled="true"/></td>
		    <td nowrap="nowrap"><html:text  styleClass="text" property="ChargeNewAmount" styleId="ChargeNewAmount" style="text-align: right;" value="${AuthorData[0].newChargeAmt}" maxlength="22" onkeypress="return isNumberKey(event);" disabled="true"/></td>
		</tr>
		
		<tr class="white1">   
		    <td nowrap="nowrap"><bean:message key="lbl.taxAmount1"/>  </td>
		    <td nowrap="nowrap"><html:text  styleClass="text" readonly="true" property="taxAmount1" styleId="taxAmount1" style="text-align: right;" value="${AuthorData[0].taxAmount1}" maxlength="22" disabled="true"/></td>
		  <td nowrap="nowrap"><html:text  styleClass="text" property="waveAmountForTaxAmt1" styleId="waveAmountForTaxAmt1" style="text-align: right;" value="${AuthorData[0].waveAmountForTaxAmt1}" maxlength="22" onkeypress="return isNumberKey(event);" onchange="return newAmount2();" disabled="true"/></td>
		    <td nowrap="nowrap"><html:text  styleClass="text" property="tax1NewAmt" styleId="tax1NewAmt" style="text-align: right;" value="${AuthorData[0].newTaxAmt1}" maxlength="22" onkeypress="return isNumberKey(event);" disabled="true"/></td>
		</tr>
		
		<tr class="white1">		  
		    <td nowrap="nowrap"><bean:message key="lbl.taxAmount2"/>  </td>
		    <td nowrap="nowrap"><html:text  styleClass="text" readonly="true" property="taxAmount2" styleId="taxAmount2" style="text-align: right;" value="${AuthorData[0].taxAmount2}" maxlength="22" disabled="true"/></td>
			<td nowrap="nowrap"><html:text  styleClass="text" property="waveAmountForTaxAmt2" styleId="waveAmountForTaxAmt2" style="text-align: right;" value="${AuthorData[0].waveAmountForTaxAmt2}" maxlength="22" onkeypress="return isNumberKey(event);" onchange="return newAmount3();" disabled="true"/></td>
		    <td nowrap="nowrap"><html:text  styleClass="text" property="tax2NewAmt" styleId="tax2NewAmt" style="text-align: right;" value="${AuthorData[0].newTaxAmt2}" maxlength="22" onkeypress="return isNumberKey(event);" disabled="true"/></td>
		</tr>
		<tr class="white1">
		 <td nowrap="nowrap"><bean:message key="lbl.AdviceAmount"/>  </td>
		    <td nowrap="nowrap"><html:text  styleClass="text" disabled="true" property="adviceAmount" styleId="adviceAmount" style="text-align: right;" value="${AuthorData[0].adviceAmount}" maxlength="22"/></td>
		  	   
		    <td nowrap="nowrap"><html:text  styleClass="text" property="totalWaveOffAmt" styleId="totalWaveOffAmt" style="text-align: right;width:150px !important;" value="${AuthorData[0].totalWaiveOffAmt}" maxlength="22" onkeypress="return isNumberKey(event);" disabled="true"/></td>
		    <td nowrap="nowrap"><html:text  styleClass="text" property="newAdviceAmt" styleId="newAdviceAmt" style="text-align: right;" value="${AuthorData[0].newAdviceAmt}" maxlength="22" onkeypress="return isNumberKey(event);" disabled="true"/></td>
		</tr>
		
		
<!--	Change from here End	-->		
		
		  <tr class="white1">		  
		    
		  <td nowrap="nowrap"><bean:message key="lbl.adjustedAmount"/>  </td>
		    <td nowrap="nowrap"><html:text  styleClass="text" readonly="true" property="txnAdjustedAmount" styleId="txnAdjustedAmount" style="text-align: right;" value="${AuthorData[0].txnAdjustedAmount}" maxlength="22" disabled="true"/></td>
		  	<td></td>
		  	<td></td>
		     </tr>
		     
		       <tr class="white1">
		    	<td><bean:message key="lbl.amountInProcess"/></td>
		  	<td><html:text  styleClass="text" disabled="true" property="amountInProcess" styleId="amountInProcess" style="text-align: right;" value="${AuthorData[0].amountInProcess}" maxlength="22" tabindex="-1"/></td>
		   <td></td>
		   <td></td>
		    </tr>
		     
		    <tr class="white1">
			<td nowrap="nowrap"><bean:message key="lbl.balanceAmount"/></td>
		    <td nowrap="nowrap">
		    <html:text styleClass="text" property="balanceAmount" disabled="true" styleId="balanceAmount" style="text-align: right;" value="${AuthorData[0].balanceAmount}" maxlength="50"/></td>
		   
		  <td>
<!--		  <bean:message key="lbl.waiveOffAmount"/><font color="red">*</font>-->
		   </td>
		  <td>
		  <html:text property="newBalanceAmt" styleId="newBalanceAmt" readonly="true" tabindex="-1" value="${AuthorData[0].newBalanceAmount}" maxlength="22" style="text-align: right;"
 				onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/>
<!--			<input type="text" id="newBalanceAmt" readonly="readonly" tabindex="-1" value="${AuthorData[0].newAdviceAmt}" maxlength="22" style="text-align: right;"-->
<!--		    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/>-->
		  </td>
		  <input type="hidden" name="txnAdviceId" id="txnAdviceId"  value=""/>
		  
		  </tr>
		  </table>
		  </td>
		  </tr>
		  
		 <tr><td></td></tr>
		 
		 
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		  <tr >
		  <td width="25%"> <bean:message key="lbl.makerRemark"/></td>
		  <td width="25%"><label>
		 <textarea name="remarks" disabled="disabled" id="remarks" >${AuthorData[0].remarks}</textarea>
          </label></td>
			
			<td width="25%"><bean:message key="lbl.authorRemarks" /></td>
			<td width="25%"><html:textarea styleClass="text" property="authorRemarks" disabled="true"
			value="${AuthorData[0].authorRemarks}" /></td>
          </tr>      
		</table>
		
	     </td></tr>
		</table>
		
	</fieldset>
	
</html:form>
	</logic:present>
	</div>

</div>
<script>
	setFramevalues("waiveOffMakerFormCSE");
</script>
</body>

</html:html>