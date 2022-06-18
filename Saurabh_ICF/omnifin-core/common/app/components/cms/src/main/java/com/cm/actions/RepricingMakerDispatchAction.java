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

import com.cm.dao.RepricingDAO;
import com.cm.vo.RepricingMakerVo;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.vo.CodeDescVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class RepricingMakerDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(RepricingMakerDispatchAction.class.getName());

	public ActionForward saveRepricingMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ......saveRepricingMaker");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";
		String makerDate ="";
		if(userobj!=null){
			makerId=userobj.getUserId();
			makerDate= userobj.getBusinessdate();
		}else{
			logger.info("here in saveRepricingMaker method of RepricingMakerDispatchAction action the session is out----------------");
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

	    DynaValidatorForm RePricingMakerDynaValidatorForm = (DynaValidatorForm)form;
		RepricingMakerVo vo = new RepricingMakerVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,RePricingMakerDynaValidatorForm);
		vo.setMakerId(makerId);
		vo.setMakerDate(makerDate);		
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		RepricingDAO service=(RepricingDAO)DaoImplInstanceFactory.getDaoImplInstance(RepricingDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());

		if(CommonFunction.checkNull(vo.getInterestRateTypeOld()).equalsIgnoreCase("F") || CommonFunction.checkNull(vo.getInterestRateTypeOld()).equalsIgnoreCase(""))
			vo.setBaseRateType("");
		logger.info("Before Save Repricing");
		
		logger.info("Before Save Repricing+++++++"+ vo.getRepricingDate());
		logger.info("Before Save getRePricingReason+++++++"+ vo.getRePricingReason());
		String rePricingDate=CommonFunction.checkNull(request.getParameter("rePricingDate"));
		logger.info("rePricingDate: "+rePricingDate);
		vo.setRepricingdate(rePricingDate);
		reschId=service.saveRepricingData(vo);
		if(!reschId.equalsIgnoreCase("") && reschId.length()<10)
		{
			ArrayList<RepricingMakerVo> rePricingData = service.selectRericingData(vo.getLbxLoanNoHID(),reschId,"P");
			request.setAttribute("rePricingData", rePricingData);
			ArrayList<CodeDescVo> baseRateList = service.getBaseRateList(makerDate);
			request.setAttribute("baseRateList",baseRateList);
			request.setAttribute("reschId",reschId);
			request.setAttribute("message","S");
		}
		else
		{
			request.setAttribute("message","E");
			request.setAttribute("msg",reschId);
		}
		return mapping.findForward("saveRepricingMaker");
	}
	//neeraj
	public ActionForward deleteRepricingMaker(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("Inside deleteRepricingMaker()");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";
		String makerDate ="";
		if(userobj!=null){
			makerId=userobj.getUserId();
			makerDate= userobj.getBusinessdate();
		}else{
			logger.info("here in deleteRepricingMaker method of RepricingMakerDispatchAction action the session is out----------------");
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
		String loan_id="";

	    DynaValidatorForm RePricingMakerDynaValidatorForm = (DynaValidatorForm)form;
		RepricingMakerVo vo = new RepricingMakerVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,RePricingMakerDynaValidatorForm);
		reschId=CommonFunction.checkNull(vo.getReschId());
		loan_id=CommonFunction.checkNull(vo.getLbxLoanNoHID());
		logger.info("reschId   :   "+reschId);
		logger.info("loan_id   :   "+loan_id);
		
		RepricingDAO service=(RepricingDAO)DaoImplInstanceFactory.getDaoImplInstance(RepricingDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		ArrayList<RepricingMakerVo> rePricingData=new ArrayList();
		//ArrayList<RepricingMakerVo> rePricingData = service.selectRericingData(loan_id,reschId,"P");
		request.setAttribute("rePricingData", rePricingData);
		boolean status=false;
		if(loan_id.trim().length()!=0 && reschId.trim().length()!=0)
		status=service.deleteRericingData(reschId,loan_id);
		logger.info("status   :   "+status);
		if(status)
			request.setAttribute("delete","delete");
		else
			request.setAttribute("notDelete","notDelete");
		logger.info("status  :  "+status);
		return mapping.findForward("saveRepricingMaker");
	}
	
	public ActionForward updateRepricingMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("Inside......updateRepricingMaker");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";
		String makerDate ="";
		if(userobj!=null){
			makerId= userobj.getUserId();
			makerDate=userobj.getBusinessdate();
		}else{
			logger.info("here in updateRepricingMaker method of RepricingMakerDispatchAction action the session is out----------------");
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
	    DynaValidatorForm RePricingMakerDynaValidatorForm = (DynaValidatorForm)form;
	    RepricingMakerVo vo = new RepricingMakerVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,RePricingMakerDynaValidatorForm);
		vo.setMakerId(makerId);
		vo.setMakerDate(makerDate);	
		logger.info("reschId from Dispatch Action: "+vo.getReschId());
		if(CommonFunction.checkNull(vo.getInterestRateTypeOld()).equalsIgnoreCase("F"))
			vo.setBaseRateType("");
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		RepricingDAO service=(RepricingDAO)DaoImplInstanceFactory.getDaoImplInstance(RepricingDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		String rePricingDate=CommonFunction.checkNull(request.getParameter("rePricingDate"));
		logger.info("rePricingDate: "+rePricingDate);
		vo.setRepricingdate(rePricingDate);
		status=service.updateRepricingData(vo,type);
		if(status && type.equalsIgnoreCase("P"))
		{
			ArrayList<RepricingMakerVo> rePricingData = service.selectRericingData(vo.getLbxLoanNoHID(),vo.getReschId(),"P");
			request.setAttribute("rePricingData", rePricingData);
			ArrayList<CodeDescVo> baseRateList = service.getBaseRateList(makerDate);
			request.setAttribute("baseRateList",baseRateList);
			request.setAttribute("reschId",vo.getReschId());
			request.setAttribute("message","S");
			retStr="saveRepricingMaker";
		}
		if(status && type.equalsIgnoreCase("F"))
		{
			ArrayList<RepricingMakerVo> rePricingData = service.selectRericingData(vo.getLbxLoanNoHID(),vo.getReschId(),"P");
			request.setAttribute("rePricingData", rePricingData);
			request.setAttribute("message","F");
			request.setAttribute("rePricingMakerSearch","rePricingMakerSearch");
			retStr="saveRepricingMaker";
		}
		else if(status==false)
		{
			ArrayList<RepricingMakerVo> rePricingData = service.selectRericingData(vo.getLbxLoanNoHID(),vo.getReschId(),"P");
			request.setAttribute("rePricingData", rePricingData);
			ArrayList<CodeDescVo> baseRateList = service.getBaseRateList(makerDate);
			request.setAttribute("baseRateList",baseRateList);
			request.setAttribute("reschId",vo.getReschId());
			request.setAttribute("message","newInstlPlan");
			retStr="saveRepricingMaker";
		}
		logger.info("retStr: "+retStr);
		return mapping.findForward(retStr);
	}
}
