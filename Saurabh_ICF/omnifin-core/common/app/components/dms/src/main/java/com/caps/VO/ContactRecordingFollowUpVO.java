package com.caps.VO;

public class ContactRecordingFollowUpVO {
	private String dpdstring;
	private String actionDate;
	private String actionStartTime;
	private String actionCode;
	private String instrumentNo;
	private String receiptNo;
	private String instrumentDate;
	private String contactMode;
	private String personContacted;
	private String placeContacted;
	private String actionAmount;
	private String nxtActionDate;
	private String nxtActionTime;
	private String contactedBy;
	private String remarks;
	private String customerMemoLine;
	private String	loanId;
	private String	scheme;
	private String	branch;
	private String	loanNo;
	private String	product;
	private String	customerId;
	private String	customerName;
	private String	customerDOB;
	private String	addressDtl;
	private String	city;
	private String	state;
	private String	email;
	private String	primaryPhone;
	private String	secondryPhone;
	private String	queue;
	private String	allocationDate;
	private String	userId;
	private String	queueDate;
	
	private String loanAmount;
	private String disbersalDate;
	private String emiAmount;
	private String totalNoOfEMI;
	private String noOfEMIDue;
	private String noOfEmiPaid;

	private String tenure;
	private String dpd;
	private String principalOut;
	private String interestOverDue;
	private String principalOverDue;
	private String otherCharge;
	private String cbcChages;
	private String lppCharges;
	private String totalAmountDue;
	private String npaStatus;
	private String frequency;
	private String emiStartDate;
	private String emiEndDate;
	private String interestRate;
	private String interestType;
	private String assetCost;
	private String billingCycle;
    private String makerId;
    private String businessDate;
    private String paymentMode;
    private String exclationFlag;
    private String totalTimeTaken;
    private String lbxUserSearchId;
    private String flatRate;
    private String effRate;
    private String irr1;
    private String irr2;
	
    private String assetType;
    private String assetDesc;
    private String assetClass;
    private String assetValue;
    private String panNO;
    private String sTaxNo;
    private String appDate;
    private String secDate;
    /*Change By Arun Starts Here 21/06/2012*/
   
    private String lastRecieptAmount;
    private String lastRecieptDate;
    private String teamLeaderName;
    private String repaymentMode;
    private String fatherHusbandName;
    /*kanika code*/
    private String vehicleRegisNo;
    private String vehicleChasisNo;
    private String vehicleMake;
    private String vehicleModel;
    private String vehicleRegistrationDate;
    private String vehicleEngineNo;
    // code added by sachin
    private String rm;
    private String rmSup;
    private String receiptDate;
    private String receiptAmount;
    private String overdueEMI;
    private String actionTime;//added by Rohit
    private String relationshipS;//added by Rohit
    
    private String addressType;
    private String applicantType;
    private String contactCodeType;
    private String contactCode;
    
    public String getOverdueEMI() {
		return overdueEMI;
	}
	public void setOverdueEMI(String overdueEMI) {
		this.overdueEMI = overdueEMI;
	}
	public String getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	public String getReceiptAmount() {
		return receiptAmount;
	}
	public void setReceiptAmount(String receiptAmount) {
		this.receiptAmount = receiptAmount;
	}
	public String getRm() {
		return rm;
	}
	public void setRm(String rm) {
		this.rm = rm;
	}
	public String getRmSup() {
		return rmSup;
	}
	public void setRmSup(String rmSup) {
		this.rmSup = rmSup;
	}
	public String getVehicleRegisNo() {
		return vehicleRegisNo;
	}
	public void setVehicleRegisNo(String vehicleRegisNo) {
		this.vehicleRegisNo = vehicleRegisNo;
	}
	public String getVehicleChasisNo() {
		return vehicleChasisNo;
	}
	public void setVehicleChasisNo(String vehicleChasisNo) {
		this.vehicleChasisNo = vehicleChasisNo;
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
	public String getVehicleRegistrationDate() {
		return vehicleRegistrationDate;
	}
	public void setVehicleRegistrationDate(String vehicleRegistrationDate) {
		this.vehicleRegistrationDate = vehicleRegistrationDate;
	}
	public String getVehicleEngineNo() {
		return vehicleEngineNo;
	}
	public void setVehicleEngineNo(String vehicleEngineNo) {
		this.vehicleEngineNo = vehicleEngineNo;
	}
	/*kanika code end*/
	public String getLastRecieptAmount() {
		return lastRecieptAmount;
	}
	public void setLastRecieptAmount(String lastRecieptAmount) {
		this.lastRecieptAmount = lastRecieptAmount;
	}
	public String getLastRecieptDate() {
		return lastRecieptDate;
	}
	public void setLastRecieptDate(String lastRecieptDate) {
		this.lastRecieptDate = lastRecieptDate;
	}
	public String getTeamLeaderName() {
		return teamLeaderName;
	}
	public void setTeamLeaderName(String teamLeaderName) {
		this.teamLeaderName = teamLeaderName;
	}
	public String getRepaymentMode() {
		return repaymentMode;
	}
	public void setRepaymentMode(String repaymentMode) {
		this.repaymentMode = repaymentMode;
	}
	public String getFatherHusbandName() {
		return fatherHusbandName;
	}
	public void setFatherHusbandName(String fatherHusbandName) {
		this.fatherHusbandName = fatherHusbandName;
	}
	/*Change By Arun ends Here 21/06/2012*/
	
	public String getNoOfEmiPaid() {
		return noOfEmiPaid;
	}
	public void setNoOfEmiPaid(String noOfEmiPaid) {
		this.noOfEmiPaid = noOfEmiPaid;
	}
    public String getAppDate() {
		return appDate;
	}
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	public String getSecDate() {
		return secDate;
	}
	public void setSecDate(String secDate) {
		this.secDate = secDate;
	}
	public String getPanNO() {
		return panNO;
	}
	public void setPanNO(String panNO) {
		this.panNO = panNO;
	}
	public String getsTaxNo() {
		return sTaxNo;
	}
	public void setsTaxNo(String sTaxNo) {
		this.sTaxNo = sTaxNo;
	}
	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	public String getAssetDesc() {
		return assetDesc;
	}
	public void setAssetDesc(String assetDesc) {
		this.assetDesc = assetDesc;
	}
	public String getAssetClass() {
		return assetClass;
	}
	public void setAssetClass(String assetClass) {
		this.assetClass = assetClass;
	}
	public String getAssetValue() {
		return assetValue;
	}
	public void setAssetValue(String assetValue) {
		this.assetValue = assetValue;
	}
	public String getFlatRate() {
		return flatRate;
	}
	public void setFlatRate(String flatRate) {
		this.flatRate = flatRate;
	}
	public String getEffRate() {
		return effRate;
	}
	public void setEffRate(String effRate) {
		this.effRate = effRate;
	}
	public String getIrr1() {
		return irr1;
	}
	public void setIrr1(String irr1) {
		this.irr1 = irr1;
	}
	public String getIrr2() {
		return irr2;
	}
	public void setIrr2(String irr2) {
		this.irr2 = irr2;
	}
	public String getDpdstring() {
		return dpdstring;
	}
	public void setDpdstring(String dpdstring) {
		this.dpdstring = dpdstring;
	}
	public String getLbxUserSearchId() {
		return lbxUserSearchId;
	}
	public void setLbxUserSearchId(String lbxUserSearchId) {
		this.lbxUserSearchId = lbxUserSearchId;
	}
	public String getTotalTimeTaken() {
		return totalTimeTaken;
	}
	public void setTotalTimeTaken(String totalTimeTaken) {
		this.totalTimeTaken = totalTimeTaken;
	}
	public String getExclationFlag() {
		return exclationFlag;
	}
	public void setExclationFlag(String exclationFlag) {
		this.exclationFlag = exclationFlag;
	}
	public String getPaymentMode() {
	return paymentMode;
}
public void setPaymentMode(String paymentMode) {
	this.paymentMode = paymentMode;
}
	public String getMakerId() {
		return makerId;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getDisbersalDate() {
		return disbersalDate;
	}
	public void setDisbersalDate(String disbersalDate) {
		this.disbersalDate = disbersalDate;
	}
	public String getEmiAmount() {
		return emiAmount;
	}
	public void setEmiAmount(String emiAmount) {
		this.emiAmount = emiAmount;
	}
	public String getTotalNoOfEMI() {
		return totalNoOfEMI;
	}
	public void setTotalNoOfEMI(String totalNoOfEMI) {
		this.totalNoOfEMI = totalNoOfEMI;
	}
	public String getNoOfEMIDue() {
		return noOfEMIDue;
	}
	public void setNoOfEMIDue(String noOfEMIDue) {
		this.noOfEMIDue = noOfEMIDue;
	}
	public String getTenure() {
		return tenure;
	}
	public void setTenure(String tenure) {
		this.tenure = tenure;
	}
	public String getDpd() {
		return dpd;
	}
	public void setDpd(String dpd) {
		this.dpd = dpd;
	}
	public String getPrincipalOut() {
		return principalOut;
	}
	public void setPrincipalOut(String principalOut) {
		this.principalOut = principalOut;
	}
	public String getInterestOverDue() {
		return interestOverDue;
	}
	public void setInterestOverDue(String interestOverDue) {
		this.interestOverDue = interestOverDue;
	}
	public String getPrincipalOverDue() {
		return principalOverDue;
	}
	public void setPrincipalOverDue(String principalOverDue) {
		this.principalOverDue = principalOverDue;
	}
	public String getOtherCharge() {
		return otherCharge;
	}
	public void setOtherCharge(String otherCharge) {
		this.otherCharge = otherCharge;
	}
	public String getCbcChages() {
		return cbcChages;
	}
	public void setCbcChages(String cbcChages) {
		this.cbcChages = cbcChages;
	}
	public String getLppCharges() {
		return lppCharges;
	}
	public void setLppCharges(String lppCharges) {
		this.lppCharges = lppCharges;
	}
	public String getTotalAmountDue() {
		return totalAmountDue;
	}
	public void setTotalAmountDue(String totalAmountDue) {
		this.totalAmountDue = totalAmountDue;
	}
	public String getNpaStatus() {
		return npaStatus;
	}
	public void setNpaStatus(String npaStatus) {
		this.npaStatus = npaStatus;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getEmiStartDate() {
		return emiStartDate;
	}
	public void setEmiStartDate(String emiStartDate) {
		this.emiStartDate = emiStartDate;
	}
	public String getEmiEndDate() {
		return emiEndDate;
	}
	public void setEmiEndDate(String emiEndDate) {
		this.emiEndDate = emiEndDate;
	}
	public String getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}
	public String getInterestType() {
		return interestType;
	}
	public void setInterestType(String interestType) {
		this.interestType = interestType;
	}
	public String getAssetCost() {
		return assetCost;
	}
	public void setAssetCost(String assetCost) {
		this.assetCost = assetCost;
	}
	public String getBillingCycle() {
		return billingCycle;
	}
	public void setBillingCycle(String billingCycle) {
		this.billingCycle = billingCycle;
	}
	public String getQueue() {
		return queue;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
	public String getAllocationDate() {
		return allocationDate;
	}
	public void setAllocationDate(String allocationDate) {
		this.allocationDate = allocationDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getQueueDate() {
		return queueDate;
	}
	public void setQueueDate(String queueDate) {
		this.queueDate = queueDate;
	}
	public String getAddressDtl() {
		return addressDtl;
	}
	public void setAddressDtl(String addressDtl) {
		this.addressDtl = addressDtl;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPrimaryPhone() {
		return primaryPhone;
	}
	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}
	public String getSecondryPhone() {
		return secondryPhone;
	}
	public void setSecondryPhone(String secondryPhone) {
		this.secondryPhone = secondryPhone;
	}
	public String getCustomerDOB() {
		return customerDOB;
	}
	public void setCustomerDOB(String customerDOB) {
		this.customerDOB = customerDOB;
	}
	public String getActionDate() {
		return actionDate;
	}
	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}
	public String getActionStartTime() {
		return actionStartTime;
	}
	public void setActionStartTime(String actionStartTime) {
		this.actionStartTime = actionStartTime;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getInstrumentNo() {
		return instrumentNo;
	}
	public void setInstrumentNo(String instrumentNo) {
		this.instrumentNo = instrumentNo;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getInstrumentDate() {
		return instrumentDate;
	}
	public void setInstrumentDate(String instrumentDate) {
		this.instrumentDate = instrumentDate;
	}
	public String getContactMode() {
		return contactMode;
	}
	public void setContactMode(String contactMode) {
		this.contactMode = contactMode;
	}
	public String getPersonContacted() {
		return personContacted;
	}
	public void setPersonContacted(String personContacted) {
		this.personContacted = personContacted;
	}
	public String getPlaceContacted() {
		return placeContacted;
	}
	public void setPlaceContacted(String placeContacted) {
		this.placeContacted = placeContacted;
	}
	public String getActionAmount() {
		return actionAmount;
	}
	public void setActionAmount(String actionAmount) {
		this.actionAmount = actionAmount;
	}
	public String getNxtActionDate() {
		return nxtActionDate;
	}
	public void setNxtActionDate(String nxtActionDate) {
		this.nxtActionDate = nxtActionDate;
	}
	public String getNxtActionTime() {
		return nxtActionTime;
	}
	public void setNxtActionTime(String nxtActionTime) {
		this.nxtActionTime = nxtActionTime;
	}
	public String getContactedBy() {
		return contactedBy;
	}
	public void setContactedBy(String contactedBy) {
		this.contactedBy = contactedBy;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCustomerMemoLine() {
		return customerMemoLine;
	}
	public void setCustomerMemoLine(String customerMemoLine) {
		this.customerMemoLine = customerMemoLine;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getActionTime() {
		return actionTime;
	}
	public void setActionTime(String actionTime) {
		this.actionTime = actionTime;
	}
	
	public String getRelationshipS() {
		return relationshipS;
	}
	public void setRelationshipS(String relationshipS) {
		this.relationshipS = relationshipS;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getApplicantType() {
		return applicantType;
	}
	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}
	public String getContactCodeType() {
		return contactCodeType;
	}
	public void setContactCodeType(String contactCodeType) {
		this.contactCodeType = contactCodeType;
	}
	public String getContactCode() {
		return contactCode;
	}
	public void setContactCode(String contactCode) {
		this.contactCode = contactCode;
	}
}
