package com.scz.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class PaymentAllocationReportExcelDataVO {
	private int id;
	private int pool_id;
	private String pool_name;
	private int loan_no;
	private String omniFinLoanNo;
	private String customer_Name;
	private Date expiryDate;
	private Date lastDateOfCollMonth;
	
	private BigDecimal opening_POS;
	
	private BigDecimal opening_Future_Flow;
	private BigDecimal opening_Overdue;
	private BigDecimal openingOverduePos;
	private BigDecimal openingOverdueInt;
	private BigDecimal actualEMI;
	private BigDecimal pos;
	private BigDecimal totalIntAsPerBank;
	private BigDecimal totalIntAsPerAU;
	private BigDecimal collectionAgainstOpeningOverdue;
	private BigDecimal collectionAgainstOpeningOverduePOS;
	private BigDecimal collectionAgainstOpeningOverdueINT;
	private BigDecimal collectionAgainstCurrentBilling;
	
	private BigDecimal collectionAgainstCurrentBillingPOS;
	private BigDecimal collectionAgainstCurrentBillingINT;
	private BigDecimal totalCollectionAgainstPOS;
	private BigDecimal totalCollectionAgainstINT;
	private BigDecimal totalCollection;
	private BigDecimal excessAmountOtherThanEmi;
	private BigDecimal preClosureAmount;
	private BigDecimal insuranceAmountOfCollMonth;
	
	private BigDecimal dpd;
	private BigDecimal dpdBucket;
	private BigDecimal totalClosingOverdue;
	private BigDecimal closingOverduePos;
	private BigDecimal closingOverdueIntWithInsuranceAmount;
	private BigDecimal totalClosingOverdueExculdingInsurance;
	private BigDecimal closingFuturePos;
	private BigDecimal closingFutureFlow;
	
	private BigDecimal posAtCustomerEnd;
	private BigDecimal emiAtCustomerEnd;
	private String status ;
	private String emiPattern;
	private Date dateOfRepo ;
	private String vehicleCateogry;
	private String make;
	private String classification;
	
	private String branch;
	private String state;
	private String month;
	
	private String makerID;
	private Date makerDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPool_id() {
		return pool_id;
	}
	public void setPool_id(int poolId) {
		pool_id = poolId;
	}
	public String getPool_name() {
		return pool_name;
	}
	public void setPool_name(String poolName) {
		pool_name = poolName;
	}
	public int getLoan_no() {
		return loan_no;
	}
	public void setLoan_no(int loanNo) {
		loan_no = loanNo;
	}
	
	public String getOmniFinLoanNo() {
		return omniFinLoanNo;
	}
	public void setOmniFinLoanNo(String omniFinLoanNo) {
		this.omniFinLoanNo = omniFinLoanNo;
	}
	public String getCustomer_Name() {
		return customer_Name;
	}
	public void setCustomer_Name(String customerName) {
		customer_Name = customerName;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Date getLastDateOfCollMonth() {
		return lastDateOfCollMonth;
	}
	public void setLastDateOfCollMonth(Date lastDateOfCollMonth) {
		this.lastDateOfCollMonth = lastDateOfCollMonth;
	}
	public BigDecimal getOpening_POS() {
		return opening_POS;
	}
	public void setOpening_POS(BigDecimal openingPOS) {
		opening_POS = openingPOS;
	}
	public BigDecimal getOpening_Future_Flow() {
		return opening_Future_Flow;
	}
	public void setOpening_Future_Flow(BigDecimal openingFutureFlow) {
		opening_Future_Flow = openingFutureFlow;
	}
	public BigDecimal getOpening_Overdue() {
		return opening_Overdue;
	}
	public void setOpening_Overdue(BigDecimal openingOverdue) {
		opening_Overdue = openingOverdue;
	}
	public BigDecimal getOpeningOverduePos() {
		return openingOverduePos;
	}
	public void setOpeningOverduePos(BigDecimal openingOverduePos) {
		this.openingOverduePos = openingOverduePos;
	}
	public BigDecimal getOpeningOverdueInt() {
		return openingOverdueInt;
	}
	public void setOpeningOverdueInt(BigDecimal openingOverdueInt) {
		this.openingOverdueInt = openingOverdueInt;
	}
	public BigDecimal getActualEMI() {
		return actualEMI;
	}
	public void setActualEMI(BigDecimal actualEMI) {
		this.actualEMI = actualEMI;
	}
	public BigDecimal getPos() {
		return pos;
	}
	public void setPos(BigDecimal pos) {
		this.pos = pos;
	}
	public BigDecimal getTotalIntAsPerBank() {
		return totalIntAsPerBank;
	}
	public void setTotalIntAsPerBank(BigDecimal totalIntAsPerBank) {
		this.totalIntAsPerBank = totalIntAsPerBank;
	}
	public BigDecimal getTotalIntAsPerAU() {
		return totalIntAsPerAU;
	}
	public void setTotalIntAsPerAU(BigDecimal totalIntAsPerAU) {
		this.totalIntAsPerAU = totalIntAsPerAU;
	}
	public BigDecimal getCollectionAgainstOpeningOverdue() {
		return collectionAgainstOpeningOverdue;
	}
	public void setCollectionAgainstOpeningOverdue(
			BigDecimal collectionAgainstOpeningOverdue) {
		this.collectionAgainstOpeningOverdue = collectionAgainstOpeningOverdue;
	}
	public BigDecimal getCollectionAgainstOpeningOverduePOS() {
		return collectionAgainstOpeningOverduePOS;
	}
	public void setCollectionAgainstOpeningOverduePOS(
			BigDecimal collectionAgainstOpeningOverduePOS) {
		this.collectionAgainstOpeningOverduePOS = collectionAgainstOpeningOverduePOS;
	}
	public BigDecimal getCollectionAgainstOpeningOverdueINT() {
		return collectionAgainstOpeningOverdueINT;
	}
	public void setCollectionAgainstOpeningOverdueINT(
			BigDecimal collectionAgainstOpeningOverdueINT) {
		this.collectionAgainstOpeningOverdueINT = collectionAgainstOpeningOverdueINT;
	}
	public BigDecimal getCollectionAgainstCurrentBilling() {
		return collectionAgainstCurrentBilling;
	}
	public void setCollectionAgainstCurrentBilling(
			BigDecimal collectionAgainstCurrentBilling) {
		this.collectionAgainstCurrentBilling = collectionAgainstCurrentBilling;
	}
	public BigDecimal getCollectionAgainstCurrentBillingPOS() {
		return collectionAgainstCurrentBillingPOS;
	}
	public void setCollectionAgainstCurrentBillingPOS(
			BigDecimal collectionAgainstCurrentBillingPOS) {
		this.collectionAgainstCurrentBillingPOS = collectionAgainstCurrentBillingPOS;
	}
	public BigDecimal getCollectionAgainstCurrentBillingINT() {
		return collectionAgainstCurrentBillingINT;
	}
	public void setCollectionAgainstCurrentBillingINT(
			BigDecimal collectionAgainstCurrentBillingINT) {
		this.collectionAgainstCurrentBillingINT = collectionAgainstCurrentBillingINT;
	}
	public BigDecimal getTotalCollectionAgainstPOS() {
		return totalCollectionAgainstPOS;
	}
	public void setTotalCollectionAgainstPOS(BigDecimal totalCollectionAgainstPOS) {
		this.totalCollectionAgainstPOS = totalCollectionAgainstPOS;
	}
	public BigDecimal getTotalCollectionAgainstINT() {
		return totalCollectionAgainstINT;
	}
	public void setTotalCollectionAgainstINT(BigDecimal totalCollectionAgainstINT) {
		this.totalCollectionAgainstINT = totalCollectionAgainstINT;
	}
	public BigDecimal getTotalCollection() {
		return totalCollection;
	}
	public void setTotalCollection(BigDecimal totalCollection) {
		this.totalCollection = totalCollection;
	}
	public BigDecimal getExcessAmountOtherThanEmi() {
		return excessAmountOtherThanEmi;
	}
	public void setExcessAmountOtherThanEmi(BigDecimal excessAmountOtherThanEmi) {
		this.excessAmountOtherThanEmi = excessAmountOtherThanEmi;
	}
	public BigDecimal getPreClosureAmount() {
		return preClosureAmount;
	}
	public void setPreClosureAmount(BigDecimal preClosureAmount) {
		this.preClosureAmount = preClosureAmount;
	}
	public BigDecimal getInsuranceAmountOfCollMonth() {
		return insuranceAmountOfCollMonth;
	}
	public void setInsuranceAmountOfCollMonth(BigDecimal insuranceAmountOfCollMonth) {
		this.insuranceAmountOfCollMonth = insuranceAmountOfCollMonth;
	}
	public BigDecimal getDpd() {
		return dpd;
	}
	public void setDpd(BigDecimal dpd) {
		this.dpd = dpd;
	}
	public BigDecimal getDpdBucket() {
		return dpdBucket;
	}
	public void setDpdBucket(BigDecimal dpdBucket) {
		this.dpdBucket = dpdBucket;
	}
	public BigDecimal getTotalClosingOverdue() {
		return totalClosingOverdue;
	}
	public void setTotalClosingOverdue(BigDecimal totalClosingOverdue) {
		this.totalClosingOverdue = totalClosingOverdue;
	}
	public BigDecimal getClosingOverduePos() {
		return closingOverduePos;
	}
	public void setClosingOverduePos(BigDecimal closingOverduePos) {
		this.closingOverduePos = closingOverduePos;
	}
	public BigDecimal getClosingOverdueIntWithInsuranceAmount() {
		return closingOverdueIntWithInsuranceAmount;
	}
	public void setClosingOverdueIntWithInsuranceAmount(
			BigDecimal closingOverdueIntWithInsuranceAmount) {
		this.closingOverdueIntWithInsuranceAmount = closingOverdueIntWithInsuranceAmount;
	}
	public BigDecimal getTotalClosingOverdueExculdingInsurance() {
		return totalClosingOverdueExculdingInsurance;
	}
	public void setTotalClosingOverdueExculdingInsurance(
			BigDecimal totalClosingOverdueExculdingInsurance) {
		this.totalClosingOverdueExculdingInsurance = totalClosingOverdueExculdingInsurance;
	}
	public BigDecimal getClosingFuturePos() {
		return closingFuturePos;
	}
	public void setClosingFuturePos(BigDecimal closingFuturePos) {
		this.closingFuturePos = closingFuturePos;
	}
	public BigDecimal getClosingFutureFlow() {
		return closingFutureFlow;
	}
	public void setClosingFutureFlow(BigDecimal closingFutureFlow) {
		this.closingFutureFlow = closingFutureFlow;
	}
	public BigDecimal getPosAtCustomerEnd() {
		return posAtCustomerEnd;
	}
	public void setPosAtCustomerEnd(BigDecimal posAtCustomerEnd) {
		this.posAtCustomerEnd = posAtCustomerEnd;
	}
	public BigDecimal getEmiAtCustomerEnd() {
		return emiAtCustomerEnd;
	}
	public void setEmiAtCustomerEnd(BigDecimal emiAtCustomerEnd) {
		this.emiAtCustomerEnd = emiAtCustomerEnd;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmiPattern() {
		return emiPattern;
	}
	public void setEmiPattern(String emiPattern) {
		this.emiPattern = emiPattern;
	}
	public Date getDateOfRepo() {
		return dateOfRepo;
	}
	public void setDateOfRepo(Date dateOfRepo) {
		this.dateOfRepo = dateOfRepo;
	}
	public String getVehicleCateogry() {
		return vehicleCateogry;
	}
	public void setVehicleCateogry(String vehicleCateogry) {
		this.vehicleCateogry = vehicleCateogry;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
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
	
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getMakerID() {
		return makerID;
	}
	public void setMakerID(String makerID) {
		this.makerID = makerID;
	}
	public Date getMakerDate() {
		return makerDate;
	}
	public void setMakerDate(Date makerDate) {
		this.makerDate = makerDate;
	}
	
	
	
	
}
