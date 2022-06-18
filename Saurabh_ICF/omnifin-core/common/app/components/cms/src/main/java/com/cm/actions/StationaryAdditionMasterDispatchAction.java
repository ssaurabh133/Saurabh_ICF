package com.cm.actions;

import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;    
import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.cm.actionform.StationaryMasterForm;
import com.cm.business.StationaryAdditionMasterBusiness;
import com.connect.CommonFunction;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class StationaryAdditionMasterDispatchAction extends DispatchAction
{
	Logger logger = Logger.getLogger(StationaryAdditionMasterDispatchAction.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	StationaryAdditionMasterBusiness stationaryAdditionMasterBusiness = new StationaryAdditionMasterBusiness();
	
	public ActionForward stationarySaveMethod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
				logger.info(" In StationaryAdditionMasterDispatchAction:-------");		
				StationaryMasterForm stationaryMasterForm = (StationaryMasterForm) form;
				HttpSession session = request.getSession(false);
				UserObject sessUser = (UserObject) session.getAttribute("userobject");
				int compid =0;
				String businessDate="";
				String userId ="";
		       if(sessUser!=null){
		    	   businessDate=sessUser.getBusinessdate();
		    	   compid=sessUser.getCompanyId();
		    	   userId = sessUser.getUserId();
	           }else{
					logger.info("here in stationarySaveMethod method of StationaryAdditionMasterDispatchAction the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				stationaryMasterForm.setCompanyID(compid);
				stationaryMasterForm.setMakerId(userId);
				stationaryMasterForm.setMakerDate(businessDate);			
				String actionName="";
				String page="";
				String resultStr ="newPage";
				actionName=request.getParameter("method");
				page=request.getParameter("page");
				logger.info("page........."+page);
				logger.info("action value-::-" + actionName);
				String BookNoL= request.getParameter("bookNoL");
				logger.info("In -----------"+BookNoL);
						
	if(actionName != null  && page != null) {
	   if(actionName.equalsIgnoreCase("stationarySaveMethod") && !page.equalsIgnoreCase("new")){
		   
				  int count=stationaryAdditionMasterBusiness.checkBookNo(stationaryMasterForm);
				  logger.info("count in action : "+ count);
				
				  int countDublicateReceipt=stationaryAdditionMasterBusiness.checkDublicateReceiptNo(stationaryMasterForm);
				  logger.info("countDublicateReceipt in action : "+ countDublicateReceipt);
			  
   	   if(!(CommonFunction.checkNull(count).equals("0")))
			{
				request.setAttribute("exist","exist");
			    resultStr="exist";
			    logger.info("resultStr: " +  resultStr);
	    }
   	   else if(!(CommonFunction.checkNull(countDublicateReceipt).equals("0")))
			{
				request.setAttribute("existDublicateReceipt","existDublicateReceipt");
			    resultStr="exist";
			    logger.info("resultStr: " +  resultStr);
	       }
   	   else
			{
			    boolean result=stationaryAdditionMasterBusiness.getsaveBankData(stationaryMasterForm);
				logger.info("result in action : " + result);
	if(result)
			{
		        request.setAttribute("saveData","saveData");
			}
				 resultStr="saveData";
	        }	
   	   }
	else if(actionName.equalsIgnoreCase("stationarySaveMethod") && page.equalsIgnoreCase("new")){
            	request.setAttribute("bankList", stationaryAdditionMasterBusiness.getBankStatement(stationaryMasterForm));
            	resultStr="newPage";
            }	
		  }
              return mapping.findForward(resultStr);
	  } 

	public ActionForward openEditStationary(ActionMapping mapping, ActionForm form,HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
			ServletContext context = getServlet().getServletContext();
			StationaryMasterForm stationaryMasterForm = (StationaryMasterForm) form;
			HttpSession session = request.getSession();
			StationaryAdditionMasterBusiness stationaryAdditionMasterBusiness = new StationaryAdditionMasterBusiness();
					
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
						stationaryMasterForm.setBookNo1(request.getParameter("bookNo"));
						logger.info("In -----------"+request.getParameter("bookNo")); 
						
						request.setAttribute("dataList", stationaryAdditionMasterBusiness.getEditStatioanry(stationaryMasterForm));
						request.setAttribute("bankList", stationaryAdditionMasterBusiness.getBankStatement(stationaryMasterForm));
						logger.info("value of status:::::::::::"+stationaryMasterForm.getStatus());
						if(CommonFunction.checkNull(stationaryMasterForm.getStatus()).equalsIgnoreCase("A"))
			 			request.setAttribute("check","A");
					    return mapping.findForward("editStationary");	
					}	
	
	public ActionForward openViewStationary(ActionMapping mapping, ActionForm form,HttpServletRequest request, 
			HttpServletResponse response)throws Exception { ServletContext context = getServlet().getServletContext();
			StationaryMasterForm stationaryMasterForm = (StationaryMasterForm) form;
			HttpSession session = request.getSession();
			StationaryAdditionMasterBusiness stationaryAdditionMasterBusiness = new StationaryAdditionMasterBusiness();
				
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
					
						stationaryMasterForm.setBookNo1(request.getParameter("bookNo"));
						logger.info("In -----------"+request.getParameter("bookNo"));  
						request.setAttribute("dataList", stationaryAdditionMasterBusiness.getEditStatioanry(stationaryMasterForm));
						request.setAttribute("viewList", stationaryAdditionMasterBusiness.getEditStatioanry(stationaryMasterForm));
						request.setAttribute("bankList", stationaryAdditionMasterBusiness.getBankStatement(stationaryMasterForm));
						logger.info("In ----------action dispatch -"+request.getParameter("bookNo"));
						logger.info("value of status:::::::::::"+stationaryMasterForm.getStatus());
						if(CommonFunction.checkNull(stationaryMasterForm.getStatus()).equalsIgnoreCase("A"))
			 			request.setAttribute("check","A");	
					    return mapping.findForward("editStationary");	
					}
	public ActionForward stationarySearchMethod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
					logger.info(" In StationaryIssuanceDispatchAction:-------");		
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
					
				
			 		request.setAttribute("list", stationaryAdditionMasterBusiness.getSearch(stationaryMasterForm));	
		            return mapping.findForward("searchPage");
	}
            }
             