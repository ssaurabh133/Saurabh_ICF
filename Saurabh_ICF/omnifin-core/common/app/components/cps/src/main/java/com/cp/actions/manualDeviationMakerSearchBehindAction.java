/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cp.actions;

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

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.DeviationApprovalDAO;
import com.cp.vo.CommonDealVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/** 
 * MyEclipse Struts
 * Creation date: 11-28-2011
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class manualDeviationMakerSearchBehindAction extends Action {
private static final Logger logger = Logger.getLogger(manualDeviationMakerSearchBehindAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
	
		logger.info("In manualDeviationMakerSearchBehindAction  ");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
			session.setAttribute("userId", userId);
			session.setAttribute("branchId", branchId);
		}else{
			logger.info("here in execute method of manualDeviationMakerSearchBehindAction action the session is out----------------");
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
		
		CommonDealVo vo = new CommonDealVo();
		DynaValidatorForm CommonDealDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CommonDealDynaValidatorForm);
		int currentPageLink = 0;
		
		logger.info("current page link .......... "+request.getParameter("d-49520-p"));
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		}
		
//		String userId=request.getParameter("userId");
		
		logger.info("getUserId() .parameter CommonDealDynaValidatorForm............... "+CommonDealDynaValidatorForm.getString("userId"));
		
		vo.setCurrentPageLink(currentPageLink);
		vo.setBranchId(branchId);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		
	vo.setRecStatus("P");
			vo.setFinancialDealStatus("P");
		//}
			/* changed by asesh */
			DeviationApprovalDAO dao=(DeviationApprovalDAO)DaoImplInstanceFactory.getDaoImplInstance(DeviationApprovalDAO.IDENTITY);
	        logger.info("Implementation class: "+dao.getClass());
	        /* End by asesh */
	        ArrayList<CommonDealVo> manualDeviationMakerSearch = dao.manualDeviationMakerSearch(vo);
		if(manualDeviationMakerSearch.size()>0)
		{
			
			logger.info("manualDeviationMakerSearch....list: "+manualDeviationMakerSearch.size());
		    request.setAttribute("list", manualDeviationMakerSearch);
		}
		else
		{
			request.setAttribute("sms","N");
		}
	    
		session.removeAttribute("pParentGroup");
	
		return mapping.findForward("deviationMakerSearch");	
	
	}
}