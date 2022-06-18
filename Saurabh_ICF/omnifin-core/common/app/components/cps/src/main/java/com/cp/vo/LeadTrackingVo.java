package com.cp.vo;

import java.io.Serializable;

public class LeadTrackingVo implements Serializable{
	private String leadno;
	private String gendate;
	private String leadGenerator;
	private String applicationdate;
	private String scheme;
	private String customername;
	private String product;
	private String rmname;
	private String branch;
	private String vendor;
	
	

    public String getLeadno() {
		return leadno;
	}
	public void setLeadno(String leadno) {
		this.leadno = leadno;
	}
	
	public String getGendate() {
		return gendate;
	}
	public void setGendate(String gendate) {
		this.gendate = gendate;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getProduct() {
		return product;
	}
    public void setProduct(String product) {
		this.product = product;
	}
	public String getRmname() {
		return rmname;
	}
    public void setRmname(String rmname) {
		this.rmname = rmname;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getApplicationdate() {
		return applicationdate;
	}
	public void setApplicationdate(String applicationdate) {
		this.applicationdate = applicationdate;
	}
	public void setLeadGenerator(String leadGenerator) {
		this.leadGenerator = leadGenerator;
	}
	public String getLeadGenerator() {
		return leadGenerator;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getScheme() {
		return scheme;
	}				
}
