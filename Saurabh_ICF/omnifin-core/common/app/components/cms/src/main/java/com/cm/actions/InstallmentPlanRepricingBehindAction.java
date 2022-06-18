package com.cm.actions;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.cm.dao.CreditManagementDAO;
import com.cm.dao.LoanInitiationDAO;
import com.cm.vo.InstallmentPlanForCMVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.roleManager.UserObject;

public class InstallmentPlanRepricingBehindAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(InstallmentPlanRepricingBehindAction.class.getName());
	
	public ActionForward viewOldInstallmentPlanRepricing(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception {
		logger.info("Inside viewOldInstallmentPlanRepricing method");
		HttpSession session = request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in viewOldInstallmentPlanRepricing method of InstallmentPlanRepricingBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		
		String repayQ="select LOAN_REPAYMENT_TYPE from cr_loan_dtl where LOAN_ID="+loanId;
	    logger.info("Repayment Query: "+repayQ);
	    String repayType=ConnectionDAO.singleReturn(repayQ);
		logger.info("Repayment Type:"+repayType);
		
		if(repayType!=null && repayType.equalsIgnoreCase("I"))
		{
			LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass()); 

			//CreditManagementDAO dao = new CreditManagementDAOImpl();
			//RepricingDAO dao=new RepricingDAOImpl();			

			ArrayList installmentList=dao.getInstallType(loanId);
			logger.info("installmentList    Size:---"+installmentList.size());
			if(installmentList!=null && installmentList.size()>0)
			{  
			   InstallmentPlanForCMVO vo=(InstallmentPlanForCMVO)installmentList.get(0);
			   
			    logger.info("Installment Type in cr_loan_dtl table: "+vo.getLoanAmount());
				String loanAmount=CommonFunction.checkNull(vo.getLoanAmount());
			    logger.info("getRateType Type in cr_loan_dtl table: "+vo.getRateType());
				String rateType=CommonFunction.checkNull(vo.getRateType());
				logger.info("Installment Type in cr_loan_dtl table: "+vo.getInstallmentType());
				String installmentType=CommonFunction.checkNull(vo.getInstallmentType());
				String totalInstallment=CommonFunction.checkNull(vo.getTotalInstallment());
				logger.info("In InstallmentPlanBehindAction InstallmentType: " + installmentType);
				//if(installmentType.equalsIgnoreCase("E")  || installmentType.equalsIgnoreCase("P")){
					request.setAttribute("cmAuthor", "cmAuthor");
		
				//}
				request.setAttribute("installmentList", installmentList);
		        request.setAttribute("installmentType", installmentType);
		        request.setAttribute("totalInstallment", totalInstallment);
		        request.setAttribute("rateType", rateType);
		        request.setAttribute("loanAmount", loanAmount);
			}	
			return mapping.findForward("success");
		}
		else
		{
			request.setAttribute("nonInstallmentBased", "nonInstallmentBased");
			return mapping.findForward("backToRepricingMaker");
		}
	}
	
	public ActionForward viewNewInstallmentPlanRepricing(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception {
		logger.info("Inside viewNewInstallmentPlanRepricing method");
		HttpSession session = request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in viewNewInstallmentPlanRepricing method of InstallmentPlanRepricingBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("cmAuthor");
		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		String reschId = CommonFunction.checkNull(request.getParameter("reschId"));
		
		String repayQ="select LOAN_REPAYMENT_TYPE from cr_loan_dtl where LOAN_ID="+loanId;
	    logger.info("Repayment Query: "+repayQ);
	    String repayType=ConnectionDAO.singleReturn(repayQ);
		logger.info("Repayment Type:"+repayType);
		
		//change by sachin
		CreditManagementDAO dao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
	     logger.info("Implementation class: "+dao.getClass());

		//end by sachin
//		CreditManagementDAO dao = new CreditManagementDAOImpl();
		ArrayList<InstallmentPlanForCMVO> installmentList=dao.viewNewInstallmentPlanRepricing(loanId,reschId);
		logger.info("installmentList    Size:---"+installmentList.size());
		if(installmentList!=null && installmentList.size()>0)
		{  
		   InstallmentPlanForCMVO vo=(InstallmentPlanForCMVO)installmentList.get(0);
		   
		    logger.info("Installment Type in cr_loan_dtl table: "+vo.getLoanAmount());
			String loanAmount=CommonFunction.checkNull(vo.getLoanAmount());
		    logger.info("getRateType Type in cr_loan_dtl table: "+vo.getRateType());
			String rateType=CommonFunction.checkNull(vo.getRateType());
			logger.info("Installment Type in cr_loan_dtl table: "+vo.getInstallmentType());
			String installmentType=CommonFunction.checkNull(vo.getInstallmentType());
			String totalInstallment=CommonFunction.checkNull(vo.getTotalInstallment());
			logger.info("In InstallmentPlanBehindAction InstallmentType: " + installmentType);
			if(installmentType.equalsIgnoreCase("E")  || installmentType.equalsIgnoreCase("P")){
				request.setAttribute("viewMode", "viewMode");
	
			}
			String fromWhere =CommonFunction.checkNull(request.getParameter("fromWhere"));
			if(fromWhere.equalsIgnoreCase("repricing")){
				request.setAttribute(fromWhere, fromWhere);
			}
			request.setAttribute("installmentList", installmentList);
			logger.info("In InstallmentPlanBehindAction installmentList: ") ;
	        request.setAttribute("installmentType", installmentType);
	        request.setAttribute("totalInstallment", totalInstallment);
	        request.setAttribute("rateType", rateType);
	        request.setAttribute("loanAmount", loanAmount);
	        request.setAttribute("loanId",loanId);
	        request.setAttribute("reschId",reschId);
		}	
		return mapping.findForward("success");
	}
	
	public ActionForward viewNewInstallmentPlanRepricingAuthor(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception {
		
		logger.info("Inside viewNewInstallmentPlanRepricingAuthor method");
		HttpSession session = request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in viewNewInstallmentPlanRepricingAuthor method of InstallmentPlanRepricingBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		
		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		String reschId = CommonFunction.checkNull(request.getParameter("reschId"));
		
		String repayQ="select LOAN_REPAYMENT_TYPE from cr_loan_dtl where LOAN_ID="+loanId;
	    logger.info("Repayment Query: "+repayQ);
	    String repayType=ConnectionDAO.singleReturn(repayQ);
		logger.info("Repayment Type:"+repayType);
		
		//change by sachin
		CreditManagementDAO dao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
	     logger.info("Implementation class: "+dao.getClass());

		//end by sachin
//		CreditManagementDAO dao = new CreditManagementDAOImpl();
		ArrayList<InstallmentPlanForCMVO> installmentList=dao.viewNewInstallmentPlanRepricing(loanId,reschId);
		logger.info("installmentList    Size:---"+installmentList.size());
		if(installmentList!=null && installmentList.size()>0)
		{  
		   InstallmentPlanForCMVO vo=(InstallmentPlanForCMVO)installmentList.get(0);
		   
		    logger.info("Installment Type in cr_loan_dtl table: "+vo.getLoanAmount());
			String loanAmount=CommonFunction.checkNull(vo.getLoanAmount());
		    logger.info("getRateType Type in cr_loan_dtl table: "+vo.getRateType());
			String rateType=CommonFunction.checkNull(vo.getRateType());
			logger.info("Installment Type in cr_loan_dtl table: "+vo.getInstallmentType());
			String installmentType=CommonFunction.checkNull(vo.getInstallmentType());
			String totalInstallment=CommonFunction.checkNull(vo.getTotalInstallment());
			logger.info("In InstallmentPlanBehindAction InstallmentType: " + installmentType);
			request.setAttribute("cmAuthor", "cmAuthor");
			request.setAttribute("installmentList", installmentList);
			logger.info("In InstallmentPlanBehindAction installmentList: ") ;
	        request.setAttribute("installmentType", installmentType);
	        request.setAttribute("totalInstallment", totalInstallment);
	        request.setAttribute("rateType", rateType);
	        request.setAttribute("loanAmount", loanAmount);
	        request.setAttribute("loanId",loanId);
	        request.setAttribute("reschId",reschId);
		}	
		return mapping.findForward("success");
	}
	
	// Behind functions for Part Payment
	
	public ActionForward viewOldInstallmentPlanPartPayment(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception {
		logger.info("Inside viewOldInstallmentPlanPartPayment method");
		HttpSession session = request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in viewOldInstallmentPlanPartPayment method of InstallmentPlanRepricingBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		
		String repayQ="select LOAN_REPAYMENT_TYPE from cr_loan_dtl where LOAN_ID="+loanId;
	    logger.info("Repayment Query: "+repayQ);
	    String repayType=ConnectionDAO.singleReturn(repayQ);
		logger.info("Repayment Type:"+repayType);
		
		if(repayType!=null && repayType.equalsIgnoreCase("I"))
		{

			LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass()); 

			//CreditManagementDAO dao = new CreditManagementDAOImpl();
			//PartPrePaymentDAO dao=new PartPrePaymentDAOImpl();

			ArrayList installmentList=dao.getInstallType(loanId);
			logger.info("installmentList    Size:---"+installmentList.size());
			if(installmentList!=null && installmentList.size()>0)
			{  
			   InstallmentPlanForCMVO vo=(InstallmentPlanForCMVO)installmentList.get(0);
			   
			    logger.info("Installment Type in cr_loan_dtl table: "+vo.getLoanAmount());
				String loanAmount=CommonFunction.checkNull(vo.getLoanAmount());
			    logger.info("getRateType Type in cr_loan_dtl table: "+vo.getRateType());
				String rateType=CommonFunction.checkNull(vo.getRateType());
				logger.info("Installment Type in cr_loan_dtl table: "+vo.getInstallmentType());
				String installmentType=CommonFunction.checkNull(vo.getInstallmentType());
				String totalInstallment=CommonFunction.checkNull(vo.getTotalInstallment());
				logger.info("In InstallmentPlanBehindAction InstallmentType: " + installmentType);
				//if(installmentType.equalsIgnoreCase("E")  || installmentType.equalsIgnoreCase("P")){
					request.setAttribute("cmAuthor", "cmAuthor");
		
				//}
				request.setAttribute("installmentList", installmentList);
		        request.setAttribute("installmentType", installmentType);
		        request.setAttribute("totalInstallment", totalInstallment);
		        request.setAttribute("rateType", rateType);
		        request.setAttribute("loanAmount", loanAmount);
			}	
			return mapping.findForward("success");
		}
		else
		{
			request.setAttribute("nonInstallmentBased", "nonInstallmentBased");
			return mapping.findForward("backToPartPaymentMaker");
		}
	}
	
	public ActionForward viewNewInstallmentPlanPartPayment(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception {
		logger.info("Inside viewNewInstallmentPlanPartPayment method");
		HttpSession session = request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in viewNewInstallmentPlanPartPayment method of InstallmentPlanRepricingBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		String reschId = CommonFunction.checkNull(request.getParameter("reschId"));
		String installNo=CommonFunction.checkNull(request.getParameter("installNo"));
		String repayQ="select LOAN_REPAYMENT_TYPE from cr_loan_dtl where LOAN_ID="+loanId;
	    logger.info("Repayment Query: "+repayQ);
	    String repayType=ConnectionDAO.singleReturn(repayQ);
		logger.info("Repayment Type:"+repayType);
		
		//change by sachin
		CreditManagementDAO dao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
	     logger.info("Implementation class: "+dao.getClass());

		//end by sachin
//		CreditManagementDAO dao = new CreditManagementDAOImpl();
		ArrayList<InstallmentPlanForCMVO> installmentList=dao.viewNewInstallmentPlanPartPayment(loanId,reschId,installNo);
		logger.info("installmentList    Size:---"+installmentList.size());
		if(installmentList!=null && installmentList.size()>0)
		{  
		   InstallmentPlanForCMVO vo=(InstallmentPlanForCMVO)installmentList.get(0);
		   
		    logger.info("Installment Type in cr_loan_dtl table: "+vo.getLoanAmount());
			String loanAmount=CommonFunction.checkNull(vo.getLoanAmount());
		    logger.info("get Rate Type in cr_loan_dtl table: "+vo.getRateType());
		    logger.info("get Recovery Type in cr_loan_dtl table: "+vo.getRecoveryType());
			String rateType=CommonFunction.checkNull(vo.getRateType());
			logger.info("Installment Type in cr_loan_dtl table: "+vo.getInstallmentType());
			String installmentType=CommonFunction.checkNull(vo.getInstallmentType());
			String totalInstallment=CommonFunction.checkNull(vo.getTotalInstallment());
			logger.info("In InstallmentPlanBehindAction InstallmentType: " + installmentType);
			if(installmentType.equalsIgnoreCase("E")  || installmentType.equalsIgnoreCase("P")){
				request.setAttribute("viewMode", "viewMode");
	
			}
			request.setAttribute("installmentList", installmentList);
			logger.info("In InstallmentPlanBehindAction installmentList: totalInstallment: "+totalInstallment) ;
	        request.setAttribute("installmentType", installmentType);
	        request.setAttribute("totalInstallment", totalInstallment);
	        request.setAttribute("rateType", rateType);
	        request.setAttribute("loanAmount", loanAmount);
	        request.setAttribute("loanId",loanId);
	        request.setAttribute("reschId",reschId);
	        request.setAttribute("noSaveButton","noSaveButton");
		}	
		return mapping.findForward("success");
	}
	
	public ActionForward viewNewInstallmentPlanPartPaymentAuthor(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception {
		logger.info("Inside viewNewInstallmentPlanPartPaymentAuthor method");
		
		HttpSession session = request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in viewNewInstallmentPlanPartPaymentAuthor method of InstallmentPlanRepricingBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		String reschId = CommonFunction.checkNull(request.getParameter("reschId"));
		String installNo=CommonFunction.checkNull(request.getParameter("installNo"));
		logger.info("installNo: "+installNo);
		String repayQ="select LOAN_REPAYMENT_TYPE from cr_loan_dtl where LOAN_ID="+loanId;
	    logger.info("Repayment Query: "+repayQ);
	    String repayType=ConnectionDAO.singleReturn(repayQ);
		logger.info("Repayment Type:"+repayType);
		
		//change by sachin
		CreditManagementDAO dao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
	     logger.info("Implementation class: "+dao.getClass());

		//end by sachin
//		CreditManagementDAO dao = new CreditManagementDAOImpl();
		ArrayList<InstallmentPlanForCMVO> installmentList=dao.viewNewInstallmentPlanPartPayment(loanId,reschId,installNo);
		logger.info("installmentList    Size:---"+installmentList.size());
		if(installmentList!=null && installmentList.size()>0)
		{  
		   InstallmentPlanForCMVO vo=(InstallmentPlanForCMVO)installmentList.get(0);
		   
		    logger.info("Installment Type in cr_loan_dtl table: "+vo.getLoanAmount());
			String loanAmount=CommonFunction.checkNull(vo.getLoanAmount());
		    logger.info("getRateType Type in cr_loan_dtl table: "+vo.getRateType());
			String rateType=CommonFunction.checkNull(vo.getRateType());
			logger.info("Installment Type in cr_loan_dtl table: "+vo.getInstallmentType());
			String installmentType=CommonFunction.checkNull(vo.getInstallmentType());
			String totalInstallment=CommonFunction.checkNull(vo.getTotalInstallment());
			logger.info("In InstallmentPlanBehindAction InstallmentType: " + installmentType);
			request.setAttribute("cmAuthor", "cmAuthor");
			request.setAttribute("installmentList", installmentList);
			logger.info("In InstallmentPlanBehindAction installmentList: totalInstallment "+totalInstallment) ;
	        request.setAttribute("installmentType", installmentType);
	        request.setAttribute("totalInstallment", totalInstallment);
	        request.setAttribute("rateType", rateType);
	        request.setAttribute("loanAmount", loanAmount);
	        request.setAttribute("loanId",loanId);
	        request.setAttribute("reschId",reschId);
		}	
		return mapping.findForward("success");
	}
}
