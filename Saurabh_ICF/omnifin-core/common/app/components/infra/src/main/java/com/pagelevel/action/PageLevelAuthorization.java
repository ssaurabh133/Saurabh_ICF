package com.pagelevel.action;

import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.lockRecord.action.ReleaseRecordFromObject;
import com.login.roleManager.Menu;
import com.login.roleManager.UserObject;
import com.login.roleManager.UserPermissionBO;
import com.login.roleManager.UserProfileManager;




public class PageLevelAuthorization extends Action {
	
	private static final Logger logger = Logger.getLogger(PageLevelAuthorization.class.getName());
	String strFlag = "";
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("All is well");
		HttpSession session = request.getSession(false);
    	UserObject userobj=(UserObject)session.getAttribute("userobject");
    	if(userobj==null){
			logger.info(" in execute method of PageLevelAuthorization action the session is out----------------");
			return mapping.findForward("sessionOut");
		}

		String pageResult = "content";
		String pageid=null;
		String moduleID=null;

		ServletContext context = getServlet().getServletContext();
		List<Menu> modid=(List<Menu>) session.getAttribute("leftsidemenulist");
		if(modid!=null)
		{
	        	 moduleID = modid.get(0).getModuleID();
	        	logger.info("moduleID"+moduleID);
		}
		        boolean flag=false;

	        	Object sessionId = session.getAttribute("sessionID");
	        	
//	        	String strFlag="";	
//	    		if(sessionId!=null)
//	    		{
//	    			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
//	    		}
//	    		
//	    	
//	    		if(!strFlag.equalsIgnoreCase(""))
//	    		{
//	    			if(strFlag.equalsIgnoreCase("sameUserSession"))
//	    			{
//	    				context.removeAttribute("msg");
//	    				context.removeAttribute("msg1");
//	    			}
//	    			else if(strFlag.equalsIgnoreCase("BODCheck"))
//	    			{
//	    				context.setAttribute("msg", "B");
//	    			}
//	    			return mapping.findForward("logout");
//	    		}
				//for releasing lock record from application level object 
				//ServletContext context=getServlet().getServletContext();
				if(context!=null && userobj!=null)
				{
				boolean Lflag = ReleaseRecordFromObject.releaselockedRecord(userobj.getUserId(), context);
				logger.info("Flag: "+Lflag);
				}
		logger.info("modid---------"+moduleID);
		int roleid=0;
		Object roleidS=session.getAttribute("roleID");
		if(roleidS!=null)
		{
			roleid=(Integer)roleidS;
		}
		session.removeAttribute("financialDealId");
		session.removeAttribute("intFreq");
		session.removeAttribute("intCal");
		session.removeAttribute("intComFreq");
		session.removeAttribute("genericMasterList");
		session.removeAttribute("customerName");   //Added By Sarvesh
		session.removeAttribute("customerType");
		session.removeAttribute("dealIdForFFAAndFA");   //Added By Sarvesh
		session.removeAttribute("disbursalTo");
		session.removeAttribute("loanRecStatus");
		session.removeAttribute("supManufId");
		session.removeAttribute("bpType");
		session.removeAttribute("session");
		session.removeAttribute("branchId");
		session.removeAttribute("cashPaymentLimit");
		//code added by neeraj
		session.removeAttribute("cmCustomerService");
		session.removeAttribute("LoanUserId");
		session.removeAttribute("uwSearchUser");
		session.removeAttribute("assetDeleteButton");
		session.removeAttribute("machinAddress");		
		session.removeAttribute("earlyClosureType");	
		session.removeAttribute("UWcustomerGroupType");
		session.removeAttribute("UWcustomerType");		
		session.removeAttribute("dealExposureAmount");
	    session.removeAttribute("dealExLoanAmount");
	    session.removeAttribute("exposureLimit");	    
		session.removeAttribute("loanClassificationList");
		session.removeAttribute("machinAddress");
		session.removeAttribute("rmChBranchId");
		session.removeAttribute("sector");
		session.removeAttribute("loginUserId");
		session.removeAttribute("installmentTypeList");	
		session.removeAttribute("lovCount");	
		session.removeAttribute("LoanCycle");
		session.removeAttribute("revolvingFlag");
		session.removeAttribute("balancePrinc");
		session.removeAttribute("forwardedAmt");		
		session.removeAttribute("new");
		session.removeAttribute("cmAuthor");
		session.removeAttribute("viewLoan");
		session.removeAttribute("editLoanMaker");
		session.removeAttribute("DealCap");
		session.removeAttribute("viewMachineInCM");
		session.removeAttribute("editLoanAuthor");
		session.removeAttribute("searchLoan");
		session.removeAttribute("viewMachineInCM");	
		session.removeAttribute("viewDevJSP");	
		
		session.removeAttribute("corporateId");
		session.removeAttribute("idividualId");
		session.removeAttribute("statusCase");
		session.removeAttribute("recStatus");
		session.removeAttribute("new");
		session.removeAttribute("underWriter");
		session.removeAttribute("cusType");
		session.removeAttribute("applType");
		session.removeAttribute("pageStatuss");
		session.removeAttribute("updateFlag");
		session.removeAttribute("approve");
		session.removeAttribute("update");
		session.removeAttribute("CUA");
		session.removeAttribute("custEntryU");
		session.removeAttribute("viewDeviation");
		session.removeAttribute("viewLoan");
		session.removeAttribute("defaultFormate");	
		session.removeAttribute("forwardLoanID");	
		session.removeAttribute("forwardInstrumentID");	
		session.removeAttribute("dealId");	
		session.removeAttribute("viewDeal");
		session.removeAttribute("autoManualFlag");
		session.removeAttribute("groupNameText");
		// add by manish 
		session.removeAttribute("mis");
		session.removeAttribute("misAuthor");
		
		//end by manish
		session.removeAttribute("forNewInstallmentType");//added by Richa

		//neeraj space end
		
		//saurabh changes starts
		session.removeAttribute("userIdLoan");
		session.removeAttribute("branchIdLoan");
		session.removeAttribute("pmstSize");
		session.removeAttribute("productModify");
		session.removeAttribute("appList");
		session.removeAttribute("UserList");
		session.removeAttribute("status");
		session.removeAttribute("mylist");
		session.removeAttribute("reschId");
		session.removeAttribute("getList");
		session.removeAttribute("list");
		session.removeAttribute("loanIdToLink");
		session.removeAttribute("makerNameMain");
		session.removeAttribute("screenForDealNumber");
		session.removeAttribute("receiptCheck");
		session.removeAttribute("assetMakerList");
		session.removeAttribute("assetLoanListNew");
		session.removeAttribute("assetLoanList");
		//saurabh changes ends
		//Nishant space starts
		session.removeAttribute("userId");
		session.removeAttribute("userRole");
		session.removeAttribute("pmstSize");
		session.removeAttribute("subRuleList");
		session.removeAttribute("userList");
		session.removeAttribute("X");
		session.removeAttribute("A");
		session.removeAttribute("policy");
		session.removeAttribute("handSighting");
		//Nishant space ends
		
		//vinod work space start
		
		//Legal start		
		session.removeAttribute("noticeCheckerList");
		session.removeAttribute("noticeCheckerNoticeId");
		session.removeAttribute("declineDispatchNoticeId");
		session.removeAttribute("declineDispatchList");
		session.removeAttribute("assignRejectLegalId");
		session.removeAttribute("assignRejectList");
		session.removeAttribute("caseFileAuthorLegalId");
		session.removeAttribute("caseFileAuthorList");
		session.removeAttribute("courtProcessingAuthorLegalId");
		session.removeAttribute("courtProcessingAuthorCaseType");
		session.removeAttribute("courtProcessingAuthorList");
		session.removeAttribute("courtProcessingAuthorStageList");
		session.removeAttribute("reopenLegalId");
		session.removeAttribute("reopenList");
		session.removeAttribute("reopenStageList");
		session.removeAttribute("reassignLegalId");
		session.removeAttribute("reassignList");
		//Legal end
	
		//Repo start
		
		session.removeAttribute("repoMarkingAuthorLoanId");
		session.removeAttribute("repoMakingAuthorList");
		session.removeAttribute("repoConfirmationList");
		session.removeAttribute("repoConfirmationLoanId");
		session.removeAttribute("repoConfirmationRepoId");
		session.removeAttribute("repoConfirmationCheckListForAgency");
		session.removeAttribute("repoConfirmationCheckListForStrockyard");
		
		//Repo end
		session.removeAttribute("closureDataDisabled");
		
		//sachin rate approvalauthor

		session.removeAttribute("author");
		session.removeAttribute("dealId");
		session.removeAttribute("productCat");
		session.removeAttribute("resultList");
		session.removeAttribute("showCollateralDetails");
		session.removeAttribute("MACHINE");
		session.removeAttribute("PROPERTY");
		session.removeAttribute("ELCATEGORY");
		session.removeAttribute("loanAmount");
		//end sachin rate approval
		//change for document upload
		session.removeAttribute("searchStatus");
		session.removeAttribute("documentCollection");
		session.removeAttribute("Forword");
		session.removeAttribute("loanHeader");
		session.removeAttribute("dealHeader");
		//change for document upload
		//vinod work space end
		//richa work space starts
		session.removeAttribute("caseAuthorUserId");
		session.removeAttribute("caseMarkingUserId");
		//richa work space ends
		//manish work space start
		
		session.removeAttribute("rateApprovalList");
		session.removeAttribute("rateApprovalProduct");
		session.removeAttribute("rateApprovalScheme");
		session.removeAttribute("dealIdForSandBack");	
		session.removeAttribute("dealReassignmentAuthor");	
		//manish work space end
		session.removeAttribute("productId");
		session.removeAttribute("schemeId");
		session.removeAttribute("baseRateList");
		session.removeAttribute("invoiceDealViewer");
		session.removeAttribute("saveForwardButtonEnableOrDisable");
		//Rohit Changes Starts for ACH...
				session.removeAttribute("ACHCapturingEditData");
				session.removeAttribute("view");
				session.removeAttribute("ViewCapture");
				session.removeAttribute("ViewMode");
				session.removeAttribute("loanId");
				session.removeAttribute("disbursalDataAuthor");
				session.removeAttribute("disbursalAuthor");
				session.removeAttribute("earlyClosureButton");
				
				session.removeAttribute("sblloanNo");
				session.removeAttribute("sbldisbursalId");
				
				session.removeAttribute("LeadId");
				session.removeAttribute("dealID");
				session.removeAttribute("panNo");
				session.removeAttribute("aadhaar");
				session.removeAttribute("existingCustomer");
				//Rohit end
				session.removeAttribute("LeadId");
				session.removeAttribute("dealID");
				session.removeAttribute("leadId");
				//saurabh changes starts here for lead capturing
				session.removeAttribute("GenderCategory");
				session.removeAttribute("addresstypeList");
				session.removeAttribute("businessSegmentList");
				session.removeAttribute("constitutionlist");
				session.removeAttribute("indconstitutionlist");
				session.removeAttribute("corconstitutionlist");
				session.removeAttribute("leadRMDetails");
				session.removeAttribute("sourceList");
				session.removeAttribute("leadNew");
				session.removeAttribute("leadDetails");
				session.removeAttribute("NEW");
				session.removeAttribute("genNewLead");
				session.removeAttribute("defaultcountry");
				session.removeAttribute("eduDetail");
				session.removeAttribute("LeadId");
				session.removeAttribute("dealID");
				session.removeAttribute("SaveLoan");
				session.removeAttribute("facilityFlag");
				session.removeAttribute("dealLoanId");
				session.removeAttribute("PendingDisbursal");
				session.removeAttribute("marginMoneyFlag");
				session.removeAttribute("LoanreschId");
				session.removeAttribute("AddLoanId");
				session.removeAttribute("AddpaymentAmt");
				session.removeAttribute("AuthConLoanId");
			    session.removeAttribute("covId");
			    session.removeAttribute("viewModeCovTrack");
			    session.removeAttribute("covLoanId");
			    session.removeAttribute("lbxDealNo");
			    session.removeAttribute("makerId");
				
			    session.removeAttribute("transactionType");
				session.removeAttribute("receiptSource");
				session.removeAttribute("payInSlipUploadDate");
				session.removeAttribute("loanAccNo");
			    session.removeAttribute("presentationDate");
			    session.removeAttribute("passwordPdf");
		//UserObject sessUser = (UserObject) session.getAttribute("userobject");
		if (userobj != null) {
			String requestedPageID = request.getParameter("parameter");			
			session.getAttribute("roleID");
			logger.info("requested menuID---" + requestedPageID);
			String userIDForMenu = null;
//			if (sessUser.isAdmin()) {
//				userIDForMenu = "12";
//			} else {
				userIDForMenu = CommonFunction.checkNull(userobj.getUserId());
//			}
				String moduleDb=(String) session.getAttribute("moduleDb");
				UserProfileManager profManager = new UserProfileManager();
				List<UserPermissionBO> userPermissionList = profManager.getPage(userIDForMenu, Integer.parseInt(requestedPageID),moduleDb);
			if (userPermissionList != null) {
				pageid=userPermissionList.get(0).getFunctionID();
				session.setAttribute("functionId", pageid);
				logger.info("--page functionId---"+pageid);
				
				//Manish Shukla for branch filter
				String branchId=null;
				String bDate=null;
				
				if(userobj!=null)
				{
					branchId=userobj.getBranchId();
					bDate=userobj.getBusinessdate();
				
				}
				session.removeAttribute("branchId");
				session.setAttribute("branchId", branchId);
				String parameterQuery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='USER_FUNCTION_LOG'";
				String parameterValue=ConnectionDAO.singleReturn(parameterQuery);
				if(CommonFunction.checkNull(parameterValue).equalsIgnoreCase("Y"))
				{
				  profManager.saveUserFunctionLog(userIDForMenu,moduleID,pageid,bDate);
				}
				//code added by neeraj
				//3000206  deal
				//4000106  loan
				//4000122  edit loan
				if(CommonFunction.checkNull(pageid).trim().equalsIgnoreCase(""))
					pageid="0";
				int funid=Integer.parseInt(pageid);
				if(funid==3000206 || funid==4000106 || funid==4000122)
				  session.setAttribute("SRCDealLoan","SRCDealLoan");
				else 
					session.removeAttribute("SRCDealLoan");
				//neeraj space end
				
				int modaccess=profManager.functionaccess(userIDForMenu,moduleID,roleid,pageid);
				if(modaccess>0){
				logger.info("pageResult:--modaccess--" + modaccess);
				pageResult = userPermissionList.get(0).getPageName();
				System.out.println("get page name "+pageResult);
				if(pageResult.equalsIgnoreCase(""))
				{
					pageResult="wip.do";
				}
				pageResult = pageResult.substring(0, pageResult.indexOf("."));
				logger.info("pageResult:--" + pageResult);
				session.setAttribute("userpermissionlist", userPermissionList);
				//code added by asesh
				session.removeAttribute("dealId");				
				//asesh
					
				  session.removeAttribute("DealCap");
					if(pageid.equals("3000206"))
					{
						logger.info("--page function id-In Deal Capturing----"+pageid);
						
						  session.setAttribute("groupTypeActivated","groupTypeActivated");
						  session.removeAttribute("corporate");
						  session.setAttribute("viewDeviation","viewDeviation");
						  session.removeAttribute("viewDeviationUND");
						  session.removeAttribute("searchLoan");
						  session.setAttribute("DealCap", "DC");
		                  session.removeAttribute("loanInit");
		                  session.removeAttribute("loanId");
		                  session.removeAttribute("viewDeal");
		                  session.removeAttribute("underWriter");
		                  session.removeAttribute("underWriterViewData");
		                  session.removeAttribute("cmAuthor");		
		                  session.removeAttribute("queryResponse");
		             	// session.setAttribute("underWriter", "underWriter");
		                  session.removeAttribute("underWriter");
						if (request.getParameter("hideId") != null && request.getParameter("operation") != null) {
							session.setAttribute("customerId", request.getParameter("hideId"));
							session.setAttribute("operation", request.getParameter("operation"));
						} else {
							session.removeAttribute("maxIdInCM");
							session.removeAttribute("dealId");
							session.removeAttribute("customerId");
							session.removeAttribute("loanInitDocs");
						
							session.removeAttribute("operation");
							session.removeAttribute("customerInfo");
							
						}
																		
					}
					else if(pageid.equals("4000137"))
					{
						 logger.info("--page function id-In search all loan in cm ----"+pageid);
						 logger.info("To view Loan Details From ");
						 session.setAttribute("cmAuthor", "A");
						 session.setAttribute("searchLoan", "L");
						 session.removeAttribute("dealId");
						 session.removeAttribute("DealCap");
						 session.setAttribute("underWriter", "underWriter");
						 session.setAttribute("underWriterViewData","underWriterViewData");
						 session.setAttribute("viewDeviation","viewDeviation");
						 session.removeAttribute("viewDeviationUND");
						 session.setAttribute("viewDevJSP", "viewDevJSP");
						 session.removeAttribute("cmdocupload");
						 session.setAttribute("specialConditionDV", "specialConditionDV");
						 session.removeAttribute("verifCPS");
						 session.setAttribute("verifCMS","verifCMS");
						 session.setAttribute("handSighting", "handSighting");
					}
					else if(pageid.equals("3000296"))
					{
						logger.info("--page function id-In Under Writer----"+pageid);
						 session.removeAttribute("loanId");
						 session.removeAttribute("dealId");
						 session.removeAttribute("DealCap");
						 session.setAttribute("underWriter", "underWriter");
						 session.setAttribute("underWriterViewData","underWriterViewData");
						 session.removeAttribute("viewBlackList");
						 session.removeAttribute("searchLoan");
						 //session.setAttribute("viewDeviation","viewDeviation");
						 session.setAttribute("viewDeviationUND","viewDeviationUND");
						 session.setAttribute("groupTypeActivated","groupTypeActivated");
						 session.removeAttribute("queryResponse");
						 session.removeAttribute("specialConditionDV");	
						 session.removeAttribute("verifCMS");
						 session.setAttribute("verifCPS","verifCPS");
					}
					else if(pageid.equals("3000951"))
					{
						session.setAttribute("viewDeviationUND","viewDeviationUND");
						session.setAttribute("verifCPS","verifCPS");
						session.removeAttribute("verifCMS");
						session.removeAttribute("loanId");
						 session.removeAttribute("dealId");
						 session.removeAttribute("DealCap");
						 session.setAttribute("underWriter", "underWriter");
						 session.setAttribute("underWriterViewData","underWriterViewData");
						 session.removeAttribute("viewBlackList");
						 session.removeAttribute("searchLoan");
						 //session.setAttribute("viewDeviation","viewDeviation");
						 session.setAttribute("groupTypeActivated","groupTypeActivated");
						 session.removeAttribute("queryResponse");
						 //session.removeAttribute("specialConditionDV");	
						 session.setAttribute("specialConditionDV", "specialConditionDV");
					}
					
					else if(pageid.equals("8000311") || pageid.equals("8000314")|| pageid.equals("8000301")|| pageid.equals("8000316")|| pageid.equals("8000226")
							|| pageid.equals("8000227") || pageid.equals("8000228") || pageid.equals("8000229") || pageid.equals("10000832")|| pageid.equals("10000833")|| pageid.equals("8000315"))
					{
						session.setAttribute("verifCPS","verifCPS");
					}
					
					
					else if(pageid.equals("4000106"))
					{
						logger.info("--page function id-In Loan Initiation Maker----"+pageid);
						String checkParaQuery="SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='NEW_ASSET_COLLATERAL'";
						logger.info("checkParaQuery: "+checkParaQuery);
						String checkParaValue=ConnectionDAO.singleReturn(checkParaQuery);
						logger.info("checkParaValue: "+checkParaValue);
						if(CommonFunction.checkNull(checkParaValue).equalsIgnoreCase("N"))
						{
							session.removeAttribute("DealCap");
						}
						else
						{
							session.setAttribute("DealCap", "DC");
						}	
						session.removeAttribute("underWriter");
		                session.removeAttribute("underWriterViewData");
						session.removeAttribute("dealId");
						session.removeAttribute("maxId");
						session.removeAttribute("viewDeal");
						session.removeAttribute("cmAuthor");	
						session.removeAttribute("viewLoan");
						session.removeAttribute("searchLoan");
						String checkCustQuery="SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='NEW_LOAN_CUSTOMER'";
						logger.info("checkCustQuery: "+checkCustQuery);
						String checkCustValue=ConnectionDAO.singleReturn(checkCustQuery);
						logger.info("checkParaValue: "+checkParaValue);
						if(CommonFunction.checkNull(checkCustValue).equalsIgnoreCase("N"))
						{
							
							session.setAttribute("groupTypeActivated","groupTypeActivated");
						}
						else
						{
							session.removeAttribute("groupTypeActivated");
						}	
						session.removeAttribute("viewDeviationUND");
						session.removeAttribute("viewDeviation");
						session.removeAttribute("viewDevJSP");
						session.removeAttribute("loanAmount");
					}
					else if(pageid.equals("4000111"))
					{
						logger.info("--page function id-In Loan Initiation Author----"+pageid);
						session.removeAttribute("loanInit");
						session.removeAttribute("dealId");
						session.removeAttribute("maxId");
						session.removeAttribute("DealCap");
						session.removeAttribute("viewDeal");
						session.removeAttribute("viewLoan");
						session.setAttribute("cmAuthor", "A");		
						session.removeAttribute("searchLoan");
						//session.setAttribute("groupTypeActivated","groupTypeActivated");
						session.setAttribute("viewDevJSP", "viewDevJSP");		
						session.removeAttribute("viewDeviation");
						session.removeAttribute("viewDeviationUND");
						session.removeAttribute("loanAmount");
						
					}
					else if(pageid.equals("2000308"))
					{
						session.removeAttribute("groupTypeActivated");
						session.removeAttribute("searchLoan");
						logger.info("--page function id-In Create Corporate Customer----"+pageid);
						session.setAttribute("PCrecStat", "PCrecStat");
						session.removeAttribute("PIrecStat");
						session.removeAttribute("PArecStat");
						session.removeAttribute("PFrecStat");
						session.removeAttribute("pageStatuss");
						session.removeAttribute("pageInfo");
						session.removeAttribute("applType");
						session.removeAttribute("corporateId");
						session.removeAttribute("idividualId");
						session.removeAttribute("updateFlag");
						logger.info("In requestedPageID" + requestedPageID);
						session.removeAttribute("update");
						session.removeAttribute("updateInMaker");
						session.removeAttribute("CUA");
						
						session.setAttribute("corporate", "C");
						session.removeAttribute("individual");
						session.removeAttribute("statusCase");
						session.removeAttribute("underWriter");
						session.removeAttribute("underWriterViewData");
						session.removeAttribute("viewBlackList");
						logger.info("In requestedPageID" + requestedPageID);
						logger.info("In operation: "
								+ request.getParameter("operation"));
						
						if (request.getParameter("hideId") != null
								&& request.getParameter("operation") != null) {
							session.setAttribute("corporateId", request
									.getParameter("hideId"));
							session.setAttribute("operation", request
									.getParameter("operation"));

						} else {
							session.removeAttribute("pageInfo");
							session.removeAttribute("corporateId");
							session.removeAttribute("operation");
							session.removeAttribute("individualInfo");
							session.removeAttribute("addressInfo");
							session.removeAttribute("statesList");
							session.removeAttribute("detailList");
							session.removeAttribute("citiesList");
							session.removeAttribute("stakeDetails");
							session.removeAttribute("ratingList");
							session.removeAttribute("updateList");
							session.removeAttribute("approve");
							session.removeAttribute("update");
							
							
						}
																
					}
					else if(pageid.equals("2000309"))
					{
						session.setAttribute("PIrecStat", "PIrecStat");
						session.removeAttribute("PCrecStat");
						session.removeAttribute("PArecStat");
						session.removeAttribute("PFrecStat");
						logger.info("--page function id-In Create Individual Customer----"+pageid);
						session.removeAttribute("pageStatuss");
						session.removeAttribute("pageInfo");
						session.removeAttribute("applType");
						session.removeAttribute("corporateId");
						session.removeAttribute("idividualId");
						session.removeAttribute("updateFlag");
						session.removeAttribute("corporate");
						session.setAttribute("individual", "I");
						session.removeAttribute("update");
						session.removeAttribute("updateInMaker");
						session.removeAttribute("CUA");
						session.removeAttribute("statusCase");
						session.removeAttribute("underWriter");
						 session.removeAttribute("underWriterViewData");
						 session.removeAttribute("viewBlackList");
						 session.removeAttribute("searchLoan");
						// logger.info("In requestedPageID"+requestedPageID);
						logger.info("In operation: "
								+ request.getParameter("operation"));

						if (request.getParameter("hideId") != null
								&& request.getParameter("operation") != null) {
							session.setAttribute("idividualId", request
									.getParameter("hideId"));

							session.setAttribute("operation", request
									.getParameter("operation"));
						} else {
							session.removeAttribute("pageInfo");
							session.removeAttribute("idividualId");
							session.removeAttribute("operation");
							session.removeAttribute("individualInfo");
							session.removeAttribute("addressInfo");
							session.removeAttribute("statesList");
							session.removeAttribute("citiesList");
							session.removeAttribute("detailList");
							session.removeAttribute("approve");
							session.removeAttribute("update");
							session.removeAttribute("searchLoan");
						}
																		
					}
					else if(pageid.equals("2000310"))
					{
						session.setAttribute("PArecStat", "PArecStat");
						session.setAttribute("viewBlackList", "viewBlackList");
						session.removeAttribute("PFrecStat");
						session.removeAttribute("PIrecStat");
						session.removeAttribute("PCrecStat");
						logger.info("--page function id-In Update Customer - Maker----"+pageid);
						session.removeAttribute("pageStatuss");
						session.removeAttribute("pageInfo");
						session.removeAttribute("applType");
						session.removeAttribute("corporateId");
						session.removeAttribute("idividualId");
						session.removeAttribute("updateFlag");
						logger.info("In requestedPageID" + requestedPageID);
						session.setAttribute("update", "update");
						session.removeAttribute("updateInMaker");
						session.removeAttribute("CUA");
						session.removeAttribute("approve");
						session.removeAttribute("corporate");
						session.removeAttribute("individual");	
						session.removeAttribute("underWriter");
						session.removeAttribute("underWriterViewData");
						session.removeAttribute("searchLoan");
						session.removeAttribute("statusCase");	
						session.removeAttribute("groupTypeActivated");
					}
					else if(pageid.equals("2000311"))
					{
						session.setAttribute("PFrecStat", "PFrecStat");
						session.removeAttribute("PArecStat");
						session.removeAttribute("PIrecStat");
						session.removeAttribute("PCrecStat");
						logger.info("--page function id-In Update Customer - Author----"+pageid);
						session.removeAttribute("pageStatuss");
						session.removeAttribute("pageInfo");
						session.removeAttribute("updateFlag");
						session.removeAttribute("applType");
						request.removeAttribute("applType");
						session.removeAttribute("corporateId");
						session.removeAttribute("idividualId");
						logger.info("In requestedPageID" + requestedPageID);
						session.setAttribute("approve", "approve");
						session.removeAttribute("update");
						session.removeAttribute("updateInMaker");
						session.removeAttribute("CUA");
						session.removeAttribute("corporate");
						session.removeAttribute("individual");
						session.removeAttribute("statusCase");		
						session.removeAttribute("underWriter");
						 session.removeAttribute("underWriterViewData");
						 session.setAttribute("viewBlackList", "viewBlackList");
						 session.removeAttribute("searchLoan");
						 session.removeAttribute("groupTypeActivated");
					}
					else if(pageid.equals("4000631"))
					{
						logger.info("manualApprove");
						session.setAttribute("manualApprove", "manualApprove");
						session.removeAttribute("searchLoan");
					}
					else if(pageid.equals("4000626"))
					{
						session.removeAttribute("manualAdviceList");
						session.removeAttribute("adviceUpdate");
						session.removeAttribute("manualApprove");
						session.removeAttribute("searchLoan");
					}
					else if(pageid.equals("2000226"))
					{
						request.setAttribute("First", "First");
					
					}
					else if(pageid.equals("4000906"))
					{
						logger.info("--page function id-In EarlyClosure----"+pageid);
						logger.info("To view Loan Details From Early Closure");
						session.setAttribute("cmAuthor", "A");
						session.setAttribute("viewLoan", "V");
						session.removeAttribute("searchLoan");
						session.removeAttribute("approvedBY");
					}
					else if(pageid.equals("4000916"))
					{
						logger.info("--page function id-In MAturity Closure----"+pageid);
						logger.info("To view Loan Details From MAturity Closure");
						session.setAttribute("cmAuthor", "A");
						session.setAttribute("viewLoan", "V");
						session.removeAttribute("searchLoan");
					}
					else if(pageid.equals("4000926"))
					{
						logger.info("--page function id-In Cancellation----"+pageid);
						logger.info("To view Loan Details From Cancellation");
						session.setAttribute("cmAuthor", "A");
						session.setAttribute("viewLoan", "V");
						session.removeAttribute("searchLoan");
					}
					else if(pageid.equals("4000911"))
					{
						logger.info("--page function id-In Early Closure Author----"+pageid);
						logger.info("To view Loan Details From Early Closure Author");
						session.setAttribute("cmAuthor", "A");
						session.setAttribute("viewLoan", "V");
						session.removeAttribute("searchLoan");
					}
					else if(pageid.equals("4000921"))
					{
						logger.info("--page function id-In MAturity Closure Author----"+pageid);
						logger.info("To view Loan Details From Maturity Closure Author");
						session.setAttribute("cmAuthor", "A");
						session.setAttribute("viewLoan", "V");
						session.removeAttribute("searchLoan");
					}
					else if(pageid.equals("4000146"))
					{
						logger.info("--page function id-In docs collection in cm maker----"+pageid);
						logger.info("To view Loan Details From Cancellation");
						session.removeAttribute("cmAuthor");
						session.removeAttribute("searchLoan");
					}
					else if(pageid.equals("4001231"))
					{
						logger.info("--page function id-In search all loan in cm ----"+pageid);
						logger.info("To view Loan Details From ");
						 session.setAttribute("cmAuthor", "A");
						 session.setAttribute("searchLoan", "L");
						 session.removeAttribute("dealId");
						 session.removeAttribute("DealCap");
						 session.setAttribute("underWriter", "underWriter");
						 session.setAttribute("underWriterViewData","underWriterViewData");
						 session.setAttribute("viewDeviation","viewDeviation");
						 session.setAttribute("viewDeviationUND","viewDeviationUND");
						 session.setAttribute("viewDevJSP", "viewDevJSP");
						 session.removeAttribute("cmdocupload");
						 session.setAttribute("specialConditionDV", "specialConditionDV");
						 session.removeAttribute("verifCPS");
						 session.setAttribute("verifCMS","verifCMS");
					}
					else if(pageid.equals("3000951"))
					{
						logger.info("--page function id-In search all deal in cp ----"+pageid);
						logger.info("To view Deal Details From ");
						 session.removeAttribute("loanId");
						 session.removeAttribute("dealId");
						 session.removeAttribute("DealCap");
						 session.setAttribute("underWriter", "underWriter");
						 session.setAttribute("underWriterViewData","underWriterViewData");
						 session.removeAttribute("viewBlackList");
						 session.removeAttribute("searchLoan");
						 session.setAttribute("viewDeviation","viewDeviation");
						 session.setAttribute("groupTypeActivated","groupTypeActivated");
						 session.removeAttribute("queryResponse");
						 session.removeAttribute("viewDeviationUND");
						 session.removeAttribute("cmdocupload");
						 session.setAttribute("specialConditionDV", "specialConditionDV");
						 session.removeAttribute("verifCMS");
						 session.setAttribute("verifCPS","verifCPS");
					}
					//Nishant space starts
					else if(pageid.equals("3000953"))
					{
						logger.info("--page function id-In search all deal in cp ----"+pageid);
						logger.info("To view Deal Details From ");
						 session.removeAttribute("loanId");
						 session.removeAttribute("dealId");
						 session.removeAttribute("DealCap");
						 session.setAttribute("underWriter", "underWriter");
						 session.setAttribute("underWriterViewData","underWriterViewData");
						 session.removeAttribute("viewBlackList");
						 session.removeAttribute("searchLoan");
						 session.setAttribute("viewDeviation","viewDeviation");
						 session.removeAttribute("viewDeviationUND");
						 session.setAttribute("groupTypeActivated","groupTypeActivated");
						 session.removeAttribute("queryResponse");
					}
					else if(pageid.equals("3000954"))
					{
						logger.info("--page function id-In search all deal in cp ----"+pageid);
						logger.info("To view Deal Details From ");
						 session.removeAttribute("loanId");
						 session.removeAttribute("dealId");
						 session.removeAttribute("DealCap");
						 session.setAttribute("underWriter", "underWriter");
						 session.setAttribute("underWriterViewData","underWriterViewData");
						 session.removeAttribute("viewBlackList");
						 session.removeAttribute("searchLoan");
						 session.setAttribute("viewDeviation","viewDeviation");
						 session.removeAttribute("viewDeviationUND");
						 session.setAttribute("groupTypeActivated","groupTypeActivated");
						 session.removeAttribute("queryResponse");
					}
					//Nishant space end
					else if(pageid.equals("3000262"))
					{
						session.setAttribute("fundFlowAuthor", "A");
						session.removeAttribute("fundFlowDealId");
						session.removeAttribute("fundFlowDealNo");
						session.removeAttribute("customerName");
					
					}
					else if(pageid.equals("3000261"))
					{
						session.removeAttribute("fundFlowAuthor");
						session.removeAttribute("fundFlowDealId");
						session.removeAttribute("fundFlowDealNo");
						session.removeAttribute("customerName");
						session.removeAttribute("underWriterViewData");
						
					}
					else if(pageid.equals("3000106"))
					{
						logger.info("--page function id-In Lead Capturing----"+pageid);
						
						session.removeAttribute("allocation");
						session.setAttribute("capturing", "capturing");
						session.setAttribute("allocationid", requestedPageID);
						session.setAttribute("leadpageid", pageid);
						session.removeAttribute("leadDetails");
						session.removeAttribute("list");
						session.removeAttribute("savedLead");
						
					}
					else if(pageid.equals("3000111"))
					{
						logger.info("--page function id-In Lead Allocation----"+pageid);
						session.removeAttribute("allocationid");
						session.removeAttribute("capturing");
						session.setAttribute("allocation", "allocation");
						session.setAttribute("allocationid", requestedPageID);
						session.setAttribute("leadpageid", pageid);
						session.removeAttribute("leadDetails");
						session.removeAttribute("list");
						session.removeAttribute("savedLead");
					}
					else if(pageid.equals("3000116"))
					{
						logger.info("--page function id-In Lead Tracking----"+pageid);
						session.removeAttribute("allocationid");
						
						session.setAttribute("allocationid", requestedPageID);
						session.setAttribute("leadpageid", pageid);
						session.removeAttribute("savedLead");
					}
					else if(pageid.equals("3000276"))
					{
						logger.info("--page function id-----"+pageid);
						session.removeAttribute("financialDetails");
						session.removeAttribute("balanceSheetAllDetailsByDeal");
						session.removeAttribute("profitLossAllDetailsByDeal");
						session.removeAttribute("otherAllDetailsByDeal");
						session.removeAttribute("balanceSheetParamCodeValues");
						session.removeAttribute("profitLossParamCodeValues");
						session.removeAttribute("othersParamCodeValues");
						session.removeAttribute("financialDealId");
						session.removeAttribute("underWriterViewData");
					}
					else if(pageid.equals("3000288"))
					{
						logger.info("--page function id-----"+pageid);
						session.removeAttribute("financialDetails");
						session.removeAttribute("incomeDetailsByDeal");
						session.removeAttribute("financialDealId");
						
					
					}
					else if(pageid.equals("1000302"))
					{
						logger.info("--page function id-----"+pageid);
						session.removeAttribute("maxBusinessDate");
					
					}
					else if(pageid.equals("3000294"))
					{
						logger.info("--page function id- devation approval----"+pageid);
						 session.removeAttribute("loanId");
						 session.removeAttribute("dealId");
						 session.removeAttribute("DealCap");
						 session.setAttribute("underWriter", "underWriter");
						 session.setAttribute("underWriterViewData","underWriterViewData");
						 session.removeAttribute("viewBlackList");
						 session.removeAttribute("searchLoan");
						 session.removeAttribute("viewDeviation");
						 session.removeAttribute("viewDeviationUND");
						 // Start By Prashant
						 session.removeAttribute("strFlagDV");
						 session.removeAttribute("queryResponse");
						 // End By Prashant
					}
					else if(pageid.equals("3000295"))
					{
						logger.info("--page function id- upload document----"+pageid);
						 session.removeAttribute("loanId");
						 session.removeAttribute("underWriter");
						 //sachin
						 session.removeAttribute("cmdocupload");
						//sachin
					
					} 
					else if(pageid.equals("4000406"))
					{
						logger.info("--page function id- instrumentCapBehindAction  document----"+pageid);
						 session.removeAttribute("arrList");
						
					} 

					// vishal start
					else if(pageid.equals("4000870"))
					 {
						logger.info("--page function id- Update Repayschedule-Maker  ----"+pageid);
						session.removeAttribute("viewDueDate");
						session.setAttribute("makerSatge", "M");
								
					 } 
					else if(pageid.equals("4000890"))
					{
						logger.info("--page function id- Update Repayschedule-Author  ----"+pageid);
						session.setAttribute("viewDueDate","viewDueDate");
						session.removeAttribute("makerSatge");
											
					} 
					// vishal end
					//Prashant start here
					else if(pageid.equals("3000209"))
					{
						logger.info("--page function id-In Cibil Report----"+pageid);
						 session.removeAttribute("loanId");
						 session.removeAttribute("dealId");
						 session.removeAttribute("DealCap");
						 session.removeAttribute("underWriter");
						 session.removeAttribute("viewBlackList");
						 session.removeAttribute("searchLoan");
						 session.setAttribute("underWriterViewData","underWriterViewData");
							
					}
					else if(pageid.equals("4000152"))
					{
						logger.info("--page function id-Note pad in CM----"+pageid);
						session.removeAttribute("DealCap");
						session.removeAttribute("dealId");
						session.removeAttribute("maxId");
						session.removeAttribute("viewDeal");
						session.removeAttribute("cmAuthor");	
						session.removeAttribute("viewLoan");
						session.removeAttribute("searchLoan");
						 															
					}
					
					//Prashant end here
					
					
					//Anil start here
					
					else if(pageid.equals("4000893"))
					{
						logger.info("--page function id-Rate Change Maker in CM----"+pageid);
						session.removeAttribute("list");
						session.removeAttribute("Author");
						session.removeAttribute("LoanId");
						
		 															
					}
					
					//Anil end here
					// Start By Prashant
					else if(pageid.equals("3000360"))
					{
						logger.info("--page function id- query response----"+pageid);
						 session.removeAttribute("loanId");
						 session.removeAttribute("dealId");
						 session.removeAttribute("DealCap");
						 session.setAttribute("underWriter", "underWriter");
						 session.setAttribute("underWriterViewData","underWriterViewData");
						 session.removeAttribute("viewBlackList");
						 session.removeAttribute("searchLoan");
						 session.setAttribute("viewDeviation","viewDeviation");
						 session.removeAttribute("viewDeviationUND");
						 session.removeAttribute("strFlagDV");
						 session.setAttribute("queryResponse","queryResponse");
						 session.setAttribute("specialConditionDV", "specialConditionDV");
					}
					else if(pageid.equals("4000109"))
					{
						logger.info("--page function id-In Deviation Author at Loan----"+pageid);
						session.removeAttribute("loanInit");
						session.removeAttribute("dealId");
						session.removeAttribute("maxId");
						session.removeAttribute("DealCap");
						session.removeAttribute("viewDeal");
						session.removeAttribute("viewLoan");
						session.setAttribute("cmAuthor", "A");		
						session.removeAttribute("searchLoan");
						session.setAttribute("groupTypeActivated","groupTypeActivated");
						session.setAttribute("viewDeviation","viewDeviation");
						session.removeAttribute("viewDeviationUND");
						session.removeAttribute("viewDevJSP");
					}
					else if(pageid.equals("4000114"))
					{
						logger.info("--page function id-In disbursal plan maker at Loan----"+pageid);
						session.removeAttribute("loanInit");
						session.removeAttribute("dealId");
						session.removeAttribute("maxId");
						session.removeAttribute("DealCap");
						session.removeAttribute("viewDeal");
						session.removeAttribute("viewLoan");
						session.removeAttribute("cmAuthor");		
						session.removeAttribute("searchLoan");
						session.removeAttribute("viewDevJSP");
					}
					 // End By Prashant
					
					//sachin
					else if(pageid.equals("4000112")){
						session.setAttribute("cmdocupload","cmdocupload");
						session.removeAttribute("underWriter");
					}
					//sachin	
					else if(pageid.equals("9000160")){
						session.setAttribute("cmdocupload","cmdocupload");
						session.removeAttribute("underWriter");
					}
					// Amit Quality Check Starts
					else if(pageid.equals("3000217"))
					{
						logger.info("--page function id-In search all deal in cp ----"+3000217);
						logger.info("To view QualityCheck From Deal");
						 session.removeAttribute("loanId");
						 session.removeAttribute("dealId");
						 session.removeAttribute("DealCap");
						 session.setAttribute("underWriter", "underWriter");
						 session.setAttribute("underWriterViewData","underWriterViewData");
						 session.removeAttribute("viewBlackList");
						 session.removeAttribute("searchLoan");
						 session.setAttribute("viewDeviation","viewDeviation");
						 session.setAttribute("stage","F");
					}
					else if(pageid.equals("4000103"))
					{
						logger.info("--page function id-In search all deal in cm ----"+4000103);
						logger.info("To view QualityCheck From Loan");
						 session.removeAttribute("loanId");
						 session.removeAttribute("dealId");
						 session.removeAttribute("DealCap");
						 session.removeAttribute("stage");
						 session.setAttribute("underWriter", "underWriter");
						 session.setAttribute("underWriterViewData","underWriterViewData");
						 session.removeAttribute("viewBlackList");
						 session.removeAttribute("searchLoan");
						 session.setAttribute("viewDeviation","viewDeviation");
						 session.setAttribute("stage","A");
					}
					//Amit Quality Check Ends
					
                     //Anil EMI CAlculator Start
					
					else if(pageid.equals("3000955"))
					{
						logger.info("--page function id-In Under Writer----"+pageid);
						 session.removeAttribute("dealId");
						 session.removeAttribute("loanList");
						 session.removeAttribute("dealHeader");
						 session.removeAttribute("editLoanDetailsForEmiCalc");
					}
					
					//Anil EMI CAlculator End
					//Surendra Code
					else if(pageid.equals("4001240"))
					{
						logger.info("--page function id-In search all deal in cm ----"+4000119);
					
						 session.removeAttribute("arrList1");
						 session.removeAttribute("arrList2");
						 session.removeAttribute("sessionLoanId");
						 session.removeAttribute("disbCancellationAuthorWithDetails");
						 	
					}
					else if(pageid.equals("4001241"))
					{
						logger.info("--page function id-In search all deal in cm ----"+4000120);
						
						session.removeAttribute("arrList1");
						 session.removeAttribute("arrList2");
						 session.removeAttribute("sessionLoanId");
						 session.removeAttribute("disbCancellationAuthorWithDetails");
					}
					/*else if(pageid.equals("3000216")||pageid.equals("3000221")||pageid.equals("3000226") ||pageid.equals("3000220"))
					{
						 logger.info("--page function id-In verification/initiation/capturing/completion in cp ----"+pageid);
						 session.removeAttribute("verifCMS");
						 session.removeAttribute("viewDeal");
						 session.setAttribute("verifCPS","verifCPS");
						 session.removeAttribute("underWriter");
					}
					else if(pageid.equals("4000132")||pageid.equals("4000133")||pageid.equals("4000134"))
					{
						 logger.info("--page function id-In verification/initiation/capturing/completion in cm ----"+pageid);
						 session.removeAttribute("verifCPS");
						 session.removeAttribute("viewDeal");
						 session.setAttribute("verifCMS","verifCMS");
					}*/
					else if(pageid.equals("3000216")||pageid.equals("3000221")||pageid.equals("3000226")  ||pageid.equals("3000220")
							||pageid.equals("8000301")||pageid.equals("8000302")||pageid.equals("8000303")
							||pageid.equals("8000304")||pageid.equals("8000305")||pageid.equals("8000306")||pageid.equals("10000830")||pageid.equals("10000831"))
					{
						 logger.info("--page function id-In verification/initiation/capturing/completion in cp ----"+pageid);
						 session.removeAttribute("verifCMS");
						 session.removeAttribute("viewDeal");
						 session.setAttribute("verifCPS","verifCPS");
						 session.removeAttribute("underWriter");
					}
					else if(pageid.equals("4000132")||pageid.equals("4000133")||pageid.equals("4000134") 
							||pageid.equals("8000237")||pageid.equals("8000238")
							||pageid.equals("8000239")||pageid.equals("8000240") ||pageid.equals("8000241")||pageid.equals("8000242"))
					{
						 logger.info("--page function id-In verification/initiation/capturing/completion in cm ----"+pageid);
						 session.removeAttribute("verifCPS");
						 session.removeAttribute("viewDeal");
						 session.setAttribute("verifCMS","verifCMS");
					}
					else if(pageid.equals("3000359"))
					{
						logger.info("--page function id-In search Query Initiation in cp ----"+3000359);
						
						session.removeAttribute("strFlagDV");
						session.removeAttribute("showList");
					}
					
					else if(pageid.equals("4000153")||pageid.equals("4001234")||pageid.equals("4000125")||pageid.equals("4000253"))
					{
						logger.info("--page function id-In file tracking in cm ----"+pageid);
						
						 session.setAttribute("cmAuthor", "A");
						 session.setAttribute("searchLoan", "L");
						 session.removeAttribute("dealId");
						 session.removeAttribute("DealCap");
						 session.setAttribute("underWriter", "underWriter");
						 session.setAttribute("underWriterViewData","underWriterViewData");
						 session.setAttribute("viewDeviation","viewDeviation");
						 session.removeAttribute("viewDeviationUND");
						 session.setAttribute("viewDevJSP", "viewDevJSP");
						 session.removeAttribute("cmdocupload");
						 session.setAttribute("specialConditionDV", "specialConditionDV");
						 session.removeAttribute("verifCPS");
						 session.setAttribute("strFlagDV", "strFlagDV");
						 session.setAttribute("verifCMS","verifCMS");
					}
					
					
								
			   }
					else if(pageid.equals("4000116"))
					{
						logger.info("--page function id-In search Disbursal maker in cm ----"+4000116);
						session.removeAttribute("disbursalInitionAuthor");
						session.removeAttribute("loanId");
						session.removeAttribute("maxIdInCM");
					}
					
				session.removeAttribute("dealHeader");
			}
		}
		if(!pageid.equals("3000206") || !pageid.equals("3000106"))
		{
			logger.info("--to remove userNameForProAtLe from lead and deal in cm ----"+3000206);
			session.removeAttribute("userNameForProAtLe");
			
		}
	    	logger.info("get pageResult "+pageResult);
			return mapping.findForward(pageResult);
	}

}

