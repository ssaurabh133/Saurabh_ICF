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

import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.ApprovalLevelDefVo;


public class ApprovalLevelDefBehindAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ApprovalLevelDefBehindAction.class.getName());
	public ActionForward OpenApprovalLevelMainJsp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In ApprovalLevelDefBehindAction.........");
		HttpSession session = request.getSession();
		boolean flag=false;
		ServletContext context = getServlet().getServletContext();
	//	request.removeAttribute("list");
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
		ApprovalLevelDefVo Vo = new ApprovalLevelDefVo();
		DynaValidatorForm CommonDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(Vo, CommonDynaValidatorForm);
		

        
        CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
        
      
		ArrayList list=new ArrayList();
		 logger.info("current page link .......... "+request.getParameter("d-49520-p"));
			
			int currentPageLink = 0;
			if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
			{
				currentPageLink=1;
			}
			else
			{
				currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
			}
// Code Start by sanjog
				int list1  = cpm.getApprovalfromPmst();
				
				logger.info("----------OpenApprovalLevelSearchJsp--"+list1);
				if(list1>2 && list1<10){
					request.setAttribute("pmstSize", list1);					
				}else if(list1<3 || list1>9){
					request.setAttribute("pmstSize", 3);
				}else {
					request.setAttribute("pmstSize", 3);
				}
//Code ended By sanjog
	
// Saurabh changes starts
			String mcFlag=cpm.getMakerCheckerFlag();
			
			logger.info("maker chaneker flag ki value hai in action :::::::"+mcFlag);
			if(CommonFunction.checkNull(mcFlag).equalsIgnoreCase("Y"))
			request.setAttribute("makerCheckerFlag","makerCheckerFlag");
// saurabh changes ends
			logger.info("current page link ................ "+request.getParameter("d-49520-p"));
			Vo.setCurrentPageLink(currentPageLink);
			list= cpm.searchApprovalLevelDef(Vo,mcFlag);

		    logger.info("In ApprovalLevelDefBehindAction....list"+list.size());
			
			if (list.size() > 0) {
				 request.setAttribute("list", list);
					request.setAttribute("list",list);
					request.setAttribute("Vo",Vo);
					
			}else{
				request.setAttribute("sms","No");
				request.setAttribute("Vo",Vo);
			}
        return mapping.findForward("ApprovalLevelSearch");
	}
	
	
	
	
	public ActionForward OpenApprovalLevelDef(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info(" in openApprovalLevelDef:::::::::::::");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
				
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
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
				ApprovalLevelDefVo av=new ApprovalLevelDefVo();
				int list1  = cpm.getApprovalfromPmst();
				logger.info("-----------------------------------------1111111111--"+list1);
				if(list1>2 && list1<10){
					request.setAttribute("pmstSize", list1);					
				}else if(list1<3 || list1>9){
					request.setAttribute("pmstSize", 3);
				}else {
					request.setAttribute("pmstSize", 3);
				}
				//saurabh changes starts
				if(request.getParameter("makerFlag").equalsIgnoreCase("Y"))
				request.setAttribute("makerCheckerFlag","makerCheckerFlag");
				//saurabh changes ends
			    return mapping.findForward("OpenApprovalLevel");	
			}
	
	
	public ActionForward EditapprovalLevelDef(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info(" in EditapprovalLevelDef:::::::::::::");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
				String userId="";
				String bDate="";
				if(userobj!=null)
				{
						userId=userobj.getUserId();
						bDate=userobj.getBusinessdate();
				}
				
				DynaValidatorForm ApprovalLevelDefDynaValidatorForm= (DynaValidatorForm)form;
				ApprovalLevelDefVo Vo=new ApprovalLevelDefVo();
			
				org.apache.commons.beanutils.BeanUtils.copyProperties(Vo, ApprovalLevelDefDynaValidatorForm);	
				
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				Vo.setMakerId(userId);
				Vo.setMakerDate(bDate);
				String productModify=request.getParameter("productModify");
				
			
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
				
				logger.info("ffffffffffffffffffffffffffffffff"+request.getParameter("makerFlag"));
				String mcFlag="";
				if(!(CommonFunction.checkNull(request.getParameter("makerFlag")).equalsIgnoreCase("")))
				 mcFlag=request.getParameter("makerFlag");
				ArrayList<ApprovalLevelDefVo> list  = cpm.editApprovalLevelDef(productModify,mcFlag);
               
				request.setAttribute("status", list.get(0).getStatus());
				request.setAttribute("editVal", "editVal");
				request.setAttribute("list", list);
				ArrayList<ApprovalLevelDefVo> levelList = new ArrayList();
				ArrayList appList=new ArrayList();
				appList.add(list.get(0).getLevel1());
				appList.add(list.get(0).getLevel2());
				appList.add(list.get(0).getLevel3());
				appList.add(list.get(0).getLevel4());
				appList.add(list.get(0).getLevel5());
				appList.add(list.get(0).getLevel6());
				appList.add(list.get(0).getLevel7());
				appList.add(list.get(0).getLevel8());
				appList.add(list.get(0).getLevel9());
				
				ArrayList LbxUserSearchList=new ArrayList();
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId11());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId12());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId13());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId21());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId22());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId23());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId31());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId32());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId33());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId41());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId42());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId43());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId51());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId52());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId53());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId61());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId62());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId63());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId71());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId72());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId73());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId81());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId82());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId83());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId91());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId92());
				LbxUserSearchList.add(list.get(0).getLbxUserSearchId93());
				
				ArrayList UserList=new ArrayList();
				UserList.add(list.get(0).getUser11());
				UserList.add(list.get(0).getUser12());
				UserList.add(list.get(0).getUser13());
				UserList.add(list.get(0).getUser21());
				UserList.add(list.get(0).getUser22());
				UserList.add(list.get(0).getUser23());
				UserList.add(list.get(0).getUser31());
				UserList.add(list.get(0).getUser32());
				UserList.add(list.get(0).getUser33());
				UserList.add(list.get(0).getUser41());
				UserList.add(list.get(0).getUser42());
				UserList.add(list.get(0).getUser43());
				UserList.add(list.get(0).getUser51());
				UserList.add(list.get(0).getUser52());
				UserList.add(list.get(0).getUser53());
				UserList.add(list.get(0).getUser61());
				UserList.add(list.get(0).getUser62());
				UserList.add(list.get(0).getUser63());
				UserList.add(list.get(0).getUser71());
				UserList.add(list.get(0).getUser72());
				UserList.add(list.get(0).getUser73());
				UserList.add(list.get(0).getUser81());
				UserList.add(list.get(0).getUser82());
				UserList.add(list.get(0).getUser83());
				UserList.add(list.get(0).getUser91());
				UserList.add(list.get(0).getUser92());
				UserList.add(list.get(0).getUser93());
				request.setAttribute("appList",appList );
				request.setAttribute("UserList",UserList );
				request.setAttribute("LbxUserSearchList",LbxUserSearchList );

				request.setAttribute("productModify", productModify);
				ApprovalLevelDefVo av=new ApprovalLevelDefVo();
				int list1  = cpm.getApprovalfromPmst();
				
				logger.info("----------------EditapprovalLevelDef--"+list1);
				if(list1>2 && list1<10){
					request.setAttribute("pmstSize", list1);					
				}else if(list1<3 || list1>9){
					request.setAttribute("pmstSize", 3);
				}else {
					request.setAttribute("pmstSize", 3);
				}
				
				String mcFlag1=cpm.getMakerCheckerFlag();
			if(CommonFunction.checkNull(mcFlag1).equalsIgnoreCase("Y"))
				request.setAttribute("makerCheckerFlag","makerCheckerFlag");
				
			    return mapping.findForward("AddApprovalLevel");	
			}
	
}
