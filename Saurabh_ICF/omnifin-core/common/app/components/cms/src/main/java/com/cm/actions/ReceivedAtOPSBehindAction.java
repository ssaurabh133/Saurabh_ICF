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

import com.cm.dao.FileTrackingDAO;
import com.cm.vo.FileTrackingVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ReceivedAtOPSBehindAction extends  DispatchAction {
	private static final Logger logger = Logger.getLogger(ReceivedAtOPSBehindAction.class.getName());

	public ActionForward operationFileTrackingOps(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
			{
		
		logger.info("In operationFileTracking");
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		String userId = null;	
		if (userobj != null) {
			userId = userobj.getUserId();	
		} else {
			logger.info("here in execute  method of FileTrackingSystemBehindAction  the session is out----------------");
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
			return mapping.findForward("logout");
		}
		FileTrackingVO fileTrackVO = new FileTrackingVO();
		DynaValidatorForm FileTrackingDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(fileTrackVO,FileTrackingDynaValidatorForm);
		fileTrackVO.setUserId(userId);
		FileTrackingDAO dao = (FileTrackingDAO) DaoImplInstanceFactory.getDaoImplInstance(FileTrackingDAO.IDENTITY);
		logger.info("Implementation class: " + dao.getClass());
		fileTrackVO.setTrackStatus("SS,RS"); // not avail this loan in operation 
        fileTrackVO.setStageStep("operation"); // for operation file tracking 
		ArrayList list = dao.searchfileTrackingOps(fileTrackVO);
		request.setAttribute("list", list);
		fileTrackVO=null;
		dao=null;
		form.reset(mapping, request);
     	return mapping.findForward("operationFileTrackingOps");

	}
	
public ActionForward savefileTrackingOps(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		logger.info(" In saveBranchFileTrackingRecord......");
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		// for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag = "";
		String branchId=null;
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
		
		
		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		String receivedDate = CommonFunction.checkNull(request.getParameter("receivedDate"));
		String remark = CommonFunction.checkNull(request.getParameter("remarks"));
		
	
		String[] loanIds = loanId.split("\\|");
		String[] receivedDates = receivedDate.split("\\|");
		String[] remarks = remark.split("\\|");	
		String search=CommonFunction.checkNull(request.getParameter("search")).trim();
		if(CommonFunction.checkNull(search).trim().equalsIgnoreCase(""))
			vo.setLbxBranchId(branchId);

		boolean status = fileTrackDAO.insertfileTrackingOps(vo,loanIds,receivedDates,remarks);
		logger.info("Inside saveFileTrackingRecord Action.....displaying status...."+ status);
		if (status)		
			request.setAttribute("sms", "S");
		 else 
			request.setAttribute("sms", "E");
		
		ArrayList<FileTrackingVO> list = new ArrayList<FileTrackingVO>();
		request.setAttribute("list", list);
		request.setAttribute("editVal", "editVal");
        form.reset(mapping, request);
        search=null;
        remarks=null;
        receivedDates=null;
        loanIds=null;
        vo=null;
        remark=null;
        receivedDate=null;
        loanId=null;
        fileTrackDAO=null;
		return mapping.findForward("saveReceivedAtOPS");
	}
}
