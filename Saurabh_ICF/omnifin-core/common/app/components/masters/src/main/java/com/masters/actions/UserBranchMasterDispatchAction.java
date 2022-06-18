package com.masters.actions;

import java.util.ArrayList;
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
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.dao.MasterDAO;
import com.masters.vo.UserBranchMasterVo;

public class UserBranchMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(UserBranchMasterDispatchAction.class.getName());
	public ActionForward openAddUserBranch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
				HttpSession session = request.getSession();
				logger.info(" in openAddUserBranch()");
				ServletContext context = getServlet().getServletContext();
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
				
				MasterDAO masterDAO=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
				logger.info("Implementation class: "+masterDAO.getClass());
				//MasterDAO masterDAO = new MasterDAOImpl();
				ArrayList<UserBranchMasterVo> branchlList = masterDAO.searchUserBranch();
				request.setAttribute("branchNameList", branchlList);
				request.setAttribute("save", "save");
				if(session.getAttribute("strParentOption")!=null)
				{
					session.removeAttribute("strParentOption");
				}
				if(session.getAttribute("pParentGroup")!=null)
				{
					session.removeAttribute("pParentGroup");
				}
			    return mapping.findForward("openAdd");	
			}
	public ActionForward openEditUserBranch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
		        UserBranchMasterVo userBranchMasterVo=new UserBranchMasterVo(); 
				logger.info("In openEditUserBranch");
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
				userBranchMasterVo.setLbxUserSearchId(request.getParameter("lbxUserSearchId"));
				userBranchMasterVo.setLbxBranchId(request.getParameter("lbxBranchId"));
				logger.info("branch Id........."+userBranchMasterVo.getLbxBranchId());
				String userId=request.getParameter("lbxUserSearchId");
				  
				logger.info("userId.......................................... "+userId);
				MasterDAO masterDAO=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
				logger.info("Implementation class: "+masterDAO.getClass());
				//MasterDAO masterDAO = new MasterDAOImpl();
				ArrayList<UserBranchMasterVo> branchDescListEdit = masterDAO.searchUserBranchDescEdit(userId);
//				ArrayList<UserBranchMasterVo> branchDescList = masterDAO.searchUserBranchDesc(userId);
				ArrayList<UserBranchMasterVo> list = masterDAO.searchUserBranchData(userBranchMasterVo);
				userBranchMasterVo=list.get(0);
				
				
				logger.info("In openEditUserBranch userBranchMasterVo list"+list.size());
				request.setAttribute("list", list);
				request.setAttribute("branchNameList", branchDescListEdit);
//				request.setAttribute("branchNameDescription", branchDescList);
				logger.info("Status:--------"+userBranchMasterVo.getUserBranchStatus());
				
				request.setAttribute("userBranchStatus", userBranchMasterVo.getUserBranchStatus());
			
				request.setAttribute("editVal", "editVal");
			    return mapping.findForward("openAdd");	
			}
	
	public ActionForward saveUserBranchDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("in save method");
		HttpSession session = request.getSession();
		boolean flag=false;
		ServletContext context = getServlet().getServletContext();
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
		String branch=CommonFunction.checkNull(request.getParameter("branch"));
		
		logger.info("branch........"+branch);
		 String[] branchName = branch.split("/");
		 
		 
		 
		 for(int i=0;i<branchName.length;i++){
			logger.info(branchName[i]);
		 }
		 String IdValues="";
		 if(request.getParameter("IdValues")!=null)
		 {
			 IdValues = request.getParameter("branch");
		 }
		  
		logger.info("Id Values ..............************................ "+IdValues);
		DynaValidatorForm UserBranchMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		UserBranchMasterVo userBranchMasterVo = new UserBranchMasterVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(userBranchMasterVo, UserBranchMasterAddDynaValidatorForm);	
		userBranchMasterVo.setMakerId(userId);
		userBranchMasterVo.setMakerDate(bDate);
		MasterDAO masterDAO=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+masterDAO.getClass());
		//MasterDAO masterDAO = new MasterDAOImpl();
		String sms="";
		
		boolean status = masterDAO.insertUserBranchMaster(userBranchMasterVo,branchName);
		logger.info("Inside UserBranchDetails Action.....displaying status...."+status);
		if(status){
			sms="S";
			request.setAttribute("sms",sms);
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
		}
	    logger.info("status"+status);
	    ArrayList<UserBranchMasterVo> list = masterDAO.searchUserBranchData(userBranchMasterVo);
	    logger.info("list: "+list.size());
		request.setAttribute("list", list);
		return mapping.getInputForward();
	}
		public ActionForward updateSearchUserBranch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		
		UserBranchMasterVo userBranchMasterVo = new UserBranchMasterVo();
		DynaValidatorForm UserBranchMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(userBranchMasterVo, UserBranchMasterAddDynaValidatorForm);	
		String userId=(String)userBranchMasterVo.getUserId();
		MasterDAO masterDAO=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+masterDAO.getClass());
		//MasterDAO masterDAO = new MasterDAOImpl();
//		boolean status = masterDAO.insertAgencyMaster(vo);
		
		logger.info("userId"+userId);
		
		ArrayList<UserBranchMasterVo> detailList = masterDAO.searchUserBranchData(userId);
		
		request.setAttribute("detailList", detailList);
		
		return mapping.findForward("add");
	}
	public ActionForward updateUserBranch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In updateUserBranch.......");
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
		
		UserBranchMasterVo userBranchMasterVo=new UserBranchMasterVo(); 
		String branch=CommonFunction.checkNull(request.getParameter("branch"));
		logger.info(branch);
		 String[] branchName = branch.split("/");
		 for(int i=0;i<branchName.length;i++){
			logger.info(branchName[i]);
		 }
		DynaValidatorForm UserBranchMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		  
		org.apache.commons.beanutils.BeanUtils.copyProperties(userBranchMasterVo, UserBranchMasterAddDynaValidatorForm);	
		
		logger.info("In updateUserBranch---status-----"+userBranchMasterVo.getUserBranchStatus());
		userBranchMasterVo.setUserBranchStatus(request.getParameter("userBranchStatus"));
		
		userBranchMasterVo.setLbxUserId(request.getParameter("userId"));
		logger.info("In updateUserBranch---status---- by getpara-"+request.getParameter("userId"));  
		
		MasterDAO masterDAO=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+masterDAO.getClass());
		//MasterDAO masterDAO = new MasterDAOImpl();
        boolean status=masterDAO.updateUserBranchData(userBranchMasterVo,branchName);
        String sms="";
        if(status){
			sms="M";
			request.setAttribute("sms",sms);
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
		}
		
        return mapping.getInputForward();
       
	}


}
