package com.caps.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.connect.ConnectionReportDumpsDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.caps.actionForm.PerformanceReportForm;


public class PerformanceReportsAction extends Action 
{	
	private static final Logger logger = Logger.getLogger(PerformanceReportsAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{	
		logger.info("In PerformanceReportsAction.........");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String cname="";
		String username="";
		if(userobj!=null)
		{
			cname=userobj.getConpanyName();
			username=userobj.getUserName();
		}else{
			logger.info("here execute method of  PerformanceReportsAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	PerformanceReportForm af=(PerformanceReportForm) form;	
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateFormatStr = resource.getString("lbl.dateForDisbursal");
	String reporttype=af.getReportformat();
	String queue=af.getLbxQueuesearchId();
	String fromdate=af.getFromdate();		
	String todate=af.getTodate();

	String userId=af.getLbxUserId();
	String user=af.getUserId();
	String queue_1=af.getQueue();
	String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
	System.out.println("hellooooooo");
	Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
	Map<Object,Object> hashMap = new HashMap<Object,Object>();
//	HttpSession session = request.getSession();
//	UserObject userobj=(UserObject)session.getAttribute("userobject");

//	Calendar currentDate = Calendar.getInstance();
//	SimpleDateFormat formatter= new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
//	String p_printed_date = formatter.format(currentDate.getTime());
	Date todaydate= new Date();
	SimpleDateFormat formate=new SimpleDateFormat(dateFormatStr);
	String formatedDate=formate.format(todaydate);
	String file = "performance_Report1";
	String p_date_from="";
	String p_date_to="";
	String p_p_date="";
	String user_id="";
	String queue_code="";
	if(!(fromdate.trim().length()==0))
	{
		p_date_from=formate(fromdate);
		p_date_to=formate(todate);
	}
	else  
		p_p_date=" From   "+p_date_from+"   To   "+p_date_to+" ";
		if(fromdate.length()==0)
	{
		p_p_date="All";
	}
	if(user!=null)
	{
	if(user.trim().length()==0)
		user_id="All";
	else 
		user_id=userId;
	}
	if(queue_1.trim().length()==0)

		queue_code="All";
	else 
		queue_code=queue_1;
	
	
	logger.info("user_id .............. "+user_id);
	logger.info("queue_code .............. "+queue_code);
//	hashMap.put("wherequery",bufInsSql.toString());
	hashMap.put("p_printed_date",formatedDate);
	hashMap.put("p_company_logo",p_company_logo);
	hashMap.put("p_printed_by",username+" ");
	hashMap.put("p_company_name",cname+" "); 

	hashMap.put("p_user_id",userId);
	hashMap.put("p_user_name",user);
	hashMap.put("p_queue_code",queue_code);
	hashMap.put("p_queue",queue);
	hashMap.put("p_date_from",fromdate);
	hashMap.put("p_date_to", todate);
	hashMap.put("p_p_date", p_p_date);
	hashMap.put("p_date_format", dateFormat);

	InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream("/collReports/" + file + ".jasper");
	JasperPrint jasperPrint = null;
	try
	{
		jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
		if(reporttype.equals("P"))
			methodForPDF(file,hashMap,connectDatabase,response, jasperPrint,request);
		if(reporttype.equals("E"))				
			methodForExcel(file,hashMap,connectDatabase,response,jasperPrint,request);
	   if(reporttype.equals("H"))	
		methodForHTML(file,hashMap,connectDatabase,response,jasperPrint,request);
	}

	catch(Exception e)
	{
		e.printStackTrace();
	}
	finally
	{
		ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
		hashMap.clear();
	}
		return null;
	}

	public void methodForPDF(String file,Map hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint, HttpServletRequest request)throws Exception

	{
	    JasperExportManager.exportReportToPdfFile(jasperPrint, request.getRealPath("/collReports") + "/" +file+".pdf");
		File f=new File(request.getRealPath("/collReports") + "/" +file+".pdf");
		logger.info("Real path of file: "+request.getRealPath("/collReports"));
		FileInputStream fin = new FileInputStream(f);
		ServletOutputStream outStream = response.getOutputStream();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment;filename='"+file+"'.pdf");
		byte[] buffer = new byte[1024];
		int n = 0;
		while ((n = fin.read(buffer)) != -1) 
		{
			outStream.write(buffer, 0, n);
			System.out.println(buffer);
		}
		outStream.flush();
		fin.close();
		outStream.close();
	}	
	public void methodForExcel(String file,Map hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint,HttpServletRequest request)throws Exception
	{
		JExcelApiExporter exporterXLS = new JExcelApiExporter();
		exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporterXLS.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, request.getRealPath("/collReports") + "/" +file+".xls");
		exporterXLS.exportReport();
		File f=new File(request.getRealPath("/collReports") + "/" +file+".xls");
		FileInputStream fin = new FileInputStream(f);
		ServletOutputStream outStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename="+file+".xls");
		byte[] buffer = new byte[1024];
		int n = 0;
		while ((n = fin.read(buffer)) != -1) {
		outStream.write(buffer, 0, n);
		System.out.println(buffer);
		}
		outStream.flush();
		fin.close();
		outStream.close();		
		
	}
	
	public void methodForHTML(String file,Map hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint,HttpServletRequest request)throws Exception
	{
		PrintWriter out=response.getWriter();
	    out.append("<head><script type='text/javascript' src='"+request.getContextPath()+"/js/report/report.js'></script></head>");
	   	response.setContentType("text/html");
	   	
		String htmlReportFileName=file+".html";
		JRHtmlExporter exporter = new JRHtmlExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN , Boolean.FALSE);
		exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME, "./images/");
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "/images/");
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
		response.setHeader("Content-Disposition", "attachment;filename=" + htmlReportFileName);
		response.setContentType("application/html");
		exporter.exportReport();
}	
	String formate(String date)
	{
		String result="";
		int m1=Integer.parseInt(date.substring(3,5));		
		switch(m1)
		{					
			case 1: result=date.substring(0,2)+"-Jan-"+date.substring(6);
					break;					
			case 2: result=date.substring(0,2)+"-Feb-"+date.substring(6);
					break;							
			case 3: result=date.substring(0,2)+"-Mar-"+date.substring(6);
					break;						
			case 4: result=date.substring(0,2)+"-Apr-"+date.substring(6);
					break;					
			case 5: result=date.substring(0,2)+"-May-"+date.substring(6);
					break;					
			case 6: result=date.substring(0,2)+"-Jun-"+date.substring(6);
					break;					
			case 7: result=date.substring(0,2)+"-Jul-"+date.substring(6);
					break;					
			case 8: result=date.substring(0,2)+"-Aug-"+date.substring(6);
					break;				
			case 9: result=date.substring(0,2)+"-Sep-"+date.substring(6);
					break;					
			case 10: result=date.substring(0,2)+"-Oct-"+date.substring(6);
					break;					
			case 11: result=date.substring(0,2)+"-Nov-"+date.substring(6);
					break;					
			case 12: result=date.substring(0,2)+"-Dec-"+date.substring(6);						
		}	
		return result;
	}
}
	


