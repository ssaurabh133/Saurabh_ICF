package com.login.dao;

import java.io.Serializable;

public class UserSearchDetailsVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2560022961782332652L;
	private Integer userID;
	private String penID;
	private String ksidID;
	private String empName;
	private Integer empID;
	private Integer deptID;
	private String deptName;
	private Integer designationID;
	private String designationName;
	private Integer distID;
	private String distName;
	private String userName;
	private String userPassWord;
	private String userEmail;
	private Integer createdBy;
	private Integer modifiedBy;
	private Boolean admin;
	private Integer OfficeID;
	private Integer roleID;
	private Integer departmentDetailsId;
	private String roleName;
	private String createstr=null;
	private String deleteStr=null;
	private String modifyStr=null;
	private String approveStr=null;
	private String readOnlyStr=null;
	private String pagestr=null;
	private Integer copyUserID=null;
	private boolean copyUserStatus=false;
	private Integer delegateUserID=null;
	private boolean delegateUserStatus=false;
	

	public Integer getCopyUserID() {
		return copyUserID;
	}

	public void setCopyUserID(Integer copyUserID) {
		this.copyUserID = copyUserID;
	}

	public boolean isCopyUserStatus() {
		return copyUserStatus;
	}

	public void setCopyUserStatus(boolean copyUserStatus) {
		this.copyUserStatus = copyUserStatus;
	}

	public Integer getDelegateUserID() {
		return delegateUserID;
	}

	public void setDelegateUserID(Integer delegateUserID) {
		this.delegateUserID = delegateUserID;
	}

	public boolean isDelegateUserStatus() {
		return delegateUserStatus;
	}

	public void setDelegateUserStatus(boolean delegateUserStatus) {
		this.delegateUserStatus = delegateUserStatus;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public String getPenID() {
		return penID;
	}

	public void setPenID(String penID) {
		this.penID = penID;
	}

	public String getKsidID() {
		return ksidID;
	}

	public void setKsidID(String ksidID) {
		this.ksidID = ksidID;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Integer getEmpID() {
		return empID;
	}

	public void setEmpID(Integer empID) {
		this.empID = empID;
	}

	public Integer getDeptID() {
		return deptID;
	}

	public void setDeptID(Integer deptID) {
		this.deptID = deptID;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getDesignationID() {
		return designationID;
	}

	public void setDesignationID(Integer designationID) {
		this.designationID = designationID;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public Integer getDistID() {
		return distID;
	}

	public void setDistID(Integer distID) {
		this.distID = distID;
	}

	public String getDistName() {
		return distName;
	}

	public void setDistName(String distName) {
		this.distName = distName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassWord() {
		return userPassWord;
	}

	public void setUserPassWord(String userPassWord) {
		this.userPassWord = userPassWord;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Integer getOfficeID() {
		return OfficeID;
	}

	public void setOfficeID(Integer officeID) {
		OfficeID = officeID;
	}

	public Integer getRoleID() {
		return roleID;
	}

	public void setRoleID(Integer roleID) {
		this.roleID = roleID;
	}

	public Integer getDepartmentDetailsId() {
		return departmentDetailsId;
	}

	public void setDepartmentDetailsId(Integer departmentDetailsId) {
		this.departmentDetailsId = departmentDetailsId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getCreatestr() {
		return createstr;
	}

	public void setCreatestr(String createstr) {
		this.createstr = createstr;
	}

	public String getDeleteStr() {
		return deleteStr;
	}

	public void setDeleteStr(String deleteStr) {
		this.deleteStr = deleteStr;
	}

	public String getModifyStr() {
		return modifyStr;
	}

	public void setModifyStr(String modifyStr) {
		this.modifyStr = modifyStr;
	}

	public String getApproveStr() {
		return approveStr;
	}

	public void setApproveStr(String approveStr) {
		this.approveStr = approveStr;
	}

	public String getReadOnlyStr() {
		return readOnlyStr;
	}

	public void setReadOnlyStr(String readOnlyStr) {
		this.readOnlyStr = readOnlyStr;
	}

	public String getPagestr() {
		return pagestr;
	}

	public void setPagestr(String pagestr) {
		this.pagestr = pagestr;
	}

	

}
