package com.masters.vo;

import java.io.Serializable;

public class FinancialPramMasterVo implements Serializable{
	private String pramSearchCode;
	private String pramSearchName;
	private String pramCode;
	private String pramName;
	private String pramType;
	private String makerId;
	private String makerDate;
	private String pramStatus;
	private String negativeAllowed;
	private String pramCodeModify;
	private int currentPageLink;
	private int totalRecordSize;
	private String subType;
	

	/*By Arun Starts here on 23-11-2012 Starts here*/
	private String pramTypeSearch;
	private String sequenceNo;
	private String autoCalculated;
	private String systemDefined;
	/*By Arun Starts here on 23-11-2012 ends here*/
	private String finParameterCode;
	private String finConstant;
	private String finOperator;
	private String finExpression;
	private String finAllParamCodes;
	private String parameterCode;



	public String getParameterCode() {
		return parameterCode;
	}
	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}
	public String getFinAllParamCodes() {
		return finAllParamCodes;
	}
	public void setFinAllParamCodes(String finAllParamCodes) {
		this.finAllParamCodes = finAllParamCodes;
	}
	public String getFinParameterCode() {
		return finParameterCode;
	}
	public void setFinParameterCode(String finParameterCode) {
		this.finParameterCode = finParameterCode;
	}
	public String getFinConstant() {
		return finConstant;
	}
	public void setFinConstant(String finConstant) {
		this.finConstant = finConstant;
	}
	public String getFinOperator() {
		return finOperator;
	}
	public void setFinOperator(String finOperator) {
		this.finOperator = finOperator;
	}
	public String getFinExpression() {
		return finExpression;
	}
	public void setFinExpression(String finExpression) {
		this.finExpression = finExpression;
	}
	public String getNegativeAllowed() {
		return negativeAllowed;
	}
	public void setNegativeAllowed(String negativeAllowed) {
		this.negativeAllowed = negativeAllowed;
	}
	public String getPramSearchCode() {
		return pramSearchCode;
	}
	public void setPramSearchCode(String pramSearchCode) {
		this.pramSearchCode = pramSearchCode;
	}
	public String getPramSearchName() {
		return pramSearchName;
	}
	public void setPramSearchName(String pramSearchName) {
		this.pramSearchName = pramSearchName;
	}
	public String getPramType() {
		return pramType;
	}
	public void setPramType(String pramType) {
		this.pramType = pramType;
	}
	public String getPramStatus() {
		return pramStatus;
	}
	public void setPramStatus(String pramStatus) {
		this.pramStatus = pramStatus;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
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
	
	public void setPramName(String pramName) {
		this.pramName = pramName;
	}
	public String getPramName() {
		return pramName;
	}
	public void setPramCode(String pramCode) {
		this.pramCode = pramCode;
	}
	public String getPramCode() {
		return pramCode;
	}
	public void setPramCodeModify(String pramCodeModify) {
		this.pramCodeModify = pramCodeModify;
	}
	public String getPramCodeModify() {
		return pramCodeModify;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getSubType() {
		return subType;
	}

	/*By Arun Starts here on 23-11-2012 Starts here*/
	public String getPramTypeSearch() {
		return pramTypeSearch;
	}
	
	public void setPramTypeSearch(String pramTypeSearch) {
		this.pramTypeSearch = pramTypeSearch;
	}
	public String getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public String getAutoCalculated() {
		return autoCalculated;
	}
	public void setAutoCalculated(String autoCalculated) {
		this.autoCalculated = autoCalculated;
	}
	public String getSystemDefined() {
		return systemDefined;
	}
	public void setSystemDefined(String systemDefined) {
		this.systemDefined = systemDefined;
	}
	
	/*By Arun Starts here on 23-11-2012 ends here*/


}
