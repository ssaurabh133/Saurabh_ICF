
package com.cm.actions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
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
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.itextpdf.text.pdf.PdfWriter;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.Menu;
import com.login.roleManager.UserObject;
import com.cm.dao.ReportsDAO;
import com.communication.engn.servlets.BulkEmail;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.DaoImplInstanceFactory;
import com.connect.PrepStmtObject;


public class GenerateAllReports extends Action 
{	
	private static final Logger logger = Logger.getLogger(GenerateAllReports.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	
	{	
		logger.info("In ReportsAction1.........");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String nhb_date=null;
		if(userobj!=null)
		{				
			nhb_date=userobj.getBusinessdate();
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
		//logger.info("strFlag .............. "+strFlag);
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
		logger.info("dateFormat.. "+dateFormat+ "dbType.. " +dbType +"reportPath.. "+reportPath);
		if(dbType.equalsIgnoreCase("MSSQL"))
			reportPath=reportPath+"MSSQLREPORTS/";
		else
			reportPath=reportPath+"MYSQLREPORTS/";		
		
		logger.info("reportPath.. "+reportPath);
				
		String reportName=(String)session.getAttribute("reportName");
		String reporttype=(String)session.getAttribute("reporttype");
		String passwordPdf=(String)session.getAttribute("passwordPdf"); // shubham
		
		Map<Object,Object> hashMap = null;
		hashMap=(Map)session.getAttribute("hashMap");
		Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
		//richa space starts
		String loanId = (String)session.getAttribute("p_loan_id");
		logger.info("shubham in session"+ loanId);
		logger.info("shubham in session p_loan_id"+ session.getAttribute("p_loan_id"));
				if(reportName.equalsIgnoreCase("sanction_letter_after_loan"))
				{
					
					ReportsDAO dao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
					reportName=CommonFunction.checkNull(dao.getSanctionReport(loanId));	
					
					hashMap.put("p_loan_id", loanId);
					logger.info("loan id : " + loanId );
				}
				//richa space ends
		//code added by Nishant
		
		String SUBREPORT_DIR=getServlet().getServletContext().getRealPath("/")+"reports\\";
		String customer_detail_location=getServlet().getServletContext().getRealPath("/")+"reports\\";
		String sub_reports_location=getServlet().getServletContext().getRealPath("/")+"reports\\";
		//String loanId = (String)session.getAttribute("p_loan_id");
		String deal_id = null;
		if(reportName.equalsIgnoreCase("loan_credit_appraisal_memo"))
		{
			if(dbType.equalsIgnoreCase("MSSQL"))
			{
				sub_reports_location=sub_reports_location+"MSSQLREPORTS\\";
				SUBREPORT_DIR=SUBREPORT_DIR+"MSSQLREPORTS\\";
				customer_detail_location=customer_detail_location+"MSSQLREPORTS\\";					
			}
			else
			{
				sub_reports_location=sub_reports_location+"MYSQLREPORTS\\";
				SUBREPORT_DIR=SUBREPORT_DIR+"MYSQLREPORTS\\";
				customer_detail_location=customer_detail_location+"MYSQLREPORTS\\";
			}	
			ReportsDAO dao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
			reportName=CommonFunction.checkNull(dao.getCAMReportAtLoan(loanId));	
			deal_id=CommonFunction.checkNull(dao.getDealId(loanId));
			
		/*	if(CommonFunction.checkNull(reportName).trim().equalsIgnoreCase(""))
				return mapping.findForward("uw");	
			if(CommonFunction.checkNull(reportName).trim().equalsIgnoreCase("MMCAMReport"))
			{
				boolean elStatus=dao.getEligibilityRecord(p_deal_id,p_business_date,userId);
			}*/
			customer_detail_location=customer_detail_location+"dealSubReports\\";
			SUBREPORT_DIR=SUBREPORT_DIR+"loanCamSubReports\\";
			hashMap.put("sub_reports_location", sub_reports_location);
			hashMap.put("SUBREPORT_DIR", SUBREPORT_DIR);
			hashMap.put("customer_detail_location", customer_detail_location);
			hashMap.put("p_loan_id", loanId);
			hashMap.put("deal_id", deal_id);
			logger.info("SUBREPORT_DIR : " + SUBREPORT_DIR);
			logger.info("customer_detail_location : " + customer_detail_location);
			logger.info("loan id : " + loanId + " deal id : " + deal_id);
		}
        //Nishant space end
		if("rp_collateral_mony_receipt".equalsIgnoreCase(reportName)|| "NOC_report_asset_Funded".equalsIgnoreCase(reportName) || "NOC_report".equalsIgnoreCase(reportName) || "SD_LIQUIDATION_REPORT".equalsIgnoreCase(reportName) || "PROVISIONAL_INTEREST_CERTIFICATE".equalsIgnoreCase(reportName) || "ECS_Mandete_Cover_Letter_Header".equalsIgnoreCase(reportName)  || "PROVISIONAL_INTEREST_CERTIFICATE".equalsIgnoreCase(reportName) || "Sanctionletter(MM)".equalsIgnoreCase(reportName) || "WelcomeLetter(MM)".equalsIgnoreCase(reportName) || "margin_deposit_receipt(mm)".equalsIgnoreCase(reportName) || "reset_letter(mm)".equalsIgnoreCase(reportName))
			reporttype="P";
		if("Delinquency_Report_For_Month_End".equalsIgnoreCase(reportName))
			reporttype="E";
				
		hashMap.put("p_report_format",reporttype );
		logger.info("report Name +reportPath :  "+reportPath+ reportName + ".jasper");
		String source = (String)session.getAttribute("source");
		InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+reportName+".jasper");
		
		
			
		JasperPrint jasperPrint = null;				
		try
		{
			if("statement_of_account".equalsIgnoreCase(reportName) && CommonFunction.checkNull(source).equalsIgnoreCase("S")){
				String eventName="STATEMENT_OF_ACCOUNT";
				new BulkEmail().sendManualMail(reportName,hashMap,loanId,eventName,reportStream);
				request.setAttribute("error", "Mail has been sent to primary Applicant");
				connectDatabase = ConnectionReportDumpsDAO.getConnection();
				 reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+reportName+".jasper");
			}
				
			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
			
			if(reporttype.equals("P")){
				// shubham
				if("Sanction Letter Report".equalsIgnoreCase(reportName) && CommonFunction.checkNull(passwordPdf).equalsIgnoreCase("Y")){
					String passwordQuery="select date_format(C.CUSTOMER_DOB,'%d%m%Y') FROM cr_deal_dtl D LEFT JOIN cr_deal_customer_m C ON D.DEAL_CUSTOMER_ID =C.CUSTOMER_ID where deal_id='"+loanId+"'";
            		String pass=ConnectionReportDumpsDAO.singleReturn(passwordQuery);
            		logger.info("passwordQuery: "+passwordQuery);
            		logger.info("pass: "+pass);
            		String ownerPasswordQuery="select ifnull(primary_phone,'') from cr_deal_dtl l join gcd_customer_m g on g.customer_id=l.deal_customer_id Join com_address_m a on a.BPID=g.customer_id and a.BPTYPE='CS' and a.COMMUNICATION_ADDRESS='Y' where deal_id='"+loanId+"' limit 1";
            		String owner=ConnectionReportDumpsDAO.singleReturn(ownerPasswordQuery);
            		if(CommonFunction.checkNull(owner).equalsIgnoreCase("")){
            			owner=pass;
            		}
            		methodForPasswordPDF(reportName, hashMap, connectDatabase, response, jasperPrint, request,owner , pass);
				}
				//end
				if(CommonFunction.checkNull(passwordPdf).equalsIgnoreCase("Y") && !CommonFunction.checkNull(loanId).equalsIgnoreCase("")){
					
					String passwordQuery="select date_format(g.CUSTOMER_DOB,'%d%m%Y') from cr_loan_dtl l join gcd_customer_m g on g.customer_id=l.loan_customer_id where loan_id='"+loanId+"'";
            		String pass=ConnectionReportDumpsDAO.singleReturn(passwordQuery);
            		logger.info("passwordQuery: "+passwordQuery);
            		logger.info("pass: "+pass);
            		String ownerPasswordQuery="select ifnull(primary_phone,'') from cr_loan_dtl l join gcd_customer_m g on g.customer_id=l.loan_customer_id Join com_address_m a on a.BPID=g.customer_id and a.BPTYPE='CS' and a.COMMUNICATION_ADDRESS='Y' where loan_id='"+loanId+"' limit 1 ";
            		String owner=ConnectionReportDumpsDAO.singleReturn(ownerPasswordQuery);
            		if(CommonFunction.checkNull(owner).equalsIgnoreCase("")){
            			owner=pass;
            		}
            		methodForPasswordPDF(reportName, hashMap, connectDatabase, response, jasperPrint, request,owner , pass);
				}else{
					methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
				}
			}
			else if(reporttype.equals("E"))				
				methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
			else if(reporttype.equals("H"))				
				methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request);
			else if(reporttype.equals("T"))				
			    methodForTEXT(reportName,hashMap,connectDatabase,response, jasperPrint,request);
			else if(reporttype.equals("X"))				
				methodForExcelNEW(reportName,hashMap,connectDatabase,response, jasperPrint);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
			ReportsDAO reportdao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
			logger.info("Implementation class: "+reportdao.getClass());
			// Code to insert report name and access log starts
			String moduleID=null;
			List<Menu> modid=(List<Menu>) session.getAttribute("leftsidemenulist");
			if(modid!=null)
			{
		        	 moduleID = modid.get(0).getModuleID();
		        	logger.info("moduleID"+moduleID);
			}
			String pageid = CommonFunction.checkNull(session.getAttribute("functionId")).toString();
			String bDate = userobj.getBusinessdate();
			reportdao.saveFunctionLogData(CommonFunction.checkNull(userobj.getUserId()), moduleID, pageid, bDate, "", "", reportName, hashMap.toString());
			// Code to insert report name and access log ends
			
			
			if("NOC_report".equalsIgnoreCase(reportName) || "NOC_report_asset_Funded".equalsIgnoreCase(reportName) ) {
				
				logger.info("Implementation class: "+reportdao.getClass());
				String checkLinkLoanFlag=reportdao.checkLinkLoanFlag();
				if(CommonFunction.checkNull(checkLinkLoanFlag).equalsIgnoreCase("Y")){				
			
				boolean status=false;
				int count=0;				
				StringBuffer bufInsSqlTempCount = new StringBuffer();
				Object loan_id=session.getAttribute("noc_loan_id");
				String noc_loan_id=loan_id.toString();
				// logger.info("loan id :::::::::"+noc_loan_id+" and date is::::"+nhb_date);
				bufInsSqlTempCount.append("SELECT COUNT(1) FROM CR_LOAN_DTL WHERE LOAN_ID='"+noc_loan_id+"' AND NOC_FLAG='Y'");
				logger.info("query for count ::::::"+bufInsSqlTempCount.toString());
				 	count =Integer.parseInt(ConnectionReportDumpsDAO.singleReturn(bufInsSqlTempCount.toString()));
				 	
				if (count <= 0){				
				StringBuffer sb=new StringBuffer();	
				ArrayList qryList = new ArrayList();
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				sb.append("UPDATE CR_LOAN_DTL SET NOC_FLAG='Y',NOC_DATE=STR_TO_DATE(?,'"+dateFormat+"') WHERE LOAN_ID=? ");
				
				if (CommonFunction.checkNull(nhb_date).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(nhb_date);

				if (CommonFunction.checkNull(noc_loan_id).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(noc_loan_id);				
				
				insertPrepStmtObject.setSql(sb.toString());

				logger.info("IN generate report update query1 for noc  ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionReportDumpsDAO.sqlInsUpdDeletePrepStmt(qryList);
				//logger.info("In insertBankBranchMaster status is................."+ status);
				
				sb=null;
				insertPrepStmtObject = null;
				qryList.clear();
				qryList=null;	
				hashMap.clear();
				}
				bufInsSqlTempCount=null;
				//form.reset(mapping, request);
				session.removeAttribute("reportName");
				session.removeAttribute("reporttype");
				session.removeAttribute("hashMap");				
				session.removeAttribute("p_loan_id");
				}
			}
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
	public  void methodForHTML(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint,HttpServletRequest request)throws Exception
	{
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
	/*changes by shubham*/
	public void methodForPasswordPDF(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint, HttpServletRequest request,String userPassword,String ownerPassword)throws Exception
  	{			
  		JRPdfExporter exporter = new JRPdfExporter();
  			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
  			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, request.getRealPath("/reports") + "/" +reportName+".pdf");
  			exporter.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE);
  			exporter.setParameter(JRPdfExporterParameter.IS_128_BIT_KEY, Boolean.TRUE);
  			exporter.setParameter(JRPdfExporterParameter.USER_PASSWORD,userPassword);
  			exporter.setParameter(JRPdfExporterParameter.OWNER_PASSWORD, ownerPassword);
  			exporter.setParameter(
  		    JRPdfExporterParameter.PERMISSIONS, 
  		    new Integer(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING)
  		    );
  		  	exporter.exportReport();
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
	/*end*/
	String formate(String date)

	{
		String result=null;
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
		date=null;
		return result;
	}
	public void methodForExcelNEW(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint)throws Exception
	{
		String excelReportFileName=reportName+".xlsx";		
		JExcelApiExporter exporterXLSX = new JExcelApiExporter();
		exporterXLSX.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
		exporterXLSX.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		exporterXLSX.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporterXLSX.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporterXLSX.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		response.setHeader("Content-Disposition", "attachment;filename=" + excelReportFileName);
		response.setContentType("application/vnd.ms-excel");
		exporterXLSX.exportReport();
	}
}
	




