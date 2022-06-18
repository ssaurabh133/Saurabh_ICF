package com.commonFunction.vo;

import java.io.Serializable;

public class CustomerCategoryVO implements Serializable {
	
	private String customerCategoryCode;
	private String customerCategoryDesc;
	private String castCategoryCode;
	private String castCategoryDesc;
	
	public String getCastCategoryCode() {
		return castCategoryCode;
	}
	public void setCastCategoryCode(String castCategoryCode) {
		this.castCategoryCode = castCategoryCode;
	}
	public String getCastCategoryDesc() {
		return castCategoryDesc;
	}
	public void setCastCategoryDesc(String castCategoryDesc) {
		this.castCategoryDesc = castCategoryDesc;
	}
	public String getCustomerCategoryCode() {
		return customerCategoryCode;
	}
	public void setCustomerCategoryCode(String customerCategoryCode) {
		this.customerCategoryCode = customerCategoryCode;
	}
	public String getCustomerCategoryDesc() {
		return customerCategoryDesc;
	}
	public void setCustomerCategoryDesc(String customerCategoryDesc) {
		this.customerCategoryDesc = customerCategoryDesc;
	}
	
	
	

}
