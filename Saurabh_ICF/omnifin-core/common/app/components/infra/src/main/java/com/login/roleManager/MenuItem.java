/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.login.roleManager;

import java.io.Serializable;

/**
 * 
 * @author sumanta.laha
 */
public class MenuItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8880234051264123520L;
	private String subMenuName;
	private String pageUrl;
	private int subMenuID;

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getSubMenuName() {
		return subMenuName;
	}

	public void setSubMenuName(String subMenuName) {
		this.subMenuName = subMenuName;
	}

	public int getSubMenuID() {
		return subMenuID;
	}

	public void setSubMenuID(int subMenuID) {
		this.subMenuID = subMenuID;
	}

}
