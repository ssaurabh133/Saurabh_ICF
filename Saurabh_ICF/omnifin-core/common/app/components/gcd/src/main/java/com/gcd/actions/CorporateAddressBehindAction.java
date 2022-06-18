package com.gcd.actions;

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

import com.connect.DaoImplInstanceFactory;
import com.gcd.dao.CorporateDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class CorporateAddressBehindAction extends Action {
	private static final Logger logger = Logger.getLogger(CorporateAddressBehindAction.class.getName());	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
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
			//code added by neeraj
			String source="NE";
			String functionId=(String)session.getAttribute("functionId");
			int funid=Integer.parseInt(functionId);		
			if(funid==4000122 || funid==4000123)
				source="ED";
			//neeraj space end
			
		 logger.info("In displayCorporateAddress");
		 logger.info("In displayCorporateAddress"+request.getParameter("status"));
		 logger.info("In displayCorporateAddress"+session.getAttribute("operation"));
		 String statusCase="";
		 String updateFlag="";
		 String updateInMaker="";
		 if(session.getAttribute("updateFlag")!=null)
		 {
			 logger.info("updateFlag........................"+session.getAttribute("updateFlag"));
			 
			 updateFlag = session.getAttribute("updateFlag").toString();
		 }
		 if((session!=null && session.getAttribute("corporateId")!=null))
		 {
			 
			 String code = session.getAttribute("corporateId").toString();
			 CorporateDAO detail=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
			 logger.info("Implementation class: "+detail.getClass());
		 ArrayList<Object>addrList = detail.getAddressList();
		 ArrayList<Object>countryList = detail.getCountryList();
		 if(session.getAttribute("operation")!=null||session.getAttribute("approve")!=null)
		 {
			 	
			    Object pageStatus = session.getAttribute("approve");
			    
				ArrayList<Object> addressInfo = detail.getAdressAll(code,pageStatus,updateFlag,source);
				logger.info("addressInfo: "+addressInfo.size());
				session.setAttribute("addressInfo", addressInfo);
		 }
		 session.setAttribute("addrList", addrList);
		 session.setAttribute("countryList", countryList);
		 session.removeAttribute("detailcountryList");
		 session.removeAttribute("detailcityList");
		 session.removeAttribute("detailregionList");
		 if(session.getAttribute("statusCase")!=null)
			{
				statusCase = session.getAttribute("statusCase").toString();
				logger.info("statusCase........................................."+statusCase);
			}
		
		 if(session.getAttribute("updateInMaker")!=null)
			{
				updateInMaker = session.getAttribute("updateInMaker").toString();
			}
		 String pageStatuss="";
		 if(session.getAttribute("pageStatuss")!=null)
		 {
			 pageStatuss = session.getAttribute("pageStatuss").toString();
		 }
		 String cuaStatus="";
		  if(session.getAttribute("CUA")!=null)
			 {
			  cuaStatus = session.getAttribute("CUA").toString();
			 }
		 ArrayList<Object> briefAddr = detail.getAddressDetails(code,statusCase,updateInMaker,pageStatuss,updateFlag,cuaStatus,source);
		 session.setAttribute("briefAddr", briefAddr);
		 return mapping.findForward("displaysuccess");
		 }
		 else
		 {
			 request.setAttribute("back", "ok");
			 return mapping.findForward("backsuccess");
		 }

	}

}
