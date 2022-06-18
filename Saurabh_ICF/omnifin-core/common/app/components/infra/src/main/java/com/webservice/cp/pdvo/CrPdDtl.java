package com.webservice.cp.pdvo;

import java.math.BigDecimal;
import java.util.List;


public class CrPdDtl {
	
	private Integer pdId;
	
	private Integer dealId;
	
	private String dealNo;
	
	private String dealLoanType;
	
	private String typeOfLoan;
	
	private BigDecimal loanAmount;
	
	private String personMet;
	
	private String useOfLoan;
	
	private String makerId;
	
	private String makerDate;
	
	private String branch;
	
	private String saveAsDraft;
	
	private String operationDate;
	
	private String longitude;
	
	private String latitude;
	
	private String locationAddress;
	
	
	private List<CrPdCustomerDtl> crPdCustomerDtlList;
	
    private List<CrPdProposedPropertyDtl> crPdProposedPropertyDtlList;
    
    private List<CrPdDealStructureDtl> crPdDealStructureDtlList;
    
    private List<CrPdResidenceFamilyDtl> crPdResidenceFamilyDtlList;
    
    private List<CrPdResidenceNeighbourDtl> crPdResidenceNeighbourDtlDtlList;
    
    private List<CrPdResidencePropertyDtl> crPdResidencePropertyDtlList;

	private CrPdResidenceAddressDtl crPdResidenceAddressDtl;

	private List<CrPdResidenceCitiesTownsDtl> crPdResidenceCitiesTownsDtlList;

	private CrPdResidenceHouseholdExpensesDtl crPdResidenceHouseholdExpensesDtl;

	private CrPdResidenceHouseFacilitiesDtl crPdResidenceHouseFacilitiesDtl;

	private CrPdResidenceStabilityDtl crPdResidenceStabilityDtl;

	private CrPdResidenceStandardLivingDtl crPdResidenceStandardLivingDtl;

	private List<CrPdResidenceVehicleFamilyDtl> crPdResidenceVehicleFamilyDtlList;
	private List<CrPdSaveInvestmentDtl> crPdSaveInvestmentDtlList;

	private CrPdResidenceVisitDtl crPdResidenceVisitDtl;
	
	private List<CrPdFamilyDtl> crPdFamilyDtlList;
	
	private List<CrPdFixedAssetDtl> crPdFixedAssetDtlList;
	
	private CrPdFixedLiquidAssetDtl crPdFixedLiquidAssetDtl;
	private List<CrPdPreviousMajorExpensesDtl> crPdPreviousMajorExpensesDtlList;
	private List<CrPdFutureMajorExpensesDtl> crPdFutureMajorExpensesDtlList;
	private List<CrPdCommitteeDtl> crPdCommitteeDtlList;
	private List<CrPdPrivateFinancerDtl> crPdPrivateFinancerDtlList;
	private List<CrPdReferenceDtl> crPdReferenceDtlList;
	private List<CrPdLifeStyleIndicatorValidationDtl> crPdLifeStyleIndicatorValidationDtlList;
	private List<CrPdBureauVerificationDtl> crPdBureauVerificationDtlList;
	private List<CrPdMonthlyIncomeDtl> crPdMonthlyIncomeDtlList;
	private List<CrPdMonthlyExpenseDtl> crPdMonthlyExpenseDtlList;
	private List<CrPdMonthlyIncomeAncillaryDtl> crPdMonthlyIncomeAncillaryDtlList;
	private List<CrPdMonthlyFixedExpenseDtl> crPdMonthlyFixedExpenseDtlList;
	private List<CrPdLOanDetailAndObligationDtl> crPdLOanDetailAndObligationDtlList;
	
	
	
	public String getDealNo() {
		return dealNo;
	}

	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}

	public String getDealLoanType() {
		return dealLoanType;
	}

	public void setDealLoanType(String dealLoanType) {
		this.dealLoanType = dealLoanType;
	}

	public List<CrPdSaveInvestmentDtl> getCrPdSaveInvestmentDtlList() {
		return crPdSaveInvestmentDtlList;
	}

	public void setCrPdSaveInvestmentDtlList(
			List<CrPdSaveInvestmentDtl> crPdSaveInvestmentDtlList) {
		this.crPdSaveInvestmentDtlList = crPdSaveInvestmentDtlList;
	}

	public List<CrPdCustomerDtl> getCrPdCustomerDtlList() {
		return crPdCustomerDtlList;
	}

	public void setCrPdCustomerDtlList(List<CrPdCustomerDtl> crPdCustomerDtlList) {
		this.crPdCustomerDtlList = crPdCustomerDtlList;
	}

	public List<CrPdProposedPropertyDtl> getCrPdProposedPropertyDtlList() {
		return crPdProposedPropertyDtlList;
	}

	public void setCrPdProposedPropertyDtlList(
			List<CrPdProposedPropertyDtl> crPdProposedPropertyDtlList) {
		this.crPdProposedPropertyDtlList = crPdProposedPropertyDtlList;
	}

	public Integer getPdId() {
		return pdId;
	}

	public void setPdId(Integer pdId) {
		this.pdId = pdId;
	}

	public Integer getDealId() {
		return dealId;
	}

	public void setDealId(Integer dealId) {
		this.dealId = dealId;
	}

	public String getTypeOfLoan() {
		return typeOfLoan;
	}

	public void setTypeOfLoan(String typeOfLoan) {
		this.typeOfLoan = typeOfLoan;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getPersonMet() {
		return personMet;
	}

	public void setPersonMet(String personMet) {
		this.personMet = personMet;
	}

	public String getUseOfLoan() {
		return useOfLoan;
	}

	public void setUseOfLoan(String useOfLoan) {
		this.useOfLoan = useOfLoan;
	}

	public String getMakerId() {
		return makerId;
	}

	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}

	public String getMakerDate() {
		return makerDate;
	}

	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLocationAddress() {
		return locationAddress;
	}

	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}

	public String getSaveAsDraft() {
		return saveAsDraft;
	}

	public void setSaveAsDraft(String saveAsDraft) {
		this.saveAsDraft = saveAsDraft;
	}

	public List<CrPdResidenceFamilyDtl> getCrPdResidenceFamilyDtlList() {
		return crPdResidenceFamilyDtlList;
	}

	public void setCrPdResidenceFamilyDtlList(
			List<CrPdResidenceFamilyDtl> crPdResidenceFamilyDtlList) {
		this.crPdResidenceFamilyDtlList = crPdResidenceFamilyDtlList;
	}

	public List<CrPdDealStructureDtl> getCrPdDealStructureDtlList() {
		return crPdDealStructureDtlList;
	}

	public void setCrPdDealStructureDtlList(
			List<CrPdDealStructureDtl> crPdDealStructureDtlList) {
		this.crPdDealStructureDtlList = crPdDealStructureDtlList;
	}

	public List<CrPdResidenceNeighbourDtl> getCrPdResidenceNeighbourDtlDtlList() {
		return crPdResidenceNeighbourDtlDtlList;
	}

	public void setCrPdResidenceNeighbourDtlDtlList(
			List<CrPdResidenceNeighbourDtl> crPdResidenceNeighbourDtlDtlList) {
		this.crPdResidenceNeighbourDtlDtlList = crPdResidenceNeighbourDtlDtlList;
	}

	public List<CrPdResidencePropertyDtl> getCrPdResidencePropertyDtlList() {
		return crPdResidencePropertyDtlList;
	}

	public void setCrPdResidencePropertyDtlList(
			List<CrPdResidencePropertyDtl> crPdResidencePropertyDtlList) {
		this.crPdResidencePropertyDtlList = crPdResidencePropertyDtlList;
	}

	public CrPdResidenceAddressDtl getCrPdResidenceAddressDtl() {
		return crPdResidenceAddressDtl;
	}

	public void setCrPdResidenceAddressDtl(
			CrPdResidenceAddressDtl crPdResidenceAddressDtl) {
		this.crPdResidenceAddressDtl = crPdResidenceAddressDtl;
	}

	public List<CrPdResidenceCitiesTownsDtl> getCrPdResidenceCitiesTownsDtlList() {
		return crPdResidenceCitiesTownsDtlList;
	}

	public void setCrPdResidenceCitiesTownsDtlList(
			List<CrPdResidenceCitiesTownsDtl> crPdResidenceCitiesTownsDtlList) {
		this.crPdResidenceCitiesTownsDtlList = crPdResidenceCitiesTownsDtlList;
	}

	public CrPdResidenceHouseholdExpensesDtl getCrPdResidenceHouseholdExpensesDtl() {
		return crPdResidenceHouseholdExpensesDtl;
	}

	public void setCrPdResidenceHouseholdExpensesDtl(
			CrPdResidenceHouseholdExpensesDtl crPdResidenceHouseholdExpensesDtl) {
		this.crPdResidenceHouseholdExpensesDtl = crPdResidenceHouseholdExpensesDtl;
	}

	public CrPdResidenceHouseFacilitiesDtl getCrPdResidenceHouseFacilitiesDtl() {
		return crPdResidenceHouseFacilitiesDtl;
	}

	public void setCrPdResidenceHouseFacilitiesDtl(
			CrPdResidenceHouseFacilitiesDtl crPdResidenceHouseFacilitiesDtl) {
		this.crPdResidenceHouseFacilitiesDtl = crPdResidenceHouseFacilitiesDtl;
	}

	public CrPdResidenceStabilityDtl getCrPdResidenceStabilityDtl() {
		return crPdResidenceStabilityDtl;
	}

	public void setCrPdResidenceStabilityDtl(
			CrPdResidenceStabilityDtl crPdResidenceStabilityDtl) {
		this.crPdResidenceStabilityDtl = crPdResidenceStabilityDtl;
	}

	public CrPdResidenceStandardLivingDtl getCrPdResidenceStandardLivingDtl() {
		return crPdResidenceStandardLivingDtl;
	}

	public void setCrPdResidenceStandardLivingDtl(
			CrPdResidenceStandardLivingDtl crPdResidenceStandardLivingDtl) {
		this.crPdResidenceStandardLivingDtl = crPdResidenceStandardLivingDtl;
	}

	public List<CrPdResidenceVehicleFamilyDtl> getCrPdResidenceVehicleFamilyDtlList() {
		return crPdResidenceVehicleFamilyDtlList;
	}

	public void setCrPdResidenceVehicleFamilyDtlList(
			List<CrPdResidenceVehicleFamilyDtl> crPdResidenceVehicleFamilyDtlList) {
		this.crPdResidenceVehicleFamilyDtlList = crPdResidenceVehicleFamilyDtlList;
	}

	public CrPdResidenceVisitDtl getCrPdResidenceVisitDtl() {
		return crPdResidenceVisitDtl;
	}

	public void setCrPdResidenceVisitDtl(CrPdResidenceVisitDtl crPdResidenceVisitDtl) {
		this.crPdResidenceVisitDtl = crPdResidenceVisitDtl;
	}

	public List<CrPdFamilyDtl> getCrPdFamilyDtlList() {
		return crPdFamilyDtlList;
	}

	public void setCrPdFamilyDtlList(List<CrPdFamilyDtl> crPdFamilyDtlList) {
		this.crPdFamilyDtlList = crPdFamilyDtlList;
	}

	public List<CrPdFixedAssetDtl> getCrPdFixedAssetDtlList() {
		return crPdFixedAssetDtlList;
	}

	public void setCrPdFixedAssetDtlList(
			List<CrPdFixedAssetDtl> crPdFixedAssetDtlList) {
		this.crPdFixedAssetDtlList = crPdFixedAssetDtlList;
	}

	public CrPdFixedLiquidAssetDtl getCrPdFixedLiquidAssetDtl() {
		return crPdFixedLiquidAssetDtl;
	}

	public void setCrPdFixedLiquidAssetDtl(
			CrPdFixedLiquidAssetDtl crPdFixedLiquidAssetDtl) {
		this.crPdFixedLiquidAssetDtl = crPdFixedLiquidAssetDtl;
	}

	

	public List<CrPdPreviousMajorExpensesDtl> getCrPdPreviousMajorExpensesDtlList() {
		return crPdPreviousMajorExpensesDtlList;
	}

	public void setCrPdPreviousMajorExpensesDtlList(
			List<CrPdPreviousMajorExpensesDtl> crPdPreviousMajorExpensesDtlList) {
		this.crPdPreviousMajorExpensesDtlList = crPdPreviousMajorExpensesDtlList;
	}

	public List<CrPdFutureMajorExpensesDtl> getCrPdFutureMajorExpensesDtlList() {
		return crPdFutureMajorExpensesDtlList;
	}

	public void setCrPdFutureMajorExpensesDtlList(
			List<CrPdFutureMajorExpensesDtl> crPdFutureMajorExpensesDtlList) {
		this.crPdFutureMajorExpensesDtlList = crPdFutureMajorExpensesDtlList;
	}


	public List<CrPdCommitteeDtl> getCrPdCommitteeDtlList() {
		return crPdCommitteeDtlList;
	}

	public void setCrPdCommitteeDtlList(List<CrPdCommitteeDtl> crPdCommitteeDtlList) {
		this.crPdCommitteeDtlList = crPdCommitteeDtlList;
	}

	

	public List<CrPdPrivateFinancerDtl> getCrPdPrivateFinancerDtlList() {
		return crPdPrivateFinancerDtlList;
	}

	public void setCrPdPrivateFinancerDtlList(
			List<CrPdPrivateFinancerDtl> crPdPrivateFinancerDtlList) {
		this.crPdPrivateFinancerDtlList = crPdPrivateFinancerDtlList;
	}

	public List<CrPdReferenceDtl> getCrPdReferenceDtlList() {
		return crPdReferenceDtlList;
	}

	public void setCrPdReferenceDtlList(List<CrPdReferenceDtl> crPdReferenceDtlList) {
		this.crPdReferenceDtlList = crPdReferenceDtlList;
	}

	public List<CrPdLifeStyleIndicatorValidationDtl> getCrPdLifeStyleIndicatorValidationDtlList() {
		return crPdLifeStyleIndicatorValidationDtlList;
	}

	public void setCrPdLifeStyleIndicatorValidationDtlList(
			List<CrPdLifeStyleIndicatorValidationDtl> crPdLifeStyleIndicatorValidationDtlList) {
		this.crPdLifeStyleIndicatorValidationDtlList = crPdLifeStyleIndicatorValidationDtlList;
	}

	public List<CrPdBureauVerificationDtl> getCrPdBureauVerificationDtlList() {
		return crPdBureauVerificationDtlList;
	}

	public void setCrPdBureauVerificationDtlList(
			List<CrPdBureauVerificationDtl> crPdBureauVerificationDtlList) {
		this.crPdBureauVerificationDtlList = crPdBureauVerificationDtlList;
	}

	public List<CrPdMonthlyIncomeDtl> getCrPdMonthlyIncomeDtlList() {
		return crPdMonthlyIncomeDtlList;
	}

	public void setCrPdMonthlyIncomeDtlList(
			List<CrPdMonthlyIncomeDtl> crPdMonthlyIncomeDtlList) {
		this.crPdMonthlyIncomeDtlList = crPdMonthlyIncomeDtlList;
	}

	public List<CrPdMonthlyExpenseDtl> getCrPdMonthlyExpenseDtlList() {
		return crPdMonthlyExpenseDtlList;
	}

	public void setCrPdMonthlyExpenseDtlList(
			List<CrPdMonthlyExpenseDtl> crPdMonthlyExpenseDtlList) {
		this.crPdMonthlyExpenseDtlList = crPdMonthlyExpenseDtlList;
	}

	public List<CrPdMonthlyIncomeAncillaryDtl> getCrPdMonthlyIncomeAncillaryDtlList() {
		return crPdMonthlyIncomeAncillaryDtlList;
	}

	public void setCrPdMonthlyIncomeAncillaryDtlList(
			List<CrPdMonthlyIncomeAncillaryDtl> crPdMonthlyIncomeAncillaryDtlList) {
		this.crPdMonthlyIncomeAncillaryDtlList = crPdMonthlyIncomeAncillaryDtlList;
	}

	public List<CrPdMonthlyFixedExpenseDtl> getCrPdMonthlyFixedExpenseDtlList() {
		return crPdMonthlyFixedExpenseDtlList;
	}

	public void setCrPdMonthlyFixedExpenseDtlList(
			List<CrPdMonthlyFixedExpenseDtl> crPdMonthlyFixedExpenseDtlList) {
		this.crPdMonthlyFixedExpenseDtlList = crPdMonthlyFixedExpenseDtlList;
	}

	public List<CrPdLOanDetailAndObligationDtl> getCrPdLOanDetailAndObligationDtlList() {
		return crPdLOanDetailAndObligationDtlList;
	}

	public void setCrPdLOanDetailAndObligationDtlList(
			List<CrPdLOanDetailAndObligationDtl> crPdLOanDetailAndObligationDtlList) {
		this.crPdLOanDetailAndObligationDtlList = crPdLOanDetailAndObligationDtlList;
	}

	@Override
	public String toString() {
		return "CrPdDtl [pdId=" + pdId + ", dealId=" + dealId + ", dealNo="
				+ dealNo + ", dealLoanType=" + dealLoanType + ", typeOfLoan="
				+ typeOfLoan + ", loanAmount=" + loanAmount + ", personMet="
				+ personMet + ", useOfLoan=" + useOfLoan + ", makerId="
				+ makerId + ", makerDate=" + makerDate + ", branch=" + branch
				+ ", saveAsDraft=" + saveAsDraft + ", operationDate="
				+ operationDate + ", longitude=" + longitude + ", latitude="
				+ latitude + ", locationAddress=" + locationAddress
				+ ", crPdCustomerDtlList=" + crPdCustomerDtlList
				+ ", crPdProposedPropertyDtlList="
				+ crPdProposedPropertyDtlList + ", crPdDealStructureDtlList="
				+ crPdDealStructureDtlList + ", crPdResidenceFamilyDtlList="
				+ crPdResidenceFamilyDtlList
				+ ", crPdResidenceNeighbourDtlDtlList="
				+ crPdResidenceNeighbourDtlDtlList
				+ ", crPdResidencePropertyDtlList="
				+ crPdResidencePropertyDtlList + ", crPdResidenceAddressDtl="
				+ crPdResidenceAddressDtl
				+ ", crPdResidenceCitiesTownsDtlList="
				+ crPdResidenceCitiesTownsDtlList
				+ ", crPdResidenceHouseholdExpensesDtl="
				+ crPdResidenceHouseholdExpensesDtl
				+ ", crPdResidenceHouseFacilitiesDtl="
				+ crPdResidenceHouseFacilitiesDtl
				+ ", crPdResidenceStabilityDtl=" + crPdResidenceStabilityDtl
				+ ", crPdResidenceStandardLivingDtl="
				+ crPdResidenceStandardLivingDtl
				+ ", crPdResidenceVehicleFamilyDtlList="
				+ crPdResidenceVehicleFamilyDtlList
				+ ", crPdSaveInvestmentDtlList=" + crPdSaveInvestmentDtlList
				+ ", crPdResidenceVisitDtl=" + crPdResidenceVisitDtl
				+ ", crPdFamilyDtlList=" + crPdFamilyDtlList
				+ ", crPdFixedAssetDtlList=" + crPdFixedAssetDtlList
				+ ", crPdFixedLiquidAssetDtl=" + crPdFixedLiquidAssetDtl
				+ ", crPdPreviousMajorExpensesDtlList="
				+ crPdPreviousMajorExpensesDtlList
				+ ", crPdFutureMajorExpensesDtlList="
				+ crPdFutureMajorExpensesDtlList + ", crPdCommitteeDtlList="
				+ crPdCommitteeDtlList + ", crPdPrivateFinancerDtlList="
				+ crPdPrivateFinancerDtlList + ", crPdReferenceDtlList="
				+ crPdReferenceDtlList
				+ ", crPdLifeStyleIndicatorValidationDtlList="
				+ crPdLifeStyleIndicatorValidationDtlList
				+ ", crPdBureauVerificationDtlList="
				+ crPdBureauVerificationDtlList + ", crPdMonthlyIncomeDtlList="
				+ crPdMonthlyIncomeDtlList + ", crPdMonthlyExpenseDtlList="
				+ crPdMonthlyExpenseDtlList
				+ ", crPdMonthlyIncomeAncillaryDtlList="
				+ crPdMonthlyIncomeAncillaryDtlList
				+ ", crPdMonthlyFixedExpenseDtlList="
				+ crPdMonthlyFixedExpenseDtlList
				+ ", crPdLOanDetailAndObligationDtlList="
				+ crPdLOanDetailAndObligationDtlList + "]";
	}
}
