package com.masters.actions;

import java.util.Properties;

import javax.naming.InitialContext;
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

import com.business.Security.MasterBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.ChangePasswordMasterVo;

public class ChangePasswordDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ChangePasswordDispatchAction.class.getName());
	
	public ActionForward updateChangePassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In updateChangePassword.......");
		HttpSession session=request.getSession(false);
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
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		logger.info("---------userId---------"+userId); 
		request.setAttribute("userId", userId);
		
		ChangePasswordMasterVo changePasswordMasterVo=new ChangePasswordMasterVo(); 
		
		DynaValidatorForm ChangePasswordMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(changePasswordMasterVo, ChangePasswordMasterAddDynaValidatorForm);	
		
       
        MasterBussinessSessionBeanRemote mb = (MasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(MasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		changePasswordMasterVo.setMakerDate(bDate);
		changePasswordMasterVo.setLbxUserId(userId);
	
		logger.info("In updateChangePassword---userId---- by getpara-"+changePasswordMasterVo.getLbxUserId());  
		
        logger.info("in countChangePassword method.....");
        int count=mb.countChangePassword(changePasswordMasterVo);
     
        String msg="";
        boolean status=false;
        if(count==0){
        	msg="M";
        }
        else if(count>=1){
        	status= mb.updateChangePassword(changePasswordMasterVo);
        if(status)
        	{msg="S";
        }
       else{
			msg="E";
			
		}
        }
        request.setAttribute("msg",msg);
        return mapping.findForward("searchPage");
       
	}
	public ActionForward updateChangePasswordforLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		MasterBussinessSessionBeanRemote mb = (MasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(MasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		        
        ServletContext context = getServlet().getServletContext();
		logger.info("In updateChangePasswordforLogin.......");
		HttpSession session =request.getSession();
		boolean flag1=false;
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
		String flag = request.getParameter("flag");
		logger.info("In flag......."+flag);
		
		String name = request.getParameter("name");
		logger.info("In name......."+name);
		

	

			 String userId= mb.getlogintimeid(name);
				logger.info("out of session userId......."+userId);

		
		ChangePasswordMasterVo changePasswordMasterVo=new ChangePasswordMasterVo(); 
		
		DynaValidatorForm ChangePasswordMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(changePasswordMasterVo, ChangePasswordMasterAddDynaValidatorForm);	
		
//		changePasswordMasterVo.setMakerDate(userobj.getBusinessdate());
		changePasswordMasterVo.setLbxUserId(userId);
		//logger.info("In updateChangePassword---userId---- by getpara-"+changePasswordMasterVo.getLbxUserId());  

		
        logger.info("in countChangePassword method.....");
       
        int count=mb.countChangePassword(changePasswordMasterVo);
        logger.info("in countChangePassword  "+count);
        int ques=mb.countQuestion(changePasswordMasterVo);
        
        logger.info("in countQuestion-----------"+ques);
        String logmsg="";
        boolean status=false;

        if(count==0){
        	logmsg="LM";
        }
        
        else if((count>=1)&(ques==0)){
        	String passworddate= mb.getbeforemakerdate();
        	logger.info("------date------"+passworddate);
        	changePasswordMasterVo.setPasswordDate(passworddate);
        	logger.info("out of session userId......."+changePasswordMasterVo.getLbxUserId());

        	status= mb.updateloginChangePassword(changePasswordMasterVo);
			
        	if(status)
			{
				 logmsg="LS";
			}else{
				logmsg="LE";
			}
        		
		}
        logger.info("in logmsg-----------"+logmsg);
    	request.setAttribute("logmsg",logmsg);
    	request.setAttribute("flagLogin", "CL");
    	request.setAttribute("name", name);
    	
		return mapping.findForward("searchPageLogiN");	
		
	}
}
