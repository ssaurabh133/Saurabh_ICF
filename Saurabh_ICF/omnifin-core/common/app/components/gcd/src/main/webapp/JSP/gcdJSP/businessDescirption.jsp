<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.logger.LoggerMsg"%>
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customerDetail.js"></script>
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
   <script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
   
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

<style type="text/css">
		textarea {
/*border:1px solid #9BB168;*/
	color:#000;
	font-family:arial,serif;
	font-size:13px;
	padding-left:2px;
	width:800px;
	resize:none;
	height:200px;
}
</style>

	</head>
<BODY oncontextmenu="return false;" onclick="parent_disable();" onload="enableAnchor();checkChanges('businessDescirptionForm');">
<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
<logic:present name="pageStatuss">
<script type="text/javascript">
  // alert("Notok");
   top.opener.location.href="custEntryAction.do";
   //alert("ok");
    
</script>
</logic:present>
<!---->
<logic:present name="updateInDeal">
<script type="text/javascript">
  //alert("Notok");
   top.opener.location.href="custEntryAction.do";
   <%session.removeAttribute("updateInDeal");%>
  
    
</script>
</logic:present>

<input type="hidden" id="contextPath" name="contextPath" value="<%=path%>"/>
<logic:notPresent name="approve">

<DIV id=centercolumn >
<DIV id=revisedcontainer>

<div id="updateAddress">
<FIELDSET ><LEGEND><bean:message key="lbl.businessDescription" /></LEGEND>
<HTML>
<HEAD>
<TITLE> <bean:message key="lbl.businessDescription" /></TITLE>
<META NAME="Generator" CONTENT="EditPlus">
<META NAME="Author" CONTENT="">
<META NAME="Keywords" CONTENT="">
<META NAME="Description" CONTENT="">
</HEAD>

<BODY oncontextmenu="return false" onload="enableAnchor();document.getElementById('businessDescirptionForm').addr_type.focus();">

<center><input type="hidden" id="duplicateAdd" value="${requestScope.commAddressCheck}"/></center>

<html:form action="/corporateBusinessDescirption" styleId="businessDescirptionForm" method="post">

<logic:present name="update">

<table cellspacing="0" cellpadding="3" border="0" width="100%">
 <html:hidden property="bp_id" styleClass="text" styleId="bp_id" value="${sessionScope.corporateId}" />
	<tr>
        <td><html:hidden property="bp_id1" styleId="bp_id1" styleClass="text" value="${businessDesc[0].bp_id1}"  /></td>
        </tr><tr>
        <td><bean:message key="lbl.businessSummary" /><font color="red">*</font></td>
        <td><html:textarea property="businessDesc" styleId="businessDesc" styleClass="textarea" value="${businessDesc[0].businessDesc}"  /></td>
	</tr>
	
	<tr>
        <td><span class="white">
       		<button type="button"  name="button1" class="topformbutton2" id="save" title="Alt+V" accesskey="V" onclick="return saveBusiDesc();"><bean:message key="button.save" /></button>
        </span></td>
	</tr>
</table>
	

</logic:present>
<logic:notPresent name="update">

<table cellspacing="0" cellpadding="3" border="0" width="100%">
 <html:hidden property="bp_id" styleClass="text" styleId="bp_id" value="${sessionScope.corporateId}" />
	<tr>
        <td><html:hidden property="bp_id1" styleId="bp_id1" styleClass="text" value="${businessDesc[0].bp_id1}"  /></td>
        </tr><tr>
        <td><bean:message key="lbl.businessSummary" /><font color="red">*</font></td>
        <td><html:textarea property="businessDesc" styleId="businessDesc" styleClass="textarea" value="${businessDesc[0].businessDesc}"  /></td>
	</tr>
	
	<tr>
        <td><span class="white">
           <button type="button"  name="button1" class="topformbutton2" id="save" title="Alt+V" accesskey="V" onclick="return saveBusiDesc();"><bean:message key="button.save" /></button>
        </span></td>
	</tr>
</table>

</logic:notPresent>
</html:form></BODY></HTML>
</FIELDSET> </div></DIV></DIV>
</logic:notPresent>
<logic:present name="approve">
<DIV id=centercolumn>
<DIV id=revisedcontainer>
<FIELDSET><LEGEND><bean:message key="lbl.businessDescription" /></LEGEND>
<HTML>
<HEAD>
<TITLE> <bean:message key="lbl.businessDescription" /></TITLE>
<META NAME="Generator" CONTENT="EditPlus">
<META NAME="Author" CONTENT="">
<META NAME="Keywords" CONTENT="">
<META NAME="Description" CONTENT="">
</HEAD>
<BODY>
<html:form action="/corporateBusinessDescirption" styleId="businessDescirptionForm" method="post">
<logic:present name="custEntryU">
<table cellspacing="0" cellpadding="3" border="0" width="100%">
 <html:hidden property="bp_id" styleClass="text" styleId="bp_id" value="${sessionScope.corporateId}" />
	<tr>
        <td><html:hidden property="bp_id1" styleId="bp_id1" styleClass="text" value="${businessDesc[0].bp_id1}"  /></td>
        </tr><tr>
        <td><bean:message key="lbl.businessSummary" /><font color="red">*</font></td>
        <td><html:textarea property="businessDesc" styleId="businessDesc" styleClass="textarea" value="${businessDesc[0].businessDesc}"  /></td>
	</tr>
	
	<tr>
        <td><span class="white">
       		<button type="button"  name="button1" class="topformbutton2" id="save" title="Alt+V" accesskey="V" onclick="return saveBusiDesc();"><bean:message key="button.save" /></button>
        </span></td>
	</tr>
</table>
</logic:present>
<logic:notPresent name="custEntryU">
<table cellspacing="0" cellpadding="3" border="0" width="100%">
		<tr>
        <td><html:hidden property="bp_id1" styleId="bp_id1" styleClass="text" value="${businessDesc[0].bp_id1}"  /></td>
        </tr><tr>
        <td><bean:message key="lbl.businessSummary" /></td>
        <td><html:textarea property="businessDesc" styleId="businessDesc" styleClass="textarea" readonly="true" value="${businessDesc[0].businessDesc}"  /></td>
	</tr>

	
</table>
<td colspan="4" >
</logic:notPresent>
</html:form>
</FIELDSET> 
</DIV></DIV>
</logic:present>

<logic:present name="back">
<script type="text/javascript">
 alert("You can't move without saving before Corporate Details!!!");
</script>
</logic:present>
<logic:present name="closeSuccess">
<script type="text/javascript">
 alert("Deleted Successfully!!!");
</script>
</logic:present>
<logic:present name="procval">
	<script type="text/javascript">
	if('<%=request.getAttribute("procval")%>'!='S')
	{
	   
		alert('<%=request.getAttribute("procval").toString()%>');
		
	}
	</script>
</logic:present>
<logic:present name="sms">
 <script type="text/javascript">
 if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="msg.DataSaved" />");
		document.getElementById('businessDescirptionForm').action="<%=request.getContextPath()%>/corporateBusinessDescirption.do?method=displayBusinessDescirption";
	    document.getElementById('businessDescirptionForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='U')
	{
		alert("<bean:message key="msg.DataSaved" />");
		document.getElementById('businessDescirptionForm').action="<%=request.getContextPath()%>/corporateBusinessDescirption.do?method=displayBusinessDescirption";
	    document.getElementById('businessDescirptionForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
			alert('<bean:message key="lbl.errorSuccess" />');
			document.getElementById('businessDescirptionForm').action="<%=request.getContextPath()%>/corporateBusinessDescirption.do?method=displayBusinessDescirption";
	}
	
	</script>
</logic:present>
<script>
	setFramevalues("businessDescirptionForm");
</script>
</BODY>

</HTML>