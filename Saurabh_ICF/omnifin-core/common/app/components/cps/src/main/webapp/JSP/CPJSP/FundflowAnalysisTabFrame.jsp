<!--
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface for all tabs of fundflow analysis
 	Documentation    :- 
 -->
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
    
    
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
    	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
  
<script type="text/javascript">
var names = new Array;
names[0]='a';
names[1]='b';
names[2]='c';
names[3]='d';
names[4]='e';


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
<base target="document" /> 
</head>
<body oncontextmenu="return false;" onload="enableAnchor();activeTab('a');">
<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
	<form name="test" id="test">
<div class="tab-bg" >

<div id="ddtabs4" class="ddcolortabs"><ul>
<%-- <li onclick="callFunction('/individualObligationBehindAction.do?method=obligationBehindDetail','e');"><a href="#" target="body" id="e" onclick="activeTab(id);"><span><bean:message key="lbl.obligation" /></span></a></li> --%>
<li onclick="callFunction('/bankAccountAnalysisBehindAction.do?method=bankAccountAnalysisBehindDetail','a');"><a href="#" target="body" id="a" onclick="activeTab(id);"><span><bean:message key="lbl.bankAccountAnalysis" /></span></a></li>
<li onclick="callFunction('/salesAnalysisBehindAction.do?method=salesAnalysisBehindDetail','b');"><a href="#" target="body" id="b" onclick="activeTab(id);"><span><bean:message key="lbl.salesAnalysis" /></span></a></li>
<li onclick="callFunction('/obligationBehindAction.do?method=obligationBehindDetail','c');"><a href="#" target="body" id="c" onclick="activeTab(id);"><span><bean:message key="lbl.obligation" /></span></a></li>

<%-- <logic:present name="customerType">
<logic:equal value="C" name="customerType">
<li onclick="callFunction('/salesAnalysisBehindAction.do?method=salesAnalysisBehindDetail','b');"><a href="#" target="body" id="b" onclick="activeTab(id);"><span><bean:message key="lbl.salesAnalysis" /></span></a></li>
<li onclick="callFunction('/obligationBehindAction.do?method=obligationBehindDetail','c');"><a href="#" target="body" id="c" onclick="activeTab(id);"><span><bean:message key="lbl.obligation" /></span></a></li>
</logic:equal>
<logic:notEqual value="C" name="customerType">
</logic:notEqual>
</logic:present> --%>
<logic:present name="fundFlowAuthor">
	<li><a href="fundFlowAuther.jsp" target="body" id="d" onclick="activeTab(id);"><span><bean:message key="lbl.author" /></span></a></li>
</logic:present>
<li onclick="callFunction('/SummaryReport.do?method=fundFlowSummaryReoprt','e');"><a href="#" target="body" id="e" onclick="activeTab(id);"><span><bean:message key="lbl.summaryReport" /></span></a></li>
<%-- <logic:notPresent name="fundFlowAuthor"> --%>
<li onclick="callFunction('/fundFlowAnalysisDownloadUploadReport.do?method=fundFlowAnalysisDownloadUploadReport','f');"><a href="#" target="body" id="f" onclick="activeTab(id);"><span><bean:message key="lbl.FundFlowDownloadUploadReport" /></span></a></li>
<%-- </logic:notPresent> --%>

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
</html>
