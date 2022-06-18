package com.cm.actions;

import java.util.ArrayList;
import java.util.Iterator;

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

import com.cm.dao.FileTrackingDAO;
import com.cm.vo.FileTrackingVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class FileTrackingDispatchAction extends DispatchAction {
	private static final Logger logger = Logger
			.getLogger(FileTrackingDispatchAction.class.getName());

	public ActionForward searchFileTrackingDataForView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" in searchFileTrackingDataForView......");
		
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		// for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag = "";
		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId
					.toString(), "", request);
		}

		logger.info("strFlag .............. " + strFlag);
		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}

		FileTrackingVO vo = new FileTrackingVO();
		DynaValidatorForm FileTrackingDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,
				FileTrackingDynaValidatorForm);
		if (userobj != null) {
			vo.setMakerId(userobj.getUserId());
			vo.setMakerDate(userobj.getBusinessdate());
		}
		FileTrackingDAO fileTrackDAO = (FileTrackingDAO) DaoImplInstanceFactory
				.getDaoImplInstance(FileTrackingDAO.IDENTITY);
		logger.info("Implementation class: " + fileTrackDAO.getClass()
				+ ".........Tracking_id......." + vo.getTrackingId());
		
		logger.info("LbxLoanNoHID................."+request.getParameter("lbxLoanNoHIDSearch"));
		
			vo.setLbxLoanNoHIDSearch(CommonFunction.checkNull(request.getParameter("lbxLoanNoHIDSearch")));
			ArrayList list = fileTrackDAO.getFileTrackingDataForView(vo);
			request.setAttribute("list", list);
			request.setAttribute("editVal", "editVal");
			request.setAttribute("viewMode", "viewMode");
			return mapping.findForward("openBranchFileTracking");
	}

	public ActionForward openNewBranchFileTracking(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		logger.info("Inside openNewBranchFileTracking...........");

		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		if (userobj == null) {
			logger
					.info("here in openNewBranchFileTracking method of FileTrackingDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		// for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag = "";
		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId
					.toString(), "", request);
		}

		logger.info("strFlag .............. " + strFlag);
		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		session.removeAttribute("loanId");
		return mapping.findForward("openNewBranchFileTracking");
	}

	public ActionForward saveBranchFileTrackingRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		logger.info(" In saveBranchFileTrackingRecord......");
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		// for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag = "";
		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId
					.toString(), "", request);
		}

		logger.info("strFlag .............. " + strFlag);
		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}

		FileTrackingVO vo = new FileTrackingVO();
		DynaValidatorForm FileTrackingDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,
				FileTrackingDynaValidatorForm);
		/* changed by asesh */
		if (userobj != null) {
			vo.setMakerId(userobj.getUserId());
			vo.setMakerDate(userobj.getBusinessdate());
		}
		FileTrackingDAO fileTrackDAO = (FileTrackingDAO) DaoImplInstanceFactory
				.getDaoImplInstance(FileTrackingDAO.IDENTITY);
		logger.info("Implementation class: " + fileTrackDAO.getClass()); // changed

		boolean status = fileTrackDAO.insertFileTracking(vo);
		logger.info("Inside saveFileTrackingRecord Action.....displaying status...."
						+ status);
		if (status)		
			request.setAttribute("sms", "S");
		 else 
			request.setAttribute("sms", "E");
		
		ArrayList<FileTrackingVO> list = new ArrayList<FileTrackingVO>();
		list.add(vo);
		request.setAttribute("list", list);
		request.setAttribute("editVal", "editVal");
        form.reset(mapping, request);
		return mapping.findForward("openBranchFileTracking");
	}

	public ActionForward updateBranchFileTrackingRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" in updateBranchFileTrackingRecord......");
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		// for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag = "";
		String loanId = "";
		loanId=(String) request.getAttribute("loanId");
		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId
					.toString(), "", request);
		}

		logger.info("strFlag .............. " + strFlag);
		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}

		FileTrackingVO vo = new FileTrackingVO();
		DynaValidatorForm FileTrackingDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,
				FileTrackingDynaValidatorForm);
		/* changed by asesh */
		if (userobj != null) {
			vo.setMakerId(userobj.getUserId());
			vo.setMakerDate(userobj.getBusinessdate());
		}
		FileTrackingDAO fileTrackDAO = (FileTrackingDAO) DaoImplInstanceFactory
				.getDaoImplInstance(FileTrackingDAO.IDENTITY);
		logger.info("Implementation class: " + fileTrackDAO.getClass()); // changed
		
		
		boolean status = fileTrackDAO.updateFileTracking(vo);
		logger.info("Inside updateBranchFileTrackingRecord Action.....displaying status...."
						+ status);
		
		if (status) {
			ArrayList<FileTrackingVO> list = new ArrayList<FileTrackingVO>();
			list.add(vo);
			request.setAttribute("list", list);
			request.setAttribute("editVal", "editVal");
			request.setAttribute("sms", "S");
		} else {

			request.setAttribute("sms", "E");
		}
		String stageStep=request.getParameter("stageStep");
		String forwardTo="openBranchFileTracking";
		if(CommonFunction.checkNull(stageStep).equalsIgnoreCase("operation"))
		{
			forwardTo="openOperationFileTracking";
		}
		else if(CommonFunction.checkNull(stageStep).equalsIgnoreCase("store"))
		{
			forwardTo="openStoreFileTracking";
		}
		return mapping.findForward(forwardTo);
	}

	public ActionForward getFileTrackingData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" In getFileTrackingData......");
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		// for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag = "";
		
		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId
					.toString(), "", request);
		}

		logger.info("strFlag .............. " + strFlag);
		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}

		FileTrackingVO vo = new FileTrackingVO();
		DynaValidatorForm FileTrackingDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,
				FileTrackingDynaValidatorForm);
		if (userobj != null) {
			vo.setMakerId(userobj.getUserId());
			vo.setMakerDate(userobj.getBusinessdate());
		}
		
		FileTrackingDAO fileTrackDAO = (FileTrackingDAO) DaoImplInstanceFactory.getDaoImplInstance(FileTrackingDAO.IDENTITY);
		logger.info("Implementation class: " + fileTrackDAO.getClass()
				+ ".........Tracking_id......." + vo.getTrackingId());
		
		vo.setLbxLoanNoHID(request.getParameter("LbxLoanNoHID"));
		vo.setTrackingId(request.getParameter("trackingId"));
		String stageStep=request.getParameter("stageStep");
		String viewMode=request.getParameter("viewMode");
		String forwardTo="openBranchFileTracking";
		vo.setTrackStatus("SO");
		if(CommonFunction.checkNull(stageStep).equalsIgnoreCase("operation"))
		{
			vo.setTrackStatus("SS,RS");
			forwardTo="openOperationFileTracking";
		}
		else if(CommonFunction.checkNull(stageStep).equalsIgnoreCase("store"))
		{
			vo.setTrackStatus("SS,RS");
			forwardTo="openStoreFileTracking";
		}
		String reason =null;
		String trackingId=request.getParameter("trackingId");
		vo.setStageStep(stageStep);
		ArrayList list = fileTrackDAO.getFileTrackingData(vo);
		ArrayList<FileTrackingVO> reasonList = fileTrackDAO.getresultForOPS(trackingId);
		Iterator<FileTrackingVO> it= reasonList.iterator();
		
		while(it.hasNext())
		{
			FileTrackingVO  vo1 = (FileTrackingVO) it.next();
			reason=reason+vo1.getLbxReasonId()+"|";
			
		}
		if(!CommonFunction.checkNull(reason).equalsIgnoreCase(""))
			reason = CommonFunction.checkNull(reason).substring(0,reason.length()-1);
			request.setAttribute("reason", reason);
			request.setAttribute("reasonList", reasonList);
		if (list.size() > 0) {
			request.setAttribute("list", list);
			request.setAttribute("editVal", "editVal");
			if(CommonFunction.checkNull(stageStep).equalsIgnoreCase("store") && CommonFunction.checkNull(viewMode).equalsIgnoreCase("RS"))
			{
				request.setAttribute("viewMode", "viewMode");
			}
			else if(CommonFunction.checkNull(stageStep).equalsIgnoreCase("branch") && CommonFunction.checkNull(viewMode).equalsIgnoreCase("SO"))
			{
				request.setAttribute("viewMode", "viewMode");
			}
		}
		request.setAttribute("stageStep", stageStep);
		return mapping.findForward(forwardTo);
	}
	
	public ActionForward updateOperationFileTrackingRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" in updateOperationFileTrackingRecord......");
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		// for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag = "";
		String reason="";
		String trackingId=request.getParameter("trackingId");
		logger.info("trackingId....."+trackingId);
		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId
					.toString(), "", request);
		}

		logger.info("strFlag .............. " + strFlag);
		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}

		FileTrackingVO vo = new FileTrackingVO();
		DynaValidatorForm FileTrackingDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,
				FileTrackingDynaValidatorForm);
		/* changed by asesh */
		if (userobj != null) {
			vo.setMakerId(userobj.getUserId());
			vo.setMakerDate(userobj.getBusinessdate());
		}
		FileTrackingDAO fileTrackDAO = (FileTrackingDAO) DaoImplInstanceFactory
				.getDaoImplInstance(FileTrackingDAO.IDENTITY);
		
		logger.info("Implementation class: " + fileTrackDAO.getClass()); // changed
        String checkStatusQuery="SELECT COUNT(1) FROM CR_FILE_TRACK_STATUS_DTL WHERE TRACKING_ID='"+CommonFunction.checkNull(vo.getTrackingId())+"' AND FILE_TRACKING_STATUS='"+CommonFunction.checkNull(vo.getFileTrackStatus())+"'";
        logger.info("check Status Query CR_FILE_TRACK_STATUS_DTL : "+checkStatusQuery);
        String checkStatusCount=ConnectionDAO.singleReturn(checkStatusQuery);
        if(CommonFunction.checkNull(checkStatusCount).equalsIgnoreCase("0"))
        {
        	boolean checkSeqStatus=fileTrackDAO.getFileTrackingSequenceStatus(vo.getTrackingId(),vo.getFileTrackStatus());
        	
        	if(checkSeqStatus)
        	{
        		
        		boolean status = fileTrackDAO.updateFileTracking(vo);
        		logger.info("Inside updateOperationFileTrackingRecord Action.....displaying status...."
        						+ status);
        	
        		if (status) {
        			
        			request.setAttribute("sms", "S");
        		} else {

        			request.setAttribute("sms", "E");
        		}
        	}
        	else
        	{
        		request.setAttribute("sms", "NOTINSEQ");
        	}
        	
        }
        else
        {
        	request.setAttribute("sms", "STATUSDUBLICATE");
        }
        
    	ArrayList<FileTrackingVO> reasonList = fileTrackDAO.getresultForOPS(trackingId);
		Iterator<FileTrackingVO> it= reasonList.iterator();
		
		while(it.hasNext())
		{
			FileTrackingVO  vo1 = (FileTrackingVO) it.next();
			reason=reason+vo1.getLbxReasonId()+"|";
			
		}
		if(!CommonFunction.checkNull(reason).equalsIgnoreCase(""))
			reason = CommonFunction.checkNull(reason).substring(0,reason.length()-1);
		request.setAttribute("reason", reason);
		request.setAttribute("reasonList", reasonList);
		
        ArrayList<FileTrackingVO> list = new ArrayList<FileTrackingVO>();
		list.add(vo);
		request.setAttribute("list", list);
		request.setAttribute("editVal", "editVal");
		String stageStep=request.getParameter("stageStep");
		request.setAttribute("stageStep", stageStep);
		String forwardTo="openBranchFileTracking";
		if(CommonFunction.checkNull(stageStep).equalsIgnoreCase("operation"))
		{
			forwardTo="openOperationFileTracking";
		}
		else if(CommonFunction.checkNull(stageStep).equalsIgnoreCase("store"))
		{
			forwardTo="openStoreFileTracking";
		}
		form.reset(mapping, request);
		return mapping.findForward(forwardTo);
	}
	//Manish Space for Bulk OPS
	public ActionForward saveBulkBranchFileTrackingRecord(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		logger.info(" In saveBranchFileTrackingRecord......");
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
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
			return mapping.findForward("logout");
		}

		FileTrackingVO vo = new FileTrackingVO();
		DynaValidatorForm FileTrackingDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,FileTrackingDynaValidatorForm);
			if (userobj != null) {
			vo.setMakerId(userobj.getUserId());
			vo.setMakerDate(userobj.getBusinessdate());
		}
		FileTrackingDAO fileTrackDAO = (FileTrackingDAO) DaoImplInstanceFactory.getDaoImplInstance(FileTrackingDAO.IDENTITY);
		logger.info("Implementation class: " + fileTrackDAO.getClass()); // changed
		
		
		String loanId = request.getParameter("loanId");
		String receivedDate = CommonFunction.checkNull(request.getParameter("receivedDate"));
		String remark = CommonFunction.checkNull(request.getParameter("remarks"));
		
		logger.info("## In saveBulkBranchFileTrackingRecord():  loanId : ==>>"+loanId);
		logger.info("## In saveBulkBranchFileTrackingRecord():  receivedDate : ==>>"+receivedDate);
		logger.info("## In saveBulkBranchFileTrackingRecord():  remark : ==>>"+remark);
		
		String[] loanIds = loanId.split("\\|");
		String[] receivedDates = receivedDate.split("\\|");
		String[] remarks = remark.split("\\|");		

		boolean status = fileTrackDAO.insertBulkFileTracking(vo,loanIds,receivedDates,remarks);
		logger.info("Inside saveFileTrackingRecord Action.....displaying status...."+ status);
		if (status)		
			request.setAttribute("sms", "S");
		 else 
			request.setAttribute("sms", "E");
		
		ArrayList<FileTrackingVO> list = new ArrayList<FileTrackingVO>();
		list.add(vo);
		request.setAttribute("list", list);
		request.setAttribute("editVal", "editVal");
        form.reset(mapping, request);
		return mapping.findForward("openBulkBranchFileTracking");
	}
}
