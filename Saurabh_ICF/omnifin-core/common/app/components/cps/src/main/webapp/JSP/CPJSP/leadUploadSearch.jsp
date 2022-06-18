<!-- 
Author Name :- Mradul
Date of Creation :06-07-2013
Purpose :-  Lead Upload
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
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
String fileName=(String)request.getAttribute("fileName");
LoggerMsg.info("in uploadDocument jsp....."+fileName);
HttpSession sess=request.getSession();
String processFile1=(String)sess.getAttribute("Processfile");
String processFile=CommonFunction.checkNull(processFile1);
if(processFile.equals(""))
{
processFile="NoFile";
}
LoggerMsg.info("in uploadDocument jsp..Process file..."+processFile);
%>
<script type="text/javascript">
function validateDocUpload()
{
	if(document.getElementById('docFile').value=="")
	{
		alert("Choose file to be uploaded.");
		document.getElementById("docFile").focus(); 
	    return false; 
	}
var fup = document.getElementById('docFile');
var file_Name = fup.value;
var ext = file_Name.substring(file_Name.lastIndexOf('.') + 1);
/*if(ext == "csv" || ext == "CSV")
{
	return true;
} */
if(ext == "xls" || ext == "XLS" || ext == "xlsx" || ext == "XLSX")
{
	return true;
} 
else
{
	alert("Upload xls/xlsx file only.");
	fup.value='';
	fup.focus();
	return false;
}
  
 }
function submitDocUpload()
	{
			DisButClass.prototype.DisButMethod();
            var sourcepath=document.getElementById("contextPath").value;	
   		if(validateDocUpload())
		 {
			document.getElementById("DocUpload").action=sourcepath+"/leadUploadProcessAction.do?method=uploadData";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("DocUpload").submit();
			return true;
	
		}else{
			DisButClass.prototype.EnbButMethod();
			return false;
		}	
	}
</script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
		<!-- css for Datepicker -->
		<link type="text/css" href="<%=request.getContextPath() %>/development-bundle/demos/demos.css" rel="stylesheet" />
		<link type="text/css" href="<%=request.getContextPath() %>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	<!-- jQuery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript"  src="<%=request.getContextPath() %>/js/cpScript/leadUpload.js"></script>
<!--[if IE 8]>
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
	<body onload="enableAnchor();document.getElementById('DocUpload').docFile.focus();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
	</div>
	<div id="centercolumn">	
	<div id="revisedcontainer">	
	<html:form styleId="DocUpload" action="/leadUploadProcessAction" method="post" enctype="multipart/form-data">    
	
	<fieldset>	  
	<legend><bean:message key="lbl.recDocUpload"/></legend>  
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>"/>
	<input type="hidden" name="txnfile" id="txnfile" value="<%=processFile%>"/>
     <tr>
      <td><table width="100%" border="0" cellspacing="1" cellpadding="4">
       	<tr>
		  <td width="20%"><bean:message key="lbl.fileDescription"/></td>
		  <td width="30%"><html:file size="40" property="docFile" styleId="docFile" styleClass="text"/></td>
		  <td width="40%"><html:button property="upload" value="Upload" styleId="upload"  styleClass="topformbutton2" onclick="submitDocUpload();" /></td>
		</tr>
		
		<tr>		
		<td colspan="4">
		 <button type="button" name="startProcessing" class="topformbutton3" title="Alt+P" accesskey="P" id="startProcessing"  onclick="startProcessLead();"><bean:message key="button.startProcng" /></button>&nbsp;&nbsp;&nbsp;
		 <%--<button type="button" name="errorLog" title="Alt+E" accesskey="E" class="topformbutton2" id="errorLog" onclick="errorLogDownloadReceipt();" ><bean:message key="button.errorlog" /></button>&nbsp;&nbsp;&nbsp;
	     <html:button property="summary" styleClass="topformbutton2" styleId="summary" value="Summary" onclick="uploadSummary();"/> --%>  
	    </td>
		</tr>
		  
      </table></td>
</tr>
</table> 
	 
	  </fieldset>  

	</html:form>
</div>
</body>
</html:html>

 <logic:present name="sms">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.sms" />');
 </script>
  </logic:present> 
  
  <logic:present name="smsno">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.smsno" />');
 </script>
  </logic:present>
  
   <logic:present name="maxCount">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.maxCount" />');
 </script>
  </logic:present>
   <logic:present name="inserted">
 <script type="text/javascript"> 
		alert("Process has completed, file successfully uploaded ");
 </script>
  </logic:present>

  <logic:present name="noinserted">
 <script type="text/javascript">	
		alert("Process has not completed ");
 </script>
  </logic:present>