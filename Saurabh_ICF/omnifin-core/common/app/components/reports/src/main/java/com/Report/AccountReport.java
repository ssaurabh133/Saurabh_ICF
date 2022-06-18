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
import net.sf.jasperreports.engine.JasperRunManager;

public class AccountReport extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	
		System.out.println("In servlet rptchartofaccounts======");
       try
       {
			ServletOutputStream servletOutputStream = response.getOutputStream();
			Connection connectDatabase =null;
			byte[] bytes = null;
			Map hashMap = new HashMap();
			try {
	
				
				String p_company = "Intec Capital";
				String accountNumber=request.getParameter("accountnumber");
		
				
	
				String file = "RotGISAccountSummary";
	
				
				hashMap.put("p_company", p_company);
				hashMap.put("Emp_policy_Number",accountNumber);
				
				File reportFile = new File(getServletConfig().getServletContext().getRealPath("/reports/" + file + ".jasper"));
				 connectDatabase = ConnectionDAO.getConnection();
				bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),hashMap, connectDatabase);
	
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
			}
			finally
			{
				ConnectionDAO.closeConnection(connectDatabase, null);
				hashMap.clear();
			}
       }
       catch(Exception e)
		{
			e.printStackTrace();
		}	
	}
}