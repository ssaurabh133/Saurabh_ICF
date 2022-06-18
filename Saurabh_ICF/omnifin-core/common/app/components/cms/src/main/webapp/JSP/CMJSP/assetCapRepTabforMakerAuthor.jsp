<%@include file="/JSP/sessioncheck.jsp" %>
<html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
     
     <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
     </script><script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>

</head>
<body onload="enableAnchor();">
<form name="test" id="test">

<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
<div class="tab-bg">

<div id="ddtabs4" class="ddcolortabs"><ul>

<!--<li onclick="callFunction('/JSP/CMJSP/assetVerificationRepComMain.jsp','');"><a href="#" target="body"><span style="color: rgb(255, 255, 255);">Asset Verification Detail</span></a></li>-->
<!--<li onclick="callFunction('/JSP/CMJSP/assetCapRepCapturingAuthor.jsp','');"><a href="#"target="body"><span style="color: rgb(255, 255, 255);">Asset Verification Approval</span></a></li>-->
<!---->
<!--<li><input type="hidden" name="recStatus" id="recStatus" value="P" /></li>-->
<!--<li><input type="hidden" name="checkModifications" id="checkModifications" value="" /></li>-->
<!--<li><input type="hidden" name="getFormName" id="getFormName"/></li>-->
<li><a href="assetVerificationRepComMain.jsp" target="body"><span style="color: rgb(255, 255, 255);">Asset Verification Detail</span></a></li>
<li><a href="assetCapRepCapturingAuthor.jsp"target="body"><span style="color: rgb(255, 255, 255);">Asset Verification Approval</span></a></li>

</ul>
</div>
<div class="ddcolortabsline"></div>

<div id="tabs">
</div>

</div>
</form>
</body>
</html>