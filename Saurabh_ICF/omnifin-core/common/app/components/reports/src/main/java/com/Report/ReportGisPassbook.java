package com.Report;

import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connect.ConnectionDAO;
import com.business.util.PropertyConfigurationController;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperRunManager;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReportGisPassbook extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	try{
		Connection connectDatabase = ConnectionDAO.getConnection();
		HashMap hashMap = new HashMap();
			try {
					String policy_id = (String) request.getAttribute("loanid");
					String duplicate = (String) request.getAttribute("duplicate");
		
					ServletOutputStream servletOutputStream = response
							.getOutputStream();
		
					String file = "RptGISFPassBiil";
					byte[] bytes = null;
		
					// String p1 = "/reports/" + file.trim() + ".jrxml";
					// System.out.println(getServletContext().getRealPath(p1));
					// String paths = getServletContext().getRealPath(p1);
					//
					// String p2 = "/reports/" + file.trim() + ".jasper";
					//
					// String japerpath = getServletContext().getRealPath(p2);
		
				
		
					hashMap.put("p_account_no", policy_id);
					hashMap.put("p_duplicate", duplicate);
		
					// String reportPath = paths;
					// japerpath = JasperCompileManager.compileReportToFile(paths);
					//
					// Connection con = ConnectDatabase.getConnection();
					//
					// String dest = "test";
					//
					// // JasperFillManager.fillReport(jasperReport, new HashMap(),con);
					// bytes = JasperRunManager.runReportToPdf(japerpath, hashMap, con);
					// System.out.println(bytes);
					File reportFile = new File(getServletConfig().getServletContext()
							.getRealPath("/reports/" + file + ".jasper"));
					System.out.println("path  reportFile======================="
							+ reportFile);
		
					
					bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),
							hashMap, connectDatabase);
					System.out.println(bytes);
		
					response.setContentType("application/pdf");
					response.setContentLength(bytes.length);
		
					servletOutputStream.write(bytes, 0, bytes.length);
		
					servletOutputStream.flush();
					servletOutputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				finally
				{
					ConnectionDAO.closeConnection(connectDatabase, null);
					hashMap.clear();
				}
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	}

}
