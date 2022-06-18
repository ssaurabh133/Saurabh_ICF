<!-- 
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 10 - jan 2012
 	Purpose          :- To provide user interface for all tabs of Individual financial analysis
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
    
     <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
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
<!--  <li onclick="callFunction('/individualFinancialBankAccAnalysisAction.do?method=openIndividualFinancialBankAccBehind','a');"><a href="#" target="body" id="a" onclick="activeTab(id);"><span><bean:message key="lbl.bankAccountAnalysis" /></span></a></li>
-->
<%-- <li onclick="callFunction('/individualObligationBehindAction.do?method=obligationBehindDetail','b');"><a href="#" target="body" id="c" onclick="activeTab(id);"><span><bean:message key="lbl.obligation" /></span></a></li> --%>
<!-- Sarvesh Changes For the CP Financial Analysis. end all tab should be apear in all situation-->
<li onclick="callFunction('/individualFinancialIncomeBehindAction.do?method=incomeBehindDetail','a');"><a href="#" target="body" id="a" onclick="activeTab(id);"><span><bean:message key="lbl.income" /></span></a></li>
<li onclick="callFunction('/editFinancialAnalysis.do?method=openEditFinancialAnalysis&reloadFlag=reload','b');"><a href="#" target="body" id="b" onclick="activeTab(id);"><span><bean:message key="lbl.balanceSheet" /></span></a></li>
<li onclick="callFunction('/profitAndLossBehindAction.do?method=profitAndLossBehindDetail','c');"><a href="<#" target="body" id="c" onclick="activeTab(id);"><span><bean:message key="lbl.profitAndLoss" /></span></a></li>
<li onclick="callFunction('/othersBehindAction.do?method=othersBehindDetail','d');"><a href="#" target="body" id="d" onclick="activeTab(id);"><span><bean:message key="lbl.others" /></span></a></li>
<li onclick="callFunction('/individualRatioAnalysisBehindAction.do?method=individualRatioAnalysisBehindDetail','e');"><a href="#" target="body" id="e" onclick="activeTab(id);"><span><bean:message key="lbl.ratioAnalysisForIndividual" /></span></a></li>
<li onclick="callFunction('/ratioAnalysisBehindAction.do?method=ratioAnalysisBehindDetail','f');"><a href="#" target="body" id="f" onclick="activeTab(id);"><span><bean:message key="lbl.ratioAnalysisForCorporate" /></span></a></li>
<!-- Sarvesh Changes For the CP Financial Analysis. End-->
<li><input type="hidden" name="recStatus" id="recStatus" value="P" /></li>
<li><input type="hidden" name="checkModifications" id="checkModifications" value="" /></li>
<li><input type="hidden" name="getFormName" id="getFormName"/></li>
</ul>
</div>


<div class="ddcolortabsline"></div>

<div id="tabs">
</div>

</div>

</body>
</html>

