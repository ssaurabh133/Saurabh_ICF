<!--  
Author Name:- Amit Kumar
Date of Creation:- 30/04/2011
Purpose:-  
-->


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page import="java.util.*" %>
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
<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		
<script type="text/javascript"  src="<%=request.getContextPath() %>/js/cpScript/documentRelated.js"></script>
  		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
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
	<body oncontextmenu="return false" onload="enableAnchor();checkChanges('underwritingDocUpload');document.getElementById('underwritingDocUpload').docDescription.focus();init_fields();">
   
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">
	</div>
<logic:notPresent name="underWriter">	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	 
	<html:form styleId="underwritingDocUpload" action="/underwritingDocUpload" method="post" enctype="multipart/form-data" onsubmit="submitDocUpload()">
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>"/>
	<fieldset>	  
	<legend><bean:message key="lbl.docUploadDetails"/></legend>  
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
     <tr>
      <td><table width="100%" border="0" cellspacing="1" cellpadding="4">
        <html:hidden property="stage" styleId="stage" value="${stage}"/>
		<tr>
		  <td width="23%"><bean:message key="lbl.docDescription"/><font color="red">*</font></td>
		  <td width="21%"><html:text property="docDescription" styleClass="text" value="" tabindex="1"  maxlength="100" styleId="docDescription" /></td>
          <td width="28%"><html:file property="docFile" styleId="docFile" styleClass="text" /></td>
         <td> <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="uploadFVCDoc('underwritingDocUpload');"><bean:message key="button.upload" /></button></td>
          
		</tr>
		<tr><td><button type="button" class="topformbutton2" name="close" id="close" onclick="self.close();"><bean:message key="button.close" /></button></td></tr>
		  
      </table></td>
</tr>
</table>    

	 
	 
	  </fieldset>




  
<fieldset>	
		 <legend><bean:message key="lbl.docInformation"/></legend> 

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
    	<td ><b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/><bean:message key="lbl.select" /></b></td> 
        <td><b><bean:message key="lbl.fileName"/></b></td>
		<td ><b><bean:message key="lbl.docDescription"/></b></td>
        <td ><b><bean:message key="lbl.uploadedBy" /></b></td>
        </tr>
        <logic:present name="uploadedDocList">
        <logic:notEmpty name="uploadedDocList">
        <logic:iterate name="uploadedDocList" id="uploadedDocSubList">
    <tr  class="white1">
    	<td><input type="checkbox" name="chk" id="chk" value="${uploadedDocSubList.fileName}"/></td>
     	<td><a href="#" onclick="downloadFVIFile('${uploadedDocSubList.fileName}','underwritingDocUpload','${stage}');">${uploadedDocSubList.fileName}</a>
     		<input type="hidden" name="fileName" id="fileName" value="${uploadedDocSubList.fileName}"/>
     	</td>
        <td>${uploadedDocSubList.docDescription}</td>
        <td>${uploadedDocSubList.userName}</td>
        </tr>
        </logic:iterate>
        </logic:notEmpty>
        </logic:present>
    	<logic:present name="uploadDocListForCp">
        <logic:iterate name="uploadDocListForCp" id="uploadedDocSubListForCp">
    <tr  class="white1">
    	<td><input type="checkbox" name="chk1" id="chk1" disabled="disabled" value="${uploadedDocSubListForCp.fileName}"/></td>
     	<td><a href="#" onclick="downloadFVIFile('${uploadedDocSubListForCp.fileName}','underwritingDocUpload','${stage}');">${uploadedDocSubListForCp.fileName}</a>
     		<input type="hidden" name="fileName" id="fileName" value="${uploadedDocSubListForCp.fileName}"/>
     	</td>
        <td>${uploadedDocSubListForCp.docDescription}</td>
        <td>${uploadedDocSubListForCp.userName}</td>
        </tr>
        </logic:iterate>
        </logic:present>
    </table>
    </td>
</tr>
</table>
<button type="button" name="delete" class="topformbutton2" id="delete" title="Alt+D" accesskey="D" onclick="deleteFVIUploadDoc('${stage}');"><bean:message key="button.delete" /></button>
	</fieldset>
	</html:form>
</div>



</div>
</logic:notPresent>
<logic:present name="underWriter">
		
		<div id="centercolumn">
	
	<div id="revisedcontainer">
		
	<html:form styleId="underwritingDocUpload" action="/underwritingDocUpload" method="post" enctype="multipart/form-data" onsubmit="submitDocUpload()">
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>"/>

<fieldset>	
		 <legend><bean:message key="lbl.docInformation"/></legend> 

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
    	<td ><b><input type="checkbox" name="allchk" id="allchk" disabled="disabled" onclick="allChecked();"/><bean:message key="lbl.select" /></b></td> 
        <td><b><bean:message key="lbl.fileName"/></b></td>
		<td ><b><bean:message key="lbl.docDescription"/></b></td>
        <td ><b><bean:message key="lbl.uploadedBy" /></b></td>
        </tr>
        <logic:present name="uploadedDocList">
        
        <logic:iterate name="uploadedDocList" id="uploadedDocSubList">
    <tr  class="white1">
    	<td><input type="checkbox" name="chk" disabled="disabled" id="chk" value="${uploadedDocSubList.fileName}"/></td>
     	<td><a href="#" onclick="downloadFile('${uploadedDocSubList.fileName}');">${uploadedDocSubList.fileName}</a>
     		<input type="hidden" name="fileName" id="fileName" value="${uploadedDocSubList.fileName}"/>
     	</td>
        <td>${uploadedDocSubList.docDescription}</td>
        <td>${uploadedDocSubList.userName}</td>
        </tr>
        </logic:iterate>
        
        </logic:present>
    
    </table>
    </td>
</tr>
</table>

	</fieldset>
	</html:form>
</div>



</div>
</logic:present>
<script>
	setFramevalues("underwritingDocUpload");
</script>

</body>
</html:html>
<logic:present name="message">

 <script type="text/javascript">
	if('<%=request.getAttribute("message").toString()%>'=='E')
	{
		alert('<bean:message key="msg.fileExist" />');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='U')
	{
		alert('<bean:message key="msg.upperLimit" />');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='S')
	{
		alert('<bean:message key="msg.selectFile" />');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='FiveDocOnly')
	{
		alert('<bean:message key="msg.fiveDocOnly" />');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='UploadSuccessful')
	{
		alert('<bean:message key="msg.uploadSuccessful" />');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='deletedoc')
	{
		alert('<bean:message key="lbl.dataDeleted" />');
	}
</script>
</logic:present>
