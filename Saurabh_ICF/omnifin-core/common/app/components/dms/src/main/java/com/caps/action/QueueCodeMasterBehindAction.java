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
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.capsVO.QueueCodeMasterVo;


public class QueueCodeMasterBehindAction extends DispatchAction{

	private static final Logger logger = Logger.getLogger(QueueCodeMasterBehindAction.class.getName());
	public ActionForward searchQueue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		logger.info("In QueueCodeMasterBehindAction.....request.getContentType()....");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here searchQueue method of QueueCodeMasterBehindAction action the session is out----------------");
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
		QueueCodeMasterVo queueCodeMasterVo = new QueueCodeMasterVo();
        DynaValidatorForm CommonDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(queueCodeMasterVo, CommonDynaValidatorForm);
	
		DebtManagementBussinessSessionBeanRemote dm = (DebtManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(DebtManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		logger.info("	actionCodeMasterVo.getCodeId(); .......... "+queueCodeMasterVo.getQueueSearchCode());
		ArrayList list=new ArrayList();
		
		  logger.info("current page link .......... "+request.getParameter("d-49520-p"));
			
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
			
			queueCodeMasterVo.setCurrentPageLink(currentPageLink);
			list= dm.searchQueueCodeData(queueCodeMasterVo);
			
			if (list.size() > 0) {
				 request.setAttribute("list", list);
					logger.info("list.isEmpty(): "+list.isEmpty());
					request.setAttribute("list",list);
			}else{
				request.setAttribute("sms","No");
			}

			
        return mapping.findForward("QueueCodeSearch");
	}
	public ActionForward openAddQueueCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		
				logger.info(" in openAddQueueCode()");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				if(userobj==null){
					logger.info("here openAddQueueCode method of QueueCodeMasterBehindAction action the session is out----------------");
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
				DynaValidatorForm QueueCodeMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
				QueueCodeMasterVo queueCodeMasterVo = new QueueCodeMasterVo();
				org.apache.commons.beanutils.BeanUtils.copyProperties(queueCodeMasterVo, QueueCodeMasterAddDyanavalidatiorForm);
				
				DebtManagementBussinessSessionBeanRemote dm = (DebtManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(DebtManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
				ArrayList<QueueCodeMasterVo> custCategoryList = dm.CustCategoryList();
			
				request.setAttribute("custCategoryList", custCategoryList);
		
				request.setAttribute("save", "save");
				session.removeAttribute("queueid");
			    return mapping.findForward("OpenAddQueue");	
			}

	
	
	  public ActionForward openEditQueueCode(ActionMapping mapping, ActionForm form,
	
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
				logger.info("In editQueueCodeData");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				if(userobj==null){
					logger.info("here openEditQueueCode method of QueueCodeMasterBehindAction action the session is out----------------");
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
			    String queue=request.getParameter("queueCode");
				logger.info("In editQueueCodeData---ID---- by getpara-"+request.getParameter("queueCode"));  
				
				DebtManagementBussinessSessionBeanRemote dm = (DebtManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(DebtManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
		        ArrayList<QueueCodeMasterVo> list = dm.editQueueCodeData(queue);	
				logger.info("In editQueueCodeData list"+list.size());				
				
				ArrayList<QueueCodeMasterVo> npaStageList = dm.NPAStageList();
				ArrayList<QueueCodeMasterVo> custCategoryList = dm.CustCategoryList();
				ArrayList<QueueCodeMasterVo> productList = dm.ProductList();
				
				request.setAttribute("npaStageList", npaStageList);
				request.setAttribute("custCategoryList", custCategoryList);
				request.setAttribute("productList", productList);
				session.setAttribute("queueid", list.get(0).getQueueCode());
				QueueCodeMasterVo queueVo=new QueueCodeMasterVo();
		
				logger.info("In editQueueCodeData--- by   getpara by vo-"+queueVo.getQueueStatus());
				request.setAttribute("status", list.get(0).getQueueStatus());
				request.setAttribute("editVal", "editVal");
				
				request.setAttribute("list", list);
				
				return mapping.findForward("OpenAddQueue");
			}

}
