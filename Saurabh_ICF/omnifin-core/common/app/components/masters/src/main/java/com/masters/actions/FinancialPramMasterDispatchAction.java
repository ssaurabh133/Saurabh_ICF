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
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.FinancialPramMasterVo;

public class FinancialPramMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(FinancialPramMasterDispatchAction.class.getName());
	public ActionForward openAddFinPram(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{ServletContext context = getServlet().getServletContext();
				logger.info(" in openAddFinPram()");
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

				CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
		        ArrayList paramList = bp.getParamDetailDetails("");
				request.setAttribute("paramList", paramList);
				return mapping.findForward("openAdd");	
			}

	
	public ActionForward saveFinPramDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		UserObject userobj1=(UserObject)session.getAttribute("userobject");
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
			DynaValidatorForm FinancialPramMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
			FinancialPramMasterVo vo = new FinancialPramMasterVo();
			
			vo.setMakerId(userId);
			vo.setMakerDate(bDate);
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FinancialPramMasterAddDyanavalidatiorForm);	
			
			CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			
			String sms="";
			boolean result=false;
			result = bp.finCheckExpression(vo);
			if(result){
			String status = CommonFunction.checkNull(bp.insertFinancialMaster(vo));
			logger.info("Inside Financial Master Action.....displaying status...."+status);
			if(status.equalsIgnoreCase("saved")){
				sms="S";
				request.setAttribute("sms",sms);
			}else if(status.equalsIgnoreCase("already")){
				sms="A";
				request.setAttribute("sms",sms);
			}else if(status.equalsIgnoreCase("alreadySeq")){
				sms="AS";
				request.setAttribute("sms",sms);
			}else{
				sms="E";
				request.setAttribute("sms",sms);
			}
			logger.info("status"+status);
			}
			else{
				sms="V";
				request.setAttribute("sms",sms);
				ArrayList list =new ArrayList();
				list.add(vo);
				request.setAttribute("list", list);
			}
			if (vo.getAutoCalculated() != null
					&& vo.getAutoCalculated().equals("on")) {
				vo.setAutoCalculated("Y");
			} 
			if (vo.getPramStatus() != null
					&& vo.getPramStatus().equals("on")) {
				vo.setPramStatus("Active");
			} 
			if (vo.getNegativeAllowed() != null
					&& vo.getNegativeAllowed().equals("on")) {
				vo.setNegativeAllowed("Active");
			} 
			if(CommonFunction.checkNull(vo.getPramType()).equalsIgnoreCase("B")){
				request.setAttribute("pramType", vo.getPramType());
			}
			request.setAttribute("autoCalculated", vo.getAutoCalculated() );
			request.setAttribute("status", vo.getPramStatus());
			request.setAttribute("negativeAllowed", vo.getNegativeAllowed() );
			logger.info("result"+result);
			ArrayList paramList = bp.getParamDetailDetails("");
			request.setAttribute("paramList", paramList);
	
			return mapping.getInputForward();

	}
	


	
		public ActionForward openEditFinPram(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		
				FinancialPramMasterVo vo=new FinancialPramMasterVo(); 
				logger.info("In openEditFinPram");
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
				
				vo.setPramSearchCode(request.getParameter("pramSearchCode"));
				logger.info("In openEditFinPram---status---- by getpara-"+request.getParameter("pramSearchCode"));  
				
				CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
				ArrayList<FinancialPramMasterVo> list = bp.searchFinPramData(vo);
				
				logger.info("In openEditFinPram FinancialPramMasterVo list"+list.size());
				
				request.setAttribute("list", list);
				
				vo=list.get(0);
				
				FinancialPramMasterVo docVo=new FinancialPramMasterVo();
				docVo=list.get(0);
				logger.info("In openEditFinPram---status---- by   getpara by vo-"+docVo.getPramStatus());
				request.setAttribute("status", vo.getPramStatus());
				request.setAttribute("negativeAllowed", vo.getNegativeAllowed() );
				if(CommonFunction.checkNull(vo.getPramType()).equalsIgnoreCase("B")){
				request.setAttribute("pramType", vo.getPramType());
				logger.info("pramType attribute set==========================");
				}
				request.setAttribute("systemDefined", vo.getSystemDefined() );
				request.setAttribute("autoCalculated", vo.getAutoCalculated() );
				request.setAttribute("editVal", "editVal");
				ArrayList paramList = bp.getParamDetailDetails("");
				request.setAttribute("paramList", paramList);
				return mapping.findForward("editFinanPram");	
			}
	
	
		
		public ActionForward updateFinPramDetails(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
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
			UserObject userobj1=(UserObject)session.getAttribute("userobject");
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
				DynaValidatorForm FinancialPramMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
				FinancialPramMasterVo vo = new FinancialPramMasterVo();
				
				vo.setMakerId(userId);
				vo.setMakerDate(bDate);
				org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FinancialPramMasterAddDyanavalidatiorForm);	
				
				CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
				String sms="";
				boolean result=false;
				result = bp.finCheckExpression(vo);
				if(result){
		        logger.info("VO.GETNEGATIVE : "+vo.getNegativeAllowed());
				String status = CommonFunction.checkNull(bp.updateFinPramData(vo));
				logger.info("Inside Financial Master Action.....displaying status...."+status);
				
				if(status.equalsIgnoreCase("saved")){
					sms="M";
					request.setAttribute("sms",sms);
				}else if(status.equalsIgnoreCase("alreadySeq")){
					sms="AS";
					request.setAttribute("sms",sms);
					vo.setPramSearchCode(vo.getPramCode());
					ArrayList<FinancialPramMasterVo> list = bp.searchFinPramData(vo);
					
					logger.info("In openEditFinPram FinancialPramMasterVo list"+list.size());
					
					request.setAttribute("list", list);
					
					vo=list.get(0);
					
					FinancialPramMasterVo docVo=new FinancialPramMasterVo();
					docVo=list.get(0);
					logger.info("In openEditFinPram---status---- by   getpara by vo-"+docVo.getPramStatus());
					request.setAttribute("status", vo.getPramStatus());
					request.setAttribute("negativeAllowed", vo.getNegativeAllowed() );
					if(CommonFunction.checkNull(vo.getPramType()).equalsIgnoreCase("B")){
					request.setAttribute("pramType", vo.getPramType());
					}
					request.setAttribute("systemDefined", vo.getSystemDefined() );
					request.setAttribute("autoCalculated", vo.getAutoCalculated() );
					request.setAttribute("editVal", "editVal");
				}else{
					sms="E";
					request.setAttribute("sms",sms);
				}
				logger.info("status"+status);
				}
				else{
					sms="V";
					request.setAttribute("sms",sms);
					ArrayList list =new ArrayList();
					list.add(vo);
					logger.info("getAutoCalculatedppp"+vo.getAutoCalculated()+"vo.getPramStatus()qqq"+vo.getPramStatus()+"vo.getNegativeAllowed()rrr"+vo.getNegativeAllowed());
					if (vo.getAutoCalculated() != null
							&& vo.getAutoCalculated().equals("on")) {
						vo.setAutoCalculated("Y");
					} 
					if (vo.getPramStatus() != null
							&& vo.getPramStatus().equals("on")) {
						vo.setPramStatus("Active");
					} 
					if (vo.getNegativeAllowed() != null
							&& vo.getNegativeAllowed().equals("on")) {
						vo.setNegativeAllowed("Active");
					} 
					if(CommonFunction.checkNull(vo.getPramType()).equalsIgnoreCase("B")){
						request.setAttribute("pramType", vo.getPramType());
					}
					request.setAttribute("list", list);
					request.setAttribute("editVal", "editVal");
					request.setAttribute("autoCalculated", vo.getAutoCalculated() );
					request.setAttribute("status", vo.getPramStatus());
					request.setAttribute("negativeAllowed", vo.getNegativeAllowed() );
					
				}
				ArrayList paramList = bp.getParamDetailDetails("");
				request.setAttribute("paramList", paramList);
		
				return mapping.getInputForward();

		}

	
}
		

