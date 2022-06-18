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
import com.cm.dao.EarlyClosureDAO;
import com.cm.dao.ManualAdviceDAO;
import com.cm.dao.ManualIntCalcDAO;
import com.cm.vo.ClosureSearchVO;
import com.cm.vo.ManualIntCalcVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ManualIntCalcDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ManualIntCalcDispatchAction.class.getName());
	
	public ActionForward openPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside openPage...........");		
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in openPage method of MIC action the session is out----------------");
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
		return mapping.findForward("success");
	}
	
	
	public ActionForward earlyClosure(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside earlyClosure...........");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in earlyClosure method of ClosureSearchDispatchAction action the session is out----------------");
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
		ClosureSearchVO vo = new ClosureSearchVO();
		vo.setCurrentPageLink(currentPageLink);

		String status="P";
		String type="T";
		DynaValidatorForm ClosureSearchDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ClosureSearchDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setStage(status);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		//ArrayList<ClosureSearchVO> searchDataList = service.searchClosureData(vo,status,type);
		
		//EarlyClosureDAO service = new EarlyClosureDAOImpl();
		EarlyClosureDAO service=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		ArrayList<ClosureSearchVO> searchDataList = service.searchClosureData(vo,status,type);
		
		request.setAttribute("earlyClosure", "earlyClosure");
		
		request.setAttribute("searchDataList", "searchDataList");
		request.setAttribute("list", searchDataList);
		//code added by neeraj tripathi
		session.removeAttribute("earlyClosureFlag");
		session.removeAttribute("closureButton");
		session.setAttribute("earlyClosureFlag","Y");
		session.removeAttribute("earlyAuthor");
		session.removeAttribute("saveCompleted");
		session.removeAttribute("waiveAllocated");
		session.removeAttribute("changeWaiveOff");
		session.setAttribute("closureButton","closureButton");
		//tripathi's space end
		return mapping.findForward("searchLoanData");
	}
	
	public ActionForward generateAdvice(ActionMapping mapping, ActionForm form,

			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session =  request.getSession();
		
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";		
		String bDate ="";
		String companyId="";
		String calledFrom="MIC";
		if(userobj!=null){
			userId= userobj.getUserId();
			bDate=userobj.getBusinessdate();
			companyId=userobj.getCompanyId()+"";
		}else{
			logger.info("here in generateAdvice method of ManualIntCalcDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}	
		logger.info("companyId .............. "+companyId);		
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

		DynaValidatorForm MICDynaValidatorForm=(DynaValidatorForm)form;
		ManualIntCalcVO vo = new ManualIntCalcVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, MICDynaValidatorForm);
		
        String loanAc =vo.getLoanAc();
		vo.setMakerId(userId);
		vo.setDate(bDate);
        vo.setCompanyId(companyId);
        vo.setCalledFrom(calledFrom);
		String status="";
		request.setAttribute("loanAc",loanAc );
		//ManualIntCalcDAO dao = new ManualIntCalcDAOImpl();
		ManualIntCalcDAO dao=(ManualIntCalcDAO)DaoImplInstanceFactory.getDaoImplInstance(ManualIntCalcDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		status= dao.generateAdvice(vo);
		String message="";
		if(status.equalsIgnoreCase("S"))
		{
			logger.info(" Data Saved Successfuly.");			
			message="S";			
		}
		else{
			logger.info(" Data Not Saved.");
			message="E";
		}
		request.setAttribute("message",message);
		return mapping.findForward("generateAdviceSuccess");
	}
	
}
