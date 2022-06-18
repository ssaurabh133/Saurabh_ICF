package com.login.dao;

import java.io.Serializable;
import java.util.List;

import com.login.roleManager.Menu;

public class UserPermissionVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8907391905161385324L;
	
	
			private String moduledesc;
			private String moduleid;
			private String functionid;
			private String sequenceid;
			private String parentid;
			private String functiondesc;
			private String subfunctiondesc;
			private String moduleremarks;
			private int roleId;
			private String userId;
			private String userName;
			private String branchId;
			
		
			
			private String pagename;
			private List<Menu> leftSubMenuList;
			
			
			public static long getSerialversionuid() {
				return serialVersionUID;
			}
			public List<Menu> getLeftSubMenuList() {
				return leftSubMenuList;
			}
			public void setLeftSubMenuList(List<Menu> leftSubMenuList) {
				this.leftSubMenuList = leftSubMenuList;
			}
			public void setModuleid(String moduleid) {
				this.moduleid = moduleid;
			}
			public String getModuleid() {
				return moduleid;
			}
			public void setModuledesc(String moduledesc) {
				this.moduledesc = moduledesc;
			}
			public String getModuledesc() {
				return moduledesc;
			}
			public void setFunctionid(String functionid) {
				this.functionid = functionid;
			}
			public String getFunctionid() {
				return functionid;
			}
			public void setSequenceid(String sequenceid) {
				this.sequenceid = sequenceid;
			}
			public String getSequenceid() {
				return sequenceid;
			}
			public void setParentid(String parentid) {
				this.parentid = parentid;
			}
			public String getParentid() {
				return parentid;
			}
			public void setFunctiondesc(String functiondesc) {
				this.functiondesc = functiondesc;
			}
			public String getFunctiondesc() {
				return functiondesc;
			}
			public void setSubfunctiondesc(String subfunctiondesc) {
				this.subfunctiondesc = subfunctiondesc;
			}
			public String getSubfunctiondesc() {
				return subfunctiondesc;
			}
		
			public void setPagename(String pagename) {
				this.pagename = pagename;
			}
			public String getPagename() {
				return pagename;
			}
			public void setModuleremarks(String moduleremarks) {
				this.moduleremarks = moduleremarks;
			}
			public String getModuleremarks() {
				return moduleremarks;
			}
			
			public void setUserId(String userId) {
				this.userId = userId;
			}
			public String getUserId() {
				return userId;
			}
			public void setRoleId(int roleId) {
				this.roleId = roleId;
			}
			public int getRoleId() {
				return roleId;
			}
			public void setUserName(String userName) {
				this.userName = userName;
			}
			public String getUserName() {
				return userName;
			}
			public void setBranchId(String branchId) {
				this.branchId = branchId;
			}
			public String getBranchId() {
				return branchId;
			}
			



}