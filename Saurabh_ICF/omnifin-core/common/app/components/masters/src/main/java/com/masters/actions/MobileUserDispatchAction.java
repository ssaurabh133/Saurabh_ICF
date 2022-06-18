package com.masters.actions;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.business.ejbClient.CreditManagementBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.MobileUserMappingVo;

public class MobileUserDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(MobileUserDispatchAction.class.getName());	
	
	public ActionForward addmobileUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info(" IN MobileUserDispatchAction addmobileUser()....");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
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
			
				request.setAttribute("Add", "Add");
			    return mapping.findForward("addMobileUser");	
			}

	public ActionForward getMobileNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info(" IN MobileUserDispatchAction getMobileNo()....");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
				String strFlag="";	
				String mobileNo="";
				String userId="";
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
			
				  if (request.getParameter("id")!="" && request.getParameter("id")!=null)
				
				  {
					  userId=request.getParameter("id");
				  }
				  MobileUserMappingVo mobileUserMappingVo=new MobileUserMappingVo();
				  mobileUserMappingVo.setUserId(userId);
				  CreditManagementBussinessSessionBeanRemote cm = (CreditManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
					
					String msg="";
					logger.info("In MobileUserDispatchAction  ");
					mobileNo = cm.getMobileNoMasterDao(mobileUserMappingVo);
				    request.setAttribute("mobileNo", mobileNo);
			        return mapping.findForward("getMobileNo");	
			}


	public ActionForward savemobileUser(ActionMapping mapping, ActionForm form,
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
				bDate=(userobj.getBusinessdate()).toString();
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
		
		DynaValidatorForm MobileUserMappingDynaValidatorForm=(DynaValidatorForm)form;
		MobileUserMappingVo mobileUserMappingVo=new MobileUserMappingVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(mobileUserMappingVo,MobileUserMappingDynaValidatorForm);
		CreditManagementBussinessSessionBeanRemote cm = (CreditManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String msg="";
		logger.info("In MobileUserDispatchAction  ");
		String groupId = cm.insertMobileUserMaster(mobileUserMappingVo,bDate);
		
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
		if(groupId.equalsIgnoreCase("EXIMIE"))
		{
			msg="EXISTSIMIE";
			request.setAttribute("msg",msg);				
		}		
		request.setAttribute("msg", msg);	
		request.setAttribute("msg1", "msg1");
		return mapping.getInputForward();
	}

	
	
	public ActionForward searchmobileUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		ServletContext context = getServlet().getServletContext();
		logger.info("IN MobileUserDispatchAction  searchmobileUser() .........");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
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
		
		MobileUserMappingVo vo=new MobileUserMappingVo();
	    DynaValidatorForm CommonDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CommonDynaValidatorForm);
		
		CreditManagementBussinessSessionBeanRemote cm = (CreditManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		ArrayList list=new ArrayList();
	
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
	    list= cm.searchMobileUserData(vo);

	    logger.info("In IN MobileUserDispatchAction...list"+list.size());
		
		logger.info("list.isEmpty(): "+list.isEmpty());
		request.setAttribute("list",list);
		if(CommonFunction.checkNull(request.getAttribute("flag")).toString().equalsIgnoreCase("yes")){
			request.setAttribute("sms","No");
		}
		request.setAttribute("msg1", "msg1");
		return mapping.findForward("success");
	}
	
	
	
	public ActionForward modifyMobileUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("In modify mobile usr details");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
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
            DynaValidatorForm MobileUseMasterDynaValidatorForm=(DynaValidatorForm)form;	
            MobileUserMappingVo mobileUserMappingVo=new MobileUserMappingVo();
            org.apache.commons.beanutils.BeanUtils.copyProperties(mobileUserMappingVo,MobileUseMasterDynaValidatorForm);
            CreditManagementBussinessSessionBeanRemote cm = (CreditManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);		

        String userId = request.getParameter("userId");
		logger.info("userId:----------"+userId);
		mobileUserMappingVo.setUserId(userId);
		ArrayList< MobileUserMappingVo> detailList = cm.modifyMobileUSerDetailsDao(mobileUserMappingVo);
		mobileUserMappingVo  = detailList.get(0); 
		logger.info("recStatus...... Set value for status" + mobileUserMappingVo.getUserStatus()); 
		request.setAttribute("list", detailList);	
		request.setAttribute("editVal", "editVal");
		request.setAttribute("dataNotSave", "dataNotSave");
		
		request.setAttribute("status",mobileUserMappingVo.getUserStatus());
		
	   return mapping.findForward("modifyDetails");	
	
	}
}