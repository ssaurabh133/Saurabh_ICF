package com.cp.actions;

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
import com.cp.dao.DealClosureDAO;
import com.cp.vo.DealCancellationVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ClosureMakerDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ClosureMakerDispatchAction.class.getName());
	
	public ActionForward openNewDealClosure(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				logger.info(" in openNewDealClosure");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
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
				logger.info("Before remove session..........");
				session.removeAttribute("list");
				return mapping.findForward("newClosure");	
			}
	
	public ActionForward saveDealCancellation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				logger.info(" in saveDealCancellation......");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
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
				
				DealCancellationVo vo = new DealCancellationVo();
				if(userobj!=null)
				{
					vo.setMakerId(userobj.getUserId());
					vo.setMakerDate(userobj.getBusinessdate());
				}
				
				DynaValidatorForm LimihanDynaValidatorForm = (DynaValidatorForm)form;
				org.apache.commons.beanutils.BeanUtils.copyProperties(vo, LimihanDynaValidatorForm);
				/* changed by asesh */
				DealClosureDAO leDAO=(DealClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(DealClosureDAO.IDENTITY);
		        logger.info("Implementation class: "+leDAO.getClass()); 	//changed by asesh
				//DealClosureDAO leDAO = new DealClosureDAOImpl();
			
				boolean status = leDAO.insertDealCancellation(vo);
				logger.info("Inside saveLimitMaker Action.....displaying status...."+status);
				if(status){
					request.setAttribute("sms","S");
					request.setAttribute("dealID",vo.getLbxDealNo());
				}
				else{
					
					request.setAttribute("sms","E");
				}
			    logger.info("status"+status+vo.getLbxDealNo());
			    ArrayList list = leDAO.dealCancellationValues(vo.getLbxDealNo(), "P");
				request.setAttribute("list", list);
				return mapping.findForward("saveDealCancellation");
			}
	
	// method for Open Deal Cancellation Value in modify mode
	
	public ActionForward openModifyDeal(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)	throws Exception
		{
			logger.info(" in ClosureMakerDispatchAction........(openModifyDeal)");
			HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
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
			
			DealCancellationVo vo = new DealCancellationVo();
						
			String dealID="";
			dealID=CommonFunction.checkNull(request.getParameter("deaId"));
			/* changed by asesh p */
			
			DealClosureDAO leDAO=(DealClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(DealClosureDAO.IDENTITY);
	        logger.info("Implementation class: "+leDAO.getClass());
	        //DealClosureDAOImpl leDAO = new DealClosureDAOImpl();
	        /* End asesh p */
			ArrayList list = leDAO.dealCancellationValues(dealID, "P");
			request.setAttribute("list", list);
			
			return mapping.findForward("openDeal");
		}
	
	public ActionForward modfyNewDeal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				logger.info(" in ClosureMakerDispatchAction......( modfyNewDeal)");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
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
				
				DealCancellationVo vo = new DealCancellationVo();
				if(userobj!=null)
				{
					vo.setMakerId(userobj.getUserId());
					vo.setMakerDate(userobj.getBusinessdate());
				}
				
				DynaValidatorForm LimihanDynaValidatorForm = (DynaValidatorForm)form;
				org.apache.commons.beanutils.BeanUtils.copyProperties(vo, LimihanDynaValidatorForm);
				/* changed by asesh p*/
				DealClosureDAO leDAO=(DealClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(DealClosureDAO.IDENTITY);
		        logger.info("Implementation class: "+leDAO.getClass());
				//DealClosureDAOImpl leDAO = new DealClosureDAOImpl();
		        /* End asesh p*/
			
				boolean status = leDAO.modifySaveDeal(vo);
				logger.info("Inside modfyNewDeal Action.....displaying status...."+status);
				if(status){
					
					request.setAttribute("sms","S");
					request.setAttribute("dealID",vo.getDealId());
				}
				else{
					
					request.setAttribute("sms","E");
				}
			    logger.info("status"+status+vo.getDealId());
			    ArrayList list = leDAO.dealCancellationValues(vo.getDealId(), "P");
				request.setAttribute("list", list);
				return mapping.findForward("saveDeal");
			}

	public ActionForward modfyForwardDeal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				logger.info(" in ClosureMakerDispatchAction........(modfyForwardDeal)");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
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
				
				DealCancellationVo vo = new DealCancellationVo();
				if(userobj!=null)
				{
					vo.setMakerId(userobj.getUserId());
					vo.setMakerDate(userobj.getBusinessdate());
				}
				
				String dealID="";
				
				DynaValidatorForm LimihanDynaValidatorForm = (DynaValidatorForm)form;
				org.apache.commons.beanutils.BeanUtils.copyProperties(vo, LimihanDynaValidatorForm);

				dealID=CommonFunction.checkNull(vo.getDealId());
		
				logger.info("dealID:----"+dealID);
				
				/* changed by asesh */
				DealClosureDAO leDAO=(DealClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(DealClosureDAO.IDENTITY);
		        logger.info("Implementation class: "+leDAO.getClass());
				//DealClosureDAO leDAO = new DealClosureDAOImpl();
				String sms="";
				
				boolean status = leDAO.modifyDealForward(vo);
				logger.info("Inside modfySaveLimit Action.....displaying status...."+status);
				if(status){
					
					request.setAttribute("sms","M");
					ArrayList list = leDAO.dealCancellationValues(dealID,"F");
					request.setAttribute("list", list);

				}
				else{
					
					request.setAttribute("sms","E");
					ArrayList list = leDAO.dealCancellationValues(dealID,"F");
					request.setAttribute("list", "list");
				}
			    logger.info("status"+status);
				return mapping.findForward("dealForward");
			}

	public ActionForward openModifyDealForAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				logger.info(" in ClosureMakerDispatchAction...........(openModifyDealForAuthor)");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
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
				
				DealCancellationVo vo = new DealCancellationVo();
							
				String dealID="";
				dealID=CommonFunction.checkNull(request.getParameter("deaId"));
				/* changed by asesh */
				DealClosureDAO leDAO=(DealClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(DealClosureDAO.IDENTITY);
		        logger.info("Implementation class: "+leDAO.getClass());
				//DealClosureDAO leDAO = new DealClosureDAOImpl();
		        /* End asesh */
								
				ArrayList list = leDAO.dealCancellationValues(dealID,"F");
				session.setAttribute("list", list);
				
				return mapping.findForward("openDealForAuthor");
			}

	public ActionForward dealMakerForAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				logger.info(" in dealMakerForAuthor()");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				if(userobj==null){
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
				
				return mapping.findForward("dealMakerAuthor");	
			}

	public ActionForward dealAuthorForAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				logger.info(" in dealAuthorForAuthor()");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				
				if(userobj==null){
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
				
				return mapping.findForward("dealAuthor");	
			}

	public ActionForward saveDealAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				logger.info(" in saveDealAuthor.....");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
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
				
				DealCancellationVo vo = new DealCancellationVo();
				if(userobj!=null)
				{
					vo.setMakerId(userobj.getUserId());
					vo.setMakerDate(userobj.getBusinessdate());
				}
				
				String dealID="";
				
				dealID=CommonFunction.checkNull(request.getParameter("dealID"));
				
				DynaValidatorForm LimihanDynaValidatorForm = (DynaValidatorForm)form;
				org.apache.commons.beanutils.BeanUtils.copyProperties(vo, LimihanDynaValidatorForm);
				/* changed by asesh */
				DealClosureDAO leDAO=(DealClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(DealClosureDAO.IDENTITY);
		        logger.info("Implementation class: "+leDAO.getClass());
				//DealClosureDAO leDAO = new DealClosureDAOImpl();
		        /* End asesh */
				String sms="";
				
				String st = leDAO.modifyDealForAuthor(vo,dealID);
				String decesion = vo.getDecison();
				logger.info("Inside saveDealAuthor Action.....displaying status....+ decision.."+st+"  "+decesion);
				
				if(st.equalsIgnoreCase("S"))
				{	
					if(decesion.equalsIgnoreCase("A")){
						logger.info("In side..decesion.equalsIgnoreCase(A)");
						request.setAttribute("message","APP");
					}
					else if(decesion.equalsIgnoreCase("X")){
						logger.info("In side..decesion.equalsIgnoreCase(X)");
						request.setAttribute("message","RJCT");
					}
					else{
						logger.info("In side..decesion.equalsIgnoreCase(P)");
						request.setAttribute("message","SB");
					}
					request.setAttribute("status",st);
				}
				
				else{
					request.setAttribute("message","C");
					request.setAttribute("message","E");
					request.setAttribute("status",st);
				}
			    logger.info("status"+st);
			   
				return mapping.findForward("modfyDealAuthor");
			}
}
