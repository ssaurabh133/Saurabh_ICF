package com.webservice.cp.cibilvo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CibilDocument {
	
	private String panId;
	
	private String panIssueDate;
	
	private String panExpirationDate;
	
	private String passportId;
	
	private String passportIssueDate;
	
	private String passportExpirationdate;
	
	private String voterId;
	
	private String voterIdIssueDate;
	
	private String voterIdExpirationDate;
	
	private String drivingLicenseNo;
	
	private String drivingLicenseIssueDate;

	private String drivingLicenseExpirationDate;
	
	private String uidNo;
	
	private String universalIdIssueDate;
	
	private String universalIdExpirationDate;
	
	private String rationCard;
	
	private String rationCardIssueDate;

	private String rationCardExpirationDate;
	
	private String idType1;
	
	private String idType1Value;
	
	private String idType2;
	
	private String idType2Value;

	public String getPanId() {
		return panId;
	}

	public void setPanId(String panId) {
		this.panId = panId;
	}

	public String getPanIssueDate() {
		return panIssueDate;
	}

	public void setPanIssueDate(String panIssueDate) {
		this.panIssueDate = panIssueDate;
	}

	public String getPanExpirationDate() {
		return panExpirationDate;
	}

	public void setPanExpirationDate(String panExpirationDate) {
		this.panExpirationDate = panExpirationDate;
	}

	public String getPassportId() {
		return passportId;
	}

	public void setPassportId(String passportId) {
		this.passportId = passportId;
	}

	public String getPassportIssueDate() {
		return passportIssueDate;
	}

	public void setPassportIssueDate(String passportIssueDate) {
		this.passportIssueDate = passportIssueDate;
	}

	public String getPassportExpirationdate() {
		return passportExpirationdate;
	}

	public void setPassportExpirationdate(String passportExpirationdate) {
		this.passportExpirationdate = passportExpirationdate;
	}

	public String getVoterId() {
		return voterId;
	}

	public void setVoterId(String voterId) {
		this.voterId = voterId;
	}

	public String getVoterIdIssueDate() {
		return voterIdIssueDate;
	}

	public void setVoterIdIssueDate(String voterIdIssueDate) {
		this.voterIdIssueDate = voterIdIssueDate;
	}

	public String getVoterIdExpirationDate() {
		return voterIdExpirationDate;
	}

	public void setVoterIdExpirationDate(String voterIdExpirationDate) {
		this.voterIdExpirationDate = voterIdExpirationDate;
	}

	public String getDrivingLicenseNo() {
		return drivingLicenseNo;
	}

	public void setDrivingLicenseNo(String drivingLicenseNo) {
		this.drivingLicenseNo = drivingLicenseNo;
	}

	public String getDrivingLicenseIssueDate() {
		return drivingLicenseIssueDate;
	}

	public void setDrivingLicenseIssueDate(String drivingLicenseIssueDate) {
		this.drivingLicenseIssueDate = drivingLicenseIssueDate;
	}

	public String getDrivingLicenseExpirationDate() {
		return drivingLicenseExpirationDate;
	}

	public void setDrivingLicenseExpirationDate(String drivingLicenseExpirationDate) {
		this.drivingLicenseExpirationDate = drivingLicenseExpirationDate;
	}

	public String getUidNo() {
		return uidNo;
	}

	public void setUidNo(String uidNo) {
		this.uidNo = uidNo;
	}

	public String getUniversalIdIssueDate() {
		return universalIdIssueDate;
	}

	public void setUniversalIdIssueDate(String universalIdIssueDate) {
		this.universalIdIssueDate = universalIdIssueDate;
	}

	public String getUniversalIdExpirationDate() {
		return universalIdExpirationDate;
	}

	public void setUniversalIdExpirationDate(String universalIdExpirationDate) {
		this.universalIdExpirationDate = universalIdExpirationDate;
	}

	public String getRationCard() {
		return rationCard;
	}

	public void setRationCard(String rationCard) {
		this.rationCard = rationCard;
	}

	public String getRationCardIssueDate() {
		return rationCardIssueDate;
	}

	public void setRationCardIssueDate(String rationCardIssueDate) {
		this.rationCardIssueDate = rationCardIssueDate;
	}

	public String getRationCardExpirationDate() {
		return rationCardExpirationDate;
	}

	public void setRationCardExpirationDate(String rationCardExpirationDate) {
		this.rationCardExpirationDate = rationCardExpirationDate;
	}

	public String getIdType1() {
		return idType1;
	}

	public void setIdType1(String idType1) {
		this.idType1 = idType1;
	}

	public String getIdType1Value() {
		return idType1Value;
	}

	public void setIdType1Value(String idType1Value) {
		this.idType1Value = idType1Value;
	}

	public String getIdType2() {
		return idType2;
	}

	public void setIdType2(String idType2) {
		this.idType2 = idType2;
	}

	public String getIdType2Value() {
		return idType2Value;
	}

	public void setIdType2Value(String idType2Value) {
		this.idType2Value = idType2Value;
	}

	@Override
	public String toString() {
		return "ID [panId=" + panId + ", panIssueDate=" + panIssueDate
				+ ", panExpirationDate=" + panExpirationDate + ", passportId="
				+ passportId + ", passportIssueDate=" + passportIssueDate
				+ ", passportExpirationdate=" + passportExpirationdate
				+ ", voterId=" + voterId + ", voterIdIssueDate="
				+ voterIdIssueDate + ", voterIdExpirationDate="
				+ voterIdExpirationDate + ", drivingLicenseNo="
				+ drivingLicenseNo + ", drivingLicenseIssueDate="
				+ drivingLicenseIssueDate + ", drivingLicenseExpirationDate="
				+ drivingLicenseExpirationDate + ", uidNo=" + uidNo
				+ ", universalIdIssueDate=" + universalIdIssueDate
				+ ", universalIdExpirationDate=" + universalIdExpirationDate
				+ ", rationCard=" + rationCard + ", rationCardIssueDate="
				+ rationCardIssueDate + ", rationCardExpirationDate="
				+ rationCardExpirationDate + ", idType1=" + idType1
				+ ", idType1Value=" + idType1Value + ", idType2=" + idType2
				+ ", idType2Value=" + idType2Value + "]";
	}
	
}
