package com.gcd.actions;

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

import com.connect.DaoImplInstanceFactory;
import com.gcd.dao.*;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class CorporateBusinessDescirptionBehindAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(CorporateBusinessDescirptionBehindAction.class.getName());
	
	public ActionForward displayBusinessDescirption(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		 HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null)
			{			
				logger.info("here displayBusinessDescirption method of CorporateAddressAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
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
			//code added by neeraj
			String source="NE";
			String functionId=(String)session.getAttribute("functionId");
			int funid=Integer.parseInt(functionId);		
			if(funid==4000122 || funid==4000123)
				source="ED";
			//neeraj space end
		 CorporateDAO detail=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
		 logger.info("Implementation class: "+detail.getClass());
		 String statusCase="";
		 String updateFlag="";
		 String updateInMaker="";
		 logger.info("In displayBusinessDescirption"+session.getAttribute("corporateId"));
		 
		 if((session!=null && session.getAttribute("corporateId")!=null))
		 {
			 String code = session.getAttribute("corporateId").toString();
			
		 if(session.getAttribute("statusCase")!=null)
		 {
				statusCase = session.getAttribute("statusCase").toString();
		 }
		 if(session.getAttribute("updateFlag")!=null)
		 {
			 
			 updateFlag = session.getAttribute("updateFlag").toString();
		 }
		 if(session.getAttribute("updateInMaker")!=null)
			{
				updateInMaker = session.getAttribute("updateInMaker").toString();
			}
		 String pageStatuss="";
		 if(session.getAttribute("pageStatuss")!=null)
		 {
			 pageStatuss = session.getAttribute("pageStatuss").toString();
		 }
		 String gcdReq = (String)session.getAttribute("gcdReq");
		  String pageStatuss1=pageStatuss;
		  String updateFlag1 =updateFlag;
		  if(gcdReq!=null)
		  {
			  pageStatuss=null;
			  updateFlag=null;
		  }
		  String cuaStatus="";
		  if(session.getAttribute("CUA")!=null)
			 {
			  cuaStatus = session.getAttribute("CUA").toString();
			 }
		 ArrayList<Object> businessDesc = detail.getBusinessDescription(code,statusCase,updateInMaker,pageStatuss,updateFlag,cuaStatus,source);
		 pageStatuss=pageStatuss1;
		  updateFlag=updateFlag1;
		 logger.info("Size of getBusinessDescription............................"+businessDesc.size());
		 session.setAttribute("businessDesc", businessDesc);
		 return mapping.findForward("corBusinessDescirption");
		 }
		 else
		 {
			 request.setAttribute("back", "ok");
			 return mapping.findForward("backsuccess");
		 }
	}
}
