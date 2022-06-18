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

//import com.business.PoolBussiness.PoolBussinessSessionBeanRemote;
import com.connect.DaoImplInstanceFactory;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
//import com.cm.dao.PoolIdAddEditDAO;
import com.scz.dao.PoolIdAddEditDAO;
import com.scz.daoImplMYSQL.PoolIdAddEditDAOImpl;
import com.scz.vo.PoolIdMakerVO;

public class poolIdAddEditBehindAction extends Action{
private static final Logger logger = Logger.getLogger(poolIdAddEditBehindAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		logger.info(" In the poolIdAddEditBehindAction----------");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info(" in execute method of poolIdAddEditBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
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
		request.setAttribute("poolIdAddEdit", "poolIdAddEdit");
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
		PoolIdMakerVO poolIdMakerVO=new PoolIdMakerVO();
		poolIdMakerVO.setCurrentPageLink(currentPageLink);		
		//PoolBussinessSessionBeanRemote dao = (PoolBussinessSessionBeanRemote)LookUpInstanceFactory.getLookUpInstance(PoolBussinessSessionBeanRemote.REMOTE_IDENTITY, request);//added by rajani kant

		// PoolIdAddEditDAO dao=(PoolIdAddEditDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIdAddEditDAO.IDENTITY);
		PoolIdAddEditDAO dao = new PoolIdAddEditDAOImpl();
		logger.info("Implementation class: "+dao.getClass());
		
		ArrayList<PoolIdMakerVO> poolIdAddEditListGrid = dao.searchPoolIdAddEditGrid(poolIdMakerVO);
		request.setAttribute("poolIdAddEditList", poolIdAddEditListGrid);
		if((poolIdAddEditListGrid.size())==0)
		request.setAttribute("datalist","datalist");
		logger.info("poolIdAddEditListGrid    Size:---"+poolIdAddEditListGrid.size());
		return mapping.findForward("onAddEdit");
	}

}


