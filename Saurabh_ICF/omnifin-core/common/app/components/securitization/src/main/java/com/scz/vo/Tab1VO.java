package com.scz.vo;

public class Tab1VO {
	private int id;
	private int hid;
	private int poolID;
	
	private String creditEnhanceType;
	private String creditEnhanceDocRefNo;
	private int creditEnhanceAmount;
	private int totalRecord;
	private String srNo;
	
	
	public int getHid() {
		return hid;
	}
	public void setHid(int hid) {
		this.hid = hid;
	}
	public String getSrNo() {
		return srNo;
	}
	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
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
	
	
	public String getCreditEnhanceType() {
		return creditEnhanceType;
	}
	public void setCreditEnhanceType(String creditEnhanceType) {
		this.creditEnhanceType = creditEnhanceType;
	}
	public String getCreditEnhanceDocRefNo() {
		return creditEnhanceDocRefNo;
	}
	public void setCreditEnhanceDocRefNo(String creditEnhanceDocRefNo) {
		this.creditEnhanceDocRefNo = creditEnhanceDocRefNo;
	}
	public int getCreditEnhanceAmount() {
		return creditEnhanceAmount;
	}
	public void setCreditEnhanceAmount(int creditEnhanceAmount) {
		this.creditEnhanceAmount = creditEnhanceAmount;
	}
}
