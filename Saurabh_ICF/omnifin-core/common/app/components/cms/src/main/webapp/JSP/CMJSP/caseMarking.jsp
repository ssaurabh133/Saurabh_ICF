<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> 



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
     
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/paymentMaker.js"></script>
    
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

<li><a href= "caseMarkingAuthorAdd.jsp"target="body" id="a"><span style="color: rgb(255, 255, 255);">Case Marking Maker</span></a></li>
<li><a href="<%=request.getContextPath()%>/caseMarkingAuthorDispatch.do?method=caseMarkingAuthorScreen " target="body" id="b"><span style="color: rgb(255, 255, 255);">Case Marking Author</span></a></li>
	</ul>
</div>


<div class="ddcolortabsline"></div>

<div id="tabs">
</div>




	
</div>
</form>
</body>
</html>