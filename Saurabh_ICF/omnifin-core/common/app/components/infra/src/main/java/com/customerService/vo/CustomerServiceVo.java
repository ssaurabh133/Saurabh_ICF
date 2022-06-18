package com.customerService.vo;

public class CustomerServiceVo {
	
	
	private String loanNo;
	private String customerName;
	private String loanStatus;
	private String disbursalStatus;
	private String fileStatus;
	private String loanApprovalDate;
	private String loanRepaymentMode;
	private String loanFlatRate;
	private String loanEffRate;
	private String loanInstallmentType;
	private String loanInstallmentMode;
	private String sectorType;
	private String npaFlag;
	private String loanDpd;
	private String loanDpdString;
	private String loanTenure;
	private String loanMaturityDate;
	private String branchId;
	private String userId;
	private String businessDate;
	private String loanId;
	
	private String caseMarkingStatus;
	private String caseMarkingFlag;
	private String caseMarkingDate;
	private String caseMarkingAgencyName;
	private String caseMarkingOtherDetails;
	private String caseMarkingRemarks;
	private String caseUnMarkingDate;
	
	private String secPoolType;
	private String secPoolMarkingDate;
	private String secAgencyName;
	
	private String reschType;
	private String reschDate;
	private String reschMakerName;
	private String reschMakerRemarks;
	private String reschAuthoremarks;
	private String reschAuthorName;
	
	private String closureType;
	private String closureDate;
	private String closureMakerName;
	private String closureMakerRemarks;
	private String closureAuthoremarks;
	private String closureAuthorName;
	private String txnId;
	private String txnType;

	private String  firstEmi;
	private String  lastEmi;
	
	private String customerRole;	
	
	private String loanBalancePrincipal;
	private String loanOverdueAmount;
	private String overdueCharges;
	private String repoFlag;
	private String legalFlag;
	private String soaLink;
	private String assetNature;
	private String vehicleMake;
	private String vehicleModel;
	private String manufact;
	private String supplier;
	private String registrationNumber;
	private String chasisNumber;
	private String engineNumber;
	private String yearofManufacture;
	private String secPoolNo;
	private String secPoolName;
	
	public String getSecPoolNo() {
		return secPoolNo;
	}
	public void setSecPoolNo(String secPoolNo) {
		this.secPoolNo = secPoolNo;
	}
	public String getSecPoolName() {
		return secPoolName;
	}
	public void setSecPoolName(String secPoolName) {
		this.secPoolName = secPoolName;
	}
	
	public String getLoanBalancePrincipal() {
		return loanBalancePrincipal;
	}
	public void setLoanBalancePrincipal(String loanBalancePrincipal) {
		this.loanBalancePrincipal = loanBalancePrincipal;
	}
	public String getLoanOverdueAmount() {
		return loanOverdueAmount;
	}
	public void setLoanOverdueAmount(String loanOverdueAmount) {
		this.loanOverdueAmount = loanOverdueAmount;
	}
	public String getOverdueCharges() {
		return overdueCharges;
	}
	public void setOverdueCharges(String overdueCharges) {
		this.overdueCharges = overdueCharges;
	}
	public String getRepoFlag() {
		return repoFlag;
	}
	public void setRepoFlag(String repoFlag) {
		this.repoFlag = repoFlag;
	}
	public String getLegalFlag() {
		return legalFlag;
	}
	public void setLegalFlag(String legalFlag) {
		this.legalFlag = legalFlag;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getClosureType() {
		return closureType;
	}
	public void setClosureType(String closureType) {
		this.closureType = closureType;
	}
	public String getClosureDate() {
		return closureDate;
	}
	public void setClosureDate(String closureDate) {
		this.closureDate = closureDate;
	}
	public String getClosureMakerName() {
		return closureMakerName;
	}
	public void setClosureMakerName(String closureMakerName) {
		this.closureMakerName = closureMakerName;
	}
	public String getClosureMakerRemarks() {
		return closureMakerRemarks;
	}
	public void setClosureMakerRemarks(String closureMakerRemarks) {
		this.closureMakerRemarks = closureMakerRemarks;
	}
	public String getClosureAuthoremarks() {
		return closureAuthoremarks;
	}
	public void setClosureAuthoremarks(String closureAuthoremarks) {
		this.closureAuthoremarks = closureAuthoremarks;
	}
	public String getClosureAuthorName() {
		return closureAuthorName;
	}
	public void setClosureAuthorName(String closureAuthorName) {
		this.closureAuthorName = closureAuthorName;
	}
	public String getReschType() {
		return reschType;
	}
	public void setReschType(String reschType) {
		this.reschType = reschType;
	}
	public String getReschDate() {
		return reschDate;
	}
	public void setReschDate(String reschDate) {
		this.reschDate = reschDate;
	}
	public String getReschMakerName() {
		return reschMakerName;
	}
	public void setReschMakerName(String reschMakerName) {
		this.reschMakerName = reschMakerName;
	}
	public String getReschMakerRemarks() {
		return reschMakerRemarks;
	}
	public void setReschMakerRemarks(String reschMakerRemarks) {
		this.reschMakerRemarks = reschMakerRemarks;
	}
	public String getReschAuthoremarks() {
		return reschAuthoremarks;
	}
	public void setReschAuthoremarks(String reschAuthoremarks) {
		this.reschAuthoremarks = reschAuthoremarks;
	}
	public String getReschAuthorName() {
		return reschAuthorName;
	}
	public void setReschAuthorName(String reschAuthorName) {
		this.reschAuthorName = reschAuthorName;
	}
	public String getSecPoolType() {
		return secPoolType;
	}
	public void setSecPoolType(String secPoolType) {
		this.secPoolType = secPoolType;
	}
	public String getSecPoolMarkingDate() {
		return secPoolMarkingDate;
	}
	public void setSecPoolMarkingDate(String secPoolMarkingDate) {
		this.secPoolMarkingDate = secPoolMarkingDate;
	}
	public String getSecAgencyName() {
		return secAgencyName;
	}
	public void setSecAgencyName(String secAgencyName) {
		this.secAgencyName = secAgencyName;
	}
	public String getCaseMarkingStatus() {
		return caseMarkingStatus;
	}
	public void setCaseMarkingStatus(String caseMarkingStatus) {
		this.caseMarkingStatus = caseMarkingStatus;
	}
	public String getCaseMarkingFlag() {
		return caseMarkingFlag;
	}
	public void setCaseMarkingFlag(String caseMarkingFlag) {
		this.caseMarkingFlag = caseMarkingFlag;
	}
	public String getCaseMarkingDate() {
		return caseMarkingDate;
	}
	public void setCaseMarkingDate(String caseMarkingDate) {
		this.caseMarkingDate = caseMarkingDate;
	}
	public String getCaseMarkingAgencyName() {
		return caseMarkingAgencyName;
	}
	public void setCaseMarkingAgencyName(String caseMarkingAgencyName) {
		this.caseMarkingAgencyName = caseMarkingAgencyName;
	}
	public String getCaseMarkingOtherDetails() {
		return caseMarkingOtherDetails;
	}
	public void setCaseMarkingOtherDetails(String caseMarkingOtherDetails) {
		this.caseMarkingOtherDetails = caseMarkingOtherDetails;
	}
	public String getCaseMarkingRemarks() {
		return caseMarkingRemarks;
	}
	public void setCaseMarkingRemarks(String caseMarkingRemarks) {
		this.caseMarkingRemarks = caseMarkingRemarks;
	}
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getDisbursalStatus() {
		return disbursalStatus;
	}
	public void setDisbursalStatus(String disbursalStatus) {
		this.disbursalStatus = disbursalStatus;
	}
	public String getFileStatus() {
		return fileStatus;
	}
	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}
	
	public String getLoanRepaymentMode() {
		return loanRepaymentMode;
	}
	public void setLoanRepaymentMode(String loanRepaymentMode) {
		this.loanRepaymentMode = loanRepaymentMode;
	}
	public String getLoanFlatRate() {
		return loanFlatRate;
	}
	public void setLoanFlatRate(String loanFlatRate) {
		this.loanFlatRate = loanFlatRate;
	}
	public String getLoanEffRate() {
		return loanEffRate;
	}
	public void setLoanEffRate(String loanEffRate) {
		this.loanEffRate = loanEffRate;
	}
	public String getLoanInstallmentType() {
		return loanInstallmentType;
	}
	public void setLoanInstallmentType(String loanInstallmentType) {
		this.loanInstallmentType = loanInstallmentType;
	}
	public String getLoanInstallmentMode() {
		return loanInstallmentMode;
	}
	public void setLoanInstallmentMode(String loanInstallmentMode) {
		this.loanInstallmentMode = loanInstallmentMode;
	}
	public String getSectorType() {
		return sectorType;
	}
	public void setSectorType(String sectorType) {
		this.sectorType = sectorType;
	}
	public String getNpaFlag() {
		return npaFlag;
	}
	public void setNpaFlag(String npaFlag) {
		this.npaFlag = npaFlag;
	}
	public String getLoanDpd() {
		return loanDpd;
	}
	public void setLoanDpd(String loanDpd) {
		this.loanDpd = loanDpd;
	}
	public String getLoanDpdString() {
		return loanDpdString;
	}
	public void setLoanDpdString(String loanDpdString) {
		this.loanDpdString = loanDpdString;
	}
	public String getLoanTenure() {
		return loanTenure;
	}
	public void setLoanTenure(String loanTenure) {
		this.loanTenure = loanTenure;
	}
	public String getLoanMaturityDate() {
		return loanMaturityDate;
	}
	public void setLoanMaturityDate(String loanMaturityDate) {
		this.loanMaturityDate = loanMaturityDate;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}
	public String getBusinessDate() {
		return businessDate;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanApprovalDate(String loanApprovalDate) {
		this.loanApprovalDate = loanApprovalDate;
	}
	public String getLoanApprovalDate() {
		return loanApprovalDate;
	}
	public void setSoaLink(String soaLink) {
		this.soaLink = soaLink;
	}
	public String getSoaLink() {
		return soaLink;
	}
	public void setFirstEmi(String firstEmi) {
		this.firstEmi = firstEmi;
	}
	public String getFirstEmi() {
		return firstEmi;
	}
	public void setLastEmi(String lastEmi) {
		this.lastEmi = lastEmi;
	}
	public String getLastEmi() {
		return lastEmi;
	}
	public void setCustomerRole(String customerRole) {
		this.customerRole = customerRole;
	}
	public String getCustomerRole() {
		return customerRole;
	}
	public String getAssetNature() {
		return assetNature;
	}
	public void setAssetNature(String assetNature) {
		this.assetNature = assetNature;
	}
	public String getVehicleMake() {
		return vehicleMake;
	}
	public void setVehicleMake(String vehicleMake) {
		this.vehicleMake = vehicleMake;
	}
	public String getVehicleModel() {
		return vehicleModel;
	}
	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}
	public String getManufact() {
		return manufact;
	}
	public void setManufact(String manufact) {
		this.manufact = manufact;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public String getChasisNumber() {
		return chasisNumber;
	}
	public void setChasisNumber(String chasisNumber) {
		this.chasisNumber = chasisNumber;
	}
	public String getEngineNumber() {
		return engineNumber;
	}
	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}
	public String getYearofManufacture() {
		return yearofManufacture;
	}
	public void setYearofManufacture(String yearofManufacture) {
		this.yearofManufacture = yearofManufacture;
	}
	public void setCaseUnMarkingDate(String caseUnMarkingDate) {
		this.caseUnMarkingDate = caseUnMarkingDate;
	}
	public String getCaseUnMarkingDate() {
		return caseUnMarkingDate;
	}
	

}
