<%--
      Author Name-  Ritu Jindal
      Date of creation -55/09/2011
      Purpose-   Field Varification Info       
      
 --%>

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
		
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/gcdScript/customerDetail.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/fieldVerificationScript.js"></script>
		

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
	<body onload="enableAnchor();document.getElementById('fieldVarificationIniationForm').dealButton.focus();">
	
	<logic:present name="verifCPS">
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
	          <td >Verification Initiation</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>
	<div id="centercolumn">
	
	<div id="revisedcontainer">

	<html:form action="/fieldVarificationInitiationAction" method="post" styleId="fieldVarificationIniationForm">
	<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
	<html:hidden property="forwardFlag" styleId="forwardFlag" value="${requestScope.forwardFlag}"/>
	
		<fieldset>	
		 <legend><bean:message key="lbl.fieldinitiation"/></legend>  

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table id="gridtable" width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
 
        <td ><b><bean:message key="lbl.varificationType"/></b></td>
        <td ><b><bean:message key="lbl.varificationSubType"/></b></td>
        <td ><b><bean:message key="lbl.veri.entityId"/></b></td>
        <td ><b><bean:message key="lbl.veri.entityType"/></b></td>
        <td ><b><bean:message key="lbl.veri.sub.entityType"/></b></td>
         <td ><b><bean:message key="lbl.ver.addressType"/></b></td>
         
        <td ><b><bean:message key="lbl.ver.desc"/></b></td>
        <td ><b><bean:message key="lbl.fieldAction"/></b></td>
        <td><b><bean:message key="lbl.appraiserIN"/></b></td>
       
       	 <td ><b><bean:message key="lbl.internalAppraiser"/></b></td>
        
        <td><b><bean:message key="lbl.externalAppraiser"/></b></td>
         <td><b><bean:message key="lbl.status"/></b></td>
       
	</tr>
	
	
<logic:present name="showList">
	<logic:iterate name="showList" id="sublist" indexId="counter">
	<tr class="white1">

	<html:hidden property="entityId" styleId="entityId${counter+1}" value="${sublist.entId}"/>
	<td width="10%">
		
		${sublist.verificationType}
       </td>
      
		<td width="10%">
	
		${sublist.verificationSubType}
            
       </td>

      <td width="5%">
    		${sublist.entId}
       </td>
     <td width="7%">
     
		${sublist.entityType}
       </td>
        <td width="7%">
      
		${sublist.entitySubType}
       </td>
       <td width="7%">
         
		  ${sublist.addressTypeDesc}
       </td>
      <td width="10%">
		${sublist.entityDesc}
       </td>
		
		<td>
		
		  ${sublist.actionValue}
	
		
		</td>
		
		<td width="7%">
		
		  ${sublist.appraiserType}
		 
	    </td>
	  
		    <td >
		  
		       ${sublist.internalAppraiser}
		  
		   
			</td>	

	  
			<td >
			
			
				${sublist.agencyName}
			
			
			</td>
	           <td >
			
			
				${sublist.status}
			
			
			</td>

		

	</tr>
	
	</logic:iterate>
	</logic:present>	
		

 </table>    </td>
</tr>


</table>

	</fieldset>


</html:form>

	</div>


</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
</logic:present>
<logic:present name="verifCMS">

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
	          <td > Verification Initiation</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>
	<div id="centercolumn">
	
	<div id="revisedcontainer">

	<html:form action="/fieldVarificationInitiationAction" method="post" styleId="fieldVarificationIniationForm">
	<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
	<html:hidden property="forwardFlag" styleId="forwardFlag" value="${requestScope.forwardFlag}"/>
	
		<fieldset>	
		 <legend><bean:message key="lbl.fieldinitiation"/></legend>  

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table id="gridtable" width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
 
        <td ><b><bean:message key="lbl.varificationType"/></b></td>
        <td ><b><bean:message key="lbl.varificationSubType"/></b></td>
        <td ><b><bean:message key="lbl.veri.entityId"/></b></td>
        <td ><b><bean:message key="lbl.veri.entityType"/></b></td>
        <td ><b><bean:message key="lbl.veri.sub.entityType"/></b></td>
         <td ><b><bean:message key="lbl.ver.addressType"/></b></td>
         
        <td ><b><bean:message key="lbl.ver.desc"/></b></td>
        <td ><b><bean:message key="lbl.fieldAction"/></b></td>
        <td><b><bean:message key="lbl.appraiserIN"/></b></td>
       
       	 <td ><b><bean:message key="lbl.internalAppraiser"/></b></td>
        
        <td><b><bean:message key="lbl.externalAppraiser"/></b></td>
          <td><b><bean:message key="lbl.status"/></b></td>
       
	</tr>
	
	
<logic:present name="showList">
	<logic:iterate name="showList" id="sublist" indexId="counter">
	<tr class="white1">

	<html:hidden property="entityId" styleId="entityId${counter+1}" value="${sublist.entId}"/>
	<td width="10%">
		
		${sublist.verificationType}
       </td>
      
		<td width="10%">
	
		${sublist.verificationSubType}
            
       </td>

      <td width="5%">
    		${sublist.entId}
       </td>
     <td width="7%">
     
		${sublist.entityType}
       </td>
        <td width="7%">
      
		${sublist.entitySubType}
       </td>
       <td width="7%">
         
		  ${sublist.addressTypeDesc}
       </td>
      <td width="10%">
		${sublist.entityDesc}
       </td>
		
		<td>
		
		  ${sublist.actionValue}
	
		
		</td>
		
		<td width="7%">
		
		  ${sublist.appraiserType}
		 
	    </td>
	  
		    <td >
		  
		       ${sublist.internalAppraiser}
		  
		   
			</td>	

	  
			<td >
			
			
				${sublist.agencyName}
			
			
			</td>
	     <td >
			
			
				${sublist.status}
			
			
			</td>

		

	</tr>
	
	</logic:iterate>
	</logic:present>	
		

 </table>    </td>
</tr>


</table>

	</fieldset>


</html:form>

	</div>


</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>

</logic:present>
</body>
</html:html>