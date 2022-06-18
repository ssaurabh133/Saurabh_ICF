package com.caps.action;

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
import org.apache.struts.validator.DynaValidatorForm;

import com.caps.VO.CRFollowUpTrailsDtlVO;
import com.caps.dao.CollDAO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class FollowUpResolutionDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(FollowUpResolutionDispatchAction.class.getName());
	
	
	//Changes Start for Resolution Detail
		public ActionForward fetchActionResolution(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response)
		throws Exception {
			logger.info("In  FollowUpResolutionDispatchAction fetchActionResolution()----------------------------"); 
			HttpSession session=request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here fetchActionResolution method of FollowUpResolutionDispatchAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			boolean flag=false;
			Object sessionId = session.getAttribute("sessionID");
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
			DynaValidatorForm ContactRordingFollowUpForm = (DynaValidatorForm) form;
			CRFollowUpTrailsDtlVO vo=new CRFollowUpTrailsDtlVO();
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo,ContactRordingFollowUpForm);
			String loanId="";
			//loanId=CommonFunction.checkNull(session.getAttribute("loanid").toString());
		    loanId=CommonFunction.checkNull(request.getParameter("loanId").toString());
			String actionId=CommonFunction.checkNull(request.getParameter("actionId").toString());
			String followupId=CommonFunction.checkNull(request.getParameter("followupId").toString());
			logger.info("Actionid::::::::::::::::::::::"+actionId);
			CollDAO coll=(CollDAO)DaoImplInstanceFactory.getDaoImplInstance(CollDAO.IDENTITY);
			logger.info("Implementation class: "+coll.getClass());
			vo.setFollActionId(actionId);
			vo.setLoanId(loanId);
			String ActionDesc=coll.getActionDesc(actionId);
			ArrayList<CRFollowUpTrailsDtlVO> actionResolutionList= coll.getActionResolutionDtl(vo);
			logger.info("Size of Action Resolution List::::::::"+actionResolutionList.size());
			ArrayList<CRFollowUpTrailsDtlVO> resolTypeList=coll.getResolutionFollowUpType();
			if(resolTypeList.size()>0)
			{
				request.setAttribute("resolTypeList", resolTypeList);
			}
			ArrayList<CRFollowUpTrailsDtlVO> resolStatusList=coll.getResolutionFollowUpStatus();
			if(resolStatusList.size()>0)
			{
				request.setAttribute("resolStatusList", resolStatusList);
			}
			if(actionResolutionList.size()>0){
			//logger.info("actionResolutionList size is >0");
			request.setAttribute("actionResolutionList", actionResolutionList);
			request.setAttribute("totalRecordSize", actionResolutionList.size());
			request.setAttribute("data", "Y");
			}else{
				request.setAttribute("data", "N");	
			}
			request.setAttribute("actionId", actionId);
			request.setAttribute("ActionDesc",ActionDesc);
			request.setAttribute("followupId", followupId);
			return mapping.findForward("resolveInfo");
					
		}
		public ActionForward saveActionResolutionDtl(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response)
		throws Exception {
			logger.info("In  ContactRecordingButtonsAction saveActionResolutionDtl()----------------------------"); 
			HttpSession session=request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here saveActionResolutionDtl method of ContactRecordingButtonsAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			boolean flag=false;
			Object sessionId = session.getAttribute("sessionID");
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
			DynaValidatorForm ContactRordingFollowUpForm = (DynaValidatorForm) form;
			CRFollowUpTrailsDtlVO vo = new CRFollowUpTrailsDtlVO();
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo,ContactRordingFollowUpForm);
			CollDAO coll=(CollDAO)DaoImplInstanceFactory.getDaoImplInstance(CollDAO.IDENTITY);
			String followupId=CommonFunction.checkNull(request.getParameter("followupId").toString().trim());
			if(!followupId.equalsIgnoreCase(""))
			{
				vo.setFollowUpId(Integer.parseInt(followupId));
			}
			logger.info("Implementation class: "+coll.getClass());
			String stat=coll.SaveActionResolutionDtl(vo);
			if(CommonFunction.checkNull(stat).trim().equalsIgnoreCase("S"))
			{
				request.setAttribute("msg", "S");
			}
			else
			{
				request.setAttribute("msg", "E");
			}
			return mapping.findForward("saveResolve");
			
		}
		//Changes End For Resolution Detail


}
