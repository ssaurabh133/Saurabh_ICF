package com.cm.vo;

public class LoanDetailForCMVO {
	

	private String dealNo;
	private String date;
	private String sanctionedLoanAmount;
	private String productType;
	private String product;
	private String scheme;
	private String utilizeLoanAmount;
	private String sanctionedValidtill;
	private String loanId;
	private String dealPromo;
	private String assetCost;
	private String margin;
	private String marginAmount;
	private String loanAmount;
	private String tenureMonths;
	private String rateType;
	private String baseRateType;
	private String baseRate;
	private String markUp;
	private String repaymentType;
	private String installmentType;
	private String dealInstallmentMode;
	private String effectiveRate;
	private String repaymentMode;
	private String typeOfDisbursal;
	private String noOfDisbursal;
	private String dealRateMethod;
	private String frequency;
	private String agreementDate;
	private String repayEffectiveDate;
	private String installments;
	private String dueDay;
	private String loanBranch;
	private String loanIndustry;
	private String loanSubIndustry;
	private String loanCustomerId;
	private String loanCustomerType;
	private String loanCustomerExisting;
	private String loanApprovalDate;
	private String userId;
	private String bussinessDate;
	
	private String showProduct;
	private String showScheme;
	
   private String loanMaturityDate;
	private String loanAgreementDate;
	private String repaymentEffectiveDate;
	private String loanNoofInstall;
	private String loanDayDue;
	private String countProduct;
	
	private String countScheme;
	private String loanNo;
	private String loanInitDate;
	private String loanDisbDate;
	private String productCat;
	private String lbxDealNo;
	private String sanctionedDate;
	private String showFrequency;
	private String showEffectiveRate;
	private String showRepayment;
	private String showInstallment;
	private String showInstMode;
	private String showRepayMode;
	private String maturityDate;
	private String showSectorType;
	private String sectorType;
	private String authorRemarks;
	private String customerName;
	private String remarks;
	private String podoFlag;
	private String loanDealId;
	private String dealIrr1;
	private String dealIrr2;
	private String dealEffectiveRate;
	private String sanctionDate;
	private String insuranceDoneBy;
	private String dealFlatRate;
	private String oneDealOneLoan;
	private String nextDueDate;
	private String prevDueDate;
	private String reschId;
	private String ltvPerc;
	
	private String recStatus;
	private String minFlatRate;
	private String maxFlatRate;
	private String minEffRate;
	private String maxEffRate;
	private String minTenure;
	private String maxTenure;
	private String dateChange;
	private String loanPurpose;
	private String lbxLoanPurpose;
	private String leadPartnerFlag;
	private String interestFrequency;
	private String interestCalculationMethod;
	private String interestCompoundingFrequency;
	private String rateTypeDesc;
	private String interestDueDate;
	private String requestedLoamt;
	private String facilityDetailsParameterFlag;
	private String isOneDealOneLoanProduct;
	private String proposedAmount;
	private String grossBlock;
	private String netBlock;
	private String sblMasterLimit;
	private String gblMasterLimit;
	private String custCurrentPos;
	private String groupPos;
	
	
	
	// Code added for handling Separate Interest type | Rahul papneja |30012018
	private String firstInterestDueDate;
	//private String maturityDate;
	//Ends Here
	// Code added for Edit Due Date| Rahul papneja| 29012018
	private String editDueDate;
	
		//start:added by indrajeet
		private String lbxLoanNoHID;
		private String disbursalNo;
		private String sblGblMasterLimit;
		private String sblGblCapturedLimit;
		private String diff;
		private String totalRecordSize;
		private String authorId;
		private String authorDate;
		private String decision;
		private String comments;
		private String disbursalId;
		private String dealId;
	//end:added by indrajeet
	
	
	public String getEditDueDate() {
		return editDueDate;
	}
	public void setEditDueDate(String editDueDate) {
		this.editDueDate = editDueDate;
	}
	// Ends Here| Rahul papneja
	// Code added for handling Separate Interest type | Rahul papneja |30012018
	
	public String getFirstInterestDueDate(){
		
		return firstInterestDueDate;
	}
	
	public void setFirstInterestDueDate(String firstInterestDueDate){
		
		this.firstInterestDueDate= firstInterestDueDate;
	}
	
	
/*	public String getMaturitydate()
	{
		return maturityDate;
	}
	
	public void setMaturitydate(String maturityDate)
	{
		this.maturityDate=maturityDate;
	}*/
	
	// Ends Here| Rahul papneja
	
	public String getRateTypeDesc() {
		return rateTypeDesc;
	}
	public void setRateTypeDesc(String rateTypeDesc) {
		this.rateTypeDesc = rateTypeDesc;
	}
	
	
	public String getLeadPartnerFlag() {
		return leadPartnerFlag;
	}
	public void setLeadPartnerFlag(String leadPartnerFlag) {
		this.leadPartnerFlag = leadPartnerFlag;
	}
	
	
	public String getLoanPurpose() {
		return loanPurpose;
	}
	public String getLbxLoanPurpose() {
		return lbxLoanPurpose;
	}
	public void setLbxLoanPurpose(String lbxLoanPurpose) {
		this.lbxLoanPurpose = lbxLoanPurpose;
	}
	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}
	//KANIKA CHANGES
	private String lbxareaCodeVal;
	private String assetFlag;
	
	//Surendra
	private String interestCalc;
	private String showInterestCalc;
	private String netLtv;
	private String tenureInDay;
	private String daysBasis;
	private String fixPriod;   
	private String editRemarks;   
	private String repayEffDate;
	private String noOfAsset;
	private String formNo;
	private String lbxLoanClassification;
	private String loanClassification;
	private String paymentModeId;
	private String paymentMode;
	private String makerId;
	private String makerDate;
	private String insurancePremium;	
	private String agriDocs;
	private String agriLand;
	private String nameAgriDoc;
	
	private String businessType;
	private String businessdesc;
	private String businessId;
	private String partnerName;
	private String lbxpartnerId;
	private String partnerRate;
	private String partnerAmount;
	private String partnerPercentage;
	private String id;
	private String chk;
	private String servicingPartnerFlag;
	private String totalVatAmt; //Richa HP VAT TAX
	private String vatPercent;// add by saorabh
	private String serviceTax;// add by saorabh
	private String	serviceAmount;// add by saorabh
	private String creditPeriod;//added by Richa
	private String servicingBranch;
    private String lbxBranchId;
	private String vehicleType;
    private String manufactId;
    private String modelDescId;
    private String branchAmt;
    private String nationalAmt;
	private String hoAmt;
	private String gridBranchAmt;
	private String gridNationalAmt;
	private String gridHOAmt;
	
	public String getPartnerPercentage() {
		return partnerPercentage;
	}
	public void setPartnerPercentage(String partnerPercentage) {
		this.partnerPercentage = partnerPercentage;
	}
	public String getServicingPartnerFlag() {
		return servicingPartnerFlag;
	}
	public void setServicingPartnerFlag(String servicingPartnerFlag) {
		this.servicingPartnerFlag = servicingPartnerFlag;
	}
	
	
	
	
	public String getLbxpartnerId() {
		return lbxpartnerId;
	}




	private String relationWithHirer;
	public String getAgriDocs() {
		return agriDocs;
	}
	public void setAgriDocs(String agriDocs) {
		this.agriDocs = agriDocs;
	}
	public String getAgriLand() {
		return agriLand;
	}
	public void setAgriLand(String agriLand) {
		this.agriLand = agriLand;
	}
	public String getNameAgriDoc() {
		return nameAgriDoc;
	}
	public void setNameAgriDoc(String nameAgriDoc) {
		this.nameAgriDoc = nameAgriDoc;
	}
	public String getRelationWithHirer() {
		return relationWithHirer;
	}
	public void setRelationWithHirer(String relationWithHirer) {
		this.relationWithHirer = relationWithHirer;
	}
	
	public String getLbxLoanClassification() {
		return lbxLoanClassification;
	}
	public void setLbxLoanClassification(String lbxLoanClassification) {
		this.lbxLoanClassification = lbxLoanClassification;
	}
	public String getLoanClassification() {
		return loanClassification;
	}
	public void setLoanClassification(String loanClassification) {
		this.loanClassification = loanClassification;
	}
	public String getFormNo() {
		return formNo;
	}
	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}
	public String getNoOfAsset() {
		return noOfAsset;
	}
	public void setNoOfAsset(String noOfAsset) {
		this.noOfAsset = noOfAsset;
	}
	public String getRepayEffDate() {
		return repayEffDate;
	}
	public void setRepayEffDate(String repayEffDate) {
		this.repayEffDate = repayEffDate;
	}
	public String getEditRemarks() {
		return editRemarks;
	}
	public void setEditRemarks(String editRemarks) {
		this.editRemarks = editRemarks;
	}
	public String getDaysBasis() {
		return daysBasis;
	}
	public void setDaysBasis(String daysBasis) {
		this.daysBasis = daysBasis;
	}
	public String getTenureInDay() {
		return tenureInDay;
	}
	public void setTenureInDay(String tenureInDay) {
		this.tenureInDay = tenureInDay;
	}
	public String getNetLtv() {
		return netLtv;
	}
	public void setNetLtv(String netLtv) {
		this.netLtv = netLtv;
	}
		public String getInterestCalc() {
		return interestCalc;
	}
	public void setInterestCalc(String interestCalc) {
		this.interestCalc = interestCalc;
	}

	public String getShowInterestCalc() {
		return showInterestCalc;
	}
	public void setShowInterestCalc(String showInterestCalc) {
		this.showInterestCalc = showInterestCalc;
	}
		public String getDateChange() {
		return dateChange;
	}
	public void setDateChange(String dateChange) {
		this.dateChange = dateChange;
	}
		public String getLbxareaCodeVal() {
			return lbxareaCodeVal;
		}
		public void setLbxareaCodeVal(String lbxareaCodeVal) {
			this.lbxareaCodeVal = lbxareaCodeVal;
		}
		private String areaCodename;
		
		public String getAreaCodename() {
			return areaCodename;
		}
		public void setAreaCodename(String areaCodename) {
			this.areaCodename = areaCodename;
		}
		//KANIKA CHNAGES END
	
	
	
	public String getMinFlatRate() {
		return minFlatRate;
	}
	public void setMinFlatRate(String minFlatRate) {
		this.minFlatRate = minFlatRate;
	}
	public String getMaxFlatRate() {
		return maxFlatRate;
	}
	public void setMaxFlatRate(String maxFlatRate) {
		this.maxFlatRate = maxFlatRate;
	}
	public String getMinEffRate() {
		return minEffRate;
	}
	public void setMinEffRate(String minEffRate) {
		this.minEffRate = minEffRate;
	}
	public String getMaxEffRate() {
		return maxEffRate;
	}
	public void setMaxEffRate(String maxEffRate) {
		this.maxEffRate = maxEffRate;
	}
	public String getMinTenure() {
		return minTenure;
	}
	public void setMinTenure(String minTenure) {
		this.minTenure = minTenure;
	}
	public String getMaxTenure() {
		return maxTenure;
	}
	public void setMaxTenure(String maxTenure) {
		this.maxTenure = maxTenure;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public String getNextDueDate() {
		return nextDueDate;
	}
	public void setNextDueDate(String nextDueDate) {
		this.nextDueDate = nextDueDate;
	}
	public String getOneDealOneLoan() {
		return oneDealOneLoan;
	}
	public void setOneDealOneLoan(String oneDealOneLoan) {
		this.oneDealOneLoan = oneDealOneLoan;
	}
	public String getDealFlatRate() {
		return dealFlatRate;
	}
	public void setDealFlatRate(String dealFlatRate) {
		this.dealFlatRate = dealFlatRate;
	}
	public String getInsuranceDoneBy() {
		return insuranceDoneBy;
	}
	public void setInsuranceDoneBy(String insuranceDoneBy) {
		this.insuranceDoneBy = insuranceDoneBy;
	}
	public String getSanctionDate() {
		return sanctionDate;
	}
	public void setSanctionDate(String sanctionDate) {
		this.sanctionDate = sanctionDate;
	}
	public String getDealEffectiveRate() {
		return dealEffectiveRate;
	}
	public void setDealEffectiveRate(String dealEffectiveRate) {
		this.dealEffectiveRate = dealEffectiveRate;
	}
	public String getDealIrr1() {
		return dealIrr1;
	}
	public void setDealIrr1(String dealIrr1) {
		this.dealIrr1 = dealIrr1;
	}
	public String getDealIrr2() {
		return dealIrr2;
	}
	public void setDealIrr2(String dealIrr2) {
		this.dealIrr2 = dealIrr2;
	}
	public String getLoanDealId() {
		return loanDealId;
	}
	public void setLoanDealId(String loanDealId) {
		this.loanDealId = loanDealId;
	}
	public String getAuthorRemarks() {
		return authorRemarks;
	}
	public void setAuthorRemarks(String authorRemarks) {
		this.authorRemarks = authorRemarks;
	}
	public String getPodoFlag() {
		return podoFlag;
	}
	public void setPodoFlag(String podoFlag) {
		this.podoFlag = podoFlag;
	}
	public String getShowSectorType() {
		return showSectorType;
	}
	public void setShowSectorType(String showSectorType) {
		this.showSectorType = showSectorType;
	}
	public String getSectorType() {
		return sectorType;
	}
	public void setSectorType(String sectorType) {
		this.sectorType = sectorType;
	}
	public String getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}
	public String getShowRepayMode() {
		return showRepayMode;
	}
	public void setShowRepayMode(String showRepayMode) {
		this.showRepayMode = showRepayMode;
	}
	public String getShowInstMode() {
		return showInstMode;
	}
	public void setShowInstMode(String showInstMode) {
		this.showInstMode = showInstMode;
	}
	public String getShowInstallment() {
		return showInstallment;
	}
	public void setShowInstallment(String showInstallment) {
		this.showInstallment = showInstallment;
	}
	public String getShowRepayment() {
		return showRepayment;
	}
	public void setShowRepayment(String showRepayment) {
		this.showRepayment = showRepayment;
	}
	public String getShowEffectiveRate() {
		return showEffectiveRate;
	}
	public void setShowEffectiveRate(String showEffectiveRate) {
		this.showEffectiveRate = showEffectiveRate;
	}
	public String getShowFrequency() {
		return showFrequency;
	}
	public void setShowFrequency(String showFrequency) {
		this.showFrequency = showFrequency;
	}
	public String getSanctionedDate() {
		return sanctionedDate;
	}
	public void setSanctionedDate(String sanctionedDate) {
		this.sanctionedDate = sanctionedDate;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getLbxDealNo() {
		return lbxDealNo;
	}
	public void setLbxDealNo(String lbxDealNo) {
		this.lbxDealNo = lbxDealNo;
	}
	public String getProductCat() {
		return productCat;
	}
	public void setProductCat(String productCat) {
		this.productCat = productCat;
	}
	public String getCountScheme() {
		return countScheme;
	}
	public void setCountScheme(String countScheme) {
		this.countScheme = countScheme;
	}
	public String getCountProduct() {
		return countProduct;
	}
	public void setCountProduct(String countProduct) {
		this.countProduct = countProduct;
	}
	public String getLoanMaturityDate() {
		return loanMaturityDate;
	}
	public void setLoanMaturityDate(String loanMaturityDate) {
		this.loanMaturityDate = loanMaturityDate;
	}
	public String getLoanAgreementDate() {
		return loanAgreementDate;
	}
	public void setLoanAgreementDate(String loanAgreementDate) {
		this.loanAgreementDate = loanAgreementDate;
	}
	public String getRepaymentEffectiveDate() {
		return repaymentEffectiveDate;
	}
	public void setRepaymentEffectiveDate(String repaymentEffectiveDate) {
		this.repaymentEffectiveDate = repaymentEffectiveDate;
	}
	public String getLoanNoofInstall() {
		return loanNoofInstall;
	}
	public void setLoanNoofInstall(String loanNoofInstall) {
		this.loanNoofInstall = loanNoofInstall;
	}
	public String getLoanDayDue() {
		return loanDayDue;
	}
	public void setLoanDayDue(String loanDayDue) {
		this.loanDayDue = loanDayDue;
	}
	public String getLoanDisbDate() {
		return loanDisbDate;
	}
	public void setLoanDisbDate(String loanDisbDate) {
		this.loanDisbDate = loanDisbDate;
	}
	public String getLoanInitDate() {
		return loanInitDate;
	}
	public void setLoanInitDate(String loanInitDate) {
		this.loanInitDate = loanInitDate;
	}
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}

	public String getShowProduct() {
		return showProduct;
	}
	public void setShowProduct(String showProduct) {
		this.showProduct = showProduct;
	}
	public String getShowScheme() {
		return showScheme;
	}
	public void setShowScheme(String showScheme) {
		this.showScheme = showScheme;
	}
	public String getLoanCustomerType() {
		return loanCustomerType;
	}
	public void setLoanCustomerType(String loanCustomerType) {
		this.loanCustomerType = loanCustomerType;
	}
	public String getDealNo() {
		return dealNo;
	}
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSanctionedLoanAmount() {
		return sanctionedLoanAmount;
	}
	public void setSanctionedLoanAmount(String sanctionedLoanAmount) {
		this.sanctionedLoanAmount = sanctionedLoanAmount;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getUtilizeLoanAmount() {
		return utilizeLoanAmount;
	}
	public void setUtilizeLoanAmount(String utilizeLoanAmount) {
		this.utilizeLoanAmount = utilizeLoanAmount;
	}
	public String getSanctionedValidtill() {
		return sanctionedValidtill;
	}
	public void setSanctionedValidtill(String sanctionedValidtill) {
		this.sanctionedValidtill = sanctionedValidtill;
	}
		
	public String getAssetCost() {
		return assetCost;
	}
	public void setAssetCost(String assetCost) {
		this.assetCost = assetCost;
	}
	public String getMargin() {
		return margin;
	}
	public void setMargin(String margin) {
		this.margin = margin;
	}
	public String getMarginAmount() {
		return marginAmount;
	}
	public void setMarginAmount(String marginAmount) {
		this.marginAmount = marginAmount;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getTenureMonths() {
		return tenureMonths;
	}
	public void setTenureMonths(String tenureMonths) {
		this.tenureMonths = tenureMonths;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getBaseRateType() {
		return baseRateType;
	}
	public void setBaseRateType(String baseRateType) {
		this.baseRateType = baseRateType;
	}
	public String getBaseRate() {
		return baseRate;
	}
	public void setBaseRate(String baseRate) {
		this.baseRate = baseRate;
	}
	public String getMarkUp() {
		return markUp;
	}
	public void setMarkUp(String markUp) {
		this.markUp = markUp;
	}
	public String getRepaymentType() {
		return repaymentType;
	}
	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}
	public String getInstallmentType() {
		return installmentType;
	}
	public void setInstallmentType(String installmentType) {
		this.installmentType = installmentType;
	}
	public String getDealInstallmentMode() {
		return dealInstallmentMode;
	}
	public void setDealInstallmentMode(String dealInstallmentMode) {
		this.dealInstallmentMode = dealInstallmentMode;
	}
	public String getEffectiveRate() {
		return effectiveRate;
	}
	public void setEffectiveRate(String effectiveRate) {
		this.effectiveRate = effectiveRate;
	}
	public String getRepaymentMode() {
		return repaymentMode;
	}
	public void setRepaymentMode(String repaymentMode) {
		this.repaymentMode = repaymentMode;
	}
	public String getTypeOfDisbursal() {
		return typeOfDisbursal;
	}
	public void setTypeOfDisbursal(String typeOfDisbursal) {
		this.typeOfDisbursal = typeOfDisbursal;
	}
	public String getNoOfDisbursal() {
		return noOfDisbursal;
	}
	public void setNoOfDisbursal(String noOfDisbursal) {
		this.noOfDisbursal = noOfDisbursal;
	}
	public String getDealRateMethod() {
		return dealRateMethod;
	}
	public void setDealRateMethod(String dealRateMethod) {
		this.dealRateMethod = dealRateMethod;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getAgreementDate() {
		return agreementDate;
	}
	public void setAgreementDate(String agreementDate) {
		this.agreementDate = agreementDate;
	}
	public String getRepayEffectiveDate() {
		return repayEffectiveDate;
	}
	public void setRepayEffectiveDate(String repayEffectiveDate) {
		this.repayEffectiveDate = repayEffectiveDate;
	}
	public String getInstallments() {
		return installments;
	}
	public void setInstallments(String installments) {
		this.installments = installments;
	}
	public String getDueDay() {
		return dueDay;
	}
	public void setDueDay(String dueDay) {
		this.dueDay = dueDay;
	}
	public String getLoanBranch() {
		return loanBranch;
	}
	public void setLoanBranch(String loanBranch) {
		this.loanBranch = loanBranch;
	}
	public String getLoanIndustry() {
		return loanIndustry;
	}
	public void setLoanIndustry(String loanIndustry) {
		this.loanIndustry = loanIndustry;
	}
	public String getLoanSubIndustry() {
		return loanSubIndustry;
	}
	public void setLoanSubIndustry(String loanSubIndustry) {
		this.loanSubIndustry = loanSubIndustry;
	}
	public String getLoanCustomerId() {
		return loanCustomerId;
	}
	public void setLoanCustomerId(String loanCustomerId) {
		this.loanCustomerId = loanCustomerId;
	}
	public String getLoanCustomerExisting() {
		return loanCustomerExisting;
	}
	public void setLoanCustomerExisting(String loanCustomerExisting) {
		this.loanCustomerExisting = loanCustomerExisting;
	}
	public String getLoanApprovalDate() {
		return loanApprovalDate;
	}
	public void setLoanApprovalDate(String loanApprovalDate) {
		this.loanApprovalDate = loanApprovalDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBussinessDate() {
		return bussinessDate;
	}
	public void setBussinessDate(String bussinessDate) {
		this.bussinessDate = bussinessDate;
	}
	public String getDealPromo() {
		return dealPromo;
	}
	public void setDealPromo(String dealPromo) {
		this.dealPromo = dealPromo;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setPrevDueDate(String prevDueDate) {
		this.prevDueDate = prevDueDate;
	}
	public String getPrevDueDate() {
		return prevDueDate;
	}
	public void setReschId(String reschId) {
		this.reschId = reschId;
	}
	public String getReschId() {
		return reschId;
	}
	public void setLtvPerc(String ltvPerc) {
		this.ltvPerc = ltvPerc;
	}
	public String getLtvPerc() {
		return ltvPerc;
	}
	public void setAssetFlag(String assetFlag) {
		this.assetFlag = assetFlag;
	}
	public String getAssetFlag() {
		return assetFlag;
	}
	public void setFixPriod(String fixPriod) {
		this.fixPriod = fixPriod;
	}
	public String getFixPriod() {
		return fixPriod;
	}
	
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getPaymentModeId() {
		return paymentModeId;
	}
	public void setPaymentModeId(String paymentModeId) {
		this.paymentModeId = paymentModeId;
	}
		public String getInsurancePremium() {
		return insurancePremium;
	}
	public void setInsurancePremium(String insurancePremium) {
		this.insurancePremium = insurancePremium;
	}
	public String getRequestedLoamt() {
		return requestedLoamt;
	}
	public void setRequestedLoamt(String requestedLoamt) {
		this.requestedLoamt = requestedLoamt;
	}
	public String getFacilityDetailsParameterFlag() {
		return facilityDetailsParameterFlag;
	}
	public void setFacilityDetailsParameterFlag(String facilityDetailsParameterFlag) {
		this.facilityDetailsParameterFlag = facilityDetailsParameterFlag;
	}
	public String getIsOneDealOneLoanProduct() {
		return isOneDealOneLoanProduct;
	}
	public void setIsOneDealOneLoanProduct(String isOneDealOneLoanProduct) {
		this.isOneDealOneLoanProduct = isOneDealOneLoanProduct;
	}
	public String getProposedAmount() {
		return proposedAmount;
	}
	public void setProposedAmount(String proposedAmount) {
		this.proposedAmount = proposedAmount;
	}
	
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessdesc() {
		return businessdesc;
	}

	public void setBusinessdesc(String businessdesc) {
		this.businessdesc = businessdesc;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getChk() {
		return chk;
	}
	public void setChk(String chk) {
		this.chk = chk;
	}
//Richa HP VAT TAX starts
	public String getTotalVatAmt() {
		return totalVatAmt;
	}
	public void setTotalVatAmt(String totalVatAmt) {
		this.totalVatAmt = totalVatAmt;
	}
//Richa HP VAT TAX ends
	public String getVatPercent() {
		return vatPercent;
	}
	public void setVatPercent(String vatPercent) {
		this.vatPercent = vatPercent;
	}
	public String getServiceTax() {
		return serviceTax;
	}
	public void setServiceTax(String serviceTax) {
		this.serviceTax = serviceTax;
	}
	public String getServiceAmount() {
		return serviceAmount;
	}
	public void setServiceAmount(String serviceAmount) {
		this.serviceAmount = serviceAmount;
	}
	public String getCreditPeriod() {
		return creditPeriod;
	}
	public void setCreditPeriod(String creditPeriod) {
		this.creditPeriod = creditPeriod;
	}
	public void setLbxpartnerId(String lbxpartnerId) {
		this.lbxpartnerId = lbxpartnerId;
	}
	public String getPartnerRate() {
		return partnerRate;
	}
	public void setPartnerRate(String partnerRate) {
		this.partnerRate = partnerRate;
	}
	public String getPartnerAmount() {
		return partnerAmount;
	}
	public void setPartnerAmount(String partnerAmount) {
		this.partnerAmount = partnerAmount;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
		public String getServicingBranch() {
		return servicingBranch;
	}
	public void setServicingBranch(String servicingBranch) {
		this.servicingBranch = servicingBranch;
	}
	public String getLbxBranchId() {
		return lbxBranchId;
	}
	public void setLbxBranchId(String lbxBranchId) {
		this.lbxBranchId = lbxBranchId;
	}

	
	public String getInterestFrequency() {
		return interestFrequency;
	}
	public void setInterestFrequency(String interestFrequency) {
		this.interestFrequency = interestFrequency;
	}
	public String getInterestCalculationMethod() {
		return interestCalculationMethod;
	}
	public void setInterestCalculationMethod(String interestCalculationMethod) {
		this.interestCalculationMethod = interestCalculationMethod;
	}
	public String getInterestCompoundingFrequency() {
		return interestCompoundingFrequency;
	}
	public void setInterestCompoundingFrequency(String interestCompoundingFrequency) {
		this.interestCompoundingFrequency = interestCompoundingFrequency;
	}
	public String getInterestDueDate() {
		return interestDueDate;
	}
	public void setInterestDueDate(String interestDueDate) {
		this.interestDueDate = interestDueDate;
	}

	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getManufactId() {
		return manufactId;
	}
	public void setManufactId(String manufactId) {
		this.manufactId = manufactId;
	}
	public String getModelDescId() {
		return modelDescId;
	}
	public void setModelDescId(String modelDescId) {
		this.modelDescId = modelDescId;
	}
	public String getBranchAmt() {
		return branchAmt;
	}
	public void setBranchAmt(String branchAmt) {
		this.branchAmt = branchAmt;
	}
	public String getNationalAmt() {
		return nationalAmt;
	}
	public void setNationalAmt(String nationalAmt) {
		this.nationalAmt = nationalAmt;
	}
	public String getHoAmt() {
		return hoAmt;
	}
	public void setHoAmt(String hoAmt) {
		this.hoAmt = hoAmt;
	}
	public String getGridBranchAmt() {
		return gridBranchAmt;
	}
	public void setGridBranchAmt(String gridBranchAmt) {
		this.gridBranchAmt = gridBranchAmt;
	}
	public String getGridNationalAmt() {
		return gridNationalAmt;
	}
	public void setGridNationalAmt(String gridNationalAmt) {
		this.gridNationalAmt = gridNationalAmt;
	}
	public String getGridHOAmt() {
		return gridHOAmt;
	}
	public void setGridHOAmt(String gridHOAmt) {
		this.gridHOAmt = gridHOAmt;
	}

	public String getGrossBlock() {
		return grossBlock;
	}
	public void setGrossBlock(String grossBlock) {
		this.grossBlock = grossBlock;
	}
	public String getNetBlock() {
		return netBlock;
	}
	public void setNetBlock(String netBlock) {
		this.netBlock = netBlock;
	}

	
		public String getLbxLoanNoHID() {
		return lbxLoanNoHID;
	}
	public void setLbxLoanNoHID(String lbxLoanNoHID) {
		this.lbxLoanNoHID = lbxLoanNoHID;
	}
	public String getDisbursalNo() {
		return disbursalNo;
	}
	public void setDisbursalNo(String disbursalNo) {
		this.disbursalNo = disbursalNo;
	}
	public String getSblGblMasterLimit() {
		return sblGblMasterLimit;
	}
	public void setSblGblMasterLimit(String sblGblMasterLimit) {
		this.sblGblMasterLimit = sblGblMasterLimit;
	}
	public String getSblGblCapturedLimit() {
		return sblGblCapturedLimit;
	}
	public void setSblGblCapturedLimit(String sblGblCapturedLimit) {
		this.sblGblCapturedLimit = sblGblCapturedLimit;
	}
	public String getDiff() {
		return diff;
	}
	public void setDiff(String diff) {
		this.diff = diff;
	}
	public String getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(String totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
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
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getAuthorDate() {
		return authorDate;
	}
	public void setAuthorDate(String authorDate) {
		this.authorDate = authorDate;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getDisbursalId() {
		return disbursalId;
	}
	public void setDisbursalId(String disbursalId) {
		this.disbursalId = disbursalId;
	}
	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
//Pooja code for SBL/GBL
	public String getSblMasterLimit() {
		return sblMasterLimit;
	}
	public void setSblMasterLimit(String sblMasterLimit) {
		this.sblMasterLimit = sblMasterLimit;
	}
	public String getGblMasterLimit() {
		return gblMasterLimit;
	}
	public void setGblMasterLimit(String gblMasterLimit) {
		this.gblMasterLimit = gblMasterLimit;
	}
	public String getCustCurrentPos() {
		return custCurrentPos;
	}
	public void setCustCurrentPos(String custCurrentPos) {
		this.custCurrentPos = custCurrentPos;
	}
	public String getGroupPos() {
		return groupPos;
	}
	public void setGroupPos(String groupPos) {
		this.groupPos = groupPos;
	}
	
	
}
