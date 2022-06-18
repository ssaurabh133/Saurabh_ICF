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

import net.sf.jasperreports.engine.JasperRunManager;


public class ReportBalansheetReport extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
			{
		try
		{
			Connection connectDatabase = ConnectionDAO.getConnection();
			HashMap hashMap = new HashMap();
			
				try {
					String sid = (String) request.getAttribute("sessID");
					String asOnDate = (String) request.getAttribute("asOnDate");
					String bID = (String) request.getAttribute("branchId");
					if (bID == "") {
						bID = "All";
		
					}
		
					String mID = (String) request.getAttribute("moduleId");
					if (mID == "") {
						mID = "All";
					}
		
					String pID = (String) request.getAttribute("productId");
		
					if (pID == "") {
						pID = "All";
					}
					Integer dID = (Integer) request.getAttribute("departmentId");
		
					String CompanyName = (String) request.getAttribute("companyName");
					String Header = "Balance Sheet As On " + asOnDate;
		
					String fheaders = "(Figures in Rupees)";
		
					Date date = new Date();
					SimpleDateFormat dateFormate = new SimpleDateFormat("dd-MM-yyyy");
					String formatedDate = dateFormate.format(date);
					String rdate = "Report Date : " + formatedDate;
		
					ServletOutputStream servletOutputStream = response
							.getOutputStream();
		
					String file = "RptBalanceSheet";
					byte[] bytes = null;
					
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
