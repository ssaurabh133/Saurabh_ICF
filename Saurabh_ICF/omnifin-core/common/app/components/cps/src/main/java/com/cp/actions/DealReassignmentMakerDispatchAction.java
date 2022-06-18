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
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cp.vo.DealMovementVo;

public class DealReassignmentMakerDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(DealReassignmentMakerDispatchAction.class.getName());

	public ActionForward  searchDealForReassignmentMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ......searchDealForReassignmentMaker method of DealReassignmentMakerDispatchAction");
		
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";
		String makerDate ="";
		String dealId = "";
		if(userobj!=null){
			makerId=userobj.getUserId();
			makerDate= userobj.getBusinessdate();
		}else{
			logger.info("here in searchDealForReassignmentMaker method of DealReassignmentMakerDispatchAction action the session is out----------------");
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
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		String status = "";
		if(!flag.equalsIgnoreCase("noSave"))
		{
			status = service.insertDealMovementEdit(vo);
			if(status.equalsIgnoreCase("S"))
			{
				ArrayList dealMovementList = service.getOldDealMovementDetail(dealId);
				logger.info("dealMovementList size .................................. "+dealMovementList.size());
				request.setAttribute("dealMovementList", dealMovementList);
				
//				ArrayList dealMovementListNew = service.getNewDealMovementDetail(dealId,type);
//				logger.info("dealMovementListNew size ......... "+dealMovementListNew.size());
//				request.setAttribute("dealMovementListNew", dealMovementListNew);
				
				request.setAttribute("dealId", dealId); 
			}
			else if(status.length()>1)
			{
				request.setAttribute("message","PROCERR");
				request.setAttribute("status",status);
			}
		}
		else
		{
			logger.info("inside else of no save...");
			ArrayList dealMovementList = service.getOldDealMovementDetail(dealId);
			logger.info("dealMovementList size .................................. "+dealMovementList.size());
			request.setAttribute("dealMovementList", dealMovementList);
			
//			ArrayList dealMovementListNew = service.getNewDealMovementDetail(dealId,type);
//			logger.info("dealMovementListNew size ......... "+dealMovementListNew.size());
//			request.setAttribute("dealMovementListNew", dealMovementListNew);
			
			request.setAttribute("dealId", dealId); 
		}
		
		ArrayList workFlowStage = service.getAllInitiateWorkFlowStage(dealId);
		request.setAttribute("dealNo", dealNo); 
		request.setAttribute("customerName", customerName); 
		request.setAttribute("workFlowStage", workFlowStage);
		request.setAttribute("dealId", dealId); 
		
		request.setAttribute("dealReassignmentNew", "dealReassignmentNew");
		
		
		return mapping.findForward("searchDealReassignmentMaker");
	}
	
	public ActionForward openDealReassignmentEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ......openDealReassignmentEdit method of DealReassignmentMakerDispatchAction");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";
		String makerDate ="";
		if(userobj!=null){
			makerId=userobj.getUserId();
			makerDate= userobj.getBusinessdate();
		}else{
			logger.info("here in openDealReassignmentEdit method of DealReassignmentMakerDispatchAction action the session is out----------------");
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

	    DynaValidatorForm DealReassignmentDynaValidatorForm = (DynaValidatorForm)form;
	    DealMovementVo vo = new DealMovementVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,DealReassignmentDynaValidatorForm);
		vo.setMakerId(makerId);
		vo.setMakerDate(makerDate);
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());

		String dealId = request.getParameter("dealId");
		String checkId= request.getParameter("checkId");
		ArrayList<DealMovementVo> editDealReassignmentData = service.selectEditDealReassignmentData(dealId,checkId);
		request.setAttribute("editDealReassignmentData", editDealReassignmentData);

		return mapping.findForward("openDealReassignmentEdit");
	}
	
	public ActionForward saveDealReassignmentEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ......saveDealReassignmentEdit method of DealReassignmentMakerDispatchAction");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";
		String makerDate ="";
		if(userobj!=null){
			makerId=userobj.getUserId();
			makerDate= userobj.getBusinessdate();
		}else{
			logger.info("here in saveDealReassignmentEdit method of DealReassignmentMakerDispatchAction action the session is out----------------");
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
		
		boolean status=false;
		String dealId = CommonFunction.checkNull(request.getParameter("dealId"));
		
	    DynaValidatorForm DealReassignmentDynaValidatorForm = (DynaValidatorForm)form;
	    DealMovementVo vo = new DealMovementVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,DealReassignmentDynaValidatorForm);
		vo.setMakerId(makerId);
		vo.setMakerDate(makerDate);
		vo.setLbxDealNo(dealId);
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		status=service.saveDealReassignmentEdit(vo);
		if(status)
		{
			String result=service.validateSaveReassignDeal(dealId);
			if((CommonFunction.checkNull(result)).trim().equalsIgnoreCase("S"))
			{
				request.setAttribute("message","S");
			}
			else
			{
				request.setAttribute("message","PROCERR");
				request.setAttribute("status",result);
			}
		}
		else
		{
			request.setAttribute("message","E");
		}
		request.setAttribute("dealId", dealId);
		logger.info("dealID for page referesh: "+request.getAttribute("dealId"));
		return mapping.findForward("saveDealReassignmentEdit");
	}
	
	/*public ActionForward saveDealReassignmentMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ......saveDealReassignmentMaker method of DealReassignmentMakerDispatchAction");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";
		String makerDate ="";
		if(userobj!=null){
			makerId=userobj.getUserId();
			makerDate= userobj.getBusinessdate();
		}else{
			logger.info("here in saveDealReassignmentMaker method of DealReassignmentMakerDispatchAction action the session is out----------------");
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
		boolean status=false;

	    DynaValidatorForm EditDealDynaValidatorForm = (DynaValidatorForm)form;
	    DealMovementVo vo = new DealMovementVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,EditDealDynaValidatorForm);
		vo.setMakerId(makerId);
		vo.setMakerDate(makerDate);		
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());

		status=service.saveEditDealData(vo);
		if(status)
		{
			ArrayList<EditDealVo> editDealData = service.selectEditDealData(vo.getLbxDealNo(),"P");
			request.setAttribute("editDealData", editDealData);
			request.setAttribute("message","S");
		}
		else
		{
			request.setAttribute("message","E");
		}
		ArrayList<Object> dealCatList = service.getDealCatList();
		request.setAttribute("dealCatList", dealCatList);
		ArrayList<Object> repaymentMode = service.getPaymentModes();
		request.setAttribute("repaymentMode",repaymentMode);
		ArrayList sector = service.getSectorList();
		request.setAttribute("sector", sector);
		return mapping.findForward("saveEditDealMaker");
	}
	
	public ActionForward updateEditDealMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ......updateEditDealMaker");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";
		String makerDate ="";
		if(userobj!=null){
			makerId=userobj.getUserId();
			makerDate= userobj.getBusinessdate();
		}else{
			logger.info("here in updateEditDealMaker method of DealReassignmentMakerDispatchAction action the session is out----------------");
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
		boolean status=false;

	    DynaValidatorForm EditDealDynaValidatorForm = (DynaValidatorForm)form;
	    DealMovementVo vo = new DealMovementVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,EditDealDynaValidatorForm);
		vo.setMakerId(makerId);
		vo.setMakerDate(makerDate);		
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());

		status=service.updateEditDealData(vo);
		if(status)
		{
			request.setAttribute("message","F");
		}
		else
		{
			request.setAttribute("message","E");
		}
		return mapping.findForward("updateEditDealMaker");
	}

	public ActionForward deleteEditDealMaker(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("Inside deleteEditDealMaker()");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";
		String makerDate ="";
		if(userobj!=null){
			makerId=userobj.getUserId();
			makerDate= userobj.getBusinessdate();
		}else{
			logger.info("here in deleteEditDealMaker method of DealReassignmentMakerDispatchAction action the session is out----------------");
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
		
		String dealId="";

	    DynaValidatorForm EditDealDynaValidatorForm = (DynaValidatorForm)form;
	    DealMovementVo vo = new DealMovementVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,EditDealDynaValidatorForm);
		dealId=CommonFunction.checkNull(vo.getLbxDealNo());
		logger.info("dealId:"+dealId);
		
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		boolean status=false;
		if(dealId.trim().length()!=0)
			status=service.deleteEditDealData(dealId);
		logger.info("status   :   "+status);
		if(status)
			request.setAttribute("delete","delete");
		else
			request.setAttribute("notDelete","notDelete");
		logger.info("status  :  "+status);
		return mapping.findForward("saveEditDealMaker");
	}*/
	
}
