package com.cm.actions;


import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
import com.cm.dao.ChangeRateDAO;
import com.cm.vo.ChangeRateVo;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import org.apache.log4j.Logger;
             
public class ChangeRateAuthorDispatchAction extends DispatchAction{
	
	private static final Logger logger = Logger.getLogger(ChangeRateAuthorDispatchAction.class.getName());	
	
	
	public ActionForward DisplayDataToAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		logger.info("In ChangeRateAuthorDispatchAction:::::::::::OpenChangeRateDataOfAuthor");
		HttpSession session=request.getSession(false);
		//UserObject userobj=new UserObject();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here OpenChangeRateDataOfAuthor the session is out-----------");
			return mapping.findForward("sessionOut");
		}

		session.removeAttribute("queueid");
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
		DynaValidatorForm changeRateDynaValidatorForm= (DynaValidatorForm)form;
		ChangeRateVo changeRateVo = new ChangeRateVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(changeRateVo, changeRateDynaValidatorForm);
        changeRateVo.setMakerId(userId);
        changeRateVo.setMakerDate(bDate);
      //change by sachin
		ChangeRateDAO dao=(ChangeRateDAO)DaoImplInstanceFactory.getDaoImplInstance(ChangeRateDAO.IDENTITY);
	    logger.info("Implementation class: "+dao.getClass());
	       //end by sachin	        
 //     ChangeRateDAO dao= new ChangeRateDAOImpl();		
		String sms="";
		String LoanId=(String) session.getAttribute("LoanId");
		changeRateVo.setLbxLoanNo(LoanId);		
		ArrayList<ChangeRateVo> searchListGrid = dao.nonEmiBasedGrid(changeRateVo);
		request.setAttribute("list", searchListGrid);
		request.setAttribute("Author", "Author");
	    return mapping.findForward("ViewRecord");
	
  
	}
	

	/* Open Screen For Approval/Rejection/Cancellation By Author */
	
	
	public ActionForward OpenAuthorDecisionScreen(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		logger.info(".......In.............OpenAuthorDecisionScreen()................ ");
		HttpSession session=request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{
			return mapping.findForward("sessionOut");
		}
		
		//ChangeRateVo changeRateVo = new ChangeRateVo(); 
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
		
		
		return mapping.findForward("openAuthorScreen");	
		
	}
	


	
	
	/*  Record Approved/Reject/Cancel To Author By Maker */
	
	public ActionForward SaveRateChangeData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("In ChangeRateAuthorDispatchAction:::::::::::SaveRateChangeData");
		HttpSession session=request.getSession(false);
		//UserObject userobj=new UserObject();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here SaveRateChangeData the session is out-----------");
			return mapping.findForward("sessionOut");
		}

		session.removeAttribute("queueid");
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
		DynaValidatorForm changeRateDynaValidatorForm= (DynaValidatorForm)form;
		ChangeRateVo changeRateVo = new ChangeRateVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(changeRateVo, changeRateDynaValidatorForm);
        changeRateVo.setMakerId(userId);
        changeRateVo.setMakerDate(bDate);
      //change by sachin
		ChangeRateDAO dao=(ChangeRateDAO)DaoImplInstanceFactory.getDaoImplInstance(ChangeRateDAO.IDENTITY);
	    logger.info("Implementation class: "+dao.getClass());
	       //end by sachin	       
 //     ChangeRateDAO dao= new ChangeRateDAOImpl();		
		String sms="";
		String LoanId=(String) session.getAttribute("LoanId");
		changeRateVo.setLbxLoanNo(LoanId);

		boolean status=dao.saveAuthorDecision(changeRateVo);
		
		
		   if(status){
				sms="S";
				request.setAttribute("defaultVal", changeRateVo);
				request.setAttribute("sms",sms);
			}
			else {
				sms="E";
				request.setAttribute("sms",sms);		
			}
		   session.removeAttribute("list");
		   session.removeAttribute("LoanId");
		   session.removeAttribute("Author");
		  		   
		   return mapping.findForward("openAuthorScreen");
	
	}
	
}
