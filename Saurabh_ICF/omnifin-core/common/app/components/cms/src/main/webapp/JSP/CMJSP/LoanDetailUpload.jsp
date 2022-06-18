<!--Author Name : Saurabh Singh-->
<!--Date of Creation : 29-April -2013-->
<!--Purpose  : Upload Manual Deviation-->
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
			LoggerMsg.info("in loanDetailUpload jsp....."+fileName);
			HttpSession sess=request.getSession();
			String processFile1=(String)sess.getAttribute("Processfile");
			String processFile=CommonFunction.checkNull(processFile1);
			if(processFile.equals(""))
			{
				processFile="NoFile";
			}
			LoggerMsg.info("in loanDetailUpload jsp..Process file..."+processFile);
	%>

<script type="text/javascript">
function validateManualDocUpload()
{
	DisButClass.prototype.DisButMethod();
	if(document.getElementById('docFile').value=="")
	{
		alert("Choose file to be uploaded.");
		DisButClass.prototype.EnbButMethod();
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
function submitDocUpload()
{
//alert("submitDocUpload");
	DisButClass.prototype.DisButMethod();
	
	var sourcepath=document.getElementById("contextPath").value;
	if(validateManualDocUpload())
	{
  
   var filelist='<%=fileName%>';
   //alert(filelist);
   var splitVal = filelist.split("~");
  
       for(var cnt=0;cnt<splitVal.length;cnt++)
         {    
             var file=document.getElementById('docFile').value;
                if(splitVal[cnt].toLowerCase()!=file.toLowerCase())
                  {                  
                 
                 
                  } 
                  else 
                  { 
                  alert(splitVal[cnt] +" has already been uploaded today.");
                  DisButClass.prototype.EnbButMethod();
                  document.getElementById('docFile').focus();
                  return false;  
                  }
         }
        
                  document.getElementById("loanDetailUpload").action=sourcepath+"/loanDetailUploadStart.do?method=uploadData";
	              document.getElementById("loanDetailUpload").submit();
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
	<body onload="enableAnchor();document.getElementById('DocUpload').docFile.focus();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
	</div>
	<div id="centercolumn">	
	<div id="revisedcontainer">	
	<html:form styleId="loanDetailUpload" action="/underwritingDocUpload" method="post" enctype="multipart/form-data">    
	
	<fieldset>	  
	<legend><bean:message key="lbl.loanDetailUploadProcess"/></legend>  
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
			<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>"/>
			<input type="hidden" name="txnfile" id="txnfile" value="<%=processFile%>"/>
		</td>
	</tr>
    <tr>
      <td><table width="100%" border="0" cellspacing="1" cellpadding="4">
       	<tr>
		  <td width="20%"><bean:message key="lbl.fileDescription"/></td>
		  <td width="30%"><html:file size="40" property="docFile" styleId="docFile" styleClass="text"/></td>
		  <td width="40%"><button type="button" name="upload"  id="upload" title="Alt+U" accesskey="U" class="topformbutton2" onclick="return submitDocUpload();" ><bean:message key="button.upload" /></button></td>
		</tr>
		
		<tr>		
		<td colspan="4">
		<button type="button" name="startProcessing" class="topformbutton3" id="startProcessing" title="Alt+P" accesskey="P" onclick="startProcessLoanDetailUpload();"><bean:message key="button.startProcng" /></button>&nbsp;&nbsp;&nbsp;
		<button type="button" name="errorLog" class="topformbutton2" id="errorLog" title="Alt+E" accesskey="E" onclick="errorLogDownloadForLoanDetailUpload();"><bean:message key="button.errorlog" /></button>&nbsp;&nbsp;&nbsp;
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
		alert('<bean:message key="lbl.processingCompleted" />'+", "+<%=request.getAttribute("noOfUploadedRecord")%>+" records has been uploaded");
 </script>
  </logic:present>

  <logic:present name="noinserted">
 <script type="text/javascript">	
 		alert("Problem with format of data provided.");
 </script>
  </logic:present>