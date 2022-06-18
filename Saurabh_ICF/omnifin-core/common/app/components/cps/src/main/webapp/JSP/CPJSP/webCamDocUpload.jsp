
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%@include file="/JSP/commonIncludeContent.jsp" %> 

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>



<!DOCTYPE html>
<html>
	<head>
		 <meta http-equiv="refresh" content="<%= session.getMaxInactiveInterval()%>;url= <%=request.getContextPath()%>/logoff.do" />
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>

		<script>
// Put event listeners into place
window.addEventListener("DOMContentLoaded", function() {
	// Grab elements, create settings, etc.
	var canvas = document.getElementById("canvas"),
		context = canvas.getContext("2d"),
		video = document.getElementById("video"),
		videoObj = { "video": true },
		errBack = function(error) {
			console.log("Video capture error: ", error.code); 
		};

	// Put video listeners into place
	if(navigator.getUserMedia) { // Standard
		navigator.getUserMedia(videoObj, function(stream) {
			video.src = stream;
			video.play();
		}, errBack);
	} else if(navigator.webkitGetUserMedia) { // WebKit-prefixed
		navigator.webkitGetUserMedia(videoObj, function(stream){
			video.src = window.webkitURL.createObjectURL(stream);
			video.play();
		}, errBack);
	}
	else if(navigator.mozGetUserMedia) { // Firefox-prefixed
		navigator.mozGetUserMedia(videoObj, function(stream){
			video.src = window.URL.createObjectURL(stream);
			video.play();
		}, errBack);
			}
		document.getElementById("snap").addEventListener("click", function() {
	context.drawImage(video, 0, 0, 320, 240);
});

}, false);

function saveWebCamPic()
{
	var canvas = document.getElementById("canvas");
	var blank = document.getElementById("blank");
	var docDescription = document.getElementById("docDescription").value;
	if(docDescription==""){
		alert("Document Description is required.");
		return false;
	}
	else
		{
	if(document.getElementById("canvas").toDataURL()==document.getElementById("blank").toDataURL())
	{
	alert("Please capture an image.");
	return false;
	}
	else{
	var image = canvas.toDataURL("image/png").replace(/^data:image\/(png|jpg);base64,/, "");
//	window.location.href=image;

	document.getElementById("webString").value=image;
	var method = "post";
 	var sourcepath=document.getElementById("contextPath").value;
    var path =sourcepath+"/underwritingDocUpload.do?method=uploadUnderwritingDocData&uploadBy=webCam";

   	     var form = document.getElementById("underwritingDocWebUpload");//document.createElement("sourcingForm");
      	  form.setAttribute("method", method);
          form.setAttribute("action", path);
   		// document.getElementById("sourcingForm").submit();
          form.submit();
   	     return true;
	}
}
}
		</script>
	</head>
<body onclick="parent_disable();" oncontextmenu="return false" onload="enableAnchor();checkChanges('applicantForm');document.getElementById('applicantForm').applicantType.focus();" onunload="closeAllWindowCallUnloadBody();closeAllWindowCallUnloadBodyAn();" >   
<table>
<tr>
<td>
	<video id="video" width="320" height="240" autoplay></video>	</td>
<td>	<button id="snap">Click Photo</button>	</td>
<td>	<canvas id="canvas" width="320" height="240"></canvas>
	</td>
	<td>	<canvas id="blank" width="320" height="240"></canvas>
	</td>
	</tr>
	</table>
<html:form action="/underwritingDocUpload" styleId="underwritingDocWebUpload" method="post" >
	<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>"/>
	<input type="hidden" name="webString" class="text" id="webString"  />

	<tr>
		 <td ><bean:message key="lbl.docDescription"/><font color="red">*</font></td>
		 <td ><html:text property="docDescription" styleClass="text" value="" tabindex="1"  maxlength="100" styleId="docDescription" /></td>
		 <td ><bean:message key="lbl.docType" /></td>
		<logic:present name="docType">
		<td >
	`	<html:select property="docTypeId" styleId="docTypeId" styleClass="text" >
		<logic:notEmpty name="docType">
		<html:optionsCollection name="docType" label="docTypeDesc" value="docTypeId" />
		</logic:notEmpty>
						
		</html:select>
		</td>
		</logic:present>
	</tr>
	<tr>
	<td> 
<button type="button" name="save" class="topformbutton2" id="save" title="Alt+D" accesskey="D" onclick="saveWebCamPic();"><bean:message key="lbl.save" /></button>
	</td>
	</tr>


</html:form>

	</body>

</html>


