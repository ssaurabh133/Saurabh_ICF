package com.login.roleManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserProfileDao;
import com.login.roleManager.Menu;
import com.login.roleManager.UserPermissionBO;
import com.logger.LoggerMsg;
import com.login.dao.UserPermissionVo;

public class UserProfileManager {
	
	private static final Logger logger = Logger.getLogger(UserProfileManager.class.getName());
	
	public List getleftMenuBar(String headerMenuID, int roleID,String moduleDb) throws SQLException {
	
		List leftMenuList = null;
		List leftSubMenuList = null;
		UserProfileDao userProfDao = (UserProfileDao)DaoImplInstanceFactory.getDaoImplInstance(UserProfileDao.IDENTITY);
		logger.info("Implementation class: "+UserProfileDao.class);
		List<UserPermissionVo> leftMenuVoList = userProfDao.getleftMenuBar(headerMenuID,roleID,moduleDb);
		Menu mainMenuObj = null;
		UserPermissionVo subMenu=null;
		
		if (leftMenuVoList != null) {
			System.out.println("----------main menu list----------" + leftMenuVoList.size());

			leftMenuList = new ArrayList<Menu>();
		}
		
		for (UserPermissionVo userperm : leftMenuVoList) {
			mainMenuObj = new Menu();
			subMenu=new UserPermissionVo();
			LoggerMsg.info("--------main menu----------"+userperm.getFunctiondesc());
			mainMenuObj.setModuleID(userperm.getModuleid());
		    mainMenuObj.setLeftHeaderMenu(userperm.getFunctiondesc());
	   		leftSubMenuList=getleftsubMenu(userperm.getFunctionid(),headerMenuID,userperm.getRoleId(),moduleDb);
			leftMenuList.add(mainMenuObj);
			subMenu.setLeftSubMenuList(leftSubMenuList);
			leftMenuList.add(subMenu);
		}
	
		
		return leftMenuList;

	}
	
	public List<Menu> getleftsubMenu(String headerMenuID, String functionId, int roleid,String moduleDb) throws SQLException {
		List<Menu> leftSubMenuList = null;
		UserProfileDao userProfDao = (UserProfileDao)DaoImplInstanceFactory.getDaoImplInstance(UserProfileDao.IDENTITY);
		logger.info("Implementation class: "+UserProfileDao.class);
		List<UserPermissionVo> leftMenuSubVoList = userProfDao.getleftsubMenu(headerMenuID,functionId,roleid,moduleDb);
		Menu mainMenuObj = null;
		if (leftMenuSubVoList != null && leftMenuSubVoList.size()!=0) {
			

			leftSubMenuList = new ArrayList<Menu>();
	
		 UserPermissionVo user=(UserPermissionVo)leftMenuSubVoList.get(0);

		for (UserPermissionVo userperm : leftMenuSubVoList) {
		
		
			mainMenuObj = new Menu();
				
				mainMenuObj.setLeftHeaderMenu(userperm.getSubfunctiondesc());
				mainMenuObj.setLeftSubMenuID(userperm.getSequenceid());
				leftSubMenuList.add(mainMenuObj);
				}
				
		}
		
		return leftSubMenuList;

	}
	

	
	public List<UserPermissionBO> getPage(
			String userID,  Integer MenuID,String moduleDb) throws SQLException {
		List<UserPermissionVo> PageVOList = null;
		List<UserPermissionBO> PageBOList = null;
		UserProfileDao userProfDao = (UserProfileDao)DaoImplInstanceFactory.getDaoImplInstance(UserProfileDao.IDENTITY);
		logger.info("Implementation class: "+UserProfileDao.class);
		PageVOList = userProfDao.getPage(userID,MenuID,moduleDb);

		if (PageVOList != null) {
			PageBOList = new ArrayList<UserPermissionBO>();
			for (UserPermissionVo userPermissionObj : PageVOList) {
				UserPermissionBO userPermBO = new UserPermissionBO();
				userPermBO.setFunctionID(userPermissionObj.getFunctionid());
				userPermBO.setPageName(userPermissionObj.getPagename());
			
				PageBOList.add(userPermBO);
			}
		}
		return PageBOList;
	}

	public String modulename(String moduleid,String moduleDb) throws SQLException {
		UserProfileDao userProfDao = (UserProfileDao)DaoImplInstanceFactory.getDaoImplInstance(UserProfileDao.IDENTITY);
		logger.info("Implementation class: "+UserProfileDao.class);
	String	modulename=userProfDao.modulename(moduleid,moduleDb);
	LoggerMsg.info("------modulename----------"+modulename);
	Menu menuobj=new Menu();
	String modname=menuobj.setModule(modulename);
	LoggerMsg.info("------modname----------"+modname);
	return modname;
		
	}

	public int modaccess(String userIDForMenu, String headerMenuID, int roleID,String moduleDb) throws SQLException {
	
		UserProfileDao userProfDao = (UserProfileDao)DaoImplInstanceFactory.getDaoImplInstance(UserProfileDao.IDENTITY);
		logger.info("Implementation class: "+UserProfileDao.class);
	int access=userProfDao.modaccess(userIDForMenu,headerMenuID,roleID,moduleDb);
	return access;
	}

	public int functionaccess(String userIDForMenu, String headerMenuID,
			int roleid, String pageid) throws SQLException {
		UserProfileDao userProfDao = (UserProfileDao)DaoImplInstanceFactory.getDaoImplInstance(UserProfileDao.IDENTITY);
		logger.info("Implementation class: "+UserProfileDao.class);
		int funcaccess=userProfDao.functionaccess(userIDForMenu,headerMenuID,roleid,pageid);
		return funcaccess;
	}

	public void saveUserFunctionLog(String userIDForMenu, String moduleID,
			String pageid, String bDate) {
		
		UserProfileDao userProfDao = (UserProfileDao)DaoImplInstanceFactory.getDaoImplInstance(UserProfileDao.IDENTITY);
		logger.info("Implementation class: "+UserProfileDao.class);
		userProfDao.saveUserFunctionLog(userIDForMenu,moduleID,pageid,bDate);
		
	}	
	
}
