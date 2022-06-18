package com.cm.vo;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

/**
 * @author Richa
 *
 */
public class PoolIdMakerVO implements Serializable  {
	 
	 
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String makerID;
	 private String makerDate;
     private String poolID;
	 private FormFile docFile;
     private String lbxPoolID;
     private String poolName;
     private String cutOffDate;
     private String instituteID;
     private String lbxinstituteID;
     private String poolCreationDate;
     private String poolType;
     private String fileName;
	 private String docPath; 
	 
	 
	 private String loanID;
	 private String loanProduct;
	 private String loanScheme;
	 private String loanCustomerID;
	 private String loanCustomerType;
	 private String loanCustomerCtegory;
	 private String loanCustomerConstituion;
	 private String loanCustomerBusinessSeg;
	 private String loanIndustry;
	 private String loanSubIndustry;
	 private String loanDisbursalDate;
	 
	 private String loanMaturityDate;
	 private String loanTenure;
	 private String loanBalanceTenure;
	 private String loanInstallmentNum;
	 
	 private String loanAdvEMINUm;
	 private String loanInitRate;
	 private String loanDisbursalStatus;
	 private String loanNPAFlag;
	 private String loanDPD;
	 
	 private String loanDPDString;
	 private String loanAssetCost;
	 private String laonAmount;
	 
	 private String loanEMI;
	 private String loanAdvEMIAmount;
	 
	 private String loanBalPrincipal;
	 private String loanOverduePrincipal;
	 
	 private String loanReceivedPrincipal;
	 
	 private String loanOverdueInstNo;
	 private String loanOverdueAmount;
	 private String loanBalnceInstNo;
	 private String loanBalInstlAmount;
	 
	 private String loanAmount;
	 private String loanAccNo;
	 private String lbxLoanNoHID;
	 private String  comments;
	 private String  decision;
	 private String authorID;
	 private String authorDate;
	 private String reportingToUserId;
	 private String userName;
	 
	 
	 private String loanInstallmentMode;
	 private String loanAdvanceInstallment;
	 private String modifyNo;

	 private int currentPageLink;
	 private int totalRecordSize;
	 
	 private String checkBoxDis;
	 
	 private String otherInstallmentCharges;
	 private String totalChargeInstallmentAmount;
	 private String repayment;
	 private String filePathWithName;
	 
	 
	 
	 
	 
	 
	 public String getRepayment() {
		return repayment;
	}
	public void setRepayment(String repayment) {
		this.repayment = repayment;
	}
	public String getOtherInstallmentCharges() {
		return otherInstallmentCharges;
	}
	public void setOtherInstallmentCharges(String otherInstallmentCharges) {
		this.otherInstallmentCharges = otherInstallmentCharges;
	}
	public String getTotalChargeInstallmentAmount() {
		return totalChargeInstallmentAmount;
	}
	public void setTotalChargeInstallmentAmount(String totalChargeInstallmentAmount) {
		this.totalChargeInstallmentAmount = totalChargeInstallmentAmount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	private String installmentType;
	 
	 
	 
	 
	public String getReportingToUserId() {
		return reportingToUserId;
	}
	public void setReportingToUserId(String reportingToUserId) {
		this.reportingToUserId = reportingToUserId;
	}
	public String getInstallmentType() {
		return installmentType;
	}
	public void setInstallmentType(String installmentType) {
		this.installmentType = installmentType;
	}
	public String getLoanInstallmentMode() {
		return loanInstallmentMode;
	}
	public void setLoanInstallmentMode(String loanInstallmentMode) {
		this.loanInstallmentMode = loanInstallmentMode;
	}
	public String getLoanAdvanceInstallment() {
		return loanAdvanceInstallment;
	}
	public void setLoanAdvanceInstallment(String loanAdvanceInstallment) {
		this.loanAdvanceInstallment = loanAdvanceInstallment;
	}
	public String getLoanAccNo() {
		return loanAccNo;
	}
	public void setLoanAccNo(String loanAccNo) {
		this.loanAccNo = loanAccNo;
	}
	public String getLbxLoanNoHID() {
		return lbxLoanNoHID;
	}
	public void setLbxLoanNoHID(String lbxLoanNoHID) {
		this.lbxLoanNoHID = lbxLoanNoHID;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getLoanID() {
		return loanID;
	}
	public void setLoanID(String loanID) {
		this.loanID = loanID;
	}
	public String getLoanProduct() {
		return loanProduct;
	}
	public void setLoanProduct(String loanProduct) {
		this.loanProduct = loanProduct;
	}
	public String getLoanScheme() {
		return loanScheme;
	}
	public void setLoanScheme(String loanScheme) {
		this.loanScheme = loanScheme;
	}
	public String getLoanCustomerID() {
		return loanCustomerID;
	}
	public void setLoanCustomerID(String loanCustomerID) {
		this.loanCustomerID = loanCustomerID;
	}
	public String getLoanCustomerType() {
		return loanCustomerType;
	}
	public void setLoanCustomerType(String loanCustomerType) {
		this.loanCustomerType = loanCustomerType;
	}
	public String getLoanCustomerCtegory() {
		return loanCustomerCtegory;
	}
	public void setLoanCustomerCtegory(String loanCustomerCtegory) {
		this.loanCustomerCtegory = loanCustomerCtegory;
	}
	public String getLoanCustomerConstituion() {
		return loanCustomerConstituion;
	}
	public void setLoanCustomerConstituion(String loanCustomerConstituion) {
		this.loanCustomerConstituion = loanCustomerConstituion;
	}
	public String getLoanCustomerBusinessSeg() {
		return loanCustomerBusinessSeg;
	}
	public void setLoanCustomerBusinessSeg(String loanCustomerBusinessSeg) {
		this.loanCustomerBusinessSeg = loanCustomerBusinessSeg;
	}
	public String getLoanIndustry() {
		return loanIndustry;
	}
	public void setLoanIndustry(String loanIndustry) {
		this.loanIndustry = loanIndustry;
	}
	public String getLoanSubIndustry() {
		return loanSubIndustry;
	}
	public void setLoanSubIndustry(String loanSubIndustry) {
		this.loanSubIndustry = loanSubIndustry;
	}
	public String getLoanDisbursalDate() {
		return loanDisbursalDate;
	}
	public void setLoanDisbursalDate(String loanDisbursalDate) {
		this.loanDisbursalDate = loanDisbursalDate;
	}
	public String getLoanMaturityDate() {
		return loanMaturityDate;
	}
	public void setLoanMaturityDate(String loanMaturityDate) {
		this.loanMaturityDate = loanMaturityDate;
	}
	public String getLoanTenure() {
		return loanTenure;
	}
	public void setLoanTenure(String loanTenure) {
		this.loanTenure = loanTenure;
	}
	public String getLoanBalanceTenure() {
		return loanBalanceTenure;
	}
	public void setLoanBalanceTenure(String loanBalanceTenure) {
		this.loanBalanceTenure = loanBalanceTenure;
	}
	public String getLoanInstallmentNum() {
		return loanInstallmentNum;
	}
	public void setLoanInstallmentNum(String loanInstallmentNum) {
		this.loanInstallmentNum = loanInstallmentNum;
	}
	public String getLoanAdvEMINUm() {
		return loanAdvEMINUm;
	}
	public void setLoanAdvEMINUm(String loanAdvEMINUm) {
		this.loanAdvEMINUm = loanAdvEMINUm;
	}
	public String getLoanInitRate() {
		return loanInitRate;
	}
	public void setLoanInitRate(String loanInitRate) {
		this.loanInitRate = loanInitRate;
	}
	public String getLoanDisbursalStatus() {
		return loanDisbursalStatus;
	}
	public void setLoanDisbursalStatus(String loanDisbursalStatus) {
		this.loanDisbursalStatus = loanDisbursalStatus;
	}
	public String getLoanNPAFlag() {
		return loanNPAFlag;
	}
	public void setLoanNPAFlag(String loanNPAFlag) {
		this.loanNPAFlag = loanNPAFlag;
	}
	public String getLoanDPD() {
		return loanDPD;
	}
	public void setLoanDPD(String loanDPD) {
		this.loanDPD = loanDPD;
	}
	public String getLoanDPDString() {
		return loanDPDString;
	}
	public void setLoanDPDString(String loanDPDString) {
		this.loanDPDString = loanDPDString;
	}
	public String getLoanAssetCost() {
		return loanAssetCost;
	}
	public void setLoanAssetCost(String loanAssetCost) {
		this.loanAssetCost = loanAssetCost;
	}
	public String getLaonAmount() {
		return laonAmount;
	}
	public void setLaonAmount(String laonAmount) {
		this.laonAmount = laonAmount;
	}
	public String getLoanEMI() {
		return loanEMI;
	}
	public void setLoanEMI(String loanEMI) {
		this.loanEMI = loanEMI;
	}
	public String getLoanAdvEMIAmount() {
		return loanAdvEMIAmount;
	}
	public void setLoanAdvEMIAmount(String loanAdvEMIAmount) {
		this.loanAdvEMIAmount = loanAdvEMIAmount;
	}
	public String getLoanBalPrincipal() {
		return loanBalPrincipal;
	}
	public void setLoanBalPrincipal(String loanBalPrincipal) {
		this.loanBalPrincipal = loanBalPrincipal;
	}
	public String getLoanOverduePrincipal() {
		return loanOverduePrincipal;
	}
	public void setLoanOverduePrincipal(String loanOverduePrincipal) {
		this.loanOverduePrincipal = loanOverduePrincipal;
	}
	public String getLoanReceivedPrincipal() {
		return loanReceivedPrincipal;
	}
	public void setLoanReceivedPrincipal(String loanReceivedPrincipal) {
		this.loanReceivedPrincipal = loanReceivedPrincipal;
	}
	public String getLoanOverdueInstNo() {
		return loanOverdueInstNo;
	}
	public void setLoanOverdueInstNo(String loanOverdueInstNo) {
		this.loanOverdueInstNo = loanOverdueInstNo;
	}
	public String getLoanOverdueAmount() {
		return loanOverdueAmount;
	}
	public void setLoanOverdueAmount(String loanOverdueAmount) {
		this.loanOverdueAmount = loanOverdueAmount;
	}
	public String getLoanBalnceInstNo() {
		return loanBalnceInstNo;
	}
	public void setLoanBalnceInstNo(String loanBalnceInstNo) {
		this.loanBalnceInstNo = loanBalnceInstNo;
	}
	public String getLoanBalInstlAmount() {
		return loanBalInstlAmount;
	}
	public void setLoanBalInstlAmount(String loanBalInstlAmount) {
		this.loanBalInstlAmount = loanBalInstlAmount;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDocPath() {
		return docPath;
	}
	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	public String getMakerID() {
		return makerID;
	}
	public void setMakerID(String makerID) {
		this.makerID = makerID;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public String getPoolID() {
		return poolID;
	}
	public void setPoolID(String poolID) {
		this.poolID = poolID;
	}
	public FormFile getDocFile() {
		return docFile;
	}
	public void setDocFile(FormFile docFile) {
		this.docFile = docFile;
	}
	public String getLbxPoolID() {
		return lbxPoolID;
	}
	public void setLbxPoolID(String lbxPoolID) {
		this.lbxPoolID = lbxPoolID;
	}
	public String getPoolName() {
		return poolName;
	}
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
	public String getCutOffDate() {
		return cutOffDate;
	}
	public void setCutOffDate(String cutOffDate) {
		this.cutOffDate = cutOffDate;
	}
	public String getInstituteID() {
		return instituteID;
	}
	public void setInstituteID(String instituteID) {
		this.instituteID = instituteID;
	}
	public String getLbxinstituteID() {
		return lbxinstituteID;
	}
	public void setLbxinstituteID(String lbxinstituteID) {
		this.lbxinstituteID = lbxinstituteID;
	}
	public String getPoolCreationDate() {
		return poolCreationDate;
	}
	public void setPoolCreationDate(String poolCreationDate) {
		this.poolCreationDate = poolCreationDate;
	}
	public String getPoolType() {
		return poolType;
	}
	public void setPoolType(String poolType) {
		this.poolType = poolType;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public String getAuthorID() {
		return authorID;
	}
	public void setAuthorID(String authorID) {
		this.authorID = authorID;
	}
	public String getAuthorDate() {
		return authorDate;
	}
	public void setAuthorDate(String authorDate) {
		this.authorDate = authorDate;
	}
	public String getModifyNo() {
		return modifyNo;
	}
	public void setModifyNo(String modifyNo) {
		this.modifyNo = modifyNo;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
  

		public void setCheckBoxDis(String checkBoxDis) {
			this.checkBoxDis = checkBoxDis;
		}
		public String getCheckBoxDis() {
			return checkBoxDis;
		}
		public void setFilePathWithName(String filePathWithName) {
			this.filePathWithName = filePathWithName;
		}
		public String getFilePathWithName() {
			return filePathWithName;
		}  
 
	
	
    
}
