

	//Author Name : neeraj kumar Tripathi-->
	//Date of Creation : -->
	//Purpose  : Dispatch Action For User Approval Matrix-->
	//Documentation : -->
package com.masters.actions;

import java.util.ArrayList;
import java.util.Properties;

import javax.naming.InitialContext;
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
import com.masters.vo.UserApprovalMatrixVo;
public class UserApprovalMatrixDispatchAction extends DispatchAction
{
		static final Logger logger = Logger.getLogger(UserApprovalMatrixDispatchAction.class.getName());
		public ActionForward saveUserApprovalRecords(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ServletContext context = getServlet().getServletContext();
			logger.info(" in saveUserApprovalRecords()......................................");		
			HttpSession session =  request.getSession();
			String flag=request.getParameter("flag");
		    logger.info("Maker author add flag : " + flag);
			//boolean flag=false;
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
			UserApprovalMatrixVo vo=new UserApprovalMatrixVo();
			DynaValidatorForm formbeen= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);
			
			CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			
			String userID="";
			String date="";
			if(userobj!=null)
			{
					userID=userobj.getUserId();
					date=userobj.getBusinessdate();
			}
			vo.setMakerId(userID);
			date=date.substring(6)+"-"+date.substring(3,5)+"-"+date.substring(0,2);
			vo.setMakerDate(date);
			vo.setAuthorId(userID);
			vo.setAuthorDate(date);
			String status=request.getParameter("status");
			if(status!=null)
			 vo.setRecStatus("A");
			else
			 vo.setRecStatus("X");
			String amountFrom=vo.getAmountFrom();
			String amountTo=vo.getAmountTo();
			String[] amFrList=amountFrom.split(",");
			String[] amToList=amountTo.split(","); 
			amountFrom="";
			amountTo="";
			for(int i=0;i<amFrList.length;i++)
				amountFrom=amountFrom+amFrList[i];
			for(int i=0;i<amToList.length;i++)
				amountTo=amountTo+amToList[i];
			vo.setAmountFrom(amountFrom);
			vo.setAmountTo(amountTo);
			String userId=vo.getLbxUserId();
			String role=vo.getRole();
			int count=cpm.checkUserId(userId,role);
			if(count!=0)
				request.setAttribute("exist","exist");
			else
			{
				boolean flage=false;
				if(CommonFunction.checkNull(flag).equalsIgnoreCase("Y"))
					flage =cpm.saveUserApprovalMatrix(vo,"Y");
				else
					flage =cpm.saveUserApprovalMatrix(vo,"N");
				if(flage)
					request.setAttribute("save","save");
			}
			return mapping.findForward("success");	
		}
		
		public ActionForward addUserApprovalRecords(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{ServletContext context = getServlet().getServletContext();
			logger.info(" in addUserApprovalRecords()......................................");
			HttpSession session =  request.getSession();
		    String flag=request.getParameter("flag");
		    logger.info("Maker author add flag : " + flag);
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
			
			CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			
			ArrayList subRuleList = cpm.getSubRoleType();
			request.setAttribute("subRuleList", subRuleList);
			int list1  = cpm.getApprovalLevelfromPmst();
			
			logger.info("list Size  ----"+list1);
			if(list1>2 && list1<10){
				request.setAttribute("pmstSize", list1);					
			}else if(list1<3 || list1>9){
				request.setAttribute("pmstSize", 3);
			}else {
				request.setAttribute("pmstSize", 3);
			}
			if(CommonFunction.checkNull(flag).equalsIgnoreCase("Y"))
				request.setAttribute("makerAuthor", "makerAuthor");
			return mapping.findForward("success");	
		}
		public ActionForward updateUserApprovedData(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{ServletContext context = getServlet().getServletContext();
			logger.info(" in updateUserApprovedData()......................................");
			HttpSession session =  request.getSession();
		    //boolean flag=false;
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
			String userId=request.getParameter("userId");
			String userRole=request.getParameter("userRole");
			logger.info(" userId"+userId);
			logger.info(" userRole"+userRole);
			if(userRole!=null)
			{
				if(userRole.charAt(0)=='P')
				{
					userRole="Policy Approval";
					request.setAttribute("policy","policy");
				}
				else
				{
					userRole="Under Writer";
					request.removeAttribute("policy"); 
				}
			}
				
			CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			
	        ArrayList subRuleList = cpm.getSubRoleType();
	        String flag = request.getParameter("flag");
	        String makerAuthorFlag = cpm.makerAuthorFlag();
	        ArrayList userList=cpm.getDetail(userId,userRole,flag);
			UserApprovalMatrixVo vo=(UserApprovalMatrixVo)userList.get(0);
			String status=vo.getRecStatus();
			int list1  = cpm.getApprovalLevelfromPmst();
			
			logger.info("List Size --"+list1);
			if(list1>2 && list1<10){
				request.setAttribute("pmstSize", list1);					
			}else if(list1<3 || list1>9){
				request.setAttribute("pmstSize", 3);
			}else {
				request.setAttribute("pmstSize", 3);
			}
			if(status.equals("X"))
				request.setAttribute("X","X");
			else
				request.setAttribute("A","A");
			request.setAttribute("subRuleList", subRuleList);
			request.setAttribute("userList",userList);
			if(CommonFunction.checkNull(makerAuthorFlag).equalsIgnoreCase("Y"))
				request.setAttribute("makerAuthor", "makerAuthor");
			return mapping.findForward("success");	
		}
		public ActionForward updateUserApprovedRecord(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{ServletContext context = getServlet().getServletContext();
			logger.info(" in updateUserApprovedRecord()......................................");
			HttpSession session =  request.getSession();
		    //boolean flag=false;
		    String flag=request.getParameter("flag");
		    logger.info("Maker author add flag : " + flag);
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
			UserApprovalMatrixVo vo=new UserApprovalMatrixVo();
			DynaValidatorForm formbeen= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);
			
			CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			
			String userId="";
			String date="";
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					date=userobj.getBusinessdate();
			}
			logger.info("getSubRuleTypeP::::::::::::"+vo.getSubRuleTypeP());
			vo.setMakerId(userId);
			date=date.substring(6)+"-"+date.substring(3,5)+"-"+date.substring(0,2);
			vo.setMakerDate(date);
			vo.setAuthorId(userId);
			vo.setAuthorDate(date);
			String status=request.getParameter("status");
			if(status!=null)
			 vo.setRecStatus("A");
			else
			 vo.setRecStatus("X");
			String amountFrom=vo.getAmountFrom();
			String amountTo=vo.getAmountTo();
			String[] amFrList=amountFrom.split(",");
			String[] amToList=amountTo.split(","); 
			amountFrom="";
			amountTo="";
			for(int i=0;i<amFrList.length;i++)
				amountFrom=amountFrom+amFrList[i];
			for(int i=0;i<amToList.length;i++)
				amountTo=amountTo+amToList[i];
			vo.setAmountFrom(amountFrom);
			vo.setAmountTo(amountTo);
			String role=vo.getRole();
			role=role.charAt(0)+"";
			vo.setRole(role);
			boolean flage=false;
			if(CommonFunction.checkNull(flag).equalsIgnoreCase("Y"))
			{
				if(CommonFunction.checkNull(vo.getStatusCase()).equalsIgnoreCase("Approved"))
					flag="N";
				flage =cpm.updateUserApprovedRecords(vo,flag);
				flag="Y";
			}
			else
				flage =cpm.updateUserApprovedRecords(vo,"");
			//Nishat space starts
        	ArrayList userList=cpm.getDetail(vo.getLbxUserId(),vo.getRole(),flag);
        	ArrayList subRuleList = cpm.getSubRoleType();
        	vo=(UserApprovalMatrixVo)userList.get(0);
			String st=vo.getRecStatus();
			int list1  = cpm.getApprovalLevelfromPmst();
			logger.info("List Size --"+list1);
			if(list1>2 && list1<10){
				request.setAttribute("pmstSize", list1);					
			}else if(list1<3 || list1>9){
				request.setAttribute("pmstSize", 3);
			}else {
				request.setAttribute("pmstSize", 3);
			}
			if(st.equals("X"))
				request.setAttribute("X","X");
			else
				request.setAttribute("A","A");
			logger.info("Role : " + vo.getSubRuleTypeP());
			if(!(CommonFunction.checkNull(vo.getRole()).equalsIgnoreCase("")))//if(userRole!=null)
			{
				if((vo.getRole()).charAt(0)=='P')
				{
					request.setAttribute("policy","policy");
					request.setAttribute("subRuleList", subRuleList);
				}
				else
				{
					request.removeAttribute("policy"); 
				}
			}
        	//Nishant space end
			if(flage)
			{
				request.setAttribute("userList",userList);
				request.setAttribute("update","update");
			}
			if(CommonFunction.checkNull(flag).equalsIgnoreCase("Y"))
				request.setAttribute("makerAuthor", "makerAuthor");
			return mapping.findForward("success");	
		}
		public ActionForward searchApprovedRecords(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception 
		{
			ServletContext context = getServlet().getServletContext();
				logger.info("In searchApprovedRecords.........");
				HttpSession session = request.getSession();
				//boolean flag=false;
				String flag=request.getParameter("flag");
			    logger.info("Maker author add flag : " + flag);
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
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
				logger.info("current page link .......... "+request.getParameter("d-49520-p"));				
				int currentPageLink = 0;
				if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
					currentPageLink=1;
				else
					currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
				logger.info("current page link ................ "+request.getParameter("d-49520-p"));
					
				UserApprovalMatrixVo vo=new UserApprovalMatrixVo();
				DynaValidatorForm formbeen= (DynaValidatorForm)form;
				org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);
				
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
				vo.setCurrentPageLink(currentPageLink);
				ArrayList<UserApprovalMatrixVo> list;

				if(CommonFunction.checkNull(flag).equalsIgnoreCase("Y"))
				{
					if(CommonFunction.checkNull(vo.getStatusCase()).equalsIgnoreCase("Approved"))
						flag="N";
					list= cpm.getsearchApprovedUser(vo,flag,"P","Y");
					flag="Y";
				}
				else
					list= cpm.getsearchApprovedUser(vo,"","","");
				
				request.setAttribute("list",list);
				request.setAttribute("search","search");
				request.setAttribute("searchList","searchList");
				request.setAttribute("totalRecordSize",list.size());
				if(CommonFunction.checkNull(flag).equalsIgnoreCase("Y"))
					request.setAttribute("makerAuthor", "makerAuthor");
		        return mapping.findForward("success");
				
		}
		public ActionForward viewBranchAccess(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception 
		{
			ServletContext context = getServlet().getServletContext();
				logger.info("In viewBranchAccess.........");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
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
				String userId=request.getParameter("userId");
				
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
		        ArrayList<UserApprovalMatrixVo> list= cpm.getBranches(userId);
				request.setAttribute("list",list);
								
		        return mapping.findForward("branch");				
		}
		public ActionForward viewProductAccess(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception 
		{
			ServletContext context = getServlet().getServletContext();
				logger.info("In viewProductAccess.........");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
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
				String userId=request.getParameter("userId");
				
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
		        ArrayList<UserApprovalMatrixVo> list= cpm.getProducts(userId);
				request.setAttribute("list",list);
								
		        return mapping.findForward("product");				
		}
		public ActionForward forwardUserApprovedRecord(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{ServletContext context = getServlet().getServletContext();
			logger.info(" in forwardUserApprovedRecord()......................................");
			HttpSession session =  request.getSession();
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
			UserApprovalMatrixVo vo=new UserApprovalMatrixVo();
			DynaValidatorForm formbeen= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);
			
			CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			
			String userId="";
			String date="";
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					date=userobj.getBusinessdate();
			}
			logger.info("getSubRuleTypeP::::::::::::"+vo.getSubRuleTypeP());
			vo.setMakerId(userId);
			date=date.substring(6)+"-"+date.substring(3,5)+"-"+date.substring(0,2);
			vo.setMakerDate(date);
			vo.setAuthorId(userId);
			vo.setAuthorDate(date);
			String status=request.getParameter("status");
			if(status!=null)
			 vo.setRecStatus("A");
			else
			 vo.setRecStatus("X");
			String amountFrom=vo.getAmountFrom();
			String amountTo=vo.getAmountTo();
			String[] amFrList=amountFrom.split(",");
			String[] amToList=amountTo.split(","); 
			amountFrom="";
			amountTo="";
			for(int i=0;i<amFrList.length;i++)
				amountFrom=amountFrom+amFrList[i];
			for(int i=0;i<amToList.length;i++)
				amountTo=amountTo+amToList[i];
			vo.setAmountFrom(amountFrom);
			vo.setAmountTo(amountTo);
			String role=vo.getRole();
			role=role.charAt(0)+"";
			vo.setRole(role);
			boolean flage=false;
			flage =cpm.forwardUserApprovedRecords(vo);
			if(flage)
				request.setAttribute("forward","forward");
			request.setAttribute("makerAuthor", "makerAuthor");
			return mapping.findForward("success");	
		}
		public ActionForward getUserApproalAuthor(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{ServletContext context = getServlet().getServletContext();
			logger.info(" in getUserApproalAuthor()......................................");
			HttpSession session =  request.getSession();
		    //boolean flag=false;
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
			String userId=request.getParameter("userId");
			String userRole=request.getParameter("userRole");
			logger.info(" userId"+userId);
			logger.info(" userRole"+userRole);
			session.setAttribute("userId", userId);
			session.setAttribute("userRole", userRole);
			if(userRole!=null)
			{
				if(userRole.charAt(0)=='P')
				{
					userRole="Policy Approval";
					session.setAttribute("policy","policy");
				}
				else
				{
					userRole="Under Writer";
					session.removeAttribute("policy"); 
				}
			}
				
			
			CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			
			ArrayList subRuleList = cpm.getSubRoleType();
	       // String flag = request.getParameter("flag");
	       // String makerAuthorFlag = cpm.makerAuthorFlag();
	        ArrayList userList=cpm.getAuthorDetail(userId,userRole);
			UserApprovalMatrixVo vo=(UserApprovalMatrixVo)userList.get(0);
			String status=vo.getRecStatus();
			logger.info("role : " + vo.getRole());
			logger.info("level : " + vo.getLevel());
			logger.info("sub rule type P : " + vo.getSubRuleTypeP());
			logger.info("sub rule type U : " + vo.getSubRuleType());
			int list1  = cpm.getApprovalLevelfromPmst();
			
			logger.info("List Size --"+list1);
			if(list1>2 && list1<10){
				//request.setAttribute("pmstSize", list1);	
				session.setAttribute("pmstSize", 3);
			}else if(list1<3 || list1>9){
				session.setAttribute("pmstSize", 3);
			}else {
				session.setAttribute("pmstSize", 3);
			}
			if(status.equals("X"))
				session.setAttribute("X","X");
			else
				session.setAttribute("A","A");
			session.setAttribute("subRuleList", subRuleList);
			session.setAttribute("userList",userList);
			//if(CommonFunction.checkNull(makerAuthorFlag).equalsIgnoreCase("Y"))
			//	request.setAttribute("makerAuthor", "makerAuthor");
			return mapping.findForward("makerAuthor");	
		}
		
		public ActionForward userApproalAuthor(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{ServletContext context = getServlet().getServletContext();
			logger.info(" in userApproalAuthor()......................................");
			HttpSession session =  request.getSession();
		    //boolean flag=false;
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
			String userId=session.getAttribute("userId").toString();
			String userRole=session.getAttribute("userRole").toString();
			logger.info(" userId"+userId);
			logger.info(" userRole"+userRole);
			if(userRole!=null)
			{
				if(userRole.charAt(0)=='P')
				{
					userRole="Policy Approval";
					session.setAttribute("policy","policy");
				}
				else
				{
					userRole="Under Writer";
					session.removeAttribute("policy"); 
				}
			}
			
			return mapping.findForward("uaAuthor");	
		}
		public ActionForward saveApprovalAuthor(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)	throws Exception
				{
					logger.info(" in saveApprovalAuthor.....");
					ServletContext context = getServlet().getServletContext();
					logger.info(" in saveUserApprovalRecords()......................................");		
					HttpSession session =  request.getSession();
					
					//boolean flag=false;
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
					UserApprovalMatrixVo vo=new UserApprovalMatrixVo();
					DynaValidatorForm formbeen= (DynaValidatorForm)form;
					org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);
					
					CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
					
					String userID="";
					String date="";
					if(userobj!=null)
					{
							userID=userobj.getUserId();
							date=userobj.getBusinessdate();
					}
					//vo.setMakerId(userID);
					//date=date.substring(6)+"-"+date.substring(3,5)+"-"+date.substring(0,2);
					//vo.setMakerDate(date);
					vo.setAuthorId(userID);
					vo.setAuthorDate(date);
					String flage=null;
					String uId=session.getAttribute("userId").toString();
					String uRole=session.getAttribute("userRole").toString();
					if(uRole!=null)
					{
						if(uRole.charAt(0)=='P')
							uRole="P";
						else
							uRole="U";
					}
					String decesion=CommonFunction.checkNull(vo.getDecison());
					logger.info(" decesion : "+decesion);
					flage =cpm.saveUserApprovalAuthor(vo, uId, uRole);
					if(flage.equalsIgnoreCase("S"))
					{	
						if(decesion.equalsIgnoreCase("A")){
							request.setAttribute("message","APP");
						}
						else if(decesion.equalsIgnoreCase("P")){
							request.setAttribute("message","SB");
						}
						
						request.setAttribute("status",flage);
					}
					else
					{
						request.setAttribute("message","E");
						request.setAttribute("status",flage);
					}
				    logger.info("status"+flage);
					return mapping.findForward("authorStatus");	
				}
		
		
}
