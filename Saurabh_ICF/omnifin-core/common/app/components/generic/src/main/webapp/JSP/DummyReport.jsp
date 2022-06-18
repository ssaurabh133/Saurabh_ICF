<%@page import="java.util.Date,java.text.SimpleDateFormat;"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%@include file="/JSP/commonIncludeContent.jsp" %> 

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
	<%

		String sid = (String)request.getAttribute("sessID");
		
		String fdt = (String)request.getAttribute("fromDate");
	
		String tdt = (String)request.getAttribute("toDate");
		
		String accN = ((String)request.getAttribute("AccountName"));
		
		
		String bID=(String)request.getAttribute("branchId");
		
		if ("".equalsIgnoreCase(bID) )
		{
			bID = "All";
	
			}	
		
	
		String mID=(String)request.getAttribute("moduleId");
		if ("".equalsIgnoreCase(mID))
		{
			mID = "All";
		}
	   
		String pID=(String)request.getAttribute("productId");
		
		if ("".equalsIgnoreCase(pID))
		{
			pID = "All";
		}
		Integer dID = (Integer)request.getAttribute("departmentId");
		
		
		String CompanyName = (String)request.getAttribute("companyName");
		String rptType=(String)request.getAttribute("rptType");
		if(rptType.equalsIgnoreCase("Sub Ledger")){
		accN=(String)request.getAttribute("subLedgerId");
		}
		String Header =rptType+ " Book A/c of " + accN + " From " + fdt + " To " + tdt ;
		
		String fheaders = "(Figures in Rupees)";
		
		Date date=new Date();
		SimpleDateFormat dateFormate=new SimpleDateFormat("dd-MM-yyyy");
		String formatedDate=dateFormate.format(date);
		String rdate = "Report Date : " +formatedDate; 
		
		  
		
	 %>

	
	
 	
 	
 	</body>
</html>