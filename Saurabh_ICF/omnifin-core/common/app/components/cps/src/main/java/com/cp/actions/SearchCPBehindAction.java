package com.cp.actions;

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
import com.cm.vo.DisbursalSearchVO;
import com.connect.DaoImplInstanceFactory;
import com.connect.LookUpInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.LeadCaptureVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class SearchCPBehindAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(SearchCPBehindAction.class.getName());
	
	public ActionForward getSearchCPScreen(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
	
		logger.info("In SearchCPBehindAction  ");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		String userId="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		if(userobj==null){
			logger.info("here in getSearchCPScreen method of SearchCPBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
		{
			userId=userobj.getUserId();
		}
		session.setAttribute("userId",userId);
		logger.info("userIDDDDDD------>>>>>>>" +userId);
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
		request.setAttribute("searchScreenForDeal","searchScreenForDeal");
		session.setAttribute("strFlagDV", "strFlagDV");
		session.removeAttribute("pParentGroup");
		session.removeAttribute("strFlagQ");
		return mapping.findForward("onSuccess");	
	
	}
	

	public ActionForward getSearchData(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		
		HttpSession session = request.getSession();
		session.removeAttribute("financialDealId");
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String branchId="";
		
		if(userobj!=null)
		{
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in getSearchData method of SearchCPBehindAction action the session is out----------------");
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
		
			logger.info("In getSearchData  ");
		CreditProcessingDAO creditDAO=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	    logger.info("Implementation class: "+creditDAO.getClass()); 	//changed by asesh
		//CreditProcessingDAO creditDAO = new CreditProcessingDAOImpl();
		DynaValidatorForm SearchForCMDynaValidatorForm = (DynaValidatorForm)form;
		
		DisbursalSearchVO vo = new DisbursalSearchVO();
	
		vo.setBranchId(branchId);
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, SearchForCMDynaValidatorForm);
		String dealIdfromCM=request.getParameter("dealIdfromCM");
		if(dealIdfromCM !=null)
		{
			vo.setLbxDealNo(dealIdfromCM);
			request.setAttribute("noSerchforCM","noSerchforCM");
		}
			
		//CreditManagementDAO dao = new CreditManagementDAOImpl();
		
		ArrayList<DisbursalSearchVO> detailListGrid = creditDAO.searchCPGrid(vo);
		request.setAttribute("searchScreenForDeal","searchScreenForDeal");
		request.setAttribute("true","true");
		request.setAttribute("list", detailListGrid);
		if((detailListGrid.size())==0)
		{
			request.setAttribute("datalist","datalist");
		}
		logger.info("detailListGrid    Size:---"+detailListGrid.size());
		return mapping.findForward("searchData");	
	
	}

	//Sanjog Changes Start Here
	
	
	public ActionForward getSearchScreenForLead(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
	
		logger.info("In SearchCPBehindAction...For LEad VIewer ");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		String userId="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in getSearchCPScreen method of SearchCPBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
		{
			userId=userobj.getUserId();
		}
		session.setAttribute("userId", userId);
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
		request.setAttribute("searchScreenForLead","searchScreenForLead");
		session.setAttribute("strFlagDV", "strFlagDV");
		session.removeAttribute("pParentGroup");
		return mapping.findForward("searchScreenForLead");	
	
	}


	public ActionForward getSearchDataForLead(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String branchId="";
		String userId="";
		
		if(userobj!=null)
		{
			branchId=userobj.getBranchId();
			userId=userobj.getUserId();
		}else{
			logger.info("here in getSearchData method of SearchCPBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.setAttribute("userId", userId);
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
		
			logger.info("In getSearchData  ");

			DynaValidatorForm LeadProcessingDynaValidatorForm = (DynaValidatorForm)form;
			LeadCaptureVo vo = new LeadCaptureVo();
			
			vo.setBranchId(branchId);
	        
			String leadNo = request.getParameter("leadNo");
	        
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, LeadProcessingDynaValidatorForm);
			
			 LeadProcessingRemote lp = (LeadProcessingRemote) LookUpInstanceFactory.getLookUpInstance(LeadProcessingRemote.REMOTE_IDENTITY, request);

		
		ArrayList detailListGrid = lp.searchCPGrid(vo,leadNo);
		request.setAttribute("searchScreenForLead","searchScreenForLead");
		request.setAttribute("list", detailListGrid);
		if((detailListGrid.size())==0)
		{
			request.setAttribute("datalist","datalist");
		}
		logger.info("detailListGrid    Size:---"+detailListGrid.size());
		return mapping.findForward("searchDataForLead");	
	
	}
	
	//Sanjog Changes End Here
}
