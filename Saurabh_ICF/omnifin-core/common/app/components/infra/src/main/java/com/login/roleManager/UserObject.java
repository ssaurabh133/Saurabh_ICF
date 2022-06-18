package com.login.roleManager;

import java.io.Serializable;

public class UserObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6575053057913980251L;
	private String userName;
	private String userId;

	private String lastLoginTime;
	private String businessdate;
	private String branchId;
	private String branchName;
	private String totalexpirydays;
	private String passexpirydays;
	private String warningdays;
	private String conpanyName;
	private int companyId;
	private int roleID;
	private String accountStatus;
	private String question1;
	private String question2;
	private String answer1;
	private String answer2;
	
	private String recStatus;
	private String accountExpiryDay;
	private String userAccWarningDays;
	private String name;
	private String type;
	private String companyCode;
	private String companyShortCode;
	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
	
	
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	public void setBusinessdate(String businessdate) {
		this.businessdate = businessdate;
	}
	public String getBusinessdate() {
		return businessdate;
	}
	
	
	public void setTotalexpirydays(String totalexpirydays) {
		this.totalexpirydays = totalexpirydays;
	}
	public String getTotalexpirydays() {
		return totalexpirydays;
	}
	public void setPassexpirydays(String passexpirydays) {
		this.passexpirydays = passexpirydays;
	}
	public String getPassexpirydays() {
		return passexpirydays;
	}
	public void setWarningdays(String warningdays) {
		this.warningdays = warningdays;
	}
	public String getWarningdays() {
		return warningdays;
	}
	
	
	public int setCompanyId(int companyId) {
		return this.companyId = companyId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public String setBranchId(String branchId) {
		return this.branchId = branchId;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchName() {
		return branchName;
	}
	public String setConpanyName(String conpanyName) {
		return this.conpanyName = conpanyName;

	}
	public String getConpanyName() {
		return conpanyName;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	public int getRoleID() {
		return roleID;
	}
	
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setQuestion1(String question1) {
		this.question1 = question1;
	}
	public String getQuestion1() {
		return question1;
	}
	public void setQuestion2(String question2) {
		this.question2 = question2;
	}
	public String getQuestion2() {
		return question2;
	}
	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}
	public String getAnswer1() {
		return answer1;
	}
	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}
	public String getAnswer2() {
		return answer2;
	}
	
	public void setUserAccWarningDays(String userAccWarningDays) {
		this.userAccWarningDays = userAccWarningDays;
	}
	public String getUserAccWarningDays() {
		return userAccWarningDays;
	}
	public void setAccountExpiryDay(String accountExpiryDay) {
		this.accountExpiryDay = accountExpiryDay;
	}
	public String getAccountExpiryDay() {
		return accountExpiryDay;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompanyShortCode() {
		return companyShortCode;
	}
	public void setCompanyShortCode(String companyShortCode) {
		this.companyShortCode = companyShortCode;
	}

		// TODO Auto-generated method stub
		
	}
	
	
	
		

