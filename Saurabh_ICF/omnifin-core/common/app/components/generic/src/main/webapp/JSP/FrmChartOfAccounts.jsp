<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%@include file="/JSP/commonIncludeContent.jsp" %> 


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		    
		<title>MyEclipse Sample Report Access Webpage</title>    
	</head>
  
	<body onload="enableAnchor();">
	<%	
     request.getRequestDispatcher("/ReportServlet").forward(request,response);
    %>	
 	</body>
</html>