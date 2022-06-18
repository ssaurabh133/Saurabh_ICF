package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class CrPdReferenceDtl {
	
	private Integer id;
	private Integer pdId;
	private String name;
	private String address;
	private String mobileNo;
	private String tvrStatus;
	private String relationship;
	private String doneBy;
	private String remark;
	private String makerDate;
	private String makerId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPdId() {
		return pdId;
	}

	public void setPdId(Integer pdId) {
		this.pdId = pdId;
	}


	public String getMakerDate() {
		return makerDate;
	}

	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}

	public String getMakerId() {
		return makerId;
	}

	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getTvrStatus() {
		return tvrStatus;
	}

	public void setTvrStatus(String tvrStatus) {
		this.tvrStatus = tvrStatus;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getDoneBy() {
		return doneBy;
	}

	public void setDoneBy(String doneBy) {
		this.doneBy = doneBy;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "CrPdReferenceDtl [id=" + id + ", pdId=" + pdId + ", name="
				+ name + ", address=" + address + ", mobileNo=" + mobileNo
				+ ", tvrStatus=" + tvrStatus + ", relationship=" + relationship
				+ ", doneBy=" + doneBy + ", remark=" + remark + ", makerDate="
				+ makerDate + ", makerId=" + makerId + "]";
	}


}
