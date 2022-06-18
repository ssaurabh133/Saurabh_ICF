<%@include file="/JSP/sessioncheck.jsp" %>
<html>
<head>
<title>HTML Frames Example - Footer</title>
 <script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>
<style type="text/css">
a {
	color:#fff;
	}
</style>
</head>
<body onload="enableAnchor();" class="footer">
<p align="center" id="footer"> &copy; Copyright 2011  <a href="http://a3spl.com" target="_blank">
A S Software Services Pvt Ltd</a> | <a href="#">Terms & Conditions</a></p>
<div id="modal" style=" background-color:none; padding:25px; font-size:150%; display:none;"></div>
</body>
</html>