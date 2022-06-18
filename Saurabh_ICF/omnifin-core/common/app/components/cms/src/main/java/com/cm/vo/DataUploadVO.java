package com.cm.vo;

import org.apache.struts.upload.FormFile;

public class DataUploadVO 
{
	
	private String docDescription;
	private String fileName;
	private FormFile docFile;	
	private String makerId;
	private String makerDate;
	private String docPath;
	private FormFile docFilee;
	private String checksum;
	private String readFlag;
	private int totalRecordSize;
	private String selectDropDownArr;
	private String userId;
	private String fileDescription;
	private String authorRemarks;
	private String totalTaxAmount;
	private String rowCount;
	
	private String host_loan_no;
	private String host_dealer_code;
	private String host_dealer_bank_acount;
	private String txn_date;
	private String txn_amount;
	private String txn_flag;
	private String xlsrownumber;
	private String error_description;
	private String errorFieldName;
	public String getErrorFieldName() {
		return errorFieldName;
	}
	public void setErrorFieldName(String errorFieldName) {
		this.errorFieldName = errorFieldName;
	}
	
	
	
	
	public String getHost_loan_no() {
		return host_loan_no;
	}
	public void setHost_loan_no(String hostLoanNo) {
		host_loan_no = hostLoanNo;
	}
	public String getHost_dealer_code() {
		return host_dealer_code;
	}
	public void setHost_dealer_code(String hostDealerCode) {
		host_dealer_code = hostDealerCode;
	}
	public String getHost_dealer_bank_acount() {
		return host_dealer_bank_acount;
	}
	public void setHost_dealer_bank_acount(String hostDealerBankAcount) {
		host_dealer_bank_acount = hostDealerBankAcount;
	}
	public String getTxn_date() {
		return txn_date;
	}
	public void setTxn_date(String txnDate) {
		txn_date = txnDate;
	}
	public String getTxn_amount() {
		return txn_amount;
	}
	public void setTxn_amount(String txnAmount) {
		txn_amount = txnAmount;
	}
	public String getTxn_flag() {
		return txn_flag;
	}
	public void setTxn_flag(String txnFlag) {
		txn_flag = txnFlag;
	}
	public String getXlsrownumber() {
		return xlsrownumber;
	}
	public void setXlsrownumber(String xlsrownumber) {
		this.xlsrownumber = xlsrownumber;
	}
	public String getError_description() {
		return error_description;
	}
	public void setError_description(String errorDescription) {
		error_description = errorDescription;
	}
	public String getTotalTaxAmount() {
		return totalTaxAmount;
	}
	public void setTotalTaxAmount(String totalTaxAmount) {
		this.totalTaxAmount = totalTaxAmount;
	}
	public String getRowCount() {
		return rowCount;
	}
	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}
	public String getFileDescription() {
		return fileDescription;
	}
	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}
	public String getAuthorRemarks() {
		return authorRemarks;
	}
	public void setAuthorRemarks(String authorRemarks) {
		this.authorRemarks = authorRemarks;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSelectDropDownArr() {
		return selectDropDownArr;
	}
	public void setSelectDropDownArr(String selectDropDownArr) {
		this.selectDropDownArr = selectDropDownArr;
	}
	public String getReadFlag() {
		return readFlag;
	}
	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}
	
		
	
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public String getChecksum() {
		return checksum;
	}
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	
	public FormFile getDocFilee() {
		return docFilee;
	}
	public void setDocFilee(FormFile docFilee) {
		this.docFilee = docFilee;
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
	public String getMakerDate() {
		return makerDate;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	
	
	public String getDocDescription() {
		return docDescription;
	}
	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public FormFile getDocFile() {
		return docFile;
	}
	public void setDocFile(FormFile docFile) {
		this.docFile = docFile;
	}
	
	
	

}
