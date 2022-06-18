package com.cm.actions;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.connect.CommonFunction;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ViewReceivableAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ViewReceivableAction.class.getName());
	public ActionForward viewReceivable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("In  ViewReceivableAction viewReceivable()----------------------------"); 
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in execute method of ViewReceivableAction action the session is out----------------");
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
		
		int loanId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("loanId")));
		String bpType=(CommonFunction.checkNull(request.getParameter("bpType")));
		int adviseId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("adviseId")));
		logger.info("In viewReceivable---adviseId---- by getpara-"+request.getParameter("adviseId")); 
		logger.info("In viewReceivable---adviseId---- by getpara-"+adviseId);  
//		CreditManagementDAO dao = new CreditManagementDAOImpl();
		//ArrayList<PaymentMakerForCMVO> getPayableList= dao.getViewPayable(loanId, bpType,adviseId);
		//request.setAttribute("getPayableList", getPayableList);
		request.setAttribute("loanId", loanId);
		request.setAttribute("bpType", bpType);
		request.setAttribute("adviseId", adviseId);
		request.setAttribute("canForward", "canForward");
		//.info("getPayableList    Size:---"+getPayableList.size());
   return mapping.findForward("viewList");
}
}