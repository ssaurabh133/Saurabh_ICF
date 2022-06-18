<%--
      Author Name-     Prashant  Kumar
      Date of creation -20/04/2011
      Purpose-          Entry of Collateral Detail
      Documentation-     
      
 --%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
	<html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/report/repayReports.js"></script>

	</head>
<BODY oncontextmenu="return false" onload="enableAnchor();checkChanges('repaymentSchedule');">

			<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
	    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
				<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
			
<DIV id=centercolumn>
<DIV id=revisedcontainer>
<FORM action="" method=post name="repaymentSchedule" id="repaymentSchedule">
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
<FIELDSET>
<LEGEND><bean:message key="lbl.repaySDetail"/> </LEGEND>

<!--	<font color="red">LoanId: ${sessionScope.loanId }${sessionScope.maxIdInCM }</font>-->

<table cellspacing="0" cellpadding="1" border="0" width="100%">
					
				<tr>
				
				
					<td width="18%"><bean:message key="lbl.repaySflatRate"/></td>
					<td width="28%"><input type="text" class="text"name="Input" disabled="disabled" style="text-align: right" value="${fromloanDtl[0].finalRate}"/></td>
								
					<td width="16%"><bean:message key="lbl.repaySeffectRate"/> </td>
					<td width="13%"><input type="text" class="text" name="Input32" disabled="disabled" style="text-align: right" value="${fromloanDtl[0].effectiveRate}"/></td>
				    <td>&nbsp;</td>
			    </tr>
				
				<tr>
					<td><bean:message key="lbl.repaySMKTIRR1"/></td>
					<td><input type="text" class="text" name="Input3" disabled="disabled" value="${fromloanDtl[0].mktIRR1}" style="text-align: right"/></td>
					<td><bean:message key="lbl.repaySMKTIRR2"/></td>
					<td><input type="text" class="text" name="Input3" disabled="disabled" value="${fromloanDtl[0].mktIRR2}" style="text-align: right"/></td>
				</tr>
				<%-- add by saorabh --%>
				<tr>
				
					<td><bean:message key="lbl.upfront.rounding.amount"/></td>
					<td><input type="text" class="text" name="upfrontRoundingAmount" disabled="disabled" value="${fromloanDtl[0].upfrontRoundingAmount}" style="text-align: right"/></td>
				</tr>
				
				<%-- end by saorabh --%>
				<%-- 
				<tr>
				
					<td><bean:message key="lbl.bussIrr"/></td>
					<td><input type="text" class="text" name="Input3" disabled="disabled" value="${fromloanDtl[0].bussIrr}" style="text-align: right"/></td>
				</tr>
					--%>
				</table>
					
  
 
	  </FIELDSET>	
				
<fieldset>	
		 <legend><bean:message key="lbl.repaySDetail"/></legend>  

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        <td ><strong><bean:message key="lbl.repaySIns"/></strong></td>
		<td ><strong><bean:message key="lbl.repaySDue"/></strong></td>
        <td ><b><bean:message key="lbl.repaySInsAm"/> </b></td>
        <td><strong><bean:message key="lbl.repaySPrinc"/></strong></td>
        <td ><strong><bean:message key="lbl.repaySInterest"/></strong></td>
        <td ><strong><bean:message key="lbl.repaySExcess"/></strong></td>
        <!-- add by saorabh -->
        <td ><strong><bean:message key="lbl.vatAmount"/></strong></td>
        <td ><strong><bean:message key="lbl.serviceAmount"/></strong></td>
       <!--  end by saorabh -->
        <td ><strong><bean:message key="lbl.otherCharges"/></strong></td>
        <td ><strong><bean:message key="lbl.repaySPrincOS"/></strong></td>
        <td ><strong><bean:message key="lbl.repaySAdF"/></strong></td>
        
	</tr>
	<logic:present name="repShedule">
	  <logic:notEmpty name="repShedule">
		  <logic:iterate name="repShedule" id="repSheduleOb">
			<tr class="white1">
		     <td  style="text-align: right">${repSheduleOb.instNo}</td>
			 <td >${repSheduleOb.dueDate}</td>
		     <td style="text-align: right">${repSheduleOb.instAmount}</td>
			 <td style="text-align: right">${repSheduleOb.principle}</td>
			 <td style="text-align: right">${repSheduleOb.instCom}</td>
		      <td style="text-align: right" >${repSheduleOb.excess}</td>
		       <!-- add by saorabh -->
		       <td style="text-align: right" >${repSheduleOb.vatAmount}</td>
		       <td style="text-align: right" >${repSheduleOb.serviceAmount}</td>
		         <!--  end by saorabh -->
		      <td style="text-align: right" >${repSheduleOb.otherCharges}</td>
		      <td style="text-align: right">${repSheduleOb.prinOS}</td>
		      <td >${repSheduleOb.advFlag}</td>
		     </tr>
	     </logic:iterate>
     </logic:notEmpty>
     </logic:present>
     </table>    </td>
</tr>
<tr>
	<td>
		<logic:present name="dealId">
			<input type="hidden" name="repayAtCP" id="repayDealNo" value='${dealId}'/>
			<button type="button" name=" mybutton"  title="Alt+G" accesskey="G" class="topformbutton3" onclick="generateReport('deal_repayment_calculation');"  ><bean:message key="button.generate" /></button>
		</logic:present>
		<logic:present name="loanId">
			<input type="hidden" name="repayAtCM" id="repayLoanNo" value='${loanId}'/>
			<button type="button" name=" mybutton"  title="Alt+G" accesskey="G" class="topformbutton3" onclick="generateReport('rp_repayment_calculation');"  ><bean:message key="button.generate" /></button>
		</logic:present>
	</td>
</tr>
</table>

	</fieldset>
		
</FORM></DIV></DIV>

<script>
	setFramevalues("repaymentSchedule");
</script>


</BODY></HTML>
<logic:present name="resultproc">
   <logic:notEmpty name="resultproc">
		<script type="text/javascript">
		if('<%=request.getAttribute("resultproc")%>'!='S')
		{
		   	alert('<%=request.getAttribute("resultproc")%>');
			
		}
		</script>
	</logic:notEmpty>
</logic:present>
<logic:present name="procval">
     <logic:notEmpty name="procval">
		<script type="text/javascript">
		if('<%=request.getAttribute("procval")%>'!='S')
		{
		   	alert('<%=request.getAttribute("procval")%>');
			
		}
		</script>
	</logic:notEmpty>
</logic:present>
<logic:present name="back">
	<script type="text/javascript">
	
			alert("<bean:message key="lbl.canNotMove" />");
			window.close();
</script>
</logic:present>
		<logic:present name="nonProduct">

	<script type="text/javascript">
	
			alert("<bean:message key="lbl.NotInstallProduct" />");
			window.close();
	
		
</script>
</logic:present>