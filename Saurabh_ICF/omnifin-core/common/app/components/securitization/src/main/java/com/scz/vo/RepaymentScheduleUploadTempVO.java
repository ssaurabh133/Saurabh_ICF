package com.scz.vo;
import java.math.BigDecimal;
import java.sql.Date;
public class RepaymentScheduleUploadTempVO {
	private int id;
	private int repaysch_id;
	private int pool_id;
	private int loan_id;
	private int instl_no;
	private Date instl_date;
	
	private BigDecimal instl_amount;
	private BigDecimal prin_comp;
	private BigDecimal int_comp;
	private BigDecimal excess_int;
	private BigDecimal other_charges;
	
	private String bill_flag;
	private BigDecimal instl_amount_recd;
	private BigDecimal prin_comp_recd;
	private BigDecimal int_comp_recd;
	private BigDecimal excess_int_recd;
	
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
	public int getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(int loanId) {
		loan_id = loanId;
	}
	public int getInstl_no() {
		return instl_no;
	}
	public void setInstl_no(int instlNo) {
		instl_no = instlNo;
	}
	public Date getInstl_date() {
		return instl_date;
	}
	public void setInstl_date(Date instlDate) {
		instl_date = instlDate;
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
	public BigDecimal getExcess_int() {
		return excess_int;
	}
	public void setExcess_int(BigDecimal excessInt) {
		excess_int = excessInt;
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
	public BigDecimal getInstl_amount_recd() {
		return instl_amount_recd;
	}
	public void setInstl_amount_recd(BigDecimal instlAmountRecd) {
		instl_amount_recd = instlAmountRecd;
	}
	public BigDecimal getPrin_comp_recd() {
		return prin_comp_recd;
	}
	public void setPrin_comp_recd(BigDecimal prinCompRecd) {
		prin_comp_recd = prinCompRecd;
	}
	public BigDecimal getInt_comp_recd() {
		return int_comp_recd;
	}
	public void setInt_comp_recd(BigDecimal intCompRecd) {
		int_comp_recd = intCompRecd;
	}
	public BigDecimal getExcess_int_recd() {
		return excess_int_recd;
	}
	public void setExcess_int_recd(BigDecimal excessIntRecd) {
		excess_int_recd = excessIntRecd;
	}
	public BigDecimal getOther_charges_recd() {
		return other_charges_recd;
	}
	public void setOther_charges_recd(BigDecimal otherChargesRecd) {
		other_charges_recd = otherChargesRecd;
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
	public Date getMaker_date() {
		return maker_date;
	}
	public void setMaker_date(Date makerDate) {
		maker_date = makerDate;
	}
	public String getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(String authorId) {
		author_id = authorId;
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
