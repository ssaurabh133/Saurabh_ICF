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

import com.business.ejbClient.CommonMasterBussinessSessionBeanRemote;
import com.connect.DaoImplInstanceFactory;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

import com.masters.dao.MasterDAO;
import com.masters.vo.SalesExecutiveMasterVo;

public class SalesExecutiveBehindAction extends Action{
	private static final Logger logger = Logger.getLogger(SalesExecutiveBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ServletContext context = getServlet().getServletContext();
		//boolean flag=false;
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
		DynaValidatorForm salesExecutiveMasterDynaValidatorForm=(DynaValidatorForm)form;
		SalesExecutiveMasterVo vo=new SalesExecutiveMasterVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, salesExecutiveMasterDynaValidatorForm);
		

 
        logger.info("current page link .......... "+request.getParameter("d-6483387-p"));
		
		int currentPageLink = 0;
		if(request.getParameter("d-6483387-p")==null || request.getParameter("d-6483387-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-6483387-p"));
		}
		
		logger.info("current page link ................ "+request.getParameter("d-6483387-p"));
		
		vo.setCurrentPageLink(currentPageLink);
       ArrayList<SalesExecutiveMasterVo>  SalesExecutiveList = new ArrayList<SalesExecutiveMasterVo> ();
        MasterDAO dao=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
        SalesExecutiveList=dao.getSalesExecutiveList(vo);
       if(SalesExecutiveList.size()>0){
        request.setAttribute("SalesExecutiveList", SalesExecutiveList);
        
     
		
       }
     CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
   	
   	ArrayList<SalesExecutiveMasterVo> employeeTypeList = bp.getEmployeeTypeList();
	request.setAttribute("typeList", employeeTypeList);

	bp=null;
	dao=null;
        return mapping.findForward("success");

}
}
	

	