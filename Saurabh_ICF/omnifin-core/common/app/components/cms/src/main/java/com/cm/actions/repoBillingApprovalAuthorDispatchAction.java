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
import com.cm.dao.CaseMarkingAuthorDAO;
import com.cm.dao.RepoBillingApprovalAuthorDAO;
import com.cm.dao.RepoBillingApprovalMakerDAO;
import com.cm.vo.CaseMarkingAuthorVO;
import com.cm.vo.RepoBillingApprovalMakerVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class repoBillingApprovalAuthorDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(repoBillingApprovalAuthorDispatchAction.class.getName());
	
public ActionForward openEditRepoBillingApprovalAuthor(ActionMapping mapping, ActionForm form,
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
					RepoBillingApprovalAuthorDAO dao=(RepoBillingApprovalAuthorDAO)DaoImplInstanceFactory.getDaoImplInstance(RepoBillingApprovalAuthorDAO.IDENTITY);
					logger.info("Implementation class: "+dao.getClass());
					ArrayList<RepoBillingApprovalMakerVO> list = dao.openEditRepoBillingApprovalAuthor(vo);
					logger.info("getInterestStopped: "+vo.getInterestStopped());
					logger.info("getbillingStatus: "+vo.getBillingStopped());
					logger.info("getbillingStatus: "+vo.getSDInterest());
					session.setAttribute("sessionrepolist", list);
					session.setAttribute("loanId", vo.getLbxDealNo());
					session.setAttribute("interestStatus", vo.getInterestStopped());
					session.setAttribute("billingStatus", vo.getBillingStopped());
					session.setAttribute("sdInterestStatus", vo.getSDInterest());
					request.setAttribute("canForward", "canForward");
					dao=null;
					strFlag=null;
					vo=null;
					return mapping.findForward("editRepoBillingApproval");	
	}
	
	
	public ActionForward repoBillingApprovalAuthorScreen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
		logger.info(" in repoBillingApprovalAuthorScreen method of CaseMarkingAuthorDispatch Action action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String sessionId = session.getAttribute("sessionID").toString();	
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		ServletContext context = getServlet().getServletContext();
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
		
		form.reset(mapping, request);
	return mapping.findForward("authorScreen");
	}
	
	public ActionForward saveRepoBillingApprovalChecker(ActionMapping mapping, ActionForm form,
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
		RepoBillingApprovalAuthorDAO dao=(RepoBillingApprovalAuthorDAO)DaoImplInstanceFactory.getDaoImplInstance(RepoBillingApprovalAuthorDAO.IDENTITY);
		logger.info("loanId"+vo.getLbxDealNo()); 
		boolean status=dao.saveRepoBillingApprovalChecker(vo);
       
		String sms=null;
		if(status)
		{
			sms="S";
			request.setAttribute("sms",sms);
			request.setAttribute("editVal", "editVal");
			request.setAttribute("status", vo.getRecStatus());
			logger.info("sms"+sms);
			request.setAttribute("decision",vo.getDecision());

			request.setAttribute("status", vo.getRecStatus());
		}
		else{
			//logger.info("s122222222222"+s1);
				sms="E";
				request.setAttribute("sms",sms);
				request.setAttribute("editVal", "editVal");
				request.setAttribute("status", vo.getRecStatus());
				request.setAttribute("decision",vo.getDecision());

				request.setAttribute("status", vo.getRecStatus());
			}
			
			//logger.info("In update case detail list"+ list.size());
			
		return mapping.getInputForward();	
      }

	
	public ActionForward search(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		HttpSession session=request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in execute  method of repoBillingApprovalAuthorBehindAction  the session is out----------------");
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
		RepoBillingApprovalAuthorDAO dao=(RepoBillingApprovalAuthorDAO)DaoImplInstanceFactory.getDaoImplInstance(RepoBillingApprovalAuthorDAO.IDENTITY);
	    logger.info("Implementation class: "+dao.getClass());
	    DynaValidatorForm CaseMarkingCheckerDynaValidatorFormSearch = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CaseMarkingCheckerDynaValidatorFormSearch);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		ArrayList<RepoBillingApprovalMakerVO> searchCaseMarkingAuthorList= dao.searchRepoBillingApprovalAuthor(vo);
		
		request.setAttribute("detailList", searchCaseMarkingAuthorList);
		request.setAttribute("datalist","datalist");
		logger.info("searchListGrid    Size:---"+searchCaseMarkingAuthorList.size());
		session.setAttribute("repoAuthorUserId",userId);
		return mapping.findForward("success");	
	}
}

