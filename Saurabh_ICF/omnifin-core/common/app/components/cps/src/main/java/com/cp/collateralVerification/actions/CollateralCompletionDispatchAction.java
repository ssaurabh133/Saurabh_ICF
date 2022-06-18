package com.cp.collateralVerification.actions;

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
import com.cp.collateralVerification.dao.CollateralVerificationDAO;
import com.cp.collateralVerification.dao.CollateralVerificationDAOImpl;
import com.cp.collateralVerification.vo.CollateralCapturingVO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class CollateralCompletionDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(CollateralCompletionDispatchAction.class.getName());

	public ActionForward openCollateralPopupCompletion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside openCollateralPopupCompletion Method........Dispatch Action");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
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
		String retStr="";
		String collateralClass = CommonFunction.checkNull(request.getParameter("collClass"));
		String collateralId = CommonFunction.checkNull(request.getParameter("collId"));
		String dealId = CommonFunction.checkNull(request.getParameter("dealId"));
		String verificationId = CommonFunction.checkNull(request.getParameter("verificationId"));
		String recStatus=CommonFunction.checkNull(request.getParameter("Status"));
		CollateralVerificationDAO service = new CollateralVerificationDAOImpl();
		
		if(collateralClass.equalsIgnoreCase("MACHINE"))
		{
			ArrayList<CollateralCapturingVO> fetchCollateralDetails = service.getCollateralCapturingMachineData(dealId,verificationId,collateralId,recStatus);
			request.setAttribute("fetchCollateralCompletionDetails", fetchCollateralDetails);
			retStr= "openMachine";
		}
		else if(collateralClass.equalsIgnoreCase("PROPERTY"))
		{
			ArrayList<CollateralCapturingVO> fetchCollateralDetails = service.getCollateralCapturingPropertyData(dealId,verificationId,collateralId,recStatus);
			request.setAttribute("fetchCollateralCompletionDetails", fetchCollateralDetails);
			retStr= "openProperty";
		}
		else if(collateralClass.equalsIgnoreCase("VEHICLE"))
		{
			ArrayList<CollateralCapturingVO> fetchCollateralDetails = service.getCollateralCapturingVehicleData(dealId,verificationId,collateralId,recStatus);
			request.setAttribute("fetchCollateralCompletionDetails", fetchCollateralDetails);
			retStr= "openVehicle";
		}
	   else if(collateralClass.equalsIgnoreCase("STOCK"))
	   {
		   ArrayList<CollateralCapturingVO> fetchCollateralDetails = service.getCollateralCapturingStockData(dealId,verificationId,collateralId,recStatus);
		   request.setAttribute("fetchCollateralCompletionDetails", fetchCollateralDetails);
		   retStr= "openStock";
	   }
	   else if(collateralClass.equalsIgnoreCase("OTHERS") || collateralClass.equalsIgnoreCase("BG")
			   || collateralClass.equalsIgnoreCase("DEBTOR") || collateralClass.equalsIgnoreCase("FD")
			   || collateralClass.equalsIgnoreCase("SBLC") || collateralClass.equalsIgnoreCase("SECURITIES"))
	   {
		   ArrayList<CollateralCapturingVO> fetchCollateralDetails = service.getCollateralCapturingOtherData(dealId,verificationId,collateralId,recStatus);
		   request.setAttribute("fetchCollateralCompletionDetails", fetchCollateralDetails);
		   retStr= "openOthers";
	   }
		request.setAttribute("collateralClass",collateralClass);
		request.setAttribute("collateralId",collateralId);
		request.setAttribute("dealId",dealId);
		request.setAttribute("verificationId",verificationId);
		return mapping.findForward(retStr);
	}
	
	public ActionForward showCollateralCompletionValues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("In showCollateralCompletionValues Method........Dispatch Action");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
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
		
		session.getAttribute("collateralDetails");
		session.getAttribute("collateralHeader");
		return mapping.findForward("showCollateralCompletionValues");
	}
	
	public ActionForward openCollateralCompletionAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("In openCollateralCompletionAuthor Method........Dispatch Action");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
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
		
		session.getAttribute("collateralHeader");
		return mapping.findForward("openCollateralCompletionAuthor");
	}
	
	public ActionForward saveCollateralCompletionDecision(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("In saveCollateralCompletionDecision Method........Dispatch Action");
		HttpSession session = request.getSession();
		boolean flag=false;
		String bDate="";
		String companyId="";
		String userId="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		if(userobj!=null)
		{
			bDate=userobj.getBusinessdate();
			companyId=""+userobj.getCompanyId();
			userId=userobj.getUserId();
		}
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
		DynaValidatorForm CollateralCapturingDynaValidatorForm = (DynaValidatorForm) form;
		CollateralCapturingVO vo = new CollateralCapturingVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CollateralCapturingDynaValidatorForm);
		
		boolean status=false;
		CollateralVerificationDAO service = new CollateralVerificationDAOImpl();
		 String checkStageM=CommonFunction.stageMovement(companyId, "DC","F",vo.getDealId(), "CVC", bDate,userId);
		 logger.info("checkStageM : "+checkStageM);
		if(checkStageM.equalsIgnoreCase("S"))
		status=service.saveCollateralCompletionDecision(vo);
		else
			  request.setAttribute("checkStageM", checkStageM);
		
		if(status  && !CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase("S"))
		{
			request.setAttribute("sms","S");
		}
		else if(status  && CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase("S"))
		{
			request.setAttribute("sms","SB");
		}
		else
			request.setAttribute("sms","N");
		
		return mapping.findForward("saveCollateralCompletionDecision");
	}
}