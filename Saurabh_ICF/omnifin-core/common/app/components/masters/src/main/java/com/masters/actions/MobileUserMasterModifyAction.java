package com.masters.actions;

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

import com.business.ejbClient.CreditManagementBussinessSessionBeanRemote;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.MobileUserMappingVo;

public class MobileUserMasterModifyAction extends Action{
	private static final Logger logger = Logger.getLogger(MobileUserMasterModifyAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info("In MobileUserMasterModifyAction .... ");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
				Object bdate= session.getAttribute("businessdate");
				String strFlag="";	

				if(bdate!=null){
					bdate=bdate.toString();
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
				
				DynaValidatorForm MobileUserMasterDynaValidatorForm=(DynaValidatorForm)form;
				MobileUserMappingVo mobileUserMappingVo=new MobileUserMappingVo();
				org.apache.commons.beanutils.BeanUtils.copyProperties(mobileUserMappingVo, MobileUserMasterDynaValidatorForm);
				CreditManagementBussinessSessionBeanRemote cm = (CreditManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			
		        mobileUserMappingVo.setUserStatus(request.getParameter("userStatus"));
		        logger.info("In update mobileUser---status-----"+mobileUserMappingVo.getUserStatus());
				String userId = request.getParameter("userId");
				mobileUserMappingVo.setUserId(userId);
				
				String user_Id="";
				String bDate="";
				if(userobj!=null)
				{
						user_Id=userobj.getUserId();
						bDate=(userobj.getBusinessdate()).toString();
			
				}
		
				String msg="";
				String result = cm.saveModifyMobileMasterDao(mobileUserMappingVo,bDate);
				
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
				if(result.equalsIgnoreCase("EXIMIE"))
				{
					msg="EXIMIE";
					request.setAttribute("msg",msg);		
				}
				if(result.equalsIgnoreCase("EXSTAT"))
				{
					msg="EXSTAT";
					request.setAttribute("msg",msg);		
				}
				
				request.setAttribute("msg", msg);	
				request.setAttribute("dataNotSave", "dataNotSave");
			  return mapping.getInputForward();		
	}
}