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
import com.cm.dao.CancellationDAO;
import com.cm.dao.EarlyClosureDAO;
import com.cm.vo.CancellationVO;
import com.cm.vo.ClosureVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class AuthorBehindAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(AuthorBehindAction.class.getName());
	public ActionForward openClosureAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		
		logger.info("In openClosureAuthor............");
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String businessDate="";
		if(userobj != null)
		{
			businessDate=userobj.getBusinessdate();
		}
		else
		{
			logger.info("here in openClosureAuthor method of AuthorBehindAction action the session is out----------------");
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
		String loanId = session.getAttribute("loanId").toString();
		EarlyClosureDAO service=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		String makerDate=service.getMakerDate(loanId);
		String checkFlag=service.earlyClosureFlag();
		request.setAttribute("checkFlag",checkFlag);
		request.setAttribute("businessDate",businessDate);
		request.setAttribute("makerDate",makerDate);		
		String closureStatus = session.getAttribute("closureStatus").toString();
		logger.info("Inside AuthorBehindAction.........loan id: "+loanId);
		logger.info("Inside AuthorBehindAction.........closureStatus: "+closureStatus);		
		return mapping.findForward("openClosureAuthor");
	}
	
	public ActionForward getClosureDataFrameset(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("In getClosureDataFrameset............");
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in getClosureDataFrameset method of AuthorBehindAction action the session is out----------------");
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
		String loanId =CommonFunction.checkNull((String)request.getParameter("loanId"));
		String fwd="";
		if(loanId.equalsIgnoreCase("null"))
			{
				logger.info("Opening JSP when loanId is null");
				session.removeAttribute("loanId");
				session.removeAttribute("closureData");
				session.removeAttribute("closureDataDisabled");
				session.removeAttribute("type");
				fwd="openNewClosure";
			}
		else
		{
			session.removeAttribute("closureDataDisabled");
			session.setAttribute("loanId", loanId);
			logger.info("Opening JSP when loanId is:"+loanId);
			//CreditManagementDAO service = new CreditManagementDAOImpl();
			EarlyClosureDAO service=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
			logger.info("Implementation class: "+service.getClass());
			ArrayList<ClosureVO> closureData = service.selectClosureData(loanId,session.getAttribute("terminationId").toString());
			session.setAttribute("closureData", closureData);
			fwd="openClosureWithData";
		}
		return mapping.findForward(fwd);
	}
	
	public ActionForward getClosureDataFramesetDisabled(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("In getClosureDataFramesetDisabled............");
		HttpSession session =  request.getSession();
		boolean flag=false;
		//System.out.println("\n\nram\n\n");
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in getClosureDataFramesetDisabled method of AuthorBehindAction action the session is out----------------");
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
		
		logger.info("Opening JSP when loanId is:"+session.getAttribute("loanId"));
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		EarlyClosureDAO service=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		ArrayList<ClosureVO> closureDataDisabled = service.selectClosureData(session.getAttribute("loanId").toString(),session.getAttribute("terminationId").toString());
		session.setAttribute("closureDataDisabled", closureDataDisabled);
		
		return mapping.findForward("openClosureDisabledWithData");
	}
	
	public ActionForward openCancellationAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("Inside Author Behind Action..........openCancellationAuthor");
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in openCancellationAuthor method of AuthorBehindAction action the session is out----------------");
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

		String loanId = session.getAttribute("loanId").toString();
		String closureStatus = session.getAttribute("closureStatus").toString();
		String terminationId = session.getAttribute("terminationId").toString();
		logger.info("Inside openCancellationAuthor.........loan id: "+loanId);
		logger.info("Inside openCancellationAuthor.........closureStatus: "+closureStatus);
		session.removeAttribute("closureNew");
		session.removeAttribute("closureData");
		session.removeAttribute("cancellationData");
		session.removeAttribute("closureDataDisabled");
		session.setAttribute("loanId",loanId);
		session.setAttribute("closureStatus",closureStatus);
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		//change by sachin
		CancellationDAO service=(CancellationDAO)DaoImplInstanceFactory.getDaoImplInstance(CancellationDAO.IDENTITY);
	    logger.info("Implementation class: "+service.getClass());
	    //end by sachin	
//		CancellationDAO service = new CancellationDAOImpl();
		ArrayList<CancellationVO> cancellationDataDisabled = service.selectCancellationData(loanId,terminationId);
		session.setAttribute("cancellationDataDisabled", cancellationDataDisabled);
		return mapping.findForward("openCancellationAuthorWithData");
	}

}