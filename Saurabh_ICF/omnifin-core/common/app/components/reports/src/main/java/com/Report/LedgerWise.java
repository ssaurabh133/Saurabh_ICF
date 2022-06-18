package com.Report;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.business.util.PropertyConfigurationController;
import com.connect.ConnectionDAO;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperRunManager;

public class LedgerWise extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ServletOutputStream servletOutputStream = response.getOutputStream();
try{
		byte[] bytes = null;
		Connection connectDatabase = ConnectionDAO.getConnection();
		HashMap hashMap = new HashMap();

		try {
			/*
			 * String penid=request.getParameter("penid"); String
			 * empSex=request.getParameter("empSex"); String
			 * premium_from=request.getParameter("premium_from"); String
			 * premium_to=request.getParameter("premium_to"); String
			 * sum_from=request.getParameter("sum_from"); String
			 * sum_to=request.getParameter("sum_to");
			 * 
			 * String age_from=request.getParameter("age_from");
			 * 
			 * String age_to=request.getParameter("age_to");
			 * 
			 * String dor_from=request.getParameter("dor_from");
			 * 
			 * String dor_to=request.getParameter("dor_to");
			 * 
			 * String dob_from=request.getParameter("dob_from");
			 * 
			 * String dob_to=request.getParameter("dob_to");
			 * 
			 * String dom_from=request.getParameter("dom_from");
			 * 
			 * String dom_to=request.getParameter("dom_to");
			 */
			Integer CompanyID = 1;
			String CompanyName = "KSID";
			String Header = "Chart of Accounts ";

			String file = "RptChartOfAccounts";

			String p1 = "/reports/" + file.trim() + ".jrxml";
			System.out.println(getServletContext().getRealPath(p1));
			String paths = getServletContext().getRealPath(p1);

			String p2 = "/reports/" + file.trim() + ".jasper";

			String japerpath = getServletContext().getRealPath(p2);

			hashMap.put("p_company_id", CompanyID);
			hashMap.put("p_headers", Header);
			hashMap.put("p_company_name", CompanyName);

			String reportPath = paths;
			japerpath = JasperCompileManager.compileReportToFile(paths);
			
			String dest = "test";

			bytes = JasperRunManager.runReportToPdf(japerpath, hashMap, connectDatabase);
			System.out.println(bytes);
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);

			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			ConnectionDAO.closeConnection(connectDatabase, null);
			hashMap.clear();
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	}
}