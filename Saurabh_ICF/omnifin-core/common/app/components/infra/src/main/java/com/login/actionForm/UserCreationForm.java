package com.login.actionForm;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class UserCreationForm extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9147811111712265167L;
	private String penID = null;
	private String ksidID = null;
	private String empName = null;
	private Integer empID = null;
	private Integer deptID = null;
	private String deptName = null;
	private Integer designationID = null;
	private String designationName = null;
	private Integer distID = null;
	private String distName = null;
	private String userName = null;
	private String userPassword = null;
	private String userEmail = null;
	private boolean admin = false;
	private Integer endlistIndex = 0;
	private String createstr=null;
	private String deleteStr=null;
	private String modifyStr=null;
	private String approveStr=null;
	private String readOnlyStr=null;
	private String pagestr=null;
	private String actionName=null;
	private Integer roleId=null;	
	private Integer departmentDetailsId=null;
	private Integer roleIDEdit=null;
	private Integer userID=null;
	private Integer copyUserID=null;
	private boolean copyUserStatus=false;
	private Integer delegateUserID=null;
	private boolean delegateUserStatus=false;

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getPagestr() {
		return pagestr;
	}

	public void setPagestr(String pagestr) {
		this.pagestr = pagestr;
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

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	
	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Integer getEndlistIndex() {
		return endlistIndex;
	}

	public void setEndlistIndex(Integer endlistIndex) {
		this.endlistIndex = endlistIndex;
	}
	
	
	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		ActionErrors actionErrors = new ActionErrors();

		if (penID == null || penID.trim().equals("")) {

			actionErrors.add("penID", new ActionMessage("error.penID"));

		}

		return actionErrors;

	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {

		this.penID = null;
		this.admin=false;
		this.ksidID=null;
		this.departmentDetailsId=null;
		this.userEmail=null;
		this.userName=null;
		this.userPassword=null;
		this.deptID=null;
		this.designationID=null;
		this.distID=null;
		this.designationName=null;
		this.distName=null;
		this.admin = false;
		this.endlistIndex = 0;
		this.createstr=null;
		this.deleteStr=null;
		this.modifyStr=null;
		this.approveStr=null;
		this.readOnlyStr=null;
		this.pagestr=null;
		this.actionName=null;
		this.roleId=null;	
		this.departmentDetailsId=null;
		this.roleIDEdit=null;
		this.userID=null;
		this.copyUserID=null;
		this.copyUserStatus=false;
		this.delegateUserID=null;
		this.delegateUserStatus=false;

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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getDepartmentDetailsId() {
		return departmentDetailsId;
	}

	public void setDepartmentDetailsId(Integer departmentDetailsId) {
		this.departmentDetailsId = departmentDetailsId;
	}

	public Integer getRoleIDEdit() {
		return roleIDEdit;
	}

	public void setRoleIDEdit(Integer roleIDEdit) {
		this.roleIDEdit = roleIDEdit;
	}

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

}
