/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.repo.actions;

import java.util.ArrayList;
import java.util.Properties;

import javax.naming.InitialContext;
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

import com.business.legalMasterBussiness.LegalMasterBusinessSessionBeanRemote;
import com.business.legalTransactionBussiness.LegalTransactionBusinessSessionBeanRemote;
import com.business.repoMasterBussiness.RepoMasterBusinessSessionBeanRemote;
import com.business.repoTransactionBussiness.RepoTransactionBusinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.legal.vo.LegalNoticeInitiationVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.repo.vo.RepoConfirmationtVo;

public class RepoConfirmationDispatchAction extends DispatchAction {
	
	private static final Logger logger = Logger.getLogger(RepoConfirmationDispatchAction.class.getName());
	
	//private static final Object repoConfirmationDynaValidatorForm = null;
	
	public ActionForward searchRepoConfirmation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		boolean flag=false;
		String bDate="";
		String companyId="";
		String status ="";
		String userId="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String branchId=userobj.getBranchId();
		if(userobj!=null)
		{
			bDate=userobj.getBusinessdate();
			companyId=""+userobj.getCompanyId();
			userId=userobj.getUserId();
		}
		else
		{
			logger.info("here in execute method of RepoConfirmationDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
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
		
		RepoConfirmationtVo vo = new RepoConfirmationtVo(); //change
        
		DynaValidatorForm dyanForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, dyanForm);


		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		vo.setBranchId(branchId);
	
		
		RepoTransactionBusinessSessionBeanRemote bp = (RepoTransactionBusinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(RepoTransactionBusinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
        ArrayList list=new ArrayList();
        logger.info("current page link .......... "+request.getParameter("d-49520-p"));
		
		int currentPageLink = 0;
		if(CommonFunction.checkNull(request.getParameter("d-49520-p")).equalsIgnoreCase("") || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		}
		
		logger.info("current page link ................ "+request.getParameter("d-49520-p"));
		logger.info("currentPageLink----------"+currentPageLink);
		if(!(CommonFunction.checkNull(currentPageLink).equalsIgnoreCase("")))
			vo.setCurrentPageLink(currentPageLink);
		else{
			
			currentPageLink=1;
		vo.setCurrentPageLink(currentPageLink);
		}
		//change from here
	
		list= bp.searchRepoConfirmation(vo);
		logger.info("In RepoConfirmationDispatchAction....list"+list.size());
		
	    request.setAttribute("list", list);
	  logger.info("list.isEmpty(): "+list.isEmpty());
		if(CommonFunction.checkNull(request.getAttribute("flag")).toString().equalsIgnoreCase("yes")){
			request.setAttribute("sms","No");
		}
	    return mapping.findForward("search");

	}
	
	
	
	public ActionForward openEditRepoConfirmation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception { 
		ServletContext context = getServlet().getServletContext();
		RepoConfirmationtVo vo = new RepoConfirmationtVo(); 
						logger.info("In openEditRepoConfirmation");
						
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
						
						String repoId =  CommonFunction.checkNull(request.getParameter("repoId"));
						
						vo.setRepoId(repoId);
						logger.info("In openEditRepoConfirmation---status---- by repoId-"+repoId);  
						logger.info("In openEditRepoConfirmation---status---- by getpara by vo-"+vo.getRepoId());
						
						/*
						Properties props = new Properties();
						props.load(request.getSession().getServletContext().getResourceAsStream("/WEB-INF/jndi.properties"));   
				        InitialContext ic = new InitialContext(props);
				        String applName=props.getProperty("enterprise.application.name");
				        String remoteBean = props.getProperty("repo.transaction.business.session.bean.remote");
				        logger.info("remoteBean : "+remoteBean);
				        logger.info("applName : "+applName);
				        String remoteBeanName=CommonFunction.checkNull(applName)+CommonFunction.checkNull(remoteBean);
				        logger.info("remoteBeanName: "+remoteBeanName);
				        RepoTransactionBusinessSessionBeanRemote bp=(RepoTransactionBusinessSessionBeanRemote)ic.lookup(remoteBeanName);
						*/
						
						RepoTransactionBusinessSessionBeanRemote bp = (RepoTransactionBusinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(RepoTransactionBusinessSessionBeanRemote.REMOTE_IDENTITY, request);
				    	
						ArrayList list = bp.openEditRepoConfirmation(vo);
						logger.info("In openEditRepoConfirmation RepoConfirmationtVo list"+list.size());
						
						ArrayList checkListForAgency = bp.getCheckListOfAgency(vo);
						
						ArrayList checkListForStrockyard = bp.getCheckListOfStockyard(vo);
					
						vo=(RepoConfirmationtVo) list.get(0);
						session.setAttribute("repoConfirmationList", list);
						session.setAttribute("repoConfirmationLoanId", vo.getLoanId());
						session.setAttribute("repoConfirmationRepoId", repoId.toString());
						session.setAttribute("repoConfirmationCheckListForAgency", checkListForAgency);
						session.setAttribute("repoConfirmationCheckListForStrockyard", checkListForStrockyard);
						
						logger.info("## saveRepoConfirmationDetail(): repoConfirmationRepoId : ------ "+session.getAttribute("repoConfirmationRepoId"));
						logger.info("## saveRepoConfirmationDetail(): repoConfirmationLoanId : ------ "+session.getAttribute("repoConfirmationLoanId"));
						logger.info("## saveRepoConfirmationDetail(): repoId : ------ "+repoId);
						
						
						
						logger.info("In openEditRepoConfirmation RepoConfirmationtVo checkListForAgency"+checkListForAgency.size());
						logger.info("In openEditRepoConfirmation RepoConfirmationtVo checkListForStrockyard"+checkListForStrockyard.size());
					
						
					   return mapping.findForward("openEdit");	
		}
	
	public ActionForward repoAuthorScreen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info(" in noticeCheckerScreen method of ReceiptAuthorProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String sessionId = session.getAttribute("sessionID").toString();	
		
		String strFlag="";	
		
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		ServletContext context = getServlet().getServletContext();
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


		return mapping.findForward("authorScreen");
	}
	
	public ActionForward saveRepoConfirmationDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse resopnse) throws Exception{
			ServletContext context = getServlet().getServletContext();
			//HttpSession session=request.getSession(false);
			HttpSession session = request.getSession();
			
			
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
			
			String userId="";
			String bDate="";
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bDate=userobj.getBusinessdate();
			}
	
		DynaValidatorForm dynaForm= (DynaValidatorForm)form;
		RepoConfirmationtVo vo = new RepoConfirmationtVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, dynaForm);
		

		vo.setAutherId(userId);
		vo.setAutherDate(bDate);
		vo.setRepoId(CommonFunction.checkNull(session.getAttribute("repoConfirmationRepoId")));
		
		logger.info("## saveRepoConfirmationDetail(): repoId : ------ "+session.getAttribute("repoConfirmationRepoId"));
		
		logger.info("## saveRepoConfirmationDetail(): repoId : ------ "+vo.getRepoId());
		
		/*
		Properties props = new Properties();
		props.load(request.getSession().getServletContext().getResourceAsStream("/WEB-INF/jndi.properties"));   
        InitialContext ic = new InitialContext(props);
        String applName=props.getProperty("enterprise.application.name");
        String remoteBean = props.getProperty("repo.transaction.business.session.bean.remote");
        logger.info("remoteBean : "+remoteBean);
        logger.info("applName : "+applName);
        String remoteBeanName=CommonFunction.checkNull(applName)+CommonFunction.checkNull(remoteBean);
        logger.info("remoteBeanName: "+remoteBeanName);
        RepoTransactionBusinessSessionBeanRemote bp=(RepoTransactionBusinessSessionBeanRemote)ic.lookup(remoteBeanName);
        */
		
		RepoTransactionBusinessSessionBeanRemote bp = (RepoTransactionBusinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(RepoTransactionBusinessSessionBeanRemote.REMOTE_IDENTITY, request);
        
    
	
		String sms="";
		
		logger.info("before calling db method");
		
		boolean status = bp.saveRepoConfirmationDetail(vo);
		
		logger.info("before calling db method");
		
		logger.info("Inside Legal Master Action.....displaying status...."+status);
		
		if(status){
			
			if(CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase("Y"))
				sms="Y";
			else if(CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase("N"))
				sms="N";
			else if(CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase("X"))
				sms="X";
			
			request.setAttribute("sms",sms);
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
		
		}
		
		
		
		logger.info("status"+status);
		
		//return mapping.findForward("success");
		return mapping.getInputForward();
	}
	
}