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
import com.cm.vo.CaseMarkingAuthorVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class CaseMarkingAuthorDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(CaseMarkingAuthorDispatchAction.class.getName());
	
public ActionForward openEditCaseMarkingAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception { 
		ServletContext context = getServlet().getServletContext();
		CaseMarkingAuthorVO vo=new CaseMarkingAuthorVO(); 
		logger.info("In openEditCaseMarkingAuthor");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
		{
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
		vo.setLbxDealNo(request.getParameter("loanId"));
		vo.setCaseId(request.getParameter("caseId"));
		CaseMarkingAuthorDAO dao=(CaseMarkingAuthorDAO)DaoImplInstanceFactory.getDaoImplInstance(CaseMarkingAuthorDAO.IDENTITY);
		ArrayList<CaseMarkingAuthorVO> list = dao.openEditCaseMarkingAuthor(vo);
		//logger.info("In openEditCaseMarkingAuthor CaseMarkingAuthorVO list"+list.size());
		session.setAttribute("list", list);				
		session.setAttribute("loanId", vo.getLbxDealNo());
		session.setAttribute("caseId", vo.getCaseId());
		dao=null;
		vo=null;
		form.reset(mapping, request);
		return mapping.findForward("editCaseMarking");	
}
	
	
	public ActionForward caseMarkingAuthorScreen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
		logger.info(" in caseMarkingAuthorScreen method of CaseMarkingAuthorDispatch Action action the session is out----------------");
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
	
	public ActionForward saveCaseMarkingCheckerDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("In saveCaseMarkingCheckerDetails.......");
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
		
		CaseMarkingAuthorVO vo=new CaseMarkingAuthorVO(); 
		DynaValidatorForm dynaForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, dynaForm);	
		
		vo.setUserId(userId);
		vo.setMakerDate(bDate);
	CaseMarkingAuthorDAO dao=(CaseMarkingAuthorDAO)DaoImplInstanceFactory.getDaoImplInstance(CaseMarkingAuthorDAO.IDENTITY);
		String decision =  request.getParameter("decision");
		//shivesh
		logger.info("decision="+decision);
		vo.setDecision(decision);
		//shivesh
        ArrayList<Object> status=dao.saveCaseMarkingCheckerDetails(vo);
    
		String s1=(String)status.get(1);
	
		String sms=null;
		
		if(CommonFunction.checkNull(s1).trim().equalsIgnoreCase("S"))
		{
			sms="S";
			session.setAttribute("message",sms);
		}
			else{
			sms="E";
			session.setAttribute("message",sms);
			}
		    request.setAttribute("editVal", "editVal");
		    request.setAttribute("message",sms);
		    request.setAttribute("decision",vo.getDecision());

			request.setAttribute("status", vo.getRecStatus());
			dao=null;
			strFlag=null;
			vo=null;
			s1=null;
			form.reset(mapping, request);
			return mapping.getInputForward();
  }
	
	public ActionForward search(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception {
		HttpSession session=request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId=null;
		String branchId=null;
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in search  method of caseMarkingAuthorDispatchAction  the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("assetID");
		session.removeAttribute("lbxAssetID");
		session.removeAttribute("assetInsuranceID");
		session.removeAttribute("datatoapproveList");
		
		CaseMarkingAuthorVO caseMarkingCheckerVO=new CaseMarkingAuthorVO();
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
		//logger.info("current page link .......... "+request.getParameter("d-49520-p"));
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		}
		
	//	logger.info("current page link ................ "+request.getParameter("d-49520-p"));
		caseMarkingCheckerVO.setCurrentPageLink(currentPageLink);
		CaseMarkingAuthorDAO dao=(CaseMarkingAuthorDAO)DaoImplInstanceFactory.getDaoImplInstance(CaseMarkingAuthorDAO.IDENTITY);
	    logger.info("Implementation class: "+dao.getClass());
	    DynaValidatorForm CaseMarkingCheckerDynaValidatorFormSearch = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(caseMarkingCheckerVO, CaseMarkingCheckerDynaValidatorFormSearch);
		if(CommonFunction.checkNull(caseMarkingCheckerVO.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			caseMarkingCheckerVO.setReportingToUserId(userId);
		}
		//logger.info("user Id:::::"+caseMarkingCheckerVO.getReportingToUserId());
		caseMarkingCheckerVO.setBranchId(branchId);
		caseMarkingCheckerVO.setUserId(userId);
		ArrayList<CaseMarkingAuthorVO> searchCaseMarkingAuthorList= dao.searchCaseMarkingAuthor(caseMarkingCheckerVO);
		request.setAttribute("detailList", searchCaseMarkingAuthorList);
		request.setAttribute("datalist","datalist");
		//logger.info("searchListGrid    Size:---"+searchCaseMarkingAuthorList.size());
		session.setAttribute("caseMarkingUserId",userId);
		dao=null;
		caseMarkingCheckerVO=null;
		form.reset(mapping, request);
		return mapping.findForward("success");	
	}
	
}

