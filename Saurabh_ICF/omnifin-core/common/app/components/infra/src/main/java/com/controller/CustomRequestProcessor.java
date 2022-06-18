package com.controller;

import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.RequestProcessor;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;
import com.login.roleManager.UserObject;


public class CustomRequestProcessor extends RequestProcessor {
	
	//String forwardPath = null;
	//private static final String loginPath = "/loadLMS";
	//ActionMapping loginActionMapping = null;
	private static final Logger logger = Logger.getLogger(CustomRequestProcessor.class.getName());	
    protected boolean processPreprocess (
        HttpServletRequest request,
        HttpServletResponse response) {
    	
//    	 Runtime rs =  Runtime.getRuntime();
//    	 logger.info("Total Memory(KB) available for JVM before Garbage Collection = "+rs.totalMemory()/1024);
//    	 logger.info("Free memory(KB) available for JVM before Garbage Collection = "+rs.freeMemory()/1024);
//         rs.gc();
//         logger.info("Free memory(KB) available for JVM after Garbage Collection = "+rs.freeMemory()/1024);
    	
    	 //Properties file change starts
    	
	    String generalQuery = "select PARAMETER_VALUE from PARAMETER_MST where PARAMETER_KEY='GENERALIZATION_PROP'";
	    String result = ConnectionDAO.singleReturn(generalQuery);
	    logger.info("Generalization parameter query : " + generalQuery + " Result :  "+result);
	    if(result.equalsIgnoreCase("Y"))
	    {
	    request.getSession().setAttribute(
				Globals.LOCALE_KEY, Locale.FRANCE);
	    }
	    else
	    {
	    	request.getSession().setAttribute(
					Globals.LOCALE_KEY, Locale.ENGLISH);
	    }
	  //Properties file change ends
	    
    	int sessTime=1;
    	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
    	String sessionTime=resource.getString("idle.sessionTime");
    	if(!CommonFunction.checkNull(sessionTime).equalsIgnoreCase(""))
    		{
    		 sessTime=Integer.parseInt(sessionTime);
    	   }
    	
    	logger.info("In: sessionTime "+sessTime);
        HttpSession session = request.getSession(false);
        
        logger.info("In: processPreprocess"+request.getServletPath());
    
	    if(session.getAttribute("userobject")!= null)
	    {
	    	session = request.getSession(true);
	    	logger.info("In if Befor getMaxInactiveInterval: processPreprocess "+session.getMaxInactiveInterval());
	    	session.setMaxInactiveInterval(60*sessTime);
	    	logger.info("In if after getMaxInactiveInterval: processPreprocess "+session.getMaxInactiveInterval());
	  	    	
	    }
	    else
	    {
	        try{
	        	logger.info("In else: processPreprocess"+session);
	        	//loginActionMapping = processMapping(request,response,loginPath );
	        	//		forwardPath =loginActionMapping.getInputForward().getPath();
	        	//		doForward( forwardPath, request, response );
	        		//	return false;
	             
	          }
	        catch(Exception ex){
	        	logger.info("In: processPreprocess"+ex);
	        }
	    }
	    String trackQuery = "select PARAMETER_VALUE from PARAMETER_MST where PARAMETER_KEY='LOGIN_TRACK'";
	    String track = ConnectionDAO.singleReturn(trackQuery);
	    logger.info("Tracking parameter query : " + trackQuery + " Result :  "+track);
	    if(CommonFunction.checkNull(track).trim().equalsIgnoreCase("Y"))
	    {	    
	    	
	    	PrepStmtObject insertPrepStmtObject=null;
	    	UserObject userobj=(UserObject)session.getAttribute("userobject");
	    	String url=request.getServletPath();
		    String userId=null;
	 	    String branchId=null;
	 	    String businessDate=null;
		    if(userobj!=null)
		    {
		    	userId=userobj.getUserId();
		 	    branchId=userobj.getBranchId();
		 	    businessDate=userobj.getBusinessdate();
		    }
		    String functionId=CommonFunction.checkNull(session.getAttribute("functionId"));
		    ArrayList qryList=new ArrayList();
		    String dbType=resource.getString("lbl.dbType");
		    StringBuilder query = new StringBuilder();
		    try
		    {
		    	insertPrepStmtObject = new PrepStmtObject();	
		    	if(dbType.equalsIgnoreCase("MSSQL"))
		    		query.append("insert into user_function_m(URL,USER_ID,BRANCH_ID,FUNCTION_ID,BUSINESS_DATE) values(?,?,?,?, GETDATE())");
		    	else
		    		query.append("insert into user_function_m(URL,USER_ID,BRANCH_ID,FUNCTION_ID,BUSINESS_DATE) values(?,?,?,?,DATE_ADD(CURDATE(),INTERVAL CURTIME() HOUR_SECOND))");
		    	
		    	if (CommonFunction.checkNull(url).equalsIgnoreCase(""))
		    		insertPrepStmtObject.addNull();
		    	else
		    		insertPrepStmtObject.addString(url.trim());
		    	
		    	if (CommonFunction.checkNull(userId).equalsIgnoreCase(""))
		    		insertPrepStmtObject.addNull();
		    	else
		    		insertPrepStmtObject.addString(userId.trim());
		    	
		    	if (CommonFunction.checkNull(branchId).equalsIgnoreCase(""))
		    		insertPrepStmtObject.addNull();
		    	else
		    		insertPrepStmtObject.addString(branchId.trim());
		    	
		    	if (CommonFunction.checkNull(functionId).equalsIgnoreCase(""))
		    		insertPrepStmtObject.addNull();
		    	else
		    		insertPrepStmtObject.addString(functionId.trim());
		    	    	
		    	insertPrepStmtObject.setSql(query.toString());
		    	logger.info("Insert Query for track  :  "+insertPrepStmtObject.printQuery());
		    	qryList.add(insertPrepStmtObject);
		    	ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);	
		    }
		    catch (Exception e) 
			{e.printStackTrace();}
		    finally
		    {
		    
				insertPrepStmtObject=null;
			    userobj=null;
			    url= null;
			    userId= null;
			    branchId= null;
			    functionId= null;
			    businessDate= null;
			    qryList.clear();
			    qryList=null;
			    dbType= null;
			    query = null;
			    resource=null;
		    }
	    }
    return true;
}

protected void processContent(HttpServletRequest request,
            HttpServletResponse response) {
	
	
	int sessTime=1;
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String sessionTime=resource.getString("idle.sessionTime");
	if(!CommonFunction.checkNull(sessionTime).equalsIgnoreCase(""))
		{
		 sessTime=Integer.parseInt(sessionTime);
	   }
	
	logger.info("In: sessionTime "+sessTime);
        
	logger.info("In: processContent"+request.getServletPath());
	
	 HttpSession session = request.getSession(false);
     
	    if(session.getAttribute("userobject")!= null)
	    {
	    	session = request.getSession(true);
	    	logger.info("In if Befor getMaxInactiveInterval: processContent "+session.getMaxInactiveInterval());
	    	session.setMaxInactiveInterval(60*sessTime);
	    	logger.info("In if after getMaxInactiveInterval: processContent "+session.getMaxInactiveInterval());
	  	    	
	    }
	    else
	    {
	        try{
	        	logger.info("In else: processContent"+session);
//	        	loginActionMapping = processMapping( request, response,loginPath );
//    			forwardPath =loginActionMapping.getInputForward().getPath();
//    			doForward( forwardPath, request, response );
    			
	          }
	        catch(Exception ex){
	        	logger.info("In: processContent"+ex);
	        }
	    }
    super.processContent(request, response);
}
}
