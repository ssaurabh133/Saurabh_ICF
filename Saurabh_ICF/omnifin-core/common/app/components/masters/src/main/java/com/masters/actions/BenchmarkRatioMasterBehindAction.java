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

import com.business.ejbClient.CommonMasterBussinessSessionBeanRemote;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.BenchmarkRatioVo;


public class BenchmarkRatioMasterBehindAction extends Action{
	private static final Logger logger = Logger.getLogger(BenchmarkRatioMasterBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("In  BenchmarkRatioMasterBehindAction .........................");
		HttpSession session = request.getSession();
		ServletContext context = getServlet().getServletContext();
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
		DynaValidatorForm BenchmarkRatioMasterDynaValidatorForm=(DynaValidatorForm)form;
		BenchmarkRatioVo vo=new BenchmarkRatioVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, BenchmarkRatioMasterDynaValidatorForm);
		
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
 
        logger.info("current page link .......... "+request.getParameter("d-3439640-p"));
		
		int currentPageLink = 0;
		if(request.getParameter("d-3439640-p")==null || request.getParameter("d-3439640-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-3439640-p"));
		}
		
		logger.info("current page link ................ "+request.getParameter("d-3439640-p"));
		
		vo.setCurrentPageLink(currentPageLink);
        ArrayList<BenchmarkRatioVo>  BenchmarkRatioMasterSearchList = new ArrayList<BenchmarkRatioVo> ();
        BenchmarkRatioMasterSearchList=bp.getBenchmarkRatioMasterList(vo);
        if(BenchmarkRatioMasterSearchList.size()>0){
        request.setAttribute("BenchmarkRatioMasterSearchList", BenchmarkRatioMasterSearchList);
        }
		return mapping.findForward("success");
	}
}
