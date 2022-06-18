package com.masters.actions;

import java.util.ArrayList;


import java.util.ResourceBundle;

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
import com.cp.vo.CommonDealVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.IrrCalculationMasterVo;
import com.masters.vo.UserMasterVo;

public class IrrCalculationAction extends DispatchAction 
{

	private static final Logger logger = Logger.getLogger(IrrCalculationAction.class.getName());
	
	public ActionForward irrCalculationMethod1(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception
	{
				logger.info(" in irrCalculationMethod1() of IrrCalculationAction.");
				ServletContext context = getServlet().getServletContext();
				HttpSession session = request.getSession();			
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
				String strFlag=null;
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
			    return mapping.findForward("openAdd");	
	}
	public ActionForward irrCalculationMethod(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("In QualityCheckAction (irrCalculationMathod)");
		HttpSession session = request.getSession();
		String userId = "";
		String branch = "";
		session.removeAttribute("strFlagDV");
		UserObject userobj = (UserObject) session.getAttribute("userobject");

		if (userobj != null) {
			userId = userobj.getUserId();
			branch = userobj.getBranchId();
		} else {
			logger.info("here in irrCalculationMathod method of QualityCheckAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		// for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag = "";
		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
		}
		logger.info("strFlag .............. " + strFlag);
		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("openAdd");
		}
		session.setAttribute("userId", userId);// For lov
		DynaValidatorForm irrCalculationAddDyanavalidatiorForm = (DynaValidatorForm) form;
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
		String dateFormat = resource.getString("lbl.dateFormat(dd-mm-yyyy)");
		CommonDealVo vo = new CommonDealVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,irrCalculationAddDyanavalidatiorForm);
		String stage = CommonFunction.checkNull(session.getAttribute("stage"));
		logger.info("stage: " + stage);
		vo.setStage(stage);
		vo.setBranchId(branch);
		if (CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
			vo.setReportingToUserId(userId);
		logger.info("user Id:::::" + vo.getReportingToUserId());
		

		//CreditProcessingDAO creditDAO = new CreditProcessingDAOImpl();
		ArrayList<CommonDealVo> dealdetails = new ArrayList<CommonDealVo>();

		logger.info("current page link .......... "+ request.getParameter("d-49520-p"));

		int currentPageLink = 0;
		if (request.getParameter("d-49520-p") == null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
		{
			currentPageLink = 1;
		}
		else
		{
			currentPageLink = Integer.parseInt(request.getParameter("d-49520-p"));
		}

		 logger.info("current page link ................ "+ request.getParameter("d-49520-p"));
		int funId = 0;
		String qualityCheckStatus="N";
		if(session.getAttribute("functionId")!=null)
		{
			String functionId=CommonFunction.checkNull(session.getAttribute("functionId")).trim();
			funId = Integer.parseInt(functionId);
		}	
	    if(funId == 4000103||qualityCheckStatus.equalsIgnoreCase("")||qualityCheckStatus.equalsIgnoreCase("N"))
	    {
	    	
	    	vo.setQualityCheckStatus("N");
	    	vo.setCurrentPageLink(currentPageLink);
	 		//dealdetails = creditDAO.fetchQualityCheckDetail(vo);
	 		logger.info("In irrCalculationMathod....list: "+ dealdetails.size());
	 		request.setAttribute("list", dealdetails);
	 		session.setAttribute("strFlagQ", "strFlagQ");
	 		session.setAttribute("strFlagDV", "strFlagDV");
	     }
	     else
	     {
	    	
	    	    vo.setQualityCheckStatus("Y");
	    	    vo.setCurrentPageLink(currentPageLink);
	    	   // dealdetails = creditDAO.fetchQualityCheckDetail(vo);
		 		logger.info("In irrCalculationMathod....list: "+ dealdetails.size());
		 		request.setAttribute("list", dealdetails);
		 		  session.removeAttribute("asd");
		 		  session.setAttribute("groupTypeActivated","groupTypeActivated");
				  session.removeAttribute("corporate");
				  session.setAttribute("viewDeviation","viewDeviation");
				  session.removeAttribute("viewDeviationUND");
				  session.removeAttribute("searchLoan");
				
                  session.removeAttribute("underWriterViewData");
                  session.removeAttribute("cmAuthor");		
                  session.removeAttribute("queryResponse");
             	// session.setAttribute("underWriter", "underWriter");
                  session.removeAttribute("underWriter");
				if (request.getParameter("hideId") != null && request.getParameter("operation") != null) {
					session.setAttribute("customerId", request.getParameter("hideId"));
					session.setAttribute("operation", request.getParameter("operation"));
				} else {
					session.removeAttribute("maxIdInCM");
					session.removeAttribute("dealId");
					session.removeAttribute("customerId");
					session.removeAttribute("loanInitDocs");
				
					session.removeAttribute("operation");
					session.removeAttribute("customerInfo");
					
				}
		 		
	     }
		
		
		return mapping.findForward("success");
	}
	
	public ActionForward irrCalculationSave(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception 
	{
		logger.info("In irrCalculationSave() method of IrrCalculationAction.");
		HttpSession session = request.getSession();
		String userId = "";
		String bDate = "";
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		if(userobj != null) 
		{
			userId = userobj.getUserId();
			bDate = userobj.getBusinessdate();
		} 
		else 
		{
			logger.info("here in qualityCheckSaveDeal method of QualityCheckAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag = "";
		if (sessionId != null) 
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
		}
		if (!strFlag.equalsIgnoreCase("")) 
		{
			if (strFlag.equalsIgnoreCase("sameUserSession")) 
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if (strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		
		DynaValidatorForm irrMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
		IrrCalculationMasterVo vo = new IrrCalculationMasterVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, irrMasterAddDyanavalidatiorForm);	
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);		
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		String status = bp.saveIrrCalMaster(vo);
		logger.info("Inside Irr Calculation Master Action.....displaying status...."+status);
		if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("S"))
			request.setAttribute("save", "save");
		else
			request.setAttribute("error",status);
		return mapping.findForward("save");	
	}
	
	public ActionForward irrCalculationSearch(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		logger.info(" in irrCalculationSearch() of  IrrCalculationAction");		
		ServletContext context = getServlet().getServletContext();
		HttpSession session = request.getSession();
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
		String source=CommonFunction.checkNull(request.getParameter("source")).trim();
		IrrCalculationMasterVo vo = new IrrCalculationMasterVo();
		if(!CommonFunction.checkNull(source).trim().equalsIgnoreCase("Y"))
		{
			DynaValidatorForm irrMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, irrMasterAddDyanavalidatiorForm);	
		}		
		Integer currentPageLink = 0;
		if(request.getParameter("d-49520-p")!=null)
			currentPageLink = Integer.parseInt(request.getParameter("d-49520-p"));
		else 
			currentPageLink = 1;			
		vo.setCurrentPageLink(currentPageLink);		
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		ArrayList<IrrCalculationMasterVo> list = bp.searchIrrCalData(vo);		
		request.setAttribute("list", list);		
		return mapping.findForward("success");
	}
	public ActionForward modifyDetails(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info(" in irrCalculationSearch() of  IrrCalculationAction");		
		ServletContext context = getServlet().getServletContext();
		logger.info("In IrrCalculationAction modifyDetails()....");
		HttpSession session = request.getSession();
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
		IrrCalculationMasterVo vo= new IrrCalculationMasterVo();
		CommonMasterBussinessSessionBeanRemote cpm = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		String irrID=CommonFunction.checkNull(request.getParameter("irrID")).trim();
		if(CommonFunction.checkNull(irrID).trim().equalsIgnoreCase(""))
			irrID="0";
		vo.setIrrID(irrID);
		ArrayList<IrrCalculationMasterVo> list = cpm.irrModifyChargeCodeDetailsDao(vo);
		vo=list.get(0);
		request.setAttribute("list", list);	
		request.setAttribute("modify", "modify");
		ArrayList<IrrCalculationMasterVo> chargeDetail = cpm.getIrrChargeDetail(vo);
		request.setAttribute("chargeDetail", chargeDetail);	
		request.setAttribute("irrID", irrID);	
		return mapping.findForward("modifyDetails");	
	}
	public ActionForward irrCalculationUpdate(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception 
	{
		logger.info("In irrCalculationUpdate() method of IrrCalculationAction.");
		HttpSession session = request.getSession();
		String userId = "";
		String bDate = "";
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		if(userobj != null) 
		{
			userId = userobj.getUserId();
			bDate = userobj.getBusinessdate();
		} 
		else 
		{
			logger.info("here in qualityCheckSaveDeal method of QualityCheckAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag = "";
		if (sessionId != null) 
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
		}
		if (!strFlag.equalsIgnoreCase("")) 
		{
			if (strFlag.equalsIgnoreCase("sameUserSession")) 
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if (strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		
		DynaValidatorForm irrMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
		IrrCalculationMasterVo vo = new IrrCalculationMasterVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, irrMasterAddDyanavalidatiorForm);	
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);		
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		String status = bp.updateIrrCalMaster(vo);
		// String status = bp.saveIrrCalMaster(vo);
		logger.info("Inside Irr Calculation Master Action.....displaying status...."+status);
		if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("S"))
		{
			request.setAttribute("update", "update");
			ArrayList<IrrCalculationMasterVo> list = bp.irrModifyChargeCodeDetailsDao(vo);
			vo=list.get(0);
			request.setAttribute("list", list);	
			request.setAttribute("modify", "modify");
			ArrayList<IrrCalculationMasterVo> chargeDetail = bp.getIrrChargeDetail(vo);
			request.setAttribute("chargeDetail", chargeDetail);	
			request.setAttribute("irrID", vo.getIrrID());
			request.setAttribute("modify", "modify");
		}
		else
			request.setAttribute("error",status);
		return mapping.findForward("save");	
	}

}
