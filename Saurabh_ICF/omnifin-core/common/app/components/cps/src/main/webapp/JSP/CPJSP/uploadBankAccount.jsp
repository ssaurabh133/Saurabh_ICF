<%@page import="java.util.ResourceBundle"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");


int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);


%>	
<script type="text/javascript" src="<%=request.getContextPath() %>/js/ocrScript/upload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/commonScript/checkMod.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="report" content="" />
 <logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
</head>
<body onload="enableAnchor();">

<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	

	<div align="center" class="opacity" style="display: none;"
		id="processingImage">
	</div>

<html:form action="/bankAccountUpload"   styleId="uploadBankAccountForm"  enctype="multipart/form-data">
<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
<input type="hidden" id="flag" name="flag" value=""/>
<fieldset><legend><bean:message key="lbl.BankAccountUpload"/></legend>
<table width="100%" border="0" cellspacing="1" cellpadding="1">
	<tr>
		<td width="40%"><bean:message key="lbl.uploadFile"/><span><font color="red">*</font></span></td>
		<td>
			<html:file size="100" property="docFile" styleId="docFile" styleClass="text" tabindex="1"  />
		</td>
								
	</tr>	
	<tr>
								
	</tr>	
	</table>
	<table width="25%" border="0" cellspacing="1" cellpadding="1">	
	<tr>
		
		<td>
		
		<button type="button" name="button" id="report" class="topformbutton4"
															title="Alt+F" accesskey="R" 
															onclick="generateAccountUploadFormat();">
															<bean:message key="lbl.generateUloadFormat" />
														</button>
		</td>
			<td>
		<button type="button" name="button" id="upload"  class="topformbutton3"
															title="Alt+F" accesskey="R" 
															onclick="uploadFundFlowFile();">
															<bean:message key="button.upload" />
														</button>
		</td>
								
	</tr>	
	</table>
 </fieldset> 
 

</html:form>

<logic:present name="msg">

 <script type="text/javascript">
	if('<%=request.getAttribute("msg").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.datasms" />");
	}
		
	else if('<%=request.getAttribute("msg").toString()%>'=='E')
	{
		alert("Data Not Uploaded.");
	}
	
	else if('<%=request.getAttribute("msg").toString()%>'=='R')
	{
		alert("<bean:message key="msg.validatiionRule" />");
	}

	else if('<%=request.getAttribute("msg").toString()%>'=='fileTypeVal')
	{
		alert("<bean:message key="msg.fileTypeVal" />");
	}

	else if('<%=request.getAttribute("msg").toString()%>'=='DS')
	{
		alert("<bean:message key="msg.docForward" />");
	}
	
	else if('<%=request.getAttribute("msg").toString()%>'=='DE')
	{
		alert("<bean:message key="msg.DocNotForward" />");
	}
	
	
	
	
</script>

</logic:present>
</body>
</html>