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
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ClosureSearchDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ClosureSearchDispatchAction.class.getName());
	public ActionForward searchLoanData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LoggerMsg.info("Inside searchLoanData::::::::::::::::::::::::::");
		logger.info("::::::::::::::::In EarlyClosureDAOImpl");
		
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
			logger.info("here in searchLoanData method of ClosureSearchDispatchAction action the session is out----------------");
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
		vo.setCurrentPageLink(currentPageLink);

		String status=request.getParameter("status");
		String type=request.getParameter("type");
		DynaValidatorForm ClosureSearchDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ClosureSearchDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setStage(status);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		EarlyClosureDAO service=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		ArrayList<ClosureSearchVO> searchDataList = service.searchClosureData(vo,status,type);
		if(request.getParameter("type").equals("T"))
		{
			request.setAttribute("earlyClosure", "earlyClosure");
		}
		if(request.getParameter("type").equals("C"))
		{
			request.setAttribute("maturityClosure", "maturityClosure");
		}
		if(request.getParameter("type").equals("X"))
		{
			request.setAttribute("cancellationClosure", "cancellationClosure");
		}
		request.setAttribute("list", searchDataList);
		request.setAttribute("searchDataList", "searchDataList");
		return mapping.findForward("searchLoanData");
	}
	
	public ActionForward openNewClosure(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside openNewClosure...........");
		logger.info("::::::::::::::::In EarlyClosureDAOImpl");
		
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in openNewClosure method of ClosureSearchDispatchAction action the session is out----------------");
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
		
		EarlyClosureDAO service=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		String checkFlag=service.earlyClosureFlag();
		session.setAttribute("checkFlag",checkFlag);
		String realizeFlag=service.earlyClosureRealizeFlag();
		session.setAttribute("realizeFlag",realizeFlag);

		session.removeAttribute("loanId");
		session.removeAttribute("closureData");
		session.removeAttribute("type");
		String type= CommonFunction.checkNull(request.getParameter("type"));
//		String type2= CommonFunction.checkNull(request.getAttribute("type2"));
		session.setAttribute("type", type);
		String fwd="";
		
		//Virender code Start
		ArrayList<Object> closureTypeList = service.getClosureTypeList();
		session.setAttribute("closureTypeList", closureTypeList);
		//Virender Code End
		
		if(type.equals("T"))
		{
			session.removeAttribute("maturityClosureLabel");
			session.removeAttribute("closureDataDisabled");
			session.removeAttribute("cancellationDataDisabled");
			session.setAttribute("closureNew","closureNew");
			request.setAttribute("earlyClosureLabel","earlyClosureLabel");
            //code added by neeraj tripathi
			request.setAttribute("saveCompleted","N");
			request.setAttribute("waiveAllocated","N");
			request.setAttribute("changeWaiveOff","Y");
            //tripathi's space end
			fwd="openNewClosure";
		}
		if(type.equals("C"))
		{     
			session.removeAttribute("earlyClosureLabel");
			session.removeAttribute("closureDataDisabled");
			session.removeAttribute("cancellationDataDisabled");
			session.setAttribute("closureNew","closureNew");
			request.setAttribute("maturityClosureLabel","maturityClosureLabel");
			//code added by neeraj tripathi
			request.setAttribute("saveCompleted","N");
			request.setAttribute("waiveAllocated","N");
			request.setAttribute("changeWaiveOff","Y");
            //tripathi's space end
			fwd="openNewMaturityClosure";
		}
		if(type.equals("X"))
		{
			session.removeAttribute("earlyClosureLabel");
			session.removeAttribute("maturityClosureLabel");
			fwd="openNewCancellationClosure";
			session.removeAttribute("closureDataDisabled");
			session.removeAttribute("closureNew");
			session.removeAttribute("cancellationDataDisabled");
			request.setAttribute("cancellationNew","cancellationNew");
		}
		return mapping.findForward(fwd);
	}
	
	public ActionForward searchLoanDataAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside searchLoanDataAuthor...........");
		
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
			logger.info("here in searchLoanDataAuthor method of ClosureSearchDispatchAction action the session is out----------------");
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
		String status = request.getParameter("status");
		
		DynaValidatorForm ClosureSearchDynaValidatorForm= (DynaValidatorForm)form;
		
		String type = request.getParameter("type"); 
		vo.setStage(status);
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ClosureSearchDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
				{ 
					vo.setReportingToUserId(userId);
				   //logger.info("When user id is not selected by the user:::::"+userId);
				}
				logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		EarlyClosureDAO service=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		ArrayList<ClosureSearchVO> searchDataList = service.searchClosureData(vo,status,type);
		if(request.getParameter("type").equals("T"))
		{
			request.setAttribute("earlyClosureAuthor", "earlyClosureAuthor");
		}
		if(request.getParameter("type").equals("C"))
		{
			request.setAttribute("maturityClosureAuthor", "maturityClosureAuthor");
		}
		if(request.getParameter("type").equals("X"))
		{
			request.setAttribute("cancellationClosureAuthor", "cancellationClosureAuthor");
		}
		request.setAttribute("list",searchDataList);
		request.setAttribute("searchDataList", "searchDataList");
		return mapping.findForward("searchLoanData");
	}

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
			logger.info("here in earlyClosure method of ClosureSearchDispatchAction action the session is out----------------");
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
		vo.setCurrentPageLink(currentPageLink);

		String status="P";
		String type="T";
		DynaValidatorForm ClosureSearchDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ClosureSearchDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setStage(status);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		//ArrayList<ClosureSearchVO> searchDataList = service.searchClosureData(vo,status,type);
		
		EarlyClosureDAO service=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		ArrayList<ClosureSearchVO> searchDataList = service.searchClosureData(vo,status,type);
		session.setAttribute("userId", userId);
		request.setAttribute("earlyClosure", "earlyClosure");
		
		request.setAttribute("searchDataList", "searchDataList");
		request.setAttribute("list", searchDataList);
		//code added by neeraj tripathi
		session.removeAttribute("earlyClosureFlag");
		session.removeAttribute("closureButton");
		session.setAttribute("earlyClosureFlag","Y");
		session.removeAttribute("earlyAuthor");
		session.removeAttribute("saveCompleted");
		session.removeAttribute("waiveAllocated");
		session.removeAttribute("changeWaiveOff");
		session.setAttribute("closureButton","closureButton");
		//tripathi's space end
		return mapping.findForward("searchLoanData");
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
			logger.info("here in maturityClosure method of ClosureSearchDispatchAction action the session is out----------------");
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
		vo.setCurrentPageLink(currentPageLink);

		String status="P";
		String type="C";
		DynaValidatorForm ClosureSearchDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ClosureSearchDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setStage(status);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		MaturityClosureDAO service=(MaturityClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(MaturityClosureDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		ArrayList<ClosureSearchVO> searchDataList = service.searchClosureData(vo,status,type);
		request.setAttribute("maturityClosure", "maturityClosure");
		//code added by neeraj tripathi
		session.removeAttribute("earlyClosureFlag");
		session.removeAttribute("closureButton");
		session.setAttribute("earlyClosureFlag","Y");
		session.removeAttribute("earlyAuthor");
		session.removeAttribute("saveCompleted");
		session.removeAttribute("waiveAllocated");
		session.removeAttribute("changeWaiveOff");
		session.setAttribute("closureButton","closureButton");
		//tripathi's space end  rani
		request.setAttribute("searchDataList", "searchDataList");
		request.setAttribute("list", searchDataList);
		return mapping.findForward("searchLoanData");
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
			logger.info("here in cancellationClosure method of ClosureSearchDispatchAction action the session is out----------------");
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
		vo.setCurrentPageLink(currentPageLink);

		String status="P";
		String type="X";
		DynaValidatorForm ClosureSearchDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ClosureSearchDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setStage(status);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
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
		
		return mapping.findForward("searchLoanData");
	}
	
	public ActionForward earlyClosureAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside earlyClosureAuthor...........");
		logger.info("::::::::::::::::In EarlyClosureDAOImpl");
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
			logger.info("here in earlyClosureAuthor method of ClosureSearchDispatchAction action the session is out----------------");
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
		vo.setCurrentPageLink(currentPageLink);
		String status="F";
		String type="T";
		DynaValidatorForm ClosureSearchDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ClosureSearchDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setStage(status);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		EarlyClosureDAO service=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		ArrayList<ClosureSearchVO> searchDataList = service.searchClosureData(vo,status,type);
		request.setAttribute("earlyClosureAuthor", "earlyClosureAuthor");		
		request.setAttribute("searchDataList", "searchDataList");
		request.setAttribute("list", searchDataList);
		//code added by neeraj tripathi
		session.setAttribute("earlyClosureFlag","Y");
		session.setAttribute("saveCompleted","Y");
		session.setAttribute("waiveAllocated","Y");
		session.setAttribute("changeWaiveOff","N");
		session.setAttribute("earlyAuthor","Y");
		session.setAttribute("closureButton","closureButton");
		//tripathi's space end
		return mapping.findForward("searchLoanData");
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
			logger.info("here in maturityClosureAuthor method of ClosureSearchDispatchAction  action the session is out----------------");
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
		vo.setCurrentPageLink(currentPageLink);

		String status="F";
		String type="C";
		DynaValidatorForm ClosureSearchDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ClosureSearchDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setStage(status);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		MaturityClosureDAO service=(MaturityClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(MaturityClosureDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		ArrayList<ClosureSearchVO> searchDataList = service.searchClosureData(vo,status,type);
		request.setAttribute("maturityClosureAuthor", "maturityClosureAuthor");
		request.setAttribute("searchDataList", "searchDataList");
		request.setAttribute("list", searchDataList);
		//code added by neeraj tripathi
		session.setAttribute("earlyClosureFlag","Y");
		session.setAttribute("saveCompleted","Y");
		session.setAttribute("waiveAllocated","Y");
		session.setAttribute("changeWaiveOff","N");
		session.setAttribute("earlyAuthor","Y");
		session.setAttribute("closureButton","closureButton");
		//tripathi's space end
		
		return mapping.findForward("searchLoanData");
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
			logger.info("here in cancellationClosureAuthor method of ClosureSearchDispatchAction action the session is out----------------");
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
		vo.setCurrentPageLink(currentPageLink);
		String status="F";
		String type="X";
		DynaValidatorForm ClosureSearchDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ClosureSearchDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setStage(status);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
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

	
		return mapping.findForward("searchLoanData");
	}
}
