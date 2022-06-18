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

import com.cm.dao.PartPrePaymentDAO;
import com.cm.vo.PartPrePaymentMakerVO;
import com.cm.vo.PartPrePaymentSearchVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class PartPrePaymentSearchDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(PartPrePaymentSearchDispatchAction.class.getName());
	
	public ActionForward openNewPartPrepayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("Inside openNewPartPrepayment");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in openNewPartPrepayment method of PartPrePaymentSearchDispatchAction action the session is out----------------");
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
		session.removeAttribute("partPrePaymentDataAuthor");
		session.removeAttribute("reschId");
		session.removeAttribute("loanId");
		
		PartPrePaymentDAO service=(PartPrePaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PartPrePaymentDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		String realizeFlag=service.partPrePaymentRealizeFlag();
		session.setAttribute("realizeFlag",realizeFlag);
		
		request.setAttribute("partPrepaymentNew","partPrepaymentNew");
		return mapping.findForward("openNewPartPrepayment");
	}
	
	public ActionForward searchPartPrePayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ........searchPartPrePayment");
		
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
			logger.info("here in searchPartPrePayment method of PartPrePaymentSearchDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		PartPrePaymentSearchVO vo = new PartPrePaymentSearchVO();
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
		vo.setCurrentPageLink(currentPageLink);
		
		String type = CommonFunction.checkNull(request.getParameter("type"));
		DynaValidatorForm SDLiquidationSearchDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,SDLiquidationSearchDynaValidatorForm);

		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
				{ 
					vo.setReportingToUserId(userId);
				   //logger.info("When user id is not selected by the user:::::"+userId);
				}
				logger.info("user Id:::::"+vo.getReportingToUserId());
				vo.setStage(type);
				vo.setBranchId(branchId);
				vo.setUserId(userId);
		//CreditManagementDAO service = new CreditManagementDAOImpl();
				PartPrePaymentDAO service=(PartPrePaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PartPrePaymentDAO.IDENTITY);
				logger.info("Implementation class: "+service.getClass());
		ArrayList<PartPrePaymentSearchVO> partPrePaymentSearchList = service.searchPartPrePaymentData(vo,type);
		if(partPrePaymentSearchList.size()==0)
		{
			request.setAttribute("message","N");
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("partPrePaymetMakerSearch","partPrePaymetMakerSearch");
				request.setAttribute("partPrePaymentSearchList", "partPrePaymentSearchList");
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("partPrePaymetAuthorSearch","partPrePaymetAuthorSearch");
				request.setAttribute("partPrePaymentSearchList", "partPrePaymentSearchList");
			}
		}
		else
		{
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("partPrePaymetMakerSearch","partPrePaymetMakerSearch");
				request.setAttribute("partPrePaymentSearchList", "partPrePaymentSearchList");
				request.setAttribute("list", partPrePaymentSearchList);
				
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("partPrePaymetAuthorSearch","partPrePaymetAuthorSearch");
				request.setAttribute("partPrePaymentSearchList", "partPrePaymentSearchList");
				request.setAttribute("list", partPrePaymentSearchList);
			}
		}
		return mapping.findForward("searchPartPrePayment");
	} 
	
	public ActionForward showPartPrePaymentDataMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ........showPartPrePaymentDataMaker");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{
			logger.info("here in showPartPrePaymentDataMaker method of PartPrePaymentSearchDispatchAction  action the session is out----------------");
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
		session.removeAttribute("partPrePaymentDataAuthor");
		session.removeAttribute("reschId");
		session.removeAttribute("loanId");
		
		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		String reschId = CommonFunction.checkNull(request.getParameter("reschId"));
		
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		PartPrePaymentDAO service=(PartPrePaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PartPrePaymentDAO.IDENTITY);
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
			request.setAttribute("message", "Locked");
			request.setAttribute("recordId", loanId);
			request.setAttribute("partPrePaymetMakerSearch","partPrePaymetMakerSearch");
			return mapping.findForward("searchPartPrePayment");
		}
		}
		ArrayList<PartPrePaymentMakerVO> partPrePaymentData = service.selectPartPrePaymentData(loanId,reschId,"P");
		String realizeFlag=service.partPrePaymentRealizeFlag();
		session.setAttribute("realizeFlag",realizeFlag);
		request.setAttribute("partPrePaymentData", partPrePaymentData);
		request.setAttribute("reschId",reschId);
		
		PartPrePaymentMakerVO makerVO =null;
		String instType = "";
		if(partPrePaymentData!=null && partPrePaymentData.size()>0)
		{
			makerVO = (PartPrePaymentMakerVO)partPrePaymentData.get(0);
		 instType = CommonFunction.checkNull(makerVO.getInstType());
		 ArrayList genericMasterList =null;
		 if(instType.equalsIgnoreCase("E") ||instType.equalsIgnoreCase("R") || instType.equalsIgnoreCase("G"))
		  genericMasterList = service.getGenericMasterInfo("RESCHEDULE_TYPE1");
		 else if(instType.equalsIgnoreCase("P") ||instType.equalsIgnoreCase("Q") || instType.equalsIgnoreCase("S"))
			 genericMasterList = service.getGenericMasterInfo("RESCHEDULE_TYPE2");
		 
		request.setAttribute("genericMasterList", genericMasterList);
		}
		
		return mapping.findForward("showPartPrePaymentDataMaker");
	}
	
	public ActionForward showPartPrePaymentDataAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ........showPartPrePaymentDataAuthor");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId = "";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{
			logger.info("here in showPartPrePaymentDataAuthor method of PartPrePaymentSearchDispatchAction action the session is out----------------");
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
		session.removeAttribute("partPrePaymentDataAuthor");
		session.removeAttribute("reschId");
		session.removeAttribute("loanId");
		
		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		String reschId = CommonFunction.checkNull(request.getParameter("reschId"));
		
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		PartPrePaymentDAO service=(PartPrePaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PartPrePaymentDAO.IDENTITY);
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
			request.setAttribute("message", "Locked");
			request.setAttribute("recordId", loanId);
			request.setAttribute("partPrePaymetAuthorSearch","partPrePaymetAuthorSearch");
			return mapping.findForward("searchPartPrePayment");
		}
		}
		ArrayList<PartPrePaymentMakerVO> partPrePaymentDataAuthor = service.selectPartPrePaymentData(loanId,reschId,"F");
		session.setAttribute("partPrePaymentDataAuthor", partPrePaymentDataAuthor);
		session.setAttribute("reschId",reschId);
		session.setAttribute("loanId",loanId);
		
		
		PartPrePaymentMakerVO makerVO =null;
		String instType = "";
		if(partPrePaymentDataAuthor!=null && partPrePaymentDataAuthor.size()>0)
		{
			makerVO = (PartPrePaymentMakerVO)partPrePaymentDataAuthor.get(0);
		 instType = CommonFunction.checkNull(makerVO.getInstType());
		 ArrayList genericMasterList =null;
		 if(instType.equalsIgnoreCase("E") ||instType.equalsIgnoreCase("R") || instType.equalsIgnoreCase("G"))
		  genericMasterList = service.getGenericMasterInfo("RESCHEDULE_TYPE1");
		 else if(instType.equalsIgnoreCase("P") ||instType.equalsIgnoreCase("Q") || instType.equalsIgnoreCase("S"))
			 genericMasterList = service.getGenericMasterInfo("RESCHEDULE_TYPE2");
		 
		session.setAttribute("genericMasterList", genericMasterList);
		}
		
		return mapping.findForward("showPartPrePaymentDataAuthor");
	}

	public ActionForward partPrePaymetMakerSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside partPrePaymetMaker...........");
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
			logger.info("here in partPrePaymetMakerSearch method of PartPrePaymentSearchDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		PartPrePaymentSearchVO vo = new PartPrePaymentSearchVO();

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
		vo.setCurrentPageLink(currentPageLink);
		
		String type = "P";
		DynaValidatorForm SDLiquidationSearchDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,SDLiquidationSearchDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setStage(type);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		PartPrePaymentDAO service=(PartPrePaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PartPrePaymentDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		ArrayList<PartPrePaymentSearchVO> partPrePaymentSearchList = service.searchPartPrePaymentData(vo,type);
		if(partPrePaymentSearchList.size()==0)
		{
			request.setAttribute("message","N");
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("partPrePaymetMakerSearch","partPrePaymetMakerSearch");
				request.setAttribute("partPrePaymentSearchList", "partPrePaymentSearchList");
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("partPrePaymetAuthorSearch","partPrePaymetAuthorSearch");
				request.setAttribute("partPrePaymentSearchList", "partPrePaymentSearchList");
			}
		}
		else
		{
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("partPrePaymetMakerSearch","partPrePaymetMakerSearch");
				request.setAttribute("partPrePaymentSearchList", "partPrePaymentSearchList");
				request.setAttribute("list", partPrePaymentSearchList);
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("partPrePaymetAuthorSearch","partPrePaymetAuthorSearch");
				request.setAttribute("partPrePaymentSearchList", "partPrePaymentSearchList");
				request.setAttribute("list", partPrePaymentSearchList);
			}
		}
		return mapping.findForward("searchPartPrePayment");
	}
	
	
	public ActionForward partPrePaymetAuthorSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside partPrePaymetAuthor...........");
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
			logger.info("here in partPrePaymetAuthorSearch method of PartPrePaymentSearchDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		PartPrePaymentSearchVO vo = new PartPrePaymentSearchVO();
	
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
		vo.setCurrentPageLink(currentPageLink);
		
		String type = "F";
		DynaValidatorForm SDLiquidationSearchDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,SDLiquidationSearchDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setStage(type);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		PartPrePaymentDAO service=(PartPrePaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PartPrePaymentDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		ArrayList<PartPrePaymentSearchVO> partPrePaymentSearchList = service.searchPartPrePaymentData(vo,type);
		if(partPrePaymentSearchList.size()==0)
		{
			request.setAttribute("message","N");
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("partPrePaymetMakerSearch","partPrePaymetMakerSearch");
				request.setAttribute("partPrePaymentSearchList", "partPrePaymentSearchList");
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("partPrePaymetAuthorSearch","partPrePaymetAuthorSearch");
				request.setAttribute("partPrePaymentSearchList", "partPrePaymentSearchList");
			}
		}
		else
		{
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("partPrePaymetMakerSearch","partPrePaymetMakerSearch");
				request.setAttribute("partPrePaymentSearchList", "partPrePaymentSearchList");
				request.setAttribute("list", partPrePaymentSearchList);
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("partPrePaymetAuthorSearch","partPrePaymetAuthorSearch");
				request.setAttribute("partPrePaymentSearchList", "partPrePaymentSearchList");
				request.setAttribute("list", partPrePaymentSearchList);
			}
		}
		return mapping.findForward("searchPartPrePayment");
	}
}
