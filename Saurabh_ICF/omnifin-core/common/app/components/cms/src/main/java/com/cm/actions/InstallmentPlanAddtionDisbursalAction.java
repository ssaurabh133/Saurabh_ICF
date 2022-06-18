/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
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

/** 
 * MyEclipse Struts
 * Creation date: 03-06-2012
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class InstallmentPlanAddtionDisbursalAction extends DispatchAction {
	/*
	 * Generated Methods
	 */
	private static final Logger logger = Logger.getLogger(InstallmentPlanAddtionDisbursalAction.class.getName());
	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward viewOldInstallmentAdditionDisbursal(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception {
		logger.info("Inside viewOldInstallmentAdditionDisbursal method");
		HttpSession session = request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in viewOldInstallmentAdditionDisbursal method of InstallmentPlanAddtionDisbursalAction action the session is out----------------");
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
			
		}
		return mapping.findForward("success");
	}
	public ActionForward viewNewInstallmentPlanAdditionDisbursal(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception {
		logger.info("Inside viewNewInstallmentPlanAdditionDisbursal method");
		HttpSession session = request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in viewNewInstallmentPlanAdditionDisbursal method of  action the session is out----------------");
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
			logger.info("In viewNewInstallmentPlanAdditionDisbursal installmentList: totalInstallment: "+totalInstallment) ;
	        request.setAttribute("installmentType", installmentType);
	        request.setAttribute("totalInstallment", totalInstallment);
	        request.setAttribute("rateType", rateType);
	        request.setAttribute("loanAmount", loanAmount);
	        request.setAttribute("loanId",loanId);
	        request.setAttribute("reschId",reschId);
	        request.setAttribute("noSaveButton","noSaveButton");
		}	
		return mapping.findForward("newInstallment");
	}
	
	public ActionForward viewNewInstallmentPlanAdditionDisbursalAuthor(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception {
		logger.info("Inside viewNewInstallmentPlanAdditionDisbursalAuthor method");
		
		HttpSession session = request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in viewNewInstallmentPlanAdditionDisbursalAuthor method of  action the session is out----------------");
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
			logger.info("In viewNewInstallmentPlanAdditionDisbursalAuthor InstallmentType: " + installmentType);
			request.setAttribute("cmAuthor", "cmAuthor");
			request.setAttribute("installmentList", installmentList);
			logger.info("In viewNewInstallmentPlanAdditionDisbursalAuthor installmentList: totalInstallment "+totalInstallment) ;
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