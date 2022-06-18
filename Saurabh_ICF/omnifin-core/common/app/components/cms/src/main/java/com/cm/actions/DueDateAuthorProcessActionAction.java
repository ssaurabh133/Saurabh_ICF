/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
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
import org.apache.struts.validator.DynaValidatorForm;
import com.cm.dao.RepayScheduleDAO;
import com.cm.vo.LoanInitAuthorVo;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/** 
 * MyEclipse Struts
 * Creation date: 03-29-2012
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class DueDateAuthorProcessActionAction extends Action {
	/*
	 * Generated Methods
	 */
	private static final Logger logger = Logger.getLogger(DueDateAuthorProcessActionAction.class.getName());
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
		
		logger.info("In DueDateAuthorProcessActionAction for submitting approval status");
		 
	    HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		 int compId=0;
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
				compId= userobj.getCompanyId();
		}else{
			logger.info("here execute method of DueDateAuthorProcessActionAction action the session is out----------------");
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
	    String loanId = "";

		if(session.getAttribute("loanId") != null) {

			loanId = session.getAttribute("loanId").toString();
		} 
        
		logger.info("In DueDateAuthorProcessActionAction loan id: " + loanId);


	 DynaValidatorForm ApproveDueDateDynaActionForm=(DynaValidatorForm)form;
	 LoanInitAuthorVo vo =new LoanInitAuthorVo();
	 org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ApproveDueDateDynaActionForm);
	 vo.setRschId(ApproveDueDateDynaActionForm.getString("reschId"));
	 vo.setLoanId(loanId);
	 vo.setUserId(userId);
	 vo.setCompanyId(""+compId);
	 vo.setBussinessDate(bDate);
	 RepayScheduleDAO service=(RepayScheduleDAO)DaoImplInstanceFactory.getDaoImplInstance(RepayScheduleDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
	 String message="";
	 String status="";
	 String checkBillFlag = service.getBillFlag(vo);
	 logger.info("Bill flag : " + checkBillFlag);
	 logger.info("In DueDateAuthorProcessActionAction status: "+status+" and vo.getDecision() "+vo.getDecision());
	 if((CommonFunction.checkNull(checkBillFlag).equalsIgnoreCase("Y")) && (CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase("A")))
		 status="Effective from installment is already billed.";
	 else
		 status=service.saveDueDateAuthor(vo);
	 
	 if(vo.getDecision().equalsIgnoreCase("A") && CommonFunction.checkNull(status).equalsIgnoreCase("S"))
	 {
		 message="S" ;
	 }
	 else if(vo.getDecision().equalsIgnoreCase("X") && CommonFunction.checkNull(status).equalsIgnoreCase("S"))
	 {
		 message="X" ;
	 }
	 else if(vo.getDecision().equalsIgnoreCase("P") && CommonFunction.checkNull(status).equalsIgnoreCase("S"))
	 {
		 message="P" ;
	 }
	 else  if(!CommonFunction.checkNull(status).equalsIgnoreCase("") && !CommonFunction.checkNull(status).equalsIgnoreCase("S"))
	 {
		 message=status ;
	 }
	   request.setAttribute("message",  message);
	return mapping.findForward("success");
	}
}