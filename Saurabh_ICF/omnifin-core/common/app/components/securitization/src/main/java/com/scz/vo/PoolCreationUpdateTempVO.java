package com.scz.vo;

import java.math.BigDecimal;



public class PoolCreationUpdateTempVO {
	
	int id;
	int batch_id;
	int pool_id;
	String customer_id;
	int deal_no;
	int loan_id;
	String loan_no;
	String loan_ref_no;
	String loan_initiation_date;
	String case_book_date;
	String loan_expiry_date;
	String loan_status;
	String loan_disbursal_status;
	String buyback;
	String loan_type;
	String loan_scheme;
	String first_emi_date;
	String advance_emi_date;
	String first_emi_amount;
	String advance_emi_amount;
	String emi_pattern;
	String no_of_advance_emi;
	String classification;
	String standard_classification;
	String vehicle_category;
	String vehicle_model;
	String seasoning_without_advance;
	String seasoning_with_advance;
	String pos_cut_off_date;
	String future_flow_cut_off_date;
	BigDecimal finance_amount;
	String loan_repayment_frequency;
	String loan_tenure;
	String remaning_tenure;
	String new_used;
	String effective_irr;
	String processing_amount;
	String file_charges;
	String customer_name;
	String owner_name;
	String branch;
	String state;
	String asset_cost;
	String gross_ltv;
	String net_ltv;
	String dpd_last_month;
	String dpd_bucket;
	String rate_bucket1;
	String rate_bucket2;
	String seasoning_bucket_without_advance;
	String seasoning_bucket_with_advance;
	String customer_segment;
	String customer_category;
	String dealer_name;
	String manufacturer;
	String vehicle_no;
	String chassis_no;
	String engine_no;
	BigDecimal overdue_amount;
	BigDecimal overdue_prin_amt;
	BigDecimal overdue_int_amt;
	String total_age_of_vehicle_loan_sanction;
	String total_age_of_vehicle_loan_maturity;
	String total_future_cashflows;
	String age_of_borrower;
	String dob;
	String year_of_manufacture;
	String repayment_mode;
	String address;
	String city_customer;
	String district_customer;
	String state_customer;
	String pan_number;
	String id_proof;
	String address_proof;
	String co_applicant_name;
	String gurantor_name;
	String total_vehicles;
	String name_of_landholding;
	String weaker_section;
	String landholding_size;
	String agri_owner_name;
	String relation_with_applicant;
	String peak_dpd;
	String net_paid_amount_customer;
	String loan_inst_mode;
	String do_date;
	String do_number;
	String security_deposit;
	String npa_status;
	String rec_status;
	String user_id;
	String maker_date;
	String uploaded_flag;
	String reject_reason;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(int batchId) {
		batch_id = batchId;
	}
	public int getPool_id() {
		return pool_id;
	}
	public void setPool_id(int poolId) {
		pool_id = poolId;
	}
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customerId) {
		customer_id = customerId;
	}
	public int getDeal_no() {
		return deal_no;
	}
	public void setDeal_no(int dealNo) {
		deal_no = dealNo;
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
	public String getLoan_ref_no() {
		return loan_ref_no;
	}
	public void setLoan_ref_no(String loanRefNo) {
		loan_ref_no = loanRefNo;
	}
	public String getLoan_initiation_date() {
		return loan_initiation_date;
	}
	public void setLoan_initiation_date(String loanInitiationDate) {
		loan_initiation_date = loanInitiationDate;
	}
	public String getCase_book_date() {
		return case_book_date;
	}
	public void setCase_book_date(String caseBookDate) {
		case_book_date = caseBookDate;
	}
	public String getLoan_expiry_date() {
		return loan_expiry_date;
	}
	public void setLoan_expiry_date(String loanExpiryDate) {
		loan_expiry_date = loanExpiryDate;
	}
	public String getLoan_status() {
		return loan_status;
	}
	public void setLoan_status(String loanStatus) {
		loan_status = loanStatus;
	}
	public String getLoan_disbursal_status() {
		return loan_disbursal_status;
	}
	public void setLoan_disbursal_status(String loanDisbursalStatus) {
		loan_disbursal_status = loanDisbursalStatus;
	}
	public String getBuyback() {
		return buyback;
	}
	public void setBuyback(String buyback) {
		this.buyback = buyback;
	}
	public String getLoan_type() {
		return loan_type;
	}
	public void setLoan_type(String loanType) {
		loan_type = loanType;
	}
	public String getLoan_scheme() {
		return loan_scheme;
	}
	public void setLoan_scheme(String loanScheme) {
		loan_scheme = loanScheme;
	}
	public String getFirst_emi_date() {
		return first_emi_date;
	}
	public void setFirst_emi_date(String firstEmiDate) {
		first_emi_date = firstEmiDate;
	}
	public String getAdvance_emi_date() {
		return advance_emi_date;
	}
	public void setAdvance_emi_date(String advanceEmiDate) {
		advance_emi_date = advanceEmiDate;
	}
	public String getFirst_emi_amount() {
		return first_emi_amount;
	}
	public void setFirst_emi_amount(String firstEmiAmount) {
		first_emi_amount = firstEmiAmount;
	}
	public String getAdvance_emi_amount() {
		return advance_emi_amount;
	}
	public void setAdvance_emi_amount(String advanceEmiAmount) {
		advance_emi_amount = advanceEmiAmount;
	}
	public String getEmi_pattern() {
		return emi_pattern;
	}
	public void setEmi_pattern(String emiPattern) {
		emi_pattern = emiPattern;
	}
	public String getNo_of_advance_emi() {
		return no_of_advance_emi;
	}
	public void setNo_of_advance_emi(String noOfAdvanceEmi) {
		no_of_advance_emi = noOfAdvanceEmi;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getStandard_classification() {
		return standard_classification;
	}
	public void setStandard_classification(String standardClassification) {
		standard_classification = standardClassification;
	}
	public String getVehicle_category() {
		return vehicle_category;
	}
	public void setVehicle_category(String vehicleCategory) {
		vehicle_category = vehicleCategory;
	}
	public String getVehicle_model() {
		return vehicle_model;
	}
	public void setVehicle_model(String vehicleModel) {
		vehicle_model = vehicleModel;
	}
	public String getSeasoning_without_advance() {
		return seasoning_without_advance;
	}
	public void setSeasoning_without_advance(String seasoningWithoutAdvance) {
		seasoning_without_advance = seasoningWithoutAdvance;
	}
	public String getSeasoning_with_advance() {
		return seasoning_with_advance;
	}
	public void setSeasoning_with_advance(String seasoningWithAdvance) {
		seasoning_with_advance = seasoningWithAdvance;
	}
	public String getPos_cut_off_date() {
		return pos_cut_off_date;
	}
	public void setPos_cut_off_date(String posCutOffDate) {
		pos_cut_off_date = posCutOffDate;
	}
	public String getFuture_flow_cut_off_date() {
		return future_flow_cut_off_date;
	}
	public void setFuture_flow_cut_off_date(String futureFlowCutOffDate) {
		future_flow_cut_off_date = futureFlowCutOffDate;
	}
	public BigDecimal getFinance_amount() {
		return finance_amount;
	}
	public void setFinance_amount(BigDecimal financeAmount) {
		finance_amount = financeAmount;
	}
	public String getLoan_repayment_frequency() {
		return loan_repayment_frequency;
	}
	public void setLoan_repayment_frequency(String loanRepaymentFrequency) {
		loan_repayment_frequency = loanRepaymentFrequency;
	}
	public String getLoan_tenure() {
		return loan_tenure;
	}
	public void setLoan_tenure(String loanTenure) {
		loan_tenure = loanTenure;
	}
	public String getRemaning_tenure() {
		return remaning_tenure;
	}
	public void setRemaning_tenure(String remaningTenure) {
		remaning_tenure = remaningTenure;
	}
	public String getNew_used() {
		return new_used;
	}
	public void setNew_used(String newUsed) {
		new_used = newUsed;
	}
	public String getEffective_irr() {
		return effective_irr;
	}
	public void setEffective_irr(String effectiveIrr) {
		effective_irr = effectiveIrr;
	}
	public String getProcessing_amount() {
		return processing_amount;
	}
	public void setProcessing_amount(String processingAmount) {
		processing_amount = processingAmount;
	}
	public String getFile_charges() {
		return file_charges;
	}
	public void setFile_charges(String fileCharges) {
		file_charges = fileCharges;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customerName) {
		customer_name = customerName;
	}
	public String getOwner_name() {
		return owner_name;
	}
	public void setOwner_name(String ownerName) {
		owner_name = ownerName;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAsset_cost() {
		return asset_cost;
	}
	public void setAsset_cost(String assetCost) {
		asset_cost = assetCost;
	}
	public String getGross_ltv() {
		return gross_ltv;
	}
	public void setGross_ltv(String grossLtv) {
		gross_ltv = grossLtv;
	}
	public String getNet_ltv() {
		return net_ltv;
	}
	public void setNet_ltv(String netLtv) {
		net_ltv = netLtv;
	}
	public String getDpd_last_month() {
		return dpd_last_month;
	}
	public void setDpd_last_month(String dpdLastMonth) {
		dpd_last_month = dpdLastMonth;
	}
	public String getDpd_bucket() {
		return dpd_bucket;
	}
	public void setDpd_bucket(String dpdBucket) {
		dpd_bucket = dpdBucket;
	}
	public String getRate_bucket1() {
		return rate_bucket1;
	}
	public void setRate_bucket1(String rateBucket1) {
		rate_bucket1 = rateBucket1;
	}
	public String getRate_bucket2() {
		return rate_bucket2;
	}
	public void setRate_bucket2(String rateBucket2) {
		rate_bucket2 = rateBucket2;
	}
	public String getSeasoning_bucket_without_advance() {
		return seasoning_bucket_without_advance;
	}
	public void setSeasoning_bucket_without_advance(
			String seasoningBucketWithoutAdvance) {
		seasoning_bucket_without_advance = seasoningBucketWithoutAdvance;
	}
	public String getSeasoning_bucket_with_advance() {
		return seasoning_bucket_with_advance;
	}
	public void setSeasoning_bucket_with_advance(String seasoningBucketWithAdvance) {
		seasoning_bucket_with_advance = seasoningBucketWithAdvance;
	}
	public String getCustomer_segment() {
		return customer_segment;
	}
	public void setCustomer_segment(String customerSegment) {
		customer_segment = customerSegment;
	}
	public String getCustomer_category() {
		return customer_category;
	}
	public void setCustomer_category(String customerCategory) {
		customer_category = customerCategory;
	}
	public String getDealer_name() {
		return dealer_name;
	}
	public void setDealer_name(String dealerName) {
		dealer_name = dealerName;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getVehicle_no() {
		return vehicle_no;
	}
	public void setVehicle_no(String vehicleNo) {
		vehicle_no = vehicleNo;
	}
	public String getChassis_no() {
		return chassis_no;
	}
	public void setChassis_no(String chassisNo) {
		chassis_no = chassisNo;
	}
	public String getEngine_no() {
		return engine_no;
	}
	public void setEngine_no(String engineNo) {
		engine_no = engineNo;
	}
	public BigDecimal getOverdue_amount() {
		return overdue_amount;
	}
	public void setOverdue_amount(BigDecimal overdueAmount) {
		overdue_amount = overdueAmount;
	}
	public BigDecimal getOverdue_prin_amt() {
		return overdue_prin_amt;
	}
	public void setOverdue_prin_amt(BigDecimal overduePrinAmt) {
		overdue_prin_amt = overduePrinAmt;
	}
	public BigDecimal getOverdue_int_amt() {
		return overdue_int_amt;
	}
	public void setOverdue_int_amt(BigDecimal overdueIntAmt) {
		overdue_int_amt = overdueIntAmt;
	}
	public String getTotal_age_of_vehicle_loan_sanction() {
		return total_age_of_vehicle_loan_sanction;
	}
	public void setTotal_age_of_vehicle_loan_sanction(
			String totalAgeOfVehicleLoanSanction) {
		total_age_of_vehicle_loan_sanction = totalAgeOfVehicleLoanSanction;
	}
	public String getTotal_age_of_vehicle_loan_maturity() {
		return total_age_of_vehicle_loan_maturity;
	}
	public void setTotal_age_of_vehicle_loan_maturity(
			String totalAgeOfVehicleLoanMaturity) {
		total_age_of_vehicle_loan_maturity = totalAgeOfVehicleLoanMaturity;
	}
	public String getTotal_future_cashflows() {
		return total_future_cashflows;
	}
	public void setTotal_future_cashflows(String totalFutureCashflows) {
		total_future_cashflows = totalFutureCashflows;
	}
	public String getAge_of_borrower() {
		return age_of_borrower;
	}
	public void setAge_of_borrower(String ageOfBorrower) {
		age_of_borrower = ageOfBorrower;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getYear_of_manufacture() {
		return year_of_manufacture;
	}
	public void setYear_of_manufacture(String yearOfManufacture) {
		year_of_manufacture = yearOfManufacture;
	}
	public String getRepayment_mode() {
		return repayment_mode;
	}
	public void setRepayment_mode(String repaymentMode) {
		repayment_mode = repaymentMode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity_customer() {
		return city_customer;
	}
	public void setCity_customer(String cityCustomer) {
		city_customer = cityCustomer;
	}
	public String getDistrict_customer() {
		return district_customer;
	}
	public void setDistrict_customer(String districtCustomer) {
		district_customer = districtCustomer;
	}
	public String getState_customer() {
		return state_customer;
	}
	public void setState_customer(String stateCustomer) {
		state_customer = stateCustomer;
	}
	public String getPan_number() {
		return pan_number;
	}
	public void setPan_number(String panNumber) {
		pan_number = panNumber;
	}
	public String getId_proof() {
		return id_proof;
	}
	public void setId_proof(String idProof) {
		id_proof = idProof;
	}
	public String getAddress_proof() {
		return address_proof;
	}
	public void setAddress_proof(String addressProof) {
		address_proof = addressProof;
	}
	public String getCo_applicant_name() {
		return co_applicant_name;
	}
	public void setCo_applicant_name(String coApplicantName) {
		co_applicant_name = coApplicantName;
	}
	public String getGurantor_name() {
		return gurantor_name;
	}
	public void setGurantor_name(String gurantorName) {
		gurantor_name = gurantorName;
	}
	public String getTotal_vehicles() {
		return total_vehicles;
	}
	public void setTotal_vehicles(String totalVehicles) {
		total_vehicles = totalVehicles;
	}
	public String getName_of_landholding() {
		return name_of_landholding;
	}
	public void setName_of_landholding(String nameOfLandholding) {
		name_of_landholding = nameOfLandholding;
	}
	public String getWeaker_section() {
		return weaker_section;
	}
	public void setWeaker_section(String weakerSection) {
		weaker_section = weakerSection;
	}
	public String getLandholding_size() {
		return landholding_size;
	}
	public void setLandholding_size(String landholdingSize) {
		landholding_size = landholdingSize;
	}
	public String getAgri_owner_name() {
		return agri_owner_name;
	}
	public void setAgri_owner_name(String agriOwnerName) {
		agri_owner_name = agriOwnerName;
	}
	public String getRelation_with_applicant() {
		return relation_with_applicant;
	}
	public void setRelation_with_applicant(String relationWithApplicant) {
		relation_with_applicant = relationWithApplicant;
	}
	public String getPeak_dpd() {
		return peak_dpd;
	}
	public void setPeak_dpd(String peakDpd) {
		peak_dpd = peakDpd;
	}
	public String getNet_paid_amount_customer() {
		return net_paid_amount_customer;
	}
	public void setNet_paid_amount_customer(String netPaidAmountCustomer) {
		net_paid_amount_customer = netPaidAmountCustomer;
	}
	public String getLoan_inst_mode() {
		return loan_inst_mode;
	}
	public void setLoan_inst_mode(String loanInstMode) {
		loan_inst_mode = loanInstMode;
	}
	public String getDo_date() {
		return do_date;
	}
	public void setDo_date(String doDate) {
		do_date = doDate;
	}
	public String getDo_number() {
		return do_number;
	}
	public void setDo_number(String doNumber) {
		do_number = doNumber;
	}
	public String getSecurity_deposit() {
		return security_deposit;
	}
	public void setSecurity_deposit(String securityDeposit) {
		security_deposit = securityDeposit;
	}
	public String getNpa_status() {
		return npa_status;
	}
	public void setNpa_status(String npaStatus) {
		npa_status = npaStatus;
	}
	public String getRec_status() {
		return rec_status;
	}
	public void setRec_status(String recStatus) {
		rec_status = recStatus;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String userId) {
		user_id = userId;
	}
	public String getMaker_date() {
		return maker_date;
	}
	public void setMaker_date(String makerDate) {
		maker_date = makerDate;
	}
	public String getUploaded_flag() {
		return uploaded_flag;
	}
	public void setUploaded_flag(String uploadedFlag) {
		uploaded_flag = uploadedFlag;
	}
	public String getReject_reason() {
		return reject_reason;
	}
	public void setReject_reason(String rejectReason) {
		reject_reason = rejectReason;
	}
	
	
	
}
