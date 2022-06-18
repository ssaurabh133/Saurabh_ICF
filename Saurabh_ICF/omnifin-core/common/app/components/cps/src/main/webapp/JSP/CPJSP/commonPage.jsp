<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@page import="com.login.roleManager.UserObject"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
	
	<style type="text/css">

input.readonly {
	background:#ebebe4;
	border:solid 1px #7f9db9;
	width:70px;
	}

</style>
	
	<%
		String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":"
					+ request.getServerPort() + path + "/";

			UserObject userobj = (UserObject) session
					.getAttribute("userobject");
			String initiationDate = userobj.getBusinessdate();
	%>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
       <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
	   
		 
		<!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->

		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
  		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		
    	<!--jquery and js for number formatting -->
  	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/jquery.emicalculator.min.js"></script>
       <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/globalize.min.js"></script>
        <%-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/fundFlowAnalysis.js"></script> --%>
      <!-- jquery and js for number formatting -->
      
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
<body oncontextmenu="return false" onload="enableAnchor();checkChanges('underWriterForm');document.getElementById('underWriterForm').viewDetails.focus();" onunload="closeAllWindowCallUnloadBody();">

<div  align="center" class="opacity" style="display: none;"  id="processingImage">
</div>
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<input type="hidden" name="dealExposureAmount" id="dealExposureAmount" value="${dealExposureAmount}" />
<input type="hidden" name="dealExLoanAmount" id="dealExLoanAmount" value="${dealExLoanAmount}" />
<input type="hidden" name="exposureLimit" id="exposureLimit" value="${exposureLimit}" />
<input type="hidden" name="uwCustomerType" id="uwCustomerType" value="${UWcustomerType}" />
<input type="hidden" name="groupNameText" id="groupNameText" value="${groupNameText}" />
<input type="hidden" name="groupType" id="groupType" value="${UWcustomerGroupType}" />
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
    		<input type="hidden" id="schemeMinAmt" value='${dealHeader[0].schemeMinAmt}'/>
    		<input type="hidden" id="schemeMaxAmt" value='${dealHeader[0].schemeMaxAmt}'/>

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
	          <td >Under Writer</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>
	
	
   <html:form action="/underWriting" styleId="underWriterForm" method="post">
<fieldset>
<legend><bean:message key="lbl.applicationDetails" />
</legend><table cellspacing="0" cellpadding="0" width="100%" border="0">
  <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>"/>
  <html:hidden styleId="dealId" property="dealId" value="${sessionScope.dealId}"/>

  <tr>
    <td>
      <table cellspacing="1" cellpadding="2" width="100%" border="0">
        <tr>
          <td><bean:message key="lbl.dealNo" /></td>
          <td ><html:text property="dealNo" disabled="true" styleClass="text" styleId="dealNo" maxlength="100" value="${underWriterList[0].dealNo}"/></td>
          <td><bean:message key="lbl.applicationFormNo" /></td>
          <td><html:text property="applicationFormNo" disabled="true" styleClass="text" styleId="applicationFormNo" value="${underWriterList[0].applicationFormNo}" /></td>
		  </tr>
        <tr>
          
          <td><bean:message key="lbl.customerName" /></td>
          <td><html:text property="customerName" disabled="true" styleClass="text" styleId="customerName" value="${underWriterList[0].customerName}" maxlength="100" /></td>
          <td><bean:message key="lbl.sourcingPartnerType" /></td>
          <td>
          <html:text property="sourcingPartnerType" disabled="true" styleClass="text" styleId="sourcingPartnerType" value="${underWriterList[0].sourcingPartnerType}" maxlength="100" />
          </td></tr>
          <tr>
          
          <td><bean:message key="lbl.sourcingPartnerName" /></td>
          <td><html:text property="sourcingPartnerName" disabled="true" styleClass="text" styleId="sourcingPartnerName" value="${underWriterList[0].sourcingPartnerName}" maxlength="100" /></td>
          <td><bean:message key="lbl.dealRm" /></td>
          <td><html:text property="dealRm" styleId="dealRm" disabled="true" maxlength="100" styleClass="text" value="${underWriterList[0].dealRm}"/></td>
          </tr>
      
	  </table></td></tr>
 
   
</table>
</fieldset>
<fieldset>
<legend><bean:message key="lbl.creditAnalysis" /></legend>

<div>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">      
     	<table width="100%" border="0" cellspacing="1" cellpadding="4">
        <tr class="white2">
        	<td width="10%"><b><bean:message key="lbl.underType" /></b></td>
    	 	<td width="10%"><b><bean:message key="lbl.result" /></b></td>
     	 	<td width="10%"><b><bean:message key="lbl.approver" /></b></td>
        </tr>
        <tr class="white1">
        	<td>
	        	<logic:present name="Positive"><a href="#" onclick="viewContactVerification();"><bean:message key="lbl.verification.details" /></a></logic:present>
     			<logic:notPresent name="Positive"><bean:message key="lbl.verification.details" /></logic:notPresent >
     		</td>
     		<td>
	        	<logic:present name="Positive">
              		<logic:equal value="P" name="Positive" >Positive</logic:equal>
              		<logic:equal value="N" name="Positive">Negative</logic:equal>
              		<logic:equal value="R" name="Positive">Refer</logic:equal>
        		</logic:present>
    			<logic:notPresent name="Positive">
    				<logic:notPresent name="fviStatus">Not Initiated</logic:notPresent>
    			</logic:notPresent>
	        	<logic:notPresent name="Positive">
	        		<logic:present name="fviStatus">
              			<logic:equal value="NI" name="fviStatus" >Pending</logic:equal>
              			<logic:equal value="W" name="fviStatus" >Waived</logic:equal>
          			</logic:present>
        		</logic:notPresent>
    		</td>
    		<td>${underWriterList[0].fieldRemarks}</td>
	    </tr>
	    <tr  class="white1">
    		<td>
    			<logic:present name="DevPositive"><a href="#" onclick="return viewDeviation();"><bean:message key="lbl.dev" /></a></logic:present>
    			<logic:notPresent name="DevPositive"><bean:message key="lbl.dev" /></logic:notPresent>
    		</td>   
    		<td> 
    			<logic:present name="DevPositive">Yes</logic:present>
    			<logic:notPresent name="DevPositive">No Deviation</logic:notPresent>
    		</td>
      		<td> </td>
        </tr>
        </table>    
     </td>
  </tr>
</table> 
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<logic:notPresent name="strFlagDV">
 <tr >

 	<td nowrap="nowrap">
 	    <button type="button" name="viewDetails" id="viewDetails" title="Alt+V" accesskey="V" class="topformbutton2" onclick="callOnLinkOrButtonWindow('viewDetails');openStatus(${sessionScope.dealId});"><bean:message key="button.viewdeal"/></button>
<logic:notEqual name="functionId" value="500000123">
 	    <button type="button" name="custDetails" id="custDetails" title="Alt+B" accesskey="B" class="topformbutton3" onclick="callOnLinkOrButtonWindow('custDetails');openCustEntry(${sessionScope.dealId});"><bean:message key="button.businessDetail"/></button>
 	    <logic:present name="countFundFlow">
 	    	<button type="button" name="viewFundFlow" id="viewFundFlow" title="Alt+V" accesskey="V" class="topformbutton4" onclick="callOnLinkOrButtonWindow('viewFundFlow');openFundFlow('${sessionScope.dealId}');"><bean:message key="lbl.viewFundFlow"/></button>
 	    </logic:present>
 		<logic:notPresent name="countFundFlow">
 	    	<button type="button" name="viewFundFlow" id="viewFundFlow" disabled="disabled" title="No Data Found" accesskey="V" class="topformbutton4" onclick="callOnLinkOrButtonWindow('viewFundFlow');openFundFlow('${sessionScope.dealId}');"><bean:message key="lbl.viewFundFlow"/></button>
 	    </logic:notPresent>
 	    <logic:present name="countFinancialAnalysis">
 	    	<button type="button" name="viewFinancial" id="viewFinancial" title="Alt+V" accesskey="V" class="topformbutton4" onclick="callOnLinkOrButtonWindow('viewFinancial');openFinancial('${sessionScope.dealId}','${underWriterList[0].customerType}');"><bean:message key="lbl.viewFinancialAnalysis"/></button>
 	    </logic:present>
 		<logic:notPresent name="countFinancialAnalysis">
 	    	<button type="button" name="viewFinancial" id="viewFinancial" disabled="disabled" title="No Data Found" accesskey="V" class="topformbutton4" onclick="callOnLinkOrButtonWindow('viewFinancial');openFinancial('${sessionScope.dealId}','${underWriterList[0].customerType}');"><bean:message key="lbl.viewFinancialAnalysis"/></button>
 	    </logic:notPresent>
</logic:notEqual>	
			<button type="button" name="facilityDetails" id="facilityDetails" title="Alt+F" accesskey="S" class="topformbutton4" onclick="callOnLinkOrButtonWindow('facilityDetails');openFacilityDetails('${sessionScope.dealId}');"><bean:message key="button.facilityDetails"/></button>
 		<button type="button" name="specialCondition" id="specialCondition" title="Alt+S" accesskey="S" class="topformbutton4" onclick="callOnLinkOrButtonWindow('specialCondition');openSpecialCondition('${sessionScope.dealId}');"><bean:message key="button.specialCondition"/></button>
<logic:notEqual name="functionId" value="500000123">	
 		<logic:present name="countRepayType">
 			<button type="button" name="disbursalSchedule" id="disbursalSchedule" title="Alt+S" accesskey="S" class="topformbutton4" onclick="callOnLinkOrButtonWindow('disbursalSchedule');openDisbursalSchedule('${sessionScope.dealId}');"><bean:message key="button.disbursalSchedule"/></button>
 		</logic:present>
 		
 		<logic:notPresent name="countRepayType">
 			<button type="button" name="disbursalSchedule" id="disbursalSchedule" disabled="disabled" title="NON-INSTALLMENT BASED PRODUCT" accesskey="S" class="topformbutton4" onclick="callOnLinkOrButtonWindow('disbursalSchedule');openDisbursalSchedule('${sessionScope.dealId}');"><bean:message key="button.disbursalSchedule"/></button>
 		</logic:notPresent>
 		<button type="button" name="camReport" id="camReport" title="Alt+R" accesskey="R" class="topformbutton2" onclick="openCamReport();" ><bean:message key="button.camReport"/></button>
 		<button type="button" name="customerExposure" id="customerExposure" title="Alt+E" accesskey="E" class="topformbutton4" onclick="openCustomerExposure('DC','${sessionScope.dealId}');"><bean:message key="button.customerExposure"/></button>
		<button type="button" name="dedupeReferral" id="dedupeReferral" title="Alt+E" accesskey="E" class="topformbutton4" onclick="callDedupeReferral('DC','${sessionScope.dealId}');"><bean:message key="button.dedupeReferral"/></button>
</logic:notEqual>			


 	</td>
 </tr>
 </logic:notPresent>
 <logic:present name="strFlagDV">
 <tr >
 	<td nowrap="nowrap">
 	    <button type="button" name="viewDetails" id="viewDetails" title="Alt+V" accesskey="V" class="topformbutton2" onclick="callOnLinkOrButtonWindow('viewDetails');openStatus(${sessionScope.dealId});"><bean:message key="button.viewdeal"/></button>
 	    <button type="button" name="viewFundFlow" id="viewFundFlow" title="Alt+V" accesskey="V" class="topformbutton4" onclick="callOnLinkOrButtonWindow('viewFundFlow');openFundFlow('${sessionScope.dealId}');"><bean:message key="lbl.viewFundFlow"/></button>
 	    <button type="button" name="viewFinancial" id="viewFinancial" title="Alt+V" accesskey="V" class="topformbutton4" onclick="callOnLinkOrButtonWindow('viewFinancial');openFinancial('${sessionScope.dealId}','${underWriterList[0].customerType}');"><bean:message key="lbl.viewFinancialAnalysis"/></button>
 		<button type="button" name="facilityDetails" id="facilityDetails" title="Alt+F" accesskey="S" class="topformbutton4" onclick="callOnLinkOrButtonWindow('facilityDetails');openFacilityDetails('${sessionScope.dealId}');"><bean:message key="button.facilityDetails"/></button>
		<button type="button" name="specialCondition" id="specialCondition" title="Alt+S" accesskey="S" class="topformbutton4" onclick="callOnLinkOrButtonWindow('specialCondition');openSpecialCondition('${sessionScope.dealId}');"><bean:message key="button.specialCondition"/></button>
 		<button type="button" name="disbursalSchedule" id="disbursalSchedule" title="Alt+S" accesskey="S" class="topformbutton4" onclick="callOnLinkOrButtonWindow('disbursalSchedule');openDisbursalSchedule('${sessionScope.dealId}');"><bean:message key="button.disbursalSchedule"/></button>
 		<button type="button" name="camReport" id="camReport" title="Alt+R" accesskey="R" class="topformbutton2" onclick="openCamReport();" ><bean:message key="button.camReport"/></button>
 		<button type="button" name="customerExposure" id="customerExposure" title="Alt+E" accesskey="E" class="topformbutton4" onclick="openCustomerExposure('DC','${sessionScope.dealId}');"><bean:message key="button.customerExposure"/></button>
		<button type="button" name="dedupeReferral" id="dedupeReferral" title="Alt+E" accesskey="E" class="topformbutton4" onclick="callDedupeReferral('DC','${sessionScope.dealId}');"><bean:message key="button.dedupeReferral"/></button>
		</td>
 </tr>
 </logic:present>
</table>
</div>
</fieldset> 
<logic:notEqual name="functionId" value="500000123">	
  <fieldset>
<legend><bean:message key="lbl.creditDecision" /></legend>

<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="gridtd">
      
      <table width="100%" border="0" cellspacing="1" cellpadding="4">
        <tr class="white2">
<!--         <td width="90" class="white2" ><b><bean:message key="lbl.productType" /></b></td>-->
          <td  ><b><bean:message key="lbl.product" /></b></td>
           <td  ><b> <bean:message key="lbl.creditType" /></b></td>
            <td ><b><bean:message key="lbl.purpose" />  </b></td>
           <td  ><b><bean:message key="lbl.requestAmt" /> </b></td>
            <td  ><b><bean:message key="lbl.sancAmt" /></b></td>
            <td  ><b><bean:message key="lbl.sancDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></b></td>
           <td  ><b><bean:message key="lbl.underTenure" /> </b></td>
        	<td ><b><bean:message key="lbl.flatRate" /></b></td>
        	<td ><b><bean:message key="lbl.effRate" /></b></td>
        	<td ><b><bean:message key="lbl.bussIrr" /></b></td>
        	<td ><b><bean:message key="lbl.grossLtv" /></b></td>
        	<td ><b><bean:message key="lbl.foir" /></b></td>
           
      </tr>
      <logic:present name="creditDecision">
      <logic:iterate name="creditDecision" id="creditDecisionList">
      <html:hidden property="dealId" styleId="dealId" value="${creditDecisionList.dealId}" />
        <tr class="white1">
        <html:hidden property="dealLoanId" styleId="dealLoanId" value="${creditDecisionList.dealLoanId}" />
		<html:hidden property="minIRR" styleId="minIRR" value="${creditDecisionList.minIRR}" />
		<html:hidden property="maxIRR" styleId="maxIRR" value="${creditDecisionList.maxIRR}" />
		<html:hidden property="repayType" styleId="repayType" value="${creditDecisionList.repayType}" />
		
          <td >
          <input type="hidden" id="businessDate" value="<%=initiationDate %>" />
             ${creditDecisionList.product}
         </td>
          <td >${creditDecisionList.creditType}</td>
          <td >${creditDecisionList.purpose}</td>
          <td >
            ${creditDecisionList.requestAmt}
            <input type="hidden" id="requestAmt" value="${creditDecisionList.requestAmt}"/>
          </td>
           <logic:notPresent name="strFlagDV">
             <td><html:text property="sancAmt" styleClass="text3" style="text-align: right" maxlength="15"  styleId="sancAmt" value="${creditDecisionList.sancAmt}" onchange="numberFormatting(this.id,'2');validateSactionedLoanAmt();" /></td>
            <td> <html:text property="sancDate" styleClass="text3" maxlength="10"  styleId="sancDate" value="${creditDecisionList.sancDate}" onchange="checkDate('sancDate');" /></td>
          </logic:notPresent>
           <logic:present name="strFlagDV">
            <td>${creditDecisionList.sancAmt}</td>
            <td>${creditDecisionList.sancDate}</td>
           </logic:present>
             <td> <html:text property="underTenure" readonly="true" styleClass="text5" style="text-align: right" maxlength="5" size="20" styleId="underTenure" value="${creditDecisionList.underTenure}" onkeypress="return isNumberKey(event);"/></td>
            <td > <html:text property="emi" style="text-align: right" readonly="true" styleClass="text4" maxlength="60" size="20" styleId="finalRate" value="${creditDecisionList.emi}" onkeypress="return numbersonly(event, id, 3)" onblur="formatNumber(this.value, id);" onchange="checkNumber(this.value, event, 3, id);checkRate('finalRate');" onfocus="keyUpNumber(this.value, event, 3, id);" /></td>
            <td > <html:text property="effectiveRate" style="text-align: right" readonly="true" styleClass="text3" maxlength="60" size="20" styleId="effectiveRate" value="${creditDecisionList.effectiveRate}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
            <td > <html:text property="busIrr" style="text-align: right" readonly="true" styleClass="text3" maxlength="60" size="20" styleId="busIrr" value="${creditDecisionList.busIrr}" onkeypress="return numbersonly(event, id, 3)" onblur="formatNumber(this.value, id);" onchange="checkNumber(this.value, event, 3, id);checkRate('busIrr');" onfocus="keyUpNumber(this.value, event, 3, id);" /></td>
            <td > <html:text property="grossLtv" style="text-align: right" readonly="true" styleClass="text3" maxlength="999" size="20" styleId="grossLtv" value="${creditDecisionList.grossLtv}" onkeypress="return numbersonly(event, id, 3)" onblur="formatNumber(this.value, id);" onchange="checkNumber(this.value, event, 3, id);checkRate('busIrr');" onfocus="keyUpNumber(this.value, event, 3, id);" /></td>
            <td > <html:text property="foir" style="text-align: right" readonly="true" styleClass="text3" maxlength="999" size="20" styleId="foir" value="${creditDecisionList.foir}" onkeypress="return numbersonly(event, id, 3)" onblur="formatNumber(this.value, id);" onchange="checkNumber(this.value, event, 3, id);checkRate('busIrr');" onfocus="keyUpNumber(this.value, event, 3, id);" /></td>
           
        </tr>
        </logic:iterate>
        
<!--      <tr>-->
<!--          <td colspan="5" class="white" id="td2"><div align="center"><strong><bean:message key="lbl.totalSancAmt" /></strong></div></td>-->
<!--          <td class="white" id="td2" colspan="3">${creditDecisionList.sancAmtDisp}</td>-->
<!--          -->
<!--        </tr>-->
        </logic:present>
        <logic:notPresent name="creditDecision">
<!--	       	<tr>-->
<!--	          <td colspan="5" class="white" id="td2"><div align="center"><strong><bean:message key="lbl.totalSancAmt" /></strong></div></td>-->
<!--	          <td class="white" id="td2" colspan="3">${requestScope.sancAmtDisp}</td>-->
<!--	          -->
<!--	        </tr>-->
        </logic:notPresent>
      </table>    </td>
  </tr>
  <logic:notPresent name="strFlagDV">
  <tr><td><button type="button" name="update" id="update" title="Alt+V" accesskey="V" class="topformbutton2" onclick="return updateCreditDecisionValues();"><bean:message key="button.save" /></button></td></tr>
  </logic:notPresent>
  
</table>
</fieldset>
</logic:notEqual>		
  </html:form>
   </div> </div>
   
<logic:present name="sms">

 <script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert('<bean:message key="lbl.underSuccess" />');
	}
	else
	{
		alert('<bean:message key="lbl.underError" />');
	}	
</script>
</logic:present>

<logic:present name="checkStatus">

 <script type="text/javascript">
	if('<%=request.getAttribute("checkStatus").toString()%>'=='groupExposure')
	{
		alert('<bean:message key="msg.groupExposureDeal"/>');
	}
	else if('<%=request.getAttribute("checkStatus").toString()%>'=='customerExposure')
	{
		alert('<bean:message key="msg.customerExposureDeal"/>');
	}	
</script>
</logic:present>

<logic:present name="NonInstallDeal">

 <script type="text/javascript">
	
		alert('<bean:message key="lbl.NotInstallProduct"/>');
	
</script>

</logic:present>



<script>
	setFramevalues("underWriterForm");
</script>

  </body>
 

</html:html>
