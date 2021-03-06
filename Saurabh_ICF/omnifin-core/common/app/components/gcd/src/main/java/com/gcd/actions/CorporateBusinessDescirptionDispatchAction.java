/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.gcd.actions;

import java.lang.reflect.InvocationTargetException;
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
import com.gcd.dao.CorporateDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class CorporateBusinessDescirptionDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(CorporateBusinessDescirptionDispatchAction.class.getName());
	
	public ActionForward saveCorporateBusinessDescirption(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException,Exception {
	{
		 logger.info("In saveCorporateBusinessDescirption in CorporateBusinessDescirptionDispatchAction");
		    HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String userId="";
			String bDate="";
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bDate=userobj.getBusinessdate();
			}else{
				logger.info("here saveCorporateBusinessDescirption method of CorporateBusinessDescirptionDispatchAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			DynaValidatorForm CustomerAddressValidatorForm= (DynaValidatorForm)form;
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
		 String id="";
		 String pageStatuss="";
		 String updateFlag="";
		 String statusCase="";
		 if(session.getAttribute("corporateId")!=null)
		 {
			 id = session.getAttribute("corporateId").toString();
		 }
		
		
		 if(session.getAttribute("pageStatuss")!=null)
		 {
			 pageStatuss = session.getAttribute("pageStatuss").toString();
		 }
		 if(session.getAttribute("updateFlag")!=null)
		    {
		    	updateFlag=session.getAttribute("updateFlag").toString();
		    		
		    }
		 if(session.getAttribute("statusCase")!=null)
			{
				statusCase = session.getAttribute("statusCase").toString();
			}
		CustomerSaveVo vo = new CustomerSaveVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CustomerAddressValidatorForm);
		vo.setPageStatus(pageStatuss);
		vo.setUpdateFlag(updateFlag);
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		 CorporateDAO detail=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
		 logger.info("Implementation class: "+detail.getClass());
		 String updateInMaker="";
		 if(session.getAttribute("updateInMaker")!=null)
			{
			 updateInMaker = session.getAttribute("updateInMaker").toString();
			}
		 
		vo.setStatusCase(statusCase);
		String insertInTempFlag="";
		String cusType="";
		 boolean status =false;
		 
		if(!statusCase.equals("") )
		{
			if(session.getAttribute("cusType")!=null)
			{
				cusType=session.getAttribute("cusType").toString();
			}
			String query1 = "Select customer_id from gcd_customer_m_temp where customer_id="+id;
			logger.info("Query  :  "+query1);
			String custCheckInTemp = ConnectionDAO.singleReturn(query1);
			logger.info("custCheckInTemp *?********************************** "+custCheckInTemp);
			if(custCheckInTemp==null)
			{
				insertInTempFlag = detail.insertAllIntoTempFromMainTable(id+"",cusType);
				 session.setAttribute("updateInMaker", "updateInMaker");
				 logger.info("status: "+insertInTempFlag);
				 if(CommonFunction.checkNull(insertInTempFlag).equalsIgnoreCase("S"))
				 {
					 logger.info("Save after temp also Business description : ");
					 status = detail.saveBusinessDescription(vo,id,source);
				 }
				 request.setAttribute("procval",insertInTempFlag );
				// logger.info("procval-1111111111------->"+request.getAttribute("procval"));
			}
			else
				 status = detail.saveBusinessDescription(vo,id,source);
		}
		else
		{
			 logger.info("ELSE Save Only Save Business description: ");
			status = detail.saveBusinessDescription(vo,id,source);
		}
		 
		 if(status)
		 {
			 session.removeAttribute("businessDesc");
			 if(session.getAttribute("statusCase")!=null)
				{
					statusCase = session.getAttribute("statusCase").toString();
				}
			 if(session.getAttribute("updateInMaker")!=null)
				{
					updateInMaker = session.getAttribute("updateInMaker").toString();
				}
			
			 if(session.getAttribute("pParentGroup")!=null)
				{
					session.removeAttribute("pParentGroup");
				}
				if(session.getAttribute("strParentOption")!=null)
				{
					session.removeAttribute("strParentOption");
				}
				String cuaStatus="";
				  if(session.getAttribute("CUA")!=null)
					 {
					  cuaStatus = session.getAttribute("CUA").toString();
					 }	
				  
				  ArrayList<Object> businessDesc = detail.getBusinessDescription(id,statusCase,updateInMaker,pageStatuss,updateFlag,cuaStatus,source);
					 logger.info("Size of getBusinessDescription............................"+businessDesc.size());
					 session.setAttribute("businessDesc", businessDesc);
//			 ArrayList<Object> businessDesc = detail.getProfileDetails(id,statusCase,updateInMaker,pageStatuss,updateFlag,cuaStatus);
//			 session.setAttribute("briefAddr", briefAddr);
			 request.setAttribute("sms","S");
			 request.setAttribute("status", "");
		 }
		 else
		 {
			 request.setAttribute("sms","E");
			 request.setAttribute("status", ""); 
		 }
		 logger.info(" saveCorporateBusinessDescirption status: "+status);
		 logger.info("procval--saveCorporateBusinessDescirption------>");
		 return mapping.findForward("success");
	}
	}
}