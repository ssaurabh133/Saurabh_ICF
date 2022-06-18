package com.cm.vo;

import org.apache.struts.upload.FormFile;

/**
 * @author software
 *
 */
public class PoolCreationForCMVO {
	
	private String cutOffDate;
	 private String loanAccountNumber;
	 private String customerName;
	 private String customerCategory;
	 private String lbxBranchId;
	 private String branchid;
	 public String getCutOffDate() {
		return cutOffDate;
	}

	public void setCutOffDate(String cutOffDate) {
		this.cutOffDate = cutOffDate;
	}

	private String customerSegment;
	 private String constitution;
	 private String assetCategory;
	 private String industry;
	 private String subIndustry;
	 private String product;
	 private String scheme;
	 private String disbursalDate1;
	 private String disbursalDate2;
	 private String maturityDate2;
	 private String maturityDate1;

	 private String loanAmount1;
	 private String loanAmount2;
	 private String installmentAmount1;
	 private String installmentAmount2;
	
	
	 private String interestRate1;
	 private String interestRate2;
	 private String rateType;
	 private String amountOutstanding1;
	 
	 private String installmentReceived1;
	 private String installmentReceived2;
	 private String overDueAmount1;
	 private String overDueAmount2;
	 private String DPD1;
	 private String DPD2;
	
	 private String lbxValue;
	 private String lbxAssetCollId;
	 private String lbxIndustry;
	 private String lbxSubIndustry;
	 private String poolID;
	 private String lbxPoolID;
	 private String lbxScheme;
	 private String lbxProductID;
 
	 private String amountOutstanding2;
	 private String totalInstallments1;
	 private String totalInstallments2	;
	 private String makerID	;
	 private String businessDate	;
		 
 
	 private String  LOAN_ID;
	 private String  LOAN_PRODUCT;
	 private String  LOAN_SCHEME;
	 private String  LOAN_CUSTOMER_ID;
	 private String  LOAN_CUSTOMER_TYPE;
	 private String LOAN_AMOUNT;
	 private String LOAN_CUSTOMER_CATEGORY;
	 private String LOAN_CUSTOMER_CONSTITUTION;
	 private String LOAN_ASSET_COST;
	 private String LOAN_CUSTOMER_BUSINESS_SEGMENT;
	 private String LOAN_INDUSTRY;
	 private String LOAN_SUB_INDUSTRY;
	 private String LOAN_DPD_STRING;
	 private String LOAN_DISBURSAL_DATE;
	 private String LOAN_MATURITY_DATE;
	 private String LOAN_DPD;
	 private String LOAN_TENURE;
	 private String LOAN_BALANCE_TENURE;
	 private String LOAN_INSTL_NUM;
	 private String LOAN_ADV_EMI_NUM;
	 private String LOAN_INT_RATE;
	 private String LOAN_NPA_FLAG;
	 private String LOAN_DISBURSAL_STATUS;
	 private String LOAN_ADV_EMI_AMOUNT;
	 private String LOAN_EMI;
	 private String LOAN_BALANCE_PRINCIPAL;
	 private String LOAN_OVERDUE_PRINCIPAL;
	 private String LOAN_RECEIVED_PRINCIPAL;
	 private String LOAN_OVERDUE_INSTL_NUM;
	 private String LOAN_OVERDUE_AMOUNT;
	 private String  LOAN_BALANCE_INSTL_NUM;
	 private String  REC_STATUS;
	 private String  LOAN_BALANCE_INSTL_AMOUNT;
	 private String fileName;
	 private String docPath;
	 private FormFile docFile;	
	 private String checkId;
	 private String legacyLoan;
	 private String branchDesc;
	 private String lbxBranchIds;
	 private String stateid;
	 private String txtStateCode;
	 private String instMode;
	 
	 public String getBranchid() {
		return branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}

	public String getLegacyLoan() {
		return legacyLoan;
	}

	public void setLegacyLoan(String legacyLoan) {
		this.legacyLoan = legacyLoan;
	}

	public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getDocPath() {
			return docPath;
		}

		public void setDocPath(String docPath) {
			this.docPath = docPath;
		}
	
	
	public FormFile getDocFile() {
			return docFile;
		}

		public void setDocFile(FormFile docFile) {
			this.docFile = docFile;
		}

	public String getLbxValue() {
			return lbxValue;
		}
		 
	public String getLOAN_ID() {
		return LOAN_ID;
	}
	public void setLOAN_ID(String lOANID) {
		LOAN_ID = lOANID;
	}
	public String getLOAN_PRODUCT() {
		return LOAN_PRODUCT;
	}
	public void setLOAN_PRODUCT(String lOANPRODUCT) {
		LOAN_PRODUCT = lOANPRODUCT;
	}
	public String getLOAN_SCHEME() {
		return LOAN_SCHEME;
	}
	public void setLOAN_SCHEME(String lOANSCHEME) {
		LOAN_SCHEME = lOANSCHEME;
	}
	public String getLOAN_CUSTOMER_ID() {
		return LOAN_CUSTOMER_ID;
	}
	public void setLOAN_CUSTOMER_ID(String lOANCUSTOMERID) {
		LOAN_CUSTOMER_ID = lOANCUSTOMERID;
	}
	public String getLOAN_CUSTOMER_TYPE() {
		return LOAN_CUSTOMER_TYPE;
	}
	public void setLOAN_CUSTOMER_TYPE(String lOANCUSTOMERTYPE) {
		LOAN_CUSTOMER_TYPE = lOANCUSTOMERTYPE;
	}
	public String getLOAN_AMOUNT() {
		return LOAN_AMOUNT;
	}
	public void setLOAN_AMOUNT(String lOANAMOUNT) {
		LOAN_AMOUNT = lOANAMOUNT;
	}
	public String getLOAN_CUSTOMER_CATEGORY() {
		return LOAN_CUSTOMER_CATEGORY;
	}
	public void setLOAN_CUSTOMER_CATEGORY(String lOANCUSTOMERCATEGORY) {
		LOAN_CUSTOMER_CATEGORY = lOANCUSTOMERCATEGORY;
	}
	public String getLOAN_CUSTOMER_CONSTITUTION() {
		return LOAN_CUSTOMER_CONSTITUTION;
	}
	public void setLOAN_CUSTOMER_CONSTITUTION(String lOANCUSTOMERCONSTITUTION) {
		LOAN_CUSTOMER_CONSTITUTION = lOANCUSTOMERCONSTITUTION;
	}
	public String getLOAN_ASSET_COST() {
		return LOAN_ASSET_COST;
	}
	public void setLOAN_ASSET_COST(String lOANASSETCOST) {
		LOAN_ASSET_COST = lOANASSETCOST;
	}
	public String getLOAN_CUSTOMER_BUSINESS_SEGMENT() {
		return LOAN_CUSTOMER_BUSINESS_SEGMENT;
	}
	public void setLOAN_CUSTOMER_BUSINESS_SEGMENT(String lOANCUSTOMERBUSINESSSEGMENT) {
		LOAN_CUSTOMER_BUSINESS_SEGMENT = lOANCUSTOMERBUSINESSSEGMENT;
	}
	public String getLOAN_INDUSTRY() {
		return LOAN_INDUSTRY;
	}
	public void setLOAN_INDUSTRY(String lOANINDUSTRY) {
		LOAN_INDUSTRY = lOANINDUSTRY;
	}
	public String getLOAN_SUB_INDUSTRY() {
		return LOAN_SUB_INDUSTRY;
	}
	public void setLOAN_SUB_INDUSTRY(String lOANSUBINDUSTRY) {
		LOAN_SUB_INDUSTRY = lOANSUBINDUSTRY;
	}
	public String getLOAN_DPD_STRING() {
		return LOAN_DPD_STRING;
	}
	public void setLOAN_DPD_STRING(String lOANDPDSTRING) {
		LOAN_DPD_STRING = lOANDPDSTRING;
	}
	public String getLOAN_DISBURSAL_DATE() {
		return LOAN_DISBURSAL_DATE;
	}
	public void setLOAN_DISBURSAL_DATE(String lOANDISBURSALDATE) {
		LOAN_DISBURSAL_DATE = lOANDISBURSALDATE;
	}
	public String getLOAN_MATURITY_DATE() {
		return LOAN_MATURITY_DATE;
	}
	public void setLOAN_MATURITY_DATE(String lOANMATURITYDATE) {
		LOAN_MATURITY_DATE = lOANMATURITYDATE;
	}
	public String getLOAN_DPD() {
		return LOAN_DPD;
	}
	public void setLOAN_DPD(String lOANDPD) {
		LOAN_DPD = lOANDPD;
	}
	public String getLOAN_TENURE() {
		return LOAN_TENURE;
	}
	public void setLOAN_TENURE(String lOANTENURE) {
		LOAN_TENURE = lOANTENURE;
	}
	public String getLOAN_BALANCE_TENURE() {
		return LOAN_BALANCE_TENURE;
	}
	public void setLOAN_BALANCE_TENURE(String lOANBALANCETENURE) {
		LOAN_BALANCE_TENURE = lOANBALANCETENURE;
	}
	public String getLOAN_INSTL_NUM() {
		return LOAN_INSTL_NUM;
	}
	public void setLOAN_INSTL_NUM(String lOANINSTLNUM) {
		LOAN_INSTL_NUM = lOANINSTLNUM;
	}
	public String getLOAN_ADV_EMI_NUM() {
		return LOAN_ADV_EMI_NUM;
	}
	public void setLOAN_ADV_EMI_NUM(String lOANADVEMINUM) {
		LOAN_ADV_EMI_NUM = lOANADVEMINUM;
	}
	public String getLOAN_INT_RATE() {
		return LOAN_INT_RATE;
	}
	public void setLOAN_INT_RATE(String lOANINTRATE) {
		LOAN_INT_RATE = lOANINTRATE;
	}
	public String getLOAN_NPA_FLAG() {
		return LOAN_NPA_FLAG;
	}
	public void setLOAN_NPA_FLAG(String lOANNPAFLAG) {
		LOAN_NPA_FLAG = lOANNPAFLAG;
	}
	public String getLOAN_DISBURSAL_STATUS() {
		return LOAN_DISBURSAL_STATUS;
	}
	public void setLOAN_DISBURSAL_STATUS(String lOANDISBURSALSTATUS) {
		LOAN_DISBURSAL_STATUS = lOANDISBURSALSTATUS;
	}
	public String getLOAN_ADV_EMI_AMOUNT() {
		return LOAN_ADV_EMI_AMOUNT;
	}
	public void setLOAN_ADV_EMI_AMOUNT(String lOANADVEMIAMOUNT) {
		LOAN_ADV_EMI_AMOUNT = lOANADVEMIAMOUNT;
	}
	public String getLOAN_EMI() {
		return LOAN_EMI;
	}
	public void setLOAN_EMI(String lOANEMI) {
		LOAN_EMI = lOANEMI;
	}
	public String getLOAN_BALANCE_PRINCIPAL() {
		return LOAN_BALANCE_PRINCIPAL;
	}
	public void setLOAN_BALANCE_PRINCIPAL(String lOANBALANCEPRINCIPAL) {
		LOAN_BALANCE_PRINCIPAL = lOANBALANCEPRINCIPAL;
	}
	public String getLOAN_OVERDUE_PRINCIPAL() {
		return LOAN_OVERDUE_PRINCIPAL;
	}
	public void setLOAN_OVERDUE_PRINCIPAL(String lOANOVERDUEPRINCIPAL) {
		LOAN_OVERDUE_PRINCIPAL = lOANOVERDUEPRINCIPAL;
	}
	public String getLOAN_RECEIVED_PRINCIPAL() {
		return LOAN_RECEIVED_PRINCIPAL;
	}
	public void setLOAN_RECEIVED_PRINCIPAL(String lOANRECEIVEDPRINCIPAL) {
		LOAN_RECEIVED_PRINCIPAL = lOANRECEIVEDPRINCIPAL;
	}
	public String getLOAN_OVERDUE_INSTL_NUM() {
		return LOAN_OVERDUE_INSTL_NUM;
	}
	public void setLOAN_OVERDUE_INSTL_NUM(String lOANOVERDUEINSTLNUM) {
		LOAN_OVERDUE_INSTL_NUM = lOANOVERDUEINSTLNUM;
	}
	public String getLOAN_OVERDUE_AMOUNT() {
		return LOAN_OVERDUE_AMOUNT;
	}
	public void setLOAN_OVERDUE_AMOUNT(String lOANOVERDUEAMOUNT) {
		LOAN_OVERDUE_AMOUNT = lOANOVERDUEAMOUNT;
	}
	public String getLOAN_BALANCE_INSTL_NUM() {
		return LOAN_BALANCE_INSTL_NUM;
	}
	public void setLOAN_BALANCE_INSTL_NUM(String lOANBALANCEINSTLNUM) {
		LOAN_BALANCE_INSTL_NUM = lOANBALANCEINSTLNUM;
	}
	public String getREC_STATUS() {
		return REC_STATUS;
	}
	public void setREC_STATUS(String rECSTATUS) {
		REC_STATUS = rECSTATUS;
	}
	public String getLOAN_BALANCE_INSTL_AMOUNT() {
		return LOAN_BALANCE_INSTL_AMOUNT;
	}
	public void setLOAN_BALANCE_INSTL_AMOUNT(String lOANBALANCEINSTLAMOUNT) {
		LOAN_BALANCE_INSTL_AMOUNT = lOANBALANCEINSTLAMOUNT;
	}
		public void setLbxValue(String lbxValue) {
			this.lbxValue = lbxValue;
		}
		public String getLbxAssetCollId() {
			return lbxAssetCollId;
		}
		public void setLbxAssetCollId(String lbxAssetCollId) {
			this.lbxAssetCollId = lbxAssetCollId;
		}
		public String getLbxIndustry() {
			return lbxIndustry;
		}
		public void setLbxIndustry(String lbxIndustry) {
			this.lbxIndustry = lbxIndustry;
		}
		public String getLbxSubIndustry() {
			return lbxSubIndustry;
		}
		public void setLbxSubIndustry(String lbxSubIndustry) {
			this.lbxSubIndustry = lbxSubIndustry;
		}
		public String getPoolID() {
			return poolID;
		}
		public void setPoolID(String poolID) {
			this.poolID = poolID;
		}
		public String getLbxPoolID() {
			return lbxPoolID;
		}
		public void setLbxPoolID(String lbxPoolID) {
			this.lbxPoolID = lbxPoolID;
		}
		public String getLbxScheme() {
			return lbxScheme;
		}
		public void setLbxScheme(String lbxScheme) {
			this.lbxScheme = lbxScheme;
		}
		public String getLbxProductID() {
			return lbxProductID;
		}
		public void setLbxProductID(String lbxProductID) {
			this.lbxProductID = lbxProductID;
		}
	public String getMakerID() {
		return makerID;
	}
	public void setMakerID(String makerID) {
		this.makerID = makerID;
	}
	
	 
	 public String getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}
	public String getLoanAccountNumber() {
		return loanAccountNumber;
	}
	public void setLoanAccountNumber(String loanAccountNumber) {
		this.loanAccountNumber = loanAccountNumber;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerCategory() {
		return customerCategory;
	}
	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
	}
	public String getCustomerSegment() {
		return customerSegment;
	}
	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}
	public String getConstitution() {
		return constitution;
	}
	public void setConstitution(String constitution) {
		this.constitution = constitution;
	}
	public String getAssetCategory() {
		return assetCategory;
	}
	public void setAssetCategory(String assetCategory) {
		this.assetCategory = assetCategory;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getSubIndustry() {
		return subIndustry;
	}
	public void setSubIndustry(String subIndustry) {
		this.subIndustry = subIndustry;
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
	public String getDisbursalDate1() {
		return disbursalDate1;
	}
	public void setDisbursalDate1(String disbursalDate1) {
		this.disbursalDate1 = disbursalDate1;
	}
	public String getDisbursalDate2() {
		return disbursalDate2;
	}
	public void setDisbursalDate2(String disbursalDate2) {
		this.disbursalDate2 = disbursalDate2;
	}
	public String getMaturityDate2() {
		return maturityDate2;
	}
	public void setMaturityDate2(String maturityDate2) {
		this.maturityDate2 = maturityDate2;
	}
	public String getMaturityDate1() {
		return maturityDate1;
	}
	public void setMaturityDate1(String maturityDate1) {
		this.maturityDate1 = maturityDate1;
	}
	public String getLoanAmount1() {
		return loanAmount1;
	}
	public void setLoanAmount1(String loanAmount1) {
		this.loanAmount1 = loanAmount1;
	}
	public String getLoanAmount2() {
		return loanAmount2;
	}
	public void setLoanAmount2(String loanAmount2) {
		this.loanAmount2 = loanAmount2;
	}
	public String getInstallmentAmount1() {
		return installmentAmount1;
	}
	public void setInstallmentAmount1(String installmentAmount1) {
		this.installmentAmount1 = installmentAmount1;
	}
	public String getInstallmentAmount2() {
		return installmentAmount2;
	}
	public void setInstallmentAmount2(String installmentAmount2) {
		this.installmentAmount2 = installmentAmount2;
	}
	public String getInterestRate1() {
		return interestRate1;
	}
	public void setInterestRate1(String interestRate1) {
		this.interestRate1 = interestRate1;
	}
	public String getInterestRate2() {
		return interestRate2;
	}
	public void setInterestRate2(String interestRate2) {
		this.interestRate2 = interestRate2;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getAmountOutstanding1() {
		return amountOutstanding1;
	}
	public void setAmountOutstanding1(String amountOutstanding1) {
		this.amountOutstanding1 = amountOutstanding1;
	}
	public String getAmountOutstanding2() {
		return amountOutstanding2;
	}
	public void setAmountOutstanding2(String amountOutstanding2) {
		this.amountOutstanding2 = amountOutstanding2;
	}
	public String getTotalInstallments1() {
		return totalInstallments1;
	}
	public void setTotalInstallments1(String totalInstallments1) {
		this.totalInstallments1 = totalInstallments1;
	}
	public String getTotalInstallments2() {
		return totalInstallments2;
	}
	public void setTotalInstallments2(String totalInstallments2) {
		this.totalInstallments2 = totalInstallments2;
	}
	public String getInstallmentReceived1() {
		return installmentReceived1;
	}
	public void setInstallmentReceived1(String installmentReceived1) {
		this.installmentReceived1 = installmentReceived1;
	}
	public String getInstallmentReceived2() {
		return installmentReceived2;
	}
	public void setInstallmentReceived2(String installmentReceived2) {
		this.installmentReceived2 = installmentReceived2;
	}
	public String getOverDueAmount1() {
		return overDueAmount1;
	}
	public void setOverDueAmount1(String overDueAmount1) {
		this.overDueAmount1 = overDueAmount1;
	}
	public String getOverDueAmount2() {
		return overDueAmount2;
	}
	public void setOverDueAmount2(String overDueAmount2) {
		this.overDueAmount2 = overDueAmount2;
	}
	public String getDPD1() {
		return DPD1;
	}
	public void setDPD1(String dPD1) {
		DPD1 = dPD1;
	}
	public String getDPD2() {
		return DPD2;
	}
	public void setDPD2(String dPD2) {
		DPD2 = dPD2;
	}

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	

	public String getTxtStateCode() {
		return txtStateCode;
	}

	public void setTxtStateCode(String txtStateCode) {
		this.txtStateCode = txtStateCode;
	}

	public String getStateid() {
		return stateid;
	}

	public void setStateid(String stateid) {
		this.stateid = stateid;
	}

	public String getInstMode() {
		return instMode;
	}

	public void setInstMode(String instMode) {
		this.instMode = instMode;
	}

	public String getBranchDesc() {
		return branchDesc;
	}

	public void setBranchDesc(String branchDesc) {
		this.branchDesc = branchDesc;
	}

	public String getLbxBranchIds() {
		return lbxBranchIds;
	}

	public void setLbxBranchIds(String lbxBranchIds) {
		this.lbxBranchIds = lbxBranchIds;
	}

	public String getLbxBranchId() {
		return lbxBranchId;
	}

	public void setLbxBranchId(String lbxBranchId) {
		this.lbxBranchId = lbxBranchId;
	}
	
	
	
	
	
	
    
}
