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

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.CommonDealVo;
//import com.cp.vo.EditDealVo;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cp.vo.DealMovementVo;

public class DealReassignmentSearchDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(DealReassignmentSearchDispatchAction.class.getName());
	
	public ActionForward openNewDealReassignment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("Inside openNewDealReassignment method of DealReassignmentSearchDispatchAction action");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in openNewDealReassignment method of DealReassignmentSearchDispatchAction action the session is out----------------");
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
//		session.removeAttribute("editDealDataAuthor");
//		session.removeAttribute("dealId");
//		session.removeAttribute("dealCatList");
//		session.removeAttribute("repaymentMode");
//		session.removeAttribute("sector");
		
//		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
//		logger.info("Implementation class: "+service.getClass());
		
		request.setAttribute("dealReassignmentNew","dealReassignmentNew");
		return mapping.findForward("openNewDealReassignment");
	}
	
	public ActionForward dealReassignmentMakerSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside dealReassignmentMakerSearch method of  DealReassignmentSearchDispatchAction action");
		
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		session.removeAttribute("userId");
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
			session.setAttribute("userId", userId);
		}else{
			logger.info("here in dealReassignmentMakerSearch method of  DealReassignmentSearchDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}

		CommonDealVo vo = new CommonDealVo();
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
		
		//Richa changes starts
		String functionId=(String)session.getAttribute("functionId");
		int funid=Integer.parseInt(functionId);	
		String type="";
		if(funid==3000961)
		{
		 type = "P";
		}
		else if (funid==3000962)
		{
			type = "F";
		}
		
		//String type = "P";
		//Richa changes ends
		DynaValidatorForm CommonDealDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CommonDealDynaValidatorForm);
		vo.setStage(type);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());

		ArrayList<CommonDealVo> dealReassignmentSearchList = service.searchDealReassignment(vo,type);
		if(dealReassignmentSearchList.size()==0)
		{
			request.setAttribute("message","N");
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("dealReassignmentMakerSearch","dealReassignmentMakerSearch");
				request.setAttribute("dealReassignmentSearchList", "dealReassignmentSearchList");
				request.setAttribute("list", dealReassignmentSearchList);
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("dealReassignmentAuthorSearch","dealReassignmentAuthorSearch");
				request.setAttribute("dealReassignmentSearchList", "dealReassignmentSearchList");
				request.setAttribute("list", dealReassignmentSearchList);
			}
		}
		else
		{
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("dealReassignmentMakerSearch","dealReassignmentMakerSearch");
				request.setAttribute("dealReassignmentSearchList", "dealReassignmentSearchList");
				request.setAttribute("list", dealReassignmentSearchList);
				
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("dealReassignmentAuthorSearch","dealReassignmentAuthorSearch");
				request.setAttribute("dealReassignmentSearchList", "dealReassignmentSearchList");
				request.setAttribute("list", dealReassignmentSearchList);
			}
		}
		
		return mapping.findForward("searchDealReassignment");
	}
	
	public ActionForward searchDealReassignment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ........searchDealReassignment method of DealReassignmentSearchDispatchAction");
		
		HttpSession session = request.getSession();
		
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		String makerDate="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
			makerDate= userobj.getBusinessdate();
		}else{
			logger.info("here in searchDealReassignment method of DealReassignmentSearchDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		CommonDealVo vo = new CommonDealVo();
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
		
		
		String type = CommonFunction.checkNull(request.getParameter("type"));
		DynaValidatorForm CommonDealDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CommonDealDynaValidatorForm);
		vo.setStage(type);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		DealMovementVo dealMovVo = new DealMovementVo();
		dealMovVo.setMakerId(userId);
		dealMovVo.setMakerDate(makerDate);
		dealMovVo.setLbxDealNo(vo.getLbxDealNo());
		String status="";
		if(CommonFunction.checkNull(type).equalsIgnoreCase("P"))
		{
		status = service.insertDealMovementEdit(dealMovVo);
		}
		ArrayList<CommonDealVo> editDealSearchList = service.searchDealReassignment(vo,type);
		if(!CommonFunction.checkNull(status).equalsIgnoreCase("S"))
		{
			request.setAttribute("message","N");
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("dealReassignmentMakerSearch","dealReassignmentMakerSearch");
				request.setAttribute("dealReassignmentSearchList", "dealReassignmentSearchList");
				request.setAttribute("list", editDealSearchList);
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("dealReassignmentAuthorSearch","dealReassignmentAuthorSearch");
				request.setAttribute("dealReassignmentSearchList", "dealReassignmentSearchList");
				request.setAttribute("list", editDealSearchList);
			}
		}
		else
		{
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("dealReassignmentMakerSearch","dealReassignmentMakerSearch");
				request.setAttribute("dealReassignmentSearchList", "dealReassignmentSearchList");

				request.setAttribute("list", editDealSearchList);
				
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("dealReassignmentAuthorSearch","dealReassignmentAuthorSearch");
				request.setAttribute("dealReassignmentSearchList", "dealReassignmentSearchList");
				request.setAttribute("list", editDealSearchList);
			}
		}
		return mapping.findForward("searchDealReassignment");
	} 
	
//	public ActionForward showEditDealDataMaker(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception{
//		logger.info("Inside ........showEditDealDataMaker");
//		
//		HttpSession session = request.getSession();
//		boolean flag=false;
//		UserObject userobj=(UserObject)session.getAttribute("userobject");
//		String userId = "";
//		String bDate = "";
//		if(userobj!=null){
//			userId= userobj.getUserId();
//			bDate=userobj.getBusinessdate();
//		}else{
//			logger.info("here in showEditDealDataMaker method of DealReassignmentSearchDispatchAction action the session is out----------------");
//			return mapping.findForward("sessionOut");
//		}
//		Object sessionId = session.getAttribute("sessionID");
//		//for check User session start
//		ServletContext context = getServlet().getServletContext();
//		String strFlag="";	
//		if(sessionId!=null)
//		{
//			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
//		}
//		
//		logger.info("strFlag .............. "+strFlag);
//		if(!strFlag.equalsIgnoreCase(""))
//		{
//			if(strFlag.equalsIgnoreCase("sameUserSession"))
//			{
//				context.removeAttribute("msg");
//				context.removeAttribute("msg1");
//			}
//			else if(strFlag.equalsIgnoreCase("BODCheck"))
//			{
//				context.setAttribute("msg", "B");
//			}
//			return mapping.findForward("logout");
//		}
//		session.removeAttribute("editDealDataAuthor");
//		session.removeAttribute("dealId");
//		
//		String dealId = CommonFunction.checkNull(request.getParameter("dealId"));
//		
//		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
//		logger.info("Implementation class: "+service.getClass());
//		logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
//		String functionId="";
//
//	
//		if(session.getAttribute("functionId")!=null)
//		{
//			functionId=session.getAttribute("functionId").toString();
//		}
//		
//		
//		//ServletContext context=getServlet().getServletContext();
//		if(context!=null)
//		{
//			flag = LockRecordCheck.lockCheck(userId,functionId,dealId,context);
//			logger.info("Flag ........................................ "+flag);
//			if(!flag)
//			{
//				logger.info("Record is Locked");			
//				request.setAttribute("message", "Locked");
//				request.setAttribute("recordId", dealId);
//				request.setAttribute("editDealMakerSearch","editDealMakerSearch");
//				return mapping.findForward("searchEditDeal");
//			}
//		}
//		ArrayList<EditDealVo> editDealData = service.selectEditDealData(dealId,"P");
//		request.setAttribute("editDealData", editDealData);
//		ArrayList<Object> dealCatList = service.getDealCatList();
//		request.setAttribute("dealCatList", dealCatList);
//		ArrayList<Object> repaymentMode = service.getPaymentModes();
//		request.setAttribute("repaymentMode",repaymentMode);
//		ArrayList sector = service.getSectorList();
//		request.setAttribute("sector", sector);
//		return mapping.findForward("showEditDealDataMaker");
//	}
//	
//	public ActionForward editDealAuthorSearch(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		logger.info("Inside editDealAuthorSearch...........");
//		
//		
//		HttpSession session =  request.getSession();
//		boolean flag=false;
//		UserObject userobj=(UserObject)session.getAttribute("userobject");
//		String userId="";
//		String branchId="";
//		if(userobj!=null)
//		{
//			userId=userobj.getUserId();
//			branchId=userobj.getBranchId();
//		}else{
//			logger.info("here in editDealAuthorSearch method of DealReassignmentSearchDispatchAction  action the session is out----------------");
//			return mapping.findForward("sessionOut");
//		}
//		CommonDealVo vo = new CommonDealVo();
//		Object sessionId = session.getAttribute("sessionID");
//
//		//for check User session start
//		ServletContext context = getServlet().getServletContext();
//		String strFlag="";	
//		if(sessionId!=null)
//		{
//			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
//		}
//		
//		logger.info("strFlag .............. "+strFlag);
//		if(!strFlag.equalsIgnoreCase(""))
//		{
//			if(strFlag.equalsIgnoreCase("sameUserSession"))
//			{
//				context.removeAttribute("msg");
//				context.removeAttribute("msg1");
//			}
//			else if(strFlag.equalsIgnoreCase("BODCheck"))
//			{
//				context.setAttribute("msg", "B");
//			}
//			return mapping.findForward("logout");
//		}
//		int currentPageLink = 0;
//		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
//		{
//			currentPageLink=1;
//		}
//		else
//		{
//			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
//		}
//		logger.info("current page link ................ "+request.getParameter("d-49520-p"));
//		vo.setCurrentPageLink(currentPageLink);
//		
//		String type = "F";
//		DynaValidatorForm CommonDealDynaValidatorForm = (DynaValidatorForm)form;
//		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CommonDealDynaValidatorForm);
//		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
//		{ 
//			vo.setReportingToUserId(userId);
//		   //logger.info("When user id is not selected by the user:::::"+userId);
//		}
//		logger.info("user Id:::::"+vo.getReportingToUserId());
//		vo.setStage(type);
//		vo.setBranchId(branchId);
//		vo.setUserId(userId);
//		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
//		logger.info("Implementation class: "+service.getClass());
//		ArrayList<CommonDealVo> editDealSearchList = service.searchEditDeal(vo,type);
//		if(editDealSearchList.size()==0)
//		{
//			request.setAttribute("message","N");
//			if(type.equalsIgnoreCase("P"))
//			{
//				request.setAttribute("editDealMakerSearch","editDealMakerSearch");
//				request.setAttribute("editDealSearchList", "editDealSearchList");
//			}
//			
//			else if(type.equalsIgnoreCase("F"))
//			{
//				request.setAttribute("editDealAuthorSearch","editDealAuthorSearch");
//				request.setAttribute("editDealSearchList", "editDealSearchList");
//			}
//		}
//		else
//		{
//			if(type.equalsIgnoreCase("P"))
//			{
//				request.setAttribute("editDealMakerSearch","editDealMakerSearch");
//				request.setAttribute("editDealSearchList", "editDealSearchList");
//				request.setAttribute("list", editDealSearchList);
//				
//			}
//			
//			else if(type.equalsIgnoreCase("F"))
//			{
//				request.setAttribute("editDealAuthorSearch","editDealAuthorSearch");
//				request.setAttribute("editDealSearchList", "editDealSearchList");
//				request.setAttribute("list", editDealSearchList);
//			}
//		}
//		ArrayList<Object> dealCatList = service.getDealCatList();
//		session.setAttribute("dealCatList", dealCatList);
//		ArrayList<Object> repaymentMode = service.getPaymentModes();
//		session.setAttribute("repaymentMode",repaymentMode);
//		ArrayList sector = service.getSectorList();
//		session.setAttribute("sector", sector);
//		return mapping.findForward("searchEditDeal");
//	}

	public ActionForward showEditDealDataAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ........showEditDealDataAuthor");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate ="";
		if(userobj!=null){
			userId= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in showEditDealDataAuthor method of DealReassignmentSearchDispatchAction action the session is out----------------");
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
		session.removeAttribute("editDealDataAuthor");
		session.removeAttribute("dealId");
		session.removeAttribute("dealCatList");
		session.removeAttribute("repaymentMode");
		session.removeAttribute("sector");
		
		String dealId = CommonFunction.checkNull(request.getParameter("dealId"));
		
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
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
			flag = LockRecordCheck.lockCheck(userId,functionId,dealId,context);
			logger.info("Flag ........................................ "+flag);
			if(!flag)
			{
				logger.info("Record is Locked");			
				request.setAttribute("message", "Locked");
				request.setAttribute("recordId", dealId);
				request.setAttribute("editDealAuthorSearch","editDealAuthorSearch");
				return mapping.findForward("searchEditDeal");
			}
		}
		//ArrayList editDealDataAuthor = service.selectEditDealData(dealId,"F");
		//session.setAttribute("editDealDataAuthor", editDealDataAuthor);
		session.setAttribute("dealId",dealId);
		ArrayList<Object> dealCatList = service.getDealCatList();
		session.setAttribute("dealCatList", dealCatList);
		//ArrayList<Object> repaymentMode = service.getPaymentModes();
		//session.setAttribute("repaymentMode",repaymentMode);
		ArrayList sector = service.getSectorList();
		session.setAttribute("sector", sector);
		return mapping.findForward("showEditDealDataAuthor");
	}
}
