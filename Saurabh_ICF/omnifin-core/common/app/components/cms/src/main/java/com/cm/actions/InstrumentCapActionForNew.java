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
import com.cm.dao.DeleteInstrumentDAO;
import com.cm.dao.InstrumentCapturingDAO;
import com.cm.vo.InstructionCapMakerVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.ApplicantTypeVO;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class InstrumentCapActionForNew extends DispatchAction {
	private static final Logger logger = Logger.getLogger(InstrumentCapActionForNew.class.getName());	
	//change by sachin
	CreditManagementDAO cdao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);

	//end by sachin
//	CreditManagementDAO cdao = new CreditManagementDAOImpl();
	public ActionForward searchInstrument(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
				logger.info("In searchInstrument()----------->");
				HttpSession session = request.getSession();
				UserObject userobj = (UserObject) session.getAttribute("userobject");
				String userId="";
				String branchId="";
				if(userobj!=null)
				{
					userId=userobj.getUserId();
					branchId=userobj.getBranchId();
				}else{
					logger.info(" in searchInstrument method of InstrumentCapActionForNew action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();
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
				session.removeAttribute("arryList");
				session.removeAttribute("arrList");
				session.removeAttribute("loanID");
				session.removeAttribute("author");
				session.removeAttribute("releasenotCheck");
				session.removeAttribute("notCheck");
				
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
					
					instructionCapMakerVO.setCurrentPageLink(currentPageLink);
					
					
				String alertMsg ="";
				
				DynaValidatorForm InstrumentCapturingMakerFirstDynaValidatorForm = (DynaValidatorForm)form;
				org.apache.commons.beanutils.BeanUtils.copyProperties(instructionCapMakerVO, InstrumentCapturingMakerFirstDynaValidatorForm);

				if(CommonFunction.checkNull(instructionCapMakerVO.getReportingToUserId()).equalsIgnoreCase(""))
						{ 
					instructionCapMakerVO.setReportingToUserId(userId);
						   //logger.info("When user id is not selected by the user:::::"+userId);
						}
						logger.info("user Id:::::"+instructionCapMakerVO.getReportingToUserId());
						instructionCapMakerVO.setBranchId(branchId);
						instructionCapMakerVO.setUserID(userId);
						//InstrumentCapturingDAO dao = new InstrumentCapturingDAOImpl();
						InstrumentCapturingDAO dao=(InstrumentCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(InstrumentCapturingDAO.IDENTITY);
						logger.info("Implementation class: "+dao.getClass());
						if(!CommonFunction.checkNull(instructionCapMakerVO.getLbxLoanNoHID()).trim().equals("") ||!CommonFunction.checkNull(instructionCapMakerVO.getLbxBankID()).trim().equals("")  || !CommonFunction.checkNull(instructionCapMakerVO.getInstrumentType()).trim().equals("") || !CommonFunction.checkNull(instructionCapMakerVO.getLbxProductID()).trim().equals("") ||!CommonFunction.checkNull(instructionCapMakerVO.getLbxUserId()).trim().equals(""))
						{
			    ArrayList<InstructionCapMakerVO> searchInstrumentList=dao.searchInstrument(instructionCapMakerVO);
			    logger.info("Size of searchInstrumentList-searchInstrument()-->"+searchInstrumentList.size());
			    if(searchInstrumentList.size() > 0){
			    	alertMsg = "Y";
			    	request.setAttribute("alertMsg", alertMsg);
			    
			    }else{
			    	alertMsg = "N";
			    	request.setAttribute("alertMsg", alertMsg);
			    }
			    request.setAttribute("list",searchInstrumentList );
						}
			    request.setAttribute("instrumentCapturingMakerSearch", "instrumentCapturingMakerSearch");
			    request.setAttribute("maker", "maker");
			 // Start Prashant here
				String customerTypeCheckQuery="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'ALERT_CUSTOMIZE'";
				logger.info("customerTypeCheckQuery------"+customerTypeCheckQuery);
				String customerTypeStatus = ConnectionDAO.singleReturn(customerTypeCheckQuery);
				logger.info("customerTypeStatus------"+customerTypeStatus);
				request.setAttribute("customerTypeStatus",customerTypeStatus);
				// End Prashant here
		        return mapping.findForward("searchInstrumentList");
	}

 
	public ActionForward searchInstrumentAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();	

				logger.info("In searchInstrumentAuthor()------------>");
				HttpSession session = request.getSession();
				UserObject userobj = (UserObject) session.getAttribute("userobject");
				String userId="";
				String branchId="";
				if(userobj!=null){
					userId=userobj.getUserId();
					branchId=userobj.getBranchId();
				}else{
					logger.info(" in searchInstrumentAuthor method of InstrumentCapActionForNew action the session is out----------------");
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
				session.removeAttribute("arryList");
				session.removeAttribute("arrList");
				session.removeAttribute("deleteAuthor");
				request.removeAttribute("deleteAuthor");
				session.removeAttribute("loanID");
				session.removeAttribute("author");
				session.removeAttribute("releasenotCheck");
				session.removeAttribute("notCheck");
				
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
					
					instructionCapMakerVO.setCurrentPageLink(currentPageLink);
					

				String alertMsg="";
				
				DynaValidatorForm InstrumentCapturingMakerFirstDynaValidatorForm = (DynaValidatorForm)form;
			
				
				instructionCapMakerVO.setBranchId(branchId);
				instructionCapMakerVO.setUserID(userId);
				
				org.apache.commons.beanutils.BeanUtils.copyProperties(instructionCapMakerVO, InstrumentCapturingMakerFirstDynaValidatorForm);

//				if(CommonFunction.checkNull(instructionCapMakerVO.getReportingToUserId()).equalsIgnoreCase(""))
//						{ 
//					instructionCapMakerVO.setReportingToUserId(userId);
//						   //logger.info("When user id is not selected by the user:::::"+userId);
//						}
//						logger.info("user Id:::::"+instructionCapMakerVO.getReportingToUserId());
				InstrumentCapturingDAO dao=(InstrumentCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(InstrumentCapturingDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
				if(!CommonFunction.checkNull(instructionCapMakerVO.getLbxLoanNoHID()).trim().equals("") ||!CommonFunction.checkNull(instructionCapMakerVO.getLbxBankID()).trim().equals("")  || !CommonFunction.checkNull(instructionCapMakerVO.getInstrumentType()).trim().equals("") || !CommonFunction.checkNull(instructionCapMakerVO.getLbxProductID()).trim().equals("")|| !CommonFunction.checkNull(instructionCapMakerVO.getLbxUserId()).trim().equals(""))
				{
			    ArrayList<InstructionCapMakerVO> searchInstrumentAuthorList=dao.searchInstrumentAuthor(instructionCapMakerVO);
			    if(searchInstrumentAuthorList.size() > 0){
			    	alertMsg = "Y";
			    	request.setAttribute("alertMsg", alertMsg);
			    
			    }else{
			    	alertMsg = "N";
			    	request.setAttribute("alertMsg", alertMsg);
			    }
			    request.setAttribute("list",searchInstrumentAuthorList );
				   
				}
			    
			   // logger.info("Size of searchInstrumentAuthorList--"+searchInstrumentAuthorList.size());
			     request.setAttribute("instrumentCapturingAuthorSearch", "instrumentCapturingAuthorSearch");
			    request.setAttribute("author", "author");
			    
		        return mapping.findForward("searchInstrumentAuthorList");
	}
	
   public ActionForward newInstrument(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {

				logger.info(" In the newInstrument()------------->");
				HttpSession session = request.getSession();
				
				UserObject userobj = (UserObject) session.getAttribute("userobject");
				//String userId="";
				if(userobj==null)
				{
				logger.info(" in  newInstrument InstrumentCapActionForNew action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				
				InstrumentCapturingDAO dao=(InstrumentCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(InstrumentCapturingDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
				ArrayList purposeList = dao.instrumentPurposeList();
				//logger.info(" In the newInstrument-"+purposeList.get(0));
				request.setAttribute("purposeList", purposeList);
				ArrayList ecsTransactionCodeList = dao.instrumentecsTransactionCodeList();
				//logger.info(" In the newInstrument-"+ecsTransactionCodeList.get(0));
				request.setAttribute("ecsTransactionCodeList", ecsTransactionCodeList);
				
				ArrayList customeracTypeList = dao.instrumentcustomeracTypeList();
				//logger.info(" In the newInstrument-"+customeracTypeList.get(0));
				request.setAttribute("customeracTypeList", customeracTypeList);
				
				
				ArrayList spnserBnkBrncCodeList = dao.instrumentspnserBnkBrncCodeList();
				//logger.info(" In the newInstrument-"+spnserBnkBrncCodeList.get(0));
				request.setAttribute("spnserBnkBrncCodeList", spnserBnkBrncCodeList);
				
				ArrayList utilityNoList = dao.instrumentutilityNoList();
				//logger.info(" In the newInstrument-"+utilityNoList.get(0));
				request.setAttribute("utilityNoList", utilityNoList);
				
				String pdcPartialForward = dao.pdcPartialForward();
				request.setAttribute("pdcPartialForward", pdcPartialForward);
				
				String ecsInstDiffList = dao.getInstallmentDiff();
				request.setAttribute("installmentDiff",ecsInstDiffList);
				//logger.info(" In the newInstrument-"+ecsInstDiffList);
				// Start Prashant here
				String customerTypeCheckQuery="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'ALERT_CUSTOMIZE'";
				logger.info("customerTypeCheckQuery------"+customerTypeCheckQuery);
				String customerTypeStatus = ConnectionDAO.singleReturn(customerTypeCheckQuery);
				logger.info("customerTypeStatus------"+customerTypeStatus);
				request.setAttribute("customerTypeStatus",customerTypeStatus);
				// End Prashant here
				//code added by neeraj tripathi
				CreditProcessingDAO detail=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		        logger.info("Implementation class: "+dao.getClass()); 
				//CreditProcessingDAO detail = new CreditProcessingDAOImpl();
				ArrayList<ApplicantTypeVO> applist = detail.getApplicantList();
				String dfltACType = detail.getDefaultAccountType();
				request.setAttribute("dfltACType",dfltACType);
				request.setAttribute("applist", applist);
				ArrayList<InstructionCapMakerVO> clearingTypeList = dao.getClearingType();
				request.setAttribute("clearingTypeList", clearingTypeList);
								
				//tripathi's space end
								
				
				session.removeAttribute("arrList");
				return mapping.findForward("success");
		}
	
	public ActionForward updateInstrument(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		
				logger.info(" In the updateInstrument()------------>");
				HttpSession session = request.getSession();
				
				UserObject userobj = (UserObject) session.getAttribute("userobject");
				String userId="";
				if(userobj!=null)
				{
					userId=userobj.getUserId();
				}else{
					logger.info(" in  updateInstrument InstrumentCapActionForNew action the session is out----------------");
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
				int id=0;
				if(request.getAttribute("loanID")!=null)
				{
					id=Integer.parseInt(request.getAttribute("loanID").toString());
				}
				else if(request.getParameter("loanID")!=null)
				{
					id=Integer.parseInt(request.getParameter("loanID"));
				}
	           logger.info(" ID is---"+id);
	
				InstrumentCapturingDAO dao=(InstrumentCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(InstrumentCapturingDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
				
				String loanID = request.getParameter("loanID");

				//InstrumentCapturingDAO service = new InstrumentCapturingDAOImpl();
				
				logger.info("function id is ........................................"+session.getAttribute("functionId"));
				String functionId="";
				
				if(session.getAttribute("functionId")!=null)
				{
					functionId=session.getAttribute("functionId").toString();
				}
				
				
				//ServletContext context=getServlet().getServletContext();
				if(context!=null)
				{
				boolean flag = LockRecordCheck.lockCheck(userId,functionId,loanID,context);
				logger.info("Flag ........................................ "+flag);
				if(!flag)
				{
					logger.info("Record is Locked");			
					request.setAttribute("alertMsg", "Locked");
					request.setAttribute("recordId", loanID);
					 request.setAttribute("maker", "maker");
					//return mapping.findForward("searchInstrumentList");
					 return mapping.getInputForward();
				}
				}
			     logger.info("Implementation class: "+cdao.getClass());
				ArrayList<InstructionCapMakerVO> arryList = cdao.getretriveCutInsValues(id);
				ArrayList<InstructionCapMakerVO> arrList= dao.getValuesforUpdate(id);
				ArrayList purposeList = dao.instrumentPurposeList();
				//logger.info(" In the newInstrument-"+purposeList.get(0));
				request.setAttribute("purposeList", purposeList);
				ArrayList ecsTransactionCodeList = dao.instrumentecsTransactionCodeList();
				//logger.info(" In the newInstrument-"+ecsTransactionCodeList.get(0));
				request.setAttribute("ecsTransactionCodeList", ecsTransactionCodeList);
				
				ArrayList customeracTypeList = dao.instrumentcustomeracTypeList();
				//logger.info(" In the newInstrument-"+customeracTypeList.get(0));
				request.setAttribute("customeracTypeList", customeracTypeList);
				
				ArrayList spnserBnkBrncCodeList = dao.instrumentspnserBnkBrncCodeList();
				//logger.info(" In the newInstrument-"+spnserBnkBrncCodeList.get(0));
				request.setAttribute("spnserBnkBrncCodeList", spnserBnkBrncCodeList);
				
				ArrayList utilityNoList = dao.instrumentutilityNoList();
				//logger.info(" In the newInstrument-"+utilityNoList.get(0));
				request.setAttribute("utilityNoList", utilityNoList);
				
				
				String ecsInstDiffList = dao.getInstallmentDiff();
				request.setAttribute("installmentDiff",ecsInstDiffList);
				//logger.info(" In update instrument  maker newInstrument-"+ecsInstDiffList);
				
				
				logger.info("ArrayList is--- "+arrList.size());		
				
				String pdcPartialForward = dao.pdcPartialForward();
				
				if(arrList.size() > 0){
					request.setAttribute("arrList", arrList);					
				}
				
				if(arryList.size() > 0){
					request.setAttribute("arryList", arryList);			
				}
				
				request.setAttribute("pdcPartialForward", pdcPartialForward);
				ArrayList insNonInsFlag = dao.insNonIns(id);
				logger.info("insNonInsFlag is--- "+insNonInsFlag);	
				request.setAttribute("insNonInsFlag", insNonInsFlag);
//				request.setAttribute("arryList", arryList);
//				request.setAttribute("arrList", arrList);
				// Start Prashant here
				String customerTypeCheckQuery="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'ALERT_CUSTOMIZE'";
				logger.info("customerTypeCheckQuery------"+customerTypeCheckQuery);
				String customerTypeStatus = ConnectionDAO.singleReturn(customerTypeCheckQuery);
				logger.info("customerTypeStatus------"+customerTypeStatus);
				request.setAttribute("customerTypeStatus",customerTypeStatus);
				// End Prashant here
				//Code added by neeraj tripathi
				CreditProcessingDAO detail=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		        logger.info("Implementation class: "+dao.getClass()); 
				ArrayList<ApplicantTypeVO> applist = detail.getApplicantList();
				String dfltACType = detail.getDefaultAccountType();
				request.setAttribute("dfltACType",dfltACType);
				request.setAttribute("applist", applist);
				ArrayList<InstructionCapMakerVO> clearingTypeList = dao.getClearingType();
				request.setAttribute("clearingTypeList", clearingTypeList);
				//tripathi's space end
				
				return mapping.findForward("updatedList");
		}
	
	
	public ActionForward openPopForLoanAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
				logger.info(" In the openPopForLoanAccount()------------>");
				HttpSession session = request.getSession();				
				UserObject userobj = (UserObject) session.getAttribute("userobject");
				//String userId="";
				if(userobj==null)
				{
				logger.info(" in  openPopForLoanAccount InstrumentCapActionForNew action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				InstrumentCapturingDAO dao=(InstrumentCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(InstrumentCapturingDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
				ArrayList<InstructionCapMakerVO> loanAccountList= dao.getLoanAccountList();
				logger.info("loanAccountList is--- "+loanAccountList.size());	
				request.setAttribute("loanAccountList", loanAccountList);
				return mapping.findForward("openPopforLoanAcc");
	}
	
	
	public ActionForward insNonIns(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
				logger.info(" In the insNonIns-");
				HttpSession session = request.getSession();				
				UserObject userobj = (UserObject) session.getAttribute("userobject");
				//String userId="";
				if(userobj==null)
				{
				logger.info(" in  openPopForLoanAccount InstrumentCapActionForNew action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				InstrumentCapturingDAO dao=(InstrumentCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(InstrumentCapturingDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
				int lbxLoanNoHID = Integer.parseInt(request.getParameter("lbxLoanNoHID"));
				ArrayList insNonInsFlag = dao.insNonIns(lbxLoanNoHID);
				logger.info("insNonInsFlag is--- "+insNonInsFlag);	
				request.setAttribute("insNonInsFlag", insNonInsFlag);
				return mapping.findForward("insNonInsFlag");
	}
	
	
	public ActionForward deleteInstrument(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
				logger.info(" in deleteInstrument()---->");
				HttpSession session = request.getSession();				
				UserObject userobj = (UserObject) session.getAttribute("userobject");
				//String userId="";
				if(userobj==null)
				{
				logger.info(" in  openPopForLoanAccount InstrumentCapActionForNew action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				boolean status =false;
				
				InstrumentCapturingDAO dao=(InstrumentCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(InstrumentCapturingDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
				int loanID = Integer.parseInt(request.getParameter("id"));
				String alertMsg = "";
				logger.info("loanID"+loanID);
				String id[] = request.getParameterValues("chk");
				for(int k=0;k<id.length;k++)
				{
					logger.info("id: "+id[k]);
					
					status = dao.deleteInstrument(id[k]);
					
				}
				if(status){
			    	alertMsg = "Y";
			    	request.setAttribute("alertMsgfrDel", alertMsg);
			    	session.removeAttribute("arrList");
			    
			    }else{
			    	alertMsg = "N";
			    	request.setAttribute("alertMsgfrDel", alertMsg);
			    }
				
				request.setAttribute("loanID", loanID);
				return mapping.findForward("deleteInstrument");
	}
	
	
	
	
	public ActionForward instrumentAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		        logger.info(" In the instrumentAuthor-()---------"); 
				
				HttpSession session = request.getSession();
				UserObject userobj = (UserObject) session.getAttribute("userobject");
				String userId="";
				if(userobj!=null)
				{
					userId = userobj.getUserId();
				}else{
					logger.info(" in instrumentAuthor method of InstrumentCapActionForNew action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				
				String sessionId = session.getAttribute("sessionID").toString();
				
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
				int id = Integer.parseInt(request.getParameter("id"));
				String ID = request.getParameter("id");
				
				InstrumentCapturingDAO dao=(InstrumentCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(InstrumentCapturingDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
				
				logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
				String functionId="";
				
				if(session.getAttribute("functionId")!=null)
				{
					functionId=session.getAttribute("functionId").toString();
				}
				
				
				//ServletContext context=getServlet().getServletContext();
				if(context!=null)
				{
				boolean flag = LockRecordCheck.lockCheck(userId,functionId,ID,context);
				logger.info("Flag ........................................ "+flag);
				if(!flag)
				{
					logger.info("Record is Locked");			
					request.setAttribute("alertMsg", "Locked1");
					request.setAttribute("recordId", ID);
					request.setAttribute("author", "author");
					//return mapping.findForward("searchInstrumentAuthorList");
					 return mapping.getInputForward();
				}
				}
			     logger.info("Implementation class: "+cdao.getClass());
				ArrayList<InstructionCapMakerVO> arryList = cdao.getretriveCutInsValues(id);
				ArrayList<InstructionCapMakerVO> arrList = dao.getValuesforAuthorUpdate(id);
				logger.info("ArrayList is "+arrList.size());
				session.setAttribute("arryList", arryList);
				session.setAttribute("arrList", arrList);
				session.setAttribute("loanID", id);
				session.setAttribute("author", "author");
				return mapping.findForward("instrumentAuthor");
		}
	
	public ActionForward savenForPDCInstrument(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		
				logger.info("In savenForPDCInstrument Method-()--------");
				HttpSession session = request.getSession(); 
				String alertMsg ="";
				UserObject userObj = (UserObject)session.getAttribute("userobject");
				String makerID="";
				String bDate ="";
				if(userObj!=null){
					makerID= userObj.getUserId();
					bDate=userObj.getBusinessdate();
				}else{
					logger.info(" in savenForPDCInstrument InstrumentCapActionForNew action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				
				InstructionCapMakerVO vo = new InstructionCapMakerVO();
				vo.setMakerID(makerID);
			    vo.setMakerDate(bDate);
				int id=Integer.parseInt(request.getParameter("id"));
				logger.info("id"+id);
				String checkedID = request.getParameter("checked");
				String[] checkedIDArr = checkedID.split("/");
				String checkedDate = request.getParameter("checkedDate");
				String[] checkedDateArr = checkedDate.split("/");
				
				InstrumentCapturingDAO dao=(InstrumentCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(InstrumentCapturingDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
			    boolean status=dao.updateFlag(id,vo,checkedIDArr,checkedDateArr);
			    if(status){
			    	alertMsg = "Y";
			    	request.setAttribute("alertMsgforSanF", alertMsg);
			    	ArrayList purposeList = dao.instrumentPurposeList();
					logger.info(" In the newInstrument-"+purposeList.get(0));
					request.setAttribute("purposeList", purposeList);
					ArrayList ecsTransactionCodeList = dao.instrumentecsTransactionCodeList();
					logger.info(" In the newInstrument-"+ecsTransactionCodeList.get(0));
					request.setAttribute("ecsTransactionCodeList", ecsTransactionCodeList);
					
					ArrayList customeracTypeList = dao.instrumentcustomeracTypeList();
					logger.info(" In the newInstrument-"+customeracTypeList.get(0));
					request.setAttribute("customeracTypeList", customeracTypeList);
					
					ArrayList spnserBnkBrncCodeList = dao.instrumentspnserBnkBrncCodeList();
					logger.info(" In the newInstrument-"+spnserBnkBrncCodeList.get(0));
					request.setAttribute("spnserBnkBrncCodeList", spnserBnkBrncCodeList);
					
					ArrayList utilityNoList = dao.instrumentutilityNoList();
					logger.info(" In the newInstrument-"+utilityNoList.get(0));
					request.setAttribute("utilityNoList", utilityNoList);
					
					String pdcPartialForward = dao.pdcPartialForward();
					request.setAttribute("pdcPartialForward", pdcPartialForward);
			    
			    }else{
			    	alertMsg = "N";
			    	request.setAttribute("alertMsgforSanF", alertMsg);
			    }
			    
			    logger.info("status" +status);
			    request.setAttribute("maker", "maker");
			    request.setAttribute("instrumentCapturingMakerSearch", "instrumentCapturingMakerSearch");
			 // Start Prashant here
				String customerTypeCheckQuery="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'ALERT_CUSTOMIZE'";
				logger.info("customerTypeCheckQuery------"+customerTypeCheckQuery);
				String customerTypeStatus = ConnectionDAO.singleReturn(customerTypeCheckQuery);
				logger.info("customerTypeStatus------"+customerTypeStatus);
				request.setAttribute("customerTypeStatus",customerTypeStatus);
				// End Prashant here
			    return mapping.findForward("instrumentCapturingMakerSearch");
			     
	    	
	}
	
	
	 public ActionForward openWindowForLnkedLoan(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {

				logger.info(" In the openWindowForLnkedLoan----------");
				HttpSession session = request.getSession();				
				UserObject userobj = (UserObject) session.getAttribute("userobject");
				//String userId="";
				if(userobj==null)
				{
				logger.info(" in  openWindowForLnkedLoan InstrumentCapActionForNew action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				//code added by nishant
				String functionId=(String)session.getAttribute("functionId");
				int funid=Integer.parseInt(functionId);		
				if(funid==4000406)
					request.setAttribute("instrumentMaker", "instrumentMaker");
				//nishant space end
				String id= request.getParameter("id");
				String installmentAmount = request.getParameter("installmentAmount");
				String date= request.getParameter("date");
				String loanAccNo = request.getParameter("loanAccNo");
				String installmentNo = request.getParameter("installmentNo");
				String instrumentAmount = request.getParameter("instrumentAmount");
				String instrumentID=request.getParameter("instrumentID");
				String restAllocatedAmount=request.getParameter("restAllocatedAmount");
				
	            request.setAttribute("loanId",id); 
	            request.setAttribute("installmentAmount",installmentAmount); 
	            request.setAttribute("date",date); 
	            request.setAttribute("loanAccNo",loanAccNo); 
	            request.setAttribute("installmentNo",installmentNo); 
	            request.setAttribute("instrumentAmount",instrumentAmount);
	            request.setAttribute("instrumentID",instrumentID);
	            request.setAttribute("restAllocatedAmount",restAllocatedAmount);
	            
				return mapping.findForward("openWindowForLnkedLoan");
			}
	
	 public ActionForward saveForLinkedLan(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {

				logger.info(" In the saveForLinkedLan----------");
	
				HttpSession session = request.getSession(); 
				UserObject userObj = (UserObject)session.getAttribute("userobject");
				String makerID="";
				String bDate ="";
				if(userObj!=null){
					makerID= userObj.getUserId();
					bDate=userObj.getBusinessdate();
				}else{
					logger.info(" in saveForLinkedLan method of InstrumentCapActionForNew action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				
				InstrumentCapturingDAO dao=(InstrumentCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(InstrumentCapturingDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
				DynaValidatorForm InstrumentCapturingMakerFirstDynaValidatorForm = (DynaValidatorForm)form;
				InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();
				
				org.apache.commons.beanutils.BeanUtils.copyProperties(instructionCapMakerVO, InstrumentCapturingMakerFirstDynaValidatorForm);
				instructionCapMakerVO.setMakerID(makerID);
				instructionCapMakerVO.setMakerDate(bDate);
				
				String instrumentID=request.getParameter("instrumentID");
				logger.info("instrumentID"+instrumentID);
				
				String loanId= request.getParameter("loanId");
				logger.info("loanId"+loanId);
				String[] loanIdList= loanId.split("/");
				logger.info("loanIdList"+loanIdList);
				
				String date = request.getParameter("date");
				logger.info("date"+date);
				String[] dateList= date.split("/");
				logger.info("dateList"+dateList);
				
				String installmentNo= request.getParameter("installmentNo");
				logger.info("totalInstallments"+installmentNo);
				String[] installmentNoList= installmentNo.split("/");
				logger.info("totalInstallmentsList"+installmentNoList);
				
				String installmentAmountMain = request.getParameter("installmentAmountMain");
				logger.info("installmentAmountMain"+installmentAmountMain);
				String[] installmentAmountMainList= installmentAmountMain.split("/");
				logger.info("installmentAmountMainList"+installmentAmountMainList);
				
				String allotedAmount = request.getParameter("allotedAmount");
				logger.info("allotedAmount"+allotedAmount);
				String[] allotedAmountList= allotedAmount.split("/");
				logger.info("allotedAmountList"+allotedAmountList);
			
				boolean status = dao.saveForLinkedLan(loanIdList,dateList,installmentNoList,installmentAmountMainList,allotedAmountList,instrumentID,instructionCapMakerVO);
		           if(status){
					request.setAttribute("sms","S");
		           }else{
		        	   request.setAttribute("sms","N");
		           }
				return mapping.findForward("saveForLinkedLan");
			}
	
	 public ActionForward searchDeleteMaker(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
				

					logger.info("In searchDeleteMaker");
					
					HttpSession session = request.getSession();
					UserObject userobj = (UserObject) session.getAttribute("userobject");
					
					String userId="";
					String branchId="";
					if(userobj!=null)
					{
						userId=userobj.getUserId();
						branchId=userobj.getBranchId();
					}else{
						logger.info(" in searchDeleteMaker method of InstrumentCapActionForNew action the session is out----------------");
						return mapping.findForward("sessionOut");
					}
					InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();
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
					session.removeAttribute("arryList");
					session.removeAttribute("arrList");
					session.removeAttribute("deleteAuthor");
					request.removeAttribute("deleteAuthor");
					session.removeAttribute("loanID");
					session.removeAttribute("author");
					session.removeAttribute("releasenotCheck");
					session.removeAttribute("notCheck");
					
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
						
						instructionCapMakerVO.setCurrentPageLink(currentPageLink);
						
					String alertMsg ="";
					
					DynaValidatorForm InstrumentCapturingMakerFirstDynaValidatorForm = (DynaValidatorForm)form;

					org.apache.commons.beanutils.BeanUtils.copyProperties(instructionCapMakerVO, InstrumentCapturingMakerFirstDynaValidatorForm);
//					if(CommonFunction.checkNull(instructionCapMakerVO.getReportingToUserId()).equalsIgnoreCase(""))
//					{ 
//						instructionCapMakerVO.setReportingToUserId(userId);
//					   //logger.info("When user id is not selected by the user:::::"+userId);
//					}
//					logger.info("user Id:::::"+instructionCapMakerVO.getReportingToUserId());
					
					instructionCapMakerVO.setBranchId(branchId);
					instructionCapMakerVO.setUserID(userId);
					
					//DeleteInstrumentDAO dao = new DeleteInstrumentDAOImpl();
					DeleteInstrumentDAO dao=(DeleteInstrumentDAO)DaoImplInstanceFactory.getDaoImplInstance(DeleteInstrumentDAO.IDENTITY);
					logger.info("Implementation class: "+dao.getClass()); 
					if(!CommonFunction.checkNull(instructionCapMakerVO.getLbxLoanNoHID()).trim().equals("") ||!CommonFunction.checkNull(instructionCapMakerVO.getLbxBankID()).trim().equals("")  || !CommonFunction.checkNull(instructionCapMakerVO.getInstrumentType()).trim().equals("") || !CommonFunction.checkNull(instructionCapMakerVO.getLbxProductID()).trim().equals(""))
					{
				    ArrayList<InstructionCapMakerVO> searchInstrumentList=dao.searchDeleteMaker(instructionCapMakerVO);
				    logger.info("Size of searchInstrumentList-insearchDeleteMaker()--->"+searchInstrumentList.size());
				    
				    if(searchInstrumentList.size() > 0){
				    	alertMsg = "Y";
				    	request.setAttribute("alertMsg", alertMsg);
				    
				    }else{
				    	alertMsg = "N";
				    	request.setAttribute("alertMsg", alertMsg);
				    }
				    request.setAttribute("list",searchInstrumentList );
					}
				    request.setAttribute("deleteInstrumentMakerSearch", "deleteInstrumentMakerSearch");
					request.setAttribute("deleteMaker", "deleteMaker");
				    session.setAttribute("userId", userId);
				 
			        return mapping.findForward("deleteInstrumentList");
		}
		

	 public ActionForward searchDeleteAuthor(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			
					logger.info("In searchDeleteAuthor");
						
					HttpSession session = request.getSession();
					UserObject userobj = (UserObject) session.getAttribute("userobject");					
					String userId="";
					String branchId="";
					if(userobj!=null)
					{
						userId=userobj.getUserId();
						branchId=userobj.getBranchId();
					}else{
						logger.info(" in searchDeleteAuthor method of InstrumentCapActionForNew action the session is out----------------");
						return mapping.findForward("sessionOut");
					}
					InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();
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
					
					session.removeAttribute("arryList");
					session.removeAttribute("arrList");
					session.removeAttribute("loanID");
					session.removeAttribute("author");
					session.removeAttribute("releasenotCheck");
					session.removeAttribute("notCheck");
					
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
						
						instructionCapMakerVO.setCurrentPageLink(currentPageLink);
						
					String alertMsg="";
					
					DynaValidatorForm InstrumentCapturingMakerFirstDynaValidatorForm = (DynaValidatorForm)form;
										
					instructionCapMakerVO.setBranchId(branchId);
					instructionCapMakerVO.setUserID(userId);
					org.apache.commons.beanutils.BeanUtils.copyProperties(instructionCapMakerVO, InstrumentCapturingMakerFirstDynaValidatorForm);
//					if(CommonFunction.checkNull(instructionCapMakerVO.getReportingToUserId()).equalsIgnoreCase(""))
//					{ 
//						instructionCapMakerVO.setReportingToUserId(userId);
//					   //logger.info("When user id is not selected by the user:::::"+userId);
//					}
//					logger.info("user Id:::::"+instructionCapMakerVO.getReportingToUserId());
					DeleteInstrumentDAO dao=(DeleteInstrumentDAO)DaoImplInstanceFactory.getDaoImplInstance(DeleteInstrumentDAO.IDENTITY);
					logger.info("Implementation class: "+dao.getClass()); 
					if(!CommonFunction.checkNull(instructionCapMakerVO.getLbxLoanNoHID()).trim().equals("") ||!CommonFunction.checkNull(instructionCapMakerVO.getLbxBankID()).trim().equals("")  || !CommonFunction.checkNull(instructionCapMakerVO.getInstrumentType()).trim().equals("") || !CommonFunction.checkNull(instructionCapMakerVO.getLbxProductID()).trim().equals(""))
					{
				    ArrayList<InstructionCapMakerVO> searchInstrumentList=dao.searchDeleteAuthor(instructionCapMakerVO);
				    logger.info("Size of searchInstrumentList--searchDeleteAuthor()-->"+searchInstrumentList.size());
				    
				    if(searchInstrumentList.size() > 0){
				    	alertMsg = "Y";
				    	request.setAttribute("alertMsg", alertMsg);
				    
				    }else{
				    	alertMsg = "N";
				    	request.setAttribute("alertMsg", alertMsg);
				    }
				    request.setAttribute("list",searchInstrumentList );
					}
				    request.setAttribute("deleteInstrumentAuthorSearch", "");
					request.setAttribute("deleteAuthor", "");
				   session.setAttribute("userId", userId);
				 
			        return mapping.findForward("deleteInstrumentAuthorList");
		}
	 
	 public ActionForward editDeleteInstrumentMaker(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception{
			
					logger.info(" In the editDeleteInstrumentMaker----------");
					HttpSession session = request.getSession();
					UserObject userobj = (UserObject) session.getAttribute("userobject");
					String userId = "";
					if(userobj!=null)
					{
						userId=userobj.getUserId();
					}else{
						logger.info(" in editDeleteInstrumentMaker method of InstrumentCapActionForNew action the session is out----------------");
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
					int id=0;
					if(request.getAttribute("loanID")!=null)
					{
						logger.info(" ID is----"+request.getAttribute("loanID"));
						id=Integer.parseInt(request.getAttribute("loanID").toString());
					}
					else if(request.getParameter("loanID")!=null)
					{
						id=Integer.parseInt(request.getParameter("loanID"));
						
					}
					logger.info(" ID is----"+id);
					
					DeleteInstrumentDAO dao=(DeleteInstrumentDAO)DaoImplInstanceFactory.getDaoImplInstance(DeleteInstrumentDAO.IDENTITY);
					logger.info("Implementation class: "+dao.getClass()); 
					String loanID=request.getParameter("loanID");
					logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
					String functionId="";

				
					if(session.getAttribute("functionId")!=null)
					{
						functionId=session.getAttribute("functionId").toString();
					}
					
					
					//ServletContext context=getServlet().getServletContext();
					if(context!=null)
					{
					boolean flag = LockRecordCheck.lockCheck(userId,functionId,loanID,context);
					logger.info("Flag ........................................ "+flag);
					if(!flag)
					{
						logger.info("Record is Locked");			
						request.setAttribute("alertMsg", "Locked");
						request.setAttribute("recordId", loanID);
						request.setAttribute("maker", "maker");
						return mapping.findForward("deleteInstrumentList");
					}
					}
				     logger.info("Implementation class: "+cdao.getClass());
					ArrayList<InstructionCapMakerVO> arryList = cdao.getretriveCutInsValues(id);
					ArrayList<InstructionCapMakerVO> arrList= dao.getValuesforDeleteInstrument(id);
					
					logger.info("ArrayList is "+arrList.size());
					
					request.setAttribute("arryList", arryList);
					request.setAttribute("arrList", arrList);
					
//					request.setAttribute("check", "check");
//					request.setAttribute("hold","hold");
					request.setAttribute("deleteMaker", "");
					return mapping.findForward("editDeleteInstrumentMaker");
			}
	 
	 public ActionForward savenForPDCDeleteInstrument(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {
			
			    logger.info("In savenForPDCDeleteInstrument");
	
				HttpSession session = request.getSession(); 
				UserObject userObj = (UserObject)session.getAttribute("userobject");
				String makerID="";
				String bDate ="";
				if(userObj!=null){
					makerID= userObj.getUserId();
					bDate=userObj.getBusinessdate();
				}else{
					logger.info(" in savenForPDCDeleteInstrument method of InstrumentCapActionForNew action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				DeleteInstrumentDAO dao=(DeleteInstrumentDAO)DaoImplInstanceFactory.getDaoImplInstance(DeleteInstrumentDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
				DynaValidatorForm InstrumentCapturingMakerFirstDynaValidatorForm = (DynaValidatorForm)form;
				InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();
			
				org.apache.commons.beanutils.BeanUtils.copyProperties(instructionCapMakerVO, InstrumentCapturingMakerFirstDynaValidatorForm);
				instructionCapMakerVO.setMakerID(makerID);
				instructionCapMakerVO.setMakerDate(bDate);
				
				String loanId=request.getParameter("loanID");
				logger.info("loanId"+loanId);
				
				String checkedhold = request.getParameter("checkedhold");
             String[] checkedholdList= checkedhold.split("/");
	            String checkedStatus = request.getParameter("checkedStatus");	
             String[] checkedStatusList= checkedStatus.split("/");                               
		        String instrumentid = request.getParameter("instrumentid");
		        String[] instrumentidList= instrumentid.split("/");
	            String newStatus = request.getParameter("newStatus");
			    String[] newStatusList= newStatus.split("/");
			    logger.info("Before updateIndiDeleteInstrument");
			    String alertMsgfrsvnfrdel ="";
			   boolean status = dao.updateIndiDeleteInstrument(checkedholdList,checkedStatusList,instrumentidList,newStatusList,instructionCapMakerVO);
			    
			   if(status){
				   alertMsgfrsvnfrdel = "Y";
			    	request.setAttribute("alertMsgfrsvnfrdel", alertMsgfrsvnfrdel);
			    
			    }else{
			    	alertMsgfrsvnfrdel = "N";
			    	request.setAttribute("alertMsgfrsvnfrdel", alertMsgfrsvnfrdel);
			    }
			   
			    request.setAttribute("loanID", loanId);
			    return mapping.findForward("savenForPDCDeleteInstrument");
		}
	 
	 public ActionForward editDeleteInstrumentAuthor(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {
			
			    logger.info(" In the editDeleteInstrumentAuthor----------");
				
			    HttpSession session = request.getSession();
			    UserObject userobj = (UserObject) session.getAttribute("userobject");
				String userId ="";
				if(userobj!=null)
				{
					userId=userobj.getUserId();
				}else{
					logger.info(" in editDeleteInstrumentAuthor method of InstrumentCapActionForNew action the session is out----------------");
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
				int id=0;
				String xid=request.getParameter("id");
				if(!CommonFunction.checkNull(xid).equalsIgnoreCase(""))
			     id= Integer.parseInt(xid);
				DeleteInstrumentDAO dao=(DeleteInstrumentDAO)DaoImplInstanceFactory.getDaoImplInstance(DeleteInstrumentDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
				String ID=request.getParameter("id");
				
				logger.info("function id is ........................................"+session.getAttribute("functionId"));
				String functionId="";
				

				if(session.getAttribute("functionId")!=null)
				{
					functionId=session.getAttribute("functionId").toString();
				}
				
				
				//ServletContext context=getServlet().getServletContext();
				if(context!=null)
				{
				boolean flag = LockRecordCheck.lockCheck(userId,functionId,ID,context);
				logger.info("Flag ........................................ "+flag);
				if(!flag)
				{
					logger.info("Record is Locked");			
					request.setAttribute("alertMsg", "ALocked");
					request.setAttribute("recordId", ID);
					session.setAttribute("deleteAuthor", "deleteAuthor");
					return mapping.findForward("deleteInstrumentAuthorList");
				}
				}
				ArrayList<InstructionCapMakerVO> arrList = dao.getValuesforDeleteInstrumentAuthor(id);
				logger.info("ArrayList is "+arrList);
				
				session.setAttribute("arrList", arrList);
				session.setAttribute("loanID", id);
				session.setAttribute("deleteAuthor", "deleteAuthor");
				
				return mapping.findForward("editDeleteInstrumentAuthor");
			}
	 
		public ActionForward savedeleteInstrumentAuthor(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception{
			
				logger.info("In savedeleteInstrumentAuthor-");
				boolean  flag=false;
				
				HttpSession session = request.getSession();
				UserObject userObj=(UserObject)session.getAttribute("userobject");
				String makerID="";
				String bDate ="";
				if(userObj!=null){
					makerID= userObj.getUserId();
					bDate=userObj.getBusinessdate();
				}else{
					logger.info(" in savedeleteInstrumentAuthor method of InstrumentCapActionForNew action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				DynaValidatorForm InstrumentCapturingMakerFirstDynaValidatorForm = (DynaValidatorForm)form;
				InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();
				int loanID=Integer.parseInt(request.getParameter("loanID"));
				logger.info("loanID"+loanID);
				
				String[] instrumentID =request.getParameterValues("instrumentID");
				logger.info("list _____--------"+instrumentID.length);
	
				org.apache.commons.beanutils.BeanUtils.copyProperties(instructionCapMakerVO, InstrumentCapturingMakerFirstDynaValidatorForm);
				DeleteInstrumentDAO dao=(DeleteInstrumentDAO)DaoImplInstanceFactory.getDaoImplInstance(DeleteInstrumentDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
				instructionCapMakerVO.setMakerID(makerID);
				instructionCapMakerVO.setMakerDate(bDate);
			    flag=dao.updateCommentNDecisionforDeleteIns(instructionCapMakerVO,instrumentID);
			    
		        if(flag){
			    	 request.setAttribute("savedSuccessfully", "S"); 
			     }else{
			    	 request.setAttribute("savedSuccessfully", "N");
			     }

//			    request.setAttribute("author", "author");
				return mapping.findForward("savedeleteInstrumentAuthor");
				
		}
		public ActionForward searchIndiDeleteInstrument(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception{
			 
					 logger.info(" In the searchIndiDeleteInstrument----------");
					 	HttpSession session = request.getSession();				
						UserObject userobj = (UserObject) session.getAttribute("userobject");
						//String userId="";
						if(userobj==null)
						{
						logger.info(" in  searchIndiDeleteInstrument InstrumentCapActionForNew action the session is out----------------");
							return mapping.findForward("sessionOut");
						}
					int id=0;
					if(request.getAttribute("loanID")!=null)
					{
						id=Integer.parseInt(request.getAttribute("loanID").toString());
					}
					else if(request.getParameter("loanID")!=null)
					{
						id=Integer.parseInt(request.getParameter("loanID"));
						
					}
					logger.info(" In the----------"+id);
					DynaValidatorForm InstrumentCapturingMakerFirstDynaValidatorForm = (DynaValidatorForm)form;
					InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();	
					org.apache.commons.beanutils.BeanUtils.copyProperties(instructionCapMakerVO, InstrumentCapturingMakerFirstDynaValidatorForm);
					DeleteInstrumentDAO dao=(DeleteInstrumentDAO)DaoImplInstanceFactory.getDaoImplInstance(DeleteInstrumentDAO.IDENTITY);
					logger.info("Implementation class: "+dao.getClass()); 
				    logger.info("Implementation class: "+cdao.getClass());
					ArrayList<InstructionCapMakerVO> arryList = cdao.getretriveCutInsValues(id);
					ArrayList<InstructionCapMakerVO> arrList= dao.searchIndiDeleteInstrument(id,instructionCapMakerVO);
					logger.info("ArrayList is "+arrList.size());
					request.setAttribute("arryList", arryList);
					request.setAttribute("arrList", arrList);
					request.setAttribute("deleteMaker", "deleteMaker");
//					request.setAttribute("hold","hold");
					return mapping.findForward("searchIndiDeleteInstrument");
			}

		//method added by Anil Kumar
		public ActionForward getNameOfPDCSubmitBy(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			
					logger.info(" In the getNameOfPDCSubmitBy::::::::::::::::::::");
					HttpSession session = request.getSession();				
					UserObject userobj = (UserObject) session.getAttribute("userobject");
					//String userId="";
					if(userobj==null)
					{
						return mapping.findForward("sessionOut");
					}
					InstrumentCapturingDAO dao=(InstrumentCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(InstrumentCapturingDAO.IDENTITY);
					logger.info("Implementation class: "+dao.getClass()); 
					int lbxLoanNoHID = Integer.parseInt(request.getParameter("lbxLoanNoHID"));
					String submitBy=CommonFunction.checkNull(request.getParameter("submitBy"));
					logger.info("lbxLoanNoHID::::::::"+lbxLoanNoHID+"::::::::::submitBy:::::::"+submitBy);	
					ArrayList getNameOfPDCSubmitBy = dao.getNameOfPDCGiven(lbxLoanNoHID,submitBy);
					logger.info("getNameOfPDCSubmitBy is--- "+getNameOfPDCSubmitBy);	
					request.setAttribute("getNameOfPDCSubmitBy", getNameOfPDCSubmitBy);
					return mapping.findForward("insNonInsFlag");
		}
}
