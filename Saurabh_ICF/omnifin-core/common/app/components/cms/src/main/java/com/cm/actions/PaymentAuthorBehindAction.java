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
import com.cm.vo.PaymentMakerForCMVO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class PaymentAuthorBehindAction extends Action{
	private static final Logger logger = Logger.getLogger(PaymentAuthorBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		HttpSession session = request.getSession();
	
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in execute method of PaymentAuthorBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instrumentID");
		session.removeAttribute("businessPartnerType");
		request.setAttribute("fromAuthor", "fromAuthor");
	//	boolean flag=false;
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
			strFlag=null;
			return mapping.findForward("logout");
		}
		PaymentMakerForCMVO vo = new PaymentMakerForCMVO();

		//ArrayList<PaymentMakerForCMVO> bussinessPartnerList= dao.getbussinessPartner();
		//request.setAttribute("bussinessPartnerList", bussinessPartnerList);
		logger.info("In PaymentAuthorBehindAction  ");
		request.setAttribute("fromAuthor", "fromAuthor");
		strFlag=null;
		 return mapping.findForward("success");
	}


}
