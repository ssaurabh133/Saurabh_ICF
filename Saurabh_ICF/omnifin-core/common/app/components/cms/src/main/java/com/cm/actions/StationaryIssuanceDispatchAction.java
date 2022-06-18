package com.cm.actions;

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
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class StationaryIssuanceDispatchAction extends DispatchAction
{
	Logger logger = Logger.getLogger(StationaryIssuanceDispatchAction.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	StationaryIssuanceMasterBusiness stationaryIssuanceMasterBusiness = new StationaryIssuanceMasterBusiness();
	
	public ActionForward stationaryIssuMethod(ActionMapping mapping, ActionForm form,
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
				UserObject sessUser = (UserObject) session.getAttribute("userobject");
				int compid =0;
				String businessDate="";
				String userId ="";
				String branchId ="";
		if(sessUser!=null){
				businessDate=sessUser.getBusinessdate();
				compid=sessUser.getCompanyId();
				userId = sessUser.getUserId();
				branchId = sessUser.getBranchId();
	}
				stationaryMasterForm.setCompanyID(compid);
				stationaryMasterForm.setMakerId(userId);
				stationaryMasterForm.setMakerDate(businessDate);
				stationaryMasterForm.setBranchid(branchId);
				String actionName="";
				String page="";
				String page1="";
				String resultStr ="newPage";
				actionName=request.getParameter("method");
				page=request.getParameter("page");
				page1=request.getParameter("page1");
//				logger.info("page........."+page);
//				logger.info("page1........."+page1);
//				logger.info("action value-::-" + actionName);
				String BookNoL= request.getParameter("bookNoL");
				//logger.info("In -----------"+BookNoL);			
				
		if(actionName != null  && page != null)
		{			
			if(actionName.equalsIgnoreCase("stationaryIssuMethod") && !page.equalsIgnoreCase("new") && !page1.equalsIgnoreCase("srch")){
				logger.info("result in action : ");
				stationaryMasterForm.setBookNoL("L");
				stationaryMasterForm.setHoAllocationFlag("");
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
	    			request.setAttribute("list", stationaryIssuanceMasterBusiness.getSrchData(stationaryMasterForm));	
	    		   return mapping.findForward("Success");
            }
		}
		request.setAttribute("list","list");   
		//stationaryIssuanceMasterBusiness=null;
//		actionName=null;
//		page=null;
//		page1=null;		
		return mapping.findForward(resultStr);
	  }
	
	
	public ActionForward openEdit(ActionMapping mapping, ActionForm form,
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
					stationaryMasterForm.setBookIssue(request.getParameter("bookNo"));
					logger.info("In -----------"+request.getParameter("bookNo"));   				
					
			 		request.setAttribute("edit", stationaryIssuanceMasterBusiness.getEdit(stationaryMasterForm));	
			 		logger.info("value of status:::::::::::"+stationaryMasterForm.getStatus1());
			 		if(CommonFunction.checkNull(stationaryMasterForm.getStatus1()).equalsIgnoreCase("A"))
			 		    request.setAttribute("check","A");
			 		else
			 			request.setAttribute("check","X");
			 		if(CommonFunction.checkNull(stationaryMasterForm.getAllBranch()).equalsIgnoreCase("Y"))
				 		request.setAttribute("checkBranch","Y");	
			 		else
			 			request.setAttribute("checkBranch","N");
			 		
			 		if(CommonFunction.checkNull(stationaryMasterForm.getLbxUserId()).equalsIgnoreCase(""))
				 		request.setAttribute("branchAllocationNo","Y");	
			 		
			 		//stationaryIssuanceMasterBusiness=null;
		            return mapping.findForward("editMode");
	}  
	public ActionForward stationaryAcknoSrch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
				logger.info(" In stationaryAcknoSrch:-------");		
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
			
				String branchId =null;
		if(sessUser!=null){
				
				compid=sessUser.getCompanyId();
				
				branchId = sessUser.getBranchId();
				}				
				stationaryMasterForm.setBranchid(branchId);
				request.setAttribute("list", stationaryIssuanceMasterBusiness.getSrchAcknow(stationaryMasterForm));				
		//		stationaryIssuanceMasterBusiness=null;
				return mapping.findForward("searchAcknow");
	}
	public ActionForward openEditAcknowledgement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
					logger.info(" In openEditAcknowledgement:-------");		
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
					stationaryMasterForm.setBookIssue(request.getParameter("bookNo"));
					logger.info("In -----------"+request.getParameter("bookNo"));   
						
			 		request.setAttribute("edit", stationaryIssuanceMasterBusiness.getEdit(stationaryMasterForm));	
					//stationaryIssuanceMasterBusiness=null;
		            return mapping.findForward("modify");
	}  
	public ActionForward saveAcknow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
					logger.info(" In saveAcknow:-------");		
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
				boolean result=stationaryIssuanceMasterBusiness.getSaveAcknow(stationaryMasterForm);				
				if(result)
				{
				 request.setAttribute("saveData","saveData");
				 request.setAttribute("bookIssue",stationaryMasterForm.getBookIssue());
				 request.setAttribute("remarks",stationaryMasterForm.getRemarks());
				 request.setAttribute("additionDate",stationaryMasterForm.getAdditionDate());
				}				
				
	            return mapping.findForward("saveAcknow");
	}  
	public ActionForward openEditHo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
					logger.info(" In openEditHo:-------");		
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
					stationaryMasterForm.setBookIssue(request.getParameter("bookNo"));
					logger.info("In openEditHo-----------"+request.getParameter("bookNo"));   
					
			 		request.setAttribute("edit", stationaryIssuanceMasterBusiness.getEdit(stationaryMasterForm));			 		
			//		stationaryIssuanceMasterBusiness=null;
		            return mapping.findForward("modifyHo");
	}  
	public ActionForward saveAcknowHo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
					logger.info(" In saveAcknowHo:-------");		
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
					String userId =null;					
			if(sessUser!=null){					
					userId = sessUser.getUserId();					
					}
					stationaryMasterForm.setReturnBy(userId);
				boolean result=stationaryIssuanceMasterBusiness.getSaveAcknowHo(stationaryMasterForm);				
				if(result)
				{
				 request.setAttribute("saveData","saveData");
				 request.setAttribute("bookIssue",stationaryMasterForm.getBookIssue());
				 request.setAttribute("remarks",stationaryMasterForm.getRemarks());
				 request.setAttribute("additionDate",stationaryMasterForm.getAdditionDate());
				}
			//	stationaryIssuanceMasterBusiness=null;
	            return mapping.findForward("saveAcknowHo");
	}  
	public ActionForward stationaryAcknoSrchHo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
				logger.info(" In stationaryAcknoSrchHo:-------");		
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
				
				String branchId =null;
		if(sessUser!=null){
				
				compid=sessUser.getCompanyId();
				
				branchId = sessUser.getBranchId();
				}				
				stationaryMasterForm.setBranchid(branchId);	
				request.setAttribute("list", stationaryIssuanceMasterBusiness.getSrchAcknowHo(stationaryMasterForm));					
			//	stationaryIssuanceMasterBusiness=null;
				return mapping.findForward("searchAcknowHo");
	}
	public ActionForward HoStationaryAcknow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
					logger.info(" In HoStationaryAcknow:-------");		
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
					stationaryMasterForm.setBookIssue(request.getParameter("bookNo"));
					logger.info("In HoStationaryAcknow-----------"+request.getParameter("bookNo"));  
			 		request.setAttribute("edit", stationaryIssuanceMasterBusiness.getEdit(stationaryMasterForm));		
			 		request.setAttribute("remarks",stationaryMasterForm.getRemarks());
			 		request.setAttribute("additionDate",stationaryMasterForm.getAdditionDate());
				//	stationaryIssuanceMasterBusiness=null;
		            return mapping.findForward("modifyGetHo");
	}  
	public ActionForward stationaryHoSearchAcknowledge(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
				logger.info(" In stationaryHoSearchAcknowledge:-------");		
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
		
				String branchId =null;
				String userId=null;
		if(sessUser!=null)
			{
				userId=sessUser.getUserId();
				compid=sessUser.getCompanyId();
				branchId = sessUser.getBranchId();
			}
				stationaryMasterForm.setLbxUserId(userId);
				stationaryMasterForm.setBranchid(branchId);	
				request.setAttribute("list", stationaryIssuanceMasterBusiness.SrchAcknowStationaryHo(stationaryMasterForm));
				//stationaryIssuanceMasterBusiness=null;
			
				return mapping.findForward("searchstationaryAcknowHo");
	}
	public ActionForward stationaryHoAcknowledge(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
					logger.info(" In stationaryHoAcknowledge:-------");		
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
					stationaryMasterForm.setBookIssue(request.getParameter("bookNo"));
					logger.info("In stationaryHoAcknowledge-----------"+request.getParameter("bookNo"));  
					request.setAttribute("edit", stationaryIssuanceMasterBusiness.getEdit(stationaryMasterForm));	
					//stationaryIssuanceMasterBusiness=null;
		            return mapping.findForward("stationaryHo");
	}  
	public ActionForward saveStationaryAcknowHo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
					logger.info(" In saveStationaryAcknowHo:-------");		
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
				boolean result=stationaryIssuanceMasterBusiness.SaveStationaryAcknowHo(stationaryMasterForm);
				if(result)
				{
				 request.setAttribute("saveData","saveData");
				 request.setAttribute("bookIssue",stationaryMasterForm.getBookIssue());
				 request.setAttribute("remarks",stationaryMasterForm.getRemarks());
				 request.setAttribute("additionDate",stationaryMasterForm.getAdditionDate());
				}	
				//stationaryIssuanceMasterBusiness=null;
	            return mapping.findForward("saveStationryAcknowHo");
	}  
	public ActionForward openEditBranchChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
					logger.info(" In openEditBranchChange:-------");		
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
					stationaryMasterForm.setBookIssue(request.getParameter("bookNo"));
					logger.info("In -----------"+request.getParameter("bookNo"));   
					
			 		request.setAttribute("editBranchChange", stationaryIssuanceMasterBusiness.getEdit(stationaryMasterForm));	
		 		
			 		logger.info("In openEditBranchChange mapping forward");  
		            return mapping.findForward("editModeBranchChange");
	}  
}          