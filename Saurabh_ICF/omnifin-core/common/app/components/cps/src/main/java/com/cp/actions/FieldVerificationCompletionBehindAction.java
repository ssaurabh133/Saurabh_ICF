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

import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.dao.FieldVerificationDAO;
import com.cp.vo.FieldVerificationVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/**
 * MyEclipse Struts Creation date: 05-13-2011
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class  FieldVerificationCompletionBehindAction  extends DispatchAction{
	private static final Logger logger = Logger.getLogger(FieldVerificationCompletionBehindAction.class.getName());
	/*
	 * Generated Methods
	 */

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward verificationCompletionCreditProcessing(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId = "";
		String branchId = "";
		if (userobj != null) {
			userId = userobj.getUserId();
			branchId = userobj.getBranchId();
		}else{
			logger.info("here in verificationCompletionCreditProcessing method of FieldVerificationCompletionBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		CreditProcessingDAO creditProcessing=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+creditProcessing.getClass()); 			// changed by asesh	
		//CreditProcessingDAO creditProcessing = new CreditProcessingDAOImpl();
		logger.info("In FieldVerificationCompletionBehindAction.verificationCompletionCreditProcessing..... ");
	
		Object sessionId = session.getAttribute("sessionID");
//		String userId=userobj.getUserName();
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
		DynaValidatorForm FieldVarificationDynaValidatorForm = (DynaValidatorForm) form;// TODO
		// Auto-generated
		// method
		// stub
		/*Changed by asesh*/
		FieldVerificationDAO fieldDao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
        logger.info("Implementation class: "+fieldDao.getClass());
		/*Changed by asesh*/
		FieldVerificationVo vo = new FieldVerificationVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,FieldVarificationDynaValidatorForm);
		
	
		vo.setUserId(userId);
		vo.setBranchId(branchId);		
		
		 ArrayList dealdetails=new ArrayList();
			
	        logger.info("current page link .......... "+request.getParameter("d-7061068-p"));
			
			int currentPageLink = 0;
			if(request.getParameter("d-7061068-p")==null || request.getParameter("d-7061068-p").equalsIgnoreCase("0"))
			{
				currentPageLink=1;
			}
			else
			{
				currentPageLink =Integer.parseInt(request.getParameter("d-7061068-p"));
			}
			
			logger.info("current page link ................ "+currentPageLink);
			
			vo.setCurrentPageLink(currentPageLink);
//			vo.setUserId(userId);
			session.removeAttribute("dealId");
			session.removeAttribute("dealHeader");
			String functionId="";
			String ID="";
			if(session.getAttribute("functionId")!=null)
			{
				functionId=session.getAttribute("functionId").toString();
			}
			logger.info("functionId: "+functionId);
			vo.setFunctionId(functionId);
		ArrayList defaultlist=fieldDao.getDefaultCompletionData(vo);
		request.setAttribute("list",defaultlist);
		return mapping.findForward("success");
	}
	
	public ActionForward verificationCompletionCreditManagement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId = "";
		String branchId = "";
		if (userobj != null) {
			userId = userobj.getUserId();
			branchId = userobj.getBranchId();
		}else{
			logger.info("here in verificationCompletionCreditManagement method of FieldVerificationCompletionBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		CreditProcessingDAO creditProcessing=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+creditProcessing.getClass()); 			// changed by asesh	
		//CreditProcessingDAO creditProcessing = new CreditProcessingDAOImpl();
		logger.info("In FieldVerificationCompletionBehindAction.verificationCompletionCreditManagement..... ");
	
		Object sessionId = session.getAttribute("sessionID");
//		String userId=userobj.getUserName();
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
		DynaValidatorForm FieldVarificationDynaValidatorForm = (DynaValidatorForm) form;// TODO
		// Auto-generated
		// method
		// stub
		/*Changed by asesh*/
		FieldVerificationDAO fieldDao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
        logger.info("Implementation class: "+fieldDao.getClass());
		/*Changed by asesh*/
		FieldVerificationVo vo = new FieldVerificationVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,FieldVarificationDynaValidatorForm);
		
	
		vo.setUserId(userId);
		vo.setBranchId(branchId);		
		session.setAttribute("userId", userId);// For lov
		session.setAttribute("branchId", branchId);//For lov
		 ArrayList dealdetails=new ArrayList();
			
	        logger.info("current page link .......... "+request.getParameter("d-7061068-p"));
			
			int currentPageLink = 0;
			if(request.getParameter("d-7061068-p")==null || request.getParameter("d-7061068-p").equalsIgnoreCase("0"))
			{
				currentPageLink=1;
			}
			else
			{
				currentPageLink =Integer.parseInt(request.getParameter("d-7061068-p"));
			}
			
			logger.info("current page link ................ "+currentPageLink);
			
			vo.setCurrentPageLink(currentPageLink);
//			vo.setUserId(userId);
			session.removeAttribute("loanId");
			session.removeAttribute("loanHeader");
			String functionId="";
			String ID="";
			if(session.getAttribute("functionId")!=null)
			{
				functionId=session.getAttribute("functionId").toString();
			}
			logger.info("functionId: "+functionId);
			vo.setFunctionId(functionId);
		ArrayList defaultlist=fieldDao.getDefaultCompletionDataAtCM(vo);
		request.setAttribute("list",defaultlist);
		return mapping.findForward("success");
	}
}

