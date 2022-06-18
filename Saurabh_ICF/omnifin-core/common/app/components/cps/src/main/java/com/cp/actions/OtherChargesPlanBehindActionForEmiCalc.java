package com.cp.actions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.cp.vo.OtherChargesPlanVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.cp.dao.CreditProcessingDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

   public class OtherChargesPlanBehindActionForEmiCalc extends DispatchAction {
		private static final Logger logger = Logger.getLogger(OtherChargesPlanBehindActionForEmiCalc.class.getName());
		public ActionForward execute(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response)
		throws Exception {
		
			logger.info("In OtherChargesPlanBehindActionForEmiCalc  ");
			ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
			String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
			DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
			HttpSession session = request.getSession();

			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in execute method of OtherChargesPlanBehindActionForEmiCalc action the session is out----------------");
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

			logger.info("In OtherChargesPlanBehindActionForEmiCalc(execute) dealId: " + dealId);
			if ((dealId != null && !dealId.equalsIgnoreCase("")))
			{
				
				String repayQ="select DEAL_REPAYMENT_TYPE from cr_deal_loan_dtl_emi_calc where DEAL_ID="+dealId;
			    logger.info("Repayment In deal Query------------------->"+repayQ);
			    String repayType=ConnectionDAO.singleReturn(repayQ);
				logger.info("Repayment Type---------------------->"+repayType);
				
				if(repayType!=null && repayType.equalsIgnoreCase("I"))
				{
					//CreditProcessingDAO detail1 = new CreditProcessingDAOImpl();
					CreditProcessingDAO detail1=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
                    logger.info("Implementation class: "+detail1.getClass()); 		//changed by anil
					String installmentType="";
					ArrayList OthChrgList=detail1.getOtherPeriodicalChargeDetailForEmiCalc(dealId,"D");
					logger.info("OthChrgList    Size:---"+OthChrgList.size());
					if(OthChrgList!=null && OthChrgList.size()>0)
					{
					OtherChargesPlanVo vo1=(OtherChargesPlanVo)OthChrgList.get(0);
				    String loanAmount=CommonFunction.checkNull(vo1.getLoanAmount());
				    logger.info("loanAmount--------------------------------------->" + loanAmount);
				    logger.info("loanAmount--------------------------------------->" + loanAmount);
					String rateType=CommonFunction.checkNull(vo1.getRateType());
					installmentType=CommonFunction.checkNull(vo1.getInstallmentType());
					String totalInstallment=CommonFunction.checkNull(vo1.getTotalInstallment());
					logger.info("In OtherChargesPlanBehindActionForEmiCalc InstallmentType: " + installmentType);
					request.setAttribute("otherChrgList", OthChrgList);
					logger.info("In OtherChargesPlanBehindActionForEmiCalc OthChrgList:Deal Id ") ;
			        request.setAttribute("installmentType", installmentType);
			        request.setAttribute("totalInstallment", totalInstallment);
			        request.setAttribute("rateType", rateType);
			        request.setAttribute("loanAmount", loanAmount);
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
				 return mapping.findForward("backToFirst");
			}
			
	}
}
