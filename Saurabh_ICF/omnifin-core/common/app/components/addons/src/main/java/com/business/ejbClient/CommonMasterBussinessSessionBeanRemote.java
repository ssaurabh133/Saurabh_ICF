package com.business.ejbClient;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.masters.vo.BenchmarkRatioVo;
import com.masters.vo.BranchMasterAreaCodeVo;
import com.masters.vo.BranchMasterVo;
import com.masters.vo.BusinessClosureVo;
import com.masters.vo.DealerMasterVo;
import com.masters.vo.HolidayMasterVo;
import com.masters.vo.UserMasterVo;
import com.masters.vo.SalesExecutiveMasterVo;
import com.masters.vo.IrrCalculationMasterVo;

@Remote
public interface CommonMasterBussinessSessionBeanRemote {
	
	
	
	 String REMOTE_IDENTITY="COMMONBUSSINESSMASTERREMOTE";
	 
	 // Contry Master by Nishant Rai
	 public ArrayList searchCountryData(Object ob) ;
	 public boolean updateCountryData(Object ob);
	 public boolean insertCountryMaster(Object ob);
	 
	 // State Master by Nishant Rai
	 public ArrayList searchStateData(Object ob);
	 public boolean updateStateData(Object ob);
	 public ArrayList defaultCountry1();
	 public boolean insertStateMaster(Object ob);
	 
	  public abstract String saveIrrCalMaster(IrrCalculationMasterVo paramIrrCalculationMasterVo);

	  public abstract ArrayList<IrrCalculationMasterVo> searchIrrCalData(Object paramObject);

	  public abstract ArrayList searchIrrCalculationData(IrrCalculationMasterVo paramIrrCalculationMasterVo);

	  public abstract ArrayList<IrrCalculationMasterVo> irrModifyChargeCodeDetailsDao(Object paramObject);

	  public abstract ArrayList<IrrCalculationMasterVo> getIrrChargeDetail(Object paramObject);

	  public abstract String updateIrrCalMaster(IrrCalculationMasterVo paramIrrCalculationMasterVo);

	 
	 // Distric Master by Nishant Rai
	 public ArrayList searchDistrictData(Object ob);
	 public boolean updateDistrictData(Object ob);
	 public boolean insertDistrictMaster(Object ob);
	 
	 // Region Master by Nishant Rai
	 public ArrayList searchRegionData(Object ob);
	 public boolean updateRegionData(Object ob);
	 public boolean insertRegionMaster(Object ob);
	 
	 // Branch Master by Nishant Rai
	 public ArrayList searchBranchData(Object ob);
	 public boolean updateBranchData(Object ob);
	 public ArrayList defaultCountry();
	 public String insertBranchMaster(Object ob);
	 
	// Department Master by Nishant Rai
	 public ArrayList searchDepartmentData(Object ob);
	 public boolean updateDepartmentData(Object ob);
	 public String insertDepartmentMaster(Object ob);
	 
	// Generic Master by Nishant Rai
	 public ArrayList searchGenericMasterDao(Object ob);
	 public ArrayList modifyGenericMasterDetailsDao(Object ob);
	 public boolean saveModifyGenericMasterDetailsDao(Object ob);
	 public String insertGenericMaster(Object ob);
	 
	 // Reason Master by Nishant Rai
	 public ArrayList searchReasonData(Object ob);
	 public boolean updateReasonData(Object ob);
	 public String insertReasonMaster(Object ob);
	 
	 //Agency Master by Nishant Rai
	 public ArrayList searchAgencyData(Object ob);
	 public ArrayList searchAgencyDataMapping(Object ob);
	 public ArrayList getAgency();
	 public boolean updateAgencyData(Object ob,String[] userMapping);
	 public String insertAgencyMaster(Object ob,String[] userMapping);
	 
	 //DSA Dealer Master by Nishant Rai
	 public ArrayList searchDealerData(Object ob);
	 public String updateDealerData(Object ob,String[] userName);
	 public String insertDealerMaster(Object ob,String userId);
	 ArrayList searchUserEdit(String userSearchId);
	 
	 //Sub Dealer Master By Surendra	 
	 public ArrayList searchSubDealerData(Object ob);
	 public String updateSubDealerData(Object ob);
	 public String insertSubDealerMaster(Object ob);
	 ArrayList searchSubDealerUserEdit(String userSearchId);
	 
	 //Industry Master by Nishant Rai
	 public ArrayList searchIndustryData(Object ob);
	 public boolean updateIndustryData(Object ob);
	 public String insertIndustryMaster(Object ob);
	 
	 //Sub Industry Master by Nishant Rai
	 public ArrayList searchSubIndustryData(Object ob);
	 public String insertSubIndustryMaster(Object ob);
	 public boolean updateSubIndustryData(Object ob);
	 
	 //Ratio Definition Master by Nishant Rai
	 public ArrayList ratioDefinitionSearch(Object ob);
	 public boolean checkExpression(Object ob);
	 boolean updateRatioDefinition(Object ob);
	 boolean saveRatioDefinition (Object ob);
	 public ArrayList getParamDetailDetails(String sourceType);
	 //Code by arun RatioDef Master 
	 public ArrayList getRatioList();
	 public String getRatioFarmula(String ratioCode);
	 //Code by arun RatioDef Master Ends here
	 //Financial Parameter Master by Nishant Rai
	 public ArrayList searchFinancialPramData(Object ob);
	 public	ArrayList searchFinPramData(Object ob);
	 public String insertFinancialMaster (Object ob);
	 public String updateFinPramData(Object ob);
	 
	//Holiday Master by Nishant Rai
	public boolean insertHolidayMaster(HolidayMasterVo vo);
	public ArrayList searchHolidayData(HolidayMasterVo ob);
	public boolean updateHolidyData(HolidayMasterVo ob) ;
	
	public String checkgroupName(String group);
	public ArrayList getAreaCode();
	public boolean insertBranchAreaCode(BranchMasterVo branchMasterVo,
			String[] areaCode);
	ArrayList<BranchMasterAreaCodeVo> searchAreaCodeBranchEdit(String userId) ;
	
	// Benchmark Ratio Master By Asesh Kumar
	public String saveBenchmarkRatioMaster(Object ob);
	public String updateBenchMarkRatioData(Object ob);
	public ArrayList<BenchmarkRatioVo> getBenchmarkRatioMasterList(Object ob);
	public ArrayList<BenchmarkRatioVo> searchBenchMarkRatioEdit(Object ob);

	// Asesh Kumar add here 
	public ArrayList<BusinessClosureVo> getBusinessMonthList(Object ob);
	public ArrayList searchBusinessMonthClosureEdit(Object ob);
	public String updateBusinessMonthClosureData(Object ob);
	public String saveBusinessMonthClosureDetails(Object ob);
	public String getStartDate(String businessMonthss,String businessYear);
	
	//Manish work space start
	
	 public ArrayList rateApprovalMakerSearch(Object ob) ;
	 public boolean saveRateApprovalMakerData(Object ob);
	 public ArrayList openEditRateApprovalMakerData(Object ob);
	 public boolean updateRateApprovalMakerData(Object ob);
	 // Rate Approval checker
	 public ArrayList rateApprovalCheckerSearch(Object ob) ;
	 public ArrayList openRateApprovalCheckerData(Object ob);
	 public boolean  saveRateApprovalChecker(Object ob);
	
	//Manish work space end

	
	public boolean finCheckExpression(Object ob);
	
	public String saveSalesExecutive(Object ob);

	public String updateSalesExecutive(Object ob);
	
	public ArrayList<SalesExecutiveMasterVo> salesExecutiveEdit(
			Object ob);
	public ArrayList<SalesExecutiveMasterVo> getSalesExecutiveList(Object ob);
	
	public ArrayList<SalesExecutiveMasterVo> getEmployeeTypeList();
	
	
	// Consortium Master by Parvez
			 public ArrayList searchConsortiumData(Object ob);
			 public boolean updateConsortiumData(Object ob);
			 public boolean insertConsortiumData(Object ob);
			 
}
