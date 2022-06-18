/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cp.actions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

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
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.FieldVerificationDAO;
import com.cp.dao.IndividualFinancialAnalysisDAO;
import com.cp.fundFlowDao.FundFlowAnalysisDAO;

import com.cp.vo.ObligationVo;

import java.text.SimpleDateFormat;
import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/** 
 * MyEclipse Struts
 * Creation date: 09-29-2011
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */

public class ObligationProcessAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(ObligationProcessAction.class.getName());
	DecimalFormat myFormatter = new DecimalFormat("###,###.####");
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	public ActionForward saveObligationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
			{
			logger.info("In saveObligationDetails................. ");
		  	boolean flag =false;
		    HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
		    String userId="";
			String bgDate="";
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bgDate=userobj.getBusinessdate();
			}else{
				logger.info("here in saveObligationDetails method of ObligationProcessAction action the session is out----------------");
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
		FundFlowAnalysisDAO fundFlowAnalysisDAO=(FundFlowAnalysisDAO)DaoImplInstanceFactory.getDaoImplInstance(FundFlowAnalysisDAO.IDENTITY);
		logger.info("Implementation class: "+fundFlowAnalysisDAO.getClass());
		DynaValidatorForm obligationDynaValidatorForm = (DynaValidatorForm) form;
		ObligationVo vo=new ObligationVo();
	    org.apache.commons.beanutils.BeanUtils.copyProperties(vo, obligationDynaValidatorForm);
	    
	    if(vo.getCustomerType().equalsIgnoreCase("APPLICANT"))
			  vo.setCustomerType("PRAPPL");
		  else if(vo.getCustomerType().equalsIgnoreCase("CO APPLICANT"))
			  vo.setCustomerType("COAPPL");
		vo.setUserId(userId);
		vo.setBusinessDate(bgDate);
		if(session.getAttribute("fundFlowDealId")!=null)
		{
			vo.setDealId(session.getAttribute("fundFlowDealId").toString());
			//logger.info("deal id.................................. "+session.getAttribute("fundFlowDealId").toString());
		}
		
		 SimpleDateFormat dateFormat1 = new SimpleDateFormat(dateForDisbursal);
		  Date maturityDate =null; 
		  Date businessDate = null;
		  businessDate = dateFormat1.parse(bgDate);
		  boolean maturityFlag=false;
		  if(CommonFunction.checkNull(vo.getTypeOfLoan()).equalsIgnoreCase("CC/OD Loan"));
		  {
				maturityFlag=true;
		  }
		if(!maturityFlag || !CommonFunction.checkNull(vo.getMaturityDate()).equalsIgnoreCase(""))
		{
				   maturityDate = dateFormat1.parse(vo.getMaturityDate()); 
				   maturityFlag=false;
				  
		}
			System.out.println("CC/OD Loan---"+vo.getTypeOfLoan());
			
		if( maturityFlag || maturityDate.after(businessDate) || maturityDate.equals(businessDate))
		{
			
			String obligationId=fundFlowAnalysisDAO.saveObligation(vo);
			if(CommonFunction.checkNull(obligationId ).equalsIgnoreCase("saved") )
			{
			 request.setAttribute("sms", "S");
			}else if(CommonFunction.checkNull(obligationId ).equalsIgnoreCase("already")){
				request.setAttribute("sms", "A");	
			}else{
				request.setAttribute("sms", "E");
			}
			// return mapping.getInputForward();
		}
		else
		{
		
			logger.info("maturity date less than business date");
			ArrayList obligationDetail =new ArrayList();
			obligationDetail.add(vo);
			request.setAttribute("obligationDetail", obligationDetail); 
			
			request.setAttribute("sms", "M");
			
			request.setAttribute("insert", "insert");
		}
		 String recStatus="";
			if(session.getAttribute("fundFlowAuthor")!=null && session.getAttribute("fundFlowAuthor").toString().equalsIgnoreCase("A"))
			{
		    	recStatus="F";
				
			}
			else
			{
				recStatus="P";
				
			}   
		ArrayList obligationList = fundFlowAnalysisDAO.getObligationDetails("",vo.getDealId(),recStatus);
		if(obligationList.size()>0)
		{
			request.setAttribute("obligationList", obligationList);
		}
		/*Code by arun For addition of field in obligation*/

		IndividualFinancialAnalysisDAO dao=(IndividualFinancialAnalysisDAO)DaoImplInstanceFactory.getDaoImplInstance(IndividualFinancialAnalysisDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 	// changed by asesh
		//IndividualFinancialAnalysisDAO dao = new IndividualFinancialAnalysisDAOImpl();
		ArrayList customerTypeList = dao.getCustomerTypeList(vo.getDealId());
		ArrayList obligationTypeList = dao.getObligationTypeList();
		ArrayList customerNameList = dao.getCustomerName(vo.getDealId(), "PRAPPL");
		ArrayList typeOfLoanList = dao.getTypeOfLoan();
		ArrayList statusList = dao.getGenericMasterInfoList("STATUS");
		ArrayList mobList = dao.getGenericMasterInfoList("MOB");
		request.setAttribute("mobList", mobList);
		request.setAttribute("statusList", statusList);
		request.setAttribute("typeOfLoanList", typeOfLoanList);
		request.setAttribute("customerNameList", customerNameList);
		request.setAttribute("customerTypeList", customerTypeList);
		request.setAttribute("obligationTypeList", obligationTypeList);
		request.setAttribute("vanillaProgramStatusList", statusList);
		FieldVerificationDAO fieldVerificationdao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
        logger.info("Implementation class: "+fieldVerificationdao.getClass()); 	// changed by asesh
		//FieldVerificationDAO fieldVerificationdao=new FieldVerificationDAOImpl();	
		ArrayList verifMethodList = fieldVerificationdao.getVerifMethodListList();
		request.setAttribute("verifMethodList", verifMethodList);
		 /*Code by arun For addition of field in obligation End here*/
		logger.info("In obligationList size................ "+obligationList.size());
	  return mapping.getInputForward();
	}
	
	public ActionForward deleteObligationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		
		 LoggerMsg.info("In deleteObligationDetails");
	
		    boolean flag =false;
		    HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in deleteObligationDetails method of ObligationProcessAction action the session is out----------------");
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
			
		 int status=0;
		
		 FundFlowAnalysisDAO dao=(FundFlowAnalysisDAO)DaoImplInstanceFactory.getDaoImplInstance(FundFlowAnalysisDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
		 String obligation[] = request.getParameterValues("chk");
		 for(int k=0;k<obligation.length;k++)
		 {
			
			 status = dao.deleteObligationDetails(obligation[k]);
		 }
		 
		 if(status>0)
		 {
			 request.setAttribute("sms", "Del"); 
			
  	  }
		  else
		  {
				 request.setAttribute("sms", "DE"); 
		  }
		 String dealId="";
		    if(session.getAttribute("fundFlowDealId")!=null)
			 {
				 dealId = session.getAttribute("fundFlowDealId").toString();
			 }
		    String recStatus="";
			if(session.getAttribute("fundFlowAuthor")!=null && session.getAttribute("fundFlowAuthor").toString().equalsIgnoreCase("A"))
			{
		    	recStatus="F";
				
			}
			else
			{
				recStatus="P";
				
			}   
		 ArrayList obligationList = dao.getObligationDetails("",dealId,recStatus);
		 if(obligationList.size()>0)
		 {
			 request.setAttribute("obligationList", obligationList);
		 }
		 /*Code by arun For addition of field in obligation*/

		 	IndividualFinancialAnalysisDAO individao=(IndividualFinancialAnalysisDAO)DaoImplInstanceFactory.getDaoImplInstance(IndividualFinancialAnalysisDAO.IDENTITY);
		 	logger.info("Implementation class: "+individao.getClass()); 	// changed by asesh
			//IndividualFinancialAnalysisDAO individao = new IndividualFinancialAnalysisDAOImpl();
			ArrayList customerTypeList = individao.getCustomerTypeList(dealId);
			ArrayList obligationTypeList = individao.getObligationTypeList();
			ArrayList customerNameList = individao.getCustomerName(dealId, "PRAPPL");
			ArrayList typeOfLoanList = individao.getTypeOfLoan();
			ArrayList statusList = individao.getGenericMasterInfoList("STATUS");
			ArrayList mobList = individao.getGenericMasterInfoList("MOB");
			request.setAttribute("vanillaProgramStatusList", statusList);
			request.setAttribute("mobList", mobList);
			request.setAttribute("statusList", statusList);
			request.setAttribute("typeOfLoanList", typeOfLoanList);
			request.setAttribute("customerNameList", customerNameList);
			request.setAttribute("customerTypeList", customerTypeList);
			request.setAttribute("obligationTypeList", obligationTypeList);
			FieldVerificationDAO fieldVerificationdao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
	        logger.info("Implementation class: "+fieldVerificationdao.getClass()); 	// changed by asesh	
			ArrayList verifMethodList = fieldVerificationdao.getVerifMethodListList();
			request.setAttribute("verifMethodList", verifMethodList);
			 /*Code by arun For addition of field in obligation End here*/
		return mapping.getInputForward();
	}
	
	public ActionForward getObligationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		logger.info("In getObligationDetails .... ");
		
		  boolean flag =false;
		    HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in getObligationDetails method of ObligationProcessAction action the session is out----------------");
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
		String obligationId="";
		 if(request.getParameter("obligationId")!=null)
		 {
			 obligationId=request.getParameter("obligationId");
		 }
		 FundFlowAnalysisDAO dao=(FundFlowAnalysisDAO)DaoImplInstanceFactory.getDaoImplInstance(FundFlowAnalysisDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
		 String dealId="";
		    if(session.getAttribute("fundFlowDealId")!=null)
			 {
				 dealId = session.getAttribute("fundFlowDealId").toString();
			 }
		    String recStatus="";
			if(session.getAttribute("fundFlowAuthor")!=null && session.getAttribute("fundFlowAuthor").toString().equalsIgnoreCase("A"))
			{
		    	recStatus="F";
				
			}
			else
			{
				recStatus="P";
				
			}   
			//Nishant space starts
		    if(session.getAttribute("underWriterViewData")!=null)
		    {
		    	recStatus="A";
		    }
		    //Nishant space end
		 ArrayList obligationList = dao.getObligationDetails(obligationId,dealId,recStatus);
		 if(obligationList.size()>0)
		 {
			 request.setAttribute("obligationDetail", obligationList); 
		 }
			
		     obligationList = dao.getObligationDetails("",dealId,recStatus);
			  if(obligationList.size()>0)
			  {
				  request.setAttribute("obligationList", obligationList);  
			  }
			  /*Code by arun For addition of field in obligation*/

			  	IndividualFinancialAnalysisDAO indvidao=(IndividualFinancialAnalysisDAO)DaoImplInstanceFactory.getDaoImplInstance(IndividualFinancialAnalysisDAO.IDENTITY);
			  	logger.info("Implementation class: "+indvidao.getClass()); 	// changed by asesh
				//IndividualFinancialAnalysisDAO indvidao = new IndividualFinancialAnalysisDAOImpl();
				ArrayList customerTypeList = indvidao.getCustomerTypeList(dealId);
				ArrayList obligationTypeList = indvidao.getObligationTypeList();
				ArrayList customerNameList = indvidao.getCustomerName(dealId, "PRAPPL");
				ArrayList typeOfLoanList = indvidao.getTypeOfLoan();
				ArrayList statusList = indvidao.getGenericMasterInfoList("STATUS");
				ArrayList mobList = indvidao.getGenericMasterInfoList("MOB");
				request.setAttribute("mobList", mobList);
				request.setAttribute("statusList", statusList);
				request.setAttribute("vanillaProgramStatusList", statusList);
				request.setAttribute("typeOfLoanList", typeOfLoanList);
				request.setAttribute("customerNameList", customerNameList);
				request.setAttribute("customerTypeList", customerTypeList);
				request.setAttribute("obligationTypeList", obligationTypeList);
				FieldVerificationDAO fieldVerificationdao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
		        logger.info("Implementation class: "+fieldVerificationdao.getClass()); 	// changed by asesh	
				ArrayList verifMethodList = fieldVerificationdao.getVerifMethodListList();
				request.setAttribute("verifMethodList", verifMethodList);
				 /*Code by arun For addition of field in obligation End here*/
		 return mapping.getInputForward();
	}
	
	public ActionForward updateObligationDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("In updateObligationDetail()");
		  	boolean flag =false;
		    HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
		    String userId="";
			String bgDate="";
		
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bgDate=userobj.getBusinessdate();
			}else{
				logger.info("here in updateObligationDetail method of ObligationProcessAction action the session is out----------------");
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
			FundFlowAnalysisDAO fundFlowAnalysisDAO=(FundFlowAnalysisDAO)DaoImplInstanceFactory.getDaoImplInstance(FundFlowAnalysisDAO.IDENTITY);
			logger.info("Implementation class: "+fundFlowAnalysisDAO.getClass());
			DynaValidatorForm obligationDynaValidatorForm = (DynaValidatorForm) form;
			ObligationVo vo=new ObligationVo();
		    org.apache.commons.beanutils.BeanUtils.copyProperties(vo, obligationDynaValidatorForm);
	    
	
		    
		    if(vo.getCustomerType().equalsIgnoreCase("APPLICANT"))
				  vo.setCustomerType("PRAPPL");
			  else if(vo.getCustomerType().equalsIgnoreCase("CO APPLICANT"))
				  vo.setCustomerType("COAPPL");
		    
			vo.setUserId(userId);
			vo.setBusinessDate(bgDate);
		
		String dealId="";
	    if(session.getAttribute("fundFlowDealId")!=null)
		 {
			 dealId = session.getAttribute("fundFlowDealId").toString();
			 vo.setDealId(dealId);
		 }
		if(request.getParameter("obligationId")!=null)
		{
			vo.setObligationId(request.getParameter("obligationId"));
			logger.info("obligationId....................."+request.getParameter("obligationId"));
		}
		
		 SimpleDateFormat dateFormat1 = new SimpleDateFormat(dateForDisbursal);
			
		 Date maturityDate =null; 
		  Date businessDate = null;
		  businessDate = dateFormat1.parse(bgDate);
		  boolean maturityFlag=false;
		  if(CommonFunction.checkNull(vo.getTypeOfLoan()).equalsIgnoreCase("CC/OD Loan"));
		  {
				maturityFlag=true;
		  }
		if(!maturityFlag || !CommonFunction.checkNull(vo.getMaturityDate()).equalsIgnoreCase(""))
		{
				   maturityDate = dateFormat1.parse(vo.getMaturityDate()); 
				   maturityFlag=false;
		}
			System.out.println("CC/OD Loan---"+vo.getTypeOfLoan());
			
		if( maturityFlag || maturityDate.after(businessDate) || maturityDate.equals(businessDate))
		{
			String status=fundFlowAnalysisDAO.updateObligation(vo);
		    if(CommonFunction.checkNull(status).equalsIgnoreCase("saved"))
		    {
		    	request.setAttribute("sms", "S");
		    }else if(CommonFunction.checkNull(status).equalsIgnoreCase("already")){
		    	request.setAttribute("sms", "A");
		    }else {
		    	request.setAttribute("sms", "E");
		    }
		}
		else
		{
			logger.info("maturity date less than business date");
			ArrayList obligationDetail =new ArrayList();
			obligationDetail.add(vo);
			request.setAttribute("obligationDetail", obligationDetail);  
			
			request.setAttribute("sms", "M");
			
			//request.setAttribute("insert", "insert"); 
		}
	     
	    String recStatus="";
		if(session.getAttribute("fundFlowAuthor")!=null && session.getAttribute("fundFlowAuthor").toString().equalsIgnoreCase("A"))
		{
	    	recStatus="F";
			
		}
		else
		{
			recStatus="P";
			
		}   
	    ArrayList obligationList = fundFlowAnalysisDAO.getObligationDetails("",dealId,recStatus);
	    if(obligationList.size()>0)
	    {
	    	request.setAttribute("obligationList", obligationList);
	    }
		  /*Code by arun For addition of field in obligation*/
	    IndividualFinancialAnalysisDAO indvidao=(IndividualFinancialAnalysisDAO)DaoImplInstanceFactory.getDaoImplInstance(IndividualFinancialAnalysisDAO.IDENTITY);
	  	logger.info("Implementation class: "+indvidao.getClass()); 	// changed by asesh
		ArrayList customerTypeList = indvidao.getCustomerTypeList(dealId);
		ArrayList obligationTypeList = indvidao.getObligationTypeList();
		ArrayList customerNameList = indvidao.getCustomerName(dealId, "PRAPPL");
		ArrayList typeOfLoanList = indvidao.getTypeOfLoan();
		ArrayList statusList = indvidao.getGenericMasterInfoList("STATUS");
		ArrayList mobList = indvidao.getGenericMasterInfoList("MOB");
		request.setAttribute("mobList", mobList);
		request.setAttribute("statusList", statusList);
		request.setAttribute("vanillaProgramStatusList", statusList);
		request.setAttribute("typeOfLoanList", typeOfLoanList);
		request.setAttribute("customerNameList", customerNameList);
		request.setAttribute("customerTypeList", customerTypeList);
		request.setAttribute("obligationTypeList", obligationTypeList);
		FieldVerificationDAO fieldVerificationdao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
        logger.info("Implementation class: "+fieldVerificationdao.getClass()); 	// changed by asesh	
		ArrayList verifMethodList = fieldVerificationdao.getVerifMethodListList();
		request.setAttribute("verifMethodList", verifMethodList);
		 /*Code by arun For addition of field in obligation End here*/
	  return mapping.getInputForward();
	}

}