package com.webservice.cp.pdvo;

public class CrPdDocumentsDtl {
	private Integer id;
	
	private Integer documentsId;
	
	private String documentsType;
	
	private Integer pdCustomerId;
	
	private String documentsDescription;
	
	private String documentsName;
	
	private String documentsPath;
	
	private String makerId;
	
	private String makerDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDocumentsId() {
		return documentsId;
	}

	public void setDocumentsId(Integer documentsId) {
		this.documentsId = documentsId;
	}

	public String getDocumentsType() {
		return documentsType;
	}

	public void setDocumentsType(String documentsType) {
		this.documentsType = documentsType;
	}

	public Integer getPdCustomerId() {
		return pdCustomerId;
	}

	public void setPdCustomerId(Integer pdCustomerId) {
		this.pdCustomerId = pdCustomerId;
	}

	public String getDocumentsDescription() {
		return documentsDescription;
	}

	public void setDocumentsDescription(String documentsDescription) {
		this.documentsDescription = documentsDescription;
	}

	public String getDocumentsName() {
		return documentsName;
	}

	public void setDocumentsName(String documentsName) {
		this.documentsName = documentsName;
	}

	public String getDocumentsPath() {
		return documentsPath;
	}

	public void setDocumentsPath(String documentsPath) {
		this.documentsPath = documentsPath;
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

	@Override
	public String toString() {
		return "CrPdDocumentsDtl [id=" + id + ", documentsId=" + documentsId
				+ ", documentsType=" + documentsType + ", pdCustomerId="
				+ pdCustomerId + ", documentsDescription="
				+ documentsDescription + ", documentsName=" + documentsName
				+ ", documentsPath=" + documentsPath + ", makerId=" + makerId
				+ ", makerDate=" + makerDate + "]";
	}
}
