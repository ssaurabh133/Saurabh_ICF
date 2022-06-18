package com.cm.actions;


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
import com.cm.dao.SDLiquidationDAO;
import com.cm.vo.LiquidationAuthorVO;
import com.connect.DaoImplInstanceFactory;
import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class SDLiquidationAuthorDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(SDLiquidationAuthorDispatchAction.class.getName());
	public ActionForward openLiquidationValuesAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		LoggerMsg.info("Inside SDLiquidationAuthorDispatchAction........openLiquidationValuesAuthor");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in openLiquidationValuesAuthor method of SDLiquidationAuthorDispatchAction action the session is out----------------");
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
		session.getAttribute("liquidationDataDisabled");
		session.getAttribute("loanId");
		session.getAttribute("sdId");
		session.getAttribute("sdLiquidationId");
		return mapping.findForward("showLiquidationDataAuthor");
	}
	
	public ActionForward openLiquidationAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside SDLiquidationAuthorDispatchAction........openLiquidationAuthor");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in openLiquidationAuthor method of SDLiquidationAuthorDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		
		String loanId = session.getAttribute("loanId").toString();
		SDLiquidationDAO dao=(SDLiquidationDAO)DaoImplInstanceFactory.getDaoImplInstance(SDLiquidationDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		String businessDate="";
		if(userobj != null)
		{
			businessDate = userobj.getBusinessdate();
		}
		String makerDate=dao.getMakerDate(loanId);
		String checkFlag=dao.earlyClosureFlag();
		request.setAttribute("checkFlag",checkFlag);
		request.setAttribute("businessDate1",businessDate);
		request.setAttribute("makerDate",makerDate);

		
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
//		session.getAttribute("loanId");
//		session.getAttribute("sdId");
		return mapping.findForward("openLiquidationAuthor");
	}
	
	public ActionForward saveLiquidationAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside SDLiquidationAuthorDispatchAction........saveLiquidationAuthor");
		
		HttpSession session =  request.getSession();
		
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		int compid =0;
		if(userobj!=null){
			userId = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
				compid=userobj.getCompanyId();
		}else{
			logger.info("here in saveLiquidationAuthor method of SDLiquidationAuthorDispatchAction action the session is out----------------");
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
		session.getAttribute("loanId");
		session.getAttribute("sdId");
		session.getAttribute("sdLiquidationId");
		String status = "";
		DynaValidatorForm SDLiquidationAuthorDynaValidatorForm = (DynaValidatorForm)form;
		LiquidationAuthorVO vo = new LiquidationAuthorVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,SDLiquidationAuthorDynaValidatorForm);

		vo.setAuthorId(userId);
		vo.setAutrhorDate(businessDate);
		vo.setCompanyId(compid);
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		SDLiquidationDAO dao=(SDLiquidationDAO)DaoImplInstanceFactory.getDaoImplInstance(SDLiquidationDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		status = dao.saveLiquidationAuthor(vo);
		if(status.equalsIgnoreCase("S"))
		{
			request.setAttribute("message","S");
		}
		else
		{
			request.setAttribute("message","E");
			request.setAttribute("status",status);
		}
		return mapping.findForward("saveLiquidationAuthor");
	}

}
