package com.scz.vo;
import java.math.BigDecimal;
import java.sql.Date;

public class BankUploadUploadVO {
	private int id;
	private int repaysch_id;
	private int pool_id;
	private int instl_no;
	private Date instl_date;
	
	private BigDecimal instl_amount;
	private BigDecimal prin_comp;
	private BigDecimal int_comp;
	private BigDecimal au_prin_comp;
	private BigDecimal other_charges;
	
	private String bill_flag;
	private BigDecimal au_int_comp;
	private BigDecimal distribution_ratio_bank;
	private BigDecimal distribution_ratio_au;
	
	private BigDecimal other_charges_recd;
	private Date last_pmnt_date;
	private Date last_od_calc_date;
	private BigDecimal total_od_amount;
	private BigDecimal interest_rate;
	
	private String adv_flag;
	private BigDecimal disbursal_amount;
	private BigDecimal prin_os;
	private String rec_type;
	private String rec_status="P";
	
	private String maker_id;
	private Date maker_date;
	private String author_id;
	private Date author_date;
	private Date repay_eff_date;
	
	private int loan_disbursal_id;
	private int seq_no;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	public int getRepaysch_id() {
		return repaysch_id;
	}
	public void setRepaysch_id(int repayschId) {
		repaysch_id = repayschId;
	}
	public int getPool_id() {
		return pool_id;
	}
	public void setPool_id(int poolId) {
		pool_id = poolId;
	}
	public int getInstl_no() {
		return instl_no;
	}
	public void setInstl_no(int instlNo) {
		instl_no = instlNo;
	}
	
	public BigDecimal getInstl_amount() {
		return instl_amount;
	}
	public void setInstl_amount(BigDecimal instlAmount) {
		instl_amount = instlAmount;
	}
	public BigDecimal getPrin_comp() {
		return prin_comp;
	}
	public void setPrin_comp(BigDecimal prinComp) {
		prin_comp = prinComp;
	}
	public BigDecimal getInt_comp() {
		return int_comp;
	}
	public void setInt_comp(BigDecimal intComp) {
		int_comp = intComp;
	}
	
	public BigDecimal getAu_prin_comp() {
		return au_prin_comp;
	}
	public void setAu_prin_comp(BigDecimal auPrinComp) {
		au_prin_comp = auPrinComp;
	}
	public BigDecimal getAu_int_comp() {
		return au_int_comp;
	}
	public void setAu_int_comp(BigDecimal auIntComp) {
		au_int_comp = auIntComp;
	}
	public BigDecimal getDistribution_ratio_bank() {
		return distribution_ratio_bank;
	}
	public void setDistribution_ratio_bank(BigDecimal distributionRatioBank) {
		distribution_ratio_bank = distributionRatioBank;
	}
	public BigDecimal getDistribution_ratio_au() {
		return distribution_ratio_au;
	}
	public void setDistribution_ratio_au(BigDecimal distributionRatioAu) {
		distribution_ratio_au = distributionRatioAu;
	}
	public BigDecimal getOther_charges() {
		return other_charges;
	}
	public void setOther_charges(BigDecimal otherCharges) {
		other_charges = otherCharges;
	}
	public String getBill_flag() {
		return bill_flag;
	}
	public void setBill_flag(String billFlag) {
		bill_flag = billFlag;
	}
	
	public BigDecimal getOther_charges_recd() {
		return other_charges_recd;
	}
	public void setOther_charges_recd(BigDecimal otherChargesRecd) {
		other_charges_recd = otherChargesRecd;
	}
	
	public BigDecimal getTotal_od_amount() {
		return total_od_amount;
	}
	public void setTotal_od_amount(BigDecimal totalOdAmount) {
		total_od_amount = totalOdAmount;
	}
	public BigDecimal getInterest_rate() {
		return interest_rate;
	}
	public void setInterest_rate(BigDecimal interestRate) {
		interest_rate = interestRate;
	}
	public String getAdv_flag() {
		return adv_flag;
	}
	public void setAdv_flag(String advFlag) {
		adv_flag = advFlag;
	}
	public BigDecimal getDisbursal_amount() {
		return disbursal_amount;
	}
	public void setDisbursal_amount(BigDecimal disbursalAmount) {
		disbursal_amount = disbursalAmount;
	}
	public BigDecimal getPrin_os() {
		return prin_os;
	}
	public void setPrin_os(BigDecimal prinOs) {
		prin_os = prinOs;
	}
	public String getRec_type() {
		return rec_type;
	}
	public void setRec_type(String recType) {
		rec_type = recType;
	}
	public String getRec_status() {
		return rec_status;
	}
	public void setRec_status(String recStatus) {
		rec_status = recStatus;
	}
	public String getMaker_id() {
		return maker_id;
	}
	public void setMaker_id(String makerId) {
		maker_id = makerId;
	}

	public String getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(String authorId) {
		author_id = authorId;
	}
	
	
	public Date getInstl_date() {
		return instl_date;
	}
	public void setInstl_date(Date instlDate) {
		instl_date = instlDate;
	}
	public Date getLast_pmnt_date() {
		return last_pmnt_date;
	}
	public void setLast_pmnt_date(Date lastPmntDate) {
		last_pmnt_date = lastPmntDate;
	}
	public Date getLast_od_calc_date() {
		return last_od_calc_date;
	}
	public void setLast_od_calc_date(Date lastOdCalcDate) {
		last_od_calc_date = lastOdCalcDate;
	}
	public Date getMaker_date() {
		return maker_date;
	}
	public void setMaker_date(Date makerDate) {
		maker_date = makerDate;
	}
	public Date getAuthor_date() {
		return author_date;
	}
	public void setAuthor_date(Date authorDate) {
		author_date = authorDate;
	}
	public Date getRepay_eff_date() {
		return repay_eff_date;
	}
	public void setRepay_eff_date(Date repayEffDate) {
		repay_eff_date = repayEffDate;
	}
	public int getLoan_disbursal_id() {
		return loan_disbursal_id;
	}
	public void setLoan_disbursal_id(int loanDisbursalId) {
		loan_disbursal_id = loanDisbursalId;
	}
	public int getSeq_no() {
		return seq_no;
	}
	public void setSeq_no(int seqNo) {
		seq_no = seqNo;
	}
}
