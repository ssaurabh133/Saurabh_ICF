<%@ page import="net.sf.jasperreports.engine.JasperRunManager" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.io.File" %>
<%@ page import="com.connect.ConnectionDAO"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%@include file="/JSP/commonIncludeContent.jsp" %> 


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		    
		<title>MyEclipse Sample Report AccessMKg</title>    
	</head>
  
	<body onload="enableAnchor();">
	<%

		
	   String product_id = (String)request.getAttribute("productId");
	   String reptype = (String)request.getAttribute("reptype");	   
	   String header = "Header";
	   String todate =  (String)request.getAttribute("toDate");
	   String sid = (String)request.getAttribute("sessID");
	   String companyid = (String)request.getAttribute("companyid"); 
	   if (reptype.equalsIgnoreCase("Proffit Loss"))
	   {
	   	header = "REVENUE ACCOUNT FOR THE YEAR ENDED " + todate + "IN RESPECT OF THE STATE LIFE \n INSURANCE BUSINESS [POLICY HOLDER'S ACCOUNT";
	   } 
	
	 %>
	 
	 
	 <%




    ServletOutputStream servletOutputStream = response.getOutputStream();
  
   
   String file="RptRevenueAccount";
   byte[] bytes = null;
 
   
   HashMap hashMap=new HashMap();
        	
        	hashMap.put("p_product_id", Integer.parseInt( product_id));
        	hashMap.put("p_company_id", Integer.parseInt(companyid));
        	hashMap.put("p_header", header);
        	hashMap.put("p_session_id",sid);
      
    
            File reportFile = new File(getServletConfig().getServletContext().getRealPath("/reports/" + file + ".jasper"));
		
			Connection connectDatabase = ConnectionDAO.getConnection();
			bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),hashMap, connectDatabase);
			System.out.println(bytes);
 
      response.setContentType("application/pdf");
      response.setContentLength(bytes.length);
 
   
      servletOutputStream.write(bytes, 0, bytes.length);
      
      servletOutputStream.flush();
      servletOutputStream.close();
 
   
%>
	 
	 
	 
	 
	 
	 
	 
	 
	
	
	
	
 	</body>
</html>