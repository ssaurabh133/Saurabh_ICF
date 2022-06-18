/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
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

import com.business.ejbClient.CommonMasterBussinessSessionBeanRemote;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.CountryMasterVo;

public class CountryMasterDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(CountryMasterDispatchAction.class.getName());
	 public ActionForward openAddCountry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				logger.info(" in openAddCountry()");
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
				
				request.setAttribute("save", "save");
			    return mapping.findForward("openAdd");	
			}
	 

		public ActionForward saveCountryDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse resopnse) throws Exception{
			ServletContext context = getServlet().getServletContext();
			//HttpSession session=request.getSession(false);
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
			
			String userId="";
			String bDate="";
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bDate=userobj.getBusinessdate();
			}
	
		DynaValidatorForm CountryMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
		CountryMasterVo vo = new CountryMasterVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CountryMasterAddDyanavalidatiorForm);
		

		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
	
		String sms="";
	
		boolean status = bp.insertCountryMaster(vo);
		logger.info("Inside Country Master Action.....displaying status...."+status);
		if(status){
			sms="S";
			request.setAttribute("sms",sms);
			request.setAttribute("save", "save");
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
			request.setAttribute("save", "save");
		}
		logger.info("status"+status);
		return mapping.getInputForward();
	}
	
	
	
		public ActionForward openEditCountry(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)	throws Exception { 
			ServletContext context = getServlet().getServletContext();
							CountryMasterVo CountryMasterVo=new CountryMasterVo(); 
							logger.info("In openEditCountry");
							
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
							
							CountryMasterVo.setCountryId(request.getParameter("CountryId"));
							logger.info("In openEditCountry---status---- by getpara-"+request.getParameter("CountryId"));  
							logger.info("In openEditCountry---status---- by getpara by vo-"+CountryMasterVo.getCountryId());
							
							CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
							
							ArrayList list = bp.searchCountryData(CountryMasterVo);
							logger.info("In openEditCountry CountryMasterVo list"+list.size());
							request.setAttribute("list", list);
						
							
							CountryMasterVo=(CountryMasterVo) list.get(0);
							CountryMasterVo docVo=new CountryMasterVo();
							docVo=(CountryMasterVo) list.get(0);
							logger.info("In openEditCountry---status---- by getpara by vo-"+docVo.getCountryStatus());
							
						
							request.setAttribute("status", CountryMasterVo.getCountryStatus());
							request.setAttribute("editVal", "editVal");
						   return mapping.findForward("editCountry");	
						}
				
				
				public ActionForward updateCountry(ActionMapping mapping, ActionForm form,
						HttpServletRequest request, HttpServletResponse response) throws Exception {
					ServletContext context = getServlet().getServletContext();
					logger.info("In updateCountryMaster.......");
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
					
					CountryMasterVo countryMastervo=new CountryMasterVo(); 
					DynaValidatorForm CountryMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
					org.apache.commons.beanutils.BeanUtils.copyProperties(countryMastervo, CountryMasterAddDyanavalidatiorForm);	

					logger.info("In updateCountryDetails---------");    
					
					CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
					
			        boolean status=bp.updateCountryData(countryMastervo);
			        String sms="";
			        if(status){
						sms="M";
						request.setAttribute("sms",sms);
						request.setAttribute("editVal", "editVal");
					}
					else{
						sms="E";
						request.setAttribute("sms",sms);
						ArrayList<CountryMasterVo> list =new ArrayList<CountryMasterVo>();
						list.add(countryMastervo);
						logger.info("In openEditCountry list"+ list.size());
						
						request.setAttribute("editVal", "editVal");
						request.setAttribute("list", list);
						request.setAttribute("status", countryMastervo.getCountryStatus());
						
					}
			       // countryMastervo.setCountryId(request.getParameter("CountryId"));
					logger.info("In updateCountrytDetails---status-----"+countryMastervo.getCountryStatus());
					logger.info("in updateCountryDetails ------description-------"+countryMastervo.getCountryDes());
					        
			        return mapping.getInputForward();
			      
					
				}
				
}