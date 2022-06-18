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
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.ApprovalLevelDefVo;

public class UWApprovalLevelDefAuthorBehindAction extends Action  {
	
	private static final Logger logger = Logger.getLogger(UWApprovalLevelDefAuthorBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In execute ::::::::::::::::::.........");
	
		HttpSession session = request.getSession();
		boolean flag=false;
		ServletContext context = getServlet().getServletContext();
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
	
		
		ApprovalLevelDefVo Vo = new ApprovalLevelDefVo();
		DynaValidatorForm CommonDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(Vo, CommonDynaValidatorForm);		
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
        
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
		
		Vo.setCurrentPageLink(currentPageLink);
		
		
		ArrayList list=new ArrayList();
		int list1=0;
		String mcFlag=cpm.getMakerCheckerFlag();
		if(CommonFunction.checkNull(mcFlag).equalsIgnoreCase("Y")){
		list= cpm.searchApprovalLevelDefAuthor(Vo);
		list1 = cpm.getApprovalfromPmst();
		}
		logger.info("----------OpenApprovalLevelSearchJsp--"+list1);
		if(list1>2 && list1<10){
			request.setAttribute("pmstSize", list1);					
		}else if(list1<3 || list1>9){
			request.setAttribute("pmstSize", 3);
		}else {
			request.setAttribute("pmstSize", 3);
		}
		
		logger.info("In UWApprovalLevelDefAuthorBehindAction....list"+list.size());
		
		if (list.size() > 0) {
			 	request.setAttribute("list",list);
				request.setAttribute("Vo",Vo);				
		}
	    else{
				request.setAttribute("sms","No");
				request.setAttribute("Vo",Vo);
		}
	
	return mapping.findForward("success");
	}
}
