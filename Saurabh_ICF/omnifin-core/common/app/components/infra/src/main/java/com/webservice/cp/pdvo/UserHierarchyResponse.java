package com.webservice.cp.pdvo;

import java.util.List;

public class UserHierarchyResponse {
	
	private String title;
	private String key;
	private List<UserHierarchyResponse> children;
	private String userList;
	@Override
	public String toString() {
		return "UserHierarchyResponse [title=" + title + ", key=" + key
				+ ", children=" + children + ", userList=" + userList + "]";
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUserList() {
		return userList;
	}
	public void setUserList(String userList) {
		this.userList = userList;
	}
	public List<UserHierarchyResponse> getChildren() {
		return children;
	}
	public void setChildren(List<UserHierarchyResponse> children) {
		this.children = children;
	}
	
}
