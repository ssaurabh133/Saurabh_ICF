package com.cp.actions;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cp.dao.CreditProcessingDAO;
import com.cp.dao.FinancialReportDao;


public class FinancialReportBehindAction extends Action{
	private static final Logger logger = Logger.getLogger(FinancialReportBehindAction.class.getName());
@Override
public ActionForward execute(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	ServletContext context = getServlet().getServletContext();
	logger.info("In execute.........");
	HttpSession session = request.getSession();
	boolean flag=false;
	String userId="";
	String branchId="";
	
	UserObject userobj=(UserObject)session.getAttribute("userobject");
	
			if(userobj==null)
			{
				logger.info("here in excute method of FinancialReportBehindAction  action the session is out----------------");
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
		String genericKey="";
		FinancialReportDao dao = (FinancialReportDao)DaoImplInstanceFactory.getDaoImplInstance(FinancialReportDao.IDENTITY);
		
		logger.info("FunctionId------"+session.getAttribute("functionId"));
		if(CommonFunction.checkNull(session.getAttribute("functionId")).equalsIgnoreCase("3000261") || CommonFunction.checkNull(session.getAttribute("viewfundflow")).equalsIgnoreCase("viewfundflow"))
		{
			genericKey="FUNDFLOW_REPORT";
			
			/*flag = dao.checkFundFlowForwardStatus(CommonFunction.checkNull(session.getAttribute("fundFlowDealId")),"FA");
			session.setAttribute("caseId", session.getAttribute("fundFlowDealId"));
			if(!flag)
			request.setAttribute("status", "EFA");*/
		}
		else
		{
			genericKey="FINANCIAL_REPORT";
			flag = dao.checkFundFlowForwardStatus(CommonFunction.checkNull(session.getAttribute("financialDealId")),"FFA");
			session.setAttribute("caseId", session.getAttribute("financialDealId"));
			if(!flag)
			session.setAttribute("status", "EFFA");
		}
		CreditProcessingDAO creditProcessing=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		String caseId1;
		
		if(!CommonFunction.checkNull(session.getAttribute("financialDealId")).equalsIgnoreCase(""))
		{
			 caseId1 = CommonFunction.checkNull(session.getAttribute("financialDealId"));
			 logger.info("financialDealId======================================="+caseId1);
		}
		else 
		{
			 caseId1 = CommonFunction.checkNull(session.getAttribute("dealId"));
			 logger.info("DealId================================================="+caseId1);
		}
		
		
		ArrayList dealHeader = creditProcessing.getDealHeader(caseId1);
		session.setAttribute("dealHeader", dealHeader);
		ArrayList reportList = dao.getReportList(genericKey);
		session.setAttribute("reportList", reportList);
		
		boolean isCreditApproval;
		
		if(CommonFunction.checkNull(session.getAttribute("functionId")).equalsIgnoreCase("3000280"))
			isCreditApproval=true;
		else
			isCreditApproval=false;
		if(session.getAttribute("viewInfo")!=null)
		{
			String caseId  = CommonFunction.checkNull(session.getAttribute("caseId"));
			logger.info("view Stage Id -------"+session.getAttribute("viewStageId"));
			String stageId = "";
			ArrayList linkList =  dao.getReportLink(caseId, "", "", "", "", stageId, isCreditApproval);
			logger.info("linkList size()--"+linkList.size());
			request.setAttribute("linkList", linkList);
		}
	// TODO Auto-generated method stub
	return mapping.findForward("success");
	
}
}
