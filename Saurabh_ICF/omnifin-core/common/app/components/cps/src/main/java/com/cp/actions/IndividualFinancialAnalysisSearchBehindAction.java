package com.cp.actions;

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
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.IndividualFinancialAnalysisDAO;
import com.cp.vo.CommonDealVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class IndividualFinancialAnalysisSearchBehindAction extends Action{
private static final Logger logger = Logger.getLogger(IndividualFinancialAnalysisSearchBehindAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
	
		logger.info("In IndividualFinancialAnalysisSearchBehindAction  ");
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String branchId="";		
		if(userobj!=null)
		{
			branchId=userobj.getBranchId();	
		}else{
			logger.info("here in execute method of IndividualFinancialAnalysisSearchBehindAction action the session is out----------------");
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
		
		CommonDealVo vo = new CommonDealVo();
		DynaValidatorForm CommonDealDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CommonDealDynaValidatorForm);
		session.removeAttribute("incomeDetailsByDeal");
		int currentPageLink = 0;
		logger.info("current page link .......... "+request.getParameter("d-49520-p"));
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
		
		
	
		vo.setBranchId(branchId);
		
			vo.setRecStatus("P");
			vo.setFinancialDealStatus("P");

		IndividualFinancialAnalysisDAO dao=(IndividualFinancialAnalysisDAO)DaoImplInstanceFactory.getDaoImplInstance(IndividualFinancialAnalysisDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 	// changed by asesh
		//IndividualFinancialAnalysisDAO dao =  new IndividualFinancialAnalysisDAOImpl();
		ArrayList<CommonDealVo> financialBehindDetails = dao.individualFinancialGetDetailBehind(vo,request);
		if(financialBehindDetails.size()>0)
		{
		    request.setAttribute("list", financialBehindDetails);
		}
		else
		{
			request.setAttribute("sms","N");
		}
	    
		session.removeAttribute("pParentGroup");
		session.removeAttribute("financialRatioCodes");
		session.removeAttribute("financialDetails");
		session.removeAttribute("financialDealId");
		return mapping.findForward("individualFinancialAnalysisSearch");	
	
	}

}
