<%--

	Created By Sanjog      
 	
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
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	Date currentDate = new Date();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

	<head>
		<meta http-equiv="content-type"
			content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		<script type="text/javascript" src="../../js/popup.js"></script>

			
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
    	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		<!--tab active script-->
		<!--
author:satish singh 
 -->
		<script type="text/javascript">
var names = new Array;
names[0]='a';
names[1]='b';


function activeTab(now){
for (z=0;z<names.length;z++){
if(now == names[z]){document.getElementById(now).className='current';}
else
document.getElementById(names[z]).className='';
}
}
</script>
		<base target="body" />
	</head>
	<body onload="enableAnchor();activeTab('a');">
	<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
	<logic:notPresent name="LeadViewMode">
	<form name="test" id="test">
		<div class="tab-bg">
			<div class="ddcolortabs">
				<ul>
					<li onclick="callFunction('/leadCapturingBehind.do?leadId=<%=session.getAttribute("leadTrackNote") %>&FromDealCap=\'\'','');">
						<a href="#" id="a" onclick="activeTab(id);"><span>Lead Details</span> </a>
					</li>
					<li onclick="callFunction('/leadNotePadPageBehind.do?leadId=<%=session.getAttribute("leadTrackNote") %>','b');">
						<a href="#" id="b" onclick="activeTab(id);"><span>Lead Tracking</span> </a>
					</li>
					<li onclick="callFunction('/JSP/CPJSP/leadDecision.jsp','c');">
						<a href="" id="c" onclick="activeTab(id);"><span>Lead Decision</span> </a>
					</li>
					<li><input type="hidden" name="recStatus" id="recStatus" value="P" /></li>
					<li><input type="hidden" name="checkModifications" id="checkModifications" value="" /></li>
					<li><input type="hidden" name="getFormName" id="getFormName"/></li>
				</ul>
			</div>
			<div class="ddcolortabsline"></div>
			<div id="tabs">
			</div>
		</div>
		</form>
		</logic:notPresent>
		
		<!-- Changes Start By Sanjog-->
		<logic:present name="LeadViewMode">
			<form name="test" id="test">
		<div class="tab-bg">
			<div class="ddcolortabs">
				<ul>
					<li>
						<a href="<%=request.getContextPath()%>/leadCapturingBehind.do?leadId=<%=session.getAttribute("leadTrackNote") %>&FromDealCap=''&LeadViewMode=LeadViewMode" id="a" onclick="activeTab(id);"><span>Lead Details</span> </a>
					</li>
					<li>
						<a href="<%=request.getContextPath()%>/leadNotePadPageBehind.do?leadId=<%=session.getAttribute("leadTrackNote") %>&LeadViewMode=LeadViewMode" id="b" onclick="activeTab(id);"><span>Lead Tracking</span> </a>
					</li>
					<li>
						<a href="<%=request.getContextPath()%>/leadCapturingBehindAction.do?method=searchDecisionDetails&leadId=<%=session.getAttribute("leadTrackNote") %>" id="c" onclick="activeTab(id);"><span>Lead Decision</span> </a>
					</li>
					<li><input type="hidden" name="recStatus" id="recStatus" value="P" /></li>
					<li><input type="hidden" name="checkModifications" id="checkModifications" value="" /></li>
					<li><input type="hidden" name="getFormName" id="getFormName"/></li>
				</ul>
			</div>
			<div class="ddcolortabsline"></div>
			<div id="tabs">
			</div>
		</div>
		</form>
		</logic:present>
		<!-- Changes End By Sanjog-->
	</body>
</html>