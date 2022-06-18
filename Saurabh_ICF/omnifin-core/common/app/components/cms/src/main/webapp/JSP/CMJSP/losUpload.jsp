<!--Author Name : Gaurav Garg-->
<!--Date of Creation : 28-Aug-2016-->
<!--Purpose  : Upload Loan details-->
<!--Documentation : -->


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
			LoggerMsg.info("in losUpload jsp....."+fileName);
			HttpSession sess=request.getSession();
			String processFile1=(String)sess.getAttribute("Processfile");
			String processFile=CommonFunction.checkNull(processFile1);
			if(processFile.equals(""))
			{
				processFile="NoFile";
			}
			LoggerMsg.info("in losUpload jsp..Process file..."+processFile);
	%>

<script type="text/javascript">
function validateFileName()
{
	DisButClass.prototype.DisButMethod();
	if(document.getElementById('docFile').value=="")
	{
		alert("Choose file to be uploaded.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("docFile").focus(); 
	    return false; 
	}
	var filename= (document.getElementById('docFile').value).replace(/^.*(\\|\/|\:)/, '');
	
	if(filename.length >  50){
		
		alert("File Name size can not be greater than 50");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("docFile").value="";
		document.getElementById("docFile").focus();
		return false;
		}
var file_Name =document.getElementById('docFile').value;
var ext = file_Name.substring(file_Name.lastIndexOf('.') + 1);

if(ext == "xls" || ext == "XLS")
{
	return true;
} 
else
{
	alert("Upload .xls file only.");
	DisButClass.prototype.EnbButMethod();
	document.getElementById('docFile').value='';
	document.getElementById('docFile').focus();
	return false;
}
  
 }
function validateDocument()
{
//alert("validateDocument");
	DisButClass.prototype.DisButMethod();
	
	var sourcepath=document.getElementById("contextPath").value;
	if(validateFileName())
	{
  
   var filelist='<%=fileName%>';
   //alert(filelist);
   var splitVal = filelist.split("~");
  
   for(var i=0;i<splitVal.length;i++)
     {    
 		var file=document.getElementById('docFile').value;
       	if(splitVal[i].toLowerCase()!=file.toLowerCase()){
       	
     	  } 
      	 else 
     	  { 
      	 alert(splitVal[i] +" has already been uploaded today.");
    	  DisButClass.prototype.EnbButMethod();
   	  	 document.getElementById('docFile').focus();
   	 	 return false;  
       }
    }
        
    document.getElementById("losUpload").action=sourcepath+"/losUploadStart.do?method=validateData";
    document.getElementById("losUpload").submit();
    return true;
	
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/uploadFiles.js"></script>


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
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
	</div>
	<div id="centercolumn">	
	<div id="revisedcontainer">	
	<html:form styleId="losUpload" action="/losUpload" method="post" enctype="multipart/form-data">    
	
	<fieldset>
	<logic:present name="LOS">
		<legend><bean:message key="lbl.losUpload"/></legend>
	</logic:present>
	<logic:present name="LMS">
		<legend><bean:message key="lbl.lmsUpload"/></legend>
	</logic:present>
		    
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
			<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>"/>
			<input type="hidden" name="txnfile" id="txnfile" value="<%=processFile%>"/>
			<input type="hidden" name="colName" id="colName" value="<%=request.getAttribute("colName")%>"/>
			<input type="hidden" name="error" id="error" value="<%=request.getAttribute("errorType")%>"/>
			<input type="hidden" name="uploadFlag" id="uploadFlag" value="${uploadFlag}"/>
		</td>
	</tr>
    <tr>
      <td><table width="100%" border="0" cellspacing="1" cellpadding="2">
       	<tr>
		  <td width="20%"><bean:message key="lbl.fileDescription"/></td>
		  <td width="30%"><html:file size="40" property="docFile" styleId="docFile" styleClass="text"/></td>
		  <td width="40%"><button type="button" name="upload"  id="upload" title="Alt+U" accesskey="U" class="topformbutton2" onclick="return validateDocument();" ><bean:message key="button.validate" /></button></td>
		</tr>
		
		<tr>		
		<td colspan="2">
		<!-- <button type="button" name="downloadFormat" class="topformbutton3" id="downloadFormat"   onclick="errorLogDownloadForLosUpload();"><bean:message key="button.downloadFormat" /></button>&nbsp;&nbsp;&nbsp;-->
		
		
		<button type="button" name="xlsfileDownload" class="topformbutton4" id="xlsfileDownload" onclick="exlsFileDownloadForLosUpload();"><bean:message key="button.xlsDownload" /></button>&nbsp;&nbsp;&nbsp;
		<button type="button" name="startProcessing" class="topformbutton3" id="startProcessing" onclick="startLosUpload();"><bean:message key="button.upload" /></button>&nbsp;&nbsp;&nbsp;
		<%-- <button type="button" name="errorLog" class="topformbutton2" id="errorLog"  onclick="errorLogDownloadForLosUpload();"><bean:message key="button.errorlog" /></button>&nbsp;&nbsp;&nbsp; --%>
	    <logic:present name="smsfail">
			<button type="button" name="errorLog" class="topformbutton2" id="errorLog"  onclick="errorLogDownloadForLosUpload();"><bean:message key="button.errorlog" /></button>&nbsp;&nbsp;&nbsp;
  		</logic:present>
	    </td>
		</tr>
		  
      </table>
     </td>
</tr>
</table> 
	 
	  </fieldset>  

	</html:form>
</div>
</div>
</body>
</html:html>
 
 
 
<logic:present name="smsfail">
 <script type="text/javascript">
 	alert('<bean:message key="lbl.ValidFail2" />');
 </script>
  </logic:present>

 <logic:present name="sms">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.ValidSuccess" />');
 </script>
  </logic:present>
  
  <logic:present name="smsno">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.smsno" />');
 </script>
  </logic:present>
<%--     <logic:present name="smsfail">
 <script type="text/javascript">
	 var colName=document.getElementById("colName").value;
	 var error=document.getElementById("error").value;
	 alert("Error encountered in sheet : "+<%=request.getAttribute("sheetName")%>+" \nRow No : "+<%=request.getAttribute("rowno")%>+"\nField Name : "+colName+"\nError Type : "+error+" Mismatch");
 </script>
  </logic:present> --%>
   <logic:present name="maxCount">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.maxCount" />');
 </script>
  </logic:present>
   <logic:present name="inserted">
 	<script type="text/javascript">	
 		alert('<bean:message key="lbl.processingCompleted" />');
		<%-- alert('<bean:message key="lbl.processingCompleted" />'+", "+<%=request.getAttribute("noOfUploadedRecord")%>+" records has been uploaded"); --%>
 	</script>
  </logic:present>

  <logic:present name="noinserted">
 <script type="text/javascript">	
 	alert('<bean:message key="lbl.rollbackDone" />');
	<%--  alert("Process Interupted,Data RollBacked::::"+<%=session.getAttribute("vresult")%>);  --%>
 	</script>
  </logic:present>
