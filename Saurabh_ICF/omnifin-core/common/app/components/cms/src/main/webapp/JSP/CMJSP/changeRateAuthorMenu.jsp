<%@ page language="java"%>
<%@ page session="true"%>
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

 

<base target="body" />
</head>
<body onload="enableAnchor();activeTab('a');">
<form name="test" id="test">

<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
<div class="tab-bg">
<div  class="ddcolortabs"><ul>

<!--<li onclick="callFunction('/changeRateAuthorDispatch.do?method=DisplayDataToAuthor','a');"><a href="#" id="a" onclick="activeTab(id);"><span>Change Rate Maker</span></a></li>-->
<!--<li onclick="callFunction('/changeRateAuthorDispatch.do?method=OpenAuthorDecisionScreen','b');"><a href="#" id="b" onclick="activeTab(id);"><span>Change Rate Author</span></a></li>-->
<!---->
<!--<li><input type="hidden" name="getTabId" id="getTabId" /></li>-->
<!--<li><input type="hidden" name="recStatus" id="recStatus" value="P" /></li>-->
<!--<li><input type="hidden" name="checkModifications" id="checkModifications" value="" /></li>-->
<!--<li><input type="hidden" name="getFormName" id="getFormName"/></li>-->
<li><a href="<%=request.getContextPath()%>/changeRateAuthorDispatch.do?method=DisplayDataToAuthor" id="a"><span>Change Rate Maker</span></a></li>
<li><a href="<%=request.getContextPath()%>/changeRateAuthorDispatch.do?method=OpenAuthorDecisionScreen" id="b"><span>Change Rate Author</span></a></li>
</ul>
</div>
<div class="ddcolortabsline"></div>

<div id="tabs">
</div>

</div>
</form>

</body>

</html>