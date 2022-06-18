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
import com.caps.actionForm.UnallocatedReportForm;
import com.caps.dao.CollReportDAO;
import com.caps.VO.ReallocationMasterVo;

public class UnallocatedReportsBehindAction extends Action {
	
	private static final Logger logger = Logger.getLogger(UnallocatedReportsBehindAction.class.getName());	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("Inside UnallocatedReportsBehindAction");
		HttpSession session = request.getSession();

		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String businessDate="";
		if(userobj==null){
			logger.info("here execute method of UnallocatedReportsBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
		{
			businessDate=userobj.getBusinessdate();
			userId=userobj.getUserId();
		}
		UnallocatedReportForm uRForm=(UnallocatedReportForm)form;
		uRForm.reset();
		CollReportDAO collDAO=(CollReportDAO)DaoImplInstanceFactory.getDaoImplInstance(CollReportDAO.IDENTITY);
		  logger.info("Implementation class: "+collDAO.getClass());
		ArrayList<ReallocationMasterVo> custcatList=collDAO.custCategoryReport();
		ArrayList<ReallocationMasterVo> npastageList=collDAO.npaStageReport();
		ArrayList<ReallocationMasterVo> productList=collDAO.productReport();
		ArrayList<ReallocationMasterVo> product=collDAO.getProductName();
		ArrayList<ReallocationMasterVo> loanClassification=collDAO.getLoanClassification();
		request.setAttribute("customercatList",custcatList);
		request.setAttribute("npastageList",npastageList);
		request.setAttribute("productList",productList);
		request.setAttribute("productlist",product);
		request.setAttribute("loanClasslist",loanClassification);
		session.setAttribute("userId", userId);
		
		
		return mapping.findForward("success");
	}
}
