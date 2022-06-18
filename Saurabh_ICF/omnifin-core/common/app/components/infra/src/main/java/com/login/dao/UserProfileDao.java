package com.login.dao;

import java.sql.SQLException;
import java.util.List;


import com.login.dao.UserPermissionVo;


public interface UserProfileDao {

	String IDENTITY="USERPROFD";	
	public List<UserPermissionVo> modulelist(String userID, String menuValue) throws SQLException;
	
	
	public List<UserPermissionVo> getPage(String userID,Integer MenuID,String moduleDb) throws SQLException;
	public List<UserPermissionVo> getleftMenuBar(String headerMenuID, int roleID,String moduleDb) throws SQLException;

	public List<UserPermissionVo> getleftsubMenu(String headerMenuID,
			String functionId, int roleid,String moduleDb) throws SQLException;


	public String modulename(String moduleid,String moduleDb) throws SQLException;


	public int modaccess(String userIDForMenu, String headerMenuID, int roleID,String moduleDb) throws SQLException;


	public int functionaccess(String userIDForMenu, String headerMenuID,
			int roleid, String pageid) throws SQLException;


	public void saveUserFunctionLog(String userIDForMenu, String moduleID,
			String pageid, String bDate);
}
