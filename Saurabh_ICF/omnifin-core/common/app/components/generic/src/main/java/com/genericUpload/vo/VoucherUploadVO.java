package com.genericUpload.vo;

public class VoucherUploadVO
{
	private String batch_id;
	private String upload_type;
	private String upload_date;
	private String Maker_id;
	private String maker_date;
	private String author_id;
	private String author_date;
	private String status;
	private String No_of_vouchers;
	private String file_name;
	private String summary1;
	private String summary2;
	private String status_msg;
	
	
	
	
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public String getUpload_type() {
		return upload_type;
	}
	public void setUpload_type(String upload_type) {
		this.upload_type = upload_type;
	}
	
	public String getUpload_date() {
		return upload_date;
	}
	public void setUpload_date(String upload_date) {
		this.upload_date = upload_date;
	}
	public String getMaker_id() {
		return Maker_id;
	}
	public void setMaker_id(String maker_id) {
		Maker_id = maker_id;
	}
	public String getMaker_date() {
		return maker_date;
	}
	public void setMaker_date(String maker_date) {
		this.maker_date = maker_date;
	}
	public String getNo_of_vouchers() {
		return No_of_vouchers;
	}
	public void setNo_of_vouchers(String no_of_vouchers) {
		No_of_vouchers = no_of_vouchers;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getSummary1() {
		return summary1;
	}
	public void setSummary1(String summary1) {
		this.summary1 = summary1;
	}
	public String getSummary2() {
		return summary2;
	}
	public void setSummary2(String summary2) {
		this.summary2 = summary2;
	}
	public String getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(String author_id) {
		this.author_id = author_id;
	}
	public String getAuthor_date() {
		return author_date;
	}
	public void setAuthor_date(String author_date) {
		this.author_date = author_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		//'B','R','W'
		this.status = status;
	}
	public String getStatus_msg() 
	{
		if(status.equalsIgnoreCase("B") )
		{
			this.status_msg = "Sent Back";
		}
		
		if(status.equalsIgnoreCase("R") )
		{
			this.status_msg = "Rejected";
		}
		
		if(status.equalsIgnoreCase("W"))
		{
			this.status_msg = "Auth. In Process";
		}
		
		if(status.equalsIgnoreCase("EK"))
		{
			this.status_msg = "Error In Sheet Format";
		}
		
		if(status.equalsIgnoreCase("Ep"))
		{
			this.status_msg = "Error In Validation";
		}
		
		if(status.equalsIgnoreCase("p"))
		{
			this.status_msg = "Pending";
		}
		
		if(status.equalsIgnoreCase("F"))
		{
			this.status_msg = "Forwarded";
		}
		
		
		return status_msg;
	}
	public void setStatus_msg(String status_msg) 
	{
		this.status_msg = status_msg;
	}
	
	
	
	
	
	
	
}


