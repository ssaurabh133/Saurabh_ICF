package com.masters.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;
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
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import com.connect.ConnectionReportDumpsDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;



public class UserFunctionAccessAction extends Action {

private static final Logger logger = Logger.getLogger(UserAccessAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{	
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
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
		
		DynaActionForm daf=(DynaActionForm)form;
		String p_printed_by="";
		String p_business_date="";
		String p_company_name="";
		{
			    p_printed_by=userobj.getUserName();
			    p_business_date=userobj.getBusinessdate();
				p_company_name= userobj.getConpanyName();
		}
		// ram ///////
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
		String dbType=resource.getString("lbl.dbType");		
		// ram ///////
		String reportformat=(String)daf.get("reportformat");
		String p_printed_date="";
		String 	reportName="User_Access_Details";
	    String userId=(String)daf.get("lbxUserId");
	    String user=(String)daf.get("userId");	    
	    int roleDescription=((Integer)daf.get("lbxRoleId")).intValue();
	    String moduleDesc=(String)daf.get("lbxModuleId");
	    String role="";
	    System.out.println("userId  :"+userId);
	    System.out.println("user  :"+user);
	    System.out.println("roleDescription  :"+roleDescription);
	    System.out.println("moduleDesc  :"+moduleDesc);
	    
//	    String user_Id="";
//	    if(userId.trim().length()==0)
//	    	userId="A";
//	    System.out.println("userId"  + userId);
//	    
//	    if(roleDescription==0)
//	    	role="A";
//	    if(moduleDesc.trim().length()==0)
//	    	moduleDesc="A";
//	    
	 			
	Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
	Map<Object,Object> hashMap = new HashMap<Object,Object>();
	
			 
	Calendar currentDate = Calendar.getInstance();
	SimpleDateFormat formatter= new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	p_printed_date = formatter.format(currentDate.getTime());

	String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports\\logo.bmp";
	hashMap.put("p_company_logo",p_company_logo);
	hashMap.put("p_company_name",p_company_name);	
 	hashMap.put("p_printed_by",p_printed_by);
	hashMap.put("p_user_id",userId);
	hashMap.put("p_module_id",moduleDesc);
	hashMap.put("p_role",role);
	hashMap.put("p_role_id",roleDescription);
	hashMap.put("p_report_format",reportformat);
	// ram ///////
	String reportPath="/reports/";	
	if(dbType.equalsIgnoreCase("MSSQL"))
		reportPath=reportPath+"MSSQLREPORTS/";
	else
		reportPath=reportPath+"MYSQLREPORTS/";
	// ram ///////
 	
		InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
		JasperPrint jasperPrint = null;
				
		try
		{
			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
			if(reportformat.equals("P"))
				methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
			if(reportformat.equals("E"))				
				methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
			if(reportformat.equals("H"))				
				methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
			hashMap.clear();
		}
		return null;
		  }
	
		 
	
	public void methodForPDF(Object reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint, HttpServletRequest request)throws Exception
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
	public void methodForExcel(Object reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint)throws Exception
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
	public void methodForHTML(Object reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint)throws Exception
	{
		String htmlReportFileName=reportName+".html";
		JRHtmlExporter exporter = new JRHtmlExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN , Boolean.FALSE);
		exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
		ServletContext context = this.getServlet().getServletContext();
		File reportFile = new File(context.getRealPath("/reports/"));
	    String image = reportFile.getPath();
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,image);
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,image + "/");
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
		response.setHeader("Content-Disposition", "attachment;filename=" + htmlReportFileName);
		response.setContentType("application/html");
		
			exporter.exportReport();
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
	


