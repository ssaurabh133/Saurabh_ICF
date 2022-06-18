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
	public class FieldVarificationBehindAction extends DispatchAction{
		private static final Logger logger = Logger.getLogger(FieldVarificationBehindAction.class.getName());

		public ActionForward verificationInitCreditProcessing(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {
			
			logger.info("In FieldVarificationBehindAction...(verificationInitCreditProcessing)... ");
			
			HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String userId="";
			String branch="";
			if(userobj!=null)
			{
				userId=userobj.getUserId();
				branch=userobj.getBranchId();
			}else{
				logger.info("here in verificationInitCreditProcessing  method of FieldVarificationBehindAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			session.removeAttribute("lbxDealNo");
			session.removeAttribute("customerName");
			session.removeAttribute("dealNo");
			session.removeAttribute("dealDate");
			session.removeAttribute("rmName");

			boolean flag=false;
			
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
			/*Changed by asesh*/
			saveToken(request);
			FieldVerificationDAO fieldVerInit=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
	        logger.info("Implementation class: "+fieldVerInit.getClass()); 
			/*Changed by asesh*/
			FieldVerificationVo vo = new FieldVerificationVo();
			
			DynaValidatorForm CommonDealDynaValidatorForm=(DynaValidatorForm) form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CommonDealDynaValidatorForm);
			vo.setUserId(userId);
			vo.setBranchId(branch);
			session.setAttribute("userId",userId);

		
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
				
				session.removeAttribute("dealId");
				session.removeAttribute("dealHeader");
				session.removeAttribute("initiatedCount");
				session.removeAttribute("verificationList");
				
				dealdetails= fieldVerInit.initiatedVerificationDealDetail(vo);
				
			
			request.setAttribute("dealdetails", dealdetails);

			return mapping.findForward("success");
		}
		public ActionForward verificationInitCreditManagement(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {
			
			logger.info("In FieldVarificationBehindAction..(verificationInitCreditManagement).... ");
			
			HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String userId="";
			String branch="";
			if(userobj!=null)
			{
				userId=userobj.getUserId();
				branch=userobj.getBranchId();
			}else{
				logger.info("here in verificationInitCreditManagement  method of FieldVarificationBehindAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
		
			boolean flag=false;
			
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
		
			FieldVerificationDAO fieldVerInit=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
	        logger.info("Implementation class: "+fieldVerInit.getClass()); 
		
			FieldVerificationVo vo = new FieldVerificationVo();
			
			DynaValidatorForm CommonDealDynaValidatorForm=(DynaValidatorForm) form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CommonDealDynaValidatorForm);
		
			vo.setUserId(userId);
			vo.setBranchId(branch);
			session.setAttribute("userId",userId);
			     ArrayList loandetails=new ArrayList();
				
		        logger.info("current page link .......... "+request.getParameter("d-7734664-p"));
				
				int currentPageLink = 0;
				if(request.getParameter("d-7734664-p")==null || request.getParameter("d-7734664-p").equalsIgnoreCase("0"))
				{
					currentPageLink=1;
				}
				else
				{
					currentPageLink =Integer.parseInt(request.getParameter("d-7734664-p"));
				}
				
				logger.info("current page link ................ "+currentPageLink);
				
				vo.setCurrentPageLink(currentPageLink);
								
				loandetails= fieldVerInit.initiatedVerificationLoanDetail(vo);
				
			  
			   request.setAttribute("loandetails", loandetails);

			   return mapping.findForward("success");
		}
		
		public ActionForward verificationModuleCompletionSearch(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {
			
			logger.info("In FieldVarificationBehindAction...(verificationModuleCompletionSearch)... ");
			
			HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String userId="";
			String branch="";
			if(userobj!=null)
			{
				userId=userobj.getUserId();
				branch=userobj.getBranchId();
			}else{
				logger.info("here in verificationInitCreditProcessing  method of FieldVarificationBehindAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			session.removeAttribute("lbxDealNo");
			session.removeAttribute("customerName");
			session.removeAttribute("dealNo");
			session.removeAttribute("dealDate");
			session.removeAttribute("rmName");

			boolean flag=false;
			
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
			/*Changed by asesh*/
			FieldVerificationDAO fieldVerInit=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
	        logger.info("Implementation class: "+fieldVerInit.getClass()); 
			/*Changed by asesh*/
			FieldVerificationVo vo = new FieldVerificationVo();
			
			DynaValidatorForm CommonDealDynaValidatorForm=(DynaValidatorForm) form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CommonDealDynaValidatorForm);
			vo.setUserId(userId);
			vo.setBranchId(branch);
			session.setAttribute("userId",userId);

		
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
				
				session.removeAttribute("dealId");
				session.removeAttribute("dealHeader");
				session.removeAttribute("initiatedCount");
				session.removeAttribute("verificationList");
				
				dealdetails= fieldVerInit.verificationCompletionDealDetail(vo);
				
			
			request.setAttribute("dealdetails", dealdetails);

			return mapping.findForward("success");
		}
}
