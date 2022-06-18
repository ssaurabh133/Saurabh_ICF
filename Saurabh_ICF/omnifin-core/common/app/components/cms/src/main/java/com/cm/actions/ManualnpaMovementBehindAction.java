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

import com.cm.dao.LoanInitiationDAO;
import com.cm.dao.ManualnpaMovementDAO;
import com.cm.dao.assetInsuranceDAO;
import com.cm.vo.AssetForCMVO;
import com.cm.vo.ManualnpaMovementVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ManualnpaMovementBehindAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ManualnpaMovementBehindAction.class.getName());	
	//KANIKA SEARCH FUNCTION FOR MAKER
	public ActionForward searchManualNpa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside search_manual_npa()...........");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		session.removeAttribute("list");
		session.removeAttribute("manualNpaId");
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in search_manual_npa () session is out----------------");
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
		ManualnpaMovementVO vo = new ManualnpaMovementVO();
		DynaValidatorForm ManualNpaMovementSearchDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ManualNpaMovementSearchDynaValidatorForm);
		vo.setCurrentPageLink(currentPageLink);

		vo.setBranchId(branchId);
		vo.setUserId(userId);
		/*vo.setReportingToUserId(userId);
		*/
		//ManualnpaMovementDAO service = new ManualnpaMovementDAOImpl();
		ManualnpaMovementDAO service=(ManualnpaMovementDAO)DaoImplInstanceFactory.getDaoImplInstance(ManualnpaMovementDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		ArrayList<ManualnpaMovementVO> searchDataList = service.searchManualNpa(vo);
		request.setAttribute("userId", userId);
		session.removeAttribute("author");
		session.removeAttribute("list");
		request.setAttribute("list", searchDataList);
		request.setAttribute("maker", "maker");
		return mapping.findForward("success");
	}
	
	public ActionForward searchManualNpadetail(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		
		HttpSession session =  request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in authorSearchDetail method of  AssetAuthorProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		session.removeAttribute("pParentGroup");
		session.removeAttribute("assetID");
		session.removeAttribute("lbxAssetID");
		session.removeAttribute("datatoapproveList");
		//for check User session start
		AssetForCMVO assetMakervo = new AssetForCMVO();
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
		logger.info("In authorsearchDetail  ");
		//change by sachin
		assetInsuranceDAO dao=(assetInsuranceDAO)DaoImplInstanceFactory.getDaoImplInstance(assetInsuranceDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass());
        //end by sachin
//		assetInsuranceDAO dao = new assetInsuranceDAOImpl();
        DynaValidatorForm AssetMakerDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(assetMakervo, AssetMakerDynaValidatorForm);
		if(CommonFunction.checkNull(assetMakervo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			assetMakervo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+assetMakervo.getReportingToUserId());
		assetMakervo.setCurrentPageLink(currentPageLink);
		assetMakervo.setUserId(userId);
		assetMakervo.setBranchId(branchId);
		ArrayList<AssetForCMVO> authordetailList = dao.assetAuthorGrid(assetMakervo);
		
		request.setAttribute("authordetailList", authordetailList);
		request.setAttribute("fromAuthor", "fromAuthor");
		
		logger.info("authordetailList    Size:---"+authordetailList.size());
		
		return mapping.findForward("authorSearch");	
	}
	

	public ActionForward viewframe(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		HttpSession session =  request.getSession(); 
		session.removeAttribute("list");
		session.removeAttribute("manualNpaId");
		session.removeAttribute("loanId");
		session.removeAttribute("npaflag");
		
		String manualNpaId=request.getParameter("manualNpaId");
		String loanId=request.getParameter("loanId");
		
		ManualnpaMovementDAO service=(ManualnpaMovementDAO)DaoImplInstanceFactory.getDaoImplInstance(ManualnpaMovementDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		ArrayList<ManualnpaMovementVO> list = service.selectManualNpa(manualNpaId,loanId);
		session.setAttribute("manualNpaId", manualNpaId);
		session.setAttribute("loanId", loanId);
		session.setAttribute("author", "author");
		session.setAttribute("npaflag", list.get(0).getCurrNpaStage());
		session.setAttribute("list", list);
		return mapping.findForward("openManualNpaMovementAuthorWithData");
	}


}
