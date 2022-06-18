package com.cm.vo;

import org.apache.struts.upload.FormFile;

public class LosUploadVO {
	
	private String docDescription;
	private String fileName;
	private FormFile docFile;	
	private String makerId;
	private String makerDate;
	private String docPath;
	private FormFile docFilee;
	
	
	public String getDocDescription() {
		return docDescription;
	}
	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public FormFile getDocFile() {
		return docFile;
	}
	public void setDocFile(FormFile docFile) {
		this.docFile = docFile;
	}
	public String getMakerId() {
		return makerId;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public String getDocPath() {
		return docPath;
	}
	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	public FormFile getDocFilee() {
		return docFilee;
	}
	public void setDocFilee(FormFile docFilee) {
		this.docFilee = docFilee;
	}

}
