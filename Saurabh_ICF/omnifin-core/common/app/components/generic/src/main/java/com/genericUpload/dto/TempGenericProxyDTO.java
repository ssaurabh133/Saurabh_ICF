package com.genericUpload.dto;

import java.io.Serializable;



public class TempGenericProxyDTO extends GenericUploadParametersDTO implements Serializable{
	
	private static final long serialVersionUID = 5565636217725669742L;
	
	private String serialNo = null;
	private int current_record_no;
	
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public int getCurrent_record_no() {
		return current_record_no;
	}
	public void setCurrent_record_no(int current_record_no) {
		this.current_record_no = current_record_no;
	}
	
	

}
