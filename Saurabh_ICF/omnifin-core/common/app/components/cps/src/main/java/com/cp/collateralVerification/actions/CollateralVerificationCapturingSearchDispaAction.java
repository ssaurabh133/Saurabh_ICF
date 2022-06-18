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
import com.cp.vo.CommonDealVo;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class CollateralVerificationCapturingSearchDispaAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(CollateralVerificationCapturingSearchDispaAction.class.getName());
	
	public ActionForward searchCollateralVerificationCapturing(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		logger.info("In searchCollateralVerificationCapturing Method........Dispatch Action");
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
		
		CommonDealVo vo = new CommonDealVo();
		DynaValidatorForm CommonDealDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CommonDealDynaValidatorForm);
		vo.setBranchId(userobj.getBranchId());
		
		String userId=vo.getReportingToUserId();
		if(userId.trim().length()==0)
			if(userobj!=null)
				userId=userobj.getUserId();
		vo.setUserId(userId);
		CollateralVerificationDAO service = new CollateralVerificationDAOImpl();
		ArrayList<CommonDealVo> dealdetails = service.searchCollateralVerifiationCapturingData(vo);
		int dealDetailListSize = dealdetails.size();
		if(dealDetailListSize>0)
		{
			ArrayList<CommonDealVo> searchParams = new ArrayList<CommonDealVo>();
			searchParams.add(vo);
			request.setAttribute("searchParams", searchParams);
			request.setAttribute("list", dealdetails);
		}
		else
		{
			ArrayList<CommonDealVo> searchParams = new ArrayList<CommonDealVo>();
			searchParams.add(vo);
			request.setAttribute("searchParams", searchParams);
			request.setAttribute("list", dealdetails);
			request.setAttribute("message","N");
		}
	    logger.info("In searchDealCapturing....list: "+dealdetails.size());
	    
		return mapping.findForward("searchCollateralVerificationCapturing");
	}
	
	
	
	
	public ActionForward openCollateralCapturing(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("In openCollateralCapturing Method........Dispatch Action");
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
		session.removeAttribute("listUnderWriter");
		session.removeAttribute("collateralCompletionDetails");
		session.removeAttribute("collateralHeader");
		session.removeAttribute("dealId");
		session.removeAttribute("verificationId");
		
		String dealId=CommonFunction.checkNull(request.getParameter("dealId"));
		String functionId="";
		
		String userId = userobj.getUserId();
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
				return mapping.findForward("searchCollateralVerificationCapturing");
			}
		}

		request.removeAttribute("searchParams");
		CollateralVerificationDAO service = new CollateralVerificationDAOImpl();
		ArrayList collateralHeader = service.getTradeHeader(dealId);
		ArrayList<CollateralCapturingVO> collateralDetails = service.getCollateralCapturingData(dealId,"F",userId);
		request.setAttribute("collateralDetails",collateralDetails);
		request.setAttribute("collateralHeader",collateralHeader);
		return mapping.findForward("openCollateralCapturing");
	}
	
	
	//.......Sanjog.......
	
	public ActionForward initialcollateralVerificationCapturing(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("In initialcollateralVerificationCapturing Method........Dispatch Action");
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
		String dealId=CommonFunction.checkNull(request.getParameter("dealId"));
		String functionId="";
		String userId ="";
		String branchId ="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}
		
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		
		
		//ServletContext context=getServlet().getServletContext();
//		if(context!=null && !userId.equalsIgnoreCase(""))
//		{
//			flag = LockRecordCheck.lockCheck(userId,functionId,dealId,context);
//			logger.info("Flag ........................................ "+flag);
//			if(!flag)
//			{
//				logger.info("Record is Locked");			
//				request.setAttribute("message", "Locked");
//				request.setAttribute("recordId", dealId);
//				return mapping.findForward("searchCollateralVerificationCapturing");
//			}
//		}
		CommonDealVo vo = new CommonDealVo();	
		DynaValidatorForm CommonDealDynaValidatorForm = (DynaValidatorForm) form;
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CommonDealDynaValidatorForm);
		vo.setUserId(userId);
		vo.setBranchId(branchId);
		CollateralVerificationDAO service = new CollateralVerificationDAOImpl();		
		ArrayList<CommonDealVo> dealdetails = service.initialCollateralVerifiationCapturingData(vo);
		request.setAttribute("list", dealdetails);
		logger.info("In searchDealCapturing....list: "+dealdetails.size());
	    
		return mapping.findForward("success");
	}


}