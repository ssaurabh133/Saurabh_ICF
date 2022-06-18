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
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/cnLoanInitjs.js"></script>
   
</head>
<body class="remove-bg" onload="enableAnchor();activeTab('a');" oncontextmenu="return false;" style="position: fixed; width:3000px;">

<script type="text/javascript">
var names = new Array;
names[0]='a';
names[1]='b';
names[2]='c';
names[3]='d';
names[4]='e';
names[5]='f';
names[6]='g';
names[7]='h';
names[8]='i';

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
		document.getElementById(names[z]).setAttribute("onclick","activeTab(id);");
	}
  }
}
</script>

<div class="tab-bg">
<div id="ddtabs4" class="ddcolortabs"><ul>
	<li><a href="poolIdMakerAdd.jsp" target="body" id="a" ><span style="color: rgb(255, 255, 255);"><bean:message key="lbl.poolIdMaker"/></span></a></li>

	<logic:present name="credit">
		<li><a href="<%=request.getContextPath()%>/tab1BehindAction.do?method=tab1OpenAction" id="b" target="body"><span><bean:message key="lbl.tab1" /></span></a></li>
	</logic:present>
	<!--<logic:notPresent name="credit">
		<li><a href="#" onclick="return hid();" id="b"><span><bean:message key="lbl.tab1" /></span></a></li>
	</logic:notPresent>-->
	
	<logic:present name="multi">
		<li><a href="<%=request.getContextPath()%>/tab1BehindAction.do?method=tab2OpenAction" target="body" id="c"><span><bean:message key="lbl.tab2" /></span></a></li>
	</logic:present>		
	<!--<logic:notPresent name="multi">
		<li><a href="#" onclick="return hid();" id="c"><span><bean:message key="lbl.tab2" /></span></a></li>
	</logic:notPresent>-->
	<li><a href="<%=request.getContextPath()%>/tab1BehindAction.do?method=downloadUploadedData" target="body" id="e"><span><bean:message key="lbl.downloadPoolData" /></span></a></li>
	
<logic:notPresent name="viewer">
	<li><a href="<%=request.getContextPath()%>/tab1BehindAction.do?method=poolIdAuthor" target="body"><span style="color: rgb(255, 255, 255);"><bean:message key="lbl.poolIdAuthor"/></span></a></li>
</logic:notPresent>	

</ul>
</div>
<div class="ddcolortabsline"></div>
<div id="tabs"></div>
</div>
</body>
</html:html>