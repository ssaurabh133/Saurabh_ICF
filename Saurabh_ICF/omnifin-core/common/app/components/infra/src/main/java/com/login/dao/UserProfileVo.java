package com.login.dao;

import java.io.Serializable;

public class UserProfileVo implements Serializable{

	private static final long serialVersionUID = -2902778312432831759L;
	private Integer userID = null;
	private String userName = null;	
	private String userPassword = null;
	private String module_desc =null;
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public void setModule_desc(String module_desc) {
		this.module_desc = module_desc;
	}
	public String getModule_desc() {
		return module_desc;
	}
}