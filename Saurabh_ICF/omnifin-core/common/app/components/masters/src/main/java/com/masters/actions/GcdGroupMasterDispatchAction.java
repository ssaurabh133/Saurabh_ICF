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

import com.business.ejbClient.CreditManagementBussinessSessionBeanRemote;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.GcdGroupMasterVo;

public class GcdGroupMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(GcdGroupMasterDispatchAction.class.getName());	
	
	public ActionForward addGroupMasterDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info(" IN GcdGroupMasterSearchProcessingAction addGroupMasterDetails()....");
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
				
				request.setAttribute("save", "save");
				request.setAttribute("Add", "Add");
			    return mapping.findForward("addGroupMasterDetails");	
			}
	
	
	public ActionForward saveGcdGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		ServletContext context = getServlet().getServletContext();
		HttpSession session=request.getSession(false);
		UserObject userobj=new UserObject();
		userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String bDate="";
		String groupName ="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		
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
		
		DynaValidatorForm GcdGroupMasterDynaValidatorForm=(DynaValidatorForm)form;
		GcdGroupMasterVo gcdGroupMasterVo= new GcdGroupMasterVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(gcdGroupMasterVo, GcdGroupMasterDynaValidatorForm);
		
		CreditManagementBussinessSessionBeanRemote cm = (CreditManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		gcdGroupMasterVo.setMakerId(userId);
		gcdGroupMasterVo .setMakerDate(bDate);
		
		String msg="";
		logger.info("In saveGcdGroupDispatchAction gcdGroupMasterVo msg is.... "+gcdGroupMasterVo.getGroupDescription());
		String groupId = cm.insertGroupCodeMaster(gcdGroupMasterVo);
		
		if(groupId.equalsIgnoreCase("S"))
		{
			msg="S";			
			request.setAttribute("msg",msg);			
		}
		if(groupId.equalsIgnoreCase("E"))
		{
			msg="E";
			request.setAttribute("msg",msg);				
		}
		if(groupId.equalsIgnoreCase("EX"))
		{
			msg="EXIST";
			request.setAttribute("msg",msg);				
		}
				
		request.setAttribute("msg", msg);	
		return mapping.getInputForward();
	}
	
	public ActionForward modifyDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info("In modify details");
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
		DynaValidatorForm GcdGroupMasterDynaValidatorForm=(DynaValidatorForm)form;
		GcdGroupMasterVo gcdGroupMasterVo= new GcdGroupMasterVo();	
		org.apache.commons.beanutils.BeanUtils.copyProperties(gcdGroupMasterVo, GcdGroupMasterDynaValidatorForm);		
				
		CreditManagementBussinessSessionBeanRemote cm = (CreditManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);		
		
		        String gcdgroupId = request.getParameter("gcdgroupId");
				logger.info("groupId:----------"+gcdgroupId);
				gcdGroupMasterVo.setGcdgroupId(gcdgroupId);
				
				ArrayList<GcdGroupMasterVo> detailList = cm.modifyGcdGroupDetailsDao(gcdGroupMasterVo);
				gcdGroupMasterVo  = detailList.get(0); //Added by Nishant Rai
				logger.info("recStatus...... Set value for status" + gcdGroupMasterVo.getRecStatus()); //Added by Nishant Rai
				request.setAttribute("list", detailList);	
				request.setAttribute("editVal", "editVal");
				request.setAttribute("status",gcdGroupMasterVo.getRecStatus());
				
			   return mapping.findForward("modifyDetails");	
			}
	
	public ActionForward saveModifyDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info("In GcdGroupDispatchAction saveModifyDetails().... ");
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
				
				DynaValidatorForm GcdGroupMasterDynaValidatorForm=(DynaValidatorForm)form;
				GcdGroupMasterVo gcdGroupMasterVo= new GcdGroupMasterVo();	
				org.apache.commons.beanutils.BeanUtils.copyProperties(gcdGroupMasterVo, GcdGroupMasterDynaValidatorForm);
				
				CreditManagementBussinessSessionBeanRemote cm = (CreditManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
		        gcdGroupMasterVo.setRecStatus(request.getParameter("recStatus"));
				logger.info("In updateGCdDetails---status-----"+gcdGroupMasterVo.getRecStatus());
				String gcdgroupId = request.getParameter("gcdgroupId");
				gcdGroupMasterVo.setGcdgroupId(gcdgroupId);
				String userId="";
				String bDate="";
				if(userobj!=null)
				{
						userId=userobj.getUserId();
						bDate=userobj.getBusinessdate();
				}
				gcdGroupMasterVo.setMakerId(userId);
				gcdGroupMasterVo.setMakerDate(bDate);
				String msg="";
				String result = cm.saveModifyGcdGroupDao(gcdGroupMasterVo);
				
				if(result.equalsIgnoreCase("S"))
				{
					msg="M";			
					request.setAttribute("msg",msg);			
				}
				if(result.equalsIgnoreCase("E"))
				{
					msg="E";
					request.setAttribute("msg",msg);				
				}
				if(result.equalsIgnoreCase("EX"))
				{
					msg="EXIST";
					request.setAttribute("msg",msg);				
				}
				
//				if(result){
//					msg="M";
//					request.setAttribute("msg",msg);
//				}else{
//					msg="E";
//					request.setAttribute("msg",msg);
//				}
				request.setAttribute("msg", msg);	
			//	request.setAttribute("modify", "modify");
			   return mapping.getInputForward();	
			}
	
	
}
