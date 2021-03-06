/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.gcd.actions;

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
import com.gcd.dao.CorporateDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/** 
 * MyEclipse Struts
 * Creation date: 02-11-2011
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class CustomerAddressAjaxAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(CustomerAddressAjaxAction.class.getName());
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
	public ActionForward getCountry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		    HttpSession session = request.getSession();
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
		
		DynaValidatorForm CustomerAddressValidatorForm= (DynaValidatorForm)form;
		 logger.info("In CustomerAddressAction in getCountry"+CustomerAddressValidatorForm.getString("country"));
		 CorporateDAO detail=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
			logger.info("Implementation class: "+detail.getClass());
		 
		 ArrayList<Object>detailcountryList = detail.getCountryDetail(CustomerAddressValidatorForm.getString("country"));
		 logger.info("detailaddrList"+detailcountryList.size());
		
		 session.setAttribute("detailcountryList", detailcountryList);
		return mapping.findForward("countryDetail");
	}
	public ActionForward getCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		DynaValidatorForm CustomerAddressValidatorForm= (DynaValidatorForm)form;
		
		    HttpSession session = request.getSession();
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
			
		 logger.info("In CustomerAddressAction in getCity"+CustomerAddressValidatorForm.getString("country"));
		 logger.info("In CustomerAddressAction in getCity"+CustomerAddressValidatorForm.getString("state"));
		 CorporateDAO detail=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
			logger.info("Implementation class: "+detail.getClass());
		 ArrayList<Object>detailcityList = detail.getCityDetail(CustomerAddressValidatorForm.getString("country"),CustomerAddressValidatorForm.getString("state"));
		// ArrayList<DeatilOfCustomerAddress>detailregionList = detail.getRegionDetail(CustomerAddressValidatorForm.getString("state"));
		 logger.info("detailcityList"+detailcityList.size());
		// logger.info("detailregionList"+detailregionList.size());
		 //session.setAttribute("detailregionList", detailregionList);
		 session.setAttribute("detailcityList", detailcityList);
		
		return mapping.findForward("cityDetail");
	}
}