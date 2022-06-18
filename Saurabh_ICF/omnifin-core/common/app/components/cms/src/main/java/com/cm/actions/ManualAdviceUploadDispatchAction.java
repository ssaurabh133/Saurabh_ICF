package com.cm.actions;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
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
import org.apache.struts.validator.DynaValidatorForm;

import com.cm.dao.ManualAdviceUploadDAO;
import com.cm.vo.ManualAdviceUploadVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ManualAdviceUploadDispatchAction extends DispatchAction {
	private static final Logger logger=Logger.getLogger(ManualAdviceUploadDispatchAction.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	
	public ActionForward uploadData(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.info(" in uploadData method of ManualAdviceUploadDispatchAction:::::::::::::::::::::::");

		
		HttpSession session = request.getSession();	
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("in  uploadData method of  ManualAdviceUploadDispatchAction action the session is out----------------");
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
		
		session.removeAttribute("Processfile");
				
		ManualAdviceUploadVo  manualAdviceUploadVo=new ManualAdviceUploadVo();
	    DynaValidatorForm manualDeviationDynaValidatorForm=(DynaValidatorForm)form;	    
	    org.apache.commons.beanutils.BeanUtils.copyProperties(manualAdviceUploadVo, manualDeviationDynaValidatorForm);
	    
		ManualAdviceUploadDAO service=(ManualAdviceUploadDAO)DaoImplInstanceFactory.getDaoImplInstance(ManualAdviceUploadDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		
	    
		String sms="";
		boolean uploadStatus=false;	
		
			uploadStatus=service.whereToUpload(request,manualAdviceUploadVo.getDocFile());			
			manualAdviceUploadVo.setFileName(request.getAttribute("fileName").toString());
			manualAdviceUploadVo.setDocPath(request.getAttribute("filePath").toString());
			String flag=(String)request.getAttribute("message");
			
			logger.info("In uploadData() : flag:==>"+flag);
			logger.info("In uploadData() : uploadStatus:==>>"+uploadStatus);
			if(uploadStatus)
			{
				int count=service.countLine(manualAdviceUploadVo.getDocFile().toString());
				logger.info("In uploadData() : count:==>>"+count+"manualAdviceUploadVo.getDocFile().toString():::::::"+manualAdviceUploadVo.getDocFile().toString());
				if(count>1000)
				{
					 request.setAttribute("maxCount","");	
					 logger.info("....In UpLoadDispatchAction..Total Line.."+count);
				}
				else
				{
				//for start process file name ..
				session.setAttribute("Processfile",manualAdviceUploadVo.getDocFile().toString());
				
				}
			}
			
			 if(flag=="O")
			 {
				    request.setAttribute("sms","");
				   
			 }
		    if(flag=="E")
		    {
				request.setAttribute("smsno","");
		    }
			    
		   
		return mapping.findForward("success");
	}
	public ActionForward startProcess(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.info(" in startProcess method of ManualAdviceUploadDispatchAction:::::::::::::::::::::::");

		HttpSession session = request.getSession();	
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("in  uploadData method of  ManualAdviceUploadDispatchAction action the session is out----------------");
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

				
		ManualAdviceUploadVo  manualAdviceUploadVo=new ManualAdviceUploadVo();
	    DynaValidatorForm manualDeviationDynaValidatorForm=(DynaValidatorForm)form;	    
	    org.apache.commons.beanutils.BeanUtils.copyProperties(manualAdviceUploadVo, manualDeviationDynaValidatorForm);
	    
		ManualAdviceUploadDAO service=(ManualAdviceUploadDAO)DaoImplInstanceFactory.getDaoImplInstance(ManualAdviceUploadDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		
		String file=(String)session.getAttribute("Processfile");
		logger.info("startProcess() : file:==>>"+file);

			//boolean status=service.readExcelforManualUpload(request, response,file);
		String functionId = (String)session.getAttribute("functionId");
		boolean status=false;
		if(CommonFunction.checkNull(functionId).equalsIgnoreCase("4000452")){
			String presentaionDate="";
			if(session.getAttribute("presentationDate")!=null){
				presentaionDate=session.getAttribute("presentationDate").toString();
			}
			 status=service.readExcelforGenerateBacthUpload(request, response,file,presentaionDate);
		}else{
			status=service.readExcelforManualUpload(request, response,file);
		}
			logger.info("vinod :startProcess():status:==>> "+status);
			if(status)
			{		
				
				request.setAttribute("inserted", "Done");
				
			}
			else
			{
				
				request.setAttribute("noinserted", "Done");
				
			}
		
		session.removeAttribute("Processfile");		
		
		return mapping.findForward("success");
	}
	public ActionForward errorLogDownload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		    logger.info("In errorLogDownload()..Action. ");
		    HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String user_id="";
			String p_company_name="";
			if(userobj!=null)
			{
				user_id=userobj.getUserId();
				p_company_name=userobj.getConpanyName();
			}else{
				logger.info("in  errorLogDownload method of ManualAdviceUploadDispatchAction action the session is out----------------");
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
			
			logger.info("In errorLogDownload(): strFlag :==>> "+strFlag);
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
			String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports\\logo.bmp";

			//logger.info("IN errorLogDownload() : p_company_logo :==>>"+p_company_logo);
		//	logger.info("IN errorLogDownload() : user_id :==>>"+user_id);
			//logger.info("IN errorLogDownload() : p_company_name :==>>"+p_company_name);
			Connection connectDatabase = ConnectionDAO.getConnection();		
			HashMap hashMap=new HashMap();
			hashMap.put("user_id",user_id);
			hashMap.put("p_company_name",p_company_name);
			hashMap.put("p_company_logo",p_company_logo);
			String functionId = (String)session.getAttribute("functionId");
			String reportName="rpt_manual_advice_upload";
			if(CommonFunction.checkNull(functionId).equalsIgnoreCase("4000452")){
			 reportName="rpt_generate_batch";  
			}
			//String reportName="rpt_manual_advice_upload";  
			//logger.info("IN errorLogDownload() : reportName :==>>"+reportName);
			//mradul starts
			String dbType=resource.getString("lbl.dbType");
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
					String excelReportFileName=reportName+".xls";
					logger.info("IN errorLogDownload() : excelReportFileName :==>>"+excelReportFileName);
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
				catch(Exception e)
				{
					e.printStackTrace();
				}	finally{
					connectDatabase.close();
				}
   
			
			return null;
		}
}
