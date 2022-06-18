/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cm.actions;

import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/** 
 * MyEclipse Struts
 * Creation date: 05-18-2011
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class SelectAssetCollateralAction extends Action {
	private static final Logger logger = Logger.getLogger(SelectAssetCollateralAction.class.getName());
	/*
	 * Generated Methods
	 */

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in execute method of SelectAssetCollateralAction action the session is out----------------");
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
		
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass());  
		//CreditProcessingDAO service = new CreditProcessingDAOImpl();
		  
	       String dealId = "";
			
			if(session.getAttribute("dealId")!=null)
			{
				
				dealId=session.getAttribute("dealId").toString();
			}
			else if(session.getAttribute("maxId")!=null)
			{
				dealId=session.getAttribute("maxId").toString();
			}
			logger.info("In SelectAssetCollateralAction ex id " +dealId);
	        ArrayList<Object> showCollateralDetails = service.getCollateralDetailsAll(dealId);	
	        session.setAttribute("showCollateralDetails", showCollateralDetails);	
	        
		   return mapping.findForward("success");
	}
}