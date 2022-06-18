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
import com.logger.LoggerMsg;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

public class PolicyReport extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("\n\nIn servlet rptchartofaccounts======\n\n");
try{
		ServletOutputStream servletOutputStream = response.getOutputStream();
		Connection connectDatabase = ConnectionDAO.getConnection();
		byte[] bytes = null;
		Map hashMap = new HashMap();
		try {
			
			String company= "Kerla State Insurance Department ";
			
			String file = "PolicyDetail";			
			hashMap.put("p_company", company);
			File reportFile = new File(getServletConfig().getServletContext().getRealPath("/reports/" + file + ".jasper"));
			
			LoggerMsg.info("Kerla State Insurance Department ");
			bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),hashMap, connectDatabase);

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