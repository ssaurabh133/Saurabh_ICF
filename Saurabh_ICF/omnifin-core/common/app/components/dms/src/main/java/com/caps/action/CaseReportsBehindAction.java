package com.caps.action;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.connect.DaoImplInstanceFactory;
import com.login.roleManager.UserObject;
import com.caps.actionForm.CasesReportForm;
import com.cm.actionform.ReportsForm;
import com.caps.dao.CollReportDAO;
import com.caps.VO.ReallocationMasterVo;

public class CaseReportsBehindAction extends Action {
	private static final Logger logger = Logger.getLogger(CaseReportsBehindAction.class.getName());	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String businessDate="";
		if(userobj==null)
		{
			logger.info("here execute method of  CaseReportsBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
		{
			businessDate=userobj.getBusinessdate();
			userId=userobj.getUserId();
		}
		logger.info("Inside CaseReportsBehindAction");
		CasesReportForm cRForm=(CasesReportForm)form;
		cRForm.reset();
		CollReportDAO collDAO=(CollReportDAO)DaoImplInstanceFactory.getDaoImplInstance(CollReportDAO.IDENTITY);
		 logger.info("Implementation class: "+collDAO.getClass());
		ArrayList<ReallocationMasterVo> custcatList=collDAO.custCategoryReport();
		ArrayList<ReallocationMasterVo> npastageList=collDAO.npaStageReport();
		ArrayList<ReallocationMasterVo> productList=collDAO.productReport();
		//amandeep work starts
		ArrayList<ReallocationMasterVo> product=collDAO.getProductName();
		ArrayList<ReallocationMasterVo> loanClassification=collDAO.getLoanClassification();
		//amandeep work ends
		request.setAttribute("customercatList",custcatList);
		request.setAttribute("npastageList",npastageList);
		request.setAttribute("productList",productList);
		request.setAttribute("productlist",product);
		request.setAttribute("loanClasslist",loanClassification);
		session.setAttribute("userId", userId);
		return mapping.findForward("success");
	}
	

}
