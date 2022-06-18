/**
 * This class  is the Action class for the Holiday Master
 * @author Vishal Singh
 * @Date  31 March 2012
 * 
 */
package com.masters.actions;

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

import com.business.ejbClient.CommonMasterBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.HolidayMasterVo;

public class HolidayMaintenanceBehindAction extends Action  {
		private static final Logger logger = Logger.getLogger(HolidayMaintenanceBehindAction.class.getName());
		
		public ActionForward execute(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			HttpSession session=request.getSession(false);
			ServletContext context = getServlet().getServletContext();
			logger.info("****** Inside  HolidayMaintenanceBehindAction *************");
			
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");
			
			
			//for check User session start
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
			
			//for check User session end
			
			HolidayMasterVo vo = new HolidayMasterVo();
			DynaValidatorForm CommonDynaValidatorForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CommonDynaValidatorForm);
			
			CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			
			/*  request parameter ***********/
			String holidaySearchDate=request.getParameter("holidaySearchDate");
			String holidaySearchDes=request.getParameter("holidaySearchDes");
			String holidayTypeSearch=request.getParameter("holidayTypeSearch");
			
			/* request parameter **************/
				
			ArrayList list=new ArrayList();
			
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
			list= bp.searchHolidayData(vo);			
			
		    request.setAttribute("list", list);	
		    
		    
		    logger.info("holiday type................ "+holidayTypeSearch);
		    
		    request.setAttribute("holidaySearchDate", holidaySearchDate!=null?holidaySearchDate:"");
		    request.setAttribute("holidaySearchDes", holidaySearchDes!=null?holidaySearchDes:"");
		    request.setAttribute("holidayTypeSearch", holidayTypeSearch!=null?holidayTypeSearch:"");
	
			request.setAttribute("list",list);
			if(CommonFunction.checkNull(request.getAttribute("flag")).toString().equalsIgnoreCase("yes")){
				request.setAttribute("sms","No");
			}
		    return mapping.findForward("success");


}
}
