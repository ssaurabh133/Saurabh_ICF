package com.cm.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import org.apache.struts.action.DynaActionForm;
import com.connect.CommonFunction;
import org.omg.CORBA._PolicyStub;
import com.connect.ConnectionReportDumpsDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class RepaymentServicesReportsAction extends Action{
	private static final Logger logger = Logger.getLogger(RepaymentServicesReportsAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{	
		logger.info("In  RepaymentServicesReportsAction-------------------------"); 
		try
		{
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userName="";
				String userId="";
				String p_company_name="";
				if(userobj!=null){
					userName= userobj.getUserName();
					userId= userobj.getUserId();
					p_company_name=userobj.getConpanyName();
				}else{
					logger.info(" in execute method of RepaymentServicesReportsAction action the session is out----------------");
					return mapping.findForward("sessionOut");
				}	
				Object sessionId = session.getAttribute("sessionID");
				ServletContext context = getServlet().getServletContext();
				String strFlag="";	
				if(sessionId!=null)
					strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
				ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
				String dateFormat=resource.getString("lbl.dateInDao");
				String dbType=resource.getString("lbl.dbType");				
				String reportPath="/reports/";				
				if(dbType.equalsIgnoreCase("MSSQL"))
					reportPath=reportPath+"MSSQLREPORTS/";
				else
					reportPath=reportPath+"MYSQLREPORTS/";
	
				Calendar currentDate = Calendar.getInstance();
				SimpleDateFormat formatter= new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
				String p_printed_date = formatter.format(currentDate.getTime());		
				DynaActionForm dif=(DynaActionForm)form;
				String reportName=(String )dif.get("reportName");		
				String reportformat=(String )dif.get("reportformat");
				String p_customer_name=(String )dif.get("custName");
				String instrument_mode=(String )dif.get("InstrumentMode");
				String status=(String )dif.get("status");
				String p_from_date=(String )dif.get("fromDate");
				String p_to_date=(String )dif.get("toDate");
				String asOnDate=(String )dif.get("asonDate");
				String p_loan_number=(String )dif.get("loanNo");
				String p_loan_id=(String )dif.get("lbx_loan_from_id");
				String p_print_date="";
				String p_instrument_mode="";
				String p_status="";
				String p_branch_id=(String )dif.get("lbxBranchId");
				String p_branch_type=(String )dif.get("branch");
				String productCategory=(String )dif.get("producTCategory");
				String loanClass=(String )dif.get("loanClassification");
				logger.info("p_branch_id111::"+p_branch_id);
				if(!CommonFunction.checkNull(p_branch_id).trim().equalsIgnoreCase(""))
				{
					logger.info("p_branch_id::"+p_branch_id);
					p_branch_id=""+p_branch_id.replace("|",",");
					logger.info("p_branch_id (new):::"+p_branch_id);
					}
			
				logger.info("p_branch_type"+p_branch_type);
				logger.info("productCategory"+productCategory);
				logger.info("loanClass"+loanClass);
			
				String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
				if(instrument_mode.equals("All"))
					p_instrument_mode="All";
				if(instrument_mode.equals("Q"))
					p_instrument_mode="PDC";
				if(instrument_mode.equals("E"))
					p_instrument_mode="ECS";		
				if(instrument_mode.equals("H"))
					p_instrument_mode="ACH";
				if(status.equals("All"))
					p_status="All";
				if(status.equals("P"))
					p_status="Pending";
				if(status.equals("F"))
					p_status="Forward";
				if(status.equals("A"))
					p_status="Approved";
				if(status.equals("X"))
					p_status="Cancel";
			
				if(p_loan_number.trim().length()==0)
				{
					p_loan_number="All";
					p_customer_name="All";
				}
			    if(p_from_date.trim().length()==0)
			    	p_print_date="All";
			    else
			    	p_print_date="From   "+formate(p_from_date)+"   To   "+formate(p_to_date);
				Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
				Map<Object,Object> hashMap = new HashMap<Object,Object>();				  
				String p_report_name="";
				String p_reson_status="H";
				if(reportName.equals("H"))
				{
					p_report_name="Hold Instrument Pending/Forward/Approved/Reject During a Period ";
					p_reson_status="H";
					reportName="hold_release_delete";
				}
				if(reportName.equals("L"))
				{
					p_report_name="Release Instrument Pending/Forward/Approved/Reject During a Period ";
					p_reson_status="L";
					reportName="hold_release_delete";
				}
				if(reportName.equals("X"))
				{
					p_report_name="Delete Instrument Pending/Forward/Approved/Reject During a Period ";
					p_reson_status="X";
					reportName="hold_release_delete";
				}
					
				hashMap.put("companyName",p_company_name);
				hashMap.put("printed_by", userName);
				hashMap.put("company_logo",p_company_logo);
				hashMap.put("printed_date", p_printed_date);		
				hashMap.put("p_instrument_mode", p_instrument_mode);
				hashMap.put("p_print_date", p_print_date);
				hashMap.put("p_loan_number", p_loan_number);
				hashMap.put("p_customer_name", p_customer_name);
				hashMap.put("p_status", p_status);
				hashMap.put("p_from_date", p_from_date);
				hashMap.put("p_to_date", p_to_date);
				hashMap.put("p_loan_id", p_loan_id);
				hashMap.put("instrument_mode", instrument_mode);
				hashMap.put("status", status);
				hashMap.put("p_report_name", p_report_name);
				hashMap.put("p_reson_status", p_reson_status);
				hashMap.put("p_date_format", dateFormat);
				hashMap.put("p_report_format", reportformat);
				hashMap.put("p_branch", p_branch_id);
				hashMap.put("p_branch_type", p_branch_type);
				hashMap.put("productCategory", productCategory);
				hashMap.put("loanClass", loanClass);
				hashMap.put("p_user_id", userId);
				InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
				JasperPrint jasperPrint = null;
				try
				{
					logger.info("In  RepaymentServicesReportsAction   report        -------"); 
					jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
					if(reportformat.equals("P"))
						methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
					if(reportformat.equals("E"))				
						methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
					if(reportformat.equals("H"))				
						methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
					hashMap.clear();
					//add by saorabh on 15/02/2014
					userName = null;
					p_company_name= null;
					strFlag = null;
					resource = null;
							dbType= null;
							dateFormat=null;
							reportPath=null;
							currentDate=null;
							formatter=null;
							p_printed_date=null;
							reportName=null;
							reportformat=null;
							instrument_mode=null;
							p_customer_name=null;
							status=null;p_from_date=null;
							p_to_date=null;
							asOnDate=null;
							p_loan_id=null;
							p_loan_number=null;
							p_instrument_mode=null;
							p_status=null;
							p_company_logo=null;
							p_print_date=null;
							p_report_name=null;
							p_reson_status=null;
					// end by saorabh
					
				}
				
	}
	catch(Exception e)
	{
		e.printStackTrace();
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
		//add by saorabh on 15/02/2014
		excelReportFileName=null;
		exporterXLS=null;
		//end by saorabh
	}	
	public void methodForHTML(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint, HttpServletRequest request)throws Exception
	{
		PrintWriter out=response.getWriter();
	    out.append("<head><script type='text/javascript' src='"+request.getContextPath()+"/js/report/report.js'></script></head>");
	   	response.setContentType("text/html");
		String htmlReportFileName=reportName+".html";
		JRHtmlExporter exporter = new JRHtmlExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN , Boolean.FALSE);
		exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME, "./images/");
		ServletContext context = this.getServlet().getServletContext();
		File reportFile = new File(context.getRealPath("/reports/"));
	    String image = reportFile.getPath();
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,image);
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,image + "/");
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
		response.setHeader("Content-Disposition", "attachment;filename=" + htmlReportFileName);
		response.setContentType("application/html");
		
			exporter.exportReport();
			//add by saorabh on 15/01/2014
			htmlReportFileName=null;
			exporter=null;
			image=null;
			reportFile=null;
			//end by saorabh 
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
