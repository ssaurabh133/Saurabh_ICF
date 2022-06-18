package com.scz.vo;

import java.util.Date;

public class UploadSummaryTempVO {
	int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	int batch_id;
	String file_name;
	String maker_id;
	Date maker_date;
	int no_of_records_rejected;
	int no_of_records_uploaded;
	int instrument_batch_id;
	public int getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(int batchId) {
		batch_id = batchId;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String fileName) {
		file_name = fileName;
	}
	public String getMaker_id() {
		return maker_id;
	}
	public void setMaker_id(String makerId) {
		maker_id = makerId;
	}
	public Date getMaker_date() {
		return maker_date;
	}
	public void setMaker_date(Date makerDate) {
		maker_date = makerDate;
	}
	public int getNo_of_records_rejected() {
		return no_of_records_rejected;
	}
	public void setNo_of_records_rejected(int noOfRecordsRejected) {
		no_of_records_rejected = noOfRecordsRejected;
	}
	public int getNo_of_records_uploaded() {
		return no_of_records_uploaded;
	}
	public void setNo_of_records_uploaded(int noOfRecordsUploaded) {
		no_of_records_uploaded = noOfRecordsUploaded;
	}
	public int getInstrument_batch_id() {
		return instrument_batch_id;
	}
	public void setInstrument_batch_id(int instrumentBatchId) {
		instrument_batch_id = instrumentBatchId;
	}
}
