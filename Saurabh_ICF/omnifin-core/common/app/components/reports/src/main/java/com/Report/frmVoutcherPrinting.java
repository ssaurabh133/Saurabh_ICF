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

public class frmVoutcherPrinting extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
try{
		ServletOutputStream servletOutputStream = response.getOutputStream();
		Connection connectDatabase = ConnectionDAO.getConnection();
		String file = "RptVoucherPrinting";
		byte[] bytes = null;
		HashMap hashMap = new HashMap();
		System.out.println("inside servlet.... frm voutcher printing.");
		try {

			/*
			 * String sid = (String) request.getAttribute("sessID");
			 * 
			 * String CompanyName = (String)
			 * request.getAttribute("companyName");
			 * 
			 * String sid = (String) request.getParameter("sessID").trim();
			 * 
			 * String CompanyName = (String)
			 * request.getParameter("companyName").trim();
			 * 
			 */
			String sid = (String) request.getSession().getAttribute("sessID");
			String CompanyName = (String) request.getSession().getAttribute(
					"companyName");

			System.out.println(sid + "servlet-----" + CompanyName);
			String p1 = "/reports/" + file.trim() + ".jrxml";
			System.out.println(getServletContext().getRealPath(p1));
			String paths = getServletContext().getRealPath(p1);

			String p2 = "/reports/" + file.trim() + ".jasper";

			String japerpath = getServletContext().getRealPath(p2);

			
			hashMap.put("p_sessionID", sid);

			hashMap.put("p_company_name", CompanyName);

			System.out.println(sid + "     " + CompanyName);

			// JasperDesign jasperDesign = JRXmlLoader.load(path);

			File reportFile = new File(getServletConfig().getServletContext()
					.getRealPath("/reports/" + file + ".jasper"));
			System.out.println("path  reportFile======================="
					+ reportFile);

			
			bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),
					hashMap, connectDatabase);

			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);

			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (Exception e) {
			// display stack trace in the browser
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
			response.getOutputStream().print(stringWriter.toString());
		}finally
		{
			ConnectionDAO.closeConnection(connectDatabase, null);
			hashMap.clear();
		}
		
	}catch(Exception e){
		
	}
	}
}