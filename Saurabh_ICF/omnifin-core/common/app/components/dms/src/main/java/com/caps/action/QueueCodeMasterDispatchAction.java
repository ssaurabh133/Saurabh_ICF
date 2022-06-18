package com.caps.action;

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

import com.business.ejbClient.DebtManagementBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.capsVO.QueueCodeMasterVo;


public class QueueCodeMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(QueueCodeMasterDispatchAction.class.getName());
			
	
	public ActionForward saveQueueCodeDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		logger.info("In QueueCodeMasterDispatchAction:::::::::::saveQueueCodeDetails");
		HttpSession session=request.getSession(false);
		//UserObject userobj=new UserObject();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here saveQueueCodeDetails method of  QueueCodeMasterDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}

		session.removeAttribute("queueid");
		boolean flag=false;
		
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
		DynaValidatorForm QueueCodeMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
		QueueCodeMasterVo queueCodeMasterVo = new QueueCodeMasterVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(queueCodeMasterVo, QueueCodeMasterAddDyanavalidatiorForm);
		
		DebtManagementBussinessSessionBeanRemote dm = (DebtManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(DebtManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String branch=queueCodeMasterVo.getBranch1();
		String product=queueCodeMasterVo.getProduct1();
		String scheme=queueCodeMasterVo.getScheme1();
		logger.info("queueCodeMasterVo.getBranch1()::::::::::::::::::::::::::::"+branch);
		logger.info("queueCodeMasterVo.getProduct1()::::::::::::::::::::::::::::"+product);
		logger.info("queueCodeMasterVo.getScheme1()::::::::::::::::::::::::::::"+scheme);
		if((CommonFunction.checkNull(branch)).equalsIgnoreCase("ALL"))
		{
		queueCodeMasterVo.setLbxBranchId("0");
		}
		
		if((CommonFunction.checkNull(product)).equalsIgnoreCase("ALL") || (CommonFunction.checkNull(scheme)).equalsIgnoreCase("ALL"))
		{
		queueCodeMasterVo.setLbxProductID("0");
		queueCodeMasterVo.setLbxscheme("0");	
		}
	
		queueCodeMasterVo.setMakerId(userId);
		queueCodeMasterVo.setMakerDate(bDate);
				
		String sms="";
	   
		String check=dm.checkQueueCodeMaster(queueCodeMasterVo);
		 logger.info("Inside checkQueueCodeMaster...check..............................."+check);
		if(check.equalsIgnoreCase("Pass")){
			boolean status = dm.insertQueueCodeMaster(queueCodeMasterVo);
		    logger.info("Inside saveQueueCodeDetails...displaying status..............................."+status);
			
		    if(status){
				sms="S";
				ArrayList<QueueCodeMasterVo> custCategoryList = dm.CustCategoryList();
				request.setAttribute("custCategoryList", custCategoryList);
				request.setAttribute("defaultVal",queueCodeMasterVo);
				request.setAttribute("sms",sms);
				request.setAttribute("save", "save");
			}
			else {
				sms="N";
				ArrayList<QueueCodeMasterVo> custCategoryList = dm.CustCategoryList();
				request.setAttribute("custCategoryList", custCategoryList);
				request.setAttribute("defaultVal",queueCodeMasterVo);
				request.setAttribute("sms",sms);
				request.setAttribute("save", "save");
			
			}
			
		}
		else if(check.equalsIgnoreCase("QueueExist")){
			sms="Q";
			ArrayList<QueueCodeMasterVo> custCategoryList = dm.CustCategoryList();
			request.setAttribute("custCategoryList", custCategoryList);
			request.setAttribute("defaultVal",queueCodeMasterVo);
			request.setAttribute("sms",sms);
			request.setAttribute("save", "save");
		}else if(check.equalsIgnoreCase("PriorityExist")){
			sms="P";
			ArrayList<QueueCodeMasterVo> custCategoryList = dm.CustCategoryList();
			request.setAttribute("custCategoryList", custCategoryList);
			request.setAttribute("defaultVal",queueCodeMasterVo);
			request.setAttribute("sms",sms);
			request.setAttribute("save", "save");	
		}
		
			  
	return mapping.findForward("QueueSuccess");
	
	  
	}
	

	
	public ActionForward updateQueueCodeData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In updateQueueCodeData.......");
		 String sms="";
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here updateQueueCodeData method of  QueueCodeMasterDispatchAction action the session is out----------------");
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
		
		QueueCodeMasterVo queueCodeMasterVo=new QueueCodeMasterVo(); 
		DynaValidatorForm QueueCodeMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;	
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(queueCodeMasterVo, QueueCodeMasterAddDyanavalidatiorForm);	
		
		DebtManagementBussinessSessionBeanRemote dm = (DebtManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(DebtManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
		queueCodeMasterVo.setQueueStatus(CommonFunction.checkNull(request.getParameter("queueStatus")));
		
		
		queueCodeMasterVo.setQueueCode(CommonFunction.checkNull(request.getParameter("queueCode")));
		
		String branch=queueCodeMasterVo.getBranch1();
		String product=queueCodeMasterVo.getProduct1();
		String scheme=queueCodeMasterVo.getScheme1();

		if((CommonFunction.checkNull(branch)).equalsIgnoreCase("ALL"))
		{
		queueCodeMasterVo.setLbxBranchId("0");	
		}
		
		if((CommonFunction.checkNull(product)).equalsIgnoreCase("ALL") || (CommonFunction.checkNull(scheme)).equalsIgnoreCase("ALL"))
		{
		queueCodeMasterVo.setLbxProductID("0");
		queueCodeMasterVo.setLbxscheme("0");	
		}
	
		queueCodeMasterVo.setMakerId(userId);
		queueCodeMasterVo.setMakerDate(bDate);
	
		String check=dm.checkPriority(queueCodeMasterVo);
		 logger.info("Inside checkPriority...check..............................."+check);
		if(check.equalsIgnoreCase("Pass")){
		boolean status=dm.updateQueueCodeData(queueCodeMasterVo);
		
		  logger.info("status::::::::::::::::::::"+status);	
	       
	        if(status){
				sms="M";
				request.setAttribute("sms",sms);
				request.setAttribute("editVal", "editVal");
				ArrayList<QueueCodeMasterVo> list =new ArrayList<QueueCodeMasterVo>();
				list.add(queueCodeMasterVo);
				request.setAttribute("list", list);
				session.removeAttribute("queueid");
	
				request.setAttribute("save", "save");
			}
	        else {
				sms="N";
				request.setAttribute("sms",sms);
				request.setAttribute("editVal", "editVal");
				ArrayList<QueueCodeMasterVo> list =new ArrayList<QueueCodeMasterVo>();
				list.add(queueCodeMasterVo);
				request.setAttribute("list", list);
			}
		}else if(check.equalsIgnoreCase("PriorityExist")){
			sms="PE";
			request.setAttribute("sms",sms);
			request.setAttribute("editVal", "editVal");
			ArrayList<QueueCodeMasterVo> list =new ArrayList<QueueCodeMasterVo>();
			list.add(queueCodeMasterVo);
			request.setAttribute("list", list);	
		}
		
        return mapping.findForward("QueueSuccess");
      
	}
	
}
