//Author Name : Ritu Jindal-->
//Date of Creation : -->
//Purpose  : Dispatch Action For Agency Master-->
//Documentation : -->

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

import com.business.ejbClient.CommonMasterBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.MasterVo;



public class AgencyMasterDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(AgencyMasterDispatchAction.class.getName());
	public ActionForward openAddAgency(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info(" in openAddAgency()");
				MasterVo vo=new MasterVo(); 
					
				CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
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
				
				ArrayList list = bp.getAgency();
				request.setAttribute("agencyType", list);
				request.setAttribute("save", "save");
				request.setAttribute("Add", "Add");
				request.setAttribute("status","Active");
			    return mapping.findForward("openAdd");	
			}
	public ActionForward openEditAgency(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		        MasterVo vo=new MasterVo(); 
		        ServletContext context = getServlet().getServletContext();
				logger.info("In openEditAgency");
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
				
				vo.setAgencySearchCode(request.getParameter("agencySearchCode"));
				logger.info("In openEditAgency---status---- by getpara-"+request.getParameter("agencySearchCode"));  
				logger.info("In openEditAgency---status---- by getpara by vo-"+vo.getAgencyCode());
				
				CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
		        ArrayList<MasterVo> list = bp.searchAgencyData(vo);
				
				ArrayList<MasterVo> branchDescList = bp.searchAgencyDataMapping(vo);
				
				//ArrayList<UserBranchMasterVo> list1 = masterDAO.searchUserBranchData(userMasterVo);
				
				String lbxUserId ="";
				Iterator<MasterVo> it= branchDescList.iterator();
				
				while(it.hasNext())
				{
					MasterVo  vo1 = (MasterVo) it.next();
					lbxUserId=lbxUserId+vo1.getLbxUserIds()+"|";
				}
				
				
				if(!lbxUserId.equalsIgnoreCase(""))
				{
					lbxUserId = lbxUserId.substring(0,lbxUserId.length()-1);
				}
				
				logger.info("brach Desc String ............................... "+lbxUserId);
				request.setAttribute("userNameList", branchDescList);
				request.setAttribute("lbxUserIds", lbxUserId);

				logger.info("In openEditAgency vo list"+list.size());
				request.setAttribute("list", list);
				
				MasterVo mVo=new MasterVo();
				mVo=list.get(0);
				logger.info("In openEditAgency---status---- by   getpara by vo-"+mVo.getAgencyStatus());
				ArrayList list1 = bp.getAgency();
				request.setAttribute("agencyType", list1);
				request.setAttribute("status", mVo.getAgencyStatus());
				request.setAttribute("editVal", "editVal");
			   return mapping.findForward("openAdd");	
			}
	
	public ActionForward saveAgencyDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
		DynaValidatorForm AgencyMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		logger.info("Inside Agency Master Action......"+AgencyMasterAddDynaValidatorForm.getString("agencyStatus") );
		HttpSession session=request.getSession(false);
		UserObject userobj=new UserObject();
		userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String bDate="";
		
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		
		boolean flag=false;
		
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
		
	
		MasterVo vo = new MasterVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, AgencyMasterAddDynaValidatorForm);	
		
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
        String sms="";
		
		 String userMapp=CommonFunction.checkNull(request.getParameter("userlist"));
		 logger.info("branch........"+userMapp);
		 
			String[] userMappingList = userMapp.split("/");
			 
			for(int i=0;i<userMappingList.length;i++){
		    logger.info("user mapping list.:====------"+userMappingList[i]);
			 }

		
		String status = bp.insertAgencyMaster(vo,userMappingList);
		
		logger.info("Inside Agency Master Action.....displaying status...."+status);
		
		if(status.equalsIgnoreCase("S")){
			sms="S";
			request.setAttribute("sms",sms);
			AgencyMasterAddDynaValidatorForm.reset(mapping, request);
		}
		if(status.equalsIgnoreCase("E")){
			sms="E";
			ArrayList list =new ArrayList();
			list.add(vo);
			request.setAttribute("list", list);
			ArrayList list1 = bp.getAgency();
			request.setAttribute("agencyType", list1);
			request.setAttribute("sms",sms);
		}
		if(status.equalsIgnoreCase("EX")){
			sms="EX";
			ArrayList list =new ArrayList();
			list.add(vo);
			request.setAttribute("list", list);
			ArrayList list1 = bp.getAgency();
			request.setAttribute("agencyType", list1);
			request.setAttribute("sms",sms);
		}
	    logger.info("status"+status);

		return mapping.getInputForward();
	}
		public ActionForward updateAgencyBranch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
			ServletContext context = getServlet().getServletContext();
		MasterVo vo = new MasterVo();
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
		
		DynaValidatorForm AgencyMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, AgencyMasterAddDynaValidatorForm);	
		String agencyCode=(String)vo.getAgencyCode();
		
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
//        boolean status = masterDAO.insertAgencyMaster(vo);
		
		logger.info("agencyCode"+agencyCode);
		
		ArrayList<MasterVo> detailList = bp.searchAgencyData(agencyCode);
		
		request.setAttribute("detailList", detailList);
		
		return mapping.findForward("add");
	}
	public ActionForward updateAgency(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In updateAgency.......");
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
		
		MasterVo vo=new MasterVo(); 
		DynaValidatorForm AgencyMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		  
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, AgencyMasterAddDynaValidatorForm);	
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		logger.info("In updateAgencyDetails---status-----"+vo.getAgencyStatus());
		vo.setAgencyStatus(request.getParameter("agencyStatus"));
		logger.info("In updateAgencyDetails---status---- by getpara-"+request.getParameter("agencyStatus"));
		
		vo.setAgencyCode(request.getParameter("agencyCode"));
		
		 String userMapp=CommonFunction.checkNull(request.getParameter("userlist"));
		 logger.info("branch........"+userMapp);
		 
			String[] userMappingList = userMapp.split("/");
			 
			for(int i=0;i<userMappingList.length;i++){
		    logger.info("user mapping list.:====------"+userMappingList[i]);
			 }

		logger.info("In updateAgencyDetails---status---- by getpara-"+request.getParameter("agencyCode"));  
		logger.info("In updateAgencyDetails---status---- by getpara by vo-"+vo.getAgencyCode());
		logger.info("In updateAgencyDetails---------");    
		
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
        boolean status=bp.updateAgencyData(vo,userMappingList);
        
        String sms="";
        if(status){
			sms="M";
			request.setAttribute("sms",sms);
			 request.setAttribute("userNameList", "userNameList");
			AgencyMasterAddDynaValidatorForm.reset(mapping, request);
		}
		else{
			sms="E";
			ArrayList list1 = bp.getAgency();
			 request.setAttribute("userNameList", "userNameList");
			request.setAttribute("agencyType", list1);
			request.setAttribute("sms",sms);
			//request.setAttribute("editVal", "editVal");
		}
       
        return mapping.getInputForward();
       
		
	}

}