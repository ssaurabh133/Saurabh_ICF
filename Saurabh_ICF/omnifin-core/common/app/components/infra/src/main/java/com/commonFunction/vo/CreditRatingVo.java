package com.commonFunction.vo;

import java.io.Serializable;

public class CreditRatingVo implements Serializable{
	private String cRatingId;
	private String institute;
	private String rating;
	private String creditDate;
	private String pageStat;
	private String statusCase;
	private String updateFlag;
	private String makerId;
	private String makerDate;
	
	
	
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
	public String getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}
	public String getStatusCase() {
		return statusCase;
	}
	public void setStatusCase(String statusCase) {
		this.statusCase = statusCase;
	}
	public String getPageStat() {
		return pageStat;
	}
	public void setPageStat(String pageStat) {
		this.pageStat = pageStat;
	}
	public String getcRatingId() {
		return cRatingId;
	}
	public void setcRatingId(String cRatingId) {
		this.cRatingId = cRatingId;
	}
	public String getInstitute() {
		return institute;
	}
	public void setInstitute(String institute) {
		this.institute = institute;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getCreditDate() {
		return creditDate;
	}
	public void setCreditDate(String creditDate) {
		this.creditDate = creditDate;
	}
	
}
