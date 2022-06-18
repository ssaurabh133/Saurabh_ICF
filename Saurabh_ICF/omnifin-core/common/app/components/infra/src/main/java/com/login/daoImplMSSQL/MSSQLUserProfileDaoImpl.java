/**
 * 
 */
package com.login.daoImplMSSQL;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.ConnectionUploadDAO;
import com.connect.PrepStmtObject;
import com.login.dao.UserPermissionVo;
import com.login.dao.UserProfileDao;


public class MSSQLUserProfileDaoImpl implements UserProfileDao {
	
	private static final Logger logger = Logger.getLogger(MSSQLUserProfileDaoImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
	String moduleColumnsInRow=resource.getString("count.moduleColumnsInRow");
	// Added for menu creation
	public List<UserPermissionVo> modulelist(String userID,String menuValue) throws SQLException{
		if(CommonFunction.checkNull(menuValue).trim().equalsIgnoreCase(""))
			 menuValue="'0'";
		
		List<UserPermissionVo> permissionList =null;

		try {
			logger.info("--------in--modulelist()------");
			

				  ArrayList list=new ArrayList ();
				  ArrayList modList=new ArrayList ();
				  UserPermissionVo user=new UserPermissionVo();
				  
				String modules="SELECT SMM.MODULE_DESC,SMM.MODULE_ID, SMM.MODULE_REMARKS,SUAM.ROLE_ID " +
						"FROM sec_module_m SMM LEFT OUTER JOIN sec_user_access_m SUAM " +
						"ON SMM.MODULE_ID = SUAM.MODULE_ID AND SUAM.REC_STATUS='A' AND SUAM.USER_ID='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(userID).trim())+"' WHERE SMM.REC_STATUS='A'  AND SHOW_AT_SERVER in ("+menuValue+") order by SMM.SEQUENCE_ID";
				logger.info("query for modules........"+modules);
				list=ConnectionDAO.sqlSelect(modules);
				modules = null;
				permissionList=new ArrayList<UserPermissionVo>();
				
				for(int i=0;i<list.size();i++){
					modList=(ArrayList)list.get(i);
					
					if(modList.size()>0){
						
					user=new UserPermissionVo();
					user.setModuledesc(StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)modList.get(0)).trim()));
					user.setModuleid(StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)modList.get(1)).trim()));
					user.setModuleremarks(StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)modList.get(2)).trim()));
					if((String) modList.get(3)!=null){
					user.setRoleId(Integer.parseInt(StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String) modList.get(3)).trim())));
					}
				
					permissionList.add(user);
					
				}
			}
				
				int val=list.size()%Integer.parseInt(moduleColumnsInRow);
			    val=Integer.parseInt(moduleColumnsInRow)-val;
				for(int i=0;i<val;i++){
					
					user=new UserPermissionVo();
					user.setModuledesc("");
					user.setModuleid("");
					user.setModuleremarks("");
					user.setRoleId(-1);	
					permissionList.add(user);
					
				
			}
				
			
		} catch (Exception ex) {
			ex.getStackTrace();
		} 
		return permissionList;
	}
	
				
			
					
			

	
	public List<UserPermissionVo> getleftMenuBar(String ModuleID,int roleId,String moduleDb) throws SQLException  {
	
		List<UserPermissionVo> menuList =new ArrayList<UserPermissionVo>();

		try {
			logger.info("--------in--getleftMenuBar()------");
			

				  ArrayList mainlist=new ArrayList ();
				  ArrayList subList=new ArrayList ();
				  UserPermissionVo user=new UserPermissionVo();
				  
				  StringBuffer query = new StringBuffer(); 
				  query.append(" SELECT A.FUNCTION_DESC,A.FUNCTION_ID,A.PARENT_FUNCTION_ID,A.SEQUENCE_ID,'"+CommonFunction.checkNull(roleId).trim()+"' AS ROLE_ID,A.MODULE_ID "); 
				  query.append(" FROM SEC_FUNCTION_M A WHERE EXISTS ");
				  query.append(" (SELECT  1 ");
				  query.append(" FROM SEC_FUNCTION_M SFM, SEC_ROLE_ACCESS_M SRAM ");
				  query.append(" WHERE SFM.PARENT_FUNCTION_ID IN (SELECT FUNCTION_ID FROM SEC_FUNCTION_M WHERE PARENT_FUNCTION_ID=0 AND REC_STATUS='A' AND MODULE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(ModuleID).trim())+"') ");
				  query.append(" AND SFM.FUNCTION_ID = SRAM.FUNCTION_ID  ");
				  query.append(" AND SFM.MODULE_ID = SRAM.MODULE_ID AND SRAM.ROLE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId).trim())+"' AND SRAM.MODULE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(ModuleID).trim())+"' AND SFM.PARENT_FUNCTION_ID=A.FUNCTION_ID ");
				  query.append(" ) order by A.SEQUENCE_ID ASC ");

				  if(CommonFunction.checkNull(moduleDb).equalsIgnoreCase("UPL"))
				  {
					  mainlist=ConnectionUploadDAO.sqlSelect(query.toString());
				  }
				  else if(CommonFunction.checkNull(moduleDb).equalsIgnoreCase("REPORT"))
				  {
					  mainlist=ConnectionReportDumpsDAO.sqlSelect(query.toString());
				  }
				else 
				  {

					  mainlist=ConnectionDAO.sqlSelect(query.toString());
				  }
			  
		

			if(mainlist.size()>0){
			for(int i=0;i<mainlist.size();i++){
				subList=(ArrayList)mainlist.get(i);
				if(subList.size()>0){
				user=new UserPermissionVo();
			
				user.setFunctiondesc(StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)subList.get(0)).trim()));
				user.setFunctionid(StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)subList.get(1)).trim()));
				user.setParentid(StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)subList.get(2)).trim()));
				user.setSequenceid(StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)subList.get(3)).trim()));
				user.setRoleId(Integer.parseInt(StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)subList.get(4)).trim())));
				user.setModuleid(StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)subList.get(5)).trim()));
				menuList.add(user);
			
				} 
			}
			}
	
		
		}catch (Exception ex) {
			ex.getStackTrace();
		} 
		return menuList;
			

	}
	public int functionaccess(String id,String modid,int roleid,String functionid) throws SQLException {
		
		int access=0;

		
		try {
				logger.info("------------user id---------"+id);
				String sql="select count(1) from sec_user_access_m A,sec_role_access_m B WHERE A.ROLE_ID=B.ROLE_ID AND " +
						"A.MODULE_ID=B.MODULE_ID AND A.USER_ID='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(id).trim())+"' " +
								"AND A.MODULE_ID='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(modid).trim())+"' AND A.ROLE_ID='"+roleid+"' " +
								"AND A.REC_STATUS='A' AND B.FUNCTION_ID='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(functionid).trim())+"'";
				
				access=Integer.parseInt(ConnectionDAO.singleReturn(sql));
				
			}catch (Exception ex) {
				ex.getStackTrace();
			} 
			
			return  access;
			
		
	}
	public List<UserPermissionVo> getleftsubMenu(String functionid,String ModuleID,int roleId,String moduleDb) throws SQLException {
	
		List<UserPermissionVo> submenuList =new ArrayList<UserPermissionVo>();

		try {
			logger.info("--------in--getleftsubMenu()------");
		
				  ArrayList mainlist=new ArrayList ();
				  ArrayList subList=new ArrayList ();
				  UserPermissionVo user=new UserPermissionVo();
				  logger.info("------function id--------"+functionid);
				  String query="select SFM.FUNCTION_DESC,SFM.FUNCTION_ID,SFM.PARENT_FUNCTION_ID,SFM.SEQUENCE_ID from sec_function_m SFM," +
				  		"sec_ROLE_access_m SRAM where SFM.MODULE_ID='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(ModuleID).trim())+"' and SFM.PARENT_FUNCTION_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(functionid).trim())+"' AND SFM.REC_STATUS='A' AND" +
				  		" SFM.FUNCTION_ID = SRAM.FUNCTION_ID AND SFM.MODULE_ID = SRAM.MODULE_ID AND SRAM.ROLE_ID='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(roleId).trim())+"'" +
				  				" AND SRAM.MODULE_ID='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(ModuleID).trim())+"' " +
				  		"order by SFM.SEQUENCE_ID ASC";

				  if(CommonFunction.checkNull(moduleDb).equalsIgnoreCase("UPL"))
				  {
					  mainlist=ConnectionUploadDAO.sqlSelect(query);
				  }
				  else if(CommonFunction.checkNull(moduleDb).equalsIgnoreCase("REPORT"))
				  {
					  mainlist=ConnectionReportDumpsDAO.sqlSelect(query);
				  }
				else 
				  {
					  mainlist=ConnectionDAO.sqlSelect(query);
				  }
		
			if(mainlist.size()>0){
			for(int i=0;i<mainlist.size();i++){
				subList=(ArrayList)mainlist.get(i);
				if(subList.size()>0){
			
				user=new UserPermissionVo();
			
				user.setSubfunctiondesc(StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)subList.get(0)).trim()));
				user.setFunctionid(StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)subList.get(1)).trim()));
				user.setParentid(StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)subList.get(2)).trim()));
				user.setSequenceid(StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)subList.get(3)).trim()));
				
		
				submenuList.add(user);
				}
				} }
			
		}catch (SQLException ex) {
			ex.getStackTrace();
		} 
		return submenuList;
			

	}
	public List<UserPermissionVo> getPage(String userID,Integer MenuID,String moduleDb) throws SQLException{
		
		List<UserPermissionVo> pageList =new ArrayList<UserPermissionVo>();

		try {
			logger.info("--------in--getPage()------");
	

				  ArrayList mainlist=new ArrayList ();
				  ArrayList subList=new ArrayList ();
				  UserPermissionVo permissinObj = new UserPermissionVo();

				  String pagequery="SELECT FUNCTION_ID,PAGE_NAME FROM sec_function_m WHERE SEQUENCE_ID='"+ StringEscapeUtils.escapeHtml(CommonFunction.checkNull(MenuID).trim())+"'";
			
				  if(CommonFunction.checkNull(moduleDb).equalsIgnoreCase("UPL"))
				  {
					  mainlist=ConnectionUploadDAO.sqlSelect(pagequery);
				  }
				 else if(CommonFunction.checkNull(moduleDb).equalsIgnoreCase("REPORT"))
				  {
					  mainlist=ConnectionReportDumpsDAO.sqlSelect(pagequery);
				  }
				else 
				  {
					  mainlist=ConnectionDAO.sqlSelect(pagequery);
				  }
				  
			
				  if(mainlist.size()>0){
						for(int i=0;i<mainlist.size();i++){
							subList=(ArrayList)mainlist.get(i);
							if(subList.size()>0){
						permissinObj = new UserPermissionVo();
						permissinObj.setFunctionid(StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)subList.get(0)).trim()));
						permissinObj.setPagename(StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)subList.get(1)).trim()));
						
						pageList.add(permissinObj);
							}}
				}
		
		} catch (SQLException ex) {
			ex.getStackTrace();
		} 
		return pageList;
	}
	public String modulename(String modid,String moduleDb) throws SQLException {
	
		String module =null;
	
		
		try {
			logger.info("--------in--modulename()------");
			String moddesc="select module_desc from sec_module_m where module_id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(modid).trim())+"'";
				if(CommonFunction.checkNull(moduleDb).equalsIgnoreCase("UPL"))
				{
					module=StringEscapeUtils.escapeHtml(CommonFunction.checkNull(ConnectionUploadDAO.singleReturn(moddesc)).trim());
				}
				else if(CommonFunction.checkNull(moduleDb).equalsIgnoreCase("REPORT"))
				{
					module=StringEscapeUtils.escapeHtml(CommonFunction.checkNull(ConnectionReportDumpsDAO.singleReturn(moddesc)).trim());
				}
				else
				{
					module=StringEscapeUtils.escapeHtml(CommonFunction.checkNull(ConnectionDAO.singleReturn(moddesc)).trim());
				}
			} catch (Exception ex) {
				ex.getStackTrace();
			} 		
			return module;
	}

	public int modaccess(String id,String modid,int roleid,String moduleDb) throws SQLException {
		
		int modaccess=0;

		
		try {
				logger.info("------------user id---------"+id);
				String sql="select count(1) from sec_user_access_m where role_id='"+roleid+"' and module_id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(modid).trim())+"' AND  " +
						"user_id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(id).trim())+"'";
				
				if(CommonFunction.checkNull(moduleDb).equalsIgnoreCase("UPL"))
				{
				modaccess=Integer.parseInt(ConnectionUploadDAO.singleReturn(sql));
				}
				else if(CommonFunction.checkNull(moduleDb).equalsIgnoreCase("REPORT"))
				{
					modaccess=Integer.parseInt(ConnectionReportDumpsDAO.singleReturn(sql));
				}
				else
				{
					modaccess=Integer.parseInt(ConnectionDAO.singleReturn(sql));
				}
	
				
			} catch (Exception ex) {
				ex.getStackTrace();
			} 
			
			return  modaccess;
			
		
	}

	@Override
	public void saveUserFunctionLog(String userIDForMenu, String moduleID,
			String pageid, String bDate) {
		
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
		String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
		String dbo=resource.getString("lbl.dbPrefix");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		StringBuffer bufInsSql =	new StringBuffer();
		try{
			String qry="select convert(varchar(8),getdate(),108)";
			String bTime=ConnectionDAO.singleReturn(qry);
			logger.info("current time is ::: "+bTime);
			bDate = bDate.concat(" "+bTime);
		logger.info("In insert saveUserFunctionLog");
		
		bufInsSql.append("insert into SEC_USER_FUNCTION_LOG(USER_ID,MODULE_ID,FUNCTION_ID,ACCESS_DATE,IP_ADDRESS,SESSION_NO)");
		bufInsSql.append(" values ( ");
		bufInsSql.append(" ?," ); //USER_ID
		bufInsSql.append(" ?," ); //MODULE_ID
		bufInsSql.append(" ?," ); //FUNCTION_ID
		bufInsSql.append(dbo);
		bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')," ); //ACCESS_DATE
		bufInsSql.append(" ?," ); //IP_ADDRESS
		bufInsSql.append(" ? )" ); //SESSION_NO
	
		if((CommonFunction.checkNull(userIDForMenu)).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(userIDForMenu).trim()));

		if((CommonFunction.checkNull(moduleID).trim()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((moduleID).trim()); //MODULE_ID

				
		if((CommonFunction.checkNull(pageid).trim()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((pageid).trim()); //FUNCTION_ID
		
		if((CommonFunction.checkNull(bDate).trim()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((bDate).trim()); //ACCESS_DATE
		
		insertPrepStmtObject.addNull();  //IP_ADDRESS
		
		insertPrepStmtObject.addNull();  //SESSION_NO

		insertPrepStmtObject.setSql(bufInsSql.toString());
		logger.info("IN saveUserFunctionLog() insert query1 ### "+insertPrepStmtObject.printQuery());
		qryList.add(insertPrepStmtObject);
		boolean status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		logger.info("In saveUserFunctionLog......................"+status);

		// TODO Auto-generated method stub
		
	  }
		catch(Exception e)
		{
			logger.info("In saveUserFunctionLog......................"+e);
		}
		finally
		{
			insertPrepStmtObject=null;
			qryList=null;
			bufInsSql=null;
			dateFormatWithTime=null;
			resource=null;
			userIDForMenu=null;
			moduleID=null;
			pageid=null;
			bDate=null;
			dbo=null;
		}
	}

}	

