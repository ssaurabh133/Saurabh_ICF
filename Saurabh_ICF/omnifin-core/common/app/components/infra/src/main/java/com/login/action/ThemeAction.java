/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.login.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/** 
 * MyEclipse Struts
 * Creation date: 08-31-2011
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class ThemeAction extends Action {
	/*
	 * Generated Methods
	 */
	private static final Logger logger = Logger.getLogger(ThemeAction.class.getName());
	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String themeVal=request.getParameter("val");
		HttpSession session = request.getSession();
		logger.info("themeVal: "+themeVal);
		if(themeVal.equalsIgnoreCase("Green"))
 		{
 		
 			session.setAttribute("css","css/theme3");
 			session.setAttribute("tab","ddtabmenufiles/theme3");
 			session.setAttribute("image","images/theme3");
 		
 		}
 		else if(themeVal.equalsIgnoreCase("Blue"))
 		{
 			
 			session.setAttribute("css","css/theme1");
 			session.setAttribute("tab","ddtabmenufiles/theme1");
 			session.setAttribute("image","images/theme1");
 		
 		}
 		else if(themeVal.equalsIgnoreCase("Brown"))
 		{
 		
 			session.setAttribute("css","css/theme2");
 			session.setAttribute("tab","ddtabmenufiles/theme2");
 			session.setAttribute("image","images/theme2");
 		
 		}
		return mapping.findForward("success");
	}
}