package com.cp.vo;

import java.io.Serializable;
import org.apache.struts.upload.FormFile;

public class UnderwritingDocUploadVo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String dealId;
  private String txnType;
  private String docDescription;
  private String fileName;
  private FormFile docFile;
  private String docPath;
  private String userName;
  private String fieldVerificationUniqueId;
  private String uploadedState;
  private String txnId;
  private String customerId;
  private String customerName;
  private String webString;
  private String docTypeId;
  private String docTypeDesc;
  private String recStatus;
  private String docDes;
  private String lbxDocId;
  private String docEntity;
  private String makerId;
  private String branchId;
  private String radioButton;
  private String docId;
  private String custRef;
  private int currentPageLink;
  private String docType;
  private String formatType;
  private String productDesc;
  private String productId;
  private String schemeDesc;
  private int totalRecordSize;
  private String bankId;
  private String formDate;
  private String accountType;
  private String DocFormat;
  private String lbxBankID;
  private String bankBranch;
  private String lbxBranchID;
  private String chk;
  private String[] chkValue;
  private String TransferInwardOutward;
  private String BouncingInwardOutward;
  private String IgnoreFlag;
  private String remarks;
  private String businessDate;
  private String lastUpdatedBY;
  private String lastUpdateDate;
  private String entityId;
  private String accountNo;
  private String dealNo;
  private String fromDate;
  private String makerDate;
  private String refId;
  private String toDate;
  private String bankName;
  private String customerRoleType;
  private String incomeSouceType;
  private String entityCustomerId;
  private String descriptionValue;
  private String caseId;
  private String uploadedBy;
  private String sourceType;
  private String errorMsg;
  private String excelSheetName;
  private String stageCode;
  private String formatType2;
  private String bankStmtDateFormat;
  private String comments;
  private String stage;
  private String dateIncrementalOrder;
  private String odccLimitAmount;
  private String accountTypeDesc;
  public String decision;
  private String EntityName;
  private String DocumentType;
  private String DocumentName;
  private String DmsDocId;
  private String DmsDocUrl;
  private String DmsDocNumber;
  private String UploadedDate;
  private String ChildDocDesc;
  private String documentStatus;

  public String getEntityName() {
		return EntityName;
	}
	public void setEntityName(String entityName) {
		EntityName = entityName;
	}
	public String getDocumentType() {
		return DocumentType;
	}
	public void setDocumentType(String documentType) {
		DocumentType = documentType;
	}
	public String getDocumentName() {
		return DocumentName;
	}
	public void setDocumentName(String documentName) {
		DocumentName = documentName;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
  public String getToDate()
  {
    return this.toDate;
  }
  public String getFormatType2() {
    return this.formatType2;
  }
  public void setFormatType2(String formatType2) {
    this.formatType2 = formatType2;
  }
  public void setToDate(String toDate) {
    this.toDate = toDate;
  }

  public String getRecStatus()
  {
    return this.recStatus;
  }
  public void setRecStatus(String recStatus) {
    this.recStatus = recStatus;
  }

  public String getDocTypeDesc() {
    return this.docTypeDesc;
  }
  public void setDocTypeDesc(String docTypeDesc) {
    this.docTypeDesc = docTypeDesc;
  }
  public String getDocTypeId() {
    return this.docTypeId;
  }
  public void setDocTypeId(String docTypeId) {
    this.docTypeId = docTypeId;
  }
  public String getWebString() {
    return this.webString;
  }
  public void setWebString(String webString) {
    this.webString = webString;
  }
  public String getCustomerId() {
    return this.customerId;
  }
  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }
  public String getCustomerName() {
    return this.customerName;
  }
  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }
  public String getDealId() {
    return this.dealId;
  }
  public void setDealId(String dealId) {
    this.dealId = dealId;
  }
  public String getUploadedState() {
    return this.uploadedState;
  }
  public void setUploadedState(String uploadedState) {
    this.uploadedState = uploadedState;
  }
  public String getFileName() {
    return this.fileName;
  }
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public FormFile getDocFile() {
    return this.docFile;
  }
  public void setDocFile(FormFile docFile) {
    this.docFile = docFile;
  }
  public void setDocPath(String docPath) {
    this.docPath = docPath;
  }
  public String getDocPath() {
    return this.docPath;
  }
  public String getUserName() {
    return this.userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public void setTxnType(String txnType) {
    this.txnType = txnType;
  }
  public String getTxnType() {
    return this.txnType;
  }
  public void setDocDescription(String docDescription) {
    this.docDescription = docDescription;
  }
  public String getDocDescription() {
    return this.docDescription;
  }
  public void setFieldVerificationUniqueId(String fieldVerificationUniqueId) {
    this.fieldVerificationUniqueId = fieldVerificationUniqueId;
  }
  public String getFieldVerificationUniqueId() {
    return this.fieldVerificationUniqueId;
  }
  public void setTxnId(String txnId) {
    this.txnId = txnId;
  }
  public String getTxnId() {
    return this.txnId;
  }
  public String getDocDes() {
    return this.docDes;
  }
  public void setDocDes(String docDes) {
    this.docDes = docDes;
  }
  public String getLbxDocId() {
    return this.lbxDocId;
  }
  public void setLbxDocId(String lbxDocId) {
    this.lbxDocId = lbxDocId;
  }
  public String getDocEntity() {
    return this.docEntity;
  }
  public void setDocEntity(String docEntity) {
    this.docEntity = docEntity;
  }
  public String getMakerId() {
    return this.makerId;
  }
  public void setMakerId(String makerId) {
    this.makerId = makerId;
  }
  public String getBranchId() {
    return this.branchId;
  }
  public void setBranchId(String branchId) {
    this.branchId = branchId;
  }
  public int getCurrentPageLink() {
    return this.currentPageLink;
  }
  public void setCurrentPageLink(int currentPageLink) {
    this.currentPageLink = currentPageLink;
  }
  public String getRadioButton() {
    return this.radioButton;
  }
  public void setRadioButton(String radioButton) {
    this.radioButton = radioButton;
  }
  public String getDocId() {
    return this.docId;
  }
  public void setDocId(String docId) {
    this.docId = docId;
  }
  public String getCustRef() {
    return this.custRef;
  }
  public void setCustRef(String custRef) {
    this.custRef = custRef;
  }

  public String getDecision()
  {
    return this.decision;
  }

  public void setdecision(String decision) {
    this.decision = decision;
  }

  public String getDocType() {
    return this.docType;
  }
  public void setDocType(String docType) {
    this.docType = docType;
  }
  public String getFormatType() {
    return this.formatType;
  }
  public void setFormatType(String formatType) {
    this.formatType = formatType;
  }
  public void setDecision(String decision) {
    this.decision = decision;
  }
  public String getProductDesc() {
    return this.productDesc;
  }
  public void setProductDesc(String productDesc) {
    this.productDesc = productDesc;
  }
  public String getSchemeDesc() {
    return this.schemeDesc;
  }
  public void setSchemeDesc(String schemeDesc) {
    this.schemeDesc = schemeDesc;
  }
  public int getTotalRecordSize() {
    return this.totalRecordSize;
  }
  public void setTotalRecordSize(int totalRecordSize) {
    this.totalRecordSize = totalRecordSize;
  }

  public String getForm_date() {
    return this.formDate;
  }
  public void setForm_date(String form_date) {
    this.formDate = form_date;
  }

  public String getAccountType()
  {
    return this.accountType;
  }
  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }
  public String getDocFormat() {
    return this.DocFormat;
  }
  public void setDocFormat(String docFormat) {
    this.DocFormat = docFormat;
  }
  public String getBankId() {
    return this.bankId;
  }
  public void setBankId(String bankId) {
    this.bankId = bankId;
  }
  public String getFormDate() {
    return this.formDate;
  }
  public void setFormDate(String formDate) {
    this.formDate = formDate;
  }
  public String getLbxBankID() {
    return this.lbxBankID;
  }
  public void setLbxBankID(String lbxBankID) {
    this.lbxBankID = lbxBankID;
  }
  public String getBankBranch() {
    return this.bankBranch;
  }
  public void setBankBranch(String bankBranch) {
    this.bankBranch = bankBranch;
  }
  public String getLbxBranchID() {
    return this.lbxBranchID;
  }
  public void setLbxBranchID(String lbxBranchID) {
    this.lbxBranchID = lbxBranchID;
  }
  public String getChk() {
    return this.chk;
  }
  public void setChk(String chk) {
    this.chk = chk;
  }
  public String[] getChkValue() {
    return this.chkValue;
  }
  public void setChkValue(String[] chkValue) {
    this.chkValue = chkValue;
  }
  public String getTransferInwardOutward() {
    return this.TransferInwardOutward;
  }
  public void setTransferInwardOutward(String transferInwardOutward) {
    this.TransferInwardOutward = transferInwardOutward;
  }
  public String getBouncingInwardOutward() {
    return this.BouncingInwardOutward;
  }
  public void setBouncingInwardOutward(String bouncingInwardOutward) {
    this.BouncingInwardOutward = bouncingInwardOutward;
  }
  public String getIgnoreFlag() {
    return this.IgnoreFlag;
  }
  public void setIgnoreFlag(String ignoreFlag) {
    this.IgnoreFlag = ignoreFlag;
  }
  public String getRemarks() {
    return this.remarks;
  }
  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }
  public String getBusinessDate() {
    return this.businessDate;
  }
  public void setBusinessDate(String businessDate) {
    this.businessDate = businessDate;
  }
  public String getLastUpdatedBY() {
    return this.lastUpdatedBY;
  }
  public void setLastUpdatedBY(String lastUpdatedBY) {
    this.lastUpdatedBY = lastUpdatedBY;
  }
  public String getLastUpdateDate() {
    return this.lastUpdateDate;
  }
  public void setLastUpdateDate(String lastUpdateDate) {
    this.lastUpdateDate = lastUpdateDate;
  }
  public String getEntityId() {
    return this.entityId;
  }
  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }
  public String getAccountNo() {
    return this.accountNo;
  }
  public void setAccountNo(String accountNo) {
    this.accountNo = accountNo;
  }
  public String getDealNo() {
    return this.dealNo;
  }
  public void setDealNo(String dealNo) {
    this.dealNo = dealNo;
  }
  public String getFromDate() {
    return this.fromDate;
  }
  public void setFromDate(String fromDate) {
    this.fromDate = fromDate;
  }
  public String getMakerDate() {
    return this.makerDate;
  }
  public void setMakerDate(String makerDate) {
    this.makerDate = makerDate;
  }
  public String getRefId() {
    return this.refId;
  }
  public void setRefId(String refId) {
    this.refId = refId;
  }
  public String getBankName() {
    return this.bankName;
  }
  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getCustomerRoleType() {
    return this.customerRoleType;
  }
  public void setCustomerRoleType(String customerRoleType) {
    this.customerRoleType = customerRoleType;
  }
  public String getIncomeSouceType() {
    return this.incomeSouceType;
  }
  public void setIncomeSouceType(String incomeSouceType) {
    this.incomeSouceType = incomeSouceType;
  }

  public String getDescriptionValue() {
    return this.descriptionValue;
  }
  public void setDescriptionValue(String descriptionValue) {
    this.descriptionValue = descriptionValue;
  }

  public String getCaseId() {
    return this.caseId;
  }
  public void setCaseId(String caseId) {
    this.caseId = caseId;
  }
  public String getUploadedBy() {
    return this.uploadedBy;
  }
  public void setUploadedBy(String uploadedBy) {
    this.uploadedBy = uploadedBy;
  }

  public String getSourceType() {
    return this.sourceType;
  }
  public void setSourceType(String sourceType) {
    this.sourceType = sourceType;
  }
  public String getEntityCustomerId() {
    return this.entityCustomerId;
  }
  public void setEntityCustomerId(String entityCustomerId) {
    this.entityCustomerId = entityCustomerId;
  }
  public String getErrorMsg() {
    return this.errorMsg;
  }
  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }
  public String getExcelSheetName() {
    return this.excelSheetName;
  }
  public void setExcelSheetName(String excelSheetName) {
    this.excelSheetName = excelSheetName;
  }
  public String getStageCode() {
    return this.stageCode;
  }
  public void setStageCode(String stageCode) {
    this.stageCode = stageCode;
  }
  public String getBankStmtDateFormat() {
    return this.bankStmtDateFormat;
  }
  public void setBankStmtDateFormat(String bankStmtDateFormat) {
    this.bankStmtDateFormat = bankStmtDateFormat;
  }
  public String getComments() {
    return this.comments;
  }
  public void setComments(String comments) {
    this.comments = comments;
  }
  public String getStage() {
    return this.stage;
  }
  public void setStage(String stage) {
    this.stage = stage;
  }
  public String getDateIncrementalOrder() {
    return this.dateIncrementalOrder;
  }
  public void setDateIncrementalOrder(String dateIncrementalOrder) {
    this.dateIncrementalOrder = dateIncrementalOrder;
  }
  public String getOdccLimitAmount() {
    return this.odccLimitAmount;
  }
  public void setOdccLimitAmount(String odccLimitAmount) {
    this.odccLimitAmount = odccLimitAmount;
  }
  public String getProductId() {
    return this.productId;
  }
  public void setProductId(String productId) {
    this.productId = productId;
  }
  public String getAccountTypeDesc() {
    return this.accountTypeDesc;
  }
  public void setAccountTypeDesc(String accountTypeDesc) {
    this.accountTypeDesc = accountTypeDesc;
  }
  public MDBVO getMdbvo(Object obj) {
    UnderwritingDocUploadVo vo = (UnderwritingDocUploadVo)obj;
    MDBVO mdbVo = new MDBVO();
    mdbVo.setDealId(vo.getDealId());
    mdbVo.setTxnType(vo.getTxnType());
    mdbVo.setDocDescription(vo.getDocDescription());
    mdbVo.setFileName(vo.getDocFile().getFileName());
    mdbVo.setDocPath(vo.getDocPath());
    mdbVo.setUserName(vo.getUserName());
    mdbVo.setFieldVerificationUniqueId(vo.getFieldVerificationUniqueId());
    mdbVo.setUploadedState(vo.getUploadedState());
    mdbVo.setTxnId(vo.getTxnId());
    mdbVo.setCustomerId(vo.getCustomerId());
    mdbVo.setCustomerName(vo.getCustomerName());
    mdbVo.setWebString(vo.getWebString());
    mdbVo.setDocTypeId(vo.getDocTypeId());
    mdbVo.setDocTypeDesc(vo.getDocTypeDesc());
    mdbVo.setRecStatus(vo.getRecStatus());
    mdbVo.setDocDes(vo.getDocDes());
    mdbVo.setLbxDocId(vo.getLbxDocId());
    mdbVo.setDocEntity(vo.getDocEntity());
    mdbVo.setMakerId(vo.getMakerId());
    mdbVo.setBranchId(vo.getBranchId());
    mdbVo.setRadioButton(vo.getRadioButton());
    mdbVo.setDocId(vo.getDocId());
    mdbVo.setCustRef(vo.getCustRef());
    mdbVo.setDocType(vo.getDocType());
    mdbVo.setFormatType(vo.getFormatType());
    mdbVo.setProductDesc(vo.getProductDesc());
    mdbVo.setProductId(vo.getProductId());
    mdbVo.setSchemeDesc(vo.getSchemeDesc());

    mdbVo.setBankId(vo.getBankId());
    mdbVo.setFormDate(vo.getFormDate());
    mdbVo.setAccountType(vo.getAccountType());
    mdbVo.setDocFormat(vo.getDocFormat());
    mdbVo.setLbxBankID(vo.getLbxBankID());
    mdbVo.setBankBranch(vo.getBankBranch());
    mdbVo.setLbxBranchID(vo.getLbxBranchID());
    mdbVo.setChk(vo.getChk());
    mdbVo.setTransferInwardOutward(vo.getTransferInwardOutward());
    mdbVo.setBouncingInwardOutward(vo.getBouncingInwardOutward());
    mdbVo.setIgnoreFlag(vo.getIgnoreFlag());
    mdbVo.setRemarks(vo.getRemarks());
    mdbVo.setBusinessDate(vo.getBusinessDate());
    mdbVo.setLastUpdatedBY(vo.getLastUpdatedBY());
    mdbVo.setLastUpdateDate(vo.getLastUpdateDate());
    mdbVo.setEntityId(vo.getEntityId());
    mdbVo.setAccountNo(vo.getAccountNo());
    mdbVo.setDealNo(vo.getDealNo());
    mdbVo.setFromDate(vo.getFromDate());
    mdbVo.setMakerDate(vo.getMakerDate());
    mdbVo.setRefId(vo.getRefId());
    mdbVo.setToDate(vo.getToDate());
    mdbVo.setBankName(vo.getBankName());

    mdbVo.setCustomerRoleType(vo.getCustomerRoleType());
    mdbVo.setIncomeSouceType(vo.getIncomeSouceType());
    mdbVo.setEntityCustomerId(vo.getEntityCustomerId());

    mdbVo.setDescriptionValue(vo.getDescriptionValue());
    mdbVo.setCaseId(vo.getCaseId());
    mdbVo.setUploadedBy(vo.getUploadedBy());

    mdbVo.setSourceType(vo.getSourceType());
    mdbVo.setErrorMsg(vo.getErrorMsg());
    mdbVo.setExcelSheetName(vo.getExcelSheetName());
    mdbVo.setStageCode(vo.getStageCode());
    mdbVo.setFormatType2(vo.getFormatType2());
    mdbVo.setBankStmtDateFormat(vo.getBankStmtDateFormat());
    mdbVo.setComments(vo.getComments());
    mdbVo.setStage(vo.getStage());
    mdbVo.setDateIncrementalOrder(vo.getDateIncrementalOrder());
    mdbVo.setOdccLimitAmount(vo.getOdccLimitAmount());

    mdbVo.setAccountTypeDesc(vo.getAccountTypeDesc());
    return mdbVo;
  }
/*public String getDmsDocId() {
	return DmsDocId;
}
public void setDmsDocId(String dmsDocId) {
	DmsDocId = dmsDocId;
}*/
public String getDmsDocNumber() {
	return DmsDocNumber;
}
public void setDmsDocNumber(String dmsDocNumber) {
	DmsDocNumber = dmsDocNumber;
}
public String getUploadedDate() {
	return UploadedDate;
}
public void setUploadedDate(String uploadedDate) {
	UploadedDate = uploadedDate;
}
public String getDmsDocUrl() {
	return DmsDocUrl;
}
public void setDmsDocUrl(String dmsDocUrl) {
	DmsDocUrl = dmsDocUrl;
}
/*public String getDocumentStatus() {
	return documentStatus;
}
public void setDocumentStatus(String documentStatus) {
	this.documentStatus = documentStatus;
}
public String getChildDocDesc() {
	return ChildDocDesc;
}
public void setChildDocDesc(String childDocDesc) {
	ChildDocDesc = childDocDesc;
}*/
public String getDmsDocId() {
	return DmsDocId;
}
public void setDmsDocId(String dmsDocId) {
	DmsDocId = dmsDocId;
}
public String getChildDocDesc() {
	return ChildDocDesc;
}
public void setChildDocDesc(String childDocDesc) {
	ChildDocDesc = childDocDesc;
}
public String getDocumentStatus() {
	return documentStatus;
}
public void setDocumentStatus(String documentStatus) {
	this.documentStatus = documentStatus;
}
}