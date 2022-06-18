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
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.dao.ScoreParamMasterDAO;
import com.masters.vo.scoreMasterVo;

public class ScoreMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ScoreMasterDispatchAction.class.getName());	
	public ActionForward initialscoringMaster(ActionMapping mapping, ActionForm form,
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
				DynaValidatorForm ScoreMasterDynaValidatorForm=(DynaValidatorForm) form;
				scoreMasterVo ob= new scoreMasterVo();
				org.apache.commons.beanutils.BeanUtils.copyProperties(ob,ScoreMasterDynaValidatorForm);
				ScoreParamMasterDAO MasterDao=(ScoreParamMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ScoreParamMasterDAO.IDENTITY);
		        logger.info("Implementation class: "+MasterDao.getClass()); 
				//ScoreParamMasterDAO MasterDao = new ScoreParamMasterDAOImpl();	
				
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
				ArrayList list=new ArrayList();
				list= MasterDao.searchScoreMaster(ob,request);
				
				
				request.setAttribute("list",list);
//				if(CommonFunction.checkNull(request.getAttribute("flag")).toString().equalsIgnoreCase("yes")){
//					request.setAttribute("sms","No");
//				}
				
			    return mapping.findForward("scoringList");	
			}
	
	
	public ActionForward scoringMasterList(ActionMapping mapping, ActionForm form,
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
				DynaValidatorForm ScoreMasterDynaValidatorForm=(DynaValidatorForm) form;
				scoreMasterVo ob= new scoreMasterVo();
				org.apache.commons.beanutils.BeanUtils.copyProperties(ob,ScoreMasterDynaValidatorForm);
				ScoreParamMasterDAO MasterDao=(ScoreParamMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ScoreParamMasterDAO.IDENTITY);
		        logger.info("Implementation class: "+MasterDao.getClass()); 
				//ScoreParamMasterDAO MasterDao = new ScoreParamMasterDAOImpl();	
				
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
				ArrayList list=new ArrayList();
				list= MasterDao.searchScoreMaster(ob,request);
				
				
				request.setAttribute("list",list);
				if(CommonFunction.checkNull(request.getAttribute("flag")).toString().equalsIgnoreCase("yes")){
					request.setAttribute("sms","No");
				}
				
			    return mapping.findForward("scoringList");	
			}
	
	
	public ActionForward addScoreMasterDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		ServletContext context = getServlet().getServletContext();
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
		
		DynaValidatorForm ScoreMasterDynaValidatorForm=(DynaValidatorForm)form;
		scoreMasterVo scoreMasterVo= new scoreMasterVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(scoreMasterVo, ScoreMasterDynaValidatorForm);

		request.setAttribute("save", "save");
	    return mapping.findForward("scoringAdd");
	}
	
	
	
	public ActionForward insertScoreMasterDetails(ActionMapping mapping, ActionForm form,
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
		
		DynaValidatorForm ScoreMasterDynaValidatorForm=(DynaValidatorForm) form;
		logger.info("-----------------------above the code");
		scoreMasterVo ob= new scoreMasterVo();
		logger.info("-----------------------below the vo");
		org.apache.commons.beanutils.BeanUtils.copyProperties(ob,ScoreMasterDynaValidatorForm);
		logger.info("-----------------------below the save bean");
	
		ob.setMakerId(userId);
		ob.setMakerDate(bDate);
		
		ScoreParamMasterDAO MasterDao=(ScoreParamMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ScoreParamMasterDAO.IDENTITY);
        logger.info("Implementation class: "+MasterDao.getClass()); 
		//ScoreParamMasterDAO MasterDao = new ScoreParamMasterDAOImpl();	
		String msg="";
		
		boolean result = MasterDao.insertScoreMaster(ob);
		
		if(result){
			
			request.setAttribute("msg","S");
		}else{
			
			request.setAttribute("msg","E");
		}
		request.setAttribute("save", "save");
	    return mapping.findForward("addNew");
	}
	
	
	
	
	
	public ActionForward openScoreMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
						logger.info("In openScoreMaster");
						
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

						DynaValidatorForm ScoreMasterDynaValidatorForm=(DynaValidatorForm)form;
						scoreMasterVo scoreMasterVo= new scoreMasterVo();
						org.apache.commons.beanutils.BeanUtils.copyProperties(scoreMasterVo, ScoreMasterDynaValidatorForm);
						ScoreParamMasterDAO MasterDao=(ScoreParamMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ScoreParamMasterDAO.IDENTITY);
				        logger.info("Implementation class: "+MasterDao.getClass()); 
						//ScoreParamMasterDAO MasterDao = new ScoreParamMasterDAOImpl();	
												
						String scoringCode=CommonFunction.checkNull(request.getParameter("scoreCode"));
						logger.info(".........."+request.getParameter("scoreCode"));

						ArrayList<scoreMasterVo> list = MasterDao.searchScoreMasterForModify(scoreMasterVo,scoringCode);
						logger.info("In openScoreMaster list"+list.size());
						request.setAttribute("list", list);
						
						logger.info("....status is......"+scoreMasterVo.getScoreStatus());
						request.setAttribute("status",scoreMasterVo.getScoreStatus());
						request.setAttribute("editVal", "editVal");
						
					   return mapping.findForward("editScoreDetails");	
					}
			
			
			public ActionForward updateScoreDetails(ActionMapping mapping, ActionForm form,
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
				
				scoreMasterVo scoreMasterVo=new scoreMasterVo(); 
				DynaValidatorForm ScoreMasterDynaValidatorForm= (DynaValidatorForm)form;
				org.apache.commons.beanutils.BeanUtils.copyProperties(scoreMasterVo, ScoreMasterDynaValidatorForm);	

			
				logger.info("In updateCountryDetails---------");    
				ScoreParamMasterDAO masterDAO=(ScoreParamMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ScoreParamMasterDAO.IDENTITY);
		        logger.info("Implementation class: "+masterDAO.getClass()); 
				//ScoreParamMasterDAO masterDAO = new ScoreParamMasterDAOImpl();

		        boolean status=masterDAO.updateScoreMaster(scoreMasterVo);
		        String sms="";
		        if(status){
					sms="M";
					request.setAttribute("msg",sms);
					request.setAttribute("editVal", "editVal");
				}
				else{
					sms="E";
					request.setAttribute("msg",sms);
					ArrayList<scoreMasterVo> list =new ArrayList<scoreMasterVo>();
					list.add(scoreMasterVo);
					logger.info("In openEditCountry list"+ list.size());
					
					request.setAttribute("editVal", "editVal");
					request.setAttribute("status", scoreMasterVo.getScoreStatus());
					
				}
		      
		       // countryMastervo.setCountryId(request.getParameter("CountryId"));
				logger.info("In updateCountrytDetails---status-----"+scoreMasterVo.getScoreStatus());
				logger.info("in updateCountryDetails ------description-------"+scoreMasterVo.getScoreDesc());
				
				return mapping.findForward("updateScore");
		      
				
			}
			
			
			
			// For Score Card Section
			
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
						DynaValidatorForm ScoreMasterDynaValidatorForm=(DynaValidatorForm) form;
						scoreMasterVo ob= new scoreMasterVo();
						org.apache.commons.beanutils.BeanUtils.copyProperties(ob,ScoreMasterDynaValidatorForm);
						ScoreParamMasterDAO MasterDao=(ScoreParamMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ScoreParamMasterDAO.IDENTITY);
				        logger.info("Implementation class: "+MasterDao.getClass()); 
						//ScoreParamMasterDAO MasterDao = new ScoreParamMasterDAOImpl();	
						
						ArrayList list=new ArrayList();
						list= MasterDao.searchScoreMaster(ob,request);

						
						request.setAttribute("list",list);
						if(CommonFunction.checkNull(request.getAttribute("flag")).toString().equalsIgnoreCase("yes")){
							request.setAttribute("sms","No");
						}
						
					    return mapping.findForward("scoringList");	
					}
			
}
