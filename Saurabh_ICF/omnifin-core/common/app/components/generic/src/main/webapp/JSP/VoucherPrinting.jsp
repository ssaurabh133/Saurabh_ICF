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
		
		
		String vtype = (String)request.getAttribute("voucherType");
	
		Integer bID=(Integer)request.getAttribute("branchId");
		

		String mID=(String)request.getAttribute("businees");
		if ("".equalsIgnoreCase(mID) )
		{
			mID = "All";
		}
	 
		String pID=(String)request.getAttribute("productId");
		
		if ("".equalsIgnoreCase(pID) )
		{
			pID = "All";
		}
		Integer dID = (Integer)request.getAttribute("departmentId");
		
	
		String CompanyName =(String)request.getAttribute("companyName");
 %>
	
	<birt:viewer id="birtViewer" reportDesign="reports/VoucherPrinting.rptdesign"
			pattern="frameset"
			format="HTML"
			isHostPage = "true">
			
			<birt:param name="SessionID" value="<%=sid %>"/>
			<birt:param name="CompanyName" value="<%=CompanyName %>"/>
			<birt:param name="BranchHeaders" value="<%=bID %>"/>
			<birt:param name="BusinessHeaders" value="<%=mID %>"/>
			<birt:param name="ProductHeaders" value="<%=pID %>"/>
			<birt:param name="CostCentreHeaders" value="<%=dID%>"/>

			
			

</birt:viewer>
	
	
 	</body>
</html>