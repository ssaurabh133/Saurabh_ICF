<%@include file="/JSP/sessioncheck.jsp" %> 
<%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
<head>
	<title>Excel Upload</title>
	<script type="text/javascript" src=" <%=request.getContextPath() %>/js/genericUploadJS/${jsfile}"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.datepicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.tabs.js"></script>
	 <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<link type="text/css" rel="stylesheet" href="./css/theme2/style.css" />
	<logic:present name="css">
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css}/displaytable.css"/>
	</logic:present>
	<logic:notPresent name="css">
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
	</logic:notPresent>
	<script type="text/javascript">
		
		function uploadToDoCaseData(){
			var docFile=document.getElementById("docFile").value;
			var msg1="";
			var abc1 ="";	
			var basePath=document.getElementById("context").value;
			var ext=docFile.substr(docFile.lastIndexOf(".")+1);
			if(docFile!=''){
				if(ext.toUpperCase()=='XLS' || ext.toUpperCase()=='XLSX'){
					document.getElementById("uploadMaker").action = basePath+'/toDoListUpload.do?method=uploadToDoCaseExcel';
					document.getElementById("processingImage").style.display = '';
					document.getElementById("uploadMaker").submit();
					return true;
				}else{
					alert("Only excel(i.e. XLS/XLSX) is allowed for upload");
					return false;
				}
			}else{
				alert("Please select excel file for upload");
				return false;
			}
		}
		function downloadUploadedExcel(){
			var basePath=document.getElementById("context").value;
			var batchId=document.getElementById("batchId").value;
			document.getElementById("uploadMaker").action = basePath+'/toDoListUpload.do?method=downloadToDoCaseExcel&batch_id='+batchId;
			document.getElementById("uploadMaker").submit();
		}
	</script>
</head>
<body onload="enableAnchor();">
	<div><div>
	<html:form action="genericUploadMaker" method="post" styleId="uploadMaker" enctype="multipart/form-data">
		<html:hidden property="actionName" styleId="actionnameid"/>
		<html:hidden property="context" styleId="context" value="<%=request.getContextPath() %>"/>
		<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>"/>
		<input type="hidden" name="batchId" id="batchId" value="${batchId}"/>
		<input type="hidden" name="recStatus" id="recStatus" value="${recStatus}"/>
		<input type="hidden" name="functionId" id="functionId" value="${functionId}"/>
		
		<fieldset><legend>${fieldSetLebel}</legend>
			<table width="100%"  border="0" cellspacing="0" cellpadding="0"  >
			<tr>
				<td>Select ToDo Case Excel</td>
				<td><html:file size="100" property="docFile" styleId="docFile" styleClass="text" tabindex="3"  onchange="handleFiles(this.files)" /></td>
				<!-- <td colspan="4">Download ToDo Excel<button type="button" id="download" tabindex="3"  class="topformbutton2"  title="Alt+U" accesskey="U" onclick="return downloadUploadedExcel();" >Download</button></td> -->
				<td colspan="4">Download ToDo Excel &nbsp 
				<button type="button" name=" mybutton"  title="Alt+G" accesskey="G" class="topformbutton3" onclick="return downloadUploadedExcel();">Download</button>
				</td> 
				 
			</tr>
			<tr>
				<td colspan="4">
					<button type="button" id="upload" class="topformbutton2"  title="Alt+U" accesskey="U" onclick="return uploadToDoCaseData();"><bean:message key="button.upload" /></button>
				</td>
			</tr>
			</table>
		</fieldset>
		 
		 <%-- <fieldset><legend>Uploaded Excel Detail</legend>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<logic:present name="searchList">
			<logic:notEmpty name="searchList">
				<tr><td>
					<display:table  id="lst"  name="searchList" style="width: 100%" class="dataTable" pagesize="6" size="${size}" partialList="true"  cellspacing="1" requestURI="achUploadMaker.do" >
					<display:setProperty name="paging.banner.placement"  value="bottom"/>
					<display:column title="ToDo Id"><a href="#" onclick="downloadUploadedExcel()">${lst.batch_id}</a></display:column>
					<display:column property="loanId" title="Loan Id"/>
					<display:column property="maker_id" title="User Id"/>
					<display:column property="maker_date" title="Active On"/>
					<display:column property="createdBy"  title="Created By"/>
					<display:column property="createdOn"  title="Created On"/>
					</display:table>
				</td></tr>
			</logic:notEmpty>
			<logic:empty name="searchList">
				<tr><td class="gridtd">
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
					<tr class="white2">
						<td><b>ToDo Id</b></td>
						<td><b>Loan Id</b></td>
						<td><b>User Id</b></td>
						<td><b>Active On</b></td>
						<td><b>Created By</b></td>
						<td><b>Created On</b></td>
					</tr>
					<tr class="white2">
						<td colspan="7">No Data Found</td>
					</tr>
				</table>
				</td></tr>
			</logic:empty>
			</logic:present>
			<logic:notPresent name="searchList">
				<tr><td class="gridtd">
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
					<tr class="white2">
						<td><b>ToDo Id</b></td>
						<td><b>Loan Id</b></td>
						<td><b>User Id</b></td>
						<td><b>Active On</b></td>
						<td><b>Created By</b></td>
						<td><b>Created On</b></td>
					</tr>
					<tr class="white2">
						<td colspan="7">No Data Found</td>
					</tr>
				</table>
				</td></tr>
			</logic:notPresent>
			</table>
		</fieldset> --%>
	</html:form>
	</div></div>
	<div  align="center" class="opacity" style="display:none" id="processingImage" ></div>
	<logic:present name="operationMessage" scope="request" >
		<script type="text/javascript">	
			alert('${operationMessage}');
		</script>
	</logic:present>
</body>
</html:html>