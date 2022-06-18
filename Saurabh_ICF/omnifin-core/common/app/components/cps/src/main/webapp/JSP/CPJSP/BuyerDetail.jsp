<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
	<html:html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
     <link type="text/css" rel="stylesheet" href="../../css/contentstyle.css"/>
	 <link type="text/css" rel="stylesheet" href="../../css/subpage.css"/>
     <script type="text/javascript" src="../../js/popup.js"></script>
    <!-- CSS for Tab Menu #1 -->
<link href="../../ddtabmenufiles/ddtabmenu.css" type="text/css" rel="stylesheet"/>

<!-- CSS for Tab Menu #2 -->
<link href="../../ddtabmenufiles/glowtabs.css" type="text/css" rel="stylesheet"/>

<!-- CSS for Tab Menu #3 -->
<link href="../../ddtabmenufiles/solidblocksmenu.css" type="text/css" rel="stylesheet"/>

<!-- CSS for Tab Menu #4 -->
<link href="../../ddtabmenufiles/ddcolortabs.css" type="text/css" rel="stylesheet"/>

<!-- CSS for Tab Menu #5 -->
<link href="../../ddtabmenufiles/chromemenu.css" type="text/css" rel="stylesheet"/>


</head>
<body onload="enableAnchor();init_fields();">
<div class="maincontainer">

<div id="ddtabs4" class="ddcolortabs"><ul>

<li><a href="buyerBehindAction.do" target="body"><span style="color: rgb(255, 255, 255);"><bean:message key="creditProcessing.buyersCheck" /> </span></a></li>
<li><a href="Supplier.jsp" target="body"><span style="color: rgb(255, 255, 255);"><bean:message key="creditProcessing.supplierCheck" /></span></a></li>
<li><a href="MarketCheck.jsp" target="body"><span style="color: rgb(255, 255, 255);"><bean:message key="creditProcessing.marketCheck" /></span></a></li>
</ul>
</div>


<div class="ddcolortabsline"></div>

<div id="tabs">
</div>




	
</div>
</body>
</html:html>