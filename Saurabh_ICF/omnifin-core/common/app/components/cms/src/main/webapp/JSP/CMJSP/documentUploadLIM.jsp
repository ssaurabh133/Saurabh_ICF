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

		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/loanDetails.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	   <script type="text/javascript"  src="<%=request.getContextPath() %>/js/cpScript/documentRelated.js"></script>
	<logic:present name="back">

<script type="text/javascript">
  alert("You can't move without saving before Lead Details!!!");
</script>

</logic:present>
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
	  <fieldset>

<logic:present name="loanId">
<table cellspacing=0 cellpadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${loanHeader[0].dealLoanNo}</td>
           <td ><b><bean:message key="lbl.loanNo" /></b></td>
          <td >${loanHeader[0].loanNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${loanHeader[0].loanDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${loanHeader[0].loanCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${loanHeader[0].loanProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${loanHeader[0].loanProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${loanHeader[0].loanScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Loan Initiation</td>
          </tr>
        </table>
</td>
</tr>
</table>
 </logic:present>

</fieldset>
	<html:form styleId="underwritingDocUpload" action="/underwritingDocUpload" method="post" enctype="multipart/form-data" onsubmit="submitDocUpload()">
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>"/>
	
<fieldset>	
		 <legend><bean:message key="lbl.docInformation"/></legend> 

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="6">
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
    	  <logic:notEmpty name="uploadDocListForCp">
	        <logic:iterate name="uploadDocListForCp" id="uploadedDocSubListForCp" indexId="count">  
	         <tr  class="white1">
	    	
	     	 	<td><input type="checkbox" name="chk" id="chk" value="${uploadedDocSubListForCp.fileName}"/></td>
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
        </logic:notEmpty>
        </logic:present>
        
        
             <logic:present name="uploadedDocSanctioLetterList">
        <logic:notEmpty name="uploadedDocSanctioLetterList">
        <logic:iterate name="uploadedDocSanctioLetterList" id="uploadedDocSubListForSanction" indexId="count">
	        <tr  class="white1">
	    	 
	     	<td><input type="checkbox" name="chk" id="chk" value="${uploadedDocSubListForSanction.fileName}"/></td>
     	<td><a href="#" onclick="downloadFileNew('${uploadedDocSubListForSanction.fileName}','${count}','SL');">${uploadedDocSubListForSanction.fileName}</a>
     		<input type="hidden" name="fileName" id="fileName" value="${uploadedDocSubListForSanction.fileName}"/>
     	</td>
	        <td>${uploadedDocSubListForSanction.docDescription}</td>
	         <td>${uploadedDocSubListForSanction.uploadedState}</td>
	         <td>${uploadedDocSubListForSanction.docTypeDesc}</td>
	        <td>${uploadedDocSubListForSanction.userName}</td>
	        </tr>
	        
			<input type="hidden" name="lbxDocId" id="lbxDocId_SL_${count}" value="${uploadedDocSubListForSanction.lbxDocId}"/>
			<input type="hidden" name="dmsDocId" id="dmsDocId_SL_${count}" value="${uploadedDocSubListForSanction.dmsDocId}"/>
			<input type="hidden" name="dmsDocUrl" id="dmsDocUrl_SL_${count}" value="${uploadedDocSubListForSanction.dmsDocUrl}"/>
			
        </logic:iterate>
        </logic:notEmpty>
        </logic:present>
        
        
    </table>
    </td>
</tr>
</table>

	</fieldset>
	</html:form>
</div>



</div>



<script>
	setFramevalues("underwritingDocUpload");
</script>

<script>
	parent.menu.document.test.checkModifications.value = '';
	parent.menu.document.test.getFormName.value = "deviationApprovalForm";
</script>
</body>
</html:html>
