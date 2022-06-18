package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class CrPdFamilyDtl {

	private Integer id;

	private Integer pdId;

	private String customerName;

	private String applicantRealationship;

	private String dob;

	private String occupation;

	private String medicalCover;

	private BigDecimal medicalCoverAmount;

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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getApplicantRealationship() {
		return applicantRealationship;
	}

	public void setApplicantRealationship(String applicantRealationship) {
		this.applicantRealationship = applicantRealationship;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getMedicalCover() {
		return medicalCover;
	}

	public void setMedicalCover(String medicalCover) {
		this.medicalCover = medicalCover;
	}

	public BigDecimal getMedicalCoverAmount() {
		return medicalCoverAmount;
	}

	public void setMedicalCoverAmount(BigDecimal medicalCoverAmount) {
		this.medicalCoverAmount = medicalCoverAmount;
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
		return "CrPdFamilyDtl [id=" + id + ", pdId=" + pdId + ", customerName="
				+ customerName + ", applicantRealationship="
				+ applicantRealationship + ", dob=" + dob + ", occupation="
				+ occupation + ", medicalCover=" + medicalCover
				+ ", medicalCoverAmount=" + medicalCoverAmount + ", makerDate="
				+ makerDate + ", makerId=" + makerId + "]";
	}
}
