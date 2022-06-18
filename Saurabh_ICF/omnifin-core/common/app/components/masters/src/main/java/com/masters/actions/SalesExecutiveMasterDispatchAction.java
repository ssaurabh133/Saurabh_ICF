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
import com.masters.vo.SalesExecutiveMasterVo;


public class SalesExecutiveMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(SalesExecutiveMasterDispatchAction.class.getName());

	public ActionForward addSalesExecutive(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				ServletContext context = getServlet().getServletContext();
				logger.info("in addSalesExecutive  ");
				HttpSession session = request.getSession();
				//boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
				String strFlag="";	
				if(userobj==null){
					logger.info(" in SalesExecutiveMasterDispatchAction the session is out----------------");
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
			//	request.setAttribute("save","save");
				CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
						
				ArrayList<SalesExecutiveMasterVo> employeeTypeList = bp.getEmployeeTypeList();
				request.setAttribute("typeList", employeeTypeList);
				bp=null;
				return mapping.findForward("openAdd");	
			}



public ActionForward saveSalesExecutive(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {
	
	
	ServletContext context = getServlet().getServletContext();
	HttpSession session=request.getSession(false);
	logger.info("in saveSalesExecutive  ");
	UserObject userobj=new UserObject();
	if(userobj==null){
		logger.info(" in SalesExecutiveMasterDispatchAction the session is out----------------");
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
	
	DynaValidatorForm salesExecutiveMasterDynaValidatorForm= (DynaValidatorForm)form;
	SalesExecutiveMasterVo vo = new SalesExecutiveMasterVo();
	org.apache.commons.beanutils.BeanUtils.copyProperties(vo, salesExecutiveMasterDynaValidatorForm);
	CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
	
	vo.setMakerId(userId);
	vo.setMakerDate(bDate);

	
	String sms="";	
	status=bp.saveSalesExecutive(vo);
	logger.info("problem: " + status);
	ArrayList list = new ArrayList();
	list.add(vo);
	request.setAttribute("list",list);
	logger.info("insert status in action : " + status);
	request.setAttribute("save", "save");
	if((status.equalsIgnoreCase("datasaved"))){
		
		sms="S";
		logger.info("sms : " + sms);
		request.setAttribute("sms",sms);
	}else if((status.equalsIgnoreCase("dataExist"))){
		sms="DE";
		request.setAttribute("sms",sms);
					
	}else{
		sms="E";
		logger.info("sms : " + sms);
		request.setAttribute("sms",sms);
					
	}

	return mapping.findForward("EditSalesExecutive");


}


public ActionForward updateSalesExecutive(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {
	ServletContext context = getServlet().getServletContext();
	logger.info("In updateSalesExecutive.......");
	HttpSession session = request.getSession();
	
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

	SalesExecutiveMasterVo vo=new SalesExecutiveMasterVo(); 
	DynaValidatorForm salesExecutiveMasterDynaValidatorForm= (DynaValidatorForm)form;
	org.apache.commons.beanutils.BeanUtils.copyProperties(vo, salesExecutiveMasterDynaValidatorForm);	
	
	vo.setMakerId(userId);
	vo.setMakerDate(bDate);	
	
	CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
	
    String resultStatus=bp.updateSalesExecutive(vo);
    logger.info("resultStatus: "+resultStatus);
    String sms="";
    if(CommonFunction.checkNull(resultStatus).equalsIgnoreCase("saved")){
    	sms="M";
		request.setAttribute("sms",sms);
		
	}
	else if(CommonFunction.checkNull(resultStatus).equalsIgnoreCase("notsaved")){
		sms="E";
		request.setAttribute("sms",sms);
		ArrayList<SalesExecutiveMasterVo> list =new ArrayList<SalesExecutiveMasterVo>();
		list.add(vo);
		
		
		request.setAttribute("list", list);
		request.setAttribute("status", vo.getRecStatus());
	}else if(CommonFunction.checkNull(resultStatus).equalsIgnoreCase("dataExist")){
		sms="UPDE";
		request.setAttribute("sms",sms);
		
		ArrayList<SalesExecutiveMasterVo> list =new ArrayList<SalesExecutiveMasterVo>();
		list.add(vo);
		
		request.setAttribute("editVal", "editVal");
		request.setAttribute("list", list);
		request.setAttribute("status", vo.getRecStatus());
	   // return mapping.findForward("editBusinessClosure");
	}
    logger.info("update status : " + sms);
    return mapping.findForward("updateSearch");

}
public ActionForward openEditSalesExecutive(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)	throws Exception {
	SalesExecutiveMasterVo vo=new SalesExecutiveMasterVo(); 
		ServletContext context = getServlet().getServletContext();

			logger.info("In openEditSalesExecutive ");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");
			if(userobj==null){
				logger.info(" in salesExecutiveDispatchAction the session is out----------------");
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
			String bpEmpUniqueId=request.getParameter("bpEmpUniqueId");
			logger.info("bpEmpUniqueId: "+bpEmpUniqueId);
			vo.setBpEmpUniqueId(bpEmpUniqueId);
			ArrayList<SalesExecutiveMasterVo> list = bp.salesExecutiveEdit(vo);
			ArrayList<SalesExecutiveMasterVo> employeeTypeList = bp.getEmployeeTypeList();
			request.setAttribute("typeList", employeeTypeList);
			
			//logger.info("In openEditBenchMarkRatio BenchmarkRatioVo list size = "+list.size());
			request.setAttribute("list", list);
			String recStatus="";
			if(list.size()>0)
			{
				SalesExecutiveMasterVo vo1 = (SalesExecutiveMasterVo)list.get(0);
				recStatus=vo1.getRecStatus();
			}
			logger.info("In recStatus "+recStatus);
			
			logger.info("In openEditUser SalesExecutiveMasterVo list"+list.size());
			request.setAttribute("list", list);
			request.setAttribute("status",recStatus);
			request.setAttribute("editVal","editVal");
			
		   return mapping.findForward("EditSalesExecutive");	
		}
}