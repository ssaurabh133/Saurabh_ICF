package com.Report;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;

public class CashBankReport extends HttpServlet {
	private static final Logger logger = Logger.getLogger(CashBankReport.class.getName());
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try
		{
			Connection connectDatabase = ConnectionDAO.getConnection();
			HashMap hashMap = new HashMap();
					try {
						String sid = (String) request.getAttribute("sessID");
			
						String fdt = (String) request.getAttribute("fromDate");
			
						String tdt = (String) request.getAttribute("toDate");
			
						String accN = ((String) request.getAttribute("AccountName"));
			
						String bID = (String) request.getAttribute("branchId");
						
						String detailOrSum = (String) request.getAttribute("detailOrSum");
			
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
						String rptType = (String) request.getAttribute("rptType");
			
						if (rptType.equalsIgnoreCase("Sub Ledger")) {
							accN = (String) request.getAttribute("subLedgerId");
							String ledgerText= CommonFunction.checkNull(request.getAttribute("ledgerText"));
							logger.info("ledgerText:---"+ledgerText);
							String ledgerId=CommonFunction.checkNull(request.getAttribute("ledgerId"));
							logger.info("ledgerId:---"+ledgerId);
							if(!ledgerText.equalsIgnoreCase("")){
								accN=accN+" ("+ledgerText +")";	
							}
						}
						String Header = rptType + " Book A/c of " + accN + " From " + fdt
								+ " To " + tdt;
			
						String fheaders = "(Figures in Rupees)";
						Date date = new Date();
						SimpleDateFormat dateFormate = new SimpleDateFormat("dd-MM-yyyy");
						String formatedDate = dateFormate.format(date);
						String rdate = "Report Date : " + formatedDate;
			
						ServletOutputStream servletOutputStream = response
								.getOutputStream();
			
						String file = "RptCashBankLedger";
						if(CommonFunction.checkNull(detailOrSum).equalsIgnoreCase("S")){
							file="RptCashBankLedgerSummary";
						}
			
						/*
						 * 
						 * File reportFile = new
						 * File(getServletConfig().getServletContext().getRealPath("/reports/"+file+".jasper"));
						 * System.out.println("path
						 * reportFile======================="+reportFile);
						 * 
						 * Connection connectDatabase=ConnectDatabase.getConnection(); bytes =
						 * JasperRunManager.runReportToPdf(reportFile.getPath(), hashMap,
						 * connectDatabase);
						 */
			
						// byte[] bytes = null;
						//
						// String p1 = "/reports/" + file.trim() + ".jrxml";
						// System.out.println(getServletContext().getRealPath(p1));
						// String paths = getServletContext().getRealPath(p1);
						//
						// String p2 = "/reports/" + file.trim() + ".jasper";
						//
						// String japerpath = getServletContext().getRealPath(p2);
						
			
						hashMap.put("p_session_id", sid);
						hashMap.put("p_company_name", CompanyName);
						hashMap.put("p_headers", Header);
						hashMap.put("p_figures_headers", fheaders);
			
						hashMap.put("p_branchHeaders", bID);
						hashMap.put("p_businessHeaders", mID);
			
						hashMap.put("p_productheaders", pID);
						hashMap.put("p_costcentreheaders", dID);
						hashMap.put("p_report_date", rdate);
			
					logger.info(sid + "#" + CompanyName + "#" + Header + "#"
								+ fheaders + "#" + bID + "#" + mID + "#" + pID + "#" + dID
								+ "#" + rdate);
			
						// JasperDesign jasperDesign = JRXmlLoader.load(path);
						//
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
						byte[] bytes = null;
						File reportFile = new File(getServletConfig().getServletContext()
								.getRealPath("/reports/" + file + ".jasper"));
						logger.info("path  reportFile======================="
								+ reportFile);
			
						
						bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),
								hashMap, connectDatabase);
						response.setContentType("application/pdf");
						response.setContentLength(bytes.length);
			
						servletOutputStream.write(bytes, 0, bytes.length);
						servletOutputStream.flush();
						servletOutputStream.close();
			
					} 
					catch (Exception e) 
					{e.printStackTrace();}
					finally
					{
						ConnectionDAO.closeConnection(connectDatabase, null);
						hashMap.clear();
					}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
