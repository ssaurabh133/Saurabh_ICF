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

import com.business.legalMasterBussiness.LegalMasterBusinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.legal.vo.NoticeTypeMasterVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class NoticeTypeMasterBehindAction extends Action  {
		private static final Logger logger = Logger.getLogger(NoticeTypeMasterBehindAction.class.getName());
		
		public ActionForward execute(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			HttpSession session=request.getSession(false);
			ServletContext context = getServlet().getServletContext();
			logger.info(" ## In NoticeTypeMasterBehindAction() : .........");
			
	
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");
			//for check User session start
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
			
			NoticeTypeMasterVo vo = new NoticeTypeMasterVo(); //change
	        
			DynaValidatorForm noticeTypeMasterDynaValidatorForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, noticeTypeMasterDynaValidatorForm);
			//TestVO vo1 = new TestVO();

			//org.apache.commons.beanutils.BeanUtils.copyProperties(vo1, CommonDynaValidatorForm);

			LegalMasterBusinessSessionBeanRemote bp = (LegalMasterBusinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(LegalMasterBusinessSessionBeanRemote.REMOTE_IDENTITY, request);
	        
	        ArrayList list=new ArrayList();
			
			logger.info("current page link .......... "+request.getParameter("d-49520-p"));
			
			int currentPageLink = 0;
			if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
			{
				currentPageLink=1;
			}
			else
			{
				currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
			}
			
			logger.info("current page link ................ "+request.getParameter("d-49520-p"));
			
			vo.setCurrentPageLink(currentPageLink);
			
			//change from here
			list= bp.searchNoticeTypeMasterData(vo);

		    logger.info("In NoticeTypeMasterBehindAction....list"+list.size());
			
		    request.setAttribute("list", list);
			
			logger.info("list.isEmpty(): "+list.isEmpty());
			request.setAttribute("list",list);
			if(CommonFunction.checkNull(request.getAttribute("flag")).toString().equalsIgnoreCase("yes")){
				request.setAttribute("sms","No");
			}
		    return mapping.findForward("success");


}
}