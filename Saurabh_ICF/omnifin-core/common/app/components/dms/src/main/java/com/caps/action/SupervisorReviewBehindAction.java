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
import com.caps.VO.ContactRecordingSearchVO;
import com.caps.VO.ReallocationMasterVo;
import com.caps.dao.CollDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class SupervisorReviewBehindAction extends Action{

	private static final Logger logger = Logger.getLogger(SupervisorReviewBehindAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		logger.info("In SupervisorReviewBehindAction.........");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{
			logger.info("here execute method of  SupervisorReviewBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		
		DynaValidatorForm ContactRordingSearchForm= (DynaValidatorForm)form;
		ContactRecordingSearchVO conteactRecVO = new ContactRecordingSearchVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(conteactRecVO, ContactRordingSearchForm);
	
		conteactRecVO.setMakerId(userId);
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
//		String SearchFlag=CommonFunction.checkNull(request.getParameter("searchFlag"));
	//	if(SearchFlag.equalsIgnoreCase("Search")){
			
			ArrayList<ContactRecordingSearchVO> detailListGrid= new ArrayList<ContactRecordingSearchVO>();
			

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
				
				conteactRecVO.setCurrentPageLink(currentPageLink);
				CollDAO collDAO=(CollDAO)DaoImplInstanceFactory.getDaoImplInstance(CollDAO.IDENTITY);
				  logger.info("Implementation class: "+collDAO.getClass());
				detailListGrid= collDAO.supervisorReviewSearchList(conteactRecVO);
				
				logger.info("In SupervisorReviewBehindAction....list"+detailListGrid.size());
				
				
				if(detailListGrid.size()>0){
					 request.setAttribute("list", detailListGrid);
						
						logger.info("list.isEmpty(): "+detailListGrid.isEmpty());
						request.setAttribute("list",detailListGrid);
				}else{
					request.setAttribute("flagg","No");  	  
				}
	//	}
		
    
	
		ArrayList<ReallocationMasterVo> custcatList=collDAO.custcategory();
		ArrayList<ReallocationMasterVo> npastageList=collDAO.npastage();
		ArrayList<ReallocationMasterVo> productList=collDAO.product();
		request.setAttribute("customercatList",custcatList);
		request.setAttribute("npastageList",npastageList);
		request.setAttribute("productList",productList);
		    
        return mapping.findForward("success");
	}
}
