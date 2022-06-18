package com.cm.actions;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
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

public class ChangeRateAuthorBehindAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ChangeRateAuthorBehindAction.class.getName());
	
	public ActionForward OpenScreenToAuthor(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		HttpSession session=request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{
			logger.info("here in execute  method of ChangeRateAuthorBehindAction session is out----------------");
			return mapping.findForward("sessionOut");
		}
		
		ChangeRateVo nonEmiBasedvo = new ChangeRateVo(); 
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
		
		nonEmiBasedvo.setCurrentPageLink(currentPageLink);
		//change by sachin
	    ChangeRateDAO dao=(ChangeRateDAO)DaoImplInstanceFactory.getDaoImplInstance(ChangeRateDAO.IDENTITY);
	    logger.info("Implementation class: "+dao.getClass());
	      //end by sachin			
//		ChangeRateDAO dao= new ChangeRateDAOImpl();
		DynaValidatorForm changeRateDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(nonEmiBasedvo, changeRateDynaValidatorForm);
		session.removeAttribute("list");
		session.removeAttribute("Author");
		session.removeAttribute("LoanId");
		
	
		String lbxLoanNo=request.getParameter("lbxLoanNo");
		logger.info("lbxLoanNo :::::::::::::::---"+lbxLoanNo);
		String type="F";
		logger.info("type :::::::::::::::---"+type);
		nonEmiBasedvo.setUserId(userId);
		nonEmiBasedvo.setModifyLoan(lbxLoanNo);
	    
	    ArrayList<ChangeRateVo> searchListGrid = dao.nonEmiBasedMakerGrid(nonEmiBasedvo,type);
		

		if((searchListGrid.size())<1)
		{
			request.setAttribute("datalist","datalist");
		}
		
		request.setAttribute("OpenAuthor", "OpenAuthor");
		request.setAttribute("list", searchListGrid);
		
		
		return mapping.findForward("onSuccess");	
		
	}
	
	
	
	public ActionForward ViewChangeRateDataToAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		logger.info("In ChangeRateAuthorDispatchAction:::::::::::ViewChangeRateDataToAuthor");
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
			logger.info("here ViewChangeRateDataToAuthor the session is out-----------");
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
//        ChangeRateDAO dao= new ChangeRateDAOImpl();		
		String sms="";
		String LoanId=request.getParameter("lbxLoanNo");
		changeRateVo.setLbxLoanNo(LoanId);	
       	String makerDate=dao.getMakerDate(LoanId);
		session.setAttribute("businessDate",bDate);
		session.setAttribute("makerDate",makerDate);
		ArrayList<ChangeRateVo> searchListGrid = dao.nonEmiBasedGrid(changeRateVo);
		session.setAttribute("list", searchListGrid);
		session.setAttribute("Author", "Author");
		session.setAttribute("LoanId",LoanId);

	    return mapping.findForward("ViewChangeRate");
	
  
	}
	
}
