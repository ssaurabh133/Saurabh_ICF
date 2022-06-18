<!--  
Author Name:- Prashant Kumar
Date of Creation:- 12/06/2012
Purpose:-  The purpose of this page is to provide details of deal Verfication Movement.
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
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customer_address.js"></script>
		<script type="text/javascript"  src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
  		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
<logic:present name="back">

<script type="text/javascript">
  alert("You can't move without saving before Lead Details!!!");
</script>

</logic:present>
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
<title><bean:message key="lbl.underwriterDealTracking"/></title>
	</head>
<body oncontextmenu="return false" onload="enableAnchor();checkChanges('dealMovementForm');document.getElementById('dealMovementForm').Close.focus();init_fields();">

	<div  align="center" class="opacity" style="display: none;"  id="processingImage">
</div>
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


		<html:form styleId="dealMovementForm" action="/dealMovement" method="post">
  <fieldset>
     <table cellSpacing=0 cellPadding=0 width="100%" border=0>
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
	          <td >Underwriter Queue tracking</td>
          </tr>
        </table> 
</td>
</tr>
</table>
</fieldset>


	  <fieldset>	
		 <legend><bean:message key="lbl.underwriterDealTracking"/></legend>  

         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
    				<tr class="white2">
      					<td ><b><bean:message key="lbl.userNam"/></b></td>
         <td><b><bean:message key="lbl.level"/></b></td>
        <td><b><bean:message key="lbl.branchDes"/></b></td>
        <td><b><bean:message key="lbl.approvalBy"/> </b></td>
        <td><b><bean:message key="lbl.approvalDecision"/> </b></td>
        <td><b><bean:message key="lbl.approvalRemark"/> </b></td>
        
    				</tr>
    				<logic:present name="dealVerificationMovementList">
    				
		 			<logic:iterate name="dealVerificationMovementList" id="subdealVerificationMovementList">
		 			
		 			<tr class="white1">
						
			     			<td>${subdealVerificationMovementList.userName}</td>
				 			<td>${subdealVerificationMovementList.level}</td>
				 			<td>${subdealVerificationMovementList.branchDesc}</td>
				 			<td>${subdealVerificationMovementList.approvalBy}</td>
				 			<td>${subdealVerificationMovementList.approvalDecision}</td>
				 			<td>${subdealVerificationMovementList.approvalRemark}</td>
				 			
				 		</tr>	
				 			
				 		
			 			    			</logic:iterate>
	   				</logic:present>
 				</table>
			</td>
		</tr>
	</table>

	</fieldset>
		 
	
	
</html:form>




</div>


</body>
</html:html>
