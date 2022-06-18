package com.cp.actions;

import java.text.DecimalFormat;
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

import com.business.CPClient.LeadProcessingRemote;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.connect.LookUpInstanceFactory;
import com.cp.dao.ACHCapturingDAO;
import com.cp.dao.FieldVerificationDAO;
import com.cp.fundFlowDao.FundFlowAnalysisDAO;
import com.cp.vo.ACHCapturingVo;
import com.cp.vo.CreditProcessingLeadDetailDataVo;
import com.cp.vo.ImdMakerVO;
import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class ACHCapturingDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(ACHCapturingDispatchAction.class.getName());
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");

	public ActionForward searchACHCapturingRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("In ACHCapturingDispatchAction");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in searchACHCapturingRecords method of ACHCapturingDispatchAction action the session is out.");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
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
		session.removeAttribute("message");
		ACHCapturingDAO achCapturingDAO=(ACHCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(ACHCapturingDAO.IDENTITY);
		DynaValidatorForm achCapturingDynaValidatorForm = (DynaValidatorForm) form;
		ACHCapturingVo achCapturingVo = new ACHCapturingVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(achCapturingVo, achCapturingDynaValidatorForm);

		String userId="";
		String bDate ="";
		String branch ="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			bDate=userobj.getBusinessdate();
			branch=userobj.getBranchId();
		}else{
			logger.info(" in saveNewACHRecord method ");
			return mapping.findForward("sessionOut");
		}
		String functionId="";
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		logger.info("functionId: "+functionId);
		achCapturingVo.setFunctionId(functionId);
		achCapturingVo.setMakerId(userId);
		achCapturingVo.setBranchId(branch);
		ArrayList fetchSavedRecordList = achCapturingDAO.fetchSavedRecordList(achCapturingVo);
		
		request.setAttribute("fetchSavedRecordList",fetchSavedRecordList);
		
		return mapping.findForward("searchACHData");
	}
	
	public ActionForward newACHRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in newACHRecord method of ACHCapturingDispatchAction action the session is out.");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
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
		logger.debug("Inside NewACHRecordFunction");
		session.removeAttribute("message");
		session.removeAttribute("achCapturingId");
		session.removeAttribute("achCapturingEditDatarecord");
		session.removeAttribute("ACHCapturingEditData");
		ACHCapturingDAO achCapturingDAO=(ACHCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(ACHCapturingDAO.IDENTITY);
		DynaValidatorForm achCapturingDynaValidatorForm = (DynaValidatorForm) form;
		ArrayList toDebitList = achCapturingDAO.getToDebitList();
		session.setAttribute("toDebitList",toDebitList);
		ArrayList debitTypeList = achCapturingDAO.getDebitTypeList();
		session.setAttribute("debitTypeList",debitTypeList);
		ArrayList FrequencyList = achCapturingDAO.getFrequencyList();
		session.setAttribute("FrequencyList",FrequencyList);
		session.removeAttribute("ACHCapturingEditData");
		return mapping.findForward("newACHData");
		
	}
	 
	
	
	
	public ActionForward saveNewACHRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in saveNewACHRecord method of ACHCapturingDispatchAction action the session is out.");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
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
		session.removeAttribute("message");
		logger.debug("Inside saveNewACHRecordFunction");
		
		String userId="";
		String bDate ="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info(" in saveNewACHRecord method ");
			return mapping.findForward("sessionOut");
		}
		
		ACHCapturingDAO achCapturingDAO=(ACHCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(ACHCapturingDAO.IDENTITY);

		DynaValidatorForm achCapturingDynaValidatorForm = (DynaValidatorForm) form;
		ACHCapturingVo achCapturingVo = new ACHCapturingVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(achCapturingVo, achCapturingDynaValidatorForm);
		
		achCapturingVo.setMakerId(userId);
		achCapturingVo.setMakerDate(bDate);
		String functionId="";
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		logger.info("functionId: "+functionId);
		achCapturingVo.setFunctionId(functionId);
		int generatedAchId = achCapturingDAO.saveNewACHRecordList(achCapturingVo);
		
		ArrayList toDebitList = achCapturingDAO.getToDebitList();
		session.setAttribute("toDebitList",toDebitList);
		ArrayList debitTypeList = achCapturingDAO.getDebitTypeList();
		session.setAttribute("debitTypeList",debitTypeList);
		ArrayList FrequencyList = achCapturingDAO.getFrequencyList();
		session.setAttribute("FrequencyList",FrequencyList);
		
		ArrayList fetchAchRecordList = achCapturingDAO.fetchAchRecordList(String.valueOf(generatedAchId),functionId);
		session.setAttribute("achCapturingEditDatarecord", fetchAchRecordList);
		session.setAttribute("ACHCapturingEditData", "ACHCapturingEditData");
		
		session.setAttribute("achCapturingId",String.valueOf(generatedAchId));
		if (generatedAchId!=0)
		{
			session.setAttribute("message", "S");
		}
		
		return mapping.findForward("saveNewACHRecord");
		
	}
	
	
	public ActionForward fetchACHData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in fetchACHData method of ACHCapturingDispatchAction action the session is out.");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
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
		session.removeAttribute("message");
		logger.debug("Inside fetchACHData");
		return mapping.findForward("fetchACHData");
	}
	public ActionForward achStatusTracking(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in achStatusTracking method of ACHCapturingDispatchAction action the session is out.");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
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
		session.removeAttribute("message");
		String forward="";
		String achCapturingId = CommonFunction.checkNull(session.getAttribute("achCapturingId"));
		logger.info("Inside achStatusTracking : achCapturing Id is "+ achCapturingId);
		if(!CommonFunction.checkNull(achCapturingId).equalsIgnoreCase(""))
		{
		ACHCapturingDAO achCapturingDAO=(ACHCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(ACHCapturingDAO.IDENTITY);
		DynaValidatorForm achCapturingDynaValidatorForm = (DynaValidatorForm) form;
		
		ArrayList alAchTrackingList = achCapturingDAO.fetchSavedACHTrackingRecordList(achCapturingId); 
		request.setAttribute("alAchTrackingList",alAchTrackingList);
		
		ArrayList ACHStatusList = achCapturingDAO.getACHStatusList();
		session.setAttribute("ACHStatusList",ACHStatusList);
		
		ArrayList ACHReceivedStatusList = achCapturingDAO.getACHReceivedStatusList();
		session.setAttribute("ACHReceivedStatusList",ACHReceivedStatusList);
		
		
		
		}
		else{
			
			session.setAttribute("message", "Back");
			
		}
		return mapping.findForward("achStatusTracking");
	}
	
	
	public ActionForward saveNewACHTrackingRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in saveNewACHTrackingRecord method of ACHCapturingDispatchAction action the session is out.");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
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
		session.removeAttribute("message");
		logger.debug("Inside saveNewACHTrackingRecordFunction");
		
		String userId="";
		String bDate ="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info(" in saveNewACHRecord method ");
			return mapping.findForward("sessionOut");
		}
		
		session.removeAttribute("message");
		ACHCapturingDAO achCapturingDAO=(ACHCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(ACHCapturingDAO.IDENTITY);

		DynaValidatorForm achCapturingDynaValidatorForm = (DynaValidatorForm) form;
		ACHCapturingVo achCapturingVo = new ACHCapturingVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(achCapturingVo, achCapturingDynaValidatorForm);
		
		achCapturingVo.setMakerId(userId);
		achCapturingVo.setMakerDate(bDate);
		boolean send=true;
		if(achCapturingVo.getSelACHStatus().equalsIgnoreCase("RV"))
		{
		 send=achCapturingDAO.checkSendtovender(achCapturingVo);
		 logger.info("checkSendtovender...."+send);
		}
		if(send)
		{
		boolean insStatus = achCapturingDAO.saveNewACHTrackingRecordList(achCapturingVo);

		ArrayList alAchTrackingList = achCapturingDAO.fetchSavedACHTrackingRecordList(achCapturingVo.getHidAchCapturingId()); 
		request.setAttribute("alAchTrackingList",alAchTrackingList);
		
		ArrayList ACHStatusList = achCapturingDAO.getACHStatusList();
		session.setAttribute("ACHStatusList",ACHStatusList);
		
		if (insStatus)
		{
			session.setAttribute("message", "S");
		}
		}
		else
		{
			session.setAttribute("message", "B");
		}
		return mapping.findForward("saveNewACHTrackingRecord");
		
	}
	
	public ActionForward openModifyACHCapturing(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
			HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in openModifyACHCapturing method of ACHCapturingDispatchAction action the session is out.");
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
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
			session.removeAttribute("message");
			String achCapId="";
			String Status=CommonFunction.checkNull(request.getParameter("status"));
			if(Status.equalsIgnoreCase("UWA")){
				String dealId=CommonFunction.checkNull(request.getParameter("dealId"));
				String qry="SELECT max(ACH_CAPTURING_ID) FROM CR_ACH_CAPTURING_DTL WHERE LOAN_ID='"+dealId+"'  limit 1 ";
				achCapId=CommonFunction.checkNull(ConnectionDAO.singleReturn(qry));
				session.setAttribute("view", "view");
			}
			else{
				 achCapId = CommonFunction.checkNull(request.getParameter("achCapturingId")).toString();
			}
			logger.debug("Inside openModifyACHCapturing");
			
			ACHCapturingDAO achCapturingDAO=(ACHCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(ACHCapturingDAO.IDENTITY);
			DynaValidatorForm achCapturingDynaValidatorForm = (DynaValidatorForm) form;
			String functionId="";
			if(session.getAttribute("functionId")!=null)
			{
				functionId=session.getAttribute("functionId").toString();
			}
			logger.info("functionId: "+functionId);
			ArrayList toDebitList = achCapturingDAO.getToDebitList();
			session.setAttribute("toDebitList",toDebitList);
			ArrayList debitTypeList = achCapturingDAO.getDebitTypeList();
			session.setAttribute("debitTypeList",debitTypeList);
			ArrayList FrequencyList = achCapturingDAO.getFrequencyList();
			session.setAttribute("FrequencyList",FrequencyList);
		ArrayList fetchAchRecordList = new ArrayList();
		if (!CommonFunction.checkNull(achCapId).equalsIgnoreCase("")) {
			fetchAchRecordList = achCapturingDAO.fetchAchRecordList(achCapId,functionId);
			}
			session.setAttribute("achCapturingId",achCapId);
			session.setAttribute("achCapturingEditDatarecord", fetchAchRecordList);
			session.setAttribute("ACHCapturingEditData", "ACHCapturingEditData");
			return mapping.findForward("openModifyACHCapturing");
		}
	
	public ActionForward udpateACHRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
	
			HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in udpateACHRecord method of ACHCapturingDispatchAction action the session is out.");
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
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
			session.removeAttribute("message");
			ACHCapturingDAO achCapturingDAO=(ACHCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(ACHCapturingDAO.IDENTITY);
			DynaValidatorForm achCapturingDynaValidatorForm = (DynaValidatorForm) form;
			ACHCapturingVo achCapturingVo = new ACHCapturingVo();
			org.apache.commons.beanutils.BeanUtils.copyProperties(achCapturingVo, achCapturingDynaValidatorForm);
			String functionId="";
			logger.info("ID----:-"+achCapturingVo.getHidAchCapturingId());
			if(session.getAttribute("functionId")!=null)
			{
				functionId=session.getAttribute("functionId").toString();
			}
			logger.info("functionId: "+functionId);
			
			boolean udpateStatus = achCapturingDAO.updateACHCapturingData(achCapturingVo);			
		 
			if (udpateStatus)
			{
				session.setAttribute("message", "S");
			}
			
			ArrayList toDebitList = achCapturingDAO.getToDebitList();
			session.setAttribute("toDebitList",toDebitList);
			ArrayList debitTypeList = achCapturingDAO.getDebitTypeList();
			session.setAttribute("debitTypeList",debitTypeList);
			ArrayList FrequencyList = achCapturingDAO.getFrequencyList();
			session.setAttribute("FrequencyList",FrequencyList);
			
			ArrayList fetchAchRecordList = achCapturingDAO.fetchAchRecordList(achCapturingVo.getHidAchCapturingId(),functionId);
			session.setAttribute("achCapturingEditDatarecord", fetchAchRecordList);
			session.setAttribute("ACHCapturingEditData", "ACHCapturingEditData");
			return mapping.findForward("saveNewACHRecord");
	}
	
	
	public ActionForward udpateACHRecordForExist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
	
			HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in udpateACHRecord method of ACHCapturingDispatchAction action the session is out.");
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
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
			session.removeAttribute("message");
			ACHCapturingDAO achCapturingDAO=(ACHCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(ACHCapturingDAO.IDENTITY);
			DynaValidatorForm achCapturingDynaValidatorForm = (DynaValidatorForm) form;
			ACHCapturingVo achCapturingVo = new ACHCapturingVo();
			org.apache.commons.beanutils.BeanUtils.copyProperties(achCapturingVo, achCapturingDynaValidatorForm);
			String functionId="";
			logger.info("ID----:-"+achCapturingVo.getHidAchCapturingId());
			if(session.getAttribute("functionId")!=null)
			{
				functionId=session.getAttribute("functionId").toString();
			}
			logger.info("functionId: "+functionId);
			
			boolean udpateStatus = achCapturingDAO.updateACHCapturingDataforExist(achCapturingVo);			
			logger.info("udpateStatus by rohit: "+udpateStatus);
			if (udpateStatus)
			{
				session.setAttribute("message", "S");
			}
			
			ArrayList toDebitList = achCapturingDAO.getToDebitList();
			session.setAttribute("toDebitList",toDebitList);
			ArrayList debitTypeList = achCapturingDAO.getDebitTypeList();
			session.setAttribute("debitTypeList",debitTypeList);
			ArrayList FrequencyList = achCapturingDAO.getFrequencyList();
			session.setAttribute("FrequencyList",FrequencyList);
			logger.info("ID2:-"+achCapturingVo.getHidAchCapturingId());
			/*ArrayList alAchTrackingList = achCapturingDAO.fetchSavedACHTrackingRecordList(achCapturingVo.getHidAchCapturingId()); 
			request.setAttribute("alAchTrackingList",alAchTrackingList);*/
			
			/*ArrayList fetchAchRecordList = achCapturingDAO.fetchAchRecordList(achCapturingVo.getHidAchCapturingId(),functionId);
			session.setAttribute("achCapturingEditDatarecord", fetchAchRecordList);
			session.setAttribute("ACHCapturingEditData", "ACHCapturingEditData");
			return mapping.findForward("saveNewACHRecord");*/
			ArrayList fetchAchRecordList = achCapturingDAO.fetchAchRecordList(achCapturingVo.getHidAchCapturingId(),functionId);
			session.setAttribute("achCapturingEditDatarecord", fetchAchRecordList);
			session.setAttribute("ACHCapturingEditData", "ACHCapturingEditData");
			return mapping.findForward("saveNewACHRecord");
	}
	
	
	
	
	
	
	
	public ActionForward deleteACHData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in deleteACHData method of ACHCapturingDispatchAction action the session is out.");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
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
		session.removeAttribute("message");
		logger.debug("Inside deleteACHDataFunction");
		
		String userId="";
		String bDate ="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info(" in deleteACHData method ");
			return mapping.findForward("sessionOut");
		}
		
		session.removeAttribute("message");
		ACHCapturingDAO achCapturingDAO=(ACHCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(ACHCapturingDAO.IDENTITY);

		DynaValidatorForm achCapturingDynaValidatorForm = (DynaValidatorForm) form;
		ACHCapturingVo achCapturingVo = new ACHCapturingVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(achCapturingVo, achCapturingDynaValidatorForm);
		
		achCapturingVo.setMakerId(userId);
		achCapturingVo.setMakerDate(bDate);
		
		boolean delStatus = achCapturingDAO.deleteACHDataList(achCapturingVo.getHidAchCapturingId());
		
		ArrayList toDebitList = achCapturingDAO.getToDebitList();
		session.setAttribute("toDebitList",toDebitList);
		ArrayList debitTypeList = achCapturingDAO.getDebitTypeList();
		session.setAttribute("debitTypeList",debitTypeList);
		ArrayList FrequencyList = achCapturingDAO.getFrequencyList();
		session.setAttribute("FrequencyList",FrequencyList);
		
		ArrayList ACHStatusList = achCapturingDAO.getACHStatusList();
		session.setAttribute("ACHStatusList",ACHStatusList);
		
		
		if (delStatus)
		{
			session.setAttribute("message", "D");
		}
		
		return mapping.findForward("deleteACHDataList");
		
	}
}
