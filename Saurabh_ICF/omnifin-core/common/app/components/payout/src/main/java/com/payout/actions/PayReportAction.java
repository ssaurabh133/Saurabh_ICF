package com.payout.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
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

import com.business.PayoutCilent.PayoutBusinessMasterSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.payout.vo.ActivityMasterVO;
import com.payout.vo.PayReportVO;



public class PayReportAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(PayReportAction.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	public ActionForward openBpListReportJsp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In PayReportAction........openBpListReportJsp()......");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here openBpListReportJsp method of PayReportAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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
			/*else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}*/
			return mapping.findForward("logout");
		}
	  
		PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
           
		  ArrayList<ActivityMasterVO> sourceList=payMaster.sourceList();
	      request.setAttribute("sourceList", sourceList);
	      ArrayList reportFormat=payMaster.getReportFormat();
	      request.setAttribute("reportFormatList", reportFormat);
		return mapping.findForward("openJsp");
	}
	public ActionForward showBpListReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In PayReportAction........showBpListReport()......");
		HttpSession session = request.getSession();
		boolean flag=false;
		String userId="";
		int companyID=0;
		String currDate="";
		String companyName="";
		String userName="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here showBpListReport method of PayReportAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}else{
			userId= userobj.getUserId();
			companyID=userobj.getCompanyId();
			currDate=userobj.getBusinessdate();
			companyName=userobj.getConpanyName();
			userName = userobj.getUserName()+" ";
			
			
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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
			/*else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}*/
			return mapping.findForward("logout");
		}
		PayReportVO payReportVO=new PayReportVO();
		DynaValidatorForm CommonReportDynaForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(payReportVO, CommonReportDynaForm);
		
		String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
		HashMap hashMap = new HashMap();
		
		hashMap.put("p_company_logo",p_company_logo);
		hashMap.put("p_company_name",companyName);
		hashMap.put("p_printed_by",userName);
		hashMap.put("p_printed_date",currDate);
		hashMap.put("p_source_id",CommonFunction.checkNull(payReportVO.getSourceId()));
		hashMap.put("p_activity_code",CommonFunction.checkNull(payReportVO.getLbxActivityCode()));
		logger.info("p_report_date  : "+currDate);
	//mradul starts for payout reports
		String dbType=resource.getString("lbl.dbType");
		String reportPath="/reports/";
		
		if(dbType.equalsIgnoreCase("MSSQL"))
			reportPath=reportPath+"MSSQLREPORTS/payoutReport/";
		else
			reportPath=reportPath+"MYSQLREPORTS/payoutReport/";
			
		
		String reporttype=CommonFunction.checkNull(payReportVO.getReportType());
		String reportName = "BpListReport";
		//String reportName ="report1";
		Connection connectDatabase = ConnectionDAO.getConnection();
		logger.info("report Name  :  "+ reportName + ".jasper");
		logger.info("p_source_id  :  "+ CommonFunction.checkNull(payReportVO.getSourceId()));
		logger.info("p_activity_code  :  "+ CommonFunction.checkNull(payReportVO.getLbxActivityCode()));
		InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
		logger.info("p_source_id 2222 :  "+ reportStream);
		//logger.info("p_source_id 2222 :  "+ reportStream.);
	//mradul ends for payout reports
		logger.info("p_source_id 2222 :  "+ CommonFunction.checkNull(payReportVO.getSourceId()));
		JasperPrint jasperPrint = null;					
			try
			{
				jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
				if(reporttype.equals("P"))
					methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
				if(reporttype.equals("E"))				
					methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
				if(reporttype.equals("H"))		
					methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request,"");
					//methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint);
					
			}
			catch(JRException e)
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

	
	public ActionForward opentransactionSummaryReportJsp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In PayReportAction........opentransactionSummaryReportJsp()......");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here opentransactionSummaryReportJsp method of PayReportAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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
			/*else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}*/
			return mapping.findForward("logout");
		}
	  
		PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
		
           ArrayList reportFormat=payMaster.getReportFormat();
	      request.setAttribute("reportFormatList", reportFormat);
		return mapping.findForward("openTransactionSummaryJsp");
	}
  public ActionForward showTransacttionSumReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In PayReportAction........showBpListReport()......");
		HttpSession session = request.getSession();
		boolean flag=false;
		String userId="";
		int companyID=0;
		String currDate="";
		String companyName="";
		String userName="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here showBpListReport method of PayReportAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}else{
			userId= userobj.getUserId();
			companyID=userobj.getCompanyId();
			currDate=userobj.getBusinessdate();
			companyName=userobj.getConpanyName();
			userName = userobj.getUserName()+" ";
			
			
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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
			/*else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}*/
			return mapping.findForward("logout");
		}
		PayReportVO payReportVO=new PayReportVO();
		DynaValidatorForm CommonReportDynaForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(payReportVO, CommonReportDynaForm);
		
		String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
		HashMap hashMap = new HashMap();
		
		logger.info("p_report_date  : "+currDate);
		
		String reporttype=CommonFunction.checkNull(request.getParameter("reportType"));
		
		String lbxBpId=CommonFunction.checkNull(request.getParameter("lbxBpId"));
		String lbxSchemeName=CommonFunction.checkNull(request.getParameter("lbxSchemeName"));
		String bpName=CommonFunction.checkNull(request.getParameter("bpName"));
		String schemeName=CommonFunction.checkNull(request.getParameter("schemeName"));
		
		logger.info("lbxBpId:=="+lbxBpId);
		logger.info("lbxSchemeName:=="+lbxSchemeName);
		String reportName = "PayTransactionSummary";
		
		
		String parent="";
		String url="";
	
					
		currDate=	formate(currDate);
		logger.info("App Path req:---"+request.getContextPath());
		logger.info("App Path req:---"+ request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());
		logger.info("App Path:---"+getServlet().getServletConfig().getServletContext());
		String app_path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		hashMap.put("p_company_logo",p_company_logo);
		hashMap.put("p_company_name",companyName);
		hashMap.put("p_printed_by",userName);
		hashMap.put("p_printed_date",currDate);
		hashMap.put("p_report_format",reporttype);
		hashMap.put("p_bp_id",lbxBpId);
		hashMap.put("p_scheme_name",lbxSchemeName);
		hashMap.put("p_user_Id",userId);
		hashMap.put("p_app_path",app_path);
		
		if(reporttype.trim().equalsIgnoreCase("P"))
			hashMap.put("IS_IGNORE_PAGINATION",false);
		else
			hashMap.put("IS_IGNORE_PAGINATION",true);

		
		//code for Drilling is start from here,it is only for HTML
		if(reporttype.trim().equalsIgnoreCase("H"))
		{
			
			parent=request.getParameter("parent");
			url="/payReportAction.do?method=opentransactionSummaryReportJsp";					
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages = new ArrayList();
			String s1="";
			String s2="";
			in.add(userId);     // userID
			in.add(parent);     //parent report
			in.add(reportName); // current report
			in.add(url);        // current URL
			in.add("F");        // backForward
			out.add(s1);
			out.add(s2);
			try
			{
				logger.info("Generate_Report_Hierarchy("+in.toString()+""+out.toString()+")");
				outMessages=(ArrayList) ConnectionDAO.callSP("Generate_Report_Hierarchy",in,out);
				s1=CommonFunction.checkNull(outMessages.get(0));
				s2=CommonFunction.checkNull(outMessages.get(1));
				logger.info("s1      : "+s1);
			    logger.info("s2      : "+s2);
			}
			catch (Exception e) 
			{e.printStackTrace();}						
		}
		//Drilling code end
		
		logger.info("p_company_logo       :  "+p_company_logo);
		logger.info("p_company_name       :  "+companyName);
		logger.info("p_printed_date       :  "+currDate);
		logger.info("p_printed_by         :  "+userName);
		logger.info("p_report_format      :  "+reporttype);
		logger.info("p_bp_id              :  "+lbxBpId);
		logger.info("p_scheme_name        :  "+lbxSchemeName);
		
		logger.info("report Name  :  "+ reportName + ".jasper");
		//String reportName ="report1";
		//mradul starts for payout reports
		String dbType=resource.getString("lbl.dbType");
		String reportPath="/reports/";
		
		if(dbType.equalsIgnoreCase("MSSQL"))
			reportPath=reportPath+"MSSQLREPORTS/payoutReport/";
		else
			reportPath=reportPath+"MYSQLREPORTS/payoutReport/";
		
		Connection connectDatabase = ConnectionDAO.getConnection();
		InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
		JasperPrint jasperPrint = null;					
			try
			{
				jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
				if(reporttype.equals("P"))
					methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
				if(reporttype.equals("E"))				
					methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
				if(reporttype.equals("H"))	
					methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request,"");
					//methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint);
			}
			catch(JRException e)
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
  public ActionForward showTransactionSumReportOnScheme(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In PayReportAction........showBpListReport()......");
		HttpSession session = request.getSession();
		boolean flag=false;
		String userId="";
		int companyID=0;
		String currDate="";
		String companyName="";
		String userName="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here showBpListReport method of PayReportAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}else{
			userId= userobj.getUserId();
			companyID=userobj.getCompanyId();
			currDate=userobj.getBusinessdate();
			companyName=userobj.getConpanyName();
			userName = userobj.getUserName()+" ";
			
			
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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
			/*else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}*/
			return mapping.findForward("logout");
		}
		PayReportVO payReportVO=new PayReportVO();
		DynaValidatorForm CommonReportDynaForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(payReportVO, CommonReportDynaForm);
		
		String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
		HashMap hashMap = new HashMap();
		
		logger.info("p_report_date  : "+currDate);
		
		String reportType=CommonFunction.checkNull(request.getParameter("reportType"));
		
		String lbxBpId=CommonFunction.checkNull(request.getParameter("lbxBpId"));
		String lbxSchemeName=CommonFunction.checkNull(request.getParameter("lbxSchemeName"));
		String bpName=CommonFunction.checkNull(request.getParameter("bpName"));
		String schemeName=CommonFunction.checkNull(request.getParameter("schemeName"));
		String flow=CommonFunction.checkNull(request.getParameter("flow"));
		String scheme=CommonFunction.checkNull(request.getParameter("scheme"));
		String bp=CommonFunction.checkNull(request.getParameter("bp"));
		
		logger.info("lbxBpId:=="+lbxBpId);
		logger.info("lbxSchemeName:=="+lbxSchemeName);
		String reportName = "PayTransactionSummaryOnScheme";
		
		
		String parent="";
		String url="";
	
					
		currDate=	formate(currDate);
		
		logger.info("App Path req:---"+ request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());
		String app_path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		hashMap.put("p_company_logo",p_company_logo);
		hashMap.put("p_company_name",companyName);
		hashMap.put("p_printed_by",userName);
		hashMap.put("p_printed_date",currDate);
		hashMap.put("p_report_format",reportType);
		hashMap.put("p_bp_id",lbxBpId);
		hashMap.put("p_scheme_name",lbxSchemeName);
		hashMap.put("p_user_Id",userId);
		hashMap.put("p_app_path",app_path);
		hashMap.put("p_scheme",scheme);
		hashMap.put("p_bp",bp);
		
		if(reportType.trim().equalsIgnoreCase("P"))
			hashMap.put("IS_IGNORE_PAGINATION",false);
		else
			hashMap.put("IS_IGNORE_PAGINATION",true);

		
		//code for Drilling is start from here,it is only for HTML
		if(reportType.trim().equalsIgnoreCase("H"))
		{
			
			parent=request.getParameter("parent");//from requestParameter old report Name
			url="/payReportAction.do?method=showTransacttionSumReport&lbxBpId="+lbxBpId+"&lbxSchemeName"+lbxSchemeName+"&reportType="+reportType;					
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages = new ArrayList();
			String s1="";
			String s2="";
			in.add(userId); // userID
			in.add(parent); //parent report
			in.add(reportName); // current report
			in.add(url); // current URL
			in.add(flow); // backForward/Forward
			out.add(s1);
			out.add(s2);
			try
			{
				logger.info("Generate_Report_Hierarchy("+in.toString()+""+out.toString()+")");
				outMessages=(ArrayList) ConnectionDAO.callSP("Generate_Report_Hierarchy",in,out);
				s1=CommonFunction.checkNull(outMessages.get(0));
				s2=CommonFunction.checkNull(outMessages.get(1));
				logger.info("s1      : "+s1);
			    logger.info("s2      : "+s2);
			}
			catch (Exception e) 
			{e.printStackTrace();}						
		}
		//Drilling code end
		
		logger.info("p_company_logo       :  "+p_company_logo);
		logger.info("p_company_name       :  "+companyName);
		logger.info("p_printed_date       :  "+currDate);
		logger.info("p_printed_by         :  "+userName);
		logger.info("p_report_format      :  "+reportType);
		logger.info("p_bp_id              :  "+lbxBpId);
		logger.info("p_scheme_name        :  "+lbxSchemeName);
		
		logger.info("report Name  :  "+ reportName + ".jasper");
		//String reportName ="report1";
		//mradul starts for payout reports
		String dbType=resource.getString("lbl.dbType");
		String reportPath="/reports/";
		
		if(dbType.equalsIgnoreCase("MSSQL"))
			reportPath=reportPath+"MSSQLREPORTS/payoutReport/";
		else
			reportPath=reportPath+"MYSQLREPORTS/payoutReport/";
		
		Connection connectDatabase = ConnectionDAO.getConnection();
		InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
		JasperPrint jasperPrint = null;					
			try
			{
				jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
				if(reportType.equals("P"))
					methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
				if(reportType.equals("E"))				
					methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
				if(reportType.equals("H"))	
					methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request,"");
					//methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint);
			}
			catch(JRException e)
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
  
  public ActionForward openPaymentSummaryReportJsp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In PayReportAction........openPaymentSummaryReportJsp()......");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here openPaymentSummaryReportJsp method of PayReportAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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
			/*else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}*/
			return mapping.findForward("logout");
		}
	
		PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
		
         ArrayList reportFormat=payMaster.getReportFormat();
	      request.setAttribute("reportFormatList", reportFormat);
		return mapping.findForward("openPaymentSummaryReportJsp");
	}
  public ActionForward showPaymentSumReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In PayReportAction........showPaymentSumReport()......");
		HttpSession session = request.getSession();
		boolean flag=false;
		String userId="";
		int companyID=0;
		String currDate="";
		String companyName="";
		String userName="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here showPaymentSumReport method of PayReportAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}else{
			userId= userobj.getUserId();
			companyID=userobj.getCompanyId();
			currDate=userobj.getBusinessdate();
			companyName=userobj.getConpanyName();
			userName = userobj.getUserName()+" ";
			
			
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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
			/*else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}*/
			return mapping.findForward("logout");
		}
		PayReportVO payReportVO=new PayReportVO();
		DynaValidatorForm CommonReportDynaForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(payReportVO, CommonReportDynaForm);
		
		String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
		HashMap hashMap = new HashMap();
		
		logger.info("p_report_date  : "+currDate);
		
		String reportType=CommonFunction.checkNull(request.getParameter("reportType"));
		
		String fromDate=CommonFunction.checkNull(request.getParameter("fromDate"));
		String toDate=CommonFunction.checkNull(request.getParameter("toDate"));
		String paySumParameter=CommonFunction.checkNull(request.getParameter("paySumParameter"));
		String flow=CommonFunction.checkNull(request.getParameter("flow"));
		
		StringBuilder whrQuery=new StringBuilder();
		logger.info("flow:-----------------------------"+flow);
		if(!flow.equals("B")){
			if(!fromDate.equals("")){
				fromDate=CommonFunction.changeFormat(fromDate);
			}
			if(!toDate.equals("")){
				toDate=CommonFunction.changeFormat(toDate);			
				}	
	  	}
			
		
		
		
		
		logger.info("fromDate:=="+fromDate);
		logger.info("todate:=="+toDate);
		logger.info("whrQuery:=="+whrQuery.toString());
		String reportName = "PaymentSummaryReport";
		
		
		String parent="";
		String url="";
	
					
		currDate=	formate(currDate);
		logger.info("App Path req:---"+request.getContextPath());
		logger.info("App Path req:---"+ request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());
		logger.info("App Path:---"+getServlet().getServletConfig().getServletContext());
		String app_path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		hashMap.put("p_company_logo",p_company_logo);
		hashMap.put("p_company_name",companyName);
		hashMap.put("p_printed_by",userName);
		hashMap.put("p_printed_date",currDate);
		hashMap.put("p_report_format",reportType);
		hashMap.put("p_user_Id",userId);
		hashMap.put("p_app_path",app_path);
		hashMap.put("p_from_date",fromDate);
		hashMap.put("p_to_date",toDate);
		hashMap.put("p_report_parem",paySumParameter);
		hashMap.put("p_where_qry",whrQuery.toString());
		
		
		if(reportType.trim().equalsIgnoreCase("P"))
			hashMap.put("IS_IGNORE_PAGINATION",false);
		else
			hashMap.put("IS_IGNORE_PAGINATION",true);

		
		//code for Drilling is start from here,it is only for HTML
		if(reportType.trim().equalsIgnoreCase("H"))
		{
			
			parent=request.getParameter("parent");
			url="/payReportAction.do?method=openPaymentSummaryReportJsp";					
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages = new ArrayList();
			String s1="";
			String s2="";
			in.add(userId);     // userID
			in.add(parent);     //parent report
			in.add(reportName); // current report
			in.add(url);        // current URL
			in.add("F");        // backForward
			out.add(s1);
			out.add(s2);
			try
			{
				logger.info("Generate_Report_Hierarchy("+in.toString()+""+out.toString()+")");
				outMessages=(ArrayList) ConnectionDAO.callSP("Generate_Report_Hierarchy",in,out);
				s1=CommonFunction.checkNull(outMessages.get(0));
				s2=CommonFunction.checkNull(outMessages.get(1));
				logger.info("s1      : "+s1);
			    logger.info("s2      : "+s2);
			}
			catch (Exception e) 
			{e.printStackTrace();}						
		}
		//Drilling code end
		
		logger.info("p_company_logo       :  "+p_company_logo);
		logger.info("p_company_name       :  "+companyName);
		logger.info("p_printed_date       :  "+currDate);
		logger.info("p_printed_by         :  "+userName);
		logger.info("p_report_format      :  "+reportType);
	
		logger.info("report Name  :  "+ reportName + ".jasper");
		//String reportName ="report1";
		
		//mradul starts for payout reports
		String dbType=resource.getString("lbl.dbType");
		String reportPath="/reports/";
		
		if(dbType.equalsIgnoreCase("MSSQL"))
			reportPath=reportPath+"MSSQLREPORTS/payoutReport/";
		else
			reportPath=reportPath+"MYSQLREPORTS/payoutReport/";
		
		Connection connectDatabase = ConnectionDAO.getConnection();
		InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
		JasperPrint jasperPrint = null;					
			try
			{
				jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
				if(reportType.equals("P"))
					methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
				if(reportType.equals("E"))				
					methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
				if(reportType.equals("H"))	
					methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request,"");
					//methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint);
			}
			catch(JRException e)
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
  public ActionForward showPaymentSumReportOnActivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In PayReportAction........showPaymentSumReport()......");
		HttpSession session = request.getSession();
		boolean flag=false;
		String userId="";
		int companyID=0;
		String currDate="";
		String companyName="";
		String userName="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here showPaymentSumReport method of PayReportAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}else{
			userId= userobj.getUserId();
			companyID=userobj.getCompanyId();
			currDate=userobj.getBusinessdate();
			companyName=userobj.getConpanyName();
			userName = userobj.getUserName()+" ";
			
			
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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
			/*else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}*/
			return mapping.findForward("logout");
		}
		PayReportVO payReportVO=new PayReportVO();
		DynaValidatorForm CommonReportDynaForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(payReportVO, CommonReportDynaForm);
		
		String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
		HashMap hashMap = new HashMap();
		
		logger.info("p_report_date  : "+currDate);
		
		String reportType=CommonFunction.checkNull(request.getParameter("reportType"));
		
		String fromDate=CommonFunction.checkNull(request.getParameter("fromDate"));
		String todate=CommonFunction.checkNull(request.getParameter("toDate"));
		String paySumParameter=CommonFunction.checkNull(request.getParameter("paySumParameter"));
		String activityCode=CommonFunction.checkNull(request.getParameter("activityCode"));
		String flow=CommonFunction.checkNull(request.getParameter("flow"));
		
		StringBuilder whrQuery=new StringBuilder();
		if(!fromDate.equals("")&&!fromDate.equals("")){
			whrQuery.append(" A.MAKER_DATE BETWEEN STR_TO_DATE('"+fromDate+"','"+dateFormat+"') and  STR_TO_DATE('"+todate+"','"+dateFormat+"')  ");
		}else{
			if(!fromDate.equals("")){
				whrQuery.append(" A.MAKER_DATE > STR_TO_DATE('"+fromDate+"','"+dateFormat+"')  ");
			}
			if(!todate.equals("")){
				whrQuery.append(" A.MAKER_DATE < STR_TO_DATE('"+fromDate+"','"+dateFormat+"')  ");			
				}
		}
		
		logger.info("fromDate:=="+fromDate);
		logger.info("todate:=="+todate);
		logger.info("whrQuery:=="+whrQuery.toString());
		String reportName = "PaymentSummaryReportOnActivity";
		
		
		String parent="";
		String url="";
	  
		currDate=	formate(currDate);
		logger.info("App Path req:---"+request.getContextPath());
		logger.info("App Path req:---"+ request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());
		logger.info("App Path:---"+getServlet().getServletConfig().getServletContext());
		String app_path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		hashMap.put("p_company_logo",p_company_logo);
		hashMap.put("p_company_name",companyName);
		hashMap.put("p_printed_by",userName);
		hashMap.put("p_printed_date",currDate);
		hashMap.put("p_report_format",reportType);
		hashMap.put("p_user_Id",userId);
		hashMap.put("p_app_path",app_path);
		hashMap.put("p_from_date",fromDate);
		hashMap.put("p_to_date",todate);
		hashMap.put("p_report_parem",paySumParameter);
		hashMap.put("p_where_qry",whrQuery.toString());
		hashMap.put("p_activiry_code",activityCode);
		
		
		
		if(reportType.trim().equalsIgnoreCase("P"))
			hashMap.put("IS_IGNORE_PAGINATION",false);
		else
			hashMap.put("IS_IGNORE_PAGINATION",true);

		
		//code for Drilling is start from here,it is only for HTML
		if(reportType.trim().equalsIgnoreCase("H"))
		{
			
			parent=request.getParameter("parent");
			url="/payReportAction.do?method=showPaymentSumReport&reportType="+reportType+"&fromDate="+fromDate+"&toDate="+todate+"&paySumParameter="+paySumParameter;	
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages = new ArrayList();
			String s1="";
			String s2="";
			in.add(userId);     // userID
			in.add(parent);     //parent report
			in.add(reportName); // current report
			in.add(url);        // current URL
			in.add(flow);        // backForward
			out.add(s1);
			out.add(s2);
			try
			{
				logger.info("Generate_Report_Hierarchy("+in.toString()+""+out.toString()+")");
				outMessages=(ArrayList) ConnectionDAO.callSP("Generate_Report_Hierarchy",in,out);
				s1=CommonFunction.checkNull(outMessages.get(0));
				s2=CommonFunction.checkNull(outMessages.get(1));
				logger.info("s1      : "+s1);
			    logger.info("s2      : "+s2);
			}
			catch (Exception e) 
			{e.printStackTrace();}						
		}
		//Drilling code end
		
		logger.info("p_company_logo       :  "+p_company_logo);
		logger.info("p_company_name       :  "+companyName);
		logger.info("p_printed_date       :  "+currDate);
		logger.info("p_printed_by         :  "+userName);
		logger.info("p_report_format      :  "+reportType);
	
		logger.info("report Name  :  "+ reportName + ".jasper");
		//String reportName ="report1";
		//mradul starts for payout reports
		String dbType=resource.getString("lbl.dbType");
		String reportPath="/reports/";
		
		if(dbType.equalsIgnoreCase("MSSQL"))
			reportPath=reportPath+"MSSQLREPORTS/payoutReport/";
		else
			reportPath=reportPath+"MYSQLREPORTS/payoutReport/";
		
		Connection connectDatabase = ConnectionDAO.getConnection();
		InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
		JasperPrint jasperPrint = null;					
			try
			{
				jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
				if(reportType.equals("P"))
					methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
				if(reportType.equals("E"))				
					methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
				if(reportType.equals("H"))	
					methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request,"");
					//methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint);
			}
			catch(JRException e)
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
	public void methodForHTML(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint,HttpServletRequest request,String windowName)throws Exception
	{
		    PrintWriter out=response.getWriter();
		    out.append("<head><script type='text/javascript' src='"+request.getContextPath()+"/js/report/report.js'></script></head>");
		    out.append("<head><title>"+windowName+"</title></head>");
		    
			response.setContentType("text/html");
			
	        request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,jasperPrint);
	        Map imagesMap = new HashMap();
	        float f1=1.1f;
	        request.getSession().setAttribute("IMAGES_MAP", imagesMap);
	        JRHtmlExporter exporter = new JRHtmlExporter();
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN ,Boolean.FALSE);
	        exporter.setParameter(JRHtmlExporterParameter.IGNORE_PAGE_MARGINS ,Boolean.TRUE); 
	        exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
	        exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
	        exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML,"");
	        exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
	        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
	        exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);
	        exporter.setParameter(JRHtmlExporterParameter.ZOOM_RATIO ,f1);
	        ServletContext context = this.getServlet().getServletContext();
	        File reportFile = new File(context.getRealPath("/reports/Dashboard/"));
	        String image = reportFile.getPath();
	        exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,image);
	        exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,image + "/");
	        exporter.exportReport();  
	        out.close();
	        out.flush();
	}
	public String formate(String date)
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
