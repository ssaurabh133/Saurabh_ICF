/*
Created By:- Abhishek Mathur
Date of Creation:- 14/10/2015
Purpose:- Rejected deals would be available at deal capturing stage for re-processing 
*/

package com.cp.actions;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cp.vo.ReprocessingDealVo;
import com.cp.dao.CreditProcessingDAO;
import com.cp.dao.DealReassignDAO;

public class DealReprocessingDispatchAction extends DispatchAction
	{
	
		private static final Logger logger=Logger.getLogger(DealReprocessingDispatchAction.class.getName()); 
		public ActionForward rejectedDealSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
		throws Exception
			{
				logger.info("in rejectedDealSearch mehtod of DealReprocessingDispatchAction::::::::::::::::::");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String makerId ="";
				String businessDate ="";
				
				if(userobj!=null)
					{
						makerId=userobj.getUserId();
						businessDate = userobj.getBusinessdate();
					}
				else
					{
						logger.info("here in rejectedDealSearch method of DealReprocessingDispatchAction action the session is out----------------");
						return mapping.findForward("sessionOut");
					}
				
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
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
				
				String FunctionId = CommonFunction.checkNull(session.getAttribute("functionId"));
				request.setAttribute("FunctionId", FunctionId);
				
				ReprocessingDealVo ReprocessingDealVo=new ReprocessingDealVo();
				DynaValidatorForm DealReprocessingDynaValidator = (DynaValidatorForm)form;
				org.apache.commons.beanutils.BeanUtils.copyProperties(ReprocessingDealVo, DealReprocessingDynaValidator);
				
				ReprocessingDealVo.setCurrentDate(businessDate);
				String dealId = ReprocessingDealVo.getLbxDealNo();
				logger.info("Deal No In rejectedDealSearch method of DealReprocessingDispatchAction :- "+dealId);
				
				CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass());
				logger.info("makerId==================================="+makerId);
				ArrayList<ReprocessingDealVo> rejectedDealList= dao.getRejectedDealNo(dealId,makerId,businessDate);
				
				if((rejectedDealList.size())> 0)
				{
					request.setAttribute("rejectedDealList", rejectedDealList);
									
				}
				else{
						logger.info("***********Selected Deal Number Not Found*****************");
						request.setAttribute("rejectedDealList", rejectedDealList);
						request.setAttribute("msg", "rejectedDealList");
					}
				
				return mapping.findForward("searchRejectedDeal");	
			}
		
		
		public ActionForward saveAndForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
		throws Exception
			{
			
				logger.info("in saveAndForward mehtod of DealReprocessingDispatchAction::::::::::::::::::");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId ="";
				String businessDate ="";
				
				if(userobj!=null)
					{
						userId=userobj.getUserId();
						businessDate = userobj.getBusinessdate();
					}
				else
					{
						logger.info("here in saveAndForward method of DealReprocessingDispatchAction action the session is out----------------");
						return mapping.findForward("sessionOut");
					}
				
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
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
				boolean status=false;
				String FunctionId = CommonFunction.checkNull(session.getAttribute("functionId"));
				request.setAttribute("FunctionId", FunctionId);
				ReprocessingDealVo ReprocessingDealVo=new ReprocessingDealVo();
				DynaValidatorForm DealReprocessingDynaValidator = (DynaValidatorForm)form;
				org.apache.commons.beanutils.BeanUtils.copyProperties(ReprocessingDealVo, DealReprocessingDynaValidator);
				
				String dealId = ReprocessingDealVo.getLbxDealNo();
				
				
				logger.info("Deal No In saveAndForward method of DealReprocessingDispatchAction :- "+dealId);
				
				
				CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass());
				
				
				status = dao.getDesionfromMaker(dealId);
								
				if(status)
				{
					logger.info("status................."+status);
					request.setAttribute("msg", "dealForward");
				}
				else 
				{
					logger.info("status................."+status);
					request.setAttribute("msg", "dataNotSaved");
				}
				
				return mapping.findForward("searchRejectedDeal");	
			}

		
		public ActionForward rejectedDealSearchAuthor(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
				throws Exception
					{
						logger.info("in rejectedDealSearchAuthor mehtod of DealReprocessingDispatchAction::::::::::::::::::");
						HttpSession session = request.getSession();
						UserObject userobj=(UserObject)session.getAttribute("userobject");
						String userId ="";
						String businessDate ="";
						
						if(userobj!=null)
							{
								userId=userobj.getUserId();
								businessDate = userobj.getBusinessdate();
							}
						else
							{
								logger.info("here in rejectedDealSearchAuthor method of DealReprocessingDispatchAction action the session is out----------------");
								return mapping.findForward("sessionOut");
							}
						
						Object sessionId = session.getAttribute("sessionID");
						//for check User session start
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
						
						ReprocessingDealVo ReprocessingDealVo=new ReprocessingDealVo();
						DynaValidatorForm DealReprocessingDynaValidator = (DynaValidatorForm)form;
						org.apache.commons.beanutils.BeanUtils.copyProperties(ReprocessingDealVo, DealReprocessingDynaValidator);
						
						
						String dealId = ReprocessingDealVo.getLbxDealNo();
						logger.info("Deal No In rejectedDealSearch method of DealReprocessingDispatchAction :- "+dealId);
						
						CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
						logger.info("Implementation class: "+dao.getClass());
						ArrayList<ReprocessingDealVo> rejectedDealListAuthor= dao.getRejectedDealNoAuthor(dealId);
						
						if((rejectedDealListAuthor.size())> 0)
						{
							request.setAttribute("rejectedDealListAuthor", rejectedDealListAuthor);
											
						}
						else{
								logger.info("***********Selected Deal Number Not Found*****************");
								request.setAttribute("blankrejectedDealList", rejectedDealListAuthor);
							}
						
						return mapping.findForward("searchRejectedDeal");	
					}
		
				public ActionForward saveDecision(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
				throws Exception
					{
					
						logger.info("in saveDecision mehtod of DealReprocessingDispatchAction::::::::::::::::::");
						HttpSession session = request.getSession();
						UserObject userobj=(UserObject)session.getAttribute("userobject");
						String userId ="";
						String businessDate ="";
						String companyId ="";
						if(userobj!=null)
							{
								userId=userobj.getUserId();
								logger.info("userId in save Decision method"+userId);
								businessDate = userobj.getBusinessdate();
								logger.info("businessDate in save Decision method"+businessDate);
								companyId=CommonFunction.checkNull(userobj.getCompanyId()+"");
							}
						else
							{
								logger.info("here in saveDecision method of DealReprocessingDispatchAction action the session is out----------------");
								return mapping.findForward("sessionOut");
							}
						
						Object sessionId = session.getAttribute("sessionID");
						//for check User session start
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
						boolean status=false;
						String statusProc="";
						ReprocessingDealVo ReprocessingDealVo=new ReprocessingDealVo();
						DynaValidatorForm DealReprocessingDynaValidator = (DynaValidatorForm)form;
						org.apache.commons.beanutils.BeanUtils.copyProperties(ReprocessingDealVo, DealReprocessingDynaValidator);
						
						String dealId = ReprocessingDealVo.getLbxDealNo();
						String decision = ReprocessingDealVo.getDecision();
						ReprocessingDealVo.setCurrentDate(businessDate);
						ReprocessingDealVo.setUserId(userId);
						
						logger.info("Deal No In saveDecision method of DealReprocessingDispatchAction :- "+dealId);
						logger.info("Decision In saveDecision method of DealReprocessingDispatchAction :- "+decision);
						
						CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
						logger.info("Implementation class: "+dao.getClass());
						
						
						statusProc = dao.getDesionfromAuthor(ReprocessingDealVo ,dealId,decision);
						logger.info("value of status in action "+statusProc);
//						if(decision.equalsIgnoreCase("A"))
//						{
//							DealReassignDAO dealReassignDAO=(DealReassignDAO)DaoImplInstanceFactory.getDaoImplInstance(DealReassignDAO.IDENTITY);
//							boolean dealMoveStatus=dealReassignDAO.UpdateInsertDealMovemenDtl(ReprocessingDealVo,companyId,businessDate,userId);
//							//CommonFunction.stageMovement(companyId, "DC","I",""+dealId, "DC", businessDate,userId);
//						}
						/*if(status)
						{
							logger.info("status................."+status);
							request.setAttribute("msg", "datasaved");
						}
						else 
						{
							logger.info("status................."+status);
							request.setAttribute("msg", "dataNotSaved");
						}*/
						String msg="";
						if(statusProc.equalsIgnoreCase("S"))
						{
							request.setAttribute("msg", "datasaved");
						}else {
							logger.info("status................."+statusProc);
							request.setAttribute("msg", "showprocmsg");
							request.setAttribute("sms1", statusProc);
						}
						//request.setAttribute("msg", msg);
						return mapping.findForward("searchRejectedDeal");	
					}
		
		
}
