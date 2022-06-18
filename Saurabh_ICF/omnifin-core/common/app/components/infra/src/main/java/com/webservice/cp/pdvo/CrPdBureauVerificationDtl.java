package com.webservice.cp.pdvo;

public class CrPdBureauVerificationDtl {

	private Integer id;
	private Integer pdId;
	private Integer customerId;
	private String applicantName;
	private String cibilScore;
	private String cibilVintage;
	private String noOfTradeLines;
	private String highMarkScore;
	private String deviationDefaultMark;
	

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


	public String getApplicantName() {
		return applicantName;
	}


	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}


	public String getCibilScore() {
		return cibilScore;
	}


	public void setCibilScore(String cibilScore) {
		this.cibilScore = cibilScore;
	}


	public String getCibilVintage() {
		return cibilVintage;
	}


	public void setCibilVintage(String cibilVintage) {
		this.cibilVintage = cibilVintage;
	}


	public String getNoOfTradeLines() {
		return noOfTradeLines;
	}


	public void setNoOfTradeLines(String noOfTradeLines) {
		this.noOfTradeLines = noOfTradeLines;
	}


	public String getHighMarkScore() {
		return highMarkScore;
	}


	public void setHighMarkScore(String highMarkScore) {
		this.highMarkScore = highMarkScore;
	}


	public String getDeviationDefaultMark() {
		return deviationDefaultMark;
	}


	public void setDeviationDefaultMark(String deviationDefaultMark) {
		this.deviationDefaultMark = deviationDefaultMark;
	}


	@Override
	public String toString() {
		return "CrPdBureauVerificationDtl [id=" + id + ", pdId=" + pdId
				+ ", customerId=" + customerId + ", applicantName="
				+ applicantName + ", cibilScore=" + cibilScore
				+ ", cibilVintage=" + cibilVintage + ", noOfTradeLines="
				+ noOfTradeLines + ", highMarkScore=" + highMarkScore
				+ ", deviationDefaultMark=" + deviationDefaultMark + "]";
	}
}
