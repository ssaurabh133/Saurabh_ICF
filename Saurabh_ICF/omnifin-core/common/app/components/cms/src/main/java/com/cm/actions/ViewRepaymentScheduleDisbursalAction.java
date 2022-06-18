package com.cm.actions;

import java.util.ArrayList;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cm.dao.EarlyClosureDAO;
import org.apache.struts.actions.DispatchAction;
import com.cm.dao.CreditManagementDAO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.vo.RepayScheduleVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ViewRepaymentScheduleDisbursalAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(ViewRepaymentScheduleDisbursalAction.class.getName());
	
	public ActionForward repaymentSchedule(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		logger.info("in repaymentSchedule() ------->");
		HttpSession session = request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		if(userobj!=null){
			userId=userobj.getUserId();
		}else{
			logger.info(" in repaymentSchedule method of ViewRepaymentScheduleDisbursalAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		//for check User session start
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
			
		//CreditManagementDAO dao = new CreditManagementDAOImpl();
		EarlyClosureDAO dao=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		
		
		String loanId=CommonFunction.checkNull(request.getParameter("loanId"));
		logger.info("In  repaymentSchedule() ---loanId-"+loanId);  
		ArrayList<RepayScheduleVo> repShedule=dao.getRepayScheduleDisbursal(loanId);
		request.setAttribute("repShedule", repShedule);
		logger.info("repShedule:   "+repShedule.size());
		ArrayList<RepayScheduleVo> fromloanDtl=dao.getRepaySchFieldsDetail(loanId);
		request.setAttribute("fromloanDtl", fromloanDtl);
		return mapping.findForward("viewRepaymentScheduleDisbursal");
	}
	
	public ActionForward oldRepaymentSchedule(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 
	{
	
		logger.info(" in oldRepaymentSchedule method of customer service");
		HttpSession session = request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info(" in oldRepaymentSchedule  method of ViewRepaymentScheduleDisbursalAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		
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
		
		//change by sachin
		CreditManagementDAO dao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
	     logger.info("Implementation class: "+dao.getClass());

		//end by sachin
//		CreditManagementDAO dao = new CreditManagementDAOImpl();
		EarlyClosureDAO dao1=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
		logger.info("Implementation class: "+dao1.getClass()); 
		String loanId=CommonFunction.checkNull(request.getParameter("loanId"));
		logger.info("in oldRepaymentSchedule method---loanId-"+loanId);  
		ArrayList<RepayScheduleVo> repShedule=dao.getOldRepayScheduleDisbursal(loanId);
		request.setAttribute("repShedule", repShedule);
		logger.info("repShedule:   "+repShedule.size());
		ArrayList<RepayScheduleVo> fromloanDtl=dao1.getRepaySchFieldsDetail(loanId);
		request.setAttribute("fromloanDtl", fromloanDtl);
		return mapping.findForward("viewRepaymentScheduleDisbursal");
	}

}
