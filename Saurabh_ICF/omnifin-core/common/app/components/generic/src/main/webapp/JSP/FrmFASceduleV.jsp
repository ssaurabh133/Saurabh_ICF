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

		String sid = (String)request.getAttribute("sessID");
	    String fdt = (String)request.getAttribute("from_date");	
		String tdt = (String)request.getAttribute("to_date"); 
		String procval = (String)request.getAttribute("procval"); 
		request.setAttribute("sessID",sid);
		request.setAttribute("from_date",fdt);
		request.setAttribute("to_date",tdt);

		 
	 %>
<logic:present name="procval">
	<script type="text/javascript">
	if('<%=request.getAttribute("procval")%>'!='')
	{
	   	alert('<%=request.getAttribute("procval").toString()%>');
		
	}else{
	<% request.getRequestDispatcher("/ReportfrmSchudule").forward(request,response);%>
	}
	</script>
</logic:present>
 	</body>
</html>