//Author Name :Kanika Maheshwari-->
//Date of Creation : -->
//Purpose  : Behind Action for Reallocation SEARCH-->
//Documentation : -->


package com.caps.action;

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
import com.caps.VO.ReallocationGridVo;
import com.caps.VO.ReallocationMasterVo;
import com.caps.dao.CollDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ReallocationSearchBehindAction extends Action {
	
	private static final Logger logger = Logger.getLogger(ReallocationSearchBehindAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			
			HttpServletRequest request, HttpServletResponse response) throws Exception {
				logger.info("ReallocationSearchBehindAction........");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId="";
				if(userobj!=null)
				{
					userId=userobj.getUserId();
				}else{
					logger.info("here execute method of  ReallocationSearchBehindAction action the session is out----------------");
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
				DynaValidatorForm ReallocationDynaValidatorForm= (DynaValidatorForm)form;
				ReallocationMasterVo reallocVo=new ReallocationMasterVo();
				ArrayList<ReallocationGridVo> detailList=new ArrayList<ReallocationGridVo>();
				org.apache.commons.beanutils.BeanUtils.copyProperties(reallocVo, ReallocationDynaValidatorForm);

				
				 logger.info("current page link .......before... "+request.getParameter("d-49520-p"));
					
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
					
					reallocVo.setCurrentPageLink(currentPageLink);
					reallocVo.setMakerId(userId);
					CollDAO collDAO=(CollDAO)DaoImplInstanceFactory.getDaoImplInstance(CollDAO.IDENTITY);
					  logger.info("Implementation class: "+collDAO.getClass());
					detailList= collDAO.reallocationGrid(reallocVo);
					request.setAttribute("lastval", reallocVo);
					
					logger.info("In ReallocationSearchBehindAction....list"+detailList.size());
					
					
					if(detailList.size()>0){
						 request.setAttribute("list", detailList);
							
							logger.info("list.isEmpty(): "+detailList.isEmpty());
					
					}else{
						
							logger.info("In ReallocationSearchBehindAction....");
						
							request.setAttribute("flagg","No");  	  
						
					}
					ArrayList<ReallocationMasterVo> custcatList=collDAO.custcategory();
					ArrayList<ReallocationMasterVo> npastageList=collDAO.npastage();
					ArrayList<ReallocationMasterVo> productList=collDAO.product();
					request.setAttribute("customercatList",custcatList);
					request.setAttribute("npastageList",npastageList);
					request.setAttribute("productList",productList);
					
		        return mapping.findForward("success");
		
	}

}
