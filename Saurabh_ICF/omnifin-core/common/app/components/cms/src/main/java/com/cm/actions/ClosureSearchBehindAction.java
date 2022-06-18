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
import org.apache.struts.validator.DynaValidatorForm;
import com.cm.dao.CancellationDAO;
import com.cm.dao.EarlyClosureDAO;
import com.cm.dao.MaturityClosureDAO;
import com.cm.vo.ClosureSearchVO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ClosureSearchBehindAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ClosureSearchBehindAction.class.getName());	
	public ActionForward earlyClosure(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside earlyClosure...........");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in earlyClosure method of ClosureSearchBehindAction action the session is out----------------");
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
		ClosureSearchVO vo = new ClosureSearchVO();
		DynaValidatorForm ClosureSearchDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ClosureSearchDynaValidatorForm);
		vo.setCurrentPageLink(currentPageLink);

		String status="P";
		String type="T";
		vo.setStage(status);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		vo.setReportingToUserId(userId);
		
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		EarlyClosureDAO service=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		ArrayList<ClosureSearchVO> searchDataList = service.searchClosureData(vo,status,type);
		request.setAttribute("earlyClosure", "earlyClosure");
		
		request.setAttribute("searchDataList", "searchDataList");
		request.setAttribute("list", searchDataList);
		return mapping.findForward("earlyClosure");
	}
	
	public ActionForward maturityClosure(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside maturityClosure...........");
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in maturityClosure method of ClosureSearchBehindAction action the session is out----------------");
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
		ClosureSearchVO vo = new ClosureSearchVO();
		DynaValidatorForm ClosureSearchDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ClosureSearchDynaValidatorForm);
		vo.setCurrentPageLink(currentPageLink);

		String status="P";
		String type="C";
		vo.setStage(status);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		vo.setReportingToUserId(userId);
		
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		//MaturityClosureDAO service = new MaturityClosureDAOImpl();
		MaturityClosureDAO service=(MaturityClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(MaturityClosureDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		ArrayList<ClosureSearchVO> searchDataList = service.searchClosureData(vo,status,type);
		request.setAttribute("maturityClosure", "maturityClosure");
		
		request.setAttribute("searchDataList", "searchDataList");
		request.setAttribute("list", searchDataList);
		return mapping.findForward("maturityClosure");
	}
	
	public ActionForward cancellationClosure(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside cancellationClosure...........");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in cancellationClosure method of ClosureSearchBehindAction action the session is out----------------");
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
		ClosureSearchVO vo = new ClosureSearchVO();
		DynaValidatorForm ClosureSearchDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ClosureSearchDynaValidatorForm);
		vo.setCurrentPageLink(currentPageLink);

		String status="P";
		String type="X";
		vo.setStage(status);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		vo.setReportingToUserId(userId);
		
		
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		//change by sachin
		CancellationDAO service=(CancellationDAO)DaoImplInstanceFactory.getDaoImplInstance(CancellationDAO.IDENTITY);
	    logger.info("Implementation class: "+service.getClass());
	    //end by sachin	
//		CancellationDAO service = new CancellationDAOImpl();
		ArrayList<ClosureSearchVO> searchDataList = service.searchClosureData(vo,status,type);
		request.setAttribute("cancellationClosure", "cancellationClosure");
		request.setAttribute("searchDataList", "searchDataList");
		request.setAttribute("list", searchDataList);
		
		return mapping.findForward("cancellationClosure");
	}
	
	public ActionForward earlyClosureAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside earlyClosureAuthor...........");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in earlyClosureAuthor method of ClosureSearchBehindAction action the session is out----------------");
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
		ClosureSearchVO vo = new ClosureSearchVO();
		DynaValidatorForm ClosureSearchDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ClosureSearchDynaValidatorForm);
		vo.setCurrentPageLink(currentPageLink);

		String status="F";
		String type="T";
		vo.setStage(status);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		vo.setReportingToUserId(userId);
		
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		EarlyClosureDAO service=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		ArrayList<ClosureSearchVO> searchDataList = service.searchClosureData(vo,status,type);
		request.setAttribute("earlyClosureAuthor", "earlyClosureAuthor");
		
		request.setAttribute("searchDataList", "searchDataList");
		request.setAttribute("list", searchDataList);
		return mapping.findForward("earlyClosureAuthor");
	}
	
	public ActionForward maturityClosureAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside maturityClosureAuthor...........");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in maturityClosureAuthor method of ClosureSearchBehindAction action the session is out----------------");
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
		ClosureSearchVO vo = new ClosureSearchVO();
		DynaValidatorForm ClosureSearchDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ClosureSearchDynaValidatorForm);
		vo.setCurrentPageLink(currentPageLink);

		String status="F";
		String type="C";
		vo.setStage(status);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		vo.setReportingToUserId(userId);
		
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		MaturityClosureDAO service=(MaturityClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(MaturityClosureDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		ArrayList<ClosureSearchVO> searchDataList = service.searchClosureData(vo,status,type);
		request.setAttribute("maturityClosureAuthor", "maturityClosureAuthor");
		request.setAttribute("searchDataList", "searchDataList");
		request.setAttribute("list", searchDataList);
		return mapping.findForward("maturityClosureAuthor");
	}
	
	public ActionForward cancellationClosureAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside cancellationClosureAuthor...........");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in cancellationClosureAuthor method of ClosureSearchBehindAction action the session is out----------------");
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
		ClosureSearchVO vo = new ClosureSearchVO();
		DynaValidatorForm ClosureSearchDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ClosureSearchDynaValidatorForm);
		vo.setCurrentPageLink(currentPageLink);

		String status="F";
		String type="X";
		vo.setStage(status);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		vo.setReportingToUserId(userId);
		
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		//change by sachin
		CancellationDAO service=(CancellationDAO)DaoImplInstanceFactory.getDaoImplInstance(CancellationDAO.IDENTITY);
	    logger.info("Implementation class: "+service.getClass());
	    //end by sachin	
//		CancellationDAO service = new CancellationDAOImpl();
		ArrayList<ClosureSearchVO> searchDataList = service.searchClosureData(vo,status,type);
		request.setAttribute("cancellationClosureAuthor", "cancellationClosureAuthor");
		request.setAttribute("searchDataList", "searchDataList");
		request.setAttribute("list", searchDataList);

	
		return mapping.findForward("cancellationClosureAuthor");
	}

}
