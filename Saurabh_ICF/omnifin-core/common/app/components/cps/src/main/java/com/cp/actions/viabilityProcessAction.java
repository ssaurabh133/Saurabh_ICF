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
import org.apache.struts.actions.*;
import org.apache.struts.validator.DynaValidatorForm;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.Viability;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class viabilityProcessAction extends DispatchAction
{
	private static final Logger logger = Logger.getLogger(viabilityProcessAction.class.getName());	
	
	public ActionForward openViabilityView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();		
		logger.info("In viabilityProcessAction openViabilityView to open viability.jsp page ");
		boolean flag=false;
		int count=0;
		String dealId = CommonFunction.checkNull(session.getAttribute("dealId"));
		CreditProcessingDAO detail=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+detail.getClass()); 	//changed by asesh
		//CreditProcessingDAO detail = new CreditProcessingDAOImpl();	
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in viabilityProcessAction openViabilityView action the session is out----------------");
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
        if ((dealId != null && !dealId.equalsIgnoreCase(""))) {			
			
			count = detail.getProductCategaryStatus(dealId,"V");		
		    
		}
		else
		{
			request.setAttribute("back", "B");
			return mapping.findForward("backToFirst");			
		}
		if(count>0)
	    {
			ArrayList list=detail.getViability(dealId);
			if(list.size()>0)
			{
				request.setAttribute("list", list);
			}
			return mapping.findForward("success");
			
	    }
	    else
	    {
	    	request.setAttribute("checkViabilityTab", "checkViabilityTab");
	    	return mapping.findForward("checkProductCat");
	    }			
		
			
	} 
	
	
	public ActionForward openViability(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();		
		logger.info("In viabilityProcessAction openViability to open viability.jsp page ");
		boolean flag=false;
		int count=0;
		String dealId = CommonFunction.checkNull(session.getAttribute("dealId"));
		CreditProcessingDAO detail=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+detail.getClass()); 	//changed by asesh
		//CreditProcessingDAO detail = new CreditProcessingDAOImpl();	
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in viabilityProcessAction openViability action the session is out----------------");
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
        if ((dealId != null && !dealId.equalsIgnoreCase(""))) {	
			
			
			count = detail.getProductCategaryStatus(dealId,"V");
			
		    
		}
		else
		{
			request.setAttribute("back", "B");
			return mapping.findForward("backToFirst");
			
		}
		if(count>0)
	    {
			ArrayList list=detail.getViability(dealId);
			if(list.size()>0)
			{
				request.setAttribute("list", list);
			}
			return mapping.findForward("success");
			
	    }
	    else
	    {
	    	request.setAttribute("checkViabilityTab", "checkViabilityTab");
	    	return mapping.findForward("checkProductCatNew");
	    }			
		
			
		} 
	public ActionForward saveViability(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		DynaValidatorForm viabilityDynaValidatorForm = (DynaValidatorForm) form;
		Viability vo=new Viability();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, viabilityDynaValidatorForm);
		logger.info("In viabilityProcessAction saveViability to open viability.jsp page ");
        String dealid=request.getParameter("dealid");
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
		{
			logger.info("here in viabilityProcessAction saveViability action the session is out----------------");
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
		CreditProcessingDAO detail=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+detail.getClass()); 	//changed by asesh
		//CreditProcessingDAO detail = new CreditProcessingDAOImpl();	
		vo.setMakerid(userobj.getUserId());
		vo.setMakerdate(userobj.getBusinessdate());
		vo.setDealid(dealid);
		
		ArrayList list=detail.saveViability(vo);
		if(list.size()>0)
		{
			request.setAttribute("list", list);
			request.setAttribute("save", "");
		}
		
		return mapping.findForward("success");
			
		} 
}