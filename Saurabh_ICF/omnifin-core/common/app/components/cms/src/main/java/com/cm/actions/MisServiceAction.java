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
import org.apache.struts.validator.DynaValidatorForm;

import com.cm.dao.CreditManagementDAO;
import com.cm.vo.LoanInitAuthorVo;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class MisServiceAction extends Action {
	
	private static final Logger logger = Logger.getLogger(MisServiceAction.class.getName());
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
		logger.info("Inside  MisServiceAction action");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");		
		if(userobj==null)
		{
			//logger.info("here in saveNmbMapping method of  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
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
		
		String userId=null;
		String bDate=null;
		String sms=null;
		String loanId=null;
		String loanNo=null;
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		LoanInitAuthorVo vo=new LoanInitAuthorVo();
		DynaValidatorForm formbeen= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);
		loanNo  = CommonFunction.checkNull(request.getParameter("loanNo"));
		loanId  = CommonFunction.checkNull(request.getParameter("loanId"));
		//String serviceBranch = CommonFunction.checkNull(request.getParameter("serviceBranch"));
		String lbxserviceBranchID = CommonFunction.checkNull(request.getParameter("lbxserviceBranchID"));
		//vo.setServiceBranch(serviceBranch);
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		vo.setLoanId(loanId);
		vo.setLoanNo(loanNo);
		vo.setLbxserviceBranchID(lbxserviceBranchID);

		CreditManagementDAO dao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass());
		boolean status = dao.updateServiceBranch(vo,loanNo);
		if(status)
		{
			sms="S";
			request.setAttribute("sms",sms);
			request.setAttribute("save", "save");
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
			request.setAttribute("save", "save");
		}
		ArrayList serviceBranch1=dao.getServiceBranch(loanId);
		 request.setAttribute("serviceBranch", serviceBranch1);
		
		return mapping.findForward("success");
}
}
