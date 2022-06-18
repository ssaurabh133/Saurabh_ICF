package com.masters.actions;

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

import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.ManulaDeviationVO;


public class ManualDeviationMasterBehindAction extends Action {
	private static final Logger logger = Logger.getLogger(ManualDeviationMasterBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("In ManualDeviationMasterBehindAction.....###");
		HttpSession session = request.getSession();
		boolean flag=false;
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
		   

			
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
	        ManulaDeviationVO vo = new ManulaDeviationVO();
		
		    DynaValidatorForm CommonDynaValidatorForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CommonDynaValidatorForm);				
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
			
			 vo.setCurrentPageLink(String.valueOf(currentPageLink));
			 ArrayList<ManulaDeviationVO> list=new ArrayList<ManulaDeviationVO>();
			 list=cpm.getManualDeviationRecords(currentPageLink);
			 logger.info("list ................ "+list.size());
			 if(list.size()>0)
			 {
			 request.setAttribute("list", list);		
		
			 }
		
	        return mapping.findForward("SearchPage");
		}
	}