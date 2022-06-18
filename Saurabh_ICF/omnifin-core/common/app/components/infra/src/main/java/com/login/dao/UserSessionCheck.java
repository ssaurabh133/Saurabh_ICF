package com.login.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringEscapeUtils;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.logger.LoggerMsg;
import com.login.roleManager.UserObject;


public class UserSessionCheck extends HttpServlet
{	
	
	private static final Logger logger = Logger.getLogger(UserSessionCheck.class.getName());
	public static String checkSameUserSession(UserObject userObject,String sessionId,String functionId,HttpServletRequest request)throws SQLException, IOException
	{
		
		String forwardString = "";
		boolean flag = false;
		logger.info("function id is ......................... "+functionId);
		flag = checkUsersSession(userObject,sessionId);
		if(flag)
		{
			logger.info("sameUserSession.,,flag......................"+flag);
			return "sameUserSession";
		}
//		if(!(functionId.equalsIgnoreCase("4001001")||functionId.equalsIgnoreCase("4001006")))
//		{
//			ServletContext sct=request.getSession().getServletContext();
//			String bODCheck= (String)sct.getAttribute("BODCheck");
//			if(CommonFunction.checkNull(bODCheck).trim().equalsIgnoreCase("block"))
//			forwardString="BODCheck";
//		}
		
									flag = BODCheck(userObject,sessionId,functionId);
									logger.info("BODCheck.1111,flag......................"+flag);
									if(flag)
									{
										logger.info("BODCheck.22222,flag......................"+flag);
										forwardString="BODCheck";
									}		
							//		flag = EODCheck(userObject,sessionId);
							//		if(flag)
							//		{
							//			logger.info("EODCheck.,,flag......................"+flag);
							//			return "EODCheck";
							//		}
									
		
		return forwardString;
	}
	
	public static boolean checkUsersSession(UserObject userObject,String sessionId)
	{
		String realSessionID = null;
		
		boolean flag =false;
		String userid="";
		if(userObject!=null)
		{
			userid= userObject.getUserId();
		}
		
		LoggerMsg.info("In UserSessionCheck , sessionId .......................................... "+sessionId);
		try
		{
			String sql="SELECT SESSION_NO from sec_user_log where LOGIN_STATUS ='Y' AND USER_ID = '"+userid+"'";
			LoggerMsg.info("query : "+sql);
			realSessionID=StringEscapeUtils.escapeHtml(CommonFunction.checkNull(ConnectionDAO.singleReturn(sql)).trim());
			LoggerMsg.info("realSessionID : "+realSessionID);
			if(sessionId.equalsIgnoreCase(realSessionID))
			{
				LoggerMsg.info("both session id is same....");
				
			}else
			{
				LoggerMsg.info("Forwarding to login page....");
				flag=true;
				
			}
			sql=null; // add by saorabh
		}
		catch (Exception ex) 
		{
			LoggerMsg.debug("error in UserSessionCheck class --checkUsersSession Method"+ ex.getMessage());
		}
		return flag;
	}
	
	public static boolean EODCheck (UserObject userObject,String sessionId)
	throws SQLException , IOException
	{
		logger.info("In EODCheck...............");
		boolean flag =false;
		String eodCheckFlag="";
		try
		{
								//			CreditManagementDAO service=new CreditManagementDAOImpl();
								//			eodCheckFlag = ConnectionDAO.singleReturn("SELECT 1 FROM parameter_mst WHERE '"+userObject.getBusinessdate()+"' <> (SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='BUSINESS_DATE') and 'B' = (SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='EOD_BOD_FLAG')");
								//			
								//			if(eodCheckFlag==null)
								//			{
								//				ArrayList loggedUserList = ConnectionDAO.sqlSelect("SELECT SESSION_NO,USER_ID FROM sec_user_log WHERE LOGIN_STATUS='Y'");	
								//				logger.info("loggedUserList ******************************** "+loggedUserList.size());
								//				//flag = service.updateLoginStatus(userObject.getUserId());
								//				logger.info("bodCheckFlag value ****************************** "+eodCheckFlag);
								//				if(eodCheckFlag==null)
								//				{
								//					flag=true;
								//				}
								//				
								//			}
			eodCheckFlag = ConnectionDAO.singleReturn("SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='EOD_RUNNING_FLAG'");
			
			if(eodCheckFlag.equalsIgnoreCase("Y"))
			{
					flag=true;
			}
		}
		catch (Exception ex) 
		{
			LoggerMsg.debug("error in UserSessionCheck class --EODRunningCheck Method"+ ex.getMessage());
		}
		return flag;
	}
	
	public static boolean BODCheck (UserObject userObject,String sessionId,String functionId)
	throws SQLException , IOException
	{
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
		String dbType=resource.getString("lbl.dbType");
		String dbo=resource.getString("lbl.dbPrefix");
		String dateFormat=resource.getString("lbl.dateInDao");
		logger.info("In BODCheck...............");
		String bodCheckFlag="";
		String bDate="";
		boolean flag =false;
		if(userObject!=null)
		{
			bDate=userObject.getBusinessdate();
		}
		try
		{
			//CreditManagementDAO service=new CreditManagementDAOImpl();   
			if(!functionId.equalsIgnoreCase("4001001"))
			{
				StringBuilder query = new StringBuilder();
				query.append("SELECT count(*) FROM parameter_mst WHERE '"+bDate+"' = ");
				query.append(dbo);
				query.append("DATE_FORMAT( (SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='BUSINESS_DATE'), '"+dateFormat+"') and 'E' = (SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='EOD_BOD_FLAG')");
				logger.info("Check query for BOD: "+query.toString());
				bodCheckFlag = ConnectionDAO.singleReturn(query.toString());
				//bodCheckFlag = ConnectionDAO.singleReturn("SELECT 1 FROM parameter_mst WHERE 'E' = (SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='EOD_BOD_FLAG')");
				logger.info("bodCheckFlag value ****************************** "+bodCheckFlag);
				if(CommonFunction.checkNull(bodCheckFlag).equalsIgnoreCase("0"))
				{
					flag=true;       
				}
				query = null;// add by saorabh
			}
		}
		catch (Exception ex) 
		{
			LoggerMsg.debug("error in UserSessionCheck class --BODRunningCheck Method"+ ex.getMessage());
		}
		return flag;
	}
		
}
