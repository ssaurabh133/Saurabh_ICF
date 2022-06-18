package com.cp.actions;

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
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.DealClosureDAO;
import com.cp.vo.DealCancellationVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ClosureMakerBehindAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ClosureMakerBehindAction.class.getName());

	public ActionForward dealCancellationMakerSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {	
	
		logger.info("In  ClosureMakerBehindAction"); 
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		if(userobj==null){
			logger.info(" in ClosureMakerBehindAction the session is out----------------");
			return mapping.findForward("sessionOut");
		}
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

		DealCancellationVo vo = new DealCancellationVo();
		
		logger.info("In Vo : " + vo);
			
		if(request.getParameter("value") != null || request.getParameter("value") == "S"){
			DynaValidatorForm dealCancellationForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, dealCancellationForm);
			logger.info("request.getParameter............ "+request.getParameter("value"));
		}else{
			vo.setLbxDealNo("") ;
	     }

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
		vo.setMakerId(userId);
		
		/* changed by asesh */
		
		DealClosureDAO leDAO=(DealClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(DealClosureDAO.IDENTITY);
        logger.info("Implementation class: "+leDAO.getClass());
		// DealClosureDAOImpl leDAO = new DealClosureDAOImpl();
        
        /* end by asesh */
		
		ArrayList<DealCancellationVo> list = leDAO.searchDealCancellation(vo,"P", request);
		logger.info("In dealCancellationMakerSearch.......list "+list.size());
		request.setAttribute("list",list);
		logger.info("Before forward makerSearch");
		return mapping.findForward("makerSearch");
	}
	
	public ActionForward dealAuthorSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("In ClosureMakerBehindAction............ dealAuthorSearch ");

		DealCancellationVo vo = new DealCancellationVo();
		HttpSession session = request.getSession();

		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{
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
		/* changed by asesh */
		DealClosureDAO leDAO=(DealClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(DealClosureDAO.IDENTITY);
        logger.info("Implementation class: "+leDAO.getClass());
		//DealClosureDAO leDAO = new DealClosureDAOImpl();
		if(request.getParameter("value") != null || request.getParameter("value") == "M"){
	        DynaValidatorForm LimitHanSearchDynaValidatorForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, LimitHanSearchDynaValidatorForm);	
			
			logger.info("request.getParameter............ "+request.getParameter("value"));
		}else{
			vo.setLbxDealNo("") ;
			//vo.setLbxLoanNo("");
			//vo.setLoanNo("");
			//vo.setDealNo("");
	     }

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
		vo.setMakerId(userId);
		
		ArrayList<DealCancellationVo> list = leDAO.searchDealCancellation(vo,"F", request);
		logger.info("In ClosureMakerBehindAction..Author..list"+list.size());
		request.setAttribute("AuthorSearch","AuthorSearch");
		request.setAttribute("list",list);
		
		return mapping.findForward("authorSearch");
	}
}
