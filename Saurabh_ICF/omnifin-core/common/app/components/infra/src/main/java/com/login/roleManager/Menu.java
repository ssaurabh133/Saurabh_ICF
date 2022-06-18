package com.login.roleManager;

import java.io.Serializable;
import java.util.ArrayList;

public class Menu implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 312394338741481353L;
	private String leftHeaderMenu;
	private int leftHeaderMenuID;
	private String leftHeaderPageUrl;
	private String moduleID;
	private String module;
	private boolean leftHeaderStatus;
	private String leftSubMenuID;
	private ArrayList<MenuItem> leftSubMenuList;

	public String getLeftHeaderMenu() {
		return leftHeaderMenu;
	}

	public void setLeftHeaderMenu(String leftHeaderMenu) {
		this.leftHeaderMenu = leftHeaderMenu;
	}

	public int getLeftHeaderMenuID() {
		return leftHeaderMenuID;
	}

	public void setLeftHeaderMenuID(int leftHeaderMenuID) {
		this.leftHeaderMenuID = leftHeaderMenuID;
	}

	public boolean isLeftHeaderStatus() {
		return leftHeaderStatus;
	}

	public void setLeftHeaderStatus(boolean leftHeaderStatus) {
		this.leftHeaderStatus = leftHeaderStatus;
	}

	public ArrayList<MenuItem> getLeftSubMenuList() {
		return leftSubMenuList;
	}

	public void setLeftSubMenuList(ArrayList<MenuItem> leftSubMenuList) {
		this.leftSubMenuList = leftSubMenuList;
	}

	public String getLeftHeaderPageUrl() {
		return leftHeaderPageUrl;
	}

	public void setLeftHeaderPageUrl(String leftHeaderPageUrl) {
		this.leftHeaderPageUrl = leftHeaderPageUrl;
	}

	public void setLeftSubMenuID(String leftSubMenuID) {
		this.leftSubMenuID = leftSubMenuID;
	}

	public String getLeftSubMenuID() {
		return leftSubMenuID;
	}

	public void setModuleID(String moduleID) {
		this.moduleID = moduleID;
	}

	public String getModuleID() {
		return moduleID;
	}

	public String setModule(String module) {
		return this.module = module;
	}

	public String getModule() {
		return module;
	}
	
}
