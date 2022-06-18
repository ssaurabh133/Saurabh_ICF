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
	<style type="text/css">
		.opacity{
		width:100%;
		height:100%;
		background:url("../../images/screan-loader.gif") 44% -6px no-repeat;
		position: fixed;
		top: 0;
		left: 0;
}
	</style>
    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
<script type="text/javascript">
function activeTab(now){
var names = [];
names[0]='a';
names[1]='b';
names[2]='c';
names[3]='d';
names[4]='e';
names[5]='f';
names[6]='g';
names[7]='h';
names[8]='i';

for (var z=0; z<names.length; z++)
{

	//alert("--current--"+now+"--other--"+names[z]);
	if(names[z] == now)
	{
		//alert("if   ---"+names[z]);
		document.getElementById(now).className='current';
		document.getElementById(now).setAttribute("onclick","return false;");		
	}
	else
	{
		//alert("else ---"+names[z]);		
		document.getElementById(names[z]).className='';
		//document.getElementById(names[z]).removeAttribute("onclick");
		document.getElementById(names[z]).setAttribute("onclick","activeTab(id);");
	}
  }
  
}
</script>
<!--[if IE]>
	<style type="text/css">
		.opacity{
			 position:fixed;
  			 _position:absolute;
  			 top:0;
 			 _top:expression(eval(document.body.scrollTop));
		}
	</style>
<! [endif] -->
<base target="loanbody" /> 
</head>
<body class="remove-bg" onload="enableAnchor();activeTab('a');" oncontextmenu="return false;" style="position: fixed; width:3000px;">
<form name="test" id="test">

<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
<input type="hidden" name="dealMoveMsg" id="dealMoveMsg" value="<bean:message key="lbl.dealMove" />" />
<!--<input type="hidden" name="recStatus" id="recStatus" value="P" />-->
<!--<input type="hidden" name="checkModifications" id="checkModifications" value="" />-->
<!--<input type="hidden" name="getFormName" id="getFormName" value="" />-->


<logic:notPresent name="viewDeal">
<div class="tab-bg">
<div class="ddcolortabs"><ul>

<li><a href="<%=request.getContextPath()%>/loanBehindAction.do?method=loanBehindDetail" id="a" onclick="activeTab(id);"><span>Loan Details</span></a></li>
<li><a href="<%=request.getContextPath()%>/collateralBehindAction.do?method=collateralBehindDetail" id="b" onclick="activeTab(id);"><span><bean:message key="lbl.asset.colllateral" /></span></a></li>
<logic:notEqual name="functionId" value="500000123">
<li><a href="<%=request.getContextPath()%>/cpInsurance.do?method=cpInsuranceBehind" id="i" onclick="activeTab(id);"><span><bean:message key="lbl.insurance" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/chargeBehindAction.do?method=chargeInDeal" id="c" onclick="activeTab(id);"><span>Charges</span></a></li>
<li><a href="<%=request.getContextPath()%>/securityBehindAction.do?method=securityBehind" id="d" onclick="activeTab(id);"><span ><bean:message key="lbl.securityDeposit" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/installmentPlanBehindAction.do" id="e" onclick="activeTab(id);"><span ><bean:message key="lbl.installmentPlan" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/otherChargesPlanBehindAction.do" id="f" onclick="activeTab(id);"><span ><bean:message key="lbl.otherChargesPlan" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/repaymentScheduleBehindAction.do" id="g" onclick="activeTab(id);"><span ><bean:message key="lbl.repaymentSchedule" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/imdMakerDetailsAction.do?method=imdDetailsGrid" id="h" onclick="activeTab(id);"><span ><bean:message key="lbl.imdDetails" /></span></a></li>
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
</logic:notPresent>

<logic:present name="viewDeal">
<div class="tab-bg">
<div class="ddcolortabs"><ul>

<li><a href="<%=request.getContextPath()%>/loanBehindAction.do?method=loanBehindDetail" id="a" onclick="activeTab('a');"><span>Loan Details</span></a></li>
<li><a href="<%=request.getContextPath()%>/collateralBehindAction.do?method=collateralBehindDetail&status=UWA" id="b" onclick="activeTab(id);"><span ><bean:message key="lbl.asset.colllateral" /></span></a></li>
<logic:notEqual name="functionId" value="500000123">
<li><a href="<%=request.getContextPath()%>/cpInsurance.do?method=viewCpInsuranceBehind" id="i" onclick="activeTab(id);"><span><bean:message key="lbl.insurance" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/chargeBehindAction.do?method=chargeInDeal" id="c" onclick="activeTab(id);"><span>Charges</span></a></li>
<li><a href="<%=request.getContextPath()%>/securityBehindAction.do?method=securityBehind" id="d" onclick="activeTab(id);"><span ><bean:message key="lbl.securityDeposit" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/installmentPlanBehindAction.do" id="e" onclick="activeTab(id);"><span ><bean:message key="lbl.installmentPlan" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/otherChargesPlanBehindAction.do" id="f" onclick="activeTab(id);"><span ><bean:message key="lbl.otherChargesPlan" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/repaymentScheduleBehindAction.do" id="g" onclick="activeTab(id);"><span ><bean:message key="lbl.repaymentSchedule" /></span></a></li>
<li><a href="<%=request.getContextPath()%>/imdMakerDetailsAction.do?method=imdDetailsGrid" id="h" onclick="activeTab(id);"><span ><bean:message key="lbl.imdDetails" /></span></a></li>
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
</logic:present>
<div  align="center" class="opacity" style="display:none" id="processingImage" ></div>
</form>
</body>
</html>