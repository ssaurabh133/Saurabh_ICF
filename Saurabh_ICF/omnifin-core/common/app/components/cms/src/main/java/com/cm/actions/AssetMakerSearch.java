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

import com.cm.dao.assetInsuranceDAO;

import com.cm.vo.AssetForCMVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class AssetMakerSearch extends Action{
	private static final Logger logger = Logger.getLogger(AssetMakerSearch.class.getName());	
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
			logger.info("here in execute  method of AssetMakerSearch action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
			
		logger.info("In searchDetail  ");
		AssetForCMVO assetMakervo = new AssetForCMVO(); 
		boolean flag=false;
	
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
		session.removeAttribute("loanNo");
		session.removeAttribute("cusName");
		session.removeAttribute("loanId");
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
		
		assetMakervo.setCurrentPageLink(currentPageLink);
		//change by sachin
		assetInsuranceDAO dao=(assetInsuranceDAO)DaoImplInstanceFactory.getDaoImplInstance(assetInsuranceDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass());

		DynaValidatorForm AssetMakerDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(assetMakervo, AssetMakerDynaValidatorForm);
		if(CommonFunction.checkNull(assetMakervo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			assetMakervo.setReportingToUserId(userId);
		}
		logger.info("user Id:::::"+assetMakervo.getReportingToUserId());
		assetMakervo.setBranchId(branchId);
		assetMakervo.setUserId(userId);
        ArrayList<AssetForCMVO> searchListGrid = dao.assetMakerGrid(assetMakervo);
		
		request.setAttribute("list", searchListGrid);
		if((searchListGrid.size())==0)
		{
			request.setAttribute("datalist","datalist");
		}
		logger.info("searchListGrid    Size:---"+searchListGrid.size());
		return mapping.findForward("search");	
	
	}
}
