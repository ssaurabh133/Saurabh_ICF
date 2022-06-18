package com.masters.vo;

public class scoreCardMasterVo {
	
	private String scoreCardId;
	private String scoreCardDesc;	
	private String hiddenScoreCode;
	private String productId;
	private String cardStatus;
	private String makerId;
	private String makerDate;
	public int totalRecordSize;
	public int currentPageLink;
	private String lbxProductID;
	
	
	public String getLbxProductID() {
		return lbxProductID;
	}
	public void setLbxProductID(String lbxProductID) {
		this.lbxProductID = lbxProductID;
	}
	public String getHiddenScoreCode() {
		return hiddenScoreCode;
	}
	public void setHiddenScoreCode(String hiddenScoreCode) {
		this.hiddenScoreCode = hiddenScoreCode;
	}
	public String getScoreCardId() {
		return scoreCardId;
	}
	public void setScoreCardId(String scoreCardId) {
		this.scoreCardId = scoreCardId;
	}
	public String getScoreCardDesc() {
		return scoreCardDesc;
	}
	public void setScoreCardDesc(String scoreCardDesc) {
		this.scoreCardDesc = scoreCardDesc;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
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
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	
	

	
		
}
