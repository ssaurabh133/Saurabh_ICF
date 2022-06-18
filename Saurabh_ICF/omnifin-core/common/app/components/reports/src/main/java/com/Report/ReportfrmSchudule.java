package com.Report;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.log4j.Logger;

import com.GL.util.ParseStringToDate;
import com.connect.ConnectionDAO;

public class ReportfrmSchudule extends HttpServlet {

	private static final Logger logger = Logger.getLogger(ReportfrmSchudule.class.getName());


	 ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
	    String dateFormat = resource.getString("lbl.dateForDisbursal");
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try
		{
			Connection connectDatabase = ConnectionDAO.getConnection();
			HashMap hashMap = new HashMap();
				try {
					String sid = (String) request.getAttribute("sessID");
					String fdt = (String) request.getAttribute("from_date");
					String tdt = (String) request.getAttribute("to_date");
					ServletOutputStream servletOutputStream = response
							.getOutputStream();
					String file = "RptFAScheduleVI";
					byte[] bytes = null;
					
			
					hashMap.put("i_session_id", sid);
					hashMap.put("i_from_date", fdt);
					hashMap.put("i_to_date", tdt);
					System.out.println(sid + "#" + sid + "#fdate" + fdt + "#" + tdt
							+ "#" + fdt);
		          
					//hashMap.put("i_from_date_calc", PropertyConfigurationController.getDateConvertion(fdt));
					SimpleDateFormat formate=new SimpleDateFormat("yyyy-MM-dd");
					//logger.info("date:---------"+date);
					String formateDate=formate.format(ParseStringToDate.changeStringToDate(fdt));
					logger.info("formateDate:-------"+formateDate);
					hashMap.put("i_from_date_calc", formateDate);
					System.out.println(sid + "#" + sid + "#fdate" + fdt + "#" + tdt
							+ "#");
		
					
					File reportFile111 = new File(getServletConfig().getServletContext()
							.getRealPath("/reports/" + file + ".jasper"));
					System.out.println("path  reportFile======================="
							+ reportFile111);
		
					
					System.out.println("path  reportFileknbjdfsknbjdfsklnhjadsklnjadfskl;jdfskl;jadfsdkljs======================="+ reportFile111);
					bytes = JasperRunManager.runReportToPdf(reportFile111.getPath(),hashMap, connectDatabase);
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
