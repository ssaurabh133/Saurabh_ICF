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

import com.VO.CorporateBusinessActivityVO;
import com.connect.DaoImplInstanceFactory;
import com.gcd.dao.*;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class CorporateBusinessActivityBehindAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(CorporateBusinessActivityBehindAction.class.getName());
	
	public ActionForward displayBusinessActivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CorporateDAO pos=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
		logger.info("Implementation class: "+pos.getClass());
	    HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
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
		int cid =0;
		String statusCase="";
		String updateFlag="";
		String updateInMaker="";
		String pageStatuss="";
		String statusCE="";
		String showCE="";
		if(session.getAttribute("corporateId")!=null)
		{
			cid = Integer.parseInt(session.getAttribute("corporateId").toString());
		}
		if(session.getAttribute("statusCase")!=null)
		{
			statusCase = session.getAttribute("statusCase").toString();
		}
		if(session.getAttribute("updateFlag")!=null)
		{
			updateFlag = (String)session.getAttribute("updateFlag");
		}
		if(session.getAttribute("updateInMaker")!=null)
		{
			updateInMaker = session.getAttribute("updateInMaker").toString();
		}
		if(session.getAttribute("pageStatuss")!=null)
		{
			pageStatuss = session.getAttribute("pageStatuss").toString();
		}
		if(session.getAttribute("custEntryU")!=null)
		{
			statusCE = session.getAttribute("custEntryU").toString();
		}
		if(session.getAttribute("showCE")!=null)
		{
			showCE = session.getAttribute("showCE").toString();
			if(!(showCE.equalsIgnoreCase("Y")))
				session.removeAttribute("custEntryU");
		}
		if(session.getAttribute("showCE")==null || showCE.equalsIgnoreCase(""))
		{
			session.removeAttribute("custEntryU");
		}

		if(session.getAttribute("corporateId")!=null)
		{
			Object pageStatus=null;
			if(session.getAttribute("approve")!=null )
			{
				if(session.getAttribute("operation")!=null||session.getAttribute("approve")!=null)
				{
					pageStatus = session.getAttribute("approve");
				}
				String gcdReq = (String)session.getAttribute("gcdReq");
				if(gcdReq!=null)
				{
					pageStatus=null;
					updateFlag=null;
				}
				ArrayList<CorporateBusinessActivityVO> businessActivityDetails = pos.getBusinessActivity(cid,pageStatus,statusCase,updateFlag,updateInMaker,pageStatuss,"",source);
				session.setAttribute("businessActivityDetails", businessActivityDetails);
			}
			
			
			
			String gcdReq = (String)session.getAttribute("gcdReq");
			Object pageStatus1=pageStatus;
			String updateFlag1 =updateFlag;
			if(gcdReq!=null)
			{
				pageStatus=null;
				updateFlag=null;
			}
			String cuaStatus="";
			if(session.getAttribute("CUA")!=null)
		 	{
				cuaStatus = session.getAttribute("CUA").toString();
		 	}
			logger.info("Value of Flag cid: "+cid);
			logger.info("Value of Flag pageStatus: "+pageStatus);
			logger.info("Value of Flag statusCase: "+statusCase);
			logger.info("Value of Flag updateFlag: "+updateFlag);
			logger.info("Value of Flag updateInMaker: "+updateInMaker);
			logger.info("Value of Flag cuaStatus: "+cuaStatus);
			logger.info("Value of Flag statusCE: "+statusCE);
			logger.info("Value of Flag showCE: "+showCE);
			ArrayList<CorporateBusinessActivityVO> businessActivityDetails = pos.getBusinessActivity(cid,pageStatus,statusCase,updateFlag,updateInMaker,pageStatuss,cuaStatus,source);
			pageStatus=pageStatus1;
			updateFlag=updateFlag1;
			session.setAttribute("businessActivityDetails", businessActivityDetails);
			return mapping.findForward("corBusinessActivity");
		 }
		 else
		 {
			 request.setAttribute("back", "ok");
			 return mapping.findForward("backsuccess");
		 }
	}
}
