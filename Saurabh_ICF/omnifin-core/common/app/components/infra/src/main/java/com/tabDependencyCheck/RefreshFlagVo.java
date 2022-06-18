package com.tabDependencyCheck;

public class RefreshFlagVo {
	private int recordId;
	private int tabIndex;
	private String moduleName;
	private String flagValue;
	private String nonInstallment;
	private String customerType;
	private String costomerID;
	private String deleteFlag;
	
	
	
	
	
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getCostomerID() {
		return costomerID;
	}
	public void setCostomerID(String costomerID) {
		this.costomerID = costomerID;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getNonInstallment() {
		return nonInstallment;
	}
	public void setNonInstallment(String nonInstallment) {
		this.nonInstallment = nonInstallment;
	}
	public String getFlagValue() {
		return flagValue;
	}
	public void setFlagValue(String flagValue) {
		this.flagValue = flagValue;
	}
	public int getRecordId() {
		return recordId;
	}
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	public int getTabIndex() {
		return tabIndex;
	}
	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	

}
