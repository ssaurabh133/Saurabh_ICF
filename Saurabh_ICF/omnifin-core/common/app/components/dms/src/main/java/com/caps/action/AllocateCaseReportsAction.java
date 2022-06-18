package com.caps.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.DecimalFormat;
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

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.login.roleManager.UserObject;
import com.caps.actionForm.CasesReportForm;


public class AllocateCaseReportsAction extends Action 
{	
	private static final Logger logger = Logger.getLogger(CaseReportsAction.class.getName());
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{	
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String username="";
		String cname="";
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
			username=userobj.getUserName();
			cname=userobj.getConpanyName();
			userId = userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here execute method of  AllocateCaseReportsAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		String dbo=resource.getString("lbl.dbPrefix");
		CasesReportForm af=(CasesReportForm) form;	
		String loanNo=af.getLbxLoanno();
		String reporttype=af.getReportformat();
		String customerName=af.getCustomername();
		String product=af.getLbxProductID();
		String user=af.getLbxUserSearchId();

		String customertype=af.getCustype();
		String customercategory=af.getCustcategory();		
		String balanceprincipal=af.getBalanceprincipal();
		String queue=af.getLbxQueuesearchId();
		String fromdate=af.getFromdate();		
		String todate=af.getTodate();
		String dpd1=af.getDpd1();
		String dpd2=af.getDpd2();
		String pos1="";
		String pos2="";
		String productCategory=af.getProducTCategory();
		String loanClass=af.getLoanClassification();
		String p_branch_type=af.getBranch();
		String p_branch_id=af.getLbxBranchId();

		String  firstDate="";
			if(!CommonFunction.checkNull(bDate).trim().equalsIgnoreCase(""))
			{
				firstDate="01"+bDate.substring(2);
				logger.info("firstDate"+firstDate);
				
			}
		if(!CommonFunction.checkNull(p_branch_id).trim().equalsIgnoreCase(""))
		{
			logger.info("p_branch_id::"+p_branch_id);
			p_branch_id=""+p_branch_id.replace("|",",");
			logger.info("p_branch_id (new):::"+p_branch_id);
			}
		else
		{
			p_branch_id="''";
		}
		if(!af.getPos1().equalsIgnoreCase("")){
			pos1=CommonFunction.decimalNumberConvert(StringEscapeUtils.escapeSql(af.getPos1())).toString();
		}else{
			pos1=af.getPos1();
		}
		if(!af.getPos2().equalsIgnoreCase("")){
			pos2=CommonFunction.decimalNumberConvert(StringEscapeUtils.escapeSql(af.getPos2())).toString();
		}else{
			pos2=af.getPos2();
		}
		String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
		StringBuffer bufInsSql = new StringBuffer();	 
		String dateFormat = resource.getString("lbl.dateInDao");
		String dateFormatStr = resource.getString("lbl.dateForDisbursal");
		String dbType=resource.getString("lbl.dbType");
		Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
		

		Date todaydate= new Date();
		SimpleDateFormat formate=new SimpleDateFormat(dateFormatStr);
		String formatedDate=formate.format(todaydate);

		if (dbType.equalsIgnoreCase("MSSQL"))
	    {
	      bufInsSql.append("SELECT A.LOAN_NO,A.LOAN_REFERENCE_NO,GCM.CUSTOMER_NAME,CBM.BRANCH_DESC,(select user_name from SEC_USER_M where SEC_USER_M.USER_ID=CDD.DEAL_RM) AS DEAL_RM, A.LOAN_LOAN_AMOUNT,A.LOAN_TENURE,ISNULL(A.LOAN_DPD,0)Loan_DPD, ISNULL(A.LOAN_OVERDUE_PRINCIPAL,0) LOAN_OVERDUE_PRINCIPAL, ISNULL(A.LOAN_OVERDUE_AMOUNT,0) INSTALLMENT_OVERDUE_AMOUNT, ISNULL((ISNULL(A.LOAN_BALANCE_PRINCIPAL,0.0)+ISNULL(A.LOAN_OVERDUE_AMOUNT,0.0)),0.0) TAOS, CONCAT(CQDM.QUEUE_DESC,' ') AS QUEUE_DESC ,dbo.DATE(CCD.QUEUE_DATE)QUEUE_DATE, CONCAT(SU.USER_NAME,' ') AS USER_NAME ,dbo.DATE(CCD.ALLOCATION_DATE) AS ALLOCATION_DATE FROM CR_LOAN_DTL A  JOIN COM_BRANCH_M CBM ON CBM.BRANCH_ID=A.LOAN_BRANCH  JOIN COLL_CASE_DTL CCD ON CCD.LOAN_ID=A.LOAN_ID   JOIN CR_DEAL_DTL CDD ON A.LOAN_DEAL_ID = CDD.DEAL_ID   JOIN GCD_CUSTOMER_M GCM ON GCM.CUSTOMER_ID=A.LOAN_CUSTOMER_ID   JOIN COLL_QUEUE_DEF_M CQDM ON CQDM.QUEUE_CODE=CCD.QUEUE_CODE  JOIN SEC_USER_M SU ON SU.USER_ID=CCD.USER_ID   WHERE A.REC_STATUS='A' AND CCD.DELINQUENCY_FLAG='Y' AND A.LOAN_DPD>0 AND CCD.USER_ID IN (SELECT USER_ID FROM USER_GROUP_REPORTING WHERE PARENT_USER_ID ='" + userId + "') " + " and (('" + CommonFunction.checkNull(p_branch_type).trim() + "'='All' and A.loan_BRANCH in (SELECT BRANCH_ID FROM sec_user_branch_dtl where  user_id=  '" + CommonFunction.checkNull(userId).trim() + "')) or A.loan_BRANCH in(" + CommonFunction.checkNull(p_branch_id).trim() + "))" + " and (('" + CommonFunction.checkNull(productCategory).trim() + "'='All' and 'A'='A' ) or (A.LOAN_PRODUCT_CATEGORY =('" + CommonFunction.checkNull(productCategory).trim() + "')))" + " and (('" + CommonFunction.checkNull(loanClass).trim() + "'='All' and 'A'='A') or (A.sale_off_flag='" + CommonFunction.checkNull(loanClass).trim() + "')) ");
	    }
	    else
	    {
	      bufInsSql.append("SELECT A.LOAN_NO,A.LOAN_REFERENCE_NO,GCM.CUSTOMER_NAME,CBM.BRANCH_DESC,(select user_name from SEC_USER_M where SEC_USER_M.USER_ID=CDD.DEAL_RM) AS DEAL_RM, A.LOAN_LOAN_AMOUNT,A.LOAN_TENURE,IFNULL(A.LOAN_DPD,0)Loan_DPD,IFNULL(CCD.LOAN_DPD,0)COLL_LOAN_DPD, IFNULL(A.LOAN_OVERDUE_PRINCIPAL,0) LOAN_OVERDUE_PRINCIPAL, IFNULL(A.LOAN_OVERDUE_AMOUNT,0) INSTALLMENT_OVERDUE_AMOUNT, IFNULL((IFNULL(A.LOAN_BALANCE_PRINCIPAL,0.0)+IFNULL(A.LOAN_OVERDUE_AMOUNT,0.0)),0.0) TAOS, CONCAT(CQDM.QUEUE_DESC,' ') AS QUEUE_DESC,DATE(CCD.QUEUE_DATE)QUEUE_DATE, CONCAT(SU.USER_NAME,' ') AS USER_NAME,DATE(CCD.ALLOCATION_DATE) AS ALLOCATION_DATE FROM CR_LOAN_DTL A  JOIN COM_BRANCH_M CBM ON CBM.BRANCH_ID=A.LOAN_BRANCH  JOIN COLL_CASE_DTL CCD ON CCD.LOAN_ID=A.LOAN_ID   JOIN CR_DEAL_DTL CDD ON A.LOAN_DEAL_ID = CDD.DEAL_ID   JOIN GCD_CUSTOMER_M GCM ON GCM.CUSTOMER_ID=A.LOAN_CUSTOMER_ID   JOIN COLL_QUEUE_DEF_M CQDM ON CQDM.QUEUE_CODE=CCD.QUEUE_CODE  JOIN SEC_USER_M SU ON SU.USER_ID=CCD.USER_ID   WHERE A.REC_STATUS='A' AND CCD.DELINQUENCY_FLAG='Y' AND " + dbo + "DATE_FORMAT(CCD.ALLOCATION_DATE,'" + dateFormat + "') <= '" + bDate + "' " + " AND " + dbo + "DATE_FORMAT(CCD.ALLOCATION_DATE,'" + dateFormat + "') >= '" + firstDate + "' " + " AND  CCD.USER_ID IN (SELECT USER_ID FROM USER_GROUP_REPORTING " + "WHERE PARENT_USER_ID ='" + userId + "') " + " and (('" + CommonFunction.checkNull(p_branch_type).trim() + "'='All' and A.loan_BRANCH in (SELECT BRANCH_ID FROM sec_user_branch_dtl where  user_id=  '" + CommonFunction.checkNull(userId).trim() + "')) or A.loan_BRANCH in('" + CommonFunction.checkNull(p_branch_id).trim() + "'))" + " and (('" + CommonFunction.checkNull(productCategory).trim() + "'='All' and 'A'='A' ) or (A.LOAN_PRODUCT_CATEGORY =('" + CommonFunction.checkNull(productCategory).trim() + "')))" + " and (('" + CommonFunction.checkNull(loanClass).trim() + "'='All' and 'A'='A') or (A.sale_off_flag='" + CommonFunction.checkNull(loanClass).trim() + "')) ");
	    }
		
		
		    if((!(loanNo.trim().equalsIgnoreCase("")))) {
				  bufInsSql.append(" AND A.LOAN_ID='"+loanNo+"' ");
		        
		    }
			if((!(fromdate.trim().equalsIgnoreCase("")))) {
				bufInsSql.append(" AND ");
				if(dbType.equalsIgnoreCase("MSSQL"))
				{
					bufInsSql.append(" DBO.");
				}
			  	  bufInsSql.append("date(CCD.ALLOCATION_DATE) >= '"+CommonFunction.changeFormat(fromdate)+"'");
			 
			}
			if((!(todate.trim().equalsIgnoreCase("")))) {
				bufInsSql.append(" AND ");
				if(dbType.equalsIgnoreCase("MSSQL"))
				{
					bufInsSql.append(" DBO.");
				}
			      bufInsSql.append("date(CCD.ALLOCATION_DATE) <= '"+CommonFunction.changeFormat(todate)+"' ");
			    
			}
			if((!(customerName.trim().equalsIgnoreCase("")))) {
				  bufInsSql.append(" AND GCM.CUSTOMER_NAME like '%"+customerName+"%'  ");
				   
			}
						
			if((!(product.trim().equalsIgnoreCase("")))) {
				  bufInsSql.append(" AND A.LOAN_PRODUCT='"+product+"' ");
				    
			}
			
			if((!(dpd2.trim().equalsIgnoreCase("")))) {
				   bufInsSql.append(" AND A.LOAN_DPD >= '"+dpd2+"' ");
			}
			if((!(dpd1.trim().equalsIgnoreCase("")))) {
				   bufInsSql.append(" AND A.LOAN_DPD <= '"+dpd1+"' ");
				    
			}
			if((!(pos2.trim().equalsIgnoreCase("")))) {
	
					bufInsSql.append(" AND A.LOAN_OVERDUE_AMOUNT  >= '"+pos2+"' ");
			}
			if((!(pos1.trim().equalsIgnoreCase("")))) {
				  	bufInsSql.append(" AND  A.LOAN_OVERDUE_AMOUNT  <= '"+pos1+"' ");
				    	
			}
			if((!(customertype.trim().equalsIgnoreCase("")))) {
				    bufInsSql.append(" AND A.LOAN_CUSTOMER_TYPE  ='"+customertype+"' ");
				    	
			}
			if((!(customercategory.trim().equalsIgnoreCase("")))) {
					 bufInsSql.append(" AND GCM.CUSTOMER_CATEGORY  ='"+customercategory+"' ");
					    
			}
			if((!(balanceprincipal.trim().equalsIgnoreCase("")))) {
					 bufInsSql.append(" AND A.LOAN_BALANCE_PRINCIPAL  ='"+balanceprincipal+"' ");
					    	
			}
			if((!(queue.trim().equalsIgnoreCase("")))) {
					 bufInsSql.append(" AND CCD.QUEUE_CODE  ='"+queue+"' ");
					    	
			}
			String reportPath="/reports/";
			if(dbType.equalsIgnoreCase("MSSQL"))
				reportPath=reportPath+"MSSQLREPORTS/";
			else
				reportPath=reportPath+"MYSQLREPORTS/";
		
		String file = "allocatedCaseReports";
		logger.info("REPORT  QUERY   :  " + bufInsSql.toString());
		logger.info("REPORT  PATH    :  "+reportPath);
		logger.info("REPORT  NAME    :  "+file);
		
		
		Map hashMap = new HashMap();
		InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+ file + ".jasper");
		JasperPrint jasperPrint = null;

		hashMap.put("wherequery",bufInsSql.toString());
		hashMap.put("p_printed_date",formatedDate);
		hashMap.put("p_company_logo",p_company_logo);
	
		hashMap.put("p_printed_by",username+" ");
		hashMap.put("p_company_name",cname+" "); 
		hashMap.put("p_report_format",reporttype);
		
		jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
		try
		{
		if(reporttype.equals("P"))
				methodForPDF(file,hashMap,connectDatabase,response,jasperPrint,request);
		if(reporttype.equals("E"))				
			methodForExcel(file,hashMap,connectDatabase,response, jasperPrint);
		if(reporttype.equals("H"))				
				methodForHTML(file,hashMap,connectDatabase,response,jasperPrint,request);	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
			hashMap.clear();
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
	
	public void methodForHTML(String file,Map hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint,HttpServletRequest request)throws Exception
	{
		PrintWriter out=response.getWriter();
	    out.append("<head><script type='text/javascript' src='"+request.getContextPath()+"/js/report/report.js'></script></head>");
	   	response.setContentType("text/html");
		
		String htmlReportFileName=file+".html";
		JRHtmlExporter exporter = new JRHtmlExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN , Boolean.FALSE);
		exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME, "./images/");
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "/images/");
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
		response.setHeader("Content-Disposition", "attachment;filename=" + htmlReportFileName);
		response.setContentType("application/html");
		exporter.exportReport();
	}	


}
	


