package com.login.roleManager;

import java.io.Serializable;

public class UserPermissionBO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3372443679582044119L;
	 private String functionID;
	 private String pageName;
	public void setFunctionID(String functionID) {
		this.functionID = functionID;
	}
	public String getFunctionID() {
		return functionID;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getPageName() {
		return pageName;
	}
	
	
}
