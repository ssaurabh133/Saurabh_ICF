package com.customerService.actions;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.customerService.dao.CustomerServiceDAO;
import com.customerService.vo.CustomerServiceVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class LoanSummaryDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(LoanSummaryDispatchAction.class.getName());
	
	public ActionForward loanSummaryViewer(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		
		logger.info("In LoanSummaryDispatchAction ......loanSummaryViewer() ");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		//String branchId="";
		if(userobj!=null)
		{	
			//branchId=userobj.getBranchId();
		}else{
			logger.info(" in loanSummaryViewer method of LoanSummaryDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
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
		logger.info("before: "); 
		CustomerServiceDAO dao=(CustomerServiceDAO)DaoImplInstanceFactory.getDaoImplInstance(CustomerServiceDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		
		String loanId=CommonFunction.checkNull(request.getParameter("loanId"));
		CustomerServiceVo vo = new CustomerServiceVo();
	
		vo.setLoanId(loanId);
		//org.apache.commons.beanutils.BeanUtils.copyProperties(vo, SearchForCMDynaValidatorForm);
			
		ArrayList<CustomerServiceVo> loanSummaryList = dao.loanSummaryViewer(vo);
		request.setAttribute("loanSummaryList", loanSummaryList);
		
		ArrayList<CustomerServiceVo> vehicleDetailsList = dao.vehicleDetails(vo);
		request.setAttribute("vehicleDetailsList", vehicleDetailsList);
		
		ArrayList<CustomerServiceVo> caseMarkingList = dao.caseMarkingDetail(vo);
		request.setAttribute("caseMarkingList", caseMarkingList);
		
		ArrayList<CustomerServiceVo> secuitizationList = dao.secuitizationDetail(vo);
		request.setAttribute("secuitizationList", secuitizationList);
		
		ArrayList<CustomerServiceVo> rescheduleList = dao.reshcedulingDetail(vo);
		request.setAttribute("rescheduleList", rescheduleList);
		
		ArrayList<CustomerServiceVo> closureList = dao.closureDetail(vo);
		request.setAttribute("closureList", closureList);
		
		ArrayList<CustomerServiceVo> notePadList = dao.notePadListLoanSummaryDetail(vo);
		request.setAttribute("notePadList", notePadList);
		
		dao=null;	
		vo=null;
		loanId=null;
		return mapping.findForward("loanSummaryViewer");	
	
	}
}
