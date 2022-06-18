
<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
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
  
<script type="text/javascript">
function activeTab(now){
	var names = [];
	names[0]='a';
	names[1]='b';

	for (var z=0; z<names.length; z++)
	{
		if(names[z] == now)
		{
			document.getElementById(now).className='current';
			document.getElementById(now).setAttribute("onclick","return false;");		
		}
		else
		{
			document.getElementById(names[z]).className='';
			document.getElementById(names[z]).setAttribute("onclick","activeTab(id);");
		}
	  }
	  
	}
</script>
<base target="body" /> 
</head>
<body oncontextmenu="return false;" onload="enableAnchor();activeTab('a');">
<div class="tab-bg">
<div  class="ddcolortabs"><ul>
<li><a href="<%=request.getContextPath()%>/achCapturingAction.do?method=fetchACHData" id="a" onclick="activeTab(id);"><span><bean:message key="lbl.achDetailCapturing" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/achCapturingAction.do?method=achStatusTracking" id="b" onclick="activeTab(id);"><span><bean:message key="lbl.achStatusTracking" /></span></a></li>


	<li><input type="hidden" name="recStatus" id="recStatus" value="P" /></li>
	<li><input type="hidden" name="checkModifications" id="checkModifications" value="" /></li>
	<li><input type="hidden" name="getFormName" id="getFormName"/></li>
</ul>
</div>
<div class="ddcolortabsline"></div>

<div id="tabs">
</div>

</div>
</body>
</html>
