package com.caps.VO;

import javax.smartcardio.ATR;

import org.apache.struts.action.ActionForm;

public class ViewpayableRecievableVo {
	
	private String viewPRDate;
	private String advicetype;
	private String charge;
	private String balamt;
	private int currentPageLink;
	private int TotalRecordSize;


	public String getViewPRDate() {
		return viewPRDate;
	}
	public void setViewPRDate(String viewPRDate) {
		this.viewPRDate = viewPRDate;
	}
	public String getAdvicetype() {
		return advicetype;
	}
	public void setAdvicetype(String advicetype) {
		this.advicetype = advicetype;
	}
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}
	public String getBalamt() {
		return balamt;
	}
	public void setBalamt(String balamt) {
		this.balamt = balamt;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public int getTotalRecordSize() {
		return TotalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		TotalRecordSize = totalRecordSize;
	}

	
}
