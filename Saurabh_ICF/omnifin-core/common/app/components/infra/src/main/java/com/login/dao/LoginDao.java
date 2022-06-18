package com.login.dao;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;


import com.cm.vo.ManualAdviceCreationVo;
import com.login.actionForm.LoginActionForm;
import com.login.dao.RoleDetailVO;
import com.login.dao.UserVO;
import com.login.roleManager.UserObject;

public interface LoginDao {

	String IDENTITY="LOGIND";
	public String isUserExist(String userName,String userPassword) throws SQLException;
	public String isforcepassword(String userid) throws SQLException;
	
	public UserVO saveLoginDetails(String userid,String sessId, String ipaddress) throws RemoteException, SQLException;
	public boolean saveLogOutDetails(String userid) throws SQLException;
	public String getbranch(String id) throws SQLException;
	
	public String getLogindate(String userid) throws SQLException;
	public String passwordexpiry(String userid);
	public String totalexpirydays() throws SQLException;
	public boolean passcount(String string, int count) throws SQLException;
	public String totalwarningdays() throws SQLException;

	public String passtimes() throws SQLException;
	public boolean statuslock(String name) throws SQLException;

	public UserVO companydata() throws SQLException;


	public UserObject getuserdetails(String id) throws SQLException;
	public UserVO statuscheck(String name) throws SQLException;
	public String loggedonce(String name) throws SQLException;
	public String userpass(String name) throws SQLException;
	public ArrayList getallbranch() throws SQLException;
	public ArrayList question(String name) throws SQLException;
	public int matchAnswer(String username, LoginActionForm loginForm) throws SQLException;
	public int matchuserid(String username) throws SQLException;
	public String businessdate();
	public String updateUserPassword(String username, String businessdate);
	public String portno() throws SQLException;
	public String hostname() throws SQLException;
	public String companyemail() throws SQLException;
	public String useremail(String username) throws SQLException;
	public void sendMail(String companyemail,String password,String host, String port,
			String useremail, String bodytext, String subject) throws SendFailedException, MessagingException;
	public String userpass() throws SQLException;


	public boolean adminPwdCheck(String adminPwd) throws SQLException;
	public boolean saveNewBusinessDate(ManualAdviceCreationVo vo);
	public String checkForBusinessDateOpen(Object ob);
	
	public String getuserEmailId(String username)throws SQLException;
	public String getSmtpHost()throws SQLException;
	public String getSmtpPort()throws SQLException;
	public String getSmtpMailAddress() throws SQLException;
	public String getSmtpMailPassword()throws SQLException;
	public String accountExpiryDate(String userid);
	public String getUserAccWarningDays() throws SQLException;
	public boolean updateUserStatus(String userid) throws SQLException;
	public boolean checkRecStatus(String userid) throws SQLException;
	public ArrayList getAboutCompany();
	public String checkEmailParameter();
	public abstract String isUserExistPass(String paramString)
	throws SQLException;
	public abstract String getuserName(String paramString)
	throws SQLException;
}
