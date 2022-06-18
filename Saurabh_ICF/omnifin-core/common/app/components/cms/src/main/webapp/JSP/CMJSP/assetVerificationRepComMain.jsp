<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
		<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		<%
		String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":"
					+ request.getServerPort() + path + "/";

			UserObject userobj = (UserObject) session
					.getAttribute("userobject");
			String initiationDate = userobj.getBusinessdate();
	%>	 
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=path%>/js/commonScript/calendar.js"></script>
	 	<%@page import="com.login.roleManager.UserObject"%>

   <script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
   <script type="text/javascript" src="<%=path%>/js/cmScript/cmLoanInitjs.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>

		<script type="text/javascript">
	        function infoCall()
        {	
        document.getElementById("loanInfo").style.display='';
        }
      
			function no_disbursal()
			{
					var url="PopupDisbursal.html"
	newwindow=window.open(url,'init_disbursal','top=200,left=250,scrollbars=no,width=1366,height=768' );
	if (window.focus) {newwindow.focus()}
	return false;
			}
		</script>
		
	
	</head>
	<body onclick="parent_disable();" onunload="closeLOVWindow()" onload="enableAnchor();checkChanges('sourcingForm');document.getElementById('sourcingForm').assetLocation.focus()">
	 <input type="hidden" name="businessDate" id="businessDate" value="<%=initiationDate%>" />
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
    		
	<div id="centercolumn">
	<div id="revisedcontainer">
	
	<html:form action="/assetVerificationInitMain" method="post" styleId="sourcingForm" >
 <html:javascript formName="AssetVerificationInitiationMainDynaValidatorForm"/>

	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" name="assetVerificationID1" id="assetVerificationID1" value="${requestScope.assetId}" />
	
	<logic:present name="list"> 
	<logic:notEmpty name="list">
	<logic:iterate id="listobj" name="list">
	   <fieldset>	  
	<legend><bean:message key="lbl.assetVerificationRepCap"/></legend>           
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		
		<tr>		
		<td width="13%"><bean:message key="lbl.loanNumber"/></td>
		
	 <td width="13%">
	 
	 <html:text styleClass="text" property="loanAccNo" styleId="loanAccNo" value="${listobj.loanAccNo}" readonly="true" tabindex="-1" /> 
 
	 </td>
	
	 <td width="13%"><bean:message key="lbl.customerName"/></td>
	 <td width="13%">
	 
	  <html:text styleClass="text" property="customerName" styleId="customerName" value="${listobj.customerName}" readonly="true" tabindex="-1"/> 
       <input type="hidden" name="assetVerificationID" id="assetVerificationID" value="${listobj.assetVerificationID}"/>
	 </td>
	
	 </tr>
	  <tr>
	    <td><bean:message key="lbl.product"/></td>
	    <td>
	    
	      <html:text styleClass="text" property="loanProduct" styleId="loanProduct" value="${listobj.loanProduct}" readonly="true" tabindex="-1"/>
 
	    </td>
	    
		<td><bean:message key="lbl.loanScheme"/> </td>
	    <td> 
	    
	     <html:text styleClass="text" property="loanScheme" styleId="loanScheme" value="${listobj.loanScheme}" readonly="true" tabindex="-1"/>
	   
	    </td>
	 
	  </tr>
	
	  <tr>
	    <td><bean:message key="lbl.loanAmount"/> </td>
	    <td>
	    
	     <html:text styleClass="text" property="loanAmount" styleId="loanAmount" value="${listobj.loanAmount}" readonly="true" tabindex="-1"/>
 
	    </td>
	    
		<td><bean:message key="lbl.loanApprovalDate"/> </td>
	    <td> 
	    
	     <html:text styleClass="text" property="loanApprovalDate" styleId="loanApprovalDate" value="${listobj.loanApprovalDate}" readonly="true" tabindex="-1"/>
 
	    </td>
	 
	  </tr>
	  	
   </table>
	 </td>
	</tr>

	</table>
	</fieldset>	
	
	<fieldset>	
		 <legend><bean:message key="lbl.AssetDesc"/></legend>    
			<table width="100%"  border="0" cellspacing="0" cellpadding="0" >
			
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr>			
		   <td width="13%"><bean:message key="lbl.AssetDesc"/></td>
			 <td width="13%">
			 
			 <html:text styleClass="text" property="assetDescription" styleId="assetDescription" value="${listobj.assetDescription}" readonly="true" tabindex="-1"/>
	 
			 </td>
			

	   <logic:equal name="listobj" property="appraiser" value="I">
	    <td width="13%"><bean:message key="lbl.apptype"/> </td>
         <td width="13%"> <html:text styleClass="text" property="assetDescription" styleId="assetDescription" value="Internal" readonly="true" tabindex="-1"/></td>
         </logic:equal>
          <logic:equal name="listobj" property="appraiser" value="EA">
           <td width="13%"><bean:message key="lbl.apptype"/> </td>
         <td width="13%"><html:text styleClass="text" property="assetDescription" styleId="assetDescription" value="External" readonly="true" tabindex="-1"/></td>
         </logic:equal>
   
		
		
		</tr>
	  <tr>
	    <td><bean:message key="lbl.appraiserName"/> </td>
	    <td >
 	     <html:text styleClass="text" property="appraiserName" styleId="appraiserName" value="${listobj.appraiserName}" readonly="true" tabindex="-1"/>
 	    </td>
		<td></td>
		<td></td>
		
		 </tr>
	 
	
	</table>
  </td>
	</tr>

	</table>
	</fieldset> 
	
	 <fieldset>	
		 <legend><bean:message key="lbl.assetInformation"/></legend>    
			<table width="100%"  border="0" cellspacing="0" cellpadding="0" >
			
	<tr>
		<td width="13%">
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		
	<tr>			
		   <td width="13%"><bean:message key="lbl.assetLocation"/></td>
			 <td width="13%">
			 <html:text styleClass="text" property="assetLocation" styleId="assetLocation" readonly="true" value="${listobj.assetLocation}" tabindex="-1"/>
			  </td>
	   
	   
	   <td width="13%"><bean:message key="lbl.visitDate"/></td>
	   <td width="13%">
	    <html:text styleClass="text" property="visitDate" styleId="visitDate"  readonly="true" value="${listobj.visitDate}" tabindex="-1"/> </td>
		</tr>
		
	  <tr>
	    <td><bean:message key="lbl.assetCondition"/></td>
	    <td > 
	     <html:text styleClass="text" property="assetCondition" styleId="assetCondition" readonly="true" value="${listobj.assetCondition}" tabindex="-1"/>   </td>
	     
	   
		<td><bean:message key="lbl.invoiceCollected"/></td>
		 
		<logic:equal name="listobj" property="invoiceCollected" value="Y">
         <td><html:text styleClass="text" property="invoiceCollected" styleId="invoiceCollected" readonly="true" value="Yes" tabindex="-1"/> </td>
         </logic:equal>
          <logic:equal name="listobj" property="invoiceCollected" value="N">
         <td><html:text styleClass="text" property="invoiceCollected" styleId="invoiceCollected" readonly="true" value="No" tabindex="-1"/> </td>
         </logic:equal>
         
	  </tr>
	  
	  	<tr>			
		   <td><bean:message key="lbl.invoiceNumber"/></td>
			 <td>
			 <html:text styleClass="text" property="invoiceNumber" styleId="invoiceNumber"  maxlength="100" readonly="true" value="${listobj.invoiceNumber}" tabindex="-1"/> </td>
			 
	   <td> </td>
	   <td></td>
		</tr>
		<tr>
	    <td><bean:message key="lbl.visitRemarks"/><font color="red">*</font></td>
	    <td>
	    <html:textarea styleClass="text" property="comments" styleId="comments" readonly="true" tabindex="-1" value="${listobj.comments}"></html:textarea>
	     </td>
	     
	      <td><bean:message key="lbl.makerRemarks"/></td>
	    <td>
	    <html:textarea styleClass="text" property="makerRemarks" styleId="makerRemarks" readonly="true" tabindex="-1" value="${listobj.makerRemarks}"></html:textarea>
	     </td>
	   </tr>  
		
		<tr>
	     <td><bean:message key="lbl.authorRemarks" /></td>
		<td><html:textarea styleClass="text" property="authorRemarks" disabled="true" tabindex="-1" value="${listobj.authorRemarks}" /></td>

		 </tr>
  	
	
	</table>
  </td>
	</tr>
	
 
	</table>
	</fieldset> 
	
	</logic:iterate>
	</logic:notEmpty>
    </logic:present>
    
 
  
    

  </html:form>
</div>

</div>
<script>
	setFramevalues("sourcingForm");
</script>

</body>
</html:html>