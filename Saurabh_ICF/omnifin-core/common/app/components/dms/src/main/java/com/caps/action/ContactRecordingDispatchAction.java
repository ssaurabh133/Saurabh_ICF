package com.caps.action;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.caps.VO.ContactRecordingFollowUpVO;
import com.caps.dao.CollDAO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cp.vo.ApplicantTypeVO;
import com.cp.dao.CreditProcessingDAO;

public class ContactRecordingDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ContactRecordingDispatchAction.class.getName());
	
	
	public ActionForward openContactRecordingTrail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		logger.info(" in openContactRecordingTrail():-----");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here openContactRecordingTrail method of ContactRecordingDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String loanId=CommonFunction.checkNull(request.getParameter("loanId"));
		String exclationFlag=CommonFunction.checkNull(request.getParameter("exclationFlag"));
		String superReview=CommonFunction.checkNull(request.getParameter("superReview"));
		String caseHistory=CommonFunction.checkNull(request.getParameter("caseHistory"));
		logger.info("loanId:-------"+loanId);
		logger.info("exclationFlag:-------"+exclationFlag);
		ContactRecordingFollowUpVO ContactRecordingVO=new ContactRecordingFollowUpVO();
		ContactRecordingVO.setLoanId(loanId);
		CollDAO collDAO=(CollDAO)DaoImplInstanceFactory.getDaoImplInstance(CollDAO.IDENTITY);
		  logger.info("Implementation class: "+collDAO.getClass());
		request.setAttribute("customerInfoList", collDAO.customerInfoList(ContactRecordingVO));
		request.setAttribute("contactInfoList", collDAO.customerContactInfoList(ContactRecordingVO));
		//request.setAttribute("allocationInfoList", collDAO.allocationInfoList(ContactRecordingVO));
		request.setAttribute("loanInfoList", collDAO.loanInfoList(ContactRecordingVO));
		request.setAttribute("followUpGridList", collDAO.followUpGridList(ContactRecordingVO));
		request.setAttribute("assetInfoList", collDAO.assetInfoList(ContactRecordingVO));
		ArrayList<ContactRecordingFollowUpVO> vehicleInfoList=(ArrayList<ContactRecordingFollowUpVO>) collDAO.vehicleInfoList(ContactRecordingVO);
		logger.info("vehicleInfoList--------------"+vehicleInfoList.size() );
		if(vehicleInfoList.size()>0){
		request.setAttribute("vehicleInfoList", vehicleInfoList);
		}
		logger.info("after asset detail:---------------------------------" );
		request.setAttribute("exclationFlag",exclationFlag);
		if(superReview.equalsIgnoreCase("superReview")){//To open Contact recording page as Super Review
			request.setAttribute("superReview",superReview);	
		}
		if(!caseHistory.equalsIgnoreCase("")){
			request.setAttribute("caseHistory",caseHistory);	
		}
		return mapping.findForward("openContactRecording");
	}
	public ActionForward openFollowUpTrail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		logger.info(" in openFollowUpTrail():-----");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here openFollowUpTrail method of ContactRecordingDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String loanId=request.getParameter("loanId");
		String exclationFlag=request.getParameter("exclationFlag");
		String superReview=request.getParameter("superReview");
		
		request.setAttribute("loanId",loanId);
		request.setAttribute("exclationFlag",exclationFlag);
		request.setAttribute("superReview",superReview);
		 CollDAO collDAO=(CollDAO)DaoImplInstanceFactory.getDaoImplInstance(CollDAO.IDENTITY);
		  logger.info("Implementation class: "+collDAO.getClass());
		request.setAttribute("actionCodeList", collDAO.actionCodeList());
		
		CreditProcessingDAO detail=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		ArrayList<ApplicantTypeVO> applist = detail.getApplicantList();
		String dfltACType = detail.getDefaultAccountType();
		request.setAttribute("dfltACType",dfltACType);
		request.setAttribute("applist", applist);
		
		return mapping.findForward("openFollowUp");
	}
	
	public ActionForward saveFollowUpTrail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		logger.info(" in saveFollowUpTrail():-----");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userid="";
		String bDate="";
		if(userobj!=null){
			userid=userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here saveFollowUpTrail method of  ContactRecordingDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String superReview=CommonFunction.checkNull(request.getParameter("superReview"));
		request.setAttribute("superReview",superReview);
		DynaValidatorForm ContactRordingFollowUpForm= (DynaValidatorForm)form;
		ContactRecordingFollowUpVO ContactRecordingVO=new ContactRecordingFollowUpVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(ContactRecordingVO, ContactRordingFollowUpForm);
		ContactRecordingVO.setMakerId(userid);
		ContactRecordingVO.setBusinessDate(bDate);
		CollDAO collDAO=(CollDAO)DaoImplInstanceFactory.getDaoImplInstance(CollDAO.IDENTITY);
		  logger.info("Implementation class: "+collDAO.getClass());
		boolean status=collDAO.saveFollowUpTrail(ContactRecordingVO);
		if(status){
			logger.info("Data save Successfully");
			request.setAttribute("sms", "S");
			request.setAttribute("loanId", ContactRecordingVO.getLoanId());
			request.setAttribute("exclationFlag", ContactRecordingVO.getExclationFlag());
			request.setAttribute("actionCode",ContactRecordingVO.getActionCode() );
			//return mapping.findForward("contactRecordingBehindAction");	
			
		}else{
			request.setAttribute("loanId", ContactRecordingVO.getLoanId());
			request.setAttribute("exclationFlag", ContactRecordingVO.getExclationFlag());
			logger.info("Data not  save Successfully");
			request.setAttribute("sms", "E");
		}
		request.setAttribute("actionCodeList", collDAO.actionCodeList());
		return mapping.findForward("openFollowUp");
	}
	public ActionForward showAddressDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
				
				logger.info("In showAddressDetails()...................");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				if(userobj==null){
					logger.info("here showAddressDetails method of ContactRecordingDispatchAction action the session is out----------------");
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
								
				ArrayList list=new ArrayList();
				
				  logger.info("current page link .......... "+request.getParameter("d-49520-p"));
					
					
			        String loanId=request.getParameter("loanId");
			        logger.info("In loanId:::::::::::::::::::::::::::::"+loanId);
			        CollDAO collDAO=(CollDAO)DaoImplInstanceFactory.getDaoImplInstance(CollDAO.IDENTITY);
					  logger.info("Implementation class: "+collDAO.getClass());
					list= collDAO.searchClassificationData(loanId);
					
					int listSize = list.size();
					if (listSize> 0) {
						 request.setAttribute("list", list);
							logger.info("list.isEmpty(): "+list.isEmpty());
							request.setAttribute("list",list);
					}else{
						request.setAttribute("sms","No");
					}
								
				return mapping.findForward("showSuccess");	
			}
}
