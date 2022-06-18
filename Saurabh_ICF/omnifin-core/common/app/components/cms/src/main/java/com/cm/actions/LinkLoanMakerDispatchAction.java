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

import com.cm.dao.LinkLoanDAO;
import com.cm.vo.LinkLoanVo;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class LinkLoanMakerDispatchAction extends DispatchAction {
	private static final Logger logger=Logger.getLogger(LinkLoanMakerDispatchAction.class.getName());
	
	public ActionForward openLinkLoanAdd(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.info("in openLinkLoanAdd method of  LinkLoanMakerDispatchAction:::::::");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			businessDate = userobj.getBusinessdate();
		}else{
			logger.info("here in openLinkLoanAdd method of LinkLoanMakerDispatchAction action the session is out----------------");
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
		
		request.setAttribute("list", "");
		
		return mapping.findForward("openLinkAdd");
	}
	public ActionForward getLinkLoanDetail(ActionMapping mapping,ActionForm form,
										HttpServletRequest request,HttpServletResponse response)throws Exception {
		logger.info("in getLinkLoanDetail method of LinkLoanMakerDispatchAction:::::::::::::::::::: ");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			businessDate = userobj.getBusinessdate();
		}else{
			logger.info("here in getLinkLoanDetail method of LinkLoanMakerDispatchAction action the session is out----------------");
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
				
		String loanId=CommonFunction.checkNull(request.getParameter("lbxLoanNoHID"));
		String loanAddFlag=CommonFunction.checkNull(request.getParameter("flagAdd"));
		String loanIdToadd="";
		if(CommonFunction.checkNull(loanAddFlag).equalsIgnoreCase("Y"))
			loanIdToadd=CommonFunction.checkNull(request.getParameter("lbxLoanNoHIDAdd"));
		logger.info(" In getLinkLoanDetail the loan id is:::::::---"+loanId);
		LinkLoanDAO dao=(LinkLoanDAO)DaoImplInstanceFactory.getDaoImplInstance(LinkLoanDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		
		ArrayList<LinkLoanVo> linkLoanList= dao.getLinkLoanDetails(loanId,loanIdToadd);
		
		logger.info("value of list is ::::::::::::");
		if(linkLoanList.size() > 0){
		logger.info("value of count ............... "+linkLoanList.get(0).getTotalRecordSize());
		request.setAttribute("listSize",linkLoanList.get(0).getTotalRecordSize());
		}
		request.setAttribute("list", linkLoanList);
		
		ArrayList<LinkLoanVo> getList=new ArrayList<LinkLoanVo>();
		getList.add(linkLoanVo);
		request.setAttribute("getList", getList);
		logger.info(" saurabh singh:::::::---"+loanId);
		return mapping.findForward("openLinkAdd");
	}
  public ActionForward saveLinkLoanDetail(ActionMapping mapping,ActionForm form,
		  HttpServletRequest request,HttpServletResponse response)throws Exception{
	  logger.info("In saveLinkLoanDetail method of LinkLoanMakerDispatchAction:::::::::::::::::::: ");
	  	HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			businessDate = userobj.getBusinessdate();
		}else{
			logger.info("here in saveLinkLoanDetail method of LinkLoanMakerDispatchAction action the session is out----------------");
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
		
		
		
		String loanAddFlag=CommonFunction.checkNull(request.getParameter("flagAdd"));
		
		String loanIdToadd="";
		if(CommonFunction.checkNull(loanAddFlag).equalsIgnoreCase("Y"))
			loanIdToadd=CommonFunction.checkNull(request.getParameter("lbxLoanNoHIDAdd"));
		
		
		
		String allLoanIds=request.getParameter("allLoanIds");		
		String[] loanIds=allLoanIds.split("\\|");		
		logger.info("value of all loan ids:::::::::::::"+loanIds);
		String primaryLoanNumberHid=request.getParameter("primaryLoanNumberHid");
		String[] primaryLoanIds=primaryLoanNumberHid.split("\\|");
		logger.info("value of all primaryLoanIds ids:::::::::::::"+primaryLoanIds.length);
		
		linkLoanVo.setLoanIds(loanIds);
		linkLoanVo.setPrimaryLoanIds(primaryLoanIds);
		
		
		
		linkLoanVo.setUserId(userId);
		linkLoanVo.setMakerdDate(businessDate);
		
		LinkLoanDAO dao=(LinkLoanDAO)DaoImplInstanceFactory.getDaoImplInstance(LinkLoanDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		
		boolean status= dao.saveLinkLoanDetail(linkLoanVo);
		
		if(status)
			request.setAttribute("sms", "S");
		else
			request.setAttribute("sms", "E");
		
		ArrayList<LinkLoanVo> linkLoanList=new ArrayList<LinkLoanVo>();
		//loanId=linkLoanVo.getLoanNoSave();
		String searchFlag="N";
		linkLoanList=dao.getAfterSaveData(linkLoanVo,searchFlag);
		searchFlag=null;
		if(linkLoanList.size() > 0)
			request.setAttribute("listSize",linkLoanList.get(0).getTotalRecordSize());
		else
			request.setAttribute("listSize",0);
		request.setAttribute("list", linkLoanList);
		
		ArrayList<LinkLoanVo> getList=new ArrayList<LinkLoanVo>();
		getList.add(linkLoanVo);
		request.setAttribute("getList", getList);
		request.setAttribute("edit", "edit");
		
	  return mapping.findForward("openLinkAdd");
  }
  public ActionForward forwardLinkLoanDetail(ActionMapping mapping,ActionForm form,
		  		HttpServletRequest request,HttpServletResponse response)throws Exception{
	  logger.info("in forwardLinkLoanDetail of  LinkLoanMakerDispatchAction::::::::::::::::::::::");
	  HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			businessDate = userobj.getBusinessdate();
		}else{
			logger.info("here in forwardLinkLoanDetail method of LinkLoanMakerDispatchAction action the session is out----------------");
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

		String loanId=CommonFunction.checkNull(request.getParameter("lbxLoanNoHID"));
		logger.info("loan id is:::::::::::::::::::::::::::::::::::::"+loanId);
		
		LinkLoanDAO dao=(LinkLoanDAO)DaoImplInstanceFactory.getDaoImplInstance(LinkLoanDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		
		boolean status=dao.forwardLinkLoanData(loanId);
		
		logger.info("status is::::::::::::::::::::"+status);
		
		if(status)
			request.setAttribute("sms","F");
		else
			request.setAttribute("sms","N");
		
	  return mapping.findForward("searchPage");
  }
  public ActionForward editLinkLoanDeatil(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{

		logger.info("in openLinkLoanAdd method of  LinkLoanMakerDispatchAction:::::::");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			businessDate = userobj.getBusinessdate();
		}else{
			logger.info("here in editLinkLoanDeatil method of LinkLoanMakerDispatchAction action the session is out----------------");
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
		
		logger.info("value of main loan edit mode::::"+linkLoanVo.getLbxLoanNoHID());
		logger.info("value of  loan ::::"+linkLoanVo.getLbxLoanNoHIDAdd());
		
		ArrayList<LinkLoanVo> linkLoanList=new ArrayList<LinkLoanVo>();
		String loanId=request.getParameter("lbxLoanNoHID");
		
		String searchFlag="N";
		linkLoanList=dao.getAfterSaveData(linkLoanVo,searchFlag);
		searchFlag=null;
		if(linkLoanList.size() > 0)
			request.setAttribute("listSize",linkLoanList.get(0).getTotalRecordSize());
		else
			request.setAttribute("listSize",0);
		
		ArrayList<LinkLoanVo> getList=new ArrayList<LinkLoanVo>();		
		getList=dao.getEditData(loanId);
		
		request.setAttribute("getList", getList);		
		request.setAttribute("list", linkLoanList);
		request.setAttribute("edit", "edit");

		return mapping.findForward("openLinkAdd");
	
  }
  public ActionForward linkNewLoan(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{

		logger.info("in linkNewLoan method of  LinkLoanMakerDispatchAction:::::::");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			businessDate = userobj.getBusinessdate();
		}else{
			logger.info("here in linkNewLoan method of LinkLoanMakerDispatchAction action the session is out----------------");
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
		
		
		logger.info("value of main loan ::::"+linkLoanVo.getLbxLoanNoHID());
		logger.info("value of  loan ::::"+linkLoanVo.getLbxLoanNoHIDAdd());
		
		ArrayList<LinkLoanVo> linkLoanList=new ArrayList<LinkLoanVo>();
		String loanId=request.getParameter("lbxLoanNoHID");
		String searchFlag="Y";
		linkLoanList=dao.getAfterSaveData(linkLoanVo,searchFlag);
		searchFlag=null;
		if(linkLoanList.size() > 0)
			request.setAttribute("listSize",linkLoanList.get(0).getTotalRecordSize());
		else
			request.setAttribute("listSize",0);
		
		ArrayList<LinkLoanVo> getList=new ArrayList<LinkLoanVo>();		
		getList=dao.getEditData(loanId);
		
		logger.info("message is:::+++++++ ::::"+ linkLoanVo.getMessage());
		
		request.setAttribute("message", linkLoanVo.getMessage());
		request.setAttribute("getList", getList);		
		request.setAttribute("list", linkLoanList);
		request.setAttribute("edit", "edit");
		
		return mapping.findForward("openLinkAdd");
			}
}
