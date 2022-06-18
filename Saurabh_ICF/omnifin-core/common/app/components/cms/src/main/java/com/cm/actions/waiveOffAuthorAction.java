package com.cm.actions;

import java.util.ArrayList;
import javax.servlet.*;
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


public class waiveOffAuthorAction extends DispatchAction
{
	private static final Logger logger = Logger.getLogger(waiveOffAuthorAction.class.getName());
	public ActionForward waiveOffAuthorFrame(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("in waiveOffAuthorFrame........... ");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{
			logger.info("in  waiveOffAuthorFrame  method of  waiveOffAuthorAction action the session is out----------------");
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
		String bp_id = CommonFunction.checkNull(request.getParameter("bpid"));
		String waveOffId = CommonFunction.checkNull(request.getParameter("waveOffId"));
		session.setAttribute("waveOffId", waveOffId);
		
		logger.info("in waiveOffAuthorCSE.....link...... "+loanId);
		logger.info("in waiveOffAuthorCSE.....link...... "+bp_id);
		
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		WaiveOffDAO service=(WaiveOffDAO)DaoImplInstanceFactory.getDaoImplInstance(WaiveOffDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
	
		request.setAttribute("waveOffId", waveOffId);
		
		//logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
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
			request.setAttribute("searchWaiveOffAuthorNoData", "Locked");
			request.setAttribute("recordId", loanId);
			//request.setAttribute("userId", userId);
			return mapping.findForward("searchWaiveOffAuthor");
		}
		}
		ArrayList AuthorData = service.selectWaiveOffAuthorData(loanId,bp_id,waveOffId);
		
		session.setAttribute("AuthorData", AuthorData);		    
		return mapping.findForward("waiveOffAuthorFrame");
	}

	
	public ActionForward searchWaiveOffAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {	
		
		logger.info("in searchWaiveOffAuthor........... ");
		
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
			logger.info("in  searchWaiveOffAuthor method of waiveOffAuthorAction  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");

		WaiveOffVO vo= new WaiveOffVO();
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

		session.removeAttribute("searchWaiveOffAuthorNoData");
		session.removeAttribute("searchWaiveOffAuthor");
		
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
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, WaiveOffMakerDynaValidatorForm);

		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
				{ 
					vo.setReportingToUserId(userId);
				   //logger.info("When user id is not selected by the user:::::"+userId);
				}
				logger.info("user Id:::::"+vo.getReportingToUserId());
				vo.setBranchId(branchId);
				vo.setUserId(userId);
				
		//CreditManagementDAO service=new CreditManagementDAOImpl();
				WaiveOffDAO service=(WaiveOffDAO)DaoImplInstanceFactory.getDaoImplInstance(WaiveOffDAO.IDENTITY);
				logger.info("Implementation class: "+service.getClass());
		ArrayList searchWaiveOffAuthor=service.searchWaiveOffAuthor(vo,request);
		
			logger.info("in searchWaiveOffAuthor........... "+searchWaiveOffAuthor);
			request.setAttribute("searchList", searchWaiveOffAuthor);
		
		return mapping.findForward("searchWaiveOffAuthor");
	}
	
	
}
