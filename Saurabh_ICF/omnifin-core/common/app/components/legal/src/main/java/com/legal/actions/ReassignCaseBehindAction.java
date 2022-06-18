package com.legal.actions;

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

import com.business.legalTransactionBussiness.LegalTransactionBusinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.legal.vo.ReassignCaseVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ReassignCaseBehindAction extends Action  {
		private static final Logger logger = Logger.getLogger(ReassignCaseBehindAction.class.getName());
		
		public ActionForward execute(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			HttpSession session=request.getSession(false);
			ServletContext context = getServlet().getServletContext();
			logger.info(" ## In execute() : .........");
			
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");
			//for check User session start
			String strFlag=null;	
			if(sessionId!=null)
			{
				strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			}
			
			logger.info("strFlag .............. "+strFlag);
			if(!(CommonFunction.checkNull(strFlag).equalsIgnoreCase("")))
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
			
			ReassignCaseVo vo = new ReassignCaseVo(); //change
	        
			DynaValidatorForm reassignCaseDynaValidatorForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, reassignCaseDynaValidatorForm);
		

			LegalTransactionBusinessSessionBeanRemote bp = (LegalTransactionBusinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(LegalTransactionBusinessSessionBeanRemote.REMOTE_IDENTITY, request);
	        
	        ArrayList list=new ArrayList();
			
	        logger.info("current page link .......... " + request.getParameter("d-49520-p"));
			
			int currentPageLink = 0;

			if ((request.getParameter("d-49520-p") == null) || (request.getParameter("d-49520-p").equalsIgnoreCase("0")))
			     {
			       currentPageLink = 1;
			    }
			     else
			     {
			      currentPageLink = Integer.parseInt(request.getParameter("d-49520-p"));
			     }
			
			     logger.info("current page link ................ " + request.getParameter("d-49520-p"));
			 
			vo.setCurrentPageLink(currentPageLink);

			
			list= bp.searchReassignCaseData(vo);

		    logger.info("In reassignCaseBehindAction....list"+list.size());

			request.setAttribute("list",list);
			if(CommonFunction.checkNull(request.getAttribute("flag")).toString().equalsIgnoreCase("yes")){
				request.setAttribute("sms","No");
			}
		    return mapping.findForward("success");


}
}