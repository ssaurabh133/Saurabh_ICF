package com.payout.actions;

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
import org.apache.struts.validator.DynaValidatorForm;

import com.business.PayoutCilent.PayoutBusinessMasterSessionBeanRemote;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.payout.vo.PaymentReceiptVO;

public class PaymentReceiptAuthorBehindAction extends Action{
	private static final Logger logger = Logger.getLogger(PaymentReceiptAuthorBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In PaymentReceiptBehindAction................");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here execute method of PaymentReceiptBehindAction action the session is out----------------");
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
			/*else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}*/
			return mapping.findForward("logout");
		}
		
		 session.removeAttribute("authorList");
		 session.removeAttribute("author");
		 session.removeAttribute("paymentId");
		 
		PaymentReceiptVO paymentReceiptVo=new PaymentReceiptVO();
		DynaValidatorForm CommonPayDynaForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(paymentReceiptVo, CommonPayDynaForm);
		paymentReceiptVo.setMakerId(userobj.getUserId());
		paymentReceiptVo.setMakerDate(userobj.getBusinessdate());
		
		PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
       
		ArrayList<PaymentReceiptVO> list=new ArrayList<PaymentReceiptVO>();
		
		  logger.info("current page link .......... "+request.getParameter("d-49520-p"));
			
			int currentPageLink = 0;
			if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
			{
				currentPageLink=1;
			}
			else
			{
				currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
			}
			
			logger.info("current page link ................ "+request.getParameter("d-49520-p"));
			
			paymentReceiptVo.setCurrentPageLink(currentPageLink);
			list= payMaster.searchPaymentReceiptAuthor(paymentReceiptVo);
			int listSize = list.size();
			if (listSize > 0) {
				 request.setAttribute("list", list);
					logger.info("list.isEmpty(): "+list.isEmpty());
					request.setAttribute("list",list);
			}else{
				request.setAttribute("sms","No");
			}
        return mapping.findForward("success");
	}
}
