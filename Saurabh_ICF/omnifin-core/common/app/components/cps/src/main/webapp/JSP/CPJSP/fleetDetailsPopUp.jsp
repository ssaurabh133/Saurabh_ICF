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
<link type="text/css" href="development-bundle/demos/demos.css" rel="stylesheet" />
<link type="text/css" href="development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />

	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
			<script type="text/javascript">
 	
		
	</script>
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
<body oncontextmenu="return false" onunload="closeAllLovCallUnloadBody();" onload="enableAnchor();checkChanges('fleetDetailForm');" onclick="parent_disable();"><br />
	
<html:form action="/saveFleetProcessAction"  styleId="fleetDetailForm">
	
<fieldset>	
<!--		 <legend><bean:message key="stakeObligation.details"/> </legend>  -->

  
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
    	<td >
    	<logic:present name="fleetDetailsList">
    		<b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();" disabled="disabled"/><bean:message key="contact.select" /></b>
    	</logic:present>
    	 <logic:notPresent name="fleetDetailsList">
    	 <b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/><bean:message key="contact.select" /></b>
    	 </logic:notPresent></td> 
        <td ><b><bean:message key="lbl.vehicleOwner"/></b> </td>
		<td ><strong><bean:message key="lbl.relationshipS"/> </strong></td>
        <td ><b><bean:message key="lbl.vehicleNo"/> </b></td>
        <td ><strong><bean:message key="lbl.vehicleModel"/></strong></td>
        <td ><b><bean:message key="lbl.mfgYear"/> </b></td>
        <td ><strong><bean:message key="lbl.docCollected"/></strong></td>
	</tr>
	
	
		<logic:present name="fleetDetailsList">
			<logic:iterate id="subFleetDetailsList" name="fleetDetailsList">
				<tr class="white1">
					<td >
    					<input type="checkbox" name="chk" id="chk" value="${subFleetDetailsList.dealFleetId }" disabled="disabled"/>
					</td>
					<td >${subFleetDetailsList.vehicleOwner }</td>
					<td >${subFleetDetailsList.relationship }</td>
					<td>${subFleetDetailsList.vehicleNo }</td>
					<td>${subFleetDetailsList.vehicleModel }</td>
					<td >${subFleetDetailsList.mfgYear }</td>
					<td>${subFleetDetailsList.docCollected }</td>
				</tr>	
			</logic:iterate>
			
		</logic:present>	
	
	
 </table>    </td>
</tr>

</table></fieldset>

	</html:form>
</body>
</html:html>
