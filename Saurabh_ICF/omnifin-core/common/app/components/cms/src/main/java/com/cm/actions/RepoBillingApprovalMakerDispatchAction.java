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

import com.business.legalMasterBussiness.LegalMasterBusinessSessionBeanRemote;
import com.cm.dao.CaseMarkingMakerDAO;
import com.cm.dao.RepoBillingApprovalMakerDAO;
import com.cm.vo.CaseMarkingMakerVO;
import com.cm.vo.RepoBillingApprovalMakerVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.connect.LookUpInstanceFactory;
import com.legal.vo.CaseTypeMasterVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class RepoBillingApprovalMakerDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(RepoBillingApprovalMakerDispatchAction.class.getName());
	
	
	public ActionForward newAddRepoBillingApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				logger.info(" in newAddRepoBillingApproval()");
				ServletContext context = getServlet().getServletContext();
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				if(userobj==null){
					return mapping.findForward("sessionOut");
				}
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
				RepoBillingApprovalMakerDAO dao=(RepoBillingApprovalMakerDAO)DaoImplInstanceFactory.getDaoImplInstance(RepoBillingApprovalMakerDAO.IDENTITY);
			    logger.info("Implementation class: "+dao.getClass());
				strFlag=null;
				dao=null;
				request.setAttribute("save", "save");
				form.reset(mapping, request);
			    return mapping.findForward("openAdd");	
			}
	
	
	public ActionForward saveRepoBillingApprovalDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse resopnse) throws Exception{
			ServletContext context = getServlet().getServletContext();
			
			HttpSession session = request.getSession();
		
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");
			//for check User session start
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
			
			String userId=null;
			String bDate=null;
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bDate=userobj.getBusinessdate();
			}
	
		DynaValidatorForm RepoBillingApprovalDynaValidatorForm= (DynaValidatorForm)form;
		RepoBillingApprovalMakerVO vo = new RepoBillingApprovalMakerVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, RepoBillingApprovalDynaValidatorForm);
		

		vo.setMakerId(userId);
		vo.setMakerDate(bDate);

		RepoBillingApprovalMakerDAO dao=(RepoBillingApprovalMakerDAO)DaoImplInstanceFactory.getDaoImplInstance(RepoBillingApprovalMakerDAO.IDENTITY);
		String sms=null;
		logger.info("loanId:::"+vo.getLbxDealNo());
		
		boolean status = dao.saveRepoBillingApprovalDetails(vo);
		logger.info("Inside Country Master Action.....displaying status...."+status);
		
		
		ArrayList list = new ArrayList();
		list.add(vo);
		
		request.setAttribute("canForward", "canForward");
		if(status){
			sms="S";
			request.setAttribute("sms",sms);
			request.setAttribute("save", "save");
			request.setAttribute("loanId", vo.getLbxDealNo()); 
			//request.setAttribute("list", list);
			logger.info("loanId:::"+vo.getLbxDealNo());
			logger.info("sms:::"+sms);
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
			request.setAttribute("list", list);
			//request.setAttribute("save", "save");
		}
		logger.info("status"+status);
		return mapping.getInputForward();
	}
	public ActionForward forwardRepoBillingApprovalMarkingDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("In forwardCaseMarkingMaker.......");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId=null;
		String bDate=null;
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}else{
		return mapping.findForward("sessionOut");
		}
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
	
		RepoBillingApprovalMakerVO vo=new RepoBillingApprovalMakerVO(); 
		DynaValidatorForm dynaForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, dynaForm);	
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		RepoBillingApprovalMakerDAO dao=(RepoBillingApprovalMakerDAO)DaoImplInstanceFactory.getDaoImplInstance(RepoBillingApprovalMakerDAO.IDENTITY);
		 boolean status=dao.forwardRepoBillingApprovalMarkingDetails(vo);
       
		String sms=null;
		if(status)
		{
			sms="F";
			request.setAttribute("sms",sms);
			request.setAttribute("editVal", "editVal");
			request.setAttribute("status", vo.getRecStatus());
			logger.info("sms"+sms);
		}
		else{
			//logger.info("s122222222222"+s1);
				sms="E";
				request.setAttribute("sms",sms);
				request.setAttribute("editVal", "editVal");
				request.setAttribute("status", vo.getRecStatus());
			}
			
			//logger.info("In update case detail list"+ list.size());
			
		return mapping.getInputForward();	
      }
	
	public ActionForward openEditRepoBillingMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception { 
		
		logger.info("In openEditRepoBillingMaker::::::::");
		        ServletContext context = getServlet().getServletContext();
		     	HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String bDate=null;
				if(userobj!=null)
				{
						bDate=userobj.getBusinessdate();
				}else{
					return mapping.findForward("sessionOut");
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
						RepoBillingApprovalMakerVO vo=new RepoBillingApprovalMakerVO();
						
						vo.setBusinessdate(bDate);
						vo.setLbxDealNo(request.getParameter("loanId"));
						RepoBillingApprovalMakerDAO dao=(RepoBillingApprovalMakerDAO)DaoImplInstanceFactory.getDaoImplInstance(RepoBillingApprovalMakerDAO.IDENTITY);
						logger.info("Implementation class: "+dao.getClass());
						ArrayList<RepoBillingApprovalMakerVO> list = dao.openEditRepoBillingMaker(vo);
						vo=(RepoBillingApprovalMakerVO) list.get(0);
						RepoBillingApprovalMakerVO docVo=new RepoBillingApprovalMakerVO();
						docVo=(RepoBillingApprovalMakerVO) list.get(0);
						
						logger.info("getInterestStopped: "+docVo.getInterestStopped());
						logger.info("getbillingStatus: "+docVo.getBillingStopped());
						request.setAttribute("list", list);
						request.setAttribute("interestStatus", docVo.getInterestStopped());
						request.setAttribute("billingStatus", docVo.getBillingStopped());
						request.setAttribute("sdInterestStatus", docVo.getSDInterest());
							request.setAttribute("editVal", "editVal");
						request.setAttribute("canForward", "canForward");
						dao=null;
						strFlag=null;
						vo=null;
						form.reset(mapping, request);
						return mapping.findForward("editRepoBillingApproval");	
		}
	
	public ActionForward updateRepoBillingApprovalDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse resopnse) throws Exception{
			ServletContext context = getServlet().getServletContext();
			
			HttpSession session = request.getSession();
		
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");
			//for check User session start
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
			
			String userId=null;
			String bDate=null;
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bDate=userobj.getBusinessdate();
			}
	
		DynaValidatorForm RepoBillingApprovalDynaValidatorForm= (DynaValidatorForm)form;
		RepoBillingApprovalMakerVO vo = new RepoBillingApprovalMakerVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, RepoBillingApprovalDynaValidatorForm);
		

		vo.setMakerId(userId);
		vo.setMakerDate(bDate);

		RepoBillingApprovalMakerDAO dao=(RepoBillingApprovalMakerDAO)DaoImplInstanceFactory.getDaoImplInstance(RepoBillingApprovalMakerDAO.IDENTITY);
		String sms=null;
	
		boolean status = dao.updateRepoBillingApprovalDetails(vo);
		logger.info("Inside Country Master Action.....displaying status...."+status);
		
		
		ArrayList list = new ArrayList();
		list.add(vo);
		
		request.setAttribute("canForward", "canForward");
		if(status){
			sms="M";
			request.setAttribute("sms",sms);
			request.setAttribute("loanId", vo.getLbxDealNo()); 
			logger.info("loanId:::"+vo.getLbxDealNo());
			logger.info("sms:::"+sms);
			//request.setAttribute("save", "save");
			//request.setAttribute("list", list);
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
			request.setAttribute("list", list);
			//request.setAttribute("save", "save");
		}
		logger.info("status"+status);
		return mapping.getInputForward();
	}


	public ActionForward searchRepoBillingMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		HttpSession session=request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in execute  method of RepoBillingApprovalMakerBehindAction  the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("assetID");
		session.removeAttribute("lbxAssetID");
		session.removeAttribute("assetInsuranceID");
		session.removeAttribute("datatoapproveList");
		RepoBillingApprovalMakerVO vo=new RepoBillingApprovalMakerVO();
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
		logger.info("current page link .......... "+request.getParameter("d-49520-p"));
		
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
		vo.setCurrentPageLink(currentPageLink);
		RepoBillingApprovalMakerDAO dao=(RepoBillingApprovalMakerDAO)DaoImplInstanceFactory.getDaoImplInstance(RepoBillingApprovalMakerDAO.IDENTITY);
	    logger.info("Implementation class: "+dao.getClass());
	    DynaValidatorForm RepoBillingApprovalDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, RepoBillingApprovalDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		  
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setBranchId(branchId);
		vo.setMakerId(userId);
		session.setAttribute("caseMarkingUserId", vo.getReportingToUserId());
		ArrayList<RepoBillingApprovalMakerVO> searchRepoBillingApprovalMakerList= dao.searchRepoBillingApprovalMaker(vo);
		request.setAttribute("detailList", searchRepoBillingApprovalMakerList);
		request.setAttribute("datalist","datalist");
		logger.info("searchListGrid    Size:---"+searchRepoBillingApprovalMakerList.size());
		session.setAttribute("repomakerUserId",userId);
		return mapping.findForward("success");	
	}
	
	public ActionForward deleteRepoBillingApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				logger.info(" in deleteRepoBillingApproval()");
				ServletContext context = getServlet().getServletContext();
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				if(userobj==null)
				{
					return mapping.findForward("sessionOut");
				}
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
				String repoBillingApprovalLoanId=CommonFunction.checkNull(request.getParameter("repoBillingApprovalLoanId"));
			    //logger.info("caseMarkingLoanId: "+caseMarkingLoanId+" tableStatus: "+tableStatus);
				RepoBillingApprovalMakerDAO dao=(RepoBillingApprovalMakerDAO)DaoImplInstanceFactory.getDaoImplInstance(RepoBillingApprovalMakerDAO.IDENTITY);
			    logger.info("Implementation class: "+dao.getClass());
				String deleteMsg= dao.deleteRepoBillingApproval(repoBillingApprovalLoanId); 
				request.setAttribute("deleteMsg", deleteMsg);
				form.reset(mapping, request);
				repoBillingApprovalLoanId=null;
				dao=null;
				 form.reset(mapping, request);
			    return mapping.findForward("deleteSuccess");	
			}
}
