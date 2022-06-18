package com.cm.actions;

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

import com.cm.actionform.StationaryMasterForm;
import com.cm.business.StationaryIssuanceMasterBusiness;
import com.connect.CommonFunction;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class StationaryBranchgChangeDispatchAction extends DispatchAction
{
	private static final Logger logger = Logger.getLogger(StationaryBranchgChangeDispatchAction.class.getName());
	StationaryIssuanceMasterBusiness stationaryIssuanceMasterBusiness = new StationaryIssuanceMasterBusiness();
	
	
	public ActionForward openEditBranchChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
					StationaryMasterForm stationaryMasterForm = (StationaryMasterForm) form;
					ServletContext context = getServlet().getServletContext();
					HttpSession session = request.getSession(false);
				    UserObject userobj=(UserObject)session.getAttribute("userobject");
					Object sessionId = session.getAttribute("sessionID");
					String userId=null;
				if(userobj!=null)
				{		
					userId=userobj.getUserId();
				    }
					session.setAttribute("userId", userId);// For lov
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
					     session.setAttribute("userId", userId);
					     stationaryMasterForm.setBookIssue(request.getParameter("bookNo")); 
					     ArrayList bookTypeList = stationaryIssuanceMasterBusiness.getBookTypeGeneric();
					     request.setAttribute("bookTypeList", bookTypeList);
					     request.setAttribute("edit", stationaryIssuanceMasterBusiness.getstationarybranchgchangeEdit(stationaryMasterForm));	
			 			
			 		
	        		 if    (CommonFunction.checkNull(stationaryMasterForm.getAllBranch()).equalsIgnoreCase("Y"))
					        request.setAttribute("checkBranch","Y");	
			 		else
			 				request.setAttribute("checkBranch","N");
			 		
	        		 form.reset(mapping, request);
					     	return mapping.findForward("editMode");
	}  
	
	public ActionForward saveStationarybranchChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
					logger.info(" In saveStationarybranchChange-------");		
					StationaryMasterForm stationaryMasterForm = (StationaryMasterForm) form;
					ServletContext context = getServlet().getServletContext();
					HttpSession session = request.getSession(false);
					UserObject userobj=(UserObject)session.getAttribute("userobject");
			        Object sessionId = session.getAttribute("sessionID");
					String userId=null;
			      	String bDate=null;
					if(userobj!=null)
					{		
						userId=userobj.getUserId();
						bDate=userobj.getBusinessdate();
					}
			       	session.setAttribute("userId", userId);// For lov
		            String strFlag="";
				if  (sessionId!=null)
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
					stationaryMasterForm.setMakerId(userId);
					stationaryMasterForm.setMakerDate(bDate);
					boolean status=false;
					status= stationaryIssuanceMasterBusiness.saveStationarybranchChange(stationaryMasterForm);
				if(status)
				{
				   request.setAttribute("msg", "S");
				   }
				   else
				{
				   request.setAttribute("msg", "E");
				   }
			       form.reset(mapping, request);
		           return mapping.findForward("saved");
	} 
	
	public ActionForward searchBranchChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		logger.info("in:::: searchBranchChange");
		StationaryMasterForm stationaryMasterForm = (StationaryMasterForm) form;
		StationaryIssuanceMasterBusiness stationaryIssuanceMasterBusiness = new StationaryIssuanceMasterBusiness();
				
		request.setAttribute("list", stationaryIssuanceMasterBusiness.getStationaryBranchChangeSearch(stationaryMasterForm));
		form.reset(mapping, request);
		return mapping.findForward("right");
}
	
}        


