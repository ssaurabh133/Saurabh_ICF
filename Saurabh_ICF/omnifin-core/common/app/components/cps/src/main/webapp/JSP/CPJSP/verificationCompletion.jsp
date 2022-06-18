<%@ page language="java" import="java.util.ResourceBundle"%>
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
	
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency-1.3.0.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency.all.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/gcdScript/customerDetail.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/fieldVerificationScript.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
<style>
		.watermark {
		    color:lightgray;
		}
		.adddate {
		    color: #000;
		}
</style>
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
	
<body onload="enableAnchor();return onLoadJsp();document.getElementById('fieldVerificationForm').referenceId.focus();">
<logic:present name="verifCPS">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
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
	          <td >Verification Capturing</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>
<html:form styleId="fieldVerificationForm" action="/fieldVerificationSave" method="post" >

<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<logic:present name="decision">	
	<fieldset>
	<legend><bean:message key="lbl.verification.details"/></legend>
			 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table id="gridtable" width="100%" border="0" cellspacing="1" cellpadding="1">
   
	 <tr class="white2">
	     <td width="3%"><input type="checkbox" name="allchkd" id="allchkd" onclick="allChecked();" /></td>
 		<td ><b><bean:message key="lbl.verification.type"/></b></td>
 		<td ><b><bean:message key="lbl.verification.subtype"/></b></td>
        <td ><b><bean:message key="lbl.AppName"/></b></td>
        <td ><b><bean:message key="lbl.appraiserDate"/></b></td>
        <td ><b><bean:message key="lbl.veri.entityType"/></b></td>
       
   
	</tr>
<logic:present name="completionList">
	<logic:iterate name="completionList" id="sublist" indexId="counter">
	<input type="hidden" name="verificationId" id=""verificationId"" value="${sublist.verificationId}" />
	<tr class="white1">
      <td ><input type="checkbox" name="chk" id="chk" value="${sublist.fieldVerificationUniqueId}"/></td>
		<td width="10%">
		
			${sublist.verificationType}
			
       </td>
       <td width="10%">
		
			${sublist.verificationSubType}
			
       </td>
       
       	<td width="10%">
		
			${sublist.appraisalName}
			
       </td>
       	<td width="10%">
		
			${sublist.appraisalDate}
			
       </td>
	 
       	<td width="10%">
		
			<a href="#" onclick="viewCapturedVerification('${sublist.fieldVerificationUniqueId}');">${sublist.entityType}</a>
			
       </td>
 
 
	</tr>
	
	</logic:iterate>
	</logic:present>	
		

 </table>    </td>
</tr>


</table>
<button type="button" name="add" class="topformbutton2" id="Add" title="Alt+A" accesskey="A" onclick="addCapturedVerification();"><bean:message key="button.add" /></button>
	</fieldset>
</logic:present>
<logic:notPresent name="decision">	
	<fieldset>
	<legend><bean:message key="lbl.verification.details"/></legend>
			 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table id="gridtable" width="100%" border="0" cellspacing="1" cellpadding="1">
   
	 <tr class="white2">
 		<td ><b><bean:message key="lbl.verification.type"/></b></td>
 		<td ><b><bean:message key="lbl.verification.subtype"/></b></td>
        <td ><b><bean:message key="lbl.AppName"/></b></td>
        <td ><b><bean:message key="lbl.appraiserDate"/></b></td>
        <td ><b><bean:message key="lbl.veri.entityType"/></b></td>
        
   
	</tr>
<logic:present name="completionList">
	<logic:iterate name="completionList" id="sublist" indexId="counter">
	<tr class="white1">

		<td width="10%">
		
			${sublist.verificationType}
			
       </td>
       <td width="10%">
		
			${sublist.verificationSubType}
			
       </td>
       
       	<td width="10%">
		
			${sublist.appraisalName}
			
       </td>
       	<td width="10%">
		
			${sublist.appraisalDate}
			
       </td>
	 
       	<td width="10%">
		
			<a href="#" onclick="viewCapturedVerification('${sublist.fieldVerificationUniqueId}');">${sublist.entityType}</a>
			
       </td>
 
 
	</tr>
	
	</logic:iterate>
	</logic:present>	
		

 </table>    </td>
</tr>


</table>
	</fieldset>
</logic:notPresent>


</html:form>
</logic:present>
<%--     For CM        --%>   

<logic:present name="verifCMS">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
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
	          <td >Verification Completion</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>
<html:form styleId="fieldVerificationForm" action="/fieldVerificationSave" method="post" >

<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<logic:present name="decision">	
	<fieldset>
	<legend><bean:message key="lbl.verification.details"/></legend>
			 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table id="gridtable" width="100%" border="0" cellspacing="1" cellpadding="1">
   
	 <tr class="white2">
	     <td width="3%"><input type="checkbox" name="allchkd" id="allchkd" onclick="allChecked();" /></td>
 		<td ><b><bean:message key="lbl.verification.type"/></b></td>
 		<td ><b><bean:message key="lbl.verification.subtype"/></b></td>
        <td ><b><bean:message key="lbl.AppName"/></b></td>
        <td ><b><bean:message key="lbl.appraiserDate"/></b></td>
        <td ><b><bean:message key="lbl.veri.entityType"/></b></td>
       
   
	</tr>
<logic:present name="completionList">
	<logic:iterate name="completionList" id="sublist" indexId="counter">
	<tr class="white1">
      <td ><input type="checkbox" name="chk" id="chk" value="${sublist.fieldVerificationUniqueId}"/></td>
		<td width="10%">
		
			${sublist.verificationType}
			
       </td>
       <td width="10%">
		
			${sublist.verificationSubType}
			
       </td>
       
       	<td width="10%">
		
			${sublist.appraisalName}
			
       </td>
       	<td width="10%">
		
			${sublist.appraisalDate}
			
       </td>
	 
       	<td width="10%">
		
			<a href="#" onclick="viewCapturedVerificationAtCM('${sublist.fieldVerificationUniqueId}');">${sublist.entityType}</a>
			
       </td>
 
 
	</tr>
	
	</logic:iterate>
	</logic:present>	
		

 </table>    </td>
</tr>


</table>
<button type="button" name="add" class="topformbutton2" id="Add" title="Alt+A" accesskey="A" onclick="addCapturedVerification();"><bean:message key="button.add" /></button>
	</fieldset>
</logic:present>
<logic:notPresent name="decision">	
	<fieldset>
	<legend><bean:message key="lbl.verification.details"/></legend>
			 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table id="gridtable" width="100%" border="0" cellspacing="1" cellpadding="1">
   
	 <tr class="white2">
 		<td ><b><bean:message key="lbl.verification.type"/></b></td>
 		<td ><b><bean:message key="lbl.verification.subtype"/></b></td>
        <td ><b><bean:message key="lbl.AppName"/></b></td>
        <td ><b><bean:message key="lbl.appraiserDate"/></b></td>
        <td ><b><bean:message key="lbl.veri.entityType"/></b></td>
        
   
	</tr>
<logic:present name="completionList">
	<logic:iterate name="completionList" id="sublist" indexId="counter">
	<tr class="white1">

		<td width="10%">
		
			${sublist.verificationType}
			
       </td>
       <td width="10%">
		
			${sublist.verificationSubType}
			
       </td>
       
       	<td width="10%">
		
			${sublist.appraisalName}
			
       </td>
       	<td width="10%">
		
			${sublist.appraisalDate}
			
       </td>
	 
       	<td width="10%">
		
			<a href="#" onclick="viewCapturedVerificationAtCM('${sublist.fieldVerificationUniqueId}');">${sublist.entityType}</a>
			
       </td>
 
 
	</tr>
	
	</logic:iterate>
	</logic:present>	
		

 </table>    </td>
</tr>


</table>
	</fieldset>
</logic:notPresent>


</html:form>
</logic:present>
  </body>
</html:html>
