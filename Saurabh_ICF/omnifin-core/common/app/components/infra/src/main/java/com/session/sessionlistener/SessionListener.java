package com.session.sessionlistener;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.actions.ApproveCustomerAction;
import com.login.dao.LoginDao;
import com.login.roleManager.LoginManger;
import com.login.roleManager.UserObject;
import com.lockRecord.Vo.LockCheckVo;

public class SessionListener implements HttpSessionListener{
	
	private static final Logger logger = Logger.getLogger(SessionListener.class.getName());
	 public void init(ServletConfig config){  
     }  
  
     /** 
      * Adds sessions to the context scoped HashMap when they begin. 
      */  
     public void sessionCreated(HttpSessionEvent event){  
//         HttpSession    session  = event.getSession();  
//         ServletContext context  = session.getServletContext();  
//         Map  activeUsers = (Map)context.getAttribute("userobject");  
//         activeUsers.put(session.getId(), session);  
     }  
  
     /** 
      * Removes sessions from the context scoped HashMap when they expire 
      * or are invalidated. 
      */  
     public void sessionDestroyed(HttpSessionEvent event){  
         HttpSession    session     = event.getSession();  
         ServletContext context     = session.getServletContext(); 
        // add by saorabh
         try{
         LoginDao logindao=(LoginDao)DaoImplInstanceFactory.getDaoImplInstance(LoginDao.IDENTITY);
 	    logger.info("Implementation class: "+logindao.getClass());
 	    ArrayList aboutCompany= logindao.getAboutCompany();
 	    logger.info("In aboutCompany  : "+aboutCompany.size());  
 		// Code Ended..
 		LoginManger loginMgr = new LoginManger();
 		boolean status=false;
         UserObject  userobj=(UserObject)session.getAttribute("userobject");
         if(userobj!=null){
 			logger.info(" inside userobj----------"+userobj);
 			String userId=userobj.getUserId();
 			status=loginMgr.saveLogOutDetails(userId);
 			logger.info("IN LOGOUT ACTION----------"+status);
 			 logger.info("Session Id = "+ session.getId());
 			 
  			if(status){
 				context.removeAttribute("msg");
 				context.removeAttribute("msg1");

 				//for releasing lock record from application level object 
 				if(context!=null && userobj!=null)
 				{
 					ArrayList recordAccessObject =new ArrayList();
 					recordAccessObject = (ArrayList) context.getAttribute("recordAccessObject");
 					
 				     if(recordAccessObject!=null && recordAccessObject.size()>0)
 				     {
 				    		for(int i=0;i<recordAccessObject.size();i++)
 							{
 								LockCheckVo vo = new LockCheckVo();
 								vo = (LockCheckVo) recordAccessObject.get(i);
 								if((vo.getUserId().equalsIgnoreCase(userId)))
 								{

 									recordAccessObject.remove(i);
 								}
 							} 
 				     }
 				}
 			}	 
 			 
 			}	
         }catch (Exception e) {
			e.printStackTrace();
		}
         //end by saorabh
        /* Map activeUsers = (Map)context.getAttribute("userobject");  
         activeUsers.remove(session.getId()); */ 
         logger.info("With Listener: "+session.getId());
     }  

}
