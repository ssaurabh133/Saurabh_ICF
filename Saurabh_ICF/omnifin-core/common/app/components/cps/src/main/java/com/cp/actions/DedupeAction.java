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
import org.apache.struts.validator.DynaValidatorForm;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cm.vo.DedupeVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.DedupeDAO;

public class DedupeAction extends DispatchAction
{
	private static final Logger logger = Logger.getLogger(DedupeAction.class.getName());

	public ActionForward behindMethod(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{	
		logger.info("In  behindMethod"); 
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info(" in behindMethod the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
		return mapping.findForward("success");
	}
	public ActionForward searchDuplicateRecord(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{	
		logger.info("In  searchDuplicateRecord"); 
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userID="";
		String businessDate="";
		if(userobj==null)
		{
			logger.info(" in searchDuplicateRecord the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
		{
			userID=userobj.getUserId();
			businessDate=userobj.getBusinessdate();
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
		DedupeVO vo=new DedupeVO();
		DynaValidatorForm formbeen= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);
		vo.setUserID(userID);
		vo.setBusinessDate(businessDate);
		ArrayList list=new ArrayList();
		list.add(vo);
		request.setAttribute("resultList",list);
		logger.info("current page link .......... "+request.getParameter("d-1334207-p"));		
		int currentPageLink = 0;
		if(request.getParameter("d-1334207-p")==null || request.getParameter("d-1334207-p").equalsIgnoreCase("0"))
			currentPageLink=1;
		else
			currentPageLink =Integer.parseInt(request.getParameter("d-1334207-p"));
		logger.info("current page link ................ "+request.getParameter("d-1334207-p"));
		vo.setCurrentPageLink(currentPageLink);
		DedupeDAO dao=(DedupeDAO)DaoImplInstanceFactory.getDaoImplInstance(DedupeDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 
		//DedupeDAO dao=new DedupeDAOImpl();
		ArrayList dupList=dao.getDupRecord(vo);
		String msg=(String)dupList.get(0);
		if(CommonFunction.checkNull(msg).trim().length()!=0)
			request.setAttribute("error",msg);
		else
		{
			ArrayList dList=(ArrayList)dupList.get(1);
			request.setAttribute("dupList",dList);			
		}
		String customerID=CommonFunction.checkNull(vo.getCustomerID());
		if(!CommonFunction.checkNull(vo.getCustomerID()).trim().equalsIgnoreCase(""))
			request.setAttribute("show","show");
		
		return mapping.findForward("success");
	}
	public ActionForward getDefltCustDtl(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{	
		logger.info("In  getDefltCustDtl"); 
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userID="";
		String businessDate="";
		if(userobj==null)
		{
			logger.info(" in getDefltCustDtl the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
		{
			userID=userobj.getUserId();
			businessDate=userobj.getBusinessdate();
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
		String customerID=request.getParameter("customerID");
		logger.info("customerID   :  "+customerID);
		DedupeDAO dao=(DedupeDAO)DaoImplInstanceFactory.getDaoImplInstance(DedupeDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 
		//DedupeDAO dao=new DedupeDAOImpl();
		ArrayList list=dao.getDefltCustDtl(customerID);
		request.setAttribute("list",list);
		return mapping.findForward("ajax");
	}
	public ActionForward replaceCustomerID(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{	
		logger.info("In  replaceCustomerID"); 
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
		{
			logger.info(" in replaceCustomerID the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
		DedupeVO vo=new DedupeVO();
		DynaValidatorForm formbeen= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);
		String customerID=request.getParameter("customerID");
		logger.info("customerID   :  "+customerID);
		DedupeDAO dao=(DedupeDAO)DaoImplInstanceFactory.getDaoImplInstance(DedupeDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 
		//DedupeDAO dao=new DedupeDAOImpl();
		String tarCustID=request.getParameter("tarCustID");
		vo.setDupCustomerID(tarCustID);
		boolean status=dao.replaceCustomerID(vo);
		if(status)
			request.setAttribute("done","done");
		else
			request.setAttribute("notDone","notDone");
		return mapping.findForward("success");
	}
	
	
}
