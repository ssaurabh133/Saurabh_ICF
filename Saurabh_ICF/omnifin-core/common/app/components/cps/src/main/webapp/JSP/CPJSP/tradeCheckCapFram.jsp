<%--
      Author Name-      Ankit Agarwal
      Date of creation -06\09\11
      Purpose-          Trade Check capturing fram
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
     <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
     <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
    	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
   
<script type="text/javascript">
var names = new Array;
names[0]='a';
names[1]='b';

function activeTab(now){
for (i=0;i<names.length;i++){
	if(now == names[i]){
		document.getElementById(now).className='current';
	}else
		document.getElementById(names[i]).className='';
	}
}
</script>
<base target="body" />
</head>
<body oncontextmenu="return false" onload="enableAnchor();activeTab('a');">
<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
	<form name="test" id="test">
<div class="tab-bg">
<div class="ddcolortabs"><ul>
<!--<li onclick="callFunction('/tradeCheck.do?method=buyerSupplierNew','');"><a href="#" id="a" onclick="activeTab(id);"><span>Trade check capturing</span></a></li>-->
<!--<li onclick="callFunction('/tradeCheck.do?method=marketCheckNew','b');"><a href="#" id="b" onclick="activeTab(id);"><span>Trade check completion</span></a></li>-->
<!---->
<!--<li><input type="hidden" name="recStatus" id="recStatus" value="P" /></li>-->
<!--<li><input type="hidden" name="checkModifications" id="checkModifications" value="" /></li>-->
<!--<li><input type="hidden" name="getFormName" id="getFormName"/></li>-->
<li><a href="<%=request.getContextPath()%>/tradeCheck.do?method=buyerSupplierNew" id="a"><span>Trade check capturing</span></a></li>
<li><a href="<%=request.getContextPath()%>/tradeCheck.do?method=marketCheckNew" id="b"><span>Trade check completion</span></a></li>
</ul>
</div>


<div class="ddcolortabsline"></div>

<div id="tabs">
</div>
	
</div>
</form>

</body>
</html>