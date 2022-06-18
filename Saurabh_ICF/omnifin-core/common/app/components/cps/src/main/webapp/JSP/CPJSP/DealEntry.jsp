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
<body class="remove-bg" onload="enableAnchor();activeTab('a')" oncontextmenu="return false;" style="position: fixed; width:3000px;">
<form name="test" id="test">
<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
<input type="hidden" name="dealMoveMsg" id="dealMoveMsg" value="<bean:message key="lbl.dealMove" />" />
<!--<input type="hidden" name="recStatus" id="recStatus" value="${leadInfo[0].recStatus}" />-->
<!--<input type="hidden" name="checkModifications" id="checkModifications" value="" />-->
<!--<input type="hidden" name="getFormName" id="getFormName" value="" />-->


<logic:notPresent name="viewDeal">

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
		//document.getElementById(names[z]).removeAttribute("onclick","activeTab(id)");
		document.getElementById(names[z]).setAttribute("onclick","activeTab(id);");
	}

  }
  
}

</script>

<div class="tab-bg">
<div class="ddcolortabs"><ul>
<logic:notEqual name="functionId" value="500000123">
<li><a href="<%=request.getContextPath()%>/deal.do?method=leadEntryCapturing" id="a" onclick="activeTab(id);"><span><bean:message key="lbl.lead" /></span></a></li>
</logic:notEqual>
<li><a href="<%=request.getContextPath()%>/custEntryAction.do" id="b" onclick="activeTab(id);"><span><bean:message key="lbl.custEntry" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/openLoanDetailAction.do" id="c" onclick="activeTab(id);"><span><bean:message key="lbl.loanDetails" /></span></a></li>
<logic:notEqual name="functionId" value="500000123">
<li><a href="<%=request.getContextPath()%>/docsCollectionBehindAction.do" id="d" onclick="activeTab(id);"><span><bean:message key="lbl.docsCollection" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/notePadPageBehind.do?status=np" id="e" onclick="activeTab(id);"><span><bean:message key="lbl.notePad" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/devationPageBehind.do?status=UWA" id="f" onclick="activeTab(id);"><span ><bean:message key="lbl.deviationTab" /></span></a></li>

<!--Abhimanyu added New Tab Start-->
<logic:present name="Viability">
   <logic:equal name="Viability"  value="Y">
<li><a href="<%=request.getContextPath()%>/viabilityAction.do?method=openViability" id="g"  onclick="activeTab(id);"><span ><bean:message key="lbl.viability" /></span></a></li>
<!--Abhimanyu added New Tab End-->
 </logic:equal>
</logic:present>
</logic:notEqual>
<%-- Start By Prashant  --%>
<logic:present name="fleetDetail">
   <logic:equal name="fleetDetail"  value="Y">
<li><a href="<%=request.getContextPath()%>/fleetBehindAction.do?method=openFleetPage" id="h" onclick="activeTab(id);"><span ><bean:message key="lbl.fleetDetail" /></span></a></li>
<%-- End By Prashant --%>
 </logic:equal>
</logic:present>
<li><input type="hidden" name="getTabId" id="getTabId"/></li>
<li><input type="hidden" name="recStatus" id="recStatus" value="${leadInfo[0].recStatus}" /></li>
<li><input type="hidden" name="checkModifications" id="checkModifications" value="" /></li>
<li><input type="hidden" name="getFormName" id="getFormName"/></li>


</ul>
</div>


<div class="ddcolortabsline"></div>

<div id="tabs">
</div>
	
</div>
</logic:notPresent>

<logic:present name="viewDeal">
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
names[9]='j';
names[10]='k';
names[11]='m';
names[11]='n';
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
<logic:notEqual name="functionId" value="500000123">
<li><a href="<%=request.getContextPath()%>/deal.do?method=leadEntryCapturing&status=UWA" id="a" onclick="activeTab(id);"><span ><bean:message key="lbl.lead" /></span></a></li>
</logic:notEqual>
<li><a href="<%=request.getContextPath()%>/custEntryAction.do?&status=UWA" id="b" onclick="activeTab(id);"><span ><bean:message key="lbl.custEntry" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/openLoanDetailAction.do?status=UWA" id="c" onclick="activeTab(id);"><span ><bean:message key="lbl.loanDetails" /></span></a></li>
<logic:notEqual name="functionId" value="500000123">
<li><a href="<%=request.getContextPath()%>/docsCollectionBehindAction.do?status=UWA" id="d" onclick="activeTab(id);"><span ><bean:message key="lbl.docsCollection" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/notePadPageBehind.do?status=UWA" id="e" onclick="activeTab(id);"><span ><bean:message key="lbl.notePad" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/devationPageBehind.do?status=UWA" id="f" onclick="activeTab(id);"><span ><bean:message key="lbl.deviationTab" /></span></a></li>
<!--Abhimanyu added New Tab Start-->
<logic:present name="Viability">
   <logic:equal name="Viability"  value="Y">
<li><a href="<%=request.getContextPath()%>/viabilityAction.do?method=openViabilityView" id="g" onclick="activeTab(id);"><span ><bean:message key="lbl.viability" /></span></a></li>
<!--Abhimanyu added New Tab End-->
 </logic:equal>
</logic:present>


<logic:present name="strFlagDV">
<logic:notPresent name="strFlagQ">
	<li><a href="<%=request.getContextPath()%>/commonPageBehind.do?dealId=" id="h" onclick="activeTab(id);" ><span ><bean:message key="lbl.underwriter" /></span></a></li>
</logic:notPresent>
</logic:present>
<logic:present name="strFlagQ">
	<li ><a href="<%=request.getContextPath()%>/termSheetDispatchAction.do?method=openTermSheet" id="h" onclick="activeTab(id);" ><span >Term Sheet</span></a></li>
</logic:present>
<%-- Start By Prashant  --%>
<logic:present name="fleetDetail">
   <logic:equal name="fleetDetail"  value="Y">
<li><a href="<%=request.getContextPath()%>/fleetBehindAction.do?method=openAuthorFleetPage" id="i" onclick="activeTab(id);" ><span ><bean:message key="lbl.fleetDetail" /></span></a></li>
 </logic:equal>
</logic:present>
<li><a href="<%=request.getContextPath()%>/queryResponseBehindAction.do" id="j" onclick="activeTab(id);" ><span ><bean:message key="lbl.queryResponse" /></span></a></li>
</logic:notEqual>
<%-- End By Prashant --%>
<li><input type="hidden" name="getTabId" id="getTabId"/></li>
<li><input type="hidden" name="recStatus" id="recStatus" value="P" /></li>
<li><input type="hidden" name="checkModifications" id="checkModifications" value="" /></li>
<li><input type="hidden" name="getFormName" id="getFormName"/></li>
</ul>
</div>
</div>

<div class="ddcolortabsline"></div>

<div id="tabs">
</div>
</logic:present>
</form>
</body>
</html>