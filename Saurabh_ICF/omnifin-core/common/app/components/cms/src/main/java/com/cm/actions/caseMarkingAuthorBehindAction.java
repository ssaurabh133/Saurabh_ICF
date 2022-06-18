package com.cm.actions;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;
import com.cm.dao.CaseMarkingAuthorDAO;
import com.cm.vo.CaseMarkingAuthorVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class caseMarkingAuthorBehindAction extends Action{
	private static final Logger logger = Logger.getLogger(caseMarkingAuthorBehindAction.class.getName());	
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,
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
			logger.info("here in execute  method of caseMarkingAuthorBehindAction  the session is out----------------");
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
		
		caseMarkingCheckerVO.setCurrentPageLink(currentPageLink);
		CaseMarkingAuthorDAO dao=(CaseMarkingAuthorDAO)DaoImplInstanceFactory.getDaoImplInstance(CaseMarkingAuthorDAO.IDENTITY);
	    logger.info("Implementation class: "+dao.getClass());
	    DynaValidatorForm CaseMarkingCheckerDynaValidatorFormSearch = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(caseMarkingCheckerVO, CaseMarkingCheckerDynaValidatorFormSearch);
		if(CommonFunction.checkNull(caseMarkingCheckerVO.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			caseMarkingCheckerVO.setReportingToUserId(userId);
		}
		logger.info("user Id:::::"+caseMarkingCheckerVO.getReportingToUserId());
		caseMarkingCheckerVO.setBranchId(branchId);
		caseMarkingCheckerVO.setUserId(userId);
		ArrayList<CaseMarkingAuthorVO> searchCaseMarkingAuthorList= dao.searchCaseMarkingAuthor(caseMarkingCheckerVO);
		
		request.setAttribute("detailList", searchCaseMarkingAuthorList);
		request.setAttribute("datalist","datalist");
		logger.info("searchListGrid    Size:---"+searchCaseMarkingAuthorList.size());
		session.setAttribute("caseMarkingUserId",userId);
		return mapping.findForward("success");	
	}
}
