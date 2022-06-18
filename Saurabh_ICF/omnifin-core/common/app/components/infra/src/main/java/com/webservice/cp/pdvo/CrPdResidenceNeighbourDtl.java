package com.webservice.cp.pdvo;

public class CrPdResidenceNeighbourDtl {

	private Integer id;

	private Integer pdId;

	private String name;

	private String occupation;

	private String contactAddressDtl;

	private String since;

	private String generalFeedback;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPdId() {
		return pdId;
	}

	public void setPdId(Integer pdId) {
		this.pdId = pdId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getContactAddressDtl() {
		return contactAddressDtl;
	}

	public void setContactAddressDtl(String contactAddressDtl) {
		this.contactAddressDtl = contactAddressDtl;
	}

	public String getSince() {
		return since;
	}

	public void setSince(String since) {
		this.since = since;
	}

	public String getGeneralFeedback() {
		return generalFeedback;
	}

	public void setGeneralFeedback(String generalFeedback) {
		this.generalFeedback = generalFeedback;
	}

	@Override
	public String toString() {
		return "CrPdResidenceNeighbourDtl [id=" + id + ", pdId=" + pdId
				+ ", name=" + name + ", occupation=" + occupation
				+ ", contactAddressDtl=" + contactAddressDtl + ", since="
				+ since + ", generalFeedback=" + generalFeedback + "]";
	}
}
