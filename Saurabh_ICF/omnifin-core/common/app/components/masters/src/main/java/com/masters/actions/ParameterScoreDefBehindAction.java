package com.masters.actions;

import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.dao.masterParameterDAO;
import com.masters.vo.ParameterScoreDefVO;

public class ParameterScoreDefBehindAction extends Action
{
	private static final Logger logger = Logger.getLogger(ParameterScoreDefBehindAction.class.getName());	
public ActionForward execute(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response)	throws Exception {
	ServletContext context = getServlet().getServletContext();
		logger.info(" IN ParameterScoreDefBehindAction execute()....");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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
		
		DynaValidatorForm frm=(DynaValidatorForm) form;		
		ParameterScoreDefVO vo= new ParameterScoreDefVO();
		BeanUtils.copyProperties(vo, frm);
		masterParameterDAO service=(masterParameterDAO)DaoImplInstanceFactory.getDaoImplInstance(masterParameterDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass()); 
		//masterParameterDAO service=new MasterParameterDAOIMPL();
		ArrayList list=service.getParameterDefOnLoad(vo);
		if(list.size()>0)
		{
			request.setAttribute("paramlist", list);
			request.setAttribute("rowsige", list.size());
		}
		return mapping.findForward("success");
}

}
