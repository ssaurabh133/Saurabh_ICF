package com.genericUpload.dto;

import java.io.Serializable;

import com.genericUpload.dto.VoucherUploadParametersDTO;

public class TempVoucherProxyDTO extends VoucherUploadParametersDTO implements Serializable{
	
	private static final long serialVersionUID = 5565636217725669742L;
	
	private String serialNo = null;
	private int current_voucher_no;
	
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public int getCurrent_voucher_no() {
		return current_voucher_no;
	}
	public void setCurrent_voucher_no(int current_voucher_no) {
		this.current_voucher_no = current_voucher_no;
	}
	

}
