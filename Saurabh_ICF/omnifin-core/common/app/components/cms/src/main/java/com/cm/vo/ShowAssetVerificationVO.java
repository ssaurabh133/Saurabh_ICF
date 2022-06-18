package com.cm.vo;

public class ShowAssetVerificationVO {
	 
	 
	 private String assetID; 
	 private String assetDescription;
	 
		 private String assetCost;
		 private String assetManufaturer;
		 private String assetSupplier;

		 private String recStatus;
		 private String loanAccNo;
		 private String loanID;
			

	
	
	public String getLoanID() {
			return loanID;
		}
		public void setLoanID(String loanID) {
			this.loanID = loanID;
		}
	public String getAssetCost() {
		return assetCost;
	}
	public void setAssetCost(String assetCost) {
		this.assetCost = assetCost;
	}
	public String getAssetManufaturer() {
		return assetManufaturer;
	}
	public void setAssetManufaturer(String assetManufaturer) {
		this.assetManufaturer = assetManufaturer;
	}
	public String getAssetSupplier() {
		return assetSupplier;
	}
	public void setAssetSupplier(String assetSupplier) {
		this.assetSupplier = assetSupplier;
	}
	
	
	
	
	
	
	public String getLoanAccNo() {
		return loanAccNo;
	}
	public void setLoanAccNo(String loanAccNo) {
		this.loanAccNo = loanAccNo;
	}
	
	public String getAssetID() {
		return assetID;
	}
	public void setAssetID(String assetID) {
		this.assetID = assetID;
	}
	public String getAssetDescription() {
		return assetDescription;
	}
	public void setAssetDescription(String assetDescription) {
		this.assetDescription = assetDescription;
	}
	
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public String getRecStatus() {
		return recStatus;
	}
	
	
	
  
    
}
