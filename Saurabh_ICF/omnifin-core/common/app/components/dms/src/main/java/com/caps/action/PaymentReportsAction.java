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
import com.caps.actionForm.PaymentReportsForm;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import java.text.DecimalFormat;
import com.login.roleManager.UserObject;


public class PaymentReportsAction extends Action {
	
	private static final Logger logger = Logger.getLogger(PaymentReportsAction.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dbo=resource.getString("lbl.dbPrefix");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	public ActionForward execute(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response)throws Exception {
		    logger.info("In PaymentReportsAction::::::::::::::::::::::::::::::");
			  
		    
			HttpSession session = request.getSession();
			UserObject userobj = (UserObject) session.getAttribute("userobject");
			String cname="";
			String username="";
			String userID="";
			int company_id=0;
			if(userobj!=null)
			{
				cname=userobj.getConpanyName();
				username=userobj.getUserName();
				 company_id=userobj.getCompanyId();		
				 userID = userobj.getUserId();
			}else{
				logger.info("here execute method of  PaymentReportsAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
		    ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		    String dbType=resource.getString("lbl.dbType");
	
		    PaymentReportsForm paymentForm=(PaymentReportsForm) form;	
			String reportformat=paymentForm.getReportformat();
			String loanNo=paymentForm.getLbxLoanno();
			String product=paymentForm.getProduct();
			String customerName=paymentForm.getCustomername();
			String dpd2=paymentForm.getDpd2();
			String dpd1=paymentForm.getDpd1();
			String queue=paymentForm.getLbxQueuesearchId();
			String pos2=paymentForm.getPos2();
			String pos1=paymentForm.getPos1();
			String customerType=paymentForm.getCustype();
			String custCategory=paymentForm.getCustcategory();
			String balanceprincipal=paymentForm.getBalanceprincipal();
			String fromdate=paymentForm.getFromdate();
			String todate=paymentForm.getTodate();
			String user=paymentForm.getLbxUserSearchId();
			String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
			StringBuffer bufInsSql = new StringBuffer();	 
			String dateFormat = resource.getString("lbl.dateInDao");
			String dateFormatstr = resource.getString("lbl.dateForDisbursal");
			String fromDueDate=paymentForm.getFromDueDate();
			String toDueDate=paymentForm.getToDueDate();
			Connection connectDatabase = ConnectionDAO.getConnection();		
			if(dbType.equalsIgnoreCase("MSSQL"))
			{
				bufInsSql.append("SELECT  CLD.rec_status,DELINQUENCY_FLAG,CLD.LOAN_NO,CLD.LOAN_DPD,CLD.LOAN_ID,");
				bufInsSql.append("concat(gcm.CUSTOMER_NAME,' ')CUSTOMER_NAME,CLD.LOAN_OVERDUE_PRINCIPAL,");
				bufInsSql.append("(ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0)+ISNULL(B.OTHER_CHARGE,0)) AS TOTALOVERDUE, "); 
				bufInsSql.append("concat(CCD.QUEUE_CODE,' ')QUEUE_CODE,CONCAT(SEC.USER_NAME,' ') as USERNAME ,");
				bufInsSql.append("CCD.ALLOCATION_DATE,CCD.QUEUE_DATE,  CONCAT(CCD.LAST_ACTION_CODE,' ') AS LAST_ACTION_CODE ,");
				bufInsSql.append("CCD.LAST_ACTION_DATE,IFNULL(ALLC.ACTION_AMOUNT,0) ACTION_AMOUNT,ALLC.NEXT_ACTION_DATE");
				bufInsSql.append("from coll_case_dtl CCD ");
				bufInsSql.append("JOIN cr_loan_dtl CLD ON CLD.LOAN_ID=CCD.LOAN_ID AND  CLD.REC_STATUS='A'  AND CLD.LOAN_DPD >0 ");
				bufInsSql.append("LEFT JOIN gcd_customer_m gcm on gcm.CUSTOMER_ID=CCD.LOAN_CUSTOMER_ID "); 
				bufInsSql.append("LEFT JOIN sec_user_m SEC ON SEC.USER_ID=CCD.USER_ID");
				bufInsSql.append("LEFT JOIN ");
				bufInsSql.append("(Select LOAN_ID, IFNULL(sum(ISNULL(ADVICE_AMOUNT,0)-ISNULL(TXN_ADJUSTED_AMOUNT,0)-IFNULL(AMOUNT_IN_PROCESS,0)), 0) OTHER_CHARGE");
				bufInsSql.append("FROM cr_txnadvice_dtl  ");        
				bufInsSql.append("WHERE ADVICE_TYPE='R' AND REC_STATUS='A'  AND CHARGE_CODE_ID NOT IN ('109','110')  ");
				bufInsSql.append("GROUP BY LOAN_ID ) b  ON  CLD.LOAN_ID= b.LOAN_ID   ");
				bufInsSql.append("JOIN coll_follow_up_trails ALLC ON ALLC.LOAN_ID=CCD.LOAN_ID AND ALLC.ACTION_CODE = 'PTP'");
				if((!(fromdate.trim().equalsIgnoreCase("")))) {
				  	  bufInsSql.append(" AND "+dbo+"date(ALLC.ACTION_DATE) >= '"+CommonFunction.changeFormat(fromdate)+"'");
				 
				}
				if((!(todate.trim().equalsIgnoreCase("")))) {
				      bufInsSql.append(" AND "+dbo+"date(ALLC.ACTION_DATE) <= '"+CommonFunction.changeFormat(todate)+"' ");
				    
				}
				if((!(fromDueDate.trim().equalsIgnoreCase("")))) {
				  	  bufInsSql.append(" AND "+dbo+"DATE(ALLC.LAST_ACTION_DATE) >= '"+CommonFunction.changeFormat(fromDueDate)+"' ");
				 
				}
				if((!(toDueDate.trim().equalsIgnoreCase("")))) {
				      bufInsSql.append(" AND "+dbo+"DATE(ALLC.LAST_ACTION_DATE) <= '"+CommonFunction.changeFormat(toDueDate)+"' ");
				      				    
				}
			    bufInsSql.append("where   CCD.DELINQUENCY_FLAG = 'Y' ");
			}
			else
			{
				bufInsSql.append("SELECT  CLD.rec_status,DELINQUENCY_FLAG,CLD.LOAN_NO,CLD.LOAN_DPD,IFNULL(CCD.LOAN_DPD,0)COLL_LOAN_DPD,CLD.LOAN_ID, ");
				bufInsSql.append("concat(gcm.CUSTOMER_NAME,' ')CUSTOMER_NAME,CLD.LOAN_OVERDUE_PRINCIPAL, ");
				bufInsSql.append(" concat(CCD.QUEUE_CODE,' ')QUEUE_CODE,CONCAT(ifnull(SEC.USER_NAME,''),' ') as USERNAME , ");
				bufInsSql.append("IF(DATE(CCD.ALLOCATION_DATE)='0000-00-00','',IFNULL(date_format(CCD.ALLOCATION_DATE,'%Y-%m-%d'),''))AS ALLOCATION_DATE, ");
				bufInsSql.append("IF(DATE(CCD.QUEUE_DATE)='0000-00-00',null	,IFNULL(date_format(CCD.QUEUE_DATE,'%Y-%m-%d'),''))QUEUE_DATE,IF(DATE(ALLC.NEXT_ACTION_DATE)='0000-00-00',null,IFNULL(date_format(ALLC.NEXT_ACTION_DATE,'%Y-%m-%d'),''))NEXT_ACTION_DATE, ");
				bufInsSql.append("CONCAT(ALLC.ACTION_CODE,' ') AS LAST_ACTION_CODE , date_format(ALLC.ACTION_DATE,'%Y-%m-%d') as LAST_ACTION_DATE,IFNULL(ALLC.ACTION_AMOUNT,0) ACTION_AMOUNT,@A:=0, ");
				bufInsSql.append("(SELECT @A:=IFNULL(sum(IFNULL(ADVICE_AMOUNT,0)-IFNULL(TXN_ADJUSTED_AMOUNT,0)-IFNULL(AMOUNT_IN_PROCESS,0)), 0) ");
				bufInsSql.append("FROM cr_txnadvice_dtl   WHERE ADVICE_TYPE='R' AND REC_STATUS='A'  ");
				bufInsSql.append(" AND CHARGE_CODE_ID NOT IN ('109','110') AND  CLD.LOAN_ID=cr_txnadvice_dtl.LOAN_ID)AS OTHER_CHARGE, ");
				bufInsSql.append("(IFNULL(CLD.LOAN_OVERDUE_AMOUNT,0)+IFNULL(@A,0)) AS TOTALOVERDUE ");
				bufInsSql.append("from coll_case_dtl CCD   ");
				bufInsSql.append("JOIN cr_loan_dtl CLD ON CLD.LOAN_ID=CCD.LOAN_ID AND  CLD.REC_STATUS='A'  AND CLD.LOAN_DPD >0 ");
				bufInsSql.append("LEFT JOIN gcd_customer_m gcm on gcm.CUSTOMER_ID=CCD.LOAN_CUSTOMER_ID  ");
				bufInsSql.append("LEFT JOIN sec_user_m SEC ON SEC.USER_ID=CCD.USER_ID ");
				bufInsSql.append("JOIN coll_follow_up_trails ALLC ON ALLC.LOAN_ID=CCD.LOAN_ID AND ALLC.ACTION_CODE = 'PTP'  ");	
				
				//Commented for query tuning
				/*bufInsSql.append(" SELECT  CLD.rec_status,DELINQUENCY_FLAG,CLD.LOAN_NO,CLD.LOAN_DPD,CLD.LOAN_ID, ");
				bufInsSql.append(" concat(gcm.CUSTOMER_NAME,' ')CUSTOMER_NAME,CLD.LOAN_OVERDUE_PRINCIPAL, ");
				bufInsSql.append(" (IFNULL(CLD.LOAN_OVERDUE_AMOUNT,0)+IFNULL(B.OTHER_CHARGE,0)) AS TOTALOVERDUE, "); 
				bufInsSql.append(" concat(CCD.QUEUE_CODE,' ')QUEUE_CODE,CONCAT(SEC.USER_NAME,' ') as USERNAME ,");
				bufInsSql.append(" IF(DATE(CCD.ALLOCATION_DATE)='0000-00-00','',IFNULL(date_format(CCD.ALLOCATION_DATE,'%d-%m-%Y'),''))AS ALLOCATION_DATE, ");
				bufInsSql.append(" IF(DATE(CCD.QUEUE_DATE)='0000-00-00','',IFNULL(date_format(CCD.QUEUE_DATE,'%d-%m-%Y'),''))QUEUE_DATE,  CONCAT(CCD.LAST_ACTION_CODE,' ') AS LAST_ACTION_CODE ,");
				bufInsSql.append(" CCD.LAST_ACTION_DATE,IFNULL(ALLC.ACTION_AMOUNT,0) ACTION_AMOUNT");
				bufInsSql.append(" from coll_case_dtl CCD ");
				bufInsSql.append(" JOIN cr_loan_dtl CLD ON CLD.LOAN_ID=CCD.LOAN_ID AND  CLD.REC_STATUS='A'  AND CLD.LOAN_DPD >0 ");
				bufInsSql.append(" LEFT JOIN gcd_customer_m gcm on gcm.CUSTOMER_ID=CCD.LOAN_CUSTOMER_ID "); 
				bufInsSql.append(" LEFT JOIN sec_user_m SEC ON SEC.USER_ID=CCD.USER_ID");
				bufInsSql.append(" LEFT JOIN ");
				bufInsSql.append(" (Select LOAN_ID, IFNULL(sum(IFNULL(ADVICE_AMOUNT,0)-IFNULL(TXN_ADJUSTED_AMOUNT,0)-IFNULL(AMOUNT_IN_PROCESS,0)), 0) OTHER_CHARGE");
				bufInsSql.append(" FROM cr_txnadvice_dtl  ");        
				bufInsSql.append(" WHERE ADVICE_TYPE='R' AND REC_STATUS='A'  AND CHARGE_CODE_ID NOT IN ('109','110')  ");
				bufInsSql.append(" GROUP BY LOAN_ID ) b  ON  CLD.LOAN_ID= b.LOAN_ID   ");
				bufInsSql.append(" JOIN coll_follow_up_trails ALLC ON ALLC.LOAN_ID=CCD.LOAN_ID AND ALLC.ACTION_CODE = 'PTP' ");*/
				if((!(fromdate.trim().equalsIgnoreCase("")))) {
				  	  bufInsSql.append(" AND "+dbo+"date(ALLC.ACTION_DATE) >= '"+CommonFunction.changeFormat(fromdate)+"'");
				 
				}
				if((!(todate.trim().equalsIgnoreCase("")))) {
				      bufInsSql.append(" AND "+dbo+"date(ALLC.ACTION_DATE) <= '"+CommonFunction.changeFormat(todate)+"' ");
				    
				}
				if((!(fromDueDate.trim().equalsIgnoreCase("")))) {
				  	  bufInsSql.append(" AND "+dbo+"DATE(ALLC.NEXT_ACTION_DATE) >= '"+CommonFunction.changeFormat(fromDueDate)+"' ");
				 
				}
				if((!(toDueDate.trim().equalsIgnoreCase("")))) {
				      bufInsSql.append(" AND "+dbo+"DATE(ALLC.NEXT_ACTION_DATE) <= '"+CommonFunction.changeFormat(toDueDate)+"' ");
				      				    
				}
			    bufInsSql.append("where   CCD.DELINQUENCY_FLAG = 'Y' ");
				
				
				
				
//				bufInsSql.append(" SELECT  CLD.rec_status,DELINQUENCY_FLAG,CLD.LOAN_NO,CLD.LOAN_DPD,CLD.LOAN_ID,concat(gcm.CUSTOMER_NAME,' ')CUSTOMER_NAME,CLD.LOAN_OVERDUE_PRINCIPAL, ");
//				bufInsSql.append(" (IFNULL(CLD.LOAN_OVERDUE_AMOUNT,0)+IFNULL(B.OTHER_CHARGE,0)) AS TOTALOVERDUE, ");
//				bufInsSql.append(" concat(CCD.QUEUE_CODE,' ')QUEUE_CODE,CONCAT(SEC.USER_NAME,' ') as USERNAME ,CCD.ALLOCATION_DATE,CCD.QUEUE_DATE, ");
//				bufInsSql.append(" CONCAT(CCD.LAST_ACTION_CODE,' ') AS LAST_ACTION_CODE ,CCD.LAST_ACTION_DATE,IFNULL(ALLC.ACTION_AMOUNT,0)ACTION_AMOUNT ");
//				bufInsSql.append(" from cr_loan_dtl CLD ");
//				bufInsSql.append(" JOIN coll_case_dtl CCD  ON CLD.LOAN_NO=CCD.LOAN_NO AND  CLD.REC_STATUS='A'  AND CLD.LOAN_DPD >0 ");
//				bufInsSql.append(" JOIN gcd_customer_m gcm on gcm.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID ");
//				bufInsSql.append(" JOIN sec_user_m SEC ON SEC.USER_ID=CCD.USER_ID ");
//				bufInsSql.append(" JOIN ");
//				bufInsSql.append(" ( ");
//				bufInsSql.append(" 		Select LOAN_ID, IFNULL(sum(IFNULL(ADVICE_AMOUNT,0)-IFNULL(TXN_ADJUSTED_AMOUNT,0)-IFNULL(AMOUNT_IN_PROCESS,0)), 0) OTHER_CHARGE ");
//				bufInsSql.append("		FROM cr_txnadvice_dtl ");
//				bufInsSql.append(" 		WHERE ADVICE_TYPE='R' AND REC_STATUS='A'  AND CHARGE_CODE_ID NOT IN ('109','110') ");
//				bufInsSql.append(" 		GROUP BY LOAN_ID ");
//				bufInsSql.append(" )b ON  CLD.LOAN_ID= b.LOAN_ID ");
//				bufInsSql.append(" LEFT JOIN");
//				bufInsSql.append(" (");
//				bufInsSql.append("		select LOAN_ID,sum(ifnull(ACTION_AMOUNT,0))ACTION_AMOUNT from"); 
//				bufInsSql.append("		coll_follow_up_trails");
//				bufInsSql.append("		group by loan_id");
//				bufInsSql.append(" )ALLC ON(ALLC.LOAN_ID=CLD.LOAN_ID)");
//				bufInsSql.append(" where CCD.DELINQUENCY_FLAG = 'Y'  and CCD.LAST_ACTION_CODE='PTP' ");
			}
					 		
			
			     if((!(loanNo.trim().equalsIgnoreCase("")))) {
					  bufInsSql.append(" AND CLD.LOAN_ID='"+loanNo+"' ");
			        
			    }
				if((!(user.trim().equalsIgnoreCase("")))) {
			    	  bufInsSql.append(" AND CCD.USER_ID ='"+user+"' ");
			    	
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
				if((!(customerType.trim().equalsIgnoreCase("")))) {
					    bufInsSql.append(" AND CLD.LOAN_CUSTOMER_TYPE  ='"+customerType+"' ");
					    	
				}
				if((!(custCategory.trim().equalsIgnoreCase("")))) {
						 bufInsSql.append(" AND gcm.CUSTOMER_CATEGORY  ='"+custCategory+"' ");
						    
				}
				if((!(balanceprincipal.trim().equalsIgnoreCase("")))) {
						 bufInsSql.append(" AND CLD.LOAN_BALANCE_PRINCIPAL  ='"+balanceprincipal+"' ");
						    	
				}
				if((!(queue.trim().equalsIgnoreCase("")))) {
						 bufInsSql.append(" AND CCD.QUEUE_CODE  ='"+queue+"' ");
						    	
				}
				
//				 bufInsSql.append(" ) a LEFT JOIN ");
//				 bufInsSql.append("(Select LOAN_ID, ifnull(sum(IFNULL(ADVICE_AMOUNT,0)-IFNULL(TXN_ADJUSTED_AMOUNT,0)-IFNULL(AMOUNT_IN_PROCESS,0)), 0) OTHER_CHARGE FROM cr_txnadvice_dtl WHERE ADVICE_TYPE='R' AND REC_STATUS='A'  AND CHARGE_CODE_ID NOT IN ('109','110','7') GROUP BY LOAN_ID ");
//				 bufInsSql.append(") b ON  a.LOAN_ID= b.LOAN_ID ");
//				 bufInsSql.append("LEFT JOIN ( ");
//				 bufInsSql.append("Select  LOAN_ID, ifnull(sum(IFNULL(ADVICE_AMOUNT,0)-IFNULL(TXN_ADJUSTED_AMOUNT,0)-IFNULL(AMOUNT_IN_PROCESS,0)), 0) CBC FROM cr_txnadvice_dtl WHERE    ADVICE_TYPE='R' AND REC_STATUS='A' AND CHARGE_CODE_ID='110' GROUP BY LOAN_ID ");
//				 bufInsSql.append(") C ON  B.LOAN_ID= C.LOAN_ID ");
//				 bufInsSql.append(" LEFT JOIN  ( ");
//				 bufInsSql.append("Select LOAN_ID, ifnull(sum(IFNULL(ADVICE_AMOUNT,0)-IFNULL(TXN_ADJUSTED_AMOUNT,0)-IFNULL(AMOUNT_IN_PROCESS,0)), 0) LPP FROM cr_txnadvice_dtl WHERE    ADVICE_TYPE='R'AND REC_STATUS='A'AND CHARGE_CODE_ID='109' GROUP BY LOAN_ID ");
//				 bufInsSql.append(") D ON C.LOAN_ID= D.LOAN_ID ");		
							
			String file = "paymentReports";
			String reportPath="/reports/";
			if(dbType.equalsIgnoreCase("MSSQL"))
				reportPath=reportPath+"MSSQLREPORTS/";
			else
				reportPath=reportPath+"MYSQLREPORTS/";
			
			logger.info("REPORT  QUERY   :  "+bufInsSql.toString());
			logger.info("REPORT  PATH    :  "+reportPath);
			logger.info("REPORT  NAME    :  "+file);

			Map hashMap = new HashMap();
			
			
					
				Date date =new Date();
				SimpleDateFormat simpleDate=new SimpleDateFormat(dateFormatstr);
				String CurrentDate=simpleDate.format(date);
				InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + file + ".jasper");
			    JasperPrint jasperPrint = null;
			    
				hashMap.put("whereClause",bufInsSql.toString());
		
				
				hashMap.put("p_company_name",cname+" ");
				hashMap.put("p_printed_by",username+" ");
				hashMap.put("p_printed_date",CurrentDate);

				hashMap.put("p_company_logo",p_company_logo);
				hashMap.put("p_report_format",reportformat);
				

			

			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
			try
			{
				if(reportformat.equals("P"))
					methodForPDF(file,hashMap,connectDatabase,response,jasperPrint,request);
				if(reportformat.equals("E"))				
					methodForExcel(file,hashMap,connectDatabase,response,jasperPrint,request);
				if(reportformat.equals("H"))				
					methodForHTML(file,hashMap,connectDatabase,response,jasperPrint,request);
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
