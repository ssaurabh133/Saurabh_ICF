package com.cm.actions;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;

import com.connect.CommonFunction;
import com.connect.ConnectionUploadDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class NotepadUploadCM extends DispatchAction
{
	private static final Logger logger = Logger.getLogger(UploadDocuments.class.getName()); 
	static ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
	static String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	static String dateFormat=resource.getString("lbl.dateInDao");
	static String dbType=resource.getString("lbl.dbType");
	static String dbo=resource.getString("lbl.dbPrefix");
	
	static int count=0;
	
	public ActionForward beahindMethod(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{	
		logger.info("In  behindMethod"); 
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info(" in behindMethod the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		if(!"".equalsIgnoreCase(strFlag))
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
		strFlag=null;
		form.reset(mapping, request);
		return mapping.findForward("success");
	}
	public ActionForward notepadUploadcm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{	
		logger.info("In  notepadUpload"); 
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId=null;
		String businessDate=null;
		if(userobj==null){
			logger.info(" in behindMethod the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
		{
			userId=userobj.getUserId();
			businessDate=userobj.getBusinessdate();
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		if(!"".equalsIgnoreCase(strFlag))
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
		String status=null;
		DynaValidatorForm DynaForm = (DynaValidatorForm) form;
		status= notepadUploadcm(request,response,(FormFile)DynaForm.get("docFile"),userId,businessDate);
		logger.info("Uploaded and processing status  :  "+status);
	
		String uploadError=null;
		if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("MXE"))
			uploadError=resource.getString("lbl.maxCount");//MXE
		else if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("MNE"))
			uploadError=resource.getString("lbl.nodata");//MXE
		else if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("FNE"))
			uploadError=resource.getString("lbl.smks");//FNE
		else if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("CLE"))
			uploadError=resource.getString("lbl.formatInvalid");//CLE
		else if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("PRE"))
			uploadError=resource.getString("lbl.errorHeader");//PRE
		else if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("UPE"))
			uploadError=resource.getString("msg.notsuccess");//UPE
		if(!CommonFunction.checkNull(uploadError).trim().equalsIgnoreCase(""))
			request.setAttribute("uploadError",uploadError);
		else	
			request.setAttribute("uploaded",status);
		
		strFlag=null;
		status=null;
		userId=null;
		businessDate=null;
		form.reset(mapping, request);
		return mapping.findForward("success");
	}	
	String notepadUploadcm(HttpServletRequest request,HttpServletResponse response,FormFile myFile,String makerName,String makerDate)
	{		
		logger.info("Inside uploadFile");
		String result=null;
		boolean uploadStatus=false;	
		try
		{
			uploadStatus=UploadDocuments.docUpload(request,myFile);			
			if(uploadStatus)
				result=UploadDocuments.readExcelnotepadUpload(request,response,myFile.toString(),makerName,makerDate);
			else
				result="UPE";
		}
		catch (Exception e) 
		{
			result="PRE";
			e.printStackTrace();}
		makerName=null;
		makerDate=null;
		return result;
	}
	public ActionForward generateErrorLog(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("In generateErrorLog() of notepadUploadAction");
	  	
	    HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
	    String userId=null;
	    String p_company_name=null;
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			p_company_name=userobj.getConpanyName()+" ";	
				
		}
		else
		{
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		
		if(!"".equalsIgnoreCase(strFlag))
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
		Connection connectDatabase = ConnectionUploadDAO.getConnection();		
		Map<Object,Object> hashMap = new HashMap<Object,Object>();
		try
		{
			ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
			String dbType=resource.getString("lbl.dbType");
			String reportPath="/reports/";
			String reportName="generateNotepadUploadErrorLog";//notepadUploadError
			
			if(dbType.equalsIgnoreCase("MSSQL"))
				reportPath=reportPath+"MSSQLREPORTS/";
			else
				reportPath=reportPath+"MYSQLREPORTS/";
			
			
			String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
			
		
						
			hashMap.put("p_company_name",p_company_name );
			hashMap.put("p_company_logo",p_company_logo );
			
			hashMap.put("userId",userId );
			
			logger.info("In generateErrorLog() of rateReviewUpload  p_company_name  :  "+p_company_name);
			logger.info("In generateErrorLog() of rateReviewUpload  p_company_logo  :  "+p_company_logo);
			
			logger.info("In generateErrorLog() of rateReviewUpload  userId          :  "+userId);
			logger.info("In generateErrorLog() of rateReviewUpload  Report Name     :  "+reportPath+reportName+".jasper");
			
			InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+reportName+".jasper");
			JasperPrint jasperPrint = null;
			
			logger.info("In generateNotepadUploadErrorLog() : reportStream    :  "+reportStream);
			
			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
			
			logger.info("In generateNotepadUploadErrorLog(): jasperPrint    :  "+jasperPrint);
			
			methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
			dbType=null;
			reportName=null;
			reportPath=null;
			p_company_logo=null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			strFlag=null;
			userId=null;
			p_company_name=null;
			form.reset(mapping, request);
			ConnectionUploadDAO.closeConnection(connectDatabase, null);
			hashMap.clear();			
		}		
		return null;
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
	
}