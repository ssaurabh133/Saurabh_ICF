package com.legal.actions;

import java.util.ArrayList;
import java.util.Properties;

import javax.naming.InitialContext;
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
import com.legal.vo.LegalNoticeInitiationVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class NoticeCheckerBehindAction extends Action  {
		private static final Logger logger = Logger.getLogger(NoticeCheckerBehindAction.class.getName());
		
		public ActionForward execute(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			HttpSession session=request.getSession(false);
			ServletContext context = getServlet().getServletContext();
			logger.info(" ## In execute() : .........");
			
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
			
			String userId="";
			String bDate="";
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bDate=userobj.getBusinessdate();
			}
			
			LegalNoticeInitiationVo vo = new LegalNoticeInitiationVo(); //change
	        
			DynaValidatorForm NoticeCheckerDynaValidatorForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, NoticeCheckerDynaValidatorForm);
			
			vo.setMakerId(userId);
			vo.setMakerDate(bDate);
			request.setAttribute("userId",userId);
			session.setAttribute("userId", userId);
			
			Properties props = new Properties();
			props.load(request.getSession().getServletContext().getResourceAsStream("/WEB-INF/jndi.properties"));   
	        InitialContext ic = new InitialContext(props);
	        String applName=props.getProperty("enterprise.application.name");
	        String remoteBean = props.getProperty("legal.transaction.business.session.bean.remote");
	        logger.info("remoteBean : "+remoteBean);
	        logger.info("applName : "+applName);
	        String remoteBeanName=CommonFunction.checkNull(applName)+CommonFunction.checkNull(remoteBean);
	        logger.info("remoteBeanName: "+remoteBeanName);
	        LegalTransactionBusinessSessionBeanRemote bp=(LegalTransactionBusinessSessionBeanRemote)ic.lookup(remoteBeanName);
	        
	        ArrayList list=new ArrayList();
			
			logger.info("current page link .......... "+request.getParameter("d-49520-p"));
			
			int currentPageLink = 0;
			
			
			
			vo.setCurrentPageLink(currentPageLink);

			//change from here
			list= bp.searchLegalNoticeInitiationCheckerData(vo);

		    logger.info("In CaseTypeMasterBehindAction....list"+list.size());
			
		    request.setAttribute("list", list);

			request.setAttribute("list",list);
			if(CommonFunction.checkNull(request.getAttribute("flag")).toString().equalsIgnoreCase("yes")){
				request.setAttribute("sms","No");
			}
		    return mapping.findForward("success");


}
}