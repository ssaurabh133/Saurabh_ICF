
package com.cp.actions;

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
import org.pentaho.di.core.database.FirebirdDatabaseMeta;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.FileUtilityDao;
import com.cp.fundFlowDao.FundFlowAnalysisDAO;
import com.cp.vo.FundFlowDownloadUploadVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;




public class FundFlowSummaryReportDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(FundFlowSummaryReportDispatchAction.class.getName());

	
	public ActionForward fundFlowSummaryReoprt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside fundFlowSummaryReoprt........>>");
		
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
		String dealId="";
		if(session.getAttribute("fundFlowDealId")!=null)
		{
			dealId = session.getAttribute("fundFlowDealId").toString();
		}
		logger.info("dealId:::"+dealId);
		DynaValidatorForm FundFlowSummaryDynaValidatorForm = (DynaValidatorForm)form;
		FundFlowDownloadUploadVo vo=new FundFlowDownloadUploadVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,FundFlowSummaryDynaValidatorForm);
		request.setAttribute("dealId",dealId);
		return mapping.findForward("success");
	}
	
	public ActionForward openSummaryReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	
	{ 
		
		logger.info("Inside openSummaryReport........>>");
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
		logger.info("here in  openSummaryReport method of FundFlowSummaryReportDispatchAction action the session is out----------------");
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
	
	DynaValidatorForm FundFlowSummaryDynaValidatorForm = (DynaValidatorForm)form;
	FundFlowDownloadUploadVo vo=new FundFlowDownloadUploadVo();
	org.apache.commons.beanutils.BeanUtils.copyProperties(vo,FundFlowSummaryDynaValidatorForm);
	String reporttype="";
	String dealId="";
	String stmtDay="";
	String fday="";
	String sday="";
	String tday="";
	String[] strDay=new String[5];
	stmtDay=CommonFunction.checkNull(vo.getLbxdays());
	FundFlowAnalysisDAO dao=(FundFlowAnalysisDAO)DaoImplInstanceFactory.getDaoImplInstance(FundFlowAnalysisDAO.IDENTITY);
	logger.info("stmtDay:::::"+stmtDay);
	if(!stmtDay.equalsIgnoreCase(""))
	{
		strDay=stmtDay.split("\\|");
		logger.info("strDay::::"+strDay);
		if(strDay.length>0)
		{
			fday=strDay[0].toString();	
			sday=strDay[1].toString();	
			tday=strDay[2].toString();	
		}
	}
	
	if(session.getAttribute("fundFlowDealId")!=null)
	{
		dealId = session.getAttribute("fundFlowDealId").toString();
	}
	
	
	ArrayList list=dao.getBankStatementDtl(dealId);
	if(list.size()>0)
	{
	logger.info("list size:::"+list.size());	
	logger.info("list:::"+list);
	}
	String reportName="";
	String fromdate=vo.getFromdate();
	String todate=vo.getTodate();
	reporttype=CommonFunction.checkNull(vo.getReporttype());
	reportName=CommonFunction.checkNull(vo.getReportName());
	logger.info("reportName:::::"+reportName+" reporttype::::::"+reporttype);
	FileUtilityDao fudao = (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
	ArrayList BankingProgramOutMessage = fudao.generateBankingProgram(dealId, businessDate, userId);
	logger.info("BankingProgramOutMessage:::::::"+BankingProgramOutMessage.size());
	/*fday = dao.getDaysForSummeryReport(fday,dealId);
	sday = dao.getDaysForSummeryReport(sday,dealId);
	tday = dao.getDaysForSummeryReport(tday,dealId);
	logger.info("fday: "+fday+" sday: "+sday+" tday: "+tday);*/
	if(reportName.equalsIgnoreCase(""))
	{
		reportName="Fund_Flow_Summary_Report";
		
	}
	/*if(reportName.equalsIgnoreCase("Average_Bank_Bal_Calc_Report"))
	{
	FileUtilityDao fudao = (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
	ArrayList BankingProgramOutMessage = fudao.generateBankingProgram(dealId, businessDate, userId);
	logger.info("BankingProgramOutMessage:::::::"+BankingProgramOutMessage.size());
	}*/
	
	//String strreportName=CommonFunction.checkNull(dao.getSummaryReport(dealId));
/*	if(!CommonFunction.checkNull(strreportName).equalsIgnoreCase(""))
	{
		reportName=strreportName;
	}*/
	
	logger.info("here in  openReport method of FundFlowSummaryReportDispatchAction:::dealId"+dealId);
	logger.info("here in  openReport method of FundFlowSummaryReportDispatchAction:::fromdate"+fromdate);
	logger.info("here in  openReport method of FundFlowSummaryReportDispatchAction:::reportName"+reportName);
	logger.info("here in  openReport method of FundFlowSummaryReportDispatchAction:::todate"+todate);
	logger.info("here in  openReport method of FundFlowSummaryReportDispatchAction:::userId"+userId);
	logger.info("here in  openReport method of FundFlowSummaryReportDispatchAction:::stmtDay"+stmtDay);
	
	String reportPath="/reports/";
	if(dbType.equalsIgnoreCase("MSSQL"))
		reportPath=reportPath+"MSSQLREPORTS/";
	else
		reportPath=reportPath+"MYSQLREPORTS/";
	
	Map<Object,Object> hashMap = new HashMap<Object,Object>();
	Connection connectDatabase = ConnectionDAO.getConnection();		
	
	String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
	
	businessDate=formate(businessDate);
	fromdate=CommonFunction.changeFormat(fromdate);
	todate=CommonFunction.changeFormat(todate);
	String SUBREPORT_DIR=getServlet().getServletContext().getRealPath("/")+"reports\\";
	if(dbType.equalsIgnoreCase("MSSQL"))
	{
		SUBREPORT_DIR=SUBREPORT_DIR+"MSSQLREPORTS\\";
		
	}
	else
	{
		SUBREPORT_DIR=SUBREPORT_DIR+"MYSQLREPORTS\\";
	}
	
	
	hashMap.put("p_report_format",reporttype );
	hashMap.put("p_Current_date",businessDate );
	hashMap.put("p_case_id",dealId );
	hashMap.put("p_from_date",fromdate );
	hashMap.put("p_to_date",todate );
	hashMap.put("p_user_id",userId );
	hashMap.put("SUBREPORT_DIR", SUBREPORT_DIR);
	hashMap.put("stmtDay", stmtDay);
	/*hashMap.put("fday", fday);
	hashMap.put("sday", sday);
	hashMap.put("tday", tday);*/
	
				
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

	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	finally
	{
		ConnectionDAO.closeConnection(connectDatabase, null);

	}
	return null;
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
