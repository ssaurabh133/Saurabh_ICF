package com.caps.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
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

import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.login.roleManager.UserObject;
import com.caps.actionForm.UnallocatedReportForm;


public class UnallocatedReportsAction extends Action 
{	
	private static final Logger logger = Logger.getLogger(UnallocatedReportsAction.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dbo=resource.getString("lbl.dbPrefix");
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{	
		
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String cname="";
		String username="";
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
			userId = userobj.getUserId();
			cname=userobj.getConpanyName();
			username=userobj.getUserName();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here execute method of  UnallocatedReportsAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}	
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dbType=resource.getString("lbl.dbType");
	UnallocatedReportForm af=(UnallocatedReportForm) form;	
	String loanNo=af.getLbxLoanno();
	String reporttype=af.getReportformat();
	String customerName=af.getCustomername();
	String product=af.getProduct(); 


	String customertype=af.getCustype();
	String customercategory=af.getCustcategory();		
	String balanceprincipal=af.getBalanceprincipal();
	String queue=af.getLbxQueuesearchId();
	String fromdate=af.getFromdate();		
	String todate=af.getTodate();
	String dpd1=af.getDpd1();
	String dpd2=af.getDpd2();
	String pos1=af.getPos1();
	String pos2=af.getPos2();
	String productCategory=af.getProducTCategory();
	String loanClass=af.getLoanClassification();
	String p_branch_type=af.getBranch();
	String p_branch_id=af.getLbxBranchId();
	String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
	StringBuffer bufInsSql = new StringBuffer();	 
	String dateFormat = resource.getString("lbl.dateInDao");
	String dateFormatStr = resource.getString("lbl.dateForDisbursal");
	Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
	

	Date todaydate= new Date();
	SimpleDateFormat formate=new SimpleDateFormat(dateFormatStr);
	String formatedDate=formate.format(todaydate);
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
	 
	if(dbType.equalsIgnoreCase("MSSQL"))
	{
		bufInsSql.append("Select A.LOAN_NO,A.LOAN_DPD,concat(A.CUSTOMER_NAME,' ') as CUSTOMER_NAME,A.LOAN_OVERDUE_PRINCIPAL,(ISNULL(A.ACTIVEPAYMENT,0)+ISNULL(B.OTHER_CHARGE,0)+ ISNULL(C.CBC,0)+ ISNULL(D.LPP,0)) AS TOTALOVERDUE," +
				" A.QUEUE_CODE,A.QUEUE_DATE  from ( select CLD.LOAN_NO,CLD.LOAN_DPD,CLD.LOAN_ID,gcm.CUSTOMER_NAME,CLD.LOAN_OVERDUE_PRINCIPAL, " +
				" ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0)+ISNULL('0.0',0) as activePayment , CCD.QUEUE_CODE,ISNULL("+dbo+"DATE_FORMAT(CCD.QUEUE_DATE,'%d-%m-%Y'),'') as QUEUE_DATE " +
				" from cr_loan_dtl CLD  left join coll_case_dtl CCD  ON CLD.LOAN_NO=CCD.LOAN_NO " +
				" join gcd_customer_m gcm on gcm.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID  where CLD.REC_STATUS='A' AND DELINQUENCY_FLAG = 'Y' and CCD.USER_ID='' ");
    }
	else
	{
		bufInsSql.append(" 	 Select CLD.LOAN_NO,CLD.LOAN_DPD,concat(gcm.CUSTOMER_NAME,' ') as CUSTOMER_NAME,CCD.LOAN_OVERDUE_PRINCIPAL, ");
		bufInsSql.append("IFNULL(CCD.QUEUE_CODE,'')QUEUE_CODE,IFNULL(DATE_FORMAT(CCD.QUEUE_DATE,'%d-%m-%Y'),'') as QUEUE_DATE,@A:=0,@B:=0,@C:=0, ");
		bufInsSql.append("(SELECT @A:=ifnull(sum(IFNULL(ADVICE_AMOUNT,0)-IFNULL(TXN_ADJUSTED_AMOUNT,0)-IFNULL(AMOUNT_IN_PROCESS,0)), 0)  ");
		bufInsSql.append("FROM cr_txnadvice_dtl WHERE ADVICE_TYPE='R' AND REC_STATUS='A'  AND CHARGE_CODE_ID NOT IN ('109','110','7') AND  CLD.LOAN_ID= cr_txnadvice_dtl.LOAN_ID)AS OTHER_CHARGE, ");
		bufInsSql.append("(SELECT @B:=ifnull(sum(IFNULL(ADVICE_AMOUNT,0)-IFNULL(TXN_ADJUSTED_AMOUNT,0)-IFNULL(AMOUNT_IN_PROCESS,0)), 0) ");
		bufInsSql.append("FROM cr_txnadvice_dtl WHERE    ADVICE_TYPE='R' AND REC_STATUS='A' AND CHARGE_CODE_ID='110' AND cr_txnadvice_dtl.LOAN_ID= CLD.LOAN_ID) AS CBC, ");
		bufInsSql.append("(SELECT @C:= ifnull(sum(IFNULL(ADVICE_AMOUNT,0)-IFNULL(TXN_ADJUSTED_AMOUNT,0)-IFNULL(AMOUNT_IN_PROCESS,0)), 0)   ");
		bufInsSql.append("FROM cr_txnadvice_dtl WHERE    ADVICE_TYPE='R'AND REC_STATUS='A'AND CHARGE_CODE_ID='109' AND cr_txnadvice_dtl.LOAN_ID= CLD.LOAN_ID) AS LPP, ");
		bufInsSql.append("(IFNULL(CLD.LOAN_OVERDUE_AMOUNT,0)+IFNULL('0.0',0)+IFNULL(@A,0)+ IFNULL(@B,0)+ IFNULL(@C,0)) AS TOTALOVERDUE");
		bufInsSql.append(" from cr_loan_dtl CLD ");
		bufInsSql.append("left join coll_case_dtl CCD  ON CLD.LOAN_NO=CCD.LOAN_NO ");
		bufInsSql.append("join gcd_customer_m gcm on gcm.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID  where CLD.REC_STATUS='A' AND DELINQUENCY_FLAG = 'Y' and CCD.USER_ID=''");
		//bufInsSql.append(" AND "+dbo+"DATE_FORMAT(CCD.ALLOCATION_DATE,'"+dateFormat+"') <= '"+bDate+"' ");
		//bufInsSql.append(" AND "+dbo+"DATE_FORMAT(CCD.ALLOCATION_DATE,'"+dateFormat+"') >= '"+firstDate+"' ");

		//COMMENTED FOR QUERY TUNING
		/*bufInsSql.append("Select A.LOAN_NO,A.LOAN_DPD,concat(A.CUSTOMER_NAME,' ') as CUSTOMER_NAME,A.LOAN_OVERDUE_PRINCIPAL,(IFNULL(A.ACTIVEPAYMENT,0)+IFNULL(B.OTHER_CHARGE,0)+ IFNULL(C.CBC,0)+ IFNULL(D.LPP,0)) AS TOTALOVERDUE, A.QUEUE_CODE,A.QUEUE_DATE ");   
		bufInsSql.append(" from ( select CLD.LOAN_NO,CLD.LOAN_DPD,CLD.LOAN_ID,gcm.CUSTOMER_NAME,CLD.LOAN_OVERDUE_PRINCIPAL, IFNULL(CLD.LOAN_OVERDUE_AMOUNT,0)+IFNULL('0.0',0) as activePayment , ");
		bufInsSql.append("CCD.QUEUE_CODE,IFNULL(DATE_FORMAT(CCD.QUEUE_DATE,'%d-%m-%Y'),'') as QUEUE_DATE from cr_loan_dtl CLD  ");
		bufInsSql.append("left join coll_case_dtl CCD  ON CLD.LOAN_NO=CCD.LOAN_NO join gcd_customer_m gcm on gcm.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID  where CLD.REC_STATUS='A' AND DELINQUENCY_FLAG = 'Y' and CCD.USER_ID=''");
*/	
		}
	
	     if((!(loanNo.trim().equalsIgnoreCase("")))) {
			  bufInsSql.append(" AND CLD.LOAN_ID='"+loanNo+"' ");
	        
	    }
		if((!(fromdate.trim().equalsIgnoreCase("")))) {
		  	  bufInsSql.append(" AND "+dbo+"DATE_FORMAT(CCD.ALLOCATION_DATE,'"+dateFormat+"') >= '"+fromdate+"' ");
		}
		if((!(todate.trim().equalsIgnoreCase("")))) {
		      bufInsSql.append(" AND "+dbo+"DATE_FORMAT(CCD.ALLOCATION_DATE,'"+dateFormat+"') <= '"+todate+"' ");
		}
		if((!(customerName.trim().equalsIgnoreCase("")))) {
			  bufInsSql.append(" AND gcm.CUSTOMER_NAME like '%"+customerName+"%'  ");
			   
		}
					
		if((!(product.trim().equalsIgnoreCase("")))) {
			  bufInsSql.append(" AND CLD.LOAN_PRODUCT='"+product+"' ");
			    
		}
		if((!(dpd2.trim().equalsIgnoreCase("")))) {
			   bufInsSql.append(" AND CLD.LOAN_DPD >= '"+dpd2+"' ");
		}
		if((!(dpd1.trim().equalsIgnoreCase("")))) {
			   bufInsSql.append(" AND CLD.LOAN_DPD <= '"+dpd1+"' ");
			    
		}
		if((!(pos2.trim().equalsIgnoreCase("")))) {
				bufInsSql.append(" AND CLD.LOAN_OVERDUE_PRINCIPAL  >= '"+pos2+"' ");
		}
		if((!(pos1.trim().equalsIgnoreCase("")))) {
			  	bufInsSql.append(" AND  CLD.LOAN_OVERDUE_PRINCIPAL  <= '"+pos1+"' ");
			    	
		}
		if((!(customertype.trim().equalsIgnoreCase("")))) {
			    bufInsSql.append(" AND CLD.LOAN_CUSTOMER_TYPE  ='"+customertype+"' ");
			    	
		}
		if((!(customercategory.trim().equalsIgnoreCase("")))) {
				 bufInsSql.append(" AND gcm.CUSTOMER_CATEGORY  ='"+customercategory+"' ");
				    
		}
		if((!(balanceprincipal.trim().equalsIgnoreCase("")))) {
				 bufInsSql.append(" AND CLD.LOAN_BALANCE_PRINCIPAL  ='"+balanceprincipal+"' ");
				    	
		}
		if((!(queue.trim().equalsIgnoreCase("")))) {
				 bufInsSql.append(" AND CCD.QUEUE_CODE  ='"+queue+"' ");
				    	
		}
		if((!(loanClass.trim().equalsIgnoreCase("All")))) {
			  bufInsSql.append(" and  CLD.sale_off_flag =('"+CommonFunction.checkNull(loanClass).trim()+"') ");
	        
	    }
	     
	     if((!(productCategory.trim().equalsIgnoreCase("All")))) {
			  bufInsSql.append(" and  CLD.LOAN_PRODUCT_CATEGORY =('"+CommonFunction.checkNull(productCategory).trim()+"') ");
	        
	    }
	     
	     
	     if((!(p_branch_type.trim().equalsIgnoreCase("All")))) {
			  bufInsSql.append("and CLD.loan_BRANCH in("+CommonFunction.checkNull(p_branch_id).trim()+") ");
			  
			  
	        
	    }
	     if(((p_branch_type.trim().equalsIgnoreCase("All")))) {
	    	 bufInsSql.append("and CLD.loan_BRANCH in (SELECT BRANCH_ID FROM sec_user_branch_dtl where  user_id=  '"+CommonFunction.checkNull(userId).trim()+"') ");
	     }
		if(dbType.equalsIgnoreCase("MSSQL"))
		{
			bufInsSql.append(" ) a LEFT JOIN ");
			bufInsSql.append("(Select LOAN_ID, ISNULL(sum(ISNULL(ADVICE_AMOUNT,0)-ISNULL(TXN_ADJUSTED_AMOUNT,0)-ISNULL(AMOUNT_IN_PROCESS,0)), 0) OTHER_CHARGE FROM cr_txnadvice_dtl WHERE ADVICE_TYPE='R' AND REC_STATUS='A'  AND CHARGE_CODE_ID NOT IN ('109','110','7') GROUP BY LOAN_ID ");
			bufInsSql.append(") b ON  a.LOAN_ID= b.LOAN_ID ");
			bufInsSql.append("LEFT JOIN ( ");
			bufInsSql.append("Select  LOAN_ID, ISNULL(sum(ISNULL(ADVICE_AMOUNT,0)-ISNULL(TXN_ADJUSTED_AMOUNT,0)-ISNULL(AMOUNT_IN_PROCESS,0)), 0) CBC FROM cr_txnadvice_dtl WHERE    ADVICE_TYPE='R' AND REC_STATUS='A' AND CHARGE_CODE_ID='110' GROUP BY LOAN_ID ");
			bufInsSql.append(") C ON  B.LOAN_ID= C.LOAN_ID ");
			bufInsSql.append(" LEFT JOIN  ( ");
			bufInsSql.append("Select LOAN_ID, ISNULL(sum(ISNULL(ADVICE_AMOUNT,0)-ISNULL(TXN_ADJUSTED_AMOUNT,0)-ISNULL(AMOUNT_IN_PROCESS,0)), 0) LPP FROM cr_txnadvice_dtl WHERE    ADVICE_TYPE='R'AND REC_STATUS='A'AND CHARGE_CODE_ID='109' GROUP BY LOAN_ID ");
			bufInsSql.append(") D ON C.LOAN_ID= D.LOAN_ID ");		
		}
		/*else
		{
			bufInsSql.append(" ) a LEFT JOIN ");
			bufInsSql.append("(Select LOAN_ID, ifnull(sum(IFNULL(ADVICE_AMOUNT,0)-IFNULL(TXN_ADJUSTED_AMOUNT,0)-IFNULL(AMOUNT_IN_PROCESS,0)), 0) OTHER_CHARGE FROM cr_txnadvice_dtl WHERE ADVICE_TYPE='R' AND REC_STATUS='A'  AND CHARGE_CODE_ID NOT IN ('109','110','7') GROUP BY LOAN_ID ");
			bufInsSql.append(") b ON  a.LOAN_ID= b.LOAN_ID ");
			bufInsSql.append("LEFT JOIN ( ");
			bufInsSql.append("Select  LOAN_ID, ifnull(sum(IFNULL(ADVICE_AMOUNT,0)-IFNULL(TXN_ADJUSTED_AMOUNT,0)-IFNULL(AMOUNT_IN_PROCESS,0)), 0) CBC FROM cr_txnadvice_dtl WHERE    ADVICE_TYPE='R' AND REC_STATUS='A' AND CHARGE_CODE_ID='110' GROUP BY LOAN_ID ");
			bufInsSql.append(") C ON  B.LOAN_ID= C.LOAN_ID ");
			bufInsSql.append(" LEFT JOIN  ( ");
			bufInsSql.append("Select LOAN_ID, ifnull(sum(IFNULL(ADVICE_AMOUNT,0)-IFNULL(TXN_ADJUSTED_AMOUNT,0)-IFNULL(AMOUNT_IN_PROCESS,0)), 0) LPP FROM cr_txnadvice_dtl WHERE    ADVICE_TYPE='R'AND REC_STATUS='A'AND CHARGE_CODE_ID='109' GROUP BY LOAN_ID ");
			bufInsSql.append(") D ON C.LOAN_ID= D.LOAN_ID " );
									
			}*/
			
		String file = "UnallocatedCasesReport";
		String reportPath="/reports/";
		if(dbType.equalsIgnoreCase("MSSQL"))
			reportPath=reportPath+"MSSQLREPORTS/";
		else
			reportPath=reportPath+"MYSQLREPORTS/";
		
		logger.info("REPORT  QUERY   :  "+bufInsSql.toString());
		logger.info("REPORT  PATH    :  "+reportPath);
		logger.info("REPORT  NAME    :  "+file);
		
	
	Map hashMap = new HashMap();


	InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + file + ".jasper");
	JasperPrint jasperPrint = null;

	hashMap.put("whereclause",bufInsSql.toString());
	hashMap.put("p_printed_date",formatedDate);
	hashMap.put("p_company_logo",p_company_logo);
	hashMap.put("p_printed_by",username+" ");
	hashMap.put("p_company_name",cname+" "); 
	hashMap.put("p_report_format",reporttype);
		try{
			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
		
			
			if(reporttype.equals("P"))
					methodForPDF(file,hashMap,connectDatabase,response, jasperPrint,request);
			if(reporttype.equals("E"))				
					methodForExcel(file,hashMap,connectDatabase,response, jasperPrint,request);
			if(reporttype.equals("H"))				
					methodForHTML(file,hashMap,connectDatabase,response, jasperPrint,request);
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


	
	public void methodForPDF(String reportName,Map hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint, HttpServletRequest request)throws Exception
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
	public void methodForExcel(String file,Map hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint,HttpServletRequest request)throws Exception
	{
		String excelReportFileName=file+".xls";		
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
	


