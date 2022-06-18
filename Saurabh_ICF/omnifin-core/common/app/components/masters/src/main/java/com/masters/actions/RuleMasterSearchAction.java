/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.masters.actions;

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
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.dao.MasterDAO;
import com.masters.vo.MasterVo;

/** 
 * MyEclipse Struts
 * Creation date: 12-03-2011
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class RuleMasterSearchAction extends Action {
	private static final Logger logger = Logger.getLogger(RuleMasterSearchAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
MasterVo vo = new MasterVo();
		
		HttpSession session = request.getSession();
		boolean flag=false;
		ServletContext context = getServlet().getServletContext();
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
		
		MasterDAO masterDAO=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+masterDAO.getClass());
		//MasterDAO masterDAO = new MasterDAOImpl();
        DynaValidatorForm CommonDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CommonDynaValidatorForm);
		ArrayList list=new ArrayList();
		
		logger.info("In rule code action))))))))))))))))))) "+vo.getRatioCode());
		logger.info("In rule Name action))))))))))))))))))) "+vo.getRatioName());
		
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
		
		vo.setCurrentPageLink(currentPageLink);
		list= masterDAO.ruleMasterSearch(vo);

	    logger.info("In RuleMasterSearchAction....list"+list.size());
		
	    request.setAttribute("list", list);
	    request.setAttribute("search","search");
		
//		logger.info("list.isEmpty(): "+list.isEmpty());
//		request.setAttribute("list",list);
//		if(CommonFunction.checkNull(request.getAttribute("flag")).toString().equalsIgnoreCase("yes"))
//		{
//			request.setAttribute("sms","No");
//		}
		
        return mapping.findForward("success");
	}
}