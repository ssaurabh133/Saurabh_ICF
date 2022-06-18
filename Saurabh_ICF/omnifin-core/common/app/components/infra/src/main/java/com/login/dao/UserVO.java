package com.login.dao;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserVO implements Serializable{
	
	/**
	 * SELECT USER_ID,USER_NAME,USER_EMP_ID,USER_DEPARTMENT,USER_DEF_BRANCH,
				USER_DESIGNATION,USER_PHONE1,USER_PHONE2,USER_EMAIL,USER_PASSWORD
	 */
	private static final long serialVersionUID = 513652783959453952L;
	private String userName;
	private String userId;

	private Timestamp lastLoginTime;
	
	private String passCount;
	private String accountStatus;
	private String password;
	private String businessdate;
	private String lastpassdate;
	private String conpanyName;
	private int companyId;
	private String emailId;
	private String phoneNo;
	private String compName;
	
	
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getUserName() {
		return userName;
	}
	
	public String getUserId() {
		return userId;
	}
	public String setUserId(String userId) {
		return this.userId = userId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	
	
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}

	public String setBusinessdate(String businessdate) {
		return this.businessdate = businessdate;
	}
	public String getBusinessdate() {
		return businessdate;
	}

	public void setLastpassdate(String lastpassdate) {
		this.lastpassdate = lastpassdate;
	}

	public String getLastpassdate() {
		return lastpassdate;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setPassCount(String passCount) {
		this.passCount = passCount;
	}

	public String getPassCount() {
		return passCount;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setConpanyName(String conpanyName) {
		this.conpanyName = conpanyName;
	}

	public String getConpanyName() {
		return conpanyName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getCompName() {
		return compName;
	}

	
}
