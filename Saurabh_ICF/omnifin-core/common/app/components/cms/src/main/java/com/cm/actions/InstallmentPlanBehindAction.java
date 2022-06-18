package com.cm.actions;

import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cm.dao.LoanInitiationDAO;
import com.cm.vo.InstallmentPlanForCMVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import java.text.DecimalFormat;


public class InstallmentPlanBehindAction extends Action {
	private static final Logger logger = Logger.getLogger(InstallmentPlanBehindAction.class.getName());
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	public ActionForward execute(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
	
		logger.info("In InstallmentPlanBehindAction  ");
		
		HttpSession session = request.getSession();
	//	boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in execute method of DeleteInstrumentAuthorBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag=null;	
		
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
		//code added by neeraj 
		String functionId=(String)session.getAttribute("functionId");
		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase(""))
			functionId="0";
		int id=Integer.parseInt(functionId);
		if(id==4000122 || id==4000123 || id==500000123 )
		{
			session.setAttribute("cmAuthor","cmAuthor");
			session.setAttribute("viewLoan","viewLoan");
		}
		if(id==4000106 || id==4000122)
		{
			session.removeAttribute("underWriterViewData");
			session.removeAttribute("leadNo");
			session.removeAttribute("dealHeader");
			session.removeAttribute("dealId");
			session.removeAttribute("leadInfo");
			session.removeAttribute("viewDeal");
			session.removeAttribute("dealCatList");
			session.removeAttribute("sourceTypeList");
			session.removeAttribute("checkLoginUserLevel");
			session.removeAttribute("creditApprovalList");
			session.removeAttribute("leadMValue");
			session.removeAttribute("bsflag");
	
		}
		//neeraj space end 
		
		session.removeAttribute("pParentGroup");	
		
		String dealId = "";

		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}
		
		// Start Implementation for Multiple Product Concept --> Prashant Kumar
		String dealLoanId = "0";
		
		String facilityFlag = CommonFunction.checkNull((String)request.getParameter("facilityFlag"));
		session.setAttribute("facilityFlag", facilityFlag);
		session.setAttribute("dealLoanId", CommonFunction.checkNull((String)request.getParameter("dealLoanId")));
		
		
		if (session.getAttribute("dealLoanId") != null && !"Y".equalsIgnoreCase(facilityFlag)) {
			dealLoanId = session.getAttribute("dealLoanId").toString();
			facilityFlag = session.getAttribute("facilityFlag").toString();
		} else if ("Y".equalsIgnoreCase(facilityFlag)) {
			session.removeAttribute("dealLoanId");   
			session.removeAttribute("facilityFlag");	
			dealLoanId = request.getParameter("dealLoanId");
			session.setAttribute("dealLoanId",dealLoanId);				
			session.setAttribute("facilityFlag",facilityFlag);
		}

		logger.info("In InstallmentPlanBehindAction dealId: "+dealId +" ::dealLoanId:: "+dealLoanId+" ::facilityFlag:: "+facilityFlag);
	// End Implementation for Multiple Product Concept--> Prashant Kumar 


		logger.info("In InstallmentPlanBehindAction(execute) dealId: " + dealId);
		
		String loanId = "";
	
		if ((session.getAttribute("loanId") != null )) {

			loanId = session.getAttribute("loanId").toString();
		} else if (session.getAttribute("maxIdInCM") != null) {
			loanId = session.getAttribute("maxIdInCM").toString();
		}

		logger.info("In InstallmentPlanBehindAction loan id: " + loanId);
		
		
		if ((loanId != null && !loanId.equalsIgnoreCase("")))
		{
			
			String repayQ="select LOAN_REPAYMENT_TYPE from cr_loan_dtl where LOAN_ID="+loanId;
		    logger.info("Repayment Query: "+repayQ);
		    String repayType=ConnectionDAO.singleReturn(repayQ);
			logger.info("Repayment Type:"+repayType);
			
			if(repayType!=null && repayType.equalsIgnoreCase("I"))
			{
				/*String f=CommonFunction.editableFlag();
		    	if(f!=null && f.equalsIgnoreCase("N"))
				{
					request.setAttribute("cmAuthor", "cmAuthor");
				}*/
				LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
				ArrayList installmentList=dao.getInstallType(loanId);
				logger.info("installmentList    Size:---"+installmentList.size());
				if(installmentList!=null && installmentList.size()>0)
				{  
				   InstallmentPlanForCMVO vo=(InstallmentPlanForCMVO)installmentList.get(0);
				   
				  // logger.info("Installment Type in cr_loan_dtl table: "+vo.getLoanAmount());
					String loanAmount=CommonFunction.checkNull(vo.getLoanAmount());
				//   logger.info("getRateType Type in cr_loan_dtl table: "+vo.getRateType());
					String rateType=CommonFunction.checkNull(vo.getRateType());
				//	logger.info("Installment Type in cr_loan_dtl table: "+vo.getInstallmentType());
					String installmentType=CommonFunction.checkNull(vo.getInstallmentType());
					String totalInstallment=CommonFunction.checkNull(vo.getTotalInstallment());
					String repayEffDate=CommonFunction.checkNull(vo.getRepayeffdate());//added by Richa
					String maxDate=CommonFunction.checkNull(vo.getMaxDate());//added by Richa
					String dueDate=CommonFunction.checkNull(vo.getDueDatee());//added by Richa
					
				 //	logger.info("In InstallmentPlanBehindAction InstallmentType: " + installmentType);
					// Changes added for Edit Due Date| Rahul papneja| 29012018
					String editDueDate=CommonFunction.checkNull(vo.getEditDueDate());//added by Vishal Kumar
					//	String recoveryType=CommonFunction.checkNull(vo.getRecoveryType());//added by Vishal Kumar
						String insNextDueDate=CommonFunction.checkNull(vo.getInsNextDueDate());//added by Vishal Kumar
					// added one filed for auto file due date in installment plan|DEAL_REPAYMENT_FREQ|Vijendra Singh	
					String frequency=CommonFunction.checkNull(vo.getFrequency());				
					// End code| Vijendra Singh
			
						if((installmentType.equalsIgnoreCase("E")  || installmentType.equalsIgnoreCase("P")) && editDueDate.equalsIgnoreCase("N")){
							//Ends Here| Rahul paneja
					request.setAttribute("viewMode", "viewMode");
			
				}
		        if(!CommonFunction.checkNull(dueDate).equals("")&& installmentType.equalsIgnoreCase("I"))
		        {
		        	session.setAttribute("forNewInstallmentType", "forNewInstallmentType");
		        }
				request.setAttribute("installmentList", installmentList);
				logger.info("In InstallmentPlanBehindAction installmentList: "+installmentList.toString()) ;
		        request.setAttribute("installmentType", installmentType);
		        request.setAttribute("totalInstallment", totalInstallment);
		        request.setAttribute("rateType", rateType);
		        request.setAttribute("loanAmount", loanAmount);
		        request.setAttribute("repayeffdate", repayEffDate);//added by Richa
		        request.setAttribute("maxDate", maxDate);//added by Richa
		        //Code added for Edit Due Date | Rahul Papneja | 29012018
		        request.setAttribute("editDueDate",editDueDate);//added by Vishal Kumar
				   //   request.setAttribute("recoveryType",recoveryType);//added by Vishal Kumar
				        request.setAttribute("insNextDueDate",insNextDueDate);//added by Vishal Kumar
				        
		        // Ends Here| Rahul Papneja
			    // added one filed for auto file due date in installment plan|DEAL_REPAYMENT_FREQ|Vijendra Singh	
				        request.setAttribute("frequency",frequency);				
				// End code| Vijendra Singh
				}	
				return mapping.findForward("success");
			}
			else
			{
				request.setAttribute("nonProduct", "nonProduct");
				if("Y".equalsIgnoreCase(facilityFlag)){
					return mapping.findForward("success");
				}else{
				return mapping.findForward("backNonProductInLoan");
				}
				
			}
		}
		else if(loanId.equalsIgnoreCase("") && dealId.equalsIgnoreCase(""))
		{
			 request.setAttribute("back", "back");
			 if("Y".equalsIgnoreCase(facilityFlag)){
					return mapping.findForward("success");
				}else{
			 return mapping.findForward("backSuccess");
				}
		}
			
		
		if ((dealId != null && !dealId.equalsIgnoreCase("")))
		{
			
			if("Y".equalsIgnoreCase(facilityFlag)){
				
				String repayQ="select DEAL_REPAYMENT_TYPE from cr_deal_facility_dtl where DEAL_ID="+dealId+" AND DEAL_LOAN_ID = "+dealLoanId;
			    logger.info("Repayment In deal Query: "+repayQ);
			    String repayType=ConnectionDAO.singleReturn(repayQ);
				logger.info("Repayment Type:"+repayType);
				
				if(repayType!=null && repayType.equalsIgnoreCase("I"))
				{
				
					if((session.getAttribute("viewDeal")!=null && session.getAttribute("viewDeal").equals("UWA")))
					{
						request.setAttribute("cmAuthor", "cmAuthor");
					}
					CreditProcessingDAO detail1=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
			        logger.info("Implementation class: "+detail1.getClass()); 
					//CreditProcessingDAO detail1 = new CreditProcessingDAOImpl();
					
					ArrayList installmentListFicility=getInstallTypeInFacility(dealId,dealLoanId);
			        ArrayList installmentList=detail1.getInstallTypeInDeal(dealId);
			        logger.info("installmentList    Size:---"+installmentList.size());
					if(installmentList!=null && installmentList.size()>0)
					{  
					   InstallmentPlanForCMVO vo=(InstallmentPlanForCMVO)installmentList.get(0);
					   InstallmentPlanForCMVO vo1=(InstallmentPlanForCMVO)installmentListFicility.get(0);
				//	   logger.info("Installment Type in cr_deal_loan_dtl table: "+vo.getInstallmentType());
						String installmentType=CommonFunction.checkNull(vo1.getInstallmentType());
					   
				//		 logger.info("Installment Type in cr_deal_loan_dtl table: "+vo.getLoanAmount());
						String loanAmount=CommonFunction.checkNull(vo1.getLoanAmount());
						
				//		  logger.info("getRateType Type in cr_deal_loan_dtl table: "+vo.getRateType());
							String rateType=CommonFunction.checkNull(vo.getRateType());
						String totalInstallment=CommonFunction.checkNull(vo1.getTotalInstallment());
				//		logger.info("In InstallmentPlanBehindAction InstallmentType: " + installmentType);
			        if(installmentType.equalsIgnoreCase("E")  || installmentType.equalsIgnoreCase("P")){
						request.setAttribute("viewMode", "viewMode");
				
					}
					request.setAttribute("installmentList", installmentListFicility);
					logger.info("In InstallmentPlanBehindAction installmentList: ") ;
			        request.setAttribute("installmentType", installmentType);
			        request.setAttribute("totalInstallment", totalInstallment);
			        request.setAttribute("rateType", rateType);
			        request.setAttribute("loanAmount", loanAmount);
			        request.setAttribute("dealId", "dealId");
			        request.setAttribute("facilityFlag", "Y");
			        request.setAttribute("facilityFlag", "Y");
			       
			        String repayEffDate=CommonFunction.checkNull(vo.getRepayeffdate());//added by Richa
			        String maxDate=CommonFunction.checkNull(vo.getMaxDate());//added by Richa
			        String editDueDate=CommonFunction.checkNull(vo.getEditDueDate());//added by Vishal Kumar
			        String insNextDueDate=CommonFunction.checkNull(vo.getInsNextDueDate());//added by Vishal Kumar
			        String frequency=CommonFunction.checkNull(vo.getFrequency());	
			        
			        request.setAttribute("installmentList", installmentListFicility);
					logger.info("In InstallmentPlanBehindAction installmentList: ") ;
			        request.setAttribute("installmentType", installmentType);
			        request.setAttribute("totalInstallment", totalInstallment);
			        request.setAttribute("rateType", rateType);
			        request.setAttribute("loanAmount", loanAmount);
			        request.setAttribute("dealId", "dealId");
			        request.setAttribute("repayeffdate", repayEffDate);
			        request.setAttribute("maxDate", maxDate);//added by Richa
			        request.setAttribute("editDueDate",editDueDate);//added by Vishal Kumar
			        request.setAttribute("insNextDueDate",insNextDueDate);//added by Vishal Kumar
			        request.setAttribute("frequency",frequency);				

			        
			        
			        
					}	
					return mapping.findForward("success");
			  }
				else
				{
					
					request.setAttribute("nonProduct", "nonProduct");
					if("Y".equalsIgnoreCase(facilityFlag)){
						return mapping.findForward("success");
					}else{
					return mapping.findForward("backNonProductInDeal");
					}
				}
			}else{
			String repayQ="select DEAL_REPAYMENT_TYPE from cr_deal_loan_dtl where DEAL_ID="+dealId;
		    logger.info("Repayment In deal Query: "+repayQ);
		    String repayType=ConnectionDAO.singleReturn(repayQ);
			logger.info("Repayment Type:"+repayType);
			
			if(repayType!=null && repayType.equalsIgnoreCase("I"))
			{
			
				if(session.getAttribute("viewDeal")!=null && session.getAttribute("viewDeal").equals("UWA"))
				{
					request.setAttribute("cmAuthor", "cmAuthor");
				}
				CreditProcessingDAO detail1=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		        logger.info("Implementation class: "+detail1.getClass()); 
				//CreditProcessingDAO detail1 = new CreditProcessingDAOImpl();
				
				ArrayList installmentList=detail1.getInstallTypeInDeal(dealId);
				logger.info("installmentList    Size:---"+installmentList.size());
				if(installmentList!=null && installmentList.size()>0)
				{  
				   InstallmentPlanForCMVO vo=(InstallmentPlanForCMVO)installmentList.get(0);
				   
			//	   logger.info("Installment Type in cr_deal_loan_dtl table: "+vo.getInstallmentType());
					String installmentType=CommonFunction.checkNull(vo.getInstallmentType());
				   
			//		 logger.info("Installment Type in cr_deal_loan_dtl table: "+vo.getLoanAmount());
					String loanAmount=CommonFunction.checkNull(vo.getLoanAmount());
					
			//		  logger.info("getRateType Type in cr_deal_loan_dtl table: "+vo.getRateType());
						String rateType=CommonFunction.checkNull(vo.getRateType());
					String totalInstallment=CommonFunction.checkNull(vo.getTotalInstallment());
					String repayEffDate=CommonFunction.checkNull(vo.getRepayeffdate());//added by Richa
					String maxDate=CommonFunction.checkNull(vo.getMaxDate());//added by Richa
					String dueDate=CommonFunction.checkNull(vo.getDueDatee());//added by Richa
					// Code Added for Edit Due Date| Rahul papneja| 25012018
					String editDueDate=CommonFunction.checkNull(vo.getEditDueDate());//added by Vishal Kumar
				  //	String recoveryType=CommonFunction.checkNull(vo.getRecoveryType());//added by Vishal Kumar
					String insNextDueDate=CommonFunction.checkNull(vo.getInsNextDueDate());//added by Vishal Kumar
					// Ends Here| Rahul papneja										
	              	
					// added one filed for auto file due date in installment plan|DEAL_REPAYMENT_FREQ|Vijendra Singh	
					String frequency=CommonFunction.checkNull(vo.getFrequency());				
					// End code| Vijendra Singh
					
					
		//		logger.info("In InstallmentPlanBehindAction InstallmentType: " + installmentType);
					// Added By Rahul papneja| Edit Due Date Change| 5012018
					 if((installmentType.equalsIgnoreCase("E")  || installmentType.equalsIgnoreCase("P")) && editDueDate.equalsIgnoreCase("N")){
						 // Ends Here| Rahul papneja
					request.setAttribute("viewMode", "viewMode");
			
				}
		        if(!CommonFunction.checkNull(dueDate).equals("")&& installmentType.equalsIgnoreCase("I"))
		        {
		        	session.setAttribute("forNewInstallmentType", "forNewInstallmentType");
		        }
				request.setAttribute("installmentList", installmentList);
				logger.info("In InstallmentPlanBehindAction installmentList: ") ;
		        request.setAttribute("installmentType", installmentType);
		        request.setAttribute("totalInstallment", totalInstallment);
		        request.setAttribute("rateType", rateType);
		        request.setAttribute("loanAmount", loanAmount);
		        request.setAttribute("dealId", "dealId");
		        request.setAttribute("repayeffdate", repayEffDate);//added by Richa
		        request.setAttribute("maxDate", maxDate);//added by Richa
		        
		        // Code added for Edit Due Date| Rahul papneja| 25012018
		        request.setAttribute("editDueDate",editDueDate);//added by Vishal Kumar
		     //   request.setAttribute("recoveryType",recoveryType);//added by Vishal Kumar
		        request.setAttribute("insNextDueDate",insNextDueDate);//added by Vishal Kumar
		        // Ends Here		       
		        // added one filed for auto file due date in installment plan|DEAL_REPAYMENT_FREQ|Vijendra Singh	
		        request.setAttribute("frequency",frequency);				
				// End code| Vijendra Singh
				}	
				return mapping.findForward("success");
		  }
			else
			{
				
				request.setAttribute("nonProduct", "nonProduct");
				if("Y".equalsIgnoreCase(facilityFlag)){
					return mapping.findForward("success");
				}else{
				return mapping.findForward("backNonProductInDeal");
				}
			}
		}	
		}
		else
		{
			 request.setAttribute("back", "back");
			 if("Y".equalsIgnoreCase(facilityFlag)){
					return mapping.findForward("success");
				}else{
			 return mapping.findForward("backDealSuccess");
				}
			 
		}
		
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
