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


public class CollateralVerificationCompletionSearchDispaAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(CollateralVerificationCompletionSearchDispaAction.class.getName());
	
	public ActionForward searchCollateralVerificationCompletion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("In searchCollateralVerificationCompletion Method........Dispatch Action");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		String userId="";
		String branch="";
		
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branch=userobj.getBranchId();
		}
		
		
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
		
		CommonDealVo vo = new CommonDealVo();
		logger.info("current page link ................ "+request.getParameter("d-49520-p"));
		vo.setCurrentPageLink(currentPageLink);
		DynaValidatorForm CommonDealDynaValidatorForm = (DynaValidatorForm) form;
		vo.setBranchId(userobj.getBranchId());
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CommonDealDynaValidatorForm);
		vo.setUserId(userId);		
		CollateralVerificationDAO service = new CollateralVerificationDAOImpl();
		ArrayList<CommonDealVo> dealdetails = service.searchCollateralVerifiationCompletionData(vo);
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
			request.setAttribute("message","N");
		}
	    logger.info("In searchDealCapturing....list: "+dealdetails.size());
	    
		return mapping.findForward("searchCollateralVerificationCompletion");
	}
	
	public ActionForward openCollateralCompletion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("In openCollateralCompletion Method........Dispatch Action");
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
		String verificationId = CommonFunction.checkNull(request.getParameter("verificationId"));
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
		
		CollateralVerificationDAO service = new CollateralVerificationDAOImpl();
		ArrayList collateralHeader = service.getTradeHeader(dealId);
		ArrayList<CollateralCapturingVO> collateralCompletionDetails = service.getCollateralCapturingData(dealId,"R",userId);
		logger.info("collateralCompletionDetails size: "+collateralCompletionDetails.size());
		session.setAttribute("collateralCompletionDetails",collateralCompletionDetails);
		session.setAttribute("collateralHeader",collateralHeader);
		session.setAttribute("dealId",dealId);
		session.setAttribute("verificationId",verificationId);
		return mapping.findForward("openCollateralCompletion");
	}
}