package com.business.ejbClient;

import java.util.ArrayList;








import javax.ejb.Remote;

//import com.masters.vo.GoldOrnamentMasterVo;
//import com.masters.vo.ManufacturerSupplierMappingVO;
import com.masters.vo.ApprovalLevelDefVo;
import com.masters.vo.ChargeCodeMasterVo;
import com.masters.vo.ChargeMasterVo;
import com.masters.vo.DocChildMasterVo;
import com.masters.vo.DocumentChecklistMasterVo;
import com.masters.vo.MakeModelmasterVO;
import com.masters.vo.ManufacturerSupplierMappingVO;
import com.masters.vo.ManulaDeviationVO;
import com.masters.vo.ScoringBenchmarkMasterVo;
import com.masters.vo.ScoringMasterVO;
import com.masters.vo.UsedVehiclePricingVo;
import com.masters.vo.UsedVehiclePricingVo;
import com.masters.vo.UserApprovalMatrixVo;
import com.masters.vo.UserProductAccessVo;
import com.masters.vo.pcdMasterVo;
import com.masters.vo.ruleParamMasterVo;

@Remote
public interface CreditProcessingMasterBussinessSessionBeanRemote {
	
	
	
	String REMOTE_IDENTITY="CREDITPROCESSINGBUSSINESSMASTERREMOTE";
	
	ArrayList searchProductData(Object ob);
	boolean insertProductMaster (Object ob);
	boolean updateProductData(Object ob);
	ArrayList getProductCategory();
	
	//--------------------Gold Ornament----------------------
	
	ArrayList searchGoldOrnamentData(Object ob);
	String insertGoldOrnamentMaster (Object ob);
	boolean updateGoldOrnamentData(Object ob);
	ArrayList getGoldOrnamentType();
	ArrayList getGoldOrnamentStandard();
	ArrayList getGoldOrnamentCategory();
	
	
	//---------------------Scheme-----------------------------
	
	ArrayList  searchScemeCodeDao(Object ob);
	String insertSchemeCodeMaster(Object ob,String ratMet,String rwEve);	
	ArrayList  modifySchemeDetailsDao(Object ob);
	ArrayList  getBranchIdsDao(String schemeId);
	boolean saveModifySchemeDetailsDao(Object ob,String ratMet,String rwEve);
	ArrayList getRepaymentModeList();
	
	//---------------------Base Rate-----------------------------
	
	boolean insertBaseRateMaster(Object ob);
	ArrayList getBaseRateData();
	ArrayList searchBaseRateData(Object Ob);
	boolean updateBaseRateData(Object ob);
	ArrayList getbaseRateTypeList();
	
	//---------------------Bank-------------------------------
	
	String insertBankMaster (Object ob);
	ArrayList searchBankData(Object ob);
	boolean updateBankData(Object ob);

	//---------------------Bank Branch-------------------------------
	
	boolean insertBankBranchMaster(Object ob);
	ArrayList   modifyBankDetailsDao(Object ob);
	boolean saveModifyBankDetailsDao(Object ob);
	ArrayList   searchBankBranchDao(Object ob);
	
	//---------------------Bank Account-------------------------------
	
	boolean insertBankAccountMaster(Object ob);
	ArrayList searchBankAccountData(Object ob);
	boolean updateBankAccountData(Object ob);
	
	//---------------------Document Master-------------------------------
	
	boolean insertDocumentMaster(Object ob);
	ArrayList getDocumentData();
	ArrayList searchDocumentData(Object ob);
	boolean updateDocumentData(Object ob);
	
	//---------------------Document checkList Master-------------------------------	
	
	boolean insertDocCheckListMaster(String[] docIdList,String[] docMandatoryList, String[] docOriginalList, String[] docExpiryFlagList,String[] statusList, DocumentChecklistMasterVo vo);
	ArrayList <DocumentChecklistMasterVo>searchDocCheckListData(Object ob);
	ArrayList getStage();
	ArrayList getEntity(String parentValue);
	ArrayList getConstitution();
	boolean updatedocCheckListData(String[] docIdList,String[] docCheckIdList,String[] docMandatoryList, String[] docOriginalList, String[] docExpiryFlagList,String[] statusList, DocumentChecklistMasterVo vo,String[] docCheckAllIdVal);
	
	//---------------------Document child Master-----------by Nishant Rai----
	public ArrayList<DocChildMasterVo> searchDocChildData(Object ob);
	public boolean updateDocChildData(Object ob);
	public boolean insertDocChildMaster(Object ob);
	
	//---------------------Charge Code Master-----------by Nishant Rai----
	String insertchagreCodeMaster(Object ob);
	ArrayList <ChargeCodeMasterVo>  modifyChargeCodeDetailsDao(Object ob);
	boolean saveModifyChargeCodeDetailsDao(Object ob);
	ArrayList <ChargeCodeMasterVo>  searchChargeCodeDao(Object ob);
	
	//---------------------Charges Definition Master-----------by Nishant Rai----
	boolean insertChargeMaster(Object ob);
	ArrayList <ChargeMasterVo> getChargeData();
	boolean updateChargeData(Object ob,String chargeId);
	ArrayList <ChargeMasterVo> searchChargeData(Object Ob);
	
	//---------------------Rule Master-----------by Nishant Rai----
	public ArrayList ruleMasterSearch(Object ob);
	public ArrayList getRuleMasterParam(String sourceType);
	public String updateRuleDetail(Object ob);
	public String saveRuleDetail(Object ob);
	public ArrayList getRoleType();
	public ArrayList getSubRoleType();
	
	//---------------------Rule Parameter Master-----------by Nishant Rai----
	ArrayList<ruleParamMasterVo> searchRuleMaster(Object ob);
	boolean updateRuleMaster(Object ob);
	boolean insertRuleScoreMaster(Object ob);
	
	//---------------------User Product Access Master-----------by Nishant Rai----
	boolean saveUserProductAccess(UserProductAccessVo vo,String [] productId);
	public ArrayList getRecordAtSearch(int currentPageLink) ;
	ArrayList<UserProductAccessVo> getRecordForUpdate(String userId);
	ArrayList searchUserProductRecord(UserProductAccessVo vo);
	ArrayList<UserProductAccessVo> getProductRecordForUpdate(String userId);
	
	//---------------------User Approval Matrix Master-----------by Nishant Rai----
	public ArrayList<UserApprovalMatrixVo> getApprovedUser(int currentPageLink,String flag); 
	boolean saveUserApprovalMatrix(UserApprovalMatrixVo vo,String flag);
	ArrayList getDetail(String userId, String userRole,String flag);
	boolean updateUserApprovedRecords(UserApprovalMatrixVo vo, String flag);
	ArrayList<UserApprovalMatrixVo> getsearchApprovedUser(UserApprovalMatrixVo vo,String flag,String recStatus,String makerAuthorFlag);
	int checkUserId(String userId, String role);
	ArrayList<UserApprovalMatrixVo> getBranches(String userId);
	ArrayList<UserApprovalMatrixVo> getProducts(String userId);
	String makerAuthorFlag();
	boolean forwardUserApprovedRecords(UserApprovalMatrixVo fieldVo);
	ArrayList<UserApprovalMatrixVo> getAuthorSearchUser(int currentPageLink,String userId,String userRole);
	ArrayList getAuthorDetail(String userId, String userRole);
	String saveUserApprovalAuthor(Object ob, String userId, String role);
	//---------------------U/W Approval Level Definition Master-----------by Nishant Rai----
	public ArrayList searchApprovalLevelDef(ApprovalLevelDefVo Vo,String mcFlag);
	public boolean saveApprovalLevelDef(ApprovalLevelDefVo Vo,String mcFlag);
	public ArrayList<ApprovalLevelDefVo> editApprovalLevelDef(String productModify,String mcFlag);
	public String updateApprovalLevelDef(ApprovalLevelDefVo Vo, String productModify,String mcFlag);
	
	// saurabh changes 
	public String getApprovalId();
	public String forwardApprovalLevel(String approvalId);
	public ArrayList searchApprovalLevelDefAuthor(ApprovalLevelDefVo Vo);
	public String saveUWApproval(ApprovalLevelDefVo Vo,String productModify);
	
	//mradul changes 
	ArrayList getVehicleSegment();
	// mradul changes ends
	
	
	//---------------------Policy Check Definition Master-----------by Nishant Rai----
	ArrayList <pcdMasterVo> searchScreenPolicyListData(Object ob);
	boolean insertPolicyCheckListMaster(String[] ruleCodeList,String[] ruleDescList,String[] actionList,String[] appLevelList,pcdMasterVo vo);
	boolean updatePolicyCheckListMaster(String[] pcdCheckId,String[] ruleCodeList,String[] ruleDescList,String[] actionList,String[] appLevelList,pcdMasterVo vo);
	ArrayList<pcdMasterVo> searchPolicyListData(Object ob);
	ArrayList<pcdMasterVo> searchPolicyListDataForEditting(Object ob,String product,String scheme, String stageId);
	
	//---------------------Make Model Master-----------by Nishant Rai----
	boolean saveMakeModelRecord(MakeModelmasterVO vo,String state);
	boolean checkRecord(MakeModelmasterVO vo);
	ArrayList<MakeModelmasterVO> getMakeModelrecords(int currentPageLink);
	ArrayList<MakeModelmasterVO> searchMakeModelRecords(MakeModelmasterVO vo);
	ArrayList<MakeModelmasterVO> getParticularRecord(String makeModelId);
	String updateMakeModelRecord(MakeModelmasterVO vo,String[] stateId);
	boolean checkRecordforUpdate(MakeModelmasterVO vo);
	int getMakeModelID(MakeModelmasterVO vo) ;
	
	//---------------------manual deviation Master-----------by Abhimanyu ----
	boolean saveManualDeviation(ManulaDeviationVO vo);
	ArrayList<ManulaDeviationVO> getManualDeviationRecords(int linksize);
	ArrayList<ManulaDeviationVO> searchManualDeviationList(ManulaDeviationVO vo);
	ArrayList<ManulaDeviationVO> getSingleRecord(String manualid);
	//---------------------manual deviation Master-----------by Abhimanyu----
	
	
	//---------------------Manufacturer Supplier Mapping Master-----------by Anil ----
	boolean saveMfrSuppMappingRecord(ManufacturerSupplierMappingVO vo,String[] supplier);
	ArrayList<ManufacturerSupplierMappingVO> getManufacturerSupplierMappingRecords(ManufacturerSupplierMappingVO vo, int linksize);
	ArrayList<ManufacturerSupplierMappingVO> searchManufacturerSupplierMappingList(ManufacturerSupplierMappingVO vo);
	ArrayList <ManufacturerSupplierMappingVO> searchSupplierDescEdit(String mappingId);
	boolean updateMfrSuppMappingRecord(ManufacturerSupplierMappingVO vo,String[] supplier);
	//---------------------Manufacturer Supplier Mapping Master-----------by Anil----
	
	
	//---------------------Question Master-----------by Prashant ----
	    
	      public ArrayList searchVerificationQuestionData(Object ob) ;
	      public boolean insertVerificationQuestMaster(Object ob,String[] productId,String check);
	      public boolean updateVerificationQuestMaster(Object ob,String[] productId,String check);
	      ArrayList getVerificationQuestData(Object ob);
	//---------------------Question Master-----------by Prashant----
	      
	//-------------------Varifiation Question---------------------------by vinod----
	      
	     public ArrayList getGridInEditVerificationQuest(Object ob) ;
		int countVerificationCombination(Object vo);
		
	      
	    //-------------------Varifiation Question---------------------------by vinod----
		//----------------Scoring MAster---- ------------By arun
		public ArrayList<ScoringMasterVO> getScoringMasterList(ScoringMasterVO vo);
		public String saveScoringMaster(ScoringMasterVO vo);
		public String updateScoringMaster(ScoringMasterVO vo);
		public ArrayList<ScoringMasterVO> editScoringMasterHeader(ScoringMasterVO vo);
		public ArrayList<ScoringMasterVO> editScoringMasterdtl(ScoringMasterVO vo);
		public ArrayList<ScoringMasterVO> editScoringParamValueDtl(ScoringMasterVO vo);
		public String saveScoringMasterParamValue(ScoringMasterVO vo);
		//----------------Scoring MAster----------------By arun
		int getApprovalfromPmst();
		int getApprovalLevelfromPmst();
		//added by manish
		ArrayList<Object> getAddressList();
		ArrayList<Object> getProductList(String questionId);
		//start by sachin for BankBranchMaster
		boolean ifscBankBranchMaster(Object ob); 
		// SAURABH CHANGES 
		public String getMakerCheckerFlag();
		boolean saveScoringDetailsMaster(ScoringBenchmarkMasterVo vo);
		public ArrayList getsearchScoringDetails(ScoringBenchmarkMasterVo vo);
		public abstract boolean insertSblMaster(Object paramObject);
		public abstract ArrayList searchSblData(Object paramObject);
		public abstract boolean updateSBLData(Object paramObject);
		//ArrayList getUsedVehiclePricingData(Object ob);
		String getNoOfYearAtUsedVehicle();
		//boolean insertUsedVehiclePricing(UsedVehiclePricingVo usedVehiclePricingVo);
		//ArrayList selectUsedVehiclePricing(String makeModelId);
		//boolean updateVehiclePricingdata(Object ob);
		//ArrayList searchUsedVehiclePricing(Object ob);
		
		
	
			
       
	//---------------------------used vehicle pricing---------By Richa--------------
	    ArrayList   getUsedVehiclePricingData(Object ob);
		public boolean insertUsedVehiclePricing(UsedVehiclePricingVo usedVehiclePricingVo);
	//String getNoOfYearAtUsedVehicle();
	  public boolean updateVehiclePricingdata(Object ob);
       ArrayList<UsedVehiclePricingVo> selectUsedVehiclePricing(String makeModelId);
        ArrayList<UsedVehiclePricingVo> searchUsedVehiclePricing(Object ob);
        }
        
	
 //---------------------------used vehicle pricing---------By Richa--------------
	
		   //pooja code for SBL & GBL Master
		
	
		
	

