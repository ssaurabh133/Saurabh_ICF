<%--
      Author Name-      Prashant Kumar
      Date of creation -15/04/2011
      Purpose-          Entry of Leads
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
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
    
<base target="body" /> 
</head>
<body class="remove-bg" onload="enableAnchor();activeTab('a')" oncontextmenu="return false;">
<form name="test" id="test">
<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
<input type="hidden" name="dealMoveMsg" id="dealMoveMsg" value="<bean:message key="lbl.dealMove" />" />



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
		document.getElementById(now).setAttribute("onclick","return false;");
	}
	else
	{
		document.getElementById(names[z]).className='';
		//document.getElementById(names[z]).removeAttribute("onclick","activeTab(id)");
		document.getElementById(names[z]).setAttribute("onclick","activeTab(id);");
	}

  }
  
}
</script>

<div class="tab-bg">
<div class="ddcolortabs" ><ul>
<logic:present name="verifCPS">
	<li><a href="<%=request.getContextPath()%>/fieldVarificationInitiationAction.do?method=openTabVerification" id="a" onclick="activeTab(id);"><span ><bean:message key="lbl.ver.initiation"/></span></a></li>
	<li><a href="<%=request.getContextPath()%>/fieldVarificationInitiationAction.do?method=showTabVerification" id="b" onclick="activeTab(id);"><span ><bean:message key="lbl.ver.initiatedto"/></span></a></li>
</logic:present>
<logic:present name="verifCMS">
	<li><a href="<%=request.getContextPath()%>/fieldVarificationInitiationAction.do?method=openTabVerificationAtCM" id="a" onclick="activeTab(id);"><span ><bean:message key="lbl.ver.initiation"/></span></a></li>
	<li><a href="<%=request.getContextPath()%>/fieldVarificationInitiationAction.do?method=showTabVerificationAtCM" id="b" onclick="activeTab(id);"><span ><bean:message key="lbl.ver.initiatedto"/></span></a></li>
</logic:present>
</ul>
</div>
</div>

<div class="ddcolortabsline"></div>

<div id="tabs">
</div>

</form>
</body>
</html>