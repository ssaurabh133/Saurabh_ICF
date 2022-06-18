	<%@page language="java"%>
<%@page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
     
       	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
      	Date currentDate = new Date();
%>

<html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script> 
<base target="body" /> 
<script type="text/javascript">
	 function hid(){
	//	alert("Please save data first");
	//	return false;	
		alert("You can add only 1 data.");
		return true;
	} 
	function btn_reset() {
		 	//parent.location.reload(); 
		 	//parent.result.location.reload();
	}
</script>
</head>

<body class="remove-bg" onload="enableAnchor();activeTab('a');btn_reset()" oncontextmenu="return false;" style="position: fixed; width:3000px;">
<form name="test" id="test">
<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
<input type="hidden" name="dealMoveMsg" id="dealMoveMsg" value="<bean:message key="lbl.dealMove" />" />

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
<div class="ddcolortabs">
<ul>
	<li><a href="<%=request.getContextPath()%>/tab1BehindAction.do?method=newPoolIdMaker" id="a"><span><bean:message key="lbl.poolIdMaker" /></span></a></li>
	
	<logic:present name="credit">
		<li><a href="<%=request.getContextPath()%>/tab1BehindAction.do?method=tab1OpenAction" id="b"><span><bean:message key="lbl.tab1" /></span></a></li>
	</logic:present>
	<logic:notPresent name="credit">
		<li><a href="#" onclick="return hid();" id="b"><span><bean:message key="lbl.tab1" /></span></a></li>
	</logic:notPresent>
	
	<logic:present name="multi">
		<li><a href="<%=request.getContextPath()%>/tab1BehindAction.do?method=tab2OpenAction" id="c"><span><bean:message key="lbl.tab2" /></span></a></li>
	</logic:present>		
	<logic:notPresent name="multi">
		<%-- <li><a href="#" onclick="return hid();" id="c"><span><bean:message key="lbl.tab2" /></span></a></li> --%>	<!-- comment: seema -->
	 <li><a href="<%=request.getContextPath()%>/tab1BehindAction.do?method=tab2OpenAction" onclick="return hid();" id="c"><span><bean:message key="lbl.tab2" /></span></a></li>	<!-- add:seema -->
	</logic:notPresent>
	
	<li><a href="<%=request.getContextPath()%>/tab1BehindAction.do?method=downloadUploadedData" id="e"><span><bean:message key="lbl.downloadPoolData" /></span></a></li>
</ul>
</div>

<div class="ddcolortabsline"></div>
<div id="tabs"></div>
</div>

</form>
</body>
</html>