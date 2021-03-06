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

import com.business.legalTransactionBussiness.LegalTransactionBusinessSessionBeanRemote;
import com.business.repoTransactionBussiness.RepoTransactionBusinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.legal.vo.LegalCaseFileVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.repo.vo.RepoDetailVo;

public class RepoDetailbyAgencyDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(RepoDetailbyAgencyDispatchAction.class.getName());
	
	
	public ActionForward searchRepoDetailbyAgency(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		logger.info("## In searchRepoDetailbyAgency() : ");
		
		HttpSession session=request.getSession(false);
		ServletContext context = getServlet().getServletContext();
		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String branchId=userobj.getBranchId();
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("## In searchRepoDetailbyAgency() : strFlag : ==> "+strFlag);
		
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
		
		String userId=null;
		String bDate=null;
		
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		
		RepoDetailVo vo = new RepoDetailVo(); 
        
		DynaValidatorForm dyanForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, dyanForm);
		
		
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		vo.setBranchId(branchId);
	
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
		
		logger.info("## In searchRepoDetailbyAgency() : request.getParameter(d-49520-p) :==> "+request.getParameter("d-49520-p"));
		
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		}
		
		logger.info("## In searchRepoDetailbyAgency() : current page link : ==> "+currentPageLink);
		
		vo.setCurrentPageLink(currentPageLink);
		
		ArrayList list=new ArrayList();
	
		list= bp.searchRepoDetailbyAgency(vo);

	    logger.info("## In searchRepoDetailbyAgency() : list.size() : ==>> "+list.size());
	    
	    logger.info("## In searchRepoDetailbyAgency() : list.isEmpty():  ==>> "+list.isEmpty());
		
	    request.setAttribute("list", list);
	
	    return mapping.findForward("search");

	}
	
	public ActionForward openEditRepoDetailbyAgency(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		
		logger.info("## In openEditRepoDetailbyAgency() : ");
		
		ServletContext context = getServlet().getServletContext();
		RepoDetailVo vo=new RepoDetailVo(); 
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		
		//for check User session start
		String strFlag=null;
		
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("## In openEditRepoDetailbyAgency() : strFlag : ==>> "+strFlag);
		
		if(!CommonFunction.checkNull(strFlag).equalsIgnoreCase(""))
		{
			if(CommonFunction.checkNull(strFlag).equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(CommonFunction.checkNull(strFlag).equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		
		vo.setRepoId(request.getParameter("repoId"));
		
		logger.info("## In openEditRepoDetailbyAgency() : vo.getRepoId() : ==>> "+vo.getRepoId());
		
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
		
		ArrayList list = bp.getRepoDetailbyAgencyForEdit(vo);
		
		
		ArrayList checkList = bp.getCheckListDetail(vo);
		
		logger.info("## In openEditRepoDetailbyAgency() : list.size() : ==>> "+list.size());
		
		request.setAttribute("list", list);
		request.setAttribute("checkList", checkList);
		
		
	   return mapping.findForward("edit");	
	}
	
	public ActionForward saveRepoDetailsByAgency(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse resopnse) throws Exception{
		
			logger.info("## In saveRepoDetailsByAgency() : ");
			
			ServletContext context = getServlet().getServletContext();
			HttpSession session = request.getSession();
		
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");
			
			//for check User session start
			String strFlag=null;
			if(sessionId!=null)
			{
				strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			}
			
			logger.info("## In saveRepoDetailsByAgency() : strFlag : ==>> "+strFlag);
			
			if(!CommonFunction.checkNull(strFlag).equalsIgnoreCase(""))
			{
				if(CommonFunction.checkNull(strFlag).equalsIgnoreCase("sameUserSession"))
				{
					context.removeAttribute("msg");
					context.removeAttribute("msg1");
				}
				else if(CommonFunction.checkNull(strFlag).equalsIgnoreCase("BODCheck"))
				{
					context.setAttribute("msg", "B");
				}
				return mapping.findForward("logout");
			}
			
			String userId=null;
			String bDate=null;
			
			if(userobj!=null)
			{
					userId=CommonFunction.checkNull(userobj.getUserId());
					bDate=CommonFunction.checkNull(userobj.getBusinessdate());
			}
	
		DynaValidatorForm dynaForm= (DynaValidatorForm)form;
		RepoDetailVo vo = new RepoDetailVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, dynaForm);
		

		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		
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
		
        String assetCheckLists  = request.getParameter("assetCheckLists");
        String[] checkList= assetCheckLists.split("\\|");
      
     
        String checkListStatus  = request.getParameter("checkListStatus"); 			
        String[] statusList= checkListStatus.split("\\|");      
    
        
        String checkListRemarks  = request.getParameter("checkListRemarks");                    			
        String[] remarkList= checkListRemarks.split("\\|"); 
        
	
		boolean status = bp.saveRepoDetailsByAgency(vo,checkList,statusList,remarkList);
		
		
		logger.info("## In saveRepoDetailsByAgency() : status : ==>> "+status);
		
		if(status){
			
			request.setAttribute("repoId",CommonFunction.checkNull(vo.getRepoId()));	
			
			logger.info("## In saveRepoDetailsByAgency() : repoId : ==>>"+request.getAttribute("repoId"));
			
			if(CommonFunction.checkNull(vo.getSaveForwardFlag()).equalsIgnoreCase("S"))
			{
				request.setAttribute("sms","S");
			}
			else if(CommonFunction.checkNull(vo.getSaveForwardFlag()).equalsIgnoreCase("F"))
			{
				request.setAttribute("sms","F");
			}
			
		}
		else{
			
			request.setAttribute("sms","E");
		}
		logger.info("## In saveRepoDetailsByAgency() : sms : ==>>"+request.getAttribute("sms"));
		return mapping.getInputForward();
	}
				
}