package com.webservice.cp.pdvo;


public class CrPdDocumentM {
	
	private Integer docId;
	
	private String docName;
	
	private String docType;
	
	private String entityType;
	
	private String entityRole;
	
	private String isMandatory;
	
	private String lastUpdate;

	public Integer getDocId() {
		return docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getEntityRole() {
		return entityRole;
	}

	public void setEntityRole(String entityRole) {
		this.entityRole = entityRole;
	}

	public String getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return "CrPdDocumentM [docId=" + docId + ", docName=" + docName
				+ ", docType=" + docType + ", entityType=" + entityType
				+ ", entityRole=" + entityRole + ", isMandatory=" + isMandatory
				+ ", lastUpdate=" + lastUpdate + "]";
	}
}
