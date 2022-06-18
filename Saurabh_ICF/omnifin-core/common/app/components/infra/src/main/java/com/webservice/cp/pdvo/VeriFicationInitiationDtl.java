package com.webservice.cp.pdvo;

public class VeriFicationInitiationDtl 
{
	private String verificationId;
	private String verificationType; 	
	private String verificationSubType;	
	private String entityId;	
	private String entityType;	
	private String entitySubType;	
	private String addressType;	
	private String managementType;	
	private String lastInitiatedTo;	
	private String description; 	
	private String action;	
	private String appraiserType;	
	private String internalAppraiser; 	
	private String externalappraiser;
	private String entityDesc;
	private String initiatedUserId;
	private String externalId;
	private String actionCodeId;
	private String reassignmentId;

	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getReassignmentId() {
		return reassignmentId;
	}
	public void setReassignmentId(String reassignmentId) {
		this.reassignmentId = reassignmentId;
	}
	public String getActionCodeId() {
		return actionCodeId;
	}
	public void setActionCodeId(String actionCodeId) {
		this.actionCodeId = actionCodeId;
	}
	public String getInitiatedUserId() {
		return initiatedUserId;
	}
	public void setInitiatedUserId(String initiatedUserId) {
		this.initiatedUserId = initiatedUserId;
	}
	public String getVerificationId() {
		return verificationId;
	}
	public void setVerificationId(String verificationId) {
		this.verificationId = verificationId;
	}
	public String getVerificationType() {
		return verificationType;
	}
	public void setVerificationType(String verificationType) {
		this.verificationType = verificationType;
	}
	public String getVerificationSubType() {
		return verificationSubType;
	}
	public void setVerificationSubType(String verificationSubType) {
		this.verificationSubType = verificationSubType;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public String getEntitySubType() {
		return entitySubType;
	}
	public void setEntitySubType(String entitySubType) {
		this.entitySubType = entitySubType;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getManagementType() {
		return managementType;
	}
	public void setManagementType(String managementType) {
		this.managementType = managementType;
	}
	public String getLastInitiatedTo() {
		return lastInitiatedTo;
	}
	public void setLastInitiatedTo(String lastInitiatedTo) {
		this.lastInitiatedTo = lastInitiatedTo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getAppraiserType() {
		return appraiserType;
	}
	public void setAppraiserType(String appraiserType) {
		this.appraiserType = appraiserType;
	}
	public String getInternalAppraiser() {
		return internalAppraiser;
	}
	public void setInternalAppraiser(String internalAppraiser) {
		this.internalAppraiser = internalAppraiser;
	}
	public String getExternalappraiser() {
		return externalappraiser;
	}
	public void setExternalappraiser(String externalappraiser) {
		this.externalappraiser = externalappraiser;
	}
	public String getEntityDesc() {
		return entityDesc;
	}
	public void setEntityDesc(String entityDesc) {
		this.entityDesc = entityDesc;
	}
	@Override
	public String toString() {
		return "VeriFicationInitiationDtl [verificationId=" + verificationId
				+ ", verificationType=" + verificationType
				+ ", verificationSubType=" + verificationSubType
				+ ", entityId=" + entityId + ", entityType=" + entityType
				+ ", entitySubType=" + entitySubType + ", addressType="
				+ addressType + ", managementType=" + managementType
				+ ", lastInitiatedTo=" + lastInitiatedTo + ", description="
				+ description + ", action=" + action + ", appraiserType="
				+ appraiserType + ", internalAppraiser=" + internalAppraiser
				+ ", externalappraiser=" + externalappraiser + ", entityDesc="
				+ entityDesc + ", initiatedUserId=" + initiatedUserId
				+ ", externalId=" + externalId + ", actionCodeId="
				+ actionCodeId + ", reassignmentId=" + reassignmentId + "]";
	}
	
	
}
