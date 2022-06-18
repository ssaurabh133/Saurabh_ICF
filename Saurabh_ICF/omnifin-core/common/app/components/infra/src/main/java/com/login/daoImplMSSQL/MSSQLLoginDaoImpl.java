package com.login.daoImplMSSQL;

import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.vo.ManualAdviceCreationVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.connect.md5;
import com.login.actionForm.LoginActionForm;
import com.login.dao.LoginDao;
import com.login.dao.UserVO;
import com.login.roleManager.UserObject;



public class MSSQLLoginDaoImpl implements LoginDao {
	private static final Logger logger = Logger.getLogger(MSSQLLoginDaoImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
	
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");

	public String isUserExist(String userName, String userPassword) throws SQLException {
		
		String userid="" ;
		logger.info("DB Type : " + dbType);
		System.out.println("Enter for DB isUserExist action");
		try {
			
			
//			
				logger.info("password...."+userPassword);
				String user=" SELECT User_ID FROM sec_user_m WHERE User_ID='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(userName)).trim()+"' " +
						"AND USER_PASSWORD='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(userPassword).trim())+"'" +
								" AND REC_STATUS='A' AND ACCOUNT_STATUS='U'";
				userid=CommonFunction.checkNull(ConnectionDAO.singleReturn(user));
				
			
			
			}catch(Exception e){
				e.getStackTrace();
				 
			}
		 
		 
			
			return  userid;
			
		
	}
	public String userpass(String userName) throws SQLException {
		
		String userpass="";

		logger.info("Enter userpass");
		try {
		
				
				String user=" SELECT USER_PASSWORD FROM sec_user_m WHERE User_ID='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(userName).trim())+"' " +
					" AND REC_STATUS='A' AND ACCOUNT_STATUS='U'";
				userpass=CommonFunction.checkNull(ConnectionDAO.singleReturn(user));
				
				
			
				}catch(Exception e){
					e.getStackTrace();
					 
				}
			
			
			
			return  userpass;
			
		
	}
	public UserObject getuserdetails(String id) throws SQLException {
		

		UserObject user=new UserObject();
		logger.info("Enter getuserdetails");
		try {
			
//				
				  ArrayList list=new ArrayList ();
				  ArrayList modList=new ArrayList ();
		
				
				String userdata="SELECT USER_NAME,USER_DEF_BRANCH,rec_status,ACCOUNT_STATUS" +
						" FROM sec_user_m WHERE User_ID='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(id).trim())+"'";
				
				list=ConnectionDAO.sqlSelect(userdata);
			
			
				for(int i=0;i<list.size();i++){
					modList=(ArrayList)list.get(i);
					if(modList.size()>0){

					user.setUserName(StringEscapeUtils.escapeSql(CommonFunction.checkNull((String)modList.get(0)).trim()));
					user.setBranchId(StringEscapeUtils.escapeSql(CommonFunction.checkNull((String)modList.get(1)).trim()));

					
					user.setRecStatus(StringEscapeUtils.escapeSql(CommonFunction.checkNull((String)modList.get(2)).trim()));
					user.setAccountStatus(StringEscapeUtils.escapeSql(CommonFunction.checkNull((String)modList.get(3)).trim()));
		
				//	logger.info("........."+	modList);

					
					

					}
				}
					
				
			} catch(Exception e){
				e.getStackTrace();
				 
			} 
			return user;
		}
	
	public String getbranch(String branchid) throws SQLException {
		
		String userbranch="";

		logger.info("Enter getbranch");
		try {
			
		
				String user="select branch_desc from com_branch_m where BRANCH_ID ='"+CommonFunction.checkNull(branchid)+"'";
				userbranch=CommonFunction.checkNull(ConnectionDAO.singleReturn(user));
	
			
				}
			
		catch(Exception e){
			e.getStackTrace();
			 
		} 
			
			return  userbranch;
			
		
	}
	public String getLogindate(String id) throws SQLException {
		
		String logindate="";

		logger.info("Enter getLogindate");
		try {
			
		
				StringBuilder login=new StringBuilder();
				login.append("select ");
				login.append("TOP 1 ");
				login.append(dbo);
				login.append("DATE_FORMAT(LOGIN_DATE,'"+dateFormatWithTime+"') AS LOGIN_DATE FROM sec_user_log where Login_status='N' ORDER BY  USER_LOG_ID DESC ");
				
				
				logger.info("Query: "+login.toString());
				logindate=CommonFunction.checkNull(ConnectionDAO.singleReturn(login.toString()));
	
			if(logindate.equalsIgnoreCase("")){
				
				StringBuilder newdate = new StringBuilder();
				newdate.append("select ");
				newdate.append(dbo);
				newdate.append("DATE_FORMAT(" );
				newdate.append(dbo);
				newdate.append("SYSDATE(),'"+dateFormatWithTime+"')");
			    logger.info("newdate: "+newdate.toString());				
				logindate=CommonFunction.checkNull(ConnectionDAO.singleReturn(newdate.toString()));
				
			}}
			catch(Exception e){
				e.getStackTrace();
				 
			} 
			
			return  logindate;
			
		
	}
	public String isforcepassword(String userid)  throws SQLException{
		
		String userlogin ="";
	
		logger.info("Enter isforcepassword");
		try {
			

				String passflag="select FORCED_PASSWORD_FLAG from sec_user_m where USER_ID='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(userid).trim())+"'";
				userlogin=CommonFunction.checkNull(ConnectionDAO.singleReturn(passflag));				
				
				
			
			} catch(Exception e){
				e.getStackTrace();
				 
			} 	
			return userlogin;
	}

	public UserVO companydata()  throws SQLException{
	

		UserVO user=new UserVO();
		logger.info("Enter companydata");
		try {
			
//				
				  ArrayList list=new ArrayList ();
				  ArrayList modList=new ArrayList ();
			
					String sql ="select COMPANY_DESC,COMPANY_ID from com_company_m where COMPANY_ID=1";					

					list=ConnectionDAO.sqlSelect(sql);
				
				
					for(int i=0;i<list.size();i++){
						modList=(ArrayList)list.get(i);
						if(modList.size()>0){
		
						user.setConpanyName(StringEscapeUtils.escapeSql(CommonFunction.checkNull((String)modList.get(0)).trim()));
						user.setCompanyId(Integer.parseInt(StringEscapeUtils.escapeSql(CommonFunction.checkNull((String)modList.get(1)).trim())));
					
			
					
					
						}
					}
						
				
				} catch(Exception e){
					e.getStackTrace();
					 
				} 	
				return user;
			}
	public String totalexpirydays()  throws SQLException{
		
		String expirydays="";
		logger.info("Enter totalexpirydays");
		try {
			
			
					String sql ="SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='PASSOWRD_EXPIRY_DAYS'";					
					expirydays=CommonFunction.checkNull(ConnectionDAO.singleReturn(sql));
	
			
			} catch(Exception e){
				e.getStackTrace();
				 
			} 	
			
			return expirydays;
			
		
	}
	public String totalwarningdays()  throws SQLException{
		
	String warningdays="";
		logger.info("Enter totalwarningdays");
		try {
			
			
					String sql ="SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='WARNING_DAYS'";					
					warningdays=CommonFunction.checkNull(ConnectionDAO.singleReturn(sql));
					 			
		
			}catch(Exception e){
				e.getStackTrace();
				 
			} 	
			return warningdays;
			
		
	}
	public String passwordexpiry(String userid) {
		
	String days="";
		logger.info("Enter passwordexpiry");
		try {
		
				String query="SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='BUSINESS_DATE'";
				String businessdate=CommonFunction.checkNull(ConnectionDAO.singleReturn(query));
	
				
				String sqlquery="select LAST_PASSWORD_DATE from sec_user_m where user_id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(userid))+"'";
				String lastpassdate=CommonFunction.checkNull(ConnectionDAO.singleReturn(sqlquery));
	
			    StringBuilder dayquery = new StringBuilder();
			    dayquery.append("select ");
			    dayquery.append(dbo);
			    dayquery.append("DATEDIFF(  ");
			    dayquery.append(dbo);
			    dayquery.append("sysdate(),'"+lastpassdate+"')");
			    
				//String dayquery="select DATEDIFF(sysdate(),'"+lastpassdate+"') as value from dual";
			    logger.info("dayquery: "+dayquery.toString());
				days=CommonFunction.checkNull(ConnectionDAO.singleReturn(dayquery.toString()));
				
					if(days.equalsIgnoreCase("0")){
						days="1";
					}
					
					logger.info(dayquery);
				
			} catch(Exception e){
				e.getStackTrace();
				 
			} 	
			
			return days;
			
		
	}
	public String businessdate() {
	
		String businessdays="";
		logger.info("Enter businessdate");
		try {
			        StringBuilder bDateQuery=new StringBuilder();
			        bDateQuery.append("SELECT ");
			        bDateQuery.append(dbo);
			        bDateQuery.append("DATE_FORMAT(PARAMETER_VALUE,'"+dateFormat+"') FROM parameter_mst WHERE PARAMETER_KEY='BUSINESS_DATE'");
					logger.info("bDateQuery: "+bDateQuery.toString());					
					businessdays=CommonFunction.checkNull(ConnectionDAO.singleReturn(bDateQuery.toString()));
			
			}catch(Exception e){
				e.getStackTrace();
				 
			} 	
			
			return businessdays;
			
		
	}

	public UserVO saveLoginDetails(String userid,String sessId,String ipaddress) throws RemoteException, SQLException {
		
		UserVO userVo =new UserVO();
			ArrayList logList = new ArrayList();
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

		boolean status = false;		
		logger.info("Enter saveLoginDetails in Login dao impl");
		
		try {
			
			StringBuilder bDateQuery=new StringBuilder();
	        bDateQuery.append("SELECT ");
	        bDateQuery.append(dbo);
	        bDateQuery.append("DATE_FORMAT(PARAMETER_VALUE,'"+dateFormat+"') FROM parameter_mst WHERE PARAMETER_KEY='BUSINESS_DATE'");
			logger.info("bDateQuery: "+bDateQuery.toString());	
			String businessdate=CommonFunction.checkNull(ConnectionDAO.singleReturn(bDateQuery.toString()));
			userVo.setBusinessdate(businessdate);
			
			StringBuffer bufInsSql =	new StringBuffer();

			bufInsSql.append(" insert into sec_user_log (USER_ID,LOGIN_DATE,BUSINESS_DATE,LOGIN_STATUS,IP_ADDRESS,SESSION_NO) ");

			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?, " ); //USER_ID
			bufInsSql.append(dbo);
			bufInsSql.append("sysdate(), " ); //LOGIN_DATE
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'), " ); //BUSINESS_DATE
			bufInsSql.append(" 'Y'," ) ; //LOGIN_STATUS
			bufInsSql.append(" ?," );//IP_ADDRESS
			bufInsSql.append(" ?)" ); //SESSION_NO
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(userid).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(userid).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(userVo.getBusinessdate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(userVo.getBusinessdate()).trim());
	

			 if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ipaddress).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(ipaddress).trim());
			 if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(sessId).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(sessId).trim());
				
			
			 insertPrepStmtObject.setSql(bufInsSql.toString());
			 logger.info("IN saveLoginDetails() update query1 ### "+insertPrepStmtObject.printQuery());
			 logList.add(insertPrepStmtObject);
			 status=ConnectionDAO.sqlInsUpdDeletePrepStmt(logList);
			
		
		} catch(Exception e){
			e.getStackTrace();
			 
		} 	
		return userVo;
	}
	
public boolean saveLogOutDetails(String userid) throws SQLException {
		
logger.info("Enter saveLogOutDetails");
		
ArrayList logList = new ArrayList();

boolean status = false;		
StringBuffer bufInsSql =	new StringBuffer();

try{
	bufInsSql.append(" UPDATE sec_user_log");
	bufInsSql.append(" SET LOGOUT_DATE = ");
	bufInsSql.append(dbo);
	bufInsSql.append("sysdate(),LOGIN_STATUS ='N' ");
	bufInsSql.append(" WHERE USER_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(userid).trim())+"'  AND LOGIN_STATUS='Y'");
	logger.info("IN saveLogOutDetails() update query1 ### "+bufInsSql.toString());
	logList.add(bufInsSql);
	status=ConnectionDAO.sqlInsUpdDelete(logList);
	}catch(Exception e){
			e.getStackTrace();
			 
		} 	

	return status;
}
	
	public UserVO statuscheck(String userid) throws SQLException {
	
		UserVO user=new UserVO();
		logger.info("Enter statuscheck");
		try {
			
				  ArrayList list=new ArrayList ();
				  ArrayList modList=new ArrayList ();
		
					String sql ="select ACCOUNT_STATUS,USER_NAME,PASS_COUNT from sec_user_m where USER_ID='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(userid).trim())+"'";
					logger.info("sqlQuery: "+sql);
					list=ConnectionDAO.sqlSelect(sql);
					
					
					for(int i=0;i<list.size();i++){
						modList=(ArrayList)list.get(i);
						if(modList.size()>0){
		
						user.setAccountStatus(StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)modList.get(0)).trim()));
						user.setUserName(StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)modList.get(1)).trim()));
						user.setPassCount(StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)modList.get(2)).trim()));
			
					//	logger.info("........."+	modList);
				
						}
					}
		}catch(Exception e){
						e.getStackTrace();
						 
					} 
				return user;
			}
	
	public boolean passcount(String id, int count) throws SQLException {
		

		ArrayList passList = new ArrayList();
		
	
		boolean status = false;		
		logger.info("Enter passcount");
 
			StringBuilder bufInsSql =	new StringBuilder();
		try{	
			
			bufInsSql.append("UPDATE sec_user_m SET PASS_COUNT='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(count).trim())+"' WHERE USER_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"'");
			logger.info("Update password count Query: "+bufInsSql.toString());
			passList.add(bufInsSql);
		
			status=ConnectionDAO.sqlInsUpdDelete(passList);
	}catch(Exception e){
				e.getStackTrace();
				 
			} 
			return status;
	}	
	public boolean statuslock(String userid) throws SQLException {

		ArrayList statusList = new ArrayList();
		
		boolean status = false;		
		logger.info("Enter statuslock");
	
		StringBuilder bufInsSql =	new StringBuilder();
		try{
		
			bufInsSql.append("UPDATE sec_user_m SET ACCOUNT_STATUS='L' WHERE USER_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(userid).trim())+"'");
			logger.info("Update ACCOUNT_STATUS Query: "+bufInsSql.toString());
		    statusList.add(bufInsSql);
	        status=ConnectionDAO.sqlInsUpdDelete(statusList);
		}catch(Exception e){
			e.getStackTrace();
			 
		} 
		return status;
	}
	
	public String passtimes() throws SQLException {
	
	
		String passattempts="";

		logger.info("Enter passtimes");
		try {
		
		String sql="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='PASSWORD_ATTEMPTS'";
		logger.info("Password Attempts Query: "+sql);
		passattempts=CommonFunction.checkNull(ConnectionDAO.singleReturn(sql));

			}
		catch(Exception e){
			e.getStackTrace();
			 
		} 
		
		return  passattempts;
		
}
	public String loggedonce(String userid) throws SQLException {
		
			String logged="";

			logger.info("Enter loggedonce");
			try {
			
			String sql="select USER_LOG_ID  from sec_user_log where LOGIN_STATUS ='Y' AND USER_ID='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(userid).trim())+"'";
			logger.info("USER_LOG_ID Query: "+sql);
			logged=CommonFunction.checkNull(ConnectionDAO.singleReturn(sql));
			logger.info(logged);

				}
			catch(Exception e){
				e.getStackTrace();
				 
			} 
			
			return  logged;
			
	}
	
	public ArrayList getallbranch() throws SQLException {
		
		UserObject userobj=null;
		String userbranch="";
       ArrayList mainList = new ArrayList();
		logger.info("Enter getallbranch");
		try {
		
				 ArrayList list=new ArrayList ();
				  ArrayList branchList=new ArrayList ();

				String user="select BRANCH_ID,branch_desc from com_branch_m ";
				logger.info("BRANCH_ID,branch_desc Query: "+user);
				list=ConnectionDAO.sqlSelect(user);
				for(int i=0;i<list.size();i++){
					branchList=(ArrayList)list.get(i);
					if(branchList.size()>0){
						userobj=new UserObject();
						userobj.setBranchId(StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)branchList.get(0)).trim()));
						userobj.setBranchName(StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)branchList.get(1)).trim()));
					
						mainList.add(userobj);
					//	logger.info("........."+	modList);
				
						}
					
					}
				}
		catch(Exception e){
			e.getStackTrace();
			 
		} 
			
			return  mainList;
			
		
	}
	public ArrayList question(String userid) throws SQLException {
		
		UserObject userobj=null;
		String userbranch="";
       ArrayList mainList = new ArrayList();
		logger.info("Enter question");
		try {
			
				 ArrayList list=new ArrayList ();
				  ArrayList quesList=new ArrayList ();

				String user="select SECURITY_QUESTION1,SECURITY_QUESTION2,SECURITY_ANSWER1,SECURITY_ANSWER2 from sec_user_m where USER_ID='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(userid).trim())+"'";
				logger.info("sec_user_m Query: "+user);
				
				list=ConnectionDAO.sqlSelect(user);
				for(int i=0;i<list.size();i++){
					quesList=(ArrayList)list.get(i);
					if(quesList.size()>0){
						userobj=new UserObject();
						userobj.setQuestion1((StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)quesList.get(0)).trim())));
						userobj.setQuestion2((StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)quesList.get(1)).trim())));
						userobj.setAnswer1((StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)quesList.get(2)).trim())));
						userobj.setAnswer2((StringEscapeUtils.escapeHtml(CommonFunction.checkNull((String)quesList.get(3)).trim())));
					
						mainList.add(userobj);
					//	logger.info("........."+	modList);
				
						}
					
					}
				
			
			} catch(Exception e){
				e.getStackTrace();
				 
			} 
			
			return  mainList;
			
		
	}

	public int matchAnswer(String userid,LoginActionForm loginForm) throws SQLException {

		int count=0;
		ArrayList qryList = new ArrayList();
		boolean status = false;
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		logger.info("Enter matchAnswer");
		StringBuilder querBuilder = new StringBuilder();
		String ans1= StringEscapeUtils.escapeHtml(CommonFunction.checkNull(loginForm.getAns1()).trim());
		String ans2= StringEscapeUtils.escapeHtml(CommonFunction.checkNull(loginForm.getAns2()).trim());
		logger.info("ans1 ****************************************** "+ans1);
		logger.info("ans2 ****************************************** "+ans2);
		
		try {
			
			querBuilder.append("select count(1) from sec_user_m where SECURITY_ANSWER1=dbo.md5(?) and SECURITY_ANSWER2=dbo.md5(?) and USER_ID=?");
			
			if (CommonFunction.checkNull(loginForm.getAns1()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(ans1);
			
			if (CommonFunction.checkNull(loginForm.getAns2()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else  
				insertPrepStmtObject.addString(ans2);
					
			if (CommonFunction.checkNull(userid).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else  
				insertPrepStmtObject.addString(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(userid).trim()));
			
			insertPrepStmtObject.setSql(querBuilder.toString());

			logger.info("IN select query ### "+ insertPrepStmtObject.printQuery());
			//qryList.add(insertPrepStmtObject);
			ArrayList list = ConnectionDAO.sqlSelectPrepStmt(insertPrepStmtObject);
			
			ArrayList list1 = new ArrayList();
			list1 =(ArrayList) list.get(0);
			//logger.info("list1.get(0).toString() ........................ "+list1.get(0).toString());
			if (Integer.parseInt(CommonFunction.checkNull(list1.get(0).toString())) > 0) {
				count =1;
			}
			
			}catch(Exception e){
				e.getStackTrace();
				 
			} 
			
			return  count;
	}
	public int matchuserid(String userid) throws SQLException {
		
	
		int count=0;
    
		logger.info("Enter matchuserid");

			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			StringBuilder querBuilder = new StringBuilder();
			String userId= StringEscapeUtils.escapeSql(CommonFunction.checkNull(userid).trim());
			
			try {
				
				querBuilder.append("select count(1) from sec_user_m where USER_ID=?");
				
				if (CommonFunction.checkNull(userid).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userId);
				
								
				insertPrepStmtObject.setSql(querBuilder.toString());

				logger.info("IN select query ### "+ insertPrepStmtObject.printQuery());
				//qryList.add(insertPrepStmtObject);
				ArrayList list = ConnectionDAO.sqlSelectPrepStmt(insertPrepStmtObject);
				
				ArrayList list1 = new ArrayList();
				list1 =(ArrayList) list.get(0);
				//logger.info("list1.get(0).toString() ........................ "+list1.get(0).toString());
				if (Integer.parseInt(CommonFunction.checkNull(list1.get(0).toString())) > 0) {
					count =1;
				}
				logger.info("In select value list.size()......................"	+ list.size());
				
				}catch(Exception e){
					e.getStackTrace();
					 
				} 
			return  count;
	}
	
	public String updateUserPassword(String userid,String businessdate) {
		ArrayList updatelist = new ArrayList();
		ArrayList updatePasslist = new ArrayList();
		String updPassword = null;
		boolean status = false;

		final String charset = "0123456789abcdefghijklmnopqrstuvwxyz";

		try {

			int length = 8;
			Random rand = new Random(System.currentTimeMillis());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < length; i++) {
				int pos = rand.nextInt(charset.length());
				sb.append(charset.charAt(pos));
			}

			updPassword = sb.toString();
			logger.info("In updateUserPassword()updPassword:: "
					+ updPassword);

			String passQuery = "SELECT USER_PASSWORD,USER_LAST_PASSWORD_1,USER_LAST_PASSWORD_2,USER_LAST_PASSWORD_3,USER_LAST_PASSWORD_4,USER_LAST_PASSWORD_5 FROM SEC_USER_M WHERE USER_ID='"
					+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(userid).trim()) + "'";
			logger.info("passQuery" + passQuery);
			updatePasslist = ConnectionDAO.sqlSelect(passQuery);

			logger.info("IN updateUserPass() update Password query1 ### "
					+ passQuery);
			logger.info("updateUserPass " + updatePasslist.size());

			for (int i = 0; i < updatePasslist.size(); i++) {
				logger.info("updateUserPassList "
						+ updatePasslist.get(i).toString());
				ArrayList data = (ArrayList) updatePasslist.get(i);
				if (data.size() > 0) {
					String userPass = (CommonFunction.checkNull(data.get(0))
							.toString());
					String userPass1 = (CommonFunction.checkNull(data.get(1))
							.toString());
					String userPass2 = (CommonFunction.checkNull(data.get(2))
							.toString());
					String userPass3 = (CommonFunction.checkNull(data.get(3))
							.toString());
					String userPass4 = (CommonFunction.checkNull(data.get(4))
							.toString());
					String userPass5 = (CommonFunction.checkNull(data.get(5))
							.toString());

				
					String pass = md5.en(updPassword);

					logger.info("password" + pass);
					logger
							.info("In updateUserData.....................................Dao Impl");
					StringBuilder query =new StringBuilder();
					query.append("UPDATE SEC_USER_M SET FORCED_PASSWORD_FLAG='Y',LAST_PASSWORD_DATE=");
					query.append(dbo);
					query.append("sysdate() ");
					query.append(",USER_LAST_PASSWORD_1='"+userPass+"'");
					query.append(",USER_LAST_PASSWORD_2='"+ userPass1+"' ");
					query.append(",USER_LAST_PASSWORD_3='"+ userPass2+"' ");
					query.append(",USER_LAST_PASSWORD_4='"+ userPass3+"' ");
					query.append(",USER_LAST_PASSWORD_5='"+ userPass4+"' ");
					query.append(",USER_PASSWORD='"+pass+"' ");
					query.append(" WHERE USER_ID= '"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(userid).trim())+"'");
					updatelist.add(query);
					logger.info("In updateUserPassword: " + query.toString());
					status = ConnectionDAO.sqlInsUpdDelete(updatelist);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return updPassword;
	}
	public String portno() throws SQLException {
		
		
		String portno="";
    
		logger.info("Enter portno");
		try {
			

				String query="select Parameter_value from parameter_mst where PARAMETER_KEY='PORT_NO'";
				logger.info("PORT_NO query: "+query);
				portno=ConnectionDAO.singleReturn(query);
			
			}catch(Exception e){
				e.getStackTrace();
				 
			} 
			
			return  portno;
			
		
	}
	public String hostname() throws SQLException {
		
		
		String host="";
    
		logger.info("Enter hostname");
		try {
			

				String query="select Parameter_value from parameter_mst where PARAMETER_KEY='HOST'";
				logger.info("HOST query: "+query);
				host=ConnectionDAO.singleReturn(query);
			
			}catch(Exception e){
				e.getStackTrace();
				 
			} 
			
			return  host;
			
		
	}
	public String companyemail() throws SQLException {
		
		
		String companyemail="";
    
		logger.info("Enter companyemail");
		try {
			

				String query="select Parameter_value from parameter_mst where PARAMETER_KEY='COMPANY_EMAIL'";
				logger.info("COMPANY_EMAIL query: "+query);			
				companyemail=ConnectionDAO.singleReturn(query);
			
			}catch(Exception e){
				e.getStackTrace();
				 
			} 
			
			return  companyemail;
			
		
	}
	public String useremail(String userid) throws SQLException {
		
		String useremail="";

		logger.info("Enter useremail");
		try {
			

				String query="select USER_EMAIL from sec_user_m where USER_ID='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(userid).trim())+"'";
				logger.info("USER_EMAIL query: "+query);		
				useremail=ConnectionDAO.singleReturn(query);
			
			}catch(Exception e){
				e.getStackTrace();
				 
			} 
			
			return  useremail;
		
	}
public String userpass() throws SQLException {
		
		String userpass="";

		logger.info("Enter useremail");
		try {
			

				String query="select Parameter_value from parameter_mst where PARAMETER_KEY='COMPAY_PASSWORD'";
				logger.info("COMPAY_PASSWORD query: "+query);
				userpass=ConnectionDAO.singleReturn(query);
			
			}catch(Exception e){
				e.getStackTrace();
				 
			} 
			
			return  userpass;
		
	}
	public void sendMail(String from,String password, String host, String port, String to, String message, String Subject) throws SendFailedException,MessagingException
    {
       
       Session session = null;
       Properties props = new Properties();
       props.put(host, port);
	session = Session.getDefaultInstance(props, null);

//       props.setProperty("host", "127.0.0.1");
//       session = Session.getInstance(props);
       MimeMessage msg = new MimeMessage(session);
       Multipart multipart = new MimeMultipart();
       BodyPart bodyPart = new MimeBodyPart();
       bodyPart.setContent(message, "text/html");
       multipart.addBodyPart(bodyPart);
       msg.setContent(multipart);
       msg.setSubject(Subject);
       msg.setFrom(new InternetAddress(from));
       msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
       Transport.send(msg);
   }
	
	public boolean adminPwdCheck(String adminPwd) throws SQLException {

		boolean status = false;		
		logger.info("Enter statuslock");
		try {
		String query = "select USER_ID from sec_user_m WHERE USER_PASSWORD=dbo.MD5('"+adminPwd+"') and USER_ID='ADMIN'";
		logger.info("USER_ID query: "+query);
			String userId = ConnectionDAO.singleReturn(query);
			if(userId!=null && !userId.equalsIgnoreCase(""))
			{
				status=true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}
	
	
	
	public String checkForBusinessDateOpen(Object ob) {

		String status = "";
		CallableStatement cst = null;
		int statusProc = 0;
		Connection con=null;
		
		ManualAdviceCreationVo vo = (ManualAdviceCreationVo) ob;

		try {
			con = ConnectionDAO.getConnection();
			con.setAutoCommit(false);
		
				logger.info("checkForBusinessDateOpen , getCompanyId .....is... "+vo.getCompanyId());
				logger.info("getAutherDate................................................. "+vo.getNewBusinessDate());			
				logger.info("call GetYearPeriodID...... ");			

				cst = con.prepareCall("call GetYearPeriodID (?,"+dbo+"STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?)");
				
				cst.setInt(1,Integer.parseInt(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCompanyId()).trim())));
				cst.setString(2,StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getNewBusinessDate()).trim()));
				cst.registerOutParameter(3, Types.INTEGER);
				cst.registerOutParameter(4, Types.INTEGER);
				cst.registerOutParameter(5, Types.CHAR);
				cst.registerOutParameter(6, Types.CHAR);
				cst.registerOutParameter(7, Types.CHAR);
				cst.registerOutParameter(8, Types.CHAR);
				logger.info("cst ........................... "+cst.toString());
				statusProc = cst.executeUpdate();
				logger.info("Status Proc: "+statusProc);
				String s1 = cst.getString(7);
				String s2 = cst.getString(8);
				logger.info("s1: " + s1);
				logger.info("s2: " + s2);
				
				if(s1.equalsIgnoreCase("S")){
					
					logger.info("After Proc inside If ");
					status="S";
					con.commit();
				}else
				{
					status=s2;
					con.rollback();
				}
		} catch (Exception e) {

			try{
				con.rollback();
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			logger.info(e.getMessage());
		}
		finally{
			try{
				con.close();
				cst.close();
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			
		}
		logger.info("value of status ............................... "+status);
		return status;
		
		}

	public boolean saveNewBusinessDate(ManualAdviceCreationVo vo)
	{
		
		logger.info("In saveNewBusinessDate.....................................Dao Impl");
		String maxId = "";
		ArrayList qryList =  new ArrayList();
		StringBuffer bufInsSql = new StringBuffer();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		boolean status=false;
		

		String stat="";
		try{
		        bufInsSql.append("Insert into business_date_change_hst(USER_ID,CURRENT_BUSINESS_DATE,NEW_BUSINESS_DATE,SYSTEM_DATETIME,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				
				bufInsSql.append(" ?, " );
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'), " );
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'), " );
				bufInsSql.append(dbo);
				bufInsSql.append("sysDate(), " );
				bufInsSql.append(" ?, " );
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"')" );
				bufInsSql.append(" )");
				
				if(CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else 
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getMakerId()).trim());
				
				
				if(CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getMakerDate()).trim());
				
				
				if(CommonFunction.checkNull(vo.getNewBusinessDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getNewBusinessDate()).trim());
				
						
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))//MAKER_ID
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getMakerId()).trim());
				if (CommonFunction.checkNull(vo.getMakerDate())//MAKER_DATE
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getMakerDate()).trim());	
				
				insertPrepStmtObject.setSql(bufInsSql.toString());	  
	             logger.info("IN saveNewBusinessDate insert query1 ### "+insertPrepStmtObject.printQuery());
	             
	             qryList.add(insertPrepStmtObject);		           
	              status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);    
	              logger.info("In Applicant......................"+status);
	              if(status)
				  {
	            	  ArrayList queryList=new ArrayList();
	            	  ArrayList qryListUp = new ArrayList();
	          		  PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
	            	  StringBuilder queryUpdate = new StringBuilder();
	            	  queryUpdate.append("UPDATE parameter_mst set PARAMETER_VALUE=");
	            	  queryUpdate.append(dbo);
	            	  queryUpdate.append("STR_TO_DATE(?,'"+dateFormat+"'),MAKER_ID=?,MAKER_DATE=");
	            	  queryUpdate.append(dbo);
	            	  queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') WHERE PARAMETER_KEY='BUSINESS_DATE'");
	          		
	          		if (CommonFunction.checkNull(vo.getNewBusinessDate()).equalsIgnoreCase(""))
	        			updatePrepStmtObject.addNull();
	        		else
	        			updatePrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getNewBusinessDate()).trim()));
	          		if (CommonFunction.checkNull(vo.getMakerId())
	        				.equalsIgnoreCase(""))
	        			updatePrepStmtObject.addNull();
	        		else
	        			updatePrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
	        		if (CommonFunction.checkNull(vo.getMakerDate())
	        				.equalsIgnoreCase(""))
	        			updatePrepStmtObject.addNull();
	        		else
	        			updatePrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
	        		
	        		updatePrepStmtObject.setSql(queryUpdate.toString());

	        		logger.info("IN updateListOfvalue() insert query1 ### "+ updatePrepStmtObject.printQuery());
	        		qryListUp.add(updatePrepStmtObject);
	        		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListUp);
						
				  }
																	
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		return status;		
	}
	// *************************************** Asesh kumar ****************************************
	public String getuserEmailId(String userid)
	{
		String usermailId="";
		logger.info("Enter userEmail");
		try {	
				String emailquery=" SELECT USER_EMAIL FROM sec_user_m WHERE User_ID='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(userid).trim())+"'";
				logger.info("emailquery ### "+ emailquery);
				usermailId=CommonFunction.checkNull(ConnectionDAO.singleReturn(emailquery));
			}catch(Exception e){
					e.getStackTrace();	 
			}
			return  usermailId;
	}
	public String getSmtpHost()
	{
		String host="";
		logger.info("Enter smtpHost");
		try {	
				String hostquery=" SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='SMTP_HOST'; ";
				logger.info("hostquery ### "+ hostquery);
				host=CommonFunction.checkNull(ConnectionDAO.singleReturn(hostquery));
			}catch(Exception e){
					e.getStackTrace();	 
			}
			return  host;
	}
	public String getSmtpPort()
	{
		String port="";
		logger.info("Enter smtpPort");
		try {	
				String portquery=" SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='SMTP_PORT'; ";
				logger.info("SMTP_PORT ### "+ portquery);
				port=CommonFunction.checkNull(ConnectionDAO.singleReturn(portquery));
			}catch(Exception e){
					e.getStackTrace();	 
			}
			return  port;
	}
	public String getSmtpMailAddress()
	{
		String mailAddress="";
		logger.info("Enter getSmtpMailAddress");
		try {	
				String mailquery=" SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='SMTP_MAIL_ADDRESS'; ";
				logger.info("SMTP_MAIL_ADDRESS ### "+ mailquery);
				mailAddress=CommonFunction.checkNull(ConnectionDAO.singleReturn(mailquery));
			}catch(Exception e){
					e.getStackTrace();	 
			}
			return  mailAddress;
	}
	public String getSmtpMailPassword()
	{
		String mailPassword="";
		logger.info("Enter getSmtpMailPassword");
		try {	
				String mailquery=" SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='SMTP_MAIL_PASSWORD'; ";
				logger.info("SMTP_MAIL_PASSWORD ### "+ mailquery);
				mailPassword=CommonFunction.checkNull(ConnectionDAO.singleReturn(mailquery));
			}catch(Exception e){
					e.getStackTrace();	 
			}
			return  mailPassword;
	}
	//  ************************************* End method **********************************************
	
	// Asesh space start  
	public String accountExpiryDate(String userid) {
		
		String days="";
			logger.info("Enter accountExpiryDate");
			try {
			
				String sqlquery="select VALIDITY_DATE from sec_user_m where user_id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(userid))+"'";
				String accountExpiryDate=CommonFunction.checkNull(ConnectionDAO.singleReturn(sqlquery));
	
				String query="SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='BUSINESS_DATE'";
				String businessdate=CommonFunction.checkNull(ConnectionDAO.singleReturn(query));
					
				String dayquery="select dbo.DATEDIFF('"+accountExpiryDate+"',dbo.SYSDATE()) ";
					
				days=CommonFunction.checkNull(ConnectionDAO.singleReturn(dayquery));
				logger.info("days............................."+days);
				logger.info("dayquery............................."+dayquery);
					
				} catch(Exception e){
					e.getStackTrace();
					 
				} 	
				
				return days;
				
		}


	public String getUserAccWarningDays() throws SQLException {
		
		String userAccWarningDays="";
			logger.info("Enter totalwarningdays");
			try {
						String sql ="SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='USER_ACCOUNT_WARNING_DAYS'";					
						userAccWarningDays=CommonFunction.checkNull(ConnectionDAO.singleReturn(sql));
						 			
			
				}catch(Exception e){
					e.getStackTrace();
					 
				} 	
				return userAccWarningDays;
				
			
		}
	
	public boolean updateUserStatus(String userid)
	{
		boolean statusFlage=false;
		ArrayList statusList = new ArrayList();
		StringBuilder updateQuery=new StringBuilder();
		logger.info("in updateUserStatus ");
		try
		{
			updateQuery.append(" UPDATE sec_user_m SET rec_status='X' where user_id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(userid))+"'");
			logger.info("updateUserStatus query " +updateQuery);
			statusList.add(updateQuery);
			statusFlage=ConnectionDAO.sqlInsUpdDelete(statusList);
		}
		catch(Exception exception)
		{
			exception.getStackTrace();
		}
		return statusFlage;
		
	}
	
	public boolean checkRecStatus(String userid)
	{
		boolean statusFlage=false;
		try {
			String status="";
			String statuQuery=" SELECT REC_STATUS FROM SEC_USER_M WHERE USER_ID='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(userid).trim())+"'";
			logger.info("statuQuery -----"+statuQuery);
			status=CommonFunction.checkNull(ConnectionDAO.singleReturn(statuQuery));
			if(status.equalsIgnoreCase("X"))
			{
				statusFlage=true;
			}
			else
			{
				statusFlage=false;
			}
		}catch(Exception e){
				e.getStackTrace();	 
		}

		return statusFlage;
	}
	
	@Override
	public ArrayList getAboutCompany() {
		
		
		ArrayList list = new ArrayList();

		logger.info("In getAboutCompany: ");
		
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();

		try {
			 StringBuilder query =new StringBuilder();
			 query.append("SELECT TOP 1 COMPANY_DESC,PHONE_NO,ITHELP_EMAIL_ID from com_company_m WHERE REC_STATUS='A' ");
			logger.info("Query in getAboutCompany-----" + query.toString());
		
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			
			query=null;
	         int size=mainlist.size();
			for (int i = 0; i < size; i++) {
				
				subList = (ArrayList) mainlist.get(i);
				UserVO vo = new UserVO();
				if (subList.size()> 0) {       
					vo.setCompName((CommonFunction.checkNull(subList.get(0)).trim()));
					vo.setPhoneNo((CommonFunction.checkNull(subList.get(1)).trim()));
					vo.setEmailId((CommonFunction.checkNull(subList.get(2)).trim()));
					}
				subList.clear();
				subList=null;
				list.add(vo);
				vo=null;
			}
			mainlist.clear();
			mainlist=null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	@Override
	public String checkEmailParameter() 
	{
		logger.info("in  checkEmailParameter().");
		String parameter="";
		try
		{
			String query="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='EMAIL_FOR_PASSWORD_CHANGE'";
			logger.info("Query for checkEmailParameter :  "+query);			
			parameter=ConnectionDAO.singleReturn(query);
			if(CommonFunction.checkNull(parameter).trim().equalsIgnoreCase(""))
				parameter="N";
			query=null;		
		}
		catch(Exception e)
		{e.printStackTrace();}		
		return parameter;	
	}
		public String getuserName(String paramString)
	  {
		  //dummy method
		  
		  return null;
	  }
		public String isUserExistPass(String paramString)
		{
			//dummy method
			return null;
		}
		
}


