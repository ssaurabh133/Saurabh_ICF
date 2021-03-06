/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cp.actions;

import java.text.DecimalFormat;
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
import com.cp.dao.CreditProcessingDAO;
import com.cp.dao.FieldVerificationDAO;
import com.cp.fundFlowDao.FundFlowAnalysisDAO;
import com.cp.vo.BankAccountAnalysisVo;
import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class BankAccountAnalysisAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(BankAccountAnalysisAction.class.getName());
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	public ActionForward saveBankAccountAnalysisDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
			 
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
				logger.info("here in saveBankAccountAnalysisDetails method of BankAccountAnalysisAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
			ServletContext context = getServlet().getServletContext();
			String strFlag="";	
			String dealId="";
			if(sessionId!=null)
			{
				strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			}
			
			logger.info("strFlag ..............1 "+strFlag);
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
		DynaValidatorForm bankAccountAnalysisDynaValidatorForm = (DynaValidatorForm) form;
		logger.info("Value:::::????"+bankAccountAnalysisDynaValidatorForm.get("limitObligation"));
		logger.info("Value:::::????"+bankAccountAnalysisDynaValidatorForm.get("chequeBouncing"));
		/*Code added by Arun*/
		/*Changed by asesh*/
		FieldVerificationDAO fieldVerificationdao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
        logger.info("Implementation class: "+fieldVerificationdao.getClass()); 
        //FieldVerificationDAO fieldVerificationdao=new FieldVerificationDAOImpl();
		/*Changed by asesh*/
		 ArrayList verifMethodList = fieldVerificationdao.getVerifMethodListList();
		 request.setAttribute("verifMethodList", verifMethodList);
		 /*Code added by Arun*/
		BankAccountAnalysisVo vo=new BankAccountAnalysisVo();
	    org.apache.commons.beanutils.BeanUtils.copyProperties(vo, bankAccountAnalysisDynaValidatorForm);
	    
	    if(vo.getCustomerType().equalsIgnoreCase("APPLICANT"))
			  vo.setCustomerType("PRAPPL");
		  else if(vo.getCustomerType().equalsIgnoreCase("CO APPLICANT"))
			  vo.setCustomerType("COAPPL");

	   logger.info("cheque in wards:::::::::::::::>>>>"+vo.getChequeBouncing());
	   logger.info("limit obligation::::::::::::::>>>>"+vo.getLimitObligation());
		
		vo.setUserId(userId);
		vo.setBusinessDate(bgDate);
		String exist="";
		
		String recStatus="";
		exist = ConnectionDAO.singleReturn("select count(BANK_ANALYSIS_ID) from cr_bank_analysis_dtl where BANK_NAME="+vo.getLbxBankID()+" " +
				"and BANK_BRANCH="+vo.getLbxBranchID()+" and ACCOUNT_NO="+vo.getAccountNo()+" and ACCOUNT_TYPE="+vo.getAccountType()+" " +
						"and STATEMENT_YEAR="+vo.getYear()+" and STATEMENT_MONTH="+vo.getMonth()+" and DEAL_ID="+vo.getLbxDealNo()+" ");
		logger.info(exist);
		//if(Integer.parseInt(myFormatter.parse(vo.getHighestBalance()).toString()) < Integer.parseInt(myFormatter.parse(vo.getLowestBalance()).toString()))
		String hbal=CommonFunction.checkNull((vo.getHighestBalance()));
		String lbal=CommonFunction.checkNull((vo.getLowestBalance()));
		double higBal=0.00;
		double lowBal=0.00;
		if(!hbal.equalsIgnoreCase(""))
		{
			higBal=Double.parseDouble(myFormatter.parse(vo.getHighestBalance()).toString());
		}
		if(!lbal.equalsIgnoreCase(""))
		{
			lowBal=Double.parseDouble(myFormatter.parse(vo.getLowestBalance()).toString());
		}
		if(higBal < lowBal)
		{
			//logger.info("highest balance .................................. "+Integer.parseInt(myFormatter.parse(vo.getHighestBalance()).toString()));
			//logger.info("Lowest balance .................................. "+Integer.parseInt(myFormatter.parse(vo.getLowestBalance()).toString()));
			request.setAttribute("sms", "B");
			recStatus="P";
		
			ArrayList bankAcAnalysisList = fundFlowAnalysisDAO.getBankAccountAnalysisDetails("",vo.getLbxDealNo(),recStatus);
			if(bankAcAnalysisList.size()>0)
			{
				request.setAttribute("bankAcAnalysisList", bankAcAnalysisList);
				
			}
		}
		else if(higBal<0 || higBal<0.00)
		{
			recStatus="P";
			request.setAttribute("sms", "Z");
			ArrayList bankAcAnalysisList = fundFlowAnalysisDAO.getBankAccountAnalysisDetails("",vo.getLbxDealNo(),recStatus);
			if(bankAcAnalysisList.size()>0)
			{
				request.setAttribute("bankAcAnalysisList", bankAcAnalysisList);
				
			}
		}
		else if(!CommonFunction.checkNull(exist).equalsIgnoreCase("0"))
		{
			recStatus="P";
			request.setAttribute("sms", "C");
			ArrayList bankAcAnalysisList = fundFlowAnalysisDAO.getBankAccountAnalysisDetails("",vo.getLbxDealNo(),recStatus);
			if(bankAcAnalysisList.size()>0)
			{
				request.setAttribute("bankAcAnalysisList", bankAcAnalysisList);
				
			}
		}
		else
		{
			if(vo.getLbxDealNo()!=null)
			{
				session.setAttribute("fundFlowDealId", vo.getLbxDealNo());
				dealId=vo.getLbxDealNo();
				session.setAttribute("fundFlowDealNo", vo.getDealNo());
				session.setAttribute("customerName", vo.getCustomerName());
				logger.info("deal ID.class............................... "+vo.getLbxDealNo());
			}
			
			int bankAccountId=fundFlowAnalysisDAO.saveBankAccountAnalysis(vo);
			
			 if(session.getAttribute("fundFlowAuthor")!=null && session.getAttribute("fundFlowAuthor").toString().equalsIgnoreCase("A"))
				{
			    	recStatus="F";
					
				}
				else
				{
					recStatus="P";
					
				}  
			if(bankAccountId > 0 )
			{
				
				ArrayList bankAcAnalysisList = fundFlowAnalysisDAO.getBankAccountAnalysisDetails("",vo.getLbxDealNo(),recStatus);
				request.setAttribute("bankAcAnalysisList", bankAcAnalysisList);
				request.setAttribute("sms", "S");
				String status = CommonFunction.checkNull(request.getParameter("status"));
				CreditProcessingDAO creditProcessing=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		        logger.info("Implementation class: "+creditProcessing.getClass()); 			// changed by asesh
			    //CreditProcessingDAO creditProcessing = new CreditProcessingDAOImpl();
			    if ((dealId != null && !dealId.equals(""))
						&& (status == null || status.equals(""))) {

					ArrayList<Object> leadInfo = creditProcessing.getLeadEntryList(dealId);

					ArrayList dealHeader = creditProcessing.getDealHeader(dealId);
					session.setAttribute("dealHeader", dealHeader);

					session.setAttribute("dealId", dealId);
					logger.info("Size of header: " + dealHeader.size());
					logger.info("Size of leadInfo: " + leadInfo.size());
					session.setAttribute("leadInfo", leadInfo);
					session.removeAttribute("viewDeal");
				}

				else if ((dealId != null && !dealId.equals(""))
						&& (status != null && status.equals("UWA"))) {
					logger.info(" CreditProcessiongLeadEntryAction in leadEntryCapturing For view and set viewDeal in session");
					ArrayList<Object> leadInfo = creditProcessing.getLeadEntryList(dealId);
					session.setAttribute("dealId", dealId);
					session.setAttribute("leadInfo", leadInfo);
					session.setAttribute("viewDeal", "UWA");

				} else {
					session.removeAttribute("viewDeal");
					session.removeAttribute("leadInfo");
					session.removeAttribute("dealId");
					session.removeAttribute("dealHeader");
					session.removeAttribute("subIndustryList");
					session.removeAttribute("relationalManagerList");
				}
				
//				CommonDealVo cvo = new CommonDealVo();
//				cvo.setLbxProductID("");
//				cvo.setLbxscheme("");
//				if(session.getAttribute("fundFlowAuthor")!=null && session.getAttribute("fundFlowAuthor").toString().equalsIgnoreCase("A"))
//				{
//					cvo.setRecStatus("F");
//					cvo.setFundFlowDealStatus("F");
//				}
//				else
//				{
//					cvo.setRecStatus("P");
//					cvo.setFundFlowDealStatus("P");
//				}
//				ArrayList<CommonDealVo> fundFlowBehindDetails = fundFlowAnalysisDAO.fundFlowSearchGetDetailBehind(cvo,request);
//				if(fundFlowBehindDetails.size()>0)
//				{
//					logger.info("FundFlowAnalysisSearchBehindAction....list: "+fundFlowBehindDetails.size());
//				    request.setAttribute("list", fundFlowBehindDetails);
//				    
//				}
				
			}
			else
			{
				session.removeAttribute("fundFlowDealId");
				session.removeAttribute("fundFlowDealNo");
				session.removeAttribute("customerName");
				request.setAttribute("sms", "E");
			}
			ArrayList yearList = fundFlowAnalysisDAO.getYears(bgDate);
			 request.setAttribute("yearList", yearList);
			
			return mapping.getInputForward();
		}
		
			//logger.info("customer name is .................................... "+vo.getCustomerName());
			ArrayList bankAcAnalysisDetail =new ArrayList();
			String customerType ="";
			 if(vo.getCustomerType().equalsIgnoreCase("PRAPPL"))
				  customerType="APPLICANT";
			  else if(customerType.equalsIgnoreCase("COAPPL"))
				  customerType="CO APPLICANT";
			 vo.setCustomerType(customerType);
			bankAcAnalysisDetail.add(vo);
			request.setAttribute("bankAcAnalysisDetail", bankAcAnalysisDetail); 
			request.setAttribute("insert", "insert");
			
			ArrayList bankAcAnalysisList = fundFlowAnalysisDAO.getBankAccountAnalysisDetails("",vo.getLbxDealNo(),recStatus);
			if(bankAcAnalysisList.size()>0)
			{
				request.setAttribute("bankAcAnalysisList", bankAcAnalysisList);
				
			}
			ArrayList yearList = fundFlowAnalysisDAO.getYears(bgDate);
			 request.setAttribute("yearList", yearList);
	
	  return mapping.getInputForward();
	}
	
	public ActionForward deleteBankAccountAnalysis(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		 LoggerMsg.info("In deleteBankAccountAnalysis ");
		
		  boolean flag =false;
		    HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
		String bgDate="";
			
			if(userobj!=null)
			{					
				bgDate=userobj.getBusinessdate();					
			}else{
				logger.info("here in deleteBankAccountAnalysis method of BankAccountAnalysisAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
			ServletContext context = getServlet().getServletContext();
			String strFlag="";	
			if(sessionId!=null)
			{
				strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			}
			
			logger.info("strFlag ..............2 "+strFlag);
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
		 int bankAccountId=0;
		 String dealId ="";
		 if(session.getAttribute("fundFlowDealId")!=null)
		 {
			 dealId = session.getAttribute("fundFlowDealId").toString();
		 }
		 /*Code added by Arun*/
		 /*Changed by asesh*/
			FieldVerificationDAO fieldVerificationdao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
	        logger.info("Implementation class: "+fieldVerificationdao.getClass());
		 /*Changed by asesh*/	
		 ArrayList verifMethodList = fieldVerificationdao.getVerifMethodListList();
		 request.setAttribute("verifMethodList", verifMethodList);
			 /*Code added by Arun*/
		 
		 FundFlowAnalysisDAO dao=(FundFlowAnalysisDAO)DaoImplInstanceFactory.getDaoImplInstance(FundFlowAnalysisDAO.IDENTITY);
		 logger.info("Implementation class: "+dao.getClass());
		 String FundFlow[] = request.getParameterValues("chk");
		 for(int k=0;k<FundFlow.length;k++)
		 {
			 status = dao.deleteBankAnalysis(FundFlow[k]);
		 }
		 
		 if(status>0)
		 {
			 request.setAttribute("sms", "D"); 
			
     	  }
		  else
		  {
				 request.setAttribute("sms", "DE"); 
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
		 ArrayList bankAcAnalysisList = dao.getBankAccountAnalysisDetails("",dealId,recStatus);
		 if(bankAcAnalysisList.size()>0)
		 {
			 request.setAttribute("bankAcAnalysisList", bankAcAnalysisList);
			 
		 }
		 else
		 {
			 request.setAttribute("bankAcAnalysisListSize", "0");
		 }
		
	
		 ArrayList yearList = dao.getYears(bgDate);
		 request.setAttribute("yearList", yearList);
		 logger.info("bankAcAnalysisList.....................size : "+bankAcAnalysisList.size());
		return mapping.getInputForward();
	}
	
	
	public ActionForward getBankAcAnalysis(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		 LoggerMsg.info("In getBankAcAnalysis .... ");
		
		  boolean flag =false;
		    HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String bgDate="";
			 
			if(userobj!=null)
			{					
				bgDate=userobj.getBusinessdate();					
			}else{
				logger.info("here in getBankAcAnalysis method of action BankAccountAnalysisAction  the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
			ServletContext context = getServlet().getServletContext();
			String strFlag="";	
			if(sessionId!=null)
			{
				strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			}
			
			logger.info("strFlag ..............3 "+strFlag);
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
		 String bankAccountId="";
		 String dealId ="";
		 if(request.getParameter("dealId")!=null)
		 {
			 dealId=request.getParameter("dealId");
		 }
		 else
		 if(session.getAttribute("fundFlowDealId")!=null)
		 {
			 dealId = session.getAttribute("fundFlowDealId").toString();
		 }
		 
		 if(request.getParameter("bankAcAnalysisId")!=null)
		 {
			 bankAccountId=request.getParameter("bankAcAnalysisId");
		 }
		 FundFlowAnalysisDAO dao=(FundFlowAnalysisDAO)DaoImplInstanceFactory.getDaoImplInstance(FundFlowAnalysisDAO.IDENTITY);
		 logger.info("Implementation class: "+dao.getClass());
		 String recStatus="";
		 if(session.getAttribute("fundFlowAuthor")!=null && session.getAttribute("fundFlowAuthor").toString().equalsIgnoreCase("A"))
			{
		    	recStatus="F";
				
			}
			else
			{
				recStatus="P";
				
			}  
		  if(session.getAttribute("underWriterViewData")!=null)
		    {
		    	recStatus="A";
		    }
		 ArrayList bankAcAnalysisList = dao.getBankAccountAnalysisDetails(bankAccountId+"",dealId,recStatus);
		 if(bankAcAnalysisList.size()>0)
		 {
		 request.setAttribute("bankAcAnalysisDetail", bankAcAnalysisList);
		 }
		 
		bankAcAnalysisList = dao.getBankAccountAnalysisDetails("",dealId,recStatus);
		 if(bankAcAnalysisList.size()>0)
		 {
			 request.setAttribute("bankAcAnalysisList", bankAcAnalysisList);
		 }
		
		
		 session.setAttribute("fundFlowDealId", dealId);
		 ArrayList yearList = dao.getYears(bgDate);
		 request.setAttribute("yearList", yearList);
		
		 LoggerMsg.info("bankAcAnalysisDetail size is .... "+bankAcAnalysisList.size());
		 /*Code added by Arun*/
		 /*Changed by asesh*/
			FieldVerificationDAO fieldVerificationdao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
	        logger.info("Implementation class: "+fieldVerificationdao.getClass()); 
		 /*Changed by asesh*/	
		 ArrayList verifMethodList = fieldVerificationdao.getVerifMethodListList();
		 request.setAttribute("verifMethodList", verifMethodList);
			 /*Code added by Arun*/
		 return mapping.getInputForward();
	}
	
	public ActionForward updateBankAcAnalysis(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("here in updateBankAcAnalysis method of BankAccountAnalysisAction ");
		  	boolean flag =false;
		    HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
		    String userId="";
			String bgDate="";
			
			if(userobj!=null)
			{		userId=userobj.getUserId();
					bgDate=userobj.getBusinessdate();				
			} else{
				logger.info("here in updateBankAcAnalysis method of BankAccountAnalysisAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
			ServletContext context = getServlet().getServletContext();
			String strFlag="";	
			if(sessionId!=null)
			{
				strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			}
			
			logger.info("strFlag ..............4 "+strFlag);
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
		DynaValidatorForm bankAccountAnalysisDynaValidatorForm = (DynaValidatorForm) form;
		BankAccountAnalysisVo vo=new BankAccountAnalysisVo();
	    org.apache.commons.beanutils.BeanUtils.copyProperties(vo, bankAccountAnalysisDynaValidatorForm);
	    
	    if(vo.getCustomerType().equalsIgnoreCase("APPLICANT"))
			  vo.setCustomerType("PRAPPL");
		  else if(vo.getCustomerType().equalsIgnoreCase("CO APPLICANT"))
			  vo.setCustomerType("COAPPL");
	   
		
		vo.setUserId(userId);
		vo.setBusinessDate(bgDate);
		/*Code added by Arun*/
		/*Changed by asesh*/
		FieldVerificationDAO fieldVerificationdao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
        logger.info("Implementation class: "+fieldVerificationdao.getClass()); 
		/*Changed by asesh*/	
		 ArrayList verifMethodList = fieldVerificationdao.getVerifMethodListList();
		 request.setAttribute("verifMethodList", verifMethodList);
		/*Code added by Arun*/
		if(request.getParameter("bankAcAnalysisId")!=null)
		{
			vo.setBankAcAnId(request.getParameter("bankAcAnalysisId"));
		}
		
	    boolean status=fundFlowAnalysisDAO.updateBankAccountAnalysis(vo);
	    if(status)
	    {
	    	
	    	request.setAttribute("sms", "S");
	    }
	    else
	    {
	    	request.setAttribute("sms", "E");
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
	    ArrayList bankAcAnalysisList = fundFlowAnalysisDAO.getBankAccountAnalysisDetails("",dealId,recStatus);
	    if(bankAcAnalysisList.size()>0)
	    {
	    	request.setAttribute("bankAcAnalysisList", bankAcAnalysisList);
	    }
	    ArrayList yearList = fundFlowAnalysisDAO.getYears(bgDate);
		 request.setAttribute("yearList", yearList);
	  return mapping.getInputForward();
	}
	
}