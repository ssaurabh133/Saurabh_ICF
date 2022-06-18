package com.VO;

import java.io.Serializable;

public class CustomerSaveVo implements Serializable {
	private String addId;
	private String addr_type;
	private String country;
	private String txtCountryCode;
    private String bp_type;
	private String bp_id;
	private String bp_id1;
	private String addr1;
	private String addr2;
	private String addr3;
	private String state;
	private String txtStateCode;
	private String dist;
	private String txtDistCode;
	private String region;
	private int regionId;
	private String pincode;
	private String primaryPhoneNo;
	private String alternatePhoneNo;
	private String tollfreeNo;
	private String faxNo;
	private String landMark;
	private String noYears;
	private String noMonths;
	private String flag;
	private String communicationAddress;
	private String pageStatus;
	private String statusCase;
	private String updateFlag;
	private String makerId;
	private String makerDate;
	private String addDetails;
	private String  defaultcountryid;
	private String  defaultcountryname;

	private String firstName;       
	private String middleName;
	private String lastName;
	private String relationshipS;
	private String primaryRefMbNo;
	private String alternateRefPhNo;
	private String knowingSince;
	private String tableToInsert;
	private String refName;
	private String refId;
	private String bpId;
	private String totalRecordSize;

	private String  distanceBranch;
	private String  tahsil;

	private String customerProfile;
	private String customerName;	
	private String relationShip;
	
	private String relationCode;
	private String relationShipDesc;
	
	private String businessDesc;
//	Surendra Cust Bank Details Fields Added from here
	private String 	bankCode;
	private String 	lbxBankID;
	private String 	bankBranchName;
	private String 	lbxBranchID;
	private String 	ifscCode;
	private String 	micrCode;
	private String accountNo;
	private String bankAccountStatus;	
	private String customerId;
	private String pageStat;
	private String recStatus;
	private String addRef;
	private String source;
	private String loanId;
	// Rohit Changes for Sarv Surksha Starts......
	private String nominee;
	private String nomineeRelation;
	private String nomineeDOB;
//	Rohit Changes for Sarv Surksha end......
	private String gstIn;	
	private String dealId;

	private String txnId;
	private String dealNo;

	private String institutionEmail;
private String relationShipFlag;
private String relationShipId;
private String relation;
private String relationAddressId;
private String relationCustomerId;
private String relationCustomerName;
private String relationPhone;
private String relationCustomerType;	
	private String stdNo;	

	public String getAddRef() {
		return addRef;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public void setAddRef(String addRef) {
		this.addRef = addRef;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public String getBusinessDesc() {
		return businessDesc;
	}
	public void setBusinessDesc(String businessDesc) {
		this.businessDesc = businessDesc;
	}
	public String getRelationShipDesc() {
		return relationShipDesc;
	}
	public void setRelationShipDesc(String relationShipDesc) {
		this.relationShipDesc = relationShipDesc;
	}
	public String getRelationCode() {
		return relationCode;
	}
	public void setRelationCode(String relationCode) {
		this.relationCode = relationCode;
	}
	public String getBpId() {
		return bpId;
	}
	public void setBpId(String bpId) {
		this.bpId = bpId;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getRefName() {
		return refName;
	}
	public void setRefName(String refName) {
		this.refName = refName;
	}
	public String getTableToInsert() {
		return tableToInsert;
	}
	public void setTableToInsert(String tableToInsert) {
		this.tableToInsert = tableToInsert;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRelationshipS() {
		return relationshipS;
	}
	public void setRelationshipS(String relationshipS) {
		this.relationshipS = relationshipS;
	}
	public String getPrimaryRefMbNo() {
		return primaryRefMbNo;
	}
	public void setPrimaryRefMbNo(String primaryRefMbNo) {
		this.primaryRefMbNo = primaryRefMbNo;
	}
	public String getAlternateRefPhNo() {
		return alternateRefPhNo;
	}
	public void setAlternateRefPhNo(String alternateRefPhNo) {
		this.alternateRefPhNo = alternateRefPhNo;
	}
	public String getKnowingSince() {
		return knowingSince;
	}
	public void setKnowingSince(String knowingSince) {
		this.knowingSince = knowingSince;
	}

	public String getDistanceBranch() {
		return distanceBranch;
	}
	public void setDistanceBranch(String distanceBranch) {
		this.distanceBranch = distanceBranch;
	}
	public String getTahsil() {
		return tahsil;
	}
	public void setTahsil(String tahsil) {
		this.tahsil = tahsil;
	}

	public String getAddDetails() {
		return addDetails;
	}
	public void setAddDetails(String addDetails) {
		this.addDetails = addDetails;
	}
	public String getMakerId() {
		return makerId;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	
	public String getDefaultcountryid() {
		return defaultcountryid;
	}
	public void setDefaultcountryid(String defaultcountryid) {
		this.defaultcountryid = defaultcountryid;
	}
	public String getDefaultcountryname() {
		return defaultcountryname;
	}
	public void setDefaultcountryname(String defaultcountryname) {
		this.defaultcountryname = defaultcountryname;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public String getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}
	public String getStatusCase() {
		return statusCase;
	}
	public void setStatusCase(String statusCase) {
		this.statusCase = statusCase;
	}
	public String getPageStatus() {
		return pageStatus;
	}
	public void setPageStatus(String pageStatus) {
		this.pageStatus = pageStatus;
	}
	public String getTxtCountryCode() {
		return txtCountryCode;
	}
	public void setTxtCountryCode(String txtCountryCode) {
		this.txtCountryCode = txtCountryCode;
	}
	public String getTxtStateCode() {
		return txtStateCode;
	}
	public void setTxtStateCode(String txtStateCode) {
		this.txtStateCode = txtStateCode;
	}
	public String getTxtDistCode() {
		return txtDistCode;
	}
	public void setTxtDistCode(String txtDistCode) {
		this.txtDistCode = txtDistCode;
	}
	public String getCommunicationAddress() {
		return communicationAddress;
	}
	public void setCommunicationAddress(String communicationAddress) {
		this.communicationAddress = communicationAddress;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getBp_id1() {
		return bp_id1;
	}
	public void setBp_id1(String bpId1) {
		bp_id1 = bpId1;
	}
	public String getAddId() {
		return addId;
	}
	public void setAddId(String addId) {
		this.addId = addId;
	}
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getAddr_type() {
		return addr_type;
	}
	public void setAddr_type(String addrType) {
		addr_type = addrType;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getAddr3() {
		return addr3;
	}
	public void setAddr3(String addr3) {
		this.addr3 = addr3;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return dist;
	}
	public void setCity(String city) {
		this.dist = city;
	}
	
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	
	
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public String getLandMark() {
		return landMark;
	}
	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}
	public String getNoYears() {
		return noYears;
	}
	public void setNoYears(String noYears) {
		this.noYears = noYears;
	}
	public String getDist() {
		return dist;
	}
	public void setDist(String dist) {
		this.dist = dist;
	}
	public String getPrimaryPhoneNo() {
		return primaryPhoneNo;
	}
	public void setPrimaryPhoneNo(String primaryPhoneNo) {
		this.primaryPhoneNo = primaryPhoneNo;
	}
	public String getAlternatePhoneNo() {
		return alternatePhoneNo;
	}
	public void setAlternatePhoneNo(String alternatePhoneNo) {
		this.alternatePhoneNo = alternatePhoneNo;
	}
	public String getTollfreeNo() {
		return tollfreeNo;
	}
	public void setTollfreeNo(String tollfreeNo) {
		this.tollfreeNo = tollfreeNo;
	}
	public String getBp_type() {
		return bp_type;
	}
	public void setBp_type(String bpType) {
		bp_type = bpType;
	}
	public String getBp_id() {
		return bp_id;
	}
	public void setBp_id(String bpId) {
		bp_id = bpId;
	}
	public void setNoMonths(String noMonths) {
		this.noMonths = noMonths;
	}
	public String getNoMonths() {
		return noMonths;
	}
	public void setCustomerProfile(String customerProfile) {
		this.customerProfile = customerProfile;
	}
	public String getCustomerProfile() {
		return customerProfile;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setRelationShip(String relationShip) {
		this.relationShip = relationShip;
	}
	public String getRelationShip() {
		return relationShip;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getLbxBankID() {
		return lbxBankID;
	}
	public void setLbxBankID(String lbxBankID) {
		this.lbxBankID = lbxBankID;
	}
	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	public String getLbxBranchID() {
		return lbxBranchID;
	}
	public void setLbxBranchID(String lbxBranchID) {
		this.lbxBranchID = lbxBranchID;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getMicrCode() {
		return micrCode;
	}
	public void setMicrCode(String micrCode) {
		this.micrCode = micrCode;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getBankAccountStatus() {
		return bankAccountStatus;
	}
	public void setBankAccountStatus(String bankAccountStatus) {
		this.bankAccountStatus = bankAccountStatus;
	}	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getPageStat() {
		return pageStat;
	}
	public void setPageStat(String pageStat) {
		this.pageStat = pageStat;
	}
	public String getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(String totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public String getNominee() {
		return nominee;
	}
	public void setNominee(String nominee) {
		this.nominee = nominee;
	}
	public String getNomineeRelation() {
		return nomineeRelation;
	}
	public void setNomineeRelation(String nomineeRelation) {
		this.nomineeRelation = nomineeRelation;
	}
	public String getNomineeDOB() {
		return nomineeDOB;
	}
	public void setNomineeDOB(String nomineeDOB) {
		this.nomineeDOB = nomineeDOB;
	}
	public String getGstIn() {
		return gstIn;
	}
	public void setGstIn(String gstIn) {
		this.gstIn = gstIn;
}
	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;

	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getDealNo() {
		return dealNo;
	}
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;

	}
	public String getInstitutionEmail() {
		return institutionEmail;
	}
	public void setInstitutionEmail(String institutionEmail) {
		this.institutionEmail = institutionEmail;

	}	
	public String getStdNo() {
		return stdNo;
	}
	public void setStdNo(String stdNo) {
		this.stdNo = stdNo;
	}
	public String getRelationShipFlag() {
		return relationShipFlag;
	}
	public void setRelationShipFlag(String relationShipFlag) {
		this.relationShipFlag = relationShipFlag;
	}
	public String getRelationShipId() {
		return relationShipId;
	}
	public void setRelationShipId(String relationShipId) {
		this.relationShipId = relationShipId;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getRelationAddressId() {
		return relationAddressId;
	}
	public void setRelationAddressId(String relationAddressId) {
		this.relationAddressId = relationAddressId;
	}
	public String getRelationCustomerId() {
		return relationCustomerId;
	}
	public void setRelationCustomerId(String relationCustomerId) {
		this.relationCustomerId = relationCustomerId;
	}
	public String getRelationCustomerName() {
		return relationCustomerName;
	}
	public void setRelationCustomerName(String relationCustomerName) {
		this.relationCustomerName = relationCustomerName;
	}
	public String getRelationPhone() {
		return relationPhone;
	}
	public void setRelationPhone(String relationPhone) {
		this.relationPhone = relationPhone;
	}
	public String getRelationCustomerType() {
		return relationCustomerType;
	}
	public void setRelationCustomerType(String relationCustomerType) {
		this.relationCustomerType = relationCustomerType;
	}

	
}
