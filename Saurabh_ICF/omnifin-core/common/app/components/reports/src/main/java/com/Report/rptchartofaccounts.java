package com.Report;

import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import com.connect.ConnectionDAO;
import com.login.roleManager.UserObject;
import com.business.util.PropertyConfigurationController;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

public class rptchartofaccounts extends HttpServlet {
	private static final Logger logger = Logger.getLogger(rptchartofaccounts.class.getName());
	byte[] bytes = null;
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.info("In servlet rptchartofaccounts======");
		try
		{
			Connection connectDatabase = ConnectionDAO.getConnection();
			ServletOutputStream servletOutputStream = response.getOutputStream();
			HashMap hashMap = new HashMap();
		
	
			try {
	           HttpSession session=request.getSession();
	           UserObject sessuser=new UserObject();
	           sessuser=(UserObject)session.getAttribute("userobject");
				int id = 1;
				String p_company_name = sessuser.getConpanyName()+" ";
				logger.info("In servlet rptchartofaccounts=====p_company_name="+p_company_name);
				String p_headers = "Chart of Accounts ";
				//Nishant space starts
				ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
				String dbType=resource.getString("lbl.dbType");
				String reportPath="/reports/";
				if(dbType.equalsIgnoreCase("MSSQL"))
					reportPath=reportPath+"MSSQLREPORTS/";
				else
					reportPath=reportPath+"MYSQLREPORTS/";
				logger.info("Report path : "+ reportPath);
				//Nishant space ends
	
				String file = "RptChartOfAccounts";
	
				hashMap.put("p_company_id", id);
				hashMap.put("p_company_name", p_company_name);
				hashMap.put("p_headers", p_headers);
				//
				
				File reportFile = new File(getServletConfig().getServletContext().getRealPath(reportPath + file + ".jasper"));
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