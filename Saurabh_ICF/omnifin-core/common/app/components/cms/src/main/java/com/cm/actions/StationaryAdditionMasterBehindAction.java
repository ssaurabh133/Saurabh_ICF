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
import com.cm.business.StationaryAdditionMasterBusiness;

public class StationaryAdditionMasterBehindAction extends Action
{
	Logger logger = Logger.getLogger(StationaryAdditionMasterBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		StationaryAdditionMasterBusiness stationaryAdditionMasterBusiness = new StationaryAdditionMasterBusiness();
		StationaryMasterForm stationaryMasterForm = new StationaryMasterForm();
		logger.info(" In StationaryAdditionMasterBehindAction:-------");		
		
		
		request.setAttribute("list", stationaryAdditionMasterBusiness.getList(stationaryMasterForm));
		request.setAttribute("bankList", stationaryAdditionMasterBusiness.getBankStatement(stationaryMasterForm));
		return mapping.findForward("Success");
}
}