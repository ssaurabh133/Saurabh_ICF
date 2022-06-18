package com.webservice.cp.crifvo;

import java.util.List;

public class CrifVO {
	 private String requestXML;
	 private String userId;
	 private String password;
	 private String mbrid;
	 private String productType;
	 private String productVersion;
	 private String reqVolType;
	 private String issueReqXml;
	 private String crifId;
	 private String makerId;
	 private String makerDate;
	
	public String getRequestXML() {
		return requestXML;
	}
	public void setRequestXML(String requestXML) {
		this.requestXML = requestXML;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMbrid() {
		return mbrid;
	}
	public void setMbrid(String mbrid) {
		this.mbrid = mbrid;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductVersion() {
		return productVersion;
	}
	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}
	public String getReqVolType() {
		return reqVolType;
	}
	public void setReqVolType(String reqVolType) {
		this.reqVolType = reqVolType;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getIssueReqXml() {
		return issueReqXml;
	}
	public void setIssueReqXml(String issueReqXml) {
		this.issueReqXml = issueReqXml;
	}
	//@Override
	/*public String toString() {
		return "CrifVO [requestXML=" + requestXML + ", userId=" + userId
				+ ", password=" + password + ", mbrid=" + mbrid
				+ ", productType=" + productType + ", productVersion="
				+ productVersion + ", reqVolType=" + reqVolType + "]";
	}*/
	@Override
	public String toString() {
		return "CrifVO [requestXML=" + requestXML + ", userId=" + userId
				+ ", password=" + password + ", mbrid=" + mbrid
				+ ", productType=" + productType + ", productVersion="
				+ productVersion + ", reqVolType=" + reqVolType
				+ ", issueReqXml=" + issueReqXml + "]";
	}
	public String getCrifId() {
		return crifId;
	}
	public void setCrifId(String crifId) {
		this.crifId = crifId;
	}
	public String getMakerId() {
		return makerId;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}

}

