<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
    
     <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
     <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
  
</head>
<body onload="enableAnchor();">
<form name="test" id="test">

<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
<div class="tab-bg">



<div id="ddtabs4" class="ddcolortabs"><ul>

<!--<li onclick="callFunction('/JSP/CMJSP/WaiveOffAuthor_CSE.jsp','');"><a href="#" target="body"><span style="color: rgb(255, 255, 255);"><bean:message key="lbl.waiveOffmaker"/></span></a></li>-->
<!--<li onclick="callFunction('/JSP/CMJSP/WaiveOffAuthorPage.jsp','');"><a href="#" target="body"><span style="color: rgb(255, 255, 255);"><bean:message key="lbl.waiveOffAuthor"/></span></a></li>-->
<!---->
<!--<li><input type="hidden" name="recStatus" id="recStatus" value="P" /></li>-->
<!--<li><input type="hidden" name="checkModifications" id="checkModifications" value="" /></li>-->
<!--<li><input type="hidden" name="getFormName" id="getFormName"/></li>-->
<li><a href="WaiveOffAuthor_CSE.jsp" target="body"><span style="color: rgb(255, 255, 255);"><bean:message key="lbl.waiveOffmaker"/></span></a></li>
<li><a href="WaiveOffAuthorPage.jsp" target="body"><span style="color: rgb(255, 255, 255);"><bean:message key="lbl.waiveOffAuthor"/></span></a></li>

</ul>
</div>


<div class="ddcolortabsline"></div>

<div id="tabs">
</div>




	
</div>
</form>
</body>
</html>