package com.login.dao;

import java.io.Serializable;

public class RoleDetailVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2733637290217782766L;
	private Integer roleID;
	private String roleName;

	public Integer getRoleID() {
		return roleID;
	}

	public void setRoleID(Integer roleID) {
		this.roleID = roleID;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
