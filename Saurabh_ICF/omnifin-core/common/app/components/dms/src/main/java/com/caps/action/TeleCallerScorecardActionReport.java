package com.caps.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

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

import com.caps.actionForm.UnallocatedReportForm;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.login.roleManager.UserObject;


public class TeleCallerScorecardActionReport extends Action 
{	
	private static final Logger logger = Logger.getLogger(TeleCallerScorecardActionReport.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{	
		
		logger.info("TeleCallerScorecardActionReport..............");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String cname="";
		String username="";
		String bDate="";
		if(userobj!=null)
		{
			cname=userobj.getConpanyName();
			username=userobj.getUserName();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here execute method of  TeleCallerScorecardActionReport action the session is out----------------");
			return mapping.findForward("sessionOut");
		}	
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dbType=resource.getString("lbl.dbType");
	DynaActionForm af=(DynaActionForm)form;	
	String fromDate=(String)af.get("fromDate");
	String user=(String)af.get("toDate");
	fromDate=CommonFunction.changeFormat(fromDate);
	logger.info("date is :::::::>>>>>>>>>"+fromDate);
	logger.info("user is :::::::>>>>>>>>>"+user);	
	String reporttype="P";
	
	String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
	String dateFormat = resource.getString("lbl.dateInDao");
	String dateFormatStr = resource.getString("lbl.dateForDisbursal");
	Connection connectDatabase = ConnectionDAO.getConnection();		
	

	//Date todaydate= new Date();
	logger.info("Business date : " + bDate);
	String p_b_date=CommonFunction.changeFormat(bDate);
	logger.info("p_b_date : " + p_b_date);
	//SimpleDateFormat formate=new SimpleDateFormat(dateFormatStr);
	//String formatedDate=formate.format(todaydate);
	String reportPath="/reports/";
	
	if(dbType.equalsIgnoreCase("MSSQL"))
		reportPath=reportPath+"MSSQLREPORTS/";
	else
		reportPath=reportPath+"MYSQLREPORTS/";
	String file = "TeleCaller_Scorecard";
	logger.info("Report path : " + reportPath);
	Map hashMap = new HashMap();


	InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + file + ".jasper");
	JasperPrint jasperPrint = null;

	hashMap.put("p_printed_date",bDate);
	hashMap.put("p_company_logo",p_company_logo);
	hashMap.put("p_printed_by",username+" ");
	hashMap.put("p_company_name",cname+" "); 
	hashMap.put("p_report_format",reporttype);
	hashMap.put("p_user",user);
	hashMap.put("p_date",fromDate);
	hashMap.put("p_b_date", p_b_date);
		try{
			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
			methodForPDF(file,hashMap,connectDatabase,response, jasperPrint,request);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionDAO.closeConnection(connectDatabase, null);
			hashMap.clear();
		}
			
			return null;
		}


	
	public void methodForPDF(String file,Map hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint, HttpServletRequest request)throws Exception
	{
		JasperExportManager.exportReportToPdfFile(jasperPrint,request.getRealPath("/reports") + "/" +file+".pdf");
		File f=new File(request.getRealPath("/reports") + "/" +file+".pdf");
		FileInputStream fin = new FileInputStream(f);
		ServletOutputStream outStream = response.getOutputStream();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment;filename='"+file+"'.pdf");
		byte[] buffer = new byte[1024];
		int n = 0;
		while ((n = fin.read(buffer)) != -1) 
			outStream.write(buffer, 0, n);			
		outStream.flush();
		fin.close();
		outStream.close();
	}	
	
}
	


