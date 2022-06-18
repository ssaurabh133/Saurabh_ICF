//<!--Author Name :- Mradul Agarwal-->
//<!--Date of Creation : 04_March_2013-->
//<!--Purpose :-  Stationary Addition Screen-->

package com.cm.actionform;

import org.apache.struts.action.ActionForm;

public class StationaryMasterForm extends ActionForm
{
	private String bankName;
	private String bankId;
	private String asOnDate;
	private String desc;
    private String bankList;
    private String bookNoL;
    private String bookNoU;
    private String additionDate;
    private String additionDate1;
    private String actionName;
    private String checkType;
    private String checkType2;
    private String[] stationaryID;
    private String[] bookNo;
    private String[] instruNo;
    private String[] instruFrom;
    private String[] instruTo;
    private String status;
    private String status1;
    private String allocto;
    private String allocdate;
    private String bookNo1;
    private String bookNo2;
    private String makerId;
    private String makerDate;
	private String instruNo1;
    private String instruFrom1;
    private String instruTo1;
    private String instruNo2;
    private String instruFrom2;
    private String instruTo2;
    private String status2;
	private String allocto2;
    private String issuedate;
    private String allocdate2;
    private String bookIssue;
    private String userIssue;
    private int currentPageLink; 
    private String radio;
	private int companyID;
	private String branchid;
	private String lbxUserId;
	private String lbxBranchId;
	private String allBranch;
	private String hoAllocationFlag;
	private String hoRemarks;
	private String lbxBookNo;
	private String stationaryId;
	private String[] stationaryArrayId;
    private int psize;
    private int pcheck;
    private String checkTypeValue;
    private String receiptNo;
    private String remarks;
    private String stationaryBookType;
    private String userName;
    private String returnBy;
    private String allocatedBranch;
    private String returnToHODate;
   
    
	public String getAllocatedBranch() {
		return allocatedBranch;
	}
	public void setAllocatedBranch(String allocatedBranch) {
		this.allocatedBranch = allocatedBranch;
	}
    
    
	public String getHoAllocationFlag() {
		return hoAllocationFlag;
	}
	public void setHoAllocationFlag(String hoAllocationFlag) {
		this.hoAllocationFlag = hoAllocationFlag;
	}
	public String getHoRemarks() {
		return hoRemarks;
	}
	public void setHoRemarks(String hoRemarks) {
		this.hoRemarks = hoRemarks;
	}
	public String getIssuedate() {
		return issuedate;
	}
	public void setIssuedate(String issuedate) {
		this.issuedate = issuedate;
	}


	
	
    
    public String getCheckType2() {
		return checkType2;
	}
	public void setCheckType2(String checkType2) {
		this.checkType2 = checkType2;
	}
	public String getInstruNo2() {
		return instruNo2;
	}
	public void setInstruNo2(String instruNo2) {
		this.instruNo2 = instruNo2;
	}
	public String getInstruFrom2() {
		return instruFrom2;
	}
	public void setInstruFrom2(String instruFrom2) {
		this.instruFrom2 = instruFrom2;
	}
	public String getInstruTo2() {
		return instruTo2;
	}
	public void setInstruTo2(String instruTo2) {
		this.instruTo2 = instruTo2;
	}
	public String getStatus2() {
		return status2;
	}
	public void setStatus2(String status2) {
		this.status2 = status2;
	}
	public String getAllocto2() {
		return allocto2;
	}
	public void setAllocto2(String allocto2) {
		this.allocto2 = allocto2;
	}
	public String getAllocdate2() {
		return allocdate2;
	}
	public void setAllocdate2(String allocdate2) {
		this.allocdate2 = allocdate2;
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
   
    
    public String getBookNo1() {
		return bookNo1;
	}
	public void setBookNo1(String bookNo1) {
		this.bookNo1 = bookNo1;
	}
	public String getInstruNo1() {
		return instruNo1;
	}
	public void setInstruNo1(String instruNo1) {
		this.instruNo1 = instruNo1;
	}
	public String getInstruFrom1() {
		return instruFrom1;
	}
	public void setInstruFrom1(String instruFrom1) {
		this.instruFrom1 = instruFrom1;
	}
	public String getInstruTo1() {
		return instruTo1;
	}
	public void setInstruTo1(String instruTo1) {
		this.instruTo1 = instruTo1;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAllocto() {
		return allocto;
	}
	public void setAllocto(String allocto) {
		this.allocto = allocto;
	}
	public String getAllocdate() {
		return allocdate;
	}
	public void setAllocdate(String allocdate) {
		this.allocdate = allocdate;
	}
   
    public String[] getInstruNo() {
		return instruNo;
	}
	public void setInstruNo(String[] instruNo) {
		this.instruNo = instruNo;
	}
	public String[] getInstruFrom() {
		return instruFrom;
	}
	public void setInstruFrom(String[] instruFrom) {
		this.instruFrom = instruFrom;
	}
	public String[] getInstruTo() {
		return instruTo;
	}
	public void setInstruTo(String[] instruTo) {
		this.instruTo = instruTo;
	}
	
    public int getPsize() {
		return psize;
	}
	public void setPsize(int psize) {
		this.psize = psize;
	}
	public int getPcheck() {
		return pcheck;
	}
	public void setPcheck(int pcheck) {
		this.pcheck = pcheck;
	}
	
	public String[] getBookNo() {
		return bookNo;
	}
	public void setBookNo(String[] bookNo) {
		this.bookNo = bookNo;
	}
	public int getCompanyID() {
		return companyID;
	}
	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public void setAsOnDate(String asOnDate) {
		this.asOnDate = asOnDate;
	}
	public String getAsOnDate() {
		return asOnDate;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDesc() {
		return desc;
	}
	public void setBankList(String bankList) {
		this.bankList = bankList;
	}
	public String getBankList() {
		return bankList;
	}
	public void setAdditionDate(String additionDate) {
		this.additionDate = additionDate;
	}
	public String getAdditionDate() {
		return additionDate;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getActionName() {
		return actionName;
	}
	public void setStationaryID(String[] stationaryID) {
		this.stationaryID = stationaryID;
	}
	public String[] getStationaryID() {
		return stationaryID;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getBankId() {
		return bankId;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setRadio(String radio) {
		this.radio = radio;
	}
	public String getRadio() {
		return radio;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setBookNoL(String bookNoL) {
		this.bookNoL = bookNoL;
	}
	public String getBookNoL() {
		return bookNoL;
	}
	public void setBookNo2(String bookNo2) {
		this.bookNo2 = bookNo2;
	}
	public String getBookNo2() {
		return bookNo2;
	}
	public void setBookIssue(String bookIssue) {
		this.bookIssue = bookIssue;
	}
	public String getBookIssue() {
		return bookIssue;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}
	public String getBranchid() {
		return branchid;
	}
	public void setStatus1(String status1) {
		this.status1 = status1;
	}
	public String getStatus1() {
		return status1;
	}
	public void setUserIssue(String userIssue) {
		this.userIssue = userIssue;
	}
	public String getUserIssue() {
		return userIssue;
	}
	public void setLbxUserId(String lbxUserId) {
		this.lbxUserId = lbxUserId;
	}
	public String getLbxUserId() {
		return lbxUserId;
	}
	public void setAdditionDate1(String additionDate1) {
		this.additionDate1 = additionDate1;
	}
	public String getAdditionDate1() {
		return additionDate1;
	}
	public void setBookNoU(String bookNoU) {
		this.bookNoU = bookNoU;
	}
	public String getBookNoU() {
		return bookNoU;
	}
	public void setLbxBranchId(String lbxBranchId) {
		this.lbxBranchId = lbxBranchId;
	}
	public String getLbxBranchId() {
		return lbxBranchId;
	}
	public void setAllBranch(String allBranch) {
		this.allBranch = allBranch;
	}
	public String getAllBranch() {
		return allBranch;
	}
	public void setLbxBookNo(String lbxBookNo) {
		this.lbxBookNo = lbxBookNo;
	}
	public String getLbxBookNo() {
		return lbxBookNo;
	}
	public void setStationaryId(String stationaryId) {
		this.stationaryId = stationaryId;
	}
	public String getStationaryId() {
		return stationaryId;
	}
	public void setStationaryArrayId(String[] stationaryArrayId) {
		this.stationaryArrayId = stationaryArrayId;
	}
	public String[] getStationaryArrayId() {
		return stationaryArrayId;
	}
	public void setCheckTypeValue(String checkTypeValue) {
		this.checkTypeValue = checkTypeValue;
	}
	public String getCheckTypeValue() {
		return checkTypeValue;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setStationaryBookType(String stationaryBookType) {
		this.stationaryBookType = stationaryBookType;
	}
	public String getStationaryBookType() {
		return stationaryBookType;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public void setReturnBy(String returnBy) {
		this.returnBy = returnBy;
	}
	public String getReturnBy() {
		return returnBy;
	}
	public void setReturnToHODate(String returnToHODate) {
		this.returnToHODate = returnToHODate;
	}
	public String getReturnToHODate() {
		return returnToHODate;
	}


}
