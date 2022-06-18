package com.scz.vo;

public class PoolDownloadTempVO {
	private int loan_id;
	String loan_no;
	String loan_reference_no;
	String customer_name;
	String address;
	String asset_collateral_class;
	String asset_collateral_desc;
	String manufaturer_desc;
	String asset_new_old;
	String repayment_mode;
	String pdc;
	int loan_tenure;
	String installment_type;
	String appl_fin_doc;
	String gur_fin_doc;
	String  loan_sector_type;
	String  appl_address_proof;
	String  appl_id_proof;
	String  appl_signature_proof;
	String  gur_address_proof;
	String  gur_id_proof;
	String  gur_signature_proof;
	Double loan_loan_amount;
	String disbursal_date;
	String firstemidate;
	String last_emi;
	String first_emi_month;
	String last_emi_month;
	Double instl_amount;
	int seasoning;
	int remaining_tenure;
	Double loan_emi_amount;
	Double loan_eff_rate;
	String product_desc;
	String scheme_desc;
	String description;
	String customer_business_segment;
	String industry_desc;
	String sub_industry_desc;
	String loan_maturity_date;
	int loan_no_of_installment;
	int loan_advance_instl;
	Double loan_final_rate;
	String disbursal_status;
	String npa_flag;
	String loan_status;
	int loan_dpd;
	String loan_dpd_string;
	Double loanadvanceemiamount;
	Double loan_balance_principal;
	Double loan_overdue_principal;
	Double loan_received_principal;
	int loan_overdue_instl_num;
	Double loan_overdue_amount;
	int loan_balance_instl_no;
	Double loan_balance_instl_amount;
	String branch_desc;
	String advanceflg;
	Double sdamt;
	Double received_sdamt;
	Double undisbursed;
	Double interest_component;
	Double received_interest_amt;
	String user_id;
	public Double getLoan_loan_amount() {
		return loan_loan_amount;
	}
	public void setLoan_loan_amount(Double loanLoanAmount) {
		loan_loan_amount = loanLoanAmount;
	}
	public Double getInstl_amount() {
		return instl_amount;
	}
	public void setInstl_amount(Double instlAmount) {
		instl_amount = instlAmount;
	}
	public Double getLoan_emi_amount() {
		return loan_emi_amount;
	}
	public void setLoan_emi_amount(Double loanEmiAmount) {
		loan_emi_amount = loanEmiAmount;
	}
	public Double getLoan_eff_rate() {
		return loan_eff_rate;
	}
	public void setLoan_eff_rate(Double loanEffRate) {
		loan_eff_rate = loanEffRate;
	}
	public Double getLoan_final_rate() {
		return loan_final_rate;
	}
	public void setLoan_final_rate(Double loanFinalRate) {
		loan_final_rate = loanFinalRate;
	}
	public Double getLoanadvanceemiamount() {
		return loanadvanceemiamount;
	}
	public void setLoanadvanceemiamount(Double loanadvanceemiamount) {
		this.loanadvanceemiamount = loanadvanceemiamount;
	}
	public Double getLoan_balance_principal() {
		return loan_balance_principal;
	}
	public void setLoan_balance_principal(Double loanBalancePrincipal) {
		loan_balance_principal = loanBalancePrincipal;
	}
	public Double getLoan_overdue_principal() {
		return loan_overdue_principal;
	}
	public void setLoan_overdue_principal(Double loanOverduePrincipal) {
		loan_overdue_principal = loanOverduePrincipal;
	}
	public Double getLoan_received_principal() {
		return loan_received_principal;
	}
	public void setLoan_received_principal(Double loanReceivedPrincipal) {
		loan_received_principal = loanReceivedPrincipal;
	}
	public Double getLoan_overdue_amount() {
		return loan_overdue_amount;
	}
	public void setLoan_overdue_amount(Double loanOverdueAmount) {
		loan_overdue_amount = loanOverdueAmount;
	}
	public Double getLoan_balance_instl_amount() {
		return loan_balance_instl_amount;
	}
	public void setLoan_balance_instl_amount(Double loanBalanceInstlAmount) {
		loan_balance_instl_amount = loanBalanceInstlAmount;
	}
	public Double getSdamt() {
		return sdamt;
	}
	public void setSdamt(Double sdamt) {
		this.sdamt = sdamt;
	}
	public Double getReceived_sdamt() {
		return received_sdamt;
	}
	public void setReceived_sdamt(Double receivedSdamt) {
		received_sdamt = receivedSdamt;
	}
	public Double getUndisbursed() {
		return undisbursed;
	}
	public void setUndisbursed(Double undisbursed) {
		this.undisbursed = undisbursed;
	}
	public Double getInterest_component() {
		return interest_component;
	}
	public void setInterest_component(Double interestComponent) {
		interest_component = interestComponent;
	}
	public Double getReceived_interest_amt() {
		return received_interest_amt;
	}
	public void setReceived_interest_amt(Double receivedInterestAmt) {
		received_interest_amt = receivedInterestAmt;
	}
	public int getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(int loanId) {
		loan_id = loanId;
	}
	public String getLoan_no() {
		return loan_no;
	}
	public void setLoan_no(String loanNo) {
		loan_no = loanNo;
	}
	public String getLoan_reference_no() {
		return loan_reference_no;
	}
	public void setLoan_reference_no(String loanReferenceNo) {
		loan_reference_no = loanReferenceNo;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customerName) {
		customer_name = customerName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAsset_collateral_class() {
		return asset_collateral_class;
	}
	public void setAsset_collateral_class(String assetCollateralClass) {
		asset_collateral_class = assetCollateralClass;
	}
	public String getAsset_collateral_desc() {
		return asset_collateral_desc;
	}
	public void setAsset_collateral_desc(String assetCollateralDesc) {
		asset_collateral_desc = assetCollateralDesc;
	}
	public String getManufaturer_desc() {
		return manufaturer_desc;
	}
	public void setManufaturer_desc(String manufaturerDesc) {
		manufaturer_desc = manufaturerDesc;
	}
	public String getAsset_new_old() {
		return asset_new_old;
	}
	public void setAsset_new_old(String assetNewOld) {
		asset_new_old = assetNewOld;
	}
	public String getRepayment_mode() {
		return repayment_mode;
	}
	public void setRepayment_mode(String repaymentMode) {
		repayment_mode = repaymentMode;
	}
	public String getPdc() {
		return pdc;
	}
	public void setPdc(String pdc) {
		this.pdc = pdc;
	}
	public int getLoan_tenure() {
		return loan_tenure;
	}
	public void setLoan_tenure(int loanTenure) {
		loan_tenure = loanTenure;
	}
	public String getInstallment_type() {
		return installment_type;
	}
	public void setInstallment_type(String installmentType) {
		installment_type = installmentType;
	}
	public String getAppl_fin_doc() {
		return appl_fin_doc;
	}
	public void setAppl_fin_doc(String applFinDoc) {
		appl_fin_doc = applFinDoc;
	}
	public String getGur_fin_doc() {
		return gur_fin_doc;
	}
	public void setGur_fin_doc(String gurFinDoc) {
		gur_fin_doc = gurFinDoc;
	}
	public String getLoan_sector_type() {
		return loan_sector_type;
	}
	public void setLoan_sector_type(String loanSectorType) {
		loan_sector_type = loanSectorType;
	}
	public String getAppl_address_proof() {
		return appl_address_proof;
	}
	public void setAppl_address_proof(String applAddressProof) {
		appl_address_proof = applAddressProof;
	}
	public String getAppl_id_proof() {
		return appl_id_proof;
	}
	public void setAppl_id_proof(String applIdProof) {
		appl_id_proof = applIdProof;
	}
	public String getAppl_signature_proof() {
		return appl_signature_proof;
	}
	public void setAppl_signature_proof(String applSignatureProof) {
		appl_signature_proof = applSignatureProof;
	}
	public String getGur_address_proof() {
		return gur_address_proof;
	}
	public void setGur_address_proof(String gurAddressProof) {
		gur_address_proof = gurAddressProof;
	}
	public String getGur_id_proof() {
		return gur_id_proof;
	}
	public void setGur_id_proof(String gurIdProof) {
		gur_id_proof = gurIdProof;
	}
	public String getGur_signature_proof() {
		return gur_signature_proof;
	}
	public void setGur_signature_proof(String gurSignatureProof) {
		gur_signature_proof = gurSignatureProof;
	}
	public String getDisbursal_date() {
		return disbursal_date;
	}
	public void setDisbursal_date(String disbursalDate) {
		disbursal_date = disbursalDate;
	}
	public String getFirstemidate() {
		return firstemidate;
	}
	public void setFirstemidate(String firstemidate) {
		this.firstemidate = firstemidate;
	}
	public String getLast_emi() {
		return last_emi;
	}
	public void setLast_emi(String lastEmi) {
		last_emi = lastEmi;
	}
	public String getFirst_emi_month() {
		return first_emi_month;
	}
	public void setFirst_emi_month(String firstEmiMonth) {
		first_emi_month = firstEmiMonth;
	}
	public String getLast_emi_month() {
		return last_emi_month;
	}
	public void setLast_emi_month(String lastEmiMonth) {
		last_emi_month = lastEmiMonth;
	}
	public int getSeasoning() {
		return seasoning;
	}
	public void setSeasoning(int seasoning) {
		this.seasoning = seasoning;
	}
	public int getRemaining_tenure() {
		return remaining_tenure;
	}
	public void setRemaining_tenure(int remainingTenure) {
		remaining_tenure = remainingTenure;
	}
	public String getProduct_desc() {
		return product_desc;
	}
	public void setProduct_desc(String productDesc) {
		product_desc = productDesc;
	}
	public String getScheme_desc() {
		return scheme_desc;
	}
	public void setScheme_desc(String schemeDesc) {
		scheme_desc = schemeDesc;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCustomer_business_segment() {
		return customer_business_segment;
	}
	public void setCustomer_business_segment(String customerBusinessSegment) {
		customer_business_segment = customerBusinessSegment;
	}
	public String getIndustry_desc() {
		return industry_desc;
	}
	public void setIndustry_desc(String industryDesc) {
		industry_desc = industryDesc;
	}
	public String getSub_industry_desc() {
		return sub_industry_desc;
	}
	public void setSub_industry_desc(String subIndustryDesc) {
		sub_industry_desc = subIndustryDesc;
	}
	public String getLoan_maturity_date() {
		return loan_maturity_date;
	}
	public void setLoan_maturity_date(String loanMaturityDate) {
		loan_maturity_date = loanMaturityDate;
	}
	public int getLoan_no_of_installment() {
		return loan_no_of_installment;
	}
	public void setLoan_no_of_installment(int loanNoOfInstallment) {
		loan_no_of_installment = loanNoOfInstallment;
	}
	public int getLoan_advance_instl() {
		return loan_advance_instl;
	}
	public void setLoan_advance_instl(int loanAdvanceInstl) {
		loan_advance_instl = loanAdvanceInstl;
	}
	public String getDisbursal_status() {
		return disbursal_status;
	}
	public void setDisbursal_status(String disbursalStatus) {
		disbursal_status = disbursalStatus;
	}
	public String getNpa_flag() {
		return npa_flag;
	}
	public void setNpa_flag(String npaFlag) {
		npa_flag = npaFlag;
	}
	public String getLoan_status() {
		return loan_status;
	}
	public void setLoan_status(String loanStatus) {
		loan_status = loanStatus;
	}
	public int getLoan_dpd() {
		return loan_dpd;
	}
	public void setLoan_dpd(int loanDpd) {
		loan_dpd = loanDpd;
	}
	public String getLoan_dpd_string() {
		return loan_dpd_string;
	}
	public void setLoan_dpd_string(String loanDpdString) {
		loan_dpd_string = loanDpdString;
	}
	public int getLoan_overdue_instl_num() {
		return loan_overdue_instl_num;
	}
	public void setLoan_overdue_instl_num(int loanOverdueInstlNum) {
		loan_overdue_instl_num = loanOverdueInstlNum;
	}
	public int getLoan_balance_instl_no() {
		return loan_balance_instl_no;
	}
	public void setLoan_balance_instl_no(int loanBalanceInstlNo) {
		loan_balance_instl_no = loanBalanceInstlNo;
	}
	public String getBranch_desc() {
		return branch_desc;
	}
	public void setBranch_desc(String branchDesc) {
		branch_desc = branchDesc;
	}
	public String getAdvanceflg() {
		return advanceflg;
	}
	public void setAdvanceflg(String advanceflg) {
		this.advanceflg = advanceflg;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String userId) {
		user_id = userId;
	}
	
	
}
