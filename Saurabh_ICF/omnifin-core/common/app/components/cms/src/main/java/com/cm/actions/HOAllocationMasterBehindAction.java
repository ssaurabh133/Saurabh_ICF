//AUTHOR NAME:  Mradul Agarwal
//CREATION DATE:17-Jan-2013 


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

public class HOAllocationMasterBehindAction extends Action
{
	Logger logger = Logger.getLogger(StationaryIssuanceMasterBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		logger.info("in:::: HOAllocationMasterBehindAction");
		StationaryMasterForm stationaryMasterForm = new StationaryMasterForm();
		StationaryIssuanceMasterBusiness stationaryIssuanceMasterBusiness = new StationaryIssuanceMasterBusiness();
		
		
		request.setAttribute("list", stationaryIssuanceMasterBusiness.getHoAllocationSearch(stationaryMasterForm));
		return mapping.findForward("Success");
}
}
		