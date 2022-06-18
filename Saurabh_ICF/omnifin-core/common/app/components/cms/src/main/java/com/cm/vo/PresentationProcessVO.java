package com.cm.vo;

import org.apache.struts.upload.FormFile;

public class PresentationProcessVO 
{ 
	private FormFile docFile;
	private String bank;
	private String micr;
	private String ifscCode;
    private String branch;
    private int lbxBankID;
    private int lbxBranchID;  
    private String makerId;
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
	public String getMakerId() {
		return makerId;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	private String makerDate; 
    private String bankAccount;
    private String presentationDate;
    private String fileName;    
	private String DocPath;
    private int companyID;
    
    
	public FormFile getDocFile() {
		return docFile;
	}
	public void setDocFile(FormFile docFile) {
		this.docFile = docFile;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public int getLbxBankID() {
		return lbxBankID;
	}
	public void setLbxBankID(int lbxBankID) {
		this.lbxBankID = lbxBankID;
	}
	public int getLbxBranchID() {
		return lbxBranchID;
	}
	public void setLbxBranchID(int lbxBranchID) {
		this.lbxBranchID = lbxBranchID;
	}
	
	
	public String getMakerDate() {
		return makerDate;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getPresentationDate() {
		return presentationDate;
	}
	public void setPresentationDate(String presentationDate) {
		this.presentationDate = presentationDate;
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
	public int getCompanyID() {
		return companyID;
	}
	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}	
    
    
    
    
}
