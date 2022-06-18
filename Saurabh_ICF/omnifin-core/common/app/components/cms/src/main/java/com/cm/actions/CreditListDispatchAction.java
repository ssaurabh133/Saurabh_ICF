/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
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
import com.cm.dao.LoanInitiationDAO;
import com.cm.vo.CommonLoanVo;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/** 
 * MyEclipse Struts
 * Creation date: 03-16-2011
 * 
 * XDoclet definition:
 * @struts.action input="/JSP/CMJSP/CreditList.jsp" validate="true"
 */
public class CreditListDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(CreditListDispatchAction.class.getName());
	/*
	 * Generated Methods
	 */

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward searchLoanDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		
		HttpSession session =  request.getSession();
		//boolean flag=false;
		String userId=null;
		String branchId=null;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		if(userobj!=null){
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in searchLoanDetail method of CreditListDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		DynaValidatorForm CreditListDynaValidatorForm = (DynaValidatorForm) form;// TODO Auto-generated method stub
		logger.info("In CreditListDynaValidatorForm, searchLoanDetail");
		
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!"".equalsIgnoreCase(strFlag))
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
		CommonLoanVo vo = new CommonLoanVo();
		String stage = request.getParameter("stage");
		
		logger.info("stage: " +stage);
		//logger.info("current page link .......... "+request.getParameter("d-49520-p"));
		
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		}
		
		//logger.info("current page link ................ "+request.getParameter("d-49520-p"));
		
		vo.setCurrentPageLink(currentPageLink);
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CreditListDynaValidatorForm);
		
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
			vo.setReportingToUserId(userId);
		
		//logger.info("user Id:::::"+vo.getReportingToUserId());
		
		vo.setStage(stage);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		
		//CreditManagementDAO creditDAO = new CreditManagementDAOImpl();
		LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		ArrayList<Object> loandetails = dao.fetchLoanDetail(vo,request);
		request.setAttribute("list", loandetails);	

		if(stage.equalsIgnoreCase("P"))
		{
			vo=null;
			dao=null;
			stage=null;
			userId=null;
			branchId=null;
			strFlag=null;
			form.reset(mapping, request);
			return mapping.findForward("loanInitSMaker");
		}
		else
		{
			vo=null;
			dao=null;
			stage=null;
			userId=null;
			branchId=null;
			strFlag=null;
			form.reset(mapping, request);
			return mapping.findForward("loanInitSAuthor");
		}
		
		
	}

	public ActionForward searchLinkForMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		
		HttpSession session =  request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId=null;
		String branchId=null;
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in searchLinkForMaker method of CreditListDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		DynaValidatorForm CreditListDynaValidatorForm = (DynaValidatorForm) form;// TODO Auto-generated method stub
		logger.info("In CreditListDynaValidatorForm, searchLoanDetail");
		
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!"".equalsIgnoreCase(strFlag))
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
		CommonLoanVo vo = new CommonLoanVo();
		String stage = "P";
		
		logger.info("stage: " +stage);
		//logger.info("current page link .......... "+request.getParameter("d-49520-p"));
		
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
		
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CreditListDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
			vo.setReportingToUserId(userId);
		logger.info("user Id:::::"+vo.getReportingToUserId());
		
		vo.setCurrentPageLink(currentPageLink);
		vo.setStage(stage);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		
		//CreditManagementDAO creditDAO = new CreditManagementDAOImpl();
		LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		ArrayList<Object> loandetails = dao.fetchLoanDetail(vo,request);
		//code added by neeraj tripathi
		CreditProcessingDAO creditProcessing=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 						// changed by asesh
		//CreditProcessingDAO creditProcessing = new CreditProcessingDAOImpl();
		String ediFlag = creditProcessing.getEditableFlag();
		if(ediFlag.trim().equalsIgnoreCase("Y"))
			session.setAttribute("editable","editable");
		//neeraj tripathi's space end
		request.setAttribute("list", loandetails);	
		
		if(stage.equalsIgnoreCase("P"))
		{
			vo=null;
			dao=null;
			stage=null;
			userId=null;
			branchId=null;
			strFlag=null;
			creditProcessing=null;
			ediFlag=null;
			form.reset(mapping, request);
			return mapping.findForward("loanInitSMaker");
		}
		else
		{
			vo=null;
			dao=null;
			stage=null;
			userId=null;
			branchId=null;
			strFlag=null;
			creditProcessing=null;
			ediFlag=null;
			form.reset(mapping, request);
			return mapping.findForward("loanInitSAuthor");
		}

	}
	
	 public ActionForward searchLinkForAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		
		
		HttpSession session =  request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId=null;
		String branchId=null;
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in searchLinkForAuthor method of CreditListDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		DynaValidatorForm CreditListDynaValidatorForm = (DynaValidatorForm) form;// TODO Auto-generated method stub
		logger.info("In CreditListDynaValidatorForm, searchLoanDetail");
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!"".equalsIgnoreCase(strFlag))
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

		CommonLoanVo vo = new CommonLoanVo();
		String stage = "F";
		
		//logger.info("stage: " +stage);
		//logger.info("current page link .......... "+request.getParameter("d-49520-p"));
		
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
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CreditListDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setStage(stage);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		//CreditManagementDAO creditDAO = new CreditManagementDAOImpl();
		LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		ArrayList<Object> loandetails = dao.fetchLoanDetail(vo,request);
		request.setAttribute("list", loandetails);	
		session.setAttribute("userIdLoan", userId);
		session.setAttribute("branchIdLoan", branchId);
				
		if(stage.equalsIgnoreCase("P"))
		{
			vo=null;
			dao=null;
			stage=null;
			strFlag=null;
			form.reset(mapping, request);
			return mapping.findForward("loanInitSMaker");
		}
		else
		{
			vo=null;
			dao=null;
			stage=null;
			strFlag=null;
			form.reset(mapping, request);
			return mapping.findForward("loanInitSAuthor");
		}

	
	}
	
	public ActionForward searchLinkForDeviationAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		
		
		HttpSession session =  request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId=null;
		String branchId=null;
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in searchLinkForDeviationAuthor method of CreditListDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		DynaValidatorForm CreditListDynaValidatorForm = (DynaValidatorForm) form;// TODO Auto-generated method stub
		logger.info("In CreditListDynaValidatorForm, searchLinkForDeviationAuthor");
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag=null;	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		//logger.info("strFlag .............. "+strFlag);
		if(!"".equalsIgnoreCase(strFlag))
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

		CommonLoanVo vo = new CommonLoanVo();
		String stage = "F";
		
		logger.info("stage: " +stage);
		//logger.info("current page link .......... "+request.getParameter("d-49520-p"));
		
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		}
		
		//logger.info("current page link ................ "+request.getParameter("d-49520-p"));
		
		vo.setCurrentPageLink(currentPageLink);
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CreditListDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		//logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setStage(stage);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		//CreditManagementDAO creditDAO = new CreditManagementDAOImpl();
		LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		
	    	ArrayList<Object> loandetails = dao.fetchLoanDetailForDeviation(vo);
	    	request.setAttribute("list", loandetails);	
    	dao=null;
	    vo=null;
	    stage=null;	
	    userId=null;
		branchId=null;
		strFlag=null;	
		form.reset(mapping, request);				
		return mapping.findForward("loanDevitationAuthor");
	
	}
}	


