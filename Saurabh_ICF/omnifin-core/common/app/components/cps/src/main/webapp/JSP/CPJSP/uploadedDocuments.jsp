 <!--  
Author Name:- Richa Bansal
Date of Creation:- 30/09/2016
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
<script type="text/javascript"  src="<%=request.getContextPath() %>/js/cpScript/partnerCapturingDetails.js"></script>
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

	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form styleId="underwritingDocUpload" action="/underwritingDocUpload" method="post" enctype="multipart/form-data" onsubmit="submitDocUpload()">
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>"/>
		<input type="hidden" id="bpId" name="bpId" value="${sessionScope.bpId }"/>
	<input type="hidden" id="bpType" name="bpType" value="${sessionScope.bpType }"/>
	<input type="hidden" id="entityId" name="entityId" value="${entityId }"/>
	
	<html:hidden property="documentId" styleId="documentId" value="${requestScope.docId}" />
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
	<logic:notPresent name="viewDeal">
		<logic:notEqual name="dmsProvider" value="dMAC">
	<fieldset>	  
	<legend><bean:message key="lbl.docUploadDetails"/></legend>  
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
     <tr>
      <td><table width="100%" border="0" cellspacing="1" cellpadding="4">
        <html:hidden property="txnType" styleId="txnType" value="D"/>
		<tr>
		  <td width="23%" style="width:23%"><bean:message key="lbl.docDescription"/><font color="red">*</font></td>
		  <td width="21%"><html:text property="docDescription" styleClass="text" value="" tabindex="1"  maxlength="100" styleId="docDescription" /></td>
          <td width="25%"><html:file property="docFile" styleId="docFile" styleClass="text" /></td>
          
         	<td width="20%"> <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="return submitDocUpload();"><bean:message key="button.upload" /></button></td>
         
		</tr>
		<logic:present name="InternalDocs">
		<logic:present name="childAvailble">
		<tr>
		 <td style="width:23%"><bean:message key="lbl.child"/><font color="red">*</font></td>
 			<td>

 	 	<html:select property="documentStatus" styleId="documentStatus" styleClass="text" disabled="false"> 
        	<html:option value="" >--<bean:message key="lbl.select" />--</html:option>
        	<logic:present name="documentStatus">
        		<html:optionsCollection name="documentStatus" label="documentDesc" value="docChildId" />
        	</logic:present>	         	  	
  	  		</html:select>
 				
 			</td>
  		</tr>
  		</logic:present>
	</logic:present>	  
		  
      </table></td>
</tr>
</table>    

	 
	 
	  </fieldset>
	  </logic:notEqual>
	<logic:equal name="dmsProvider" value="dMAC">
	<legend><bean:message key="lbl.dmsMetadata"/></legend>  
	<fieldset>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><table width="100%" border="0" cellspacing="1" cellpadding="4">
					<logic:present name="InternalDocs">
						<logic:present name="childAvailble">
							<tr>
								<td style="width:23%"><bean:message key="lbl.child"/><font color="red">*</font></td>
								<td>
									<html:select property="documentStatus" styleId="documentStatus" styleClass="text" disabled="false" onchange="manageChildDoc()"> 
										<html:option value="" >--<bean:message key="lbl.select" />--</html:option>
										<logic:present name="documentStatus">
											<html:optionsCollection name="documentStatus" label="documentDesc" value="docChildId" />
										</logic:present>
									</html:select>
								</td>
							</tr>
						</logic:present>
					</logic:present>
					<tr>
						<td>Form No</td><td><input type="text" disabled="disabled" name="applicationFormNo" id="applicationFormNo" value="${dmsDetailsMap['applicationFormNo']}"/></td>
						<td>Branch</td><td><input type="text" disabled="disabled" name="branch" id="branch" value="${dmsDetailsMap['branch']}"/></td>
						<td>Product</td><td><input type="text" disabled="disabled" name="product" id="product" value="${dmsDetailsMap['product']}"/></td>
					</tr>
					<tr>
						<td>Scheme</td><td><input type="text" disabled="disabled" name="scheme" id="scheme" value="${dmsDetailsMap['scheme']}"/></td>
						<td>DMS Number</td><td><input type="text" disabled="disabled" name="dmsNumber" id="dmsNumber" value="${dmsDetailsMap['dmsNumber']}"/></td>
						<td>Entity Type</td><td><input type="text" disabled="disabled" name="entityType" id="entityType" value="${dmsDetailsMap['entityType']}"/></td>
					</tr>
					<tr>
						<td>Entity Id</td><td><input type="text" disabled="disabled" name="entityId" id="entityId" value="${dmsDetailsMap['entityId']}"/></td>
						<td>Entity Desc</td><td><input type="text" disabled="disabled" name="entityDesc" id="entityDesc" value="${dmsDetailsMap['entityDesc']}"/></td>
						<td>PAN No</td><td><input type="text" disabled="disabled" name="panNumber" id="panNumber" value="${dmsDetailsMap['panNumber']}"/></td>
					</tr>
					<tr>
						<td>Aadhar No</td><td><input type="text" disabled="disabled" name="aadharNumber" id="aadharNumber" value="${dmsDetailsMap['aadharNumber']}"/></td>
						<td>Email Id</td><td><input type="text" disabled="disabled" name="email" id="email" value="${dmsDetailsMap['email']}"/></td>
						<td>Mobile No</td><td><input type="text" disabled="disabled" name="mobileNumber" id="mobileNumber" value="${dmsDetailsMap['mobileNumber']}"/></td>
					</tr>
					<tr>
						<td>DOB</td><td><input type="text" disabled="disabled" name="dob" id="dob" value="${dmsDetailsMap['dob']}"/></td>
						<td>Document Type</td><td><input type="text" disabled="disabled" name="documentTypeName" id="documentTypeName" value="${dmsDetailsMap['documentTypeName']}"/></td>
						<td>Uploaded By</td><td><input type="text" disabled="disabled" name="uploadedBy" id="uploadedBy" value="${dmsDetailsMap['uploadedBy']}"/></td>
					</tr>
					<td width="20%"> <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="return docUpload('${dmsCredential['DMS_URL']}','checklist');"><bean:message key="button.upload" /></button></td>
					</tr>
				</table></td>
			</tr>
		</table>
	</fieldset>
	</logic:equal>
</logic:notPresent>


  
<fieldset>	
		 <legend><bean:message key="lbl.docInformation"/></legend> 

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
    	<logic:notPresent name="viewDeal">
    	<td ><b><bean:message key="lbl.select" /></b></td>
    	</logic:notPresent>
        <td><b><bean:message key="lbl.fileName"/></b></td>
		<td ><b><bean:message key="lbl.docDescription"/></b></td>
		<td ><b><bean:message key="lbl.childDocDescription"/></b></td>
		<td ><b><bean:message key="lbl.uploadState"/></b></td>
		
        <td ><b><bean:message key="lbl.uploadedBy" /></b></td>
        <td> </td>
        </tr>
        <logic:present name="fetchUploadDocument">
        <logic:notEmpty name="fetchUploadDocument">
        <logic:iterate name="fetchUploadDocument" id="uploadedDocSubList" indexId="count">
    <tr  class="white1">
    <logic:notPresent name="viewDeal">
    	<td><input type="radio" name="chk" id="chk" value="${uploadedDocSubList.lbxDocId}"/></td>
    	</logic:notPresent>
     	<td>${uploadedDocSubList.fileName}
     		<input type="hidden" name="fileName" id="fileName" value="${uploadedDocSubList.fileName}"/>
     	</td>
        <td>${uploadedDocSubList.docDescription}</td>
         <td>${uploadedDocSubList.childDocDesc}</td>
         <td>${uploadedDocSubList.uploadedState}</td>
        <td>${uploadedDocSubList.userName}</td>
        <td align="center">
			<input type="hidden" name="dmsDocId" id="dmsDocId_${count}" value="${uploadedDocSubList.dmsDocId}"/>
			<input type="hidden" name="dmsDocUrl" id="dmsDocUrl_${count}" value="${uploadedDocSubList.dmsDocUrl}"/>
			<input type="hidden" name="lbxDocId" id="lbxDocId_${count}" value="${uploadedDocSubList.lbxDocId}"/>
			<a href="#" onclick="downloadFile('${uploadedDocSubList.fileName}','${uploadedDocSubList.docId}','${count}');"><img style=\"cursor:pointer\" src="<%=request.getContextPath()%>/images/theme1/invoiceDownload1.png" width="18" height="18" ></a>	
		</td>
        </tr>
        </logic:iterate>
        </logic:notEmpty>
        </logic:present>

    </table>
    </td>
</tr>
</table>
<%-- <logic:notPresent name="viewDeal">
<button type="button" name="delete" class="topformbutton2" id="delete" title="Alt+D" accesskey="D" onclick="deleteUploadDoc();"><bean:message key="button.delete" /></button>
	</logic:notPresent> --%>
	</fieldset>
	</html:form>
</div>



</div>


<script>
	setFramevalues("underwritingDocUpload");
</script>

</body>
</html:html>
<logic:present name="msg">

 <script type="text/javascript">
	if('<%=request.getAttribute("msg").toString()%>'=='childNotFound')
	{
		alert('Please Capture Child Docs First!!!');
		window.close();
	}
</script>
</logic:present>

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
	else if('<%=request.getAttribute("message").toString()%>'=='UploadSuccessful')
	{
		alert('<bean:message key="msg.uploadSuccessful" />');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='deletedoc')
	{
		alert('<bean:message key="lbl.dataDeleted" />');
	}
	
	else if ('<%=request.getAttribute("message").toString()%>'=='X')
	{
	     alert('<bean:message key="lbl.Filetype" />');
	}
	else if ('<%=request.getAttribute("message").toString()%>'=='Notdeletedoc')
	{
	     alert('File Not Deleted');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='fileavailable')
		{
		alert('file already uploaded');
		}
</script>
</logic:present>