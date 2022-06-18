<!--  
Author Name:- ravindra Kumar
Date of Creation:- 12/12/2011
Purpose:-  The purpose of this page is to provide details of deal movement.
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
	</head>
<body oncontextmenu="return false" onload="enableAnchor();checkChanges('dealMovementForm');document.getElementById('dealMovementForm').Close.focus();init_fields();">


<input type="hidden" name="contextPath" id="contextPath" value='<%=request.getContextPath() %>'/>

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
	          <td >Deal Movement</td>
          </tr>
        </table> 
</td>
</tr>
</table>
</fieldset>

	  <fieldset>	
		 <legend><bean:message key="lbl.dealMovementDetails"/></legend>  

         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
    				<tr class="white2">
      					<td><strong><bean:message key="lbl.seqNo"/></strong></td>
        				<td><strong><bean:message key="lbl.stage"/></strong></td>
						<td><strong><bean:message key="lbl.dealReceived"/></strong></td>
						<td><strong><bean:message key="lbl.dealForwarded"/></strong></td>
						<td><strong><bean:message key="lbl.action"/></strong></td>
        				<td><strong><bean:message key="lbl.dealForwardedUser"/></strong></td>
    				</tr>
    				<logic:present name="dealMovementList">
    				
		 			<logic:iterate name="dealMovementList" id="subDealMovementList">
		 			<logic:equal name="subDealMovementList" property="action" value="INITIATED">
						<tr bgcolor="#8CD1E6">
						
			     			<td>${subDealMovementList.seqNo}</td>
				 			<td>${subDealMovementList.stage}</td>
				 			<td>${subDealMovementList.dealReceived}</td>
				 			<td>${subDealMovementList.dealForwarded}</td>
				 			<td>${subDealMovementList.action}</td>
				 			<td>${subDealMovementList.dealForwardedUser}</td>
				 			
				 		</tr>
			 		</logic:equal>
			 		<logic:equal name="subDealMovementList" property="action" value="COMPLETED">
						<tr bgcolor="#aaff91">
						
			     			<td>${subDealMovementList.seqNo}</td>
				 			<td>${subDealMovementList.stage}</td>
				 			<td>${subDealMovementList.dealReceived}</td>
				 			<td>${subDealMovementList.dealForwarded}</td>
				 			<td>${subDealMovementList.action}</td>
				 			<td>${subDealMovementList.dealForwardedUser}</td>
				 			
				 		</tr>
			 		</logic:equal>
			 		<logic:equal name="subDealMovementList" property="action" value="PENDING">
						<tr  bgcolor="#E0E04E">
						
			     			<td>${subDealMovementList.seqNo}</td>
				 			<td>${subDealMovementList.stage}</td>
				 			<td>${subDealMovementList.dealReceived}</td>
				 			<td>${subDealMovementList.dealForwarded}</td>
				 			<td>${subDealMovementList.action}</td>
				 			<td>${subDealMovementList.dealForwardedUser}</td>
				 			
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
	<td>
	   <button type="button" name="verificationDetails" class="topformbutton4" id="verificationDetails" title="Alt+C" accesskey="C" onclick="verificationDetailst();"> <bean:message key="button.verificationDetails" /></button>
	   </td>
	   <td>
	   <button type="button" name="underwriterDealTracking" class="topformbutton4" id="underwriterDealTracking" title="Alt+U" accesskey="U" onclick="underwriterDeal();"> <bean:message key="button.underwriterDealTracking" /></button>
</td>
<td>
	   <button type="button" name="deviationDealTracking" class="topformbutton4" id="deviationDealTracking" title="Alt+D" accesskey="D" onclick="deviationDeal();"> <bean:message key="button.deviationDealTracking" /></button>
</td>
</tr>
</table>
		
</html:form>



</div>

<script>
	setFramevalues("dealMovementForm");
</script>
</body>
</html:html>
