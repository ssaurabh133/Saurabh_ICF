package com.cm.actions;

import javax.servlet.http.HttpServletRequest;    

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cm.actionform.StationaryMasterForm;
import com.cm.business.StationaryIssuanceMasterBusiness;

public class StationaryBranchgChangeBehindAction extends Action{
	
	Logger logger = Logger.getLogger(StationaryBranchgChangeBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		logger.info("In execute ");
		StationaryMasterForm stationaryMasterForm = new StationaryMasterForm();
		StationaryIssuanceMasterBusiness stationaryIssuanceMasterBusiness = new StationaryIssuanceMasterBusiness();
				
		request.setAttribute("list", stationaryIssuanceMasterBusiness.getStationaryBranchChangeSearch(stationaryMasterForm));
		form.reset(mapping, request);
		return mapping.findForward("success");
}

}
