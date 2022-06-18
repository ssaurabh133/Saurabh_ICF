package com.cm.actions;

import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.*;
import org.apache.struts.actions.*;
import org.apache.struts.validator.DynaValidatorForm;
import com.cm.dao.WaiveOffDAO;
import com.cm.vo.WaiveOffVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class WaiveOffMakerAction extends DispatchAction
{
	private static final Logger logger = Logger.getLogger(WaiveOffMakerAction.class.getName());
	public ActionForward waiveOffMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("in waiveOffMaker........... ");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("in  waiveOffMaker method of WaiveOffMakerAction  action the session is out----------------");
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
		
		return mapping.findForward("waiveOffMaker");
	}
	public ActionForward waiveOffMakerCSE(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
			{		
		    logger.info("in WaiveOffMakerCSE.....link...... ");	
		    
		    HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String userId = "";
			if(userobj!=null){
				userId= userobj.getUserId();
			}else{
				logger.info("in  WaiveOffMakerCSE method of WaiveOffMakerAction action the session is out----------------");
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
		  
			String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
			//String bp_id = CommonFunction.checkNull(request.getParameter("bpid"));
			String bpType = CommonFunction.checkNull(request.getParameter("bpType"));
			String waveOffId = CommonFunction.checkNull(request.getParameter("waveOffId"));
			
			logger.info("in WaiveOffMakerCSE.....link...... "+loanId);
			//logger.info("in WaiveOffMakerCSE.....link...... "+bp_id);
			
			//CreditManagementDAO service = new CreditManagementDAOImpl();
			WaiveOffDAO service=(WaiveOffDAO)DaoImplInstanceFactory.getDaoImplInstance(WaiveOffDAO.IDENTITY);
			logger.info("Implementation class: "+service.getClass());
			
			logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
			String functionId="";

		
			if(session.getAttribute("functionId")!=null)
			{
				functionId=session.getAttribute("functionId").toString();
			}
			
			//ServletContext context=getServlet().getServletContext();
			if(context!=null)
			{
			flag = LockRecordCheck.lockCheck(userId,functionId,loanId,context);
			logger.info("Flag ........................................ "+flag);
			if(!flag)
			{
				logger.info("Record is Locked");			
				request.setAttribute("searchWaiveOffNoData", "Locked");
				request.setAttribute("recordId", loanId);
				//request.setAttribute("userId", userId);
				return mapping.findForward("waiveOffMaker");
			}
			}
			ArrayList WaiveOffData = service.selectWaiveOffData(loanId,bpType,waveOffId);
		    
			logger.info("List Size is .................... "+WaiveOffData.size());
		    logger.info("List Size is .................... "+WaiveOffData.toString());
			
		    request.setAttribute("WaiveOffData", WaiveOffData);		    
		    return mapping.findForward("waiveOffMakerCSE");
	        }	
	
	public ActionForward openNewWaivOff(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {			
		logger.info("in openNewWaivOff........... ");
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("in  openNewWaivOff method of WaiveOffMakerAction  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("WaiveOffData");
		boolean flag1=false;
		
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
		
		String flag=(String)request.getParameter("Flag");
		logger.info("in openNewWaivOff........... "+flag);
		
		if(flag.equals(null))
		{
			session.setAttribute("flag", "0");
		}
		else
		{
			session.setAttribute("flag", flag);
		}
		
		return mapping.findForward("openNewWaivOff");
	}
	
	
	public ActionForward searchWaiveOff(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {			
		logger.info("in searchWaiveOff........... ");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null){
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("in  searchWaiveOff method of WaiveOffMakerAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		WaiveOffVO vo= new WaiveOffVO();
		
		boolean flag=false;
		
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
	logger.info("current page link .......... "+request.getParameter("d-49520-p"));
		
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		}
		
		logger.info("current page link ................ "+request.getParameter("d-49520-p"));
		
		vo.setCurrentPageLink(currentPageLink);
		
		DynaValidatorForm WaiveOffMakerDynaValidatorForm= (DynaValidatorForm)form;
		
		vo.setBranchId(branchId);
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, WaiveOffMakerDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		//CreditManagementDAO service=new CreditManagementDAOImpl();
		WaiveOffDAO service=(WaiveOffDAO)DaoImplInstanceFactory.getDaoImplInstance(WaiveOffDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		ArrayList searchWaiveOff=service.searchwaiveOff(vo);
		if(searchWaiveOff.size()==0)
		{
			request.setAttribute("list", searchWaiveOff);	
			request.setAttribute("searchWaiveOff", "searchWaiveOff");	
			logger.info("in searchWaiveOff........... "+searchWaiveOff);
		}
		else
		{
			request.setAttribute("list", searchWaiveOff);	
			request.setAttribute("searchWaiveOff", "searchWaiveOff");	
		logger.info("in searchWaiveOff........... "+searchWaiveOff);
		}
		return mapping.findForward("searchWaiveOff");
	}
	


	
}
