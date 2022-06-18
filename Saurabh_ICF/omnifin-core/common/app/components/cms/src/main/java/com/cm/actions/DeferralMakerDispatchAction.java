/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
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


import com.cm.dao.DeferralDAO;
//import com.cm.dao.DeferralDAOImpl;
import com.cm.vo.DeferralMakerVo;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/** 
 * MyEclipse Struts
 * Creation date: 10-01-2011
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class DeferralMakerDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(DeferralMakerDispatchAction.class.getName());
	/*
	 * Generated Methods
	 */

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward saveDeferralMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ......saveDeferralMaker");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";
		String makerDate="";
		if(userobj!=null)
		{
			makerId=userobj.getUserId();
			makerDate=userobj.getBusinessdate();
		}else{
			logger.info("here in saveDeferralMaker method of DeferralMakerDispatchAction action the session is out----------------");
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

		String reschId="";
		ArrayList<DeferralMakerVo> deferralNewFailed = new ArrayList<DeferralMakerVo>();
        DynaValidatorForm DeferralMakerDynaValidatorForm = (DynaValidatorForm)form;
		DeferralMakerVo vo = new DeferralMakerVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,DeferralMakerDynaValidatorForm);
		
		vo.setMakerId(makerId);
		vo.setMakerDate(makerDate);		
		
		//CreditManagementDAO service = new CreditManagementDAOImpl();	
		DeferralDAO service=(DeferralDAO)DaoImplInstanceFactory.getDaoImplInstance(DeferralDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		
		String deferralFeasibility = service.checkDeferralSaveFeasibility(vo);
		if(deferralFeasibility.equalsIgnoreCase("maxDefrMnthAllwd"))
		{
			String reason = vo.getDeferralReason().trim();
			vo.setDeferralReason(reason);
			deferralNewFailed.add(vo);
			request.setAttribute("deferralNewFailed",deferralNewFailed);
			request.setAttribute("message", deferralFeasibility);
		}
		if(deferralFeasibility.equalsIgnoreCase("maxDefrMnthAllwd"))
		{
			String reason = vo.getDeferralReason().trim();
			vo.setDeferralReason(reason);
			deferralNewFailed.add(vo);
			request.setAttribute("deferralNewFailed",deferralNewFailed);
			request.setAttribute("message", deferralFeasibility);
		}
		if(deferralFeasibility.equalsIgnoreCase(""))
		{
			reschId=service.saveDeferralData(vo);
			if(!reschId.equalsIgnoreCase(""))
			{
				ArrayList<DeferralMakerVo> deferralData = service.selectDeferralData(vo.getLbxLoanNoHID(),reschId,"P");
				request.setAttribute("deferralData", deferralData);
				request.setAttribute("reschId",reschId);
				request.setAttribute("message","S");
			}
			else
			{
				request.setAttribute("message","E");
			}
		}
		
		return mapping.findForward("saveDeferralMaker");
	}
	
	public ActionForward updateDeferralMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("Inside......updateDeferralMaker");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId ="";
		String makerDate ="";
		if(userobj!=null){
			makerId = userobj.getUserId();
			makerDate=userobj.getBusinessdate();
		}else{
			logger.info("here in updateDeferralMaker method of DeferralMakerDispatchAction  action the session is out----------------");
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
	    DynaValidatorForm DeferralMakerDynaValidatorForm = (DynaValidatorForm)form;
	    DeferralMakerVo vo = new DeferralMakerVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,DeferralMakerDynaValidatorForm);
		vo.setMakerId(makerId);
		vo.setMakerDate(makerDate);	
		logger.info("reschId from Dispatch Action: "+vo.getReschId());
		logger.info("No. of Months to be Deferred from Dispatch Action: "+vo.getDeferredInstallmentNo());
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		DeferralDAO service=(DeferralDAO)DaoImplInstanceFactory.getDaoImplInstance(DeferralDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		String deferralFeasibility = service.checkDeferralSaveFeasibility(vo);
		if(deferralFeasibility.equalsIgnoreCase("maxDefrMnthAllwd"))
		{
			ArrayList<DeferralMakerVo> deferralData = service.selectDeferralData(vo.getLbxLoanNoHID(),vo.getReschId(),"P");
			request.setAttribute("deferralData", deferralData);
			request.setAttribute("reschId",vo.getReschId());
			request.setAttribute("message", deferralFeasibility);
			retStr="saveDeferralMaker";
		}
		if(deferralFeasibility.equalsIgnoreCase("maxDefrMnthAllwd"))
		{
			ArrayList<DeferralMakerVo> deferralData = service.selectDeferralData(vo.getLbxLoanNoHID(),vo.getReschId(),"P");
			request.setAttribute("deferralData", deferralData);
			request.setAttribute("reschId",vo.getReschId());
			request.setAttribute("message", deferralFeasibility);
			retStr="saveDeferralMaker";
		}
		if(deferralFeasibility.equalsIgnoreCase(""))
		{
			status=service.updateDeferralData(vo,type);
			if(status && type.equalsIgnoreCase("P"))
			{
				ArrayList<DeferralMakerVo> deferralData = service.selectDeferralData(vo.getLbxLoanNoHID(),vo.getReschId(),"P");
				request.setAttribute("deferralData", deferralData);
				request.setAttribute("reschId",vo.getReschId());
				request.setAttribute("message","S");
				retStr="saveDeferralMaker";
			}
			if(status && type.equalsIgnoreCase("F"))
			{
				ArrayList<DeferralMakerVo> deferralData = service.selectDeferralData(vo.getLbxLoanNoHID(),vo.getReschId(),"P");
				request.setAttribute("deferralData", deferralData);
				request.setAttribute("message","F");
				request.setAttribute("deferralMakerSearch","deferralMakerSearch");
				retStr="saveDeferralMaker";
			}
			else if(status==false)
			{
				request.setAttribute("message","E");
				request.setAttribute("deferralMakerSearch","deferralMakerSearch");
				retStr="updateDeferralMaker";
			}
			logger.info("retStr: "+retStr);
		}
		return mapping.findForward(retStr);
	}
	public ActionForward deleteDeferralData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		logger.info("Inside......deleteDeferralData()");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId ="";
		String makerDate ="";
		if(userobj!=null){
			makerId = userobj.getUserId();
			makerDate=userobj.getBusinessdate();
		}else{
			logger.info("here in deleteDeferralData method of DeferralMakerDispatchAction  action the session is out----------------");
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
	    DynaValidatorForm DeferralMakerDynaValidatorForm = (DynaValidatorForm)form;
	    DeferralMakerVo vo = new DeferralMakerVo();
	    DeferralMakerVo vo1 = new DeferralMakerVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,DeferralMakerDynaValidatorForm);
		String reschId=CommonFunction.checkNull(vo.getReschId());
		String loanId=CommonFunction.checkNull(vo.getLbxLoanNoHID());
		logger.info("deleteDeferralData()   reschId  :  "+reschId);
		logger.info("deleteDeferralData()   loanId  :  "+loanId);
		DeferralDAO service=(DeferralDAO)DaoImplInstanceFactory.getDaoImplInstance(DeferralDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		ArrayList<DeferralMakerVo> deferralData=new ArrayList();
		deferralData.add(vo1);
		//ArrayList<DeferralMakerVo> deferralData = service.selectDeferralData(loanId,reschId,"P");
		request.setAttribute("deferralData", deferralData);
		if(loanId.trim().length()!=0 && reschId.trim().length()!=0)
		status=service.deleteDeferralData(loanId,reschId);
		
		if(status)
			request.setAttribute("delete","delete");
		else
			request.setAttribute("notDelete","notDelete");
		
		return mapping.findForward("saveDeferralMaker");
	}
}