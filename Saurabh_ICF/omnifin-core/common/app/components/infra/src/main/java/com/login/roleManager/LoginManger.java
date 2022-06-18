package com.login.roleManager;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.net.*;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.connect.md5;
import com.login.dao.LoginDao;
import com.login.roleManager.UserObject;
import com.login.actionForm.LoginActionForm;
import com.login.dao.UserVO;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
public class LoginManger {
	String saltParamLgMngr = "1";
	
	private static final Logger logger = Logger.getLogger(LoginManger.class.getName());
	
	  public LoginManger()
	  {
	  }

	  public LoginManger(String saltParam, String username)
	  {
	    if (null != saltParam)
	    {
	      this.saltParamLgMngr = saltParam;
	    }
	  }

	public UserObject isUserExist(LoginActionForm loginForm) {
		UserObject userObj =null;
		UserVO userVo = new UserVO();
		String id=null;
	
		try{

			LoginDao userlogin=(LoginDao)DaoImplInstanceFactory.getDaoImplInstance(LoginDao.IDENTITY);
			logger.info("Implementation class: "+userlogin.getClass());
			String userPass=loginForm.getUserPassword();
		    
			String Userid = loginForm.getUserName();
		    String salt = loginForm.getSaltPass();

		    String userPassByDB = userlogin.isUserExistPass(Userid);
		    String passWithsalt = salt + userPassByDB;
		    String userPassword = md5.sha256(passWithsalt);
			//String userPassword=md5.en(userPass);
	
			logger.info("password--"+userPassword);
		logger.info("userName--"+loginForm.getUserName());
		if (userPassword.equals(userPass))
	      {
		id= userlogin.isUserExist(loginForm.getUserName().trim(),
				userPassByDB);
		logger.info("--in if isUserExist id----"+id);
	      }
		
	if(!(CommonFunction.checkNull(id).equalsIgnoreCase(""))){
		userObj = new UserObject();
		logger.info("----------branch---"+id);
		
		logger.info("--in if isUserExist----");
		logger.info("Existing User: "+id);
	userObj=userlogin.getuserdetails(id);
	
	String branch= userlogin.getbranch(userObj.getBranchId());
	logger.info("----------branch---"+branch);
	
	
	logger.info("----getUserName()----"+userObj.getUserName());
	userVo=userlogin.companydata();

	String passwordexpiry = userlogin.passwordexpiry(id);
	logger.info("----------passwordexpiry---"+passwordexpiry);
	
	// Asesh Kumar Change Start Here
	String accountExpiryDay = userlogin.accountExpiryDate(id);
	logger.info("----LoginManger------accountExpiryDay--- ="+accountExpiryDay);
	String userAccWarningDays = userlogin.getUserAccWarningDays();
	logger.info("------LoginManger-----userAccWarningDays--- "+userAccWarningDays);
	userObj.setAccountExpiryDay(accountExpiryDay);
	userObj.setUserAccWarningDays(userAccWarningDays);	
	//Asesh Kumar End Here
	

	userObj.setUserId(id);
	userObj.setBranchName(branch);	
	
	//userObj.setUserName(userObj.getUserName());	

	userObj.setPassexpirydays(passwordexpiry);	
	String totalexpirydays = userlogin.totalexpirydays();
	logger.info("-----------totalexpirydays---"+totalexpirydays);	
	String warningdays = userlogin.totalwarningdays();
	logger.info("-----------warningdays---"+warningdays);
	System.out.println("Existing UserPassword: "+userPassword);
	
	userObj.setWarningdays(warningdays);	
	userObj.setTotalexpirydays(totalexpirydays);
	userObj.setBranchId(CommonFunction.checkNull(userObj.getBranchId()));
	userObj.setCompanyId(Integer.parseInt(CommonFunction.checkNull(userVo.getCompanyId())));
	userObj.setConpanyName(CommonFunction.checkNull(userVo.getConpanyName()));
	
		}
	
	
	
		}	catch(Exception e){
				e.printStackTrace();
		}

		return userObj;
	}
	public UserVO saveLoginDetails(String userid, String sessId, HttpServletRequest request) throws RemoteException, SQLException{
		UserVO userVo=new UserVO();
		
		if(userid!=null){
			
			LoginDao userlogin=(LoginDao)DaoImplInstanceFactory.getDaoImplInstance(LoginDao.IDENTITY);
			logger.info("Implementation class: "+userlogin.getClass());	
		logger.info("in saveLoginDetails --getRemoteAddr----"+request.getRemoteAddr());
		String ipaddress=request.getRemoteAddr();
		userVo=userlogin.saveLoginDetails(userid,sessId,ipaddress);
		
		}
		return userVo;
	}
	public String getLogindate(String userid) throws RemoteException, SQLException{

		String logindate=null;
		if(userid!=null){
	
			LoginDao userlogin=(LoginDao)DaoImplInstanceFactory.getDaoImplInstance(LoginDao.IDENTITY);
			logger.info("Implementation class: "+userlogin.getClass());
		logger.info("-----------user id---------------"+userid);	
		  logindate=userlogin.getLogindate(userid);
//	
		}
		return logindate;
	}

	public boolean saveLogOutDetails(String user) throws SQLException{
		boolean save_result=false;
		if(user!=null){
			LoginDao userlogin=(LoginDao)DaoImplInstanceFactory.getDaoImplInstance(LoginDao.IDENTITY);
			logger.info("Implementation class: "+userlogin.getClass());
			save_result=userlogin.saveLogOutDetails(user);
			
		}
		return save_result;
	}
	
	public String loggedonce(String name) throws SQLException{
		LoginDao userlogin=(LoginDao)DaoImplInstanceFactory.getDaoImplInstance(LoginDao.IDENTITY);
		logger.info("Implementation class: "+userlogin.getClass());
		String logid=userlogin.loggedonce(name);
		return logid;
	}
	public String GetOwnIP(HttpServletRequest request)
	{
		 String ipaddress="";	
	  try{

	  InetAddress ownIP=InetAddress.getLocalHost();

	  logger.info("IP of my system is := "+ownIP.getHostAddress());
//	  logger.info("IP getRemoteAddr ----:= "+);
	  ipaddress=request.getRemoteAddr();
	  }catch (Exception e){
		  logger.info("Exception caught ="+e.getMessage());
	  }
	return ipaddress;
	  }
	
}


