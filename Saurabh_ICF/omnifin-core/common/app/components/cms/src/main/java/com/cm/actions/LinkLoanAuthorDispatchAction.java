package com.cm.actions;

import java.io.IOException;
import java.sql.SQLException;
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

import com.cm.dao.LinkLoanDAO;
import com.cm.vo.LinkLoanVo;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class LinkLoanAuthorDispatchAction extends DispatchAction {
	private static final Logger logger=Logger.getLogger(LinkLoanAuthorDispatchAction.class.getName());
	
	public ActionForward linkLoanAuthorShow(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response)throws Exception{
		logger.info("in openMakerAuthor method of  LinkLoanAuthorDispatchAction::::::::::::");
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			businessDate = userobj.getBusinessdate();
		}else{
			logger.info("here in linkLoanAuthorShow method of LinkLoanAuthorDispatchAction action the session is out----------------");
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
		
		 
		   LinkLoanVo linkLoanVo=new LinkLoanVo();

			DynaValidatorForm linkLoanDynaValidatorForm = (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(linkLoanVo, linkLoanDynaValidatorForm);
			
		
			LinkLoanDAO dao=(LinkLoanDAO)DaoImplInstanceFactory.getDaoImplInstance(LinkLoanDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());

			
			ArrayList<LinkLoanVo> linkLoanList=new ArrayList<LinkLoanVo>();
			
			String loanId="";
			if(CommonFunction.checkNull(request.getParameter("lbxLoanNoHID")).equalsIgnoreCase(""))
			    loanId=(String)session.getAttribute("loanIdToLink");
			else
				loanId=request.getParameter("lbxLoanNoHID");
			
			String makerName="";
			
			if(CommonFunction.checkNull(request.getParameter("makerName")).equalsIgnoreCase(""))
				makerName=(String)session.getAttribute("makerNameMain");
			else
				makerName=request.getParameter("makerName");
			
			
			logger.info("value of loan to approve::::::"+makerName);
			
			linkLoanVo.setLbxLoanNoHID(loanId);
			linkLoanVo.setCheckBoxDisable("Y");
			
			String searchFlag="N";
			linkLoanList=dao.getAfterSaveData(linkLoanVo,searchFlag);
			searchFlag=null;
			
			ArrayList<LinkLoanVo> getList=new ArrayList<LinkLoanVo>();		
			getList=dao.getEditData(loanId);		
			
			session.setAttribute("loanIdToLink", loanId);
			session.setAttribute("getList", getList);		
			session.setAttribute("list", linkLoanList);
			session.setAttribute("makerNameMain", makerName);

		return mapping.findForward("linkLoanAuthorShow");
	}
public ActionForward openLinkLoanAuthor(ActionMapping mapping,ActionForm form,
		HttpServletRequest request,HttpServletResponse response)throws IOException,SQLException{
	logger.info("in openLinkLoanAuthor method of  LinkLoanAuthorDispatchAction::::::::::::");
	
	HttpSession session = request.getSession();
	UserObject userobj=(UserObject)session.getAttribute("userobject");
	String userId ="";
	String businessDate ="";
	if(userobj!=null)
	{
		userId=userobj.getUserId();
		businessDate = userobj.getBusinessdate();
	}else{
		logger.info("here in openLinkLoanAuthor method of LinkLoanAuthorDispatchAction action the session is out----------------");
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

	
	return mapping.findForward("openLinkLoanAuthor");
	}
public ActionForward showLinkLoanAuthorDetail(ActionMapping mapping,ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
	logger.info("in showLinkLoanAuthorDetail method of  LinkLoanAuthorDispatchAction::::::::::::");
	
	HttpSession session = request.getSession();
	UserObject userobj=(UserObject)session.getAttribute("userobject");
	String userId ="";
	String businessDate ="";
	if(userobj!=null)
	{
		userId=userobj.getUserId();
		businessDate = userobj.getBusinessdate();
	}else{
		logger.info("here in showLinkLoanAuthorDetail method of LinkLoanAuthorDispatchAction action the session is out----------------");
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


	return mapping.findForward("showLinkLoanAuthorDetail");
	}
public ActionForward saveLinkLoanAuthor(ActionMapping mapping,ActionForm form,
					HttpServletRequest request,HttpServletResponse response)throws Exception {
	logger.info("in saveLinkLoanAuthor method of  LinkLoanAuthorDispatchAction::::::::::::");
	
	HttpSession session = request.getSession();
	UserObject userobj=(UserObject)session.getAttribute("userobject");
	String userId ="";
	String businessDate ="";
	if(userobj!=null)
	{
		userId=userobj.getUserId();
		businessDate = userobj.getBusinessdate();
	}else{
		logger.info("here in saveLinkLoanAuthor method of LinkLoanAuthorDispatchAction action the session is out----------------");
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
	
	    LinkLoanVo linkLoanVo=new LinkLoanVo();

		DynaValidatorForm linkLoanDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(linkLoanVo, linkLoanDynaValidatorForm);		
		
		LinkLoanDAO dao=(LinkLoanDAO)DaoImplInstanceFactory.getDaoImplInstance(LinkLoanDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());

		String loanId=(String)session.getAttribute("loanIdToLink");
		linkLoanVo.setLbxLoanNoHID(loanId);
		linkLoanVo.setUserId(userId);
		linkLoanVo.setMakerdDate(businessDate);
		String maker=(String)session.getAttribute("makerNameMain");
		linkLoanVo.setMakerName(maker);
		
		String status=dao.saveLinkLoanAuthor(linkLoanVo);
		if(CommonFunction.checkNull(status).equalsIgnoreCase(""))
			request.setAttribute("sms", "S");
		else{
			request.setAttribute("sms", "E");
			request.setAttribute("message",status);
		}
	
	return mapping.findForward("openLinkLoanAuthor");
}
}
