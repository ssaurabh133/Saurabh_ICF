package com.webservice.cp.pdvo;

public class PdSibling
{
	private String siblingType;
	private String siblingName;
	private String siblingMaritalStatus;
	private String siblingBusinessOccupation;
	private String siblingIncome;
	public String getSiblingType() {
		return siblingType;
	}
	public void setSiblingType(String siblingType) {
		this.siblingType = siblingType;
	}
	public String getSiblingName() {
		return siblingName;
	}
	public void setSiblingName(String siblingName) {
		this.siblingName = siblingName;
	}
	public String getSiblingMaritalStatus() {
		return siblingMaritalStatus;
	}
	public void setSiblingMaritalStatus(String siblingMaritalStatus) {
		this.siblingMaritalStatus = siblingMaritalStatus;
	}
	public String getSiblingBusinessOccupation() {
		return siblingBusinessOccupation;
	}
	public void setSiblingBusinessOccupation(String siblingBusinessOccupation) {
		this.siblingBusinessOccupation = siblingBusinessOccupation;
	}
	public String getSiblingIncome() {
		return siblingIncome;
	}
	public void setSiblingIncome(String siblingIncome) {
		this.siblingIncome = siblingIncome;
	}
	@Override
	public String toString() {
		return "PdSibling [siblingType=" + siblingType + ", siblingName="
				+ siblingName + ", siblingMaritalStatus="
				+ siblingMaritalStatus + ", siblingBusinessOccupation="
				+ siblingBusinessOccupation + ", siblingIncome="
				+ siblingIncome + "]";
	}
}