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

import com.commonFunction.daoImplMYSQL.commonFunctionDaoImpl;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.NhbMappingDAO;
import com.cp.vo.NhbMappingVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class NhbMappingAction extends DispatchAction 
{
	private static final Logger logger = Logger.getLogger(NhbMappingAction.class.getName());
	static boolean fs = false;
		
	public ActionForward saveNhbMapping(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		logger.info("In saveNmbMapping() OF NhbMappingAction");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");		
		if(userobj==null)
		{
			logger.info("here in saveNmbMapping method of  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
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
		
		String userId=null;
		String bDate=null;
		String sms=null;
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		NhbMappingVo vo=new NhbMappingVo();
		DynaValidatorForm formbeen= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);

		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		NhbMappingDAO dao=(NhbMappingDAO)DaoImplInstanceFactory.getDaoImplInstance(NhbMappingDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 
        String nhbCategoryId=vo.getNhbCategoryId();
		String[] arrNhbCategoryId = nhbCategoryId.split("\\|");
		 
		for(int i=0;i<arrNhbCategoryId.length;i++){
	    logger.info("value is:::::::::::::::::::::::::::"+arrNhbCategoryId[i]);
		 }
		
		boolean status = dao.insertNhbCategory(vo,arrNhbCategoryId);
		if(status){
			sms="S";
			request.setAttribute("sms",sms);
			request.setAttribute("save", "save");
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
			request.setAttribute("save", "save");
		}

		return mapping.findForward("success");
	}
	public ActionForward openEditNhbMapping(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		logger.info("In openEditNhbMapping() OF NhbMappingAction");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");		
		if(userobj==null)
		{
			logger.info("here in openEditNhbMapping method of  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
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
		
		String userId=null;
		String bDate=null;
		String sms=null;
		String nhbCategoryId=null;
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		NhbMappingVo vo=new NhbMappingVo();
		DynaValidatorForm formbeen= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);

		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		NhbMappingDAO dao=(NhbMappingDAO)DaoImplInstanceFactory.getDaoImplInstance(NhbMappingDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 
        
		 
		String dealId =CommonFunction.checkNull(request.getParameter("dealId"));
		logger.info("dealId"+dealId);
		if(!CommonFunction.checkNull(dealId).equalsIgnoreCase("")){
		ArrayList gridList = dao.getNhbMapping(dealId);
		if(gridList.size()>0){
			 vo=(NhbMappingVo)gridList.get(0);
			 nhbCategoryId=vo.getNhbCategoryId();
			 if(!CommonFunction.checkNull(nhbCategoryId).equalsIgnoreCase(""))
			 {
				 nhbCategoryId=nhbCategoryId.replace("|","','");
				 nhbCategoryId="'"+nhbCategoryId+"'";
				 ArrayList nhbDesc=dao.getNhbDescription(nhbCategoryId);
				 if(nhbDesc.size()>0)
					 request.setAttribute("nhbDesc", nhbDesc);
			 
			 }
			 
			 
			request.setAttribute("listOfData",gridList );
		}
		}
		
		request.setAttribute("editVal","editVal");
		return mapping.findForward("success");
	}
	
	public ActionForward saveInEditMode(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		logger.info("In saveNmbMapping() OF NhbMappingAction");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");		
		if(userobj==null)
		{
			logger.info("here in saveNmbMapping method of  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
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
		
		String userId=null;
		String bDate=null;
		String sms=null;
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		NhbMappingVo vo=new NhbMappingVo();
		DynaValidatorForm formbeen= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);

		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		NhbMappingDAO dao=(NhbMappingDAO)DaoImplInstanceFactory.getDaoImplInstance(NhbMappingDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 
        String nhbCategoryId=vo.getNhbCategoryId();
		String[] arrNhbCategoryId = nhbCategoryId.split("\\|");
		 
		for(int i=0;i<arrNhbCategoryId.length;i++){
	    logger.info("value is:::::::::::::::::::::::::::"+arrNhbCategoryId[i]);
		 }
		vo.setEditFlag("EDIT");
		boolean status = dao.insertNhbCategory(vo,arrNhbCategoryId);
		if(status){
			sms="M";
			request.setAttribute("sms",sms);
			request.setAttribute("save", "save");
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
			request.setAttribute("save", "save");
		}

		return mapping.findForward("success");
	}

}