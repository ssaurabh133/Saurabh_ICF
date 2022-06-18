package com.login.actionForm;


import org.apache.struts.action.ActionForm;



public class BranchActionForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2420213738998653091L;

	private String branchId;
	private String branchName;
	private String lbxBranchId;
	
	public String getLbxBranchId() {
		return lbxBranchId;
	}
	public void setLbxBranchId(String lbxBranchId) {
		this.lbxBranchId = lbxBranchId;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}


	

	


 
}
