package com.cm.actions;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;


import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;

import com.cm.actionform.ReportsForm;
import com.cm.dao.ReportsDAO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cm.actions.UploadDocuments;

public class BulkReportGenerationCM extends DispatchAction
{
	private static final Logger logger = Logger.getLogger(UploadDocuments.class.getName()); 
	static ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
	static String dbType=resource.getString("lbl.dbType");
	static String dbo=resource.getString("lbl.dbPrefix");
	
	static int count=0;
	
	public ActionForward reportGenerationMethod(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{	
		logger.info("In  reportGenerationMethod"); 
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info(" in reportGenerationMethod the session is out----------------");
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
	public ActionForward reportBulkGeneration(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{	
		logger.info("In  bulkReportGenerationCM"); 
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId=null;
		String businessDate=null;
		if(userobj==null){
			logger.info(" in reportGenerationMethod the session is out----------------");
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
		status=uploadFile(request,response,(FormFile)DynaForm.get("docFile"),userId,businessDate);
		logger.info("Uploaded and processing status  :  "+status);
		if(CommonFunction.checkNull(status).trim().equalsIgnoreCase(""))
		{	logger.info("when the value is true..");		
			request.setAttribute("uploaded","uploaded");	
		}
		else
		{
			String uploadError=null;
			if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("MXE"))
				uploadError=resource.getString("lbl.maxCount");//MXE
			else if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("MNE"))
				uploadError=resource.getString("lbl.smsk");//MXE
			else if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("FNE"))
				uploadError=resource.getString("lbl.smks");//FNE
			else if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("NUE"))
				uploadError=resource.getString("lbl.numericError");//NUE
			else if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("CLE"))
				uploadError=resource.getString("lbl.formatInvalid");//CLE
			else if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("PRE"))
				uploadError=resource.getString("lbl.errorHeader");//PRE
			else if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("UPE"))
				uploadError=resource.getString("msg.notsuccess");//UPE
			else
			{
				if(CommonFunction.checkNull(status).trim().length()>4)
					uploadError=status.substring(4);
			}				
			request.setAttribute("uploadError",uploadError);
		}
		strFlag=null;
		status=null;
		userId=null;
		businessDate=null;
		form.reset(mapping, request);
		return mapping.findForward("success");
	}	
	String uploadFile(HttpServletRequest request,HttpServletResponse response,FormFile myFile,String makerName,String makerDate)
	{		
		logger.info("Inside uploadFile");
		String result=null;
		boolean uploadStatus=false;	
		try
		{
			uploadStatus=ReportUploadDocuments.docUpload(request,myFile);			
			if(uploadStatus)
				result=ReportUploadDocuments.readExcelBulkReport(request,response,myFile.toString(),makerName,makerDate);
			else
				result="UPE";
		}
		catch (Exception e) 
		{
			result="PRE";
			e.getMessage();
			}
		makerName=null;
		makerDate=null;
		return result;
	}
	public ActionForward generateErrorLog(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("In generateErrorLog() of bulkReportGenerationCMAction");
	  	
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
		ReportsDAO dao=(ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		
		 boolean reportFlag=dao.checkReportGeneration();
		  logger.info("the report has not been  generated :::: "+reportFlag);
		  if(reportFlag)
		  {
		Connection connectDatabase = ConnectionDAO.getConnection();		
		Map<Object,Object> hashMap = new HashMap<Object,Object>();
		try
		{
			ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
			String dbType=resource.getString("lbl.dbType");
			String reportPath="/reports/";
			String reportName="bulkReportGenerationErrorLog";//bulkReportGenerationErrorLog
			
			if(dbType.equalsIgnoreCase("MSSQL"))
				reportPath=reportPath+"MSSQLREPORTS/";
			else
				reportPath=reportPath+"MYSQLREPORTS/";
			
			
			String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
						
			hashMap.put("p_company_name",p_company_name );
			hashMap.put("p_company_logo",p_company_logo );
			hashMap.put("userId",userId );
			
			logger.info("In generateErrorLog() of bulkReportGenerationCM  p_company_name  :  "+p_company_name);
			logger.info("In generateErrorLog() of bulkReportGenerationCM  p_company_logo  :  "+p_company_logo);
			logger.info("In generateErrorLog() of bulkReportGenerationCM  userId          :  "+userId);
			logger.info("In generateErrorLog() of bulkReportGenerationCM  Report Name     :  "+reportPath+reportName+".jasper");
			
			InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+reportName+".jasper");
			JasperPrint jasperPrint = null;
			
			logger.info("In generateErrorLog() : reportStream    :  "+reportStream);
			
			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
			
			logger.info("In generateErrorLog(): jasperPrint    :  "+jasperPrint);
			
			methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
			dbType=null;
			reportName=null;
			reportPath=null;
			p_company_logo=null;
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		finally
		{
			strFlag=null;
			userId=null;
			p_company_name=null;
			form.reset(mapping, request);
			ConnectionDAO.closeConnection(connectDatabase, null);
			hashMap.clear();			
		}	
		  }
		  else
			  request.setAttribute("errorFlag","errorFlag");
		  
		  return mapping.findForward("success");
	}
	
	public ActionForward generateReportDesc(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
        logger.info("the method is in generateReportDesc");
		HttpSession session =  request.getSession();		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		String p_company_name ="";
		int compid =0;
		String p_printed_by="";
		if(userobj!=null)
		{
			userId = userobj.getUserId();
			businessDate=userobj.getBusinessdate();
			compid=userobj.getCompanyId();
			p_company_name=userobj.getConpanyName();
			p_printed_by=userobj.getUserName();
			logger.info("company id is :: "+compid);
		}
		else{
			return mapping.findForward("sessionOut");
		}
		
		Object sessionId = session.getAttribute("sessionID");
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
		ReportsDAO dao=(ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
	
	      boolean reportFlag=dao.checkReportGeneration();
		  logger.info("the report has not been  generated :::: "+reportFlag);
		  if(reportFlag)
		  {
		
		boolean updateReportFlag=dao.updateReportGenerationFlag();
		  logger.info("report generate flag has been updated"+updateReportFlag); 
		
		
		
		String loanDate="";
		String disbursalId="";
		String fromDisbursalDate="";
		String toDisbursalDate="";
		String productCategory="";
		String source="B";
		Map<Object,Object> hashMap = new HashMap<Object,Object>();		
		String SUBREPORT_DIR=getServlet().getServletContext().getRealPath("/")+"reports\\";		
		String reportPath="/reports/";
		if(dbType.equalsIgnoreCase("MSSQL"))
		{
			SUBREPORT_DIR=SUBREPORT_DIR+"MSSQLREPORTS\\";
			reportPath=reportPath+"MSSQLREPORTS/";
		}
		else
		{
			SUBREPORT_DIR=SUBREPORT_DIR+"MYSQLREPORTS\\";
			reportPath=reportPath+"MYSQLREPORTS/";	
		}
		String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
		String p_indian_rupee_logo=getServlet().getServletContext().getRealPath("/")+"reports/rupee.bmp";
		hashMap.put("p_company_logo",p_company_logo);	
		hashMap.put("p_indian_rupee_logo",p_indian_rupee_logo);
		hashMap.put("SUBREPORT_DIR",SUBREPORT_DIR);	
		String status=null;
		try
		{
			String[] fileArray = new String[3];
			String reportName="Bulk_Welcome_Letter_Report";
			String qry=dao.getWelcomeReportQry(loanDate,disbursalId,fromDisbursalDate,toDisbursalDate,productCategory,source);		          
			logger.info("the query which is required to run for Welcome letter is:::::"+qry);
			hashMap.put("qry",qry);
			status=	generateReport(request,response,qry,hashMap,reportPath,reportName,businessDate,userId);	
			fileArray[0]="Bulk_Welcome_Letter_Report";
			ArrayList<ReportsForm> list=dao.getCompanyAddress(1);
			ReportsForm frm = new ReportsForm();
			frm=list.get(0);
			String p_address1=frm.getAddress1()+" "+frm.getCity()+" "+frm.getPincode()+" "+frm.getPhone()+" "+frm.getFax();
			String p_email=frm.getEmail()+" "+frm.getWebsite();
			String p_email1=frm.getEmail();
			
			hashMap.put("p_company_name",p_company_name);
			hashMap.put("p_printed_date",businessDate);
			hashMap.put("p_address1",p_address1);
			hashMap.put("p_email",p_email);
			hashMap.put("p_email1",p_email1);
			hashMap.put("p_user_id",userId);
			hashMap.put("p_effective_date",CommonFunction.changeFormat(businessDate));
			hashMap.put("p_printed_by",p_printed_by+" ");
			
			reportName="Bulk_ForeClosure_Report";
		    String fcQry=dao.getForeClosureReportQry();
	        logger.info("the query which is required to run is:::::"+fcQry);
	        ArrayList getForClosureLoanId=dao.getForClosureLoanId();
	        logger.info("getForClosureLoanId  :: "+getForClosureLoanId);
	        int size=getForClosureLoanId.size();
	        logger.info("size  :: "+size);
	        for(int i=0;i<size;i++)
			{
	        ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();       
			ArrayList outMessages = new ArrayList();
			String s1="";
			String s2="";
			in.add(compid);
			String loanId = (getForClosureLoanId.get(i)).toString();
			
			//int i = ((Integer) obj).intValue();
		//	int loanId=(Integer) getForClosureLoanId.get(i) ;
			in.add(loanId);
			logger.info("getForClosureLoanId.get(i)  :: "+getForClosureLoanId.get(i));
			in.add(CommonFunction.changeFormat(businessDate));
			in.add(userId);
			in.add(CommonFunction.checkNull(reportName).trim());
			out.add(s1);
			out.add(s2);  
			try
			{
				logger.info("Early_Closure_Detail_Report ("+in.toString()+","+out.toString());
				outMessages=(ArrayList) ConnectionReportDumpsDAO.callSP("Early_Closure_Detail_Report",in,out);
				s1=CommonFunction.checkNull(outMessages.get(0));
				s2=CommonFunction.checkNull(outMessages.get(1));
		        logger.info("s1  : "+s1);
		        logger.info("s2  : "+s2);
			}
			catch (Exception e) 
			{
				logger.info("s2.........  : "+e);
				e.printStackTrace();
			}	
			}
			 hashMap.put("fcQry",fcQry);
			status=generateReport(request,response,qry,hashMap,reportPath,reportName,businessDate,userId);
			fileArray[1]="Bulk_ForeClosure_Report";
			boolean deleteTerminationDtl = dao.deleteTerminationDtl(userId);
			reportName="Bulk_Welcome_Letter_guarantor_Report";
			String wlGuQry=dao.getWlGuaranterReportQry();
	        logger.info("the query which is required to run is:::::"+wlGuQry);
			hashMap.put("wlGuQry",wlGuQry);
			status=generateReport(request,response,wlGuQry,hashMap,reportPath,reportName,businessDate,userId);
			fileArray[2]="Bulk_Welcome_Letter_guarantor_Report";
			methodForZip(fileArray, response,  request, businessDate, userId);
			//multiFile(fileArray, response,  request, businessDate, userId);
		}
		catch (Exception e)
		{
			logger.info("s2.........  : "+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			hashMap.clear();
		}	
		 boolean updateFlag=dao.checkUpdateFlag();
		if(updateFlag)
		{	logger.info("when report is generated ..");		
			request.setAttribute("generate","generate");	
		}
		else
			request.setAttribute("generateError","generateError");
		  }
		  else
			  request.setAttribute("generateFlag","generateFlag");
		return null/*mapping.findForward("success")*/;
	}
	
	String generateReport(HttpServletRequest request,HttpServletResponse response,String qry,Map<Object,Object> hashMap,String reportPath,String reportName,String businessDate,String userId) throws Exception
	{
		Connection connectDatabase = ConnectionDAO.getConnection();			
		logger.info("report Name  :  "+ reportName + ".jasper");				
	    InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
	    JasperPrint jasperPrint = null;
	    String result=null;
		try
		{
			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
			methodForPDF(reportName,qry,hashMap,connectDatabase,response, jasperPrint,request,businessDate,userId);
			result="a";
		}
		catch(Exception e)
		{
			e.getMessage();
			e.printStackTrace();
		}
		finally
		{
			ConnectionDAO.closeConnection(connectDatabase, null);							
		}
		return  result;
	}
	public void methodForPDF(String reportName,String qry,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint, HttpServletRequest request,String businessDate,String userId)throws Exception
	{
			String path="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'EOD_REPORT_PATH'";
			String reportPath=ConnectionDAO.singleReturn(path);
			JasperExportManager.exportReportToPdfFile(jasperPrint,reportPath + "/" +reportName+"_"+userId+"_"+businessDate+".pdf");
	}
	public void methodForZip(String[] sourceFiles,HttpServletResponse response, HttpServletRequest request,String businessDate,String userId)throws Exception
	{
	{
		 try
         {
			 String path="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'EOD_REPORT_PATH'";
				String reportPath=ConnectionDAO.singleReturn(path);
				String zipFile = "Bulk_Report_"+userId+"_"+businessDate+".zip";
                 byte[] buffer = new byte[1024];
                  
                  response.setContentType("application/zip");
                  response.setHeader("Content-Disposition", "attachment; filename="+zipFile);

                  FileOutputStream fos = new FileOutputStream(reportPath +zipFile);
                  ZipOutputStream zos = new ZipOutputStream(fos);
                  for (String fileName : sourceFiles) {
                	  File repFile = new File(reportPath+"\\"+fileName+"_"+userId+"_"+businessDate+".pdf");
                      addToZipFile(repFile, reportPath+"\\"+fileName+"_"+userId+"_"+businessDate+".pdf", zos);
                  }
                  zos.close();
                  fos.close();
                  File zipFileName = new File(reportPath+"\\"+zipFile);
                  FileInputStream fileIn = new FileInputStream(zipFileName);
                  ServletOutputStream out = response.getOutputStream();

                  byte[] outputByte = new byte[1024];

                  //copy binary contect to output stream
                  while (fileIn.read(outputByte, 0, 1024) != -1) {
                      out.write(outputByte, 0, 1024);
                  }
                  
                  fileIn.close();
                  out.flush();
                  out.close();
                  logger.info("Delete Zip File Status  "+new File(reportPath+"\\"+zipFile).delete());
         }catch (Exception e) {
			e.printStackTrace();
		}
                     
	}
 }
	
	 public static void addToZipFile(File file, String path, ZipOutputStream zos)
		        throws FileNotFoundException, IOException {
		        System.out.println("Writing '" + path + "' to zip file");

		        //        File file = new File(fileName);
		        FileInputStream fis = new FileInputStream(file);
		        ZipEntry zipEntry = new ZipEntry(file.getName() );
		        zos.putNextEntry(zipEntry);

		        byte[] bytes = new byte[1024];
		        int length;

		        while ((length = fis.read(bytes)) >= 0) {
		            zos.write(bytes, 0, length);
		        }
		       
		        zos.closeEntry();
		        fis.close();
		        logger.info("Delete pdf File Status  "+new File(path).delete());
		        
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