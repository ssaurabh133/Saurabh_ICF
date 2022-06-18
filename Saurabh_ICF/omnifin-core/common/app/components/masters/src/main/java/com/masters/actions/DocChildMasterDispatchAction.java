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

import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.DocChildMasterVo;


public class DocChildMasterDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(DocChildMasterDispatchAction.class.getName());
	 public ActionForward openAddDoc(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)	throws Exception
				{ServletContext context = getServlet().getServletContext();
					logger.info(" in openAddDoc()");
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
		 

			public ActionForward saveDocDetails(ActionMapping mapping, ActionForm form,
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
				DynaValidatorForm DocChildMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
				DocChildMasterVo vo = new DocChildMasterVo();

				vo.setMakerId(userId);
				vo.setMakerDate(bDate);
				org.apache.commons.beanutils.BeanUtils.copyProperties(vo, DocChildMasterAddDyanavalidatiorForm);
				
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
				String sms="";
		
				boolean status = cpm.insertDocChildMaster(vo);
				logger.info("Inside State Master Action.....displaying status...."+status);
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
	
		
			public ActionForward openEditDocChild(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)	throws Exception {
				ServletContext context = getServlet().getServletContext();
								DocChildMasterVo docChildMasterVo=new DocChildMasterVo(); 
								logger.info("In openEditState");
								
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
								docChildMasterVo.setDocChildID(request.getParameter("DocChildId"));
								logger.info("In openEditDocChild---status---- by getpara-"+request.getParameter("DocChildId"));  
								logger.info("In openEditDocChild---status---- by getpara by vo-"+docChildMasterVo.getDocChildID());
								
								CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
								
						        ArrayList<DocChildMasterVo> list = cpm.searchDocChildData(docChildMasterVo);
								logger.info("In openEditDocChild DocChildMasterVo list"+list.size());
								request.setAttribute("list", list);
							
								
								docChildMasterVo=list.get(0);
								DocChildMasterVo docVo=new DocChildMasterVo();
								docVo=list.get(0);
								logger.info("In openEditDocChild---status---- by   getpara by vo-"+docVo.getStatus());
								
							
								request.setAttribute("status", docChildMasterVo.getStatus());
								request.setAttribute("editVal", "editVal");
							   return mapping.findForward("editState");	
							}
					
					
					public ActionForward updateDocChild(ActionMapping mapping, ActionForm form,
							HttpServletRequest request, HttpServletResponse response) throws Exception {
						ServletContext context = getServlet().getServletContext();
						logger.info("In updateDocChild.......");
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
						DocChildMasterVo docChildMasterVo=new DocChildMasterVo(); 
						DynaValidatorForm DocChildMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
						org.apache.commons.beanutils.BeanUtils.copyProperties(docChildMasterVo, DocChildMasterAddDyanavalidatiorForm);	
						
						CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
						
						logger.info("In updateDocChildDetails-----");
						
				        boolean status=cpm.updateDocChildData(docChildMasterVo);
				        String sms="";
				        if(status){
			        	sms="M";
							request.setAttribute("sms",sms);
							request.setAttribute("editValUpdate", "editValUpdate");
						}
						else{
							sms="E";
							request.setAttribute("sms",sms);
							ArrayList<DocChildMasterVo> list =new ArrayList<DocChildMasterVo>();
							list.add(docChildMasterVo);
							request.setAttribute("editValUpdate", "editValUpdate");
							request.setAttribute("list", list);
							request.setAttribute("status", docChildMasterVo.getStatus());
							
						}
				        
						logger.info("In updateDocChildDetails---DocChildID-----"+docChildMasterVo.getDocChildID());
						logger.info("in updateDocChildDetails ------description-------"+docChildMasterVo.getDocChildDes());
						        
				        return mapping.getInputForward();
				      
						
					}
			
	}