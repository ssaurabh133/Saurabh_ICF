package com.cp.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cp.dao.FileUtilityDao;
import com.cp.financialDao.FinancialDAO;
import com.cp.fundFlowDao.FundFlowAnalysisDAO;
import com.cp.dao.OcrDAO;
import com.cp.vo.CodeDescVo;

public class AjaxActionForOCR extends DispatchAction{
	private static final Logger logger = Logger.getLogger(AjaxActionForOCR.class.getName());
	public ActionForward   getCustomerNameForEntityType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("In AjaxActionForOCR.........");
		HttpSession session = request.getSession();
		boolean flag=false;
		String userId="";
		String branchId="";
		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		if(userobj==null)
				{
					logger.info("here in getCustomerNameForEntityType method of AjaxActionForOCR  action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				else
				{
					userId=userobj.getUserId();
					branchId=userobj.getBranchId();
				}
		Object sessionId = session.getAttribute("sessionID");
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
		String caseId = CommonFunction.checkNull(request.getParameter("caseId"));
		String entityType =  CommonFunction.checkNull(request.getParameter("entityType"));
		logger.info("caseId-----"+caseId);
		logger.info("entityType-----"+entityType);
		OcrDAO dao =(OcrDAO)DaoImplInstanceFactory.getDaoImplInstance(OcrDAO.IDENTITY);
		
		ArrayList<CodeDescVo> customerList = dao.getCustomerList(caseId,entityType);
		request.setAttribute("customerList", customerList);
		if(CommonFunction.checkNull(session.getAttribute("functionId")).equalsIgnoreCase("3000276"))
			return mapping.findForward("financialCustomer");
		return mapping.findForward("customer");
	}

	public ActionForward   getSheetData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("In AjaxActionForOCR.........");
		HttpSession session = request.getSession();
		boolean flag=false;
		String userId="";
		String branchId="";
		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		if(userobj==null)
				{
					logger.info("here in getCustomerNameForEntityType method of AjaxActionForOCR  action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				else
				{
					userId=userobj.getUserId();
					branchId=userobj.getBranchId();
				}
		Object sessionId = session.getAttribute("sessionID");
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
		String caseId = CommonFunction.checkNull(request.getParameter("caseId"));
		String entityType =  CommonFunction.checkNull(request.getParameter("entityType"));
		String sourceType =  CommonFunction.checkNull(request.getParameter("sourceType"));
		String customerId =  CommonFunction.checkNull(request.getParameter("customerId"));
		
		logger.info("caseId-----"+caseId);
		logger.info("entityType-----"+entityType);
		logger.info("sourceType-----"+sourceType);
		FinancialDAO dao=(FinancialDAO)DaoImplInstanceFactory.getDaoImplInstance(FinancialDAO.IDENTITY);
		String recStatus = "";
		if(session.getAttribute("viewInfo")!=null)
			recStatus="A";
		else
			recStatus="P";
		FileUtilityDao utilityDao = (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
		String financialYear = utilityDao.getFinancialYearFromCustomerDemeographics(caseId);
		financialYear = financialYear.substring(financialYear.lastIndexOf("-")+1,financialYear.length());
		 int year =Integer.parseInt(financialYear);
		 int py1=year-1;
		 int py2=year-2;
		ArrayList paramList = dao.getdealAllParamDeatils(sourceType,caseId,customerId,recStatus,year,py1,py2);
		request.setAttribute("paramList", paramList);
		return mapping.findForward("sheetData");
	}
	public ActionForward   getBalanceAsOnDateData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("In getBalanceAsOnDateData.........");
		HttpSession session = request.getSession();
		boolean flag=false;
		String userId="";
		String branchId="";
		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		if(userobj==null)
				{
					logger.info("here in getBalanceAsOnDateData method of AjaxActionForOCR  action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				else
				{
					userId=userobj.getUserId();
					branchId=userobj.getBranchId();
				}
		Object sessionId = session.getAttribute("sessionID");
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
		String caseId = CommonFunction.checkNull(request.getParameter("caseId"));
		String balanceAsOnDate =  CommonFunction.checkNull(request.getParameter("balanceAsOnDate"));
		String custRef =  CommonFunction.checkNull(request.getParameter("custRef"));
		String customerId =  CommonFunction.checkNull(request.getParameter("customerId"));
		String documentId =  CommonFunction.checkNull(request.getParameter("documentId"));
		
		logger.info("caseId-----"+caseId);
		logger.info("custRef-----"+custRef);
		logger.info("balanceAsOnDate-----"+balanceAsOnDate);
		logger.info("customerId-----"+customerId);
		logger.info("customerId-----"+documentId);
		
		FundFlowAnalysisDAO dao =(FundFlowAnalysisDAO)DaoImplInstanceFactory.getDaoImplInstance(FundFlowAnalysisDAO.IDENTITY);
		
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(balanceAsOnDate);
			balanceAsOnDate = format1.format(date);
			logger.info("balanceAsOnDate---------"+balanceAsOnDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String balance = dao.getBalanceAsOnDateData(caseId,balanceAsOnDate,custRef,customerId,documentId);
		logger.info("balance amount "+balance);
		response.getWriter().write(balance);
		return null;
	}

	
}
