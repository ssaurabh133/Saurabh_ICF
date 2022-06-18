package com.cp.vo;

public class PushDataInTargetSheetVo {
	private int id   ;        

	private String fieldName;

	private String targetWorksheetName;

	private String yearType; //-->CY,PY1,PY2

	private String targetWorksheetCell;

	private String value;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getTargetWorksheetName() {
		return targetWorksheetName;
	}

	public void setTargetWorksheetName(String targetWorksheetName) {
		this.targetWorksheetName = targetWorksheetName;
	}

	public String getYearType() {
		return yearType;
	}

	public void setYearType(String yearType) {
		this.yearType = yearType;
	}

	public String getTargetWorksheetCell() {
		return targetWorksheetCell;
	}

	public void setTargetWorksheetCell(String targetWorksheetCell) {
		this.targetWorksheetCell = targetWorksheetCell;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
