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

import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.ChargeCodeMasterVo;



public class ChagreCodeDispatchAction extends DispatchAction{
	
	private static final Logger logger = Logger.getLogger(ChagreCodeDispatchAction.class.getName());
	public ActionForward addChargeCodeDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info(" IN ChagreCodeDispatchAction addChargeCodeDetails()....");
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
				
				request.setAttribute("save", "save");
			    return mapping.findForward("addChargeCodeDetails");	
			}
	
	
	public ActionForward saveChargeCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		ServletContext context = getServlet().getServletContext();
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
		
		boolean flag=false;
		
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
		
		logger.info("In ChagreCodeDispatchAction saveChargeCode().... ");
		DynaValidatorForm ChargeCodeDynaValidatorForm=(DynaValidatorForm)form;
		ChargeCodeMasterVo chargeCodeMasterVo= new ChargeCodeMasterVo();
		chargeCodeMasterVo.setMakerId(userId);
		chargeCodeMasterVo.setMakerDate(bDate);
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(chargeCodeMasterVo, ChargeCodeDynaValidatorForm);
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String msg="";	
		String result = cpm.insertchagreCodeMaster(chargeCodeMasterVo);
		logger.info("for checking data............................................."+result);
		if(result.equalsIgnoreCase("datasaved")){
					msg="S";
			request.setAttribute("msg",msg);
		}
		else if(result.equalsIgnoreCase("datanotsaved")){
			msg="E";
			request.setAttribute("msg",msg);
		}else if(result.equalsIgnoreCase("dataexist")){
			msg="D";
			request.setAttribute("msg",msg);
		}
		logger.info("result"+result);
		logger.info("In ChagreCodeDispatchAction saveChargeCode msg is.... "+msg);

		return mapping.getInputForward();	
	}
	
	public ActionForward modifyDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info("In ChagreCodeDispatchAction modifyDetails()....");
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
				
				DynaValidatorForm ChargeCodeDynaValidatorForm=(DynaValidatorForm)form;
				ChargeCodeMasterVo chargeCodeMasterVo= new ChargeCodeMasterVo();
				org.apache.commons.beanutils.BeanUtils.copyProperties(chargeCodeMasterVo, ChargeCodeDynaValidatorForm);
				
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
				String chargeCode = request.getParameter("chargeCode");
				
				chargeCodeMasterVo.setChargeCode(chargeCode);
				
				ArrayList<ChargeCodeMasterVo> detailList = cpm.modifyChargeCodeDetailsDao(chargeCodeMasterVo);
				chargeCodeMasterVo=detailList.get(0);

				request.setAttribute("list", detailList);	
				request.setAttribute("systemDefinedFlag", chargeCodeMasterVo.getSystemDefinedFlag());
				logger.info("chargeCodeMasterVo.getSystemDefinedFlag()........"+chargeCodeMasterVo.getSystemDefinedFlag());
				request.setAttribute("adviceFlag", chargeCodeMasterVo.getManualAdviceFlag());
				request.setAttribute("waveOffFlag", chargeCodeMasterVo.getWaveOffFlag());
				request.setAttribute("status", chargeCodeMasterVo.getRecStatus());
				request.setAttribute("cgstId", chargeCodeMasterVo.getCgstId());
				request.setAttribute("sgstId", chargeCodeMasterVo.getSgstId());
				request.setAttribute("igstId", chargeCodeMasterVo.getIgstId());
				request.setAttribute("ugstId", chargeCodeMasterVo.getUgstId());
				request.setAttribute("taxdescription1", chargeCodeMasterVo.getTaxdescription1());
				request.setAttribute("taxdescription2", chargeCodeMasterVo.getTaxdescription2());
				request.setAttribute("taxdescription3", chargeCodeMasterVo.getTaxdescription3());
				request.setAttribute("lbxTaxdescription1", chargeCodeMasterVo.getLbxTaxdescription1());
				request.setAttribute("lbxTaxdescription2", chargeCodeMasterVo.getLbxTaxdescription2());
				request.setAttribute("lbxTaxdescription3", chargeCodeMasterVo.getLbxTaxdescription3());
				request.setAttribute("lbxCgstId", chargeCodeMasterVo.getLbxCgstId());
				request.setAttribute("lbxSgstId", chargeCodeMasterVo.getLbxSgstId());
				request.setAttribute("lbxIgstId", chargeCodeMasterVo.getLbxIgstId());
				request.setAttribute("lbxUgstId", chargeCodeMasterVo.getLbxUgstId());
				request.setAttribute("dueReceiptBasis", chargeCodeMasterVo.getDueReceiptBasis());
				request.setAttribute("taxapp", chargeCodeMasterVo.getTaxapp());
				request.setAttribute("taxrate1", chargeCodeMasterVo.getTaxrate1());
				request.setAttribute("taxrate2", chargeCodeMasterVo.getTaxrate2());
				
				logger.info("In due receipt basis.......... "+chargeCodeMasterVo.getDueReceiptBasis());
				
				request.setAttribute("hscscn", chargeCodeMasterVo.getHscscn());
				request.setAttribute("hscscnc", chargeCodeMasterVo.getHscscnc());
				
				
				request.setAttribute("modify", "modify");
			   return mapping.findForward("modifyDetails");	
			}
	
	public ActionForward saveModifyDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info("In ChagreCodeDispatchAction saveModifyDetails().... ");
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
				
				DynaValidatorForm ChargeCodeDynaValidatorForm=(DynaValidatorForm)form;
				ChargeCodeMasterVo chargeCodeMasterVo= new ChargeCodeMasterVo();
				org.apache.commons.beanutils.BeanUtils.copyProperties(chargeCodeMasterVo, ChargeCodeDynaValidatorForm);
				
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
				String msg="";	
				boolean result = cpm.saveModifyChargeCodeDetailsDao(chargeCodeMasterVo);
				
				
				if(result){
					msg="M";
					request.setAttribute("msg",msg);
				}
				else{
					msg="E";
					request.setAttribute("msg",msg);
				}
				logger.info("result"+result);
				logger.info("In ChagreCodeDispatchAction saveChargeCode msg is.... "+msg);
				request.setAttribute("modify", "modify");
			   return mapping.getInputForward();	
			}
	
	
}
