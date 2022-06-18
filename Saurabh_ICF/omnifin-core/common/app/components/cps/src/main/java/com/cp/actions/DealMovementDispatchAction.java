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
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class DealMovementDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(DealMovementDispatchAction.class.getName());
	
	public ActionForward getSearchData(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String branchId="";
		if(userobj!=null)
		{
			branchId=userobj.getBranchId();
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
	    logger.info("Implementation class: "+creditDAO.getClass()); 	//changed by asesh
		DynaValidatorForm SearchForCMDynaValidatorForm = (DynaValidatorForm)form;
		
		DisbursalSearchVO vo = new DisbursalSearchVO();
	
		vo.setBranchId(branchId);
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, SearchForCMDynaValidatorForm);
		String dealIdfromCM=request.getParameter("dealIdfromCM");
		logger.info("dealIdfromCM ---------------------"+dealIdfromCM);
		if(dealIdfromCM !=null)
		{
			vo.setLbxDealNo(dealIdfromCM);
			request.setAttribute("noSerchforCM","noSerchforCM");
		}
			
		
		ArrayList<DisbursalSearchVO> detailListGrid = creditDAO.searchDealMovement(vo);
		DisbursalSearchVO list = detailListGrid.get(0); 
		request.setAttribute("searchScreenForDeal","searchScreenForDeal");
		request.setAttribute("true","true");
		request.setAttribute("list", detailListGrid);
		if((detailListGrid.size())==0)
		{
			request.setAttribute("datalist","datalist");
		}
		logger.info("detailListGrid    Size:---"+detailListGrid.size());
		return mapping.findForward("searchData");	
	
	}
	
	public ActionForward trackDealStages(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("In DealMovementBehindAction(execute)");
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
