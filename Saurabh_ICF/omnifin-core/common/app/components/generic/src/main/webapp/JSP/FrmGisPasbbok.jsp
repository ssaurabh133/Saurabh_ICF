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

		String policy_id = (String)request.getParameter("loanid");
	    String duplicate = (String)request.getParameter("duplicate");
	    request.setAttribute("loanid",policy_id);
	     request.setAttribute("duplicate",duplicate);
	  
		
     request.getRequestDispatcher("/ReportGisPassbook").forward(request,response);
    
		
		 
	 %>
	
	
 	</body>
</html>