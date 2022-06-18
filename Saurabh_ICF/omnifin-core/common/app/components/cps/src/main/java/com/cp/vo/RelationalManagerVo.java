package com.cp.vo;

public class RelationalManagerVo {
	private int id;
	private String name;
	private int branchFkId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBranchFkId() {
		return branchFkId;
	}
	public void setBranchFkId(int branchFkId) {
		this.branchFkId = branchFkId;
	}
}
