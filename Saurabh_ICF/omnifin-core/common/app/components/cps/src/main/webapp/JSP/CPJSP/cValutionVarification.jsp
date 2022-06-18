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
<logic:notPresent name="strFlagDV">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />    
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
    	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
     
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
names[11]='l';
names[12]='m';
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
<base target="udderWriterBody" /> 
</head>
<body oncontextmenu="return false" onload="enableAnchor();activeTab('a');">
<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
	<form name="test" id="test">
<div class="tab-bg">
<div  class="ddcolortabs"><ul>

<logic:notEqual name="functionId" value="500000123">
<li><a href="<%=request.getContextPath()%>/underWriting.do?method=getUnderWriterDataFrameset" id="a" onclick="activeTab(id);"><span >Under Writing</span></a></li>
</logic:notEqual>   

<logic:equal name="functionId" value="500000123">
<li><a href="<%=request.getContextPath()%>/underWriting.do?method=getUnderWriterDataFrameset" id="a" onclick="activeTab(id);"><span >Co-Lending Under Writing</span></a></li>
</logic:equal>

<li><a href="<%=request.getContextPath()%>/cibilCustomer.do?method=cibilReportLoad" id="b" onclick="activeTab(id);"><span >Bureau</span></a></li>
<li><a href="<%=request.getContextPath()%>/underwritingDocUploadBehind.do" id="c" onclick="activeTab(id);"><span >Doc Upload</span></a></li>
<logic:notEqual name="functionId" value="500000123">
<li><a href="<%=request.getContextPath()%>/personalDiscussBehindAction.do" id="d" onclick="activeTab(id);"><span >Personal Discussion</span></a></li>
<li><a href="<%=request.getContextPath()%>/queryBehind.do?method=showQueryDataFirst" id="e" onclick="activeTab(id);"><span >Query</span></a></li>
<li><a href="<%=request.getContextPath()%>/notePadPageBehind.do?status=np" id="f" onclick="activeTab(id);"><span >NotePad</span></a></li>
</logic:notEqual>
<li><a href="<%=request.getContextPath()%>/devationPageBehind.do" id="g" onclick="activeTab(id);"><span ><bean:message key="lbl.deviationTab" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/underwritingApprovalBehind.do?maker=" id="h" onclick="activeTab(id);" ><span >Approval</span></a></li>

<logic:notEqual name="functionId" value="500000123">
<li><a href="<%=request.getContextPath()%>/dealMovementBehind.do?method=trackDealStages" id="i" onclick="activeTab(id);" ><span >Deal Movement</span></a></li>
<li><a href="<%=request.getContextPath()%>/groupExposureLimitDispatchAction.do?method=openGroupExposerLimitGrid" id="j" onclick="activeTab(id);" ><span >Group Exposure</span></a></li>
<li><a href="<%=request.getContextPath()%>/scoringGeneration.do?method=getScoringDtl" id="k" onclick="activeTab(id);" ><span >Scoring</span></a></li>
<li><a href="<%=request.getContextPath()%>/termSheetDispatchAction.do?method=openTermSheet" id="l" onclick="activeTab(id);" ><span >Term Sheet</span></a></li>
<li><a href="<%=request.getContextPath()%>/reportDownload.do" id="m" onclick="activeTab(id);"><span><bean:message key="lbl.reportDownload" /></span></a></li>
</logic:notEqual>

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
</body>
</logic:notPresent>
<logic:present name="strFlagDV">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />    
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
    	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
     


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
names[11]='l';
names[12]='m';

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

<base target="udderWriterBody" /> 
</head>
<body oncontextmenu="return false" onload="enableAnchor();activeTab('a');">
<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
	<form name="test" id="test">
<div class="tab-bg">
<div  class="ddcolortabs"><ul>

<logic:notEqual name="functionId" value="500000123">
<li> <a href="<%=request.getContextPath()%>/underWriting.do?method=getUnderWriterDataFrameset" id="a" onclick="activeTab(id);"><span >Under Writing</span></a></li>
</logic:notEqual>   
 
<logic:equal name="functionId" value="500000123">
<li> <a href="<%=request.getContextPath()%>/underWriting.do?method=getUnderWriterDataFrameset" id="a" onclick="activeTab(id);"><span >C0-Lending Under Writing</span></a></li>
</logic:equal>


<li><a href="<%=request.getContextPath()%>/cibilCustomer.do?method=cibilReportLoad" id="b" onclick="activeTab(id);"><span >Bureau</span></a></li>
<li><a href="<%=request.getContextPath()%>/underwritingDocUploadBehind.do" id="c" onclick="activeTab(id);"><span >Doc Upload</span></a></li>
<logic:notEqual name="functionId" value="500000123">
<li><a href="<%=request.getContextPath()%>/personalDiscussBehindAction.do" id="d" onclick="activeTab(id);"><span >Personal Discussion</span></a></li>
<li><a href="<%=request.getContextPath()%>/queryBehind.do?method=showQueryDataFirst" id="e" onclick="activeTab(id);"><span >Query</span></a></li>
<li><a href="<%=request.getContextPath()%>/notePadPageBehind.do?status=np" id="f" onclick="activeTab(id);"><span >NotePad</span></a></li>
</logic:notEqual>
<li><a href="<%=request.getContextPath()%>/devationPageBehind.do" id="g" onclick="activeTab(id);"><span ><bean:message key="lbl.deviationTab" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/underwritingApprovalBehind.do?maker=" id="h" onclick="activeTab(id);" ><span >Approval</span></a></li>

<logic:notEqual name="functionId" value="500000123">
<li><a href="<%=request.getContextPath()%>/dealMovementBehind.do?method=trackDealStages" id="i" onclick="activeTab(id);" ><span >Deal Movement</span></a></li>
<logic:notEqual name="functionId" value="500000123">
<li><a href="<%=request.getContextPath()%>/groupExposureLimitDispatchAction.do?method=openGroupExposerLimitGrid" id="j" onclick="activeTab(id);" ><span >Group Exposure</span></a></li>
</logic:notEqual>
<li><a href="<%=request.getContextPath()%>/scoringGeneration.do?method=getScoringDtl" id="k" onclick="activeTab(id);" ><span >Scoring</span></a></li>
<li><a href="<%=request.getContextPath()%>/termSheetDispatchAction.do?method=openTermSheet" id="l" onclick="activeTab(id);" ><span >Term Sheet</span></a></li>
<%-- <li onclick="callFunction('/reportDownload.do','l');"><a href="#" target="body" id="l" onclick="activeTab(id);"><span><bean:message key="lbl.reportDownload" /></span></a></li> --%>
<li><a href="<%=request.getContextPath()%>/reportDownload.do" id="m" onclick="activeTab(id);"><span><bean:message key="lbl.reportDownload" /></span></a></li>
</logic:notEqual>

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

</body>
</logic:present>
</html>