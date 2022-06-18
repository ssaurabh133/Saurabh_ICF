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
import com.cm.dao.ReceiptDAO;

import com.cm.vo.ReceiptMakerVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ReceiptMakerSearch extends Action{
	private static final Logger logger = Logger.getLogger(ReceiptMakerSearch.class.getName());
	public ActionForward execute(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
	
		logger.info("In searchDetail  ");
		
		HttpSession session = request.getSession();		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId=null;
		String branchId=null;
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info(" in Execute method of  ReceiptMakerSearch action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instrumentID");
		session.removeAttribute("businessPartnerType");
		session.removeAttribute("datatoapproveList");
		session.removeAttribute("receiptNoFlag");
		//boolean flag=false;
		Object sessionId = session.getAttribute("sessionID");

		ReceiptMakerVO vo = new ReceiptMakerVO();
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
		//Manish Baranwal on 14/04/2014
		//PaymentReceiptBusinessBeanRemote dao = (PaymentReceiptBusinessBeanRemote)LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request); 
		ReceiptDAO dao=(ReceiptDAO)DaoImplInstanceFactory.getDaoImplInstance(ReceiptDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
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
		

		DynaValidatorForm ReceiptCMDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ReceiptCMDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		vo.setCurrentPageLink(currentPageLink);
		ArrayList<ReceiptMakerVO> receiptMakerSearchGrid = dao.receiptMakerGrid(vo);
		
		request.setAttribute("list", receiptMakerSearchGrid);
		request.setAttribute("receiptMakerSearchGrid","receiptMakerSearchGrid");
		if((receiptMakerSearchGrid.size())==0)
		{
			request.setAttribute("datalist","datalist");
		}
		logger.info("receiptMakerSearchGrid    Size:---"+receiptMakerSearchGrid.size());

		String forward=request.getParameter("forward");
		String search=request.getParameter("search");
		
		String frdLoanID=null;
		String frdInstrumentID=null;
		String autoManualFlag=null;
		if(forward==null)
		{
			forward="No";
			frdLoanID="0";
			frdInstrumentID="0";
		}
		else
		{
			forward="Yes";
			frdLoanID=request.getParameter("frdLoanID");
			frdInstrumentID=request.getParameter("frdInstrumentID");
			autoManualFlag=request.getParameter("autoManualFlag");
		}
		if(search!=null)
		{
			forward="No";
			frdLoanID="0";
			frdInstrumentID="0";
		}
		
		request.setAttribute("forward",forward);
		request.setAttribute("frdLoanID",frdLoanID);
		request.setAttribute("frdInstrumentID",frdInstrumentID);
		request.setAttribute("autoManualFlag",autoManualFlag);
//Neeraj Tripathi end
		dao=null;
		vo=null;
		form.reset(mapping, request);
		return mapping.findForward("receiptMakerSearch");	
	
	}
	
}
