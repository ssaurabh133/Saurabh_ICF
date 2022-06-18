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
import com.cm.dao.CreditManagementDAO;
import com.cm.dao.InstrumentCapturingDAO;
import com.cm.vo.InstructionCapMakerVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.ApplicantTypeVO;
import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class InstrumentCapturingMaker  extends DispatchAction{

	private static final Logger logger = Logger.getLogger(InstrumentCapturingMaker.class.getName());

	//change by sachin
	CreditManagementDAO cdao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);

	//end by sachin
//	CreditManagementDAO cdao = new CreditManagementDAOImpl();
	public ActionForward generatePDC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String resultstr="success";
					LoggerMsg.info("In generatePDC Method---------");
					
					HttpSession session = request.getSession();					
					UserObject userobj=(UserObject)session.getAttribute("userobject");
					//UserObject userObj=(UserObject)session.getAttribute("userobject");
					String makerID="";
					String bDate ="";
					if(userobj!=null){
						makerID= userobj.getUserId();
						bDate=userobj.getBusinessdate();
					}else{
						logger.info(" in generatePDC method of InstrumentCapturingMaker action the session is out----------------");
						return mapping.findForward("sessionOut");
					}
					//boolean flag=false;
					Object sessionId = session.getAttribute("sessionID");
					DynaValidatorForm InstrumentCapturingMakerValidatorForm = (DynaValidatorForm)form;
					ArrayList<InstructionCapMakerVO> arrList =null;
					ArrayList<InstructionCapMakerVO> arryList=null;
					
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


					
					String alertMsg ="";
					InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();	
					
					org.apache.commons.beanutils.BeanUtils.copyProperties(instructionCapMakerVO, InstrumentCapturingMakerValidatorForm);
					instructionCapMakerVO.setMakerID(makerID);
					instructionCapMakerVO.setMakerDate(bDate);
					String type = instructionCapMakerVO.getInstrumentType();
					InstrumentCapturingDAO dao=(InstrumentCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(InstrumentCapturingDAO.IDENTITY);
					logger.info("Implementation class: "+dao.getClass()); 
					String xid=request.getParameter("id");
					int id=0;
					if(!CommonFunction.checkNull(xid).equalsIgnoreCase(""))
					 id=Integer.parseInt(xid);
					//Nishant Space Starts
					int startingNo=0;
					String startingChequeNo=instructionCapMakerVO.getStartingChequeNo();
					int endingNo=0;
					String endingChequeNo = instructionCapMakerVO.getEndingChequeNo();
					String temp=null;
					if(!(CommonFunction.checkNull(startingChequeNo).equalsIgnoreCase("")) && !(CommonFunction.checkNull(endingChequeNo).equalsIgnoreCase("")))
					{
						startingNo = Integer.parseInt(startingChequeNo);
						endingNo = Integer.parseInt(endingChequeNo);
						if(startingNo>endingNo)
						{
								temp=startingChequeNo;
								startingChequeNo=endingChequeNo;
								endingChequeNo=temp;
								temp=null;
						}
					}
					//Nishant Space end					
					ArrayList InstrumentCheck = null;
					String instrumentCheckQuery="SELECT 1 FROM cr_pdc_instrument_dtl WHERE PDC_STATUS NOT IN ('X','D','L') AND PDC_ISSUEING_BANK_ID='"+instructionCapMakerVO.getLbxBankID()+"' AND PDC_ISSUEING_BRANCH_ID='"+instructionCapMakerVO.getLbxBranchID()+"'AND PDC_ISSUEING_BANK_ACCOUNT='"+instructionCapMakerVO.getBankAccount()+"' AND PDC_INSTRUMENT_NO BETWEEN '"+startingChequeNo+"' and '"+endingChequeNo+"'";
					InstrumentCheck = ConnectionDAO.sqlSelect(instrumentCheckQuery);	
					logger.info("InstrumentCheck *instrumentCheckQuery*************** "+instrumentCheckQuery+" Account No: "+instructionCapMakerVO.getBankAccount());
					
//					
					logger.info("InstrumentCheck **************** "+InstrumentCheck.size());
//						request.setAttribute("alertMsg", "I");
//						return mapping.getInputForward();
					
					if(InstrumentCheck.size()<= 0)
					{
					 arrList=dao.insertListToGeneratePDC(instructionCapMakerVO);
					 logger.info("arrList procval--"+arrList.get(0).getProcvalue());
					 logger.info("arrList SIZE--"+arrList.size());
					
																		
																		
//							      arrList=dao.insertListECS(instructionCapMakerVO);
//								  arryList = dao.getretriveCutInsValues(id);
//								request.setAttribute("alertMsg", alertMsg);
					 
				    	
					
					 if((arrList.size() > 0)&&(arrList.get(0).getProcvalue().equalsIgnoreCase("NONE"))){
						 logger.info("arrList SIZE-1-->"+arrList.size());
						 logger.info("type----->"+type);
						 if(type.equalsIgnoreCase("Q"))
						 {
					    	alertMsg = "Q";
					    	request.setAttribute("alertMsg", alertMsg);
					    	session.setAttribute("arrList",arrList);
					    	request.setAttribute("arrList", arrList);
						 }
						 else if(type.equalsIgnoreCase("E"))
						 {
							 alertMsg = "E";
						    request.setAttribute("alertMsg", alertMsg);
						    session.setAttribute("arrList",arrList);
					    	request.setAttribute("arrList", arrList);
						 }
						 else if(type.equalsIgnoreCase("DIR"))
						 {
							 alertMsg = "D";
						    request.setAttribute("alertMsg", alertMsg);
						    session.setAttribute("arrList",arrList);
					    	request.setAttribute("arrList", arrList);
						 }
						 else if(type.equalsIgnoreCase("H"))
						 {
							 alertMsg = "H";
						    request.setAttribute("alertMsg", alertMsg);
						    session.setAttribute("arrList",arrList);
					    	request.setAttribute("arrList", arrList);
						 }
					    
					    }else{
					    	if(type.equalsIgnoreCase("Q")){
					    	alertMsg = "N";
					    	request.setAttribute("alertMsg", alertMsg);
					    }else if(type.equalsIgnoreCase("E")){
							 alertMsg = "NE";
							    request.setAttribute("alertMsg", alertMsg);
							 }
					    else{
					    	 alertMsg = "NH";
							    request.setAttribute("alertMsg", alertMsg);
					    }
					    }
					
					logger.info("In generatePDC Method-arrList--"+arrList.size());

					if(arrList.size()>0){
						logger.info("arrList SIZE-2-->"+arrList.size());
						if(!(arrList.get(0).getProcvalue().equalsIgnoreCase("NONE"))){
						String procval=arrList.get(0).getProcvalue();
						
						request.setAttribute("procval",procval);
				//		resultstr="procerror";
						
						}}
					
					}
					else
					{
						request.setAttribute("alertMsg", "I");
					}
					
					ArrayList purposeList = dao.instrumentPurposeList();
					logger.info(" In the instrumentPurposeList-"+purposeList.get(0));
					request.setAttribute("purposeList", purposeList);
					
					ArrayList ecsTransactionCodeList = dao.instrumentecsTransactionCodeList();
					logger.info(" In the instrumentecsTransactionCodeList-"+ecsTransactionCodeList.get(0));
					request.setAttribute("ecsTransactionCodeList", ecsTransactionCodeList);
					
					ArrayList customeracTypeList = dao.instrumentcustomeracTypeList();
					logger.info(" In the instrumentcustomeracTypeList-"+customeracTypeList.get(0));
					request.setAttribute("customeracTypeList", customeracTypeList);
					
					ArrayList spnserBnkBrncCodeList = dao.instrumentspnserBnkBrncCodeList();
					logger.info(" In the instrumentspnserBnkBrncCodeList-"+spnserBnkBrncCodeList.get(0));
					request.setAttribute("spnserBnkBrncCodeList", spnserBnkBrncCodeList);
					
					ArrayList utilityNoList = dao.instrumentutilityNoList();
					logger.info(" In the instrumentutilityNoList-"+utilityNoList.get(0));
					request.setAttribute("utilityNoList", utilityNoList);
					
					String pdcPartialForward = dao.pdcPartialForward();
					logger.info(" In the pdcPartialForward-");
					request.setAttribute("pdcPartialForward", pdcPartialForward);
					
					ArrayList insNonInsFlag = dao.insNonIns(id);
					logger.info("insNonIns is--- "+insNonInsFlag);	
					request.setAttribute("insNonInsFlag", insNonInsFlag);
					
					
					String ecsInstDiffList = dao.getInstallmentDiff();
					request.setAttribute("installmentDiff",ecsInstDiffList);
					logger.info(" In update instrument  maker newInstrument-"+ecsInstDiffList);
					
					// Start Prashant here
					String customerTypeCheckQuery="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'ALERT_CUSTOMIZE'";
					logger.info("customerTypeCheckQuery------"+customerTypeCheckQuery);
					String customerTypeStatus = ConnectionDAO.singleReturn(customerTypeCheckQuery);
					logger.info("customerTypeStatus------"+customerTypeStatus);
					request.setAttribute("customerTypeStatus",customerTypeStatus);
					// End Prashant here
					
					//Start KK Tiwari
					CreditProcessingDAO detail=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
			        logger.info("Implementation class: "+dao.getClass()); 
					//CreditProcessingDAO detail = new CreditProcessingDAOImpl();
					ArrayList<ApplicantTypeVO> applist = detail.getApplicantList();
					String dfltACType = detail.getDefaultAccountType();
					request.setAttribute("dfltACType",dfltACType);
					request.setAttribute("applist", applist);
					ArrayList<InstructionCapMakerVO> clearingTypeList = dao.getClearingType();
					request.setAttribute("clearingTypeList", clearingTypeList);
					//End KK Tiwari
					
				    logger.info("Implementation class: "+cdao.getClass());
				    arryList = cdao.getretriveCutInsValues(id);
					request.setAttribute("arryList", arryList);
					logger.info("arryList is--- "+arryList.size());	
					return mapping.findForward(resultstr);
	}
	
	
	
}