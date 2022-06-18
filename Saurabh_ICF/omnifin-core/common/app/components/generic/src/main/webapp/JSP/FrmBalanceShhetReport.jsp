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
		    String asOnDate = (String)request.getAttribute("asOnDate");
			String bID=(String)request.getAttribute("branchId");
			request.setAttribute("sessID",sid);
			request.setAttribute("asOnDate",asOnDate);
			request.setAttribute("branchId",bID);
			
     request.getRequestDispatcher("/ReportBalansheetReport").forward(request,response);
  // response.sendRedirect("ReportBalansheetReport");
   %>
	
 	</body>
</html>