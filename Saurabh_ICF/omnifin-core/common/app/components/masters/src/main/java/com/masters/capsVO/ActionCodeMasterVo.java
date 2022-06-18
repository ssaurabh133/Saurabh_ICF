package com.masters.capsVO;

import java.io.Serializable;

public class ActionCodeMasterVo implements Serializable{

	private String codeId;
	private String codeDesc;
	private String codeStatus;
	private String makerId;
	private String makerDate;
	private String codeIdModify;
	private int totalRecordSize;
	private int currentPageLink;
	private String codeSearchId;
	private String codeSearchDesc;
	
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}

	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	
	public String getCodeStatus() {
		return codeStatus;
	}
	public void setCodeStatus(String codeStatus) {
		this.codeStatus = codeStatus;
	}
	public void setCodeIdModify(String codeIdModify) {
		this.codeIdModify = codeIdModify;
	}
	public String getCodeIdModify() {
		return codeIdModify;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getMakerId() {
		return makerId;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}
	public String getCodeDesc() {
		return codeDesc;
	}
	public void setCodeSearchId(String codeSearchId) {
		this.codeSearchId = codeSearchId;
	}
	public String getCodeSearchId() {
		return codeSearchId;
	}
	public void setCodeSearchDesc(String codeSearchDesc) {
		this.codeSearchDesc = codeSearchDesc;
	}
	public String getCodeSearchDesc() {
		return codeSearchDesc;
	}

}
