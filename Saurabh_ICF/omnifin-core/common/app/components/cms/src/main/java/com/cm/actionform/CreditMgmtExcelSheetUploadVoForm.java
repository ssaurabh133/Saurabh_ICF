package com.cm.actionform;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;


public class CreditMgmtExcelSheetUploadVoForm extends ActionForm 
{
	private String actionName;
	private String makerDate;
	private int companyId;
	private String makerId;
	private String status;
	private FormFile docFile;
	private String sessionId;
	private String branchId;
	private String businessDate;
	private String radioButton;
	private String filePathWithName;
	
	//for voucherupload maker author
	private String batch_id;
	private String maker_id;
	private String maker_date;
	private String upload_date;
	private String file_name;
	private String comment;
	private String rpt2;
	private String batchId;  // for fore_closure upload_download
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
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public String getMaker_id() {
		return maker_id;
	}
	public void setMaker_id(String maker_id) {
		this.maker_id = maker_id;
	}
	public String getMaker_date() {
		return maker_date;
	}
	public void setMaker_date(String maker_date) {
		this.maker_date = maker_date;
	}
	public String getUpload_date() {
		return upload_date;
	}
	public void setUpload_date(String upload_date) {
		this.upload_date = upload_date;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getRpt2() {
		return rpt2;
	}
	public void setRpt2(String rpt2) {
		this.rpt2 = rpt2;
	}
}

	