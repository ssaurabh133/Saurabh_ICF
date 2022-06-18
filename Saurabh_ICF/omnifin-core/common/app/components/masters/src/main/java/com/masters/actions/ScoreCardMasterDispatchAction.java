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
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.dao.ScoreParamMasterDAO;
import com.masters.vo.scoreCardMasterVo;


public class ScoreCardMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ScoreMasterDispatchAction.class.getName());	

			// For Score Card Section
			
	public ActionForward initialCardList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info(" IN GcdGroupMasterSearchProcessingAction addGroupMasterDetails()....");
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
				DynaValidatorForm ScoreCardMasterSearchDynaValidatorForm=(DynaValidatorForm) form;
				scoreCardMasterVo ob= new scoreCardMasterVo();
				int currentPageLink = 0;
				if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
				{
					currentPageLink=1;
				}
				else
				{
					currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
				}
				
				logger.info("current page link ................ "+request.getParameter("d-49520-p"));
				
				ob.setCurrentPageLink(currentPageLink);
				
				org.apache.commons.beanutils.BeanUtils.copyProperties(ob,ScoreCardMasterSearchDynaValidatorForm);
				ScoreParamMasterDAO MasterDao=(ScoreParamMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ScoreParamMasterDAO.IDENTITY);
		        logger.info("Implementation class: "+MasterDao.getClass()); 
				//ScoreParamMasterDAO MasterDao = new ScoreParamMasterDAOImpl();	
				
				ArrayList list=new ArrayList();
				list= MasterDao.searchCardScoreMaster(ob,request);
				request.setAttribute("list",list);
				
				
				logger.info("current page link ................ "+list.size());
				logger.info("current page link ................ "+request.getAttribute("flag"));
//				if(CommonFunction.checkNull(request.getAttribute("flag")).toString().equalsIgnoreCase("yes")){
//					request.setAttribute("sms","No");
//				}
				
			    return mapping.findForward("cardList");	
			}
			public ActionForward scoringCardList(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)	throws Exception {
				ServletContext context = getServlet().getServletContext();
						logger.info(" IN GcdGroupMasterSearchProcessingAction addGroupMasterDetails()....");
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
						DynaValidatorForm ScoreCardMasterSearchDynaValidatorForm=(DynaValidatorForm) form;
						scoreCardMasterVo ob= new scoreCardMasterVo();
						int currentPageLink = 0;
						if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
						{
							currentPageLink=1;
						}
						else
						{
							currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
						}
						
						logger.info("current page link ................ "+request.getParameter("d-49520-p"));
						
						ob.setCurrentPageLink(currentPageLink);
						
						org.apache.commons.beanutils.BeanUtils.copyProperties(ob,ScoreCardMasterSearchDynaValidatorForm);
						ScoreParamMasterDAO MasterDao=(ScoreParamMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ScoreParamMasterDAO.IDENTITY);
				        logger.info("Implementation class: "+MasterDao.getClass()); 
						//ScoreParamMasterDAO MasterDao = new ScoreParamMasterDAOImpl();	
						
						ArrayList list=new ArrayList();
						list= MasterDao.searchCardScoreMaster(ob,request);
						request.setAttribute("list",list);
						
						
						logger.info("current page link ................ "+list.size());
//						logger.info("current page link ................ "+request.getAttribute("flag"));
//						if(CommonFunction.checkNull(request.getAttribute("flag")).toString().equalsIgnoreCase("yes")){
//							request.setAttribute("sms","No");
//						}
//						
					    return mapping.findForward("cardList");	
					}
			
			
			public ActionForward addCardScoreDetails(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)
			throws Exception {
				
				HttpSession session=request.getSession(false);
				UserObject userobj=new UserObject();
				userobj=(UserObject)session.getAttribute("userobject");
				String userId="";
				String bDate="";
				if(userobj!=null)
				{
						userId=userobj.getUserId();
						bDate=userobj.getBusinessdate();
				}
				
				ServletContext context = getServlet().getServletContext();
				boolean flag=false;
				
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
				
				DynaValidatorForm ScoreCardMasterSearchDynaValidatorForm=(DynaValidatorForm)form;
				scoreCardMasterVo scoreCardMasterVo= new scoreCardMasterVo();
				org.apache.commons.beanutils.BeanUtils.copyProperties(scoreCardMasterVo, ScoreCardMasterSearchDynaValidatorForm);

				request.setAttribute("save", "save");
				
			    return mapping.findForward("scoreCardAdd");
			}
			
			
			

			public ActionForward insertCardScoreMasterDetails(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)throws Exception {
				
				HttpSession session=request.getSession(false);
				UserObject userobj=new UserObject();
				userobj=(UserObject)session.getAttribute("userobject");
				String userId="";
				String bDate="";
				if(userobj!=null)
				{
						userId=userobj.getUserId();
						bDate=userobj.getBusinessdate();
				}
				ServletContext context = getServlet().getServletContext();
				boolean flag=false;
				
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
				
				DynaValidatorForm ScoreCardMasterDynaValidatorForm=(DynaValidatorForm) form;
				logger.info("-----------------------above the code");
				scoreCardMasterVo ob= new scoreCardMasterVo();
				logger.info("-----------------------below the vo");
				org.apache.commons.beanutils.BeanUtils.copyProperties(ob,ScoreCardMasterDynaValidatorForm);
				logger.info("-----------------------below the save bean");
			
				ob.setMakerId(userId);
				ob.setMakerDate(bDate);
				
				ScoreParamMasterDAO MasterDao=(ScoreParamMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ScoreParamMasterDAO.IDENTITY);
		        logger.info("Implementation class: "+MasterDao.getClass()); 
				//ScoreParamMasterDAO MasterDao = new ScoreParamMasterDAOImpl();	
				String msg="";
				boolean result = MasterDao.insertCardScoreMaster(ob);
			
				if(result){
					
					request.setAttribute("msg","S");
				}else{
					
					request.setAttribute("msg","E");
				}
				request.setAttribute("save", "save");
			    return mapping.findForward("addCardNew");
			}
			
			
			
			public ActionForward openCardScoreMaster(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)	throws Exception {
				ServletContext context = getServlet().getServletContext();
								scoreCardMasterVo scoreCardMasterVo= new scoreCardMasterVo();
								logger.info("In openCardScoreMaster");
								
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
								logger.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+request.getParameter("cardId"));
								
								String CardId = request.getParameter("cardId");
								
								scoreCardMasterVo.setScoreCardId(CardId);
								//scoreCardMasterVo.setScoreCardDesc(request.getParameter("cardDesc"));

								ScoreParamMasterDAO masterDAO=(ScoreParamMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ScoreParamMasterDAO.IDENTITY);
						        logger.info("Implementation class: "+masterDAO.getClass()); 
								//ScoreParamMasterDAO masterDAO = new ScoreParamMasterDAOImpl();
								ArrayList<scoreCardMasterVo> list = masterDAO.searchCardScoreMaster(scoreCardMasterVo,request);
								logger.info("In openScoreMaster list"+list.size());
								request.setAttribute("list", list);
							
								scoreCardMasterVo docVo =new scoreCardMasterVo();
								
								docVo=list.get(0);						
								request.setAttribute("status",docVo.getCardStatus());
								request.setAttribute("editVal", "editVal");
								
							   return mapping.findForward("editCardDetails");	
							}

			
			public ActionForward updateCardScore(ActionMapping mapping, ActionForm form,
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
				
				scoreCardMasterVo scoreCardMasterVo=new scoreCardMasterVo(); 
				DynaValidatorForm ScoreCardMasterDynaValidatorForm= (DynaValidatorForm)form;
				org.apache.commons.beanutils.BeanUtils.copyProperties(scoreCardMasterVo, ScoreCardMasterDynaValidatorForm);	

				ScoreParamMasterDAO masterDAO=(ScoreParamMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ScoreParamMasterDAO.IDENTITY);
		        logger.info("Implementation class: "+masterDAO.getClass()); 
				//ScoreParamMasterDAO masterDAO = new ScoreParamMasterDAOImpl();
		        		        
		        boolean status=masterDAO.updateCardScoreMaster(scoreCardMasterVo);
		        String sms="";
		    	logger.info("-----------------------below the save bean"+status);
		        if(status){
					sms="M";
					request.setAttribute("msg",sms);
					request.setAttribute("editVal", "editVal");
				}
				else{
					sms="E";
					request.setAttribute("msg",sms);
					ArrayList<scoreCardMasterVo> list =new ArrayList<scoreCardMasterVo>();
					list.add(scoreCardMasterVo);
					logger.info("In openEditCountry list"+ list.size());
					
					request.setAttribute("editVal", "editVal");
					request.setAttribute("status", scoreCardMasterVo.getCardStatus());
					
				}
		      
		       // countryMastervo.setCountryId(request.getParameter("CountryId"));
				
				return mapping.findForward("updateCardScore");
		      
				
			}


}
