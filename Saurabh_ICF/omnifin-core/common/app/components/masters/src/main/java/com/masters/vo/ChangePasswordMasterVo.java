package com.masters.vo;

import java.io.Serializable;

public class ChangePasswordMasterVo implements Serializable{
	private String userId;
	private String lbxUserId;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	private String makerDate;
	private String ques1;
	private String ans1;
	private String ques2;
	private String ans2;
	//kanika code
	private String passwordDate;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLbxUserId() {
		return lbxUserId;
	}
	public void setLbxUserId(String lbxUserId) {
		this.lbxUserId = lbxUserId;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setPasswordDate(String passwordDate) {
		this.passwordDate = passwordDate;
	}
	public String getPasswordDate() {
		return passwordDate;
	}
	public void setQues1(String ques1) {
		this.ques1 = ques1;
	}
	public String getQues1() {
		return ques1;
	}
	public void setAns1(String ans1) {
		this.ans1 = ans1;
	}
	public String getAns1() {
		return ans1;
	}
	public void setQues2(String ques2) {
		this.ques2 = ques2;
	}
	public String getQues2() {
		return ques2;
	}
	public void setAns2(String ans2) {
		this.ans2 = ans2;
	}
	public String getAns2() {
		return ans2;
	}
	
}
