/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
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
import com.cm.dao.SDLiquidationDAO;
import com.cm.vo.LiquidationMakerVO;
import com.cm.vo.LiquidationSearchVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.lockRecord.action.LockRecordCheck;
import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/** 
 * MyEclipse Struts
 * Creation date: 07-30-2011
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class SDLiquidationSearchDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(SDLiquidationSearchDispatchAction.class.getName());	
	/*
	 * Generated Methods
	 */

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward openNewLiquidation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("Inside SDLiquidationSearchDispatchAction...........openNewLiquidation");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in openNewLiquidation method of SDLiquidationSearchDispatchAction action the session is out----------------");
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
		session.removeAttribute("liquidationDataDisabled");
		session.removeAttribute("loanId");
		session.removeAttribute("sdId");
		session.removeAttribute("sdLiquidationId");
		request.setAttribute("sdLiquidationNew","sdLiquidationNew");
		return mapping.findForward("openNewLiquidation");
	}
	 
	 public ActionForward searchLiquidation(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception{
			logger.info("Inside SDLiquidationSearchDispatchAction........searchLiquidation");
			
			HttpSession session = request.getSession();
		
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String userId="";
			String branchId="";
			if(userobj!=null)
			{
				userId=userobj.getUserId();
				branchId=userobj.getBranchId();
			}else{
				logger.info("here in searchLiquidation method of SDLiquidationSearchDispatchAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			LiquidationSearchVO vo = new LiquidationSearchVO();
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
			
			String type = CommonFunction.checkNull(request.getParameter("type"));
			
			DynaValidatorForm SDLiquidationSearchDynaValidatorForm = (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo,SDLiquidationSearchDynaValidatorForm);

			if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
					{ 
						vo.setReportingToUserId(userId);
					   //logger.info("When user id is not selected by the user:::::"+userId);
					}
					logger.info("user Id:::::"+vo.getReportingToUserId());
					vo.setStage(type);
					vo.setBranchId(branchId);
					vo.setUserId(userId);

			//CreditManagementDAO service = new CreditManagementDAOImpl();
					SDLiquidationDAO service=(SDLiquidationDAO)DaoImplInstanceFactory.getDaoImplInstance(SDLiquidationDAO.IDENTITY);
					logger.info("Implementation class: "+service.getClass());
			ArrayList<LiquidationSearchVO> liquidationSearchList = service.searchLiquidationData(vo,type);
			if(liquidationSearchList.size()==0)
			{
				request.setAttribute("message","N");
				if(type.equalsIgnoreCase("P"))
				{
					request.setAttribute("sdLiquidationMakerSearch","sdLiquidationMakerSearch");
					request.setAttribute("liquidationSearchList", "liquidationSearchList");
				}
				
				else if(type.equalsIgnoreCase("F"))
				{
					request.setAttribute("sdLiquidationAuthorSearch","sdLiquidationAuthorSearch");
					request.setAttribute("liquidationSearchList", "liquidationSearchList");
				}
			}
			else
			{
				if(type.equalsIgnoreCase("P"))
				{
					request.setAttribute("sdLiquidationMakerSearch","sdLiquidationMakerSearch");
					request.setAttribute("liquidationSearchList", "liquidationSearchList");
					request.setAttribute("list", liquidationSearchList);
				}
				
				else if(type.equalsIgnoreCase("F"))
				{
					request.setAttribute("sdLiquidationAuthorSearch","sdLiquidationAuthorSearch");
					request.setAttribute("liquidationSearchList", "liquidationSearchList");
					request.setAttribute("list", liquidationSearchList);
				}
			}
			vo.setLbxLoanNoHID("");
			vo.setCustomerName("");
			return mapping.findForward("searchLiquidation");
		} 
	
	 public ActionForward openLiquidationValues(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception{
			logger.info("Inside SDLiquidationSearchDispatchAction........openLiquidationValues");
			
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String userId = "";
			if(userobj!=null)
			{
				userId=userobj.getUserId();
			}else{
				logger.info("here in openLiquidationValues method of SDLiquidationSearchDispatchAction action the session is out----------------");
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
			session.removeAttribute("liquidationDataDisabled");
			session.removeAttribute("loanId");
			session.removeAttribute("sdId");
			session.removeAttribute("sdLiquidationId");
			
			String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
			String sdId = CommonFunction.checkNull(request.getParameter("sdId"));
			String sdLiquidationId = CommonFunction.checkNull(request.getParameter("sdLiquidId"));
			
			//CreditManagementDAO service = new CreditManagementDAOImpl();
			SDLiquidationDAO service=(SDLiquidationDAO)DaoImplInstanceFactory.getDaoImplInstance(SDLiquidationDAO.IDENTITY);
			logger.info("Implementation class: "+service.getClass());
			
			String businessDate="";
			if(userobj != null)
			{
				businessDate = userobj.getBusinessdate();
			}
			String makerDate=service.getMakerDate(loanId);
			String checkFlag=service.earlyClosureFlag();
			request.setAttribute("checkFlag",checkFlag);
			request.setAttribute("businessDate1",businessDate);
			request.setAttribute("makerDate",makerDate);
			
			logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
			String functionId="";
	
			if(session.getAttribute("functionId")!=null)
			{
				functionId=session.getAttribute("functionId").toString();
			}
			
			
			//ServletContext context=getServlet().getServletContext();
			if(context!=null)
			{
			flag = LockRecordCheck.lockCheck(userId,functionId,loanId,context);
			logger.info("Flag ........................................ "+flag);
			if(!flag)
			{
				logger.info("Record is Locked");			
				request.setAttribute("message", "Locked");
				request.setAttribute("recordId", loanId);
				request.setAttribute("sdLiquidationMakerSearch","sdLiquidationMakerSearch");
				return mapping.findForward("searchLiquidation");
			}
			}
			ArrayList<LiquidationMakerVO> liquidationData = service.selectLiquidationData(loanId,sdId,sdLiquidationId,"P");
			LiquidationMakerVO vo=new LiquidationMakerVO();
			vo=liquidationData.get(0);
			String liqFlag ="";
			liqFlag =vo.getLiquidationFlag();
			logger.info("vo.getLiquidationFlag()  :  "+vo.getLiquidationFlag());
			
			if(liqFlag.equalsIgnoreCase("P"))
				request.setAttribute("default",vo.getSdInterestAccrued());
			if(liqFlag.equalsIgnoreCase("F"))
				request.setAttribute("default",vo.getSdFinalInterest());
							
			request.setAttribute("liquidationData", liquidationData);
			request.setAttribute("sdLiquidationId",sdLiquidationId);
			return mapping.findForward("showLiquidationDataMaker");
		}
	 
	 public ActionForward openLiquidationValuesAuthor(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception{
			logger.info("Inside SDLiquidationSearchDispatchAction........openLiquidationAuthorValues");
			
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String userId = "";
			if(userobj!=null)
			{
				userId=userobj.getUserId();
			}else{
				logger.info("here in openLiquidationValuesAuthor method of SDLiquidationSearchDispatchAction action the session is out----------------");
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
			session.removeAttribute("liquidationDataDisabled");
			session.removeAttribute("loanId");
			session.removeAttribute("sdId");
			
			String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
			String sdId = CommonFunction.checkNull(request.getParameter("sdId"));
			String sdLiquidationId = CommonFunction.checkNull(request.getParameter("sdLiquidId"));
			
			//CreditManagementDAO service = new CreditManagementDAOImpl();
			SDLiquidationDAO service=(SDLiquidationDAO)DaoImplInstanceFactory.getDaoImplInstance(SDLiquidationDAO.IDENTITY);
			logger.info("Implementation class: "+service.getClass());
			
			logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
			String functionId="";
	
			
			if(session.getAttribute("functionId")!=null)
			{
				functionId=session.getAttribute("functionId").toString();
			}
			
			
			//ServletContext context=getServlet().getServletContext();
			if(context!=null)
			{
			flag = LockRecordCheck.lockCheck(userId,functionId,loanId,context);
			logger.info("Flag ........................................ "+flag);
			if(!flag)
			{
				logger.info("Record is Locked");			
				request.setAttribute("message", "Locked");
				request.setAttribute("recordId", loanId);
				request.setAttribute("sdLiquidationAuthorSearch","sdLiquidationAuthorSearch");
				return mapping.findForward("searchLiquidation");
			}
			}
			ArrayList<LiquidationMakerVO> liquidationDataDisabled = service.selectLiquidationData(loanId,sdId,sdLiquidationId,"F");
			
			LiquidationMakerVO lvo=new LiquidationMakerVO();
			lvo=liquidationDataDisabled.get(0);
			String liqFlag ="";
			liqFlag =lvo.getLiquidationFlag();
			logger.info("vo.getLiquidationFlag()  :  "+lvo.getLiquidationFlag());
			String amount="";
			
			if(liqFlag.equalsIgnoreCase("P"))
				amount=lvo.getSdInterestAccrued();
			if(liqFlag.equalsIgnoreCase("F"))
				amount=lvo.getSdFinalInterest();
			session.setAttribute("amount",amount);	
			logger.info("Final Amount : "+amount);
			session.setAttribute("liquidationDataDisabled", liquidationDataDisabled);
			session.setAttribute("loanId",loanId);
			session.setAttribute("sdId",sdId);
			session.setAttribute("sdLiquidationId",sdLiquidationId);
			return mapping.findForward("showLiquidationDataAuthor");
		}
	 
	 public ActionForward sdLiquidationMakerSearch(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {
			LoggerMsg.info("Inside SDLiquidationSearchBehindAction...........sdLiquidationMakerSearch");
			
			HttpSession session = request.getSession();
			
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String userId="";
			String branchId="";
			if(userobj!=null)
			{
				userId=userobj.getUserId();
				branchId=userobj.getBranchId();
			}else{
				logger.info("here in sdLiquidationMakerSearch method of SDLiquidationSearchDispatchAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			LiquidationSearchVO vo = new LiquidationSearchVO();
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
			
			String type = "P";
			
			DynaValidatorForm SDLiquidationSearchDynaValidatorForm = (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo,SDLiquidationSearchDynaValidatorForm);
			if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
			{ 
				vo.setReportingToUserId(userId);
			   //logger.info("When user id is not selected by the user:::::"+userId);
			}
			logger.info("user Id:::::"+vo.getReportingToUserId());
			vo.setStage(type);
			vo.setBranchId(branchId);
			vo.setUserId(userId);
			vo.setLbxLoanNoHID("");
			vo.setCustomerName("");
			//CreditManagementDAO service = new CreditManagementDAOImpl();
			SDLiquidationDAO service=(SDLiquidationDAO)DaoImplInstanceFactory.getDaoImplInstance(SDLiquidationDAO.IDENTITY);
			logger.info("Implementation class: "+service.getClass());
			ArrayList<LiquidationSearchVO> liquidationSearchList = service.searchLiquidationData(vo,type);
			if(liquidationSearchList.size()==0)
			{
				request.setAttribute("message","N");
				if(type.equalsIgnoreCase("P"))
				{
					request.setAttribute("sdLiquidationMakerSearch","sdLiquidationMakerSearch");
					request.setAttribute("liquidationSearchList", "liquidationSearchList");
				}
				
				else if(type.equalsIgnoreCase("F"))
				{
					request.setAttribute("sdLiquidationAuthorSearch","sdLiquidationAuthorSearch");
					request.setAttribute("liquidationSearchList", "liquidationSearchList");
				}
			}
			else
			{
				if(type.equalsIgnoreCase("P"))
				{
					request.setAttribute("sdLiquidationMakerSearch","sdLiquidationMakerSearch");
					request.setAttribute("liquidationSearchList", "liquidationSearchList");
					request.setAttribute("list", liquidationSearchList);
				}
				
				else if(type.equalsIgnoreCase("F"))
				{
					request.setAttribute("sdLiquidationAuthorSearch","sdLiquidationAuthorSearch");
					request.setAttribute("liquidationSearchList", "liquidationSearchList");
					request.setAttribute("list", liquidationSearchList);
				}
			}

			return mapping.findForward("searchLiquidation");
		}
		
		public ActionForward sdLiquidationAuthorSearch(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {
			logger.info("Inside SDLiquidationSearchBehindAction...........sdLiquidationAuthorSearch");
			
			HttpSession session = request.getSession();
			
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String userId="";
			String branchId="";
			if(userobj!=null)
			{
				userId=userobj.getUserId();
				branchId=userobj.getBranchId();
			}else{
				logger.info("here in sdLiquidationAuthorSearch method of SDLiquidationSearchDispatchAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			LiquidationSearchVO vo = new LiquidationSearchVO();
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
			
			String type = "F";
			
			DynaValidatorForm SDLiquidationSearchDynaValidatorForm = (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo,SDLiquidationSearchDynaValidatorForm);
			if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
					{ 
						vo.setReportingToUserId(userId);
					   //logger.info("When user id is not selected by the user:::::"+userId);
					}
					logger.info("user Id:::::"+vo.getReportingToUserId());
			vo.setStage(type);
			vo.setBranchId(branchId);
			vo.setUserId(userId);
			vo.setLbxLoanNoHID("");
			vo.setCustomerName("");
			//CreditManagementDAO service = new CreditManagementDAOImpl();
			SDLiquidationDAO service=(SDLiquidationDAO)DaoImplInstanceFactory.getDaoImplInstance(SDLiquidationDAO.IDENTITY);
			logger.info("Implementation class: "+service.getClass());
			ArrayList<LiquidationSearchVO> liquidationSearchList = service.searchLiquidationData(vo,type);
			if(liquidationSearchList.size()==0)
			{
				request.setAttribute("message","N");
				if(type.equalsIgnoreCase("P"))
				{
					request.setAttribute("sdLiquidationMakerSearch","sdLiquidationMakerSearch");
					request.setAttribute("liquidationSearchList", "liquidationSearchList");
				}
				
				else if(type.equalsIgnoreCase("F"))
				{
					request.setAttribute("sdLiquidationAuthorSearch","sdLiquidationAuthorSearch");
					request.setAttribute("liquidationSearchList", "liquidationSearchList");
				}
			}
			else
			{
				if(type.equalsIgnoreCase("P"))
				{
					request.setAttribute("sdLiquidationMakerSearch","sdLiquidationMakerSearch");
					request.setAttribute("liquidationSearchList", "liquidationSearchList");
				}
				
				else if(type.equalsIgnoreCase("F"))
				{
					request.setAttribute("sdLiquidationAuthorSearch","sdLiquidationAuthorSearch");
					request.setAttribute("liquidationSearchList", "liquidationSearchList");
					request.setAttribute("list", liquidationSearchList);
				}
			}

			return mapping.findForward("searchLiquidation");
		}
}