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
import com.cm.dao.ReportsDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class AdhocContactRecordingBehindAction extends Action{

	private static final Logger logger = Logger.getLogger(AdhocContactRecordingBehindAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		logger.info("In AdhocContactRecordingBehindAction.........");
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
			logger.info("here execute method of  AdhocContactRecordingBehindAction the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		DynaValidatorForm ContactRordingSearchForm= (DynaValidatorForm)form;
		ContactRecordingSearchVO conteactRecVO = new ContactRecordingSearchVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(conteactRecVO, ContactRordingSearchForm);
		conteactRecVO.setMakerId(userId);
		conteactRecVO.setBusinessdate(bDate); 
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
		//if(SearchFlag.equalsIgnoreCase("Search")){
			
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
				
				
				conteactRecVO.setCurrentPageLink(currentPageLink);
				 CollDAO collDAO=(CollDAO)DaoImplInstanceFactory.getDaoImplInstance(CollDAO.IDENTITY);
				  logger.info("Implementation class: "+collDAO.getClass());
				detailListGrid= collDAO.adhocContactRecordingSearchList(conteactRecVO);
				int detailListGridSize = detailListGrid.size();	 			
				if(detailListGridSize>0){
					 request.setAttribute("list", detailListGrid);
					 logger.info("list.isEmpty(): "+detailListGrid.isEmpty());
						
				}else{
					logger.info("flagg:------------------------no ");	
					request.setAttribute("flagg","No");  	  
				}
		//}
		
		ArrayList<ReallocationMasterVo> custcatList=collDAO.custcategory();
		ArrayList<ReallocationMasterVo> npastageList=collDAO.npastage();
		ArrayList<ReallocationMasterVo> productList=collDAO.product();
		request.setAttribute("customercatList",custcatList);
		request.setAttribute("npastageList",npastageList);
		request.setAttribute("productList",productList);
		ReportsDAO reportdao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
		logger.info("Implementation class: "+reportdao.getClass());
		String defaultFormate=reportdao.getDefaultFormateSOA();
		session.setAttribute("defaultFormate",defaultFormate);	
		    
        return mapping.findForward("success");
	}
}
