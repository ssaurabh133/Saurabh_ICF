    <%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@page import="com.logger.LoggerMsg"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

  <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	 <meta http-equiv="pragma" content="no-cache" />
	 <meta http-equiv="cache-control" content="no-cache" />
	 <meta http-equiv="expires" content="0" />
	 <meta name="author" content="" />
	 <link rel="shortcut icon" href="<%=request.getContextPath()%>/images/titleLogo.png" /> 

<title>Insert title here</title>
</head>
<script type="text/javascript">
function openMobileDownload()
{
	
	var contextPath =document.getElementById("contextPath").value;
	document.getElementById("MobileDownloadAction").action=contextPath+"/MobileDownloadAction.do";
	document.getElementById("MobileDownloadAction").submit();
	/* document.getElementById("processingImage").style.display = ''; */
	return true;
	
	  
}
</script>
<body onload="enableAnchor();">
<html:form action="/MobileDownloadAction" styleId="MobileDownloadAction">
<!-- <div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div> -->
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"  />
<fieldset>
<tr>
<a href="#24851" onclick="openMobileDownload();">Mobile APK Download</a>
</tr>
</fieldset>
</html:form>

</body>
</html>
