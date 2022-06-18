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

import com.connect.ConnectionUploadDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cm.dao.UploadDocumentDAO;
import com.cm.vo.uploadVO;

public class UploadReceiptProcessAction extends DispatchAction
{
	private static final Logger logger = Logger.getLogger(UploadReceiptProcessAction.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
	public ActionForward uploadData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		boolean flag1=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("in  uploadData method of  UploadReceiptProcessAction action the session is out----------------");
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
		
		session.removeAttribute("Processfile");
		logger.info("Inside Processing Action of Doc Upload");
		uploadVO uwDocVo = new uploadVO();
	    DynaValidatorForm UploadDynaValidatorForm=(DynaValidatorForm)form;	    
	//	logger.info("Copying form data to Vo");
	    org.apache.commons.beanutils.BeanUtils.copyProperties(uwDocVo, UploadDynaValidatorForm);
//	    CreditManagementDAO service = new CreditManagementDAOImpl();
	//	String sms=null;
		boolean uploadStatus=false;	
			uploadStatus=UploadDocuments.docUpload(request,uwDocVo.getDocFile());			
			uwDocVo.setFileName(request.getAttribute("fileName").toString());
			uwDocVo.setDocPath(request.getAttribute("filePath").toString());
			String flag=(String)request.getAttribute("message");
			if(uploadStatus)
			{
				int count=UploadDocuments.countLine(uwDocVo.getDocFile().toString());
				if(count>1000)
				{
					 request.setAttribute("maxCount","");	
					 logger.info("....In UploadReceiptProcessAction..Total Line.."+count);
				}
				else
				{
					//for start process file name ..
				session.setAttribute("Processfile",uwDocVo.getDocFile().toString());
				logger.info("....In UploadReceiptProcessAction..Total Line.."+count);
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
	    flag = null;
		uwDocVo = null;
		strFlag = null;    
		
		return mapping.findForward("success");
	}
	
	public ActionForward startProcessReceipt(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception 
{		
		logger.info("In startProcessReceipt():");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String branchId=null;
		if(userobj==null)
		{
			logger.info("in  startProcessReceipt method of  UploadReceiptProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}else{
			branchId = userobj.getBranchId();
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null && userobj != null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		
		logger.info("In startProcessReceipt(): strFlag ==>> "+strFlag);
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
		
		String file=(String)session.getAttribute("Processfile");
		logger.info("In startProcessReceipt(): file ==>> "+file);

		int count=UploadDocuments.countLine(file);
		logger.info("In startProcessReceipt(): count:==>>"+count);
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
			boolean status=UploadDocuments.readExcelforReciptUpload(request, response,file,branchId);
			logger.info("In startProcessReceipt(): status :==>> "+status);
			if(status)
			{		
				//logger.info("In startProcessReceipt():if condition");
				request.setAttribute("inserted", "Done");
				
			}
			else
			{
				//logger.info("In startProcessReceipt():else condition");
				request.setAttribute("noinserted", "Done");
				
			}
		}
		file = null;
		strFlag = null;
		
		session.removeAttribute("Processfile");
		return mapping.findForward("success");
}
	public ActionForward uploadsummary(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		    logger.info("In uploadsummary()... ");
		    HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String userId=null;
			if(userobj!=null)
			{
				userId=userobj.getUserId();
			}else{
				logger.info("here in uploadsummary UploadReceiptProcessAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
			//for check User session start
			ServletContext context = getServlet().getServletContext();
			String strFlag=null;	
			if(sessionId!=null)
			{
				strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			}
			
			logger.info("strFlag .............. "+strFlag);
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
			
			//change by sachin
			UploadDocumentDAO service=(UploadDocumentDAO)DaoImplInstanceFactory.getDaoImplInstance(UploadDocumentDAO.IDENTITY);
		     logger.info("Implementation class: "+service.getClass());

			//end by sachin
//			CreditManagementDAO service=new CreditManagementDAOImpl();
	
		   				
		ArrayList list =service.getUploadSummary(userId,request);
			if(list.size()>=1)
			{
			request.setAttribute("uploadSummary", list);
			logger.info("In uploadsummary()...List"+list)		;
			}
			else
			{
				request.setAttribute("nodata", "");
			}
			service=null;
			userId=null;
			strFlag=null;
			
			return mapping.findForward("uploadsummary");
		}
	public ActionForward errorLogDownloadReceipt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		    logger.info("In errorLogDownloadReceipt()..Action. ");
		    HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String user_id=null;
			String p_company_name=null;
			if(userobj!=null)
			{
				user_id=userobj.getUserId();
				p_company_name=userobj.getConpanyName();
			}else{
				logger.info("here errorLogDownloadReceipt method of UploadReceiptProcessAction  action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
			//for check User session start
			ServletContext context = getServlet().getServletContext();
			String strFlag=null;	
			if(sessionId!=null)
			{
				strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			}
			
			logger.info("In errorLogDownloadReceipt(): strFlag :==>> "+strFlag);
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
			String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports\\logo.bmp";

			//logger.info("In errorLogDownloadReceipt() :  user_id"+user_id);
			//logger.info("In errorLogDownloadReceipt() :  p_company_name"+p_company_name);
			Connection connectDatabase = ConnectionUploadDAO.getConnection();		
			HashMap hashMap=new HashMap();
			hashMap.put("user_id",user_id);
			hashMap.put("p_company_name",p_company_name);
			hashMap.put("p_company_logo",p_company_logo);
			String reportName="Receipt Upload"; 
			//mradul starts
			String dbType=resource.getString("lbl.dbType");
			String reportPath="/reports/";
			
			if(dbType.equalsIgnoreCase("MSSQL"))
				reportPath=reportPath+"MSSQLREPORTS/";
			else
				reportPath=reportPath+"MYSQLREPORTS/";
			
			logger.info("In errorLogDownloadReceipt() :  reportName"+reportName);
				InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
				JasperPrint jasperPrint = null;
				//mradul ends		
				try
				{
					jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
					String excelReportFileName=reportName+".xls";
					//logger.info("In errorLogDownloadReceipt() :  excelReportFileName"+excelReportFileName);
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
				}finally{
					reportName=null;
					dbType=null;
					reportPath=null;
					user_id=null;
					p_company_name=null;
					p_company_logo=null;
					strFlag=null;
		
					connectDatabase.close();
				}
//			CreditManagementDAO service=new CreditManagementDAOImpl();
//		    String userId=userobj.getUserId();		    
//		    boolean status=service.downLoadErrorLogReceipt(request,response,userId);
//		    logger.info("In errorLogDownload()...Action. status is.."+status);	 
				
			return null;
		}
    
	
}