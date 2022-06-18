package com.cm.actions;

/*
Author: Vishal SIngh 
Date:27-03-2012 
Purpose:Repay Schedule Maker/Author 

*/

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

import com.cm.dao.RepayScheduleDAO;
import com.cm.vo.UpdateRepayscheduleSearchVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;



public class UpdateRepayscheduleBehindAction extends Action {
	
	private static final Logger logger = Logger.getLogger(UpdateRepayscheduleBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
logger.info("Inside UpdateRepayscheduleBehindAction...........");
		
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in  UpdateRepayscheduleBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		UpdateRepayscheduleSearchVO vo = new UpdateRepayscheduleSearchVO();
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
		
		String makerSatge="";
		if(session.getAttribute("makerSatge")!=null)
			makerSatge=session.getAttribute("makerSatge").toString();
		logger.info("*********************** makerSatge ***************************" +makerSatge);
		String type = "F";
		if(makerSatge!=null && !CommonFunction.checkNull(makerSatge).equals("") && makerSatge.equalsIgnoreCase("M")){
			type="P";
		}
		
			
		DynaValidatorForm repayScheduleActionDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,repayScheduleActionDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		//vo.setStage(type);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		
		String forward=(String)request.getParameter("forward");
		if(forward!=null && !forward.equals("") && forward.equals("true")){
		request.setAttribute("message", (String)request.getAttribute("message"));
		vo.reset(mapping, request);
		}
		
		RepayScheduleDAO service=(RepayScheduleDAO)DaoImplInstanceFactory.getDaoImplInstance(RepayScheduleDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		ArrayList<UpdateRepayscheduleSearchVO> repayScheduleList = service.searchRepaySchedule(vo,type);
		
		
		if(repayScheduleList.size()==0)
		{
			//request.setAttribute("message","N");
			
		}
		else
		{
			request.setAttribute("list", repayScheduleList);
			
		}
		
		
		
		logger.info("Inside the action of *********** message ******"+(String)request.getAttribute("message"));
		return mapping.findForward("searchRepayschedule");
	}

}
