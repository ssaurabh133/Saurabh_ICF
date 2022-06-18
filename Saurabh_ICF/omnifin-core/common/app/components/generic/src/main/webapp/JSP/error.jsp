<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
     <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/contentstyle.css"/>
	 <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/subpage.css"/>
   
    <!-- CSS for Tab Menu #1 -->

     <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/mainstyle.css"/>
<style lang="css">
#Login {
border:1px solid #DFA641;
color:#FF0000;
padding:50px;
font-size: 12px;
position:relative;

}
#Login a {

color:#8A9AA6;



}</style>

<!--[if gte IE 5]>
<style type="text/css">
div.flower {
background:none;
filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/logo.png' ,sizingMethod='crop');
}
div.rose {
background:none;
filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/lo.png' ,sizingMethod='crop');
}
div.error {
background:none;
filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/error40.png' ,sizingMethod='crop');
}
</style>
<![endif]-->		
</head>
<body onload="enableAnchor();">
<div style="font-weight: bold;color:#FFFFFF;height:31px; " >
</div>
<div class="maincontainer">

<div id="toprightdiv">

<div id="toprightdivbox">


</div>

</div>
<div style="height: 2px;">&nbsp;</div>
<div style="height: 79px;width:130px;float:left;" class="flower"></div>

<div style="margin-left:130px;" class="rose"></div>

	<div id="ddtabs4" class="ddcolortabs">&nbsp;
</div>


<div class="ddcolortabsline"></div>

                         
<div style="padding: 10%;text-align: center;">
<fieldset id="Login" class="InfoBox">
<div style="margin-left:120px;">
<div style="height: 30px;width:35px;float: left;" class="error">
</div>
<div style="text-align: left;padding-top: 3px;">
<strong>You are about to be signout due to inactivity.&nbsp;<a href="<%=request.getContextPath()%>/JSP/Login.jsp" target="_parent">Stay Logged In</a> 
</strong>
</div></div> </fieldset></div>
  


</div></body>
</html>