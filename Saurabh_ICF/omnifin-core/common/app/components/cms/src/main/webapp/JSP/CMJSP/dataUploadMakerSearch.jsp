
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
	if(document.getElementById('fileDescription').value=="")
	{
		alert("Please Fill File Description.");
		document.getElementById("fileDescription").focus(); 
	    return false; 
	}
var fup = document.getElementById('docFile');

var file_Name = fup.value;
var ext = file_Name.substring(file_Name.lastIndexOf('.') + 1);
var fileInitialName=file_Name.substring(0,8);

if((ext == "xls" || ext == "XLS")&& (fileInitialName == "TA_LOANS"))
{
	return true;
} 
else
{
	alert("Upload Valid Excel file only with extension xls");
	fup.value='';
	fup.focus();
	return false;
}
  
 }
function submitDocUpload()
{
//alert("submitDocUpload");
	DisButClass.prototype.DisButMethod();
              var sourcepath=document.getElementById("contextPath").value;	
        if(validateDocUpload())
	{
                  document.getElementById("DocUpload").action=sourcepath+"/dataUploadMakerProcessAction.do?method=uploadData";
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
<script type="text/javascript"  src="<%=request.getContextPath() %>/js/cmScript/cmLoanInitjs.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/dataUpload.js"></script>
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
	<body onload="enableAnchor();document.getElementById('DocUpload').docFile.focus();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
	</div>
	<div id="centercolumn">	
	<div id="revisedcontainer">	
	<html:form styleId="DocUpload" action="/dataUploadMakerProcessAction" method="post" enctype="multipart/form-data">    
	
	<fieldset>	  
	<legend><bean:message key="lbl.dataDocUploadMaker"/></legend>  
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>"/>
	<input type="hidden" name="txnfile" id="txnfile" value="<%=processFile%>"/>
     <tr>
      <td><table width="100%" border="0" cellspacing="1" cellpadding="4">
       	<tr>
       	 <td width="20%"><bean:message key="lbl.dataUploadFileDescriptionCol"/></td>
       	<td width="20%"><html:text property="fileDescription" styleId="fileDescription"></html:text></td>
		  <td width="20%"><bean:message key="lbl.dataUploadFileDescription"/></td> 
		  <td width="30%"><html:file size="40" property="docFile" styleId="docFile" styleClass="text"/></td>
		  <td width="40%"><html:button property="upload" value="Upload" styleId="upload"  styleClass="topformbutton2" onclick="submitDocUpload();" /></td>
		</tr>
		
      </table></td>
</tr>
</table> 
	 
	  </fieldset>  
<!-- Grid Starts  Here -->
<br/>
<fieldset>
		 <legend><bean:message key="lbl.dataUploadMakerList" /></legend> 
<table width="100%" border="0" cellpadding="0" cellspacing="1" >
<tr>
 <td class="gridtd">
 <table width="100%" border="0" cellpadding="4" cellspacing="1">
 <tr class="white2">
 			
 			<td><strong><bean:message key="lbl.select"/></strong></td>
 			<td><strong><bean:message key="lbl.fileName"/></strong></td>
 			<td><strong><bean:message key="lbl.fileDescription"/></strong></td>
 			<td><strong><bean:message key="lbl.rowCount"/></strong></td>
 			<td><strong><bean:message key="lbl.taxAmountSum"/></strong></td>
       		<td><strong><bean:message key="lbl.authorRemarksMaker"/></strong></td>
      	</tr>
  
<logic:present name="list">
<logic:iterate name="list" id="sublist" >

  <tr  class="white1">
  			<td><input type="checkbox" name="chk" id="chk" value="" /></td>
  			
 			<td><html:link action='/download?fileName=${sublist.fileName}'>${sublist.fileName}</html:link></td>
			<input type="hidden" name="fileChkName" value="${sublist.fileName}" />
			<td>${sublist.fileDescription}</td>
			<td>${sublist.rowCount}</td>
			<td>${sublist.totalTaxAmount}</td>
			
			<td width="20">${sublist.authorRemarks} </td>
	 </tr>

  </logic:iterate>

  </logic:present>

</table>
</td></tr></table>
<logic:present name="list">
			<tr>
				 	 <td> <button type="button" name="button" id="button" class="topformbutton3" title="Alt+D" accesskey="P" onclick="deleteFile();"><bean:message key="button.Delete" /></button>
				  	</td>	
				  	<td> <button type="button" name="button" id="button" class="topformbutton3" title="Alt+F" accesskey="P" onclick="forwardFile();"><bean:message key="button.Forward" /></button>
				  	</td>			
			  </tr>
		 </logic:present>
</fieldset>  
<!-- Grid ends Here -->
	</html:form>
</div>
</body>
</html:html>

 <logic:present name="sms">
 <script type="text/javascript">	
 		
 		alert('<bean:message key="lbl.datasms" />'+'\n'+'Processed File Has '+<%=request.getAttribute("row_count")%>+ ' rows and Total TXN_AMOUNT is '+<%=request.getAttribute("total_txn_amount")%>);
		//alert('<bean:message key="lbl.datasms" />');
		 var sourcepathSubmit=document.getElementById("contextPath").value;	
		 document.getElementById("DocUpload").action=sourcepathSubmit+"/dataUploadMaker.do";
		 document.getElementById("DocUpload").submit();
 </script>
  </logic:present> 
  
  <logic:present name="smsno">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.datasmsno" />');
 </script>
  </logic:present>
   <logic:present name="error">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.fileAlreadyExists" />');
		var sourcepathSubmit=document.getElementById("contextPath").value;	
		 document.getElementById("DocUpload").action=sourcepathSubmit+"/dataUploadMaker.do";
		 document.getElementById("DocUpload").submit();
 </script>
  </logic:present>
   <logic:present name="status">
 <script type="text/javascript">
 		if('<%=request.getAttribute("status").toString()%>'=='forward'){
 			alert('<bean:message key="lbl.forward" />');
 		}else if('<%=request.getAttribute("status").toString()%>'=='notforward'){
 			alert('<bean:message key="lbl.notForward" />');
 		}
		else if('<%=request.getAttribute("status").toString()%>'=='delete'){
 			alert('<bean:message key="lbl.deleteFile" />');
 		}else{
 			alert('<bean:message key="lbl.notDeleteFile" />');
 		}
		var sourcepathSubmit=document.getElementById("contextPath").value;	
		 document.getElementById("DocUpload").action=sourcepathSubmit+"/dataUploadMaker.do";
		 document.getElementById("DocUpload").submit();
 </script>
  </logic:present>
  