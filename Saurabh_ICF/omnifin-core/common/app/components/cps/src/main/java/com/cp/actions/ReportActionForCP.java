package com.cp.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

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
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.DaoImplInstanceFactory;
import com.itextpdf.text.pdf.PdfWriter;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.Menu;
import com.login.roleManager.UserObject;
import com.cm.dao.ReportsDAO;
import com.connect.ConnectionDAO;

public class ReportActionForCP extends Action {

	private static final Logger logger = Logger.getLogger(ReportActionForCP.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	
	{	
		ReportsDAO dao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
		logger.info("In ReportActionForCP.........");
		try
		{
				HttpSession session = request.getSession();
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
				String p_company_name="";
				String userName="";
				String p_business_date="";
				String userId="";
				String p_company_short_code="";
				if(userobj !=null)
				{
					p_company_name=userobj.getConpanyName()+" ";			
					userName = userobj.getUserName()+" ";
					p_business_date=userobj.getBusinessdate();
					userId = userobj.getUserId();
					p_company_short_code=userobj.getCompanyShortCode()+" ";
				}
				else
				{
					logger.info(" in execute method of ReportActionForCP  action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				DynaActionForm daf=(DynaActionForm)form;
				ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
				String dateFormat=resource.getString("lbl.dateInDao");
				String dbType=resource.getString("lbl.dbType");
				String reportName=(String)daf.get("reportName");
				String reporttype=(String)daf.get("reportformat");
				String p_branch_type=(String)daf.get("branch");
				String branchName=(String)daf.get("branchid");
				String p_branch_id=(String)daf.get("lbxBranchId");	
				String p_deal=(String)daf.get("specificDealNo");
				String p_lead=(String)daf.get("specificLeadNo");
				String p_deal_from=(String)daf.get("lbxDealNo");
				String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
				String p_deal_id="";
				String p_lead_id="";
				String deal_id="";
				String p_status="";
				String loanType=(String)daf.get("loanno");
				String fromDeal=(String)daf.get("fromDeal");
				String toDeal=(String)daf.get("toDeal");
				String p_deal_to=(String)daf.get("lbxDealTo");
				String p_printed_deal="";
				String fromDate=(String)daf.get("fromDate");
				String toDate=(String)daf.get("toDate");
				String branchDet=(String)daf.get("branchDet");
				String leadBranchId=(String)daf.get("leadBranchId");
				String rmAllo=(String)daf.get("rmAllo");
				String lbxUserId=(String)daf.get("lbxUserId");
				String p_p_date="";
				String p_rm="";
				String p_branch="";
				String p_lead_branch_id="";
				String p_print_generation_date="";
				String p_from_generation_date="";
				String p_to_generation_date="";
				String p_rm_ID="";
				String user_id=(String)daf.get("userID");
				String specificDeal_id="";
				String status=(String)daf.get("status1");
				String p_dealRange="";
				String p_dateRange="";
				StringBuffer query = new StringBuffer();
				String reportPath="/reports/";
				String sub_reports_location=getServlet().getServletContext().getRealPath("/")+"reports\\";
				String SUBREPORT_DIR=getServlet().getServletContext().getRealPath("/")+"reports\\";
				String customer_detail_location=getServlet().getServletContext().getRealPath("/")+"reports\\";
				String p_loan_id="";	
				String p_checkbox_path=getServlet().getServletContext().getRealPath("/")+"reports//";
				String p_imageCheckbox=getServlet().getServletContext().getRealPath("/")+"reports/imageCheckbox.bmp";//added for ACH
				String ach_capturing_id="";//added for ACH
				String specificDealNo1="";//added for ACH
				String p_stage=(String)daf.get("level");
				String p_type=resource.getString("productCategory");
				String approvalFromDate="";
				String approvalToDate="";
				String productIds=(String)daf.get("productIds");
				String passwordPdf=(String)daf.get("passwordPdf");// shubham
				logger.info("passwordPdf...    : "+passwordPdf);
				String products=productIds;
				String functionId=(String)session.getAttribute("functionId");
				//Changes for Loan Sanction Letter
				String p_Loan_no = (String)daf.get("specificLoanNo");
				String lbxLoan_id = (String)daf.get("lbx_loan_from_id");
				String p_Loan_Button = (String)daf.get("specificLoanButton");
			//	String p_bank_id=(String)daf.get("lbxBankID");
				//String p_branch_id=(String)daf.get("lbxBranchID");
				String productCategory=(String)daf.get("producTCategory");
				logger.info("productCategory"+productCategory);
				logger.info("functionId"+session.getAttribute("functionId").toString());
				int funid=Integer.parseInt(functionId);		
				if(funid==4001231)
				{
					reportName=request.getParameter("reportName");
					
				}
				if(!CommonFunction.checkNull(p_branch_id).trim().equalsIgnoreCase(""))
				{
					logger.info("p_branch_id::"+p_branch_id);
					p_branch_id=""+p_branch_id.replace("|",",");
					logger.info("p_branch_id (new):::"+p_branch_id);
					}logger.info("p_branch_id"+p_branch_id);
				logger.info("p_branch_type"+p_branch_type);
				

				if(CommonFunction.checkNull(reportName).trim().equals("tat_report")&& !(CommonFunction.checkNull(products).trim().equalsIgnoreCase("")))
				{
					products=products.replace("|","','");
					products="'"+products+"'";
				}
				String p_app_path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
				
				if(dbType.equalsIgnoreCase("MSSQL"))
				{
					reportPath=reportPath+"MSSQLREPORTS/";
					sub_reports_location=sub_reports_location+"MSSQLREPORTS\\";
					SUBREPORT_DIR=SUBREPORT_DIR+"MSSQLREPORTS\\";
					customer_detail_location=customer_detail_location+"MSSQLREPORTS\\";					
				}
				else
				{
					reportPath=reportPath+"MYSQLREPORTS/";
					sub_reports_location=sub_reports_location+"MYSQLREPORTS\\";
					SUBREPORT_DIR=SUBREPORT_DIR+"MYSQLREPORTS\\";
					customer_detail_location=customer_detail_location+"MYSQLREPORTS\\";
					
				}				
								
				if(request.getParameter("repayAtCP")!=null)
				{
						reporttype="P";
					    reportName="deal_repayment_calculation";
					    p_deal_from=request.getParameter("repayAtCP");
				}
				if(request.getParameter("repayAtCM")!=null)
				{
						reporttype="P";
					    reportName="rp_repayment_calculation";
					    p_loan_id=request.getParameter("repayAtCM");
				}
				//	Himanshu Verma		Changes started for offer letter generation from underwriter
				
				///Loan_application_form_parameter
				String  appFormTypeFlag="";
				if(reportName.equalsIgnoreCase("LOAN_APPLICATION_FORM")){
				String appFormType="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='APPLICATION_FORM_TYPE'";
				appFormTypeFlag= ConnectionDAO.singleReturn(appFormType);
				request.setAttribute("appFormTypeFlag", appFormTypeFlag);
				}
				Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
				Map<Object,Object> hashMap = new HashMap<Object,Object>();
								
				String p_printed_date =p_business_date;
				String fromDate1=null;
				String toDate1=null;
				if(fromDate != null)
				{
					if(!(fromDate.trim().equals("")))						
						fromDate1=fromDate;
					if(!(toDate.trim().equals("")))	
						toDate1=toDate;
				}
				String p_p_date_from="";
				String p_p_date_to="";
				String p_as_on_date="";
						
				if(fromDate != null)
				{
					if(!(fromDate.trim().equals("")))
					{
						p_p_date_from=formate(fromDate);
						p_p_date_to=formate(toDate);			
					}
					p_p_date=" From   "+p_p_date_from+"   To   "+p_p_date_to+" ";
					if(fromDate.length()==0)
						p_p_date="All";
				}//richa changes
				 functionId=(String)session.getAttribute("functionId");
				 funid=Integer.parseInt(functionId);
				if(funid==4001231 && reportName.equalsIgnoreCase("subsequentDisbursementVoucher"))
				{
					if(request.getParameter("loanId")!=null)
					{
						p_loan_id=request.getParameter("loanId");
						reportName=CommonFunction.checkNull(dao.getDvReport(p_loan_id));
						reporttype="P";
						
						logger.info("p_loan_id...    : "+p_loan_id);
					}
				}
				//richa changes ends
				if(reportName.equalsIgnoreCase("deal_details_header"))
				{
					p_deal_id=p_deal_from;
					reporttype="P";
					sub_reports_location=sub_reports_location+"dealSubReports\\";
					SUBREPORT_DIR=SUBREPORT_DIR+"dealSubReports\\";
				}
				//Changes for Loan Sanction Letter
			    if (reportName.equals("sanction_letter_after_loan"))
			    {
			       reporttype = "P";
			       p_loan_id = lbxLoan_id;
			    }
			    //Changes For Loan Sanction Letter
			  //pooja Changes for deal Sanction Letter
			    if (reportName.equals("sanction_letter_after_deal"))
			    {
			       reporttype = "P";
			       p_deal_id=p_deal_from;
			       String prodName = ConnectionDAO.singleReturn(new StringBuilder().append("select DEAL_PRODUCT from cr_deal_loan_dtl where deal_id='").append(p_deal_id).append("' ").toString());
			          if (prodName.equals("LAP")) {
			            reportName = "sanction_letter_after_deal_LAP";
			          }
			          else if (prodName.equals("BIL"))
			          {
			            reportName = "sanction_letter_after_deal_BIL";
			          }
			          hashMap.put("p_deal_id",p_deal_id);
			          logger.info("prodName...    : "+prodName);
			          logger.info("p_deal_id...    : "+p_deal_id); 
			          
			    }
			    

			      if (reportName.equals("SME_Sanction_Letter_Report_For_CP"))
			      {
			        reporttype = "P";
			        p_deal_id = p_deal_from;
			        String prodName = ConnectionDAO.singleReturn("select DEAL_PRODUCT from cr_deal_loan_dtl where deal_id='" + p_deal_id + "' ");

			        hashMap.put("p_deal_id", p_deal_id);
			        logger.info("prodName...    : " + prodName);
			        logger.info("p_deal_id...    : " + p_deal_id);
			      }
			    
			  //pooja Changes for deal Sanction Letter
				if(reportName.equalsIgnoreCase("termSheet_Report"))
				{
					p_deal_id=p_deal_from;
					reporttype="P";					
				}
				//mradul starts for new rpt 			
				if(reportName.equalsIgnoreCase("Branch_credit_login_Report"))
				{					
					approvalFromDate=CommonFunction.changeFormat(fromDate1);
					approvalToDate=CommonFunction.changeFormat(toDate1);				
				}
				if(reportName.equals("Applicants_Linked_To_Deals"))
				{
			 status=(String)daf.get("status2");
			if(status.equals("All"))
				p_status="All ";
			if(status.equals("A"))
				p_status="Approved ";
			if(status.equals("X"))
				p_status="Deleted ";
			if(status.equals("P"))
				p_status="Pending ";
			
			if(loanType != null)
			{
				if(loanType.equals("A"))
					p_printed_deal="All ";
				else
					p_printed_deal="From   "+fromDeal+"   To   "+toDeal+" ";
			}
			}
				if(reportName.equals("FI_REPORT")){
					if(loanType != null)
					{
						if(loanType.equals("A"))
							p_printed_deal="All ";
						else
							p_printed_deal="From   "+fromDeal+"   To   "+toDeal+" ";
					}
				}
	
				if ((reportName.equals("Deals_Pending_Approved_Approved_Rejected_Cancelled_During_a_Period")) || (reportName.equals("Deals_Pending_Approved_Rejected_Cancelled_During_a_Period")))
				{
					if(status.equals("All"))
						p_status="All ";
					if(status.equals("A"))
						p_status="Approved ";
					if(status.equals("R"))
						p_status="Rejected ";
					if(status.equals("C"))
						p_status="Cancelled ";
					if(status.equals("P"))
						p_status="Pending ";
					
					if(loanType != null)
					{
						if(loanType.equals("A"))
							p_printed_deal="All ";
						else
							p_printed_deal="From   "+fromDeal+"   To   "+toDeal+" ";
					}
				}
				
				if(reportName.equalsIgnoreCase("deal_dump_report" ))
				{
					reporttype="E";
				}
				if(reportName.equalsIgnoreCase("lead_Report" ))
				{
				/*	if(CommonFunction.checkNull(lbxUserId).equalsIgnoreCase(""))
						p_rm="All";
					else
					{
						p_rm=rmAllo;
						p_rm_ID=lbxUserId;
					}*/
	                  
				    if(CommonFunction.checkNull(branchDet).equalsIgnoreCase(""))
					{
						p_branch="All";
						p_lead_branch_id="";
					}
					else
					{
						p_branch=branchDet;
						p_lead_branch_id=leadBranchId;
					}
					if(CommonFunction.checkNull(fromDate).equalsIgnoreCase("")&& CommonFunction.checkNull(toDate).equalsIgnoreCase(""))
					{
						p_print_generation_date="All";
					}
					else
					{
						p_print_generation_date=" From   :  "+formate(fromDate)+"   To  :  "+formate(toDate);
						p_from_generation_date=fromDate;
						p_to_generation_date=toDate;
						
					}
				}
				if(reportName.equalsIgnoreCase("deal_Movement_Report" ))
				{
					p_deal_id=p_deal_from;
					reporttype="P";
					
				}
				//Rohit Changes starts for ACH
				if(reportName.equalsIgnoreCase("ACH_SLIP_REPORT" ) || reportName.equalsIgnoreCase("ACH_SLIP_REPORT_DEAL" ))
				{
					if(request.getParameter("dealId")!=null)
					{
						p_deal_id=request.getParameter("dealId");
						reporttype="P";
						logger.info("deal_id : " + p_deal_id);
					}
					if(request.getParameter("hidDealNo")!=null)
					{
						p_deal_id=request.getParameter("hidDealNo");
						reporttype="P";
						logger.info("deal_id for DealNo : " + p_deal_id);
					}
					
				}	//Rohit Changes End
				if(reportName.equalsIgnoreCase("LEAD_FOLLOWUP_REPORT" ))
				{
					p_lead_id = p_lead;
					logger.info("Lead Id : " + p_lead_id);
					reporttype="P";
				}	
				//code added by neeraj
				if(reportName.equalsIgnoreCase("credit_appraisal_memo"))
				{
					if(request.getParameter("undDealId")!=null)
					{
						p_deal_id=request.getParameter("undDealId");
						reporttype="P";
					}
					else
						p_deal_id=p_deal_from;
					
					reportName=CommonFunction.checkNull(dao.getCAMReport(p_deal_id));	
					if(CommonFunction.checkNull(reportName).trim().equalsIgnoreCase(""))
					{
						if(request.getParameter("undDealId")!=null)
						return mapping.findForward("uw");
						else
						return mapping.findForward("success");
					}
					if(CommonFunction.checkNull(reportName).trim().equalsIgnoreCase("MMCAMReport"))
					{
						boolean elStatus=dao.getEligibilityRecord(p_deal_id,p_business_date,userId);
					}
					customer_detail_location=customer_detail_location+"dealSubReports\\";
					SUBREPORT_DIR=SUBREPORT_DIR+"camSubReports\\";
				}
                //neeraj space end
						
				if ((reportName.trim().equalsIgnoreCase("Deals_Pending_Approved_Approved_Rejected_Cancelled_During_a_Period")) || (reportName.trim().equalsIgnoreCase("Deals_Pending_Approved_Rejected_Cancelled_During_a_Period")))
				{
					fromDate1=CommonFunction.changeFormat(fromDate1);
					toDate1=CommonFunction.changeFormat(toDate1);
				}
				if(reportName.equalsIgnoreCase("tat_report" ))
				{
					query.append(" select a.LEAD_ID,a.DEAL_ID,a.deal_no,CONCAT(c.customer_name,' ')customer_name,concat(pr.PRODUCT_DESC,' ')product, ");
					query.append(" concat(sc.SCHEME_DESC,' ')scheme,if(a.deal_current_approval_level>1,l.DEAL_SANCTION_AMOUNT,l.DEAL_LOAN_AMOUNT)loan_amount, ");
					query.append(" IFNULL(b.DC,0)DC,IFNULL(b.QC,0)QC,IFNULL(b.BSA,0)BSA,IFNULL(b.CVC,0)CVC,IFNULL(b.FAC,0)FAC,IFNULL(b.FFC,0)FFC,IFNULL(b.FVC,0)FVC, ");
					query.append(" IFNULL(b.FVI,0)FVI,IFNULL(b.POC,0)POC,IFNULL(b.TCC,0)TCC,IFNULL(b.UNC,0)UNC,(IFNULL(b.LOAN,0)+IFNULL(b.DISBURSAL,0))DISBURSAL,IFNULL(b.LEAD,0)LEAD ");
					query.append(" ,app.APPROVAL_DATE,ld.LEAD_GENERATION_DATE,a.DEAL_INITIATION_DATE ");
					query.append(" from cr_deal_dtl a ");
					query.append(" join cr_deal_loan_dtl l on(a.deal_id=l.deal_id) ");
					query.append(" join cr_product_m pr on(pr.PRODUCT_ID=l.DEAL_PRODUCT) ");
					query.append(" join cr_scheme_m sc on(sc.SCHEME_ID=l.DEAL_SCHEME) ");					
					query.append(" left join stage_holding_time_dtl b on(a.deal_id=b.deal_id) ");
					query.append(" left join cr_lead_dtl ld on(ld.LEAD_ID=a.LEAD_ID) ");
					query.append(" left join ");
					query.append(" ( ");
					query.append(" 		select deal_id,min(APPROVAL_DATE)APPROVAL_DATE "); 
					query.append(" 		from CR_DEAL_LEVEL_APPROVAL_DTL "); 
					query.append(" 		where approval_level=1 and level_decision='A' "); 
					query.append(" 		group by deal_id "); 
					query.append(" )app on(a.deal_id=app.deal_id) ");					
					query.append(" left join cr_deal_customer_m c on(a.deal_customer_id=c.customer_id) where 1=1 ");					
					if(CommonFunction.checkNull(p_branch_type).trim().equalsIgnoreCase("Specific"))  
					{
						//p_branch=branchName;
						query.append(" AND a.DEAL_BRANCH IN ("+CommonFunction.checkNull(p_branch_id).trim()+")");
					}
					else
					{
						query.append(" AND a.DEAL_BRANCH IN (SELECT BRANCH_ID FROM sec_user_branch_dtl where  user_id= '"+CommonFunction.checkNull(user_id).trim()+"')");
						p_branch="All";
					}
					
					if(!CommonFunction.checkNull(productCategory).trim().equalsIgnoreCase("all"))  
					{
						//p_branch=branchName;
						query.append(" AND l.DEAL_PRODUCT_CATEGORY = ('"+CommonFunction.checkNull(productCategory).trim()+"')");
						
					}
					
					//p_branch_id=p_branch+" ";
					if(!CommonFunction.checkNull(status).trim().equalsIgnoreCase("All"))
						query.append(" AND a.REC_STATUS='"+CommonFunction.checkNull(status).trim()+"'");
					
					if(status.equals("All"))
						p_status="All ";
					if(status.equals("A"))
						p_status="Approved ";
					if(status.equals("R"))
						p_status="Rejected ";
					if(status.equals("C"))
						p_status="Cancelled ";
					if(status.equals("P"))
						p_status="Pending ";					
									
					if(CommonFunction.checkNull(loanType).trim().equalsIgnoreCase("R"))
					{
						p_dealRange="From : "+fromDeal+"  To : "+toDeal+" ";
						query.append(" AND a.DEAL_ID>='"+CommonFunction.checkNull(p_deal_from).trim()+"' AND a.DEAL_ID<='"+CommonFunction.checkNull(p_deal_to).trim()+"'");
					}
					else
					{
						p_dealRange="All ";
					}
					if(!CommonFunction.checkNull(fromDate).trim().equalsIgnoreCase(""))
					{
						p_dateRange="From : "+formate(fromDate)+"  To : "+formate(toDate)+" ";
						query.append(" AND DATE(DEAL_INITIATION_DATE)>='"+CommonFunction.changeFormat(CommonFunction.checkNull(fromDate).trim())+"'");
						query.append(" AND DATE(DEAL_INITIATION_DATE)<='"+CommonFunction.changeFormat(CommonFunction.checkNull(toDate).trim())+"'");
					}
					else
					{
						p_dateRange="All ";
					}
					if(!CommonFunction.checkNull(products).trim().equalsIgnoreCase(""))
					{
						query.append(" AND l.deal_product in("+CommonFunction.checkNull(products).trim()+") ");
						products=products+" ";
					}
					else
					{
						products="All ";
					}
					query.append(" ORDER BY a.DEAL_ID ");
					logger.info("Report Query  : "+query.toString());
				}
				if(reportName.equalsIgnoreCase("LOAN_APPLICATION_FORM")){
					if(StringUtils.endsWithIgnoreCase(appFormTypeFlag, "SATIN")){	
						reportName="Loan_Application_Form_Satin";
						reporttype="P";
							p_deal_id=p_deal_from;
					}
					else if(StringUtils.endsWithIgnoreCase(appFormTypeFlag, "ART"))
					{
						reporttype="P";
							p_deal_id=p_deal_from;
					}
				}
				functionId=CommonFunction.checkNull(session.getAttribute("functionId")).trim();
				hashMap.put("functionId",functionId);
				hashMap.put("products",products);
				hashMap.put("query",query.toString());
				hashMap.put("p_dealRange",p_dealRange);
				hashMap.put("p_dateRange",p_dateRange);

				hashMap.put("p_rm",p_rm);
				hashMap.put("p_rm_ID",p_rm_ID);				
				hashMap.put("p_lead_branch",p_branch);
				hashMap.put("p_lead_branch_id",p_lead_branch_id);
				hashMap.put("p_print_generation_date",p_print_generation_date);
				hashMap.put("p_from_generation_date",p_from_generation_date);
				hashMap.put("p_to_generation_date",p_to_generation_date);
				hashMap.put("p_company_logo",p_company_logo);
				hashMap.put("p_checkbox_path",p_checkbox_path);	
				hashMap.put("p_company_name",p_company_name);
				hashMap.put("p_status",p_status);
				hashMap.put("status",status);
				hashMap.put("p_p_date_from",p_p_date_from);
				hashMap.put("p_p_date_to", p_p_date_to);
				hashMap.put("p_as_on_date", p_as_on_date);
			    hashMap.put("p_business_date",p_business_date);
				hashMap.put("p_printed_by", userName);
				hashMap.put("p_branch_type", p_branch_type);
				hashMap.put("p_branch", p_branch_id);
				hashMap.put("p_loan_no_type", loanType);
				hashMap.put("p_deal_from", p_deal_from);
				hashMap.put("p_deal_to", p_deal_to);
				hashMap.put("p_printed_date", p_printed_date);
				hashMap.put("p_date_from",fromDate1);
				hashMap.put("p_date_to", toDate1);
				hashMap.put("p_loan_type", loanType);
				hashMap.put("p_p_date", p_p_date);
				hashMap.put("p_printed_deal", p_printed_deal);
				hashMap.put("sub_reports_location", sub_reports_location);
				hashMap.put("SUBREPORT_DIR", SUBREPORT_DIR);
				hashMap.put("p_deal_id", p_deal_id);
				hashMap.put("p_lead_id", p_lead_id);
				hashMap.put("p_date_format", dateFormat);
				hashMap.put("p_user_id",userId );
				hashMap.put("p_date",p_printed_date );
				hashMap.put("p_report_format",reporttype );		
				hashMap.put("user_id",user_id );	
				hashMap.put("customer_detail_location",customer_detail_location );
				hashMap.put("deal_id",p_deal_id );
				hashMap.put("p_loan_id",p_loan_id );
				hashMap.put("p_approval_date_from",approvalFromDate);
				hashMap.put("p_approval_date_to", approvalToDate);
				hashMap.put("p_app_path",p_app_path );		
				hashMap.put("p_imageCheckbox",p_imageCheckbox);//added for ACH
				hashMap.put("p_stage",p_stage);
				hashMap.put("level",p_stage);
				hashMap.put("p_type",p_type);
				hashMap.put("p_company_short_code",p_company_short_code);
				hashMap.put("ach_capturing_id",ach_capturing_id);//added for ACH
				//hashMap.put("p_branch_id",p_branch_id);
				hashMap.put("p_branch_type", p_branch_type);
				hashMap.put("productCategory",productCategory );	
				if(CommonFunction.checkNull(reporttype).trim().equalsIgnoreCase("P"))
					hashMap.put("IS_IGNORE_PAGINATION",false);
				else
					hashMap.put("IS_IGNORE_PAGINATION",true);
				
//				logger.info("user_id                    : "+ user_id);
//				logger.info("reportPath                 : "+ reportPath);
//				logger.info("reportName                 : "+ reportName);
//				logger.info("customer_detail_location   : "+ customer_detail_location);
//				logger.info("SUBREPORT_DIR              : "+ SUBREPORT_DIR);
				
				
				
				InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+reportName+".jasper");
				
				JasperPrint jasperPrint = null;
				try
				{
					jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
					// shubham
					if(reporttype.equals("P")){  
						if(CommonFunction.checkNull(passwordPdf).equalsIgnoreCase("Y") && !CommonFunction.checkNull(p_deal_from).equalsIgnoreCase("")){
							String passwordQuery="select date_format(C.CUSTOMER_DOB,'%d%m%Y') FROM cr_deal_dtl D LEFT JOIN cr_deal_customer_m C ON D.DEAL_CUSTOMER_ID =C.CUSTOMER_ID where deal_id='"+p_deal_from+"'";
		            		String pass=ConnectionDAO.singleReturn(passwordQuery);
		            		logger.info("passwordQuery: "+passwordQuery);
		            		logger.info("pass: "+pass);
		            		String ownerPasswordQuery="select ifnull(primary_phone,'') from cr_deal_dtl l join gcd_customer_m g on g.customer_id=l.deal_customer_id Join com_address_m a on a.BPID=g.customer_id and a.BPTYPE='CS' and a.COMMUNICATION_ADDRESS='Y' where deal_id='"+p_deal_from+"' limit 1";
		            		String owner=ConnectionDAO.singleReturn(ownerPasswordQuery);
		            		if(CommonFunction.checkNull(owner).equalsIgnoreCase("")){
		            			owner=pass;
		            		}
		            		methodForPasswordDealPDF(reportName, hashMap, connectDatabase, response, jasperPrint, request,owner , pass);
						}else{
			
						methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
					}	
					}
					/*end*/
					if(reporttype.equals("E"))				
						methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
					if(reporttype.equals("H"))				
						methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					// Code to insert report name and access log starts
					userId = CommonFunction.checkNull(userobj.getUserId());
					String moduleID=null;
					List<Menu> modid=(List<Menu>) session.getAttribute("leftsidemenulist");
					if(modid!=null)
					{
				        	 moduleID = modid.get(0).getModuleID();
				        	logger.info("moduleID"+moduleID);
					}
					String pageid = CommonFunction.checkNull(session.getAttribute("functionId")).toString();
					String bDate = userobj.getBusinessdate();
					dao.saveFunctionLogData(userId, moduleID, pageid, bDate, "", "", reportName, hashMap.toString());
					// Code to insert report name and access log ends
					
					strFlag=null;
					p_company_name=null;
					userName=null;
					p_business_date=null;
					userId=null;
					dateFormat=null;
					dbType=null;
					reportName=null;
					reporttype=null;
					p_branch_type=null;
					branchName=null;
					p_branch_id=null;
					p_deal=null;
					p_lead=null;
					p_deal_from=null;
					p_company_logo=null;
					p_deal_id=null;
					p_lead_id=null;
					deal_id=null;
					p_status=null;
					loanType=null;
					fromDeal=null;
					toDeal=null;
					p_deal_to=null;
					p_printed_deal=null;
					fromDate=null;
					toDate=null;
					branchDet=null;
					leadBranchId=null;
					rmAllo=null;
					lbxUserId=null;
					p_p_date=null;
					p_rm=null;
					p_branch=null;
					p_lead_branch_id=null;
					p_print_generation_date=null;
					p_from_generation_date=null;
					p_to_generation_date=null;
					p_rm_ID=null;
					user_id=null;
					specificDeal_id=null;
					status=null;
					p_dealRange=null;
					p_dateRange=null;
					query=null;
					reportPath=null;
					sub_reports_location=null;
					SUBREPORT_DIR=null;
					customer_detail_location=null;
					p_loan_id=null;
					approvalFromDate=null;
					approvalToDate=null;
					productIds=null;
					products=null;
					p_app_path=null;
					p_printed_date=null;
					fromDate1=null;
					toDate1=null;
					p_p_date_from=null;
					p_p_date_to=null;
					p_as_on_date=null;
					ach_capturing_id=null;
					daf.reset(mapping, request);
					ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
					hashMap.clear();
					
				}
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
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
		public  void methodForHTML(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint,HttpServletRequest request)throws Exception
		{
			PrintWriter out=response.getWriter();
		    out.append("<head><script type='text/javascript' src='"+request.getContextPath()+"/js/report/report.js'></script></head>");
		   	response.setContentType("text/html");
		   	
			String htmlReportFileName=reportName+".html";
			JRHtmlExporter exporter = new JRHtmlExporter();			
			response.setContentType("text/html");
	        request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,jasperPrint);			
			float f1=1.4f;
			Map imagesMap = new HashMap();
	        request.getSession().setAttribute("IMAGES_MAP", imagesMap);
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN ,Boolean.FALSE);
	        exporter.setParameter(JRHtmlExporterParameter.IGNORE_PAGE_MARGINS ,Boolean.TRUE); 
	        exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
	        exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
	        exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
	        exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML,"");
	        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
	        exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);
	        exporter.setParameter(JRHtmlExporterParameter.ZOOM_RATIO ,f1);
	        ServletContext context = this.getServlet().getServletContext();
	        File reportFile = new File(context.getRealPath("/reports/"));
	        String image = reportFile.getPath();
	        exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,image);
	        exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,image + "/");
	        exporter.exportReport();     
		}	
		
		/*changes by shubham*/
		public void methodForPasswordDealPDF(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint, HttpServletRequest request,String userPassword,String ownerPassword)throws Exception
	  	{			
	  		JRPdfExporter exporter = new JRPdfExporter();
	  			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	  			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, request.getRealPath("/reports") + "/" +reportName+".pdf");
	  			exporter.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE);
	  			exporter.setParameter(JRPdfExporterParameter.IS_128_BIT_KEY, Boolean.TRUE);
	  			exporter.setParameter(JRPdfExporterParameter.USER_PASSWORD,userPassword);
	  			exporter.setParameter(JRPdfExporterParameter.OWNER_PASSWORD, ownerPassword);
	  			exporter.setParameter(
	  		    JRPdfExporterParameter.PERMISSIONS, 
	  		    new Integer(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING)
	  		    );
	  		  	exporter.exportReport();
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

		
		/*end*/
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

	
		
		