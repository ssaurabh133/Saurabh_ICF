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


public class UWApprovalLevelDefAuthorDispatchAction extends DispatchAction {
	
	private static final Logger logger=Logger.getLogger(UWApprovalLevelDefAuthorDispatchAction.class.getName());
	
	public  ActionForward EditapprovalLevelDefAuthor(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.info("in EditapprovalLevelDefAuthor of UWApprovalLevelDefAuthorDispatchAction ::::::::::::::::::::: ");

				ServletContext context = getServlet().getServletContext();				
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
					
				Object approvalId="";
				int first=0;
				String openJsp="";
				
				if (CommonFunction.checkNull(productModify).equalsIgnoreCase("")){
					 approvalId=session.getAttribute("productModify");
					 productModify=approvalId.toString();
					 first=1;
				}
			
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
				
		
				String mcFlag="Y";

				ArrayList<ApprovalLevelDefVo> list  = cpm.editApprovalLevelDef(productModify,mcFlag);
               
				session.setAttribute("status", list.get(0).getStatus());
				session.setAttribute("mylist", list);
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
				session.setAttribute("appList",appList );
				session.setAttribute("UserList",UserList );
				//session.setAttribute("LbxUserSearchList",LbxUserSearchList );

				session.setAttribute("productModify", productModify);
				//ApprovalLevelDefVo av=new ApprovalLevelDefVo();
				int list1  = cpm.getApprovalfromPmst();
				
				logger.info("----------------EditapprovalLevelDef--"+list1);
				if(list1>2 && list1<10){
					session.setAttribute("pmstSize", list1);					
				}else if(list1<3 || list1>9){
					session.setAttribute("pmstSize", 3);
				}else {
					session.setAttribute("pmstSize", 3);
				}
				
				if(first==1)
					return mapping.findForward("openWithoutFrame");
			   else					   
				    return mapping.findForward("openFrame");
	}
	
	
	public ActionForward openApprovalLevelAuthor(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		logger.info("in EditapprovalLevelDefAuthor of UWApprovalLevelDefAuthorDispatchAction ::::::::::::::::::::: ");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
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

		
		String productModify=request.getParameter("productModify");
		
		return mapping.findForward("openApprovalAuthor");
	}
	
	public ActionForward saveUWAuthor(ActionMapping mapping,ActionForm form,
						HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		logger.info("in saveUWAuthor of UWApprovalLevelDefAuthorDispatchAction ::::::::::::::::::::: ");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
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
	
		//for check User session start
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag ..............gg "+strFlag);
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
		
		Object approvalId=session.getAttribute("productModify");
		String productModify=approvalId.toString();
		
		logger.info("value of approval id is ::::::"+productModify);

		String   flag1= cpm.saveUWApproval(Vo,productModify);
		
		logger.info("error::::::::::::::::::::::::"+flag1);
		
		if(CommonFunction.checkNull(flag1).equalsIgnoreCase("S"))
			request.setAttribute("message", "S");
		else{
			request.setAttribute("message", "E");
			request.setAttribute("status",flag1);
		}
		
		return mapping.findForward("onAuthorSearchPage");
		
	}
}
