package com.cm.actions;

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
import com.cm.dao.SDLiquidationDAO;
import com.cm.vo.LiquidationMakerVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class SDLiquidationMakerDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(SDLiquidationMakerDispatchAction.class.getName());
	public ActionForward saveLiquidationData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("Inside SDLiquidationMakerDispatchAction......saveLiquidationData");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";
		String makerDate ="";
		if(userobj!=null){
			makerId= userobj.getUserId();
			makerDate=userobj.getBusinessdate();
		}else{
			logger.info("here in saveLiquidationData method of SDLiquidationMakerDispatchAction action the session is out----------------");
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

		String sdLiquidationId="";


		SDLiquidationDAO service=(SDLiquidationDAO)DaoImplInstanceFactory.getDaoImplInstance(SDLiquidationDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		String checkFlag=service.earlyClosureFlag();
		request.setAttribute("checkFlag",checkFlag);
		request.setAttribute("businessDate1",makerDate);
		request.setAttribute("makerDate",makerDate);

		DynaValidatorForm SDLiquidationMakerDynaValidatorForm = (DynaValidatorForm)form;
		LiquidationMakerVO vo = new LiquidationMakerVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,SDLiquidationMakerDynaValidatorForm);
		vo.setMakerId(makerId);
		vo.setMakerDate(makerDate);		
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		sdLiquidationId=service.saveLiquidationData(vo);
		if(!sdLiquidationId.equalsIgnoreCase(""))
		{
			ArrayList<LiquidationMakerVO> liquidationData = service.selectLiquidationData(vo.getLbxLoanNoHID(),vo.getSdNo(),sdLiquidationId,"P");
			//neeraj
			LiquidationMakerVO lvo=new LiquidationMakerVO();
			lvo=liquidationData.get(0);
			String liqFlag ="";
			liqFlag =lvo.getLiquidationFlag();
			logger.info("vo.getLiquidationFlag()  :  "+lvo.getLiquidationFlag());
			
			if(liqFlag.equalsIgnoreCase("P"))
				request.setAttribute("default",lvo.getSdInterestAccrued());
			if(liqFlag.equalsIgnoreCase("F"))
				request.setAttribute("default",lvo.getSdFinalInterest());
			
			request.setAttribute("liquidationData", liquidationData);
			request.setAttribute("sdLiquidationId",sdLiquidationId);
			request.setAttribute("message","S");
			request.setAttribute("liquidationData",liquidationData);
		}
		else
		{
			request.setAttribute("message","E");
			request.setAttribute("sdLiquidationMakerSearch","sdLiquidationMakerSearch");
		}
		return mapping.findForward("saveLiquidationData");
	}
	
	public ActionForward updateLiquidationData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("Inside SDLiquidationMakerDispatchAction......updateLiquidationData");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";
		String makerDate ="";
		if(userobj!=null){
			makerId= userobj.getUserId();
			makerDate=userobj.getBusinessdate();
		}else{
			logger.info("here in updateLiquidationData method of SDLiquidationMakerDispatchAction action the session is out----------------");
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
		String type=CommonFunction.checkNull(request.getParameter("type"));
		logger.info("Type: "+type);

	   
	    String retStr="";
	    DynaValidatorForm SDLiquidationMakerDynaValidatorForm = (DynaValidatorForm)form;
		LiquidationMakerVO vo = new LiquidationMakerVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,SDLiquidationMakerDynaValidatorForm);
		SDLiquidationDAO service=(SDLiquidationDAO)DaoImplInstanceFactory.getDaoImplInstance(SDLiquidationDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		String checkFlag=service.earlyClosureFlag();
		request.setAttribute("checkFlag",checkFlag);
		request.setAttribute("businessDate1",makerDate);
		request.setAttribute("makerDate",makerDate);
		vo.setMakerId(makerId);
		vo.setMakerDate(makerDate);	
		logger.info("sdLiquidationId from Dispatch Action: "+vo.getSdLiquidationId());
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		status=service.updateLiquidationData(vo,type);
		if(status && type.equalsIgnoreCase("P"))
		{
			//neeraj
			ArrayList<LiquidationMakerVO> liquidationData = service.selectLiquidationData(vo.getLbxLoanNoHID(),vo.getSdNo(),vo.getSdLiquidationId(),"P");
			
			LiquidationMakerVO lvo=new LiquidationMakerVO();
			lvo=liquidationData.get(0);
			String liqFlag ="";
			liqFlag =lvo.getLiquidationFlag();
			logger.info("vo.getLiquidationFlag()  :  "+lvo.getLiquidationFlag());
			
			if(liqFlag.equalsIgnoreCase("P"))
				request.setAttribute("default",lvo.getSdInterestAccrued());
			if(liqFlag.equalsIgnoreCase("F"))
				request.setAttribute("default",lvo.getSdFinalInterest());
			
			request.setAttribute("liquidationData", liquidationData);
			request.setAttribute("sdLiquidationId",vo.getSdLiquidationId());
			request.setAttribute("message","S");
			retStr="saveLiquidationData";
		}
		if(status && type.equalsIgnoreCase("F"))
		{
			request.setAttribute("message","F");
			request.setAttribute("sdLiquidationMakerSearch","sdLiquidationMakerSearch");
			retStr="updateLiquidationData";
		}
		else if(status==false)
		{
			request.setAttribute("message","E");
			request.setAttribute("sdLiquidationMakerSearch","sdLiquidationMakerSearch");
			retStr="updateLiquidationData";
		}
		logger.info("retStr: "+retStr);
		return mapping.findForward(retStr);
	}

	public ActionForward deleteLiquidationData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("Inside SDLiquidationMakerDispatchAction......deleteLiquidationData");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";
		String makerDate ="";
		if(userobj!=null){
			makerId= userobj.getUserId();
			makerDate=userobj.getBusinessdate();
		}else{
			logger.info("here in deleteLiquidationData method of SDLiquidationMakerDispatchAction action the session is out----------------");
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
		String retStr="";
		DynaValidatorForm SDLiquidationMakerDynaValidatorForm = (DynaValidatorForm)form;
		LiquidationMakerVO vo = new LiquidationMakerVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,SDLiquidationMakerDynaValidatorForm);
		SDLiquidationDAO service=(SDLiquidationDAO)DaoImplInstanceFactory.getDaoImplInstance(SDLiquidationDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());

		vo.setMakerId(makerId);
		vo.setMakerDate(makerDate);	

		status=service.deleteLiquidationData(vo);
		
		if(status)
		{
			session.removeAttribute("liquidationDataDisabled");
			session.removeAttribute("loanId");
			session.removeAttribute("sdId");
			session.removeAttribute("sdLiquidationId");
			request.setAttribute("sdLiquidationNew","sdLiquidationNew");
			request.setAttribute("message","D");
			retStr="deleteLiquidation";
		}
		else if(!status)
		{
			ArrayList<LiquidationMakerVO> liquidationData = service.selectLiquidationData(vo.getLbxLoanNoHID(),vo.getSdNo(),vo.getSdLiquidationId(),"P");
			request.setAttribute("liquidationData", liquidationData);
			request.setAttribute("sdLiquidationId",vo.getSdLiquidationId());
			request.setAttribute("message","E");
			retStr="saveLiquidationData";
		}
		
		return mapping.findForward(retStr);
	}
}
