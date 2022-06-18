package com.scz.vo;

import org.apache.struts.upload.FormFile;

public class BuyBackUploadVO {

	private int id;
	private String makerID;
	private String makerDate;
	private int currentPageLink;
	FormFile docFile;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMakerID() {
		return makerID;
	}
	public void setMakerID(String makerID) {
		this.makerID = makerID;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public FormFile getDocFile() {
		return docFile;
	}
	public void setDocFile(FormFile docFile) {
		this.docFile = docFile;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDocPath() {
		return docPath;
	}
	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	public String getFilePathWithName() {
		return filePathWithName;
	}
	public void setFilePathWithName(String filePathWithName) {
		this.filePathWithName = filePathWithName;
	}
	private String fileName;
	private String docPath;
	private String filePathWithName;
	
}
