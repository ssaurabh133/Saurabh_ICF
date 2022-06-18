package com.login.action;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.lockRecord.action.ReleaseRecordFromObject;
import com.logger.LoggerMsg;
import com.login.roleManager.LoginManger;
import com.login.roleManager.UserObject;
import com.login.actionForm.LoginActionForm;
import com.login.dao.LoginDao;
import com.login.dao.UserVO;
import com.session.utility.SessionRenew;

public class LoginAction extends Action {

	private static final Logger logger = Logger.getLogger(LoginAction.class
			.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String resultStr = "error";
		String passcount = "";
		logger.info("In Login Action");
		HttpSession session = request.getSession(false);
	    String salt = CommonFunction.checkNull(session.getAttribute("randomAlphaNumericSalt"));
	    request.setAttribute("randomAlphaNumericSaltFroPass", salt);
	    logger.info("In Login Action salt : " + salt);
	    session = SessionRenew.recreateSesssion(session, request, response);

		LoginActionForm loginForm = (LoginActionForm) form;
		String password = "";
	    String name = "";
	    loginForm.setSaltPass(salt);
	    name = loginForm.getUserName();
	    password = loginForm.getUserPassword();
	    LoginManger loginMgr;
	    //LoginManger loginMgr;
	    if (salt != null)
	    {
	      loginMgr = new LoginManger(salt, name);
	    }
	    else
	    {
		loginMgr = new LoginManger();
	    }
		UserObject user = new UserObject();
		//HttpSession session = request.getSession(false);
		 LoginDao logindao=(LoginDao)DaoImplInstanceFactory.getDaoImplInstance(LoginDao.IDENTITY);
		    logger.info("Implementation class: "+logindao.getClass());
		/*String password = "";
		String name = "";*/
		    UserVO userVo = new UserVO();
		    userVo = logindao.companydata();
		    int companyId = userVo.getCompanyId();
		    String companyName = userVo.getConpanyName();
        // Surendra Code For Dyanamic Email ID, Contact No
		
		    ArrayList aboutCompany= logindao.getAboutCompany();
		    logger.info("In aboutCompany  : "+aboutCompany.size());   
		    request.setAttribute("aboutCompany",aboutCompany);
		
		//Surendra Code Ended..
		
        
		String flag = CommonFunction.checkNull(request.getParameter("flag"));
		logger.info("in LoginAction----- flag----" + flag);
		//UserVO userVo = new UserVO();
		String relogin = "firstlogin";
		int count = 0;
		String forceflagpass = "";
		String totalexpirydays = "";
		String logid = "";
		String warningdays = "";
		String statusFlag = "";
		int passwordexpirydays = 0;
		int accountExpiryDays=0;
		String userAccWarningDays="";
		String accountFlag = "";
		String loginUserIdLOv="";
		if (flag.equalsIgnoreCase("logininput")) {

			user = loginMgr.isUserExist(loginForm);

			name = loginForm.getUserName();
			password = loginForm.getUserPassword();
			String id = loginForm.getUserName();
			loginMgr.saveLogOutDetails(name);
			//user = loginMgr.isUserExist(loginForm);
			
		      logid = loginMgr.loggedonce(name);
		      user = logindao.getuserdetails(id);
		      String branch = logindao.getbranch(user.getBranchId());
		      userVo = logindao.companydata();
		      user.setUserId(id);
		      user.setBranchName(branch);
		      user.setCompanyId(companyId);
		      user.setConpanyName(companyName);
		      loginMgr.saveLogOutDetails(name);
		      
			logid = loginMgr.loggedonce(name);
			forceflagpass = logindao.isforcepassword(name);
			totalexpirydays = user.getTotalexpirydays();

			warningdays = user.getWarningdays();
			userAccWarningDays=user.getUserAccWarningDays();
			logger.info("--in relogin----");


			user.setLastLoginTime(loginMgr.getLogindate(name));
			String businessdate = logindao.businessdate();
			user.setBusinessdate(businessdate);
			logindao.passcount(name, count);
			if (statusFlag.equalsIgnoreCase("PE")) {
				resultStr = "success1";
			} else {
				resultStr = "success";
			}

			session.setAttribute("userobject", user);
			session.setAttribute("sessionID", session.getId());
			if(user!=null)
			{
				loginUserIdLOv=user.getUserId();
			}
			session.setAttribute("loginUserIdLOv", loginUserIdLOv);
			
			userVo = loginMgr.saveLoginDetails(name, session.getId(),request);
			//session.setMaxInactiveInterval(-2);

			logger.info("After Save login details");

		}

		else {
			logger.info("----------if login for the first time---------");

			name = loginForm.getUserName();
			password = loginForm.getUserPassword();

			userVo = logindao.statuscheck(name);

			// if (loginForm.getUserName() != null &&
			// loginForm.getUserPassword() != "")
			if (!name.equalsIgnoreCase("") && !password.equalsIgnoreCase("")) {
				if (userVo != null) {

					user = loginMgr.isUserExist(loginForm);
					logid = loginMgr.loggedonce(name);
					if(logindao.checkRecStatus(name))
					{
						request.setAttribute("flag","EXP");
						resultStr = "error";
						logger.info("ResultStrr:----LoginAction----"+ resultStr);
						logger.info("Your Account Is Expired -------");
					}

					else if (user != null) {

						String statuslock = userVo.getAccountStatus();

						if (!CommonFunction.checkNull(statuslock).equalsIgnoreCase("")
								&& CommonFunction.checkNull(statuslock).equalsIgnoreCase("U")) {

							logger.info("--in if----");

							String userid = user.getUserId();

							forceflagpass = logindao.isforcepassword(userid);
							//totalexpirydays = user.getTotalexpirydays();
							 totalexpirydays = logindao.totalexpirydays();

							warningdays = user.getWarningdays();
							userAccWarningDays = user.getUserAccWarningDays();
							if (!(user.getPassexpirydays().equalsIgnoreCase(""))
									&& (Integer.parseInt(user
											.getPassexpirydays())) > 0) {
								passwordexpirydays = Integer.parseInt(user
										.getPassexpirydays());

							} else {
								passwordexpirydays = 0;
							}
							if (!(user.getAccountExpiryDay().equalsIgnoreCase("")) && (Integer.parseInt(user.getAccountExpiryDay())) > 0) {
								accountExpiryDays = Integer.parseInt(user.getAccountExpiryDay());

							} else {
								accountExpiryDays = 0;
							}
							logger.info("accountExpiryDays --------------"+accountExpiryDays);
							logger.info("userAccWarningDays -------------"+userAccWarningDays);
							logger.info("--in if2----");

							if (!userid.equalsIgnoreCase("")) {

								logger.info("--in if3----");
								if(CommonFunction.checkNull(userAccWarningDays).trim().equalsIgnoreCase(""))
									userAccWarningDays="0";
								if(accountExpiryDays<=Integer.parseInt(userAccWarningDays)){
									logger.info("Your Account will Expire soon ");
									request.setAttribute("aflag", "AE");
									request.setAttribute("dayLeft",accountExpiryDays);
									accountFlag="AE";
									
								}
								if(accountExpiryDays<=0){
									user.setLastLoginTime(loginMgr.getLogindate(name));
									String businessdate = logindao.businessdate();
									user.setBusinessdate(businessdate);

									logindao.passcount(name, count);
									session.setAttribute("userobject",user);
									session.setAttribute("sessionID",session.getId());
									if(user!=null)
									{
										loginUserIdLOv=user.getUserId();
									}
									session.setAttribute("loginUserIdLOv", loginUserIdLOv);
									userVo = loginMgr.saveLoginDetails(name,session.getId(),request);
									logindao.updateUserStatus(userid);
									request.setAttribute("flag","EXP");
									logger.info("Account Expired -----");
									resultStr = "error";	
								} 
								else if (forceflagpass.equalsIgnoreCase("Y")|| (passwordexpirydays > Integer.parseInt(warningdays))) {

									request.setAttribute("flag", "PE");
									statusFlag = "PE";

									if (forceflagpass.equalsIgnoreCase("Y")|| ((passwordexpirydays) >= (Integer.parseInt(totalexpirydays)))) {

										resultStr = "forcechangepass";
										request.setAttribute("name", loginForm.getUserName());
						                loginForm.getUserName();
						                session.setAttribute("forcechangepassForSalt", resultStr);
									}

									else if ((logid.equalsIgnoreCase(""))|| (passwordexpirydays < Integer.parseInt(warningdays))) {

										user.setLastLoginTime(loginMgr.getLogindate(name));
										String businessdate = logindao.businessdate();
										user.setBusinessdate(businessdate);

										logindao.passcount(name, count);
										if (statusFlag.equalsIgnoreCase("PE")) {
											resultStr = "success1";
										} else {
											resultStr = "success";
										}

										session.setAttribute("userobject",user);
										session.setAttribute("sessionID",session.getId());
										if(user!=null)
										{
											loginUserIdLOv=user.getUserId();
										}
										session.setAttribute("loginUserIdLOv", loginUserIdLOv);
										
										userVo = loginMgr.saveLoginDetails(name, session.getId(),request);
										// session.setMaxInactiveInterval(-2);

										// for releasing lock record from
										// application level object
										UserObject userobj = (UserObject) session.getAttribute("userobject");
										ServletContext context = getServlet().getServletContext();
										if (context != null) {
											boolean Lflag = ReleaseRecordFromObject.releaselockedRecord(userobj.getUserId(),context);
										}
										
									} else {

										request.setAttribute("userName", name);
										request.setAttribute("userPassword",password);
										resultStr = "logonce";
										request.setAttribute("userName", name);

									}
								} else if ((logid.equalsIgnoreCase(""))&& (passwordexpirydays < Integer.parseInt(warningdays))) {

									user.setLastLoginTime(loginMgr.getLogindate(name));

									String businessdate = logindao.businessdate();
									user.setBusinessdate(businessdate);

									logindao.passcount(name, count);
									if (statusFlag.equalsIgnoreCase("PE")|| accountFlag.equalsIgnoreCase("AE")) {
										resultStr = "success1";
									} else {
										resultStr = "success";
									}

									session.setAttribute("userobject", user);
									session.setAttribute("sessionID", session.getId());
									
							
									if(user!=null)
									{
										loginUserIdLOv=user.getUserId();
									}
									session.setAttribute("loginUserIdLOv", loginUserIdLOv);
									
									userVo = loginMgr.saveLoginDetails(name,session.getId(),request);
									// session.setMaxInactiveInterval(-2);
									// for releasing lock record from
									// application level object
									UserObject userobj = (UserObject) session.getAttribute("userobject");
									ServletContext context = getServlet().getServletContext();
									if (context != null && userobj != null) {
										boolean Lflag = ReleaseRecordFromObject.releaselockedRecord(userobj.getUserId(), context);
									}
									
								} else {
									logger.info("In Already Login........part...........");
									request.setAttribute("userName", name);
									request.setAttribute("userPassword",password);
									resultStr = "logonce";
									request.setAttribute("userName", name);
									
								}
							}
						}
					}

					else if ((name != null && !CommonFunction.checkNull(name).equalsIgnoreCase(""))&& (!CommonFunction.checkNull(userVo.getAccountStatus()).equalsIgnoreCase("L"))) {
						String userName = CommonFunction.checkNull(userVo.getUserName());

						if ((!userName.equalsIgnoreCase(""))&& userName != null) {
							String countval = userVo.getPassCount();
							logger.info("##countval##" + countval);
							count = Integer.parseInt(countval);
							logger.info("##count##" + count);
							String passtimes = logindao.passtimes();

							count++;
							boolean status = logindao.passcount(name, count);
							if (count >= Integer.parseInt(passtimes)) {

								boolean statuscheck = logindao.statuslock(name);

								request.setAttribute("flag1", "L");
								count = 0;
								boolean passstatus = logindao.passcount(name,count);

							}
						}
						resultStr = "error";
						request.setAttribute("flag", "error");
						logger.info("ResultStrr:----LoginAction----"+ resultStr);

					} else if (userVo.getAccountStatus() != null && CommonFunction.checkNull(userVo.getAccountStatus()).equalsIgnoreCase("L")) {
						request.setAttribute("flag", "L");
					}

				}
			}
		}

		ServletContext context = getServlet().getServletContext();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		//for check User session start
		String strFlag="";	
		String eodCheckFlag="";
		try
		{
			eodCheckFlag = ConnectionDAO.singleReturn("SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='EOD_RUNNING_FLAG'");
			
			if(eodCheckFlag.equalsIgnoreCase("Y"))
			{
				strFlag="EODRunning";
			}
		}
		catch (Exception ex) 
		{
			LoggerMsg.debug("error in UserSessionCheck class --EODRunningCheck Method"+ ex.getMessage());
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			context.setAttribute("msg", "E");
			return mapping.findForward("logout");
		}
		
		
		logger.info("ResultStrr:----flag----" + request.getAttribute("flag"));
		logger.info("ResultStrr:----aflag----" + request.getAttribute("aflag"));
		if (request.getAttribute("flag") == null && request.getAttribute("aflag")== null) {
			request.setAttribute("flag", "userdonotexist");
		}
		logger.info("ResultStrr:----LoginAction----" + resultStr);
		
		return mapping.findForward(resultStr);
	}
}
