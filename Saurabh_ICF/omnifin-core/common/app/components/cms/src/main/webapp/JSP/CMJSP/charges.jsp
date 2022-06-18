<%--
      Author Name-      Prashant Kumar
      Date of creation -04/05/2011
      Purpose-          Entry of Charges
      Documentation-     
      
 --%>

<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
     
       SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
       Date currentDate = new Date();
       

%>

<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
		 
		 <!-- css for Datepicker -->
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/demos/demos.css" rel="stylesheet" />
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	<!-- jQuery for Datepicker -->
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
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
<BODY oncontextmenu="return false" onload="enableAnchor();checkChanges('ChrageForm');">
<DIV id=centercolumn>
<DIV id=revisedcontainer>
<logic:notPresent name="cmAuthor">
<html:form action="/chargesProcessAction" method="post" styleId="ChrageForm">
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<input type="hidden" name="roundType" id="roundType"  value="${roundType }" />
<input type="hidden" name="roundPara" id="roundPara"  value="${roundPara }" />	
<input type="hidden" name="source" id="source"  value="${source}"/>	

<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
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
 
</fieldset>	
<FIELDSET><LEGEND><bean:message key="lbl.charges" /></LEGEND>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">

				<table width="100%" border="0" cellspacing="1" cellpadding="1">
							<tr class="white2">
							
							     <td><b><bean:message key="lbl.chargeType" /></b></td>
								 <td><b><bean:message key="lbl.chargeCode" /></b></td>									
                                 <td><b><bean:message key="lbl.chargeDesc" /></b></td>							
								 <td><b><bean:message key="lbl.chargeBPT" /></b></td>
								 <td><B><bean:message key="lbl.chargeBPN" /></b></td>								 
								 <td><b><bean:message key="lbl.taxIncl" /></b></td>							
								 <td><b><bean:message key="lbl.taxRate1" /></b></td>
								 <td><B><bean:message key="lbl.taxRate2" /></b></td>								 
								 <td><B><bean:message key="lbl.bpMethod" /></b></td>
                                 <td><B><bean:message key="lbl.chargeAmount" /></b></td>
								 <td><B><bean:message key="lbl.chargeFinal" /></b></td>
								
							</tr>			
							
						<logic:present name="charges">	
							<logic:notEmpty name="charges">
						<logic:iterate name="charges" id="subcharges" indexId="i" >
						
							
							
						<tr  class="white1">
							<input type="hidden" name="loanAmount" id="loanAmounts" value="${subcharges.loanAmount}"/>
							<input type="hidden" name="loanAmount" id="loanAmount${i}" value="${subcharges.loanAmount}"/>
							<input type="hidden" name="marginAmount" id="marginAmount${i}" value="${subcharges.marginAmount}"/>
							<input type="hidden" name="calCulatedOn" id="calCulatedOn${i}" value="${subcharges.chargeCalculatedOn}"/>
							<input type="hidden" name="chargeMethod" id="chargeMethod${i}" value="${subcharges.chargeMethod}"/>
							<input type="hidden" name="minChargeMethod" id="minChargeMethod${i}" value="${subcharges.minChargeMethod}"/>
							<input type="hidden" name="minChargeCalculatedOn" id="minChargeCalculatedOn${i}" value="${subcharges.minChargeCalculatedOn}"/>
							<input type="hidden" name="taxsInclusive" id="taxsInclusive${i}" value="${subcharges.taxsInclusive}"/>
							<input type="hidden" name="taxtRat1" id="taxtRat1${i}" value="${subcharges.taxtRat1}"/>
							<input type="hidden" name="taxtRat2" id="taxtRat2${i}" value="${subcharges.taxtRat2}"/>
							<input type="hidden" name="dealChargeTaxApp" id="dealChargeTaxApp${i}" value="${subcharges.dealChargeTaxApp}"/>
							<input type="hidden" name="dealChargeTdsApp" id="dealChargeTdsApp${i}" value="${subcharges.dealChargeTdsApp}"/>
							<input type="hidden" name="dealChargeTaxAmountInConfig1" id="dealChargeTaxAmount1${i}" value="${subcharges.dealChargeTaxAmount1}"/>
							<input type="hidden" name="dealChargeTaxAmountInConfig2" id="dealChargeTaxAmount2${i}" value="${subcharges.dealChargeTaxAmount2}"/>
							<input type="hidden" name="dealChargeMinChargeAmount" id="dealChargeMinChargeAmount${i}" value="${subcharges.dealChargeMinChargeAmount}"/>
							<input type="hidden" name="dealChargeTdsRate" id="dealChargeTdsRate${i}" value="${subcharges.dealChargeTdsRate}"/>
							<input type="hidden" name="dealChargeTdsAmountInConfig" id="dealChargeTdsAmount${i}" value="${subcharges.dealChargeTdsAmount}"/>
							<input type="hidden" name="dealChargeNetAmountInConfig" id="dealChargeNetAmount${i}" value="${subcharges.dealChargeNetAmount}"/>
							<input type="hidden" name="chargeDescription" id="chargeDesc${i}" value="${subcharges.chargeDesc}"/>
							<input type="hidden" name="chargeCode" id="chargeCode${i}" value="${subcharges.chargeCode}" />
								
							
							    <html:hidden property="chargeIdDtl" value="${subcharges.chargeId}" />
							    <td >${subcharges.chargeType}</td>							
								<td>${subcharges.chargeCode}</td>
								<td>${subcharges.chargeDesc}</td>
								<td>${subcharges.chargeBPType}</td>					
								<td>${subcharges.chargeBPId}</td>
								<td>${subcharges.taxsInclusive}</td>
								<td>${subcharges.taxtRat1}</td>					
								<td>${subcharges.taxtRat2}</td>
								<td>${subcharges.chargeMethod}</td>
						<html:hidden styleClass="text" 	styleId="hiddenChargeAmount${i}" property="hiddenChargeAmount"	value="${subcharges.chargeCal}"	/>
						<html:hidden styleClass="text" 	styleId="hiddenFinalAmount${i}" property="hiddenFinalAmount" value=" ${subcharges.chargeFinal}" />
																	
						<logic:equal name="subcharges" property="chargeMethod" value="FLAT">
							<td ><html:text styleClass="text" style="text-align: right" styleId="chargeAmount${i}" property="chargeAmount" value="${subcharges.chargeCal}" onkeypress="return numbersonly(event, id, 18);" onblur="formatNumber(this.value, id);calculateFinalCharge('chargeAmount${i}','loanAmount${i}','marginAmount${i}','calCulatedOn${i}','chargeMethod${i}','finalAmount${i}','minChargeCalculatedOn${i}','minChargeMethod${i}','taxsInclusive${i}','taxtRat1${i}','taxtRat2${i}','dealChargeTaxApp${i}','dealChargeTdsApp${i}','dealChargeTaxAmount1${i}','dealChargeTaxAmount2${i}','dealChargeMinChargeAmount${i}','dealChargeTdsRate${i}','dealChargeTdsAmount${i}','dealChargeNetAmount${i}','hiddenChargeAmount${i}','hiddenFinalAmount${i}');" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" /></td>
						</logic:equal>
						<logic:equal name="subcharges" property="chargeMethod" value="PERCENTAGE">
							<td><html:text styleClass="text" style="text-align: right" styleId="chargeAmount${i}" property="chargeAmount" value="${subcharges.chargeCal}" onkeypress="return fourDecimalnumbersonly(event, id, 3);" onblur="fourDecimalNumber(this.value, id);calculateFinalCharge('chargeAmount${i}','loanAmount${i}','marginAmount${i}','calCulatedOn${i}','chargeMethod${i}','finalAmount${i}','minChargeCalculatedOn${i}','minChargeMethod${i}','taxsInclusive${i}','taxtRat1${i}','taxtRat2${i}','dealChargeTaxApp${i}','dealChargeTdsApp${i}','dealChargeTaxAmount1${i}','dealChargeTaxAmount2${i}','dealChargeMinChargeAmount${i}','dealChargeTdsRate${i}','dealChargeTdsAmount${i}','dealChargeNetAmount${i}','hiddenChargeAmount${i}','hiddenFinalAmount${i}');" onkeyup="fourDecimalcheckNumber(this.value, event, 3, id);" onfocus="fourDecimalkeyUpNumber(this.value, event, 3, id);" /></td>
						</logic:equal>
					
						<td>
						<logic:equal name="subcharges" property="applStage" value="LIM">
						<logic:equal name="subcharges" property="chargeMethod" value="FLAT">
							<html:text styleClass="text" style="text-align: right" styleId="finalAmount${i}" property="finalAmount" value=" ${subcharges.chargeFinal}" onkeypress="return numbersonly(event, id, 18);" onblur="formatNumber(this.value, id);calculateChargeAmount('chargeCode${i}','chargeAmount${i}','loanAmount${i}','marginAmount${i}','calCulatedOn${i}','chargeMethod${i}','finalAmount${i}','minChargeCalculatedOn${i}','minChargeMethod${i}','taxsInclusive${i}','taxtRat1${i}','taxtRat2${i}','dealChargeTaxApp${i}','dealChargeTdsApp${i}','dealChargeTaxAmount1${i}','dealChargeTaxAmount2${i}','dealChargeMinChargeAmount${i}','dealChargeTdsRate${i}','dealChargeTdsAmount${i}','dealChargeNetAmount${i}','hiddenChargeAmount${i}','hiddenFinalAmount${i}');" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/>
						</logic:equal>
						<logic:equal name="subcharges" property="chargeMethod" value="PERCENTAGE">
							<html:text styleClass="text" style="text-align: right" styleId="finalAmount${i}" property="finalAmount" value=" ${subcharges.chargeFinal}"  onkeypress="return numbersonly(event, id, 18);" onblur="return calculateChargeAmount('chargeCode${i}','chargeAmount${i}','loanAmount${i}','marginAmount${i}','calCulatedOn${i}','chargeMethod${i}','finalAmount${i}','minChargeCalculatedOn${i}','minChargeMethod${i}','taxsInclusive${i}','taxtRat1${i}','taxtRat2${i}','dealChargeTaxApp${i}','dealChargeTdsApp${i}','dealChargeTaxAmount1${i}','dealChargeTaxAmount2${i}','dealChargeMinChargeAmount${i}','dealChargeTdsRate${i}','dealChargeTdsAmount${i}','dealChargeNetAmount${i}','hiddenChargeAmount${i}','hiddenFinalAmount${i}');" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/>
						</logic:equal>
						</logic:equal>
						<logic:notEqual name="subcharges" property="applStage" value="LIM">
							<html:text styleClass="text" style="text-align: right" styleId="finalAmount${i}" property="finalAmount" value=" ${subcharges.chargeFinal}" readonly="true"/>
						</logic:notEqual>
							
						</td>	
						</tr>
						</logic:iterate>
						<tr class="white1">
						        <td>Receivable</td>
								<td colspan="8" align="center">TOTAL CUSTOMER CHARGES</td>
								<td>${customerCharge}</td>
								<td>${customerFinalCharge}</td>
						</tr>
						</logic:notEmpty>
					</logic:present>
			
			</table> 
			 <tr>
      <td colspan="4">
     
       <!--  <html:button property="saveButton" styleId="saveButton" value="Save" styleClass="topformbutton2" onclick="saveCharges();" accesskey="V" title="Alt+V"/>
        <html:button property="refreshButton" value="Refresh" styleClass="topformbutton2" onclick="refreshChargesInCm();" accesskey="R" title="Alt+R"/>
        -->
        <button type="button" class="topformbutton2" name="saveButton"  id="saveButton"  onclick="saveCharges();"
 		title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
		<button type="button" class="topformbutton2" name="refreshButton" onclick="refreshChargesInCm();"
 		title="Alt+R" accesskey="R" ><bean:message key="button.refresh" /></button>
      </td>
      
    </tr>
</td></tr>
	
</table>
</FIELDSET> 
<logic:present name="sms">
<script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert('<bean:message key="lbl.chargeSuccess" />');
		document.getElementById("source").value='N';
	}
	else
	{
		alert('<bean:message key="lbl.chargeError" />');
	}	
</script>
</logic:present>  
</html:form>
</logic:notPresent>

<%--					Loan Initiation for Author															 --%>

<logic:present name="cmAuthor">
	<html:form action="/chargesProcessAction" method="post"  styleId="ChrageForm">
<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
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
 
</fieldset>	
<FIELDSET><LEGEND><bean:message key="lbl.charges" /></LEGEND>

	

<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">

			<table width="100%" border="0" cellspacing="1" cellpadding="1">
							<tr class="white2">
							
							  <td  ><b><bean:message key="lbl.chargeType" /></b></td>
								 <td ><b><bean:message key="lbl.chargeCode" /></b></td>									
                                <td><b><bean:message key="lbl.chargeDesc" /></b></td>							
								 <td ><b><bean:message key="lbl.chargeBPT" /></b></td>
								 <td ><B><bean:message key="lbl.chargeBPN" /></b></td>
								 
								 <td><b><bean:message key="lbl.taxIncl" /></b></td>							
								 <td ><b><bean:message key="lbl.taxRate1" /></b></td>
								 <td ><B><bean:message key="lbl.taxRate2" /></b></td>
								 
								 <td ><B><bean:message key="lbl.bpMethod" /></b></td>
                                <td ><B><bean:message key="lbl.chargeAmount" /></b></td>
								 <td ><B><bean:message key="lbl.chargeFinal" /></b></td>
								
							</tr>
										
						<logic:present name="dealCharges">	
						
						<logic:iterate name="dealCharges" id="subdealCharges">
							<tr class="white1">
							    <html:hidden property="chargeIdDtl" value="${subdealCharges.chargeId}" />
							    <td  >${subdealCharges.chargeType}</td>							
								<td  >${subdealCharges.chargeCode}</td>
								<td >${subdealCharges.chargeDesc}</td>
								<td>${subdealCharges.chargeBPType}</td>					
								
								<td >${subdealCharges.chargeBPId}</td>
								<td >${subdealCharges.chargeFinal}</td>
								<td ><html:text styleClass="text" styleId="finalAmount" property="finalAmount" disabled="true" value="${subdealCharges.chargeFinal}" style="text-align: right" /></td>
								
						</tr>
						</logic:iterate>
					</logic:present>
						
						<logic:present name="charges">	
						
						<logic:iterate name="charges" id="subcharges">
							<tr class="white1">
							    <html:hidden property="chargeIdDtl" value="${subcharges.chargeId}" />
							    <td >${subcharges.chargeType}</td>							
								<td>${subcharges.chargeCode}</td>
								<td  >${subcharges.chargeDesc}</td>
								<td  >${subcharges.chargeBPType}</td>					
								<td  >${subcharges.chargeBPId}</td>
								<td  >${subcharges.taxsInclusive}</td>
								<td  >${subcharges.taxtRat1}</td>					
								<td  >${subcharges.taxtRat2}</td>
								<td  >${subcharges.chargeMethod}</td>
								<td  ><html:text styleClass="text" styleId="chargeAmount${i}" property="chargeAmount" disabled="true" value="${subcharges.chargeCal}" onkeypress="return numbersonly(event, id, 18);" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);calculateFinalCharge('chargeAmount${i}','loanAmount${i}','marginAmount${i}','calCulatedOn${i}','chargeMethod${i}','finalAmount${i}');" onfocus="keyUpNumber(this.value, event, 18, id);" style="text-align: right"/></td>
								<td  ><html:text styleClass="text" styleId="finalAmount${i}" disabled="true" property="finalAmount" value=" ${subcharges.chargeFinal}" style="text-align: right" /></td>
								
						</tr>
						</logic:iterate>
						<tr class="white1">
								<td>Receivable</td>
								<td colspan="8" align="center">TOTAL CUSTOMER CHARGES</td>
								<td>${customerCharge}</td>
								<td>${customerFinalCharge}</td>
						</tr>
					</logic:present>
			
								</table> 
							
    
   
</td></tr>
	
</table>
</FIELDSET> 
  
</html:form>
</logic:present>
<logic:present name="saveCharge">
<script type="text/javascript">
	if('<%=request.getAttribute("saveCharge").toString()%>'=='C')
	{
		alert("<bean:message key="lbl.chargeMandatory" />");
	}
	
</script>
</logic:present> 
</DIV></DIV>
<script>
	parent.menu.document.test.getFormName.value = document.getElementById("ChrageForm").id;
</script>
<div align="center" class="opacity" style="display: none;"
		id="processingImage">
</div>
</BODY></html:html>

