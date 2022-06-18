package com.cp.actions;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cm.dao.ReportsDAO;
import com.connect.ConnectionDAO;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.connect.PrepStmtObject;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.Menu;
import com.login.roleManager.UserObject;
import com.cp.dao.CreditProcessingDAO;
import com.cp.dao.DealSanctionLetterDAO;
import com.cp.vo.DealMovementVo;
import com.cp.vo.DealSanctionLetterVo;
import com.connect.DealSanctionLetterInstanceFactory;
import com.connect.CommonFunction;

import org.apache.struts.action.DynaActionForm;

import com.connect.ConnectionReportDumpsDAO;

public class DealSanctionLetterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(DealSanctionLetterDispatchAction.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dbType=resource.getString("lbl.dbType");
	String dateFormat=resource.getString("lbl.dateInDao");

	
	
	public ActionForward openLoanSanctionLetter(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
	HttpSession session = request.getSession();
	boolean flag=false;
	UserObject userobj=(UserObject)session.getAttribute("userobject");
	String userId = "";
	String branchId = "";
	if (userobj != null) {
		userId = userobj.getUserId();
		branchId = userobj.getBranchId();
	}else{
		logger.info("here in LoanSanctionLetterDispatchAction method of DealSanctionLetterDAOImpl action the session is out----------------");
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
	DealSanctionLetterDAO dao=(DealSanctionLetterDAO)DealSanctionLetterInstanceFactory.getDaoImplInstance(DealSanctionLetterDAO.IDENTITY);
	//logger.info("Implementation class: "+dao.getClass()); 			
	DynaValidatorForm LoanSanctionLetterDynaValidatorForm = (DynaValidatorForm) form;
	DealSanctionLetterVo vo = new DealSanctionLetterVo();
	org.apache.commons.beanutils.BeanUtils.copyProperties(vo,LoanSanctionLetterDynaValidatorForm);
	
	vo.setUserId(userId);
	vo.setBranchId(branchId);		
		String functionId="";
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		request.setAttribute("functionId", functionId);

	return mapping.findForward("success");
}
	
	public ActionForward reportSaveGenerateAction(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		
		logger.info("In reportSaveGenerateAction(execute)");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		String p_company_name="";
		String userName="";
		String p_business_date="";
		
		if(userobj!=null)
		{
				branchId=userobj.getBranchId();	
				p_company_name=userobj.getConpanyName()+" ";			
				userName = userobj.getUserName()+" ";
				p_business_date=userobj.getBusinessdate();
				userId = userobj.getUserId();
		}else{
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

		DealSanctionLetterDAO dao=(DealSanctionLetterDAO)DealSanctionLetterInstanceFactory.getDaoImplInstance(DealSanctionLetterDAO.IDENTITY);			
		DynaValidatorForm LoanSanctionLetterDynaValidatorForm = (DynaValidatorForm) form;// TODO
		DealSanctionLetterVo vo = new DealSanctionLetterVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,LoanSanctionLetterDynaValidatorForm);
		String dealId=request.getParameter("lbxDealNoHID");
		String msg = "";
		
		vo.setUserId(userId);
		vo.setMakerDate(p_business_date);
		vo.setDealId(dealId);
		logger.info("dealId ---------------------"+dealId);

				String query1="Select count(1) from CR_LOANSANCTION_LETTER_DTL where deal_id="+dealId+"";
				logger.info("query1::::"+query1.toString());
				String recStatus1 =ConnectionDAO.singleReturn(query1.toString());
				if(!recStatus1.equalsIgnoreCase("0"))
				{
					ArrayList qryList=new ArrayList();	
					ArrayList qryList1=new ArrayList();
					PrepStmtObject deletePrepStmtObject=null;
					PrepStmtObject insertPrepStmtObject=null;
					StringBuilder	bufInsSql=null;
				    deletePrepStmtObject = new PrepStmtObject();
				    StringBuilder delsSql =	new StringBuilder();
				    insertPrepStmtObject = new PrepStmtObject();
				    bufInsSql =	new StringBuilder();
					bufInsSql.append("insert into CR_LOANSANCTION_LETTER_hst(REPORT_ID,DEAL_ID,LOAN_ID,CUSTOMER_ID,Insurance_Premium,ROI,Processing_Fees,Margin_Money_Amount,Margin_Money_Remarks,MAKER_ID,MAKER_DATE,total_loan_amount,monthly_roi,EMI,Loan_Amt,No_Of_Installment)");
					bufInsSql.append(" SELECT REPORT_ID,DEAL_ID,LOAN_ID,CUSTOMER_ID,Insurance_Premium,ROI,Processing_Fees,Margin_Money_Amount,Margin_Money_Remarks,MAKER_ID,MAKER_DATE,total_loan_amount,monthly_roi,EMI,Loan_Amt,No_Of_Installment FROM CR_LOANSANCTION_LETTER_DTL WHERE deal_id='"+(CommonFunction.checkNull(vo.getDealId()).trim())+ "'");
					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN approveNoOfDisb()del insert query1 ### "+insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);	
					ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					
					    delsSql.append("delete from CR_LOANSANCTION_LETTER_DTL where deal_id='"+(CommonFunction.checkNull(vo.getDealId()).trim())+ "'");
				    deletePrepStmtObject.setSql(delsSql.toString());
					logger.info("IN approveNoOfDisb() delete query1 ### "+deletePrepStmtObject.printQuery());
					qryList1.add(deletePrepStmtObject);
					ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);	
				}
				boolean saveLoanSanctionLetter = false;
				saveLoanSanctionLetter = dao.saveLoanSanctionLetter(vo);
				if(saveLoanSanctionLetter){
						msg = "saved";
						
						DynaActionForm daf=(DynaActionForm)form;
						ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
						String dateFormat=resource.getString("lbl.dateInDao");
						String reportName=("SME_Sanction_Letter_Report_For_CP");
						String reporttype=(String)daf.get("reportformat"); 
						String p_branch_type=(String)daf.get("branch"); 
						String p_deal=(String)daf.get("dealNo"); 
						String p_deal_from=(String)daf.get("lbxDealNoHID");
						String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp"; //D:\sme_jboss\jboss-4.2.3\server\default\.\deploy\OmniFin-4.0.0.ear\LMS-indostar-1.0.0.war\reports/logo.bmp
						String p_deal_id="";
						String p_lead_id="";
						String deal_id="";
						String p_status="";
						String user_id=userId;	 
						String reportPath="/reports/";
						String sub_reports_location=getServlet().getServletContext().getRealPath("/")+"reports\\"; //D:\sme_jboss\jboss-4.2.3\server\default\.\deploy\OmniFin-4.0.0.ear\LMS-indostar-1.0.0.war\reports\
						String SUBREPORT_DIR=getServlet().getServletContext().getRealPath("/")+"reports\\"; //D:\sme_jboss\jboss-4.2.3\server\default\.\deploy\OmniFin-4.0.0.ear\LMS-indostar-1.0.0.war\reports\
						String customer_detail_location=getServlet().getServletContext().getRealPath("/")+"reports\\"; //D:\sme_jboss\jboss-4.2.3\server\default\.\deploy\OmniFin-4.0.0.ear\LMS-indostar-1.0.0.war\reports\
						String p_imageCheckbox=getServlet().getServletContext().getRealPath("/")+"reports/imageCheckbox.bmp";//added for ACH
						String p_app_path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath(); //http://localhost:8080/OmniFin
						reportPath=reportPath+"MYSQLREPORTS/";
						sub_reports_location=sub_reports_location+"MYSQLREPORTS\\";
						SUBREPORT_DIR=SUBREPORT_DIR+"MYSQLREPORTS\\";
						customer_detail_location=customer_detail_location+"MYSQLREPORTS\\";
						Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
						Map<Object,Object> hashMap = new HashMap<Object,Object>();
						String p_printed_date =p_business_date;
						 reporttype = "P";
					        p_deal_id = p_deal_from;
					        String prodName = ConnectionDAO.singleReturn("select DEAL_PRODUCT from cr_deal_loan_dtl where deal_id='" + p_deal_id + "' ");

					        hashMap.put("p_deal_id", p_deal_id);
					        logger.info("prodName...    : " + prodName);
					        logger.info("p_deal_id...    : " + p_deal_id);
					    	hashMap.put("p_company_logo",p_company_logo);
							hashMap.put("p_company_name",p_company_name);
							hashMap.put("p_business_date",p_business_date);
							hashMap.put("p_printed_by", userName); 
							hashMap.put("p_branch_type", p_branch_type); 
							hashMap.put("p_deal_from", p_deal_from); 
							hashMap.put("p_printed_date", p_printed_date); 
							hashMap.put("sub_reports_location", sub_reports_location);
							hashMap.put("SUBREPORT_DIR", SUBREPORT_DIR);
							hashMap.put("p_deal_id", p_deal_id);
							hashMap.put("p_date_format", dateFormat);
							hashMap.put("p_user_id",userId );
							hashMap.put("p_date",p_printed_date );
							hashMap.put("p_report_format",reporttype );		
							hashMap.put("user_id",userId );
							hashMap.put("customer_detail_location",customer_detail_location );
							hashMap.put("deal_id",p_deal_id );
							hashMap.put("p_app_path",p_app_path );		
							hashMap.put("p_imageCheckbox",p_imageCheckbox);
							if(CommonFunction.checkNull(reporttype).trim().equalsIgnoreCase("P"))
								hashMap.put("IS_IGNORE_PAGINATION",false);
							else
								hashMap.put("IS_IGNORE_PAGINATION",true);
							InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+reportName+".jasper");
							
							JasperPrint jasperPrint = null;
							try
							{
								jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
									methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
													}
							catch(Exception e)
							{
								e.printStackTrace();
							}
							finally
							{
								strFlag=null;
								p_company_name=null;
								userName=null;
								p_business_date=null;
								userId=null;
								dateFormat=null;
								dbType=null;
								reporttype=null;
								p_branch_type=null;
								p_deal=null;
								p_deal_from=null;
								p_company_logo=null;
								p_deal_id=null;
								p_lead_id=null;
								deal_id=null;
								p_status=null;
								user_id=null;
								reportPath=null;
								sub_reports_location=null;
								SUBREPORT_DIR=null;
								customer_detail_location=null;
								p_app_path=null;
								p_printed_date=null;
								daf.reset(mapping, request);
								ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
								hashMap.clear();
								
							}
							return null;
						
				}else
				{
					msg = "notSaved";
				}
				request.setAttribute("msg", msg);
				
				ArrayList loanSanctionInfo=new ArrayList();
				loanSanctionInfo.add(vo);
				request.setAttribute("loanSanctionInfo", loanSanctionInfo);
				
				
			return mapping.findForward("success");
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
}