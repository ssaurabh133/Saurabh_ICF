package com.masters.vo;

import java.io.Serializable;

public class ruleParamMasterVo implements Serializable{
	
	private String parameterCode;
	private String hiddenparameterCode;
	private String paramName;
	private String parameterStatus;
	private String makerId;
	private String makerDate;
	public int totalRecordSize;
	public int currentPageLink;
	private String sourceTable;
	private String sourceColumn;
	private String dataType;
	private String dataType1;
	private String dataType2;
	private String lbxTables;
	private String lbxColumn;
	
	
	
	public String getParameterCode() {
		return parameterCode;
	}
	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}
	public String getHiddenparameterCode() {
		return hiddenparameterCode;
	}
	public void setHiddenparameterCode(String hiddenparameterCode) {
		this.hiddenparameterCode = hiddenparameterCode;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParameterStatus() {
		return parameterStatus;
	}
	public void setParameterStatus(String parameterStatus) {
		this.parameterStatus = parameterStatus;
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
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public String getSourceTable() {
		return sourceTable;
	}
	public void setSourceTable(String sourceTable) {
		this.sourceTable = sourceTable;
	}
	public String getSourceColumn() {
		return sourceColumn;
	}
	public void setSourceColumn(String sourceColumn) {
		this.sourceColumn = sourceColumn;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDataType1() {
		return dataType1;
	}
	public void setDataType1(String dataType1) {
		this.dataType1 = dataType1;
	}
	public String getDataType2() {
		return dataType2;
	}
	public void setDataType2(String dataType2) {
		this.dataType2 = dataType2;
	}
	public String getLbxTables() {
		return lbxTables;
	}
	public void setLbxTables(String lbxTables) {
		this.lbxTables = lbxTables;
	}
	public String getLbxColumn() {
		return lbxColumn;
	}
	public void setLbxColumn(String lbxColumn) {
		this.lbxColumn = lbxColumn;
	}
	
	

}