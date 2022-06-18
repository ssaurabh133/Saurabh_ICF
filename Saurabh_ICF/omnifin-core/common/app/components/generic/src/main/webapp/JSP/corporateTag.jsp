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
    <base target="body" /> 
</head>
<body onload="enableAnchor();" oncontextmenu="return false">
<form name="test" id="test">

<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
<div class="tab-bg">



<div id="ddtabs4" class="ddcolortabs"><ul>
<logic:notPresent name="custEntryU">
<li onclick="callFunction('/corporateDetailAction.do?method=displayCorporateDetails','a');"><a href="#"><span style="color: rgb(255, 255, 255);">Corporate Details</span></a></li>
<li onclick="callFunction('/corporateAddressAction.do?method=displayCorporateAddress','b');"><a id="corporateAdd" href="#"><span style="color: rgb(255, 255, 255);">Address Details</span></a></li>
<logic:notEqual name="functionId" value="500000123">
<li onclick="callFunction('/corporateStackHolderAction.do','c');"><a href="#"><span style="color: rgb(255, 255, 255);">Management Details</span></a></li>
<li onclick="callFunction('/corporateCradingRatingAction.do','d');"><a href="#"><span style="color: rgb(255, 255, 255);">Credit Rating</span></a></li>
</logic:notEqual>
<li onclick="callFunction('/corporateAddressAction.do?method=displayCorporateReference','e');"><a id="individualRef" href="#"><span style="color: rgb(255, 255, 255);">Reference Details</span></a></li>
<logic:notEqual name="functionId" value="500000123">
<li onclick="callFunction('/corporateAddressAction.do?method=displayCorporateProfile','f');"><a id="corporateProfile" href="#"><span style="color: rgb(255, 255, 255);">Customer Profile</span></a></li>
<li onclick="callFunction('/corporateBusinessActivityBehindAction.do?method=displayBusinessActivity','g');"><a id="businessActivity" href="#"><span style="color: rgb(255, 255, 255);">Business Activity</span></a></li>
<li onclick="callFunction('/corporateBusinessDescirption.do?method=displayBusinessDescirption','h');"><a id="businessDescirption" href="#"><span style="color: rgb(255, 255, 255);">Business Description</span></a></li>
<li onclick="callFunction('/custBankDetailAction.do?method=displayCorpCustBankDetails','i');"><a id="corpCustBankDetails" href="#"><span style="color: rgb(255, 255, 255);">Bank Details</span></a></li>
<logic:equal name="applType" value="PRAPPL">
<logic:present name="SarvaSurksha">
   <logic:equal name="SarvaSurksha"  value="Y">
<li onclick="callFunction('/corporateAddressAction.do?method=displayCorporateSarvaSurksha','f');"><a id="indSarvSurakshaDetails" href="#"><span style="color: rgb(255, 255, 255);">Sarva Surksha</span></a></li>
 </logic:equal>
</logic:present>

</logic:equal>
</logic:notEqual>
</logic:notPresent>
<logic:notEqual name="functionId" value="500000123">
<logic:present name="custEntryU" >
<li onclick="callFunction('/corporateBusinessActivityBehindAction.do?method=displayBusinessActivity','g');"><a id="businessActivity" href="#"><span style="color: rgb(255, 255, 255);">Business Activity</span></a></li>
<li onclick="callFunction('/corporateBusinessDescirption.do?method=displayBusinessDescirption','h');"><a id="businessDescirption" href="#"><span style="color: rgb(255, 255, 255);">Business Description</span></a></li>
</logic:present>
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
</html>