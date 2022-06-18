package com.login.actionForm;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.logger.LoggerMsg;

public class LoginActionForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2420213738998653091L;

	private String userName;
	private String userPassword;
	private String name;
	private String password;
	private String recStatus;
	private String branch;
	private String branchID;
	private String branchDesc;
	private String question;
	private String quest;
	private String ans_des;
	private String ques1;
	private String ques2;
	private String ans1;
	private String ans2;
	private String lbxBranchId;
	private String saltPass;

	public String getSaltPass() {
		return saltPass;
	}

	public void setSaltPass(String saltPass) {
		this.saltPass = saltPass;
	}

	public String getBranchID() {
		return branchID;
	}

	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}

	public String getBranchDesc() {
		return branchDesc;
	}

	public void setBranchDesc(String branchDesc) {
		this.branchDesc = branchDesc;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		ActionErrors actionErrors = new ActionErrors();

		if (userName == null || userName.trim().equals("")) {
			actionErrors.add("userName", new ActionMessage("error.username"));
		}
		try {
			if (userPassword == null || userPassword.trim().equals("")) {
				actionErrors.add("password",
						new ActionMessage("error.password"));
			}
		} catch (Exception e) {
			LoggerMsg.debug(e.getMessage());
		}
		return actionErrors;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.userName = null;
		this.userPassword = null;
		
	}

	public String setRecStatus(String recStatus) {
		return this.recStatus = recStatus;
	}

	public String getRecStatus() {
		return recStatus;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getBranch() {
		return branch;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuest(String quest) {
		this.quest = quest;
	}

	public String getQuest() {
		return quest;
	}

	public void setAns_des(String ans_des) {
		this.ans_des = ans_des;
	}

	public String getAns_des() {
		return ans_des;
	}

	public void setQues2(String ques2) {
		this.ques2 = ques2;
	}

	public String getQues2() {
		return ques2;
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

	public void setAns2(String ans2) {
		this.ans2 = ans2;
	}

	public String getAns2() {
		return ans2;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	
	public void setLbxBranchId(String lbxBranchId) {
		this.lbxBranchId = lbxBranchId;
	}

	public String getLbxBranchId() {
		return lbxBranchId;
	}

}
