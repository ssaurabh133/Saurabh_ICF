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

<logic:notPresent name="ViewRSPRepayment">
<fieldset>


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
	          <td >RSP Repayment Schedule Capturing</td>
          </tr>
        </table>
</td>
</tr>
</table>

</fieldset>	
</logic:notPresent>
<FIELDSET>
<LEGEND><bean:message key="lbl.repaySDetail"/> </LEGEND>

<!--	<font color="red">LoanId: ${sessionScope.loanId }${sessionScope.maxIdInCM }</font>-->

<table cellspacing="0" cellpadding="1" border="0" width="100%">
					
				<tr>
			
								
					<td width="16%"><bean:message key="lbl.repaySeffectRate"/> </td>
					<td width="13%"><input type="text" class="text" name="Input32" disabled="disabled" style="text-align: right" value="${effectiveRate}"/></td>
				    <td>&nbsp;</td>
			    </tr>
				
				
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

       <!--  end by saorabh -->

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

		         <!--  end by saorabh -->
		      <td style="text-align: right">${repSheduleOb.prinOS}</td>
		      <td >${repSheduleOb.advFlag}</td>
		     </tr>
	     </logic:iterate>
     </logic:notEmpty>
     </logic:present>
     </table>    </td>
</tr>

</table>

	</fieldset>
		
</FORM></DIV></DIV>

<script>
	setFramevalues("repaymentSchedule");
</script>


</BODY></HTML>

<logic:present name="procval">
     <logic:notEmpty name="procval">
		<script type="text/javascript">
		if('<%=request.getAttribute("procval")%>'=='S')
		{
		   	alert('Date Saved Sucessfully');
			
		}
		else if('<%=request.getAttribute("procval")%>'=='ONEMUSTROW')
		{
		   	alert('Save Installment Plan First');
			
		}
		else if('<%=request.getAttribute("procval")%>'=='ONEMUSTLEADPARTNER')
		{
		   	alert('Select At Least One Lead Partner');
			
		}
		else
			{
			alert('<%=request.getAttribute("procval")%>');
			}
		</script>
	</logic:notEmpty>
</logic:present>
