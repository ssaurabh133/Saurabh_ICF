<%@ page language="java"%>
<%@ page session="true"%>

<%@page import="com.connect.CommonFunction"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
String fileName=(String)request.getAttribute("fileName");

HttpSession sess=request.getSession();
String processFile1=(String)sess.getAttribute("Processfile");
String processFile=CommonFunction.checkNull(processFile1);
if(processFile.equals(""))
{
processFile="NoFile";
}

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
if(ext == "csv" || ext == "CSV")
{
	return true;
} 
/* if(ext == "xls" || ext == "XLS" || ext == "xlsx" || ext == "XLSX")
{
	return true;
}  */
else
{
	alert("Upload excel file only.");
	fup.value='';
	fup.focus();
	return false;
}
  
 }
function submitDocUpload()
{
//alert("submitDocUpload");

	var sourcepath=document.getElementById("contextPath").value;
	if(validateDocUpload())
	{
  
   var filelist='<%=fileName%>';
   //alert(filelist);
   var splitVal = filelist.split("~");
  
       for(var cnt=0;cnt<splitVal.length;cnt++)
         {    
             var file=document.getElementById('docFile').value;
             var count = file.split('.').length-1; 
         	 if(count > 1)  
         	 {  
              alert("Please provide right file name");  
         	  return false;
         	 }
                if(splitVal[cnt].toLowerCase()!=file.toLowerCase())
                  {                  
                 
                 
                  } 
                  else 
                  { 
                  alert(splitVal[cnt] +" has already been uploaded today.");
                  document.getElementById('docFile').focus();
                  return false;  
                  }
         }
        
                  document.getElementById("sourcingForm").action=sourcepath+"/presentaionProcUpload.do?method=uploadFile";
	              document.getElementById("sourcingForm").submit();
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
		

<script type="text/javascript"  src="<%=request.getContextPath() %>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript"  src="<%=request.getContextPath() %>/js/cmScript/presentationProcessUpload.js"></script>
	</head>
	<body onload="enableAnchor();document.getElementById('DocUpload').docFile.focus();">
	
	<div id="centercolumn">	
	<div id="revisedcontainer">	
	<html:form action="/presentaionProcessMain" method="post" styleId="sourcingForm" enctype="multipart/form-data">		
	<fieldset><legend><bean:message key="lbl.PresentationUpload"/></legend>  
	 <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	 <input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	 <input type="hidden" name="txnfile" id="txnfile" value="<%=processFile%>"/>
	 <input type="hidden" name="msg" id="msg" value="<%=request.getAttribute("msg") %>"/>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
      <td><table width="100%" border="0" cellspacing="1" cellpadding="4">
      		<tr>
      			 <td><bean:message key="lbl.presentationDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
			 	 <td><html:text property="presentationDate" readonly="true"  styleId="presentationDate" styleClass="text" value="${userobject.businessdate }" maxlength="100"/>&nbsp;</td>
			   	 <td><bean:message key="lbl.depositBankId"/><font color="red">*</font></td>
				 <td>	
	 				<html:text styleClass="text" property="bank" styleId="bank" maxlength="100" value="${data.bank}" readonly="false"
	 				onchange="closeLovCallonLov1();openLOVCommon(79,'sourcingForm','bank','','branch', 'lbxBranchID','','','lbxBankID','','','Y')"
	 				 tabindex="-1"/>
    				<html:button property="bankButton" styleId="bankButton" onclick="closeLovCallonLov1();openLOVCommon(79,'sourcingForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" value=" " styleClass="lovbutton"></html:button>
    			    <html:hidden  property="lbxBankID" styleId="lbxBankID" value="${data.lbxBankID}"/>
				 </td>      			
      		</tr>
      		<tr>
      			<td><bean:message key="lbl.depositBranchId"/></td>
				<td>
					<html:text styleClass="text" property="branch" styleId="branch" maxlength="100" value="${data.branch}" readonly="false"
					onchange="openLOVCommon(80,'sourcingForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifscCode','bankAccount','Y');closeLovCallonLov('bank');"
					 tabindex="-1"/>
   					<html:hidden  property="lbxBranchID" styleId="lbxBranchID" value="${data.lbxBranchID}"/>
  					<html:button property="bankButton" styleId="bankButton" onclick="openLOVCommon(80,'sourcingForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifscCode','bankAccount');closeLovCallonLov('bank');" value=" " styleClass="lovbutton"></html:button>  
  					<html:hidden styleClass="text" property="micr" styleId="micr" value="${data.micr}" />
  					<html:hidden styleClass="text" property="ifscCode" styleId="ifscCode"  value="${data.ifscCode}" />
  				</td>			
				<td><bean:message key="lbl.depositBnkAcc"/><font color="red">*</font></td>
				<td><html:text styleClass="text" property="bankAccount" styleId="bankAccount"  maxlength="50" value="${data.bankAccount}" readonly="true"  /></td>
      		</tr>
       	<tr>
		  <td width="20%"><bean:message key="lbl.fileDescription"/></td>
		  <td width="30%"><html:file size="40" property="docFile" styleId="docFile" styleClass="text"/></td>
		</tr>
		
		<tr>		
			<td colspan="4">
				<button type="button" name="upload" title="Alt+U" accesskey="U" id="upload"  class="topformbutton2" onclick="return submitDocUpload();" ><bean:message key="button.upload" /></button>
				<button type="button" name="button" id="save" class="topformbutton3" title="Alt+G" accesskey="G" onclick="return generateProcess();" ><bean:message key="button.generatebatch" /></button>
				<button type="button" name="button"  title="Alt+E" accesskey="E"  id="error" class="topformbutton3"   onclick="return generateReport()" ><bean:message key="button.errorlog" /></button>
	
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
 		var contextPath =document.getElementById('contextPath').value ;
 		var msg=document.getElementById('msg').value ;
 		alert(msg);		
		document.getElementById("sourcingForm").action=contextPath+"/presentationProcessBehindAction.do";
		document.getElementById("sourcingForm").submit();
 </script>
</logic:present>

  <logic:present name="noinserted">
<script type="text/javascript">	
		alert('Processing Error.');
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("sourcingForm").action=contextPath+"/presentationProcessBehindAction.do";
		document.getElementById("sourcingForm").submit();
</script>
</logic:present>
<logic:present name="uploadError">
 	<script type="text/javascript">	
		alert('${uploadError}');	
</script>
</logic:present>