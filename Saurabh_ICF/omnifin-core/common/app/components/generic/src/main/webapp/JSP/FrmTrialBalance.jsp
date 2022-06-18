<%@ page import="net.sf.jasperreports.engine.JasperRunManager" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="java.util.HashMap,java.util.Date,java.text.SimpleDateFormat" %>
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
			String sid = (String) request.getAttribute("sessID");

			String fdt = (String) request.getAttribute("fromDate");

			String tdt = (String) request.getAttribute("toDate");

			String accN = ((String) request.getAttribute("AccountName"));

			String bID = (String) request.getAttribute("branchId");

			if ("".equalsIgnoreCase(bID)) {
				bID = "All";

			}

			String mID = (String) request.getAttribute("moduleId");
			if ("".equalsIgnoreCase(mID)) {
				mID = "All";
			}

			String pID = (String) request.getAttribute("productId");

			if ("".equalsIgnoreCase(pID)) {
				pID = "All";
			}
			Integer dID = (Integer) request.getAttribute("departmentId");

			String CompanyName = (String) request.getAttribute("companyName");
			String rptType = (String) request.getAttribute("rptType");
			String Header = "Trial Balance  From " + fdt + " To " + tdt;

			String fheaders = "(Figures in Rupees)";

			Date date = new Date();
			SimpleDateFormat dateFormate = new SimpleDateFormat("dd-MM-yyyy");
			String formatedDate = dateFormate.format(date);
			String rdate = "Report Date : " + formatedDate;
		%>


		<%
			ServletOutputStream servletOutputStream = response
					.getOutputStream();

			String file = "RptTrialBalance";
			byte[] bytes = null;

			HashMap hashMap = new HashMap();

			hashMap.put("p_company_name", CompanyName);
			hashMap.put("p_headers", Header);
			hashMap.put("p_figures_headers", fheaders);
			hashMap.put("p_session_id", sid);
			hashMap.put("p_report_date", rdate);

			hashMap.put("p_branchHeaders", bID);
			hashMap.put("p_businessHeaders", mID);

			hashMap.put("p_productheaders", pID);
			hashMap.put("p_costcentreheaders", dID);

			System.out.println(sid + "#" + CompanyName + "#" + Header + "#"
					+ fheaders + "#" + bID + "#" + mID + "#" + pID + "#" + dID
					+ "#" + rdate);

			String dest = "test";

			File reportFile = new File(getServletConfig().getServletContext()
					.getRealPath("/reports/" + file + ".jasper"));
			

			Connection connectDatabase = ConnectionDAO.getConnection();
			try{
			
			bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),
					hashMap, connectDatabase);
				response.setContentType("application/pdf");
			response.setContentLength(bytes.length);

			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();
			}catch(Exception e)
			{
				e.printStackTrace();}
				finally
				{
					connectDatabase.close();
				}
		%>



	</body>
</html>