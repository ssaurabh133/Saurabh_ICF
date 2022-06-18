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
import com.reportUtility.ReportGenerateUtility;
import com.caps.actionForm.CasesReportForm;


public class CaseReportsAction extends Action 
{	
	private static final Logger logger = Logger.getLogger(CaseReportsAction.class.getName());
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{	
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String username="";
		String userId="";
		String cname="";
		String bDate="";
		if(userobj!=null)
		{
			userId = userobj.getUserId();
			cname=userobj.getConpanyName();
			username=userobj.getUserName();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here execute method of  CaseReportsAction action the session is out----------------");
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
				logger.info("firstDate");
				//firstDate=new Date(startDate.getYear(),startDate.getMonth(),1);
				
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
		String p_printed_by="";
		logger.info("p_printed_by::"+p_printed_by);
		
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
	
		logger.info("p_branch_type"+p_branch_type);
		logger.info("p_branch_id"+p_branch_id);
		
	/*	 bufInsSql.append("Select A.LOAN_NO,A.LOAN_DPD,concat(A.CUSTOMER_NAME,' ') as CUSTOMER_NAME,A.LOAN_OVERDUE_PRINCIPAL,(IFNULL(A.ACTIVEPAYMENT,0)+IFNULL(B.OTHER_CHARGE,0)+ IFNULL(C.CBC,0)+ IFNULL(D.LPP,0)) AS TOTALOVERDUE, A.QUEUE_CODE,A.USERNAME ,A.ALLOCATIONDATE,A.QUEUE_DATE ");   
		 bufInsSql.append(" from ( select CLD.LOAN_NO,CLD.LOAN_DPD,CLD.LOAN_ID,gcm.CUSTOMER_NAME,CLD.LOAN_OVERDUE_PRINCIPAL, IFNULL(CLD.LOAN_OVERDUE_AMOUNT,0)+IFNULL('0.0',0) as activePayment , ");
		 bufInsSql.append("CCD.QUEUE_CODE,CONCAT((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=CCD.USER_ID),' ') as USERNAME ,DATE_FORMAT(CCD.ALLOCATION_DATE,'%d-%m-%Y') AS ALLOCATIONDATE,IFNULL(DATE_FORMAT(CCD.QUEUE_DATE,'%d-%m-%Y'),'') as QUEUE_DATE from cr_loan_dtl CLD  ");
		 bufInsSql.append("left join coll_case_dtl CCD  ON CLD.LOAN_NO=CCD.LOAN_NO join gcd_customer_m gcm on gcm.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID  where CLD.REC_STATUS='A' AND DELINQUENCY_FLAG = 'Y' ");
*/
		if(dbType.equalsIgnoreCase("MSSQL"))
		{
			bufInsSql.append("SELECT A.LOAN_REFERENCE_NO,A.LOAN_NO,(select user_name from SEC_USER_M where SEC_USER_M.USER_ID=CDD.DEAL_RM) AS DEAL_RM,GCM.CUSTOMER_NAME," +
					"CBM.BRANCH_DESC,A.LOAN_LOAN_AMOUNT,ISNULL(A.LOAN_DPD,0)Loan_DPD,CQDM.QUEUE_DESC,IIF(CCD.QUEUE_DATE IS NULL,NULL,DBO.DATE(CCD.QUEUE_DATE)) AS QUEUE_DATE,ISNULL(SU.USER_NAME,' ')USER_NAME,IIF(CCD.ALLOCATION_DATE IS NULL,NULL,DBO.DATE(CCD.ALLOCATION_DATE)) AS ALLOCATION_DATE," +
					"ISNULL(A.LOAN_OVERDUE_AMOUNT,0)INSTALLMENT_OVERDUE_AMOUNT,ISNULL(B.OTHER_CHARGES_DUE,0)OTHER_CHARGES,ISNULL((A.LOAN_OVERDUE_AMOUNT+B.OTHER_CHARGES_DUE),0) AS TOTAL_OVERDUE_AMT ," +
					"ISNULL((ISNULL(A.LOAN_BALANCE_PRINCIPAL,0.0)+ISNULL(A.LOAN_OVERDUE_AMOUNT,0.0)),0.0) POS,ISNULL(SD.SDAMOUNT,0.0)SDAMOUNT," +
					"(ISNULL(A.LOAN_BALANCE_PRINCIPAL,0.0)+ISNULL(A.LOAN_OVERDUE_AMOUNT,0.0))AMT,((ISNULL(A.LOAN_BALANCE_PRINCIPAL,0.0)+ISNULL(A.LOAN_OVERDUE_AMOUNT,0.0))-ISNULL(SD.SDAMOUNT,0.0))AS NPOS" +
					" FROM CR_LOAN_DTL A " +
					"JOIN COM_BRANCH_M CBM ON CBM.BRANCH_ID=A.LOAN_BRANCH  " +
					"JOIN COLL_CASE_DTL CCD ON CCD.LOAN_ID=A.LOAN_ID  " +
					" JOIN CR_DEAL_DTL CDD ON A.LOAN_DEAL_ID = CDD.DEAL_ID  " +
					"JOIN GCD_CUSTOMER_M GCM ON GCM.CUSTOMER_ID=A.LOAN_CUSTOMER_ID  " +
					"LEFT OUTER JOIN COLL_QUEUE_DEF_M CQDM ON CQDM.QUEUE_CODE=CCD.QUEUE_CODE " +
					"LEFT OUTER JOIN SEC_USER_M SU ON SU.USER_ID=CCD.USER_ID  " +
					"LEFT OUTER JOIN " +
					"(	" +
					"	SELECT LOAN_ID,SUM((ISNULL(ADVICE_AMOUNT,0)-ISNULL(TXN_ADJUSTED_AMOUNT,0))) AS OTHER_CHARGES_DUE  	" +
					"	FROM CR_TXNADVICE_DTL " +
					"	WHERE ADVICE_TYPE='R' AND CHARGE_CODE_ID <>'7' AND REC_STATUS <> 'X' 	" +
					"	GROUP BY LOAN_ID) AS B ON A.LOAN_ID=B.LOAN_ID  " +
					"LEFT OUTER JOIN " +
					"(	" +
					"	SELECT SUM(ISNULL(C.SD_AMOUNT,0)) SDAMOUNT,C.LOAN_ID  " +
					"	FROM CR_SD_DTL C WHERE C.REC_STATUS='A' 	" +
					"	GROUP BY C.LOAN_ID)SD ON A.LOAN_ID=SD.LOAN_ID  " +
					"WHERE A.REC_STATUS='A' AND CCD.DELINQUENCY_FLAG='Y' AND A.LOAN_DPD>0" +
					" and (('"+CommonFunction.checkNull(p_branch_type).trim()+"'='All' and A.loan_BRANCH in (SELECT BRANCH_ID FROM sec_user_branch_dtl where  user_id=  '"+CommonFunction.checkNull(userId).trim()+"')) or A.loan_BRANCH in("+CommonFunction.checkNull(p_branch_id).trim()+"))" +
					" and (('"+CommonFunction.checkNull(productCategory).trim()+"'='All' and 'A'='A' ) or (A.LOAN_PRODUCT_CATEGORY =('"+CommonFunction.checkNull(productCategory).trim()+"')))" +
					" and (('"+CommonFunction.checkNull(loanClass).trim()+"'='All' and 'A'='A') or (A.sale_off_flag='"+CommonFunction.checkNull(loanClass).trim()+"')) " );
		}
		else
		{
			
			bufInsSql.append("SELECT A.LOAN_REFERENCE_NO,A.LOAN_NO,(select user_name from SEC_USER_M where SEC_USER_M.USER_ID=CDD.DEAL_RM) AS DEAL_RM,GCM.CUSTOMER_NAME,");
			bufInsSql.append("CBM.BRANCH_DESC,A.LOAN_LOAN_AMOUNT,IFNULL(A.LOAN_DPD,0)Loan_DPD,IFNULL(CQDM.QUEUE_DESC,'')QUEUE_DESC,IF(DATE(CCD.QUEUE_DATE)='0000-00-00',null,DATE(CCD.QUEUE_DATE)) AS QUEUE_DATE,IFNULL(SU.USER_NAME,'')USER_NAME,IF(DATE(CCD.ALLOCATION_DATE)='0000-00-00',null,DATE(CCD.ALLOCATION_DATE)) AS ALLOCATION_DATE, ");
			bufInsSql.append("IFNULL(A.LOAN_OVERDUE_AMOUNT,0)INSTALLMENT_OVERDUE_AMOUNT,");
			bufInsSql.append("IFNULL((IFNULL(A.LOAN_BALANCE_PRINCIPAL,0.0)+IFNULL(A.LOAN_OVERDUE_AMOUNT,0.0)),0.0) POS,IFNULL(SD.SDAMOUNT,0.0)SDAMOUNT,");
			bufInsSql.append("(IFNULL(A.LOAN_BALANCE_PRINCIPAL,0.0)+IFNULL(A.LOAN_OVERDUE_AMOUNT,0.0))AMT,((IFNULL(A.LOAN_BALANCE_PRINCIPAL,0.0)+IFNULL(A.LOAN_OVERDUE_AMOUNT,0.0))-IFNULL(SD.SDAMOUNT,0.0))AS NPOS,@a:=0,	 ");
			bufInsSql.append("(SELECT @a :=SUM((IFNULL(ADVICE_AMOUNT,0)-IFNULL(TXN_ADJUSTED_AMOUNT,0))) AS OTHER_CHARGES_DUE FROM CR_TXNADVICE_DTL 	WHERE ADVICE_TYPE='R' AND CHARGE_CODE_ID <>'7' AND REC_STATUS <> 'X' and CR_TXNADVICE_DTL.LOAN_ID=A.LOAN_ID ) as OTHER_CHARGES,IFNULL((A.LOAN_OVERDUE_AMOUNT+@a),0) AS TOTAL_OVERDUE_AMT ");
			bufInsSql.append("FROM CR_LOAN_DTL A ");
			bufInsSql.append("JOIN COM_BRANCH_M CBM ON CBM.BRANCH_ID=A.LOAN_BRANCH ");
			bufInsSql.append("JOIN COLL_CASE_DTL CCD ON CCD.LOAN_ID=A.LOAN_ID  ");
			bufInsSql.append("JOIN CR_DEAL_DTL CDD ON A.LOAN_DEAL_ID = CDD.DEAL_ID  ");
			bufInsSql.append("JOIN GCD_CUSTOMER_M GCM ON GCM.CUSTOMER_ID=A.LOAN_CUSTOMER_ID  ");
			bufInsSql.append("LEFT OUTER JOIN COLL_QUEUE_DEF_M CQDM ON CQDM.QUEUE_CODE=CCD.QUEUE_CODE ");
			bufInsSql.append("LEFT OUTER JOIN SEC_USER_M SU ON SU.USER_ID=CCD.USER_ID  ");
			bufInsSql.append("LEFT OUTER JOIN " );
			bufInsSql.append("(		SELECT SUM(IFNULL(C.SD_AMOUNT,0)) SDAMOUNT,C.LOAN_ID  ");
			bufInsSql.append("	FROM CR_SD_DTL C WHERE C.REC_STATUS='A' 	" );
			bufInsSql.append("	GROUP BY C.LOAN_ID)SD ON A.LOAN_ID=SD.LOAN_ID  " );
			bufInsSql.append(" WHERE A.REC_STATUS='A' AND CCD.DELINQUENCY_FLAG='Y' AND A.LOAN_DPD>0 ");
			bufInsSql.append(" AND "+dbo+"DATE_FORMAT(CCD.ALLOCATION_DATE,'"+dateFormat+"') <= '"+bDate+"' ");
			bufInsSql.append(" AND "+dbo+"DATE_FORMAT(CCD.ALLOCATION_DATE,'"+dateFormat+"') >= '"+firstDate+"' ");
			bufInsSql.append(" and (('"+CommonFunction.checkNull(p_branch_type).trim()+"'='All' and A.loan_BRANCH in (SELECT BRANCH_ID FROM sec_user_branch_dtl where  user_id=  '"+CommonFunction.checkNull(userId).trim()+"')) or A.loan_BRANCH in('"+CommonFunction.checkNull(p_branch_id).trim()+"'))");
			bufInsSql.append(" and (('"+CommonFunction.checkNull(productCategory).trim()+"'='All' and 'A'='A' ) or (A.LOAN_PRODUCT_CATEGORY =('"+CommonFunction.checkNull(productCategory).trim()+"')))" );
			bufInsSql.append(" and (('"+CommonFunction.checkNull(loanClass).trim()+"'='All' and 'A'='A') or (A.sale_off_flag='"+CommonFunction.checkNull(loanClass).trim()+"')) " );
			
			/*bufInsSql.append("SELECT A.LOAN_REFERENCE_NO,A.LOAN_NO,(select user_name from SEC_USER_M where SEC_USER_M.USER_ID=CDD.DEAL_RM) AS DEAL_RM,GCM.CUSTOMER_NAME," +
					"CBM.BRANCH_DESC,A.LOAN_LOAN_AMOUNT,IFNULL(A.LOAN_DPD,0)Loan_DPD,CQDM.QUEUE_DESC,IF(DATE(CCD.QUEUE_DATE)='0000-00-00',NULL,DATE(CCD.QUEUE_DATE)) AS QUEUE_DATE,SU.USER_NAME,IF(DATE(CCD.ALLOCATION_DATE)='0000-00-00',NULL,DATE(CCD.ALLOCATION_DATE)) AS ALLOCATION_DATE," +
					"IFNULL(A.LOAN_OVERDUE_AMOUNT,0)INSTALLMENT_OVERDUE_AMOUNT,IFNULL(B.OTHER_CHARGES_DUE,0)OTHER_CHARGES,IFNULL((A.LOAN_OVERDUE_AMOUNT+B.OTHER_CHARGES_DUE),0) AS TOTAL_OVERDUE_AMT ," +
					"IFNULL((IFNULL(A.LOAN_BALANCE_PRINCIPAL,0.0)+IFNULL(A.LOAN_OVERDUE_AMOUNT,0.0)),0.0) POS,IFNULL(SD.SDAMOUNT,0.0)SDAMOUNT," +
					"(IFNULL(A.LOAN_BALANCE_PRINCIPAL,0.0)+IFNULL(A.LOAN_OVERDUE_AMOUNT,0.0))AMT,((IFNULL(A.LOAN_BALANCE_PRINCIPAL,0.0)+IFNULL(A.LOAN_OVERDUE_AMOUNT,0.0))-IFNULL(SD.SDAMOUNT,0.0))AS NPOS " +
					" FROM CR_LOAN_DTL A " +
					"JOIN COM_BRANCH_M CBM ON CBM.BRANCH_ID=A.LOAN_BRANCH  " +
					"JOIN COLL_CASE_DTL CCD ON CCD.LOAN_ID=A.LOAN_ID  " +
					" JOIN CR_DEAL_DTL CDD ON A.LOAN_DEAL_ID = CDD.DEAL_ID  " +
					"JOIN GCD_CUSTOMER_M GCM ON GCM.CUSTOMER_ID=A.LOAN_CUSTOMER_ID  " +
					"LEFT OUTER JOIN COLL_QUEUE_DEF_M CQDM ON CQDM.QUEUE_CODE=CCD.QUEUE_CODE " +
					"LEFT OUTER JOIN SEC_USER_M SU ON SU.USER_ID=CCD.USER_ID  " +
					"LEFT OUTER JOIN " +
					"(	" +
					"	SELECT LOAN_ID,SUM((IFNULL(ADVICE_AMOUNT,0)-IFNULL(TXN_ADJUSTED_AMOUNT,0))) AS OTHER_CHARGES_DUE  	" +
					"	FROM CR_TXNADVICE_DTL " +
					"	WHERE ADVICE_TYPE='R' AND CHARGE_CODE_ID <>'7' AND REC_STATUS <> 'X' 	" +
					"	GROUP BY LOAN_ID) AS B ON A.LOAN_ID=B.LOAN_ID  " +
					"LEFT OUTER JOIN " +
					"(	" +
					"	SELECT SUM(IFNULL(C.SD_AMOUNT,0)) SDAMOUNT,C.LOAN_ID  " +
					"	FROM CR_SD_DTL C WHERE C.REC_STATUS='A' 	" +
					"	GROUP BY C.LOAN_ID)SD ON A.LOAN_ID=SD.LOAN_ID  " +
					" WHERE A.REC_STATUS='A' AND CCD.DELINQUENCY_FLAG='Y' AND A.LOAN_DPD>0 " +
					" and (('"+CommonFunction.checkNull(p_branch_type).trim()+"'='All' and A.loan_BRANCH in (SELECT BRANCH_ID FROM sec_user_branch_dtl where  user_id=  '"+CommonFunction.checkNull(userId).trim()+"')) or A.loan_BRANCH in('"+CommonFunction.checkNull(p_branch_id).trim()+"'))" +
					" and (('"+CommonFunction.checkNull(productCategory).trim()+"'='All' and 'A'='A' ) or (A.LOAN_PRODUCT_CATEGORY =('"+CommonFunction.checkNull(productCategory).trim()+"')))" +
					" and (('"+CommonFunction.checkNull(loanClass).trim()+"'='All' and 'A'='A') or (A.sale_off_flag='"+CommonFunction.checkNull(loanClass).trim()+"')) " );*/
				}
		logger.info("productCategory"+productCategory);
		logger.info("loanClass"+loanClass);
		logger.info("p_branch_type"+p_branch_type);
		/* if((!(productCategory.trim().equalsIgnoreCase("")))) {
			  bufInsSql.append(" and (if('"+CommonFunction.checkNull(productCategory).trim()+"'='All',A.LOAN_PRODUCT_CATEGORY in (select  product_category from cr_productcategory_m),A.LOAN_PRODUCT_CATEGORY =('"+CommonFunction.checkNull(productCategory).trim()+"'))) ");
	        
	    }
		
		 if((!(loanClass.trim().equalsIgnoreCase("")))) {
			  bufInsSql.append(" and (if('"+CommonFunction.checkNull(loanClass).trim()+"'='All',ifnull(A.sale_off_flag,'') in (select value from generic_master where generic_key ='sale_off_flag'),A.sale_off_flag =('"+CommonFunction.checkNull(loanClass).trim()+"')))");
	        
	    }
		 if(((p_branch_type.trim().equalsIgnoreCase("All")))) {
			  bufInsSql.append(" and A.loan_BRANCH in (SELECT BRANCH_ID FROM sec_user_branch_dtl where user_id=(select user_id from sec_user_m where user_name= '"+CommonFunction.checkNull(username).trim()+"')");
	        
	    }
		 
		 if(((p_branch_type.trim().equalsIgnoreCase("specific")))) {
			  bufInsSql.append(" and A.loan_BRANCH in('"+CommonFunction.checkNull(p_branch_id).trim()+"'))");
	        
	    }
		*/ 
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
//			if((!(todate.trim().equalsIgnoreCase("")))) {
//				bufInsSql.append(" AND ");
//				if(dbType.equalsIgnoreCase("MSSQL"))
//				{
//					bufInsSql.append(" DBO.");
//				}
//			      bufInsSql.append("date(CCD.ALLOCATION_DATE) <= '"+CommonFunction.changeFormat(todate)+"' ");
//			    
//			}
			if((!(customerName.trim().equalsIgnoreCase("")))) {
				  bufInsSql.append(" AND GCM.CUSTOMER_NAME like '%"+customerName+"%'  ");
				   
			}
						
			if((!(product.trim().equalsIgnoreCase("")))) {
				  bufInsSql.append(" AND A.LOAN_PRODUCT='"+product+"' ");
				    
			}
			
			if((!(user.trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" AND CCD.USER_ID ='"+user+"' ");
		    	
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
		
		String file = "deliquentCaseReports";
		logger.info("REPORT  QUERY   :  "+bufInsSql.toString());
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
		long timeStamp = System.nanoTime();
		reportName=reportName+"_"+timeStamp;
		JasperExportManager.exportReportToPdfFile(jasperPrint,request.getRealPath("/reports") + "/" +reportName+".pdf");
		File f=new File(request.getRealPath("/reports") + "/" +reportName+".pdf");
		FileInputStream fin = new FileInputStream(f);
		ServletOutputStream outStream = response.getOutputStream();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment;filename='"+reportName+"'.pdf");
		byte[] buffer = new byte[1024];
		int n = 0;
		try{
			while ((n = fin.read(buffer)) != -1) 
				outStream.write(buffer, 0, n);			
			/*outStream.flush();
			outStream.close();*/
			ReportGenerateUtility.closeOutputStream(reportName,fin, outStream);
		}catch(Exception e){
			logger.error("Socket exception occurred. Now being handled by application to close output steam. "+reportName);
		}
	}
	public void methodForExcel(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint)throws Exception
	{
		long timeStamp = System.nanoTime();
		reportName=reportName+"_"+timeStamp;
		String excelReportFileName=reportName+".xls";		
		JExcelApiExporter exporterXLS = new JExcelApiExporter();
		exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		response.setHeader("Content-Disposition", "attachment;filename=" + excelReportFileName);
		response.setContentType("application/vnd.ms-excel");
		StringBuffer buffer = new StringBuffer();
		exporterXLS.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, buffer);
		exporterXLS.exportReport();
		response.getOutputStream().write(buffer.toString().getBytes());
		response.flushBuffer();
	}	
	
	public void methodForHTML(String file,Map hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint,HttpServletRequest request)throws Exception
	{
		long timeStamp = System.nanoTime();
		file=file+"_"+timeStamp;
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
	


