package com.webservice.cp.pdvo;


public class UserHierarchyRequest {
	
	private UserCredentials userCredentials;
	private String userId;
	private String userName;
	public UserCredentials getUserCredentials() {
		return userCredentials;
	}
	public void setUserCredentials(UserCredentials userCredentials) {
		this.userCredentials = userCredentials;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "UserHierarchyRequest [userCredentials=" + userCredentials
				+ ", userId=" + userId + ", userName=" + userName + "]";
	}
}
