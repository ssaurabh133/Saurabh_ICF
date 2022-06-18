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
import com.caps.VO.EscalationMatrixVo;
import com.caps.dao.CollDAO;
import com.connect.DaoImplInstanceFactory;
import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class EscalationMatrixUpdateAction extends Action {
	
	private static final Logger logger = Logger.getLogger(EscalationMatrixUpdateAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In EscalationMatrixUpdateAction.......execute");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{
			logger.info("here execute method of  EscalationMatrixUpdateAction action the session is out----------------");
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

	
		LoggerMsg.info("userId::::::::::::::::::::::::::::::::::::::::"+userId);
		
		DynaValidatorForm EscalationMatrixForm= (DynaValidatorForm)form;
		EscalationMatrixVo escalationVo=new EscalationMatrixVo();
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(escalationVo, EscalationMatrixForm);
		escalationVo.setUserID(userId);
		String checkbox[] = request.getParameterValues("checkId");
		CollDAO collDAO=(CollDAO)DaoImplInstanceFactory.getDaoImplInstance(CollDAO.IDENTITY);
		logger.info("Implementation class: "+collDAO.getClass());
        String sms="";
		if(checkbox==null)
		{
			sms="F";
			request.setAttribute("sms",sms);
		}		
		else
		{
          boolean status = collDAO.updateEscalationMatrix(escalationVo,checkbox);
			
			logger.info("updateEscalationMatrix.....displaying status...."+status);
			if(status)
			{
				
					sms="M";
					request.setAttribute("sms",sms);

			}
			else
			{
				sms="E";
				ArrayList<EscalationMatrixVo> detailList=new ArrayList<EscalationMatrixVo>();
				detailList.add(escalationVo);	
				logger.info("detailList:::::::::"+detailList.size());
				request.setAttribute("list", detailList);
				request.setAttribute("sms",sms);
				
			}
			logger.info("status"+status);
		
		}
		
    		
		
        return mapping.findForward("success");
       
	}
}
