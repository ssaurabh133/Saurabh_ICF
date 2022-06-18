/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
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

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.DeviationApprovalDAO;
import com.cp.vo.DeviationApprovalVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/** 
 * MyEclipse Struts
 * Creation date: 09-11-2012
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class LoanDeviationBehindAction extends Action {
	/*
	 * Generated Methods
	 */
	private static final Logger logger = Logger.getLogger(LoanDeviationBehindAction.class.getName());
	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		// TODO Auto-generated method stub

		logger.info("In LoanDeviationBehindAction(execute)");
		HttpSession session = request.getSession();
	//	boolean flag=false;
		String userId=null;
		String bDate=null;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj!=null)
		{
			userId	=userobj.getUserId();
			bDate=userobj.getBusinessdate();
			
		}else{
			logger.info("here in execute method of LoanDeviationBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag=null;	
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
		//code added by neeraj 
		String functionId=(String)session.getAttribute("functionId");
		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase(""))
			functionId="0";
		int id=Integer.parseInt(functionId);
		if(id==4000122 || id==4000123)
		{
			session.setAttribute("cmAuthor","cmAuthor");
			session.setAttribute("viewLoan","viewLoan");
			session.setAttribute("viewDevJSP","viewDevJSP");			
		}
		if(id==4000106 || id==4000122)
		{
			session.removeAttribute("underWriterViewData");
			session.removeAttribute("leadNo");
			session.removeAttribute("dealHeader");
			session.removeAttribute("dealId");
			session.removeAttribute("leadInfo");
			session.removeAttribute("viewDeal");
			session.removeAttribute("dealCatList");
			session.removeAttribute("sourceTypeList");
			session.removeAttribute("checkLoginUserLevel");
			session.removeAttribute("creditApprovalList");
			session.removeAttribute("leadMValue");
			session.removeAttribute("bsflag");
	
		}
		//neeraj space end 
		String stage="";
		if(id==4000109)
			stage="DEVIATION";
		String loanId ="";
		
		if(session.getAttribute("loanId") != null) {
			loanId = session.getAttribute("loanId").toString();
		} else if (session.getAttribute("maxIdInCM") != null) {
			loanId = session.getAttribute("maxIdInCM").toString();
		}

		logger.info("In LoanDeviationBehindAction(execute) loanId: " + loanId);
		
		if ((loanId != null && !loanId.equalsIgnoreCase(""))) {
			/* changed by asesh */
			DeviationApprovalDAO service=(DeviationApprovalDAO)DaoImplInstanceFactory.getDaoImplInstance(DeviationApprovalDAO.IDENTITY);
	        logger.info("Implementation class: "+service.getClass()); 	//changed by asesh
	
			boolean st= service.saveLoanManualDeviationFromMaster(loanId,userId,bDate);
			ArrayList<DeviationApprovalVo> manualDeviationM =new ArrayList<DeviationApprovalVo>();
			logger.info("stage:::::::::::::::"+stage);
			if(stage.equalsIgnoreCase("DEVIATION")){
				manualDeviationM = service.getLoanAllDeviation(loanId,userId,functionId);
				request.setAttribute("manualDevList", manualDeviationM);
			}else{
				manualDeviationM = service.getLoanAllDeviation(loanId,"",functionId);
				request.setAttribute("manualDevList", manualDeviationM);
			}
			//request.setAttribute("list", list);
			return mapping.findForward("success");
		}

		else {
			request.setAttribute("back", "back");
			return mapping.findForward("backSuccess");
		}
	
	}
}