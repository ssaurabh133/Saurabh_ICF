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
import org.apache.struts.validator.DynaValidatorForm;

import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.CreditProcessingNotepadVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class PersonalDiscussionForSaveAction extends Action {
	private static final Logger logger = Logger.getLogger(PersonalDiscussionForSaveAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("In PersonalDiscussionForSaveAction(execute)  ");
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
			logger.info("here in execute method of PersonalDiscussionForSaveAction action the session is out----------------");
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
		
		String dealId = "";

		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}
		logger
				.info("In PersonalDiscussionForSaveAction dealid: "
						+ dealId);

		DynaValidatorForm PersonalDiscussionDynaValidatorForm = (DynaValidatorForm) form;


		CreditProcessingNotepadVo pdVo = new CreditProcessingNotepadVo();
		pdVo.setUserId("" + userId);
		pdVo.setMakerDate(bDate);
		pdVo.setDealId(dealId);
		org.apache.commons.beanutils.BeanUtils.copyProperties(pdVo,
				PersonalDiscussionDynaValidatorForm);
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
		//CreditProcessingDAO service = new CreditProcessingDAOImpl();
		logger.info("setPdCode" + pdVo.getPdCode());
		boolean status = service.savePersonalDiscussion(pdVo);
		String msg = "";
		if (status) {
			msg = "S";
			logger.info("00msg" + msg);
			ArrayList<CreditProcessingNotepadVo> showPDList = service
					.showPDData(pdVo);
			logger.info("showPDList    Size:---" + showPDList.size());
			request.setAttribute("showPDList", showPDList);
		} else {
			msg = "E";

			logger.info("msg1" + msg);

		}
		request.setAttribute("msg", msg);
		logger.info("status" + status);

		return mapping.findForward("onSave");

	}

}
