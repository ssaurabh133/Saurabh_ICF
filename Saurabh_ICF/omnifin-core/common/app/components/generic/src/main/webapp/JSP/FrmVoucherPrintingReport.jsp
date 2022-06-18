<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.io.File" %>
<%@ page import="com.connect.ConnectionDAO"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%@include file="/JSP/commonIncludeContent.jsp" %> 


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		    
		<title>MyEclipse Sample Report Access Webpage</title>    
	</head>
  
	<body onload="enableAnchor();">
	<%
	    String sid = (String)request.getAttribute("sessID");
	
		String CompanyName =(String)request.getAttribute("companyName"); 

		
		
	
		
		request.setAttribute("sessID",sid.toString());
	
		request.setAttribute("companyName",CompanyName.toString());
        request.getSession().setAttribute("sessID",sid);
        request.getSession().setAttribute("companyName",CompanyName);
		//request.getRequestDispatcher("/frmVoutcherPrinting").forward(request,response);
		response.sendRedirect("frmVoutcherPrinting");
	
	 %>
	
	
 	</body>
</html>