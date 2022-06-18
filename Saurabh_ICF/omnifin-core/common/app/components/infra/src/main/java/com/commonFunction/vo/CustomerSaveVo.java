package com.commonFunction.vo;

import java.io.Serializable;

public class CustomerSaveVo implements Serializable{
	private String addId;
	private String addr_type;
	private String country;
	private String txtCountryCode;
    private String bp_type;
	private String bp_id;
	private String bp_id1;
	private String addr1;
	private String addr2;
	private String addr3;
	private String state;
	private String txtStateCode;
	private String dist;
	private String txtDistCode;
	private String region;
	private int regionId;
	private String pincode;
	private String primaryPhoneNo;
	private String alternatePhoneNo;
	private String tollfreeNo;
	private String faxNo;
	private String landMark;
	private String noYears;
	private String noMonths;
	private String flag;
	private String communicationAddress;
	private String pageStatus;
	private String statusCase;
	private String updateFlag;
	private String makerId;
	private String makerDate;
	private String addDetails;
	private String  defaultcountryid;
	private String  defaultcountryname;

	private String firstName;       
	private String middleName;
	private String lastName;
	private String relationshipS;
	private String primaryRefMbNo;
	private String alternateRefPhNo;
	private String knowingSince;
	private String tableToInsert;
	private String refName;
	private String refId;
	private String bpId;
	

	private String  distanceBranch;
	private String  tahsil;
	private String totalRecordSize;
	// Rohit Changes for Sarv Surksha Starts......
		private String nominee;
		private String nomineeRelation;
		private String nomineeDOB;
//		Rohit Changes for Sarv Surksha end......
		
	

	public String getBpId() {
		return bpId;
	}
	public String getNominee() {
		return nominee;
	}
	public void setNominee(String nominee) {
		this.nominee = nominee;
	}
	public String getNomineeRelation() {
		return nomineeRelation;
	}
	public void setNomineeRelation(String nomineeRelation) {
		this.nomineeRelation = nomineeRelation;
	}
	public String getNomineeDOB() {
		return nomineeDOB;
	}
	public void setNomineeDOB(String nomineeDOB) {
		this.nomineeDOB = nomineeDOB;
	}
	public void setBpId(String bpId) {
		this.bpId = bpId;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getRefName() {
		return refName;
	}
	public void setRefName(String refName) {
		this.refName = refName;
	}
	public String getTableToInsert() {
		return tableToInsert;
	}
	public void setTableToInsert(String tableToInsert) {
		this.tableToInsert = tableToInsert;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRelationshipS() {
		return relationshipS;
	}
	public void setRelationshipS(String relationshipS) {
		this.relationshipS = relationshipS;
	}
	public String getPrimaryRefMbNo() {
		return primaryRefMbNo;
	}
	public void setPrimaryRefMbNo(String primaryRefMbNo) {
		this.primaryRefMbNo = primaryRefMbNo;
	}
	public String getAlternateRefPhNo() {
		return alternateRefPhNo;
	}
	public void setAlternateRefPhNo(String alternateRefPhNo) {
		this.alternateRefPhNo = alternateRefPhNo;
	}
	public String getKnowingSince() {
		return knowingSince;
	}
	public void setKnowingSince(String knowingSince) {
		this.knowingSince = knowingSince;
	}

	public String getDistanceBranch() {
		return distanceBranch;
	}
	public void setDistanceBranch(String distanceBranch) {
		this.distanceBranch = distanceBranch;
	}
	public String getTahsil() {
		return tahsil;
	}
	public void setTahsil(String tahsil) {
		this.tahsil = tahsil;
	}

	public String getAddDetails() {
		return addDetails;
	}
	public void setAddDetails(String addDetails) {
		this.addDetails = addDetails;
	}
	public String getMakerId() {
		return makerId;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	
	public String getDefaultcountryid() {
		return defaultcountryid;
	}
	public void setDefaultcountryid(String defaultcountryid) {
		this.defaultcountryid = defaultcountryid;
	}
	public String getDefaultcountryname() {
		return defaultcountryname;
	}
	public void setDefaultcountryname(String defaultcountryname) {
		this.defaultcountryname = defaultcountryname;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public String getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}
	public String getStatusCase() {
		return statusCase;
	}
	public void setStatusCase(String statusCase) {
		this.statusCase = statusCase;
	}
	public String getPageStatus() {
		return pageStatus;
	}
	public void setPageStatus(String pageStatus) {
		this.pageStatus = pageStatus;
	}
	public String getTxtCountryCode() {
		return txtCountryCode;
	}
	public void setTxtCountryCode(String txtCountryCode) {
		this.txtCountryCode = txtCountryCode;
	}
	public String getTxtStateCode() {
		return txtStateCode;
	}
	public void setTxtStateCode(String txtStateCode) {
		this.txtStateCode = txtStateCode;
	}
	public String getTxtDistCode() {
		return txtDistCode;
	}
	public void setTxtDistCode(String txtDistCode) {
		this.txtDistCode = txtDistCode;
	}
	public String getCommunicationAddress() {
		return communicationAddress;
	}
	public void setCommunicationAddress(String communicationAddress) {
		this.communicationAddress = communicationAddress;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getBp_id1() {
		return bp_id1;
	}
	public void setBp_id1(String bpId1) {
		bp_id1 = bpId1;
	}
	public String getAddId() {
		return addId;
	}
	public void setAddId(String addId) {
		this.addId = addId;
	}
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getAddr_type() {
		return addr_type;
	}
	public void setAddr_type(String addrType) {
		addr_type = addrType;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getAddr3() {
		return addr3;
	}
	public void setAddr3(String addr3) {
		this.addr3 = addr3;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return dist;
	}
	public void setCity(String city) {
		this.dist = city;
	}
	
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	
	
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public String getLandMark() {
		return landMark;
	}
	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}
	public String getNoYears() {
		return noYears;
	}
	public void setNoYears(String noYears) {
		this.noYears = noYears;
	}
	public String getDist() {
		return dist;
	}
	public void setDist(String dist) {
		this.dist = dist;
	}
	public String getPrimaryPhoneNo() {
		return primaryPhoneNo;
	}
	public void setPrimaryPhoneNo(String primaryPhoneNo) {
		this.primaryPhoneNo = primaryPhoneNo;
	}
	public String getAlternatePhoneNo() {
		return alternatePhoneNo;
	}
	public void setAlternatePhoneNo(String alternatePhoneNo) {
		this.alternatePhoneNo = alternatePhoneNo;
	}
	public String getTollfreeNo() {
		return tollfreeNo;
	}
	public void setTollfreeNo(String tollfreeNo) {
		this.tollfreeNo = tollfreeNo;
	}
	public String getBp_type() {
		return bp_type;
	}
	public void setBp_type(String bpType) {
		bp_type = bpType;
	}
	public String getBp_id() {
		return bp_id;
	}
	public void setBp_id(String bpId) {
		bp_id = bpId;
	}
	public void setNoMonths(String noMonths) {
		this.noMonths = noMonths;
	}
	public String getNoMonths() {
		return noMonths;
	}
	public String getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(String totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}


}
