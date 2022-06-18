package com.communication.engn.servlets;

import java.io.InputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;

import com.communication.engn.daoImplMySql.SmsDAOImpl;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;

public class MISTriggerAction
{	
	public void startEmailTask(HttpServletRequest request, HttpServletResponse response, String path, InputStream reportStream)
	{
		System.out.println("MIS Genration Trigger : " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		try
		{
			
			String bDate = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DATE_FORMAT(PARAMETER_VALUE,'%d-%m-%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));
			boolean stats = false;
			String  EventName = "MIS_Report";
			String rec = "Select count(1) from comm_event_list_m where Event_name='"
							+ EventName + "' and rec_status='A' ";
				int	 cont = Integer.parseInt(ConnectionDAO
							.singleReturn(rec));
					if (cont != 0) {
						 stats = new SmsDAOImpl().getEmailDetails(
								"", bDate, EventName);
					}
					System.out.println("MIS Data Generation Flag : "+stats);
		}
		catch(Exception e)
		{e.printStackTrace();}
	}	
	
	public void generateHTMLFromReport(String reportName, InputStream reportStream, Map reportParameterMap, StringBuffer out){

		Connection con = null;

        try {
        	
		con = ConnectionDAO.getConnection();		
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, reportParameterMap,con);
		
		
	    out.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/></head>");
	  //  if(CommonFunction.checkNull(reportName).trim().equalsIgnoreCase("receiptAllocationCollection"))
	    out.append("<body>");	 	   	
		JRHtmlExporter exporter = new JRHtmlExporter();		
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN ,Boolean.FALSE);
        exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,"");
        exporter.setParameter(JRHtmlExporterParameter.IGNORE_PAGE_MARGINS ,Boolean.TRUE); 
        exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
        exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
        exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
        exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML,"");
        exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, out);

			exporter.exportReport();

		out.append("</body>").append("</html>");
		    
        } catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		

	    
	}
	
}
	
	
	
	