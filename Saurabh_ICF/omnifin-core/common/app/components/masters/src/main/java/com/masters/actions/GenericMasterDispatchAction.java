package com.masters.actions;

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

import com.business.ejbClient.CommonMasterBussinessSessionBeanRemote;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.GenericMasterVo;



public class GenericMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(GenericMasterDispatchAction.class.getName());	
	public ActionForward newSaveGenericMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info(" IN GenericMasterDispatchAction newSaveGenericMaster()....");
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
				
				//request.setAttribute("save", "save");
			    return mapping.findForward("newGenericMaster");	
			}
	
	
	public ActionForward saveGenericMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		ServletContext context = getServlet().getServletContext();
		HttpSession session=request.getSession(false);
		UserObject userobj=new UserObject();
		userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		
		logger.info("In GenericMasterDispatchAction saveGenericMaster().... ");
		
		boolean flag=false;
		
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
		
		DynaValidatorForm GenericMasterAddDynaValidatorForm=(DynaValidatorForm) form;
		GenericMasterVo genericMasterVo= new GenericMasterVo();
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(genericMasterVo, GenericMasterAddDynaValidatorForm);
		genericMasterVo.setMakerId(userId);
		genericMasterVo.setMakerDate(bDate);
		
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String msg="";
		 String result = bp.insertGenericMaster(genericMasterVo);
		
		if(result.equalsIgnoreCase("datasave")){
			msg="S";
			request.setAttribute("msg",msg);
		}else if(result.equalsIgnoreCase("dataexist")){
			msg="DE";
			request.setAttribute("msg",msg);
		}
		else if(result.equalsIgnoreCase("notsave")){
			msg="E";
			request.setAttribute("msg",msg);
		}

		return mapping.getInputForward();	
	}
	
	public ActionForward modifyDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info("In genericMasterDispatchAction modifyDetails()....");
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
				
				DynaValidatorForm GenericMasterAddDynaValidatorForm=(DynaValidatorForm)form;
				GenericMasterVo genericMasterVo= new GenericMasterVo();
				org.apache.commons.beanutils.BeanUtils.copyProperties(genericMasterVo, GenericMasterAddDynaValidatorForm);
				
				CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
		        String genericKey = request.getParameter("genericSearchKey");
				String value = request.getParameter("value");
				String parentValue = request.getParameter("parentValue");
				logger.info("genericKey======="+genericKey);
				logger.info("value========"+value);
				
				logger.info("parentValue======"+parentValue);
				
				genericMasterVo.setGenericSearchKey(genericKey);
				genericMasterVo.setLbxGenericId(genericKey);
				genericMasterVo.setParentValue(parentValue);
				genericMasterVo.setGenericval(value);
				
				
				ArrayList<GenericMasterVo> detailList = bp.modifyGenericMasterDetailsDao(genericMasterVo);
				genericMasterVo=detailList.get(0);

				request.setAttribute("list", detailList);	

				request.setAttribute("status", genericMasterVo.getStatus());

				
				request.setAttribute("modify", "modify");
			   return mapping.findForward("modifyDetails");	
			}
	
	public ActionForward saveModifyDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
	         	DynaValidatorForm GenericMasterAddDynaValidatorForm=(DynaValidatorForm) form;
				logger.info("In genericMasterDispatchAction saveModifyDetails().... ");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
				String userId="";
				String bDate="";
				if(userobj!=null)
				{
						userId=userobj.getUserId();
						bDate=userobj.getBusinessdate();
				}
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
				logger.info("In genericMasterDispatchAction saveModifyDetails()..parentValue.. "+GenericMasterAddDynaValidatorForm);
				logger.info("In genericMasterDispatchAction saveModifyDetails()..parentValue.. "+GenericMasterAddDynaValidatorForm.getString("parentValue"));
				GenericMasterVo genericMasterVo= new GenericMasterVo();
				org.apache.commons.beanutils.BeanUtils.copyProperties(genericMasterVo, GenericMasterAddDynaValidatorForm);
				String msg="";
				genericMasterVo.setMakerId(userId);
				genericMasterVo.setMakerDate(bDate);
				
				
				CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
				boolean result = bp.saveModifyGenericMasterDetailsDao(genericMasterVo);

				if(result){
					msg="M";
					request.setAttribute("msg",msg);
				}
				else{
					msg="E";
					request.setAttribute("msg",msg);
				}

				logger.info("In genericMasterDispatchAction saveModifyDetails msg is.... "+msg);
				
							
				 return mapping.getInputForward();	
			}
	
	
}
