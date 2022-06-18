package com.webservice.cp.pdvo;

public class CrPdResidenceStabilityDtl {

	private Integer id;

	private Integer pdId;

	private Integer currentAddressYear;

	private Integer totalYearSameCity;

	private String priorAddressStability;

	private String mobileNo1;

	private String mobileNo2;

	private String landlineNo;

	private String coapplicant1;

	private String coapplicant2;

	private String propertyOwnerName;

	private String propertyProofSeen;

	private String realtionshipOwner;

	private String contactDtlOwner;

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

	public Integer getCurrentAddressYear() {
		return currentAddressYear;
	}

	public void setCurrentAddressYear(Integer currentAddressYear) {
		this.currentAddressYear = currentAddressYear;
	}

	public Integer getTotalYearSameCity() {
		return totalYearSameCity;
	}

	public void setTotalYearSameCity(Integer totalYearSameCity) {
		this.totalYearSameCity = totalYearSameCity;
	}

	public String getPriorAddressStability() {
		return priorAddressStability;
	}

	public void setPriorAddressStability(String priorAddressStability) {
		this.priorAddressStability = priorAddressStability;
	}

	public String getMobileNo1() {
		return mobileNo1;
	}

	public void setMobileNo1(String mobileNo1) {
		this.mobileNo1 = mobileNo1;
	}

	public String getMobileNo2() {
		return mobileNo2;
	}

	public void setMobileNo2(String mobileNo2) {
		this.mobileNo2 = mobileNo2;
	}

	public String getLandlineNo() {
		return landlineNo;
	}

	public void setLandlineNo(String landlineNo) {
		this.landlineNo = landlineNo;
	}

	public String getCoapplicant1() {
		return coapplicant1;
	}

	public void setCoapplicant1(String coapplicant1) {
		this.coapplicant1 = coapplicant1;
	}

	public String getCoapplicant2() {
		return coapplicant2;
	}

	public void setCoapplicant2(String coapplicant2) {
		this.coapplicant2 = coapplicant2;
	}

	public String getPropertyOwnerName() {
		return propertyOwnerName;
	}

	public void setPropertyOwnerName(String propertyOwnerName) {
		this.propertyOwnerName = propertyOwnerName;
	}

	public String getPropertyProofSeen() {
		return propertyProofSeen;
	}

	public void setPropertyProofSeen(String propertyProofSeen) {
		this.propertyProofSeen = propertyProofSeen;
	}

	public String getRealtionshipOwner() {
		return realtionshipOwner;
	}

	public void setRealtionshipOwner(String realtionshipOwner) {
		this.realtionshipOwner = realtionshipOwner;
	}

	public String getContactDtlOwner() {
		return contactDtlOwner;
	}

	public void setContactDtlOwner(String contactDtlOwner) {
		this.contactDtlOwner = contactDtlOwner;
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
		return "CrPdResidenceStabilityDtl [id=" + id + ", pdId=" + pdId
				+ ", currentAddressYear=" + currentAddressYear
				+ ", totalYearSameCity=" + totalYearSameCity
				+ ", priorAddressStability=" + priorAddressStability
				+ ", mobileNo1=" + mobileNo1 + ", mobileNo2=" + mobileNo2
				+ ", landlineNo=" + landlineNo + ", coapplicant1="
				+ coapplicant1 + ", coapplicant2=" + coapplicant2
				+ ", propertyOwnerName=" + propertyOwnerName
				+ ", propertyProofSeen=" + propertyProofSeen
				+ ", realtionshipOwner=" + realtionshipOwner
				+ ", contactDtlOwner=" + contactDtlOwner + ", makerDate="
				+ makerDate + ", makerId=" + makerId + "]";
	}
}
