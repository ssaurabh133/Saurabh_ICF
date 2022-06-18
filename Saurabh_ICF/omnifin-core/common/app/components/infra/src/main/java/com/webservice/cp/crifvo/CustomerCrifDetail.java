package com.webservice.cp.crifvo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@XmlRootElement(name="CustomerCrifDetail")
@Entity
@Table(name= "CR_CRIF_DETAIL")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CustomerCrifDetail{

	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name = "CRIF_ID", unique = true, nullable = false)
	private Integer crifId;
	
	//@Column(name="DEAL_ID")
	private Integer dealId;
	
	//@Column(name="CUSTOMER_ID")
	private Integer customerId;
	
	//@Column(name="CUSTOMER_NAME")
	private String customerName;
	
	//@Column(name="USER_ID")
	private String userId;
	
	//@Column(name="PASSWORD")
	private String password;
	
	//@Column(name="PRODUCTTYPE")
	private String productType;
	
	//@Column(name="PRODUCTVERSION")
	private String productVersion;
	
	//@Column(name="MBRID")
	private String mbrId;
	
	//@Column(name="REPORT_ID")
	private String reportId;
	
	//@Column(name="INQUIRY_UNIQUE_REF_NO")
	private String inquiryUniqueRefNo;
	
	//@Column(name="REQVOLTYPE")
	private String reqvolType;
	
	//@JsonIgnore
	//@Column(name="REQUESTXML")
	private String requestXml;
	
	//@JsonIgnore
	//@Column(name="RESPONSEXML")
	private String responseXml;
	
	//@Column(name="FINALREQUESTXML")
	private String finalRequestXml;
	
	//@JsonIgnore
	//@Column(name="FINALRESPONSEXML")
	private String finalResponseXml;
	
	//@Column(name="SCORE")
	private String score;
	
	//@Column(name="MAKER_ID")
	private String makerId;
	
	//@JsonIgnore
	//@Column(name="MAKER_DATE")
	private String makerDate;
	
	//@Column(name="AUTHOR_ID")
	private String authorId;
	
	//@JsonIgnore
	//@Column(name="AUTHOR_DATE")
	private String authorDate;
	
	//@Column(name="REC_STATUS")
	private String recStatus;
	
	//@Column(name="REPORT_PATH")
	private String reportPath;
	
	//@Column(name="SOURCE_NAME")
	private String sourceName;
	
	//@Column(name="REPORT_NAME")
	private String reportName;

	//@Transient
	private String htmlStream;

	public Integer getCrifId() {
		return crifId;
	}

	public void setCrifId(Integer crifId) {
		this.crifId = crifId;
	}

	public Integer getDealId() {
		return dealId;
	}

	public void setDealId(Integer dealId) {
		this.dealId = dealId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}

	public String getMbrId() {
		return mbrId;
	}

	public void setMbrId(String mbrId) {
		this.mbrId = mbrId;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getInquiryUniqueRefNo() {
		return inquiryUniqueRefNo;
	}

	public void setInquiryUniqueRefNo(String inquiryUniqueRefNo) {
		this.inquiryUniqueRefNo = inquiryUniqueRefNo;
	}

	public String getReqvolType() {
		return reqvolType;
	}

	public void setReqvolType(String reqvolType) {
		this.reqvolType = reqvolType;
	}

	public String getRequestXml() {
		return requestXml;
	}

	public void setRequestXml(String requestXml) {
		this.requestXml = requestXml;
	}

	public String getResponseXml() {
		return responseXml;
	}

	public void setResponseXml(String responseXml) {
		this.responseXml = responseXml;
	}

	public String getFinalRequestXml() {
		return finalRequestXml;
	}

	public void setFinalRequestXml(String finalRequestXml) {
		this.finalRequestXml = finalRequestXml;
	}

	public String getFinalResponseXml() {
		return finalResponseXml;
	}

	public void setFinalResponseXml(String finalResponseXml) {
		this.finalResponseXml = finalResponseXml;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
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

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getAuthorDate() {
		return authorDate;
	}

	public void setAuthorDate(String authorDate) {
		this.authorDate = authorDate;
	}

	public String getRecStatus() {
		return recStatus;
	}

	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}

	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getHtmlStream() {
		return htmlStream;
	}

	public void setHtmlStream(String htmlStream) {
		this.htmlStream = htmlStream;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	@Override
	public String toString() {
		return "CustomerCrifDetail [crifId=" + crifId + ", dealId=" + dealId
				+ ", customerId=" + customerId + ", customerName="
				+ customerName + ", userId=" + userId + ", password="
				+ password + ", productType=" + productType
				+ ", productVersion=" + productVersion + ", mbrId=" + mbrId
				+ ", reportId=" + reportId + ", inquiryUniqueRefNo="
				+ inquiryUniqueRefNo + ", reqvolType=" + reqvolType
				+ ", requestXml=" + requestXml + ", responseXml=" + responseXml
				+ ", finalRequestXml=" + finalRequestXml
				+ ", finalResponseXml=" + finalResponseXml + ", score=" + score
				+ ", makerId=" + makerId + ", makerDate=" + makerDate
				+ ", authorId=" + authorId + ", authorDate=" + authorDate
				+ ", recStatus=" + recStatus + ", reportPath=" + reportPath
				+ ", sourceName=" + sourceName + ", reportName=" + reportName
				+ ", htmlStream=" + htmlStream + "]";
	}
	
	
		

	
}