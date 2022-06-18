package com.cm.actionform;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;


public class CMUploadForm extends ActionForm 
{
	
	private String actionName;
	private int companyId;
	
	private String status;
	
	private String sessionId;
	private String branchId;
	private String businessDate;
	private String radioButton;

	private String branch;
	private Integer lbxBankID;
	private Integer lbxBranchID;
	private String presentationDate;
	private String bankAccount;
	private String makerId;
	private String makerDate;	
	private String fileName;
	private String DocPath;
	private FormFile docFile;	
	private String micr;
	private String ifscCode;
	private String filePathWithName;
	private String lbxPoolID;
    private String poolName;
    private String cutOffDate;
    private String instituteID;
    private String lbxinstituteID;
    private String poolCreationDate;
    private String poolType;
    private String poolID;
	
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public Integer getLbxBankID() {
		return lbxBankID;
	}
	public void setLbxBankID(Integer lbxBankID) {
		this.lbxBankID = lbxBankID;
	}
	public Integer getLbxBranchID() {
		return lbxBranchID;
	}
	public void setLbxBranchID(Integer lbxBranchID) {
		this.lbxBranchID = lbxBranchID;
	}
	public String getPresentationDate() {
		return presentationDate;
	}
	public void setPresentationDate(String presentationDate) {
		this.presentationDate = presentationDate;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDocPath() {
		return DocPath;
	}
	public void setDocPath(String docPath) {
		DocPath = docPath;
	}
	public String getMicr() {
		return micr;
	}
	public void setMicr(String micr) {
		this.micr = micr;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}	

	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getMakerId() {
		return makerId;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public FormFile getDocFile() {
		return docFile;
	}
	public void setDocFile(FormFile docFile) {
		this.docFile = docFile;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}
	public String getBusinessDate() {
		return businessDate;
	}
	public void setRadioButton(String radioButton) {
		this.radioButton = radioButton;
	}
	public String getRadioButton() {
		return radioButton;
	}
	public void setFilePathWithName(String filePathWithName) {
		this.filePathWithName = filePathWithName;
	}
	public String getFilePathWithName() {
		return filePathWithName;
	}
	public String getLbxPoolID() {
		return lbxPoolID;
	}
	public void setLbxPoolID(String lbxPoolID) {
		this.lbxPoolID = lbxPoolID;
	}
	public String getPoolName() {
		return poolName;
	}
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
	public String getCutOffDate() {
		return cutOffDate;
	}
	public void setCutOffDate(String cutOffDate) {
		this.cutOffDate = cutOffDate;
	}
	public String getInstituteID() {
		return instituteID;
	}
	public void setInstituteID(String instituteID) {
		this.instituteID = instituteID;
	}
	public String getLbxinstituteID() {
		return lbxinstituteID;
	}
	public void setLbxinstituteID(String lbxinstituteID) {
		this.lbxinstituteID = lbxinstituteID;
	}
	public String getPoolCreationDate() {
		return poolCreationDate;
	}
	public void setPoolCreationDate(String poolCreationDate) {
		this.poolCreationDate = poolCreationDate;
	}
	public String getPoolType() {
		return poolType;
	}
	public void setPoolType(String poolType) {
		this.poolType = poolType;
	}
	public String getPoolID() {
		return poolID;
	}
	public void setPoolID(String poolID) {
		this.poolID = poolID;
	}
	

}

	