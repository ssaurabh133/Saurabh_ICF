package com.cm.actions;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.cm.actionform.StationaryMasterForm;
import com.cm.business.StationaryIssuanceMasterBusiness;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class HOAllocationDispatchAction extends DispatchAction
{
	Logger logger = Logger.getLogger(HOAllocationDispatchAction.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	StationaryIssuanceMasterBusiness stationaryIssuanceMasterBusiness = new StationaryIssuanceMasterBusiness();
	
	public ActionForward stationaryIssuMethod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
				logger.info(" In HOAllocationDispatchAction:stationaryIssuMethod-------");		
				StationaryMasterForm stationaryMasterForm = (StationaryMasterForm) form;
				ServletContext context = getServlet().getServletContext();
				HttpSession session = request.getSession(false);
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
				UserObject sessUser = (UserObject) session.getAttribute("userobject");
				int compid =0;
				String businessDate="";
				String userId ="";
		if(sessUser!=null){
				businessDate=sessUser.getBusinessdate();
				compid=sessUser.getCompanyId();
				userId = sessUser.getUserId();
	}
				stationaryMasterForm.setCompanyID(compid);
				stationaryMasterForm.setMakerId(userId);
				stationaryMasterForm.setMakerDate(businessDate);
				String actionName="";
				String page="";
				String page1="";
				String resultStr ="newPage";
				actionName=request.getParameter("method");
				page=request.getParameter("page");
				page1=request.getParameter("page1");
				logger.info("page........."+page);
				logger.info("page1........."+page1);
				logger.info("action value-::-" + actionName);
				String BookNoL= request.getParameter("bookNoL");
				logger.info("In -----------"+BookNoL);
			
						
		if(actionName != null  && page != null)
		{			
			if(actionName.equalsIgnoreCase("stationaryIssuMethod") && !page.equalsIgnoreCase("new") && !page1.equalsIgnoreCase("srch")){
				logger.info("result in action : ");
				stationaryMasterForm.setBookNoL("L");
				stationaryMasterForm.setHoAllocationFlag("A");
			    boolean result=stationaryIssuanceMasterBusiness.getsaveIssue(stationaryMasterForm);
				logger.info("result in action : " + result);
		if(result)
				{
				 request.setAttribute("saveData","saveData");
				}
				 resultStr="saveData";
	    }				
	    else if(actionName.equalsIgnoreCase("stationaryIssuMethod") && page.equalsIgnoreCase("new") && !page1.equalsIgnoreCase("srch")){

            	resultStr="newPage";
                }
	    else if(actionName.equalsIgnoreCase("stationaryIssuMethod") && !page.equalsIgnoreCase("new")&& page1.equalsIgnoreCase("srch")){
	    			logger.info("result in action : ");
	    			request.setAttribute("list", stationaryIssuanceMasterBusiness.getHoAllocationSearch(stationaryMasterForm));	
	    		   return mapping.findForward("Success");
            }
		}
		request.setAttribute("list","list");      
		return mapping.findForward(resultStr);
	  }
	
	
	public ActionForward openEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
					logger.info(" In HOAllocationDispatchAction:openEdit-------");		
					StationaryMasterForm stationaryMasterForm = (StationaryMasterForm) form;
					ServletContext context = getServlet().getServletContext();
					HttpSession session = request.getSession(false);
					UserObject userobj=(UserObject)session.getAttribute("userobject");
					Object sessionId = session.getAttribute("sessionID");
					String userId="";
					if(userobj!=null)
					{		
						userId=userobj.getUserId();
					}
					session.setAttribute("userId", userId);// For lov
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
					stationaryMasterForm.setBookIssue(request.getParameter("bookNo"));
					logger.info("In -----------"+request.getParameter("bookNo"));   
					ArrayList bookTypeList = stationaryIssuanceMasterBusiness.getBookTypeGeneric();
					request.setAttribute("bookTypeList", bookTypeList);
			 		request.setAttribute("edit", stationaryIssuanceMasterBusiness.getHoAllocationEdit(stationaryMasterForm));	
			 		logger.info("value of status:::::::::::"+stationaryMasterForm.getStatus1());
			 		if(CommonFunction.checkNull(stationaryMasterForm.getStatus1()).equalsIgnoreCase("A"))
			 		request.setAttribute("check","A");			
			 		
			 		if(CommonFunction.checkNull(stationaryMasterForm.getAllBranch()).equalsIgnoreCase("Y"))
				 		request.setAttribute("checkBranch","Y");	
			 		else
			 			request.setAttribute("checkBranch","N");
			 		
		            return mapping.findForward("editMode");
	}  
	
	public ActionForward saveForwardCancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
					logger.info(" In saveForwardCancel-------");		
					StationaryMasterForm stationaryMasterForm = (StationaryMasterForm) form;
					ServletContext context = getServlet().getServletContext();
					HttpSession session = request.getSession(false);
					UserObject userobj=(UserObject)session.getAttribute("userobject");
					Object sessionId = session.getAttribute("sessionID");
					String userId="";
					String bDate="";
					if(userobj!=null)
					{		
						userId=userobj.getUserId();
						bDate=userobj.getBusinessdate();
					}
					session.setAttribute("userId", userId);// For lov
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
					stationaryMasterForm.setMakerId(userId);
					stationaryMasterForm.setMakerDate(bDate);
					String selfStatus= stationaryIssuanceMasterBusiness.checkReceiptSelf(stationaryMasterForm);
					logger.info("selfStatus: "+selfStatus);
					if(CommonFunction.checkNull(selfStatus).equalsIgnoreCase("Y"))
					{
						String prevCheckStatus= stationaryIssuanceMasterBusiness.prevCheckReceiptNoUsedCanceled(stationaryMasterForm);
						logger.info("prevCheckStatus: "+prevCheckStatus);
						if(CommonFunction.checkNull(prevCheckStatus).equalsIgnoreCase("Y"))
						{
							String checkStatus= stationaryIssuanceMasterBusiness.checkReceiptNoUsedCanceled(stationaryMasterForm);
							boolean status=false;
							if(CommonFunction.checkNull(checkStatus).equalsIgnoreCase("N"))
							{
								logger.info("current checkStatus before: "+checkStatus+" Receipt No: "+stationaryMasterForm.getReceiptNo());
								status= stationaryIssuanceMasterBusiness.saveForwardCancelReceipt(stationaryMasterForm);
								if(status)
						 		{
						 			request.setAttribute("msg", "S");
						 		}
						 		else
						 		{
						 			request.setAttribute("msg", "E");
						 		}
							}
							else
							{
								request.setAttribute("msg", "UC");
							}
						}
						else
						{
							request.setAttribute("msg", "PUC");
						}
										
				    }
					else
					{
						request.setAttribute("msg", "U");
					}
					String userNameQuery="SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID='"+CommonFunction.checkNull(userId)+"'";
					String userName=ConnectionDAO.singleReturn(userNameQuery);
					request.setAttribute("userName", userName);
					userName=null;
					userNameQuery=null;
					form.reset(mapping, request);
		            return mapping.findForward("success");
	}  
	
	public ActionForward saveForwardUse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
					logger.info(" In saveForwardUse-------");		
					StationaryMasterForm stationaryMasterForm = (StationaryMasterForm) form;
					ServletContext context = getServlet().getServletContext();
					HttpSession session = request.getSession(false);
					UserObject userobj=(UserObject)session.getAttribute("userobject");
					Object sessionId = session.getAttribute("sessionID");
					String userId="";
					String bDate="";
					if(userobj!=null)
					{		
						userId=userobj.getUserId();
						bDate=userobj.getBusinessdate();
					}
					session.setAttribute("userId", userId);// For lov
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
					stationaryMasterForm.setMakerId(userId);
					stationaryMasterForm.setMakerDate(bDate);
					
					String selfStatus= stationaryIssuanceMasterBusiness.checkReceiptSelf(stationaryMasterForm);
					logger.info("selfStatus: "+selfStatus);
					if(CommonFunction.checkNull(selfStatus).equalsIgnoreCase("Y"))
					{
						String checkStatus= stationaryIssuanceMasterBusiness.checkReceiptNoUsedCanceled(stationaryMasterForm);
						boolean status=false;
						if(CommonFunction.checkNull(checkStatus).equalsIgnoreCase("N"))
						{
							status= stationaryIssuanceMasterBusiness.saveForwardUsedReceipt(stationaryMasterForm);
							if(status)
					 		{
					 			request.setAttribute("msg", "S");
					 		}
					 		else
					 		{
					 			request.setAttribute("msg", "E");
					 		}
						}
						else
						{
							request.setAttribute("msg", "UC");
						}
					}
					else
					{
						request.setAttribute("msg", "U");
					}
					String userNameQuery="SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID='"+CommonFunction.checkNull(userId)+"'";
					String userName=ConnectionDAO.singleReturn(userNameQuery);
					request.setAttribute("userName", userName);
					userName=null;
					userNameQuery=null;
					form.reset(mapping, request);
		            return mapping.findForward("success");
	}  
	

}          