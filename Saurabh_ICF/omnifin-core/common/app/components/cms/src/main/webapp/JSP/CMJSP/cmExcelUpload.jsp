
<%@ include file="/JSP/sessioncheck.jsp" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ page language="java" import="java.util.*" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>
<title><bean:message key="a3s.noida" /></title>

  	<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.datepicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.tabs.js"></script>
	 <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
 <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
 	
<script type="text/javascript">

function saveAction()
{
	var docFile=document.getElementById("docFile").value;
	var count = docFile.split('.').length-1; 
	if(count > 1)  
	{  
    alert("Please provide right file name");  
	return false;
	} 
 	var msg1="";
    var abc1 ="";	
    var basePath=document.getElementById("context").value;
    var exc=false;
    var csv=false;
    if(document.getElementById("abc")!=null){
    	exc=document.getElementById("abc").checked;
    }
    if(document.getElementById("abc1")!=null){
    	csv=document.getElementById("abc1").checked
    }

   if((exc==false && csv==false) || docFile==""){
   		if(exc==false && csv==false){
			msg1+='* Please Select Import Format\n';
		}if(docFile == ''){
	  		msg1+='* Please Browse  First File Path\n';	  	
		}
		alert(msg1);
		return false;
		}
	var ext1=docFile.substr(docFile.lastIndexOf("xls")); 
  	var ext=docFile.substr(docFile.lastIndexOf("csv"));
  	
    if(exc==true ){
		abc1 =document.getElementById("abc").value;
	}else if(csv==true){
		abc1 =document.getElementById("abc1").value;
	}
	
	 if(docFile != '') {
	  	if((ext1.toUpperCase()=='XLS')&& abc1=="excel"){
		    document.getElementById("ExcelSheetUpload").action = basePath+'/glExcelSheetUploadCM.do?actionName=saveExcel';
		 	document.getElementById("processingImage").style.display = '';
		 	document.getElementById("ExcelSheetUpload").submit();   
	    	return true;
		}else if((ext.toUpperCase()=="CSV" ||ext.toUpperCase()=="CSVX") && abc1=="csv" ){
			  document.getElementById("ExcelSheetUpload").action = basePath+'/glExcelSheetUploadCM.do?actionName=saveCsv';
			document.getElementById("processingImage").style.display = '';
			document.getElementById("ExcelSheetUpload").submit();
			return true;
		 }else{
		 	 alert("Please provide right file format ");
	 		return false;
		}
	}
}
//added by Ajay Singh

function downloadUpdateInstrumentFormat()
{
	var basePath=document.getElementById("context").value;
	  document.getElementById("ExcelSheetUpload").action = basePath+'/glExcelSheetUploadCM.do?actionName=downInstrumentFormat';
	  document.getElementById("ExcelSheetUpload").submit();
}
//ended by Ajay Singh
 </script>
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
<div>
<div>
<html:form action="glExcelSheetUploadCM" method="post" styleId="ExcelSheetUpload" enctype="multipart/form-data">
<html:hidden property="actionName" styleId="actionnameid"  />
 <html:hidden property="context" styleId="context" value="<%=request.getContextPath() %>"/> 
   		
<fieldset>
<legend>Excel-Sheet Upload</legend>
<table width="100%"  border="0" cellspacing="7" cellpadding="0" >
	<tr>
	<logic:notPresent name="show">
        <td><bean:message key="lbl.importformat" /><span><font color="red">*</font></span></td>
        <td><html:radio property="radioButton" styleClass="" styleId="abc"  value="excel" tabindex="1" /><bean:message key="lbl.excel" />
       		 <html:radio property="radioButton" styleClass="" styleId="abc1"  value="csv" tabindex="2" /><bean:message key="lbl.csv" />
       	</td>  
      </logic:notPresent>
      <logic:present name="show">
      <td><bean:message key="lbl.importformat" /><span><font color="red">*</font></span></td>
       <td><html:radio property="radioButton" styleClass="" styleId="abc"  value="excel" tabindex="1" /><bean:message key="lbl.excel" />
       	</td>
       </logic:present>
    </tr> 
      <tr>
        <td width="10%"><bean:message key="lbl.filepath"/></td>
		<td width="90%"><html:file size="60" property="docFile" styleId="docFile" styleClass="text" tabindex="3"/></td>	
	</tr>
	    
    <tr> 
      <td><button type="button" id="save" class="topformbutton2"  title="Alt+V" accesskey="V" onclick="return saveAction();" tabindex="4" ><bean:message key="button.save" /></button>
    </td>
       	 <!-- Added by Ajay Singh for Update Instrument Details bulk Upload -->
   	 <logic:equal name="bounceRealization" value="Update Instrument Details Upload">
   	 <td><button type="button" id="down" class="topformbutton2"  value="down"  onclick="return downloadUpdateInstrumentFormat();" tabindex="4" ><bean:message key="button.download" /></button>
   	 </td>
   	 </logic:equal>
   	 <!-- Ends By Ajay Singh for Update Instrument Details bulk Upload -->
		 
	</tr>
</table>

</fieldset>
</html:form>
</div>
<logic:present name="fieldUpdate">
       <script type="text/javascript">	
			alert("<%=request.getAttribute("fieldUpdate")%>");
       </script>
 </logic:present>
 <logic:present name="errorMsg">
       <script type="text/javascript">	
			alert("<%=request.getAttribute("errorMsg")%>");
			 var basePath=document.getElementById("context").value;	
		     document.getElementById("ExcelSheetUpload").action = basePath+'/glExcelSheetUploadCM.do?actionName=openExcel';
		     document.getElementById("ExcelSheetUpload").submit(); 
       </script>
 </logic:present>
 
 </div>
 <div  align="center" class="opacity" style="display:none" id="processingImage" ></div>
</body>
</html:html>
