package com.cp.vo;

import java.io.Serializable;

public class CodeDescVo implements Serializable{
	private String id;
	private String name;
	private String cycleDateValue;
	private String cycleDateDesc;
//Abhishek Start
	private String value;
	private String customerDesc;//Abhishek
	private String customerCode;
	private String stageCode;
	private String stageDesc;
	private String paramOperatorCode;
	private String paramOperatorDesc;
	private String description;
	//Abhishek End
	public String getCycleDateValue() {
		return cycleDateValue;
	}
	public void setCycleDateValue(String cycleDateValue) {
		this.cycleDateValue = cycleDateValue;
	}
	public String getCycleDateDesc() {
		return cycleDateDesc;
	}
	public void setCycleDateDesc(String cycleDateDesc) {
		this.cycleDateDesc = cycleDateDesc;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCustomerDesc() {
		return customerDesc;
	}
	public void setCustomerDesc(String customerDesc) {
		this.customerDesc = customerDesc;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getStageCode() {
		return stageCode;
	}
	public void setStageCode(String stageCode) {
		this.stageCode = stageCode;
	}
	public String getStageDesc() {
		return stageDesc;
	}
	public void setStageDesc(String stageDesc) {
		this.stageDesc = stageDesc;
	}
	public String getParamOperatorCode() {
		return paramOperatorCode;
	}
	public void setParamOperatorCode(String paramOperatorCode) {
		this.paramOperatorCode = paramOperatorCode;
	}
	public String getParamOperatorDesc() {
		return paramOperatorDesc;
	}
	public void setParamOperatorDesc(String paramOperatorDesc) {
		this.paramOperatorDesc = paramOperatorDesc;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

}
