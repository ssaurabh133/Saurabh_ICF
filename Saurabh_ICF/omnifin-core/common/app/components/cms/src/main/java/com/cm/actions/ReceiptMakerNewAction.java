package com.cm.actions;



import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cm.dao.ReceiptDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ReceiptMakerNewAction extends Action{
	private static final Logger logger = Logger.getLogger(ReceiptMakerNewAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		logger.info(" in ReceiptMakerNewAction----------");	
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info(" in execute method of ReceiptMakerNewAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		
		String strFlag="";	
		
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
				
		ServletContext context = getServlet().getServletContext();
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
		//Rajnikant Tiwari
		//PaymentReceiptBusinessBeanRemote dao = (PaymentReceiptBusinessBeanRemote)LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request);
		ReceiptDAO dao=(ReceiptDAO)DaoImplInstanceFactory.getDaoImplInstance(ReceiptDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		String receiptNoFlag=dao.receiptNoCheckFlag();
		//Nishant space starts
		
		
		String cashDepositFlag=dao.cashDepositFlag();
		String nonCashDepositFlag=dao.nonCashDepositFlag();
		session.setAttribute("cashDepositFlag",cashDepositFlag);
		session.setAttribute("nonCashDepositFlag",nonCashDepositFlag);
	
		String allocationGridReceipt=dao.getAllocationGridReceipt();
		session.setAttribute("allocationGridReceipt",allocationGridReceipt);
		
		String tdsreceiptStatus=dao.getTDSreceiptStatus();
		session.setAttribute("tdsreceiptStatus",tdsreceiptStatus);
	
		String saveForwardReceipt = dao.getsaveForwardReceipt();
		session.setAttribute("saveForwardReceipt", saveForwardReceipt);
		//Nishant space ends
		
		session.setAttribute("receiptNoFlag",receiptNoFlag);
		
		//ArrayList purposeList = dao.receiptPurposeList();
		//request.setAttribute("purposeList", purposeList);
		
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instumentID");
		session.removeAttribute("datatoapproveList");
		session.removeAttribute("businessPartnerType");
		session.removeAttribute("charges");
		session.removeAttribute("otherAddCharges");
		session.removeAttribute("loanId");
		session.removeAttribute("autoManualFlag");
		dao=null;

		return mapping.findForward("new");
		}


}
