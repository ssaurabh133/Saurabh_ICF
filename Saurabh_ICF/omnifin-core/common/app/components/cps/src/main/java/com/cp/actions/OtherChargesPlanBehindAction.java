package com.cp.actions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.connect.PrepStmtObject;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.OtherChargesPlanVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class OtherChargesPlanBehindAction extends Action {
	private static final Logger logger = Logger.getLogger(OtherChargesPlanBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
	
		logger.info("In OtherChargesPlanBehindAction  ");
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
		DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
			
		String dbType=resource.getString("lbl.dbType");
		//String dbo=resource.getString("lbl.dbPrefix");

		HttpSession session = request.getSession();

		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in execute method of OtherChargesPlanBehindAction action the session is out----------------");
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
		if(id==4000122 || id==4000123)
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

		logger.info("In OtherChargesPlanBehindAction(execute) dealId: " + dealId);
		
		String loanId = "";
	
		if ((session.getAttribute("loanId") != null )) {

			loanId = session.getAttribute("loanId").toString();
		} else if (session.getAttribute("maxIdInCM") != null) {
			loanId = session.getAttribute("maxIdInCM").toString();
		}

		logger.info("In OtherChargesPlanBehindAction loan id: " + loanId);
		
		
		if ((loanId != null && !loanId.equalsIgnoreCase("")))
		{
			
			String repayQ="select LOAN_REPAYMENT_TYPE from cr_loan_dtl where LOAN_ID="+loanId;
		    logger.info("Repayment Query: "+repayQ);
		    String repayType=ConnectionDAO.singleReturn(repayQ);
			logger.info("Repayment Type:"+repayType);
			
			if(repayType!=null && repayType.equalsIgnoreCase("I"))
			{
				String f=CommonFunction.editableFlag();
		    	if(f!=null && f.equalsIgnoreCase("N"))
				{
					request.setAttribute("cmAuthor", "cmAuthor");
				}
				 LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass());
				CreditProcessingDAO detail1=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
			     logger.info("Implementation class: "+detail1.getClass()); 			// changed by asesh
				//CreditProcessingDAO detail1 = new CreditProcessingDAOImpl();
				
				String strdealId =ConnectionDAO.singleReturn("select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID="+loanId); 
				int dealCount = Integer.parseInt(ConnectionDAO.singleReturn("SELECT COUNT(1) FROM cr_deal_charge_plan_dtl WHERE DEAL_ID="+strdealId));
				
				if(dealCount > 0)
				{
					int loanCount = Integer.parseInt(ConnectionDAO.singleReturn("SELECT COUNT(1) FROM cr_loan_charge_plan_dtl WHERE LOAN_ID="+loanId));
					ArrayList qryList=new ArrayList();
					boolean status=false;
					if(loanCount <= 0)
					{
					
						try 
						{
						
						ArrayList OthChrgListTemp=detail1.getOtherPeriodicalChargeDetailInDeal(strdealId,"D");
						 PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
						 StringBuffer bufInsSql =	new StringBuffer();

						 for(int k=0;k<OthChrgListTemp.size();k++)  
						{
							 //ArrayList subOthChrgListTemp=(ArrayList)OthChrgListTemp.get(k);
							 OtherChargesPlanVo othVo=(OtherChargesPlanVo)OthChrgListTemp.get(k);

							 bufInsSql =	new StringBuffer();
						     insertPrepStmtObject = new PrepStmtObject();
     						 //bufInsSql.append("insert into cr_loan_charge_plan_dtl (LOAN_ID,FROM_INSTL_NO,TO_INSTL_NO,CHARGE_TYPE,AMOUNT,CHARGE_CODE,REC_STATUS,MAKER_ID,MAKER_DATE) values(?,?,?,?,?,?,?,?,DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");
						     bufInsSql.append("insert into cr_loan_charge_plan_dtl (LOAN_ID,FROM_INSTL_NO,TO_INSTL_NO,CHARGE_TYPE,AMOUNT,CHARGE_CODE,REC_STATUS,MAKER_ID,MAKER_DATE) values(?,?,?,?,?,?,?,?,");
							
     						if(dbType.equalsIgnoreCase("MSSQL"))
     						{
     							bufInsSql.append(" dbo.STR_TO_DATE(?,'"+dateFormatWithTime+"') +''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
     						}
     						else
     						{
     							bufInsSql.append(" DATE_ADD(STR_TO_DATE(?,'"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )");
     						}
   						 
     						 
     						if(CommonFunction.checkNull(loanId).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(loanId.trim());   // loan Id
								
							if(CommonFunction.checkNull(othVo.getFromInstallment()).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(othVo.getFromInstallment().trim()); //From Installment
							
						    if(CommonFunction.checkNull(othVo.getToInstallment()).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(othVo.getToInstallment().trim());// To Installment 
						    	
//						    if(CommonFunction.checkNull(othVo.getChargeType()).trim().equalsIgnoreCase(""))
//								insertPrepStmtObject.addNull();
//							else
//								insertPrepStmtObject.addString(othVo.getChargeType().trim());// charge type
						    insertPrepStmtObject.addString("F");
						    	 
						    if((CommonFunction.checkNull(othVo.getChargeAmount())).trim().equalsIgnoreCase(""))
						    	insertPrepStmtObject.addString("0.00");
							else
							 insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(othVo.getChargeAmount()).trim())).toString());// charge amount   	
						    	
						    if((CommonFunction.checkNull(othVo.getChargeCode())).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(othVo.getChargeCode().trim()); //Charge code 
						    	 
						    insertPrepStmtObject.addString("P");// REC_STATUS   	   	
												
						  	if (CommonFunction.checkNull(userobj.getUserId()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(userobj.getUserId()).trim()));
							
							if (CommonFunction.checkNull(userobj.getBusinessdate()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(userobj.getBusinessdate()).trim()));
						    	//============================================================
						    	
							  
							 	insertPrepStmtObject.setSql(bufInsSql.toString());
								 logger.info("IN saveOtherChargesPlanInDeal() LOAN insert query1 ### ACTION "+insertPrepStmtObject.printQuery());
								qryList.add(insertPrepStmtObject);
								bufInsSql=null;
								
								
								
//									bufInsSql =	new StringBuffer();
//									insertPrepStmtObject = new PrepStmtObject();
//					
//						
//									insertPrepStmtObject = new PrepStmtObject();
//							bufInsSql.append("insert into cr_loan_charge_plan_dtl (LOAN_ID,FROM_INSTL_NO,TO_INSTL_NO,CHARGE_TYPE,AMOUNT,CHARGE_CODE,REC_STATUS,MAKER_ID,MAKER_DATE) values(?,?,?,?,?,?,?,?,DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");
//											
//							if(CommonFunction.checkNull(loanId).trim().equalsIgnoreCase(""))
//								insertPrepStmtObject.addNull();
//							else
//								insertPrepStmtObject.addString(loanId.trim());   // loan Id
//							
//							if(CommonFunction.checkNull(subOthChrgListTemp.get(0)).trim().equalsIgnoreCase(""))
//								insertPrepStmtObject.addNull();
//							else
//								insertPrepStmtObject.addString((CommonFunction.checkNull(subOthChrgListTemp.get(0))).trim()); //From Installment
//							
//							if(CommonFunction.checkNull(subOthChrgListTemp.get(1)).trim().equalsIgnoreCase(""))
//								insertPrepStmtObject.addNull();
//							else
//								insertPrepStmtObject.addString((CommonFunction.checkNull(subOthChrgListTemp.get(1))).trim());// To Installment 
//								
//					    	if(CommonFunction.checkNull(subOthChrgListTemp.get(2)).trim().equalsIgnoreCase(""))
//								insertPrepStmtObject.addNull();
//							else
//								insertPrepStmtObject.addString((CommonFunction.checkNull(subOthChrgListTemp.get(2))).trim());// charge type 	
//					    	
//					    	if((CommonFunction.checkNull(subOthChrgListTemp.get(3))).trim().equalsIgnoreCase(""))
//				        		insertPrepStmtObject.addString("0.00");
//					    	else
//					    		insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(subOthChrgListTemp.get(3)).trim())).toString()); //amount
//					    	
//					    	if(CommonFunction.checkNull(subOthChrgListTemp.get(4)).trim().equalsIgnoreCase(""))
//								insertPrepStmtObject.addNull();
//							else
//								insertPrepStmtObject.addString((CommonFunction.checkNull(subOthChrgListTemp.get(4))).trim()); //Charge Id 
//					    	 
//					    	insertPrepStmtObject.addString("P");// REC_STATUS   	   	
//											
//					    	if (CommonFunction.checkNull(userobj.getUserId()).equalsIgnoreCase(""))
//								insertPrepStmtObject.addNull();
//							else
//								insertPrepStmtObject.addString((CommonFunction.checkNull(userobj.getUserId()).trim()));
//							
//							if (CommonFunction.checkNull(userobj.getBusinessdate()).equalsIgnoreCase(""))
//								insertPrepStmtObject.addNull();
//							else
//								insertPrepStmtObject.addString((CommonFunction.checkNull(userobj.getBusinessdate()).trim()));
//					    	//============================================================
//					    	
//						  
//						 	insertPrepStmtObject.setSql(bufInsSql.toString());
//							 logger.info("IN saveOtherChargesPlanInDeal() LOAN insert query1 ### "+insertPrepStmtObject.printQuery());
//							qryList.add(insertPrepStmtObject);
//							bufInsSql=null;
						}
									
							status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				ArrayList OthChrgList=detail1.getOtherPeriodicalChargeDetailInDeal(loanId,"L");
				logger.info("OthChrgList Size:---"+OthChrgList.size());
				if(OthChrgList!=null && OthChrgList.size()>0)
				{  
					OtherChargesPlanVo vo=(OtherChargesPlanVo)OthChrgList.get(0);
				   
				  // logger.info("Installment Type in cr_loan_dtl table 1111: "+vo.getLoanAmount());
					String loanAmount=CommonFunction.checkNull(vo.getLoanAmount());
				 //  logger.info("getRateType Type in cr_loan_dtl table1111: "+vo.getRateType());
					String rateType=CommonFunction.checkNull(vo.getRateType());
				//	logger.info("Installment Type in cr_loan_dtl table 1111: "+vo.getInstallmentType());
					String installmentType=CommonFunction.checkNull(vo.getInstallmentType());
					String totalInstallment=CommonFunction.checkNull(vo.getTotalInstallment());
				//	logger.info("In OtherChargesPlanBehindAction InstallmentType 1111: " + installmentType);
		        /*if(installmentType.equalsIgnoreCase("E")  || installmentType.equalsIgnoreCase("P")){
					request.setAttribute("viewMode", "viewMode");
			
				}*/
					
					
				request.setAttribute("otherChrgList", OthChrgList);
				logger.info("In OtherChargesPlanBehindAction OthChrgList: LoanId ") ;
		        request.setAttribute("installmentType", installmentType);
		        request.setAttribute("totalInstallment", totalInstallment);
		        request.setAttribute("rateType", rateType);
		        request.setAttribute("loanAmount", loanAmount);
				}	
				return mapping.findForward("success");
			}
			else
			{
				request.setAttribute("nonProduct", "nonProduct");
				return mapping.findForward("backNonProductInLoan");
			}
		}
		else if(loanId.equalsIgnoreCase("") && dealId.equalsIgnoreCase(""))
		{
			 request.setAttribute("back", "back");
			 return mapping.findForward("backSuccess");
		}
			
		
		if ((dealId != null && !dealId.equalsIgnoreCase("")))
		{
			
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
			    logger.info("Implementation class: "+detail1.getClass()); 			// changed by asesh
				String installmentType="";
				ArrayList OthChrgList=detail1.getOtherPeriodicalChargeDetailInDeal(dealId,"D");
				logger.info("OthChrgList    Size:---"+OthChrgList.size());
				if(OthChrgList!=null && OthChrgList.size()>0)
				{
					OtherChargesPlanVo vo1=(OtherChargesPlanVo)OthChrgList.get(0);
				//	logger.info("Installment Type in cr_loan_dtl table: "+vo1.getInstallmentType());
				//	 logger.info("Installment Type in cr_deal_loan_dtl table: "+vo1.getLoanAmount());
						String loanAmount=CommonFunction.checkNull(vo1.getLoanAmount());
					//	 logger.info("getRateType Type in cr_deal_loan_dtl table: "+vo1.getRateType());
						String rateType=CommonFunction.checkNull(vo1.getRateType());
					installmentType=CommonFunction.checkNull(vo1.getInstallmentType());
					String totalInstallment=CommonFunction.checkNull(vo1.getTotalInstallment());
				//	logger.info("In OtherChargesPlanBehindAction InstallmentType: " + installmentType);
//		        if(installmentType.equalsIgnoreCase("E")  || installmentType.equalsIgnoreCase("P")){
//					request.setAttribute("viewMode", "viewMode");
	//
//				}
				request.setAttribute("otherChrgList", OthChrgList);
				logger.info("In OtherChargesPlanBehindAction OthChrgList:Deal Id ") ;
		        request.setAttribute("installmentType", installmentType);
		        request.setAttribute("totalInstallment", totalInstallment);
		        request.setAttribute("rateType", rateType);
		        request.setAttribute("loanAmount", loanAmount);
				}	
				return mapping.findForward("success");
		  }
			else
			{
				request.setAttribute("nonProduct", "nonProduct");
				return mapping.findForward("backNonProductInDeal");
			}
			
		}
		else
		{
			 request.setAttribute("back", "back");
			 return mapping.findForward("backDealSuccess");
		}
		
}
	
}