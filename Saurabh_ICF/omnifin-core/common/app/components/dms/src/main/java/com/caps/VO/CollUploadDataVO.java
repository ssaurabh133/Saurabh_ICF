package com.caps.VO;

import org.apache.struts.upload.FormFile;

public class CollUploadDataVO {
private FormFile uploadFileLoan;
private String makerId;
private String makerDate;
private String fileName;
private String docPath;


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

public FormFile getUploadFileLoan() {
	return uploadFileLoan;
}

public void setUploadFileLoan(FormFile uploadFileLoan) {
	this.uploadFileLoan = uploadFileLoan;
}

}
