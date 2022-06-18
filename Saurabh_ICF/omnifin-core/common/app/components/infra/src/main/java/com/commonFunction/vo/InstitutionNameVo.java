package com.commonFunction.vo;

import java.io.Serializable;

public class InstitutionNameVo implements Serializable{
	private String agencyCode;
	private String agencyDesc;
	public String getAgencyCode() {
		return agencyCode;
	}
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}
	public String getAgencyDesc() {
		return agencyDesc;
	}
	public void setAgencyDesc(String agencyDesc) {
		this.agencyDesc = agencyDesc;
	}
	
}
