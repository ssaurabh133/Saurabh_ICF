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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
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
	  <fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
        <logic:present name="cmdocupload">
          <td><b><bean:message key="lbl.loanNo" /></b></td>
          <td>${dealHeader[0].dealNo}</td>
          </logic:present>
          <logic:notPresent name="cmdocupload">
          <td><b><bean:message key="lbl.dealNo" /></b></td>
          <td>${dealHeader[0].dealNo}</td>
          </logic:notPresent>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${dealHeader[0].dealDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${dealHeader[0].dealCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${dealHeader[0].dealProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${dealHeader[0].dealProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${dealHeader[0].dealScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Documents Upload</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 

</fieldset>
	<html:form styleId="underwritingDocUpload" action="/underwritingDocUpload" method="post" enctype="multipart/form-data" onsubmit="return submitDocUpload();" >
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>"/>
	<input type="hidden" name="dealId" id="dealId" value="${dealId}"/>
	<input type="hidden" name="loanId" id="loanId" value="${loanId}"/>
	<input type="hidden" name="loanDisbursalId" id="loanDisbursalId" value="${loanDisbursalId}"/>
	<input type="hidden" name="txnType" id="txnType" value="${dmsDetailsMap['txnType']}"/>
	<input type="hidden" name="txnId" id="txnId" value="${dmsDetailsMap['txnId']}"/>
	<input type="hidden" name="docId" id="docId" value="${dmsDetailsMap['docId']}"/>
	<input type="hidden" name="childDocId" id="childDocId" value="0"/>
	<input type="hidden" name="dmsSystemUserId" id="dmsSystemUserId" value="${dmsCredential['DMS_USER_ID']}"/>
	<input type="hidden" name="dmsSystemUserPassword" id="dmsSystemUserPassword" value="${dmsCredential['DMS_USER_PASSWORD']}"/>
	<input type="hidden" name="dmsCategoryName" id="dmsCategoryName" value="${dmsCredential['DMS_CATEGORY_NAME']}"/>
	<input type="hidden" name="dmsDealRoom" id="dmsDealRoom" value="${dmsCredential['DMS_DEAL_ROOM']}"/>
	<input type="hidden" name="dmsDocSetParentName" id="dmsDocSetParentName" value="${dmsCredential['DMS_DOC_SET_PARENT_NAME']}"/>
	<input type="hidden" name="dmsDocSetChildName" id="dmsDocSetChildName" value="${dmsCredential['DMS_DOC_SET_CHILD_NAME']}"/>
	<input type="hidden" name="dmsIgName" id="dmsIgName" value="${dmsCredential['DMS_IG_NAME']}"/>
	<input type="hidden" name="dmsImName" id="dmsImName" value="${dmsCredential['DMS_IM_NAME']}"/>
	<input type="hidden" name="dmacVersion" id="dmacVersion" value="${dmsCredential['DMAC_VERSION']}"/>
	<input type="hidden" name="dmsImfName" id="dmsImfName" value="${dmsCredential['DMS_IMF_NAME']}"/>
	  <!-- Test Start -->
	  <logic:notEqual name="dmsProvider" value="dMAC">
   <logic:present name="fromPYMAuthor">
   
   
   
   	<fieldset style="display:none">	  

 
	<legend><bean:message key="lbl.docUploadDetails"/></legend>  
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
     <tr>
      <td><table width="100%" border="0" cellspacing="1" cellpadding="4">
        <html:hidden property="txnType" styleId="txnType" value="D"/>
		<tr>
		  <td width="23%" style="width:23%"><bean:message key="lbl.docDescription"/><font color="red">*</font></td>
		  <td width="21%"><html:text property="docDescription" styleClass="text" value="" tabindex="1"  maxlength="50" styleId="docDescription" /></td>
         <logic:present name="fromPYMAuthor">
          <td width="25%"><html:file property="docFile" styleId="docFile" styleClass="text"/></td>
       
         <td width="20%"> <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="return submitDocUpload();" disabled="disabled"><bean:message key="button.upload" /></button></td>
          </logic:present>
           <logic:notPresent name="fromPYMAuthor">
          <td width="25%"><html:file property="docFile" styleId="docFile" styleClass="text" /></td>
         <td width="20%"> <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="return submitDocUpload();"><bean:message key="button.upload" /></button></td>
          </logic:notPresent>
		</tr>
		  
      </table></td>
</tr>
</table>    

	 
	 
	  </fieldset>
   
   
   
   </logic:present>
   
  
  <!-- Test End -->
	
	  <logic:notPresent name="fromPYMAuthor">
	
	<fieldset>	  

 
	<legend><bean:message key="lbl.docUploadDetails"/></legend>  
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
     <tr>
      <td><table width="100%" border="0" cellspacing="1" cellpadding="4">
        <html:hidden property="txnType" styleId="txnType" value="D"/>
		<tr>
		  <td width="23%" style="width:23%"><bean:message key="lbl.docDescription"/><font color="red">*</font></td>
		  <td width="21%"><html:text property="docDescription" styleClass="text" value="" tabindex="1"  maxlength="50" styleId="docDescription" /></td>
         <td width="25%"><html:file property="docFile" styleId="docFile" styleClass="text" /></td>
         <logic:present name="fromPYMAuthor">
          
         <td width="20%"> <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="return submitDocUpload();" disabled="disabled"><bean:message key="button.upload" /></button></td>
          </logic:present>
          
           <logic:notPresent name="fromPYMAuthor">
          
         <td width="20%"> <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="return submitDocUpload();"><bean:message key="button.upload" /></button></td>
          </logic:notPresent>
		</tr>
		  
      </table></td>
</tr>
</table> 
	  </fieldset>
	  </logic:notPresent>
	  </logic:notEqual>
	<logic:equal name="dmsProvider" value="dMAC">
		<logic:notPresent name="viewDeal">
	<legend><bean:message key="lbl.dmsMetadata"/></legend>  
	<fieldset>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><table width="100%" border="0" cellspacing="1" cellpadding="1">
					<tr>
						<td>Form No</td><td><input type="text" disabled="disabled" name="applicationFormNo" id="applicationFormNo" value="${dmsDetailsMap['applicationFormNo']}"/></td>
						<td>Branch</td><td><input type="text" disabled="disabled" name="branch" id="branch"  value="${dmsDetailsMap['branch']}"/></td>
						<td>Product</td><td><input type="text" disabled="disabled" name="product" id="product"  value="${dmsDetailsMap['product']}"/></td>
					</tr>
					<tr>
						<td>Scheme</td><td><input type="text" disabled="disabled" name="scheme" id="scheme"  value="${dmsDetailsMap['scheme']}"/></td>
						<td>DMS Number</td><td><input type="text" disabled="disabled" name="dmsNumber" id="dmsNumber"  value="${dmsDetailsMap['dmsNumber']}"/></td>
						<td>Entity Type</td><td><input type="text" disabled="disabled" name="entityType" id="entityType"  value="${dmsDetailsMap['entityType']}"/></td>
					</tr>
					<tr>
						<td>Entity Id</td><td><input type="text" disabled="disabled" name="entityId" id="entityId"  value="${dmsDetailsMap['entityId']}"/></td>
						<td>Entity Desc</td><td><input type="text" disabled="disabled" name="entityDesc" id="entityDesc"  value="${dmsDetailsMap['entityDesc']}"/></td>
						<td>PAN No</td><td><input type="text" disabled="disabled" name="panNumber" id="panNumber"  value="${dmsDetailsMap['panNumber']}"/></td>
					</tr>
					<tr>
						<td>Aadhar No</td><td><input type="text" disabled="disabled" name="aadharNumber" id="aadharNumber"  value="${dmsDetailsMap['aadharNumber']}"/></td>
						<td>Email Id</td><td><input type="text" disabled="disabled" name="email" id="email"  value="${dmsDetailsMap['email']}"/></td>
						<td>Mobile No</td><td><input type="text" disabled="disabled" name="mobileNumber" id="mobileNumber"  value="${dmsDetailsMap['mobileNumber']}"/></td>
					</tr>
					<tr>
						<td>DOB</td><td><input type="text" disabled="disabled" name="dob" id="dob"  value="${dmsDetailsMap['dob']}"/></td>
						<td>Document Type</td><td><input type="text" disabled="disabled" name="documentTypeName" id="documentTypeName"  value="${dmsDetailsMap['documentTypeName']}"/></td>
						<td>Uploaded By</td><td><input type="text" disabled="disabled" name="uploadedBy" id="uploadedBy" value="${dmsDetailsMap['uploadedBy']}"/></td>
					</tr>
					<td width="20%"> <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="return docUpload('${dmsCredential['DMS_URL']}','global');"><bean:message key="button.upload" /></button></td>
					</tr>
				</table></td>
			</tr>
		</table>
	</fieldset>
	</logic:notPresent>
	</logic:equal>
	 <fieldset><legend><bean:message key="lbl.docInformation"/></legend> 

  
         <%-- table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
    	<td ><b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/><bean:message key="lbl.select" /></b></td> 
        <td><b><bean:message key="lbl.fileName"/></b></td> 
		<td ><b><bean:message key="lbl.entityType"/></b></td>
		<td ><b><bean:message key="lbl.entityName"/></b></td>
		<td ><b><bean:message key="lbl.documentType"/></b></td>
		<td ><b><bean:message key="lbl.documentName"/></b></td>
		<td ><b><bean:message key="lbl.docDescription"/></b></td>
		
		<td ><b><bean:message key="lbl.uploadState"/></b></td>
		<td ><b><bean:message key="lbl.documentType"/></b></td>
        <td ><b><bean:message key="lbl.uploadedBy" /></b></td>
        <td ><b><bean:message key="lbl.uploadedDate" /></b></td>
        <td ><b><bean:message key="lbl.DmsDocNumber" /></b></td>
        <td> </td>
        </tr>
        <logic:present name="uploadedDocList">
        <logic:notEmpty name="uploadedDocList">
        <logic:iterate name="uploadedDocList" id="uploadedDocSubList" indexId="count">
    <tr  class="white1">
    	<td><input type="checkbox" name="chk" id="chk${count}" value="${uploadedDocSubList.lbxDocId}"/></td>
     	 <td><a href="#" onclick="downloadFile('${uploadedDocSubList.fileName}');">${uploadedDocSubList.fileName}</a>
     		<input type="hidden" name="fileName" id="fileName" value="${uploadedDocSubList.fileName}"/>
     	</td> 
		<td>${uploadedDocSubList.docTypeDesc}</td>
		<td>${uploadedDocSubList.entityName}</td>
		<td>${uploadedDocSubList.documentType}</td>
		<td>${uploadedDocSubList.documentName}</td>
        <td>${uploadedDocSubList.docDescription}</td>
         <td>${uploadedDocSubList.uploadedState}</td>
          <td>${uploadedDocSubList.docTypeDesc}</td>
        <td>${uploadedDocSubList.userName}</td>
        <td>${uploadedDocSubList.uploadedDate}</td>
        <td>${uploadedDocSubList.dmsDocNumber}</td>
		<td align="center">
			<input type="hidden" name="lbxDocId" id="lbxDocId_${count}" value="${uploadedDocSubList.lbxDocId}"/>
			<input type="hidden" name="dmsDocId" id="dmsDocId_${count}" value="${uploadedDocSubList.dmsDocId}"/>
			<input type="hidden" name="dmsDocUrl" id="dmsDocUrl_${count}" value="${uploadedDocSubList.dmsDocUrl}"/>
			<a href='#' onClick="downloadFile('${uploadedDocSubList.fileName}','${count}');"><img style=\"cursor:pointer\" src="<%=request.getContextPath()%>/images/theme1/invoiceDownload1.png" width="18" height="18" ></a>	
		</td>
        </tr>
        </logic:iterate>
        </logic:notEmpty>
        </logic:present>
    	<logic:present name="uploadDocListForCp">
        <logic:iterate name="uploadDocListForCp" id="uploadedDocSubListForCp" indexId="count">
    <tr  class="white1">
    	<td><input type="checkbox" name="chk1" id="chk1${count}" disabled="disabled" value="${uploadedDocSubListForCp.fileName}"/></td>
     	<td><a href="#" onclick="downloadFile('${uploadedDocSubListForCp.fileName}');">${uploadedDocSubListForCp.fileName}</a>
     		<input type="hidden" name="fileName" id="fileName" value="${uploadedDocSubListForCp.fileName}"/>
     	</td>
		<td>${uploadedDocSubListForCp.docTypeDesc}</td>
		<td>${uploadedDocSubListForCp.entityName}</td>
		<td>${uploadedDocSubListForCp.documentType}</td>
		<td>${uploadedDocSubListForCp.documentName}</td>
        <td>${uploadedDocSubListForCp.docDescription}</td>
         <td>${uploadedDocSubListForCp.uploadedState}</td>
		 <td>${uploadedDocSubListForCp.docTypeDesc}</td>
        <td>${uploadedDocSubListForCp.userName}</td>
         <td>${uploadedDocSubListForCp.uploadedDate}</td>
        <td>${uploadedDocSubListForCp.dmsDocNumber}</td>
        <td align="center">
        	<input type="hidden" name="lbxDocId" id="lbxDocId_${count}" value="${uploadedDocSubListForCp.lbxDocId}"/>
        	<input type="hidden" name="dmsDocId" id="dmsDocId_${count}" value="${uploadedDocSubListForCp.dmsDocId}"/>
			<input type="hidden" name="dmsDocUrl" id="dmsDocUrl_${count}" value="${uploadedDocSubListForCp.dmsDocUrl}"/>
			<a href='#' onClick="downloadFile('${uploadedDocSubListForCp.fileName}','${count}');"><img style=\"cursor:pointer\" src="<%=request.getContextPath()%>/images/theme1/invoiceDownload1.png" width="18" height="18" ></a>	
		</td>
        </tr>
        </logic:iterate>
        </logic:present>
    </table>
    </td>
</tr>
</table> --%>

<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
    	<td ><b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/><bean:message key="lbl.select" /></b></td> 
        <td><b><bean:message key="lbl.fileName"/></b></td>
		<td ><b><bean:message key="lbl.docDescription"/></b></td>
		
		<td ><b><bean:message key="lbl.uploadState"/></b></td>
		<td ><b><bean:message key="lbl.documentType"/></b></td>
        <td ><b><bean:message key="lbl.uploadedBy" /></b></td>
        </tr>
        <logic:present name="uploadedDocList">
        <logic:notEmpty name="uploadedDocList">
         <logic:iterate name="uploadedDocList" id="uploadedDocSubList" indexId="count">
    <tr  class="white1">
    	<td><input type="checkbox" name="chk" id="chk" value="${uploadedDocSubList.fileName}"/></td>
     	<td><a href="#" onclick="downloadFileNew('${uploadedDocSubList.fileName}','${count}','CM');">${uploadedDocSubList.fileName}</a>
     		<input type="hidden" name="fileName" id="fileName" value="${uploadedDocSubList.fileName}"/>
     	</td>
        <td>${uploadedDocSubList.docDescription}</td>
         <td>${uploadedDocSubList.uploadedState}</td>
          <td>${uploadedDocSubList.docTypeDesc}</td>
        <td>${uploadedDocSubList.userName}</td>
        </tr>
        
			<input type="hidden" name="lbxDocId" id="lbxDocId_${count}" value="${uploadedDocSubList.lbxDocId}"/>
			<input type="hidden" name="dmsDocId" id="dmsDocId_${count}" value="${uploadedDocSubList.dmsDocId}"/>
			<input type="hidden" name="dmsDocUrl" id="dmsDocUrl_${count}" value="${uploadedDocSubList.dmsDocUrl}"/>
		
        </logic:iterate>
        </logic:notEmpty>
        </logic:present>
    	<logic:present name="uploadDocListForCp">
        <logic:iterate name="uploadDocListForCp" id="uploadedDocSubListForCp" indexId="count">
    <tr  class="white1">
    	<td><input type="checkbox" name="chk1" id="chk1" disabled="disabled" value="${uploadedDocSubListForCp.fileName}"/></td>
     	<td><a href="#" onclick="downloadFileNew('${uploadedDocSubListForCp.fileName}','${count}','CP');">${uploadedDocSubListForCp.fileName}</a>
     		<input type="hidden" name="fileName" id="fileName" value="${uploadedDocSubListForCp.fileName}"/>
     	</td>
        <td>${uploadedDocSubListForCp.docDescription}</td>
         <td>${uploadedDocSubListForCp.uploadedState}</td>
		 <td>${uploadedDocSubListForCp.docTypeDesc}</td>
        <td>${uploadedDocSubListForCp.userName}</td>
        </tr>
        
        	<input type="hidden" name="lbxDocId" id="lbxDocId_CP_${count}" value="${uploadedDocSubListForCp.lbxDocId}"/>
        	<input type="hidden" name="dmsDocId" id="dmsDocId_CP_${count}" value="${uploadedDocSubListForCp.dmsDocId}"/>
			<input type="hidden" name="dmsDocUrl" id="dmsDocUrl_CP_${count}" value="${uploadedDocSubListForCp.dmsDocUrl}"/>
				
		
        </logic:iterate>
        </logic:present>
    </table>
    </td>
</tr>
</table>

<logic:notEqual name="dmsProvider" value="dMAC">
<logic:present name="fromPYMAuthor">
<button type="button" name="delete" class="topformbutton2" id="delete" title="Alt+D" accesskey="D" onclick="deleteUploadDoc();" style="display:none"><bean:message key="button.delete" /></button>
</logic:present>

<logic:notPresent name="fromPYMAuthor">
<button type="button" name="delete" class="topformbutton2" id="delete" title="Alt+D" accesskey="D" onclick="deleteUploadDoc();"><bean:message key="button.delete" /></button>
</logic:notPresent>
</logic:notEqual>
	</fieldset>
	</html:form>
</div>



</div>
</logic:notPresent>
<logic:present name="underWriter">
		
		<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td><b><bean:message key="lbl.dealNo" /></b></td>
          <td>${dealHeader[0].dealNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${dealHeader[0].dealDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${dealHeader[0].dealCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${dealHeader[0].dealProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${dealHeader[0].dealProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${dealHeader[0].dealScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Under Writer</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
 
 

 
</fieldset>
	
	<html:form styleId="underwritingDocUpload" action="/underwritingDocUpload" method="post" enctype="multipart/form-data">
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>"/>

<fieldset>	






		 <legend><bean:message key="lbl.docInformation"/></legend> 

  
         <%-- <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
    	<td ><b><input type="checkbox" name="allchk" id="allchk" disabled="disabled" onclick="allChecked();"/><bean:message key="lbl.select" /></b></td> 
        <td><b><bean:message key="lbl.fileName"/></b></td>
		<td ><b><bean:message key="lbl.entityType"/></b></td>
		<td ><b><bean:message key="lbl.entityName"/></b></td>
		<td ><b><bean:message key="lbl.documentType"/></b></td>
		<td ><b><bean:message key="lbl.documentName"/></b></td>
		<td ><b><bean:message key="lbl.docDescription"/></b></td>
		<td ><b><bean:message key="lbl.uploadState"/></b></td>
        <td ><b><bean:message key="lbl.uploadedBy" /></b></td>
        <td ><b><bean:message key="lbl.uploadedDate" /></b></td>
        <td ><b><bean:message key="lbl.DmsDocNumber" /></b></td>
        <td> </td>
        </tr>
        <logic:present name="uploadedDocList">
        
        <logic:iterate name="uploadedDocList" id="uploadedDocSubList" indexId="count">
    <tr  class="white1">
    	<td><input type="checkbox" name="chk" disabled="disabled" id="chk${count}" value="${uploadedDocSubList.lbxDocId}"/></td>
     	<td><a href="#" onclick="downloadFile('${uploadedDocSubList.fileName}');">${uploadedDocSubList.fileName}</a>
     		<input type="hidden" name="fileName" id="fileName" value="${uploadedDocSubList.fileName}"/>
     	</td>
     	
		<td>${uploadedDocSubList.docTypeDesc}</td>
		<td>${uploadedDocSubList.entityName}</td>
		<td>${uploadedDocSubList.documentType}</td>
		<td>${uploadedDocSubList.documentName}</td>
        <td>${uploadedDocSubList.docDescription}</td>
        <td>${uploadedDocSubList.uploadedState}</td>
        <td>${uploadedDocSubList.userName}</td>
        <td>${uploadedDocSubList.uploadedDate}</td>
        <td>${uploadedDocSubList.dmsDocNumber}</td>
        <td align="center">
        	<input type="hidden" name="lbxDocId" id="lbxDocId_${count}" value="${uploadedDocSubList.lbxDocId}"/>
        	<input type="hidden" name="dmsDocId" id="dmsDocId_${count}" value="${uploadedDocSubList.dmsDocId}"/>
			<input type="hidden" name="dmsDocUrl" id="dmsDocUrl_${count}" value="${uploadedDocSubList.dmsDocUrl}"/>
			<a href='#' onClick="downloadFile('${uploadedDocSubList.fileName}','${count}');"><img style=\"cursor:pointer\" src="<%=request.getContextPath()%>/images/theme1/invoiceDownload1.png" width="18" height="18" ></a>	
		</td>
        </tr>
        </logic:iterate>
        
        </logic:present>
    
    </table>
    </td>
</tr>
</table> --%>

<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
    	<td ><b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/><bean:message key="lbl.select" /></b></td> 
        <td><b><bean:message key="lbl.fileName"/></b></td>
		<td ><b><bean:message key="lbl.docDescription"/></b></td>
		
		<td ><b><bean:message key="lbl.uploadState"/></b></td>
		<td ><b><bean:message key="lbl.documentType"/></b></td>
        <td ><b><bean:message key="lbl.uploadedBy" /></b></td>
        </tr>
        <logic:present name="uploadedDocList">
        <logic:notEmpty name="uploadedDocList">
         <logic:iterate name="uploadedDocList" id="uploadedDocSubList" indexId="count">
    <tr  class="white1">
    	<td><input type="checkbox" name="chk" id="chk" value="${uploadedDocSubList.fileName}"/></td>
     	<td><a href="#" onclick="downloadFileNew('${uploadedDocSubList.fileName}','${count}','CM');">${uploadedDocSubList.fileName}</a>
     		<input type="hidden" name="fileName" id="fileName" value="${uploadedDocSubList.fileName}"/>
     	</td>
        <td>${uploadedDocSubList.docDescription}</td>
         <td>${uploadedDocSubList.uploadedState}</td>
          <td>${uploadedDocSubList.docTypeDesc}</td>
        <td>${uploadedDocSubList.userName}</td>
        </tr>
        
			<input type="hidden" name="lbxDocId" id="lbxDocId_${count}" value="${uploadedDocSubList.lbxDocId}"/>
			<input type="hidden" name="dmsDocId" id="dmsDocId_${count}" value="${uploadedDocSubList.dmsDocId}"/>
			<input type="hidden" name="dmsDocUrl" id="dmsDocUrl_${count}" value="${uploadedDocSubList.dmsDocUrl}"/>
		
        </logic:iterate>
        </logic:notEmpty>
        </logic:present>
    	<logic:present name="uploadDocListForCp">
        <logic:iterate name="uploadDocListForCp" id="uploadedDocSubListForCp" indexId="count">
    <tr  class="white1">
    	<td><input type="checkbox" name="chk1" id="chk1" disabled="disabled" value="${uploadedDocSubListForCp.fileName}"/></td>
     	<td><a href="#" onclick="downloadFileNew('${uploadedDocSubListForCp.fileName}','${count}','CP');">${uploadedDocSubListForCp.fileName}</a>
     		<input type="hidden" name="fileName" id="fileName" value="${uploadedDocSubListForCp.fileName}"/>
     	</td>
        <td>${uploadedDocSubListForCp.docDescription}</td>
         <td>${uploadedDocSubListForCp.uploadedState}</td>
		 <td>${uploadedDocSubListForCp.docTypeDesc}</td>
        <td>${uploadedDocSubListForCp.userName}</td>
        </tr>
        
        	<input type="hidden" name="lbxDocId" id="lbxDocId_CP_${count}" value="${uploadedDocSubListForCp.lbxDocId}"/>
        	<input type="hidden" name="dmsDocId" id="dmsDocId_CP_${count}" value="${uploadedDocSubListForCp.dmsDocId}"/>
			<input type="hidden" name="dmsDocUrl" id="dmsDocUrl_CP_${count}" value="${uploadedDocSubListForCp.dmsDocUrl}"/>
				
		
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
	else if('<%=request.getAttribute("message").toString()%>'=='K')
	{
		alert('<bean:message key="msg.descExist" />');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='FiveDocOnly')
	{
		alert('<%=request.getAttribute("limitOfDocument").toString()%>');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='UploadSuccessful')
	{
		alert('<bean:message key="msg.uploadSuccessful" />');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='deletedoc')
	{
		alert('<bean:message key="lbl.dataDeleted" />');
	}
	
	if ('<%=request.getAttribute("message").toString()%>'=='X')
	{
	     alert('<bean:message key="lbl.Filetype" />');
	}
</script>
</logic:present>