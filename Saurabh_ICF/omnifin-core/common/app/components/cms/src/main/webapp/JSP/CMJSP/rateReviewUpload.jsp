<!-- 
Author Name :- Manish Shukla
Date of Creation :
Purpose :-  Upload
-->

<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.logger.LoggerMsg"%>
<%@page import="com.connect.CommonFunction"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page import="java.util.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />		 
		<!-- css for Datepicker -->
		<link type="text/css" href="<%=request.getContextPath() %>/development-bundle/demos/demos.css" rel="stylesheet" />
		<link type="text/css" href="<%=request.getContextPath() %>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript"  src="<%=request.getContextPath() %>/js/cmScript/cmLoanInitjs.js"></script>
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
	<body onload="enableAnchor();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<html:form styleId="reteReview" action="/rateReviewUpload" method="post" enctype="multipart/form-data">   
	 <input type="hidden" id="contextPath" value="<%=request.getContextPath() %>" /> 
	<fieldset><legend><bean:message key="lbl.rateReviewUpload"/></legend>  
		
		<legend><bean:message key="lbl.UploadData"/></legend>  	 
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
		  		<td><bean:message key="lbl.fileDescription"/></td>
		  		<td><html:file size="40" property="docFile" styleId="docFile" styleClass="text"/></td>
		  		<td></td>
		  		<td>
		  			<button type="button" name="upload"  id="upload" title="Alt+U" accesskey="U" class="topformbutton2" onclick="uploadRateReview();" ><bean:message key="button.upload"/></button>
		  			<button type="button" name="errorLog" class="topformbutton2" id="errorLog" title="Alt+E" accesskey="E" onclick="generateRateReviewErrorLog();"><bean:message key="button.errorlog"/></button>
		  		</td>
		</tr>		
		</table>
    </fieldset>
	</html:form>
</body>
</html:html>
<logic:present name="uploadError">
	<script type="text/javascript">
	alert('${uploadError}');
	</script>
</logic:present>
<logic:present name="uploaded">
	<script type="text/javascript">
		alert("<bean:message key="lbl.sms" />");
	</script>
</logic:present>
