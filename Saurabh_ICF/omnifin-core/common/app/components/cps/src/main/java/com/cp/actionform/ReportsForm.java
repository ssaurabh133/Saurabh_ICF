package com.cp.actionform;

import org.apache.struts.action.ActionForm;

public class ReportsForm extends ActionForm {
	

	//	Added by Rudra Start 
	String partnerShipType;
	String partnerType;
	String partnerCode;
	String lbxpartnerCodeID;
	String partnerCodeButton;
	
	String partnerName;
	String lbxPartnerId;
	String partnerNameButton;
	String loan_NO;
	String lbxloan_Id;
	String loanNoButton;
	
	
//	Rudra Ends....
	
	public String getPartnerShipType() {
		return partnerShipType;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getLbxPartnerId() {
		return lbxPartnerId;
	}
	public void setLbxPartnerId(String lbxPartnerId) {
		this.lbxPartnerId = lbxPartnerId;
	}
	public String getPartnerNameButton() {
		return partnerNameButton;
	}
	public void setPartnerNameButton(String partnerNameButton) {
		this.partnerNameButton = partnerNameButton;
	}
	
	public String getLoan_NO() {
		return loan_NO;
	}
	public void setLoan_NO(String loanNO) {
		loan_NO = loanNO;
	}
	public String getLbxloan_Id() {
		return lbxloan_Id;
	}
	public void setLbxloan_Id(String lbxloanId) {
		lbxloan_Id = lbxloanId;
	}
	public String getLoanNoButton() {
		return loanNoButton;
	}
	public void setLoanNoButton(String loanNoButton) {
		this.loanNoButton = loanNoButton;
	}
	public void setPartnerShipType(String partnerShipType) {
		this.partnerShipType = partnerShipType;
	}
	public String getPartnerType() {
		return partnerType;
	}
	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}
	public String getPartnerCode() {
		return partnerCode;
	}
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
	public String getLbxpartnerCodeID() {
		return lbxpartnerCodeID;
	}
	public void setLbxpartnerCodeID(String lbxpartnerCodeID) {
		this.lbxpartnerCodeID = lbxpartnerCodeID;
	}
	public String getPartnerCodeButton() {
		return partnerCodeButton;
	}
	public void setPartnerCodeButton(String partnerCodeButton) {
		this.partnerCodeButton = partnerCodeButton;
	}
	String branch;
	String loanno;
	String from;
	String to;
	String fromDate;
	String toDate;
	String asonDate;
	String  branchid;	
	String status;
	String status1;
	String status2;
	String status3;
	String status4;
	String status5;
	String lbxBranchId;
	String reportName;
	String reportid;
	String specificLoanNo;
	String lbx_loan_from_id;
	String lbx_loan_to_id;
	String lbxDealNo;
	String toDeal;
	String lbxDealTo;
	String fromDeal;
	String specificDealNo;
	String installmentDate;
	String userName;
	String presentationdate;
	String instrumentType;
	String approvalDateFrom;
	String approvalDateTo;
	String loanNumber;
	String lbx_loan_Number;
	String lbxloannumber;
	String rdpLoanNumber;
	String rdp;
	String BankID;
	String lbxBankID;
	String bankName;
	String reportId;
	String reportformatid;
	String reportformat;
	String Address1;
	String companyadd;
	String Msg;
	String Phone;
	String Fax;
	String Email;
	String City;
	String pincode;
	String website;
	String groupNumber;
	String lbxGroupID;
	String reportNameId;
	String AsOnDateForSD;
	String fromdateforsd;
	String todateforsd;
	String dateTo;
	String dateFrom;
	String batchNo;
	String lbxBatchNo;
	//added by neeraj
	String dashboard;
	String instrumentMode;
	//Amit Starts	
	String rateInterest;
	String interestType;
	String lbxDisbursalID;
	String sponsor;
	String sponsorCode;
	String sponsorDesc;
	String sponsorDate;
	String financeYear;
	String intCerFromDate;
	String intCerToDate;
    String recStatus;
    String schemeID;
    String schemeDesc;
	String paymentMode;
	String charges;
	String chargesId;
	String customerRole;
	// sidharth starts
	String interestMethod;
	String detailOrSum;
	String amtType;
	String intervalFreq;
	//sidharth ends	
	String cutOffFlag;
	String soaFor;	
	String adviceType1;
	String adviceAmount1;
	String adjustedAmount1;
	String chargeType1;
	String chargeAmount1;
	String blanceAmount1;
	String adviceType2;
	String adviceAmount2;
	String adjustedAmount2;
	String chargeType2;
	String chargeAmount2;
	String blanceAmount2;
	String adviceType3;
	String adviceAmount3;
	String adjustedAmount3;
	String chargeType3;
	String chargeAmount3;
	String blanceAmount3;
	String instrumentId;
	String instrumentNo;
	String productDescs;
	String productIds;
	String bucket;
	String loginBranchDesc;
	String loginBranchId;
	String approvalLevel;
	String companyLogo;
	String balConfFromDate;
	String balConfToDate;
	String status6;
	String batchPurpose;
	String exitLoad;
	String foreClosureCharges;
	String receiptDate;
	String lbxBatchNO;
	String lbxBranchID;
	String productCategory;
	String producTCategory;
	String producTCategoryID;
	String loanClassification;
	String instrumentsMode;
	String scheme;
	String lbxSchemeID;
	String simulationReportType;
	String lbxLoanId;
	String REP_loanno;
	String branch3;
	String branch4;
	// Kumar Aman Change Start
	String month;
	String branchWiseBranchSummary;
	String branchWiseExecutiveSummary;
	String reporttype;
	String reporttype12;
	String passwordPdf;
	
	
	public String getReporttype12() {
		return reporttype12;
	}
	public void setReporttype12(String reporttype12) {
		this.reporttype12 = reporttype12;
	}
	String startDate1;
	public String getStartDate1() {
		return startDate1;
	}
	public void setStartDate1(String startDate1) {
		this.startDate1 = startDate1;
	}
	public String getReporttype() {
		return reporttype;
	}
	public void setReporttype(String reporttype) {
		this.reporttype = reporttype;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStartPeriod() {
		return startPeriod;
	}
	public void setStartPeriod(String startPeriod) {
		this.startPeriod = startPeriod;
	}
	String startDate;
	String startPeriod;
	String P_Period_date;
	
	// Kumar Aman Change End	
		
	public String getP_Period_date() {
		return P_Period_date;
	}
	public void setP_Period_date(String p_Period_date) {
		P_Period_date = p_Period_date;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getBranchWiseBranchSummary() {
		return branchWiseBranchSummary;
	}
	public void setBranchWiseBranchSummary(String branchWiseBranchSummary) {
		this.branchWiseBranchSummary = branchWiseBranchSummary;
	}
	public String getBranchWiseExecutiveSummary() {
		return branchWiseExecutiveSummary;
	}
	public void setBranchWiseExecutiveSummary(String branchWiseExecutiveSummary) {
		this.branchWiseExecutiveSummary = branchWiseExecutiveSummary;
	}

	public String getBranch4() {
		return branch4;
	}
	public void setBranch4(String branch4) {
		this.branch4 = branch4;
	}
	public String getBranch3() {
		return branch3;
	}
	public void setBranch3(String branch3) {
		this.branch3 = branch3;
	}
	
	String selectDate;
	
	
	public String getSelectDate() {
		return selectDate;
	}
	public void setSelectDate(String selectDate) {
		this.selectDate = selectDate;
	}
	public String getREP_loanno() {
		return REP_loanno;
	}
	public void setREP_loanno(String rEP_loanno) {
		REP_loanno = rEP_loanno;
	}
	public String getLbxLoanId() {
		return lbxLoanId;
	}
	public void setLbxLoanId(String lbxLoanId) {
		this.lbxLoanId = lbxLoanId;
	}
	public String getLbxSchemeID() {
		return lbxSchemeID;
	}
	public void setLbxSchemeID(String lbxSchemeID) {
		this.lbxSchemeID = lbxSchemeID;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getSimulationReportType() {
		return simulationReportType;
	}
	public void setSimulationReportType(String simulationReportType) {
		this.simulationReportType = simulationReportType;
	}
	

	public String getLoanClassification() {
		return loanClassification;
	}
	public void setLoanClassification(String loanClassification) {
		this.loanClassification = loanClassification;
	}
	public String getProducTCategoryID() {
		return producTCategoryID;
	}
	public void setProducTCategoryID(String producTCategoryID) {
		this.producTCategoryID = producTCategoryID;
	}
	public String getProducTCategory() {
		return producTCategory;
	}
	public void setProducTCategory(String producTCategory) {
		this.producTCategory = producTCategory;
	}
	public String getStatus6() {
		return status6;
	}
	public void setStatus6(String status6) {
		this.status6 = status6;
	}
	public String getBalConfFromDate() {
		return balConfFromDate;
	}
	public void setBalConfFromDate(String balConfFromDate) {
		this.balConfFromDate = balConfFromDate;
	}
	public String getBalConfToDate() {
		return balConfToDate;
	}
	public void setBalConfToDate(String balConfToDate) {
		this.balConfToDate = balConfToDate;
	}
	public String getCompanyLogo() {
		return companyLogo;
	}
	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}
	public String getProductDescs() {
		return productDescs;
	}
	public void setProductDescs(String productDescs) {
		this.productDescs = productDescs;
	}
	public String getProductIds() {
		return productIds;
	}
	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	public String getLoginBranchDesc() {
		return loginBranchDesc;
	}
	public void setLoginBranchDesc(String loginBranchDesc) {
		this.loginBranchDesc = loginBranchDesc;
	}
	public String getLoginBranchId() {
		return loginBranchId;
	}
	public void setLoginBranchId(String loginBranchId) {
		this.loginBranchId = loginBranchId;
	}
	public String getApprovalLevel() {
		return approvalLevel;
	}
	public void setApprovalLevel(String approvalLevel) {
		this.approvalLevel = approvalLevel;
	}
	public String getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}
	public String getInstrumentNo() {
		return instrumentNo;
	}
	public void setInstrumentNo(String instrumentNo) {
		this.instrumentNo = instrumentNo;
	}
	public String getAdviceType1() {
		return adviceType1;
	}
	public void setAdviceType1(String adviceType1) {
		this.adviceType1 = adviceType1;
	}
	public String getAdviceAmount1() {
		return adviceAmount1;
	}
	public void setAdviceAmount1(String adviceAmount1) {
		this.adviceAmount1 = adviceAmount1;
	}
	public String getAdjustedAmount1() {
		return adjustedAmount1;
	}
	public void setAdjustedAmount1(String adjustedAmount1) {
		this.adjustedAmount1 = adjustedAmount1;
	}
	public String getChargeType1() {
		return chargeType1;
	}
	public void setChargeType1(String chargeType1) {
		this.chargeType1 = chargeType1;
	}
	public String getChargeAmount1() {
		return chargeAmount1;
	}
	public void setChargeAmount1(String chargeAmount1) {
		this.chargeAmount1 = chargeAmount1;
	}
	public String getBlanceAmount1() {
		return blanceAmount1;
	}
	public void setBlanceAmount1(String blanceAmount1) {
		this.blanceAmount1 = blanceAmount1;
	}
	public String getAdviceType2() {
		return adviceType2;
	}
	public void setAdviceType2(String adviceType2) {
		this.adviceType2 = adviceType2;
	}
	public String getAdviceAmount2() {
		return adviceAmount2;
	}
	public void setAdviceAmount2(String adviceAmount2) {
		this.adviceAmount2 = adviceAmount2;
	}
	public String getAdjustedAmount2() {
		return adjustedAmount2;
	}
	public void setAdjustedAmount2(String adjustedAmount2) {
		this.adjustedAmount2 = adjustedAmount2;
	}
	public String getChargeType2() {
		return chargeType2;
	}
	public void setChargeType2(String chargeType2) {
		this.chargeType2 = chargeType2;
	}
	public String getChargeAmount2() {
		return chargeAmount2;
	}
	public void setChargeAmount2(String chargeAmount2) {
		this.chargeAmount2 = chargeAmount2;
	}
	public String getBlanceAmount2() {
		return blanceAmount2;
	}
	public void setBlanceAmount2(String blanceAmount2) {
		this.blanceAmount2 = blanceAmount2;
	}
	public String getAdviceType3() {
		return adviceType3;
	}
	public void setAdviceType3(String adviceType3) {
		this.adviceType3 = adviceType3;
	}
	public String getAdviceAmount3() {
		return adviceAmount3;
	}
	public void setAdviceAmount3(String adviceAmount3) {
		this.adviceAmount3 = adviceAmount3;
	}
	public String getAdjustedAmount3() {
		return adjustedAmount3;
	}
	public void setAdjustedAmount3(String adjustedAmount3) {
		this.adjustedAmount3 = adjustedAmount3;
	}
	public String getChargeType3() {
		return chargeType3;
	}
	public void setChargeType3(String chargeType3) {
		this.chargeType3 = chargeType3;
	}
	public String getChargeAmount3() {
		return chargeAmount3;
	}
	public void setChargeAmount3(String chargeAmount3) {
		this.chargeAmount3 = chargeAmount3;
	}
	public String getBlanceAmount3() {
		return blanceAmount3;
	}
	public void setBlanceAmount3(String blanceAmount3) {
		this.blanceAmount3 = blanceAmount3;
	}
	public String getSoaFor() {
		return soaFor;
	}
	public void setSoaFor(String soaFor) {
		this.soaFor = soaFor;
	}
	public String getCutOffFlag() {
		return cutOffFlag;
	}
	public void setCutOffFlag(String cutOffFlag) {
		this.cutOffFlag = cutOffFlag;
	}
	public String getCustomerRole() {
		return customerRole;
	}
	public void setCustomerRole(String customerRole) {
		this.customerRole = customerRole;
	}
	public String getCharges() {
		return charges;
	}
	public void setCharges(String charges) {
		this.charges = charges;
	}
	public String getChargesId() {
		return chargesId;
	}
	public void setChargesId(String chargesId) {
		this.chargesId = chargesId;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	    
	public String getSchemeID() {
		return schemeID;
	}
	public void setSchemeID(String schemeID) {
		this.schemeID = schemeID;
	}
	public String getSchemeDesc() {
		return schemeDesc;
	}
	public void setSchemeDesc(String schemeDesc) {
		this.schemeDesc = schemeDesc;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	
	
	public String getIntCerFromDate() {
		return intCerFromDate;
	}
	public void setIntCerFromDate(String intCerFromDate) {
		this.intCerFromDate = intCerFromDate;
	}
	public String getIntCerToDate() {
		return intCerToDate;
	}
	public void setIntCerToDate(String intCerToDate) {
		this.intCerToDate = intCerToDate;
	}
	public String getFinanceYear() {
		return financeYear;
	}
	public void setFinanceYear(String financeYear) {
		this.financeYear = financeYear;
	}
	public String getSponsorDate() {
		return sponsorDate;
	}
	public void setSponsorDate(String sponsorDate) {
		this.sponsorDate = sponsorDate;
	}
	public String getSponsor() {
		return sponsor;
	}
	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}
	public String getSponsorCode() {
		return sponsorCode;
	}
	public void setSponsorCode(String sponsorCode) {
		this.sponsorCode = sponsorCode;
	}
	public String getSponsorDesc() {
		return sponsorDesc;
	}
	public void setSponsorDesc(String sponsorDesc) {
		this.sponsorDesc = sponsorDesc;
	}
	public String getLbxDisbursalID() {
		return lbxDisbursalID;
	}
	public void setLbxDisbursalID(String lbxDisbursalID) {
		this.lbxDisbursalID = lbxDisbursalID;
	}
	public String getRateInterest() {
		return rateInterest;
	}
	public void setRateInterest(String rateInterest) {
		this.rateInterest = rateInterest;
	}
	public String getInterestType() {
		return interestType;
	}
	public void setInterestType(String interestType) {
		this.interestType = interestType;
	}
	//sidharth
	public String getInterestMethod() {
		return interestMethod;
	}
	public void setInterestMethod(String interestMethod) {
		this.interestMethod = interestMethod;
	}
	
	public String getDetailOrSum() {
		return detailOrSum;
	}
	public void setDetailOrSum(String detailOrSum) {
		this.detailOrSum = detailOrSum;
	}
	public String getAmtType() {
		return amtType;
	}
	public void setAmtType(String amtType) {
		this.amtType = amtType;
	}
	public String getIntervalFreq() {
		return intervalFreq;
	}
	public void setIntervalFreq(String intervalFreq) {
		this.intervalFreq = intervalFreq;
	}
	//sidharth Ends
	//Amit Ends
	public String getInstrumentMode() {
		return instrumentMode;
	}
	public void setInstrumentMode(String instrumentMode) {
		this.instrumentMode = instrumentMode;
	}
	public String getLbxBatchNo() {
		return lbxBatchNo;
	}
	public String getDashboard() {
		return dashboard;
	}
	public void setDashboard(String dashboard) {
		this.dashboard = dashboard;
	}
	public void setLbxBatchNo(String lbxBatchNo) {
		this.lbxBatchNo = lbxBatchNo;
	}
	String batchGenerates;
	String presentationDateForBatch;
	
	public String getPresentationDateForBatch() {
		return presentationDateForBatch;
	}
	public void setPresentationDateForBatch(String presentationDateForBatch) {
		this.presentationDateForBatch = presentationDateForBatch;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getBatchGenerates() {
		return batchGenerates;
	}
	public void setBatchGenerates(String batchGenerates) {
		this.batchGenerates = batchGenerates;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getFromdateforsd() {
		return fromdateforsd;
	}
	public void setFromdateforsd(String fromdateforsd) {
		this.fromdateforsd = fromdateforsd;
	}
	public String getTodateforsd() {
		return todateforsd;
	}
	public void setTodateforsd(String todateforsd) {
		this.todateforsd = todateforsd;
	}
	public String getAsOnDateForSD() {
		return AsOnDateForSD;
	}
	public void setAsOnDateForSD(String asOnDateForSD) {
		AsOnDateForSD = asOnDateForSD;
	}
	public String getReportNameId() {
		return reportNameId;
	}
	public void setReportNameId(String reportNameId) {
		this.reportNameId = reportNameId;
	}
	public String getLoanNumber() {
		return loanNumber;
	}
	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}
	public String getLbx_loan_Number() {
		return lbx_loan_Number;
	}
	public void setLbx_loan_Number(String lbxLoanNumber) {
		lbx_loan_Number = lbxLoanNumber;
	}
	public String getApprovalDateFrom() {
		return approvalDateFrom;
	}
	public void setApprovalDateFrom(String approvalDateFrom) {
		this.approvalDateFrom = approvalDateFrom;
	}
	public String getApprovalDateTo() {
		return approvalDateTo;
	}
	public void setApprovalDateTo(String approvalDateTo) {
		this.approvalDateTo = approvalDateTo;
	}
	public String getPresentationdate() {
		return presentationdate;
	}
	public void setPresentationdate(String presentationdate) {
		this.presentationdate = presentationdate;
	}
	public String getInstrumentType() {
		return instrumentType;
	}
	public void setInstrumentType(String instrumentType) {
		this.instrumentType = instrumentType;
	}
	public String getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}
	public String getLbxGroupID() {
		return lbxGroupID;
	}
	public void setLbxGroupID(String lbxGroupID) {
		this.lbxGroupID = lbxGroupID;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getAddress1() {
		return Address1;
	}
	public void setAddress1(String address1) {
		Address1 = address1;
	}
	public String getCompanyadd() {
		return companyadd;
	}
	public void setCompanyadd(String companyadd) {
		this.companyadd = companyadd;
	}
	public String getMsg() {
		return Msg;
	}
	public void setMsg(String msg) {
		Msg = msg;
	}
		public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getFax() {
		return Fax;
	}
	public void setFax(String fax) {
		Fax = fax;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getReportformatid() {
		return reportformatid;
	}
	public void setReportformatid(String reportformatid) {
		this.reportformatid = reportformatid;
	}
	
	public String getReportformat() {
		return reportformat;
	}
	public void setReportformat(String reportformat) {
		this.reportformat = reportformat;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	
	public String getReportid() {
		return reportid;
	}
	public String getStatus5() {
		return status5;
	}
	public void setStatus5(String status5) {
		this.status5 = status5;
	}
	public void setReportid(String reportid) {
		this.reportid = reportid;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getLbxBankID() {
		return lbxBankID;
	}
	public void setLbxBankID(String lbxBankID) {
		this.lbxBankID = lbxBankID;
	}
	public String getBankID() {
		return BankID;
	}
	public void setBankID(String bankID) {
		BankID = bankID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLbxloannumber() {
		return lbxloannumber;
	}
	public void setLbxloannumber(String lbxloannumber) {
		this.lbxloannumber = lbxloannumber;
	}
	public String getRdpLoanNumber() {
		return rdpLoanNumber;
	}
	public void setRdpLoanNumber(String rdpLoanNumber) {
		this.rdpLoanNumber = rdpLoanNumber;
	}
	public String getRdp() {
		return rdp;
	}
	public void setRdp(String rdp) {
		this.rdp = rdp;
	}
	public String getInstallmentDate() {
		return installmentDate;
	}
	public void setInstallmentDate(String installmentDate) {
		this.installmentDate = installmentDate;
	}
	public String getSpecificDealNo() {
		return specificDealNo;
	}
	public void setSpecificDealNo(String specificDealNo) {
		this.specificDealNo = specificDealNo;
	}
	public String getFromDeal() {
		return fromDeal;
	}
	public void setFromDeal(String fromDeal) {
		this.fromDeal = fromDeal;
	}
	public String getLbxDealNo() {
		return lbxDealNo;
	}
	public void setLbxDealNo(String lbxDealNo) {
		this.lbxDealNo = lbxDealNo;
	}
	public String getToDeal() {
		return toDeal;
	}
	public void setToDeal(String toDeal) {
		this.toDeal = toDeal;
	}
	public String getLbxDealTo() {
		return lbxDealTo;
	}
	public void setLbxDealTo(String lbxDealTo) {
		this.lbxDealTo = lbxDealTo;
	}
	
	public String getStatus4() {
		return status4;
	}
	public void setStatus4(String status4) {
		this.status4 = status4;
	}
	public String getStatus3() {
		return status3;
	}
	public void setStatus3(String status3) {
		this.status3 = status3;
	}
	public String getStatus2() {
		return status2;
	}
	public void setStatus2(String status2) {
		this.status2 = status2;
	}
	public String getStatus1() {
		return status1;
	}
	public void setStatus1(String status1) {
		this.status1 = status1;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLbx_loan_from_id() {
		return lbx_loan_from_id;
	}
	public void setLbx_loan_from_id(String lbxLoanFromId) {
		lbx_loan_from_id = lbxLoanFromId;
	}
	
	public String getLbx_loan_to_id() {
		return lbx_loan_to_id;
	}
	public void setLbx_loan_to_id(String lbxLoanToId) {
		lbx_loan_to_id = lbxLoanToId;
	}
	public String getSpecificLoanNo() {
		return specificLoanNo;
	}
	public void setSpecificLoanNo(String specificLoanNo) {
		this.specificLoanNo = specificLoanNo;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getLoanno() {
		return loanno;
	}
	public void setLoanno(String loanno) {
		this.loanno = loanno;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getAsonDate() {
		return asonDate;
	}
	public void setAsonDate(String asonDate) {
		this.asonDate = asonDate;
	}
	public String getBranchid() {
		return branchid;
	}
	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}
	public String getLbxBranchId() {
		return lbxBranchId;
	}
	public void setLbxBranchId(String lbxBranchId) {
		this.lbxBranchId = lbxBranchId;
	}
	public String getBatchPurpose() {
		return batchPurpose;
	}
	public void setBatchPurpose(String batchPurpose) {
		this.batchPurpose = batchPurpose;
	}
	public String getExitLoad() {
		return exitLoad;
	}
	public void setExitLoad(String exitLoad) {
		this.exitLoad = exitLoad;
	}
	public String getForeClosureCharges() {
		return foreClosureCharges;
	}
	public void setForeClosureCharges(String foreClosureCharges) {
		this.foreClosureCharges = foreClosureCharges;
	}
	public String getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	public String getLbxBatchNO() {
		return lbxBatchNO;
	}
	public void setLbxBatchNO(String lbxBatchNO) {
		this.lbxBatchNO = lbxBatchNO;
	}
	public String getLbxBranchID() {
		return lbxBranchID;
	}
	public void setLbxBranchID(String lbxBranchID) {
		this.lbxBranchID = lbxBranchID;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public String getInstrumentsMode() {
		return instrumentsMode;
	}
	public void setInstrumentsMode(String instrumentsMode) {
		this.instrumentsMode = instrumentsMode;
	}
	public String getPasswordPdf() {
		return passwordPdf;
	}
	public void setPasswordPdf(String passwordPdf) {
		this.passwordPdf = passwordPdf;
	}
	
	
}
