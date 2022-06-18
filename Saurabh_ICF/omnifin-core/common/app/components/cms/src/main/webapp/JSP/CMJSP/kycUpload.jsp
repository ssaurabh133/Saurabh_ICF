	<%@ include file="/JSP/sessioncheck.jsp" %>
	<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
	<%@ page language="java" import="java.util.ResourceBundle.*" pageEncoding="ISO-8859-1" %>
	<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
	<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
	<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
	<%@ page language="java" import="java.util.ResourceBundle.*" %>
	<%@ page language="java" import="java.util.ResourceBundle"%>
	<%@ page language="java" import="java.util.*" %>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	<head>
	<title><bean:message key="a3s.noida" /></title>
	<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.datepicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.tabs.js"></script>
	<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/kycUpload.js"></script>
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
	<div>
	<div>
	<html:form action="kycUpload" method="post" styleId="kycUploadDynaForm" enctype="multipart/form-data">
	<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>"/> 
	<fieldset>
	<logic:present name="NACH">
	<legend>NACH REVERSE UPLOAD</legend>
	</logic:present>
	<logic:notPresent name="NACH">
	<legend>Kyc-Sheet Upload</legend>
	</logic:notPresent>
	<table width="100%"  border="0" cellspacing="7" cellpadding="0" >
	<tr>
		<td width="10%"><bean:message key="lbl.filepath"/></td>
		<td width="90%"><html:file size="60" property="docFile" styleId="docFile" styleClass="text" tabindex="3"/></td>	
	</tr>
	<tr> 
	  <td><button type="button" id="save" class="topformbutton2"  title="Alt+V" accesskey="V" onclick="return saveKycUpload();" tabindex="4" ><bean:message key="button.save" /></button></td>
	</tr>
	</table>
	</fieldset>
	</html:form>
	</div>
	 </div>
	 <div  align="center" class="opacity" style="display:none" id="processingImage" ></div>
	</body>
	</html:html>
	
<logic:present name="sms">
 <script type="text/javascript">
  if('<%=request.getAttribute("sms").toString()%>'=='upload')
	{
		alert('Data Uploaded Successfully');
	}
  else if('<%=request.getAttribute("sms").toString()%>'=='Failed')
	{
	  alert('For Some Case Data Not Uploaded, Please check Manually')
	}
</script>
</logic:present>