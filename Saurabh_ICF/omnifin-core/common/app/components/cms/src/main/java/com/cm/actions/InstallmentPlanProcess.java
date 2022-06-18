package com.cm.actions;

import java.text.DecimalFormat;
import java.util.ArrayList;
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

import com.cm.dao.LoanInitiationDAO;
import com.cm.vo.InstallmentPlanForCMVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.connect.PrepStmtObject;
import com.cp.dao.CreditProcessingDAO;
import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.tabDependencyCheck.RefreshFlagValueInsert;
import com.tabDependencyCheck.RefreshFlagVo;



   public class InstallmentPlanProcess extends DispatchAction {
	private static final Logger logger = Logger.getLogger(InstallmentPlanProcess.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	
	public ActionForward saveInstallmentPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {

	

	HttpSession session = request.getSession();

	boolean flag=false;
	UserObject userobj=(UserObject)session.getAttribute("userobject");
	 String makerID="";
		String bDate ="";
		if(userobj!=null){
			makerID= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in saveInstallmentPlan method of InstallmentPlanProcess action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
	DynaValidatorForm InstallmentPlanDynaValidatorForm = (DynaValidatorForm)form;
	LoggerMsg.info("In installmentPlanProcess ");
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
	String dealId = "";

	if (session.getAttribute("dealId") != null) {

		dealId = session.getAttribute("dealId").toString();
	} else if (session.getAttribute("maxId") != null) {
		dealId = session.getAttribute("maxId").toString();
	}
	String dealLoanId = "";
	String facilityFlag = "N";

	facilityFlag = CommonFunction.checkNull((String)session.getAttribute("facilityFlag"));
	if(CommonFunction.checkNull(facilityFlag).equalsIgnoreCase("")){
		String functionId=CommonFunction.checkNull((String)session.getAttribute("functionId"));
		if(CommonFunction.checkNull(functionId).equalsIgnoreCase("3000296") || CommonFunction.checkNull(functionId).equalsIgnoreCase("500000123")){
			facilityFlag = "Y";
		}
	}
	
	if (session.getAttribute("dealLoanId") != null) {
		dealLoanId = session.getAttribute("dealLoanId").toString();
		facilityFlag = session.getAttribute("facilityFlag").toString();
	} else if (request.getParameter("dealLoanId") != null) {
		session.removeAttribute("dealLoanId");   
		dealLoanId = request.getParameter("dealLoanId").toString();
		session.setAttribute("dealLoanId",dealLoanId);
		session.setAttribute("facilityFlag",facilityFlag);
	}
	logger.info("In InstallmentPlanProcess(saveInstallmentPlan) dealId: " + dealId);

	String loanId = "";
	if (session.getAttribute("loanId") != null) {

		loanId = session.getAttribute("loanId").toString();
	} else if (session.getAttribute("maxIdInCM") != null) {
		loanId = session.getAttribute("maxIdInCM").toString();
	}
	
	logger.info("In saveInstallmentPlan loan id: " + loanId);
	 String sms="";
	 	if ((loanId != null && !loanId.equalsIgnoreCase("")))
		{
			 InstallmentPlanForCMVO ipvo=new InstallmentPlanForCMVO();
			org.apache.commons.beanutils.BeanUtils.copyProperties(ipvo, InstallmentPlanDynaValidatorForm);
			 String dealIdQ="select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID="+loanId;
			 String loanDealId=ConnectionDAO.singleReturn(dealIdQ);
			 logger.info("Loan Deal Id in cr_loan_dtl: "+loanDealId);
			 // Code added for Edit Due Date| Rahul papneja | 29012018
			 String editDueDate =CommonFunction.checkNull((String)request.getParameter("editDueDate"));
            // Ends Here| Rahul papneja			 
			// added one filed for auto file due date in installment plan|DEAL_REPAYMENT_FREQ|Vijendra Singh	
			 String frequency= CommonFunction.checkNull((String)request.getParameter("frequency"));								
			 String maxDate=CommonFunction.checkNull((String)request.getParameter("maxDate"));										
			// End code| Vijendra Singh
				
			 ipvo.setLoanId(loanId);
			 ipvo.setDealId(loanDealId);
			 ipvo.setTxnType("LIM");
			 
			 // Code Added for Edit Due Date| Rahul Papneja | 29012018
			 ipvo.setEditDueDate(editDueDate);
			 // Ends Here

			 ipvo.setMakerId(makerID);
			 ipvo.setMakerDate(bDate);

				LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
			 boolean status=false;
			 String installmentType=(CommonFunction.checkNull((String)request.getParameter("installmentType")));
				logger.info(" InstallmentType: " + installmentType);
				// Code Added for Edit Due Date | Rahul papneja | 29012018
				
				if((CommonFunction.checkNull(installmentType).equalsIgnoreCase("E")  || CommonFunction.checkNull(installmentType).equalsIgnoreCase("P"))&& CommonFunction.checkNull(editDueDate).equalsIgnoreCase("N"))
					// Ends Here| Rahul papneja
				{
				status = dao.saveInstallPlan(ipvo);
				}
				else{
				status = dao.saveInstallPlan(ipvo);
				}
				if(status)
				 {
					sms="S";
						RefreshFlagVo vo = new RefreshFlagVo();
						vo.setTabIndex(5);
		    			vo.setRecordId(Integer.parseInt(loanId.trim()));
		        		vo.setModuleName("CM");
		    			RefreshFlagValueInsert.updateRefreshFlag(vo);

				 }
				else
				{
					sms="E";
				}

			ArrayList installmentList=dao.getInstallType(loanId);
			logger.info("installmentList    Size:---"+installmentList.size());
			if(installmentList!=null && installmentList.size()>0)
			{
			   InstallmentPlanForCMVO vo=(InstallmentPlanForCMVO)installmentList.get(0);
				logger.info("Installment Type in cr_loan_dtl table: "+vo.getInstallmentType());
				logger.info("Installment Type in cr_loan_dtl table: "+vo.getLoanAmount());
				String loanAmount=CommonFunction.checkNull(vo.getLoanAmount());
			   logger.info("getRateType Type in cr_loan_dtl table: "+vo.getRateType());
				String rateType=CommonFunction.checkNull(vo.getRateType());
				String totalInstallment=CommonFunction.checkNull(vo.getTotalInstallment());
				logger.info("In InstallmentPlanBehindAction InstallmentType: " + installmentType);									
				// Code added for Edit Due Date | Rahul papneja | 29012018
				String insNextDueDate=CommonFunction.checkNull(vo.getInsNextDueDate());
				logger.info("In InstallmentPlanBehindAction InstallmentType: " + installmentType);				
				if((CommonFunction.checkNull(installmentType).equalsIgnoreCase("E")  || CommonFunction.checkNull(installmentType).equalsIgnoreCase("P"))&& CommonFunction.checkNull(editDueDate).equalsIgnoreCase("N")){
	        	// Ends Here| Rahul papneja
					request.setAttribute("viewMode", "viewMode");

			}
			request.setAttribute("installmentList", installmentList);
			logger.info("In InstallmentPlanBehindAction installmentList: ") ;
	        request.setAttribute("installmentType", installmentType);
	        request.setAttribute("totalInstallment", totalInstallment);
	        request.setAttribute("rateType", rateType);
	        request.setAttribute("loanAmount", loanAmount);
	        // COde Added for Edit Due Date | Rahul papneja | 29012018
	        request.setAttribute("editDueDate",editDueDate);
	        request.setAttribute("insNextDueDate",insNextDueDate);
	        // ENds Here
	        // added one filed for auto file due date in installment plan|DEAL_REPAYMENT_FREQ|Vijendra Singh	
	        request.setAttribute("frequency",frequency);				
	        request.setAttribute("maxDate",maxDate);
	        // End code| Vijendra Singh
			}
		}
		if ((dealId != null && !dealId.equalsIgnoreCase("")))
		{
			 InstallmentPlanForCMVO ipvo=new InstallmentPlanForCMVO();
			 org.apache.commons.beanutils.BeanUtils.copyProperties(ipvo, InstallmentPlanDynaValidatorForm);
			String loanIdQ="select DEAL_LOAN_ID from cr_deal_loan_dtl where DEAL_ID="+dealId;
			 String loanDealId=ConnectionDAO.singleReturn(loanIdQ);
			 logger.info("Loan loandeal Id in cr_deal_loan_dtl: "+loanDealId);
       // Code added for Edit Due date| Rahul papneja | 25012018
			 String editDueDate =CommonFunction.checkNull((String)request.getParameter("editDueDate"));
			 //Ends Here
			// added one filed for auto file due date in installment plan|DEAL_REPAYMENT_FREQ|Vijendra Singh	
			 String frequency=CommonFunction.checkNull((String)request.getParameter("frequency"));								
			 String maxDate=CommonFunction.checkNull((String)request.getParameter("maxDate"));		
			// End code| Vijendra Singh
			 ipvo.setLoanId(dealLoanId);
			 ipvo.setDealId(dealId);
			 ipvo.setTxnType("DC");
			 //Code Added for Edir Due Date| Rahul papneja| 25012018
			 
			 ipvo.setEditDueDate(editDueDate);
			 // Ends Here| Rahul papneja

			 ipvo.setMakerId(makerID);
			 ipvo.setMakerDate(bDate);
			 CreditProcessingDAO detail1=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		     logger.info("Implementation class: "+detail1.getClass()); 
			 //CreditProcessingDAO detail1 = new CreditProcessingDAOImpl();
			 boolean status=false;
				String installmentType=(CommonFunction.checkNull(request.getParameter("installmentType")));
				logger.info(" InstallmentType: " + installmentType);
				// Code Added for Edit Due date| Rahul papneja| 25012018
				if((CommonFunction.checkNull(installmentType).equalsIgnoreCase("E")  || CommonFunction.checkNull(installmentType).equalsIgnoreCase("P"))&& CommonFunction.checkNull(editDueDate).equalsIgnoreCase("N"))
					// Ends Here| Rahul papneja
				{

					if("Y".equalsIgnoreCase(facilityFlag)){
						boolean RefreshFlag =CheckFacilityFlag(dealLoanId,dealId);
						String refFlag=getFacilityFlag(dealLoanId,dealId,"2");
						if(RefreshFlag){
							status = saveInstallPlanInFacility(ipvo);
						}else{
							sms="I";
						}
					}else{
					status = detail1.saveInstallPlanInDeal(ipvo);
					}

				}
				else{

					if("Y".equalsIgnoreCase(facilityFlag)){
						boolean RefreshFlag = CheckFacilityFlag(dealLoanId,dealId);
						String refFlag= getFacilityFlag(dealLoanId,dealId,"2");
						if(RefreshFlag){
					status =  saveInstallPlanInFacility(ipvo);
						}else{
							sms="I";
						}
					}else{
					status = detail1.saveInstallPlanInDeal(ipvo);
				}
				}
				if(status)
				 {
					sms="S";
						RefreshFlagVo vo = new RefreshFlagVo();
						vo.setTabIndex(8);
		    			vo.setRecordId(Integer.parseInt(dealId));
		        		vo.setModuleName("CP");
		    			RefreshFlagValueInsert.updateRefreshFlag(vo);

				 }
				else
				{
					sms="E";
				}
				ArrayList installmentList = new ArrayList();
				if("Y".equalsIgnoreCase(facilityFlag)){
					 installmentList=getInstallTypeInFacility(dealId,dealLoanId);
						}else{
							 installmentList=detail1.getInstallTypeInDeal(dealId);
						}	
			//ArrayList installmentList=detail1.getInstallTypeInDeal(dealId);
			logger.info("installmentList    Size:---"+installmentList.size());
			if(installmentList!=null && installmentList.size()>0)
			{
			   InstallmentPlanForCMVO vo=(InstallmentPlanForCMVO)installmentList.get(0);
				logger.info("Installment Type in cr_loan_dtl table: "+vo.getInstallmentType());
				 logger.info("Installment Type in cr_deal_loan_dtl table: "+vo.getLoanAmount());
					String loanAmount=CommonFunction.checkNull(vo.getLoanAmount());
					 logger.info("getRateType Type in cr_deal_loan_dtl table: "+vo.getRateType());
					String rateType=CommonFunction.checkNull(vo.getRateType());
				installmentType=CommonFunction.checkNull(vo.getInstallmentType());
				String totalInstallment=CommonFunction.checkNull(vo.getTotalInstallment());
				String insNextDueDate=CommonFunction.checkNull(vo.getInsNextDueDate());
				logger.info("In InstallmentPlanBehindAction InstallmentType: " + installmentType);		
				// Code Added for edit Due Date | Rahul papneja | 25012018
				  if((CommonFunction.checkNull(installmentType).equalsIgnoreCase("E")  || CommonFunction.checkNull(installmentType).equalsIgnoreCase("P"))&& CommonFunction.checkNull(editDueDate).equalsIgnoreCase("N")){
					 // Ends Here| Rahul papneja
				request.setAttribute("viewMode", "viewMode");

			}
			request.setAttribute("installmentList", installmentList);
			request.setAttribute("forNewInstallmentType", "forNewInstallmentType");
			logger.info("In InstallmentPlanBehindAction installmentList: ") ;
	        request.setAttribute("installmentType", installmentType);
	        request.setAttribute("totalInstallment", totalInstallment);
	        request.setAttribute("rateType", rateType);
	        request.setAttribute("loanAmount", loanAmount);
	        // Code Added for Edit Due date| Rahul Papneja| 25012018
	        request.setAttribute("editDueDate",editDueDate);
		    request.setAttribute("insNextDueDate",insNextDueDate);
	        // Ends Here| rahul Papneja
		    // added one filed for auto file due date in installment plan|DEAL_REPAYMENT_FREQ|Vijendra Singh	
	        request.setAttribute("frequency",frequency);				
	        request.setAttribute("maxDate",maxDate);
	        // End code| Vijendra Singh
			}
		}
	 logger.info("status: "+sms);
	 session.removeAttribute("planCheck");
	 if(sms.trim().equals("S"))
		 session.setAttribute("planCheck","Y");
	 else
	 	 session.setAttribute("planCheck","N");
	request.setAttribute("sms", sms);
	return mapping.findForward("success");
}


	public ActionForward saveInstallmentPlanForEmiCal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
	HttpSession session = request.getSession();

	boolean flag=false;
	UserObject userobj=(UserObject)session.getAttribute("userobject");
	 String makerID="";
		String bDate ="";
		if(userobj!=null){
			makerID= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in saveInstallmentPlan method of InstallmentPlanProcess action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
	DynaValidatorForm InstallmentPlanDynaValidatorForm = (DynaValidatorForm)form;
	LoggerMsg.info("In installmentPlanProcess ");
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
	String dealId = "";

	if (session.getAttribute("dealId") != null) {

		dealId = session.getAttribute("dealId").toString();
	} 
	logger.info("In InstallmentPlanProcess(saveInstallmentPlan) dealId: " + dealId);

	String sms="";
	 	
		if ((dealId != null && !dealId.equalsIgnoreCase("")))
		{
			 InstallmentPlanForCMVO ipvo=new InstallmentPlanForCMVO();
			 org.apache.commons.beanutils.BeanUtils.copyProperties(ipvo, InstallmentPlanDynaValidatorForm);
			String loanIdQ="select DEAL_LOAN_ID from cr_deal_loan_dtl_emi_calc where DEAL_ID="+dealId;
			 String loanDealId=ConnectionDAO.singleReturn(loanIdQ);
			 logger.info("loanDealId:::::::::::::::::::::::::::::::::::"+loanDealId);

			 ipvo.setLoanId(loanDealId);
			 ipvo.setDealId(dealId);
			 ipvo.setTxnType("LCL");

			 ipvo.setMakerId(makerID);
			 ipvo.setMakerDate(bDate);

			//CreditProcessingDAO detail1 = new CreditProcessingDAOImpl();
            CreditProcessingDAO detail1=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
            logger.info("Implementation class: "+detail1.getClass()); 		//changed by anil
			 boolean status=false;
				String installmentType=(CommonFunction.checkNull(request.getParameter("installmentType")));
				logger.info(" InstallmentType: " + installmentType);
				if(installmentType.equalsIgnoreCase("E")  || installmentType.equalsIgnoreCase("P"))
				{

					status = detail1.saveInstallPlanForEmiCalc(ipvo);

				}
				else{

					status = detail1.saveInstallPlanForEmiCalc(ipvo);
				}
				if(status)
				 {
					sms="S";				
				 }
				else
				{
					sms="E";
				}

			ArrayList installmentList=detail1.getInstallTypeForEmiCalc(dealId);
			logger.info("installmentList    Size:---"+installmentList.size());
			if(installmentList!=null && installmentList.size()>0)
			{
			   InstallmentPlanForCMVO vo=(InstallmentPlanForCMVO)installmentList.get(0);
			   String loanAmount=CommonFunction.checkNull(vo.getLoanAmount());
			   String rateType=CommonFunction.checkNull(vo.getRateType());
			   installmentType=CommonFunction.checkNull(vo.getInstallmentType());
			   String totalInstallment=CommonFunction.checkNull(vo.getTotalInstallment());
	        if(installmentType.equalsIgnoreCase("E")  || installmentType.equalsIgnoreCase("P")){
				request.setAttribute("viewMode", "viewMode");

			}
			request.setAttribute("installmentList", installmentList);
	        request.setAttribute("installmentType", installmentType);
	        request.setAttribute("totalInstallment", totalInstallment);
	        request.setAttribute("rateType", rateType);
	        request.setAttribute("loanAmount", loanAmount);
			}
		}
	 logger.info("status---------------------------------------->"+sms);
	 session.removeAttribute("planCheck");
	 if(sms.trim().equals("S"))
		 session.setAttribute("planCheck","Y");
	 else
	 	 session.setAttribute("planCheck","N");
	request.setAttribute("sms", sms);
	return mapping.findForward("successForEmiCal");
   }
	
	
	
	public ActionForward saveRSPInstallmentPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {

	

	HttpSession session = request.getSession();

	boolean flag=false;
	UserObject userobj=(UserObject)session.getAttribute("userobject");
	 String makerID="";
		String bDate ="";
		if(userobj!=null){
			makerID= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in saveRSPInstallmentPlan method of InstallmentPlanProcess action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
	DynaValidatorForm InstallmentPlanDynaValidatorForm = (DynaValidatorForm)form;
	LoggerMsg.info("In installmentPlanProcess ");
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
	

	String loanId = "";
	if (session.getAttribute("loanId") != null) {

		loanId = session.getAttribute("loanId").toString();
	} else if (session.getAttribute("maxIdInCM") != null) {
		loanId = session.getAttribute("maxIdInCM").toString();
	}
	logger.info("In saveInstallmentPlan loan id: " + loanId);
	 String sms="";
	 	if ((loanId != null && !loanId.equalsIgnoreCase("")))
		{
			 InstallmentPlanForCMVO ipvo=new InstallmentPlanForCMVO();
			org.apache.commons.beanutils.BeanUtils.copyProperties(ipvo, InstallmentPlanDynaValidatorForm);
			 String dealIdQ="select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID="+loanId;
			 String loanDealId=ConnectionDAO.singleReturn(dealIdQ);
			 logger.info("Loan Deal Id in cr_loan_dtl: "+loanDealId);
			 ipvo.setLoanId(loanId);
			 ipvo.setDealId(loanDealId);
			 ipvo.setTxnType("LIM");

			 ipvo.setMakerId(makerID);
			 ipvo.setMakerDate(bDate);

				LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
				String installmentType=(CommonFunction.checkNull(request.getParameter("installmentType")));
				
				String customerRSPId="";
				if (session.getAttribute("customerRSPId") != null) {

					customerRSPId = session.getAttribute("customerRSPId").toString();
				}
				
				ipvo.setCustomerRSPId(customerRSPId);
				
				logger.info(" InstallmentType: " + installmentType);
				
				String status = dao.saveRSPInstallPlan(ipvo);
//				if(installmentType.equalsIgnoreCase("E")  || installmentType.equalsIgnoreCase("P"))
//				{
//				status = dao.saveRSPInstallPlan(ipvo);
//				}
//				else{
//				status = dao.saveRSPInstallPlan(ipvo);
//				}
				if(CommonFunction.checkNull(status).equalsIgnoreCase("S"))
				 {
					sms="S";
						RefreshFlagVo vo = new RefreshFlagVo();
						vo.setTabIndex(5);
		    			vo.setRecordId(Integer.parseInt(loanId.trim()));
		        		vo.setModuleName("CM");
		    			RefreshFlagValueInsert.updateRefreshFlag(vo);
		    			request.setAttribute("forNewInstallmentType", "forNewInstallmentType");

				 }
				else if(CommonFunction.checkNull(status).equalsIgnoreCase("ONEMUSTLEADPARTNER"))
				{
					sms="ONEMUSTLEADPARTNER";
				}
				else{
					sms="E";
				}

			ArrayList installmentList=dao.getRspInstallType(loanId,customerRSPId);
			logger.info("installmentList    Size:---"+installmentList.size());
			if(installmentList!=null && installmentList.size()>0)
			{
			   InstallmentPlanForCMVO vo=(InstallmentPlanForCMVO)installmentList.get(0);
			   installmentType=vo.getInstallmentType();
				logger.info("Installment Type in cr_loan_dtl table: "+vo.getInstallmentType());
				logger.info("Installment Type in cr_loan_dtl table: "+vo.getLoanAmount());
				String loanAmount=CommonFunction.checkNull(vo.getLoanAmount());
			   logger.info("getRateType Type in cr_loan_dtl table: "+vo.getRateType());
				String rateType=CommonFunction.checkNull(vo.getRateType());
				String totalInstallment=CommonFunction.checkNull(vo.getTotalInstallment());
				logger.info("In InstallmentPlanBehindAction InstallmentType: " + installmentType);
	        if(installmentType.equalsIgnoreCase("E")  || installmentType.equalsIgnoreCase("P")){
				request.setAttribute("viewMode", "viewMode");

			}
	        	        
	        session.setAttribute("installmentList", installmentList);
			logger.info("In InstallmentPlanBehindAction installmentList: ") ;
			request.setAttribute("installmentType", installmentType);
			request.setAttribute("totalInstallment", totalInstallment);
			request.setAttribute("rateType", rateType);
			request.setAttribute("loanAmount", loanAmount);
	        

			}
		}

	 logger.info("status: "+sms);
	 session.removeAttribute("planCheck");
	 if(sms.trim().equals("S"))
		 session.setAttribute("planCheck","Y");
	 else
	 	 session.setAttribute("planCheck","N");
	request.setAttribute("sms", sms);
	return mapping.findForward("successForRSP");
}	
	
	public boolean CheckFacilityFlag(String dealLoanId,String dealId) {
		boolean flag=false;
		try{
		String query="select REFRESH_FLAG_FACILITY from cr_deal_facility_dtl where deal_id='"+dealId+"' and Deal_loan_ID='"+dealLoanId+"' ";
		String res=ConnectionDAO.singleReturn(query);
		logger.info("Query for flag : "+query.toString());
		char facility=res.charAt(0);
		if(facility=='N'){
			flag=true;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public String getFacilityFlag(String dealLoanId, String dealId,String position) {
		String flag="";
		boolean Status=false;
		ArrayList list= new ArrayList();
		try{
		String query="select REFRESH_FLAG_FACILITY from cr_deal_facility_dtl where deal_id='"+dealId+"' and Deal_loan_ID='"+dealLoanId+"' ";
		String res=ConnectionDAO.singleReturn(query);
		
		
			if(CommonFunction.checkNull(res).equalsIgnoreCase("YYY")){
				flag="YNY";
			}
			if(CommonFunction.checkNull(res).equalsIgnoreCase("NYY")){
				flag="NNY";
			}
			if(CommonFunction.checkNull(res).equalsIgnoreCase("NNY")){
				flag="NNY";
			}
			if(CommonFunction.checkNull(res).equalsIgnoreCase("NNN")){
				flag="NNY";
			}
			if(CommonFunction.checkNull(res).equalsIgnoreCase("YNN")){
				flag="YNN";
			}
			if(CommonFunction.checkNull(res).equalsIgnoreCase("YYN")){
				flag="YNN";
			}
			if(CommonFunction.checkNull(res).equalsIgnoreCase("YNY")){
				flag="YNY";
			}
			if(CommonFunction.checkNull(res).equalsIgnoreCase("NYN")){
				flag="NNY";
			}
			
		
		
		if(CommonFunction.checkNull(flag).equalsIgnoreCase("")){
			flag="NNY";
		}
	String  updateQuery="update CR_DEAL_FACILITY_DTL set REFRESH_FLAG_FACILITY='"+flag+"' where deal_id='"+dealId+"' and Deal_loan_ID='"+dealLoanId+"' ";
	list.add(updateQuery);
	logger.info("Refresh Flag Facility ::::: "+flag);
	logger.info("getFacilityFlag Refresh Flag Facility ::::: "+updateQuery);
	 Status=ConnectionDAO.sqlInsUpdDelete(list);
		}catch(Exception e){
			e.printStackTrace();
		}
	return flag;
	}
	public boolean saveInstallPlanInFacility(InstallmentPlanForCMVO ipvo) {
		logger.info("In saveInstallPlanInFacility");
			String FromInstallment[] = ipvo.getFromInstall();
			
			String ToInstallment[] = ipvo.getToInstall();
		
			String RecoveryPercen[] = ipvo.getRecoveryPer();
		
			String InstallmentType= ipvo.getInstallmentType();
	
			String TotalInstallment=ipvo.getTotalInstallment();
			
			String toInstallment = ipvo.getToInstallment();	
		
			String prinAmount[] = ipvo.getPrincipalAmount();
   			
   			String instalAmount[] = ipvo.getInstallmentAmount();
   		
   			String loanId = ipvo.getLoanId();
   		
   			
			ArrayList qryList=new ArrayList();
			boolean status=false;
			try {
				 PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				 StringBuffer bufInsSql =	new StringBuffer();
				 StringBuilder checkQ=new StringBuilder();
				    checkQ.append("select count(*) from CR_DEAL_FACILITY_INSTALLMENT_PLAN where DEAL_ID='"+(CommonFunction.checkNull(ipvo.getDealId()).trim())+"' AND DEAL_LOAN_ID='"+(CommonFunction.checkNull(ipvo.getLoanId()).trim())+"' ");
				   String count=ConnectionDAO.singleReturn(checkQ.toString());
				   
				   checkQ=null;
				   
				   if(!count.equalsIgnoreCase("0"))
					  { 
					  insertPrepStmtObject = new PrepStmtObject();
					  StringBuilder qry=new StringBuilder();
					  qry.append("DELETE FROM CR_DEAL_FACILITY_INSTALLMENT_PLAN WHERE DEAL_ID='"+(CommonFunction.checkNull(ipvo.getDealId()).trim())+ "' AND DEAL_LOAN_ID='"+(CommonFunction.checkNull(ipvo.getLoanId()).trim())+"' ");

				     insertPrepStmtObject.setSql(qry.toString());
					 qryList.add(insertPrepStmtObject);
					// ConnectionDAO.sqlInsUpdDelete(qryList);
					  
					 qry=null;
					  }
			
				 for(int k=0;k<FromInstallment.length;k++)  
				{
					bufInsSql =	new StringBuffer();
			     insertPrepStmtObject = new PrepStmtObject();
				  
				
				  insertPrepStmtObject = new PrepStmtObject();
					bufInsSql.append("insert into CR_DEAL_FACILITY_INSTALLMENT_PLAN (DEAL_ID,DEAL_LOAN_ID,FROM_INSTL_NO,TO_INSTL_NO,RECOVERY_PERCENT,PRINCIPAL_AMOUNT,INSTALLMENT_AMOUNT,REC_STATUS,SEQ_NO,RECOVERY_TYPE,MAKER_ID,MAKER_DATE) values(?,?,?,?,?,?,?,?,?,?,?,DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");
					
					
					if(CommonFunction.checkNull(ipvo.getDealId()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(ipvo.getDealId().trim()); //deal Id
					
					if(CommonFunction.checkNull(ipvo.getLoanId()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(ipvo.getLoanId().trim());   // loan Id
					
					if(CommonFunction.checkNull(FromInstallment[k]).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(FromInstallment[k].trim()); //From Installment
					
			    	if(CommonFunction.checkNull(ToInstallment[k]).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString(ToInstallment[k].trim());// To Installment 
			    	
			    	 if((CommonFunction.checkNull(RecoveryPercen[k])).trim().equalsIgnoreCase(""))
			        		insertPrepStmtObject.addString("0.00");
					else
					 insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(RecoveryPercen[k]).trim())).toString());
			    	 
			    	 if((CommonFunction.checkNull(prinAmount[k])).trim().equalsIgnoreCase(""))
			        		insertPrepStmtObject.addString("0.00");
					else
					 insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(prinAmount[k]).trim())).toString());// PRINCIPAL_AMOUNT   	
			    	if((CommonFunction.checkNull(instalAmount[k])).trim().equalsIgnoreCase(""))
		        		insertPrepStmtObject.addString("0.00");
				     else
				    insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(instalAmount[k]).trim())).toString());// INSTALLMENT_AMOUNT	
			    	insertPrepStmtObject.addString("P");// REC_STATUS   	   	
									
			    	insertPrepStmtObject.addString(""+(k+1));// To Installment 
			    	
			    	if(CommonFunction.checkNull(ipvo.getRecoveryType()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString(ipvo.getRecoveryType().trim());// RECOVERY_TYPE
			    	//============================================================
			    	if (CommonFunction.checkNull(ipvo.getMakerId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(ipvo.getMakerId()).trim()));

					
					if (CommonFunction.checkNull(ipvo.getMakerDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(ipvo.getMakerDate()).trim()));
			    	//============================================================
			    	
				  
				 	insertPrepStmtObject.setSql(bufInsSql.toString());
					 logger.info("IN saveInstallPlanInFacility() insert query1 ### "+insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
					bufInsSql=null;
				  
				}
				
				
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				} catch (Exception e) {
					e.printStackTrace();
				}
				finally{
					ipvo=null;
				}
			     logger.info("In saveInstallPlanInFacility......................"+status);
			return status;
	}

	public ArrayList getInstallTypeInFacility(String dealId,String dealLoanId) {
		 ArrayList<InstallmentPlanForCMVO> list=new ArrayList<InstallmentPlanForCMVO>();
       ArrayList mainList=new ArrayList ();
			ArrayList subList =new ArrayList();
   		
   		try{
   			
   			StringBuilder query=new StringBuilder();
            	 	query.append(" select distinct FROM_INSTL_NO,DEAL_NO_OF_INSTALLMENT,RECOVERY_PERCENT,DEAL_INSTALLMENT_TYPE,DEAL_NO_OF_INSTALLMENT,PRINCIPAL_AMOUNT,INSTALLMENT_AMOUNT,DEAL_RATE_TYPE,DEAL_LOAN_AMOUNT,RECOVERY_TYPE,DEAL_FINAL_RATE,TO_INSTL_NO "+
   				"  from cr_deal_facility_dtl L left JOIN cr_deal_facility_installment_plan D on D.DEAL_ID=L.DEAL_ID AND D.DEAL_LOAN_ID=L.DEAL_LOAN_ID "+               			
   				" where L.DEAL_ID="+dealId+" AND L.DEAL_LOAN_ID ="+dealLoanId+" order by FROM_INSTL_NO asc");
   			
   		 logger.info("getInstallType Queryl: "+query);
   		
   		mainList=ConnectionDAO.sqlSelect(query.toString());
   		
   		query=null;
   		
			for(int i=0;i<mainList.size();i++)
			{
				subList= (ArrayList)mainList.get(i);
				if(subList.size()>0){
					InstallmentPlanForCMVO ipVo= new InstallmentPlanForCMVO();
   				ipVo = new InstallmentPlanForCMVO();  
   				if(CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("E")||CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("P"))
   				{
   					if(CommonFunction.checkNull(subList.get(0)).equalsIgnoreCase("") || CommonFunction.checkNull(subList.get(1)).equalsIgnoreCase("")){
     						ipVo.setFromInstallment("1");
     						ipVo.setToInstallment((CommonFunction.checkNull(subList.get(4))).trim());
     					}else{
     						ipVo.setFromInstallment((CommonFunction.checkNull(subList.get(0))).trim());
     						ipVo.setToInstallment((CommonFunction.checkNull(subList.get(1))).trim());
     					}
     					
   					if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")){
      				     Number RecoveryPercen = myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
 	 			   
      				     logger.info("RecoveryPercen: "+RecoveryPercen);			
 	 			         ipVo.setRecoveryPercen(myFormatter.format(RecoveryPercen));
      				      }else{
      				     ipVo.setRecoveryPercen("0");
      				          }
   				    ipVo.setInstallmentType((CommonFunction.checkNull(subList.get(3))).trim());
    			  	    ipVo.setTotalInstallment((CommonFunction.checkNull(subList.get(4))).trim());
    			       // ipVo.setRecoveryType("P");
   				}
   				else{
   				    ipVo.setFromInstallment((CommonFunction.checkNull(subList.get(0))).trim());
   				    if(i==(mainList.size())-1){
       				      ipVo.setToInstallment((CommonFunction.checkNull(subList.get(1))).trim());
       				    }else{
       				    ipVo.setToInstallment((CommonFunction.checkNull(subList.get(11))).trim());
       				    }
   				    if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")){
   				     Number RecoveryPercen = myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
	 			   
   				     logger.info("RecoveryPercen: "+RecoveryPercen);			
	 			         ipVo.setRecoveryPercen(myFormatter.format(RecoveryPercen));
   				      }else{
   				     ipVo.setRecoveryPercen("0");
   				          }
   				    ipVo.setInstallmentType((CommonFunction.checkNull(subList.get(3))).trim());
   				    ipVo.setTotalInstallment((CommonFunction.checkNull(subList.get(4))).trim());
   				   
     				
     				
   				    }
   				
   				ipVo.setRateType((CommonFunction.checkNull(subList.get(7))).trim());
   				if(!CommonFunction.checkNull(subList.get(5)).equalsIgnoreCase("")){
   				     Number princam = myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());
	 			   
   				     logger.info("princ amount: "+princam);			
	 			         ipVo.setPrinAm(myFormatter.format(princam));
   				      }else{
   				     ipVo.setPrinAm("0.00");
   				          }
   				if(CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("E")||CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("P"))
   				{
    				if(CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase("")){
   				     Number instam = myFormatter.parse(calculateEMIAmount(CommonFunction.checkNull(subList.get(8)),CommonFunction.checkNull(subList.get(4)),CommonFunction.checkNull(subList.get(10))));
   				     
   				     logger.info("inst amount: "+instam);			
	 			         ipVo.setInstalAm(myFormatter.format(instam));
   				      }else{
   				     ipVo.setInstalAm((CommonFunction.checkNull(subList.get(6))).trim());
   				          }
   				}else{
   					if(!CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase("")){
         				     Number instam = myFormatter.parse((CommonFunction.checkNull(subList.get(6))).trim());
     	 			   
         				     logger.info("instamount: "+instam);			
     	 			         ipVo.setInstalAm(myFormatter.format(instam));
         				      }else{
         				     ipVo.setInstalAm("0.00");
         				          }
   				}
   				if(!CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("")){
   				     Number instam = myFormatter.parse((CommonFunction.checkNull(subList.get(8))).trim());
	 			   
   				     logger.info("setLoanAmount: "+instam);			
	 			         ipVo.setLoanAmount(myFormatter.format(instam));
   				      }else{
   				     ipVo.setLoanAmount("0.00");
   				          }
   				ipVo.setRecoveryType((CommonFunction.checkNull(subList.get(9))).trim());
   				   list.add(ipVo);
   			     }
   		  }
   		}catch(Exception e){
   			e.printStackTrace();
   		}
   		finally{
   			dealId=null;
   		}
   		return list;
	}

	public String calculateEMIAmount(String loanAmt, String tenure, String roi) {
		String amount =loanAmt;
	    String inter = tenure;
	    String month = roi;
	    
	    

	    //  double loanAmount = 200000;
	     // int rateOfInterest = 12;
	     // int numberOfMonths = 84;

	    double loanAmount = Double.parseDouble(amount);
	    double rateOfInterest = Double.parseDouble(month);
	    int numberOfMonths = Integer.parseInt(inter);

	      double temp = 1200;           //100*numberofmonths(12))
	      double interestPerMonth = rateOfInterest/temp;
	      //System.out.println(interestPerMonth);

	      double onePlusInterestPerMonth = 1 + interestPerMonth;
	      //System.out.println(onePlusInterestPerMonth);

	      double powerOfOnePlusInterestPerMonth = Math.pow(onePlusInterestPerMonth,numberOfMonths);
	      //System.out.println(powerOfOnePlusInterestPerMonth);

	      double powerofOnePlusInterestPerMonthMinusOne = powerOfOnePlusInterestPerMonth-1;
	      //System.out.println(powerofOnePlusInterestPerMonthMinusOne);

	      double divides = powerOfOnePlusInterestPerMonth/powerofOnePlusInterestPerMonthMinusOne;

	      double principleMultiplyInterestPerMonth = loanAmount * interestPerMonth;
	      //System.out.println(principleMultiplyInterestPerMonth);

	      double totalEmi =  principleMultiplyInterestPerMonth*divides;
	      System.out.println("EMI per month (Exact) : " + totalEmi);

	      double finalValue = Math.round( totalEmi * 100.0 ) / 100.0;

	      System.out.println("EMI per month (Rounded) : " + finalValue);
	      return String.valueOf(finalValue);
	}

}
