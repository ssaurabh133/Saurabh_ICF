package com.cm.actionform;

import org.apache.struts.action.ActionForm;

public class ReportsForm extends ActionForm {
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
	   String expectedIRR;
	   String branch;
	   String loanno;
	   String from;
	   String to;
	   String fromDate;
	   String toDate;
	   String asonDate;
	   String branchid;
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
	   String dashboard;
	   String instrumentMode;
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
	   String interestMethod;
	   String detailOrSum;
	   String amtType;
	   String intervalFreq;
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
	   String month;
	   String branchWiseBranchSummary;
	   String branchWiseExecutiveSummary;
	   String reporttype;
	   String reporttype12;
	   String reporttype13;
	   String reporttype11;
	   private String bankName1;
	   private String bankAccount;
	   String lbxBranchIdMultiple;
	   String startDate1;
	   String startDate;
	   String startPeriod;
	   String P_Period_date;
	   String selectDate;
	   String batchGenerates;
	   String presentationDateForBatch;
	   	String reportSubType;
	   String rtgsType;	
	   private String drfId;
		private String suplier;
		private String lbxSupplier;
		private String manufacturerDesc;
		private String manufacturerId;
		private String drfCustomername;
		private String drfDealNo;
		private String drfDealId;
		private String customerId;
		private String  product_desc;
		private String customerName;
		private String productId;
		private String overdueReportLoanId;
		private String earliest;
		private String early_loan_id;
		private String repaymentScheduleFromDate;
		private String  repaymentScheduleToDate;
		private String repaymentScheduleId;
		private String repaymentType;
		private String cirScheduleId;
		private String cirType;
		private String asonDateInvoice;
		private String invoiceLoanId;
		private String cirRemarks;
		private String grpType;
		private String groupID;
		private String groupName;
		private String dealNoCov;
		private String lbxDealNoHID;
		private String frequency;
		private String reportFormatValue;
		private String reportFormatDescription;
		private String reportformat1;
		private String covDcsn;
		private String loanNoCov;
		private String loanNo;
		private String lbxLoanNoHID;
		private String loanApprovalDate;
		private String rstatus;
		String passwordPdf;
		
		  public String getRstatus() 
		  {
			  return rstatus;
		  }
		  public void setRstatus(String rstatus)  
		  {
			  this.rstatus = rstatus;
		  }
	 
	   public String getPartnerShipType()
	   {
	/*  */     return this.partnerShipType;
	   }
	   public String getExpectedIRR() {
	/*  */     return this.expectedIRR;
	   }
	   public void setExpectedIRR(String expectedIRR) {
	/*  */     this.expectedIRR = expectedIRR;
	   }
	   public String getPartnerName() {
	/*  */     return this.partnerName;
	   }
	   public void setPartnerName(String partnerName) {
	/*  */     this.partnerName = partnerName;
	   }
	   public String getLbxPartnerId() {
	/*  */     return this.lbxPartnerId;
	   }
	   public void setLbxPartnerId(String lbxPartnerId) {
	/*  */     this.lbxPartnerId = lbxPartnerId;
	   }
	   public String getPartnerNameButton() {
	/*  */     return this.partnerNameButton;
	   }
	   public void setPartnerNameButton(String partnerNameButton) {
	/*  */     this.partnerNameButton = partnerNameButton;
	   }
	 
	   public String getLoan_NO() {
	/*  */     return this.loan_NO;
	   }
	   public void setLoan_NO(String loanNO) {
	/*  */     this.loan_NO = loanNO;
	   }
	   public String getLbxloan_Id() {
	/*  */     return this.lbxloan_Id;
	   }
	   public void setLbxloan_Id(String lbxloanId) {
	/*  */     this.lbxloan_Id = lbxloanId;
	   }
	   public String getLoanNoButton() {
	/*  */     return this.loanNoButton;
	   }
	   public void setLoanNoButton(String loanNoButton) {
	/*  */     this.loanNoButton = loanNoButton;
	   }
	   public void setPartnerShipType(String partnerShipType) {
	/*  */     this.partnerShipType = partnerShipType;
	   }
	   public String getPartnerType() {
	/*  */     return this.partnerType;
	   }
	   public void setPartnerType(String partnerType) {
	/*  */     this.partnerType = partnerType;
	   }
	   public String getPartnerCode() {
	/*  */     return this.partnerCode;
	   }
	   public void setPartnerCode(String partnerCode) {
	/*  */     this.partnerCode = partnerCode;
	   }
	   public String getLbxpartnerCodeID() {
	/*  */     return this.lbxpartnerCodeID;
	   }
	   public void setLbxpartnerCodeID(String lbxpartnerCodeID) {
	/*  */     this.lbxpartnerCodeID = lbxpartnerCodeID;
	   }
	   public String getPartnerCodeButton() {
	/*  */     return this.partnerCodeButton;
	   }
	   public void setPartnerCodeButton(String partnerCodeButton) {
	/*  */     this.partnerCodeButton = partnerCodeButton;
	   }
	 
	   public String getLbxBranchIdMultiple()
	   {
	/* */     return this.lbxBranchIdMultiple;
	   }
	   public void setLbxBranchIdMultiple(String lbxBranchIdMultiple) {
	/* */     this.lbxBranchIdMultiple = lbxBranchIdMultiple;
	   }
	   public String getReporttype11() {
	/* */     return this.reporttype11;
	   }
	   public void setReporttype11(String reporttype11) {
	/* */     this.reporttype11 = reporttype11;
	   }
	   public String getReporttype13() {
	/* */     return this.reporttype13;
	   }
	   public void setReporttype13(String reporttype13) {
	/* */     this.reporttype13 = reporttype13;
	   }
	   public String getReporttype12() {
	/* */     return this.reporttype12;
	   }
	   public void setReporttype12(String reporttype12) {
	/* */     this.reporttype12 = reporttype12;
	   }
	 
	   public String getStartDate1() {
	/* */     return this.startDate1;
	   }
	   public void setStartDate1(String startDate1) {
	/* */     this.startDate1 = startDate1;
	   }
	   public String getReporttype() {
	/* */     return this.reporttype;
	   }
	   public void setReporttype(String reporttype) {
	/* */     this.reporttype = reporttype;
	   }
	   public String getStartDate() {
	/* */     return this.startDate;
	   }
	   public void setStartDate(String startDate) {
	/* */     this.startDate = startDate;
	   }
	   public String getStartPeriod() {
	/* */     return this.startPeriod;
	   }
	   public void setStartPeriod(String startPeriod) {
	/* */     this.startPeriod = startPeriod;
	   }
	 
	   public String getP_Period_date()
	   {
	/* */     return this.P_Period_date;
	   }
	   public void setP_Period_date(String p_Period_date) {
	/* */     this.P_Period_date = p_Period_date;
	   }
	   public String getMonth() {
	/* */     return this.month;
	   }
	   public void setMonth(String month) {
	/* */     this.month = month;
	   }
	   public String getBranchWiseBranchSummary() {
	/* */     return this.branchWiseBranchSummary;
	   }
	   public void setBranchWiseBranchSummary(String branchWiseBranchSummary) {
	/* */     this.branchWiseBranchSummary = branchWiseBranchSummary;
	   }
	   public String getBranchWiseExecutiveSummary() {
	/* */     return this.branchWiseExecutiveSummary;
	   }
	   public void setBranchWiseExecutiveSummary(String branchWiseExecutiveSummary) {
	/* */     this.branchWiseExecutiveSummary = branchWiseExecutiveSummary;
	   }
	 
	   public String getBranch4() {
	/* */     return this.branch4;
	   }
	   public void setBranch4(String branch4) {
	/* */     this.branch4 = branch4;
	   }
	   public String getBranch3() {
	/* */     return this.branch3;
	   }
	   public void setBranch3(String branch3) {
	/* */     this.branch3 = branch3;
	   }
	 
	   public String getSelectDate()
	   {
	/* */     return this.selectDate;
	   }
	   public void setSelectDate(String selectDate) {
	/* */     this.selectDate = selectDate;
	   }
	   public String getREP_loanno() {
	/* */     return this.REP_loanno;
	   }
	   public void setREP_loanno(String rEP_loanno) {
	/* */     this.REP_loanno = rEP_loanno;
	   }
	   public String getLbxLoanId() {
	/* */     return this.lbxLoanId;
	   }
	   public void setLbxLoanId(String lbxLoanId) {
	/* */     this.lbxLoanId = lbxLoanId;
	   }
	   public String getLbxSchemeID() {
	/* */     return this.lbxSchemeID;
	   }
	   public void setLbxSchemeID(String lbxSchemeID) {
	/* */     this.lbxSchemeID = lbxSchemeID;
	   }
	   public String getScheme() {
	/* */     return this.scheme;
	   }
	   public void setScheme(String scheme) {
	/* */     this.scheme = scheme;
	   }
	   public String getSimulationReportType() {
	/* */     return this.simulationReportType;
	   }
	   public void setSimulationReportType(String simulationReportType) {
	/* */     this.simulationReportType = simulationReportType;
	   }
	 
	   public String getLoanClassification()
	   {
	/* */     return this.loanClassification;
	   }
	   public void setLoanClassification(String loanClassification) {
	/* */     this.loanClassification = loanClassification;
	   }
	   public String getProducTCategoryID() {
	/* */     return this.producTCategoryID;
	   }
	   public void setProducTCategoryID(String producTCategoryID) {
	/* */     this.producTCategoryID = producTCategoryID;
	   }
	   public String getProducTCategory() {
	/* */     return this.producTCategory;
	   }
	   public void setProducTCategory(String producTCategory) {
	/* */     this.producTCategory = producTCategory;
	   }
	   public String getStatus6() {
	/* */     return this.status6;
	   }
	   public void setStatus6(String status6) {
	/* */     this.status6 = status6;
	   }
	   public String getBalConfFromDate() {
	/* */     return this.balConfFromDate;
	   }
	   public void setBalConfFromDate(String balConfFromDate) {
	/* */     this.balConfFromDate = balConfFromDate;
	   }
	   public String getBalConfToDate() {
	/* */     return this.balConfToDate;
	   }
	   public void setBalConfToDate(String balConfToDate) {
	/* */     this.balConfToDate = balConfToDate;
	   }
	   public String getCompanyLogo() {
	/* */     return this.companyLogo;
	   }
	   public void setCompanyLogo(String companyLogo) {
	/* */     this.companyLogo = companyLogo;
	   }
	   public String getProductDescs() {
	/* */     return this.productDescs;
	   }
	   public void setProductDescs(String productDescs) {
	/* */     this.productDescs = productDescs;
	   }
	   public String getProductIds() {
	/* */     return this.productIds;
	   }
	   public void setProductIds(String productIds) {
	/* */     this.productIds = productIds;
	   }
	   public String getBucket() {
	/* */     return this.bucket;
	   }
	   public void setBucket(String bucket) {
	/* */     this.bucket = bucket;
	   }
	   public String getLoginBranchDesc() {
	/* */     return this.loginBranchDesc;
	   }
	   public void setLoginBranchDesc(String loginBranchDesc) {
	/* */     this.loginBranchDesc = loginBranchDesc;
	   }
	   public String getLoginBranchId() {
	/* */     return this.loginBranchId;
	   }
	   public void setLoginBranchId(String loginBranchId) {
	/* */     this.loginBranchId = loginBranchId;
	   }
	   public String getApprovalLevel() {
	/* */     return this.approvalLevel;
	   }
	   public void setApprovalLevel(String approvalLevel) {
	/* */     this.approvalLevel = approvalLevel;
	   }
	   public String getInstrumentId() {
	/* */     return this.instrumentId;
	   }
	   public void setInstrumentId(String instrumentId) {
	/* */     this.instrumentId = instrumentId;
	   }
	   public String getInstrumentNo() {
	/* */     return this.instrumentNo;
	   }
	   public void setInstrumentNo(String instrumentNo) {
	/* */     this.instrumentNo = instrumentNo;
	   }
	   public String getAdviceType1() {
	/* */     return this.adviceType1;
	   }
	   public void setAdviceType1(String adviceType1) {
	/* */     this.adviceType1 = adviceType1;
	   }
	   public String getAdviceAmount1() {
	/* */     return this.adviceAmount1;
	   }
	   public void setAdviceAmount1(String adviceAmount1) {
	/* */     this.adviceAmount1 = adviceAmount1;
	   }
	   public String getAdjustedAmount1() {
	/* */     return this.adjustedAmount1;
	   }
	   public void setAdjustedAmount1(String adjustedAmount1) {
	/* */     this.adjustedAmount1 = adjustedAmount1;
	   }
	   public String getChargeType1() {
	/* */     return this.chargeType1;
	   }
	   public void setChargeType1(String chargeType1) {
	/* */     this.chargeType1 = chargeType1;
	   }
	   public String getChargeAmount1() {
	/* */     return this.chargeAmount1;
	   }
	   public void setChargeAmount1(String chargeAmount1) {
	/* */     this.chargeAmount1 = chargeAmount1;
	   }
	   public String getBlanceAmount1() {
	/* */     return this.blanceAmount1;
	   }
	   public void setBlanceAmount1(String blanceAmount1) {
	/* */     this.blanceAmount1 = blanceAmount1;
	   }
	   public String getAdviceType2() {
	/* */     return this.adviceType2;
	   }
	   public void setAdviceType2(String adviceType2) {
	/* */     this.adviceType2 = adviceType2;
	   }
	   public String getAdviceAmount2() {
	/* */     return this.adviceAmount2;
	   }
	   public void setAdviceAmount2(String adviceAmount2) {
	/* */     this.adviceAmount2 = adviceAmount2;
	   }
	   public String getAdjustedAmount2() {
	/* */     return this.adjustedAmount2;
	   }
	   public void setAdjustedAmount2(String adjustedAmount2) {
	/* */     this.adjustedAmount2 = adjustedAmount2;
	   }
	   public String getChargeType2() {
	/* */     return this.chargeType2;
	   }
	   public void setChargeType2(String chargeType2) {
	/* */     this.chargeType2 = chargeType2;
	   }
	   public String getChargeAmount2() {
	/* */     return this.chargeAmount2;
	   }
	   public void setChargeAmount2(String chargeAmount2) {
	/* */     this.chargeAmount2 = chargeAmount2;
	   }
	   public String getBlanceAmount2() {
	/* */     return this.blanceAmount2;
	   }
	   public void setBlanceAmount2(String blanceAmount2) {
	/* */     this.blanceAmount2 = blanceAmount2;
	   }
	   public String getAdviceType3() {
	/* */     return this.adviceType3;
	   }
	   public void setAdviceType3(String adviceType3) {
	/* */     this.adviceType3 = adviceType3;
	   }
	   public String getAdviceAmount3() {
	/* */     return this.adviceAmount3;
	   }
	   public void setAdviceAmount3(String adviceAmount3) {
	/* */     this.adviceAmount3 = adviceAmount3;
	   }
	   public String getAdjustedAmount3() {
	/* */     return this.adjustedAmount3;
	   }
	   public void setAdjustedAmount3(String adjustedAmount3) {
	/* */     this.adjustedAmount3 = adjustedAmount3;
	   }
	   public String getChargeType3() {
	/* */     return this.chargeType3;
	   }
	   public void setChargeType3(String chargeType3) {
	/* */     this.chargeType3 = chargeType3;
	   }
	   public String getChargeAmount3() {
	/* */     return this.chargeAmount3;
	   }
	   public void setChargeAmount3(String chargeAmount3) {
	/* */     this.chargeAmount3 = chargeAmount3;
	   }
	   public String getBlanceAmount3() {
	/* */     return this.blanceAmount3;
	   }
	   public void setBlanceAmount3(String blanceAmount3) {
	/* */     this.blanceAmount3 = blanceAmount3;
	   }
	   public String getSoaFor() {
	/* */     return this.soaFor;
	   }
	   public void setSoaFor(String soaFor) {
	/* */     this.soaFor = soaFor;
	   }
	   public String getCutOffFlag() {
	/* */     return this.cutOffFlag;
	   }
	   public void setCutOffFlag(String cutOffFlag) {
	/* */     this.cutOffFlag = cutOffFlag;
	   }
	   public String getCustomerRole() {
	/* */     return this.customerRole;
	   }
	   public void setCustomerRole(String customerRole) {
	/* */     this.customerRole = customerRole;
	   }
	   public String getCharges() {
	/* */     return this.charges;
	   }
	   public void setCharges(String charges) {
	/* */     this.charges = charges;
	   }
	   public String getChargesId() {
	/* */     return this.chargesId;
	   }
	   public void setChargesId(String chargesId) {
	/* */     this.chargesId = chargesId;
	   }
	   public String getPaymentMode() {
	/* */     return this.paymentMode;
	   }
	   public void setPaymentMode(String paymentMode) {
	/* */     this.paymentMode = paymentMode;
	   }
	 
	   public String getSchemeID() {
	/* */     return this.schemeID;
	   }
	   public void setSchemeID(String schemeID) {
	/* */     this.schemeID = schemeID;
	   }
	   public String getSchemeDesc() {
	/* */     return this.schemeDesc;
	   }
	   public void setSchemeDesc(String schemeDesc) {
	/* */     this.schemeDesc = schemeDesc;
	   }
	   public String getRecStatus() {
	/* */     return this.recStatus;
	   }
	   public void setRecStatus(String recStatus) {
	/* */     this.recStatus = recStatus;
	   }
	 
	   public String getIntCerFromDate()
	   {
	/* */     return this.intCerFromDate;
	   }
	   public void setIntCerFromDate(String intCerFromDate) {
	/* */     this.intCerFromDate = intCerFromDate;
	   }
	   public String getIntCerToDate() {
	/* */     return this.intCerToDate;
	   }
	   public void setIntCerToDate(String intCerToDate) {
	/* */     this.intCerToDate = intCerToDate;
	   }
	   public String getFinanceYear() {
	/* */     return this.financeYear;
	   }
	   public void setFinanceYear(String financeYear) {
	/* */     this.financeYear = financeYear;
	   }
	   public String getSponsorDate() {
	/* */     return this.sponsorDate;
	   }
	   public void setSponsorDate(String sponsorDate) {
	/* */     this.sponsorDate = sponsorDate;
	   }
	   public String getSponsor() {
	/* */     return this.sponsor;
	   }
	   public void setSponsor(String sponsor) {
	/* */     this.sponsor = sponsor;
	   }
	   public String getSponsorCode() {
	/* */     return this.sponsorCode;
	   }
	   public void setSponsorCode(String sponsorCode) {
	/* */     this.sponsorCode = sponsorCode;
	   }
	   public String getSponsorDesc() {
	/* */     return this.sponsorDesc;
	   }
	   public void setSponsorDesc(String sponsorDesc) {
	/* */     this.sponsorDesc = sponsorDesc;
	   }
	   public String getLbxDisbursalID() {
	/* */     return this.lbxDisbursalID;
	   }
	   public void setLbxDisbursalID(String lbxDisbursalID) {
	/* */     this.lbxDisbursalID = lbxDisbursalID;
	   }
	   public String getRateInterest() {
	/* */     return this.rateInterest;
	   }
	   public void setRateInterest(String rateInterest) {
	/* */     this.rateInterest = rateInterest;
	   }
	   public String getInterestType() {
	/* */     return this.interestType;
	   }
	   public void setInterestType(String interestType) {
	/* */     this.interestType = interestType;
	   }
	 
	   public String getInterestMethod() {
	/* */     return this.interestMethod;
	   }
	   public void setInterestMethod(String interestMethod) {
	/* */     this.interestMethod = interestMethod;
	   }
	 
	   public String getDetailOrSum() {
	/* */     return this.detailOrSum;
	   }
	   public void setDetailOrSum(String detailOrSum) {
	/* */     this.detailOrSum = detailOrSum;
	   }
	   public String getAmtType() {
	/* */     return this.amtType;
	   }
	   public void setAmtType(String amtType) {
	/* */     this.amtType = amtType;
	   }
	   public String getIntervalFreq() {
	/* */     return this.intervalFreq;
	   }
	   public void setIntervalFreq(String intervalFreq) {
	/* */     this.intervalFreq = intervalFreq;
	   }
	 
	   public String getInstrumentMode()
	   {
	/* */     return this.instrumentMode;
	   }
	   public void setInstrumentMode(String instrumentMode) {
	/* */     this.instrumentMode = instrumentMode;
	   }
	   public String getLbxBatchNo() {
	/* */     return this.lbxBatchNo;
	   }
	   public String getDashboard() {
	/* */     return this.dashboard;
	   }
	   public void setDashboard(String dashboard) {
	/* */     this.dashboard = dashboard;
	   }
	   public void setLbxBatchNo(String lbxBatchNo) {
	/* */     this.lbxBatchNo = lbxBatchNo;
	   }
	 
	   public String getPresentationDateForBatch()
	   {
	/* */     return this.presentationDateForBatch;
	   }
	   public void setPresentationDateForBatch(String presentationDateForBatch) {
	/* */     this.presentationDateForBatch = presentationDateForBatch;
	   }
	   public String getBatchNo() {
	/* */     return this.batchNo;
	   }
	   public void setBatchNo(String batchNo) {
	/* */     this.batchNo = batchNo;
	   }
	   public String getBatchGenerates() {
	/* */     return this.batchGenerates;
	   }
	   public void setBatchGenerates(String batchGenerates) {
	/* */     this.batchGenerates = batchGenerates;
	   }
	   public String getDateTo() {
	/* */     return this.dateTo;
	   }
	   public void setDateTo(String dateTo) {
	/* */     this.dateTo = dateTo;
	   }
	   public String getDateFrom() {
	/* */     return this.dateFrom;
	   }
	   public void setDateFrom(String dateFrom) {
	/* */     this.dateFrom = dateFrom;
	   }
	   public String getFromdateforsd() {
	/* */     return this.fromdateforsd;
	   }
	   public void setFromdateforsd(String fromdateforsd) {
	/* */     this.fromdateforsd = fromdateforsd;
	   }
	   public String getTodateforsd() {
	/* */     return this.todateforsd;
	   }
	   public void setTodateforsd(String todateforsd) {
	/* */     this.todateforsd = todateforsd;
	   }
	   public String getAsOnDateForSD() {
	/* */     return this.AsOnDateForSD;
	   }
	   public void setAsOnDateForSD(String asOnDateForSD) {
	/* */     this.AsOnDateForSD = asOnDateForSD;
	   }
	   public String getReportNameId() {
	/* */     return this.reportNameId;
	   }
	   public void setReportNameId(String reportNameId) {
	/* */     this.reportNameId = reportNameId;
	   }
	   public String getLoanNumber() {
	/* */     return this.loanNumber;
	   }
	   public void setLoanNumber(String loanNumber) {
	/* */     this.loanNumber = loanNumber;
	   }
	   public String getLbx_loan_Number() {
	/* */     return this.lbx_loan_Number;
	   }
	   public void setLbx_loan_Number(String lbxLoanNumber) {
	/* */     this.lbx_loan_Number = lbxLoanNumber;
	   }
	   public String getApprovalDateFrom() {
	/* */     return this.approvalDateFrom;
	   }
	   public void setApprovalDateFrom(String approvalDateFrom) {
	/* */     this.approvalDateFrom = approvalDateFrom;
	   }
	   public String getApprovalDateTo() {
	/* */     return this.approvalDateTo;
	   }
	   public void setApprovalDateTo(String approvalDateTo) {
	/* */     this.approvalDateTo = approvalDateTo;
	   }
	   public String getPresentationdate() {
	/* */     return this.presentationdate;
	   }
	   public void setPresentationdate(String presentationdate) {
	/* */     this.presentationdate = presentationdate;
	   }
	   public String getInstrumentType() {
	/* */     return this.instrumentType;
	   }
	   public void setInstrumentType(String instrumentType) {
	/* */     this.instrumentType = instrumentType;
	   }
	   public String getGroupNumber() {
	/* */     return this.groupNumber;
	   }
	   public void setGroupNumber(String groupNumber) {
	/* */     this.groupNumber = groupNumber;
	   }
	   public String getLbxGroupID() {
	/* */     return this.lbxGroupID;
	   }
	   public void setLbxGroupID(String lbxGroupID) {
	/* */     this.lbxGroupID = lbxGroupID;
	   }
	   public String getCity() {
	/* */     return this.City;
	   }
	   public void setCity(String city) {
	/* */     this.City = city;
	   }
	   public String getPincode() {
	/* */     return this.pincode;
	   }
	   public void setPincode(String pincode) {
	/* */     this.pincode = pincode;
	   }
	   public String getWebsite() {
	/* */     return this.website;
	   }
	   public void setWebsite(String website) {
	/* */     this.website = website;
	   }
	   public String getAddress1() {
	/* */     return this.Address1;
	   }
	   public void setAddress1(String address1) {
	/* */     this.Address1 = address1;
	   }
	   public String getCompanyadd() {
	/* */     return this.companyadd;
	   }
	   public void setCompanyadd(String companyadd) {
	/* */     this.companyadd = companyadd;
	   }
	   public String getMsg() {
	/* */     return this.Msg;
	   }
	   public void setMsg(String msg) {
	/* */     this.Msg = msg;
	   }
	   public String getPhone() {
	/* */     return this.Phone;
	   }
	   public void setPhone(String phone) {
	/* */     this.Phone = phone;
	   }
	   public String getFax() {
	/* */     return this.Fax;
	   }
	   public void setFax(String fax) {
	/* */     this.Fax = fax;
	   }
	   public String getEmail() {
	/* */     return this.Email;
	   }
	   public void setEmail(String email) {
	/* */     this.Email = email;
	   }
	   public String getReportformatid() {
	/* */     return this.reportformatid;
	   }
	   public void setReportformatid(String reportformatid) {
	/* */     this.reportformatid = reportformatid;
	   }
	 
	   public String getReportformat() {
	/* */     return this.reportformat;
	   }
	   public void setReportformat(String reportformat) {
	/* */     this.reportformat = reportformat;
	   }
	   public String getReportId() {
	/* */     return this.reportId;
	   }
	   public void setReportId(String reportId) {
	/* */     this.reportId = reportId;
	   }
	 
	   public String getReportid() {
	/* */     return this.reportid;
	   }
	   public String getStatus5() {
	/* */     return this.status5;
	   }
	   public void setStatus5(String status5) {
	/* */     this.status5 = status5;
	   }
	   public void setReportid(String reportid) {
	/* */     this.reportid = reportid;
	   }
	   public String getBankName() {
	/* */     return this.bankName;
	   }
	   public void setBankName(String bankName) {
	/* */     this.bankName = bankName;
	   }
	   public String getLbxBankID() {
	/* */     return this.lbxBankID;
	   }
	   public void setLbxBankID(String lbxBankID) {
	/* */     this.lbxBankID = lbxBankID;
	   }
	   public String getBankID() {
	/* */     return this.BankID;
	   }
	   public void setBankID(String bankID) {
	/* */     this.BankID = bankID;
	   }
	   public String getUserName() {
	/* */     return this.userName;
	   }
	   public void setUserName(String userName) {
	/* */     this.userName = userName;
	   }
	   public String getLbxloannumber() {
	/* */     return this.lbxloannumber;
	   }
	   public void setLbxloannumber(String lbxloannumber) {
	/* */     this.lbxloannumber = lbxloannumber;
	   }
	   public String getRdpLoanNumber() {
	/* */     return this.rdpLoanNumber;
	   }
	   public void setRdpLoanNumber(String rdpLoanNumber) {
	/* */     this.rdpLoanNumber = rdpLoanNumber;
	   }
	   public String getRdp() {
	/* */     return this.rdp;
	   }
	   public void setRdp(String rdp) {
	/* */     this.rdp = rdp;
	   }
	   public String getInstallmentDate() {
	/* */     return this.installmentDate;
	   }
	   public void setInstallmentDate(String installmentDate) {
	/* */     this.installmentDate = installmentDate;
	   }
	   public String getSpecificDealNo() {
	/* */     return this.specificDealNo;
	   }
	   public void setSpecificDealNo(String specificDealNo) {
	/* */     this.specificDealNo = specificDealNo;
	   }
	   public String getFromDeal() {
	/* */     return this.fromDeal;
	   }
	   public void setFromDeal(String fromDeal) {
	/* */     this.fromDeal = fromDeal;
	   }
	   public String getLbxDealNo() {
	/* */     return this.lbxDealNo;
	   }
	   public void setLbxDealNo(String lbxDealNo) {
	     this.lbxDealNo = lbxDealNo;
	   }
	   public String getToDeal() {
	     return this.toDeal;
	   }
	   public void setToDeal(String toDeal) {
	     this.toDeal = toDeal;
	   }
	   public String getLbxDealTo() {
	     return this.lbxDealTo;
	   }
	   public void setLbxDealTo(String lbxDealTo) {
	     this.lbxDealTo = lbxDealTo;
	   }
	 
	   public String getStatus4() {
	     return this.status4;
	   }
	   public void setStatus4(String status4) {
	     this.status4 = status4;
	   }
	   public String getStatus3() {
	     return this.status3;
	   }
	   public void setStatus3(String status3) {
	     this.status3 = status3;
	   }
	   public String getStatus2() {
	     return this.status2;
	   }
	   public void setStatus2(String status2) {
	     this.status2 = status2;
	   }
	   public String getStatus1() {
	     return this.status1;
	   }
	   public void setStatus1(String status1) {
	     this.status1 = status1;
	   }
	   public String getStatus() {
	     return this.status;
	   }
	   public void setStatus(String status) {
	     this.status = status;
	   }
	   public String getLbx_loan_from_id() {
	     return this.lbx_loan_from_id;
	   }
	   public void setLbx_loan_from_id(String lbxLoanFromId) {
	     this.lbx_loan_from_id = lbxLoanFromId;
	   }
	 
	   public String getLbx_loan_to_id() {
	     return this.lbx_loan_to_id;
	   }
	   public void setLbx_loan_to_id(String lbxLoanToId) {
	     this.lbx_loan_to_id = lbxLoanToId;
	   }
	   public String getSpecificLoanNo() {
	     return this.specificLoanNo;
	   }
	   public void setSpecificLoanNo(String specificLoanNo) {
	     this.specificLoanNo = specificLoanNo;
	   }
	   public String getReportName() {
	     return this.reportName;
	   }
	   public void setReportName(String reportName) {
	     this.reportName = reportName;
	   }
	 
	   public String getBranch()
	   {
	     return this.branch;
	   }
	   public void setBranch(String branch) {
	     this.branch = branch;
	   }
	   public String getLoanno() {
	     return this.loanno;
	   }
	   public void setLoanno(String loanno) {
	     this.loanno = loanno;
	   }
	   public String getFrom() {
	     return this.from;
	   }
	   public void setFrom(String from) {
	     this.from = from;
	   }
	   public String getTo() {
	     return this.to;
	   }
	   public void setTo(String to) {
	     this.to = to;
	   }
	   public String getFromDate() {
	     return this.fromDate;
	   }
	   public void setFromDate(String fromDate) {
	     this.fromDate = fromDate;
	   }
	   public String getToDate() {
	     return this.toDate;
	   }
	   public void setToDate(String toDate) {
	     this.toDate = toDate;
	   }
	   public String getAsonDate() {
	     return this.asonDate;
	   }
	   public void setAsonDate(String asonDate) {
	     this.asonDate = asonDate;
	   }
	   public String getBranchid() {
	     return this.branchid;
	   }
	   public void setBranchid(String branchid) {
	     this.branchid = branchid;
	   }
	   public String getLbxBranchId() {
	     return this.lbxBranchId;
	   }
	   public void setLbxBranchId(String lbxBranchId) {
	     this.lbxBranchId = lbxBranchId;
	   }
	   public String getBatchPurpose() {
	     return this.batchPurpose;
	   }
	   public void setBatchPurpose(String batchPurpose) {
	     this.batchPurpose = batchPurpose;
	   }
	   public String getExitLoad() {
	     return this.exitLoad;
	   }
	   public void setExitLoad(String exitLoad) {
	     this.exitLoad = exitLoad;
	   }
	   public String getForeClosureCharges() {
	     return this.foreClosureCharges;
	   }
	   public void setForeClosureCharges(String foreClosureCharges) {
	     this.foreClosureCharges = foreClosureCharges;
	   }
	   public String getReceiptDate() {
	     return this.receiptDate;
	   }
	   public void setReceiptDate(String receiptDate) {
	     this.receiptDate = receiptDate;
	   }
	   public String getLbxBatchNO() {
	     return this.lbxBatchNO;
	   }
	   public void setLbxBatchNO(String lbxBatchNO) {
	     this.lbxBatchNO = lbxBatchNO;
	   }
	   public String getLbxBranchID() {
	     return this.lbxBranchID;
	   }
	   public void setLbxBranchID(String lbxBranchID) {
	     this.lbxBranchID = lbxBranchID;
	   }
	   public String getProductCategory() {
	     return this.productCategory;
	   }
	   public void setProductCategory(String productCategory) {
	     this.productCategory = productCategory;
	   }
	   public String getInstrumentsMode() {
	     return this.instrumentsMode;
	   }
	   public void setInstrumentsMode(String instrumentsMode) {
	     this.instrumentsMode = instrumentsMode;
	   }
	   public String getBankName1() {
	     return this.bankName1;
	   }
	   public void setBankName1(String bankName1) {
	     this.bankName1 = bankName1;
	   }
	   public String getBankAccount() {
	     return this.bankAccount;
	   }
	   public void setBankAccount(String bankAccount) {
	     this.bankAccount = bankAccount;
	   }
	 
	 	public String getReportSubType() {
		return reportSubType;
	}
	public void setReportSubType(String reportSubType) {
		this.reportSubType = reportSubType;
	}
	public String getRtgsType() {
		return rtgsType;
	}
	public void setRtgsType(String rtgsType) {
		this.rtgsType = rtgsType;
	}
	public String getDrfId() {
		return drfId;
	}
	public void setDrfId(String drfId) {
		this.drfId = drfId;
	}
	public String getSuplier() {
		return suplier;
	}
	public void setSuplier(String suplier) {
		this.suplier = suplier;
	}
	public String getLbxSupplier() {
		return lbxSupplier;
	}
	public void setLbxSupplier(String lbxSupplier) {
		this.lbxSupplier = lbxSupplier;
	}
	public String getManufacturerDesc() {
		return manufacturerDesc;
	}
	public void setManufacturerDesc(String manufacturerDesc) {
		this.manufacturerDesc = manufacturerDesc;
	}
	public String getManufacturerId() {
		return manufacturerId;
	}
	public void setManufacturerId(String manufacturerId) {
		this.manufacturerId = manufacturerId;
	}
	public String getDrfCustomername() {
		return drfCustomername;
	}
	public void setDrfCustomername(String drfCustomername) {
		this.drfCustomername = drfCustomername;
	}
	public String getDrfDealNo() {
		return drfDealNo;
	}
	public void setDrfDealNo(String drfDealNo) {
		this.drfDealNo = drfDealNo;
	}
	public String getDrfDealId() {
		return drfDealId;
	}
	public void setDrfDealId(String drfDealId) {
		this.drfDealId = drfDealId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getProduct_desc() {
		return product_desc;
	}
	public void setProduct_desc(String product_desc) {
		this.product_desc = product_desc;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getOverdueReportLoanId() {
		return overdueReportLoanId;
	}
	public void setOverdueReportLoanId(String overdueReportLoanId) {
		this.overdueReportLoanId = overdueReportLoanId;
	}
	public String getEarliest() {
		return earliest;
	}
	public void setEarliest(String earliest) {
		this.earliest = earliest;
	}
	public String getEarly_loan_id() {
		return early_loan_id;
	}
	public void setEarly_loan_id(String early_loan_id) {
		this.early_loan_id = early_loan_id;
	}
	public String getRepaymentScheduleFromDate() {
		return repaymentScheduleFromDate;
	}
	public void setRepaymentScheduleFromDate(String repaymentScheduleFromDate) {
		this.repaymentScheduleFromDate = repaymentScheduleFromDate;
	}
	public String getRepaymentScheduleToDate() {
		return repaymentScheduleToDate;
	}
	public void setRepaymentScheduleToDate(String repaymentScheduleToDate) {
		this.repaymentScheduleToDate = repaymentScheduleToDate;
	}
	public String getRepaymentScheduleId() {
		return repaymentScheduleId;
	}
	public void setRepaymentScheduleId(String repaymentScheduleId) {
		this.repaymentScheduleId = repaymentScheduleId;
	}
	public String getRepaymentType() {
		return repaymentType;
	}
	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}
	public String getCirScheduleId() {
		return cirScheduleId;
	}
	public void setCirScheduleId(String cirScheduleId) {
		this.cirScheduleId = cirScheduleId;
	}
	public String getCirType() {
		return cirType;
	}
	public void setCirType(String cirType) {
		this.cirType = cirType;
	}
	public String getAsonDateInvoice() {
		return asonDateInvoice;
	}
	public void setAsonDateInvoice(String asonDateInvoice) {
		this.asonDateInvoice = asonDateInvoice;
	}
	public String getInvoiceLoanId() {
		return invoiceLoanId;
	}
	public void setInvoiceLoanId(String invoiceLoanId) {
		this.invoiceLoanId = invoiceLoanId;
	}
	public String getCirRemarks() {
		return cirRemarks;
	}
	public void setCirRemarks(String cirRemarks) {
		this.cirRemarks = cirRemarks;
	}
	public String getGrpType() {
		return grpType;
	}
	public void setGrpType(String grpType) {
		this.grpType = grpType;
	}
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getDealNoCov() {
		return dealNoCov;
	}
	public void setDealNoCov(String dealNoCov) {
		this.dealNoCov = dealNoCov;
	}
	public String getLbxDealNoHID() {
		return lbxDealNoHID;
	}
	public void setLbxDealNoHID(String lbxDealNoHID) {
		this.lbxDealNoHID = lbxDealNoHID;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getReportFormatValue() {
		return reportFormatValue;
	}
	public void setReportFormatValue(String reportFormatValue) {
		this.reportFormatValue = reportFormatValue;
	}
	public String getReportFormatDescription() {
		return reportFormatDescription;
	}
	public void setReportFormatDescription(String reportFormatDescription) {
		this.reportFormatDescription = reportFormatDescription;
	}
	public String getReportformat1() {
		return reportformat1;
	}
	public void setReportformat1(String reportformat1) {
		this.reportformat1 = reportformat1;
	}
	public String getCovDcsn() {
		return covDcsn;
	}
	public void setCovDcsn(String covDcsn) {
		this.covDcsn = covDcsn;
	}
	public String getLoanNoCov() {
		return loanNoCov;
	}
	public void setLoanNoCov(String loanNoCov) {
		this.loanNoCov = loanNoCov;
	}
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getLbxLoanNoHID() {
		return lbxLoanNoHID;
	}
	public void setLbxLoanNoHID(String lbxLoanNoHID) {
		this.lbxLoanNoHID = lbxLoanNoHID;
	}
	public String getLoanApprovalDate() {
		return loanApprovalDate;
	}
	public void setLoanApprovalDate(String loanApprovalDate) {
		this.loanApprovalDate = loanApprovalDate;
	}
	public String getPasswordPdf() {
		return passwordPdf;
	}
	public void setPasswordPdf(String passwordPdf) {
		this.passwordPdf = passwordPdf;
	}
	
	
}