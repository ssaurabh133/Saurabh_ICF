package com.masters.actions;

import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
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
import com.masters.dao.masterParameterDAO;
import com.masters.vo.ParameterScoreDefVO;

public class ParameterScoreDefDispatchAction extends DispatchAction
{
	
	private static final Logger logger = Logger.getLogger(ParameterScoreDefDispatchAction.class.getName());	
	public ActionForward saveParameter(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
			logger.info(" IN ParameterScoreDefDispatchAction saveParameter()....");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");			
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
			
			DynaValidatorForm frm=(DynaValidatorForm) form;		
			ParameterScoreDefVO vo= new ParameterScoreDefVO();
			BeanUtils.copyProperties(vo, frm);
			masterParameterDAO service=(masterParameterDAO)DaoImplInstanceFactory.getDaoImplInstance(masterParameterDAO.IDENTITY);
	        logger.info("Implementation class: "+service.getClass()); 
			//masterParameterDAO service=new MasterParameterDAOIMPL();
			boolean status= service.saveParameterDescription(vo);
			if(status)
			{
				request.setAttribute("save", "S");
			}else
			{
				request.setAttribute("save", "E");	
			}
			
			
			
			return mapping.findForward("success");
	}
	public ActionForward getParameterDef(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info(" IN ParameterScoreDefDispatchAction saveParameter()....");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");			
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
				String scorecardid=request.getParameter("scorecardid");
				String parametercode=request.getParameter("parametercode");
				masterParameterDAO service=(masterParameterDAO)DaoImplInstanceFactory.getDaoImplInstance(masterParameterDAO.IDENTITY);
		        logger.info("Implementation class: "+service.getClass()); 
				//masterParameterDAO service=new MasterParameterDAOIMPL();
				ArrayList list= service.getParameterEdit(scorecardid,parametercode);
			    if(list.size()>0)
			    {
			    	request.setAttribute("Paramdata", list);
			    	return mapping.findForward("paramsuccess");
			    	
			    }
				
				
				return mapping.findForward("success");
		}
	public ActionForward openAddParameter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info(" IN ParameterScoreDefDispatchAction openAddParameter()....");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");		
				DynaValidatorForm frm=(DynaValidatorForm) form;		
				ParameterScoreDefVO vo= new ParameterScoreDefVO();
				BeanUtils.copyProperties(vo, frm);
				if(CommonFunction.checkNull(vo.getFlag()).equalsIgnoreCase(""))
				{
				request.setAttribute("notshowLov", "");	
				}
				
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
				String scorecardid=CommonFunction.checkNull(request.getParameter("scorecardid"));
				String parametercode=CommonFunction.checkNull(request.getParameter("parametercode"));
				
				if(scorecardid.equalsIgnoreCase(""))
				{
					
				}
				else
				{
					masterParameterDAO service=(masterParameterDAO)DaoImplInstanceFactory.getDaoImplInstance(masterParameterDAO.IDENTITY);
			        logger.info("Implementation class: "+service.getClass()); 
					//masterParameterDAO service=new MasterParameterDAOIMPL();
					ArrayList list= service.getParameterDef(scorecardid,parametercode);
				    if(list.size()>0)
				    {
				    	request.setAttribute("Paramdata", list);				    	
				    }
				    ArrayList parameterScore=service.getParameterScoreDef(scorecardid);
				    request.setAttribute("parameterScore", parameterScore);
				}
				
				
				return mapping.findForward("success");
		}
	public ActionForward editParameterList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info(" IN ParameterScoreDefDispatchAction editParameterList()....");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");		
				DynaValidatorForm frm=(DynaValidatorForm) form;		
				ParameterScoreDefVO vo= new ParameterScoreDefVO();
				BeanUtils.copyProperties(vo, frm);
							
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
				masterParameterDAO service=(masterParameterDAO)DaoImplInstanceFactory.getDaoImplInstance(masterParameterDAO.IDENTITY);
		        logger.info("Implementation class: "+service.getClass()); 
				//masterParameterDAO service=new MasterParameterDAOIMPL();
					int a=service.updateParameterList(vo);
					ArrayList list= service.getParameterDef(vo.getScoreCardId(),vo.getParameterCode());
				    if(list.size()>0)
				    {
				    	request.setAttribute("Paramdata", list);				    	
				    }
				   request.setAttribute("scorecardid", vo.getScoreCardId());
				
				
				return mapping.findForward("paramedit");
		}
	
}
