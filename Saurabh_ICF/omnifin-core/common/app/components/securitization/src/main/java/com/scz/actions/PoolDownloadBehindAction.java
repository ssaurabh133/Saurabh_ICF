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
import com.scz.dao.DownloadDAO;
import com.scz.daoImplMYSQL.DownloadDAOImpl;
import com.connect.DaoImplInstanceFactory;
import com.gcd.dao.CorporateDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class PoolDownloadBehindAction extends Action{
	private static final Logger logger = Logger.getLogger(PoolDownloadBehindAction.class.getName());	
	public ActionForward execute(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		logger.info("In  PoolDownloadBehindAction"); 
		HttpSession session = request.getSession();		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info(" in execute method of PoolDownloadBehindAction action the session is out----------------");
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
		CorporateDAO corporateDao=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
		logger.info("Implementation class: "+corporateDao.getClass());
		ArrayList<Object> customerCategorylist=corporateDao.getCustomerCategoryList();
		ArrayList<Object> businessSegmentList = corporateDao.getBusinessSegmentList();
		request.setAttribute("customerCategory",customerCategorylist);
		request.setAttribute("businessSegmentList",businessSegmentList);
		
		// DownloadDAO dao=(DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance(DownloadDAO.IDENTITY);
		DownloadDAO dao = new DownloadDAOImpl();
		logger.info("Implementation class: "+dao.getClass()); 
		String cutOffDateList=dao.getCutoffDate();
		logger.info("List Off Cut_Off_Date  :  "+cutOffDateList);
		request.setAttribute("cutOffDateList",cutOffDateList);
		
//		CreditProcessingDAO creditProcessing=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
//		ArrayList sector = creditProcessing.getSectorList();
//        session.setAttribute("sector",sector);
//		
       String cutOffDate = dao.getCutOffDate(userobj.getBusinessdate());
       request.setAttribute("cutOffDate",cutOffDate);
        
	   	return mapping.findForward("onSuccess");	
	
	}


}
