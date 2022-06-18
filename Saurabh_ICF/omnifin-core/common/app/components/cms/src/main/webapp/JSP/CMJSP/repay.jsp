<!-- 
Author Name :- Vishal Singh
Date of Creation :26-03-2012
Purpose :-  Repay Schedule Maker
-->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> 



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
    
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/paymentMaker.js"></script>
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
</head>
<body oncontextmenu="return false" onload="enableAnchor();activeTab('a');">
<form name="test" id="test">

<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
<div class="tab-bg">



<div id="ddtabs4" class="ddcolortabs"><ul>
<!--<li onclick="callFunction('/JSP/CMJSP/repaySchedule.jsp','');"><a href="#" target="body" id="a" onclick="activeTab(id);"><span style="color: rgb(255, 255, 255);"><bean:message key="lbl.repayScheduleDetails" /></span></a></li>-->
<!--<li onclick="callFunction('/JSP/CMJSP/approveDueDate.jsp','b');"><a href="#" target="body" id="b" onclick="activeTab(id);"><span style="color: rgb(255, 255, 255);">Approve</span></a></li>-->
<!---->
<!--<li><input type="hidden" name="recStatus" id="recStatus" value="P" /></li>-->
<!--<li><input type="hidden" name="checkModifications" id="checkModifications" value="" /></li>-->
<!--<li><input type="hidden" name="getFormName" id="getFormName"/></li>-->
<li><a href="repaySchedule.jsp" target="body" id="a"><span style="color: rgb(255, 255, 255);"><bean:message key="lbl.repayScheduleDetails" /></span></a></li>
<li><a href="approveDueDate.jsp" target="body" id="b"><span style="color: rgb(255, 255, 255);">Approve</span></a></li>

</ul>
</div>


<div class="ddcolortabsline"></div>

<div id="tabs">
</div>




	
</div>
</form>
</body>
</html:html>