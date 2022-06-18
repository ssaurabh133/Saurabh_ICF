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
import com.cm.dao.ChequeStatusUpdateDAO;
import com.cm.dao.CreditManagementDAO;
import com.cm.vo.ChequeStatusVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.communication.engn.daoImplMySql.SmsDAOImpl;

public class ChequeStatusAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ChequeStatusAction.class.getName());
	public ActionForward searchChequesByPayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info("In searchChequesByPayment Action");
				HttpSession session =request.getSession();
				boolean flag=false;
				String alertMsg ="";
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String branch ="";
				if(userobj != null)
				{
					branch = userobj.getBranchId();
				}
				else{
					logger.info("in  searchChequesByPayment method of  ChequeStatusAction action the session is out----------------");
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
				DynaValidatorForm ChequeStatusDynaValidatorForm = (DynaValidatorForm)form;
				ChequeStatusVO chequeStatusVO=new ChequeStatusVO();	
				org.apache.commons.beanutils.BeanUtils.copyProperties(chequeStatusVO, ChequeStatusDynaValidatorForm);
				//PaginationUtill pageutill=new PaginationUtill();
				
				logger.info("Cheque Status"+chequeStatusVO.getChequeStatus());
				logger.info("Instrument type"+chequeStatusVO.getInstrumentType());
				 
				logger.info("No of records"+chequeStatusVO.getNoOfRecords());
							
				
				if(!(CommonFunction.checkNull(chequeStatusVO.getChequeStatus()).equalsIgnoreCase("ALL")))
				{
					
					if((CommonFunction.checkNull(chequeStatusVO.getInstrumentType()).equalsIgnoreCase("P")))
					{
						request.setAttribute("ButtonP", "ButtonP");
						if((CommonFunction.checkNull(chequeStatusVO.getChequeStatus()).equalsIgnoreCase("A")))
							request.setAttribute("Approve", "Approve");
						if((CommonFunction.checkNull(chequeStatusVO.getChequeStatus()).equalsIgnoreCase("C")))
							request.setAttribute("SendToCustomer", "SendToCustomer");
						if((CommonFunction.checkNull(chequeStatusVO.getChequeStatus()).equalsIgnoreCase("R")))
							request.setAttribute("Realise", "Realise");
						if((CommonFunction.checkNull(chequeStatusVO.getChequeStatus()).equalsIgnoreCase("S")))
							request.setAttribute("StopPayment", "StopPayment");
					}
					else if((CommonFunction.checkNull(chequeStatusVO.getInstrumentType()).equalsIgnoreCase("R")))
					{
						request.setAttribute("ButtonR", "ButtonR");
						if((CommonFunction.checkNull(chequeStatusVO.getChequeStatus()).equalsIgnoreCase("A")))
							request.setAttribute("Approve", "Approve");
						if((CommonFunction.checkNull(chequeStatusVO.getChequeStatus()).equalsIgnoreCase("D")))
							request.setAttribute("Deposit", "Deposit");
						if((CommonFunction.checkNull(chequeStatusVO.getChequeStatus()).equalsIgnoreCase("B")))
							request.setAttribute("Bounce", "Bounce");
						if((CommonFunction.checkNull(chequeStatusVO.getChequeStatus()).equalsIgnoreCase("R")))
							request.setAttribute("Realise", "Realise");
					}
					
				}
				

//				 
//				 if((chequeStatusVO.getNoOfRecords())>0){
//					 
//					 chequeStatusVO.getNoOfRecords();
//				 }
//				 
//			        int page = 1;
//			        try {
//			            page = Integer.parseInt(request.getParameter("page"));
//			        } catch (NumberFormatException e) {
//			        }
//			        int offset = maxEntriesPerPage * (page - 1);
//			        pageutill.setOffset(offset);
//			        pageutill.setLength(maxEntriesPerPage);
//			        pageutill.getPaginationListForChequeStatus(chequeStatusVO);
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
				chequeStatusVO.setCurrentPageLink(currentPageLink);
				chequeStatusVO.setBranch(branch);
				chequeStatusVO.setTxnType(request.getParameter("txnType"));
				 logger.info("Transaction getTxnType getTxnType :::::::::::: "+chequeStatusVO.getTxnType());
				//change by sachin

				ChequeStatusUpdateDAO dao=(ChequeStatusUpdateDAO)DaoImplInstanceFactory.getDaoImplInstance(ChequeStatusUpdateDAO.IDENTITY);
			    logger.info("Implementation class: "+dao.getClass());
				//end by sachin				

			     
					ArrayList list= dao.searchChequesByPayment(chequeStatusVO);
			        

					request.setAttribute("lbxBranchId",chequeStatusVO.getLbxBranchId());
					request.setAttribute("defaultBranch",chequeStatusVO.getDefaultBranch());
			        request.setAttribute("list", list);
		
			     //start by ravi    
			        StringBuilder query1 = new StringBuilder();
					 String loanRecStatus="";
						try{
							query1.append("select rec_status from cr_loan_dtl where loan_id='"+CommonFunction.checkNull(chequeStatusVO.getLbxLoanNoHID())+"'");
							
							loanRecStatus=ConnectionDAO.singleReturn(query1.toString());
					  		logger.info("IN searchChequesByPayment loanRecStatus....... "+loanRecStatus);
					  		request.setAttribute("loanRecStatus", loanRecStatus);
					    }
					  	   catch(Exception e){
									e.printStackTrace();
								}
				  //end by ravi      	   
				
					  	 if(CommonFunction.checkNull(chequeStatusVO.getTxnType()).equalsIgnoreCase("DC"))
						   {
					    	 session.setAttribute("screenForDealNumber", "screenForDealNumber");
					    	 session.removeAttribute("screenForLoanNumber");
						   }
						  else
						   {
							  session.setAttribute("screenForLoanNumber", "screenForLoanNumber");
							  session.removeAttribute("screenForDealNumber");
						   }
		    
 			return mapping.findForward("searchChequesByPayment");
		
	}
	
	
	 public ActionForward mainChequeStatus(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {
			
			    logger.info("in mainChequeStatus"); 
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userObj=(UserObject)session.getAttribute("userobject");
				String makerID ="";
				String bDate ="";
				int cId =0;
				if(userObj!=null){
					makerID = userObj.getUserId();
					bDate=userObj.getBusinessdate();
					cId=userObj.getCompanyId();
				}else{
					logger.info("in  mainChequeStatus metyhod of ChequeStatusAction action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
			    
				//change by sachin
				CreditManagementDAO dao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
			     logger.info("Implementation class: "+dao.getClass());

				//end by sachin
//				CreditManagementDAO dao = new CreditManagementDAOImpl();
				DynaValidatorForm InstrumentCapturingMakerFirstDynaValidatorForm = (DynaValidatorForm)form;
		
				
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
				ServletContext context = getServlet().getServletContext();
				String strFlag="";	
				if(sessionId!=null)
				{
					strFlag = UserSessionCheck.checkSameUserSession(userObj,sessionId.toString(),"",request);
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

				ChequeStatusVO chequeStatusVO = new ChequeStatusVO();	
				org.apache.commons.beanutils.BeanUtils.copyProperties(chequeStatusVO, InstrumentCapturingMakerFirstDynaValidatorForm);
				chequeStatusVO.setMakerID(makerID);
				chequeStatusVO.setMakerDate(bDate);
				chequeStatusVO.setCompanyID(cId);
				
				logger.info(userObj.getCompanyId()+"jkhgkhjgkjghkjh");
		        
				
				String checkedinstrumentNo =CommonFunction.checkNull(request.getParameter("checkedinstrumentNo"));
	            String[] checkedinstrumentNoList= checkedinstrumentNo.split("/");
	            
	            
	            String checkeddate = CommonFunction.checkNull(request.getParameter("checkeddate")); 			
	            String[] checkeddateList= checkeddate.split("/"); 
	            
	            logger.info("checkeddate >>>>>>>>>>" +checkeddate);
	            
		        String checkedinstrumentAmount =CommonFunction.checkNull( request.getParameter("checkedinstrumentAmount"));                    			
	            String[] checkedinstrumentAmountList= checkedinstrumentAmount.split("/"); 
	            
	            String checkedstatus = CommonFunction.checkNull(request.getParameter("checkedstatus"));                     			
	            String[] checkedstatusList= checkedstatus.split("/");
	            
	            String checkedlbxBPTypeHID = CommonFunction.checkNull(request.getParameter("checkedlbxBPTypeHID"));                     			
	            String[] checkedlbxBPTypeHIDList= checkedlbxBPTypeHID.split("/");
	            
	            String checkedlbxBPNID = CommonFunction.checkNull(request.getParameter("checkedlbxBPNID"));                     			
	            String[] checkedlbxBPNIDList= checkedlbxBPNID.split("/");
	            
	            String checkedlbxBankID = CommonFunction.checkNull(request.getParameter("checkedlbxBankID"));                     			
	            String[] checkedlbxBankIDList= checkedlbxBankID.split("/");
	            
	            String checkedlbxBranchID = CommonFunction.checkNull(request.getParameter("checkedlbxBranchID"));                     			
	            String[] checkedlbxBranchIDList= checkedlbxBranchID.split("/");
	            
	            String instrumentID = CommonFunction.checkNull(request.getParameter("instrumentID"));                     			
	            String[] instrumentIDList= instrumentID.split("/"); 
	            
	            String tdsAmountList = CommonFunction.checkNull(request.getParameter("tdsAmountList"));                     			
	            String[] tdsAmountListList= tdsAmountList.split("/"); 
	            
	            String checkedlbxReasonHID = CommonFunction.checkNull(request.getParameter("checkedlbxReasonHID"));                     			
	            String[] checkedlbxReasonHIDList= checkedlbxReasonHID.split("/"); 
	            
	            String pdcInstrumentId = CommonFunction.checkNull(request.getParameter("pdcInstrumentIdList"));
	            
	            logger.info("pdcInstrumentId: "+pdcInstrumentId);
	            
	            String[] pdcInstrumentIdList= pdcInstrumentId.split("/"); 
	            
//Start By Prashant	            
                String checkedvalueDate = CommonFunction.checkNull(request.getParameter("checkedvalueDate"));
	            
	            logger.info("checkedvalueDate: "+checkedvalueDate);
	            
	            String[] checkedvalueDateList= checkedvalueDate.split("/"); 
	            
                String reasonRemarksList = CommonFunction.checkNull(request.getParameter("reasonRemarksList"));
	            
	            logger.info("reasonRemarksList: "+reasonRemarksList);
	            
	            String[] checkedReasonRemarksList= reasonRemarksList.split("/"); 
//End By Prashant	            
	            
	            
	            //logger.info("pdcInstrumentId: "+pdcInstrumentIdList[0]);
	            
	            
	            String status = CommonFunction.checkNull(request.getParameter("buttonstatus"));
	            logger.info("status is ---------"+status);
	 
	            String pdcFlag = CommonFunction.checkNull(request.getParameter("pdcFlag"));
	            logger.info("pdcFlag is ---------"+pdcFlag);
	            
	            String instrumentType = CommonFunction.checkNull(request.getParameter("instrumentType"));
	            logger.info("instrumentType is ---------"+instrumentType);
	            
	            String depositBankId=CommonFunction.checkNull(request.getParameter("depositBankIdList"));
	            logger.info("depositBankId is ---------"+depositBankId);
	            String[] depositBankIdList= depositBankId.split("/"); 
	            
	            String depositBranchId=CommonFunction.checkNull(request.getParameter("depositBranchIdList"));
	            String[] depositBranchIdList= depositBranchId.split("/"); 
	            
	            String depositMicrCode=CommonFunction.checkNull(request.getParameter("depositMicrCodeList"));
	            String[] depositMicrCodeList= depositMicrCode.split("/"); 
	            
	            String depositIfscCode=CommonFunction.checkNull(request.getParameter("depositIfscCodeList"));
	            String[] depositIfscCodeList= depositIfscCode.split("/"); 
	            
	            String depositBankAccount=CommonFunction.checkNull(request.getParameter("depositBankAccountList"));
	            String[] depositBankAccountList= depositBankAccount.split("/"); 
	            //check existing record by neeraj
	            boolean recordStatus=dao.checkExistRecord(instrumentIDList,checkedstatusList,status);
	            boolean OtcStatus=false;
	            if(CommonFunction.checkNull(status).equalsIgnoreCase("STC")){
	            OtcStatus=dao.checkDocuments(instrumentIDList,checkedstatusList,status);
	            }
	            if(recordStatus)
	            {
	            	request.setAttribute("recordExist","recordExist");
	            	 return mapping.findForward("updateInstrumentDetailMain");
	            }else if(OtcStatus && CommonFunction.checkNull(status).equalsIgnoreCase("STC")){
	            	request.setAttribute("OTC","OTC");
	            	 return mapping.findForward("updateInstrumentDetailMain");
	            }
	            else
	            {	            
	            	String result = dao.updateInstrumentDetailForPayment(tdsAmountListList,instrumentIDList,checkedinstrumentNoList,checkeddateList,checkedinstrumentAmountList,checkedstatusList,checkedlbxBPTypeHIDList,checkedlbxBPNIDList,checkedlbxBankIDList,checkedlbxBranchIDList,chequeStatusVO,status,checkedlbxReasonHIDList,pdcInstrumentIdList,pdcFlag,instrumentType,depositBankIdList,depositBranchIdList,depositMicrCodeList,depositIfscCodeList,depositBankAccountList,checkedvalueDateList,checkedReasonRemarksList);
	            	if(result.equals("S"))
	            	{
	            		//Hina Changes Starts for email/sms on cheque DEPOSIT,Realized,Bounce,Cancel
		            
		    			
		            		request.setAttribute("dss", "S");
		            		
			            	
			            	for(int i=0;i<instrumentIDList.length;i++)
			            	{
			            		String reasonID=checkedlbxReasonHIDList[i];
			            		if(checkedlbxReasonHIDList[i].equalsIgnoreCase("test")){
									reasonID="";
								}
			            		String EventName="";
			            		if(CommonFunction.checkNull(status).equalsIgnoreCase("RBP"))
			            		{
			            			 EventName="Cheque_Realized";
			            			 	
			            		}
			            		if(CommonFunction.checkNull(status).equalsIgnoreCase("BBR"))
			            		{
			            			 EventName="Cheque_Bounce";
			            			 	
			            		}	          		
			            		
			           		String instrumentId=instrumentIDList[i];
			    			boolean stats=new SmsDAOImpl().getEmailDetails(instrumentId, bDate,EventName);
			            					
			            	}
		            	
		            	}
		            	//Hina Changes end for email/sms on cheque DEPOSIT,Realized,Bounce,Cancel
	            	
	            		            		
			    	else
			    	request.setAttribute("procresult", result);	
			    }		    
			    return mapping.findForward("updateInstrumentDetailMain");
		}
	
	 public ActionForward openPopForDepositCheque(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			
					logger.info("In openPopForDepositCheque Action");
					HttpSession session = request.getSession();
					boolean flag=false;
					UserObject userObj=(UserObject)session.getAttribute("userobject");
					String makerID ="";
					String bDate ="";
					int cId =0;
					if(userObj!=null){
						makerID = userObj.getUserId();
						bDate=userObj.getBusinessdate();
						cId=userObj.getCompanyId();
					}else{
						logger.info("in  openPopForDepositCheque method of ChequeStatusAction action the session is out----------------");
						return mapping.findForward("sessionOut");
					}
				    
//					CreditManagementDAO dao = new CreditManagementDAOImpl();
					DynaValidatorForm InstrumentCapturingMakerFirstDynaValidatorForm = (DynaValidatorForm)form;
	
					Object sessionId = session.getAttribute("sessionID");
					//for check User session start
					ServletContext context = getServlet().getServletContext();
					String strFlag="";	
					if(sessionId!=null)
					{
						strFlag = UserSessionCheck.checkSameUserSession(userObj,sessionId.toString(),"",request);
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
	
					
					
					ChequeStatusVO chequeStatusVO = new ChequeStatusVO();	
					org.apache.commons.beanutils.BeanUtils.copyProperties(chequeStatusVO, InstrumentCapturingMakerFirstDynaValidatorForm);
					chequeStatusVO.setMakerID(makerID);
					chequeStatusVO.setMakerDate(bDate);
					logger.info(cId+"---openPopForDepositCheque");
				
					
					String checkedinstrumentNo = request.getParameter("checkedinstrumentNo");
		            String checkeddate = request.getParameter("checkeddate"); 				         
		   	        String checkedinstrumentAmount = request.getParameter("checkedinstrumentAmount");                    					            
		            String checkedstatus = request.getParameter("checkedstatus");                     			 
		            String checkedlbxBPTypeHID = request.getParameter("checkedlbxBPTypeHID");                     			
		            String checkedlbxBPNID = request.getParameter("checkedlbxBPNID");                     			
		            String checkedlbxBankID = request.getParameter("checkedlbxBankID");                     			
		            String checkedlbxBranchID = request.getParameter("checkedlbxBranchID");                     			
		            String instrumentID = request.getParameter("instrumentID");                     			
		            String tdsAmountList = request.getParameter("tdsAmountList");                     			
		            String checkedlbxReasonHID = request.getParameter("checkedlbxReasonHID");    
		            String pdcInstrumentIdList = request.getParameter("pdcInstrumentIdList");   
		            String mode = request.getParameter("mode");
		            //start by ravi    
		            String loanRecStatus = request.getParameter("loanRecStatus");   
					//end by ravi    		           
 logger.info("pdcInstrumentIdList-------------"+pdcInstrumentIdList);
		            
		            
		            String status = request.getParameter("buttonstatus");
		            logger.info("status is ---------"+status);
		//Start By Prashant	  
		            String checkedvalueDate = request.getParameter("checkedvalueDate");
		            logger.info("status is -----checkedvalueDate----"+checkedvalueDate);
		   //End By Prashant	          
		            String pdcFlag = request.getParameter("pdcFlag");
		            String instrumentType = request.getParameter("instrumentType");
		            
		            request.setAttribute("mode", mode);
				    request.setAttribute("checkedinstrumentNo", checkedinstrumentNo);
				    request.setAttribute("checkeddate", checkeddate);
				    request.setAttribute("checkedinstrumentAmount", checkedinstrumentAmount);
				    request.setAttribute("checkedstatus", checkedstatus);
				    request.setAttribute("checkedlbxBPTypeHID", checkedlbxBPTypeHID);
				    request.setAttribute("checkedlbxBPNID", checkedlbxBPNID);
				    request.setAttribute("checkedlbxBankID", checkedlbxBankID);
				    request.setAttribute("checkedlbxBranchID", checkedlbxBranchID);
				    request.setAttribute("instrumentID", instrumentID);
				    request.setAttribute("tdsAmountList", tdsAmountList);
				    request.setAttribute("checkedlbxReasonHID", checkedlbxReasonHID);
				    request.setAttribute("pdcInstrumentIdList", pdcInstrumentIdList);
				    
				    request.setAttribute("pdcFlag", pdcFlag);
				    request.setAttribute("instrumentType", instrumentType);
//Start By Prashant	 
				    request.setAttribute("checkedvalueDate", checkedvalueDate);
//End By Prashant	 				    
				   // logger.info("requerst uri size ::::::::::::::: "+request.getRequestURI().length());
				  //start by ravi    
				    logger.info("loanRecStatus::::::::::::::: "+loanRecStatus);//ravi
				    request.setAttribute("loanRecStatus", loanRecStatus);//ravi
				  //end by ravi      	
	 			return mapping.findForward("openPopForDepositCheque");
			
		}
		
	 public ActionForward saveDepositCheque(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {
			
			    logger.info("In saveDepositCheque"); 
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userObj=(UserObject)session.getAttribute("userobject");
				String makerID ="";
				String bDate ="";
				int cId =0;
				if(userObj!=null){
					makerID = userObj.getUserId();
					bDate=userObj.getBusinessdate();
					cId=userObj.getCompanyId();
				}else{
					logger.info("in  saveDepositCheque method o0f ChequeStatusAction  action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
			    
				//change by sachin
				CreditManagementDAO dao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
			     logger.info("Implementation class: "+dao.getClass());

				//end by sachin
//				CreditManagementDAO dao = new CreditManagementDAOImpl();
				DynaValidatorForm InstrumentCapturingMakerFirstDynaValidatorForm = (DynaValidatorForm)form;
			
				
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
				ServletContext context = getServlet().getServletContext();
				String strFlag="";	
				if(sessionId!=null)
				{
					strFlag = UserSessionCheck.checkSameUserSession(userObj,sessionId.toString(),"",request);
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
	
				
				ChequeStatusVO chequeStatusVO = new ChequeStatusVO();	
				org.apache.commons.beanutils.BeanUtils.copyProperties(chequeStatusVO, InstrumentCapturingMakerFirstDynaValidatorForm);
				chequeStatusVO.setMakerID(makerID);
				chequeStatusVO.setMakerDate(bDate);
				chequeStatusVO.setCompanyID(cId);
				logger.info(cId+"---saveDepositCheque");
				
				String checkedinstrumentNo = request.getParameter("checkedinstrumentNo");
	            String[] checkedinstrumentNoList= checkedinstrumentNo.split("/");
	            
	            String checkeddate = request.getParameter("checkeddate"); 			
	            String[] checkeddateList= checkeddate.split("/");      
	            
		        String checkedinstrumentAmount = request.getParameter("checkedinstrumentAmount");                    			
	            String[] checkedinstrumentAmountList= checkedinstrumentAmount.split("/"); 
	            
	            String checkedstatus = request.getParameter("checkedstatus");                     			
	            String[] checkedstatusList= checkedstatus.split("/");
	            
	            String checkedlbxBPTypeHID = request.getParameter("checkedlbxBPTypeHID");                     			
	            String[] checkedlbxBPTypeHIDList= checkedlbxBPTypeHID.split("/");
	            
	            String checkedlbxBPNID = request.getParameter("checkedlbxBPNID");                     			
	            String[] checkedlbxBPNIDList= checkedlbxBPNID.split("/");
	            
	            String checkedlbxBankID = request.getParameter("checkedlbxBankID");                     			
	            String[] checkedlbxBankIDList= checkedlbxBankID.split("/");
	            
	            String checkedlbxBranchID = request.getParameter("checkedlbxBranchID");                     			
	            String[] checkedlbxBranchIDList= checkedlbxBranchID.split("/");
	            
	            String instrumentID = request.getParameter("instrumentID");                     			
	            String[] instrumentIDList= instrumentID.split("/"); 
	            
	            String tdsAmountList = request.getParameter("tdsAmountList");                     			
	            String[] tdsAmountListList= tdsAmountList.split("/"); 
	            
	            String checkedlbxReasonHID = request.getParameter("checkedlbxReasonHID");                     			
	            String[] checkedlbxReasonHIDList= checkedlbxReasonHID.split("/"); 
	            
	            String pdcInstrumentId = request.getParameter("pdcInstrumentIdList");  
	            logger.info("pdcInstrumentId-------------"+pdcInstrumentId);
	            String[] pdcInstrumentIdList= pdcInstrumentId.split("/"); 
	            
	            String status = request.getParameter("buttonstatus");
	            logger.info("status is ---------"+status);
	   //Start By Prashant	   
	            String checkedvalueDate = request.getParameter("checkedvalueDate");  
	            logger.info("checkedvalueDate-------------"+checkedvalueDate);
	            String[] checkedvalueDateList= checkedvalueDate.split("/");
		//End By Prashant	 		
	            String lbxBankID = request.getParameter("lbxBankID");
	            logger.info("lbxBankID is ---------"+lbxBankID);
	            String lbxBranchID = request.getParameter("lbxBranchID");
	            logger.info("lbxBranchID is ---------"+lbxBranchID);
	            String micr = request.getParameter("micr");
	            logger.info("micr is ---------"+micr);
	            String ifscCode = request.getParameter("ifscCode");
	            logger.info("ifscCode is ---------"+ifscCode);
	            String bankAccount = request.getParameter("bankAccount");
	            logger.info("bankAccount is ---------"+bankAccount);
	            String pdcFlag = request.getParameter("pdcFlag");
	            String instrumentType = request.getParameter("instrumentType");
	            boolean recordStatus=dao.checkExistRecord(instrumentIDList,checkedstatusList,status);
	            
	            
	            
	            if(recordStatus)
	            {
	            	request.setAttribute("recordExist","recordExist");
	            	 return mapping.findForward("updateInstrumentDetail");
	            }
	            else
	            {	            
	            	String result = dao.updateDepositCheque(tdsAmountListList,instrumentIDList,checkedinstrumentNoList,checkeddateList,checkedinstrumentAmountList,checkedstatusList,checkedlbxBPTypeHIDList,checkedlbxBPNIDList,checkedlbxBankIDList,checkedlbxBranchIDList,chequeStatusVO,status,checkedlbxReasonHIDList,lbxBankID,lbxBranchID,micr,ifscCode,bankAccount,pdcInstrumentIdList,pdcFlag,instrumentType,checkedvalueDateList);
	            	if(result.equalsIgnoreCase("S"))			    	
	            		request.setAttribute("DSS", "S");
	            	else
	            		request.setAttribute("PROCVAL", result);			    
			    }			    
			    return mapping.findForward("updateInstrumentDetail");
		}
	 
	 public ActionForward changeScreenUpdateChequeStatus(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			
			        logger.info("In changeScreenUpdateChequeStatus Action");
					HttpSession session =request.getSession();
					
					UserObject userobj=(UserObject)session.getAttribute("userobject");
					if(userobj == null)
					{
						logger.info("in  changeScreenUpdateChequeStatus method of  ChequeStatusAction action the session is out----------------");
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
					session.removeAttribute("mobileReceiptValue");
					 String txnType=request.getParameter("txnType");
					 logger.info("Transaction getTxnType ::::::::::: "+txnType);
				    	   
				     if(CommonFunction.checkNull(txnType).equalsIgnoreCase("DC"))
					   {
				    	 session.setAttribute("screenForDealNumber", "screenForDealNumber");
				    	 session.removeAttribute("screenForLoanNumber");
					   }
					  else
					   {
						//ENABLE_MOBILE_RECEIPT VALUE
							String enableMobileReceiptQuery="SELECT IFNULL(PARAMETER_VALUE,'N')PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='ENABLE_MOBILE_RECEIPT'";
							String mobileReceiptValue=CommonFunction.checkNull(ConnectionDAO.singleReturn(enableMobileReceiptQuery));
							session.setAttribute("mobileReceiptValue",mobileReceiptValue);
						  session.setAttribute("screenForLoanNumber", "screenForLoanNumber");
						  session.removeAttribute("screenForDealNumber");
					   }
			    
	 			return mapping.findForward("changeScreenUpdateChequeStatus");
			
		}

	


		
}
