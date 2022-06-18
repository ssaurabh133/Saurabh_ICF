package com.cp.actions;

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
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class FVIUploadBehind extends Action {
	private static final Logger logger = Logger.getLogger(FVIUploadBehind.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info("Inside UnderwritingUploadBehind(execute)");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in execute method of UnderwritingUploadBehind action the session is out----------------");
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
		logger.info("------------------------------------------"+request.getParameter("dealId"));
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
		
		String fieldVerificationUniqueId = "";
		//sachin
	
		if(request.getParameter("fieldVerificationUniqueId")!=null)
		{
			fieldVerificationUniqueId=request.getParameter("fieldVerificationUniqueId");
			if(session.getAttribute("fieldVerificationUniqueId")!=null)
			{
				session.removeAttribute("fieldVerificationUniqueId");
				session.setAttribute("fieldVerificationUniqueId", fieldVerificationUniqueId);
			}
			else
			{
				session.setAttribute("fieldVerificationUniqueId", fieldVerificationUniqueId);
			}
			
			logger.info("fieldVerificationUniqueId: "+fieldVerificationUniqueId);
			
		}
		//end by sachin
		
		logger.info("Inside uploadDocList(execute) fieldVerificationUniqueId "+ fieldVerificationUniqueId);
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass()); 	//changed by asesh
		//CreditProcessingDAO service = new CreditProcessingDAOImpl();
		//sachin
		ArrayList dealHeader=new ArrayList();
		ArrayList uploadDocList=new ArrayList();
		String stage = request.getParameter("stage");
	     //dealHeader = service.getDealHeader(dealId);
	     uploadDocList = service.getUploadDocForFVC(fieldVerificationUniqueId,stage);
	    
	     request.setAttribute("stage", stage);
		
		logger.info("Size : "+uploadDocList.size() );
		if(uploadDocList.size() > 0)
		{
			request.setAttribute("uploadedDocList", uploadDocList);
		}
		////end by sachin												
		return mapping.findForward("success");
	}
}