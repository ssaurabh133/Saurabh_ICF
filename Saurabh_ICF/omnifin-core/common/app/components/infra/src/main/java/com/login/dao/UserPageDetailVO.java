package com.login.dao;

public class UserPageDetailVO {
	private Integer pageId;
	private String  menuName;
	private boolean  canCreate;
	private boolean  canModify;
	private boolean  canDelete;
	private boolean  canApprove;
	private boolean  canReadOnly;
	
	
	public Integer getPageId() {
		return pageId;
	}
	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public boolean isCanCreate() {
		return canCreate;
	}
	public void setCanCreate(boolean canCreate) {
		this.canCreate = canCreate;
	}
	public boolean isCanModify() {
		return canModify;
	}
	public void setCanModify(boolean canModify) {
		this.canModify = canModify;
	}
	public boolean isCanDelete() {
		return canDelete;
	}
	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}
	public boolean isCanApprove() {
		return canApprove;
	}
	public void setCanApprove(boolean canApprove) {
		this.canApprove = canApprove;
	}
	public boolean isCanReadOnly() {
		return canReadOnly;
	}
	public void setCanReadOnly(boolean canReadOnly) {
		this.canReadOnly = canReadOnly;
	}
	
	

}
