package com.webservice.cp.pdvo;

import java.math.BigDecimal;
import java.util.List;

public class CrPdCustomerDtl {
	
	private Integer pdCustomerId;
	
	private Integer pdId;
	
	private Integer gcdId;
	
	private String customerRole;
	
	private String customerType;
	
	private String registeredAddress;
	
	private String dateOfCommencement;
	
	private String shareCapital;
	
	private String salutation;
	
	private String customerName;
	
	private String photoStream;
	private String entityType;
	
	private String customerFormat;
	
	
	private String relationshipWithApplicant;
	
	private String dob;
	
	private String gender;
	
	private String qualification;
	
	private BigDecimal income;
	
	private String nameOfInstitution;
	
	private String constitution;
	
	private String saveAsDraft;
	
	private Integer noOfChildern;
	
	private Integer noOfSpouse;
	
	private Integer noOfSibling;
	
	private Integer noOfParent;
	
	private String multipleCibil;
	
	private String negativeCibil;
	
	private String positiveRemarks;
	
	private String negativeRemarks;
	
	private String politicalLinkSeen;
	
	private String communityDominatedArea;
	
	private String makerId;
	
	private String makerDate;
	
	private String operationDate;
	
	private String longitude;
	
	private String latitude;

	private String locationAddress;
	
	
	private List<CrPdCustomerEmploymentDtl> crPdCustomerEmploymentDtl;
	
	private List<CrPdCustomerAddressDtl> crPdCustomerAddressDtl;
	
	private List<CrPdCustomerAssetDtl> crPdCustomerAssetDtl;
	
	private List<CrPdCustomerBankDtl> crPdCustomerBankDtl;
	
	private List<CrPdCustomerBusinessDtl> crPdCustomerBusinessDtl;
	
	private List<CrPdCustomerChildrenDtl> crPdCustomerChildrenDtl;
	
	private List<CrPdCustomerExistingLoanDtl> crPdCustomerExistingLoanDtl;
	
	private List<CrPdCustomerInvestmentDtl> crPdCustomerInvestmentDtl;
	
	private List<CrPdCustomerOtherIncomeDtl> crPdCustomerOtherIncomeDtl;
	
	private List<CrPdCustomerParentsDtl> crPdCustomerParentsDtl;
	
	private List<CrPdCustomerSiblingDtl> crPdCustomerSiblingDtl;
	
	private List<CrPdFinancialAnalysisDtl> crPdFinancialAnalysisDtl;
	
	private List<CrPdDocumentsDtl> crPdDocumentsDtl;
	
	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getCustomerFormat() {
		return customerFormat;
	}

	public void setCustomerFormat(String customerFormat) {
		this.customerFormat = customerFormat;
	}

	public List<CrPdDocumentsDtl> getCrPdDocumentsDtl() {
		return crPdDocumentsDtl;
	}

	public void setCrPdDocumentsDtl(List<CrPdDocumentsDtl> crPdDocumentsDtl) {
		this.crPdDocumentsDtl = crPdDocumentsDtl;
	}

	private List<CrPdInventoryMachinerySeen> crPdInventoryMachinerySeen;
	
	private List<CrPdMainSuppliersCustomers> crPdMainSuppliersCustomers;
	
	public List<CrPdCustomerEmploymentDtl> getCrPdCustomerEmploymentDtl() {
		return crPdCustomerEmploymentDtl;
	}

	public void setCrPdCustomerEmploymentDtl(
			List<CrPdCustomerEmploymentDtl> crPdCustomerEmploymentDtl) {
		this.crPdCustomerEmploymentDtl = crPdCustomerEmploymentDtl;
	}

	public List<CrPdCustomerAddressDtl> getCrPdCustomerAddressDtl() {
		return crPdCustomerAddressDtl;
	}

	public void setCrPdCustomerAddressDtl(
			List<CrPdCustomerAddressDtl> crPdCustomerAddressDtl) {
		this.crPdCustomerAddressDtl = crPdCustomerAddressDtl;
	}

	public List<CrPdCustomerAssetDtl> getCrPdCustomerAssetDtl() {
		return crPdCustomerAssetDtl;
	}

	public void setCrPdCustomerAssetDtl(
			List<CrPdCustomerAssetDtl> crPdCustomerAssetDtl) {
		this.crPdCustomerAssetDtl = crPdCustomerAssetDtl;
	}

	public List<CrPdCustomerBankDtl> getCrPdCustomerBankDtl() {
		return crPdCustomerBankDtl;
	}

	public void setCrPdCustomerBankDtl(List<CrPdCustomerBankDtl> crPdCustomerBankDtl) {
		this.crPdCustomerBankDtl = crPdCustomerBankDtl;
	}

	public List<CrPdCustomerBusinessDtl> getCrPdCustomerBusinessDtl() {
		return crPdCustomerBusinessDtl;
	}

	public void setCrPdCustomerBusinessDtl(
			List<CrPdCustomerBusinessDtl> crPdCustomerBusinessDtl) {
		this.crPdCustomerBusinessDtl = crPdCustomerBusinessDtl;
	}

	public List<CrPdCustomerChildrenDtl> getCrPdCustomerChildrenDtl() {
		return crPdCustomerChildrenDtl;
	}

	public void setCrPdCustomerChildrenDtl(
			List<CrPdCustomerChildrenDtl> crPdCustomerChildrenDtl) {
		this.crPdCustomerChildrenDtl = crPdCustomerChildrenDtl;
	}

	public List<CrPdCustomerExistingLoanDtl> getCrPdCustomerExistingLoanDtl() {
		return crPdCustomerExistingLoanDtl;
	}

	public void setCrPdCustomerExistingLoanDtl(
			List<CrPdCustomerExistingLoanDtl> crPdCustomerExistingLoanDtl) {
		this.crPdCustomerExistingLoanDtl = crPdCustomerExistingLoanDtl;
	}

	public List<CrPdCustomerInvestmentDtl> getCrPdCustomerInvestmentDtl() {
		return crPdCustomerInvestmentDtl;
	}

	public void setCrPdCustomerInvestmentDtl(
			List<CrPdCustomerInvestmentDtl> crPdCustomerInvestmentDtl) {
		this.crPdCustomerInvestmentDtl = crPdCustomerInvestmentDtl;
	}

	public List<CrPdCustomerOtherIncomeDtl> getCrPdCustomerOtherIncomeDtl() {
		return crPdCustomerOtherIncomeDtl;
	}

	public void setCrPdCustomerOtherIncomeDtl(
			List<CrPdCustomerOtherIncomeDtl> crPdCustomerOtherIncomeDtl) {
		this.crPdCustomerOtherIncomeDtl = crPdCustomerOtherIncomeDtl;
	}

	public List<CrPdCustomerParentsDtl> getCrPdCustomerParentsDtl() {
		return crPdCustomerParentsDtl;
	}

	public void setCrPdCustomerParentsDtl(
			List<CrPdCustomerParentsDtl> crPdCustomerParentsDtl) {
		this.crPdCustomerParentsDtl = crPdCustomerParentsDtl;
	}

	public List<CrPdCustomerSiblingDtl> getCrPdCustomerSiblingDtl() {
		return crPdCustomerSiblingDtl;
	}

	public void setCrPdCustomerSiblingDtl(
			List<CrPdCustomerSiblingDtl> crPdCustomerSiblingDtl) {
		this.crPdCustomerSiblingDtl = crPdCustomerSiblingDtl;
	}

	public List<CrPdFinancialAnalysisDtl> getCrPdFinancialAnalysisDtl() {
		return crPdFinancialAnalysisDtl;
	}

	public void setCrPdFinancialAnalysisDtl(
			List<CrPdFinancialAnalysisDtl> crPdFinancialAnalysisDtl) {
		this.crPdFinancialAnalysisDtl = crPdFinancialAnalysisDtl;
	}

	public List<CrPdInventoryMachinerySeen> getCrPdInventoryMachinerySeen() {
		return crPdInventoryMachinerySeen;
	}

	public void setCrPdInventoryMachinerySeen(
			List<CrPdInventoryMachinerySeen> crPdInventoryMachinerySeen) {
		this.crPdInventoryMachinerySeen = crPdInventoryMachinerySeen;
	}

	public List<CrPdMainSuppliersCustomers> getCrPdMainSuppliersCustomers() {
		return crPdMainSuppliersCustomers;
	}

	public void setCrPdMainSuppliersCustomers(
			List<CrPdMainSuppliersCustomers> crPdMainSuppliersCustomers) {
		this.crPdMainSuppliersCustomers = crPdMainSuppliersCustomers;
	}

	public Integer getPdCustomerId() {
		return pdCustomerId;
	}

	public void setPdCustomerId(Integer pdCustomerId) {
		this.pdCustomerId = pdCustomerId;
	}

	public Integer getPdId() {
		return pdId;
	}

	public void setPdId(Integer pdId) {
		this.pdId = pdId;
	}

	public Integer getGcdId() {
		return gcdId;
	}

	public void setGcdId(Integer gcdId) {
		this.gcdId = gcdId;
	}

	public String getCustomerRole() {
		return customerRole;
	}

	public void setCustomerRole(String customerRole) {
		this.customerRole = customerRole;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getDateOfCommencement() {
		return dateOfCommencement;
	}

	public void setDateOfCommencement(String dateOfCommencement) {
		this.dateOfCommencement = dateOfCommencement;
	}

	public String getShareCapital() {
		return shareCapital;
	}

	public void setShareCapital(String shareCapital) {
		this.shareCapital = shareCapital;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhotoStream() {
		return photoStream;
	}

	public void setPhotoStream(String photoStream) {
		this.photoStream = photoStream;
	}

	public String getRelationshipWithApplicant() {
		return relationshipWithApplicant;
	}

	public void setRelationshipWithApplicant(String relationshipWithApplicant) {
		this.relationshipWithApplicant = relationshipWithApplicant;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public String getNameOfInstitution() {
		return nameOfInstitution;
	}

	public void setNameOfInstitution(String nameOfInstitution) {
		this.nameOfInstitution = nameOfInstitution;
	}

	public String getConstitution() {
		return constitution;
	}

	public void setConstitution(String constitution) {
		this.constitution = constitution;
	}

	public String getSaveAsDraft() {
		return saveAsDraft;
	}

	public void setSaveAsDraft(String saveAsDraft) {
		this.saveAsDraft = saveAsDraft;
	}

	public Integer getNoOfChildern() {
		return noOfChildern;
	}

	public void setNoOfChildern(Integer noOfChildern) {
		this.noOfChildern = noOfChildern;
	}

	public Integer getNoOfSpouse() {
		return noOfSpouse;
	}

	public void setNoOfSpouse(Integer noOfSpouse) {
		this.noOfSpouse = noOfSpouse;
	}

	public Integer getNoOfSibling() {
		return noOfSibling;
	}

	public void setNoOfSibling(Integer noOfSibling) {
		this.noOfSibling = noOfSibling;
	}

	public Integer getNoOfParent() {
		return noOfParent;
	}

	public void setNoOfParent(Integer noOfParent) {
		this.noOfParent = noOfParent;
	}

	public String getMultipleCibil() {
		return multipleCibil;
	}

	public void setMultipleCibil(String multipleCibil) {
		this.multipleCibil = multipleCibil;
	}

	public String getNegativeCibil() {
		return negativeCibil;
	}

	public void setNegativeCibil(String negativeCibil) {
		this.negativeCibil = negativeCibil;
	}

	public String getPositiveRemarks() {
		return positiveRemarks;
	}

	public void setPositiveRemarks(String positiveRemarks) {
		this.positiveRemarks = positiveRemarks;
	}

	public String getNegativeRemarks() {
		return negativeRemarks;
	}

	public void setNegativeRemarks(String negativeRemarks) {
		this.negativeRemarks = negativeRemarks;
	}

	public String getPoliticalLinkSeen() {
		return politicalLinkSeen;
	}

	public void setPoliticalLinkSeen(String politicalLinkSeen) {
		this.politicalLinkSeen = politicalLinkSeen;
	}

	public String getCommunityDominatedArea() {
		return communityDominatedArea;
	}

	public void setCommunityDominatedArea(String communityDominatedArea) {
		this.communityDominatedArea = communityDominatedArea;
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

	public String getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLocationAddress() {
		return locationAddress;
	}

	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}

	@Override
	public String toString() {
		return "CrPdCustomerDtl [pdCustomerId=" + pdCustomerId + ", pdId="
				+ pdId + ", gcdId=" + gcdId + ", customerRole=" + customerRole
				+ ", customerType=" + customerType + ", registeredAddress="
				+ registeredAddress + ", dateOfCommencement="
				+ dateOfCommencement + ", shareCapital=" + shareCapital
				+ ", salutation=" + salutation + ", customerName="
				+ customerName + ", photoStream=" + photoStream
				+ ", entityType=" + entityType + ", customerFormat="
				+ customerFormat + ", relationshipWithApplicant="
				+ relationshipWithApplicant + ", dob=" + dob + ", gender="
				+ gender + ", qualification=" + qualification + ", income="
				+ income + ", nameOfInstitution=" + nameOfInstitution
				+ ", constitution=" + constitution + ", saveAsDraft="
				+ saveAsDraft + ", noOfChildern=" + noOfChildern
				+ ", noOfSpouse=" + noOfSpouse + ", noOfSibling=" + noOfSibling
				+ ", noOfParent=" + noOfParent + ", multipleCibil="
				+ multipleCibil + ", negativeCibil=" + negativeCibil
				+ ", positiveRemarks=" + positiveRemarks + ", negativeRemarks="
				+ negativeRemarks + ", politicalLinkSeen=" + politicalLinkSeen
				+ ", communityDominatedArea=" + communityDominatedArea
				+ ", makerId=" + makerId + ", makerDate=" + makerDate
				+ ", operationDate=" + operationDate + ", longitude="
				+ longitude + ", latitude=" + latitude + ", locationAddress="
				+ locationAddress + ", crPdCustomerEmploymentDtl="
				+ crPdCustomerEmploymentDtl + ", crPdCustomerAddressDtl="
				+ crPdCustomerAddressDtl + ", crPdCustomerAssetDtl="
				+ crPdCustomerAssetDtl + ", crPdCustomerBankDtl="
				+ crPdCustomerBankDtl + ", crPdCustomerBusinessDtl="
				+ crPdCustomerBusinessDtl + ", crPdCustomerChildrenDtl="
				+ crPdCustomerChildrenDtl + ", crPdCustomerExistingLoanDtl="
				+ crPdCustomerExistingLoanDtl + ", crPdCustomerInvestmentDtl="
				+ crPdCustomerInvestmentDtl + ", crPdCustomerOtherIncomeDtl="
				+ crPdCustomerOtherIncomeDtl + ", crPdCustomerParentsDtl="
				+ crPdCustomerParentsDtl + ", crPdCustomerSiblingDtl="
				+ crPdCustomerSiblingDtl + ", crPdFinancialAnalysisDtl="
				+ crPdFinancialAnalysisDtl + ", crPdDocumentsDtl="
				+ crPdDocumentsDtl + ", crPdInventoryMachinerySeen="
				+ crPdInventoryMachinerySeen + ", crPdMainSuppliersCustomers="
				+ crPdMainSuppliersCustomers + "]";
	}
}
