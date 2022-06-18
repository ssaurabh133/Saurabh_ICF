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

import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.CreditProcessingNotepadVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class PersonalDiscussionProcessAction extends Action {
	private static final Logger logger = Logger.getLogger(PersonalDiscussionProcessAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info("In PersonalDiscussionProcessAction(execute)  ");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in execute method of PersonalDiscussionProcessAction action the session is out----------------");
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

		CreditProcessingNotepadVo pdVo = new CreditProcessingNotepadVo();
		CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 			// changed by asesh
		//CreditProcessingDAO dao = new CreditProcessingDAOImpl();
		String PD = request.getParameter("PD");
		pdVo.setPdCode(PD);
		logger.info("setPdCode" + pdVo.getPdCode());
		ArrayList<CreditProcessingNotepadVo> showPDList = dao.showPDData(pdVo);
		logger.info("showPDList    Size:---" + showPDList.size());
		request.setAttribute("showPDList", showPDList);
		return mapping.findForward("show");
	}
}