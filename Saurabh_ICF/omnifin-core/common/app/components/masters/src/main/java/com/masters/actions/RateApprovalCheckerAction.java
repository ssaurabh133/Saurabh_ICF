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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.business.ejbClient.CommonMasterBussinessSessionBeanRemote;
import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.business.legalTransactionBussiness.LegalTransactionBusinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.legal.vo.LegalCaseFileVo;
import com.legal.vo.LegalNoticeInitiationVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.RateApprovalVo;
import com.masters.vo.VerificationQuestionVo;

/** 
 * MyEclipse Struts
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class RateApprovalCheckerAction extends DispatchAction {
	
	private static final Logger logger = Logger.getLogger(RateApprovalCheckerAction.class.getName());
	
	
	public ActionForward rateApprovalCheckerSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession(false);
		ServletContext context = getServlet().getServletContext();
		logger.info(" ## In rateApprovalCheckerSearch() :  .........");
		
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
			
			logger.info("## in rateApprovalCheckerSearch() : userId : ==>> "+userId);
			logger.info("## in rateApprovalCheckerSearch() : bDate : ==>> "+bDate);
			DynaValidatorForm dynaForm= (DynaValidatorForm)form;
		RateApprovalVo vo = new RateApprovalVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, dynaForm);
		
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		logger.info("## in rateApprovalCheckerSearch() after set the value: userId : ==>> "+userId);


		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
        
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
		//vo1.setCurrentPageLink(currentPageLink);
		
		//change from here
		list= bp.rateApprovalCheckerSearch(vo);

	    logger.info("In Rate Approval Checker Action....list"+list.size());
		
	    request.setAttribute("list", list);
	  
		
		logger.info("list.isEmpty(): "+list.isEmpty());
				
	    return mapping.findForward("search");

}
	public ActionForward rateApprovalCheckerOpen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse resopnse) throws Exception{
		
		logger.info(" ## In rateApprovalCheckerOpen() :  .........");
		
			ServletContext context = getServlet().getServletContext();
			//HttpSession session=request.getSession(false);
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");
			logger.info("rateApprovalCheckerOpen()--->>");
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
			
			logger.info("## in rateApprovalCheckerOpen() : userId : ==>> "+userId);
			logger.info("## in rateApprovalCheckerOpen() : bDate : ==>> "+bDate);
	
		DynaValidatorForm dynaForm= (DynaValidatorForm)form;
		RateApprovalVo vo = new RateApprovalVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, dynaForm);
		

		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		
		logger.info("## in rateApprovalCheckerOpen() : vo.getMakerId() : ==>> "+vo.getMakerId());
		logger.info("## in rateApprovalCheckerOpen() : vo.getMakerDate() : ==>> "+vo.getMakerDate());
		
		
		
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
	
		String sms="";
		
		
		String product =  CommonFunction.checkNull(request.getParameter("product"));
		String scheme =  CommonFunction.checkNull(request.getParameter("scheme"));
		
		logger.info("## in rateApprovalCheckerOpen() : product : ==>> "+product);
		logger.info("## in rateApprovalCheckerOpen() : scheme : ==>> "+scheme);
		
		vo.setLbxProductID(product);
		vo.setLbxScheme(scheme);
		
		ArrayList list = bp.openRateApprovalCheckerData(vo);
		
		session.setAttribute("rateApprovalList", list);
		session.setAttribute("rateApprovalProduct", product);
		session.setAttribute("rateApprovalScheme", scheme);
		
		
		logger.info("## in rateApprovalCheckerOpen() : session : product : ==>> "+session.getAttribute("rateApprovalProduct"));
		logger.info("## in rateApprovalCheckerOpen() : session : scheme : ==>> "+session.getAttribute("rateApprovalScheme"));
		
		
		return mapping.findForward("open");
	}
	
	public ActionForward openRateApprovalChecker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info(" in authorScreen method of RateApprovalCheckerAction  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");

		String strFlag="";	
		
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		//for check User session start
		ServletContext context = getServlet().getServletContext();
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
		
		logger.info("## in openRateApprovalChecker() : session : product : ==>> "+session.getAttribute("rateApprovalProduct"));
		logger.info("## in openRateApprovalChecker() : session : scheme : ==>> "+session.getAttribute("rateApprovalScheme"));
		
		
		return mapping.findForward("authorScreen");
	}
	
	public ActionForward saveRateApprovalChecker1111(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse resopnse) throws Exception{
			ServletContext context = getServlet().getServletContext();
			HttpSession session=request.getSession(false);
			//HttpSession session = request.getSession();
			logger.info("## in saveRateApprovalChecker() : session : ==>> "+session);
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");
			
			logger.info("## in saveRateApprovalChecker() : userobj : ==>> "+userobj);
			logger.info("## in saveRateApprovalChecker() : sessionId : ==>> "+sessionId);
			
			logger.info("rateApprovalCheckerOpen()--->>");
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
			
			logger.info("## in saveRateApprovalChecker() : userId : ==>> "+userId);
			logger.info("## in saveRateApprovalChecker() : bDate : ==>> "+bDate);
	
		DynaValidatorForm dynaForm= (DynaValidatorForm)form;
		RateApprovalVo vo = new RateApprovalVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, dynaForm);
		

		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		
		logger.info("## in saveRateApprovalChecker() : vo.getMakerId() : ==>> "+vo.getMakerId());
		logger.info("## in saveRateApprovalChecker() : vo.getMakerDate() : ==>> "+vo.getMakerDate());
		
		//vo.setLegalId(session.getAttribute("caseFileAuthorLegalId").toString());
		
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		
		logger.info("## in saveRateApprovalChecker() : session : product : ==>> "+session.getAttribute("rateApprovalProduct"));
		logger.info("## in saveRateApprovalChecker() : session : scheme : ==>> "+session.getAttribute("rateApprovalScheme"));
		
		String product = (String) session.getAttribute("rateApprovalProduct");
		String scheme = (String) session.getAttribute("rateApprovalScheme");
		
		logger.info("## In saveRateApprovalChecker() : product : ==>> "+product);
		logger.info("## In saveRateApprovalChecker() : scheme : ==>> "+scheme);
		
		vo.setLbxProductID(product);
		vo.setLbxScheme(scheme);
		
		String comment=vo.getComments();
		String decission=vo.getDecision();
		logger.info("rate approval checker Action.....Comment...."+comment);
		logger.info("rate approval checker Action........."+decission);
		logger.info("rate approval checker Action........."+vo.getLbxProductID());
		logger.info("rate approval checker Action........."+vo.getLbxScheme());
		boolean status = bp.saveRateApprovalChecker(vo);
		logger.info("Rate approval checker Action.....displaying status...."+status);
		
		if(status){
			request.setAttribute("sms","SBA");
			  if(CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase("A"))
			{
				request.setAttribute("decision","Case Approved Successfully");
			}
			else if(CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase("P"))
			{
				request.setAttribute("decision","Case SendBack Successfully");
			}
			
			//request.setAttribute("save", "save");
		}
		else{
			
			request.setAttribute("sms","E");
			//request.setAttribute("save", "save");
		}
		logger.info("status"+status);
		return mapping.getInputForward();
	}
	
	public ActionForward saveRateApprovalChecker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse resopnse) throws Exception{
		
		logger.info(" ## In rateApprovalCheckerOpen() :  .........");
		
			ServletContext context = getServlet().getServletContext();
			//HttpSession session=request.getSession(false);
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");
			
			logger.info("## in saveRateApprovalChecker() : userobj : ==>> "+userobj);
			logger.info("## in saveRateApprovalChecker() : sessionId : ==>> "+sessionId);
			
			logger.info("rateApprovalCheckerOpen()--->>");
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
			
			logger.info("## in saveRateApprovalChecker() : userId : ==>> "+userId);
			logger.info("## in saveRateApprovalChecker() : bDate : ==>> "+bDate);
	
		DynaValidatorForm dynaForm= (DynaValidatorForm)form;
		RateApprovalVo vo = new RateApprovalVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, dynaForm);
		

		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		
		logger.info("## in saveRateApprovalChecker() : vo.getMakerId() : ==>> "+vo.getMakerId());
		logger.info("## in saveRateApprovalChecker() : vo.getMakerDate() : ==>> "+vo.getMakerDate());
		
		
		
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
	
		String sms="";
		
		
		String product = (String) session.getAttribute("rateApprovalProduct");
		String scheme = (String) session.getAttribute("rateApprovalScheme");
		
		logger.info("## In saveRateApprovalChecker() : product : ==>> "+product);
		logger.info("## In saveRateApprovalChecker() : scheme : ==>> "+scheme);
		
		vo.setLbxProductID(product);
		vo.setLbxScheme(scheme);
		
		boolean status = bp.saveRateApprovalChecker(vo);
		
		logger.info("## in saveRateApprovalChecker() : session : product : ==>> "+session.getAttribute("rateApprovalProduct"));
		logger.info("## in saveRateApprovalChecker() : session : scheme : ==>> "+session.getAttribute("rateApprovalScheme"));
		
		logger.info("## in saveRateApprovalChecker() : status : ==>> "+status);
		
		
		if(status){
			request.setAttribute("sms","S");
			  if(CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase("A"))
			{
				request.setAttribute("decision","Data Approved Successfully");
			}
			else if(CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase("P"))
			{
				request.setAttribute("decision","Data SendBack Successfully");
			}
			
			//request.setAttribute("save", "save");
		}
		else{
			
			request.setAttribute("sms","E");
			//request.setAttribute("save", "save");
		}
		logger.info("status"+status);
		return mapping.getInputForward();
	}
	
}