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
import org.apache.struts.validator.DynaValidatorForm;
import com.cm.dao.ChangeRateDAO;
import com.cm.vo.ChangeRateVo;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ChangeRateMakerBehindAction extends Action{
	private static final Logger logger = Logger.getLogger(ChangeRateMakerBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		HttpSession session=request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{
			logger.info("here in execute  method of ChangeRateMakerBehindAction session is out----------------");
			return mapping.findForward("sessionOut");
		}
		
		ChangeRateVo changeRateVo = new ChangeRateVo(); 
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
		
		changeRateVo.setCurrentPageLink(currentPageLink);
		//change by sachin
		ChangeRateDAO dao=(ChangeRateDAO)DaoImplInstanceFactory.getDaoImplInstance(ChangeRateDAO.IDENTITY);
	    logger.info("Implementation class: "+dao.getClass());
	    //end by sachin			
//		ChangeRateDAO dao= new ChangeRateDAOImpl();
		DynaValidatorForm changeRateDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(changeRateVo, changeRateDynaValidatorForm);
		String lbxLoanNo=request.getParameter("lbxLoanNo");
		String type="P";
		changeRateVo.setUserId(userId);
		changeRateVo.setModifyLoan(lbxLoanNo);
	    
	    ArrayList<ChangeRateVo> searchListGrid = dao.nonEmiBasedMakerGrid(changeRateVo,type);
		

		if((searchListGrid.size())<1)
		{
			request.setAttribute("datalist","datalist");
		}
		request.setAttribute("list", searchListGrid);
		
		return mapping.findForward("onSuccess");	
		
	}
}
