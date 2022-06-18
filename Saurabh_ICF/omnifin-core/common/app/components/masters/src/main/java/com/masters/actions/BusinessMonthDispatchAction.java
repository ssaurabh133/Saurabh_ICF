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
import com.masters.vo.BusinessClosureVo;

public class BusinessMonthDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(BusinessMonthDispatchAction.class.getName());
	public ActionForward addBusinessMonth(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				ServletContext context = getServlet().getServletContext();
				logger.info("in BusinessMonthDispatchAction  ");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
				String strFlag="";	
				if(userobj==null){
					logger.info(" in BusinessMonthDispatchAction the session is out----------------");
					return mapping.findForward("sessionOut");
				}
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
				request.setAttribute("save","save");
				return mapping.findForward("openAdd");	
			}
	
	public ActionForward saveBusinessMonthClosureDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
		HttpSession session=request.getSession(false);
		logger.info("in saveBusinessMonthClosureDetails  ");
		UserObject userobj=new UserObject();
		if(userobj==null){
			logger.info(" in BusinessMonthDispatchAction the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		String status="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		
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
		
		DynaValidatorForm BusinessMonthClosureDynaValidatorForm= (DynaValidatorForm)form;
		BusinessClosureVo vo = new BusinessClosureVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, BusinessMonthClosureDynaValidatorForm);
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		logger.info("getBusinessMonthss----"+vo.getBusinessMonthss());
		logger.info("getBusinessYear----"+vo.getBusinessYear());
		
		String sms="";	
		status=bp.saveBusinessMonthClosureDetails(vo);
		ArrayList list = new ArrayList();
		list.add(vo);
		request.setAttribute("List",list);
		logger.info("insert status in action : " + status);
		request.setAttribute("save", "save");
		if((status.equalsIgnoreCase("datasaved"))){
			sms="S";
			request.setAttribute("sms",sms);
		}else if((status.equalsIgnoreCase("dataExist"))){
			sms="DE";
			request.setAttribute("sms",sms);
						
		}else{
			sms="E";
			request.setAttribute("sms",sms);
						
		}

		return mapping.getInputForward();
	


	}
	
	public ActionForward openEditBusinessClosure(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
			BusinessClosureVo vo=new BusinessClosureVo(); 
			ServletContext context = getServlet().getServletContext();
			String businessMonth=request.getParameter("businessMonth");
			String businessYear=request.getParameter("businessYear");
			vo.setBusinessMonth(businessMonth);
			vo.setBusinessYear(businessYear);
			request.setAttribute("businessMonth",businessMonth);
				logger.info("In openEditBusinessClosure ");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
				if(userobj==null){
					logger.info(" in BusinessMonthDispatchAction the session is out----------------");
					return mapping.findForward("sessionOut");
				}
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
				
				ArrayList<BusinessClosureVo> list = bp.searchBusinessMonthClosureEdit(vo);
				logger.info("In openEditBenchMarkRatio BenchmarkRatioVo list size = "+list.size());
				request.setAttribute("list", list);
				String recStatus="";
				if(list.size()>0)
				{
					BusinessClosureVo vo1 = (BusinessClosureVo)list.get(0);
					recStatus=vo1.getRecStatus();
				}
				logger.info("In recStatus "+recStatus);
				
				logger.info("In openEditUser BusinessClosureVo list"+list.size());
				request.setAttribute("list", list);
				request.setAttribute("status",recStatus);
				request.setAttribute("editVal", "editVal");
			   return mapping.findForward("editBusinessClosure");	
			}
	
	
	public ActionForward updateBusinessMonthClosure(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("In updateBusinessMonthClosure.......");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
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
		String businessMonth=request.getParameter("businessMonth");
		BusinessClosureVo vo=new BusinessClosureVo(); 
		DynaValidatorForm BusinessMonthClosureDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, BusinessMonthClosureDynaValidatorForm);	
		vo.setBusinessMonth(businessMonth);
		
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);	
		
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
        String resultStatus=bp.updateBusinessMonthClosureData(vo);
        String sms="";
        if(resultStatus.equalsIgnoreCase("saved")){
        	sms="M";
			request.setAttribute("sms",sms);
			request.setAttribute("editValUpdate", "editValUpdate");
		}
		else if(resultStatus.equalsIgnoreCase("notsaved")){
			sms="E";
			request.setAttribute("sms",sms);
			ArrayList<BusinessClosureVo> list =new ArrayList<BusinessClosureVo>();
			list.add(vo);
			logger.info("In updateSubDealerDetails list"+ list.size());
			
			request.setAttribute("editValUpdate", "editValUpdate");
			request.setAttribute("list", list);
			request.setAttribute("status", vo.getRecStatus());
		}else if(resultStatus.equalsIgnoreCase("dataExist")){
			sms="UPDE";
			request.setAttribute("sms",sms);
			
			ArrayList<BusinessClosureVo> list =new ArrayList<BusinessClosureVo>();
			list.add(vo);
			logger.info("In updateBenchMarkRatio list"+ list.size());
			logger.info("list valusesssss"+list);
			logger.info("businessMonth"+businessMonth);
			request.setAttribute("businessMonth",businessMonth);
			request.setAttribute("editVal", "editVal");
			request.setAttribute("list", list);
			request.setAttribute("status", vo.getRecStatus());
		    return mapping.findForward("editBusinessClosure");
		}
        logger.info("update status : " + sms);
        return mapping.findForward("updateSearch");
      
		
	}
	public ActionForward getStartDate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("IngetStartDate.......");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info(" in behindMethod the session is out----------------");
			return mapping.findForward("sessionOut");
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
		logger.info("in getStartDate ..............................action ");
		String businessMonthss=request.getParameter("businessMonthss");
		String businessYear=request.getParameter("businessYear");
		
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
        String resultStatus=bp.getStartDate(businessMonthss,businessYear); 
        request.setAttribute("resultStatus", resultStatus);
        logger.info("resultStatus ------------------------------------------ "+resultStatus);
        return mapping.findForward("ajaxBusinessMonth");
      
		
	}
	


}



