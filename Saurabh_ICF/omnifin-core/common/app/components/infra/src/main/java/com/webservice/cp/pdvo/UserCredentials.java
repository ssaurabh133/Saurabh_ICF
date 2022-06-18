package com.webservice.cp.pdvo;

public class UserCredentials {

	private String userId;
	private String userPassword;
	private String source;
	private String sourceId;
	private String initiatedBy;
	private String sourceVersion;
	private String longitude;
	private String latitude;
	private String address;

	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getInitiatedBy() {
		return initiatedBy;
	}
	public void setInitiatedBy(String initiatedBy) {
		this.initiatedBy = initiatedBy;
	}
	public String getSourceVersion() {
		return sourceVersion;
	}
	public void setSourceVersion(String sourceVersion) {
		this.sourceVersion = sourceVersion;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	@Override
	public String toString() {
		return "UserCredentials [userId=" + userId + ", userPassword="
				+ userPassword + ", source=" + source + ", sourceId="
				+ sourceId + ", initiatedBy=" + initiatedBy
				+ ", sourceVersion=" + sourceVersion + ", longitude="
				+ longitude + ", latitude=" + latitude + ", address=" + address
				+ "]";
	}

}
