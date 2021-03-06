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
import org.apache.struts.validator.DynaValidatorForm;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.BuyerVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/**
 * MyEclipse Struts Creation date: 04-01-2011
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class BuyerProcessAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(BuyerProcessAction.class.getName());
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
	public ActionForward saveBuyerDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("In BuyerProcessAction(saveBuyerDetails) save buyer Details");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";		
		if(userobj!=null)
		{		userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();				
		}else{
			logger.info("here in saveBuyerDetails method of BuyerProcessAction action the session is out----------------");
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

		DynaValidatorForm buyerDynaValidatorForm = (DynaValidatorForm) form;


		
		
		BuyerVo sh = new BuyerVo();
		sh.setUserId("" + userId);
		sh.setMakerDate(bDate);
		org.apache.commons.beanutils.BeanUtils.copyProperties(sh,
				buyerDynaValidatorForm);

		String dealId = "";

		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}
		logger.info("In BuyerProcessAction(saveBuyerDetails) DealId "+ dealId);

		CreditProcessingDAO service = (CreditProcessingDAO) DaoImplInstanceFactory.getDaoImplInstance("CP");
		logger.info("In BuyerProcessAction(saveBuyerDetails) "+ service.getClass());
		sh.setDealId(dealId);
		String sms = "";

		boolean status = service.saveBuyerDetails(sh);
		if (status) {
			ArrayList<Object> showdetails = service.getBuyerDetailsAll("DB",
					"B", dealId);
			logger
					.info("In BuyerProcessAction(saveBuyerDetails) arrayList(showdetails)"
							+ showdetails);
			session.setAttribute("showdetails", showdetails);
			sms = "S";
		} else {
			sms = "E";
		}
		request.setAttribute("insertSuccess", sms);

		return mapping.findForward("success");
	}

	
		
	
	public ActionForward updateBuyerDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("In BuyerProcessAction(updateBuyerDetails) update buyer Details");
		boolean status = false;
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		 
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
				
		}else{
			logger.info("here in updateBuyerDetails method of BuyerProcessAction action the session is out----------------");
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

		DynaValidatorForm buyerDynaValidatorForm = (DynaValidatorForm) form;
		BuyerVo sh = new BuyerVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(sh,buyerDynaValidatorForm);
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass()); 
		//CreditProcessingDAO service = new CreditProcessingDAOImpl();

		String dealId = "";

		sh.setUserId(userId);
		sh.setMakerDate(bDate);

		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}
		logger.info("In BuyerProcessAction(updateBuyerDetails) DealId "
				+ dealId);
		String primaryId = CommonFunction.checkNull(request
				.getParameter("primaryId"));
		logger.info("In BuyerProcessAction(updateBuyerDetails) primaryId "
				+ primaryId);
		String sms = "";
		status = service.updateBuyerDetailsAll(sh, "DB", primaryId);
		if (status) {
			ArrayList<Object> showdetails = service.getBuyerDetailsAll("DB",
					"B", dealId);
			logger.info("In BuyerProcessAction(saveBuyerDetails) arrayList(showdetails)"
							+ showdetails);
			session.setAttribute("showdetails", showdetails);
			sms = "S";
		} else {
			sms = "E";
		}
		request.setAttribute("updateSuccess", sms);
		return mapping.findForward("success");
	}
}
