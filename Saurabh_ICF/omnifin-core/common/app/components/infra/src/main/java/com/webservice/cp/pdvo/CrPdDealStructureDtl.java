package com.webservice.cp.pdvo;

public class CrPdDealStructureDtl {

	private Integer id;

	private Integer pdId;

	private Integer customerId;

	private String customerName;

	private String applicantType;

	private String dob;

	private String educationQualification;

	private String relationship;

	private String propertyOwner;

	private String incomeConsidered;

	private String pancard;

	private String aadhaarCard;

	private String drivingLicence;

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

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getApplicantType() {
		return applicantType;
	}

	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getEducationQualification() {
		return educationQualification;
	}

	public void setEducationQualification(String educationQualification) {
		this.educationQualification = educationQualification;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getPropertyOwner() {
		return propertyOwner;
	}

	public void setPropertyOwner(String propertyOwner) {
		this.propertyOwner = propertyOwner;
	}

	public String getIncomeConsidered() {
		return incomeConsidered;
	}

	public void setIncomeConsidered(String incomeConsidered) {
		this.incomeConsidered = incomeConsidered;
	}

	public String getPancard() {
		return pancard;
	}

	public void setPancard(String pancard) {
		this.pancard = pancard;
	}

	public String getAadhaarCard() {
		return aadhaarCard;
	}

	public void setAadhaarCard(String aadhaarCard) {
		this.aadhaarCard = aadhaarCard;
	}

	public String getDrivingLicence() {
		return drivingLicence;
	}

	public void setDrivingLicence(String drivingLicence) {
		this.drivingLicence = drivingLicence;
	}

	@Override
	public String toString() {
		return "CrPdDealStructureDtl [id=" + id + ", pdId=" + pdId
				+ ", customerId=" + customerId + ", customerName="
				+ customerName + ", applicantType=" + applicantType + ", dob="
				+ dob + ", educationQualification=" + educationQualification
				+ ", relationship=" + relationship + ", propertyOwner="
				+ propertyOwner + ", incomeConsidered=" + incomeConsidered
				+ ", pancard=" + pancard + ", aadhaarCard=" + aadhaarCard
				+ ", drivingLicence=" + drivingLicence + "]";
	}
}
