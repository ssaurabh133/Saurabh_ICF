package com.cp.vo;

import java.io.Serializable;

public class RefreshFlagVo implements Serializable{
	private int recordId;
	private int tabIndex;
	private String moduleName;
	private String flagValue;
	private String nonInstallment;
	
	
	
	
	
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
