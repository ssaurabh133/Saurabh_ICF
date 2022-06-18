package com.masters.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.masters.vo.ApprovalLevelDefVo;
import com.masters.vo.AutoAllocationDefinitionVo;
import com.masters.vo.BankAccountMasterVo;
import com.masters.vo.BankBranchMasterVo;
import com.masters.vo.BankMasterVo;
import com.masters.vo.BaseRateMasterVo;
import com.masters.vo.BenchmarkRatioVo;
import com.masters.vo.BranchMasterAreaCodeVo;
import com.masters.vo.BranchMasterVo;
import com.masters.vo.BusinessClosureVo;
import com.masters.vo.ChangePasswordMasterVo;
import com.masters.vo.ChargeCodeMasterVo;
import com.masters.vo.ChargeMasterVo;
import com.masters.vo.CrSchemeMasterVo;
import com.masters.vo.DealerMasterVo;
import com.masters.vo.DepartmentMasterVo;
import com.masters.vo.DistrictMasterVo;
import com.masters.vo.DocChildMasterVo;
import com.masters.vo.DocumentChecklistMasterVo;
import com.masters.vo.DocumentMasterVo;
import com.masters.vo.FinancialPramMasterVo;
import com.masters.vo.GcdGroupMasterVo;
import com.masters.vo.GenericMasterVo;
import com.masters.vo.IndustryMasterVo;
import com.masters.vo.IrrCalculationMasterVo;
import com.masters.vo.MasterVo;
import com.masters.vo.MobileUserMappingVo;
import com.masters.vo.NPAMasterVo;
import com.masters.vo.ProductMasterVo;
import com.masters.vo.ReasonMasterVo;
import com.masters.vo.RegionMasterVo;
import com.masters.vo.RoleAccessMasterVo;
import com.masters.vo.RoleMasterVo;
import com.masters.vo.SalesExecutiveMasterVo;
import com.masters.vo.ScoringBenchmarkMasterVo;
import com.masters.vo.StateMasterVo;
import com.masters.vo.SubIndustryMasterVo;
import com.masters.vo.UnlockUserVo;
import com.masters.vo.UsedVehiclePricingVo;
import com.masters.vo.UserAccessMasterVo;
import com.masters.vo.UserBranchMasterVo;
import com.masters.vo.UserMasterVo;



public interface MasterDAO {

	String IDENTITY="MD"; 
	boolean insertBankBranchMaster(Object ob);
	ArrayList <BankBranchMasterVo>  modifyBankDetailsDao(Object ob);
	boolean saveModifyBankDetailsDao(Object ob);
	ArrayList <BankBranchMasterVo>  searchBankBranchDao(Object ob);
	
	// generic master by apoorva
	ArrayList modifyGenericMasterDetailsDao(Object ob);
	boolean saveModifyGenericMasterDetailsDao(Object ob);
	String insertGenericMaster(Object ob);
	ArrayList searchGenericMasterDao(Object ob);
	ArrayList getAreaCode();


	
   // Code Start By Apoorva for GCD group Master
	
	String insertGroupCodeMaster(GcdGroupMasterVo gcdGroupMasterVo);
	ArrayList <GcdGroupMasterVo>  modifyGcdGroupDetailsDao(Object ob);
	public String saveModifyGcdGroupDao(Object ob);
	ArrayList <GcdGroupMasterVo> searchGcdGroupData(Object ob);

	String insertBankMaster (Object ob);
	ArrayList <BankMasterVo> searchBankData(Object ob);
	boolean updateBankData(Object ob);
	
	String insertchagreCodeMaster(Object ob);
	ArrayList <ChargeCodeMasterVo>  modifyChargeCodeDetailsDao(Object ob);
	boolean saveModifyChargeCodeDetailsDao(Object ob);
	ArrayList <ChargeCodeMasterVo>  searchChargeCodeDao(Object ob);
	

	
	//Code For Dealer Master By Ankit	
	String insertDealerMaster (Object ob,String userId);
	ArrayList searchDealerData(Object ob);
	String updateDealerData(Object ob,String[] userName);
	ArrayList searchUserEdit(String userSearchId);
	
	//Code For Department Master By Ankit	
	String insertDepartmentMaster (Object ob);
	ArrayList searchDepartmentData(Object ob);
	boolean updateDepartmentData(Object ob);
	
	
	//Code For Reason Master By Ankit
	String insertReasonMaster (Object ob);
	ArrayList searchReasonData(Object ob);
	boolean updateReasonData(Object ob);

	
//Code For Country Master By Ankit
	
	boolean insertCountryMaster (Object ob);
	ArrayList  searchCountryData(Object ob);
	boolean updateCountryData(Object ob);
	
//Code For Region Master By Ankit
	
	boolean insertRegionMaster (Object ob);
	ArrayList searchRegionData(Object ob);
	boolean updateRegionData(Object ob);
	
//Code For District Master By Ankit
	
	boolean insertDistrictMaster (Object ob);
	ArrayList searchDistrictData(Object ob);
	boolean updateDistrictData(Object ob);
	
	
//Code For State Master By Ankit
	
	boolean insertStateMaster (Object ob);
	ArrayList  searchStateData(Object ob);
	boolean updateStateData(Object ob);
	
//Code Start By Ritu For Agency Master

	String insertAgencyMaster(Object ob,String[] userMapping);
	ArrayList searchAgencyData(Object ob);
	ArrayList searchAgencyDataMapping(Object ob);
	boolean updateAgencyData(Object ob,String[] userMapping);
	ArrayList getAgency();

//Code Start By Ritu For Branch Master
	String insertBranchMaster(Object ob);
	ArrayList searchBranchData(Object ob);
	boolean updateBranchData(Object ob);
	//Ritu
	ArrayList defaultCountry();
	
	//Code Start By Ritu For Industry Master
	String insertIndustryMaster(Object ob);
	ArrayList searchIndustryData(Object ob);
	boolean updateIndustryData(Object ob);



	//Code Start By Ritu For SubIndustry Master
	String insertSubIndustryMaster(Object ob);
	ArrayList searchSubIndustryData(Object ob);
	boolean updateSubIndustryData(Object ob);
	
	//Code Start By Ritu For Document Master
	boolean insertDocumentMaster(Object ob);
	ArrayList <DocumentMasterVo> getDocumentData();
	ArrayList <DocumentMasterVo> searchDocumentData(Object ob);
	boolean updateDocumentData(Object ob);
	
	//Code Start By Ritu For Base Rate Master
	boolean insertBaseRateMaster(Object ob);
	ArrayList <BaseRateMasterVo> getBaseRateData();
	ArrayList <BaseRateMasterVo> searchBaseRateData(Object Ob);
	boolean updateBaseRateData(Object ob);
	
	 //Code Start By Ritu For Charge Master
	boolean insertChargeMaster(Object ob);
	ArrayList <ChargeMasterVo> getChargeData();
	boolean updateChargeData(Object ob,String chargeId);
	ArrayList <ChargeMasterVo> searchChargeData(Object Ob);
	

	//Code Start By Ritu For Role Master
	boolean insertRoleMaster(Object ob);
	boolean updateRoleData(Object ob);
	ArrayList <RoleMasterVo> searchRoleData(Object Ob);
	

	// Code Start By Ritu For Role Access Master
	boolean insertRoleAccessMaster(Object ob,String[] checkbox);
	ArrayList <RoleAccessMasterVo> searchRoleAccessData(Object Ob);

	
	// Code By Ritu For User Master
	String insertUserMaster(Object ob,String[] branchName,String[] levelName);//changed by Nishant Rai 
	boolean checkEmpIDInUserMaster(Object ob);
	boolean checkEmpIDInEditUserMaster(Object ob);
	
	boolean checkReportingToUserStatus(Object ob);
	
	boolean updateUserData(Object ob);
	ArrayList <UserMasterVo> searchUserData(Object Ob);
	String updateUserPassword(Object ob);
	boolean insertUserBranch(Object ob,String[] branchName);
	ArrayList <UserMasterVo> searchUserBranchEdit(String userSearchId);
	boolean updateUserData1(Object ob,String[] branchName);

	
	//Code Start By Ritu For User Branch Master
	boolean insertUserBranchMaster(Object ob,String[] branchName);
	boolean updateUserBranchData(Object ob,String[] branchName);
	ArrayList <UserBranchMasterVo> searchUserBranchData(Object Ob);
	ArrayList <UserBranchMasterVo> searchUserBranch();
	ArrayList <UserBranchMasterVo> searchUserBranchDesc(String userId);
	ArrayList <UserBranchMasterVo> searchUserBranchDescEdit(String userId);
	
	//Code Start By Ritu For User Access Master
	boolean insertUserAccessMaster(Object ob);
	boolean updateUserAccessData(Object ob);
	ArrayList <UserAccessMasterVo> searchUserAccessData(Object Ob);
	
	//Code Start By Ritu For Change Password Master
     boolean updateChangePassword(Object ob);
     int countChangePassword(Object ob);
	
   //Code Start By Ritu For NPA Master
		String insertNPAStageMaster(Object ob);
		ArrayList <NPAMasterVo> getNPAStageData();
		ArrayList <NPAMasterVo> searchNPAStageData(Object ob);
		String updateNPAStageData(Object ob);
		public ArrayList<NPAMasterVo> getProductId();
	public String getProduct(String str);
	boolean insertProductMaster (Object ob);
	ArrayList <ProductMasterVo> searchProductData(Object ob);
	boolean updateProductData(Object ob);
	ArrayList getProductCategory();
	


//Code For Doc Child Master By Ankit
	
	boolean insertDocChildMaster (Object ob);
	ArrayList <DocChildMasterVo> searchDocChildData(Object ob);
	boolean updateDocChildData(Object ob);
	
	
	//code for cr Scheme Master
	ArrayList <CrSchemeMasterVo>  searchScemeCodeDao(Object ob);
	String insertSchemeCodeMaster(Object ob,String ratMet,String rwEve);	
	ArrayList <CrSchemeMasterVo>  modifySchemeDetailsDao(Object ob);
	boolean saveModifySchemeDetailsDao(Object ob,String ratMet,String rwEve);
	ArrayList <CrSchemeMasterVo> accountDetailMapping(Object ob);
	boolean saveAccountDtl(String[] accountFlagList,String[] checkbox,Object ob);
	boolean modifyAccountDtl(String[] accountFlagList,String[] checkbox,Object ob);
	ArrayList <CrSchemeMasterVo> stageDetailMapping(Object ob);
	ArrayList  getRepaymentModeList();
	ArrayList <CrSchemeMasterVo>  getBranchIdsDao(String schemeId);


	
	//Code For Document Checklist Master By Ankit
	boolean insertDocCheckListMaster(String[] docIdList,String[] docMandatoryList, String[] docOriginalList, String[] docExpiryFlagList,String[] statusList, DocumentChecklistMasterVo vo);
	ArrayList <DocumentChecklistMasterVo>searchDocCheckListData(Object ob);
	ArrayList getStage();
	ArrayList getEntity(String parentValue);
	ArrayList getConstitution();
	boolean updatedocCheckListData(String[] docIdList,String[] docCheckIdList,String[] docMandatoryList, String[] docOriginalList, String[] docExpiryFlagList,String[] statusList, DocumentChecklistMasterVo vo,String[] docCheckAllIdVal);
	//kanika code 
	String getlogintimeid(String userName);
	String getbeforemakerdate();
	
	// Code for Bank Account Master
	boolean insertBankAccountMaster(Object ob);
	ArrayList <BankAccountMasterVo> searchBankAccountData(Object ob);
	boolean updateBankAccountData(Object ob);
	
	boolean updateloginChangePassword(ChangePasswordMasterVo changePasswordMasterVo);
	int countQuestion(Object ob);
	ArrayList showquestion(ChangePasswordMasterVo changePasswordMasterVo);
	
	//ravi, ratio definition master
	ArrayList  ratioDefinitionSearch(Object ob);
	boolean saveRatioDefinition (Object ob);
	boolean updateRatioDefinition(Object ob);
	boolean checkExpression(Object ob);	
	public ArrayList getParamDetailDetails(String sourceType);
	//code by arun for ratio def
	public ArrayList getRatioList();
	public String getRatioFarmula(String ratioCode);
	//code by arun for ratio def
	//Ankit, Financial Prameter

	ArrayList searchFinancialPramData(Object ob);
	ArrayList searchFinPramData(Object ob);
	String insertFinancialMaster (Object ob);
	String updateFinPramData(Object ob);
	

	

	boolean saveApprovalLevelDef(ApprovalLevelDefVo Vo,String mcFlag);
	ArrayList searchApprovalLevelDef(ApprovalLevelDefVo Vo,String mcFlag);
	ArrayList<ApprovalLevelDefVo> editApprovalLevelDef(String productModify,String mcFlag);
	String updateApprovalLevelDef(ApprovalLevelDefVo Vo, String productModify,String mcFlag);
	
	// saurabh changes 
	String getApprovalId();
	public String forwardApprovalLevel(String approvalId);
	public ArrayList searchApprovalLevelDefAuthor(ApprovalLevelDefVo Vo);
	public String saveUWApproval(ApprovalLevelDefVo Vo,String productModify);
	
	ArrayList getVehicleSegment();	

	//Ravi, Rule master
	
	ArrayList <MasterVo>  ruleMasterSearch(Object ob);
	public ArrayList getRoleType();
	public ArrayList getSubRoleType();
	public String saveRuleDetail (Object ob);
	public String updateRuleDetail(Object ob);
	int counthieirarchyusers(UserMasterVo userMasterVo);
	boolean getRecordStatus(ApprovalLevelDefVo vo);
	
	ArrayList searchUnlockuser(UnlockUserVo vo);
	boolean updateUnlockuser(UnlockUserVo vo, String[] users);
	
	public ArrayList getRuleMasterParam(String sourceType);
	boolean insertUserLevel(Object ob, String[] levelName);
	boolean updateUserLevel(Object ob, String[] levelName);
	ArrayList<UserMasterVo> searchUserLevelEdit(String userSearchId);
	
	
	String checkgroupName(String groupName);
	boolean insertBranchAreaCode(BranchMasterVo branchMasterVo,
			String[] areaCode);
	ArrayList<BranchMasterAreaCodeVo> searchAreaCodeBranchEdit(String userId) ;
	
	
	ArrayList searchVerificationQuestionData(Object ob);
	boolean insertVerificationQuestMaster(Object ob);
	boolean insertQuestionProduct(Object ob, String[] productId,String check);
	boolean updateVerificationQuestMaster(Object ob);
	ArrayList getVerificationQuestData(Object ob);
	
	//vinod
	
	ArrayList getGridInEditVerificationQuest(Object ob);
	int countVerificationCombination(Object vo);
	
	

	//ravi
	String saveAutoAllocationDefDetail(Object ob);
	ArrayList <AutoAllocationDefinitionVo> getAutoAllocationDefData(Object ob);
	ArrayList <AutoAllocationDefinitionVo> searchAutoAllocationDefData(Object ob);
	String updateAutoAllocationDefDetail(Object ob);

	//Surendra Added Here
	String insertSubDealerMaster (Object ob);
	ArrayList searchSubDealerData(Object ob);
	String updateSubDealerData(Object ob);
	ArrayList searchSubDealerUserEdit(String userSearchId);	
	ArrayList getbaseRateTypeList();
	int getApprovalfromPmst();
	// Asesh Kumar add here 
	public ArrayList<BenchmarkRatioVo> getBenchmarkRatioMasterList(Object ob);
	public String saveBenchmarkRatioMaster(Object ob);
	public String updateBenchMarkRatioData(Object ob);
	public ArrayList searchBenchMarkRatioEdit(Object ob);
	
	//added by manish
	ArrayList getAddressList();
	public boolean finCheckExpression(Object ob);

	// Asesh Kumar add here 
	public ArrayList<BusinessClosureVo> getBusinessMonthList(Object ob);
	public ArrayList searchBusinessMonthClosureEdit(Object ob);
	public String updateBusinessMonthClosureData(Object ob);
	public String saveBusinessMonthClosureDetails(Object ob);
	public String getStartDate(String businessMonthss,String businessYear);
	int getApprovalLevelfromPmst();
	
	//start by sachin for bank branch master
	boolean ifscBankBranchMaster(Object ob); 
	//saurabh changes starts
	public String getMakerCheckerFlag();
	boolean saveScoringBenchmarkMaster(ScoringBenchmarkMasterVo vo);
	ArrayList getScorelistDetails(ScoringBenchmarkMasterVo vo);
	
	//Manish Work space start
	
	ArrayList rateApprovalMakerSearch(Object ob);
	public boolean saveRateApprovalMakerData(Object ob);
	public ArrayList openEditRateApprovalMakerData(Object ob) ;
	public boolean updateRateApprovalMakerData(Object ob);
	ArrayList rateApprovalCheckerSearch(Object ob);
	ArrayList openRateApprovalCheckerData(Object ob);
	boolean saveRateApprovalChecker(Object ob);
	String saveSalesExecutive(Object ob);
	boolean deleteBranchData(Object ob);
	boolean deleteLevelData(Object ob);
   //Manish work space end

	ArrayList<SalesExecutiveMasterVo> getSalesExecutiveList(
			Object ob);
	public String updateSalesExecutive(Object ob);
	public ArrayList salesExecutiveEdit(Object ob);
	ArrayList getEmployeeTypeList();
	ArrayList getProductList(String questionId);
	//parvez starts for Consortium Partner Master
	
	ArrayList searchConsortiumData(Object ob);
	boolean insertConsortiumPartner (Object ob);
	boolean updateConsortiumData(Object ob);
	
	
	// Parvez start for gold Ornamnet
	
	ArrayList searchGoldOrnamentData(Object ob);
	String insertGoldOrnament (Object ob);
	boolean updateGoldOrnamentData(Object ob);
	ArrayList getGoldOrnamentType();
	ArrayList getGoldOrnamentStandard();
	ArrayList getGoldOrnamentCategory();
	  public abstract String insertMobileUserMaster(MobileUserMappingVo paramMobileUserMappingVo, String paramString);

	  public abstract String saveModifyMobileMasterDao(Object paramObject, String paramString);

	  public abstract ArrayList<MobileUserMappingVo> modifyMobileUSerDetailsDao(Object paramObject);

	  public abstract ArrayList<MobileUserMappingVo> searchMobileUserData(Object paramObject);

	  public abstract String getMobileNoMasterDao(Object paramObject);

	  public abstract String insertIrrMaster(IrrCalculationMasterVo paramIrrCalculationMasterVo);

	  public abstract ArrayList searchIrrCalData(Object paramObject);

	  public abstract ArrayList irrModifyChargeCodeDetailsDao(Object paramObject);

	  public abstract ArrayList getIrrChargeDetail(Object paramObject);

	  public abstract String updateIrrMaster(IrrCalculationMasterVo paramIrrCalculationMasterVo);
	
	//Richa work end for used vehicle pricing
	ArrayList <UsedVehiclePricingVo>  getUsedVehiclePricingData(Object ob);
	
	boolean insertUsedVehiclePricing(UsedVehiclePricingVo usedVehiclePricingVo);		
	boolean updateVehiclePricingdata(Object ob);
	String getNoOfYearAtUsedVehicle();
	ArrayList<UsedVehiclePricingVo> selectUsedVehiclePricing(String makeModelId);
	ArrayList<UsedVehiclePricingVo> searchUsedVehiclePricing(Object ob);
	


	//Richa work end for used vehicle pricing
			// pooja code for SBL & GBL Master
			 boolean insertSblMaster (Object ob);
			 ArrayList <ProductMasterVo> searchSblData(Object ob);
			 boolean updateSBLData(Object ob);
	
				public boolean deleteUser(String userId);
}

