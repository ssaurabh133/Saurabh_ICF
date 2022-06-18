package com.VO;

import java.io.Serializable;

public class CustomerCategoryVO implements Serializable {
	
	private String customerCategoryCode;
	private String customerCategoryDesc;
	private String castCategoryCode;
	private String castCategoryDesc;
	private String genderCode;
	private String genderDesc;
	private String eduDetailDesc;
	private String eduDetailCode;

	public String getEduDetailDesc() {
		return eduDetailDesc;
	}
	public void setEduDetailDesc(String eduDetailDesc) {
		this.eduDetailDesc = eduDetailDesc;
	}
	public String getEduDetailCode() {
		return eduDetailCode;
	}
	public void setEduDetailCode(String eduDetailCode) {
		this.eduDetailCode = eduDetailCode;
	}
	public String getGenderCode() {
		return genderCode;
	}
	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}
	public String getGenderDesc() {
		return genderDesc;
	}
	public void setGenderDesc(String genderDesc) {
		this.genderDesc = genderDesc;
	}
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
