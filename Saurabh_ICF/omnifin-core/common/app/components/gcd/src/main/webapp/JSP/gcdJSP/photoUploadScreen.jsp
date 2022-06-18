

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%@include file="/JSP/commonIncludeContent.jsp" %> 

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
	    <meta http-equiv="refresh" content="<%= session.getMaxInactiveInterval()%>;url= <%=request.getContextPath()%>/logoff.do" />
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/applicant.js"></script>
	

    </head>
  
<body onclick="parent_disable();" oncontextmenu="return false" onload="enableAnchor();checkChanges('applicantForm');document.getElementById('applicantForm').applicantType.focus();" onunload="closeAllWindowCallUnloadBody();closeAllWindowCallUnloadBodyAn();" >   

<html:errors />

<html:form action="/customerDocUpload" styleId="photoUploadForm" method="post" enctype="multipart/form-data">
<input type="hidden" id="customerId" value="${requestScope.customerId}"/>
<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>"/>

<fieldset>
<logic:notPresent name="ViewMode">

      <table cellspacing=1 cellpadding=1 width="75%" border=0>
     
	 
 
  <tr>
         <td>
        <bean:message key="lbl.photoUploadType"/><font color="red">*</font></td>
        
                <td > <input type="radio" name="photoUploadType" id="photoUploadType"  value=""  onclick="return enableDisableField();" />WebCam</td>
        <td><input type="radio" name="photoUploadType" id="photoUploadType1" value="" onclick="return enableDisableField();" />Upload</td> 
        
        
        </tr>
  		<tr id="photo" style="display: none;">
          <td width="25%"><html:file property="docFile" styleId="docFile" styleClass="text" /></td>
         <td width="20%"> <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="return submitPicUpload();"><bean:message key="button.upload" /></button></td>
          
		</tr>      

   
   </table>
   
   
  <fieldset>	
		 <legend><bean:message key="lbl.docInformation"/></legend> 

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
    	<td ><b><bean:message key="lbl.select" /></b></td> 
        <td><b><bean:message key="lbl.fileName"/></b></td>
		<td ><b><bean:message key="lbl.docDescription"/></b></td>
		
		<td ><b><bean:message key="lbl.uploadState"/></b></td>
		
        </tr>
        <logic:present name="uploadedDocList">
        <logic:notEmpty name="uploadedDocList">

        <logic:iterate name="uploadedDocList" id="uploadedDocSubList">
    <tr  class="white1">
    	<td><input type="checkbox" name="chk" id="chk" value="${uploadedDocSubList.customerId}"/></td>
     	<td><a href="#" onclick="customerDocDownloadFile('${uploadedDocSubList.fileName}','${uploadedDocSubList.customerId}');">${uploadedDocSubList.fileName}</a>
     		<input type="hidden" name="fileName" id="fileName" value="${uploadedDocSubList.fileName}"/>
     	</td>
        <td>${uploadedDocSubList.docDescription}</td>
         <td>${uploadedDocSubList.uploadedState}</td>
        </tr>
        </logic:iterate>
        </logic:notEmpty>
 
        </logic:present>
	      
    </table>
    </td>

		   </tr>
</table>
		<logic:present name="uploadedDocList">
	   <tr>   <button type="button" name="delete" class="topformbutton2" id="delete" title="Alt+D" accesskey="D" onclick="deleteUploadDocForCustomer('${uploadedDocList[0].customerId}','${uploadedDocList[0].fileName}');"><bean:message key="button.delete" /></button></tr>
        </logic:present>
	</fieldset> 
	</logic:notPresent>
	
	
<logic:present name="ViewMode">	

     <fieldset>	
		 <legend><bean:message key="lbl.docInformation"/></legend> 

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
    	<td ><b><bean:message key="lbl.select" /></b></td> 
        <td><b><bean:message key="lbl.fileName"/></b></td>
		<td ><b><bean:message key="lbl.docDescription"/></b></td>
		
		<td ><b><bean:message key="lbl.uploadState"/></b></td>
		
        </tr>
        <logic:present name="uploadedDocList">
        <logic:notEmpty name="uploadedDocList">

        <logic:iterate name="uploadedDocList" id="uploadedDocSubList">
    <tr  class="white1">
    	<td><input type="checkbox" name="chk" id="chk" value="${uploadedDocSubList.customerId}" readonly="readonly"/></td>
     	<td><a href="#" onclick="customerDocDownloadFile('${uploadedDocSubList.fileName}','${uploadedDocSubList.customerId}');">${uploadedDocSubList.fileName}</a>
     		<input type="hidden" name="fileName" id="fileName" value="${uploadedDocSubList.fileName}"/>
     	</td>
        <td>${uploadedDocSubList.docDescription}</td>
         <td>${uploadedDocSubList.uploadedState}</td>
        </tr>
        </logic:iterate>
        </logic:notEmpty>
 
        </logic:present>
	      
    </table>
    </td>

		   </tr>
</table>

	</fieldset> 
	</logic:present>
</fieldset> 




</html:form>

  </body>
  
  
  </html:html>
<logic:present name="message">

 <script type="text/javascript">
	if('<%=request.getAttribute("message").toString()%>'=='E')
	{
		alert('<bean:message key="msg.fileExist" />');
	}

	else if('<%=request.getAttribute("message").toString()%>'=='UploadSuccessful')
	{
		alert('<bean:message key="msg.uploadSuccessful" />');
		
	}
	else if('<%=request.getAttribute("message").toString()%>'=='deletedoc')
	{
		alert('<bean:message key="lbl.dataDeleted" />');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='deleteNotdoc')
	{
		alert('<bean:message key="lbl.dataNtDeleted" />');
	}
	
	if ('<%=request.getAttribute("message").toString()%>'=='X')
	{
	     alert('<bean:message key="lbl.Filetype" />');
	}
	   window.close();
	   opener.document.location.reload(true);
	   ///alert("alert");
	   //opener.window.location.href="collaterlInCMBehindAction.do";
	   opener.window.close();
	
</script>
</logic:present>

  
 