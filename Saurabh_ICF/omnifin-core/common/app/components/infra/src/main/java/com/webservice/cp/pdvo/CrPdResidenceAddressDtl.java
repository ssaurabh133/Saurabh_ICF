package com.webservice.cp.pdvo;

public class CrPdResidenceAddressDtl {
	private Integer id;
	
	private Integer pdId;

	private String currentAddress;

	private String currentAddressLandmark;

	private String currentAddressProof;

	private String permanentAddress;

	private String permanentAddressLandmark;

	private String permanentAddressContactDtl;

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

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public String getCurrentAddressLandmark() {
		return currentAddressLandmark;
	}

	public void setCurrentAddressLandmark(String currentAddressLandmark) {
		this.currentAddressLandmark = currentAddressLandmark;
	}

	public String getCurrentAddressProof() {
		return currentAddressProof;
	}

	public void setCurrentAddressProof(String currentAddressProof) {
		this.currentAddressProof = currentAddressProof;
	}

	public String getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public String getPermanentAddressLandmark() {
		return permanentAddressLandmark;
	}

	public void setPermanentAddressLandmark(String permanentAddressLandmark) {
		this.permanentAddressLandmark = permanentAddressLandmark;
	}

	public String getPermanentAddressContactDtl() {
		return permanentAddressContactDtl;
	}

	public void setPermanentAddressContactDtl(String permanentAddressContactDtl) {
		this.permanentAddressContactDtl = permanentAddressContactDtl;
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

	@Override
	public String toString() {
		return "CrPdResidenceAddressDtl [id=" + id + ", pdId=" + pdId
				+ ", currentAddress=" + currentAddress
				+ ", currentAddressLandmark=" + currentAddressLandmark
				+ ", currentAddressProof=" + currentAddressProof
				+ ", permanentAddress=" + permanentAddress
				+ ", permanentAddressLandmark=" + permanentAddressLandmark
				+ ", permanentAddressContactDtl=" + permanentAddressContactDtl
				+ ", makerDate=" + makerDate + ", makerId=" + makerId + "]";
	}
}
