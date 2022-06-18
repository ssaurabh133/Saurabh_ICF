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
<title><bean:message key="lbl.verificationDetails"/></title>
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
	          <td >Deal Verification Movement</td>
          </tr>
        </table> 
</td>
</tr>
</table>
</fieldset>


	  <fieldset>	
		 <legend><bean:message key="lbl.verificationDetails"/></legend>  

         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
    				<tr class="white2">
      					<td><strong><bean:message key="lbl.seqNo"/></strong></td>
        				<td><strong><bean:message key="msg.verificationtype"/></strong></td>
        				<td><strong><bean:message key="lbl.verification_sub_type"/></strong></td>
						<td><strong><bean:message key="lbl.appraiserIN"/></strong></td>
						<td><strong><bean:message key="lbl.user_id"/></strong></td>
						<td><strong><bean:message key="lbl.assignedOn"/></strong></td>
						<td><strong><bean:message key="lbl.verificationAction"/></strong></td>
        				<td><strong><bean:message key="lbl.Status"/></strong></td>
    				</tr>
    				<logic:present name="dealVerificationMovementList">
    				
		 			<logic:iterate name="dealVerificationMovementList" id="subdealVerificationMovementList">
		 			
		 			<logic:equal name="subdealVerificationMovementList" property="status" value="FORWARDED">
						<tr bgcolor="#8CD1E6">
						
			     			<td>${subdealVerificationMovementList.seqNo}</td>
				 			<td>${subdealVerificationMovementList.verificationType}</td>
				 			<td>${subdealVerificationMovementList.verificationSubType}</td>
				 			<td>${subdealVerificationMovementList.appraiserType}</td>
				 			<td>${subdealVerificationMovementList.userId}</td>
				 			<td>${subdealVerificationMovementList.assignedTo}</td>
				 			<td>${subdealVerificationMovementList.verificationAction}</td>
				 			<td>${subdealVerificationMovementList.status}</td>
				 			
				 		</tr>
			 		</logic:equal>
			 		<logic:equal name="subdealVerificationMovementList" property="status" value="COMPLETED">
						<tr bgcolor="#aaff91">
						
			     			<td>${subdealVerificationMovementList.seqNo}</td>
				 			<td>${subdealVerificationMovementList.verificationType}</td>
				 			<td>${subdealVerificationMovementList.verificationSubType}</td>
				 			<td>${subdealVerificationMovementList.appraiserType}</td>
				 			<td>${subdealVerificationMovementList.userId}</td>
				 			<td>${subdealVerificationMovementList.assignedTo}</td>
				 			<td>${subdealVerificationMovementList.verificationAction}</td>
				 			<td>${subdealVerificationMovementList.status}</td>
				 			
				 		</tr>
			 		</logic:equal>
			 		<logic:equal name="subdealVerificationMovementList" property="status" value="PENDING">
						<tr  bgcolor="#E0E04E">
						
			     			<td>${subdealVerificationMovementList.seqNo}</td>
				 			<td>${subdealVerificationMovementList.verificationType}</td>
				 			<td>${subdealVerificationMovementList.verificationSubType}</td>
				 			<td>${subdealVerificationMovementList.appraiserType}</td>
				 			<td>${subdealVerificationMovementList.userId}</td>
				 			<td>${subdealVerificationMovementList.assignedTo}</td>
				 			<td>${subdealVerificationMovementList.verificationAction}</td>
				 			<td>${subdealVerificationMovementList.status}</td>
				 			
				 		</tr>
			 		</logic:equal>
			 		<logic:equal name="subdealVerificationMovementList" property="status" value="CAPTURED">
						<tr  bgcolor="#E0E040">
						
			     			<td>${subdealVerificationMovementList.seqNo}</td>
				 			<td>${subdealVerificationMovementList.verificationType}</td>
				 			<td>${subdealVerificationMovementList.verificationSubType}</td>
				 			<td>${subdealVerificationMovementList.appraiserType}</td>
				 			<td>${subdealVerificationMovementList.userId}</td>
				 			<td>${subdealVerificationMovementList.assignedTo}</td>
				 			<td>${subdealVerificationMovementList.verificationAction}</td>
				 			<td>${subdealVerificationMovementList.status}</td>
				 			
				 		</tr>
			 		</logic:equal>
	    			</logic:iterate>
	   				</logic:present>
 				</table>
			</td>
		</tr>
	</table>

	</fieldset>
		 <table> 
            <tr>
			<td nowrap colspan="10" class="white">
		
			 <button type="button" name="Save" class="topformbutton2" id="Close" title="Alt+C" accesskey="C" onclick="closeVerificationDetails();" value="Close"><bean:message key="button.close" /></button>	
			</td>
			</tr>
			
		</table>
	
	
</html:form>




</div>


</body>
</html:html>
