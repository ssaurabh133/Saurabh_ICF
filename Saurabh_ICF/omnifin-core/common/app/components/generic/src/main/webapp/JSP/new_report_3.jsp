<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">    
		<title>MyEclipse Sample Report Access Webpage</title>    
	</head>
  
	<body onload="enableAnchor();">
	<a href="<%= path + "/frameset?__report=reports/new_report_3.rptdesign&__format=HTML" %>">View Report (reports/new_report_3.rptdesign)</a>
 	</body>
</html>