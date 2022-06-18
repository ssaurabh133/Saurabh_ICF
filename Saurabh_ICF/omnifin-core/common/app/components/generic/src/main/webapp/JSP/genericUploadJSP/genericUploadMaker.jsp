
<%@include file="/JSP/sessioncheck.jsp" %> 
<%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>
<title><bean:message key="a3s.noida" /></title>
	
	<script type="text/javascript" src=" <%=request.getContextPath() %>/js/genericUploadJS/${jsfile}"></script>
  	<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.core.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.datepicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.tabs.js"></script>
	 <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
 	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
 <link type="text/css" rel="stylesheet" href="./css/theme2/style.css" />
     <logic:present name="css">
	    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
     </logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
 
 
 	
<script type="text/javascript">


function forwardAction()
{
	 var batch_id = "";
	 var radios=document.getElementsByName("radio");
	 for(var i = 0; i < radios.length; i++)
	 {
		if(radios[i].checked == true)
		{
			batch_id = radios[i].value;
		}
	 }
	 
	if(batch_id.length > 0)
	{
		
		document.getElementById('actionnameid').value="forward";
		var contextPath= document.getElementById("context").value;
		document.getElementById("uploadMaker").action=contextPath+"/genericUploadMaker.do?batch_id="+batch_id;
		document.getElementById('uploadMaker').submit();
	}
	else
	{
		alert("Please select batch first");
	}
}


function deleteAction()
{
	var batch_id = "";
	 var radios=document.getElementsByName("radio");
	 for(var i = 0; i < radios.length; i++)
	 {
		if(radios[i].checked == true)
		{
			batch_id = radios[i].value;
		}
	 }
	 
	if(batch_id.length > 0)
	{
		
		document.getElementById('actionnameid').value="delete";
		var contextPath= document.getElementById("context").value;
		document.getElementById("uploadMaker").action=contextPath+"/genericUploadMaker.do?batch_id="+batch_id;
		document.getElementById('uploadMaker').submit();
	}
	else
	{
		alert("Please select batch first");
	}
	
}

function saveAction()
{
	var docFile=document.getElementById("docFile").value;
 	var msg1="";
    var abc1 ="";	
    var basePath=document.getElementById("context").value;
   if(document.getElementById("abc").checked==false && (document.getElementById("abc1").checked==false) || docFile==""){
   		if(document.getElementById("abc").checked==false && (document.getElementById("abc1").checked==false)){
			msg1+='* Please Select Import Format\n';
		}if(docFile == ''){
	  		msg1+='* Please browse file path first\n';	  	
		}
		alert(msg1);
		return false;
		}
	var ext1=docFile.substr(docFile.lastIndexOf("xls")); 
  	var ext=docFile.substr(docFile.lastIndexOf("csv"));
  	
    if(document.getElementById("abc").checked==true ){
		abc1 =document.getElementById("abc").value;
	}else if(document.getElementById("abc1").checked==true){
		abc1 =document.getElementById("abc1").value;
	}
	
	 if(docFile != '') {
	  	if((ext1.toUpperCase()=='XLS')&& abc1=="excel"){
		    document.getElementById("ExcelSheetUpload").action = basePath+'/glExcelSheetUpload.do?actionName=saveExcel';
		 	document.getElementById("processingImage").style.display = '';
		 	document.getElementById("ExcelSheetUpload").submit();   
	    	return true;
		}else if((ext.toUpperCase()=="CSV" ||ext.toUpperCase()=="CSVX") && abc1=="csv" )
		{
			document.getElementById("uploadMaker").action = basePath+'/genericUploadMaker.do?actionName=saveCsv';
			document.getElementById("processingImage").style.display = '';
			document.getElementById("uploadMaker").submit();
			return true;
		 }else{
		 	 alert("Please provide right file format ");
	 		return false;
		}
	}
}

function checkData(){
	 var basePath=document.getElementById("context").value;
	  document.getElementById("uploadMaker").action = basePath+'/genericUploadMaker.do?actionName=openExcel';
	  document.getElementById("uploadMaker").submit();
}
function templateDownload(){
	 var basePath=document.getElementById("context").value;
	  document.getElementById("uploadMaker").action = basePath+'/genericUploadMaker.do?actionName=downloadtemplate';
	  document.getElementById("uploadMaker").submit();
}

function downloaddump()
{										
	 var batch=document.getElementById("batch").value;
	 if(batch.length > 0)
	 {
	 	var basePath=document.getElementById("context").value;
	  	document.getElementById("uploadMaker").action = basePath+'/genericUploadMaker.do?actionName=downloaddump&batch='+batch;
	  	document.getElementById("uploadMaker").submit();
	 }
	 else
	 {
		alert('Please select batch ID ');	 
	 }
}

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
<html:form action="genericUploadMaker" method="post" styleId="uploadMaker" enctype="multipart/form-data">
<html:hidden property="actionName" styleId="actionnameid"  />
 <html:hidden property="context" styleId="context" value="<%=request.getContextPath() %>"/> 
 <html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>"/>  		
   		
<fieldset>
	<legend>Upload Maker</legend>

<table width="100%"  border="0" cellspacing="0" cellpadding="0" >
	<tr>
        <td width="10%" ><bean:message key="lbl.importformat" /><span><font color="red">*</font></span></td>
        <td width="30%" >
             <html:radio style="display:none" property="radioButton" styleClass="" styleId="abc"  value="excel" tabindex="1" />
             <input type="radio" name="abc1"  id="abc1" value="csv" checked="checked" /><bean:message key="lbl.csv" />
       </td>
       
        <td width="10%" ><bean:message key="lbl.filepath"/></td>
		<td><html:file size="100" property="docFile" styleId="docFile" styleClass="text" tabindex="3"  onchange="handleFiles(this.files)" /></td>
       
           
    </tr> 
    
  <!--    <tr>
        <td width="10%"><bean:message key="lbl.filepath"/></td>
		<td width="90%"><html:file size="60" property="docFile" styleId="docFile" styleClass="text" tabindex="3"/></td>	
	</tr>  -->
	    
    <tr> 
      <td><button type="button" id="save" class="topformbutton2"  title="Alt+U" accesskey="U" onclick="return saveAction();" tabindex="4" ><bean:message key="button.upload" /></button>
   	 </td>
   	 <logic:present name="logDetail">
   	 <!--    	<td><button type="button" id="save" class="topformbutton2"  title="Alt+U" accesskey="U" onclick="return checkData();" tabindex="4" ><bean:message key="button.errorlog" /></button>
   	 	</td>  -->
   	 </logic:present>
   	  <logic:present name="functionId">
       <logic:equal name="functionId" value="10000836">
			<td><button type="button" id="save" class="topformbutton2"  title="Alt+U" accesskey="U" onclick="return templateDownload();" tabindex="4" ><bean:message key="button.template" /></button>
   	 	</td>  
   	 	</logic:equal>
   	 	</logic:present>
	</tr>
	
	
	
</table>

</fieldset>

<fieldset>
	<legend>Dump Download</legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0" >
	<tr>
		<td>
		Select batch ID
		</td>
		<td>
			<input type="text" name="bid" id="bid" readonly="true" tabindex="-1"  class="text" />
			<input type="hidden" name="batch" id="batch" class="text" /> 
    		<html:button property="countryButton" styleId="countryButton" onclick="closeLovCallonLov1();openLOVCommon(${lovid} ,'uploadMaker','bid','','','','','','batch');" value=" " styleClass="lovbutton"> </html:button>
		</td>
	</tr>
	<tr>
		<td>
		<button type="button" id="download" class="topformbutton2"  title="Alt+U" accesskey="U" onclick="return downloaddump()" >Download</button>
		</td>
	</tr>
	</table>

</fieldset>


 <fieldset>
         <legend>Upload Search List</legend>
         <table width="100%" border="0" cellspacing="0" cellpadding="0">
           <logic:notEmpty name="searchList">
		 <tr>
			<td>
			     <display:table  id="lst"  name="searchList" style="width: 100%" class="dataTable" pagesize="6" size="${size}" partialList="true"  cellspacing="1" requestURI="genericUploadMaker.do?extra=grid" >
			     <display:setProperty name="paging.banner.placement"  value="bottom"/>
			    <!--  <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>  -->
			    <!--  <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>   -->
				 <display:column  title="Select"><input type="radio" name="radio" value="${lst.batch_id}" /> </display:column>
				 <display:column  title="Batch ID" ><a href="genericUploadMaker.do?actionName=openExcel&batch_id=${lst.batch_id}">${lst.batch_id}</a></display:column>
				 <display:column property="status_msg" title="Status"    />
				 <display:column property="upload_type" title="Upload Type"    />
				 <display:column property="upload_date" title="Upload Date"   />
				 <display:column property="maker_id" title="Maker ID"  />
				 <display:column property="maker_date"  title="Maker Date"     />
				 <display:column property="author_id"  title="Author ID"     />
				 <display:column property="author_date"  title="Author Date"     />
				 <display:column property="no_of_records"  title="No Of Records"    />
				 <display:column property="file_name"  title="File Name"    />
				 <display:column property="summary1"  title="Author Comment"    />
				
				</display:table>
   			</td>
		</tr>
		
		</logic:notEmpty>
		<logic:empty name="searchList">
		<td class="gridtd">
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
			<tr class="white2">
				<td><b>Batch ID</b></td>
				<td><b>Status</b></td>
				<td><b>Upload Type</b></td>
				<td><b>Upload Date</b></td>
				<td><b>Maker ID</b></td>
				<td><b>Maker Date</b></td>
				<td><b>Author ID</b></td>
				<td><b>Author Date</b></td>
				<td><b>No Of Records</b></td>
				<td><b>File Name</b></td>
				<td><b>Author Comment</b></td>
				
				
			</tr>
			<tr class="white2">
				<td colspan="11">No Data Found</td>
			</tr>
		 </table>
		  </td>
		</logic:empty>
		</table>
        </fieldset>


	<fieldset>
			<legend>Upload Maker Actions</legend>
  			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
     		<tr>
		      <td style="width:20%;"><input name="button" type="button" accesskey="S" title="Alt+S" class="topformbutton2" value="Forward" onclick="forwardAction()" /> </td>
			  <td><input name="button" type="button" accesskey="S" title="Alt+S" class="topformbutton2" value="Delete" onclick="deleteAction()" /> </td>
			</tr>
    		</table>
	</fieldset>






</html:form>
</div>

 
 
 
 
 
 
 
 <logic:present name="output" scope="request" >
       <script type="text/javascript">	
			alert("<%=request.getAttribute("output")%>");
       </script>
 </logic:present>
 
 
 <logic:present name="uploadStatus" scope="request" >
       <script type="text/javascript">	
			if("<%=request.getAttribute("uploadStatus")%>" == "success")
			{	
				alert("File uploaded successfully ,your batch id is "+<%=request.getAttribute("batchid")%>);
			}
			else
			{
				alert("Error in file uploading");
			}
			
       </script>
 </logic:present>
 
 
 
 
 
 
 
 </div>
 <div  align="center" class="opacity" style="display:none" id="processingImage" ></div>
</body>
</html:html>
