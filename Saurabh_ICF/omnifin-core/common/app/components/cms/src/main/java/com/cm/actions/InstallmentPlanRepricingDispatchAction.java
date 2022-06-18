package com.cm.actions;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
import com.cm.dao.CreditManagementDAO;
import com.cm.vo.InstallmentPlanForCMVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


   public class InstallmentPlanRepricingDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(InstallmentPlanRepricingDispatchAction.class.getName());
	
	public ActionForward saveNewInstallmentPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		HttpSession session = request.getSession();

		//boolean flag = false;
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in saveNewInstallmentPlan method of InstallmentPlanRepricingDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
			
		DynaValidatorForm InstallmentPlanDynaValidatorForm = (DynaValidatorForm) form;
		logger.info("In saveNewInstallmentPlan ");
		InstallmentPlanForCMVO ipvo = new InstallmentPlanForCMVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(ipvo,InstallmentPlanDynaValidatorForm);

		Object sessionId = session.getAttribute("sessionID");
		// for check User session start
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
		int functionId=0;
		if(session.getAttribute("functionId")!=null)
		{
			functionId= Integer.parseInt(session.getAttribute("functionId").toString());
		}

		String loanId = CommonFunction.checkNull(ipvo.getLoanId());

		logger.info("In saveNewInstallmentPlan loan id: " + loanId);
		String sms = "";
		if ((loanId != null && !loanId.equalsIgnoreCase(""))) 
		{
			String dealIdQ = "select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID="+ loanId;
			String loanDealId = ConnectionDAO.singleReturn(dealIdQ);
			logger.info("Loan Deal Id in cr_loan_dtl: " + loanDealId);

			ipvo.setDealId(loanDealId);
			ipvo.setTxnType("LIM");

			//change by sachin
			CreditManagementDAO dao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
		     logger.info("Implementation class: "+dao.getClass());

			//end by sachin
	//		CreditManagementDAO dao = new CreditManagementDAOImpl();
			boolean status = false;
			ArrayList<InstallmentPlanForCMVO> installmentList=null;
			String installmentType = (CommonFunction.checkNull(request.getParameter("installmentType")));
			logger.info(" InstallmentType: " + installmentType);
			if (installmentType.equalsIgnoreCase("E")|| installmentType.equalsIgnoreCase("P")) 
			{
				status = dao.saveNewInstallPlan(ipvo);
			} else {
				status = dao.saveNewInstallPlan(ipvo);
			}
			if (status) 
			{
				sms = "S";
			} 
			else 
			{
				sms = "E";
			}
			String installNo=CommonFunction.checkNull(request.getParameter("totalInstallment"));
			logger.info("In saveNewInstallmentPlan totalInstallment ie balance installament fron ---:--"+installNo);
			if(functionId==4000826)
			{
				installmentList=dao.viewNewInstallmentPlanPartPayment(loanId,ipvo.getReschId(),installNo);
			}
			if(functionId==4000806)
			{
				installmentList=dao.viewNewInstallmentPlanRepricing(loanId,ipvo.getReschId());
			}
			if(functionId==4000835)
			{
				installmentList=dao.viewNewInstallmentPlanRepricing(loanId,ipvo.getReschId());
			}
			logger.info("installmentList    Size:---" + installmentList.size());
			if (installmentList != null && installmentList.size() > 0) 
			{
				InstallmentPlanForCMVO vo = (InstallmentPlanForCMVO) installmentList.get(0);
				logger.info("Installment Type in cr_loan_dtl table: "+ vo.getInstallmentType());
				logger.info("Installment Type in cr_loan_dtl table: "+ vo.getLoanAmount());
				String loanAmount = CommonFunction.checkNull(vo.getLoanAmount());
				logger.info("getRateType Type in cr_loan_dtl table: "+ vo.getRateType());
				String rateType = CommonFunction.checkNull(vo.getRateType());
				String totalInstallment = CommonFunction.checkNull(vo.getTotalInstallment());
				logger.info("In InstallmentPlanBehindAction InstallmentType: "+ installmentType);
				if (installmentType.equalsIgnoreCase("E")|| installmentType.equalsIgnoreCase("P"))
				{
					request.setAttribute("viewMode", "viewMode");
				}
				request.setAttribute("installmentList", installmentList);
				logger.info("In InstallmentPlanBehindAction installmentList: ");
				request.setAttribute("installmentType", installmentType);
				request.setAttribute("totalInstallment", totalInstallment);
				request.setAttribute("rateType", rateType);
				request.setAttribute("loanAmount", loanAmount);
			}
		}
		logger.info("status: " + sms);
		request.setAttribute("sms", sms);
		return mapping.findForward("success");
	}


}
