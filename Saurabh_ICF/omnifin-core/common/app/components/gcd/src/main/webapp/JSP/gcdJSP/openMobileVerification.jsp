<!--  
Author Name:- Shashank Agnihotri
Date of Creation:- 09/10/2020
Purpose:-  
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

    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>

    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/cpInsurance.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customer_address.js"></script>
    

<style type="text/css">
textarea {

color:#000;
font-family:arial,serif;
font-size:13px;
padding-left:2px;
width:700px;
resize:none;
height:50px;
}

</style>
<title></title>
    </head>
	<body oncontextmenu="return false" onload="enableAnchor();checkChanges('specialConditionForm');">
	
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
    		<html:hidden property="addId" styleId="addId" 	value="${addId}" />
	<div id="centercolumn">
	<html:form styleId="openMobileVerificationForm" action="/customerAddressAction" method="post">
	<fieldset>
	
	<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>" />
	  <legend><bean:message key="lbl.mobileVerification"/></legend>

	      <table width="100%"  border="0" cellspacing="2" cellpadding="0">
	<tr>
		<td>
			
		<table width="100%" border="0" cellspacing="1" cellpadding="1">

	
		<tr>
		 <td><bean:message key="lbl.mobileNo" /></td>
                        <td > 
                        <html:text property="mobileNo" styleId="mobileNo" readonly="true" styleClass="text" value="${MobileNo}" maxlength="20" /> </td>
           
	    </tr>
	  <tr>
		 <td><bean:message key="lbl.verifyOtp" /><span><font color="red">*</font></span></td>
                        <td > 
                        <html:text property="verifyOtp" styleId="verifyOtp" styleClass="text" value="" maxlength="10" /> </td>
           
	    </tr>
		 <tr>
		   <td >									
		    <button type="button" name="saveButton" id="saveButton" onclick="callVerifyOtp();" title="Alt+V" accesskey="V" class="topformbutton2" ><bean:message key="button.verifyOtp" /></button>								
			 <button type="button" name="reSendButton" id="reSendButton" onclick="callReSendOtp();" title="Alt+R" accesskey="R" class="topformbutton2" ><bean:message key="button.reSendOtp" /></button>								
			
			</td>
		   </tr>
		</table>
	      </td>
	</tr>	
	</table>
	</fieldset>
	</html:form>
</div>   
<script>
	setFramevalues("specialConditionForm");
</script>

<logic:present name="status">
<script type="text/javascript">
	if('<%=request.getAttribute("status").toString()%>'=='Y')
	{
		alert('Mobile No. Verified.');
	    javascript:window.close();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='N')
	{
		alert('Mobile No. Not Verified.');
	}
</script>
</logic:present>

</body>
</html:html>

<logic:present name="stats">
<script type="text/javascript">
	if('<%=request.getAttribute("stats").toString()%>'=='Y')
	{
		alert('OTP Send Successfully.');
		 
	}
	else if('<%=request.getAttribute("stats").toString()%>'=='N')
	{
		alert('OTP Not Send Successfully.');
	}
</script>
</logic:present>

<logic:present name="msg">
<script type="text/javascript">
	if('<%=request.getAttribute("msg").toString()%>'=='S')
	{
		alert('<bean:message key="msg.DataSaved" />');
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='E')
	{
		alert('<bean:message key="msg.DataNotSaved" />');
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='D')
	{
		alert('<bean:message key="lbl.deleted" />');
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='N')
	{
		alert('<bean:message key="lbl.notDelete" />');
	}
</script>
</logic:present>
