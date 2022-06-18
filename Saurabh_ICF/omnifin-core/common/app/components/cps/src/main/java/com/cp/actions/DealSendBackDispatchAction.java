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

import com.cm.vo.DisbursalSearchVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.DealMovementVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.connect.ConnectionDAO;
import com.communication.engn.daoImplMySql.SmsDAOImpl;

public class DealSendBackDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(DealSendBackDispatchAction.class.getName());
	
	public ActionForward getSearchData(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String branchId="";
		String userId="";
		if(userobj!=null)
		{
			branchId=userobj.getBranchId();
			userId=userobj.getUserId();
		}else{

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
		
			logger.info("In getSearchData  ");
		CreditProcessingDAO creditDAO=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	    logger.info("Implementation class: "+creditDAO.getClass()); 	
		DynaValidatorForm SearchForCPDynaValidatorForm = (DynaValidatorForm)form;
		
		DisbursalSearchVO vo = new DisbursalSearchVO();
	
		vo.setBranchId(branchId);
	
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, SearchForCPDynaValidatorForm);
		String dealIdfromCM=request.getParameter("lbxDealNo");
		logger.info("dealIdfromCM ---------------------"+dealIdfromCM);
		String funId=CommonFunction.checkNull(session.getAttribute("functionId")).trim();
		if(dealIdfromCM !=null)
		{
			vo.setLbxDealNo(dealIdfromCM);
			request.setAttribute("noSerchforCM","noSerchforCM");
		}
			
		
		
		//String customerName=CommonFunction.checkNull(vo.getCustomerName());
		String customerName=request.getParameter("customerName");
	//	String dealNo=CommonFunction.checkNull(vo.getDealNo());
		String dealNo=request.getParameter("dealId");
		ArrayList workFlowStage = creditDAO.getworkFlowStages(dealIdfromCM,funId);
		request.setAttribute("workFlowStage", workFlowStage);
		ArrayList dealMovementList = creditDAO.getDealMovementDetail(dealIdfromCM,userId);
		logger.info("dealMovementList size .................................. "+dealMovementList.size());
		request.setAttribute("dealIdfromCM", dealIdfromCM);
		
		request.setAttribute("customerName",customerName);
		request.setAttribute("dealNo", dealNo);
		request.setAttribute("dealMovementList", dealMovementList); 
		request.setAttribute("searchScreenForDeal","searchScreenForDeal");
		request.setAttribute("true","true");
		request.setAttribute("list", dealMovementList);
		session.removeAttribute("dealIdForSandBack");
		session.setAttribute("dealIdForSandBack",dealIdfromCM);
		if((dealMovementList.size())==0)
		{
			request.setAttribute("datalist","datalist");
		}
		logger.info("detailListGrid    Size:---"+dealMovementList.size());
		return mapping.findForward("searchData");	
	
	}
	
	
	public ActionForward saveDealSendBack(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		
		logger.info("In DealSendBackDispatchAction(execute)");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		String bDate="";
		session.removeAttribute("dealIdForSandBack");
		//String dealId="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				branchId=userobj.getBranchId();
				bDate=userobj.getBusinessdate();					
		}else{
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

		
		CreditProcessingDAO creditDAO=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	    logger.info("Implementation class: "+creditDAO.getClass()); 	
		DynaValidatorForm SearchForCPDynaValidatorForm = (DynaValidatorForm)form;
		
		DealMovementVo vo=new DealMovementVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, SearchForCPDynaValidatorForm);
		String dealId=request.getParameter("lbxDealNo");
		String sms = null;
		
		String sendBackStage=request.getParameter("sendBackStage");
		String remarks=request.getParameter("remarks");
		
		vo.setUserId(userId);
		vo.setSendBackDate(bDate);
		vo.setSendBackStage(sendBackStage);
		vo.setRemarks(remarks);
		
		logger.info("dealId ---------------------"+dealId);
		logger.info("remarks ---------------------"+remarks);
		logger.info("sendBackStage ---------------------"+sendBackStage);
		String funId=CommonFunction.checkNull(session.getAttribute("functionId")).trim();
		
		if(dealId !=null)
		{
			vo.setLbxDealNo(dealId);
			request.setAttribute("noSerchforCM","noSerchforCM");
		}

		logger.info("In DealMovementDispatchAction(execute) dealid: " + dealId);
		if ((dealId != null && !dealId.equalsIgnoreCase(""))) {
			
			CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+service.getClass()); 			
			
	        String addStatus= service.saveDealSendBackDetails(vo);
	        logger.info("addStatus------>"+addStatus);
			 
		if (addStatus.equalsIgnoreCase("S")) {
				
				vo.setDecision("P");
				String query1="Select count(1) from comm_event_list_m where event_name='DEAL_SEND_BACK' and REC_STATUS='A' ";
				logger.info("query1::::"+query1.toString());
				String recStatus1 =ConnectionDAO.singleReturn(query1.toString());
				if(!recStatus1.equalsIgnoreCase("0"))
				{
						String EventName="DEAL_SEND_BACK";
						
							boolean stats=new SmsDAOImpl().getEmailDetails(dealId,bDate,EventName);
							logger.info("Email Send on event Deal_Approved::: "+stats);
						
					
				}
				else
				logger.info("Email is not active at event 'DEAL_SEND_BACK' from comm_event_list_m....");
				 if(CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase("P"))
				{
					
					 sms = "P";
				}
					
				request.setAttribute("sms", sms);
			}

			else {
				
				sms = "X";
				request.setAttribute("sms", sms);
				request.setAttribute("addStatus", addStatus);
			}
	
			 
	        
	        
	        
	        
	        
			return mapping.findForward("saveData");
		}

		else {
			request.setAttribute("back", "B");
			return mapping.findForward("saveData");
		}
		}
	
	
	public ActionForward trackDealStages(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("In DealSendBackDispatchAction(execute)");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				branchId=userobj.getBranchId();
				bDate=userobj.getBusinessdate();					
		}else{
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

		String dealId =request.getParameter("dealId");
		session.setAttribute("dealId",dealId);
		
			
		logger.info("In DealMovementBehindAction(execute) dealid: " + dealId);
		if ((dealId != null && !dealId.equalsIgnoreCase(""))) {
			
			CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
			//CreditProcessingDAO service = new CreditProcessingDAOImpl();
			
			ArrayList dealHeader = service.getDealHeader(dealId);
			request.setAttribute("dealHeader", dealHeader);
			
	
			ArrayList dealMovementList = service.getDealMovementDetail(dealId,userId);
			logger.info("dealMovementList size .................................. "+dealMovementList.size());
			request.setAttribute("dealMovementList", dealMovementList);
			 
			
			
			 
			return mapping.findForward("success");
		}

		else {
			request.setAttribute("back", "B");
			return mapping.findForward("success");
		}
	}


}
