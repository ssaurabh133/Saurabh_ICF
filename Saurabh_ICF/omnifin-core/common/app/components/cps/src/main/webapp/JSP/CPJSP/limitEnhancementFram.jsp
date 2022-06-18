<%--
      Author Name-      Ankit Agarwal
      Date of creation -01\03\12
      Purpose-         Limit Enhancement Form Jsp
      Documentation-     
      
 --%>

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
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
     
       SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
       Date currentDate = new Date();

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
     
     <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
   
<script type="text/javascript">
var names = new Array;
names[0]='a';
names[1]='b';

function activeTab(now){
for (i=0;i<names.length;i++){
if(now == names[i]){document.getElementById(now).className='current';}
else
document.getElementById(names[i]).className='';
}
}
</script>
<base target="body" />
</head>
<body oncontextmenu="return false" onload="enableAnchor();activeTab('a');">
<form name="test" id="test">

<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
<div class="tab-bg">
<div class="ddcolortabs"><ul>
<!--<li onclick="callFunction('/limitEnhanceAuthor.do?method=limitMakerForAuthor','a');"><a href="#" id="a" onclick="activeTab(id);"><span>Limit Enhancement Maker</span></a></li>-->
<!--<li onclick="callFunction('/limitEnhanceAuthor.do?method=limitAuthorForAuthor','b');"><a href="#" id="b" onclick="activeTab(id);"><span>Limit Enhancement Author</span></a></li>-->
<!---->
<!--<li><input type="hidden" name="recStatus" id="recStatus" value="P" /></li>-->
<!--<li><input type="hidden" name="checkModifications" id="checkModifications" value="" /></li>-->
<!--<li><input type="hidden" name="getFormName" id="getFormName"/></li>-->
<li><a href="<%=request.getContextPath()%>/limitEnhanceAuthor.do?method=limitMakerForAuthor" id="a"><span>Limit Enhancement Maker</span></a></li>
<li><a href="<%=request.getContextPath()%>/limitEnhanceAuthor.do?method=limitAuthorForAuthor" id="b"><span>Limit Enhancement Author</span></a></li>
</ul>
</div>


<div class="ddcolortabsline"></div>

<div id="tabs">
</div>
	
</div>
</form>
</body>
</html>