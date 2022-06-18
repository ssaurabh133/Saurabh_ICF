package com.cm.dao;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cm.vo.*;
import com.cp.vo.CollateralVo;
import com.cp.vo.RepayScheduleVo;

public interface LoanInitiationDAO {   
	String IDENTITY="LOANINITD";
	ArrayList<Object> fetchLoanDetail(CommonLoanVo vo,HttpServletRequest request);
	
	boolean checkLoanSanctionVaildTill(String loanId,String businessDate,int functionId);
	ArrayList getloanListInLoan(String loanId);
	ArrayList getLoanHeader(String loanId);
	String getOneDealOneLoanFlag(String laonId);
	boolean checkOldNewTenureAreEqual(String loanId,String reschId);

	ArrayList<LoanDetailForCMVO> getLoanListOfProduct(int dealno);
	ArrayList getloanListForNotePad(CommonLoanVo vo,HttpServletRequest request);
	
	ArrayList refreshchargesDetailInCM(String loanStage, String loanId);
	ArrayList getTenureAmount(String loanId);
	ArrayList<CompoundFrequencyVO> getCompFrequencyList();
	public ArrayList<Object> getSecurityDetailAll(String loanId);
	 ArrayList<InstallmentPlanForCMVO> getInstallType(String loanId);

	 ArrayList<RepayScheduleVo> getNewRepayScheduleRepricing(RepayScheduleVo vo, String loanId, String reschId);
	 ArrayList getNoOfDisb(String loanId);
	 ArrayList<Object> getCustomerInCMList(String loanId, String source);
	 ArrayList<LoanDetailForCMVO> getListOfValues(int dealNo,String businessDate);
	 boolean deleteSecurityDeposit(String[] txnId);
	 ArrayList updateListOfvalue(LoanDetailForCMVO loanDetailForCMVO);
	 boolean saveAssetCollateralFrmLoanInit(LoanDetailForCMVO loanDetailForCMVO);
	 public String checkStageInLoanInit(String loanId);
	 public boolean stageForwardInLoanInit(String loanId,String makerId,String businessDate);
	 ArrayList<CollateralVo> getcolletralDetail(CollateralVo collateralVo ,String loanId) ;
	 boolean addassetCollateralInCM(String[] id, String loanId,String makerId,String makerDate);
	  boolean saveInstallPlan(InstallmentPlanForCMVO ipvo);
	 
	  boolean saveNoOfDisb(NoOfDisbursalVo nvo);
	  boolean deleteNoOfDisb(String loanId,String disNo);
	  String checkDealSanctionVaildTill(String dealId, String businessDate);
	  
	  ArrayList getRevertDataInLoanInit(String loanId);
	  String insertLoanAuthor(LoanInitAuthorVo vo, String source);
	  
		boolean saveSecurityDeposit(SecurityDepositVO vo);
	  
		boolean insertAsset(String[] assetId, String dealOrLoanId, String userId,
				String bDate, String fromDealOrLoan);
		ArrayList getDate(String loanId);
		
		//ravi
		boolean insertUpdateSupManfCust(LoanDetailForCMVO loanDetailForCMVO);
		public ArrayList<Object> fetchLoanDetailForDeviation(CommonLoanVo vo);
       public boolean getCountForDoc(String loanId);

	boolean saveDisbursalScheduleFromDeal(String loanId);

	ArrayList getLoanDetailForDisb(String loanId);

	boolean updateDisbTypeAndDisbNo(String loanId, String count);

	boolean saveDisbursalScheduleDefault(String loanId);

	ArrayList<Object> fetchLoanDetailForDisbursalPlanMaker(CommonLoanVo vo);

	ArrayList<Object> fetchLoanDetailForDisbursalPlanAuthor(CommonLoanVo vo);

	ArrayList getNoOfDisbPlanMaker(String loanId);

	boolean approveNoOfDisb(NoOfDisbursalVo nvo);

	boolean saveDisbursalScheduleFromLoanInTemp(String loanId);

	boolean saveNoOfDisbPlanMenu(NoOfDisbursalVo nvo);

	ArrayList<Object> forwardedEditLoan(CommonLoanVo vo);

	ArrayList<Object> forwardEditLoan(CommonLoanVo vo);

	ArrayList copyData(String loanId, String userId, String businessDate);

	String validateFormNo(String loanDealId, String formNo);
	
	boolean saveNewInstallPlan(InstallmentPlanForCMVO ipvo);	
	ArrayList<InstallmentPlanForCMVO> getNewInstallType(String loanId);
	ArrayList<InstallmentPlanForCMVO> getNewInstallmentType(String loanId,String stage);
	String getdisbrsedAmt(String loanId);
	String getdisbrsalAmt(String loanId);

	ArrayList getPayeeName(String lbxLoanNoHID); 
	 ArrayList getresultForLoan(String loanId);
	//amandeep starts
		ArrayList<Object> getPaymentModes();


          boolean saveSectorTypeDetails(Object ob,String dealId,String loanId);
		public ArrayList editSectorTypeDetails(Object ob) ;
		ArrayList<Object> getAgriDocsList();
		String getDeal(String loanId);

		String savePartnerDetails(Object ob, String id,String businessType);

		ArrayList<LoanDetailForCMVO> getPartnerDetails(LoanDetailForCMVO loanDetailForCMVO, String loanId,String businessType);

		ArrayList<LoanDetailForCMVO> getPartnerBusDetails(LoanDetailForCMVO loanDetailForCMVO,String id, String loanId,String businessType);

		int deletePartnerDtl(String partnerDtl, String loanId,String businessType);

		ArrayList<LoanDetailForCMVO> getPartnerDetailsforPopUp(LoanDetailForCMVO loanDetailForCMVO, String loanId,String businessType);
		String  getvalidateBusinessPartnerRule(String loanId,String businessId,String businessType);//add by saorabh 6 sep 2014	
		Boolean  deleteBusinessPartnr(String loanId,String businessId,String businessType);
		boolean saveDefaultValue(String loanId,String userId,String businessDate,String businessType);
		boolean validationOnVatPercent(String loanId);
		int checkParticipationAmt(String loanId,String partnerType);
		
		ArrayList<Object> fetchLoanDetailForRepaymentService(CommonLoanVo vo,HttpServletRequest request);
		 ArrayList<InstallmentPlanForCMVO> getRspInstallType(String loanId,String customerRSPId);
		  String saveRSPInstallPlan(InstallmentPlanForCMVO ipvo);
//		  ArrayList  getLoanDetailForRepaymenTSchedule(String loanId,String cpID);
//		  
//		  ArrayList  GenerateRepaymenTScheduleForRSP(ArrayList vo);
//		  boolean saveRSPRepaymentSchdule(ArrayList vo);
		String generateRSPRepySchedule(String loanId,String partnerId,String makerId);
		 ArrayList getRSPRepaySched(String loanId, String partnerId);
		 int checkLeadPartner(String loanId);
		 String fetchRspId(String loanId);
		 
		String savePartnerDetailsForRsp(Object ob, String id,String businessType);

		ArrayList<LoanDetailForCMVO> getPartnerDetailsForRsp(LoanDetailForCMVO loanDetailForCMVO, String loanId,String businessType);

		ArrayList<LoanDetailForCMVO> getPartnerBusDetailsForRsp(LoanDetailForCMVO loanDetailForCMVO,String id, String loanId,String businessType);

		int deletePartnerDtlForRsp(String partnerDtl, String loanId,String businessType);

		ArrayList<LoanDetailForCMVO> getPartnerDetailsforPopUpForRsp(LoanDetailForCMVO loanDetailForCMVO, String loanId,String businessType);		 
		 
		String forwardPartnerDetailsForRsp(String loanId);
		
		String rspApproval(LoanDetailForCMVO loanDetailForCMVO);
		
		ArrayList<Object> fetchLoanDetailForRepaymentServiceForAuthor(CommonLoanVo vo,HttpServletRequest request);
		  
		ArrayList getloanListInLoanForEdit(String loanId);
		boolean saveEditLoan(CommonLoanVo vo); // add by sachin for edit loan
		
		String checkDealSanctionLimit(String dealId, String loanId, String productId, String loanAmout);

		ArrayList getInsuranceProviders();

		ArrayList getInsuranceProducts(String insuranceProvider);

		ArrayList getpolicyTypes();

		ArrayList getCustomerDetailList(CmInsuranceVo vo, String loanId);

		ArrayList getInsuranceData(String loanId);

		ArrayList getAllInsuranceData(CmInsuranceVo vo, String loanId);

		ArrayList getSaveInsuranceData(CmInsuranceVo vo,
				String insuranceProvider, String sumAssured, String tenure,
				String age, String insuranceProduct, String policyType,
				String premiumFinanced, String loanId);

		ArrayList getPropertyType(CmInsuranceVo vo, String loanId);

		ArrayList getRelation(CmInsuranceVo vo, String loanId);

		Map calculateCmInsurance(CmInsuranceVo vo, String insuranceProvider,
				String sumAssured, String tenure, String age,
				String insuranceProduct, String policyType,
				String premiumFinanced);

		int insuranceUpdateId(CmInsuranceVo vo, String loanId);

		String getinsuId(CmInsuranceVo vo, String loanId);

		int getDealSumInsuranceAmount(CmInsuranceVo vo, String loanId);

		int getsumAssureDealLoan1(CmInsuranceVo vo, String loanId);

		int getAssetCost(CmInsuranceVo vo, String loanId);

		boolean insertCmInsurance(CmInsuranceVo vo, String loanId);

		boolean updateDealLoan(CmInsuranceVo vo, String loanId);

		boolean deleteCmInsurance(CmInsuranceVo vo, String loanId);

		ArrayList getViewInsurance(CmInsuranceVo vo, String loanId);

		ArrayList getReloadInsurance(CmInsuranceVo vo, String loanId);

		String getCust(CmInsuranceVo vo, String loanId);
}
