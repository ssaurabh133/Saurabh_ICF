//Author Name : Ritu Jindal-->
//Date of Creation : -->
//Purpose  : VO for Branch Master-->
//Documentation : -->

package com.masters.vo;

import java.io.Serializable;

public class BranchMasterAreaCodeVo implements Serializable{
	private String areaCode;
	private String areaDesc;
	private String branchId;
	
	private String areaCodename[];
	private String lbxareaCodeVal[];
	

	public String[] getAreaCodename() {
		return areaCodename;
	}
	public void setAreaCodename(String[] areaCodename) {
		this.areaCodename = areaCodename;
	}
	public String[] getLbxareaCodeVal() {
		return lbxareaCodeVal;
	}
	public void setLbxareaCodeVal(String[] lbxareaCodeVal) {
		this.lbxareaCodeVal = lbxareaCodeVal;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaDesc() {
		return areaDesc;
	}
	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	
}
