package com.scz.actions;

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

import com.scz.dao.PoolIDDAO;
import com.scz.daoImplMYSQL.PoolIDDAOImpl;
import com.scz.vo.PoolIdMakerVO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class PoolViewerBehindAction extends Action{

	private static final Logger logger = Logger.getLogger(PoolViewerBehindAction.class.getName());
	 public ActionForward execute(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {
		 logger.info("In PoolViewerBehindAction   ");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String makerID="";
			if(userobj==null){
				logger.info(" in execute method of PoolIdBehindAuthorAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			else 
				makerID=userobj.getUserId();
			Object sessionId = session.getAttribute("sessionID");
			session.removeAttribute("authorPoolIdSavedList");
			session.removeAttribute("List");
			session.removeAttribute("poolID");
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
			
			logger.info("current page link .........pool id author. "+request.getParameter("d-1344872-p"));		
			int currentPageLink = 0;
			if(request.getParameter("d-1344872-p")==null || request.getParameter("d-1344872-p").equalsIgnoreCase("0"))
				currentPageLink=1;
			else
				currentPageLink =Integer.parseInt(request.getParameter("d-1344872-p"));
			
			PoolIdMakerVO poolIdMakerVO =new PoolIdMakerVO();
			poolIdMakerVO.setCurrentPageLink(currentPageLink);
			// PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
			PoolIDDAO dao = new PoolIDDAOImpl();
		// 	logger.info("Implementation class: "+dao.getClass());
		    poolIdMakerVO.setMakerID(makerID);	
		    poolIdMakerVO.setTemp("viewer");
	    	ArrayList<PoolIdMakerVO> poolIdAuthorListGrid = dao.searchPoolIdAuthorGrid(poolIdMakerVO);	
	    	
	    	session.setAttribute("viewer", "viewer");
	    	request.setAttribute("authordetailList", poolIdAuthorListGrid);
	    	
	    	if((poolIdAuthorListGrid.size())==0)
	    	{
	    		request.setAttribute("datalist","datalist");
	    	}
	    	logger.info("poolIdAuthorListGrid    Size:---"+poolIdAuthorListGrid.size());
			return mapping.findForward("onSuccess");
		}
}
