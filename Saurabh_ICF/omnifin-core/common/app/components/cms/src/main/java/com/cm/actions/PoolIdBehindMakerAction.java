package com.cm.actions;


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

//import com.business.PoolBussiness.PoolBussinessSessionBeanRemote;
import com.connect.DaoImplInstanceFactory;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cm.dao.PoolIDDAO;
import com.cm.vo.PoolIdMakerVO;

public class PoolIdBehindMakerAction extends Action{
	private static final Logger logger = Logger.getLogger(PoolIdBehindMakerAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		logger.info(" In the PoolIdBehindMakerAction----------");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerID="";
		if(userobj==null){
			logger.info(" in execute method of PoolIdBehindMakerAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
			makerID=userobj.getUserId();
		session.removeAttribute("authorPoolIdSavedList");
		session.removeAttribute("List");
		session.removeAttribute("poolID");
		session.removeAttribute("myList");
		boolean flag=false;
		
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
		//request.setAttribute("poolIdMaker", "poolIdMaker");
		logger.info("current page link .......... "+request.getParameter("d-1344872-p"));
		
		int currentPageLink = 0;
		if(request.getParameter("d-1344872-p")==null || request.getParameter("d-1344872-p").equalsIgnoreCase("0"))
		   currentPageLink=1;
		else
		   currentPageLink =Integer.parseInt(request.getParameter("d-1344872-p"));
		logger.info("current page link ................ "+request.getParameter("d-1344872-p"));
		
		PoolIdMakerVO poolIdMakerVO= new PoolIdMakerVO();
		poolIdMakerVO.setCurrentPageLink(currentPageLink);
		poolIdMakerVO.setMakerID(makerID);
		//edit by=========================================================================== amritesh
		//PoolBussinessSessionBeanRemote dao=(PoolBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PoolBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		//edit by=========================================================================== amritesh
		PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		ArrayList<PoolIdMakerVO> poolIdMakerListGrid = dao.searchPoolIdMakerGrid(poolIdMakerVO);
		request.setAttribute("poolIdMakerList", poolIdMakerListGrid);
		if((poolIdMakerListGrid.size())==0)
			request.setAttribute("datalist","datalist");
		logger.info("poolIdMakerListGrid    Size:---"+poolIdMakerListGrid.size());
		return mapping.findForward("onSuccess");
	}

}
