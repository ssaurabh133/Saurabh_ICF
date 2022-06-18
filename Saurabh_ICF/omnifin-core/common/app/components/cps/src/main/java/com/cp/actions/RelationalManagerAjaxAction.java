/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
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
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.CodeDescVo;
import com.cp.vo.RelationalManagerVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class RelationalManagerAjaxAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(RelationalManagerAjaxAction.class.getName());
	public ActionForward displayRelationalManager(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("In RelationalManagerAjaxAction(displayRelationalManager) ");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in displayRelationalManager method of RelationalManagerAjaxAction action the session is out----------------");
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
		String branchId = request.getParameter("branch");
		CreditProcessingDAO creditDao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+creditDao.getClass()); 			// changed by asesh
		//CreditProcessingDAO creditDao = new CreditProcessingDAOImpl();

		ArrayList<RelationalManagerVo> relationalManagerList = new ArrayList<RelationalManagerVo>();
		relationalManagerList = creditDao.getRelationalManagerList(branchId);

		request.setAttribute("relationalManagerList", relationalManagerList);

		return mapping.findForward("displaySuccess");
	}

	public ActionForward getProductDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception

	{
		logger.info("In CreditProcessiongLeadEntryAction getProductDetail ");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in getProductDetail method of RelationalManagerAjaxAction action the session is out----------------");
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

		String productType = request.getParameter("productType");
		logger.info("productType: " + productType);
		CreditProcessingDAO creditDao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+creditDao.getClass()); 			// changed by asesh
		ArrayList<CodeDescVo> productList = creditDao.getProductList(productType);
		session.setAttribute("productList", productList);

		return mapping.findForward("productsuccess");
	}

	public ActionForward getSchemeDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception

	{
		logger.info("In CreditProcessiongLeadEntryAction getProductDetail ");

		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in getSchemeDetail method of RelationalManagerAjaxAction action the session is out----------------");
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
		String product = request.getParameter("product");
		logger.info("product: " + product);
		CreditProcessingDAO creditDao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+creditDao.getClass()); 			// changed by asesh
		ArrayList<CodeDescVo> schemeList = creditDao.getSchemeList(product);
		session.setAttribute("schemeList", schemeList);
		//ArrayList cycle = creditDao.getCycleDateList();
		//session.setAttribute("cycle", cycle);
		return mapping.findForward("schemesuccess");
	}

	public ActionForward getBaseRateDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception

	{
		logger.info("In CreditProcessiongLeadEntryAction getBaseRateDetail ");

		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String bdate="";
		if(userobj==null){
			logger.info("here in getBaseRateDetail method of RelationalManagerAjaxAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}else{
			bdate=userobj.getBusinessdate();
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

		String baseRateType = request.getParameter("baseRateType");
		logger.info("baseRateType: " + baseRateType);
		CreditProcessingDAO creditDao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+creditDao.getClass()); 			// changed by asesh
		String baseRateList = creditDao.getBaseRate(baseRateType,bdate);
		request.setAttribute("baseRateList", baseRateList);

		return mapping.findForward("baseRateSuccess");
	}

	public ActionForward getDefaultLoanDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception

	{
		logger.info("In CreditProcessiongLeadEntryAction getDefaultLoanDetail ");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String bdate="";
		if(userobj==null){
			logger.info("here in getDefaultLoanDetail method of RelationalManagerAjaxAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}else{
			bdate=userobj.getBusinessdate();
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

		String scheme = request.getParameter("scheme");
		logger.info("scheme: " + scheme);
		CreditProcessingDAO creditDao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+creditDao.getClass()); 			// changed by asesh
		ArrayList loanShemeList = creditDao.getLoanDetailScheme(scheme,bdate);
		ArrayList cycleDueDay = creditDao.getCycleDueDay(scheme,"DC");
		request.setAttribute("cycleDueDay", cycleDueDay);
		request.setAttribute("loanShemeList", loanShemeList);

		return mapping.findForward("loanShemeSuccess");
	}
//Prashant	
	
	public ActionForward getLtvFromMakeModel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception

	{
		logger.info("In  getLtvFromMakeModel ");

		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in getLtvFromMakeModel method of RelationalManagerAjaxAction action the session is out----------------");
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
		
		String branchId=userobj.getBranchId();
		String assetNature = request.getParameter("assetNature");
		String txtProductCat = request.getParameter("txtProductCat");
		String makeModelId = request.getParameter("makeModelId");
		String year = request.getParameter("vehicleYearOfManufact");
		logger.info("txtProductCat: " + txtProductCat+" assetNature: "+assetNature+"makeModelId: "+makeModelId+"   year: "+year);
		CreditProcessingDAO creditDao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+creditDao.getClass()); 			// changed by asesh
		ArrayList ltvList = creditDao.getLtvFromMakeMod(assetNature,txtProductCat,makeModelId,branchId,year);
		request.setAttribute("ltvList", ltvList);

		return mapping.findForward("ltvSuccess");
	}
	
	//Start By Anil
	public ActionForward getDefaultLoanDetailForEmiCal(ActionMapping mapping,ActionForm form, 
			HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		logger.info("In CreditProcessiongLeadEntryAction getDefaultLoanDetailForEmiCal ");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String bdate="";
		if(userobj==null){
			logger.info("here in getDefaultLoanDetailForEmiCal method of RelationalManagerAjaxAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}else{
			bdate=userobj.getBusinessdate();
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

		String scheme = request.getParameter("scheme");
		logger.info("Scheme For Emi Calculator::::::::::::::::::::::" + scheme);
		//CreditProcessingDAO creditDao = new CreditProcessingDAOImpl();
        CreditProcessingDAO creditDao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+creditDao.getClass()); 			// changed by anil
		
		ArrayList loanShemeListForEmiCal = creditDao.getLoanDetailSchemeForEmiCal(scheme,bdate);
		request.setAttribute("loanShemeListForEmiCal", loanShemeListForEmiCal);

		return mapping.findForward("loanSchemeForEmiCalcSuccess");
	
}
	//End By Anil
	
}