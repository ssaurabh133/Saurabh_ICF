package com.masters.actions;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.business.Security.MasterBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.connect.LookUpInstanceFactory;
import com.login.dao.LoginDao;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.dao.MasterDAO;
import com.masters.vo.UserMasterVo;
import com.utils.Mail;


public class UserMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(UserMasterDispatchAction.class.getName());
	
	public ActionForward openAddUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info(" in openAddUser()");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
				String strFlag="";	
				if(sessionId!=null)
				{
					strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
				}
				
				logger.info("strFlag .............. "+strFlag);
				if(!strFlag.equalsIgnoreCase(""))
				{
					if(strFlag.equalsIgnoreCase("sameUserSession"))
					{
						context.removeAttribute("msg");
						context.removeAttribute("msg1");
					}
					else if(strFlag.equalsIgnoreCase("BODCheck"))
					{
						context.setAttribute("msg", "B");
					}
					return mapping.findForward("logout");
				}
				request.setAttribute("save", "save");
			    return mapping.findForward("openAdd");	
			}
	public ActionForward openEditUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
		        UserMasterVo userMasterVo=new UserMasterVo(); 
				logger.info("In openEditUser");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
				String strFlag="";	
				if(sessionId!=null)
				{
					strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
				}
				
				logger.info("strFlag .............. "+strFlag);
				if(!strFlag.equalsIgnoreCase(""))
				{
					if(strFlag.equalsIgnoreCase("sameUserSession"))
					{
						context.removeAttribute("msg");
						context.removeAttribute("msg1");
					}
					else if(strFlag.equalsIgnoreCase("BODCheck"))
					{
						context.setAttribute("msg", "B");
					}
					return mapping.findForward("logout");
				}
				String userSearchId=request.getParameter("userSearchId");
				logger.info("userId............"+userSearchId);
				userMasterVo.setUserSearchId(request.getParameter("userSearchId"));
				logger.info("In openEditUser---userId---- by getpara-"+request.getParameter("userSearchId"));  

				MasterBussinessSessionBeanRemote mb = (MasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(MasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		        
				ArrayList<UserMasterVo> list = mb.searchUserData(userMasterVo);
				ArrayList<UserMasterVo> branchDescList = mb.searchUserBranchEdit(userSearchId);
				ArrayList<UserMasterVo> levelList = mb.searchUserLevelEdit(userSearchId);
				String branchDesc ="";
				String levelDesc ="";
				String branchSelection ="";
				String levelSelection ="";
				Iterator<UserMasterVo> it= branchDescList.iterator();
				
				while(it.hasNext())
				{
					UserMasterVo  vo1 = (UserMasterVo) it.next();
					logger.info("vo1.getBranchId()---"+vo1.getBranchId());
					branchDesc=branchDesc+vo1.getBranchId()+"|";
					logger.info("getSelectionAccess---"+vo1.getSelectionAccess());
					logger.info("branchDesc---"+branchDesc);
					branchSelection=vo1.getSelectionAccess();
				}
				if(!branchDesc.equalsIgnoreCase(""))
				branchDesc = branchDesc.substring(0,branchDesc.length()-1);
				logger.info("branchDesc--2--"+branchDesc);
				Iterator<UserMasterVo> itr= levelList.iterator();
				while(itr.hasNext())
				{
					UserMasterVo  vo1 = (UserMasterVo) itr.next();
					levelDesc=levelDesc+vo1.getLevelID()+"|";
					logger.info("getLevelAccess---"+vo1.getLevelAccess());
					levelSelection=vo1.getLevelAccess();
				}
				
				if(!levelDesc.equalsIgnoreCase(""))
					levelDesc = levelDesc.substring(0,levelDesc.length()-1);
				logger.info("brach Desc String ............................... "+branchDesc);
				logger.info("level Desc String ............................... "+levelDesc);
				request.setAttribute("branchDescIds", branchDesc);
				request.setAttribute("levelDescIds", levelDesc);
				userMasterVo=list.get(0);
				logger.info("In openEditUser userMasterVo list"+list.size());
				request.setAttribute("list", list);
				request.setAttribute("branchNameList", branchDescList);
				request.setAttribute("levelNameList", levelList);
				logger.info("Status:--------"+userMasterVo.getUserStatus());
				logger.info("Status:--Selection Access------"+branchSelection);
				logger.info("Status:--levelSelection Access------"+levelSelection);
				request.setAttribute("SelectionAccecc", branchSelection);
				request.setAttribute("levelSelectionAccecc", levelSelection);
				request.setAttribute("status", userMasterVo.getUserStatus());
				request.setAttribute("editVal", "editVal");
			    return mapping.findForward("openAdd");	
			}
	
	public ActionForward saveUserDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		//HttpSession session=request.getSession(false);
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String sessionId = session.getAttribute("sessionID").toString();
		boolean status = false;
		boolean status1 =false;
		boolean status2 =false;
		String result = "";
		String passnew="";
		ServletContext context = getServlet().getServletContext();
		boolean flag=false;
		
		//for check User session start
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		
        String branch=CommonFunction.checkNull(request.getParameter("branch"));
		
		logger.info("branch........"+branch);
		String[] branchName = branch.split("/");
		 
		for(int i=0;i<branchName.length;i++){
	    logger.info(branchName[i]);
		 }

		logger.info("branch........"+branch);
		 String level=CommonFunction.checkNull(request.getParameter("level"));
			
			String[] levelName = level.split("/");
			 
			for(int i=0;i<levelName.length;i++){
		    logger.info(levelName[i]);
			 }
		DynaValidatorForm UserMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		UserMasterVo userMasterVo = new UserMasterVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(userMasterVo, UserMasterAddDynaValidatorForm);	
		
		MasterBussinessSessionBeanRemote mb = (MasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(MasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		userMasterVo.setMakerId(userId);
		userMasterVo.setMakerDate(bDate);
		String sms="";
		
		status = mb.checkEmpIDInUserMaster(userMasterVo);
		
		if(status){
			result = mb.insertUserMaster(userMasterVo,branchName,levelName);	
			/*if(status){
			status1 = mb.insertUserBranch(userMasterVo,branchName);
			if(userMasterVo.getSelection().length!=0){
				status2 = mb.insertUserLevel(userMasterVo,levelName);	
			}
			passnew=result;
			}else{
				sms="Exist";
				request.setAttribute("sms",sms);
			}
			*/
			passnew=result;
			
		}else{
			sms="DUPLIEMPID";
			request.setAttribute("sms",sms);
			request.setAttribute("save", "save");
		}
		
		
		userMasterVo.setUserId(request.getParameter("userId"));
		logger.info("Inside User Master Action.....displaying status...."+status);

		if(!CommonFunction.checkNull(result).equalsIgnoreCase("")){
			sms="S";
			request.setAttribute("sms",sms);
			request.setAttribute("randompassword",passnew );
			request.setAttribute("save", "save");
			LoginDao logindao=(LoginDao)DaoImplInstanceFactory.getDaoImplInstance(LoginDao.IDENTITY);
			logger.info("Implementation class: "+logindao.getClass());
			String username=request.getParameter("userId");
	        logger.info(" UserId is **************** :"+username); 
	        String userMailId=logindao.getuserEmailId(username);
			String smtpHost=logindao.getSmtpHost();
			String smtpPort=logindao.getSmtpPort();
			String smtpMail=logindao.getSmtpMailAddress();
			String smtpPassword=logindao.getSmtpMailPassword();
			Mail.sendMail(smtpMail,smtpPassword,smtpHost,smtpPort,"true",userMailId," OmniFin-login Password ",passnew);
		}else if(sms.equalsIgnoreCase("DUPLIEMPID")){
			request.setAttribute("sms",sms);
			
			request.setAttribute("save", "save");
		}
		else if(sms.equalsIgnoreCase("Exist")){
			request.setAttribute("sms",sms);			
			request.setAttribute("save", "save");
		}else{

			request.setAttribute("sms",sms);
			request.setAttribute("save", "save");
		}

	    logger.info("status"+status);
	    logger.info("status1"+status1);
//	    ArrayList<UserMasterVo> list = masterDAO.searchUserData(userMasterVo);
//	    request.setAttribute("list", list);
		return mapping.getInputForward();
	}
	
	public ActionForward updateSearchUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
		UserMasterVo userMasterVo = new UserMasterVo();
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		
		DynaValidatorForm UserMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(userMasterVo, UserMasterAddDynaValidatorForm);	
		
		MasterBussinessSessionBeanRemote mb = (MasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(MasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String userId=(String)userMasterVo.getUserId();
//		boolean status = masterDAO.insertAgencyMaster(vo);
		
		logger.info("userId"+userId);
		
		ArrayList<UserMasterVo> detailList = mb.searchUserData(userId);
		
		request.setAttribute("detailList", detailList);
		
		return mapping.findForward("add");
	}
	
	public ActionForward updateUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		logger.info("In updateUser.......");
		ServletContext context = getServlet().getServletContext();
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		String sessionId = session.getAttribute("sessionID").toString();
		boolean status=false;
		boolean status1=false;
		boolean status2=false;
		String sms="";
		String result="success";
		boolean flag=false;
		
		//for check User session start
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		UserMasterVo userMasterVo=new UserMasterVo(); 
		
		String branch=CommonFunction.checkNull(request.getParameter("branch"));
		logger.info(branch);
		
		String[] branchName = branch.split("/");
		 for(int i=0;i<branchName.length;i++){
			logger.info(branchName[i]);
		 }
		 String level=CommonFunction.checkNull(request.getParameter("level"));
			
			String[] levelName = level.split("/");
			 
			for(int i=0;i<levelName.length;i++){
		    logger.info("levelName"+levelName[i]);
			 }
		DynaValidatorForm UserMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		  
		org.apache.commons.beanutils.BeanUtils.copyProperties(userMasterVo, UserMasterAddDynaValidatorForm);	
		
		MasterBussinessSessionBeanRemote mb = (MasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(MasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		

		userMasterVo.setUserId(request.getParameter("userStatus"));
		userMasterVo.setMakerId(userId);
		userMasterVo.setMakerDate(bDate);
		
		userMasterVo.setUserId(request.getParameter("userId"));
  
        
        int countuser=0;
        status = mb.checkEmpIDInEditUserMaster(userMasterVo);
   	    countuser=mb.counthieirarchyusers(userMasterVo);
   	    if (userMasterVo.getUserStatus() != null && userMasterVo.getUserStatus().equals("on")) {
   	        
                 if(status){
            		 boolean userReportingStatus=mb.checkReportingToUserStatus(userMasterVo);
            		 if(userReportingStatus){
                 	   status=mb.updateUserData(userMasterVo,branchName,levelName);
                      if(status){
                    	  
                    	  logger.info("status: "+status);
//                 		status1=mb.updateUserData1(userMasterVo,branchName);
//                 		if(userMasterVo.getSelection().length!=0){
//                 			status2 = mb.updateUserLevel(userMasterVo,levelName);
           		//	}
                 		
                 	}else{
                 		sms="Exist";
                 	}
                 	
            		 }else{
            			 sms="UserInactive"; 
            			 status=false;
            		 }

                     logger.info("In updateUser status"+ status);
         		}else{
     			sms="EMPID";
     			request.setAttribute("sms",sms);
     			ArrayList<UserMasterVo> list =new ArrayList<UserMasterVo>();
     			list.add(userMasterVo);
     			logger.info("In updateUser list"+ list.size());
     			
     			request.setAttribute("editVal", "editVal");
     			request.setAttribute("list", list);
     			request.setAttribute("status", userMasterVo.getUserStatus());
     			
     		}
             logger.info("sms in :-"+sms);
        	 if(status){
     			sms="M";
     			request.setAttribute("sms",sms);
     			request.setAttribute("editValUpdate", "editValUpdate");
     		}
     		else if(sms.equalsIgnoreCase("EMPID")){
    			request.setAttribute("sms",sms);
    			
     			ArrayList<UserMasterVo> list =new ArrayList<UserMasterVo>();
     			
     			list.add(userMasterVo);
     			request.setAttribute("editVal", "editVal");
     			request.setAttribute("list", list);
     			request.setAttribute("status", userMasterVo.getUserStatus());
     			
    		}else if(sms.equalsIgnoreCase("Exist")){
    			request.setAttribute("sms",sms);
    			
     			ArrayList<UserMasterVo> list =new ArrayList<UserMasterVo>();
     			
     			list.add(userMasterVo);
     			request.setAttribute("editVal", "editVal");
     			request.setAttribute("list", list);
     			request.setAttribute("status", userMasterVo.getUserStatus());	
    		}
    		else if(sms.equalsIgnoreCase("UserInactive")){
    			logger.info("sms in fi:-"+sms);
    			request.setAttribute("sms",sms);
    			
     			ArrayList<UserMasterVo> list =new ArrayList<UserMasterVo>();
//     			logger.info("userMasterVo.getUserStatus()::::::::::::"+userMasterVo.getUserStatus());
//      			logger.info("userMasterVo.getUserStatus()::::::::::::"+userMasterVo.getUserStatus());
     			list.add(userMasterVo);
     			request.setAttribute("editVal", "editVal");
     			request.setAttribute("list", list);
     			request.setAttribute("status", userMasterVo.getUserStatus());	
    		}
        	 
     		else{
    			request.setAttribute("sms",sms);
    			
     			ArrayList<UserMasterVo> list =new ArrayList<UserMasterVo>();
     			list.add(userMasterVo);
     			request.setAttribute("editVal", "editVal");
     			request.setAttribute("list", list);
     			request.setAttribute("status", userMasterVo.getUserStatus());
     			
    		}
        	 result="success1";
   		}
		//start by abhishek sharma
		else if (userMasterVo.getUserStatus() != null && userMasterVo.getUserStatus().equals("") ) {
	        
         if(status){
    		 boolean userReportingStatus=mb.checkReportingToUserStatus(userMasterVo);
    		 if(userReportingStatus){
         	   status=mb.updateUserData(userMasterVo,branchName,levelName);
              if(status){
            	  
            	  logger.info("status: "+status);
//         		status1=mb.updateUserData1(userMasterVo,branchName);
//         		if(userMasterVo.getSelection().length!=0){
//         			status2 = mb.updateUserLevel(userMasterVo,levelName);
   		//	}
         		
         	}else{
         		sms="Exist";
         	}
         	
    		 }else{
    			 sms="UserInactive"; 
    			 status=false;
    		 }

             logger.info("In updateUser status"+ status);
 		}else{
			sms="EMPID";
			request.setAttribute("sms",sms);
			ArrayList<UserMasterVo> list =new ArrayList<UserMasterVo>();
			list.add(userMasterVo);
			logger.info("In updateUser list"+ list.size());
			
			request.setAttribute("editVal", "editVal");
			request.setAttribute("list", list);
			request.setAttribute("status", userMasterVo.getUserStatus());
			
		}
     logger.info("sms in :-"+sms);
	 if(status){
			sms="M";
			request.setAttribute("sms",sms);
			request.setAttribute("editValUpdate", "editValUpdate");
		}
		else if(sms.equalsIgnoreCase("EMPID")){
		request.setAttribute("sms",sms);
		
			ArrayList<UserMasterVo> list =new ArrayList<UserMasterVo>();
			
			list.add(userMasterVo);
			request.setAttribute("editVal", "editVal");
			request.setAttribute("list", list);
			request.setAttribute("status", userMasterVo.getUserStatus());
			
	}else if(sms.equalsIgnoreCase("Exist")){
		request.setAttribute("sms",sms);
		
			ArrayList<UserMasterVo> list =new ArrayList<UserMasterVo>();
			
			list.add(userMasterVo);
			request.setAttribute("editVal", "editVal");
			request.setAttribute("list", list);
			request.setAttribute("status", userMasterVo.getUserStatus());	
	}
	else if(sms.equalsIgnoreCase("UserInactive")){
		logger.info("sms in fi:-"+sms);
		request.setAttribute("sms",sms);
		
			ArrayList<UserMasterVo> list =new ArrayList<UserMasterVo>();
//			logger.info("userMasterVo.getUserStatus()::::::::::::"+userMasterVo.getUserStatus());
//			logger.info("userMasterVo.getUserStatus()::::::::::::"+userMasterVo.getUserStatus());
			list.add(userMasterVo);
			request.setAttribute("editVal", "editVal");
			request.setAttribute("list", list);
			request.setAttribute("status", userMasterVo.getUserStatus());	
	}
	 
		else{
		request.setAttribute("sms",sms);
		
			ArrayList<UserMasterVo> list =new ArrayList<UserMasterVo>();
			list.add(userMasterVo);
			request.setAttribute("editVal", "editVal");
			request.setAttribute("list", list);
			request.setAttribute("status", userMasterVo.getUserStatus());
			
	}
	 result="success1";
	}
 
		//end by abhishek sharma 
   		 else if(countuser>0){
   	   	    	  request.setAttribute("heirarchyuser","exist");
   	   	    	  result="success1";
   	   	      
        
   		 }
   		 else{
   			MasterDAO dao=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
   			status=dao.deleteUser(userMasterVo.getUserId());
   			/*if(status){
             	
        		 boolean userReportingStatus=mb.checkReportingToUserStatus(userMasterVo);
        		 if(userReportingStatus){
        			  status=mb.updateUserData(userMasterVo,branchName,levelName);
                   if(status){
                	   
                	   logger.info("In updateUser status"+ status);
             		
             	}else{
             		sms="Exist";
             	}
             	
        		 }else{
        			 sms="UserInactive"; 
        			 status=false;
        		 }

                 logger.info("In updateUser status"+ status);
     		
                 
                 
     		}else{
     			sms="EMPID";
     			request.setAttribute("sms",sms);
     			ArrayList<UserMasterVo> list =new ArrayList<UserMasterVo>();
     			list.add(userMasterVo);
     			logger.info("In updateUser list"+ list.size());
     			
     			request.setAttribute("editVal", "editVal");
     			request.setAttribute("list", list);
     			request.setAttribute("status", userMasterVo.getUserStatus());
     			
     		}*/
        	 if(status){
     			sms="M";
     			request.setAttribute("sms",sms);
     			request.setAttribute("editValUpdate", "editValUpdate");
     			
     			ArrayList<UserMasterVo> list =new ArrayList<UserMasterVo>();
      			list.add(userMasterVo);
      			request.setAttribute("list", list);
      			request.setAttribute("status", userMasterVo.getUserStatus());
      			
     		}
        	 
        	/* else if(sms.equalsIgnoreCase("UserInactive")){
     			logger.info("sms in fi:-"+sms);
     			request.setAttribute("sms",sms);
     			
      			ArrayList<UserMasterVo> list =new ArrayList<UserMasterVo>();
      			
      			list.add(userMasterVo);
      			request.setAttribute("editVal", "editVal");
      			request.setAttribute("list", list);
//      			logger.info("userMasterVo.getUserStatus()::::::::::::"+userMasterVo.getUserStatus());
//      			logger.info("userMasterVo.getUserStatus()::::::::::::"+userMasterVo.getUserStatus());
      			request.setAttribute("status", userMasterVo.getUserStatus());	
     		}
        	 
     		else if(!sms.equalsIgnoreCase("EMPID")){
     			sms="E";
     			request.setAttribute("sms",sms);
     		
     			
     			ArrayList<UserMasterVo> list =new ArrayList<UserMasterVo>();
     			list.add(userMasterVo);
     			logger.info("In openEditUser list"+ list.size());
     			
     			request.setAttribute("editVal", "editVal");
     			request.setAttribute("list", list);
     			request.setAttribute("status", userMasterVo.getUserStatus());
     			
     		}
     		*/
        	 
        	 result="success1";
   			 
   		 }
        
        logger.info("In status---------"+status);    
        
        
        
		
        
      return mapping.findForward(result);
      // return mapping.findForward("success");	
	}

	public ActionForward updateUserPassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In updateUserPassword.......");
		ServletContext context = getServlet().getServletContext();
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		UserMasterVo userMasterVo=new UserMasterVo(); 
		DynaValidatorForm UserMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		  
		org.apache.commons.beanutils.BeanUtils.copyProperties(userMasterVo, UserMasterAddDynaValidatorForm);	
		
		MasterBussinessSessionBeanRemote mb = (MasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(MasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		userMasterVo.setMakerId(userId);
		userMasterVo.setMakerDate(bDate);
		
		userMasterVo.setUserId(request.getParameter("userId"));

        String result=mb.updateUserPassword(userMasterVo);
        String generatedpass=result;
        logger.info("generatedpass......." + generatedpass);
        //*************** Asesh kumar ******************************************
        LoginDao logindao=(LoginDao)DaoImplInstanceFactory.getDaoImplInstance(LoginDao.IDENTITY);
		logger.info("Implementation class: "+logindao.getClass());
        String username=request.getParameter("userId");
        logger.info(" UserId is **************** :"+username);
        String userMailId=logindao.getuserEmailId(username);
		String smtpHost=logindao.getSmtpHost();
		String smtpPort=logindao.getSmtpPort();
		String smtpMail=logindao.getSmtpMailAddress();
		String smtpPassword=logindao.getSmtpMailPassword();
        String sms="";
        if(result != ""){
			sms="R";
			request.setAttribute("sms",sms);
			request.setAttribute("randompassword",generatedpass );
			Mail.sendMail(smtpMail,smtpPassword,smtpHost,smtpPort,"true",userMailId,"OmniFin-login Password Reinitialized",generatedpass); 
		//************** Asesh Kumar end work space ***************************
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
		}
		
        
        return mapping.getInputForward();
       
	}

}
