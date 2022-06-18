/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cp.collateralVerification.actions;

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
import com.cp.collateralVerification.dao.CollateralVerificationDAO;
import com.cp.collateralVerification.dao.CollateralVerificationDAOImpl;
import com.cp.vo.CommonDealVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class CollateralCompletionBehindAction extends Action {
	private static final Logger logger = Logger.getLogger(CollateralCompletionBehindAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)  throws Exception{
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";
		String userId="";
		String branch="";
		
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branch=userobj.getBranchId();
		}
		
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

		DynaValidatorForm CommonDealDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CommonDealDynaValidatorForm);
		vo.setBranchId(userobj.getBranchId());
		vo.setUserId(userId);		
				
		CollateralVerificationDAO service = new CollateralVerificationDAOImpl();
		ArrayList<CommonDealVo> dealdetails = service.initialCollateralVerifiationCompletionData(vo);
		int dealDetailListSize = dealdetails.size();
		if(dealDetailListSize>0)
		{
			request.setAttribute("list", dealdetails);
		}
		else
		{
			request.setAttribute("message","asd");
		}
	    logger.info("In collateralCompletionSearch....list: "+dealdetails.size());
	    
		return mapping.findForward("collateralCompletionSearch");
	}
}