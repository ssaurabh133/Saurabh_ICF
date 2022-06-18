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
 
if(ext == "xls" || ext == "XLS")
{
	return true;
} 
else
{
	alert("Upload xls file only.");
	fup.focus();
	return false;
}
  
 }
function submitPoolUpload()
{
 

	var sourcepath=document.getElementById("contextPath").value;
	
	if((document.getElementById("poolID").value)==""){
	alert("Please select PoolID ");
	document.getElementById("upload").removeAttribute("disabled");
	return false;
	}
	 
	if(validateDocUpload())
	{
                  document.getElementById("poolCreationForm").action=sourcepath+"/poolCreationProcessAction.do?method=submitPoolUpload";
	              document.getElementById("poolCreationForm").submit();
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
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/poolCreation.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		
<script type="text/javascript"  src="<%=request.getContextPath() %>/js/cmScript/cmLoanInitjs.js"></script>
	</head>
	<body onload="enableAnchor();document.getElementById('poolCreationForm').poolButton.focus();init_fields();" oncontextmenu="return false" onclick="parent_disable();" onunload="closeAllLovCallUnloadBody();">
	
	<div id="centercolumn">	
	<div id="revisedcontainer">	
	<html:form styleId="poolCreationForm" action="/poolCreationProcessAction"  enctype="multipart/form-data">    
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>"/>
	<input type="hidden" name="txnfile" id="txnfile" value="<%=processFile%>"/>
	<input type="hidden" value="<%=request.getAttribute("poolID")%>"  name="poolId" id="poolId"/>
	<fieldset>	  
	<legend><bean:message key="lbl.poolCreationUpload"/></legend>  
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	
     <tr>
      <td>
      <table width="100%" border="0" cellspacing="1" cellpadding="4">
      
      	 <tr>
			<td><bean:message key="lbl.poolID"></bean:message><font color="red">* </font>
	
		   <html:text styleClass="text" property="poolID" styleId="poolID" maxlength="20" readonly="true" tabindex="-1" />
           <!-- <img onclick="openLOVCommon(122,'poolCreationForm','poolID','','', '','','','hcommon')" src="<%= request.getContextPath()%>/images/lov.gif"> -->
           <html:button property="poolButton" styleId="poolButton" onclick="openLOVCommon(122,'poolCreationForm','poolID','','', '','','','hcommon');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
           <html:hidden  property="lbxPoolID" styleId="lbxPoolID"/>
           <input type="hidden" name="hcommon" id="hcommon"/> 
   		   </td>
   		 	
		  <td><bean:message key="lbl.fileDescription"/>
		  <html:file size="40" property="docFile" styleId="docFile" styleClass="text"/> 
		  </td>
	
		<td>  <button type="button" property="upload" styleId="upload"  styleClass="topformbutton2" title="Alt+U" accesskey="U" onclick="return submitPoolUpload();" ><bean:message key="button.upload" /></button></td>
		</tr>
	
		  
      </table>
      </td>
    </tr>
    	<tr>		
		<td>
		<button type="button" name="startProcessing" class="topformbutton3" id="startProcessing"  title="Alt+P" accesskey="P" onclick="return startPoolProcess();"><bean:message key="button.startProcng" /></button>&nbsp;&nbsp;&nbsp;
		<button type="button" name="errorLog" class="topformbutton2" id="errorLog" title="Alt+E" accesskey="E" onclick="return errorLogPoolDownload();"><bean:message key="button.errorlog" /></button>&nbsp;&nbsp;&nbsp;
	    <button type="button" name="summary" class="topformbutton2" id="summary" title="Alt+S" accesskey="S" onclick="return uploadPoolSummary();"><bean:message key="button.summary" /></button>
	    <button type="button" id="addEdit" class="topformbutton3" onclick="return addEditWindow();" title="Alt+A" accesskey="A" ><bean:message key="button.addedit" /></button> 
	    </td>
	    <td></td>
	    <td></td>
   		   <td></td>
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
		alert('<bean:message key="lbl.processingCompleted" />');
 </script>
  </logic:present>

  <logic:present name="noinserted">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.processingError" />');
 </script>
  </logic:present>