package com.cm.actions;

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
import com.cm.vo.InstallmentPlanForCMVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.cp.dao.CreditProcessingDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.connect.DaoImplInstanceFactory;

public class InstallmentPlanBehindActionForEmiCalc extends Action {
	private static final Logger logger = Logger.getLogger(InstallmentPlanBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
	
		logger.info("In InstallmentPlanBehindActionForEmiCalc  ");
	
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in execute method of DeleteInstrumentAuthorBehindAction action the session is out----------------");
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
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		session.removeAttribute("pParentGroup");	
		
		String dealId = "";

		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		} 

		logger.info("In InstallmentPlanBehindActionForEmiCalc(execute) dealId: " + dealId);
		
				
		if ((dealId != null && !dealId.equalsIgnoreCase("")))
		{
			
			String repayQ="select DEAL_REPAYMENT_TYPE from cr_deal_loan_dtl_emi_calc where DEAL_ID="+dealId;
		    logger.info("Repayment In deal Query: "+repayQ);
		    String repayType=ConnectionDAO.singleReturn(repayQ);
			logger.info("Repayment Type:"+repayType);
			
			if(repayType!=null && repayType.equalsIgnoreCase("I"))
			{
			
				
				//CreditProcessingDAO detail1 = new CreditProcessingDAOImpl();
				CreditProcessingDAO detail1=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
                logger.info("Implementation class: "+detail1.getClass()); 		//changed by anil
				ArrayList installmentList=detail1.getInstallTypeForEmiCalc(dealId);
				if(installmentList!=null && installmentList.size()>0)
				{  
				   InstallmentPlanForCMVO vo=(InstallmentPlanForCMVO)installmentList.get(0);
					String installmentType=CommonFunction.checkNull(vo.getInstallmentType());
					String loanAmount=CommonFunction.checkNull(vo.getLoanAmount());
				    String rateType=CommonFunction.checkNull(vo.getRateType());
					String totalInstallment=CommonFunction.checkNull(vo.getTotalInstallment());
					logger.info("In InstallmentPlanBehindActionForEmiCalc InstallmentType: " + installmentType);
		        if(installmentType.equalsIgnoreCase("E")  || installmentType.equalsIgnoreCase("P")){
					request.setAttribute("viewMode", "viewMode");
			
				}
				request.setAttribute("installmentList", installmentList);
				logger.info("In InstallmentPlanBehindActionForEmiCalc installmentList: ") ;
		        request.setAttribute("installmentType", installmentType);
		        request.setAttribute("totalInstallment", totalInstallment);
		        request.setAttribute("rateType", rateType);
		        request.setAttribute("loanAmount", loanAmount);
		        request.setAttribute("dealId", "dealId");
				}	
				return mapping.findForward("success");
		  }
			else
			{
				request.setAttribute("nonProduct", "nonProduct");
				return mapping.findForward("backNonProductInDeal");
			}
			
		}
		else
		{
			 request.setAttribute("back", "back");
			 return mapping.findForward("backInstallmentToEmiCal");
		}		
}
}