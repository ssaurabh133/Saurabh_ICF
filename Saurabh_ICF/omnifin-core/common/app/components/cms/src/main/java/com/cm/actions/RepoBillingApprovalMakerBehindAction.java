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
import com.cm.dao.RepoBillingApprovalMakerDAO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cm.vo.RepoBillingApprovalMakerVO;


public class RepoBillingApprovalMakerBehindAction extends Action{
	private static final Logger logger = Logger.getLogger(RepoBillingApprovalMakerBehindAction.class.getName());	
	
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
}
