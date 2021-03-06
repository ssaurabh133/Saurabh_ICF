/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.gcd.actions;

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
import com.VO.CustomerSaveVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.gcd.dao.*;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/** 
 * MyEclipse Struts
 * Creation date: 02-08-2011
 * 
 * XDoclet definition:
 * @struts.action input="/JSP/gcdJSP/corporateCustomerAddress.jsp" parameter="method" scope="request" validate="true"
 */
public class CustBankDetailAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(CustBankDetailAction.class.getName());
	/*
	 * Generated Methods
	 */

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward	 */
	

	public ActionForward displayCustBankDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {	
		
		
		 HttpSession session = request.getSession();	
		 //session.removeAttribute("list");
		 logger.info("displayCustBankDetails");		   
		 UserObject userobj=(UserObject)session.getAttribute("userobject");
					if(userobj==null)
					{			
						logger.info("here displayCustBankDetails method of displayCustBankDetails action the session is out----------------");
						return mapping.findForward("sessionOut");
					}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
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
					//code added by neeraj
					String source="NE";
					String functionId=(String)session.getAttribute("functionId");
					int funid=Integer.parseInt(functionId);		
					if(funid==4000122 || funid==4000123)
						source="ED";
					//neeraj space end
					
	 CorporateDAO detail=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
	 logger.info("Implementation class: "+detail.getClass());
	 String statusCase="";
	 String updateFlag="";
	 String updateInMaker="";
	 String idividualId="";
	 idividualId=CommonFunction.checkNull(session.getAttribute("idividualId")).toString();	 
	 if(session!=null && !idividualId.equalsIgnoreCase(""))
	 {
		 	String code = session.getAttribute("idividualId").toString();		

		 	if(session.getAttribute("operation")!=null||session.getAttribute("approve")!=null||session.getAttribute("updateFlag")!=null)
		 	{		
			 	Object pageStatus = session.getAttribute("approve");
			    updateFlag = (String)session.getAttribute("updateFlag");
			    String gcdReq = (String)session.getAttribute("gcdReq");
			    if(gcdReq!=null)
				  {
					  pageStatus=null;
					  updateFlag=null;
				  }
		  //ArrayList<CustomerSaveVo> list = detail.getCustBankDetails(code,statusCase,updateInMaker,"",updateFlag,"");	
			// logger.info("in CustBankDetailAction ..List"+list.size());
			// request.setAttribute("list", list);
		 	}
			 if(session.getAttribute("statusCase")!=null)
			 {
					statusCase = session.getAttribute("statusCase").toString();
					logger.info("statusCase"+statusCase);
			 }
			 if(session.getAttribute("updateFlag")!=null)
			 {
				 
				 updateFlag = session.getAttribute("updateFlag").toString();
				 logger.info("updateFlag"+updateFlag);
			 }
			if(session.getAttribute("updateInMaker")!=null)
			{
					updateInMaker = session.getAttribute("updateInMaker").toString();
					logger.info("updateInMaker"+updateInMaker);
			}
			 String pageStatuss="";
			 if(session.getAttribute("pageStatuss")!=null)
			 {
				 pageStatuss = session.getAttribute("pageStatuss").toString();
				 logger.info("pageStatuss"+pageStatuss);
			 }
	  String gcdReq = (String)session.getAttribute("gcdReq");
	  logger.info("gcdReq"+gcdReq);	  
		  if(gcdReq!=null&&gcdReq!="")
		  {
			  pageStatuss=null;
			  updateFlag=null;
		  }
		  String cuaStatus="";
		  logger.info("CUA"+session.getAttribute("CUA"));
		  if(session.getAttribute("CUA")!=null)
		  {
			  cuaStatus = session.getAttribute("CUA").toString();
		  }
	 ArrayList<CustomerSaveVo> list = detail.getCustBankDetails(code,statusCase,updateInMaker,pageStatuss,updateFlag,cuaStatus,source);	
	 logger.info("in CustBankDetailAction ..List"+list.size());
	 session.setAttribute("list", list);
	 ///session.setAttribute("briefAddr", briefAddr);
	 return mapping.findForward("indCustBankDetails");
	 }
	 else
	 {
		 request.setAttribute("back", "ok");
		  return mapping.findForward("backIndCustBankDetails"); 
		 //return mapping.findForward("indCustBankDetails");
	 }
		
		
	}
	public ActionForward displayCorpCustBankDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {			
		
				 HttpSession session = request.getSession();		
				 logger.info("displayCorpCustBankDetails");		   
				 UserObject userobj=(UserObject)session.getAttribute("userobject");
					if(userobj==null)
					{			
						logger.info("here displayCorpCustBankDetails method of displayCustBankDetails action the session is out----------------");
						return mapping.findForward("sessionOut");
					}
				Object sessionId = session.getAttribute("sessionID");
				ServletContext context = getServlet().getServletContext();
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
					//code added by neeraj
					String source="NE";
					String functionId=(String)session.getAttribute("functionId");
					int funid=Integer.parseInt(functionId);		
					if(funid==4000122 || funid==4000123)
						source="ED";
					//neeraj space end
		CorporateDAO detail=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
		 logger.info("Implementation class: "+detail.getClass());
		 String statusCase="";
		 String updateFlag="";
		 String updateInMaker=""; 
		 String corporateId="";
		 corporateId=CommonFunction.checkNull(session.getAttribute("corporateId")).toString();
		 if(session!=null && !corporateId.equalsIgnoreCase(""))
		 {
			 String code = corporateId;		
	
		 if(session.getAttribute("operation")!=null||session.getAttribute("approve")!=null||session.getAttribute("updateFlag")!=null)
		 {
		
		 Object pageStatus = session.getAttribute("approve");
		 updateFlag = (String)session.getAttribute("updateFlag");
		 String gcdReq = (String)session.getAttribute("gcdReq");
		  if(gcdReq!=null)
		  {
			  pageStatus=null;
			  updateFlag=null;
		  }
		  ArrayList<CustomerSaveVo> list = detail.getCustBankDetails(code,statusCase,updateInMaker,"",updateFlag,"",source);	
			 logger.info("in CustBankDetailAction ..List"+list.size());
			 request.setAttribute("list", list);

	 }

	 if(session.getAttribute("statusCase")!=null)
	 {
			statusCase = session.getAttribute("statusCase").toString();
			logger.info("statusCase"+statusCase);
	 }
	 if(session.getAttribute("updateFlag")!=null)
	 {
		 
		 updateFlag = session.getAttribute("updateFlag").toString();
		 logger.info("updateFlag"+updateFlag);
	 }
	 if(session.getAttribute("updateInMaker")!=null)
		{
			updateInMaker = session.getAttribute("updateInMaker").toString();
			logger.info("updateInMaker"+updateInMaker);
		}
	 String pageStatuss="";
	 if(session.getAttribute("pageStatuss")!=null)
	 {
		 pageStatuss = session.getAttribute("pageStatuss").toString();
	 }
	 String gcdReq = (String)session.getAttribute("gcdReq");
	 logger.info("gcdReq"+gcdReq);
	  if(gcdReq!=null)
	  {
		  pageStatuss=null;
		  updateFlag=null;
	  }
	  String cuaStatus="";
	  logger.info("CUA"+session.getAttribute("CUA"));
	  if(session.getAttribute("CUA")!=null)
		 {
		  cuaStatus = session.getAttribute("CUA").toString();
		 }
	 ArrayList<CustomerSaveVo> list = detail.getCustBankDetails(code,statusCase,updateInMaker,pageStatuss,updateFlag,cuaStatus,source);	
	 logger.info("in CustBankDetailAction ..List"+list.size());
	 session.setAttribute("list", list);
	 ///session.setAttribute("briefAddr", briefAddr);
	 return mapping.findForward("indCustBankDetails");
	 }
	 else
	 {
		 request.setAttribute("back", "ok");
		  return mapping.findForward("backCorpCustBankDetails"); 
		// return mapping.findForward("indCustBankDetails");
	 }	
		
	}
	
	
	public ActionForward saveCustBankDetails(ActionMapping mapping, ActionForm form,
			   HttpServletRequest request, HttpServletResponse response)throws Exception
		   {
			   logger.info("In saveCustBankDetails() --> ");
			
			    HttpSession session = request.getSession();
			    session.removeAttribute("list");
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId="";
				String bDate="";
				if(userobj!=null)
				{
						userId=userobj.getUserId();
						bDate=userobj.getBusinessdate();
				}else{
					logger.info("here saveCustBankDetails method of  the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				
				DynaValidatorForm CustBankDetailsDynaValidatorForm = (DynaValidatorForm)form; 
				Object sessionId = session.getAttribute("sessionID");
				ServletContext context = getServlet().getServletContext();
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
				
				 String dealId = "";
					
		      		if(session.getAttribute("dealId")!=null)
		      		{
		      			
		      			dealId=session.getAttribute("dealId").toString();
		      		}
		      		else if(session.getAttribute("maxId")!=null)
		      		{
		      			dealId=session.getAttribute("maxId").toString();
		      		}
		    		//added by Virender
		    		logger.info("Virender");
		    		ArrayList alDeleteQuery = new ArrayList(1);
		    		String hunterQry="delete from cr_hunter_marking_dtl where deal_id='"+dealId+"'";
		    		logger.info("hunterQry "+ hunterQry);
		    		alDeleteQuery.add(hunterQry);
		    		boolean status1 = ConnectionDAO.sqlInsUpdDelete(alDeleteQuery);
		    		hunterQry = null;
		    		//Virender changes end
				
				//code added by neeraj
				String source="NE";
				String functionId=(String)session.getAttribute("functionId");
				int funid=Integer.parseInt(functionId);		
				if(funid==4000122 || funid==4000123)
					source="ED";
				//neeraj space end
				CustomerSaveVo custSaveVo = new CustomerSaveVo();
				org.apache.commons.beanutils.BeanUtils.copyProperties(custSaveVo, CustBankDetailsDynaValidatorForm);
				String id="";
				String pageStatuss="";
				String updateFlag="";
				if(session.getAttribute("pageStatuss")!=null)
				{
					pageStatuss = session.getAttribute("pageStatuss").toString();
					logger.info("pageStatuss .............. "+pageStatuss);
				}
				else
				{
					pageStatuss = request.getParameter("pageStatuss");
					
					logger.info("pageStatuss .............. "+pageStatuss);
				}
				
				if(session.getAttribute("updateFlag")!=null)
				{
					updateFlag=session.getAttribute("updateFlag").toString();
				}
				else
				{
					updateFlag = request.getParameter("updateFlag");
					
					logger.info("updateFlag .............. "+updateFlag);
				}
				
			    custSaveVo.setPageStat(pageStatuss);
			    custSaveVo.setUpdateFlag(updateFlag);
			    custSaveVo.setMakerId(userId);
			    custSaveVo.setMakerDate(bDate);
			    
			    String cusType=CommonFunction.checkNull(session.getAttribute("cusType")).toString();
			    if(cusType.equalsIgnoreCase("I")){
			    	if(session.getAttribute("idividualId")!=null)
					{
						id = CommonFunction.checkNull(session.getAttribute("idividualId")).toString();
					}
			    }
			    if(cusType.equalsIgnoreCase("C")){
			    	if(session.getAttribute("corporateId")!=null)
					{
						id = CommonFunction.checkNull(session.getAttribute("corporateId")).toString();
					}
			    }
			  
				
			 
				String statusCase="";
				String updateInMaker="";
				CorporateDAO service=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
				 logger.info("Implementation class: "+service.getClass());
				boolean status = false;
				if(session.getAttribute("statusCase")!=null)
 			{
 				statusCase = session.getAttribute("statusCase").toString();
 			}
				custSaveVo.setStatusCase(statusCase);
				String insertInTempFlag="";				
	    		 
				if(!statusCase.equals("") )
				{
					 if(cusType.equalsIgnoreCase("I")){
					    	if(session.getAttribute("idividualId")!=null)
							{
								id = CommonFunction.checkNull(session.getAttribute("idividualId")).toString();
							}
					    }
					    if(cusType.equalsIgnoreCase("C")){
					    	if(session.getAttribute("corporateId")!=null)
							{
								id = CommonFunction.checkNull(session.getAttribute("corporateId")).toString();
							}
					    } 
					if(session.getAttribute("cusType")!=null)
					{
						cusType=session.getAttribute("cusType").toString();
						logger.info("Customer type is ....getApproval................"+cusType);
					}
					String query = "Select customer_id from gcd_customer_m_temp where customer_id="+id;
					String custCheckInTemp = ConnectionDAO.singleReturn(query);
					logger.info("custCheckInTemp *?********************************** "+custCheckInTemp);
	    			
					if(custCheckInTemp==null)
					{
						insertInTempFlag = service.insertAllIntoTempFromMainTable(id+"",cusType);
						session.setAttribute("updateInMaker", "updateInMaker");
//						request.setAttribute("procval",insertInTempFlag );
					}
					status = service.saveCustBankDetails(custSaveVo,Integer.parseInt(id),source);
	    		}
	    		else
	    		{
	    			logger.info("only insert ****************************.....statusCase.........."+statusCase);
	    			status = service.saveCustBankDetails(custSaveVo,Integer.parseInt(id),source);
	    		}
	    		request.setAttribute("sms", "S");
		   
	    		logger.info("status *********************************************"+status);
				if(status)
				{				
					if(session.getAttribute("updateInMaker")!=null)
					{
						updateInMaker = session.getAttribute("updateInMaker").toString();
					}
						
					session.removeAttribute("briefStake");
					String cuaStatus="";
					if(session.getAttribute("CUA")!=null)
					{
						cuaStatus = session.getAttribute("CUA").toString();
					}
					ArrayList<CustomerSaveVo> list = service.getCustBankDetails(id, statusCase, updateInMaker, pageStatuss, updateFlag, cuaStatus,source);
					session.setAttribute("list", list);
				 }
				 else
				 {
					 request.setAttribute("sms","E");
				 }
			return mapping.findForward("saveSuceess");
		}
		
		public ActionForward updateCustBankDetails(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {
			
			    HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId="";
				String bDate="";
				logger.info("Inside updateCustBankDetails ");
				if(userobj!=null)
				{
						userId=userobj.getUserId();
						bDate=userobj.getBusinessdate();
				}else{
					logger.info("here updateCustBankDetails method of  the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				
				Object sessionId = session.getAttribute("sessionID");
				ServletContext context = getServlet().getServletContext();
				String strFlag="";	
				if(sessionId!=null)
				{
					strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
				}
				
				logger.info(" updateCustBankDetails strFlag .............. "+strFlag);
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
				
				 String dealId = "";
					
		      		if(session.getAttribute("dealId")!=null)
		      		{
		      			
		      			dealId=session.getAttribute("dealId").toString();
		      		}
		      		else if(session.getAttribute("maxId")!=null)
		      		{
		      			dealId=session.getAttribute("maxId").toString();
		      		}
		    		//added by Virender
		    		logger.info("Virender");
		    		ArrayList alDeleteQuery = new ArrayList(1);
		    		String hunterQry="delete from cr_hunter_marking_dtl where deal_id='"+dealId+"'";
		    		logger.info("hunterQry "+ hunterQry);
		    		alDeleteQuery.add(hunterQry);
		    		boolean status1 = ConnectionDAO.sqlInsUpdDelete(alDeleteQuery);
		    		hunterQry = null;
		    		//Virender changes end
				
				//code added by neeraj
				String source="NE";
				String functionId=(String)session.getAttribute("functionId");
				int funid=Integer.parseInt(functionId);		
				if(funid==4000122 || funid==4000123)
					source="ED";
				//neeraj space end
			DynaValidatorForm CustBankDetailsDynaValidatorForm = (DynaValidatorForm)form; 
			CustomerSaveVo sh = new CustomerSaveVo();
			String recStatus="";
			String updateFlag="";
			String statusCase="";
			String pageStatuss="";

			sh.setMakerId(userId);
			sh.setMakerDate(bDate);
			if(session.getAttribute("pageStatuss")!=null)
			{
				pageStatuss = session.getAttribute("pageStatuss").toString();
			}
			 
			sh.setPageStat(pageStatuss);
			
			
			if(session.getAttribute("recStatus")!=null)
		    {
		    	recStatus=session.getAttribute("recStatus").toString();
		    }
			int id=0;
			//int id = Integer.parseInt(session.getAttribute("idividualId").toString());
			String cusType=CommonFunction.checkNull(session.getAttribute("cusType").toString());
		    if(cusType.equalsIgnoreCase("I")){
		    	if(session.getAttribute("idividualId")!=null)
				{
					id = Integer.parseInt(session.getAttribute("idividualId").toString());
				}
		    }
		    if(cusType.equalsIgnoreCase("C")){
		    	if(session.getAttribute("corporateId")!=null)
				{
					id = Integer.parseInt(session.getAttribute("corporateId").toString());
				}
		    }
				
			org.apache.commons.beanutils.BeanUtils.copyProperties(sh, CustBankDetailsDynaValidatorForm);
			
			CorporateDAO service=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
			 logger.info("Implementation class: "+service.getClass());
			if(session.getAttribute("statusCase")!=null)
		    {
		    	statusCase=session.getAttribute("statusCase").toString();
		    }
			if(session.getAttribute("updateFlag")!=null)
			{
				updateFlag = session.getAttribute("updateFlag").toString();
			}
			
			
			
			if(session.getAttribute("cusType")!=null)
			{
				cusType=session.getAttribute("cusType").toString();
			}
			
			
			String updateInMaker="";
			if(session.getAttribute("updateInMaker")!=null)
			{
				updateInMaker = session.getAttribute("updateInMaker").toString();
			}
			
		    	String query = "Select customer_id from gcd_customer_m_temp where customer_id="+id;
				String custCheckInTemp = ConnectionDAO.singleReturn(query);
				String insertInTempFlag="";
			
				if(custCheckInTemp==null && session.getAttribute("statusCase")!=null && recStatus.equalsIgnoreCase("A"))
				{
					insertInTempFlag = service.insertAllIntoTempFromMainTable(id+"",cusType);
					logger.info(" insertInTempFlag  "+insertInTempFlag);
					int status = service.updateCustBankDetails(sh,id,recStatus,statusCase,updateFlag,pageStatuss,source);
					 request.setAttribute("procval",insertInTempFlag );
					 if(status>0)
						{
							request.setAttribute("sms", "U");
						}	else{
							request.setAttribute("sms","E");
						}
					session.setAttribute("updateInMaker", "updateInMaker");
				}
				else
				{
					int status = service.updateCustBankDetails(sh,id,recStatus,statusCase,updateFlag,pageStatuss,source);
					if(status>0)
					{
						request.setAttribute("sms", "U");
					}	else{
						request.setAttribute("sms","E");
					}
				}
				 String cuaStatus="";
				  if(session.getAttribute("CUA")!=null)
					 {
					  cuaStatus = session.getAttribute("CUA").toString();
					 }
				
				if(session.getAttribute("pageStatuss")!=null)
				{
					pageStatuss = session.getAttribute("pageStatuss").toString();
				}
				if(session.getAttribute("updateInMaker")!=null)
				{
					updateInMaker = session.getAttribute("updateInMaker").toString();
				}
				  if(session.getAttribute("CUA")!=null)
					 {
					  cuaStatus = session.getAttribute("CUA").toString();
					 }
				//ArrayList<CustomerSaveVo> businessActivityDetails = service.getBusinessActivity(id,pageStatuss,statusCase,updateFlag,updateInMaker,pageStatuss,cuaStatus);
				//session.setAttribute("businessActivityDetails", businessActivityDetails);
				  ArrayList<CustomerSaveVo> list = service.getCustBankDetails(String.valueOf(id), statusCase, updateInMaker, pageStatuss, updateFlag, cuaStatus,source);
					session.setAttribute("list", list);
			return mapping.findForward("updateSuccess");

		}	
		
		
}