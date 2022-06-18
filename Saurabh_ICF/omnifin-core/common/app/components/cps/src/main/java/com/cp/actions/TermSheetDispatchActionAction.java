/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cp.actions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.cp.dao.CreditProcessingDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.vo.TermSheetVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;

/** 
 * MyEclipse Struts
 * Creation date: 07-23-2012
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class TermSheetDispatchActionAction extends DispatchAction {
	/*
	 * Generated Methods
	 */
	private static final Logger logger = Logger.getLogger(TermSheetDispatchActionAction.class.getName());
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	
	public ActionForward openTermSheet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		logger.info(" In the TermSheetDispatchActionAction----------");
		
		logger.info("In openTermSheet.....");
		HttpSession session = request.getSession();
		String userId="";
		//String branch="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		//	branch=userobj.getBranchId();
		}else{
			logger.info("here in openTermSheet method of TermSheetDispatchActionAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag=null;	
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
		String functionId=(String)session.getAttribute("functionId");
		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase(""))
			functionId="0";
		int id=Integer.parseInt(functionId);
		if(id==4000106 || id==4000122)
		{
			session.removeAttribute("underWriterViewData");
			session.removeAttribute("leadNo");
			session.removeAttribute("dealHeader");
			session.removeAttribute("dealId");
			session.removeAttribute("leadInfo");
			session.removeAttribute("viewDeal");
			session.removeAttribute("dealCatList");
			session.removeAttribute("sourceTypeList");
			session.removeAttribute("checkLoginUserLevel");
			session.removeAttribute("creditApprovalList");
			session.removeAttribute("leadMValue");
			session.removeAttribute("bsflag");
	
		}
		 session.removeAttribute("loanHeader");
		 String dealId = (String) session.getAttribute("dealId");
		 logger.info("In TermSheetDispatchActionAction(openTermSheet) dealId "+ dealId);
		 if(!CommonFunction.checkNull(dealId).equalsIgnoreCase(""))
		 {
			
			 CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		      logger.info("Implementation class: "+dao.getClass()); 			
			 //CreditProcessingDAO dao = new CreditProcessingDAOImpl();
		    ArrayList<Object> exposr=dao.getExposure(dealId,userId);
		    request.setAttribute("exposr", exposr);
		    ArrayList<Object> termsheet=dao.getTermSheet(dealId);
		    ArrayList<Object> machineDetails = dao.getMachineDetails(dealId);
			ArrayList<Object> approvalCommitteeList = dao.getApprovalCommitteeList(dealId);
			ArrayList<Object> creditcommitteeList = dao.getCreditcommitteeList();
			if(termsheet.size()>0){
				request.setAttribute("termsheet", termsheet);
			}
		    if(machineDetails.size()>0){
				request.setAttribute("machineDetails",machineDetails);
			}
		    if(approvalCommitteeList.size()>0){
				request.setAttribute("approvalCommitteeList", approvalCommitteeList);
			}
		    if(creditcommitteeList.size()>0){
				request.setAttribute("creditcommitteeList", creditcommitteeList);
			}
			return mapping.findForward("openTermSheet");
	   
		 }
		 else
		 {
	   
			return mapping.findForward("canNotOpenTermSheet");
		 }
	   
}
	
	public ActionForward openTermSheetCM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		logger.info(" In the TermSheetDispatchActionAction----------");
		
		logger.info("In openTermSheet.....");
		HttpSession session = request.getSession();
	//	String userId="";
	//	String branch="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		if(userobj!=null)
		{
		//	userId=userobj.getUserId();
		//	branch=userobj.getBranchId();
		}else{
			logger.info("here in openTermSheet method of TermSheetDispatchActionAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag=null;	
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
		//code added by neeraj 
		String functionId=(String)session.getAttribute("functionId");
		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase(""))
			functionId="0";
		int id=Integer.parseInt(functionId);
		if(id==4000122 || id==4000123)
		{
			session.setAttribute("cmAuthor","cmAuthor");
			session.setAttribute("viewLoan","viewLoan");
		}
		//neeraj space end 
		 CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	      logger.info("Implementation class: "+dao.getClass()); 
		 //CreditProcessingDAO dao = new CreditProcessingDAOImpl();
		 if(!(CommonFunction.checkNull(session.getAttribute("loanId")).equalsIgnoreCase("")))
		 {
			 String loanId=session.getAttribute("loanId").toString();
			 String dealId =dao.getDealIdInCm(loanId);
			 request.setAttribute("strFlagDV","strFlagDV");
			 session.removeAttribute("dealHeader");
			 logger.info("In TermSheetDispatchActionAction(openTermSheet) dealId "+ dealId);

			 ArrayList<Object> termsheet=dao.getTermSheet(dealId);
			 ArrayList<Object> machineDetails = dao.getMachineDetails(dealId);
			 ArrayList<Object> approvalCommitteeList = dao.getApprovalCommitteeList(dealId);
			 if(termsheet.size()>0){
				request.setAttribute("termsheet", termsheet);
			 }
			 if(machineDetails.size()>0){
				request.setAttribute("machineDetails",machineDetails);
			 }
			 if(approvalCommitteeList.size()>0){
				request.setAttribute("approvalCommitteeList", approvalCommitteeList);
			 }
			 return mapping.findForward("openTermSheet");
	
		 }
		 else
		 {
			request.setAttribute("back", "back");
			return mapping.findForward("canNotOpenTermSheetCM");
		 }
	   
}
	
	
	public ActionForward saveTermSheet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		logger.info(" In the TermSheetDispatchActionAction----------");
		
		logger.info("In saveTermSheet.....");
		HttpSession session = request.getSession();
		String userId=null;
		String bDate=null;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in saveTermSheet method of TermSheetDispatchActionAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag=null;	
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
		
		DynaValidatorForm TermSheetDynaValidatorForm = (DynaValidatorForm) form;
		TermSheetVo termsVo=new TermSheetVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(termsVo, TermSheetDynaValidatorForm);
		termsVo.setMakerId(userId);
		termsVo.setMakerDate(bDate);
	    String dealId = (String) session.getAttribute("dealId");
	    if(!CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
	    {
	    	String loanId = (String) session.getAttribute("loanId");
	    }
		logger.info("In TermSheetDispatchActionAction(saveTermSheet) dealId "+ dealId);
		termsVo.setDealId(dealId);
		 CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	      logger.info("Implementation class: "+dao.getClass()); 
		//CreditProcessingDAO dao = new CreditProcessingDAOImpl();
		 String flagSt=dao.saveTermSheetDetails(termsVo);
		if(CommonFunction.checkNull(flagSt).equalsIgnoreCase("S"))
		{			
			request.setAttribute("sms", "S");
		}
		else
		{
			request.setAttribute("sms", "X");
		}
		form.reset(mapping, request);
		dao=null;
		return mapping.findForward("saveTermSheet");
	}
	
		//method added by neeraj
	public ActionForward generateTermSheet(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		logger.info("In generateTermSheet() of TermSheetDispatchActionAction");
	  	
	    HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String p_company_name=null;
	    String p_printed_by=null;
	    String businessdate=null;
	    String loanId =null;
		if(userobj!=null)
		{
			p_company_name=userobj.getConpanyName()+" ";	
			p_printed_by=userobj.getUserName()+" ";
			businessdate=userobj.getBusinessdate();				
		}
		else
		{
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag=null;	
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		
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
		Connection connectDatabase = ConnectionDAO.getConnection();		
		Map<Object,Object> hashMap = new HashMap<Object,Object>();
		try
		{
			ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
			String dbType=resource.getString("lbl.dbType");
			String reportPath="/reports/";
			String reportName="termSheet_Report";
			String SUBREPORT_DIR=getServlet().getServletContext().getRealPath("/")+"reports\\";
			
			if(dbType.equalsIgnoreCase("MSSQL"))
			{
				reportPath=reportPath+"MSSQLREPORTS/";
				SUBREPORT_DIR=SUBREPORT_DIR+"MSSQLREPORTS\\";
			}
			else
			{
				reportPath=reportPath+"MYSQLREPORTS/";
				SUBREPORT_DIR=SUBREPORT_DIR+"MYSQLREPORTS\\";
			}			
			
			String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
			String p_deal_id=(String) session.getAttribute("dealId");			
			String p_printed_date=formate(businessdate);
			if(CommonFunction.checkNull(p_deal_id).trim().equalsIgnoreCase(""))
		    {
		    	loanId = (String) session.getAttribute("loanId");
		    	logger.info("In generateErrorLog() of openTermSheet  loan_id      :  "+loanId);
		    	CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
				p_deal_id = dao.getDealIdforTerm(loanId);
		    }
			
			hashMap.put("p_deal_id",p_deal_id );
			hashMap.put("SUBREPORT_DIR",SUBREPORT_DIR );
			hashMap.put("p_company_name",p_company_name );
			hashMap.put("p_company_logo",p_company_logo );
			hashMap.put("p_printed_by",p_printed_by );
			hashMap.put("p_printed_date",p_printed_date );
			
			logger.info("In generateErrorLog() of openTermSheet  p_deal_id      :  "+p_deal_id);
			logger.info("In generateErrorLog() of openTermSheet  SUBREPORT_DIR  :  "+SUBREPORT_DIR);
			logger.info("In generateErrorLog() of openTermSheet  p_company_name :  "+p_company_name);
			logger.info("In generateErrorLog() of openTermSheet  p_company_logo :  "+p_company_logo);
			logger.info("In generateErrorLog() of openTermSheet  p_printed_by   :  "+p_printed_by);
			logger.info("In generateErrorLog() of openTermSheet  p_printed_date :  "+p_printed_date);
			logger.info("In generateErrorLog() of openTermSheet  report         :  "+reportPath+reportName+".jasper");
		
			InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+reportName+".jasper");
			JasperPrint jasperPrint = null;
			
			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
			methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
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
	//method added by neeraj
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