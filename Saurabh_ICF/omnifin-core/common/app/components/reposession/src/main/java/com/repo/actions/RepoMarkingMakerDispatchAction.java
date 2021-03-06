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
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.repo.vo.RepoMarkingMakerVo;

public class RepoMarkingMakerDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(RepoMarkingMakerDispatchAction.class.getName());
	
	public ActionForward openAddRepoMarkingMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				logger.info(" in openAddRepoMarkingMaker()");
				ServletContext context = getServlet().getServletContext();
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
				
				request.setAttribute("save", "save");
			    return mapping.findForward("openAdd");	
			}
	public ActionForward searchRepoMarkingMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession(false);
		ServletContext context = getServlet().getServletContext();
		logger.info(" ## In execute() : .........");
		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String branchId=userobj.getBranchId();
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
		
		RepoMarkingMakerVo vo = new RepoMarkingMakerVo(); //change
        
		DynaValidatorForm dyanForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, dyanForm);
	
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
		
		
        ArrayList list=new ArrayList();
		
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
		vo.setBranch(branchId);
		//vo1.setCurrentPageLink(currentPageLink);
		
		
		
		//change from here
		
		list= bp.searchRepoMarkingMaker(vo);

	    logger.info("In searchStockyardMasterData....list"+list.size());
		
	    request.setAttribute("list", list);
		
		logger.info("list.isEmpty(): "+list.isEmpty());
		request.setAttribute("list",list);
		if(CommonFunction.checkNull(request.getAttribute("flag")).toString().equalsIgnoreCase("yes")){
			request.setAttribute("sms","No");
		}
	    return mapping.findForward("search");


	}
	
	public ActionForward openRepoMarkingMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception { 
		ServletContext context = getServlet().getServletContext();
		RepoMarkingMakerVo vo=new RepoMarkingMakerVo(); 
						logger.info("In openRepoMArkingMaker");
						
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
						
						vo.setLoanId(request.getParameter("loanId"));
						logger.info("In openRepoMarkingMaker---status---- by getpara-"+request.getParameter("loanId"));  
						logger.info("In openRepoMarkingMaker---status---- by getpara by vo-"+vo.getLoanId());
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
						
						ArrayList list = bp.openEditRepoMarkingMaker(vo);
						logger.info("In openRepoMarkingMaker RepoMarkingMakerVo list"+list.size());
						request.setAttribute("list", list);
					
						
						vo=(RepoMarkingMakerVo) list.get(0);
						RepoMarkingMakerVo docVo=new RepoMarkingMakerVo();
						docVo=(RepoMarkingMakerVo) list.get(0);
						logger.info("In openEditCaseTypeMaster---status---- by getpara by vo-"+docVo.getRecStatus());
						
					
						request.setAttribute("status", vo.getRecStatus());
						request.setAttribute("editVal", "editVal");
					   return mapping.findForward("edit");	
		}
	
	public ActionForward saveRepoMarkingMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse resopnse) throws Exception{
			ServletContext context = getServlet().getServletContext();
			//HttpSession session=request.getSession(false);
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
			
			String userId="";
			String bDate="";
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bDate=userobj.getBusinessdate();
			}
	
		DynaValidatorForm dynaForm= (DynaValidatorForm)form;
		RepoMarkingMakerVo vo = new RepoMarkingMakerVo();
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
	
		String sms="";
	
		boolean status = bp.insertRepoMarkingMaker(vo);
		logger.info("Inside Country Master Action.....displaying status...."+status);
		if(status){
			sms="S";
			request.setAttribute("sms",sms);
			request.setAttribute("loanId", CommonFunction.checkNull(vo.getLoanId()));
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
		}
		logger.info("status"+status);
		return mapping.getInputForward();
	}
		
	public ActionForward updateRepoMarkingMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("In updateCase.......");
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
		
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		
		RepoMarkingMakerVo vo=new RepoMarkingMakerVo(); 
		DynaValidatorForm dynaForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, dynaForm);	

		logger.info("In updateCountryDetails---------");  
		
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
		
        String updateForwardFlag = CommonFunction.checkNull(request.getParameter("updateForwardFlag"));
        
        logger.info("## In updateLegalNoticeInitiationMaker() : updateForwardFlag :==>> "+updateForwardFlag);
        
        vo.setUpdateForwardFlag(updateForwardFlag);
        
        logger.info("## In updateLegalNoticeInitiationMaker() : vo.getUpdateForwardFlag() :==>> "+vo.getUpdateForwardFlag());
		
        boolean status=bp.updateRepoMarkingMaker(vo);
        
        logger.info("## In updateLegalNoticeInitiationMaker() : status : ==>> "+status);
        logger.info("## In updateLegalNoticeInitiationMaker() : vo.getLoanNo() : ==>> "+vo.getLoanNo());
        
        String sms="";
        if(status){
        	
        	if(vo.getUpdateForwardFlag().equalsIgnoreCase("U"))
        	{
			sms="S";
			request.setAttribute("sms",sms);
			request.setAttribute("editVal", "editVal");
			request.setAttribute("loanId", CommonFunction.checkNull(vo.getLoanId()));
        	}
        	else if(vo.getUpdateForwardFlag().equalsIgnoreCase("F"))
        	{
        	sms="F";
			request.setAttribute("sms",sms);
			request.setAttribute("editVal", "editVal");
        	}
		
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
			ArrayList<RepoMarkingMakerVo> list =new ArrayList<RepoMarkingMakerVo>();
			list.add(vo);
			logger.info("In update case detail list"+ list.size());
			
			request.setAttribute("editVal", "editVal");
			request.setAttribute("list", list);
			request.setAttribute("status", vo.getRecStatus());
			
		}
       // vo.setCountryId(request.getParameter("CountryId"));
		logger.info("In update Case Detail---status-----"+vo.getRecStatus());

		        
        return mapping.getInputForward();
      
		
	}
	
	public ActionForward getDetailofLoanForRepoMarkingMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception { 
		ServletContext context = getServlet().getServletContext();
		RepoMarkingMakerVo vo=new RepoMarkingMakerVo(); 
						logger.info("In getDetailofLoanForRepoMarkingMaker");
						
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
						
						vo.setLoanId(request.getParameter("loanId"));
						logger.info("In openRepoMarkingMaker---status---- by getpara-"+request.getParameter("loanId"));  
						logger.info("In openRepoMarkingMaker---status---- by getpara by vo-"+vo.getLoanId());
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
						
						ArrayList subloanList = bp.getDetailofLoanForRepoMarkingMaker(vo);
						logger.info("In openRepoMarkingMaker RepoMarkingMakerVo list"+subloanList.size());
						request.setAttribute("subloanList", subloanList);
					
						
						vo=(RepoMarkingMakerVo) subloanList.get(0);
						RepoMarkingMakerVo docVo=new RepoMarkingMakerVo();
						docVo=(RepoMarkingMakerVo) subloanList.get(0);
						logger.info("In openEditCaseTypeMaster---status---- by getpara by vo-"+docVo.getRecStatus());
						
					
						request.setAttribute("status", vo.getRecStatus());
						request.setAttribute("retriveValues", "retriveValues");
					   return mapping.findForward("addedit");	
		}
	
}