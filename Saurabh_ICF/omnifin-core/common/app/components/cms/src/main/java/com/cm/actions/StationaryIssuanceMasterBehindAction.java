//AUTHOR NAME:  Mradul Agarwal
//CREATION DATE:17-Jan-2013 


package com.cm.actions;

import javax.servlet.http.HttpServletRequest;    
import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cm.actionform.StationaryMasterForm;
import com.cm.business.StationaryIssuanceMasterBusiness;
import com.login.roleManager.UserObject;

public class StationaryIssuanceMasterBehindAction extends Action
{
	Logger logger = Logger.getLogger(StationaryIssuanceMasterBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		
		logger.info("in:::: StationaryIssuanceMasterBehindAction");
		StationaryMasterForm stationaryMasterForm = new StationaryMasterForm();
		HttpSession session = request.getSession(false);
		UserObject sessUser = (UserObject) session.getAttribute("userobject");
		int compid =0;
		String businessDate=null;
		String branchId =null;
       if(sessUser!=null){
		businessDate=sessUser.getBusinessdate();
		compid=sessUser.getCompanyId();
		branchId = sessUser.getBranchId();
       }
		stationaryMasterForm.setCompanyID(compid);
		stationaryMasterForm.setBranchid(branchId);
		stationaryMasterForm.setMakerDate(businessDate);
		
		session.setAttribute("branchId", branchId);// For Lov
		
		StationaryIssuanceMasterBusiness stationaryIssuanceMasterBusiness = new StationaryIssuanceMasterBusiness();
		
		
		request.setAttribute("list", stationaryIssuanceMasterBusiness.getSearch(stationaryMasterForm));
		form.reset(mapping, request);
		return mapping.findForward("Success");
}
}
		