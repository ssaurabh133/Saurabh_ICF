package com.webservice.cp.pdvo;

public class CrPdResidenceVisitDtl {

	private Integer id;

	private Integer pdId;

	private String dateOfVisit;

	private String personMet;

	private String pdTime;

	private String gpsCoordinate;

	private String photagraphTaken;

	private String nameSignature;

	private String empCode;

	private String kycProofsVerified;

	private String kvcPhotographCustomer;

	private String endUseOfFunds;

	private String recommendationNote;

	private String makerId;

	private String makerDate;

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

	public String getDateOfVisit() {
		return dateOfVisit;
	}

	public void setDateOfVisit(String dateOfVisit) {
		this.dateOfVisit = dateOfVisit;
	}

	public String getPersonMet() {
		return personMet;
	}

	public void setPersonMet(String personMet) {
		this.personMet = personMet;
	}

	public String getPdTime() {
		return pdTime;
	}

	public void setPdTime(String pdTime) {
		this.pdTime = pdTime;
	}

	public String getGpsCoordinate() {
		return gpsCoordinate;
	}

	public void setGpsCoordinate(String gpsCoordinate) {
		this.gpsCoordinate = gpsCoordinate;
	}

	public String getPhotagraphTaken() {
		return photagraphTaken;
	}

	public void setPhotagraphTaken(String photagraphTaken) {
		this.photagraphTaken = photagraphTaken;
	}

	public String getNameSignature() {
		return nameSignature;
	}

	public void setNameSignature(String nameSignature) {
		this.nameSignature = nameSignature;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getKycProofsVerified() {
		return kycProofsVerified;
	}

	public void setKycProofsVerified(String kycProofsVerified) {
		this.kycProofsVerified = kycProofsVerified;
	}

	public String getKvcPhotographCustomer() {
		return kvcPhotographCustomer;
	}

	public void setKvcPhotographCustomer(String kvcPhotographCustomer) {
		this.kvcPhotographCustomer = kvcPhotographCustomer;
	}

	public String getEndUseOfFunds() {
		return endUseOfFunds;
	}

	public void setEndUseOfFunds(String endUseOfFunds) {
		this.endUseOfFunds = endUseOfFunds;
	}

	public String getRecommendationNote() {
		return recommendationNote;
	}

	public void setRecommendationNote(String recommendationNote) {
		this.recommendationNote = recommendationNote;
	}

	public String getMakerId() {
		return makerId;
	}

	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}

	public String getMakerDate() {
		return makerDate;
	}

	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}

	@Override
	public String toString() {
		return "CrPdResidenceVisitDtl [id=" + id + ", pdId=" + pdId
				+ ", dateOfVisit=" + dateOfVisit + ", personMet=" + personMet
				+ ", pdTime=" + pdTime + ", gpsCoordinate=" + gpsCoordinate
				+ ", photagraphTaken=" + photagraphTaken + ", nameSignature="
				+ nameSignature + ", empCode=" + empCode
				+ ", kycProofsVerified=" + kycProofsVerified
				+ ", kvcPhotographCustomer=" + kvcPhotographCustomer
				+ ", endUseOfFunds=" + endUseOfFunds + ", recommendationNote="
				+ recommendationNote + ", makerId=" + makerId + ", makerDate="
				+ makerDate + "]";
	}
}
