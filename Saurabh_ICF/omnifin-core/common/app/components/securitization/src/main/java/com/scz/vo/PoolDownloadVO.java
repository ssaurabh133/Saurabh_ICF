package com.scz.vo;

import java.sql.Date;

public class PoolDownloadVO {
	private int id;
	private String parameters;
	private String type;
	private Date requestTime;
	private Date processStartTime;
	private Date processEndTime;
	private String status;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	public Date getProcessStartTime() {
		return processStartTime;
	}
	public void setProcessStartTime(Date processStartTime) {
		this.processStartTime = processStartTime;
	}
	public Date getProcessEndTime() {
		return processEndTime;
	}
	public void setProcessEndTime(Date processEndTime) {
		this.processEndTime = processEndTime;
	}
	private String makerID;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMakerID() {
		return makerID;
	}
	public void setMakerID(String makerID) {
		this.makerID = makerID;
	}
	
}
