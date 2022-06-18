package com.scz.vo;

import java.math.BigDecimal;

public class CrSecuritizationPoolDtlTempVO
{
  int id;
  int batch_id;
  int pool_id;
  String customer_id;
  String deal_no;
  int loan_id;
  String loan_no;
  String loan_ref_no;
  String loan_initiation_date;
  String case_book_date;
  String loan_expiry_date;
  String loan_status;
  String loan_disbursal_status;
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
  BigDecimal total_future_cashflows;
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
  String buyback;
  String buyback_date;

  public int getId()
  {
    return this.id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public int getBatch_id() {
    return this.batch_id;
  }

  public BigDecimal getTotal_future_cashflows() {
    return this.total_future_cashflows;
  }
  public void setTotal_future_cashflows(BigDecimal totalFutureCashflows) {
    this.total_future_cashflows = totalFutureCashflows;
  }
  public void setBatch_id(int batchId) {
    this.batch_id = batchId;
  }
  public int getPool_id() {
    return this.pool_id;
  }
  public void setPool_id(int poolId) {
    this.pool_id = poolId;
  }
  public String getCustomer_id() {
    return this.customer_id;
  }
  public void setCustomer_id(String customerId) {
    this.customer_id = customerId;
  }
  public String getDeal_no() {
    return this.deal_no;
  }
  public void setDeal_no(String dealNo) {
    this.deal_no = dealNo;
  }
  public int getLoan_id() {
    return this.loan_id;
  }
  public void setLoan_id(int loanId) {
    this.loan_id = loanId;
  }
  public String getLoan_no() {
    return this.loan_no;
  }
  public void setLoan_no(String loanNo) {
    this.loan_no = loanNo;
  }
  public String getLoan_ref_no() {
    return this.loan_ref_no;
  }
  public void setLoan_ref_no(String loanRefNo) {
    this.loan_ref_no = loanRefNo;
  }
  public String getLoan_initiation_date() {
    return this.loan_initiation_date;
  }
  public void setLoan_initiation_date(String loanInitiationDate) {
    this.loan_initiation_date = loanInitiationDate;
  }
  public String getCase_book_date() {
    return this.case_book_date;
  }
  public void setCase_book_date(String caseBookDate) {
    this.case_book_date = caseBookDate;
  }
  public String getLoan_expiry_date() {
    return this.loan_expiry_date;
  }
  public void setLoan_expiry_date(String loanExpiryDate) {
    this.loan_expiry_date = loanExpiryDate;
  }
  public String getLoan_status() {
    return this.loan_status;
  }
  public void setLoan_status(String loanStatus) {
    this.loan_status = loanStatus;
  }
  public String getLoan_disbursal_status() {
    return this.loan_disbursal_status;
  }
  public void setLoan_disbursal_status(String loanDisbursalStatus) {
    this.loan_disbursal_status = loanDisbursalStatus;
  }
  public String getLoan_type() {
    return this.loan_type;
  }
  public void setLoan_type(String loanType) {
    this.loan_type = loanType;
  }
  public String getLoan_scheme() {
    return this.loan_scheme;
  }
  public void setLoan_scheme(String loanScheme) {
    this.loan_scheme = loanScheme;
  }
  public String getFirst_emi_date() {
    return this.first_emi_date;
  }
  public void setFirst_emi_date(String firstEmiDate) {
    this.first_emi_date = firstEmiDate;
  }
  public String getAdvance_emi_date() {
    return this.advance_emi_date;
  }
  public void setAdvance_emi_date(String advanceEmiDate) {
    this.advance_emi_date = advanceEmiDate;
  }
  public String getFirst_emi_amount() {
    return this.first_emi_amount;
  }
  public void setFirst_emi_amount(String firstEmiAmount) {
    this.first_emi_amount = firstEmiAmount;
  }
  public String getAdvance_emi_amount() {
    return this.advance_emi_amount;
  }
  public void setAdvance_emi_amount(String advanceEmiAmount) {
    this.advance_emi_amount = advanceEmiAmount;
  }
  public String getEmi_pattern() {
    return this.emi_pattern;
  }
  public void setEmi_pattern(String emiPattern) {
    this.emi_pattern = emiPattern;
  }
  public String getNo_of_advance_emi() {
    return this.no_of_advance_emi;
  }
  public void setNo_of_advance_emi(String noOfAdvanceEmi) {
    this.no_of_advance_emi = noOfAdvanceEmi;
  }
  public String getClassification() {
    return this.classification;
  }
  public void setClassification(String classification) {
    this.classification = classification;
  }
  public String getStandard_classification() {
    return this.standard_classification;
  }
  public void setStandard_classification(String standardClassification) {
    this.standard_classification = standardClassification;
  }
  public String getVehicle_category() {
    return this.vehicle_category;
  }
  public void setVehicle_category(String vehicleCategory) {
    this.vehicle_category = vehicleCategory;
  }
  public String getVehicle_model() {
    return this.vehicle_model;
  }
  public void setVehicle_model(String vehicleModel) {
    this.vehicle_model = vehicleModel;
  }
  public String getSeasoning_without_advance() {
    return this.seasoning_without_advance;
  }
  public void setSeasoning_without_advance(String seasoningWithoutAdvance) {
    this.seasoning_without_advance = seasoningWithoutAdvance;
  }
  public String getSeasoning_with_advance() {
    return this.seasoning_with_advance;
  }
  public void setSeasoning_with_advance(String seasoningWithAdvance) {
    this.seasoning_with_advance = seasoningWithAdvance;
  }
  public String getPos_cut_off_date() {
    return this.pos_cut_off_date;
  }
  public void setPos_cut_off_date(String posCutOffDate) {
    this.pos_cut_off_date = posCutOffDate;
  }
  public String getFuture_flow_cut_off_date() {
    return this.future_flow_cut_off_date;
  }
  public void setFuture_flow_cut_off_date(String futureFlowCutOffDate) {
    this.future_flow_cut_off_date = futureFlowCutOffDate;
  }
  public BigDecimal getFinance_amount() {
    return this.finance_amount;
  }
  public void setFinance_amount(BigDecimal financeAmount) {
    this.finance_amount = financeAmount;
  }
  public String getLoan_repayment_frequency() {
    return this.loan_repayment_frequency;
  }
  public void setLoan_repayment_frequency(String loanRepaymentFrequency) {
    this.loan_repayment_frequency = loanRepaymentFrequency;
  }
  public String getLoan_tenure() {
    return this.loan_tenure;
  }
  public void setLoan_tenure(String loanTenure) {
    this.loan_tenure = loanTenure;
  }
  public String getRemaning_tenure() {
    return this.remaning_tenure;
  }
  public void setRemaning_tenure(String remaningTenure) {
    this.remaning_tenure = remaningTenure;
  }
  public String getNew_used() {
    return this.new_used;
  }
  public void setNew_used(String newUsed) {
    this.new_used = newUsed;
  }
  public String getEffective_irr() {
    return this.effective_irr;
  }
  public void setEffective_irr(String effectiveIrr) {
    this.effective_irr = effectiveIrr;
  }
  public String getProcessing_amount() {
    return this.processing_amount;
  }
  public void setProcessing_amount(String processingAmount) {
    this.processing_amount = processingAmount;
  }
  public String getFile_charges() {
    return this.file_charges;
  }
  public void setFile_charges(String fileCharges) {
    this.file_charges = fileCharges;
  }
  public String getCustomer_name() {
    return this.customer_name;
  }
  public void setCustomer_name(String customerName) {
    this.customer_name = customerName;
  }
  public String getOwner_name() {
    return this.owner_name;
  }
  public void setOwner_name(String ownerName) {
    this.owner_name = ownerName;
  }
  public String getBranch() {
    return this.branch;
  }
  public void setBranch(String branch) {
    this.branch = branch;
  }
  public String getState() {
    return this.state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public String getAsset_cost() {
    return this.asset_cost;
  }
  public void setAsset_cost(String assetCost) {
    this.asset_cost = assetCost;
  }
  public String getGross_ltv() {
    return this.gross_ltv;
  }
  public void setGross_ltv(String grossLtv) {
    this.gross_ltv = grossLtv;
  }
  public String getNet_ltv() {
    return this.net_ltv;
  }
  public void setNet_ltv(String netLtv) {
    this.net_ltv = netLtv;
  }
  public String getDpd_last_month() {
    return this.dpd_last_month;
  }
  public void setDpd_last_month(String dpdLastMonth) {
    this.dpd_last_month = dpdLastMonth;
  }
  public String getDpd_bucket() {
    return this.dpd_bucket;
  }
  public void setDpd_bucket(String dpdBucket) {
    this.dpd_bucket = dpdBucket;
  }
  public String getRate_bucket1() {
    return this.rate_bucket1;
  }
  public void setRate_bucket1(String rateBucket1) {
    this.rate_bucket1 = rateBucket1;
  }
  public String getRate_bucket2() {
    return this.rate_bucket2;
  }
  public void setRate_bucket2(String rateBucket2) {
    this.rate_bucket2 = rateBucket2;
  }
  public String getSeasoning_bucket_without_advance() {
    return this.seasoning_bucket_without_advance;
  }

  public void setSeasoning_bucket_without_advance(String seasoningBucketWithoutAdvance) {
    this.seasoning_bucket_without_advance = seasoningBucketWithoutAdvance;
  }
  public String getSeasoning_bucket_with_advance() {
    return this.seasoning_bucket_with_advance;
  }
  public void setSeasoning_bucket_with_advance(String seasoningBucketWithAdvance) {
    this.seasoning_bucket_with_advance = seasoningBucketWithAdvance;
  }
  public String getCustomer_segment() {
    return this.customer_segment;
  }
  public void setCustomer_segment(String customerSegment) {
    this.customer_segment = customerSegment;
  }
  public String getCustomer_category() {
    return this.customer_category;
  }
  public void setCustomer_category(String customerCategory) {
    this.customer_category = customerCategory;
  }
  public String getDealer_name() {
    return this.dealer_name;
  }
  public void setDealer_name(String dealerName) {
    this.dealer_name = dealerName;
  }
  public String getManufacturer() {
    return this.manufacturer;
  }
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }
  public String getVehicle_no() {
    return this.vehicle_no;
  }
  public void setVehicle_no(String vehicleNo) {
    this.vehicle_no = vehicleNo;
  }
  public String getChassis_no() {
    return this.chassis_no;
  }
  public void setChassis_no(String chassisNo) {
    this.chassis_no = chassisNo;
  }
  public String getEngine_no() {
    return this.engine_no;
  }
  public void setEngine_no(String engineNo) {
    this.engine_no = engineNo;
  }
  public BigDecimal getOverdue_amount() {
    return this.overdue_amount;
  }
  public void setOverdue_amount(BigDecimal overdueAmount) {
    this.overdue_amount = overdueAmount;
  }
  public BigDecimal getOverdue_prin_amt() {
    return this.overdue_prin_amt;
  }
  public void setOverdue_prin_amt(BigDecimal overduePrinAmt) {
    this.overdue_prin_amt = overduePrinAmt;
  }
  public BigDecimal getOverdue_int_amt() {
    return this.overdue_int_amt;
  }
  public void setOverdue_int_amt(BigDecimal overdueIntAmt) {
    this.overdue_int_amt = overdueIntAmt;
  }
  public String getTotal_age_of_vehicle_loan_sanction() {
    return this.total_age_of_vehicle_loan_sanction;
  }

  public void setTotal_age_of_vehicle_loan_sanction(String totalAgeOfVehicleLoanSanction) {
    this.total_age_of_vehicle_loan_sanction = totalAgeOfVehicleLoanSanction;
  }
  public String getTotal_age_of_vehicle_loan_maturity() {
    return this.total_age_of_vehicle_loan_maturity;
  }

  public void setTotal_age_of_vehicle_loan_maturity(String totalAgeOfVehicleLoanMaturity) {
    this.total_age_of_vehicle_loan_maturity = totalAgeOfVehicleLoanMaturity;
  }
  public String getAge_of_borrower() {
    return this.age_of_borrower;
  }
  public void setAge_of_borrower(String ageOfBorrower) {
    this.age_of_borrower = ageOfBorrower;
  }
  public String getDob() {
    return this.dob;
  }
  public void setDob(String dob) {
    this.dob = dob;
  }
  public String getYear_of_manufacture() {
    return this.year_of_manufacture;
  }
  public void setYear_of_manufacture(String yearOfManufacture) {
    this.year_of_manufacture = yearOfManufacture;
  }
  public String getRepayment_mode() {
    return this.repayment_mode;
  }
  public void setRepayment_mode(String repaymentMode) {
    this.repayment_mode = repaymentMode;
  }
  public String getAddress() {
    return this.address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public String getCity_customer() {
    return this.city_customer;
  }
  public void setCity_customer(String cityCustomer) {
    this.city_customer = cityCustomer;
  }
  public String getDistrict_customer() {
    return this.district_customer;
  }
  public void setDistrict_customer(String districtCustomer) {
    this.district_customer = districtCustomer;
  }
  public String getState_customer() {
    return this.state_customer;
  }
  public void setState_customer(String stateCustomer) {
    this.state_customer = stateCustomer;
  }
  public String getPan_number() {
    return this.pan_number;
  }
  public void setPan_number(String panNumber) {
    this.pan_number = panNumber;
  }
  public String getId_proof() {
    return this.id_proof;
  }
  public void setId_proof(String idProof) {
    this.id_proof = idProof;
  }
  public String getAddress_proof() {
    return this.address_proof;
  }
  public void setAddress_proof(String addressProof) {
    this.address_proof = addressProof;
  }
  public String getCo_applicant_name() {
    return this.co_applicant_name;
  }
  public void setCo_applicant_name(String coApplicantName) {
    this.co_applicant_name = coApplicantName;
  }
  public String getGurantor_name() {
    return this.gurantor_name;
  }
  public void setGurantor_name(String gurantorName) {
    this.gurantor_name = gurantorName;
  }
  public String getTotal_vehicles() {
    return this.total_vehicles;
  }
  public void setTotal_vehicles(String totalVehicles) {
    this.total_vehicles = totalVehicles;
  }
  public String getName_of_landholding() {
    return this.name_of_landholding;
  }
  public void setName_of_landholding(String nameOfLandholding) {
    this.name_of_landholding = nameOfLandholding;
  }
  public String getWeaker_section() {
    return this.weaker_section;
  }
  public void setWeaker_section(String weakerSection) {
    this.weaker_section = weakerSection;
  }
  public String getLandholding_size() {
    return this.landholding_size;
  }
  public void setLandholding_size(String landholdingSize) {
    this.landholding_size = landholdingSize;
  }
  public String getAgri_owner_name() {
    return this.agri_owner_name;
  }
  public void setAgri_owner_name(String agriOwnerName) {
    this.agri_owner_name = agriOwnerName;
  }
  public String getRelation_with_applicant() {
    return this.relation_with_applicant;
  }
  public void setRelation_with_applicant(String relationWithApplicant) {
    this.relation_with_applicant = relationWithApplicant;
  }
  public String getPeak_dpd() {
    return this.peak_dpd;
  }
  public void setPeak_dpd(String peakDpd) {
    this.peak_dpd = peakDpd;
  }
  public String getNet_paid_amount_customer() {
    return this.net_paid_amount_customer;
  }
  public void setNet_paid_amount_customer(String netPaidAmountCustomer) {
    this.net_paid_amount_customer = netPaidAmountCustomer;
  }
  public String getLoan_inst_mode() {
    return this.loan_inst_mode;
  }
  public void setLoan_inst_mode(String loanInstMode) {
    this.loan_inst_mode = loanInstMode;
  }
  public String getDo_date() {
    return this.do_date;
  }
  public void setDo_date(String doDate) {
    this.do_date = doDate;
  }
  public String getDo_number() {
    return this.do_number;
  }
  public void setDo_number(String doNumber) {
    this.do_number = doNumber;
  }
  public String getSecurity_deposit() {
    return this.security_deposit;
  }
  public void setSecurity_deposit(String securityDeposit) {
    this.security_deposit = securityDeposit;
  }
  public String getNpa_status() {
    return this.npa_status;
  }
  public void setNpa_status(String npaStatus) {
    this.npa_status = npaStatus;
  }
  public String getRec_status() {
    return this.rec_status;
  }
  public void setRec_status(String recStatus) {
    this.rec_status = recStatus;
  }
  public String getUser_id() {
    return this.user_id;
  }
  public void setUser_id(String userId) {
    this.user_id = userId;
  }
  public String getMaker_date() {
    return this.maker_date;
  }
  public void setMaker_date(String makerDate) {
    this.maker_date = makerDate;
  }
  public String getBuyback() {
    return this.buyback;
  }
  public void setBuyback(String buyback) {
    this.buyback = buyback;
  }
  public String getBuyback_date() {
    return this.buyback_date;
  }
  public void setBuyback_date(String buybackDate) {
    this.buyback_date = buybackDate;
  }
}