package com.cp.vo;

import com.webservice.cp.pdvo.UserCredentials;


public class ExperianHunterRequestWrapper {
	
	private UserCredentials userCredentials;
	private Integer dealId;
	private String selectedCustomers;
	private String selectedCorporateCustomers;
	
	public Integer getDealId() {
		return dealId;
	}
	public void setDealId(Integer dealId) {
		this.dealId = dealId;
	}
	
	public UserCredentials getUserCredentials() {
		return userCredentials;
	}
	public void setUserCredentials(UserCredentials userCredentials) {
		this.userCredentials = userCredentials;
	}

	public String getSelectedCustomers() {
		return selectedCustomers;
	}
	public String getSelectedCorporateCustomers() {
		return selectedCorporateCustomers;
	}
	public void setSelectedCorporateCustomers(String selectedCorporateCustomers) {
		this.selectedCorporateCustomers = selectedCorporateCustomers;
	}
	public void setSelectedCustomers(String selectedCustomers) {
		this.selectedCustomers = selectedCustomers;
	}
	@Override
	public String toString() {
		return "ExperianHunterRequestWrapper [userCredentials="
				+ userCredentials + ", dealId=" + dealId
				+ ", selectedCustomers=" + selectedCustomers
				+ ", selectedCorporateCustomers=" + selectedCorporateCustomers
				+ "]";
	}
}
	