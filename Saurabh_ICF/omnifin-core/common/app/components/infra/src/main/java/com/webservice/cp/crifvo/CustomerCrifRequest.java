package com.webservice.cp.crifvo;

import java.math.BigDecimal;
import java.util.List;

import com.webservice.cp.cibilvo.CibilAddress;
import com.webservice.cp.cibilvo.CibilDocument;
import com.webservice.cp.cibilvo.CibilName;
import com.webservice.cp.cibilvo.CibilPhone;
import com.webservice.cp.pdvo.UserCredentials;

public class CustomerCrifRequest {

	/*private UserCredentials userCredentials;
	private CustomerCibilRequest cibilRequest;*/
	private String customerName;
	private String source;
	private String txnType;
	private String applicationFormNo;
	private Integer txnId;
	private Integer customerId;
	private Integer referanceId;
	private String makerId;
	private String sourceId;
	/*	private Integer tenure;
	private String emailId1;
	private String emailId2;
	private BigDecimal monthlyIncome;*/
	private Integer cibilId;
	/**
	 * mandatory
	 */
	private String loanType;
	/**
	 * mandatory
	 */
	private BigDecimal loanAmount;
	/**
	 * mandatory
	 * value=object
	 */
	private CibilName name;
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getMakerId() {
		return makerId;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getApplicationFormNo() {
		return applicationFormNo;
	}
	public void setApplicationFormNo(String applicationFormNo) {
		this.applicationFormNo = applicationFormNo;
	}
	public Integer getTxnId() {
		return txnId;
	}
	public void setTxnId(Integer txnId) {
		this.txnId = txnId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getReferanceId() {
		return referanceId;
	}
	public void setReferanceId(Integer referanceId) {
		this.referanceId = referanceId;
	}
	/**
	 * mandatory
	 */
	private String gender;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * mandatory
	 */
	private String birthDate;
	/**
	 * mandatory
	 * value=list 
	 */
	private List<CibilAddress> addressList;
	/**
	 * conditional mandatory
	 * value=object
	 */
	private CibilDocument cibilDocument;
	/**
	 * conditional mandatory
	 * value=object
	 */
	private List<CibilPhone> phoneList;
	
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public CibilDocument getCibilDocument() {
		return cibilDocument;
	}
	public void setCibilDocument(CibilDocument cibilDocument) {
		this.cibilDocument = cibilDocument;
	}
	public List<CibilPhone> getPhoneList() {
		return phoneList;
	}
	public void setPhoneList(List<CibilPhone> phoneList) {
		this.phoneList = phoneList;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public List<CibilAddress> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<CibilAddress> addressList) {
		this.addressList = addressList;
	}
	public CibilName getName() {
		return name;
	}
	public Integer getCibilId() {
		return cibilId;
	}
	public void setCibilId(Integer cibilId) {
		this.cibilId = cibilId;
	}
	public void setName(CibilName name) {
		this.name = name;
	}
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	@Override
	public String toString() {
		return "CustomerCrifRequest [customerName=" + customerName
				+ ", source=" + source + ", txnType=" + txnType
				+ ", applicationFormNo=" + applicationFormNo + ", txnId="
				+ txnId + ", customerId=" + customerId + ", referanceId="
				+ referanceId + ", makerId=" + makerId + ", sourceId="
				+ sourceId + ", cibilId=" + cibilId + ", loanType=" + loanType
				+ ", loanAmount=" + loanAmount + ", name=" + name + ", gender="
				+ gender + ", birthDate=" + birthDate + ", addressList="
				+ addressList + ", cibilDocument=" + cibilDocument
				+ ", phoneList=" + phoneList + "]";
	}
	

	
}