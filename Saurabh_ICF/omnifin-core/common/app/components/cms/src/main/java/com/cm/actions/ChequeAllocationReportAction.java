package com.cm.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

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
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.connect.ConnectionReportDumpsDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ChequeAllocationReportAction extends Action {
	
	
	
	private static final Logger logger = Logger.getLogger(ReportsAction.class.getName());
    public ActionForward execute(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)throws Exception
		{
    	   	
    	logger.info("::::::::::::::::in my Reports Action");
    	
    	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
		String dbType=resource.getString("lbl.dbType");
		String dbo=resource.getString("lbl.dbPrefix");
    	HttpSession session=request.getSession();
    	Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();
    	HashMap hashMap = new HashMap();
    	DynaValidatorForm dynaValidatorForm = (DynaValidatorForm) form;
    	
    				String sid = session.getId();
    				String reportName ="";
    				//String reporttype=(String)dynaValidatorForm.get("reportformat");
    				String reporttype=request.getParameter("reportFormat");
    				logger.info("report type is::::::::::::::::"+reporttype);
    				String resultStr=null;
    				UserObject userobj=(UserObject)session.getAttribute("userobject");
    				String userId="";
    				String currDate="";
    				int companyID=0;
    				String CompanyName ="";
    					if(userobj!=null){
    					userId= userobj.getUserId();
    					companyID=userobj.getCompanyId();
    					currDate=userobj.getBusinessdate();
    					CompanyName=userobj.getConpanyName();
    				}else{
    					logger.info(" in execute method of ReportsAction1 action the session is out----------------");
    					return mapping.findForward("sessionOut");
    				}
    				 				
    				Object sessionId = session.getAttribute("sessionID");
    				ServletContext context = getServlet().getServletContext();
    				String strFlag="";	
    				if(sessionId!=null)
    				{
    					strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
    				}				
    				
    				String instrumentID=request.getParameter("instrumentNo");
    				logger.info("instrumentNo::::::::::::::::"+instrumentID);
    				
    				reportName="ChequeAllocationDetail";
    				
    				String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
    				hashMap.put("p_company_logo",p_company_logo);
    				hashMap.put("p_printed_by", userId);
    				logger.info("p_printed_by  :"+ userId);
    				hashMap.put("p_company_name", CompanyName);
    				logger.info("company name : "+ CompanyName);
    				hashMap.put("p_printed_date", currDate);
    				logger.info("p_printed_date  : "+currDate);
    				hashMap.put("p_report_format", reporttype);
    				logger.info("p_report_format  : "+reporttype);
    				hashMap.put("p_instrument_no", instrumentID);
    				logger.info("p_instrument_no  : "+instrumentID);
    			
    				
    				logger.info("report Name  :  "+ reportName + ".jasper");
    				
    				String reportPath="/reports/";
    	
					
					if(dbType.equalsIgnoreCase("MSSQL"))
						reportPath=reportPath+"MSSQLREPORTS/";
					else
						reportPath=reportPath+"MYSQLREPORTS/";
					
    				InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
    				
    				JasperPrint jasperPrint = null;					
    				
    				
    				try
    				{
    					
    					jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
    					if(reporttype.equals("P"))
    						methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
    					if(reporttype.equals("E"))				
    						methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
    					if(reporttype.equals("H"))	
    					{
    						
    						response.setContentType("text/html");
    		                request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,jasperPrint);
    		            Map imagesMap = new HashMap();
    		            float f1=1.2f;
    		            
    		            request.getSession().setAttribute("IMAGES_MAP", imagesMap);
    		            JRHtmlExporter exporter = new JRHtmlExporter();
    		            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
    		            exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN ,Boolean.FALSE);
    		            exporter.setParameter(JRHtmlExporterParameter.IGNORE_PAGE_MARGINS ,Boolean.TRUE); 
    		            exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
    		            exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.FALSE);
    		            exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
    		            exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
    		            exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);
    		            exporter.setParameter(JRHtmlExporterParameter.ZOOM_RATIO ,f1);
    		            ServletContext context1 = this.getServlet().getServletContext();
    		            File reportFile = new File(context1.getRealPath("/reports/"));
    		            String image = reportFile.getPath();
    		            exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,image);
    		            exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,image + "/");
    		            exporter.exportReport();         
    		             
    					}
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


    public void methodForPDF(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint, HttpServletRequest request)throws Exception
    {
    	
    	
    	
        JasperExportManager.exportReportToPdfFile(jasperPrint,request.getRealPath("/reports") + "/" +reportName+".pdf");
    	File f=new File(request.getRealPath("/reports") + "/" +reportName+".pdf");
    	FileInputStream fin = new FileInputStream(f);
    	ServletOutputStream outStream = response.getOutputStream();
    	response.setContentType("application/pdf");
    	response.setHeader("Content-Disposition", "attachment;filename='"+reportName+"'.pdf");
    	byte[] buffer = new byte[1024];
    	int n = 0;
    	while ((n = fin.read(buffer)) != -1) 
    		outStream.write(buffer, 0, n);			
    	outStream.flush();
    	fin.close();
    	outStream.close();
    }	
    public void methodForExcel(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint)throws Exception
    {
    	String excelReportFileName=reportName+".xls";		
    	JExcelApiExporter exporterXLS = new JExcelApiExporter();
    	exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
    	exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
    	exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
    	exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
    	exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
    	response.setHeader("Content-Disposition", "attachment;filename=" + excelReportFileName);
    	response.setContentType("application/vnd.ms-excel");
    	exporterXLS.exportReport();
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