/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.login.action;

/**
 * 
 * @author sumanta.laha
 */
import java.util.List;

import java.util.Properties;
import javax.naming.InitialContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserPermissionVo;
import com.login.dao.UserProfileDao;


import com.login.roleManager.UserObject;


public class LoadMenuAction extends Action {

	private static final Logger logger = Logger.getLogger(LoadMenuAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//code added by neeraj
		Properties props = new Properties();
		props.load(request.getSession().getServletContext().getResourceAsStream("/WEB-INF/jndi.properties"));   
        /*String applName=props.getProperty("enterprise.application.name");*/
        String applId=props.getProperty("omnifin.installation.id");
        String menuValue="'0'";
        if(CommonFunction.checkNull(applId).trim().equalsIgnoreCase("1"))
        	menuValue=menuValue+",'1'";
        else
        	menuValue=menuValue+",'2'";
		// neeraj space end
		HttpSession session=request.getSession(false);
		String userIDForMenu=null;		
			
		UserObject sessUser=(UserObject) session.getAttribute("userobject");
		logger.info("enter...LoadMenuAction....");	
		if(sessUser!=null){
			
			UserProfileDao profManager=(UserProfileDao)DaoImplInstanceFactory.getDaoImplInstance(UserProfileDao.IDENTITY);
			logger.info("Implementation class: "+profManager.getClass());
			
			
			userIDForMenu=sessUser.getUserId();				
		
			
			try{
				
				
			List<UserPermissionVo> menuItemList =profManager.modulelist(userIDForMenu,menuValue);
	if(menuItemList.size()>0){
			session.setAttribute("headermenulist",menuItemList);
			
	}
			
			}catch(Exception e){
				e.printStackTrace();
			} 
			
		}else{
			
		}
		logger.info("LoadMenuAction:----flag----"+request.getAttribute("flag"));		
		//request.setAttribute("flag", request.getAttribute("flag"));
		return mapping.findForward("success");
	}
	
}