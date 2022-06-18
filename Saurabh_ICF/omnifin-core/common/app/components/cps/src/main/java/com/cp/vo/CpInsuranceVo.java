package com.cp.vo;

import java.io.Serializable;
import java.util.Comparator;

//public class CpInsuranceVo implements Serializable, Comparable<CpInsuranceVo>{
	public class CpInsuranceVo implements Serializable{
	private String insuranceProvider;
	private String sumAssured;
	private String tenure;
	private String age;
	private String insurancePremium;
	private String chargesOnInsurance;
	private String chargeId;
	private String insuranceProviderDesc;
	private String dealid;
	private String insuranceid;
	private String makerId;
	private String makerDate;
	private String otherChargeId;
	private String viewInsurance;
	private String lbxOtherChargeId;
	private String insuranceProduct;
	private String insuranceProductId;
	private String policyType;
	private String policyTypeId;
	private String borrowerName;
	private String customerType;
	private String dob;
	private String customerId;
	private String ids[];
	private String age1;
	private String insuranceId;
	private int totalRecordSize;
	private String premiumFinanced;
	private String custId;
	private String nomineeName;
	private String dateOfbirth;
	private String gender;
	private String relationshp;
	private String sumAssuPer;
	private String relationshpId;
	private String sum;
	private String policyTenure;
	private String propertyTypeId;
	private String propertyType;
	private String customerConstitution;
	private String customerConstitutionValue;
	private String sms1;
	private String deathBenefitOption;
	private String sumAssuredMapping;
	private String	percentage;
	private String	addr;
	private String	nomineeName1;
	private String	dateOfbirth1;
	private String	gender1;
	private String	relationshp1;
	private String	percentage1;
	private String	addr1;
	private String	nomineeName2;
	private String	dateOfbirth2;
	private String	gender2;
	private String	relationshp2;
	private String	percentage2;
	private String	addr2;
	private String	nomineeName3;
	private String	dateOfbirth3;
	private String	gender3;
	private String	relationshp3;
	private String	percentage3;
	private String	addr3;
	private String	nomineeName4;
	private String	dateOfbirth4;
	private String	gender4;
	private String	relationshp4;
	private String	percentage4;
	private String	addr4;
	//Saurabh Changes starts here for Nominee Details
	//private String nomineeFName;
	private String nomineeMName;
	private String nomineeLName;
	private String smaritalStatus;
	private String snomineeArea;
	private String snomineeCity;
	private String snomineeState;
	private String snomineePin;
	private String nomineeMName1;
	private String nomineeLName1;
	private String smaritalstatus1;
	private String sNomineeArea1;
	private String sNomineeCity1;
	private String sNomineeState1;
	private String sNomineePin1;
	private String nomineeMName2;
	private String nomineeLName2;
	private String smaritalStatus2;
	private String sNomineeArea2;
	private String sNomineeCity2;
	private String sNomineeState2;
	private String sNomineePin2;
	private String nomineeMName3;
	private String nomineeLName3;
	private String smaritalStatus3;
	private String sNomineeArea3;
	private String sNomineeCity3;
	private String sNomineeState3;
	private String sNomineePin3;
	private String nomineeMName4;
	private String nomineeLName4;
	private String smaritalStatus4;
	private String sNomineeArea4;
	private String sNomineeCity4;
	private String sNomineeState4;
	private String sNomineePin4;
	private String sPrefix;
	private String sPrefix1;
	private String sPrefix2;
	private String sPrefix3;
	private String sPrefix4;
	private String txtDistCode;
	private String distButton;
	private String txtDistCode1;
	private String distButton1;
	private String txtDistCode2;
	private String distButton2;
	private String txtDistCode3;
	private String distButton3;
	private String txtDistCode4;
	private String distButton4;
	private String txtStateCode;
	private String stateButton;
	private String txtStateCode1;
	private String stateButton1;
	private String txtStateCode2;
	private String stateButton2;
	private String txtStateCode3;
	private String stateButton3;
	private String txtStateCode4;
	private String stateButton4;
	private String sinsuranceStreet;
	private String saddressType;
	
	private String sinsuranceStreet1;
	private String saddressType1;
	private String sinsuranceStreet2;
	private String saddressType2;
	private String sinsuranceStreet3;
	private String saddressType3;
	private String sinsuranceStreet4;
	private String saddressType4;
	
	public String getNomineeMName() {
		return nomineeMName;
	}
	public void setNomineeMName(String nomineeMName) {
		this.nomineeMName = nomineeMName;
	}
	public String getNomineeLName() {
		return nomineeLName;
	}
	public void setNomineeLName(String nomineeLName) {
		this.nomineeLName = nomineeLName;
	}
	
	public String getSmaritalStatus() {
		return smaritalStatus;
	}
	public void setSmaritalStatus(String smaritalStatus) {
		this.smaritalStatus = smaritalStatus;
	}
	/*public String getSmaritalStatus() {
		return SmaritalStatus;
	}
	public void setSmaritalStatus(String smaritalStatus) {
		SmaritalStatus = smaritalStatus;
	}*/
	public String getSnomineeArea() {
		return snomineeArea;
	}
	public void setSnomineeArea(String snomineeArea) {
		this.snomineeArea = snomineeArea;
	}
	/*public String getsNomineeArea() {
		return sNomineeArea;
	}
	public void setsNomineeArea(String sNomineeArea) {
		this.sNomineeArea = sNomineeArea;
	}*/
	
	public String getSnomineeCity() {
		return snomineeCity;
	}
	public void setSnomineeCity(String snomineeCity) {
		this.snomineeCity = snomineeCity;
	}
	public String getSnomineeState() {
		return snomineeState;
	}
	public void setSnomineeState(String snomineeState) {
		this.snomineeState = snomineeState;
	}
	public String getSnomineePin() {
		return snomineePin;
	}
	public void setSnomineePin(String snomineePin) {
		this.snomineePin = snomineePin;
	}
	
	
	/*public String getsNomineeCity() {
		return sNomineeCity;
	}
	
	
	
	
	public void setsNomineeCity(String sNomineeCity) {
		this.sNomineeCity = sNomineeCity;
	}
	public String getsNomineeState() {
		return sNomineeState;
	}
	public void setsNomineeState(String sNomineeState) {
		this.sNomineeState = sNomineeState;
	}
	public String getsNomineePin() {
		return sNomineePin;
	}
	public void setsNomineePin(String sNomineePin) {
		this.sNomineePin = sNomineePin;
	}*/
	
	public String getNomineeMName1() {
		return nomineeMName1;
	}
	
	public void setNomineeMName1(String nomineeMName1) {
		this.nomineeMName1 = nomineeMName1;
	}
	public String getNomineeLName1() {
		return nomineeLName1;
	}
	public void setNomineeLName1(String nomineeLName1) {
		this.nomineeLName1 = nomineeLName1;
	}
	/*public String getSmaritalStatus1() {
		return SmaritalStatus1;
	}
	public void setSmaritalStatus1(String smaritalStatus1) {
		SmaritalStatus1 = smaritalStatus1;
	}*/
	public String getsNomineeArea1() {
		return sNomineeArea1;
	}
	public void setsNomineeArea1(String sNomineeArea1) {
		this.sNomineeArea1 = sNomineeArea1;
	}
	public String getsNomineeCity1() {
		return sNomineeCity1;
	}
	public void setsNomineeCity1(String sNomineeCity1) {
		this.sNomineeCity1 = sNomineeCity1;
	}
	public String getsNomineeState1() {
		return sNomineeState1;
	}
	public void setsNomineeState1(String sNomineeState1) {
		this.sNomineeState1 = sNomineeState1;
	}
	public String getsNomineePin1() {
		return sNomineePin1;
	}
	public void setsNomineePin1(String sNomineePin1) {
		this.sNomineePin1 = sNomineePin1;
	}
	public String getNomineeMName2() {
		return nomineeMName2;
	}
	public void setNomineeMName2(String nomineeMName2) {
		this.nomineeMName2 = nomineeMName2;
	}
	public String getNomineeLName2() {
		return nomineeLName2;
	}
	public void setNomineeLName2(String nomineeLName2) {
		this.nomineeLName2 = nomineeLName2;
	}
	/*public String getSmaritalStatus2() {
		return SmaritalStatus2;
	}
	public void setSmaritalStatus2(String smaritalStatus2) {
		SmaritalStatus2 = smaritalStatus2;
	}*/
	public String getsNomineeArea2() {
		return sNomineeArea2;
	}
	public void setsNomineeArea2(String sNomineeArea2) {
		this.sNomineeArea2 = sNomineeArea2;
	}
	public String getsNomineeCity2() {
		return sNomineeCity2;
	}
	public void setsNomineeCity2(String sNomineeCity2) {
		this.sNomineeCity2 = sNomineeCity2;
	}
	public String getsNomineeState2() {
		return sNomineeState2;
	}
	public void setsNomineeState2(String sNomineeState2) {
		this.sNomineeState2 = sNomineeState2;
	}
	public String getsNomineePin2() {
		return sNomineePin2;
	}
	public void setsNomineePin2(String sNomineePin2) {
		this.sNomineePin2 = sNomineePin2;
	}
	public String getNomineeMName3() {
		return nomineeMName3;
	}
	public void setNomineeMName3(String nomineeMName3) {
		this.nomineeMName3 = nomineeMName3;
	}
	public String getNomineeLName3() {
		return nomineeLName3;
	}
	public void setNomineeLName3(String nomineeLName3) {
		this.nomineeLName3 = nomineeLName3;
	}
	/*public String getSmaritalStatus3() {
		return SmaritalStatus3;
	}
	public void setSmaritalStatus3(String smaritalStatus3) {
		SmaritalStatus3 = smaritalStatus3;
	}*/
	public String getsNomineeArea3() {
		return sNomineeArea3;
	}
	public void setsNomineeArea3(String sNomineeArea3) {
		this.sNomineeArea3 = sNomineeArea3;
	}
	public String getsNomineeCity3() {
		return sNomineeCity3;
	}
	public void setsNomineeCity3(String sNomineeCity3) {
		this.sNomineeCity3 = sNomineeCity3;
	}
	public String getsNomineeState3() {
		return sNomineeState3;
	}
	public void setsNomineeState3(String sNomineeState3) {
		this.sNomineeState3 = sNomineeState3;
	}
	public String getsNomineePin3() {
		return sNomineePin3;
	}
	public void setsNomineePin3(String sNomineePin3) {
		this.sNomineePin3 = sNomineePin3;
	}
	public String getNomineeMName4() {
		return nomineeMName4;
	}
	public void setNomineeMName4(String nomineeMName4) {
		this.nomineeMName4 = nomineeMName4;
	}
	public String getNomineeLName4() {
		return nomineeLName4;
	}
	public void setNomineeLName4(String nomineeLName4) {
		this.nomineeLName4 = nomineeLName4;
	}
	/*public String getSmaritalStatus4() {
		return SmaritalStatus4;
	}
	public void setSmaritalStatus4(String smaritalStatus4) {
		SmaritalStatus4 = smaritalStatus4;
	}*/
	public String getsNomineeArea4() {
		return sNomineeArea4;
	}
	public void setsNomineeArea4(String sNomineeArea4) {
		this.sNomineeArea4 = sNomineeArea4;
	}
	public String getsNomineeCity4() {
		return sNomineeCity4;
	}
	public void setsNomineeCity4(String sNomineeCity4) {
		this.sNomineeCity4 = sNomineeCity4;
	}
	public String getsNomineeState4() {
		return sNomineeState4;
	}
	public void setsNomineeState4(String sNomineeState4) {
		this.sNomineeState4 = sNomineeState4;
	}
	public String getsNomineePin4() {
		return sNomineePin4;
	}
	public void setsNomineePin4(String sNomineePin4) {
		this.sNomineePin4 = sNomineePin4;
	}
	
	public String getsPrefix() {
		return sPrefix;
	}
	public void setsPrefix(String sPrefix) {
		this.sPrefix = sPrefix;
	}
	public String getsPrefix1() {
		return sPrefix1;
	}
	public void setsPrefix1(String sPrefix1) {
		this.sPrefix1 = sPrefix1;
	}
	public String getsPrefix2() {
		return sPrefix2;
	}
	public void setsPrefix2(String sPrefix2) {
		this.sPrefix2 = sPrefix2;
	}
	public String getsPrefix3() {
		return sPrefix3;
	}
	public void setsPrefix3(String sPrefix3) {
		this.sPrefix3 = sPrefix3;
	}
	public String getsPrefix4() {
		return sPrefix4;
	}
	public void setsPrefix4(String sPrefix4) {
		this.sPrefix4 = sPrefix4;
	}
	
	public String getTxtDistCode() {
		return txtDistCode;
	}
	public void setTxtDistCode(String txtDistCode) {
		this.txtDistCode = txtDistCode;
	}
	public String getDistButton() {
		return distButton;
	}
	public void setDistButton(String distButton) {
		this.distButton = distButton;
	}
	public String getTxtDistCode1() {
		return txtDistCode1;
	}
	public void setTxtDistCode1(String txtDistCode1) {
		this.txtDistCode1 = txtDistCode1;
	}
	public String getDistButton1() {
		return distButton1;
	}
	public void setDistButton1(String distButton1) {
		this.distButton1 = distButton1;
	}
	public String getTxtDistCode2() {
		return txtDistCode2;
	}
	public void setTxtDistCode2(String txtDistCode2) {
		this.txtDistCode2 = txtDistCode2;
	}
	public String getDistButton2() {
		return distButton2;
	}
	public void setDistButton2(String distButton2) {
		this.distButton2 = distButton2;
	}
	public String getTxtDistCode3() {
		return txtDistCode3;
	}
	public void setTxtDistCode3(String txtDistCode3) {
		this.txtDistCode3 = txtDistCode3;
	}
	public String getDistButton3() {
		return distButton3;
	}
	public void setDistButton3(String distButton3) {
		this.distButton3 = distButton3;
	}
	public String getTxtDistCode4() {
		return txtDistCode4;
	}
	public void setTxtDistCode4(String txtDistCode4) {
		this.txtDistCode4 = txtDistCode4;
	}
	public String getDistButton4() {
		return distButton4;
	}
	public void setDistButton4(String distButton4) {
		this.distButton4 = distButton4;
	}
	
	public String getTxtStateCode() {
		return txtStateCode;
	}
	public void setTxtStateCode(String txtStateCode) {
		this.txtStateCode = txtStateCode;
	}
	public String getStateButton() {
		return stateButton;
	}
	public void setStateButton(String stateButton) {
		this.stateButton = stateButton;
	}
	public String getTxtStateCode1() {
		return txtStateCode1;
	}
	public void setTxtStateCode1(String txtStateCode1) {
		this.txtStateCode1 = txtStateCode1;
	}
	public String getStateButton1() {
		return stateButton1;
	}
	public void setStateButton1(String stateButton1) {
		this.stateButton1 = stateButton1;
	}
	public String getTxtStateCode2() {
		return txtStateCode2;
	}
	public void setTxtStateCode2(String txtStateCode2) {
		this.txtStateCode2 = txtStateCode2;
	}
	public String getStateButton2() {
		return stateButton2;
	}
	public void setStateButton2(String stateButton2) {
		this.stateButton2 = stateButton2;
	}
	public String getTxtStateCode3() {
		return txtStateCode3;
	}
	public void setTxtStateCode3(String txtStateCode3) {
		this.txtStateCode3 = txtStateCode3;
	}
	public String getStateButton3() {
		return stateButton3;
	}
	public void setStateButton3(String stateButton3) {
		this.stateButton3 = stateButton3;
	}
	public String getTxtStateCode4() {
		return txtStateCode4;
	}
	public void setTxtStateCode4(String txtStateCode4) {
		this.txtStateCode4 = txtStateCode4;
	}
	public String getStateButton4() {
		return stateButton4;
	}
	public void setStateButton4(String stateButton4) {
		this.stateButton4 = stateButton4;
	}
	
	public String getSmaritalstatus1() {
		return smaritalstatus1;
	}
	public void setSmaritalstatus1(String smaritalstatus1) {
		this.smaritalstatus1 = smaritalstatus1;
	}
	/*public String getSmaritalStatus1() {
		return smaritalStatus1;
	}
	public void setSmaritalStatus1(String smaritalStatus1) {
		this.smaritalStatus1 = smaritalStatus1;
	}*/
	public String getSmaritalStatus2() {
		return smaritalStatus2;
	}
	public void setSmaritalStatus2(String smaritalStatus2) {
		this.smaritalStatus2 = smaritalStatus2;
	}
	public String getSmaritalStatus3() {
		return smaritalStatus3;
	}
	public void setSmaritalStatus3(String smaritalStatus3) {
		this.smaritalStatus3 = smaritalStatus3;
	}
	public String getSmaritalStatus4() {
		return smaritalStatus4;
	}
	public void setSmaritalStatus4(String smaritalStatus4) {
		this.smaritalStatus4 = smaritalStatus4;
	}
	
	
	public String getSinsuranceStreet() {
		return sinsuranceStreet;
	}
	public void setSinsuranceStreet(String sinsuranceStreet) {
		this.sinsuranceStreet = sinsuranceStreet;
	}
	public String getSaddressType() {
		return saddressType;
	}
	public void setSaddressType(String saddressType) {
		this.saddressType = saddressType;
	}
	
	
	
	public String getSinsuranceStreet1() {
		return sinsuranceStreet1;
	}
	public void setSinsuranceStreet1(String sinsuranceStreet1) {
		this.sinsuranceStreet1 = sinsuranceStreet1;
	}
	public String getSaddressType1() {
		return saddressType1;
	}
	public void setSaddressType1(String saddressType1) {
		this.saddressType1 = saddressType1;
	}
	public String getSinsuranceStreet2() {
		return sinsuranceStreet2;
	}
	public void setSinsuranceStreet2(String sinsuranceStreet2) {
		this.sinsuranceStreet2 = sinsuranceStreet2;
	}
	public String getSaddressType2() {
		return saddressType2;
	}
	public void setSaddressType2(String saddressType2) {
		this.saddressType2 = saddressType2;
	}
	public String getSinsuranceStreet3() {
		return sinsuranceStreet3;
	}
	public void setSinsuranceStreet3(String sinsuranceStreet3) {
		this.sinsuranceStreet3 = sinsuranceStreet3;
	}
	public String getSaddressType3() {
		return saddressType3;
	}
	public void setSaddressType3(String saddressType3) {
		this.saddressType3 = saddressType3;
	}
	public String getSinsuranceStreet4() {
		return sinsuranceStreet4;
	}
	public void setSinsuranceStreet4(String sinsuranceStreet4) {
		this.sinsuranceStreet4 = sinsuranceStreet4;
	}
	public String getSaddressType4() {
		return saddressType4;
	}
	public void setSaddressType4(String saddressType4) {
		this.saddressType4 = saddressType4;
	}
	//Saurabh Ends
	public String getCustomerConstitution() {
		return customerConstitution;
	}
	public void setCustomerConstitution(String customerConstitution) {
		this.customerConstitution = customerConstitution;
	}
	public String getCustomerConstitutionValue() {
		return customerConstitutionValue;
	}
	public void setCustomerConstitutionValue(String customerConstitutionValue) {
		this.customerConstitutionValue = customerConstitutionValue;
	}
	public String getNomineeName() {
		return nomineeName;
	}
	public void setNomineeName(String nomineeName) {
		this.nomineeName = nomineeName;
	}
	public String getDateOfbirth() {
		return dateOfbirth;
	}
	public void setDateOfbirth(String dateOfbirth) {
		this.dateOfbirth = dateOfbirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRelationshp() {
		return relationshp;
	}
	public void setRelationshp(String relationshp) {
		this.relationshp = relationshp;
	}
	public String getRelationshpId() {
		return relationshpId;
	}
	public void setRelationshpId(String relationshpId) {
		this.relationshpId = relationshpId;
	}
	public String getInsuranceProduct() {
		return insuranceProduct;
	}
	public void setInsuranceProduct(String insuranceProduct) {
		this.insuranceProduct = insuranceProduct;
	}
	public String getInsuranceProductId() {
		return insuranceProductId;
	}
	public void setInsuranceProductId(String insuranceProductId) {
		this.insuranceProductId = insuranceProductId;
	}
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	public String getPolicyTypeId() {
		return policyTypeId;
	}
	public void setPolicyTypeId(String policyTypeId) {
		this.policyTypeId = policyTypeId;
	}
	public String getPremiumFinanced() {
		return premiumFinanced;
	}
	public void setPremiumFinanced(String premiumFinanced) {
		this.premiumFinanced = premiumFinanced;
	}
	public void setInsuranceProvider(String insuranceProvider) {
		this.insuranceProvider = insuranceProvider;
	}
	public String getInsuranceProvider() {
		return insuranceProvider;
	}
	public void setSumAssured(String sumAssured) {
		this.sumAssured = sumAssured;
	}
	public String getSumAssured() {
		return sumAssured;
	}
	public void setTenure(String tenure) {
		this.tenure = tenure;
	}
	public String getTenure() {
		return tenure;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getAge() {
		return age;
	}
	public void setInsurancePremium(String insurancePremium) {
		this.insurancePremium = insurancePremium;
	}
	public String getInsurancePremium() {
		return insurancePremium;
	}
	public void setChargesOnInsurance(String chargesOnInsurance) {
		this.chargesOnInsurance = chargesOnInsurance;
	}
	public String getChargesOnInsurance() {
		return chargesOnInsurance;
	}
	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}
	public String getChargeId() {
		return chargeId;
	}
	public void setInsuranceProviderDesc(String insuranceProviderDesc) {
		this.insuranceProviderDesc = insuranceProviderDesc;
	}
	public String getInsuranceProviderDesc() {
		return insuranceProviderDesc;
	}
	public void setDealid(String dealid) {
		this.dealid = dealid;
	}
	public String getDealid() {
		return dealid;
	}
	public void setInsuranceid(String insuranceid) {
		this.insuranceid = insuranceid;
	}
	public String getInsuranceid() {
		return insuranceid;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getMakerId() {
		return makerId;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setOtherChargeId(String otherChargeId) {
		this.otherChargeId = otherChargeId;
	}
	public String getOtherChargeId() {
		return otherChargeId;
	}
	public void setViewInsurance(String viewInsurance) {
		this.viewInsurance = viewInsurance;
	}
	public String getViewInsurance() {
		return viewInsurance;
	}
	public void setLbxOtherChargeId(String lbxOtherChargeId) {
		this.lbxOtherChargeId = lbxOtherChargeId;
	}
	public String getLbxOtherChargeId() {
		return lbxOtherChargeId;
	}
	public String getBorrowerName() {
		return borrowerName;
	}
	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getAge1() {
		return age1;
	}
	public void setAge1(String age1) {
		this.age1 = age1;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getInsuranceId() {
		return insuranceId;
	}
	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public String[] getIds() {
		return ids;
	}
	public void setIds(String ids[]) {
		this.ids = ids;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getSumAssuPer() {
		return sumAssuPer;
	}
	public void setSumAssuPer(String sumAssuPer) {
		this.sumAssuPer = sumAssuPer;
	}
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	public String getPolicyTenure() {
		return policyTenure;
	}
	public void setPolicyTenure(String policyTenure) {
		this.policyTenure = policyTenure;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public String getPropertyTypeId() {
		return propertyTypeId;
	}
	public void setPropertyTypeId(String propertyTypeId) {
		this.propertyTypeId = propertyTypeId;
	}
	public String getSms1() {
		return sms1;
	}
	public void setSms1(String sms1) {
		this.sms1 = sms1;
	}
	
	public String getDeathBenefitOption() {
		return deathBenefitOption;
	}
	public void setDeathBenefitOption(String deathBenefitOption) {
		this.deathBenefitOption = deathBenefitOption;
	}
	

	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getNomineeName1() {
		return nomineeName1;
	}
	public void setNomineeName1(String nomineeName1) {
		this.nomineeName1 = nomineeName1;
	}
	public String getDateOfbirth1() {
		return dateOfbirth1;
	}
	public void setDateOfbirth1(String dateOfbirth1) {
		this.dateOfbirth1 = dateOfbirth1;
	}
	public String getGender1() {
		return gender1;
	}
	public void setGender1(String gender1) {
		this.gender1 = gender1;
	}
	public String getRelationshp1() {
		return relationshp1;
	}
	public void setRelationshp1(String relationshp1) {
		this.relationshp1 = relationshp1;
	}
	public String getPercentage1() {
		return percentage1;
	}
	public void setPercentage1(String percentage1) {
		this.percentage1 = percentage1;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getNomineeName2() {
		return nomineeName2;
	}
	public void setNomineeName2(String nomineeName2) {
		this.nomineeName2 = nomineeName2;
	}
	public String getDateOfbirth2() {
		return dateOfbirth2;
	}
	public void setDateOfbirth2(String dateOfbirth2) {
		this.dateOfbirth2 = dateOfbirth2;
	}
	public String getGender2() {
		return gender2;
	}
	public void setGender2(String gender2) {
		this.gender2 = gender2;
	}
	public String getRelationshp2() {
		return relationshp2;
	}
	public void setRelationshp2(String relationshp2) {
		this.relationshp2 = relationshp2;
	}
	public String getPercentage2() {
		return percentage2;
	}
	public void setPercentage2(String percentage2) {
		this.percentage2 = percentage2;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getNomineeName3() {
		return nomineeName3;
	}
	public void setNomineeName3(String nomineeName3) {
		this.nomineeName3 = nomineeName3;
	}
	public String getDateOfbirth3() {
		return dateOfbirth3;
	}
	public void setDateOfbirth3(String dateOfbirth3) {
		this.dateOfbirth3 = dateOfbirth3;
	}
	public String getGender3() {
		return gender3;
	}
	public void setGender3(String gender3) {
		this.gender3 = gender3;
	}
	public String getRelationshp3() {
		return relationshp3;
	}
	public void setRelationshp3(String relationshp3) {
		this.relationshp3 = relationshp3;
	}
	public String getPercentage3() {
		return percentage3;
	}
	public void setPercentage3(String percentage3) {
		this.percentage3 = percentage3;
	}
	public String getAddr3() {
		return addr3;
	}
	public void setAddr3(String addr3) {
		this.addr3 = addr3;
	}
	public String getNomineeName4() {
		return nomineeName4;
	}
	public void setNomineeName4(String nomineeName4) {
		this.nomineeName4 = nomineeName4;
	}
	public String getDateOfbirth4() {
		return dateOfbirth4;
	}
	public void setDateOfbirth4(String dateOfbirth4) {
		this.dateOfbirth4 = dateOfbirth4;
	}
	public String getGender4() {
		return gender4;
	}
	public void setGender4(String gender4) {
		this.gender4 = gender4;
	}
	public String getRelationshp4() {
		return relationshp4;
	}
	public void setRelationshp4(String relationshp4) {
		this.relationshp4 = relationshp4;
	}
	public String getPercentage4() {
		return percentage4;
	}
	public void setPercentage4(String percentage4) {
		this.percentage4 = percentage4;
	}
	public String getAddr4() {
		return addr4;
	}
	public void setAddr4(String addr4) {
		this.addr4 = addr4;
	}


	public static final Comparator<CpInsuranceVo> insuranceProviderSorting = new Comparator<CpInsuranceVo>(){

        @Override
        public int compare(CpInsuranceVo o1, CpInsuranceVo o2) {
            return o1.insuranceProvider.compareTo(o2.insuranceProvider);
        }
	
    };
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CpInsuranceVo other = (CpInsuranceVo) obj;
        
        if ((this.insuranceProvider == null) ? (other.insuranceProvider != null) : !this.insuranceProvider.equals(other.insuranceProvider)) {
            return false;
        }
     
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.insuranceProvider != null ? this.insuranceProvider.hashCode() : 0);
        return hash;
    }
	public String getSumAssuredMapping() {
		return sumAssuredMapping;
	}
	public void setSumAssuredMapping(String sumAssuredMapping) {
		this.sumAssuredMapping = sumAssuredMapping;
	}
	
}