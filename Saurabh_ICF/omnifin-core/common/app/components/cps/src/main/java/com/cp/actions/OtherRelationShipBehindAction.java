/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cp.actions;

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
import com.cp.dao.CreditProcessingDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/** 
 * MyEclipse Struts
 * Creation date: 03-26-2013
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class OtherRelationShipBehindAction extends DispatchAction {
	/*
	 * Generated Methods
	 */
	private static final Logger logger = Logger.getLogger(OtherRelationShipBehindAction.class.getName());
	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward openOtherTab(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		// TODO Auto-generated method stub
		
		
       logger.info("In openOtherTab Detail  ");
		
		HttpSession session = request.getSession();

		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info(" in  openOtherTab method of OtherRelationShipBehindAction action the session is out----------------");
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
		String dealId = "";

		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}
		logger.info("In OtherRelationShipBehindAction openOtherTab id "+ dealId);
		
		  CreditProcessingDAO detail=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		  logger.info("Implementation class: "+detail.getClass());
		  ArrayList<Object> list=new ArrayList<Object>(); 
		  list=detail.getOtherRelationList();
		  request.setAttribute("relationType",list);
		  ArrayList otherList = detail.getOtherGridList(dealId);
		  request.setAttribute("otherList", otherList);
		  return mapping.findForward("success");
	}
	public ActionForward fetchOtherRelation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		
        logger.info("In fetchOtherRelation Detail  ");
		
		HttpSession session = request.getSession();

		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		if(userobj==null)
	    {
			logger.info(" in  fetchOtherRelation method of OtherRelationShipBehindAction action the session is out----------------");
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
		String dealId = "";

		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}
		logger.info("In OtherRelationShipBehindAction fetchOtherRelation id "+ dealId);
		
		  CreditProcessingDAO detail=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		 logger.info("Implementation class: "+detail.getClass());
		 String otherUniqueId=request.getParameter("otherUniqueId");
		 logger.info("otherUniqueId: "+otherUniqueId);
		 ArrayList otherDetailList = detail.getOtherDetail(otherUniqueId);
		 request.setAttribute("otherDetailList", otherDetailList);
		  ArrayList<Object> list=new ArrayList<Object>(); 
		  list=detail.getOtherRelationList();
		  request.setAttribute("relationType",list);
		  ArrayList otherList = detail.getOtherGridList(dealId);
		  request.setAttribute("otherList", otherList);
         request.setAttribute("update", "update");
         return  mapping.findForward("success");
	}
	public ActionForward deleteOtherRelation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		
        logger.info("In deleteOtherRelation Detail  ");
		
		HttpSession session = request.getSession();

		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		if(userobj==null)
	    {
			logger.info(" in  fetchOtherRelation method of OtherRelationShipBehindAction action the session is out----------------");
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
		String dealId = "";

		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}
		logger.info("In OtherRelationShipBehindAction deleteOtherRelation id "+ dealId);
		
		  CreditProcessingDAO detail=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		 logger.info("Implementation class: "+detail.getClass());
		 String []otherUniqueId=null;
		 if(request.getParameterValues("chk")!=null)
		 {
			 otherUniqueId = request.getParameterValues("chk"); 
		 }
		logger.info("otherUniqueId: "+otherUniqueId.toString());
		 boolean status = detail.deleteOtherDetail(otherUniqueId);
		 if(status)
	        {
	        	request.setAttribute("deleteMsg", "S");
	        }else
	        {
	        	request.setAttribute("deleteMsg", "E");
	        }
		  ArrayList<Object> list=new ArrayList<Object>(); 
		  list=detail.getOtherRelationList();
		  request.setAttribute("relationType",list);
		  ArrayList otherList = detail.getOtherGridList(dealId);
		  request.setAttribute("otherList", otherList);
          return  mapping.findForward("success");
	}
}