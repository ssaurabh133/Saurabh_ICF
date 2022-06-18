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
import org.apache.struts.actions.DispatchAction;
import com.cm.dao.DeferralDAO;
import com.cm.dao.PartPrePaymentDAO;
import com.cm.dao.RepricingDAO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.vo.RepayScheduleVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class NewRepaymentScheduleDeferralAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(NewRepaymentScheduleDeferralAction.class.getName());
	
	public ActionForward newRepaymentScheduleDeferral(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		logger.info("New Repayment Schedule Deferral Action");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		int compid =0;
		if(userobj!=null){
			userId = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
				compid=userobj.getCompanyId();
		}else{
			logger.info("here in newRepaymentScheduleDeferral method of NewRepaymentScheduleDeferralAction action the session is out----------------");
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
		
		RepayScheduleVo vo = new RepayScheduleVo();

		
		vo.setCompanyId(compid);
		vo.setAuthorId(userId);
		vo.setAuthorDate(businessDate);
		//EarlyClosureDAO edao = new EarlyClosureDAOImpl();
		//DeferralDAO dao = new DeferralDAOImpl();
		DeferralDAO dao=(DeferralDAO)DaoImplInstanceFactory.getDaoImplInstance(DeferralDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		String reschId = CommonFunction.checkNull(request.getParameter("reschId"));
		logger.info("In NewRepaymentScheduleDeferralAction---loanId-"+loanId);  
//		ArrayList<RepayScheduleVo> fromloanDtl=edao.getRepaySchFieldsDetail(loanId);
//		request.setAttribute("fromloanDtl", fromloanDtl);
		ArrayList<RepayScheduleVo> repShedule=dao.getNewRepayScheduleDeferral(vo,loanId,reschId);
		request.setAttribute("repShedule", repShedule);
if(repShedule.size()>0){
		if(repShedule.get(0).getProcvalstatus().equalsIgnoreCase("E")){
			request.setAttribute("procStatus",repShedule.get(0).getProcval());
		}
}
		logger.info("repShedule:   "+repShedule.size());
		return mapping.findForward("newRepaymentScheduleDeferral");
	}
	
	public ActionForward newRepaymentSchedulePartPayment(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		logger.info("New Repayment Schedule Part Payment Action");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		int compid =0;
		if(userobj!=null){
			userId = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
				compid=userobj.getCompanyId();
		}else{
			logger.info("here in newRepaymentSchedulePartPayment method of  NewRepaymentScheduleDeferralAction action the session is out----------------");
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
		
		RepayScheduleVo vo = new RepayScheduleVo();
		vo.setCompanyId(userobj.getCompanyId());
		vo.setAuthorId(userobj.getUserId());
		vo.setAuthorDate(userobj.getBusinessdate());
		//EarlyClosureDAO edao = new EarlyClosureDAOImpl();
		PartPrePaymentDAO dao=(PartPrePaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PartPrePaymentDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		String reschId = CommonFunction.checkNull(request.getParameter("reschId"));
		logger.info("In newRepaymentSchedulePartPayment---loanId-"+loanId); 
		//ArrayList<RepayScheduleVo> fromloanDtl=edao.getRepaySchFieldsDetail(loanId);
		//request.setAttribute("fromloanDtl", fromloanDtl);
		ArrayList<RepayScheduleVo> repShedule=dao.getNewRepaySchedulePartPayment(vo,loanId,reschId);
		request.setAttribute("repShedule", repShedule);
		logger.info("repShedule:   "+repShedule.size());
//		if(repShedule.size()==0)
//			request.setAttribute("noData","noData");
if(repShedule.size()>0){
		if(repShedule.get(0).getProcvalstatus().equalsIgnoreCase("E")){
			request.setAttribute("procStatus",repShedule.get(0).getProcval());
		}
}
		return mapping.findForward("newRepaymentSchedulePartPayment");
		
	}
	
	public ActionForward newRepaymentScheduleRepricing(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception {
		logger.info("New Repayment Schedule Repricing Action");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		int compid =0;
		if(userobj!=null){
			userId = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
				compid=userobj.getCompanyId();
		}else{
			logger.info("here in newRepaymentScheduleRepricing method of NewRepaymentScheduleDeferralAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
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

		
		RepayScheduleVo vo = new RepayScheduleVo();
		vo.setCompanyId(compid);
		vo.setAuthorId(userId);
		vo.setAuthorDate(businessDate);
		//EarlyClosureDAO edao = new EarlyClosureDAOImpl();
		RepricingDAO dao=(RepricingDAO)DaoImplInstanceFactory.getDaoImplInstance(RepricingDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		String reschId = CommonFunction.checkNull(request.getParameter("reschId"));
		String rePricingCondition = CommonFunction.checkNull(request.getParameter("rePricingCondition"));
		logger.info("In newRepaymentScheduleRepricing---loanId-"+loanId+" Parameter: "+rePricingCondition+" reschId: "+reschId);
		
			if(rePricingCondition.equalsIgnoreCase("I"))
			{
				//boolean checkOldNewTenureFlag=dao.checkOldNewTenureAreEqual(loanId, reschId,"R");
				//logger.info("checkOldNewTenureFlag:----"+checkOldNewTenureFlag);
				//if(checkOldNewTenureFlag){
				ArrayList<RepayScheduleVo> repShedule=dao.getNewRepayScheduleRepricing(vo,loanId,reschId);
				request.setAttribute("repShedule", repShedule);
				logger.info("repShedule:   "+repShedule.size());
				if(repShedule.size()>0){
					if(repShedule.get(0).getProcvalstatus().equalsIgnoreCase("E")){
					request.setAttribute("procStatus",repShedule.get(0).getProcval());
				}
}

			}
		    else if (rePricingCondition.equalsIgnoreCase("T"))
		    {
		      ArrayList repShedule = dao.getNewRepayScheduleRepricing(vo, loanId, reschId);
		      request.setAttribute("repShedule", repShedule);
		      logger.info("repShedule:   " + repShedule.size());
		      if ((repShedule.size() > 0) && 
		        (((RepayScheduleVo)repShedule.get(0)).getProcvalstatus().equalsIgnoreCase("E"))) {
		        request.setAttribute("procStatus", ((RepayScheduleVo)repShedule.get(0)).getProcval());
		      }
		    }
				else{
					
					ArrayList<RepayScheduleVo> repShedule=dao.getNewRepayScheduleRepricing(vo,loanId,reschId);
					request.setAttribute("repShedule", repShedule);
					logger.info("repShedule:   "+repShedule.size());
					if(repShedule.size()==0){
						request.setAttribute("noData","noData");
						}
					}
		
		
		
		
		
		return mapping.findForward("newRepaymentScheduleRepricing");
	}
}
