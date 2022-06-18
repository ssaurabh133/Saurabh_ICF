package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class CrPdResidenceHouseFacilitiesDtl {
	
	private Integer id;

	private Integer pdId;

	private Integer noOfRoom;

	private String constructionQuality;

	private String separateToilet;

	private Integer noOfFloors;

	private BigDecimal totalPlotArea;

	private BigDecimal totalCoveredArea;

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

	public Integer getNoOfRoom() {
		return noOfRoom;
	}

	public void setNoOfRoom(Integer noOfRoom) {
		this.noOfRoom = noOfRoom;
	}

	public String getConstructionQuality() {
		return constructionQuality;
	}

	public void setConstructionQuality(String constructionQuality) {
		this.constructionQuality = constructionQuality;
	}

	public String getSeparateToilet() {
		return separateToilet;
	}

	public void setSeparateToilet(String separateToilet) {
		this.separateToilet = separateToilet;
	}

	public Integer getNoOfFloors() {
		return noOfFloors;
	}

	public void setNoOfFloors(Integer noOfFloors) {
		this.noOfFloors = noOfFloors;
	}

	public BigDecimal getTotalPlotArea() {
		return totalPlotArea;
	}

	public void setTotalPlotArea(BigDecimal totalPlotArea) {
		this.totalPlotArea = totalPlotArea;
	}

	public BigDecimal getTotalCoveredArea() {
		return totalCoveredArea;
	}

	public void setTotalCoveredArea(BigDecimal totalCoveredArea) {
		this.totalCoveredArea = totalCoveredArea;
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
		return "CrPdResidenceHouseFacilitiesDtl [id=" + id + ", pdId=" + pdId
				+ ", noOfRoom=" + noOfRoom + ", constructionQuality="
				+ constructionQuality + ", separateToilet=" + separateToilet
				+ ", noOfFloors=" + noOfFloors + ", totalPlotArea="
				+ totalPlotArea + ", totalCoveredArea=" + totalCoveredArea
				+ ", makerDate=" + makerDate + ", makerId=" + makerId + "]";
	}
}
