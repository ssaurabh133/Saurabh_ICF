package com.cm.actions;

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
import com.cm.dao.LoanInitiationDAO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class LoanDetailsForClosureAction extends Action{
private static final Logger logger = Logger.getLogger(LoanDetailsForClosureAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		logger.info(" In the LoanDetailsForClosureAction----------");
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in execute method of LoanDetailsForClosureAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		CreditProcessingDAO creditProcessing=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+creditProcessing.getClass()); 		//changed by asesh
		//CreditProcessingDAO creditProcessing = new CreditProcessingDAOImpl();
		//ArrayList cycle = creditProcessing.getCycleDateList(loanId,"LIM");
		//session.setAttribute("cycle", cycle);
		logger.info(" In the loanInitMakerAuthor---cycle-------");
		
		boolean flag=false;
		
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

		//CreditManagementDAO dao = new CreditManagementDAOImpl();
		
		LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 

		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		
		logger.info(" In the LoanInitBehindAction------loanId----"+loanId);

		ArrayList loanList = dao.getloanListInLoan(loanId);
		if(loanId!=null && !loanId.equalsIgnoreCase(""))
		{
			ArrayList loanHeader = dao.getLoanHeader(loanId);
			session.setAttribute("loanHeader", loanHeader);
			logger.info("Size: "+loanList.size());
			session.setAttribute("loanList", loanList);
			session.setAttribute("loanId",loanId);
		}
		else
		{
			session.removeAttribute("loanList");
			session.removeAttribute("loanId");
		}
		
		return mapping.findForward("success");
	}
}
