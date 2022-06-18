//AUTHOR NAME:  Mradul Agarwal


//CREATION DATE:11-Sept-2013 


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

public class BranchHoStationaryAcknowledgment extends Action
{
	Logger logger = Logger.getLogger(BranchHoStationaryAcknowledgment.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		
		logger.info("in:::: BranchHoStationaryAcknowledgment");
		StationaryMasterForm stationaryMasterForm = new StationaryMasterForm();
		HttpSession session = request.getSession(false);
		UserObject sessUser = (UserObject) session.getAttribute("userobject");
		int compid =0;
		String businessDate=null;
		String branchId =null;
		String userId=null;
       if(sessUser!=null)
       {
    	   userId=sessUser.getUserId();
    	   businessDate=sessUser.getBusinessdate();
    	   compid=sessUser.getCompanyId();
    	   branchId = sessUser.getBranchId();
       }
		stationaryMasterForm.setCompanyID(compid);
		stationaryMasterForm.setBranchid(branchId);
		stationaryMasterForm.setMakerDate(businessDate);
		stationaryMasterForm.setLbxUserId(userId);
		session.setAttribute("branchId", branchId);		
		StationaryIssuanceMasterBusiness stationaryIssuanceMasterBusiness = new StationaryIssuanceMasterBusiness();
				
		request.setAttribute("list", stationaryIssuanceMasterBusiness.getSearchHoStationaryAcknow(stationaryMasterForm));
		return mapping.findForward("Success");
}
}
		