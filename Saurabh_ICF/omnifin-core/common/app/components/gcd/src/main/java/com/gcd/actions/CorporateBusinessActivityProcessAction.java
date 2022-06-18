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

import com.VO.CorporateBusinessActivityVO;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.gcd.dao.CorporateDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class CorporateBusinessActivityProcessAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(CorporateBusinessActivityProcessAction.class.getName());
	
	public ActionForward saveBusinessActivity(ActionMapping mapping, ActionForm form,
			   HttpServletRequest request, HttpServletResponse response)throws Exception
		   {
			   logger.info("In saveBusinessActivity() --> CorporateBusinessActivityProcessAction");
			
			    HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId="";
				String bDate="";
				if(userobj!=null)
				{
						userId=userobj.getUserId();
						bDate=userobj.getBusinessdate();
				}else{
					logger.info("here saveBusinessActivity method of CorporateBusinessActivityProcessAction the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				
				DynaValidatorForm BusinessActivityDynaValidatorForm = (DynaValidatorForm)form; 
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
				CorporateBusinessActivityVO sh = new CorporateBusinessActivityVO();	      
				int id=0;
				String pageStatuss="";
				String updateFlag="";
				if(session.getAttribute("pageStatuss")!=null)
				{
					pageStatuss = session.getAttribute("pageStatuss").toString();
				}
				
				if(session.getAttribute("updateFlag")!=null)
				{
					updateFlag=session.getAttribute("updateFlag").toString();
				}
				
			    sh.setPageStat(pageStatuss);
			    sh.setUpdateFlag(updateFlag);
			    sh.setMakerId(userId);
			    sh.setMakerDate(bDate);
				if(session.getAttribute("corporateId")!=null)
				{
					id = Integer.parseInt(session.getAttribute("corporateId").toString());
				}
			  
				org.apache.commons.beanutils.BeanUtils.copyProperties(sh, BusinessActivityDynaValidatorForm);
			 
				String statusCase="";
				String updateInMaker="";
				CorporateDAO service=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
				logger.info("Implementation class: "+service.getClass());
				boolean status = false;
				if(session.getAttribute("statusCase")!=null)
    			{
    				statusCase = session.getAttribute("statusCase").toString();
    			}
				sh.setStatusCase(statusCase);
				String insertInTempFlag="";
				String cusType="";
	    		 
				if(!statusCase.equals("") )
				{
					if(session.getAttribute("corporateId")!=null)
					{
						id = Integer.parseInt(session.getAttribute("corporateId").toString());
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
					status = service.saveBusinessActivity(sh,id,source);
	    		}
	    		else
	    		{
	    			logger.info("only insert ****************************.....statusCase.........."+statusCase);
	    			status = service.saveBusinessActivity(sh,id,source);
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
					ArrayList<CorporateBusinessActivityVO> businessActivityDetails = service.getBusinessActivity(id,pageStatuss,statusCase,updateFlag,updateInMaker,pageStatuss,cuaStatus,source);
					session.setAttribute("businessActivityDetails", businessActivityDetails);
				 }
				 else
				 {
					 request.setAttribute("sms","E");
				 }
			return mapping.findForward("success");
		}
		
		public ActionForward updateBusinessActivity(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {
			
			    HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId="";
				String bDate="";
				if(userobj!=null)
				{
						userId=userobj.getUserId();
						bDate=userobj.getBusinessdate();
				}else{
					logger.info("here updateBusinessActivity method of CorporateBusinessActivityProcessAction the session is out----------------");
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
			
			DynaValidatorForm BusinessActivityDynaValidatorForm = (DynaValidatorForm)form; 
			CorporateBusinessActivityVO sh = new CorporateBusinessActivityVO();
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
			int id = Integer.parseInt(session.getAttribute("corporateId").toString());
				
			org.apache.commons.beanutils.BeanUtils.copyProperties(sh, BusinessActivityDynaValidatorForm);
			
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
			
			String cusType="";
			
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
					int status = service.updateBusinessActivity(sh,id,recStatus,statusCase,updateFlag,pageStatuss,source);
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
					int status = service.updateBusinessActivity(sh,id,recStatus,statusCase,updateFlag,pageStatuss,source);
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
				ArrayList<CorporateBusinessActivityVO> businessActivityDetails = service.getBusinessActivity(id,pageStatuss,statusCase,updateFlag,updateInMaker,pageStatuss,cuaStatus,source);
				session.setAttribute("businessActivityDetails", businessActivityDetails);
			return mapping.findForward("success");

		}
}
