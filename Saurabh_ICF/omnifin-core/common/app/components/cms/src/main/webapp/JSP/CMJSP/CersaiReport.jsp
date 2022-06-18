<%@ page language="java"%>
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
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>

<!-- css and jquery for Datepicker -->
<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>

<!-- css and jquery for Datepicker -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/omniFinReportScript.js"></script>
<%
//String path = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
</head>

<body onload="enableAnchor();document.getElementById('cersaiFile').interestWorkingDate.focus();init_fields();">
<div id="centercolumn">
<div id="revisedcontainer">
<logic:present name="image">
<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
</logic:present>
<logic:notPresent name="image">
<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
</logic:notPresent>
<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />

<html:form action="/CersaiReportUpld" method="POST" styleId="CersaiReportUpld" enctype="multipart/form-data">
	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	<fieldset>	  
	<legend><bean:message key="lbl.CersaiReport"/> </legend>         
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
		<td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">

		<tr>
				<td>File Name<font color="red">*</font></td>
	    <td><html:select property="fileName" styleId="fileName" styleClass="text" value="">
		<html:option value="">-- Select --</html:option>
		<html:option value="C">CKYC Upload</html:option>
		<html:option value="K">Risk Category Upload</html:option> 
		<html:option value="CGTMSE">CGTMSE Upload</html:option>
		</html:select></td>
		
		<td >File Name</td>
		<td><html:file property="cersaiFile" styleId="cersaiFile" styleClass="text" size="100" tabindex=""/></td>
		</tr>
		
		<tr>
		<td><button type="button" value="" class="topformbutton3"  title="Alt+U" accesskey="U" onclick="return CersaiReportUpload();"> <bean:message key="button.upload" /></button></td>
		</tr>
		<tr>
		<td><button type="button" value="" class="topformbutton3"  title="Alt+D" accesskey="D" onclick="return CersaiReportDownload();"> <bean:message key="button.download" /></button></td>
		</tr>
		
		</table>
		</td>
		</tr>
	</table>
	</fieldset>	
</html:form>
</div>
</div>

<logic:present name="vstatus">
<script type="text/javascript">
alert('File Uploaded Successfully !!!');
</script>
</logic:present>

<logic:present name="sstatus">
<script type="text/javascript">
alert('File not Uploaded !!!');
</script>
</logic:present>

<%-- <logic:present name="vstatus">
<script type="text/javascript">
alert('File Name Should be CERSAI_entry.xls');
</script>
</logic:present> --%>

<logic:present name="CheckFileName">
<script type="text/javascript">
alert('File Name Not Correct');
</script>
</logic:present>

</body>
</html:html>
