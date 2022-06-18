package com.cm.actions;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
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

import com.connect.CommonFunction;
import com.connect.ConnectionUploadDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cm.dao.UploadDocumentDAO;
import com.cm.vo.uploadVO;

public class UploadDispatchAction extends DispatchAction
{
	private static final Logger logger = Logger.getLogger(UploadDispatchAction.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
	public ActionForward uploadData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();

		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("in  uploadData method of  UploadDispatchAction action the session is out----------------");
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
		
		logger.info("In uploadData() : strFlag:==>> "+strFlag);
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
		
		uploadVO uwDocVo = new uploadVO();
	    DynaValidatorForm UploadDynaValidatorForm=(DynaValidatorForm)form;	    
		//logger.info("In uploadData() : Copying form data to Vo");
	    org.apache.commons.beanutils.BeanUtils.copyProperties(uwDocVo, UploadDynaValidatorForm);
		//change by sachin
	    UploadDocumentDAO service=(UploadDocumentDAO)DaoImplInstanceFactory.getDaoImplInstance(UploadDocumentDAO.IDENTITY);
	     logger.info("Implementation class: "+service.getClass());

		//end by sachin
//	    CreditManagementDAO service = new CreditManagementDAOImpl();

		boolean uploadStatus=false;	
			uploadStatus=UploadDocuments.docUpload(request,uwDocVo.getDocFile());			
			uwDocVo.setFileName(request.getAttribute("fileName").toString());
			uwDocVo.setDocPath(request.getAttribute("filePath").toString());
			String flag=(String)request.getAttribute("message");
			
			//logger.info("In uploadData() : flag:==>"+flag);
			//logger.info("In uploadData() : uploadStatus:==>>"+uploadStatus);
			if(uploadStatus)
			{
				int count=UploadDocuments.countLine(uwDocVo.getDocFile().toString());
				//logger.info("In uploadData() : count:==>>"+count);
				if(count>1000)
				{
					 request.setAttribute("maxCount","");	
					 //logger.info("....In UpLoadDispatchAction..Total Line.."+count);
				}
				else
				{
					//for start process file name ..
				session.setAttribute("Processfile",uwDocVo.getDocFile().toString());
				
				}
			}
			
			 if(CommonFunction.checkNull(flag).equalsIgnoreCase("O"))
			 {
				    request.setAttribute("sms","");
				   
			 }
		    if(CommonFunction.checkNull(flag).equalsIgnoreCase("E"))
		    {
				request.setAttribute("smsno","");
		    }
			    

		 flag=null;
		 strFlag=null;
		 uwDocVo=null;
		 form.reset(mapping, request);
		return mapping.findForward("success");
	}
	
	public ActionForward startProcess(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		 logger.info("In startProcess():");
		HttpSession session = request.getSession();

		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("in  startProcess method of  UploadDispatchAction action the session is out----------------");
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
		
		logger.info("vinod:startProcess():strFlag ==>> "+strFlag);
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
		
		String file=(String)session.getAttribute("Processfile");
	//	logger.info("startProcess() : file:==>>"+file);
//		if(file.equals("")||file==null)
//		{
//		request.setAttribute("nofile", "");
//		}
		int count=UploadDocuments.countLine(file);
		//logger.info("vinod :In startProcess(): count"+count);
		if(count>1000)
		{
			 request.setAttribute("maxCount","");	
			 
		}
		
		if(count==0)
		{
			 request.setAttribute("zeroCount","");	
			
		}
		
		else
		{
			//boolean status=UploadDocuments.csvRead(request, response,file);//readExcelforBounceUpload
			boolean status=UploadDocuments.readExcelforBounceUpload(request, response,file);
		//	logger.info("vinod :startProcess():status:==>> "+status);
			if(status)
			{		
				
				request.setAttribute("inserted", "Done");
				
			}
			else
			{
				
				request.setAttribute("noinserted", "Done");
				
			}
		}
		session.removeAttribute("Processfile");
		file=null;
		strFlag=null;
		form.reset(mapping, request);
		return mapping.findForward("startProcess");
	}
	public ActionForward uploadsummary(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		    logger.info("In uploadsummary()... ");
		    HttpSession session = request.getSession();
			
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String userId="";
			if(userobj!=null){
				userId= userobj.getUserId();
			}else{
				logger.info("in  uploadsummary method of UploadDispatchAction action the session is out----------------");
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
			
			//change by sachin
			UploadDocumentDAO service=(UploadDocumentDAO)DaoImplInstanceFactory.getDaoImplInstance(UploadDocumentDAO.IDENTITY);
		     logger.info("Implementation class: "+service.getClass());

			//end by sachin
//			CreditManagementDAO service=new CreditManagementDAOImpl();
				
			ArrayList list =service.getUploadSummary(userId,request);
			if(list.size()>=1)
			{
			request.setAttribute("uploadSummary", list);
			logger.info("In uploadsummary()...List"+list);
			}
			else
			{
				request.setAttribute("nodata", "");
			}
			
			userId=null;
			strFlag=null;
			service=null;
			list.clear();
			list = null;
			form.reset(mapping, request);
			return mapping.findForward("uploadsummary");
		}
	public ActionForward errorLogDownload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		    logger.info("In errorLogDownload()..Action. ");
		    HttpSession session = request.getSession();
		
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String user_id="";
			String p_company_name="";
			if(userobj!=null)
			{
				user_id=userobj.getUserId();
				p_company_name=userobj.getConpanyName();
			}else{
				logger.info("in  errorLogDownload method of UploadDispatchAction action the session is out----------------");
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
			
			//logger.info("In errorLogDownload(): strFlag :==>> "+strFlag);
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
	


			//logger.info("IN errorLogDownload() : user_id :==>>"+user_id);
			//logger.info("IN errorLogDownload() : p_company_name :==>>"+p_company_name);
			Connection connectDatabase = ConnectionUploadDAO.getConnection();		
			HashMap hashMap=new HashMap();
			hashMap.put("user_id",user_id);
			hashMap.put("p_company_name",p_company_name);
			hashMap.put("p_company_logo",p_company_logo);
			String reportName="Cheque Bounce Realization Upload Process";  
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
					//logger.info("IN errorLogDownload() : no problem");
					//jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
					jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
					//logger.info("IN errorLogDownload() : there is  problem");
					String excelReportFileName=reportName+".xls";
					//logger.info("IN errorLogDownload() : excelReportFileName :==>>"+excelReportFileName);
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
					user_id=null;
					p_company_name=null;
					strFlag=null;
					dbType=null;
					reportPath=null;
					form.reset(mapping, request);
					connectDatabase.close();
				}
//			CreditManagementDAO service=new CreditManagementDAOImpl();
//		    String userId=userobj.getUserId();		    
//		    boolean status=service.downLoadErrorLog(request,response,userId);
//		    logger.info("In errorLogDownload()...Action. status is.."+status);	   
			
			return null;
		}
    
	
}