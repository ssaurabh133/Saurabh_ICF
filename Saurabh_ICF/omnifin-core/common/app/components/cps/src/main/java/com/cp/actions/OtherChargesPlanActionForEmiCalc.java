package com.cp.actions;

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
import com.cp.vo.OtherChargesPlanVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.cp.dao.CreditProcessingDAO;
import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.connect.DaoImplInstanceFactory;

   public class OtherChargesPlanActionForEmiCalc extends DispatchAction {
	private static final Logger logger = Logger.getLogger(OtherChargesPlanActionForEmiCalc.class.getName());

	public ActionForward saveOtherChargesPlanForEmiCalc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {

	HttpSession session = request.getSession();
	boolean flag=false;
	UserObject userobj=(UserObject)session.getAttribute("userobject");
	 String makerID="";
		String bDate ="";
		if(userobj!=null){
			makerID= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in saveOtherChargesPlanForEmiCalc method of OtherChargesPlanActionForEmiCalc action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
	DynaValidatorForm InstallmentPlanDynaValidatorForm = (DynaValidatorForm)form;
	LoggerMsg.info("In OtherChargesPlanActionForEmiCalc ");
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
	
	String dealId = "";
	String sms="";
	if (session.getAttribute("dealId") != null) {
		dealId = session.getAttribute("dealId").toString();
	}
	logger.info("In saveOtherChargesPlanForEmiCalc method of OtherChargesPlanActionForEmiCalc action dealId: " + dealId);
	 
		if ((dealId != null && !dealId.equalsIgnoreCase("")))
		{
			 OtherChargesPlanVo vo=new OtherChargesPlanVo();
			 org.apache.commons.beanutils.BeanUtils.copyProperties(vo, InstallmentPlanDynaValidatorForm);
			 String loanIdQ="select DEAL_LOAN_ID from cr_deal_loan_dtl_emi_calc where DEAL_ID="+dealId;
			 String loanDealId=ConnectionDAO.singleReturn(loanIdQ);
			 vo.setLoanId(loanDealId);
			 vo.setDealId(dealId);
			 vo.setMakerId(makerID);
			 vo.setMakerDate(bDate);

			//CreditProcessingDAO detail1 = new CreditProcessingDAOImpl();
			 CreditProcessingDAO detail1=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
             logger.info("Implementation class: "+detail1.getClass()); 		//changed by anil			
			 boolean status=false;
				String installmentType=(CommonFunction.checkNull(request.getParameter("installmentType")));
				logger.info(" InstallmentType: " + installmentType);
				status = detail1.saveOtherChargesPlanForEmiCalc(vo);
				if(status)
				 {
					sms="S";
				 }
				else
				{
					sms="E";
				}

			ArrayList OthChrgList=detail1.getOtherPeriodicalChargeDetailForEmiCalc(dealId,"D");
			logger.info("OthChrgList Size:------------->"+OthChrgList.size());
			if(OthChrgList!=null && OthChrgList.size()>0)
			{
				OtherChargesPlanVo vo1=(OtherChargesPlanVo)OthChrgList.get(0);
				String loanAmount=CommonFunction.checkNull(vo1.getLoanAmount());
				String rateType=CommonFunction.checkNull(vo1.getRateType());
				installmentType=CommonFunction.checkNull(vo.getInstallmentType());
				String totalInstallment=CommonFunction.checkNull(vo.getTotalInstallment());
			request.setAttribute("otherChrgList", OthChrgList);
			logger.info("In saveOtherChargesPlanForEmiCalc OthChrgList---------------------------") ;
	        request.setAttribute("installmentType", installmentType);
	        request.setAttribute("totalInstallment", totalInstallment);
	        request.setAttribute("rateType", rateType);
	        request.setAttribute("loanAmount", loanAmount);
			}
		}
	 logger.info("status------------------------------>"+sms);
	 session.removeAttribute("planCheck");
	 if(sms.trim().equals("S"))
		 session.setAttribute("planCheck","Y");
	 else
	 	 session.setAttribute("planCheck","N");
	request.setAttribute("sms", sms);
	return mapping.findForward("success");
}


}
