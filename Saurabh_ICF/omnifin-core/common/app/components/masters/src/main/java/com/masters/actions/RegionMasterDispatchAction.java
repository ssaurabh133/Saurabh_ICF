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
import com.masters.vo.RegionMasterVo;

/** 
 * MyEclipse Struts
 * Creation date: 05-21-2011
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class RegionMasterDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(RegionMasterDispatchAction.class.getName());
	 public ActionForward openAddRegion(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)	throws Exception
				{ServletContext context = getServlet().getServletContext();
					logger.info(" in openAddRegion()");
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
		 

			public ActionForward saveRegionDetails(ActionMapping mapping, ActionForm form,
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
			DynaValidatorForm RegionMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
			RegionMasterVo vo = new RegionMasterVo();
			vo.setMakerId(userId);
			vo.setMakerDate(bDate);
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, RegionMasterAddDyanavalidatiorForm);	
			
			CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			
	        String sms="";
		
			boolean status = bp.insertRegionMaster(vo);
			logger.info("Inside Region Master Action.....displaying status...."+status);
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
		
		
		
			public ActionForward openEditRegion(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)	throws Exception {
							
								RegionMasterVo regionMasterVo=new RegionMasterVo(); 
								logger.info("In openEditRegion");
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
								
								regionMasterVo.setRegionId(request.getParameter("RegionId"));
								logger.info("In openEditRegion---status---- by getpara-"+request.getParameter("RegionId"));  
								logger.info("In openEditRegion---status---- by getpara by vo-"+regionMasterVo.getRegionId());
								
								CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
						        
						        ArrayList list = bp.searchRegionData(regionMasterVo);
								logger.info("In openEditRegion RegionMasterVo list"+list.size());
								request.setAttribute("list", list);
							
								
								regionMasterVo=(RegionMasterVo) list.get(0);
								RegionMasterVo docVo=new RegionMasterVo();
								docVo=(RegionMasterVo) list.get(0);
								logger.info("In openEditRegion---status---- by   getpara by vo-"+docVo.getRegionStatus());
								
							
								request.setAttribute("status", regionMasterVo.getRegionStatus());
								request.setAttribute("editVal", "editVal");
							   return mapping.findForward("editRegion");	
							}
					
					
					public ActionForward updateRegion(ActionMapping mapping, ActionForm form,
							HttpServletRequest request, HttpServletResponse response) throws Exception {
						
						logger.info("In updateRegionMaster.......");
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
						RegionMasterVo regionMastervo=new RegionMasterVo(); 
						DynaValidatorForm RegionMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
						org.apache.commons.beanutils.BeanUtils.copyProperties(regionMastervo, RegionMasterAddDyanavalidatiorForm);	

					
						logger.info("In updateRegionDetails---------");    
						
						CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
						
				        boolean status=bp.updateRegionData(regionMastervo);
				        String sms="";
				        if(status){
				        	sms="M";
							request.setAttribute("sms",sms);
							request.setAttribute("editValUpdate", "editValUpdate");
						}
						else{
							sms="E";
							request.setAttribute("sms",sms);
							ArrayList<RegionMasterVo> list =new ArrayList<RegionMasterVo>();
							list.add(regionMastervo);
							logger.info("In updateRegion list"+ list.size());
							
							request.setAttribute("editValUpdate", "editValUpdate");
							request.setAttribute("list", list);
							request.setAttribute("status", regionMastervo.getRegionStatus());
							
						}
//				        regionMastervo.setRegionId(request.getParameter("RegionId"));
//						logger.info("In updateRegiontDetails---status-----"+regionMastervo.getRegionStatus());
//						logger.info("in updateRegionDetails ------description-------"+regionMastervo.getRegionDes());
						        
				        return mapping.getInputForward();
				      
						
					}
					
	}