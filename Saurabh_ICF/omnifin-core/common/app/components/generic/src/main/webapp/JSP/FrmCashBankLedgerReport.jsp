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
		    String sid = (String) request.getAttribute("sessID");
			String fdt = (String) request.getAttribute("fromDate");
			String tdt = (String) request.getAttribute("toDate");
			String accN = ((String) request.getAttribute("AccountName"));
			String bID = (String) request.getAttribute("branchId");
			String detailOrSum = (String) request.getAttribute("detailOrSum");
			
			request.setAttribute("sessID",sid);
			request.setAttribute("fromDate",fdt);
			request.setAttribute("toDate",tdt);
			request.setAttribute("AccountName",accN);
			request.setAttribute("branchId",bID);
			request.setAttribute("detailOrSum",detailOrSum);
			
	
	// response.sendRedirect("CashBankReport");
	 %>
	 <logic:present name="procval">
	<script type="text/javascript">
	if('<%=request.getAttribute("procval")%>'!='')
	{
	   	alert('<%=request.getAttribute("procval").toString()%>');
		
	}else{
	<% request.getRequestDispatcher("/CashBankReport").forward(request,response);%>
	}
	</script>
</logic:present>
	 <logic:present name="pprocval">
	<script type="text/javascript">
	if('<%=request.getAttribute("pprocval")%>'!='')
	{
	   	alert('<%=request.getAttribute("pprocval").toString()%>');
		
	}else{
	<% request.getRequestDispatcher("/CashBankReport").forward(request,response);%>
	}
	</script>
</logic:present>
 	</body>
</html>