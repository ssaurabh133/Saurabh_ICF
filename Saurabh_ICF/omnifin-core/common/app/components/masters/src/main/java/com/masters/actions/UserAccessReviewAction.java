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
import com.connect.ConnectionDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.connect.CommonFunction;
import java.util.*;



public class UserAccessReviewAction extends Action {

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
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		String dbType=resource.getString("lbl.dbType");		
		// ram ///////
		String reportformat=(String)daf.get("reportformat");
		String p_printed_date="";
		String 	reportName="";
	    String p_user_Type=(String)daf.get("userType");
	    
	    if (CommonFunction.checkNull(p_user_Type).equalsIgnoreCase("A")){ 
	    	reportName="userAccessReview";
	    }
	    else 
	    {
	    	reportName="userAccessReviewDeativate";	
	    }
	    String from_date=(String)daf.get("fromDate");
	    String to_date=(String)daf.get("toDate");
	    String from_d_date=(String)daf.get("UserDeactivatedStartDate");
	    String to_d_date=(String)daf.get("UserDeactivatedEndDate");
	    String p_from_date = CommonFunction.changeFormat(from_date);
	    String p_to_date = CommonFunction.changeFormat(to_date);
	    String p_from_d_date = CommonFunction.changeFormat(from_d_date);
	    String p_to_d_date = CommonFunction.changeFormat(to_d_date);
	    
	   // int roleDescription=((Integer)daf.get("lbxRoleId")).intValue();
	   // String moduleDesc=(String)daf.get("lbxModuleId");
	    String role="";
	    System.out.println("p_user_Type  :"+p_user_Type);
	    System.out.println("p_from_date  :"+p_from_date);
	    System.out.println("p_to_date  :"+p_to_date);
	    System.out.println("p_from_d_date  :"+p_from_d_date);
	    System.out.println("p_to_d_date  :"+p_to_d_date);
	    
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
	 			
	Connection connectDatabase = ConnectionDAO.getConnection();		
	Map<Object,Object> hashMap = new HashMap<Object,Object>();
	
			 
	Calendar currentDate = Calendar.getInstance();
	SimpleDateFormat formatter= new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	p_printed_date = formatter.format(currentDate.getTime());

	String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports\\logo.bmp";
	hashMap.put("p_company_logo",p_company_logo);
	hashMap.put("p_company_name",p_company_name);	
 	hashMap.put("p_printed_by",p_printed_by);
	hashMap.put("p_user_Type",p_user_Type);
	hashMap.put("p_from_date",p_from_date);
	//hashMap.put("p_role",role);
	hashMap.put("p_to_date",p_to_date);
	hashMap.put("p_from_d_date",p_from_d_date);
	hashMap.put("p_to_d_date",p_to_d_date);
	hashMap.put("p_report_format",reportformat);
	// ram ///////
	String[] loanArr = null;
	if (CommonFunction.checkNull(reportName).equalsIgnoreCase("userAccessReview")){
     loanArr = getLoan(p_user_Type, p_from_date, p_to_date);
	}
	else  
	{
		 loanArr = getLoanAccessReviewDeativate(p_user_Type, p_from_date, p_to_date, p_from_d_date, p_to_d_date);	
	}
	
    
    
		if(loanArr!= null){
		
		
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
			//ConnectionDAO.closeConnection(connectDatabase, null);
			hashMap.clear();
			}
		}
		else
		{
		    logger.info("Else Part  loanArr :: "+loanArr);	
            hashMap.clear();
            request.setAttribute("error", "No Record found!!!");
            return mapping.findForward("error");
		
  	}
	return null;
	}
	
	public String[] getLoanAccessReviewDeativate(String userType,String fromDate,String toDate,String fromDDate,String toDDate){
		
    	ArrayList loanList = new ArrayList();
		StringBuffer query = new StringBuffer();
		String[] loanArr=null;

		try{
			
			query.append("select ifnull(a.USER_NAME,'')as user_name from sec_user_m a "
					+ " left  join com_department_m on (a.USER_DEPARTMENT=com_department_m.DEPARTMENT_ID) "
					+ " left join com_branch_m on (a.USER_DEF_BRANCH=com_branch_m.BRANCH_ID) "
					+ " where  a.rec_status='X' and "
					+ " IF('"+fromDate+"'='',1=1,date_format(date(a.USER_CREATION_DATE),'%Y-%m-%d')>='"+fromDate+"' AND date_format(date(a.USER_CREATION_DATE),'%Y-%m-%d')<='"+toDate+"' )  and "
					+ " IF('"+fromDDate+"'='',1=1,date_format(date(a.USER_DEACTIVATION_DATE),'%Y-%m-%d')>='"+fromDDate+"'  AND date_format(date(a.USER_DEACTIVATION_DATE),'%Y-%m-%d')<='"+toDDate+"') ");
		
		logger.info(" getLoanAccessReviewDeativate query....... "+query);
		loanList = ConnectionDAO.sqlSelect(query.toString());
	    logger.info("loanList  :: "+loanList);
	    int size= loanList.size();
	    logger.info("size of loanList  :: "+size);
	    if(size>0)
	    {
		    loanArr = new String[size];
		    for (int i = 0; i < size; i++) 
		    {
				ArrayList data = (ArrayList) loanList.get(i);
				if (data.size()>0) 
				{						
					loanArr[i]=(String) data.get(0);
				}
		    }
	    }
    	
		}
	    catch (Exception e) 
		{
			e.printStackTrace();
		}
		return loanArr;
	}

    public String[] getLoan(String userType,String fromDate,String toDate){
	
    	ArrayList loanList = new ArrayList();
		StringBuffer query = new StringBuffer();
		String[] loanArr=null;
		
		try{
			
			query.append("select ifnull(a.USER_NAME,'')as user_name from sec_user_m a "
					+ "left  join com_department_m on (a.USER_DEPARTMENT=com_department_m.DEPARTMENT_ID) "
					+ "left join com_branch_m on (a.USER_DEF_BRANCH=com_branch_m.BRANCH_ID)  "
					+ "where date_format(date(a.USER_CREATION_DATE),'%Y-%m-%d')>='"+fromDate+"' AND date_format(date(a.USER_CREATION_DATE),'%Y-%m-%d')<='"+toDate+"' and a.rec_status='A' ");

		
		logger.info(" getLoan  query....... "+query);
		loanList = ConnectionDAO.sqlSelect(query.toString());
	    logger.info("loanList  :: "+loanList);
	    int size= loanList.size();
	    logger.info("size of loanList  :: "+size);
	    if(size>0)
	    {
		    loanArr = new String[size];
		    for (int i = 0; i < size; i++) 
		    {
				ArrayList data = (ArrayList) loanList.get(i);
				if (data.size()>0) 
				{						
					loanArr[i]=(String) data.get(0);
				}
		    }
	    }
    	
		}
	    catch (Exception e) 
		{
			e.printStackTrace();
		}
		return loanArr;
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
	


