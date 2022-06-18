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
import com.cp.vo.DealMovementVo;
//import com.cp.vo.EditDealVo;
import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class DealReassignmentAuthorDispatchAction extends DispatchAction{

	private static final Logger logger = Logger.getLogger(DealReassignmentAuthorDispatchAction.class.getName());
	
	
	public ActionForward openReassignDealAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ........openReassignDealAuthor");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		int compid =0;
		if(userobj!=null){
			userId = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
				compid=userobj.getCompanyId();
		}else{
			logger.info("here in openReassignDealAuthor method of DealReassignmentAuthorDispatchAction action the session is out----------------");
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
		return mapping.findForward("openReassignDealAuthor");
	}
	
	public ActionForward saveDealReassignAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ........saveEditDealAuthor");
		
		HttpSession session =  request.getSession();
		
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		int compid =0;
		if(userobj!=null){
			userId = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
				compid=userobj.getCompanyId();
		}else{
			logger.info("here in saveDealReassignAuthor method of DealReassignmentAuthorDispatchAction action the session is out----------------");
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
		
		String dealId = session.getAttribute("dealId").toString();
		
		String status = "";
		DynaValidatorForm EditDealDynaValidatorForm = (DynaValidatorForm)form;
		DealMovementVo vo = new DealMovementVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,EditDealDynaValidatorForm);

		
		vo.setAuthorId(userId);
		vo.setAuthorDate(businessDate);
		CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		status = dao.saveReassignDealAuthor(vo,dealId);
		if(status.equalsIgnoreCase("S"))
		{
			request.setAttribute("message","S");
		}
		else
		{
			request.setAttribute("message","PROCERR");
			request.setAttribute("status",status);
		}
		return mapping.findForward("saveReassignDealAuthor");
	}
	//Richa changes starts
		public ActionForward showEditDealDataAuthor(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception{
			logger.info("Inside ......searchDealForReassignmentAuthor method of DealReassignmentMakerDispatchAction");
			
			HttpSession session =  request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String makerId="";
			String makerDate ="";
			String dealId = "";
			if(userobj!=null){
				makerId=userobj.getUserId();
				makerDate= userobj.getBusinessdate();
			}else{
				logger.info("here in searchDealForReassignmentAuthor method of DealReassignmentMakerDispatchAction action the session is out----------------");
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
			
			dealId = CommonFunction.checkNull(request.getParameter("dealId"));
			String dealNo = CommonFunction.checkNull(request.getParameter("dealNo"));
			String customerName = CommonFunction.checkNull(request.getParameter("customerName"));
			String flag = CommonFunction.checkNull(request.getParameter("flag"));
			logger.info("flaf==============****==============: "+flag);
			DealMovementVo vo = new DealMovementVo();
			vo.setMakerId(makerId);
			vo.setMakerDate(makerDate);
			vo.setLbxDealNo(dealId);
			
			CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
			logger.info("Implementation class: "+service.getClass());
			String status = "";
			String functionId=(String)session.getAttribute("functionId");
			int funid=Integer.parseInt(functionId);	
//			String type="";
//			if(funid==3000961)
//			{
//			 type = "P";
//			}
//			else if (funid==3000962)
//			{
//				type = "F";
//			}
			
				logger.info("inside else of no save...");
				ArrayList dealMovementList = service.getOldDealMovementDetail(dealId);
				logger.info("dealMovementList size .................................. "+dealMovementList.size());
				session.setAttribute("dealMovementList", dealMovementList);
				
//				ArrayList dealMovementListNew = service.getNewDealMovementDetail(dealId,type);
//				logger.info("dealMovementListNew size ......... "+dealMovementListNew.size());
//				request.setAttribute("dealMovementListNew", dealMovementListNew);
//				
//				session.setAttribute("dealId", dealId); 

			
			ArrayList workFlowStage = service.getAllInitiateWorkFlowStage(dealId);
			request.setAttribute("workFlowStage", workFlowStage);
			 ArrayList stageDetails=service.getStageDetails(dealId);
			 session.setAttribute("stageDetails", stageDetails);
			session.setAttribute("dealNo", dealNo); 
			session.setAttribute("customerName", customerName); 
			request.setAttribute("workFlowStage", workFlowStage);
			session.setAttribute("dealId", dealId); 
			session.setAttribute("dealReassignmentAuthor", "dealReassignmentAuthor");
			logger.info("in first Method");
			//start by raj
			if(stageDetails.size()>0)
			{
			DealMovementVo vo1=(DealMovementVo)stageDetails.get(0);
			String stageActionDesc=vo1.getStageActionDesc();
			//String stageActionDesc=CommonFunction.checkNull(vo.getStageActionDesc());
			logger.info("stageActionDesc---->>>"+stageActionDesc);
			if(stageActionDesc.equalsIgnoreCase("User ReAssignment"))
			{
				session.setAttribute("stageActionDesc1", stageActionDesc);
			}
			if(stageActionDesc.equalsIgnoreCase("Stage Correction"))
			{
				session.setAttribute("stageActionDesc2", stageActionDesc);
			}
			}
			//end by raj
			
			return mapping.findForward("searchDealReassignmentAuthor");
		}
		//Richa changes ends
		
		public ActionForward showEditDealDataAuthorTab(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception{
			logger.info("Inside ......searchDealForReassignmentAuthor method of DealReassignmentMakerDispatchAction");
			
			HttpSession session =  request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String makerId="";
			String makerDate ="";
			String dealId = "";
			if(userobj!=null){
				makerId=userobj.getUserId();
				makerDate= userobj.getBusinessdate();
			}else{
				logger.info("here in searchDealForReassignmentAuthor method of DealReassignmentMakerDispatchAction action the session is out----------------");
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
			
			dealId = CommonFunction.checkNull(session.getAttribute("dealId"));
			String dealNo = CommonFunction.checkNull(session.getAttribute("dealNo"));
			String customerName = CommonFunction.checkNull(session.getAttribute("customerName"));
			String flag = CommonFunction.checkNull(session.getAttribute("flag"));
			logger.info("flaf==============****==============: "+flag);
			DealMovementVo vo = new DealMovementVo();
			vo.setMakerId(makerId);
			vo.setMakerDate(makerDate);
			vo.setLbxDealNo(dealId);
			
			CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
			logger.info("Implementation class: "+service.getClass());
			String status = "";
			String functionId=(String)session.getAttribute("functionId");
			int funid=Integer.parseInt(functionId);	
//			String type="";
//			if(funid==3000961)
//			{
//			 type = "P";
//			}
//			else if (funid==3000962)
//			{
//				type = "F";
//			}
			
				logger.info("inside else of no save...");
				ArrayList dealMovementList = service.getOldDealMovementDetail(dealId);
				logger.info("dealMovementList size .................................. "+dealMovementList.size());
				session.setAttribute("dealMovementList", dealMovementList);
				
//				ArrayList dealMovementListNew = service.getNewDealMovementDetail(dealId,type);
//				logger.info("dealMovementListNew size ......... "+dealMovementListNew.size());
//				request.setAttribute("dealMovementListNew", dealMovementListNew);
//				
//				session.setAttribute("dealId", dealId); 

			
			ArrayList workFlowStage = service.getAllInitiateWorkFlowStage(dealId);
			request.setAttribute("workFlowStage", workFlowStage);
			 ArrayList stageDetails=service.getStageDetails(dealId);
			 session.setAttribute("stageDetails", stageDetails);
			session.setAttribute("dealNo", dealNo); 
			session.setAttribute("customerName", customerName); 
			request.setAttribute("workFlowStage", workFlowStage);
			session.setAttribute("dealId", dealId); 
			session.setAttribute("dealReassignmentAuthor", "dealReassignmentAuthor");
			logger.info("In second method");
			
			
			return mapping.findForward("searchDealReassignmentAuthoTab");
		}
}
