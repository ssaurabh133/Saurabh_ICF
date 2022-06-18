package com.scz.vo;

public class Tab2VO {
	private int id;
	private int hid;
	private int poolID;
	private String instituteID;
	private String lbxinstituteID;
	private String investmentRatio;
	private String interestRate;
	private String distributionPriority;
	private String srNo;
	private int totalRecord;
	
	
	public int getHid() {
		return hid;
	}
	public void setHid(int hid) {
		this.hid = hid;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public String getSrNo() {
		return srNo;
	}
	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getPoolID() {
		return poolID;
	}
	public void setPoolID(int poolID) {
		this.poolID = poolID;
	}
	public String getInstituteID() {
		return instituteID;
	}
	public void setInstituteID(String instituteID) {
		this.instituteID = instituteID;
	}
	public String getLbxinstituteID() {
		return lbxinstituteID;
	}
	public void setLbxinstituteID(String lbxinstituteID) {
		this.lbxinstituteID = lbxinstituteID;
	}
	public String getInvestmentRatio() {
		return investmentRatio;
	}
	public void setInvestmentRatio(String investmentRatio) {
		this.investmentRatio = investmentRatio;
	}
	public String getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}
	public String getDistributionPriority() {
		return distributionPriority;
	}
	public void setDistributionPriority(String distributionPriority) {
		this.distributionPriority = distributionPriority;
	}
	
	
}
