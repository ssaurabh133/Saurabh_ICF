
package com.caps.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
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
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.reportUtility.ReportGenerateUtility;
import com.caps.dao.CollReportDAO;
import com.caps.VO.FollowUpTrailVo;
import com.caps.VO.ReallocationMasterVo;



public class FollowUpTrailDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(FollowUpTrailDispatchAction.class.getName());

	
	public ActionForward openFollowUpTrailReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside openFollowUpTrailReport........>>");
		
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String username="";
		String nhb_date="";
		String authorDate="";
		
		String businessDate="";
		if(userobj!=null)
		{
			username=userobj.getUserName();
			userId= userobj.getUserId();	
			nhb_date=userobj.getBusinessdate();
			authorDate=userobj.getBusinessdate();
			businessDate=userobj.getBusinessdate();
			session.setAttribute("userId", userId);
			
		}else{
			logger.info("here in  openFollowUpTrailReport method of FollowUpTrailDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		logger.info("username "+ userId);	
		logger.info("username "+ username);	
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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
	
		FollowUpTrailVo vo = new FollowUpTrailVo();
		DynaValidatorForm DisbursedInitiationAuthorDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,DisbursedInitiationAuthorDynaValidatorForm);
		CollReportDAO collDAO=(CollReportDAO)DaoImplInstanceFactory.getDaoImplInstance(CollReportDAO.IDENTITY);
		  logger.info("Implementation class: "+collDAO.getClass());
		  ArrayList<ReallocationMasterVo> product=collDAO.getProductName();
			ArrayList<ReallocationMasterVo> loanClassification=collDAO.getLoanClassification();
			request.setAttribute("productlist",product);
			request.setAttribute("loanClasslist",loanClassification);
		return mapping.findForward("openSuccess");
	}
	
	 
	//private static final Logger logger = Logger.getLogger(GenerateAllReports.class.getName());
	public ActionForward openReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	
	{ 
		
		logger.info("Inside openReport........>>");
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		String dateFormat=resource.getString("lbl.dateInDao");
		String dbType=resource.getString("lbl.dbType");
	
	HttpSession session =  request.getSession();
	UserObject userobj=(UserObject)session.getAttribute("userobject");
	
	String userName="";
	String userId="";
	String businessDate="";
	String companyName="";
	if(userobj!=null)
	{
		userName= userobj.getUserName();	
		userId= userobj.getUserId();
		businessDate=userobj.getBusinessdate();
		companyName=userobj.getConpanyName();
	}else{
		logger.info("here in  openReport method of FollowUpTrailDispatchAction action the session is out----------------");
		return mapping.findForward("sessionOut");
	}
			
	Object sessionId = session.getAttribute("sessionID");
//for check User session start
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
	
    DynaValidatorForm dForm= (DynaValidatorForm)form;
	String reporttype=dForm.getString("reportformat");
	String loanId=dForm.getString("lbxLoanno");
	String user=dForm.getString("actionTaken");
	 String fromdate=dForm.getString("fromdate");
	String todate=dForm.getString("todate");
	String customerName=dForm.getString("customerName");
	String productCategory=dForm.getString("producTCategory");
	String loanClass=dForm.getString("loanClassification");
	String p_branch_type=dForm.getString("branch");
	String p_branch_id=dForm.getString("lbxBranchId");
	
		String  firstDate="";
	if(!CommonFunction.checkNull(businessDate).trim().equalsIgnoreCase(""))
	{
		firstDate="01"+businessDate.substring(2);
		logger.info("firstDate"+firstDate);
		
	}
	if(!CommonFunction.checkNull(p_branch_id).trim().equalsIgnoreCase(""))
	{
		logger.info("p_branch_id::"+p_branch_id);
		p_branch_id=""+p_branch_id.replace("|",",");
		logger.info("p_branch_id (new):::"+p_branch_id);
		}

	logger.info("p_branch_type"+p_branch_type);
	
	
	String reportName="followUpTrail_Reports";
	logger.info("here in  openReport method of FollowUpTrailDispatchAction >>>"+loanId);
	logger.info("here in  openReport method of FollowUpTrailDispatchAction >>>"+user);
	logger.info("here in  openReport method of FollowUpTrailDispatchAction >>>"+firstDate);
	logger.info("here in  openReport method of FollowUpTrailDispatchAction >>>"+reportName);
	logger.info("here in  openReport method of FollowUpTrailDispatchAction >>>"+todate);
	logger.info("here in  openReport method of FollowUpTrailDispatchAction >>>"+customerName);
	
	logger.info("here in  openReport method of FollowUpTrailDispatchAction >>>"+productCategory);
	logger.info("here in  openReport method of FollowUpTrailDispatchAction >>>"+loanClass);
	logger.info("here in  openReport method of FollowUpTrailDispatchAction >>>"+userId);
	
	/// addition for reports
	String reportPath="/reports/";
	if(dbType.equalsIgnoreCase("MSSQL"))
		reportPath=reportPath+"MSSQLREPORTS/";
	else
		reportPath=reportPath+"MYSQLREPORTS/";
	
	Map<Object,Object> hashMap = new HashMap<Object,Object>();
	Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
	
	String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
	
	businessDate=formate(businessDate);
fromdate=CommonFunction.changeFormat(fromdate);
	todate=CommonFunction.changeFormat(todate);
	
	
	
	hashMap.put("p_report_format",reporttype );
	hashMap.put("p_company_name",companyName );
	hashMap.put("p_company_logo",p_company_logo );
	hashMap.put("p_printed_by",userName );
	hashMap.put("p_printed_date",businessDate );
	hashMap.put("p_loan_id",loanId );
	hashMap.put("firstDate",firstDate );
	hashMap.put("p_to_date",todate );
	hashMap.put("p_from_date",fromdate );
	hashMap.put("p_user",user );
	hashMap.put("productCategory",productCategory);
	hashMap.put("loanClass",loanClass);
	hashMap.put("p_branch", p_branch_id);
	hashMap.put("p_user_id",userId );
	hashMap.put("p_branch_type", p_branch_type);
			
	
	
	logger.info("report Name  :  "+ reportName + ".jasper");
	InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+reportName+".jasper");		
	JasperPrint jasperPrint = null;				
	try
	{
		jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
		if(reporttype.equals("P"))
			methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
		else if(reporttype.equals("E"))				
			methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
		else if(reporttype.equals("H"))				
			methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request);
		else if(reporttype.equals("T"))				
		    methodForTEXT(reportName,hashMap,connectDatabase,response, jasperPrint,request);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	finally
	{
		ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);

	}
	return null;
}
	
		public void methodForPDF(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint, HttpServletRequest request)throws Exception
		{
			
			long timeStamp = System.nanoTime();
			reportName=reportName+"_"+timeStamp;
			JasperExportManager.exportReportToPdfFile(jasperPrint,request.getRealPath("/reports") + "/" +reportName+".pdf");
			File f=new File(request.getRealPath("/reports") + "/" +reportName+".pdf");
			FileInputStream fin = new FileInputStream(f);
			ServletOutputStream outStream = response.getOutputStream();
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment;filename='"+reportName+"'.pdf");
			byte[] buffer = new byte[1024];
			int n = 0;
			try{
				while ((n = fin.read(buffer)) != -1) 
					outStream.write(buffer, 0, n);			
				/*outStream.flush();
				outStream.close();*/
				ReportGenerateUtility.closeOutputStream(reportName,fin, outStream);
			}catch(Exception e){
				logger.error("Socket exception occurred. Now being handled by application to close output steam. "+reportName);
			}
		}	
		public void methodForExcel(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint)throws Exception
		{
			long timeStamp = System.nanoTime();
			reportName=reportName+"_"+timeStamp;
			String excelReportFileName=reportName+".xls";		
			JExcelApiExporter exporterXLS = new JExcelApiExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
			response.setHeader("Content-Disposition", "attachment;filename=" + excelReportFileName);
			response.setContentType("application/vnd.ms-excel");
			StringBuffer buffer = new StringBuffer();
			exporterXLS.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, buffer);
			exporterXLS.exportReport();
			response.getOutputStream().write(buffer.toString().getBytes());
			response.flushBuffer();
		}	
		public  void methodForHTML(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint,HttpServletRequest request)throws Exception
		{
			long timeStamp = System.nanoTime();
			reportName=reportName+"_"+timeStamp;
			PrintWriter out=response.getWriter();
		    out.append("<head><script type='text/javascript' src='"+request.getContextPath()+"/js/report/report.js'></script></head>");
		    if(CommonFunction.checkNull(reportName).trim().equalsIgnoreCase("receiptAllocationCollection"))
		    out.append("<body style='background-image: url(/OmniFin/reports/backGround.bmp)'></body>");	 	   	
			JRHtmlExporter exporter = new JRHtmlExporter();		
			response.setContentType("text/html");
	        request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,jasperPrint);		
			float f1=1.2f;
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN ,Boolean.FALSE);
	        exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,"");
	        exporter.setParameter(JRHtmlExporterParameter.IGNORE_PAGE_MARGINS ,Boolean.TRUE); 
	        exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
	        exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
	        exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
	        exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML,"");
	        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
	        exporter.setParameter(JRHtmlExporterParameter.ZOOM_RATIO ,f1);
	        exporter.exportReport();      
			
				
		}	
		public void methodForTEXT(Object reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint, HttpServletRequest request)throws Exception
		{
			long timeStamp = System.nanoTime();
			reportName=reportName+"_"+timeStamp;
			String excelReportFileName=reportName+".txt";		
			JRCsvExporter exporter = new JRCsvExporter();  
			exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER,"\n");  
			ByteArrayOutputStream baos = new ByteArrayOutputStream();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);  
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);  
			exporter.exportReport();  
			byte[] output = baos.toByteArray();  
			response.setHeader("Content-Disposition", "attachment;filename=" + excelReportFileName);
			response.setContentType("tap/plain");  
			response.setContentLength(output.length);  
			ServletOutputStream ouputStream = response.getOutputStream();  
			ouputStream.write(output);  
			ouputStream.flush();  
			ouputStream.close();
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
