package com.cp.actions;

import java.math.BigDecimal;
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
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.collateralVerification.dao.CollateralVerificationDAO;
import com.cp.collateralVerification.dao.CollateralVerificationDAOImpl;
import com.cp.collateralVerification.vo.CollateralCapturingVO;
import com.cp.dao.CreditProcessingDAO;
import com.cp.dao.FieldVerificationDAO;
import com.cp.dao.TradeCheckDAO;
import com.cp.vo.CommonPageSecVo;
import com.cp.vo.CommonPageVo;
import com.cp.vo.FieldVerificationVo;
import com.cp.vo.TermSheetVo;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/**
 * MyEclipse Struts Creation date: 03-05-2011
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */

public class CommonPageProcessingAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(CommonPageProcessingAction.class.getName());
	/*
	 * Generated Methods
	 */

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */

	public ActionForward updateCreditDecision(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("In CommonPageProcessingAction(updateCreditDecision)");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bgDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bgDate=userobj.getBusinessdate();
				
		}else{
			logger.info("here in updateCreditDecision method of CommonPageProcessingAction action the session is out----------------");
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
		
	
		
		String sms = "";
		String retStr="";
		session.removeAttribute("creditDecision");
		session.removeAttribute("underWriterList");
		DynaValidatorForm UnderWritingDynaValidatorForm = (DynaValidatorForm) form;

		CommonPageSecVo vo = new CommonPageSecVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,UnderWritingDynaValidatorForm);
		vo.setUseId(userId);
		vo.setBussinessDate(bgDate);
		CreditProcessingDAO detail=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+detail.getClass()); 			// changed by asesh
		//CreditProcessingDAO detail = new CreditProcessingDAOImpl();
		
//		if(!CommonFunction.checkNull(detail.checkCustomerType(vo)).equalsIgnoreCase("0") && CommonFunction.checkNull(detail.getGroupExposureLimitCheck(vo)).equalsIgnoreCase("groupExposure"))
//		{
//			request.setAttribute("checkStatus","groupExposure");
//			
//			retStr="checkStatusFailed";
//		}
//		
//		else if(!CommonFunction.checkNull(detail.checkCustomerType(vo)).equalsIgnoreCase("0") && CommonFunction.checkNull(detail.getCustomerExposureLimitCheck(vo)).equalsIgnoreCase("customerExposure") )
//		{
//			request.setAttribute("checkStatus","customerExposure");
//			
//			retStr="checkStatusFailed";
//		}
//		else
//		{
        	String sancAmt=CommonFunction.checkNull(request.getParameter("sancAmt")).trim();
        	if(CommonFunction.checkNull(sancAmt).trim().equalsIgnoreCase(""))
        		sancAmt="0.00";
			TermSheetVo termsVo=new TermSheetVo();
			termsVo.setMakerId(userId);
			termsVo.setMakerDate(bgDate);
		    String dealId = (String) session.getAttribute("dealId");
		    termsVo.setDealId(dealId);	
		    termsVo.setGrossAmountLoan(sancAmt);
		    termsVo.setAction("I");
		    String flagSt=detail.saveTermSheetDetails(termsVo);
			double sancAmtDisp = detail.updateCreditDecision(vo);
			if (sancAmtDisp > 0) {
				sms = "S";
			} else {
				sms = "E";
			}
			request.setAttribute("sms", sms);
			request.setAttribute("sancAmtDisp", sancAmtDisp);
			logger
					.info("In CommonPageProcessingAction(updateCreditDecision) Total Sanctioned Amount................................. : "
							+ sancAmtDisp);
			retStr="success";
		//}
		return mapping.findForward(retStr);

	}

	public ActionForward getUnderWriterDataFrameset(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("In CommonPageProcessingAction(getUnderWriterDataFrameset)");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId=null;
		String businessDate=null;
		if(userobj==null)
		{
			logger.info("here in getUnderWriterDataFrameset method of CommonPageProcessingAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
		{
			userId=userobj.getUserId();
			businessDate=userobj.getBusinessdate();
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

		String dealId = (String) session.getAttribute("dealId");
		session.removeAttribute("creditDecision");
		session.removeAttribute("dealId");
		session.removeAttribute("underWriterList");
		CommonPageVo cr = new CommonPageVo();
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
		//CreditProcessingDAO service = new CreditProcessingDAOImpl();
		ArrayList underWriterList = service.getUnderWriterData(dealId);
		ArrayList creditDecision = service.getCreditDecisionData(dealId);
		logger.info("In CommonPageProcessingAction(getUnderWriterDataFrameset) Size of Credit Decision list Frameset............ "
						+ creditDecision.size());
		session.setAttribute("underWriterList", underWriterList);
		session.setAttribute("creditDecision", creditDecision);
		session.setAttribute("dealId", dealId);
		String uwSearchUser=CommonFunction.checkNull(session.getAttribute("uwSearchUser")).trim();
		CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	    logger.info("Implementation class: "+dao.getClass()); // changed by asesh
		ArrayList expoGrid=dao.getGroupExpLimitGrid(dealId,userId,uwSearchUser,businessDate);
		String exQry=" select LOAN_BALANCE_PRINCIPAL,LOAN_OVERDUE_PRINCIPAL,SD_ADVICE_AMOUNT,DEAL_SD_CHARGES,EXPOSURE_AMOUNT,TERM_LOAN_AMOUNT,INCLUDE_EXPOSURE,EXPOSURE_WITH_SD " +
	    " from UNDER_WRITER_SEARCH_TEMP_DATA where deal_id='"+CommonFunction.checkNull(dealId).trim()+"' AND USER_ID='"+CommonFunction.checkNull(userId).trim()+"'";
	    logger.info("Exposure Query is    :  "+exQry);
		ArrayList getGroupExposureDataDetail = ConnectionDAO.sqlSelect(exQry);
		for(int i=0;i<getGroupExposureDataDetail.size();i++)
		{
			ArrayList data=(ArrayList)getGroupExposureDataDetail.get(i);
			String balPrincipal=CommonFunction.checkNull(data.get(0)).trim();
			if(CommonFunction.checkNull(balPrincipal).trim().equalsIgnoreCase(""))
				balPrincipal="0.00";
			String overDuePrincipal=CommonFunction.checkNull(data.get(1)).trim();
			if(CommonFunction.checkNull(overDuePrincipal).trim().equalsIgnoreCase(""))
				overDuePrincipal="0.00";
			String sdAdviceAmt=CommonFunction.checkNull(data.get(2)).trim();
			if(CommonFunction.checkNull(sdAdviceAmt).trim().equalsIgnoreCase(""))
				sdAdviceAmt="0.00";
			String dealSDCharge=CommonFunction.checkNull(data.get(3)).trim();
			if(CommonFunction.checkNull(dealSDCharge).trim().equalsIgnoreCase(""))
				dealSDCharge="0.00";
			String loanAmt=CommonFunction.checkNull(data.get(5)).trim();
			if(CommonFunction.checkNull(loanAmt).trim().equalsIgnoreCase(""))
				loanAmt="0.00";			
			
			BigDecimal balancePrincipalAmt = new BigDecimal("0.00");
			BigDecimal overDuePrincipalAmt = new BigDecimal("0.00");
			BigDecimal sdAdviceAmount = new BigDecimal("0.00");
			BigDecimal dealSDChargeAmt = new BigDecimal("0.00");
			BigDecimal loanAmount = new BigDecimal("0.00");
			BigDecimal eposureAmount = new BigDecimal("0.00");
			
			balancePrincipalAmt = new BigDecimal(balPrincipal.toString());
			overDuePrincipalAmt = new BigDecimal(overDuePrincipal.toString());
			sdAdviceAmount = new BigDecimal(sdAdviceAmt.toString());
			dealSDChargeAmt = new BigDecimal(dealSDCharge.toString());
			loanAmount = new BigDecimal(loanAmt.toString());
			eposureAmount=loanAmount;		
			
			// Exposure Logic start
			String IncludeExposure=CommonFunction.checkNull(data.get(6)).trim();
			String exposureWithSD=CommonFunction.checkNull(data.get(7)).trim();
			String customerGroupType=CommonFunction.checkNull(session.getAttribute("UWcustomerGroupType"));
			String customerType=CommonFunction.checkNull(session.getAttribute("UWcustomerType"));
			
			if(CommonFunction.checkNull(customerType).trim().equalsIgnoreCase("I"))
			{
				balancePrincipalAmt = new BigDecimal("0.00");
				overDuePrincipalAmt = new BigDecimal("0.00");
				sdAdviceAmount = new BigDecimal("0.00");
			}
			if(CommonFunction.checkNull(IncludeExposure).trim().equalsIgnoreCase("Y"))
			{
				if(CommonFunction.checkNull(exposureWithSD).trim().equalsIgnoreCase("Y"))
				{
					eposureAmount=balancePrincipalAmt.add(overDuePrincipalAmt).subtract(sdAdviceAmount).subtract(dealSDChargeAmt).add(loanAmount);
				}
				else
				{
					eposureAmount=balancePrincipalAmt.add(loanAmount);
				}
			}
			
			if(CommonFunction.checkNull(IncludeExposure).trim().equalsIgnoreCase("Y"))
			{
				if(CommonFunction.checkNull(exposureWithSD).trim().equalsIgnoreCase("Y"))
				{
					eposureAmount=balancePrincipalAmt.add(overDuePrincipalAmt).subtract(sdAdviceAmount).subtract(dealSDChargeAmt).add(loanAmount);
				}
				else
				{
					eposureAmount=balancePrincipalAmt.add(loanAmount);
				}
			}
			session.setAttribute("dealExposureAmount", eposureAmount);
			session.setAttribute("dealExLoanAmount", loanAmount);
			
			String limitQry=" select CUSTOMER_GROUP_EXPOSURE_LIMIT from cr_deal_dtl a join cr_deal_customer_m b on(a.deal_customer_id=b.customer_id) where deal_id='"+CommonFunction.checkNull(dealId).trim()+"' ";
		    logger.info("Exposure Limit Query is    :  "+limitQry);
		    String exposureLimit=ConnectionDAO.singleReturn(limitQry);
		    exposureLimit=CommonFunction.checkNull(exposureLimit).trim();
		    if(CommonFunction.checkNull(exposureLimit).trim().equalsIgnoreCase(""))
		    	exposureLimit="0.00";
		    session.setAttribute("exposureLimit", exposureLimit);
		}	    
		return mapping.findForward("commonPage");

	}

	
	public ActionForward tradeCheckSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception

	{
		logger.info("In commonPageProcessing.....(tradeCheckSearch)");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in tradeCheckSearch method of CommonPageProcessingAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		String userId=userobj.getUserId();
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
		
		if(CommonFunction.checkNull(request.getParameter("loanStatus")).equalsIgnoreCase("NEW") )
		{
			session.removeAttribute("maxIdInCM");
			session.removeAttribute("loanHeader");
		}
		TradeCheckDAO dao=(TradeCheckDAO)DaoImplInstanceFactory.getDaoImplInstance(TradeCheckDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 	
		//TradeCheckDAO dao = new TradeCheckDAOImpl();
		String dealId = "";
		
			dealId = CommonFunction.checkNull(session.getAttribute("dealId"));
		
		logger.info(" In the commonPageProcessing------dealId----"+dealId);
		
		//ArrayList tradeBuyerSuplierList = dao.getBuyerSuplierInfo(dealId);
		if(dealId!=null && !dealId.equalsIgnoreCase(""))
		{
			ArrayList tradeHeader = dao.getTradeHeader(dealId);
			ArrayList tradeList=dao.getTradeList(dealId,userId,"A");
			session.setAttribute("dealHeader", tradeHeader);
			session.setAttribute("tradeList", tradeList);
			session.setAttribute("dealId",dealId);
		}
		else
		{
			session.removeAttribute("dealId");
			session.removeAttribute("tradeList");
		}
		 
		return mapping.findForward("tradeInfo");
	}
	
	public ActionForward viewCollforUR(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("In openCollateralCompletion Method........Dispatch Action");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();				
		}else{
			logger.info("here in viewCollforUR method of CommonPageProcessingActionaction the session is out----------------");
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
		session.removeAttribute("listUnderWriter");
		session.removeAttribute("collateralCompletionDetails");
		session.removeAttribute("collateralHeader");
		session.removeAttribute("dealId");
		session.removeAttribute("verificationId");
		
		String dealId=CommonFunction.checkNull(request.getParameter("dealId"));
		String verificationId = CommonFunction.checkNull(request.getParameter("verificationId"));
		String functionId="";
		

		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		
		
		//ServletContext context=getServlet().getServletContext();
		if(context!=null)
		{
			flag = LockRecordCheck.lockCheck(userId,functionId,dealId,context);
			logger.info("Flag ........................................ "+flag);
			if(!flag)
			{
				logger.info("Record is Locked");			
				request.setAttribute("message", "Locked");
				request.setAttribute("recordId", dealId);
				return mapping.findForward("searchCollateralVerificationCapturing");
			}
		}
		
		CollateralVerificationDAO service = new CollateralVerificationDAOImpl();
		ArrayList collateralHeader = service.getTradeHeader(dealId);
		ArrayList<CollateralCapturingVO> collateralCompletionDetails = service.getCollateralCapturingData(dealId,"C",userId);
		logger.info("collateralCompletionDetails size: "+collateralCompletionDetails.size());
		session.setAttribute("listUnderWriter",collateralCompletionDetails);
		session.setAttribute("collateralHeader",collateralHeader);
		session.setAttribute("dealId",dealId);
		session.setAttribute("verificationId",verificationId);
		return mapping.findForward("openCollateralCompletion");
	}
	
	public ActionForward viewFieldList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userID="";
		
		if(userobj!=null)
		{
			userID=userobj.getUserId();				
		}else{
			logger.info("here in viewFieldList method of CommonPageProcessingAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		FieldVerificationVo vo=new FieldVerificationVo(); 		
		DynaValidatorForm FieldVarificationDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FieldVarificationDynaValidatorForm);					

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
		/*Changed by asesh*/
		FieldVerificationDAO dao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 
		/*Changed by asesh*/
		//FieldVerificationDAOImpl dao=new FieldVerificationDAOImpl();	
		String dealId = "";				
		dealId = CommonFunction.checkNull(request.getParameter("dealId"));
		if(dealId!=null && !dealId.equalsIgnoreCase(""))
		{
			ArrayList tradeHeader = dao.getTradeHeader(dealId);
			ArrayList customer_detail = dao.getComCustomerDetail(dealId,"A");
			session.setAttribute("dealHeader", tradeHeader);
			request.setAttribute("head","head");
			ArrayList detail=(ArrayList)session.getAttribute("customer_detail");
			if(detail!=null)
			session.removeAttribute("customer_detail");				
			session.setAttribute("customer_detail",customer_detail);
			
			session.setAttribute("dealId",dealId);
		}
		    return mapping.findForward("newFieldCapture");	
	}
}
