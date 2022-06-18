<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
     
     <script type="text/javascript" src="<%=path%>/js/popup.js"></script>
   
</head>
<body onload="enableAnchor();">
<div class="tab-bg">



<div id="ddtabs4" class="ddcolortabs"><ul>

<li><a href="Release_Instrument_CSE.jsp" target="body"><span style="color: rgb(255, 255, 255);"><bean:message key="releaseInstrumentMaker"/></span></a></li>

<li><a href="ReleaseReasonAuthor.jsp" target="body"><span style="color: rgb(255, 255, 255);"><bean:message key="releaseInstrumentAuthor"/></span></a></li>
</ul>
</div>


<div class="ddcolortabsline"></div>

<div id="tabs">
</div>




	
</div>
</body>
</html:html>