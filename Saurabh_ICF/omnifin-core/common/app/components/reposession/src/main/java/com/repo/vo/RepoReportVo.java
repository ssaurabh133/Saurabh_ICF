//Author Name : Vinod Kumar Gupta-->
//Date of Creation : 11-04-2013-->
//Purpose  : vo for Legal report-->
//Documentation : -->

package com.repo.vo;

import java.io.Serializable;

public class RepoReportVo implements Serializable{
	
	private String loanNo;
	private String lbxLoanId;
	private String stockYardDesc;
	private String lbxStockYard;
	private String approverName;
	private String lbxUserId;
	private String agency;
	private String lbxAgencyId;
	private String repossessionDate;
	
	
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getLbxLoanId() {
		return lbxLoanId;
	}
	public void setLbxLoanId(String lbxLoanId) {
		this.lbxLoanId = lbxLoanId;
	}
	public String getStockYardDesc() {
		return stockYardDesc;
	}
	public void setStockYardDesc(String stockYardDesc) {
		this.stockYardDesc = stockYardDesc;
	}
	public String getLbxStockYard() {
		return lbxStockYard;
	}
	public void setLbxStockYard(String lbxStockYard) {
		this.lbxStockYard = lbxStockYard;
	}
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	public String getLbxUserId() {
		return lbxUserId;
	}
	public void setLbxUserId(String lbxUserId) {
		this.lbxUserId = lbxUserId;
	}
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	public String getLbxAgencyId() {
		return lbxAgencyId;
	}
	public void setLbxAgencyId(String lbxAgencyId) {
		this.lbxAgencyId = lbxAgencyId;
	}
	public String getRepossessionDate() {
		return repossessionDate;
	}
	public void setRepossessionDate(String repossessionDate) {
		this.repossessionDate = repossessionDate;
	}
	
	
	
}
