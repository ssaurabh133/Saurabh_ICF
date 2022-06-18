package com.cp.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;

import com.cp.vo.DisbursalSearchVO;
import com.cp.vo.InstallmentPlanForCMVO;
import com.cp.vo.ObligationVo;
import com.cp.vo.OtherChargesPlanVo;
import com.cp.vo.SecurityDepositVO;
import com.cp.vo.ApplicantTypeVO;
import com.cp.vo.BuyerSupplierOtherVo;
import com.cp.vo.BuyerVo;
import com.cp.vo.ChargeVo;
import com.cp.vo.CodeDescVo;
import com.cp.vo.CollateralVo;
import com.cp.vo.CommonDealVo;
import com.cp.vo.CommonPageSecVo;
import com.cp.vo.ConsumerVo;
import com.cp.vo.CreditProcessingCustomerEntryVo;
import com.cp.vo.CreditProcessingNotepadVo;
import com.cp.vo.DealDisbursalVo;
import com.cp.vo.ExistingAssetVo;
import com.cp.vo.FieldVerificationVo;
import com.cp.vo.LeaddetailDealVo;
import com.cp.vo.LoanedProductVo;
import com.cp.vo.RelationalManagerVo;
import com.cp.vo.LinkCustomerVo;
import com.cp.vo.ScoringVO;
import com.cp.vo.SpecialConditionVo;
import com.cp.vo.UnderwriterApprovalVo;
import com.cp.vo.UnderwritingDocUploadVo;
import com.cp.vo.UploadTypeVo;
import com.cp.vo.Viability;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.masters.vo.GcdGroupMasterVo;
import com.cp.vo.TermSheetVo;



public interface OcrDAO {


			
	 String IDENTITY="OCR"; 		


	ArrayList<CodeDescVo> getBranchList();
	 ArrayList<Object> getsourceTypeList();
	 ArrayList getProductTypeList();
	 ArrayList getBaseRateList(String businessdate);
	 ArrayList<LoanedProductVo> getLoanListOfProduct(String dealID);
	 ArrayList<CodeDescVo> getProductList(String productType);
	 ArrayList<CodeDescVo> getSchemeList(String product);
	 ArrayList<RelationalManagerVo> getRelationalManagerList(String branchId);

	 int saveCPLeadEntry(Object vo);
	
		
	  public boolean saveBuyerDetails(Object ob);
		public ArrayList<Object> getBuyerDetailsAll(String bpType,String bRelation,String id);	
		public ArrayList<Object> modifyBuyerDetailsAll(String bptype,String addrId);
		public boolean updateBuyerDetailsAll(Object ob,String bptype,String addrId);	
		public boolean deleteBuyerDetails(String[] id,String bpType);
		public boolean saveSupplierDetails(Object ob);	
		public ArrayList<Object> getSupplierDetailsAll(String bpType1,String srelation,String id);
		public ArrayList<Object> modifySupplierDetailsAll(String bpType1,String addrId);
		public boolean updateSupplierDetailsAll(Object ob,String bpType1,String addrId);	
		public boolean deleteSupplierDetails(String[] id,String bpType1);


	
	public boolean saveCollateralDetails(Object ob);	
	public ArrayList<Object> getCollateralDetailsAll(String dealId);
	public ArrayList<Object> fetchCollateralDetailsAll(String primaryId ,String propValue, String source);
	public ArrayList<Object> getSecurityType();
	public boolean updateCollateralDetailsAll(Object ob,String primaryId,String assetType, String source);
	public boolean deleteCollateralDetails(String assetId, String type, String source);
	 ArrayList<Object> getAddressList();
	 ArrayList<Object> getBusinessPartnerTypeList();
		


  	ArrayList<Object> getApplicantDetailAll();
	
	

	 
	 boolean saveNotepadData(Object ob);

	 ArrayList getNoteCode();
	
	 ArrayList getLoanDetailList(String dealId);
	 ArrayList<Object> getLeadEntryList(String leadId);
	 String getBaseRate(String baseRateType,String bDate);
	 ArrayList getLoanDetailScheme(String scheme, String bdate);
	 ArrayList getAllLoanDetails(String scheme);
	 boolean saveLoanDetails(Object ob);	
	 boolean deleteLoanDetails(String[] id);

	 boolean saveDocument(Object vo, String source);

	


	 String collectDocuments(String txnType,String dealId,String stage, String source);



	ArrayList getDealHeader(String id);
	
	String getAppraiserType(String dealId,String verificationId);

    boolean saveCharges(Object ob);
   
    ArrayList getchargesDetail(String dealCap,String dealId); //dealCapturing,deal ID,loanamount,marginamount,customerId,productid,schemeid

    
    ArrayList fetchHeader(String id);


	String checkStage(String dealId);


	boolean stageForward(String dealId,String bDate);


	ArrayList<Object> fetchDealDetail(CommonDealVo vo);
	
	ArrayList getLeadNotepadDataCP(String txnid,String txnType);
	ArrayList getLeadNotepadData(String txnid,String txnType);
	ArrayList getNotepadData(String txnid,String txnType);
//Ritu
	ArrayList getNotepadDataForNotepad(String txnid);


	ArrayList getUnderWriterData(String id);
	ArrayList getCreditDecisionData(String dealNo);
	String getCustomerExposureLimitCheck(CommonPageSecVo vo);
	String getGroupExposureLimitCheck(CommonPageSecVo vo);
	double updateCreditDecision(Object ob);
	boolean saveUnderwritingQueryData(Object ob,String dealId);
	ArrayList getUnderwritingQueryData(String dealId);


	ArrayList showUnderwritingQueryData(Object ob);
	boolean updateUnderwritingQueryData(Object ob,String dealId);
	int uploadUnderwritingData(Object ob);
	
	boolean checkUploadFeasibility(String dealId);
	ArrayList getUploadUnderwritingData(String dealId);
	
	boolean deleteDocumentUploadEntry(String docName,String id);
	boolean updateDocumentUploadEntry(String documentName,String dealId);
	String getFileInfo(String dealId,String fileName);
	
	
	
   

	//boolean saveCreditProcessingCustomerEntry(CreditProcessingCustomerEntryVo vo);
    public ArrayList<ApplicantTypeVO> getApplicantList();
    ArrayList<Object> getApplicantDetailAll(String dealId);
	int deleteCustomerEntry(String id);
	


	
	 ArrayList<Object> searchApplicant(LinkCustomerVo lo);

	 boolean saveApprovalData(Object ob);
	 ArrayList getApprovalData(String dealId,String role);
	 String updateApprovalData(Object ob);
	 boolean checkApplicationFormNo(String appNo,String dealId);
	ArrayList getAssets(ExistingAssetVo sh);
	ArrayList<Object> getAssetsAll(String asset);
	ArrayList<Object> getCollateralsAll(String collateral);
	ArrayList getApplicationDocuments(String entityType,String commonId,String stage, String txnType, String source);
	ArrayList getAllApplicantDocs(String entityType,String commonId,String stage, String txnType, String source);
	ArrayList getAssetCollateralDocuments(String entityType,String commonId,String stage, String txnType, String source);
	ArrayList getchargesDetailInCM(String string, String loanId);
	
	ArrayList getChildDocs(String docId);
	
//avinash start
	 ArrayList<ConsumerVo> getCibilData(Object ob);
	 int saveCibilData(Object ob);
	 boolean deleteCibilEntry(String[] s1,String dealId);
	 ArrayList refreshchargesDetail(String dealCap,String dealId);
	 
	//avinash end
	 
	 //Manisha Start
	 ArrayList<CreditProcessingNotepadVo>showPDData(CreditProcessingNotepadVo pdVo);
	 boolean savePersonalDiscussion(CreditProcessingNotepadVo pdVo);
	 
	//Search CM
	 	ArrayList<DisbursalSearchVO> searchCPGrid(DisbursalSearchVO vo);
	 	//Scoring
	 	ArrayList<ScoringVO>scoringSearchGrid(ScoringVO scoringVo);
	 	ArrayList<ScoringVO>getScoringList(ScoringVO scoringVo);
	 	boolean updateOnSaveScore(ScoringVO scoringVo);
	 //Manisha End
	 ArrayList getSectorList();
	 //@Surendra Code for Interest Calc.
	 ArrayList getinterstCalcFrom();
	 //@Surendra End Code
	ArrayList getMakerData(String dealId);
	ArrayList getNotepadDataInLoanInit(String loanId,String txnType);

	
	boolean saveGuaranteeAmount(Object vo);
	
	ArrayList getTenureAmountInDeal(String dealId);
	ArrayList getInstallTypeInDeal(String dealId);
	ArrayList getCycleDateList(String dealId,String stage);
	ArrayList getCycleDueDay(String scheme,String stage);
	ArrayList<Object> getSecurityDetailAllInDeal(String dealId, String functionId);
	boolean deleteSecurityDepositInDeal(String[] id);
	boolean saveSecurityDepositInDeal(SecurityDepositVO vo);
	boolean updateInstallPlanInDeal(InstallmentPlanForCMVO ipvo);
	boolean saveInstallPlanInDeal(InstallmentPlanForCMVO ipvo);
	String generateRepyScheduleInDeal(String dealId,String makerId);
	ArrayList getFromLoanDtlInDeal(String dealId);
	ArrayList getRepaySchedInDeal(String dealId);
	boolean deleteChargeInstallmentRepay(String dealId, String[] dealLoanId);
	ArrayList<Object> getDealCatList();
	boolean deleteApplDocs(String dealId, String[] dealLoanId);
	boolean deleteCollateralAssetDocs(String dealId, String[] id, String[] type,String tabType);

	int dealExisForBuySupp(BuyerVo sh);
	boolean forwardDealforbuySupp(BuyerVo sh);

	ArrayList retriveLeadInfoValues(String lbxLeadNo);
	boolean saveBuyersupplierAuthorDetails(BuyerVo sh);
	ArrayList searchDealDetailforBuyerSupp(FieldVerificationVo vo,
			HttpServletRequest request);
	ArrayList searchDealDetailforBuyerSuppAuthor(FieldVerificationVo vo,
			HttpServletRequest request);

	ArrayList getDealMovementDetail(String txnid,String userId);
	ArrayList docUploadedDetailInSearch(CommonDealVo vo,HttpServletRequest request);
	
	String callProcedure(String DC,String dealId);
	String callRefreshChargesDetailPro(String string, String dealId);
	ArrayList getworkFlowStage(String dealId,String funId);
	boolean updatesecuritydeposit(ChargeVo vo, String dealId);
	

	// Loan type List
	public ArrayList getLoanTypeList();
	ArrayList getAssetLoanDetailAmount(String dealId);
	ArrayList getDealWithCibilReport(CommonDealVo vo, HttpServletRequest request);
	boolean uploadCibilReportData(ConsumerVo consumerVo);
	ArrayList getUploadCibilData(String dealId);
	String getFileInfoOFCibilReport(String dealId, String fileName);
	ArrayList<LeaddetailDealVo> CustomerDetailsList(String leadId);
	String saveLeadCustomerDetails(LeaddetailDealVo vo);
	boolean saveLeadCustomerAddressDetails(LeaddetailDealVo vo);
	boolean linkLeadCustomer(LeaddetailDealVo vo);
	boolean updateDealCustomer(LeaddetailDealVo vo);

	ArrayList getProductSchemeDetailsFromLead(String product, String scheme,
			String loanTenure, String loanAmount, String loanPurpose,String bdate, String loanType,String sectorType);
	
	ArrayList getDealVerificationMovementDetail(String txnid,String userId);
	String getEditableFlag();
    //added by neeraj tripathi
	String getDefaultAccountType();



	 //Start By Prashant
	int getProductCategaryStatus(String dealId, String tabName);
	String saveFleetInCP(Object vo);
	ArrayList getFleetList(String dealId);
	ArrayList getAllFleetList(String dealFleetId);
	boolean deleteFleetDtl(String[] dealFleetId);
	 //End By Prashant
	boolean updateDealAreaCodeFromLead(LeaddetailDealVo vo);
	
	
	
	
	
	
	
	
	//Added by Abhimanyu	
	String getProductType(String DealId);
	ArrayList getViability(String DealId);	
	ArrayList<Viability> saveViability(Viability vo);
	int getViabilityCount(String dealid);
	boolean deleteViabilityDtl(String dealid);
//Added by Abhimanyu

// Prashant
	ArrayList getLtvFromMakeMod(String assetNature, String txtProductCat,String makeModelId,String branchId);
	ArrayList getGroupExposureData(String dealId,String customerId, String hGroupId, String groupType);
	String saveGroupExposureLimit(GcdGroupMasterVo groupVo);
	ArrayList getGroupExpLimitGrid(String dealId,String userId,String opUserId,String businessDate);
	ArrayList fetchDueDayNextDate(String repayEffectiveDate);
	ArrayList fetchDueDay(String repayEffectiveDate, String cycleDate);

	//Changes By Amit Starts
	ArrayList getAdditionalDocs(String entityType, String commonId, String stage, String txnType, String source);
	//Changes By Amit Ends


	// Anil
	ArrayList getEMIAmount(String dealId);
	boolean checkEMIAmountInViability(String dealId);
	//prashant
	public ArrayList<CommonDealVo> queryResponseSearchDetail(CommonDealVo vo);
	boolean saveQueryResponseData(Object cr);
	ArrayList getQueryResponseData(String dealId,String userId,String queryStatus);
	public ArrayList getNoOfDisbAtUnderWriter(String dealId);
	

	//ravi start
	boolean saveOtherChargesPlanInDeal(OtherChargesPlanVo ipvo);
	public ArrayList getOtherPeriodicalChargeDetailInDeal(String dealId,String stage);
	//ravi end
	
	//sachin
	ArrayList getDealHeaderForCm(String id);

	boolean uploadUnderwritingDataForCm(Object ob);
	
	boolean uploadDocumentDataForFVC(Object ob,String stage);

	ArrayList getUploadUnderwritingDataForCm(String dealId);

	boolean deleteDocumentUploadEntryForCm(String docName,String id);

	String getFileInfoForCm(String dealId,String fileName);

	ArrayList docUploadedDetailInSearchForCm(CommonDealVo vo,HttpServletRequest request);
	ArrayList docUploadedDetailInSearchForLegal(CommonDealVo vo,HttpServletRequest request);
	
	ArrayList getApplicationDealDocuments(String entityType,String commonId,String stage, String txnType);
	
	ArrayList getAdditionalDealDocs(String entityType, String commonId, String stage, String txnType);
	
	ArrayList getAllApplicantDealDocs(String entityType,String commonId,String stage, String txnType);
	
	ArrayList getAssetCollateralDealDocuments(String entityType,String commonId,String stage, String txnType);
	
	ArrayList getUnderWriterDealDetail(String txnid,String userId);
	
	ArrayList getDeviationDealDetail(String txnid);
	//end by sachin
	String saveSpecialCondition(SpecialConditionVo vo);
	ArrayList getSpecialConditionList(String dealId);
	ArrayList fecthConditionList(String specialConditionId);
	String updateSpecialCondition(SpecialConditionVo vo);
	boolean deleteSpecialCondition(String[] specialId);
	boolean saveDealDisb(DealDisbursalVo nvo);
	ArrayList getDealDisbScheduleList(String dealId);
	String updateSpecialConditionRemarks(SpecialConditionVo vo);
	ArrayList getShowSpecialConditionList(String loanId);
	String checkAssetVerifInit(String[] id, String dealId);
	boolean deleteVerificationInitAsset(String commonId, String[] id,String type);
	String checkBuyerVerifInit(String[] id, String dealId, String bptype);
	boolean deleteVerificationInitBuyer(String dealId, String[] id, String bptype);
	
	String checkSupplierVerifInit(String[] id, String dealId, String bptype);
	boolean deleteVerificationInitSupplier(String dealId, String[] id, String bptype);
	boolean deleteMarketVerif(String dealId);
	boolean checkUploadFeasibilityCM(String dealId);
	boolean checkUploadFeasibilityFVC(String dealId,String stage);
	ArrayList getUploadUnderwritingDataForCmCp(String dealId);
	String getFileInfoForCmCp(String dealId,String fileName);

	ArrayList<ApplicantTypeVO> getCustRoleAtCMList();
	ArrayList<Object> searchCustomerLinkAtCM(LinkCustomerVo vo);
	boolean saveGuaranteeAmountAtCM(CreditProcessingCustomerEntryVo vo, String source);
	
	//Quality Check By Amit Starts
	ArrayList<CommonDealVo> fetchQualityCheckDetail(CommonDealVo vo);
	boolean saveQualityCheckDetails(String[] remarksArr, String[] dealIdArr, String[] decisionArr, String txnType, CommonDealVo vo);
	//Quality Check By Amit Ends
	//Check By prashant Start
	String checkCustomerType(CommonPageSecVo vo);
	//Check By prashant end
	
	//Code by Arun  for getting scoring list Starts here
	ArrayList<CommonDealVo> getScoringDtlList(CommonDealVo vo);
	//Code by Arun  for getting scoring list  ends here
	
	//Start Emi Calculator By Anil
	String insertDealForEmiLoan(String userId,String bgDate);
	boolean saveLoanForEmiCalc(Object ob);	
	boolean updateLoanForEmiCalc(Object ob);	
	String getDealIdForEmiCalc(String userId);
	boolean saveChargesForEmiCal(Object ob);
	String callProcForEmiCalc(String dealCap,String dealId);
	ArrayList getchargesDtlForEmiCalc(String dealCap,String dealId); 
	ArrayList getLoanDetailSchemeForEmiCal(String scheme, String bdate);
	ArrayList getLoanDetailListForEmiCalc(String dealId);
	ArrayList getAllLoanDetailsForEmiCalc(String dealLoanId);
	String callRefreshChargesDetailProForEmiCalc(String dealCap, String dealId);
	ArrayList refreshchargesDetailForEmiCalc(String dealCap,String dealId);
	ArrayList getTenureAmountInDealForEmiCalc(String dealId);
	ArrayList<Object> getSecurityDetailAllInDealForEmiCalc(String dealId);
	boolean saveSecurityDepositInDealForEmiCalc(SecurityDepositVO vo);
	boolean updatesecuritydepositForEmiCalc(ChargeVo vo, String dealId);
	ArrayList getInstallTypeForEmiCalc(String dealId);
	boolean saveInstallPlanForEmiCalc(InstallmentPlanForCMVO ipvo);
	String generateRepyScheduleForEmiCalc(String dealId,String makerId);
	ArrayList getFromLoanDtlForEmiCalc(String dealId);
	ArrayList getRepaySchedForEmiCalc(String dealId);
	public ArrayList getOtherPeriodicalChargeDetailForEmiCalc(String dealId,String stage);
	boolean saveOtherChargesPlanForEmiCalc(OtherChargesPlanVo ipvo);
	ArrayList fetchDueDayNextDateForEmiCalc(String repayEffectiveDate);
	ArrayList fetchDueDayForEmiCalc(String repayEffectiveDate, String cycleDate);
	//End Emi Calculator By Anil

	// KK
	String getDealIdInCm(String loanId);
	public ArrayList<Object> getTermSheet(String dealId);
	public ArrayList<Object> getMachineDetails(String dealId);
	public ArrayList<Object> getApprovalCommitteeList(String dealId);
	String saveTermSheetDetails(TermSheetVo termsVo);
	String checkApprovalLevel(String userId);
	ArrayList<Object> getPropertyType();
	
	//start by sachin
	public ArrayList<Object> getPropertyStatus();
	public ArrayList<Object> getPropertytTitle();
	public ArrayList<Object> getPropertyOwnerForDeal(String propOwner,String dealId);
	public ArrayList<Object> getPropertyOwnerForLoan(String propOwner,String dealId);
	public String getPropertyOwnerGcdId(String propOwner);
	public ArrayList<Object> showInsuranceRelWithNominee();
	
	//end by sachin

	// Doc upload by sanjog
	
	ArrayList getUploadDocForFVC(String dealId,String stage);
	boolean deleteDocumentUploadedForFVI(String string, String fieldVerificationUniqueId,String stage);
	String getFVIFileInfo(String fieldVerificationUniqueId, String fileName,String stage);

	String getAppraiserTypeAtCM(String loanId, String verificationId);
	ArrayList<Object> fetchCollateralDetailsAllAtCM(String entityId,String string);

	
	// Amit Query Search
	ArrayList<CommonDealVo> querySearchDetail(CommonDealVo vo);
	ArrayList getQueryInitiationData(String dealId, String userId);
	// Amit Query Search Ends
	
	// Anil
	ArrayList getLoginUserLevel(String userId, String userName);
	ArrayList getRelationshipManager(String relationManager,String relationOfficer);
	
	//change for loan detail
	public ArrayList<Object> getInstallmentTypeList();
	
	ArrayList <DisbursalSearchVO> searchDealMovement(DisbursalSearchVO vo);
	ArrayList<Object> getOtherRelationList();
	boolean saveOtherRelation(BuyerSupplierOtherVo vo);
	ArrayList getOtherDetail(String otherUniqueId);
	ArrayList getOtherGridList(String dealId);
	boolean updateOtherRelation(BuyerSupplierOtherVo vo);
	boolean deleteOtherDetail(String[] otherUniqueId);
	boolean updateConfirmDeal(String dealId);
	ArrayList<Object> getCreditcommitteeList();	
	//start by sachin for cibil information in view mode
	String getDealId(String loanId);
	//end by sachin
	String checkAssetVerifInitAtCM(String[] id, String loanId);

	ArrayList<Object> getMortageList();
	//start by manish shukla
	String getDealIdforTerm(String loanId);
	//end by manish shukla
	String callProcUnderWriterUsersQueue(String dealId,String userId,String bDate,String branchId);
	int getLimitOfDocumentUpload();
	public boolean deleteOtherChargesPlanInDeal(String id,String markedRow,String stage);
	ArrayList<UnderwriterApprovalVo> getApprovalRecommend(String dealId,UnderwriterApprovalVo vo);
	 String getApprovalLevel(UnderwriterApprovalVo docVo);

	 ArrayList<Object> getFinancialInstList();
	 ArrayList fetchFinancialInstName(String financialInst);
	 ArrayList fetchFleetDataList(String lbxLoanNoHid,String bDate);

	
	 ArrayList getresultForDeal(String dealId);

	ArrayList<Object> getExposure(String dealId, String userId);
	ArrayList getFleetDetailsList(String loanId);
	String checkChesisNoVehicle(CollateralVo sh,String source);
	String checkEnginNoVehicle(CollateralVo sh,String source);
	String checkRegNoVehicle(CollateralVo sh,String source);
	String getDownLoadFileInfoOmniFin(String txnId, String txnType,	String fileName);
	//amandeep starts
	ArrayList<Object> getPaymentModes();
	boolean saveSectorTypeDetails(Object ob,String dealId);
	public ArrayList editSectorTypeDetails(Object ob) ;
	ArrayList<Object> getAgriDocsList();
	//ArrayList getbusinessList();
	String checkRefinaceReqInfo();
	String getVatValueStateWise(String supplierID); // add by saorabh
	boolean vatDetail(CollateralVo vo);// add by saorabh
	String getServiceTaxStateWise(String supplierID); // add by saorabh
	String getProductLoanType(String productID);// add by saorabh
	
		String getInvoiceAssetId();
	public String saveAssetInvoiceDetails(Object ob);
	public String saveAssetDispatchDetails(Object ob);
	public ArrayList<CollateralVo> getAssetDispatchDetailList(String assetId,String source);
	public ArrayList<CollateralVo> getAssetInvoiceDetailList(String assetId,String source);
	public ArrayList<CollateralVo> getInvoiceDetail(String recordId,String source);
	public ArrayList<CollateralVo> getDispatchDetail(String recordId,String source);
	public boolean deleteDispatchDetail(String recordId,String source);
	public boolean deleteInvoiceDetail(String recordId,String source);
	public ArrayList getInvoiceProductList(String assetId,String source);
	public ArrayList getSiRdName();
	String checkDuplicateGoldOrnament(String dealId,String ornamentType,String ornamentStandard);
	String checkDuplicateGoldOrnamentForLoan(String loanId,String ornamentType,String ornamentStandard);
	String checkDuplicateGoldOrnamentForUpdateLoan(String loanId,String ornamentType,String ornamentStandard,String assetId);
	String checkDuplicateGoldOrnamentUpdateDeal(String dealId,String ornamentType,String ornamentStandard,String assetId);
	public  String getSchemeForGoldList(String dealId);
	//public  String getloanAmountForGoldOrnament(String dealId);
	public String getSchemeForGoldForLoan(String loanId);
	//public String getloanAmountForGoldOrnamentLoan(String loanId);
	
	boolean saveUploadPath(String txnType,String customerId,String path,String fileName);
	ArrayList getUploadCustomerPicData(String customerId,String source);
	boolean deleteDocumentForCustomer(String docName,String customerId,String source);
	public String getDownLoadFilePathForCustomerDoc(String customerId, String source);
	ArrayList<Object> getDocTypeList();

	
	public ArrayList getObligationDetails(ObligationVo vo);
	public ArrayList  updateObligation(ObligationVo vo);
	ArrayList getObligationDetails(String string, String dealId,
			String recStatus);
	
	
		
	// Rohit Changes for OCR.....
	ArrayList saveDetails(CommonDealVo vo);
	ArrayList DocumentType(String dealId,String type,String custRef);
	ArrayList makerSearch(UnderwritingDocUploadVo uwDocVo);
	
	
	//Abhishek change for OCR
	ArrayList AuthorSearch(UnderwritingDocUploadVo uwDocVo , String userId);

	public boolean approvedDoc(UnderwritingDocUploadVo uwDocVo);
	ArrayList ViewerSearch(String dealId,String customerType, String userId, String FunctionId, String BranchId);
	ArrayList viewerSearch(UnderwritingDocUploadVo uwDocVo);
	/*ArrayList DocumentType(String dealId, String customerType);*/
	ArrayList Viewer(String dealId, String customerType,String CustRef);
	/*ArrayList deleteDocumentUploadEntrymaker(String dealId);*/
	
	/*change Abhishek For Customer Demographics (Search Button)*/
	ArrayList CustDemoSearch(UnderwritingDocUploadVo uwDocVo);
	ArrayList<UploadTypeVo> getDocType();
	ArrayList<UploadTypeVo> getFormatType();

	ArrayList<com.cp.vo.UnderwritingDocUploadVo> getViewAllDocumentList(UnderwritingDocUploadVo vo);
	ArrayList<com.cp.vo.UnderwritingDocUploadVo> getUploadDocList(UnderwritingDocUploadVo uwDocVo);
	ArrayList DocumentType1(String dealId, String customerType, String custRef,String functionId);
	ArrayList DocumentViewer(String dealId, String customerType, String custRef,String functionId);
	
	//ArrayList getCreditApprovalDetails(String dealId, String customerType, String custRef);
	//ArrayList<CommonDealVo> getCreditApprovalDetails(CommonDealVo vo);
	
	/*Abhishek Change for Content Verification*/
	
	ArrayList<UnderwritingDocUploadVo> ContentVerificationForGrid(String dealId, String customerType, String custRef,String functionId);
	
	ArrayList<CodeDescVo> getCustomerList(String caseId,String entityType);

	
	

	ArrayList<CommonDealVo> getFinancialyear();
	boolean checkApplicationRefNo(CommonDealVo vo);
	
	boolean checkApplicationFormNo(CommonDealVo vo);
	boolean forwardDoc(UnderwritingDocUploadVo uwDocVo , String dealId);
	String getQueAnsResultMatch(String caseId, String string);
	boolean checkUploadFileData(String caseId);
	boolean checkAuthorContentVerificationStatus(String caseId, String stageId);
	ArrayList<CodeDescVo> getBankStmntDateFormat();
	ArrayList<CodeDescVo> getPropertyList();
	
	/*Abhishek Start For account Type add on Doc upload maker screen from generic master :- 26-Nov-2015*/
	ArrayList<CodeDescVo> getAccountType();
	String getAccountTypeDesc(String accountType);
	ArrayList<CommonDealVo> getcamPreparationMode();
	
	/*Abhishek End For account Type add on Doc upload maker screen from generic master :- 26-Nov-2015*/
	//HINA change here
	public boolean getStatusDetail(String caseId);
	boolean checkDocStatus(String caseId);
	
}


