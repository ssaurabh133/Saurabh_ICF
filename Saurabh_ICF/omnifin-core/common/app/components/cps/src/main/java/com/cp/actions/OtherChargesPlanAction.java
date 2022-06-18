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

import com.cm.dao.LoanInitiationDAO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.OtherChargesPlanVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.tabDependencyCheck.RefreshFlagValueInsert;
import com.tabDependencyCheck.RefreshFlagVo;

   public class OtherChargesPlanAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(OtherChargesPlanAction.class.getName());

public ActionForward saveOtherChargesPlan(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
{
	UserObject userobj=null;
	String makerID=null;
	String bDate =null;	
	DynaValidatorForm InstallmentPlanDynaValidatorForm =null;
	OtherChargesPlanVo vo=null;
	try
	{

	HttpSession session = request.getSession();

	userobj=(UserObject)session.getAttribute("userobject");
	makerID="";
	bDate ="";
	if(userobj!=null)
	{
		makerID= userobj.getUserId();
		bDate=userobj.getBusinessdate();
	}
	else
	{
		logger.info("here in saveOtherChargesPlan method of OtherChargesPlanAction action the session is out----------------");
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
	InstallmentPlanDynaValidatorForm = (DynaValidatorForm)form;
	String dealId = "";
	if (session.getAttribute("dealId") != null) {

		dealId = session.getAttribute("dealId").toString();
	} else if (session.getAttribute("maxId") != null) {
		dealId = session.getAttribute("maxId").toString();
	}
	String loanId = "";
	if (session.getAttribute("loanId") != null) {

		loanId = session.getAttribute("loanId").toString();
	} else if (session.getAttribute("maxIdInCM") != null) {
		loanId = session.getAttribute("maxIdInCM").toString();
	}
	logger.info("In saveOtherChargesPlan loan id: " + loanId);
	 String sms="";
	 	if ((loanId != null && !loanId.equalsIgnoreCase("")))
		{
	 		vo=new OtherChargesPlanVo();
	 		CreditProcessingDAO detail1=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+detail1.getClass()); 			// changed by asesh
	 		//CreditProcessingDAO detail1 = new CreditProcessingDAOImpl();
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, InstallmentPlanDynaValidatorForm);
			 String dealIdQ="select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID="+loanId;
			 String loanDealId=ConnectionDAO.singleReturn(dealIdQ);
			 logger.info("Loan Deal Id in cr_loan_dtl: "+loanDealId);
			 vo.setLoanId(loanId);
			 vo.setDealId(loanDealId);
			 //vo.setTxnType("LIM");

			 vo.setMakerId(makerID);
			 vo.setMakerDate(bDate);

			 LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass());
			 boolean status=false;
				String installmentType=(CommonFunction.checkNull(request.getParameter("installmentType")));
				logger.info(" InstallmentType: " + installmentType);
			//	if(installmentType.equalsIgnoreCase("E")  || installmentType.equalsIgnoreCase("P"))
				//{
				//status = dao.saveInstallPlan(ipvo);
				//}
				//else{
				vo.setStage("LIM");
				status = detail1.saveOtherChargesPlanInDeal(vo);
				//}
				if(status)
				 {
					sms="S";
						RefreshFlagVo vo1 = new RefreshFlagVo();
						vo1.setTabIndex(5);
		    			vo1.setRecordId(Integer.parseInt(loanId.trim()));
		        		vo1.setModuleName("CM");
		    			RefreshFlagValueInsert.updateRefreshFlag(vo1);

				 }
				else
				{
					sms="E";
				}

			ArrayList OthChrgList=detail1.getOtherPeriodicalChargeDetailInDeal(loanId,"L");
			logger.info("OthChrgList    Size:---"+OthChrgList.size());
			if(OthChrgList!=null && OthChrgList.size()>0)
			{
				OtherChargesPlanVo vo1=(OtherChargesPlanVo)OthChrgList.get(0);
				logger.info("Installment Type in cr_loan_dtl table: "+vo.getInstallmentType());
				logger.info("Installment Type in cr_deal_loan_dtl table: "+vo1.getLoanAmount());
				String loanAmount=CommonFunction.checkNull(vo1.getLoanAmount());
				logger.info("getRateType Type in cr_deal_loan_dtl table: "+vo1.getRateType());
				String rateType=CommonFunction.checkNull(vo1.getRateType());
				installmentType=CommonFunction.checkNull(vo.getInstallmentType());
				String totalInstallment=CommonFunction.checkNull(vo1.getTotalInstallment());
				logger.info("In saveOtherChargesPlan totalInstallment: " + totalInstallment);
				logger.info("In saveOtherChargesPlan InstallmentType: " + installmentType);
//	        if(installmentType.equalsIgnoreCase("E")  || installmentType.equalsIgnoreCase("P")){
//				request.setAttribute("viewMode", "viewMode");
//
//			}
			request.setAttribute("otherChrgList", OthChrgList);
			logger.info("In saveOtherChargesPlan OthChrgList: ") ;
	        request.setAttribute("installmentType", installmentType);
	        request.setAttribute("totalInstallment", totalInstallment);
	        request.setAttribute("rateType", rateType);
	        request.setAttribute("loanAmount", loanAmount);
			}
		}
		if ((dealId != null && !dealId.equalsIgnoreCase("")))
		{
			vo=new OtherChargesPlanVo();
			String checkedVal=CommonFunction.checkNull(request.getParameter("checkedVal")).trim();
			if(!CommonFunction.checkNull(checkedVal).trim().equalsIgnoreCase("0"))
				org.apache.commons.beanutils.BeanUtils.copyProperties(vo, InstallmentPlanDynaValidatorForm);
			 String loanIdQ="select DEAL_LOAN_ID from cr_deal_loan_dtl where DEAL_ID="+dealId;
			 String loanDealId=ConnectionDAO.singleReturn(loanIdQ);
			 logger.info("Loan loandeal Id in cr_deal_loan_dtl: "+loanDealId);

			 vo.setLoanId(loanDealId);
			 vo.setDealId(dealId);
			 //ipvo.setTxnType("DC");
			 vo.setMakerId(makerID);
			 vo.setMakerDate(bDate);

			 CreditProcessingDAO detail1=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		        logger.info("Implementation class: "+detail1.getClass()); 			// changed by asesh
			 boolean status=false;
				String installmentType=(CommonFunction.checkNull(request.getParameter("installmentType")));
				logger.info(" InstallmentType: " + installmentType);
//				if(installmentType.equalsIgnoreCase("E")  || installmentType.equalsIgnoreCase("P"))
//				{
//
//					status = detail1.saveOtherChargesPlanInDeal(vo);
//
//				}
//				else{
					vo.setStage("DC");
					status = detail1.saveOtherChargesPlanInDeal(vo);
				//}
				if(status)
				 {
					sms="S";
						RefreshFlagVo vo1 = new RefreshFlagVo();
						vo1.setTabIndex(8);
		    			vo1.setRecordId(Integer.parseInt(dealId));
		        		vo1.setModuleName("CP");
		    			RefreshFlagValueInsert.updateRefreshFlag(vo1);
				 }
				else
				{
					sms="E";
				}

			ArrayList OthChrgList=detail1.getOtherPeriodicalChargeDetailInDeal(dealId,"D");
			logger.info("OthChrgList    Size:---"+OthChrgList.size());
			if(OthChrgList!=null && OthChrgList.size()>0)
			{
				OtherChargesPlanVo vo1=(OtherChargesPlanVo)OthChrgList.get(0);
				logger.info("Installment Type in cr_loan_dtl table: "+vo.getInstallmentType());
				 logger.info("Installment Type in cr_deal_loan_dtl table: "+vo1.getLoanAmount());
					String loanAmount=CommonFunction.checkNull(vo1.getLoanAmount());
					 logger.info("getRateType Type in cr_deal_loan_dtl table: "+vo1.getRateType());
					String rateType=CommonFunction.checkNull(vo1.getRateType());
				installmentType=CommonFunction.checkNull(vo.getInstallmentType());
				String totalInstallment=CommonFunction.checkNull(vo1.getTotalInstallment());
				logger.info("In saveOtherChargesPlan totalInstallment: " + totalInstallment);
				logger.info("In saveOtherChargesPlan InstallmentType: " + installmentType);
//	        if(installmentType.equalsIgnoreCase("E")  || installmentType.equalsIgnoreCase("P")){
//				request.setAttribute("viewMode", "viewMode");
//
//			}
			request.setAttribute("otherChrgList", OthChrgList);
			logger.info("In saveOtherChargesPlan OthChrgList: ") ;
	        request.setAttribute("installmentType", installmentType);
	        request.setAttribute("totalInstallment", totalInstallment);
	        request.setAttribute("rateType", rateType);
	        request.setAttribute("loanAmount", loanAmount);
			}
		}
	 logger.info("status: "+sms);
	 session.removeAttribute("planCheck");
	 if(sms.trim().equals("S"))
		 session.setAttribute("planCheck","Y");
	 else
	 	 session.setAttribute("planCheck","N");
	request.setAttribute("sms", sms);
	return mapping.findForward("success");
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return null;
	}
	finally
	{
		userobj=null;
		makerID=null;
		bDate =null;	
		InstallmentPlanDynaValidatorForm =null;
		vo=null;
	}
}
public ActionForward deleteOtherChargesPlan(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
{
	UserObject userobj=null;
	String makerID=null;
	String bDate =null;	
	DynaValidatorForm InstallmentPlanDynaValidatorForm =null;
	OtherChargesPlanVo vo=null;
	try
	{
		HttpSession session = request.getSession();
		userobj=(UserObject)session.getAttribute("userobject");
		makerID="";
		bDate ="";
		if(userobj!=null)
		{
			makerID= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}
		else
		{
			logger.info("here in saveOtherChargesPlan method of OtherChargesPlanAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
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
		InstallmentPlanDynaValidatorForm = (DynaValidatorForm)form;
		String dealId = "";
		if (session.getAttribute("dealId") != null) 
		{
			dealId = session.getAttribute("dealId").toString();
		} 
		else if (session.getAttribute("maxId") != null) 
		{
			dealId = session.getAttribute("maxId").toString();
		}
		String loanId = "";
		if (session.getAttribute("loanId") != null) 
		{
			loanId = session.getAttribute("loanId").toString();
		} 
		else if (session.getAttribute("maxIdInCM") != null) 
		{
			loanId = session.getAttribute("maxIdInCM").toString();
		}
		logger.info("In saveOtherChargesPlan loan id: " + loanId);
		String checkedRow=CommonFunction.checkNull(request.getParameter("checkedRow")).trim();
		
		String sms="";
	 	if ((loanId != null && !loanId.equalsIgnoreCase("")))
		{
	 		vo=new OtherChargesPlanVo();
	 		CreditProcessingDAO detail1=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+detail1.getClass()); 			// changed by asesh
	 		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, InstallmentPlanDynaValidatorForm);
			String dealIdQ="select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID="+loanId;
			String loanDealId=ConnectionDAO.singleReturn(dealIdQ);
			logger.info("Loan Deal Id in cr_loan_dtl: "+loanDealId);
			vo.setLoanId(loanId);
			vo.setDealId(loanDealId);
			vo.setMakerId(makerID);
			vo.setMakerDate(bDate);
			LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
			boolean status=false;
			String installmentType=(CommonFunction.checkNull(request.getParameter("installmentType")));
			logger.info(" InstallmentType: " + installmentType);
			vo.setStage("LIM");
			status = detail1.deleteOtherChargesPlanInDeal(loanId,checkedRow,"LIM");
			if(status)
			{
					sms="SDL";
					RefreshFlagVo vo1 = new RefreshFlagVo();
					vo1.setTabIndex(5);
		    		vo1.setRecordId(Integer.parseInt(loanId.trim()));
		        	vo1.setModuleName("CM");
		    		RefreshFlagValueInsert.updateRefreshFlag(vo1);
			 }
			else
			{
				sms="EDL";
			}
			ArrayList OthChrgList=detail1.getOtherPeriodicalChargeDetailInDeal(loanId,"L");
			if(OthChrgList!=null && OthChrgList.size()>0)
			{
				OtherChargesPlanVo vo1=(OtherChargesPlanVo)OthChrgList.get(0);
				String loanAmount=CommonFunction.checkNull(vo1.getLoanAmount());
				String rateType=CommonFunction.checkNull(vo1.getRateType());
				installmentType=CommonFunction.checkNull(vo.getInstallmentType());
				String totalInstallment=CommonFunction.checkNull(vo1.getTotalInstallment());
				request.setAttribute("otherChrgList", OthChrgList);
				request.setAttribute("installmentType", installmentType);
				request.setAttribute("totalInstallment", totalInstallment);
				request.setAttribute("rateType", rateType);
				request.setAttribute("loanAmount", loanAmount);
			}
		}
		if ((dealId != null && !dealId.equalsIgnoreCase("")))
		{
			vo=new OtherChargesPlanVo();
			String checkedVal=CommonFunction.checkNull(request.getParameter("checkedVal")).trim();
			if(!CommonFunction.checkNull(checkedVal).trim().equalsIgnoreCase("0"))
				org.apache.commons.beanutils.BeanUtils.copyProperties(vo, InstallmentPlanDynaValidatorForm);
			 String loanIdQ="select DEAL_LOAN_ID from cr_deal_loan_dtl where DEAL_ID="+dealId;
			 String loanDealId=ConnectionDAO.singleReturn(loanIdQ);
			 vo.setLoanId(loanDealId);
			 vo.setDealId(dealId);
			 vo.setMakerId(makerID);
			 vo.setMakerDate(bDate);
			 CreditProcessingDAO detail1=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		     logger.info("Implementation class: "+detail1.getClass()); 			// changed by asesh
			 boolean status=false;
			 String installmentType=(CommonFunction.checkNull(request.getParameter("installmentType")));
			 logger.info(" InstallmentType: " + installmentType);
			 vo.setStage("DC");
			 status = detail1.deleteOtherChargesPlanInDeal(dealId,checkedRow,"DC");
			 if(status)
			 {
				 sms="SDL";
				RefreshFlagVo vo1 = new RefreshFlagVo();
				vo1.setTabIndex(8);
		    	vo1.setRecordId(Integer.parseInt(dealId));
		    	vo1.setModuleName("CP");
		    	RefreshFlagValueInsert.updateRefreshFlag(vo1);
			}
			else
			{
				sms="EDL";
			}
			ArrayList OthChrgList=detail1.getOtherPeriodicalChargeDetailInDeal(dealId,"D");
			if(OthChrgList!=null && OthChrgList.size()>0)
			{
				OtherChargesPlanVo vo1=(OtherChargesPlanVo)OthChrgList.get(0);
				String loanAmount=CommonFunction.checkNull(vo1.getLoanAmount());
				String rateType=CommonFunction.checkNull(vo1.getRateType());
				installmentType=CommonFunction.checkNull(vo.getInstallmentType());
				String totalInstallment=CommonFunction.checkNull(vo1.getTotalInstallment());
				request.setAttribute("otherChrgList", OthChrgList);
				request.setAttribute("installmentType", installmentType);
				request.setAttribute("totalInstallment", totalInstallment);
				request.setAttribute("rateType", rateType);
				request.setAttribute("loanAmount", loanAmount);
			}
		}
		logger.info("status: "+sms);
		session.removeAttribute("planCheck");
		if(sms.trim().equals("S"))
			session.setAttribute("planCheck","Y");
		else
			session.setAttribute("planCheck","N");
		request.setAttribute("sms", sms);
		return mapping.findForward("success");
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return null;
	}
	finally
	{
		userobj=null;
		makerID=null;
		bDate =null;	
		InstallmentPlanDynaValidatorForm =null;
		vo=null;
	}	
}

}
