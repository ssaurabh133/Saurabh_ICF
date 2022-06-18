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
    
<script type="text/javascript">
var names = new Array;
names[0]='a';
names[1]='b';

function activeTab(now){

for (z=0;z<names.length;z++)
{
	if(now == names[z])
	{
		document.getElementById(now).className='current';
		document.getElementById(now).setAttribute("onclick","return false");
	}
	else
	{
		document.getElementById(names[z]).className='';
		//document.getElementById(names[z]).removeAttribute("onclick","activeTab(id)");
		document.getElementById(names[z]).setAttribute("onclick","activeTab(id)");
	}

  }
  
}
</script>
</head>
<body oncontextmenu="return false" onload="enableAnchor();activeTab('a');">
<div class="tab-bg">

<div id="ddtabs4" class="ddcolortabs"><ul>

<li><a href="WaiveOff_CSE.jsp" target="body" id="a" onclick="activeTab(id);"><span style="color: rgb(255, 255, 255);"><bean:message key="waiveOffMaker"/></span></a></li>
<li><a href="waiveoffauthor.jsp" target="body" id="b" onclick="activeTab(id);"><span style="color: rgb(255, 255, 255);"><bean:message key="waiveOffAuthor"/></span></a></li>
</ul>
</div>
<div class="ddcolortabsline"></div>

<div id="tabs">
</div>

</div>
</body>
</html:html>