package com.webservice.cp.pdvo;

public class CrPdResidenceFamilyDtl {

	private Integer id;

	private Integer pdId;

	private String name;

	private String relation;

	private String contact;

	private String educationQualification;

	private String dob;

	private String collegeAddress;

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

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEducationQualification() {
		return educationQualification;
	}

	public void setEducationQualification(String educationQualification) {
		this.educationQualification = educationQualification;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getCollegeAddress() {
		return collegeAddress;
	}

	public void setCollegeAddress(String collegeAddress) {
		this.collegeAddress = collegeAddress;
	}

	@Override
	public String toString() {
		return "CrPdResidenceFamilyDtl [id=" + id + ", pdId=" + pdId
				+ ", name=" + name + ", relation=" + relation + ", contact="
				+ contact + ", educationQualification="
				+ educationQualification + ", dob=" + dob + ", collegeAddress="
				+ collegeAddress + "]";
	}
}
