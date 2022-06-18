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
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;
import com.cm.dao.LoanInitiationDAO;
import com.cm.vo.CommonLoanVo;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/** 
 * MyEclipse Struts
 * Creation date: 12-26-2011
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class SearchNotePadBehindInCM extends Action {
	/*
	 * Generated Methods
	 */
	private static final Logger logger = Logger.getLogger(SearchNotePadBehindInCM.class.getName());
	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {


		
		logger.info("In SearchNotePadBehindInCM(execute)");
		HttpSession session = request.getSession();
		boolean flag=false;
		String bDate="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in execute method of SearchNotePadBehindInCM action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		DynaValidatorForm CreditListDynaValidatorForm = (DynaValidatorForm) form;// TODO Auto-generated method stub
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
//		 CreditManagementDAO dao = new CreditManagementDAOImpl();
		 CommonLoanVo vo = new CommonLoanVo();
		 org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CreditListDynaValidatorForm);
		 if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
			{ 
				vo.setReportingToUserId(userId);
			   //logger.info("When user id is not selected by the user:::::"+userId);
			}
			logger.info("user Id:::::"+vo.getReportingToUserId());
			//String stage = request.getParameter("stage");
			
			//logger.info("stage: " +stage);
			logger.info("current page link .......... "+request.getParameter("d-49520-p"));
			
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
			//vo.setStage(stage);
			vo.setBranchId(branchId);
			
			LoanInitiationDAO creditDAO=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
			logger.info("Implementation class: "+creditDAO.getClass());         
			ArrayList<Object> loandetails = creditDAO.getloanListForNotePad(vo,request);
			request.setAttribute("list", loandetails);	
		
		
		return mapping.findForward("success");
	}
}