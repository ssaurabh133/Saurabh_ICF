package com.webservice.cp.pdvo;

public class CrPdResidenceCitiesTownsDtl {

	private Integer id;

	private Integer pdId;

	private Integer yearOfStay;

	private String cityName;

	private String state;

	private String ownedRented;

	private String shiftingReason;

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

	public Integer getYearOfStay() {
		return yearOfStay;
	}

	public void setYearOfStay(Integer yearOfStay) {
		this.yearOfStay = yearOfStay;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOwnedRented() {
		return ownedRented;
	}

	public void setOwnedRented(String ownedRented) {
		this.ownedRented = ownedRented;
	}

	public String getShiftingReason() {
		return shiftingReason;
	}

	public void setShiftingReason(String shiftingReason) {
		this.shiftingReason = shiftingReason;
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
		return "CrPdResidenceCitiesTownsDtl [id=" + id + ", pdId=" + pdId
				+ ", yearOfStay=" + yearOfStay + ", cityName=" + cityName
				+ ", state=" + state + ", ownedRented=" + ownedRented
				+ ", shiftingReason=" + shiftingReason + ", makerDate="
				+ makerDate + ", makerId=" + makerId + "]";
	}
}
