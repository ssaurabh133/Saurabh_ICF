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

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ChequeStatusBehindAction extends Action {
	private static final Logger logger = Logger.getLogger(ChequeStatusBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
			logger.info(" In the ChequeStatusBehindAction----------");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("in execute method of  ChequeStatusBehindAction action the session is out----------------");
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
			session.removeAttribute("arryList");
			session.removeAttribute("arrList");
			session.removeAttribute("loanID");
			session.removeAttribute("author");
			session.removeAttribute("releasenotCheck");
			session.removeAttribute("notCheck");
			
//			CreditManagementDAO dao = new CreditManagementDAOImpl();
//			ArrayList<InstructionCapMakerVO> bussinessPartnerList= dao.getbussinessPartnerList();
//			request.setAttribute("bussinessPartnerList", bussinessPartnerList);
			request.setAttribute("Active", "Yes");
			if(session.getAttribute("screenForDealNumber")==null){
			session.setAttribute("screenForLoanNumber", "screenForLoanNumber");
			String enableMobileReceiptQuery="SELECT IFNULL(PARAMETER_VALUE,'N')PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='ENABLE_MOBILE_RECEIPT'";
			String mobileReceiptValue=CommonFunction.checkNull(ConnectionDAO.singleReturn(enableMobileReceiptQuery));
			session.setAttribute("mobileReceiptValue",mobileReceiptValue);
			}

			return mapping.findForward("success");
		}

}
