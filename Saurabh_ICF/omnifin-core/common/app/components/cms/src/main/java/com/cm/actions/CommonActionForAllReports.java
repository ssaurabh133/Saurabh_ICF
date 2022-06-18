package com.cm.actions;



import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
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


public class CommonActionForAllReports extends Action 
{	
	private static final Logger logger = Logger.getLogger(CommonActionForAllReports.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		logger.info("In CommonActionForAllReports.........");
		try
		{
				HttpSession session = request.getSession();
			
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String cname =null;
				String username =null;
				String bDate =null;
				if(userobj!=null){
					cname = userobj.getConpanyName();
					username=userobj.getUserName();
					bDate=userobj.getBusinessdate();
				}else{
					logger.info(" in execute method of CommonActionForAllReports  action the session is out----------------");
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

				DynaActionForm daf=(DynaActionForm)form;
				ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
				String dateFormat=resource.getString("lbl.dateInDao");
				String dbType=resource.getString("lbl.dbType");
				String p_company_name=cname+" ";
				String p_report_name="";
				String reportformat=(String)daf.get("reportformat");
				String p_instrument_type=(String)daf.get("instrumentType");
				String p_status_type="";		
				String p_printed_by=username+" ";
				String p_printed_date="";
				String p_loan_no=(String)daf.get("loanNumber");	
				String p_loan_id=(String)daf.get("lbx_loan_from_id");
				String P_deal_loan="";
				String p_stage=(String)daf.get("stage");
				String p_status=(String)daf.get("status");
				String p_branch=(String)daf.get("branchid");
				StringBuilder query=new StringBuilder();
				String p_from_number="";
				String p_to_number="";
				String p_to_id="";
				String p_from_id="";
				String 	reportName=(String)daf.get("reportId");
				String p_printed_author_date="";
				String 	p_from_author_date=(String)daf.get("fromDate");
				String 	p_to_author_date=(String)daf.get("toDate");
				String p_form_number="";
				String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports\\logo.bmp";
				String reportPath="/reports/";
				if(dbType.equalsIgnoreCase("MSSQL"))
					reportPath=reportPath+"MSSQLREPORTS/";
				else
					reportPath=reportPath+"MYSQLREPORTS/";
				
			
			//	*****************************Cheque Status Space************************************************************
			if(reportName.equals("cheque_Status"))
			{
				if(!(p_instrument_type.equalsIgnoreCase("")))
				{
					if(p_instrument_type.equals("P"))
						p_status_type=(String)daf.get("statusTypeR");
					else
						p_status_type=(String)daf.get("statusTypeP");
				}			
				if(p_loan_no.trim().length()==0)
			      	       	p_loan_no="All";
				else 
					p_loan_id=p_loan_id;
				
				logger.info(p_loan_no +"p_loan_no");
							      	        
					if(p_instrument_type.equals("P"))
					{
						
						if(p_status_type.equals("All"))				
							p_report_name="Payments Sent to Customer/Realised/Cancelled/Marked as Stopped    ";
						if(p_status_type.equals("C"))
							p_report_name="Payments Sent to Customer ";
						if(p_status_type.equals("R"))
							p_report_name="Payments Realised to Customer ";
						if(p_status_type.equals("X"))
							p_report_name="Payments Cancelled ";
						if(p_status_type.equals("S"))
							p_report_name="Payments Marked as Stopped ";
					}
					else
					{
						if(p_status_type.equals("All"))
							p_report_name="Receipts Deposited/Bounced/Cancelled/Realised ";
						if(p_status_type.equals("D"))
							p_report_name="Receipts Deposited ";
						if(p_status_type.equals("B"))
							p_report_name="Receipts Bounced ";
						if(p_status_type.equals("X"))			
							p_report_name="Receipts Cancelled ";			
						if(p_status_type.equals("R"))				
							p_report_name="Receipts Realised ";
					}
					p_printed_author_date="  From  "+formate(p_from_author_date)+"   To   "+formate(p_to_author_date);			
			//*****************************Cheque Status Space End ************************************************************
			}
			else if(reportName.equals("Documents_Collection"))
			{	
				//*****************************Documents Collection Space ************************************************************
				String check=(String)daf.get("fromLoan");
				if(check.length()==0)
				{
					p_from_number=(String)daf.get("fromDeal");
					p_to_number=(String)daf.get("toDeal");
					p_to_id=(String)daf.get("lbxDealTo");
					p_from_id=(String)daf.get("lbxDealNo");
					p_form_number="Deal Application Form ";
					int dealfrom;
					dealfrom=Integer.parseInt(p_from_id);
					int dealto;
					dealto=Integer.parseInt(p_to_id);
					P_deal_loan="Deal ";
					if(dbType.equalsIgnoreCase("MSSQL"))
					{
						query= new StringBuilder();
						query.append("	select cr_deal_dtl.DEAL_APPLICATION_FORM_NO as form_number,com_branch_m.BRANCH_DESC,cr_deal_dtl.DEAL_ID loan_id," +
									" cr_document_dtl.STAGE_ID,cr_deal_dtl.DEAL_NO as deal_loan, CONCAT(cr_deal_customer_m.CUSTOMER_NAME,'   ')as CUSTOMER_NAME," +
									" CONCAT(cr_product_m.PRODUCT_DESC,'   ')as product, CONCAT(cr_scheme_m.SCHEME_DESC,'  ')as scheme," +
									" case when DOC_STATUS='R' then 'RECEIVED ' when DOC_STATUS='D' then 'DEFFERED ' when DOC_STATUS ='P' then 'PENDING ' when DOC_STATUS='W' then 'WAIVED ' end as DOC_STATUS," +
									" CONCAT(generic_master.DESCRIPTION,'  ')as DOC_TYPE,STAGE_ID," +
									"(SELECT STUFF((SELECT '|'+concat(A.DOC_DESC,' ')" +
									"FROM cr_document_dtl A  where A.TXN_DOC_ID =cr_document_dtl.TXN_DOC_ID  FOR XML PATH ('')),1,1,''))as Document_Name ," +
									"(SELECT STUFF((SELECT '|'+concat(B.DOC_DESC,' ')" +
									"FROM cr_document_dtl A  JOIN cr_document_child_m B on CHARINDEX( concat('|',B.DOC_CHILD_ID,'|'),concat('|',A.DOC_CHILD_IDS)) > 0" +
									"where A.TXN_DOC_ID =cr_document_dtl.TXN_DOC_ID  FOR XML PATH ('')),1,1,''))as Child_Document_Name," +
									"cr_document_dtl.DOC_RECEIVED_DATE as RECEIVED_DATE,cr_document_dtl.DOC_DEFFRED_DATE as DEFERRED_DATE," +
									" concat(iif(DATEDIFF(DD,cr_document_dtl.DOC_DEFFRED_DATE,(select convert(datetime, PARAMETER_VALUE,  121) from PARAMETER_MST where PARAMETER_KEY='BUSINESS_DATE'))>=0,DATEDIFF(DD,cr_document_dtl.DOC_DEFFRED_DATE,(select convert(datetime, PARAMETER_VALUE,  121) from PARAMETER_MST where PARAMETER_KEY='BUSINESS_DATE')),null),' ')Aging_days,  "+
									" z.DISBURSAL_DATE DISBURSAL_DATE,IIF(e.USER_NAME IS NULL,' ',CONCAT(e.USER_NAME,' ')) AREA_MANEGER,IIF(f.USER_NAME IS NULL,' ',CONCAT(f.USER_NAME,' ')) AREA_EXECUTIVE, e.USER_PHONE1 MOBILE_NO " +
									" from cr_deal_dtl " +
									" left join CR_LOAN_DTL a on(cr_deal_dtl.DEAL_ID=a.LOAN_DEAL_ID and a.REC_STATUS='A') " +
									" left join CR_LOAN_DISBURSAL_DTL z on(z.LOAN_ID=a.LOAN_ID and z.REC_STATUS='A' and z.DISBURSAL_NO=1 ) " +
									" left join SEC_USER_M e on(cr_deal_dtl.DEAL_RM=e.USER_ID) " +
									" left join SEC_USER_M f on(cr_deal_dtl.DEAL_RO=f.USER_ID) " +
 									" join cr_deal_loan_dtl on(cr_deal_dtl.DEAL_ID=cr_deal_loan_dtl.DEAL_ID)" +
									" join cr_deal_customer_m on (cr_deal_dtl.DEAL_CUSTOMER_ID=cr_deal_customer_m.CUSTOMER_ID) " +
									" join cr_product_m on(cr_deal_loan_dtl.DEAL_PRODUCT=cr_product_m.PRODUCT_ID) " +
									" join cr_scheme_m on(cr_deal_loan_dtl.DEAL_SCHEME=cr_scheme_m.SCHEME_ID)" +
									" join com_branch_m on(cr_deal_dtl.DEAL_BRANCH=com_branch_m.BRANCH_ID)" +
									" left join cr_document_dtl on(cr_document_dtl.TXNID=cr_deal_dtl.DEAL_ID and txn_type='DC')" +
									" left join generic_master on(cr_document_dtl.DOC_TYPE=generic_master.VALUE and GENERIC_KEY='DOC_ENTITY_TYPE')	" +
									" where cr_deal_dtl.DEAL_ID>='"+dealfrom+"' and cr_deal_dtl.DEAL_ID<='"+dealto+"' " +
									" and STAGE_ID='"+p_stage+"' " );
							if(p_branch.length()==0)
								p_branch="All";
							else
								query.append(" and com_branch_m.BRANCH_DESC='"+p_branch+"'" );
							if(!(p_status.equals("ALL")))
								query.append(" and DOC_STATUS='"+p_status+"'" );
					}
					else
					{
						query= new StringBuilder();
						query.append("	select cr_deal_dtl.DEAL_APPLICATION_FORM_NO as form_number,com_branch_m.BRANCH_DESC,cr_deal_dtl.DEAL_ID loan_id," +
								" cr_document_dtl.STAGE_ID,cr_deal_dtl.DEAL_NO as deal_loan, CONCAT(cr_deal_customer_m.CUSTOMER_NAME,'   ')as CUSTOMER_NAME," +
								" CONCAT(cr_product_m.PRODUCT_DESC,'   ')as product, CONCAT(cr_scheme_m.SCHEME_DESC,'  ')as scheme," +
								" case when DOC_STATUS='R' then 'RECEIVED ' when DOC_STATUS='D' then 'DEFFERED ' when DOC_STATUS ='P' then 'PENDING ' when DOC_STATUS='W' then 'WAIVED ' end as DOC_STATUS," +
								" CONCAT( cr_document_dtl.DOC_DESC,' ')  as Document_Name,concat(CHD.CH ,' ') as Child_Document_Name," +
								" CONCAT(generic_master.DESCRIPTION,'  ')as DOC_TYPE,STAGE_ID,if(date(cr_document_dtl.DOC_RECEIVED_DATE)='0000-00-00',null,cr_document_dtl.DOC_RECEIVED_DATE) as RECEIVED_DATE,if(date(cr_document_dtl.DOC_DEFFRED_DATE)='0000-00-00',null,cr_document_dtl.DOC_DEFFRED_DATE) as DEFERRED_DATE, " +
								" concat(if(DATEDIFF((select PARAMETER_VALUE from PARAMETER_MST where PARAMETER_KEY='BUSINESS_DATE'),DOC_DEFFRED_DATE)>=0,DATEDIFF((select  PARAMETER_VALUE from PARAMETER_MST where PARAMETER_KEY='BUSINESS_DATE'),cr_document_dtl.DOC_DEFFRED_DATE),' '),' ')Aging_days "+
								" from cr_deal_dtl " +
								" join cr_deal_loan_dtl on(cr_deal_dtl.DEAL_ID=cr_deal_loan_dtl.DEAL_ID)" +
								" join cr_deal_customer_m on (cr_deal_dtl.DEAL_CUSTOMER_ID=cr_deal_customer_m.CUSTOMER_ID) " +
								" join cr_product_m on(cr_deal_loan_dtl.DEAL_PRODUCT=cr_product_m.PRODUCT_ID) " +
								" join cr_scheme_m on(cr_deal_loan_dtl.DEAL_SCHEME=cr_scheme_m.SCHEME_ID)" +
								" join com_branch_m on(cr_deal_dtl.DEAL_BRANCH=com_branch_m.BRANCH_ID)" +
								" left join cr_document_dtl on(cr_document_dtl.TXNID=cr_deal_dtl.DEAL_ID and txn_type='DC')" +
								" left join generic_master on(cr_document_dtl.DOC_TYPE=generic_master.VALUE and GENERIC_KEY='DOC_ENTITY_TYPE')	" +
								" LEFT JOIN " +
								" (" +
								"		SELECT group_concat(concat(B.DOC_DESC,' ') separator ' | ')as CH,A.TXN_DOC_ID " +
								"		FROM cr_document_dtl A " +
								"		JOIN cr_document_child_m B " +
								"		on instr(concat( '|',A.DOC_CHILD_IDS),concat('|',B.DOC_CHILD_ID,'|') ) > 0 " +
								"		where A.TXNID >='"+dealfrom+"' and A.TXNID <='"+dealto+"' " +
								"		and STAGE_ID='"+p_stage+"'" +
								"		GROUP BY A.TXN_DOC_ID " +
								" )CHD ON(CHD.TXN_DOC_ID=cr_document_dtl.TXN_DOC_ID) " +
								" where cr_deal_dtl.DEAL_ID>='"+dealfrom+"' and cr_deal_dtl.DEAL_ID<='"+dealto+"' " +
								" and STAGE_ID='"+p_stage+"' " );
						if(p_branch.length()==0)
								p_branch="All";
						else
							query.append(" and com_branch_m.BRANCH_DESC='"+p_branch+"'" );
						if(!(p_status.equals("ALL")))
							query.append(" and DOC_STATUS='"+p_status+"'" );
					}
				}
				else
				{
					p_from_number=(String)daf.get("fromLoan");
					p_to_number=(String)daf.get("toLoan");
					p_to_id=(String)daf.get("lbx_loan_to_id");
					p_from_id=(String)daf.get("lbx_loan_from_id");
					p_form_number="Application form";
					int	loanto; 
					loanto= Integer.parseInt(p_to_id);
					int	loanfrom;
					loanfrom=Integer.parseInt(p_from_id);
					P_deal_loan="Loan ";
					if(dbType.equalsIgnoreCase("MSSQL"))
					{
						query= new StringBuilder();
						query.append("select a.LOAN_REFERENCE_NO as form_number,a.loan_id, com_branch_m.BRANCH_DESC,a.LOAN_NO as deal_loan," +
								" CONCAT(gcd_customer_m.CUSTOMER_NAME,'   ')as CUSTOMER_NAME, CONCAT(cr_product_m.PRODUCT_DESC,'   ')as product," +
								" CONCAT(cr_scheme_m.SCHEME_DESC,'  ')as scheme, case DOC_STATUS when 'R' then 'RECEIVED ' when 'D' then 'DEFFERED ' when 'P' then 'PENDING ' when 'W' then 'WAIVED ' end as DOC_STATUS," +
								" CONCAT(generic_master.DESCRIPTION,'  ')as DOC_TYPE,STAGE_ID," +
								"(SELECT STUFF((SELECT '|'+concat(A.DOC_DESC,' ')" +
								"FROM cr_document_dtl A  where A.TXN_DOC_ID =cr_document_dtl.TXN_DOC_ID  FOR XML PATH ('')),1,1,''))as Document_Name ," +
								"(SELECT STUFF((SELECT '|'+concat(B.DOC_DESC,' ')" +
								"FROM cr_document_dtl A  JOIN cr_document_child_m B on CHARINDEX( concat('|',B.DOC_CHILD_ID,'|'),concat('|',A.DOC_CHILD_IDS)) > 0" +
								"where A.TXN_DOC_ID =cr_document_dtl.TXN_DOC_ID  FOR XML PATH ('')),1,1,''))as Child_Document_Name," +
								"cr_document_dtl.DOC_RECEIVED_DATE as RECEIVED_DATE,cr_document_dtl.DOC_DEFFRED_DATE as DEFERRED_DATE," +
								" concat(iif(DATEDIFF(DD,cr_document_dtl.DOC_DEFFRED_DATE,(select convert(datetime, PARAMETER_VALUE,  121) from PARAMETER_MST where PARAMETER_KEY='BUSINESS_DATE'))>=0,DATEDIFF(DD,cr_document_dtl.DOC_DEFFRED_DATE,(select convert(datetime, PARAMETER_VALUE,  121) from PARAMETER_MST where PARAMETER_KEY='BUSINESS_DATE')),null),' ')Aging_days,  "+
								" b.DISBURSAL_DATE DISBURSAL_DATE,IIF(e.USER_NAME IS NULL,' ',CONCAT(e.USER_NAME,' ')) AREA_MANEGER,IIF(f.USER_NAME IS NULL,' ',CONCAT(f.USER_NAME,' ')) AREA_EXECUTIVE, e.USER_PHONE1 MOBILE_NO " +
								" from cr_loan_dtl a " +
								" join CR_DEAL_DTL c on (c.DEAL_ID=a.LOAN_DEAL_ID and c.REC_STATUS='A') " +
								" left join SEC_USER_M e on(c.DEAL_RM=e.USER_ID) " +
								" left join SEC_USER_M f on(c.DEAL_RO=f.USER_ID) " +
								" left join CR_LOAN_DISBURSAL_DTL b on (b.LOAN_ID=a.LOAN_ID and b.REC_STATUS='A' and b.DISBURSAL_NO=1 ) " +
								" left join cr_document_dtl on(cr_document_dtl.TXNID=a.LOAN_ID) " +
								" join generic_master on(cr_document_dtl.DOC_TYPE=generic_master.VALUE and GENERIC_KEY='DOC_ENTITY_TYPE' )" +
								" join gcd_customer_m on(a.LOAN_CUSTOMER_ID=gcd_customer_m.CUSTOMER_ID)" +
								" join cr_product_m on(a.LOAN_PRODUCT=cr_product_m.PRODUCT_ID)" +
								" join cr_scheme_m on(a.LOAN_SCHEME=cr_scheme_m.SCHEME_ID) " +
								" join com_branch_m on(a.LOAN_BRANCH=com_branch_m.BRANCH_ID)" +
								" where a.loan_id>='"+loanfrom+"' and a.loan_id<='"+loanto+"'" +
							    " and cr_document_dtl.STAGE_ID='"+p_stage+"' and a.REC_STATUS='A' " ) ;	
						if(p_branch.length()==0)
							p_branch="All";
						else
							query.append(" and com_branch_m.BRANCH_DESC='"+p_branch+"'" );
						if(!(p_status.equals("ALL")))
							query.append(" and DOC_STATUS='"+p_status+"'" );	
					}
					else
					{
						query= new StringBuilder();
						query.append("select a.LOAN_REFERENCE_NO as form_number,a.loan_id, com_branch_m.BRANCH_DESC,a.LOAN_NO as deal_loan," +
							" CONCAT(gcd_customer_m.CUSTOMER_NAME,'   ')as CUSTOMER_NAME, CONCAT(cr_product_m.PRODUCT_DESC,'   ')as product," +
							" CONCAT(cr_scheme_m.SCHEME_DESC,'  ')as scheme, case DOC_STATUS when 'R' then 'RECEIVED ' when 'D' then 'DEFFERED ' when 'P' then 'PENDING ' when 'W' then 'WAIVED ' end as DOC_STATUS," +
							" CONCAT( cr_document_dtl.DOC_DESC,' ')  as Document_Name,concat(CHD.CH ,' ') as Child_Document_Name,cr_document_dtl.STAGE_ID," +
							" CONCAT(generic_master.DESCRIPTION,'  ')as DOC_TYPE,STAGE_ID,if(date(cr_document_dtl.DOC_RECEIVED_DATE)='0000-00-00',null,cr_document_dtl.DOC_RECEIVED_DATE) as RECEIVED_DATE,if(date(cr_document_dtl.DOC_DEFFRED_DATE)='0000-00-00',null,cr_document_dtl.DOC_DEFFRED_DATE) as DEFERRED_DATE , " +
							" concat(if(DATEDIFF((select PARAMETER_VALUE from PARAMETER_MST where PARAMETER_KEY='BUSINESS_DATE'),DOC_DEFFRED_DATE)>=0,DATEDIFF((select  PARAMETER_VALUE from PARAMETER_MST where PARAMETER_KEY='BUSINESS_DATE'),cr_document_dtl.DOC_DEFFRED_DATE),' '),' ')Aging_days "+
							" from cr_loan_dtl a " +
							" left join cr_document_dtl on(cr_document_dtl.TXNID=a.LOAN_ID) " +
							" join generic_master on(cr_document_dtl.DOC_TYPE=generic_master.VALUE and GENERIC_KEY='DOC_ENTITY_TYPE' )" +
							" join gcd_customer_m on(a.LOAN_CUSTOMER_ID=gcd_customer_m.CUSTOMER_ID)" +
							" join cr_product_m on(a.LOAN_PRODUCT=cr_product_m.PRODUCT_ID)" +
							" join cr_scheme_m on(a.LOAN_SCHEME=cr_scheme_m.SCHEME_ID) " +
							" join com_branch_m on(a.LOAN_BRANCH=com_branch_m.BRANCH_ID)" +
							" LEFT JOIN " +
							" (" +
							" 		SELECT group_concat(concat(B.DOC_DESC,' ') separator ' | ')as CH,A.TXN_DOC_ID " +
							" 		FROM cr_document_dtl A " +
							" 		JOIN cr_document_child_m B on instr(concat( '|',A.DOC_CHILD_IDS),concat('|',B.DOC_CHILD_ID,'|') ) > 0 " +
							" 		where A.TXNID >='"+loanfrom+"' and A.TXNID <='"+loanto+"' " +
							"		and STAGE_ID='"+p_stage+"'" +
							" GROUP BY A.TXN_DOC_ID " +
							" ) CHD ON(CHD.TXN_DOC_ID=cr_document_dtl.TXN_DOC_ID)" +
							" where a.loan_id>='"+loanfrom+"' and a.loan_id<='"+loanto+"'" +
						    " and cr_document_dtl.STAGE_ID='"+p_stage+"'" );	
							if(p_branch.length()==0)
								p_branch="All";
							else
								query.append(" and com_branch_m.BRANCH_DESC='"+p_branch+"'" );
							if(!(p_status.equals("ALL")))
								query.append(" and DOC_STATUS='"+p_status+"'" );	
					}
				}
				logger.info("Document Collection Report Query  :   "+query.toString());	
				
									
				//*****************************Documents Collection Space End************************************************************
			}
				
			Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
			Map<Object,Object> hashMap = new HashMap<Object,Object>();
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter= new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			p_printed_date = formatter.format(currentDate.getTime());
						
			hashMap.put("p_company_logo",p_company_logo);
			hashMap.put("p_report_name",p_report_name);
			hashMap.put("p_instrument_type", p_instrument_type);
			hashMap.put("p_status_type", p_status_type);
			hashMap.put("p_loan_no",p_loan_no);	
			hashMap.put("p_printed_date", p_printed_date);
			hashMap.put("p_printed_author_date", p_printed_author_date);	
			hashMap.put("p_from_author_date", p_from_author_date);
			hashMap.put("p_to_author_date", p_to_author_date);
			hashMap.put("p_branch", p_branch);
			hashMap.put("p_status", p_status);
			hashMap.put("p_stage", p_stage);
			hashMap.put("p_from_number",p_from_number);
			hashMap.put("p_to_number",p_to_number);
			hashMap.put("P_deal_loan", P_deal_loan);
			hashMap.put("p_form_number", p_form_number);
			hashMap.put("query", query.toString());
			hashMap.put("p_company_name",p_company_name);	
		 	hashMap.put("p_printed_by",p_printed_by);
		 	hashMap.put("p_date_format",dateFormat); 
		 	hashMap.put("p_from_id",p_from_id); 
		 	hashMap.put("p_to_id",p_to_id); 
		 	hashMap.put("p_report_format",reportformat );
		 	hashMap.put("p_loan_id",p_loan_id );
		 	
		 	logger.info("Rport name : "+reportPath+reportName+".jasper");
		 	InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+reportName+".jasper");
			JasperPrint jasperPrint = null;
						
				try
				{
					jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
					if(reportformat.equals("P"))
						methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
					if(reportformat.equals("E"))				
						methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
					if(reportformat.equals("H"))				
						methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
					hashMap.clear();
					hashMap=null;
					p_company_logo=null;
					p_report_name=null;
					p_instrument_type=null;
					p_status_type=null;
					p_loan_no=null;
					p_printed_date=null;
					p_printed_author_date=null;
					p_from_author_date=null;
					p_to_author_date=null;
					p_branch=null;
					p_status=null;
					p_stage=null;
					p_from_number=null;
					p_to_number=null;
					P_deal_loan=null;
					p_form_number=null;
					query=null;
					p_company_name=null;
					p_printed_by=null;
					dateFormat=null;
					p_from_id=null;
					p_to_id=null;
					reportformat=null;
					p_loan_id=null;
				}
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
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
	public void methodForHTML(Object reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint,HttpServletRequest request)throws Exception
	{
		PrintWriter out=response.getWriter();
	    out.append("<head><script type='text/javascript' src='"+request.getContextPath()+"/js/report/report.js'></script></head>");
	   	response.setContentType("text/html");
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
		date=null;
		return result;
	}
	
}
	


