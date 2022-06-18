
<!-- 
Author Name :-Sachin Singhal
Date of Creation :15-07-2015
Purpose :-  screen for Labels used or Loan Initiation
-->
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
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
<base target="body" /> 
</head>
<body class="remove-bg" onload="enableAnchor();activeTab('a')" oncontextmenu="return false;">
<form name="test" id="test">
<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
<input type="hidden" name="dealMoveMsg" id="dealMoveMsg" value="<bean:message key="lbl.dealMove" />" />




<script type="text/javascript">


var names = new Array;
names[0]='a';
names[1]='b';
names[2]='c';
names[3]='d';


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
<div  class="ddcolortabs"><ul>

<li><a href="<%=request.getContextPath()%>/repaymentServiceAction.do?method=openPartnerDetailsForRsp&forwardString=Y" id="a" onclick="activeTab(id);"><span ><bean:message key="lbl.partnerDetails" /></span></a></li>

<li><a href="<%=request.getContextPath()%>/repaymentServiceAction.do?method=repaymentServiceMainPage&forwardString=Y" id="b" onclick="activeTab(id);"><span ><bean:message key="lbl.installmentPlan" /></span></a></li>

<li><a href="<%=request.getContextPath()%>/repaymentServiceAction.do?method=generateRepaymentSchedule" id="c" onclick="activeTab(id);"><span ><bean:message key="lbl.repaymentSchedule" /></span></a></li>

<logic:present name="RSPAuthor">
<li><a href="<%=request.getContextPath()%>/JSP/CMJSP/rspApproval.jsp" id="d" onclick="activeTab(id);"><span ><bean:message key="lbl.approval" /></span></a></li>
</logic:present>



</ul>
</div>


<div class="ddcolortabsline"></div>

<div id="tabs">
</div>
</div>




</form>


</body>

</html>