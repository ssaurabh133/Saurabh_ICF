package com.scz.vo;

import com.scz.DateFormator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.struts.upload.FormFile;

public class PoolIdMakerVO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int id;
  private int hid;
  private String makerID;
  private int poolID;
  private String temp;
  private FormFile docFile;
  private String lbxPoolID;
  private String poolName;
  private String instituteID;
  private String lbxinstituteID;
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
  private String comments;
  private String decision;
  private String authorID;
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
  private String assignType;
  private String makerDate;
  private String authorDate;
  private String poolApprovalDate;
  private Date makerDate1 = DateFormator.dmyToSQL("00-00-0000");
  private Date authorDate1 = DateFormator.dmyToSQL("00-00-0000");
  private Date cutOffDate1 = DateFormator.dmyToSQL("00-00-0000");
  private Date poolCreationDate1 = DateFormator.dmyToSQL("00-00-0000");
  private Date assignDate1 = DateFormator.dmyToSQL("00-00-0000");
  private Date poolApprovalDate1 = DateFormator.dmyToSQL("00-00-0000");
  private String percentage;
  private String assignmentPercentage;
  private String investmentRatio;
  private String cutOffDate;
  private String poolCreationDate;
  private String assignDate;
  private BigDecimal dealAmount = new BigDecimal(0);
  private BigDecimal interestRate = new BigDecimal(0);

  private String creditEnhancement = "N";
  private String multipleInvestor = "N";
  private String poolDescription;
  private String recStatus;
  private String makerRemark;
  private String authorRemark;
  private String installmentType;

  public String getPercentage()
  {
    return this.percentage;
  }
  public void setPercentage(String percentage) {
    this.percentage = percentage;
  }
  public String getAssignmentPercentage() {
    return this.assignmentPercentage;
  }
  public void setAssignmentPercentage(String assignmentPercentage) {
    this.assignmentPercentage = assignmentPercentage;
  }
  public String getInvestmentRatio() {
    return this.investmentRatio;
  }
  public void setInvestmentRatio(String investmentRatio) {
    this.investmentRatio = investmentRatio;
  }

  public String getAuthorDate() {
    return this.authorDate;
  }
  public void setAuthorDate(String authorDate) {
    this.authorDate = authorDate;
  }

  public String getPoolApprovalDate() {
    return this.poolApprovalDate;
  }
  public void setPoolApprovalDate(String poolApprovalDate) {
    this.poolApprovalDate = poolApprovalDate;
  }
  public Date getPoolApprovalDate1() {
    return this.poolApprovalDate1;
  }
  public void setPoolApprovalDate1(Date poolApprovalDate1) {
    this.poolApprovalDate1 = poolApprovalDate1;
  }
  public String getMakerDate() {
    return this.makerDate;
  }
  public void setMakerDate(String makerDate) {
    this.makerDate = makerDate;
  }
  public Date getMakerDate1() {
    return this.makerDate1;
  }
  public void setMakerDate1(Date makerDate1) {
    this.makerDate1 = makerDate1;
  }
  public Date getAuthorDate1() {
    return this.authorDate1;
  }
  public void setAuthorDate1(Date authorDate1) {
    this.authorDate1 = authorDate1;
  }

  public Date getCutOffDate1()
  {
    return this.cutOffDate1;
  }
  public void setCutOffDate1(Date cutOffDate1) {
    this.cutOffDate1 = cutOffDate1;
  }
  public Date getPoolCreationDate1() {
    return this.poolCreationDate1;
  }
  public void setPoolCreationDate1(Date poolCreationDate1) {
    this.poolCreationDate1 = poolCreationDate1;
  }
  public Date getAssignDate1() {
    return this.assignDate1;
  }
  public void setAssignDate1(Date assignDate1) {
    this.assignDate1 = assignDate1;
  }

  public String getTemp() {
    return this.temp;
  }
  public void setTemp(String temp) {
    this.temp = temp;
  }
  public int getHid() {
    return this.hid;
  }
  public void setHid(int hid) {
    this.hid = hid;
  }

  public String getPoolDescription()
  {
    return this.poolDescription;
  }
  public void setPoolDescription(String poolDescription) {
    this.poolDescription = poolDescription;
  }
  public String getRecStatus() {
    return this.recStatus;
  }
  public void setRecStatus(String recStatus) {
    this.recStatus = recStatus;
  }
  public String getMakerRemark() {
    return this.makerRemark;
  }
  public void setMakerRemark(String makerRemark) {
    this.makerRemark = makerRemark;
  }
  public String getAuthorRemark() {
    return this.authorRemark;
  }
  public void setAuthorRemark(String authorRemark) {
    this.authorRemark = authorRemark;
  }

  public int getId() {
    return this.id;
  }
  public void setId(int id) {
    this.id = id;
  }

  public String getCreditEnhancement() {
    return this.creditEnhancement;
  }
  public void setCreditEnhancement(String creditEnhancement) {
    this.creditEnhancement = creditEnhancement;
  }
  public String getMultipleInvestor() {
    return this.multipleInvestor;
  }
  public void setMultipleInvestor(String multipleInvestor) {
    this.multipleInvestor = multipleInvestor;
  }
  public String getAssignType() {
    return this.assignType;
  }
  public void setAssignType(String assignType) {
    this.assignType = assignType;
  }

  public static long getSerialversionuid()
  {
    return 1L;
  }
  public String getRepayment() {
    return this.repayment;
  }
  public void setRepayment(String repayment) {
    this.repayment = repayment;
  }
  public String getOtherInstallmentCharges() {
    return this.otherInstallmentCharges;
  }
  public void setOtherInstallmentCharges(String otherInstallmentCharges) {
    this.otherInstallmentCharges = otherInstallmentCharges;
  }
  public String getTotalChargeInstallmentAmount() {
    return this.totalChargeInstallmentAmount;
  }
  public void setTotalChargeInstallmentAmount(String totalChargeInstallmentAmount) {
    this.totalChargeInstallmentAmount = totalChargeInstallmentAmount;
  }
  public String getUserName() {
    return this.userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getReportingToUserId()
  {
    return this.reportingToUserId;
  }
  public void setReportingToUserId(String reportingToUserId) {
    this.reportingToUserId = reportingToUserId;
  }
  public String getInstallmentType() {
    return this.installmentType;
  }
  public void setInstallmentType(String installmentType) {
    this.installmentType = installmentType;
  }
  public String getLoanInstallmentMode() {
    return this.loanInstallmentMode;
  }
  public void setLoanInstallmentMode(String loanInstallmentMode) {
    this.loanInstallmentMode = loanInstallmentMode;
  }
  public String getLoanAdvanceInstallment() {
    return this.loanAdvanceInstallment;
  }
  public void setLoanAdvanceInstallment(String loanAdvanceInstallment) {
    this.loanAdvanceInstallment = loanAdvanceInstallment;
  }
  public String getLoanAccNo() {
    return this.loanAccNo;
  }
  public void setLoanAccNo(String loanAccNo) {
    this.loanAccNo = loanAccNo;
  }
  public String getLbxLoanNoHID() {
    return this.lbxLoanNoHID;
  }
  public void setLbxLoanNoHID(String lbxLoanNoHID) {
    this.lbxLoanNoHID = lbxLoanNoHID;
  }
  public String getLoanAmount() {
    return this.loanAmount;
  }
  public void setLoanAmount(String loanAmount) {
    this.loanAmount = loanAmount;
  }
  public String getLoanID() {
    return this.loanID;
  }
  public void setLoanID(String loanID) {
    this.loanID = loanID;
  }
  public String getLoanProduct() {
    return this.loanProduct;
  }
  public void setLoanProduct(String loanProduct) {
    this.loanProduct = loanProduct;
  }
  public String getLoanScheme() {
    return this.loanScheme;
  }
  public void setLoanScheme(String loanScheme) {
    this.loanScheme = loanScheme;
  }
  public String getLoanCustomerID() {
    return this.loanCustomerID;
  }
  public void setLoanCustomerID(String loanCustomerID) {
    this.loanCustomerID = loanCustomerID;
  }
  public String getLoanCustomerType() {
    return this.loanCustomerType;
  }
  public void setLoanCustomerType(String loanCustomerType) {
    this.loanCustomerType = loanCustomerType;
  }
  public String getLoanCustomerCtegory() {
    return this.loanCustomerCtegory;
  }
  public void setLoanCustomerCtegory(String loanCustomerCtegory) {
    this.loanCustomerCtegory = loanCustomerCtegory;
  }
  public String getLoanCustomerConstituion() {
    return this.loanCustomerConstituion;
  }
  public void setLoanCustomerConstituion(String loanCustomerConstituion) {
    this.loanCustomerConstituion = loanCustomerConstituion;
  }
  public String getLoanCustomerBusinessSeg() {
    return this.loanCustomerBusinessSeg;
  }
  public void setLoanCustomerBusinessSeg(String loanCustomerBusinessSeg) {
    this.loanCustomerBusinessSeg = loanCustomerBusinessSeg;
  }
  public String getLoanIndustry() {
    return this.loanIndustry;
  }
  public void setLoanIndustry(String loanIndustry) {
    this.loanIndustry = loanIndustry;
  }
  public String getLoanSubIndustry() {
    return this.loanSubIndustry;
  }
  public void setLoanSubIndustry(String loanSubIndustry) {
    this.loanSubIndustry = loanSubIndustry;
  }
  public String getLoanDisbursalDate() {
    return this.loanDisbursalDate;
  }
  public void setLoanDisbursalDate(String loanDisbursalDate) {
    this.loanDisbursalDate = loanDisbursalDate;
  }
  public String getLoanMaturityDate() {
    return this.loanMaturityDate;
  }
  public void setLoanMaturityDate(String loanMaturityDate) {
    this.loanMaturityDate = loanMaturityDate;
  }
  public String getLoanTenure() {
    return this.loanTenure;
  }
  public void setLoanTenure(String loanTenure) {
    this.loanTenure = loanTenure;
  }
  public String getLoanBalanceTenure() {
    return this.loanBalanceTenure;
  }
  public void setLoanBalanceTenure(String loanBalanceTenure) {
    this.loanBalanceTenure = loanBalanceTenure;
  }
  public String getLoanInstallmentNum() {
    return this.loanInstallmentNum;
  }
  public void setLoanInstallmentNum(String loanInstallmentNum) {
    this.loanInstallmentNum = loanInstallmentNum;
  }
  public String getLoanAdvEMINUm() {
    return this.loanAdvEMINUm;
  }
  public void setLoanAdvEMINUm(String loanAdvEMINUm) {
    this.loanAdvEMINUm = loanAdvEMINUm;
  }
  public String getLoanInitRate() {
    return this.loanInitRate;
  }
  public void setLoanInitRate(String loanInitRate) {
    this.loanInitRate = loanInitRate;
  }
  public String getLoanDisbursalStatus() {
    return this.loanDisbursalStatus;
  }
  public void setLoanDisbursalStatus(String loanDisbursalStatus) {
    this.loanDisbursalStatus = loanDisbursalStatus;
  }
  public String getLoanNPAFlag() {
    return this.loanNPAFlag;
  }
  public void setLoanNPAFlag(String loanNPAFlag) {
    this.loanNPAFlag = loanNPAFlag;
  }
  public String getLoanDPD() {
    return this.loanDPD;
  }
  public void setLoanDPD(String loanDPD) {
    this.loanDPD = loanDPD;
  }
  public String getLoanDPDString() {
    return this.loanDPDString;
  }
  public void setLoanDPDString(String loanDPDString) {
    this.loanDPDString = loanDPDString;
  }
  public String getLoanAssetCost() {
    return this.loanAssetCost;
  }
  public void setLoanAssetCost(String loanAssetCost) {
    this.loanAssetCost = loanAssetCost;
  }
  public String getLaonAmount() {
    return this.laonAmount;
  }
  public void setLaonAmount(String laonAmount) {
    this.laonAmount = laonAmount;
  }
  public String getLoanEMI() {
    return this.loanEMI;
  }
  public void setLoanEMI(String loanEMI) {
    this.loanEMI = loanEMI;
  }
  public String getLoanAdvEMIAmount() {
    return this.loanAdvEMIAmount;
  }
  public void setLoanAdvEMIAmount(String loanAdvEMIAmount) {
    this.loanAdvEMIAmount = loanAdvEMIAmount;
  }
  public String getLoanBalPrincipal() {
    return this.loanBalPrincipal;
  }
  public void setLoanBalPrincipal(String loanBalPrincipal) {
    this.loanBalPrincipal = loanBalPrincipal;
  }
  public String getLoanOverduePrincipal() {
    return this.loanOverduePrincipal;
  }
  public void setLoanOverduePrincipal(String loanOverduePrincipal) {
    this.loanOverduePrincipal = loanOverduePrincipal;
  }
  public String getLoanReceivedPrincipal() {
    return this.loanReceivedPrincipal;
  }
  public void setLoanReceivedPrincipal(String loanReceivedPrincipal) {
    this.loanReceivedPrincipal = loanReceivedPrincipal;
  }
  public String getLoanOverdueInstNo() {
    return this.loanOverdueInstNo;
  }
  public void setLoanOverdueInstNo(String loanOverdueInstNo) {
    this.loanOverdueInstNo = loanOverdueInstNo;
  }
  public String getLoanOverdueAmount() {
    return this.loanOverdueAmount;
  }
  public void setLoanOverdueAmount(String loanOverdueAmount) {
    this.loanOverdueAmount = loanOverdueAmount;
  }
  public String getLoanBalnceInstNo() {
    return this.loanBalnceInstNo;
  }
  public void setLoanBalnceInstNo(String loanBalnceInstNo) {
    this.loanBalnceInstNo = loanBalnceInstNo;
  }
  public String getLoanBalInstlAmount() {
    return this.loanBalInstlAmount;
  }
  public void setLoanBalInstlAmount(String loanBalInstlAmount) {
    this.loanBalInstlAmount = loanBalInstlAmount;
  }
  public String getFileName() {
    return this.fileName;
  }
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  public String getDocPath() {
    return this.docPath;
  }
  public void setDocPath(String docPath) {
    this.docPath = docPath;
  }
  public String getMakerID() {
    return this.makerID;
  }
  public void setMakerID(String makerID) {
    this.makerID = makerID;
  }

  public int getPoolID() {
    return this.poolID;
  }
  public void setPoolID(int poolID) {
    this.poolID = poolID;
  }
  public FormFile getDocFile() {
    return this.docFile;
  }
  public void setDocFile(FormFile docFile) {
    this.docFile = docFile;
  }
  public String getLbxPoolID() {
    return this.lbxPoolID;
  }
  public void setLbxPoolID(String lbxPoolID) {
    this.lbxPoolID = lbxPoolID;
  }
  public String getPoolName() {
    return this.poolName;
  }
  public void setPoolName(String poolName) {
    this.poolName = poolName;
  }

  public String getInstituteID() {
    return this.instituteID;
  }
  public void setInstituteID(String instituteID) {
    this.instituteID = instituteID;
  }
  public String getLbxinstituteID() {
    return this.lbxinstituteID;
  }
  public void setLbxinstituteID(String lbxinstituteID) {
    this.lbxinstituteID = lbxinstituteID;
  }

  public String getPoolType() {
    return this.poolType;
  }
  public void setPoolType(String poolType) {
    this.poolType = poolType;
  }
  public String getComments() {
    return this.comments;
  }
  public void setComments(String comments) {
    this.comments = comments;
  }
  public String getDecision() {
    return this.decision;
  }
  public void setDecision(String decision) {
    this.decision = decision;
  }
  public String getAuthorID() {
    return this.authorID;
  }
  public void setAuthorID(String authorID) {
    this.authorID = authorID;
  }

  public String getCutOffDate() {
    return this.cutOffDate;
  }
  public void setCutOffDate(String cutOffDate) {
    this.cutOffDate = cutOffDate;
  }
  public String getPoolCreationDate() {
    return this.poolCreationDate;
  }
  public void setPoolCreationDate(String poolCreationDate) {
    this.poolCreationDate = poolCreationDate;
  }
  public String getAssignDate() {
    return this.assignDate;
  }
  public void setAssignDate(String assignDate) {
    this.assignDate = assignDate;
  }

  public BigDecimal getDealAmount() {
    return this.dealAmount;
  }
  public void setDealAmount(BigDecimal dealAmount) {
    this.dealAmount = dealAmount;
  }
  public BigDecimal getInterestRate() {
    return this.interestRate;
  }
  public void setInterestRate(BigDecimal interestRate) {
    this.interestRate = interestRate;
  }
  public String getModifyNo() {
    return this.modifyNo;
  }
  public void setModifyNo(String modifyNo) {
    this.modifyNo = modifyNo;
  }
  public int getCurrentPageLink() {
    return this.currentPageLink;
  }
  public void setCurrentPageLink(int currentPageLink) {
    this.currentPageLink = currentPageLink;
  }
  public int getTotalRecordSize() {
    return this.totalRecordSize;
  }
  public void setTotalRecordSize(int totalRecordSize) {
    this.totalRecordSize = totalRecordSize;
  }

  public void setCheckBoxDis(String checkBoxDis)
  {
    this.checkBoxDis = checkBoxDis;
  }
  public String getCheckBoxDis() {
    return this.checkBoxDis;
  }
  public void setFilePathWithName(String filePathWithName) {
    this.filePathWithName = filePathWithName;
  }
  public String getFilePathWithName() {
    return this.filePathWithName;
  }
}