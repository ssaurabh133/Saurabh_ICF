package com.cp.vo;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

public class MDBVO 	implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String dealId;
		private String txnType;
		private String docDescription;
		private String fileName;
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
		private String docDes;//added By Rohit
		private String lbxDocId;//added by Rohit
		private String docEntity;//added by Rohit
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
		
		private String bankId;			//added by Abhishek
		private String formDate;		//added by Abhishek
		private String accountType;		//added by Abhishek
		private String DocFormat;		//added by Abhishek
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
		private String uploadedBy;//by digendra kumar

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
		
		public String getToDate() {
			return toDate;
		}
		public String getFormatType2() {
			return formatType2;
		}
		public void setFormatType2(String formatType2) {
			this.formatType2 = formatType2;
		}
		public void setToDate(String toDate) {
			this.toDate = toDate;
		}
		
		
		public String getRecStatus() {
			return recStatus;
		}
		public void setRecStatus(String recStatus) {
			this.recStatus = recStatus;
		}
		
		public String getDocTypeDesc() {
			return docTypeDesc;
		}
		public void setDocTypeDesc(String docTypeDesc) {
			this.docTypeDesc = docTypeDesc;
		}
		public String getDocTypeId() {
			return docTypeId;
		}
		public void setDocTypeId(String docTypeId) {
			this.docTypeId = docTypeId;
		}
		public String getWebString() {
			return webString;
		}
		public void setWebString(String webString) {
			this.webString = webString;
		}
		public String getCustomerId() {
			return customerId;
		}
		public void setCustomerId(String customerId) {
			this.customerId = customerId;
		}
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public String getDealId() {
			return dealId;
		}
		public void setDealId(String dealId) {
			this.dealId = dealId;
		}
		public String getUploadedState() {
			return uploadedState;
		}
		public void setUploadedState(String uploadedState) {
			this.uploadedState = uploadedState;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		
		
		public void setDocPath(String docPath) {
			this.docPath = docPath;
		}
		public String getDocPath() {
			return docPath;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public void setTxnType(String txnType) {
			this.txnType = txnType;
		}
		public String getTxnType() {
			return txnType;
		}
		public void setDocDescription(String docDescription) {
			this.docDescription = docDescription;
		}
		public String getDocDescription() {
			return docDescription;
		}
		public void setFieldVerificationUniqueId(String fieldVerificationUniqueId) {
			this.fieldVerificationUniqueId = fieldVerificationUniqueId;
		}
		public String getFieldVerificationUniqueId() {
			return fieldVerificationUniqueId;
		}
		public void setTxnId(String txnId) {
			this.txnId = txnId;
		}
		public String getTxnId() {
			return txnId;
		}
		public String getDocDes() {
			return docDes;
		}
		public void setDocDes(String docDes) {
			this.docDes = docDes;
		}
		public String getLbxDocId() {
			return lbxDocId;
		}
		public void setLbxDocId(String lbxDocId) {
			this.lbxDocId = lbxDocId;
		}
		public String getDocEntity() {
			return docEntity;
		}
		public void setDocEntity(String docEntity) {
			this.docEntity = docEntity;
		}
		public String getMakerId() {
			return makerId;
		}
		public void setMakerId(String makerId) {
			this.makerId = makerId;
		}
		public String getBranchId() {
			return branchId;
		}
		public void setBranchId(String branchId) {
			this.branchId = branchId;
		}
		public int getCurrentPageLink() {
			return currentPageLink;
		}
		public void setCurrentPageLink(int currentPageLink) {
			this.currentPageLink = currentPageLink;
		}
		public String getRadioButton() {
			return radioButton;
		}
		public void setRadioButton(String radioButton) {
			this.radioButton = radioButton;
		}
		public String getDocId() {
			return docId;
		}
		public void setDocId(String docId) {
			this.docId = docId;
		}
		public String getCustRef() {
			return custRef;
		}
		public void setCustRef(String custRef) {
			this.custRef = custRef;
		}
		
		//change by Abhishek For Document Upload Author
		public String decision;
		
		public String getDecision()
		{
			return decision;
		}
		public void setdecision(String decision)
		{
			this.decision = decision;
		}
		
		public String getDocType() {
			return docType;
		}
		public void setDocType(String docType) {
			this.docType = docType;
		}
		public String getFormatType() {
			return formatType;
		}
		public void setFormatType(String formatType) {
			this.formatType = formatType;
		}
		public void setDecision(String decision) {
			this.decision = decision;
		}
		public String getProductDesc() {
			return productDesc;
		}
		public void setProductDesc(String productDesc) {
			this.productDesc = productDesc;
		}
		public String getSchemeDesc() {
			return schemeDesc;
		}
		public void setSchemeDesc(String schemeDesc) {
			this.schemeDesc = schemeDesc;
		}
		public int getTotalRecordSize() {
			return totalRecordSize;
		}
		public void setTotalRecordSize(int totalRecordSize) {
			this.totalRecordSize = totalRecordSize;
		}
		
		public String getForm_date() {
			return formDate;
		}
		public void setForm_date(String form_date) {
			this.formDate = form_date;
		}
		/*public String getTo_date() {
			return toDate;
		}
		public void setTo_date(String to_date) {
			this.toDate = to_date;
		}*/
		public String getAccountType() {
			return accountType;
		}
		public void setAccountType(String accountType) {
			this.accountType = accountType;
		}
		public String getDocFormat() {
			return DocFormat;
		}
		public void setDocFormat(String docFormat) {
			DocFormat = docFormat;
		}
		public String getBankId() {
			return bankId;
		}
		public void setBankId(String bankId) {
			this.bankId = bankId;
		}
		public String getFormDate() {
			return formDate;
		}
		public void setFormDate(String formDate) {
			this.formDate = formDate;
		}
		public String getLbxBankID() {
			return lbxBankID;
		}
		public void setLbxBankID(String lbxBankID) {
			this.lbxBankID = lbxBankID;
		}
		public String getBankBranch() {
			return bankBranch;
		}
		public void setBankBranch(String bankBranch) {
			this.bankBranch = bankBranch;
		}
		public String getLbxBranchID() {
			return lbxBranchID;
		}
		public void setLbxBranchID(String lbxBranchID) {
			this.lbxBranchID = lbxBranchID;
		}
		public String getChk() {
			return chk;
		}
		public void setChk(String chk) {
			this.chk = chk;
		}
		public String[] getChkValue() {
			return chkValue;
		}
		public void setChkValue(String[] chkValue) {
			this.chkValue = chkValue;
		}
		public String getTransferInwardOutward() {
			return TransferInwardOutward;
		}
		public void setTransferInwardOutward(String transferInwardOutward) {
			TransferInwardOutward = transferInwardOutward;
		}
		public String getBouncingInwardOutward() {
			return BouncingInwardOutward;
		}
		public void setBouncingInwardOutward(String bouncingInwardOutward) {
			BouncingInwardOutward = bouncingInwardOutward;
		}
		public String getIgnoreFlag() {
			return IgnoreFlag;
		}
		public void setIgnoreFlag(String ignoreFlag) {
			IgnoreFlag = ignoreFlag;
		}
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
		public String getBusinessDate() {
			return businessDate;
		}
		public void setBusinessDate(String businessDate) {
			this.businessDate = businessDate;
		}
		public String getLastUpdatedBY() {
			return lastUpdatedBY;
		}
		public void setLastUpdatedBY(String lastUpdatedBY) {
			this.lastUpdatedBY = lastUpdatedBY;
		}
		public String getLastUpdateDate() {
			return lastUpdateDate;
		}
		public void setLastUpdateDate(String lastUpdateDate) {
			this.lastUpdateDate = lastUpdateDate;
		}
		public String getEntityId() {
			return entityId;
		}
		public void setEntityId(String entityId) {
			this.entityId = entityId;
		}
		public String getAccountNo() {
			return accountNo;
		}
		public void setAccountNo(String accountNo) {
			this.accountNo = accountNo;
		}
		public String getDealNo() {
			return dealNo;
		}
		public void setDealNo(String dealNo) {
			this.dealNo = dealNo;
		}
		public String getFromDate() {
			return fromDate;
		}
		public void setFromDate(String fromDate) {
			this.fromDate = fromDate;
		}
		public String getMakerDate() {
			return makerDate;
		}
		public void setMakerDate(String makerDate) {
			this.makerDate = makerDate;
		}
		public String getRefId() {
			return refId;
		}
		public void setRefId(String refId) {
			this.refId = refId;
		}
		public String getBankName() {
			return bankName;
		}
		public void setBankName(String bankName) {
			this.bankName = bankName;
		}

		public String getCustomerRoleType() {
			return customerRoleType;
		}
		public void setCustomerRoleType(String customerRoleType) {
			this.customerRoleType = customerRoleType;
		}
		public String getIncomeSouceType() {
			return incomeSouceType;
		}
		public void setIncomeSouceType(String incomeSouceType) {
			this.incomeSouceType = incomeSouceType;
		}

		public String getDescriptionValue() {
			return descriptionValue;
		}
		public void setDescriptionValue(String descriptionValue) {
			this.descriptionValue = descriptionValue;
		}

		public String getCaseId() {
			return caseId;
		}
		public void setCaseId(String caseId) {
			this.caseId = caseId;
		}
		public String getUploadedBy() {
			return uploadedBy;
		}
		public void setUploadedBy(String uploadedBy) {
			this.uploadedBy = uploadedBy;
		}

		public String getSourceType() {
			return sourceType;
		}
		public void setSourceType(String sourceType) {
			this.sourceType = sourceType;
		}
		public String getEntityCustomerId() {
			return entityCustomerId;
		}
		public void setEntityCustomerId(String entityCustomerId) {
			this.entityCustomerId = entityCustomerId;
		}
		public String getErrorMsg() {
			return errorMsg;
		}
		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}
		public String getExcelSheetName() {
			return excelSheetName;
		}
		public void setExcelSheetName(String excelSheetName) {
			this.excelSheetName = excelSheetName;
		}
		public String getStageCode() {
			return stageCode;
		}
		public void setStageCode(String stageCode) {
			this.stageCode = stageCode;
		}
		public String getBankStmtDateFormat() {
			return bankStmtDateFormat;
		}
		public void setBankStmtDateFormat(String bankStmtDateFormat) {
			this.bankStmtDateFormat = bankStmtDateFormat;
		}
		public String getComments() {
			return comments;
		}
		public void setComments(String comments) {
			this.comments = comments;
		}
		public String getStage() {
			return stage;
		}
		public void setStage(String stage) {
			this.stage = stage;
		}
		public String getDateIncrementalOrder() {
			return dateIncrementalOrder;
		}
		public void setDateIncrementalOrder(String dateIncrementalOrder) {
			this.dateIncrementalOrder = dateIncrementalOrder;
		}
		public String getOdccLimitAmount() {
			return odccLimitAmount;
		}
		public void setOdccLimitAmount(String odccLimitAmount) {
			this.odccLimitAmount = odccLimitAmount;
		}
		public String getProductId() {
			return productId;
		}
		public void setProductId(String productId) {
			this.productId = productId;
		}
		public String getAccountTypeDesc() {
			return accountTypeDesc;
		}
		public void setAccountTypeDesc(String accountTypeDesc) {
			this.accountTypeDesc = accountTypeDesc;
		}
		public UnderwritingDocUploadVo getUnderwriterUploadVo(Object obj) {
			MDBVO vo = (MDBVO)obj;
			UnderwritingDocUploadVo mdbVo = new UnderwritingDocUploadVo();
			mdbVo.setDealId(vo.getDealId());
			mdbVo.setTxnType(vo.getTxnType());
			mdbVo.setDocDescription(vo.getDocDescription());
			mdbVo.setFileName(vo.getFileName());
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
			mdbVo.setDocDes(vo.getDocDes());//added By Rohit
			mdbVo.setLbxDocId(vo.getLbxDocId());//added by Rohit
			mdbVo.setDocEntity(vo.getDocEntity());//added by Rohit
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
			
			mdbVo.setBankId(vo.getBankId());			//added by Abhishek
			mdbVo.setFormDate(vo.getFormDate());		//added by Abhishek
			mdbVo.setAccountType(vo.getAccountType());		//added by Abhishek
			mdbVo.setDocFormat(vo.getDocFormat());		//added by Abhishek
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
			mdbVo.setUploadedBy(vo.getUploadedBy());//by digendra kumar

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
}
