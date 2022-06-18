package com.cp.actions;

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
import com.cp.dao.ImdDAO;
import com.cp.vo.ImdMakerVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ImdMakerSearch extends Action{
	private static final Logger logger = Logger.getLogger(ImdMakerSearch.class.getName());
	public ActionForward execute(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
	
		logger.info("In searchDetail  ");
		
		HttpSession session = request.getSession();		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
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
		session.setAttribute("userId", userId);// for lov
		session.setAttribute("branchId", branchId);//for lov
		//boolean flag=false;
		Object sessionId = session.getAttribute("sessionID");

		ImdMakerVO vo = new ImdMakerVO();
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
		
		ImdDAO dao=(ImdDAO)DaoImplInstanceFactory.getDaoImplInstance(ImdDAO.IDENTITY);
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
		
		
		
	//	ArrayList<PaymentMakerForCMVO> bussinessPartnerList= dao.getbussinessPartner();
		//request.setAttribute("bussinessPartnerList", bussinessPartnerList);
		DynaValidatorForm ImdCPDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ImdCPDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		vo.setCurrentPageLink(currentPageLink);
		ArrayList<ImdMakerVO> receiptMakerSearchGrid = dao.imdMakerGrid(vo);
		
		request.setAttribute("list", receiptMakerSearchGrid);
		request.setAttribute("receiptMakerSearchGrid","receiptMakerSearchGrid");
		if((receiptMakerSearchGrid.size())==0)
		{
			request.setAttribute("datalist","datalist");
		}
		logger.info("receiptMakerSearchGrid    Size:---"+receiptMakerSearchGrid.size());
//Neeraj Tripathi start
		//receiptMakerSearch.do?forward=forward&frdLoanID="+frdLoanID+"&frdInstrumentID="+frdInstrumentID;" +
		String forward=request.getParameter("forward");
		String search=request.getParameter("search");
		logger.info(" forward    "+forward);
		
		String frdLoanID="";
		String frdInstrumentID="";
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
//Neeraj Tripathi end
		return mapping.findForward("imdMakerSearch");	
	
	}
	
}
